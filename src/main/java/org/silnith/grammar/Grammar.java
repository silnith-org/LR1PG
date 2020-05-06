package org.silnith.grammar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * A grammar is the definition of a language.  It is a collection of productions
 * over a set of symbols.  Symbols are divided into terminal and non-terminal
 * symbols.
 * 
 * @param <T> the concrete type of identifiers for terminal symbols
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
// @param <D> the type of the abstract syntax tree nodes generated by the {@link ProductionHandler} implementations
public class Grammar<T extends TerminalSymbol> {
    
    /**
     * A default implementation that returns instances of {@link HashSet}.
     * 
     * @param <V> the type of elements in the set
     */
    public static class DefaultSetFactory<V> implements SetFactory<V> {
        
        @Override
        public Set<V> getNewSet() {
            return new HashSet<>();
        }
        
        @Override
        public Set<V> getNewSet(final Collection<V> c) {
            return new HashSet<>(c);
        }
        
    }
    
    private static final NonTerminalSymbol START = new NonTerminalSymbol() {
        
        @Override
        public String toString() {
            return "START";
        }
        
    };
    
    /**
     * A production handler that trivially returns the first element of the right-hand side.
     */
    private class IdentityProductionHandler implements ProductionHandler {
        
        @Override
        public Object handleReduction(final List<DataStackElement> rightHandSide) {
            return rightHandSide.get(0).getAbstractSyntaxTreeElement();
        }
        
    }
    
    private final ItemFactory itemFactory;

    private final LookaheadItemFactory<T> lookaheadItemFactory;

    private final ParserStateFactory<T> parserStateFactory;

    private final EdgeFactory<T> edgeFactory;

    private final SetFactory<T> terminalSetFactory;
    
    private final Set<T> lexicon;
    
    private final Set<NonTerminalSymbol> nonTerminalSymbols;
    
    /**
     * The mapping of non-terminal symbols to productions.
     */
    private final Map<NonTerminalSymbol, Set<Production>> productions;
    
    /**
     * The nullable non-terminal symbols.
     */
    private final Set<NonTerminalSymbol> nullable;
    
    /**
     * The first set for each symbol.
     */
    private final Map<Symbol, Set<T>> first;
    
    /**
     * The follow set for each symbol.
     */
    private final Map<Symbol, Set<T>> follow;
    
    /**
     * Creates a new grammar.
     */
    public Grammar() {
        this(new DefaultSetFactory<T>());
    }
    
    /**
     * Creates a new grammar.
     * 
     * @param terminalSetFactory a factory for sets of terminal symbols
     */
    public Grammar(final SetFactory<T> terminalSetFactory) {
        super();
        if (terminalSetFactory == null) {
            throw new IllegalArgumentException();
        }
        this.itemFactory = new ItemFactory();
        this.lookaheadItemFactory = new LookaheadItemFactory<>();
        this.parserStateFactory = new ParserStateFactory<>();
        this.edgeFactory = new EdgeFactory<>();
        this.terminalSetFactory = terminalSetFactory;
        this.lexicon = this.terminalSetFactory.getNewSet();
        this.nonTerminalSymbols = new HashSet<>();
        this.productions = new HashMap<>();
        this.nullable = new HashSet<>();
        this.first = new HashMap<>();
        this.follow = new HashMap<>();
    }
    
    /**
     * Returns the lexicon for the language.  This is the set of terminal symbols that
     * compose anything written in the language.
     * <p>
     * There need not be separate terminal symbols for all possible inputs because parsers operate
     * on streams of tokens, where each token is a terminal symbol coupled with additional data.
     * 
     * @return the language lexicon
     */
    public Set<T> getLexicon() {
        return lexicon;
    }
    
    /**
     * Returns the nullable set.  This is the set of non-terminal symbols that are nullable.
     * 
     * @return the nullable set
     */
    public Set<NonTerminalSymbol> getNullableSet() {
        return nullable;
    }
    
    /**
     * Returns a map containing the first set for each symbol in the language.
     * 
     * @return a map of first sets for the symbols
     */
    public Map<Symbol, Set<T>> getFirstSet() {
        return first;
    }
    
    /**
     * Returns a map containing the follow set for each symbol in the language.
     * 
     * @return a map of follow sets for the symbols
     */
    public Map<Symbol, Set<T>> getFollowSet() {
        return follow;
    }
    
    /**
     * Resets the grammar to its default state where it has no production and no lexicon.
     */
    public void clear() {
        lexicon.clear();
        productions.clear();
        
        uncompute();
    }
    
    private void uncompute() {
        nullable.clear();
        first.clear();
        follow.clear();
    }
    
    /**
     * Returns an instance of {@link NonTerminalSymbol} for clients to use.
     * This is a convenience function so clients do not need to implement {@link NonTerminalSymbol} unless they really
     * want to.
     * 
     * @param name the name for the non-terminal symbol
     * @return a non-terminal symbol
     */
    public NonTerminalSymbol getNonTerminalSymbol(final String name) {
        return new NonTerminal(name);
    }
    
    /**
     * Adds a production to the grammar.
     * 
     * @param leftHandSide the non-terminal symbol
     * @param productionHandler the handler that performs the reduction for the production
     * @param rightHandSide the list of symbols that can be reduced to the non-terminal symbol
     */
    public void addProduction(final NonTerminalSymbol leftHandSide, final ProductionHandler productionHandler,
            final Symbol... rightHandSide) {
        final Production production = new Production(productionHandler, rightHandSide);
        final Set<Production> productionSet = getProductionSet(leftHandSide);
        productionSet.add(production);
        addSymbols(leftHandSide);
        addSymbols(rightHandSide);
    }
    
    private void addSymbols(final Symbol... symbols) {
        for (final Symbol symbol : symbols) {
            if (symbol instanceof TerminalSymbol) {
                /*
                 * If a client specifies an enum type as T but then calls addProduction with a TerminalSymbol that is
                 * not a member of that enum, this will throw a ClassCastException when the terminal is added.
                 */
                lexicon.add((T) symbol);
            } else if (symbol instanceof NonTerminalSymbol) {
                nonTerminalSymbols.add((NonTerminalSymbol) symbol);
            } else {
                throw new IllegalArgumentException();
            }
        }
    }
    
    private Set<Production> getProductionSet(final NonTerminalSymbol symbol) {
        if (productions.containsKey(symbol)) {
            return productions.get(symbol);
        } else {
            final Set<Production> newSet = new HashSet<>();
            productions.put(symbol, newSet);
            return newSet;
        }
    }
    
    protected class NullableSetComputer implements Callable<Set<NonTerminalSymbol>> {
        
        private final NonTerminalSymbol nonTerminalSymbol;
        
        private final Production production;

        public NullableSetComputer(final NonTerminalSymbol nonTerminalSymbol, final Production production) {
            super();
            this.nonTerminalSymbol = nonTerminalSymbol;
            this.production = production;
        }

        @Override
        public Set<NonTerminalSymbol> call() throws Exception {
            if (nullable.containsAll(production.getSymbols())) {
                return Collections.singleton(nonTerminalSymbol);
            } else {
                return Collections.emptySet();
            }
        }
        
    }
    
    private void threadedComputeNullable(final ExecutorService executorService) throws InterruptedException, ExecutionException {
        nullable.clear();

        final List<Callable<Set<NonTerminalSymbol>>> tasks = new ArrayList<>();
        boolean changed;
        do {
            tasks.clear();
            
            for (final Map.Entry<NonTerminalSymbol, Set<Production>> entry : productions.entrySet()) {
                final NonTerminalSymbol nonTerminalSymbol = entry.getKey();
                final Set<Production> productionSet = entry.getValue();

                if (nullable.contains(nonTerminalSymbol)) {
                    continue;
                }
                for (final Production production : productionSet) {
                    final NullableSetComputer nullableSetComputer = new NullableSetComputer(nonTerminalSymbol, production);
                    
                    tasks.add(nullableSetComputer);
                }
            }
            
            final List<Future<Set<NonTerminalSymbol>>> futures = executorService.invokeAll(tasks);

            changed = false;
            
            for (final Future<Set<NonTerminalSymbol>> future : futures) {
                final Set<NonTerminalSymbol> set = future.get();
                
                final boolean b = nullable.addAll(set);
                
                changed = b || changed;
            }
        } while (changed);
    }
    
    /**
     * Compute the nullable set for each symbol.
     */
    private void computeNullable() {
        nullable.clear();
        
        boolean changed;
        do {
            changed = false;
            for (final Map.Entry<NonTerminalSymbol, Set<Production>> entry : productions.entrySet()) {
                final NonTerminalSymbol nonTerminal = entry.getKey();
                if (nullable.contains(nonTerminal)) {
                    continue;
                }
                final Set<Production> productionsForSymbol = entry.getValue();
                
                for (final Production production : productionsForSymbol) {
                    if (nullable.containsAll(production.getSymbols())) {
                        final boolean b = nullable.add(nonTerminal);
                        assert b;
                        changed = true;
                        break;
                    }
                }
            }
        } while (changed);
    }
    
    protected class FirstSetComputer implements Callable<Map<NonTerminalSymbol, Set<T>>> {
        
        private final NonTerminalSymbol nonTerminalSymbol;
        
        private final Production production;

        public FirstSetComputer(final NonTerminalSymbol nonTerminalSymbol, final Production production) {
            super();
            this.nonTerminalSymbol = nonTerminalSymbol;
            this.production = production;
        }

        @Override
        public Map<NonTerminalSymbol, Set<T>> call() throws Exception {
            final Set<T> firstSet = terminalSetFactory.getNewSet();
            
            for (final Symbol symbol : production.getSymbols()) {
                firstSet.addAll(first.get(symbol));
                
                if (!nullable.contains(symbol)) {
                    break;
                }
            }
            
            return Collections.singletonMap(nonTerminalSymbol, firstSet);
        }
        
    }
    
    private void threadedComputeFirstSet(final ExecutorService executorService) throws InterruptedException, ExecutionException {
        first.clear();
        for (final T terminalSymbol : lexicon) {
            first.put(terminalSymbol, terminalSetFactory.getNewSet(Collections.singleton(terminalSymbol)));
        }
        for (final NonTerminalSymbol nonTerminalSymbol : nonTerminalSymbols) {
            first.put(nonTerminalSymbol, terminalSetFactory.getNewSet());
        }
        
        final List<FirstSetComputer> tasks = new ArrayList<>();
        boolean changed;
        do {
            tasks.clear();
            
            for (final Map.Entry<NonTerminalSymbol, Set<Production>> entry : productions.entrySet()) {
                final NonTerminalSymbol nonTerminalSymbol = entry.getKey();
                final Set<Production> productionSet = entry.getValue();
                
                for (final Production production : productionSet) {
                    final FirstSetComputer firstSetComputer = new FirstSetComputer(nonTerminalSymbol, production);
                    tasks.add(firstSetComputer);
                }
            }
            
            final List<Future<Map<NonTerminalSymbol, Set<T>>>> futures = executorService.invokeAll(tasks);
            
            changed = false;
            
            for (final Future<Map<NonTerminalSymbol, Set<T>>> future : futures) {
                final Map<NonTerminalSymbol, Set<T>> map = future.get();
                
                for (final Map.Entry<NonTerminalSymbol, Set<T>> entry : map.entrySet()) {
                    final NonTerminalSymbol nonTerminalSymbol = entry.getKey();
                    final Set<T> firstSet = entry.getValue();
                    
                    final boolean b = first.get(nonTerminalSymbol).addAll(firstSet);
                    
                    changed = b || changed;
                }
            }
        } while (changed);
    }
    
    private void computeFirst() {
        first.clear();
        for (final T terminalSymbol : lexicon) {
            first.put(terminalSymbol, terminalSetFactory.getNewSet(Collections.singleton(terminalSymbol)));
        }
        for (final NonTerminalSymbol nonTerminalSymbol : nonTerminalSymbols) {
            first.put(nonTerminalSymbol, terminalSetFactory.getNewSet());
        }
        
        boolean changed;
        do {
            changed = false;
            for (final Map.Entry<NonTerminalSymbol, Set<Production>> entry : productions.entrySet()) {
                final NonTerminalSymbol leftHandSide = entry.getKey();
                final Set<Production> productions = entry.getValue();
                
                final Set<T> firstSetForLeftHandSide = first.get(leftHandSide);
                for (final Production production : productions) {
                    final List<Symbol> symbols = production.getSymbols();
                    boolean changedByProduction = expandFirstSetByProduction(firstSetForLeftHandSide, symbols);
                    changed = changedByProduction || changed;
                }
            }
        } while (changed);
    }
    
    private class FollowSetComputer implements Callable<Map<Symbol, Set<T>>> {
        
        private final NonTerminalSymbol nonTerminalSymbol;
        
        private final Production production;
        
        public FollowSetComputer(final NonTerminalSymbol nonTerminalSymbol, final Production production) {
            super();
            this.nonTerminalSymbol = nonTerminalSymbol;
            this.production = production;
        }
        
        @Override
        public Map<Symbol, Set<T>> call() throws Exception {
            final Map<Symbol, Set<T>> followSetAdditions = new HashMap<>();
            
            followSetAdditions.put(nonTerminalSymbol, terminalSetFactory.getNewSet());
            for (final Symbol symbol : production.getSymbols()) {
                followSetAdditions.put(symbol, terminalSetFactory.getNewSet());
            }
            
            final Set<T> nonTerminalFollowSet = follow.get(nonTerminalSymbol);
            
            final List<Symbol> productionSymbols = production.getSymbols();
            final ListIterator<Symbol> revIter = productionSymbols.listIterator(productionSymbols.size());
            while (revIter.hasPrevious()) {
                final Symbol symbol = revIter.previous();
                
                final Set<T> additionsForSymbol = followSetAdditions.get(symbol);
                additionsForSymbol.addAll(nonTerminalFollowSet);
                
                if (!nullable.contains(symbol)) {
                    break;
                }
            }
            
            final ListIterator<Symbol> forwardIter = productionSymbols.listIterator();
            while (forwardIter.hasNext()) {
                final int startIndex = forwardIter.nextIndex();
                final Symbol startSymbol = forwardIter.next();
                
                final Set<T> additionsForSymbol = followSetAdditions.get(startSymbol);
                
                final ListIterator<Symbol> innerIter = productionSymbols.listIterator(startIndex + 1);
                while (innerIter.hasNext()) {
                    final Symbol endSymbol = innerIter.next();
                    
                    additionsForSymbol.addAll(first.get(endSymbol));
                    
                    if (!nullable.contains(endSymbol)) {
                        break;
                    }
                }
            }
            
            return followSetAdditions;
        }
        
    }
    
    private void threadedComputeFollowSet(final ExecutorService executorService) throws InterruptedException, ExecutionException {
        follow.clear();
        for (final T terminalSymbol : lexicon) {
            follow.put(terminalSymbol, terminalSetFactory.getNewSet());
        }
        for (final NonTerminalSymbol nonTerminalSymbol : nonTerminalSymbols) {
            follow.put(nonTerminalSymbol, terminalSetFactory.getNewSet());
        }
        
        final List<FollowSetComputer> tasks = new ArrayList<>();
        boolean changed;
        do {
            tasks.clear();
            
            for (final Map.Entry<NonTerminalSymbol, Set<Production>> entry : productions.entrySet()) {
                final NonTerminalSymbol nonTerminalSymbol = entry.getKey();
                final Set<Production> productionSet = entry.getValue();
                
                for (final Production production : productionSet) {
                    final FollowSetComputer followSetComputer = new FollowSetComputer(nonTerminalSymbol, production);
                    tasks.add(followSetComputer);
                }
            }
            
            final List<Future<Map<Symbol, Set<T>>>> futures = executorService.invokeAll(tasks);
            
            changed = false;
            
            for (final Future<Map<Symbol, Set<T>>> future : futures) {
                final Map<Symbol, Set<T>> map = future.get();
                
                for (final Map.Entry<Symbol, Set<T>> entry : map.entrySet()) {
                    final Symbol symbol = entry.getKey();
                    final Set<T> followSet = entry.getValue();
                    
                    final boolean b = follow.get(symbol).addAll(followSet);
                    
                    changed = b || changed;
                }
            }
        } while (changed);
    }
    
    private void computeFollow() {
        follow.clear();
        for (final T terminalSymbol : lexicon) {
            follow.put(terminalSymbol, terminalSetFactory.getNewSet());
        }
        for (final NonTerminalSymbol nonTerminalSymbol : nonTerminalSymbols) {
            follow.put(nonTerminalSymbol, terminalSetFactory.getNewSet());
        }
        
        boolean changed;
        do {
            changed = false;
            for (final Map.Entry<NonTerminalSymbol, Set<Production>> entry : productions.entrySet()) {
                final NonTerminalSymbol leftHandSide = entry.getKey();
                final Set<T> followSetForLeftHandSide = follow.get(leftHandSide);
                final Set<Production> productions = entry.getValue();
                
                for (final Production production : productions) {
                    final List<Symbol> productionSymbols = production.getSymbols();
                    final ListIterator<Symbol> revIter = productionSymbols.listIterator(productionSymbols.size());
                    while (revIter.hasPrevious()) {
                        final Symbol symbol = revIter.previous();
                        final Set<T> followSetForSymbolInProduction = follow.get(symbol);
                        changed = followSetForSymbolInProduction.addAll(followSetForLeftHandSide) || changed;
                        if (!nullable.contains(symbol)) {
                            break;
                        }
                    }
                    
                    final ListIterator<Symbol> forwardIter = productionSymbols.listIterator();
                    while (forwardIter.hasNext()) {
                        final int startIndex = forwardIter.nextIndex();
                        final Symbol startSymbol = forwardIter.next();
                        final Set<T> followSetForRangeStart = follow.get(startSymbol);
                        final ListIterator<Symbol> innerIter = productionSymbols.listIterator(startIndex + 1);
                        while (innerIter.hasNext()) {
                            final Symbol endSymbol = innerIter.next();
                            final Set<T> firstSetForRangeEnd = first.get(endSymbol);
                            followSetForRangeStart.addAll(firstSetForRangeEnd);
                            if (!nullable.contains(endSymbol)) {
                                break;
                            }
                        }
                    }
                }
            }
        } while (changed);
    }
    
    protected void threadedCompute(final ExecutorService executorService) throws InterruptedException, ExecutionException {
        threadedComputeNullable(executorService);
        threadedComputeFirstSet(executorService);
        threadedComputeFollowSet(executorService);
    }
    
    protected void compute() {
        computeNullable();
        computeFirst();
        computeFollow();
    }
    
    protected Set<List<Symbol>> getProductionRemainders(final Item item, final Collection<T> lookaheadSet) {
        final List<Symbol> symbols = item.getProduction().getSymbols();
        final int secondSymbolIndex = item.getParserPosition() + 1;
        if (secondSymbolIndex < symbols.size()) {
            final List<Symbol> remainder = new ArrayList<>(symbols.subList(secondSymbolIndex, symbols.size()));
            final Set<List<Symbol>> productionRemainders = new HashSet<>();
            for (final T lookahead : lookaheadSet) {
                final List<Symbol> listCopy = new ArrayList<>(remainder);
                listCopy.add(lookahead);
                productionRemainders.add(listCopy);
            }
            return productionRemainders;
        } else {
            final Set<List<Symbol>> productionRemainders = new HashSet<>();
            for (final T lookahead : lookaheadSet) {
                productionRemainders.add(Collections.<Symbol>singletonList(lookahead));
            }
            return productionRemainders;
        }
    }
    
    private boolean expandFirstSetByProduction(final Set<T> firstSet, final List<Symbol> symbols) {
        boolean changedByProduction = false;
        for (final Symbol symbol : symbols) {
            final Set<T> firstSetForSymbolInProduction = first.get(symbol);
            final boolean addedElementsToFirstSet = firstSet.addAll(firstSetForSymbolInProduction);
            changedByProduction = addedElementsToFirstSet || changedByProduction;
            
            if (!nullable.contains(symbol)) {
                break;
            }
        }
        return changedByProduction;
    }

    protected Set<T> calculateFirstSet(final List<Symbol> symbols) {
        final Set<T> firstSet = terminalSetFactory.getNewSet();
        expandFirstSetByProduction(firstSet, symbols);
        return firstSet;
    }
    
    protected ParserState<T> calculateClosure(final Collection<LookaheadItem<T>> items) {
        final Map<Item, Set<T>> itemLookaheadMap = new HashMap<>();
        for (final LookaheadItem<T> lookaheadItem : items) {
            final Set<T> newSet = terminalSetFactory.getNewSet(lookaheadItem.getLookaheadSet());
            itemLookaheadMap.put(lookaheadItem.getItem(), newSet);
        }
        boolean changed;
        do {
            changed = false;
            // Copy the item set because we are going to modify it in the loop.
            // Without the copy this throws ConcurrentModificationExceptions.
            for (final Item item : new ArrayList<>(itemLookaheadMap.keySet())) {
                if (item.isComplete()) {
                    continue;
                } else {
                    final Symbol nextSymbol = item.getNextSymbol();
                    final Set<List<Symbol>> remainderList = getProductionRemainders(item, itemLookaheadMap.get(item));
                    for (final List<Symbol> remainder : remainderList) {
                        final Set<T> firstSetOfRemainder = calculateFirstSet(remainder);
                        if (nextSymbol instanceof NonTerminalSymbol) {
                            final NonTerminalSymbol nextNonTerminalSymbol = (NonTerminalSymbol) nextSymbol;
                            final Set<Production> productionsForNextSymbol = getProductionSet(nextNonTerminalSymbol);
                            for (final Production production : productionsForNextSymbol) {
                                final Item newItem = itemFactory.createItem(nextNonTerminalSymbol, production, 0);
                                final Set<T> lookaheadSet = itemLookaheadMap.get(newItem);
                                if (lookaheadSet == null) {
                                    final Set<T> newSet = terminalSetFactory.getNewSet(firstSetOfRemainder);
                                    itemLookaheadMap.put(newItem, newSet);
                                    changed = true;
                                } else {
                                    changed = lookaheadSet.addAll(firstSetOfRemainder) || changed;
                                }
                            }
                        }
                    }
                }
            }
        } while (changed);
        final Set<LookaheadItem<T>> itemSet = new HashSet<>(itemLookaheadMap.size());
        for (final Map.Entry<Item, Set<T>> entry : itemLookaheadMap.entrySet()) {
            itemSet.add(lookaheadItemFactory.createInstance(entry.getKey(), entry.getValue()));
        }
        return parserStateFactory.createInstance(itemSet);
    }
    
    protected ParserState<T> calculateGoto(final Collection<LookaheadItem<T>> itemSet, final Symbol symbol) {
        final Set<LookaheadItem<T>> jset = new HashSet<>(itemSet.size());
        for (final LookaheadItem<T> item : itemSet) {
            if (item.isComplete()) {
                continue;
            }
            final Symbol nextSymbol = item.getNextSymbol();
            if (symbol.equals(nextSymbol)) {
                final Item newItem =
                        itemFactory.createItem(item.getTarget(), item.getProduction(), item.getParserPosition() + 1);
                final LookaheadItem<T> newLookaheadItem = lookaheadItemFactory.createInstance(newItem, item.getLookaheadSet());
                jset.add(newLookaheadItem);
            }
        }
        return calculateClosure(jset);
    }
    
    private class ParserStateComputer implements Callable<Edge<T>> {
        
        private final ParserState<T> parserState;
        
        private final Set<LookaheadItem<T>> stateItems;
        
        private final LookaheadItem<T> item;
    
        public ParserStateComputer(final ParserState<T> parserState, final Set<LookaheadItem<T>> stateItems,
                final LookaheadItem<T> item) {
            super();
            this.parserState = parserState;
            this.stateItems = stateItems;
            this.item = item;
        }
    
        @Override
        public Edge<T> call() throws Exception {
            final Symbol nextSymbol = item.getNextSymbol();
            final ParserState<T> newParserState = calculateGoto(stateItems, nextSymbol);
            final Edge<T> newEdge = edgeFactory.createInstance(parserState, nextSymbol, newParserState);
            return newEdge;
        }
        
    }

//    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final ExecutorService executor = Executors.newFixedThreadPool(8);

    /**
     * Creates a parser for the grammar.  This is called after all calls to
     * {@link #addTerminalSymbol} and
     * {@link #addProduction}.
     * 
     * @param startSymbol the initial non-terminal symbol that the parser will attempt to produce
     *         from the input stream of terminal symbols
     * @param endOfFileSymbol the terminal symbol that represents the end of the input
     * @return a parser for the language defined by this grammar
     * @throws ExecutionException foo
     * @throws InterruptedException bar
     */
    public Parser<T> createParser(final NonTerminalSymbol startSymbol, final T endOfFileSymbol) {
        addProduction(START, new IdentityProductionHandler(), startSymbol, endOfFileSymbol);
        compute();
//        try {
//            threadedCompute(executor);
//        } catch (final InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//            return null;
//        }
        final long startTime = System.currentTimeMillis();
        
        final Set<ParserState<T>> parserStates = new HashSet<>();
        final Set<Edge<T>> edges = new HashSet<>();
        
        final Set<T> endOfFileSet = terminalSetFactory.getNewSet(Collections.singleton(endOfFileSymbol));
        final Set<LookaheadItem<T>> initialItems = new HashSet<>();
        for (final Production production : getProductionSet(START)) {
            final Item item = itemFactory.createItem(START, production, 0);
            final LookaheadItem<T> lookaheadItem = lookaheadItemFactory.createInstance(item, endOfFileSet);
            initialItems.add(lookaheadItem);
        }
        
        final ParserState<T> startState = calculateClosure(initialItems);
        parserStates.add(startState);
//        final List<ParserStateComputer> tasks = new ArrayList<>();
        boolean changed;
        do {
//            tasks.clear();
            
            final Set<ParserState<T>> newParserStates = new HashSet<>();
            final Set<Edge<T>> newEdges = new HashSet<>();
            
            for (final ParserState<T> parserState : parserStates) {
                final Set<LookaheadItem<T>> stateItems = parserState.getItems();
                for (final LookaheadItem<T> item : stateItems) {
//                    System.out.print('.');
                    if (item.isComplete()) {
                        continue;
                    }
                    final Symbol nextSymbolInProduction = item.getNextSymbol();
                    if (endOfFileSymbol.equals(nextSymbolInProduction)) {
                        continue;
                    }
//                    tasks.add(new ParserStateComputer(parserState, stateItems, item));
                    final ParserState<T> newParserState = calculateGoto(stateItems, nextSymbolInProduction);
                    final Edge<T> newEdge = edgeFactory.createInstance(parserState, nextSymbolInProduction, newParserState);
                    newParserStates.add(newParserState);
                    newEdges.add(newEdge);
                }
//                System.out.println();
            }
            
//            final List<Future<Edge<T>>> futures;
//            try {
//                futures = executor.invokeAll(tasks);
//            } catch (final InterruptedException e) {
//                e.printStackTrace();
//                return null;
//            }
//            for (final Future<Edge<T>> future : futures) {
//                final Edge<T> newEdge;
//                try {
//                    newEdge = future.get();
//                } catch (final InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                    return null;
//                }
//                final ParserState<T> finalState = newEdge.getFinalState();
//                
//                newParserStates.add(finalState);
//                newEdges.add(newEdge);
//            }
            
            final boolean addedParserStates = parserStates.addAll(newParserStates);
            final boolean addedEdges = edges.addAll(newEdges);
            changed = addedParserStates || addedEdges;
            
//            System.out.print("Number of states: ");
//            System.out.println(parserStates.size());
//            System.out.print("Number of edges: ");
//            System.out.println(edges.size());
        } while (changed);
        
        final long endTime = System.currentTimeMillis();
        
        System.out.print("Parser states creation, Duration: ");
        System.out.println(endTime - startTime);
        
        final Parser<T> parser = new Parser<>(parserStates, edges, startState, endOfFileSymbol);
        
//        System.out.print("Item factory call count: ");
//        System.out.println(itemFactory.getCallCount());
//        System.out.print("Item factory instance count: ");
//        System.out.println(itemFactory.getInstanceCount());
//
//        System.out.print("Look-ahead Item factory call count: ");
//        System.out.println(lookaheadItemFactory.getCallCount());
//        System.out.print("Look-ahead Item factory instance count: ");
//        System.out.println(lookaheadItemFactory.getInstanceCount());
//
//        System.out.print("Parser state factory call count: ");
//        System.out.println(parserStateFactory.getCallCount());
//        System.out.print("Parser state factory instance count: ");
//        System.out.println(parserStateFactory.getInstanceCount());
//
//        System.out.print("Edge factory call count: ");
//        System.out.println(edgeFactory.getCallCount());
//        System.out.print("Edge factory instance count: ");
//        System.out.println(edgeFactory.getInstanceCount());

        return parser;
    }
    
}
