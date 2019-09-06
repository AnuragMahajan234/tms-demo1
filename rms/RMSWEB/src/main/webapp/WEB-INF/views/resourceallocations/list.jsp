<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
	<spring:url value="/resources/js-framework/date.js?ver=${app_js_ver}" var="jquery_date_js" />
	
<spring:url
	value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url
	value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />
<spring:url
	value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}"
	var="multiselect_js" />
<script src="${multiselect_js}" type="text/javascript"></script>

<spring:url
	value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}"
	var="multiselect_filter_js" />
	
<%-- <spring:url
	value="/projectallocations/findResourceDetailsForReleaseSummary/employees/"
	var="resourceDetailsForReleaseSummary" /> --%>
	
<script src="${multiselect_filter_js}" type="text/javascript"></script>

<spring:url
	value="/projectallocations/findOpenAllocationsOfCopiedResource/"
	var="myProfile" /> 
<script>
var global = null;
var status;
var employeeid='';
var yashempid='';
var yashempname='';
var lastactivityDate;
var firstActivityDate;
var allCopied;
var resourceallocationTable;
var copyResourceIdTest;
var addNewAlloc =false;
var editAlloc = false;
var dateOfProjectEnd;
var id;
var empIdGlobal="";
var projectIdGlobal="";
var allocStartDateGlobal="";
var allocEndDateGlobal="";
var dateOfJoiningGlobal="";
var allocationIdGlobal="";

function showProjectDetails(projectId){
	id=projectId;
}
	$(".fancybox_ProjectDetails").fancybox({
		fitToView	: true,
		autoSize	: true,
		closeClick	: true,
        content: '<div><table id="projectDetail" cellpadding="5" cellspacing="5" class="display dataTable" style="width: 400px"><thead><tr class="totalColumn"><th colspan="2" align="center">Project Detail</th></tr></thead><tbody></tbody></table></div>',
    	beforeShow: function(){
    		$.ajax({
				type: 'GET',
		        url: '/rms/projects/'+id,
		     	success: function(response) { 
		     		$("#projectDetail > tbody").append("<tr class=odd><td>ProjectName</td><td>"+response.projectName+"</td></tr>");
		     		$("#projectDetail > tbody").append("<tr class=even><td>CustomerName</td><td>"+response.customerNameId.customerName+"</td></tr>");
		     		$("#projectDetail > tbody").append("<tr class=odd><td>Offshore Manager</td><td>"+response.offshoreDelMgr.employeeName+"</td></tr>");
		     		if(response.onsiteDelMgr!='')
		     		$("#projectDetail > tbody").append("<tr class=even><td>Onshore Manager</td><td>"+response.onsiteDelMgr+"</td></tr>");
		     		else
			     	$("#projectDetail > tbody").append("<tr class=odd><td>Onshore Manager</td><td>NA</td></tr>");	
		     		$("#projectDetail > tbody").append("<tr class=odd><td>Project BU</td><td>"+response.orgHierarchy.parentId.name+"-"+response.orgHierarchy.name+"</td></tr>");
		     		if(response.engagementModelId!=null)
		     		$("#projectDetail > tbody").append("<tr class=even><td>Engagement Mode</td><td>"+response.engagementModelId.engagementModelName+"</td></tr>");
		     		else
		     		$("#projectDetail > tbody").append("<tr class=odd><td>Engagement Mode</td><td>NA</td></tr>");	
		     		
		     		if(response.projectKickOff!=null)
		     		$("#projectDetail > tbody").append("<tr class=even><td>Project Kick Off Date</td><td>"+response.projectKickOff+"</td></tr>");
		     		else
		     		$("#projectDetail > tbody").append("<tr class=odd><td>Project Kick Off Date</td><td>NA</td></tr>");		
		     		if(response.plannedProjSize!=null)
		     		$("#projectDetail > tbody").append("<tr class=even><td>Planned Project Size</td><td>"+response.plannedProjSize+"</td></tr>");
		     		else
		     			$("#projectDetail > tbody").append("<tr class=odd><td>Planned Project Size</td><td>NA</td></tr>");
		     		if(response.deliveryMgr!=null && response.deliveryMgr!='' && response.deliveryMgr.employeeName!='' && response.deliveryMgr.employeeName!=null)
		     			$("#projectDetail > tbody").append("<tr class=even><td>Delivery Manager</td><td>"+response.deliveryMgr.employeeName+"</td></tr>");
		     		else
		     			$("#projectDetail > tbody").append("<tr class=odd><td>Delivery Manager</td><td>NA</td></tr>");
		     	    return;
		     	    
		     	}
		 });  	
        
        }
	});

function callJSONWithErrorCheck( sSource, aoData, callback1, callback2) {
    if (typeof (callback1) != 'function' && callback1 != null) {
         return callback2(callback1);
    }
    if (callback1 == null) {
         callback1 = callback2;
    }
    try {        
        $.ajax({
           url: sSource,
           type : "GET",
                       dataType: 'json',
                       data: aoData,
                       success:  function (json) { 
                           if (json != null && json != "") {
                              callback1(json);
                           } else {
	                           // alert("Error Occured - Nothing returned from server");
	                         // Added for task # 216 - Start
	        					var text="Error Occured - Nothing returned from server";
	        					showAlert(text);
	        					// Added for task # 216 - End
                           }
           },
                       error:  function (XMLHttpRequest, textStatus, errorThrown)  { 
                       //    alert("Error Occured. \nText: " + textStatus + "\nError: " + errorThrown + "\nHTTP Status: " + XMLHttpRequest.responseText);
                       }
                    });   
    } catch (err) {
       txt="There was an error on this page.\n\n";
       txt+="Error description: " + err.description + "\n\n";
       txt+="Click OK to continue.\n\n";
     //  alert(txt);    
    }
} 

function setGlobalVariable(employeeId,projectId,allocationId,allocStartDate,allocEndDate,dateOfJoining) {
	empIdGlobal=employeeId;	
	projectIdGlobal=projectId;
	allocStartDateGlobal=allocStartDate;
    allocEndDateGlobal=allocEndDate;
    dateOfJoiningGlobal=dateOfJoining;	
    allocationIdGlobal=allocationId;
}


function promptForAllocationHrs(allocationHrsVar){
	if($(allocationHrsVar).val() > 40) {
		var answer=confirm("Are You sure, you  are allocating more than 40 hours as weekly hours?");
		if(answer) {
			
		} else {
			setTimeout(function(){allocationHrsVar.focus();}, 10);
			allocationHrsVar.style.border = "1px red solid";			
		}
	}
	
		return true;
}


function checkAllocationRemark(){
	var regExp = /^[a-zA-Z0-9 ]+$/;
	
	var check = document.getElementById("allocRemarks").value;
	
	
		if(!check.match(regExp) && check !=null ){
			if(check == ""){
				return true;
			}else{
			//alert("please enter proper allocation remarks");
			// Added for task # 216 - Start
					/* alert("Please enter and save the data"); */
					var text="Please enter proper Allocation Remarks";
					showAlert(text);
					// Added for task # 216 - End
			return false;
			}
		}
	
		else
			{
				return true;
			}
	
}
// Added for Task # 302 -start
function validAllocHours(weeklyAllocHours){
	var reg=  /^[a-zA-Z]+$/;
	var oneDot= /^[0-9]*[.][0-9]+$/;                       /* /\d*\.?\d*/  
	if(reg.test(weeklyAllocHours) || oneDot.test(weeklyAllocHours)){
		return false;
	}
	else
	return true;
}

//Added for Task # 302 - End

function validDates1(fromDate, toDate) {
	var SDate='';
	var startDate='';
	var EDate='';
	var endDate='';
	
	if(toDate != ""){
		 var substring = "-";
		 var string = toDate.toString();
		 if(string.indexOf(substring) !== -1)
			 {
			  var dateSplit = toDate.split("-"); 
			  dateObjendDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);
			  EDate = dateObjendDate;
			 }else{			
				 EDate = toDate;	
			 }	

	    endDate = new Date(EDate);endDate.setHours(0, 0, 0, 0);
	}
	/* if(toDate != ""){
		var dateSplit = toDate.split("-"); 
		dateObjendDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);
	    EDate = dateObjendDate;
	    endDate = new Date(EDate);endDate.setHours(0, 0, 0, 0);
	} */
	
	if(fromDate != ""){				 
		 var substring = "-";
		 var string = fromDate.toString();
		 if(string.indexOf(substring) !== -1)
			 {
			 var dateSplit1 = fromDate.split("-"); 
			 var dateObjfromDate = new Date(dateSplit1[1] + " " + dateSplit1[0] + ", " + dateSplit1[2]);
			 SDate = dateObjfromDate;
			 }else{			
		 	SDate = fromDate;	
			 }
		 startDate = new Date(SDate);startDate.setHours(0, 0, 0, 0); 
	}
	
	if(SDate != '' && EDate != '' && startDate>endDate) 
		return false;	   	
		return true;
}

function validDatesForJoining(dateOfJoining, allocationStartDate) {
	var SDate='';
	var startDate='';
	var EDate='';
	var endDate='';
	
	if(allocationStartDate != ""){
		var dateSplit = allocationStartDate.split("-"); 
		var dateObjendDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);
		EDate = dateObjendDate;
		endDate = new Date(EDate);endDate.setHours(0, 0, 0, 0);
	}
	if(dateOfJoining != ""){
		 var dateSplit1 = dateOfJoining.split("-"); 
		 var dateObjfromDate = new Date(dateSplit1[1] + " " + dateSplit1[0] + ", " + dateSplit1[2]);	
		 SDate = dateObjfromDate	
	     startDate = new Date(SDate);startDate.setHours(0, 0, 0, 0); 
	} 	
   	if(SDate != '' && EDate != '' && startDate>endDate) 
       	return false;
	    return true;
}

</script>
 

<script id="resourceAllocationRows" type="text/x-jquery-tmpl">
<tr>
<td align="center">
{{if projectEndRemarks!='NA' && projectEndRemarks!=null}}	
<a class="fancyboxForlst" href="#inline{{:#index}}"><img src="resources/images/feedbackremark.png"/></a>
							<div id="inline{{:#index}}" style="width:400px;display: none;"> 
							{{>projectEndRemarks+}}</div>
							{{else}}
							 NA
							{{/if}}
</td>
<td align="center"> 

                        <sec:authorize access="hasAnyRole('ROLE_HR')">
                             <a href="javascript:void(0)" class="">{{>employeeId.yashEmpId}}</a>
						</sec:authorize>
                        <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_ADMIN','ROLE_BEHALF_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
                         <a href="resourceallocations/readonlyEmployeeDetails/{{>employeeId.employeeId}}" class="employeeDetails">{{>employeeId.yashEmpId}}</a>
					    </sec:authorize>	
			                	<input type="hidden" id="employeeId" value="{{>employeeId.employeeId }}"/>
								<input type="hidden" id="id" value="{{>id }}"/>
			                	<input type="hidden" class="empID" id="employeeBuId{{:#index}}" value="{{>employeeId.currentBuId.id }}"/>
								<input type="hidden" class="prjID" id="projectBuId{{:#index}}" value="{{>projectId.orgHierarchy.id }}"/>
								
								{{if allocatedBy}}					
								<input id="allocatedBy.employeeId" type="hidden" value="{{>allocatedBy.employeeId }}"/>
								{{else}}
                                    <input id="allocatedBy" type="hidden"  value=""/>
								{{/if}}
<input type="hidden" id="lastActivityDate{{>id }}" value="{{>lastUserActivityDate}}"/>

<input type="hidden" id="firstActivityDate{{>id }}" value="{{>firstUserActivityDate}}"/>
<input type="hidden" id="isReleasedIndicator{{>employeeId.yashEmpId }}" value="{{>employeeId.isReleasedIndicator}}"/>
			                </td>
			                <td align="center">
							   {{if allocationSeq}}
									{{>allocationSeq}}
							   {{/if}}
							</td>
			                <td align="center">
								{{>allocationTypeId.allocationType}}
			                	<input type="hidden" id="allocationTypeId" value="{{>allocationTypeId.id}}"/>
			                	<input type="hidden" class="allocatEndDate" value="{{>allocEndDate }}"/>
			                	<input type="hidden" class="allocatStartDate" value="{{>allocStartDate }}"/>
							</td>
                            <td align="center">                             
								{{>allocPercentage}}
							</td>

			                <td align="center">
								{{if allocStartDate}}
									{{>allocStartDate}}
								{{/if}}
			                 </td>
							<td align="center">
							
								{{if allocEndDate}}
									{{>allocEndDate}}
								{{/if}}
			                 </td>   
			                 <td align="center" >
                                {{if projectId+}}
							 <a class="fancybox_ProjectDetails" style= cursor:pointer; id="{{>projectId.id }}" onClick="showProjectDetails({{>projectId.id }});">{{>projectId.projectName}}</a>
							 <input type="hidden" id="projectId" value="{{>projectId.id }}"/>
							 {{else}}
                              No Project
                                    <input type="hidden" id="projectId" value="-1"/>
								{{/if}}
							 </td>
			                 <td align="center" >
								{{if allocRemarks+}}
									{{>allocRemarks+}}
								{{/if}}
								
							</td>
			                 <td align="center" >
								{{if curProj}}
									Yes
								  {{else}}
									  No
								{{/if}}
							</td>  

							<td align="center" >
								{{>resourceType}}
							</td>     
			                  
							
                          <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
							{{if employeeId.isReleasedIndicator}}						
	
							 <td align="center" >
								<a class="addNew" id="hiddenAnchor" onclick="setGlobalVariable({{>employeeId.employeeId}}, {{>projectId.id }},{{>id }}, '{{>allocStartDate}}','{{>allocEndDate}}','{{>employeeId.dateOfJoining}}');" style="display:none;"></a>
								<a class="edit" onclick="setGlobalVariable({{>employeeId.employeeId}}, {{>projectId.id }},{{>id }}, '{{>allocStartDate}}','{{>allocEndDate}}','{{>employeeId.dateOfJoining}}');" href="">Edit</a>
							 </td>

 							<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                   {{if lastUserActivityDate !=null}}
   									<td align="center" class="removeInactive" title="Can't delete as timesheet is filled for this Project." >Delete</td>
  								  {{else}}
   									<td align="center" ><a class="delete" href="" onclick="deleteDataRow({{>id}},{{>employeeId.employeeId }},{{>projectId.id }});">Delete</a></td>
									{{/if}}
							</sec:authorize>
						 	
							<td>
								&nbsp;
							<a href="javascript:void(0);" onclick="copyDataRow({{>id}},{{:#index}},{{>employeeId.employeeId}},{{>projectId.id}},'{{>allocStartDate}}');">Copy</a>
							</td>
							  {{else}}
							 <td align="center" ></td>
										 <td align="center" title="Can't delete as Release Date for Resource has been already filled.">
							 </td>
<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
									 <td align="center" >
 							</td>
 </sec:authorize>
							{{/if}}
						  </sec:authorize>
						  
			            </tr>
</script>


<script type="text/javascript" charset="utf-8">
var resourceallocationTable;
		var rowProjectId = "";
		var saveOpen = false;
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
		function cancelDataRow(id,empId,projId){
			 if (id == undefined || id == null || id == '') {
	             oTable.fnDeleteRow( global );
	        	  $("table#example tbody").find("tr:first").find("input[type=text]").blur();
	            	saveOpen = false;
	            	nEditing = null;
	            	$('.toasterBgDiv').remove();
	            	return;
	            }
	          $('.toasterBgDiv').remove();
	 	       	//startProgress();
		}
		
		function deleteDataRow(id,empId,projId){
			var text = "Are you sure you want to delete this row ?";
		    noty({
			 	      text: text,
			 	      type: 'confirm',
			 	      dismissQueue: false,
			 	      layout: 'center',
			 	      theme: 'defaultTheme',
			 	      buttons: [
			 	        {addClass: 'btn btn-primary', text: 'Ok', onClick: function($noty) {
			 	        	//Changed to solve bug# 269 ****Start*****
			 	        //	$noty.close();
			 	        	$.noty.closeAll();
	 	        	 		//****End*****//
			 	           if (id == undefined || id == null || id == '') {
			 	             oTable.fnDeleteRow( global );
			 	        	  $("table#example tbody").find("tr:first").find("input[type=text]").blur();
//  							  var rowCount = $('#example >tbody >tr').length;
//  								if(rowCount==0){
//  									$('#example > tfoot tr th').find('input').val('');
//  								}	
			 	            	saveOpen = false;
			 	            	nEditing = null;
			 	            	$('.toasterBgDiv').remove();
			 	            	return;
			 	            }
			 	          $('.toasterBgDiv').remove();
			 	          //    noty({dismissQueue: true, force: true, layout: 'top', theme: 'defaultTheme', text: 'You clicked "Ok" button', type: 'success'});
				 	       	//startProgress();
				 	       	
				 	      /*  $.ajax({
								type: 'POST',
						        url: 'resourceallocations/approveDetails/'+id+"/"+empId+"/"+projId,
						     	contentType: "application/json; charset=utf-8",
						     	async:false,
						     	success: function(data) { 
						     		
						     			 if(data.check==false){
							     			var warningMsg = " some disapprove data is present which also be deleted.";
											 var conf=confirm(warningMsg);
										 if(conf){
											 $.ajax({
													type: 'DELETE',
											        url: 'resourceallocations/delete/'+id,
											     	contentType: "text/html",
											     	async:false,
											     	success: function(succeResponse) { 
											     		showSuccess("Resource Allocation has been successfully deleted.");
											     		var id = document.getElementById("employeeIdHidden").value;
														//getResourceAllocationById(id);
											     		checkAll('');
											     	},
											 	    error: function(errorResponse)
											 	    {
											 	    	showError("Resource Allocation cannot be  deleted.");
											 	    }
											 });
											     	
								     			stopProgress();
								     			
				                    }else{
				                    	stopProgress();
				                    }
				                    }
						     			else{*/
						     				
						     				$.ajax({
												type: 'DELETE',
										        url: 'resourceallocations/delete/'+id,
										     	contentType: "text/html",
										     	async:false,
										     	success: function(succeResponse) { 
										     		 var id = document.getElementById("employeeIdHidden").value;
														//getResourceAllocationById(id);
										     		checkAll('');
														showSuccess("Resource Allocation has been successfully deleted.");
										     	},
										     	error: function(errorResponse)
										 	    {
										 	    	showError("Resource Allocation cannot be  deleted.");
										 	    }
										 });	
						     			
								     			stopProgress();
						     	/* 		}			
						     			
						    	}
							});
				 	       	 */
				 	       /* 	$.deleteJson_('resourceallocations/'+id,{}, function(data){
				 	        var id = document.getElementById("employeeIdHidden").value;
							getResourceAllocationById(id);
							}, 'json');  
						 	stopProgress();
							showSuccess("Resource Allocation has been successfully deleted."); */
							// To solve "e is undefined problem" in fnDeleteRow
						//	 oTable.fnDeleteRow( nRow );
			 	        }
			 	        },
			 	        {addClass: 'btn btn-danger', text: 'Cancel', onClick: function($noty) {
			 	        	//Changed to solve bug# 269 ****Start*****
			 	          //  $noty.close();
			 	        	$.noty.closeAll();
			 	        	 $('.toasterBgDiv').remove();
	 	        	 		//****End*****//
			 	           //  getResourceAllocationById(empId);
			 	       //     noty({dismissQueue: true, force: true, layout: 'top', theme: 'defaultTheme', text: 'You clicked "Cancel" button', type: 'error'});
			 	         }
			 	        }
			 	      ],
			 	   closeWith:['Button']
			 	    });
			
		}
		
		var monthNames = ["Jan", "Feb", "Mar",
		                  "Apr", "May", "Jun", "Jul",
		                  "Aug", "Sep", "Oct",
		                  "Nov", "Dec"];
		
		function copyDataRow(id,index,employeeId,projectId,alocationStartDate){
			
			document.getElementById('selectProjectId').value=projectId;
			document.getElementById('copyRowId').value=id;
			document.getElementById('copyResourceId').value=employeeId;
			document.getElementById('searchEmp').value='';
			copyResourceIdTest = employeeId;
			var demo = "";
			
			if ((objOffsetVersion=objAgent.indexOf("Firefox"))!=-1) { 
				objbrowserName = "Firefox"; 
				
				var dateSplit = alocationStartDate.split("-");            
				dateObjStartDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);
				
				var month = monthNames[dateObjStartDate.getMonth()];
				var day = dateObjStartDate.getDate();
				var year = dateObjStartDate.getFullYear();
				
				newStartDate = day + "-" + month + "-" + year;
				
			}
			
			else if ((objOffsetVersion=objAgent.indexOf("Chrome"))!=-1) { 
				
				objbrowserName = "Chrome"; 
				objfullVersion = objAgent.substring(objOffsetVersion+7); 					
			
				var dateObjStartDate = new Date(alocationStartDate);
				
				var month = monthNames[dateObjStartDate.getMonth()];
				var day = dateObjStartDate.getDate();
				var year = dateObjStartDate.getFullYear();
				
				newStartDate = day + "-" + month + "-" + year;
				
			}	
			
			else {
				var dateObjStartDate  = Date.parse(alocationStartDate);
				var month = monthNames[dateObjStartDate.getMonth()];
				var day = dateObjStartDate.getDate();
				var year = dateObjStartDate.getFullYear();
				
				newStartDate = day + "-" + month + "-" + year;
				
			} 
			$("#dialog").dialog('open');
			 $.ajax({
					type: 'POST',
			        url: 'resources/getEligibleResourcesForCopy/'+employeeId+'/'+projectId,
			        data: {"alocationStartDate":newStartDate},
			     	success: function(succesResponse) { 
			     		eligibleResToCopy=JSON.parse(succesResponse);
			     		 var option = '';
			     		 $.each(eligibleResToCopy, function (i, n) {
			     		    option = '<option value="' + n[0] +  '">' + n[1]+" "+n[2]+" ["+n[3]+"]"+'</option>';			     		
			     		$('#pdo').append(option);
			     		});
			     	    return;
			     	}
			 });
			
		}
		
		function noSundays(date) {
		    return [date.getDay() != 0, ''];
		}
		
		function noSaturday(date){
		    return [date.getDay() != 6, ''];
		}
		function refreshGrid(){
		}
		
		var oTable;
		var allocationEndDate;
		
		function editRow ( oTable, nRow ){
			var changedAllocEnddate= null;
			var changedAllocStartdate= null;
			var aData = oTable.fnGetData(nRow);
			var jqTds = $(">td", nRow);
			
			if(jqTds.length < 1) return;
			  var allocationStartDate = null;
	          var dateOfJoining = new Date(document.getElementById('dateofjoiningvalidate').value);
				  dateOfJoining = (dateOfJoining.getMonth() + 1) + '/' + dateOfJoining.getDate() + '/' +  dateOfJoining.getFullYear(); 
				  
	         <c:forEach var="project" items="${projects}">
	          var projectId = '${project.id}';
	          var projectEndDate = '${project.projectEndDate}';
	         
	          var projectKickOffDate = new Date('${project.projectKickOff}'); 
	          if(projectKickOffDate !='')
	          projectKickOffDate = (projectKickOffDate.getMonth() + 1) + '/' + projectKickOffDate.getDate() + '/' +  projectKickOffDate.getFullYear(); 
			 
	          
	          
			  if(projectId == getInputValue(aData[5])){
	          		if( projectKickOffDate != null && projectKickOffDate != '' )
	          		{  
	          		
	          		if( new Date(projectKickOffDate) > new Date(dateOfJoining)  )
	          			{ 
	          			allocationStartDate = projectKickOffDate;
	          			}
	          		else
	          			{
	          			allocationStartDate = dateOfJoining;
	          			}
	          		}
	          		else{
	          			allocationStartDate = dateOfJoining;
	          		}
	            	if( projectEndDate != null && projectEndDate != ''   ){
	            	var date = new Date(projectEndDate);
	            	changedAllocStartdate = (date.getMonth() + 1) + '/' + date.getDate() + '/' +  date.getFullYear();
	            	}
	            	else
	            	changedAllocStartdate = null;
	            }
	            </c:forEach>
			
	            //added by neha for datepicker issue 
			jqTds[1].innerHTML = aData[1];
			jqTds[2].innerHTML = aData[2];
			jqTds[4].innerHTML = aData[4]; 
			if (aData[4]) {
				jqTds[4].innerHTML = '<input class="form-control" name="allocPercentage" id="allocPercentage" onchange="onAllocationPercentageChange()" type="text" value="'+ aData[4] +'">';
			} else {
				jqTds[4].innerHTML = '<input class="form-control" name="allocPercentage" id="allocPercentage" onchange="onAllocationPercentageChange()" type="text" value="0">';
			}
			jqTds[5].innerHTML = '<input class="fromdatepicker requir" name="allocStartDate"  readonly="readonly" type="text" value="'+ aData[5] +'" id="allocStartDate">';
			
			dateOfProjectEnd=aData[6];
			jqTds[6].innerHTML = '<input class="todatepicker" name="allocEndDate" readonly="readonly" type="text" value="'+ aData[6] +'" id="allocEndDate">';
			
			allocationEndDate =  aData[6];						
			jqTds[7].innerHTML = '<select class="comboselect" id ="projectNameEditId'+getInputValue(aData[7])+'" name="projectId.id" >'+
								  
									<c:forEach var="project" items="${projects}">
										'<option value="${project.id}">${project.projectName}</option>'+
									</c:forEach>
								 +'</select>';
								 
			jqTds[8].innerHTML ='<textarea id ="allocRemarks" name="allocRemarks" cols="10" rows="0">'+aData[8]+'</textarea>'  ;
			jqTds[9].innerHTML ='<select class="comboselect" id ="currentProjectEditId'+'" name="curProj"><option value="true">Yes</option><option value="false">No</option></select>';
// 			jqTds[8].innerHTML ='<select class="comboselect" id ="billableEditId'+getInputValue(aData[8])+'" name="billable"><option value="true">Yes</option><option value="false">No</option></select>';
/* 			jqTds[9].innerHTML ='<input id ="allocHrs" class="requir" value="'+aData[9]+'" name="allocHrs" type="text" onBlur="promptForAllocationHrs(this);" >';
 */			/* jqTds[10].innerHTML ='<select class="comboselect" id ="rateId' + aData[10] +'" name="rateId.id">'+
									<c:forEach var="rates" items="${rates}">
										'<option value="${rates.id}">${rates.billingRate}</option>'+
									</c:forEach>
								+'</select>'; */
			//jqTds[11].innerHTML = aData[11];
			//jqTds[12].innerHTML = aData[12];
			/* jqTds[11].innerHTML ='<select class="comboselect" id ="teamId' + getInputValue(aData[11]) +'" name="team.id">'+
			'<option value="-1">SELECT</option>'+						
			<c:forEach var="teams" items="${teams}">
										'<option value="${teams.id}">${teams.teamName}</option>'+
									</c:forEach>
								+'</select>'; */
			//jqTds[9].innerHTML ='<a id="save" onclick="saveResourceReleaseDetails('+empIdGlobal+','+projectIdGlobal+','+allocationIdGlobal+',\''+allocStartDateGlobal+'\',\''+allocEndDateGlobal+'\',\''+dateOfJoiningGlobal+'\');" class="editP" href="javascript:void(0);"  >Save</a> / <a id="cancel" class="cancel" href="#"  >Cancel</a>';
			
			jqTds[10].innerHTML ='<select class="comboselect" id ="resourceType'+'" name="resourceType"><option value="Internal" selected>Internal</option><option value="External">External</option></select>';
			
			jqTds[11].innerHTML ='<a id="save" class="edit" href="#"  >Save</a> / <a id="cancel" class="cancel" href="#"  >Cancel</a>';
			jqTds[12].innerHTML =aData[12];
			
			
			if(jqTds.length>13)
			jqTds[13].innerHTML=aData[13];
			//populate different select boxes
			var value = getInputValue(aData[3]);
	
			
			/* value = getInputValue(aData[11]);
		//	alert("team value"+value);
			$("#teamId"+value).val(value); */
			
			value = getInputValue(aData[7]);
			//alert("project value"+value);
			$("#projectNameEditId"+value).val(value);
			/* $("#rateId"+aData[10]).val(aData[10]); */
			
		
			//Start Added to solve default values in drop down
			value= getInputValue(aData[9]);
			if(aData[9].toLowerCase() == 'yes'){
				$("#currentProjectEditId").val("true");	
			}else{
				$("#currentProjectEditId").val("false");
			}
			
 			if(aData[10].toLowerCase() == 'external'){
 				$("#resourceType").val("External");	
 			}else{
 				$("#resourceType").val("Internal");
 			}
			
			
			
			
			
			var index = aData[1].indexOf("<input");
			var strArray = aData[1].substr(index , aData[1].length).split(">", 3);
			var strEmployeeId = $(strArray[0] +">").val();
			var strId = $(strArray[1] +">").val();
 

			var lastDate="lastActivityDate"+strId;
			var lastActDate ="";
			//alert("here"+lastDate);
		if(document.getElementById(lastDate)!=null){
			lastActDate=document.getElementById(lastDate).value;
			if(document.getElementById(lastDate).value=='')
				lastactivityDate ='';
			else
				
			  lastactivityDate=Date.parse(document.getElementById(lastDate).value);
		 
			 // alert("date==="+lastactivityDate);
		}
 
			var firstDate="firstActivityDate"+strId;
			var firstActDate="";
		if(document.getElementById(firstDate)!=null){
			 firstActDate= document.getElementById(firstDate).value;
			  firstActivityDate = Date.parse(document.getElementById(firstDate).value);
		}
		//alert("gfdg"+getInputValue(aData[2]));
		<sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
		if (lastactivityDate == null || lastactivityDate == "") {
			jqTds[3].innerHTML = '<select class="comboselect" id ="allocTypeEditId'+getInputValue(aData[3])+'" name="allocationTypeId.id" onchange="onAllocationTypeChange(event)">'+
			<c:forEach var="allocationtype" items="${allocationtypes}">
				'<option value="${allocationtype.id}">${allocationtype.allocationType}</option>'+
			</c:forEach>
		 '</select>';
		 $("#allocTypeEditId"+getInputValue(aData[3])).val(getInputValue(aData[3]));
		}else{
			jqTds[3].innerHTML =  '<input id ="allocTypeEditId'+getInputValue(aData[3])+'" type="hidden" name="allocationTypeId.id" value="">'	     +aData[3];
			//jqTds[2].innerHTML = aData[2];
		}
		</sec:authorize>
		<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
		jqTds[3].innerHTML = '<select class="comboselect" id ="allocTypeEditId'+getInputValue(aData[3])+'" name="allocationTypeId.id" onchange="onAllocationTypeChange(event)">'+
		<c:forEach var="allocationtype" items="${allocationtypes}">
			'<option value="${allocationtype.id}">${allocationtype.allocationType}</option>'+
		</c:forEach>
	   '</select>';
	   $("#allocTypeEditId"+getInputValue(aData[3])).val(getInputValue(aData[3]));
	   </sec:authorize>
	 	
		
			//End  Added to solve default values in drop down
			
			//populate datepickers
			// Commented by Neha
			 /* $('.fromdatepicker').datepicker({changeMonth: true,changeYear: true ,beforeShowDay:noSaturday, onOpen :function(){},
			    	onClose: function( selectedDate ) {
			    		
			    	
			    		changedAllocStartdate = selectedDate;
			    		 
		                if(this.value == ""){
		   				 $(this).css("border", "1px solid #ff0000");
		   			 	}
		   			 else
		   				{
		   					 $(this).css("border", "1px solid #D5D5D5");
		   				}
		                
		            },
		            beforeShow: function(){
		            	if(changedAllocEnddate==null && firstActDate=="")
					    {
	            		 $(this).datepicker("option", "maxDate", aData[4]);
					    }
		            	else
		            		{
		            		 if(firstActDate!="")
					    		{
					    		 
					    		 $( this ).datepicker( "option", "maxDate", document.getElementById(firstDate).value );
					    		}
		            		 else
		            			 $( this ).datepicker( "option", "maxDate", changedAllocEnddate );		            		}
		            }
			    });
			 $('.todatepicker').datepicker({changeMonth: true,changeYear: true,beforeShowDay: noSundays,yearRange: "2014:2025", minDate: 'today',

			    	onClose: function( selectedDate ) {
			    		changedAllocEnddate = selectedDate;
		               
		                
		            },
		            beforeShow: function()
		            {
		            	 if(changedAllocStartdate==null && lastActDate=="")
						    {
		            		  
		            		 $(this).datepicker("option", "minDate", aData[4]);
						    }
		            	 else
		            		 {
		            		 if(lastActDate!="")
					    		{
					    		 
					    		 $( this ).datepicker( "option", "minDate", document.getElementById(lastDate).value );
					    		}
		            		 else
		            			 $( this ).datepicker( "option", "minDate", changedAllocStartdate );
		            		 }
		            }
			    }); */
			 // End commented by Neha
			
			 // added by neha for datepicker issue - start
			 var s=nRow.cells[1].innerHTML;	
			 $('.fromdatepicker').datepicker({changeMonth: true,changeYear: true ,dateFormat: 'dd-M-yy',beforeShowDay:noSaturday, onOpen :function(){},
			    	onClose: function( selectedDate ) {
			    		 //changedAllocStartdate = selectedDate;
		                if(this.value == ""){
		   				 $(this).css("border", "1px solid #ff0000");
		   			 	}
		   				else
		   				{
		   					 $(this).css("border", "1px solid #D5D5D5");
		   				}
		            }
		           
			 
		            			
			    }); 
			 $('.todatepicker').datepicker({changeMonth: true,changeYear: true,dateFormat: 'dd-M-yy',beforeShowDay: noSundays,
			    	onClose: function( selectedDate ) {
			    		changedAllocEnddate = selectedDate;
		            },
		            beforeShow: function()
		            {

		            	 if(changedAllocStartdate ==null)
						 {
		            		 $(this).datepicker("option", "maxDate", changedAllocStartdate);
		            		 $(this).datepicker("option", "minDate",aData[5]);
		            		<sec:authorize access="!hasAnyRole('ROLE_ADMIN')">
		            		 $(this).datepicker("option", "minDate",-15);
		            		</sec:authorize> 
		            		
						 }
		            	 else
		            	 {
		            		 $( this ).datepicker( "option", "maxDate", changedAllocStartdate );
		            		 $(this).datepicker("option", "minDate", aData[5]);
		            		 <sec:authorize access="!hasAnyRole('ROLE_ADMIN')">
		            		 $(this).datepicker("option", "minDate",-15);
		            		 </sec:authorize> 
		            	 }
		            }
			    });
			    
			// added by neha for datepicker issue - end
			
			$("#allocRemarks").autoGrow();			
			
			containerWidth();
		}
		
		
		function addDataTableSearch(table){
			
			try {
				$("thead input").keyup( function(){
					oTable.fnFilter( this.value, oTable.oApi._fnVisibleToColumnIndex(oTable.fnSettings(), $("thead input").index(this) ) );
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
				
				  $("thead input").blur( function () {
					     if ( this.className == "search_init" ){
					      this.className = "";
					      this.value = "";
					     }
					    });
				
				
			/* 	$("thead input").blur( function (i) {
					if ( this.value == "" ){
						this.className = "search_init";
						this.value = this.initVal;
					}
				} );
				 */
				/* <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
				
				oTable = $('#example').dataTable( {
					//"sDom": 'RClfrtip<"clear">',
					"sDom": 'RC<"clear">lfrti<"top">rt<"bottom"ip<"clear">',
					"bDestroy": true,
						"sPaginationType": "full_numbers",
					"aoColumnDefs": [
						{ "bVisible": false, "aTargets": [] }
					],
					"oLanguage": {
						"sSearch": "Search all columns:"
					},
					"aoColumns": [{},{},{},{},{},{},{},{},{},{},{ "bSortable": false },{ "bSortable": false },{"bSortable": false}],
					"bSortCellsTop": true
				} );
				</sec:authorize> */
<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
				
				oTable = $('#example').dataTable({
					//"sDom": 'RClfrtip<"clear">',
					"sDom": 'RC<"clear">lfrti<"top">rt<"bottom"ip<"clear">',
					"bDestroy": true,
						"sPaginationType": "full_numbers",
					"aoColumnDefs": [
						{ "bVisible": false, "aTargets": [] }
					],
					"oLanguage": {
						"sSearch": "Search all columns:"
					},
					"aoColumns": [{},{},{},{},{},{},{},{},{},{},{},{ "bSortable": false },{ "bSortable": false },{"bSortable": false}],
					"bSortCellsTop": true
				} );
				</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
				
				oTable = $('#example').dataTable( {
					//"sDom": 'RClfrtip<"clear">',
					"sDom": 'RC<"clear">lfrti<"top">rt<"bottom"ip<"clear">',
					"bDestroy": true,
						"sPaginationType": "full_numbers",
					"aoColumnDefs": [
						{ "bVisible": false, "aTargets": [] }
					],
					"oLanguage": {
						"sSearch": "Search all columns:"
					},
					"aoColumns": [{},{},{},{},{},{},{},{},{},{},{},{ "bSortable": false },{"bSortable": false}],
					"bSortCellsTop": true
				} );
				</sec:authorize>
				
				<sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
				
				oTable = $('#example').dataTable( {
					//"sDom": 'RClfrtip<"clear">',
					"sDom": 'RC<"clear">lfrti<"top">rt<"bottom"ip<"clear">',
					"bDestroy": true,
					"sPaginationType": "full_numbers",
					"aoColumnDefs": [
						{ "bVisible": false, "aTargets": [] }
					],
					"oLanguage": {
						"sSearch": "Search all columns:"
					},
					//"aoColumns": [{},{},{},{},{},{},{},{},{},{},{},{ "bSortable": false },{ "bSortable": false },{}],
					"bSortCellsTop": true
				} );
				</sec:authorize>
				/*------for adding new row 07 sep 2012--------*/
			} catch(err) {
			}
				
		}
		
		function getJsonString(name, value){
			if(name.indexOf(".") > -1){
				var items = name.split("." , 2);
				var jsonInner = getJsonString(items[1],value);
				var json = '"'+items[0]+ '":{'+jsonInner+'}';
				return json;
			}
			if(name=='projectEndRemarks'|| name=='allocRemarks'){
				numberOfLineBreaks = (value.match(/\n/g)||[]).length;
				value=value.replace(/(\r\n|\n|\r)/gm, " ");
			}
			if(value.toLowerCase() == "true" )return '"'+name+ '":' + true;
			if(value.toLowerCase() =="false")return '"'+name+ '":' + false;
			if($.trim(value) == '' || $.trim(value).toLowerCase() == 'null')return '';
			return '"'+name+ '":"'+value+'"';
		}
		
		// Wait until the DOM has loaded before querying the document
		var nEditing = null;
		function restoreRow ( oTable, nRow ){
			var aData = oTable.fnGetData(nRow);
			var jqTds = $('>td', nRow);
			for ( var i=0, iLen=jqTds.length ; i<iLen ; i++ ) {
				oTable.fnUpdate( aData[i], nRow, i, true );
			}
			oTable.fnDraw();
		}
		
		function saveRow ( oTable, nRow, projectEndRemarks) {
			//startProgress();
			var aData = oTable.fnGetData(nRow);
		 	
			if(aData == null || aData.length < 1)return;
	 		
			var index = aData[1].indexOf("<input");
			var strArray = aData[1].substr(index , aData[1].length).split(">", 5);
			
			var strEmployeeId = $(strArray[0] +">").val();
		 
		    var resourceAllocionId=$(strArray[1] +">").val();
		 
			var strId ='';
			var allocatedBy;
			var index1='';
	 
			 var allocctypeid ;
			 
			
		 
	 if(aData[3]==''){
		 
			if(document.getElementById("allocTypeEditIdnull").value!=null && document.getElementById("allocTypeEditIdnull").value!='')
			 {
				allocatedBy = "";
				
				index1 =  $("allocTypeEditIdnull").val();
				
			}}
			else {
				
				allocatedBy = $(strArray[4] +">").val();
				  //allocctypeid =   $(aData[2] +">").val();
				  allocctypeid =   getInputValue(aData[3]);
				var alloTypeId= "allocTypeEditId"+allocctypeid;
				if( document.getElementById(alloTypeId).value!='')
				index1 =	 document.getElementById(alloTypeId).value;
				else
				 
					index1 =  getInputValue(aData[3]);
				strId = $(strArray[1] +">").val();
				
			}
	 
			var sData = $('*', oTable.fnGetNodes()).serializeArray();
			if(index1!=null && index1!=''){
				sData.splice(0, 1); 
				sData.push({name:"allocationTypeId.id",value:index1});
				
			
			}
			
			
			if(strId != null && $.trim(strId) != '')
			sData.push({name:"id",value:strId});
			sData.push({name:"allocationSeq",value:aData[2]});
			sData.push({name:"employeeId.employeeId",value:strEmployeeId});


			if(allocatedBy != null && $.trim(allocatedBy) != '')
				sData.push({name:"allocatedBy.employeeId",value:allocatedBy});
			index = aData[10].indexOf("<input");
			
				if(index != -1 ) {
					strArray = aData[10].substr(index , aData[12].length).split(">", 2);
					var allocatedById = $(strArray[0] +">").val();
					if(allocatedById != null && $.trim(allocatedById) != '')
					sData.push({name:"allocatedBy.employeeId",value:allocatedById});
				}
				index = aData[11].indexOf("<input");
				if(index != -1 ) {
				strArray = aData[11].substr(index , aData[11].length).split(">", 2);
				var updatedById = $(strArray[0] +">").val();
				if(updatedById != null && $.trim(updatedById) != '')
				sData.push({name:"updatedBy.employeeId",value:updatedById});
				}
				if(addNewAlloc== true){
					editAlloc=false;
					if (!(typeof projectEndRemarks === "undefined")) {
					if(projectEndRemarks!="")
					sData.push({name:"projectEndRemarks",value:projectEndRemarks});
				}
					else{
						sData.push({name:"projectEndRemarks",value:"NA"});
					}
				}
				if(editAlloc== true){
				if(aData[0]=='NA' && typeof projectEndRemarks === "undefined")
				{
					sData.push({name:"projectEndRemarks",value:'NA'});
				}
				
				if(aData[0]=='NA' && (typeof projectEndRemarks!= "undefined"))
				{
					sData.push({name:"projectEndRemarks",value:projectEndRemarks});
				}
				
				if(aData[0]!='NA' && (typeof projectEndRemarks!= "undefined"))
				{
					sData.push({name:"projectEndRemarks",value:projectEndRemarks});
				}
				
				if(aData[0]!='NA' && typeof projectEndRemarks === "undefined")
				{
					var n = aData[0].indexOf("div");
					var divId = aData[0].substring(n+8, n+15);
					sData.push({name:"projectEndRemarks",value:$("#"+divId).text().trim()});
				}
			}
				
				var allocPercentage = $("#allocPercentage").val().trim();
				var idx = sData.findIndex(function(obj) {
					 return obj.name == 'allocPercentage';
				});
				if (idx > -1) {
					sData[idx].value =  allocPercentage || "0";
				} else {
					sData.push({name:"allocPercentage",value: allocPercentage || "0"});
				}
	
			var jsonData ='{'
			$.each(sData, function(i, item) {
				if(item.name == 'projectId.id' && item.value == -1) {
				}else {
					var jsonString = getJsonString(item.name , item.value);
					if(jsonString != null && $.trim(jsonString) != '')
					jsonData += getJsonString(item.name , item.value) +",";
				}
				
			});
			jsonData = jsonData.slice(0, -1);
			jsonData +='}';
			
			//US3090: START: Future timesheet delete functionality 
			var resAllocId=resourceAllocionId;
			var weekEndDate =null;
			var weekEndDate123 =null;
			var endDate=document.getElementById("allocEndDate").value;
			if( endDate != null && endDate != ''){
				 weekEndDate= document.getElementById("allocEndDate").value;
			 	 weekEndDate123= document.getElementById("allocEndDate").value;
				} 
			// var a = ( weekEndDate.getDate()) +"-"+((weekEndDate.getMonth() + 1))+"-"+ (weekEndDate.getFullYear());
			 var flag = false;
			 var userActivityId;
			 if(resAllocId!=""){ 
			 if( weekEndDate != null && weekEndDate != '')
			 {  
				 $.ajax({
							type: 'POST',
						    url: 'resourceallocations/getTimesheetStatus/'+resAllocId +"/"+weekEndDate,
						    contentType: "application/json; charset=utf-8",
						    async:false,
						    success: function(data) {
						    	var jsonData1 = JSON.parse(data);	
						    	if(jsonData1.status == "true")
					    		{
						    		$.postData("resourceallocations", jsonData, function(data) {
											//var id = document.getElementById("employeeIdHidden").value;
											//getResourceAllocationById(id);
											checkAll('');
											saveOpen = false;
											nEditing= null;
											stopProgress();
											var successMsg ="Resource Allocation detail have been saved successfully"; 
											showSuccess(successMsg);
											 /* if(endDate!='')
												{
												 $('#hiddenAnchor').trigger('click');
													saveResourceReleaseDetails(empIdGlobal,projectIdGlobal,allocationIdGlobal,allocStartDateGlobal,allocEndDateGlobal,dateOfJoiningGlobal);
												}
											 
											 else if(endDate!='' ){
												if(endDate!=document.getElementById("projectEndDateHidden").value){
													//$('#hiddenAnchor').trigger('click');
													saveResourceReleaseDetails(empIdGlobal,projectIdGlobal,allocationIdGlobal,allocStartDateGlobal,allocEndDateGlobal,dateOfJoiningGlobal);
												}
											 } */
										 }, 
										 "json"
										);
					    		}
						    	else{
						    	   $.each(jsonData1, function(i, item) {
			                            if(item.status=='false')
			                            {
			                            	flag = true;
			                            }
						    	 });
			                     if(flag){
			                           
			                    	 		userActivityId = JSON.stringify(jsonData1);
			  							    var answer=confirm("Are You sure, you  want to save this end date? If yes, then all the submitted timesheet after this date will be deleted.");
			  							    if(answer){
			  									 $.ajax({
			  											type: 'GET',
			  											contentType : 'application/json; charset=utf-8',
			  								            dataType: 'json',
			  										    url: 'resourceallocations/deleteFutureTimesheet',
			  										    async:false,
			  										   // data : {userActivityId: userActivityId},
			  										   data : "userActivityId=" + userActivityId + "&resourceAllocId=" + resAllocId
			  										+ "&weekEndDate=" + weekEndDate123,
			  										
			  										    success: function(data) {
			  										    	
			  										    	$.postData("resourceallocations", jsonData, function(data) {
			  													//var id = document.getElementById("employeeIdHidden").value;
//			  													//getResourceAllocationById(id);
			  													checkAll('');
			  													saveOpen = false;
			  													nEditing= null;
			  													stopProgress();
			  													var successMsg ="Resource Allocation detail have been saved successfully"; 
			  													showSuccess(successMsg);
			  													 /* if(endDate!='')
				  													{
				  													 $('#hiddenAnchor').trigger('click');
				  														saveResourceReleaseDetails(empIdGlobal,projectIdGlobal,allocationIdGlobal,allocStartDateGlobal,allocEndDateGlobal,dateOfJoiningGlobal);
				  													}
			  												 
				  												 else if(endDate!='' ){
				  													if(endDate!=document.getElementById("projectEndDateHidden").value){
				  														//$('#hiddenAnchor').trigger('click');
				  														saveResourceReleaseDetails(empIdGlobal,projectIdGlobal,allocationIdGlobal,allocStartDateGlobal,allocEndDateGlobal,dateOfJoiningGlobal);
				  													}
				  												 } */
			  												 }, 
			  												 "json"
			  												); 
			  										    	
			  										    }
			  										    
			  									    });
			  									
			  								}
			  							    else{
			  							    	
			  									stopProgress();
			  							    
			  							       }
			  							    }
			  						 }
						   
          				    }// success ends
				});
			}
			 
			 else{
		    	   $.postData("resourceallocations", jsonData, function(data) {
							//var id = document.getElementById("employeeIdHidden").value;
//							//getResourceAllocationById(id);
							checkAll('');
							saveOpen = false;
							nEditing= null;
							stopProgress();
							var successMsg ="Resource Allocation detail have been saved successfully"; 
							showSuccess(successMsg);
							 /* if(endDate!='')
								{
								 $('#hiddenAnchor').trigger('click');
									saveResourceReleaseDetails(empIdGlobal,projectIdGlobal,allocationIdGlobal,allocStartDateGlobal,allocEndDateGlobal,dateOfJoiningGlobal);
								}
							 
							 else if(endDate!='' ){
								if(endDate!=document.getElementById("projectEndDateHidden").value){
									//$('#hiddenAnchor').trigger('click');
									saveResourceReleaseDetails(empIdGlobal,projectIdGlobal,allocationIdGlobal,allocStartDateGlobal,allocEndDateGlobal,dateOfJoiningGlobal);
								}
							 } */
						 }, 
						 "json"
						); 
		       }
			 
       }	
			
		 if(resAllocId == "") {
			 
			 var startDate=document.getElementById("allocStartDate").value;
			 var endDate=document.getElementById("allocEndDate").value;
  			 var flag = true;
				
			 flag = validDates1(startDate,endDate);
										
				if(!flag) {
					showError("End date should be greater than start date.");
					return;
				}
				
		 $.postData("resourceallocations", jsonData, function(data) {
				
				//var id = document.getElementById("employeeIdHidden").value;
				//getResourceAllocationById(id);
				checkAll('');
				saveOpen = false;
				nEditing= null;
				stopProgress();
				var successMsg ="Resource Allocation detail have been saved successfully"; 
				showSuccess(successMsg);
				/*  if(endDate!='')
					{
					 $('#hiddenAnchor').trigger('click');
						saveResourceReleaseDetails(empIdGlobal,projectIdGlobal,allocationIdGlobal,allocStartDateGlobal,allocEndDateGlobal,dateOfJoiningGlobal);
					}
				 
				 else if(endDate!='' ){
					if(endDate!=document.getElementById("projectEndDateHidden").value){
						//$('#hiddenAnchor').trigger('click');
						saveResourceReleaseDetails(empIdGlobal,projectIdGlobal,allocationIdGlobal,allocStartDateGlobal,allocEndDateGlobal,dateOfJoiningGlobal);
					}
				 } */
			 }, 
			 "json"
			);   
		}	 
	}
	//US3090: END: Future timesheet delete functionality	
		
		$.fn.dataTableExt.oApi.fnStandingRedraw = function (oSettings) {
		   
		        var before = oSettings._iDisplayStart;

		        oSettings.oApi._fnReDraw(oSettings);

		        // iDisplayStart has been reset to zero - so lets change it back
		        oSettings._iDisplayStart = before;
		        oSettings.oApi._fnCalculateEnd(oSettings);
		    

		    // draw the 'current' page
		    oSettings.oApi._fnDraw(oSettings);
		};
		
		
		var listActiveOrAll;
		var showEntries ;
		$(document).ready(function(){ 
			
			var navigationFlag="${navigationFlag}";
			
			if(navigationFlag=="true"){
				var empId="${empId}";
				var yashEmpId="${yashEmpId}";
				var empName="${empName}";
				var empDOJ="${empDateOfJoining}";
				
				openMaintainance(empId,yashEmpId,empName,empDOJ);
			}
			
			//startProgress();
			//listActiveOrAll = "${listActiveOrAll}";	
			//document.getElementById('listActiveOrAll').value= listActiveOrAll;
			showEntries = 20;
			containerWidth();
			$(".tab_div").hide();
			$('ul.tabs a').click(function () {
				/* $("td input[type=text]").each(function(){
					var default_value = $(this).prop("defaultValue");
					$(this).val(default_value);
				}); */
				
				$(".tab_div").hide().filter(this.hash).show();
				saveOpen = false;
				$("ul.tabs a").removeClass('active');
				$('a[href$="tab2"]').addClass('MaintenanceTab');
				$(this).addClass('active');
				
				$("#maitainanceId").css("display","none");
				
				
				
				
				containerWidth();
				return false;
				
			}).filter(':first').click();
			
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
			
			var default_value;
			 $("thead input").keyup( function(e){
					
				 /* if(this.value == "")
				{
					 if (resourceallocationTable != null) {
						  resourceallocationTable.fnClearTable();
						initTable();

						}
				} */
				if (e.keyCode == 13) {
				       
						resourceallocationTable.fnFilter( this.value, resourceallocationTable.oApi._fnVisibleToColumnIndex(resourceallocationTable.fnSettings(), $("thead input").index(this) ) );
				    }else if(this.value == 0){
				    	resourceallocationTable.fnFilter( this.value, resourceallocationTable.oApi._fnVisibleToColumnIndex(resourceallocationTable.fnSettings(), $("thead input").index(this) ) );
				    }
				//resourceallocationTable.fnFilter( this.value, resourceallocationTable.oApi._fnVisibleToColumnIndex(resourceallocationTable.fnSettings(), $("thead input").index(this) ) );
				});	 
				
			 $("thead input").each( function(i){
					this.initVal = this.value;
				});
				$("thead input").focus( function () {
					  /* default_value = $(this).prop("defaultValue"); */
					if ( this.className == "search_init" ){
						this.className = "";
						this.value = "";
					}
				});
				
				$("thead input").blur( function (i) {
			  if ( this.value == "" ){
				 this.className = "search_init";
					this.value = this.initVal;
			
					//$(this).val(default_value);
					}  
			 /* if(this.value != "")
				{
					resourceallocationTable.fnFilter( this.value, resourceallocationTable.oApi._fnVisibleToColumnIndex(resourceallocationTable.fnSettings(), $("thead input").index(this) ) );
				} */
				} );
		
			  resourceallocationTable = $('#resourceallocationTableId').dataTable({
                "bProcessing": true,
                "bServerSide": true,       
                "sAjaxSource": 'resourceallocations/list/'+"active",
                "sPaginationType": 'full_numbers',
                "pagingType": "full_numbers",
                "bAutoWidth" : false,
                "sScrollX": "100%",
                "bScrollCollapse": true,
                "bPaginate": true,
                "iDisplayLength": showEntries,
                "bDestory": true,
                "bRetrieve": true,
                "sDom": '<"projecttoolbar">lfrtip',
                "sDom": '<"top"lfi>rt<"bottom"ip<"clear">',
                "aaSorting": [ [0,'asc']],
                "oLanguage": {
                                "sLengthMenu": 'Show <SELECT class="smallSelectBoxStyle" id ="display">'+ '<OPTION value=20>20</OPTION>'+ '<OPTION value=25>25</OPTION>'+ '<OPTION value=50>50</OPTION>'+ '<OPTION value=100>100</OPTION>'+ '<OPTION value=200>200</OPTION></SELECT> entries'
                },
               
         		"bSortCellsTop": true,
                "fnServerData": function ( sSource, aoData, fnCallback ) {
                    sSource = sSource + '?noCache=' + new Date().getTime();
                    callJSONWithErrorCheck(sSource,aoData,null,function (json) {  
                           fnCallback(json); 
                    });                           
				    },
				    "fnDrawCallback": function() {
				                    handlePaginationButtons(resourceallocationTable, "resourceallocationTableId");
				                    },
				    "fnInitComplete": function() {
				    },
				    
				    
				    "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
				     $('td:eq(0)', nRow).html('<a href="return false;" onClick="openMaintainance('+aData[0]+',\''+aData[1]+'\',\''+aData[2]+'\',\''+aData[9]+'\');return false;">' +
			                aData[1] + '</a>'); 
				     var releaseDate = aData[8];
			     	 if(releaseDate && releaseDate < today){	
			     		 
						nRow.className='redColorResource';
					}
			            return nRow;
			        },
				   
				    "aoColumns": [ 
									{ 
										sName: "employeeId",
										sWidth : "10px",
										bSortable: false,
										bVisible:false
									},
													                  
									 
									{ 
										sName: "yashEmpId",
										sWidth : "161px",
										bSortable: true
										
									},
									{ 
										sName: "employeeName",
										sWidth : "218px",
										bSortable: true
									},
									{ 
										sName: "designation_name",
										sWidth : "188px",
										bSortable: true
									},
									{ 
										sName: "grade",
										sWidth : "113px",
										bSortable: true
									},
									{ 
										sName: "currentBgBuName",
										sWidth : "188px",
										bSortable: false
									},
						
									{ 
											sName: "location",
											sWidth : "188px",
											bSortable: true
									},
									
								 										 
									{ 
										sName: "currentProject",
										sWidth : "138px",
										bSortable: true
							
									}
									
							        ] });							 
									
									/* { 
										sName: "totalPlannedHrs",
										sWidth : "138px",
										bSortable: true
									},
									{ 
										sName: "totalReportedHrs",
										sWidth : "156px",
										bSortable: true
									},
									{ 
										sName: "totalBilledHrs",
										sWidth : "111px",
										bSortable: true
									}  */
              
			$("#resourceallocationTableId_filter").hide();
			
			
			$('#update').click(function(){
				displayMaintainance();
			});
			
			var projectEndRemarks='';
			$('#okbtn').click(function(){
				var projectEndRemarks = "";
				//projectEndRemarks=$("#feedbackText").text();
				projectEndRemarks=$.trim($("#feedbackText").val())
				$("#feedbackText").val(null);
				if(projectEndRemarks.length  == "" || projectEndRemarks == null ){
					$("#feedbackText").focus();
					showError("Please give project allocation end feedback.");
				}
				else{
					$.fancybox.close();
					saveRow(oTable, nEditing,projectEndRemarks);
				}
			});
			
			$('#cancelbtn').on("click", function(){
				$("#feedbackText").val(null);
				$.fancybox.close();
				
			});
			
			function initTable()
			{
				var listActiveOrAll = document.getElementById("listActiveOrAll").value;
				var sAjaxSource ;
			
				if(listActiveOrAll=='All')
   				{
						sAjaxSource = "resourceallocations/list/all";
   				 
   				}
   				else
   				{
   					sAjaxSource = "resourceallocations/list/active";
   				   
   			 
   				}
			 
		  	resourceallocationTable.fnDestroy();
				$('#resourceallocationTableId').dataTable({
	                "bProcessing": true,
	                "bServerSide": true,       
	                "sAjaxSource": sAjaxSource,
	                "sPaginationType": 'full_numbers',
	                "pagingType": "full_numbers",
	                "bAutoWidth" : false,
	                "sScrollX": "100%",
	                "bScrollCollapse": true,
	                "bPaginate": true,
	                "iDisplayLength": showEntries,
	                "bDestory": true,
	                "aaSorting": [ [0,'asc']],
	                "bRetrieve": true,
	                "sDom": '<"projecttoolbar">lfrtip',
	                "sDom": '<"top"lfip>rt<"bottom"ip<"clear">',
	               	                "oLanguage": {
	                                "sLengthMenu": 'Show <SELECT id ="display">'+ '<OPTION value=20>20</OPTION>'+ '<OPTION value=25>25</OPTION>'+ '<OPTION value=50>50</OPTION>'+ '<OPTION value=100>100</OPTION>'+ '<OPTION value=200>200</OPTION></SELECT> entries'
	                },
	               
	         		"bSortCellsTop": true,
	                "fnServerData": function ( sSource, aoData, fnCallback ) {
	                    sSource = sSource + '?noCache=' + new Date().getTime();
	                    callJSONWithErrorCheck(sSource,aoData,null,function (json) {  
	                           fnCallback(json); 
	                    });                           
					    },
					    "fnDrawCallback": function() {
					                    handlePaginationButtons(resourceallocationTable, "resourceallocationTableId");
					                    },
					    "fnInitComplete": function() {
					    },
					    
					    
					    "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
					    $('td:eq(0)', nRow).html('<a href="return false;" onClick="openMaintainance('+aData[0]+',\''+aData[1]+'\',\''+aData[2]+'\',\''+aData[9]+'\');return false;">' +
				                aData[1] + '</a>');
					    var releaseDate = aData[8];
				     	 if(releaseDate && releaseDate < today){					
							nRow.className='redColorResource';
						}
				            return nRow;
				        },
					   
					    "aoColumns": [ 
										{ 
											sName: "employeeId",
											sWidth : "10px",
											bSortable: false,
											bVisible:false
										},
														                  
										 
										{ 
											sName: "yashEmpId",
											sWidth : "161px",
											bSortable: true
											
										},
										{ 
											sName: "employeeName",
											sWidth : "218px",
											bSortable: true
										},
										{ 
											sName: "designation_name",
											sWidth : "188px",
											bSortable: true
										},
										 { 
											sName: "locName",
											sWidth : "188px",
											bSortable: true
										},
										{ 
											sName: "curBgBu",
											sWidth : "188px",
											bSortable: true
										}, 
										{ 
											sName: "grade",
											sWidth : "113px",
											bSortable: true
										},
										 
										{ 
											sName: "currentProject",
											sWidth : "138px",
											bSortable: true
										}  
							             			 
										
										/* { 
											sName: "totalPlannedHrs",
											sWidth : "138px",
											bSortable: true
										},
										{ 
											sName: "totalReportedHrs",
											sWidth : "156px",
											bSortable: true
										},
										{ 
											sName: "totalBilledHrs",
											sWidth : "111px",
											bSortable: true
										}  */
										 ]	});			
				
				
				$("#resourceallocationTableId_filter").hide(); 
				$('#resourceallocationTableId_info').show();
				 
		    }
			
			 $(document.body).on('change','#listActiveOrAll',function(){
				
				  if(this.value=='All')
		   				{
		   				var oSettings = resourceallocationTable.fnSettings();
		   				    oSettings.sAjaxSource  = "resourceallocations/list/all";
		   				 resourceallocationTable.fnClearTable();
		   				resourceallocationTable.fnDraw();
		   				 
		   				}
		   				else
		   				{
		   				var oSettings = resourceallocationTable.fnSettings();
		   				    oSettings.sAjaxSource  = "resourceallocations/list/active";
		   				 resourceallocationTable.fnClearTable();
		   				resourceallocationTable.fnDraw();
		   				}
				});
			$('#report').click(function(){
				
				 $.ajax({
						type: 'POST',
				        url: 'resourceallocations/reports',
				     	contentType: "pdf/html",
				     	async:false,
				     	success: function(succeResponse) { 
				     		
				     	}
				 });
			});	
			
			 $(document).on("change", '#display', function(ev){
				
				});
			
			
			
		 
			
			$('#addNew').click(function (e) {
				/* $("td input[type=text]").each(function(){
					var default_value = $(this).prop("defaultValue");
					$(this).val(default_value);
				}); */
				lastactivityDate="";
				addNewAlloc =true;
				editAlloc = false;
				if(saveOpen){
					// Added for task # 216 - Start
					
					var text="Please enter and save the data";
					
					/* Added for task #846 */
					$.blockUI({ 
			            message: null,
			            onBlock: function() { 
							showAlert(text);
							$('.noty_buttons').on('click', function() {
								$.unblockUI();
							});
			            } 
			        }); 
					/* showAlert(text);*/
					// Added for task # 216 - End
					e.preventDefault();
					return; 
				}
				e.preventDefault();
				var nYashEmpId = document.getElementById("yashEmpIdHidden").value;
				/**Start - Added check to fix Bug #206 */
				if(null == nYashEmpId || nYashEmpId == ''){
					var errorMsg ="Please select Resource from the List"; 
					showError(errorMsg);
					return;
				}
				/**End - Added check to fix Bug #206 */
				
				//Start- Added for task #258
				var nYashEmpIdStr = '<center>'+nYashEmpId+'</center>'+ '<input type="hidden" id="employeeId" value="' + document.getElementById("employeeIdHidden").value +'"/>' +
				'<input type="hidden" id="id" value=""/>' ;
				//var aiNe = oTable.fnAddDataAndDisplay(['','']);
				//alert('aiNew >'+aiNe);
				
				/* var aiNew = oTable.fnAddDataAndDisplay( [ nYashEmpIdStr , '', '', '', '','', '', '', '', '','', '', '', '','','',
					'<a class="edit" href="">Edit</a>']); */
					
					//Display of delete as per role - start
					//var aiNew = oTable.fnAddDataAndDisplay( ['', nYashEmpIdStr , '', '', '', '','', '', '', '','','<a class="delete" href="" onclick="deleteDataRow(null);" >Delete</a>','' ] );
					var aiNew = "";
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						aiNew = oTable.fnAddDataAndDisplay( ['', nYashEmpIdStr , '', '', '', '', '','', '', '', '','','<a class="delete" href="" onclick="deleteDataRow(null);" >Delete</a>','' ] );
					</sec:authorize>
					<sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
						aiNew = oTable.fnAddDataAndDisplay( ['', nYashEmpIdStr , '', '', '', '', '','', '', '', '','','','' ]  );
					</sec:authorize>
					//Display of delete as per role - start
					
				//End - Added for task #258
				
				//alert('test table  '+aiNew);
				var nRow = aiNew.nTr;
				global = nRow;
				//var nRow = oTable.fnGetNodes(aiNew[0]);
				editRow( oTable, nRow );
				nEditing = nRow;
				saveOpen = true;
				$(".comboselect").combobox();
				
                // for removing grid on clicking addnew link
				$("#example tbody tr:first").find("td").find("a.cancel").addClass("delAddedRow");
				// for removing grid on clicking addnew link
				
			});
			
			
			$(document).on('click','#example a.delete', function (e) {
				e.preventDefault();
				//var nRow = $(this).parents('tr')[0];
			//	saveOpen = false;
				//nEditing= null;
				 $('<div class="toasterBgDiv"></div>').appendTo($('body'));
			});
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
			
			var cells = [];
			$(document).on('click', '#example a.edit', function (e) {
				cells = [];
	
				editAlloc = true;
				var rows = $("#example").dataTable().fnGetNodes();
				var inputArray = new Array();
				var labelArray = new Array();
				
				/* for (var i = 0; i < rows.length; i++) {
					// Get HTML of 3rd column (for example)
					var current = $(rows[i]).find("td:eq(7)").html();
				
					if(current!=='Yes' ||current!=='No'){
					alert(current + 1);
					cells.push(current);}
				} */
				var labelSelect = $('#example' + ' >tbody >tr').find("td:eq(9)");
				var inpSelect = $.trim(($('#example' + ' >tbody >tr').find("td:eq(9) select")).val());
				var rowCount = $('#example'  + ' >tbody >tr').length;
				$(labelSelect).each(function() {
					if ($(this).text() != '') {
						tdInpVal = $.trim($(this).text());
						labelArray.push(tdInpVal);
					} else {
						inputArray.push(inpSelect);
					}
				});
				
				
				$(this).parent().parent().find("td").attr("align","left");
				$(this).parent().parent().find("td:nth-child(1)").attr("align","center");
				if(saveOpen && !this.innerHTML == "Save"){
					// Added for task # 216 - Start
					/* alert("Please enter and save the data"); */
					var text="Please enter and save the data";
					showAlert(text);
					// Added for task # 216 - End
					e.preventDefault();
					return;
				}
				saveOpen=true;
				e.preventDefault();
				
				/* Get the row as a parent of the link that was clicked on */
				var nRow = $(this).parents('tr')[0];
				if ( nEditing !== null && nEditing != nRow  ) {
					/* Currently editing - but not this row - restore the old before continuing to edit mode */
					restoreRow( oTable, nEditing );
					editRow( oTable, nRow );
					nEditing = nRow;
				}
				
				// this block executes when we save data without any change in resource allocation
				else if ( nEditing == nRow && this.innerHTML == "Save" ) {
					//validation put here.. if fails add return..
					/*------------------------Added By Prasoon------------------------------*/
					var flag = true;
					var startDate=document.getElementById("allocStartDate").value;
					 if(startDate == "" || startDate == undefined){
						showError("Start Date Cannot be blank");
						 $("#allocStartDate").css("border", "1px solid #ff0000");
						return;
					} 
					 else{				
						 $("#allocStartDate").css("border", "1px solid #D5D5D5");
						 
						}
					
					var endDate=document.getElementById("allocEndDate").value;
					/* var dateOfJoin= $(this).closest("tr").find("td:nth-child(2)").find("#dateOfJoining").val(); */
					var dateOfJoin=document.getElementById("dateofjoiningvalidate").value;
					// Added for Task # 302- Start
// 					var weeklyAllocationHours=document.getElementById("allocHrs").value;
					// Added for Task # 302 - End
					
					var isValidPercentage = onAllocationPercentageChange();
				     if (!isValidPercentage) {
					return false;
				    }
					
					var selectedProjectId = $("[name='projectId.id']").val();
					var errorMsg = "";
					var projectList = '${projects}';
					<c:forEach var="project" items="${projects}">
					var projectId = '${project.id}';
					var projectKickOffDate = '${project.projectKickOff}';
					var projectEndDate = '${project.projectEndDate}';
						if(projectId == selectedProjectId){
							var flag = true;
							flag = validDates1(projectKickOffDate, startDate);
							if(!flag){
								showError("Resource Cannot be allocated before Project Kick Off");
								return;
							}
							flag = validDates1(startDate,projectEndDate);
							if(!flag){
								showError("Resource Cannot be allocated after Project End Date");
								return;
							}
							flag = validDates1(endDate,projectEndDate);
							if(!flag){
								showError("Resource Allocation End Date Cannot be after Project End Date");
								return;
							}
							flag = validDates1(projectKickOffDate,endDate);
							if(!flag){
								showError("Resource Cannot be allocated for the given span");
								return;
							}
							   // var JDate = new Date(dateOfJoin);JDate.setHours(0, 0, 0, 0);
							//var SDate = new Date(startDate);SDate.setHours(0, 0, 0, 0);
							
						 flag = validDatesForJoining(dateOfJoin,startDate);
						 if(!flag){
							    var dateSplit = dateOfJoin.split("-"); 
								var dateObjendDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);	
								var error = "Resource Cannot be allocated before  " + dateObjendDate + "  Joining Date "; 
							 showError(error);
									return;	
							 } 
						 
						 var primaryProject = $('#currentProjectEditId').find('option:selected').text();
						
						 	var d = new Date();						
						    var m_names = new Array("Jan", "Feb", "Mar", 
				    		"Apr", "May", "Jun", "Jul", "Aug", "Sep", 
				    		"Oct", "Nov", "Dec");
						    var curr_date = d.getDate();
						    var curr_month = d.getMonth();
						    var curr_year = d.getFullYear();
						    
						    var currentDate= curr_date + "-" + m_names[curr_month]+ "-" + curr_year;
							flag = validDates1(currentDate ,endDate);
							if(!flag){
								for (var i = 0; i < labelArray.length; i++) {
									if ( primaryProject =='Yes') {
										//showError("Please Change the Primary Project to No before giving the Allocation End Date");
										//return;
										flag = true;
									}else{
										flag = true;	
									}
								}
							}
							
						}
					</c:forEach>
					
					
					 $(".tbl tbody").find("input[type=text].requir").each(function(){
						 var dataTblInputVal = $(this).val();
						 if(dataTblInputVal == ""){
							 flag = false;
							 $(this).css("border", "1px solid #ff0000");
						 }
						 else
							{				
							 $(this).css("border", "1px solid #D5D5D5");
							 
							}
					 });
					 
					 if(flag == true)
				        {
						 //we have remove (Project Allocation End Feedback) popup
						 /* var aData = oTable.fnGetData(nEditing);
						  if(endDate!='' && addNewAlloc==true)
							{
							 	$.fancybox("#commentBox");
								return;
							}
						 
						 else if(endDate!='' && editAlloc==true){
							 if(endDate!=dateOfProjectEnd){
							 $.fancybox("#commentBox");
								return;
							}
						 }  */
						 saveRow(oTable, nEditing);
						 return false;
				        }
					
				}
				else {
					/* No edit in progress - let's start one */
								
						editRow( oTable, nRow );
						nEditing = nRow; 
						
					
				}
				$( ".comboselect" ).combobox();
				});
			$('a[href$="tab2"]').click(function(){
				$("input.search_init").each(function(){
					var default_value = $(this).prop("defaultValue");
					$(this).val(default_value);
					
				});
				 if(oTable != null)oTable.fnClearTable(); 
	  			 document.getElementById("inner").innerHTML="";
			}); 
			
			if(navigationFlag!="true"){
			    document.getElementById("selectedEmployeeName").innerHTML= "Maintenance";
			}    
			$('#MaintenanceTabInactive').off('click');	
			$('a[href$="tab1"]').click(function(){
				
			
				document.getElementById("selectedEmployeeName").innerHTML= "Maintenance";
				//document.getElementById("editTip").innerHTML="";
				/* $("input.search_init").each(function(){
					var default_value = $(this).prop("defaultValue");
					$(this).val(default_value);
					var oSettings = resourceallocationTable.fnSettings();
					//oSettings.bProcessing = false;
					//$('#resourceallocationTable').dataTable({ "bProcessing": false },{ "bSort": false }).fnDraw();
					resourceallocationTable.fnDraw();
				}); */
				saveOpen = false;
				//initTable();
			});
			
			
		});
		
		$(document).on('click', '.delAddedRow', function(){
			cancelDataRow(null,null,null);
		});
		
		
		
		
		
		/*------------------------------Blur Function For Validation------------------------------------*/
		$(document).on('blur','.tbl tbody input[type=text].requir', function (e) {
			 var dataTblInputVal = $(this).val();
			 if(dataTblInputVal == ""){
				 $(this).css("border", "1px solid #ff0000");
			 }
			 else
				{
					 $(this).css("border", "1px solid #D5D5D5");
				}
		});	
		
		/*------------------------------Function to Display Grid data  after loader-----------------------------------*/
function displayTableData(){
	setTimeout(function(){
		stopProgress();
		$('#resourceallocationTableId').show();
		$('#resourceallocationTableId_info').show();
		}, 500);
}
		
			
function displayMaintainance(){
	$(".maintainanceTblData").show();
	$(".maintainanceDialog").show();
	$(".maintainancetab_div").show();
	$(".resourceAllocationBtnIcon").show();
	$("#maitainanceId").css("display","inline-block");
	 $("ul.tabs a").removeClass('active');
	 $(".tab_div").hide().next("#tab2".hash).show();
	 $('a[href$="tab2"]').removeClass('MaintenanceTab');
	 $('a[href$="tab2"]').addClass('active');
	 containerWidth();
}	

var vm = {
		getMultiSelectResource:function(){
		    var multiObjs =  document.getElementById("multiresource").value;
// 		    for(var i=0; i<multiObjs.length; i++ ) {
		    	var obj = multiObjs;
		    	$(obj).multiselect({
					 noneSelectedText: 'Select Resources',
						 selectedList: 2
			    });
				$(obj).multiselect("uncheckAll");
// 		    }
		}
	};
	$.views.helpers({ getMultiResource: vm.getMultiSelectResource });
function openMaintainance(id,yashEmpId,yashEmpName,dateOfJoining){
	employeeid = id;
	yashempid = yashEmpId;
	yashempname=yashEmpName;
	startProgress();
	document.getElementById('dateofjoiningvalidate').value=dateOfJoining;
	$( ".MaintenanceTab" ).show();
	$( "#selectedEmployeeName" ).show();
	document.getElementById("selectedEmployeeName").innerHTML= yashEmpName+" ["+yashEmpId+"]";
	document.getElementById("yashEmpIdHidden").value = yashEmpId;
	document.getElementById("employeeIdHidden").value = id;
	document.getElementById("activeOrAll").value = 'Active';
	getResourceAllocationById(id);
	//DE2997(Added by Maya): START
	getEmployeeDetail(employeeid);
	
}

function getEmployeeDetail(id){  
	$.getJSON("resourceallocations",{find:"getResourceByEmployeeId" , employeeId:id}, showResourceReleaseDate);
}

function showResourceReleaseDate(data){
	var releaseDate=data.relDate;
	if(releaseDate!=null){
		if(new Date(data.relDate)<=new Date()){
			$(".blue_link").hide();
		}
	}	
	else{
		$(".blue_link").show();
	}
}
//DE2997(Added by Maya): END		

function getResourceAllocationById(id){
	$.getJSON("resourceallocations",{find:"ByActiveTypeResourceEmployeeId" , employeeId:id}, showResourceAllocation);

/* 	$.getJSON("resourceallocations",{find:"ByResourceEmployeeId" , employeeId:id}, showResourceAllocation);
 */}

var addSearch = true;
function showResourceAllocation(data){

   document.getElementById('navigationToTimeHrs').innerHTML="<strong>Approve "+ yashempname+"'s Timesheet</strong>"+"<img src='resources/images/review-icon-custom.png'/>";

	//resallocdata= data;
	if(oTable != null)oTable.fnClearTable();
	$("#example > tbody:last").append($("#resourceAllocationRows").render(data));
	//vm.getMultiSelectResource();
	displayMaintainance();
	
	//Edit Disabled Start: if BGAdmin bus and Project bu different
	
	var bGBuIds = [];
     $.ajax({
		type: 'GET',
	    url: '/rms/resourceallocations/getAllBusForBGADMIN/',
	      contentType: "application/json; charset=utf-8",
	   
	    async:true,
	    success: function(data) { 
	    
	     $.each(data, function(key,val){
	    	 
	    	 bGBuIds.push(data[key].id);
				
		});
	    
	     $(".prjID").each(function(){ 
	    	 var count=0;
	     for(i=0;i<bGBuIds.length;i++){ 
	        if(bGBuIds[i]==$(this).val()){
	    	   count++;
	       } 
	     }
	     var index=$(this).closest('tr').index();
	     
	     if(count==0){ 
	    	$(this).closest('tr').find('td:eq(10)').addClass("disabled");
	    	//  $(".delete").eq(index).addClass("disabled");
		   //  $(".delete").eq(index).click(function () {return false;}); 
	       $(".edit").eq(index).addClass("disabled");
		   //$(".edit").eq(index).click(function () {return false;});
		   
	     }
	       
	  });
	     	
	    },
	    error: function() {
	    	 showError("Some error occured");
	      }
	}); 
      
	//Edit Disabled End: if BGAdmin bus and Project bu different
	
	highlightRow();
	stopProgress();
	
	$(".multiSelectDd").multiselect().multiselectfilter();
	if(addSearch){
		
	}
	addDataTableSearch($("#example"));
	containerWidth();
}

function saveResourceAllocation(){
	var pk = $("#resourceallocationForm input[name=id]").val();
	//not doing any validation .. We will use jquery validation framework to do that for us before form gets submitted
	if( pk != null && pk > 0){
		 $.putJson('resourceallocations', $("#resourceallocationForm").toDeepJson(),function(data){
		 location.reload();
		 } ,'json');
	}else{
		 $.postJson('resourceallocations',$("#resourceallocationForm").toDeepJson(),function(data){
			 //should call refreshGrid instead of location reload, and update customerTableId table.
			 location.reload();
		}, 'json');
}
}

/* Added for task #1229 */
function highlightRow(){
	var employeeBuId = [];
	var projectBuId = [];
	var allocatEndDate = [];
	var rowId= [];
	var highlightRowTipFlag = 0;
	
		$(".empID").each(function(){
			//var employeeBuId = $(this).val();
			employeeBuId.push($(this).val());
			rowId.push($(this).attr("id"));
			
		});
			
		$(".prjID").each(function(){
			//var employeeBuId = $(this).val();
			projectBuId.push($(this).val());
		});
		
		$(".allocatEndDate").each(function(){
			allocatEndDate.push($(this).val());
		});
		
		for(var i=0;i<employeeBuId.length;i++){
			if(allocatEndDate[i] == null || allocatEndDate[i] == "" ){
				if(projectBuId[i]!=employeeBuId[i]){
					highlightRowTipFlag++;		
					$("#"+rowId[i]).closest("tr").addClass("redColor");
					
				}
			}else{
				if(new Date(allocatEndDate[i]) > new Date() ){
					if(projectBuId[i] != employeeBuId[i]){
						highlightRowTipFlag++;		
						$("#"+rowId[i]).closest("tr").addClass("redColor");
					}
				}else{
					// $(".edit").eq(i).addClass("removeInactive"); //commented by neha for edit enable functionality for all  wrt timesheet
					//$(".edit").eq(i).click(function () {return false;}); //commented by neha for edit enable functionality for all  wrt timesheet
				}
			}
			
			
		   
		}
		
		
		if(highlightRowTipFlag != 0 ){
			$("#highlightRowTip").show();
		}else{
			$("#highlightRowTip").hide();
		}
			
}

function refreshGrid(){
	$.getJSON("resourceallocations", function(data){
		
	});
}

/* ------------------ Allocation for Active or All-----------------------  */
function checkAll(calledBy){
	
	// Added for task # 260 - start
 	saveOpen=false; 
	// Added for task # 260 - end
	var id=$("#employeeIdHidden").val();
	var action=document.getElementById("activeOrAll").value;
	if(action=="Active" && calledBy =="copy"){
		$.getJSON("resourceallocations",{find:"ByActiveTypeResourceEmployeeId" , employeeId:id}, showResourceAllocation).error(function() { showError("Some of the copied Resource already have Primary Project");});

	}
	else
	if(action=="Active"){
		$.getJSON("resourceallocations",{find:"ByActiveTypeResourceEmployeeId" , employeeId:id}, showResourceAllocation);

	}
	else{
		$.getJSON("resourceallocations",{find:"ByResourceEmployeeId" , employeeId:id}, showResourceAllocation);
	}


}



</script>

<script>
var eligibleResToCopy;
var oTable;
$(document).on('click','#h1 a.employeeDetails', function (e) {
	  $.ajax({
		 // type: 'POST',
          url: 'resourceallocations/readonlyEmployeeDetails',
          data: {employeeid:employeeid}  ,
          contentType: "text/html",
          success: function(data) { 
          }
       });   
});

$(".employeeDetails").fancybox({
		fitToView	: true,
		autoSize	: true,
		closeClick	: true,
		type : 'ajax'
	});
	
var divValue;
var divId1;
$(".fancyboxForlst").fancybox({
	 beforeShow: function(){
		 divId1=$(this).attr('href').replace('#', '');
		 var value=$("#"+divId1).text();
	     divValue=value;
	     $("#"+divId1).html(value);
	   },
	   afterClose: function(){
		   $("#"+divId1).text(divValue);
	   } 
	});

function openTimesheetApproval(){ 
 
	var yashEmpId =document.getElementById("yashEmpIdHidden").value; 
 	var empId = employeeid; //document.getElementById("employeeId").value; 
 	var _href = $('#navigationToTimeHrs').attr('href');
	var res = _href.substring(0, 9);
	var requestParam = "?empId="+empId+"&"+"yashEmpId="+yashEmpId;
	$('#navigationToTimeHrs').attr("href", res+requestParam);
	
}
</script>

<div id="resourceAllocationYashRMS" class="content-wrapper">
	<!--right section-->
	<div class="botMargin">
		<h1 id="h1">
			Resource Allocation
			<spring:url
				value="/resourceallocations/readonlyEmployeeDetails/${yashempname}"
				var="employeeDetails" />


		</h1>
	</div> 
	<div class="breadcrums">
		<a class="breadcrumbslink" href="#">RAM</a> <img
			src="resources/images/imgBreadCrumsArrow.gif" width="10" height="11" />
		<strong>Page Name</strong>
	</div>
	<div class="tab_seaction">
		<ul class='tabs'>
			<li><a href='#tab1' id="tab1">List</a></li>
			<li><a class="MaintenanceTab" id="MaintenanceTabInactive"
				href='#tab2' class="employeeDetails"> <span
					id="selectedEmployeeName"></span>
			</a></li>
			<li><span style="color: #0029FF;">* If user filled his
					time sheet on some date then End date calendar will display it as a
					disable.</span></li>
		</ul>

		<div id='tab1' class="tab_div maintainancetab_div">

			<div class="tbl">
				<input type="hidden" id="yashEmpIdHidden" name="yashEmpIdHidden">
				<input type="hidden" id="employeeIdHidden" name="employeeIdHidden">
				<input type="hidden" id="employeeNameHidden"
					name="employeeNameHidden">
				<script>
					displayTableData();
					</script>
					
			<div id="resourceAllocationTbl">
				<table id="resourceallocationTableId" class="dataTbl display tablesorter dataTable addNewRow alignCenter white-sort-icons my_table positiondashboard-table tableAlignment"
					style="max-width: 1290px !important;">

					<div id="xyz"
						style="position: absolute; left: 145px; z-index: 9; top: 0px;">
						<span class="font-bold-style-yash">&nbsp; Status</span> <select class="smallSelectBoxStyle"
							id="listActiveOrAll">
							<option value="Active" selected="selected">Active</option>
							<option value="All">All</option>


						</select> </span>
						<!-- <input type="Submit" value="Search"  class="projectSearch"></input> -->
					</div>
					<thead>
						<tr>
							<th width="4%" align="center" valign="middle" hidden="true">Emp
								ID</th>
							<th width="161px" align="center" valign="middle">Employee ID</th>
							<th width="218px" align="center" valign="middle">Employee
								Name</th>
							<th width="188px" align="center" valign="middle">Designation</th>
							<th width="113px" align="center" valign="middle">Grade</th>
							<th width="188px" align="center" valign="middle">Current BG-BU</th>
							<th width="188px" align="center" valign="middle">Current Location</th>
							<th width="113px" align="center" valign="middle">Current Project</th>
							<!-- <th width="10%" align="center" valign="middle">Planned
								Capacity</th>
							<th width="10%" align="center" valign="middle">Actual
								Capacity</th> -->
							<!-- <th width="138px" align="center" valign="middle">Total Planned
								Hours</th>
							<th width="156px" align="center" valign="middle">Total
								Reported Hours</th>
							<th width="111px" align="center" valign="middle">Total Billed
								Hours</th> -->
						</tr>


						<tr class="">
							<td><input type="text" name="search_yashEmpID"
								value="Yash Employee ID" class="search_init" hidden="true" /></td>



							<td><input type="text" name="search_empID"
								value="Employee ID" class="search_init"
								placeholder="Employee ID" /></td>

							<td><input type="text" name="search_empName"
								value="Employee Name" class="search_init"
								placeholder="Employee Name" /></td>

							<td><input type="text" name="search_designation"
								value="Designation" class="search_init"
								placeholder="Designation" /></td>
								
															
							<td><input type="text" name="search_grade" value="Grade"
								class="search_init" placeholder="Grade" /></td>
								
							<td><input type="text" name="search_bgbu"
								value="Current-BGBU" class="search_init"
								placeholder="Current BGBU" /></td>
								
							<td><input type="text" name="search_baseloc"
								value="Current Location" class="search_init"
								placeholder="Location" /></td>

							<td><input type="text" name="search_currentProject"
								value="Current Project" class="search_init"
								placeholder="Current Project" /></td>


							<!-- <td><input type="text" name="search_totalPlanned" value="Total Planned Hours"
								class="search_init" disabled="disabled"/></td>
								<td><input type="text" name="search_totalReported" value="Total Reported Hours"
								class="search_init" disabled="disabled"/></td>
								<td><input type="text" name="search_totalBilled" value="Total Billed Hours"
								class="search_init" disabled="disabled"/></td> -->


						</tr>
					</thead>
					<tbody>


						<%-- <c:forEach var="resource" items="${resources}">
							<tr id="${resource.employeeId}">
								<td align="center" valign="middle"><a href="#"
									onclick="openMaintainance(${resource.employeeId},'${resource.yashEmpId}');">${resource.yashEmpId}</a></td>
								<td align="center" valign="middle">${resource.employeeName}</td>
								<td align="center" valign="middle">${resource.designation_name}</td>
								<td align="center" valign="middle">${resource.grade}</td>
								<td align="center" valign="middle">${resource.plannedCapacity}</td>
								<td align="center" valign="middle">${resource.actualCapacity}</td>
								<td align="center" valign="middle">${resource.totalPlannedHrs}</td>
								<td align="center" valign="middle">${resource.totalReportedHrs}</td>
								<td align="center" valign="middle">${resource.totalBilledHrs}</td>
							</tr>
							<input type="hidden" value="${resource.yashEmpId}" id="yashEmpId"/>
						</c:forEach> --%>
					</tbody>
				</table>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<div id='tab2' class="tab_div">
			<div class="search_filter">
				<div class="btnIcon1 resourceAllocationBtnIcon">
					<span> <sec:authorize
							access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
							<a href="javascript:void(0);" class="blue_link blueBtnYash" id="addNew">
								+ Add New
							</a>
							<input type="hidden" id="dateofjoiningvalidate" />
						</sec:authorize> <input type="hidden" id="dateofjoiningvalidate" />
					</span> <span class="ResAllocStatusdrpdwn">&nbsp; Status
						&nbsp;<select class="smallSelectBoxStyle" onchange="checkAll('')" id="activeOrAll">

							<option selected="selected" value="Active">Active</option>
							<option value="All">All</option>

					</select>

					</span>

				</div>
			</div>
			<div class="tbl" id="allocation_table">

				<table id="example"
					class="dataTbl display tablesorter addNewRow maintainanceTblData">
					<thead>
						<tr>
							<th width="7%">Feedback Remarks</th>
							<th width="7%">Employee ID</th>
							<th width="7%">Allocation Sequence</th>
							<th width="7%">Allocation Type</th>
							<th width="7%">Allocation Percentage</th>
							<th width="4%">Start Date</th>
							<th width="4%">End Date</th>
							<th width="7%">Project Name</th>
							<th width="7%">Allocation Remarks</th>
							<th width="9%">Primary Project</th>
							<th width="9%">Resource Type</th>

							<!--<th width="5%">Billable</th>
							 <th width="7%">Weekly Allocation Hours</th> -->
							<!-- <th width="5%">Rate ID</th> -->
							<!-- <th width="15%">Team Name</th> -->
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
								<th width="4%">Edit</th>
							</sec:authorize>
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN')">
								<th width="4%">Delete</th>
							</sec:authorize>
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
								<th width="9%">Copy Allocation</th>
							</sec:authorize>
						</tr>
					</thead>
					<tbody id="example">
					</tbody>
					<span class="label label-info resource_alloc_note_msg"><span
						style="color: #FF0000 !important;">*</span> Calendar Dates
						Enables when<br> # On Start Date- Joining date is less than
						or equal to current date.</span>
					<a href="timehrses" onclick="openTimesheetApproval()"
						id="navigationToTimeHrs" class="display-inline-block"></a>
				</table>



			</div>
			<div class="clear"></div>
			<div id="highlightRowTip" class="highlightRowTip"
				style="background-color: #5CB3FF; display: inline; font-style: bold;">Tip:
				Highlighted resource does not belong to Project BU. (i.e. Resource's
				Current BU is not equal to Project BU).</div>
		</div>
	</div>
	<!--right section-->
</div>

<!-- we have remove (Project Allocation End Feedback) popup -->
<!-- <div id="commentBox" align="center"
	style="display: none; border: 2px solid;">
	<div class="">
		<table>
			<tr>
				<td align="center" colspan="2"><strong>Project
						Allocation End Feedback:</strong></td>

			</tr>
			<tr>
				<td align="center" colspan="2"><textarea rows="10" cols="60"
						id="feedbackText" maxlength="500"></textarea></td>
			</tr>
			<tr>
				<td colspan="4" align="left"><input type="button" value="Ok"
					id="okbtn"></td>
				<td colspan="4" align="right"><input type="button"
					value="Cancel" id="cancelbtn"></td>
			</tr>
		</table>
	</div>
</div> -->
<div id="dialog" class=""
	style="background: white; width: 800px; height: 300px;">
	<input type="hidden" name="copyRowId" id="copyRowId" /> <input
		type="hidden" name="copyResourceId" id="copyResourceId" /> <input
		type="hidden" name="selectProjectId" id="selectProjectId" />
	<div id="dialogText">
		 

	</div>

	<div id="copyResource">
		<table width="99%" border="0" align="center" cellpadding="1"
			cellspacing="1">
			<tbody>
				<!-- <h3>Search Resource</h3><input style="height: 25px;width: 187px" id="searchEmp" type="text"></input><button style="margin-left: 15px; margin-top: -3px" onclick="searchEmployee()">Search</button>
				<p style="margin: 10px;color: #000;">Associates whose joining dates are equal to or greater then allocation start date are eligible for search/allocation OR Resource is already allocated to Project</p> -->
				<tr>
					<td width="50%" valign="top"><table width="100%" border="0"
							cellpadding="2" cellspacing="1">
							<tbody>
								<tr>
									<td width="58%" valign="top">
										<div class="defaultActivityWrapper">
											<table border="0" align="left" cellpadding="2"
												cellspacing="1" class="borderlightgray">
												<tbody>
													<h3>Search Resource</h3>
													<input style="height: 25px; width: 187px" id="searchEmp"
														type="text"></input>
													<button style="margin-left: 10px; margin-top: -3px"
														onclick="searchEmployee()">Search</button>
													&nbsp;
													<p class="label label-info res_alloc_resourcetocopy_msg-yash-rms">

														# Associates whose joining dates are equal to or lesser
														than allocation start date are eligible for<br>
														&nbsp;&nbsp;search/allocation. <br> # Resources
														already allocated to project will not be eligible for
														search.
														<!-- OR Resource is already allocated to Project. -->
													</p>
													<tr align="center" valign="middle">

														<td align="left" valign="top"
															class="ent_elevenred defaultActivityHeading">Resources</td>
														<td align="left" valign="top" class="text">&nbsp;</td>
														<td align="left" valign="top"
															class="ent_elevenred defaultActivityHeading">Selected
															Resources</td>
														<td align="left" valign="top" class="text">&nbsp;</td>
													</tr>
													<tr align="center" valign="middle">
														<td valign="top" class="text"><span
															id="selectionView"> <select name="pdo" size="7"
																multiple="" class="textField textFieldWidth" id="pdo"
																style="width: 150"></select>
														</span></td>
														<td class="text" style="padding:0 15px;"><table width="60%" border="0"
																cellpadding="2" cellspacing="7">
																<tbody>
																	<tr>
																		<td align="center" valign="middle"><input
																			name="btn_Exit" type="button" class="ent_button"
																			id="btn_Exit" value=">>"
																			onclick="move_item(pdo, listMenu)"></td>
													 				</tr>
																	<tr>
																		<td align="center" valign="middle"><input
																			name="btn_Exit" type="button" class="ent_button"
																			id="btn_Exit" value="<<" onclick="move_item(listMenu,pdo)"></td>
																	</tr>
																</tbody>
															</table></td>
														<td class="text"><table cellpadding="0"
																cellspacing="0" width="100%">
																<tbody>
																	<tr>
																		<td width="72%" valign="top"><span
																			id="selectionView"> <select name="listMenu"
																				size="7" multiple=""
																				class="textField textFieldWidth" id="listMenu"
																				style="width: 150"></select>
																		</span></td>
																	</tr>
																</tbody>
															</table></td>

													</tr>
												</tbody>
											</table>
										</div>
									</td>
								</tr>
							</tbody>
						</table></td>
				</tr>
				<tr></tr>
				<tr></tr>
			</tbody>
		</table>
		<%--  <c:if test="${not empty eligibleResources}">

			<select name="multiresource" id="multiresource" class="multiSelectDd"
				multiple="multiple">
				<c:forEach var="eligibleResources" items="${eligibleResources}">
					<option value="${eligibleResources.employeeId}">
						${eligibleResources.firstName} ${eligibleResources.lastName}[${eligibleResources.yashEmpId}]</option>
				</c:forEach>
			</select>
		</c:if> --%>
	</div>
</div>


<!--START: Alert: Added by Pratyoosh Tripathi -->
<div class="notification-bar">
	<!-- <div class="close closeIconPosition">close</div> -->
	<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
	<!-- <div class="notification-text">Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.</div> -->
	<sec:authorize
		access="hasAnyRole('ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
		<div class="notification-text">You can add or allocate a project
			to any resource clicking on its employee id.</div>

	</sec:authorize>
	<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_HR','ROLE_ADMIN')">
		<div class="notification-text">You can add or allocate a project
			to any resource clicking on its employee id.</div>
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
	
	$('[data-toggle="tooltip"]').tooltip();
	
	setInterval(function(){ $('[data-toggle="tooltip"]').fadeToggle(500); }, 1000);
	
	<%Boolean flag = false;
			flag = (Boolean) session.getAttribute("notificationbarflag");%>
	
	
$('.close').click(function(){
    $('.notification-bar').hide();
   });
});
</script>

<!--END: Alert: Added by Pratyoosh Tripathi -->



<script type="text/javascript">
function move_item(from, to)
{
  var f;
  var SI; 
  //alert(from.value);
  if(from.options.length>0)
  {
    for(i=0;i<from.length;i++)
    {
      if(from.options[i].selected)
      {
        SI=from.selectedIndex;
        f=from.options[SI].index;
        to.options[to.length]=new Option(from.options[SI].text,from.options[SI].value);
        from.options[f]=null;
        i--; 
      }
    }
  }
}

/* function checkForDuplicates(succesResponse,selectedResourcesIdArray) {
	alreadyAssignedEmployeeIds=	succesResponse.replace(/,(\s+)?$/, '');
		var assignedIDS = alreadyAssignedEmployeeIds.split(',');
		for(var i=0;i<selectedResourcesIdArray.length;i++){
			for(var j=0; j<assignedIDS.length;j++){
				if(selectedResourcesIdArray[i]==assignedIDS[j]){
					var resourceId= selectedResourcesIdArray[i];
					
					$.ajax({
							type: 'POST',
					        url: 'resources/getResource/'+resourceId,
					     	success: function(resourceName) { 
					     		showError(resourceName+ " is already allocated");
					     	}
						 });
				}
			}
		}
} */


    $(document).ready(function () {
        $("#dialog").dialog({ 
			autoOpen: false,
			modal: true,
			title: 'Resource To Copy',
			closeText: "hide",
			height: 350, 
			width: 910,
			dialogClass: "alert",
			resizable: true
		});
		
		$("#dialog" ).dialog( "option", "buttons", [ { text: "Allocate", click: function() {  
        
       // startProgress();
       		
		var id=document.getElementById('copyRowId').value;
		var employeeId=document.getElementById('copyResourceId').value;
		var selectedprojectId=document.getElementById('selectProjectId').value;
		var multiObjs =  document.getElementById('listMenu').options;
		var selectedResourceIds="";
		var stringValue = "";
		var selectedResourcesIdArray = new Array();
		for(var i=0; i<multiObjs.length; i++ ) {
			selectedResourceIds=selectedResourceIds+ multiObjs[i].value+","; 
	    }
		selectedResourceIds=selectedResourceIds.replace(/,(\s+)?$/, '');  
		/**Start - Added check to fix Bug #204 */
		if(null == selectedResourceIds || selectedResourceIds == ""){
			var errorMsg ="Please select Resources to copy the Allocation"; 
			showError(errorMsg);
			stopProgress();
			return;
		} 
		var alreadyAssignedEmployeeIds = "";
		$(".abc").each(function(e){
			alreadyAssignedEmployeeIds = alreadyAssignedEmployeeIds + ", "+$(this).val();
		});
		
			startProgress();
		 var selectedResourcesIdArray = selectedResourceIds.split(',');
		 
		 //flag var is used to check if no allocation is there then not to open allocation frame
		 var flag;
		 $.ajax({
				type: 'GET',
			    url: "projectallocations/checkForExistingOpenAllocations/"+selectedResourceIds,
			    contentType: "application/json; charset=utf-8",
			    async:false,
			    success: function(data) {
			    	
			    	var resultData = JSON.parse(data);
			    
			    	if(resultData.openflag=="true"){
			    		flag = true;
			    	}else{
			    		flag = false;
			    	}
			     stopProgress();
			    	
			    },
			    error : function(data){
			    	stopProgress();
			    	showError("Error");
		     		flag = false;
			    }
	   });
		
	 if(flag){
		 $.fancybox({ 
		       //fitToView	: false,
									autoSize : false,
									closeClick : false,
									//autoScale   : true,
									autoDimensions : true,
									transitionIn : 'fade',
									transitionOut : 'fade',
									openEffect : 'easingIn',
									closeEffect : 'easingOut',
									type : 'iframe',
									href : '${myProfile}'+selectedResourcesIdArray,
									'width' : '100%',
									preload : true,
									beforeShow : function() {
										var thisH = this.height - 35;
										$(".fancybox-iframe").contents().find(
												'html').find(".midSection")
												.css('height', thisH);
									},
									helpers   : { 
										   overlay : {closeClick: false} // prevents closing when clicking OUTSIDE fancybox 
										  },
										  afterClose: function() {
											  $.ajax({
												type: 'POST',
										        url: 'resourceallocations/getAllocs/'+selectedprojectId,
										     	success: function(succesResponse) { 
										     		
										     		alreadyAssignedEmployeeIds=	succesResponse.replace(/,(\s+)?$/, '');
										    		var assignedIDS = alreadyAssignedEmployeeIds.split(',');
										    		
										    		for(var i=0;i<selectedResourcesIdArray.length;i++){
										    			for(var j=0; j<assignedIDS.length;j++){
										    				if(selectedResourcesIdArray[i]==assignedIDS[j]){
										    					var resourceId= selectedResourcesIdArray[i];
										    					var index = selectedResourcesIdArray.indexOf(resourceId);
										    					
										    						selectedResourcesIdArray.splice( index, 1 );
										    					
										    					$.ajax({
										    							type: 'POST',
										    					        url: 'resources/getResource/'+resourceId,
										    					     	success: function(resourceName) { 
										    					     		showError(resourceName+ " is already allocated");
										    					     	}
										    						 });
										    				}
										    			}
										    		}
										    		selectedResourceIds=selectedResourcesIdArray.toString();
										    		
										    		
										    		startProgress();
										   		 $.ajax({
										   											type: 'POST',
										   									        url: 'resourceallocations/'+id+'/'+selectedResourceIds,
										   									     	contentType: "text/html",
										   									     	success: function(succeResponse) { 
										   									     		
										   									     		
										   									     		$('#multiresource').multiselect('uncheckAll');
										   									 
										   	                                    checkAll('copy');

										   									     		stopProgress();
										   									     		showSuccess("Allocated successfully.");
										   									     	$('#listMenu').find('option').remove();
										   									     	},
										   									     	error: function(errorResponse)
										   									 	    {
										   									 	    	
										   									 	    }
										   									 });
										    		
										    		
										    		
										     		
										     		
										     		stopProgress();
										     	},
										     	error: function(errorResponse)
										 	    {
										 	    	showError("Error");
										 	    }
										 });   
											  }
											  
												/* saveOpen = false;
												addNewAlloc=false;*/
											 
											
		    });
		
	  }else{
		  $.ajax({
					type: 'POST',
			        url: 'resourceallocations/'+id+'/'+selectedResourceIds,
			     	contentType: "text/html",
			     	success: function(succeResponse) { 
			     		
			     		
			     		$('#multiresource').multiselect('uncheckAll');
			 
             checkAll('copy');

			     		stopProgress();
			     		// showSuccess("Allocated successfully.");
			     	$('#listMenu').find('option').remove();
			     	},
			     	error: function(errorResponse)
			 	    {
			 	       showError("Error");
			 	    }
			 });
	  }
		
		 $( this ).dialog( "close" );
		 
		 
		$( ".MaintenanceTab" ).hide();
		/* $( "#selectedEmployeeName" ).hide(); */
		/**End - Added Rcheck to fix Bug #204 */
		
		multiObjs='';
		selectedResourceIds='';
        
        } } ] );

        // $("#dialog").dialog({
		// 	open: function( ) {
		// 		//vm.getMultiSelectResource();
		// 	},
    	// });
    });
    function searchEmployee(){
   	 
  	  var searchEmp=$('#searchEmp').val();
  	  $('#pdo').find('option').remove();
  	  if(searchEmp==""){
  		  var option = '';
    		 $.each(eligibleResToCopy, function (i, n) {
    		    option = '<option value="' + n[0] + '">' + n[1]+" "+n[2] +" ["+n[3]+"]" + '</option>';			     		
    		$('#pdo').append(option);
    		});
  	  }else{
  	  $.each(eligibleResToCopy, function (i, n) {
  		  var empName=n[1]+" "+n[2]+" "+n[3];
  		  	if (empName.toLowerCase().indexOf(searchEmp.toLowerCase()) >= 0){
  		  		var option = '';
  		    option = '<option value="' + n[0] + '">' + n[1]+" "+n[2] +" ["+n[3] +"]"+  '</option>';
  		  	}
  		$('#pdo').append(option);
  		});
  	  }
  	  
  	  $("#listMenu option").each(function()
  			  {
  		  var val= $(this).val();
  		  $("#pdo option[value="+val+"]").remove();
  			  });
     }
  
    
 /* function saveResourceReleaseDetails(empId,projectId,allocationId,startDate,endDate,joiningDate){		
		if(!endDate){	
			endDate="-";
		}		
	$.fancybox.open({
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
		href : '${resourceDetailsForReleaseSummary}'+empId+"/projects/"+projectId+"/allocationId/"+allocationId+"/joiningDate/"+joiningDate+"/startDates/"+startDate+"/endDates/"+endDate,			
		'width' : '100%',
		'height' : '563px',
		preload : true,
		beforeShow : function() {
			var thisH = this.height - 35;
			$(".fancybox-iframe").contents().find('html').find(".midSection").css('height', thisH);		
		
		},
		helpers : {
			overlay : {
				closeClick : true
			}
		// prevents closing when clicking OUTSIDE fancybox 
		},
	/* 	afterClose : function() {
			var href = window.location.pathname;
			if (href.toLowerCase().indexOf("admindashboard") >= 0 || href.toLowerCase().indexOf("userdashboard") >= 0) {
				window.location.reload();
			}
		} 

	});
		} */
		
</script>
<!--START: Alert: Added by Pratyoosh Tripathi -->
<div class="notification-bar">
	<!-- <div class="close closeIconPosition">close</div> -->
	<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
	<!-- <div class="notification-text">Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.</div> -->
	<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_HR')">
		<div class="notification-text">You can add or allocate a project
			to a resource clicking on its employee id.</div>


	</sec:authorize>
	<sec:authorize access="hasAnyRole('ROLE_MANAGER')">
		<div class="notification-text">You can only view the project
			details of the resources clicking on their Employee Id but cannot
			allocate them.</div>


	</sec:authorize>
	<sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER')">
		<div class="notification-text">You can add or view the project
			details of the resources clicking on their Employee Id.</div>
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
	
	$('[data-toggle="tooltip"]').tooltip();
	
	setInterval(function(){ $('[data-toggle="tooltip"]').fadeToggle(500); }, 1000);
	
	<%Boolean flag1 = false;
			flag = (Boolean) session.getAttribute("notificationbarflag");%>
	
	
$('.close').click(function(){
    $('.notification-bar').hide();
   });
});
function onAllocationTypeChange(e) {
	var val = e.target.value;
	if (val === '2') {
		$('#allocPercentage').val('100');
	} else if (val === '3') {
		$('#allocPercentage').val('50');
	} else{
	    $('#allocPercentage').val('0');
	}
}

function onAllocationPercentageChange(){
	var allocPercentage = $('#allocPercentage').val().trim();
	var numbers = /^[0-9]+$/;
	if (!allocPercentage.match(numbers) && allocPercentage){
		showError("\u2022 Allocation Percentage should be digit only.");
		return;
	} 
	if (!allocPercentage) {
		allocPercentage = "0";
	} else if (allocPercentage > 100){
		showError("\u2022 Allocation Percentage should be <br /> between (0-100).");
		return;
	}
	return true;
}

</script>

<!--END: Alert: Added by Pratyoosh Tripathi -->

<Script Language="JavaScript"> 
var objappVersion = navigator.appVersion; 
var objAgent = navigator.userAgent; 
var objbrowserName = navigator.appName; 
var objfullVersion = ''+parseFloat(navigator.appVersion); 
var objBrMajorVersion = parseInt(navigator.appVersion,10); 
var objOffsetName,objOffsetVersion,ix;
 </script>
 
 <!-- Current Date in yyyy-mm-dd format -->
 <script>
 var today = new Date();
 var dd = today.getDate();
 var mm = today.getMonth() + 1;
 var yyyy = today.getFullYear();

 if (dd < 10) {
   dd = '0' + dd;
 }

 if (mm < 10) {
   mm = '0' + mm;
 }

 today = yyyy + '-' + mm + '-' + dd;
 </script>
