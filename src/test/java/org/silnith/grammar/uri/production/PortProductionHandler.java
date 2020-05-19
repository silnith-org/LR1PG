package org.silnith.grammar.uri.production;

import java.util.List;

import org.silnith.grammar.ProductionHandler;
import org.silnith.grammar.uri.token.Digit;

public class PortProductionHandler implements ProductionHandler {

    private static final PortProductionHandler portProductionHandler = new PortProductionHandler();

    public static PortProductionHandler getInstance() {
        return portProductionHandler;
    }

    @Override
    public Object handleReduction(final List<Object> rightHandSide) {
        if (rightHandSide.isEmpty()) {
            return new PortProduction();
        } else if (rightHandSide.size() == 2) {
            final Object digitAST = rightHandSide.get(0);
            final Digit digitToken = (Digit) digitAST;
            final char character = digitToken.getCharacter();

            final Object portProductionAST = rightHandSide.get(1);
            final PortProduction portProduction = (PortProduction) portProductionAST;

            portProduction.prependCharacter(character);

            return portProduction;
        } else {
            throw new IllegalStateException();
        }
    }

}
