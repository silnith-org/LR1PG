package org.silnith.grammar.uri.production;

public class PercentEncodedProduction extends UriAbstractSyntaxTree {

    private final char firstCharacter;

    private final char secondCharacter;

    public PercentEncodedProduction(final char f, final char s) {
        super();
        this.firstCharacter = f;
        this.secondCharacter = s;
    }

    public char getCharacter() {
        final String hexString = new StringBuilder()
                .append(firstCharacter)
                .append(secondCharacter)
                .toString();
        return (char) Integer.parseInt(hexString, 16);
    }

    @Override
    public String toString() {
        return "%" + firstCharacter + secondCharacter + "=" + getCharacter();
    }

}
