package org.silnith.grammar.uri.production;

import java.util.List;

import org.silnith.grammar.DataStackElement;
import org.silnith.grammar.ProductionHandler;
import org.silnith.grammar.uri.token.Digit;

public class PortProductionHandler implements ProductionHandler {

    private static final PortProductionHandler portProductionHandler = new PortProductionHandler();

    public static PortProductionHandler getInstance() {
        return portProductionHandler;
    }

    @Override
    public Object handleReduction(final List<DataStackElement> rightHandSide) {
        if (rightHandSide.isEmpty()) {
            return new PortProduction();
        } else if (rightHandSide.size() == 2) {
            final DataStackElement digitElement = rightHandSide.get(0);
            final DataStackElement portProductionElement = rightHandSide.get(1);

            final Object digitAST = digitElement.getAbstractSyntaxTreeElement();
            final Digit digitToken = (Digit) digitAST;
            final char character = digitToken.getCharacter();

            final Object portProductionAST = portProductionElement.getAbstractSyntaxTreeElement();
            final PortProduction portProduction = (PortProduction) portProductionAST;

            portProduction.prependCharacter(character);

            return portProduction;
        } else {
            throw new IllegalStateException();
        }
    }

}
