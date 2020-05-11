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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;


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
    
    private static class StartSymbol implements NonTerminalSymbol {
    
        @Override
        public String toString() {
            return "START";
        }
    
    }

    /**
     * A production handler that trivially returns the first element of the right-hand side.
     */
    private static class IdentityProductionHandler implements ProductionHandler {
        
        @Override
        public Object handleReduction(final List<DataStackElement> rightHandSide) {
            return rightHandSide.get(0).getAbstractSyntaxTreeElement();
        }
        
    }

    private static final NonTerminalSymbol START = new StartSymbol();
    
    private final ItemFactory itemFactory;

    private final LookaheadItemFactory<T> lookaheadItemFactory;

    private final ParserStateFactory<T> parserStateFactory;

    private final EdgeFactory<T> edgeFactory;

    private final SetFactory<T> terminalSetFactory;
    
    /**
     * The lexicon for the language.
     */
    private final Set<T> lexicon;
    
    /**
     * All non-terminals used in productions.
     */
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
    
    private final Set<ParserState<T>> parserStates;

    private final Set<Edge<T>> edges;

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
        this.parserStates = new HashSet<>();
        this.edges = new HashSet<>();
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
        nonTerminalSymbols.clear();
        productions.clear();
        
        nullable.clear();
        first.clear();
        follow.clear();
        
        parserStates.clear();
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
     * Adds a production to the grammar.
     * 
     * @param nonTerminalSymbol the non-terminal symbol
     * @param productionHandler the handler that performs the reduction for the production
     * @param symbols the list of symbols that can be reduced to the non-terminal symbol
     */
    public void addProduction(final NonTerminalSymbol nonTerminalSymbol, final ProductionHandler productionHandler,
            final Symbol... symbols) {
        final Production production = new Production(productionHandler, symbols);
        final Set<Production> productionSet;
        if (productions.containsKey(nonTerminalSymbol)) {
            productionSet = productions.get(nonTerminalSymbol);
        } else {
            productionSet = new HashSet<Production>();
            productions.put(nonTerminalSymbol, productionSet);
        }
        productionSet.add(production);
        
        nonTerminalSymbols.add(nonTerminalSymbol);
        addSymbols(symbols);
    }
    
    /**
     * Adds all symbols to the {@link #lexicon} and {@link #nonTerminalSymbols}.
     * 
     * @param symbols the symbols to add
     */
    private void addSymbols(final Symbol... symbols) {
        for (final Symbol symbol : symbols) {
            if (symbol instanceof TerminalSymbol) {
                /*
                 * If a client specifies an enum type as T but then calls addProduction with a TerminalSymbol that is
                 * not a member of that enum, this will throw a ClassCastException when the terminal is added.
                 */
                final T terminalSymbol = (T) symbol;
                lexicon.add(terminalSymbol);
            } else if (symbol instanceof NonTerminalSymbol) {
                final NonTerminalSymbol nonTerminalSymbol = (NonTerminalSymbol) symbol;
                nonTerminalSymbols.add(nonTerminalSymbol);
                if ( !productions.containsKey(nonTerminalSymbol)) {
                    productions.put(nonTerminalSymbol, new HashSet<Production>());
                }
            } else {
                throw new IllegalArgumentException();
            }
        }
    }
    
    /**
     * Compute the nullable set.
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
     * Compute the first set for each symbol.
     */
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
    
    /**
     * Compute the follow set for each symbol.
     */
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
    
    protected void compute() {
        computeNullable();
        computeFirst();
        computeFollow();
    }
    
    /**
     * Finds the first set for a sequence of symbols.  This checks for symbols that are
     * nullable and walks the list of symbols coalescing first sets until it encounters
     * a symbol that is not nullable, or the list ends.
     * 
     * @param firstSet the set that receives the results
     * @param symbols the list of symbols
     * @return {@code true} if {@code firstSet} was modified by this method
     */
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
    
    private ParserState<T> calculateClosure(final Collection<LookaheadItem<T>> items) {
        /*
         * The canonical algorithm creates look-ahead items that are composed of a single item
         * and a single look-ahead terminal.  This modifies the classic algorithm by consolidating
         * identical items to a single item with a set of look-ahead terminals.
         * 
         * Since all the items are part of a single parser state, they will all share the same
         * outbound edges.  Therefore, this optimization causes no loss of fidelity in the
         * resulting parser.
         */
        final Map<Item, Set<T>> itemLookaheadMap = new HashMap<>();
        /*
         * Copy the items in the input set to a new map keyed by item
         * where the value is the look-ahead set for that item.
         */
        for (final LookaheadItem<T> lookaheadItem : items) {
            final Item item = lookaheadItem.getItem();
            final Set<T> lookaheadSet = lookaheadItem.getLookaheadSet();
            
            final Set<T> newSet = terminalSetFactory.getNewSet(lookaheadSet);
            itemLookaheadMap.put(item, newSet);
        }

        final Map<Item, Set<T>> additions = new HashMap<Item, Set<T>>();
        
        boolean changed;
        do {
            additions.clear();
            
            /*
             * Go through all the items in the nascent parser state.
             * If the next symbol in an item is a non-terminal, then all productions that can be
             * reduced to that non-terminal need to be added to the potential parser state.
             */
            for (final Map.Entry<Item, Set<T>> entry : itemLookaheadMap.entrySet()) {
                final Item item = entry.getKey();
                final Set<T> lookaheadSet = entry.getValue();
                
                if (item.isComplete()) {
                    continue;
                }
                
                final Symbol nextSymbol = item.getNextSymbol();
                if (!(nextSymbol instanceof NonTerminalSymbol)) {
                    continue;
                }
                
                final NonTerminalSymbol nextNonTerminalSymbol = (NonTerminalSymbol) nextSymbol;
                final Set<Production> productionsForNextSymbol = productions.get(nextNonTerminalSymbol);
                
                /*
                 * Add all the productions for the non-terminal to the parser state.
                 */
                final Set<Item> newItems = new HashSet<>();
                for (final Production production : productionsForNextSymbol) {
                    final Item newItem = itemFactory.createItem(nextNonTerminalSymbol, production, 0);
                    newItems.add(newItem);
                }
                
                /*
                 * Given an item and a look-ahead set, get all possible sequences of symbols that could follow
                 * the current symbol in the item.  This is the production from after the current symbol to
                 * the end, appended by each symbol in the look-ahead set.
                 */
                final List<Symbol> symbols = item.getProduction().getSymbols();
                
                final int nextSymbolIndex = item.getParserPosition() + 1;
                
                if (nextSymbolIndex < symbols.size()) {
                    final List<Symbol> remainder1 = symbols.subList(nextSymbolIndex, symbols.size());
                    
                    for (final T lookahead : lookaheadSet) {
                        final List<Symbol> remainder = new ArrayList<>(remainder1);
                        remainder.add(lookahead);
                        
                        final Set<T> firstSetOfRemainder = terminalSetFactory.getNewSet();
                        
                        for (final Symbol symbol : remainder) {
                            final Set<T> firstSetForSymbolInProduction = first.get(symbol);
                            
                            firstSetOfRemainder.addAll(firstSetForSymbolInProduction);
                            
                            if (!nullable.contains(symbol)) {
                                break;
                            }
                        }
                        
                        for (final Item newItem : newItems) {
                            final Set<T> lookaheadAdditions = additions.get(newItem);
                            if (lookaheadAdditions == null) {
                                additions.put(newItem, terminalSetFactory.getNewSet(firstSetOfRemainder));
                            } else {
                                lookaheadAdditions.addAll(firstSetOfRemainder);
                            }
                        }
                    }
                } else {
                    /*
                     * The production is completed.  Return the look-aheads.
                     */
                    
                    for (final T lookahead : lookaheadSet) {
                        final Set<T> firstSetOfRemainder = terminalSetFactory.getNewSet();
                        
                        firstSetOfRemainder.add(lookahead);
                        
                        for (final Item newItem : newItems) {
                            final Set<T> lookaheadAdditions = additions.get(newItem);
                            if (lookaheadAdditions == null) {
                                additions.put(newItem, terminalSetFactory.getNewSet(firstSetOfRemainder));
                            } else {
                                lookaheadAdditions.addAll(firstSetOfRemainder);
                            }
                        }
                    }
                }
                
                /*
                 * The new items added need look-ahead sets.  The look-ahead for each item is
                 * the first set of everything that comes after the next symbol in the item.
                 */
            }
            
            changed = false;
            
            for (final Map.Entry<Item, Set<T>> entry : additions.entrySet()) {
                final Item item = entry.getKey();
                final Set<T> lookahead = entry.getValue();
                
                final Set<T> set = itemLookaheadMap.get(item);
                if (set == null) {
                    itemLookaheadMap.put(item, lookahead);
                    
                    changed = true;
                } else {
                    final boolean b = set.addAll(lookahead);
                    
                    changed = b || changed;
                }
            }
        } while (changed);
        
        final Set<LookaheadItem<T>> itemSet = new HashSet<>(itemLookaheadMap.size());
        for (final Map.Entry<Item, Set<T>> entry : itemLookaheadMap.entrySet()) {
            final Item item = entry.getKey();
            final Set<T> lookaheadSet = entry.getValue();
            
            final LookaheadItem<T> lookaheadItem = lookaheadItemFactory.createInstance(item, lookaheadSet);
            itemSet.add(lookaheadItem);
        }
        
        return parserStateFactory.createInstance(itemSet);
    }

    /**
     * Calculates a new parser state by taking an existing state and one symbol, finding all items in the state that
     * would be advanced by that symbol, and for those items creating new items that are advanced by that one symbol.
     * The set of new items that have been advanced are combined into a new parser state.
     * 
     * @param itemSet the existing parser state items.
     * @param symbol the symbol to use to advance items
     * @return a new parser state that is the existing parser state advanced by the symbol
     */
    private ParserState<T> calculateGoto(final Collection<LookaheadItem<T>> itemSet, final Symbol symbol) {
        final Set<LookaheadItem<T>> newItemSet = new HashSet<>(itemSet.size());
        
        /*
         * Go through the look-ahead item set, find the items that would be advanced by the given symbol.
         * Create new items for each of those that are advanced by one parse position.
         */
        // simple enough not to parallelize
        for (final LookaheadItem<T> lookaheadItem : itemSet) {
            final Item item = lookaheadItem.getItem();
            if (item.isComplete()) {
                continue;
            }
            
            final Symbol nextSymbol = item.getNextSymbol();
            if (symbol.equals(nextSymbol)) {
                final Item newItem = itemFactory.createItem(item.getTarget(), item.getProduction(), item.getParserPosition() + 1);
                final LookaheadItem<T> newLookaheadItem = lookaheadItemFactory.createInstance(newItem, lookaheadItem.getLookaheadSet());
                newItemSet.add(newLookaheadItem);
            }
        }
        
        return calculateClosure(newItemSet);
    }
    
    private ParserState<T> computeParseStates(final Set<LookaheadItem<T>> initialItems, final T endOfFileSymbol) {
        final ParserState<T> startState = calculateClosure(initialItems);
        /*
         * Start with just the initial state.
         */
        
        Set<ParserState<T>> pending = Collections.singleton(startState);
        
        while ( !pending.isEmpty()) {
            final Set<ParserState<T>> newParserStates = new HashSet<>();
            final Set<Edge<T>> newEdges = new HashSet<>();
            
            for (final ParserState<T> parserState : pending) {
                final Set<LookaheadItem<T>> stateItems = parserState.getItems();
                
                /*
                 * For each production + parse position + lookahead in the parser state...
                 */
                /*
                 * Find all symbols that could advance any one of the items in the state.
                 */
                final Set<Symbol> relevantSymbols = new HashSet<>();
                for (final LookaheadItem<T> lookaheadItem : stateItems) {
                    final Item item = lookaheadItem.getItem();
                    if (item.isComplete()) {
                        continue;
                    }
                    
                    final Symbol nextSymbol = item.getNextSymbol();
                    
                    relevantSymbols.add(nextSymbol);
                }
                
                relevantSymbols.remove(endOfFileSymbol);
                
                /*
                 * For each symbol that could advance an item...
                 */
                for (final Symbol symbol : relevantSymbols) {
                    /*
                     * Find the closure of all items that would be advanced by the next symbol in the current item.
                     */
                    final ParserState<T> newParserState = calculateGoto(stateItems, symbol);
                    /*
                     * Create an edge from the existing state to the new state.
                     */
                    final Edge<T> newEdge = edgeFactory.createInstance(parserState, symbol, newParserState);
                    
                    newParserStates.add(newParserState);
                    newEdges.add(newEdge);
                }
            }
            
            parserStates.addAll(pending);
            edges.addAll(newEdges);
            
            pending = newParserStates;
            pending.removeAll(parserStates);
        }
        
        return startState;
    }

    private Set<LookaheadItem<T>> createInitialItem(final NonTerminalSymbol startSymbol, final T endOfFileSymbol) {
        final Set<T> endOfFileSet = terminalSetFactory.getNewSet(Collections.singleton(endOfFileSymbol));
        
        addProduction(START, new IdentityProductionHandler(), startSymbol, endOfFileSymbol);

        final Set<LookaheadItem<T>> initialItems = new HashSet<>();
        
        for (final Production production : productions.get(START)) {
            final Item item = itemFactory.createItem(START, production, 0);
            final LookaheadItem<T> lookaheadItem = lookaheadItemFactory.createInstance(item, endOfFileSet);
            initialItems.add(lookaheadItem);
        }
        
        return initialItems;
    }

    /**
     * Creates a parser for the grammar.  This is called after all calls to {@link #addProduction}.
     * 
     * @param startSymbol the initial non-terminal symbol that the parser will attempt to produce
     *         from the input stream of terminal symbols
     * @param endOfFileSymbol the terminal symbol that represents the end of the input
     * @return a parser for the language defined by this grammar
     */
    public Parser<T> createParser(final NonTerminalSymbol startSymbol, final T endOfFileSymbol) {
        final long startTime = System.currentTimeMillis();
        
        final Set<LookaheadItem<T>> initialItems = createInitialItem(startSymbol, endOfFileSymbol);

        compute();
        
        final ParserState<T> startState = computeParseStates(initialItems, endOfFileSymbol);
        
        final Parser<T> parser = new Parser<>(parserStates, edges, startState, endOfFileSymbol);
        
        final long endTime = System.currentTimeMillis();
        
        System.out.print("Parser states creation, Duration: ");
        System.out.println(endTime - startTime);
        
        printStatistics();
        
        return parser;
    }

    /**
     * Creates a parser for the grammar.  This is called after all calls to {@link #addProduction}.
     * 
     * @param startSymbol the initial non-terminal symbol that the parser will attempt to produce
     *         from the input stream of terminal symbols
     * @param endOfFileSymbol the terminal symbol that represents the end of the input
     * @param executorService the thread pool to use
     * @return a parser for the language defined by this grammar
     * @throws ExecutionException foo
     * @throws InterruptedException bar
     */
    public Parser<T> threadedCreateParser(final NonTerminalSymbol startSymbol, final T endOfFileSymbol, final ExecutorService executorService) throws InterruptedException, ExecutionException {
        final long startTime = System.currentTimeMillis();
        
        final Set<LookaheadItem<T>> initialItems = createInitialItem(startSymbol, endOfFileSymbol);

//        threadedCompute(executorService);
        compute();
        
//        final ParserState<T> startState = threadedComputeParseStates(initialItems, endOfFileSymbol, executorService);
        final ParserState<T> startState = computeParseStates(initialItems, endOfFileSymbol);
        
        final Parser<T> parser = new Parser<>(parserStates, edges, startState, endOfFileSymbol);
        
        final long endTime = System.currentTimeMillis();
        
//        System.out.print("Parser states creation, Duration: ");
//        System.out.println(endTime - startTime);
//        
//        printStatistics();
        
        return parser;
    }
    
    private void printFactoryStatistics(final String name, final WeakCanonicalFactory<?> factory) {
        System.out.print(name);
        System.out.print(" calls: ");
        System.out.println(factory.getCallCount());
        System.out.print(name);
        System.out.print(" instances: ");
        System.out.println(factory.getInstanceCount());
    }
    
    public void printStatistics() {
        printFactoryStatistics("Item factory", itemFactory);
        printFactoryStatistics("Look-ahead factory", lookaheadItemFactory);
        printFactoryStatistics("State factory", parserStateFactory);
        printFactoryStatistics("Edge factory", edgeFactory);
    }
    
}
