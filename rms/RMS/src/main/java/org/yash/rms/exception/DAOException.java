package org.yash.rms.exception;

import org.springframework.stereotype.Component;

import flexjson.JSONSerializer;

@Component
	public class DAOException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public final static String _FAIL_TO_INSERT = String.valueOf(1);
    public final static String _UPDATE_FAILED = String.valueOf(2);
    public final static String _SQL_ERROR = String.valueOf(3);
	 
    private String errCode;
    private String message;
    
    public DAOException() {}
    
    public DAOException(String errCode, String message) {
    	super();
        this.errCode = errCode;
        this.setMessage(message);
    }
    public String getErrCode() {
        return errCode;
    }
 
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
 
    
    public static String toJsonArray(
			org.yash.rms.exception.DAOException rmsException) {
		return new JSONSerializer()
				.include("errCode", "message")
				.exclude("*").serialize(rmsException);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
