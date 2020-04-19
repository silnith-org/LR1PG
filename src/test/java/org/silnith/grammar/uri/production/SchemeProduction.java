package org.silnith.grammar.uri.production;

public class SchemeProduction extends UriAbstractSyntaxTree {

    private final StringBuilder stringBuilder;

    public SchemeProduction(final char character) {
        super();
        this.stringBuilder = new StringBuilder();
        this.stringBuilder.append(character);
    }

    public void appendCharacter(final char character) {
        stringBuilder.append(character);
    }

    @Override
    public String toString() {
        return "Scheme [" + stringBuilder + "]";
    }

}
