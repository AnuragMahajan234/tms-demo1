package org.yash.rms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.yash.rms.service.AdminActiveService;

@Controller
@RequestMapping(value = "/adminActivity")
public class AdminActivityController {
	
	@Autowired @Qualifier("adminActiveService")
	private AdminActiveService adminActiveService;
	
	private static final Logger logger = LoggerFactory.getLogger(AdminActivityController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String doApproveOrDenyProfile (@RequestParam(value = "adminId",required = false ) Integer adminId , @RequestParam(value = "resourceId",required = false ) String resourceId, @RequestParam(value = "approve" ) String approve,@RequestParam(value = "code") String code,HttpServletRequest httpServletRequest,HttpSession session) throws Exception{
		logger.info("------AdminActivityController doApproveOrDenyProfile method start------");
		String success = "";
		try {
			success = adminActiveService.doApproveOrDenyProfile(adminId, resourceId, approve, code, session, httpServletRequest);
		}catch(RuntimeException e){
			logger.error("RuntimeException occured in doApproveOrDenyProfile method of AdminActivityController :"+e);
			throw e;
		} catch (Exception e) {
			logger.error("Exception occured in doApproveOrDenyProfile method of AdminActivityController :"+e);
			throw e;
		}
		logger.info("------AdminActivityController doApproveOrDenyProfile method end------");
		return success;
	}
}
