package org.yash.rms.rest.service.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.yash.rms.rest.service.generic.IRequestScopeDataService;


/**
 * The Class RequestScopeDataServiceImpl.
 */
@Service
public class RequestScopeDataServiceImpl implements IRequestScopeDataService {

	/** The exception list. */
	private Map<String,Throwable> exceptionList;
	
	/**
	 * Gets the exception list.
	 *
	 * @return the exception list
	 * @see com.inn.decent.service.IRequestScopeDataService.getExceptionList()
	 */
	//@Override
	public Map<String,Throwable> getExceptionList() {
		return exceptionList;
	}

	/**
	 * Sets the exception list.
	 *
	 * @param exceptionList the exception list
	 * @see com.inn.decent.service.IRequestScopeDataService.setExceptionList(java.util.Map)
	 */
	//@Override
	public void setExceptionList(Map<String,Throwable> exceptionList) {
		this.exceptionList = exceptionList;
	}
	
	/**
	 * Sets the exception in list.
	 *
	 * @param code the code
	 * @param exception the exception
	 * @see com.inn.decent.service.IRequestScopeDataService.setExceptionInList(java.lang.String, java.lang.Throwable)
	 */
	//@Override
	public void setExceptionInList(String code,Throwable exception) {
		if(this.exceptionList==null)
		{
			this.exceptionList=new HashMap<String, Throwable>();
		}
		exceptionList.put(code,exception);
	}


	/**
	 * Instantiates a new request scope data service impl.
	 */
	public RequestScopeDataServiceImpl() {
	}
	
	/**
	 * Initialize list.
	 *
	 * @see com.inn.decent.service.IRequestScopeDataService.initializeList()
	 */
	//@Override
	public void initializeList() {
		
			this.exceptionList=new HashMap<String, Throwable>();
		
	}
	
	
}
