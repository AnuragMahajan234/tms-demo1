<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div id="loginsection">
<div class="botMargin">
	<h1>Login</h1>
</div>
<div class="loginSection">
	<c:if test="${not empty param.login_error}">
		<div class="errors">
			<p>
				<spring:message code="security_login_unsuccessful" />
				<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
				.
			</p>
		</div>
	</c:if>
	<c:if test="${empty param.login_error}">
		<p>
			<spring:message code="security_login_message" />
		</p>
	</c:if>
	<spring:url value="/resources/j_spring_security_check" var="form_url" />

	<form name="f" id="loginForm" action="${fn:escapeXml(form_url)}" method="POST">
		<table border="0" cellspacing="1" cellpadding="2" >
			<tr>
				<td><label for="username"> <spring:message
							code="security_login_form_name" />
				</label></td>
				<td><input id="username" type='text' name='username'
					class="loginInput" /></td>
			</tr>
			<tr>
				<td><label for="password"> <spring:message
							code="security_login_form_password" />
				</label></td>
				<td><input id="password" type='password' name='password'
					class="loginInput" /></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;
				 <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="Login" /> <input type="reset" value="Cancel" /></td>
			</tr>
		</table>
	</form>
</div>
</div>
<script>
<%
String user =null;

if(request.getUserPrincipal() != null){
	
	 user =  request.getRemoteUser().replace("\\", "").replace("YASH", "");
	 user=user.toLowerCase();
}

session.setAttribute("notificationbarflag", true);
session.setAttribute("defaultProjectFlag", true);
session.setAttribute("copyTimeSheetFlag", true);
session.setAttribute("admindashboardflag", true);
session.setAttribute("loanandtransferflag", true);
%>
var remoteUser = "<%=user%>";
if(remoteUser != null && remoteUser != "null" && remoteUser != "") {
	document.getElementById("loginsection").style.display="none";
	$("#username").val(remoteUser);
	$("#password").val(remoteUser);
	document.getElementById("loginForm").action="${fn:escapeXml(form_url)}";
	$("#loginForm").submit();	
}

</script>