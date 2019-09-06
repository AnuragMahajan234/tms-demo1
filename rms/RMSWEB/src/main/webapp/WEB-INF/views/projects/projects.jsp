<%@page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<spring:url
	value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url
	value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />

<!-- Code for single select - start -->
	<spring:url value="/resources/css/select2.min.css?ver=${app_js_ver}"
    var="select2_css" />
<link href="${select2_css}" rel="stylesheet" type="text/css"></link>

<spring:url value="/resources/js/select2.min.js?ver=${app_js_ver}"
    var="select2_js" />
<script src="${select2_js}" type="text/javascript"></script>
<!-- Code for single select - end -->

<style type="text/css" title="currentStyle">
thead input {
	width: 100%
}

table.dataTable {
	margin: 10px auto 0;
}

input.search_init {
	color: #999
}

#dialog, .ui-dialog {
	overflow: visible;
}

#projectTableId_wrapper, #projectTableId_wrapper .dataTables_scrollHeadInner,
	#projectTableId_wrapper .dataTable {
	width: 100% !important;
}

#projectTableId {
	table-layout: fixed;
}

#projectTableId thead th {
	width: 120px;
}

#projectTableId td {
	word-wrap: break-word;
}

#projectTableId_wrapper .dataTables_scrollBody {
	max-height: 700px;
}

 
/*added by kratika-1-4-19 for #280*/
#projectTableId_wrapper .dataTables_scrollHead{
   width:100% !important;
}
#projectTableId_wrapper table.dataTable {
	margin: 0 auto;
	clear: both;
	width: 100%;
	border: 0px solid #9b9b9b;
	border-collapse: separate;
	border-spacing: 1px;
}
table#modal_data_table {
    width: 99% !important;
}
/*updated css added by kratika-06-5-19*/
table.dataTbl.display.tablesorter.addNewRow.alignCenter.my_table.tablefixed.dataTable
	{
	table-layout: auto;
}

table.dataTbl.display.tablesorter.addNewRow.alignCenter.my_table.dataTable
	{
	max-height: 751px;
	display: table !important;
}
.mytable.alignCenter tbody td {
    text-align: left;
}
.mytable.alignCenter tbody td:first-child {
    width: 18%;
}
.my_table thead th {
    border-bottom: 1px solid #ccc !important;
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

#projectTable .dataTables_scroll {
	overflow-x: hidden !important;
}

#projectTable .dataTables_scrollHead {
	width: 100% !important;
}

#projectTable .dataTables_wrapper .dataTables_info {
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
#projectTableId_length {
    padding-right: 10px;
    padding-top: 7px;
}
#projectTable .resourceBtnIcon {
    left: 800px !important;
    position: absolute;
    top: 1px !important;
    z-index: 9;
}
.dataTables_paginate.paging_full_numbers {
    margin-bottom: 8px;
    float: left !important;
    margin-left: 165px !important;
}

 #projectTableId_length label select {
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
#projectTable .projecttoolbar{
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
#projectTable .projectTableId_wrapper .dataTables_info {
    width: 20%;
    margin-top: 9px;
}
.select2-container--default .select2-selection--multiple .select2-selection__choice {
	color: #000;
	background-color: #ddd;
 }
 .select2-container {
	width: 135px !important;
 }
 div#modal_data_table_wrapper {
	max-height: 420px;
	min-height: 160px;
	overflow-x: auto;
	background: transparent;
	height: auto;
 }
 table#modal_data_table {
	margin:0 0 10px 0;
	table-layout: auto;
 }
#rrfModal .modal-body {
	background: #f5faff;
	padding-top: 40px;
 }
 #rrfModal .dataTables_length {
	top: 8px;
 }
 #rrfModal  .dataTables_filter {
	left: 145px;
	top: 8px;
 }
 #modal_data_table_wrapper table.dataTbl thead th {
	padding: 8px 3px;
    border-radius: 0;
    width: auto !important;
 }
 #rrfModal .paging_full_numbers a.paginate_button {
    background: transparent !important;
 }
 #rrfModal .dataTables_paginate {
	float: none !important;
	text-align: center;
	margin: 0 auto;
	padding-top: 25px;
	margin-left: 0 !important;
 }
 #rrfModal #modal_data_table_filter input {
 	width: 250px;
 }
</style>

<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css1" />
<link href="${style_css1}" rel="stylesheet" type="text/css"></link>
<spring:url
	value="/resources/js-framework/validate/jquery.validVal-4.0.3-packed.js?ver=${app_js_ver}"
	var="jquery_validVal" />
<script id="projectTableRows" type="text/x-jsrender">
 						<tr id="{{>id}}">
			               <sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER')">
			                <td align="left" valign="middle">
							{{if managerReadonly}}
			                	<a href="#" onclick="openMaintainance({{>id}});"> {{>projectCode}}</a>
							{{else}}
								<a href="javascript:void(0)"  class="copyInactive"  disabled="disabled"> {{>projectCode}}</a>
							{{/if}}
 							</td>
						</sec:authorize>
			            <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_HR')">
						<td align="left" valign="middle">
   							<a href="#" onclick="openMaintainance({{>id}});"> {{>projectCode}}</a>
						</td>
						</sec:authorize>		
				
			                <td align="left" valign="middle">
							{{if projectName}}
									{{>projectName}}
								  {{else}}
									  N.A.
								{{/if}}
							</td>
						 <td align="left" valign="middle" >
                             {{if orgHierarchy}}
									{{>orgHierarchy.parentId.name}}-{{>orgHierarchy.name}}
								  {{else}}
									  N.A.
								{{/if}}
								
							</td>
			                <td align="left" valign="middle">
								{{if customerNameId}}
									{{>customerNameId.customerName}}
								  {{else}}
									  N.A.
								{{/if}}
							</td>
			               
			                 <td align="left" valign="middle">
								{{if offshoreDelMgr.employeeName}}
									{{>offshoreDelMgr.employeeName}}
								  {{else}}
									  N.A.
								{{/if}}
							</td>
			                 <td align="left" valign="middle">
								{{if onsiteDelMgr}}
									{{>onsiteDelMgr}}
								  {{else}}
									  N.A.
								{{/if}}
							 </td>
			                 <td align="left" valign="middle">
								{{if engagementModelId}}
									{{>engagementModelId.engagementModelName}}
								  {{else}}
									  N.A.
								{{/if}}
								
							</td>
			                 <td align="left" valign="middle">
								{{if projectKickOff}}
									{{>projectKickOff}}
								  {{else}}
									  N.A.
								{{/if}}
							</td>
			                 <td align="left" valign="middle">
								{{if plannedProjSize}}
									{{>plannedProjSize}}
								  {{else}}
									  N.A.
								{{/if}}
							</td>
							<td align="left" valign="middle">
								{{if deliveryMgr.employeeName}}
									{{>deliveryMgr.employeeName}}
								  {{else}}
									  N.A.
								{{/if}}
							</td>
			            </tr>

</script>
<script id="poTableRows" type="text/x-jsrender">
 		<tr class="costRow" >
			<td ><div class="positionRel" id="">
				<input type="text" id="poNumber" name="projectPoes[{{:~GetRowId()}}].poNumber"  class="required string numericInp readOnlyinput poNumber" />
				<span class="errorNumeric" style="display: none;">Please Enter Numeric Numbers Only <img src="images/errorAerrow.png"></span>
				</div>
			</td>
	    	<td width="40%">
				<input type="text" id="poAcName" name="projectPoes[{{:~GetRowId()}}].accountName" class="string required readOnlyinput server-validation"  />
			</td>
			<td width="20%"><div class="positionRel">
				<input type="text" id="poCost" name="projectPoes[{{:~GetRowId()}}].cost" class="required inpCost numericInp" />
				<span class="errorNumeric" style="display: none;">Please Enter Numeric Numbers Only <img src="images/errorAerrow.png"></span>
			</div></td>
			<td>
				<input type="text" id="issueDate{{:~GetRowId()}}" name="projectPoes[{{:~GetRowId()}}].issueDate" />
			</td>
			<td>
				<input type="text" id="validUptoDate{{:~GetRowId()}}" name="projectPoes[{{:~GetRowId()}}].validUptoDate" />
			</td>
<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_HR')">
			<td width="5%" align="left">
				<a href="javaScript:void(0)"><img width="16" height="16" class="removePo" src="resources/images/remove.png"></a>
			</td>
</sec:authorize>
	    </tr>

</script>

<script id="poTableRowsWithValues" type="text/x-jsrender">
 						<tr class="costRow" id="{{>id}}">
							<td><div class="positionRel">
							<input type="text" id="poNumber" name="projectPoes[{{:#index}}].poNumber"  class="required string numericInp readOnlyinput" value="{{>poNumber}}"/>
							<span class="errorNumeric" style="display: none;">Please Enter Numeric Numbers Only <img src="images/errorAerrow.png"></span>
							</div></td>
	                		<td width="40%">
								<input type="hidden" id="poId" name="projectPoes[{{:#index}}].id" value="{{>id}}"/>
								<input type="text" id="poAcName" name="projectPoes[{{:#index}}].accountName" class="required string readOnlyinput" value="{{>accountName}}"/>
							</td>
							<td width="20%"><div class="positionRel">
								<input type="text" id="poCost" name="projectPoes[{{:#index}}].cost" class="required inpCost numericInp readOnlyinput"   value="{{>cost}}" />
								<span class="errorNumeric" style="display: none;">Please Enter Numeric Numbers Only <img src="images/errorAerrow.png"></span>
							</div></td>
							<td>
							<input type="text" id="issueDate{{:#index}}" name="projectPoes[{{:#index}}].issueDate" value="{{>issueDate}}" class="readOnlyinput"/>
							</td>
							<td>
							<input type="text" id="validUptoDate{{:#index}}" name="projectPoes[{{:#index}}].validUptoDate" value="{{>validUptoDate}}" class="readOnlyinput" />
							</td>
<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_HR')">
							<td width="5%" align="left"><a href="javaScript:void(0)"><img width="16" height="16" class="removePo" src="resources/images/remove.png" class="readOnlyinput" ></a></td>
</sec:authorize>
	                	</tr>

</script>

<script>

function moveToRRF(){
	 window.location = "${pageContext.request.contextPath}/requestsReports/positionDashboard";
}
function moveByRRFId(id){

	window.location = "${pageContext.request.contextPath}/requests?reqId="+id+"&copyflag=copyFlagFalse";
}	
function getSerialNo(i){
	return i+"";
}
function prepareRequirementId(rrfId, rrfName){

	dtData="";
	rrfName=rrfName.trim();
	dtData="<a href='#' onclick='moveByRRFId("+rrfId+");' >"+rrfName+"</a></br>";
	return dtData;
}
function closeModal() {
    $('#modal_data_table').dataTable().fnFilter('');
}
var data={
		getCostRowId: function(arg){
			return 	$("table#addPoTbl tbody").find("tr").length;			
		}
};
$.views.helpers({ GetRowId: data.getCostRowId });
 
$.templates("poTableRowsTempl", {
    markup: "#poTableRows"
});
$.templates("poTableRowsWithValuesTempl", {
    markup: "#poTableRowsWithValues"
    
});

</script>

 <!-- Modal -->
  <div class="modal fade" id="rrfModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"  onclick="closeModal()">&times;</button>
          <h1 class="modal-title">RRF Details</h1>
        </div>
        <div class="modal-body">
               <table class="dataTbl display tablesorter addNewRow alignCenter my_table tablefixed mytable"
							id="modal_data_table"
							style="overflow: auto; display: inline-block; border: 1px solid #ccc;">
							<thead>
								<tr class="content">
									<th align="center" valign="middle">S. No.</th>
									<th align="center" valign="middle">RRF Name</th>
									<th align="center" valign="middle">Status</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
        </div>
        <div class="modal-footer">
          <!--  <button type="button" onclick="moveToRRF()">Go To Resource Request</button> -->
           <button onclick="moveToRRF()">Go To Resource Request</button>
           <button type="button" class="btn btn-default" data-dismiss="modal"  onclick="closeModal()">Close</button>
        </div>
      </div>
      
    </div>
  </div>

 <!-- Modal -->

<div class="content-wrapper">
	<!--right section-->
	<div class="botMargin">
		<h1>Projects</h1>
	</div>
	<div class="tab_seaction">
		<ul class='tabs'>
			<li><a href='#tab1' onclick="listClick()" class="listTab">List</a></li>
			<li><a class="MaintenanceTab listTab"
				id="MaintenanceTabInactive" href='#tab2'>Maintenance</a></li>
		</ul>
		<div id='tab1' class="tab_div">
			<div class="tbl"></div>
			<div class="clear"></div>
			<div id="projectTable">
				<table class="dataTbl display tablesorter dataTable addNewRow alignCenter white-sort-icons my_table positiondashboard-table"
					id="projectTableId" style="overflow: auto; display: inline-block;">
					<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_HR')">
						<div id="projStatus"
							style="position: absolute; left: 150px; z-index: 9; top: 12px;padding-left:5px;font-weight:700">
							<span>Status : <select id="projectActive">
									<option selected="selected" value="active">Active</option>
									<option value="all">All</option>
							</select>
							</span>
						</div>
						<span class="addNewContainer" style="float:right"><a href="#"
								class="blue_link" id="addNew">
									+ Add New
							</a></span>
					</sec:authorize>
					<thead>
						<tr>
							<th align="center" valign="middle">Project ID</th>
							<th align="center" valign="middle">Project ID</th>
							<th align="center" valign="middle">Project Name</th>
							<th align="center" valign="middle">Customer Name</th>
							<th align="center" valign="middle">Manager</th>
							<th align="center" valign="middle">Customer Relationship
								Manager</th>
							<th align="center" valign="middle">Project BU</th>
							<th align="center" valign="middle">Engagement Mode</th>
							<th align="center" valign="middle">Project Kick off Date</th>
							<th align="center" valign="middle">Planned Project Size</th>
							<th align="center" valign="middle">Project End Date</th>
							<th align="center" valign="middle">Delivery Manager</th>
						</tr>
						<tr class="">
							<td><input type="text" name="search_projectID"
								value="Project ID" class="search_init" hidden="true" /></td>
							<td><input type="text" name="search_projectCode"
								value="Project ID" class="search_init" disabled="disabled" /></td>
							<td><input type="text" name="search_projectName"
								value="Project Name" class="search_init" /></td>
							<td><input type="text" name="search_customerName"
								value="Customer Name" class="search_init" /></td>
							<td><input type="text" name="search_offshoreManager"
								value="Offshore Manager" class="search_init" /></td>
							<td><input type="text" name="search_onsiteManager"
								value="Onsite Manager" class="search_init" /></td>
							<td><input type="text" name="search_currentBG-BU"
								value="Current BG-BU" class="search_init" /></td>
							<td><input type="text" name="search_engagementModel"
								value="Engagement Model" class="search_init" /></td>
							<td><input type="text" name="search_projectKickoffDate"
								value="Project Kick off Date" class="search_init"
								disabled="disabled" /></td>
							<td><input type="text" name="search_plannedProjectSize"
								value="Planned Project Size" class="search_init"
								id="plannedProjectSize" /></td>
							<td><input type="text" name="search_projectEndDate"
								disabled="disabled" value="Project End Date" class="search_init" /></td>
							<td><input type="text" name="search_deliveryManager"
								value="Delivery Manager" class="search_init" /></td>
						</tr>
					</thead>
					<tbody>


					</tbody>
				</table>
				</div>
			
			<div class="clear"></div>
		</div>
		<div id='tab2' class="tab_div">
			<sec:authorize
				access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_DEL_MANAGER','ROLE_HR')">
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
				<form id="projectForm" name="projectForm" action="projects">
					<table id=projectFormTable>

						<tr id="defaultRowId">
							<td><input type="checkbox" name="defaultActivitiesId"
								id="defaultActivitiesId" style="width: 20px;">Set
								Default Activities</td>
						</tr>

						<tr>
							<td width="15%" align="left">Project ID :</td>
							<td width="18%" align="left"><input name="projectCode"
								id="projectCode" readonly="readonly"
								value="${project.projectCode} " /><input type="hidden" value=""	name="id" id="projectId"/></td>
							<td width="15%" align="left">Project Name :<span
								class="astric">*</span></td>
							<td width="15%" align="left">
								<div class="positionRel">
									<input type="text" value="" name="projectName"
										class="required string server-validation" maxlength="50" />
									<div class="errorNumericLast errorNumeric"
										style="display: none">
										<img src="resources/images/errorAerrowUp.png"><span>This
											Project Name is already registered. </span>
									</div>
								</div>
							</td>
							
							<td align="left">Project Name as SOW: </td>
							<td align="left">
								<div class="positionRel">
									<input type="text" value="" name="projectNameSOW" id="projectNameSOW" />
								</div>
							</td>
							
						</tr>
						<tr>
							<td align="left">BU :<span class="astric">*</span></td>
							<td><select id="orgHierarchy" name="orgHierarchy.id"
								class="required comboselect dd_Custom_BU">
									<c:forEach var="bus" items="${bus}">
										<option value="${bus.id}">${bus.parentId.name}-${bus.name}</option>

									</c:forEach>
							</select></td>
							<td align="left">Customer Name :<span class="astric">*</span></td>
							<td><select id="customerNameSelect" name="customerNameId.id"
								class="required comboselect dd_customer">
									<option value="-1">SELECT</option>
									<c:forEach var="customer" items="${customers}">
										<option value="${customer.id}">${customer.customerName}</option>
									</c:forEach>
							</select></td>
							
							<td id="customerGroupText" align="left">Customer Group :</td>
							<td id="customerGroupDD"><select id="customerGroupSelect" name="customerGroup.groupId"
								class="comboselect dd_project">
							</select>
							</td>
							
						</tr>
						<tr>
						
						<td width="18%" align="left">Parent Project / Team :</td>
							<td width="20%" align="left"><select id="team.id"
								name="team.id" class="comboselect">
									<option value="-1">SELECT</option>
									<c:forEach var="team" items="${teams}">
										<option value="${team.id}">${team.teamName}</option>
									</c:forEach>
							</select></td>
						
							<td align="left">Customer Contacts :</td>
							<td align="left"><input type="text" value=""
								name="customerContacts" class="string" /></td>
							<td align="left">Manager :<span class="astric">*</span></td>
							<td align="left"><!-- <select name="offshoreDelMgr.employeeId"
								id="offshoreDelMgr.employeeId"
								class="dd_cmn comboselect required"> -->
								
								<select name="offshoreDelMgr.employeeId"  id="offshoreDelMgrId" class="dd_cmn required resClass">

									
									<%-- <option value="-1">SELECT</option>
									<c:forEach var="resource" items="${resources}">
										<option value="${resource.employeeId}">${resource.employeeName}</option>
									</c:forEach>  --%>
							</select></td>
							</tr>
							
						<tr>
						<td align="left">Customer Relationship Manager :</td>
							<td align="left"><input type="text" value=""
								name="onsiteDelMgr" maxlength="50" class="string" /></td>
						
							<td align="left">Engagement Model: <span class="astric">*</span></td>
							<td align="left"><select id="engagementModelId.id"
								name="engagementModelId.id"
								class="required comboselect dd_customer">
									<option value="-1">SELECT</option>
									<c:forEach var="engagementmodel" items="${engagementmodels}">
										<option value="${engagementmodel.id}">${engagementmodel.engagementModelName}</option>
									</c:forEach>
							</select></td>

							<!-- added extra combobox (hidden),To compare changed value -->
							<select id="engagementModelId" name="engagementModelId.id"
								style="display: none;">
								<option value="-1">SELECT</option>
								<c:forEach var="engagementmodel" items="${engagementmodels}">
									<option value="${engagementmodel.id}">${engagementmodel.engagementModelName}</option>
								</c:forEach>
							</select>
							</td>

							<td align="left">Project Kick off date :</td>
							<td align="left"><input type="text" value=""
								name="projectKickOff" id="projectKickOff" readonly="readonly" /></td>
								</tr>
							
						
						<tr>
						<td align="left">End Date :</td>
							<td><input type="text" id="endDate" name="projectEndDate"
								readonly="readonly" /></td>
						
							<td align="left">Planned Project Size (Person Weeks):</td>
							<td align="left">
								<div class="positionRel">
									<input type="text" value="" name="plannedProjSize"
										id="plannedProjSize" class="number" maxlength="3" />
								</div>
							</td>

							<td align="left">Project tracking currency : <span
								class="astric">*</span></td>
							<td align="left"><select id="projectTrackingCurrencyId.id"
								name="projectTrackingCurrencyId.id" class="comboselect required">
									<option value="-1">SELECT</option>
									<c:forEach var="currency" items="${currencys}">
										<option value="${currency.id}">${currency.currencyName}</option>
									</c:forEach>
							</select></td>
							</tr>
							
						
						<tr>
						<td align="left">Planned Project Budget :</td>
							<td align="left"><label id="planProBudg"></label> 
								<input type="hidden" id="planProBudgId" name="plannedProjCost"
								value="" /></td>
							<td align="left">Development Methodology :<span
								class="astric">*</span></td>
							<td><select name="projectMethodologyId.id"
								id="projectMethodologyId.id" class="required dd_cmn comboselect">
									<option value="-1">SELECT</option>
									<c:forEach var="projectmethodology"
										items="${projectmethodologys}">
										<option value="${projectmethodology.id}">${projectmethodology.methodology}</option>
									</c:forEach>
							</select></td>
							<td align="left">Description :</td>
							<td><textarea rows="" cols="" name="description">${description}</textarea></td>
							</tr>
							<tr>
							<td width="18%" align="left">Invoiced by : <span
								class="astric">*</span></td>
							<td width="20%" align="left">
								<select id="invoiceById.id"
									name="invoiceById.id" class="required comboselect">
									<option value="-1">SELECT</option>
									<c:forEach var="invoiceBy" items="${invoicesBy}">
										<option value="${invoiceBy.id}">${invoiceBy.name}</option>
									</c:forEach>
								</select>
							</td>
							<td align="left">Delivery Manager :<span class="astric">*</span></td>
							<td align="left"><%-- <select name="deliveryMgr.employeeId"
								id="deliveryMgr.employeeId"
								class="dd_cmn comboselect required">

									<option value="-1">SELECT</option>
									<c:forEach var="resource" items="${resources}">
										<option value="${resource.employeeId}">${resource.employeeName}</option>
									</c:forEach>
							</select> --%>
							
							<select name="deliveryMgr.employeeId"	id="deliveryMgrId"	class="dd_cmn required resClass"></select>
							</td>
							<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
								<div class="col-sm-12 check-box-message">
								 <td align="left"> is Trainee Project :</td>
							  	 <td align="left"><input style="width: inherit;" type="checkbox" id="traineeProject" name="traineeProject"/> </td>
							    </div>
						    </sec:authorize>
						</tr>
						
						<tr>
						<td width="18%" align="left">Project Category :</td>
							<td width="20%" align="left"><select id="pCId"
								name="projectCategoryId.id" class="comboselect dd_project">
									<option value="-1">SELECT</option>
									<c:forEach var="projectcategory" items="${projectcategorys}">
										<option value="${projectcategory.id}">${projectcategory.category}</option>
									</c:forEach>
							</select></td>
						</tr>
					</table>
					<div class="clear"></div>
					<div class="poTbl">
						<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
							<a href="javaScript:void(0)" id="addPo" class="blue_link fr"><img
								height="22" width="16" src="resources/images/addUser.gif">Add
								PO </a>
						</sec:authorize>

						<div class="clear"></div>
						<table class="tablesorter dataTable" id="addPoTbl">
							<thead>
								<tr>
									<th>PO Number</th>
									<th>PO / Invoicing Details</th>
									<th>Cost</th>
									<th>Issue Date</th>
									<th>Valid Upto Date</th>
									<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
										<th>Delete</th>
									</sec:authorize>
								</tr>
							</thead>
							<tbody>

							</tbody>
						</table>
					</div>
				</form>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<!--right section-->
</div>
<div class="clear"></div>
<div class="notification-bar">
	<!-- <div class="close closeIconPosition">close</div> -->
	<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
	<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_HR')">
		<div class="notification-text">You can able to view or add a
			Project into RMS clicking on "Add new link".</div>


	</sec:authorize>
	<sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER')">
		<div class="notification-text">You can only view the details of
			your projects clicking on its Project Id but unable to add a new.</div>
	</sec:authorize>

</div>

<style>
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
<script type="text/javascript">
$( document ).ready(function() {
      
	$("#customerGroupDD").hide();
	$("#customerGroupText").hide();	
	$('[data-toggle="tooltip"]').tooltip();
	
	setInterval(function(){ $('[data-toggle="tooltip"]').fadeToggle(500); }, 1000);
	
	<%Boolean flag = false;
			flag = (Boolean) session.getAttribute("notificationbarflag");%>
	
	
$('.close').click(function(){
    $('.notification-bar').hide();
   });
   


	$(".resClass").select2({
		ajax: {
			url: "/rms/projects/activeUserList",
			dataType: 'json',
			data: function (params) {
				 
				return {
					userInput: params.term || '',
				};
			},
			processResults: function (data, params) {
				return {
					results: populateActiveUsers(JSON.parse(data.activeUserList)),
				};
			},
		},	
		minimumInputLength: 3,
		allowClear: true,
		createTag: function(params) {
            return undefined;
	   },
	  placeholder: 'Search Recipient'
	});


function populateActiveUsers(userList) {
				
	$.map(userList, function(item, idx) {
		item.id = item.employeeId;
		
	 	item.text = item.firstName + " " + item.lastName + "(" + item.yashEmpId + ")";
	});

	return userList;
}

});

/* $("thead input").keyup( function(){
	projectTable.fnFilter( this.value, projectTable.oApi._fnVisibleToColumnIndex(projectTable.fnSettings(), $("thead input").index(this) ));
}); */
$("thead input").keyup( function(i){
	
	if((i.which==13 && this.value.trim().length!=0)||(i.which==8 && this.value.trim().length==0))
		{
		
		projectTable.fnFilter( this.value, projectTable.oApi._fnVisibleToColumnIndex(projectTable.fnSettings(), $("thead input").index(this) ) );
		}
		});

$("thead input").each( function(i){
	this.initVal = this.value;
});

$("thead input").focus( function () {
	if ( this.className == "search_init" ){
		this.className = "";
		this.value = "";
	}
});
	
$("thead input").blur( function (i) {
	if ( this.value == "" ){
		this.className = "search_init";
		this.value = this.initVal;
	}
});


</script>

<script>
var endDatePicker="";
function listClick(){
	 $("#customerGroupDD").hide();
		$("#customerGroupText").hide();	
}

$(document).on('change','#customerNameSelect',function(){
	var customerID = $('#customerNameSelect').val(); 
	customerName = $('#customerNameSelect option:selected').text();
	 if(customerID == "select" || customerID == 0){
			showErrorAlert("Please Select a Client!");
			
	}else{
		 $('#customerGroupSelect').text('');
			startProgress();
		$.ajax({
    		type: 'GET',
    		dataType: 'text',
            url: '/rms/requests/getGroupBasedOnCustomerId/'+customerID,
            contentType:"application/json; charset=utf-8",
            cache: false,
            success: function(data) { 
            	stopProgress();
            	var optionForCustomerGroup="";
				 $('#customerGroupSelect').empty();
				 
            	 var response = JSON.parse(data);   
            	if(!response.length){
            		$("#customerGroupDD").hide();
            		$("#customerGroupText").hide();
            	}
            	else{
            		$("#customerGroupDD").show();
            		$("#customerGroupText").show();
            		for( var i = 0 ; i< response.length ; i++){
                		var index = response[i];                		
                	    optionForCustomerGroup = "<option value= '" + index[0] + "'>" + index[2] + "</option>";
                	    $("#customerGroupSelect").append(optionForCustomerGroup);
                	}
            	}	
	     	},
	     	error: function(error){	     		
	     		stopProgress();
	     		showError("Something went wrong!!");
	     		//window.location.reload();
	     	}
		});
	}
});

var idArray = new Array();
var status = "";
$('.poTbl').on("click", ".removePo", function(){

	 	 var conf=confirm("Are you sure you want to delete this PO?");	 
		 var id =  $(this).closest("tr").attr('id');
		 idArray.push(id);
		 
		  if(conf){
			  
			 $(this).closest("tr").remove();	
			 
		 } 
		 return;				    	
 });


		// Wait until the DOM has loaded before querying the document
		var projectTable;
		var isNew;
		function appendUsDeere()
		{
			var test=$("#buId option[value='5']").text();
	 	    if(test==""){
	 			$("#buId").append('<option value="5">US-Deere</option>');
	 		}	 
	 	  	
		}
		function appendInternal()
		{
			var test=$("#pCId option[value='3']").text();
	 		 if(test==""){
	 			$("#pCId").append('<option value="3">Internal</option>');
	 		}	  
	 	  	
		}	
		function addDataTableSearch(table){
			projectTable = $(table).dataTable({
				

				"bStateSave": true,
			     "sDom": '<"projecttoolbar">lfrtip',
				"bDestroy": true,
				"sPaginationType": "full_numbers", 
				"fnCookieCallback": function (sName, oData, sExpires, sPath) {
				      if(isNew=='true') {
				      	return sName + "="+JSON.stringify(oData)+"; expires=" + new Date() +"; path=" + sPath;
				    }else {
				    	return sName + "="+JSON.stringify(oData)+"; expires=" + sExpires +"; path=" + sPath;
				    }
				}
			});
			isNew='false';
			<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_HR')"> 
			$("div.projecttoolbar").html(
					'<div class="btnIcon">'+
						'<a href="#" class="blue_link" id="addNew" >'+
							'<img src="resources/images/addUser.gif" width="16" height="22" /> '+
						'Add New </a>'+
					'</div>');
			</sec:authorize>
			
			  
			
			projectTable.$('tr').dblclick( function () {
	  		    var sData = projectTable.fnGetData(this);
	  		    openMaintainance(($(this).attr('id')));
	  		});
			$('#update').click(function(){
				$("#projectForm").reset();
				displayMaintainance();
			});
			$('#addNew').on("click",function(){
				$("#projectForm").reset();
				$('#offshoreDelMgrId, #deliveryMgrId, .resClass').val(null).trigger('change');
				removeInvalidProp();
				$("#projectForm input[name=id]").val(-1);
				$("table#addPoTbl tbody").html("");
				displayMaintainance();
				$(':input','#projectForm')
				 .not(':button, :submit, :reset, :hidden')
				 .val('')
				 .removeAttr('checked')
				 .removeAttr('selected');
				//User Story #2731
				
				$("#defaultRowId").show();
				
				if(!($('#defaultActivitiesId').is(':checked'))) {
					$('#defaultActivitiesId').prop('checked', true);
				} 
				
				$('#defaultActivitiesId').click(function() {
					 if($('#defaultActivitiesId').is(':checked')){
						 $('#defaultActivitiesId').prop('checked', true);
					 }
					 else if($('#defaultActivitiesId').is(':unchecked')) {
				 
						 $('#defaultActivitiesId').prop('checked', false);
						 showMessage("if You Unchecked! You have to Add Default Activities Manually");
					    } 
					 });
					// End	User Story #2731
				$("#planProBudgId").val('');
				$("#planProBudg").html('');
			});
		}
		
		
		function addTotal(){
			total=0;
			$("table#addPoTbl tbody tr td").find("input[type=text].inpCost").each(function(){
				total += Number($(this).val());
			});
			$("#planProBudg").html(total);
		};
		function addDatePicker(){
			addPoLength=$("table#addPoTbl tbody").find("tr").length;
			for(var i =0; i<addPoLength; i++){
				 $("#issueDate"+i).datepicker({
					 changeMonth: true,
				      changeYear: true, 
				      dateFormat:"dd-M-yy",
				      
					 onClose: function( selectedDate ) {
			                $( "#validUptoDate"+i).datepicker( "option", "minDate", selectedDate );
			            }
				    });
				 $("#validUptoDate"+i).datepicker({
					 changeMonth: true, 
				      changeYear: true,
				      dateFormat:"dd-M-yy",
				       onClose: function( selectedDate ) {
			                $( "#issueDate"+i ).datepicker( "option", "maxDate", selectedDate );
			            }
				    });
			}
		}
		
		var DELAY = 700, 
		clicks = 0, 
		timer = null;
		clickable = true;
		
		
		$(document).ready(function(){
			$('#modal_data_table').DataTable({
				"bPaginate": true,
				"bFilter": true,
				"sPaginationType" : "full_numbers",
				// "aaSorting": [],
				"bInfo": true,
			//  'bStyle':'overflow: auto; display: inline-block; height: 200px; border: 1px solid #ccc;',
				'aoColumns': [ 
						{'aTargets': 0, 'width': '12%','bSortable': false}, // numeric id
						{'aTargets': 1, 'bSortable': false},	
						{'aTargets': 2, 'bSortable': false},
								
					]
				
			});
			 
			 $('#plannedProjectSize').keypress(function (event) {
				 var charCode = (event.which) ? event.which : event.keyCode
				 if ((charCode < 48 || charCode > 57)){
				 return false;
			     }
			     return true;  
		    });
			projectTable = $('#projectTableId').dataTable({
                "bProcessing": true,
                "bServerSide": true,       
                "sAjaxSource": '/rms/projectallocations/list/'+"active",
                "sPaginationType": 'full_numbers',
                "pagingType": "full_numbers",
                "bAutoWidth" : false,
                "sScrollX": "100%",
                "sScrollY": "350",
                "bScrollCollapse": true,
                "bPaginate": true,
                "bDestory": true,
                "bRetrieve": true,
                "aaSorting": [ [0,'asc']],
                "oLanguage": {
                                "sLengthMenu": 'Show <SELECT>'+ '<OPTION value=10>10</OPTION>'+ '<OPTION value=25>25</OPTION>'+ '<OPTION value=50>50</OPTION>'+ '<OPTION value=100>100</OPTION>'+ '<OPTION value=200>200</OPTION></SELECT> entries'
                },
               
         		"bSortCellsTop": true,
				    "fnDrawCallback": function() {
				                    handlePaginationButtons(projectTable, "projectTableId");
				                    },
				    "fnInitComplete": function() {
				    },
				    "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
				    	var projectName= aData[2];
				    	var projectEndDate= aData[10];
				    	// console.log('projectEndDate',projectEndDate);
				    	 if(projectEndDate && projectEndDate < today){	
								nRow.className='redColorResource';
							}
				    	 if(projectName == ""||projectName == null){
						 $('td:eq(1)', nRow).html("N.A");
						 }
				    	 var customerName = aData[3];
				    	 if(customerName == ""||customerName == null){
							 $('td:eq(2)', nRow).html("N.A");
						 }
				    	 
				    	 var projectManager = aData[4];
				    	 if(projectManager == ""|| projectManager == null){
							 $('td:eq(3)', nRow).html("N.A");
						 }
				    	 
				    	 var onsiteManager = aData[5];
				    	 if(onsiteManager == ""|| onsiteManager == null){
							 $('td:eq(4)', nRow).html("N.A");
						 } 
				    	 
				    	 var currentBgBu = aData[6];
				    	 if(currentBgBu == ""|| currentBgBu == null){
							 $('td:eq(5)', nRow).html("N.A");
						 } 
				    	 var engagementModel = aData[7];
				    	 if(engagementModel == ""|| engagementModel == null){
							 $('td:eq(6)', nRow).html("N.A");
						 } 
				    	 
				    	 var projectKickOffDate = aData[8];
				    	 if(projectKickOffDate == "" ||projectKickOffDate == null){
							 $('td:eq(7)', nRow).html("N.A");
						 }
				    	 
				    	 var plannedProjectSize = aData[9];
				    	 if(plannedProjectSize == ""|| plannedProjectSize == null || plannedProjectSize == 0){
							 $('td:eq(8)', nRow).html("N.A");
						 }
				    	 
				    	 var deliveryMgrVar = aData[14];
				    	 if(deliveryMgrVar == ""|| deliveryMgrVar == null || deliveryMgrVar == 0){
							 $('td:eq(9)', nRow).html("N.A");
						 }else
							 {
							 $('td:eq(9)', nRow).html(deliveryMgrVar);
							 }
				    	 
				    	<sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
				    	var managerReadonly = aData[12];	
				    	if(managerReadonly==false){
		                	$('td:eq(0)', nRow).html('<a href="#" onclick="openMaintainance('+aData[0]+');">'+ aData[1]+'</a>');
		                }
		                
		                else
		                {
		                	$('td:eq(0)', nRow).html('<a href="javascript:void(0)" class="copyInactive" disabled="disabled">'+ aData[1]+'</a>');
		                }
					 </sec:authorize>
					 <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
					 $('td:eq(0)', nRow).html('<a href="#" onclick="openMaintainance('+aData[0]+');">'+ aData[1]+'</a>');	
					 </sec:authorize>
			         return nRow;
			        },
			        
			
			       
				    "aoColumns": [
								{ 
										sName: "id",
										sWidth : "",
										bSortable: false,
										bVisible:false
									},
									{ 
										sName: "projectCode",
										sWidth : "",
										bSortable: false
										
									},
									{ 
										sName: "projectName",
										sWidth : "",
										bSortable: true
									},
									{ 
										sName: "customerName",
										sWidth : "",
										bSortable: true
									},
									{ 
										sName: "offshoreDelMgr",
										sWidth : "",
										bSortable: true
									},
									{ 
										sName: "onsiteDelMgr",
										sWidth : "",
										bSortable: true
									},
									{ 
										sName: "BuCode",
										sWidth : "",
										bSortable: false
									},
									
									{ 
										sName: "engagementModelId",
										sWidth : "",
										bSortable: true
									},
									 
									{ 
										sName: "projectKickOffDate",
										sWidth : "",
										bSortable: true
									},
									
									{ 
										sName: "plannedProjectSize",
										sWidth : "",
										bSortable: true,
										bVisible:true
									},
									
									{ 
										sName: "projectEndDate",
										sWidth : "",
										bSortable: true,
										bVisible:false
									},
									{ 
										sName: "deliveryManager",
										sWidth : "",
										bSortable: true,
										bVisible:true
									}
									
                      ],
                      
                      "sDom": '<"projecttoolbar">lfi<"top">rt<"bottom"<"#refresh">ip><"clear">'
                    	 
			});
			
			$("#projectTableId_filter").hide();
			$('#addNew').on("click",function(){
				$("#projectForm").reset();
				$('#offshoreDelMgrId, #deliveryMgrId, .resClass').val(null).trigger('change');
				removeInvalidProp();
				$("#projectForm input[name=id]").val(-1);
				$("table#addPoTbl tbody").html("");
				displayMaintainance();
				$(':input','#projectForm')
				 .not(':button, :submit, :reset, :hidden')
				 .val('')
				 .removeAttr('checked')
				 .removeAttr('selected');
				$(this).attr('checked', true);
				//User Story #2731
				
				$("#defaultRowId").show();
				
				if(!($('#defaultActivitiesId').is(':checked'))) {
					$('#defaultActivitiesId').prop('checked', true);
				} 
				
				$('#defaultActivitiesId').click(function() {
					 if($('#defaultActivitiesId').is(':checked')){
						 $('#defaultActivitiesId').prop('checked', true);
					 }
					 else if($('#defaultActivitiesId').is(':unchecked')) {
				
						 $('#defaultActivitiesId').prop('checked', false);
				          
						 showMessage("if You Unchecked! You have to Add Default Activities Manually");
					    } 
					 });
					// End	User Story #2731
				$("#planProBudgId").val('');
				$("#planProBudg").html('');
			});
			/*--------------------------Numeric Validation----------------------------------------------------*/
			$(".errorNumeric").hide();
			$(document).on("keydown",".numericInp",function(event){  
				$(".errorNumeric").hide();
                if(event.shiftKey)
                    event.preventDefault();                    
                if (event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9) {
                	
                } else {
                	if (event.keyCode < 95) {  
                        if (event.keyCode<48 || event.keyCode>57) {
                        	$(this).parent().parent("td").find(".errorNumeric").fadeIn("slow");
                            event.preventDefault();
                        }
                        else
                        	{
                        		$(".errorNumeric").fadeOut("fast");
                        	}
                    } else {  
                    	
                        if (event.keyCode<96 || event.keyCode>105) {
                            event.preventDefault();
                        }
                    }
                }
			});
			/* $("#endDate").datepicker({changeMonth: true,changeYear: true, dateFormat:'dd-M-yy'}).val();
			$('#endDate').bind('changeDate', onDateChange); */
			 $( function() {
				 	
				    endDatePicker=$( "#endDate" ).datepicker({
				    		autoclose:true,
						 dateFormat: "dd-M-yy" ,
						 changeMonth: true,
						 defaultDate: null,
						 setDate:null,
						 changeYear: true,			 
						 onSelect: function(selectedDate) {
							 var projectId=$("#projectId").val();
							 getRRFDetails(projectId)
						      }
						 
						});
				  } );
		
	
						
		 $(".dd_Custom_BU").change(function() {
			 
			businessUnitValidate(this.value);
			 
			}); 
          $(".dd_customer").change(function() {
			 
			 checkcust();
			 
		}); 
		$(".dd_project").change(function() {
			 
			 checkcust();
			
		      projectCategoryValidate(this.value); 
		      
		});
		
		$("#mailconfg").fancybox(
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
					href : '${mailConfiguration/1}',
					'width' : '100%',
					reload : false,
					/*'height':400,*/
					beforeShow : function() {
						var thisH = this.height - 35;
						$(".fancybox-iframe").contents().find('html')
								.find(".midSection").css('height',
										thisH);
					}

				});
			
    		/*----------po table------------*/
			$("#addPo").on("click",function(){				
				$(".poTbl table tbody:last").append($.render.poTableRowsTempl());
				
				var x = 2;
				if (x % 2 == 0)
				{
					$(".poTbl table tbody tr:even").addClass('even');
				}
				var len=$("table#addPoTbl tbody").find("tr").length -1;
				 $("#issueDate"+len).datepicker({
					  changeMonth: true, 
				       changeYear: true,
				      dateFormat:"dd-M-yy",
					 onClose: function( selectedDate ) {
			                $( "#validUptoDate"+len).datepicker( "option", "minDate", selectedDate );
			            }
				    });
				 $("#validUptoDate1"+len).datepicker({
					   changeMonth: true, 
				    	changeYear: true,
				    	dateFormat:"dd-M-yy",
				    	 onClose: function( selectedDate ) {
			                $( "#issueDate"+len ).datepicker( "option", "maxDate", selectedDate );
			            }
				    });
				addDatePicker();
				addTotal();
			});
    		
			$("#addPoTbl").find("input[type=text].inpCost").on("blur", function(){
				addTotal();
			});
			
		$(".tab_div").hide();
		$('ul.tabs a.listTab').click(function () {
			$('#MaintenanceTabInactive').off('click');
			$(".tab_div").hide().filter(this.hash).show();
			$("ul.tabs a.listTab").removeClass('active');
			$('a[href$="tab2"]').addClass('MaintenanceTab');
			$(this).addClass('active');
			return false;
		}).filter(':first').click();
		
		//Added for new date format for projectkickoff date:Projectscreen readonly details: RMS team/
		$('#projectKickOff').datepicker({changeMonth: true,changeYear: true, dateFormat:'dd-M-yy'}).val();
		
	
		
		
	    
		
		$('a[href$="tab2"]').click(function(){
			$("#projectForm").reset();
			appendInternal();
			appendUsDeere();
			$("table#addPoTbl tbody").html("");
			$("#planProBudg").html("");
		});

		
		
		$('a[href$="tab1"]').click(function(){
  			$("div.errorNumericLast").hide();
  			$(".required").removeClass("brdrRed");
  			$("a").removeClass("brdrRed");
  			
		});
		



function getRRFDetails(projectId) {

	$.ajax({
       
        url: "${pageContext.request.contextPath}/projects/rrfdetails/"+projectId,
        dataType: 'text json',
        headers:{
              'Accept': 'application/json'
        },   
         success: function(response){
                if(null!=response){   
                  	var prepareData="";
                    var mymodal = $('#rrfModal');
                    var rrfData= JSON.parse(response.rrfDetails);
                	if (rrfData.length > 0) {
        				$('#modal_data_table').dataTable().fnClearTable();
        				var i = 1;
        				$.each(rrfData,function(key,val) {
        					var dateValue=null;
        					mymodal.modal('show' );
        					$('#modal_data_table').dataTable().fnAddData(
        						[
        							
        							getSerialNo(i++),
        							prepareRequirementId(val.id, val.requirementId),
        							val.status
        						
        						]
        					);
        					
        				 });
        			  }else{
        					//mymodal.modal('show' )
        					//$('#modal_data_table').dataTable().fnClearTable();  
        			  }
                                  
         }else{   
            showError("Something happend wrong!! ");
         }
            
        },
       
 }); 
}


		
function businessUnitValidate(businessUnit){
			if(businessUnit == "5"){
				 	 $("#pCId option").each(function(){
				 		 if("3"==$(this).val()){
			 			    $(this).remove();
			  			 }	  
			 	  });
			 	}
			 	else{
			 		appendInternal();
			 	  }	
	}
	
function handlePaginationButtons(dataTableObj, dataTableId){
    if (dataTableObj != null) {
                    var oSettings = dataTableObj.fnSettings();
                    if (oSettings._iDisplayLength >= oSettings.fnRecordsDisplay()) {
                                    $('#'+dataTableId+'_paginate').fadeTo(0,.1);
                                    $('#'+dataTableId+'_first').css('cursor','default');
                                    $('#'+dataTableId+'_last').css('cursor','default');
                                    $('#'+dataTableId+'_next').css('cursor','default');
                                    $('#'+dataTableId+'_previous').css('cursor','default');
                                    $('.paginate_active').css('cursor','default');
                    } else {
                                    $('#'+dataTableId+'_paginate').fadeTo(0,1);
                                    $('#'+dataTableId+'_first').css('cursor','pointer');
                                    $('#'+dataTableId+'_last').css('cursor','pointer');
                                    $('#'+dataTableId+'_next').css('cursor','pointer');
                                    $('.paginate_active').css('cursor','pointer');
                    }
    }
}
		
 function projectCategoryValidate(projectCategory){
	 if(projectCategory == "3"){
	 	 $("#buId option").each(function()
	 	  {
	 		  if("5"==$(this).val()) 
	 			 {
	 				$(this).remove();
	 			  }	  
	 	  });
	 	}
	 	else
	 	{
	 		appendUsDeere();
	 	}	
	}


function validateSelect2(select2Array){
var varcount= 0;
$(select2Array).each(function(index) {
var selectDdVal = $(this).parents('td').find('select').val();
if(!selectDdVal){
 $(this).addClass("brdrRed");
 varcount++;
}else{
 $(this).removeClass("brdrRed");
}
});
if(varcount>0) {
return false;
} else { 
return true;
}
}

		function validateCombo(comboBoxArray){
  			var varcount = 0;
  			$(comboBoxArray).each(function(index) {
  				var selectDdVal =$(this).val();
  				if(selectDdVal == ''){
					$(this).addClass("brdrRed").next("a").addClass("brdrRed");
					varcount++;
				}else{
					$(this).removeClass("brdrRed").next("a").removeClass("brdrRed");
				}
  			});
  			if(varcount>0)return false;
  			else return true;
			
  			
  		}

		$("#ok").click(function(){
			 $(this).parent().parent().hide();
			 $("#bgDiv").remove();
		 });
		 var comboBoxBlur = $("select.required").parent("td").find("span.ui-combobox input");
		 
			$(comboBoxBlur).each(function(){
				$(this).addClass("required");
			});
			$(comboBoxBlur).blur(function(){
				if($(this).hasClass("required")){
					if($(this).val()==''){
						$(this).next("a").addClass("brdrRed");
					}else{
						$(this).next("a").removeClass("brdrRed");
						$(this).removeClass("brdrRed");
					}
				}
			});
			
	
			
			
			$('#projectActive').on('change', function() { 
				if(this.value=='all')
	   				{
					var oSettings = projectTable.fnSettings();
	   				
	   				    oSettings.sAjaxSource  = "/rms/projectallocations/list/all";
	   				 	projectTable.fnClearTable();
	   				 	projectTable.fnDraw();
	   				   
	   				 
	   				}
	   				else
	   				{
	   				var oSettings = projectTable.fnSettings();
	   				    oSettings.sAjaxSource  = "/rms/projectallocations/list/active";
	   				 	projectTable.fnClearTable();
	   				 	projectTable.fnDraw();
	   				}
	  	  });
			
	$( "#save" ).click(function( event ) {
		
		 select2Arr = $("select.required").parent("td").find("span.select2-selection");
			
		 if (validateCombo(comboBoxBlur)) {
		     validationFlag = true;
		 } else {
		     validationFlag = false;
		 }
		 
		 if (validateSelect2(select2Arr)) {
		     validationFlag = true;
		 } else {
		     validationFlag = false;
		 }
		 
		if(validationFlag){
			startProgress();	
		}
		
		addTotal();

		if(idArray.length > 0){
		for(var i=0; i<idArray.length; i++){
		var id = idArray[i];
			$.deleteJson("projects/"+id, function(data){
				  addTotal();						
			  }, 'json');
		}	
		}
		
		var poNumberArray = new Array();
		var rowCount = $('#addPoTbl >tbody >tr ').length;
		var temp=0;
		//added for PO issue
		var validPoname=0;
		var validPonum=0;
		var validPocost=0;
		//added for PO issue
		var inpSelect = $('#addPoTbl >tbody >tr ').find("td:nth-child(1) input");
		var inpPoName = $('#addPoTbl >tbody >tr ').find("td:nth-child(2) input");
		var inpPoCost = $('#addPoTbl >tbody >tr ').find("td:nth-child(3) input");
		$(inpSelect).each(function(){
			var tdInpVal = $(this).val();
			 if (!tdInpVal.trim()){ //added for PO issue
				 validPonum++;
					validationFlag = false;
					$(this).addClass("brdrRed");
				}else{ //added for PO issue
					$(this).removeClass("brdrRed");
				}
			poNumberArray.push(tdInpVal);
		});
		//added for PO issue starts
		$(inpPoName).each(function(){
			var tdinpPoNameVal = $(this).val();
			 if (!tdinpPoNameVal.trim()){
				 validPoname ++;
					validationFlag = false;
					$(this).addClass("brdrRed");
				}else{
					$(this).removeClass("brdrRed");
				}
		});
		
		$(inpPoCost).each(function(){
			var tdinpPoCostVal = $(this).val();
			 if (!tdinpPoCostVal.trim()){
				 validPocost ++;
					validationFlag = false;
					$(this).addClass("brdrRed");
				}else{
					$(this).removeClass("brdrRed");
				}
		});
		//added for PO issue ends
		
		//Below code is uncommented for duplicate project validation
		 event.preventDefault();
		$("#projectForm").validVal({
			customValidations: {
			"server-validation": function(val) {
				var projectName="";
				var projectCode="";
				var projectResult = true;
				projectName=$.trim($("#projectForm input[name=projectName]").val());
				projectCode=$.trim($("#projectCode").val());
					$.ajax({
						async:false,
						url: "projects/validate",
						data: "projectName=" + projectName + "&projectCode=" + projectCode,
						success: function( data ) {
							if ( data.projectResult != true ) {
								projectResult = false;
							} 
						}
					});
					if(projectResult==false){
						$(this).next("div.errorNumericLast").show();
					}else{
						$(this).next("div.errorNumericLast").hide();
						return projectResult;
					}
					 return projectResult;
			}
		}
	}); 
		
		var pk = $("#projectForm input[name=id]").val();
		var form_data = $( "#projectForm" ).triggerHandler( "submitForm" );
	    addPoLength=$("table#addPoTbl tbody").find("tr").length;
	    var errorMsg="";
	    var flag = true;
		var projectKickOffDate = document.getElementById("projectKickOff").value;
		var projectEndDate = document.getElementById("endDate").value;
		var plannedprojsize = document.getElementById("plannedProjSize").value;
		if(plannedprojsize != "" && !plannedprojsize.match(/^[-+]?[0-9]+$/)) {
			validationFlag = false;
			errorMsg =  errorMsg+"\u2022 Please enter only numeric characters in Planned Project Size<br /> ";			
	   	}
	  	if(!(validateDates(projectKickOffDate, projectEndDate))) {
	     	validationFlag = false;
			errorMsg =  errorMsg+"\u2022 End Date should be greater than Project Kick off Date<br /> ";
	   	}
	  	if(rowCount>1){
			for(var count=0 ;count<poNumberArray.length ; count++ ){
				for(var count1=count+1;count1<poNumberArray.length ; count1++ ){
					if(poNumberArray[count] && poNumberArray[count1]){
						if(poNumberArray[count] == poNumberArray[count1]){
						 	temp++;
					    }
					 }
				 }
			 }
			if(temp > 0){				
				validationFlag = false;
				errorMsg =  errorMsg+"\u2022 Duplicate entries of PO Number is not Allowed<br /> ";
			}
		}  
	  //added for PO issue starts
	  	if(validPoname>0){
			errorMsg = errorMsg + "\u2022 PO / Invoicing Details should not be blank ! <br />";
		}
	  	if(validPonum>0){
			errorMsg = errorMsg + "\u2022 PO number should not be blank ! <br />";
		}
	  	if(validPocost>0){
			errorMsg = errorMsg + "\u2022 PO Cost should not be blank ! <br />";
		}
	  //added for PO issue ends
		for(var i =0; i<addPoLength; i++){
			var issueDate =	$("#issueDate"+i).val();
			var validUptoDate =	$("#validUptoDate"+i).val();
			if(!(validateDates(issueDate, validUptoDate))) {
				validationFlag = false;
				errorMsg = errorMsg+"\u2022 ValidUpto Date should be greater than Issue Date<br /> ";
				//$("#validUptoDate"+i).val("");
			}

         //added for date validation defect:RMS TEAM//
			  if(!(validateDates(issueDate, projectEndDate))){
					validationFlag = false;
					errorMsg = errorMsg+"\u2022Issue Date should be less than ProjectEndDate<br /> ";


					}

			  if(!(validateDates( validUptoDate, projectEndDate))){
					validationFlag = false;
					errorMsg = errorMsg+"\u2022 ValidUpto Date should be less than or equal to ProjectEndDate<br />";


					}
			  //added for date validation defect:RMS TEAM//
		}
		 if (document.getElementById("engagementModelId.id").value=="-1"){

				validationFlag = false;
				errorMsg = errorMsg + "\u2022 Engagement Model should not be blank ! <br />";
			}
		 //added for PO validate end 
		 if (document.getElementById("projectMethodologyId.id").value=="-1"){

				validationFlag = false;
				errorMsg = errorMsg + "\u2022 Development Methodology should not be blank ! <br />";
			}
		 
		 
		 if (document.getElementById("customerNameSelect").value=="-1"){

				validationFlag = false;
				errorMsg = errorMsg + "\u2022 Customer Name should not be blank ! <br />";
			}
		 
		 if (document.getElementById("offshoreDelMgrId").value=="-1"){

				validationFlag = false;
				errorMsg = errorMsg + "\u2022 Manager should not be blank ! <br />";
  		  }
		//Validation message for Delivery Manager
		 if (document.getElementById("deliveryMgrId").value=="-1"){

				validationFlag = false;
				errorMsg = errorMsg + "\u2022 Delivery Manager should not be blank ! <br />";
		  }
		
		 if (document.getElementById("projectTrackingCurrencyId.id").value=="-1"){

				validationFlag = false;
				errorMsg = errorMsg + "\u2022 Project Tracking Currency should not be blank ! <br />";
			}
	
		 
		 if (document.getElementById("invoiceById.id").value=="-1"){

				validationFlag = false;
				errorMsg = errorMsg + "\u2022 Invoice By should not be blank ! <br />";
		}
		 
		var flag1=false;
		var temp=document.getElementById("engagementModelId.id").value;
		var temp2=document.getElementById("engagementModelId").value;
		if(temp!=temp2)
		 {
			 flag1=true;
		 }
		 
		if(!validationFlag) {
			stopProgress();
			if(errorMsg.length > 0) 							
				showError(errorMsg);							
			return;
		}	
		else  if(flag1==true && pk != null && pk > 0){
			 
			stopProgress();
			var text = "Do You want to change Enagagement Model ?";
	   	    noty({
		 	      text: text,
		 	      type: 'confirm',
		 	      dismissQueue: false,
		 	      layout: 'center',
		 	      theme: 'defaultTheme',
		 	      buttons: [
		 	        {addClass: 'btn btn-primary', text: 'Ok', onClick: function($noty) {
		 	        	startProgress();
		 	            $.noty.closeAll();
		 	        	
		 					 $.putJson('projects', $("#projectForm").toDeepJson() , function(data){
		 						
		 				          // added for kickOffDate issue  - start
		 				          if( data.status == "false")
		 				           {
		 				            stopProgress();
		 				           errorMsg = " Project Kick off date sholud be less than Allocation Start date !" ;      
		 				           showError(errorMsg);       
		 				           return;
		 				           }
		 				           else{
		 				          //refreshGrid();
		 				          stopProgress();
		 				            showSuccess(successMsg);
		 				            window.location.reload();
		 				          }} , 'json');
		 				        // added for kickOffDate issue  - end
		        	          $('.toasterBgDiv').remove();
		 	    
		 	        }
		 	        },
		 	        {addClass: 'btn btn-danger', text: 'Cancel', onClick: function($noty) {
		 	        	  $.noty.closeAll();
		 	        	 $('.toasterBgDiv').remove();
		 	        	
		 	         }
		 	        }
		 	      ],
		 	   closeWith:['Button']
		 	    });
			
			}else{
				 startProgress();
				 var successMsg ="Project details have been saved successfully";
				var succ="here";
				var suc="not here";
				 $("#planProBudgId").val(total);
				 
				if( pk != null && pk > 0){
					 $.putJson('projects', $("#projectForm").toDeepJson() , function(data){
						// added by Neha for kickOffDate issue  - start
						 if( data.status == "false")
							 {
							 	stopProgress();
								errorMsg = " Project Kick off date should be less than Allocation Start date !"	;						
								showError(errorMsg);							
								return;
							 }
							 else{
							refreshGrid();
							stopProgress();
				  			showSuccess(successMsg);
				  			window.location.reload();
						 }} , 'json');
						 } // added by Neha for kickOffDate issue  - end
				 else 
				 {
					$("#projectForm input[name=id]").val("");
					 $.postJson('projects',$("#projectForm").toDeepJson() , function(data){
						 isNew='true';
						 refreshGrid();
						 stopProgress();
						 showSuccess(successMsg);
						 window.location.reload();
					}, 'json');
				}
			} 
	   	
	 });
	
			
	
	
		});//end of document.ready function
		
		
		
		function openMaintainance(id){
			
			startProgress();
			getProjectById(id);
			displayMaintainance();
			stopProgress();	
			
		}
		function removeInvalidProp() {
		    $('.select2-selection').removeClass('brdrRed');  
		   		    
		}
		
		function showProject(data){
			// User Story #2731
			$("#defaultRowId").hide();
			//End User Story #2731
			$("#projectForm").populate(data, {debug:false, resetForm:true});
			removeInvalidProp();
			$("#offshoreDelMgrId").append("<option selected='selected' value="+data.offshoreDelMgr.employeeId+">"+ data.offshoreDelMgr.employeeName+"("+data.offshoreDelMgr.yashEmpId+")</option>");
			$("#deliveryMgrId").append("<option selected='selected' value="+data.deliveryMgr.employeeId+">"+ data.deliveryMgr.employeeName+"("+data.deliveryMgr.yashEmpId+")</option>");
			$('#traineeProject').prop('checked', data.traineeProject);
			$(".poTbl table tbody").find("tr").remove();
			$(".poTbl table tbody:last").append($.render.poTableRowsWithValuesTempl(data.projectPoes));
			if(data.customerGroup.customerGroupName != null && data.customerGroup.customerGroupName != "") {
				$("#customerGroupDD").show();
				$("#customerGroupText").show();	
				var customerId=data.customerNameId.id;
				var customerGroup=data.customerGroup.customerGroupName;
					$.ajax({
		    		type: 'GET',
		    		dataType: 'text',
		            url: '/rms/requests/getGroupBasedOnCustomerId/'+customerId,
		            contentType:"application/json; charset=utf-8",
		            cache: false,
		            success: function(data) { 
		            	stopProgress();
		            	$("#customerGroupSelect").empty();
		            	var response = JSON.parse(data);   
		            		for( var i = 0 ; i< response.length ; i++){
		                		var index = response[i];                		
		                	    optionForCustomerGroup = "<option value= '" + index[0] + "'>" + index[2] + "</option>";
		                	    $("#customerGroupSelect").append(optionForCustomerGroup);
		                	}
		    				var dd = document.getElementById('customerGroupSelect');
		    				for (var i = 0; i < dd.options.length; i++) {
		    				    if (dd.options[i].text === customerGroup) {
		    				        dd.selectedIndex = i;
		    				        break;
		    				    }
		    				}
		    				$("#customerGroupSelect").combobox('destroy').combobox();
			     	},
			     	error: function(error){	     		
			     		stopProgress();
			     		showError("Something went wrong!!");
			     		//window.location.reload();
			     	}
				});			
			}
			addDatePicker();
			addTotal();			
		}
		
		
		$("#projectTableId a.copyInactive,#projectTableId td").on("click", function(event){
	        
			clicks++;  //count clicks

	        if(clicks === 1) {

	            timer = setTimeout(function() {
	            	
	            	event.stopPropagation();
	    			clickable = true;
	                clicks = 0;  //after action performed, reset counter

	            }, DELAY);

	        } else {

	            clearTimeout(timer);  //prevent single-click action
	            
	            event.stopPropagation();
				clickable = false;
				
	            setTimeout(function(){clickable = true;  }, 800);

	            clicks = 0;  //after action performed, reset counter
	        }

	    }).on("dblclick", function(e){
	    	event.stopPropagation();  //cancel system double-click event
	    	
	    	clickable = true;
	    });

			

		
		function getProjectById(id){
			$.getJSON("projects/"+id,{}, showProject);
	    }
		
		function displayMaintainance(){
			if(clickable != true){
		}
		else{
			$("ul.tabs a.listTab").removeClass('active');
			$(".tab_div").hide().next("#tab2".hash).show();
			$('a[href$="tab2"]').removeClass('MaintenanceTab');
			$('a[href$="tab2"]').addClass('active');
		}
			//$('.readOnlyinput').removeAttr("disabled");
			<sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER')">

				 //hide a div after 3 seconds
				setTimeout(function(){ 
					$('.readOnlyinput').attr("disabled","disabled"); 
					$('.removePo').click(function () {return false;});
				 
				}, 1000);

	
			$('form#projectForm input[type="text"]  ,textarea').attr("readonly","readonly");
			$('form#projectForm input[type=checkbox]').attr("disabled","disabled");
			$('form#projectForm input:radio').attr("disabled","disabled");
			var dateFieldIds = ["#endDate" ,"#projectKickOff"];
			$.each(dateFieldIds,function(index,fieldId) {
				$(fieldId).datepicker("destroy");
			});
			 
			</sec:authorize>
			appendInternal();
			appendUsDeere();
		}
		
		// End To fix #189	
		
		function saveProject(){
		}
		
		function refreshGrid(){
			if(projectTable != null) projectTable.fnClearTable();
				$.getJSON("projects/list", function(data) {
					$("#projectTableId > tbody:last").append(
					$("#projectTableRows").render(data)
				);
				addDataTableSearch($("#projectTableId"));
				$(".tab_div").hide();
				// code added to clear the search filter
				
				projectTable.fnFilter('');
				
				//.............//
				$('ul.tabs a.listTab').click(function () {
					$(".tab_div").hide().filter(this.hash).show();
					$("ul.tabs a.listTab").removeClass('active');
					$(this).addClass('active');
					return false;
				}).filter(':first').click();
			});
			
		}
		
		function checkcust(){
			var projectCat= document.getElementById('pCId').options[document.getElementById('pCId').selectedIndex].value;
			var customerName= document.getElementById('customerNameSelect').options[document.getElementById('customerNameSelect').selectedIndex].value ;
			
			var select=document.getElementById('customerNameSelect');
			
			for (i=0;i<select.length;i++) {
				if(projectCat==1){
				   if (select.options[i].value==7) {
					   select.options[i].text='';
				   }
				}else{
					select.options[7].text="Yash Internal";
				}
			} 
			
		 }
		
		function validateDates(fromDate, toDate) {
			
		if(fromDate != ""){
			 var dateObjfromDate  = Date.parse(fromDate);	
		}
		if(toDate != ""){
			var dateObjToDate  = Date.parse(toDate);				
		}
			var SDate = dateObjfromDate;
			var EDate = dateObjToDate;
			var endDate = new Date(EDate);endDate.setHours(0, 0, 0, 0);
			var startDate = new Date(SDate);startDate.setHours(0, 0, 0, 0); 
			if(SDate != '' && EDate != '' && startDate>endDate) 
			return false;	   	
			return true;
		}
		
		function showError(msg) {
			toastr.error(msg, "Error")
		}
		
		 var today = new Date().toLocaleDateString('en-GB', {
			    day : 'numeric',
			    month : 'short',
			    year : 'numeric'
			}).split(' ').join('-');
		
</script>
 
 


