package org.silnith.grammar;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Parser<T extends TerminalSymbol> {

	private final Map<ItemSet<T>, String> parserStateNames;

	private final Set<Edge<T>> edges;

	private final ItemSet<T> startState;

	private final T endOfFileSymbol;

	private final Table<ItemSet<T>, Symbol, Action<T>> parsingTable;

	private final Deque<Symbol> symbolStack;

	private final Deque<ItemSet<T>> stateStack;

	private final Deque<Object> dataStack;

	public Parser(final Set<ItemSet<T>> parserStates, final Set<Edge<T>> edges,
			final ItemSet<T> startState, final T endOfFileSymbol) {
		super();
		this.parserStateNames = new HashMap<>(parserStates.size());
		int i = 1;
		for (final ItemSet<T> state : parserStates) {
			this.parserStateNames.put(state, "state" + i);
			i++;
		}
		this.edges = edges;
		this.startState = startState;
		this.endOfFileSymbol = endOfFileSymbol;
		this.parsingTable = new Table<>();
		this.symbolStack = new ArrayDeque<>();
		this.stateStack = new ArrayDeque<>();
		this.dataStack = new ArrayDeque<>();
		
		this.calculateParseTable();
	}

	public void calculateParseTable() {
		for (final Edge<T> edge : edges) {
			final ItemSet<T> parserState = edge.getInitialState();
			final Symbol symbol = edge.getSymbol();
			final ItemSet<T> destinationState = edge.getFinalState();
			final Action<T> action;
			if (symbol.getType() == Symbol.Type.TERMINAL) {
				final Shift<T> shiftAction = new Shift<>(parserState, symbol, destinationState);
				action = shiftAction;
			} else if (symbol.getType() == Symbol.Type.NON_TERMINAL) {
				final Goto<T> gotoAction = new Goto<>(parserState, symbol, destinationState);
				action = gotoAction;
			} else {
				throw new IllegalStateException("Symbol is neither terminal nor non-terminal: " + symbol);
			}
			putAction(parserState, symbol, action);
		}
		for (final ItemSet<T> parserState : parserStateNames.keySet()) {
			for (final LookaheadItem<T> item : parserState.getItems()) {
				if (item.isComplete()) {
					for (final Symbol lookahead : item.getLookaheadSet()) {
						putAction(parserState, lookahead, new Reduce<>(parserState, lookahead, item));
					}
				} else {
					final Symbol symbol = item.getNextSymbol();
					if (symbol.equals(endOfFileSymbol)) {
						putAction(parserState, symbol, new Accept<>(parserState, symbol));
					}
				}
			}
		}
//		parsingTable.printTableLong();
	}

	private int conflictCount = 0;

	protected void putAction(final ItemSet<T> parserState, final Symbol symbol, final Action<T> action) {
		final Action<T> previousAction = parsingTable.put(parserState, symbol, action);
		if (previousAction != null) {
			System.out.println("Action conflict #" + conflictCount++);
//			System.out.println(parserState);
			parserState.printLong();
			System.out.println(symbol);
			System.out.println(previousAction);
			System.out.println(action);
		}
	}

	protected String getName(final ItemSet<T> state) {
		return parserStateNames.get(state);
	}

	private Symbol currentSymbol;

	private Symbol lookahead;

	protected Symbol getNextSymbol(final Iterator<T> iterator) {
		if (currentSymbol == null) {
			currentSymbol = iterator.next();
		} else {
			currentSymbol = lookahead;
		}
		if (iterator.hasNext()) {
			lookahead = iterator.next();
		} else {
			lookahead = endOfFileSymbol;
		}
		return currentSymbol;
	}

	protected Symbol getLookahead() {
		return lookahead;
	}

	public Object parse(final Iterator<T> iterator) {
		ItemSet<T> currentState = startState;
		stateStack.push(currentState);
		Symbol nextSymbol = getNextSymbol(iterator);
//		System.out.print("First symbol: ");
//		System.out.println(nextSymbol);
		boolean done = false;
		do {
//			System.out.println(stateStack);
//			System.out.println(symbolStack);
//			System.out.println(getName(currentState));
//			currentState.printLong();
//			System.out.print("next symbol: ");
//			System.out.println(nextSymbol);
//			final Map<Symbol, Action> actionRow = parsingTable.getRow(currentState);
//			for (final Map.Entry<Symbol, Action> entry : actionRow.entrySet()) {
//				final Symbol key = entry.getKey();
//				System.out.print("Symbol: ");
//				System.out.println(key);
//				System.out.print("Action: ");
//				System.out.println(entry.getValue().getClass());
//			}
			final Action<T> action = parsingTable.get(currentState, nextSymbol);
			if (action == null) {
				currentState.printLong();
				System.out.print("Next symbol: ");
				System.out.println(nextSymbol);
				throw new IllegalStateException("No parse action for symbol: " + nextSymbol + " and state: " + getName(currentState));
			}
			switch (action.getType()) {
			case SHIFT: {
				final Shift<T> shiftAction = (Shift<T>) action;
				currentState = shiftAction.getDestinationState();
				symbolStack.push(nextSymbol);
				stateStack.push(currentState);
				dataStack.push(nextSymbol);
//				System.out.print("Shifted terminal: ");
//				System.out.println(nextSymbol);
				nextSymbol = getNextSymbol(iterator);
			} break;
			case REDUCE: {
				final Reduce<T> reduceAction = (Reduce<T>) action;
				final LookaheadItem<T> reduceItem = reduceAction.getReduceItem();
				final NonTerminalSymbol leftHandSide = reduceItem.getLeftHandSide();
				final Production production = reduceItem.getRightHandSide();
				final List<Object> data = new LinkedList<>();
				for (@SuppressWarnings("unused") final Symbol symbol : production.getSymbols()) {
					symbolStack.pop();
					stateStack.pop();
					final Object datum = dataStack.pop();
					data.add(0, datum);
				}
				final ProductionHandler handler = production.getProductionHandler();
				final Object newDatum;
				if (handler == null) {
					newDatum = new Object() {};
				} else {
					newDatum = handler.handleReduction(data);
				}
				currentState = stateStack.peek();
//				System.out.println(getName(currentState));
//				currentState.printLong();
//				for (final Map.Entry<Symbol, Action> entry : parsingTable.getRow(currentState).entrySet()) {
//					final Symbol key = entry.getKey();
//					System.out.print("Symbol: ");
//					System.out.println(key);
//					System.out.print("Action: ");
//					System.out.println(entry.getValue().getClass());
//				}
				final Action<T> tempAction = parsingTable.get(currentState, leftHandSide);
				assert tempAction instanceof Goto;
				final Goto<T> gotoAction = (Goto<T>) tempAction;
				currentState = gotoAction.getDestinationState();
				symbolStack.push(leftHandSide);
				stateStack.push(currentState);
				dataStack.push(newDatum);
//				System.out.print("Reduced by production: ");
//				System.out.println(reduceItem);
			} break;
			case GOTO: {
				final Goto<T> gotoAction = (Goto<T>) action;
				currentState = gotoAction.getDestinationState();
//				System.out.print("Gone to state: ");
//				System.out.println(getName(currentState));
			} break;
			case ACCEPT: {
				done = true;
				System.out.println("Accept.");
//				System.out.println(dataStack);
			} break;
			default: {
				throw new IllegalStateException("Unknown action: " + action);
			} // break;
			}
		} while ( !done);
		return dataStack.getLast();
	}

}
