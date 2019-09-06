package org.yash.rms.exception;


//public class BusinessException extends Exception {
public class BusinessException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3892116817542890495L;

/**
 * 
 * @param e Exception
 */
	public BusinessException(Exception e)
	{
		super(e.getMessage(),e);
	}
	
/**
 * 
 * @param string
 */
	public BusinessException(String string)
	{
		super(string);
		
	}
/**
 * 
 * @param string
 * @param guimasaage
 */
	public BusinessException(String string,String guimasaage)
    {
        super(guimasaage);
    
    }
}
