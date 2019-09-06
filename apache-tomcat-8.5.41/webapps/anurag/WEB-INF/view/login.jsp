<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PhoneBook</title>

<!-- link preparation -->
<s:url value="static/css/bootstrap.min.css" var="url_bootstrap"/>
<s:url value="static/css/style.css" var="url_style"/>

<link href="${url_style}" rel="stylesheet" type="text/css" />
<link href="${url_bootstrap}" rel="stylesheet" type="text/css" />
</head>
<body>
	<jsp:include page="commons/navigation.jsp"/>
<div class=container>
	<h2>Login</h2>
	<hr>
	<div class="row">
		<div class="col-md-12">
					<p class="well">Enter Login Details</p>
		</div>
	</div>
	<c:if test="${errMsg != null}">
	<div class="row">
		<div class="col-md-12">
	<div class="alert alert-warning" role="alert">${errMsg}</div>
	</div>
	</div>
	</c:if>
	<c:if test="${msg != null}">
	<div class="row">
		<div class="col-md-12">
	<div class="alert alert-success" role="alert">${msg}</div>
	</div>
	</div>
	</c:if>
	<div class="row">
		<div class="col-md-12">
			<form:form action="authenticate" modelAttribute="command">

				<div class="form-group">
					<label for="loginname">Login Name:</label>
					<form:input path="loginname" class="form-control"
						placeholder="Enter Login Name" />
				</div>
				<div class="form-group">
					<label for="password">Password:</label>
					<form:password path="password" class="form-control"
						placeholder="Enter Password" />
				</div>
				<button type="submit" class="btn btn-success">Login</button>
			</form:form>
		</div>
	</div>
</div>
<jsp:include page="commons/foot.jsp" />
</body>
</html>