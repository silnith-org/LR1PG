package org.silnith.grammar;

import java.util.List;

public class TestProductionHandler implements ProductionHandler {
	
	private final String prefix;

	public TestProductionHandler(final String prefix) {
		super();
		this.prefix = prefix;
	}

	@Override
	public Object handleReduction(final List<Object> rightHandSide) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(prefix);
		
		String separator = " ::= ";
		
		for (final Object dataStackElement : rightHandSide) {
			stringBuilder.append(separator);
			stringBuilder.append('[');
			stringBuilder.append(dataStackElement);
            stringBuilder.append(']');
			
			separator = " ";
		}
		
		return stringBuilder.toString();
	}

}