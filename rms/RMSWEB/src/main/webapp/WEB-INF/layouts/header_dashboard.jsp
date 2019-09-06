<%@page import="org.yash.rms.util.UserUtil"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<spring:url value="/editProfile/showProfile" var="editProfile" />
<spring:url value="/help" var="help" />
<spring:url value="/releasenotes" var="releasenotes" />
<spring:url value="/resources/j_spring_security_check" var="form_url" />
<spring:message code="application_js_version" var="app_js_ver" htmlEscape="false" />
<script async type="text/javascript" src="/rms/resources/js/switch-theme.js"></script>

<style>

#evalForm #j_username {
width:0px !important;
}
#platform_img {
	border-radius: 11px !important;
	width: "150" !important;;
	height: "35" !important;;
} 
.os-download-img {
	float: right !important;
	margin-top: 7px;
	margin-right: 14px;
} 
#camera-image {
	width: 150px;
	height: 150px; 
} 
.user-status {
	position: absolute;
	background-color: green;
	width: 15px;
	height: 15px;
	border-radius: 50%;
	top: 11px;
	left: 180px;
} 
.user-status:hover {
	width: 60px;
} 
.user-image-status {
	position: relative;
} 
.status-icon {
	display: none;
	color: #fff;
} 
.user-status:hover .status-icon {
	display: block;
}
</style>

<header class="main-header">
	<!-- Logo -->
	<sec:authorize
		access="hasAnyRole('ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
		<c:set var="flag" value="1" />
	</sec:authorize>

	<sec:authorize
		access="hasAnyRole('ROLE_USER','ROLE_HR','ROLE_SEPG_USER','ROLE_ADMIN')">
		<c:set var="flag" value="0" />
	</sec:authorize>  
	<span class="logo"> <span class="logo-lg pull-left"> RMS<sub
			class="sub-ver">ver 5.0</sub>
	</span> <a href="javascript:void(0);" class="sidebar-toggle pull-right"
		data-toggle="offcanvas" role="button"> <i style="font-size: 20px;"
			class="fa fa-bars"> </i>
	</a>
	</span>

	<!-- Header Navbar: style can be found in header.less -->
	<nav class="navbar navbar-static-top" role="navigation">
		<!-- Sidebar toggle button-->
		<div class="yash_logo"><img src="/rms/resources/images/yash_logo.svg" alt="Yash" /></div>
		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav navbar-left">

			<li>
				<a href="javascript:void(0);" class="dropdown-toggle" title="Choose theme"
				data-toggle="dropdown"> <i class="fa fa-paint-brush headerIcon"></i>
				</a>
				<ul class="dropdown-menu usefulMenu theme-switcher">
					<li class="clearfix">
						Choose Theme
						<br />
						<label class="white" onclick="changeTheme('white_theme')"></label>
						<label class="blue" onclick="changeTheme('blue_theme')"></label>
						<label class="green" onclick="changeTheme('green_theme')"></label>
						<label class="black" onclick="changeTheme('black_theme')"></label>
						<label class="cyan" onclick="changeTheme('cyan_theme')"></label>
						<label class="red" onclick="changeTheme('red_theme')"></label>
						<label class="lightgreen" onclick="changeTheme('lightgreen_theme')"></label>
						<label class="purple" onclick="changeTheme('purple_theme')"></label>
					</li>
				</ul>
			</li> 
				<li>
					<a href="javascript:void(0);" class="help" title="RMS Guide">
						<i class="fa fa-question headerIcon"></i>
					</a>	
				</li> 
				<li><a href="javascript:void(0);" class="dropdown-toggle" title="Useful Links"
					data-toggle="dropdown"> <i class="fa fa-external-link headerIcon"></i>
				</a>
					<ul class="dropdown-menu usefulMenu">
						<li><a id="iconnectId" class="iconnect"
							href="javascript:void(0);">IConnect</a></li>
						<li><a id="youPortalId" class="youPortal"
							href="javascript:void(0);">You Portal</a></li>
					</ul>
				</li>
		
		<li> 
		    <a href="/rms/contactus" title="Write Us"> 
		      <i class="fa fa-envelope-o headerIcon"></i>
		      <span class="badge" id="theme_badge">NEW</span> 
		    </a>
		</li>
		  
				<!-- User Account: style can be found in dropdown.less -->
				<li class="dropdown user user-menu" id="user-profile"><a
					href="javascript:void(0);" onclick="openNav()"> <span
						class="hidden-xs nameDesig"> <span class="headeruserName">${firstName}</span>
							<span class="designation" id="designation">${ROLE}</span>
					</span> <c:choose>
							<c:when test="${not empty UserImage }">
								<img src="data:image/jpeg;base64,${UserImage}"
									class="user-image" alt="User Image">
							</c:when>
							<c:otherwise>
								<img
									src="/rms/resources/dashboardscript/dist/img/User-Image.png"
									class="user-image" id="user-image" alt="User Image">
							</c:otherwise>
						</c:choose>
				</a>
					<div id="mySidenav" class="sidenavprofile profile-right-sidebar">
						<!-- User image -->
						<div class="profile-bar-top">
							<a href="javascript:void(0);" class="arrow-btn"
								onclick="closeNav()"> <i class="fa fa-long-arrow-right"
								aria-hidden="true"></i>
							</a>
						</div>

						<div class="user-header user-image-status">
							<c:choose>
								<c:when test="${not empty UserImage }">
									<img src="data:image/jpeg;base64,${UserImage}"
										class="img-circle" alt="User Image">
								</c:when>
								<c:otherwise>
								<div id="profile-image">
									<img
										src="/rms/resources/dashboardscript/dist/img/User-Image.png"
										id="profile-image" class="img-circle" alt="User Image"
										>

									<img
										src="/rms/resources/dashboardscript/dist/img/camera-image.png"
										id="camera-image" class="img-circle" alt="User Image">
										</div>
								</c:otherwise>
							</c:choose>
					 
							<p class="profile-bar-name">${firstName}</p>
							<p class="profile-bar-designation">${designationName}</p>
						 
						</div>
						<!-- User other details -->

						<div class="user-other-details">
							<p>
								<span class="details-title">Current BU</span> <span
									class="details-txt"> <span class="text-left"><%=UserUtil.getCurrentResource().getOstVisibility()%></span>
								</span> 
							</p>
							<p>
								<span class="details-title">Parent BU</span> <span
									class="details-txt"><span class="text-left"><%=UserUtil.getCurrentResource().getOstVisibility()%></span>
								</span> 
							</p>
							<p>
								<span class="details-title">Current Location</span> <span title="<%=UserUtil.getCurrentResource().getDeploymentLocation()%>"
									class="details-txt"><%=UserUtil.getCurrentResource().getDeploymentLocation()%></span>
							</p>
							<p>
								<span class="details-title">Base Location</span> <span title="<%=UserUtil.getCurrentResource().getLocation()%>"
									class="details-txt"><%=UserUtil.getCurrentResource().getLocation()%></span>
							</p> 
						</div>
						<!-- Menu Footer-->
						<div class="user-footer">
							<ul>
								<li> 
									 <a href="/rms/editProfile/showProfile">
										<i class="fa fa-pencil"></i> Edit Profile
									</a> 
							
								</li>
								
								<li><a href="javascript:void(0);" class="releaseNotes">
										<i class="fa fa-file-text-o"></i> Release Notes
								</a> <li>
					 <a href="<c:url value="${pageContext.request.contextPath}/j_spring_security_logout"/>">
										<i class="fa fa-sign-out" aria-hidden="true"></i>
										Sign out</a>
								</li>
							</ul>
							 
						</div>
					</div>
				</li>
			</ul>
		</div>
		<!-- We have remove Simulate for Role (ROLE_DEL_MANAGER,ROLE_BG_ADMIN) -->
		<div class="simulate_search">
			<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
				<div class='navbar-form '>
					<form id="evalForm" method="post" class='form-inline'>
						<div class="positionRel pull-left">
								<select name="j_username" id="j_username"
								class="required comboselect brdrDd form-control"
								placeholder="Type to filter">
										<option value=""></option>
										<c:forEach var="resourcesList" items="${allResources}">
											<c:choose>
												<c:when test="${not empty resourcesList[2]}">
			
													<option value="${resourcesList[4]}">${resourcesList[1]}
														${resourcesList[2]} ${resourcesList[3]}</option>
												</c:when>
												<c:otherwise>
													<option value="${resourcesList[4]}">${resourcesList[1]}
														${resourcesList[3]}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select> 
						</div>
						
						<input id="j_password" type="hidden" name='j_password' /> 
						<input type="button" class="btn-default simulateBtn"
							value="Simulate"
							title="simulate user after entering window user name"
							onclick="evaluateUser();" />
					</form>
				</div>
			</sec:authorize>
		</div>
	</nav>
	<div id="sessionTimoutMessageBox" style="display: none;"
		class="AccNumDiv CustomPopupBox">
		<table width="80%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="dataTbl display tablesorter addNewRow">
			<tr>
				<td align="left" valign="top">
					<table width="100%" border="0" align="center" valign="top"
						cellpadding="1" cellspacing="0" bgcolor="#d3d3d3">
						<tr>
							<td colspan="3" class="botMargin"><b>Session Expire
									Warning</b></td>
						</tr>
						<tr valign="bottom">
							<td colspan="3" bgcolor="#FFFFFF" align="center"><font><div
										id="sessionTimoutMessage" align="center">Your session
										will expire in 2 minutes?</div></font></td>
						</tr>
						<tr valign="bottom">
							<td colspan="3" bgcolor="#FFFFFF">
								<table border="0" width="100%" class="CustomPopupBoxBtns">
									<tr>
										<td bgcolor="#FFFFFF" width="23%" align="center"></td>
										<td bgcolor="#FFFFFF" width="25%" align="center"><input
											type="button" id="yesButton" value="  Yes  "
											onclick="resetSessionTime();"></td>
										<td bgcolor="#FFFFFF" width="4%" align="center"></td>
										<td bgcolor="#FFFFFF" width="25%" align="center"><input
											type="button" id="noButton" value="  No  "
											onclick="endSession();"></td>
										<td bgcolor="#FFFFFF" width="23%" align="center"></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</header>

<script type="text/javascript" charset="utf-8">
	$(document).ready(
			function() {
				$(".editProfile").fancybox(
						{
							//fitToView	: false,
							autoSize : false,
							closeClick : false,
							//autoScale   : true,
							autoDimensions : true,
							transitionIn : 'fade',
							transitionOut : 'fade',
							openEffect : 'easingIn',
							closeEffect : 'easingOut',
							type : 'iframe',
							href : '${editProfile}',
							'width' : '100%',
							preload : true,
							beforeShow : function() {
								var thisH = this.height - 35;
								$(".fancybox-iframe").contents().find('html').find(".midSection").css('height', thisH);
							},
							helpers : {
								overlay : {
									closeClick : false
								}
							// prevents closing when clicking OUTSIDE fancybox 
							},
							afterClose : function() {
								var href = window.location.pathname;
								if (href.toLowerCase()
										.indexOf("admindashboard") >= 0
										|| href.toLowerCase().indexOf(
												"userdashboard") >= 0) {
									window.location.reload();
								}
							}

						});


	$(document).ready(function() { 
		 
				$(".help").fancybox(
						{
							//fitToView	: false,
							autoSize : false,
							closeClick : true,
							//autoScale   : true,
							autoDimensions : true,
							transitionIn : 'fade',
							transitionOut : 'fade',
							openEffect : 'easingIn',
							closeEffect : 'easingOut',
							type : 'iframe',
							href : '${help}',
							'width' : '20%',
							'height' : '30%',
							preload : true,
							/*'height':400,*/
							beforeShow : function() {
								var thisH = this.height - 35;
								$(".fancybox-iframe").contents().find('html')
										.find(".midSection").css('height',
												thisH);
							}


						});
				$(".releaseNotes").fancybox(
						{
							//fitToView	: false,
							autoSize : false,
							closeClick : true,
							//autoScale   : true,
							autoDimensions : true,
							transitionIn : 'fade',
							transitionOut : 'fade',
							openEffect : 'easingIn',
							closeEffect : 'easingOut',
							type : 'iframe',
							href : '${releasenotes}',
							'width' : '100%',
							preload : true,
							//'height':400,
							beforeShow : function() {
								var thisH = this.height - 35;
								$(".fancybox-iframe").contents().find('html')
										.find(".midSection").css('height',
												thisH);
							}

						});

				$(".iconnect").on("click", function() {
					window.open('https://iconnect.yash.com', '_blank');
				});


		$(".youPortal").on("click", function() {
			window.open('https://youportal.yash.com/', '_blank');
		});
		
		
          
	});

				$(".youPortal").on("click", function() {
					window.open('https://youportal.yash.com/', '_blank');
				});
			});
	 
	function trim(sString) {
		sString = sString.replace(/^ */g, "");
		sString = sString.replace(/ *$/g, "");
		return sString;
	}

	function submitOnEnter(inputElement, event) {
		if (event.keyCode == 13) { // No need to do browser specific checks. It is always 13.  
			var resource = $("#j_username").val();
			resource = trim(resource);
			if (resource == null || resource == '')
				return;
			else {
				$("#j_password").val(resource);
				document.getElementById("evalForm").action = "${fn:escapeXml(form_url)}";
				$("#evalForm").submit();
			}
		}
	}
	function evaluateUser() {
		var resource = $("#j_username").val();
		resource = trim(resource);
		if (resource == null || resource == '')
			return;
		else {
			resource = resource.toLowerCase();
			$("#j_password").val(resource);
			document.getElementById("evalForm").action = "${fn:escapeXml(form_url)}";
			$("#evalForm").submit();
		}
	}
</script> <script type="text/javascript" charset="utf-8">
	var fiveMinInMilisecond = 300000;
	var timeOutTime = ((
<%=session.getMaxInactiveInterval()%>
	) * 1000)
			- fiveMinInMilisecond;
	var timeout = timeOutTime;
	// recursivly after 30 minute
	var intervalTimer;
	var timerIds = [];
	// recursivly after each second for watch
	var timer;
	intervalTimer = setInterval(function() {
		popUp();
	}, timeout);

	function startTimer(timeoutCount) {
		if (timeoutCount <= 0) {
			endSession();
		} else {
			var min;
			var sec;
			if (timeoutCount > 60) {
				min = parseInt(timeoutCount / 60);
				sec = ((timeoutCount) % (60));
			} else {
				min = 0;
				sec = timeoutCount;
			}
			if (sec < 10) {
				document.getElementById('sessionTimoutMessage').innerHTML = '<center>Your session will expire in 0'
						+ (min)
						+ ':0'
						+ (sec)
						+ ' minutes, would you like to continue ? </center><br>';
			} else {
				document.getElementById('sessionTimoutMessage').innerHTML = '<center>Your session will expire in 0'
						+ (min)
						+ ':'
						+ (sec)
						+ ' minutes, would you like to continue ? </center><br>';
			}
		}
		timer = setTimeout(function() {
			startTimer(timeoutCount - 1);
		}, '1000');
		timerIds.push(timer);

	}

	function clearTimers() {
		for (var i = 0; i < timerIds.length; i++) {
			clearTimeout(timerIds[i]);
		}
		timerIds = [];
	}

	function closePopUp() {
		document.getElementById("sessionTimoutMessageBox").style.display = "none";
	}

	function popUp() {
		var test = window.location.href;

		var result = test.indexOf("logout", 0);
		if (result < 0) {
			document.getElementById("sessionTimoutMessageBox").style.display = "block";
			startTimer(fiveMinInMilisecond / 1000);
		}
	}

	function endSession() {
		closePopUp();
		window.location.href = '${pageContext.request.contextPath}'
				+ "/j_spring_security_logout";
	}

	function resetSessionTime() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				closePopUp();
				clearInterval(intervalTimer);
				clearTimers();
				intervalTimer = setInterval(function() {
					popUp();
				}, timeout);
			}
		}
		var url = '${pageContext.request.contextPath}'
				+ "/resources?userAction=ajaxCallForSessionTimeout";
		xmlhttp.open("GET", url, true);
		xmlhttp.send();
	}

	//
	function openNav() {
		document.getElementById("mySidenav").style.right = "0";
		document.getElementById("user-profile").style.background = "rgba(230, 230, 230, 0.95)";
		document.getElementById("designation").style.color = "#333333";
		document.getElementById("user-image").style.display = "none";
	}

	function closeNav() {
		document.getElementById("mySidenav").style.right = "-300px";
		document.getElementById("user-profile").style.background = "#ffffff";
		document.getElementById("designation").style.color = "#cccccc";
		document.getElementById("user-image").style.display = "block";

	}
</script>