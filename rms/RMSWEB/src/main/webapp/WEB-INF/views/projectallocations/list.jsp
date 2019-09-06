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
	<spring:url value="/projectallocations/findOpenAllocationsOfCopiedResource/"
	var="copiedResourceAllocationList" />
<script src="${multiselect_js}" type="text/javascript"></script>

<spring:url
	value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}"
	var="multiselect_filter_js" />

<script src="${multiselect_filter_js}" type="text/javascript"></script>


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

#projectallocationTableId_wrapper, #projectallocationTableId_wrapper .dataTables_scrollHeadInner
	 {
	width: 100% !important;
}

#projectallocationTableId {
	table-layout: fixed;
}

#projectallocationTableId thead th {
	width: 120px;
}

#projectallocationTableId td {
	word-wrap: break-word;
}

#projectallocationTableId_wrapper .dataTables_scrollBody {
	max-height: 240px;
}

#projectallocationTableId_wrapper {
	/* margin-top:-35px; */
	
}

.proj_alloc_note_msg {
	margin-left: 650px;
	display: inline-block;
	text-align: justify;
	font-size: 12px;
}

.proj_alloc_resourcetocopy_msg {
	display: inline-block;
	text-align: justify;
	font-size: 12px;
	width: 570px;
	vertical-align: middle;
	margin-top: 6px;
}
</style>

<script>
var global = null;
var addNewAlloc = false;
var editAlloc = false;
var projectN = "";
var projectId = "";
var projectAllocationTable;
var dateOfJoin;
var dateOfProjectEnd;
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
</script>

<script>
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

$(".projectDetails").fancybox({ 
	fitToView	: true,
	autoSize	: true,  
	//closeClick	: true, commented by Neha
	//added by Neha - start
	closeClick  : false, // prevents closing when clicking INSIDE fancybox
	 helpers     : { 
	        overlay : {closeClick: false} // prevents closing when clicking OUTSIDE fancybox
	    },
	  //added by Neha - end
	type : 'ajax'
}); 

</script>
<!-- Delete changes done by Neha -start -->
<script id="projectAllocationRows" type="text/x-jquery-tmpl">
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
								<a href="projectallocations/readonlyProjectDetails/{{>employeeId.employeeId}}" class="projectDetails" >
								{{>employeeId.yashEmpId}}
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
								
			                </td>
			                <td align="center">                             
								{{>allocationTypeId.allocationType}}
			                	<input type="hidden" id="allocationTypeId" value="{{>allocationTypeId.id }}"/>
			                	<input type="hidden" class="allocatEndDate" value="{{>allocEndDate}}"/>
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
									
						
 					<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
						{{if employeeId.isReleasedIndicator}}
							 <td align="center" >
								<a class="edit" id="editRow{{:#index}}" class="removeInactive" href="">Edit</a>
				 </td>
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
							
							<td>
								&nbsp;
							<a href="javascript:void(0);" onclick="copyDataRow({{>id}},{{:#index}},{{>employeeId.employeeId}},'{{>allocStartDate}}');">Copy</a>
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
                        //   alert("Error Occured. \nText: " + textStatus + "\nError: " + errorThrown + "\nHTTP Status: " + XMLHttpRequest.responseText);
                       }
                    });   
    } catch (err) {
       txt="There was an error on this page.\n\n";
       txt+="Error description: " + err.description + "\n\n";
       txt+="Click OK to continue.\n\n";
     //  alert(txt);    
    }
}  

var lastactivityDate;
var firstActivityDate;
 
    	var saveOpen = false;
		function getInputValue(str){
			/* var index = str.indexOf("<input");
			if(index < 0) return null;
			str = str.substr(index , str.length);
			return $(str).val(); */
			// for internet explorer
			var index1 = str.indexOf("<INPUT");
			
			// for mozilla
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
		 	         //   $noty.close();
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
		 	      //     noty({dismissQueue: true, force: true, layout: 'top', theme: 'defaultTheme', text: 'You clicked "Ok" button', type: 'success'});
			 	       	//startProgress();
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
										     		//getProjectAllocationById(id);
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
		 	        	//Changed to solve bug# 269 ****Start*****
			 	       // $noty.close();
		 	        	  $.noty.closeAll();
		 	        	 $('.toasterBgDiv').remove();
		 	        	//****End*****//
		 	        //     noty({dismissQueue: true, force: true, layout: 'top', theme: 'defaultTheme', text: 'You clicked "Cancel" button', type: 'error'});
		 	         }
		 	        }
		 	      ],
		 	   closeWith:['Button']
		 	    });
		}
		function copyDataRow(id,index,employeeId,alocationStartDate){
			document.getElementById('copyRowId').value=id;
			document.getElementById('copyResourceId').value=employeeId;
			document.getElementById('searchEmp').value='';
			$("#dialog").dialog({
				  minWidth: 300
			}).dialog('open');
			
			$.ajax({
				type: 'POST',
		        url: 'resources/getEligibleResourcesForCopy/'+employeeId+'/'+projectId,
		        data: {"alocationStartDate":alocationStartDate},
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
		
		function refreshGrid(){
		}
		
		var oTable;
		
			//changes done by isha starts....
			//Ajax Call on selection of resource
			function ownershipResource (obj){
				var rid= $(obj).val();
				
				$.ajax({
			        url: '/rms/loanAndTransfer/loadResourceData',
			     	data: {"resourceid":rid},
			     	success: function(response) {
			     		$('#ownershipId').val(response.ownership.id);
			     		dateOfJoin =response.dateOfJoining;
			     		$(obj).parent().parent().find('td').eq(4).html( $('#ownership [value="'+response.ownership.id+'"]').html() );
			     	}
		     	});
			}
			
			
			
$(document).on('change','.changeTest',function(){
	
	
});
	
	function editRow ( oTable, nRow ){
			var changedAllocEnddate= null;
			var changedAllocStartdate= null;
			aData = oTable.fnGetData(nRow);			
			global = nRow;
			jqTds = $('>td', nRow);
		   
			if(jqTds.length < 1)return;
			
			/* added by neha 
			   for datepicker issue for start date and end date,
			   If kickOffDate is null then in calender dates should be shown from date of joining.
			   If kickOffDate is not null then it is checked whether kickOffDate is greater or dateOfJoining is greater, it should set the greater date in allocationstartdate
			  */
			  var allocationStartDate = null; 
			  var projectEndDate=null, projectKickOffDate=null, dateOfJoining=null;
			  if(!addNewAlloc){
		  	  if(document.getElementById('endDate').value!="" && document.getElementById('endDate').value!=null){
				projectEndDate = new Date(document.getElementById('endDate').value);
				projectEndDate = (projectEndDate.getMonth() + 1) + '/' + projectEndDate.getDate() + '/' +  projectEndDate.getFullYear(); 
			   }
			   
			  projectKickOffDate= new Date(document.getElementById('kickOff').value);
	          if(projectKickOffDate !='')
	        	projectKickOffDate = (projectKickOffDate.getMonth() + 1) + '/' + projectKickOffDate.getDate() + '/' +  projectKickOffDate.getFullYear(); 
	          
	          dateOfJoining = new Date(document.getElementById('dateOfJoining').value);
			  dateOfJoining = (dateOfJoining.getMonth() + 1) + '/' + dateOfJoining.getDate() + '/' +  dateOfJoining.getFullYear(); 
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
						jqTds[2].innerHTML = "<select  class='comboselect changeTest' onchange='ownershipResource(this)' id =\"employeeNameEditId"+curIndex+"\" name=\"employeeId.employeeId\">"+ 
										<c:forEach var="resource" items="${resources}">
											"<option value=\"${resource.employeeId}\">${resource.employeeName}[${resource.yashEmpId}]</option>"+
										</c:forEach>
									 +"</select>";	 	
								 
				}
				
				else{
					
					jqTds[2].innerHTML = '<input id="employeeId.employeeId" type="hidden" value="'+curIndex+'">'+aData[2]; 
					
					
				}
			//jqTds[0].innerHTML = aData[0];
			
			//changes done by isha ends here...
			
			     	
			     			 jqTds[4].innerHTML = aData[4]; 
			     			 
					
			jqTds[5].innerHTML = '<input class="fromdatepicker requir" name="allocStartDate"  id="allocStartDate" type="text" readonly="readonly" value="'+ aData[5] +'">';
			
			dateOfProjectEnd=aData[6];
			
			jqTds[6].innerHTML = '<input class="todatepicker" name="allocEndDate" id="allocEndDate" type="text" readonly="readonly" value="'+ aData[6] +'">';
			/*var curIndex = getInputValue(aData[4]);	
			 if(addNewAlloc==true){
			jqTds[4].innerHTML = "<select class='comboselect' id =\"employeeNameEditId"+curIndex+"\" name=\"employeeId.employeeId\">"+ 
									<c:forEach var="resource" items="${resources}">
										"<option value=\"${resource.employeeId}\">${resource.employeeName}[${resource.yashEmpId}]</option>"+
									</c:forEach>
								 +"</select>";	 	}
			
			else{
				jqTds[4].innerHTML = '<input id="employeeId.employeeId" type="hidden" value="'+curIndex+'">'	+aData[4];
				
			} */
			jqTds[7].innerHTML ='<textarea id ="allocRemarks" name="allocRemarks" cols="10" rows="0">'+aData[7]+'</textarea>'  ;	
			
			jqTds[8].innerHTML ='<select class="comboselect" id ="currentProjectEditId'+getInputValue(aData[8])+'" name="curProj"><option value="true">Yes</option><option value="false">No</option></select>'
			
			//jqTds[6].innerHTML ='<select class="comboselect" id ="billableEditId'+getInputValue(aData[6])+'" name="billable"><option value="true">Yes</option><option value="false">No</option></select>';
			
/* 			jqTds[8].innerHTML ='<input id ="allocHrs" class="requir" value="'+aData[8]+'" name="allocHrs" type="text" onBlur="promptForAllocationHrs(this);" >';
 */			
			/* jqTds[9].innerHTML ='<select class="comboselect" id ="rateId' + aData[9] +'" name="rateId.id">'+
									<c:forEach var="rates" items="${rates}">
										'<option value="${rates.id}">${rates.billingRate}</option>'+
									</c:forEach>
								+'</select>'; */
								
			jqTds[9].innerHTML ='<select class="comboselect" id ="behalfManager'+getInputValue(aData[9])+'" name="behalfManager"><option value="true">Yes</option><option value="false">No</option></select>'; 
			
			/* 	jqTds[10].innerHTML = '<select class="comboselect" id ="teamId' + getInputValue(aData[10]) +'" name="team.id">'+
				'<option value="-1">Select</option>'+
				<c:forEach var="teams" items="${teams}">
				'<option value="${teams.id}">${teams.teamName}</option>'+
			</c:forEach>
		+'</select>';	 */
													
			jqTds[10].innerHTML ='<a id="save" class="edit" href="#"  >Save</a> / <a id="cancel" class="cancel" href="#" >Cancel</a>';
			jqTds[11].innerHTML =aData[11];
			jqTds[12].innerHTML =aData[12];
			
			//populate different select boxes
			var value = getInputValue(aData[3]);
					
			$("#employeeNameEditId"+curIndex).val(curIndex);
				
				//new
			value= getInputValue(aData[8]);
			if(aData[8].toLowerCase() == 'yes'){
				$("#currentProjectEditId"+value).val("true");	
			}else{
				$("#currentProjectEditId"+value).val("false");
			}
			/* 
			value= getInputValue(aData[6]);
			if(aData[6].toLowerCase() == 'yes'){
				$("#billableEditId"+value).val("true");	
			}else{
				$("#billableEditId"+value).val("false");
			} */
			
			//Start Added to solve random issue
			value= getInputValue(aData[9]);
			if(aData[9].toLowerCase() == 'yes'){
				$("#behalfManager"+value).val("true");	
			}else{
				$("#behalfManager"+value).val("false");
			}

			//End Added to solve random issue
			
			
			/* $("#rateId"+aData[9]).val(aData[9]); */
			var index = aData[2].indexOf("<input");
			var strArray = aData[2].substr(index , aData[2].length).split(">", 8);
			var strId="";
			
			if(addNewAlloc==false){
		//	var strEmployeeId = $(strArray[0] +">").val();
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
				 
				if (lastactivityDate == null || lastactivityDate == "") {
					jqTds[3].innerHTML = '<select class="comboselect" id ="allocTypeEditId'+getInputValue(aData[3])+'" name="allocationTypeId.id">'+
					<c:forEach var="allocationtype" items="${allocationtypes}">
						'<option value="${allocationtype.id}">${allocationtype.allocationType}</option>'+
					</c:forEach>
				 '</select>';
				 $("#allocTypeEditId"+getInputValue(aData[3])).val(getInputValue(aData[3]));
				} else{
					jqTds[3].innerHTML =  '<input id ="allocTypeEditId'+getInputValue(aData[3])+'" type="hidden" name="allocationTypeId.id" value="">'+aData[3];
					//jqTds[2].innerHTML = aData[2];
				}	 
	
			//populate datepickers
			// Commented by Neha
			    /* $('.fromdatepicker').datepicker({changeMonth: true,changeYear: true, beforeShowDay:noSaturday,
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
		            	if(changedAllocEnddate==null && firstActDate=="" && aData[5]!="")
					    {
	            		 $(this).datepicker("option", "maxDate", aData[5]);
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
			    $('.todatepicker').datepicker({changeMonth: true,changeYear: true,beforeShowDay: noSundays,yearRange: "2014:2025",
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
			
			  //added by neha for datepicker issue  - start
			    $('.fromdatepicker').datepicker({changeMonth: true,changeYear: true, beforeShowDay:noSaturday,onOpen :function(){},
			    	onClose: function( selectedDate ) {
			    		 //changedAllocStartdate = selectedDate;
		                if(this.value == ""){
			   				 $(this).css("border", "1px solid #ff0000");
			   			 	}
			   			 else
			   				{
			   					 $(this).css("border", "1px solid #D5D5D5");
			   				}
		            },
		            beforeShow: function(){
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
			 
		            			
			    }); 
			 $('.todatepicker').datepicker({changeMonth: true,changeYear: true,beforeShowDay: noSundays,
			    	onClose: function( selectedDate ) {
			    		changedAllocEnddate = selectedDate;
		               
		                
		            },
		            beforeShow: function()
		            {
		            	 if(changedAllocStartdate ==null)
						 {
		            		 $(this).datepicker("option", "maxDate", changedAllocStartdate);
		            		 $(this).datepicker("option", "minDate",aData[5]);
		            		
						 }
		            	 else
		            	 {
		            		 $( this ).datepicker( "option", "maxDate", changedAllocStartdate );
		            		 $(this).datepicker("option", "minDate", aData[5]);
		            	 }
		            }
			    });
			 //added by neha for datepicker issue - end
			    
			$("#allocRemarks").autoGrow();			
			
			containerWidth();
		}
		
		
		function addDataTableSearch(table){
			
				$("#tab2 thead input").keyup( function(){
					oTable.fnFilter( this.value, oTable.oApi._fnVisibleToColumnIndex(oTable.fnSettings(), $("#tab2 thead input").index(this) ) );
				});	
				
				
				$("#tab2 thead input").each( function(i){
					this.initVal = this.value;
				});
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
					//"sDom": 'RC<"clear">lfrtip',
					"sDom": 'RC<"clear">lfrtip<"top">rt<"bottom"ip<"clear">',
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
					"bSortCellsTop": true/* ,
					"fnPreDrawCallback": function() {
					      highlightRow();
				     
				      
				    } */

			} );				
			/*------for adding new row 07 sep 2012--------*/
		}
		$(document).on('click','#example a.cancel', function (e) {
			$("#example tbody tr").find("td").attr("align","center");
			 	//Updated for Issue #46
			 	addNewAlloc=false;
			 	editAlloc = false;
			saveOpen = false;
			//Updated for Issue #46
			e.preventDefault();	
			var nRow = $(this).parents('tr')[0];
			nEditing=nRow;
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
			
			for ( var i=0, iLen=jqTds.length ; i<iLen ; i++ ) {
				oTable.fnUpdate( aData[i], nRow, i, false );
			}
			//oTable.fnDraw();
		}
		
		function saveRow ( oTable, nRow, projectEndRemarks){
			
			var aData = oTable.fnGetData(nRow);
			if(aData == null || aData.length < 1)return;
			var index = aData[2].indexOf("<input");
			var strArray = aData[2].substr(index , aData[2].length).split(">", 12);
			var strProjectId = document.getElementById("projectIdHidden").value;
		
			var allocatedBy ="";
			var strId;
			var index1 ='';
			if(addNewAlloc== true)
				strId = null;
			
			else{
				var allocctypeid =   getInputValue(aData[3]);
				var alloTypeId= "allocTypeEditId"+allocctypeid;
				if( document.getElementById(alloTypeId).value!='')
				index1 =	 document.getElementById(alloTypeId).value;
				else
					//index1  =	 $(aData[2] +">").val();
				index1  =	 getInputValue(aData[3]);
				strId = $(strArray[4] +">").val();
				  //var resourceAllocionId=  strId;
				  allocatedBy =	document.getElementById("allocatedBy.employeeId").value;
			}
			
			var employeeName= aData[5].indexOf("<input");
			
			var sData = $('*', oTable.fnGetNodes()).serializeArray();
			
 				if(index1!=null && index1!='')
 					{
 					sData.splice(0, 1); 
 					sData.push({name:"allocationTypeId.id",value:index1});
 					sData.push({name:"id",value:strId});
 					}
				
			//sData.push({name:"allocationSeq",value:aData[1]});
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
				
				var employeeId=getInputValue(aData[2]);
				sData.push({name:"employeeId.employeeId",value:employeeId});
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
	
			if(editAlloc== false){ 
			var ownershipId=document.getElementById("ownershipId").value;
			sData.push({name:"ownershipId.id",value:ownershipId});
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
			/*  $.ajax({
					type: 'POST',
			        url: 'projectallocations',
			     	contentType: "json",
			     	success: function(data) {
						var id = document.getElementById("projectIdHidden").value;alert('hiiiii');
						getProjectAllocationById(id);
						saveOpen = false;
						addNewAlloc=false;
					 	editAlloc = false;
						nEditing= null;  
						var successMsg ="Project allocation details have been saved successfully"; 
						showSuccess(successMsg);
					 },
			     	error: function(errorResponse)
			 	    {
			 	    	//showError("Project Allocation cannot be  copied.");
			 	    }
			 }); */
			 
			 
			 //US3090: START: Future timesheet delete functionality
			 
			 var resAllocId = document.getElementById("id").value;
			// var weekEndDate = new Date(document.getElementById("allocEndDate").value);
			 
			   //var resAllocId=resourceAllocionId;
			   var weekEndDate =null;
			   var weekEndDate123 =null;
			   var endDate=document.getElementById("allocEndDate").value;
			   if( endDate != null && endDate != ''){
				 weekEndDate= new Date(document.getElementById("allocEndDate").value);
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
						    url: 'projectallocations/getTimesheetStatus/'+resAllocId +"/"+weekEndDate,
						    contentType: "application/json; charset=utf-8",
						    async:false,
						    success: function(data) {
						    	var jsonData1 = JSON.parse(data);	
						    	if(jsonData1.status == "true")
					    		{
						    		$.postData("projectallocations", jsonData, function(data) {
											//var id = document.getElementById("employeeIdHidden").value;
											//getResourceAllocationById(id);
											checkAll('');
											saveOpen = false;
											nEditing= null;
											addNewAlloc = false;
											stopProgress();
											var successMsg ="Project allocation detail have been saved successfully"; 
											showSuccess(successMsg);
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
			  										    url: 'projectallocations/deleteFutureTimesheet',
			  										    async:false,
			  										    //data : {userActivityId: userActivityId},
			  										    data : "userActivityId=" + userActivityId + "&resourceAllocId=" + resAllocId
			  										+ "&weekEndDate=" + weekEndDate123,
			  										    success: function(data) {
			  										    	
			  										    	$.postData("resourceallocations", jsonData, function(data) {
			  													
			  													//var id = document.getElementById("employeeIdHidden").value;
			  													//getResourceAllocationById(id);
			  													checkAll('');
			  													saveOpen = false;
			  													editAlloc=false;
			  													nEditing= null;
			  													stopProgress();
			  													var successMsnehag ="Project allocation detail have been saved successfully"; 
			  													showSuccess(successMsg);
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
         		 
          		}); editAlloc=false;  
			} else{ 
				$.postData("projectallocations", jsonData, function(data) {
					//var id = document.getElementById("employeeIdHidden").value;
					//getResourceAllocationById(id);
					checkAll('');
					saveOpen = false;
					editAlloc=false;
					nEditing= null;
					stopProgress();
					var successMsg ="Project allocation details have been saved successfully"; 
					showSuccess(successMsg);
				 }, 
				 "json"
				);
	       }
		}
			
		if(resAllocId==""){  
			var id=document.getElementById("employeeNameEditIdnull").value;
			 var startDate=document.getElementById("allocStartDate").value;
			
			 if( startDate != null && startDate != '')
				 startDate= new Date(document.getElementById("allocStartDate").value);
			 
			 var alloctionTypeId=document.getElementById("allocTypeEditIdnull").value;
			 if(alloctionTypeId=='' || alloctionTypeId==null){
				 var errorMsg="Please Select Allocation Type.";
				 showError(errorMsg);
				 return;
			 }
			 
			/* $.ajax({
				type: 'GET',
			    url: '/rms/projectallocations/findOpenAllocationsOfResource/'+id,
			    async:true,
			   
			});  */
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
			     		//showError("Transaction failed: " + obj.error);
			     		flag = false;
				    }
				    	 
			});
			
			/* $.postData("projectallocations/checkForExistingAllocation", jsonData, function(data) {
				//var id = document.getElementById("employeeIdHidden").value;
				//getResourceAllocationById(id);
				checkAll('');
				saveOpen = false;
				nEditing= null;
				stopProgress();
				var successMsg ="Project allocation details have been saved successfully"; 
				showSuccess(successMsg);
			 }, 
			 "json"
			); */
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
											href : '${myProfile}'+id+'/'+startDate,
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
													  var href = window.location.pathname;
													  if (href.toLowerCase().indexOf("admindashboard") >= 0 || href.toLowerCase().indexOf("userdashboard") >= 0 ){
													    window.location.reload();
													  }
													  $.postData("projectallocations", jsonData, function(data) {
															var id = document.getElementById("projectIdHidden").value;
															//getProjectAllocationById(id);
															checkAll('');
															saveOpen = false;
															addNewAlloc=false;
														 	editAlloc = false;
															nEditing= null;  
															var successMsg ="Project allocation details have been saved successfully"; 
															showSuccess(successMsg);
														 } ,
														 "json"
														).error(function(data) {
											        });   
														/* saveOpen = false;
														addNewAlloc=false;*/
													 	editAlloc = false; 
													  }
				    });
			}
			else{
				  $.postData("projectallocations", jsonData, function(data) {
						var id = document.getElementById("projectIdHidden").value;
						//getProjectAllocationById(id);
						checkAll('');
						saveOpen = false;
						addNewAlloc=false;
					 	editAlloc = false;
						nEditing= null;  
						var successMsg ="Project allocation details have been saved successfully"; 
						showSuccess(successMsg);
					 } ,
					 "json"
					).error(function(data) {
		        });   
					/* saveOpen = false;
					addNewAlloc=false;*/
				 	editAlloc = false;
			}

			//location.reload(true);
			
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
			//startProgress();
			showEntries = 10;
			containerWidth();
			
			$(".tab_div").hide();
			$('ul.tabs a.listTab').click(function () {
				/* $("td input[type=text]").each(function(){
					var default_value = $(this).prop("defaultValue");
					$(this).val(default_value);
				}); */
				saveOpen = false;
				$(".tab_div").hide().filter(this.hash).show();
				$("ul.tabs a.listTab").removeClass('active');
				$(this).addClass('active');
				$('a[href$="tab2"]').addClass('MaintenanceTab');
				$("#maitainanceId").css("display","none");
				containerWidth();
				return false;
				
			}).filter(':first').click();
		/* 	$('#update').click(function(){
				displayMaintainance();
			}); */
			
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
						 if (projectAllocationTable != null) {
							projectAllocationTable.fnClearTable();
							initTable();

							}
					} */
					if (e.keyCode == 13) {
					       projectAllocationTable.fnFilter( this.value, projectAllocationTable.oApi._fnVisibleToColumnIndex(projectAllocationTable.fnSettings(), $("thead input").index(this) ) );
					    }
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
					}  
			 /* if(this.value != "")
				{
				 projectAllocationTable.fnFilter( this.value, projectAllocationTable.oApi._fnVisibleToColumnIndex(projectAllocationTable.fnSettings(), $("thead input").index(this) ) );
				} */
				});
			
		//Task #1107
				projectAllocationTable = $('#projectallocationTableId').dataTable({
                "bProcessing": true,
                "bServerSide": true,       
                "sAjaxSource": 'projectallocations/list/'+"active",
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
                "sDom": '<"top"lfip>rt<"bottom"ip<"clear">',
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
				    	
				    	
				        var onsiteMag = aData[5];
				        var currentbg = aData[6];
				        var projectKickOffDate = aData[7];
				        var managerReadonly = aData[10];
					 if(onsiteMag == ""){
				    	$('td:eq(4)', nRow).html("N.A");
				    	}
				    if(currentbg == null){
				    	$('td:eq(5)', nRow).html("N.A");
				    }
				    if(projectKickOffDate == null){
				    	$('td:eq(6)', nRow).html("N.A");
				    }
				    <sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER')">
				    if(managerReadonly == true){
				    	 $('td:eq(0)', nRow).html('<a href="return false;" onClick="openMaintainance(\''+aData[0]+'\',\''+aData[2]+'\',\''+aData[7]+'\',\''+aData[8]+'\');return false;">' +
					                aData[1] + '</a>');
					            return nRow;
				    }else{
				    	 $('td:eq(0)', nRow).html('<a href="#" class="copyInactive"  disabled="disabled" >'+  aData[1] + '</a>');
					            return nRow;
				    }
				    </sec:authorize>
				    <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_MANAGER')">
				    $('td:eq(0)', nRow).html('<a href="return false;" onClick="openMaintainance(\''+aData[0]+'\',\''+aData[2]+'\',\''+aData[7]+'\',\''+aData[8]+'\');return false;">' +
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
										sName: "projectKickOffDate",
										sWidth : "",
										bSortable: true
									} ,
									{ 
										sName: "projectEndDate",
										sWidth : "",
										bSortable: true
									} ,
									//{ 
										//sName: "project BU",
										//sWidth : "90px",
										//bSortable: true
									//} ,
									
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
					saveRow(oTable, nEditing,projectEndRemarks);
				}
			});
			
			$('#cancelbtn').on("click", function(){
				$("#feedbackText").val(null);
				$.fancybox.close();
				
			});
			
			function initTable(){
				
				var listActiveOrAll = document.getElementById("listActiveOrAll").value;
				var sAjaxSource ;
			
				if(listActiveOrAll=='All')
   				{
					sAjaxSource = "projectallocations/list/all";
   				 
   				}
   				else{
   					sAjaxSource = "projectallocations/list/active";
   					 }
				   
				
			
				projectAllocationTable.fnDestroy();
			$('#projectallocationTableId').dataTable({
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
                "bRetrieve": true,
                "sDom": '<"projecttoolbar">lfrtip',
                "sDom": '<"top"lfip>rt<"bottom"ip<"clear">',
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
				    	    var onsiteMag = aData[5];
 					        var currentbg = aData[6];
 					        var projectKickOffDate = aData[7];
 											
 					    if(onsiteMag == ""){
 					    	$('td:eq(4)', nRow).html("N.A");
 					    	}
 					    if(currentbg == null){
 					    	$('td:eq(5)', nRow).html("N.A");
 					    }
 					    if(projectKickOffDate == null){
 					    	$('td:eq(6)', nRow).html("N.A");
 					    }
 				            $('td:eq(0)', nRow).html('<a href="return false;" onClick="openMaintainance(\''+aData[0]+'\',\''+aData[2]+'\',\''+aData[7]+'\',\''+aData[8]+'\');return false;">' +
 				                aData[1] + '</a>');
 				            return nRow;
 				        },
				   
			        "aoColumns":  [ 
									{ 
										sName: "id",
										sWidth : "10px",
										bSortable: false,
										bVisible:false
									},
													                  
									{ 
										sName: "projectCode",
										sWidth : "129px",
										bSortable: false
										
									},
									
									{ 
										sName: "projectName",
										sWidth : "140px",
										bSortable: true
										
									},
									{ 
										sName: "customerName",
										sWidth : "115px",
										bSortable: true
									},
									{ 
										sName: "offshoreDelMgr",
										sWidth : "130px",
										bSortable: true
									},
									 
									{ 
										sName: "onsiteDelMgr",
										sWidth : "118px",
										bSortable: true
									},
									{ 
										sName: "BuCode",
										sWidth : "101px",
										bSortable: false
									},
									{ 
										sName: "projectKickOffDate",
										sWidth : "101px",
										bSortable: true
									} ,
									{ 
										sName: "projectEndDate",
										sWidth : "101px",
										bSortable: true
									} ,
									//{ 
										//sName: "projectBU",
										//sWidth : "90px",
										//bSortable: true
									//} ,
									
									
                    ] 
			});
			$("#projectallocationTableId_filter").hide(); 
			$('#projectallocationTableId_info').show();
			} 
			
			 $(document.body).on('change','#listActiveOrAll',function(){
					
				  if(this.value=='All')
		   				{
		   				var oSettings = projectAllocationTable.fnSettings();
		   				oSettings.sAjaxSource  = "projectallocations/list/all";
		   				 projectAllocationTable.fnClearTable();
		   				projectAllocationTable.fnDraw();
		   				 
		   				}
		   				else
		   				{
		   				var oSettings = projectAllocationTable.fnSettings();
		   				    oSettings.sAjaxSource  = "projectallocations/list/active";
		   				 projectAllocationTable.fnClearTable();
		   				projectAllocationTable.fnDraw();
		   				}
				});
			
			 $(document).on("change", '#display', function(ev){
				});
			
			/* "fnDrawCallback"[ function() {
			    $("select.multiselect").multiselect({
			        noneSelectedText: "- select one -",
			        header: false,
			        classes: "multiSelectDd",
			        multiple: false,
			        selectedList: 1
			    });
			  }] */
			
			$('#addNew').click(function (e) {
				addNewAlloc =true;
			
				if(editAlloc== true && addNewAlloc ==true)
					{
						var text="Please edit and save data or cancel it";
						showAlert(text);
						// Added for task # 216 - End
						addNewAlloc = false;
						e.preventDefault();
						return;
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
				var nYashEmpIdStr = nYashEmpId+ '<input type="hidden" id="employeeId" value="' + document.getElementById("employeeIdHidden").value +'"/>' +
				'<input type="hidden" id="id" value=""/>' ;
				var projectName = document.getElementById("projectNameHidden").value;
				
				/**Start - Added check to fix Bug #206 */
				if(null == projectName || projectName == ''){
					var errorMsg ="Please select Project from the List"; 
					showError(errorMsg);
					return;
				}
				/**End - Added check to fix Bug #206 */
				
				var aiNew = oTable.fnAddDataAndDisplay(['',nYashEmpIdStr, '', '','', '','','', '', '', '', 
					'<a class="delete" href="" onclick="deleteDataRow(null);">Delete</a>','']); 
				var nRow = aiNew.nTr;
				editRow( oTable, nRow);
				nEditing = nRow;
				saveOpen = true;
				$( ".comboselect" ).combobox();
				
				 // for removing grid on clicking addnew link
				$("#example tbody tr:first").find("td").find("a.cancel").addClass("delAddedRow");
				// for removing grid on clicking addnew link
			});
			
			function validDates1(fromDate, toDate) {
				var SDate = fromDate;
				var EDate = toDate;
				var endDate = new Date(EDate);endDate.setHours(0, 0, 0, 0);
				var startDate = new Date(SDate);startDate.setHours(0, 0, 0, 0); 
				if(SDate != '' && EDate != '' && startDate>endDate) 
			    	return false;	    	
				return true;
				}
			
			
			function validDatesForJoining(fromDate, toDate) {
				var JDate = fromDate;
				var SDateJ = toDate;
				var endDate = new Date(SDateJ);endDate.setHours(0, 0, 0, 0);
				var startDate = new Date(JDate);startDate.setHours(0, 0, 0, 0); 
				if(JDate != '' && SDateJ != '' && startDate>endDate) 
			    	return false;	    	
				return true;
				}
			
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
									//validation put here.. if fails add return..
									/*------------------------Added By Prasoon------------------------------*/
									var nRow = $(this).parents('tr')[0];
									var otherInputs = $(this).parents('td').siblings().find('input');
									var startDate=document.getElementById("allocStartDate").value;
									var endDate=document.getElementById("allocEndDate").value;
									
									var projectKickOffDate= document.getElementById("projectKickOffDateHidden").value;
									var projectEndDate = document.getElementById("projectEndDateHidden").value;
									
									var flag = true;
									//End Date must be greater than the Start Date check is commented 
									//uncommented for allocating resource for a single day in project
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
										flag = validDatesForJoining(dateOfJoin,startDate);
										if(!flag){
											var error = "Resource Cannot be allocated before  " + dateOfJoin + "  Joining Date "; 
											 showError(error);
											 //showError("Resource Cannot be allocated before Joining Date");
											 //"Resource Cannot be allocated before  " + dateOfJoin + "  Joining Date ";
												return;	
										 } 
									}else{
										flag = validDatesForJoining(dateOfJoiningVal,startDate);
										if(!flag){
											var error = "Resource Cannot be allocated before  " + dateOfJoiningVal + "  Joining Date "; 
											 showError(error);
											 //showError("Resource Cannot be allocated before Joining Date");
											 //"Resource Cannot be allocated before  " + dateOfJoin + "  Joining Date ";
												return;	
										 } 
									}
							 		
									
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
										 var aData = oTable.fnGetData(nEditing);
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
										 }
									    saveRow(oTable, nEditing);
											//saveOpen=false;
											//nEditing = null;
										return false;
//				 							var successMsg ="Resourceallocation details have been saved successfully"; 
//				 							showSuccess(successMsg);
								        }
								}
								else {
									/* No edit in progress - let's start one */		
									
									editRow( oTable, nRow );
									nEditing = nRow;
								}
								$( ".comboselect" ).combobox();
								
								if(status.indexOf('No')>0)
								{
								  $('#behalfManagernull option[value=false]').attr('selected', 'selected');
								  $("#behalfManagernull option:selected").text("No");
								  $("#behalfManagernull").parent("td").find("input").val("No");
								}
								});
			
			
			
			
			
			
			
			$('a[href$="tab2"]').click(function(){
				$("input.search_init").each(function(){
					var default_value = $(this).prop("defaultValue");
					$(this).val(default_value);
				});
				
				 if(oTable != null)oTable.fnClearTable(); 
	  			 document.getElementById("inner").innerHTML="";
			});
			document.getElementById("selectedProjectName").innerHTML= "Maintenance";
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
				$('#projectallocationTableId').show();
				$('#projectallocationTableId_info').show();
				}, 4000);
		}
			

function displayMaintainance(){
	if(clickable != true){
	}
	else{
	$(".proAllocTblData").show();
	$(".proAllocDialog").show();
	$(".proAlloctab_div").show();
	$(".projectAllocationBtnIcon").show();
	 $("#maitainanceId").css("display","inline-block");
	 $("ul.tabs a.listTab").removeClass('active');
	 $(".tab_div").hide().next("#tab2".hash).show();
	 $('a[href$="tab2"]').removeClass('MaintenanceTab');
	 $('a[href$="tab2"]').addClass('active');
	 containerWidth();
	}
}
var vm = {
		getMultiSelectResource:function(){
		    var multiObjs =  $('#multiresource').val();
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
			//setTimeout(displayMaintainance(),3000);
			displayMaintainance();
			//setTimeout(stopProgress(),3000000);
			//stopProgress();
			
}
/* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%  */		
function getProjectAllocationById(id){
	//$.getJSON("projectallocations",{find:"allocationByProjectId" , projectId:id}, showProjectAllocation);
	
      $.ajax({  
        url: "projectallocations",  
        dataType: 'json',  
        data: {find:"ByActiveProjectId" , projectId:id},  
        async: true,  
        success: function(data){  
        	showProjectAllocation(data);
                     },
        error: function(){
               showError('Some Problem Occurred...Cannot display data!!');
               stopProgress();
        }
      }) ; 

}
function showProjectAllocation(data){
	if(oTable != null)oTable.fnClearTable();
	$("#example > tbody:last").append($("#projectAllocationRows").render(data));
	highlightRow();
	addDataTableSearch($("#example"));
	$(".multiSelectDd").multiselect().multiselectfilter();
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
				//document.getElementById('editRow').removeAttribute('href');
				// Commented by Neha
				/* $(".edit").eq(i).addClass("removeInactive"); //commented by neha for edit enable functionality for all  wrt timesheet
				$(".edit").eq(i).click(function () {return false;}); */ //commented by neha for edit enable functionality for all  wrt timesheet
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
</script>

<script>

var oTable;

</script>

<div class="content-wrapper">
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
						<td align="center" colspan="2"><strong>Project Allocation End Feedback:</strong></td>

					</tr>
					<tr>
						<td align="center" colspan="2"><textarea rows="10" cols="60"
								id="feedbackText" maxlength="1000"></textarea></td>
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
				<script>
					displayTableData();
					</script>
				<table id="projectallocationTableId" class="dataTbl">
					<div id="xyz"
						style="position: absolute; left: 150px; z-index: 9; top: 0px;">
						<span>Status : <select id="listActiveOrAll">

								<option selected="selected" value="Active">Active</option>
								<option value="All">All</option>

						</select>
						</span>
						<!-- <input type="Submit" value="Search" class="projectSearch"></input> -->
					</div>
					<thead>
						<tr>
							<th align="center" valign="middle">Project ID</th>
							<th align="center" valign="middle">Project ID</th>
							<th align="center" valign="middle">Project Name</th>
							<th align="center" valign="middle">Customer Name</th>
							<th align="center" valign="middle">Offshore Manager</th>
							<th align="center" valign="middle">Onsite Manager</th>
							<th align="center" valign="middle">Current BG-BU</th>
							<th align="center" valign="middle">Project Kick off Date</th>
							<th align="center" valign="middle">Project End Date</th>
							<!--  <th  width="90px" align="center" valign="middle">Project tracking Currency </th> -->

						</tr>

						<tr class="">
							<td><input type="text" name="search_projectID"
								value="Project ID" class="search_init" hidden="true" /></td>
							<td><input type="text" name="search_projectCode"
								value="Project ID" class="search_init" disabled="disabled" /></td>
							<td><input type="text" name="search_projectName"
								value="Project Name" class="search_init" /></td>
							<td><input type="text" name="search_customerName"
								value="Customer Name" class="search_init" disabled="disabled" /></td>
							<td><input type="text" name="search_offshoreManager"
								value="Offshore Manager" class="search_init" /></td>
							<td><input type="text" name="search_onsiteManager"
								value="Onsite Manager" class="search_init" /></td>
							<td><input type="text" name="search_currentBG-BU"
								value="Current BG-BU" class="search_init" disabled="disabled" /></td>
							<td><input type="text" name="search_projectKickoffDate"
								value="Project Kick off Date" class="search_init"
								disabled="disabled" /></td>
							<td><input type="text" name="search_projectEndDate"
								value="Project End Date" class="search_init" disabled="disabled" /></td>

							<!--  <td><input type="hidden" name="search_projectTrackingCurrency"
							value="Project Tracking Currency" class="search_init" disabled="disabled"/></td>  -->
					</thead>
					<tbody>

					</tbody>

					<!-- <span class = "label label-info proj_alloc_note_msg"><span style="color: #FF0000 !important;" >*</span> Calendar Dates Enables when<br>
# On Start Date- Joining date is less than or equal to current date.</span> -->

				</table>
			</div>
			<div class="clear"></div>
		</div>
		<div id='tab2' class="tab_div">
			<div class="search_filter">
				<div class="btnIcon1 projectAllocationBtnIcon">
					<span> <sec:authorize
							access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">

							<a href="javascript:void(0);" class="blue_link" id="addNew">
								<img src="resources/images/addUser.gif" width="16" height="22" />
								Add New
							</a>

						</sec:authorize>
					</span> <span class="ProjectAllocStatusdrpdwn">&nbsp;&nbsp; Status
						: &nbsp;&nbsp;<select onchange="checkAll('')" id="activeOrAll">

							<option value="Active">Active</option>
							<option value="All">All</option>

					</select>

					</span>

				</div>
			</div>
			<div></div>
			<div class="tbl">
				<table id="example"
					class="display tablesorter addNewRow proAllocTblData">
					<thead>
						<tr>
							<th>Feedback Remarks</th>
							<th>Employee ID</th>
							<th>Employee Name</th>
							<th>Allocation Type</th>
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
								access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
								<th>Edit</th>
								<th>Delete</th>
								<th width="10%">Copy Allocation</th>
							</sec:authorize>
						</tr>
						<tr class="noBg" draggable="false">
							<td><input type="text" name="search_feedback"
                                 value="Feedback Remarks" class="search_init" disabled="disabled" /></td>
							<td><input type="text" name="search_eId" value="Employee ID"
								class="search_init" /></td>
							<td><input type="text" name="search_eName"
								value="Employee Name" class="search_init" /></td>
							<td><input type="text" name="search_alType"
								value="Allocation Type" class="search_init" /></td>
							<td><input type="text" name="search_status"
								value="Loan/Owned" class="search_init" /></td>
							<td><input type="text" name="search_stDate"
								value="Start Date" class="search_init" /></td>
							<td><input type="text" name="search_enDate" value="End Date"
								class="search_init" /></td>

							<td><input type="text" name="search_alRem"
								value="Allocation Remarks" class="search_init" /></td>
							<td><input type="text" name="search_priProject"
								value="Primary Project" class="search_init" /></td>

							<!-- 	<td><input type="text" name="search_billable"
								value="Billable" class="search_init" /></td>
						<td><input type="text" name="search_alHours"
								value="Allocation Hours" class="search_init" /></td> -->
							<!-- <td><input type="text" name="search_rId" value="Rate ID"
								class="search_init" /></td> -->


							<td><input type="text" name="search_rId"
								value="TimeHrs Manager" class="search_init" /></td>
							<!-- <td><input type="text" name="search_teamName"
								value="Team Name" class="search_init" /></td> -->
							<sec:authorize
								access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</sec:authorize>
						</tr>
					</thead>
					<tbody id="example">
					</tbody>
					<span class="label label-info proj_alloc_note_msg"><span
						style="color: #FF0000 !important;">*</span> Calendar Dates
						Enables when<br> # On Start Date- Joining date is less than
						or equal to current date.</span>
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
													<!-- <h3>Search Resource</h3><input style="height: 25px;width: 187px" id="searchEmp" type="text"></input><button style="margin-left: 15px; margin-top: -3px" onclick="searchEmployee()">Search</button>
												<p style="margin: 5px;color: #000;">Associates whose joining dates are equal to or greater then allocation start date are eligible for search/allocation OR Resource is already allocated to Project</p> -->
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
		<%-- <c:if test="${not empty eligibleResources}">

			<select name="multiresource" id="multiresource" class="multiSelectDd"
				multiple="multiple">
				<c:forEach var="eligibleResources" items="${eligibleResources}">
					<option value="${eligibleResources.employeeId}">
						${eligibleResources.firstName} ${eligibleResources.lastName}</option>
				</c:forEach>
			</select>
		</c:if> --%>
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
<script type="text/javascript">

$( ".ui-combobox").hide();

	 

</script>

<script type="text/javascript">
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
    		    		
    		    	}else{
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
    		//vm.getMultiSelectResource();
    	}
    });
        
        
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
</script>
<!--Added by Pratyoosh Tripathi -->

<div class="notification-bar">
<!-- <div class="close closeIconPosition">close</div> -->
<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
  <!-- <div class="notification-text">Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.</div> -->
  <sec:authorize
				access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')">
				<div class="notification-text">You can add or allocate a resource to a project clicking on its Project id.</div>			
	</sec:authorize>
  <sec:authorize
				access="hasAnyRole('ROLE_MANAGER')">
				<div class="notification-text">You can only view the resources allocated to a project clicking on a particular Project Id but unable to add.</div>			
	</sec:authorize>
	<sec:authorize
				access="hasAnyRole('ROLE_DEL_MANAGER')">
				<div class="notification-text">You can add or view the resources allocated to a project clicking on its particular Project Id.</div>			
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
<!-- Added by Pratyoosh Tripathi -->


 

 
    
   
   










