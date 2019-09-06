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
			<h2 class="col-md-3">My Contacts</h2>
			<form class="form-inline col-md-6" role="search" action=test()>
				<div class="form-group ">
					<input type="text" class="form-control" name="title"
						placeholder="Search">

					<button type="submit" class="btn btn-default" value="Search">Search</button>
				</div>
			</form>
			<a class="btn btn-primary col-md-1 col-md-offset-1" href="./user/create">Create</a>
		</div> 
		<table class="table">
			<tr>
				<th>#</th>
				<th>Name</th>
				<th>Phone</th>
				<th>Group Name</th>
				<th>Address</th>
				<th>Email</th>
				<th>Remark</th>
				<th>Created At</th>
				<th>Updated At</th>
				<th>Action</th>
			</tr>
			<c:forEach var="contact" items="${contacts}">
				<tr>
					<td><c:out value="${contact.id}"></c:out></td>
					<td><c:out value="${contact.name}"></c:out></td>
					<td><c:out value="${contact.phone}"></c:out></td>
					<td><c:out value="${contact.group_name}"></c:out></td>
					<td><c:out value="${contact.address}"></c:out></td>
					<td><c:out value="${contact.email}"></c:out></td>
					<td><c:out value="${contact.remark}"></c:out></td>
					<td><c:out value="${contact.created_at}"></c:out></td>
					<td><c:out value="${contact.updated_at}"></c:out></td>
					<td><a class="btn btn-default"
						href="#">Edit</a> 
						<a class="btn btn-danger" href="#">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

<jsp:include page="commons/foot.jsp" />
</body>
</html>