package org.silnith.grammar.uri.production;

public class SegmentProduction extends UriAbstractSyntaxTree {

    private final StringBuilder stringBuilder;

    public SegmentProduction() {
        super();
        this.stringBuilder = new StringBuilder();
    }

    public void prependCharacter(final char character) {
        stringBuilder.insert(0, character);
    }

    public String getSegment() {
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "Segment [" + stringBuilder + "]";
    }

}
