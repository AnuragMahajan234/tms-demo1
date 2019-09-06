package org.yash.rms.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.UserUtil;

import waffle.servlet.WindowsPrincipal;
import waffle.spring.NegotiateSecurityFilter;
import waffle.util.AuthorizationHeader;
import waffle.windows.auth.IWindowsAuthProvider;
import waffle.windows.auth.IWindowsIdentity;

public class CustomAuthenticationFilter extends NegotiateSecurityFilter {
 
    private static final String IS_AUTHENTICATED = "isAuthenticated";

	Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);
 
    @Autowired
    private UserUtil userUtil;
 
    @Autowired
    private ResourceService resourceService;
    
    private IWindowsAuthProvider _auth = null;
    
   
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
 
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
 
        logger.info("<>>>>>>>>>>>UserName: "+request.getParameter("j_username")+", Password: "+request.getParameter("j_password"));
        logger.info(request.getMethod() + " " + request.getRequestURI() + ", contentlength: " + request.getContentLength());
             
        AuthorizationHeader authorizationHeader = new AuthorizationHeader(request);

        // authenticate user
        if (!authorizationHeader.isNull()
                && getProvider().isSecurityPackageSupported(authorizationHeader.getSecurityPackage())) {

            IWindowsIdentity windowsIdentity = null;
            boolean userAuthenticated = false;
 
            try {
                windowsIdentity = getProvider().doFilter(request, response);
                if (windowsIdentity == null) {
                    return;
                }
            } catch (Exception e) {
            	if(userUtil.authenticateUser((String)request.getAttribute("un"),(String)request.getAttribute("pa"))){
            		userAuthenticated = true;
            	}else{
            		response.sendRedirect(request.getContextPath()+"/index");
            		return;
            		
            	}
            }
  
            try {
            	WindowsPrincipal principal = null;
                if(null!=windowsIdentity){
                	logger.debug("logged in user: " + windowsIdentity.getFqn() + " (" + windowsIdentity.getSidString() + ")");
                	principal = new WindowsPrincipal(windowsIdentity, getPrincipalFormat(), getRoleFormat());
                	logger.debug("roles: " + principal.getRolesString());
                }
 
                /// NOTE: This is where you customize auth. You'd need your own token and initialization mechanism different from 
                /// Waffle's built in token implementation
                // Populate Authentication Token along with GrantedAuthorities
                CustomAuthenticationToken authentication = new CustomAuthenticationToken(principal,(String)request.getAttribute("un"),(String)request.getAttribute("pa"), userUtil,userAuthenticated);
                request.getSession().setAttribute(IS_AUTHENTICATED, true);
                request = new RMSHttpRequestWrapper(request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                SecurityContextHolderAwareRequestWrapper sc = new SecurityContextHolderAwareRequestWrapper(request,"");
                
        		if (authentication != null) {
        			/*
        			 * Maintaining the record of the logged in user. It will check if previous logged in user and current logged in
        			 * user is different if this is the case then nullify the resource list, so that current user should see only 
        			 * those resource corresponding to their role.
        			 */
        			request.getSession().setAttribute("userName",authentication.getName());
        			
        			System.out.println(authentication.getUserName() + "  "+authentication.getUserRole() + " "+authentication.getAuthorities());
        			
        			if(request.getSession().getAttribute("allResources") == null){
        				
        				if(sc.isUserInRole("ROLE_DEL_MANAGER")){
        					List<Resource> empIdList = new ArrayList<Resource>();
        					empIdList.addAll(resourceService.findResourceByRM1RM2(userUtil.getLoggedInResource(),false));
        					request.getSession().setAttribute("allResources", empIdList);				
        				}
        				
        				if(sc.isUserInRole("ROLE_ADMIN")){
        						List<org.yash.rms.domain.Resource> resources = resourceService.findAll();
        						request.getSession().setAttribute("allResources", resources);					
        				}
        				
        				if (request.isUserInRole("ROLE_BG_ADMIN")) {
        					List<OrgHierarchy> accessRight = UserUtil.getCurrentResource().getAccessRight();
        					List<Resource> resourceList = null;
        					if(null!=accessRight && !accessRight.isEmpty()){
        						resourceList = resourceService.findResourcesByBusinessGroup(accessRight,false,false);
        					}
        					request.getSession().setAttribute("allResources", null!=resourceList?resourceList:new ArrayList<Resource>());
        				}
        			}

        			String redirectURL = null;	
        			if(sc.isUserInRole("ROLE_USER")) {
//        				redirectURL = prepareURLForUserRole(request);
        				redirectURL = "/";
        			}
        			
        			if(redirectURL != null){
        				request.getRequestDispatcher(redirectURL).forward(request, response);;
        				return;
        			}
        		 }
        			
                logger.info("successfully logged in user: " + authentication.getName() + "with role as" + authentication.getUserRole());
            }catch(Exception ex){
            	logger.error("Exception Occured in logging : " + ex.getMessage());
            	response.sendRedirect(request.getContextPath()+"/login");
            	return;
            } finally {
            	if(null!=windowsIdentity)
                windowsIdentity.dispose();
            }
        }
 
        chain.doFilter(request, response);
    }
 
    private static String prepareURLForUserRole(HttpServletRequest request) {
		StringBuffer str= new StringBuffer("/useractivitys");
		Calendar prevMonthCal = Calendar.getInstance();
		prevMonthCal.add(Calendar.MONTH, -1);
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
    /**
     * Send a 401 Unauthorized along with protocol authentication headers.
     *
     * @param response HTTP Response
     * @param close    Close connection.
     */
    public void sendUnauthorized(HttpServletResponse response, boolean close) {
        try {
            getProvider().sendUnauthorized(response);
            if (close) {
                response.setHeader("Connection", "close");
            } else {
                response.setHeader("Connection", "keep-alive");
            }
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	public IWindowsAuthProvider get_auth() {
		return _auth;
	}

	public void set_auth(IWindowsAuthProvider _auth) {
		this._auth = _auth;
	}

}