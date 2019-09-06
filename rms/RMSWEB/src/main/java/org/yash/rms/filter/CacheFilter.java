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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CacheFilter implements Filter {
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;
		String path = httpServletRequest.getServletPath();
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 3);
		final DateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		String expiryDate = httpDateFormat.format(calendar.getTime());
		httpResp.setHeader("Expires", expiryDate);
		httpResp.setHeader("Cache-Control", "max-age="+calendar.getTimeInMillis()+", public");
		httpResp.flushBuffer();
		System.out.println("expiration date set for path: " + path + " = " + expiryDate);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}
}