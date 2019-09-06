
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PhoneBook</title>
<link href="../static/css/style.css" rel="stylesheet" type="text/css" />
<link href="../static/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<jsp:include page="../commons/navigation.jsp"/>
	<h2>User Home page</h2>
	<p>${loggedInUser.name}</p>
	<jsp:include page="../commons/foot.jsp"/>
</body>
</html>