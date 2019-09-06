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
<s:url value="../static/css/bootstrap.min.css" var="url_bootstrap"/>
<s:url value="../static/css/style.css" var="url_style"/>

<link href="${url_style}" rel="stylesheet" type="text/css" />
<link href="${url_bootstrap}" rel="stylesheet" type="text/css" />
</head>
<body>
	<jsp:include page="commons/navigation.jsp"/>

	<div class="container">
		<div class="rows">
			<h2 class="col-md-3">Users</h2>
			<form class="form-inline col-md-6" role="search" action=test()>
				<div class="form-group ">
					<input type="text" class="form-control" name="title"
						placeholder="Search">

					<button type="submit" class="btn btn-default" value="Search">Search</button>
				</div>
			</form>
			<a class="btn btn-primary col-md-1 col-md-offset-1"
				href="./user/create">Create</a>
		</div> 
		<table class="table">
			<tr>
				<th>#</th>
				<th>Name</th>
				<th>Phone</th>
				<th>Address</th>
				<th>Email</th>
				<th>Login Name</th>
				<th>Role</th>
				<th>Created At</th>
				<th>Updated At</th>
				<th>Action</th>
			</tr>
			<c:forEach var="user" items="${users}">
				<tr>
					<td><c:out value="${user.id}"></c:out></td>
					<td><c:out value="${user.name}"></c:out></td>
					<td><c:out value="${user.phone}"></c:out></td>
					<td><c:out value="${user.address}"></c:out></td>
					<td><c:out value="${user.email}"></c:out></td>
					<td><c:out value="${user.loginname}"></c:out></td>
					<td><c:out value="${user.role}"></c:out></td>
					<td><c:out value="${user.created_at}"></c:out></td>
					<td><c:out value="${user.updated_at}"></c:out></td>
					<td><a class="btn btn-default"
						href="ViewBlogController?id=${user.id}">View</a> 
						<a class="btn btn-danger" href="DeleteBlogController?id=${user.id}">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

<jsp:include page="commons/foot.jsp" />
</body>
</html>