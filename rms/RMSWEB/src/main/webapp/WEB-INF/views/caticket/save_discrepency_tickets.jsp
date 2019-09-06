<%@ page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<spring:url
	value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}"
	var="multiselect_js" />
<script src="${multiselect_js}" type="text/javascript"></script>
<spring:url
 value="/resources/js-framework/datetimepicker_css.js?ver=${app_js_ver}"
 var="datetimepicker_css" />
<script src="${datetimepicker_css }" type="text/javascript"></script>
		<!-- New Date Picker -->

<!-- Validating form data -->		
<spring:url
	value="/resources/js-catickets/caticket.validation.js?ver=${app_js_ver}"
	var="caticket_validation_js" />
<script src="${caticket_validation_js }" type="text/javascript"></script>
<!-- End Validating form data -->
	
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

<script>
var isT3Open = false;
var isDefectLogOpen = false;
var isSolRevOpen = false;
var isCropOpen = false;
var isReworkOpen = false;

function datepicker(dateId){
	//alert(dateId);
	return NewCssCal(dateId,"yyyyMMdd","arrow","true", 12, "true");
}

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
     		 option = '<option value="-1" selected=selected>Please Select</option>';
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
     		 option = '<option value="-1" selected=selected>Select</option>';			     		
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
	        option = '<option value="-1" selected=selected>Please Select</option>';
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
		});
		
		var ca = $('#caTicketNoID').val();
		var closedPending = $("#caTicketNoID"); */
		//alert(ca);
		
		
		
		$('#contributionTable').dataTable();
		$('#solutionReviewTable').dataTable();
		$('#defectLogTable').dataTable();
		$('#cropTable').dataTable();
		$('#reworkTable').dataTable();
		
		var requirementCompletedDate = document.getElementById("requiredCompletedDateId").value;
		var analysisCompletedDate = document.getElementById("analysisCompletedDateId").value;
		var solutionDevelopedDate = document.getElementById("solutiondevelopedDateId").value;
		var solutionReviewDate = document.getElementById("solutionreViewDateId").value;
		var solutionAcceptanceDate = document.getElementById("solutionAcceptedDateId").value;
		var closedDate = document.getElementById("closePendingCustomerApprovalDateId").value;

		var requirementCompletedFlag = document.getElementById("reqCompleteFlagId").value;
		var analysisCompletedFlag = document.getElementById("analysisCompleteFlagId").value;
		var solutionDevelopedFlag = document.getElementById("solutionDevelopedFlagId").value;
		var solutionReadyForReviewId = document.getElementById("solutionReadyForReviewId").value;
		var solutionAcceptedFlag = document.getElementById("solutionAcceptedFlagId").value;
		var customerApprovalFlag = document.getElementById("customerApprovalFlagId").value;
		
		if((closedDate !=null && closedDate !="") || customerApprovalFlag == 'N.A.'){
			
			$("#reqCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			$("#reqCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
			
			$("#analysisCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			$("#analysisCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
			
			$("#solutionDevelopedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			$("#solutionDevelopedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
			
			/* $("#solutionReviewFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			$("#solutionReviewFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
			
			$("#solutionAcceptedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			$("#solutionAcceptedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
			
			$("#customerApprovalFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			$("#customerApprovalFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		}else{
			
			if((solutionAcceptanceDate !=null && solutionAcceptanceDate !="") || solutionAcceptedFlag == 'N.A.'){
				$("#reqCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
				$("#reqCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
				
				$("#analysisCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
				$("#analysisCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
				
				$("#solutionDevelopedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
				$("#solutionDevelopedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
				
				/* $("#solutionReviewFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
				$("#solutionReviewFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
				
				$("#solutionAcceptedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
				$("#solutionAcceptedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
				
				$("#customerApprovalFlagId").next("span").find("input, a").removeAttr("disabled").removeClass("disableInpDd");
				$("#customerApprovalFlagId").next("span").find("div#ddIcon").remove();
				
				$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled");
			}else{
				
				if((solutionReviewDate !=null && solutionReviewDate !="") || solutionReadyForReviewId == 'N.A.' || solutionReadyForReviewId == 'Yes'){
					
					$("#reqCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
					$("#reqCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
					
					$("#analysisCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
					$("#analysisCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
					
					$("#solutionDevelopedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
					$("#solutionDevelopedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
					
					/* $("#solutionReviewFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
					$("#solutionReviewFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
					
					$("#solutionAcceptedFlagId").next("span").find("input, a").removeAttr("disabled").removeClass("disableInpDd");
					$("#solutionAcceptedFlagId").next("span").find("div#ddIcon").remove();
					
					$("#customerApprovalFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
					$("#customerApprovalFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
					
					$("#solutionAcceptedDateId").attr("disabled", "disabled");
					$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled");
					
				}else{
					
					if((solutionDevelopedDate !=null && solutionDevelopedDate !="") || solutionDevelopedFlag == 'N.A.'){
						
						$("#reqCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
						$("#reqCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
						
						$("#analysisCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
						$("#analysisCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
						
						$("#solutionDevelopedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
						$("#solutionDevelopedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
						
						/* $("#solutionReviewFlagId").next("span").find("input, a").removeAttr("disabled").removeClass("disableInpDd");
						$("#solutionReviewFlagId").next("span").find("div#ddIcon").remove(); */
						
						$("#solutionAcceptedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
						$("#solutionAcceptedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
						
						$("#customerApprovalFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
						$("#customerApprovalFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
						
						$("#solutionreViewDateId").attr("disabled", "disabled");
						$("#solutionAcceptedDateId").attr("disabled", "disabled");
						$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled");
						
					}else{
						if((analysisCompletedDate !=null && analysisCompletedDate !="") || analysisCompletedFlag == 'N.A.'){
							$("#reqCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
							$("#reqCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
							
							$("#analysisCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
							$("#analysisCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
							
							$("#solutionDevelopedFlagId").next("span").find("input, a").removeAttr("disabled").removeClass("disableInpDd");
							$("#solutionDevelopedFlagId").next("span").find("div#ddIcon").remove();
							
							/* $("#solutionReviewFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
							$("#solutionReviewFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
							
							$("#solutionAcceptedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
							$("#solutionAcceptedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
							
							$("#customerApprovalFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
							$("#customerApprovalFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
							
							$("#solutiondevelopedDateId").attr("disabled", "disabled");
							$("#solutionreViewDateId").attr("disabled", "disabled");
							$("#solutionAcceptedDateId").attr("disabled", "disabled");
							$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled");
						}else{
							if((requirementCompletedDate !=null && requirementCompletedDate !="") || requirementCompletedFlag == 'N.A.'){
								$("#reqCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#reqCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
								
								$("#analysisCompleteFlagId").next("span").find("input, a").removeAttr("disabled").removeClass("disableInpDd");
								$("#analysisCompleteFlagId").next("span").find("div#ddIcon").remove();
								
								$("#solutionDevelopedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#solutionDevelopedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
								
								/* $("#solutionReviewFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#solutionReviewFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
								
								$("#solutionAcceptedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#solutionAcceptedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
								
								$("#customerApprovalFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#customerApprovalFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
								
								$("#analysisCompletedDateId").attr("disabled", "disabled");
								$("#solutiondevelopedDateId").attr("disabled", "disabled");
								$("#solutionreViewDateId").attr("disabled", "disabled");
								$("#solutionAcceptedDateId").attr("disabled", "disabled");
								$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled");
							}else{
								$("#reqCompleteFlagId").next("span").find("input, a").removeAttr("disabled").removeClass("disableInpDd");
								$("#reqCompleteFlagId").next("span").find("div#ddIcon").remove();
								
								$("#analysisCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#analysisCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
								
								$("#solutionDevelopedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#solutionDevelopedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
								
								/* $("#solutionReviewFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#solutionReviewFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
								
								$("#solutionAcceptedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#solutionAcceptedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
								
								$("#customerApprovalFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#customerApprovalFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
								
								$("#requiredCompletedDateId").attr("disabled", "disabled");
								$("#analysisCompletedDateId").attr("disabled", "disabled");
								$("#solutiondevelopedDateId").attr("disabled", "disabled");
								$("#solutionreViewDateId").attr("disabled", "disabled");
								$("#solutionAcceptedDateId").attr("disabled", "disabled");
								$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled");
							}
						}
					}
					
				}
			}
			
			
		}
		
		if($("#problemManagementId").val() == 'No' || $("#problemManagementId").val() == 'N.A.'){
			$("#problemManagementDivId").hide();
		}
		
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
		action="../../caticket/saveDiscrepencyTicket">
		<div id="caTicketNoDiv" class="center_div">
			<div class="form">
		
				<table id="formTable" width="100%">
						<input type="hidden" value="${discTickets.id }"
									name="discrepencyId" id="discrepencyId" maxlength="10"
									class="required string server-validation" />
									<input type="hidden" value="${role }"
									name="role" id="roleId"/>
					<tr>
						<td width="11%" align="right" id="ticketNoId">Ticket No :<span
							class="astric">*</span>
						</td>
						<td width="18%" align="left">
							<div class="positionRel">
							
								<input type="text" value="${discTickets.caTicketNo }"
									name="caTicketNo" id="caTicketNoID" maxlength="20" readonly="readonly" style="background-color: #DCDCDC;"
									class="required string server-validation" />
							</div>
						</td>
						<td width="11%" align="right">Description :<span
							class="astric">*</span></td>
						<td width="18%" align="left"><textarea name="description"
								id="description" cols="" rows="2" class="string">${discTickets.description}</textarea></td>


						<td width="13%" align="right">Priority :</td>
						<td align="left"><select name="priority"
							class="required comboselect check" id="priority">
								<option selected="selected" value="${discTickets.priority}">${discTickets.priority}</option>
								<!-- <option value="-1" selected="selected">Please Select</option> -->
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
								<%-- <option value="${resource.employeeId}">${resource.employeeName}</option> --%>
								<c:choose>
										 <c:when test="${discTickets.reviewer.employeeId==resource.employeeId}">
												<option value="${resource.employeeId}" selected="selected">${resource.employeeName}</option>
										 </c:when>
										 <c:otherwise>
										 	<option value="${resource.employeeId}">${resource.employeeName}</option>
										 </c:otherwise>
									</c:choose>
								 </c:forEach> 
						</select></td>
					</tr>
					<tr>
						<td align="right">Unit :<span class="astric">*</span>
						</td>
						<td align="left"><select name="unitId.id" id="unit" onchange="getRegoinByUnit(this)"
							class="required comboselect check">
								<%-- <option selected="selected" value="${catickets.unit}">${catickets.unit}</option> --%>
								<!-- <option value="-1" selected="selected">Please Select</option> -->
								<c:forEach var="unit" items="${units}">
								<%-- <option value="${unit.id}">${unit.unitName}</option> --%>
								<c:choose>
										 <c:when test="${discTickets.unitId.id==unit.id}">
												<option value="${unit.id}" selected="selected">${unit.unitName}</option>
										 </c:when>
										 <c:otherwise>
										 	<option value="${unit.id}">${unit.unitName}</option>
										 </c:otherwise>
									</c:choose>
								 </c:forEach> 
						</select></td>
						<td align="right">Region :<span class="astric">*</span></td>
						<td align="left"><select name="region.id" id="regionId"
							class="required comboselect check">
								<c:choose>
										 <c:when test="${not empty discTickets.region.id}">
												<option value="${discTickets.region.id}" selected="selected">${discTickets.region.regionName}</option>
										 </c:when>
										 <c:otherwise>
										 	<option value="-1">Please Select</option>
										 </c:otherwise>
									</c:choose>
						</select></td>
						<td align="right">Assignee Name :<span class="astric">*</span></td>
						<td align="left"><select name="assigneeId.employeeId"
							class="required comboselect check" id="assigneeNameId" onchange="getModuleByEmployee(this);"> 
							<option value="-1" selected="selected">Please Select</option>
						<%-- <option selected="selected" value="${catickets.assigneeId}">${catickets.assigneeId.employeeName}</option> --%>  
								<c:forEach var="resource" items="${resources}">
								<%-- <option value="${resource.employeeId}">${resource.employeeName}</option> --%>
								<c:choose>
										 <c:when test="${discTickets.assigneeId.employeeId==resource.employeeId}">
												<option value="${resource.employeeId}" selected="selected">${resource.employeeName}</option>
										 </c:when>
										 <c:otherwise>
										 	<option value="${resource.employeeId}">${resource.employeeName}</option>
										 </c:otherwise>
									</c:choose>
								 </c:forEach> 
						</select></td>
						<td width="13%" align="right">Module :</td>
						<td align="left"><select name="moduleId.id"
							class="required comboselect check" id="moduleId" onchange="moduleProcessFunction(this)">
							<!-- <option value="-1" selected="selected">Please Select</option> -->
							<%-- <option selected="selected" value="${catickets.moduleId.id}">${catickets.moduleId.moduleName}</option> --%> 
								 <c:forEach var="project" items="${projects}">
									 <c:choose>
										 <c:when test="${discTickets.moduleId.id==project.id}">
												<option value="${project.id}" selected="selected">${project.moduleName}</option>
										 </c:when>
										 <%-- <c:otherwise>
										 	<option value="${project.id}">${project.moduleName}</option>
										 </c:otherwise> --%>
									</c:choose>
								</c:forEach>
								
						</select></td>
					</tr>
					<tr>
						<td align="right">Landscape :<span class="astric">*</span></td>
						<td align="left"><select name="landscapeId.id"
							class="required comboselect check" id="landscape">
							<!-- <option value="-1" selected="selected">Please Select</option> -->
								<c:forEach var="landscape" items="${landscapes}">
								<%-- <option value="${landscape.id}">${landscape.landscapeName}</option> --%>
									<c:choose>
										 <c:when test="${discTickets.landscapeId.id==landscape.id}">
												<option value="${landscape.id}" selected="selected">${landscape.landscapeName}</option>
										 </c:when>
										 <c:otherwise>
										 	<option value="${landscape.id}">${landscape.landscapeName}</option>
										 </c:otherwise>
									</c:choose>
								 </c:forEach>
						</select></td>
						<td align="right">Solution Ready For Review : <span
							class="astric">*</span></td>
						<td align="left"><select name="solutionReadyForReview"
							id="solutionReadyForReviewId" class="required comboselect check">
								<!-- <option value="-1">Please Select</option>
								<option value="Yes">Yes</option>
								<option value="No">No</option>
								<option value="N.A.">N.A.</option> -->
								<c:choose>
									<c:when test="${discTickets.solutionReadyForReview == 'Yes'}">
										<option value="Yes" selected="selected">Yes</option>
										<option value="No">No</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:when test="${discTickets.solutionReadyForReview == 'N.A.'}">
										<option value="Yes">Yes</option>
										<option value="No">No</option>
										<option value="N.A." selected="selected">N.A.</option>
									</c:when>
									<c:otherwise>
										<option value="Yes">Yes</option>
										<option value="N.A.">N.A.</option>
										<option value="No" selected="selected">No</option>
									</c:otherwise>
								</c:choose>
						</select></td>
						<td align="right">Aging :<span class="astric">*</span></td>
						<td align="left"><select disabled name="aging" id="agingId">
								<option value="${catickets.aging}">${catickets.aging}</option>
						</select></td>

						<td align="right"></td>
						<td align="left"></td>
					</tr>
					<tr>
						<td align="right">SLA Missed : <span class="astric">*</span></td>
						<td align="left"><select disabled name="slaMissed"
							id="slaMissedId">
								<option value="${catickets.slaMissed }">${catickets.slaMissed }</option>
						</select></td>
						<td align="right">Reason for SLA Missed : <span
							class="astric">*</span></td>
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
						<td align="right">Days Open :<span class="astric">*</span></td>
						<td align="left">
						<input type="text" disabled value="${catickets.daysOpen}"
									name="daysOpen" id="daysOpen" />
						</td>
						<td align="right">Reopen Ticket Frequency :<span
							class="astric">*</span></td>
						<td align="left"><select disabled name="reopenFrequency"
							id="reopenTicketFrequencyId">
								<option value="${discTickets.reopenFrequency }">${catickets.reopenFrequency }</option>
						</select>
						<td align="right"></td>
						<td align="left"></td>
					</tr>
					<tr>
						<td align="right">Reason For Reopen : <span class="astric">*</span></td>
						<c:choose>
							<c:when test="${discTickets.reopenFrequency>=1 }">
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
								<option value="${catickets.justifiedHopping}">${catickets.justifiedHopping}</option>
						</select></td>
						<td align="right">Reason For Hopping : <span class="astric">*</span></td>
						<td align="left"><input type="text" value="${catickets.reasonForHopping}"
							name="reasonForHopping" id="reasonForHoppingId" disabled /></td>
						<td align="right">Group :<span class="astric">*</span></td>
						<td align="left"><select name="groupName" id="groupId"
							class="required comboselect check">
							        	<option value="-1" selected="selected">Please Select</option>
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
							
							<c:choose>
								<c:when test="${empty discTickets.creationDate}">
									<fmt:formatDate value="${discTickets.creationDate}" var="parsedCreationDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedCreationDate}" name="creationDate"
										id="creationDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('creationDateId','yyyyMMdd','arrow','true', 12, 'true','past')"  />
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${discTickets.creationDate}" var="parsedCreationDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedCreationDate}" name="creationDate"
										id="creationDateId" maxlength="25" size="25" readonly="readonly"  style="background-color: #DCDCDC;"/>
								</c:otherwise>
							</c:choose>
							
							</div>
						</td>
						<td width="11%" align="right">Acknowledged Date & Time Stamp
							:<span class="astric">*</span>
						</td>
						<td width="18%" align="left">
							<div class="positionRel">
							
							<c:choose>
								<c:when test="${empty discTickets.acknowledgedDate}">
									<fmt:formatDate value="${discTickets.acknowledgedDate}" var="parsedAcknowledgedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedAcknowledgedDate}" name="acknowledgedDate"
									id="acknowledgedDateId" maxlength="25" size="25"
									onclick="javascript:NewCssCal ('acknowledgedDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${discTickets.acknowledgedDate}" var="parsedAcknowledgedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedAcknowledgedDate}" name="acknowledgedDate"
									id="acknowledgedDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"/>
								</c:otherwise>
							</c:choose>
							</div>
						</td>
						<td width="11%" align="right">Requirement completed Date &
							Time Stamp :<span class="astric">*</span>
						</td>
						<td width="18%" align="left">
							<div class="positionRel" id="reqCompleteDivId">
							
							<c:choose>
								<c:when test="${not empty discTickets.requiredCompletedDate || discTickets.reqCompleteFlag == 'N.A.'}">
									<fmt:formatDate value="${discTickets.requiredCompletedDate}" var="parsedRequiredCompletedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedRequiredCompletedDate }" name="requiredCompletedDate"
									id="requiredCompletedDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"/><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${discTickets.requiredCompletedDate}" var="parsedRequiredCompletedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedRequiredCompletedDate }" name="requiredCompletedDate"
									id="requiredCompletedDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('requiredCompletedDateId','yyyyMMdd','arrow','true', 12, 'true','past')" /><br>
								</c:otherwise>
							</c:choose>
								<br> <select class="required comboselect check"
									name="reqCompleteFlag" id="reqCompleteFlagId">
									<c:choose>
										<c:when test="${discTickets.reqCompleteFlag == 'Yes' }">
											<option	value="Yes" selected="selected">Yes</option>
											<option value="No">No</option>
											<option value="N.A.">N.A.</option>
										</c:when>
										<c:when test="${discTickets.reqCompleteFlag == 'No' }">
											<option	value="Yes">Yes</option>
											<option value="No" selected="selected">No</option>
											<option value="N.A.">N.A.</option>
										</c:when>
										<c:otherwise>
											<option	value="Yes">Yes</option>
											<option value="No">No</option>
											<option value="N.A." selected="selected">N.A.</option>
										</c:otherwise>
									</c:choose></select>
							</div>
						</td>
						<td width="11%" align="right">Analysis completed Date & Time
							Stamp :<span class="astric">*</span>
						</td>
						<td width="18%" align="left">
							<div class="positionRel" id="analysisCompleteDivId">
							<c:choose>
								<c:when test="${not empty discTickets.analysisCompletedDate || discTickets.analysisCompleteFlag == 'N.A.'}">
									<fmt:formatDate value="${discTickets.analysisCompletedDate}" var="parsedAnalysisCompletedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedAnalysisCompletedDate }" name="analysisCompletedDate"
									id="analysisCompletedDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;" /><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${discTickets.analysisCompletedDate}" var="parsedAnalysisCompletedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedAnalysisCompletedDate }" name="analysisCompletedDate"
									id="analysisCompletedDateId" maxlength="25" size="25"
      							 onclick="javascript:NewCssCal ('analysisCompletedDateId','yyyyMMdd','arrow','true', 12, 'true','past')" /><br>
								</c:otherwise>
							</c:choose>
							
								<br> <select class="required comboselect check"
									name="analysisCompleteFlag" id="analysisCompleteFlagId">
									<c:choose>
										<c:when test="${discTickets.analysisCompleteFlag == 'Yes' }">
											<option	value="Yes" selected="selected">Yes</option>
											<option value="No">No</option>
											<option value="N.A.">N.A.</option>
										</c:when>
										<c:when test="${discTickets.analysisCompleteFlag == 'No' }">
											<option	value="Yes">Yes</option>
											<option value="No" selected="selected">No</option>
											<option value="N.A.">N.A.</option>
										</c:when>
										<c:otherwise>
											<option	value="Yes">Yes</option>
											<option value="No">No</option>
											<option value="N.A." selected="selected">N.A.</option>
										</c:otherwise>
									</c:choose></select>
							</div>
						</td>
					</tr>
					</thead>
					<!-- <tbody id="resourcerequestTableBody"> -->
						<tr>
								<td width="11%" align="right">Solution Developed completed
								Date & Time Stamp :<span class="astric">*</span>
							</td>
							<td width="18%" align="left">
								<div class="positionRel" id="solutiondevelopedDivId">
								<c:choose>
								<c:when test="${not empty discTickets.solutiondevelopedDate || discTickets.solutionDevelopedFlag == 'N.A.'}">
									<fmt:formatDate value="${discTickets.solutiondevelopedDate}" var="parsedSolutionDevelopedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionDevelopedDate }" name="solutiondevelopedDate"
										id="solutiondevelopedDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"
      							  /><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${discTickets.solutiondevelopedDate}" var="parsedSolutionDevelopedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionDevelopedDate }" name="solutiondevelopedDate"
										id="solutiondevelopedDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('solutiondevelopedDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/><br>
								</c:otherwise>
							</c:choose>
								
									<br> <select class="required comboselect check"
										name="solutionDevelopedFlag" id="solutionDevelopedFlagId">
										<c:choose>
										<c:when test="${discTickets.solutionDevelopedFlag == 'Yes' }">
											<option	value="Yes" selected="selected">Yes</option>
											<option value="No">No</option>
											<option value="N.A.">N.A.</option>
										</c:when>
										<c:when test="${discTickets.solutionDevelopedFlag == 'No' }">
											<option	value="Yes">Yes</option>
											<option value="No" selected="selected">No</option>
											<option value="N.A.">N.A.</option>
										</c:when>
										<c:otherwise>
											<option	value="Yes">Yes</option>
											<option value="No">No</option>
											<option value="N.A." selected="selected">N.A.</option>
										</c:otherwise>
									</c:choose></select>
								</div>
							</td>
						
							<td width="11%" align="right">Solution review Date & Time
								Stamp :<span class="astric">*</span>
							</td>
							<td width="18%" align="left">
								<div class="positionRel" id="solutionReviewDivId">
								<c:choose>
								<c:when test="${not empty discTickets.solutionreViewDate || discTickets.solutionReadyForReview == 'N.A.'}">
									<fmt:formatDate value="${discTickets.solutionreViewDate}" var="parsedSolutionReviewDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionReviewDate }" name="solutionreViewDate"
										id="solutionreViewDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;" /><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${discTickets.solutionreViewDate}" var="parsedSolutionReviewDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionReviewDate }" name="solutionreViewDate"
										id="solutionreViewDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"  /><br>
								</c:otherwise>
							</c:choose>
								
									<%-- <br> <select class="required comboselect check"
										name="solutionReviewFlag" id="solutionReviewFlagId">
										<c:choose>
										<c:when test="${catickets.solutionReadyForReview == 'Yes' }">
											<option	value="Yes" selected="selected">Yes</option>
											<option value="No">No</option>
											<option value="N.A.">N.A.</option>
										</c:when>
										<c:when test="${catickets.solutionReadyForReview == 'No' }">
											<option	value="Yes">Yes</option>
											<option value="No" selected="selected">No</option>
											<option value="N.A.">N.A.</option>
										</c:when>
										<c:otherwise>
											<option	value="Yes">Yes</option>
											<option value="No">No</option>
											<option value="N.A." selected="selected">N.A.</option>
										</c:otherwise>
									</c:choose>
										</select> --%>
								</div>
							</td>
							
							<td width="11%" align="right">Solution (User Acceptance)
								accepted Date & Time Stamp :<span class="astric">*</span>
							</td>
							<td width="18%" align="left">
								<div class="positionRel" id="solutionAcceptedDivId">
								<c:choose>
								<c:when test="${not empty discTickets.solutionAcceptedDate || discTickets.solutionAcceptedFlag == 'N.A.'}">
									<fmt:formatDate value="${discTickets.solutionAcceptedDate}" var="parsedSolutionAcceptedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionAcceptedDate }" name="solutionAcceptedDate"
										id="solutionAcceptedDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"
      							  /><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${discTickets.solutionAcceptedDate}" var="parsedSolutionAcceptedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionAcceptedDate }" name="solutionAcceptedDate"
										id="solutionAcceptedDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('solutionAcceptedDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/><br>
								</c:otherwise>
							</c:choose>
								
									<br> <select class="required comboselect check"
										name="solutionAcceptedFlag" id="solutionAcceptedFlagId">
										<c:choose>
										<c:when test="${discTickets.solutionAcceptedFlag == 'Yes' }">
											<option	value="Yes" selected="selected">Yes</option>
											<option value="No">No</option>
											<option value="N.A.">N.A.</option>
										</c:when>
										<c:when test="${discTickets.solutionAcceptedFlag == 'No' }">
											<option	value="Yes">Yes</option>
											<option value="No" selected="selected">No</option>
											<option value="N.A.">N.A.</option>
										</c:when>
										<c:otherwise>
											<option	value="Yes">Yes</option>
											<option value="No">No</option>
											<option value="N.A." selected="selected">N.A.</option>
										</c:otherwise>
									</c:choose>
										</select>
								</div>
							</td>
							<td width="11%" align="right">Closed pending customer
								approval Date & Time Stamp :<span class="astric">*</span>
							</td>
							<td width="18%" align="left">
								<div class="positionRel" id="closePendingCustomerApprovalDivId">
								<c:choose>
								<c:when test="${not empty discTickets.closePendingCustomerApprovalDate}">
									<fmt:formatDate value="${discTickets.closePendingCustomerApprovalDate}" var="parsedClosePendingCustomerApprovalDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedClosePendingCustomerApprovalDate }"
										name="closePendingCustomerApprovalDate"
										id="closePendingCustomerApprovalDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"
      							/><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${discTickets.closePendingCustomerApprovalDate}" var="parsedClosePendingCustomerApprovalDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedClosePendingCustomerApprovalDate }"
										name="closePendingCustomerApprovalDate"
										id="closePendingCustomerApprovalDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('closePendingCustomerApprovalDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/><br>
								</c:otherwise>
							</c:choose>
								
									<br> <select class="required comboselect check"
										name="customerApprovalFlag" id="customerApprovalFlagId">
										<c:choose>
										<c:when test="${discTickets.customerApprovalFlag == 'Yes' }">
											<option	value="Yes" selected="selected">Yes</option>
											<option value="No">No</option>
											<option value="N.A.">N.A.</option>
										</c:when>
										<c:when test="${discTickets.customerApprovalFlag == 'No' }">
											<option	value="Yes">Yes</option>
											<option value="No" selected="selected">No</option>
											<option value="N.A.">N.A.</option>
										</c:when>
										<c:otherwise>
											<option	value="Yes">Yes</option>
											<option value="No">No</option>
											<option value="N.A." selected="selected">N.A.</option>
										</c:otherwise>
									</c:choose>
										</select>
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
							<c:choose>
								<c:when test="${discTickets.problemManagementFlag == 'Yes' }">
									<option value="No">No</option>
									<option value="Yes" selected="selected">Yes</option>
									<option value="N.A.">N.A.</option>
								</c:when>
								<c:when test="${discTickets.problemManagementFlag == 'No' }">
									<option value="No" selected="selected">No</option>
									<option value="Yes">Yes</option>
									<option value="N.A.">N.A.</option>
								</c:when>
								<c:when test="${discTickets.problemManagementFlag == 'N.A.' }">
									<option value="No">No</option>
									<option value="Yes">Yes</option>
									<option value="N.A." selected="selected">N.A.</option>
								</c:when>	
								<c:otherwise>
									<option value="No" selected="selected">No</option>
									<option value="Yes">Yes</option>
									<option value="N.A.">N.A.</option>
								</c:otherwise>								
							</c:choose>
								
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
							<c:forEach var="processes" items="${process }">
							<option value="${processes.id}">${processes.processName}</option>
								<%-- <c:choose>
										 <c:when test="${discTickets.process.id == processes.id}">
												<option value="${discTickets.process.id}" selected="selected">${discTickets.process.processName}</option>
										 </c:when>
										 <c:otherwise>
										 	<option value="${processes.id}">${processes.processName}</option>
										 </c:otherwise>
									</c:choose> --%>
								</c:forEach>
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
							<c:forEach var="subprocesses" items="${subprocess }">
							<option value="${subprocess.id}" selected="selected">${subprocess.processName}</option>
							<%-- <c:choose>
								<c:when test="${discTickets.subProcess.id == subprocesses.id}">
									<option value="${discTickets.subProcess.id}" selected="selected">${discTickets.subProcess.subProcessName}</option>
								</c:when>
								<c:otherwise>
								 	<option value="${subprocess.id}" selected="selected">${subprocess.processName}</option>
								</c:otherwise>
							</c:choose> --%>
							</c:forEach>
								<!-- <option value="">Select One</option>
								<option value="SP1">Sub P1</option>
								<option value="SP2">Sub P2</option>
								<option value="SP3">Sub P3</option> -->
						</select></td>
						<td width="11%" align="right">Root Cause :<span
							class="astric">*</span>
						</td>
						<td align="left"><select name="rootCause.id"
							class="required comboselect check" id="rootCauseId">
										 <c:if test="${empty discTickets.rootCause.id}">
												<option value="-1" selected="selected">Please Select</option>
										 </c:if>
								<c:forEach var="rc" items="${rootcause}">
								<c:choose>
									<c:when  test="${discTickets.rootCause.id == rc.id}">
										<option value="${rc.id}" selected="selected">${rc.rootCause}</option>
									</c:when>
									<c:otherwise>
										<option value="${rc.id}">${rc.rootCause}</option>
									</c:otherwise>
								</c:choose>
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
								<input type="text" value="${discTickets.parentTicketNo }" name="parentTicketNo"
									id="parentTicketNo" maxlength="10" />
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
									<option value="No" selected="selected">No</option>
										 	<option value="Yes">Yes</option>
									<%-- <c:choose>
										 <c:when test="${discTickets.justifiedProblemManagement=='Yes'}">
												<option value="Yes">Yes</option>
												<option value="No" selected="selected">No</option>
										 </c:when>
										 <c:otherwise>
										 	<option value="No" selected="selected">No</option>
										 	<option value="Yes">Yes</option>
										 </c:otherwise>
									</c:choose> --%>
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
		<br>
		<br>
		<div class="button_div">
			<div style="float: right; margin-top: 5px; padding-top: 20px;">
				<input type="button" name="save" value="Save" id="save" onclick="return validateForm(false,'../../caticket/isTicketAlreadyExist');"> <input
					type="button" name="back" value="Back">
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

