<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />
<title>Success</title>
</head>
<body><%String approveStatus = (String) request.getAttribute("approveStatus"); %>
<b>
<center>
<c:if test="${approveStatus == 'Y'}">  
  <p>
	<spring:message code="approve_message" />
 </p>
</c:if> 
<c:if test="${approveStatus == 'N'}">  
  <p>
	<spring:message code="not_approve_message" />
 </p>
</c:if>
<c:if test="${approveStatus == 'D'}">  
  <p>
	<spring:message code="deny_message" />
 </p>
</c:if>
<c:if test="${approveStatus == 'EA'}">  
  <p>
	<spring:message code="approve_expired_message" />
 </p>
</c:if>
<c:if test="${approveStatus == 'ED'}">  
  <p>
	<spring:message code="deny_expired_message" />
 </p>
</c:if>
</center></b>
</body>
</html>