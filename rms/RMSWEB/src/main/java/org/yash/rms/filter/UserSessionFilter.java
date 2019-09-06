package org.yash.rms.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Custom filter to be applied on request to store the user name and role into
 * MDC context, this helps in logging the user name  to log file.
 * 
 * @author sunil.sharma
 * 
 */
@Component(value="userSessionFilter")
public class UserSessionFilter implements Filter {


	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain arg2) throws IOException, ServletException {
		String userName = "Anonymous";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			userName = authentication.getName();
			
		}

		MDC.put("userName", userName);
		//Ankita
		Enumeration<String> enum1= req.getParameterNames();
	       HttpServletRequest request=(HttpServletRequest)req;
	       HttpSession httpSession=request.getSession();
	       while(enum1.hasMoreElements()){
	              String paramName=enum1.nextElement();
	              String paramValue=req.getParameter(paramName);
	              httpSession.setAttribute(paramName,paramValue);
	       }


		// clear browser cache
		HttpServletResponse httpres = (HttpServletResponse) res;
		httpres.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP
																					// 1.1.
		httpres.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		/*httpres.setHeader("x-frame-options", "allow");*/
		/*httpres.setHeader("X-Frame-Options", "SAMEORIGIN");*/
		httpres.setDateHeader("Expires", 0); // Proxies.
	
		arg2.doFilter(req, res);

	}


	public void init(FilterConfig arg0) throws ServletException {

	}

}
