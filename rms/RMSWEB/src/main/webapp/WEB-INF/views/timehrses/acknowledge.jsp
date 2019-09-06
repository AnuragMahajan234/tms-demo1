<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}" var="style_css"/>
	<link href="${style_css}" rel="stylesheet" type="text/css"></link>

<spring:url value="resources/js-framework/weeklycalendar.js?ver=${app_js_ver}"
	var="jquery_weeklycalendar_js" />
<spring:url value="resources/js-framework/date.js?ver=${app_js_ver}" var="jquery_date_js"/>


<div class="content-wrapper">
<div class="mid_section">
	<!--right section-->
	<div class="botMargin">
		<h1 class="fl">Timesheet Approval</h1>
		<div class="fr positionRel">
			<img src="resources/images/helpIcon.png" id="helpIcon" />
			<div class="helpContent" style="display: none;">
				<h3>To approve/review/reject time sheet.</h3>
				<table>
					<tr>
						<td>
							<ol class="orderList">
								<li>Click on employee id.</li>
								<li>Make sure your employee had submitted his timsheet.</li>
								<li>Edit and populate planned, billed hours and remarks.</li>
								<li>you can now see <img src="resources/images/review_icon.png"/> to Approve time sheet.</li>
								<li>you can now see &nbsp <img src="resources/images/reject_timesheet.png" height="16" width="16"> &nbsp to Reject time sheet.</li>
								<li><font color="red">Not able to see <img src="resources/images/review_icon.png"/>: Check if employee had submitted timesheet.</font></li>
								<li><font color="red">Review timesheet : Timesheet can be marked review only by admins. Please consult your admin.</font></li>
								
								<li><font color="red"> Not able to see <img src="resources/images/reject_timesheet.png" height="16" width="16"/> if user has not submitted timesheet.</font></li>
							</ol>
						</td>
					</tr>
				</table>
				<div class="closeHelp"></div>
				<img src="resources/images/arrowIcon.png" class="arrowIcon" />
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<div class="tab_seaction">
		
		<div id="div" >
		
			<table id="example">
			<tr>
				<td>
					<c:if test="${not empty status}">${status} </c:if>
					<c:if test="${empty status}">Timesheet of user: ${user.employeeId.employeeName} for weekEnd date: ${user.weekEndDate} is ${timesheetStatus}  
					 	<%-- <c:if test="${user.status} == A">Approved</c:if>
						<c:if test="${user.status} == R">Rejected</c:if>  --%>
					</c:if>
				</td>
			</tr>
			</table>
			
		</div>

	</div>
	<!--right section-->
</div>
</div>