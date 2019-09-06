package org.yash.rms.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yash.rms.domain.Resource;
import org.yash.rms.exception.RestException;
import org.yash.rms.security.CustomAuthenticationToken;
import org.yash.rms.security.RMSHttpRequestWrapper;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.UserUtil;

import com.google.gson.JsonObject;

@Path("/UnAuthRestImpl")
@Service("UnAuthRestImpl")
public class UnAuthRestImpl {
	
	@Autowired
    private UserUtil userUtil;
	
	@Autowired
	public ResourceService resourceService;

	@POST
	@Path("/auth")
	@Produces("application/json")
	public Resource XamarinAuthenticationRequest(@FormParam("username") String username,
			@FormParam("password") String checksum) throws RestException
	{
	 try {
		   System.out.println("username is"+username+" passwd is "+checksum);
		   CustomAuthenticationToken authentication = new CustomAuthenticationToken(username,checksum, userUtil,true);
		  
		   SecurityContext sc =SecurityContextHolder.getContext();
		   sc.setAuthentication(authentication);
           
         
       	if (authentication != null) {
			/*
			 * Maintaining the record of the logged in user. It will check if previous logged in user and current logged in
			 * user is different if this is the case then nullify the resource list, so that current user should see only 
			 * those resource corresponding to their role.
			 */
       		
        	
       		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
    				.currentRequestAttributes();

    		if (attr != null) {
    		    HttpServletRequest request=attr.getRequest();
    		    HttpSession session = request.getSession(true);
    		    session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
    		    session.setAttribute("isAuthenticated", true);
    		    session.setAttribute("userName",authentication.getName());
    	           request = new RMSHttpRequestWrapper(request);
    	           SecurityContextHolderAwareRequestWrapper sc1 = new SecurityContextHolderAwareRequestWrapper(request,"");
    	           return resourceService.find(authentication.getEmployeeId());
    		}
    		
       	}
		return new Resource();
	 }catch(Exception ex)
	 {
		 return new Resource();
	 }
	}
}
