package org.silnith.grammar.uri;

import org.silnith.grammar.TerminalSymbolMatch;

public class Percent extends UriTerminal {

    private static final Percent instance = new Percent();

    public static Percent getInstance() {
        return instance;
    }

    private Percent() {
        super('%');
    }

    @Override
    public TerminalSymbolMatch getMatch() {
        return UriTerminalType.Percent;
    }

}