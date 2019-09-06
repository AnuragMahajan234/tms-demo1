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
 table#dtbl thead tr:first-child th:nth-child(even)		{ background-color:#3c8dbc; } 
table#dtbl thead tr:first-child th:nth-child(odd)		{ background-color: #01498B;}
	</style>
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}" var="style_css"/>
	<link href="${style_css}" rel="stylesheet" type="text/css"></link>
	<script src="${multiselect_filter_js}" type="text/javascript"></script>
<style type="text/css" title="currentStyle">
thead input {
	width: 100%
}

input.search_init {
	color: #999
}
.projecttoolbar {border: -65px solid red; width: 50%; float:left; }
.blue_link{left: -200}
#defaultTableId {table-layout: fixed;}
#defaultTableId thead th { width:120px;}
#defaultTableId td {word-wrap: break-word;}
</style>
<div class="content-wrapper">
	<div class="botMargin">
		<h1>Default Projects</h1>
	</div>
	<div class="tab_seaction">
		<ul class='tabs'>
			<li><a href='#tab1'  id="tab1id" >List</a></li>
			<li><a class="MaintenanceTab" id="MaintenanceTabInactive" href='#tab2'>Maintenance</a></li>
		</ul>
			<div id='tab1' class="tab_div">

					
			<div class="tbl">
	<p></p><p></p><p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</p>
				<table class="dataTbl display tablesorter dataTable" id="defaultTableId">
					<thead>
					<tr>
							<th width="15%" align="center" valign="middle">BG-BU Name</th>
							<th width="20%" align="center" valign="middle" >Default Project</th>
							<th width="25%" align="center" valign="middle" >Allocation Type</th>
							</tr>
	</thead>
						<input type="hidden" id="saveEditId" name="saveEdit" />
					<c:forEach items="${defaultprojects}" var="defaultprojects">   
					<input type="hidden" id="hiddenId" name="id" value= "${defaultprojects.id}"/>
					<tr class="even" id="${theCount.index}">
								<td align="center" valign="middle" id="oldRow"
									onclick="openMaintainance(${defaultprojects.id},${defaultprojects.orgHierarchy.id}, ${defaultprojects.projectId.id},${defaultprojects.allocationTypeId.id},'${defaultprojects.orgHierarchy.parentId.name}-${defaultprojects.orgHierarchy.name}','${defaultprojects.projectId.id}');">
									<a href="#"> ${defaultprojects.orgHierarchy.parentId.name}-${defaultprojects.orgHierarchy.name} </a>
								</td>
								
   <td>
     ${defaultprojects.projectId.projectName}</td>
     <td> 
   ${defaultprojects.allocationTypeId.allocationType}</td>
     </tr>
</c:forEach>
		<tbody></tbody>	</tbody></table></div><div class="clear"></div></div>

	<div id='tab2' class="tab_div">
			
	<div class="form">

<form name="defaultFormName"  action="defaultprojects/saveProject" method="post" id="defaultForm">
<table id="defaultTable">
				<tr><input type="hidden" id="formHiddenId" name="id" /></tr>
	                        <tr> <td align="left"> &nbsp &nbsp &nbsp &nbsp &nbsp BG-BU  :</td>
							<td align="left"><select name="orgHierarchy.id"
								class="  check" id="orgHierarchy">
								
									
							</select></td>
							
							<td align="left"> &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp Project  :</td>
							<td align="left"><select name="projectId.id"
								class="required  check" id="projectId">
									
							</select></td>
							
							
							<td align="left"> &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp Allocation Type  :</td>
							<td align="left"><select name="allocationTypeId.id"
								class=" check " id="allocationTypeId">
									
							</select></td>
							
							<td>
							&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp
							
						<a href="#" class="blue_link" id="save"> <img
							src="resources/images/save.png" name="save" width="16"
							height="22" /> Save
						</a>
					
							
							</td>
							</tr>
														</table>
						</form></div>
			<div class="clear"></div>
		</div>
	</div>
</div>
<script>

// add new
$(document).on("click",'#addUpdate', function(){
	 $('#saveEditId').val("save");
	 $('#projectId ').val(-1);
	 $('#allocationTypeId ').val(-1);
$('#formHiddenId').val("");
	displayMaintainance();
	//get all bg-bu list
	 $.ajax({
			type: 'GET',
		    url: '/rms/defaultprojects/getAll/',
		      contentType: "application/json; charset=utf-8",
		   
		    async:true,
		    success: function(data) { 
		    
		    if(data.length!=0){
				var htmlVar='<option value="-1">Select One</option>';
				
			$.each(data, function(key,val){
				
					htmlVar +='<option value="'+data[key].id+'">'+data[key].parentId.name+'-'+data[key].name+'</option> ';	
				
					
			});

			var select = $('#orgHierarchy');
			select.empty().append(htmlVar);}
		    	
		    },
		    error: function() {
		    	 showError("Some error occured");
		      }
		}); 
	
	 $('#orgHierarchy option').val(-1);
});


////////////////////////////////////////////////////////////////////
 $('a[href$="tab1"]').click(function(){
	
		$("input.search_init").each(function(){
		var default_value = $(this).prop("defaultValue");
		$(this).val(default_value);
	});}); 
	
///////////////////////////////////////////////////////////////////
function displayMaintainance()
{
    $("#maitainanceId").css("display","inline-block");
     $("ul.tabs a").removeClass('active');
     $(".tab_div").hide().next("#tab2".hash).show();
     $('a[href$="tab2"]').removeClass('MaintenanceTab');
     $('a[href$="tab2"]').addClass('active');
     $("#comment").hide();
     $.fancybox.close();
}

$('ul.tabs a').click(function () {
	    $(".tab_div").hide().filter(this.hash).show();
	    $("ul.tabs a").removeClass('active');
	    $('a[href$="tab2"]').addClass('MaintenanceTab');
	    $(this).addClass('active');                 
	    return false;
 }).filter(':first').click();
///////////////////////////////////////////////////////////////

//save new default project
$('#save').on('click', function() {
	var errmsg="Please select:";
	var validationFlag = true;
	var buid=$('#orgHierarchy').val();
	var allocid=$('#allocationTypeId').val();
	var projid=$('#projectId').val();
	if(document.getElementById("orgHierarchy").value=="-1"){
		errmsg=errmsg+" <br>  BG-BU ";
		validationFlag = false;
	}
	 if(document.getElementById("allocationTypeId").value=="-1"){
		 errmsg=errmsg+"<br> Allocation ";
			validationFlag = false;
	}
	if (document.getElementById("projectId").value=="-1"){
errmsg=errmsg+"<br> Project ";
    	
    	validationFlag = false;
	}
	
	if(!validationFlag){
		stopProgress();
		if(errmsg.length > 0) 							
			showError(errmsg);							
		return;
	}
	 else{
		element1 = document.getElementById("formHiddenId");
if(element1!=null && document.getElementById("saveEditId").value=="save"){
	//alert(buid);
	 $.ajax({
			type: 'GET',
		    url: '/rms/defaultprojects/getAllExistingEntries/'+buid,
		      contentType: "application/json; charset=utf-8",
		    async:true,
		    success: function(data) { 
		    	//  alert("sucess"+data.status);
		    	 $.ajax({
		 			type: 'POST',
		 		    url: '/rms/defaultprojects/saveProject/',
		 		
		 		   data:$("#defaultForm").serialize(),
		 		    async:true,
		 		    success: function(data) { 
		 		
		 		    	var text="Default Project has been saved";
		 		    	showSuccess(text);
		 		    	location.reload();
		 		    },
		 		    error: function() {
		 		    	showError("some error occured while saving!!");
		 		      }
		 		}); 
		    },error: function(data) {
		    	 //alert("error"+data.status);
		    	showError("Cannot save!! Project for this BG-BU already exist");
		    	stopProgress();
		      }
		}); 
	}
else if (document.getElementById("saveEditId").value=="edit"){
	$.ajax({
			type: 'POST',
		    url: '/rms/defaultprojects/saveProject/',
		
		   data:$("#defaultForm").serialize(),
		    async:true,
		    success: function(data) { 
		
		    	var text="Default Project has been saved";
		    	showSuccess(text);
		    	location.reload();
		    },
		    error: function() {
		    	showError("some error occured while saving!!");
		      }
		}); 
	}
else{
	showError("some error occured");
}
} 
	



}); 

//get all allocation types
$(document).ready(function(){
	 $.ajax({
		type: 'GET',
	    url: '/rms/allocationtypes/allocation/',
	      contentType: "application/json; charset=utf-8",
	   
	    async:true,
	    success: function(data) { 
	    if(data.length!=0){
			var htmlVar='<option value="-1">Select One</option>';
			
		$.each(data, function(key,val){
			
				htmlVar +='<option value="'+data[key].id+'">'+data[key].allocationType+'</option> ';	
		});

		var select = $('#allocationTypeId');
		select.empty().append(htmlVar);}
	    	
	    },
	    error: function() {
	    	 showError("Some error occured");
	      }
	}); 
	 
	 $('#defaultTableId').DataTable( {
	        "order": [ [0,'asc'],[ 1, 'asc' ],[2, 'asc' ]],
	   "sDom": '<"projecttoolbar">lfrtip'
	
	    } ); 
	 <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')"> 
		$("div.projecttoolbar").html(
				'<div class="btnIcon">'+
					'<a href="#"  class="blue_link" id="addUpdate" >'+
						'<img src="resources/images/addUser.gif" width="16" height="22" /> '+
					'Add New </a>'+
				'</div>');
		</sec:authorize>
	 
	 
	 
});

//on change of bu populate project dropdown
$('#orgHierarchy').on('change', function() {
	var count=0;
if(count>0){
	$('#ProjectId').val(-1);

}
	var value;
		   value= $(this).val();
		   if(value!=-1){
		$.getJSON("/rms/projects/getAllProjectByBUid/"+value,{}, showProject);
		   }
		count++;
	}); 
	
	
// show project in dropdown
function showProject(data){
	var htmlVar='<option value="-1">Select One</option>';
	var select = $('#projectId');
	select.empty().append(htmlVar);

if(data.length!=0){
	//var htmlVar='';
	
$.each(data, function(key,val){
	
		htmlVar +='<option value="'+data[key].id+'">'+data[key].projectName+'</option> ';	
		});
//var select = $('#projectId');
select.empty().append(htmlVar);
}
else{//alert("in else");
	showError("Projects not found for this BG-BU");
}
}

//  show allocation in dropdown

function showAllocationType(data){
//	alert(data);
	if(data.lenght!=0){
		var htmlVar='<option value="-1">Select One</option>';
		
	$.each(data, function(key,val){
			htmlVar +='<option value="'+data[key].id+'">'+data[key].allocationType+'</option> ';	
			
			});

	var select = $('#AllocationId');
	select.empty().append(htmlVar);
	}
	else{
		showError("Some error occurred ");
		}
	}

$('#MaintenanceTabInactive').off('click');	
    
    // edit function
 function openMaintainance(id,buid,projId,allocId,value,value1) {
        displayMaintainance();
        
        var select = $('#orgHierarchy');
        var htmlVar='<option value="'+buid+'">'+value+'</option>';
		select.empty().append(htmlVar);
		
        //get project of bu to be edited
    
         $.ajax({
		type: 'GET',
	    url: '/rms/projects/getAllProjectByBUid/'+buid,
	      contentType: "application/json; charset=utf-8",
	   	    async:true,
	    success: function(data) { 
	    	
	    if(data.length!=0){
	    	var htmlVar1='<option value="-1">Select One</option>';
		$.each(data, function(key,val){
			
				htmlVar1 +='<option value="'+data[key].id+'">'+data[key].projectName+'</option> ';	
				
			});
		var select1 = $('#projectId');
		select1.empty().append(htmlVar1);}
	select1.val(value1);
 
	    },
	    error: function() {
	    	 showError("Some error occured");
	      }
	}); 
      
      //  now set values for edit
 
      $('#allocationTypeId').val(allocId);
      $('#formHiddenId').val(id);
      $('#saveEditId').val("edit");
}
</script>


   
   
   
   
   <!--START: Alert: Added by Pratyoosh Tripathi -->			
				<div class="notification-bar">
<!-- <div class="close closeIconPosition">close</div> -->
<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
  <!-- <div class="notification-text">Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.</div> -->
  <sec:authorize
				access="hasAnyRole('ROLE_BG_ADMIN','ROLE_ADMIN')">
				<div class="notification-text">You can set a default project for your unit once a new resource has been created and then default project will be allocated automatically.</div>
						
	</sec:authorize>
	<sec:authorize
				access="hasAnyRole('ROLE_USER','ROLE_HR')">
				<div class="notification-text">Now user can set a default project for a particular BG-BU, So any new resource created under this BG-BU will be allocated to the default project.</div>			
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
   