package org.yash.rms.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import waffle.servlet.spi.SecurityFilterProvider;
import waffle.util.AuthorizationHeader;
import waffle.util.NtlmServletRequest;
import waffle.windows.auth.IWindowsAuthProvider;
import waffle.windows.auth.IWindowsIdentity;
import waffle.windows.auth.IWindowsSecurityContext;

public class CustomAuthenticationProvider  implements SecurityFilterProvider  {

	private Logger _log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	private IWindowsAuthProvider _auth = null;
	private boolean isPrincipalException = false;
	
	public CustomAuthenticationProvider(IWindowsAuthProvider auth) {
		_auth = auth;
	}

	public void sendUnauthorized(HttpServletResponse response) {
		response.addHeader("WWW-Authenticate", "DB=\"DBAuthentication\"");
	}

	public boolean isPrincipalException(HttpServletRequest request) {
		return isPrincipalException;
	}

	public IWindowsIdentity doFilter(HttpServletRequest request, HttpServletResponse response) throws IOException {

		AuthorizationHeader authorizationHeader = new AuthorizationHeader(request);
		boolean ntlmPost = authorizationHeader
				.isNtlmType1PostAuthorizationHeader();

		// maintain a connection-based session for NTLM tokns
		String connectionId = NtlmServletRequest.getConnectionId(request);
		String securityPackage = authorizationHeader.getSecurityPackage();
		_log.debug("security package: " + securityPackage + ", connection id: "
				+ connectionId);

		if (ntlmPost) {
			// type 2 NTLM authentication message received
			_auth.resetSecurityToken(connectionId);
		}

		byte[] tokenBuffer = authorizationHeader.getTokenBytes();
		_log.debug("token buffer: " + tokenBuffer.length + " byte(s)");
		IWindowsSecurityContext securityContext = _auth.acceptSecurityToken(connectionId, tokenBuffer, securityPackage);

		byte[] continueTokenBytes = securityContext.getToken();
		if (continueTokenBytes != null && continueTokenBytes.length > 0) {
			String continueToken = new String(Base64Utils.encode(continueTokenBytes));
			_log.debug("continue token: " + continueToken);
			response.addHeader("WWW-Authenticate", securityPackage + " "
					+ continueToken);
		}

		_log.debug("continue required: " + securityContext.isContinue());
		if (securityContext.isContinue() || ntlmPost) {
			response.setHeader("Connection", "keep-alive");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.flushBuffer();
			return null;
		}

		final IWindowsIdentity identity = securityContext.getIdentity();
		securityContext.dispose();
		return identity;
	}

	public boolean isSecurityPackageSupported(String securityPackage) {
		return true;
	}

	public void initParameter(String parameterName, String parameterValue) {
	}
}