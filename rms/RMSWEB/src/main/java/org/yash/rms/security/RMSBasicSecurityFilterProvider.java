package org.yash.rms.security;

import java.io.IOException;
import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import waffle.servlet.spi.BasicSecurityFilterProvider;
import waffle.servlet.spi.SecurityFilterProvider;
import waffle.util.AuthorizationHeader;
import waffle.windows.auth.IWindowsAuthProvider;
import waffle.windows.auth.IWindowsIdentity;

/**
 * Servlet Filter implementation class RMSBasicSecurityFilterProvider
 */
public class RMSBasicSecurityFilterProvider implements SecurityFilterProvider {

	private Logger _log = LoggerFactory
			.getLogger(BasicSecurityFilterProvider.class);
	private String _realm ;//= "BasicSecurityFilterProvider";
	private IWindowsAuthProvider _auth = null;

	public RMSBasicSecurityFilterProvider(IWindowsAuthProvider auth) {
		_auth = auth;
	}

	public IWindowsIdentity doFilter(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		AuthorizationHeader authorizationHeader = new AuthorizationHeader(
				request);
		String usernamePassword = new String(
				authorizationHeader.getTokenBytes());
		String[] usernamePasswordArray = usernamePassword.split(":", 2);
		if (usernamePasswordArray.length != 2) {
			throw new RuntimeException(
					"Invalid username:password in Authorization header.");
		}
		request.setAttribute("un", usernamePasswordArray[0]);
		request.setAttribute("pa", usernamePasswordArray[1]);
		_log.debug("logging in user: " + usernamePasswordArray[0]);
		return _auth.logonUser(usernamePasswordArray[0],
				usernamePasswordArray[1]);
	}

	public boolean isPrincipalException(HttpServletRequest request) {
		return false;
	}

	public boolean isSecurityPackageSupported(String securityPackage) {
		return securityPackage.equalsIgnoreCase("Basic");
	}

	public void sendUnauthorized(HttpServletResponse response) {
		response.addHeader("WWW-Authenticate", "Basic realm=\"" + _realm + "\"");
	}

	/**
	 * Protection space.
	 * 
	 * @return Name of the protection space.
	 */
	public String getRealm() {
		return _realm;
	}

	/**
	 * Set the protection space.
	 * 
	 * @param realm
	 *            Protection space name.
	 */
	public void setRealm(String realm) {
		_realm = realm;
	}

	/**
	 * Init configuration parameters.
	 */
	public void initParameter(String parameterName, String parameterValue) {
		if (parameterName.equals("realm")) {
			setRealm(parameterValue);
		} else {
			throw new InvalidParameterException(parameterName);
		}
	}
}
