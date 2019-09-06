<%@page import="org.yash.rms.util.UserUtil"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%-- <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> --%>
<%-- <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> --%>

<web-app xmlns="http://java.sun.com/xml/ns/j2ee"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd"
	version="2.4">

<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="${style_css}" rel="stylesheet" type="text/css"></link>

<style>

.releasefeedbackDisable{
	pointer-events: none;
	cursor: default;
}
.table-no-border {
	margin-top: 4px !important;
}

.table-no-border, .table-no-border tbody tr, .table-no-border tbody tr td
	{
	border-spacing: 0px !important;
	border: none !important;
}

.no-padding {
	padding: 0 !important;
}

.td-align-bottom {
	vertical-align: bottom;
	text-align: center;
}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		
		//For update report count when we add new report count increase on dashboard 
		var countId = $('#our-stat-table tbody tr').length - 1;
		if (countId != ($('#reportCount').val())) {
			$.ajax({
				async : true,
				contentType : "application/json",
				url : "javaReports/update",
				data: "countId="+countId,
				success : function(data) {

				}
			});
		}

		$('#our-stat-image').hide();
		if ($('#our-stat-table').height() < 216) {
			$('#our-stat-image').css({
				height : $('#our-stat-table').height() + 'px',
				width : "auto"
			});
		}
		$('#our-stat-image').show();
	});
</script>

</head>
<body>
	<!-- <div class="midSection posRelMidSection">
		left section
		<tiles:insertAttribute name="menu" ignore="true" />
		left section
	</div> -->


	<div class="content-wrapper">
		<div>
			<h1>
				<img src="/rms/resources/images/report.png" height="66" width="66"
					align="absMiddle" />JAVA REPORTS
			</h1>

		</div>

		<div style="position: relative">
			<table border="0" cellpadding="5" cellspacing="5"
				class=" dataTable dataTbl tablesorter table-no-border"
				id="our-stat-table">
				<div class="contentArea">
					<tiles:insertAttribute name="header" />
				</div>
				<thead>
					<tr>
						<th width="30%">Name</th>
						<th width="70%" colspan="2">Description</th>
					</tr>
				</thead>
				<tbody>

					<div></div>

					<tr>
						<td class="no-padding"></td>
						<td class="no-padding"></td>
						<td rowspan="50" class="td-align-bottom"><img
							src="/rms/resources/images/graph1.png" id="our-stat-image"
							width="250" /></td>

					</tr>

					<c:if test="${reportUser==1 || reportUser==3 }">

						<tr>
							<td>
								<a href="/rms/rmReports/" data-toggle="tooltip" title="RM Report"> 
									<img src="/rms/resources/images/reportIcon.png" height="16"
									width="16" align="absMiddle" /><b> RM Details Of Employee</b>
								</a>
							</td>

							<td>To view Resource complete details.</td>
						</tr>


						<tr>
							<td><a href="/rms/plReports/" data-toggle="tooltip"
								title="PL Report"> <img
									src="/rms/resources/images/reportIcon.png" height="16"
									width="16" align="absMiddle" /><b> PL Report</b></a></td>
							<td>Profit & Loss Report. [Cost is not inclusive yet]</td>

						</tr>
						<tr>
							<td><a href="/rms/muneerReports/"
								data-toggle="tooltip" title="Management Report"> <img
									src="/rms/resources/images/reportIcon.png" height="16"
									width="16" align="absMiddle" /><b> Management Report</b></a></td>
							<td>To view current status of BG/BU </td>

						</tr>
						<tr>
							<td><a href="/rms/pwrReports/"
								data-toggle="tooltip" title="Project Wise Resource Report">
									<img src="/rms/resources/images/reportIcon.png" height="16"
									width="16" align="absMiddle" /><b> Project Wise Resource
										Report</b>
							</a></td>
							<td>Project Wise Resource allocation Report</td>

						</tr>

						
						<tr>
							<td><a href="/rms/teamReports/"
								data-toggle="tooltip" title="Team Report"> <img
									src="/rms/resources/images/reportIcon.png" height="16"
									width="16" align="absMiddle" /><b> Team Report</b></a></td>
							<td>Team Report - Customised for US team which works on Team concept</td>

						</tr>
						<tr>
							<td><a
								href="/rms/ResourceMovementReports/"
								data-toggle="tooltip" title="Resource Movement Report"> <img
									src="/rms/resources/images/reportIcon.png" height="16"
									width="16" align="absMiddle" /><b> Resource Movement Report</b></a></td>
							<td>To view details of internal BG/BU transfer of resources [Under beta testing]</td>

						</tr>
						<tr>
							<td><a href="/rms/moduleReports/" data-toggle="tooltip"
								title="Module Report"> <img
									src="/rms/resources/images/reportIcon.png" height="16"
									width="16" align="absMiddle"/><b> Module Report</b></a></td>
							<td>Billing Hrs wrt Project Module. [Customized report for CAT business]</td>

						</tr>
						
						<!--start feedbackreport  -->
						<tr>
							<td><a href="/rms/releasefeedback/" data-toggle="tooltip"
								title="Resource Release Feedback Report" class="releasefeedbackDisable"> <img
									src="/rms/resources/images/reportIcon.png" height="16"
									width="16" align="absMiddle"/><b> Resource Feedback Report</b></a></td>
							<td>Resource Feedback Report [Under Beta testing]</td>

						</tr>
						<!--end feedbackreport -->
									
									<!--start utilizationreport  -->
									<tr>
										<td><a href="/rms/UtilizationReport/" data-toggle="tooltip"
											title="Utilization Detail Report"> <img
												src="/rms/resources/images/reportIcon.png" height="16"
												width="16" align="absMiddle"/><b>Utilization Detail Report</b></a></td>
										<td>To view Resources' Utilization/ Hours details</td>
				
									</tr>
									<!--end utilizationreport -->
									
									<!--start customizereport  -->
									<!-- <tr>
										<td><a href="/rms/customizeReport/" data-toggle="tooltip"
											title="Customize Report"> <img
												src="/rms/resources/images/reportIcon.png" height="16"
												width="16" align="absMiddle"/><b>Customize Report</b></a></td>
										<td>Customize Report For BG4BU5</td>
				
									</tr> -->
									<!--end customizereport -->
									<!--start CurrentWeekAllocation  -->
									<tr>
										<td><a href="/rms/currentWeekAllocation/" data-toggle="tooltip"
											title="Current Week Allocation Report"> <img
												src="/rms/resources/images/reportIcon.png" height="16"
												width="16" align="absMiddle"/><b>Current Week Allocation Report</b></a></td>
										<td>Current Week Allocation Report</td>
				
									</tr>
									<!--end utilizationreport -->
									<!--start Customized Header Report  -->
									<!-- <tr>
										<td><a href="/rms/customizedHeaderReport/" data-toggle="tooltip"
											title="Customized Header Report"> <img
												src="/rms/resources/images/reportIcon.png" height="16"
												width="16" align="absMiddle"/><b>Customized Header Report</b></a></td>
										<td>Customized Header Report</td>
				
									</tr> -->
									<!--end Customized Header Report -->
									<!--start Resource Requisition Report  -->
									<tr>
										<td><a href="/rms/requestRequisitionReport/" data-toggle="tooltip"
											title="Resource Requisition Report"> <img
												src="/rms/resources/images/reportIcon.png" height="16"
												width="16" align="absMiddle"/><b>Resource Requisition Report</b></a></td>
										<td>To view all open/close Resource requirements its status and submissions</td>
				
									</tr>
									
									<!--end Resource Requisition Report -->
									
									<!--start TimesheetHours  -->
									<tr>
										<td><a href="/rms/timeSheetReport/" data-toggle="tooltip"
											title="Timesheet Hours"> <img
												src="/rms/resources/images/reportIcon.png" height="16"
												width="16" align="absMiddle"/><b>Time Sheet Compliance Report</b></a></td>
										<td>To view Resource Details who have not submitted time sheet</td>
									</tr>
									<!--end TimesheetHours -->

									<tr>
										<td><a href="/rms/resourceSkillReport/"
											data-toggle="tooltip" title="Resource Report"> <img
												src="/rms/resources/images/reportIcon.png" height="16"
												width="16" align="absMiddle" /><b> Resource's Skill Report</b>
										</a></td>
			
										<td>This report shows details of all employees including
											skills depending upon filters.</td>
									</tr>

						<!--start Resource Metrics Report  -->
									<c:if test="<%=UserUtil.getCurrentResource().getEmployeeId()==430 
										|| UserUtil.getCurrentResource().getEmployeeId()==221 
										|| UserUtil.getCurrentResource().getEmployeeId()==5567 %>">
									<tr>
										<td><a href="/rms/rmgReport/" data-toggle="tooltip"
											title="Resource Metrics Report"> <img
												src="/rms/resources/images/reportIcon.png" height="16"
												width="16" align="absMiddle"/><b>RMG - Resource Management Group Report</b></a></td>
										<td>RMG - Resource Management Group Report</td>
				
									</tr>
									</c:if>
									<!--end Resource Metrics Report -->
									
					</c:if>
             
             <%-- Added for Module Report access only --%>
                  <c:if test="${reportUser==4}">
					    <tr>
							<td><a href="/rms/moduleReports/" data-toggle="tooltip"
								title="Module Report"> <img
									src="/rms/resources/images/reportIcon.png" height="16"
									width="16" align="absMiddle"/><b> Module Report</b></a></td>
							<td>Module Report</td>

						  </tr>
						  <!--start TimesheetHours  -->
							<tr>
								<td><a href="/rms/timeSheetReport/" data-toggle="tooltip"
									title="Timesheet Hours"> <img
										src="/rms/resources/images/reportIcon.png" height="16"
										width="16" align="absMiddle"/><b>Timesheet Hours Report</b></a></td>
								<td>Fetch report for Timesheet hours</td>
							</tr>
							<!--end TimesheetHours -->
					</c:if>
					
					<%-- <c:if test="${reportUser==2 || reportUser==3 }">

						<tr>
							<td>
								<a target="_blank" 
				href="${reportPath }RM_detailsofEmployee_ROLE"> <a
								target="_blank" href="/rms/rmReports/viewRMReport"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /><b> RM Details Of Employee</b>
							</a>
							</td>

							<td>This report shows details of all employees depending
								upon filters.</td>
						</tr>


						<tr>
							<td><a href="reports/rmsReport" data-toggle="tooltip"
								title="PL Report"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /><b>PL Report</b></a></td>
							<td>PL Report</td>

						</tr>

					</c:if> --%>

				</tbody>

			</table>
		</div>
	</div>
	 
        <input type="hidden" value="${reportCount}" id="reportCount"> 


</body>
</html>
