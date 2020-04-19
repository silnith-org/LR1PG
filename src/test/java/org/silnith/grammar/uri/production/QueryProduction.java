package org.silnith.grammar.uri.production;

public class QueryProduction extends UriAbstractSyntaxTree {

    private final StringBuilder stringBuilder;

    public QueryProduction() {
        super();
        this.stringBuilder = new StringBuilder();
    }

    public void prependCharacter(final char character) {
        stringBuilder.insert(0, character);
    }

    @Override
    public String toString() {
        return "Query [" + stringBuilder + "]";
    }

}
