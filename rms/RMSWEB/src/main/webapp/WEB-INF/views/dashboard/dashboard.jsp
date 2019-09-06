<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%--  <%@taglib prefix="d" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<spring:message code="application_js_version" var="app_js_ver"
	htmlEscape="false" />
<!-- <fmt:requestEncoding value="UTF-8" /> -->
	
<link rel="stylesheet"
	href="/rms/resources/dashboardscript/plugins/morris/morris.css?ver=${app_js_ver}" />
<spring:url
	value="/resources/styles/rms_home_dashboard.css?ver=${app_js_ver}"
	var="rms_home_dashboard_css" />
<link href="${rms_home_dashboard_css}" rel="stylesheet" type="text/css"></link>
<!-- Chart JS CDN -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js?ver=${app_js_ver}"></script>
<spring:url
	value="/resources/js/rmsUIValidation.js"
	var="rmsUIValidation_js" />
<script src="${rmsUIValidation_js}" type="text/javascript"></script>
  
 
<style type="text/css" title="currentStyle">
 
.dashboard-nxt-btn {
    position: absolute;
    top: 144px;
    }
.prv-nxt-btn {
float: right;
}
 
.full-msg p{
    height: 74px;
    overflow: auto;
        word-break: break-word;
        }
.prv-nxt-btn .carousel-control{
    float: left;
    height: 10px;
    margin: 0 -4px; 
}
.carousel-control .glyphicon-chevron-left, .carousel-control .icon-prev
	{
	top: 175px !important;
	left: 175px !important;
	font-size: 20px !important;
	color: black !important;
}
 
.carousel-control .glyphicon-chevron-right, .carousel-control .icon-next
	{
	top: 175px !important;
	font-size: 20px !important;
	color: black !important;
}
 
.user-name-align {
	margin-top: 14px;
}
 
 
.next-button, .next-button:hover {
    background: var(--nav-active);
    border: 1px solid var(--nav-active);
    border-radius: 50px;
    padding: 5px 15px;
    margin-right: 10px;
    color: #fff !important;
    text-decoration: none;
    margin-top: 10px;
}
 
#myMessageModal .modal-title {
	color: var(--nav-active);
	font-weight:bold;
	font-size: 20px;
}
.msg-box .carousel-inner {
    height:194px;
}
.products-list .product-info {
    margin-left: 60px;
    margin-bottom: 9px;
}
.msg-date{
margin-bottom:0px;
}
 
.no-data-msg-board {
    float: left;
    text-align: center;
    width: 100%;
    margin-top: 25px;
}
 
 
 
</style>
 
 
<!-- Calendar CSS JS -->
<script src="/rms/resources/dashboardscript/plugins/dashboard-calendar/jquery.datetimepicker.min.js?ver=${app_js_ver}"></script>
<link rel="stylesheet"href="/rms/resources/dashboardscript/plugins/dashboard-calendar/jquery.datetimepicker.min.css?ver=${app_js_ver}" />
<div class="content-wrapper">
	<!-- Main content -->
 
	<div class="page-head">
		<h1 class="theme-h1">Dashboard</h1>
	</div>
<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
	<section class="content well-custom">
		<div class="row">
			<div class="col-xs-12 col-sm-12 new-cards">
				<div class="row">
					<div class="col-xs-6 col-sm-3">
						<div class="well well-small well-small-hover well-small-non-hover">
							<h3>Resources</h3>
								<a href="javascript:void(0);" class="dropdown-toggle pull-right"
									data-toggle="dropdown"><i class="fa fa-ellipsis-v"></i></a>
								<ul class="dropdown-menu cardAction">
									<li><a href="javascript:void(0);">View All</a></li>
									<li><a href="javascript:void(0);">Add Resource</a></li>
									<li><a href="javascript:void(0);">Resource Allocation</a></li>
									<li><a href="javascript:void(0);">Loan or Transfer</a></li>
									<li><a href="javascript:void(0);">Remove</a></li>
								</ul>
							<h1>
								${resourceCount}
							</h1>
							<div class="view-all-section">
								<a id="menuResourceList" href="/rms/resources" type="html"> View All</a>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3">
						<div class="well well-small well-small-hover well-small-non-hover">
							<h3>Projects</h3>
							<a href="javascript:void(0);" class="dropdown-toggle pull-right"
									data-toggle="dropdown"><i class="fa fa-ellipsis-v"></i></a>
								<ul class="dropdown-menu cardAction">
									<li><a href="javascript:void(0);">View All</a></li>
									<li><a href="javascript:void(0);">Add Project</a></li>
									<li><a href="javascript:void(0);">Project Allocation</a></li>
									<li><a href="javascript:void(0);">Remove</a></li>
								</ul>
							<h1> 
								${projectCount}
							</h1>
							<div class="view-all-section">
								<a id="menuProjectList" href="/rms/projects"> View All</a>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3">
						<div class="well well-small well-small-hover well-small-non-hover">
							<h3>Customers</h3>
							<a href="javascript:void(0);" class="dropdown-toggle pull-right"
									data-toggle="dropdown"><i class="fa fa-ellipsis-v"></i></a>
								<ul class="dropdown-menu cardAction">
									<li><a href="javascript:void(0);">View All</a></li>
									<li><a href="javascript:void(0);">Add Customer</a></li>
									<li><a href="javascript:void(0);">Add Customer Group</a></li>
									<li><a href="javascript:void(0);">Edit Customer</a></li>
									<li><a href="javascript:void(0);">Remove</a></li>
								</ul>
							<h1>
								<!-- 3354 -->
								${customerCount}
							</h1>
							<div class="view-all-section">
								<a id="menuCustomerList" href="/rms/customers"> View All</a>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3">
						<div class="well well-small well-small-hover well-small-non-hover">
							<h3>Reports</h3>
							<a href="javascript:void(0);" class="dropdown-toggle pull-right"
									data-toggle="dropdown"><i class="fa fa-ellipsis-v"></i></a>
								<ul class="dropdown-menu cardAction">
									<li><a href="javascript:void(0);">View All</a></li>
									<li><a href="javascript:void(0);">Download</a></li>
									<li><a href="javascript:void(0);">Remove</a></li>
								</ul>
							<h1>
								${reportCount}
							</h1>
							<div class="view-all-section">
								<a id="reportsjava" href="/rms/reports/javaReports">View All</a>
							</div>
						</div>
					</div>
				</div>
				 
			</div>
			<div class="col-xs-12 col-sm-12 new-cards">
				<div class="row">
					<div class="col-xs-6 col-sm-3">
						<div class="well well-small well-small-hover well-small-non-hover">
							<h3>Approvals</h3>
							<a href="javascript:void(0);" class="dropdown-toggle pull-right"
									data-toggle="dropdown"><i class="fa fa-ellipsis-v"></i></a>
								<ul class="dropdown-menu cardAction">
									<li><a href="javascript:void(0);">View All</a></li>
									<li><a href="javascript:void(0);">Pending Approvals</a></li>
									<li><a href="javascript:void(0);">Rejected Approvals</a></li>
									<li><a href="javascript:void(0);">My Requests</a></li>
								</ul>
							<h1>
								${approvedTimeSheet}
							</h1>
							<div class="view-all-section">
								<a id="menuTimeHoursEntry" href="/rms/timehrses"> View All</a>
							</div>
						</div>
					</div>
						<div class="col-xs-6 col-sm-3">
							<div
								class="well well-small well-small-hover well-small-non-hover">
								<h3>Timesheets</h3>
								<a href="javascript:void(0);" class="dropdown-toggle pull-right"
									data-toggle="dropdown"><i class="fa fa-ellipsis-v"></i></a>
								<ul class="dropdown-menu cardAction">
									<li><a href="javascript:void(0);">View</a></li>
									<li><a href="javascript:void(0);">Submit Timesheet</a></li>
									<li><a href="javascript:void(0);">My Requests</a></li>
								</ul>
								<h1>${totalTimeSheet}</h1>
								<div class="view-all-section">
									<div class="view-all-section">
										<!-- <a id="menuTimeHoursEntry" href="/rms/timehrses"> View All</a>  -->
										<a id="menuUserActivityList" href="#" onclick="showtimesheet();">View All </a>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-6 col-sm-3">
						<div class="well well-small well-small-hover well-small-non-hover">
							<h3>My Skills</h3>
							<a href="javascript:void(0);" class="dropdown-toggle pull-right"
									data-toggle="dropdown"><i class="fa fa-ellipsis-v"></i></a>
								<ul class="dropdown-menu cardAction">
									<li><a href="javascript:void(0);">View All</a></li>
									<li><a href="javascript:void(0);">Add Skills</a></li>
									<li><a href="javascript:void(0);">Update Skills</a></li>
									<li><a href="javascript:void(0);">Remove Skills</a></li>
								</ul>
							<h1>
								<!-- 3 -->
								${totalSkillCount}
							</h1>
							<div class="view-all-section">
							 <a href="javascript:void(0);" class="editProfile"> Add Skill</a>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3">
						<div class="well well-small well-small-hover well-small-non-hover">
							<h3>My Projects</h3>
							<a href="javascript:void(0);" class="dropdown-toggle pull-right"
									data-toggle="dropdown"><i class="fa fa-ellipsis-v"></i></a>
								<ul class="dropdown-menu cardAction">
									<li><a href="javascript:void(0);">View All</a></li>
									<li><a href="javascript:void(0);">Manage Project</a></li>
									<li><a href="javascript:void(0);">Add Resource</a></li>
									<li><a href="javascript:void(0);">Remove</a></li>
								</ul>
							<h1>
								<!-- 123 -->
								 ${myProjectCount}
							</h1>
							<div class="view-all-section">
						 		<a href="javascript:void(0);" style="visibility:hidden;">Blank</a> 
							</div>
						</div>
					</div>
				</div>
				 
			</div> 
			<div class="col-xs-12 col-sm-12" style="display:none;">
				<div class="row">
					<div class="col-xs-12 col-sm-6">
						<div class="well well-small well-small-non-hover">
							<div class="col-xs-12 col-sm-12 nopadding">
								<h3 class="pull-left">Reports</h3>
								<a href="javascript:void(0);" class="dropdown-toggle pull-right"
									data-toggle="dropdown"><i class="fa fa-ellipsis-v"></i></a>
								<ul class="dropdown-menu cardAction">
									<li><a href="javascript:void(0);">Add</a></li>
									<li><a href="javascript:void(0);">Update</a></li>
									<li><a href="javascript:void(0);">Delete</a></li>
								</ul>
							</div>							
							<div class="col-xs-12 col-sm-12 dashboardGraph chart-container">
								<canvas id="myChart"></canvas> 
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="well well-small well-small-non-hover">
							<div class="col-xs-12 col-sm-12 nopadding">
								<h3 class="pull-left">My Calender</h3>
								<a href="javascript:void(0);" class="dropdown-toggle pull-right"
									data-toggle="dropdown"><i class="fa fa-ellipsis-v"></i></a>
								<ul class="dropdown-menu cardAction">
									<li><a href="javascript:void(0);">Add</a></li>
									<li><a href="javascript:void(0);">Update</a></li>
									<li><a href="javascript:void(0);">Delete</a></li>
								</ul>
							</div>
							<div class="col-xs-12 col-sm-12 dashboardCalender">
								 <div id="dashbaordCal"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			 
		</div>
 
	</section>
	</sec:authorize>
	
	
		<!-- The below code for ROLE_USER START-->
	<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR','ROLE_BEHALF_MANAGER')">
		<section class="content">
		<!--TIMESHEET STATUS -->
		<div class="row custom-widgets">
			<div class="col-md-3">
				<div class="box box-warning direct-chat direct-chat-warning">
					<div class="box-header">
						<h3 class="box-title">
							<i class="fa fa-warning"></i> Pending Approval
						</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-footer no-padding">
						<ul class="nav nav-stacked">
							<c:forEach items="${pendingtimesheet}" var="item">
								<li><a href="#",>WE - <c:out value="${item}" /></a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="box box-success direct-chat direct-chat-warning">
					<div class="box-header">
						<h3 class="box-title">
							<i class="fa fa-check-circle"></i> My Approved Timesheets
						</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-footer no-padding">
						<ul class="nav nav-stacked">
							<c:forEach items="${approvedsheet}" var="item">
								<li><a href="#">WE - <c:out value="${item}" /></a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="box box-danger direct-chat direct-chat-warning">
					<div class="box-header">
						<h3 class="box-title">
							<i class="fa fa-times-circle-o"></i> My Rejected Timesheets
						</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-footer no-padding">
						<ul class="nav nav-stacked">
							<c:forEach items="${rejectedsheet}" var="item">
								<li><a
									href="/rms/useractivitys?minWeekStartDate=6/5/2016&maxWeekStartDate=${item}&i=1&find=ByWeekStartDateBetweenAndEmployeeIdView",>WE
										- <c:out value="${item}" />
								</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div
					class="box box-primary direct-chat direct-chat-warning todo-list">
					<div class="box-header">
						<h3 class="box-title">
							<i class="fa fa-list-ul"></i> To Do List
						</h3>
						<div class="pull-right">
							<sec:authorize
								access="hasAnyRole('ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
								<a href="#" data-toggle="tooltip" data-placement="bottom"
									class="toDoTooltip"
									title="You can directly navigate to their pending for approval timesheets form Pending Approval using one click."><i
									class="fa fa-plus-square"></i> New</a>
							</sec:authorize>
							<sec:authorize
								access="hasAnyRole('ROLE_USER','ROLE_HR','ROLE_ADMIN')">
								<a href="#" data-toggle="tooltip" data-placement="bottom"
									class="toDoTooltip"
									title="You can directly navigate to your timesheet on clicking at To Do List and My Rejected Timesheet."><i
									class="fa fa-plus-square"></i> New</a>
							</sec:authorize>
 
						</div>
					</div>
					<!-- /.box-header -->
					<div class="box-footer no-padding">
						<ul class="nav nav-stacked">
							<c:forEach items="${tododatehyperlink}" var="item">
								<li><a
									href="/rms/useractivitys?minWeekStartDate=6/5/2016&maxWeekStartDate=${item}&i=1&find=ByWeekStartDateBetweenAndEmployeeIdView",>WE
										- <c:out value="${item}" />
								</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<!-- /.row -->
 
		<!--MESSAGE DETAILS -->
		<div class="row custom-info">
			<div class="col-md-3">
				<div class="box box-even">
						<div class="box-header">
							<h3 class="box-title">Message Board</h3>
						</div>
						<div class="box-body">
							<div id="messageCarousel" class="carousel slide msg-box " data-ride="carousel">
								<div class="carousel-inner">
								<c:set var="msgList" value="${myMessages}" />
								<c:set var="msgLen" value="${fn:length(myMessages)}" />
								<c:choose>
								<c:when test="${fn:length(myMessages) ge 1}">
											<c:set var="even" value="0" />
											<c:forEach items="${myMessages}" var="msg" varStatus="loop">
												<c:set var="val1" value="${loop.index}" />
												<c:set var="even" value="${val1 % 2}" />
												<c:set var="txt" value="${msg.text}" />
												<div class="${even == 0 && val1==0 ? 'item active':'item'}">
													<ul class="products-list product-list-in-box">
														<li class="item">
														<c:choose>
																<c:when test="${not empty msg.creatorProfileImage}">
																	<div class="product-img">
																		<img
																			src="data:image/jpeg;base64,${msg.creatorProfileImage}"
																			alt="Product Image" class="img-circle" />
																	</div>
																</c:when>
																<c:otherwise>
																	<div class="product-img">
																		<img
																			src="/rms/resources/dashboardscript/dist/img/User-Image.png"
																			alt="Product Image" class="img-circle" />
																	</div>
																</c:otherwise>
															</c:choose>
															<div class="product-info user-name-align">
																<p class="msg-date">${msg.createdTime}</p>
																<a href="javascript::;" class="product-title">${msg.creator}
																</a>
															</div>
															 
															<div class="full-msg">
																<div class="pull-left">
																	<%-- <p>${msg.text}</p> --%>
																	<%-- <p id="msgTextId${val1}">
																	${fn:escapeXml((txt))}
																 <script type="text/javascript">decode_utf8("msgTextId${val1}",'${txt}')</script>														
																	</p> --%>
																	<p>${msg.text}
																	<!-- <script type="text/javascript">console.log("msgTextId${val1}",'${txt}')</script> -->
																	<!-- <script type="text/javascript">decode_utf8("msgTextId${val1}",'${txt}')</script> -->
																	</p>																	 
																</div>
															</div></li>
														<!-- /.item -->
													</ul>
												</div>
											</c:forEach>
											<a class="btn next-button dashboard-nxt-btn" data-toggle="modal"
												data-target="#myMessageModal">Add New Message</a>
									</c:when>									
									<c:otherwise>
											<div class="no-data-msg-board">
												<div><p>No Message Available </p></div>
											 
												<div><p>Please add a message</p></div>
												<a class="btn next-button" data-toggle="modal"
												data-target="#myMessageModal">Add New Message</a>
											</div>
											
										</c:otherwise>
									</c:choose>
								</div>
								<c:choose>
 
								<c:when test="${fn:length(myMessages) gt 1}">
								<!-- <a class="btn next-button" data-toggle="modal"
									data-target="#myMessageModal">Add New Message</a> --> 
								<div class="prv-nxt-btn">
								<a class="left carousel-control" href="#messageCarousel"
									data-slide="prev"> <span
									class="glyphicon glyphicon-chevron-left"></span> <span
									class="sr-only">Previous</span></a>
								<a class="right carousel-control " href="#messageCarousel"
									data-slide="next"> <span
									class="glyphicon glyphicon-chevron-right"></span> <span
									class="sr-only">Next</span>
								</a>
								</div>
								</c:when>
								<c:otherwise>								
										<!-- <div class="no-data-msg-board">
												<div><p>No Message Available </p></div>
											 
												<div><p>Please add a message</p></div>
												<a class="btn next-button" data-toggle="modal"
												data-target="#myMessageModal">Add New Message</a>
											</div> -->								
								</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<!--message modal  -->
					<div id="myMessageModal" class="modal fade" role="dialog">
						<div class="modal-dialog">
 
							<!-- Modal content-->
							<div class="modal-content" style="width: 530px;margin: 0 auto;">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" onclick="closeAddMessagePopup()">&times;</button>
									<h4 class="modal-title">Add New Message</h4>
								</div>
								<div class="modal-body">
									<div class="form-group">
										<label for="newMsg">Message:</label>
										<textarea id="newMsg" class="form-control" maxlength="500" style="resize: none; height: 120px;"></textarea>
									</div>
								</div>
								<div class="modal-footer">
								<button type="button" id="close-btn"
							class="btn btn-secondary next-button" style=""
							data-dismiss="modal" onclick="closeAddMessagePopup()">Close</button>
									<button type="button" class="btn next-button" id="add-btn"
										 onclick="onAddNewMessage()">Submit</button>
								</div>
							</div>
 
						</div>
					</div>
					<!-- message modal end - -->
			</div>
			<div class="col-md-3">
				<div class="box box-odd">
					<div class="box-header">
						<h3 class="box-title">My Reporting Managers</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<ul class="products-list product-list-in-box">
							<li class="item"><c:choose>
									<c:when test="${not empty IRMImage }">
										<div class="product-img">
											<img src="data:image/jpeg;base64,${IRMImage}"
												alt="Product Image" class="img-circle" />
										</div>
									</c:when>
									<c:otherwise>
										<div class="product-img">
											<img
												src="/rms/resources/dashboardscript/dist/img/User-Image.png"
												alt="Product Image" class="img-circle" />
										</div>
									</c:otherwise>
								</c:choose>
								<div class="product-info">
									<a href="javascript::;" class="product-title">${IRM} <span
										class="product-description"> ${IRMDesignation} </span>
									</a>
								</div></li>
							<li class="item"><c:choose>
									<c:when test="${not empty SRMImage }">
										<div class="product-img">
											<img src="data:image/jpeg;base64,${SRMImage}"
												alt="Product Image" class="img-circle" />
										</div>
									</c:when>
									<c:otherwise>
										<div class="product-img">
											<img
												src="/rms/resources/dashboardscript/dist/img/User-Image.png"
												alt="Product Image" class="img-circle" />
										</div>
									</c:otherwise>
								</c:choose>
								<div class="product-info">
									<a href="javascript::;" class="product-title">${SRM} <span
										class="product-description">${SRMDesignation} </span>
									</a>
								</div></li>
							<!-- /.item -->
						</ul>
					</div>
					<!-- /.box-body -->
				</div>
			</div>
			<div class="col-md-3">
				<div class="box box-even">
					<div class="box-header">
						<h3 class="box-title">My Projects</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<ul class="products-list product-list-in-box project-description">
							<c:forEach items="${myProject}" var="item">
								<c:set var="arrayofmsg" value="${fn:split(item,':')}" />
								<li class="item primary-items">
									<div class="product-info">
										<a href="javascript::;" class="product-title">
											${arrayofmsg[0]} <span class="product-description">
												${arrayofmsg[1]} </span>
										</a>
									</div>
								</li>
							</c:forEach>
							<!-- /.item -->
						</ul>
					</div>
					<!-- /.box-body -->
				</div>
			</div>
			<div class="col-md-3">
				<div class="box box-odd">
					<div class="box-header">
						<h3 class="box-title">My Skills</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<ul class="products-list product-list-in-box skills-description">
							<li class="item primary-skills">
								<div class="product-img">
									<i class="fa fa-circle text-primary"></i>
								</div>
								<div class="product-info">
									<a href="javascript::;" class="product-title"> Primary
										Skills <c:forEach items="${primaryskills}" var="item">
											<span class="product-description"> <c:out
													value="${item}" />
											</span>
										</c:forEach>
									</a>
								</div>
							</li>
							<li class="item primary-skills">
								<div class="product-img">
									<i class="fa fa-circle text-primary"></i>
								</div>
								<div class="product-info">
									<a href="javascript::;" class="product-title"> Secondary
										Skills <c:forEach items="${secondarySkills}"
											var="secondaryskill">
											<span class="product-description"> <c:out
													value="${secondaryskill}" />
											</span>
										</c:forEach>
									</a>
								</div>
							</li>
 
							<!-- /.item -->
						</ul>
					</div>
					<!-- /.box-body -->
				</div>
			</div>
		</div>
		</section>
		</sec:authorize>
	<!-- The below code for ROLE_USER START-->
	
	
	
	
	<section class="content">
		<!--TIMESHEET STATUS -->
		<div class="row custom-widgets hidden">
			<div class="col-md-3">
				<div class="box box-warning direct-chat direct-chat-warning">
					<div class="box-header">
						<h3 class="box-title">
							<i class="fa fa-warning"></i> Pending Approval
						</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-footer no-padding">
						<ul class="nav nav-stacked">
							<c:forEach items="${pendingtimesheet}" var="item">
								<li><a href="#",>WE - <c:out value="${item}" /></a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="box box-success direct-chat direct-chat-warning">
					<div class="box-header">
						<h3 class="box-title">
							<i class="fa fa-check-circle"></i> My Approved Timesheets
						</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-footer no-padding">
						<ul class="nav nav-stacked">
							<c:forEach items="${approvedsheet}" var="item">
								<li><a href="#">WE - <c:out value="${item}" /></a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="box box-danger direct-chat direct-chat-warning">
					<div class="box-header">
						<h3 class="box-title">
							<i class="fa fa-times-circle-o"></i> My Rejected Timesheets
						</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-footer no-padding">
						<ul class="nav nav-stacked">
							<c:forEach items="${rejectedsheet}" var="item">
								<li><a
									href="/rms/useractivitys?minWeekStartDate=6/5/2016&maxWeekStartDate=${item}&i=1&find=ByWeekStartDateBetweenAndEmployeeIdView",>WE
										- <c:out value="${item}" />
								</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div
					class="box box-primary direct-chat direct-chat-warning todo-list">
					<div class="box-header">
						<h3 class="box-title">
							<i class="fa fa-list-ul"></i> To Do List
						</h3>
						<div class="pull-right">
							<sec:authorize
								access="hasAnyRole('ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
								<a href="#" data-toggle="tooltip" data-placement="bottom"
									class="toDoTooltip"
									title="You can directly navigate to their pending for approval timesheets form Pending Approval using one click."><i
									class="fa fa-info-circle"></i></a>
							</sec:authorize>
							<sec:authorize
								access="hasAnyRole('ROLE_USER','ROLE_HR','ROLE_ADMIN')">
								<a href="#" data-toggle="tooltip" data-placement="bottom"
									class="toDoTooltip"
									title="You can directly navigate to your timesheet on clicking at To Do List and My Rejected Timesheet."><i
									class="fa fa-info-circle"></i></a>
							</sec:authorize>
 
						</div>
					</div>
					<!-- /.box-header -->
					<div class="box-footer no-padding">
						<ul class="nav nav-stacked">
							<c:forEach items="${tododatehyperlink}" var="item">
								<li><a
									href="/rms/useractivitys?minWeekStartDate=6/5/2016&maxWeekStartDate=${item}&i=1&find=ByWeekStartDateBetweenAndEmployeeIdView",>WE
										- <c:out value="${item}" />
								</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<!-- /.row -->
 
		<!--EMPLOYEE DETAILS -->
		<div class="row custom-info hidden">
			<div class="col-md-3">
				<div class="box box-even">
					<div class="box-header">
						<h3 class="box-title">My Info</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<ul class="products-list product-list-in-box">
							<li class="item"><c:choose>
									<c:when test="${not empty UserImage }">
										<div class="product-img">
											<img src="data:image/jpeg;base64,${UserImage}"
												alt="Product Image" class="img-circle" />
										</div>
									</c:when>
									<c:otherwise>
										<div class="product-img">
											<img
												src="/rms/resources/dashboardscript/dist/img/User-Image.png"
												alt="Product Image" class="img-circle" />
										</div>
									</c:otherwise>
								</c:choose>
								<div class="product-info">
									<a href="javascript::;" class="product-title">${firstName}<span
										class="product-description">${designation}</span>
									</a>
								</div>
								<div>
									<div class="product-description info-description pull-left">
										<p>
											<span class="info-title-color">Current BU : </span><span title="${CurrentBU}">${CurrentBU}</span></p>
										<p>
											<span class="info-title-color">Parent BU : </span><span title="${ParentBU}">${ParentBU}</span></p>
										<p>
											<span class="info-title-color">Current Location : </span><span title="${CurrentLocation}">${CurrentLocation}</span></p>
										<p>
											<span class="info-title-color">Base Location : </span><span title="${ParentLocation}">${ParentLocation}</span></p>
									</div>
								</div></li>
							<!-- /.item -->
						</ul>
					</div>
					<!-- /.box-body -->
				</div>
			</div>
			<div class="col-md-3">
				<div class="box box-odd">
					<div class="box-header">
						<h3 class="box-title">My Reporting Managers</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<ul class="products-list product-list-in-box">
							<li class="item"><c:choose>
									<c:when test="${not empty IRMImage }">
										<div class="product-img">
											<img src="data:image/jpeg;base64,${IRMImage}"
												alt="Product Image" class="img-circle" />
										</div>
									</c:when>
									<c:otherwise>
										<div class="product-img">
											<img
												src="/rms/resources/dashboardscript/dist/img/User-Image.png"
												alt="Product Image" class="img-circle" />
										</div>
									</c:otherwise>
								</c:choose>
								<div class="product-info">
									<a href="javascript::;" class="product-title">${IRM} <span
										class="product-description"> ${IRMDesignation} </span>
									</a>
								</div></li>
							<li class="item"><c:choose>
									<c:when test="${not empty SRMImage }">
										<div class="product-img">
											<img src="data:image/jpeg;base64,${SRMImage}"
												alt="Product Image" class="img-circle" />
										</div>
									</c:when>
									<c:otherwise>
										<div class="product-img">
											<img
												src="/rms/resources/dashboardscript/dist/img/User-Image.png"
												alt="Product Image" class="img-circle" />
										</div>
									</c:otherwise>
								</c:choose>
								<div class="product-info">
									<a href="javascript::;" class="product-title">${SRM} <span
										class="product-description">${SRMDesignation} </span>
									</a>
								</div></li>
							<!-- /.item -->
						</ul>
					</div>
					<!-- /.box-body -->
				</div>
			</div>
			<div class="col-md-3">
				<div class="box box-even">
					<div class="box-header">
						<h3 class="box-title">My Projects</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<ul class="products-list product-list-in-box project-description">
							<c:forEach items="${myProject}" var="item">
								<c:set var="arrayofmsg" value="${fn:split(item,':')}" />
								<li class="item primary-items">
									<div class="product-info">
										<a href="javascript::;" class="product-title">
											${arrayofmsg[0]} <span class="product-description">
												${arrayofmsg[1]} </span>
										</a>
									</div>
								</li>
							</c:forEach>
							<!-- /.item -->
						</ul>
					</div>
					<!-- /.box-body -->
				</div>
			</div>
			<div class="col-md-3">
				<div class="box box-odd">
					<div class="box-header">
						<h3 class="box-title">My Skills</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<ul class="products-list product-list-in-box skills-description">
							<li class="item primary-skills">
								<div class="product-img">
									<i class="fa fa-circle text-primary"></i>
								</div>
								<div class="product-info">
									<a href="javascript::;" class="product-title"> Primary
										Skills <c:forEach items="${primaryskills}" var="item">
											<span class="product-description"> <c:out
													value="${item}" />
											</span>
										</c:forEach>
									</a>
								</div>
							</li>
							<li class="item primary-skills">
								<div class="product-img">
									<i class="fa fa-circle text-primary"></i>
								</div>
								<div class="product-info">
									<a href="javascript::;" class="product-title"> Secondary
										Skills <c:forEach items="${secondarySkills}"
											var="secondaryskill">
											<span class="product-description"> <c:out
													value="${secondaryskill}" />
											</span>
										</c:forEach>
									</a>
								</div>
							</li>
 
							<!-- /.item -->
						</ul>
					</div>
					<!-- /.box-body -->
				</div>
			</div>
		</div>
		
		<br><br>
		
		<!-- Content Header (Page header) -->
		<section class="content-header arrow_box"
			id="timesheetStatusGraphBubble">
			<h1>
				<i class="fa fa-line-chart"></i> Time log &amp; Summation <small>reports
					and statistics</small>
			</h1>
		</section>
		<br>
		<!--CHART -->
 
		<div class="row" id="timehseetStatusGraph">
			<div class="col-sm-12 col-md-12 col-xs-12">
				<div class="box box-solid">
					<div class="box-header with-border">
						<i class="fa fa-clock-o"></i>
						<h3 class="box-title">Time-sheet Submission Statistics</h3>
					</div>
					<div class="box box-solid">
						<div class="box-body chart-responsive">
							<div id="timesheet-status"></div>
							<div class="xAxis axisTitle">Weeks -----></div>
							<div class="yAxis axisTitle">Timesheet Count -----></div>
						</div>
					</div>
					<!-- /.box-body -->
				</div>
			</div>
		</div>
		<!-- /.box -->
	</section>
	
</div>
 
 
<script type="text/javascript">
	jQuery(document).ready(function() { 
		
		 
		function moveProgressBar() { 
			  var width = 1; 
			  var id = setInterval(frame, 70); 
			  function frame() {
			    if (width >= 100) {
			      clearInterval(id);
			    } else {
			      width++; 
			      document.getElementsByClassName("myBar1")[0].style.width = width + '%';
			      document.getElementsByClassName("myBar2")[0].style.width = width + '%';
			      document.getElementsByClassName("myBar3")[0].style.width = width + '%';
			    }
			  }
			};
		moveProgressBar();
		$(".toast-close").on('click', function() {
			$(this).parent().css('display', 'none');
		}); 
		 
		setTimeout(function(){   
			$('.notification-bar').css('display', 'none'); 
		}, 8000);
		
		//Progress bar for notifications
				
	});
		
	$(function(result) {
		"use strict";
		var morrisBar;
		var data = [];
		morrisBar = new Morris.Bar({
			element : 'timesheet-status',
			/* barSizeRatio:0.1, */
			barSizeRatio : 0.70,
			xLabelMargin : 1,
			data : [],
			xkey : 'weekEndDate',
			ykeys : [ 'approvedCount', 'pendingCount', 'rejectedCount',
					'notSubmittedCount' ],
			labels : [ 'Approved', 'Pending', 'Rejected', 'Not Submitted' ],
			axes : true,
			stacked : true,
			resize : true,
			barColors : [ '#4D82B6', '#BB534A', '#99BB5D', '#FE8C29' ]
		});
 
		$.ajax({
			url : '/rms/dashboard/loadUserGraph',
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			//processData: false,
			success : function(data) {
				morrisBar.setData(data);
				if (data == '') {
					$('#timehseetStatusGraph').hide();
					$('#timesheetStatusGraphBubble').hide();
				}
 
			},
			error : function(e) {
				console.log("ERROR: ", e);
 
			}
		});
	});
	function closeAddMessagePopup(){
		$("#newMsg").val('');
		document.getElementById("add-btn").disabled = false;
		var modal = document.getElementById('myMessageModal');
		modal.style.display = "none";
	}


	
function onAddNewMessage(){
		
		startProgress();
			
		 
		var messageText =  document.getElementById('newMsg').value;		
		messageText = messageText.trim();
		if(messageText.length == 0){
			showError("Please enter message!");
			stopProgress();
			return false;
		}
		if(regExForMsgTxtFailed(messageText)){
			showError("Please enter alphanumeric characters (a-zA-Z0-9-_!@#$%^&*()~ and space)!");
			stopProgress();
			return false;
		}
		if(messageText.length > 500){
			showError("Your message exceeds 500 characters!");
			stopProgress();
			return false;
		}
		
		//document.getElementById("add-btn").disabled = true;
		$("#newMsg").val('');	 
		//modal.style.display = "none";
		
		
		$.ajax({
			type : 'POST',
			url : '/rms/messageboard/addMessage',
			data : {"text" : messageText },
			success : function(response) {
				if (null != response) {
					showSuccess("Your Message has been Successfully sent to Admin!");
					$('#myMessageModal').modal('hide');
					$(".modal-backdrop.in").hide();
					 
				} else {
					showError("Something happend wrong!! ");
				}
				stopProgress();
				
			},
			error : function(xhr, status, thrownError) {
				stopProgress();
				if (xhr.responseText.startsWith("<!DOCTYPE")) {
					//Session has Expired,redirect to login page
					window.location.href = "/rms/login";
				} else {
					if (xhr.status != 200) {
						showError(((JSON.parse(xhr.responseText))['errMsg']));
 
					} else {
						showError(((JSON.parse(xhr.responseText))['errMsg']));
					}					
				}
			}
		});
	}
</script>
 
 
<div class="notification-bar">
	<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
	<sec:authorize
		access="hasAnyRole('ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
		<div class="notification-text">You can directly navigate to your
			pending timesheets on clicking at Pending For Approval.</div>
	</sec:authorize>
 
	<sec:authorize
		access="hasAnyRole('ROLE_USER','ROLE_HR','ROLE_ADMIN','ROLE_SEPG_USER')">
		<div class="notification-text">You can directly navigate to your
			timesheet on clicking at To Do List and My Rejected Timesheet.</div>
	</sec:authorize>
<div class="myProgress">
  <div class="myBar myBar1"></div>
</div>
</div>
 
<sec:authorize
		access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
    <div class="notification-bar notification3">
	<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
	
		<div class="notification-text">All Resources of respective
			Projects are available in the Projection allocation module for Search</div>
			<div class="myProgress">
			  <div class="myBar myBar2"></div>
			</div>
	</div>
</sec:authorize>
<sec:authorize
	access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
	<div class="notification-bar notification2">
		<span class="toast-close close"><span
			title="Close Notification">&nbsp;&nbsp;</span></span>
		<div class="notification-text">You can configure/set activity,
			project module and mail setting from Configurations link.</div>
			<div class="myProgress">
			  <div class="myBar myBar3"></div>
			</div>
	</div>
</sec:authorize>
 
<script>
 
Chart.defaults.global.legend.position = 'right';
var ctx = document.getElementById('myChart').getContext('2d'); 
var chart = new Chart(ctx, {
    // The type of chart we want to create
   		type: "doughnut",
        indexLabelPlacement: "outside",        
        radius: "90%",
    // The data for our dataset
    data: {
        labels: ["Number 1", "Number 2", "Number 3", "Number 4"], 
        datasets: [{
            label: "My First dataset",
            backgroundColor: 
            	[
                 	"#004465",
                    "#4f9ed3",
                    "#c1ddf1",
                    "#ffc52b"
                 ], 
            data: [27, 23, 16, 34],
        }]
    },
 
    // Configuration options go here
    options: {
    	aspectRatio: 1.5, 
    	legend: { 
    		 display: true, 
             labels: {
                 fontColor: '#333333',
                 verticalAlign: "center",
             }
         }
    }
}); 
 
$(document).ready(function(){
    function logEvent(type, date) {
        $("<div class='log__entry'/>").hide().html("<strong>"+type + "</strong>: "+date).prependTo($('#eventlog')).show(200);
    }
    $('#clearlog').click(function() {
        $('#eventlog').html('');
    });
$('#dashbaordCal').datetimepicker({
    //date: new Date(),
    viewMode: 'YMDHMS',
    //date selection event
    onDateChange: function() {
        logEvent('onDateChange', this.getValue());
 
        $('#date-text1-1').text(this.getText());
        $('#date-text-ymd1-1').text(this.getText('YYYY-MM-DD'));
        $('#date-value1-1').text(this.getValue());
    },
    //clear button click event
    onClear: function() {
        logEvent('onClear', this.getValue());
    },
    //ok button click event
    onOk: function() {
        logEvent('onOk', this.getValue());
    },
    //close button click event
    onClose: function() {
        logEvent('onClose', this.getValue());
    }
});
 
$('#newMsg').bind("keypress", function (event) {
	var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	if(regExForMsgTxtFailed(key)){
	        event.preventDefault();
	        return false;
	    }
 });
 
});
	</script>
 
 
<script type="text/javascript">
	function showtimesheet() {
		var i;
		var mMonthStartDate;
		var mMonthEndDate;
		var date = new Date();
		var prevoius2Date = new Date();
		var mMonth = date.getMonth() + 1;
		var mYear = date.getFullYear();
		var pre2Month = new Date(prevoius2Date.setMonth(date.getMonth() + (-1)));
		var preMonth = pre2Month.getMonth() + 1;
		var preYear = pre2Month.getFullYear();
		if (mMonth == "12" || mMonth == "10" || mMonth == "8" || mMonth == "7"
				|| mMonth == "8" || mMonth == "5" || mMonth == "3"
				|| mMonth == "1") {
			mMonthStartDate = mMonth + "/" + "1" + "/" + mYear;
			mMonthEndDate = mMonth + "/" + "31" + "/" + mYear;
		} else {
			if (mMonth == "2") {
				if (mYear % 4 == 0) {
					mMonthStartDate = mMonth + "/" + "1" + "/" + mYear;
					mMonthEndDate = mMonth + "/" + "29" + "/" + mYear;
				} else {
					mMonthStartDate = mMonth + "/" + "1" + "/" + mYear;
					mMonthEndDate = mMonth + "/" + "28" + "/" + mYear;
				}
			} else {
				mMonthStartDate = mMonth + "/" + "1" + "/" + mYear;
				mMonthEndDate = mMonth + "/" + "30" + "/" + mYear;
			}
		}
		window.location.href = "/rms/useractivitys?minWeekStartDate="
				+ mMonthStartDate + "&maxWeekStartDate=" + mMonthEndDate
				+ "&i=" + 0 + "&find=ByWeekStartDateBetweenAndEmployeeIdView";
	}
</script>