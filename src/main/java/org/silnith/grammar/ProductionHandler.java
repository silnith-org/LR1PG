package org.silnith.grammar;

import java.util.List;

public interface ProductionHandler {

	Object handleReduction(List<Object> rightHandSide);

}
