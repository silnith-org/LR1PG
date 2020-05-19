package org.silnith.grammar;

import java.util.List;


/**
 * A handler responsible for providing the object that will go on the data stack whenever this production is reduced.
 * Clients of the API implement this interface in order to build up whatever data structure they want to generate from
 * parsing the language.
 */
public interface ProductionHandler {
	
	/**
     * Returns the data object created by this production.  The input elements are the data objects returned by other
     * production handlers, and the terminal symbols of the grammar.  The terminal symbols will be instances of whatever
     * type corresponds to parameter {@code T} of type {@link Grammar}.
     * 
     * @param rightHandSide the data objects for the symbols in the production
     * @return the data object for this production
     */
    Object handleReduction(List<Object> rightHandSide);
    
}
