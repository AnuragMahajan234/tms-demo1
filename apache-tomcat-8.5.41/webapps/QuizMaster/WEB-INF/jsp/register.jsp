<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>QuizMaster</title>
</head>
<body>
<h2>Hello</h2>
	<table>
	
 <form:form method="post" action="saveNewUser" modelAttribute="user">
 <form:input path ="firstName"></form:input>
  <form:input path ="lastName"></form:input>
  <form:input path = "userName"></form:input>
 	<input type = "submit" value = "Save">
  </form:form>  
 </table>
</body>
</html>