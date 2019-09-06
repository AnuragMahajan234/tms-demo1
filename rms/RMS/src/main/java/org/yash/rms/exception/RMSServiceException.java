package org.yash.rms.exception;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import flexjson.JSONSerializer;

@Component
	public class RMSServiceException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	 
    private String errCode;
    private String message;
    
    public RMSServiceException() {}
    
    public RMSServiceException(String errCode, String message) {
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
			org.yash.rms.exception.RMSServiceException rmsException) {
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
