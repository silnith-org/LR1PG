package org.silnith.grammar.uri.production;

import java.util.List;

import org.silnith.grammar.ProductionHandler;
import org.silnith.grammar.uri.token.UriTerminal;

public class SchemeProductionHandler implements ProductionHandler {

    private static final SchemeProductionHandler schemeProductionHandler = new SchemeProductionHandler();

    public static SchemeProductionHandler getInstance() {
        return schemeProductionHandler;
    }

    @Override
    public Object handleReduction(final List<Object> rightHandSide) {
        if (rightHandSide.size() == 2) {
            final SchemeProduction scheme = (SchemeProduction) rightHandSide.get(0);
            final UriTerminal character = (UriTerminal) rightHandSide.get(1);

            scheme.appendCharacter(character.getCharacter());

            return scheme;
        } else if (rightHandSide.size() == 1) {
            final UriTerminal character = (UriTerminal) rightHandSide.get(0);

            return new SchemeProduction(character.getCharacter());
        } else {
            throw new IllegalStateException();
        }
    }

}
