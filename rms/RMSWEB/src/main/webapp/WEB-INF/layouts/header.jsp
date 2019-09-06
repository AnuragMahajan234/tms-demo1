<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<spring:url value="/resources/j_spring_security_check" var="form_url" />



<div class="headSection">
	<div class="midSection">

		<div class="header">
			<div class="logo">
			<sec:authorize
			access="hasAnyRole('ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
			<a href="<c:url value="/dashboard/admindashboard"/>">
			</sec:authorize>
			
			<sec:authorize
   				access="hasAnyRole('ROLE_USER','ROLE_HR','ROLE_SEPG_USER','ROLE_ADMIN')">
   				<a href="<c:url value="/dashboard/userdashboard"/>">   
   				 <img src="${pageContext.request.contextPath}/resources/images/logo.jpg" width="129" height="59" />
    			</a>
    		</sec:authorize>
     
			</div>
			<div class="fl compName">
				Resource Management System <span>
				<spring:message var="version" code="application_version">${version}</spring:message>
				<spring:message var="env" code="ENVIORNMENT">${env}</spring:message></span>
			</div>

			<div class="fr loginInfo">
				<sec:authorize access="isAuthenticated()">
					<sec:authorize
						access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_BEHALF_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_USER','ROLE_HR','ROLE_SEPG_USER')">
						<div class="userName">
							Welcome, <span><sec:authentication
									property="principal.employeeName" /></span>
						</div>
						<div class="userRole" style="display: none">
							<span><sec:authentication
									property="principal.authorities[0]" /></span>
						</div>
						<div style="margin-top: 18px; text-align: right;">
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_BEHALF_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_SEPG_USER')">
								<a href="/rms" title="Home" style="text-decoration: none;">
									<img class="navHomeIcon" width="16" height="22"
									src="${pageContext.request.contextPath}/resources/images/Home.png"}">
								</a>|
                          <!--  Bug 103 in redmine -->
								
                          </sec:authorize>
	<spring:url value="/releasenotes" var="releasenotes" />
								<a href="" class="releaseNotes">Release Notes</a>|
							<spring:url value="/help" var="help" />
							<a href="" class="help">Help</a>

							<spring:url value="/editProfile/showProfile" var="editProfile" />
							| <a href="#" class="editProfile" onclick="">Edit Profile</a> |
							<%--   <spring:url value="/logout" var="logout"/>
					     | <a href="${logout}">
					        <spring:message code="security_logout"/>
					      </a> --%>

							<a
								href="<c:url value="${pageContext.request.contextPath}/j_spring_security_logout"/>">
								Logout</a> <br />
						</div>

					</sec:authorize>
				</sec:authorize>


			</div>


		</div>


		<div class="clear"></div>


	</div>

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
							<td colspan="3" bgcolor="#FFFFFF" align="center"><font><div id="sessionTimoutMessage" align="center">Your session will expire in 2 minutes?</div></font></td>
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

	<div class="botSection">
		<div class="fl left"></div>
		<div class="fr right"></div>
		<div class="mid"></div>
		<div class="clear"></div>
	</div>
</div>
<script type="text/javascript" charset="utf-8">
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
</script>
<script type="text/javascript" charset="utf-8">
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
	$(document).ready(
			function() {

				$(".editProfile").fancybox(
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
							href : '${editProfile}',
							'width' : '100%',
							preload : true,
							/*'height':400,*/
							beforeShow : function() {
								var thisH = this.height - 35;
								$(".fancybox-iframe").contents().find('html')
										.find(".midSection").css('height',
												thisH);
							}

						});

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
							/*'height':400,*/
							beforeShow : function() {
								var thisH = this.height - 35;
								$(".fancybox-iframe").contents().find('html')
										.find(".midSection").css('height',
												thisH);
							}

						});
			});
</script>