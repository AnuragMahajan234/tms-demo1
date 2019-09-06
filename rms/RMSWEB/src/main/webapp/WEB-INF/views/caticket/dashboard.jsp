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

<div class="mid_section">
	<h1>Dashboard</h1>
	<br>
	<div class="center_div">
		<div class="form" style="width: 1290px; overflow-x: scroll;">
			<input type="button" name="filter" value="Filter"
				style="background-color: #d3d3d3; color: #37B; float: right;" />
			<table cellspacing="10" cellpadding="10">
				<tr>
					<td width="10%">Priority</td>
					<td><select name="priority" id="priorityId"
						class="required comboselect check">
							<option value="">SELECT</option>
							<option value="Priority1">Priority1</option>
							<option value="Priority2">Priority2</option>
							<option value="Priority3">Priority3</option>
							<option value="Priority4">Priority4</option>
					</select></td>
					<td>Module</td>
					<td><select name="module" id="moduleId"
						class="required comboselect check">
							<option value="">SELECT</option>
							<option value="Module1">Module1</option>
							<option value="Module2">Module2</option>
							<option value="Module3">Module3</option>
							<option value="Module4">Module4</option>
					</select></td>
					<sec:authorize
						access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
						<td>Reviewer/IRM Name</td>
						<td><select name="reviewer" id="reviewerId"
							class="required comboselect check">
								<option value="">SELECT</option>
								<option value="ReviewerName1">ReviewerName1</option>
								<option value="ReviewerName2">ReviewerName2</option>
								<option value="ReviewerName3">ReviewerName3</option>
								<option value="ReviewerName4">ReviewerName4</option>
						</select></td></sec:authorize>
				</tr>
				<tr>
					<td width="10%">LandScape</td>
					<td><select name="landscape"
						class="required comboselect check" id="landscapeId">
							<option value="">SELECT</option>
							<option value="LandScape1">LandScape1</option>
							<option value="LandScape2">LandScape2</option>
							<option value="LandScape3">LandScape3</option>
							<option value="LandScape4">LandScape4</option>
					</select></td>
					<td width="10%">Region</td>
					<td><select name="region" id="regionId"
						class="required comboselect check">
							<option value="">SELECT</option>
							<option value="Region1">Region1</option>
							<option value="Region2">Region2</option>
							<option value="Region3">Region3</option>
							<option value="Region4">Region4</option>
					</select></td>
					<sec:authorize
						access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
						<td>Assignee Name</td>
						<td><select name="asignee" id="asigneeId"
							class="required comboselect check">
								<option value="">SELECT</option>
								<option value="AssigneeName1">Assignee Name 1</option>
								<option value="AssigneeName2">Assignee Name 2</option>
								<option value="AssigneeName3">Assignee Name 3</option>
								<option value="AssigneeName4">Assignee Name 4</option>
						</select></td></sec:authorize>
				</tr>
				<tr>
					<td>From Time Period</td>
					<td><input type="Text" readonly="readonly" id="frmPeriod" maxlength="25" size="25"
						onclick="javascript:NewCssCal ('frmPeriod','MMddyyyy','arrow',true,'12',true)" />
					</td>
					<td>To Time Period</td>
					<td><input type="Text" readonly="readonly" id="toPeriod" maxlength="25" size="25"
						onclick="javascript:NewCssCal ('toPeriod','MMddyyyy','arrow',true,'12',true)" />
					</td>
				</tr>
			</table>

			<table class="dataTbl display tablesorter dataTable"
				id="ticketDetailTable"
				style="margin-top: 0px; margin-left: 0px !important;">
				<thead>
					<tr>
						<th align="center" valign="middle" rowspan="2">Ticket
							Assigned</th>
						<th align="center" valign="middle" rowspan="2">Open</th>
						<th align="center" valign="middle" colspan="2">Total Resolved</th>
						<th align="center" valign="middle" rowspan="2">SLA</th>
						<th align="center" valign="middle" colspan="2">Aging</th>
						<th align="center" valign="middle" colspan="2">Project</th>
						<th align="center" valign="middle" colspan="3">Problem
							Management</th>
						<th align="center" valign="middle" colspan="3">Resolution
							Time</th>
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
						<th>Justificedecrop</th>
						<th>Urgnt</th>
						<th>High</th>
						<th>Medium/Low</th>
					</tr>
				</thead>
				<tbody id="ticketDetailTableBody">
					<td align="center">Auto populate</td>
					<td align="center">Auto populate</td>
					<td align="center">Yes %</td>
					<td align="center">No %</td>
					<td align="center">SLA %</td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>

					<c:forEach var="caticket" items="${catickets}">
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
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<div id="fileAlert">
	<p></p>
	<div class="fileAlertBtm">
		<input type="button" name="Ok" value="Ok" id="ok" />
	</div>
</div>
