package org.silnith.grammar.uri.production;

import java.util.List;

import org.silnith.grammar.DataStackElement;
import org.silnith.grammar.ProductionHandler;
import org.silnith.grammar.uri.token.UriTerminal;

// TODO: consolidate with query
public class FragmentProductionHandler implements ProductionHandler {

    private static final FragmentProductionHandler fragmentProductionHandler = new FragmentProductionHandler();

    public static FragmentProductionHandler getInstance() {
        return fragmentProductionHandler;
    }

    @Override
    public Object handleReduction(final List<DataStackElement> rightHandSide) {
        if (rightHandSide.isEmpty()) {
            return new FragmentProduction();
        } else if (rightHandSide.size() == 2) {
            final Object abstractSyntaxTreeElement = rightHandSide.get(0).getAbstractSyntaxTreeElement();
            final char character;
            if (abstractSyntaxTreeElement instanceof PercentEncodedProduction) {
                final PercentEncodedProduction percentEncodedProduction = (PercentEncodedProduction) abstractSyntaxTreeElement;
                character = percentEncodedProduction.getCharacter();
            } else if (abstractSyntaxTreeElement instanceof UriTerminal) {
                final UriTerminal terminal = (UriTerminal) abstractSyntaxTreeElement;
                character = terminal.getCharacter();
            } else {
                throw new IllegalStateException();
            }
            final FragmentProduction fragment = (FragmentProduction) rightHandSide.get(1).getAbstractSyntaxTreeElement();

            fragment.prependCharacter(character);

            return fragment;
        } else {
            throw new IllegalStateException();
        }
    }

}
