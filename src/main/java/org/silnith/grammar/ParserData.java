package org.silnith.grammar;

import java.util.Set;

import org.silnith.grammar.util.LinkedNode;

class ParserData<T extends TerminalSymbol> {

    private ParserState<T> state;
    
    private LinkedNode<ParserState<T>> stateStack;
    
    private LinkedNode<DataStackElement> dataStack;
    
    private boolean done;
    
    boolean isDone() {
        return done;
    }
    
    void setDone() {
        done = true;
    }

    Action getAction(final Symbol symbol) {
        return state.getAction(symbol);
    }
    
    Set<Action> getActions(final Symbol symbol) {
        return state.getActions(symbol);
    }

    void pushData(final Object datum) {
        dataStack = new LinkedNode<>(new DataStackElement(datum), dataStack);
    }

    Object popData() {
        final DataStackElement datum = dataStack.getFirst();
        dataStack = dataStack.getNext();
        final Object abstractSyntaxTreeElement = datum.getAbstractSyntaxTreeElement();
        return abstractSyntaxTreeElement;
    }

    void setState(final ParserState<T> destinationState) {
        state = destinationState;
    }

    ParserState<T> peekState() {
        return stateStack.getFirst();
    }

    void pushState() {
        stateStack = new LinkedNode<ParserState<T>>(state, stateStack);
    }

    void popState() {
        stateStack = stateStack.getNext();
    }
    
}