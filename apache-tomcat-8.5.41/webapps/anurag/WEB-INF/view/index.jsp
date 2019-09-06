<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="commons/head.jsp"/>
<c:if test="${msg != null}">
	<div class="row">
		<div class="col-md-12">
	<div class="alert alert-success" role="alert">${msg}</div>
	</div>
	</div>
	</c:if>
	<div class="container">

		<!-- main start -->
		<div class="mainSection">
			<div class="row">
				<div class="jumbotron" class="col-md-12">
					<h1>Welcome in PhoneBook App</h1>
					<p>This is a PhoneBook app. It is developed in Servlet, JSP,
						HTML, CSS, JS and Bootstrap</p>
					<button class="btn btn-warning">Join Us</button>
				</div>


				<div class="blogsection col-md-12">
					<div class=" col-md-12">


						<h3>Heading</h3>
						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit,
							sed do eiusmod tempor incididunt ut labore et dolore magna
							aliqua. Ut enim ad minim veniam, quis nostrud exercitation
							ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis
							aute irure dolor in reprehenderit in voluptate velit esse cillum
							dolore eu fugiat nulla pariatur. Excepteur sint occaecat
							cupidatat non proident, sunt in culpa qui officia deserunt mollit
							anim id est laborum.</p>
						<a href="EditController?id=${blog.id}" class="btn btn-primary">Read
							More</a>
						<hr>


					</div>
				</div>
				
			</div>
		</div>
	</div>
	<jsp:include page="commons/foot.jsp"/>
</body>
</html>