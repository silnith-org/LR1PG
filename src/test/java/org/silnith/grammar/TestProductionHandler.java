package org.silnith.grammar;

import java.util.List;

public class TestProductionHandler implements ProductionHandler {
	
	private final String prefix;

	public TestProductionHandler(final String prefix) {
		super();
		this.prefix = prefix;
	}

	@Override
	public Object handleReduction(final List<DataStackElement> rightHandSide) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(prefix);
		
		String separator = " ::= ";
		
		for (final DataStackElement dataStackElement : rightHandSide) {
			stringBuilder.append(separator);
			stringBuilder.append(dataStackElement);
			
			separator = " ";
		}
		
		return stringBuilder.toString();
	}

}