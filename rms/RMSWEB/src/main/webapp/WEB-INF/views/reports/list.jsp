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
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />
<title>Insert title here</title>
<link href="${style_css}" rel="stylesheet" type="text/css"></link>

<style>
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
				<img src="resources/images/report.png" height="66" width="66"
					align="absMiddle" />RMS REPORTS
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

					<div>




						<!--  <tr style="visibility: hidden"> -->




					</div>



					<%-- <c:choose>  --%>

					<tr>
						<td class="no-padding"></td>
						<td class="no-padding"></td>
						<td rowspan="50" class="td-align-bottom"><img
							src="/rms/resources/images/graph1.png" id="our-stat-image"
							width="250" /></td>

					</tr>

					<c:if test="${reportUser==1 || reportUser==3 }">


						<tr>
							<td><a href="plReports/rmsReport" data-toggle="tooltip"
								title="PL Report"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /><b> PL Report</b></a></td>
							<td>PL Report</td>

						</tr>

						<tr>
							<td><a href="${reportPath }CurrentWeekAllocation_ROLE"
								target="_blank" data-toggle="tooltip"
								data-placement="CurrentWeek"
								title="It shows Project allocation of employees for the current week">
									<img src="resources/images/reportIcon.png" height="16"
									width="16" align="absMiddle" /> <b>Current Week Allocation</b>
							</a></td>

							<td>Project allocation of employees for the current week</td>
						</tr>




						<tr>
							<td><a target="_blank"
								href="${reportPath }Employee_NonProductive_hour_ROLE"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b>Employee NonProductive hours
										Report</b>
							</a></td>

							<td>PROD and non-PROD hour details of resources</td>
						</tr>





						<tr>
							<td><a target="_blank"
								href="${reportPath }LoanTransfer_ROLE"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b> LoanTransfer Report</b>
							</a></td>
							<td>transfer details of employees according to date
								selected.</td>
						</tr>

						<tr>
							<td><a target="_blank"
								href="${reportPath }Parent_Project_Report_ROLE"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b> Parent Project Report</b>
							</a></td>

							<td>Shows ResourceWise project details and ProjectWise
								resource Details.</td>
						</tr>

						<tr>
							<td><a target="_blank"
								href="${reportPath }ProjectwiseResources_ROLE"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b> ProjectWise Resources </b>
							</a></td>

							<td>ResourceWise project details and ProjectWise resource
								Details.</td>
						</tr>

						<tr>
							<td><a target="_blank"
								href="${reportPath }ResourceList_Muneer_ROLE"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b> Management - Resource List</b>
							</a></td>

							<td>Allocation of employee with discrepancy data and
								unallocated resources.</td>
						</tr>

						<tr>
							<td><a target="_blank"
								href="${reportPath }RM_detailsofEmployee_ROLE"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b> RM Details Of Employee</b>
							</a></td>

							<td>This report shows details of all employees depending
								upon filters.</td>
						</tr>

						<tr>
							<td><a target="_blank"
								href="${reportPath }TeamWise_Report_ROLE"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b>TeamWise Report</b>
							</a></td>

							<td>This report shows the Project details of employees
								teamwise and vice versa.</td>
						</tr>

						<tr>
							<td><a target="_blank"
								href="${reportPath }TimeSheetcompliance_ROLE"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b>Timesheet Compliance Report</b>
							</a></td>

							<td>Details of employees who have not filled timesheets.</td>
						</tr>


						<tr>
							<td><a target="_blank"
								href="${reportPath }UserActivity_ROLE"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b>User Activity Report</b>
							</a></td>

							<td>Timesheet details of employees according to week and
								status.</td>
						</tr>

						<tr>
							<td><a target="_blank"
								href="${reportPath }UtilizationDetailReport_ROLE"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b>Utilization Detail Report</b>
							</a></td>

							<td>Timesheet details of employees according to week .</td>
						</tr>

						<tr>
							<td><a target="_blank"
								href="${reportPathCMMI }CMMI_Analysis"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b>CMMI Analysis</b>
							</a></td>

							<td>Details of CA Tickets Status(Defect_log,t3Contrbuion
								etc).</td>
						</tr>

						<tr>
							<td><a target="_blank"
								href="${reportPathCMMI }CMMI_Analysis_Date"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b>CMMI Analysis Date</b>
							</a></td>

							<td>Details of CA Tickets Status (Dates).</td>
						</tr>

						<tr>
							<td><a target="_blank"
								href="${reportPathCMMI }CMMI_Dashboard"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b>CMMI Dashboard</b>
							</a></td>

							<td>Details of CA Tickets Status (Along with Indicators).</td>
						</tr>

						<tr>
							<td><a target="_blank" href="${reportPathCMMI }CMMI_Dates">
									<img src="resources/images/reportIcon.png" height="16"
									width="16" align="absMiddle" /> <b>CMMI Dates</b>
							</a></td>

							<td>Details of CA Tickets Status.</td>
						</tr>

						<tr>
							<td><a target="_blank"
								href="${reportPathCMMI }CMMI_Module_Wise_SLA"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b>CMMI Module Wise SLA </b>
							</a></td>

							<td>Details of CA Tickets Status(SLA Percent).</td>
						</tr>

						<tr>
							<td><a target="_blank"
								href="${reportPathCMMI }CMMI_Problem_Management"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b>CMMI Problem Management</b>
							</a></td>

							<td>Details of CA Tickets Status(Problem Management).</td>
						</tr>

						<tr>
							<td><a target="_blank"
								href="${reportPathCMMI }CMMI_Solution_Review"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b>CMMI Solution Review</b>
							</a></td>

							<td>Details of CA Tickets Status(Solution Review Data).</td>
						</tr>

						<tr>
							<td><a target="_blank"
								href="${reportPathCMMI }CMMI_Utilization_MonitoringOpen"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b>CMMI Utilization MonitoringOpen</b>
							</a></td>

							<td>Utilization percentage detail of CA Ticket(Utilization
								Percent).</td>
						</tr>
					</c:if>

					<c:if test="${reportUser==2 || reportUser==3 }">

						<tr>
							<td><a target="_blank"
								href="${reportPathSEPG }Parent_Project_Report_SEPG"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b>Parent Project Report</b>
							</a></td>

							<td>Hour details of employees according to project and
								vice-versa .</td>
						</tr>

						<tr>
							<td><a target="_blank"
								href="${reportPathSEPG }SEPG_Resource_Details"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b>SEPG Resource Details</b>
							</a></td>

							<td>Details of Employees based on filters.</td>
						</tr>

						<tr>
							<td><a target="_blank"
								href="${reportPathSEPG }SEPG_TimeSheet_Details"> <img
									src="resources/images/reportIcon.png" height="16" width="16"
									align="absMiddle" /> <b> SEPG_Timesheet_Details</b>
							</a></td>

							<td>Timesheet details of employees according to week .</td>
						</tr>
					</c:if>





				</tbody>

			</table>
		</div>
	</div>
	
        


</body>
</html>