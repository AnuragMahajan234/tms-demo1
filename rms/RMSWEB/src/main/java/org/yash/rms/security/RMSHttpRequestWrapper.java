package org.yash.rms.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Base64Utils;

/**
 * Servlet implementation class RMSHttpRequestWrapper
 */
public class RMSHttpRequestWrapper extends HttpServletRequestWrapper {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RMSHttpRequestWrapper(HttpServletRequest request) {
        super(request);
        request.setAttribute("Authorization", getRMSAuthenticationHeader());
        
    }
    
    private HttpServletRequest _getHttpServletRequest() {
	return (HttpServletRequest) super.getRequest();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
    /**
     * The default behavior of this method is to return getHeader(String name)
     * on the wrapped request object.
     */
    public String getHeader(String name) {
	return (null!=name && name.equals("Authorization") && ((Boolean)_getHttpServletRequest().getSession().getAttribute("isAuthenticated")))?"Basic "+Base64Utils.encode(getDummyByteArrayEncodeinBase64Format()):_getHttpServletRequest().getHeader(name);
    }
    
    private String getRMSAuthenticationHeader(){
    	return "Basic "+getDummyByteArrayEncodeinBase64Format();
    }
    
    private byte[] getDummyByteArrayEncodeinBase64Format(){
    	String temp = "";
        byte[] b = new byte[temp.length()];
        for(int i=0;i<temp.length();i++){
        	b[i]=(byte) temp.charAt(i);
        }
        return b;
    }

}
