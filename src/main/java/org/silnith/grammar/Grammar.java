package org.silnith.grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;


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
    
    private class Worker implements Callable<Boolean> {
        
        private final ItemSet<T> itemSet;
        
        final T endOfFileSymbol;
        
        public Worker(final ItemSet<T> itemSet, final T endOfFileSymbol) {
            super();
            this.itemSet = itemSet;
            this.endOfFileSymbol = endOfFileSymbol;
        }
        
        @Override
        public Boolean call() throws Exception {
            boolean changed = false;
            final Set<LookaheadItem<T>> stateItems = itemSet.getItems();
            for (final LookaheadItem<T> item : stateItems) {
                if (item.isComplete()) {
                    continue;
                }
                final Symbol nextSymbolInProduction = item.getNextSymbol();
                if (endOfFileSymbol.equals(nextSymbolInProduction)) {
                    continue;
                }
                final ItemSet<T> newParserState = calculateGoto(stateItems, nextSymbolInProduction);
                final Edge<T> newEdge = new Edge<>(itemSet, nextSymbolInProduction, newParserState);
                
                final Integer previousName = addState(newParserState);
                if (previousName == null) {
                    changed = true;
                }
                final Boolean previousEdge = edges.putIfAbsent(newEdge, true);
                if (previousEdge == null) {
                    changed = true;
                }
            }
            return changed;
        }
        
    }
    
    private final SetFactory<T> terminalSetFactory;
    
    private final Set<T> lexicon;
    
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
    
//    private volatile boolean computed;
    
    private final ConcurrentMap<ItemSet<T>, Integer> parserStates;
    
    private final ConcurrentMap<Integer, ItemSet<T>> parserStatesByName;
    
    private final AtomicInteger stateCounter;
    
    private final ConcurrentMap<Edge<T>, Boolean> edges;
    
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
        this.terminalSetFactory = terminalSetFactory;
        this.lexicon = this.terminalSetFactory.getNewSet();
        this.productions = new HashMap<>();
        this.nullable = new HashSet<>();
        this.first = new HashMap<>();
        this.follow = new HashMap<>();
        
//        this.computed = false;
        
        this.parserStates = new ConcurrentHashMap<>();
        this.parserStatesByName = new ConcurrentHashMap<>();
        this.stateCounter = new AtomicInteger();
        this.edges = new ConcurrentHashMap<>();
    }
    
    /**
     * Returns the lexicon for the language.  This is the set of terminal symbols that
     * compose anything written in the language.
     * <p>
     * In most cases, this will be ASCII or Unicode characters.
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
//        computed = false;
        
        parserStates.clear();
        parserStatesByName.clear();
        stateCounter.set(0);
        edges.clear();
        
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
     * Adds a terminal symbol to the grammar.
     * <p>
     * Note that any terminal symbols used in productions are automatically added.
     * 
     * @param terminal the terminal symbol to add to the grammar
     */
    public void addTerminalSymbol(final T terminal) {
//        computed = false;
        lexicon.add(terminal);
    }
    
    @SafeVarargs
    public final void addTerminalSymbols(final T... terminals) {
//        computed = false;
        lexicon.addAll(Arrays.asList(terminals));
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
//        computed = false;
        final Production production = new Production(productionHandler, rightHandSide);
        final Set<Production> productionSet = getProductionSet(leftHandSide);
        productionSet.add(production);
        addTerminalSymbolsInternal(rightHandSide);
    }
    
    private void addTerminalSymbolsInternal(final Symbol... symbols) {
        for (final Symbol symbol : symbols) {
            if (symbol instanceof TerminalSymbol) {
                /*
                 * If a client specifies an enum type as T but then calls addProduction with a TerminalSymbol that is
                 * not a member of that enum, this will throw a ClassCastException when the terminal is added.
                 */
                addTerminalSymbol((T) symbol);
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
    
    /**
     * Returns the first set for the symbol.  This is a live object, modifications will affect the state of the grammar.
     * 
     * @param symbol
     * @return the first set for the symbol
     */
    protected Set<T> getFirstSet(final Symbol symbol) {
        if (first.containsKey(symbol)) {
            return first.get(symbol);
        } else {
            final Set<T> newSet = terminalSetFactory.getNewSet();
            first.put(symbol, newSet);
            return newSet;
        }
    }
    
    private void computeFirst() {
        first.clear();
        for (final T terminalSymbol : lexicon) {
            final Set<T> firstSet = getFirstSet(terminalSymbol);
            firstSet.add(terminalSymbol);
        }
        
        boolean changed;
        do {
            changed = false;
            for (final Map.Entry<NonTerminalSymbol, Set<Production>> entry : productions.entrySet()) {
                final NonTerminalSymbol leftHandSide = entry.getKey();
                final Set<T> firstSetForLeftHandSide = getFirstSet(leftHandSide);
                final Set<Production> productions = entry.getValue();
                
                for (final Production production : productions) {
                    final List<Symbol> symbols = production.getSymbols();
                    boolean changedByProduction = expandFirstSetByProduction(firstSetForLeftHandSide, symbols);
                    changed = changedByProduction || changed;
                }
            }
        } while (changed);
    }

    protected Set<T> getFollowSet(final Symbol symbol) {
        if (follow.containsKey(symbol)) {
            return follow.get(symbol);
        } else {
            final Set<T> newSet = terminalSetFactory.getNewSet();
            follow.put(symbol, newSet);
            return newSet;
        }
    }
    
    private void computeFollow() {
        follow.clear();
        boolean changed;
        do {
            changed = false;
            for (final Map.Entry<NonTerminalSymbol, Set<Production>> entry : productions.entrySet()) {
                final NonTerminalSymbol leftHandSide = entry.getKey();
                final Set<T> followSetForLeftHandSide = getFollowSet(leftHandSide);
                final Set<Production> productions = entry.getValue();
                
                for (final Production production : productions) {
                    final List<Symbol> productionSymbols = production.getSymbols();
                    final ListIterator<Symbol> revIter = productionSymbols.listIterator(productionSymbols.size());
                    while (revIter.hasPrevious()) {
                        final Symbol symbol = revIter.previous();
                        final Set<T> followSetForSymbolInProduction = getFollowSet(symbol);
                        changed = followSetForSymbolInProduction.addAll(followSetForLeftHandSide) || changed;
                        if (!nullable.contains(symbol)) {
                            break;
                        }
                    }
                    
                    final ListIterator<Symbol> forwardIter = productionSymbols.listIterator();
                    while (forwardIter.hasNext()) {
                        final int startIndex = forwardIter.nextIndex();
                        final Symbol startSymbol = forwardIter.next();
                        final Set<T> followSetForRangeStart = getFollowSet(startSymbol);
                        final ListIterator<Symbol> innerIter = productionSymbols.listIterator(startIndex + 1);
                        while (innerIter.hasNext()) {
                            final Symbol endSymbol = innerIter.next();
                            final Set<T> firstSetForRangeEnd = getFirstSet(endSymbol);
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
    
    protected void compute() {
        computeNullable();
        computeFirst();
        computeFollow();
    }
    
    protected LookaheadItem<T> createInitialProduction(final NonTerminalSymbol symbol, final T endOfFileSymbol) {
        getFirstSet(endOfFileSymbol).add(endOfFileSymbol);
        final Item item = new Item(START, new Production(new IdentityProductionHandler(), symbol, endOfFileSymbol), 0);
        final Set<T> lookaheadSet = terminalSetFactory.getNewSet();
        lookaheadSet.add(endOfFileSymbol);
        return new LookaheadItem<>(item, lookaheadSet);
    }
    
    protected Set<List<Symbol>> getProductionRemainders(final Item item, final Collection<T> lookaheadSet) {
        final List<Symbol> symbols = item.getRightHandSide().getSymbols();
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
            final Set<T> firstSetForSymbolInProduction = getFirstSet(symbol);
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

    protected ItemSet<T> calculateClosure(final Collection<LookaheadItem<T>> items) {
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
                                final Item newItem = new Item(nextNonTerminalSymbol, production, 0);
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
            itemSet.add(new LookaheadItem<>(entry.getKey(), entry.getValue()));
        }
        return new ItemSet<>(itemSet);
    }
    
    protected ItemSet<T> calculateGoto(final Collection<LookaheadItem<T>> itemSet, final Symbol symbol) {
        final Set<LookaheadItem<T>> jset = new HashSet<>(itemSet.size());
        for (final LookaheadItem<T> item : itemSet) {
            if (item.isComplete()) {
                continue;
            }
            final Symbol nextSymbol = item.getNextSymbol();
            if (symbol.equals(nextSymbol)) {
                final Item newItem =
                        new Item(item.getLeftHandSide(), item.getRightHandSide(), item.getParserPosition() + 1);
                final LookaheadItem<T> newLookaheadItem = new LookaheadItem<>(newItem, item.getLookaheadSet());
                jset.add(newLookaheadItem);
            }
        }
        return calculateClosure(jset);
    }
    
    /**
     * Creates a parser for the grammar.  This is called after all calls to
     * {@link #addTerminalSymbol} and
     * {@link #addProduction}.
     * 
     * @param startSymbol the initial non-terminal symbol that the parser will attempt to produce
     *         from the input stream of terminal symbols
     * @param endOfFileSymbol the terminal symbol that represents the end of the input
     * @return a parser for the language defined by this grammar
     */
    public Parser<T> createParser(final NonTerminalSymbol startSymbol, final T endOfFileSymbol) {
        compute();
        final long startTime = System.currentTimeMillis();
        
        final Set<ItemSet<T>> parserStates = new HashSet<>();
        final Set<Edge<T>> edges = new HashSet<>();
        final LookaheadItem<T> initialProduction = createInitialProduction(startSymbol, endOfFileSymbol);
        final ItemSet<T> startState = calculateClosure(Collections.singleton(initialProduction));
        parserStates.add(startState);
        boolean changed;
        do {
            final Set<ItemSet<T>> newParserStates = new HashSet<>();
            final Set<Edge<T>> newEdges = new HashSet<>();
            for (final ItemSet<T> parserState : parserStates) {
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
                    final ItemSet<T> newParserState = calculateGoto(stateItems, nextSymbolInProduction);
                    final Edge<T> newEdge = new Edge<>(parserState, nextSymbolInProduction, newParserState);
                    newParserStates.add(newParserState);
                    newEdges.add(newEdge);
                }
//                System.out.println();
            }
            final boolean addedParserStates = parserStates.addAll(newParserStates);
            final boolean addedEdges = edges.addAll(newEdges);
            changed = addedParserStates || addedEdges;
            System.out.print("Number of states: ");
            System.out.println(parserStates.size());
            System.out.print("Number of edges: ");
            System.out.println(edges.size());
        } while (changed);
        
        final long endTime = System.currentTimeMillis();
        
        System.out.print("Parser states creation, Duration: ");
        System.out.println(endTime - startTime);
        
        final Parser<T> parser = new Parser<>(parserStates, edges, startState, endOfFileSymbol);
        return parser;
    }
    
    private Integer addState(final ItemSet<T> state) {
        final Integer firstTry = parserStates.get(state);
        if (firstTry != null) {
            return firstTry;
        } else {
            final int name = stateCounter.incrementAndGet();
            final Integer secondTry = parserStates.putIfAbsent(state, name);
            parserStatesByName.put(name, state);
            return secondTry;
        }
    }
    
    /**
     * A parallelized version of {@link #createParser}.
     * 
     * @param startSymbol the initial non-terminal symbol that the parser will attempt to produce
     *         from the input stream of terminal symbols
     * @param endOfFileSymbol the terminal symbol that represents the end of the input
     * @param executorService the executor service to use
     * @return a parser for the language defined by this grammar
     * @throws InterruptedException sometimes
     * @throws ExecutionException other times
     */
    public Parser<T> threadedCreateParser(final NonTerminalSymbol startSymbol, final T endOfFileSymbol,
            final ExecutorService executorService) throws InterruptedException, ExecutionException {
        compute();
        parserStates.clear();
        parserStatesByName.clear();
        stateCounter.set(0);
        edges.clear();
        final long startTime = System.currentTimeMillis();
        
        final LookaheadItem<T> initialProduction = createInitialProduction(startSymbol, endOfFileSymbol);
        final ItemSet<T> startState = calculateClosure(Collections.singleton(initialProduction));
        parserStates.put(startState, stateCounter.get());
        parserStatesByName.putIfAbsent(stateCounter.get(), startState);
        final ArrayList<Worker> workers = new ArrayList<>();
        boolean changed;
        do {
            changed = false;
            workers.clear();
            for (final ItemSet<T> parserState : parserStates.keySet()) {
                final Worker worker = new Worker(parserState, endOfFileSymbol);
                workers.add(worker);
            }
            final List<Future<Boolean>> results = executorService.invokeAll(workers);
            for (final Future<Boolean> result : results) {
                changed = result.get() || changed;
            }
            System.out.print("Number of states: ");
            System.out.println(parserStates.size());
            System.out.print("Number of edges: ");
            System.out.println(edges.size());
        } while (changed);
        
        final long endTime = System.currentTimeMillis();
        
        System.out.print("Parser states creation, Duration: ");
        System.out.println(endTime - startTime);
        
        final Parser<T> parser = new Parser<>(parserStates.keySet(), edges.keySet(), startState, endOfFileSymbol);
        return parser;
    }
    
}
