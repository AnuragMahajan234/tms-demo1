 <%@page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
/* #tableResourceData resourceHData > tr,td{
border:1px solid #ccc;
} */

</style>

<spring:url value="/resources/styles/style.css?ver=${app_js_ver}" var="style_css"/>
	<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<spring:url value="/resources/js-framework/date.js?ver=${app_js_ver}" var="jquery_date_js" />
<spring:url value="/resources/js-user/validations.js?ver=${app_js_ver}" var="validations_js" />

<!-- Code for single select - start -->
	<spring:url value="/resources/css/select2.min.css?ver=${app_js_ver}"
    var="select2_css" />
<link href="${select2_css}" rel="stylesheet" type="text/css"></link>

<spring:url value="/resources/js/select2.min.js?ver=${app_js_ver}"
    var="select2_js" />
<script src="${select2_js}" type="text/javascript"></script>
<!-- Code for single select - end -->

<Script Language="JavaScript"> 
var objappVersion = navigator.appVersion; 
var objAgent = navigator.userAgent; 
var objbrowserName = navigator.appName; 
var objfullVersion = ''+parseFloat(navigator.appVersion); 
var objBrMajorVersion = parseInt(navigator.appVersion,10); 
var objOffsetName,objOffsetVersion,ix;
 </script>
<script>
var parentBu=null;
var vLoan=null;
var vTransfer=null;
var vContractor =null;
var fromTransferDate;

$(document).ready(function(){
	if(localStorage.getItem("butEnable")){
		$('#loadResourcesId').show();
		//localStorage.removeItem("butEnable");
	}
	$( ".comboselectScroll" ).combobox({
		 'maxHeight': 100,
		 "maxOptionSize": 1000

	});

	 function ownershipDropdown(){	
		if(vLoan==null && vTransfer==null&& vContractor==null){
			//vLoan = $("#ownership option[value='7']").detach();
			vTransfer = $("#ownership option[value='8']").detach();
			vContractor = $("#ownership option[value='6']").detach();
		}
	} 
	
	$("#eventId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);			
	$("#eventId").parent().find("a.ui-button").button("disabled");	
	disableAll();
	 
	
	    $(".firstcal").datepicker({
	        dateFormat: "dd-M-yy",
	        onSelect: function(dateText, instance) {
	     
	            date = $.datepicker.parseDate(instance.settings.dateFormat, dateText, instance.settings);
	            date.setMonth(date.getMonth() + 3);
	            fromTransferDate = date;
	            $(".secondcal").datepicker("setDate", date);
	             var ownership = document.getElementById("ownership");
		    	var ownershipId = ownership.options[ownership.selectedIndex].value;
		    	if(ownershipId==7){
		    		$(".secondcal").datepicker("setDate", date);
		    		//$("#transferTo").removeAttr("disabled","disabled");
		    	}else{
		    		$("#transferTo").val("");
		    		//$("#transferTo").attr("disabled","disabled");
		    	} 
	            
	            
	        }
	    });
	    $(".secondcal").datepicker({
	        dateFormat: "dd-M-yy",
	        beforeShow: function(){
	        	$(this).datepicker("option", "minDate", document.getElementById("transferDate").value);
	       							},
	        onSelect: function(dateText, instance) {
	            date = $.datepicker.parseDate(instance.settings.dateFormat, dateText, instance.settings);
	           if(Date.parse(date) > Date.parse(fromTransferDate))
	        	   showWarning("You have selected more than three months");
	     
	          
	           
	        }
	    });
	 
	 $( "#eventId" ).on("change",function() {	
		 disabledAllField();
		var selectVal = $("#eventId option[value="+document.getElementById("eventId").value+"]").text();
		 if(selectVal==("Permanent BU Transfer")||selectVal==("Permanent BG Transfer")||selectVal==("Permanent Client Site Transfer")||selectVal==("Permanent Relocation")||selectVal==("Permanent Company Transfer")){
			$("#transferTo").val("");
		}
		<c:forEach var="selectedEvent" items="${event}">
			if ('${selectedEvent.event}' == selectVal) {
				
				//get description for the description selected
				if ('${selectedEvent.description}' != null && '${selectedEvent.description}' != "") {
					//$("#description").val('${selectedEvent.description}');
					description.innerHTML = '#'+'${selectedEvent.description}';
				} else {
					description.innerHTML = '# No description available for the selected event';
				}
				// if RM1 field is true then enable RM1 field here
				if ('${selectedEvent.currentReportingManager}'  == "Y") {
						$("#currentReportingManager").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false);			
						$("#currentReportingManager").removeAttr("disabled","disabled");
						$("#currentReportingManager").parent().find("a.ui-button").css("pointer-events","visible");		
				}
				// if RM2 field is true then enable RM2 field here
				if ('${selectedEvent.currentReportingTwo}'  == "Y") {
					$("#currentReportingManagerTwo").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false)
					$("#currentReportingManagerTwo").removeAttr("disabled","disabled");
					$("#currentReportingManagerTwo").parent().find("a.ui-button").css("pointer-events","visible");		
				}
				// if grade field is true then enable grade field here
				if ('${selectedEvent.gradeId}'  == "Y") {
					$("#gradeId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);
					$("#gradeId").parent().find("a.ui-button").css("pointer-events","visible");		
				}
				// if grade field is true then enable deployment location field here
				if ('${selectedEvent.payrollLocation}'  == "Y") {
					$("#deploymentLocationId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);
					$("#deploymentLocationId").parent().find("a.ui-button").css("pointer-events","visible");		
				}
				// if grade field is true then enable deployment location field here
				if ('${selectedEvent.locationId}'  == "Y") {
					$("#locationId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);
					$("#locationId").parent().find("a.ui-button").css("pointer-events","visible");		
				}
				// if grade field is true then enable deployment location field here
				if ('${selectedEvent.ownership}'  == "Y" ) {
					$("#ownership").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);
					$("#ownership").parent().find("a.ui-button").css("pointer-events","visible");;	
					ownershipDropdown();
				}
				
				if ('${selectedEvent.employeeCategory}'  == "Y" ) {
					$("#employeeCategory").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);
					$("#employeeCategory").parent().find("a.ui-button").css("pointer-events","visible");		
				}
				// if grade field is true then enable deployment location field here
				if ('${selectedEvent.designationId}'  == "Y") {
					$("#designationId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);
					$("#designationId").parent().find("a.ui-button").css("pointer-events","visible");		
				}
				if ('${selectedEvent.buId}'  == "Y") {
					$("#buId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);
					$("#buId").parent().find("a.ui-button").css("pointer-events","visible");		
				}
				if ('${selectedEvent.currentBuId}'  == "Y") {
					$("#currentBuId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);
					$("#currentBuId").parent().find("a.ui-button").css("pointer-events","visible");		
				}
				if ('${selectedEvent.emailId}'  == "Y" ) {
					$("#emailId").removeAttr("disabled","disabled");
				}
				/* if ('${selectedEvent.yashEmpId}'  == "Y" ) {
					$("#yashEmpId").removeAttr("disabled","disabled");
				} */
				if ('${selectedEvent.contactNumberTwo}'  == "Y") {
					$("#contactNumberTwo").removeAttr("disabled","disabled");
				}
				if ('${selectedEvent.contactNumber}'  == "Y") {
					$("#contactNumber").removeAttr("disabled","disabled");
				}
				if ('${selectedEvent.dateOfJoining}'  == "Y" ) {
					$("#dateOfJoining").removeAttr("disabled","disabled");
				}
				if ('${selectedEvent.confirmationDate}'  == "Y") {
					$("#confirmationDate").removeAttr("disabled","disabled");
				}
				if ('${selectedEvent.lastAppraisal}'  == "Y" ) {
					$("#lastAppraisal").removeAttr("disabled","disabled");
				}
				if ('${selectedEvent.penultimateAppraisal}'  == "Y") {
					$("#penultimateAppraisal").removeAttr("disabled","disabled");
				}
				
				if ('${selectedEvent.releaseDate}'  == "Y") {
					$("#releaseDate").removeAttr("disabled","disabled");
				}
				if ('${selectedEvent.transferDate}'  == "Y" ) {
					$("#transferDate").removeAttr("disabled","disabled");
				}
				//Added
				if ('${selectedEvent.endTransferDate}'  == "Y" ) {
					$("#transferTo").removeAttr("disabled","disabled");
				}
				
				if ('${selectedEvent.bGHName}'  == "Y" ) {
					$("#bGHName").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false);
					$("#bGHName").removeAttr("disabled","disabled");
					$("#bGHName").parent().find("a.ui-button").css("pointer-events","visible");		
				}
				if ('${selectedEvent.bGHComments}'  == "Y" ) {
					$("#bGHComments").removeAttr("disabled","disabled");
				}
				if ('${selectedEvent.bGCommentsTimestamp}'  == "Y") {
					$("#bGCommentsTimestamp").removeAttr("disabled","disabled");
				}
				if ('${selectedEvent.bUHName}'  == "Y" ) {
					$("#bUHName").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false);
					$("#bUHName").removeAttr("disabled","disabled");
					$("#bUHName").parent().find("a.ui-button").css("pointer-events","visible");		
				}
				if ('${selectedEvent.bUHComments}'  == "Y" ) {
					$("#bUHComments").removeAttr("disabled","disabled");
				}
				if ('${selectedEvent.bUCommentsTimestamp}'  == "Y") {
					$("#bUCommentsTimestamp").removeAttr("disabled","disabled");
				}
				if ('${selectedEvent.hRName}'  == "Y" ) {
					$("#hRName").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false);
					$("#hRName").removeAttr("disabled","disabled");
					$("#hRName").parent().find("a.ui-button").css("pointer-events","visible");		
				}
				if ('${selectedEvent.hRComments}'  == "Y" ) {
					$("#hRComments").removeAttr("disabled","disabled");
				}
				if ('${selectedEvent.hRCommentsTimestamp}'  == "Y" ) {
					$("#hRCommentsTimestamp").removeAttr("disabled","disabled");
				}
			}
		</c:forEach>
	}); 
		
		function disableAll() {
			 //disable each field
     		//$("#yashEmpId").val(loadedData.yashEmpId);
			$("#yashEmpId").attr("disabled","disabled"); 
			//disable all text boxes
			$("#emailId").attr("disabled","disabled");
			$("#contactNumber").attr("disabled","disabled");
			$("#contactNumberTwo").attr("disabled","disabled");
			$("#dateOfJoining").attr("disabled","disabled");
			$("#confirmationDate").attr("disabled","disabled");
			$("#lastAppraisal").attr("disabled","disabled");
			$("#penultimateAppraisal").attr("disabled","disabled");
			$("#releaseDate").attr("disabled","disabled");
			$("#transferDate").attr("disabled","disabled"); 
			
			//added
			$("#transferTo").attr("disabled","disabled");
			$("#bGHComments").attr("disabled","disabled"); 
			$("#bGCommentsTimestamp").attr("disabled","disabled"); 
			$("#bUHComments").attr("disabled","disabled"); 
			$("#bUCommentsTimestamp").attr("disabled","disabled"); 
			$("#hRComments").attr("disabled","disabled"); 
			$("#hRCommentsTimestamp").attr("disabled","disabled"); 
			
		}
	 
	//Redirected from resource Page 
	if("${resourceData}"!=null && "${resourceData}"!=""){			
		
		//Select Resource
	   // $("#resourceId option[value=${resourceData.employeeId}]").prop('selected', true);	
	  //  $("#resourceId").parent().find("input").val($("#resourceId option[value=${resourceData.employeeId}]").text());
	    //added to enable event combo box even after saving the resource(isha)
	    $("#eventId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);			
 		$("#eventId").parent().find("a.ui-button").button("enable");	
 		
	    var formattedDOJ;
	    var formattedConfirmationDate;
 		var formattedTransferDate;
 		//added
 		var formattedendTransferDate;
 		var formattedappraisalDate1;
 		var formattedappraisalDate2;
 		var formattedReleaseDate;
 		var formattedbGCommentsTimestamp;
 		var formattedbUCommentsTimestamp;
 		
 
    if("${resourceData.dateOfJoining}" !="")
	    	formattedDOJ  =formateDate("${resourceData.dateOfJoining}");
	    else
	    	formattedDOJ="${resourceData.dateOfJoining}";
	    	if("${resourceData.confirmationDate}"!="")
	    		{
	    		formattedConfirmationDate=	formateDate("${resourceData.confirmationDate}");
	    		}
	    	else
	    		formattedConfirmationDate="${resourceData.confirmationDate}";
	    		if("${resourceData.transferDate}"!="")
	    		{
	    			formattedTransferDate=	formateDate("${resourceData.transferDate}");
	    		}
	    	else
	    		formattedTransferDate="${resourceData.transferDate}";
	    		
	    		//added
	    		
	    		
	    			if("${resourceData.endTransferDate}"!="")
	    		{
	    				formattedendTransferDate=	formateDate("${resourceData.endTransferDate}");
	    		}
	    	else
	    		formattedendTransferDate="${resourceData.endTransferDate}";
	    		
	    		
	    		if("${resourceData.lastAppraisal}"!="")
	    		{
	    			formattedappraisalDate1=	formateDate("${resourceData.lastAppraisal}");
	    		}
	    	else
	    		formattedappraisalDate1="${resourceData.lastAppraisal}";
	    		
	    		if("${resourceData.penultimateAppraisal}"!="")
	    		{
	    			formattedappraisalDate2=	formateDate("${resourceData.penultimateAppraisal}");
	    		}
	    	else
	    		formattedappraisalDate2="${resourceData.penultimateAppraisal}";
	    	 
	    		if("${resourceData.releaseDate}"!="")
	    		{
	    			formattedReleaseDate=	formateDate("${resourceData.releaseDate}");
	    		}
	    	else{
	    	
	    		formattedReleaseDate="${resourceData.releaseDate}";
	    	}
	    		if("${resourceData.bGCommentsTimestamp}" !="")
	    			formattedbGCommentsTimestamp  =formateDate("${resourceData.bGCommentsTimestamp}");
	    	    else
	    	    	formattedbGCommentsTimestamp="${resourceData.bGCommentsTimestamp}";	
	    	    	
	var rm1 = JSON.parse('{"employeeId":"${resourceData.currentReportingManager.employeeId}", "yashEmpId":"${resourceData.currentReportingManager.yashEmpId}", "employeeName":"${resourceData.currentReportingManager.employeeName}"}');
	var rm2 = JSON.parse('{"employeeId":"${resourceData.currentReportingManagerTwo.employeeId}", "yashEmpId":"${resourceData.currentReportingManagerTwo.yashEmpId}", "employeeName":"${resourceData.currentReportingManagerTwo.employeeName}"}');
	
	var bGHName = JSON.parse('{"employeeId":"${resourceData.bGHName.employeeId}", "userName":"${resourceData.bGHName.userName}"}');
	var bUHName = JSON.parse('{"employeeId":"${resourceData.bUHName.employeeId}", "userName":"${resourceData.bUHName.userName}"}');
	var hRName =  JSON.parse('{"employeeId":"${resourceData.hRName.employeeId}", "userName":"${resourceData.hRName.userName}"}');
	
	populatedata("${resourceData.designationId.id}","${resourceData.gradeId.id}","${resourceData.locationId.id}","${resourceData.deploymentLocation.id}",
			"${resourceData.buId.id}","${resourceData.currentBuId.id}", rm1,rm2,"${resourceData.ownership.id}","${resourceData.yashEmpId}",
			"${resourceData.emailId}","${resourceData.contactNumber}","${resourceData.contactNumberTwo}","${resourceData.employeeId}",
			formattedTransferDate,formattedDOJ,formattedappraisalDate1,formattedappraisalDate2,formattedReleaseDate,formattedConfirmationDate,
			"${resourceData.bGHComments}",formattedbGCommentsTimestamp,"${resourceData.bUHComments}","${resourceData.hRComments}",
			("${resourceData.employeeCategory}" != null) ? "${resourceData.employeeCategory.id}" :"",formattedendTransferDate,"${resourceData.userName}",
			bGHName, bUHName, hRName);		
	}	
	$("#transferDate").datepicker({changeMonth: true,changeYear: true, dateFormat:'dd-M-yy'}).val();
	//Added
	$("#transferTo").datepicker({changeMonth: true,changeYear: true , dateFormat:'dd-M-yy'}).val();
	
	$("#dateOfJoining").datepicker({changeMonth: true,changeYear: true , dateFormat:'dd-M-yy'}).val();
	$("#lastAppraisal").datepicker({changeMonth: true,changeYear: true , dateFormat:'dd-M-yy'}).val();
	$("#penultimateAppraisal").datepicker({changeMonth: true,changeYear: true, dateFormat:'dd-M-yy'}).val();
	$("#releaseDate").datepicker({changeMonth: true,changeYear: true, dateFormat:'dd-M-yy'}).val();
	$("#confirmationDate").datepicker({changeMonth: true,changeYear: true, dateFormat:'dd-M-yy'}).val();
	$("#bGCommentsTimestamp").datepicker({changeMonth: true,changeYear: true, dateFormat:'dd-M-yy'}).val();
	$("#bUCommentsTimestamp").datepicker({changeMonth: true,changeYear: true, dateFormat:'dd-M-yy'}).val();
	$("#hRCommentsTimestamp").datepicker({changeMonth: true,changeYear: true, dateFormat:'dd-M-yy'}).val();
 
	/* $("select#ownership").on("change",function(){		
		//on selection of Loan		
		if($("#ownership option[value="+document.getElementById("ownership").value+"]").text()=='Loan'){					
			$("#buId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);			
			$("#buId").parent().find("a.ui-button").button("disabled");	
			
			//Set default parent Bu id
			if(parentBu!=null){
				$("#buId option[value="+parentBu+"]").prop('selected', true);			
				$("#buId").parent().find("input").val($("#buId option[value="+parentBu+"]").text());
			}
		}
		else{
			$("#buId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);			
			$("#buId").parent().find("a.ui-button").button("enable");			
		}
	}) */
	
	//on condition trasfer currentbuid & parent buid will be same
	/* $("select#currentBuId").on("change",function(){	
		if($("#ownership option[value="+document.getElementById("ownership").value+"]").text()=='Transfer'){
			var buid=$(this).val();
			$("#buId option[value="+buid+"]").prop('selected', true);			
			$("#buId").parent().find("input").val($("#buId option[value="+buid+"]").text());
		}
	}) */
	
	

//Ajax Call on selection of resource
$("select#resourceId").on("change",function(){	
	var rid=$(this).val();		
	var loadedData;
	if(!rid) {
		stopProgress();
		return false;
	}
	$.ajax({				
        url: 'loanAndTransfer/loadResourceData',
     	contentType: "application/json",
     	async:false,
     	data: {"resourceid":rid},  	
     	success: function(response) { 
     		loadedData=response;
     		
     		//enable Event field
     		$("#eventId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);			
     		$("#eventId").parent().find("a.ui-button").button("enable");	
     		
     		 //disable each field
     		$("#yashEmpId").val(loadedData.yashEmpId);
			$("#yashEmpId").attr("disabled","disabled");
			
			disabledAllField();
			
     	},
     	error: function(errorResponse)
 	    { 	    	
     		showError(errorResponse);
 	    } 
     	
 	})
 	if(loadedData != null) {
	 	populatedata(loadedData.designationId.id,loadedData.gradeId.id,loadedData.locationId.id,(loadedData.deploymentLocation!=null)?loadedData.deploymentLocation.id:null,
	 			loadedData.buId.id,loadedData.currentBuId.id,loadedData.currentReportingManager,loadedData.currentReportingManagerTwo,
	 			loadedData.ownership.id,loadedData.yashEmpId,loadedData.emailId,loadedData.contactNumber,loadedData.contactNumberTwo,
	 			loadedData.employeeId,loadedData.transferDate,loadedData.dateOfJoining,loadedData.lastAppraisal,loadedData.penultimateAppraisal,
	 			loadedData.releaseDate,loadedData.confirmationDate,loadedData.bGHComments,loadedData.bGCommentsTimestamp,loadedData.bUHComments,
	 			loadedData.hRComments,(loadedData.employeeCategory != null) ? loadedData.employeeCategory.id:"",loadedData.endTransferDate,
	 			loadedData.userName,loadedData.bGHName,loadedData.bUHName,loadedData.hRName);
 	}
	
})

function disabledAllField() {
	
	$("#ownership").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		//.css({"background-color":"#CCCCCC !important","color":"rgb(84, 84, 84) !important"})
		$("#ownership").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);
		$("#ownership").parent().find("a.ui-button").css("pointer-events","none");

	$("#employeeCategory").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#employeeCategory").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);
		$("#employeeCategory").parent().find("a.ui-button").css("pointer-events","none");
	
	$("#designationId").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#designationId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);
		$("#designationId").parent().find("a.ui-button").css("pointer-events","none");
	
	$("#gradeId").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#gradeId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);
		$("#gradeId").parent().find("a.ui-button").css("pointer-events","none");

	$("#locationId").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#locationId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);
		$("#locationId").parent().find("a.ui-button").css("pointer-events","none");

	
	$("#deploymentLocationId").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#deploymentLocationId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);
		$("#deploymentLocationId").parent().find("a.ui-button").css("pointer-events","none");

	$("#buId").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#buId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);
		$("#buId").parent().find("a.ui-button").css("pointer-events","none");

	$("#currentBuId").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#currentBuId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);
		$("#currentBuId").parent().find("a.ui-button").css("pointer-events","none");

	$("#currentReportingManager").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#currentReportingManager").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true);
		$("#currentReportingManager").attr("disabled","disabled");
		$("#currentReportingManager").parent().find("a.ui-button").css("pointer-events","none");
	
	$("#currentReportingManagerTwo").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#currentReportingManagerTwo").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true);
		$("#currentReportingManagerTwo").attr("disabled","disabled");
		$("#currentReportingManagerTwo").parent().find("a.ui-button").css("pointer-events","none");

	
	$("#bGHName").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#bGHName").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true);
		$("#bGHName").attr("disabled","disabled");
		$("#bGHName").parent().find("a.ui-button").css("pointer-events","none");

	$("#bUHName").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#bUHName").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true);
		$("#bUHName").attr("disabled","disabled");
		$("#bUHName").parent().find("a.ui-button").css("pointer-events","none");
	
	$("#hRName").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#hRName").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true);
		$("#hRName").attr("disabled","disabled");
		$("#hRName").parent().find("a.ui-button").css("pointer-events","none");

	
	//disable all text boxes
	$("#emailId").attr("disabled","disabled");
	$("#contactNumber").attr("disabled","disabled");
	$("#contactNumberTwo").attr("disabled","disabled");
	$("#dateOfJoining").attr("disabled","disabled");
	$("#confirmationDate").attr("disabled","disabled");
	$("#lastAppraisal").attr("disabled","disabled");
	$("#penultimateAppraisal").attr("disabled","disabled");
	$("#releaseDate").attr("disabled","disabled");
	$("#transferDate").attr("disabled","disabled"); 
	//added    
	$("#transferTo").attr("disabled","disabled");
	
	$("#bGHComments").attr("disabled","disabled"); 
	$("#bGCommentsTimestamp").attr("disabled","disabled"); 
	$("#bUHComments").attr("disabled","disabled"); 
	$("#bUCommentsTimestamp").attr("disabled","disabled"); 
	$("#hRComments").attr("disabled","disabled"); 
	$("#hRCommentsTimestamp").attr("disabled","disabled"); 
};

function validateEmail(emailValue) {
	if(emailValue != '') {
    	var emailDomain = emailValue.split('@').pop();
    	if(emailDomain== emailValue){
    		return false;
    	}
    	if(emailDomain == null )//"yash.com".toUpperCase()  ----.toUpperCase()
    		return false;
    } 		    
    return true;
 }
	
function onEventSelect() {
	//alert("inside onEventSelect");
}	 
	
//Save function with validation
$( "#save" ).click(function(event) {	
	var comboBoxBlur = $("select.required").parent("td").find("span.ui-combobox input");
	var validationFlag = validateCombo(comboBoxBlur);
	if(validationFlag){
		startProgress();	
	}
	
	//enable all fields before saving
	$("#yashEmpId").removeAttr("disabled","disabled");
	$("#ownership").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);	
	$("#employeeCategory").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);	
	$("#designationId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);
	$("#gradeId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);
	$("#locationId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);			
	$("#deploymentLocationId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);	
	$("#buId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);			
	$("#currentBuId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);	
	$("#currentReportingManager").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false);
	$("#currentReportingManager").removeAttr("disabled","disabled");
	$("#currentReportingManagerTwo").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false);
	$("#currentReportingManagerTwo").removeAttr("disabled","disabled");
	$("#bGHName").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false);
	$("#bGHName").removeAttr("disabled","disabled");
	$("#bUHName").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false);
	$("#bUHName").removeAttr("disabled","disabled");
	$("#hRName").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false);
	$("#hRName").removeAttr("disabled","disabled");
	$("#emailId").removeAttr("disabled","disabled");
	$("#contactNumber").removeAttr("disabled","disabled");
	$("#contactNumberTwo").removeAttr("disabled","disabled");
	$("#dateOfJoining").removeAttr("disabled","disabled");
	$("#confirmationDate").removeAttr("disabled","disabled");
	$("#lastAppraisal").removeAttr("disabled","disabled");
	$("#penultimateAppraisal").removeAttr("disabled","disabled");
	$("#releaseDate").removeAttr("disabled","disabled");
	$("#transferDate").removeAttr("disabled","disabled");
	
	//added
	$("#transferTo").removeAttr("disabled","disabled");
	
	$("#bGHComments").removeAttr("disabled","disabled"); 
	$("#bGCommentsTimestamp").removeAttr("disabled","disabled"); 
	$("#bUHComments").removeAttr("disabled","disabled"); 
	$("#bUCommentsTimestamp").removeAttr("disabled","disabled"); 
	$("#hRComments").removeAttr("disabled","disabled"); 
	$("#hRCommentsTimestamp").removeAttr("disabled","disabled"); 
	event.preventDefault();
    $("#resourceLoanTransferForm" ).triggerHandler("submitForm" );    
	var errorMsg = "";	
	var parentBu= document.getElementById("buId").value;
	var currentBu=document.getElementById("currentBuId").value;	
	var location=document.getElementById("locationId").value;	
	var deploymentLocationId=document.getElementById("deploymentLocationId").value;
	var rm=document.getElementById("currentReportingManager").value;	
	var rm2=document.getElementById("currentReportingManagerTwo").value;
	//var ltStatus=document.getElementById("ltStatus").value;
  	var designationIds=document.getElementById("designationId").value;
  	var gradeId=document.getElementById("gradeId").value;
  	var eventId=document.getElementById("eventId").value;
  	var ownership=document.getElementById("ownership").value;
  	var yashEmpId= document.getElementById("yashEmpId").value;
  	var resourceId=$("select#resourceId").val();
  	var transferDateFrom= document.getElementById("transferDate").value;
	var transferDateTo= document.getElementById("transferTo").value;
	var dateOfJoining = document.getElementById("dateOfJoining").value;
  	var confirmationDate = document.getElementById("confirmationDate").value;
	var releaseDate = document.getElementById("releaseDate").value;
	var appraisalDate = document.getElementById("lastAppraisal").value;
	var appraisalDate1 = document.getElementById("penultimateAppraisal").value;  
 // var confirmationDate=document.getElementById("confirmationDate").value;
  	var emailId = document.getElementById("emailId").value;
  	var employeeCategory = document.getElementById("employeeCategory").value;
  
  	var	bGHComments = document.getElementById("bGHComments").value.length;
    var	bUHComments = document.getElementById("bUHComments").value.length;
    var	hRComments = document.getElementById("hRComments").value.length;
  	
  	//Current BU != Parent BU then Loan
   		if($("#ownership option[value="+document.getElementById("ownership").value+"]").text()=='Loan' && (parentBu==currentBu))
  			{		
  				validationFlag = false;
		 		errorMsg=errorMsg+"For Loan Resource Parent BG-BU & Current BG-BU should not be same";
		 	} 
  	
  		//a.	Current BU=Parent BU then Owned or Transfer
  	 	if(($("#ownership option[value="+document.getElementById("ownership").value+"]").text()=='Owned') && parentBu!=currentBu ){
  			validationFlag = false;
		 	errorMsg=errorMsg+"For Owned Resource Parent BG-BU & Current BG-BU should be same";
  				} 
 
	 	if(($("#ownership option[value="+document.getElementById("ownership").value+"]").text()=='Transfer') && parentBu==currentBu ){
  			validationFlag = false;
		 	errorMsg=errorMsg+"For Transfer Resource Parent BG-BU & Current BG-BU should be Different";
  				} 
  	if(dateOfJoining==''){
  		validationFlag = false;
  		 errorMsg=errorMsg+"\u2022 Please select DOJ <br />";
  	}
  
  	if(!resourceId || resourceId==-1){
		 validationFlag = false;
		 errorMsg=errorMsg+" \u2022 Please select Resource <br />";
	 }
  	 /*if(ltStatus==-1){
		 validationFlag = false;
		 errorMsg=errorMsg+"<br />Please select Loan or Transfer Status";
	 }*/
  	 if(designationIds=="-1"){
		 validationFlag = false;
		 errorMsg=errorMsg+"\u2022 Please select Designtaion <br />";
	 }
	 if(gradeId=="-1"){
		 validationFlag = false;
		 errorMsg=errorMsg+"\u2022 Please select Grade <br />";
	 }	 
	 if(eventId=="-1"){
		 validationFlag = false;
		 errorMsg=errorMsg+"\u2022 Please select Event <br />";
	 }	
	 if(location=="-1"){
		 validationFlag = false;
		 errorMsg=errorMsg+"\u2022 Please select Location <br />";
	 }	 
	 
	 if(deploymentLocationId=="-1"){
		 validationFlag = false;
		 errorMsg=errorMsg+"\u2022 Please select Deployment Location <br />";
	 }
	 
	 
	 if(ownership=="-1"){
		 validationFlag = false;
		 errorMsg=errorMsg+"\u2022 Please select ownership status <br />";
	 }
	 
	 if(employeeCategory=="-1"){
		 validationFlag = false;
		 errorMsg=errorMsg+"\u2022 Please select Employee Category status <br />";
	 }
	 if(parentBu=="-1" && $("#buId").parent().find("input.ui-autocomplete-input").prop("disabled")==false){
		 validationFlag = false;
		 errorMsg=errorMsg+"\u2022 Please select Parent BG-BU <br />";
	 }
	 if(currentBu=="-1"){		 		 
		 validationFlag = false;		 
		 errorMsg=errorMsg+"\u2022 Please select Current BG-BU <br />";
	 }
	
	 if((rm=="-1"||rm2=="-1")||(!rm2 || !rm)){
		 validationFlag = false;
		 errorMsg=errorMsg+"\u2022 Please select Reporting Manager <br />";
	 }
	
	if(emailId == '')
		{
		 validationFlag = false;
		 errorMsg=errorMsg+"\u2022 Please Enter Email Id <br />";
	 
		}
	
	if (!(validateEmail($("#emailId").val()))){
		validationFlag = false;
		errorMsg = errorMsg + "\u2022 Please enter valid Email Address! <br />";
	}
	
	var contactNumber = document.getElementById("contactNumber").value;
		 var phoneRegExp = 
	        /^((\+)?[1-9]{1,2})?([-\s\.])?((\(\d{1,4}\))|\d{1,4})(([-\s\.])?[0-9]{1,12}){1,2}$/;
	        
	   if(contactNumber != null && contactNumber != "" && contactNumber != 0){
		 if(!phoneRegExp.test(contactNumber)){
			validationFlag = false;
			 errorMsg = errorMsg + "\u2022 Please enter only numeric values into Employee Contact number field! <br />";
		
		 } 
	   } 
	   
	   var contactNumberTwo = document.getElementById("contactNumberTwo").value;
		       
	   if(contactNumberTwo != null && contactNumberTwo != "" && contactNumberTwo != 0){
		 if(!phoneRegExp.test(contactNumberTwo)){
			validationFlag = false;
			 errorMsg = errorMsg + "\u2022 Please enter only numeric values into Employee Contact number Two field! <br />";
		
		 } 
	   } 
	   if(!validDates1(dateOfJoining, appraisalDate)){
		   	validationFlag = false;
           errorMsg = errorMsg + "\u2022 Appraisal Date1 should be greater than the date of joining ! <br />";
       }
		if(!validDates1(dateOfJoining, appraisalDate1)){
           validationFlag = false;
           errorMsg = errorMsg + "\u2022 Appraisal Date2 should be greater than the date of joining ! <br />";
       }
      	if(!validDates1(appraisalDate, appraisalDate1)){
           validationFlag = false;
           errorMsg = errorMsg + "\u2022 Appraisal Date2 should be greater than Appraisal Date 1 ! <br />";
       }
		if (!validDates1(dateOfJoining, confirmationDate)){
			validationFlag = false;
			errorMsg = errorMsg + "\u2022 Confirmation Date should be greater than the Date of Joining ! <br />";
			
		}
		if (!validDates1(dateOfJoining, releaseDate)){
			validationFlag = false;
			errorMsg = errorMsg + "\u2022 Release Date should be greater than the Date of Joining ! <br />";
			
		}						
		if (!validDates(confirmationDate, releaseDate)){
			validationFlag = false;
			errorMsg = errorMsg + "\u2022 Release Date should be greater than the Confirmation Date ! <br />";
		}
		//added by pratyoosh//
		if(!validDates1(transferDateFrom, transferDateTo)){
           validationFlag = false;
            
           errorMsg= errorMsg + "\u2022  Transfer Date To should be greater than the Transfer Date From ! <br />";

		}
		if(!validDates1(transferDateFrom, releaseDate )){
	           validationFlag = false;
	            
	           errorMsg= errorMsg + "\u2022  ReleaseDate should be greater than the Transfer Date From ! <br />";

			}
		
		if(!validDates1(transferDateTo, releaseDate )){
	           validationFlag = false;
	            
	           errorMsg= errorMsg + "\u2022  ReleaseDate should be greater than the Transfer Date To ! <br />";

			}
		
		if(bGHComments>256){
			validationFlag = false;
             errorMsg = "BGH Comments can't be more than 256 characters";
            
         }
		if(bUHComments>256){
			validationFlag = false;
             errorMsg = "BUH Comments can't be more than 256 characters";
            
         }
		if(hRComments>256){
			validationFlag = false;
             errorMsg = "HR Comments can't be more than 256 characters";
            
         }
		
		
		/* var e=document.getElementById("currentReportingManager");
		var temp = e.options[e.selectedIndex].value;
		var f=document.getElementById("currentReportingManagerTwo");
		var temp2= f.options[f.selectedIndex].value; */
	  
		/* if((document.getElementById("eventId").value==2)){
		if(temp==rmOne)
			{
			validationFlag = false;
			errorMsg = errorMsg + "\u2022 Please change RM1 before saving <br/>!";
			}
		

		if(temp2==rm2)
			{
			validationFlag = false;
			errorMsg = errorMsg + "\u2022 Please change RM2 before saving <br/>!";
			}
		} */
		
		 var dateofjoining=document.getElementById("dateOfJoining").value;
		 var monthNames = ["Jan", "Feb", "Mar",
		                   "Apr", "May", "Jun", "Jul",
		                   "Aug", "Sep", "Oct",
		                   "Nov", "Dec"];
		 
		    var today = new Date();
		    var curr_date = today.getDate();
		    var curr_month = monthNames[today.getMonth()];
		    var curr_year = today.getFullYear();
		   // var todayDate = new Date(curr_month+"/"+curr_date+"/"+curr_year);
		  // var todayDate = curr_month+"/"+curr_date+"/"+curr_year;
		    var todayDate = curr_date+"-"+curr_month+"-"+curr_year;
		   if (!validDatesForJoining(dateofjoining, todayDate)) {
		    	 errorMsg = errorMsg + "\u2022 Date Of Joining  should not be greater than the current date! <br />";
		    	 validationFlag = false;	 
           }
		   
		    var employeeId = $("#resourceLoanTransferForm input[name=employeeId]").val() ;
		    $.ajax({
				async: false, // !important
				url: "${pageContext.request.contextPath}/resources/validateReleaseDate",
				data: "releaseDate=" + releaseDate+"&employeeId="+employeeId,
				success: function( data ) {
					
				},
		    error:function(data){
		    	 errorMsg = errorMsg + "\u2022 Release Date should be entered once All Allocations have been released! <br />";
		    	 validationFlag = false;	 
		    }
			});
		if(!validationFlag) {			
			stopProgress();
			if(errorMsg.length > 0) 							
				showError(errorMsg);							
			return;
		}	
		/** Submit the form when no validation is failed */
		else {	
			$("#buId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);			
			$("#buId").parent().find("a.ui-button").button("enable");
   			stopProgress();
			var successMsg ="Resource Details have been saved successfully";
			
			var employeeId = 0;
			var result = true;
			employeeId = $("#resourceLoanTransferForm input[name=employeeId]").val() ;
			 
		 
			$.ajax({
				async: false, // !important
				url: "loanAndTransfer/validate",
				data: "value=" + yashEmpId + "&employeeId="+employeeId,
				success: function( data ) {
					$("#eventId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);			
		     		$("#eventId").parent().find("a.ui-button").button("enable");	
					if ( data.result != true ) {
							result = false;
						//isDuplicateEmailOrID = true;
					} 
				}
			});

		if(result) {	
	            $("#resourceLoanTransferForm").submit();
           }
       else {
	          showError("YashEmpId "+yashEmpId+ " already Exist Please enter another one");
	           return ;
	        } 		
	    showSuccess(successMsg);
   }					
})

function validateCombo(comboBoxArray){
		var varcount = 0;
		$(comboBoxArray).each(function(index) {
			var selectDdVal =$(this).val();
			if(selectDdVal == '' || selectDdVal=="-1"){
			$(this).addClass("brdrRed").next("a").addClass("brdrRed");
			varcount++;
		}else{
			$(this).removeClass("brdrRed").next("a").removeClass("brdrRed");
		}
		});
		if(varcount>0)return false;
		else return true;
	
		
	}
	
	
	$("#resourceId").select2({
		openOnEnter: false,
		ajax: {
			url: "/rms/loanAndTransfer/activeUserList",
			dataType: 'json',
			data: function (params) { 
				return { userInput: params.term || '',};
			},
			processResults: function (data, params) {
				return {
					results: formatData(JSON.parse(data.activeUserList)),
				};
			},
		},	
		minimumInputLength: 3,
		allowClear: true,
		createTag: function(params) {
	        return undefined;
	   },
	   placeholder: 'Search Recipient(s)'
	});
	

	$("#currentReportingManager").select2({
		openOnEnter: false,
		ajax: {
			url: "/rms/loanAndTransfer/activeUserList",
			dataType: 'json',
			data: function (params) { 
				return { userInput: params.term || '',};
			},
			processResults: function (data, params) {
				return {
					results: formatData(JSON.parse(data.activeUserList)),
				};
			},
		},	
		minimumInputLength: 3,
		allowClear: true,
		createTag: function(params) {
	        return undefined;
	   },
	   placeholder: 'Search Recipient(s)'
	});
	
	
	$("#currentReportingManagerTwo").select2({
		openOnEnter: false,
		ajax: {
			url: "/rms/loanAndTransfer/activeUserList",
			dataType: 'json',
			data: function (params) { 
				return { userInput: params.term || '',};
			},
			processResults: function (data, params) {
				return {
					results: formatData(JSON.parse(data.activeUserList)),
				};
			},
		},	
		minimumInputLength: 3,
		allowClear: true,
		createTag: function(params) {
	        return undefined;
	   },
	   placeholder: 'Search Recipient(s)'
	});
	
	$("#bGHName").select2({
		openOnEnter: false,
		ajax: {
			url: "/rms/loanAndTransfer/activeUserListByBGADMINROLE",
			dataType: 'json',
			data: function (params) { 
				return { userInput: params.term || '',};
			},
			processResults: function (data, params) {
				return {
					results: formatData(JSON.parse(data.activeUserList)),
				};
			},
		},	
		minimumInputLength: 3,
		allowClear: true,
		createTag: function(params) {
	        return undefined;
	   },
	   placeholder: 'Search Recipient(s)'
	});
	
	
	$("#bUHName").select2({
		openOnEnter: false,
		ajax: {
			url: "/rms/loanAndTransfer/activeUserListByBGADMINROLE",
			dataType: 'json',
			data: function (params) { 
				return { userInput: params.term || '',};
			},
			processResults: function (data, params) {
				return {
					results: formatData(JSON.parse(data.activeUserList)),
				};
			},
		},	
		minimumInputLength: 3,
		allowClear: true,
		createTag: function(params) {
	        return undefined;
	   },
	   placeholder: 'Search Recipient(s)'
	});
	
	$("#hRName").select2({
		openOnEnter: false,
		ajax: {
			url: "/rms/loanAndTransfer/activeUserListByHRROLE",
			dataType: 'json',
			data: function (params) { 
				return { userInput: params.term || '',};
			},
			processResults: function (data, params) {
				return {
					results: formatData(JSON.parse(data.activeUserList)),
				};
			},
		},	
		minimumInputLength: 3,
		allowClear: true,
		createTag: function(params) {
	        return undefined;
	   },
	   placeholder: 'Search Recipient(s)'
	});
	
	
	function formatData(userList) {
		$.map(userList, function(item, idx) {
			item.id = item.employeeId;
		 	item.text = item.firstName + " " + item.lastName + "(" + item.yashEmpId + ")";
		});

		return userList;
	}
	
	
});
var rmOne="";
var rm2="";
function populatedata(designationId,gradeId,locationId,deploymentLocationId,buId,currentbuId,rm1,rm2,ownershipId,yashEmpId,emailId,contactNumber,contactNumberTwo,employeeId,transferDate,dateOfJoining,lastAppraisal,penultimateAppraisal,releaseDate,confirmationDate,bGHComments,bGCommentsTimestamp,bUHComments,hRComments,employeecategoryID,endTransferDate,empName,bGHName,bUHName,hRName){

	parentBu=buId;
	//Select Designation
	empName = empName.replace(/\./g, " ");
	$("#resourceId").append("<option selected='selected' value="+format(employeeId)+">"+ empName+ " ["+yashEmpId+"]</option>");
	
	$("#designationId option[value="+format(designationId)+"]").prop('selected', true);			
	$("#designationId").parent().find("input").val($("#designationId option[value="+format(designationId)+"]").text());
	$("#employeeId").val(employeeId);
	$("#yashEmpId").val(format(yashEmpId));
	$("#emailId").val(emailId);
	$("#contactNumber").val(format(contactNumber));
	$("#contactNumberTwo").val(format(contactNumberTwo));
	
	//Select Deployment Location	
	if(deploymentLocationId!=null && deploymentLocationId!=""){
		$("#deploymentLocationId option[value="+format(deploymentLocationId)+"]").prop('selected', true);			
		$("#deploymentLocationId").parent().find("input").val($("#deploymentLocationId option[value="+format(deploymentLocationId)+"]").text());
	}	
	//Select locationId
	$("#locationId option[value="+format(locationId)+"]").prop('selected', true);			
	$("#locationId").parent().find("input").val($("#locationId option[value="+format(locationId)+"]").text());
	
	//Select Grade 
	$("#gradeId option[value="+format(gradeId)+"]").prop('selected', true);			
	$("#gradeId").parent().find("input").val($("#gradeId option[value="+format(gradeId)+"]").text());
	
	
	//Select buid
	$("#buId option[value="+format(buId)+"]").prop('selected', true);			
	$("#buId").parent().find("input").val($("#buId option[value="+format(buId)+"]").text());
	
	//Select currentBuId 		
	$("#currentBuId option[value="+format(currentbuId)+"]").prop('selected', true);			
	$("#currentBuId").parent().find("input").val($("#currentBuId option[value="+format(currentbuId)+"]").text());
	
	if(rm1!=null && rm1.employeeId != null)
	{
		 $("#currentReportingManager").append("<option selected='selected' value="+format(rm1.employeeId)+">"+rm1.employeeName + "["+rm1.yashEmpId+"]</option>");
	  /* var optionExists = ($("#currentReportingManager option[value="+format(rm1.employeeId)+"]").length > 0);
	  if(!optionExists){
		 
		  $("#currentReportingManager").append("<option selected='selected' value="+format(rm1.employeeId)+">"+rm1.employeeName + "["+rm1.yashEmpId+"]</option>");
	  }
	  else{
         $("#currentReportingManager option[value="+format(rm1.employeeId)+"]").prop('selected', true);
	  }
  	     $("#currentReportingManager").parent().find("input").val($("#currentReportingManager option[value="+format(rm1.employeeId)+"]").text());
    */
    }
	
	else{
		$("#currentReportingManager option[value=-1]").prop('selected', true);
	//	$("#currentReportingManager").parent().find("input").val($("#currentReportingManager option[value=-1]").text());
	}
		
	fromTransferDate =   transferDate;
 		
	$("#transferDate").val(transferDate);
	$("#transferTo").val(endTransferDate);
	$("#dateOfJoining").val(dateOfJoining);
	$("#lastAppraisal").val(lastAppraisal);
	$("#penultimateAppraisal").val(penultimateAppraisal);
	$("#releaseDate").val(releaseDate);
	$("#confirmationDate").val(confirmationDate);
		
	//Select currentReportingManagerTwo		
	if(rm2 != null && rm2.employeeId != null) {
		
		 $("#currentReportingManagerTwo").append("<option selected='selected' value="+format(rm2.employeeId)+">"+rm2.employeeName+"["+rm2.yashEmpId +"]</option>");
		 
		/*  var optionExists = ($("#currentReportingManagerTwo option[value="+format(rm2.employeeId)+"]").length > 0);
		 if(!optionExists){
		  $("#currentReportingManagerTwo").append("<option selected='selected' value="+format(rm2.employeeId)+">"+rm2.employeeName+"["+rm2.yashEmpId +"]</option>");
		 }
		 else{
	        $("#currentReportingManagerTwo option[value="+format(rm2.employeeId)+"]").prop('selected', true);
		 }
	  	    $("#currentReportingManagerTwo").parent().find("input").val($("#currentReportingManagerTwo option[value="+format(rm2.employeeId)+"]").text());
	   */
	   
		}
	else
	{
	    $("#currentReportingManagerTwo option[value=-1]").prop('selected', true);
	  //  $("#currentReportingManagerTwo").parent().find("input").val($("#currentReportingManagerTwo option[value=-1]").text());
	}
	//Select ownership
	$("#ownership option[value="+format(ownershipId)+"]").prop('selected', true);	
	$("#ownership").parent().find("input").val($("#ownership option[value="+format(ownershipId)+"]").text());	
	

	$("#employeeCategory option[value="+format(employeecategoryID)+"]").prop('selected', true);	
	$("#employeeCategory").parent().find("input").val($("#employeeCategory option[value="+format(employeecategoryID)+"]").text());
	
	var e=document.getElementById("currentReportingManager");
	rmOne = e.options[e.selectedIndex].value;
	
	var f=document.getElementById("currentReportingManagerTwo");
	rm2 = f.options[f.selectedIndex].value;
	
	//Select BGH Name
	
	if(bGHName != null) {
	  var userName = bGHName.userName ;
	  userName = userName.replace(/\./g, " ");
	  $("#bGHName").append("<option selected='selected' value="+format(bGHName.employeeId)+">"+userName +"</option>");
	}
	
	if(bUHName != null) {
		var userName = bUHName.userName ;
		userName = userName.replace(/\./g, " ");
		$("#bUHName").append("<option selected='selected' value="+format(bUHName.employeeId)+">"+userName +"</option>");
	}
	
	if(hRName != null) {
		var userName = hRName.userName ;
		userName = userName.replace(/\./g, " ");
		$("#hRName").append("<option selected='selected' value="+format(hRName.employeeId)+">"+userName +"</option>");
	}
	
}	

function format( str){
	if(str != null && str != ""){
		return str;		
	}else{
		return ' ';
	}
}
function validDates1(fromDate, toDate) {
	var SDate='';
	var startDate='';
	var EDate='';
	var endDate='';
	
	if(toDate != ""){
		var dateSplit = toDate.split("-"); 
		var dateObjendDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);
	    var EDate = dateObjendDate;
	    endDate = new Date(EDate);
	    endDate.setHours(0, 0, 0, 0);
	}
	
	if(fromDate != ""){	
		  var dateSplit1 = fromDate.split("-"); 
		 var dateObjfromDate = new Date(dateSplit1[1] + " " + dateSplit1[0] + ", " + dateSplit1[2]);	 
		 var SDate = dateObjfromDate;	
		 startDate = new Date(SDate);
		 startDate.setHours(0, 0, 0, 0); 
	}
	
	if(SDate != '' && EDate != '' && startDate>endDate) 
		return false;	   	
		return true;
}

function validDatesForJoining(fromDate, toDate) {
    
    var SDate='';
    var startDate='';
    var EDate='';
    var endDate='';
    
    if(toDate != ""){
     var dateSplit = toDate.split("-"); 
     var dateObjendDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);
     EDate = dateObjendDate;
     endDate = new Date(EDate);endDate.setHours(0, 0, 0, 0);
    }
    if(fromDate != ""){
      var dateSplit1 = fromDate.split("-"); 
      var dateObjfromDate = new Date(dateSplit1[1] + " " + dateSplit1[0] + ", " + dateSplit1[2]); 
      SDate = dateObjfromDate; 
      startDate = new Date(SDate);startDate.setHours(0, 0, 0, 0); 
    }  
    if(SDate != '' && EDate != '' && startDate>endDate) 
        return false;
        return true;
   }
/* function checkownersipLoanStatus(){
	if(document.getElementById("ownership").value=='6'){					
		$("#buId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);			
		$("#buId").parent().find("a.ui-button").button("disabled");			
	}
} */
function formateDate(FormateDate)
{
	/* var doj=Date.parse(FormateDate);
	var dateObject=new Date(doj);
	var formattedDate;
	var dd=dateObject.getDate();
	var mm=dateObject.getMonth()+1;
	var yyyy=dateObject.getFullYear();
	if(dd<10){dd='0'+dd} if(mm<10){mm='0'+mm} 
	{
		//formattedDate = mm+'/'+dd+'/'+yyyy;
	
	} */
	
	/* added new code for date format */
	var monthNames = ["Jan", "Feb", "Mar","Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct","Nov", "Dec"];
	
	if ((objOffsetVersion=objAgent.indexOf("Firefox"))!=-1) { 
		objbrowserName = "Firefox"; 
		
		var dateSplit = FormateDate.split("-");            
		dateObjStartDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);
		
		var month =  monthNames[dateObjStartDate.getMonth()];
		var day = dateObjStartDate.getDate();
		var year = dateObjStartDate.getFullYear();
		
		if(day<10){day='0'+day} if(month<10){month='0'+month} 	{	
			formattedDate = day + "-" + month + "-" + year;
			}
		
	}
	
	else if ((objOffsetVersion=objAgent.indexOf("Chrome"))!=-1) { 
		
		objbrowserName = "Chrome"; 
		objfullVersion = objAgent.substring(objOffsetVersion+7); 					
	
		var dateObjStartDate = new Date(FormateDate);
		
		var month = monthNames[dateObjStartDate.getMonth()];
		var day = dateObjStartDate.getDate();
		var year = dateObjStartDate.getFullYear();
		
		if(day<10){day='0'+day} if(month<10){month='0'+month} 	{	
			formattedDate = day + "-" + month + "-" + year;
			}
		
	}	
	
	else {
	
		var dateObjStartDate  = Date.parse(FormateDate);
		
		var month = monthNames[dateObjStartDate.getMonth()];
		var day = dateObjStartDate.getDate();
		var year = dateObjStartDate.getFullYear();
		
		if(day<10){day='0'+day} if(month<10){month='0'+month} 	{	
			formattedDate = day + "-" + month + "-" + year;
			}
		
	} 
	return formattedDate; 
}
$(document).ready(function(){
	var key=localStorage.getItem("key");
	if(key){
    $("#referenceResourceID").val(localStorage.getItem("id"));
    $('#resourceId').closest('td').find(".ui-combobox-input").val(localStorage.getItem("empfname")); 
    $('#resourceId').closest('td').find("#refResourceId").val(localStorage.getItem("id")); 
    //document.getElementById("resourceId").val(localStorage.getItem("empfname"));
	var rid=$('#referenceResourceID').val();	
	var loadedData;
	$.ajax({				
        url: 'loanAndTransfer/loadResourceData',
     	contentType: "application/json",
     	async:false,
     	data: {"resourceid":rid},  	
     	success: function(response) { 
     		loadedData=response;
     		$('#eventId').val('');
     		//enable Event field
     		$("#eventId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", false).prop("disabled",false);			
     		$("#eventId").parent().find("a.ui-button").button("enable");	
     		 //disable each field
     		$("#yashEmpId").val(loadedData.yashEmpId);
			$("#yashEmpId").attr("disabled","disabled");
			//disabledAllField();
			//changes for disabled all the field
			$("#ownership").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		//.css({"background-color":"#CCCCCC !important","color":"rgb(84, 84, 84) !important"})
		$("#ownership").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);
		$("#ownership").parent().find("a.ui-button").css("pointer-events","none");
	$("#employeeCategory").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#employeeCategory").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);
		$("#employeeCategory").parent().find("a.ui-button").css("pointer-events","none");
	$("#designationId").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#designationId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);
		$("#designationId").parent().find("a.ui-button").css("pointer-events","none");
	$("#gradeId").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#gradeId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);
		$("#gradeId").parent().find("a.ui-button").css("pointer-events","none");
	$("#locationId").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#locationId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);
		$("#locationId").parent().find("a.ui-button").css("pointer-events","none");
	$("#deploymentLocationId").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#deploymentLocationId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);
		$("#deploymentLocationId").parent().find("a.ui-button").css("pointer-events","none");
	$("#buId").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#buId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);
		$("#buId").parent().find("a.ui-button").css("pointer-events","none");
	$("#currentBuId").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#currentBuId").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true).prop("disabled",true);
		$("#currentBuId").parent().find("a.ui-button").css("pointer-events","none");
	$("#currentReportingManager").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#currentReportingManager").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true);
		$("#currentReportingManager").attr("disabled","disabled");
		$("#currentReportingManager").parent().find("a.ui-button").css("pointer-events","none");
	$("#currentReportingManagerTwo").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#currentReportingManagerTwo").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true);
		$("#currentReportingManagerTwo").attr("disabled","disabled");
		$("#currentReportingManagerTwo").parent().find("a.ui-button").css("pointer-events","none");
	$("#bGHName").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#bGHName").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true);
		$("#bGHName").attr("disabled","disabled");
		$("#bGHName").parent().find("a.ui-button").css("pointer-events","none");
	$("#bUHName").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#bUHName").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true);
		$("#bUHName").attr("disabled","disabled");
		$("#bUHName").parent().find("a.ui-button").css("pointer-events","none");
	$("#hRName").parent().find("input").removeClass("uiDropDown ui-state-default ui-widget-content");
		$("#hRName").parent().find("input.ui-autocomplete-input").autocomplete("option", "disabled", true);
		$("#hRName").attr("disabled","disabled");
		$("#hRName").parent().find("a.ui-button").css("pointer-events","none");
	//disable all text boxes
	$("#emailId").attr("disabled","disabled");
	$("#contactNumber").attr("disabled","disabled");
	$("#contactNumberTwo").attr("disabled","disabled");
	$("#dateOfJoining").attr("disabled","disabled");
	$("#confirmationDate").attr("disabled","disabled");
	$("#lastAppraisal").attr("disabled","disabled");
	$("#penultimateAppraisal").attr("disabled","disabled");
	$("#releaseDate").attr("disabled","disabled");
	
	$("#bGHComments").attr("disabled","disabled"); 
	$("#bGCommentsTimestamp").attr("disabled","disabled"); 
	$("#bUHComments").attr("disabled","disabled"); 
	$("#bUCommentsTimestamp").attr("disabled","disabled"); 
	$("#hRComments").attr("disabled","disabled"); 
	$("#hRCommentsTimestamp").attr("disabled","disabled"); 
	$("#loadResourcesId").show();
	localStorage.setItem('butEnable',1);
			//chnages for disabled all the fields
     	},
     	error: function(errorResponse)
 	    { 	    	
     		showError(errorResponse);
 	    } 
 	})
 	
 	populatedata(loadedData.designationId.id,loadedData.gradeId.id,loadedData.locationId.id,
 			(loadedData.deploymentLocation!=null)?loadedData.deploymentLocation.id:null,loadedData.buId.id,
 			loadedData.currentBuId.id,loadedData.currentReportingManager,loadedData.currentReportingManagerTwo,
 			loadedData.ownership.id,loadedData.yashEmpId,loadedData.emailId,loadedData.contactNumber,loadedData.contactNumberTwo,
 			loadedData.employeeId,loadedData.transferDate,loadedData.dateOfJoining,loadedData.lastAppraisal,loadedData.penultimateAppraisal,
 			loadedData.releaseDate,loadedData.confirmationDate,loadedData.bGHComments,loadedData.bGCommentsTimestamp,
 			loadedData.bUHComments,loadedData.hRComments,(loadedData.employeeCategory != null) ? loadedData.employeeCategory.id:"",
 			loadedData.endTransferDate,loadedData.userName,loadedData.bGHName,loadedData.bUHName,loadedData.hRName);
	
	}
	localStorage.removeItem("key");
	localStorage.removeItem("id");
	localStorage.removeItem("empfname");
	});
</script>




<div class="content-wrapper">
	<!--right section-->
	<div class="botMargin">
		<h1>Loan or Transfer Resources</h1>
	</div>
	<div class="tab_seaction">
		<ul class='tabs'>
			<li><a href='#tab1'>Loan / Transfer</a></li>			
		</ul>
		<div id='tab1' class="tab_div">
		 <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_HR')">
			<div class="search_filter">
				<div align="right">
					<a href="#" class="blue_link" id="save"> <img
						src="resources/images/save.png" name="save" width="16" height="22" />
						Save
					</a>
				</div>
			</div>
			</sec:authorize>
			<div class="form">
				<form id="resourceLoanTransferForm" name="resourceLoanTransferForm" method="post" >
				<input type="hidden" id="referenceResourceID" value="">	
				<input type="hidden" id="transferDateId" value="">		
				<input type="hidden" id="isRedirectedFronResource" value="${isRedirectedFromResource}">		

					<table id="loadTransderTable"width="100%">
					<tr>
					
					<td align="right"> Resource Name :<span class="astric">*</span></td>
								<td  align="left" > 
									<select name="resourceId.employeeId" id="resourceId" class="">
									<%-- <option value="" selected></option>										
									<c:forEach var="resource" items="${resourcesRM}">
										<option value="${resource.employeeId}">${resource.employeeName}[${resource.yashEmpId}]</option>										
									</c:forEach> --%> 
									</select>
									<input type="hidden" name="refResourceId" id="refResourceId"  value=''/>
								
								</td>
					
						<td align="right" >Event :<span class="astric">*</span></td>
						<td align="left" >
						
							<select name="eventId.id"
									id="eventId" class="required comboselectScroll" >
									<option value="" selected></option>		
										<c:forEach var="event" items="${event}">
											<option value="${event.id}">${event.event}</option>
										</c:forEach>						
								</select>
								
						</td>	
						
						<td align="right">Employee Category :<span class="astric">*</span></td>
							<td align="left"><select name="employeeCategory.id"
								id="employeeCategory" class="required comboselect check">
									<option value="" selected></option>		
									<c:forEach var="employeecategory" items="${employeecategory}">									
											<option value="${employeecategory.id}">${employeecategory.employeecategoryName}</option>										
									</c:forEach>
							</select></td>
						
						
					</tr>
							<tr>
							
						
								
								<td align="right">Employee Id :<span class="astric">*</span></td>
								
							 <input type="hidden"
								  name="employeeId" id="employeeId"  />
								<td  align="left">
								<input type="text" id = "yashEmpId" name="yashEmpId"  > 
									<%-- <select name="resourceId.yashEmpId"
									id="yashEmpId" class="comboselect">
									<option value="-1" selected>Select Resource</option>										
										<c:forEach var="resource" items="${resourcesRM}">
											<option value="${resource.employeeId}">${resource.yashEmpId}</option>										
										</c:forEach>
									</select> --%>
								
								</td>
								<td></td>
									<td>
										<div id="description" align="left" style="background: white; width =860; height =350; color: #3377BB;font-weight: bold" ></div>
									</td>
					
								
							
							<td align="right">Grade :<span class="astric">*</span></td>
								<td align="left"><select name="gradeId.id" id="gradeId"
									class="required comboselect">
									<option value="" selected></option>		
									<c:forEach var="grade" items="${grade}">
										<option value="${grade.id}">${grade.grade}</option>
									</c:forEach>
									</select>
								</td>
								
								</tr>
								<tr>	
								<td align="right">Ownership :<span class="astric">*</span></td>
								 <td align="left"><select name="ownership.id"
									id="ownership" class="required comboselect readonly" >
										<option value="" selected></option>		
										<c:forEach var="ownershipId" items="${ownership}">
											<option value="${ownershipId.id}">${ownershipId.ownershipName}</option>
										</c:forEach>
									</select>
								</td>
								
								<td align="right">Designation :<span class="astric">*</span></td>
								<td align="left"><select name="designationId.id" id="designationId"
									class="required comboselect" >
										<option value="" selected></option>		
										<c:forEach var="designation" items="${designation}">
											<option value="${designation.id}">${designation.designationName}</option>
										</c:forEach>
									</select>
								</td>								
							
							<td align="right">Parent BG-BU :<span class="astric">*</span></td>
								<td align="left"><select name="buId.id"
								class="required comboselect" id="buId">
									<option value="" selected></option>		
									<c:forEach var="bus" items="${bus}">
										<option value="${bus.id}">${bus.parentId.name}-${bus.name}</option>
									</c:forEach>
								</select></td>
								
								
							</tr>
							<tr>
						<td align="right">Base Location :<span class="astric">*</span></td>
								<td align="left"><select name="locationId.id" id="locationId"
									class="required comboselect">
										<option value="" selected></option>		
										<c:forEach var="location" items="${location}">
											<option value="${location.id}">${location.location}</option>
										</c:forEach>
									</select></td>
								
								<td align="right">Current Location :<span class="astric">*</span></td>
								<td align="left"><select name="deploymentLocationId.id" id="deploymentLocationId"
								  class="required comboselect">
									<option value="" selected></option>		
									<c:forEach var="location" items="${location}">
										<option value="${location.id}">${location.location}</option>
									</c:forEach>
									</select>
								</td>
								
									
							<td align="right">Current BG-BU :<span class="astric">*</span></td>
								<td align="left"><select name="currentBuId.id"
								class="required comboselect" id="currentBuId">
									<option value="" selected></option>		
									<c:forEach var="bus" items="${bus}">
										<option value="${bus.id}">${bus.parentId.name}-${bus.name}</option>
									</c:forEach>
								</select></td>
								

									
							<!--<td align="right">Ownership :<span class="astric">*</span></td>
								 <td align="left"><select name="ownership.id"
									id="ownership" class="required comboselect">
										<option value="-1">Select Ownership</option>
										<c:forEach var="ownershipId" items="${ownership}">
											<option value="${ownershipId.id}">${ownershipId.ownershipName}</option>
										</c:forEach>
									</select>
								</td>		-->				
					 
						
								</tr><tr>	
										
								<td align="right">IRM :<span class="astric">*</span></td>
								<td align="left">
								 	<select name="currentReportingManager.employeeId" id="currentReportingManager" class="">
										<%-- <option selected="selected" value="-1">Select One</option>	
										<c:forEach var="resource" items="${resourcesRM}">
											<option value="${resource.employeeId}">${resource.employeeName}[${resource.yashEmpId}]</option>										
										</c:forEach> --%>
									</select>
								</td>
							
																			
								
								<td align="right">SRM:<span class="astric">*</span></td>
								<td align="left">
									<select name="currentReportingManagerTwo.employeeId" id="currentReportingManagerTwo" class="">
										<%-- <option selected="selected" value="-1">Select One</option>		
										<c:forEach var="resource" items="${resourcesRM}">
											<option value="${resource.employeeId}">${resource.employeeName}[${resource.yashEmpId}]</option>
										</c:forEach> --%>
									</select>
								</td>
								
								<td align="right">Email Id :<span class="astric">*</span></td>
								<td align="left">
								<input type="text" id = "emailId" name ="emailId" class="required email string server-validation">
								<div class="errorNumericLast errorNumeric"
										style="display: none"></div>
							 
								</td>
								
								</tr><tr>
								
								
								
								<td align="right">Contact Number 1 :</td>
								<td align="left">
								<input type="text" id = "contactNumber" name="contactNumber" value="">
								<%-- <select
									name="resourceId.contactNumber"
									id="contactNumber" class="comboselect">
										<option selected="selected" value="-1">Select Contact Number 1</option>
										<c:forEach var="resource" items="${resourcesRM}">
											<option value="${resource.employeeId}">${resource.contactNumber}</option>
										</c:forEach>
									</select> --%>
								</td>
								
								<td align="right">Contact Number 2 :</td>
								<td align="left">
								<input type="text" id = "contactNumberTwo" name ="contactNumberTwo" value="">
								<%-- <select
									name="resourceId.contactNumberTwo"
									id="contactNumberTwo" class="comboselect">
										<option selected="selected" value="-1">Select Contact Number 1</option>
										<c:forEach var="resource" items="${resourcesRM}">
											<option value="${resource.employeeId}">${resource.contactNumberTwo}</option>
										</c:forEach>
									</select> --%>
								</td>
								<td align="right">DOJ :<span class="astric">*</span></td>
							<td align="left"><input type="text" value=""
								id="dateOfJoining" name="dateOfJoining" class="required"
								readonly="readonly" disabled="disabled"/></td>
								
								</tr>
								<tr>
							
							<td align="right">Confirmation Date :</td>
							<td align="left"><input type="text" value=""
								readonly="readonly" name="confirmationDate"
								id="confirmationDate" /></td>
								<td align="right">Appraisal Date 1 :</td>
							<td align="left"><input type="text" value=""
								readonly="readonly" name="lastAppraisal" id="lastAppraisal" /></td>
								
								<td align="right">Appraisal Date 2 :</td>
							<td align="left"><input type="text" value=""
								readonly="readonly" name="penultimateAppraisal"
								id="penultimateAppraisal"></td>
								
								</tr>
								
								<tr>
								
								<td align="right">Release Date :</td>
							<td align="left"><input type="text" value="" 
								readonly="readonly" name="releaseDate" id="releaseDate"></td>	
								<td align="right">Loan/Transfer Date From:</td>
							<td align="left">
								<input id="transferDate"  type="text" name="transferDate" readonly="readonly" value="" class= "firstcal">
								</td>
								 
								<td align="right">Loan Transfer Date To:</td>
								<td align="left"><input type="text" readonly="readonly" name="endTransferDate" id="transferTo" class = "secondcal"></input>
								</td>
							</tr>	
							<!-- Adding comment box : -->
							
							<tr>
						 		<td align="right">BGH Name :</td>
								<td align="left">
								<select name="bGHName.employeeId" id="bGHName" class="">
										<%-- <option value="" selected></option>		
										<c:forEach var="bghName" items="${bghName}">
											<option value="${bghName.employeeId}">${bghName.employeeName}[${bghName.yashEmpId}]</option>										
										</c:forEach> --%>
									</select>
								</td>	
								<td align="right">BUH Name:</td>
								<td align="left">
									<select name="bUHName.employeeId" id="bUHName" class="">
									<%-- <option value="" selected></option>										
										<c:forEach var="buhName" items="${buhName}">
											<option value="${buhName.employeeId}">${buhName.employeeName}[${buhName.yashEmpId}]</option>										
										</c:forEach> --%>
									</select>
								</td>
								<td align="right">HR Name:</td>
								<td align="left">
									<select name="hRName.employeeId" id="hRName" class="">
									<%-- <option value="" selected></option>										
										<c:forEach var="hrNames" items="${hrName}">
											<option value="${hrNames.employeeId}">${hrNames.employeeName}[${hrNames.yashEmpId}]</option>										
										</c:forEach> --%>
									</select>
								</td>
								
							</tr>
							
							<tr>
						 		<td align="right">BGH Comments:</td>
								<td align="left"><textarea rows="2" cols=""  
								 name="bGHComments" id="bGHComments"  placeholder="256 characters." ></textarea>
								</td>
								 <td align="right">BUH Comments:</td>
								<td align="left"><textarea rows="2" cols=""  
								 name="bUHComments" id="bUHComments"  placeholder="256 characters." ></textarea>
								</td>
								<td align="right">HR Comments:</td>
								<td align="left"><textarea rows="2" cols=""  
								 name="hRComments" id="hRComments"  placeholder="256 characters." ></textarea>
								</td>
								
							</tr>
							
							<tr>
						 		<td align="right">BGH Comments Date:</td>
								<td align="left"><input type="text" readonly="readonly" name="bGCommentsTimestamp" id="bGCommentsTimestamp"   ></input>
								</td>
								<td align="right">BU Comments Date:</td>
								<td align="left"><input type="text" readonly="readonly" name="bUCommentsTimestamp" id="bUCommentsTimestamp"   ></input>
								</td>
								<td align="right">HR Comments Date:</td>
								<td align="left"><input type="text" readonly="readonly" name="hRCommentsTimestamp" id="hRCommentsTimestamp" ></input>
								</td>
								
							</tr>
							
							<tr>
								<td>&nbsp;</td>
							</tr>
							
							<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td id="loadResourcesId" hidden value=""   colspan="" align="left" rowspan="2"><a href="javascript:void(0);" onclick="loadResources();"><font size="2">Back To Resources</font></a></td>
							<td></td>
								<td colspan="" align="left" rowspan="2"><a href="javascript:void(0);" onclick="loadHistory();"><font size="2">Loan/Transfer History</font></a></td>
							</tr>
								
					</table>
				</form>
			</div>
			<div class="clear"></div>
		</div>		
	</div>
	<!--right section-->
</div>
<div class="clear"></div>
<script type="text/javascript">
	var resourceHistoryData;
	function loadResources()
	{
		window.location.href  = "resources";
	}
	function loadHistory(){			
		if(!$("select#resourceId").val() &&  $("#refResourceId").val()){
			$.ajax({				
		        url: 'resources/loadHistory',
		     	contentType: "application/json",
		     	async:false,
		     	data: {"resourceid":$("#refResourceId").val()},  	
		     	success: function(response) { 
		     		resourceHistoryData=response;	
		     		if(resourceHistoryData==""){
		     			alert('Resource Loan or Transfer History data not found');
		     		}else{     	     			     			     		
		     			$("#resourceHData").html("");
		     			$("#tableResourceData > tbody:last").append($("#resourceAllocationRows").render(resourceHistoryData));     
		     			//$("#tableResourceDataInfo > tbody:last").append($("#resourceAllocationInfo").render(resourceHistoryData));   
		     			containerWidth();
		     			$("#dialoglt").dialog('open');
		     			$("#tableResourceData").show();
		    			$("#dialogText").show();
		     		}
		     	},
		     	error: function(errorResponse)
		 	    { 	    			     		
		     		showError(errorResponse);
		     	}     	
		 	})
		}else{
		if($("select#resourceId").val()!=-1){
				 $.ajax({				
			        url: 'resources/loadHistory',
			     	contentType: "application/json",
			     	async:false,
			     	data: {"resourceid":$("select#resourceId").val()},  	
			     	success: function(response) { 
			     		resourceHistoryData=response;	
			     		if(resourceHistoryData==""){
			     			alert('Resource Loan or Transfer History data not found');
			     		}else{     	     			     			     		
			     			
			     			$("#resourceHData").html("");
			     			$("#tableResourceData > tbody:last").append($("#resourceAllocationRows").render(resourceHistoryData));     
			     			//$("#tableResourceDataInfo > tbody:last").append($("#resourceAllocationInfo").render(resourceHistoryData));   
			     			containerWidth();
			     			$("#dialoglt").dialog('open');
			     			$("#tableResourceData").show();
			    			$("#dialogText").show();
			     		}
			     	},
			     	error: function(errorResponse)
			 	    { 	    			     		
			     		showError(errorResponse);
			     	}     	
			 	})
		}
		  else{
			//alert('Please select Resource to view History data');
			// Added for task # 216 - Start
							/* alert("Please enter and save the data"); */
							var text="Please select Resource to view History data";
							showAlert(text);
							// Added for task # 216 - End
		}
		}
}	
</script>
<script type="text/javascript">
    $(document).ready(function () {
                $("#dialoglt").dialog({ autoOpen: false });
			    $("#dialoglt" ).dialog( "option", "modal", true );
		        $("#dialoglt" ).dialog( "option", "title", "Report" );
		        $("#dialoglt" ).dialog( "option", "buttons", [ { text: "Ok", click: function() { $( this ).dialog( "close" ); } } ] );
		        $("#dialoglt" ).dialog({ closeText: "hide" });
		        $("#dialoglt" ).dialog({ dialogClass: "alert" });
		        $("#dialoglt" ).dialog({ minHeight:250,width: 860,height: 350});
		        $("#dialoglt" ).dialog( "option", "hide", { effect: "explode", duration: 1000 } );		        
		        $("#dialoglt" ).dialog({ show: { effect: "explode", duration: 800 } });		      
    });    
</script>


<script id="resourceAllocationInfo" type="text/x-jquery-tmpl">					
 						<tr >
			                <td width="15%" align="center" valign="middle" colspan=""  rowspan="2">{{>resourceId.employeeName}}</td>
						</tr>
</script>
<script id="resourceAllocationRows" type="text/x-jquery-tmpl">					

 						<tr class="even resourceallocrow" style="color:black;font-weight: bold">
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
<td width="15%" align="center" valign="middle">{{>endTransferDate}}</td>
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
<<script type="text/javascript">
//alert("resource name"+rName);
</script>
<div id="dialoglt"
	style="background: white; width =860; height =350; color: #3377BB;">
	<div id="dialogText" align="center" style="">
		<b>Resource Loan/Transfer History</b>
	
			</h3>
 	</div>

	<div>

		<table id="tableResourceData"
			class="dataTbl display tablesorter addNewRow">
			<thead>
				<tr style="color: red;font-weight: bold">
			<th width="15" align="center" valign="middle">Event</th>
							<th width="15" align="center" valign="middle">Status</th>
						    <th width="15%" align="center" valign="middle">Employee Category Status</th>
							<th width="15%" align="center" valign="middle">Designation</th>
							<th width="15%" align="center" valign="middle">Grade</th>							
							<th width="15%" align="center" valign="middle">IRM</th>
							<th width="15%" align="center" valign="middle">SRM</th>
					
							<th width="15%" align="center" valign="middle">Base Location</th>
							<th width="15%" align="center" valign="middle">Current Location</th>
							<th width="15%" align="center" valign="middle">Parent BG-BU</th>
							<th width="15%" align="center" valign="middle">Current BG-BU</th>

							<th width="15%" align="center" valign="middle">System Date</th>
							<th width="15%" align="center" valign="middle">Loan/Transfer Date From</th>
							<th width="15%" align="center" valign="middle">Loan/Transfer Date To</th>
							<th width="15%" align="center" valign="middle">Updated by</th>

							<th width="15%" align="center" valign="middle">BGH Name</th>
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
			<tbody id="resourceHData" >
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
/* $(document).ready(function(){
	
$('body').find('#resourceHData').find('tr').find('td').addClass('abc');	
}); */
</script>
 <%-- <script type="text/javascript">
$( document ).ready(function() {
	
	<%
		Boolean flag=false;
		flag=(Boolean)session.getAttribute("loanandtransferflag");
	%>
	
	var flag1 =<%=flag.booleanValue()%>;
	
      if(flag1==true)
    	{
    	  <%
    	  session.setAttribute("loanandtransferflag", false);
    	  %>
    	 } 
      else{
    		 $('.notification-bar').hide();
      }
$('.close').click(function(){
    $('.notification-bar').hide();
   });
});
</script>--%>
<!--START: Alert: Added by Pratyoosh Tripathi -->			
				<div class="notification-bar">
<!-- <div class="close closeIconPosition">close</div> -->
<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
  <!-- <div class="notification-text">Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.</div> -->
  <sec:authorize
				access="hasAnyRole('ROLE_BG_ADMIN','ROLE_ADMIN')">
				<div class="notification-text">To update Resource Data- Please enter Resource Name or Employee Id and select "Event" accordingly.</div>
				
						
	</sec:authorize>
  <sec:authorize
				access="hasAnyRole('ROLE_HR')">
				<div class="notification-text">To update Resource Data- Please enter Resource Name or 
Employee Id and select "Event" accordingly.</div>			
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

.notification-text { 
    font-size: 14px;
    color: #fff;
    text-align: justify;
    }
    
  .select2-container--default .select2-selection--multiple .select2-selection__choice {
	color: #000;
	background-color: #ddd;
 }
 .select2-container {
	width: 135px !important;
 }
    
   </style>
    <script type="text/javascript">
$( document ).ready(function() {
	
	$('[data-toggle="tooltip"]').tooltip();
	
	setInterval(function(){ $('[data-toggle="tooltip"]').fadeToggle(500); }, 1000);
	
	<%
		Boolean flag1=false;
		flag1=(Boolean)session.getAttribute("notificationbarflag");
	%>
	
	
$('.close').click(function(){
    $('.notification-bar').hide();
   });
});
</script>

<!--END: Alert: Added by Pratyoosh Tripathi -->
