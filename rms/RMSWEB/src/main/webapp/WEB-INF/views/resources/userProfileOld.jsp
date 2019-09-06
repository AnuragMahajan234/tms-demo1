<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />
<!--[if IE]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<![endif]-->
 <spring:url value="/resources/js-framework/date.js?ver=${app_js_ver}" var="jquery_date_js" />
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}" var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<spring:url value="/resources/images/favicon.jpg" var="favicon" />
<spring:url value="/resources/js-framework/jquery-1.7.2.min.js?ver=${app_js_ver}"
	var="jquery_url" />
<spring:url
	value="/resources/js-framework/jquery-ui/jquery-ui-1.8.22.custom.min.js?ver=${app_js_ver}"
	var="jquery_ui_1_8_21_custom_min_js" />
<spring:url value="/resources/js-framework/jquery-ui/combobox.js?ver=${app_js_ver}"
	var="combobox_js" />
<spring:url
	value="/resources/js-framework/validate/jquery.validate.min.js?ver=${app_js_ver}"
	var="jquery_validate_min_js" />
<spring:url
	value="/resources/js-framework/validate/jquery.validVal-4.0.3-packed.js?ver=${app_js_ver}"
	var="jquery_validVal" />
<spring:url
	value="/resources/js-framework/validate/additional-methods.min.js?ver=${app_js_ver}"
	var="additional_methods_min_js" />


<spring:url value="/resources/js-user/rmsCommon.js?ver=${app_js_ver}" var="rmsCommon_js" />
<spring:url value="/resources/js-user/validations.js?ver=${app_js_ver}"
	var="validations_js" />
<script src="${validations_js}" type="text/javascript"></script>	
<spring:url value="/resources/js-framework/accordianFilter.js?ver=${app_js_ver}"
	var="accordianFilter_js" />

<spring:url
	value="/resources/js-framework/fancy-box/jquery.fancybox.pack.js?ver=${app_js_ver}"
	var="jquery_fancybox" />
	
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}" var="style_css"/>
	<link href="${style_css}" rel="stylesheet" type="text/css"></link>


<script src="${jquery_url}" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="${jquery_ui_1_8_21_custom_min_js}" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="${combobox_js}" type="text/javascript">
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

<!-- required for common functions -->
<script src="${rmsCommon_js}" type="text/javascript"></script>
<!-- required for common validations -->
<script src="${validations_js}" type="text/javascript"></script>

<spring:url
	value="/resources/js-framework/populate/jquery.populate.pack.js?ver=${app_js_ver}"
	var="jquery_populate_pack_js" />
<spring:url
	value="/resources/js-framework/fancy-box/jquery.fancybox.pack.js?ver=${app_js_ver}"
	var="jquery_fancybox" />
<spring:url value="/resources/js-framework/toastr/toastr.js?ver=${app_js_ver}"
	var="toastr_js" />
<spring:url value="/resources/js-framework/blockUI/jquery.blockUI.js?ver=${app_js_ver}"
	var="blockUI" />

<!-- Populate -->
<script src="${jquery_populate_pack_js}" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="${jquery_fancybox}" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<spring:url value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}"
	var="multiselect_js" />
<script src="${multiselect_js}" type="text/javascript"></script>

<spring:url value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}"
	var="multiselect_filter_js" />

<script src="${multiselect_filter_js}" type="text/javascript"></script>
<script src="${jquery_fancybox}" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="${toastr_js}" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="${blockUI}" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>

<!-- This is css class for edit profile download button start -->
<link href="/rms/resources/dashboardscript/bootstrap/css/font-awesome.min.css" rel="stylesheet" type="text/css"></link>
<style>
.action_btn {
    position: absolute;
    right: 302px;
    top: 127px;
    background: #eeeae7;
    color: #3376bc !important;
    padding: 5px 3px;
    border-radius: 50px;
    height: 16px;
    border-radius: 0px 0px 0px 0px;
    transition: all 0.5s;
}
.pointer {
	cursor: pointer;
}
.brdrRed {
		border: 1px solid red !important;
}
</style>

<!-- This is css class for edit profile download button -->

<script src="${accordianFilter_js}" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>

<script type="text/javascript">
var skilltype='';

function setSkillType(skillType){
	
	skilltype = "Selected "+skillType+" Skills";
	
	document.getElementById('para').innerHTML = skilltype;
}
	
$(document).ready(function() {
	
		stopProgress();
		
		<c:if test="${not empty success}">
			showSuccess("Your profile has been updated successfully!!");
		</c:if>
		
		$("select.comboselect").multiselect().multiselectfilter();
		
		$('.chk').change(function() {

			if (!$(this).is(':checked')) {
				$(this).removeAttr("checked");
			} else {
				$(this).attr("checked", "checked");
			}
		});
		
		function startProgress() {
			$.blockUI({
				message : '</br></br><div class="circle"></div><div class="circle1"></div><center>Just a moment</center>'
			});
		}

		function stopProgress() {
			$.unblockUI();
		}

		function showSuccess(msg) {
			toastr.success(msg, "Success")
		}

		function showError(msg) {
			toastr.error(msg, "Error").delay(4000);
		}

		function showWarning(msg) {
			toastr.warning(msg, "Warning")
		}
/* Start - code to populate relevant experience dropdowns - Digdershika*/
		
		var totalExperMonthSelect = document.getElementById('totalExpMonth');
        var totalExperYearSelect = document.getElementById('totalExpYear');
		var relevantExpMonthSelect = document.getElementById('relevantExpMonth');
		var relevantExpYearSelect = document.getElementById('relevantExpYear');
		
		for( var i = 0 ; i<=50  ; i++){
			var optYear = document.createElement('option');
			
			if(i.toString().length==1){				
				optYear.value = '0'+i;
				optYear.innerHTML = i + " YY";
				if(i<=11){
					var optMonth = document.createElement('option');
				optMonth.value = '0'+i;
				optMonth.innerHTML = i + " MM";
				}
			}else{				
				optYear.value = i;
				optYear.innerHTML = i + " YY";
				if(i<=11){
					var optMonth = document.createElement('option');
				optMonth.value = i;
				optMonth.innerHTML = i + " MM";
				}
			}			
			totalExperYearSelect.append(optYear);
			totalExperMonthSelect.append(optMonth);
    	}
		
		for( var i = 0 ; i<=50  ; i++){
			var optYear = document.createElement('option');
			
			if(i.toString().length==1){				
				optYear.value = '0'+i;
				optYear.innerHTML = i + " YY";
				if(i<=11){
					var optMonth = document.createElement('option');
				optMonth.value = '0'+i;
				optMonth.innerHTML = i + " MM";
				}
			}else{				
				optYear.value = i;
				optYear.innerHTML = i + " YY";
				if(i<=11){
					var optMonth = document.createElement('option');
				optMonth.value = i;
				optMonth.innerHTML = i + " MM";
				}
			}			
			relevantExpYearSelect.append(optYear);
			relevantExpMonthSelect.append(optMonth);
    	}
		/* End - code to populate relevant experience dropdowns - Digdershika*/
		
		/* Start - code to split relevant experience in years and months and then display - Digdershika*/
			var relevantExpValue = ${currentLoggedinResource.relevantExp};
			var relevantExpYear=Math.floor(relevantExpValue);
			var relevantMonth=((relevantExpValue % 1).toFixed(2)).slice(".");
			
			if(relevantExpYear.toString().length == 1){				
				relevantExpYear="0"+relevantExpYear;
			}
			 
			document.getElementById("relevantExpYear").value = relevantExpYear;
			document.getElementById("relevantExpMonth").value = relevantMonth.split(".")[1];
			
			var totalExpValue = ${currentLoggedinResource.totalExp};
			var totalExpYear=Math.floor(totalExpValue);
			var totalExpMonth=((totalExpValue % 1).toFixed(2)).slice(".");
			
			if(totalExpYear.toString().length == 1){				
				totalExpYear="0"+totalExpYear;
			}
			 
		//	document.getElementById("totalExp").value = totalExpYear+"."+totalExpMonth.split(".")[1];
			
			document.getElementById("totalExpYear").value = totalExpYear;
			document.getElementById("totalExpMonth").value = totalExpMonth.split(".")[1];
			
		/* End - code to split relevant experience in years and months and then display - Digdershika*/
		 document.getElementById("totalExp").value  = document.getElementById("totalExpYear").value.toString()+"."+document.getElementById("totalExpMonth").value.toString();
				
		var form_data;
		var validationFlag;
		var columns;
		$("#save").click(function(event) {
			
			form_data = true;
			validationFlag = true;
			var errorMsg = "";
			var primaryExperience=0;
			var secondaryExperience=0;	
			 
			
			
			// resume upload and validation added by Vikas Patidar
			var uploadResumeFileName = $.trim($("#uploadResume").val()) ;
			if (uploadResumeFileName != "") {
				if (!/(\.doc|\.docx|\.pdf)$/i.test(uploadResumeFileName)) {
					validationFlag = false;
					showError("Uploaded resume should be in doc or pdf format.");
					$("#uploadResume").addClass("brdrRed");
					stopProgress();
					return false;
				}
				var sizeofdoc =  $("#uploadResume")[0].files[0].size;
				if(sizeofdoc>5242880) {
					validationFlag = false;
					showError("File size should not be greater than 5MB.");
					$("#uploadResume").addClass("brdrRed");
					stopProgress();
					return false;
				} 
				
			    var startIndex = (uploadResumeFileName.indexOf('\\') >= 0 ? uploadResumeFileName.lastIndexOf('\\') : uploadResumeFileName.lastIndexOf('/'));
			    var filename = uploadResumeFileName.substring(startIndex);
			    if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0) {
			        filename = filename.substring(1);
			    }
			    document.getElementById("uploadResumeFileName").value = filename;
			} 
			var uploadImage = document.getElementById("uploadImage").value;
			
			if (uploadImage != '') {
				var imgsize = $("#uploadImage")[0].files[0].size;
				
				// image upload
				if (imgsize >= 1000000) {
					errorMsg = errorMsg + "\u2022 Please select Image Size upto 1MB ! <br/>";
					validationFlag = false;
				}
				var checkimg = uploadImage.toLowerCase();
				
				if (!checkimg.match(/(\.jpg|\.png|\.JPG|\.PNG|\.jpeg|\.JPEG)$/)) {
					
					errorMsg = errorMsg + "\u2022 Please enter  Image  File Extensions .jpg,.png,.jpeg ! <br/>";
					validationFlag = false;
					$("#uploadImage").addClass("brdrRed");

				} else {
					$("#uploadImage").removeClass("brdrRed");
				}
			}

			var contactNumber1 = document.getElementById("contactNumber1").value;
			var contactNumber2 = document.getElementById("contactNumber2").value;
			var regExp = /^((\+)?[1-9]{1,2})?([-\s\.])?((\(\d{1,4}\))|\d{1,4})(([-\s\.])?[0-9]{1,12}){1,2}$/;

			if (contactNumber1 != null && contactNumber1 != "" && contactNumber1 != 0) {
				
				if(contactNumber1.search(regExp))
				
				if (!regExp.test(contactNumber1)) {
					
					validationFlag = false;
					errorMsg = errorMsg + "\u2022 Please enter only numeric values into Contact Number 1 field ! <br />";
				}
			}
			
			if (contactNumber2 != null && contactNumber2 != "" && contactNumber2 != 0) {
				if (!regExp.test(contactNumber2)) {
					
					validationFlag = false;
					errorMsg = errorMsg + "\u2022 Please enter only numeric values into Contact Number 2 field ! <br />";
				}
			}
			// Added for task # 290 -Start
			var customerUserIdDetails = document.getElementById("customerIdDetails").value;
			if (customerUserIdDetails.length > 265) {

				validationFlag = false;
				errorMsg = errorMsg + "\u2022 Customer User Id Details should be less than 265 characters !  <br />";
			}
			// Added for task # 290 - End
			/* Start - Validations for all types of Experiences - Digdershika */
			 /*Check for validation*/
			 var relevantExpYY = document.getElementById("relevantExpYear").value;
			 var relevantExpMM = document.getElementById("relevantExpMonth").value;
			 var relevantExpInFloat = parseFloat("00.00");		
			 
			 var yashExpValue = document.getElementById("yashExp").value;
			 var yashExpFloatValue = parseFloat(yashExpValue);
			 
			 var totalExpValue = document.getElementById("totalExp").value;
			 var totalExpFloatValue = parseFloat(totalExpValue);
			 
			 var totalExpYY = document.getElementById("totalExpYear").value;
			 var totalExpMM = document.getElementById("totalExpMonth").value;
			 var totalExpInFloat = parseFloat("00.00");	
			 
			 /*if YY and MM for relevant exp is in correct format then save value in relevant exp hidden field and let it pass to backend*/
			//For total
			 if (!basicValidationsNullTypeEmpty(totalExpYY)) {
					validationFlag = false;
					$("#totalExpYear").addClass("brdrRed");					
					errorMsg = errorMsg + "\u2022 Please enter Total experience (years) ! <br />";						
		 	 } else if (!basicValidationsNullTypeEmpty(totalExpMM)) {
					validationFlag = false;
					$("#totalExpMonth").addClass("brdrRed");
					errorMsg = errorMsg + "\u2022 Please enter Total experience (months) ! <br />";
		 	 } else if(basicValidationsNullTypeEmpty(totalExpYY)  && basicValidationsNullTypeEmpty(totalExpMM)){		 		 
				 var totalExpInString = totalExpYY.toString()+"."+totalExpMM.toString();
				 totalExpInFloat = parseFloat(totalExpInString);
				 $("#totalExpYear").removeClass("brdrRed");
				 $("#totalExpMonth").removeClass("brdrRed");
				 document.getElementById("totalExp").value=totalExpInString;
			 }
			 
			 //For relevant
			 if (!basicValidationsNullTypeEmpty(relevantExpYY)) {
					validationFlag = false;
					$("#relevantExpYear").addClass("brdrRed");					
					errorMsg = errorMsg + "\u2022 Please enter Relevant experience (years) ! <br />";						
		 	 } else if (!basicValidationsNullTypeEmpty(relevantExpMM)) {
					validationFlag = false;
					$("#relevantExpMonth").addClass("brdrRed");
					errorMsg = errorMsg + "\u2022 Please enter Relevant experience (months) ! <br />";
		 	 } else if(basicValidationsNullTypeEmpty(relevantExpYY)  && basicValidationsNullTypeEmpty(relevantExpMM)){		 		 
				 var relevantExpInString = relevantExpYY.toString()+"."+relevantExpMM.toString();
				 relevantExpInFloat = parseFloat(relevantExpInString);
				 $("#relevantExpYear").removeClass("brdrRed");
				 $("#relevantExpMonth").removeClass("brdrRed");
				 //document.getElementById("relevantExp").value=relevantExpInFloat;
				 document.getElementById("relevantExp").value=relevantExpInString;
			 }
			
			 /*Start - Check for less than and greater than*/
			 if(totalExpInFloat<yashExpFloatValue){
					validationFlag = false;
					 $("#totalExpYear").addClass("brdrRed");
					 $("#totalExpMonth").addClass("brdrRed");
					 errorMsg = errorMsg + "\u2022 Please enter Total experience greater or equal to Yash experience ! <br />";
			 }else if(relevantExpInFloat>totalExpInFloat){
				 validationFlag = false;
				 $("#relevantExpYear").addClass("brdrRed");
				 $("#relevantExpMonth").addClass("brdrRed");
				 errorMsg = errorMsg + "\u2022 Please enter Relevant experience less than Total Experience ! <br />";
			 }else if(relevantExpInFloat<yashExpFloatValue){
				 validationFlag = false;
				 $("#relevantExpYear").addClass("brdrRed");
				 $("#relevantExpMonth").addClass("brdrRed");
				 errorMsg = errorMsg + "\u2022 Please enter Relevant experience greater or equal to Yash experience ! <br />";
			 }
			 /*End - Check for less than and greater than*/
			/* End - Validations for all types of Experiences - Digdershika */
			
			//added by Barkha
				var totalExp=document.getElementById("totalExp").value;
				if (!basicValidationsNullTypeEmpty(totalExp)){
					validationFlag = false;
					$("#totalExp").addClass("brdrRed");					
					errorMsg = errorMsg + "\u2022 Please enter Total experience ! <br />";	
					stopProgress();
					if (errorMsg.length > 0)
						showError(errorMsg);
					return false;
				}
				var year=totalExp.split(".",1);
				var fractional = parseInt(totalExp.split(".")[1]);
				var months=parseInt(year*12);
				var total=months+fractional; 
				
			    
	 		    var primarySkillsTable=$("#primarytSkillsTable tbody");
	 		    var secondarySkillsTable=$("#secondarySkillsTable tbody");
	 		    var primaryCheckBoxFlag=true;
	 		    var primaryInputBoxFlag=true;
	 		   	var secondaryCheckBoxFlag=true;
			    var secondaryInputBoxFlag=true;
	 		    primarySkillsTable.find('tr').each(function () {
			        columns = $(this).find('td');
			            primaryExperience =columns.eq(1).find('.primaryExperience input[type="text"]').val();
			            if(!columns.eq(0).find('input[type="checkbox"]').is(':checked') && columns.eq(1).find('.primaryExperience input[type="text"]').val()!=""){
			            	validationFlag = false;
			            	if(primaryCheckBoxFlag){
			            		showError("Please select Primary Skill checkbox for corresponding value you entered");
			            		primaryCheckBoxFlag=false;
			            	}
			            	stopProgress();
						
			            }
			            if(primaryExperience>total&&columns.eq(0).find('input[type="checkbox"]').is(':checked') && columns.eq(1).find('.primaryExperience input[type="text"]').val()!=""){
			            	validationFlag = false;
			            	if(primaryInputBoxFlag){
			            		showError("Primary Skill experience should be less than total experience");
			            		primaryInputBoxFlag=false;
			            	}
			              	stopProgress();
			              	$(columns.eq(1).find('.primaryExperience input[type="text"]')).addClass("brdrRed");
			              
			              }
			            else
			            	$(columns.eq(1).find('.primaryExperience input[type="text"]')).removeClass("brdrRed");
			    });   
	 		    
	 		   secondarySkillsTable.find('tr').each(function () {
			       columns = $(this).find('td');
			            secondaryExperience =columns.eq(1).find('.secondaryExperience input[type="text"]').val();
			            if(!columns.eq(0).find('input[type="checkbox"]').is(':checked') && columns.eq(1).find('.secondaryExperience input[type="text"]').val()!=""){
			            	validationFlag = false;
			            	if(secondaryCheckBoxFlag){
			            		showError("Please select Secondary Skill checkbox for corresponding value you entered");
			            		secondaryCheckBoxFlag=false;
			            	}
			            	stopProgress();
			            }
			         
			            if(secondaryExperience>total&&columns.eq(0).find('input[type="checkbox"]').is(':checked') && columns.eq(1).find('.secondaryExperience input[type="text"]').val()!=""){
			            	validationFlag = false;
			            	if(secondaryInputBoxFlag){
			            		showError("Secondary Skill experience should be less than total experience");
			            		secondaryInputBoxFlag=false;
			            	}
			              	stopProgress();
		            		$(columns.eq(1).find('.secondaryExperience input[type="text"]')).addClass("brdrRed");
			              }		
			            else
				           	$(columns.eq(1).find('.secondaryExperience input[type="text"]')).removeClass("brdrRed");
			    }); 
	 		   
			if (!validationFlag) {
				stopProgress();
				if (errorMsg.length > 0 && errorMsg!="")
					showError(errorMsg);
				return false;
			}

			/** Submit the form when no validation is failed */
			else if (form_data) {
				//stopProgress();				
				startProgress();
				$("form#employeeProfile")
						.submit(function() {
							//showSuccess("Profile has been updated successfully!!");
							//parent.$.fancybox.close();
							//window.location.reload();
						});

			} else {
				stopProgress();
				return;
			}
		});
						$('#upload')
								.click(
										function() {
											$('body')
													.append(
															'<div id="popUp_div" class="popUp"></div>');
											$('.popUp').show();
										});
						$('.popUp_cancel , .close_popUp').click(function() {
							$('.popUp').hide();
						});

						/*----------------------File Upload-----------------*/

						$("#fileUpload")
								.live(
										"change",
										function() {
											var iSize = 0;
											var file = $(
													'input[type="file"]#fileUpload')
													.val();

											$('#uploadInput').val(file);
											var fileValue = $('#uploadInput')
													.val();
											var exts = [ 'doc', 'docx', 'rtf',
													'odt' ];
											var bgDiv = $("<div id = 'bgDiv' />");
											$("body").append(bgDiv);

											var x = $(window).width();
											var y = $(document).height();
											$("#bgDiv").css("height", y);
											$("#bgDiv").css("width", x);

											if (fileValue) {
												// split file name at dot
												var get_ext = file.split('.');
												// reverse name to check extension
												get_ext = get_ext.reverse();

												// check file type is valid as given in 'exts' array
												if ($.inArray(get_ext[0]
														.toLowerCase(), exts) > -1) {
													//alert( 'Allowed extension!' );
													if ($.browser.msie) {
														var objFSO = new ActiveXObject(
																"Scripting.FileSystemObject");
														var sPath = $("#fileUpload")[0].value;
														var objFile = objFSO
																.getFile(sPath);
														var iSize = objFile.size;
														iSize = iSize / 1024;
														//file
													} else
														iSize = ($("#fileUpload")[0].files[0].size);
													//alert(iSize);
													if (iSize > 35840000) {

														$("#bgDiv").css(
																"display",
																"block");
														showError("File Size is Too Large");
														//$('#fileAlert').show().find('p').html('File Size is Too Large');
														$('#uploadInput').val(
																'');
														$("#lblSize").html("");
														$("#bgDiv").remove();
													} else {
														iSize = (Math
																.round(iSize * 100) / 100)
														$("#lblSize").html(
																iSize + "kb");
													}
												} else {
													$("#bgDiv").css("display",
															"block");
													showError("Invalid file Type");
													//$('#fileAlert').show().find('p').html('Invalid file Type' );
													$('#uploadInput').val('');
													$("#lblSize").html("");
													$("#bgDiv").remove();
												}
											}
										});
						$('#uploadInput').click(function() {
							$('input[type="file"]#fileUpload').click();
						});

						$("#ok").click(function() {
							$(this).parent().parent().hide();
							$("#bgDiv").remove();
						});

						$('.see_seleted')
								.click(
										function() {
											try {
												var divForSelectedList = $(
														'.popUp').find(
														'.selected_list');
												divForSelectedList.empty(); // Initially Clean the div
												/* $('.popUp').find(
												'.selected_list').append($("para")); */

												var listElement = $(this)
														.closest('td').find(
																'#skillsList')
												.find('td');
												var selectedSkills = [];

												listElement
														.each(function() {
															if ($(this)
																	.find(
																			"input.chk")
																	.attr(
																			"checked") == "checked") {
												var skill = $(this).find('div.product').text();
												
												
												var record_row= '<td>'+skill+'</td>';
																selectedSkills
														.push(record_row);
															}
														});

										selectedList = $("<table class='selected_skills'><tr><th>Skill</th></tr></table>");
												if (selectedSkills.length > 0) {
													$
															.each(
																	selectedSkills,
																	function(
																			index,
																			value) {
																elem = $("<tr>"
																				+ value
																		+ "</tr>");
																		selectedList
																				.append(elem);
																	});
												}
												$('.popUp').find(
														'.selected_list')
														.append(selectedList);

											} catch (err) {
											}
										});

					});// Close on ready
			 function isNumberKey(evt)
		      {
		         var charCode = (evt.which) ? evt.which : event.keyCode
		         if (charCode > 31 && (charCode < 48 || charCode > 57))
		            return false;
		         return true;
		      }
</script>
</head>
<body>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>
				<div class="container">
					<div class="contentArea" style="padding-top: 8px">
						<div>
							<div class="topSection">
								<!-- Added for task # 299 -->
								<h3>
									<center>
										<font color="black">User Profile</font>
									</center>
								</h3>
								<div class="fl left"></div>
								<div class="fr right"></div>
								<div class="mid"></div>
								<div class="clear"></div>
							</div>
							<div class="midSection">
								<div class="form">

									<form:form action="editProfile/saveProfile" method="post" id="employeeProfile" name="employeeProfile" enctype="multipart/form-data">
										<input type="hidden" name="userAction" id="userAction" />
										
										<input type="hidden" value="${currentLoggedinResource.yashEmployeeId}" name="userProfile.yashEmpId" id="yashEmpId" />
										<table id="formTable" width="100%">

											<tr>

												<td width="13%" align="right">First Name:<span class="astric">*</span></td>
												<td width="18%" align="left">
													<input type="text" name="userProfile.firstName" id="firstName" value="${currentLoggedinResource.firstName}" class="required string" disabled="disabled"/>
													<input type="hidden" name="userProfile.firstName" value="${currentLoggedinResource.firstName}" />
												</td>

												<td width="13%" align="right">Middle Name:</td>
												<td width="18%" align="left">
													<input type="text" name="userProfile.middleName" id="middleName" value="${currentLoggedinResource.middleName}" class="required string" disabled="disabled"/>
													<input type="hidden" name="userProfile.middleName" value="${currentLoggedinResource.middleName}" />
												</td>

												<td width="13%" align="right">Last Name:<span class="astric">*</span></td>
												<td width="18%" align="left">
													<input type="text" id="lastName" name="userProfile.lastName" value="${currentLoggedinResource.lastName}" class="required string" disabled="disabled"/>
													<input type="hidden" name="userProfile.lastName" value="${currentLoggedinResource.lastName}" />
												</td>
												
												<td align="right" width="13%">E-Mail ID:<span class="astric">*</span></td>
												<td align="left" width="18%">
													<input type="hidden" name="userProfile.emailId" value="${currentLoggedinResource.emailId}" />
														<div class="positionRel">
														<!-- Added for task #292 - Start  -->
													<input type="text" value="${currentLoggedinResource.emailId}" name="userProfile.emailId" id="emailId" class="required email string server-validation" oncopy="return false;" onpaste="return false;" oncut="return false;" disabled="disabled"/>
														<!-- Added for task #292 - End  -->
														<div class="errorNumericLast errorNumeric" style="display: none">
															<img src="../resources/images/errorAerrowUp.png" />
																<span>This E-Mail ID is already registered with another resource. </span>
														</div>
														</div>
												</td>
											</tr>
											<tr>

												<td align="right">Contact Number 1:</td>
												<td align="left">
													<div class="positionRel">
														<input type="text" value="${currentLoggedinResource.contactNumberOne}" name="userProfile.contactNumberOne" id="contactNumber1" class="number" maxlength="15" />
													</div>
												</td>
												<td align="right">Contact Number 2:</td>
												<td align="left">
													<div class="positionRel">
														<input type="text" value="${currentLoggedinResource.contactNumberTwo}" name="userProfile.contactNumberTwo" class="number" maxlength="15" id="contactNumber2" />
													</div>
												</td>
												<td align="right">Customer User ID Details:</td>
												<td align="left">
													<textarea id="customerIdDetails" name="userProfile.customerIdDetail" value="userProfile.customerIdDetail" cols="" rows="" class="string">${currentLoggedinResource.customerIdDetail}</textarea>
												</td>
 												<td align="right">Upload Image :</td>
                            					<td align="left"><input type="file" id="uploadImage"  name="userProfile.uploadImage" /></td>
											</tr>
											<tr>
												<td align="right">Grade:</td>
												<td align="left">
													<input type="text" value="${currentLoggedinResource.grade}" name="currentLoggedinResource.gradeId.grade" disabled="disabled" id="grade" class="required string" />
													<input type="hidden" name="currentLoggedinResource.gradeId.grade" value="${currentLoggedinResource.grade}" />
												</td>
												<td align="right">Designation:</td>
												<td align="left">
													<input type="text" value="${currentLoggedinResource.designationName}" name="currentLoggedinResource.designationId.designationName" disabled="disabled" id="designation" class="required string" />
													<input type="hidden" name="currentLoggedinResource.designationId.designationName" value="${currentLoggedinResource.designationName}" />
												</td>
												<td align="right">Upload Resume:</td>
												<td align="left">
													<input type="file" id="uploadResume"  name="resource.uploadResume" /> 
													<input type="hidden" value="" id="uploadResumeFileName" name="resource.uploadResumeFileName" />
											    </td>
												<td></td>
												<td style="font-weight: bold;">Image size should not be more than 1 MB</td>

											</tr>
											
											<tr></tr>
											<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
											<tr>
												<td align="right">RM1:</td>
												<td align="left">
													<input type="text" value="${currentLoggedinResource.resourceManager1}" name="currentLoggedinResource.currentReportingManager.employeeNamee" disabled="disabled" id="designation" class="required string" />
													<input type="hidden" name="currentLoggedinResource.currentReportingManager.employeeNamee" value="${currentLoggedinResource.resourceManager1}" />
												</td>
													
												<td align="right">RM2:</td>
												<td align="left">
													<input type="text" value="${currentLoggedinResource.resourceManager2}" name="currentLoggedinResource.currentReportingManagerTwo.employeeName" disabled="disabled" id="designation" class="required string" />
													<input type="hidden" name="currentLoggedinResource.currentReportingManagerTwo.employeeName" value="${currentLoggedinResource.resourceManager2}" />
												</td>
												
												<!-- Download Resume on Dashboard Edit Profile -->
												<td align="right">Available Resume:</td>
												 <td align="left">
													<input type="text" value="${currentLoggedinResource.uploadResumeFileName}"  id="downloadResume" disabled="disabled" />
													<c:if
														test="${not empty currentLoggedinResource.uploadResumeFileName}">

														<a class="pointer action_btn"
															onclick="ResumefileDownload(${userEmpId})"
															title="Download Resume"> <i class="fa fa-download"
															style="padding: 2px; font-size: 20px"></i></a>
													</c:if>
												</td> 
											     <td></td>
												<td><a href="/rms/editProfile/resumeFormat">Download YASH Resume Template</a></td>
												</tr>
												<tr></tr>
											<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
												<tr>
													<%-- <td align="right">Total Exp (YY.MM):<span class="astric">*</span></td>
												<td align="left">												   
													<input type="text" value="${currentLoggedinResource.totalExp}" name="userProfile.totalExp"  id="totalExp" class="required number" />
													<input type="text" value="${currentLoggedinResource.totalExp}" name="userProfile.totalExp"  id="totalExp" class="required number" disabled="disabled"/>
													<input type="text" value="${currentLoggedinResource.totalExp}" name="userProfile.totalExp"  id="totalExp" class="required number"/>
													<input type="hidden" value="${currentLoggedinResource.totalExp}" name="userProfile.totalExp"/>
													<!-- End -  code for Total exp disable it- Digdershikha -->
												</td> --%>
												<!-- New Code start -->
												<td align="right">Total Exp (YY.MM):<span class="astric">*</span></td>
												<td align="left">																										
													<div>													
													<input type="hidden" value="${currentLoggedinResource.totalExp}" name="userProfile.totalExp"  id="totalExp" class="required" />
														 <select onfocus='this.size=05;' onblur='this.size=1;' onchange='this.size=1; this.blur();' name="userProfile.totalExp"  id="totalExpYear" class="required number"
														 style="width:65px;float: left;">
														</select>
													 
														<select onfocus='this.size=05;' onblur='this.size=1;' onchange='this.size=1; this.blur();' name="userProfile.totalExp"  id="totalExpMonth" class="required number"
														 style="width:68px;float: left;margin-left: 5px;">
														</select>
													</div>
												</td>
												<!-- New Code End -->
												<td align="right">Relevant IT Exp (YY.MM):</td>
												<td align="left">																										
													<%-- <input type="text" value="${currentLoggedinResource.relevantExp}" name="userProfile.relevantExp"  id="relevantExp" class="required number" /> --%>	
													<div>													
													<input type="hidden" value="${currentLoggedinResource.relevantExp}" name="userProfile.relevantExp"  id="relevantExp" class="required" />
														 <select onfocus='this.size=05;' onblur='this.size=1;' onchange='this.size=1; this.blur();' name="userProfile.relevantExp"  id="relevantExpYear" class="required number"
														 style="width:65px;float: left;">
														</select>
													 
														<select onfocus='this.size=05;' onblur='this.size=1;' onchange='this.size=1; this.blur();' name="userProfile.relevantExp"  id="relevantExpMonth" class="required number"
														 style="width:68px;float: left;margin-left: 5px;">
														</select>
													</div>
												</td>
												<td align="right">Experience In Yash :</td>
												<td align="left">
													<input type="text" value="${currentLoggedinResource.yashExp}" name="currentLoggedinResource.yashExp" disabled="disabled" id="yashExp" class="required string" />
												</td>
												</tr>
											<tr></tr>
											<tr></tr>
											<tr></tr>
										</table>
										<table border="0" cellspacing="0" cellpadding="0" width="100%">
											<tr>
												<td width="49%" align="left" valign="top" id="primarytSkillsTable">
													<ol class="search_list">
														<li class="first">
															<h3 class="search_head accordianActive">Primary Skills</h3>
															<div class="filter_selected">
																<a class="linkBtn see_seleted" href="javascript:void(0);" style="text-align: center" onclick="setSkillType('Primary')">
																	View Selected <img src="../resources/images/filter_icon.png" width="13" height="12" align="absmiddle" />
																</a>
															</div>
															<div class="clear"></div>
															<div class="search_body">
																<div class="text_field_cancel_button">
																	<input name="" placeholder="Search..." type="text" id="search" autocomplete="off" class="search-box-bg selectarea searchInp" searchDiv="product_list" /> 
																	<a class="searchClearIcon" href="javascript:void(0);"><img border="0" id="search_clear" src="../resources/images/search-box-cancel.png" /></a>
																</div>
																<div class="clear"></div>
																<div id="skillsList">
																	<div class="product-list box">
																		<table class="skill_table full">
																		<thead>
																			<tr>
																				<th style="width: 194px;">Skills</th>
																				<th style="width: 137px;">Exp (In Months)</th>
																				<th style="width: 161px;">Ratings</th>
																			</tr>
																		</thead>
																		<tbody>
																			<c:forEach var="primarySkills" items="${primarySkills}" varStatus="status">
																				<tr>
																				<td>
																					<input name="entries[${status.index}].primarySkill_Type" type="hidden" value="Primary" /> 
																						<c:set var="isSelected" value=""></c:set> 
																						<c:if test="${not empty userProfilePrimarySkillList}">
																							<c:forEach var="userProfilePrimarySkillList" items="${userProfilePrimarySkillList}">
																								<c:choose>
																									<c:when test="${userProfilePrimarySkillList.primarySkillId==primarySkills.id}">
																										<c:set var="isSelected" value="checked"></c:set>
																										<input name="entries[${status.index}].primarkSkillPKId" id="primarkSkillPKId:${status.index}" type="hidden" value="${userProfilePrimarySkillList.primarySkillPKId}" />
																									</c:when>
																								</c:choose>
																							</c:forEach>
																						</c:if> 
																					<input name="entries[${status.index}].primarySkill_Id" id="primarySkill_Id:${status.index}" type="checkbox" value="${primarySkills.id}"  class="chk" ${isSelected} />

																					<div class="product">${primarySkills.skill}</div>
																				</td>
																						<td>
																							<div class="primaryExperience">
																							<%-- <c:forEach var="primaryExperienceList" items="${userProfilePrimarySkillList.primaryExperience}"> --%>
																								<c:set var="primaryExperience" value=""></c:set>
																								<c:if
																									test="${not empty userProfilePrimarySkillList}">
																									<c:forEach var="userProfilePrimaryExperieceList"
																										items="${userProfilePrimarySkillList}">
																										<c:choose>
																											<c:when
																												test="${userProfilePrimaryExperieceList.primarySkillId==primarySkills.id}">
																												 <c:set var="primaryExperience"
																													value="${userProfilePrimaryExperieceList.primaryExperience}" ></c:set> 
																											</c:when>
																										</c:choose>
																									</c:forEach>
																								</c:if>
																								<input name="entries[${status.index}].primaryExperience" id="primarySkill_experience:${status.index}" type="text" value="${primaryExperience}" onkeypress="return isNumberKey(event)" maxlength="3"/>
																							<%-- 	</c:forEach> --%>
																							</div>
																						</td>
																						<td>
																					<div class="skillsRating">
																						<select
																							name="entries[${status.index}].primarySkillRating_Id"
																							id="primarySkillRatingId;${status.index}">
																							<c:forEach var="ratingList" items="${rating}">
																								<c:set var="isOptionSelected" value=""></c:set>
																								<c:if
																									test="${not empty userProfilePrimarySkillList}">
																									<c:forEach
																										var="userProfilePrimaryRatingSkillList"
																										items="${userProfilePrimarySkillList}">
																										<c:if
																											test="${userProfilePrimaryRatingSkillList.primarySkillRatingId==ratingList.id and userProfilePrimaryRatingSkillList.primarySkillId==primarySkills.id}">
																											<c:set var="isOptionSelected"
																												value="selected='selected'"></c:set>
																										</c:if>
																									</c:forEach>
																								</c:if>
																								<option ${isOptionSelected}
																									value="${ratingList.id}">${ratingList.name}</option>
																							</c:forEach>

																						</select>
																					</div>
																					</td>
																				</tr>
																			</c:forEach>
																			</tbody>
																		</table>
																	</div>
																</div>
															</div>
														</li>
													</ol>
												</td>
												<td width="2%"></td>
												<td width="49%" align="left" valign="top" id="secondarySkillsTable">
													<ol class="search_list">

														<li class="first">
															<h3 class="search_head accordianActive">Secondary
																Skills</h3>
															<div class="filter_selected">
																<a class="linkBtn see_seleted"
																	href="javascript:void(0);" style="text-align: center" onclick="setSkillType('Secondary')">View
																	Selected <img src="../resources/images/filter_icon.png"
																	width="13" height="12" align="absmiddle" />
																</a>
															</div>
															<div class="clear"></div>
															<div class="search_body">
																<div class="text_field_cancel_button">
																	<input name="" placeholder="Search..." type="text" id="search"
																		autocomplete="off"
																		class="search-box-bg selectarea searchInp"
																		searchDiv="product_list" /> <a
																		class="searchClearIcon" href="javascript:void(0);"><img
																		border="0" id="search_clear"
																		src="../resources/images/search-box-cancel.png" /></a>
																</div>
																<div class="clear"></div>
																<div id="skillsList">
																	<div class="product-list box">
																	<table class="skill_table full">
																		<thead>
																			<tr>
																				<th style="width: 194px;">Skills</th>
																				<th style="width: 137px;">Exp (In Months)</th>
																				<th style="width: 161px;">Ratings</th>
																			</tr>
																		</thead>
																		<tbody>
																			<c:forEach var="secondariesSkills"
																				items="${secondarySkills}" varStatus="status">
																				<tr><td>
																				<input	name="entries[${status.index}].secondarySkill_Type"
																					type="hidden" value="Secondary" /> <c:set
																						var="isSelected2" value=""></c:set> <c:if
																						test="${not empty userProfileSecondarySkillList}">

																						<c:forEach var="userProfileSecondarySkillList"
																							items="${userProfileSecondarySkillList}">
																							<c:choose>
																								<c:when
																									test="${userProfileSecondarySkillList.secondarySkillId==secondariesSkills.id}">
																									<c:set var="isSelected2" value="checked"></c:set>
																									<input
																										name="entries[${status.index}].secondarySkillPKId"
																										type="hidden"
																										value="${userProfileSecondarySkillList.secondarySkillPKId}" />
																								</c:when>
																							</c:choose>
																						</c:forEach>
																					</c:if> 
																					<input name="entries[${status.index}].secondarySkill_Id" type="checkbox" value="${secondariesSkills.id}" class="chk" ${isSelected2} />

																					<div class="product">${secondariesSkills.skill}</div>
																					</td>
																					<td>
																					<div class="secondaryExperience">
																						<c:set var="secondaryExperience" value=""></c:set>	
																						<c:if test="${not empty userProfileSecondarySkillList}">
																						<c:forEach var="userProfileSecondaryExperienceList"
																										items="${userProfileSecondarySkillList}">
																										<c:choose>
																										<c:when test="${userProfileSecondaryExperienceList.secondarySkillId==secondariesSkills.id}">
																										<c:set var ="secondaryExperience" value="${userProfileSecondaryExperienceList.experience}"></c:set>
																										</c:when>
																										</c:choose>
																										</c:forEach>
																						</c:if>
																						<input name="entries[${status.index}].secondaryExperience" id="secondariesSkills_secondaryExperience:${status.index}" type="text" value="${secondaryExperience}" onkeypress="return isNumberKey(event)" maxlength="3"/>		
																							</div>
																					</td>
																					<td>
																					<div class="skillsRating">
																						<select
																							name="entries[${status.index}].secondarySkillRating_Id">
																							<c:forEach var="ratingList" items="${rating}">
																								<c:set var="isSecondryOptionSelected" value=""></c:set>
																								<c:if test="${not empty userProfileSecondarySkillList}">
																									<c:forEach
																										var="userProfileSecondaryRatingSkillList"
																										items="${userProfileSecondarySkillList}">
																										<c:if
																											test="${userProfileSecondaryRatingSkillList.secondarySkillRatingId==ratingList.id and userProfileSecondaryRatingSkillList.secondarySkillId==secondariesSkills.id}">
																											<c:set var="isSecondryOptionSelected"
																												value="selected='selected'"></c:set>
																										</c:if>
																									</c:forEach>
																								</c:if>
																								<option ${isSecondryOptionSelected}
																									value="${ratingList.id}">${ratingList.name}</option>
																							</c:forEach>
																						</select>
																					</div>
																					</td>
																					</tr>
																					<div class="clear"></div></li>
																			</c:forEach>
																			</tbody>
																			</table>
																		</ul>
																	</div>
																</div>
															</div>
														</li>
													</ol>
												</td>
											</tr>
											<tr>
												<td colspan="3">&nbsp;</td>
											</tr>
											<tr>
												<td colspan="3" align="right"><input type="submit"
													value="Save" id="save" /></td>
											</tr>
										</table>


										<%-- </form> --%>
									</form:form>
								</div>
								<div class="clear"></div>
							</div>
							<div class="botSection">
								<div class="fl left"></div>
								<div class="fr right"></div>
								<div class="mid"></div>
								<div class="clear"></div>
							</div>
						</div>
					</div>
				</div>

			</td>
		</tr>
	</table>
	<div id="fileAlert">
		<p></p>
		<div class="fileAlertBtm">
			<input type="button" name="Ok" value="Ok" id="ok" />
		</div>
	</div>

	<!--popUp-->
	<div id="popUp_div" class="popUp">&nbsp;</div>
	<div id="assign_popUp" class="popUp selectedItemsPopup">
		<h1 class="green_bg_head" id="para" name="para"></h1>
		
		<div class="selected_list"></div>
		<div class="closeAccordianPopUp"></div>
	</div>
	
</body>
</html>

<!-- Download Resume on Dashboard Edit Profile Ajax Call -->
<script>
function ResumefileDownload(id){
	 $.ajax({				
       url: 'downloadResume',
    	contentType: "application/json",
    	async:false,
    	data: {
    			"reqid":id
    		  
		},  	
		success : function(response) { 	
    		if(response==""){
    			
    			$.alert("Resume Not Available", "RESUME");
    			
    			}else{     	     			     			     		
    		    if(id !== ''){
					window.location.href = 'downloadResume?reqid='+id;
				} 
				else{				 
					$.alert("Resume Not Available", "RESUME");
				}
   		}
    	},
    	 error:function(data){
    		 $.alert("Resume Not Available", "RESUME");
    		 } 
    	
	}); 
};
$.extend({ alert: function (message, title) {
	  $("<div></div>").dialog( {
	    buttons: { "Ok": function () { $(this).dialog("close"); } },
	    close: function (event, ui) { $(this).remove(); },
	    resizable: false,
	    title: title,
	    modal: true
	  }).text(message);
	}
	});

</script>
