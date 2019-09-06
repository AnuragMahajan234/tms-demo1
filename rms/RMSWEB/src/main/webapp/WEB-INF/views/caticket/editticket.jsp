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
/* window.setInterval(function () {
	var date=$('#acknowledgedDateId').val();
	var reqDate=$('#requiredCompletedDateId').val();
	 
    if(date==""){
    	document.getElementById("reqCompleteFlagId").disabled=true;
    }else{
    	if(reqDate==null || reqDate==""){
    	document.getElementById("reqCompleteFlagId").disabled=false;
    	}
    }
}, 300); */
var isT3Open = false;
var isDefectLogOpen = false;
var isSolRevOpen = false;
var isCropOpen = false;
var isReworkOpen = false;

function backPage() {
  $('#caTicketFormId').attr("action", "${pageContext.request.contextPath}/caticket/back");
  $("#caTicketFormId").submit();
  //history.go(-2);
  //window.history.back();
}

function clearDate(){
	$('#acknowledgedDateId').val("");
}

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
	
function isNumberKey(evt){
	  if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
		    event.preventDefault();
		  }
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
		
		/* if(document.getElementById("solutionReadyForReviewId").value == 'No'){
			$("#solutionReviewMainId").hide();
		} */
		
		// disabling requirement analysis and unit testing hours if property is high
		
		if(document.getElementById("priority").value != "2 - High"){
			$('#requirementAnalysisHoursId').attr('readonly', true);
			 $('#requirementAnalysisHoursId').css({'background-color' : '#DEDEDE'});
			$('#unitTestingHoursId').attr('readonly', true);
			$('#unitTestingHoursId').css({'background-color' : '#DEDEDE'});
		}
		
		$(document).on("change", "#priority", function(){
			var newVal = $(this).val();
			if(newVal == "2 - High"){
				$('#requirementAnalysisHoursId').attr('readonly', false);
				$('#requirementAnalysisHoursId').css({'background-color' : '#FFFFFF'});
				$('#unitTestingHoursId').attr('readonly', false);
				$('#unitTestingHoursId').css({'background-color' : '#FFFFFF'});
				
			}else{
				$('#requirementAnalysisHoursId').attr('readonly', true);
				 $('#requirementAnalysisHoursId').css({'background-color' : '#DEDEDE'});
				$('#unitTestingHoursId').attr('readonly', true);
				$('#unitTestingHoursId').css({'background-color' : '#DEDEDE'});
			}
		});
		$('#contributionTable').dataTable({
			  "aoColumns": [ 
			                {"sClass": "hide_column"},
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" }
			                /* {"bVisible":    true } */
			              ]  
			          });
		$('#solutionReviewTable').dataTable({
			  "aoColumns": [ 
			                {"sClass": "hide_column"},
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" }
			                /* {"bVisible":    true } */
			              ]  
			          });
		$('#defectLogTable').dataTable({
			  "aoColumns": [ 
			                {"sClass": "hide_column"},
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" }
			                /* {"bVisible":    true } */
			              ]  
			          });
		$('#cropTable').dataTable({
			  "aoColumns": [ 
			                {"sClass": "hide_column"},
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" }
			                /* {"bVisible":    true } */
			              ]  
			          });
		$('#reworkTable').dataTable({
			  "aoColumns": [ 
			                {"sClass": "hide_column"},
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" },
			                {"sClass": "show_column" }
			                /* {"bVisible":    true } */
			              ]  
			          });
		
		var solutionReviewTableBodyLength = $("#solutionReviewTableBody tr").length;
		if(solutionReviewTableBodyLength > 0){
			var solutionReviewDataValue = $("#solutionReviewTableBody tr")
			.find("td:first").html();
			if(document.getElementById("solutionReadyForReviewId").value != 'Yes' && solutionReviewDataValue != 'No data available in table'){
				showError("\u2022 Solution ready for review should be selected Yes if solution review record is already saved!! <br/> \u2022 Or delete solution review record!!");
			}
		}
		var roleName = document.getElementById("roleId").value;
		var isReviewer = document.getElementById("isReviewerId").value;
		if(document.getElementById("problemManagementId").value == 'No'){
			$( "#problemManagementDivId" ).hide();
		}else{
			$( "#problemManagementDivId" ).show();
		}
		
		if(document.getElementById("t3ContributionId").value == 'Yes'){
			$( "#t3ContributionDivId" ).show();
		}else{
			$( "#t3ContributionDivId" ).hide();
		}
		
		if(roleName == 'ROLE_ADMIN' || roleName == 'ROLE_DEL_MANAGER' || roleName == 'ROLE_BG_ADMIN' || roleName == 'ROLE_MANAGER' || isReviewer == 'true'){
			if(document.getElementById("defectLogId").value == 'Yes'){
				$( "#defectLogDivId" ).show();
			}else{
				$( "#defectLogDivId" ).hide();
			}
			
			if(document.getElementById("cropId").value == 'Yes'){
				$( "#cropDivId" ).show();
			}else{
				$( "#cropDivId" ).hide();
			}
		}
		if(document.getElementById("reworkId").value == 'Yes'){
			$( "#reworkDivId" ).show();
		}else{
			$( "#reworkDivId" ).hide();
		}
		
		$("#priority").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		$("#priority").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		
		$("#reviewer").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		$("#reviewer").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		
		$("#assigneeNameId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		$("#assigneeNameId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		
		$("#moduleId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		$("#moduleId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		
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
		
		/* $("#solutionReadyForReviewId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		$("#solutionReadyForReviewId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
		if((closedDate !=null && closedDate !="") || customerApprovalFlag == 'N.A.'){
			
			/* $("#reqCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			$("#reqCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
			
			$("#analysisCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			$("#analysisCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
			
			$("#solutionDevelopedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			$("#solutionDevelopedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
			
			/* $("#solutionReviewFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			$("#solutionReviewFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
			
			/* $("#solutionAcceptedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			$("#solutionAcceptedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
			
			$("#customerApprovalFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			$("#customerApprovalFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
			if(roleName == 'ROLE_ADMIN' || roleName == 'ROLE_DEL_MANAGER' || roleName == 'ROLE_BG_ADMIN' || roleName == 'ROLE_MANAGER' || isReviewer == 'true'){
			
			}else{document.getElementById("reqCompleteFlagId").disabled=true;
			document.getElementById("analysisCompleteFlagId").disabled=true;
			document.getElementById("solutionDevelopedFlagId").disabled=true;
			document.getElementById("solutionAcceptedFlagId").disabled=true;
			document.getElementById("customerApprovalFlagId").disabled=true;
			}
			
		}else{
			
			if((solutionAcceptanceDate !=null && solutionAcceptanceDate !="") || solutionAcceptedFlag == 'N.A.'){
				/* $("#reqCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
				$("#reqCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
				
				$("#analysisCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
				$("#analysisCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
				
				$("#solutionDevelopedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
				$("#solutionDevelopedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
				
				/* $("#solutionReviewFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
				$("#solutionReviewFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
				
				/* $("#solutionAcceptedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
				$("#solutionAcceptedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
				
				$("#customerApprovalFlagId").next("span").find("input, a").removeAttr("disabled").removeClass("disableInpDd");
				$("#customerApprovalFlagId").next("span").find("div#ddIcon").remove(); */
				
				$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled");
				if(roleName == 'ROLE_ADMIN' || roleName == 'ROLE_DEL_MANAGER' || roleName == 'ROLE_BG_ADMIN' || roleName == 'ROLE_MANAGER' || isReviewer == 'true'){
				
				}else{document.getElementById("reqCompleteFlagId").disabled=true;
				document.getElementById("analysisCompleteFlagId").disabled=true;
				document.getElementById("solutionDevelopedFlagId").disabled=true;
				document.getElementById("solutionAcceptedFlagId").disabled=true;
				}
				document.getElementById("customerApprovalFlagId").disabled=false;
			}else{
				
				if((solutionReviewDate !=null && solutionReviewDate !="")/*  || solutionReadyForReviewId == 'N.A.' || solutionReadyForReviewId == 'Yes' */){
					
					/* $("#reqCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
					$("#reqCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
					
					$("#analysisCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
					$("#analysisCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
					
					$("#solutionDevelopedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
					$("#solutionDevelopedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
					
					/* $("#solutionReviewFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
					$("#solutionReviewFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
					
					/* $("#solutionAcceptedFlagId").next("span").find("input, a").removeAttr("disabled").removeClass("disableInpDd");
					$("#solutionAcceptedFlagId").next("span").find("div#ddIcon").remove();
					
					$("#customerApprovalFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
					$("#customerApprovalFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
					
					$("#solutionAcceptedDateId").attr("disabled", "disabled");
					$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled");
					if(roleName == 'ROLE_ADMIN' || roleName == 'ROLE_DEL_MANAGER' || roleName == 'ROLE_BG_ADMIN' || roleName == 'ROLE_MANAGER' || isReviewer == 'true'){
					
					}else{document.getElementById("reqCompleteFlagId").disabled=true;
					document.getElementById("analysisCompleteFlagId").disabled=true;
					document.getElementById("solutionDevelopedFlagId").disabled=true;
					document.getElementById("customerApprovalFlagId").disabled=true;
					}
					document.getElementById("solutionAcceptedFlagId").disabled=false;
					
					
				}else{
					
					if((solutionDevelopedDate !=null && solutionDevelopedDate !="") || solutionDevelopedFlag == 'N.A.'){
						
						/* $("#reqCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
						$("#reqCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
						
						$("#analysisCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
						$("#analysisCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
						
						$("#solutionDevelopedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
						$("#solutionDevelopedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
						
						$("#solutionReadyForReviewId").next("span").find("input, a").removeAttr("disabled").removeClass("disableInpDd");
						$("#solutionReadyForReviewId").next("span").find("div#ddIcon").remove(); */
						
						/* $("#solutionReviewFlagId").next("span").find("input, a").removeAttr("disabled").removeClass("disableInpDd");
						$("#solutionReviewFlagId").next("span").find("div#ddIcon").remove(); */
						
						/* $("#solutionAcceptedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
						$("#solutionAcceptedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
						
						$("#customerApprovalFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
						$("#customerApprovalFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
						
						$("#solutionreViewDateId").attr("disabled", "disabled");
						$("#solutionAcceptedDateId").attr("disabled", "disabled");
						$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled");
						if(roleName == 'ROLE_ADMIN' || roleName == 'ROLE_DEL_MANAGER' || roleName == 'ROLE_BG_ADMIN' || roleName == 'ROLE_MANAGER' || isReviewer == 'true'){
						
						}else{
							document.getElementById("reqCompleteFlagId").disabled=true;
							document.getElementById("analysisCompleteFlagId").disabled=true;
							document.getElementById("solutionDevelopedFlagId").disabled=true;
						}
						document.getElementById("solutionReadyForReviewId").disabled=false;
						document.getElementById("solutionAcceptedFlagId").disabled=true;
						document.getElementById("customerApprovalFlagId").disabled=true;
						
					}else{
						if((analysisCompletedDate !=null && analysisCompletedDate !="") || analysisCompletedFlag == 'N.A.'){
							/* $("#reqCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
							$("#reqCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
							
							$("#analysisCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
							$("#analysisCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
							
							$("#solutionDevelopedFlagId").next("span").find("input, a").removeAttr("disabled").removeClass("disableInpDd");
							$("#solutionDevelopedFlagId").next("span").find("div#ddIcon").remove(); */
							
							/* $("#solutionReviewFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
							$("#solutionReviewFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
							
							/* $("#solutionAcceptedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
							$("#solutionAcceptedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
							
							$("#customerApprovalFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
							$("#customerApprovalFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
							
							$("#solutiondevelopedDateId").attr("disabled", "disabled");
							$("#solutionreViewDateId").attr("disabled", "disabled");
							$("#solutionAcceptedDateId").attr("disabled", "disabled");
							$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled");
							
							if(roleName == 'ROLE_ADMIN' || roleName == 'ROLE_DEL_MANAGER' || roleName == 'ROLE_BG_ADMIN' || roleName == 'ROLE_MANAGER' || isReviewer == 'true'){
								
							}else{
							document.getElementById("reqCompleteFlagId").disabled=true;
							document.getElementById("analysisCompleteFlagId").disabled=true;
							document.getElementById("solutionAcceptedFlagId").disabled=true;
							document.getElementById("customerApprovalFlagId").disabled=true;
							}
							document.getElementById("solutionDevelopedFlagId").disabled=false;
							
						}else{
							if((requirementCompletedDate !=null && requirementCompletedDate !="") || requirementCompletedFlag == 'N.A.'){
								/* $("#reqCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#reqCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
								
								$("#analysisCompleteFlagId").next("span").find("input, a").removeAttr("disabled").removeClass("disableInpDd");
								$("#analysisCompleteFlagId").next("span").find("div#ddIcon").remove();
								
								$("#solutionDevelopedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#solutionDevelopedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
								
								/* $("#solutionReviewFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#solutionReviewFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
								
								/* $("#solutionAcceptedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#solutionAcceptedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
								
								$("#customerApprovalFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#customerApprovalFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
								
								$("#analysisCompletedDateId").attr("disabled", "disabled");
								$("#solutiondevelopedDateId").attr("disabled", "disabled");
								$("#solutionreViewDateId").attr("disabled", "disabled");
								$("#solutionAcceptedDateId").attr("disabled", "disabled");
								$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled");
								
								if(roleName == 'ROLE_ADMIN' || roleName == 'ROLE_DEL_MANAGER' || roleName == 'ROLE_BG_ADMIN' || roleName == 'ROLE_MANAGER' || isReviewer == 'true'){
									
								}else{
								document.getElementById("reqCompleteFlagId").disabled=true;
								document.getElementById("solutionDevelopedFlagId").disabled=true;
								document.getElementById("solutionAcceptedFlagId").disabled=true;
								document.getElementById("customerApprovalFlagId").disabled=true;
								}
								document.getElementById("analysisCompleteFlagId").disabled=false;
								
							}else{
								/* $("#reqCompleteFlagId").next("span").find("input, a").removeAttr("disabled").removeClass("disableInpDd");
								$("#reqCompleteFlagId").next("span").find("div#ddIcon").remove();
								
								$("#analysisCompleteFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#analysisCompleteFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
								
								$("#solutionDevelopedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#solutionDevelopedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
								
								/* $("#solutionReviewFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#solutionReviewFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
								
								/* $("#solutionAcceptedFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#solutionAcceptedFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
								
								$("#customerApprovalFlagId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
								$("#customerApprovalFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
								
								$("#requiredCompletedDateId").attr("disabled", "disabled");
								$("#analysisCompletedDateId").attr("disabled", "disabled");
								$("#solutiondevelopedDateId").attr("disabled", "disabled");
								$("#solutionreViewDateId").attr("disabled", "disabled");
								$("#solutionAcceptedDateId").attr("disabled", "disabled");
								$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled");
								
								document.getElementById("reqCompleteFlagId").disabled=false;
								if(roleName == 'ROLE_ADMIN' || roleName == 'ROLE_DEL_MANAGER' || roleName == 'ROLE_BG_ADMIN' || roleName == 'ROLE_MANAGER' || isReviewer == 'true'){
									
								}else{
								document.getElementById("analysisCompleteFlagId").disabled=true;
								document.getElementById("solutionDevelopedFlagId").disabled=true;
								document.getElementById("solutionAcceptedFlagId").disabled=true;
								document.getElementById("customerApprovalFlagId").disabled=true;
								}
							}
						}
					}
					
				}
				/* $("#solutionAcceptedDateId").attr("disabled", "disabled");
				$("#solutionAcceptedFlagId").next("span").find("input, a").removeAttr("disabled").removeClass("disableInpDd");
				$("#solutionAcceptedFlagId").next("span").find("div#ddIcon").remove();
				
				$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled");
				$("#customerApprovalFlagId").next("span").find("input, a").attr("disabled", "disabled").val('No').addClass("disableInpDd");
				$("#customerApprovalFlagId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>"); */
			}
			
			
		}
		
		if(document.getElementById("solutionReadyForReviewId").value == 'N.A.'){
			document.getElementById("solutionAcceptedFlagId").disabled=false;
		}
		//Disabling all phases initially....
		
		 /* var analysisComplete = $("#analysisCompleteFlagId").next("span");
		var solutionReview = $("#solutionReviewFlagId").next("span");
		var solutionDeveloped = $("#solutionDevelopedFlagId").next("span");
		var solutionAccepted = $("#solutionAcceptedFlagId").next("span");
		var closedPending = $("#customerApprovalFlagId").next("span");
		
		$("#requiredCompletedDateId").attr("disabled", "disabled");
		$("#analysisCompletedDateId").attr("disabled", "disabled");
		$("#analysisCompleteDivId").attr("disabled", "disabled");
		$("#solutionreViewDateId").attr("disabled", "disabled");
		$("#solutionReviewDivId").attr("disabled", "disabled");
		$("#solutiondevelopedDateId").attr("disabled", "disabled");
		$("#solutionDevelopedDivId").attr("disabled", "disabled");
		$("#solutionAcceptedDateId").attr("disabled", "disabled");
		$("#solutionAcceptedDivId").attr("disabled", "disabled");
		$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled");
		$("#closePendingCustomerApprovalDivId").attr("disabled", "disabled");
		analysisComplete.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		analysisComplete.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		solutionReview.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		solutionReview.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		solutionDeveloped.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		solutionDeveloped.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		solutionAccepted.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		solutionAccepted.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		closedPending.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		closedPending.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");  */
		
		/* if($("#problemManagementId").val() == 'No' || $("#problemManagementId").val() == 'N.A.'){
			$("#problemManagementDivId").hide();
		} */
		
		if($("#t3ContributionId").val() == 'No' || $("#t3ContributionId").val() == 'N.A.'){
			$("#t3ContributionDivId").hide();
		}
		
		/* if($("#solutionReviewId").val() == 'No' || $("#solutionReviewId").val() == 'N.A.'){
			$("#solutionReviewDetaileDivId").hide();
		} */
		
		if($("#defectLogId").val() == 'No' || $("#defectLogId").val() == 'N.A.'){
			$("#defectLogDivId").hide();
		}
		
		if($("#cropId").val() == 'No' || $("#cropId").val() == 'N.A.'){
			$("#cropDivId").hide();
		}
		
		if($("#reworkId").val() == 'No' || $("#reworkId").val() == 'N.A.'){
			$("#reworkDivId").hide();
		}
		
		$("#agingId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		 $("#agingId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		 
		 $("#slaMissedId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		 $("#slaMissedId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		 
		 $('#daysOpen').attr('readonly', 'true'); // mark it as read only
	     $('#daysOpen').css('background-color' , '#DEDEDE'); // change the background color
		 
		 $("#reopenTicketFrequencyId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		 $("#reopenTicketFrequencyId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		 
		 if(document.getElementById("slaMissedId").value != 'Yes'){
			 $("#reasonForSLAMissedId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			 $("#reasonForSLAMissedId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		 }
		 
		 /* if(document.getElementById("justifiedHoppingId").value != 'Yes'){ */
			 $("#reasonForHoppingId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			 $("#reasonForHoppingId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		 /* } */
		 
		 if(document.getElementById("reopenTicketFrequencyId").value < 1){
			 $("#reasonForReopenId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			 $("#reasonForReopenId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		 }
		 
		 /* if(document.getElementById("reopenTicketFrequencyId").value < 1){
			 $('#reasonForReopenId').attr('readonly', 'true'); // mark it as read only
		     $('#reasonForReopenId').css('background-color' , '#DEDEDE'); // change the background color
		 } */
		 
	     $("#justifiedHoppingId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		 $("#justifiedHoppingId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		 
		 $('#reasonForHoppingId').attr('readonly', 'true'); // mark it as read only
	     $('#reasonForHoppingId').css('background-color' , '#DEDEDE'); // change the background color
	     
		/* if(roleName == 'ROLE_USER'){
			 $('#descripId').attr('readonly', 'true'); // mark it as read only
		     $('#descripId').css('background-color' , '#DEDEDE'); // change the background color
		     
		     $("#unit").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			 $("#unit").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");  
				
			 $("#regionId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			 $("#regionId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
			 
			 $("#landscape").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			 $("#landscape").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
			 
			 var groupName = document.getElementById("groupId").value;
			    
			    if (groupName != null && groupName != "" && groupName != "-1") {
			 $("#groupId").next("span").find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
			 $("#groupId").next("span").append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
			    }
		} */
	     /* $('#reqCompleteFlagId').attr("disabled","disabled"); */
		
	});
	
	
	
	$(document).on('click', '#contributionTable a.t3EditEnable', function (e) {
		
		if(isT3Open){
			var text = "Please enter and save the data in new row or cancel it";
			showAlert(text);
			e.preventDefault();
			return;
		}else{
			isT3Open = true;
		//alert("t3Contribution table editing");
		var oContributionTable =  $('#contributionTable').dataTable();
		var nRow = $(this).parents('tr')[0];
		var oData = oContributionTable.fnGetData(nRow);
		//alert("Checking id: "+oData[0]);
		
		var jqTds = $(">td", nRow);
		if(jqTds.length < 1)return;
		jqTds[0].innerHTML = oData[0];
		jqTds[1].innerHTML = oData[1];
		jqTds[2].innerHTML = oData[2];
		jqTds[3].innerHTML = '<input type="text" name="dateContacted" id="dateContId" value="'+oData[3]+'" maxlength="25" size="25" onclick="datepicker(id);">';
		jqTds[4].innerHTML = '<input type="text"  id="t3Description"value="'+oData[4]+'">';
		jqTds[5].innerHTML = '<input type="text" id="reasonHelpId" value="'+oData[5]+'">';
		jqTds[6].innerHTML = '<input type="text"  id="hrTakenId"value="'+oData[6]+'">';
		jqTds[7].innerHTML = '<select name="justified" id="justifiedId" class="required comboselect check"><option value="Yes">Yes</option><option value="No">No</option></select>';
		jqTds[8].innerHTML = '<a id="aT3Id" href="javascript:void(0)" class="t3Edit">Save</a> / <a href="javascript:void(0)" class="t3CancelRow">Cancel</a>';
		jqTds[9].innerHTML = '<a id="delT3Id" href="javascript:void(0)" class="t3DeleteRow">Delete</a>';
		}
		});
	
	$(document).on('click', '#contributionTable a.t3Edit', function (e) {
		/* var value = getInputValue(aData[7]); */
		var oContributionTable =  $('#contributionTable').dataTable();
		var sData = $('*', oContributionTable.fnGetNodes()).serializeArray();	
		var nRow = $(this).parents('tr')[0];
		var oData = oContributionTable.fnGetData(nRow);
		//var idx = $('#contributionTable').DataTable().row(this).index();
		var tr = $(this).closest("tr");
    	var idx = tr.index();
		sData.push({name:"id",value:oData[0]});
		sData.push({name:"caTicket.id",value:document.getElementById("caTicketId").value});
		sData.push({name:"ticketNumber",value:oData[1]});
		//sData.push({name:"dateContacted",value:oData[3]});
		sData.push({name:"description",value:document.getElementById("t3Description").value});
		sData.push({name:"reasonForHelp",value:document.getElementById("reasonHelpId").value});
		sData.push({name:"noOfhoursTaken",value:document.getElementById("hrTakenId").value});
		//sData.push({name:"justified",value:oData[7]});
		
		
		var jsonData ='{';
			$.each(sData, function(i, item) {
					 /* alert("Item: "+item.name+" Value: "+item.value); */ 
					var jsonString = getJsonString(item.name , item.value);
					if(jsonString != null && $.trim(jsonString) != '')
					jsonData += getJsonString(item.name , item.value) +",";
				
			});
			 jsonData = jsonData.slice(0, -1); 
			jsonData +='}';
			startProgress();
			$.ajax({
				type : 'POST',
				dataType : 'json',
				url : '${pageContext.request.contextPath}/caticket/t3Update', 
				/* url : 't3Update/', */
				contentType : "text/html",
				data : jsonData,
				cache : false,
				success : function(data) {
					//stopProgress();
					showSuccess("T3 Contributions Updated Successfully!");
					isT3Open = false;
					//window.location.reload();
					 
					 $('#contributionTable').dataTable().fnUpdate( [
					                                                  ''+data[0].id+'',
					                                                  ''+document.getElementById("caTicketNoID").value+'',
					                                                  ''+document.getElementById("moduleName").value+'',
					                                                  ''+data[0].dateContacted+'',
					                                                  ''+data[0].description+'',
					                                                  ''+data[0].reasonForHelp+'',
					                                                  ''+data[0].noOfhoursTaken+'',
					                                                  ''+data[0].justified+'',
					                                                  '<a href="javascript:void(0)" class="t3EditEnable">Edit</a>',
					                                                  '<a href="javascript:void(0)" class="deleteRowt3">Delete</a>' ], idx ); // Row
					 stopProgress();
				},
				error:function(response){
					stopProgress();
					showError("Somthing happends wrong!!");
				},
			});
		});
	
	$(document).on('click', '#contributionTable a.t3CancelRow', function(e) {
		isT3Open = false;
		e.preventDefault();
		var oContributionTable =  $('#contributionTable').dataTable();
		var nRow = $(this).parents('tr')[0];
		nEditing = nRow;
		restoreRow(oContributionTable, nEditing);
	});
	
	// Editing Solution Review Section
	$(document).on('click', '#solutionReviewTable a.solutionReviewEditEnable', function (e) {
		//alert("t3Contribution table editing");
		if(isSolRevOpen){
			var text = "Please enter and save the data in new row or cancel it";
			showAlert(text);
			e.preventDefault();
			return;
		}else{
			isSolRevOpen = true;
		var oContributionTable =  $('#solutionReviewTable').dataTable();
		var nRow = $(this).parents('tr')[0];
		var oData = oContributionTable.fnGetData(nRow);
		//alert("Checking id: "+oData[0]);
		
		var jqTds = $(">td", nRow);
		if(jqTds.length < 1)return;
		jqTds[0].innerHTML = oData[0];
		jqTds[1].innerHTML = oData[1];
		jqTds[2].innerHTML = oData[2];
		jqTds[3].innerHTML = oData[3];
		jqTds[4].innerHTML = '<input type="text" name="reviewDate" id="reviewDateId" value="'+oData[4]+'" maxlength="25" size="25" onclick="datepicker(id);">';
		jqTds[5].innerHTML = oData[5];
		jqTds[6].innerHTML = oData[6];
		if(oData[7]=='Yes'){
			jqTds[7].innerHTML= '<select name="isTheIssueUnderstandingCorrect" id="issueUnderstandingId" class="required comboselect check"><option value="-1">Please Select</option><option value="Yes" selected="selected">Yes</option><option value="No">No</option></select>';
		}else if (oData[7]=='No') {
			jqTds[7].innerHTML= '<select name="isTheIssueUnderstandingCorrect" id="issueUnderstandingId" class="required comboselect check"><option value="-1">Please Select</option><option value="Yes">Yes</option><option value="No" selected="selected">No</option></select>';
		}else{
			jqTds[7].innerHTML= '<select name="isTheIssueUnderstandingCorrect" id="issueUnderstandingId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Yes">Yes</option><option value="No">No</option></select>';
		}
		/* + '<td align="center"><select name="issueUnderstanding" id="issueUnderstandingSR'+trIDSR+'" class="required comboselect check"><option value="-1">Please Select</option><option value="Yes">Yes</option><option value="No">No</option></select></td>' */
		jqTds[8].innerHTML = '<input type="text" name="alternateSolution" id="alternateSolutionId"value="'+oData[8]+'">';
		if(oData[9]=='Yes'){
			jqTds[9].innerHTML= '<select name="isSolAppropriate" id="isSolAppropriateId" class="required comboselect check"><option value="-1">Please Select</option><option value="Yes" selected="selected">Yes</option><option value="No">No</option></select>';
		}else if (oData[9]=='No') {
			jqTds[9].innerHTML= '<select name="isSolAppropriate" id="isSolAppropriateId" class="required comboselect check"><option value="-1">Please Select</option><option value="Yes">Yes</option><option value="No" selected="selected">No</option></select>';
		}else{
			jqTds[9].innerHTML= '<select name="isSolAppropriate" id="isSolAppropriateId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Yes">Yes</option><option value="No">No</option></select>';
		}
		
		if(oData[10]=='Yes'){
			jqTds[10].innerHTML= '<select name="agreeWithRca" id="rcaId" class="required comboselect check"><option value="-1">Please Select</option><option value="Yes" selected="selected">Yes</option><option value="No">No</option></select>';
		}else if (oData[10]=='No') {
			jqTds[10].innerHTML= '<select name="agreeWithRca" id="rcaId" class="required comboselect check"><option value="-1">Please Select</option><option value="Yes">Yes</option><option value="No" selected="selected">No</option></select>';
		}else{
			jqTds[10].innerHTML= '<select name="agreeWithRca" id="rcaId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Yes">Yes</option><option value="No">No</option></select>';
		}
		
		if(oData[11]=='Complex'){
			jqTds[11].innerHTML= '<select name="rating" id="ratingId" class="required comboselect check"><option value="-1">Please Select</option><option value="Complex" selected="selected">Complex</option><option value="High">High</option><option value="Medium">Medium</option><option value="Regular">Regular</option><option value="Repetitive">Repetitive</option></select>';
		}else if (oData[11]=='High') {
			jqTds[11].innerHTML= '<select name="rating" id="ratingId" class="required comboselect check"><option value="-1">Please Select</option><option value="Complex">Complex</option><option value="High" selected="selected">High</option><option value="Medium">Medium</option><option value="Regular">Regular</option><option value="Repetitive">Repetitive</option></select>';
		}else if (oData[11]=='Medium'){
			jqTds[11].innerHTML= '<select name="rating" id="ratingId" class="required comboselect check"><option value="-1">Please Select</option><option value="Complex">Complex</option><option value="High">High</option><option value="Medium" selected="selected">Medium</option><option value="Regular">Regular</option><option value="Repetitive">Repetitive</option></select>';
		}else if (oData[11]=='Regular'){
			jqTds[11].innerHTML= '<select name="rating" id="ratingId" class="required comboselect check"><option value="-1">Please Select</option><option value="Complex">Complex</option><option value="High">High</option><option value="Medium">Medium</option><option value="Regular" selected="selected">Regular</option><option value="Repetitive">Repetitive</option></select>';
		}else if (oData[11]=='Repetitive'){
			jqTds[11].innerHTML= '<select name="rating" id="ratingId" class="required comboselect check"><option value="-1">Please Select</option><option value="Complex">Complex</option><option value="High">High</option><option value="Medium">Medium</option><option value="Regular">Regular</option><option value="Repetitive" selected="selected">Repetitive</option></select>';
		}else{
			jqTds[11].innerHTML= '<select name="rating" id="ratingId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Complex">Complex</option><option value="High">High</option><option value="Medium">Medium</option><option value="Regular">Regular</option><option value="Repetitive">Repetitive</option></select>';
		}
		
		jqTds[12].innerHTML = '<input type="text" name="comments" id="commentsId" value="'+oData[12]+'">';
		jqTds[13].innerHTML = '<a id="aT3Id" href="javascript:void(0)" class="solRevEdit">Save</a> / <a href="javascript:void(0)" class="solRevCancelRow">Cancel</a>';
		jqTds[14].innerHTML = '<a id="delT3Id" href="javascript:void(0)" class="deleteRow">Delete</a>';
	}
		});
	
	$(document).on('click', '#solutionReviewTable a.solRevEdit', function (e) {
		/* var value = getInputValue(aData[7]); */
		startProgress();
		var oSolutionReviewTable =  $('#solutionReviewTable').dataTable();
		var sData = $('*', oSolutionReviewTable.fnGetNodes()).serializeArray();	
		var nRow = $(this).parents('tr')[0];
		var oData = oSolutionReviewTable.fnGetData(nRow);
		var tr = $(this).closest("tr");
    	var idx = tr.index();
		//sData.push({name:"reviewDate",value:oData[3]});
		sData.push({name:"caTicket.id",value:document.getElementById("caTicketId").value});
		sData.push({name:"id",value:oData[0]});
		/* sData.push({name:"alternateSolution",value:oData[8]});
		sData.push({name:"comments",value:oData[12]});  */
		/* sData.push({name:"dateContacted",value:oData[3]}); */
		/* sData.push({name:"description",value:document.getElementById("t3Description").value});
		sData.push({name:"reasonForHelp",value:document.getElementById("reasonHelpId").value});
		sData.push({name:"noOfhoursTaken",value:document.getElementById("hrTakenId").value}); */
		//sData.push({name:"justified",value:oData[7]});
		
		
		var jsonData ='{';
			$.each(sData, function(i, item) {
					 /* alert("Item: "+item.name+" Value: "+item.value); */ 
					var jsonString = getJsonString(item.name , item.value);
					if(jsonString != null && $.trim(jsonString) != '')
					jsonData += getJsonString(item.name , item.value) +",";
				
			});
			 jsonData = jsonData.slice(0, -1); 
			jsonData +='}';
			$.ajax({
				type : 'POST',
				dataType : 'json',
				url : '${pageContext.request.contextPath}/caticket/solRevUpdate', 
				/* url : 't3Update/', */
				contentType : "text/html",
				data : jsonData,
				cache : false,
				success : function(data) {
					
					showSuccess("Solution Review Updated Successfully!");
					$('#solutionReviewTable').dataTable().fnUpdate( [
					                                                  ''+data[0].id+'',
					                                                  ''+document.getElementById("landscapeName").value+'',
					                                                  ''+document.getElementById("moduleName").value+'',
					                                                  ''+document.getElementById("reviewerName").value+'',
					                                                  ''+data[0].reviewDate+'',
					                                                  ''+document.getElementById("caTicketNoID").value+'',
					                                                  ''+document.getElementById("assigneeName").value+'',
					                                                  ''+data[0].isTheIssueUnderstandingCorrect+'',
					                                                  ''+data[0].alternateSolution+'',
					                                                  ''+data[0].isSolAppropriate+'',
					                                                  ''+data[0].agreeWithRca+'',
					                                                  ''+data[0].rating+'',
					                                                  ''+data[0].comments+'',
					                                                  '<a href="javascript:void(0)" class="solutionReviewEditEnable">Edit</a>',
					                                                  '<a href="javascript:void(0)" class="deleteRowSolutionReview">Delete</a>' ], idx );
					//window.location.reload();
					isSolRevOpen = false;
					stopProgress();
					
				},
				error:function(response){
					stopProgress();
					showError("Something happend wrong!!");
				},
			});
		});
	
	$(document).on('click', '#solutionReviewTable a.solRevCancelRow', function(e) {
		isSolRevOpen = false;
		e.preventDefault();
		var oSolutionReviewTable =  $('#solutionReviewTable').dataTable();
		var nRow = $(this).parents('tr')[0];
		nEditing = nRow;
		restoreRow(oSolutionReviewTable, nEditing);
	});
	
	
	// Enabling defect log fields for editing
	// Editing Solution Review Section
	$(document).on('click', '#defectLogTable a.defectLogEditEnable', function (e) {
		if(isDefectLogOpen){
			var text = "Please enter and save the data in new row or cancel it";
			showAlert(text);
			e.preventDefault();
			return;
		}else{
			isDefectLogOpen = true;
		var oDefectLogTable =  $('#defectLogTable').dataTable();
		var nRow = $(this).parents('tr')[0];
		var oData = oDefectLogTable.fnGetData(nRow);
		//alert("Checking id: "+oData[0]);
		
		var jqTds = $(">td", nRow);
		if(jqTds.length < 1)return;
		jqTds[0].innerHTML = oData[0];
		jqTds[1].innerHTML = oData[1];
		
		if(oData[2] == 'Review'){
			jqTds[2].innerHTML = '<select name="defectType" id="defectTypeId" class="required comboselect check"><option value="-1">Please Select</option><option value="Review" selected="selected">Review</option><option value="Testing">Testing</option></select>';
		}else if (oData[2] == 'Testing') {
			jqTds[2].innerHTML = '<select name="defectType" id="defectTypeId" class="required comboselect check"><option value="-1">Please Select</option><option value="Review">Review</option><option value="Testing" selected="selected">Testing</option></select>';
		}else{
			jqTds[2].innerHTML = '<select name="defectType" id="defectTypeId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Review">Review</option><option value="Testing">Testing</option></select>';
		}
		
		jqTds[3].innerHTML = '<input type="text" name="defectDescription" id="defectDescriptionId" value="'+oData[3]+'">';
		
		if(oData[4] == 'Internal'){
			jqTds[4].innerHTML = '<select name="internalExternal" id="internalExternalId" class="required comboselect check"><option value="-1">Please Select</option><option value="Internal" selected="selected">Internal</option><option value="External">External</option></select>';
		}else if (oData[4] == 'External') {
			jqTds[4].innerHTML = '<select name="internalExternal" id="internalExternalId" class="required comboselect check"><option value="-1">Please Select</option><option value="Internal">Internal</option><option value="External" selected="selected">External</option></select>';
		}else{
			jqTds[4].innerHTML = '<select name="internalExternal" id="internalExternalId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Internal">Internal</option><option value="External">External</option></select>';
		}
		
		if(oData[5] == 'Coding'){
			jqTds[5].innerHTML = '<select name="defectCategory" id="defectCategoryId" class="required comboselect check"><option value="-1">Please Select</option><option value="Coding" selected="selected">Coding</option><option value="Technical">Technical</option><option value="Standards">Standards</option><option value="Design">Design</option><option value="Others">Others</option></select>';
		}else if (oData[5] == 'Technical') {
			jqTds[5].innerHTML = '<select name="defectCategory" id="defectCategoryId" class="required comboselect check"><option value="-1">Please Select</option><option value="Coding">Coding</option><option value="Technical" selected="selected">Technical</option><option value="Standards">Standards</option><option value="Design">Design</option><option value="Others">Others</option></select>';
		}else if (oData[5] == 'Standards') {
			jqTds[5].innerHTML = '<select name="defectCategory" id="defectCategoryId" class="required comboselect check"><option value="-1">Please Select</option><option value="Coding">Coding</option><option value="Technical">Technical</option><option value="Standards" selected="selected">Standards</option><option value="Design">Design</option><option value="Others">Others</option></select>';
		}else if (oData[5] == 'Design') {
			jqTds[5].innerHTML = '<select name="defectCategory" id="defectCategoryId" class="required comboselect check"><option value="-1">Please Select</option><option value="Coding">Coding</option><option value="Technical">Technical</option><option value="Standards">Standards</option><option value="Design" selected="selected">Design</option><option value="Others">Others</option></select>';
		}else if (oData[5] == 'Others') {
			jqTds[5].innerHTML = '<select name="defectCategory" id="defectCategoryId" class="required comboselect check"><option value="-1">Please Select</option><option value="Coding">Coding</option><option value="Technical">Technical</option><option value="Standards">Standards</option><option value="Design">Design</option><option value="Others" selected="selected">Others</option></select>';
		}else{
			jqTds[5].innerHTML = '<select name="defectCategory" id="defectCategoryId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Coding">Coding</option><option value="Technical">Technical</option><option value="Standards">Standards</option><option value="Design">Design</option><option value="Others">Others</option></select>';
		}
		
		if(oData[6] == 'Medium'){
			jqTds[6].innerHTML = '<select name="severity" id="sevirityId" class="required comboselect check"><option value="-1">Please Select</option><option value="Medium" selected="selected">Medium</option><option value="Low">Low</option><option value="High">High</option></select>';
		}else if (oData[6] == 'Low') {
			jqTds[6].innerHTML = '<select name="severity" id="sevirityId" class="required comboselect check"><option value="-1">Please Select</option><option value="Medium">Medium</option><option value="Low" selected="selected">Low</option><option value="High">High</option></select>';
		}else if (oData[6] == 'High') {
			jqTds[6].innerHTML = '<select name="severity" id="sevirityId" class="required comboselect check"><option value="-1">Please Select</option><option value="Medium">Medium</option><option value="Low">Low</option><option value="High" selected="selected">High</option></select>';
		}else{
			jqTds[6].innerHTML = '<select name="severity" id="sevirityId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Medium">Medium</option><option value="Low">Low</option><option value="High">High</option></select>';
		}
		
		if(oData[7] == 'Accepted'){
			jqTds[7].innerHTML = '<select name="defectStatus" id="defectStatusId" class="required comboselect check"><option value="-1">Please Select</option><option value="Accepted" selected="selected">Accepted</option><option value="Closed">Closed</option><option value="WIP">WIP</option></select>';
		}else if (oData[7] == 'Closed') {
			jqTds[7].innerHTML = '<select name="defectStatus" id="defectStatusId" class="required comboselect check"><option value="-1">Please Select</option><option value="Accepted">Accepted</option><option value="Closed" selected="selected">Closed</option><option value="WIP">WIP</option></select>';
		}else if(oData[7] == 'WIP'){
			jqTds[7].innerHTML = '<select name="defectStatus" id="defectStatusId" class="required comboselect check"><option value="-1">Please Select</option><option value="Accepted">Accepted</option><option value="Closed">Closed</option><option value="WIP" selected="selected">WIP</option></select>';
		}else{
			jqTds[7].innerHTML = '<select name="defectStatus" id="defectStatusId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Accepted">Accepted</option><option value="Closed">Closed</option><option value="WIP">WIP</option></select>';
		}
		
		jqTds[8].innerHTML = '<input type="text" name="identifiedDate" id="identifiedDateId" value="'+oData[8]+'" maxlength="25" size="25" onclick="datepicker(id);">';
		jqTds[9].innerHTML = oData[9];
		
		if(oData[10] == 'Analysis'){
			jqTds[10].innerHTML = '<select name="identifiedPhase" id="identifiedPhaseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Analysis" selected="selected">Analysis</option><option value="Coding">Coding</option><option value="Design">Design</option><option value="Post Implementation">Post Implementation</option><option value="Requirements">Requirements</option><option value="Unit Testing">Unit Testing</option><option value="UAT">UAT</option></select>';
		}else if (oData[10] == 'Coding') {
			jqTds[10].innerHTML = '<select name="identifiedPhase" id="identifiedPhaseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Analysis">Analysis</option><option value="Coding" selected="selected">Coding</option><option value="Design">Design</option><option value="Post Implementation">Post Implementation</option><option value="Requirements">Requirements</option><option value="Unit Testing">Unit Testing</option><option value="UAT">UAT</option></select>';
		}else if (oData[10] == 'Design') {
			jqTds[10].innerHTML = '<select name="identifiedPhase" id="identifiedPhaseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Design" selected="selected">Design</option><option value="Post Implementation">Post Implementation</option><option value="Requirements">Requirements</option><option value="Unit Testing">Unit Testing</option><option value="UAT">UAT</option></select>';
		}else if (oData[10] == 'Post Implementation') {
			jqTds[10].innerHTML = '<select name="identifiedPhase" id="identifiedPhaseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Design">Design</option><option value="Post Implementation" selected="selected">Post Implementation</option><option value="Requirements">Requirements</option><option value="Unit Testing">Unit Testing</option><option value="UAT">UAT</option></select>';
		}else if (oData[10] == 'Requirements') {
			jqTds[10].innerHTML = '<select name="identifiedPhase" id="identifiedPhaseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Design">Design</option><option value="Post Implementation">Post Implementation</option><option value="Requirements" selected="selected">Requirements</option><option value="Unit Testing">Unit Testing</option><option value="UAT">UAT</option></select>';
		}else if (oData[10] == 'Unit Testing') {
			jqTds[10].innerHTML = '<select name="identifiedPhase" id="identifiedPhaseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Design">Design</option><option value="Post Implementation">Post Implementation</option><option value="Requirements">Requirements</option><option value="Unit Testing" selected="selected">Unit Testing</option><option value="UAT">UAT</option></select>';
		}else if (oData[10] == 'UAT') {
			jqTds[10].innerHTML = '<select name="identifiedPhase" id="identifiedPhaseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Design">Design</option><option value="Post Implementation">Post Implementation</option><option value="Requirements">Requirements</option><option value="Unit Testing">Unit Testing</option><option value="UAT" selected="selected">UAT</option></select>';
		}else {
			jqTds[10].innerHTML = '<select name="identifiedPhase" id="identifiedPhaseId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Design">Design</option><option value="Post Implementation">Post Implementation</option><option value="Requirements">Requirements</option><option value="Unit Testing">Unit Testing</option><option value="UAT">UAT</option></select>';
		}
		
		jqTds[11].innerHTML = oData[11];
		
		if(oData[12] == 'Analysis'){
			jqTds[12].innerHTML = '<select name="injectedPhase" id="injectedPhaseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Analysis" selected="selected">Analysis</option><option value="Coding">Coding</option><option value="Design">Design</option><option value="Post Implementation">Post Implementation</option><option value="Requirements">Requirements</option><option value="Unit Testing">Unit Testing</option><option value="UAT">UAT</option></select>';
		}else if (oData[12] == 'Coding') {
			jqTds[12].innerHTML = '<select name="injectedPhase" id="injectedPhaseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Analysis">Analysis</option><option value="Coding" selected="selected">Coding</option><option value="Design">Design</option><option value="Post Implementation">Post Implementation</option><option value="Requirements">Requirements</option><option value="Unit Testing">Unit Testing</option><option value="UAT">UAT</option></select>';
		}else if (oData[12] == 'Design') {
			jqTds[12].innerHTML = '<select name="injectedPhase" id="injectedPhaseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Design" selected="selected">Design</option><option value="Post Implementation">Post Implementation</option><option value="Requirements">Requirements</option><option value="Unit Testing">Unit Testing</option><option value="UAT">UAT</option></select>';
		}else if (oData[12] == 'Post Implementation') {
			jqTds[12].innerHTML = '<select name="injectedPhase" id="injectedPhaseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Design">Design</option><option value="Post Implementation" selected="selected">Post Implementation</option><option value="Requirements">Requirements</option><option value="Unit Testing">Unit Testing</option><option value="UAT">UAT</option></select>';
		}else if (oData[12] == 'Requirements') {
			jqTds[12].innerHTML = '<select name="injectedPhase" id="injectedPhaseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Design">Design</option><option value="Post Implementation">Post Implementation</option><option value="Requirements" selected="selected">Requirements</option><option value="Unit Testing">Unit Testing</option><option value="UAT">UAT</option></select>';
		}else if (oData[12] == 'Unit Testing') {
			jqTds[12].innerHTML = '<select name="injectedPhase" id="injectedPhaseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Design">Design</option><option value="Post Implementation">Post Implementation</option><option value="Requirements">Requirements</option><option value="Unit Testing" selected="selected">Unit Testing</option><option value="UAT">UAT</option></select>';
		}else if (oData[12] == 'UAT') {
			jqTds[12].innerHTML = '<select name="injectedPhase" id="injectedPhaseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Design">Design</option><option value="Post Implementation">Post Implementation</option><option value="Requirements">Requirements</option><option value="Unit Testing">Unit Testing</option><option value="UAT" selected="selected">UAT</option></select>';
		}else {
			jqTds[12].innerHTML = '<select name="injectedPhase" id="injectedPhaseId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Design">Design</option><option value="Post Implementation">Post Implementation</option><option value="Requirements">Requirements</option><option value="Unit Testing">Unit Testing</option><option value="UAT">UAT</option></select>';
		}
		
		if(oData[13] == 'Code'){
			jqTds[13].innerHTML = '<select name="workProductName" id="workProductId" class="required comboselect check"><option value="-1">Please Select</option><option value="Code" selected="selected">Code</option><option value="Design Documents">Design Documents</option><option value="Reviews">Reviews</option><option value="Testing">Testing</option></select>';
		}else if (oData[13] == 'Design Documents') {
			jqTds[13].innerHTML = '<select name="workProductName" id="workProductId" class="required comboselect check"><option value="-1">Please Select</option><option value="Code">Code</option><option value="Design Documents" selected="selected">Design Documents</option><option value="Reviews">Reviews</option><option value="Testing">Testing</option></select>';
		}else if (oData[13] == 'Reviews') {
			jqTds[13].innerHTML = '<select name="workProductName" id="workProductId" class="required comboselect check"><option value="-1">Please Select</option><option value="Code">Code</option><option value="Design Documents">Design Documents</option><option value="Reviews" selected="selected">Reviews</option><option value="Testing">Testing</option></select>';
		}else if (oData[13] == 'Testing') {
			jqTds[13].innerHTML = '<select name="workProductName" id="workProductId" class="required comboselect check"><option value="-1">Please Select</option><option value="Code">Code</option><option value="Design Documents">Design Documents</option><option value="Reviews">Reviews</option><option value="Testing" selected="selected">Testing</option></select>';
		}else {
			jqTds[13].innerHTML = '<select name="workProductName" id="workProductId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Code">Code</option><option value="Design Documents">Design Documents</option><option value="Reviews">Reviews</option><option value="Testing">Testing</option></select>';
		}
		
		if(oData[14] == 'Yes'){
			jqTds[14].innerHTML = '<select name="reopened" id="reopenedId" comboselect check"><option value="-1">Please Select</option><option value="Yes" selected="selected">Yes</option><option value="No">No</option></select>';
		}else if (oData[14] == 'No') {
			jqTds[14].innerHTML = '<select name="reopened" id="reopenedId" comboselect check"><option value="-1">Please Select</option><option value="Yes">Yes</option><option value="No" selected="selected">No</option></select>';
		}else{
			jqTds[14].innerHTML = '<select name="reopened" id="reopenedId" comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Yes">Yes</option><option value="No">No</option></select>';
		}
		
		jqTds[15].innerHTML = oData[15];
		jqTds[16].innerHTML = '<input type="text" name="defectRootCause" id="defectRootCauseId" value="'+oData[16]+'">';
		
		if(oData[17] == 'Accidential'){
			jqTds[17].innerHTML = '<select name="categoryOfRootCause" id="categoryOfRootCauseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Accidential" selected="selected">Accidential</option><option value="Insufficient Tools">Insufficient Tools</option><option value="Miscommunication">Miscommunication</option></select>';
		}else if (oData[17] == 'Insufficient Tools') {
			jqTds[17].innerHTML = '<select name="categoryOfRootCause" id="categoryOfRootCauseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Accidential">Accidential</option><option value="Insufficient Tools" selected="selected">Insufficient Tools</option><option value="Miscommunication">Miscommunication</option></select>';
		}else if (oData[17] == 'Miscommunication') {
			jqTds[17].innerHTML = '<select name="categoryOfRootCause" id="categoryOfRootCauseId" class="required comboselect check"><option value="-1">Please Select</option><option value="Accidential">Accidential</option><option value="Insufficient Tools">Insufficient Tools</option><option value="Miscommunication" selected="selected">Miscommunication</option></select>';
		}else{
			jqTds[17].innerHTML = '<select name="categoryOfRootCause" id="categoryOfRootCauseId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Accidential">Accidential</option><option value="Insufficient Tools">Insufficient Tools</option><option value="Miscommunication">Miscommunication</option></select>';
		}
		
		jqTds[18].innerHTML = '<input type="text" name="resolvedBy" id="resolvedById" value="'+oData[18]+'">';
		jqTds[19].innerHTML = '<input type="text" name="resolvedDate" id="resolvedDateId" value="'+oData[19]+'" maxlength="25" size="25" onclick="datepicker(id);">';
		jqTds[20].innerHTML = '<input type="text" name="closedDate" id="closedDateId" value="'+oData[20]+'" maxlength="25" size="25" onclick="datepicker(id);">';
		jqTds[21].innerHTML = '<a id="aT3Id" href="javascript:void(0)" class="defectLogEdit">Save</a> / <a href="javascript:void(0)" class="defectLogCancelRow">Cancel</a>';
		jqTds[22].innerHTML = '<a id="delT3Id" href="javascript:void(0)" class="deleteRow">Delete</a>';
	}
		});
	
	$(document).on('click', '#defectLogTable a.defectLogEdit', function (e) {
		startProgress();
		var oDefectLogTable =  $('#defectLogTable').dataTable();
		var sData = $('*', oDefectLogTable.fnGetNodes()).serializeArray();	
		var nRow = $(this).parents('tr')[0];
		var oData = oDefectLogTable.fnGetData(nRow);
		var tr = $(this).closest("tr");
    	var idx = tr.index();

		sData.push({name:"caTicket.id",value:document.getElementById("caTicketId").value});
		sData.push({name:"id",value:oData[0]});
		
		var jsonData ='{';
			$.each(sData, function(i, item) {
					 /* alert("Item: "+item.name+" Value: "+item.value); */ 
					var jsonString = getJsonString(item.name , item.value);
					if(jsonString != null && $.trim(jsonString) != '')
					jsonData += getJsonString(item.name , item.value) +",";
				
			});
			 jsonData = jsonData.slice(0, -1); 
			jsonData +='}';
			$.ajax({
				type : 'POST',
				dataType : 'json',
				url : '${pageContext.request.contextPath}/caticket/defectLogUpdate', 
				/* url : 't3Update/', */
				contentType : "text/html",
				data : jsonData,
				cache : false,
				success : function(data) {
					
					showSuccess("Defect Log Updated Successfully!");
					isDefectLogOpen = false;
					$('#defectLogTable').dataTable().fnUpdate( [
				                                                  ''+data[0].id+'',
				                                                  ''+document.getElementById("ZREQNo").value+'',
				                                                  ''+data[0].defectType+'',
				                                                  ''+data[0].defectDescription+'',
				                                                  ''+data[0].internalExternal+'',
				                                                  ''+data[0].defectCategory+'',
				                                                  ''+data[0].severity+'',
				                                                  ''+data[0].defectStatus+'',
				                                                  ''+data[0].identifiedDate+'',
				                                                  ''+data[0].identifiedBy+'',
				                                                  ''+data[0].identifiedPhase+'',
				                                                  ''+document.getElementById("moduleName").value+'',
				                                                  ''+data[0].injectedPhase+'',
				                                                  ''+data[0].workProductName+'',
				                                                  ''+data[0].reopened+'',
				                                                  ''+document.getElementById("caTicketNoID").value+'',
				                                                  ''+data[0].defectRootCause+'',
				                                                  ''+data[0].categoryOfRootCause+'',
				                                                  ''+data[0].resolvedBy+'',
				                                                  ''+data[0].resolvedDate+'',
				                                                  ''+data[0].closedDate+'',
				                                                  '<a href="javascript:void(0)" class="defectLogEditEnable">Edit</a>',
				                                                  '<a href="javascript:void(0)" class="deleteRowDefectLog">Delete</a>' ], idx );
					
					stopProgress();
				},
				error:function(response){
					stopProgress();
					showError("Something happend wrong!");
				},
			});
		});
	
	$(document).on('click', '#defectLogTable a.defectLogCancelRow', function(e) {
		isDefectLogOpen = false;
		e.preventDefault();
		var oDefectLogTable =  $('#defectLogTable').dataTable();
		var nRow = $(this).parents('tr')[0];
		nEditing = nRow;
		restoreRow(oDefectLogTable, nEditing);
	});
	
	// Enabling Crop fields for editing
	$(document).on('click', '#cropTable a.cropEditEnable', function (e) {
		if(isCropOpen){
			var text = "Please enter and save the data in new row or cancel it";
			showAlert(text);
			e.preventDefault();
			return;
		}else{
			isCropOpen = true;
		var oCropTable =  $('#cropTable').dataTable();
		var nRow = $(this).parents('tr')[0];
		var oData = oCropTable.fnGetData(nRow);
		//alert("Checking id: "+oData[0]);
		
		var jqTds = $(">td", nRow);
		if(jqTds.length < 1)return;
		jqTds[0].innerHTML = oData[0];
		jqTds[1].innerHTML = '<input type="text" name="title" id="titleId" value="'+oData[1]+'">';
		jqTds[2].innerHTML = oData[2];
		jqTds[3].innerHTML = '<input type="text" name="description" id="descriptionId" value="'+oData[3]+'">';
		
		if(oData[4] == 'Resource Optimization'){
			jqTds[4].innerHTML = '<select name="source" id="sourceId" class="required comboselect check"><option value="-1">Please Select</option><option value="Resource Optimization" selected="selected">Resource Optimization</option><option value="Process Improvement">Process Improvement</option><option value="Break Fix">Break Fix</option></select>';
		}else if (oData[4] == 'Process Improvement') {
			jqTds[4].innerHTML = '<select name="source" id="sourceId" class="required comboselect check"><option value="-1">Please Select</option><option value="Resource Optimization">Resource Optimization</option><option value="Process Improvement" selected="selected">Process Improvement</option><option value="Break Fix">Break Fix</option></select>';
		}else if(oData[4] == 'Break Fix'){
			jqTds[4].innerHTML = '<select name="source" id="sourceId" class="required comboselect check"><option value="-1">Please Select</option><option value="Resource Optimization">Resource Optimization</option><option value="Process Improvement">Process Improvement</option><option value="Break Fix" selected="selected">Break Fix</option></select>';
		}else{
			jqTds[4].innerHTML = '<select name="source" id="sourceId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Resource Optimization">Resource Optimization</option><option value="Process Improvement">Process Improvement</option><option value="Break Fix">Break Fix</option></select>';
		}
		
		if(oData[5] == 'continuous'){
			jqTds[5].innerHTML = '<select name="benefitType" id="benefitTypeId"class="required comboselect check"><option value="-1">Please Select</option><option value="continuous" selected="selected">Continuous</option><option value="One Time">One Time</option></select>';
		}else if (oData[5] == 'One Time') {
			jqTds[5].innerHTML = '<select name="benefitType" id="benefitTypeId"class="required comboselect check"><option value="-1">Please Select</option><option value="continuous">Continuous</option><option value="One Time" selected="selected">One Time</option></select>';
		}else{
			jqTds[5].innerHTML = '<select name="benefitType" id="benefitTypeId"class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="continuous" selected="selected">Continuous</option><option value="One Time">One Time</option></select>';
		}
		
		jqTds[6].innerHTML = '<input type="text" name="totalBusinessHrsSaved" id="totalBusinessHrsSavedId" value="'+oData[6]+'">';
		jqTds[7].innerHTML = '<input type="text" name="totalITHoursSaved" id="totalITHoursSavedId" value="'+oData[7]+'">';
		jqTds[8].innerHTML = '<input type="text" name="savingsInUSD" id="savingsInUSDId" value="'+oData[8]+'">';
		
		if(oData[9] == 'Yes'){
			jqTds[9].innerHTML = '<select name="justified" id="justifiedId" class="required comboselect check"><option value="-1">Please Select</option><option value="Yes" selected="selected">Yes</option><option value="No">No</option></select>';
		}else if (oData[9] == 'No') {
			jqTds[9].innerHTML = '<select name="justified" id="justifiedId" class="required comboselect check"><option value="-1">Please Select</option><option value="Yes">Yes</option><option value="No" selected="selected">No</option></select>';
		}else{
			jqTds[9].innerHTML = '<select name="justified" id="justifiedId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Yes">Yes</option><option value="No">No</option></select>';
		}
		
		jqTds[10].innerHTML = '<a id="aT3Id" href="javascript:void(0)" class="cropEdit">Save</a> / <a href="javascript:void(0)" class="cropCancelRow">Cancel</a>';
		jqTds[11].innerHTML = '<a id="delT3Id" href="javascript:void(0)" class="deleteRow">Delete</a>';
	}
		});
	
	$(document).on('click', '#cropTable a.cropEdit', function (e) {
		startProgress();
		var oCropTable =  $('#cropTable').dataTable();
		var sData = $('*', oCropTable.fnGetNodes()).serializeArray();	
		var nRow = $(this).parents('tr')[0];
		var tr = $(this).closest("tr");
    	var idx = tr.index();
		var oData = oCropTable.fnGetData(nRow);
		sData.push({name:"caTicket.id",value:document.getElementById("caTicketId").value});
		sData.push({name:"id",value:oData[0]});
		
		var jsonData ='{';
			$.each(sData, function(i, item) {
					 /* alert("Item: "+item.name+" Value: "+item.value); */ 
					var jsonString = getJsonString(item.name , item.value);
					if(jsonString != null && $.trim(jsonString) != '')
					jsonData += getJsonString(item.name , item.value) +",";
				
			});
			 jsonData = jsonData.slice(0, -1); 
			jsonData +='}';
			$.ajax({
				type : 'POST',
				dataType : 'json',
				url : '${pageContext.request.contextPath}/caticket/cropUpdate', 
				/* url : 't3Update/', */
				contentType : "text/html",
				data : jsonData,
				cache : false,
				success : function(data) {
					
					showSuccess("Crop Updated Successfully!");
					isCropOpen = false; 
					 $('#cropTable').dataTable().fnUpdate( [
			                                                  ''+data[0].id+'',
			                                                  ''+data[0].title+'',
			                                                  ''+document.getElementById("moduleName").value+'',
			                                                  ''+data[0].description+'',
			                                                  ''+data[0].source+'',
			                                                  ''+data[0].benefitType+'',
			                                                  ''+data[0].totalBusinessHrsSaved+'',
			                                                  ''+data[0].totalITHoursSaved+'',
			                                                  ''+data[0].savingsInUSD+'',
			                                                  ''+data[0].justified+'',
			                                                  '<a href="javascript:void(0)" class="cropEditEnable">Edit</a>',
			                                                  '<a href="javascript:void(0)" class="deleteRowCrop">Delete</a>' ], idx ); // Row
					stopProgress();
				},
				error:function(response){
					stopProgress();
					showError("Something wrong happends!!");
				},
			});
		});
	
	$(document).on('click', '#cropTable a.cropCancelRow', function(e) {
		isCropOpen = false;
		e.preventDefault();
		var oCropTable =  $('#cropTable').dataTable();
		var nRow = $(this).parents('tr')[0];
		nEditing = nRow;
		restoreRow(oCropTable, nEditing);
	});
	
	// Enabling Crop fields for editing
	$(document).on('click', '#reworkTable a.reworkEditEnable', function (e) {
		if(isCropOpen){
			var text = "Please enter and save the data in new row or cancel it";
			showAlert(text);
			e.preventDefault();
			return;
		}else{
			isReworkOpen = true;
		var oReworkTable =  $('#reworkTable').dataTable();
		var nRow = $(this).parents('tr')[0];
		var oData = oReworkTable.fnGetData(nRow);
		//alert("Checking id: "+oData[0]);
		
		var jqTds = $(">td", nRow);
		if(jqTds.length < 1)return;
		jqTds[0].innerHTML = oData[0];
		
		if(oData[1] == 'Requirements'){
			jqTds[1].innerHTML = '<select name="reworkType" id="reworkTypeId" class="required comboselect check"><option value="-1">Please Select</option><option value="Requirements" selected="selected">Requirements</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Testing">Testing</option></select>';
		}else if (oData[1] == 'Analysis') {
			jqTds[1].innerHTML = '<select name="reworkType" id="reworkTypeId" class="required comboselect check"><option value="-1">Please Select</option><option value="Requirements">Requirements</option><option value="Analysis" selected="selected">Analysis</option><option value="Coding">Coding</option><option value="Testing">Testing</option></select>';
		}else if(oData[1] == 'Coding'){
			jqTds[1].innerHTML = '<select name="reworkType" id="reworkTypeId" class="required comboselect check"><option value="-1">Please Select</option><option value="Requirements">Requirements</option><option value="Analysis">Analysis</option><option value="Coding" selected="selected">Coding</option><option value="Testing">Testing</option></select>';
		}else if(oData[1] == 'Testing'){
			jqTds[1].innerHTML = '<select name="reworkType" id="reworkTypeId" class="required comboselect check"><option value="-1">Please Select</option><option value="Requirements">Requirements</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Testing" selected="selected">Testing</option></select>';
		}else{
			jqTds[1].innerHTML = '<select name="reworkType" id="reworkTypeId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Requirements">Requirements</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Testing">Testing</option></select>';
		}
		
		jqTds[2].innerHTML = '<input type="text" name="startDateTimestamp" id="startDateTimestampId" value="'+oData[2]+'" maxlength="25" size="25" onclick="datepicker(id);">';
		jqTds[3].innerHTML = '<input type="text" name="endDateTimestamp" id="endDateTimestampId" value="'+oData[3]+'" maxlength="25" size="25" onclick="datepicker(id);">';
		jqTds[4].innerHTML = oData[4];
		
		if(oData[5] == 'Yes'){
			jqTds[5].innerHTML = '<select name="justified" id="justifiedId" class="required comboselect check"><option value="-1">Please Select</option><option value="Yes" selected="selected">Yes</option><option value="No">No</option></select>';
		}else if (oData[5] == 'No') {
			jqTds[5].innerHTML = '<select name="justified" id="justifiedId" class="required comboselect check"><option value="-1">Please Select</option><option value="Yes">Yes</option><option value="No" selected="selected">No</option></select>';
		}else{
			jqTds[5].innerHTML = '<select name="justified" id="justifiedId" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Yes">Yes</option><option value="No">No</option></select>';
		}
		
		jqTds[6].innerHTML = '<a id="aT3Id" href="javascript:void(0)" class="reworkEdit">Save</a> / <a href="javascript:void(0)" class="reworkCancelRow">Cancel</a>';
		jqTds[7].innerHTML = '<a id="delT3Id" href="javascript:void(0)" class="deleteRow">Delete</a>';
	}
		});
	
	$(document).on('click', '#reworkTable a.reworkEdit', function (e) {
		startProgress();
		var oReworkTable =  $('#reworkTable').dataTable();
		var sData = $('*', oReworkTable.fnGetNodes()).serializeArray();	
		var nRow = $(this).parents('tr')[0];
		var tr = $(this).closest("tr");
    	var idx = tr.index();

		var oData = oReworkTable.fnGetData(nRow);
		sData.push({name:"caTicket.id",value:document.getElementById("caTicketId").value});
		sData.push({name:"id",value:oData[0]});
		sData.push({name:"hourse",value:oData[4]});
		
		var jsonData ='{';
			$.each(sData, function(i, item) {
					 /* alert("Item: "+item.name+" Value: "+item.value); */ 
					var jsonString = getJsonString(item.name , item.value);
					if(jsonString != null && $.trim(jsonString) != '')
					jsonData += getJsonString(item.name , item.value) +",";
				
			});
			 jsonData = jsonData.slice(0, -1); 
			jsonData +='}';
			$.ajax({
				type : 'POST',
				dataType : 'json',
				url : '${pageContext.request.contextPath}/caticket/reworkUpdate', 
				/* url : 't3Update/', */
				contentType : "text/html",
				data : jsonData,
				cache : false,
				success : function(data) {
					
					showSuccess("Rework Updated Successfully!");
					isReworkOpen = false;
					 
					 $('#reworkTable').dataTable().fnUpdate( [
			                                                  ''+data[0].id+'',
			                                                  ''+data[0].reworkType+'',
			                                                  ''+data[0].startDateTimestamp+'',
			                                                  ''+data[0].endDateTimestamp+'',
			                                                  ''+data[0].hourse+'',
			                                                  ''+data[0].justified+'',
			                                                  '<a href="javascript:void(0)" class="reworkEditEnable">Edit</a>',
			                                                  '<a href="javascript:void(0)" class="deleteRowRework">Delete</a>' ], idx ); // Row
					 stopProgress();
				},
				error:function(response){
					stopProgress();
					showError("Something wrong happends!!");
				},
			});
		});
	
	$(document).on('click', '#reworkTable a.reworkCancelRow', function(e) {
		isReworkOpen = false;
		e.preventDefault();
		var oReworkTable =  $('#reworkTable').dataTable();
		var nRow = $(this).parents('tr')[0];
		nEditing = nRow;
		restoreRow(oReworkTable, nEditing);
	});
	function getInputValue(str){
		// for internet explorer
		var index1 = str.indexOf("<INPUT");
		
		// for firefox
		var index = str.indexOf("<input");
		/* if(index < 0)return null;
		str = str.substr(index , str.length);
		return $(str).val(); */
		if(index <0 && index1 < 0){
			return null;
		}
		
		
		if(index < 0){
			str = str.substr(index1, str.length);
			return $(str).val();
		}
		
		if(index1 < 0){
			str = str.substr(index, str.length);
			return $(str).val();
		}
	}
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


<script type="text/javascript">
	function saveRowT3(id) {
		
		var isValidate=validate("T3");
		if(isValidate){
		//var id1 = id.slice(-1);
		var id1 = id;
		//alert("id1: "+id1);
		startProgress();
		//alert("8989 :: "+$("#ticketNoId"+id1).val());
		var requirements = {
			parameters : []
		};

		requirements.parameters.push({
			"id" : "",
			"caTicket" : $("#ticketNoId" + id1).val(),
			"ticketNumber" : $("#ticketNo" + id1).val(),
			"module" : $("#module" + id1).val(),
			"dateContacted" : $("#dateContId" + id1).val(),
			"description" : $("#description" + id1).val(),
			"reasonForHelp" : $("#reasonHelpId" + id1).val(),
			"noOfhoursTaken" : $("#hrTakenId" + id1).val(),
			"justified" : $("#justifiedId" + id1).val()
		});

		startProgress();
		$.ajax({
			type : 'POST',
			dataType : 'json',
			url : 'saveT3/',
			contentType : "text/html",
			data : JSON.stringify(requirements),
			cache : false,
			success : function(data) {
				isT3Open = false;
			   
			    showSuccess("T3 Contributions saved Successfully! ");
			    $('#contributionTable').dataTable().fnAddData( [
			                                                  ''+data[0].id+'',
			                                                  ''+document.getElementById("caTicketNoID").value+'',
			                                                  ''+document.getElementById("moduleName").value+'',
			                                                  ''+data[0].dateContacted+'',
			                                                  ''+data[0].description+'',
			                                                  ''+data[0].reasonForHelp+'',
			                                                  ''+data[0].noOfhoursTaken+'',
			                                                  ''+data[0].justified+'',
			                                                  '<a href="javascript:void(0)" class="t3EditEnable">Edit</a>',
			                                                  '<a href="javascript:void(0)" class="deleteRowt3">Delete</a>' ] );
			                stopProgress();
			    //window.location.reload();
			},
			error : function(error) {
				stopProgress();
				showError("Something happend wrong!! ");
			},
		});
	}
	}

	function editRowT3(nRow) {
		//alert("T3 id: "+id);
		//alert("8989 :: "+$("#ticketNoId"+id1).val());
		/* var requirements = {
			parameters : []
		};

		requirements.parameters.push({
			"id" : oData[0],
			"caTicket" : $("#caTicketId").val(),
			"ticketNumber" : oData[1],
			"module" : oData[2],
			"dateContacted" : oData[3],
			"description" : oData[4],
			"reasonForHelp" : oData[5],
			"noOfhoursTaken" : oData[6],
			"justified" : oData[7]
		});

		//startProgress();
		$.ajax({
			type : 'POST',
			dataType : 'json',
			url : 'saveT3/',
			contentType : "text/html",
			data : JSON.stringify(requirements),
			cache : false,
			success : function(data) {
				//stopProgress();
				showSuccess("T3 Contributions Updated Successfully! ");
				//window.location.reload();
			},error: function(errorResponse)
	 	    {
	 	    	//showError("Project Allocation cannot be  copied.");
	 	    },
		}); */
	}
	
	function saveRowSR(id) {
		
		var isValidate=validate("SR");
		if(isValidate){
		//var id1 = id.slice(-1);
		var id1 = id;
		var requirements = {
			parameters : []
		};
		startProgress();
		//alert("sdfsd :: " + $("#caTicketIDSR" + id1).val());

		requirements.parameters.push({
			"caTicket" : $("#caTicketIDSR" + id1).val(),
			/* "caTicketNumber" : $("#caTicketNoSR"+id1).val(), */
			"reviewDate" : $("#reViewDateIdSR"+id1).val(),
			"isTheIssueUnderstandingCorrect" : $("#issueUnderstandingSR" + id1).val(),
			"alternateSolution" : $("#alternateSolSR" + id1).val(),
			"isSolAppropriate" : $("#isSolAppropriateSR" + id1).val(),
			"agreeWithRca" : $("#rcaSR" + id1).val(),
			"rating" : $("#ratingSR" + id1).val(),
			"comments" : $("#commentsSR" + id1).val(),
		});

		//alert($("#reViewDateIdSR"+id1).val());
		var date = $("#reViewDateIdSR"+id1).val();
		$.ajax({
			type : 'POST',
			dataType : 'json',
			url : 'saveSR/',
			contentType : "text/html",
			data : JSON.stringify(requirements),
			cache : false,
			success : function(data) {
				
				isSolRevOpen = false;
			    
			    showSuccess("Solution Review saved Successfully! ");
			    $('#solutionReviewTable').dataTable().fnAddData( [
			                                                  ''+data[0].id+'',
			                                                  ''+document.getElementById("landscapeName").value+'',
			                                                  ''+document.getElementById("moduleName").value+'',
			                                                  ''+document.getElementById("reviewerName").value+'',
			                                                  ''+data[0].reviewDate+'',
			                                                  ''+document.getElementById("caTicketNoID").value+'',
			                                                  ''+document.getElementById("assigneeName").value+'',
			                                                  ''+data[0].isTheIssueUnderstandingCorrect+'',
			                                                  ''+data[0].alternateSolution+'',
			                                                  ''+data[0].isSolAppropriate+'',
			                                                  ''+data[0].agreeWithRca+'',
			                                                  ''+data[0].rating+'',
			                                                  ''+data[0].comments+'',
			                                                  '<a href="javascript:void(0)" class="solutionReviewEditEnable">Edit</a>',
			                                                  '<a href="javascript:void(0)" class="deleteRowSolutionReview">Delete</a>' ] );
			                stopProgress();
				//window.location.reload();
			},
			error : function(error) {
				stopProgress();
				showError("Something happend wrong!! ");
				//window.location.reload();
			},
		});
		
		document.getElementById('solutionreViewDateId').val = date;
		}
	}

	function saveRowDL(id) {
		var isValidate=validate("DL");
		if(isValidate){
		//var id1 = id.slice(-1);
		var id1 = id;
		var requirements = {
			parameters : []
		};
	startProgress();
		requirements.parameters.push({
			"caTicket" : $("#caTicketIdDL" + id1).val(),
			"defectType" : $("#defectTypeId" + id1).val(),
			"defectDescription" : $("#defectDescriptionId" + id1).val(),
			"internalExternal" : $("#internalExternalId" + id1).val(),
			"defectCategory" : $("#defectCategoryId" + id1).val(),
			"severity" : $("#sevirityId" + id1).val(),
			"defectStatus" : $("#defectStatusId" + id1).val(),
			"identifiedDate" : $("#identifiedDateId" + id1).val(),
			"identifiedBy" : $("#identifiedById" + id1).val(),
			"identifiedPhase" : $("#identifiedPhaseId" + id1).val(),
			"injectedPhase" : $("#injectedPhaseId" + id1).val(),
			"workProductName" : $("#workProductId" + id1).val(),
			"reopened" : $("#reopenedId" + id1).val(),
			"defectRootCause" : $("#defectRootCauseId" + id1).val(),
			"categoryOfRootCause" : $("#categoryRootCauseId" + id1).val(),
			"resolvedBy" : $("#resolvedById" + id1).val(),
			"resolvedDate" : $("#resolvedDateId" + id1).val(),
			"closedDate" : $("#closedDateId" + id1).val(),

		});

		$.ajax({
			type : 'POST',
			dataType : 'json',
			url : 'saveDL/',
			contentType : "text/html",
			data : JSON.stringify(requirements),
			cache : false,
			success : function(data) {
				isDefectLogOpen = false;
			    
			    showSuccess("Defect Log saved Successfully! ");
			    $('#defectLogTable').dataTable().fnAddData( [
			                                                  ''+data[0].id+'',
			                                                  ''+document.getElementById("ZREQNo").value+'',
			                                                  ''+data[0].defectType+'',
			                                                  ''+data[0].defectDescription+'',
			                                                  ''+data[0].internalExternal+'',
			                                                  ''+data[0].defectCategory+'',
			                                                  ''+data[0].severity+'',
			                                                  ''+data[0].defectStatus+'',
			                                                  ''+data[0].identifiedDate+'',
			                                                  ''+data[0].identifiedBy+'',
			                                                  ''+data[0].identifiedPhase+'',
			                                                  ''+document.getElementById("moduleName").value+'',
			                                                  ''+data[0].injectedPhase+'',
			                                                  ''+data[0].workProductName+'',
			                                                  ''+data[0].reopened+'',
			                                                  ''+document.getElementById("caTicketNoID").value+'',
			                                                  ''+data[0].defectRootCause+'',
			                                                  ''+data[0].categoryOfRootCause+'',
			                                                  ''+data[0].resolvedBy+'',
			                                                  ''+data[0].resolvedDate+'',
			                                                  ''+data[0].closedDate+'',
			                                                  '<a href="javascript:void(0)" class="defectLogEditEnable">Edit</a>',
			                                                  '<a href="javascript:void(0)" class="deleteRowDefectLog">Delete</a>' ] );
				stopProgress();
				
				//window.location.reload();
			},
			error : function(error) {
				stopProgress();
				showError("Something happend wrong!! ");
				//window.location.reload();
			},
		});
	}
	}
	function saveRowCrop(id) {
		
		var isValidate=validate("CROP");
		if(isValidate){
		//var id1 = id.slice(-1);
		var id1 = id;
		var requirements = {
			parameters : []
		};
	startProgress();
		requirements.parameters.push({
			"caTicket" : $("#caTicketIDCrop" + id1).val(),
			"title" : $("#cropTitle" + id1).val(),
			"description" : $("#cropDesc" + id1).val(),
			"source" : $("#sourceId" + id1).val(),
			"benefitType" : $("#benefitTypeId" + id1).val(),
			"totalBusinessHrsSaved" : $("#totalBusinessHrsId" + id1).val(),
			"totalITHoursSaved" : $("#totalITHrsId" + id1).val(),
			"savingsInUSD" : $("#savingUSD" + id1).val(),
			"justified" : $("#justifiedCropId" + id1).val(),

		});

		$.ajax({
			type : 'POST',
			dataType : 'json',
			url : 'saveCrop/',
			contentType : "text/html",
			data : JSON.stringify(requirements),
			cache : false,
			success : function(data) {
				
				isCropOpen = false;
			    
			    showSuccess("Crop saved Successfully! ");
			    $('#cropTable').dataTable().fnAddData( [
			                                                  ''+data[0].id+'',
			                                                  ''+data[0].title+'',
			                                                  ''+document.getElementById("moduleName").value+'',
			                                                  ''+data[0].description+'',
			                                                  ''+data[0].source+'',
			                                                  ''+data[0].benefitType+'',
			                                                  ''+data[0].totalBusinessHrsSaved+'',
			                                                  ''+data[0].totalITHoursSaved+'',
			                                                  ''+data[0].savingsInUSD+'',
			                                                  ''+data[0].justified+'',
			                                                  '<a href="javascript:void(0)" class="cropEditEnable">Edit</a>',
			                                                  '<a href="javascript:void(0)" class="deleteRowCrop">Delete</a>' ] );
			     stopProgress();
			},
			error : function(error) {
				stopProgress();
				showError("Something happend wrong!! ");
			},
		});
	}
	}
	function saveRowRW(id) {
		
		var isValidate=validate("RW");
		if(isValidate){
		//var id1 = id.slice(-1);
		var id1 = id;
		var requirements = {
			parameters : []
		};
	startProgress();
		requirements.parameters.push({
			"caTicket" : $("#caTicketIDRW" + id1).val(),
			"reworkType" : $("#reworkTypeId" + id1).val(),
			"startDateTimestamp" : $("#startDateRW" + id1).val(),
			"endDateTimestamp" : $("#endDateRW" + id1).val(),
			"justified" : $("#justifiedIdRW" + id1).val(),

		});

		$.ajax({
			type : 'POST',
			dataType : 'json',
			url : 'saveRework/',
			contentType : "text/html",
			data : JSON.stringify(requirements),
			cache : false,
			success : function(data) {
				
				isReworkOpen = false;
			     
			    showSuccess("Rework saved Successfully! ");
			    $('#reworkTable').dataTable().fnAddData( [
			                                                  ''+data[0].id+'',
			                                                  ''+data[0].reworkType+'',
			                                                  ''+data[0].startDateTimestamp+'',
			                                                  ''+data[0].endDateTimestamp+'',
			                                                  ''+data[0].hourse+'',
			                                                  ''+data[0].justified+'',
			                                                  '<a href="javascript:void(0)" class="reworkEditEnable">Edit</a>',
			                                                  '<a href="javascript:void(0)" class="deleteRowRework">Delete</a>' ] );
			     stopProgress();
			},
			error : function(error) {
				stopProgress();
				showError("Something happend wrong!! ");
				window.location.reload();
			},
		});
	}
	}
	
	$(document)
			.ready(
					function() {
						$(".addNewContribution")
								.click(
										function() {
											if(isT3Open){
												var text = "Please enter and save the data in new row or cancel it";
												showAlert(text);
												editAlloc = false;
												e.preventDefault();
												return;
											}else{
												isT3Open = true;
											var trID = $("#contributionTableBody tr").length;
											//alert("trID... : "+trID);
											$("#contributionTableBody")
												.append(
														'<tr class="even" align="center"><td><input type="hidden" id="ticketNoId'+trID+'" value="${catickets.id}"><input type="hidden" id="ticketNo'+trID+'" value="${catickets.caTicketNo}">${catickets.caTicketNo}</td>'
											              + '<td align="center"><input type="hidden" id="module'+trID+'" value="${catickets.moduleId.moduleName}">${catickets.moduleId.moduleName}</td>'
											              + '<td align="center"><input type="text"  id="dateContId'+trID+'"value="" maxlength="25" size="25" onclick="datepicker(id);"></td>'
											              + '<td align="center"><input type="text"  id="description'+trID+'"value=""></td>'
											              + '<td align="center"><input type="text" id="reasonHelpId'+trID+'" value=""></td>'
											              + '<td align="center"><input type="text"  id="hrTakenId'+trID+'"value=""></td>'
											              + '<td align="center"><select name="justified" id="justifiedId'+trID+'" class="required comboselect check"><option value="Yes">Yes</option><option value="No">No</option></select></td>'
											              + '<td align="center"><a id="aT3Id'+trID+ '" href="javascript:void(0)" onClick="saveRowT3('+trID+')">Save</a> / <a href="javascript:void(0)" class="t3DeleteRow">Cancel</a></td>'
											              + '<td align="center"><a id="delT3Id'+ trID+ '" href="javascript:void(0)" class="t3DeleteRow">Delete</a></td></tr>');
											}
										});

						$(document).on('click', '.t3DeleteRow', function() {
							isT3Open = false;
							$(this).parent().parent().remove();
						});

						var trIDSR = $("#solutionReviewTableBody tr").length;
						$(".addNewSolutionReview")
								.click(
										function() {
											if(isSolRevOpen){
												var text = "Please enter and save the data in new row or cancel it";
												showAlert(text);
												e.preventDefault();
												return;
											}else{
												isSolRevOpen = true;
											$("#solutionReviewTableBody")
												.append(
														'<tr class="even">'
														+ '<td align="center">${catickets.landscapeId.landscapeName}</td>'
														+ '<td align="center">${catickets.moduleId.moduleName}</td>'
														+ '<td align="center">${catickets.reviewer.employeeName}</td>'
														+ '<td align="center"><input type="text" id="reViewDateIdSR'+trIDSR+'"  maxlength="25" size="25" onclick="datepicker(id);"/></td>'    /* maxlength="10" class="required string server-validation datepickerStamp"  */
														/* + '<td width="18%" align="left"><input type="text" id="reViewDateIdSR'+trIDSR+'" maxlength="10" class="required string server-validation datepickerStamp"/></td>' */
														+ '<td align="center"><input type="hidden" id="caTicketIDSR'+trIDSR+'" value=${catickets.id}><input type="hidden" id="caTicketNoSR'+trIDSR+'" value=${catickets.caTicketNo}>${catickets.caTicketNo}</td>'
														+ '<td align="center">${catickets.assigneeId.employeeName}</td>'
														+ '<td align="center"><select name="issueUnderstanding" id="issueUnderstandingSR'+trIDSR+'" class="required comboselect check"><option value="-1">Please Select</option><option value="Yes">Yes</option><option value="No">No</option></select></td>'
														+ '<td align="center"><input type="text" id="alternateSolSR'+trIDSR+'" value=""></td>'
														+ '<td align="center"><select name="isSolAppropriate" id="isSolAppropriateSR'+trIDSR+'" class="required comboselect check"><option value="-1">Please Select</option><option value="Yes">Yes</option><option value="No">No</option></select></td>'
														+ '<td align="center"><select name="rca" id="rcaSR'+trIDSR+'" class="required comboselect check"><option value="-1">Please Select</option><option value="Yes">Yes</option><option value="No">No</option></select></td>'
														+ '<td align="center"><select name="ratingName" id="ratingSR'+trIDSR+'" class="required comboselect check"><option value="-1">Please Select</option><option value="Complex">Complex</option><option value="High">High</option><option value="Medium">Medium</option><option value="Regular">Regular</option><option value="Repetitive">Repetitive</option></select></td>'
														+ '<td align="center"><input type="text"  id="commentsSR'+trIDSR+'" value=""></td>'
														+ '<td align="center"><a  id="saveSR'+ trIDSR+ '" href="javascript:void(0)" onClick="saveRowSR('+trIDSR+')" class="saveRow">Save</a> / <a href="javascript:void(0)" class="solRevDeleteRow">Cancel</a></td>'
														+ '<td align="center"><a  id="deleteSR'+ trIDSR+ '" href="javascript:void(0)" class="solRevDeleteRow">Delete</a></td></tr>');
										}
										});
						$(document).on('click', '.solRevDeleteRow', function() {
							isSolRevOpen = false;
							$(this).parent().parent().remove();
						});
						var trIDDL = $("#defectLogTableBody tr").length;
						$(".addDefectLog")
								.click(
										function() {
											if(isDefectLogOpen){
												var text = "Please enter and save the data in new row or cancel it";
												showAlert(text);
												e.preventDefault();
												return;
											}else{
												isDefectLogOpen = true;
											$("#defectLogTableBody")
												.append(
														'<tr class="even">'
														+ '<td><input type="hidden" id="caTicketIdDL'+trIDDL+'" value="${catickets.id}">${catickets.ZREQNo}</td>'
				+ '<td><select name="defectTypeName" id="defectTypeId'+trIDDL+'" class="required comboselect check"><option value="-1">Please Select</option><option value="Review">Review</option><option value="Testing">Testing</option></select></td>'
				+ '<td><input type="text" value="" name="defectDescriptionName" id="defectDescriptionId'+trIDDL+'"></td>'
				+ '<td><select name="internalExternalName" id="internalExternalId'+trIDDL+'" class="required comboselect check"><option value="-1">Please Select</option><option value="Internal">Internal</option><option value="External">External</option></select></td>'
				+ '<td><select name="defectCategoryName" id="defectCategoryId'+trIDDL+'" class="required comboselect check"><option value="-1">Please Select</option><option value="Coding">Coding</option><option value="Technical">Technical</option><option value="Standards">Standards</option><option value="Design">Design</option><option value="Others">Others</option></select></td>'
				+ '<td><select name="severityName" id="sevirityId'+trIDDL+'" class="required comboselect check"><option value="-1">Please Select</option><option value="Medium">Medium</option><option value="Low">Low</option><option value="High">High</option></select></td>'
				+ '<td><select name="defectStatusName" id="defectStatusId'+trIDDL+'" class="required comboselect check"><option value="-1">Please Select</option><option value="Accepted">Accepted</option><option value="Closed">Closed</option><option value="WIP">WIP</option></select></td>'
				+ '<td><input type="text" value="" id="identifiedDateId'+trIDDL+'" name="identifiedDateName" maxlength="25" size="25" onclick="datepicker(id);"/></td>'
				+ '<td><select name="identifiedBy" id="identifiedById'+trIDDL+'" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><c:forEach var="resource" items="${resources}"><c:choose><c:when test="${catickets.assigneeId.employeeId==resource.employeeId}"><option value="${resource.employeeName}" selected="selected">${resource.employeeName}</option></c:when><c:otherwise><option value="${resource.employeeName}">${resource.employeeName}</option></c:otherwise></c:choose></c:forEach></select></td>'
				+ '<td><select name="identifiedPhaseName" id="identifiedPhaseId'+trIDDL+'" class="required comboselect check"><option value="-1">Please Select</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Design">Design</option><option value="Post Implementation">Post Implementation</option><option value="Requirements">Requirements</option><option value="Unit Testing">Unit Testing</option><option value="UAT">UAT</option></select></td>'
				+ '<td>${catickets.moduleId.moduleName}</td>'
				+ '<td><select name="injectedPhaseName" id="injectedPhaseId'+trIDDL+'" comboselect check"><option value="-1">Please Select</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Design">Design</option><option value="Post Implementation">Post Implementation</option><option value="Requirements">Requirements</option><option value="Unit Testing">Unit Testing</option><option value="UAT">UAT</option></select></td>'
				+ '<td><select name="workProductName" id="workProductId'+trIDDL+'" class="required comboselect check"><option value="-1">Please Select</option><option value="Code">Code</option><option value="Design Documents">Design Documents</option><option value="Reviews">Reviews</option><option value="Testing">Testing</option></select></td>'
				+ '<td><select name="reopenedName" id="reopenedId'+trIDDL+'" comboselect check"><option value="-1">Please Select</option><option value="Yes">Yes</option><option value="No">No</option></select></td>'
				+ '<td>${catickets.caTicketNo}</td>'
				+ '<td><input type="text" value="" name="defectRootCauseName" id="defectRootCauseId'+trIDDL+'"></td>'
				+ '<td><select name="categoryRootCauseName" id="categoryRootCauseId'+trIDDL+'" class="required comboselect check"><option value="-1">Please Select</option><option value="Accidential">Accidential</option><option value="Insufficient Tools">Insufficient Tools</option><option value="Miscommunication">Miscommunication</option></select></td>'
				+ '<td><input type="text" value="" name="resolvedByName" id="resolvedById'+trIDDL+'"></td>'
				+ '<td><input type="text" value="" id="resolvedDateId'+trIDDL+'" name="resolvedDateName" maxlength="25" size="25" onclick="datepicker(id);"/></td>'
				+ '<td><input type="text" value="" id="closedDateId'+trIDDL+'" name="closedDateName" maxlength="25" size="25" onclick="datepicker(id);"/></td>'
				+ '<td><a id="saveDL' + trIDDL + '" href="javascript:void(0)" onClick="saveRowDL('+trIDDL+')" class="saveRow">Save</a> / <a href="javascript:void(0)" class="defectLogDeleteRow">Cancel</a></td>'
				+ '<td><a id="deleteDL' + trIDDL + '" href="javascript:void(0)" class="defectLogDeleteRow">Delete</a></td></tr>');
				}
				});
						$(document).on('click', '.defectLogDeleteRow', function() {
							isDefectLogOpen = false;
							$(this).parent().parent().remove();
						});
						var trIDCrop = $("#cropTableBody tr").length;
						$(".addNewCrop")
								.click(
										function() {
											if(isCropOpen){
												var text = "Please enter and save the data in new row or cancel it";
												showAlert(text);
												e.preventDefault();
												return;
											}else{
												isCropOpen = true;
											$("#cropTableBody")
												.append(
														'<tr class="even">'
														+ '<td><input type="hidden" id="caTicketIDCrop'+trIDCrop+'" value=${catickets.id}><input type="text" value="" id="cropTitle'+trIDCrop+'"></td>'
														+ '<td>${catickets.moduleId.moduleName}</td>'
														+ '<td><input type="text" id="cropDesc'+trIDCrop+'"></td>'
														+ '<td><select name="source" id="sourceId'+trIDCrop+'" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Resource Optimization">Resource Optimization</option><option value="Process Improvement">Process Improvement</option><option value="Break Fix">Break Fix</option></select></td>'
														+ '<td><select name="benefitType" id="benefitTypeId'+trIDCrop+'"class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="continuous">Continuous</option><option value="One Time">One Time</option></select></td>'
														+ '<td><input type="text" id="totalBusinessHrsId'+trIDCrop+'"</td>'
														+ '<td><input type="text" id="totalITHrsId'+trIDCrop+'" value=""></td>'
														+ '<td><input type="text" id="savingUSD'+trIDCrop+'" value=""></td>'
														+ '<td><select name="justified" id="justifiedCropId'+trIDCrop+'" class="required comboselect check"><option value="-1" selected="selected">Please Select</option><option value="Yes">Yes</option><option value="No">No</option></select></td>'
														+ '<td><a id="saveCrop'+trIDCrop+'" href="javascript:void(0)" onClick="saveRowCrop('+trIDCrop+')"class="saveRow">Save</a> / <a href="javascript:void(0)" class="cropDeleteRow">Cancel</a></td>'
														+ '<td><a id="deleteCrop'+trIDCrop+'" href="javascript:void(0)" class="cropDeleteRow">Delete</a></td></tr>'
														);
										}
									});

						$(document).on('click', '.cropDeleteRow', function() {
							isCropOpen = false;
							$(this).parent().parent().remove();
						});
						
						var trIDRW = $("#reworkTableBody tr").length;
						$(".addRework")
								.click(
										function() {
											if(isReworkOpen){
												var text = "Please enter and save the data in new row or cancel it";
												showAlert(text);
												e.preventDefault();
												return;
											}else{
												isReworkOpen = true;
											$("#reworkTableBody")
												.append(
													'<tr class="even">'
													+ '<td><input type="hidden" id="caTicketIDRW'+trIDRW+'" value="${catickets.id}"><select name="reworkType" id="reworkTypeId'+trIDRW+'" class="required comboselect check"><option value="Requirements">Requirements</option><option value="Analysis">Analysis</option><option value="Coding">Coding</option><option value="Testing">Testing</option></select></td>'
													+ '<td><input type="text" value="" id="startDateRW'+trIDRW+'" maxlength="25" size="25" onclick="datepicker(id);"></td>'
													+ '<td><input type="text" value="" id="endDateRW'+trIDRW+'" maxlength="25" size="25" onclick="datepicker(id);"></td>'
													+ '<td>Time difference</td>'
													+ '<td><select name="justified" id="justifiedIdRW'+trIDRW+'" class="required comboselect check"><option value="-1">Please Select</option><option value="Yes">Yes</option><option value="No">No</option></select></td>' 
													+ '<td><a id="saveRW'+trIDRW+ '" href="javascript:void(0)" onClick="saveRowRW('+trIDRW+')" class="saveRow">Save</a> / <a href="javascript:void(0)" class="reworkDeleteRow">Cancel</a></td>'
													+ '<td><a id="deleteRW'+trIDRW+ '" href="javascript:void(0)"class="reworkDeleteRow">Delete</a></td></tr>'
													);
										}
						/* $(document).on('click', '.deleteRow', function() {
							$(this).parent().parent().remove();
						}); */

						/* $("#identifiedDateId" + trIDCrop).datepicker({
							changeMonth : true,
							changeYear : true
						});
						$("#reViewDateIdSR"+trIDSR).addClass('required string server-validation datepickerStamp');
						$("#resolvedDateId" + trIDDL).datepicker({
							changeMonth : true,
							changeYear : true
						});
						$("#closedDateId" + trIDCrop).datepicker({
							changeMonth : true,
							changeYear : true
						}); */

					});
						$(document).on('click', '.reworkDeleteRow', function() {
							isReworkOpen = false;
							$(this).parent().parent().remove();
						});
	
					});
	
	$(document)
			.on(
					'click',
					'#contributionTable a.saveRow',
					function(e) {
						if (addNewAlloc == true && this.innerHTML == "Edit") {
							var text = "Please enter and save the data in new row or cancel it";
							showAlert(text);
							editAlloc = false;
							e.preventDefault();
							return;
						}
					});
	
	$(document).on('click','.deleteRowt3',function(){
			var flag=confirm("Are you sure want to delete row?");
			if (flag){
				startProgress();
				 var rowId=$(this).parent().parent().find("td:first").html();
				 var tr = $(this).closest("tr");
			     var idx = tr.index();
				  $.ajax({
				   type : 'POST',
				   url : '${pageContext.request.contextPath}/caticket/t3Delete', 
				   dataType: "text",
				   data: {"rowId":rowId},
				   success : function(data) {
				  //stopProgress();
					showSuccess("T3 Contributions: Row deleted Successfully!");
					isT3Open = false;
					 
					 
					 $('#contributionTable').dataTable().fnDeleteRow(idx); // Row
				    
					 stopProgress();
				   },
				   error:function(response){
					   stopProgress();
				    showError("Somthing happends wrong!!");
				   },
				  });
				  
				
			} 
	 });
		 
		 $(document).on('click','.deleteRowSolutionReview',function(){
			 
			 var solutionReviewTableBodyLength = $("#solutionReviewTableBody tr").length;
				/* if(solutionReviewTableBodyLength > 1){ */
			 var flag=confirm("Are you sure want to delete row?");
				if (flag){
					startProgress();
		var rowId=$(this).parent().parent().find("td:first").html();
		var tr = $(this).closest("tr");
    	var idx = tr.index();
		  $.ajax({
		   type : 'POST',
		   url : '${pageContext.request.contextPath}/caticket/solutionReviewDeleteRow', 
		   dataType: "text",
		   data: {"rowId":rowId},
		   success : function(data) {
		    showSuccess("Solution Review : Row deleted Successfully!");
		    $('#solutionReviewTable').dataTable().fnDeleteRow(idx);
		    stopProgress();
		   // window.location.reload();
		   },
		   error:function(response){
		    showError("Somthing happends wrong!!");
		    stopProgress();
		   },
		  });
		}
		 /* }else{
			alert("You can not delete this record since Solution Acceptance date already filled!!");
		 } */
		 });
		 
		 $(document).on('click','.deleteRowDefectLog',function(){
			 var flag=confirm("Are you sure want to delete row?");
				if (flag){
					startProgress();
					var tr = $(this).closest("tr");
			    	var idx = tr.index();
		var rowId=$(this).parent().parent().find("td:first").html();
		  $.ajax({
		   type : 'POST',
		   url : '${pageContext.request.contextPath}/caticket/defectLogDeleteRow', 
		   dataType: "text",
		   data: {"rowId":rowId},
		   success : function(data) {
		    showSuccess("Defect Log : Row deleted Successfully!");
		    $('#defectLogTable').dataTable().fnDeleteRow(idx);
		    stopProgress();
		    //window.location.reload();
		   },
		   error:function(response){
		    showError("Somthing happends wrong!!");
		    stopProgress();
		   },
		  });
				}
		 });
		 
		 $(document).on('click','.deleteRowCrop',function(){
			 var flag=confirm("Are you sure want to delete row?");
				if (flag){
					startProgress();
			 var rowId=$(this).parent().parent().find("td:first").html();
		  $.ajax({
		   type : 'POST',
		   url : '${pageContext.request.contextPath}/caticket/cropDeleteRow', 
		   dataType: "text",
		   data: {"rowId":rowId},
		   success : function(data) {
		    showSuccess("CROP : Row deleted Successfully!");
		    isCropOpen = false;
			 
			 $('#cropTable').dataTable().fnDeleteRow(idx); // Row
		    
			 stopProgress();
		   },
		   error:function(response){
		    showError("Somthing happends wrong!!");
		    stopProgress();
		   },
		  });
				}
		 });
		 
		 $(document).on('click','.deleteRowRework',function(){
			 var flag=confirm("Are you sure want to delete row?");
				if (flag){
					var tr = $(this).closest("tr");
			    	var idx = tr.index();
			 var rowId=$(this).parent().parent().find("td:first").html();
			 startProgress();
		  $.ajax({
		   type : 'POST',
		   url : '${pageContext.request.contextPath}/caticket/reworkDeleteRow', 
		   dataType: "text",
		   data: {"rowId":rowId},
		   success : function(data) {
		    showSuccess("Rework : Row deleted Successfully!");
		     
			 $('#reworkTable').dataTable().fnDeleteRow(idx); // Row
		     
			 stopProgress();
		   },
		   error:function(response){
		    showError("Somthing happends wrong!!");
		    stopProgress();
		   },
		  });
				}
		 });
</script>

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
		action="${pageContext.request.contextPath}/caticket/editTicket">
		<div id="caTicketNoDiv" class="center_div">
			<div class="form">
		
				<table id="formTable" width="100%">
						<input type="hidden" value="${catickets.id }"
									name="id" id="caTicketId" maxlength="10"
									class="required string server-validation" />
									<input type="hidden" value="${role }"
									name="role" id="roleId"/>
									
									<c:choose>
									<c:when test="${loggedInId == catickets.reviewer.employeeId}">
									<input type="hidden" value="true"
									name="isReviewer" id="isReviewerId"/>
									</c:when>
									<c:otherwise>
									<input type="hidden" value="false"
									name="isReviewer" id="isReviewerId"/>
									</c:otherwise>
									</c:choose>
									<input type="hidden" value="${catickets.moduleId.moduleName }"
									name="moduleName" id="moduleName"/>
									<input type="hidden" value="${catickets.landscapeId.landscapeName }"
									name="landscapeName" id="landscapeName"/>
									<input type="hidden" value="${catickets.reviewer.employeeName }"
									name="reviewerName" id="reviewerName"/>
									<input type="hidden" value="${catickets.assigneeId.employeeName }"
									name="assigneeName" id="assigneeName"/>
					<tr>
						<td width="11%" align="right" id="ticketNoId">Ticket No :<span
							class="astric">*</span>
						</td>
						<td width="18%" align="left">
							<div class="positionRel">
							
								<input type="text" value="${catickets.caTicketNo }"
									name="caTicketNo" id="caTicketNoID" maxlength="10" readonly="readonly" style="background-color: #DCDCDC;"
									class="required string server-validation" />
							</div>
						</td>
						<td width="11%" align="right">Description :<span
							class="astric">*</span></td>
						<td width="18%" align="left"><textarea name="description"
								id="descripId" cols="" rows="2" class="string">${catickets.description}</textarea></td>


						<td width="13%" align="right">Priority :</td>
						<td align="left"><select name="priority"
							class="required comboselect check" id="priority">
								<option selected="selected" value="${catickets.priority}">${catickets.priority}</option>
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
										 <c:when test="${catickets.reviewer.employeeId==resource.employeeId}">
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
								<option value="-1" selected="selected">Please Select</option>
								<c:forEach var="unit" items="${units}">
								<%-- <option value="${unit.id}">${unit.unitName}</option> --%>
								<c:choose>
										 <c:when test="${catickets.unitId.id==unit.id}">
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
										 <c:when test="${not empty catickets.region.id}">
												<option value="${catickets.region.id}" selected="selected">${catickets.region.regionName}</option>
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
										 <c:when test="${catickets.assigneeId.employeeId==resource.employeeId}">
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
										 <c:when test="${catickets.moduleId.id==project.id}">
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
										 <c:when test="${catickets.landscapeId.id==landscape.id}">
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
									<c:when test="${catickets.solutionReadyForReview == 'Yes'}">
										<option value="Yes" selected="selected">Yes</option>
										<option value="No">No</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:when test="${catickets.solutionReadyForReview == 'N.A.'}">
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
						<td align="left"><select name="aging" id="agingId" class="required comboselect check">
								<option value="${catickets.aging}">${catickets.aging}</option>
						</select></td>

						<td align="right"></td>
						<td align="left"></td>
					</tr>
					<tr>
						<td align="right">SLA Missed : <span class="astric">*</span></td>
						<td align="left"><select name="slaMissed" class="required comboselect check"
							id="slaMissedId">
								<option value="${catickets.slaMissed }">${catickets.slaMissed }</option>
						</select></td>
						<td align="right">Reason for SLA Missed : <span
							class="astric">*</span></td>
						<%-- <c:choose>
							<c:when test="${catickets.slaMissed=='Yes'}">
								<td align="left"><select name="reasonForSLAMissed"
									id="reasonForSLAMissedId" class="required comboselect check">
										<option value="Pending with us">Pending with us</option>
								</select></td>
							</c:when>
							<c:otherwise>
								<td align="left"><select name="reasonForSLAMissed"
									id="reasonForSLAMissedId"  class="required comboselect check">
										<option value="Pending with us">Pending with us</option>
								</select></td>
							</c:otherwise>
						</c:choose> --%>
						<td>
						<select name="reasonForSLAMissed.id"
							id="reasonForSLAMissedId"> 
							<option value="-1" selected="selected">Please Select</option>
						<%-- <option selected="selected" value="${catickets.assigneeId}">${catickets.assigneeId.employeeName}</option> --%>  
								<c:forEach var="reasonForSLAMissed" items="${reasonForSLAMissed}">
								<%-- <option value="${resource.employeeId}">${resource.employeeName}</option> --%>
								<c:choose>
										 <c:when test="${catickets.reasonForSLAMissed.id==reasonForSLAMissed.id}">
												<option value="${reasonForSLAMissed.id}" selected="selected">${reasonForSLAMissed.reason}</option>
										 </c:when>
										 <c:otherwise>
										 	<option value="${reasonForSLAMissed.id}">${reasonForSLAMissed.reason}</option>
										 </c:otherwise>
									</c:choose>
								 </c:forEach> 
						</select>
						</td>
						<td align="right">Days Open :<span class="astric">*</span></td>
						<td align="left">
						<input type="text" disabled value="${catickets.daysOpen}"
									name="daysOpen" id="daysOpen" />
						</td>
						<td align="right">Reopen Ticket Frequency :<span
							class="astric">*</span></td>
						<td align="left"><select name="reopenFrequency" class="required comboselect check"
							id="reopenTicketFrequencyId">
								<option value="${catickets.reopenFrequency }">${catickets.reopenFrequency }</option>
						</select>
						<td align="right"></td>
						<td align="left"></td>
					</tr>
					<tr>
						<td align="right">Reason For Reopen : <span class="astric">*</span></td>
						<%-- <c:choose>
							<c:when test="${catickets.reopenFrequency>=1 }">
								<td align="left"><input type="text" value=""
									name="reasonForReopen" id="reasonForReopenId" /></td>
							</c:when>
							<c:otherwise>
								<td align="left"><input type="text" disabled value=""
									name="reasonForReopen" id="reasonForReopenId" /></td>
							</c:otherwise>
						</c:choose> --%>
						<td>
							<select name="reasonForReopen.id"
							class="required comboselect check" id="reasonForReopenId"> 
							<option value="-1" selected="selected">Please Select</option>
						<%-- <option selected="selected" value="${catickets.assigneeId}">${catickets.assigneeId.employeeName}</option> --%>  
								<c:forEach var="reasonForReopen" items="${reasonForReopen}">
								<%-- <option value="${resource.employeeId}">${resource.employeeName}</option> --%>
								<c:choose>
										 <c:when test="${catickets.reasonForReopen.id==reasonForReopen.id}">
												<option value="${reasonForReopen.id}" selected="selected">${reasonForReopen.reason}</option>
										 </c:when>
										 <c:otherwise>
										 	<option value="${reasonForReopen.id}">${reasonForReopen.reason}</option>
										 </c:otherwise>
									</c:choose>
								 </c:forEach> 
						</select>
						</td>
						<td align="right">Justified Hopping :<span class="astric">*</span></td>
						<td align="left"><select name="justifiedHopping" class="required comboselect check"
							id="justifiedHoppingId">
								<option value="${catickets.justifiedHopping}">${catickets.justifiedHopping}</option>
						</select></td>
						<td align="right">Reason For Hopping : <span class="astric">*</span></td>
						<td align="left">
						<%-- <input type="text" value="${catickets.reasonForHopping}"
							name="reasonForHopping" id="reasonForHoppingId" disabled /> --%>
							<select name="reasonForHopping.id"
							class="required comboselect check" id="reasonForHoppingId"> 
							<option value="-1" selected="selected">Please Select</option>
						<%-- <option selected="selected" value="${catickets.assigneeId}">${catickets.assigneeId.employeeName}</option> --%>  
								<c:forEach var="reasonForHopping" items="${reasonForHopping}">
								<%-- <option value="${resource.employeeId}">${resource.employeeName}</option> --%>
								<c:choose>
										 <c:when test="${catickets.reasonForHopping.id==reasonForHopping.id}">
												<option value="${reasonForHopping.id}" selected="selected">${reasonForHopping.reason}</option>
										 </c:when>
										 <c:otherwise>
										 	<option value="${reasonForHopping.id}">${reasonForHopping.reason}</option>
										 </c:otherwise>
									</c:choose>
								 </c:forEach> 
						</select>
							</td>
						<td align="right">Group :<span class="astric">*</span></td>
						<td align="left"><select name="groupName" id="groupId"
							class="required comboselect check">
								<c:choose>
									<c:when test="${catickets.groupName == 'Tier 2'}">
										<option value="Tier 2" selected="selected">Tier 2</option>
										<option value="Unit">Unit</option>
										<option value="Global">Global</option>
										<option value="External">External</option>
										<option value="Security">Security</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:when test="${catickets.groupName == 'Unit'}">
										<option value="Tier 2">Tier 2</option>
										<option value="Unit" selected="selected">Unit</option>
										<option value="Global">Global</option>
										<option value="External">External</option>
										<option value="Security">Security</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:when test="${catickets.groupName == 'Global'}">
										<option value="Tier 2">Tier 2</option>
										<option value="Unit">Unit</option>
										<option value="Global" selected="selected">Global</option>
										<option value="External">External</option>
										<option value="Security">Security</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:when test="${catickets.groupName == 'External'}">
										<option value="Tier 2">Tier 2</option>
										<option value="Unit">Unit</option>
										<option value="Global">Global</option>
										<option value="External" selected="selected">External</option>
										<option value="Security">Security</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:when test="${catickets.groupName == 'Security'}">
										<option value="Tier 2">Tier 2</option>
										<option value="Unit">Unit</option>
										<option value="Global">Global</option>
										<option value="External">External</option>
										<option value="Security" selected="selected">Security</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:when test="${catickets.groupName == 'N.A.'}">
										<option value="Tier 2">Tier 2</option>
										<option value="Unit">Unit</option>
										<option value="Global">Global</option>
										<option value="External">External</option>
										<option value="Security">Security</option>
										<option value="N.A." selected="selected">N.A.</option>
									</c:when>
									<c:otherwise>
							        	<option value="-1" selected="selected">Please Select</option>
							         	<option value="Tier 2">Tier 2</option>
							          	<option value="Unit">Unit</option>
							          	<option value="Global">Global</option>
							          	<option value="External">External</option>
							          	<option value="Security">Security</option>
							          	<option value="N.A.">N.A.</option>
							        </c:otherwise>
								</c:choose>
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
							
							
							<%-- <c:when test="${role == 'ROLE_USER' && loggedInId != catickets.reviewer.employeeId}"> --%>
							<c:when test="${role == 'ROLE_ADMIN' || role == 'ROLE_DEL_MANAGER' || role == 'ROLE_BG_ADMIN' || role == 'ROLE_MANAGER' || loggedInId == catickets.reviewer.employeeId}">
								
								<fmt:formatDate value="${catickets.creationDate}" var="parsedCreationDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedCreationDate}" name="creationDate"
										id="creationDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('creationDateId','yyyyMMdd','arrow','true', 12, 'true','past')"  />
										
								</c:when>
								<c:otherwise>
								<c:choose>
								<c:when test="${empty catickets.creationDate}">
									<fmt:formatDate value="${catickets.creationDate}" var="parsedCreationDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedCreationDate}" name="creationDate"
										id="creationDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('creationDateId','yyyyMMdd','arrow','true', 12, 'true','past')"  />
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${catickets.creationDate}" var="parsedCreationDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedCreationDate}" name="creationDate"
										id="creationDateId" maxlength="25" size="25" readonly="readonly"  style="background-color: #DCDCDC;"/>
								</c:otherwise>
								</c:choose>
								</c:otherwise>
							</c:choose>
							</div>
						</td>
						<td width="11%" align="right">Acknowledged Date & Time Stamp
							:<span class="astric">*</span>
						</td>
						<td width="18%" align="left">
							<div class="positionRel">
							
							<%-- <c:choose>
								<c:when test="${empty catickets.acknowledgedDate}">
									<fmt:formatDate value="${catickets.acknowledgedDate}" var="parsedAcknowledgedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedAcknowledgedDate}" name="acknowledgedDate"
									id="acknowledgedDateId" maxlength="25" size="25"
									onclick="javascript:NewCssCal ('acknowledgedDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${catickets.acknowledgedDate}" var="parsedAcknowledgedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedAcknowledgedDate}" name="acknowledgedDate"
									id="acknowledgedDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"/>
								</c:otherwise>
							</c:choose> --%>
							
							<c:choose>
							
							
							<%-- <c:when test="${role == 'ROLE_USER' && loggedInId != catickets.reviewer.employeeId}"> --%>
							<c:when test="${role == 'ROLE_ADMIN' || role == 'ROLE_DEL_MANAGER' || role == 'ROLE_BG_ADMIN' || role == 'ROLE_MANAGER' || loggedInId == catickets.reviewer.employeeId}">
								
								<fmt:formatDate value="${catickets.acknowledgedDate}" var="parsedAcknowledgedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedAcknowledgedDate}" name="acknowledgedDate"
									id="acknowledgedDateId" maxlength="25" size="25"
									onclick="javascript:NewCssCal ('acknowledgedDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/>
										
								</c:when>
								<c:otherwise>
								<c:choose>
								<c:when test="${empty catickets.acknowledgedDate}">
									<fmt:formatDate value="${catickets.acknowledgedDate}" var="parsedAcknowledgedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedAcknowledgedDate}" name="acknowledgedDate"
									id="acknowledgedDateId" maxlength="25" size="25"
									onclick="javascript:NewCssCal ('acknowledgedDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${catickets.acknowledgedDate}" var="parsedAcknowledgedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedAcknowledgedDate}" name="acknowledgedDate"
									id="acknowledgedDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"/>
								</c:otherwise>
							</c:choose>
								</c:otherwise>
							</c:choose>
							
							</div>
						</td>
						<td width="11%" align="right">Requirement completed Date &
							Time Stamp :<span class="astric">*</span>
						</td>
						<td width="18%" align="left">
							<div class="positionRel" id="reqCompleteDivId">
							
							<%-- <c:choose>
								<c:when test="${not empty catickets.requiredCompletedDate || catickets.reqCompleteFlag == 'N.A.'}">
									<fmt:formatDate value="${catickets.requiredCompletedDate}" var="parsedRequiredCompletedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedRequiredCompletedDate }" name="requiredCompletedDate"
									id="requiredCompletedDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"/><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${catickets.requiredCompletedDate}" var="parsedRequiredCompletedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedRequiredCompletedDate }" name="requiredCompletedDate"
									id="requiredCompletedDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('requiredCompletedDateId','yyyyMMdd','arrow','true', 12, 'true','past')" /><br>
								</c:otherwise>
							</c:choose> --%>
							<c:choose>
							
							
							<%-- <c:when test="${role == 'ROLE_USER' && loggedInId != catickets.reviewer.employeeId}"> --%>
							<c:when test="${role == 'ROLE_ADMIN' || role == 'ROLE_DEL_MANAGER' || role == 'ROLE_BG_ADMIN' || role == 'ROLE_MANAGER' || loggedInId == catickets.reviewer.employeeId}">
								
								<fmt:formatDate value="${catickets.requiredCompletedDate}" var="parsedRequiredCompletedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedRequiredCompletedDate }" name="requiredCompletedDate"
									id="requiredCompletedDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('requiredCompletedDateId','yyyyMMdd','arrow','true', 12, 'true','past')" /><br>
										
								</c:when>
								<c:otherwise>
								<c:choose>
								<c:when test="${not empty catickets.requiredCompletedDate || catickets.reqCompleteFlag == 'N.A.'}">
									<fmt:formatDate value="${catickets.requiredCompletedDate}" var="parsedRequiredCompletedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedRequiredCompletedDate }" name="requiredCompletedDate"
									id="requiredCompletedDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"/><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${catickets.requiredCompletedDate}" var="parsedRequiredCompletedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedRequiredCompletedDate }" name="requiredCompletedDate"
									id="requiredCompletedDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('requiredCompletedDateId','yyyyMMdd','arrow','true', 12, 'true','past')" /><br>
								</c:otherwise>
							</c:choose>
								</c:otherwise>
							</c:choose>
								<br> <select
									name="reqCompleteFlag1" id="reqCompleteFlagId">
									<c:choose>
										<c:when test="${catickets.reqCompleteFlag == 'Yes' }">
											<option	value="Yes" selected="selected">Yes</option>
											<option value="No">No</option>
											<option value="N.A.">N.A.</option>
										</c:when>
										<c:when test="${catickets.reqCompleteFlag == 'No' }">
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
									
									<input type="hidden" name="reqCompleteFlag" value="${catickets.reqCompleteFlag}" id="reqCompleteFlagId1" readonly="readonly" style="background-color: #DCDCDC;"/>
							</div>
						</td>
						<td width="11%" align="right">Analysis completed Date & Time
							Stamp :<span class="astric">*</span>
						</td>
						<td width="18%" align="left">
							<div class="positionRel" id="analysisCompleteDivId">
							<%-- <c:choose>
								<c:when test="${not empty catickets.analysisCompletedDate || catickets.analysisCompleteFlag == 'N.A.'}">
									<fmt:formatDate value="${catickets.analysisCompletedDate}" var="parsedAnalysisCompletedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedAnalysisCompletedDate }" name="analysisCompletedDate"
									id="analysisCompletedDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;" /><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${catickets.analysisCompletedDate}" var="parsedAnalysisCompletedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedAnalysisCompletedDate }" name="analysisCompletedDate"
									id="analysisCompletedDateId" maxlength="25" size="25"
      							 onclick="javascript:NewCssCal ('analysisCompletedDateId','yyyyMMdd','arrow','true', 12, 'true','past')" /><br>
								</c:otherwise>
							</c:choose> --%>
							
							<c:choose>
							
							
							<%-- <c:when test="${role == 'ROLE_USER' && loggedInId != catickets.reviewer.employeeId}"> --%>
							<c:when test="${role == 'ROLE_ADMIN' || role == 'ROLE_DEL_MANAGER' || role == 'ROLE_BG_ADMIN' || role == 'ROLE_MANAGER' || loggedInId == catickets.reviewer.employeeId}">
								
								<fmt:formatDate value="${catickets.analysisCompletedDate}" var="parsedAnalysisCompletedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedAnalysisCompletedDate }" name="analysisCompletedDate"
									id="analysisCompletedDateId" maxlength="25" size="25"
      							 onclick="javascript:NewCssCal ('analysisCompletedDateId','yyyyMMdd','arrow','true', 12, 'true','past')" /><br>
										
								</c:when>
								<c:otherwise>
								<c:choose>
								<c:when test="${not empty catickets.analysisCompletedDate || catickets.analysisCompleteFlag == 'N.A.'}">
									<fmt:formatDate value="${catickets.analysisCompletedDate}" var="parsedAnalysisCompletedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedAnalysisCompletedDate }" name="analysisCompletedDate"
									id="analysisCompletedDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;" /><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${catickets.analysisCompletedDate}" var="parsedAnalysisCompletedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<input type="text" value="${parsedAnalysisCompletedDate }" name="analysisCompletedDate"
									id="analysisCompletedDateId" maxlength="25" size="25"
      							 onclick="javascript:NewCssCal ('analysisCompletedDateId','yyyyMMdd','arrow','true', 12, 'true','past')" /><br>
								</c:otherwise>
							</c:choose>
								</c:otherwise>
							</c:choose>
							
								<br> <select
									name="analysisCompleteFlag1" id="analysisCompleteFlagId">
									<c:choose>
										<c:when test="${catickets.analysisCompleteFlag == 'Yes' }">
											<option	value="Yes" selected="selected">Yes</option>
											<option value="No">No</option>
											<option value="N.A.">N.A.</option>
										</c:when>
										<c:when test="${catickets.analysisCompleteFlag == 'No' }">
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
									
									<input type="hidden" name="analysisCompleteFlag" value="${catickets.analysisCompleteFlag}" id="analysisCompleteFlagId1" readonly="readonly" style="background-color: #DCDCDC;"/>
							</div>
						</td>
					</tr>
					</thead>
					<!-- <tbody id="resourcerequestTableBody"> -->
						<tr>
						
						<td width="11%" align="right">Requirement Analysis (In Hours) :<span class="astric">*</span>
							</td>
							<td width="18%" align="left">
								<div class="positionRel" id="solutionReviewDivId">
									<input type="text" value="${catickets.requirementAnalysisHours }" name="requirementAnalysisHours" 
										id="requirementAnalysisHoursId"  onkeypress="return isNumberKey(event)"/><br>
								
								</div>
							</td>
							
								<td width="11%" align="right">Solution Developed completed
								Date & Time Stamp :<span class="astric">*</span>
							</td>
							<td width="18%" align="left">
								<div class="positionRel" id="solutiondevelopedDivId">
								<%-- <c:choose>
								<c:when test="${not empty catickets.solutiondevelopedDate || catickets.solutionDevelopedFlag == 'N.A.'}">
									<fmt:formatDate value="${catickets.solutiondevelopedDate}" var="parsedSolutionDevelopedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionDevelopedDate }" name="solutiondevelopedDate"
										id="solutiondevelopedDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"
      							  /><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${catickets.solutiondevelopedDate}" var="parsedSolutionDevelopedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionDevelopedDate }" name="solutiondevelopedDate"
										id="solutiondevelopedDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('solutiondevelopedDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/><br>
								</c:otherwise>
							</c:choose> --%>
							
							<c:choose>
							
							
							<%-- <c:when test="${role == 'ROLE_USER' && loggedInId != catickets.reviewer.employeeId}"> --%>
							<c:when test="${role == 'ROLE_ADMIN' || role == 'ROLE_DEL_MANAGER' || role == 'ROLE_BG_ADMIN' || role == 'ROLE_MANAGER' || loggedInId == catickets.reviewer.employeeId}">
								
								<fmt:formatDate value="${catickets.solutiondevelopedDate}" var="parsedSolutionDevelopedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionDevelopedDate }" name="solutiondevelopedDate"
										id="solutiondevelopedDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('solutiondevelopedDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/><br>
										
								</c:when>
								<c:otherwise>
								<c:choose>
								<c:when test="${not empty catickets.solutiondevelopedDate || catickets.solutionDevelopedFlag == 'N.A.'}">
									<fmt:formatDate value="${catickets.solutiondevelopedDate}" var="parsedSolutionDevelopedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionDevelopedDate }" name="solutiondevelopedDate"
										id="solutiondevelopedDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"
      							  /><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${catickets.solutiondevelopedDate}" var="parsedSolutionDevelopedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionDevelopedDate }" name="solutiondevelopedDate"
										id="solutiondevelopedDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('solutiondevelopedDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/><br>
								</c:otherwise>
							</c:choose>
								</c:otherwise>
							</c:choose>
							
								
									<br> <select
										name="solutionDevelopedFlag1" id="solutionDevelopedFlagId">
										<c:choose>
										<c:when test="${catickets.solutionDevelopedFlag == 'Yes' }">
											<option	value="Yes" selected="selected">Yes</option>
											<option value="No">No</option>
											<option value="N.A.">N.A.</option>
										</c:when>
										<c:when test="${catickets.solutionDevelopedFlag == 'No' }">
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
									
									<input type="hidden" name="solutionDevelopedFlag" value="${catickets.solutionDevelopedFlag}" id="solutionDevelopedFlagId1" readonly="readonly" style="background-color: #DCDCDC;"/>
								</div>
							</td>
						
						<td width="11%" align="right">Unit Testing (In Hours) :<span class="astric">*</span>
							</td>
							<td width="18%" align="left">
								<div class="positionRel" id="solutionReviewDivId">
									<input type="text" value="${catickets.unitTestingHours }" name="unitTestingHours" 
										id="unitTestingHoursId"  onkeypress="return isNumberKey(event)"/><br>
								
								</div>
							</td>
							
							<td width="11%" align="right">Solution review Date & Time
								Stamp :<span class="astric">*</span>
							</td>
							<td width="18%" align="left">
								<div class="positionRel" id="solutionReviewDivId">
								<c:choose>
								<c:when test="${not empty catickets.solutionreViewDate || catickets.solutionReadyForReview == 'N.A.'}">
									<fmt:formatDate value="${catickets.solutionreViewDate}" var="parsedSolutionReviewDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionReviewDate }" name="solutionreViewDate"
										id="solutionreViewDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;" /><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${catickets.solutionreViewDate}" var="parsedSolutionReviewDate" pattern="yyyy-MM-dd hh:mm:ss a" />
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
							
						</tr>
						
						<tr>
							
							<td width="11%" align="right">Solution (User Acceptance)
								accepted Date & Time Stamp :<span class="astric">*</span>
							</td>
							<td width="18%" align="left">
								<div class="positionRel" id="solutionAcceptedDivId">
								<%-- <c:choose>
								<c:when test="${not empty catickets.solutionAcceptedDate || catickets.solutionAcceptedFlag == 'N.A.'}">
									<fmt:formatDate value="${catickets.solutionAcceptedDate}" var="parsedSolutionAcceptedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionAcceptedDate }" name="solutionAcceptedDate"
										id="solutionAcceptedDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"
      							  /><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${catickets.solutionAcceptedDate}" var="parsedSolutionAcceptedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionAcceptedDate }" name="solutionAcceptedDate"
										id="solutionAcceptedDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('solutionAcceptedDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/><br>
								</c:otherwise>
							</c:choose> --%>
								<c:choose>
							
							
							<%-- <c:when test="${role == 'ROLE_USER' && loggedInId != catickets.reviewer.employeeId}"> --%>
							<c:when test="${role == 'ROLE_ADMIN' || role == 'ROLE_DEL_MANAGER' || role == 'ROLE_BG_ADMIN' || role == 'ROLE_MANAGER' || loggedInId == catickets.reviewer.employeeId}">
								
								<fmt:formatDate value="${catickets.solutionAcceptedDate}" var="parsedSolutionAcceptedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionAcceptedDate }" name="solutionAcceptedDate"
										id="solutionAcceptedDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('solutionAcceptedDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/><br>
										
								</c:when>
								<c:otherwise>
								<c:choose>
								<c:when test="${not empty catickets.solutionAcceptedDate || catickets.solutionAcceptedFlag == 'N.A.'}">
									<fmt:formatDate value="${catickets.solutionAcceptedDate}" var="parsedSolutionAcceptedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionAcceptedDate }" name="solutionAcceptedDate"
										id="solutionAcceptedDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"
      							  /><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${catickets.solutionAcceptedDate}" var="parsedSolutionAcceptedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedSolutionAcceptedDate }" name="solutionAcceptedDate"
										id="solutionAcceptedDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('solutionAcceptedDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/><br>
								</c:otherwise>
							</c:choose>
								</c:otherwise>
							</c:choose>
									<br> <select
										name="solutionAcceptedFlag1" id="solutionAcceptedFlagId">
										<c:choose>
										<c:when test="${catickets.solutionAcceptedFlag == 'Yes' }">
											<option	value="Yes" selected="selected">Yes</option>
											<option value="No">No</option>
											<option value="N.A.">N.A.</option>
										</c:when>
										<c:when test="${catickets.solutionAcceptedFlag == 'No' }">
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
										<input type="hidden" name="solutionAcceptedFlag" value="${catickets.solutionAcceptedFlag}" id="solutionAcceptedFlagId1" readonly="readonly" style="background-color: #DCDCDC;"/>
								</div>
							</td>
							<td width="11%" align="right">Closed pending customer
								approval Date & Time Stamp :<span class="astric">*</span>
							</td>
							<td width="18%" align="left">
								<div class="positionRel" id="closePendingCustomerApprovalDivId">
								<%-- <c:choose>
								<c:when test="${not empty catickets.closePendingCustomerApprovalDate || catickets.customerApprovalFlag == 'N.A.'}">
									<fmt:formatDate value="${catickets.closePendingCustomerApprovalDate}" var="parsedClosePendingCustomerApprovalDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedClosePendingCustomerApprovalDate }"
										name="closePendingCustomerApprovalDate"
										id="closePendingCustomerApprovalDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"
      							/><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${catickets.closePendingCustomerApprovalDate}" var="parsedClosePendingCustomerApprovalDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedClosePendingCustomerApprovalDate }"
										name="closePendingCustomerApprovalDate"
										id="closePendingCustomerApprovalDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('closePendingCustomerApprovalDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/><br>
								</c:otherwise>
							</c:choose> --%>
								<c:choose>
							
							
							<%-- <c:when test="${role == 'ROLE_USER' && loggedInId != catickets.reviewer.employeeId}"> --%>
							<c:when test="${role == 'ROLE_ADMIN' || role == 'ROLE_DEL_MANAGER' || role == 'ROLE_BG_ADMIN' || role == 'ROLE_MANAGER' || loggedInId == catickets.reviewer.employeeId}">
								
								<fmt:formatDate value="${catickets.closePendingCustomerApprovalDate}" var="parsedClosePendingCustomerApprovalDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedClosePendingCustomerApprovalDate }"
										name="closePendingCustomerApprovalDate"
										id="closePendingCustomerApprovalDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('closePendingCustomerApprovalDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/><br>
										
								</c:when>
								<c:otherwise>
								<c:choose>
								<c:when test="${not empty catickets.closePendingCustomerApprovalDate || catickets.customerApprovalFlag == 'N.A.'}">
									<fmt:formatDate value="${catickets.closePendingCustomerApprovalDate}" var="parsedClosePendingCustomerApprovalDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedClosePendingCustomerApprovalDate }"
										name="closePendingCustomerApprovalDate"
										id="closePendingCustomerApprovalDateId" maxlength="25" size="25" readonly="readonly" style="background-color: #DCDCDC;"
      							/><br>
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${catickets.closePendingCustomerApprovalDate}" var="parsedClosePendingCustomerApprovalDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<input type="text" value="${parsedClosePendingCustomerApprovalDate }"
										name="closePendingCustomerApprovalDate"
										id="closePendingCustomerApprovalDateId" maxlength="25" size="25" onclick="javascript:NewCssCal ('closePendingCustomerApprovalDateId','yyyyMMdd','arrow','true', 12, 'true','past')"/><br>
								</c:otherwise>
							</c:choose>
								</c:otherwise>
							</c:choose>
									<br> <select
										name="customerApprovalFlag1" id="customerApprovalFlagId">
										<c:choose>
										<c:when test="${catickets.customerApprovalFlag == 'Yes' }">
											<option	value="Yes" selected="selected">Yes</option>
											<option value="No">No</option>
											 <option value="N.A.">N.A.</option> 
										</c:when>
										<c:when test="${catickets.customerApprovalFlag == 'No' }">
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
										<input type="hidden" name="customerApprovalFlag" value="${catickets.customerApprovalFlag}" id="customerApprovalFlagId1" readonly="readonly" style="background-color: #DCDCDC;"/>
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
								<c:when test="${catickets.problemManagementFlag == 'Yes' }">
									<option value="No">No</option>
									<option value="Yes" selected="selected">Yes</option>
									<option value="N.A.">N.A.</option>
								</c:when>
								<c:when test="${catickets.problemManagementFlag == 'No' }">
									<option value="No" selected="selected">No</option>
									<option value="Yes">Yes</option>
									<option value="N.A.">N.A.</option>
								</c:when>
								<c:when test="${catickets.problemManagementFlag == 'N.A.' }">
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
							
								<c:choose>
										 <c:when test="${catickets.process.id == processes.id}">
												<option value="${catickets.process.id}" selected="selected">${catickets.process.processName}</option>
										 </c:when>
										 <c:otherwise>
										 	<option value="${processes.id}">${processes.processName}</option>
										 </c:otherwise>
									</c:choose>
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
							<c:choose>
								<c:when test="${catickets.subProcess.id == subprocesses.id}">
									<option value="${catickets.subProcess.id}" selected="selected">${catickets.subProcess.subProcessName}</option>
								</c:when>
								<c:otherwise>
								 	<option value="${subprocesses.id}">${subprocesses.subProcessName}</option>
								</c:otherwise>
							</c:choose>
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
							id="rootCauseId">
										 <c:if test="${empty catickets.rootCause.id}">
												<option value="-1" selected="selected">Please Select</option>
										 </c:if>
								<c:forEach var="rc" items="${rootcause}">
								<c:choose>
									<c:when  test="${catickets.rootCause.id == rc.id}">
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
								<input type="text" value="${catickets.ZREQNo }" name="ZREQNo" id="ZREQNo"
									maxlength="10" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="11%" align="right">Parent Ticket No :<span
							class="astric">*</span></td>
						<td width="18%" align="left">
							<div class="positionRel">
								<input type="text" value="${catickets.parentTicketNo }" name="parentTicketNo"
									id="parentTicketNo" maxlength="10" />
							</div>
						</td>
						<td align="right">Comment :<span class="astric">*</span>
						</td>
						<td align="left"><textarea name="comment" cols=""
								id="comment" rows="2" class="string">${catickets.comment }</textarea></td>
						<%-- <sec:authorize
							access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_MANAGER')"> --%>
							<c:if test="${role == 'ROLE_ADMIN' || role == 'ROLE_DEL_MANAGER' || role == 'ROLE_BG_ADMIN' || role == 'ROLE_MANAGER' || loggedInId == catickets.reviewer.employeeId}">
							<td width="11%" align="right">Justified Problem Management :<span
								class="astric">*</span>
							</td>
							<td align="left"><select name="justifiedProblemManagement"
								class="required comboselect check" id="justifiedProblemManagement">
									<!-- <option value="Yes">Yes</option>
									<option value="No">No</option> -->
									<c:choose>
										 <c:when test="${catickets.justifiedProblemManagement=='Yes'}">
												<option value="Yes" selected="selected">Yes</option>
												<option value="No">No</option>
										 </c:when>
										 <c:otherwise>
										 	<option value="No" selected="selected">No</option>
										 	<option value="Yes">Yes</option>
										 </c:otherwise>
									</c:choose>
							</select></td>
							</c:if>
						<%-- </sec:authorize> --%>
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
<c:if test="${not empty catickets}">
		
		<%-- <sec:authorize
			access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_MANAGER')"> --%>
			<c:if test="${role == 'ROLE_ADMIN' || role == 'ROLE_DEL_MANAGER' || role == 'ROLE_BG_ADMIN' || role == 'ROLE_MANAGER' || loggedInId == catickets.reviewer.employeeId}">
			<div id="solutionReviewMainId">
			<h1 id="solutionReviewHeadingId"  style="display:inline-block;">Solution Review</h1>
			<%-- <select name="solutionStatusFlag" style="font: 15px;"
							id="solutionReviewId" class="required comboselect check">
								<c:choose>
									<c:when test="${catickets.solutionStatusFlag == 'Yes'}">
										<option value="Yes" selected="selected">Yes</option>
										<option value="No">No</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:when test="${catickets.solutionStatusFlag == 'No'}">
										<option value="Yes">Yes</option>
										<option value="No" selected="selected">No</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:otherwise>
										<option value="Yes">Yes</option>
										<option value="No">No</option>
										<option value="N.A." selected="selected">N.A.</option>
									</c:otherwise>
								</c:choose>
						</select> --%>
			<br><br>
			<div class="center_div" id="solutionReviewDetaileDivId">
				<div style="float: left;margin-left:24px;margin-top:5px" class="addNewSolutionReview btnIcon resourceBtnIcon left325">
					<span class="addNewContainer"><a href="javascript:void(0)"
						class="blue_link" id="addNewSolutionReviewId"><img
							src="${pageContext.request.contextPath}/resources/images/addUser.gif"
							width="16" height="22"> Add New </a></span>
				</div>
				<div style="width: 1290px; overflow-x: scroll;">
					<table class="dataTbl display tablesorter dataTable"
						id="solutionReviewTable"
						style="margin-top: 30px; margin-left: 0px !important; width: 100% !important">
						<thead>
							<tr><th align="center" valign="middle" style="display: none;">Id</th>
								<th align="center" valign="middle">Landscape</th>
								<th align="center" valign="middle">Module</th>
								<th align="center" valign="middle">Reviewer</th>
								<th align="center" valign="middle">Review Date</th>
								<th align="center" valign="middle">CA Ticket Number</th>
								<th align="center" valign="middle">Assignee</th>
									<th align="center" valign="middle">Is the issue understanding correct?</th>
									<th align="center" valign="middle">Any alternate solution thought and proposed?</th>
									<th align="center" valign="middle">Is the solution appropriate?</th>
									<th align="center" valign="middle">Agree with RCA?</th>
									<th align="center" valign="middle">Rating</th>
									<th align="center" valign="middle">Comments</th>
									<th align="center" valign="middle">Edit</th>
									<th align="center" valign="middle">Delete</th>

								</tr>

							</thead>
							<tbody id="solutionReviewTableBody">
								<c:forEach var="solRev" items="${catickets.solutionReview}">
									<tr><td value="${solRev.id }" align="center" style="display: none;">${solRev.id }</td>
										<td align="center" value="${catickets.landscapeId.id }">${catickets.landscapeId.landscapeName }</td>
										<td align="center" value="${catickets.moduleId.id }">${catickets.moduleId.moduleName}</td>
										<td align="center" value="${catickets.reviewer.employeeId }">${catickets.reviewer.employeeName}</td>
										<fmt:formatDate value="${solRev.reviewDate}" var="reviewDate" pattern="yyyy-MM-dd hh:mm:ss a" />
										<td align="center" value="${reviewDate }">${reviewDate}</td>
										<td align="center" value="${catickets.caTicketNo }">${catickets.caTicketNo }</td>
										<td align="center" value="${catickets.assigneeId.employeeId }">${catickets.assigneeId.employeeName }</td>
										<td align="center" value="${solRev.isTheIssueUnderstandingCorrect }">${solRev.isTheIssueUnderstandingCorrect}</td>
										<td align="center" value="${solRev.alternateSolution }">${solRev.alternateSolution }</td>
										<td align="center" value="${solRev.isSolAppropriate }">${solRev.isSolAppropriate }</td>
										<td align="center" value="${solRev.agreeWithRca}">${solRev.agreeWithRca}</td>
										<td align="center" value="${solRev.rating}">${solRev.rating}</td>
										<td align="center" value="${solRev.comments}">${solRev.comments }</td>
										<td align="center"><a href="javascript:void(0)" class="solutionReviewEditEnable">Edit</a></td>
										<td align="center"><a href="javascript:void(0)" class="deleteRowSolutionReview">Delete</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>


				</div>
			</div>
</div>
			<br>

			<h1 style="display:inline-block;">Defect Log</h1>&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
			<select name="defectStatusFlag" style="font: 15px;"
							id="defectLogId" class="required comboselect check">
								<c:choose>
									<c:when test="${catickets.defectStatusFlag == 'Yes' || not empty catickets.defectLog}">
										<option value="Yes" selected="selected">Yes</option>
										<option value="No">No</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:when test="${catickets.defectStatusFlag == 'No'}">
										<option value="Yes">Yes</option>
										<option value="No" selected="selected">No</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:otherwise>
										<option value="Yes">Yes</option>
										<option value="No">No</option>
										<option value="N.A." selected="selected">N.A.</option>
									</c:otherwise>
								</c:choose>
						</select>
			<br><br>
			<div class="center_div" id="defectLogDivId">
				<div style="float: left;margin-left:24px;margin-top:5px" class="addDefectLog btnIcon resourceBtnIcon left325">
					<span class="addNewContainer"><a href="javascript:void(0)"
						class="blue_link" id="addDefectLogId"><img
							src="${pageContext.request.contextPath}/resources/images/addUser.gif"
							width="16" height="22"> Add New </a></span>
				</div>
				<div style="width: 1290px; overflow-x: scroll;">


					<table class="dataTbl display tablesorter dataTable"
						id="defectLogTable"
						style="margin-top: 30px; margin-left: 0px !important; width: 100% !important">
						<thead>
							<tr><th align="center" valign="middle" style="display: none;">Id</th>
								<th align="center" valign="middle">ZREQ/CA No</th>
								<th align="center" valign="middle">Defect Type</th>
								<th align="center" valign="middle">Defect Description</th>
								<th align="center" valign="middle">Internal-External</th>
								<th align="center" valign="middle">Defect Category</th>
								<th align="center" valign="middle">Severity</th>
								<th align="center" valign="middle">Defect Status</th>
								<th align="center" valign="middle">Identified Date</th>
								<th align="center" valign="middle">Identified by</th>
								<th align="center" valign="middle">Identified Phase</th>
								<th align="center" valign="middle">Module Name</th>
								<th align="center" valign="middle">Injected Phase</th>
								<th align="center" valign="middle">Work Product Name</th>
								<th align="center" valign="middle">Reopened</th>
								<th align="center" valign="middle">Requirement ID/Ticket#</th>
								<th align="center" valign="middle">Defect Root Cause</th>
								<th align="center" valign="middle">Category of Root Cause</th>
								<th align="center" valign="middle">Resolved by</th>
								<th align="center" valign="middle">Resolved Date</th>
								<th align="center" valign="middle">Closed Date</th>
								<th align="center" valign="middle">Edit</th>
								<th align="center" valign="middle">Delete</th>

							</tr>

						</thead>
						<tbody id="defectLogTableBody">
							<c:forEach var="log" items="${catickets.defectLog}">
								<tr><td value="${log.id }" align="center" style="display: none;">${log.id }</td>
									<td align="center" value="${catickets.ZREQNo }">${catickets.ZREQNo }</td>
									<td align="center" value="${log.defectType }">${log.defectType }</td>
									<td align="center" value="${log.defectDescription }">${log.defectDescription }</td>
									<td align="center" value="${log.internalExternal }">${log.internalExternal }</td>
									<td align="center" value="${log.defectCategory }">${log.defectCategory }</td>
									<td align="center" value="${log.severity }">${log.severity }</td>
									<td align="center" value="${log.defectStatus }">${log.defectStatus }</td>
									<fmt:formatDate value="${log.identifiedDate}" var="identifiedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<td align="center" value="${identifiedDate }">${identifiedDate}</td>
									<td align="center" value="${log.identifiedBy }">${log.identifiedBy }</td>
									<td align="center" value="${log.identifiedPhase }">${log.identifiedPhase }</td>
									<td align="center" value="${catickets.moduleId.moduleName }">${catickets.moduleId.moduleName }</td>
									<td align="center" value="${log.injectedPhase }">${log.injectedPhase }</td>
									<td align="center" value="${log.workProductName }">${log.workProductName }</td>
									<td align="center" value="${log.reopened }">${log.reopened }</td>
									<td align="center" value="${catickets.caTicketNo }">${catickets.caTicketNo }</td>
									<td align="center" value="${log.defectRootCause}">${log.defectRootCause}</td>
									<td align="center" value="${log.categoryOfRootCause }">${log.categoryOfRootCause }</td>
									<td align="center" value="${log.resolvedBy }">${log.resolvedBy }</td>
									<fmt:formatDate value="${log.resolvedDate}" var="resolvedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<td align="center" value="${resolvedDate }">${resolvedDate }</td>
									<fmt:formatDate value="${log.closedDate}" var="closedDate" pattern="yyyy-MM-dd hh:mm:ss a" />
									<td align="center" value="${closedDate}">${closedDate}</td>
									<td align="center"><a href="javascript:void(0)" class="defectLogEditEnable">Edit</a></td>
									<td align="center"><a href="javascript:void(0)" class="deleteRowDefectLog">Delete</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>


				</div>
			</div>
			<br>
			
			<%-- </sec:authorize> --%>
			</c:if>
			
			<c:if test="${not empty catickets}">
		<h1 style="display:inline-block;">Rework</h1> &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
			<select name="reworkStatusFlag" style="font: 15px;"
							id="reworkId" class="required comboselect check">
								<c:choose>
									<c:when test="${catickets.reworkStatusFlag == 'Yes'}">
										<option value="Yes" selected="selected">Yes</option>
										<option value="No">No</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:when test="${catickets.reworkStatusFlag == 'No'}">
										<option value="Yes">Yes</option>
										<option value="No" selected="selected">No</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:otherwise>
										<option value="Yes">Yes</option>
										<option value="No">No</option>
										<option value="N.A." selected="selected">N.A.</option>
									</c:otherwise>
								</c:choose>
						</select>
		<br><br>
		<div class="center_div" id="reworkDivId">
			<div style="float: left;margin-left:24px;margin-top:5px" class="addRework btnIcon resourceBtnIcon left325">
				<span class="addNewContainer"><a href="javascript:void(0)"
					class="blue_link" id="addReworkId"><img
						src="${pageContext.request.contextPath}/resources/images/addUser.gif"
						width="16" height="22"> Add New </a></span>
			</div>
			<div style="width: 1290px; overflow-x: scroll;">
				<table class="dataTbl display tablesorter dataTable"
					id="reworkTable"
					style="margin-top: 30px; margin-left: 0px !important; width: 100% !important">
					<thead>
						<tr><th align="center" valign="middle" style="display: none;">Id</th>
							<th align="center" valign="middle">Rework Type</th>
							<th align="center" valign="middle">Start Date Time Stamp</th>
							<th align="center" valign="middle">End Date Time Stamp</th>
							<th align="center" valign="middle">Hours</th>
							<th align="center" valign="middle">Justified</th>
							<th align="center" valign="middle">Edit</th>
							<th align="center" valign="middle">Delete</th>

						</tr>

					</thead>
					<tbody id="reworkTableBody">
						<c:forEach var="rework" items="${catickets.rework}">
							<tr><td value="${rework.id }" align="center" style="display: none;">${rework.id }</td>
								<td align="center" value="${rework.reworkType }">${rework.reworkType }</td>
								<fmt:formatDate value="${rework.startDateTimestamp}" var="startDateTimestamp" pattern="yyyy-MM-dd hh:mm:ss a" />
								<td align="center" value="${startDateTimestamp}">${startDateTimestamp}</td>
								<fmt:formatDate value="${rework.endDateTimestamp}" var="endDateTimestamp" pattern="yyyy-MM-dd hh:mm:ss a" />
								<td align="center" value="${endDateTimestamp}">${endDateTimestamp}</td>
								<td align="center" value="${rework.hourse }">${rework.hourse }</td>
								<td align="center" value="${rework.justified }">${rework.justified }</td>
								<td align="center"><a href="javascript:void(0)" class="reworkEditEnable">Edit</a></td>
								<td align="center"><a href="javascript:void(0)" class="deleteRowRework">Delete</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>


			</div>

			<!-- <div style="float: right; margin-top: 5px; padding-top: 20px;">
				<input type="submit" name="save" value="save" id="save"> <input
					type="button" name="back" value="Back">
			</div> -->
		</div>
		
		<h1 style="display:inline-block;margin-top:7px;">T3 Contribution Screen</h1>
		<select name="t3StatusFlag" style="font: 15px;"
							id="t3ContributionId" class="required comboselect check">
								<c:choose>
									<c:when test="${catickets.t3StatusFlag == 'Yes'}">
										<option value="Yes" selected="selected">Yes</option>
										<option value="No">No</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:when test="${catickets.t3StatusFlag == 'No'}">
										<option value="Yes">Yes</option>
										<option value="No" selected="selected">No</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:otherwise>
										<option value="Yes">Yes</option>
										<option value="No">No</option>
										<option value="N.A." selected="selected">N.A.</option>
									</c:otherwise>
								</c:choose>
						</select>
		<br><br>
		<div class="center_div" id="t3ContributionDivId">
			<div style="float: left;margin-left:24px;margin-top:5px" class="addNewContribution btnIcon resourceBtnIcon left325">
				<span class="addNewContainer"><a href="javascript:void(0)"
					class="blue_link" id="addNewContrbutionId"><img
						src="${pageContext.request.contextPath}/resources/images/addUser.gif"
						width="16" height="22"> Add New </a></span>
			</div>
			<div style="width: 1290px; overflow-x: scroll;">
				<table class="dataTbl display tablesorter dataTable"
					id="contributionTable"
					style="margin-top: 30px; margin-left: 0px !important; width: 100% !important">
					<thead>
						<tr><th align="center" valign="middle" style="display: none;">Id</th>
							<th align="center" valign="middle">Ticket No.</th>
							<th align="center" valign="middle">Module</th>
							<th align="center" valign="middle">Date Contacted</th>
							<th align="center" valign="middle">Description</th>
							<th align="center" valign="middle">Reason For Help From T3</th>
							<th align="center" valign="middle">No. Of Hours Taken</th>
							<th align="center" valign="middle">Justified</th>
							<th align="center" valign="middle">Edit</th>
							<th align="center" valign="middle">Delete</th>

						</tr>

					</thead>
					<tbody id="contributionTableBody">

						 <c:forEach var="t3" items="${catickets.t3Contribution}">
							<tr><td value="${t3.id }" align="center" style="display: none;">${t3.id }</td>
								<td value="${catickets.caTicketNo }" align="center">${catickets.caTicketNo }</td>
								<td value="${catickets.moduleId.moduleName }" align="center">${catickets.moduleId.moduleName }</td>
								<fmt:formatDate value="${t3.dateContacted}" var="dateContacted" pattern="yyyy-MM-dd hh:mm:ss a" />
								<td value="${dateContacted }" align="center">${dateContacted}</td>
								<td value="${t3.description }" align="center">${t3.description }</td>
								<td value="${t3.reasonForHelp }" align="center">${t3.reasonForHelp }</td>
								<td value="${t3.noOfhoursTaken }" align="center">${t3.noOfhoursTaken }</td>
								<td value="${t3.justified }" align="center">${t3.justified }</td>
								<td align="center"><a  href="javascript:void(0)" class="t3EditEnable">Edit</a></td>
								<td align="center"><a href="javascript:void(0)" class="deleteRowt3">Delete</a></td>
							</tr>
						</c:forEach> 
					</tbody>
				</table>


			</div>

			<!-- <div style="float: right; margin-top: 5px; padding-top: 20px;">
				<input type="submit" name="save" value="save" id="save"> <input
					type="button" name="back" value="Back">
			</div> -->
		</div>
		
		</c:if>
			<%-- <sec:authorize
			access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_MANAGER')"> --%>
			<c:if test="${role == 'ROLE_ADMIN' || role == 'ROLE_DEL_MANAGER' || role == 'ROLE_BG_ADMIN' || role == 'ROLE_MANAGER' || loggedInId == catickets.reviewer.employeeId}">
			<br>
			<h1 style="display:inline-block;">CROP</h1>&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
			<select name="cropStatusFlag" style="font: 15px;"
							id="cropId" class="required comboselect check">
								<c:choose>
									<c:when test="${catickets.cropStatusFlag == 'Yes'}">
										<option value="Yes" selected="selected">Yes</option>
										<option value="No">No</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:when test="${catickets.cropStatusFlag == 'No'}">
										<option value="Yes">Yes</option>
										<option value="No" selected="selected">No</option>
										<option value="N.A.">N.A.</option>
									</c:when>
									<c:otherwise>
										<option value="Yes">Yes</option>
										<option value="No">No</option>
										<option value="N.A." selected="selected">N.A.</option>
									</c:otherwise>
								</c:choose>
						</select>
			<div class="center_div" id="cropDivId" style="margin-top:23px;">
				<div style="float: left;margin-left:24px;margin-top:5px" class="addNewCrop btnIcon resourceBtnIcon left325">
					<span class="addNewContainer"><a href="javascript:void(0)"
						class="blue_link" id="addNewCropId"><img
							src="${pageContext.request.contextPath}/resources/images/addUser.gif"
							width="16" height="22"> Add New </a></span>
				</div>
				<div style="width: 1290px; overflow-x: scroll;">
					<table class="dataTbl display tablesorter dataTable" id="cropTable"
						style="margin-top: 30px; margin-left: 0px !important; width: 100% !important">
						<thead>
							<tr><th align="center" valign="middle" style="display: none;">Id</th>
								<th align="center" valign="middle">Title</th>
								<th align="center" valign="middle">Module</th>
								<th align="center" valign="middle">Description</th>
								<th align="center" valign="middle">Source</th>
								<th align="center" valign="middle">Benefit Type</th>
								<th align="center" valign="middle">Total business hours
									saved</th>
								<th align="center" valign="middle">Total IT hours saved</th>
								<th align="center" valign="middle">Savings in USD</th>
								<th align="center" valign="middle">Justified</th>
								<th align="center" valign="middle">Edit</th>
								<th align="center" valign="middle">Delete</th>

							</tr>

						</thead>
						<tbody id="cropTableBody">
							<c:forEach var="crop" items="${catickets.crop}">
								<tr><td value="${crop.id }" align="center" style="display: none;">${crop.id }</td>
									<td align="center" value="${crop.title }">${crop.title }</td>
									<td align="center" value="${catickets.moduleId.moduleName }">${catickets.moduleId.moduleName }</td>
									<td align="center" value="${crop.description }">${crop.description }</td>
									<td align="center" value="${crop.source }">${crop.source }</td>
									<td align="center" value="${crop.benefitType }">${crop.benefitType }</td>
									<td align="center" value="${crop.totalBusinessHrsSaved }">${crop.totalBusinessHrsSaved }</td>
									<td align="center" value="${crop.totalITHoursSaved }">${crop.totalITHoursSaved }</td>
									<td align="center" value="${crop.savingsInUSD }">${crop.savingsInUSD }</td>
									<td align="center" value="${crop.justified }">${crop.justified }</td>
									<td align="center"><a href="javascript:void(0)" class="cropEditEnable">Edit</a></td>
									<td align="center"><a href="javascript:void(0)" class="deleteRowCrop">Delete</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>


				</div>

				<!-- <div style="float: right; margin-top: 5px; padding-top: 20px;">
				<input type="submit" name="save" value="save" id="save"> <input
					type="button" name="back" value="Back">
			</div> -->
			</div>
		<%-- </sec:authorize> --%>
		</c:if>
		</c:if>
		<br>
		
		<br>
		<div class="button_div">
			<div style="float: right; margin-top: 5px; padding-top: 20px;">
				<input type="submit" name="save" value="Save" id="save" onclick="return validateForm(true,'../../caticket/isTicketAlreadyExist');"> <input
					type="button" name="back" value="Back" onclick="backPage();">
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

