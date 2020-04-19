package org.silnith.grammar.uri.production;

public class PortProduction extends UriAbstractSyntaxTree {

    private final StringBuilder stringBuilder;

    public PortProduction() {
        super();
        this.stringBuilder = new StringBuilder(5);
    }

    public void prependCharacter(final char character) {
        stringBuilder.insert(0, character);
    }

    public int getPort() {
        return Integer.parseInt(stringBuilder.toString());
    }

    @Override
    public String toString() {
        return "Port [" + stringBuilder + "]";
    }

}
