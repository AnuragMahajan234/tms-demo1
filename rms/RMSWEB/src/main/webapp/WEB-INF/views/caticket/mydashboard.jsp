<%@ page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<spring:url
	value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}"
	var="multiselect_js" />
<script src="${multiselect_js}" type="text/javascript"></script>


<spring:url
	value="/resources/js-framework/datetimepicker_css.js?ver=${app_js_ver}"
	var="datetimepicker_css" />
<script src="${datetimepicker_css }" type="text/javascript"></script>


<spring:url
	value="/resources/js-framework/jquery.datetimepicker.js?ver=${app_js_ver}"
	var="datetimepicker_js" />
<script src="${datetimepicker_js }" type="text/javascript"></script>
<spring:url value="/resources/styles/jquery.datetimepicker.css"
	var="datetimepicker_style_css" />
<link href="${datetimepicker_style_css}" rel="stylesheet"
	type="text/css"></link>

<spring:url
	value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}"
	var="multiselect_filter_js" />

<%-- <spring:url value="/resources/styles/themes/default/style.min.css" var="style_css"/>
<link href="${style_css}" rel="stylesheet" type="text/css"></link>

<spring:url value="/resources/js-framework/jstree/jquery.jstree.js"
	var="js_tree" />
	<spring:url value="/resources/js-framework/jstree.min.js"
	var="jstree_min_js" />
<script src="${js_tree}" type="text/javascript"></script>
<script src="${jstree_min_js}" type="text/javascript"></script> --%>
<spring:url
	value="/resources/styles/skin/ui.dynatree.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<!-- <link href="resources/styles/skin/ui.dynatree.css" rel="stylesheet" type="text/css" id="skinSheet"> -->
<spring:url
	value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url
	value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />
<spring:url
	value="resources/js-framework/jquery.cookie.js?ver=${app_js_ver}"
	var="js_cookie" />
<spring:url
	value="/resources/js-framework/jquery.dynatree.js?ver=${app_js_ver}"
	var="js_dynatree" />
<script src="${js_cookie}" type="text/javascript"></script>
<script src="${js_dynatree}" type="text/javascript"></script>

<!--   <script src="resources/js-framework/jquery.cookie.js" type="text/javascript"></script>
  <script src="resources/js-framework/jquery.dynatree.js" type="text/javascript"></script> -->

<script src="${multiselect_filter_js}" type="text/javascript"></script>
<style type="text/css" title="currentStyle">
thead input {
	width: 100%
}

input.search_init {
	color: #999
}
</style>
<script>
  $(function(){
    $("#myTicketDetailsByPendingStatusId").dataTable();
    $("#teamTicketDetailsByPendingStatusId").dataTable();
  })
  
  function checkFilterValues(filter){
	  var priority = $('#priorityFilter').val();
	  var module = $('#moduleFilter').val();
	  var reviewer = $('#reviewerFilter').val();
	  var landscape = $('#landscapeFilter').val();
	  var region = $('#regionFilter').val();
	  var assignee = $('#asigneeFilter').val();
	  var fromTime = $('#fromPeriod').val();
	  var toTime = $('#toPeriod').val();
	 /*  alert("from TIME :: "+fromTime);
	  alert("to TIME :: "+toTime); */
	  if(fromTime == ""){
		  if(toTime != ""){
			  alert("Please select from time");
			  return false;
		  }
	  }
	  if(priority == "-1" && module == "-1" && reviewer == "-1" && landscape == "-1" && 
			  region == "-1" && assignee == "-1" && fromTime=="" && toTime=="" ){
		  return false;
	  }
  }
  </script>
<div class="mid_section">
	<h1>Dashboard</h1>
	<br>

	<div class="center_div">
		<div style="float: left;" class="addNewContribution">
			<span class="addNewContainer"><a href="createTicketHome"
				class="blue_link" id="addNewContrbutionId"><img
					src="${pageContext.request.contextPath}/resources/images/addUser.gif"
					width="16" height="22"> Add New </a></span>
		</div>
		<div class="form" style="width: 1290px;">
			<form:form method="post" id="dashboardFilterId"
				name="dashboardFilter" modelAttribute="dashboardFilter"
				action="${pageContext.request.contextPath}/caticket/dashboardFilter">
				<input type="submit" name="filter" value="Filter"
					onClick="return checkFilterValues(this)"
					style="background-color: #d3d3d3; color: #37B; float: right;" />
				<table cellspacing="10" cellpadding="10">
					<tr>
						<td width="10%">Priority</td>
						<td><select name="priority" id="priorityFilter"
							class="required comboselect check">
								<option value="-1">Please Select</option>
								<option value="1 - Urgent">1 - Urgent</option>
								<option value="2 - High">2 - High</option>
								<option value="3 - Medium">3 - Medium</option>
								<option value="4 - Low">4 - Low</option>
								<option value="5 - Project">5 - Project</option>
						</select></td>
						<td>Module</td>
						<td><select name="module" id="moduleFilter"
							class="required comboselect check">
								<option value="-1">Please Select</option>
								<c:forEach var="project" items="${projects}">
									<option value="${project.id}">${project.moduleName}</option>
								</c:forEach>
						</select></td>
						<sec:authorize
							access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN', 'ROLE_MANAGER')">
							<td>Reviewer/IRM Name</td>
							<td><select name="reviewer" id="reviewerFilter"
								class="required comboselect check">
									<option value="-1">Please Select</option>
									<c:forEach var="resource" items="${resources}">
										<option value="${resource.employeeId}">${resource.employeeName}</option>
									</c:forEach>
							</select></td>
						</sec:authorize>
					</tr>
					<tr>
						<td width="10%">LandScape</td>
						<td><select name="landscape"
							class="required comboselect check" id="landscapeFilter">
								<option value="-1">Please Select</option>
								<c:forEach var="landscape" items="${landscapes}">
									<option value="${landscape.id}">${landscape.landscapeName}</option>
								</c:forEach>
						</select></td>
						<td width="10%">Region</td>
						<td><select name="region" id="regionFilter"
							class="required comboselect check">
								<option value="-1">Please Select</option>
								<c:forEach var="region" items="${regionNames}">
									<option value="${region}">${region}</option>

								</c:forEach>

						</select></td>
						<sec:authorize
							access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_MANAGER')">
							<td>Assignee Name</td>
							<td><select name="assignee" id="asigneeFilter"
								class="required comboselect check">
									<option value="-1">Please Select</option>
									<c:forEach var="resource" items="${resources}">
										<option value="${resource.employeeId}">${resource.employeeName}</option>
									</c:forEach>
							</select></td>
						</sec:authorize>
					</tr>
					<tr>
						<td>From Time Period</td>
						<td><input type="Text" id="fromPeriod" name="fromTime"
							maxlength="25" size="25"
							onclick="javascript:NewCssCal ('fromPeriod','yyyyMMdd','arrow')" />
						</td>
						<td>To Time Period</td>
						<td><input type="Text" id="toPeriod" name="toTime"
							maxlength="25" size="25"
							onclick="javascript:NewCssCal ('toPeriod','yyyyMMdd','arrow')" />
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
	<br>

	<sec:authorize
		access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_MANAGER')">
		<div class="center_div">
			<h1 style="font-size: 15px; color: #669999;">My Team Performance</h1>
			<br>
			<table class="dataTbl display tablesorter dataTable"
				id="ticketDetailTable"
				style="margin-top: 0px; margin-left: 0px !important;">
				<thead>
					<tr>
						<th style="background-color: #669999;" align="center"
							valign="middle" rowspan="2">Ticket Assigned</th>
						<th style="background-color: #669999;" align="center"
							valign="middle" rowspan="2">Open</th>
						<th style="background-color: #669999;" align="center"
							valign="middle" colspan="2">Total Resolved</th>
						<th style="background-color: #669999;" align="center"
							valign="middle" rowspan="2">SLA %</th>
						<th style="background-color: #669999;" align="center"
							valign="middle" colspan="2">Aging</th>
						<th style="background-color: #669999;" align="center"
							valign="middle" colspan="2">Project</th>
						<th style="background-color: #669999;" align="center"
							valign="middle" colspan="3">Problem Management</th>
						<th style="background-color: #669999;" align="center"
							valign="middle" colspan="3">Resolution Time (In Days)</th>
					</tr>
					<tr style="background-color: #669999;">
						<th style="background-color: #669999;">Yes</th>
						<th style="background-color: #669999;">No</th>
						<th style="background-color: #669999;">Resolved</th>
						<th style="background-color: #669999;">Open</th>
						<th style="background-color: #669999;">Resolved</th>
						<th style="background-color: #669999;">Open</th>
						<th style="background-color: #669999;">Proposed</th>
						<th style="background-color: #669999;">Justified</th>
						<th style="background-color: #669999;">Justified Crop</th>
						<th style="background-color: #669999;">Urgent</th>
						<th style="background-color: #669999;">High</th>
						<th style="background-color: #669999;">Medium/Low</th>
					</tr>
				</thead>
				<tbody id="ticketDetailTableBody">
					<c:forEach var="myTeamDashboardPerformance"
						items="${myTeamDashboardPerformanceList }">
						<td align="center">${myTeamDashboardPerformance.totalAssigned }</td>
						<td align="center">${myTeamDashboardPerformance.totalOpen }</td>
						<td align="center">${myTeamDashboardPerformance.totalResolvedYes }</td>
						<td align="center">${myTeamDashboardPerformance.totalResolvedNo }</td>
						<td align="center">${myTeamDashboardPerformance.slaMissed }</td>
						<td align="center">${myTeamDashboardPerformance.agingResolved }</td>
						<td align="center">${myTeamDashboardPerformance.agingOpen }</td>
						<td align="center">${myTeamDashboardPerformance.projectResolved }</td>
						<td align="center">${myTeamDashboardPerformance.projectOpen }</td>
						<td align="center">${myTeamDashboardPerformance.problemManagementProposed }</td>
						<td align="center">${myTeamDashboardPerformance.problemManagementJustified }</td>
						<td align="center">${myTeamDashboardPerformance.problemManagementJustifiedCrop }</td>
						<td align="center">${myTeamDashboardPerformance.urgenResolutionTime }</td>
						<td align="center">${myTeamDashboardPerformance.highResolutionTime }</td>
						<td align="center">${myTeamDashboardPerformance.mediumResolutionTime }</td>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<br>
		<sec:authorize
			access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_MANAGER')">
			<h1 style="font-size: 15px; color:#669999;">Team Ticket Pending Status</h1>
			<br>
			<div class="center_div" id="myTeamTicketDetailsByPendingStatusDivId">
				<br>
				<table class="dataTbl display tablesorter dataTable"
					id="teamTicketStatusDetailTableId"
					style="margin-top: 20px; margin-left: 0px !important;">
					<thead>
						<tr>
							<th align="center" valign="middle" style="background-color: #669999;">Module</th>
							<th align="center" valign="middle" style="background-color: #669999;">Assignee Name</th>
							<th align="center" valign="middle" style="background-color: #669999;" id="teamAcknowledgedHeaderId">Not
								Acknowledged</th>
							<th align="center" valign="middle" style="background-color: #669999;"
								id="teamProblemManagementHeaderId">Problem Management</th>
							<th align="center" valign="middle"  style="background-color: #669999;" id="teamRequirementHeaderId">In
								Requirement</th>
							<th align="center" valign="middle"  style="background-color: #669999;" id="teamAnalysisHeaderId">In
								Analysis</th>
							<th align="center" valign="middle" style="background-color: #669999;" id="teamTestingHeaderId">In
								Testing</th>
							<%-- <sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_MANAGER')"> --%>
								<th align="center" valign="middle" style="background-color: #669999;" id="teamReviewHeaderId">For
									Review</th>
							<%-- </sec:authorize> --%>
							<%-- <sec:authorize access="hasAnyRole('ROLE_USER')">
								<th align="center" valign="middle" style="background-color: #669999;" id="teamReviewHeaderId">Sent
									For Review</th>
							</sec:authorize> --%>
							<th align="center" valign="middle" style="background-color: #669999;" id="teamClosureHeaderId">For
								Closure</th>
							<th align="center" valign="middle" style="background-color: #669999;" id="teamFollowUpHeaderId">For
								Follow up </th>
						</tr>

					</thead>

					<tbody id="teamTicketStatusDetailBody">
						<c:forEach var="myTeamDashboard"
							items="${myTeamDashboardFlagCountList }">
							<tr>
								<td align="center">${myTeamDashboard.module }</td>
								<td align="center">${myTeamDashboard.assigneeName }</td>
								<td align="center"><a href="javascript:void(0);"
									onclick="teamPerformance(${myTeamDashboard.empId},${myTeamDashboard.moduleId},'inacknowledged');"><u>${myTeamDashboard.acknowledgedCount }</u></td>
								<td align="center"><a href="javascript:void(0);"
									onclick="teamPerformance(${myTeamDashboard.empId},${myTeamDashboard.moduleId},'inproblemmanagement');"><u>${myTeamDashboard.problemManagementCount}</u></td>
								<td align="center"><a href="javascript:void(0);"
									id="myTicketPendingStatusId"
									onclick="teamPerformance(${myTeamDashboard.empId},${myTeamDashboard.moduleId},'inrequirement');">${myTeamDashboard.reqCompleteCount}</a></td>
								<td align="center"><a href="javascript:void(0);"
									id="myTicketPendingStatusId"
									onclick="teamPerformance(${myTeamDashboard.empId},${myTeamDashboard.moduleId},'inanalysis');">${myTeamDashboard.analysisCompleteCount}</a></td>
								<td align="center"><a href="javascript:void(0);"
									id="myTicketPendingStatusId"
									onclick="teamPerformance(${myTeamDashboard.empId},${myTeamDashboard.moduleId},'intesting');"
									>${myTeamDashboard.solutionAcceptedCount}</a></td>
								<td align="center"><a href="javascript:void(0);"
									id="myTicketPendingStatusId2"
									onclick="teamPerformance(${myTeamDashboard.empId},${myTeamDashboard.moduleId},'forreview');">${myTeamDashboard.forMyReviewCount}</a></td>
								<td align="center"><a href="javascript:void(0);"
									id="myClosedTicket" class="myTicketPendingStatus"
									onclick="teamPerformance(${myTeamDashboard.empId},${myTeamDashboard.moduleId},'inclose');">${myTeamDashboard.customerApprovalCount}</a></td>
								<td align="center"><a href="javascript:void(0);"
									id="myFollowUpTicket" class="myTicketPendingStatus"
									onclick="teamPerformance(${myTeamDashboard.empId},${myTeamDashboard.moduleId},'infollowUp');">${myTeamDashboard.groupCount}</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
			<br>
			<div class="center_div" id="teamTicketDetailsByPendingStatusDivId">
				<table class="dataTbl display tablesorter dataTable"
					id="teamTicketDetailsByPendingStatusId"
					style="margin-top: 20px; margin-left: 0px !important;">
					<thead>
						<tr>
							<th align="center" valign="middle"
								style="background-color: #104E8B">Tickect No.</th>
							<th align="center" valign="middle"
								style="background-color: #104E8B">Module</th>
							<th align="center" valign="middle"
								style="background-color: #104E8B">Priority</th>
							<th align="center" valign="middle"
								style="background-color: #104E8B">LandScape</th>
							<th align="center" valign="middle"
								style="background-color: #104E8B">Region</th>
							<th align="center" valign="middle"
								style="background-color: #104E8B">Aging</th>
							<th align="center" valign="middle"
								style="background-color: #104E8B">SLA Missed</th>
							<th align="center" valign="middle"
								style="background-color: #104E8B">Days Open</th>
							<!-- <th align="center" valign="middle"
								style="background-color: #104E8B">Phase</th> -->
							<th align="center" valign="middle"
								style="background-color: #104E8B">Problem Managment</th>
						</tr>

					</thead>
					<tbody id="teamTicketDetailsByPendingStatusBody">
					</tbody>
				</table>
			</div>
		</sec:authorize>
	</sec:authorize>
	<br>
	<div class="center_div">
		<h1 style="font-size: 15px;">My Performance</h1>
		<br>
		<table class="dataTbl display tablesorter dataTable"
			id="ticketDetailTable"
			style="margin-top: 0px; margin-left: 0px !important;">
			<thead>
				<tr>
					<th align="center" valign="middle" rowspan="2">Ticket Assigned</th>
					<th align="center" valign="middle" rowspan="2">Open</th>
					<th align="center" valign="middle" colspan="2">Total Resolved</th>
					<th align="center" valign="middle" rowspan="2">SLA %</th>
					<th align="center" valign="middle" colspan="2">Aging</th>
					<th align="center" valign="middle" colspan="2">Project</th>
					<th align="center" valign="middle" colspan="3">Problem
						Management</th>
					<th align="center" valign="middle" colspan="3">Resolution Time (In Days)</th>
				</tr>
				<tr>
					<th>Yes</th>
					<th>No</th>
					<th>Resolved</th>
					<th>Open</th>
					<th>Resolved</th>
					<th>Open</th>
					<th>Proposed</th>
					<th>Justified</th>
					<th>Justified Crop</th>
					<th>Urgent</th>
					<th>High</th>
					<th>Medium/Low</th>
				</tr>
			</thead>
			<tbody id="ticketDetailTableBody">
				<!-- <td align="center">Auto populate</td>
				<td align="center">Auto populate</td>
				<td align="center">Yes %</td>
				<td align="center">No %</td>
				<td align="center">SLA %</td>
				<td align="center">as</td>
				<td align="center">asd</td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td> -->
				<c:forEach var="myDashboardPerformance"
					items="${myDashboardPerformanceList }">
					<td align="center">${myDashboardPerformance.totalAssigned }</td>
					<td align="center">${myDashboardPerformance.totalOpen }</td>
					<td align="center">${myDashboardPerformance.totalResolvedYes }</td>
					<td align="center">${myDashboardPerformance.totalResolvedNo }</td>
					<td align="center">${myDashboardPerformance.slaMissed }</td>
					<td align="center">${myDashboardPerformance.agingResolved }</td>
					<td align="center">${myDashboardPerformance.agingOpen }</td>
					<td align="center">${myDashboardPerformance.projectResolved }</td>
					<td align="center">${myDashboardPerformance.projectOpen }</td>
					<td align="center">${myDashboardPerformance.problemManagementProposed }</td>
					<td align="center">${myDashboardPerformance.problemManagementJustified }</td>
					<td align="center">${myDashboardPerformance.problemManagementJustifiedCrop }</td>
					<td align="center">${myDashboardPerformance.urgenResolutionTime }</td>
					<td align="center">${myDashboardPerformance.highResolutionTime }</td>
					<td align="center">${myDashboardPerformance.mediumResolutionTime }</td>
				</c:forEach>
				<%-- <c:forEach var="caticket" items="${catickets}">
					<tr>
						<td align="center" valign="middle"><a
							href="${pageContext.request.contextPath}/caticket/getTicketbyTicketNumber/${caticket.caTicketNo}"><c:out
									value="${caticket.caTicketNo}" /></a></td>
						<td align="center" valign="middle"><c:out
								value="${caticket.module}" /></td>
						<td align="center" valign="middle"><c:out
								value="${caticket.priority}" /></td>
						<td align="center" valign="middle"><c:out
								value="${caticket.landscape}" /></td>
						<td align="center" valign="middle"><c:out
								value="${caticket.region}" /></td>
						<td align="center" valign="middle"><c:out
								value="${caticket.aging}" /></td>
						<td align="center" valign="middle"><c:out
								value="${caticket.slaMissed}" /></td>
						<td align="center" valign="middle"><c:out
								value="${caticket.daysOpen}" /></td>
					</tr>
				</c:forEach> --%>
			</tbody>
		</table>

	</div>
	<br>
	<h1 style="font-size: 15px;">My Ticket Pending Status</h1>
	<br>
	<div class="center_div">

		<br>
		<table class="dataTbl display tablesorter dataTable"
			id="ticketStatusDetailTableId"
			style="margin-top: 0px; margin-left: 0px !important;">
			<thead>
				<tr>
					<th align="center" valign="middle">Module</th>
					<th align="center" valign="middle">Assignee Name</th>
					<th align="center" valign="middle" id="myAcknowledgedHeaderId">Not
						Acknowledged</th>
					<th align="center" valign="middle" id="myProblemManagementHeaderId">Problem
						Management</th>
					<th align="center" valign="middle" id="myRequirementHeaderId">In
						Requirement</th>
					<th align="center" valign="middle" id="myAnalysisHeaderId">In
						Analysis</th>
					<th align="center" valign="middle" id="myTestingHeaderId">In
						Testing</th>
					
					<%-- <sec:authorize access="hasAnyRole('ROLE_USER')"> --%>
						<th align="center" valign="middle" id="teamReviewHeaderId">Sent
							For Review</th>
					<%-- </sec:authorize> --%>
					
					<th align="center" valign="middle" id="myClosureHeaderId">For
						Closure</th>
						<%-- <sec:authorize
						access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_MANAGER')"> --%>
						<th align="center" valign="middle" id="teamReviewHeaderId">For
							My Review</th>
					<%-- </sec:authorize> --%>
					<th align="center" valign="middle" id="myClosureHeaderId">For
						Follow Up</th>
				</tr>

			</thead>

			<tbody id="ticketStatusDetailBody">
				<c:forEach var="myDashboard" items="${myDashboardFlagCountList }">
					<tr>
						<td align="center">${myDashboard.module }</td>
						<td align="center">${myDashboard.assigneeName }</td>
						<c:choose>
						<c:when test="${myDashboard.acknowledgedCount>0}">
						<td align="center"><a href="javascript:void(0);"
							onclick="myPerformance(${myDashboard.empId},${myDashboard.moduleId},'inacknowledged');"><u>${myDashboard.acknowledgedCount }</u></td>
						</c:when>
						<c:otherwise>
						<td align="center">${myDashboard.acknowledgedCount }</td>
						</c:otherwise>
						</c:choose>
						
						<c:choose>
						<c:when test="${myDashboard.problemManagementCount>0}">
						<td align="center"><a href="javascript:void(0);"
							onclick="myPerformance(${myDashboard.empId},${myDashboard.moduleId},'inproblemmanagement');"><u>${myDashboard.problemManagementCount}</u></td>
							</c:when>
						<c:otherwise>
						<td align="center">${myDashboard.problemManagementCount }</td>
						</c:otherwise>
						</c:choose>
						
						<c:choose>
						<c:when test="${myDashboard.reqCompleteCount>0}">
						<td align="center"><a href="javascript:void(0);"
							id="myTicketPendingStatusId"
							onclick="myPerformance(${myDashboard.empId},${myDashboard.moduleId},'inrequirement');">${myDashboard.reqCompleteCount}</a></td>
							</c:when>
							<c:otherwise>
						<td align="center">${myDashboard.reqCompleteCount }</td>
						</c:otherwise>
						</c:choose>
						
						<c:choose>
						<c:when test="${myDashboard.analysisCompleteCount>0}">
						<td align="center"><a href="javascript:void(0);"
							id="myTicketPendingStatusId"
							onclick="myPerformance(${myDashboard.empId},${myDashboard.moduleId},'inanalysis');">${myDashboard.analysisCompleteCount}</a></td>
						</c:when>
							<c:otherwise>
						<td align="center">${myDashboard.analysisCompleteCount }</td>
						</c:otherwise>
						</c:choose>	
						
						<c:choose>
						<c:when test="${myDashboard.solutionAcceptedCount>0}">
						<td align="center"><a href="javascript:void(0);"
							id="myTicketPendingStatusId"
							onclick="myPerformance(${myDashboard.empId},${myDashboard.moduleId},'intesting');">${myDashboard.solutionAcceptedCount}</a></td>
							</c:when>
							<c:otherwise>
						<td align="center">${myDashboard.solutionAcceptedCount }</td>
						</c:otherwise>
						</c:choose>	
						
						<c:choose>
						<c:when test="${myDashboard.sentForReviewCount>0}">
						<td align="center"><a href="javascript:void(0);"
							id="myTicketPendingStatusId2"
							onclick="myPerformance(${myDashboard.empId},${myDashboard.moduleId},'sentforreview');">${myDashboard.sentForReviewCount}</a></td>
						</c:when>
							<c:otherwise>
						<td align="center">${myDashboard.sentForReviewCount }</td>
						</c:otherwise>
						</c:choose>		
							
						<c:choose>
						<c:when test="${myDashboard.customerApprovalCount>0}">
						<td align="center"><a href="javascript:void(0);"
							id="myClosedTicket" class="myTicketPendingStatus"
							onclick="myPerformance(${myDashboard.empId},${myDashboard.moduleId},'inclose');">${myDashboard.customerApprovalCount}</a></td>
						</c:when>
							<c:otherwise>
						<td align="center">${myDashboard.customerApprovalCount }</td>
						</c:otherwise>
						</c:choose>	
						
						<c:choose>
						<c:when test="${myDashboard.forMyReviewCount>0}">
							<td align="center"><a href="javascript:void(0);"
							id="myTicketPendingStatusId2"
							onclick="myPerformance(${myDashboard.empId},${myDashboard.moduleId},'formyreview');">${myDashboard.forMyReviewCount}</a></td>
							</c:when>
							<c:otherwise>
						<td align="center">${myDashboard.forMyReviewCount }</td>
						</c:otherwise>
						</c:choose>	
						
						<c:choose>
						<c:when test="${myDashboard.groupCount>0}">
						<td align="center"><a href="javascript:void(0);"
							id="myFollowUpTicket" class="myTicketPendingStatus"
							onclick="myPerformance(${myDashboard.empId},${myDashboard.moduleId},'infollowUp');">${myDashboard.groupCount}</a></td>
							</c:when>
							<c:otherwise>
						<td align="center">${myDashboard.groupCount }</td>
						</c:otherwise>
						</c:choose>	
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<br> <br>
	<div class="center_div" id="myTicketDetailsByPendingStatusDivId">
		<table class="dataTbl display tablesorter dataTable"
			id="myTicketDetailsByPendingStatusId"
			style="margin-top: 20px; margin-left: 0px !important;">
			<thead>
				<tr>
					<th align="center" valign="middle"
						style="background-color: #104E8B">Tickect No.</th>
					<th align="center" valign="middle"
						style="background-color: #104E8B">Module</th>
					<th align="center" valign="middle"
						style="background-color: #104E8B">Priority</th>
					<th align="center" valign="middle"
						style="background-color: #104E8B">LandScape</th>
					<th align="center" valign="middle"
						style="background-color: #104E8B">Region</th>
					<th align="center" valign="middle"
						style="background-color: #104E8B">Aging</th>
					<th align="center" valign="middle"
						style="background-color: #104E8B">SLA Missed</th>
					<th align="center" valign="middle"
						style="background-color: #104E8B">Days Open</th>
					<!-- <th align="center" valign="middle"
						style="background-color: #104E8B">Phase</th> -->
					<th align="center" valign="middle"
						style="background-color: #104E8B">Problem Managment</th>
				</tr>

			</thead>
			<tbody id="myTicketDetailsByPendingStatusBody">
			</tbody>
		</table>
	</div>
</div>

<script type="text/javascript">

function changeColorCode(phase){
	if(phase=='inacknowledged'){
		$("#myAcknowledgedHeaderId").css("background-color", "#104E8B");
		$("#myProblemManagementHeaderId").css("background-color", "#3377bb");
		$("#myRequirementHeaderId").css("background-color", "#3377bb");
		$("#myAnalysisHeaderId").css("background-color", "#3377bb");
		$("#myTestingHeaderId").css("background-color", "#3377bb");
		$("#myReviewHeaderId").css("background-color", "#3377bb");
		$("#myClosureHeaderId").css("background-color", "#3377bb");
	}else if(phase=='inproblemmanagement'){
		$("#myAcknowledgedHeaderId").css("background-color", "#3377bb");
		$("#myProblemManagementHeaderId").css("background-color", "#104E8B");
		$("#myRequirementHeaderId").css("background-color", "#3377bb");
		$("#myAnalysisHeaderId").css("background-color", "#3377bb");
		$("#myTestingHeaderId").css("background-color", "#3377bb");
		$("#myReviewHeaderId").css("background-color", "#3377bb");
		$("#myClosureHeaderId").css("background-color", "#3377bb");
	}else if(phase=='inrequirement'){
		$("#myAcknowledgedHeaderId").css("background-color", "#3377bb");
		$("#myProblemManagementHeaderId").css("background-color", "#3377bb");
		$("#myRequirementHeaderId").css("background-color", "#104E8B");
		$("#myAnalysisHeaderId").css("background-color", "#3377bb");
		$("#myTestingHeaderId").css("background-color", "#3377bb");
		$("#myReviewHeaderId").css("background-color", "#3377bb");
		$("#myClosureHeaderId").css("background-color", "#3377bb");
	}else if(phase=='inanalysis'){
		$("#myAcknowledgedHeaderId").css("background-color", "#3377bb");
		$("#myProblemManagementHeaderId").css("background-color", "#3377bb");
		$("#myRequirementHeaderId").css("background-color", "#3377bb");
		$("#myAnalysisHeaderId").css("background-color", "#104E8B");
		$("#myTestingHeaderId").css("background-color", "#3377bb");
		$("#myReviewHeaderId").css("background-color", "#3377bb");
		$("#myClosureHeaderId").css("background-color", "#3377bb");
	}else if(phase=='inreview'){
		$("#myAcknowledgedHeaderId").css("background-color", "#3377bb");
		$("#myProblemManagementHeaderId").css("background-color", "#3377bb");
		$("#myRequirementHeaderId").css("background-color", "#3377bb");
		$("#myAnalysisHeaderId").css("background-color", "#3377bb");
		$("#myTestingHeaderId").css("background-color", "#3377bb");
		$("#myReviewHeaderId").css("background-color", "#104E8B");
		$("#myClosureHeaderId").css("background-color", "#3377bb");
	}else if(phase=='inclose'){
		$("#myAcknowledgedHeaderId").css("background-color", "#3377bb");
		$("#myProblemManagementHeaderId").css("background-color", "#3377bb");
		$("#myRequirementHeaderId").css("background-color", "#3377bb");
		$("#myAnalysisHeaderId").css("background-color", "#3377bb");
		$("#myTestingHeaderId").css("background-color", "#3377bb");
		$("#myReviewHeaderId").css("background-color", "#3377bb");
		$("#myClosureHeaderId").css("background-color", "#104E8B");
	}
}

function changeTeamColorCode(phase){
	if(phase=='inacknowledged'){
		$("#teamAcknowledgedHeaderId").css("background-color", "#104E8B");
		$("#teamProblemManagementHeaderId").css("background-color", "#669999");
		$("#teamRequirementHeaderId").css("background-color", "#669999");
		$("#teamAnalysisHeaderId").css("background-color", "#669999");
		$("#teamTestingHeaderId").css("background-color", "#669999");
		$("#teamReviewHeaderId").css("background-color", "#669999");
		$("#teamClosureHeaderId").css("background-color", "#669999");
	}else if(phase=='inproblemmanagement'){
		$("#teamAcknowledgedHeaderId").css("background-color", "#669999");
		$("#teamProblemManagementHeaderId").css("background-color", "#104E8B");
		$("#teamRequirementHeaderId").css("background-color", "#669999");
		$("#teamAnalysisHeaderId").css("background-color", "#669999");
		$("#teamTestingHeaderId").css("background-color", "#669999");
		$("#teamReviewHeaderId").css("background-color", "#669999");
		$("#teamClosureHeaderId").css("background-color", "#669999");
	}else if(phase=='inrequirement'){
		$("#teamAcknowledgedHeaderId").css("background-color", "#669999");
		$("#teamProblemManagementHeaderId").css("background-color", "#669999");
		$("#teamRequirementHeaderId").css("background-color", "#104E8B");
		$("#teamAnalysisHeaderId").css("background-color", "#669999");
		$("#teamTestingHeaderId").css("background-color", "#669999");
		$("#teamReviewHeaderId").css("background-color", "#669999");
		$("#teamClosureHeaderId").css("background-color", "#669999");
	}else if(phase=='inanalysis'){
		$("#teamAcknowledgedHeaderId").css("background-color", "#669999");
		$("#teamProblemManagementHeaderId").css("background-color", "#669999");
		$("#teamRequirementHeaderId").css("background-color", "#669999");
		$("#teamAnalysisHeaderId").css("background-color", "#104E8B");
		$("#teamTestingHeaderId").css("background-color", "#669999");
		$("#teamReviewHeaderId").css("background-color", "#669999");
		$("#teamClosureHeaderId").css("background-color", "#669999");
	}else if(phase=='inreview'){
		$("#teamAcknowledgedHeaderId").css("background-color", "#669999");
		$("#teamProblemManagementHeaderId").css("background-color", "#669999");
		$("#teamRequirementHeaderId").css("background-color", "#669999");
		$("#teamAnalysisHeaderId").css("background-color", "#669999");
		$("#teamTestingHeaderId").css("background-color", "#669999");
		$("#teamReviewHeaderId").css("background-color", "#104E8B");
		$("#teamClosureHeaderId").css("background-color", "#669999");
	}else if(phase=='inclose'){
		$("#teamAcknowledgedHeaderId").css("background-color", "#669999");
		$("#teamProblemManagementHeaderId").css("background-color", "#669999");
		$("#teamRequirementHeaderId").css("background-color", "#669999");
		$("#teamAnalysisHeaderId").css("background-color", "#669999");
		$("#teamTestingHeaderId").css("background-color", "#669999");
		$("#teamReviewHeaderId").css("background-color", "#669999");
		$("#teamClosureHeaderId").css("background-color", "#104E8B");
	}
}
 $(document).ready(function() { 
	 myPerformance = function(empId,module,phase) { //now has global scope.
	        //alert('did it'+empId+' test '+module+' test111 '+phase);
	 changeColorCode(phase);
	        var htmlVar = '';
			startProgress();
			$.ajax({
				type : 'GET',
				url : '${pageContext.request.contextPath}/caticket/getTicketsByPhase',
				data: "empId="+empId+"&module=" + module+"&phase=" + phase,
				contentType : "application/json",
				dataType : "json",
				success : function(data) {
					 
					$('#myTicketDetailsByPendingStatusId').empty();
					htmlVar += '<tbody id="myTicketDetailsByPendingStatusBody">'; 
					for (var i = 0; i < data.length; i++) {
						var obj = data[i];
						 
						htmlVar += '<tr>';
						htmlVar += '<td align="center"><a href="${pageContext.request.contextPath}/caticket/getTicketById/'+obj.id+'">'+obj.caTicketNo+'</td>';
						htmlVar += '<td align="center">'+obj.moduleId.moduleName+'</td>';
						htmlVar += '<td align="center">'+obj.priority+'</td>';
						if(obj.landscapeId!=null){
						htmlVar += '<td align="center">'+obj.landscapeId.landscapeName+'</td>';
						}else{
							htmlVar += '<td align="center"></td>';
						}
						
						if(obj.region!=null){
							htmlVar += '<td align="center">'+obj.region.regionName+'</td>';
							}else{
								htmlVar += '<td align="center"></td>';
							}
						
						
						htmlVar += '<td align="center">'+obj.aging+'</td>';
						htmlVar += '<td align="center">'+obj.slaMissed+'</td>';
						htmlVar += '<td align="center">'+obj.daysOpen+'</td>';
						/* htmlVar += '<td align="center">'+obj.customerApprovalFlag+'</td>'; */
						htmlVar += '<td align="center">'+obj.problemManagementFlag+'</td>'; 
						htmlVar += '</tr>';
					}
					htmlVar += '</tbody>';
					$('#myTicketDetailsByPendingStatusId').append(htmlVar);
					
					 $('#myTicketDetailsByPendingStatusId').DataTable(
								{
									"bProcessing" : true,
									"bAutoWidth" : false,
									"bScrollCollapse" : true,
									"bPaginate" : true,
									"bDestory" : true,
									"bRetrieve" : true,
									"bStateSave": true,
									//  "sDom": '<"projecttoolbar">lfrtip',
									"aaSorting" : [ [ 0, 'asc' ] ],
									"bDeferRender" : true,
									"oLanguage" : {
										"sLengthMenu" : 'Show <SELECT>'
												+ '<OPTION value=10>10</OPTION>'
												+ '<OPTION value=25>25</OPTION>'
												+ '<OPTION value=50>50</OPTION>'
												+ '<OPTION value=100>100</OPTION>'
												+ '<OPTION value=200>200</OPTION></SELECT> entries'
									},

									"bSortCellsTop" : true,

									//"sDom": '<"projecttoolbar">lfrtip'
									"sDom" : '<"projecttoolbar">lfip<"top">rt<"bottom"<"#refresh">ip><"clear">'
								// "sDom": '<"top"i>rt<"bottom"<"#refresh">flp><"clear">'
					 });
					  $('#myTicketDetailsByPendingStatusId').DataTable({
						 "bDestroy": true,
						 aaSorting: [[0, 'desc']],							 
						 "bAutoWidth": false,
					 }); 
					 $('#myTicketDetailsByPendingStatusDivId').show();
					stopProgress();
				},
				error : function(errorResponse) {
					showError(errorResponse);
				}

			});
	    };
	 $('#ticketStatusDetailTableId').DataTable(
				{
					"bProcessing" : true,
					"bAutoWidth" : false,
					"bScrollCollapse" : true,
					"bPaginate" : true,
					"bDestory" : true,
					"bRetrieve" : true,
					"bStateSave": true,
					//  "sDom": '<"projecttoolbar">lfrtip',
					"aaSorting" : [ [ 0, 'asc' ] ],
					"bDeferRender" : true,
					"oLanguage" : {
						"sLengthMenu" : 'Show <SELECT>'
								+ '<OPTION value=10>10</OPTION>'
								+ '<OPTION value=25>25</OPTION>'
								+ '<OPTION value=50>50</OPTION>'
								+ '<OPTION value=100>100</OPTION>'
								+ '<OPTION value=200>200</OPTION></SELECT> entries'
					},

					"bSortCellsTop" : true,

					//"sDom": '<"projecttoolbar">lfrtip'
					"sDom" : '<"projecttoolbar">lfip<"top">rt<"bottom"<"#refresh">ip><"clear">'
				// "sDom": '<"top"i>rt<"bottom"<"#refresh">flp><"clear">'
	 });
	 
	 $('#myTicketDetailsByPendingStatusDivId').hide();
	 /* $('#myTeamTicketDetailsByPendingStatusDivId').hide(); */
	 
	 // Creation datatable for team tickets pending status
	 $('#teamTicketDetailsByPendingStatusDivId').hide();
	  teamPerformance = function(empId,module,phase) { //now has global scope.
	        //alert('did it'+empId+' test '+module+' test111 '+phase);
		  changeTeamColorCode(phase);
	        var htmlVar = '';
			startProgress();
			$.ajax({
				type : 'GET',
				url : '${pageContext.request.contextPath}/caticket/getTicketsByPhase',
				data: "empId="+empId+"&module=" + module+"&phase=" + phase,
				contentType : "application/json",
				dataType : "json",
				success : function(data) {
					 
					$('#teamTicketDetailsByPendingStatusId').empty();
					htmlVar += '<tbody id="teamTicketDetailsByPendingStatusBody">'; 
					for (var i = 0; i < data.length; i++) {
						var obj = data[i]; 
						htmlVar += '<tr>';
						htmlVar += '<td align="center"><a href="${pageContext.request.contextPath}/caticket/getTicketById/'+obj.id+'">'+obj.caTicketNo+'</td>';
						htmlVar += '<td align="center">'+obj.moduleId.moduleName+'</td>';
						htmlVar += '<td align="center">'+obj.priority+'</td>';
						/* htmlVar += '<td align="center">'+obj.landscapeId.landscapeName+'</td>';
						htmlVar += '<td align="center">'+obj.region.regionName+'</td>'; */
						if(obj.landscapeId!=null){
							htmlVar += '<td align="center">'+obj.landscapeId.landscapeName+'</td>';
							}else{
								htmlVar += '<td align="center"></td>';
							}
							
							if(obj.region!=null){
								htmlVar += '<td align="center">'+obj.region.regionName+'</td>';
								}else{
									htmlVar += '<td align="center"></td>';
								}
						htmlVar += '<td align="center">'+obj.aging+'</td>';
						htmlVar += '<td align="center">'+obj.slaMissed+'</td>';
						htmlVar += '<td align="center">'+obj.daysOpen+'</td>';
						/* htmlVar += '<td align="center">'+obj.customerApprovalFlag+'</td>'; */
						htmlVar += '<td align="center">'+obj.problemManagementFlag+'</td>'; 
						htmlVar += '</tr>';
					}
					htmlVar += '</tbody>';
					$('#teamTicketDetailsByPendingStatusId').append(htmlVar);
					
					 $('#teamTicketDetailsByPendingStatusId').DataTable(
								{
									"bProcessing" : true,
									"bAutoWidth" : false,
									"bScrollCollapse" : true,
									"bPaginate" : true,
									"bDestory" : true,
									"bRetrieve" : true,
									"bStateSave": true,
									//  "sDom": '<"projecttoolbar">lfrtip',
									"aaSorting" : [ [ 0, 'asc' ] ],
									"bDeferRender" : true,
									"oLanguage" : {
										"sLengthMenu" : 'Show <SELECT>'
												+ '<OPTION value=10>10</OPTION>'
												+ '<OPTION value=25>25</OPTION>'
												+ '<OPTION value=50>50</OPTION>'
												+ '<OPTION value=100>100</OPTION>'
												+ '<OPTION value=200>200</OPTION></SELECT> entries'
									},

									"bSortCellsTop" : true,

									//"sDom": '<"projecttoolbar">lfrtip'
									"sDom" : '<"projecttoolbar">lfip<"top">rt<"bottom"<"#refresh">ip><"clear">'
								// "sDom": '<"top"i>rt<"bottom"<"#refresh">flp><"clear">'
					 });
					  $('#teamTicketDetailsByPendingStatusId').DataTable({
						 "bDestroy": true,
						 aaSorting: [[0, 'desc']],							 
						 "bAutoWidth": false,
					 }); 
					 $('#teamTicketDetailsByPendingStatusDivId').show();
					stopProgress();
				},
				error : function(errorResponse) {
					showError(errorResponse);
				}

			});
	    };
	    
	 $('#teamTicketStatusDetailTableId').DataTable(
				{
					"bProcessing" : true,
					"bAutoWidth" : false,
					"bScrollCollapse" : true,
					"bPaginate" : true,
					"bDestory" : true,
					"bRetrieve" : true,
					"bStateSave": true,
					//  "sDom": '<"projecttoolbar">lfrtip',
					"aaSorting" : [ [ 0, 'asc' ] ],
					"bDeferRender" : true,
					"oLanguage" : {
						"sLengthMenu" : 'Show <SELECT>'
								+ '<OPTION value=10>10</OPTION>'
								+ '<OPTION value=25>25</OPTION>'
								+ '<OPTION value=50>50</OPTION>'
								+ '<OPTION value=100>100</OPTION>'
								+ '<OPTION value=200>200</OPTION></SELECT> entries'
					},

					"bSortCellsTop" : true,

					//"sDom": '<"projecttoolbar">lfrtip'
					"sDom" : '<"projecttoolbar">lfip<"top">rt<"bottom"<"#refresh">ip><"clear">'
				// "sDom": '<"top"i>rt<"bottom"<"#refresh">flp><"clear">'
	 });
		
 });
 
 
</script>