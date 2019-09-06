<%@ page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<spring:message code="application_js_version" var="app_js_ver" htmlEscape="false" />
	
 <!-- get Employee id when redirected from creat.jsp page of loanandtransfer 
      while updating the particular resource's loan/transfer -->
<%
String employee_id="";
try{
    employee_id=request.getParameter("employeeId");
   }
catch(Exception ex) {
	employee_id="";
	ex.printStackTrace();
 }
%> 

 <script>
  var employee_id=<%=employee_id%>;
  var contextPath="<%=request.getContextPath()%>"+"/resources/";
</script>
 
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<%-- <script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
<script src="${moment_js}" type="text/javascript"></script>	 --%>
<spring:url
	value="resources/js/moment.min.js?ver=${app_js_ver}"
	var="moment_js"/>
<script src="${moment_js}" type="text/javascript"></script>	

<spring:url
	value="/resources/js-framework/validate/jquery.validate.min.js?ver=${app_js_ver}"
	var="jquery_validate_min_js" />
<spring:url
	value="/resources/js-framework/validate/jquery.validVal-4.0.3-packed.js?ver=${app_js_ver}"
	var="jquery_validVal" />
<spring:url
	value="/resources/js-framework/validate/additional-methods.min.js?ver=${app_js_ver}"
	var="additional_methods_min_js" />
<script src="${jquery_validate_min_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${additional_methods_min_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
<script src="${jquery_validVal}" type="text/javascript">  </script>

<spring:url value="/resources/js-framework/date.js?ver=${app_js_ver}"
	var="jquery_date_js" />
 
<spring:url value="/resources/styles/skin/ui.dynatree.css?ver=${app_js_ver}"
	var="ui_dynatree_css" />
<link href="${ui_dynatree_css}" rel="stylesheet" type="text/css"></link>
<!-- <link href="resources/styles/skin/ui.dynatree.css" rel="stylesheet" type="text/css" id="skinSheet"> -->
<spring:url value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />
<spring:url value="resources/js-framework/jquery.cookie.js?ver=${app_js_ver}"
	var="js_cookie" />
<spring:url value="/resources/js-framework/jquery.dynatree.js?ver=${app_js_ver}"
	var="js_dynatree" />
<spring:url value="/resources/js-framework/blockUI/jquery.blockUI.js?ver=${app_js_ver}"
	var="blockUI" />
<script src="${js_cookie}" type="text/javascript"></script>
<script src="${js_dynatree}" type="text/javascript"></script>

<spring:url value="/resources/css/select2.min.css?ver=${app_js_ver}"
	var="select2_css" />
<link href="${select2_css}" rel="stylesheet" type="text/css"></link>

<spring:url value="/resources/js/select2.min.js?ver=${app_js_ver}"
	var="select2_js" />
<script src="${select2_js}" type="text/javascript"></script>
 
<script src="${blockUI}" type="text/javascript"></script>

<spring:url
	value="/resources/js/resource_list.js?ver=${app_js_ver}"
	var="resource_list_js"/>
<script src="${resource_list_js}" type="text/javascript"></script>	

<!-- End custom loader -->
<style type="text/css" title="currentStyle">
/*Start custom loader*/
#resourceTableId_processing {
	display: none !important;
}
/*End custom loader*/
thead input {
	width: 100%
}

input.search_init {
	color: #999
}

#resourceTableId {
	table-layout: fixed;
}

#resourceTableId thead th {
	width: 120px;
}

#resourceTableId td {
	word-wrap: break-word;
}
#resourceTableId_wrapper table.dataTable {
	margin: 0 auto;
	clear: both;
	width: 100%;
	border: 0px solid #9b9b9b;
	border-collapse: separate;
	border-spacing: 1px;
}
/*updated css added by kratika-03-5-19*/
table.dataTbl.display.tablesorter.addNewRow.alignCenter.my_table.tablefixed.dataTable
	{
	table-layout: auto;
}

table.dataTbl.display.tablesorter.addNewRow.alignCenter.my_table.dataTable
	{
	max-height: 751px;
	display: table !important;
}

table.dataTbl.display.tablesorter.addNewRow.alignCenter.my_table.dataTable
	{
	table-layout: fixed;
}

.my_table {
	border-collapse: collapse !important;
}

.my_table tbody tr.content:hover td {
	background: #c99e4d !important;
	cursor: pointer;
	transition: 0.5s;
}

.my_table tbody tr td.active {
	background: #dddd !important;
	color: #5189c2 !important;
}

.my_table thead th {
	background: #fff !important;
	color: #333 !important;
}

.positiondashboard-table .paging_full_numbers a.paginate_button,
	.paging_full_numbers a.paginate_active {
	/* border: 1px solid #aaa; */
	padding: 2px 5px;
	margin: 0 3px;
	cursor: pointer;
	*cursor: hand;
	color: #333 !important;
}

.positiondashboard-table .paging_full_numbers a.paginate_button,
	.paging_full_numbers a.paginate_active {
	/* border: 1px solid #aaa; */
	padding: 2px 5px;
	margin: 0 3px;
	cursor: pointer;
	*cursor: hand;
	color: #333 !important;
}

.positiondashboard-table {
	position: relative;
	border: 1px solid rgba(0, 0, 0, .12);
	border-collapse: collapse;
	font-size: 13px;
	background-color: #fff;
}

.positiondashboard-table thead {
	padding-bottom: 3px;
}

.positiondashboard-table th {
	vertical-align: middle;
	text-overflow: ellipsis;
	text-align: left !important;
	font-weight: 700;
	line-height: 24px;
	border-right: 0px solid #FFFFFF !important;
	letter-spacing: 0;
	border-radius: 0 !important;
	font-size: 12px;
	color: rgba(0, 0, 0, .54);
	padding-bottom: 8px;
	background: #fff !important;
	color: #333 !important;
	border: none;
	padding-left: 24px !important;
}

.positiondashboard-table td, .positiondashboard-table th {
	position: relative;
	height: 48px;
	box-sizing: border-box;
}

.positiondashboard-table td {
	border-top: 1px solid rgba(0, 0, 0, .12);
	border-bottom: 1px solid rgba(0, 0, 0, .12);
	padding-top: 12px;
	vertical-align: middle;
	text-align: left !important;
	background: #fff !important;
	border: 0px solid #FFFFFF !important;
	border-top: 1px solid rgba(0, 0, 0, .12) !important;
	border-bottom: 1px solid rgba(0, 0, 0, .12) !important;
	border-radius: 0 !important;
	padding-left: 24px !important;
	padding-right: 18px !important;
}

.positiondashboard-table tbody tr:hover {
	background-color: #ddd;
}

#adminResourceTbl .dataTables_scroll {
	overflow-x: hidden !important;
}

#adminResourceTbl .dataTables_scrollHead {
	width: 100% !important;
}

#adminResourceTbl .dataTables_wrapper .dataTables_info {
	width: 20%;
	margin-top: 9px;
}

.paging_full_numbers a.paginate_active {
    color: #fff !important;
    background-color: var(--nav-active) !important;
    font-weight: bold;
    border-radius: 50%;
    border: 1px solid var(--nav-active);
}

.paging_full_numbers a {
    margin: 0 !important;
    padding: 10px 14px !important;
    background: #fff !important;
    color: #999 !important;
}
.bottom {
    padding: 15px;
    background: #fff;
    position: relative;
}

 
div.dataTables_wrapper {
    background: #fff;
}
#resourceTableId_length {
    padding-right: 10px;
    padding-top: 8px;
}
#adminResourceTbl .resourceBtnIcon {
    
    position: static;
    float:right;
     
}
.dataTables_paginate.paging_full_numbers {
    margin-bottom: 8px;
    float: left !important;
    margin-left: 165px !important;
}
#adminResourceTbl #resourceTableId_length.dataTables_length label select {
    padding: 5px;
    width: 60px;
    border-radius: 50px;
}
.ProjectStatusdrdwn select, .ResourceStatusdrdwn select {
   padding: 5px;
    border-radius: 50px;
}
#tab1 a.blue_link{
    background: var(--nav-active);
    border: 1px solid var(--nav-active);
    border-radius: 50px;
    padding: 5px 26px;
    margin-right: 10px;
    color: #fff !important;
    text-decoration: none;
    margin-top: 5px;
    font-size: 13px;
    font-weight: 500;
}
#adminResourceTbl .projecttoolbar{
    float: right;
    width: calc(100% - 145px);
    position: relative;
    top: -21px;
}
.resource-status{
    float: left;
}
#resourceTableId_length {
     border-right:none !important;
}
 
#resourceTableId_wrapper .dataTables_scrollBody {
    max-height: 645px;
}

.select2-container--default .select2-selection--multiple .select2-selection__choice {
	color: #000;
	background-color: #ddd;
 }
 .select2-container {
	width: 145px !important;
 }
</style>
<script>
$(document).ready(function(){
	<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')"> 
		$("div.projecttoolbar").html(
			'<div class="resource-status"><span class="ProjectStatusdrdwn"><label class="blue_link" title="Status"> Status</label> <select id="Activerecord"  onChange="OnActiveRecord(this.value)"><option selected="selected">Active</option><option>All</option></select>'+
			'</span></div> ' + '<div class="btnIcon resourceBtnIcon">'+ '<a href="javascript:void(0)" class="blue_link resource-tbl" id="addNew" onclick="addNew(event)" >' +	'+ Add New </a></div>'	
			);	 
	</sec:authorize>
	<sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER')">
		$("div.projecttoolbar").html(
			'<div class="btnIconResource">'+
			'Status: <select id="Activerecord"  onChange="OnActiveRecord(this.value)"><option selected="selected">Active</option><option>All</option></select>'+
			'</div>  ');	
	</sec:authorize>
});//search
		//Start - openMaintainance
		
		

		function displayMaintainance(){
			$(".resoucestab_div").show();
			$(".resourceDialog").show();
			$(".resoucePrimarySkillDialog").show();
			$(".resouceSecondarySkillDialog").show();
			$("#tableResourceData").show();
			$("#dialogText").show();
			$("ul.tabs a").removeClass('active');
			$(".tab_div").hide().next("#tab2".hash).show();
			$('a[href$="tab2"]').removeClass('MaintenanceTab');
			$('a[href$="tab2"]').addClass('active');
			
		 
			
			// Convert All dropdown on Resource Edit/New form to disabled , inputs to readonly and date fields to readonly for DEL_MANAGER
			
			 <sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER')"> // Readonly access to DEL_MANAGER
				try {
				    $('form#newEmployee input#fileUpload').attr("disabled","disabled");
				    
					$.each ($('form#newEmployee select.check') ,function() {
						$(this).show();
						$(this).next().remove();
						$(this).attr("disabled","disabled");
					});
					$('form#newEmployee input').attr("readonly","readonly");
					$('form#newEmployee input[type=checkbox]').attr("disabled","disabled");
					var dateFieldIds = ["#dateOfJoining" ,"#penultimateAppraisalId" , "#lastAppraisalId", "#confirmationDate","#releaseDate", "#visavalidDate","#transferDate" ];
					$.each(dateFieldIds,function(index,fieldId) {
						$(fieldId).datepicker("destroy");
					});
				} catch(err) {
				}
			</sec:authorize>
			
			
			containerWidth();
// 			$('#newEmployee').changeFormMethod('PUT');
			if($(eventTarget).attr("id")=="addNew")
				{
					$("#rejoiningFlagTr").show();
					$('#formTable .trUsername').hide();
					$('#formTable .tdYashExp').hide();
					$('#formTable .preferredLocation').hide();
					eventTarget =null;
				}else{
					$("#rejoiningFlagTr").hide();
					$('#formTable .trUsername').show();
					$('#formTable .tdYashExp').show();
				}
			
		}

		/* condition to check whether redirected from loan and transfer page if redirected
		  then open resource form whom updated for loan and transfer */
		
		 if(employee_id != null && employee_id != "") {
		   openMaintainance(employee_id);
		 } 
		
		stopProgress();
		$('[data-toggle="tooltip"]').tooltip();
		
		setInterval(function(){ $('[data-toggle="tooltip"]').fadeToggle(500); }, 1000);
		
		<%
			Boolean flag=false;
			flag=(Boolean)session.getAttribute("notificationbarflag");
		%>
	
		function showResourceDetail() {
		    var EmpFName = $('#firstName').val() + ' ' + $('#middleName').val() + ' ' + $('#lastName').val() + '[' + $('#yashEmpId').val() + ']'
		    localStorage.setItem('id', $("#employeeIdTxt").val());
		    localStorage.setItem('key', 1);
		    localStorage.setItem('empfname', EmpFName);
		    window.location.href = "${pageContext.request.contextPath}/loanAndTransfer?isRedirectedFromResource=" + true;
		}
		
</script>

<div class="alertWrapper success" style="display: none;">
	<div class="alert alert-success" role="alert"></div>
</div>
<div class="content-wrapper">
	<div class="botMargin">
		<h1>Resources</h1>
	</div>

	<div class="tab_seaction">
		<ul class='tabs'>
			<li><a href='#tab1'>List</a></li>
			<li><a class="MaintenanceTab" id="MaintenanceTabInactive" href='#tab2'>Maintenance</a></li>
		</ul>
		<div id='tab1' class="tab_div">
			<div class="tbl"></div>
				<div class="clear"></div>
				<div id="adminResourceTbl">
				<table class="dataTbl display tablesorter dataTable addNewRow alignCenter white-sort-icons my_table positiondashboard-table" 
				id="resourceTableId" style="overflow: auto; display: inline-block;">
					<thead>
						<tr>
							<th align="center" valign="middle" hidden="true">Emp ID</th>
							<th align="center" valign="middle">Yash Emp ID</th>
							<th align="center" valign="middle">Emp Name</th>
							<th align="center" valign="middle">Designation</th>
							<th align="center" valign="middle">Grade</th>
							<th align="center" valign="middle">Resume</th>
							<th align="center" valign="middle">TEF</th>
							<th align="center" valign="middle">DOJ</th>
							<th align="center" valign="middle">Release Date</th>
							<th align="center" valign="middle">Ownership</th>
							<th align="center" valign="middle" class="nosort">Exp In Yash (YY.MM)</th>
						   	<th align="center" valign="middle" class="nosort">Total Exp (YY.MM)</th>
						        <th align="center" valign="middle">Relevant IT Exp (YY.MM)</th>
							
							<th align="center" valign="middle">Current BG-BU</th>
							<th align="center" valign="middle" class="nosort">Parent BG-BU</th>
							
							<th align="center" valign="middle" class="resID_email_id">E-Mail</th>
							<th align="center" valign="middle">Base Location</th>
							<th align="center" valign="middle">IRM</th>
							<th align="center" valign="middle">SRM</th>
						    <th align="center" valign="middle" class="resID_user_role">User Role</th> 
							
						</tr>
						<tr class="">
					  		<td><input type="text" name="search_yashEmpID" 
								value="Yash Employee ID" class="search_init" hidden="true"/></td>
							<td><input type="text" name="search_empID"
								value="Employee ID" class="search_init" /></td>
							<td><input type="text" name="search_empName"
								value="Employee Name" class="search_init" /></td>
							<td><input type="text" name="search_designation"
								value="Designation" class="search_init" /></td>
							<td><input type="text" name="search_grade"
								value="Grade" class="search_init" /></td>
							<td> <input type="text" name="search_uploadResume" value="Resume"
								class="search_init" disabled="disabled"/> </td> 
							<td> <input type="text" name="search_uploadTEF" value="TEF"
								class="search_init" disabled="disabled"/></td> 
							 <td><input type="text" name="search_DOJ" value="DOJ"
								class="search_init" disabled="disabled"/></td> 
							<td><input type="text" name="search_releaseDt" value="Release Date"
								class="search_init" disabled="disabled"/></td>
							<td><input type="text" name="search_ownership"
								value="Ownership" class="search_init" /></td>
							<td><input type="text" name="search_Exp" value="Exp"
								class="search_init" disabled="disabled"/></td> 
							<td><input type="text" name="search_tot_Exp" value="TotExp"
								class="search_init" disabled="disabled"/></td>
							<td><input type="text" name="search_Rel" value="Relevant exp"
								class="search_init" disabled="disabled"/></td>
								<td><input type="text" name="search_currentBU"
								value="Current BG-BU" class="search_init" /></td>
								<td><input type="text" name="search_parentBU"
								value="Parent BG-BU" class="search_init" /></td>



							<td><input type="text" name="search_email" value="E-mail"
								class="search_init" /></td>

							<td><input type="text" name="search_location"
								value="Location" class="search_init" /></td>
							<td><input type="text" name="search_cRM" value="IRM"
								class="search_init" /></td> 
							<td><input type="text" name="search_cRM2" value="SRM"
								class="search_init" /></td> 
						
							<td><input type="text" name="search_userRole" value="User Role"
								class="search_init" /></td> 
							

						</tr> 
					</thead>
					<tbody >
					</tbody>
				</table>
				</div>
			
			<div class="clear"></div>
		</div>
		<div id='tab2' class="tab_div resoucestab_div">
			<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_HR')">
				<div class="search_filter">
					<div align="right">
						<a href="#" class="blue_link" id="save"> <img
							src="resources/images/save.png" name="save" width="16"
							height="22" /> Save
						</a>
					</div>
				</div>
			</sec:authorize>
			<div class="form">
				<form method="post" id="newEmployee" name="newEmployee"	enctype="multipart/form-data">
					<input type="hidden" name="userAction" id="userAction" /> 
					<input	type="hidden" name="bgAdminListOfBu" id="bgAdmin" />
					<input	type="hidden" name="resourcePrimaryskillsList" id="resourcePrimaryskills" />
					<input type="hidden" name="resourceSecondaryskillsList" id="resourceSecondaryskills" />
					<c:if test="${not empty bgAdminAccessRightlist}">
						<c:forEach var="orgId" items="${data.bgAdminAccessRightlist}">
							<%-- alert("${orgId.ordId}"); --%>
					 </c:forEach>
					</c:if>
					
					<table id="formTable">
					
					
				    <tr id="rejoiningFlagTr" style="display: none;">
				    <td width="11%" align="right" style="color: #0174A3;text-decoration:none;">Rejoining :</td>
				    <td align="left"><input type="checkbox" class="noWidth" value="Y" id="rejoiningFlag" name="rejoiningFlag" /></td>
				    </tr>
					
                    
						<tr>
							<td width="11%" align="left">Employee ID :<span
								class="astric">*</span>
							</td>
							<td width="18%" align="left"><input type="hidden"
								value="" name="employeeId" id="employeeIdTxt" /> <%-- <input
								type="hidden" value="${userName}" name="userName" /> --%>
								<div class="positionRel">
									<input type="text" value="" name="yashEmpId"
										id="yashEmpId" maxlength="10"
										class="required string server-validation" />
									<div class="errorNumericLast errorNumeric"
										style="display: none">
										<img src="resources/images/errorAerrowUp.png"><span>This
											Employee ID is already registered with another resource</span>
									</div>
								</div></td>
							<td width="11%" align="left">First Name :<span
								class="astric">*</span></td>
							<td width="18%" align="left"><input type="text"
								name="firstName" class="required string" id="firstName"></td>

							<td width="13%" align="left">Middle Name :</td>
							<td width="18%" align="left"><input type="text"
								name="middleName" id="middleName"></td>

							<td width="13%" align="left">Last Name :<span class="astric">*</span></td>
							<td width="18%" align="left"><input type="text"
								name="lastName" class="required string" id="lastName"></td>
						</tr>
						<tr>
							<td align="left">Designation :<span class="astric">*</span></td>
							<td align="left"><select name="designationId.id" 
								class="required comboselect check" id="designationId">
									<option value="">Select One</option>
									<c:forEach var="designation" items="${designation}">
										<option value="${designation.id}">${designation.designationName}</option>
									</c:forEach>
							</select></td>
							<td align="left">Grade :<span class="astric">*</span>
							</td>
							<td align="left"><select name="gradeId.id" id="selGrade"
								class="required comboselect check">
									<option value="">Select One</option>
									<c:forEach var="grade" items="${grade}">
										<option value="${grade.id}">${grade.grade}</option>
									</c:forEach> 
							</select></td>
							<td align="left">Parent BG-BU :<span class="astric">*</span></td>
							<td align="left"><select name="buId.id"
								class="required comboselect check" id="buId">
									<option value="-1">Select One</option>
									<c:forEach var="bus" items="${bus}">
										<option value="${bus.id}">${bus.parentId.name}-${bus.name}</option>
									</c:forEach>
							</select></td>
							<td align="left">Current BG-BU :<span class="astric">*</span></td>
							<td align="left"><select name="currentBuId.id"
								class="required comboselect check" id="currentBuId">
									<option value="-1">Select One</option>
									<c:forEach var="bus" items="${bus}">
										<option value="${bus.id}">${bus.parentId.name}-${bus.name}</option>
									</c:forEach>
							</select></td>
							<!-- 
							<td colspan="2" align="center" rowspan="2"><a
								href="javascript:void(0);" onclick="loadResourceLoanTrasfer();"><font
									size="2">Loan/Transfer Resource</font></a></td> -->
						</tr>
						<tr>							
							<td align="left">Base Location :<span class="astric">*</span></td>
							<td align="left"><select name="locationId.id" id = "locationId"
								class="required comboselect check">
									<option value="">Select One</option>
									<c:forEach var="location" items="${location}">
										<option value="${location.id}">${location.location}</option>
									</c:forEach>
							</select></td>						
							<td align="left">Current location : <span class="astric">*</span></td>
							<td align="left"><select name="deploymentLocation.id" id = "deploymentLocation"
								class="required comboselect check">
									<option value="-1">Select Current Location</option>
									<c:forEach var="location" items="${location}">
										<option value="${location.id}">${location.location}</option>
									</c:forEach>
							</select></td>
                 <td align="left">Employee Category :<span class="astric">*</span></td>
							<td align="left"><select name="employeeCategory.id"
								id="employeeCategory" class="required comboselect check">
									<option value="">Select One</option>
									<c:forEach var="employeecategory" items="${employeecategory}">									
											<option value="${employeecategory.id}">${employeecategory.employeecategoryName}</option>										
									</c:forEach>
							</select></td>

							<td align="left">Ownership :<span class="astric">*</span></td>
							<td align="left"><select name="ownership.id"
								id="ownership" class="required comboselect check">
									<option value="">Select One</option>
									<c:forEach var="ownershipId" items="${ownership}">									
											<option value="${ownershipId.id}">${ownershipId.ownershipName}</option>										
									</c:forEach>
							</select></td>
												
							<!-- <td colspan="2" align="center" rowspan="2"><a
								href="javascript:void(0);" onclick="loadHistory();"><font
									size="2">Loan/Transfer History</font></a></td> -->
						</tr>
						<tr>
						<td align="left">E-Mail ID :<span class="astric">*</span></td>
							<td align="left">
								<div class="positionRel">
									<input type="text" value="" name="emailId" id="emailId"
										class="required email string server-validation" />
									<div id="emailErrorDiv"  class="errorNumericLast errorNumeric"
										style="display: none">
										<img src="resources/images/errorAerrowUp.png"><span>This
											E-Mail ID is already registered with another resource.</span>
									</div>
								</div>
							</td>		
							<td align="left">Contact Number 1 :</td>
							<td align="left">
								<div class="positionRel">
									<input type="text" value="" name="contactNumber"
										id="contactNumber1" class="number" maxlength="16" />

								</div>
							</td>
							<td align="left">Contact Number 2 :</td>
							<td align="left">
								<div class="positionRel">
									<input type="text" value="" name="contactNumberTwo" id="contactNumber2"
										class="number" maxlength="16" />
								</div>
							</td>	
							<td align="left">VISA Status :<span class="astric">*</span></td>
							<td align="left"><select name="visaId.id" id="visaId"
								class="required comboselect">
									<option value="">Select One</option>
									<c:forEach var="visa" items="${visa}">
										<option value="${visa.id}">${visa.visa}</option>
									</c:forEach>
							</select></td>

						</tr>
						<tr>
							<td align="left">Visa valid until Date :</td>
							<td align="left"><input type="text" value=""
								readonly="readonly" name="visaValid" id="visavalidDate" /></td>

							
							<td align="left">IRM : <span class="astric">*</span></td>
							<td align="left">
							
							<select id="currentReportingManagerOne" name="currentReportingManager.employeeId" class="required">
							</select>
							
							</td>


							<td align="left">SRM : <span class="astric">*</span></td>
							<td align="left">
							
							<select id="currentReportingManagerTwo" name="currentReportingManagerTwo.employeeId"class="required">
							</select>
							
							</td>
							
							<td align="left">Role :</td>
							<td align="left"><select name="userRole" id="userRole"
								class="comboselect">
									<option value="ROLE_USER">ROLE_USER</option>
									<option value="ROLE_MANAGER">ROLE_MANAGER</option>
									<option value="ROLE_DEL_MANAGER">ROLE_DEL_MANAGER</option>
									<option value="ROLE_BG_ADMIN">ROLE_BG_ADMIN</option>
									<option value="ROLE_HR">ROLE_HR</option>
									<option value="ROLE_SEPG_USER">ROLE_SEPG_USER</option>
									<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
										<option value="ROLE_ADMIN">ROLE_ADMIN</option>
									</sec:authorize>
							</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <spring:url
									value="/resources/showOrgStructure" var="showOrgStructure" />
								<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
									<a href="#" class="showOrgStructure" id="showOrgStructure"
										style="display: none; width: 400; height: 400; title: 'hello'">Edit</a>
								</sec:authorize></td>	
								
						</tr>
						<tr>
						<td align="left">Customer User<br>ID Detail :</td>
							<td align="left"><textarea name="customerIdDetail" cols=""
									rows="3" class="string"></textarea></td>
									
									<td align="left">Competency :<span class="astric">*</span></td>
							<td align="left"><select name="competency.id" id="competency"
								class="required comboselect">
									<option value="">Select One</option>
									<c:forEach var="competency" items="${competency}">
										<option value="${competency.id}">${competency.skill}</option>
									</c:forEach>
							</select></td>
									
						<td align="left">Profit Centre :</td>

							<td align="left"><input type="text" value=""
								name="profitCentre" id="profileCenter" /></td>

							<td colspan="2">
								<table width="100%">
									<tr>
										<td>Time Sheet Comments Optional :
											<div>
												Primary Skills &nbsp; <a href="#"
													class="showSkillResourcePrimaries"
													id="showSkillResourcePrimaries">View</a>
											</div>

											<div>
												Secondary Skills : <a href="#"
													class="showSkillResourceSecondaries"
													id="showSkillResourceSecondaries">View</a>
											</div>
										</td>
										<td align="left" valign="top"><input type="checkbox"
											class="noWidth" id="timesheetCommentOptional"
											name="timesheetCommentOptional" value="Y" /></td>
									</tr>
								</table>
							 </td>							
							
							
						</tr>	
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td colspan="2">
								<div class="uploadFileSize"></div>
							</td>
							<td></td>
						</tr>
						<tr>
						
						<td align="left">DOJ :<span class="astric">*</span></td>
							<td align="left"><input type="text" value=""
								id="dateOfJoining" name="dateOfJoining" class="required"
								readonly="readonly" /></td>
								
						<td class="tdYashExp" align="left">Experience In Yash :<span class="astric">*</span></td>
							<td class="tdYashExp" align="left"><input type="text" value=""
								id="yearDiff" name="yearDiff" class="required" readonly="readonly"  disabled="disabled"
								/></td>
						  <td align="left">Total Exp (YY.MM): (Including Yash exp)</td>
							<td align="left">
							<div>
								<input type="hidden" name="totalExper" id="totalExper" value="">
								<select onfocus='this.size=05;' onblur='this.size=1;' onchange='this.size=1; this.blur();' 
								name="totalExper"  id="totalExperYear" style="width:62px;float: left;">
								</select>
												&nbsp;
								<select onfocus='this.size=05;' onblur='this.size=1;' onchange='this.size=1; this.blur();' 
								name="totalExper"  id="totalExperMonth" style="width:68px;float: left;">
								</select>
							</div></td>
						<td width="13%" align="left">Relevant IT Exp (YY.MM):  (Including Yash exp)<span class="astric">*</span></td>
							<td width="18%" align="left">
							<!-- <input type="text" name="relevantExper" class="required" id="relevantExper"> -->
								
								<input type="hidden" value="" id="endTransferDate" name="endTransferDate" readonly="readonly" />
								<div>
									<input type="hidden" name="relevantExper" class="required" id="relevantExper" value="">
									<select onfocus='this.size=05;' onblur='this.size=1;' onchange='this.size=1; this.blur();' 
									name="relevantExper"  id="relevantExpYear" class="required number" style="width:62px;float: left;">
									</select>
														&nbsp;
									<select onfocus='this.size=05;' onblur='this.size=1;' onchange='this.size=1; this.blur();' 
									name="relevantExper"  id="relevantExpMonth" class="required number" style="width:68px;float: left;">
									</select>
								</div>
								<!-- End - dropdowns for relevant exp - Digdershikha-->
								
								
								<!-- <input type="hidden" value=""
								id="reportUserId" name="reportUserId" 
								readonly="readonly" /> -->
								</td> 
						       </tr> 
							<tr>	
							<td align="left">Confirmation Date :</td>
							<td align="left"><input type="text" value=""
								readonly="readonly" name="confirmationDate"
								id="confirmationDate" /></td>
								<td align="left">Resignation Date :</td>
							<td align="left"><input type="text" value=""
								id="resignationDate" name="resignationDate" 
								readonly="readonly" /></td>
								<td align="left">Release Date :</td>
							<td align="left"><input type="text" value="" 
								readonly="readonly" name="releaseDate" id="releaseDate"></td>	
								
								<td align="left">Transfer Date :</td>
								
								<td align="left">
<input id="transferDate"  type="text" name="transferDate"  value="">
	</tr>					
						<tr>	
 

<td align="left">Appraisal Date 1 :</td>
							<td align="left"><input type="text" value=""
								readonly="readonly" name="lastAppraisal" id="lastAppraisalId" /></td>
							<td align="left">Appraisal Date 2 :</td>
							<td align="left"><input type="text" value=""
								readonly="readonly" name="penultimateAppraisal"
								id="penultimateAppraisalId"></td>																			
						
							<!-- <td align="left"><input type="text" value="" 
								readonly="readonly" name="transferDate" id="transferDate"></td> -->
							<td align="left">Awards and recognitions :</td>
							
							<td align="left"><input type="text" value=""
								name="awardRecognition" id="awardRecognition" /></td>
								<td align="left" id="preferred-label">Preferred Location :</td>
								<td align="left"><input type="text" value=""
								name="preferredLocation.location" id="preferredLocation" readonly="readonly"/></td>
						</tr>
									
						<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
						<tr class="trUsername">									
							<td align="left">Username :</td>								
							<td align="left">
								<input id="usernameId" type="text" name="userName"  value="">
							</td>	
						</tr>
						</sec:authorize>

						<sec:authorize access="hasAnyRole('ROLE_ADMIN' ,'ROLE_BG_ADMIN')">
						<tr>
								<td align="left">Upload Resume:</td>                            			
								<td align="left">
									<span><input type="file" style="background-color:white;" name="uploadResume"  id="uploadResume" onclick="return fileUpload(this.file)" ></span> </td>
								<td align="left">Upload TEF :</td>
								<td align="left">
									<span><input type="file" style="background-color:white;" name="uploadTEF"  id="uploadTEF" onclick="return fileUpload(this.file)"></span>
									
									<input type="hidden" value=""	id="uploadResumeFileName" name="uploadResumeFileName"  />
									<input type="hidden" value=""	id="uploadTEFfileName" name="uploadTEFfileName"  />		
									
								<td align="left" style="color: #0174A3;text-decoration:none;">RRF Access :</td>
								<td align="left">
									<span><input type="checkbox" class="noWidth" value="" id="rrfAccess" name="rrfAccess" /></td></span></td>
											
									</td>
								<td align="left" style="color: #0174A3;text-decoration:none;">Report Access :</td>
								<td align="left">
									<span><input type="checkbox" class="noWidth" value="" id="reportUserId" name="reportUserId" /></td></span></td>
											
									</td>
													
						</tr>
						</sec:authorize>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td colspan="2" align="center" rowspan="2"><a
								href="javascript:void(0);" id="loanId"
								onclick="showResourceDetail()"><font size="2">Go To :
										Loan/Transfer History</font></a></td>
							<td></td>
							<td colspan="2" align="center" rowspan="2"><a
								href="javascript:void(0);" id="loanOrTransfer"
								onclick="loadHistory();"><font size="2">Loan/Transfer
										History</font></a></td>
						</tr>

					</table>
				</form>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</div>
<div class="alertWrapper danger" style="display: none">
	<div class="alertlistWrapper">
		<span class="closemeAlert">X</span>
		<div class="alert alert-danger" role="alert"></div>
	</div>
</div>
<div id="dialog" class="resourceDialog"
	style="background: white; color: red">
	<div id="jstree1">
		<b>Please Select BG/BU for Group Admin</b>
	</div>

	<div id="jstree2">

		<c:if test="${not empty bgs}">
			<ul>
				<c:forEach var="bg" items="${bgs}">
					<li id="${bg.id}" data="icon: '../../images/addAdminUser.gif'">${bg.name}
						<c:if test="${not empty bg.childOrgHierarchies}">
							<ul>
								<c:forEach var="bu" items="${bg.childOrgHierarchies}">
									<li id="${bu.id}" data="icon: '../../images/addAdminUser.gif'">${bu.name}
								</c:forEach>
							</ul>
						</c:if>
				</c:forEach>
			</ul>
		</c:if>
	</div>
	<!-- Please note do not delete below div in comments, this will be usefull for debugging/future enhancements -->
	<!-- <div>Selected keys: <span id="echoSelection3">-</span></div>
	<div>Selected root keys: <span id="echoSelectionRootKeys3">-</span></div>
	<div>Selected root nodes: <span id="echoSelectionRoots3">-</span></div> -->
</div>


<div id="primaryskilldialog" class="resoucePrimarySkillDialog"	style="background: white; color: red">
	<div id="jstree3">
		<b>Resource Primary Skill	</b>
	</div>

	<div id="skillResourcePrimaries">

		<c:if test="${not empty primarySkills}">
			<ul>
				<c:forEach var="primarySkills" items="${primarySkills}">

					<li id="${primarySkills.id}" data="icon: '../../images/'">${primarySkills.skill}
				</c:forEach>

			</ul>
		</c:if>

	</div>
	<!-- Please note do not delete below div in comments, this will be usefull for debugging/future enhancements -->
	<!-- <div>Selected keys: <span id="echoSelection3">-</span></div>
	<div>Selected root keys: <span id="echoSelectionRootKeys3">-</span></div>
	<div>Selected root nodes: <span id="echoSelectionRoots3">-</span></div> -->
</div>
<div id="seconddaryskilldialog" class="resouceSecondarySkillDialog"
	style="background: white; width =400; height =400; color: red">
	<div id="jstree4">
		<b>Resource Secondary Skills
			</h3>
	</div>

	<div id="skillResourceSecondaries">

		<c:if test="${not empty secondrySkills}">
			<ul>
				<c:forEach var="secondrySkills" items="${secondrySkills}">
					<li id="${secondrySkills.id}" data="icon: '../../images/'">${secondrySkills.skill}
				</c:forEach>
			</ul>
		</c:if>

	</div>
	<!-- Please note do not delete below div in comments, this will be usefull for debugging/future enhancements -->
	<!-- <div>Selected keys: <span id="echoSelection3">-</span></div>
	<div>Selected root keys: <span id="echoSelectionRootKeys3">-</span></div>
	<div>Selected root nodes: <span id="echoSelectionRoots3">-</span></div> -->
</div>



<script id="resourceAllocationRows" type="text/x-jquery-tmpl">					
 							<tr class="even resourceallocrow" style="color:black">
			                <td width="15%" align="center" valign="middle">
								{{if eventId}}
									{{>eventId.event}}
								{{/if}}
							</td>
                          
							<td width="15%" align="center" valign="middle">{{>ownership.ownershipName}}</td>
                            <td width="15%" align="center" valign="middle">{{>employeeCategory.employeecategoryName}}</td>
							<td width="15%" align="center" valign="middle">{{>designationId.designationName}}</td>
							<td width="15%" align="center" valign="middle">{{>gradeId.grade}}</td>		
							<td width="15%" align="center" valign="middle">
								{{if currentReportingManager}}
									{{>currentReportingManager.employeeName}}
								{{/if}}
							</td>	
							<td width="15%" align="center" valign="middle">
								{{if currentReportingManagerTwo}}
									{{>currentReportingManagerTwo.employeeName}}
								{{/if}}
							</td>			
							<td width="15%" align="center" valign="middle">{{>locationId.location}}</td>
							<td width="15%" align="center" valign="middle">
							{{if deploymentLocationId}}
									{{>deploymentLocationId.location}}
								{{/if}}
							</td>
							<td width="15%" align="center" valign="middle">{{>buId.parentId.name}}-{{>buId.name}}</td>
							<td width="15%" align="center" valign="middle">{{>buId.parentId.name}}-{{>currentBuId.name}}</td>
                            <td width="15%" align="center" valign="middle">{{>creationTimestamp}}</td>
							<td width="15%" align="center" valign="middle">{{>transferDate}}</td>
							<td width="15%" align="center" valign="middle">{{>createdId}}</td>

							<td width="15%" align="center" valign="middle">
								{{if bGHName}}
									{{>bGHName.employeeName}}
								{{/if}}
							</td>
							<td width="15%" align="center" valign="middle">{{>bGHComments}}</td>
							<td width="15%" align="center" valign="middle">{{>bGCommentsTimestamp}}</td>
							
							<td width="15%" align="center" valign="middle">
								{{if bUHName}}
									{{>bUHName.employeeName}}
								{{/if}}
							</td>
							<td width="15%" align="center" valign="middle">{{>bUHComments}}</td>	
							<td width="15%" align="center" valign="middle">{{>bUCommentsTimestamp}}</td>

							<td width="15%" align="center" valign="middle">
								{{if hRName}}
									{{>hRName.employeeName}}
								{{/if}}
							</td>
							<td width="15%" align="center" valign="middle">{{>hRComments}}</td>					
							<td width="15%" align="center" valign="middle">{{>hRCommentsTimestamp}}</td>
																				                        			
			            </tr>					
</script>
<div id="dialoglt"
	style="background: white; width =860; height =350; color: #3377BB;">
	<div id="dialogText" align="center" style="">
		<b>Resource Loan/Transfer History
			</h3>
	</div>

	<div>
		<table id="tableResourceData"
			class="dataTbl display tablesorter addNewRow">
			<thead>
				<tr style="color: red">
			
							<th width="15" align="center" valign="middle">Event</th>
							<th width="15" align="center" valign="middle">Status</th>
						    <th width="15" align="center" valign="middle">Employee Category</th>
							<th width="15%" align="center" valign="middle">Designation</th>
							<th width="15%" align="center" valign="middle">Grade</th>							
							<th width="15%" align="center" valign="middle">RM1</th>
							<th width="15%" align="center" valign="middle">RM2</th>
							<th width="15%" align="center" valign="middle">Base Location</th>
							<th width="15%" align="center" valign="middle">Current Location</th>
							<th width="15%" align="center" valign="middle">Parent BG-BU</th>
							<th width="15%" align="center" valign="middle">Current BG-BU</th>

							<th width="15%" align="center" valign="middle">System Date</th>
							<th width="15%" align="center" valign="middle">Loan/Transfer Date</th>
							<th width="15%" align="center" valign="middle">Updated by</th>

							<th width="15%" align="center" valign="middle">BGH Name</td>
							<th width="15%" align="center" valign="middle">BGH Comments</th>
							<th width="15%" align="center" valign="middle">BGH Comment Date</th>
							
							<th width="15%" align="center" valign="middle">BU Name</th>
							<th width="15%" align="center" valign="middle">BU Comments</th>
							<th width="15%" align="center" valign="middle">BU Comment Date</th>
							
							<th width="15%" align="center" valign="middle">HR Name</th>
							<th width="15%" align="center" valign="middle">HR Comments</th>
							<th width="15%" align="center" valign="middle">HR Comment Date</th>
				</tr>
			</thead>
			<tbody id="resourceHData">
			</tbody>
		</table>

	</div>
</div>


<!-- file alert-->
<div id="fileAlert">
	<p></p>
	<div class="fileAlertBtm">
		<input type="button" name="Ok" value="Ok" id="ok" />
	</div>
</div>
 
 <script>
// $('body').on("click",'#clearDate',function(){ 
	 
/* //$("#dateSearch").datepicker("hide");
 $("#resignationDate").datepicker("hide");
 //$('#weekEndDate').datepicker('setDate', null);
 $('#resignationDate').datepicker('setDate', null);

    }); */
</script>
<!--START: Alert: Added by Pratyoosh Tripathi -->			
				<div class="notification-bar">
<!-- <div class="close closeIconPosition">close</div> -->
<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
  <!-- <div class="notification-text">Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.</div> -->
  <sec:authorize
				access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')">
				<div class="notification-text">You can view or add a Resource into RMS by clicking on "Add new" link</div>
				
						
	</sec:authorize>
	
	<sec:authorize
				access="hasAnyRole('ROLE_DEL_MANAGER')">
				<div class="notification-text">You can only view the details of the resources by clicking on its Employee Id. However, please connect with Team RMG for new Resource.</div>
				
						
	</sec:authorize>
	<sec:authorize
				access="hasAnyRole('ROLE_HR')">
				<div class="notification-text"> You can only view the details of all the resources present in 
RMS clicking on their Employee Id.</div>
				
						
	</sec:authorize>
	
	
  
  
</div>

<style>
.brdrRed {
	border: 1px solid red !important;
}
.alertWrapper {
	position: fixed;
	top: 0px;
	left: 0px;
	z-index: 9999;
	text-align: center;
	width: 100%;
	flex-direction: column;
	height: 100%;
	align-items: center;
	padding-top: 180px;
	display: none;
}

.alertWrapper.danger .alert-danger {
	background-color: #9c2c1e !important;
	color: #fff;
	border-color: #8a3226;
	width: 400px;
	margin: 0px auto;
	text-transform: capitalize;
	box-shadow: 0px 4px 8px #b7b7b7;
	position: relative;
	padding-left: 55px;
	text-align: center;
}

.alertlistWrapper {
	position: relative;
	display: table;
	margin: 0px auto;
}

.alert.alert-danger:before {
	content: "!";
	display: inline-block;
	font-family: 'Glyphicons Halflings';
	font-style: normal;
	font-weight: bold;
	line-height: 1;
	-webkit-font-smoothing: antialiased;
	position: absolute;
	left: 9px;
	top: 50%;
	font-size: 26px;
	color: #e2897e;
	background: #6b1c12;
	padding: 0px 12px 5px;
	border-radius: 50%;
	border: 1px solid #6f1e14;
	margin-top: -17px;
}

span.closemeAlert {
	position: absolute;
	top: -5px;
	right: -5px;
	width: 25px;
	height: 25px;
	background: #e2897e;
	border-radius: 50%;
	line-height: 25px;
	color: #350f0a;
	z-index: 1;
	cursor: pointer;
}

.alert.alert-danger p {
	text-align: left;
	border-bottom: 1px dashed #ea7b6d;
	padding-bottom: 5px;
	margin-bottom: 10px;
}

.alert.alert-danger p:last-child {
	border-bottom: 0px;
	margin-bottom: 0px;
}

.alertWrapper.success .alert-success {
	background-color: #228633 !important;
	color: #fff;
	border-color: #59a082;
	width: 400px;
	margin: 0px auto;
	text-transform: capitalize;
	box-shadow: 0px 4px 8px #b7b7b7;
	position: relative;
	padding-left: 55px;
	text-align: center;
}

.alert.alert-success:before {
	content: "\e013";
	display: inline-block;
	font-family: 'Glyphicons Halflings';
	font-style: normal;
	font-weight: 400;
	line-height: 1;
	-webkit-font-smoothing: antialiased;
	position: absolute;
	left: 12px;
	top: 8px;
	font-size: 17px;
	color: #cff1d5;
	background: #166523;
	padding: 8px;
	border-radius: 50%;
	border: 1px solid #12521d;
}

.notification-bar {
	width: 400px;
	background: rgba(30, 159, 180, .8);
	padding: 10px;
	position: fixed;
	bottom: 30px;
	right: 25px;
}

.closeIconPosition {
	position: absolute;
	top: -10px;
	right: -20px;
}

.toast-close {
	float: right;
	display: block;
	width: 13px;
	height: 13px;
	padding: 0px 4px;
	border-radius: 3px;
	background: #000 url(/rms/resources/images/cross_icon_white.png)
		no-repeat center !important;
	z-index: 1000;
	position: absolute;
	top: -5px;
	right: -5px;
	opacity: .7 !important;
}

.notification-text {
	font-size: 14px;
	color: #fff;
	text-align: justify;
}
</style>


<!--END: Alert: Added by Pratyoosh Tripathi -->
