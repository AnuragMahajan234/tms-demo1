<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!-- <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script> -->


<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<!-- 
[if IE]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<![endif]
	-->	
	<spring:url value="/resources/js-framework/date.js?ver=${app_js_ver}" var="jquery_date_js" />
	<spring:message code="application_js_version" var="app_js_ver" htmlEscape="false"/>
  	<spring:url value="/resources/images/favicon.ico" var="favicon"/>
    <spring:url value="/resources/js-framework/jquery-1.7.2.min.js?ver=${app_js_ver}" var="jquery_url"/>
	<spring:url value="/resources/js-framework/jquery-ui/jquery-ui-1.8.22.custom.min.js?ver=${app_js_ver}" var="jquery_ui_1_8_21_custom_min_js"/>
	<spring:url value="/resources/js-framework/jquery-ui/combobox.js?ver=${app_js_ver}" var="combobox_js"/>
	<spring:url value="/resources/js-framework/form2js/jquery.toObject.js?ver=${app_js_ver}" var="jquery_toObject_js"/>
	<spring:url value="/resources/js-framework/validate/jquery.validate.min.js?ver=${app_js_ver}" var="jquery_validate_min_js"/>
	<spring:url value="/resources/js-framework/validate/jquery.validVal-4.0.3-packed.js?ver=${app_js_ver}" var="jquery_validVal"/>
	<spring:url value="/resources/js-framework/validate/additional-methods.min.js?ver=${app_js_ver}" var="additional_methods_min_js"/>
	<spring:url value="/resources/js-framework/jsrender/jsrender.js?ver=${app_js_ver}" var="jsrender_js"/>
	<spring:url value="/resources/js-framework/jquery.autogrowtextarea.js?ver=${app_js_ver}" var="jquery_autogrowtextarea_js"/>
	<spring:url value="/resources/js-user/rmsCommon.js?ver=${app_js_ver}" var="rmsCommon_js"/>
	<spring:url value="/resources/js-user/validations.js?ver=${app_js_ver}" var="validations_js"/>
	<spring:url value="/resources/js-framework/fancy-box/jquery.fancybox.pack.js?ver=${app_js_ver}" var="jquery_fancybox"/>
	<spring:url value="/resources/js-framework/toastr/toastr.js?ver=${app_js_ver}" var="toastr_js"/>
	<spring:url value="/resources/js-framework/blockUI/jquery.blockUI.js?ver=${app_js_ver}" var="blockUI"/>
	<spring:url value="/resources/js-framework/noty/jquery.noty.js?ver=${app_js_ver}" var="noty"/>
	<spring:url value="/resources/js-framework/noty/layouts/center.js?ver=${app_js_ver}" var="noty_center"/>
	<spring:url value="/resources/js-framework/noty/themes/default.js?ver=${app_js_ver}" var="noty_default"/>
	<link rel="stylesheet" href="/rms/resources/styles/jquery-ui/css/ui-darkness/jquery-ui-1.8.22.custom.css" />
	<link rel="stylesheet" href="/rms/resources/dashboardscript/dist/css/RMS.min.css?ver=${app_js_ver}" />
	<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
	<link href="${style_css}" rel="stylesheet" type="text/css"></link>
	
    <!-- Get the user local from the page context (it was set by Spring MVC's locale resolver) -->
    <c:set var="userLocale">
      <c:set var="plocale">${pageContext.response.locale}</c:set>
      <c:out default="en" value="${fn:replace(plocale, '_', '-')}"/>
    </c:set>
   
	<script src="${jquery_url}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${jquery_ui_1_8_21_custom_min_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${combobox_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>

    <script src="${jquery_toObject_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${js2form_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
	<script src="${jquery_validate_min_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${additional_methods_min_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${jquery_validVal}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${jsrender_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
     <script src="${jquery_autogrowtextarea_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    
    <!-- required for common functions -->
    <script src="${rmsCommon_js}" type="text/javascript"></script>
  	<!-- required for common validations -->
  	<script src="${validations_js}" type="text/javascript"></script>
  	
  	 <script src="${noty}" type="text/javascript">
    <!-- required for FF3 and Opera -->
    </script>
    <script src="${noty_default}" type="text/javascript">
    <!-- required for FF3 and Opera -->
    </script>
    <script src="${noty_center}" type="text/javascript">
    <!-- required for FF3 and Opera -->
    </script>
    <script src="${jquery_date_js}" type="text/javascript"></script>
	<spring:url
		value="/resources/js-framework/datatables/jquery.dataTables.min.js?ver=${app_js_ver}"
		var="jquery_dataTables_min_js" />
	<spring:url
		value="/resources/js-framework/populate/jquery.populate.pack.js?ver=${app_js_ver}"
		var="jquery_populate_pack_js" />
	<!-- DataTables CSS -->
	<!-- DataTables -->
	<script src="${jquery_dataTables_min_js}" type="text/javascript">
	        <!-- required for FF3 and Opera -->
	</script>
	
	<!-- Populate -->
	<script src="${jquery_populate_pack_js}" type="text/javascript">
	        <!-- required for FF3 and Opera -->
	</script>
	<script src="${jquery_fancybox}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${toastr_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${blockUI}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
<style type="text/css" title="currentStyle">
body{background:#fff !important;}

.addNewRow input{max-width:90px;}

#example_filter{display:none;}


.custom-head {    font-size: 15px;
    font-weight: normal;
    text-align: center;
    border-bottom: 1px solid rgba(204, 204, 204, 0.45);
    padding: 10px 0;}
.resource_alloc_note_msg{
	margin-left: 650px;display: inline-block;text-align: justify;font-size: 12px;
}
thead input {
	width: 100%
}

input.search_init {
	color: #999
}
table.dataTable
{
	margin: 10px auto 0;
}

#resourceallocationTableId_wrapper .dataTables_scrollHeadInner,
#resourceallocationTableId_wrapper .dataTable{ width:100% !important;}

#resourceallocationTableId_wrapper .dataTables_scrollBody { max-height:240px;}
.res_alloc_resourcetocopy_msg{
 display: inline-block;
 text-align: justify; 
 font-size: 12px; 
 width: 570px; 
 vertical-align: middle; 
 margin-top: 6px;
}
.ui-button.uiDropDownArrow {
    height: 24px;
}
</style>
<Script Language="JavaScript"> 
var objappVersion = navigator.appVersion; 
var objAgent = navigator.userAgent; 
var objbrowserName = navigator.appName; 
var objfullVersion = ''+parseFloat(navigator.appVersion); 
var objBrMajorVersion = parseInt(navigator.appVersion,10); 
var objOffsetName,objOffsetVersion,ix;
 </script>   
<script  type="text/javascript" charset="utf-8">
$(document).ready(function() {
	var oTable;
	
	var resourceName=document.getElementById('employeeName').value;
	document.getElementById('h1').innerHTML = resourceName+" is already allocated with below projects, would you like to close his/her earlier allocation";
	document.getElementById('resourceName').innerHTML = resourceName;
	/* Add the events etc before DataTables hides a column */
	$("thead input").keyup( function () {
		/* Filter on the column (the index) of this element */
		oTable.fnFilter( this.value, oTable.oApi._fnVisibleToColumnIndex( 
			oTable.fnSettings(), $("thead input").index(this) ) );
	} );
	
	
	oTable = $('#example').dataTable({
		"sDom": 'RC<"clear">lfrtip<"top">rt<"bottom"ip<"clear">',
		"sPaginationType": "full_numbers",
		"bStateSave": true,
		"aoColumnDefs": [ { "bVisible": false, "aTargets": [] } ],
		
		"aoColumns": [{},{},{},{},{},{},{},{},{ "bSortable": false }],
		"bSortCellsTop": true,
	}); 
	
	
		var nEditing = null;
		var saveOpen = false;
		var endDateEdit=null;
		var allocationStartDate = null;
		
	$(document).delegate('a.edit','click', function (e) {  
		
			if (saveOpen && this.innerHTML != "Save") {
			var text="Please enter and save the data";
			showAlert(text);
			e.preventDefault();
			return;
	      }
		
		saveOpen=true;
		e.preventDefault();
		
		/* Get the row as a parent of the link that was clicked on */
		var nRow = $(this).parents('tr')[0];
		
		var rowIndex=$(this).closest("tr").index();
		
		if ( nEditing !== null && nEditing != nRow ) {
			/* Currently editing - but not this row - restore the old before continuing to edit mode */
			restoreRow(oTable, nEditing);
			editRow(oTable, nRow);
			nEditing = nRow;
			$(".comboselect").combobox();

		}
		else if (nEditing == nRow && this.innerHTML == "Save" ) {
			/* Editing this row and want to save it */
			// Provide the required parameters in the below functions to validate the inputs
			if(!validateInputs('.tbl') || !validateDuplicates('example', 'Designation', 2)) {
				return;
			}
			
			var inputArray = new Array();
			var labelArray = new Array();
			var labelSelect = $('#example' + ' >tbody >tr').find("td:eq(7)");
			var inpSelect = $.trim(($('#example' + ' >tbody >tr').find("td:eq(7) select")).val());
			
			$(labelSelect).each(function() {
				if ($(this).text() != '') {
					tdInpVal = $.trim($(this).text());
					labelArray.push(tdInpVal);
				} 
				else {
					inputArray.push(inpSelect);
				}
			});
			
			
			var primaryProject = $('#currentProjectEditId').find('option:selected').text();
			flag = validDatesForBrowserCompatible(new Date(),document.getElementById("allocEndDate").value);
			if(!flag) {
				for (var i = 0; i < labelArray.length; i++) {
					if ( primaryProject =='Yes') {
						//showError("Please Change the Primary Project to No before giving the Allocation End Date");
						//return;
						flag = true;
					}
					else
					{
						flag = true;	
					}
				}
			}
			flag = validDatesForBrowserCompatible(allocationStartDate,(document.getElementById("allocEndDate").value)); 
			if(!flag){
				showError("Allocation End Date must be greater than the allocation start date.");
				return;
			}
			saveRow( oTable, nEditing,rowIndex+1 );
			saveOpen=false;
			nEditing = null;
		}
		else {
			/* No edit in progress - let's start one */
			editRow( oTable, nRow );
			nEditing = nRow;
		 }
		$(".comboselect").combobox();
	});
	
	
	function noSundays(date) {
	    return [date.getDay() != 0, ''];
	}
	
	function noSaturday(date){
	    return [date.getDay() != 6, ''];
	}
	

	$(document).on('click','#example a.cancel', function (e) {
		$("#example tbody tr").find("td").attr("align","center");
		//Updated for Issue #46
		saveOpen = false;
		var nRow = $(this).parents('tr')[0];
		//Updated for Issue #46
		e.preventDefault();	
		var nRow = $(this).parents('tr')[0];
		//nEditing=nRow;
		restoreRow( oTable, nEditing );
	});	
	
	function restoreRow ( oTable, nRow ){
		var aData = oTable.fnGetData(nRow);
		var jqTds = $('>td', nRow);
		
		for ( var i=0, iLen=jqTds.length ; i<iLen ; i++ ) {
			oTable.fnUpdate( aData[i], nRow, i, true );
		}
		oTable.fnDraw();
	}
	
	function editRow(oTable, nRow) { 
		 		 
		var aData = oTable.fnGetData(nRow); 
		var jqTds = $(">td", nRow);
		if (jqTds.length < 1)
			return;

		//endDateEdit= jqTds[4].innerHTML;
		
		    jqTds[4].innerHTML = '<input class="todatepicker" name="allocEndDate" readonly="readonly" type="text" value="'+ jqTds[4].innerHTML +'" id="allocEndDate">';
		   
		    /* jqTds[7].innerHTML='<select class="comboselect" id ="currentProjectEditId'+'" name="curProj"><option value="true">Yes</option><option value="false">No</option></select>'; */
		    jqTds[8].innerHTML = '<a class="edit" href="">Save</a>/<a class="cancel" href="#">Cancel</a>';
		
		       if(jqTds[7].innerHTML!=null)
				if(jqTds[7].innerHTML.toLowerCase() == "yes"){ 
					jqTds[7].innerHTML='<select class="comboselect" id ="currentProjectEditId'+'" name="curProj"><option value="true" selected="selected">Yes</option><option value="false">No</option></select>';
				}else{  
				   jqTds[7].innerHTML='<select class="comboselect" id ="currentProjectEditId'+'" name="curProj"><option value="true">Yes</option><option value="false" selected="selected">No</option></select>';
				}   
			
		 allocationStartDate = jqTds[3].innerHTML;
         	 var dateOfJoining = new Date(document.getElementById('dateofjoiningvalidate').value);
		 dateOfJoining = (dateOfJoining.getMonth() + 1) + '/' + dateOfJoining.getDate() + '/' +  dateOfJoining.getFullYear(); 
		  
    
		
		 $('.fromdatepicker').datepicker({changeMonth: true,changeYear: true ,beforeShowDay:noSaturday, onOpen :function(){/*  alert("Hii open"); */},
		    	onClose: function( selectedDate ) {
		    		 //changedAllocStartdate = selectedDate;
		    		// alert("value--"+value);
	                if(this.value == ""){
	   				 $(this).css("border", "1px solid #ff0000");
	   			 	}
	   				else
	   				{
	   					 $(this).css("border", "1px solid #D5D5D5");
	   				}
	            },
	            beforeShow: function(){
	           	 }
		    }); 
		 $('.todatepicker').datepicker({changeMonth: true,changeYear: true,beforeShowDay: noSundays,dateFormat: "dd-M-yy"
		    	
		    });
	}
	
	function getInputValue(str) {
		// for internet explorer
		var index1 = str.indexOf("<INPUT");
		var index = str.indexOf("<input");
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

	function saveRow (oTable, nRow,rowIndex) { 

	 	var aData = oTable.fnGetData(nRow);
		
		if(aData == null || aData.length < 1) return;
		
		var resAllocId=document.getElementById('allocationId'+rowIndex).value;
		var allocEndDate=document.getElementById("allocEndDate").value;
		var allocStartDate=aData[3];
		var employeeId=document.getElementById("employeeId").value;
		var projectId=document.getElementById("projectId"+rowIndex).value;
		var allocTypeId=document.getElementById("allocationType"+rowIndex).value;
		var date=null;
		var newEndDate=null;
		var newStartDate=null;
		var endDatewithHypen=allocEndDate;
		var employeeName=document.getElementById("employeeName").value;
		var yashEmpId=document.getElementById("yashEmpId").value;
		var curProj=document.getElementById("currentProjectEditId").value;
		
		var parsedEndDate=null;
		var parsedAlocEndDate=null;
		var mailFlag="false";
		
		if(endDateEdit != null && endDateEdit != "")
		  parsedEndDate = Date.parse(endDateEdit);
		 
		if(allocEndDate != null && allocEndDate != "")
			parsedAlocEndDate=Date.parse(allocEndDate);
			 
		if(allocEndDate != null && allocEndDate != "") {
			if(parsedEndDate != parsedAlocEndDate)
				 mailFlag="false";
		}
		 
	
	/* Added by anjana for Date issue  */
		var allocEndDate21=document.getElementById("allocEndDate").value;
		if( allocStartDate != null && allocStartDate != "") {
			newStartDate= browserCompatibleDateFormat(allocStartDate);
		}
		
		if( allocEndDate21 != null && allocEndDate21 != "") {
			newEndDate= browserCompatibleDateFormat(allocEndDate);
		}
		
		var jsonData ='{'
		
				var jsonString = getJsonString("id" ,resAllocId);
				if(jsonString != null && $.trim(jsonString) != '')
				jsonData += getJsonString("id" ,resAllocId) +",";
				jsonData += getJsonString("allocStartDate" , newStartDate) +",";
				if(newEndDate!=null){
				 jsonData += getJsonString("allocEndDate" ,newEndDate) +",";
				}
				jsonData += getJsonString("projectId.id" ,projectId) +",";
				jsonData += getJsonString("curProj" ,curProj) +",";
				jsonData += getJsonString("allocationTypeId.id" ,allocTypeId) +",";
				jsonData += getJsonString("yashEmpId" ,yashEmpId) +",";
				jsonData += getJsonString("employeeId.employeeId" ,employeeId) +",";
				
				jsonData += getJsonString("employeeName" ,employeeName) +",";
				jsonData += getJsonString("mailFlag" ,mailFlag) +",";
				
				
				
		jsonData = jsonData.slice(0, -1);
		jsonData +='}';
		
			
		var weekEndDateNew =null;
		var weekEndDateNew1 =null;
		
		weekEndDate = document.getElementById("allocEndDate").value;
		
		if( weekEndDate != null && weekEndDate != '') {
			 
			if( weekEndDate != null && weekEndDate != ""){
				weekEndDateNew= browserCompatibleDateFormat(weekEndDate);
				}
			if( allocStartDate != null && allocStartDate != ""){
				weekEndDateNew1= browserCompatibleDateFormat(weekEndDate);
			}
		}  
		
		var jqTds = $(">td", nRow);
	   
		if(resAllocId != "") {  
		
			if( weekEndDate != null && weekEndDate != '')
			 { 
				 $.ajax({
						type: 'POST',
					    url: '/rms/resourceallocations/getTimesheetStatus/'+resAllocId +"/"+weekEndDateNew,
					    contentType: "application/json; charset=utf-8",
					    async:false,
					    success: function(data) {  
					    	var jsonData1 = JSON.parse(data);	
					    	if(jsonData1.status == "true")
				    		{  
					    	    jQuery.ajax({
				 		        type: 'POST',
				 		        url: "/rms/resourceallocations",
				 		        data: jsonData,
				 		        success: function(data) {
						 		    	saveOpen = false;
										nEditing= null;
										stopProgress();
										var successMsg ="Resource Allocation detail have been saved successfully"; 
										showSuccess(successMsg);
						 			 
										if (jqTds.length < 1)
											return;
										
										    jqTds[4].innerHTML = endDatewithHypen;
									        jqTds[8].innerHTML = '<a class="edit" href="">Edit</a>';
									        
									         if(curProj=="true"){  
									           jqTds[7].innerHTML="Yes";
									         } 
									         else{ 
									           jqTds[7].innerHTML="No";
									         }  
									         
									  },
				 		        //success: callback,
				 		        //dataType: type,
				 		     	contentType: "application/json; charset=utf-8",
				 		     	error: function(data){
				 		     		stopProgress();
				 		     		var obj = jQuery.parseJSON(data.responseText);
				 		     		showError("Transaction failed: " + obj.error);
				 		     	}
				 		        }); 
				    		}
					    	else {
					    	   $.each(jsonData1, function(i, item) {
		                            if(item.status=='false')
		                            {
		                            	flag = true;
		                            }
					    	 });
					    	   
		                     if(flag) {  
		                    	 		userActivityId = JSON.stringify(jsonData1);
		  							    var answer=confirm("Are You sure, you  want to save this end date? If yes, then all the submitted timesheet after this date will be deleted.");
		  							    if(answer){
		  									 $.ajax({
		  											type: 'GET',
		  											contentType : 'application/json; charset=utf-8',
		  								            dataType: 'json',
		  								            url: '/rms/resourceallocations/deleteFutureTimesheet',
		  										    async:false,
		  										    data : "userActivityId=" + userActivityId + "&resourceAllocId=" + resAllocId	+ "&weekEndDate=" + weekEndDateNew1,
			  										
		  										    success: function(data) {
		  										    	jQuery.ajax({
		  									 		        type: 'POST',
		  									 		        url: "/rms/resourceallocations",
		  									 		        data: jsonData,
		  									 		     	success: function(data) {
			  									 		    	saveOpen = false;
			  													nEditing= null;
			  													stopProgress();
			  													var successMsg ="Resource Allocation detail have been saved successfully"; 
			  													showSuccess(successMsg);
			  													 
			  											       if (jqTds.length < 1)
			  														return;
			  													    
			  														 jqTds[4].innerHTML = endDatewithHypen;
			  												         jqTds[8].innerHTML = '<a class="edit" href="">Edit</a>';
			  												       if(curProj=="true"){  
			  												           jqTds[7].innerHTML="Yes";
			  												       } else{ 
			  												           jqTds[7].innerHTML="No";
			  												       }    
		  									 		     }, 
		  									 		        //success: callback,
		  									 		        //dataType: type,
		  									 		     	contentType: "application/json; charset=utf-8",
		  									 		     	error: function(data)
		  									 		     	{
		  									 		     		stopProgress();
		  									 		     		var obj = jQuery.parseJSON(data.responseText);
		  									 		     		showError("Transaction failed: " + obj.error);
		  									 		     	}
		  									 		        });
		  										    }
		  										    
		  									    });
		  								}
		  							    else
		  							    {
		  									stopProgress();
		  							    }
		  							  }
		  						 }
					   
	 				    }// success ends
			});
		}
		 else
			 { 
					saveOpen = false;
					nEditing= null;
			       
					jQuery.ajax({
				        type: 'POST',
				        url: "/rms/resourceallocations",
				        data: jsonData,
				        success: function(data) {
				 		    	saveOpen = false;
								nEditing= null;
								stopProgress();
								var successMsg ="Resource Allocation detail have been saved successfully"; 
								showSuccess(successMsg);
								
								if (jqTds.length < 1)
									return;
									  
								 jqTds[4].innerHTML = endDatewithHypen;
								 jqTds[8].innerHTML = '<a class="edit" href="">Edit</a>';
								 
								 if(curProj=="true"){  
								      jqTds[7].innerHTML="Yes";
								 } 
								 else { 
								    jqTds[7].innerHTML="No";
								 }  
				 		     },
				 		    
				        //success: callback,
				        //dataType: type,
				     	contentType: "application/json; charset=utf-8",
				     	error: function(data){
				     		stopProgress();
				     		var obj = jQuery.parseJSON(data.responseText);
				     		showError("Transaction failed: " + obj.error);
				     	}
			        });
	      		}
			}
	}
});

	function startProgress(){
		  $.blockUI({ message: '</br></br><div class="circle"></div><div class="circle1"></div><center>Just a moment</center>' }); 
	  }
	  
	 function stopProgress(){
		 $.unblockUI(); 
	 }
	 
	 function showSuccess(msg){
		 toastr.success(msg,"Success")
	 }
	 
	 function showError(msg){
		toastr.options.timeOut = 12000;
		 toastr.error(msg,"Error")
	 }
	
	 function browserCompatibleDateFormat(datevalue){
		 
		 var monthNames = ["Jan", "Feb", "Mar",
		                   "Apr", "May", "Jun", "Jul",
		                   "Aug", "Sep", "Oct",
		                   "Nov", "Dec"];
		 
		 if ((objOffsetVersion=objAgent.indexOf("Firefox"))!=-1) { 
				objbrowserName = "Firefox"; 
				var dateSplit = datevalue.split("-");            
				dateObjStartDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);
				
				var month = monthNames[dateObjStartDate.getMonth()];
				var day = dateObjStartDate.getDate();
				var year = dateObjStartDate.getFullYear();
				
				modifiedDate = day + "-" + month + "-" + year; 
				
			}
			
			else if ((objOffsetVersion=objAgent.indexOf("Chrome"))!=-1) { 
				
				objbrowserName = "Chrome"; 
				objfullVersion = objAgent.substring(objOffsetVersion+7); 					
			
				var dateObjStartDate = new Date(datevalue);
				
				var month = monthNames[dateObjStartDate.getMonth()];
				var day = dateObjStartDate.getDate();
				var year = dateObjStartDate.getFullYear(); 			
				
				modifiedDate = day + "-" + month + "-" + year;
				
			}	
			else {
				var dateObjStartDate  = Date.parse(datevalue);
				var month = monthNames[dateObjStartDate.getMonth()];
				var day = dateObjStartDate.getDate();
				var year = dateObjStartDate.getFullYear();
				
				modifiedDate = day + "-" + month + "-" + year;
				
				}
		 return modifiedDate;
	}
</script>


<!-- <body> -->
<div class="botMargin">
 <h1 id="h1" class="custom-head"></h1>
</div>
		<div class="tbl">

				<table id="example" class="dataTbl display tablesorter dataTable addNewRow">
					<thead>
						<tr>
							<th width="7%">Employee ID</th>
							<th width="7%">Allocation Sequence</th>
							<th width="7%" style="min-width:250px;">Allocation Type</th>
							<th width="4%">Start Date</th>
							<th width="4%" style="max-width:100px;">End Date</th>
							<th width="7%" style="min-width:200px;">Project Name</th>
							<th width="7%">Allocation Remarks</th>
							<th width="9%">Primary Project</th>
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
								<th width="4%">Edit</th>
							</sec:authorize>
						</tr>
					 </thead>
					 <tbody >
						 <c:forEach items="${allocations}" var="allocations" varStatus="i">
						 <tr >
					    	<input type="hidden" id="dateofjoiningvalidate" value="${allocations.employeeId.dateOfJoining}" />
					    	<input type="hidden" id="allocationId${i.count}" value="${allocations.id}" /> 
					    	<%-- <input type="hidden" id="allocationId" value="${allocations.id}" /> --%>
					    	<input type="hidden" id="employeeId" value="${allocations.employeeId.employeeId}" />
					    	<input type="hidden" id="projectId${i.count}" value="${allocations.projectId.projectId}" />
					    	<%-- <input type="hidden" id="projectId" value="${allocations.projectId.id}" /> --%>
					    	 <input type="hidden" id="allocationType${i.count}" value="${allocations.allocationType.id}" /> 
					    	<%-- <input type="hidden" id="allocationTypeId" value="${allocations.allocationTypeId.id}" /> --%>
					    	<input type="hidden" id="employeeName" value="${allocations.employeeId.employeeName}" />
					    	<input type="hidden" id="yashEmpId" value="${allocations.employeeId.yashEmpId}" />
					    	
					    	
						    <td>${allocations.employeeId.yashEmpId} </td>
							<td>${allocations.allocationSeq}</td>
							<td>${allocations.allocationType.allocationType}</td>
							<td>${allocations.allocStartDate}</td>
							<td>${allocations.allocEndDate}</td>
							<td>${allocations.projectId.projectName}</td>
							<td>${allocations.allocRemarks}</td>
							<c:choose>
                                 <c:when test="${allocations.curProj==true}">
                                   <td>Yes</td>
                                 </c:when>
                                 <c:otherwise>
                                   <td>No</td>
                                 </c:otherwise>
                            </c:choose>
							<%-- <td>${allocations.curProj}</td> --%>
						    <td><a class="edit" href="">Edit</a></td>
								
						</tr>
					   </c:forEach>	
					</tbody>
				</table>
			</div>	
			  
	 <div>
		<ul class="tabs">
		<br>
		<li><span style="color: #F80000;">Note : </span> <br>
		    <p style="color: #0029FF;">If <span id="resourceName"></span>, previously allocated in "POOL" engagement model project type, then all of his/her allocations will be automatically closed.
 			 (i.e. end date of previous allocation will become day before of current allocation start date) </p></li></ul>
  	 </div>
								