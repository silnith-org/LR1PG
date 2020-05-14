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
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;


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

    private static final String sourceClass = Grammar.class.getName();

    private final Logger logger;
    
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
        this.logger = Logger.getLogger(sourceClass);
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
        final String sourceMethod = "clear";
        logger.entering(sourceClass, sourceMethod);
        
        lexicon.clear();
        nonTerminalSymbols.clear();
        productions.clear();
        
        nullable.clear();
        first.clear();
        follow.clear();
        
        nullableComputed = false;
        firstComputed = false;
        followComputed = false;
        
        parserStates.clear();
        edges.clear();
        
        logger.exiting(sourceClass, sourceMethod);
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
    
    private boolean nullableComputed = false;
    
    /**
     * Compute the nullable set.
     */
    private void computeNullable() {
        final String sourceMethod = "computeNullable";
        logger.entering(sourceClass, sourceMethod);
        
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
        
        logger.logp(Level.FINE, sourceClass, sourceMethod, "nullable set: {0}", nullable);
        
        nullableComputed = true;
        
        logger.exiting(sourceClass, sourceMethod);
    }
    
    private boolean firstComputed = false;
    
    /**
     * Compute the first set for each symbol.
     */
    private void computeFirst() {
        final String sourceMethod = "computeFirst";
        logger.entering(sourceClass, sourceMethod);
        
        first.clear();
        for (final T terminalSymbol : lexicon) {
            final Set<T> newSet = terminalSetFactory.getNewSet();
            newSet.add(terminalSymbol);
            first.put(terminalSymbol, newSet);
        }
        for (final NonTerminalSymbol nonTerminalSymbol : nonTerminalSymbols) {
            first.put(nonTerminalSymbol, terminalSetFactory.getNewSet());
        }
        
        boolean changed;
        do {
            changed = false;
            for (final Map.Entry<NonTerminalSymbol, Set<Production>> entry : productions.entrySet()) {
                final NonTerminalSymbol nonTerminal = entry.getKey();
                final Set<Production> productions = entry.getValue();
                
                final Set<T> firstSet = first.get(nonTerminal);
                for (final Production production : productions) {
                    for (final Symbol symbol : production.getSymbols()) {
                        final boolean addedElements = firstSet.addAll(first.get(symbol));
                        changed = addedElements || changed;
                        
                        assert nullableComputed;
                        if (!nullable.contains(symbol)) {
                            /*
                             * Break inner loop only, continue outer loop.
                             */
                            break;
                        }
                    }
                }
            }
        } while (changed);
        
        logger.logp(Level.FINE, sourceClass, sourceMethod, "first sets: {0}", first);
        
        firstComputed = true;
        
        logger.exiting(sourceClass, sourceMethod);
    }
    
    private boolean followComputed = false;
    
    /**
     * Compute the follow set for each symbol.
     */
    private void computeFollow() {
        final String sourceMethod = "computeFollow";
        logger.entering(sourceClass, sourceMethod);
        
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
                final NonTerminalSymbol nonTerminal = entry.getKey();
                final Set<Production> productions = entry.getValue();
                
                final Set<T> nonTerminalFollowSet = follow.get(nonTerminal);
                
                for (final Production production : productions) {
                    final List<Symbol> productionSymbols = production.getSymbols();
                    final ListIterator<Symbol> revIter = productionSymbols.listIterator(productionSymbols.size());
                    while (revIter.hasPrevious()) {
                        final Symbol symbol = revIter.previous();
                        
                        final boolean b = follow.get(symbol).addAll(nonTerminalFollowSet);
                        changed = b || changed;
                        assert nullableComputed;
                        if (!nullable.contains(symbol)) {
                            break;
                        }
                    }
                    
                    final ListIterator<Symbol> rangeStartIter = productionSymbols.listIterator();
                    while (rangeStartIter.hasNext()) {
                        final int startIndex = rangeStartIter.nextIndex();
                        final Symbol startSymbol = rangeStartIter.next();
                        
                        final Set<T> followSet = follow.get(startSymbol);
                        final ListIterator<Symbol> rangeEndIter = productionSymbols.listIterator(startIndex + 1);
                        while (rangeEndIter.hasNext()) {
                            final Symbol endSymbol = rangeEndIter.next();

                            assert firstComputed;
                            final boolean b = followSet.addAll(first.get(endSymbol));
                            changed = b || changed;
                            assert nullableComputed;
                            if (!nullable.contains(endSymbol)) {
                                break;
                            }
                        }
                    }
                }
            }
        } while (changed);
        
        logger.logp(Level.FINE, sourceClass, sourceMethod, "follow sets: {0}", follow);
        
        followComputed = true;
        
        logger.exiting(sourceClass, sourceMethod);
    }
    
    protected void compute() {
        final String sourceMethod = "compute";
        logger.entering(sourceClass, sourceMethod);
        
        computeNullable();
        computeFirst();
        computeFollow();
        
        logger.exiting(sourceClass, sourceMethod);
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
        final String sourceMethod = "calculateGoto";
        logger.entering(sourceClass, sourceMethod, new Object[] {itemSet, symbol});
        
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
        
        logger.logp(Level.FINEST, sourceClass, sourceMethod, "goto item set size: {0}", newItemSet.size());
        
        final ParserState<T> closure = calculateClosure(newItemSet);
        
        logger.exiting(sourceClass, sourceMethod, closure);
        return closure;
    }
    
    private ParserState<T> calculateClosure(final Collection<LookaheadItem<T>> items) {
        final String sourceMethod = "calculateClosure";
        logger.entering(sourceClass, sourceMethod, items);
        
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
         * These look-ahead sets will be for the new state and are modified in this method.
         * Therefore, allocate new sets initialized to the existing sets.
         */
        for (final LookaheadItem<T> lookaheadItem : items) {
            final Item item = lookaheadItem.getItem();
            final Set<T> lookaheadSet = lookaheadItem.getLookaheadSet();
            
            itemLookaheadMap.put(item, terminalSetFactory.getNewSet(lookaheadSet));
        }
    
        final Map<Item, Set<T>> additions = new HashMap<Item, Set<T>>();
        
        boolean changed;
        do {
            additions.clear();
            
            logger.logp(Level.FINEST, sourceClass, sourceMethod, "items in closure: {0}", itemLookaheadMap.size());
            
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
                /*
                 * Given an item and a look-ahead set, get all possible sequences of symbols that could follow
                 * the current symbol in the item.  This is the production from after the current symbol to
                 * the end, appended by each symbol in the look-ahead set.
                 */
                final List<Symbol> symbols = item.getProduction().getSymbols();
                
                final int nextSymbolIndex = item.getParserPosition() + 1;
                
                assert nextSymbolIndex <= symbols.size();
                
                final Set<T> firstSetOfRemainder = terminalSetFactory.getNewSet();
                
                boolean remainderIsNullable = true;
                /*
                 * The subList may be an empty list.
                 */
                for (final Symbol symbol : symbols.subList(nextSymbolIndex, symbols.size())) {
                    
                    assert firstComputed;
                    firstSetOfRemainder.addAll(first.get(symbol));
                    
                    assert nullableComputed;
                    if (!nullable.contains(symbol)) {
                        remainderIsNullable = false;
                        break;
                    }
                }
    
                /*
                 * Add all the productions for the non-terminal to the parser state.
                 */
                for (final Production production : productions.get(nextNonTerminalSymbol)) {
                    final Item newItem = itemFactory.createItem(nextNonTerminalSymbol, production, 0);
                    
                    /*
                     * The new items added need look-ahead sets.  The look-ahead for each item is
                     * the first set of everything that comes after the next symbol in the item.
                     */
                    if (!additions.containsKey(newItem)) {
                        additions.put(newItem, terminalSetFactory.getNewSet());
                    }
                    final Set<T> newItemLookahead = additions.get(newItem);
                    
                    newItemLookahead.addAll(firstSetOfRemainder);
                    if (remainderIsNullable) {
                        newItemLookahead.addAll(lookaheadSet);
                    }
                }
            }
            
            logger.logp(Level.FINEST, sourceClass, sourceMethod, "items to add to closure: {0}", additions.size());
            
            changed = false;
            
            for (final Map.Entry<Item, Set<T>> entry : additions.entrySet()) {
                final Item item = entry.getKey();
                final Set<T> lookahead = entry.getValue();
                
                if (!itemLookaheadMap.containsKey(item)) {
                    itemLookaheadMap.put(item, lookahead);
                    
                    changed = true;
                } else {
                    final boolean b = itemLookaheadMap.get(item).addAll(lookahead);
                    
                    changed = b || changed;
                }
            }
        } while (changed);
        
        /*
         * The closure is complete.  Convert it to a proper parser state.
         */
        
        final Set<LookaheadItem<T>> itemSet = new HashSet<>(itemLookaheadMap.size());
        for (final Map.Entry<Item, Set<T>> entry : itemLookaheadMap.entrySet()) {
            final Item item = entry.getKey();
            final Set<T> lookaheadSet = entry.getValue();
            
            final LookaheadItem<T> lookaheadItem = lookaheadItemFactory.createInstance(item, lookaheadSet);
            itemSet.add(lookaheadItem);
        }
        
        final ParserState<T> parserState = parserStateFactory.createInstance(itemSet);
        
        logger.exiting(sourceClass, sourceMethod, parserState);
        return parserState;
    }
    
    private Set<Edge<T>> computeOutgoingEdges(final ParserState<T> parserState, final T endOfFileSymbol) {
        final String sourceMethod = "computeOutgoingEdges";
        logger.entering(sourceClass, sourceMethod, new Object[] {parserState, endOfFileSymbol});
        
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
        
        logger.logp(Level.FINEST, sourceClass, sourceMethod, "computing new states for {0} relevant symbols", relevantSymbols.size());
        /*
         * For each symbol that could advance an item...
         */
        final Set<Edge<T>> newEdges = new HashSet<>(relevantSymbols.size());
        for (final Symbol symbol : relevantSymbols) {
            /*
             * Find the closure of all items that would be advanced by the next symbol in the current item.
             */
            final ParserState<T> newParserState = calculateGoto(stateItems, symbol);
            /*
             * Create an edge from the existing state to the new state.
             */
            final Edge<T> newEdge = edgeFactory.createInstance(parserState, symbol, newParserState);
            
            newEdges.add(newEdge);
        }
    
        logger.exiting(sourceClass, sourceMethod, newEdges);
        return newEdges;
    }
    
    private class NewEdgeComputer implements Callable<Set<Edge<T>>> {
        
        private final ParserState<T> parserState;
        
        private final T endOfFileSymbol;
    
        public NewEdgeComputer(final ParserState<T> parserState, final T endOfFileSymbol) {
            super();
            this.parserState = parserState;
            this.endOfFileSymbol = endOfFileSymbol;
        }
    
        @Override
        public Set<Edge<T>> call() {
            return computeOutgoingEdges(parserState, endOfFileSymbol);
        }
        
    }
    
    private void computeParseStates(final ParserState<T> startState, final T endOfFileSymbol) {
        final String sourceMethod = "computeParseStates";
        logger.entering(sourceClass, sourceMethod, new Object[] {startState, endOfFileSymbol});
        
        Set<ParserState<T>> pending = Collections.singleton(startState);
        
        while ( !pending.isEmpty()) {
            logger.logp(Level.FINE, sourceClass, sourceMethod, "parser states to compute: {0}", pending.size());

            final Set<Edge<T>> newEdges = new HashSet<>(pending.size());
            for (final ParserState<T> parserState : pending) {
                final Set<Edge<T>> newEdgesForState = new NewEdgeComputer(parserState, endOfFileSymbol).call();
                newEdges.addAll(newEdgesForState);
            }
            
            parserStates.addAll(pending);
            edges.addAll(newEdges);

            final Set<ParserState<T>> newParserStates = new HashSet<>(newEdges.size());
            for (final Edge<T> edge : newEdges) {
                newParserStates.add(edge.getFinalState());
            }
            
            logger.logp(Level.FINE, sourceClass, sourceMethod, "total parser states: {0}, total edges: {1}", new Object[] {parserStates.size(), edges.size()});
            
            pending = newParserStates;
            pending.removeAll(parserStates);
        }
        
        logger.exiting(sourceClass, sourceMethod);
    }
    
    private void threadedComputeParseStates(final ParserState<T> startState, final T endOfFileSymbol, final ExecutorService executorService) throws InterruptedException, ExecutionException {
        final String sourceMethod = "threadedComputeParseStates";
        logger.entering(sourceClass, sourceMethod, new Object[] {startState, endOfFileSymbol});
        
        Set<ParserState<T>> pending = Collections.singleton(startState);
        
        while ( !pending.isEmpty()) {
            logger.logp(Level.FINE, sourceClass, sourceMethod, "parser states to compute: {0}", pending.size());

            final List<NewEdgeComputer> tasks = new ArrayList<>(pending.size());
            for (final ParserState<T> parserState : pending) {
                final NewEdgeComputer task = new NewEdgeComputer(parserState, endOfFileSymbol);
                tasks.add(task);
            }
            
            final List<Future<Set<Edge<T>>>> futures = executorService.invokeAll(tasks);
            
            final Set<Edge<T>> newEdges = new HashSet<>(futures.size());
            for (final Future<Set<Edge<T>>> future : futures) {
                final Set<Edge<T>> newEdgesForState = future.get();
                newEdges.addAll(newEdgesForState);
            }
            
            parserStates.addAll(pending);
            edges.addAll(newEdges);

            final Set<ParserState<T>> newParserStates = new HashSet<>(newEdges.size());
            for (final Edge<T> edge : newEdges) {
                newParserStates.add(edge.getFinalState());
            }
            
            logger.logp(Level.FINE, sourceClass, sourceMethod, "total parser states: {0}, total edges: {1}", new Object[] {parserStates.size(), edges.size()});
            
            pending = newParserStates;
            pending.removeAll(parserStates);
        }
        
        logger.exiting(sourceClass, sourceMethod);
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
        final String sourceMethod = "createParser";
        logger.entering(sourceClass, sourceMethod, new Object[] {startSymbol, endOfFileSymbol});
        
        final long startTime = System.currentTimeMillis();
        final Set<T> endOfFileSet = terminalSetFactory.getNewSet(Collections.singleton(endOfFileSymbol));
        
        addSymbols(startSymbol, endOfFileSymbol);
        
        compute();
        
        final Production production = new Production(new IdentityProductionHandler(), startSymbol, endOfFileSymbol);
        final Item item = itemFactory.createItem(START, production, 0);
        final LookaheadItem<T> lookaheadItem = lookaheadItemFactory.createInstance(item, endOfFileSet);
        final Set<LookaheadItem<T>> initialItems = Collections.singleton(lookaheadItem);
        final ParserState<T> startState = calculateClosure(initialItems);
        /*
         * Start with just the initial state.
         */
        
        computeParseStates(startState, endOfFileSymbol);
        
        final Parser<T> parser = new Parser<>(parserStates, edges, startState, endOfFileSymbol);
        
        final long endTime = System.currentTimeMillis();
        
        logger.logp(Level.FINE, sourceClass, sourceMethod, "time to create parser: {0} ms", endTime - startTime);
        
        logStatistics();
        
        logger.exiting(sourceClass, sourceMethod, parser);
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
    public Parser<T> threadedCreateParser(final NonTerminalSymbol startSymbol, final T endOfFileSymbol, final ExecutorService executorService) {
        final String sourceMethod = "threadedCreateParser";
        logger.entering(sourceClass, sourceMethod, new Object[] {startSymbol, endOfFileSymbol});
        
        final long startTime = System.currentTimeMillis();
        final Set<T> endOfFileSet = terminalSetFactory.getNewSet(Collections.singleton(endOfFileSymbol));
        
        addSymbols(startSymbol, endOfFileSymbol);
        
        compute();
        
        final Production production = new Production(new IdentityProductionHandler(), startSymbol, endOfFileSymbol);
        final Item item = itemFactory.createItem(START, production, 0);
        final LookaheadItem<T> lookaheadItem = lookaheadItemFactory.createInstance(item, endOfFileSet);
        final Set<LookaheadItem<T>> initialItems = Collections.singleton(lookaheadItem);
        final ParserState<T> startState = calculateClosure(initialItems);
        /*
         * Start with just the initial state.
         */
        
        computeParseStates(startState, endOfFileSymbol);
        
        final Parser<T> parser = new Parser<>(parserStates, edges, startState, endOfFileSymbol);
        
        final long endTime = System.currentTimeMillis();
        
        logger.logp(Level.FINE, sourceClass, sourceMethod, "time to create parser: {0} ms", endTime - startTime);
        
        logStatistics();
        
        logger.exiting(sourceClass, sourceMethod, parser);
        return parser;
    }

    private void logFactoryStatistics(final String name, final WeakCanonicalFactory<?> factory) {
        final String sourceMethod = "logFactoryStatistics";
        logger.logp(Level.FINE, sourceClass, sourceMethod, "{0} invocations: {1}, total instances: {2}", new Object[] {name, factory.getCallCount(), factory.getInstanceCount()});
    }
    
    public void logStatistics() {
        logFactoryStatistics("Item factory", itemFactory);
        logFactoryStatistics("Look-ahead factory", lookaheadItemFactory);
        logFactoryStatistics("State factory", parserStateFactory);
        logFactoryStatistics("Edge factory", edgeFactory);
    }
    
}
