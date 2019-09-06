package org.yash.rms.filter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@WebFilter(filterName="crosOriginFilter")
public class CrosOriginFilter implements Filter {
	/** The mode. */
	private String mode = "SAMEORIGIN";
	
	/**
	 * Do filter.
	 *
	 * @param request the request
	 * @param response the response
	 * @param chain the chain
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 * @see javax.servlet.Filter.doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{		
		HttpServletResponse res = (HttpServletResponse)response;
        res.addHeader("X-FRAME-OPTIONS", mode );
        chain.doFilter(request, response);  
	}

	/**
	 * Destroy.
	 *
	 * @see javax.servlet.Filter.destroy()
	 */
	public void destroy() {
	}

	/**
	 * Inits the.
	 *
	 * @param filterConfig the filter config
	 * @see javax.servlet.Filter.init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) {
		String configMode = filterConfig.getInitParameter("mode");		
		if ( configMode != null ) {
			mode = configMode;
		}
	}
}