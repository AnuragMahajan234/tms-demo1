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
		<!-- New Date Picker -->
<spring:url
	value="/resources/js-catickets/jquery.disabled.js?ver=${app_js_ver}"
	var="disabled_js" />
<script src="${datetimepicker_js }" type="text/javascript"></script>

<spring:url
	value="/resources/js-catickets/jquery.disabled.js?ver=${app_js_ver}"
	var="disabled_js" />
<script src="${datetimepicker_js }" type="text/javascript"></script>
<!-- New Date Picker -->
<script src="${disabled_js }" type="text/javascript"></script>
<spring:url
	value="/resources/styles/catickets/jquery.datetimepicker.css?ver=${app_js_ver}"
	var="datetimepicker_style_css" />
<link href="${datetimepicker_style_css}" rel="stylesheet"
	type="text/css"></link>

 <spring:url
	value="/resources/js-catickets/caticket.validation.js?ver=${app_js_ver}"
	var="caticket_validation_js" />
<script src="${caticket_validation_js }" type="text/javascript"></script>


<script>

function clearDate(){
	 $('#acknowledgedDateId').val("");
	 
	}
window.setInterval(function () {
	  var date=$('#acknowledgedDateId').val();
	   
	     if(date==""){
	      document.getElementById("reqCompleteFlagId").disabled=true;
	      document.getElementById("reqCompleteFlagId").value='No';
	      $("#reqCompleteFlagId1").val('No');
	     }else{
	      document.getElementById("reqCompleteFlagId").disabled=false;
	     }
	 }, 300);
function datepicker(dateId){
	//alert(dateId);
	return NewCssCal(dateId,"yyyyMMdd","arrow","true", 12, "true",'past');
}
var isT3Open = false;
var isDefectLogOpen = false;
var isSolRevOpen = false;
var isCropOpen = false;
var isReworkOpen = false;

function getModuleByEmployee(obj){
	var employeeId= $(obj).val();
	//alert("mid: "+mid);
	$.ajax({
        url: '/rms/caticket/getModuleByEmployee',
     	data: {"employeeId":employeeId},
     	success: function(response) {
     		loadedData =response;
     		var option='';
     		$('#moduleId').find('option').remove();
     		 option = '<option value="-1" selected=selected>Please Select</option>';
     		$('#moduleId').append(option);
     		for(var i=0;i<response.length;i++){
     			var obj=response[i];
     			 option = '<option value="' +obj.id + '">' + obj.moduleName+ '</option>';			     		
		     		$('#moduleId').append(option);
     		}
     		//alert(JSON.stringify(response));
     		//alert("response.processName"+ ($('#processId').val(loadedData.processName)));
     		/* $("#processId option[value="+(processId)+"]").prop('selected', true);			
     		$("#processId").parent().find("input").val($("#processId option[value="+format(processId)+"]").text()); */
     		 
     	}});
	
	}
	
function moduleProcessFunction(obj){
	var mid= $(obj).val();
	//alert("mid: "+mid);
	$.ajax({
        url: '/rms/caticket/loadProcessModule',
     	data: {"moduleId":mid},
     	success: function(response) {
     		loadedData =response;
     		var option='';
     		$('#processId').find('option').remove();
     		 option = '<option value="-1" selected=selected>Select</option>';
     		$('#processId').append(option);
     		for(var i=0;i<response.length;i++){
     			var obj=response[i];
     			 option = '<option value="' +obj.id + '">' + obj.processName+ '</option>';			     		
		     		$('#processId').append(option);
     		}
     		//alert(JSON.stringify(response));
     		//alert("response.processName"+ ($('#processId').val(loadedData.processName)));
     		/* $("#processId option[value="+(processId)+"]").prop('selected', true);			
     		$("#processId").parent().find("input").val($("#processId option[value="+format(processId)+"]").text()); */
     		 
     	}});
	
	}

function getSubProcess(obj){
	var processId= $(obj).val();
	
	$.ajax({
        url: '/rms/caticket/loadSubProcesses',
     	data: {"processId":processId},
     	success: function(response) {
     		var option='';
     		$('#subProcess').find('option').remove();
     		 option = '<option value="" selected=selected>Select</option>';			     		
	     		$('#subProcess').append(option);
     		for(var i=0;i<response.length;i++){
     			var obj=response[i];
     			 option = '<option value="' +obj.id + '">' + obj.subProcessName+ '</option>';			     		
		     		$('#subProcess').append(option);
     		}
     		
     	}});
}

function getRegoinByUnit(obj){
	 var unit= $(obj).val();
	 //alert("mid: "+unit);
	 $.ajax({
	        url: '/rms/caticket/loadRegion',
	      data: {"unitId":unit},
	      success: function(response) {
	       loadedData =response;
	       var option='';
	       $('#regionId').find('option').remove();
	        option = '<option value="" selected=selected>Select</option>';
	       $('#regionId').append(option);
	       for(var i=0;i<response.length;i++){
	        
	        var obj=response[i];
	        //alert("shikhi sdvsd"+obj.regionName);
	         option = '<option value="' +obj.id + '">' + obj.regionName+ '</option>';          
	         $('#regionId').append(option);
	       }
	       //alert(JSON.stringify(response));
	       //alert("response.processName"+ ($('#processId').val(loadedData.processName)));
	       /* $("#processId option[value="+(processId)+"]").prop('selected', true);   
	       $("#processId").parent().find("input").val($("#processId option[value="+format(processId)+"]").text()); */
	        
	      }});
	 
	}

	$(document).ready(function() {
		/* $('.datepickerStamp').datetimepicker({
			mask : '9999/19/39 29:59'
		});*/
		
		var probMgmt = $("#problemManagementDivId").val();
		if(probMgmt == "No" || probMgmt==""){
			$("#problemManagementDivId").hide();
		}
		
		//Disabling all phases initially....
		
		var analysisComplete = $("#analysisCompleteFlagId").next("span");
		//var solutionReview = $("#solutionReviewFlagId").next("span");
		var solutionDeveloped = $("#solutionDevelopedFlagId").next("span");
		var solutionAccepted = $("#solutionAcceptedFlagId").next("span");
		var closedPending = $("#customerApprovalFlagId").next("span");
		var solutionReadyForReview = $("#solutionReadyForReviewId").next("span");
		
		$("#requiredCompletedDateId").attr("disabled", "disabled").val('');
		$("#analysisCompletedDateId").attr("disabled", "disabled").val('');
		//$("#analysisCompleteDivId").attr("disabled", "disabled");
		$("#solutionreViewDateId").attr("disabled", "disabled").val('');
		//$("#solutionReviewDivId").attr("disabled", "disabled");
		$("#solutiondevelopedDateId").attr("disabled", "disabled").val('');
		//$("#solutiondevelopedDivId").attr("disabled", "disabled");
		$("#solutionAcceptedDateId").attr("disabled", "disabled").val('');
		//$("#solutionAcceptedDivId").attr("disabled", "disabled");
		$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled").val('');
		//$("#closePendingCustomerApprovalDivId").attr("disabled", "disabled");
		/* analysisComplete.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		analysisComplete.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
		/* solutionReview.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		solutionReview.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
		/* solutionDeveloped.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		solutionDeveloped.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		solutionAccepted.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		solutionAccepted.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		closedPending.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		closedPending.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		solutionReadyForReview.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		solutionReadyForReview.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
		
		document.getElementById("solutionReadyForReviewId").disabled=true;
		document.getElementById("reqCompleteFlagId").disabled=true;
		document.getElementById("analysisCompleteFlagId").disabled=true;
		document.getElementById("solutionDevelopedFlagId").disabled=true;
		document.getElementById("solutionAcceptedFlagId").disabled=true;
		document.getElementById("customerApprovalFlagId").disabled=true;
		
		//$("#solutionReadyForReviewId").hide();
		
		$("#solutionReadyForReviewId1").val('No');
		$("#reqCompleteFlagId1").val('No');
		$("#analysisCompleteFlagId1").val('No');
		$("#solutionDevelopedFlagId1").val('No');
		$("#solutionAcceptedFlagId1").val('No');
		$("#customerApprovalFlagId1").val('No');
		//$("#analysisCompleteFlagId1").val('No');
	});
	
	</script>

<spring:url
	value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}"
	var="multiselect_filter_js" />
<spring:url
	value="/resources/styles/skin/ui.dynatree.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>

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
<%-- <script src="${js_cookie}" type="text/javascript"></script> --%>
<script src="${js_dynatree}" type="text/javascript"></script>



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
	
	<h1>Ticket Details</h1><br>
	<form:form method="post" id="caTicketFormId" name="caTicketFormName" modelAttribute="caTicketForm"
		action="${pageContext.request.contextPath}/caticket/createTicket">
		<div id="caTicketNoDiv" class="center_div">
			<div class="form">
		
				<table id="formTable" width="100%">
						<input type="hidden" value=""
									name="id" id="caTicketId" maxlength="10"
									class="required string server-validation" />
									
									<input type="hidden" value="${role }"
									name="role" id="roleId"/>
					<tr>
						<td width="11%" align="right" id="ticketNoId">Ticket No :<span
							class="astric">*</span>
						</td>
						<td width="18%" align="left">
							<div class="positionRel">
							
								<input type="text" value=""
									name="caTicketNo" id="caTicketNoID" maxlength="10"
									class="required string server-validation" />
							</div>
						</td>
						<td width="11%" align="right">Description :<span
							class="astric">*</span></td>
						<td width="18%" align="left"><textarea name="description"
								id="description" cols="" rows="2" class="string"></textarea></td>


						<td width="13%" align="right">Priority :</td>
						<td align="left"><select name="priority"
							class="required comboselect check" id="priority">
								<%-- <option selected="selected" value="${catickets.priority}">${catickets.priority}</option> --%>
								<option value="-1" selected="selected">Please Select</option>
								<option value="1 - Urgent">1 - Urgent</option>
								<option value="2 - High">2 - High</option>
								<option value="3 - Medium">3 - Medium</option>
								<option value="4 - Low">4 - Low</option>
								<option value="5 - Project">5 - Project</option>
						</select></td>
						<td align="right">Reviewer :<span class="astric">*</span></td>
						<td align="left"><select name="reviewer.employeeId"
							class="required comboselect check" id="reviewer">
							<option value="-1" selected="selected">Please Select</option>
							   <%-- <option selected="selected" value="${catickets.assigneeId}">${catickets.reviewer.employeeName}</option> --%>
								<c:forEach var="resource" items="${resources}">
								<option value="${resource.employeeId}">${resource.employeeName}</option>
								 </c:forEach> 
						</select></td>
					</tr>
					<tr>
						<td align="right">Unit :<span class="astric">*</span>
						</td>
						<td align="left"><select name="unitId.id" id="unit" onchange="getRegoinByUnit(this)"
							class="required comboselect check">
								<%-- <option selected="selected" value="${catickets.unit}">${catickets.unit}</option> --%>
								<option value="-1" selected="selected">Please Select</option>
								<c:forEach var="unit" items="${units}">
								<option value="${unit.id}">${unit.unitName}</option>
								 </c:forEach> 
						</select></td>
						<td align="right">Region :<span class="astric">*</span></td>
						<td align="left"><select name="region.id" id="regionId"
							class="required comboselect check">
							<option value="-1">Please Select</option>
								<%-- <option selected="selected" value="${catickets.region}">${catickets.region}</option>
								<option value="China">China</option>
								<option value="India">India</option>
								<option value="Europe">Europe</option>
								<option value="North America">North America</option>
								<option value="South America">South America</option> --%>
						</select></td>
						<td align="right">Assignee Name :<span class="astric">*</span></td>
						<td align="left"><select name="assigneeId.employeeId"
							class="required comboselect check" id="assigneeNameId" onchange="getModuleByEmployee(this)">
							<option value="-1" selected="selected">Please Select</option>
						<%-- <option selected="selected" value="${catickets.assigneeId}">${catickets.assigneeId.employeeName}</option> --%>  
								<c:forEach var="resource" items="${resources}">
								<option value="${resource.employeeId}">${resource.employeeName}</option>
								 </c:forEach> 
						</select></td>
						<td width="13%" align="right">Module :</td>
						<td align="left"><select name="moduleId.id"
							class="required comboselect check" id="moduleId" onchange="moduleProcessFunction(this)">
							<option value="-1" selected="selected">Please Select</option>
							<%-- <option selected="selected" value="${catickets.moduleId.id}">${catickets.moduleId.moduleName}</option> --%>
								 <%-- <c:forEach var="project" items="${projects}">
									<option value="${project.id}">${project.moduleName}</option>
								</c:forEach> --%>
								
						</select></td>
						
					</tr>
					<tr>
						
						<td align="right">Landscape :<span class="astric">*</span></td>
						<td align="left"><select name="landscapeId.id"
							class="required comboselect check" id="landscape">
							<option value="-1" selected="selected">Please Select</option>
								<c:forEach var="landscape" items="${landscapes}">
								<option value="${landscape.id}">${landscape.landscapeName}</option>
								 </c:forEach>
						</select></td>
						<td align="right">Solution Ready For Review : 
						<td align="left"><select name="solutionReadyForReview1"
							id="solutionReadyForReviewId">
								<!-- <option value="-1">Please Select</option> -->
								<option value="No" selected="selected">No</option>
								<option value="Yes">Yes</option>
								<!-- <option value="N.A.">N.A.</option> -->
								<!-- <option value="NA">N.A.</option> -->
						</select></td>
						<input type="hidden" name="solutionReadyForReview" value="" id="solutionReadyForReviewId1" readonly="readonly" style="background-color: #DCDCDC;"/>
						<td align="right">Aging :</td>
						<td align="left"><select disabled name="aging" id="agingId">
								<option value="${catickets.aging}">${catickets.aging}</option>
						</select></td>

						<td align="right"></td>
						<td align="left"></td>
					</tr>
					<tr>
						<td align="right">SLA Missed : </td>
						<td align="left"><select disabled name="slaMissed"
							id="slaMissedId">
								<option value="${catickets.slaMissed }">${catickets.slaMissed }</option>
						</select></td>
						<td align="right">Reason for SLA Missed : </td>
						<c:choose>
							<c:when test="${catickets.slaMissed=='Yes'}">
								<td align="left"><select name="reasonForSLAMissed"
									id="reasonForSLAMissedId" class="required comboselect check">
										<option value="Pending with us">Pending with us</option>
								</select></td>
							</c:when>
							<c:otherwise>
								<td align="left"><select name="reasonForSLAMissed"
									id="reasonForSLAMissedId" disabled>
										<option value="Pending with us">Pending with us</option>
								</select></td>
							</c:otherwise>
						</c:choose>
						<!-- <td align="right">Days Open :</td>
						<td align="left"><select disabled name="daysOpen"
							id="daysOpen">
								<option value="Yes">Yes</option>
						</select></td> -->
						<td align="right">Reopen Ticket Frequency :</td>
						<td align="left"><select disabled name="reopenFrequency"
							id="reopenTicketFrequencyId">
								<option value="${catickets.reopenFrequency }">${catickets.reopenFrequency }</option>
						</select>
						<td align="right"></td>
						<td align="left"></td>
					</tr>
					<tr>
						<td align="right">Reason For Reopen : <span class="astric">*</span></td>
						<c:choose>
							<c:when test="${catickets.reopenFrequency>=1 }">
								<td align="left"><input type="text" value=""
									name="reasonForReopen" id="reasonForReopenId" /></td>
							</c:when>
							<c:otherwise>
								<td align="left"><input type="text" disabled value=""
									name="reasonForReopen" id="reasonForReopenId" /></td>
							</c:otherwise>
						</c:choose>
						<td align="right">Justified Hopping :<span class="astric">*</span></td>
						<td align="left"><select disabled name="justifiedHopping"
							id="justifiedHoppingId">
								<option value="Yes">Yes</option>
								<option value="No" selected="selected">No</option>
						</select></td>
						<td align="right">Reason For Hopping : <span class="astric">*</span></td>
						<td align="left"><input type="text" value=""
							name="reasonForHopping" id="reasonForHoppingId" disabled /></td>
						<td align="right">Group :<span class="astric">*</span></td>
						<td align="left"><select name="groupName" id="groupId"
							class="required comboselect check">
								<option value="-1">Please Select</option>
								<option value="Tier 2">Tier 2</option>
								<option value="Unit">Unit</option>
								<option value="Global">Global</option>
								<option value="External">External</option>
								<option value="Security">Security</option>
								<option value="N.A.">N.A.</option>
						</select></td>
						<td align="right"></td>
						<td align="left"></td>
					</tr>
					<tr>
						<td align="right"></td>
						<td align="left"></td>
						<td align="right"></td>
						<td align="left"></td>
						<td align="right"></td>
						<td align="left"></td>
						<td align="right"></td>
						<td align="left"><input type="text"
							style="visibility: hidden;" value="" name="awardRecognition"
							id="awardRecognition" /></td>
					</tr>
				</table>
		
			
			</div>
		</div>
		<br>

		<h1>Phase</h1>
		<br>
		<div class="center_div">
			<div class="form">
				<table id="formTable" width="100%" cellpadding="10" cellspacing="10">
					<tr>
						<td width="11%" align="right">Created Date & Time Stamp :<span
							class="astric">*</span>
						</td>
						<td width="18%" align="left">
							<div class="positionRel">
								<input type="text" value="" name="creationDate"
									id="creationDateId" maxlength="25" size="25"
      							 onclick="javascript:NewCssCal ('creationDateId','yyyyMMdd','arrow','true', 12, 'true','past')"  />   
							</div>
						</td>
						<td width="11%" align="right">Acknowledged Date & Time Stamp
							:
						</td>
						<td width="18%" align="left">
							<div class="positionRel">
								<input type="text" value="${catickets.acknowledgedDate }" name="acknowledgedDate"
									id="acknowledgedDateId" maxlength="25" size="25" readonly="readonly"
									onclick="javascript:NewCssCal ('acknowledgedDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/>
									<span onclick="clearDate()">Clear</span>
							</div>
						</td>
						<td width="11%" align="right">Requirement completed Date &
							Time Stamp :
						</td>
						<td width="18%" align="left">
							<div class="positionRel" id="reqCompleteDivId">

								<input type="text" value="" name="requiredCompletedDate"
									id="requiredCompletedDateId" maxlength="25" size="25"
									onclick="javascript:NewCssCal ('requiredCompletedDateId','yyyyMMdd','arrow','true', 12, 'true','past')" /><br>
								<br> <select
									name="reqCompleteFlag1" id="reqCompleteFlagId"><option
										value="Yes">Yes</option>
									<option value="No" selected="selected">No</option>
									<option value="N.A.">N.A.</option></select>
									<input type="hidden" name="reqCompleteFlag" value="" id="reqCompleteFlagId1" readonly="readonly" style="background-color: #DCDCDC;"/>
							</div>
						</td>
						<td width="11%" align="right">Analysis completed Date & Time
							Stamp :
						</td>
						<td width="18%" align="left">
							<div class="positionRel" id="analysisCompleteDivId">
								<input type="text" value="" name="analysisCompletedDate"
									id="analysisCompletedDateId" maxlength="25" size="25"
									onclick="javascript:NewCssCal ('analysisCompletedDateId','yyyyMMdd','arrow','true', 12, 'true','past')" /><br>
								<br>  <select
									name="analysisCompleteFlag1" id="analysisCompleteFlagId"><option
										value="Yes">Yes</option>
									<option value="No" selected="selected">No</option>
									<option value="N.A.">N.A.</option></select> 
									<input type="hidden" name="analysisCompleteFlag" value="" id="analysisCompleteFlagId1" readonly="readonly" style="background-color: #DCDCDC;"/>
							</div>
						</td>
					</tr>
					</thead>
					<!-- <tbody id="resourcerequestTableBody"> -->
						<tr>
								<td width="11%" align="right">Solution Developed completed
								Date & Time Stamp :
							</td>
							<td width="18%" align="left">
								<div class="positionRel" id="solutiondevelopedDivId">
									<input type="text" value="" name="solutiondevelopedDate"
										id="solutiondevelopedDateId" maxlength="25" size="25"
									onclick="javascript:NewCssCal ('solutiondevelopedDateId','yyyyMMdd','arrow','true', 12, 'true','past')" /><br>
									<br> <select
										name="solutionDevelopedFlag1" id="solutionDevelopedFlagId"><option
											value="Yes">Yes</option>
										<option value="No" selected="selected">No</option>
										<option value="N.A.">N.A.</option></select>
										<input type="hidden" name="solutionDevelopedFlag" value="" id="solutionDevelopedFlagId1" readonly="readonly" style="background-color: #DCDCDC;"/>
								</div>
							</td>
						
							<td width="11%" align="right">Solution review Date & Time
								Stamp :
							</td>
							<td width="18%" align="left">
								<div class="positionRel" id="solutionReviewDivId">
									<input type="text" value="" name="solutionreViewDate"
										id="solutionreViewDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"  /><br>
									<!-- <br> <select class="required comboselect check"
										name="solutionReviewFlag" id="solutionReviewFlagId"><option
											value="Yes">Yes</option>
										<option value="No" selected="selected">No</option>
										<option value="N.A.">N.A.</option></select> -->
								</div>
							</td>
							
							<td width="11%" align="right">Solution (User Acceptance)
								accepted Date & Time Stamp :
							</td>
							<td width="18%" align="left">
								<div class="positionRel" id="solutionAcceptedDivId">
									<input type="text" value="" name="solutionAcceptedDate"
										id="solutionAcceptedDateId" maxlength="25" size="25"
									onclick="javascript:NewCssCal ('solutionAcceptedDateId','yyyyMMdd','arrow','true', 12, 'true','past')" /><br>
									<br> <select
										name="solutionAcceptedFlag1" id="solutionAcceptedFlagId"><option
											value="Yes">Yes</option>
										<option value="No" selected="selected">No</option>
										<option value="N.A.">N.A.</option></select>
										<input type="hidden" name="solutionAcceptedFlag" value="" id="solutionAcceptedFlagId1" readonly="readonly" style="background-color: #DCDCDC;"/>
								</div>
							</td>
							<td width="11%" align="right">Closed pending customer
								approval Date & Time Stamp :
							</td>
							<td width="18%" align="left">
								<div class="positionRel" id="closePendingCustomerApprovalDivId">

									<input type="text" value=""
										name="closePendingCustomerApprovalDate"
										id="closePendingCustomerApprovalDateId" maxlength="25" size="25"
									onclick="javascript:NewCssCal ('closePendingCustomerApprovalDateId','yyyyMMdd','arrow','true', 12, 'true','past')" /><br>
									<br> <select
										name="customerApprovalFlag1" id="customerApprovalFlagId"><option
											value="Yes">Yes</option>
										<option value="No" selected="selected">No</option>
										 <option value="N.A.">N.A.</option> </select>
										<input type="hidden" name="customerApprovalFlag" value="" id="customerApprovalFlagId1" readonly="readonly" style="background-color: #DCDCDC;"/>
								</div>
							</td>
						</tr>
						<tr>
							<td align="right"></td>
							<td align="left"></td>
							<td align="right"></td>
							<td align="left"></td>
							<td align="right"></td>
							<td align="left"></td>
							<td align="right"></td>
							<td align="left"><input style="visibility: hidden;"
								type="text" value="" name="awardRecognition"
								id="awardRecognition" /></td>
						</tr>
				</table>
			</div>
		</div>
		<div> <!-- id="problemManagementMainId" -->
		<br>

		<h1 style="display:inline-block;">Problem Management </h1>&nbsp;
		<select name="problemManagementFlag" style="font: 15px;"
							id="problemManagementId" class="required comboselect check">
								<option value="No" selected="selected">No</option>
								<option value="Yes">Yes</option>
								<option value="N.A.">N.A.</option>
						</select>
		
						
		<br><br>
		<div class="center_div" id="problemManagementDivId">
			<div class="form">
				<table id="formTable" width="100%">
					<tr>
						<td width="11%" align="right">Process :<span class="astric">*</span>
						</td>
						<td align="left"><select name="process.id"
							class="required comboselect check" id="processId" onchange="getSubProcess(this)">
								<option value="-1" selected="selected">Please Select</option>
								<%-- <c:forEach var="process" items="${process}">
								<option value="${process.id}">${process.processName}</option>
								 </c:forEach>  --%>
						</select></td>
						<td width="11%" align="right">Sub Process :<span
							class="astric">*</span>
						</td>
						<td align="left"><select name="subProcess.id"
							class="required comboselect check" id="subProcess">
							<option value="-1" selected="selected">Please Select</option>
								<!-- <option value="">Select One</option>
								<option value="SP1">Sub P1</option>
								<option value="SP2">Sub P2</option>
								<option value="SP3">Sub P3</option> -->
						</select></td>
						<td width="11%" align="right">Root Cause :<span
							class="astric">*</span>
						</td>
						<td align="left"><select name="rootCause.id"
							id="rootCauseId">
								<option value="-1" selected="selected">Please Select</option>
								<c:forEach var="rc" items="${rootcause}">
									<option value="${rc.id}">${rc.rootCause}</option>
								</c:forEach>

						</select></td>
						<td width="11%" align="right">ZREQ No :<span class="astric">*</span></td>
						<td width="18%" align="left">
							<div class="positionRel">
								<input type="text" value="" name="ZREQNo" id="ZREQNo"
									maxlength="10" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="11%" align="right">Parent Ticket No :<span
							class="astric">*</span></td>
						<td width="18%" align="left">
							<div class="positionRel">
								<input type="text" value="" name="parentTicketNo"
									id="parentTicketNo" maxlength="10"
									class="required string server-validation" />
							</div>
						</td>
						<td align="right">Comment :<span class="astric">*</span>
						</td>
						<td align="left"><textarea name="comment" cols=""
								id="comment" rows="2" class="string"></textarea></td>
						<sec:authorize
							access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_MANAGER')">
							<td width="11%" align="right">Justified Problem Management :<span
								class="astric">*</span>
							</td>
							<td align="left"><select name="justifiedProblemManagement"
								class="required comboselect check" id="justifiedProblemManagement">
									<option value="Yes">Yes</option>
									<option value="No" selected="selected">No</option>
							</select></td>
						</sec:authorize>
						<td align="right"></td>
						<td align="left"></td>
						<td align="right"></td>
						<td align="left"></td>

					</tr>
					<tr>
						<td align="right"></td>
						<td align="left"></td>
						<td align="right"></td>
						<td align="left"></td>
						<td align="right"></td>
						<td align="left"></td>
						<td align="right"></td>
						<td align="left"><input type="text"
							style="visibility: hidden;" value="" name="awardRecognition"
							id="awardRecognition" /></td>
					</tr>
				</table>

			</div>
		</div>
		</div>
		<div class="button_div">
			<div style="float: right; margin-top: 5px; padding-top: 20px;">
				<input type="button" name="save" value="Save" id="save" onclick="return validateForm(false,'../caticket/isTicketAlreadyExist');"> 
			</div>
		</div>
	</form:form>

</div>
<!-- file alert-->
<div id="fileAlert">
	<p></p>
	<div class="fileAlertBtm">
		<input type="button" name="Ok" value="Ok" id="ok" />
	</div>
</div>

