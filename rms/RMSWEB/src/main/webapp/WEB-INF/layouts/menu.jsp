<%@page import="org.yash.rms.util.UserUtil"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<sec:authorize
	access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_BEHALF_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR','ROLE_SEPG_USER')">
	<input type="hidden" name="mStartDate" id="mStartDate">
	<input type="hidden" name="mEndDate" id="mEndDate">
	<spring:url value="/resourceallocations" var="resourceallocationsList"
		htmlEscape="true" />
	<spring:url value="/projectallocations" var="projectallocationsList"
		htmlEscape="true" />
	<spring:url value="/timehrses" var="timehrsesList" htmlEscape="true" />
	<spring:url value="/projectactivitys" var="projectActivityList"
		htmlEscape="true" />
	<spring:url value="/loanAndTransfer" var="loanAndTransfer"
		htmlEscape="true" />
	<spring:url value="/dashboard/userdashboard" var="dashboard"
		htmlEscape="true" />

	<spring:url value="/dashboard/admindashboard" var="admindashboard"
		htmlEscape="true" />

	<!-- Updated for task: #1285[Customer should not be visible to ROLE_DEL_MANAGER] -->

	<ul class="navMenu fl">
		<sec:authorize
			access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_DEL_MANAGER','ROLE_HR')">
			<li>Admin
				<ul class="subMenu">

					<spring:url value="/projects" var="projectsList" htmlEscape="true" />
					<spring:url value="/resources" var="resourcesList"
						htmlEscape="true" />
					<spring:url value="/orghierarchys" var="orghierarchysList"
						htmlEscape="true" />
					<spring:url value="/uploadresources/upload"
						var="uploadResourcesList" htmlEscape="true" />
					<sec:authorize
						access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_DEL_MANAGER','ROLE_HR')">
						<li><a id="menuResourceList" href="${resourcesList}"
							type="html">Resources</a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_DEL_MANAGER')">
						<li><a id="menuProjectList" href="${projectsList}">Projects</a></li>

						<sec:authorize
							access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_HR')">
							<spring:url value="/customers" var="customerList"
								htmlEscape="true" />
							<li><a id="menuCustomerList" href="${customerList}">Customers</a></li>
						</sec:authorize>
					</sec:authorize>

				</ul>
		</sec:authorize>
		<%-- <ul class="navMenu fl">
	 <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')"> 
	<li>Admin
		<ul class="subMenu">
			<spring:url value="/customers" var="customerList"  htmlEscape="true"/>
			<spring:url value="/projects" var="projectsList"  htmlEscape="true"/>
			<spring:url value="/resources" var="resourcesList"  htmlEscape="true"/>
			<spring:url value="/orghierarchys" var="orghierarchysList"  htmlEscape="true"/>
			<spring:url value="/uploadresources/upload" var="uploadResourcesList"  htmlEscape="true"  />
			<spring:url value="/mailConfiguration" var="mailConfiguration"  htmlEscape="true"  />
			<li><a id="menuResourceList" href="${resourcesList}" type="html">Resources</a></li>
			<li><a id="menuProjectList" href="${projectsList}">Projects</a></li>
			<li><a id="menuCustomerList" href="${customerList}">Customers</a></li>
				
		</ul> 
	</li>--%>
		<%-- </sec:authorize>  --%>
		<%-- <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_BEHALF_MANAGER')"> --%>
		<li>Transaction
			<ul class="subMenu" style="z-index: 999 !important;">
				<sec:authorize
					access="hasAnyRole('ROLE_MANAGER','ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
					<li><a id="menuResourceAllocation"
						href="${resourceallocationsList}" type="text">Resource
							Allocation</a></li>
					<li><a id="menuProjectAllocation"
						href="${projectallocationsList}">Project Allocation</a></li>
				</sec:authorize>
				<sec:authorize
					access="hasAnyRole('ROLE_MANAGER','ROLE_BEHALF_MANAGER','ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
					<li><a id="menuTimeHoursEntry" href="${timehrsesList}">Timesheet
							Approval</a></li>
				</sec:authorize>


				<c:if test="<%=UserUtil.isCurrentUserinSEPGUserRole()%>">
					<c:if
						test="<%=UserUtil.getCurrentResource()
								.isBehalfManager()%>">
						<li><a id="menuTimeHoursEntry" href="${timehrsesList}">Timesheet
								Approval</a></li>
					</c:if>
				</c:if>

				<sec:authorize
					access="hasAnyRole('ROLE_MANAGER','ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
					<li><a id="menuProjectActivity" href="${projectActivityList}">Project
							Activity</a></li>
				</sec:authorize>

				<li><a id="menuUserActivityList" href="" onclick="setDates()">Timesheet
						Submission </a></li>
				<sec:authorize
					access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_HR')">
					<li><a id="menuUserActivityList" href="${loanAndTransfer}">Loan
							or Transfer Resources</a></li>
				</sec:authorize>
				<li><a id="menuUserDashBoard" href="${dashboard}">Dashboard</a></li>
				<li><a id="menuadminDashBoard" href="${admindashboard}">Admin
						Dashboard</a></li>
			</ul>
		</li>
		<%-- </sec:authorize>
	 <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')"> 
	    <li>Inquiry / Reports
		<ul class="subMenu">
		<spring:url value="http://10.6.16.166/Reports_DEVELOPMENT/Pages/Folder.aspx?ItemPath=%2fRMS_Reports&ViewMode=List" var="report"  htmlEscape="true"/>
		<spring:url value="/reports" var="reportList"  htmlEscape="true"/>
          <li><a id="menuActivityreports" href="${reportList}"> Reports</a></li>
        </ul>
	</li> 
	 </sec:authorize>--%>

		<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
			<li>Master Data
				<ul class="subMenu">
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
					<spring:url value="/locations" var="locationList" htmlEscape="true" />
					<spring:url value="/modules" var="modulesList" htmlEscape="true" />
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




					<div id="accordion-2">
						<h3>Timesheet Attribute</h3>
						<div>
							<li><a id="menuActivityList" href="${activitysList}">Activity</a></li>
							<li><a id="menuAllocationtypesList"
								href="${allocationtypesList}">Allocation Type</a></li>
							<%--   <li><a id="menuRateidList" href="${rateidList}">Rate</a></li> --%>
							<li><a id="menuModulesList" href="${modulesList}">Module
									/ Ticket No.</a></li>
						</div>

						<h3>Project Attribute</h3>
						<div>
							<li><a id="menuCurrencysList" href="${currencysList}">Currency</a></li>
							<li><a id="menuEngagementModelList"
								href="${engagementModelList}">Engagement Model</a></li>
							<li><a id="menuInvoicebysList" href="${invoicebysList}">Invoice
									By</a></li>
							<li><a id="menuProjectcategorysList"
								href="${projectcategorysList}">Project Category</a></li>
							<li><a id="menuProjectMethodologyList"
								href="${projectmethodologyList}">Project Methodology</a></li>

						</div>

						<h3>Resource Attribute</h3>
						<div>
							<li><a id="menuDesignationsList" href="${designationsList}">Designation</a></li>
							<li><a id="menuGradeList" href="${gradeList}">Grade</a></li>
							<li><a id="menuLocationList" href="${locationList}">Location</a></li>
							<li><a id="menuOwnershipsList" href="${ownershipsList}">Ownership</a></li>
							<li><a id="menuEmployeeCategoriesList"
								href="${employeeCategoriesList}">Employee Category</a></li>
							<li><a id="menuCompetencyList" href="${competencylist}">Competency</a></li>
							<li><a id="menuSkillsList" href="${skillsList}">Skill</a></li>
							<li><a id="menuVisaList" href="${visaList}">Visa</a></li>
						</div>


						<h3>Misc. Attribute</h3>
						<div>
							<li><a id="menuOrganizationList" href="${orghierarchysList}">BG/BU</a></li>
							<li><a id="menuTeamList" href="${teamList}">Parent
									Project / Team</a></li>
							<li><a id="menuEventList" href="${eventList}">Event</a></li>
							<li><a id="menuPdlList" href="${pdlList}">PDL</a></li>

						</div>
					</div>
				</ul>
			</li>
		</sec:authorize>
		<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
			<li class="">Upload
				<ul class="subMenu">
					<li><a id="menuUploadResourceList"
						href="${uploadResourcesList}"> Upload Resources</a></li>
				</ul>
			</li>
		</sec:authorize>

		<%--  <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')"> 
	<li>Reports
		<ul class="subMenu">
		<spring:url value="https://rmsreports.yash.com" var="report"  htmlEscape="true"/>
		<spring:url value="reports/list" var="report"  htmlEscape="true"/>
          <li><a id="reports" href="${report}" target="_blank"> Reports</a></li>
        </ul>
	</li>
	
	
	 </sec:authorize> --%>

		<%--  <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_SEPG_USER')">
	 <li>Reports
		<ul class="subMenu">
		<spring:url value="/reports" var="reportList"  htmlEscape="true"/>
          <li><a id="reports" href="${reportList}"> Reports</a></li>
        </ul>
	</li>
	</sec:authorize> --%>

		<!-- Changes done to show reports to SEPG Behalf manager -->
		<c:choose>

			<c:when test="<%=UserUtil.isCurrentUserinSEPGUserRole()%>">
				<c:choose>
					<c:when
						test="<%=UserUtil.getCurrentResource()
										.isBehalfManager()%>">
						<li>Reports
							<ul class="subMenu">
								<spring:url value="/reports" var="reportList" htmlEscape="true" />
								<li><a id="reports" href="${reportList}"> Reports</a></li>
							</ul>
						</li>
					</c:when>
					<c:otherwise>
						<sec:authorize
							access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_SEPG_USER')">
							<li>Reports
								<ul class="subMenu">
									<spring:url value="/reports" var="reportList" htmlEscape="true" />
									<li><a id="reports" href="${reportList}"> Reports</a></li>
								</ul>
								<ul class="treeview-menu">
									<spring:url value="/reports" var="reportList" htmlEscape="true" />
									<li id="menuReportListId"><a id="reports"
										href="${reportList}"><i class="fa fa-th-large"></i>
											Reports</a></li>
								</ul>
							</li>
						</sec:authorize>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<sec:authorize
					access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_SEPG_USER')">
					<li>Reports
						<ul class="subMenu">
							<spring:url value="/reports" var="reportList" htmlEscape="true" />
							<li><a id="reports" href="${reportList}"> Reports</a></li>
						</ul>
					</li>
				</sec:authorize>
			</c:otherwise>
		</c:choose>
		<sec:authorize
			access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
			<li class="">Mail Configuration
				<ul class="subMenu">
					<spring:url value="/mailConfiguration" var="mailConfiguration"
						htmlEscape="true" />
					<li><a id="menuMailConfiguration" href="${mailConfiguration}">
							Configure Mail Settings </a></li>
				</ul>
			</li>
		</sec:authorize>

		<!-- Not require for 2.9 release because need some more updates.. -->
		 <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')"> 
	<li class="">Resource Requests
		<ul class="subMenu">
			<spring:url value="/requests" var="requestDetail"  htmlEscape="true"/>
			<spring:url value="/requestsReports" var="requestReports"  htmlEscape="true"/>
			<li><a id="requestDetail" href="${requestDetail}">Resource Requisition</a></li>
			<li><a id="requestReports" href="${requestReports}">Resource Reports</a></li>
        </ul>
	</li>
</sec:authorize>  

		<sec:authorize access="hasAnyRole('ROLE_SEPG_USER','ROLE_ADMIN')">
			<li>SEPG
				<ul class="subMenu">
					<spring:url value="/activitys/sepgActivity" var="sepgActivity"
						htmlEscape="true" />
					<spring:url value="/sepgPhases" var="sepgPhasesList"
						htmlEscape="true" />
					<li><a id="menuSepgPhasesList" href="${sepgPhasesList}">SEPG
							Phases</a></li>
					<li><a id="sepgActivity" href="${sepgActivity}">SEPG
							Activity </a></li>
				</ul>
			</li>
		</sec:authorize>
		<%-- 
    	<c:if test="<%=UserUtil.getCurrentResource().isSEPGUser()%>">
   					<c:if test="<%=UserUtil.getCurrentResource().isBehalfManager()%>">
   					
   					
   		    
   				<li>SEPG
         			<ul class="subMenu">
             	<spring:url value="/activitys/sepgActivity" var="sepgActivity"  htmlEscape="true"/>
             	<spring:url value="/sepgPhases" var="sepgPhasesList"  htmlEscape="true"/>
           	<li><a id="menuSepgPhasesList" href="${sepgPhasesList}">SEPG Phases</a></li>
         	 <li><a id="sepgActivity" href="${sepgActivity}">SEPG Activity </a></li>
         </ul>
       </li> 
    
   					</c:if>
   					</c:if>
   					 --%>

		<sec:authorize
			access="hasAnyRole('BG4-BU1','BG4-BU2','BG4-BU3','BG4-BU4','BG4-BU5','BG4-BU6','BG4-BUA', 'ROLE_ADMIN','BG1-BU15')">
			<li class="last">My Workspace
				<ul class="subMenu">
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
					<li><a id="myDashboardId" href="${dashboard }">My
							Dashboard</a></li>
					<li><a id="myQueueId" href="${viewTicket }">My Ticket
							Queue</a></li>
					<li><a id="myQueue" href="${viewReviewTicket}">For My
							Review</a></li>
					<%-- <sec:authorize
					access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
					<li><a id="IRMQueueId" href="${queue }">IRM Queue</a></li>
					<li><a id="IRMDashboardId" href="${dashboard }">IRM
							Dashboard</a></li>
				</sec:authorize> --%>
					<%-- <li><a id="craeteTicketId" href="${createTicketHome }">Create
						Ticket</a></li>
				<li><a id="viewTicketId" href="${viewTicket }">View Ticket</a></li> --%>
					<sec:authorize
						access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
						<li><a id="uploadId" href="${uploadExcel }">Upload </a></li>
						<li><a id="discrepencyId" href="${discrepency}">Discrepency
								Records</a> </l>
					</sec:authorize>
				</ul>
			</li>
		</sec:authorize>
		<div class="clear"></div>
	</ul>
	<div class="fr menuDropDwm">
		<sec:authorize
			access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">

			<form id="evalForm" method="post">
				Resource Name <select name="j_username" id="j_username"
					class="required comboselect brdrDd">
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
				</select> <input id="j_password" type="hidden" name='j_password' /> <input
					type="button" class="mrgnLeft" value="Simulate User"
					title="simulate user after entering window user name"
					onclick="evaluateUser();">
			</form>
		</sec:authorize>

	</div>
	<div class="clear"></div>
	<script>
		$(function() {
			$("#accordion-2").accordion({
				header : "h3",
				collapsible : true,
				active : false
			});

		});

		$(document)
				.ready(
						function() {
							//alert($("select.brdrDd").parent().parent());
							$("select.brdrDd").parent().parent(".menuDropDwm")
									.find("span.ui-combobox input").css(
											"border", "1px solid #D5D5D5");
							$('a[id^="menu"]').removeClass('active');
							var href = window.location.pathname;
							if (href.toLowerCase().indexOf("customers") >= 0) {
								$('#menuCustomerList').addClass('active');
							} else if (href.toLowerCase().indexOf("projects") >= 0) {
								$('#menuProjectList').addClass('active');
							} else if (href.toLowerCase().indexOf("resources") >= 0) {
								if (href.toLowerCase()
										.indexOf("uploadresource") >= 0) {
									$('#menuUploadResourceList').addClass(
											'active');
								} else {
									$('#menuResourceList').addClass('active');
								}

							} else if (href.toLowerCase().indexOf(
									"resourceallocations") >= 0) {
								$('#menuResourceAllocation').addClass('active');
							} else if (href.toLowerCase().indexOf(
									"projectallocations") >= 0) {
								$('#menuProjectAllocation').addClass('active');
							} else if (href.toLowerCase().indexOf("timehrses") >= 0) {
								$('#menuTimeHoursEntry').addClass('active');

							} else if (href.toLowerCase().indexOf(
									"useractivitys") >= 0) {
								$('#menuUserActivityList').addClass('active');
							} else if (href.toLowerCase().indexOf("activitys") >= 0) {
								$('#menuActivityList').addClass('active');
							} else if (href.toLowerCase().indexOf(
									"allocationtypes") >= 0) {
								$('#menuAllocationtypesList')
										.addClass('active');
							} else if (href.toLowerCase().indexOf("bus") >= 0) {
								$('#menuBusList').addClass('active');
							} else if (href.toLowerCase().indexOf("currencys") >= 0) {
								$('#menuCurrencysList').addClass('active');
							} else if (href.toLowerCase().indexOf(
									"designationses") >= 0) {
								$('#menuDesignationsList').addClass('active');
							} else if (href.toLowerCase().indexOf("invoicebys") >= 0) {
								$('#menuInvoicebysList').addClass('active');
							} else if (href.toLowerCase().indexOf("modules") >= 0) {
								$('#menuModulesList').addClass('active');
							} else if (href.toLowerCase().indexOf("ownerships") >= 0) {
								$('#menuOwnershipsList').addClass('active');
							} else if (href.toLowerCase().indexOf(
									"employeeCategories") >= 0) {
								$('#menuEmployeeCategoriesList').addClass(
										'active');
							} else if (href.toLowerCase().indexOf(
									"competencies") >= 0) {
								$('#menuCompetencyList').addClass('active');
							} else if (href.toLowerCase().indexOf(
									"projectcategorys") >= 0) {
								$('#menuProjectcategorysList').addClass(
										'active');
							} else if (href.toLowerCase().indexOf("locations") >= 0) {
								$('#menuLocationList').addClass('active');
							} else if (href.toLowerCase().indexOf(
									"engagementmodels") >= 0) {
								$('#menuEngagementModelList')
										.addClass('active');
							} else if (href.toLowerCase().indexOf("grades") >= 0) {
								$('#menuGradeList').addClass('active');
							} else if (href.toLowerCase().indexOf("visas") >= 0) {
								$('#menuVisaList').addClass('active');
								/* }else if (href.toLowerCase().indexOf("rateids") >= 0){
									$('#menuRateidList').addClass('active'); */
							} else if (href.toLowerCase().indexOf(
									"projectmethodologys") >= 0) {
								$('#menuProjectMethodologyList').addClass(
										'active');
							} else if (href.toLowerCase().indexOf("sepgPhases") >= 0) {
								$('#menuSepgPhasesList').addClass('active');
							} else if (href.toLowerCase().indexOf("skillses") >= 0) {
								$('#menuSkillsList').addClass('active');

							} else if (href.toLowerCase().indexOf("jobtypes") >= 0) {
								$('#menuJobTypeList').addClass('active');
							} else if (href.toLowerCase().indexOf(
									"orghierarchys") >= 0) {
								$('#menuOrganizationList').addClass('active');
							} else if (href.toLowerCase().indexOf("events") >= 0) {
								$('#menuEventList').addClass('active');

							}
							 else if (href.toLowerCase().indexOf("pdls") >= 0) {
									$('#menuPdlList').addClass('active');
								}

							else if (href.toLowerCase().indexOf(
									"projectactivitys") >= 0) {
								$('#menuProjectActivity').addClass('active');

							} else if (href.toLowerCase().indexOf("dashboard") >= 0) {
								$('#menuUserDashBoard').addClass('active');
							}

							/* else if (href.toLowerCase().indexOf("projectactivity") >= 0){
								$('#menuProjectactivityList').addClass('active');
							} */

							var mMonthStartDate;
							var mMonthEndDate;
							var date = new Date();
							var prevoius2Date = new Date();
							var mMonth = date.getMonth() + 1;
							var mYear = date.getFullYear();
							var pre2Month = new Date(prevoius2Date
									.setMonth(date.getMonth() + (-1)));
							var preMonth = pre2Month.getMonth() + 1;
							var preYear = pre2Month.getFullYear();
							if (mMonth == "12" || mMonth == "10"
									|| mMonth == "8" || mMonth == "7"
									|| mMonth == "8" || mMonth == "5"
									|| mMonth == "3" || mMonth == "1") {
								/* mMonthStartDate = preMonth + "/" + "1" + "/"
										+ preYear; */
								mMonthStartDate = mMonth + "/" + "1" + "/"
										+ mYear;
								mMonthEndDate = mMonth + "/" + "31" + "/"
										+ mYear;
							} else {
								if (mMonth == "2") {
									if (mYear % 4 == 0) {
										/* mMonthStartDate = preMonth + "/" + "1"
												+ "/" + preYear; */
										mMonthStartDate = mMonth + "/" + "1"
												+ "/" + mYear;
										mMonthEndDate = mMonth + "/" + "29"
												+ "/" + mYear;
									} else {
										/* mMonthStartDate = preMonth + "/" + "1"
												+ "/" + preYear; */
										mMonthStartDate = mMonth + "/" + "1"
												+ "/" + mYear;
										mMonthEndDate = mMonth + "/" + "28"
												+ "/" + mYear;
									}
								} else {
									/* mMonthStartDate = preMonth + "/" + "1"
											+ "/" + preYear; */
									mMonthStartDate = mMonth + "/" + "1" + "/"
											+ mYear;
									mMonthEndDate = mMonth + "/" + "30" + "/"
											+ mYear;
								}
							}
							document.getElementById('menuUserActivityList').href = "/rms/useractivitys?minWeekStartDate="
									+ mMonthStartDate
									+ "&maxWeekStartDate="
									+ mMonthEndDate
									+ "&find=ByWeekStartDateBetweenAndEmployeeIdView";
						});
	</script>
</sec:authorize>
