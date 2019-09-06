<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<spring:url value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />
<spring:url value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}"
	var="multiselect_js" />
<script src="${multiselect_js}" type="text/javascript"></script>

<spring:url value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}"
	var="multiselect_filter_js" />
<%-- <script src="${multiselect_filter_js}" type="text/javascript"></script> --%>
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />

</head>
<body>
<style>
.ui-combobox .uiDropDown {
	max-width: 80% !important;
}
.ui-combobox .ui-button {
	max-width: 20% !important;
}
#requirement input[type="text"] {
	max-width: 60px !important;
	padding: 6px 0px !important;
}
.modal-body {
    max-height: 400px;
    overflow-y: auto;
}
.modal-dialog
{
width:55%;
}
</style>

<script type="text/javascript">

var project;

$(document).ready(function (){
	
	$('#edit-exist1').hide();
 	$('#newProjectTable').hide();
 	$('#newProj').hide();
 	//$('#oldProjectTable').hide();
	$('#projects').hide();
	//$('#oldProjectTableList').hide();
	$('#resource_req').hide();
	$('#requirement tr').removeClass('odd');
	//$("#projects").attr("disabled", "disabled").val('');

	//$('#projects').attr('disabled',false);
//	document.getElementById("projects").disabled = true;
	$("#client").on('change', function(){
		startProgress();
		 var clientId = $(this).val();
		$.ajax({
    		type: 'GET',
    		dataType: 'json',
            url: '/rms/requests/getProjects/'+clientId, 
            cache: false,
            contentType:"application/json; charset=utf-8",
            success: function(data) { 
            	stopProgress();
				var option="";
				 $('#projects').empty();
				// $('#projects').attr('disabled',false);
				 for(var i=0; i<data.length; i++){
					  var user = data[i];
		                option = option + "<option value='"+user[2] + "'>"+user[10] + "</option>";
		                
		            }
				 $("#projects").append(option);
	     	},
	     	error: function(error){
	     		stopProgress();
	     		showError("Something happend wrong!!");
	     	}
		});
		
	});
	
});

$(window).load(function(){
	$('#requirement tr').removeClass('odd');
});

$(function() {
	$('#mailList').multiselect({
		includeSelectAllOption: true
	}).multiselectfilter();
});

$(function() {
	
	$('#mailListCC').multiselect({
		includeSelectAllOption: true
	}).multiselectfilter();
});

$(document).ready(function (){
$(document).on('click','.addIcon',function(){
	var trID = $("#requirement tr").length;
	
	var appendTxt = "<tr><td  align='center'>"
					+"<img  src='resources/images/addUser.gif' id='add"+trID+"' class='addIcon'>&nbsp;&nbsp;" 
					+"<select id='allSkills"+trID+"' inp='allSkills' class='required comboselect check'  name='allSkills'>"
					+"<option value='0'>Select</option><c:forEach var='skills' items='${secondrySkills}'>"									
					+"<option value='${skills.id}' >${skills.skill}</option></c:forEach></select></td><td align='center'>" 
					+"<input id='resources"+trID+"' type='text' class='resources_cls'  inp='resources'/></td><td  align='left'>" 
					+"<select id='designation"+trID+"' inp='designation' class='required comboselect check'  name='allDesigs'>"
					+"<option value='0'>Select</option>"
					+"<c:forEach var='designation' items='${designation}' >"									
					+"<option value='${designation.id}' >${designation.designationName}</option>"										
					+"</c:forEach></select></td><td  align='center'>" 
	 				+"<input type='text' class='experience_cls'  inp='experience'  id='experience"+trID+"'/></td>"  
					+"<td  align='left'><select class='required comboselect check' name='billing' inp='billing' id='billing"+trID+"'>"
					+"<option value='-1'>Select</option>"
					+"<c:forEach var='allocationtype' items='${resourceAllocation}'>"
					+"<option value='${allocationtype.id}'>${allocationtype.allocationType}</option>"
					+"</c:forEach></select></td><td  align='center'>"  
					+"<select class='required comboselect check'  name='type' inp='type' id='type"+trID+"'><option value='-1'>Select</option>"
					+"<option value='1'>New Requirement</option><option value='0'>Replacement</option></select></td>"
					+"<td align='center'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id='requirementId"+trID+"' type='text' class='type_cls' inp='reqId'/>" 
					+"&nbsp;&nbsp;<img src='resources/images/remove.png' id='remove"+trID+"' align='absMiddle' class='remove' width='16' height='16'>"
					+"</td>"
					+"<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id='addSecondarySkillButton"+trID+"' href='javascript:void(0)' data-toggle='modal' data-target='#addSecondarySkill'>Add More Skills</a><input type='hidden' id='hiddenSkills"+trID+"'  inp=hiddenSkills' value=''/>&nbsp;&nbsp;<a id='edit-exist"+trID+"' class='edit-exist-class' href='javascript:void(0)'>Edit Skills</a> </td></tr>";
	
	$("#requirement tr:last").after(appendTxt);
	$( ".comboselect" ).combobox();
	$("#edit-exist"+trID-- +"").hide();
});


$(document).on("click", '.remove', function(e){
  	$(this).closest('tr').remove();
  	
/* To update ids of all the fields after removing tr*/    
  	$("#requirement tr").each(function(index){
  		$(this).find("input[type='text']").each(function(){
	  		//alert($(this).attr("inp"));
	  		if($(this).attr("inp")=="resources"){
				 $(this).attr("id", "resources"+(index));
	  		}else if($(this).attr("inp")=="experience"){
				 $(this).attr("id", "experience"+(index));
			}else if($(this).attr("inp")=="reqId"){
				 $(this).attr("id", "reqId"+(index));
			}
  		});
  		$(this).find("select").each(function(){
	  		if($(this).attr("inp")=="allSkills"){
				 $(this).attr("id", "allSkills"+(index));
	  		}else if($(this).attr("inp")=="billing"){
				 $(this).attr("id", "billing"+(index));
			}else if($(this).attr("inp")=="type"){
				 $(this).attr("id", "type"+(index));
			}
  		});
  		$(this).find("img").each(function(){
	  		if($(this).attr("inp")=="add"){
				 $(this).attr("id", "add"+(index));
	  		}else if($(this).attr("inp")=="remove"){
				 $(this).attr("id", "remove"+(index));
	  		}
	  	});
	});
  	
  	$("#requirement tr img").each(function(index){
		$(this).attr("id", "remove"+(index+1)); 
	});
	
});

$(document).on('click','#submitForm',function(){
	var skillRequestId =0,requestId = 0,edit=0;
	 if(document.getElementById("requestId") != undefined){
		 requestId=document.getElementById("requestId").value;
	 }
	 if(document.getElementById("submitForm") != undefined){
		 edit=document.getElementById("submitForm").value;
	 }
	 if(document.getElementById("RRFPrimarySkills") != undefined){
		 edit=document.getElementById("RRFPrimarySkills").value;
	 }
	 
	 if(document.getElementById("skillRequestId") != undefined){
	 skillRequestId=document.getElementById("skillRequestId").value;
 	 } 
	 
	var validation = 0; 
	var errorMsg ="";
	var resourceNo =[];
	var skills = [];
	var designations = [];
	var experience = [];
	var billings = [];
	var type = [];
	var time = [];
	var mailIDs = [];
	var mailIDcc = [];
	var skillName = [];
	var secondaryskillName = [];
 

		project = document.getElementById("projects").value;
		var projectSelect = document.getElementById("projects");
		var selectedProjectName = projectSelect.options[projectSelect.selectedIndex].text;
		
		client = document.getElementById("client").value;
		var customer = document.getElementById("client");
		var selectedCustomerName = customer.options[customer.selectedIndex].text;
	
	
	for(var i=1; i<$("#requirement tr").length; i++){
		//for skill ID
		$("#allSkills"+i).each(function(){
			skills.push($(this).val());
		});
		//for SkillName
		$("#allSkills"+i).each(function(){
			var name = $(this).find('option:selected').text();
			skillName.push(name);
		});
		$("#resources"+i).each(function(){
			var res = $.trim($(this).val());
			resourceNo.push(res);
		});
		$("#designation"+i).each(function(){
			var desig = $(this).val();
			designations.push(desig);
		});
		$("#experience"+i).each(function(){
			var exp = $.trim($(this).val());
			exp=exp.replace(/\s/g, ""); 
			experience.push(exp);
		});
		 $("#billing"+i).each(function(){
			var bill = $(this).val();
			billings.push(bill);
		}); 
		$("#type"+i).each(function(){
			var arr = $(this).val();
			type.push(arr);
		});
		$("#requirementId"+i).each(function(){
			var arr = $.trim($(this).val());
			arr=arr.replace(/\s/g, ""); 
			time.push(arr);
		});
		 $("#hiddenSkills"+i).each(function(){
			var arr = $.trim($(this).val());
			secondaryskillName.push(arr);
		}); 
		
	}
	$("#mailList option:selected").each(function () {
		var id = $(this).val();
		mailIDs.push(id);
	});
	
	if($("#mailListCC option:selected").length<=0 || $("#mailListCC option:selected")==null){
		mailIDcc.push("0-no");
	}else{
		$("#mailListCC option:selected").each(function () {
			var id = $(this).val();
			mailIDcc.push(id);
		});
	}
	
	var numericflag=0;
	var skillflag=0;
	var resflag=0;
	var desflag=0;
	var expflag=0;
	var expRegexFlag=0;
	var bilflag=0;
	var typeflag=0;
	var reqIdFlag=0;
	var timeRegex=0;
	var duplicateRow=0;
	var addSkillRow=0;
	var regex = /^[0-9]+$/; 
	var expRegex = /^\d{1,2}.*?(-\d{1,2})?$/;
	var experienceTimeRegex = /^[0-9 -]+$/; 
	
	for(var i=1; i<$("#requirement tr").length; i++){
		$("#allSkills"+i).each(function(){
			var name = $(this).find('option:selected').text();
			if(name==null ||  name=="Select" || name==""){
				$(this).next().find(".ui-combobox-input").addClass("alertBorderRed");
				$(this).next().find(".uiDropDownArrow").addClass("alertBorderRed");
				skillflag++;
			}else
			{
				$(this).next().find(".ui-combobox-input").removeClass("alertBorderRed");
				$(this).next().find(".uiDropDownArrow").removeClass("alertBorderRed");
			}
			
		});
		$("#resources"+i).each(function(){
			var res = $.trim($(this).val());
			if(res==null || res=="" || res==0){
				$(this).addClass("alertBorderRed");
				resflag++;
			}else{
				$(this).removeClass("alertBorderRed");
			}
		});
		$("#designation"+i).each(function(){
			var des = $(this).val();
			if(des==null || des=="Select" || des==0){
				$(this).next().find(".ui-combobox-input").addClass("alertBorderRed");
				$(this).next().find(".uiDropDownArrow").addClass("alertBorderRed");
				desflag++;
			}else{
				$(this).next().find(".ui-combobox-input").removeClass("alertBorderRed");
				$(this).next().find(".uiDropDownArrow").removeClass("alertBorderRed");
			}
		});
		$("#experience"+i).each(function(){
			var exp = $.trim($(this).val());
			if(exp==null || exp==0 || exp==""){
				$(this).addClass("alertBorderRed");
				expflag++;
			}else if(!exp.match(expRegex)){
				$(this).addClass("alertBorderRed");
				expRegexFlag++;
			}else if(!exp.match(experienceTimeRegex)){
				$(this).addClass("alertBorderRed");
				expRegexFlag++;
			}else{
				$(this).removeClass("alertBorderRed");
			}
		});
		$("#billing"+i).each(function(){
			var bill = $(this).val();
			if(bill== -1 || bill=="Select"){
				$(this).next().find(".ui-combobox-input").addClass("alertBorderRed");
				$(this).next().find(".uiDropDownArrow").addClass("alertBorderRed");
				bilflag++;
			}else{
				$(this).next().find(".ui-combobox-input").removeClass("alertBorderRed");
				$(this).next().find(".uiDropDownArrow").removeClass("alertBorderRed");
			}
		});
		$("#type"+i).each(function(){
			var ty = $(this).val();
			if(ty== -1 || ty=="Select"){
				$(this).next().find(".ui-combobox-input").addClass("alertBorderRed");
				$(this).next().find(".uiDropDownArrow").addClass("alertBorderRed");
				typeflag++;
			}else{
				$(this).next().find(".ui-combobox-input").removeClass("alertBorderRed");
				$(this).next().find(".uiDropDownArrow").removeClass("alertBorderRed");
			}
		});
		$("#requirementId"+i).each(function(){
			var tim = $.trim($(this).val());
			if(tim== null || tim=="" || tim==0){
				$(this).addClass("alertBorderRed");
				reqIdFlag++;
			}
			else{
				$(this).removeClass("alertBorderRed");
			}
		});
		/* Regular Expression */
		$("#resources"+i).each(function(){
			var res = $.trim($(this).val());
			if(!res.match(regex)){
				numericflag++;
				$(this).addClass("alertBorderRed");
			}else{
				$(this).removeClass("alertBorderRed");
			}
		});
		$("#hiddenSkills"+i).each(function(){
			var res = $.trim($(this).val());
			if(res==null || res=="" || res==0){
				$(this).addClass("alertBorderRed");
				addSkillRow++;
			}else{
				$(this).removeClass("alertBorderRed");
			}
		});
		
	}
	
	//var comments = $("#myTextArea").val();
	var length = $("#requirement tr").length;
	

	if(((project==0 || project==""))) {
		$('#projects').next().find(".ui-combobox-input").addClass("alertBorderRed");
		$('#projects').next().find(".uiDropDownArrow").addClass("alertBorderRed");
	}else{
		$('#projects').next().find(".ui-combobox-input").removeClass("alertBorderRed");
		$('#projects').next().find(".uiDropDownArrow").removeClass("alertBorderRed");
	} 
	/* if(((document.getElementById('newProjectSelect').checked) && (project==0 || project==""))){
		$("#newProj").addClass("brdrRed");
	}else{
		$("#newProj").removeClass("brdrRed");
	} */
	
	for(var i=1; i<$("#requirement tr").length; i++){
		for(var j=1; j<=$("#requirement tr").length; j++){
			if(i!=j){
				if(($("#allSkills"+i).val()) == ($("#allSkills"+j).val())) {
					if(($("#designation"+i).val()) == ($("#designation"+j).val())){
						duplicateRow++;
					}
				}
			}
		}
	}
	
	
	if(project==0 || project=="" || project=="Select"){
		errorMsg = errorMsg + ("\u2022 Project cannot be empty! <br/>");
	 	validation++;
	}
	
	
	if(skillflag>0){
		errorMsg = errorMsg + "\u2022 Please set Skill! <br />";
	 	validation++;
	}
	
	if(resflag>0){
		errorMsg = errorMsg + "\u2022 Please set Resource! <br />";
	 	validation++;
	}else if(numericflag>0){
		errorMsg = errorMsg + "\u2022 Resource must be numeric! <br />";
	 	validation++;
	}
	
	if(desflag>0){
		errorMsg = errorMsg + "\u2022 Please set Designation! <br />";
	 	validation++;
	}
	
	if(expflag>0){
		errorMsg = errorMsg + "\u2022 Please set Experience! <br />";
	 	validation++;
	}
	if(expRegexFlag>0){
		var expErrorMsg = "\u2022 Experience must be numeric or numeric range! <br />";
	 	showError(expErrorMsg);
	 	validation++;
	}
	
	 if(bilflag>0){
		errorMsg = errorMsg + "\u2022 Please set Allcation Type ! <br />";
	 	validation++;
	} 
	
	if(typeflag>0){
		errorMsg = errorMsg + "\u2022 Please set Type! <br />";
	 	validation++;
	}
	
	
	if(reqIdFlag>0){
		errorMsg = errorMsg + "\u2022 Please set Time-Frame! <br />";
	 	validation++;
	}
	
	/* if(timeRegex>0){
		var expErrorMsg = "\u2022 Time must be numeric or numeric range! <br />";
	 	showError(expErrorMsg);
	 	validation++;
	} */
	
	if(duplicateRow>0){
		var errMsg = "\u2022 Cannot insert Duplicate Row! <br />";
		showError(errMsg);
	 	validation++;
	}
	if(addSkillRow>0){
		errorMsg = errorMsg + "\u2022 Please Set Detail JD(RRF) <br />";
	 	validation++;
	}
	
	if(mailIDs==null || mailIDs.length<=0){
		errorMsg = errorMsg + "\u2022 There must be atleast one mail recipient! <br />";
	 	$('#mailList').next().addClass("alertBorderRed");
	 	validation++;
	}else{
		$('#mailList').next().removeClass("alertBorderRed");
	}
	
	
	if(validation!=0){
		showError(errorMsg);
	}
	if(validation==0){
		
		var requirements = {
			      parameters: []
			  };
		for(var i=0; i<(length-1); i++){
			requirements.parameters.push({ 
		          "skill" : skills[i],
		          "noOfResources"  :  resourceNo[i] ,
		          "designation"   :  designations[i]  ,
		          "experience"	: experience[i],
		          "billable"	: billings[i],
		          "type"	: type[i],
		          "reqId"	: time[i],
		          "secondaryskillName": secondaryskillName[i]
		 	});
			
		}
		/* requirements.parameters.push({
			"comments" : comments
		}); */
		// alert("ajax for project name"+project);
		startProgress();
		
		$.ajax({
    		type: 'POST',
    		dataType: 'text',
            //data: comments ,
            //  Added id in the below url for Updating the records
            url: '/rms/requests/saveRequest/'+skillRequestId+"/"+requestId+"/"+skillName+"/"+mailIDs+"/"+mailIDcc+"/"+project+"/"+selectedProjectName+"/"+client, 
        	contentType: "text/html",
        	data: JSON.stringify(requirements),
            cache: false,
            success: function(data) { 
            	stopProgress();
	     		showSuccess("Request Sent Successfully! ");
	     		/*1003958 STARTS[] for redirecting to the list page when record is successfully updated */
	     		if(skillRequestId!=0){
	     			 var successUrl = "/rms/requestsReports"; 
	 	     	    window.location.href = successUrl;
	     		}
	     		/*1003958 ENDS[]  */
	     		else{
	     			window.location.reload();
	     		}
	     	},
	     	error: function(error){
	     		stopProgress();
	     		showError("Something happend wrong!!");
	     		//window.location.reload();
	     	}
		});
	}
    
});

$(document).on('click','.edit-exist-class',function(){
	var row_id = $(this).closest('tr').index()+1;
	var rrfJSON = JSON.parse(document.getElementById("hiddenSkills"+row_id+"").value);
	
	$('#primarySkills').val(rrfJSON.primarySkills);
	$('#desirableSkills').val(rrfJSON.desirableSkills);
	$('#responsibilities').val(rrfJSON.responsibilities);
	$('#careerGrowthPlan').val(rrfJSON.careerGrowthPlan);
	$('#targetCompanies').val(rrfJSON.targetCompanies);
	$('#keyScanners').val(rrfJSON.keyScanners);
	$('#timeFrame').val(rrfJSON.timeFrame);
	$('#keyInterviewersOneText').val(rrfJSON.keyInterviewersOneText);
	$('#keyInterviewersTwoText').val(rrfJSON.keyInterviewersTwoText);
	$('#additionalComments').val(rrfJSON.additionalComments);
	
});
// for Pop UP 
var id=0;
$("#addSecondarySkill").on('shown.bs.modal',function(e){
	  var sourceElement=e.relatedTarget;
	  id=$(sourceElement).closest('tr').index();
	  id++;
	 
	  var keyInterviewersOne = $('#keyInterviewersOneText').val().split(',');
	  var keyInterviewersOneTexts = [];
	  for (var i=0; i < keyInterviewersOne.length; i++) {
		  keyInterviewersOneTexts.push($.trim(keyInterviewersOne[i]));
	  }
	  for(var i=0; i < keyInterviewersOneTexts.length; i++){
	    $("#keyInterviewersOne").multiselect("widget").find(":checkbox[value='"+keyInterviewersOneTexts[i]+"']").prop('selected', true);
	    $("#keyInterviewersOne option[value='" + keyInterviewersOneTexts[i] + "']").prop('selected', true);
	    $("#keyInterviewersOne").multiselect("refresh");
	  }
	  
	  var keyInterviewersTwo = $('#keyInterviewersTwoText').val().split(',');
	  var keyInterviewersTwoTexts = [];
	  for (var i=0; i < keyInterviewersTwo.length; i++) {
			  //alert("test: "+$.trim(keyInterviewersOne[i]));
			  keyInterviewersTwoTexts.push($.trim(keyInterviewersTwo[i]));
	  }
	  for(var i=0; i < keyInterviewersTwoTexts.length; i++){
		    $("#keyInterviewersTwo").multiselect("widget").find(":checkbox[value='"+keyInterviewersTwoTexts[i]+"']").prop('selected', true);
		    $("#keyInterviewersTwo option[value='" + keyInterviewersTwoTexts[i] + "']").prop('selected', true);
		    $("#keyInterviewersTwo").multiselect("refresh");
	  }
	   
	});
$(document).on('click','#saveskill',function(){
	
	  var addSkill = {};
	  var errorMsg = '';
	  var validation=0;
	  var expRegex = /^\d{1,2}.*?(-\d{1,2})?$/;
	  var experienceTimeRegex = /^[0-9 -]+$/; 
	  addSkill ["primarySkills"] = $("#primarySkills").val();
	  addSkill ["timeFrame"] = $("#timeFrame").val();
	  addSkill ["desirableSkills"] = $("#desirableSkills").val();
	  addSkill ["responsibilities"] = $("#responsibilities").val();
	  addSkill ["careerGrowthPlan"] = $("#careerGrowthPlan").val();
	  addSkill ["targetCompanies"] = $("#targetCompanies").val();
	  addSkill ["keyScanners"] = $("#keyScanners").val();
	  addSkill ["keyInterviewersOneText"] = $("#keyInterviewersOneText").val();
	  addSkill ["keyInterviewersTwoText"] = $("#keyInterviewersTwoText").val();
	  addSkill ["additionalComments"] = $("#additionalComments").val();
	 
	  if($("#primarySkills").val()==null || $("#primarySkills").val()==""){
			errorMsg = errorMsg + ("\u2022 Primary Skills cannot be empty! <br/>");
			validation++;
			}
	  if($("#timeFrame").val()==null || $("#timeFrame").val()==""){
			errorMsg = errorMsg + ("\u2022 Time Frame cannot be empty! <br/>");
			validation++;
			}else if(!$("#timeFrame").val().match(expRegex)){
				errorMsg = errorMsg + ("\u2022 Time Frame must be numeric or numeric range! <br/>");
				validation++;
			}else if(!$("#timeFrame").val().match(experienceTimeRegex)){
				errorMsg = errorMsg + ("\u2022 Time Frame must be numeric or numeric range! <br/>");
				validation++;
			}
      /* if($("#desirableSkills").val()==null || $("#desirableSkills").val()==""){
	   errorMsg = errorMsg + ("\u2022 Desirable Skills cannot be empty! <br/>");
	   validation++;
	    }
     if($("#responsibilities").val()==null || $("#responsibilities").val()==""){
	    errorMsg = errorMsg + ("\u2022 Responsibilities Skills cannot be empty! <br/>");
	   validation++;
	  }
      if($("#careerGrowthPlan").val()==null || $("#careerGrowthPlan").val()==""){
		errorMsg = errorMsg + ("\u2022 Career Growth Plan Skills cannot be empty! <br/>");
		validation++;
		}
      
      if($("#targetCompanies").val()==null || $("#targetCompanies").val()==""){
  		errorMsg = errorMsg + ("\u2022 Target Companies Skills cannot be empty! <br/>");
  		validation++;
  		}
      if($("#keyScanners").val()==null || $("#keyScanners").val()==""){
  		errorMsg = errorMsg + ("\u2022 key Scanners Skills cannot be empty! <br/>");
  		validation++;
  		} */
      if($("#keyInterviewersOneText").val()==null || $("#keyInterviewersOneText").val()==""){
    		errorMsg = errorMsg + ("\u2022 Key Interviewers One cannot be empty! <br/>");
    		validation++;
    		}
      if($("#keyInterviewersTwoText").val()==null || $("#keyInterviewersTwoText").val()==""){
    		errorMsg = errorMsg + ("\u2022 key Interviewers Two  cannot be empty! <br/>");
    		validation++;
    		}
     /*  if($("#additionalComments").val()==null || $("#additionalComments").val()==""){
    		errorMsg = errorMsg + ("\u2022 Additional Comments cannot be empty! <br/>");
    		validation++;
    		} */
     if(validation!=0){
	 	showError(errorMsg);
	  }
     else{
    	 
	  document.getElementById("hiddenSkills"+id).value = JSON.stringify(addSkill);
	  $('#addSecondarySkill').modal('hide');
	  $("#edit-exist"+id+"").show();
	  $("#addSecondarySkillButton"+id+"").hide();
	  }
	  
      $("#edit-exist"+id+"").attr('data-target','#addSecondarySkill');
 	  $("#edit-exist"+id+"").attr('data-toggle','modal');
});
// for model reset
$('.modal').on('hidden.bs.modal', function(){
    $(this).find('form')[0].reset();
});
//in  model multi select dropdown list
$(function() {
	
	$('#keyInterviewersOne').multiselect({
		includeSelectAllOption: true,
		id:"keyInterviewersOne_one"
	}).multiselectfilter();
	$('#keyInterviewersOne').bind('change', function() {
	     
		$('#keyInterviewersOneText').val($(this).val());
		//alert('Change: '+$(this).val()); 
		});
	$("span#keyInterviewersOne_one").next().on("click",function(){  //alert("keyInterviewersTwo_one")
		$(".keyInterviewersOne_one").val('');
	 });
});

$(function() {
	
	$('#keyInterviewersTwo').multiselect({
		includeSelectAllOption: true,
		id:"keyInterviewersTwo_two"
	}).multiselectfilter();
	
	$('#keyInterviewersTwo').bind('change', function() {
		$('#keyInterviewersTwoText').val($(this).val());
		});
	$("span#keyInterviewersTwo_two").next().on("click",function(){ //alert("keyInterviewersTwo_two");
		$(".keyInterviewersTwo_two").val('');
	 });
});

$(function() {
	$('#resourceApprovedBy').multiselect({
		includeSelectAllOption: true
	}).multiselectfilter();
});
});

</script>
<div class="content-wrapper">
	<div class="marginBottom15">
		<h1>Resource Requisition</h1>
	</div>
 <div>
	 <div align="left" class="tblBorderDiv">
	
			<table  cellpadding="5" cellspacing="5" class="display dataTable" style="border-collapse:collapse;width:100%; margin-top:0px;">
				<thead>
					<tr class="totalColumn">
						<th  colspan="4" align="left" style="font-size:small;">Indent By</th>
					</tr>
				</thead>
				<tbody>
					
					<tr class="odd">
						<td align="right" width="20%" ><strong>Request raised by :</strong> </td> 
						<td align="left" width="20%" >${currentLoggedinResource}</td>
					
						<td align="right" width="20%"><strong>Requesting Practice : </strong></td>
						<td align="left" width="20%">${resourceUnit}</td>
						
					</tr>
					<tr>
					<td align="right"><strong><span id="oldClientTable">Client <span style="color: red;">*</span>  :</strong></td>
						<td align="left"><span id="oldClientTableList">
							<select class="required comboselect check"  id="client">
									<c:choose>
										<c:when test="${buttonFlag}">
											<option value="${dataList.requestRequisition.customer.id}">${dataList.requestRequisition.customer.customerName}</option>
											
											<c:forEach var="client" items="${allCustomers}" >
												<c:if test="${dataList.requestRequisition.customer.customerName != client.customerName}">
												 <option value="${client.id}">${client.customerName}</option>	
												</c:if>
											</c:forEach>
											</c:when>
										<c:otherwise>
										<option selected="selected" style="display:none">Select</option> 
									 <c:forEach var="client" items="${allCustomers}">									
									<option value="${client.id}">${client.customerName}</option>										
								</c:forEach >
									</c:otherwise>
										</c:choose>
							</select>
						</td> 
						<td align="right"><strong><span id="oldProjectTable">Requirement For Existing Project Name <span style="color: red;">*</span>  :</strong></td>
						<td align="left"><span id="oldProjectTableList">
							<select class="required comboselect check"  id="projects" name="projectDrp">
									<c:choose>
										<c:when test="${buttonFlag}">
											<option value="${dataList.requestRequisition.project.id}">${dataList.requestRequisition.project.projectName}</option>
											
											<c:forEach var="projects" items="${allProjects}" >
												<c:if test="${dataList.requestRequisition.project.projectName != projects.projectName}">
												 <option value="${projects.id}">${projects.projectName}</option>	
												</c:if>
											</c:forEach>
											</c:when>
										<c:otherwise>
										<option selected="selected" style="display:none">Select</option> 
									 <c:forEach var="projects" items="${allProjects}">									
									<option value="${projects.id}">${projects.projectName}</option>										
								</c:forEach >
									</c:otherwise>
										</c:choose>
							</select>
						</td> 
					</tr>
					<%-- <tr>
						<td align="right"><strong>Project Name <span style="color: red;">*</span>  :</strong></td>
						<td align="left">
							<select class="required comboselect check "  id="projects" onchange="showTextBox(projects)">
							<option value="0">Select Project</option>
							<option value="new">New Project</option>
							<c:forEach var="projects" items="${allProjects}">									
								<option value="${projects}">${projects}</option>										
							</c:forEach>
							</select>
						</td>
						<td align="right"><strong><span id="newProjectTable">New Project Name <span style="color: red;">*</span> :</strong></td>
						<td><input type="text" id="newProj"></td>
					</tr> --%>
			</tbody>
			</table>
			<!-- <table  id="newProjectTable" cellpadding="5" cellspacing="5" class="display dataTable" style="border-collapse:collapse;width:100%;">
				<tr class="even">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td align="right">New Project Name :</td>
						<td align="left"><input type="text" id="newProj"></td>
				</tr>
			</table> -->
	</div>
<div id="requirementParent"  class="tblBorderDiv">
<table id="requirement" cellpadding="5" cellspacing="5" class="display dataTable" style="border-collapse:collapse;width:100%; margin-top:0px;">
	<thead>
		<tr class="totalColumn">
			<th align="center" style="font-size:small;">Skills</th>
			<th align="center" style="font-size:small;">No of Resources</th>
			<th align="center" style="font-size:small;">Designation</th>
			<th align="center" style="font-size:small;">Experience<br>(yrs/yrs-yrs)</th>
			<th align="center" style="font-size:small;">Allocation Type</th>
			<th align="center" style="font-size:small;">Type</th>
			<th align="center" style="font-size:small;">Customized<br>Requirement ID</th>
			<th align="center" style="font-size:small;">Detail JD</th>
		</tr>
	</thead>
	<tbody>
		<tr>
		<c:if test="${dataList.requestRequisition.id != null}">
		<input type="hidden" value="${requestRequisition}" name="skillRequestId" id="skillRequestId">
		<input type="hidden" value="${dataList.requestRequisition.id}" name="requestId" id="requestId">
		</c:if>
			<!-- <td align="right" width="20%"><strong>Skills <span style="color: red;">*</span> : </strong></td> -->
			<td  align="center">
			<!-- raghvendra change image-->
			 <img  src="resources/images/addUser.gif" id="add1" class="addIcon" inp="add">  
  			 <select id="allSkills1" class="required comboselect check" name="allSkills" inp="allSkills">
							<%-- 1003958 START[]--%>
									<c:choose>
										<c:when test="${buttonFlag}">
											<option value="${dataList.skill.id}">${dataList.skill.skill}
											</option>
											
											<c:forEach var="skills" items="${secondrySkills}">
												<c:if test="${dataList.requestRequisition.id != skills.id }">
													<option value="${skills.id}">${skills.skill}</option>
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<option value="0">Select</option>
											<c:forEach var="skills" items="${secondrySkills}">
												<option value="${skills.id}">${skills.skill}</option>
											</c:forEach>
										</c:otherwise>
									</c:choose>
							<%-- 1003958 ENDS[]--%>
			</select> 
 			</td>
 			<!-- <td align="right"  width="20%"><strong>No. of Resources <span style="color: red;">*</span> : </strong></td> --> 
			<td  align="center"> 
  					<input id="resources1" type="text" class="resources_cls required" value= "${dataList.noOfResources}" inp="resources"/> 
  					<!-- <img  src="resources/images/addUser.gif" id="add1" class="addIcon">
  					&nbsp;&nbsp;<img src="resources/images/remove.png" id="remove1" class="remove" width="16" height="16"> -->
 			</td>
 			<td  align="left"> 
 				<select id="designation1" class="required comboselect check string invalid"  name="allDesigs" inp="designation">
					<!-- 1003958 START[] -->
					<c:choose>
										<c:when test="${buttonFlag}">
											<option value="${dataList.designation.id}">${dataList.designation.designationName}
											</option>
											<option value="0">Select</option>
											<c:forEach var="designation" items="${designation}">
												<c:if test="${dataList.designation.id != designation.id }">
													<option value="${designation.id}">${designation.designationName }</option>
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<option value="0">Select</option>
											<c:forEach var="designation" items="${designation}">
												<option value="${designation.id}">${designation.designationName }</option>
											</c:forEach>
										</c:otherwise>
									</c:choose>
					<!--1003958 ENDS[]  -->			
				</select>
 			</td>
 			<td  align="center">
 			
 				<input type="text" class="exerience_cls required" inp="experience"  id="experience1" value= "${dataList.experience}" /> 
 				 
 			</td>
 			<td  align="left">
 			<select class="required comboselect check" id ="billing1" name="billing" inp="billing">
 							<!-- 1003958 START[] -->
									<c:choose>
										<c:when test="${buttonFlag}">
											 <option value="${dataList.allocationType.id}">${dataList.allocationType.allocationType }</option> 
											
											<c:forEach var="allocationtype" items="${resourceAllocation}">
											 <c:if test="${dataList.allocationType.id != allocationType.id }"> 
												<option value="${allocationtype.id}">${allocationtype.allocationType }</option>
												 </c:if> 
											</c:forEach>
										</c:when>
										<c:otherwise>
											<option value="-1">Select</option>
											<c:forEach var="allocationtype" items="${resourceAllocation}">
												<option value="${allocationtype.id}">${allocationtype.allocationType }</option>
											</c:forEach>
										</c:otherwise>
									</c:choose> 
							<!--1003958 ENDS[] -->
				 </select>
 				
			</td>
			<td  align="center">  
			
			<select class="required comboselect check"  name="type" id="type1" inp="type">
							<%-- 1003958 START[]--%>
								<c:choose>
										<c:when test="${buttonFlag}">
										<c:if test="${dataList.type != 1 }">
											<option selected="selected" value="${dataList.type}" >Replacement</option></c:if>
											<c:if test="${dataList.type == 1 }">
											<option selected="selected" value="${dataList.type}" >New Requirement
											</option></c:if>
								<option value="1">New Requirement</option>
								<option value="0">Replacement</option>
										</c:when>
										<c:otherwise>
 								<option value="-1">Select</option>
								<option value="1">New Requirement</option>
								<option value="0">Replacement</option>
										</c:otherwise>
									</c:choose>
							<%-- 1003958 ENDS[]--%>
			</select> 
			</td>
			<td align="center">
				<input id="requirementId1" type="text" class="type_cls" value="${dataList.requirementId}" inp="reqId"/>
				<!-- <img src="resources/images/remove.png" id="remove1" class="remove" width="16" height="16" inp="remove"> -->
			</td>
			<td align="center">
				 <!-- <a href="#myPopupDialog" data-rel="popup" data-position-to="window" data-transition="fade" class="ui-btn ui-corner-all ui-shadow ui-btn-inline">Open Dialog Popup</a> -->
				 <a id="addSecondarySkillButton1" href="javascript:void(0)" data-toggle="modal" data-target="#addSecondarySkill">Add More Skills</a> 
								<!--1003958  --> <input type="hidden" id="id" value="" />
			  <input type="hidden" id="hiddenSkills1" name="hiddenSkills" inp="hiddenSkills" value="" />
			  <a id="edit-exist1" class="edit-exist-class" href="javascript:void(0)">Edit Skills</a> 
			</td>
			
 		</tr>
 	</tbody>
 </table>
 <!-- <div align="center" style="padding-top: 15px"> 
 <input class="add" type="button" value="Add More" style="font-size: medium;" />
</div> -->
 </div>
</div>

<!-- <div class="ui-widget-header ui-corner-all ui-multiselect-header ui-helper-clearfix ui-multiselect-hasfilter"> -->
<div  class="tblBorderDiv">
	<table id="mail" cellpadding="5" cellspacing="5" class="display dataTable" style="border-collapse:collapse;width:100%; margin-top:0px;">
		<tr>
			<td align="right" width="20%"><strong>Sent Request To <span style="color: red;">*</span> : </strong></td>
			<td align="left" width="20%">
			 <div  class="positionRel">
							<select  id="mailList" class="required" multiple="multiple" >
								<!-- 1003958 START[] --> 
								
									<c:choose>
										<c:when test="${buttonFlag}">
										<c:forEach var="mail" items="${mailingList}">
											   <c:choose>
												<c:when test="${fn:contains(dataList.requestRequisition.sentMailTo,mail.employeeId)}">
												<c:set var="mailIds" value="${fn:split(dataList.requestRequisition.sentMailTo, ',')}" />
										        <c:forEach var="empid" items="${mailIds}">
										        <fmt:parseNumber var="eid" type="number" value="${fn:trim(empid)}" />
										        <c:if test="${eid==mail.employeeId}">
												<option selected="selected" value="${mail.employeeId}-${mail.emailId}">${mail.firstName} ${mail.lastName}</option>
												</c:if>
												</c:forEach>
												</c:when>
												<c:otherwise>
												<option  value="${mail.employeeId}-${mail.emailId}">${mail.firstName} ${mail.lastName}</option>	 
												</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach var="mail" items="${mailingList}">
												<option value="${mail.employeeId}-${mail.emailId}">${mail.firstName} ${mail.lastName}</option>	
											</c:forEach>
										</c:otherwise>
									</c:choose>
									<!-- 1003958 ENDS[] -->  
			</select> 
 			</div>
 			</td>
 			<td align="right" width="20%"><strong>Notify To : </strong></td>
			<td align="left" width="20%">
			<div  class="positionRel">
  				<select id="mailListCC" class="required" multiple="multiple" >
  				
									<c:choose>
										<c:when test="${buttonFlag}">
										<c:forEach var="mail" items="${mailingList}">
											   <c:choose>
												<c:when test="${fn:contains(dataList.requestRequisition.notifyMailTo,mail.employeeId)}">
												<c:set var="mailIds" value="${fn:split(dataList.requestRequisition.notifyMailTo, ',')}" />
										        <c:forEach var="empid" items="${mailIds}">
										        <fmt:parseNumber var="eid" type="number" value="${fn:trim(empid)}" />
										        <c:if test="${eid==mail.employeeId}">
												<option selected="selected" value="${mail.employeeId}-${mail.emailId}">${mail.firstName} ${mail.lastName}</option>
												</c:if>
												</c:forEach>
												</c:when>
												<c:otherwise>
												<option  value="${mail.employeeId}-${mail.emailId}">${mail.firstName} ${mail.lastName}</option>	 
												</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach var="mail" items="${mailingList}">
												<option value="${mail.employeeId}-${mail.emailId}">${mail.firstName} ${mail.lastName}</option>	
											</c:forEach>
										</c:otherwise>
									</c:choose>
					<%-- <c:forEach var="mail" items="${mailingList}" >	
						<option value="${mail.employeeId}-${mail.emailId}">${mail.firstName} ${mail.lastName}</option>										
					</c:forEach> --%>
				</select> 
			</div>
 			</td>
 			<%-- <tr>
 			<td align="right"><strong>Comments :</strong></td>
 			<td align="left"  colspan="4"> <!--   -->
 			<!-- 1003958 STARTS[] for dispaly the comments-->  
 				<textarea id="myTextArea"   placeholder="250 characters." rows="5" cols="20" style="width: 91.4%; border:1px solid #ccc">${dataList.requestRequisition.comments }</textarea><!-- 1003958 for displaying comments -->  
 			<!-- 1003958 ENDS[] -->  
 			</td>
 		</tr> --%>
			
	 </table>
	 </div>
<!-- 1003958 START[] To display buttons on the bases of boolean-->
  <c:choose>
            <c:when test="${buttonFlag}">
             <div style="padding-top: 15px" align="center" >
             
				<input id="submitForm" type="button" value="Update"
					class="
					request-form-style" style="font-size: medium;" />&nbsp;
				<input  type="button"
					onclick="javascript:window.location='/rms/requestsReports'"  class="
					request-form-style" style="font-size: medium;" data-dismiss="modal" value="Cancel"/>
					
					</div> 
			</c:when>
            <c:otherwise>
     <div style="padding-top: 15px" align="center" >
					<input id="submitForm" type="button" value="Generate Request"
						class="request-form-style" style="font-size: medium;" />
				</div>           
            </c:otherwise>
        </c:choose> 
              
     <!--1003958 ENDS[]  -->
 </div>
	

<!-- popup RRF start  -->
   <div class="modal fade" id="addSecondarySkill" role="dialog" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
    
      RECRUITMENT REQUISITION FORM 
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Please enter Detailed Job Description </h4>
        </div>

				<div class="modal-body">
					
					<div class="form-group">

						<form>
                      
							<div class="form-group">
								<label for="primarySkills" >Primary Skills <span style="color: red;">*</span>:</label>
								<textarea class="form-control" rows="3" id="primarySkills" id="RRFPrimarySkills">${dataList.primarySkills}</textarea><!-- 1003958 for displaying primary skill -->
							</div>

							<div class="form-group">
								<label for="desirableSkills">Desirable Skills :</label>
								<textarea class="form-control" rows="3" id="desirableSkills">${dataList.desirableSkills}</textarea><!-- 1003958 for displaying desirableSkills -->
							</div>
							<div class="form-group">
								<label for="responsibilities">Planned Roles and
									Responsibilities :</label>
								<textarea class="form-control" rows="3" id="responsibilities">${dataList.desirableSkills}</textarea><!-- 1003958 for displaying responsibilities -->
							</div>
							<div class="form-group">
								<label for="careerGrowthPlan">Career Growth Plan :</label>
								<textarea class="form-control" rows="2" id="careerGrowthPlan">${dataList.careerGrowthPlan}</textarea><!-- 1003958 for displaying careerGrowthPlan -->
							</div>
							<div class="form-group">
								<label for="targetCompanies">Target companies :</label>
								<textarea class="form-control" rows="2" id="targetCompanies">${dataList.targetCompanies}</textarea><!-- 1003958 for displaying targetCompanies -->
							</div>
							<div class="form-group">
								<label for="keyScanners">Key scanners :</label>
								<textarea class="form-control" rows="2" id="keyScanners">${dataList.keyScanners}</textarea><!-- 1003958 for displaying keyScanners -->
							</div>
							<div class="form-group">							
								<label for="timeFrame">Time-Frame <span style="color: red;">*</span>:</label>
								<textarea class="form-control" rows="2" id="timeFrame">${dataList.timeFrame}</textarea>
							<%-- <input id="time1" type="text" class="type_cls" value="${dataList.timeFrame}" inp="timeFrame"/> --%>
							</div>
							<br>
							<div class="form-group ">
								<label for="keyScanners">key Interviewers (For First Round) <span style="color: red;">*</span>: </label> 
								<div class="positionRel displayBlk">
								<select
									id="keyInterviewersOne" class="required" multiple="multiple">
									<c:forEach var="mail" items="${ActiveUserList}">
										<option value="${mail.emailId}">${mail.firstName} ${mail.lastName}</option>
									</c:forEach>
								</select>
								</div>
								<textarea class="form-control keyInterviewersOne_one" id="keyInterviewersOneText" 
									readonly="readonly">${dataList.keyInterviewersOne}</textarea><!-- 1003958 for displaying keyInterviewersOne -->
							</div>
							<div class="form-group">
								<label for="keyScanners">key Interviewers (For Second Round) <span style="color: red;">*</span>: </label> 
								<div class="positionRel displayBlk">
								<select
									id="keyInterviewersTwo" class="required" multiple="multiple">
									<c:forEach var="mail" items="${ActiveUserList}">
										<option value="${mail.emailId}">${mail.firstName} ${mail.lastName}</option>
									</c:forEach>
								</select>
								</div>
								<textarea class="form-control keyInterviewersTwo_two" id="keyInterviewersTwoText" 
									readonly="readonly">${dataList.keyInterviewersTwo}</textarea><!-- 1003958 for displaying keyInterviewersTwo -->
							</div>
							<div class="form-group">
								<label for="additionalComments">Additional comments, if
									any :</label>
								<textarea class="form-control" rows="2" id="additionalComments">${dataList.additionalComments}</textarea><!-- 1003958 for displaying additionalComments -->
							</div>
						</form>
							<!--1003958 ENDS[]-->
					</div>
		
				</div>
				<div class="modal-footer">
						<button id="saveskill" name="saveskill" type="button"
							class="btn btn-default">Save</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>

			</div>
  </div> 
</div>
<div class="clear"></div>