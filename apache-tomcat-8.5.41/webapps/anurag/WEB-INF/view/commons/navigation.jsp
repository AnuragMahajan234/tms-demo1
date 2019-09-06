<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- navigation start -->
	<nav class="navbar navbar-default ">
	<div class="container-fluid" style="width: 90%">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="./">PhoneBookApp</a>
		</div>

		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">

			<ul class="nav navbar-nav">
			
				<li><a href="./">Home</a></li>
				<li><a href="./about">About</a></li>
				<c:choose>
					<c:when test = "${role == 2}">
						<li><a href="./contacts">My Contacts</a></li>
					</c:when>
					<c:when test = "${role == 1}">
						<li><a href="./users">All Users</a></li>
					</c:when>
				</c:choose>

				<li><a href="./contact">Contact Us</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<c:choose>
					<c:when test = "${loggedInUser !=null}">
						<a href="../logout" class="btn btn-default navbar-btn btn-success">Logout</a>
					</c:when>
					<c:otherwise>
						<a href="./registration" class="btn btn-default navbar-btn btn-success">Sign Up</a>
						<a href="./login" class="btn btn-default navbar-btn btn-primary">Login</a>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
	</nav>
	<!-- navigation end -->