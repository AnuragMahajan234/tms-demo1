package org.yash.rms.exception;

/**
 * The Class RestException.
 * @version 1.0
 *
 */
public class RestException extends Exception {
//public class RestException extends RuntimeException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3892116817542890495L;

/**
 * The err code.
 * @param e Exception
 */
	private String errCode;

	/**
	 * Gets the err code.
	 *
	 * @return the err code
	 */
	public String getErrCode() {
		return errCode;
	}

	/**
	 * Sets the err code.
	 *
	 * @param errCode the new err code
	 */
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	/**
	 * Instantiates a new rest exception.
	 *
	 * @param e the e
	 */
	public RestException(Exception e)
	{
		super(e.getMessage(),e);
	}
	
/**
 * Instantiates a new rest exception.
 *
 * @param errCode the err code
 */
	
/**
 * 
 * @param string
 * @param guimasaage
 */
	public RestException(String errCode)
    {
        this.errCode = errCode;
    
    }
}
