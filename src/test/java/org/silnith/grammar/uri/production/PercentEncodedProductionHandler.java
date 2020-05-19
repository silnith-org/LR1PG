package org.silnith.grammar.uri.production;

import java.util.List;

import org.silnith.grammar.ProductionHandler;
import org.silnith.grammar.uri.token.Percent;
import org.silnith.grammar.uri.token.UriTerminal;

public class PercentEncodedProductionHandler implements ProductionHandler {

    private static final PercentEncodedProductionHandler percentEncodedProductionHandler = new PercentEncodedProductionHandler();

    public static PercentEncodedProductionHandler getInstance() {
        return percentEncodedProductionHandler;
    }

    @Override
    public Object handleReduction(final List<Object> rightHandSide) {
        if (rightHandSide.size() == 3) {
            final Percent percent = (Percent) rightHandSide.get(0);
            final UriTerminal firstDigit = (UriTerminal) rightHandSide.get(1);
            final UriTerminal secondDigit = (UriTerminal) rightHandSide.get(2);

            return new PercentEncodedProduction(firstDigit.getCharacter(), secondDigit.getCharacter());
        } else {
            throw new IllegalStateException();
        }
    }

}
