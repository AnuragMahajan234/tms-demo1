<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="commons/head.jsp"/>
<div class=container>
		<h2>Registration</h2>
		<hr>
		<div class="well">
			<p>Enter Details for Registration</p>
		</div>
		
		<form:form action="validate" modelAttribute="user">
			<div class="form-group">
				<label for="name">Name:</label> 
				<form:input path="name" class="form-control" placeholder="Enter Name" />
			</div>
			<div class="form-group">
				<label for="phone">Contact:</label> 
				<form:input path="phone" class="form-control" placeholder="Enter Contact"/>
			</div>
			<div class="form-group">
				<label for="address">Address:</label> 
				<form:input path="address" class="form-control" placeholder="Enter Address"/>
			</div>
			<div class="form-group">
				<label for="email">Email:</label> 
				<form:input path="email" class="form-control" placeholder="Enter Email"/>
			</div>
			<div class="form-group">
				<label for="loginname">Login Name:</label>
				<form:input path="loginname" class="form-control" placeholder="Enter Login Name" />
			</div>
			<div class="form-group">
				<label for="password">Password:</label>
				<form:password path="password" class="form-control"	placeholder="Enter Password" />
			</div>
				<button type="submit" class="btn btn-success">Register</button>
			</form:form>
	</div>
<jsp:include page="commons/foot.jsp"/>