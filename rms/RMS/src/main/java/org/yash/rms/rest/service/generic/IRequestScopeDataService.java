package org.yash.rms.rest.service.generic;

import java.util.List;
import java.util.Map;


/**
 * The Interface IRequestScopeDataService.
 */
public interface IRequestScopeDataService {


	/**
	 * Gets the exception list.
	 *
	 * @return the exception list
	 */
	Map<String, Throwable> getExceptionList();

	/**
	 * Sets the exception list.
	 *
	 * @param exceptionList the exception list
	 */
	void setExceptionList(Map<String, Throwable> exceptionList);

	/**
	 * Sets the exception in list.
	 *
	 * @param code the code
	 * @param exception the exception
	 */
	void setExceptionInList(String code, Throwable exception);
	
	/**
	 * Initialize list.
	 */
	void initializeList();
	

	
}
