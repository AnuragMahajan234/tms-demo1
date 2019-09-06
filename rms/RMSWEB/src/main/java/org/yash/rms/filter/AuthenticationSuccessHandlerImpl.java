package org.yash.rms.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.UserUtil;

public class AuthenticationSuccessHandlerImpl extends
SimpleUrlAuthenticationSuccessHandler {
	@Autowired
	ResourceService resourceService;
	@Autowired
	private UserUtil userUtil;
	
	private static String previousLoggedInUser;
	
	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest req,
			HttpServletResponse res, Authentication aut) throws IOException,
			ServletException {
		SecurityContextHolderAwareRequestWrapper sc = new SecurityContextHolderAwareRequestWrapper(req,"");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (aut != null) {
			previousLoggedInUser = null==previousLoggedInUser?authentication.getName():previousLoggedInUser;
			
			
			/*
			 * Maintaining the record of the logged in user. It will check if previous logged in user and current logged in
			 * user is different if this is the case then nullify the resource list, so that current user should see only 
			 * those resource corresponding to their role.
			 */
			if(!previousLoggedInUser.equalsIgnoreCase(authentication.getName())){
				req.getSession().setAttribute("allResources", null);
				previousLoggedInUser=authentication.getName();
			}
			req.getSession().setAttribute("userName",authentication.getName());
			if(req.getSession().getAttribute("allResources") == null){
				if(sc.isUserInRole("ROLE_DEL_MANAGER")){
					List<Object[]> empIdList = new ArrayList<Object[]>();
					String userName=authentication.getName();
					//empIdList .addAll(resourceService.findResourcesByCurrentReportingManager(resourceService.getCurrentResource(userName)));
					//empIdList .addAll(resourceService.findResourcesByCurrentReportingManagerTwo(resourceService.getCurrentResource(userName)));
					empIdList.addAll(resourceService.simlateUserList(null,userUtil.getLoggedInResource(), false));
					req.getSession().setAttribute("allResources", empIdList);				
				}
				
				if(sc.isUserInRole("ROLE_ADMIN")){
						List<Object[]> resources = resourceService.simlateUserList(null,null, true);
						req.getSession().setAttribute("allResources", resources);					
				}
				if (sc.isUserInRole("ROLE_BG_ADMIN") || sc.isUserInRole("ROLE_HR")) {
					List<OrgHierarchy> accessRight = UserUtil.getCurrentResource().getAccessRight();
					List<Object[]> resourceList = null;
					if(null!=accessRight && !accessRight.isEmpty()){
						resourceList = resourceService.simlateUserList(accessRight,null, false);
					}
					req.getSession().setAttribute("allResources", null!=resourceList?resourceList:new ArrayList<Resource>());
				}
				
			}

			if(sc.isUserInRole("ROLE_ADMIN") || sc.isUserInRole("ROLE_MANAGER")||sc.isUserInRole("ROLE_HR"))
				setDefaultTargetUrl("/");
			else if(sc.isUserInRole("ROLE_USER")) {
//				setDefaultTargetUrl(prepareURLForUserRole(req));
				setDefaultTargetUrl("/");
			}else if(sc.isUserInRole("ROLE_HR")) {
				setDefaultTargetUrl("/loanAndTransfer");
			}
		    
			super.onAuthenticationSuccess(req, res, aut);
		 }
	}
	
	private static String prepareURLForUserRole(HttpServletRequest request) {
		StringBuffer str= new StringBuffer("/useractivitys");
		Calendar prevMonthCal = Calendar.getInstance();
		prevMonthCal.add(Calendar.MONTH, 0);
		prevMonthCal.set(Calendar.DATE, 1);
		Calendar curMonthCal = Calendar.getInstance();
		curMonthCal.set(Calendar.DATE,
				curMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH));
		str.append("?minWeekStartDate=")
				.append(prevMonthCal.get(Calendar.MONTH) + 1).append("/")
				.append(prevMonthCal.get(Calendar.DATE)).append("/")
				.append(prevMonthCal.get(Calendar.YEAR));
		str.append("&maxWeekStartDate=")
				.append(curMonthCal.get(Calendar.MONTH) + 1).append("/")
				.append(curMonthCal.get(Calendar.DATE)).append("/")
				.append(curMonthCal.get(Calendar.YEAR));
		str.append("&find=ByWeekStartDateBetweenAndEmployeeIdView");
		return str.toString();		
	}
	

}
