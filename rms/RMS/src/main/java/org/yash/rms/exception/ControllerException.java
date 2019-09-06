package org.yash.rms.exception;

import org.springframework.stereotype.Component;

import flexjson.JSONSerializer;

@Component
public class ControllerException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	 
    private String errCode;
    private String errMsg;
    
    public ControllerException() {}
    
    public ControllerException(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
    public String getErrCode() {
        return errCode;
    }
 
   	public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
 
    public String getErrMsg() {
        return errMsg;
    }
 
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    
    public static String toJsonArray(
			org.yash.rms.exception.ControllerException rmsException) {
		return new JSONSerializer()
				.include("errCode", "errMsg")
				.exclude("*").serialize(rmsException);
	}
}
