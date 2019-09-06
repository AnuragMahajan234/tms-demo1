<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<spring:url value="/projectallocations/findOpenAllocationsOfResource/"
	var="myProfile" />
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<spring:url
	value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url
	value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />
<spring:url value="resources/js-framework/date.js?ver=${app_js_ver}"
	var="jquery_date_js" />
<spring:url
	value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}"
	var="multiselect_js" />

<spring:url
	value="/projectallocations/findOpenAllocationsOfCopiedResource/"
	var="copiedResourceAllocationList" />

<script src="${multiselect_js}" type="text/javascript"></script>

<spring:url
	value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}"
	var="multiselect_filter_js" />

<!-- //TODO : will be added after 9th August 2017 deployment. -->
<%-- <spring:url value="/projectallocations/findResourceDetailsForReleaseSummary/employees/" var="resourceDetailsForReleaseSummary" /> --%>

<script src="${multiselect_filter_js}" type="text/javascript"></script>
<script src="resources/js-user/rmsCommon.js" type="text/javascript"></script>


<!-- Code for resource selection while add select2 plugin - Start -->
<spring:url value="/resources/css/select2.min.css?ver=${app_js_ver}"
	var="select2_css" />
<link href="${select2_css}" rel="stylesheet" type="text/css"></link>

<spring:url value="/resources/js/select2.min.js?ver=${app_js_ver}"
	var="select2_js" />
<script src="${select2_js}" type="text/javascript"></script>
<!-- Code for resource selection while add select2 plugin - End -->
 

<script type="text/javascript" charset="utf-8">
var global = null;
var addNewAlloc = false;
var editAlloc = false;
var projectN = "";
var projectId = "";
var projectAllocationTable;
var dateOfJoin;
var dateOfProjectEnd;
var changeAllocationFlag = false;
var divValue;
var divId1;
var empIdGlobal="";
var projectIdGlobal="";
var allocStartDateGlobal="";
var allocEndDateGlobal="";
var dateOfJoiningGlobal="";
var allocationIdGlobal="";

function promptForAllocationHrs(allocationHrsVar) {
	if($(allocationHrsVar).val() > 40) {
		var answer=confirm("Are You sure, you  are allocating more than 40 hours as weekly hours?");
		if(answer) {
			
		}else {
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
			// Added for task # 216 - Start
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

$(".projectDetails").fancybox({ 
	fitToView	: true,
	autoSize	: true,
	closeClick  : false,
	 maxWidth: 647,
	 helpers     : { 
	        overlay : {closeClick: false} // prevents closing when clicking OUTSIDE fancybox
	    },
	type : 'ajax'
}); 

</script>
<!-- Delete changes done by Neha -start -->
<script id="projectAllocationRows" type="text/x-jquery-tmpl"
	charset="utf-8">
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
                         <a href="projectallocations/readonlyProjectDetails/{{>employeeId.employeeId}}" class="projectDetails" >
								{{>employeeId.yashEmpId}}
					    </sec:authorize>
							</td>
			                <td align="center"> 
									{{if employeeId+}}
										{{>employeeId.employeeName}}    
										<input type="hidden" id="employeeId.employeeId" value="{{>employeeId.employeeId}}" class="abc"/>
										<input type="hidden" class="empID" id="employeeBuId{{:#index}}" value="{{>employeeId.currentBuId.id}}"/>
										<input type="hidden" class="prjID" id="projectBuId{{:#index}}" value="{{>projectId.orgHierarchy.id }}"/>
										
									{{/if}}
								<input id="projectId" type="hidden" value="{{>projectId.id }}">
								<input id="id" type="hidden" value="{{>id }}"/>
								{{if allocatedBy}}					
								<input id="allocatedBy.employeeId" type="hidden" value="{{>allocatedBy.employeeId }}"/>
								{{else}}
                                    <input id="allocatedBy" type="hidden"  value=""/>
								{{/if}}
									<input type="hidden" id="lastActivityDate{{>id }}" value="{{>lastUserActivityDate}}"/>
									<input type="hidden" id="firstActivityDate{{>id }}" value="{{>firstUserActivityDate}}"/>
									<input type="hidden" id="isReleasedIndicator{{>employeeId.yashEmpId }}" value="{{>employeeId.isReleasedIndicator}}"/>
									<input type="hidden"  id="dateOfJoining" value="{{>employeeId.dateOfJoining }}"/>
									<input type="hidden"  id="kickOff" value="{{>projectId.projectKickOff }}"/>
         							<input type="hidden"  id="endDate" value="{{>projectId.projectEndDate }}"/>
									<input  type="hidden" id="resourceType" value="{{>resourceType}}"/>
								
			                </td>
			                <td align="center">                             
								{{>allocationTypeId.allocationType}}
			                	<input type="hidden" id="allocationTypeId" value="{{>allocationTypeId.id }}"/>
			                	<input type="hidden" class="allocatEndDate" value="{{>allocEndDate}}"/>
							</td>
							
							<td align="center">                             
								{{>allocPercentage}}
							</td>

                           <td align="center">
                            {{if ownershipId}}
                             {{>ownershipId.ownershipName}}
                             <input type="hidden" id="ownershipId" value="{{>ownershipId.id}}"/>
                            {{/if}}
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
								{{if behalfManager}}
									Yes
								  {{else}}
									  No
								{{/if}}
							</td>
									
						
 					<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
						{{if employeeId.isReleasedIndicator}}
							 <td align="center" >
								<a class="addNew" id="hiddenAnchor" onclick="setGlobalVariable({{>employeeId.employeeId}}, {{>projectId.id }},{{>id }}, '{{>allocStartDate}}','{{>allocEndDate}}','{{>employeeId.dateOfJoining}}');" style="display:none;"></a>
								<a class="edit" id="editRow{{:#index}}" onclick="setGlobalVariable({{>employeeId.employeeId}}, {{>projectId.id }},{{>id }}, '{{>allocStartDate}}','{{>allocEndDate}}','{{>employeeId.dateOfJoining}}');" class="removeInactive" href="">Edit</a>
				 	</td>
					</sec:authorize>
					<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
 							{{if lastUserActivityDate !=null}}
   							<td align="center" class="removeInactive" title="Can't delete as timesheet is filled for this Project." >
									Delete
							</td>
  							{{else}}
  								 <td align="center" >
                                 <a class="delete" href="" onclick="deleteDataRow({{>id}},{{>employeeId.employeeId }},{{>projectId.id }});">
									Delete</a>
								</td>
							{{/if}}
						</sec:authorize>
						<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">	
							<td>
								&nbsp;
							<a  class="copyData" href="javascript:void(0);" onclick="copyDataRow({{>id}},{{:#index}},{{>employeeId.employeeId}},'{{>allocStartDate}}');">Copy</a>
							</td>
							<td><img src="resources/images/change.png" onclick="changeAllocation(event, {{>id}},{{:#index}},{{>employeeId.employeeId}},'{{>allocStartDate}}','{{>employeeId.yashEmpId}}','{{>employeeId.employeeName}}', '{{>allocationTypeId.allocationType}}',{{>ownershipId.id}},'{{>ownershipId.ownershipName}}', '{{>allocRemarks+}}');" />
								<!-- <input class="fromdatepicker-change-alloc" style="background: url('resources/images/change.png') left center no-repeat transparent; width:0px;border: none;outline:none;color: transparent;box-shadow: none;" readonly="readonly"/> -->
							</td>
 						{{else}}
							 <td align="center" ></td>
							 <td align="center" ></td>
							 <td align="center" ></td>
						{{/if}}
					</sec:authorize>						  
			            </tr>
</script>

<!-- Delete changes done by Neha -end -->
<script type="text/javascript" charset="utf-8">
var abc;
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
            success: function (json) { 
						if (json != null && json != "") {
                            callback1(json);
                        } else {
	                        // Added for task # 216 - Start
	        				var text="Error Occured - Nothing returned from server";
	        				showAlert(text);
	        				// Added for task # 216 - End
						}
           			},
            error:  function (XMLHttpRequest, textStatus, errorThrown)  { 
                     }
        });   
    } catch (err) {
       	txt="There was an error on this page.\n\n";
       	txt+="Error description: " + err.description + "\n\n";
       	txt+="Click OK to continue.\n\n";
    }
}  

		var lastactivityDate;
		var firstActivityDate;
		var saveOpen = false;
		var isExistProjectAlloc=true;
		var lastRowSelectedForAllocationChange = null;
		function getInputValue(str){
			
			var index1 = str.indexOf("<INPUT");
			
			// for mozilla
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
		 	        	  $.noty.closeAll();
 	        	 		//****End*****//
		 	           if (id == undefined || id == null || id == '') {
		 	            	oTable.fnDeleteRow( global );
		 	            	saveOpen = false;
		 	            	nEditing = null;
		 	            	$('.toasterBgDiv').remove();
		 	            	return;
		 	            }
		 	          $('.toasterBgDiv').remove();
			 	       $.ajax({
							type: 'POST',
					        url: 'projectallocations/approveDetails/'+id+"/"+empId+"/"+projId,
					     	contentType: "application/json; charset=utf-8",
					     	async:false,
					     	success: function(data) { 
					     		
					     			if(data.check==false){
						     			var warningMsg = " some disapprove data is present which also be deleted.";
										 var conf=confirm(warningMsg);
									 if(conf){
										 $.ajax({
												type: 'DELETE',
										        url: 'projectallocations/delete/'+id,
										     	contentType: "text/html",
										     	async:false,
										     	success: function(succeResponse) { 
										     		showSuccess("Project Allocation has been successfully deleted.");
										     		var id = document.getElementById("projectIdHidden").value;
										     	saveOpen = false;
										     	editAlloc=false;
												nEditing= null;
										     		checkAll('');
										     	},
										     	error: function(errorResponse)
										 	    {
										 	    	showError("Project Allocation cannot be  deleted.");
										 	    }
										 });
										     	
							     			stopProgress();
											
			                    }else{
			                    	stopProgress();
			                    }
			                    }
					     			else{
					     				
					     				$.ajax({
											type: 'DELETE',
									        url: 'projectallocations/delete/'+id,
									     	contentType: "text/html",
									     	async:false,
									     	success: function(succeResponse) { 
									     		showSuccess("Project Allocation has been successfully deleted.");
									     		var id = document.getElementById("projectIdHidden").value;
									     		saveOpen = false;
												nEditing= null;
												editAlloc=false;
												getProjectAllocationById(id);
									     	},
									     	error: function(errorResponse)
									 	    {
									 	    	showError("Project Allocation cannot be  deleted.");
									 	    }
									 });	
					     			
							     			stopProgress();
											
					     			}
					    	}
						});
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
		}
		
		var monthNames = ["Jan", "Feb", "Mar",
		                  "Apr", "May", "Jun", "Jul",
		                  "Aug", "Sep", "Oct",
		                  "Nov", "Dec"];
		
		function setGlobalVariable(employeeId,projectId,allocationId,allocStartDate,allocEndDate,dateOfJoining) {
			empIdGlobal=employeeId;	
			projectIdGlobal=projectId;
			allocStartDateGlobal=allocStartDate;
		    allocEndDateGlobal=allocEndDate;
		    dateOfJoiningGlobal=dateOfJoining;		
		    allocationIdGlobal=allocationId;		   
		}
		
		function copyDataRow(id,index,employeeId,alocationStartDate){
			document.getElementById('copyRowId').value=id;
			document.getElementById('copyResourceId').value=employeeId;
			document.getElementById('searchEmp').value='';
			
			if ((objOffsetVersion=objAgent.indexOf("Firefox"))!=-1) { 
				objbrowserName = "Firefox"; 
				
				var dateSplit = alocationStartDate.split("-");            
				dateObjStartDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);
				
				var month =  monthNames[dateObjStartDate.getMonth()];
				var day = dateObjStartDate.getDate();
				var year = dateObjStartDate.getFullYear();
				
				newStartDate  = day + "-" + month + "-" + year;
				
			}
			
			else if ((objOffsetVersion=objAgent.indexOf("Chrome"))!=-1) { 
				
				objbrowserName = "Chrome"; 
				objfullVersion = objAgent.substring(objOffsetVersion+7); 					
			
				var dateObjStartDate = new Date(alocationStartDate);
				
				var month = monthNames[dateObjStartDate.getMonth()];
				var day = dateObjStartDate.getDate();
				var year = dateObjStartDate.getFullYear();
				
				newStartDate =  day + "-" + month + "-" + year;
				
			}	
			
			else {
			
				var dateObjStartDate  = Date.parse(alocationStartDate);
				
				var month = monthNames[dateObjStartDate.getMonth()];
				var day = dateObjStartDate.getDate();
				var year = dateObjStartDate.getFullYear();
				
				newStartDate =  day + "-" + month + "-" + year;
				
			} 
			
			$("#dialog").dialog({
				  minWidth: 300
			}).dialog('open');
			
			$.ajax({
				type: 'POST',
		        url: 'resources/getEligibleResourcesForCopy/'+employeeId+'/'+projectId,
		        data: {"alocationStartDate":newStartDate},
		     	success: function(succesResponse) { 
		     		abc=JSON.parse(succesResponse);
		     		var option = '';
		     		 $.each(abc, function (i, n) {
		     		    option = '<option value="' + n[0] + '">' + n[1]+" "+n[2] +" ["+n[3] +"]"+ '</option>';			     		
		     		$('#pdo').append(option);
		     		});
		     		
		     	    return;
		     	}
		 });
		}
		/**changeAllocation */
		 function changeAllocation(e, id, index, empId, allocationStartdate, yashId, employeeName, currentAllocType, ownershipId, ownershipName, remarks, primaryProject, timeHrs) {
			//update selected row on global variable
			lastRowSelectedForAllocationChange = $(e.currentTarget).closest('tr');
			var projectName = document.getElementById("projectNameHidden").value;
			$('#allocationHeader').text('Change Allocation - ' + projectName);
			
			changeAllocationFlag=true;
			var clickedRowData = {
					event : e,
					currentAllocId:id,
					index:index,
					empId:empId,
					currentAllocStartdate:allocationStartdate,
					yashId:yashId,
					employeeName:employeeName,
					currentAllocType:currentAllocType,
					ownershipId:ownershipId,
					ownershipName:ownershipName,
					currentOwnershipName:ownershipName,
					remarks:remarks,
					primaryProject:primaryProject,
					timeHrs:timeHrs,
					originalEndDate:lastRowSelectedForAllocationChange.find('td').eq(6).html()
			};

			$.each(clickedRowData,function(key,val) {
				$('#allocationModal input[data-name='+ key +']').val(val);
				if (key === 'ownershipId') {
					$('#selectOwnership').val(val);
				}
			})
			$('#allocStartDate, #allocEndDate, #currentAllocStartdate, #currentAllocEnddate').datepicker({changeMonth: true,changeYear: true, dateFormat: "dd-M-yy"});
    
		    $('#currentAllocEnddate').datepicker('setDate', 'today'); 
			var nextDayDate = $('#currentAllocEnddate').datepicker('getDate', '+1d');
			nextDayDate.setDate(nextDayDate.getDate() + 1);
			$('#allocStartDate').datepicker('setDate', nextDayDate);  

			$('#allocationModal').modal('show');
			 setTimeout(function(){
				 $('#currentAllocEnddate').datepicker("show");
			}, 500)
			
		}
		 /***/
		 /*  addNewRowForChangeAllocation */
		 function addNewRowForChangeAllocation(){
			 var clickedRowData = null;
			 if(lastRowSelectedForAllocationChange){
				 clickedRowData = lastRowSelectedForAllocationChange.data();	
			 }else{
				 return;
			 }
			lastRowSelectedForAllocationChange =null;
			addNew(clickedRowData.event);
			var thisRow = $("#example tbody tr:first");
			// Hide comboboxes in this row
			thisRow.find('.ui-combobox', 'textarea').not('#allocTypeEditIdnull').each(function(i){
				// don't hide allocationtype dropDown
				if(i===0){
					return;
				}
				$(this).hide();
			});
			thisRow.find("td").each(function(){
				$(this).attr('align', 'center');
			}); 
		 	document.getElementById("yashEmpIdHidden").value= '<input type="hidden" name="yashEmpIdHidden" value="'+clickedRowData.yashId+'" />'; 
			document.getElementById("employeeIdHidden").value= '<input type="hidden" name="employeeIdHidden" value="'+clickedRowData.empId+'" />';  
			document.getElementById("ownershipId").value=clickedRowData.ownershipId;// '<input type="text" id="ownershipId" value="'+clickedRowData.ownershipId+'" />';
			//copy yashId
			thisRow.find("td")[1].innerHTML = '<input type="hidden" name="employeeId.employeeId" value="'+clickedRowData.empId+'" />';
			//copy employeeName
			thisRow.find("td")[2].innerHTML = '<input type="text" name="employeeName" readonly="readonly" value="'+clickedRowData.employeeName+'" />';
			//copy ownershipName
			thisRow.find("td")[4].innerHTML = clickedRowData.ownershipName;//'<input type="text" name="ownershipId" readonly="readonly" value="'+clickedRowData.ownershipName+'" />';
			thisRow.find("td")[5].innerHTML = '<input id="allocStartDate" type="text" name="allocStartDate"  readonly="readonly" value="'+clickedRowData.newStartDate+'"/>';
			thisRow.find("td")[8].innerHTML ='<select class="comboselect" readonly="readonly" id ="currentProjectEditId'+clickedRowData.primaryProject+'" name="curProj"><option value="false">No</option><option value="true">Yes</option></select>';
			thisRow.find("td")[9].innerHTML ='<select class="comboselect" readonly="readonly" id ="behalfManager'+clickedRowData.timeHrs+'" name="behalfManager"><option value="false">No</option><option value="true">Yes</option></select>'; 
			//copy ownershipName
			thisRow.find("td")[7].innerHTML =  '<input type="text" name="allocRemarks" readonly="readonly" value="New Allocation Started" />';
			$(clickedRowData.event.target).closest('tr').find('input, select, textarea, a').css('cursor','not-allowed').prop('disabled','disabled');
			$(clickedRowData.event.target).closest('tr').find('span').css('cursor','not-allowed').css("pointer-events", "none");
			$(clickedRowData.event.target).closest('tr').insertAfter(thisRow);
		 }
		 /* Taken Out New Function from document.ready  */
			 function addNew(e){
				 addNewAlloc =true;
					if(editAlloc== true && addNewAlloc ==true) {
							var text="Please edit and save data or cancel it";
							showAlert(text);
							// Added for task # 216 - End
							addNewAlloc = false;
							e.preventDefault();
							return ;
						}
					$("td input[type=text]").each(function(){
						var default_value = $(this).prop("defaultValue");
						$(this).val(default_value);
					});
					if(saveOpen && !this.innerHTML == "Save"){
						// Added for task # 216 - Start
						var text="Please enter and save the data";
						showAlert(text);
						// Added for task # 216 - End
						e.preventDefault();
						return;
					}	
					if(saveOpen){
						// Added for task # 216 - Start
						var text="Please enter and save the data";
						/* showAlert(text); */
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
						// Added for task # 216 - End
						e.preventDefault();
						return;
					}
					e.preventDefault();
					var nYashEmpId = document.getElementById("yashEmpIdHidden").value;				
					var nYashEmpIdStr = nYashEmpId+ '<input type="hidden" id="employeeId" value="' + document.getElementById("employeeIdHidden").value +'" />' +
					'<input type="hidden" id="id" value=""/> ' ;
					var projectName = document.getElementById("projectNameHidden").value;
					/**Start - Added check to fix Bug #206 */
					if(null == projectName || projectName == ''){
						var errorMsg ="Please select Project from the List"; 
						showError(errorMsg);
						return;
					}
					
					var aiNew = "";
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						aiNew = oTable.fnAddDataAndDisplay(['',nYashEmpIdStr, '','', '','', '','','', '', '', '', 
							'<a class="delete" href="" onclick="deleteDataRow(null);">Delete</a>','','']);
					</sec:authorize>
					<sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
						aiNew = oTable.fnAddDataAndDisplay(['',nYashEmpIdStr, '', '','','', '','','', '', '', '', 
							'','',''] );
					</sec:authorize>
					//Display of delete as per role - end
					var nRow = aiNew.nTr;
					editRow( oTable, nRow);
					nEditing = nRow;
					saveOpen = true;
					$( ".comboselect" ).combobox();
					 // for removing grid on clicking addnew link
					$("#example tbody tr:first").find("td").find("a.cancel").addClass("delAddedRow");
					// for removing grid on clicking addnew link
			 }
		function refreshGrid(){
		}
		
		var oTable;
		
			//Ajax Call on selection of resource
			function ownershipResource (obj){
				var rid= $(obj).val(); 
				if(rid!=null){
				$.ajax({
			        url: '/rms/loanAndTransfer/loadResourceData',
			     	data: {"resourceid":rid},
			     	success: function(response) {
			     		$('#ownershipId').val(response.ownership.id);
			     		dateOfJoin =response.dateOfJoining;
			     		$(obj).parent().parent().find('td').eq(5).html( $('#ownership [value="'+response.ownership.id+'"]').html() );
			     	}
		     	});
				}
			}
			
			
// Start issue fixed for uncaught rangeerror maximum call stack size exceeded chrome	
	function selectDataValue(){
	var returnValue = '';	
		<c:forEach var="resource" items="${resources}">
		returnValue += "<option value="${resource.employeeId}">${resource.employeeName}[${resource.yashEmpId}]</option>";
		</c:forEach>
		
		
		return returnValue;
	}		
	var selectdataValueOfdropdown = selectDataValue();
	
//End  issue fixed for uncaught rangeerror maximum call stack size exceeded chrome		
	function editRow ( oTable, nRow ){
			var changedAllocEnddate= null;
			var changedAllocStartdate= null;
			aData = oTable.fnGetData(nRow);			
			global = nRow;
			jqTds = $('>td', nRow);
		   
			
			if(jqTds.length < 1)return;
			
			  var allocationStartDate = null; 
			  var projectEndDate=null, projectKickOffDate=null, dateOfJoining=null;
			  if(!addNewAlloc){
				  
			  	  if(document.getElementById('endDate').value!="" && document.getElementById('endDate').value!=null){
					projectEndDate = dateConverToNewDateBrowserCompatible(document.getElementById('endDate').value);//new Date(document.getElementById('endDate').value);
					projectEndDate = (projectEndDate.getMonth() + 1) + '/' + projectEndDate.getDate() + '/' +  projectEndDate.getFullYear(); 
				   }
			  	  
				  if(document.getElementById('kickOff').value!="" && document.getElementById('kickOff').value!=null){
	
					  projectKickOffDate= dateConverToNewDateBrowserCompatible(document.getElementById('kickOff').value);//new Date(document.getElementById('kickOff').value);
			          if(projectKickOffDate !="" || projectKickOffDate != null)
			        	projectKickOffDate = (projectKickOffDate.getMonth() + 1) + '/' + projectKickOffDate.getDate() + '/' +  projectKickOffDate.getFullYear(); 
				  }  
				  
				  if(document.getElementById('dateOfJoining').value!="" && document.getElementById('dateOfJoining').value!=null){
	
			          dateOfJoining = dateConverToNewDateBrowserCompatible(document.getElementById('dateOfJoining').value);//new Date(document.getElementById('dateOfJoining').value);
					  dateOfJoining = (dateOfJoining.getMonth() + 1) + '/' + dateOfJoining.getDate() + '/' +  dateOfJoining.getFullYear(); 
				  }
	          }
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
			
	            //added by neha for datepicker issue 
	            var curIndex = getInputValue(aData[2]);	
	            jqTds[1].innerHTML=aData[1];
			
				if(addNewAlloc==true){
					
 /* jqTds[2].innerHTML = "<select  class='comboselect changeTest' onchange='ownershipResource(this)' id =\"employeeNameEditId"+curIndex+"\" name=\"employeeId.employeeId\">"+ 
						               selectdataValueOfdropdown
									 +"</select>"; */
					jqTds[2].innerHTML = "<select  class='changeTest' onchange='ownershipResource(this)' id =\"employeeNameEditId"+curIndex+"\" name=\"employeeId.employeeId\"></select>";
								 
				}
				
				else{
					
					jqTds[2].innerHTML = '<input id="employeeId.employeeId" type="hidden" value="'+curIndex+'">'+aData[2]; 
					
					
				}
				//Code to bind select2 plugin with active resources dropdown - start
				var idIndexVar = curIndex;
				var idToBind = "#"+"employeeNameEditId"+idIndexVar;
			 
				$(idToBind).select2({
				ajax: {
					url: "/rms/projectallocations/activeUserList",
					dataType: 'json',
					data: function (params) {
						return {
							userInput: params.term || '',
						};
					},
					processResults: function (data, params) {
						return {
							results: formatData(JSON.parse(data.resources)),
						};
					},
				},	
				minimumInputLength: 3,
				allowClear: true,
				createTag: function(params) {
					return undefined;
			   },
			   placeholder: 'Search Employee'
				});
				
				function formatData(userList) {
					$.map(userList, function(item, idx) {
						item.id = item.employeeId;
					 	item.text = item.firstName + " " + item.lastName + "(" + item.yashEmpId + ")";
					});

					return userList;
				}
				//Code to bind select2 plugin with active resources dropdown - end	
			

			jqTds[4].innerHTML = aData[4]; 
			if (aData[4]) {
				jqTds[4].innerHTML = '<input class="form-control" name="allocPercentage" id="allocPercentage" onchange="onAllocationPercentageChange()" type="text" value="'+ aData[4] +'">';
			} else {
				jqTds[4].innerHTML = '<input class="form-control" name="allocPercentage" id="allocPercentage" onchange="onAllocationPercentageChange()" type="text" value="0">';
			}
					
			jqTds[6].innerHTML = '<input class="fromdatepicker requir" name="allocStartDate"  id="allocStartDate" type="text" readonly="readonly" value="'+ aData[6] +'">';
			
			dateOfProjectEnd=aData[6];
			jqTds[9].innerHTML ='<select class="comboselect" id ="currentProjectEditId'+getInputValue(aData[9])+'" name="curProj"><option value="true">Yes</option><option value="false">No</option></select>';
			jqTds[10].innerHTML ='<select class="comboselect" id ="behalfManager'+getInputValue(aData[10])+'" name="behalfManager"><option value="true">Yes</option><option value="false">No</option></select>';
			if(lastRowSelectedForAllocationChange !== null && lastRowSelectedForAllocationChange !== undefined){
						jqTds[7].innerHTML = '<input class="todatepicker" name="allocEndDate" id="allocEndDate" type="text" readonly="readonly" value="'+ lastRowSelectedForAllocationChange.find('td').eq(7).html() +'">';
						jqTds[8].innerHTML ='<textarea id ="allocRemarks" name="allocRemarks" cols="10" rows="0">'+"Allocation Changed"+'</textarea>'  ;	
				if(lastRowSelectedForAllocationChange.find('td').eq(9).find('select')[0].value=="true"){
					if(!isFuturedate(lastRowSelectedForAllocationChange.find('td').eq(7).find('input')[0].value)){
						$(jqTds[9]).find('select')[0].value = "false";
					}else{
						$(jqTds[9]).find('select')[0].value = "true";
					}
				}
				if(aData[10].toLowerCase()=="yes"){
						$(jqTds[10]).find('select')[0].value = "true";
					}else if(aData[10].toLowerCase()==="no"){
						$(jqTds[10]).find('select')[0].value = "false";
					} 
			}else{
			jqTds[7].innerHTML = '<input class="todatepicker" name="allocEndDate" id="allocEndDate" type="text" readonly="readonly" value="'+ aData[7] +'">';
			jqTds[8].innerHTML ='<textarea id ="allocRemarks" name="allocRemarks" cols="10" rows="0">'+aData[8]+'</textarea>'  ;	
				//jqTds[8].innerHTML ='<select class="comboselect" id ="currentProjectEditId'+getInputValue(aData[8])+'" name="curProj"><option value="true">Yes</option><option value="false">No</option></select>'
			}				
								
			//jqTds[9].innerHTML ='<select class="comboselect" id ="behalfManager'+getInputValue(aData[9])+'" name="behalfManager"><option value="true">Yes</option><option value="false">No</option></select>'; 
			
			if(lastRowSelectedForAllocationChange !== null && lastRowSelectedForAllocationChange !== undefined){
				 $(lastRowSelectedForAllocationChange).find('.edit, .delete, .copyData').hide();
				 //jqTds[10].innerHTML =' <a id="cancel" class="cancel" href="#" >Cancel</a>';
			}else{
			jqTds[11].innerHTML ='<a id="save" class="edit" href="#">Save</a> / <a id="cancel" class="cancel" href="#" >Cancel</a>';

			jqTds[12].innerHTML =aData[12];
			if(jqTds.length>13)
				jqTds[13].innerHTML =aData[13];
			}
			//populate different select boxes
			var value = getInputValue(aData[4]);
			
			 
			$("#employeeNameEditId"+curIndex).val(curIndex);
				
			if(!lastRowSelectedForAllocationChange){
			value= getInputValue(aData[9]);
			if(aData[9].toLowerCase() == 'yes'){
				$("#currentProjectEditId"+value).val("true");	
			}else{
				$("#currentProjectEditId"+value).val("false");
			}
			//Start Added to solve random issue
			value= getInputValue(aData[10]);
			if(aData[10].toLowerCase() == 'yes'){
				$("#behalfManager"+value).val("true");	
			}else{
				$("#behalfManager"+value).val("false");
			}

			//End Added to solve random issue
			}
			
			
			var index = aData[2].indexOf("<input");
			var strArray = aData[2].substr(index , aData[2].length).split(">", 8);
			var strId="";
			
			if(addNewAlloc==false){
			strId = $(strArray[4] +">").val();

			}
			
	
			 
			var lastactivityDate;
			var lastActDate="";
			if(strId==''){
				 lastactivityDate ='';
			}
			else{
				var lastDate="lastActivityDate"+strId;
		if(document.getElementById(lastDate)!=null && document.getElementById(lastDate).value!=''){
			lastActDate=document.getElementById(lastDate).value;
			  lastactivityDate=Date.parse(document.getElementById(lastDate).value);
		}}
		 
				
				var firstActDate="";
				if(strId==''){
					firstActivityDate ='';
				}
				else
					{
					var firstDate="firstActivityDate"+strId;
		if(document.getElementById(firstDate)!=null){
			var firstActDate= document.getElementById(firstDate).value;
			  firstActivityDate = Date.parse(document.getElementById(firstDate).value);
		}
					}
				 
				<sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
				if (lastactivityDate == null || lastactivityDate == "") {
					jqTds[3].innerHTML = '<select class="comboselect" id ="allocTypeEditId'+getInputValue(aData[3])+'" name="allocationTypeId.id" onchange="onAllocationTypeChange(event)">'+
					<c:forEach var="allocationtype" items="${allocationtypes}">
						'<option value="${allocationtype.id}">${allocationtype.allocationType}</option>'+
					</c:forEach>
				 '</select>';
				 $("#allocTypeEditId"+getInputValue(aData[3])).val(getInputValue(aData[3]));
				} else{
					jqTds[3].innerHTML = '<input id ="allocTypeEditId'+getInputValue(aData[3])+'" type="hidden" name="allocationTypeId.id" value="">'+aData[3];
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
			  //added by neha for datepicker issue  - start
			   var s=nRow.cells[1].innerHTML;	
			    $('.fromdatepicker').datepicker({changeMonth: true,changeYear: true, dateFormat: "dd-M-yy",onOpen :function(){},
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
					/* ,
		            beforeShow: function(){
            			 <sec:authorize access="!hasAnyRole('ROLE_ADMIN')">
	            			   if(s.startsWith('<a href='))
			            		{
			            			$(this).datepicker("option", "minDate",allocStartDateGlobal);
			            		}
			            		else
			            		{
	            			$(this).datepicker("option", "minDate",-1);
			            		} 
            			 </sec:authorize>
		            }  */
		          /*   beforeShow: function(){
		            	if(changedAllocEnddate==null)
					    {
	            			 $(this).datepicker("option", "maxDate", aData[6]);
	            			 $(this).datepicker("option", "minDate",allocationStartDate);// added by neha for start date (datepicker min date) start from DOJ 
	            			
					    }
		            	else
		            	{
		            		
		            		$( this ).datepicker( "option", "maxDate", changedAllocEnddate);
		            		$(this).datepicker("option", "minDate",allocationStartDate);// added by neha for start date (datepicker min date) start from DOJ 
		            	}
		            	 }
			  */
		            			
			    }); 
			 $('.todatepicker').datepicker({changeMonth: true,changeYear: true,beforeShowDay: noSundays,dateFormat: "dd-M-yy",
			    	onClose: function( selectedDate ) {
			    		changedAllocEnddate = selectedDate;
		               
		                
		            },
		             beforeShow: function()
		            {
		            	 if(changedAllocStartdate ==null)
						 {
		            		 $(this).datepicker("option", "maxDate", changedAllocStartdate);
		            		 $(this).datepicker("option", "minDate",aData[6]);
		            		 <sec:authorize access="!hasAnyRole('ROLE_ADMIN')">
		            		 $(this).datepicker("option", "minDate",-15);
		            		</sec:authorize> 
		            		
						 }
		            	 else
		            	 {
		            		 $( this ).datepicker( "option", "maxDate", changedAllocStartdate );
		            		 $(this).datepicker("option", "minDate", aData[6]);
		            		 <sec:authorize access="!hasAnyRole('ROLE_ADMIN')">
		            		 $(this).datepicker("option", "minDate",-15);
		            		</sec:authorize> 
		            	 }
		            } 
			    });
			 //added by neha for datepicker issue - end
			    
			$("#allocRemarks").autoGrow();			
			
			containerWidth();
		} //end of edit row
		
		/* utility function to get a date of one day before passed date string  */
		function getYesterday(dateString){
			var browserDate = dateConverToNewDateBrowserCompatible(dateString);
			var d = new Date(browserDate);
		    var yesterday = new Date(d.getTime() - 24*60*60*1000);
		    //Convert to DD-MMM-YYYY format
		    var m_names = new Array("Jan", "Feb", "Mar", 
    		"Apr", "May", "Jun", "Jul", "Aug", "Sep", 
    		"Oct", "Nov", "Dec");
		    var curr_date = yesterday.getDate();
		    var curr_month = yesterday.getMonth();
		    var curr_year = yesterday.getFullYear();
		    return curr_date + "-" + m_names[curr_month]+ "-" + curr_year;
		};
		
		function addDataTableSearch(table){
			
				$("#tab2 thead input").each( function(i){
					this.initVal = '';
					this.value = '';
				});
			
			    $("#tab2 thead input").keyup( function(){
					oTable.fnFilter( this.value, oTable.oApi._fnVisibleToColumnIndex(oTable.fnSettings(), $("#tab2 thead input").index(this) ) );
				});	
				
			    setTimeout(function(){
			    	$("#tab2 thead input").trigger('keyup');
			    },500);
				
				$("#tab2 thead input").focus( function () {
					if ( this.className == "search_init" ){
						this.className = "";
						this.value = "";
					}
				});
				
				$("#tab2 thead input").blur( function (i) {
					if ( this.value == "" ){
						this.className = "search_init";
						this.value = this.initVal;
					}
				} );
				
				
				oTable = $('#example').dataTable( {
					"sDom": 'RC<"clear">lfrti<"top">rt<"bottom"ip<"clear">',
					"bDestroy": true,
					"bStateSave": true,
					"iCookieDuration": 60,
					"sPaginationType": "full_numbers",
					  "aoColumnDefs": [
						{ "bVisible": false, "aTargets": [] 
						
						}
					],
					
					"oLanguage": {
						"sSearch": "Search all columns:"
					},
					"bSortCellsTop": true

			} );				
			/*------for adding new row 07 sep 2012--------*/
		}//addDataTableSearch
		
		

function saveResourceReleaseDetails(empId,projectId,allocationId,startDate,endDate,joiningDate){		
		if(!endDate){	
			endDate="-";
		}		
		
 		 $.fancybox.open({
			autoSize : false,
			closeClick : true,
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
			 afterClose : function() {
				 addNewRowForChangeAllocation();
			} 
	
		});  
		
	} 
		
		$(document).on('click','#example a.cancel', function (e) {
			$("#example tbody tr").find("td").attr("align","center");
			 	//Updated for Issue #46
			 	addNewAlloc=false;
			 	editAlloc = false;
			saveOpen = false;
			//Updated for Issue #46
			lastRowSelectedForAllocationChange=null;
			e.preventDefault();	
			var nRow = $(this).parents('tr')[0];
			nEditing=nRow;
			restoreRow( oTable, $(nRow).next()[0] );			
			$(nRow).next().find('.fromdatepicker-change-alloc').css('cursor','default').prop('disabled','');
			restoreRow( oTable, nEditing );
		});	
			
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
			
			for ( var i=0, iLen=jqTds.length-1 ; i<iLen ; i++ ) {
				oTable.fnUpdate( aData[i], nRow, i, false );
			}
		}
		
		function saveRow( oTable, nRow, projectEndRemarks){
			var aData = oTable.fnGetData(nRow);
			if(aData == null || aData.length < 1)return;
			var index = aData[2].indexOf("<input");
			//var strArray = aData[2].substr(index , aData[2].length).split(">", 12);
			var strArray = aData[2].substr(index , aData[2].length).split(">", 13);
			var strProjectId = document.getElementById("projectIdHidden").value;
		
			var allocatedBy ="";
			var strId;
			var index1 ='';
			if(addNewAlloc== true)
				strId = null;
			
			else
			{
				var allocctypeid =   getInputValue(aData[3]);
				var alloTypeId= "allocTypeEditId"+allocctypeid;
				if( document.getElementById(alloTypeId).value!='')
					index1 = document.getElementById(alloTypeId).value;
				else
					index1 = getInputValue(aData[3]);
				
				strId = $(strArray[4] +">").val();
				allocatedBy =	document.getElementById("allocatedBy.employeeId").value;
			}
			
			/* var allocPercentage=aData[5].indexOf("<input"); */
			
			var employeeName= aData[5].indexOf("<input");
			
			var sData = $('*', oTable.fnGetNodes()).serializeArray();
			
 				if(index1!=null && index1!='')
 					{
 					 
 					sData.splice(0, 1); 
 					sData.push({name:"allocationTypeId.id",value:index1});
 					sData.push({name:"id",value:strId});
 				}
 				else{
 					
 					if(document.getElementById('employeeNameEditIdnull')!=null)
 					{
	 					var id=document.getElementById('employeeNameEditIdnull').value;
	 					if(id==null || id==''){
	 						 var errorMsg="Please Select Employee Name.";
	 						 showError(errorMsg);
	 						 return;
	 					 }
 					}
 					if(document.getElementById('allocTypeEditIdnull').value == "" || document.getElementById('allocTypeEditIdnull').value == null){
 						 var errorMsg="Please Select Allocation Type.";
 						 showError(errorMsg);
 						 return;
 						}
 					}

			sData.push({name:"projectId.id",value:strProjectId});
			if(addNewAlloc== true){
				if (!(typeof projectEndRemarks === "undefined")) {
					if(projectEndRemarks!="")
					sData.push({name:"projectEndRemarks",value:projectEndRemarks});
			}
			 else{
					sData.push({name:"projectEndRemarks",value:"NA"});
				}
			}
			
			if(allocatedBy != null && $.trim(allocatedBy) != '')
			 
			sData.push({name:"allocatedBy.employeeId",value:allocatedBy});
			 
			if(editAlloc== true){
				var strResourceType = $(strArray[12] +">").val();
				sData.push({name:"resourceType",value:strResourceType});
				var employeeId=getInputValue(aData[2]);
				var isEmpIdAlreadythere = false;
				var isProjectRemarkAlreadythere = false;
				for(var i = 0; i < sData.length; i++) {
				    if (sData[i].name == 'employeeId.employeeId') {
				    	isEmpIdAlreadythere = true;
				    }
				    if(sData[i].name == 'projectEndRemarks'){
				    	isProjectRemarkAlreadythere = true;
				    }
				}
				if(!isEmpIdAlreadythere){
				sData.push({name:"employeeId.employeeId",value:employeeId});
				}
				if(!isProjectRemarkAlreadythere){					
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
			}
			if(editAlloc== false){ 
				var strResourceType = 'Internal';
				sData.push({name:"resourceType",value:strResourceType});
			var ownershipId=document.getElementById("ownershipId").value;
			sData.push({name:"ownershipId.id",value:ownershipId});
			} 
			if(lastRowSelectedForAllocationChange !== null && lastRowSelectedForAllocationChange !== undefined){				
				var ownershipId= getInputValue(aData[4]);
				if(ownershipId !=null){
				sData.push({name:"ownershipId.id",value:ownershipId});
				}
			}  
			
			index = aData[9].indexOf("<input");
			if(index != -1 ) {					
				strArray = aData[9].substr(index , aData[9].length).split(">", 2);
			}
			
				index = aData[10].indexOf("<input");
				if(index != -1 ) {
				strArray = aData[10].substr(index , aData[10].length).split(">", 2);
				var updatedById = $(strArray[0] +">").val();
				if(updatedById != null && $.trim(updatedById) != '')
				sData.push({name:"updatedBy.employeeId",value:updatedById});
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
	
				
			var jsonData ='{';
			$.each(sData, function(i, item) {
				if(item.name == 'projectId.id' && item.value == -1) {
				}else {
					var jsonString = getJsonString(item.name , item.value);
					if(jsonString != null && $.trim(jsonString) != '')
					jsonData += getJsonString(item.name , item.value) +",";
				}
				stopProgress();
			}
			);
			jsonData = jsonData.slice(0, -1);
			jsonData +='}';
			 
			 //US3090: START: Future timesheet delete functionality		 
			 
			 
			   //var resAllocId = document.getElementById("id").value;  commented value not coming while add new resource in new created project
			   
			   var resAllocId;
				element = document.getElementById("id");
				if (element != null) {
					resAllocId = element.value;
				}
				else {
					resAllocId= jQuery.parseJSON( jsonData ).allocationTypeId.id;
				}
			 
			   var weekEndDate =null;
			   var weekEndDate123 =null;
			   var endDate=document.getElementById("allocEndDate").value;
			   if( endDate != null && endDate != '') {
				 weekEndDate= document.getElementById("allocEndDate").value;
			     weekEndDate123= document.getElementById("allocEndDate").value;
			   }
			   
			 var flag = false;
			 var userActivityId;
			
			 if(resAllocId!="") 
			 {
				 if( weekEndDate != null && weekEndDate != '')
		  			{ 
		         		 $.ajax({
									type: 'POST',
								    url: 'projectallocations/getTimesheetStatus/'+resAllocId +"/"+weekEndDate,
								    contentType: "application/json; charset=utf-8",
								    async:false,
								    success: function(data) {
								    	var jsonData1 = JSON.parse(data);	
								    	if(jsonData1.status == "true")
							    		{
								    		$.postData("projectallocations", jsonData, function(data) {
													checkAll('');
													saveOpen = false;
													nEditing= null;
													addNewAlloc = false;
													stopProgress();											
													var successMsg ="Project allocation detail have been saved successfully"; 
													showSuccess(successMsg);
													 if(endDate!=''){
														 setTimeout(function(){
														 addNewRowForChangeAllocation();
															 
														 },3000);
														}
													 
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
					  							    var answer=("Are You sure, you  want to save this end date? If yes, then all the submitted timesheet after this date will be deleted.");
					  							    if(answer){
					  									 $.ajax({
					  											type: 'GET',
					  											contentType : 'application/json; charset=utf-8',
					  								            dataType: 'json',
					  										    url: 'projectallocations/deleteFutureTimesheet',
					  										    async:false,
					  										    data : "userActivityId=" + userActivityId + "&resourceAllocId=" + resAllocId + "&weekEndDate=" + weekEndDate123,
					  										    success: function(data) {
					  										    	
					  										    	$.postData("resourceallocations", jsonData, function(data) {
					  													checkAll('');
					  													saveOpen = false;
					  													editAlloc=false;
					  													nEditing= null;
					  													stopProgress();			  													
					  													var successMsg ="Project allocation detail have been saved successfully"; 
					  													showSuccess(successMsg);
					  													 if(endDate!=''){
					  														 addNewRowForChangeAllocation();
							  													}
					  												 }, 
					  												 "json"
					  												); 
					  										    	
					  										    }
					  										    
					  									    }); 
					  									}
					  							    else{
					  							    	if(changeAllocationFlag){
					  							    		changeAllocationFlag= false;
					  							    		restoreRow( oTable, lastRowSelectedForAllocationChange[0] );
					  							    	}
					  									stopProgress();
					  							       }
					  							    }
					  							}
		         		
								    }// success ends
		         		 
		          		}); editAlloc=false;  
					} 
			 else
			 { 
				  $.postData("projectallocations", jsonData, function(data) {
					checkAll('');
					saveOpen = false;
					editAlloc=false;
					nEditing= null;
					
					var successMsg ="Project allocation details have been saved successfully"; 
					showSuccess(successMsg);
					 if(endDate!=''){
						 addNewRowForChangeAllocation();

						}
				 }, "json");
	       }
		}
			 
		// addNewAlloc=false;
			
		if(resAllocId == "") {  
			var id = document.getElementById("employeeNameEditIdnull").value;
			var startDate = document.getElementById("allocStartDate").value;
			
			 if( startDate != null && startDate != '')
				 startDate = document.getElementById("allocStartDate").value;
			 
			 var alloctionTypeId = document.getElementById("allocTypeEditIdnull").value;
			 
			 if(id==null || id==''){
				 var errorMsg="Please Select Employee Name.";
				 showError(errorMsg);
				 return;
			 }
			 
			 if(alloctionTypeId=='' || alloctionTypeId==null){
				 var errorMsg="Please Select Allocation Type.";
				 showError(errorMsg);
				 return;
			 }
			 
			var flag;
			
			 $.ajax({
					type: 'POST',
				    url: "projectallocations/checkForExistingAllocation",
				    contentType: "application/json; charset=utf-8",
				    async:false,
				    data : jsonData,
				    success: function(data) {
				    	if(data.openflag=="true"){
				    		flag = true;
				    	}
				    	else{
				    		flag = false;
				    	}
				    },
				    error : function(data){
				    	stopProgress();
			     		var obj = jQuery.parseJSON(data.responseText);
			     		flag = false;
				    }
				    	 
			});
			 
			if(flag) {
				 $.fancybox({ 
						autoSize : false,
						closeClick : false,
						autoDimensions : true,
						transitionIn : 'fade',
						transitionOut : 'fade',
						openEffect : 'easingIn',
						closeEffect : 'easingOut',
						type : 'iframe',
						href : '${myProfile}'+id+'/'+startDate,'width' : '100%',
						preload : true,
						beforeShow : function() {
							var thisH = this.height - 35;
							$(".fancybox-iframe").contents().find('html').find(".midSection").css('height', thisH);
						},
						helpers : {overlay : {closeClick: false} // prevents closing when clicking OUTSIDE fancybox 
						},
						afterClose: function() {
						  var href = window.location.pathname;
						  
						  if (href.toLowerCase().indexOf("admindashboard") >= 0 || href.toLowerCase().indexOf("userdashboard") >= 0 ){
						    window.location.reload();
						  }

						  $.postData("projectallocations", jsonData, function(data) {
								var id = document.getElementById("projectIdHidden").value;
								checkAll('');
								saveOpen = false;
								addNewAlloc=false;
							 	editAlloc = false;
								nEditing= null; 															
								var successMsg ="Project allocation details have been saved successfully"; 
								showSuccess(successMsg);
								if(endDate!=''){
								 addNewRowForChangeAllocation();
								}
							 } , "json" )
							.error(function(data) {});   
						    addNewAlloc=false;	
						    editAlloc = false; 
							}
						}); 
			}
			
			else 
			{
				  $.postData("projectallocations", jsonData, function(data) {
						var id = document.getElementById("projectIdHidden").value;
						checkAll('');
						saveOpen = false;
						addNewAlloc=false;
					 	editAlloc = false;
						nEditing= null; 						
						var successMsg ="Project allocation details have been saved successfully"; 
						showSuccess(successMsg);
						 if(endDate!=''){
							 addNewRowForChangeAllocation();
							}
					 } , "json")
					 .error(function(data) {});   
				 	editAlloc = false;
				 	addNewAlloc=false;
			}

		}
	}	 //end of save row
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
			
			var projectAllocNavigationFlag="${projectAllocNavigationFlag}";
			if(projectAllocNavigationFlag=="true"){
				var projectId="${projectId}";
				var projectName="${projectName}";
				var projectKickOffDate="${projectKickOffDate}";
				var projectEndDate="${projectEndDate}";
				openMaintainance(projectId,projectName,projectKickOffDate,projectEndDate);
			}
			showEntries = 10;
			containerWidth();
			
			$(".tab_div").hide();
			$('ul.tabs a.listTab').click(function () {
				saveOpen = false;
				$(".tab_div").hide().filter(this.hash).show();
				$("ul.tabs a.listTab").removeClass('active');
				$(this).addClass('active');
				$('a[href$="tab2"]').addClass('MaintenanceTab');
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
					if (e.keyCode == 14) {
					       projectAllocationTable.fnFilter( this.value, projectAllocationTable.oApi._fnVisibleToColumnIndex(projectAllocationTable.fnSettings(), $("thead input").index(this) ) );
					    }
				 	});
		
			 	$("thead input").keyup( function(){
			 		projectAllocationTable.fnFilter( this.value, projectAllocationTable.oApi._fnVisibleToColumnIndex(projectAllocationTable.fnSettings(), $("thead input").index(this)));
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
			
		//Task #1107
				projectAllocationTable = $('#projectallocationTableId').dataTable({
                "bProcessing": true,
                "bServerSide": true,       
                "sAjaxSource": '/rms/projectallocations/list/'+"active",
                "sPaginationType": 'full_numbers',
                "pagingType": "full_numbers",
                "bAutoWidth" : false,
                "sScrollX": "100%",
                "bScrollCollapse": true,
                "iDisplayLength": showEntries,
                "bPaginate": true,
                "bDestory": true,
                "bRetrieve": true,
                "sDom": '<"projecttoolbar">lfrtip',
                "sDom": '<"top"lfi>rt<"bottom"ip<"clear">',
                "aaSorting": [ [0,'asc']],
                "oLanguage": {
                                "sLengthMenu": 'Show <SELECT id ="display">'+ '<OPTION value=10>10</OPTION>'+ '<OPTION value=20>20</OPTION>'+ '<OPTION value=50>50</OPTION>'+ '<OPTION value=100>100</OPTION>'+ '<OPTION value=200>200</OPTION></SELECT> entries'
                },
               
         		"bSortCellsTop": true,
                "fnServerData": function ( sSource, aoData, fnCallback ) {
                    sSource = sSource + '?noCache=' + new Date().getTime();
                   callJSONWithErrorCheck(sSource,aoData,null,function (json) {  
                    	    fnCallback(json); 
                    });                           
				    },
				    "fnDrawCallback": function() {
				                    handlePaginationButtons(projectAllocationTable, "projectallocationTableId");
				                    },
				    "fnInitComplete": function() {
				    },
				    
				    "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
				    	
			        var projectName=aData[2];
			    	var customerName=aData[3];
			    	var offshoreManager=aData[4];
			        var onsiteManager = aData[5];
			        var currentBgBU = aData[6];
			        var projectKickOffDate = aData[8];
			        var projectEndDate = aData[10];
			        var managerReadonly = aData[12];
					
			        if(projectName == ""||projectName == null ){
				    	$('td:eq(1)', nRow).html("N.A");
				    }
				    
			        if(customerName == ""||customerName == null ){
				    	$('td:eq(2)', nRow).html("N.A");
				    }
			        
			        if(offshoreManager == ""||offshoreManager == null ){
				    	$('td:eq(3)', nRow).html("N.A");
				    }
			        
			        if(onsiteManager == ""||onsiteManager == null ){
			    	$('td:eq(4)', nRow).html("N.A");
			    	}
				   
			        if(currentBgBU == null || currentBgBU == ""){
				    	$('td:eq(5)', nRow).html("N.A");
				    }
				    
			        if(projectKickOffDate == null ||projectKickOffDate== "" ){
				    	$('td:eq(6)', nRow).html("N.A");
				    }
			        
			        var endDate = new Date(projectEndDate);
			        var toDay = new Date(today);
			        
			        if(projectEndDate &&  endDate < toDay){	
						nRow.className='redColorResource';
					}
			        if(projectEndDate == null ||projectEndDate== "" ){
				    	$('td:eq(7)', nRow).html("N.A");
				    }
			        var deliveryMgrVar = aData[14];
			    	 if(deliveryMgrVar == ""|| deliveryMgrVar == null || deliveryMgrVar == 0){
						 $('td:eq(8)', nRow).html("N.A");
					 }else
						 {
						 $('td:eq(8)', nRow).html(deliveryMgrVar);
						 }
			    	 
			    <sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
				    var managerReadonly = aData[12];
				    if(managerReadonly == false){
				    	 $('td:eq(0)', nRow).html('<a href="return false;" onClick="openMaintainance(\''+aData[0]+'\',\''+aData[2]+'\',\''+aData[8]+'\',\''+aData[10]+'\');return false;">' +
					                aData[1] + '</a>');
					            return nRow;
				    }else{
				    	 $('td:eq(0)', nRow).html('<a href="#" class="copyInactive"  disabled="disabled" >'+  aData[1] + '</a>');
					            return nRow;
				    }
				    </sec:authorize> 
					<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_DEL_MANAGER')">
				    $('td:eq(0)', nRow).html('<a href="return false;" onClick="openMaintainance(\''+aData[0]+'\',\''+aData[2]+'\',\''+aData[8]+'\',\''+aData[10]+'\');return false;">' +
			                aData[1] + '</a>');
			            return nRow;
				    </sec:authorize>
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
										bSortable: true,
										bVisible:false
									},
									
									{ 
										sName: "projectKickOffDate",
										sWidth : "",
										bSortable: true
									} ,
									
									{ 
										sName: "plannedProjectSize",
										sWidth : "",
										bSortable: true,
										bVisible:false
									},
									
									{ 
										sName: "projectEndDate",
										sWidth : "",
										bSortable: true,
										bVisible:true
									},
									{ 
										sName: "deliveryManager",
										sWidth : "",
										bSortable: true,
										bVisible:true
									}
                      ] 
			});
			$("#projectallocationTableId_filter").hide();
			
			
			$('#update').click(function(){
				displayMaintainance();
			});
			
			var projectEndRemarks='';
			
			$('#okbtn').click(function(){
				var projectEndRemarks = "";
				projectEndRemarks=$("#feedbackText").val();
				 $("#feedbackText").val(null); 
				if(projectEndRemarks.length  == "" || projectEndRemarks == null ){
					$("#feedbackText").focus();
					showError("Please give project allocation end feedback.");
				}
				else{
					$.fancybox.close();
					if(lastRowSelectedForAllocationChange && lastRowSelectedForAllocationChange[0]){
						saveRow(oTable, lastRowSelectedForAllocationChange[0],projectEndRemarks);
					}
					else{
						saveRow(oTable, nEditing,projectEndRemarks);
					}
				}
			});
			
			$('#cancelbtn').on("click", function(){
				$("#feedbackText").val(null);
				$.fancybox.close();
				 addNewAlloc=false;
			 	editAlloc = false;
				restoreRow( oTable, lastRowSelectedForAllocationChange[0] );			
				lastRowSelectedForAllocationChange.find('.fromdatepicker-change-alloc').css('cursor','default').prop('disabled','');
				lastRowSelectedForAllocationChange=null;
				//jqTds[10].innerHTML =' <a id="cancel" class="cancel" href="#" >Cancel</a>';
			});
			
			
			
			 $(document.body).on('change','#listActiveOrAll',function(){
					
				  if(this.value=='All')
		   				{
		   				var oSettings = projectAllocationTable.fnSettings();
		   				oSettings.sAjaxSource  = "/rms/projectallocations/list/all",
		   				 projectAllocationTable.fnClearTable();
		   				projectAllocationTable.fnDraw();
		   				 
		   				}
		   				else
		   				{
		   				var oSettings = projectAllocationTable.fnSettings();
	   				    oSettings.sAjaxSource  = "/rms/projectallocations/list/active",
	   				    projectAllocationTable.fnClearTable();
		   				projectAllocationTable.fnDraw();
		   				}
				});
			
			 $(document).on("change", '#display', function(ev){
				});
			
			
			$('#addNew').click(addNew);
			
			
			$(document).on('click','#example a.delete', function (e) {
				e.preventDefault();				
				 $('<div class="toasterBgDiv"></div>').appendTo($('body'));
			});
			
			$(document).on('click', '#example a.edit', function (e) { 
				if(addNewAlloc==true && this.innerHTML == "Edit")
					{
					// Added for task # 216 - Start
					var text="Please enter and save the data in new row or cancel it";
					showAlert(text);
					// Added for task # 216 - End
					editAlloc = false;
					e.preventDefault();
					return;
					}
					
				else 
					{
					if(addNewAlloc==true && saveOpen==true){
						editAlloc = false;
					}
				if(addNewAlloc==false && saveOpen==true)
					{
					 editAlloc = true;
					}
				if( saveOpen==false)
					{
					editAlloc = true;
					addNewAlloc= false;
					}
				
					}
				var status = $(this).closest("tr").find("td:nth-child(10)").html();
				
				var dateOfJoiningVal = $(this).closest("tr").find("td:nth-child(2)").find("#dateOfJoining").val();
				
				var isValidPercentage = onAllocationPercentageChange();
				if (!isValidPercentage) {
					return false;
				}
				$(this).parent().parent().find("td").attr("align","left");
				$(this).parent().parent().find("td:nth-child(1)").attr("align","center");
				if(saveOpen && !this.innerHTML == "Save"){
					// Added for task # 216 - Start
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
 								if ( nEditing !== null && nEditing != nRow ) {
									/* Currently editing - but not this row - restore the old before continuing to edit mode */
									restoreRow( oTable, nEditing );
									editRow( oTable, nRow );
									nEditing = nRow;
									
								}
								else if ( nEditing == nRow && this.innerHTML == "Save" ) {
									/*------------------------Added By Prasoon------------------------------*/
									var nRow = $(this).parents('tr')[0];
									var otherInputs = $(this).parents('td').siblings().find('input');
									
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
									 if(validationOnEdit() == true)
								        {
										 var aData = oTable.fnGetData(nEditing);
										 if(document.getElementById("allocEndDate").value!='' && addNewAlloc==true)
											{
											 	$.fancybox("#commentBox");
												return;
											}
										 
										 else if(document.getElementById("allocEndDate").value!='' && editAlloc==true){
											if(document.getElementById("allocEndDate").value!=dateOfProjectEnd){
											 $.fancybox("#commentBox");
												return;
											}
										 }
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
			 
			
			if(projectAllocNavigationFlag!="true"){
			    document.getElementById("selectedProjectName").innerHTML= "Maintenance";
			}  
			$('#MaintenanceTabInactive').off('click');	
			$('a[href$="tab1"]').click(function(){
				//set heading to default again
				document.getElementById("selectedProjectName").innerHTML= "Maintenance";
				$("input.search_init").each(function(){
					var default_value = $(this).prop("defaultValue");
					$(this).val(default_value);
					var oSettings = projectAllocationTable.fnSettings();
					oSettings.bProcessing = false;
					$('#projectAllocationTable').dataTable({ "bProcessing": false },{ "bSort": false }).fnDraw();
					projectAllocationTable.fnDraw();
				});
				
				saveOpen = false;
			});
			
		});//End of ready function
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
				$('#projectallocationTableId').show();
				$('#projectallocationTableId_info').show();
				}, 400);
		}
			

function displayMaintainance(){
	 setTimeout(function(){
		 $(".proAllocTblData").show();
			$(".proAllocDialog").show();
			$(".proAlloctab_div").show();
			 $(".projectAllocationBtnIcon").show();
			 $("#maitainanceId").css("display","inline-block");
			 $("ul.tabs a.listTab").removeClass('active');
			 $(".tab_div").hide().next("#tab2".hash).show();
			 $('a[href$="tab2"]').removeClass('MaintenanceTab');
			 $('a[href$="tab2"]').addClass('active');
			 $("#example").css('width', '100%');
			 containerWidth();
	 },1000);
	
}
var vm = {
		getMultiSelectResource:function(){
		    var multiObjs =  $('#multiresource').val();
		    	var obj = multiObjs;
		    	$(obj).multiselect({
					 noneSelectedText: 'Select Resources',
						 selectedList: 2
			    });
				$(obj).multiselect("uncheckAll");
		}
	};
	$.views.helpers({ getMultiResource: vm.getMultiSelectResource });
	
	$("#projectTableId a.copyInactive").on("click", function(event){
        
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

	
function openMaintainance(id, projectName,projectKickOffDate,projectEndDate){// <input type="hidden" id="projectKickOffDateHidden" name="projectKickOffDateHidden">
		projectId = id;
		projectN = projectName;
			//startProgress();
			$( ".MaintenanceTab" ).show();
			$( "#selectedProjectName" ).show();
			document.getElementById("selectedProjectName").innerHTML= projectName;
			document.getElementById("projectIdHidden").value = id;
			document.getElementById("projectNameHidden").value = projectName;
			document.getElementById("projectKickOffDateHidden").value=projectKickOffDate;
			document.getElementById("projectEndDateHidden").value=projectEndDate;
			
			document.getElementById("activeOrAll").value = 'Active';
			getProjectAllocationById(id);
			document.getElementById('navigationToTimeHrs').innerHTML="<br/><strong>Approve "+ projectName+"'s resource latest timesheet</strong>";
		    $("#reviewIcon").show();
			displayMaintainance();
	}

	function getProjectAllocationById(id){
      $.ajax({  
        url: "projectallocations",  
        dataType: 'json',  
        data: {find:"ByActiveProjectId" , projectId:id},  
        async: true,  
        success: function(data){  
        	showProjectAllocation(data);
        	if(data=='')
			{
        		$("#reviewIcon").hide();
        		$("#navigationToTimeHrs").hide();
        		
			}
        	else{
 				  $("#reviewIcon").show();
 	 			   $("#navigationToTimeHrs").show();
 			}
        	
        },
        error: function(){
               showError('Some Problem Occurred...Cannot display data!!');
               stopProgress();
        }
      }) ; 

	}
function showProjectAllocation(data) {
	if(changeAllocationFlag) {
		changeAllocationFlag= false;
  		return;
  	}
	if(oTable != null){
		oTable.fnClearTable();
	}
	$("#example > tbody:last").append($("#projectAllocationRows").render(data));
	/*for change allocation datepicker */

	$('.fromdatepicker-change-alloc').datepicker({changeMonth: true,changeYear: true, dateFormat: "dd-M-yy",onOpen :function(){},
    	onClose: function(selectedDate) {
    		if(editAlloc== true || addNewAlloc ==true){				
				var text="Please enter and save the data";
				showAlert(text);				
				return;
			}	
    		if(selectedDate){
    			//For change allocation, on start date change, update selected row's end date
                if(lastRowSelectedForAllocationChange !== null && lastRowSelectedForAllocationChange !== undefined){
                	lastRowSelectedForAllocationChange.data({newStartDate:selectedDate});
                	var yesterDay = getYesterday(selectedDate);
                	lastRowSelectedForAllocationChange.find('td').eq(6).html(yesterDay);
                	editAlloc = true;
					editRow(oTable,  lastRowSelectedForAllocationChange[0] );
					 if(validationOnEdit() == true)  {
					    // $.fancybox("#commentBox"); 
              			//saveRow(oTable, $(this).parents('tr')[0]);
					 }
					 else{
						 jqTds[10].innerHTML =' <a id="cancel" class="cancel" href="#" >Cancel</a>';
						 return;
					 }
              		addNewAlloc=false;
                }
    		}
     }
    }); 
	highlightRow();
	addDataTableSearch($("#example"));
	$(".multiSelectDd").multiselect().multiselectfilter();
	
	
	if(data=='')
	{
		$("#reviewIcon").hide();
		$("#navigationToTimeHrs").hide();
		
	}else{
		  $("#reviewIcon").show();
		  $("#navigationToTimeHrs").show();
	}
	
	containerWidth();
	stopProgress();
}

/* Added for task #1229 */
function highlightRow(){
	
	var employeeBuId =[];
	var projectBuId= [];
	var rowId= [];
	var allocatEndDate = [];
	highlightRowTipFlag = 0;
	$(".empID").each(function(){
		employeeBuId.push($(this).val());
		rowId.push($(this).attr("id"));
		
	});
		
	$(".prjID").each(function(){
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
			}
		}
		
	}
	
	if(highlightRowTipFlag != 0 ){
		$("#highlightRowTip").show();
	}else{
		$("#highlightRowTip").hide();
	}
}



function saveProjectAllocation(){
	var pk = $("#projectallocationForm input[name=id]").val();
	//not doing any validation .. We will use jquery validation framework to do that for us before form gets submitted
	if( pk != null && pk > 0){
		 $.putJson('projectallocations', $("#projectallocationForm").toDeepJson(),function(data){
			 location.reload();
		 } ,'json');
	}else{
		 $.postJson('projectallocations',$("#projectallocationForm").toDeepJson(),function(data){
			 //should call refreshGrid instead of location reload, and update customerTableId table.
			 location.reload();
		}, 'json');
}
}

function noSundays(date) {
    return [date.getDay() != 0, ''];
}

function noSaturday(date) {
    return [date.getDay() != 6, ''];
}
function refreshGrid(){
	$.getJSON("projectallocations", function(data){
		
	});
}

/* ------------------ Allocation for Active or All-----------------------  */
function checkAll(calledBy){
	var id=$("#projectIdHidden").val();
	var action=document.getElementById("activeOrAll").value;
	if(action=="Active" && calledBy =="copy"){
		saveOpen = false;
		$.getJSON("projectallocations",{find:"ByActiveProjectId" , projectId:id}, showProjectAllocation).error(function() { showError("Some of the copied Resource already have Primary Project");});

	}
	else
	if(action=="Active"){
		saveOpen = false;
		$.getJSON("projectallocations",{find:"ByActiveProjectId" , projectId:id}, showProjectAllocation);
		
	}else{
		saveOpen = false;
		$.getJSON("projectallocations",{find:"allocationByProjectId" , projectId:id}, showProjectAllocation);
		
	}

   setTimeout(function(){
	
	$("#example").css("width",'100%');
	
	},1000);

}
function move_item(from, to)
{
  var f;
  var SI; 
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

function moveUp(lst){
	if (lst.selectedIndex>0){
		var SI = lst.selectedIndex;
		var tempOpt = new Option(lst.options[SI].text,lst.options[SI].value);
		lst.options[SI].text = lst.options[SI-1].text;
		lst.options[SI].value = lst.options[SI-1].value;
		lst.options[SI-1].text = tempOpt.text;
		lst.options[SI-1].value = tempOpt.value;
		lst.options[SI-1].selected=true;
	}
}

function moveDown(lst){
	if (lst.selectedIndex<lst.options.length-1){
		var SI = lst.selectedIndex;
		var tempOpt = new Option(lst.options[SI].text,lst.options[SI].value);
		lst.options[SI].text = lst.options[SI+1].text;
		lst.options[SI].value = lst.options[SI+1].value;
		lst.options[SI+1].text = tempOpt.text;
		lst.options[SI+1].value = tempOpt.value;
		lst.options[SI+1].selected=true;
	}
		
		
}

function openTimesheetApproval(event){ 
 var projectId = document.getElementById("projectId").value;
 var projectAllocStatus = document.getElementById("activeOrAll").value;
 var _href = $('#navigationToTimeHrs').attr('href');
 var res = _href.substring(0, 9);
 var weekEndDate = getLastWeekday( new Date() , 6 );
 weekEndDate = getFormattedDate(weekEndDate);
 var requestParam = "?projectId="+projectId+"&timesheetstatus=P&projectAllocStatus="+projectAllocStatus+"&weekEndDate="+weekEndDate;
 $('#navigationToTimeHrs').attr("href", res+requestParam);
}

function validDates1(fromDate, toDate) {
	
	var SDate='';
	var startDate='';
	var EDate='';
	var endDate='';
	
	if(toDate != ""){
		var dateSplit = toDate.split("-"); 
		dateObjendDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);
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

function validDatesForJoining(fromDate, toDate) {
	
	var sDate='';
	var startDate='';
	var eDate='';
	var endDate='';
	var substring = "-";
	
	if(toDate != "") {
			
		/*var string = toDate;
		if(string.indexOf(substring) !== -1)
		{ */
		var dateSplit = toDate.split("-"); 
		dateObjendDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);
		eDate = dateObjendDate;
		/*  }else{			
			 	eDate = toDate;	
		} */
		endDate = new Date(eDate);endDate.setHours(0, 0, 0, 0);
	}
		
	if(fromDate != "") {	
			
		if(fromDate==undefined){
			fromDate = document.getElementById('dateOfJoining').value;
		}
		var dateSplit1 = fromDate.split("-"); 
		var dateObjfromDate = new Date(dateSplit1[1] + " " + dateSplit1[0] + ", " + dateSplit1[2]);
		sDate = dateObjfromDate;
				
		startDate = new Date(sDate);startDate.setHours(0, 0, 0, 0); 
	}
	if(sDate != '' && eDate != '' && startDate>endDate) 
		return false;
	return true;
}

</script>

<script type="text/javascript" charset="utf-8">

var oTable;

</script>

<div id="projectAllocationYashRMS" class="content-wrapper">
	<!--right section-->
	<div class="botMargin">
		<h1>Project Allocation</h1>
	</div>
	<div class="breadcrums">
		<a class="breadcrumbslink" href="#">RAM</a> <img
			src="resources/images/imgBreadCrumsArrow.gif" width="10" height="11" />
		<strong>Page Name</strong>
	</div>

	<div class="tab_seaction">
		<ul class='tabs'>
			<li><a href='#tab1' id="tab1" class="listTab">List</a></li>
			<li><a class="MaintenanceTab listTab"
				id="MaintenanceTabInactive" href='#tab2'><span
					id="selectedProjectName" style="color: #72a1c9; font-size: 15px;"></span></a></li>
			<!-- <li><span style="color:#0029FF;">* If user filled his time sheet on some date then End date caledar will display it as a disable.</span></li> -->
		</ul>
		<div id="commentBox" align="center"
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
		</div>
		<div id='tab1' class="tab_div maintainancetab_div">

			<div class="tbl">
				<input type="hidden" id="yashEmpIdHidden" name="yashEmpIdHidden">
				<input type="hidden" id="employeeIdHidden" name="employeeIdHidden">
				<input type="hidden" id="projectIdHidden" name="projectIdHidden">
				<input type="hidden" id="projectNameHidden" name="projectNameHidden">
				<input type="hidden" id="projectKickOffDateHidden"
					name="projectKickOffDateHidden"> <input type="hidden"
					id="projectEndDateHidden" name="projectEndDateHidden"> <input
					type="hidden" id="ownershipId" name="ownershipId"> <input
					type="hidden" id="dateOfJoinHidden" name="dateOfJoinHidden" />
				<script type="text/javascript" charset="utf-8">
					displayTableData();
					</script>
				<div id="projectAllocationTbl">
					<table id="projectallocationTableId"
						class="dataTbl display tablesorter dataTable addNewRow alignCenter white-sort-icons my_table positiondashboard-table tableAlignment">
						<div id="xyz"
							style="position: absolute; left: 150px; z-index: 9; top: 0px;">
							<span class="smallSelectBoxStyle">Status <select
								id="listActiveOrAll">

									<option selected="selected" value="Active">Active</option>
									<option value="All">All</option>

							</select>
							</span>
						</div>
						<thead>
							<tr>
								<th align="center" valign="middle">Project ID</th>
								<th align="center" valign="middle">Project ID</th>
								<th align="center" valign="middle">Project Name</th>
								<th align="center" valign="middle">Customer Name</th>
								<th align="center" valign="middle">Project Manager</th>
								<th align="center" valign="middle">Onsite Manager</th>
								<th align="center" valign="middle">Current BG-BU</th>
								<th align="center" valign="middle">Engagement Model</th>
								<th align="center" valign="middle">Project Kick off Date</th>
								<th align="center" valign="middle">Planned Project Size</th>
								<th align="center" valign="middle">Project End Date</th>
								<!--  <th  width="90px" align="center" valign="middle">Project tracking Currency </th> -->
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
								<td><input type="text" name="search_projectManager"
									value="Project Manager" class="search_init" /></td>
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
									disabled="disabled" /></td>
								<td><input type="text" name="search_projectEndDate"
									value="Project End Date" class="search_init"
									disabled="disabled" /></td>
								<td><input type="text" name="search_deliveryManager"
									value="Delivery Manager" class="search_init" /></td>
								<!--  <td><input type="hidden" name="search_projectTrackingCurrency"
							value="Project Tracking Currency" class="search_init" disabled="disabled"/></td>  -->
						</thead>
						<tbody>

						</tbody>

						<!-- <span class = "label label-info proj_alloc_note_msg"><span style="color: #FF0000 !important;" >*</span> Calendar Dates Enables when<br>
					# On Start Date- Joining date is less than or equal to current date.</span> -->

					</table>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<div id='tab2' class="tab_div">
			<div class="search_filter">
				<div class="btnIcon1 projectAllocationBtnIcon">
					<span> <sec:authorize
							access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">

							<a href="javascript:void(0);" class="blue_link" id="addNew">
								+ Add New
							</a>

						</sec:authorize>
					</span> <span class="ProjectAllocStatusdrpdwn">&nbsp; Status
						&nbsp;<select onchange="checkAll('')" id="activeOrAll">

							<option value="Active">Active</option>
							<option value="All">All</option>

					</select>

					</span>

				</div>
			</div>
			<div></div>
			<div class="tbl">
				<table id="example"
					class="display projectAllocationSecodTabList tablesorter addNewRow proAllocTblData">
					<thead>
						<tr>
							<th>Feedback Remarks</th>
							<th>Employee ID</th>
							<th>Employee Name</th>
							<th>Allocation Type</th>
							<th>Allocation Percentage</th>
							<th>Status</th>
							<th>Start Date</th>
							<th>End Date</th>
							<th>Allocation Remarks</th>
							<th>Primary Project</th>
							<!-- 	<th>Billable</th>
						<th>Weekly Allocation Hours</th> -->
							<!-- <th>Rate ID</th> -->
							<th>TimeHrs Manager</th>
							<!-- 	<th width="30%">Team Name</th>		 -->
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
								<th>Edit</th>
							</sec:authorize>
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN')">
								<th>Delete</th>
							</sec:authorize>
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
								<th width="7%">Copy Allocation</th>
								<th width="7%">Change Allocation</th>
							</sec:authorize>
						</tr>
						<tr class="noBg" draggable="false">
							<td><input type="text" name="search_feedbackremarks"
								value="" placeholder="Feedback Remarks" class="search_init" /></td>
							<td><input type="text" name="search_eId" value=""
								placeholder="Employee ID" class="search_init" /></td>
							<td><input type="text" name="search_eName" value=""
								placeholder="Employee Name" class="search_init" /></td>
							<td><input type="text" name="search_alType" value=""
								placeholder="Allocation Type" class="search_init" /></td>
							<td><input type="text" name="search_alType" value=""
								placeholder="Allocation Percentage" class="search_init" /></td>
							<td><input type="text" name="search_status" value=""
								placeholder="Loan/Owned" class="search_init" /></td>
							<td><input type="text" name="search_stDate" value=""
								placeholder="Start Date" class="search_init" /></td>
							<td><input type="text" name="search_enDate" value=""
								placeholder="End Date" class="search_init" /></td>

							<td><input type="text" name="search_alRem" value=""
								placeholder="Allocation Remarks" class="search_init" /></td>
							<td><input type="text" name="search_priProject" value=""
								placeholder="Primary Project" class="search_init" /></td>

							<td><input type="text" name="search_rId" value=""
								placeholder="TimeHrs Manager" class="search_init" /></td>
							<!-- <td><input type="text" name="search_teamName"
								value="Team Name" class="search_init" /></td> -->
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
								<td>&nbsp;</td>
							</sec:authorize>
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN')">
								<td>&nbsp;</td>
							</sec:authorize>
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')">
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</sec:authorize>
						</tr>
					</thead>
					<tbody id="example">
					</tbody>
					<span class="label label-info proj_alloc_note_msg"><span
						style="color: #FF0000 !important;">*</span> Calendar Dates Enables
						when<br> # On Start Date- Joining date is less than or equal
						to current date.</span>



					<a href="timehrses" onclick="openTimesheetApproval()"
						id="navigationToTimeHrs"></a>

					<img src="resources/images/review-icon-custom.png" id="reviewIcon"
						style="display: none;" />
				</table>
			</div>
			<div class="clear"></div>

			<div id="highlightRowTip" class="highlightRowTip"
				style="background-color: #5CB3FF; display: inline;">Tip:
				Highlighted resources does not belong to Project BU. (i.e.
				Resource's Current BU is not equal to Project BU).</div>
		</div>
	</div>
	<!--right section-->
</div>
<div id="dialog" class=""
	style="background: white; width: 800px; height: 300px;">
	<input type="hidden" name="copyRowId" id="copyRowId" /> <input
		type="hidden" name="copyResourceId" id="copyResourceId" /> <input
		type="hidden" name="selectProjectId" id="selectProjectId" />
	<!-- <div id="dialogText">
		<b>Please Select Resources to Copy</b>
			
	</div> -->

	<div id="copyResource">
		<table width="99%" border="0" align="center" cellpadding="1"
			cellspacing="1">

			<tbody>
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
													<p class="label label-info proj_alloc_resourcetocopy_msg">
														# Associates whose joining dates are equal to or greater
														then allocation start date are eligible for<br>
														&nbsp;&nbsp;search/allocation. <br> # Resources
														already allcoated to project will not be eligible for
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
														<td class="text"><table width="60%" border="0"
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
	</div>
</div>

<select id="ownership" style="display: none">
	<option selected="" value=""></option>
	<option value="5">Owned</option>
	<option value="6">Contractor</option>
	<option value="7">Loan</option>
	<option value="8">Transfer</option>
	<option value="9">Test</option>
</select>

<!--  Added by Rahul Shah - Start -->
	<!-- New PopUp Screen opens up for closing the lasts allocation and opening new allocation in same project. -->
	
<div class="modal fade" tabindex="-1" role="dialog"	aria-labelledby="allocationModal" id="allocationModal" data-backdrop="static">
	<div class="modal-dialog" role="document"  style="width:730px;">
	<form class="clear" id="newAllocationModal" name="newAllocationModal" onSubmit=" event.preventDefault(); saveNewAllocationFromPopUp(this)">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="allocationHeader">Change Allocation</h4>
			</div>
			<div class="modal-body">
			 
				<div class="row"> 
				
						 <input type="hidden" class="form-control" name="employeeId.employeeId" data-name="empId" value="" />
						 <input type="hidden" class="form-control" name="currentAllocId" data-name="currentAllocId" value="" />
					
					<div class="allocation_update">
						<h5 class="col-xs-12 clear"><strong>Current Allocation</strong></h5>
						
						<div class="form-group col-xs-3">	
							<label class="required">Current Alloc Type:</label>
							<input class="form-control event-none" required="required" name="currentAllocType" id="currentAllocType" data-name="currentAllocType" value="" type="text" readonly="readonly"/>
						</div>
						
						<div class="form-group col-xs-3">	
							<label class="required">Current Allocation Status:</label>
							<input class="form-control event-none" required="required" id="currentOwnershipName" name="currentOwnershipName"  data-name="currentOwnershipName" value="" type="text" readonly="readonly"/>
						</div>
						
						<div class="form-group col-xs-3">	
							<label class="required">Current Alloc Start Date:</label>
							<input class="form-control event-none" required="required" name="currentAllocStartdate" id="currentAllocStartdate" data-name="currentAllocStartdate" value="" type="text" readonly="readonly"/>
						</div>
						
						<div class="form-group col-xs-3">	
							<label class="required">Current Alloc End Date:</label>
							<input class="fromdatepicker-change-alloc form-control" required="required" name="currentAllocEnddate" id="currentAllocEnddate" value="" type="text" />
						</div>
						
						<div class="form-group col-xs-9">
							<label>Allocation Feedback:</label>
							<textarea class="form-control" id="currentAllocFeedback" maxlength="500" name="currentAllocFeedback" id="currentAllocFeedback" type="text"></textarea>
						</div>
						
					</div>

					<hr class="clear" />
						<h5 class="col-xs-12 clear"><strong>New Allocation</strong></h5>
						<div class="col-xs-3 form-group">
							<label class="required">Employee Name: </label> 
							<input type="text" class="form-control" name="employeeName" data-name="employeeName" value="" readonly="readonly" />
						</div>

						<div class="col-xs-3 form-group">
							<label class="required">Allocation Start Date: </label>
							<input class="form-control event-none" required="required"  name="allocStartDate"  id="allocStartDate" value="" type="text" readonly />
						</div>

						<div class="col-xs-3 form-group">
							<label >Allocation End Date:  </label>
							<input class="form-control"  name="allocEndDate" id="allocEndDate" type="text" autocomplete="off"/>
						</div>


						<div class="col-xs-3 form-group">
							<label>Allocation Remark: </label> <input type="text" class="form-control" name="allocRemarks" value="" />
						</div>
						
						<div class="col-xs-3 form-group">
							<label class="required">Allocation Type: </label> 
							<div class="positionRel">
								<select class="required comboselect check" id="selectAllocationType" name="allocationTypeId.id" onchange="onAllocationTypeChange(event)">
								<option value="-1" selected="selected">Please select</option>
									<c:forEach var="allocationtype" items="${allocationtypes}">
										<option value="${allocationtype.id}">${allocationtype.allocationType}</option>
									</c:forEach>
								 </select>
							</div>
						</div>
						
						<div class="col-xs-3 form-group">
							<label class="required">Is Primary Project: </label>
							<div class="positionRel">
							 <select class="required comboselect check" id="curProj" name="curProj">
							 	<option value="-1" selected="selected">Please select</option>
								<option value="false">No</option>
								<option value="true">Yes</option>
							</select>
							</div>
						</div>
						
                     <div class="col-xs-3 form-group">
							<label class="required">Allocation Status: </label> 
							<div class="positionRel">
							<select class="disabledSelectedBox form-control" id="selectOwnership" name="ownershipId.id" data-name="ownershipId">
								<option value="5">Owned</option>
								<option value="6">Contractor</option>
								<option value="7">Loan</option>
								<option value="8">Transfer</option>
						   </select>
							 </div>
						</div>

					<div class="col-xs-3 form-group">
							<label class="required">Timehrs Manager: </label> 
							<div class="positionRel">
							<select class="required comboselect check" id= "behalfManager" name="behalfManager">
							<option value="-1" selected="selected">Please select</option>
								<option value="false">No</option>
								<option value="true">Yes</option>
							</select>
							</div>
						</div>

						<div class="col-xs-3 form-group">
							<label>Allocation Percentage: </label> <input type="text" class="form-control" data-name="allocPercentage" name="allocPercentage" id="allocPercentage" onchange="onAllocationPercentageChange()" value="0" />
						</div>
						
						<div class="col-sm-12 check-box-message">
							<p><input type="checkbox" id="dltTimeSheet" name="dltTimeSheet"/> 
							 Check if you want to delete all submitted timesheet after selected end date.
							</p>
						</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" onclick="closeAllocationModal()">Close</button>
				<button type="submit" class="btn btn-primary" id="saveModel">Save</button>
			</div>
		</div>
		</form>
	</div>
</div>

<!--  Added by Rahul Shah - End -->

<script type="text/javascript" charset="utf-8">

$( ".ui-combobox").hide();
</script>

<script type="text/javascript" charset="utf-8">
var DELAY = 700, 
clicks = 0, 
timer = null;
clickable = true;
var listActiveOrAll;
    $(document).ready(function () {
        $("#dialog").dialog({ autoOpen: false });
        $("#dialog" ).dialog( "option", "modal", true );
        $("#dialog" ).dialog( "option", "title", "Resource To Copy" );
        $("#dialog" ).dialog( "option", "buttons", [ { text: "Allocate", click: function() {
        	
        	var id=document.getElementById('copyRowId').value;
    		var employeeId=document.getElementById('copyResourceId').value;
    		var selectedprojectId=projectId;
    		var multiObjs = document.getElementById('listMenu').options;
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
    		startProgress();
    	    var selectedResourcesIdArray = selectedResourceIds.split(',');
    		 
    		
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
    						    },
    						    error : function(data){
    						    	stopProgress();
    						    	showError("Error");
    					     		flag = false;
    						    }
    				   });
    		    	if(flag){ 
    		    		$.fancybox({ 
     		  				autoSize : false,
     		  				closeClick : false,
     		  				autoDimensions : true,
     		  				transitionIn : 'fade',
     		  				openEffect : 'easingIn',
     		  				closeEffect : 'easingOut',
     		  				type : 'iframe',
     		  				href : '${copiedResourceAllocationList}'+selectedResourcesIdArray,'width' : '100%',	preload : true,
     		  				beforeShow : function() {
     		  					var thisH = this.height - 35;
     		  					$(".fancybox-iframe").contents().find('html').find(".midSection").css('height', thisH);	},
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
     		  										 	error: function(errorResponse){
     		  										   				showError("Error");				 	    	
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
     		  											
     		  		  });
    		    		
    		    	}
    		    	else {
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
								 	error: function(errorResponse){
								   		showError("Error");				 	    	
								 	}
								 });
    		 	   }		
    		     		stopProgress();
    		     	},
    		     	error: function(errorResponse)
    		 	    {
    		 	    	showError("Error");
    		 	    }
    		 });
    		 $( this ).dialog( "close" );
    		
    		 
    		 
    		$( ".MaintenanceTab" ).hide();
    		$( "#selectedEmployeeName" ).hide();
    		/**End - Added Rcheck to fix Bug #204 */
    		
    		multiObjs='';
    		selectedResourceIds='';
            
            } } ] );
        $("#dialog" ).dialog({ closeText: "hide" });
        $("#dialog" ).dialog({height: 350, width: 910});
        $("#dialog" ).dialog({ dialogClass: "alert" });
        $("#dialog" ).dialog( "option", "hide", { effect: "explode", duration: 1000 } );
        $("#dialog" ).dialog( "option", "resizable", false );
        $("#dialog" ).dialog({ show: { effect: "explode", duration: 800 } });
        $("#dialog").dialog({
    	open: function( ) {
    	}
    });
        
     $('#allocStartDate, #allocEndDate, #currentAllocStartdate, #currentAllocEnddate').datepicker({changeMonth: true,changeYear: true, dateFormat: "dd-M-yy"});
    
    $('#currentAllocEnddate').datepicker('setDate', 'today'); 
	var nextDayDate = $('#currentAllocEnddate').datepicker('getDate', '+1d');
	nextDayDate.setDate(nextDayDate.getDate() + 1);
	$('#allocStartDate').datepicker('setDate', nextDayDate);       
	
    });
    
   function move_item(from, to)
    {
      var f;
      var SI; 
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
  function searchEmployee(){
	 
	  var searchEmp=$('#searchEmp').val();
	  $('#pdo').find('option').remove();
	  if(searchEmp==""){
		  var option = '';
		  
  		 $.each(abc, function (i, n) {
  		    option = '<option value="' + n[0] + '">' + n[1]+" "+n[2] + " [" +n[3]+"]" + '</option>';			     		
  		$('#pdo').append(option);
  		});
	  }else{
	  $.each(abc, function (i, n) {
		  var empName=n[1]+" "+n[2]+" "+n[3];
		  	if (empName.toLowerCase().indexOf(searchEmp.toLowerCase()) >= 0){
		  		var option = '';
		    option = '<option value="' + n[0] + '">' + n[1]+" "+n[2] +" ["+n[3] +"]"+ '</option>';
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
  
  function validationOnEdit(){
	  	var startDate=document.getElementById("allocStartDate").value;
		var endDate=document.getElementById("allocEndDate").value;		
		 
		var dateOfJoiningValue = dateOfJoin; //global variable hence, can be used.
		var projectKickOffDate= document.getElementById("projectKickOffDateHidden").value;
		var projectEndDate = document.getElementById("projectEndDateHidden").value;		
		var flag = true;
		
		
		if(startDate == "" || startDate == undefined){
			showError("Start Date Cannot be blank");
			return;
		}
		flag = validDates1(startDate, endDate);
		if(!flag){
			showError("End Date must be greater than the Start Date");
			return;
		} 
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
		if(addNewAlloc == true){
			if((dateOfJoin != "" && dateOfJoin != undefined) && (dateOfJoiningGlobal =="" || dateOfJoiningGlobal == undefined)){
			flag = validDatesForJoining(dateOfJoin,startDate);
			if(!flag){
				//var dateSplit = dateOfJoiningGlobal.split("-"); 
				//var dateObjendDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);	
				var error = "Resource Cannot be allocated before  " + dateOfJoin + "  Joining Date "; 
				 showError(error);
					return;	
			 }
			}
			if((dateOfJoiningGlobal != "" && dateOfJoiningGlobal != undefined) && (dateOfJoin =="" || dateOfJoin == undefined)){
				flag = validDatesForJoining(dateOfJoiningGlobal,startDate);
				if(!flag){												
					var error = "Resource Cannot be allocated before  " + dateOfJoiningGlobal + "  Joining Date "; 
					 showError(error);
						return;	
				 }
				}
			if((dateOfJoiningGlobal != "" && dateOfJoiningGlobal != undefined) && (dateOfJoin != "" && dateOfJoin != undefined)){
				flag = validDatesForJoining(dateOfJoiningGlobal,startDate);
				if(!flag){												
					var error = "Resource Cannot be allocated before  " + dateOfJoiningGlobal + "  Joining Date "; 
					 showError(error);
						return;	
				 }
				}
		}else{
			if((dateOfJoin != "" && dateOfJoin != undefined) && (dateOfJoiningGlobal =="" || dateOfJoiningGlobal == undefined)){
				flag = validDatesForJoining(dateOfJoin,startDate);
				if(!flag){
					var error = "Resource Cannot be allocated before  " + dateOfJoin + "  Joining Date "; 
					 showError(error);
						return;	
				 }
				}
				if((dateOfJoiningGlobal != "" && dateOfJoiningGlobal != undefined) && (dateOfJoin =="" || dateOfJoin == undefined)){
					flag = validDatesForJoining(dateOfJoiningGlobal,startDate);
					if(!flag){												
						var error = "Resource Cannot be allocated before  " + dateOfJoiningGlobal + "  Joining Date "; 
						 showError(error);
							return;	
					 }	
					}
				if((dateOfJoiningGlobal != "" && dateOfJoiningGlobal != undefined) && (dateOfJoin != "" && dateOfJoin != undefined)){
					flag = validDatesForJoining(dateOfJoiningGlobal,startDate);
					if(!flag){												
						var error = "Resource Cannot be allocated before  " + OfingGlobal + "  Joining Date "; 
						 showError(error);
							return;	
					 }
					}				 
			}
		return flag;
  	}
 /**Utility function to determine if the passed date is a future date ? */
			function isFuturedate(dateString){
				var browserDate = dateConverToNewDateBrowserCompatible(dateString);
				if(browserDate){
					if(new Date(browserDate) > new Date()){
				    	return true;
					}else{
				   		return false;
				    }
				}else{
					console.log('Please pass a date string to isFuturedate() method');
				}
 	}
 
		$('#currentAllocEnddate').change(function() {
		    var nextDayDate = $('#currentAllocEnddate').datepicker('getDate', '+1d');
		    nextDayDate.setDate(nextDayDate.getDate() + 1);
		    $('#allocStartDate').datepicker('setDate', nextDayDate);
		});
		
		/* Added by Rahul Shah - Start*/
	
		/** Belowe function SaveNewAllocationFromPopUp() ends the privious allocation and if timesheet exist 
		after the allocation end date, then future timesheet will be deleted based on user inputand.
		parallelly new allocation will be opened for same project. */
		
		var saveAllocationReq = {};
		
		function saveNewAllocationFromPopUp(form) {

			var formArr = $(form).serializeArray();
			
			$.each(formArr, function(i, obj) {
				if(obj.name.indexOf(".") > -1){
					var name0 = obj.name.split('.')[0] || '';
					var name1 = obj.name.split('.')[1] || '';
					
					saveAllocationReq[name0] = {};
					saveAllocationReq[name0][name1] = obj.value;
				} 
				else {
					saveAllocationReq[obj.name] = obj.value
				}
				
			});
			
			var preAllocStartDate = saveAllocationReq['currentAllocStartdate'];
			var preAllocEndDate = saveAllocationReq['currentAllocEnddate'];
			
			var isValidate = validationForNewAlloc(preAllocStartDate,preAllocEndDate);
			if(!isValidate){
				return;
			}
			
			var allocStartDate = saveAllocationReq['allocStartDate'];
			var allocEndDate = saveAllocationReq['allocEndDate'];
			
			isValidate = validationForNewAlloc(allocStartDate,allocEndDate);
			if(!isValidate){
				return;
			}
			var isValidPercentage = onAllocationPercentageChange();
			if (!isValidPercentage) {
				return false;
			}
			var allocationType = saveAllocationReq['allocationTypeId']['id'];

			if(allocationType == undefined || allocationType == "" || allocationType == "-1"){
				showError("Please Select Allocation Type");
				return;
			}
			
			var curProj = saveAllocationReq['curProj'];
			
			if(curProj == undefined || curProj == "" || curProj=="-1"){
				 showError("Please Select Primary Project"); 
				return;
			}
			
			if(curProj == 'true')
				saveAllocationReq['curProj']=true;
			else
				saveAllocationReq['curProj']=false
				
			saveAllocationReq['isDeleteTimeSheet'] = false;
			
			var behalfManager = saveAllocationReq['behalfManager'];
			
			if(behalfManager == undefined || behalfManager == "" || behalfManager == "-1"){
				showError("Please Select Timehrs Manager");
				return;
			}

			if(behalfManager == 'true')
				saveAllocationReq['behalfManager'] = true;
			else
				saveAllocationReq['behalfManager'] = false;
			
			var projectId = document.getElementById("projectIdHidden").value;
			
			saveAllocationReq['projectId'] = {} ;
			
			saveAllocationReq['projectId']['id'] = projectId;
			
			saveAllocationReq['resourceType'] = 'Internal';
			
			if(allocEndDate == "" || allocEndDate == undefined)
				saveAllocationReq['allocEndDate'] = null;
			
			if(preAllocEndDate != null && preAllocEndDate != '') {
			
				if($('input#dltTimeSheet[type="checkbox"]').prop("checked") == true){
			   		saveAllocationReq['isDeleteTimeSheet'] = true;
			    }

				var employeeId = saveAllocationReq['employeeId']['employeeId'];
				var oldResourceAllocId = saveAllocationReq['currentAllocId'];
				
				var allocPercentage = document.getElementById("allocPercentage").value.trim();
				saveAllocationReq['allocPercentage'] = allocPercentage;
				
				var flag;
					
				startProgress();
				 $.ajax({
						type: 'GET',
						dataType: 'json',
					    url: "projectallocations/checkForExistingAllocation",
					    contentType: "application/json; charset=utf-8",
					    async:false,
					    data : "oldResourceAllocId=" + oldResourceAllocId  + "&employeeId=" + employeeId + "&allocStartDate=" + allocStartDate + "&allocEndDate=" + allocEndDate + "&projectId=" + projectId+
					    "&find=existingAllocation",
					    success: function(data) {
					    	if(data.openflag=="true"){
					    		flag = true;
					    	}
					    	else{
					    		stopProgress();
					     		var obj = jQuery.parseJSON(data.responseText);
					     		showError(obj.message);
					    		flag = false;
					    	}
					    },
					    error : function(data){
					    	stopProgress();
				     		var obj = jQuery.parseJSON(data.responseText);
				     		showError(obj.error);
				     		flag = false;
					    }
				});
				
				 if(flag) {
					 
					 var jsonData = JSON.stringify(saveAllocationReq); 
					 $.ajax({
							type: 'POST',
						    url: "projectallocations/saveNewAllocationFromPopUp",
						    contentType: "application/json; charset=utf-8",
						    async:false,
						    data : jsonData,
						    success: function(data) {
						    	stopProgress();	
								$('#allocationModal').modal('hide');
								lastRowSelectedForAllocationChange = null;
								$("#newAllocationModal").trigger("reset");
								var successMsg ="Project allocation detail have been saved successfully"; 
								showSuccess(successMsg);
								changeAllocationFlag = false;
								getProjectAllocationById(projectId);
						    },
						    error : function(data){
						    	stopProgress();
						    	var obj = jQuery.parseJSON(data.responseText);
						    	showError(obj.error);
						    }
					});
					 
				 }
			}
		};
	
	
	 function validationForNewAlloc(allocStartDate,allocEndDate) {
		  
		    var startDate = allocStartDate;
			var endDate = allocEndDate;		
			
			var dateOfJoiningValue = dateOfJoin; //global variable hence, can be used.
			var projectKickOffDate = document.getElementById("projectKickOffDateHidden").value;
			var projectEndDate = document.getElementById("projectEndDateHidden").value;		
			var flag = true;
			
			if(startDate == "" || startDate == undefined){
				showError("Start Date Cannot be blank");
				return;
			}
			flag = validDates1(startDate, endDate);
			if(!flag){
				showError("End Date must be greater than the Start Date");
				return;
			} 
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
			
			if((dateOfJoin != "" && dateOfJoin != undefined) && (dateOfJoiningGlobal =="" || dateOfJoiningGlobal == undefined)){
			flag = validDatesForJoining(dateOfJoin,startDate);
			if(!flag){
				var error = "Resource Cannot be allocated before  " + dateOfJoin + "  Joining Date "; 
				 showError(error);
					return;	
			 }
			}
		
			if((dateOfJoiningGlobal != "" && dateOfJoiningGlobal != undefined) && (dateOfJoin =="" || dateOfJoin == undefined)){
				flag = validDatesForJoining(dateOfJoiningGlobal,startDate);
				if(!flag){												
					var error = "Resource Cannot be allocated before  " + dateOfJoiningGlobal + "  Joining Date "; 
					 showError(error);
						return;	
				 }
				}
			if((dateOfJoiningGlobal != "" && dateOfJoiningGlobal != undefined) && (dateOfJoin != "" && dateOfJoin != undefined)){
				flag = validDatesForJoining(dateOfJoiningGlobal,startDate);
				if(!flag){												
					var error = "Resource Cannot be allocated before  " + dateOfJoiningGlobal + "  Joining Date "; 
					 showError(error);
						return;	
				 }
			}
	
			return flag;
		}
	 
	 function closeAllocationModal() {
		 $('#allocationModal').modal('hide')
		 $("#newAllocationModal").trigger("reset");
		 lastRowSelectedForAllocationChange = null;
	 }
	
	 /* Added by Rahul Shah - End*/
	 
	
	 
</script>
<!--Added by Pratyoosh Tripathi -->

<div class="notification-bar">
	<!-- <div class="close closeIconPosition">close</div> -->
	<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
	<!-- <div class="notification-text">Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.</div> -->
	<sec:authorize
		access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_HR')">
		<div class="notification-text">You can add or allocate a
			resource to a project clicking on its Project id.</div>
	</sec:authorize>
	<sec:authorize access="hasAnyRole('ROLE_MANAGER')">
		<div class="notification-text">You can only view the resources
			allocated to a project clicking on a particular Project Id but unable
			to add.</div>
	</sec:authorize>
	<sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER')">
		<div class="notification-text">You can add or view the resources
			allocated to a project clicking on its particular Project Id.</div>
	</sec:authorize>

</div>
<div class="notification-bar notification2">
	<!-- <div class="close closeIconPosition">close</div> -->
	<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
	<!-- <div class="notification-text">Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.</div> -->
	<sec:authorize
		access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_HR')">
		<div class="notification-text">All resources are available in
			the employee field of project for search</div>
	</sec:authorize>
</div>

<style>
.notification-bar {
	width: 400px;
	background: rgba(136, 187, 193, .8);
	padding: 10px;
	position: fixed;
	bottom: 30px;
	right: 25px;
}

.notification2 {
	width: 400px;
	background: rgba(30, 159, 180, .8);
	padding: 10px;
	position: fixed;
	bottom: 100px;
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

#newAllocationModal .ui-combobox {
    width: 100%;
}

#newAllocationModal  .ui-combobox-input {
	width: 100% !important;
    height: 25px;
}

#newAllocationModal .ui-button.uiDropDownArrow {
    right: 1px;
    width: 24px;
    height: 22px;
}

/* #newAllocationModal .positionRel {
    margin-top: 6px;
} */

/* #newAllocationModal .modal-body {
    height: 280px;
} */

.event-none {
	pointer-events: none;
}

#newAllocationModal .ui-combobox input {
font-size:12px !important;
    padding-left: 5px !important;
}

#toast-container {
    z-index: 99999;
}
.toasterBgDiv {
    z-index: 9999;
}
textarea.form-control {
	border-radius: 5px;
}

#newAllocationModal .required:before{
  content:"*";
  color:red
}
</style>

<script type="text/javascript" charset="utf-8">
$( document ).ready(function() {
	
	$('[data-toggle="tooltip"]').tooltip();
	
	setInterval(function(){ $('[data-toggle="tooltip"]').fadeToggle(500); }, 1000);
	
	<%Boolean flag = false;
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
		return false;
	}
	if (!allocPercentage) {
		allocPercentage = "0";
	} else if (allocPercentage > 100){
		showError("\u2022 Allocation Percentage should be <br /> between (0-100).");
		return false;
	}
	return true;
}


</script>
<!-- Added by Pratyoosh Tripathi -->
<Script Language="JavaScript"> 
var objappVersion = navigator.appVersion; 
var objAgent = navigator.userAgent; 
var objbrowserName = navigator.appName; 
var objfullVersion = ''+parseFloat(navigator.appVersion); 
var objBrMajorVersion = parseInt(navigator.appVersion,10); 
var objOffsetName,objOffsetVersion,ix;
 </script>
 
  <!-- Current Date format like (01-Jan-2019) -->
 <script>
var today = new Date().toLocaleDateString('en-GB', {
	    day : 'numeric',
	    month : 'short',
	    year : 'numeric'
	}).split(' ').join('-');
 
 </script>
