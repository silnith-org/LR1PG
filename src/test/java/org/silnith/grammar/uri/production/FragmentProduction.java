package org.silnith.grammar.uri.production;

public class FragmentProduction extends UriAbstractSyntaxTree {

    private final StringBuilder stringBuilder;

    public FragmentProduction() {
        super();
        this.stringBuilder = new StringBuilder();
    }

    public void prependCharacter(final char character) {
        stringBuilder.insert(0, character);
    }

    @Override
    public String toString() {
        return "Fragment [" + stringBuilder + "]";
    }

}
