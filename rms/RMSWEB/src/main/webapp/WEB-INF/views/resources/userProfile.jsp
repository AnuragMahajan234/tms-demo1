<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
   	<spring:url value="/resources/styles/style.css?ver=${app_js_ver}" var="style_css"/>
 <link href="${style_css}" rel="stylesheet" type="text/css"></link>
<spring:message code="application_js_version" var="app_js_ver"
	htmlEscape="false" />
<spring:url value="/resources/styles/popupstyle.css?ver=${app_js_ver}"
	var="popupstyle_css" />
<spring:url value="resources/js/rmsUIValidation.js"
	var="rmsUIValidation_js" />
<script src="${rmsUIValidation_js}" type="text/javascript"></script>
<spring:url
	value="/resources/js-framework/toastr/toastr.js?ver=${app_js_ver}"
	var="toastr_js" />
<link rel="stylesheet"
	href="/rms/resources/css/styles.css?ver=${app_js_ver}">
<spring:url
	value="/resources/js-framework/toastr/toastr.js?ver=${app_js_ver}"
	var="toastr_js" />
<spring:url
	value="/resources/js-framework/blockUI/jquery.blockUI.js?ver=${app_js_ver}"
	var="blockUI" />
<script src="${toastr_js}" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="${blockUI}" type="text/javascript">
</script>
<style>
#requestRequisitionReportTable .dataTables_scroll {
	overflow-x: hidden !important;
}

#requestRequisitionReportTable .dataTables_scrollHead {
	width: 100% !important;
}

table.dataTbl.display.tablesorter.addNewRow.alignCenter.my_table.tablefixed.dataTable
	{
	table-layout: auto;
}

table.dataTbl.display.tablesorter.addNewRow.alignCenter.my_table.dataTable
	{
	max-height: 751px;
	display: table !important;
}

table.dataTbl tr.selected td {
	background: #a4e3f1;
}

table.dataTbl.display.tablesorter.addNewRow.alignCenter.my_table.dataTable
	{
	table-layout: fixed;
}

body .editProfile-table .align-center {
	text-align: center !important;
}

.addSkillModal-table {
	margin-left: 0px !important;;
}

.editProfile-table {
	position: relative;
	border: 1px solid #ccc !important;
	border-collapse: collapse;
	font-size: 13px;
	background-color: #fff;
}

.editProfile-table thead {
	padding-bottom: 3px;
}

.editProfile-table th {
	vertical-align: middle;
	text-overflow: ellipsis;
	text-align: left !important;
	font-weight: 700;
	line-height: 24px;
	border-right: 0px solid #FFFFFF !important;
	letter-spacing: 0;
	border-radius: 0 !important;
	font-size: 12px;
	color: rgba(0, 0, 0, .54);
	padding-bottom: 8px;
	background: #fff !important;
	color: #333 !important;
	border: none;
	padding-left: 24px !important;
}

.editProfile-table td, .editProfile-table th {
	position: relative;
	height: 48px;
	box-sizing: border-box;
}

.editProfile-table td {
	border-top: 1px solid rgba(0, 0, 0, .12);
	border-bottom: 1px solid rgba(0, 0, 0, .12);
	vertical-align: middle;
	text-align: left !important;
	background: #fff !important;
	border: 0px solid #FFFFFF !important;
	border-top: 1px solid rgba(0, 0, 0, .12) !important;
	border-bottom: 1px solid rgba(0, 0, 0, .12) !important;
	border-radius: 0 !important;
	padding-left: 24px !important;
	padding-right: 18px !important;
}

.editProfile-table tbody tr:hover {
	background-color: #ddd;
}

.remove_bg thead th {
	background: #fff !important;
	color: #000000 !important;
	text-align: left !important;
	border-radius: 5px;
	border: none !important;
	border-bottom: 0px solid black !important;
	padding: 0px;
	font-weight: bold !important;
	font-size: 11px;
}

.my_table thead th {
	background: #fff !important;
	color: #333 !important;
	border-bottom: 1px solid #ccc !important;
}

#records_tableId thead th {
	/* width: 200px; */
	word-wrap: break-word;
}

.add-skills {
	margin-left: 115px;
	color: #144358 !important;
	padding-top: 0px !important;
}

.add-skills:hover, .add-skills-sec:hover {
	box-shadow: none !important;
	text-decoration: underline;
}

.add-skills-sec {
	margin-left: 97px;
	color: #144358 !important;
}

.sec-tbl-mrgin {
	margin-top: 40px;
}

td.stop_click.action-dropdown.dropbtn.dropdowns.stop_click.align-center.action-dropdown.dropdowns
	{
	padding-right: 30px !important;
}

td.stop_click.align-center.action-dropdown.dropdowns.dropbtn {
	height: 48px;
}

.dropdown:hover .dropbtn {
	background-color: #3e8e41;
}

.dropdown-menu.dropdown-content {
	border-color: #F4F4F4;
}

.dropdown.dropleft.dropDownHover {
	text-align: center;
}

.dropdown.dropleft.dropDownHover:hover {
	border-radius: 50%;
	background: #6e9ece;
	color: white;
	height: 24px;
	width: 24px;
	padding: 5px 1px 2px 1px;
}

.modal-width-change {
	border-radius: 0px !important;
	padding-top: 0px !important;
	padding-bottom: 0px !important;
}

.dwnlod-resume {
	margin-top: 30px;
}

.label-prop {
	color: #666666 !important;
	font-weight: normal !important;
	
}

.value-prop {
	color: #333333 !important;
	font-weight: 500;
}


.preferredLocationMain .ui-combobox {
    width: 100% !important;
}
#employeeProfile .input-group.input-file {
border:none;
}
#employeeProfile label {
font-size: 14px !important;
}

#employeeProfile input {
margin-top:0px !important;
font-size: 14px;
    background: #fff;
}

#employeeProfile .form-control[disabled] {
    background-color: #eee;
}
.heading-prop {
	color: #333333 !important;
	margin-top: 20px;
    margin-bottom: 10px;
    font-family: inherit;
    font-weight: 300;
    line-height: 1.1;
    font-size: 24px;
    text-transform: capitalize;
}

.add-resume {
	color: #144358 !important;
}

.add-resume:hover {
	box-shadow: none !important;
	text-decoration: underline;
}

.next-button, .next-button:hover {
	background: #144358;
	border: 1px solid #144358;
	border-radius: 4px;
	padding: 5px 26px;
	margin-right: 10px;
	color: #fff !important;
	text-decoration: none;
}

.close:hover, .close:focus {
	color: #000000;
	border-left: none;
	border-bottom: none;
}

.editProfile-table tbody tr.content:hover td {
	background-color: #c99e4d85 !important;
}

.remove-padding-y {
	padding-right: 0px !important;
}

.remove-padding-m {
	padding-left: 5px !important;
}

.add-margin-modal {
	margin-top: 27px;
}

.btn-align {
	text-align: left !important;
}

.close-btn {
	background: #f2f2f2;
	border: 1px solid #dddddc;
	border-radius: 4px;
	padding: 5px 26px;
	margin-right: 10px;
	color: #a9a9a8 !important;
	text-decoration: none;
}

.btn-alignment {
	text-align: right;
	margin-bottom: 7px;
}

.border-btm {
	border-bottom: 1px solid #dddddc;
}

form input[type="text"] {
	height: auto;
}

.ui-menu .ui-menu-item a {
	font-size: 12px;
}

.row {
	margin-right: -10px;
	margin-left: -10px;
}

.primary_skill td:nth-child(1), .secondary_skill td:nth-child(1) {
	width: 220px;
}

.add_skill_row {
	width: 100%;
	margin-bottom: 2px;
	float: left;
	background: #ecf0f5;
	padding: 10px;
}

.my_submit_btn {
	font-size: 16px;
	margin-right: 6px;
}

.new_skill td {
	background: #ecf0f5 !important;
}
/*Tble drpdown border-radius changes-27-5-19*/
#records_tableId .form-control {
	border-radius: 50px;
}

</style>


<script type="text/javascript">
var total=null;
$(document).ready(function() {
	
	//$(".new_skill").hide();
	var skilltype='';

	function setSkillType(skillType){
		
		skilltype = "Selected "+skillType+" Skills";
		
		document.getElementById('para').innerHTML = skilltype;
	}
 
	stopProgress();
 
	<c:if test="${not empty success}">
		showSuccess("Your profile has been updated successfully!!");
	</c:if>
 
	function startProgress() {
		$.blockUI({
			message : '</br></br><div class="circle"></div><div class="circle1"></div><center>Just a moment</center>'
		});
	}
 
	function stopProgress() {
		$.unblockUI();
	}
 
	function showSuccess(msg) {
		toastr.success(msg, "Success").delay(4000);
	}
 
	function showError(msg) {
		toastr.error(msg, "Error").delay(4000);
	}
 
	function showWarning(msg) {
		toastr.warning(msg, "Warning").delay(4000);
	}
	
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
	
 
	var relevantExpValue = ${currentLoggedinResource.relevantExp};
	var relevantExpYear=Math.floor(relevantExpValue);
	var relevantMonth=((relevantExpValue % 1).toFixed(2)).slice(".");
	 var totalExpValue = document.getElementById("totalExp").value;
	
	 var totalExpFloatValue = parseFloat(totalExpValue);
	 

	

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
	 total=(totalExpYear*12)+parseFloat((totalExpMonth.split(".")[1])); 
	
	document.getElementById("totalExpYear").value = totalExpYear;
	document.getElementById("totalExpMonth").value = totalExpMonth.split(".")[1];
	document.getElementById("totalExp").value  = document.getElementById("totalExpYear").value.toString()+"."+document.getElementById("totalExpMonth").value.toString();
	$('#relevantExpYear, #relevantExpMonth, #totalExpYear, #totalExpMonth').combobox();
	
 
 
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
				//	showSuccess("Profile has been updated successfully!!");
					parent.$.fancybox.close();
					window.location.reload();
				});
 
	} else {
		stopProgress();
		return;
	} 
});
});
</script>
</head>

<body>
	<div class="alertWrapper success">
		<div class="alert alert-success" role="alert"></div>
	</div>
	<div class="alertWrapper danger">
		<div class="alertlistWrapper">
			<span class="closemeAlert">X</span>
			<div class="alert alert-danger" role="alert"></div>
		</div>
	</div>

	<form:form action="editProfile/saveProfile" method="post"
		id="employeeProfile" name="employeeProfile"
		enctype="multipart/form-data">
		<input type="hidden" name="userAction" id="userAction" />
		<input type="hidden" value="${currentLoggedinResource.yashEmployeeId}"
			name="userProfile.yashEmpId" id="yashEmpId" />

		<div class="content-wrapper">
			<h1 class="margin-b-20">
				My Profile<span class="my-user-id pull-right">
					<button type="submit" value="Save" id="save"
						class="btn btn-secondary next-button" style="">Save</button>
				</span>
			</h4>

			<div class="container-fluid outerCard" id="rmsForm1">
				<div class="">
					<div class="col-md-8"></div>
					<div class="col-md-4 btn-alignment"></div>

				</div>
				<div class="">
					<div class="col-xs-12">
						<div class="form-row">
							<h3 class="heading-prop">Personal Details</h3>
						</div>
					</div>
				</div>

				<div class="">
					<div class="col-xs-12">
						<div class="form-group col-md-4 validateMe autoSelect">
							<label for="hiringbgBuSelect"><span data-toggle="tooltip"
								data-placement="top" title="" class="label-prop">First
									Name</span></label>
							<div class="positionRel">
								<input type="text" class="form-control"
									value="${currentLoggedinResource.firstName}" disabled />
							</div>
						</div>
						<div class="form-group col-md-4 validateMe">
							<label for="inputPassword4" class="label-prop">Middle
								Name</label>
							<div class="positionRel">
								<input type="text" class="form-control" class="form-control"
									value="${currentLoggedinResource.middleName}" disabled />
							</div>
						</div>
						<div class="form-group col-md-4 validateMe">
							<label for="bgBuSelect" class="label-prop">Last Name</label>
							<div class="positionRel">
								<input type="text" class="form-control"
									value="${currentLoggedinResource.lastName}" disabled />
							</div>
						</div>

						<div class="form-group col-md-4 validateMe autoSelect">
							<label for="requestorSelect" class="label-prop">Email ID</label>
							<div class="positionRel">
								<input type="text" class="form-control"
									value="${currentLoggedinResource.emailId}" disabled />
							</div>
						</div>

						<div class="form-group col-md-4 validateMe autoSelect">
							<label for="requestorSelect" class="label-prop">Designation
							</label>
							<div class="positionRel">
								<input type="text" class="form-control"
									value="${currentLoggedinResource.designationName}" disabled />
							</div>
						</div>

						<div class="form-group col-md-4 validateMe autoSelect">
							<label for="requestorSelect" class="label-prop">Experience
								In Yash</label>
							<div class="positionRel">
								<%-- <span class="value-prop">${currentLoggedinResource.yashExp}</span> --%>
								<input type="text" class="form-control"
									value="${currentLoggedinResource.yashExp}"
									name="currentLoggedinResource.yashExp" disabled="disabled"
									id="yashExp" class="required string" />
							</div>
						</div>

						<div class="form-group col-md-4 autoSelect">
							<label for="contactNumberOne" class="label-prop">Contact
								Number 1</label> <input type="text" class="form-control"
								value="${currentLoggedinResource.contactNumberOne}"
								name="userProfile.contactNumberOne" id="contactNumber1"
								class="number" maxlength="15">
						</div>

						<div class="form-group col-md-4 autoSelect">
							<label for="contactNumberTwo" class="label-prop">Contact
								Number 2</label> <input type="text" class="form-control"
								value="${currentLoggedinResource.contactNumberTwo}"
								name="userProfile.contactNumberTwo" class="number"
								maxlength="15" id="contactNumber2">
						</div>

						<div class="form-group col-md-4 autoSelect">
							<label class="label-prop">Customer User ID Details</label> <input
								type="text" class="form-control"
								value="${currentLoggedinResource.customerIdDetail}"
								id="customerIdDetails" name="userProfile.customerIdDetail">
						</div>

						<div class="form-group col-md-4 autoSelect">
							<label class="label-prop">Relevant IT Experience</label>
							<div class="">
								<input type="hidden"
									value="${currentLoggedinResource.relevantExp}"
									name="userProfile.relevantExp" id="relevantExp"
									class="required" />
								<div class="col-md-6">
									<div class="positionRel">
										<select onfocus='this.size=05;' onblur='this.size=1;'
											onchange='this.size=1; this.blur();'
											name="userProfile.relevantExp" id="relevantExpYear"
											class="required number"
											style="width: 68px; float: left; margin-left: 5px;">
										</select>
									</div>
								</div>
								<div class="col-md-6">
									<div class="positionRel">
										<select onfocus='this.size=05;' onblur='this.size=1;'
											onchange='this.size=1; this.blur();'
											name="userProfile.relevantExp" id="relevantExpMonth"
											class="required number"
											style="width: 68px; float: left; margin-left: 5px;">
										</select>
									</div>
								</div>
							</div>
						</div>

						<div class="form-group col-md-4 autoSelect">
							<label class="label-prop">Total Experience</label> <input
								type="hidden" value="${currentLoggedinResource.totalExp}"
								name="userProfile.totalExp" id="totalExp" class="required" />
							<div class="">
								<div class="col-md-6">
									<div class="positionRel">
										<input type="hidden"
											value='<fmt:formatNumber type="number" maxFractionDigits="2" value="${currentLoggedinResource.totalExp}"/>'
											name="userProfile.totalExp" id="totalExp" class="required" />
										<select onfocus='this.size=05;' onblur='this.size=1;'
											onchange='this.size=1; this.blur();'
											name="userProfile.totalExp" id="totalExpYear"
											class="required number" style="width: 65px; float: left;">
										</select>

									</div>
								</div>
								<div class="col-md-6">
									<div class="positionRel">

										<select onfocus='this.size=05;' onblur='this.size=1;'
											onchange='this.size=1; this.blur();'
											name="userProfile.totalExp" id="totalExpMonth"
											class="required number"
											style="width: 68px; float: left; margin-left: 5px;">
										</select>

									</div>
								</div>
							</div>
						</div>

						<div class="form-group col-md-4 autoSelect preferredLocationMain">
							<label class="label-prop">Preferred location</label>
							<div class="positionRel">
							 <select name="resource.preferredLocation.location" id="preferredLocation" class="required comboselect check">
								<option ></option>
								<c:forEach var="loc" items="${location}">
									<c:choose>
										<c:when
											test="${currentLoggedinResource.preferredLocation.location==loc.location}">
											<option value="${loc.location}" selected="selected">${loc.location}</option>

										</c:when>
										<c:otherwise>
											<option value="${loc.location}">${loc.location}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
                           </select>
                         </div>
						</div>
						<div class="clearfix"></div>


						<div class="form-group col-md-4 autoSelect customFileInput">
							<label class="label-prop">Upload Resume</label>
							<div class="positionRel">
								<div class="input-group input-file">

									<input type="text" class="inputfile" readonly=""
										placeholder="Choose and upload file" id="uploadResumeFileName"
										name="resource.uploadResumeFileName" /> <input type="file"
										name="resource.uploadResume" value="" class="form-control"
										id="uploadResume" placeholder="Choose and upload file"
										style="display: none;" onChange="onUploadResume(this)">
									<label class="input-group-addon btn-choose" for="uploadResume">
										Browse </label>
								</div>
							</div>
						</div>


						<div
							class="form-group col-md-4 validateMe autoSelect customFileInput">
							<label for="requestorSelect" class="label-prop">Available
								Resume</label>

							<div class="positionRel">
								<div class="input-group input-file">

									<input type="text" class="inputfile" readonly=""
										placeholder="Available Resume"
										value="${currentLoggedinResource.uploadResumeFileName}">
									<label class="input-group-addon btn-choose"
										onclick="ResumefileDownload(${userEmpId})">Download</label>
								</div>
								<a href="/rms/editProfile/resumeFormat">YASH Resume Template<span><i
										class="fa fa-download" aria-hidden="true"> </i></span>
								</a>
							</div>

						</div>

						<div class="form-group col-md-4 autoSelect customFileInput">
							<label class="label-prop">Upload Image</label>
							<!-- <input type="file" id="uploadImage"  name="userProfile.uploadImage" /> -->
							<div class="positionRel">
								<div class="input-group input-file">
									<input type="text" class="inputfile" readonly=""
										placeholder="Choose and upload image" id="uploadImageFileName">
									<input type="file" name="userProfile.uploadImage" valur=""
										class="form-control" id="uploadImage"
										placeholder="Choose and upload file" style="display: none;"
										onChange="onUploadImage(this)"> <label
										class="input-group-addon btn-choose" for="uploadImage">
										<!-- <span class="glyphicon glyphicon-paperclip"></span> -->
										Browse
									</label>
								</div>
								<label style="font-weight: bold;font-size:12px;">Image size should not
									be more than 1 MB </label>
							</div>
						</div>

						 
					</div>
				</div>
				<div class="">
					<div class="col-xs-12">
						<div class="form-row">
							<h3 class="heading-prop">Group & Managers</h3>
						</div>
					</div>
				</div>

				<div class="">
					<div class="col-xs-12">

						<div class="form-group col-md-4 validateMe autoSelect">
							<label for="requestorSelect" class="label-prop">Grade</label>
							<div class="positionRel">
								<input type="text" class="form-control"
									value="${currentLoggedinResource.grade}" disabled />
							</div>
						</div>
						<div class="form-group col-md-4 validateMe autoSelect">
							<label for="requestorSelect" class="label-prop">IRM</label>
							<div class="positionRel">
								<input type="text" class="form-control"
									value="${currentLoggedinResource.resourceManager1}" disabled />
							</div>
						</div>

						<div class="form-group col-md-4 validateMe autoSelect">
							<label for="requestorSelect" class="label-prop">SRM</label>
							<div class="positionRel">
								<input type="text" class="form-control"
									value="${currentLoggedinResource.resourceManager2}" disabled />
							</div>
						</div>
					</div>
				</div>

				<div class="">
					<div class="col-xs-12">
						<div class="form-row">
							<h3 class="heading-prop">Skills</h3>
						</div>

						<div class="col-xs-12">

							<div id="requestRequisitionReportTable">
								<h5>
									<strong>Primary Skill</strong>
								</h5>

								<table
									class="dataTbl primary_skill display tablesorter  alignCenter my_table remove_bg editProfile-table"
									id="records_tableId" style="width: 100%">
									<thead>
										<tr class="content">

											<th class="width110" align="center" valign="middle">Skill
												ID</th>
											<th class="width110" align="center" valign="middle">Exp.
												(in months)</th>
											<th class="width110" align="center" valign="middle">Rating</th>

											<th class="width125" align="center" valign="middle">Actions</th>
										</tr>
									</thead>
									<tbody class="blank_row">
										<tr class="rowClick new_skill">
											<td style="padding-left: 8px !important;">
												<div class="positionRel">

													<select class="form-control required comboselect check"
														id="primarySkillId" placeholder="Select Skill">
														<c:forEach var="primarySkills" items="${primarySkills}"
															varStatus="status">
															<option value="Select Skill" selected="selected"></option>
															<option value="${primarySkills.id}">${primarySkills.skill}</option>
														</c:forEach>
													</select>
												</div>
											</td>

											<td><input type="text" class="form-control"
												placeholder="0" id="primaryExprVal" value="0" /></td>
											<td>
												<div class="positionRel">
													<select class="form-control required check"
														id="primaryRating" placeholder="">
														<option value="1" selected>1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
													</select>
												</div>
											</td>
											<td class="stop_click action-dropdown dropbtn dropdowns">
												<span> <a href="javascript:void(0)" onclick="saveSkills(${currentLoggedinResource.employeeId},'primary')">
													<span data-toggle="tooltip" title="Add New Skill" data-placement="top">
													<i class="fa fa-plus my_submit_btn"></i></span>
                                                     
												</a>
											</span>
											</td>
										</tr>
									</tbody>
									<tbody class="primary_skill-list">
										<c:forEach var="userProfilePrimarySkill"
											items="${userProfilePrimarySkillList}">
											<tr class="rowClick content"
												data-skillId="${userProfilePrimarySkill.primarySkillPKId}">
												<td>
													<div class="positionRel">
														${userProfilePrimarySkill.skillName}</div>
												</td>

												<td><input type="text"
													data-experience="${userProfilePrimarySkill.primaryExperience}"
													value="${userProfilePrimarySkill.primaryExperience}"
													id="priExprVal" class="form-control" placeholder=""
													disabled /></td>
												<td>
													<div class="positionRel">
														<select
															class="form-control required check rating_dropdown"
															placeholder="Select rating" disabled
															data-rating="${userProfilePrimarySkill.primarySkillRatingId}">
															<c:forEach var="ratingList" items="${rating}">
																<c:set var="isOptionSelected" value=""></c:set>
																<c:if
																	test="${userProfilePrimarySkill.primarySkillRatingId==ratingList.id}">
																	<c:set var="isOptionSelected"
																		value="selected='selected'"></c:set>
																</c:if>
																<option ${isOptionSelected} value="${ratingList.id}">${ratingList.id}</option>
															</c:forEach>
														</select>
													</div>
												</td>
												<td class="stop_click action-dropdown dropbtn dropdowns">
													<span> <a href="javascript:void(0)"
														onclick="deleteSkills(${userProfilePrimarySkill.primarySkillPKId},'primary',${userProfilePrimarySkill.primarySkillId})">
															<span data-toggle="tooltip" title="Delete Skill" data-placement="top"><i class="fa fa-trash-o my_submit_btn"></i></span>

													</a> <a href="javascript:void(0)" class="hide update"
														onclick="updateSkills(${userProfilePrimarySkill.primarySkillPKId},'primary', ${userProfilePrimarySkill.primarySkillRatingId}, ${userProfilePrimarySkill.primaryExperience})">
															<span data-toggle="tooltip" title="Save Skill" data-placement="top"><i class="fa fa-check my_submit_btn"></i></span>
													</a> <a href="javascript:void(0)" class="edit"
														onclick="editSkills(this, 'primary')">
														 <span data-toggle="tooltip" title="Update Skill" data-placement="top">
														 <i class="fa fa-pencil my_submit_btn"></i></span>
													</a>
												</span>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>


						<div class="col-xs-12 mrT2 mrB2">

							<div id="requestRequisitionReportTable" class="mrB2">
								<h5>
									<strong>Secondary Skill</strong>
								</h5>
								<table
									class="dataTbl secondary_skill display tablesorter  alignCenter my_table remove_bg editProfile-table"
									id="records_tableId" style="width: 100%">
									<thead>
										<tr class="content">

											<th class="width110" align="center" valign="middle">Skill
												ID</th>
											<th class="width110" align="center" valign="middle">Exp.
												(in months)</th>
											<th class="width110" align="center" valign="middle">Rating</th>

											<th class="width125" align="center" valign="middle">Actions</th>
										</tr>
									</thead>
									<tbody class="blank_row">
										<tr class="rowClick new_skill">
											<td style="padding-left: 8px !important;">
												<div class="positionRel">

													<select class="form-control required comboselect check"
														id="secondarySkillId" placeholder="Select Skill">
														<c:forEach var="secondariesSkills"
															items="${secondarySkills}" varStatus="status">
															<option value="Select Skill" selected="selected"></option>
															<option value="${secondariesSkills.id}">${secondariesSkills.skill}</option>
														</c:forEach>
													</select>
												</div>
											</td>

											<td><input type="text" value="0" class="form-control"
												placeholder="0" id="secondaryExprVal" /></td>
											<td>
												<div class="positionRel">
													<select class="form-control required check"
														id="secondaryRating" placeholder="">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
													</select>
												</div>
											</td>
											<td class="stop_click action-dropdown dropbtn dropdowns">
												<span> <a href="javascript:void(0)"
													onclick="saveSkills(${currentLoggedinResource.employeeId},'secondary')">
														<span data-toggle="tooltip" title="Add New Skill" data-placement="top"><i class="fa fa-plus my_submit_btn"></i></span>

												</a>
											</span>
											</td>
										</tr>
									</tbody>
									<tbody class="secondary_skill-list">
										<c:forEach var="userProfileSecondarySkill"
											items="${userProfileSecondarySkillList}">
											<tr class="rowClick content"
												data-skillId="${userProfileSecondarySkill.secondarySkillPKId}">
												<td>
													<div class="positionRel">
														${userProfileSecondarySkill.skillName}</div>
												</td>

												<td><input type="text"
													data-experience="${userProfileSecondarySkill.experience}"
													value="${userProfileSecondarySkill.experience}"
													id="secExprVal" class="form-control" placeholder=""
													disabled /></td>
												<td>
													<div class="positionRel">
														<select
															class="form-control required check rating_dropdown"
															placeholder="Select rating" disabled
															data-rating="${userProfileSecondarySkill.secondarySkillRatingId}">
															<c:forEach var="ratingList" items="${rating}">
																<c:set var="isOptionSelected" value=""></c:set>
																<c:if
																	test="${userProfileSecondarySkill.secondarySkillRatingId==ratingList.id}">
																	<c:set var="isOptionSelected"
																		value="selected='selected'"></c:set>
																</c:if>
																<option ${isOptionSelected} value="${ratingList.id}">${ratingList.id}</option>
															</c:forEach>
														</select>
													</div>
												</td>
												<td class="stop_click action-dropdown dropbtn dropdowns">
													<span> <a href="javascript:void(0)"
														onclick="deleteSkills(${userProfileSecondarySkill.secondarySkillPKId},'secondary',${userProfileSecondarySkill.secondarySkillId})">
															<span data-toggle="tooltip" title="Delete Skill" data-placement="top"><i class="fa fa-trash-o my_submit_btn"></i></span>

													</a> <a href="javascript:void(0)" class="hide update"
														onclick="updateSkills(${userProfileSecondarySkill.secondarySkillPKId},'secondary', ${userProfileSecondarySkill.secondarySkillRatingId}, ${userProfileSecondarySkill.experience})">
															<span data-toggle="tooltip" title="Save Skill" data-placement="top"><i class="fa fa-check my_submit_btn"></i></span>
													</a> <a href="javascript:void(0)" class="edit" onclick="editSkills(this, 'secondary')"> 
														<span data-toggle="tooltip" title="Update Skill" data-placement="top">
														<i class="fa fa-pencil my_submit_btn"></i>
													</span>
													</a>
												</span>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>
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

<script>
var counter = 0;
var isUpdateMode = false;
var primarySkills = [];
var secondarySkills = [];
var numbers = /^[0-9]+$/;

<c:forEach items="${userProfilePrimarySkillList}" var="userProfilePrimarySkill">
	primarySkills.push(${userProfilePrimarySkill.primarySkillId});
 </c:forEach>
 
 <c:forEach items="${userProfileSecondarySkillList}" var="userProfileSecondarySkill">
	secondarySkills.push(${userProfileSecondarySkill.secondarySkillId});
 </c:forEach>
 
 //add skills function
function saveSkills(employeeId,skillType){
	var skillId=null;
	var exprienceVal=null;
	var rating=null;
	var skillPkId=null;
	var skillName=null;
	
	 
	skillId=$('#'+ skillType+'SkillId').val();
	exprienceVal=$('#'+ skillType+'ExprVal').val();
	rating=$('#'+ skillType+'Rating').val();
	
	
	
	 if(skillId == "Select Skill"){
			var err="Please Select Skill";
	    	showError(err);
			stopProgress();
			return false;
	}else{
			skillId = Number(skillId);
	}
	 
	if(exprienceVal != null && exprienceVal != ""){
			 if(!exprienceVal.match(numbers)){
				 showError("Please enter numeric value as an experience");
				 stopProgress();
				 return false; 
			 }
     }
	if(total<exprienceVal){
	    	var err="Skill experience should be less than total experience";
	    	showError(err);
			stopProgress();
			return false;
	 }
	 
	 if(!exprienceVal){
			exprienceVal = 0;
		}

	 if(skillType == "primary"){
		var skillExist = primarySkills.indexOf(skillId); 
		
	 } else {
		 var skillExist = secondarySkills.indexOf(skillId); 
	}
	 	if (skillExist >= 0) {
			var err="Skill already added. Please edit skill in below list Or add another";
		    showError(err);
			stopProgress();
			return false;
		}
	
   $.ajax({		
	        url: 'addUserSkill',
	    	contentType: "application/json",
	    	async:false,
	    	data: { 
				"skillPkId": skillPkId,
				"skillType":skillType, 
				"employeeId":employeeId,
				"skillId":skillId, 
				"exprienceVal":exprienceVal,
				"rating":rating 
	    		  
			},  	
			success : function(response) {
				var res = JSON.parse(response);
				if(res!=""){
					showSuccess("Your skill added successfully!!");
				createSkillRow(res.PrimarySkillList, 'primary');
				createSkillRow(res.SecondarySkillList, 'secondary');
				counter = 0; 
				$('.new_skill input').val('');
				$('.new_skill select').val('1');
				
				$.each(res.PrimarySkillList, function(indx, item) {
					primarySkills.push(item.skillId);
				});
				$.each(res.SecondarySkillList, function(indx, item) {
					secondarySkills.push(item.skillId);
				});
			}
				}
		}); 
          document.getElementById("primarySkillId").selectedIndex = "Select Skill";
          document.getElementById("secondarySkillId").selectedIndex = "Select Skill";
      }
 
 
 //update skills function
 function updateSkills(pId,skillType){
	var selectedRow = $("tbody").find("[data-skillId='" + pId + "']");
	var priExprVal= selectedRow.find("[data-experience]").val();
	var priRatingVal= selectedRow.find("[data-rating]").val();
	 
	if(total<priExprVal){
    	var err="Skill experience should be less than total experience";
    	showError(err);
		stopProgress();
		return false;
    }
	
	if(priExprVal != null && priExprVal != ""){
		 if(!priExprVal.match(numbers)){
			 showError("Please enter numeric value as an experience");
			 stopProgress();
			 return false; 
		 }
		 
}
	if(!priExprVal){
		 priExprVal = 0;
		}
	
	$.ajax({	
		   
		   url:"updateUserSkill?pId="+pId+"&skillType="+skillType+"&priExprVal="+priExprVal+"&priRatingVal="+priRatingVal,
		   type: "PUT",
		   data: "",	
			success : function(response) {
				var res = JSON.parse(response);
				if(res!=""){
					showSuccess("Your skill updated successfully!!");
				ele = $("tbody").find("[data-skillId='" + res.id + "']");
				ele.find("[data-experience]").val(res.experience).attr('disabled', true );
				ele.find("[data-rating]").val(res.rating).attr('disabled', true );
				$(ele).find('.edit').removeClass('hide');
				$(ele).find('.update').addClass('hide');
				isUpdateMode = false;
		}  
			}
	  });    
	}
	
 
 
//delete skills function
 function deleteSkills(pSkillId,skillType,id){
	$.ajax({
		   url:"deleteUserSkill?pSkillId="+pSkillId+"&skillType="+skillType,
		   type: "DELETE",
		   data: "",	
			success : function(response) {
				var res = JSON.parse(response);
				if(res!=""){
					showSuccess("Your skill deleted successfully!!");
				$("tbody").find("[data-skillId='" + res.id + "']").remove();
				if (skillType === "primary") {
					indx = primarySkills.indexOf(id);
					primarySkills.splice(indx, 1);
				} else {
					indx = secondarySkills.indexOf(id);
					secondarySkills.splice(indx, 1);
				}
			  }  
			}
	  });    
	}

	
	function createSkillRow(skillList, type) {
		var tableHtml = '';
		
		$.each(skillList, function(indx, item) {
			
			var ratingList = renderRatingList(item.rating);
			var createActions = renderActions(item, type);
			var hiddenCls = '';
		       tableHtml += '<tr class="rowClick content '+  hiddenCls +'" data-skillId="' + item.id + '"><td><input type="hidden" value="'+ item.id+ '" id="primarySkillPKId_'+item.id+' " class="form-control" placeholder=""><div class="positionRel">' + item.skillName +'</div></td><td><input type="text" value="'+ item.experience +'" class="form-control" placeholder="0" disabled data-experience="' + item.experience + '"></td><td><select class="form-control required check rating_dropdown" placeholder="0" data-rating="' + item.rating + '" disabled>'+ ratingList +'</select></td><td>'+ createActions +'</td></tr>'
		  });
		if (type === 'primary') {
			$('.primary_skill-list').html(tableHtml);
		} else {
			$('.secondary_skill-list').html(tableHtml);
		}
	}
	
	
	
	
	function renderRatingList(rating){
		var arr = [1,2,3,4,5];
		var options = '';
		$.each(arr, function(item){
			item++;
			if(rating === item) {
				options += '<option value="'+ item +'" selected>'+ item + '</option>';
			} else {
				options += '<option value="'+ item +'">'+ item + '</option>';
			}
		});
		return options;
	}

	function editSkills(ele, type) {
		isUpdateMode = true;
		var parentEle = ele.offsetParent.parentElement;
		$(parentEle).find('.edit').addClass('hide');
		$(parentEle).find('.update').removeClass('hide');
		$(parentEle).find('select, input').removeAttr('disabled');
	}
	
	function renderActions(item, type) {
		return `<span> <a href="javascript:void(0)" onclick="deleteSkills(`+ item.id +`,'`+ type +`',`+ item.skillId +`)">
		<span data-toggle="tooltip" title="Delete Skill" data-placement="top"><i class="fa fa-trash-o my_submit_btn"></i></span>
			</a>
			<a href="javascript:void(0)" class="hide update" onclick="updateSkills(`+ item.id +`,'`+ type +`',`+ item.rating +`,`+ item.experience +`)">
			<span data-toggle="tooltip" title="Save Skill" data-placement="top"><i class="fa fa-check my_submit_btn"></i></span>
			</a>
			<a href="javascript:void(0)" class="edit" onclick="editSkills(this, '`+ type +`')">
			<span data-toggle="tooltip" title="Update Skill" data-placement="top"><i class="fa fa-pencil my_submit_btn"></i></span>
			</a>
		</span>`
	}


	function showAllSkill(type) {
		if (type === 'primary') {
			$('.primary_skill-list tr').removeClass('hide');
		} else {
			$('.secondary_skill-list tr').removeClass('hide');
		}
		
	}
	
	function onUploadResume(ev) {	
		var file = ev.files[0];
		if (file && file.name) {
			$('#uploadResumeFileName').val(file.name);
		}
	}
	function onUploadImage(ev) {	
		var file = ev.files[0];
		if (file && file.name) {
			$('#uploadImageFileName').val(file.name);
		}
	}
</script>