package org.silnith.grammar.uri.production;

import java.util.List;

import org.silnith.grammar.ProductionHandler;
import org.silnith.grammar.uri.token.UriTerminal;

public class SegmentProductionHandler implements ProductionHandler {

    private static final SegmentProductionHandler segmentProductionHandler = new SegmentProductionHandler();

    public static final SegmentProductionHandler getInstance() {
        return segmentProductionHandler;
    }

    @Override
    public Object handleReduction(final List<Object> rightHandSide) {
        if (rightHandSide.isEmpty()) {
            return new SegmentProduction();
        } else {
            final Object abstractSyntaxTreeElement = rightHandSide.get(0);
            final char character;
            if (abstractSyntaxTreeElement instanceof PercentEncodedProduction) {
                final PercentEncodedProduction percentEncodedProduction = (PercentEncodedProduction) abstractSyntaxTreeElement;
                character = percentEncodedProduction.getCharacter();
            } else if (abstractSyntaxTreeElement instanceof SegmentProduction) {
                return abstractSyntaxTreeElement;
            } else {
                final UriTerminal terminal = (UriTerminal) abstractSyntaxTreeElement;
                character = terminal.getCharacter();
            }

            final SegmentProduction segmentProduction;
            if (rightHandSide.size() == 1) {
                segmentProduction = new SegmentProduction();
            } else if (rightHandSide.size() == 2) {
                segmentProduction = (SegmentProduction) rightHandSide.get(1);
            } else {
                throw new IllegalStateException();
            }
            segmentProduction.prependCharacter(character);
            return segmentProduction;
        }
    }

}
