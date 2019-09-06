<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page isErrorPage="true" import="java.io.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div>
  <spring:message var="title" code="error_uncaughtexception_title" htmlEscape="false" />
  <h2>${fn:escapeXml(title)}</h2>
  <p>Sorry, we encountered an internal error.</p>
  <br />
  <h4>
    <spring:message code="exception_details" />
  </h4>
  <spring:message code="exception_message" htmlEscape="false" />:
  <c:out value="${exception.localizedMessage}" />
 
  <%      
      out.println("<strong> Unknown error occured. Please contact to administrator! </strong>");
  %>
</div>
