<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<title>RMS | Admin Dashboard</title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- Bootstrap 3.3.5 -->
<link rel="stylesheet" href="/rms/resources/dashboardscript/bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="/rms/resources/dashboardscript/bootstrap/css/font-awesome.min.css" />
<!-- Theme style -->
<link rel="stylesheet" href="/rms/resources/dashboardscript/dist/css/RMS.min.css">
<!-- RMS Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="/rms/resources/dashboardscript/dist/css/skins/_all-skins.min.css">
<!-- Morrris style -->
<link rel="stylesheet" href="/rms/resources/dashboardscript/plugins/morris/morris.css" />

<spring:url value="/resources/styles/rms_home_dashboard.css?ver=${app_js_ver}" var="rms_home_dashboard_css"/>
<link href="${rms_home_dashboard_css}" rel="stylesheet" type="text/css"></link>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="bootstrap/js/html5shiv.min.js"></script>
        <script src="bootstrap/js/respond.min.js"></script>
    <![endif]-->
    <spring:url value="/rms/resources/styles/style.css?ver=${app_js_ver}" var="style_css"/>
	<link href="${style_css}" rel="stylesheet" type="text/css"></link>
	<spring:url
	value="/resources/js/rmsUIValidation.js"
	var="rmsUIValidation_js" />
<script src="${rmsUIValidation_js}" type="text/javascript"></script>
	<style>
	#resourcebilling-status svg {width:100%;}
	#billing-status svg {width:100%;}
	
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
       left: 190px !important;
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

/* #myMessageModal .modal-dialog {
       width: 30% !important;
} */
.next-button, .next-button:hover {
    background: var(--nav-active);
    border: 1px solid var(--nav-active);
    border-radius: 50px;
    padding: 5px 26px;
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
</head>
<body class="hold-transition skin-blue sidebar-mini">
  <%-- <header class="main-header"> 
    <!-- Logo --> 
    <a href="index2.html" class="logo"> 
    <!-- mini logo for sidebar mini 50x50 pixels --> 
    <span class="logo-mini"><b>Y</b>ash</span> 
    <!-- logo for regular state and mobile devices --> 
    <span class="logo-lg"><img src="/rms/resources/dashboardscript/bootstrap/img/logo.png" alt="" title="" /></span> </a> 
    
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top" role="navigation"> 
      <!-- Sidebar toggle button--> 
      <a href="javascript:void(0);" class="sidebar-toggle" data-toggle="offcanvas" role="button"> <span class="sr-only">Toggle navigation</span> <i class="name visible-lg-inline-block">Resource Management System</i></a> 
      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          
          <!-- Tasks: style can be found in dropdown.less -->
         
          <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu"> <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img src="/rms/resources/dashboardscript/dist/img/avatar5.png" class="user-image" alt="User Image"> <span class="hidden-xs">${firstName}</span> </a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header"> <img src="/rms/resources/dashboardscript/dist/img/avatar5.png" class="img-circle" alt="User Image">
                <p> ${firstName} - ${designation} <small>Employee since ${DOJ}</small> </p>
              </li>
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-right"> <a href="#" class="btn btn-default btn-flat">Sign out</a> </div>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </nav>
  </header> --%>
  <!-- Left side column. contains the logo and sidebar -->
  <%-- <aside class="main-sidebar"> 
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar"> 
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image"> <img src="/rms/resources/dashboardscript/dist/img/avatar5.png" class="img-circle" alt="User Image"> </div>
        <div class="pull-left info">
          <p>${firstName}</p>
          <a href="javascript:void(0);" class="designation"><i class="fa fa-circle text-green"></i>${designation}</a> </div>
      </div>
      <!-- search form -->
    
      <!-- /.search form --> 
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu">
        <li class="active treeview"> <a href="javascript:void(0);"> <i class="fa fa-dashboard"></i> <span>Dashboard</span><i class="fa fa-angle-left pull-right"></i> </a>
          <ul class="treeview-menu">
            <li><a href="javascript:void(0);"><i class="fa fa-user"></i> Add Current/ Past Timesheet</a></li>
            <li><a href="javascript:void(0);"><i class="fa fa-th-large"></i> View Timesheet</a></li>
            <li><a href="javascript:void(0);"><i class="fa fa-pencil-square"></i> Edit Profile</a></li>
            <li><a href="javascript:void(0);"><i class="fa fa-pencil"></i> Edit Skills</a></li>
            <li><a href="javascript:void(0);"><i class="fa fa-sticky-note"></i> Release Notes</a></li>
            <li><a href="javascript:void(0);"><i class="fa fa-question-circle"></i>Help</a></li>
          </ul>
        </li>
        <li class="treeview"> <a href="javascript:void(0);"> <i class="fa fa-user-secret"></i> <span>Admin</span> </a> </li>
        <li class="treeview"> <a href="javascript:void(0);"> <i class="fa fa-exchange"></i> <span>Transaction</span></a> </li>
        <li class="treeview"> <a href="javascript:void(0);"> <i class="fa fa-file-text"></i> <span>Reports</span></a> </li>
        <li class="treeview"> <a href="javascript:void(0);"> <i class="fa fa-envelope"></i> <span>Mail Configuration</span></a> </li>
      </ul>
    </section>
   
    <!-- /.sidebar --> 
  </aside> --%>
  
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper"> 
    <!-- Content Header (Page header) -->
    <section class="content-header arrow_box">
      <h1> <i class="fa fa-line-chart"></i> Time log &amp; Summation <small>reports and statistics<center>Graphs & Widgets data will refresh at 12:00 AM everyday</center></small> </h1>
      
      <!-- <ol class="breadcrumb">
        <li><a href="javascript:void(0);"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">Dashboard</li>
      </ol> -->
    </section>
    
    <!-- Main content -->
    <section class="content"> 
      <!--CHART -->
     	 <div class="row">
      <div class="box box-solid">
          <div class="box-header with-border"> <i class="fa fa-bars"></i>
            <h3 class="box-title">Resource Billable / Non Billable Status</h3>
          </div>
		
          <div class="box-body chart-responsive" style="overflow:auto;">
            <div  id="resourcebilling-status"></div>
			 <div class="xAxis axisTitle">Projects -----></div>
            <div class="yAxis axisTitle">Resource -----></div>
		    <!--<pre id="code" class="prettyprint linenums"></pre>--> 
          </div>
          <!-- /.box-body --> 
        </div>
      
        <div class="box box-solid">
          <div class="box-header with-border"> <i class="fa fa-bar-chart"></i>
            <h3 class="box-title">Billing Status</h3>
          </div>
          <div class="box-body chart-responsive" style="overflow:auto;">
            <div  id="billing-status"></div>
             <div class="xAxis axisTitle">Projects -----></div>
            <div class="yAxis axisTitle">Hours-----></div>
            <!--<pre id="code" class="prettyprint linenums"></pre>--> 
          </div>
          <!-- /.box-body --> 
        </div>
        
        <!-- /.box --> 
      </div>
		
		
      <!-- /.row --> 
      
      <!--TIMESHEET STATUS -->
      <div class="row custom-widgets">
        <div class="col-md-3">
          <div class="box box-warning direct-chat direct-chat-warning">
            <div class="box-header">
              <h3 class="box-title"><i class="fa fa-warning"></i>  Pending For Approval</h3>
               <div class="pull-right">
							<a href="#" data-toggle="tooltip" data-placement="bottom" title="You can directly navigate to your pending timesheets on clicking at Pending For Approval."><i class="fa fa-info-circle"></i></a>			
            	 </div>
            </div>
            <!-- /.box-header -->
            <div class="box-footer no-padding pendingApproval">
              <ul class="nav nav-stacked">
              <c:forEach items="${pendingtimesheet}" var="item">
              	<c:set var="arrayofmsg" value="${fn:split(item,':')}"/>
                <li class="item primary-items">
 
               
          <a href="/rms/timehrses?active=all&j=1&WeekEndDateHyper=${arrayofmsg[0]}">WE - ${arrayofmsg[2]} <span class="pull-right badge bg-blue">${arrayofmsg[1]}</span></a>
                </li>  
                </c:forEach>
              </ul>
            </div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="box box-success direct-chat direct-chat-warning">
            <div class="box-header">
              <h3 class="box-title"><i class="fa fa-check-circle"></i> Approved Time Sheet</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-footer no-padding approved">
              <ul class="nav nav-stacked">
               <c:forEach items="${approvedsheet}" var="item">
              	<c:set var="arrayofmsg" value="${fn:split(item,':')}"/>
                <li class="item primary-items">
                 <a href="#">WE - ${arrayofmsg[2]} <span class="pull-right badge bg-blue">${arrayofmsg[1]}</span></a>
                </li>  
                </c:forEach>
              </ul>
            </div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="box box-danger direct-chat direct-chat-warning">
            <div class="box-header ">
              <h3 class="box-title"><i class="fa fa-times-circle-o"></i>Rejected Time Sheet</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-footer no-padding rejected">
              <ul class="nav nav-stacked">
               <c:forEach items="${rejectedsheet}" var="item">
              	<c:set var="arrayofmsg" value="${fn:split(item,':')}"/>
                <li class="item primary-items">
                 <a href="#">WE - ${arrayofmsg[2]} <span class="pull-right badge bg-blue">${arrayofmsg[1]}</span></a>
                </li>  
                </c:forEach>
              </ul>
            </div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="box box-primary direct-chat direct-chat-warning">
            <div class="box-header">
              <h3 class="box-title"><i class="fa fa-list-ul"></i> Time Sheet Compliance</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-footer no-padding compliance">
              <ul class="nav nav-stacked">
                <c:forEach items="${timesheetcompliance}" var="item">
              	<c:set var="arrayofmsg" value="${fn:split(item,':')}"/>
                <li class="item primary-items">
                 <a href="#">WE - ${arrayofmsg[0]} <span class="pull-right badge bg-blue">${arrayofmsg[1]}</span></a>
                </li>  
                </c:forEach>
              </ul>
            </div>
          </div>
        </div>
      </div>
      <!-- /.row --> 
      
      <!--EMPLOYEE DETAILS -->
      <div class="row custom-info">
        <!-- Message Board Start -->
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
																<a href="javascript::;" class="product-title">${msg.createdName}
																</a>
															</div>
															 
															<div class="full-msg">
																<div class="pull-left">																	
																	<p>${msg.text}
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
								</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<!--message modal start -->
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
			<!-- Message Board End -->
        <div class="col-md-3">
          <div class="box box-odd">
            <div class="box-header">
              <h3 class="box-title">My Reporting Managers</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <ul class="products-list product-list-in-box">
                <li class="item">
                <c:choose>
                	<c:when test="${not empty IRMImage }">
                		<div class="product-img"> <img src="data:image/jpeg;base64,${IRMImage}" alt="Product Image" class="img-circle" /> </div>
                	</c:when>
                	<c:otherwise>
                		<div class="product-img"> <img src="/rms/resources/dashboardscript/dist/img/User-Image.png" alt="Product Image" class="img-circle" /> </div>
                	</c:otherwise>
                </c:choose>
                  <!-- <div class="product-img"> <img src="/rms/resources/dashboardscript/dist/img/User-Image.png" alt="Product Image" class="img-circle" /> </div> -->
                  <div class="product-info"> <a href="javascript::;" class="product-title">${IRM} <span class="product-description"> ${IRMDesignation} </span> </a> </div>
                </li>
                <li class="item">
                <c:choose>
                	<c:when test="${not empty SRMImage }">
                		<div class="product-img"> <img src="data:image/jpeg;base64,${SRMImage}" alt="Product Image" class="img-circle" /> </div>
                	</c:when>
                	<c:otherwise>
                		<div class="product-img"> <img src="/rms/resources/dashboardscript/dist/img/User-Image.png" alt="Product Image" class="img-circle" /> </div>
                	</c:otherwise>
                </c:choose>
                  <!-- <div class="product-img"> <img src="/rms/resources/dashboardscript/dist/img/User-Image.png" alt="Product Image" class="img-circle" /> </div> -->
                  <div class="product-info"> <a href="javascript::;" class="product-title">${SRM} <span class="product-description"> ${SRMDesignation} </span> </a> </div>
                </li>
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
              	<c:set var="arrayofmsg" value="${fn:split(item,':')}"/>
                <li class="item primary-items">
                  <div class="product-info"> <a href="javascript::;" class="product-title"> ${arrayofmsg[0]} <span class="product-description"> ${arrayofmsg[1]} </span> </a> </div>
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
                  <div class="product-img"> <i class="fa fa-circle text-primary"></i> </div> 
                  <div class="product-info"> <a href="javascript::;" class="product-title"> Primary Skills 
                  <c:forEach items="${primaryskills}" var="item">
                  <span class="product-description"> <c:out value="${item}"/> </span> 
                  </c:forEach>
                   </a> </div>
                </li>
                <li class="item primary-skills">
                  <div class="product-img"> <i class="fa fa-circle text-primary"></i> </div>
                  <div class="product-info"> <a href="javascript::;" class="product-title"> Secondary Skills 
                  <c:forEach items="${secondarySkills}" var="secondaryskill">
                    <span class="product-description"> <c:out value="${secondaryskill}"/> </span> 
                  </c:forEach>
                  </a> </div>
                </li>
                
                <!-- /.item -->
              </ul>
            </div>
            <!-- /.box-body --> 
          </div>
        </div>
      </div>
    </section>
    <!-- /.content --> 
  </div>
  <!-- /.content-wrapper -->
  
  <!-- <footer class="main-footer">
    <div class="pull-right hidden-xs"> <b>Version</b> 6.0 </div>
    <strong>Copyright &copy; 2015-2016 <a href="http://www.yash.com/">YASH Technologies</a>.</strong> All rights reserved. </footer> -->
<!-- ./wrapper --> 

<!-- jQuery 2.1.4 --> 
<!-- <script src="/rms/resources/dashboardscript/plugins/jQuery/jQuery-2.1.4.min.js"></script> 
Bootstrap 3.3.5 
<script src="/rms/resources/dashboardscript/bootstrap/js/bootstrap.min.js"></script> 
Morris.js charts 
<script src="/rms/resources/dashboardscript/plugins/raphael-min.js"></script> 
<script src="/rms/resources/dashboardscript/plugins/morris/morris.min.js"></script> 
RMS App 
<script src="/rms/resources/dashboardscript/dist/js/app.min.js"></script> 
SlimScroll 1.3.0 
<script src="/rms/resources/dashboardscript/plugins/slimScroll/jquery.slimscroll.min.js"></script> 
Demo 
<script src="/rms/resources/dashboardscript/dist/js/demo.js"></script>  -->
<script>
$(document).ready(function() {
	
	
	var data = [];
	var myData = [];
    "use strict";
	var morrisBar;
	var resourcebar;
	var checkflag = 0;
	
	 morrisBar= new Morris.Bar({
		  element: 'billing-status',
		  /* barSizeRatio:0.1, */
		  barSizeRatio:0.70,
		  xLabelMargin: 1,
		  data: myData,
		  xkey: 'projectName',
		  ykeys: ['Reported', 'Billable', 'Non billable'],
		  labels: ['Reported', 'Billable', 'Non billable'],
		  stacked: true,
		  resize :  true,
		  barColors: ['#4D82B6','#BB534A', '#99BB5D']
});
	 
	 resourcebar=Morris.Bar({
		  element: 'resourcebilling-status', 
		  /* barSizeRatio:0.05, */
		  barSizeRatio:0.70,
		  xLabelMargin: 1,
		  data: [],
		  xkey: 'projectName',
			  ykeys: ['others', 'billable'],
		  labels: ['Others', 'Billable'],
		  stacked: true,
		  resize :  true,
		  barColors: ['#FE8C29','#94A75C']
}); 

	$(":checked").each(function() {
		if( $(this).val().length >0 ) {
			data.push($(this).val());
	    checkflag = 1;
	    }
	});
  if(checkflag == 1){
	 // alert("I am here!!");
  startProgress();
	
	var requestData;

	var flag = 0; 

	var temp=data.join();
	var requestData= "bgAdminListOfBu="+temp;

	  $.ajax({
          url: '/rms/dashboard/submitLoadManagerBillingStatus',
          contentType : 'application/json; charset=utf-8',
          dataType: 'json',
          //processData: false,
          data : {bgAdminListOfBu: temp},
          success: function(data) {
        	  var onloaddataBilling =data.length;
              var resultwidth=300*onloaddataBilling;
              $("#billing-status").css({"width": resultwidth});

          	 
          	myData=data;
          	morrisBar.setData(myData);
          	
          },
          error : function(e) {

				stopProgress();
			}
      }); 
	  $.ajax({
          url: '/rms/dashboard/submitLoadDeliveryManagerResourceBillingStatus',
          contentType : 'application/json; charset=utf-8',
          dataType: 'json',
          //processData: false,
          data : {bgAdminListOfBu1 :temp},
          success: function(data) {
        	  var onloaddataBilling =data.length;
              var resultwidth=300*onloaddataBilling;
              $("#resourcebilling-status").css({"width": resultwidth});
         	myData=data;
          	resourcebar.setData(myData);
          	
          },
          error : function(e) {
				console.log("ERROR: ", e);
				
			}
      });
	  callWidget(temp);
	  stopProgress();
  }else{
	  startProgress();
        $.ajax({
            url: '/rms/dashboard/loadManagerBillingStatus',
            contentType : 'application/json; charset=utf-8',
            dataType: 'json',
            //processData: false,
            success: function(data) {
            	var onloaddataBilling =data.length;
                var resultwidth=300*onloaddataBilling;
                $("#billing-status").css({"width": resultwidth});
            	 
            	myData=data;
            	morrisBar.setData(myData);
            },
            error : function(e) {
				console.log("ERROR: ", e);

			}
        });
     $.ajax({
           url: '/rms/dashboard/loadDeliveryManagerResourceBillingStatus',
           contentType : 'application/json; charset=utf-8',
           dataType: 'json',
           //processData: false,
           success: function(data) {
        	   var onloaddataBilling =data.length;
               var resultwidth=300*onloaddataBilling;

               $("#resourcebilling-status").css({"width": resultwidth});

           	resourcebar.setData(data);
           },
           error : function(e) {
				console.log("ERROR: ", e);

			}
       });
     //getWidget();
     stopProgress();
  }  
	 
	  
	  $('.loadData').click(function(){
		  var checkflag = 0;
		  $(":checked").each(function() {
				if( $(this).val().length >0 ) {
			    checkflag = 1;
			    }
			});
		  if(checkflag == 1){
		  startProgress();
			var data = [];
			var requestData;
			var flag = 0;
			/*$('#billing-status svg').hide();
			$('#billing-status .morris-hover').hide(); */
			$(":checked").each(function() {
				if( $(this).val().length >0 ) {
			    data.push($(this).val());
			    flag = 1;
			    }
			});

			var temp=data.join();
			var requestData= "bgAdminListOfBu="+temp;
			if(flag == 1){
			  $.ajax({
		          url: '/rms/dashboard/submitLoadManagerBillingStatus',
		          contentType : 'application/json; charset=utf-8',
		          dataType: 'json',
		          //processData: false,
		          data : {bgAdminListOfBu: temp},
		          success: function(data) {
		        	 var selecteddataBilling =data.length;
			        	  if(selecteddataBilling<=1){
			        	         $("#billing-status").css({"width": 1075});
			        	  }
			        	  else{
			        		  var resultwidth=300*selecteddataBilling;
			        		  $("#billing-status").css({"width": resultwidth});
			        	  }
			        	  

		          	myData=data;
		          	morrisBar.setData(myData);
		          	stopProgress();
		          },
		          error : function(e) {
						console.log("ERROR: ", e);
						stopProgress();
					}
		      }); 
			}else{
				$.ajax({
		            url: '/rms/dashboard/loadManagerBillingStatus',
		            contentType : 'application/json; charset=utf-8',
		            dataType: 'json',
		            //processData: false,
		            success: function(data) {
		           	 var onloaddataBilling =data.length;
		        		  var resultwidth=300*onloaddataBilling;
		        		  $("#billing-status").css({"width": resultwidth});
		        	  

		            	myData=data;
		            	morrisBar.setData(myData);
		            	stopProgress();
		            },

		            error : function(e) { 
					
						stopProgress();
					}
		        });
			}
				$(":checked").each(function() {
					if( $(this).val().length >0 ) {
				    data.push($(this).val());
				    flag = 1;
				    }
				});
		var temp=data.join();
				var requestData= "bgAdminListOfBu1="+temp;
			  if(flag == 1){
			  $.ajax({
		          url: '/rms/dashboard/submitLoadDeliveryManagerResourceBillingStatus',
		          contentType : 'application/json; charset=utf-8',
		          dataType: 'json',
		          //processData: false,
		          data : {bgAdminListOfBu1 :temp},
		          success: function(data) {
		        	  var selecteddataResourceBilling =data.length;
		        	  if(selecteddataResourceBilling<=1){
		        	         $("#resourcebilling-status").css({"width": 1075});
		        	  }
		        	  else{
		        		  var resultwidth=300*selecteddataResourceBilling;
		        		  $("#resourcebilling-status").css({"width": resultwidth});
		        	  }
		        	  
					myData=data;
		          	resourcebar.setData(myData);
		          	
		          },
		          error : function(e) {
						console.log("ERROR: ", e);
						
					}
		      });
			  }else{
				  $.ajax({
			           url: '/rms/dashboard/loadDeliveryManagerResourceBillingStatus',
			           contentType : 'application/json; charset=utf-8',
			           dataType: 'json',
			           //processData: false,
			           success: function(data) {
			        	   var onloadResourceBilling =data.length;
				           var resultwidth=300*onloadResourceBilling;
				           $("#resourcebilling-status").css({"width": resultwidth});


			           	resourcebar.setData(data);
			           	
			           },
			           error : function(e) {
							console.log("ERROR: ", e);

						}
			       });
			  }
			  var dataNew = [];
			  var dataToSend;
			  $(":checked").each(function() {
					if( $(this).val().length >0 ) {
				    dataNew.push($(this).val());
				    }
				});
			  
			 
			  
			  dataToSend=dataNew.join();
			  callWidget(dataToSend);
			  //alert("dataToSend: "+dataToSend);
			  $.ajax({
				  type: "GET",
				  url: '/rms/dashboard/savePreferences',
				   contentType : 'application/json; charset=utf-8',
		           dataType: 'json',
		           data : {bgbuid :dataToSend},
		           success: function(dataNew) {
		        	   //alert("Save Preference call success.");
		           },
		           error : function(e) {
						console.log("ERROR in save preference call: ", e);
                   }
			  });
			  
			  		  }
		  else{
			  alert("Please select one unit!!");
		  }
		});
	  
	  function callWidget(dataToSend){
		  //alert("Inside function");
		  var dataToSend = dataToSend;
		  $.ajax({
			   url: '/rms/dashboard/submitLoadCustomWidgetsStatus',
	           contentType : 'application/json; charset=utf-8',
	           dataType: 'json',
	           data : {bgbuid :dataToSend},
	           //processData: false,
	           success: function(dataNew) {
	        	
	        	   $('.pendingApproval').empty();
		      		$('.approved').empty();
		      		$('.rejected').empty();
		      		$('.compliance').empty();

	        	var pendingApproval="<ul class=\"nav nav-stacked\">";
	        	var approved="<ul class=\"nav nav-stacked\">";
	        	var rejected="<ul class=\"nav nav-stacked\">";
	        	var compliance="<ul class=\"nav nav-stacked\">";
	        	
	           	$.each(dataNew, function(i, item) {

	           		if(item.status=='compliance')
	           		{
	           		var linkVar = "<li class=\"item primary-items\"><a href=\"#\">WE -"+item.week_end_date+"<span class=\"pull-right badge bg-blue\">"+item.employee_count+"<\/span><\/a><\/li>";
	           		compliance += linkVar;
	           		} 
	           		if(item.status=='A')
          			{
	           		var linkVar = "<li class=\"item primary-items\"><a href=\"#\">WE -"+item.week_end_date+"<span class=\"pull-right badge bg-blue\">"+item.employee_count+"<\/span><\/a><\/li>";
          			approved += linkVar;
          			}
	           		if(item.status=='R')
          			{
	           		var linkVar = "<li class=\"item primary-items\"><a href=\"#\">WE -"+item.week_end_date+"<span class=\"pull-right badge bg-blue\">"+item.employee_count+"<\/span><\/a><\/li>";
          			rejected += linkVar;
          			}
	           		if(item.status=='P')
          			{
	           	/* 	var linkVar = "<li class=\"item primary-items\"><a href=\"#\">WE -"+item.week_end_date+"<span class=\"pull-right badge bg-blue\">"+item.employee_count+"<\/span><\/a><\/li>"; */
	           	var dateStr=item.week_end_date;
	           	var res=dateStr.toString().split("/");           //convert date into requir format yy/mm/dd
	            var date=res[2]+"-"+res[0]+"-"+res[1];
	           	var linkVar = "<li class=\"item primary-items\"><a href=\"/rms/timehrses?active=all&j=1&WeekEndDateHyper="+date+"\">WE -"+item.week_end_date+"<span class=\"pull-right badge bg-blue\">"+item.employee_count+"<\/span><\/a><\/li>";   
          			pendingApproval += linkVar;
          			}
	           		
	           		
               });
	           	
	           	pendingApproval += "<\/ul>";
	           	approved += "<\/ul>";
	           	rejected += "<\/ul>";
	           	compliance += "<\/ul>";
	           	
	      		$('.pendingApproval').append(pendingApproval);
	      		$('.approved').append(approved);
	      		$('.rejected').append(rejected);
	      		$('.compliance').append(compliance);
	           	
	           },
	           error : function(e) {
					console.log("ERROR: ", e);

				}
	       });

	  };
	  

function getWidget(){
	  //alert("Inside function");
	  
	  $.ajax({
		   url: '/rms/dashboard/submitLoadCustomWidgetsStatus',
         contentType : 'application/json; charset=utf-8',
         dataType: 'json',
         
         //processData: false,
         success: function(dataNew) {
      	//alert("success");
      	   $('.pendingApproval').empty();
	      		$('.approved').empty();
	      		$('.rejected').empty();

	      		$('.compliance').empty(); 
	 
      	var pendingApproval="<ul class=\"nav nav-stacked\">";
      	var approved="<ul class=\"nav nav-stacked\">";
      	var rejected="<ul class=\"nav nav-stacked\">";
      	var compliance="<ul class=\"nav nav-stacked\">";
      	

         	$.each(dataNew, function(i, item) { 

         		if(item.status=='compliance')
         		{
         		var linkVar = "<li class=\"item primary-items\"><a href=\"#\">WE -"+item.week_end_date+"<span class=\"pull-right badge bg-blue\">"+item.employee_count+"<\/span><\/a><\/li>";
         		compliance += linkVar;
         		} 
         		if(item.status=='A')
    			{
         		var linkVar = "<li class=\"item primary-items\"><a href=\"#\">WE -"+item.week_end_date+"<span class=\"pull-right badge bg-blue\">"+item.employee_count+"<\/span><\/a><\/li>";
    			approved += linkVar;
    			}
         		if(item.status=='R')
    			{
         		var linkVar = "<li class=\"item primary-items\"><a href=\"#\">WE -"+item.week_end_date+"<span class=\"pull-right badge bg-blue\">"+item.employee_count+"<\/span><\/a><\/li>";
    			rejected += linkVar;
    			}
         		if(item.status=='P')
    			{
         		var linkVar = "<li class=\"item primary-items\"><a href=\"#\">WE -"+item.week_end_date+"<span class=\"pull-right badge bg-blue\">"+item.employee_count+"<\/span><\/a><\/li>";
    			pendingApproval += linkVar;
    			}
         		
         		
         });
         	
         	pendingApproval += "<\/ul>";
         	approved += "<\/ul>";
         	rejected += "<\/ul>";
         	compliance += "<\/ul>";
         	
    		$('.pendingApproval').append(pendingApproval);
    		$('.approved').append(approved);
    		$('.rejected').append(rejected);
    		$('.compliance').append(compliance);
         	
         },
         error : function(e) {
				console.log("ERROR: ", e);

			}
     });

};

// Notification animation progressbar
function moveProgressBar() {
			  var width = 1; 
		
			  var id = setInterval(frame, 70); 
		
			  function frame() {
			    if (width >= 100) {
			      clearInterval(id);
			    } else {
			      width++; 
			      document.getElementsByClassName("myBar4")[0].style.width = width + '%'; 
			      document.getElementsByClassName("myBar5")[0].style.width = width + '%';
			    }
			  }
			};
		moveProgressBar();
	 
		setTimeout(function(){   
			$('.notification-bar').css('display', 'none'); 
		}, 12000);
		
		//Progress bar for notifications
		$(".toast-close").on('click',function(){
			$(this).parent().css('display','none');
				
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
$('#newMsg').bind("keypress", function (event) {
	var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	if(regExForMsgTxtFailed(key)){
	        event.preventDefault();
	        return false;
	    }
 });
</script>

<!--END: Alert: Added by Pratyoosh Tripathi -->

<div class="notification-bar">
<!-- <div class="close closeIconPosition">close</div> -->
<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
  <!-- <div class="notification-text">Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.</div> -->
  <sec:authorize
				access="hasAnyRole('ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
				<div class="notification-text">You can directly navigate to your pending timesheets on clicking at Pending For Approval.</div>	
				<div class="myProgress">
			  <div class="myBar myBar4"></div>
			</div>		
	</sec:authorize>
	
  <sec:authorize
				access="hasAnyRole('ROLE_USER','ROLE_HR','ROLE_ADMIN')">
				<div class="notification-text">You can directly navigate to your timesheet on clicking at To Do List and My Rejected Timesheet.</div>	
				<div class="myProgress">
			  <div class="myBar myBar4"></div>
			</div>		
	</sec:authorize>
</div>

<sec:authorize
				access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
<div class="notification-bar notification2">
<!-- <div class="close closeIconPosition">close</div> -->
<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
  <!-- <div class="notification-text">Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.</div> -->

				<div class="notification-text">You can configure/set activity, project module and mail setting  from Configurations link.</div>			
	
  <div class="myProgress">
			  <div class="myBar myBar5"></div>
			</div>
</div>
</sec:authorize>
   <style>
   .notification-bar{
   width: 400px;
   background: rgba(30, 159, 180,.8);
padding: 10px;
position: fixed;
bottom: 30px;
right: 25px;
   
   }
   .notification2
   {
      width: 400px;
   background: rgba(30, 159, 180,.8);
padding: 10px;
position: fixed;
bottom: 100px;
right: 25px;
   }
   .closeIconPosition{    position: absolute;
    top: -10px;
    right: -20px;}
    
    .toast-close {
    float: right;
    display: block;
    width: 13px;
    height: 13px;
    padding: 0px 4px;
    border-radius: 3px;
    background: #000 url(../resources/images/cross_icon_white.png) no-repeat center !important;
    z-index: 1000;
    position: absolute;
    top: -5px;
    right: -5px;
    opacity: .7 !important;
}

.notification-text{    font-size: 14px;
    color: #fff;
    text-align: justify;}
    
   </style>
  <!--END: Alert: Added by Pratyoosh Tripathi -->
  
  
  
   
   
 
		
</body>
</html>
