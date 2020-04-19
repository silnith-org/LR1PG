package org.silnith.grammar.uri.production;

import java.util.List;

import org.silnith.grammar.DataStackElement;
import org.silnith.grammar.ProductionHandler;
import org.silnith.grammar.uri.token.UriTerminal;

public class QueryProductionHandler implements ProductionHandler {

    private static final QueryProductionHandler queryProductionHandler = new QueryProductionHandler();

    public static QueryProductionHandler getInstance() {
        return queryProductionHandler;
    }

    @Override
    public Object handleReduction(final List<DataStackElement> rightHandSide) {
        if (rightHandSide.isEmpty()) {
            return new QueryProduction();
        } else if (rightHandSide.size() == 2) {
            final Object abstractSyntaxTreeElement = rightHandSide.get(0).getAbstractSyntaxTreeElement();
            final char character;
            if (abstractSyntaxTreeElement instanceof PercentEncodedProduction) {
                final PercentEncodedProduction foo = (PercentEncodedProduction) abstractSyntaxTreeElement;
                character = foo.getCharacter();
            } else if (abstractSyntaxTreeElement instanceof UriTerminal) {
                final UriTerminal terminal = (UriTerminal) abstractSyntaxTreeElement;
                character = terminal.getCharacter();
            } else {
                throw new IllegalStateException();
            }
            final QueryProduction query = (QueryProduction) rightHandSide.get(1).getAbstractSyntaxTreeElement();

            query.prependCharacter(character);

            return query;
        } else {
            throw new IllegalStateException();
        }
    }

}
