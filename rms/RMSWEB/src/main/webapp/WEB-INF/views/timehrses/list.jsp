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
<style>

/*updated css added by kratika-16-5-19*/

</style>
 
<!-- js and css for tableTools -->
<spring:url
	value="resources/js-framework/datatables/TableTools.js?ver=${app_js_ver}"
	var="tableTools_js" />
<script src="${tableTools_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
</script>

<spring:url
	value="resources/js-framework/datatables/ZeroClipboard.js?ver=${app_js_ver}"
	var="ZeroClipboard_js" />
<script src="${ZeroClipboard_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
</script>



<!-- js and css for tableTools -->


<!-- Week Date -->
<%-- <script src="${jquery_date_js}" type="text/javascript"></script> --%>

<!-- <input type="hidden" id="resouceId" value="{{>resourceAllocId.id }}"/> -->

<script id="timehrsTableRows" type="text/x-jquery-tmpl">
			<tr id="{{>resourceAllocId.id}}">
            <td align="left" valign="middle">{{>employeeId.yashEmpId}}
			<input type="hidden" id="id" value="{{>timeHrsId}}">
            <input type="hidden" id="resourceId.employeeId"  value="{{>employeeId.employeeId}}">
			<input type="hidden" id="resourceAllocId"  value="{{>resourceAllocId.id}}"></td>
            <td align="left" valign="middle">{{>employeeId.employeeName}}</td>
			<td align="left" valign="middle">{{>employeeId.designationId.designationName}}</td>
            <td align="left" valign="middle">{{>resourceAllocId.projectId.projectName}}</td>
            <td align="left" valign="middle">
                    {{if  approveStatus=='P'}}
                       {{if   plannedHrs ==NULL || plannedHrs == 0.0}}
      <input type="textbox" name="plannedHrs" class="numericInp"  id="plannedHrs{{>id}}" value="40"/>	
                     {{else}}
 <input type="textbox" name="plannedHrs" class="numericInp"  id="plannedHrs" value="{{>plannedHrs}}"/>
{{/if}}
                      {{else}}
                     {{>plannedHrs}}
                     {{/if}}
                    </td>
            <td align="left" valign="middle">{{>weekEndDate}}</td>
            <td align="left" valign="middle">
				<input type="hidden" id="resourceId.employeeId"  value="{{>repHrsForProForWeekEndDate}}">
					<a href="useractivitys/readonlyUserActivity/{{>resourceAllocId.id}}/{{>weekEndDate}}" class="various" >{{>repHrsForProForWeekEndDate}}</a>
				</td>
            <td align="left" valign="middle">
 
               {{if approveStatus=='P' }}
					 {{if   billedHrs ==NULL || billedHrs == 0.0}}	
                     <input type="textbox" name="billedHrs" class="numericInp"  id="billedHrs{{>id}}" value="40"/>
						 {{else}}
						 <input type="textbox" name="billedHrs" class="numericInp"  id="billedHrs" value="{{>billedHrs}}"/>
{{/if}}
                     {{else}}
                     {{>billedHrs}}
                     {{/if}}
					
                    </td>



           <td align="left" valign="middle">

 				{{if approveStatus=='P' }}
					 {{if   remarks ==NULL}}	
                     <input type="textbox" name="remarks"    id="remarks{{>id}}" value=""/>
						 {{else}}
						 <input type="textbox" name="remarks" class="numericInp"  id="remarks" value="{{>remarks}}"/>
				{{/if}}
                     {{else}}
                   			 {{if approveStatus=='R'}}
                      			{{>rejectionRemarks}}
    						  {{else}}
     				            {{>remarks}}
     				        {{/if}}
                 {{/if}}
                    
   </td>

  

<td align="left" id="approveStatusId{{:#index}}" >


	

			{{if approveStatus == 'A' }}

   
				 
				<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SEPG_USER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_BEHALF_MANAGER','ROLE_MANAGER')">
					<a href="#comment" id="comment_timehrsList" title="Change To Reject Timesheet Status"><img src="resources/images/approve_icon.png" onclick="rejectTimesheet({{>id}},'{{>approveStatus}}'); "height="16" width="16" /></a>
				</sec:authorize>

             
			{{else if weekEndDate == null}}
				&nbsp;
				{{else if approveStatus == 'P' }}
					 
					<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SEPG_USER','ROLE_MANAGER','ROLE_BEHALF_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
						<a href="javascript:void(0)" title="Change To Approve Status"><img src="resources/images/review-icon-custom.png" onclick="approveEntry({{>id}},{{>resourceAllocId.id}});"  /></a>
					</sec:authorize>
             
			{{/if}}
		


</td>
			<td align="left" >
				{{if approveStatus=='A'}}
					Approved
				{{else if approveStatus=='R'}}
					Rejected
				{{else if approveStatus=='N'}}
					Not Submitted
				{{else if weekEndDate == null}}
					&nbsp;
				{{else}}
					Review
				{{/if}}
			</td>

<td align="left" id="Rejection" >




{{if approveStatus == 'P'}}
		{{if weekEndDate == null}}
			{{else}} 
				 
				<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SEPG_USER','ROLE_MANAGER','ROLE_BEHALF_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
					<a href="#comment" id="comment_timehrsList"><img src="resources/images/reject_timesheet.png" title="Reject Timesheet" onclick="rejectTimesheet({{>id}},'{{>approveStatus}}');" height="16" width="16"/></a>
				</sec:authorize>
		{{/if}}

    {{/if}}
</td>
	</tr>
</script>



<script>

var saveOpen = false;
var allocatedResources;
var currentResource;
var mEndDate;
var needRefresh=false;
var uesrActivityID;
var status;
var timeSheetStatus ;


function rejectTimesheet(id,submitStatus)
{
	uesrActivityID=id;
	status='R';
	
	startProgress();
	/*
	$.postJson('timehrses/'+id+'/'+submitStatus,{}, function(data){
		var id = document.getElementById("employeeIdHidden").value;
		getTimehrsById(id);
		saveOpen = false;
		nEditing= null;

	}, 'json');
	//stopProgress();
	var successMsg ="TimeSheet has been rejected successfully "; 
		showSuccess(successMsg); */
		
	stopProgress();	
	}

function reviewEntry(id){

	var review = confirm("Do you want to change status to Review ?");
	if(review) {
		startProgress();
		$.postJson('timehrses/'+id+'/R'+'/false',{}, function(data){
			//var id = document.getElementById("employeeIdHidden").value;
			//getTimehrsById(id);
			timeSheetStatus = document.getElementById('timeSheetStatus').value;
			  		if(timeSheetStatus =='P'){
			  			$("#inner").find('input[type="button"]').show();
			  			// $("#inner").find('input[type="button"]').button({disabled:false});
			  			 }
			  		else
			  		  $("#inner").find('input[type="button"]').hide();//button({disabled:true});
			  		  
			  		  
			  		var temp="${j}";
					if(temp==1){
					   if(timehrsTable != null)timehrsTable.fnClearTable(); 
					    var TempWeekEndDateHyper=("${WeekEndDateHyper}");
				 	    <c:forEach var="resource" items="${timehrses}"> 
			      	    var id="${resource.employeeId}";
					    
				        $.getJSON("",{find:"ByResourceIdUpdatedHyper",resourceId:id,weekEndDate:TempWeekEndDateHyper,timeSheetStatus:timeSheetStatus}, showTimehrsHyper);	
				 	    </c:forEach> 
				 	   }
					else {
			            var id = document.getElementById("employeeIdHidden").value;
			            var activeOrAll = document.getElementById('activeOrAll').value;
			            $.getJSON("",{find:"ByResourceIdUpdated" ,resourceId:id,timeSheetStatus:timeSheetStatus,activeOrAll:activeOrAll}, showTimehrs);
					}
					
			  		  
			  		  
			  		  
			  		  
			  		  
			  		  
			  		  
			  		  
			  		  
			  	//	$.getJSON("",{find:"ByResourceIdUpdated" ,resourceId:id,timeSheetStatus:timeSheetStatus}, showTimehrs);
				
			saveOpen = false;
			nEditing= null;

		}, 'json');


		
		
	}

}


//Timesheet Approval Validation Start:Pratyoosh


	    function approveEntry(id,resourceAllocId){
	    	
	    	var errorMsg = "";
	    	var plannedHours;
	    	var billedHours;
	    	
	    	 var planRegExp = new RegExp('^(|[1-9][0-9]*)$');
	         var billedRegExp = new RegExp('^(|[1-9][0-9]*)$');
	    			 
	    			 
                   if(document.getElementById("plannedHrs"+id)!=null){
	    				 plannedHours = document.getElementById("plannedHrs"+id).value;
	    		 	}else{
	    		 		plannedHours = document.getElementById("plannedHrs").value;
	    		 	}

	    		    if(document.getElementById("billedHrs"+id)!=null){
	    			   billedHours = document.getElementById("billedHrs"+id).value;
	    		 	}else{
	    		 		billedHours = document.getElementById("billedHrs").value;
	    		 	}


            if(!planRegExp.test( plannedHours) && plannedHours != 0){
		    			 
		   errorMsg = errorMsg + ("\u2022 Please enter only numeric values in Planned Hours! <br/>");
	    				 }
	    		     
	     if(!billedRegExp.test(billedHours) && billedHours != 0){					
	     errorMsg = errorMsg + ("\u2022  Please enter only numeric values in Billed Hours! <br/>");
	    		     }
		    		    
	    					 
        
               if ((plannedHours == '')  || (billedHours == '')) {
   		  
		 	 
   	         errorMsg = ("\u2022 Planned and Billed Hours should not be empty! <br/>");
   			 
   	  		   }
		   
	    	  		 if(errorMsg!= ""){
	    		    showError(errorMsg);
	    		    errorMsg = " ";
	    		 	return; 		 
	    		 	stopProgress();	
	    		    }
	    		  //Timesheet Approval Validation End:Pratyoosh
	    			   
 
	
	var approve = confirm("Do you want to change status to Approve ?");
 
	var billedhrs = -1,plannedhrs= -1,remarks = "null";
	if(document.getElementById("billedHrs"+id)!=null){
		billedhrs = document.getElementById("billedHrs"+id).value;
	}else{
		billedhrs = document.getElementById("billedHrs").value;	
	}
	if(document.getElementById("remarks"+id)!=null){
		remarks= document.getElementById("remarks"+id).value;
	} else {
		remarks= document.getElementById("remarks").value;
	} 
	if(document.getElementById("plannedHrs"+id)!=null){
		plannedhrs = document.getElementById("plannedHrs"+id).value;
	}else{
		plannedhrs = document.getElementById("plannedHrs").value;
	}
		  
	if(remarks=="")	  {
		remarks = "null";
	}
 	var resourceEmployeeId = document.getElementById("resourceId.employeeId").value;
	
	if(document.getElementById("plannedHrs"+id)!=null)
	  plannedhrs = document.getElementById("plannedHrs"+id).value;
	if(approve) {
		startProgress();	
		$.postJson('timehrses/'+id+'/A'+'/true/'+billedhrs+'/'+plannedhrs+'/'+remarks+'/'+resourceEmployeeId+'/'+resourceAllocId, function(data){	
			//alert("pratyoosh");
		//$.postJson('timehrses/'+id+'/A'+'/true/'+billedhrs+'/'+plannedhrs,{}, function(data){
			var id = document.getElementById("employeeIdHidden").value;
			getTimehrsById(id);
			saveOpen = false;
			nEditing= null;   
		}, 'json');
		//stopProgress();
		var successMsg ="TimeSheet has been approved successfully "; 
			showSuccess(successMsg);
			if(timehrsTable != null)timehrsTable.fnClearTable();                        //clear the table after timesheet submition     add by akash
	}
	    //  validateAddNewRow(jsonData);
	      
	
	
	
}

function getInputValue(str){
	var index = str.indexOf("<input");
	if(index < 0)return null;
	str = str.substr(index , str.length);
	return $(str).val();
}

	function deleteDataRow(id){
		$.deleteJson_('timehrses/'+id,{}, function(data){
		 refreshGrid();
		}, 'json');
	}
	function refreshGrid(){
	
	}

var timehrsTable;


function addRow (timehrsTable,nRow){
	var aData = timehrsTable.fnGetData(nRow);
	var jqTds = $('>td', nRow);
	if(jqTds.length < 1)
	return;
	jqTds[1].innerHTML = currentResource.firstName + ' ' +  currentResource.lastName;
	jqTds[2].innerHTML = '<input type="hidden" name="designationId" id ="designationId'+currentResource.yashEmpId+'" value ="'+currentResource.designationId.id+'" />'+currentResource.designationId.designationName;
	var str = '<select id="resourceAllocId.id" name="resourceAllocId.id">'; 
	for(var i=0; i< allocatedResources.length;i++ ) {
		str+='<option value="'+(allocatedResources[i].id)+'"/>'+(allocatedResources[i].projectId.projectName)+'</option>';
	}
	str+='</select>';
	if(aData[3]==""){    
		aData[3] = 40;
	}
	if(aData[6]==""){    
		aData[6] = 40;
	}
 	jqTds[3].innerHTML = str;//aData[2];
 	jqTds[4].innerHTML = '<input type="text" id="plannedHrs" name="plannedHrs"  id="plannedHrs" value="'+ aData[3] +'">';
 	jqTds[5].innerHTML =  '<input type="text" id="weekEndingDate" name="weekEndingDate" readonly="readonly" value="'+ $("#weekEndDateSel").val() +'" readonly>' ;
 	jqTds[6].innerHTML = '<input type="hidden" name="reportedHrs"   id="reportedHrs1" value="'+ aData[5] +'"/>'+aData[5];
 	jqTds[7].innerHTML ='<input type="textarea" name="billedHrs" class="numericInp"  id="billedHrs" value="'+aData[6]+'"/>';
 	jqTds[8].innerHTML ='<input type="textarea" name="remarks"  id="remarks" value="'+aData[7]+'">';
 	//jqTds[8].innerHTML ='<a class="delete" href="#">Delete</a>';
 	jqTds[9].innerHTML ='<a class="edit" href="#">Add</a>';
 	jqTds[10].innerHTML =aData[9];
 	jqTds[11].innerHTML ='&nbsp;';
 	
 	
 	
 	//populate different select boxes
 	//value = getInputValue(aData[1]);
 	value= currentResource.designationId.designationName;
	//$("#designationId"+value).val(value);
 	
 	value = getInputValue(aData[2]);
	$("#projectId"+value).val(value);
	
 	$("#remarks").autoGrow();
	containerWidth();
	 needRefresh = true;
}


function editRow (timehrsTable,nRow){
	var aData = timehrsTable.fnGetData(nRow);
	var jqTds = $('>td', nRow);
	if(jqTds.length < 1)
	return;
	jqTds[1].innerHTML = '<input type="hidden" name="id" id ="designationId'+getInputValue(aData[0])+'"/>'+aData[1];
	jqTds[2].innerHTML = aData[2];
 	jqTds[3].innerHTML = '<input type="hidden" name="projectId.id" id ="projectId'+getInputValue(aData[3])+'"/>'+aData[3];
 	
if(aData[4]=== " "|| aData[4].charCodeAt(1)==110){
		jqTds[4].innerHTML = '<input type="textarea" id="plannedHrs" name="plannedHrs"  id="plannedHrs" value="40">';
	}else{
		jqTds[4].innerHTML = '<input type="textarea" id="plannedHrs" name="plannedHrs"  id="plannedHrs" value="40"'+ aData[4] +'">';		
	}
 	jqTds[5].innerHTML = aData[5]+'<input type="hidden" id="weekEndingDate" readonly="readonly" name="weekEndingDate"  value="'+ aData[5] +'">' ;
 	jqTds[6].innerHTML = '<input type="hidden" name="reportedHrs"   id="reportedHrs1" value="'+ getInputValue(aData[6]) +'"/>'+aData[6];
 	if(aData[7]=== " "|| aData[7].charCodeAt(1)==110){
 	 	jqTds[7].innerHTML ='<input type="textarea" name="billedHrs" class="numericInp"  id="billedHrs" value=""/>'; 		
 	}else{
 		
 	jqTds[7].innerHTML ='<input type="textarea" name="billedHrs" class="numericInp"  id="billedHrs" value="'+aData[7]+'"/>';
 	}
 	jqTds[8].innerHTML ='<input type="textarea" name="remarks"  id="remarks" value="'+aData[8]+'">';
 	//jqTds[8].innerHTML ='<a class="delete" href="#">Delete</a>';
 	jqTds[9].innerHTML ='<a class="edit" href="#">Save</a> / <a id="cancel" class="cancel" href="" >Cancel</a>';
 	jqTds[10].innerHTML =aData[10];
 	jqTds[11].innerHTML ='&nbsp;';
 	
 	//populate different select boxes
 	value = getInputValue(aData[1]);
	$("#designationId"+value).val(value);
 	
 	value = getInputValue(aData[2]);
	$("#projectId"+value).val(value);
	
 	$("#remarks").autoGrow();
	containerWidth();
	 needRefresh = true;
}

function getJsonString(name, value){
	if(name.indexOf(".") > -1){
		var items = name.split("." , 2);
		var jsonInner = getJsonString(items[1],value);
		var json = '"'+items[0]+ '":{'+jsonInner+'}';
		return json;
	}
	if(value!=null && value.toString().toLowerCase() == "true" )return '"'+name+ '":' + true;
	if(value != null && value.toString().toLowerCase() =="false")return '"'+name+ '":' + false;
	if(value != null && ($.trim(value.toString()) == '' || $.trim(value.toString()).toLowerCase() == 'null'))return '';
	return '"'+name+ '":"'+value+'"';
}

function validateAddNewRow(jsonData){
	
startProgress();
	
	$.postData('timehrses/validate',jsonData , function(data){
		 if(!jQuery.isEmptyObject(data)){
			//alert("Time sheet for Specified Week and Project already exists.");
			// Added for task # 216 - Start
						var text="Time sheet for Specified Week and Project already exists.";
						showAlert(text);
						// Added for task # 216 - End
			//var id = document.getElementById("employeeIdHidden").value;
			
			stopProgress();
			getTimehrsById(id);
			saveOpen = false;
			nEditing= null;
			timeSheetStatus = document.getElementById('timeSheetStatus').value;
	  		if(timeSheetStatus =='P')
	  			$("#inner").find('input[type="button"]').show();
	  			// $("#inner").find('input[type="button"]').button({disabled:false});
	  		else
	  			$("#inner").find('input[type="button"]').hide();
	  		 // $("#inner").find('input[type="button"]').button({disabled:true});
	  		 
	  		 
	  		var temp="${j}";
			if(temp==1){
			   if(timehrsTable != null)timehrsTable.fnClearTable(); 
			    var TempWeekEndDateHyper=("${WeekEndDateHyper}");
		 	    <c:forEach var="resource" items="${timehrses}"> 
	      	    var id="${resource.employeeId}";
			    
		        $.getJSON("",{find:"ByResourceIdUpdatedHyper",resourceId:id,weekEndDate:TempWeekEndDateHyper,timeSheetStatus:timeSheetStatus}, showTimehrsHyper);	
		 	    </c:forEach> 
		 	   }
			else {
	                 var id = document.getElementById("employeeIdHidden").value;
	                 var activeOrAll = document.getElementById("activeOrAll").value;	
	                 $.getJSON("",{find:"ByResourceIdUpdated" ,resourceId:id,timeSheetStatus:timeSheetStatus,activeOrAll:activeOrAll}, showTimehrs);
			}
	  		 
	  		 
	  		 
	  		 
	  		 
	  		 
	  		 
		//	$.getJSON("",{find:"ByResourceIdUpdated" ,resourceId:id,timeSheetStatus:timeSheetStatus}, showTimehrs);
		
		 }else{
			 $.postData("timehrses", jsonData,function(data) {
				 
					saveOpen = false;
					nEditing= null;
					stopProgress();
					//var successMsg ="Timehours details have been added successfully"; 
					//showSuccess(successMsg);
					timeSheetStatus = document.getElementById('timeSheetStatus').value;
			  		if(timeSheetStatus =='P')
			  			$("#inner").find('input[type="button"]').show();
			  			// $("#inner").find('input[type="button"]').button({disabled:false});
			  		else
			  			$("#inner").find('input[type="button"]').hide();
			  		 // $("#inner").find('input[type="button"]').button({disabled:true});
			  		 
			  		 
			  		 
			  		var temp="${j}";
					if(temp==1){
					   if(timehrsTable != null)timehrsTable.fnClearTable(); 
					    var TempWeekEndDateHyper=("${WeekEndDateHyper}");
				 	    <c:forEach var="resource" items="${timehrses}"> 
			      	    var id="${resource.employeeId}";
					    
				        $.getJSON("",{find:"ByResourceIdUpdatedHyper",resourceId:id,weekEndDate:TempWeekEndDateHyper,timeSheetStatus:timeSheetStatus}, showTimehrsHyper);	
				 	    </c:forEach> 
				 	   }
					else {
			                 var id = document.getElementById("employeeIdHidden").value;
			                 var activeOrAll = document.getElementById("activeOrAll").value; 
						$.getJSON("",{find:"ByResourceIdUpdated" ,resourceId:id,timeSheetStatus:timeSheetStatus,activeOrAll:activeOrAll}, showTimehrs);
					}
			  		 
			  		 
			  		 
			  		 
			  		 
			  		 
			  		 
			  		 
				//	$.getJSON("",{find:"ByResourceIdUpdated" ,resourceId:id,timeSheetStatus:timeSheetStatus}, showTimehrs);
				 }, 
				 "json"
			);
		 }
	}, 'json');

    

}

function addNewRow(timehrsTable,nRow){
	var strId=null;
	var sData=[];
	var aData = timehrsTable.fnGetData(nRow);
	if(aData == null || aData.length < 1)return;

	var ssdata = null;	
	
	if(strId != null && $.trim(strId) != '')		
	
	var tableData = { tableData: [] };
	var obj = {};
	 $('table#timehrsTableId2 tbody tr:first').each(function(i,tr) {
	        $(this).find("td input,select").each(function() {
		        //alert($(this));
		       
         		//if($(this).attr("name")!="id")
         		sData.push({name:$(this).attr("name"),value:this.value})
	        	obj[$(this).attr("name")] = this.value;

	       
		 });
	        //tableData.tableData.push(obj);
	        
	    });
	 	 
	 	var jsonData ='{'
	 		 $.each(sData, function(i, item) {
	 			var jsonString = getJsonString(item.name , item.value);
	 			if(jsonString != null && $.trim(jsonString) != ''){
	 				jsonData += getJsonString(item.name , item.value) +",";			
	 			}
	 		});
	 		jsonData = jsonData.slice(0, -1); 
	 		
	 		jsonData +='}'; 
	  
	validateAddNewRow(jsonData);
	
	
	
	
	
}

function saveRow(timehrsTable,nRow){

    startProgress();
	var strResourceId=null;
	var strId=null;
	var sData=null;
	var strResourceAllocId = null;
	var aData = timehrsTable.fnGetData(nRow);
	if(aData == null || aData.length < 1)return;
	var index = aData[0].indexOf("<input");
	var strArray = aData[0].substr(index , aData[0].length).split(">", 3);
	if(strArray.length>0&&strArray[0]!=""&&strArray[1]!="")
	{
		strId = $(strArray[0] +">").val();
		strResourceId = $(strArray[1] +">").val();
		strResourceAllocId = $(strArray[2] +">").val();
	}
	sData = $('*', timehrsTable.fnGetNodes()).serializeArray();
	if(strId != null && $.trim(strId) != '')		
	sData.push({name:"id",value:strId});
	if(strResourceId != null && $.trim(strResourceId) != '')	
		sData.push({name:"resourceId.employeeId",value:strResourceId});
	if(strResourceAllocId != null && $.trim(strResourceAllocId) != '')	
		sData.push({name:"resourceAllocId.id",value:strResourceAllocId});
	var jsonData ='{'
	$.each(sData, function(i, item) {
		var jsonString = getJsonString(item.name , item.value);
		if(jsonString != null && $.trim(jsonString) != '')
		jsonData += getJsonString(item.name , item.value) +",";
	});
	jsonData = jsonData.slice(0, -1);
	jsonData +='}';
	$.postData("timehrses", jsonData,function(data) {
		 if(timehrsTable != null)timehrsTable.fnClearTable(); 
	//	getTimehrsById(id);
		saveOpen = false;
		nEditing= null;
		stopProgress();
		var successMsg ="Timehours details have been saved successfully"; 
			//showSuccess(successMsg);
			timeSheetStatus = document.getElementById('timeSheetStatus').value;
	  		if(timeSheetStatus =='P')
	  			$("#inner").find('input[type="button"]').show();
	  			// $("#inner").find('input[type="button"]').button({disabled:false});
	  		else
	  			$("#inner").find('input[type="button"]').hide();
	  			//  $("#inner").find('input[type="button"]').button({disabled:true});
			   
			var temp="${j}";
			if(temp==1){
			   if(timehrsTable != null)timehrsTable.fnClearTable(); 
			    var TempWeekEndDateHyper=("${WeekEndDateHyper}");
		 	    <c:forEach var="resource" items="${timehrses}"> 
	      	    var id="${resource.employeeId}";
			    
		        $.getJSON("",{find:"ByResourceIdUpdatedHyper",resourceId:id,weekEndDate:TempWeekEndDateHyper,timeSheetStatus:timeSheetStatus}, showTimehrsHyper);	
		 	    </c:forEach> 
		 	   }
			else {
				var activeOrAll = document.getElementById("activeOrAll").value;
				var id = document.getElementById("employeeIdHidden").value; 
				$.getJSON("",{find:"ByResourceIdUpdated" ,resourceId:id,timeSheetStatus:timeSheetStatus,activeOrAll:activeOrAll}, showTimehrs);
			}

	 }, 
	 "json"
	);



	

    
}
</script>
<script>
var activeOrAll = '';
var newTimeSheetStatus = '';
var navigationFlag;
var resDateOfJoining;
var gEmployeeId;
var gYashEmployeeId;
var gEmployeeName;
var gResourceId;
var oTable = "";
       // Wait until the DOM has loaded before querying the document
		$(document).ready(function(){
			
			activeOrAll = $('#activeOrAll').val();
			newTimeSheetStatus = $('#newTimeSheetStatus').val();
			
			if(activeOrAll=='undefined' || activeOrAll==''){
				activeOrAll ='All';
				$('#activeOrAll').val();
			}
			
			if(newTimeSheetStatus=='undefined' || newTimeSheetStatus==''){
				allDiscardSuccessVar ='L';
				$('#newTimeSheetStatus').val();
			}
			
		    oTable = $('#timesheet_table').dataTable( {
		    	"bProcessing": true,
		        "bServerSide": true,
		         "sPaginationType" : "full_numbers",	      
		        "scrollY": 200,
		        "scrollX": true,
		        "bSortCellsTop": true,
		        "sDom":"ltipr",	        
		        "sAjaxSource": '${pageContext.request.contextPath}/timehrses/list/'+activeOrAll+'/'+newTimeSheetStatus,
		         "aoColumns": [
		             		 {mData:'yashEmpId',sDefaultContent: "NA",mRender:function ( yashEmpId, type, row )  
		                		 	{
		             			 console.log("row",row.yashEmpId);	
		             			 tdData="";
		             			tdData='<a href="#" onclick="openMaintainance('+row.employeeId+', `'+yashEmpId+'`);">'+yashEmpId+'</a>';
		               			return tdData; 
		               					 
		                		
		        	      		}},
		                	 {mData:'employeeName',sDefaultContent: "NA" },
		                	 {mData:'designationName', sDefaultContent: "NA" },
		               
		                	 {mData:'grade', sDefaultContent: "NA" }, 
		                	 {mData:'projectNames', sDefaultContent: "NA", "bSortable": false }, ]
		             } );
	 		$("thead input").keyup( function(i){
		    	
		    	if(i.which==13||(i.which==8 && this.value.length==0))
		    		{
		    		
		    	oTable.fnFilter( this.value, oTable.oApi._fnVisibleToColumnIndex(oTable.fnSettings(), $("thead input").index(this) ) );
		    		}
		    		});
	 		
	 					
		$("#comment").hide();
		navigationFlag="${navigationFlag}"; 
		$("#NoAllocMessage").hide();
		$(".tab_div").hide();
		$('ul.tabs a').click(function () {
			if(this.hash=="#tab1" && needRefresh) {
				location.reload();	
				needRefresh=false;
			}
			
			/* if project approval is blank i.e user is not allocated to any project then it shows proper msg */
			
			var checkAllocForManager = document.getElementById('newTimeSheetStatus').value;
	       	if(checkAllocForManager){
	       		$("#NoAllocMessage").hide();
	       	}else{
			    <c:choose>   
					<c:when test="${empty timehrses}">
					    $("#NoAllocMessage").show();
					    $(".tab_seaction").hide();
					    $("#helpIcon").hide();
			       	</c:when>
			       	<c:otherwise>
				        $("#NoAllocMessage").hide(); 
				    </c:otherwise>
			    </c:choose>
	       	}
	       		
		    /* ......... */ 
		       
			$(".tab_div").hide().filter(this.hash).show();
			$("ul.tabs a").removeClass('active');
			$('a[href$="tab2"]').addClass('MaintenanceTab');
			$(this).addClass('active');			
			return false;

		}).filter(':first').click();
		addDataTableSearch($('#timehrsTableId1'));
		
		//activeOrAll = "${activeOrAll}";
		//document.getElementById('activeOrAll').value= activeOrAll;
		//document.getElementById('newTimeSheetStatus').value=  "${newTimeSheetStatus}";
		timehrsTable=addDataTableSearch($('#timehrsTableId2'));
		
		$('#addNew, #update').click(function(){
			
			$("#updateRecords").reset();
			displayMaintainance();
		});
  		$('a[href$="tab2"]').click(function(){
  			    if(timehrsTable != null)timehrsTable.fnClearTable(); 
  			    document.getElementById("inner").innerHTML="";
//   			$('#timehrsTableId2 > tbody').find('tr').remove();
//   			$("#updateRecords").reset();
  			  initTable("#timehrsTableId2");
		});
  		$('#MaintenanceTabInactive').off('click');	
  		$('a[href$="tab1"]').click(function(){
			initTable("#timehrsTableId1");
		});
  		
  		var navigationFlag="${navigationFlag}";
  		if(navigationFlag=="true"){ 
  			 
  			 var empId="${employeeId}";
  		     var yashEmpId="${yashEmployeeId}";
  		     displayMaintainance();
  			 openMaintainance(empId,yashEmpId);
  			} 
  		$('#ok').on("click", function(){
  			
  			var rejectionRemarks = "";
  			rejectionRemarks=$("#rejectionTxt").val();
  			$("#rejectionTxt").val(null);
  			
  			if(rejectionRemarks.length  == "" || rejectionRemarks == null ){
  				$("#comment").focus();
  				showError("Please fill rejection comments");
  				
  				
  			}
  			else{
  				
  				 startProgress();
  				  
  				 $.fancybox.close();
  				$.postJson('timehrses/'+uesrActivityID+'/'+status+'/'+rejectionRemarks+'/reject',{}, function(data){
  					var id = document.getElementById("employeeIdHidden").value;
  					getTimehrsById(id);
  					saveOpen = false;
  					nEditing= null;

  				}, 'json');
  				//stopProgress();
  				var successMsg ="TimeSheet has been rejected successfully "; 
  					showSuccess(successMsg); 
  					
  					if(timehrsTable != null)timehrsTable.fnClearTable();              //clear the table after delete timesheet   add by akash
  					
  			}
  			
  		});
  		$('#cancel').on("click", function(){
  			$("#rejectionTxt").val(null);
  			$.fancybox.close();
  		
  			
  		});
  		var cells = [];
  		$('#inner').on("click",'#approveAll', function(){  		
  			 cells = [];
  			var reviewDone = true;	
  			 var rows = $("#timehrsTableId2").dataTable().fnGetNodes();
  				var plannedHours="", billedHours  ="", weekendDate = "",resourceAllocId = "",remarks ="";
  				if(rows.length == 0)
  					{
  					showError("There are no timeSheets to approve");
  					return;
  					}
  				for (var i = 0; i < rows.length; i++) {

  					
  					// Get HTML of 3rd column (for example)
  					  plannedHours=plannedHours+$(rows[i]).find("td:eq(4) input").val() ;
  					
  			 
  					billedHours = billedHours + ($(rows[i]).find("td:eq(7) input").val())  ;
  					weekendDate = weekendDate+($(rows[i]).find("td:eq(5)").text());  
  					resourceAllocId = resourceAllocId+$(rows[i]).attr("id");
  					remarks = remarks+$(rows[i]).find("td:eq(8) input").val(); 
  					if(i<rows.length-1){
  						plannedHours=plannedHours+",";
  						billedHours = billedHours + ",";
  						weekendDate = weekendDate+",";
  						resourceAllocId = resourceAllocId+",";
  						remarks = remarks+",";
  					}
  					/* plannedHours.push($(rows[i]).find("td:eq(7) input").val());
  					 
  					billedHours.push($(rows[i]).find("td:eq(4) input").val());
  					weekendDate.push($(rows[i]).find("td:eq(5)").text()); */
  					
  				}
  				
  						if(!reviewDone)
  							{
  							showError("Please Put the planned/Billed hours");
  							return;
  							} else {
  								//var id= document.getElementById("employeeIdHidden").value;
  								var id= document.getElementById("resourceId.employeeId").value;
  								$.ajax({  
  							        url: "",  
  							        dataType: 'json',  
  							        data: {find:"approveAll" , resourceId:id, plannedHours: plannedHours ,billedHours: billedHours ,weekendDate: weekendDate ,resourceAllocId: resourceAllocId,remarks:remarks },  
  							        async: false,  
  							        success: function(){  
  							        	showSuccess("All Time Sheets Successfully Approved");
  							        	var timeSheetStatus = document.getElementById('timeSheetStatus').value;
  							        	var activeOrAll = document.getElementById("activeOrAll").value;	 
  							        	$.getJSON("",{find:"ByResourceIdUpdated" ,resourceId:id,timeSheetStatus:timeSheetStatus}, showTimehrs);
  							                     },
  							        error: function(){
  							               showError('Some Problem Occurred...Cannot display data!!');
  							               //stopProgress();
  							        }
  							      }) 
  								
  							}
  					
  		});
  		
  		$('#inner').on("click","#addNew", function(){
  			
  			if($("#weekEndDateSel").val() == '') {
  				//alert("Please select Week End Date.");
  				// Added for task # 216 - Start
  							var text="Please select Week End Date.";
  							showAlert(text);
  							// Added for task # 216 - End
  				return false;
  			}
  			var selectedWeekEndDate=$("#weekEndDateSel").val();
  			var id= document.getElementById("employeeIdHidden").value;
  			  $.ajax({  
  			        url: "timehrses",  
  			        dataType: 'json',  
  			        data: {find:"FindResourceAllocationForResource" , resourceId:id,weekEndDate:selectedWeekEndDate},  
  			        async: false,  
  			        success: function(data){  
  			        	setResourceAllocated(data);
  			                     },
  			        error: function(){
  			               showError('Some Problem Occurred...Cannot display data!!');
  			               //stopProgress();
  			        }
  			      })   
  			if(allocatedResources=="") {
  				//alert("No Project Allocated to selected Resource so can't add Record.");
  				// Added for task # 216 - Start
  							var text="No Project Allocated to selected Resource so can't add Record.";
  							showAlert(text);
  							// Added for task # 216 - End
  				return false;
  			}
  			if(saveOpen){
  				// Added for task # 216 - Start
  				/* alert("Please enter and save the data"); */
  				var text="Please enter and save the data";
  				showAlert(text);
  				// Added for task # 216 - End
  				e.preventDefault();
  				return;
  			}
  			
  			tempId=$("table.addNewRow_thm tbody").find("tr").length;
  			var yashEmpId = document.getElementById("timeHrsId").value;
  			var nYashEmpIdStr = yashEmpId+ '<input type="hidden" name="resourceId.employeeId" id="resourceId" value="' + document.getElementById("employeeIdHidden").value +'"/>' ;//+
  			//'<input type="hidden" id="id" value=""/>' ;
  			var aiNew = timehrsTable.fnAddDataAndDisplay( [ nYashEmpIdStr,'', '', '', '','','','','','','','','']);
  			var nRow = aiNew.nTr;
  			$(".addNewRow_thm tbody").prepend(nRow);
  			addRow(timehrsTable, nRow);	
  			nEditing = nRow;
  			saveOpen = true;
  		});
  		

  	var nEditing = null;
  	function restoreRow ( timehrsTable, nRow ){
  		var aData = timehrsTable.fnGetData(nRow);
  		var jqTds = $('>td', nRow);
  		for ( var i=0, iLen=jqTds.length ; i<iLen ; i++ ) {
  			timehrsTable.fnUpdate( aData[i], nRow, i, true );
  		}
  		timehrsTable.fnDraw();
  	}

  	$(document).on('click','#timehrsTableId2 a.delete', function (e) {
  		e.preventDefault();
  		var nRow = $(this).parents('tr')[0];
  		timehrsTable.fnDeleteRow( nRow );
  		saveOpen = false;
  		nEditing= null;
  	});
  	$(document).on('click','#timehrsTableId2 a.cancel', function (e) {
  		$("#example tbody tr").find("td").attr("align","center");
  			e.preventDefault();	
  			restoreRow( timehrsTable, nEditing );
  	});

  	$(document).on('click', '#timehrsTableId2 a.edit', function (e) {
  		
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
  		var  nRow = $(this).parents('tr')[0];			
  		if ( nEditing !== null && nEditing != nRow ) {
  			/* Currently editing - but not this row - restore the old before continuing to edit mode */
  			restoreRow( timehrsTable, nEditing );
  			editRow( timehrsTable, nRow );
  			nEditing = nRow;
  		} else if ( nEditing == nRow && this.innerHTML == "Save" ) {
  		    if($("#plannedHrs").val() == "") {
  		    	//alert("Planned Hours can't be empty");
  		    	// Added for task # 216 - Start
  							var text="Planned Hours can't be empty";
  							showAlert(text);
  							// Added for task # 216 - End
  		    	$("#plannedHrs").css("border",  "1px red solid");
  		    	return false;
  		    }
  		    if($("#billedHrs").val() == "") {
  		    	//alert("Billed Hours can't be empty");
  		    	// Added for task # 216 - Start
  	      var text="Billed Hours can't be empty";
  	      showAlert(text);
  	      // Added for task # 216 - End
  		    	$("#billedHrs").css("border",  "1px red solid");
  		    	return false;
  		    }

  			if(!isNumeric("#plannedHrs") || !isNumeric("#billedHrs")) {

  					if(!isNumeric($("#plannedHrs"))) {
  						setTimeout(function(){$("#plannedHrs").focus();}, 10);
  						$("#plannedHrs").css("border",  "1px red solid");
  					}
  					if(!isNumeric($("#billedHrs"))) {
  						setTimeout(function(){$("#billedHrs").focus();}, 10);
  						$("#billedHrs").css("border",  "1px red solid");
  					}
  				//alert("Please enter correct hour value");
  				// Added for task # 216 - Start
  	      var text="Please enter correct hour value";
  	      showAlert(text);
  	      // Added for task # 216 - End
  				return false;		
  			}

  			//validation put here.. if fails add return..
  			/*------------------------Added By Prasoon------------------------------*/
  			var flag = true;
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
  					saveRow( timehrsTable, nEditing );
  					nEditing = null;
  					return false;
  		        }
  		} else if ( nEditing == nRow && this.innerHTML == "Add" ) {

  		    if($("#plannedHrs").val() == "") {
  		    	//alert("Planned Hours can't be empty");
  		    	// Added for task # 216 - Start
  	      var text="Planned Hours can't be empty";
  	      showAlert(text);
  	      // Added for task # 216 - End
  		    	$("#plannedHrs").css("border",  "1px red solid");
  		    	return false;
  		    }
  		    if($("#billedHrs").val() == "") {
  		    	//alert("Billed Hours can't be empty");
  		    	// Added for task # 216 - Start
  	      var text="Billed Hours can't be empty";
  	      showAlert(text);
  	      // Added for task # 216 - End
  		    	$("#billedHrs").css("border",  "1px red solid");
  		    	return false;
  		    }

  			if(!isNumeric("#plannedHrs") || !isNumeric("#billedHrs")) {

  					if(!isNumeric($("#plannedHrs"))) {
  						setTimeout(function(){$("#plannedHrs").focus();}, 10);
  						$("#plannedHrs").css("border",  "1px red solid");
  					}
  					if(!isNumeric($("#billedHrs"))) {
  						setTimeout(function(){$("#billedHrs").focus();}, 10);
  						$("#billedHrs").css("border",  "1px red solid");
  					}
  				//alert("Please enter correct hour value");
  				// Added for task # 216 - Start
  	      var text="Please enter correct hour value";
  	      showAlert(text);
  	      // Added for task # 216 - End
  				return false;		
  			}

  			

  			//validation put here.. if fails add return..
  			/*------------------------Added By Prasoon------------------------------*/
  			var flag = true;
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
  					addNewRow( timehrsTable, nEditing );
  					nEditing = null;
  					return false;
  					}
  		}
  		else {
  			
  			editRow( timehrsTable, nRow );
  			nEditing = nRow;
  		}
  		
  		});
  	$('[data-toggle="tooltip"]').tooltip();
  	 
  	setInterval(function(){ $('[data-toggle="tooltip"]').fadeToggle(500); }, 1000);

  	<%
  		Boolean flag=false;
  		flag=(Boolean)session.getAttribute("notificationbarflag");
  	%>
	$('.close').click(function() {
							$('.notification-bar').hide();
						});

}); //end of .ready
$(document.body).on('change','#activeOrAll',function() {

	/* activeOrAll = document.getElementById('activeOrAll').value;
	var newTimeSheetStatus = document
			.getElementById('newTimeSheetStatus').value;
	window.location.href = '${pageContext.request.contextPath}/timehrses?active='
			+ activeOrAll
			+ '&newTimeSheetStatus='
			+ newTimeSheetStatus; */
activeOrAll=this.value;
var oSettings = oTable.fnSettings();
oSettings.sAjaxSource  = "${pageContext.request.contextPath}/timehrses/list/"+activeOrAll+'/'+newTimeSheetStatus;
//oTable.fnClearTable();
oTable.fnDraw();
});

$(document.body).on('change','#newTimeSheetStatus',
function() {
/* 	var newTimeSheetStatus;
	newTimeSheetStatus = document
			.getElementById('newTimeSheetStatus').value;
	document.getElementById('newTimeSheetStatus').value = newTimeSheetStatus;
	activeOrAll = document.getElementById('activeOrAll').value;
	window.location.href = '${pageContext.request.contextPath}/timehrses?active='
			+ activeOrAll
			+ '&newTimeSheetStatus='
			+ newTimeSheetStatus; */
	newTimeSheetStatus=this.value;
	 var oSettings = oTable.fnSettings();
	 oSettings.sAjaxSource  = "${pageContext.request.contextPath}/timehrses/list/"+activeOrAll+'/'+newTimeSheetStatus;
	 //oTable.fnClearTable();
	 oTable.fnDraw();
});
	function getWeekDates(udate) {
		//alert("udate: " + udate); 
		var date = Date.parse(udate);
		if (date.is().monday()) {
			weekEnd = date.next().saturday().toString('dd-MMM-yyyy');
		} else if (date.is().sunday()) {
			weekEnd = date.next().saturday().toString('dd-MMM-yyyy');
		} else if (date.is().saturday()) {
			weekEnd = date.toString('dd-MMM-yyyy');
		} else {
			weekEnd = date.next().saturday().toString('dd-MMM-yyyy');
		}
		$("#weekEndDateSel").val(weekEnd);
	}

	function isInteger(data) {
		var len = data.length;
		var c;
		for (var i = 0; i < len; i++) {
			c = data.charAt(i).charCodeAt(0);
			if (c<48 || c>57) {
				return false;
			}
		}
		return true;
	}

	function isNumeric(hrsVar) {
		val = $(hrsVar).val();
		if (!val || (typeof val != "string" || val.constructor != String)) {
			return isInteger(val);
		}
		var isNumber = !isNaN(new Number(val));
		if (isNumber) {
			if (val.indexOf('.') != -1) {
				return (true);
			} else {
				return isInteger(val);
			}
		} else {
			return isInteger(val);
		}
		/*
		var data = $(hrsVar).val();
		if (/\./.test(data)) {
		  return true;
		} else {
			return isNumber(data);
		}
		, */
	}

	//changed for redmine task #193
	function addDataTableSearch(table) {
		
		timehrsTable = $(table).dataTable({
			"sSwfPath" : "resources/media/swf/copy_csv_xls_pdf.swf",
			"bStateSave" : true,
			"fnStateLoadParams" : function(oSettings, oData) {
				oData.oSearch.sSearch = "";

			},
			"iCookieDuration" : 60,
			//	"sDom": 'T<"clear">lfrtip',
			"sDom" : 'T<"clear">lfrti<"top">rt<"bottom"ip<"clear">',
			"fnInitComplete" : function(oSettings, json) {
				//stopProgress();
			},
			"oTableTools" : {
				"aButtons" : [ {
					"sExtends" : "xls",
					"sButtonText" : "Save As XLS",
					"sFileName" : "Resource Timehours Entry.xls"
				} ]
			},
			//"sDom": 'lfrtip',
			"sPaginationType" : "full_numbers",
			"bDestroy" : true
		});

		return timehrsTable;
	} //changed for redmine task #193
	function initTable(table) {
		$(table).dataTable({
			"sSwfPath" : "resources/media/swf/copy_csv_xls_pdf.swf",
			"bStateSave" : true,
			"iCookieDuration" : 60,
			//"sDom": 'T<"clear">lfrtip',
			"sDom" : 'T<"clear">lfrtip<"top">rt<"bottom"ip<"clear">',
			// "sDom": 'T<"clear">lfrtip<"top">rt<"bottom"ip<"clear">',
			"fnInitComplete" : function(oSettings, json) {
				stopProgress();
			},
			"oTableTools" : {
				"aButtons" : [ {
					"sExtends" : "xls",
					"sButtonText" : "Save As XLS",
					"sFileName" : "Resource List.xls"
				} ]

			},
			//"sDom": 'lfrtip',
			"sPaginationType" : "full_numbers",
			"bDestroy" : true
		});

	}


	$(document.body).on('change', '#timeSheetStatus', function() {

		timeSheetStatus = document.getElementById('timeSheetStatus').value;
		if (timeSheetStatus == 'P') {
			$("#inner").find('input[type="button"]').show();
			$("#inner").find('input[type="button"]').button({
				disabled : false
			});
		} else
			$("#inner").find('input[type="button"]').hide();
		// $("#inner").find('input[type="button"]').button({disabled:true});
		// var id = document.getElementById("employeeIdHidden").value;
		var temp = "${j}";
		if (temp == 1) {
			if (timehrsTable != null)
				timehrsTable.fnClearTable();
			var TempWeekEndDateHyper = ("${WeekEndDateHyper}");
			<c:forEach var="resource" items="${timehrses}">
			var id = "${resource.employeeId}";

			$.getJSON("", {
				find : "ByResourceIdUpdatedHyper",
				resourceId : id,
				weekEndDate : TempWeekEndDateHyper,
				timeSheetStatus : timeSheetStatus
			}, showTimehrsHyper);
			</c:forEach>
		} else {
			var id = document.getElementById("employeeIdHidden").value;
			var activeOrAll = document.getElementById("activeOrAll").value;
			$.getJSON("", {
				find : "ByResourceIdUpdated",
				resourceId : id,
				timeSheetStatus : timeSheetStatus,
				activeOrAll : activeOrAll
			}, showTimehrs);
		}
		//$.getJSON("",{find:"ByResourceIdUpdated" ,resourceId:id,timeSheetStatus:timeSheetStatus}, showTimehrs);
	});

	function openMaintainance(id, yashEmpId) {
		
		//setTimeout(function(){ startProgress(); }, 30);
		startProgress();
		document.getElementById("timeHrsId").value = yashEmpId;
		document.getElementById("employeeIdHidden").value = id;
		getTimehrsById(id);

		displayMaintainance();

		timehrsTable = addDataTableSearch($('#timehrsTableId2'));
		timehrsTable.fnSort([ [ 5, 'desc' ] ]);

		document.getElementById("inner").innerHTML = "<label>	End Date : </label>"
				+ " <input type='text' name='weekEndDateSel' id='weekEndDateSel' size='16' maxlength='80' value='' readonly='readonly' />"
				+ "<a href='#' class='blue_link linkMargin TimesheetAddNew' id='addNew' >"
				+ "<img src='resources/images/addUser.gif' width='16' height='22' />"
				+ "<span>Add New</span>"
				+ " </a>"
				+ "&nbsp;&nbsp;"
				+ "Status : <select id='timeSheetStatus'>"
				+ "<option value=' '>All</option>"
				+ "<option selected='selected' value='P'>Review</option>"
				+ "<option  value='R'>Rejected</option>"
				+ "<option value='N'>Not Submitted</option>"
				+ "<option value='A'>Approved</option>"
				+ "</select>"
				+ "&nbsp;<input type='button' value = 'Approve All' id='approveAll' class='TimesheetAprvAllbtn approveBtnPosition' >"
				+ "&nbsp;" + "<br>" + "<br>";

		/*  var rows = $("#timehrsTableId2").dataTable().fnGetNodes();
		 if(rows.length == 0)
			{
			  $("#inner").find('input[type="button"]').button({disabled:true});
			} */

		$("#weekEndDateSel").datepicker({
			showOn : "button",
			buttonImage : "resources/images/calendar.gif",
			buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			dateFormat : 'dd-M-yy',
			selectWeek : true,
			maxDate : 0,
			onSelect : function(dateText, inst) {
				$('#tblActivity > tbody').find('tr').remove();
				getWeekDates(dateText);
			}
		});
		/* stopProgress(); */
	}

	function getTimehrsById(id) {
		gResourceId = id;
		//$.getJSON("resources",{find:"FindResourceByResourceId",resourceId:id},setCurrentResource);
		$.getJSON("resources", {
			find : "FindResourceByResourceId",
			resourceId : id
		},
				function(resource) {

					currentResource = resource;
					resDateOfJoining = resource.dateOfJoining;
					gEmployeeId = resource.employeeId;
					gYashEmployeeId = resource.yashEmpId;
					gEmployeeName = resource.firstName + " "
							+ resource.lastName;

					var temp = "${j}";
					if (document.getElementById('timeSheetStatus') != null)

						if (temp == 1) {
							//alert("if ");
							var TempWeekEndDateHyper = ("${WeekEndDateHyper}");
							<c:forEach var="resource" items="${timehrses}">
							var id = "${resource.employeeId}";

							$.getJSON("", {
								find : "ByResourceIdUpdatedHyper",
								resourceId : id,
								weekEndDate : TempWeekEndDateHyper,
								timeSheetStatus : timeSheetStatus
							}, showTimehrsHyper);
							</c:forEach>
						} else {
							var activeOrAll = document
									.getElementById("activeOrAll").value;
							$.getJSON("", {
								find : "ByResourceIdUpdated",
								resourceId : gEmployeeId,
								timeSheetStatus : timeSheetStatus,
								activeOrAll : activeOrAll
							}, showTimehrs);
						}

				});

	}

	function setCurrentResource(resource) {
		currentResource = resource;
	}
	function setResourceAllocated(resourcesAllocated) {
		allocatedResources = resourcesAllocated;
	}

	function showTimehrs(data) {
		if (timehrsTable != null)
			timehrsTable.fnClearTable();
		$("#timehrsTableId2 > tbody:last").append(
				$("#timehrsTableRows").render(data));

		timeSheetStatus = document.getElementById('timeSheetStatus').value;
		if (jQuery.isEmptyObject(data))
			$("#inner").find('input[type="button"]').button({
				disabled : true
			});

		addDataTableSearch($('#timehrsTableId2'));
		$(".various").fancybox({
			fitToView : true,
			autoSize : true,
			//closeClick	: true,
			type : 'ajax'
		});
		containerWidth();
		displayCheckApproveImage(data);

		stopProgress();
	}

	function showTimehrsHyper(data) {

		var TempWeekEndDateHyperNew = ("${WeekEndDateHyper}");
		var timeSheetStatus = document.getElementById('timeSheetStatus').value;
		/* if(timehrsTable != null)timehrsTable.fnClearTable();  */
		$("#timehrsTableId2 > tbody:last").append(
				$("#timehrsTableRows").render(data));

		timeSheetStatus = document.getElementById('timeSheetStatus').value;

		if (timeSheetStatus == 'P') {
			$("#inner").find('input[type="button"]').show();
			$("#inner").find('input[type="button"]').button({
				disabled : false
			});
		} else {
			$("#inner").find('input[type="button"]').hide();
		}

		/* if(jQuery.isEmptyObject(data))
			 $("#inner").find('input[type="button"]').button({disabled:true}); */

		addDataTableSearch($('#timehrsTableId2'));
		$(".various").fancybox({
			fitToView : true,
			autoSize : true,
			//closeClick	: true,
			type : 'ajax'
		});
		containerWidth();
		displayCheckApproveImage(data);

		//setTimeout(function(){  stopProgress();}, 1);
		stopProgress();
		$(".tab_seaction .tabs a[href='#tab1']").unbind('click'); //disable list click

		//			$("blue_link linkMargin TimesheetAddNew.addNew").unbind('click'); 

		$('#addNew').hide();
		$('#weekEndDateSel').val(TempWeekEndDateHyperNew); 
		$('#weekEndDateSel').next('img').hide();

	}

	function displayCheckApproveImage(data) {

		var rowCount = $('#timehrsTableId2 >tbody >tr').length;
		var count = 0;
		if (rowCount > 0) {
			$.each(data, function(key, val) {
				var d = new Date();
				var test = true
				if (!test) {
					var id = 'approveStatusId' + count;
					$("#" + id).toggle();
				}
				count++;
			});
		}

	}
	function displayMaintainance() {
		$("#maitainanceId").css("display", "inline-block");
		$("ul.tabs a").removeClass('active');
		$(".tab_div").hide().next("#tab2".hash).show();
		$('a[href$="tab2"]').removeClass('MaintenanceTab');
		$('a[href$="tab2"]').addClass('active');

		$("#comment").hide();
		containerWidth();
	}
	function updateRowData(attr) {
		editRow(timehrsTable, attr);
	}
</script>

<script type="text/javascript">
	//for time hrs reject functionality//
	$("a#comment_timehrsList").fancybox({
		'href' : '#comment',
		'titleShow' : false,
		'transitionIn' : 'elastic',
		'transitionOut' : 'elastic',
		modal : true,

	});
</script>

<script type="text/javascript">
	/*------------------------------Blur Function For Validation------------------------------------*/
	$(document).on('blur', '.tbl tbody input[type=text].requir', function(e) {
		var dataTblInputVal = $(this).val();
		if (dataTblInputVal == "") {
			$(this).css("border", "1px solid #ff0000");
		} else {
			$(this).css("border", "1px solid #D5D5D5");
		}
	});
</script>

<div id="timesheetApprovalYashRMS" class="content-wrapper">
	<!--right section-->
	<div class="botMargin">
		<h1 class="fl">Timesheet Approval </h1>
		<div class="fr positionRel">
			<img src="resources/images/helpIcon.png" id="helpIcon" />
			<div class="helpContent" style="display: none;">
				<h3>To approve/review/reject time sheet.</h3>
				<table>
					<tr>
						<td>
							<ol class="orderList">
								<li>Click on employee id.</li>
								<li>Make sure your employee had submitted his timsheet.</li>
								<li>Edit and populate planned, billed hours and remarks.</li>
								<li>you can now see <img
									src="resources/images/review_icon.png" /> to Approve time
									sheet.
								</li>
								<li>you can now see &nbsp <img
									src="resources/images/reject_timesheet.png" height="16"
									width="16"> &nbsp to Reject time sheet.
								</li>
								<li><font color="red">Not able to see <img
										src="resources/images/review_icon.png" />: Check if employee
										had submitted timesheet.
								</font></li>
								<li><font color="red">Review timesheet : Timesheet
										can be marked review only by admins. Please consult your
										admin.</font></li>

								<li><font color="red"> Not able to see <img
										src="resources/images/reject_timesheet.png" height="16"
										width="16" /> if user has not submitted timesheet.
								</font></li>
							</ol>
						</td>
					</tr>
				</table>
				<div class="closeHelp"></div>
				<img src="resources/images/arrowIcon.png" class="arrowIcon" />
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<div class="tab_seaction">
		<ul class='tabs'>

			<li><a href='#tab1'>List</a></li>

			<li><a class="MaintenanceTab" id="MaintenanceTabInactive"
				href='#tab2'>Maintenance</a></li>
		</ul>
		<div id='tab1' class="tab_div">

			<div class="search_filter_outer fr">

				<div class="search_filter" style="text-align: right;">
					<form id="frmSearch" name="frmSearch" method="post" action=""
						class="frmSearch" style="float: none;">
						<div class="btnIcon TimeapprvlStatus">

							<!-- <a href="#" class="blue_link" id="update"> <img
							src="resources/images/edit.png" width="16" height="22" /> Update
						</a> -->

							<span class="resorceAllDrpdn">&nbsp; Status <select
								id="activeOrAll">

									<option value="active"  selected='selected'>Active</option>
									<option value="all">All</option>


							</select>
							
							<select id='newTimeSheetStatus'>
									<option value='M' selected='selected'>My Projects</option>
									<option value='L'>All</option>
									<option value='P'>Pending For Approval</option>
								
							</select>
							</span>

						</div>


					</form>

					<div class="clear"></div>
				</div>
			</div>

			<div class="tbl">
				<input type="hidden" name="allocForManager" id="allocForManager"
					value="${checkAllocForManager}" /> <input type="hidden"
					name="timeHrsId" id="timeHrsId" /> <input type="hidden"
					name="employeeIdHidden" id="employeeIdHidden" />
				<div id="timesheetApproveTbl">
					<table width="100%"
						class="tablesorter fl dataTbl my_table positiondashboard-table white-sort-icons timesheetApproval"
						cellspacing="1" id="timesheet_table" cellpadding="2">
						<thead>
							<tr>
								<th width="8%" align="left" valign="middle" >Employee ID</th>
								<th width="5%" align="left" valign="middle">Employee Name</th>
								<th width="5%" align="left" valign="middle">Designation</th>
								<th width="9%" align="left" valign="middle">Grade</th>
								<th width="30%" align="left" valign="middle">Project Allocated</th>
							</tr>
							   <tr class="">
								<td><input type="text" name="search_empID" placeholder="Emp ID" class="search_init" /></td>
							<td><input type="text" name="search_empName"   placeholder="Emp Name" class="search_init" /></td>
							<td><input type="text" name="search_emp status"placeholder="Designation" class="search_init" /></td>
							<td><input type="text" name="search_grade"  	placeholder="Grade" class="search_init" /></td> 
							<td> <input type="text" name="search_prj" placeholder="Project Allocated"
								class="search_init"  readonly="readonly" /> </td> 
						</tr> 
						</thead>
					
					</table>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<div id='tab2' class="tab_div">
			<c:if test="${navigationFlag=='true'}">
				<br>
				<img src="resources/images/arrow_left.gif" />
				<a href="resourceallocations" onclick="openResourceAllocation();"
					id="navigationToResAlloc"><strong>Back to Resource
						Allocation </strong></a>
			</c:if>
			<br> <br>
			<div class="tbl">
				<div class="search_filter search_filterLeft search_filtertimeaprvl">
					<div align="left">
						<div id="inner" style="position: relative; left: 40px;"></div>
					</div>

				</div>
				<form action="timehrses" method="post" id="updateRecords">
					<br>
					<table width="100%" class="tablesorter dataTbl addNewRow_thm fl"
						cellspacing="1" cellpadding="2" id="timehrsTableId2">
						<thead>
							<tr>
								<th width="10%" align="left" valign="middle">Employee ID</th>
								<th width="14%" align="left" valign="middle">Employee Name</th>
								<th width="14%" align="left" valign="middle">Designation</th>
								<th width="13%" align="left" valign="middle">Project Name</th>
								<th width="10%" align="left" valign="middle">Planned Hours</th>
								<th width="10%" align="left" valign="middle">Week End Date</th>
								<th width="10%" align="left" valign="middle">Reported Hours</th>
								<th width="10%" align="left" valign="middle">Billed Hours</th>
								<th width="10%" align="left" valign="middle">Remarks</th>
								<th width="10%" align="left" valign="middle">Approval</th>
								<th width="10%" align="left" valign="middle">Current Status</th>
								<th width="10%" align="left" valign="middle">Rejection</th>
							</tr>
						</thead>
						<tbody id="addDatafornewRow">

						</tbody>

					</table>

				</form>

			</div>
			<div class="clear"></div>
		</div>
		<div id="comment" align="center" name="comment"
			style="border: 2px solid;">
			<table>
				<tr>
					<td align="center" colspan="2"><strong>Rejection
							Comments:</strong></td>

				</tr>
				<tr>
					<td align="center" colspan="2"><textarea rows="4" cols="30"
							id="rejectionTxt"></textarea></td>

				</tr>
				<tr>

					<td colspan="1" align="left"><input type="button" value="Ok"
						id="ok"></td>
					<td colspan="1" align="right"><input type="button"
						value="Cancel" id="cancel"></td>

				</tr>
			</table>
		</div>
	</div>
	<!--START: Alert: Added by Pratyoosh Tripathi -->
	<div class="notification-bar">
		<!-- <div class="close closeIconPosition">close</div> -->
		<span class="toast-close close"><span
			title="Close Notification">&nbsp;&nbsp;</span></span>
		<!-- <div class="notification-text">Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.</div> -->
		<sec:authorize
			access="hasAnyRole('ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_ADMIN')">
			<div class="notification-text">You can approve or reject the
				timesheets of a resource clicking on its Employee id.</div>


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
		function openResourceAllocation() {

			var empDateOfJoining = resDateOfJoining.split("/").join("-")
			var _href = $('#navigationToResAlloc').attr('href');
			var res = _href.substring(0, 19);
			var requestParam = "?empDateOfJoining=" + resDateOfJoining + "&"
					+ "empId=" + gEmployeeId + "&" + "yashEmpId="
					+ gYashEmployeeId + "&" + "empName=" + gEmployeeName;
			$('#navigationToResAlloc').attr("href", res + requestParam);

		}
	</script>

	<!--END: Alert: Added by Pratyoosh Tripathi -->
	<!--right section-->
</div>

<div id="NoAllocMessage" class="NoAllocMessage">Project is not
	allocated . Please contact to administrator.</div>

<script>
	var temp = "${j}";
	if (temp == 1) {
		function myFunction() {
			setTimeout(function() {
				openMaintainance(temp, temp);
			}, 2);
		}
		myFunction();

	}
</script>
