<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<spring:message code="application_js_version" var="app_js_ver"
	htmlEscape="false" />
<link rel="stylesheet"
	href="/rms/resources/css/styles.css?ver=${app_js_ver}">

<style>
.outerCard-contact {
    background: #fff;
    border-radius: 5px;
    padding: 0;
    margin-bottom: 20px;
    height: calc(100vh - 130px);
    margin-top: 15px;
}

.contact-h1 {
	text-align: center;
	font-size: 50px;
	color: #555555;
	font-weight: 600;
	margin-top: 70px;
	margin-bottom: 0px !important;
}

.contact-h2 {
	text-align: center;
	font-size: 18px;
	color: #555555;
	font-weight: 400;
	margin-bottom: 0px !important;
}

.phone-prop {
	text-align: center;
	width: 280px;
	border: 1px solid #cccccc;
	border-radius: 4px;
	margin-top: 20px;
	height: 70px;
}

.contact-h3 {
	font-size: 18px;
	color: #555555;
	margin-bottom: 0px !important;
	padding-top: 7px;
}
.phone-prop a {
	font-weight:500;
	font-size: 18px;
}
.phone-prop a:hover {
	text-decoration: underline;
}
</style>
</head>

<body>
	<div class="content-wrapper">
		<h1>
			Contact Us<span class="my-user-id pull-right"> </span>
		</h1>
		<div class="container-fluid outerCard-contact" id="rmsForm1">
			<div class="">
				<div class="col-md-12">
					<p class="contact-h1">We'd love to hear from you</p>
					<p class="contact-h2">Whether you a have question, query, feedback
						or suggestion our</p>
					<p class="contact-h2"> team is ready to answer them all as soon as
						possible.</p>
					<!-- <a>ebiz_rmssupport@yash.com</a> -->
				</div>
				<div class="col-md-12">
					<div class="phone-prop">
						<p class="contact-h3">Send in your queries at:</p>
						<a href="mailto:ebiz_rmssupport@yash.com">ebiz_rmssupport@yash.com</a>
						<!-- <a>ebiz_rmssupport@yash.com</a> -->
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

