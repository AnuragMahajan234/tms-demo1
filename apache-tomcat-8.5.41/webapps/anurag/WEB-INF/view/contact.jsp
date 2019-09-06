
<jsp:include page="commons/head.jsp"/>
<div class=container>
		<h2>Contact Us</h2>
		<hr>
		<div class="well">
			<p>Thanks for giving this opportunity to serve you. Share your
				contact detail with us.
		</div>
		<form action="">
			<div class="form-group">
				<label for=name">Name:</label> <input type="text"
					class="form-control" placeholder="Enter Name">
			</div>
			<div class="form-group">
				<label for=contact">Contact:</label> <input type="number"
					class="form-control" placeholder="Enter Contact">
			</div>
			<div class="form-group">
				<label for=email">Email:</label> <input type="email"
					class="form-control" placeholder="Enter Email">
			</div>
			<div class="form-group">
				<label for=comment">Comment:</label> 
				<textarea class="form-control" rows="3" placeholder="Enter your opinion"></textarea>
			</div>
			<button type="submit" class="btn btn-default">Submit</button>
		</form>
	</div>
<jsp:include page="commons/foot.jsp"/>