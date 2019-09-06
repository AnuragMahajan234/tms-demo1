package org.yash.rms.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface AdminActiveService{

	String doApproveOrDenyProfile(Integer adminId,String resourceId,String approve,String code,HttpSession session,HttpServletRequest httpServletRequest);
}
