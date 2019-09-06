package org.yash.rms.exception;

/**
 * The Class DaoException.
 * @version 1.0
 *
 */
public class DaoRestException extends Exception {
//public class DaoRestException extends RuntimeException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3892116817542890495L;
	
	/** The err code. */
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
	 * Instantiates a new dao exception.
	 *
	 * @param e the e
	 */
	public DaoRestException(Exception e)
	{
		super(e.getMessage(),e);
	}
	
/**
 * Instantiates a new dao exception.
 * @param string
 * @param guimasaage
* @param errCode the err code
 */
	public DaoRestException(String errCode)
    {
      
        this.errCode = errCode;
    
    }
}
