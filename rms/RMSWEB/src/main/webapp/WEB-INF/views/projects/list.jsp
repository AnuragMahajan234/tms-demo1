<%@page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<style>

.projecttoolbar {border: 0px solid red; width: 50%; float:left;}
#projectTableId_info {}
</style>

<spring:url value="/resources/styles/style.css?ver=${app_js_ver}" var="style_css1"/>
	<link href="${style_css1}" rel="stylesheet" type="text/css"></link>
   <spring:url value="/resources/js-framework/validate/jquery.validVal-4.0.3-packed.js?ver=${app_js_ver}" var="jquery_validVal"/> 
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
			            <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')">
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
			                <%--   <td align="center" valign="middle">
								{{if plannedProjCost}}
									{{>plannedProjCost}}
								  {{else}}
									  N.A.
								{{/if}}
							 </td>--%>

 

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
<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')">
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
<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')">
							<td width="5%" align="left"><a href="javaScript:void(0)"><img width="16" height="16" class="removePo" src="resources/images/remove.png" class="readOnlyinput" ></a></td>
</sec:authorize>
	                	</tr>

</script>

<script>

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
<div class="content-wrapper">
	<!--right section-->
	<div class="botMargin">
		<h1>Projects</h1>
	</div>
	<div class="tab_seaction">
		<ul class='tabs'>
			<li><a href='#tab1'  class="listTab" >List</a></li>
			<li><a class="MaintenanceTab listTab" id="MaintenanceTabInactive" href='#tab2'>Maintenance</a></li>
		</ul>
		<div id='tab1' class="tab_div">
			<div class="tbl">
				<table class="dataTable dataTbl" id="projectTableId" width="100%">
					<thead>
						<tr>
							<th width="7%" align="left" valign="middle">Project ID</th>
							<th width="8%" align="left" valign="middle">Project Name</th>
							<th width="10%" align="left" valign="middle">Project BU</th>
							<th width="8%" align="left" valign="middle">Customer Name</th>
							<!-- <th width="6%" align="center" valign="middle">Customer Type</th> -->
							<th width="10%" align="left" valign="middle">Manager</th>
							<th width="10%" align="left" valign="middle">Customer
								Relationship Manager</th>
							<th width="10%" align="left" valign="middle">Engagement
								Mode</th>
							<th width="10%" align="left" valign="middle">Project Kick
								off Date</th>
							<th width="10%" align="left" valign="middle">Planned
								Project Size</th>
							
							<!-- <th width="10%" align="center" valign="middle">Planned
								Project Cost</th> -->
						</tr>
					</thead>
					<tbody>

						<c:forEach var="project" items="${projects}">
							<tr id="${project.id}">
								<td align="left" valign="middle"><a href="#"
									onclick="openMaintainance(${project.id});">
										${project.projectCode}</a></td>
								<td align="left" valign="middle"><c:if
										test="${empty project.projectName}">N.A.</c:if> <c:if
										test="${not empty project.projectName}">${project.projectName}</c:if>
								</td>
								 <td align="left" valign="middle"><c:if
										test="${empty project.orgHierarchy.parentId.name}">N.A.</c:if>
									<c:if
										test="${not empty project.orgHierarchy}">${project.orgHierarchy.parentId.name}-{project.orgHierarchy.name}</c:if>
								</td>
								<td align="left" valign="middle"><c:if
										test="${empty project.customerNameId}">N.A.</c:if> <c:if
										test="${not empty project.customerNameId.customerName}">${project.customerNameId.customerName}</c:if>
								</td>
								<%-- <td align="center" valign="middle"><c:if
										test="${project.deere}">Deere</c:if> <c:if
										test="${not project.deere}">Non-Deere</c:if></td> --%>
								<td align="left" valign="middle"><c:if
										test="${empty project.offshoreDelMgr}">N.A.</c:if> <c:if
										test="${not empty project.offshoreDelMgr}">${project.offshoreDelMgr.employeeName}</c:if>
								</td>
								<td align="left" valign="middle"><c:if
										test="${empty project.onsiteDelMgr}">N.A.</c:if> <c:if
										test="${not empty project.onsiteDelMgr}">${project.onsiteDelMgr}</c:if>
								</td>
								<td align="left" valign="middle"><c:if
										test="${empty project.engagementModelId}">N.A.</c:if> <c:if
										test="${not empty project.engagementModelId.engagementModelName}">${project.engagementModelId.engagementModelName}</c:if>
								</td>
								<td align="left" valign="middle"><c:if
										test="${empty project.projectKickOff}">N.A.</c:if> <c:if
										test="${not empty project.projectKickOff}">
										<fmt:formatDate value="${project.projectKickOff}" type="date"
											pattern="<%=Constants.DATE_PATTERN %>" />
									</c:if></td>
								<td align="left" valign="middle"><c:if
										test="${empty project.plannedProjSize}">N.A.</c:if> <c:if
										test="${not empty project.plannedProjSize}">${project.plannedProjSize}</c:if>
								</td>
								
								<%-- <td align="center" valign="middle"><c:if
										test="${empty project.plannedProjCost}">N.A.</c:if> <c:if
										test="${not empty project.plannedProjCost}">${project.plannedProjCost}</c:if>
								</td>
								 --%>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="clear"></div>
		</div> 
		<div id='tab2' class="tab_div">
			<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_DEL_MANAGER')">
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
					<table>
					
					<tr id="defaultRowId"><td><input type="checkbox" name="defaultActivitiesId" id="defaultActivitiesId" style="width:20px;">Set Default Activities</td></tr>
				
						<tr>
							<td width="15%" align="left">Project ID :</td>
							<td width="18%" align="left"><input name="projectCode"
								id="projectCode" readonly="readonly"
								value="${project.projectCode} " /><input type="hidden" value=""
								name="id" /></td>
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
							<td width="18%" align="left">Project Category :</td>
							<td width="20%" align="left"><select id="pCId"
								name="projectCategoryId.id" class="comboselect dd_project">
									<option value="-1">SELECT</option>
									<c:forEach var="projectcategory" items="${projectcategorys}">
										<option value="${projectcategory.id}">${projectcategory.category}</option>
									</c:forEach>
							</select></td>
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
							<td><select id="customerNameId.id" name="customerNameId.id"
								class="required comboselect dd_customer">
									<option value="-1">SELECT</option>
									<c:forEach var="customer" items="${customers}">
										<option value="${customer.id}">${customer.customerName}</option>
									</c:forEach>
							</select></td>
							
							<td width="18%" align="left">Parent Project / Team :</td>
							<td width="20%" align="left"><select id="team.id"
								name="team.id" class="comboselect">
									<option value="-1">SELECT</option>
									<c:forEach var="team" items="${teams}">
										<option value="${team.id}">${team.teamName}</option>
									</c:forEach>
							</select></td>
							
							<%--  <td align="right">Customer :<span class="astric">*</span></td>
							<td align="left">
								<div class="radio_btn">
									<span class="radio_b"> <c:choose><c:when test="${project.deere}"><input type="radio" value="true" checked="checked"
										name="deere" id="deere" class="required"></c:when>
										<c:otherwise><input type="radio" value="true" 
										name="deere" id="deere" class="required"></c:otherwise></c:choose>
									</span> <span class="radio_lbl">Deere </span>
								</div>
								<div class="radio_btn">
									<span class="radio_b"> <c:choose><c:when test="${not project.deere}"><input type="radio" value="false" checked="checked"
										name="deere" id="nondeere" class="required"></c:when>
										<c:otherwise><input type="radio" value="false"
										name="deere" id="nondeere" class="required"></c:otherwise>
										</c:choose>
									</span> <span class="radio_lbl">Non Deere </span>
								</div>
							</td> --%>
						</tr>
						<tr>
							<td align="left">Customer Contacts :</td>
							<td align="left"><input type="text" value=""
								name="customerContacts" class="string" /></td>
							<td align="left">Manager :<span class="astric">*</span></td>
							<td align="left"><select name="offshoreDelMgr.employeeId"
								id="offshoreDelMgr.employeeId"
								class="dd_cmn comboselect required">

									<option value="-1">SELECT</option>
									<c:forEach var="resource" items="${resources}">
										<option value="${resource.employeeId}">${resource.employeeName}</option>
									</c:forEach>
							</select></td>
							<td align="left">Customer Relationship Manager :</td>
							<td align="left"><input type="text" value=""
								name="onsiteDelMgr" maxlength="50" class="string" /></td>
						</tr>
						<tr>
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
							<select id="engagementModelId"
								name="engagementModelId.id"
								
								 style="display: none;" >
									<option value="-1">SELECT</option>
									<c:forEach var="engagementmodel" items="${engagementmodels}">
										<option value="${engagementmodel.id}">${engagementmodel.engagementModelName}</option>
									</c:forEach>
							</select></td>
							
							<td align="left">Project Kick off date :</td>
							<td align="left"><input type="text" value=""
								name="projectKickOff" id="projectKickOff" readonly="readonly" /></td>
							<td align="left">End Date :</td>
							<td><input type="text" id="endDate" name="projectEndDate"
								readonly="readonly" /></td>

						</tr>
						<tr>
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
							<td align="left">Planned Project Budget :</td>
							<td align="left"><label id="planProBudg"></label> <!-- <input type="text" value="" name="plannedProjCost" readonly="readonly" id="planProBudg" /> -->
								<input type="hidden" id="planProBudgId" name="plannedProjCost"
								value="" /></td>
						</tr>
						<tr>
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
							<td width="18%" align="left">Invoiced by : <span
								class="astric">*</span></td>
							<td width="20%" align="left"><select id="invoiceById.id"
								name="invoiceById.id" class="required comboselect">
									<option value="-1">SELECT</option>
									<c:forEach var="invoiceBy" items="${invoicesBy}">
										<option value="${invoiceBy.id}">${invoiceBy.name}</option>
									</c:forEach>
							</select></td>
						</tr>
						<%-- <tr>
						<td width="18%" align="left">Parent Project / Team :</td>
							<td width="20%" align="left"><select id="team.id"
								name="team.id" class="comboselect">
									<option value="-1">SELECT</option>
									<c:forEach var="team" items="${teams}">
										<option value="${team.id}">${team.teamName}</option>
									</c:forEach>
							</select></td>
						</tr> --%>
					</table><%-- <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
					<div><a href="javaScript:void(0)" id="mailconfg" class="blue_link fr"><img
								height="22" width="16" src="resources/images/addUser.gif">Mail Configuration
								 </a></div>	</sec:authorize> --%>
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
<!--START: Alert: Added by Pratyoosh Tripathi -->			
				<div class="notification-bar">
<!-- <div class="close closeIconPosition">close</div> -->
<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
  <!-- <div class="notification-text">Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.</div> -->
  <sec:authorize
				access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')">
				<div class="notification-text">You can able to view or add a Project into RMS clicking on "Add new link".</div>
				
						
	</sec:authorize>
  <sec:authorize
				access="hasAnyRole('ROLE_DEL_MANAGER')">
				<div class="notification-text">You can only view the details of your projects clicking on its Project Id but unable to add a new.</div>			
	</sec:authorize>
  
</div>
    
   <style>
   .notification-bar{
       width: 400px;
       background: rgba(30, 159, 180,.8);
    padding: 10px;
    position: fixed;
    bottom: 30px;
    right: 25px;
   
   }
   
   .closeIconPosition{    position: absolute;
    top: -10px;
    right: -20px;}
    
    .toast-close {
    float: right;
    display: block;
    width: 13px;
    height: 13px;
    padding: 0px 4px;
    border-radius: 3px;
    background: #000 url(/rms/resources/images/cross_icon_white.png) no-repeat center !important;
    z-index: 1000;
    position: absolute;
    top: -5px;
    right: -5px;
    opacity: .7 !important;
}

.notification-text{    font-size: 14px;
    color: #fff;
    text-align: justify;}
    
   </style>
    <script type="text/javascript">
$( document ).ready(function() {
	
	$('[data-toggle="tooltip"]').tooltip();
	
	setInterval(function(){ $('[data-toggle="tooltip"]').fadeToggle(500); }, 1000);
	
	<%
		Boolean flag=false;
		flag=(Boolean)session.getAttribute("notificationbarflag");
	%>
	
	
$('.close').click(function(){
    $('.notification-bar').hide();
   });
});
</script>

<!--END: Alert: Added by Pratyoosh Tripathi -->

<script>
var idArray = new Array();
var status = "";
$('.poTbl').on("click", ".removePo", function(){
/* $('.removePo').on("click", function(){	 */
	 				
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
	 		//alert(test);
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
			//	"sDom": '<"projecttoolbar">lfrtip<"top">rt<"bottom"ip<"clear">',
			     "sDom": '<"projecttoolbar">lfrtip',
				"bDestroy": true,
				"sPaginationType": "full_numbers", 
				"fnCookieCallback": function (sName, oData, sExpires, sPath) {
				      // Customise oData or sName or whatever else here
				      if(isNew=='true') {
				      	return sName + "="+JSON.stringify(oData)+"; expires=" + new Date() +"; path=" + sPath;
				    }else {
				    	return sName + "="+JSON.stringify(oData)+"; expires=" + sExpires +"; path=" + sPath;
				    }
				}
			});
			isNew='false';
			<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')"> 
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
				         //alert("IF You Unchecked! You have to Add Default Activities Manually"); 
						 showMessage("if You Unchecked! You have to Add Default Activities Manually");
					    } 
					 });
					// End	User Story #2731
				$("#planProBudgId").val('');
				$("#planProBudg").html('');
			});
		}
		
		function initTable()
		{
			
			$('#projectTableId').dataTable({
				"bStateSave": true,
				"sPaginationType": "full_numbers",
				"sDom": '<"projecttoolbar">lfrtip<"top">rt<"bottom"ip<"clear">',
				"bDestroy": true
			});
			<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')"> 
			$("div.projecttoolbar").html(
					'<div class="btnIcon projectBtnIcon">'+
						'<span class="addNewContainer"><a href="#" class="blue_link" id="addNew" >'+
							'<img src="resources/images/addUser.gif" width="16" height="22" /> '+

						'Add New </a></span>'+'&nbsp;&nbsp; <span class="ProjectStatusdrdwn"><label class="blue_link" title="Status"> Status</label> <select id="projectActive">'+'<option selected="selected" value="active">Active</option>'+'<option value="all">All</option>'+'</select>'+

					'</span></div>');
			</sec:authorize>
			
			$('#addNew').on("click",function(){
				$("#projectForm").reset();
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
				         //alert("IF You Unchecked! You have to Add Default Activities Manually"); 
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
			//var clsName=$(this).parent().parent().attr('class');
			$("table#addPoTbl tbody tr td").find("input[type=text].inpCost").each(function(){
				total += Number($(this).val());
			});
			$("#planProBudg").html(total);
		};
		function addDatePicker(){
			addPoLength=$("table#addPoTbl tbody").find("tr").length;
			for(var i =0; i<addPoLength; i++){
				 $("#issueDate"+i).datepicker({
					 changeMonth: true, // added by Neha
				      changeYear: true,  // added by Neha
					 onClose: function( selectedDate ) {
			                $( "#validUptoDate"+i).datepicker( "option", "minDate", selectedDate );
			            }
				    });
				 $("#validUptoDate"+i).datepicker({
					 changeMonth: true, // added by Neha
				      changeYear: true,  // added by Neha	
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
			
			refreshGrid();
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
			$("#endDate").datepicker({changeMonth: true,changeYear: true});
						
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
					 changeMonth: true, // added by Neha
				    	changeYear: true, // added by Neha
					 onClose: function( selectedDate ) {
			                $( "#validUptoDate"+len).datepicker( "option", "minDate", selectedDate );
			            }
				    });
				 $("#validUptoDate1"+len).datepicker({
					 changeMonth: true, // added by Neha
				    	changeYear: true, // added by Neha	
					 onClose: function( selectedDate ) {
			                $( "#issueDate"+len ).datepicker( "option", "maxDate", selectedDate );
			            }
				    });
				addDatePicker();
				addTotal();
			});
    		
			$("#addPoTbl").find("input[type=text].inpCost").on("blur", function(){
				/*var cost = $(this).val();
				if(cost != "" && !cost.match(/^[-+]?[0-9]+$/)) {
					alert("Cost should be numeric value only");
					$(this).focus();
					return;
				}*/
				addTotal();
			});
			/*-----------delete row------------*/
			
			/* $('.removePo').live("click", function(){				
				 var conf=confirm("Are you sure you want to delete this PO?");	 
				 var id =  $(this).closest("tr").attr('id');
				 if(conf){
					 startProgress();
					 $(this).closest("tr").remove();							 
					  $.deleteJson("projectPo/"+id, function(data){
						  addTotal();						
					  }, 'json');
					  stopProgress();
				 }
				 return;
				 				    	
			}); */
		$(".tab_div").hide();
		$('ul.tabs a.listTab').click(function () {
			$('#MaintenanceTabInactive').off('click');
			$(".tab_div").hide().filter(this.hash).show();
			$("ul.tabs a.listTab").removeClass('active');
			$('a[href$="tab2"]').addClass('MaintenanceTab');
			$(this).addClass('active');
			return false;
		}).filter(':first').click();
		
		
		$('#projectKickOff').datepicker({changeMonth: true,changeYear: true});
		
	
		
		addDataTableSearch($('#projectTableId'));
	    
		
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
  			initTable();
  			//document.getElementById('projectActive').value=status;
		});
		
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
			/* var customeRadio = $(".radio_b").find(".required");
			$(customeRadio).change(function(){
				if($(this).attr("checked","true")){
					$(this).parent().parent().parent("td").find(".radio_b").removeClass("invalid");
				}
			}); */
	
			$(document.body).on('change','#projectActive',function(){
			  
				status = document.getElementById('projectActive').value;
				
			    $.ajax({
					
					url: "projects/projectActive",
					//Issue #448- changed data field from projectActive to status- Neha 
					data: "projectActive=" + status,
					 success: function( data ) {if(projectTable != null) projectTable.fnClearTable();
						 $("#projectTableId > tbody:last").append(
									$("#projectTableRows").render(data)
								);
						 addDataTableSearch($("#projectTableId"));
							$(".tab_div").hide();
							$('ul.tabs a').click(function () {
								$(".tab_div").hide().filter(this.hash).show();
								$("ul.tabs a").removeClass('active');
								$(this).addClass('active');
								return false;
							}).filter(':first').click();
							document.getElementById('projectActive').value=status;
						} 
					
				});
			});
			
			
			
	$( "#save" ).click(function( event ) {
		
		
		var validationFlag = validateCombo(comboBoxBlur);
		if(validationFlag){
			startProgress();	
		}
		
		addTotal();
		/* if(document.getElementById('nondeere').checked){
            document.getElementById('deere').value = false;
    	 }else{
            document.getElementById('deere').value = true;
     	} */

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
				// if(projectName!=null || projectName!=undefined || projectName!="") {
					//projectName=null;
			//	} 
					$.ajax({
						async:false,
						url: "projects/validate",
						data: "projectName=" + projectName + "&projectCode=" + projectCode,
						success: function( data ) {
							if ( data.projectResult != true ) {
								projectResult = false;
								//isDuplicateProjectName = true;
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
	  	if(!(validDates(projectKickOffDate, projectEndDate))) {
	     	validationFlag = false;
			//document.getElementById("endDate").focus();
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
				//showError(errorMsg);
				//stopProgress();
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
			if(!(validDates(issueDate, validUptoDate))) {
				validationFlag = false;
				errorMsg = errorMsg+"\u2022 ValidUpto Date should be greater than Issue Date<br /> ";
				//$("#validUptoDate"+i).val("");
			}
		}
		 if (document.getElementById("engagementModelId.id").value=="-1"){

				validationFlag = false;
				errorMsg = errorMsg + "\u2022 Engagement Model should not be blank ! <br />";
			}
	//	 alert($( "input[name^='projectPoes']" ).val());
		  //added for PO validate start
		  /*  if ($( "input[name*='projectPoes']" ).value=="-1"){

				validationFlag = false;
				errorMsg = errorMsg + "\u2022 PO/Invoicing Details should not be blank! <br />";
			} */
		
		/* 	var valueArray = $('.poNumber').map(function() {
			    return this.value;
			}).get();
			
			alert(valueArray); */
			
			
			/* $('').each(function() {
				alert($(this).val());
			});
			
			
		 if (typeof($('[id*=poNumber]').val())  === "undefined"){

				validationFlag = false;
				errorMsg = errorMsg + "\u2022 PO number should not be blank ! <br />";
			}  */
		 /* if (document.getElementById("poNumber").value==""){

				validationFlag = false;
				errorMsg = errorMsg + "\u2022 PO number should not be blank ! <br />";
			}  */
		 //added for PO validate end 
		 if (document.getElementById("projectMethodologyId.id").value=="-1"){

				validationFlag = false;
				errorMsg = errorMsg + "\u2022 Development Methodology should not be blank ! <br />";
			}
		 
		 
		 if (document.getElementById("customerNameId.id").value=="-1"){

				validationFlag = false;
				errorMsg = errorMsg + "\u2022 Customer Name should not be blank ! <br />";
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
		 						/*  refreshGrid();
		 				         stopProgress();
		 				           showSuccess(succ);
		 				           window.location.reload();
		 				         } , 'json'); */
		 				          // added by Neha for kickOffDate issue  - start
		 				          if( data.status == "false")
		 				           {
		 				            stopProgress();
		 				           errorMsg = " Project Kick off date sholud be less than Allocation Start date !" ;      
		 				           showError(errorMsg);       
		 				           return;
		 				           }
		 				           else{
		 				          refreshGrid();
		 				          stopProgress();
		 				            showSuccess(successMsg);
		 				            window.location.reload();
		 				          }} , 'json');
		 				        // added by Neha for kickOffDate issue  - end
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
								errorMsg = " Project Kick off date sholud be less than Allocation Start date !"	;						
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
						 /* refreshGrid();
						stopProgress();
			  			showSuccess(successMsg);
			  			window.location.reload();
					 } , 'json');} */
				 else{
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
			
			
		/*  if(form_data){
			 startProgress();
			 var successMsg ="Project details have been saved successfully";
			var succ="here";
			var suc="not here";
			 $("#planProBudgId").val(total);
			 
			if( pk != null && pk > 0){
				 $.putJson('projects', $("#projectForm").toDeepJson() , function(data){
					refreshGrid();
					stopProgress();
		  			showSuccess(successMsg);
				 } , 'json');
			}
			
			
			
			 else{
				 
				$("#projectForm input[name=id]").val("");
				 $.postJson('projects',$("#projectForm").toDeepJson() , function(data){
					 isNew='true';
					 refreshGrid();
					 stopProgress();
					 showSuccess(successMsg);
				}, 'json');
			}				
		}else{
			stopProgress();
			return;
			} */
		/* window.location.reload(); */
	   	
	 });
	
			
	
	
		});//end of document.ready function
		function openMaintainance(id){
			
			startProgress();
			getProjectById(id);
			displayMaintainance();
			stopProgress();	
			
		}
		
		function showProject(data){
 
			// User Story #2731
			$("#defaultRowId").hide();
			//End User Story #2731
			$("#projectForm").populate(data, {debug:false, resetForm:true});
			$(".poTbl table tbody").find("tr").remove();
			$(".poTbl table tbody:last").append($.render.poTableRowsWithValuesTempl(data.projectPoes));
			addDatePicker();
			addTotal();			
		}
		
/* 		$( "#projectTableId a.copyInactive" ).live( "click", function(event) {
			event.stopPropagation();
			clickable = false;
			
			alert("click"+clickable);
			}); 

		$( "#projectTableId a.copyInactive" ).live( "dblclick", function(event) {
			event.stopPropagation();
			clickable = true;
			alert("dblclick"+clickable);
			}); */
		
	
			
		
		$("#projectTableId a.copyInactive,#projectTableId td").on("click", function(event){
	        
			clicks++;  //count clicks

	        if(clicks === 1) {

	            timer = setTimeout(function() {
	            	
	            	event.stopPropagation();
	    			clickable = true;
	    			

	               // alert('Single Click'+clickable); //perform single-click action

	                clicks = 0;  //after action performed, reset counter

	            }, DELAY);

	        } else {

	            clearTimeout(timer);  //prevent single-click action
	            
	            event.stopPropagation();
				clickable = false;
				

	            //alert('Double Click'+clickable);  //perform double-click action
	            
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

	
			/* $.each ($('form#projectForm select') ,function() {
				$(this).show();
				$(this).next().remove();
				$(this).attr("disabled","disabled");
			}); */
			//$('form#projectForm input').attr("readonly","readonly");
			$('form#projectForm input[type="text"]  ,textarea').attr("readonly","readonly");
			$('form#projectForm input[type=checkbox]').attr("disabled","disabled");
			$('form#projectForm input:radio').attr("disabled","disabled");
			var dateFieldIds = ["#endDate" ,"#projectKickOff"];
			$.each(dateFieldIds,function(index,fieldId) {
				$(fieldId).datepicker("destroy");
			});
			//$('#positionRel').find('input, textarea, button, select').attr('disabled','disabled');


			//$('div#positionRel input').attr("readonly","readonly");
			 
			</sec:authorize>
			appendInternal();
			appendUsDeere();
		}
		
		// End To fix #189	
		
		function saveProject(){
		}
		
		function refreshGrid(){
			if(projectTable != null) projectTable.fnClearTable();
				$.getJSON("projects/updatedlist", function(data) {
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
			var customerName= document.getElementById('customerNameId.id').options[document.getElementById('customerNameId.id').selectedIndex].value ;
			/* if(projectCat==3 && customerName==7){
				document.getElementById('nondeere').checked=true; 
			}
			else{
				document.getElementById('deere').checked=true;
			} */
			
			var select=document.getElementById('customerNameId.id');
			
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
		
		
</script>
<!-- <script type="text/javascript">
		$("#projectForm").validVal({
			
				form: {
					onInvalid: function( $fields, language ) {
					
				   
				}
		 }
			});
		</script>  -->
	<!-- <script type="text/javascript">
		$("#addPoTbl").validVal({
			
				form: {
					onInvalid: function( $fields, language ) {
					
				   
				}
		 }
			});
		</script> -->