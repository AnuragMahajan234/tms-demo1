<%@page import="org.yash.rms.util.UserUtil"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%-- <spring:url value="/editProfile/showProfile" var="editProfile" /> --%>
<%-- <spring:url value="/help" var="help" /> --%>
<%-- <spring:url value="/releasenotes" var="releasenotes" /> --%>

<style>
.new_blink {
	display: inline-block;
	top: -10px;
	left: -12px;
	position: relative;
	font-size: 10px;
	/*transform: translateX(-50%) translateY(-50%);*/
	text-align: center;
	animation: blink 1s ease-in-out infinite;
	background: green;
	width: 30px;
	height: 15px;
	border-top-left-radius: 9px;
	border-top-right-radius: 5px;
	border-bottom-right-radius: 5px;
}

@
keyframes blink { 0% {
	background: #198c19;
}

30%
{
background
:
#329932
;

		  
}
60%
{
background
:
#198c19
;
 
}
80%
{
background
:green
;

		  
}
}
.circle {
	background-color: rgba(0, 0, 0, 0);
	border: 5px solid rgba(0, 183, 229, 0.9);
	opacity: .9;
	border-right: 5px solid rgba(0, 0, 0, 0);
	border-left: 5px solid rgba(0, 0, 0, 0);
	border-radius: 50px;
	box-shadow: 0 0 35px #2187e7;
	width: 50px;
	height: 50px;
	margin: 0 auto;
	-moz-animation: spinPulse 1s infinite ease-in-out;
	-webkit-animation: spinPulse 1s infinite linear;
}

.circle1 {
	background-color: rgba(0, 0, 0, 0);
	border: 5px solid rgba(0, 183, 229, 0.9);
	opacity: .9;
	border-left: 5px solid rgba(0, 0, 0, 0);
	border-right: 5px solid rgba(0, 0, 0, 0);
	border-radius: 50px;
	box-shadow: 0 0 15px #2187e7;
	width: 30px;
	height: 30px;
	margin: 0 auto;
	position: relative;
	top: -50px;
	-moz-animation: spinoffPulse 1s infinite linear;
	-webkit-animation: spinoffPulse 1s infinite linear;
}

@
-moz-keyframes spinPulse { 0% {
	-moz-transform: rotate(160deg);
	opacity: 0;
	box-shadow: 0 0 1px #2187e7;
}

50%
{
-moz-transform
:rotate(145deg)
;


opacity
:
1;


}
100%
{
-moz-transform
:rotate(-320deg)
;


opacity
:
0;


}
}
@
-moz-keyframes spinoffPulse { 0% {
	-moz-transform: rotate(0deg);
}

100%
{
-moz-transform
:rotate(360deg)
;


}
}
@
-webkit-keyframes spinPulse { 0% {
	-webkit-transform: rotate(160deg);
	opacity: 0;
	box-shadow: 0 0 1px #2187e7;
}

50%
{
-webkit-transform
:rotate(145deg)
;


opacity
:
1;


}
100%
{
-webkit-transform
:rotate(-320deg)


;
opacity
:
0;


}
}
@
-webkit-keyframes spinoffPulse { 0% {
	-webkit-transform: rotate(0deg);
}
100%
{
-webkit-transform
:rotate(360deg)
;


}
}
</style>
<script>
$(document).ready(function(){
	$('#menuUserActivityList1').click(function(){
		localStorage.removeItem("butEnable");
	});
	function startProgress() {
		$.blockUI({
			message : '</br></br><div class="circle"></div><div class="circle1"></div><center>Just a moment</center>'
		});
	}
	function stopProgress() {
		$.unblockUI();
	}

});
</script>
<sec:authorize
	access="hasAnyRole('ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
	<c:set var="flag" value="1" />
</sec:authorize>

<sec:authorize
	access="hasAnyRole('ROLE_USER','ROLE_HR','ROLE_SEPG_USER','ROLE_ADMIN')">
	<c:set var="flag" value="0" />
</sec:authorize>

<aside class="main-sidebar">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">
		<!-- Sidebar user panel -->
		<!-- sidebar menu: : style can be found in sidebar.less -->
		<ul class="sidebar-menu" id="sidebarMenu">
			<li class="treeview active" id="dashboardList"  title="Dashboard">
				<c:choose>
					<c:when test="${flag==1}">
						<a href="/rms/dashboard/admindashboard"><i class="fa fa-dashboard"></i> <span>Dashboard</span></a>
					</c:when>
					<c:otherwise>
						<a href="/rms/dashboard/userdashboard"><i class="fa fa-dashboard"></i> <span>Dashboard</span></a>
					</c:otherwise>
				</c:choose>				
			</li>
			<sec:authorize
				access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_DEL_MANAGER','ROLE_HR')">
				<li class="treeview" id="adminList"  title="Admin"><a
					href="javascript:void(0);"> <i class="fa fa-user"></i> <span>Admin</span><i
						class="fa fa-angle-right pull-right"></i>
				</a>
					<ul class="treeview-menu">
						<spring:url value="/projects" var="projectsList" htmlEscape="true" />
						<spring:url value="/resources" var="resourcesList"
							htmlEscape="true" />
						<spring:url value="/orghierarchys" var="orghierarchysList"
							htmlEscape="true" />
						<sec:authorize
							access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_DEL_MANAGER','ROLE_HR')">
							<li><h3 class="side-bar-title">Admin</h3></li>
							<li id="menuResourceListId"><a id="menuResourceList"
								href="${resourcesList}" type="html">Resources</a></li>
						</sec:authorize>
						<sec:authorize
							access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_DEL_MANAGER','ROLE_HR')">
							<li id="menuProjectListId"><a id="menuProjectList"
								href="${projectsList}">Projects</a></li>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')">
							<spring:url value="/customers" var="customerList"
								htmlEscape="true" />
							<li id="menuCustomerListId"><a id="menuCustomerList"
								href="${customerList}">Customers</a></li>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
							<c:if
								test="<%=UserUtil.getCurrentResource().getEmployeeId() == 430
								|| UserUtil.getCurrentResource().getEmployeeId() == 402
								|| UserUtil.getCurrentResource().getEmployeeId() == 221
								|| UserUtil.getCurrentResource().getEmployeeId() == 4905
								|| UserUtil.getCurrentResource().getEmployeeId() == 5680
								|| UserUtil.getCurrentResource().getEmployeeId() == 5766%>">
								<spring:url value="/process" var="process" htmlEscape="true" />
								<li id="processId"><a id="processId" href="${process}">Process</a></li>
							</c:if>
						</sec:authorize>
					</ul></li>
			</sec:authorize>
			<li class="treeview" id="transactionList"  title="Transaction"><a
				href="javascript:void(0);"> <i class="fa fa-book"></i> <span>Transaction</span><i
					class="fa fa-angle-right pull-right"></i>
			</a>
				<ul class="treeview-menu">
				<li><h3 class="side-bar-title">Transaction</h3></li>
					<spring:url value="/resourceallocations"
						var="resourceallocationsList" htmlEscape="true" />
					<spring:url value="/projectallocations"
						var="projectallocationsList" htmlEscape="true" />
					<spring:url value="/timehrses" var="timehrsesList"
						htmlEscape="true" />
					<spring:url value="/projectactivitys" var="projectActivityList"
						htmlEscape="true" />
					<spring:url value="/projectTickets" var="projectTickets"
						htmlEscape="true" />
					<spring:url value="/loanAndTransfer" var="loanAndTransfer"
						htmlEscape="true" />
					<spring:url value="/dashboard/userdashboard" var="dashboard"
						htmlEscape="true" />

					<spring:url value="/dashboard/admindashboard" var="admindashboard"
						htmlEscape="true" />
					<sec:authorize
						access="hasAnyRole('ROLE_MANAGER','ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
						<li id="menuResourceAllocationId"><a
							id="menuResourceAllocation" href="${resourceallocationsList}"
							type="text">Resource Allocation</a></li>
						<li id="menuProjectAllocationId"><a
							id="menuProjectAllocation" href="${projectallocationsList}">Project Allocation</a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAnyRole('ROLE_MANAGER','ROLE_BEHALF_MANAGER','ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
						<li id="menuTimeHoursEntryId"><a id="menuTimeHoursEntry"
							href="/rms/timehrses?j=0">Timesheet Approval</a></li>
					</sec:authorize>

					<c:if test="<%=UserUtil.isCurrentUserinSEPGUserRole()%>">
						<c:if test="<%=UserUtil.getCurrentResource().isBehalfManager()%>">
							<li id="menuTimeHoursEntryId"><a id="menuTimeHoursEntry"
								href="${timehrsesList}">Timesheet Approval</a></li>
						</c:if>
					</c:if>

					<c:if
						test="<%=UserUtil.getCurrentResource().getEmployeeId() == 430%>">
						<li id="resourceOnBenchId"><a href="/rms/resourceOnBench">Resource On Bench </a></li>
					</c:if>


					<li id="menuUserActivityListId"><a id="menuUserActivityList"
						href="" onclick="setDates()">Timesheet Submission </a></li>

					<sec:authorize
						access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_HR')">
						<li id="menuUserActivityList1Id"><a
							id="menuUserActivityList1" href="${loanAndTransfer}">Loan or Transfer Resources</a></li>
					</sec:authorize>


				</ul>
				</li>
			<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
				<li class="treeview" id="masterDataList"  title="Master Data"><a
					href="javascript:void(0);"> <i class="fa fa-database"></i> <span>Master
							Data</span><i class="fa fa-angle-right pull-right"></i>
				</a>
					<ul class="treeview-menu">
					<li><h3 class="side-bar-title">Master Data</h3></li>
						<spring:url value="/activitys" var="activitysList"
							htmlEscape="true" />
						<spring:url value="/allocationtypes" var="allocationtypesList"
							htmlEscape="true" />
						<spring:url value="/orghierarchys" var="orghierarchysList"
							htmlEscape="true" />
						<spring:url value="/currencys" var="currencysList"
							htmlEscape="true" />
						<spring:url value="/designationses" var="designationsList"
							htmlEscape="true" />
						<spring:url value="/engagementmodels" var="engagementModelList"
							htmlEscape="true" />
						<spring:url value="/grades" var="gradeList" htmlEscape="true" />
						<spring:url value="/invoicebys" var="invoicebysList"
							htmlEscape="true" />
						<spring:url value="/locations" var="locationList"
							htmlEscape="true" />
						<spring:url value="/modules" var="modulesList" htmlEscape="true" />
						<spring:url value="/subModules" var="subModulesList"
							htmlEscape="true" />
						<spring:url value="/ownerships" var="ownershipsList"
							htmlEscape="true" />
						<spring:url value="/employeeCategories"
							var="employeeCategoriesList" htmlEscape="true" />
						<spring:url value="/competencies" var="competencylist"
							htmlEscape="true" />
						<spring:url value="/projectcategorys" var="projectcategorysList"
							htmlEscape="true" />
						<spring:url value="/projectmethodologys"
							var="projectmethodologyList" htmlEscape="true" />

						<spring:url value="/rateids" var="rateidList" htmlEscape="true" />
						<spring:url value="/skillses" var="skillsList" htmlEscape="true" />
						<spring:url value="/visas" var="visaList" htmlEscape="true" />
						<spring:url value="/teams" var="teamList" htmlEscape="true" />
						<spring:url value="/events" var="eventList" htmlEscape="true" />
						<spring:url value="/pdls" var="pdlList" htmlEscape="true" />
						<spring:url value="/messageboard/getallmessages" var="messageList" htmlEscape="true" />
						<spring:url value="/getallinfogramactiveresources" var="infoActiveList" htmlEscape="true" />
						<spring:url value="/getinfogramainactiveresources" var="infoInactiveList" htmlEscape="true" />
						<li id="timesheetAttributeList"><a href="javascript:void(0);">Timesheet Attribute <i
								class="fa fa-angle-down pull-right"></i></a>
							<ul class="treeview-menu-child">
								<li id="menuActivityListId"><a id="menuActivityList"
									href="${activitysList}">Activity</a></li>
								<li id="menuAllocationtypesListId"><a
									id="menuAllocationtypesList" href="${allocationtypesList}">Allocation Type</a></li>
							</ul></li>
						<li id="projectAttributeList"><a href="javascript:void(0);">Project Attribute <i
								class="fa fa-angle-down pull-right"></i></a>
							<ul class="treeview-menu-child">
								<li id="menuCurrencysListId"><a id="menuCurrencysList"
									href="${currencysList}">Currency</a></li>
								<li id="menuEngagementModelListId"><a
									id="menuEngagementModelList" href="${engagementModelList}">Engagement Model</a></li>
								<li id="menuInvoicebysListId"><a id="menuInvoicebysList"
									href="${invoicebysList}">Invoice By</a></li>
								<li id="menuProjectcategorysListId"><a
									id="menuProjectcategorysList" href="${projectcategorysList}">Project Category</a></li>
								<li id="menuProjectMethodologyListId"><a
									id="menuProjectMethodologyList"
									href="${projectmethodologyList}">Project Methodology</a></li>
							</ul></li>
						<li id="resourceAttributeList"><a href="javascript:void(0);">Resource Attribute <i
								class="fa fa-angle-down pull-right"></i></a>
							<ul class="treeview-menu-child">
								<li id="menuDesignationsListId"><a
									id="menuDesignationsList" href="${designationsList}">Designation</a></li>
								<li id="menuGradeListId"><a id="menuGradeList"
									href="${gradeList}">Grade</a></li>
								<li id="menuLocationListId"><a id="menuLocationList"
									href="${locationList}">Location</a></li>
								<li id="menuOwnershipsListId"><a id="menuOwnershipsList"
									href="${ownershipsList}">Ownership</a></li>
								<li id="menuEmployeeCategoriesListId"><a
									id="menuEmployeeCategoriesList"
									href="${employeeCategoriesList}">Employee Category</a></li>
								<li id="menuCompetencyListId"><a id="menuCompetencyList"
									href="${competencylist}">Competency</a></li>
								<li id="menuSkillsListId"><a id="menuSkillsList"
									href="${skillsList}">Skill</a></li>
								<li id="menuVisaListId"><a id="menuVisaList"
									href="${visaList}">Visa</a></li>
							</ul></li>
						<li id="miscAttributeList"><a href="javascript:void(0);">Misc. Attribute <i
								class="fa fa-angle-down pull-right"></i></a>
							<ul class="treeview-menu-child">
								<li id="menuOrganizationListId"><a
									id="menuOrganizationList" href="${orghierarchysList}"> BG/BU</a></li>
								<li id="menuTeamListId"><a id="menuTeamList"
									href="${teamList}">Parent Project / Team</a></li>
								<li id="menuEventListId"><a id="menuEventList"
									href="${eventList}">Event</a></li>
								<li id="menuPdlListId"><a id="menuPdlList"
									href="${pdlList}">PDL</a></li>
								
							</ul></li>
							<li id="infoList"><a href="javascript:void(0);">Infogram <i
								class="fa fa-angle-down pull-right"></i></a>
							<ul class="treeview-menu-child">
								
								<li id="menuInfoActListId"><a id="menuPdlList"
									href="${infoActiveList}">Active Employee</a></li>
								<li id="menuInfoInactListId"><a id="menuPdlList"
									href="${infoInactiveList}">Inactive Employee</a></li>
							</ul></li>
							
							<!-- Message Board -->
							<li id="messageBoardListId"><a id="messageList"
									href="${messageList}">Message Board</a></li>

					</ul></li>
			</sec:authorize>

			<c:choose>

				<c:when test="<%=UserUtil.isCurrentUserinSEPGUserRole()%>">
					<c:choose>
						<c:when
							test="<%=UserUtil.getCurrentResource().isBehalfManager()%>">
							<li class="treeview" id="menuReportList"  title="Reports"><a
								href="javascript:void(0);"> <i class="fa fa-bar-chart"></i>
									<span>Reports</span><i class="fa fa-angle-right pull-right"></i>
							</a>
								<ul class="treeview-menu">
									<li><h3 class="side-bar-title">Reports</h3></li>
									<%-- <spring:url value="/reports" var="reportList" htmlEscape="true" />
									<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
										<li id="menuReportListId"><a id="reports"
											href="${reportList}">Reports</a></li>
									</sec:authorize> --%>
									<spring:url value="/reports/javaReports" var="reportList"
										htmlEscape="true" />

									<li id="menuReportListIdjava"><a id="reportsjava"
										href="${reportList}">Reports</a></li>
								</ul></li>
						</c:when>
						<c:otherwise>
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_SEPG_USER','ROLE_HR')">
								<li class="treeview" id="menuReportList"  title="Reports"><a
									href="javascript:void(0);"> <i class="fa fa-bar-chart"></i>
										<span>Reports</span><i class="fa fa-angle-right pull-right"></i>
								</a>
									<ul class="treeview-menu">
										<li><h3 class="side-bar-title">Reports</h3></li>
										<%-- <spring:url value="/reports" var="reportList"
											htmlEscape="true" />
										<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
											<li id="menuReportListId"><a id="reports"
												href="${reportList}">Reports</a></li>
										</sec:authorize> --%>
										<spring:url value="/reports/javaReports" var="reportList"
											htmlEscape="true" />

										<li id="menuReportListIdjava"><a id="reportsjava"
											href="${reportList}">Reports</a></li>
									</ul></li>
							</sec:authorize>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<sec:authorize
						access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_SEPG_USER','ROLE_HR')">
						<li class="treeview" id="menuReportList"  title="Reports"><a
							href="javascript:void(0);"> <i class="fa fa-file-text-o"></i> <span>Reports</span><i
								class="fa fa-angle-right pull-right"></i>
						</a>
							<ul class="treeview-menu">
								<li><h3 class="side-bar-title">Reports</h3></li>
								<%-- <spring:url value="/reports" var="reportList" htmlEscape="true" />
								<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
									<li id="menuReportListId"><a id="reports"
										href="${reportList}">Reports</a></li>
								</sec:authorize> --%>
								<spring:url value="/reports/javaReports" var="reportList"
									htmlEscape="true" />

								<li id="menuReportListIdjava"><a id="reportsjava"
									href="${reportList}">Reports</a></li>

							</ul></li>
					</sec:authorize>
				</c:otherwise>
			</c:choose>

			<%-- Added for Prachi Rao, zoheb, mayank maheshwari: Module Report access only --%>

			<c:if
				test="<%=UserUtil.getCurrentResource().getEmployeeId() == 454
						|| UserUtil.getCurrentResource().getEmployeeId() == 4334
						|| UserUtil.getCurrentResource().getEmployeeId() == 627%>">

				<li class="treeview" id="menuReportList"  title="Reports"><a
					href="javascript:void(0);"> <i class="fa fa-file-text-o"></i> <span>Reports</span><i
						class="fa fa-angle-right pull-right"></i>
				</a>
					<ul class="treeview-menu">
						<li><h3 class="side-bar-title">Reports</h3></li>
					<%-- 	<spring:url value="/reports" var="reportList" htmlEscape="true" />
						<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
							<li id="menuReportListId"><a id="reports"
								href="${reportList}">Reports</a></li>
						</sec:authorize> --%>

						<spring:url value="/reports/javaReports" var="reportList"
							htmlEscape="true" />

						<li id="menuReportListIdjava"><a id="reportsjava"
							href="${reportList}">Reports</a></li>
					</ul></li>
			</c:if>

			<sec:authorize
				access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
				<li class="treeview" id="mailConfigurationList"  title="Configurations">
				<a href="javascript:void(0);"> <i class="fa fa-cog"></i> <span>
							Configurations</span><i class="fa fa-angle-right pull-right"></i></a>
						<ul class="treeview-menu">
							<li><h3 class="side-bar-title">Configurations</h3></li>
							<spring:url value="/modules/listProject" var="modules"
								htmlEscape="true" />
							<!--subModules  -->
							<spring:url value="/subModules/listModule" var="subModules"
								htmlEscape="true" />
							<!--subModules  -->
							<spring:url value="/mailConfiguration" var="mailConfiguration"
								htmlEscape="true" />
							<li id="mailConfigurationListId"><a
								id="menuMailConfiguration" href="${mailConfiguration}">Configure Mail Settings </a></li>
							<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')">

								<spring:url value="/defaultprojects" var="defaultProject"
									htmlEscape="true" />
								<li id="defaultProjectListId"><a
									id="menudefaultProjectList" href="${defaultProject}">Set Default Project for new Resources</a></li>
							</sec:authorize>
							<sec:authorize
								access="hasAnyRole('ROLE_MANAGER','ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
								<li id="menuProjectActivityId"><a id="menuProjectActivity"
									href="${projectActivityList}">Configure Project Activity</a></li>
							</sec:authorize>
							<li id="menuListProjectId"><a id="menuListProject"
								href="${modules}">Configure Project Module</a></li>
							<li id="menuListProjectSubModuleId"><a
								id="menuListProjectSubModule" href="${subModules}">Configure Project Sub Module</a></li>
							<li id="menuProjectTicketsId"><a id="menuProjectTickets"
								href="${projectTickets}">Configure Project Ticket
							</a></li>
						</ul></li>
			</sec:authorize>

			<%-- <sec:authorize
				access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_BG_ADMIN','ROLE_DEL_MANAGER','ROLE_HR')">
				<li class="treeview" id="requestList"><a
					href="/rms/requestsReports/positionDashboard"><i
						class="fa fa-exchange"></i> <span>Resource Requests</span></a></li>
				<!-- This is old code was used for dropdown -->
				
				<li class="treeview" id="requestList"  title="Resource Requests"><a
					href="javascript:void(0);"> <i class="fa fa-exchange"></i> <span>
							Resource Requests </span>
						<div class="new-badge-main"><div class="new_blink">new</div></div>
						<i class="fa fa-angle-right pull-right"></i></a>
						<ul class="treeview-menu">
							<li><h3 class="side-bar-title">Resource Requests</h3></li>
							<spring:url value="/requests" var="requestDetail"
								htmlEscape="true" />
							<li id="resourceRequisitionId"><a id="resourceRequisition"
								href="${requestDetail}">Resource Requisition Form </a></li> 
							<spring:url value="/requestsReports/positionDashboard"
								var="positionDashboard" htmlEscape="true" />
							<li id="positionDashboardId"><a id="positionDashboard"
								href="${positionDashboard}">Position DashBoard</a></li>
						</ul></li>
			</sec:authorize> --%>
			
			
			<sec:authorize
				access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_BG_ADMIN','ROLE_DEL_MANAGER','ROLE_HR')">
				<li class="treeview" id="requestList"><a
					href="/rms/requestsReports/positionDashboard"><i
						class="fa fa-exchange"></i> <span>Resource Requests</span>
						 
						</a></li>
			</sec:authorize> 
			
			
			
			<%-- <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_BG_ADMIN','ROLE_DEL_MANAGER','ROLE_HR')">
				<li class="treeview" id="requestList"><a href="javascript:void(0);">
				<i class="fa fa-dashboard"></i> <span>
							Resource Requests </span>
							<div class="new_blink">new</div><i class="fa fa-angle-right pull-right"></i>
							</a>
						<ul class="treeview-menu">

							<spring:url value="/requests" var="requestDetail"
								htmlEscape="true" />
							<li id="resourceRequisitionId"><a id="resourceRequisition"
								href="${requestDetail}"><i class="fa fa-th-large"></i>
									Resource Requisition Form </a></li>
				<spring:url value="/requestsReports/positionDashboard" var="positionDashboard"
								htmlEscape="true" />	
							<li id="positionDashboardId"><a id="positionDashboard"
								href="${positionDashboard}"><i class="fa fa-th-large"></i>
									Position Dashboard</a></li>		
						</ul>
				</li>
				</sec:authorize> --%>




			<sec:authorize
				access="!hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_DEL_MANAGER','ROLE_HR','ROLE_MANAGER')">
				<c:choose>
					<c:when test="<%=UserUtil.getCurrentResource().isRRFAccess()%>">
						<li class="treeview" id="requestList"><a
							href="/rms/requestsReports/positionDashboard"><i
								class="fa fa-exchange"></i> <span>Resource Requests</span></a></li>
					</c:when>
					<c:otherwise>
						<c:if
							test='<%=!UserUtil.getCurrentResource().getGrade().equalsIgnoreCase("E1")
									&& !UserUtil.getCurrentResource().getGrade().equalsIgnoreCase("E2")
									&& !UserUtil.getCurrentResource().getGrade().equalsIgnoreCase("E3")
									&& !UserUtil.getCurrentResource().getGrade().equalsIgnoreCase("T")
									&& !UserUtil.getCurrentResource().getGrade().equalsIgnoreCase("AT")%>'>
							<li class="treeview" id="requestList"><a
								href="/rms/requestsReports/positionDashboard"> <i
									class="fa fa-exchange"></i> <span>Resource Requests</span>
							</a></li>
						</c:if>
					</c:otherwise>
				</c:choose>
			</sec:authorize>

			<sec:authorize
				access="!hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_DEL_MANAGER','ROLE_SEPG_USER','ROLE_HR')">
				<c:choose>
					<c:when test="<%=UserUtil.getCurrentResource().isReportAccess()%>">
						<li class="treeview" id="menuReportList" title="Reports"><a
							href="javascript:void(0);"> <i class="fa fa-file-text-o"></i>
								<span>Reports</span><i class="fa fa-angle-right pull-right"></i>
						</a>
							<ul class="treeview-menu">
								<li><h3 class="side-bar-title">Reports</h3></li>

								<spring:url value="/reports/javaReports" var="reportList"
									htmlEscape="true" />

								<li id="menuReportListIdjava"><a id="reportsjava"
									href="${reportList}">Reports</a></li>
							</ul></li>
					</c:when>
					<c:otherwise>
						<c:if test='<%=!UserUtil.getCurrentResource().getGrade().equalsIgnoreCase("E1")
									&& !UserUtil.getCurrentResource().getGrade().equalsIgnoreCase("E2")
									&& !UserUtil.getCurrentResource().getGrade().equalsIgnoreCase("E3")
									&& !UserUtil.getCurrentResource().getGrade().equalsIgnoreCase("T")
									&& !UserUtil.getCurrentResource().getGrade().equalsIgnoreCase("AT")%>'>
							<li class="treeview" id="menuReportList" title="Reports"><a
								href="javascript:void(0);"> <i class="fa fa-file-text-o"></i>
									<span>Reports</span><i class="fa fa-angle-right pull-right"></i>
							</a>
								<ul class="treeview-menu">
									<li><h3 class="side-bar-title">Reports</h3></li>

									<spring:url value="/reports/javaReports" var="reportList"
										htmlEscape="true" />

									<li id="menuReportListIdjava"><a id="reportsjava"
										href="${reportList}">Reports</a></li>
								</ul></li>
						</c:if>
					</c:otherwise>
				</c:choose>
			</sec:authorize>


			<!-- Merge RRF - Start -->	
			<%-- <spring:url value="/requestsReports/mergePositionDashboard"
									var="mergePositionDashboard" htmlEscape="true" />		
				<li class="treeview" id="mergePositionDashboardId"><a
					href="${mergePositionDashboard}"><i
						class="fa fa-exchange"></i> <span>Resource Requests - Merged</span></a></li> --%>			
			<!-- Merge RRF - END -->
			<sec:authorize access="hasAnyRole('ROLE_BG_ADMIN')">
				<li class="treeview" id="bgAdmin"  title="Graph Configuration"><a href="javascript:void(0);">
						<i class="fa fa-dashboard"></i> <span>Graph Configuration</span><i
						class="fa fa-angle-right pull-right"></i></a>

						<ul class="treeview-menu">
						<li><h3 class="side-bar-title">Graph Configuration</h3></li>
							<li><a id="bgAdmintreeviewList">
									<form style="margin-left: 30px;">
										<c:if test="${empty bgAdminSelectedList}">
											<c:forEach items="${bgAdminList}" var="item">
												<c:set var="arrayofmsg" value="${fn:split(item,':')}" />
												<li><INPUT TYPE="checkbox" name="admin_ids[]"
													VALUE="${arrayofmsg[0]}" checked>&nbsp;&nbsp;${arrayofmsg[2]}
													- ${arrayofmsg[1]}</input></li>
											</c:forEach>
										</c:if>
										<c:set var="ischecked" value="false" />
										<c:if test="${not empty bgAdminSelectedList}">
											<c:forEach items="${bgAdminList}" var="item">
												<c:forEach items="${bgAdminSelectedList}"
													var="selected_item">

													<c:set var="arrayofmsg" value="${fn:split(item,':')}" />
													<c:set var="arrayofselectedmsg"
														value="${fn:split(selected_item,':')}" />
													<c:choose>
														<c:when test="${arrayofmsg[1] == arrayofselectedmsg[1]}">
															<li><INPUT TYPE="checkbox" name="admin_ids[]"
																VALUE="${arrayofmsg[0]}" checked>&nbsp;&nbsp;${arrayofmsg[2]}
																- ${arrayofmsg[1]}</input></li>
															<c:set var="ischecked" value="true" />
														</c:when>
													</c:choose>
												</c:forEach>
												<c:if test="${ischecked == false}">
													<li><INPUT TYPE="checkbox" name="admin_ids[]"
														VALUE="${arrayofmsg[0]}">&nbsp;&nbsp;${arrayofmsg[2]}
														- ${arrayofmsg[1]}</input></li>
												</c:if>
												<c:set var="ischecked" value="false" />
											</c:forEach>
										</c:if>

										<!-- <a class="loadData" href="#">Save and View</a> -->
										<input name="confirm" type="button" style="margin-top: 10px;"
											class="loadData btn btn-primary" value="Save and View"
											href="#" />
									</form>
							</a></li>
						</ul></li>
			</sec:authorize>
			 <!-- WE have remove SEPG and My Workspace from menu_dashboard -->
			<%-- <sec:authorize access="hasAnyRole('ROLE_SEPG_USER','ROLE_ADMIN')">
				<li class="treeview" id="sepgList"  title="SEPG">
				<a href="javascript:void(0);"> <i class="fa fa-circle"></i> <span>SEPG</span><i
						class="fa fa-angle-right pull-right"></i></a>
						<ul class="treeview-menu">
							<li><h3 class="side-bar-title">SEPG</h3></li>
							<spring:url value="/activitys/sepgActivity" var="sepgActivity"
								htmlEscape="true" />
							<spring:url value="/sepgPhases" var="sepgPhasesList"
								htmlEscape="true" />
							<li id="sepgPhasesListId"><a id="menuSepgPhasesList"
								href="${sepgPhasesList}">SEPG Phases</a></li>
							<li id="sepgActivityListId"><a id="sepgActivity"
								href="${sepgActivity}">SEPG Activity </a></li>
						</ul></li>
			</sec:authorize> --%>

			<%-- <sec:authorize
				access="hasAnyRole('BG4-BU1','BG4-BU2','BG4-BU3','BG4-BU4','BG4-BU5','BG4-BU6','BG4-BUA', 'ROLE_ADMIN', 'BG1-BU15')">
				<li class="treeview"  title="My Workspace"><a href="javascript:void(0);" > <i
						class="fa fa-building-o"></i> <span>My Workspace</span><i
						class="fa fa-angle-right pull-right"></i></a>
						<ul class="treeview-menu">
							<li><h3 class="side-bar-title">My Workspace</h3></li>
							<spring:url value="/caticket/upload" var="uploadExcel"
								htmlEscape="true" />
							<spring:url value="/caticket/createTicketHome"
								var="createTicketHome" htmlEscape="true" />
							<spring:url value="/caticket/viewTicket" var="viewTicket"
								htmlEscape="true" />
							<spring:url value="/caticket/viewReviewTicket"
								var="viewReviewTicket" htmlEscape="true" />
							<spring:url value="/caticket/dashboard" var="dashboard"
								htmlEscape="true" />
							<spring:url value="/caticket/queue" var="queue" htmlEscape="true" />
							<spring:url value="/caticket/discrepency" var="discrepency"
								htmlEscape="true" />
							<li><a id="myDashboardId" href="${dashboard }">My Dashboard</a></li>
							<li><a id="myQueueId" href="${viewTicket }">My Ticket Queue</a></li>
							<li><a id="myQueue" href="${viewReviewTicket}">For My Review</a></li>
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
								<li><a id="uploadId" href="${uploadExcel }">Upload </a></li>
								<li><a id="discrepencyId" href="${discrepency}">Discrepency Records</a> </l>
							</sec:authorize>
						</ul></li>
			</sec:authorize> --%>
		</ul>
	</section>
	<!-- /.sidebar -->
</aside>
<script>

	$(document).ready(
			function() {

				$("select.brdrDd").parent().parent(".menuDropDwm").find("span.ui-combobox input").css("border", "1px solid #D5D5D5");
				$('a[id^="menu"]').removeClass('active treeview');
				var href = window.location.pathname;
				//	alert(href.toLowerCase().indexOf("defaultprojects"));
				if (href.toLowerCase().indexOf("defaultprojects") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').removeClass('active');
					$('#menuProjectListId').removeClass('activechild');
					$('#mailConfigurationList').addClass('active');
					$('#defaultProjectListId').addClass('activechild');

				}

				else if (href.toLowerCase().indexOf("customers") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').addClass('active');
					$('#menuCustomerListId').addClass('activechild');
				}else if (href.toLowerCase().indexOf("process") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').addClass('active');
					$('#processId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("projects") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').addClass('active');
					$('#menuProjectListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("resources") >= 0) {
					if (href.toLowerCase().indexOf("uploadresource") >= 0) {
						$('#menuUploadResourceList').addClass('active');
					} else {
						$('#dashboardList').removeClass('active');
						$('#adminList').addClass('active');
						$('#menuResourceListId').addClass('activechild');
					}

				} else if (href.toLowerCase().indexOf("resourceallocations") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').addClass('active');
					$('#menuResourceAllocationId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("projectallocations") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').addClass('active');
					$('#menuProjectAllocationId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("timehrses") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').addClass('active');
					$('#menuTimeHoursEntryId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("useractivitys") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').addClass('active');
					$('#menuUserActivityListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("projectactivitys") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#mailConfigurationList').addClass('active');
					$('#menuProjectActivityId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("projecttickets") >= 0) { 
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#mailConfigurationList').addClass('active');
					$('#menuProjectTicketsId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("listproject") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#mailConfigurationList').addClass('active');
					$('#menuListProjectId').addClass('activechild');
				} 
				else if (href.toLowerCase().indexOf("listmodule") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#mailConfigurationList').addClass('active');
					$('#menuListProjectSubModuleId').addClass('activechild');
				} 
				else if (href.toLowerCase().indexOf("loanandtransfer") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').addClass('active');
					$('#menuUserActivityList1Id').addClass('activechild');
				} else if (href.toLowerCase().indexOf("activitys") >= 0) {
					if (href.toLowerCase().indexOf("sepgactivity") >= 0) {
						$('#dashboardList').removeClass('active');
						$('#adminList').removeClass('active');
						$('#transactionList').removeClass('active');
						$('#masterDataList').removeClass('active');
						$('#sepgList').addClass('active');
						$('#sepgActivityListId').addClass('activechild');
					} else {
						$('#dashboardList').removeClass('active');
						$('#adminList').removeClass('active');
						$('#transactionList').removeClass('active');
						$('#masterDataList').addClass('active');
						$('#timesheetAttributeList').addClass('active');
						$('#menuActivityListId').addClass('activechild');
					}
				} else if (href.toLowerCase().indexOf("allocationtypes") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#timesheetAttributeList').addClass('active');
					$('#menuAllocationtypesListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("bus") >= 0) {
					$('#menuBusList').addClass('active');
				} else if (href.toLowerCase().indexOf("currencys") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#projectAttributeList').addClass('active');
					$('#menuCurrencysListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("designationses") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#resourceAttributeList').addClass('active');
					$('#menuDesignationsListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("invoicebys") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#menuInvoicebysListId').addClass('activechild');
					$('#projectAttributeList').addClass('active');
				} else if (href.toLowerCase().indexOf("modules") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#timesheetAttributeList').addClass('active');
					$('#menuModulesListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("ownerships") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#resourceAttributeList').addClass('active');
					$('#menuOwnershipsListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("employeecategories") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active treeview');
					$('#resourceAttributeList').addClass('active');
					$('#menuEmployeeCategoriesListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("competencies") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#resourceAttributeList').addClass('active');
					$('#menuCompetencyListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("projectcategorys") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#projectAttributeList').addClass('active');
					$('#menuProjectcategorysListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("locations") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#resourceAttributeList').addClass('active');
					$('#menuLocationListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("engagementmodels") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#projectAttributeList').addClass('active');
					$('#menuEngagementModelListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("grades") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#resourceAttributeList').addClass('active');
					$('#menuGradeListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("visas") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#resourceAttributeList').addClass('active');
					$('#menuVisaListId').addClass('activechild');
					/* }else if (href.toLowerCase().indexOf("rateids") >= 0){
						$('#menuRateidList').addClass('active'); */
				} else if (href.toLowerCase().indexOf("projectmethodologys") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#projectAttributeList').addClass('active');
					$('#menuProjectMethodologyListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("sepgphases") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').removeClass('active');
					$('#sepgList').addClass('active');
					$('#sepgPhasesListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("skillses") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#resourceAttributeList').addClass('active');
					$('#menuSkillsListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("jobtypes") >= 0) {
					$('#menuJobTypeList').addClass('active');
				} else if (href.toLowerCase().indexOf("orghierarchys") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#miscAttributeList').addClass('active');
					$('#menuOrganizationListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("events") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#miscAttributeList').addClass('active');
					$('#menuEventListId').addClass('activechild');
				} 
				else if (href.toLowerCase().indexOf("pdls") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#miscAttributeList').addClass('active');
					$('#menuPdlListId').addClass('activechild');
				}
				else if (href.toLowerCase().indexOf("/messageboard/getallmessages") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#messageBoardListId').addClass('activechild');
				}
				else if (href.toLowerCase().indexOf("teams") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').addClass('active');
					$('#miscAttributeList').addClass('active');
					$('#menuTeamListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("mailconfiguration") >= 0) {

					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').removeClass('active');
					$('#masterDataList').removeClass('active');
					$('#mailConfigurationList').addClass('active');
					$('#mailConfigurationListId').addClass('activechild');

				} else if (href.toLowerCase().indexOf("projectactivitys") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').addClass('active');

				} else if (href.toLowerCase().indexOf("projecttickets") >= 0) { 
					$('#dashboardList').removeClass('active');
					$('#adminList').removeClass('active');
					$('#transactionList').addClass('active');
				}else if(href.toLowerCase().indexOf("positiondashboard") >= 0){
					$('#dashboardList').removeClass('active');
					$('#requestList').addClass('active');
					$('#positionDashboardId').addClass('activechild');
				}else if (href.toLowerCase().indexOf("dashboard") >= 0) {
					$('#menuUserDashBoard').addClass('active');
				}else if (href.toLowerCase().indexOf("requestsreports") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#requestList').addClass('active');
					$('#requestReportsId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("rmreports") >= 0 || href.toLowerCase().indexOf("plreports") >= 0 || href.toLowerCase().indexOf("muneerreports") >= 0
						|| href.toLowerCase().indexOf("pwrreports") >= 0 || href.toLowerCase().indexOf("resourcemovementreports") >= 0 || href.toLowerCase().indexOf("teamreports") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#menuReportList').addClass('active');
					$('#menuReportListIdjava').addClass('activechild');
				} else if (href.toLowerCase().indexOf("javareports") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#menuReportList').addClass('active');
					$('#menuReportListIdjava').addClass('activechild');
				} else if (href.toLowerCase().indexOf("reports") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#menuReportList').addClass('active');
					$('#menuReportListId').addClass('activechild');
				} else if (href.toLowerCase().indexOf("requests") >= 0) {
					$('#dashboardList').removeClass('active');
					$('#requestList').addClass('active');
					$('#resourceRequisitionId').addClass('activechild');
				}

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
				if (mMonth == "12" || mMonth == "10" || mMonth == "8" || mMonth == "7" || mMonth == "8" || mMonth == "5" || mMonth == "3" || mMonth == "1") {
					/* mMonthStartDate = preMonth + "/" + "1" + "/"
							+ preYear; */
					mMonthStartDate = mMonth + "/" + "1" + "/" + mYear;
					mMonthEndDate = mMonth + "/" + "31" + "/" + mYear;
				} else {
					if (mMonth == "2") {
						if (mYear % 4 == 0) {
							/* mMonthStartDate = preMonth + "/" + "1"
									+ "/" + preYear; */
							mMonthStartDate = mMonth + "/" + "1" + "/" + mYear;
							mMonthEndDate = mMonth + "/" + "29" + "/" + mYear;
						} else {
							/* mMonthStartDate = preMonth + "/" + "1"
									+ "/" + preYear; */
							mMonthStartDate = mMonth + "/" + "1" + "/" + mYear;
							mMonthEndDate = mMonth + "/" + "28" + "/" + mYear;
						}
					} else {
						/* mMonthStartDate = preMonth + "/" + "1"
								+ "/" + preYear; */
						mMonthStartDate = mMonth + "/" + "1" + "/" + mYear;
						mMonthEndDate = mMonth + "/" + "30" + "/" + mYear;
					}
				}
				document.getElementById('menuUserActivityList').href = "/rms/useractivitys?minWeekStartDate=" + mMonthStartDate + "&maxWeekStartDate=" + mMonthEndDate + "&i=" + 0
						+ "&find=ByWeekStartDateBetweenAndEmployeeIdView";
				
				// breaking full name into firstname
				/* var username = $('#username').text();
				var result = username.split(" ");
				var fname = result[0];
				var lname = result[1];
				document.getElementById("username").innerHTML = fname; */
			});
</script>
