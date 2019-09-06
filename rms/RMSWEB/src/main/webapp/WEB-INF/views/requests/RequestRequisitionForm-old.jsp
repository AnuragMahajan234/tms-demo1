<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<spring:url value="/resources/styles/popupstyle.css?ver=${app_js_ver}"
	var="popupstyle_css" />
<link href="${popupstyle_css}" rel="stylesheet" type="text/css"></link>

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
	value="../resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}"
	var="multiselect_filter_js" />
<%-- <script src="${multiselect_filter_js}" type="text/javascript"></script> --%>
<spring:url value="../resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<%-- <link href="${style_css}" rel="stylesheet" type="text/css"></link> --%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />
<link href="/rms/resources/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="/rms/resources/css/styles.css">
 

<style>
.simulateBtn {
	top: 1px;
}

div#rmsForm1, div#rmsForm2, div#rmsForm3 {
	position: relative;
}

span.ui-icon.ui-icon-triangle-2-n-s {
	float: right;
	margin-top: 6px;
}

.combo-multiselet {
	position: relative;
}

.combo-multiselet .ui-multiselect-menu {
	position: absolute;
	top: 34px !important;
	z-index: 1;
	min-width: 100% !important;
}

.ui-multiselect-menu.ui-widget.ui-widget-content.ui-corner-all {
	width: 100% !important;
	display: none;
}

.ui-widget-header ul.ui-helper-reset, .ui-widget-header ul.ui-helper-reset>li,
	.ui-widget-header ul.ui-helper-reset>li  a {
	display: flex;
}

input[type=search] {
	color: #222 !important;
}

.ui-multiselect-menu * {
	font-weight: normal !important;
}

ul.ui-multiselect-checkboxes {
	padding: 5px 15px;
}

.ui-widget-header {
	padding: 5px 15px;
}

.ui-widget-header ul.ui-helper-reset>li {
	padding-right: 20px;
}

ul.ui-helper-reset {
	margin-top: 5px;
}

input#fname {
	margin-left: 10px;
}

span.ui-icon {
	margin-top: 2px;
	margin-right: 2px;
}

.ui-multiselect-menu a {
	color: #fff;
	text-decoration: none;
}

ul.ui-multiselect-checkboxes {
	max-height: 140px;
	overflow: auto;
}

.customFileInput .input-file .input-group-addon:last-child {
	border: 0px;
}

.customFileInput  span.glyphicon.glyphicon-paperclip {
	position: relative;
	z-index: 2;
}

.alertWrapper {
	position: fixed;
	top: 0px;
	left: 0px;
	z-index: 9999;
	text-align: center;
	width: 100%;
	flex-direction: column;
	height: 100%;
	align-items: center;
	padding-top: 180px;
}

span.required {
	padding-left: 3px;
	color: #f00;
}

.alertWrapper {
	display: none;
}

.circle {
	background-color: rgba(0, 0, 0, 0);
	border: 5px solid rgba(0, 183, 229, 0.9);
	opacity: .9;
	border-right: 5px solid rgba(0, 0, 0, 0);
	border-left: 5px solid rgba(0, 0, 0, 0);
	border-radius: 50px;
	box-shadow: 0 0 35px #2187e7;
	width: 50px;
	height: 50px;
	margin: 0 auto;
	-moz-animation: spinPulse 1s infinite ease-in-out;
	-webkit-animation: spinPulse 1s infinite linear;
}

.modal {
	z-index: 9999 !important;
}

.circle1 {
	background-color: rgba(0, 0, 0, 0);
	border: 5px solid rgba(0, 183, 229, 0.9);
	opacity: .9;
	border-left: 5px solid rgba(0, 0, 0, 0);
	border-right: 5px solid rgba(0, 0, 0, 0);
	border-radius: 50px;
	box-shadow: 0 0 15px #2187e7;
	width: 30px;
	height: 30px;
	margin: 0 auto;
	position: relative;
	top: -50px;
	-moz-animation: spinoffPulse 1s infinite linear;
	-webkit-animation: spinoffPulse 1s infinite linear;
}

@
-moz-keyframes spinPulse { 0% {
	-moz-transform: rotate(160deg);
	opacity: 0;
	box-shadow: 0 0 1px #2187e7;
}

50%
{
-moz-transform
:
 
rotate
(145deg);

		
opacity
:
 
1;
}
100%
{
-moz-transform
:
 
rotate
(-320deg);

		
opacity
:
 
0;
}
}
@
-moz-keyframes spinoffPulse { 0% {
	-moz-transform: rotate(0deg);
}

100%
{
-moz-transform
:
 
rotate
(360deg);

	
}
}
@
-webkit-keyframes spinPulse { 0% {
	-webkit-transform: rotate(160deg);
	opacity: 0;
	box-shadow: 0 0 1px #2187e7;
}

50%
{
-webkit-transform
:
 
rotate
(145deg);

		
opacity
:
 
1;
}
100%
{
-webkit-transform
:
 
rotate
(-320deg);

		
opacity
:
 
0;
}
}
@
-webkit-keyframes spinoffPulse { 0% {
	-webkit-transform: rotate(0deg);
}

100%
{
-webkit-transform
:
 
rotate
(360deg);

	
}
}
.skill1select_wrap ul.ui-multiselect-checkboxes {
	max-height: 108px !important;
	height: 108px !important;
}

.skill1select_wrap .ui-widget-header>ul.ui-helper-reset>li:first-child,
	.skill1select_wrap ul.ui-helper-reset>li:last-child {
	display: none;
}

.skill1select_wrap .ui-widget-header>ul.ui-helper-reset>li:nth-child(2)
	{
	padding-right: 0px !important;
}

.skill1select_wrap .ui-widget-header>ul.ui-helper-reset {
	display: inline-block;
}

.skill1select_wrap .ui-widget-header {
	padding-left: 8px;
	padding-right: 8px;
}

.skill1select_wrap .ui-multiselect-filter {
	display: inline !important;
}

.validateMe.warning .ui-combobox-input, .validateMe.warning input[type="text"],
	.validateMe.warning input[type="number"], .validateMe.warning .input-group-addon,
	.validateMe.warning button, .validateMe.warning textarea {
	border: 1px solid #d87777 !important;
	background: rgb(251, 238, 238) !important;
}

.my_btn {
	background: #badcff !important;
	color: #3276b7 !important;
	font-size: 12px !important;
	box-shadow: none !important;
	border-radius: 2px !important;
	border: 0 !important;
	min-width: 80px !important;
	font-weight: bold;
	text-shadow: none !important;
	transition: all 0.5s;
}

.my_btn:hover {
	background: #3276b7 !important;
	color: #fff !important;
}

.validateMe.warning>label:after {
	content: "Required*";
	color: #f00;
	float: right;
	font-size: 11px;
	font-style: italic;
}

label.input-group-addon.btn-choose {
	background: #dee2e6;
}

.positionRel {
	position: relative;
}

.alertWrapper.success .alert-success {
	background-color: #228633 !important;
	color: #fff;
	border-color: #59a082;
	width: 400px;
	margin: 0px auto;
	text-transform: capitalize;
	box-shadow: 0px 4px 8px #b7b7b7;
	position: relative;
	padding-left: 55px;
	text-align: center;
}

.alert.alert-success:before {
	content: "\e013";
	display: inline-block;
	font-family: 'Glyphicons Halflings';
	font-style: normal;
	font-weight: 400;
	line-height: 1;
	-webkit-font-smoothing: antialiased;
	position: absolute;
	left: 12px;
	top: 8px;
	font-size: 17px;
	color: #cff1d5;
	background: #166523;
	padding: 8px;
	border-radius: 50%;
	border: 1px solid #12521d;
}

.alertWrapper.danger .alert-danger {
	background-color: #9c2c1e !important;
	color: #fff;
	border-color: #8a3226;
	width: 400px;
	margin: 0px auto;
	text-transform: capitalize;
	box-shadow: 0px 4px 8px #b7b7b7;
	position: relative;
	padding-left: 55px;
	text-align: center;
}

.alertlistWrapper {
	position: relative;
	display: table;
	margin: 0px auto;
}

.alert.alert-danger:before {
	content: "!";
	display: inline-block;
	font-family: 'Glyphicons Halflings';
	font-style: normal;
	font-weight: bold;
	line-height: 1;
	-webkit-font-smoothing: antialiased;
	position: absolute;
	left: 9px;
	top: 50%;
	font-size: 26px;
	color: #e2897e;
	background: #6b1c12;
	padding: 0px 12px 5px;
	border-radius: 50%;
	border: 1px solid #6f1e14;
	margin-top: -17px;
}

span.closemeAlert {
	position: absolute;
	top: -5px;
	right: -5px;
	width: 25px;
	height: 25px;
	background: #e2897e;
	border-radius: 50%;
	line-height: 25px;
	color: #350f0a;
	z-index: 1;
	cursor: pointer;
}

.alert.alert-danger p {
	text-align: left;
	border-bottom: 1px dashed #ea7b6d;
	padding-bottom: 5px;
	margin-bottom: 10px;
}

.alert.alert-danger p:last-child {
	border-bottom: 0px;
	margin-bottom: 0px;
}
/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	padding-top: 100px; /* Location of the box */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
	background-color: #fefefe;
	margin: auto;
	padding: 20px;
	border: 1px solid #888;
	width: 525px;
	max-width: 100%;
	padding-top: 35px;
}

/* The Close Button */
span.close {
	position: absolute;
	top: -1px;
	right: -1px;
	width: 30px;
	height: 30px;
	text-align: center;
	background: #222;
	opacity: 1;
	color: #fff;
	line-height: 30px;
	border-radius: 0px 0px 0px 5px;
}

.modal-content table>tbody>tr:first-child td {
	border-top: 0px;
}

.modal-content table>tbody>tr:last-child td {
	border-bottom: 1px solid #ddd;
}

.close:hover, .close:focus {
	opacity: 1;
	color: #fff;
	border-bottom: 2px solid #ccc;
	border-left: 2px solid #ccc;
}

.text-decoration-ul {
	text-decoration: underline;
}

.nav-hover-none>li>a:hover, .nav-hover-none>li>a:active, .nav-hover-none>li>a:focus
	{
	background: none !important;
}

.mar-row {
	margin: 20px 0px 0px 0px;
}
/* .validateMe.warning span:after {
    content: "Required*";
    color: #f00;
    float: right;
    font-size: 11px;
    font-style: italic;
} */
.required-resource {
	display: none;
	color: #f00;
	font-size: 11px;
	font-style: italic;
}

.validateMe.warning .required-resource {
	display: block;
}

.display-edit {
	display: none;
}
}
</style>
</head>


<!-- <html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="requestrequisitionForm/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="requestrequisitionForm/css/styles.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head> -->
<script>
	function showSuccessAlert(message) {
		$('.alertWrapper>.alert-success').text(message);
		$('.alertWrapper.success').show().delay(5000).fadeOut('slow');
	}

	function showErrorAlert(message) {
		$('.alertWrapper .alert-danger').append('<p>' + message + '</p>');
		$('.alertWrapper.danger').show();
		//.delay(3000).empty().fadeOut("slow");
	}

	function dateCheck(data) {

		var requestDate = new Date($('#requestedDate1').val());
		var resourceRequiredDate = new Date($('#resourceRequiredDate').val());
		if (requestDate > resourceRequiredDate) {
			showErrorAlert("Resource Required date cannot be less than resource requested Date!");
			$('#resourceRequiredDate').val('');
		}
	}

	$(document).on('click', '.closemeAlert', function() {
		$('.alert-danger').empty();
		$('.alertWrapper').fadeOut("slow")
	});

	function showSuccess(msg) {
		toastr.success(msg, "Success")
	}

	function showMessage(msg) {
		toastr.options.timeOut = 12000;
		toastr.error(msg, " ")
	}

	function showError(msg) {
		toastr.options.timeOut = 12000;
		toastr.error(msg, "Error")
	}

	function showWarning(msg) {
		toastr.warning(msg, "Warning")
	}

	$(document)
			.ready(
					function() {

						$('#reasonForReplacement').val('');
						$('#resourceForReplacement').val('');
						/*$('#requestorSelect option[value=0]').attr('selected','selected');
						$('#requestorSelect option[value!=0]').removeAttr('selected');
						var s_sel=$('#clientInterviewRequiredSelect option[value="N"]').text();
						$('#clientInterviewRequiredSelect').closest('.positionRel').find('.ui-combobox-input').val(s_sel); */

						var daysToAdd = 1;
						if ($('#buttonFlagId').val() == false) {
							$("#requestedDate1").datepicker().datepicker(
									"setDate", new Date());
							$("#requestedDate1").datepicker("option",
									"minDate", new Date());
							$("#requestedDate1").datepicker("option",
									"maxDate", new Date())
						}

						/* $("#requestedDate1").datepicker({
							
						onSelect: function (selected) {
						var dtMax = new Date(selected);
						dtMax.setDate(dtMax.getDate() + daysToAdd); 
						var dd = dtMax.getDate();
						var mm = dtMax.getMonth() + 1;
						var y = dtMax.getFullYear();
						var dtFormatted = mm + '/'+ dd + '/'+ y;
						$("#resourceRequiredDate").datepicker("option", "minDate", dtFormatted);
						}
						}); */

						/* $("#resourceRequiredDate").datepicker({
						onSelect: function (selected) {
						var dtMax = new Date(selected);
						dtMax.setDate(dtMax.getDate() - daysToAdd); 
						var dd = dtMax.getDate();
						var mm = dtMax.getMonth() + 1;
						var y = dtMax.getFullYear();
						var dtFormatted = mm + '/'+ dd + '/'+ y;
						$("#requestedDate1").datepicker("option", "maxDate", dtFormatted)
						}
						}); */

						// $('[data-toggle="popover"]').popover();   
						// $('#requestedDate').val(new Date()) jab wo bole for change
						var validation = 0;
						var requirements = {
							parameters : []
						};

						var requestorsBGBU = '';
						selectedRequestorID = $('#requestorSelect').val();
						$(document)
								.on(
										'change',
										'#requestorSelect',
										function() {
											selectedRequestorID = $(
													'#requestorSelect').val(); //requestor emp ID
											//requirements.parameters.push({"requestorID" : selectedRequestorID});
											if (selectedRequestorID == "select") {
												showErrorAlert("Please select a Requestor!");
											} else {
												startProgress();
												$
														.ajax({
															type : 'GET',
															dataType : 'text',
															url : '/rms/requests/getEmployeeDetailsById/'
																	+ selectedRequestorID,
															contentType : "application/json; charset=utf-8",
															cache : false,
															success : function(
																	data) {
																stopProgress();
																var parsedData = JSON
																		.parse(data);
																$(
																		'#requestorGradeSelect')
																		.val(
																				parsedData.grade);
																$(
																		'#requestorDesignationSelect')
																		.val(
																				parsedData.designationName);

															},
															error : function(
																	error) {
																stopProgress();
																showError("Something happend wrong!!");
																//window.location.reload();
															}
														});

											}

										});

						customerGroupName = "";

							});
					 if (selMulti == "") {
						$('#notifyToIds_val').html('');
					}
					 $("#notifyToIds_val").val(selMulti.join(", "));
					addResourceInternal = $(this).val();  
				});
       
       $('#pdlMailIds').multiselect({
			includeSelectAllOption : true,
			id : 'pdlMailIds'
		}).multiselectfilter();
      
       var pdlMailToIds = [];
       pdlMailToIds = $('#pdlMailIds').val();
       $('#pdlMailIds_val').html($('#pdlMailIds option:selected').text());
       $('#pdlMailIds').bind(
				'change',
				function() {
					
					var selectedText;

					/*sarang added start*/
					var selMulti = $.map($(this).find('option:selected'),
							function(el, i) {
								pdlMailToIds = $('#pdlMailIds').val();
																
								/* var e = document.getElementById("pdlMailIds");
								pdlMailToIds.push(e.options[e.selectedIndex].value); */
								
								 
								selectedText = $(el).text();
								 
								return $(el).text();

							});
					if (selMulti == "") {
						$('#pdlMailIds_val').html($('#pdlMailIds option:selected').text());
					}
					 $("#pdlMailIds_val").val(selMulti.join(", "));
					addResourceInternal = $(this).val();  
				});
		
     
      
      /*$('#pdlMailIds').multiselect({
			includeSelectAllOption : true,
			id : 'pdlMailIds'
		}).multiselectfilter();
		*/
		
		$('#reasonButton').on('click', function(){
			var modal = document.getElementById('myModal');
		resourceReplacement = $('#resourceForReplacement').val();	
		reason = $('#reasonForReplacement').val();
		
		if(resourceReplacement!=null > 0 && reason != null ){
			
			requirements.parameters.push({"resourceReplacement" : resourceReplacement});
			requirements.parameters.push({"reason" : reason});
			modal.style.display = "none";
			$('#resourceForReplacement').val('');
			$('#reasonForReplacement').val('');
		}else{
			//showErrorAlert("Please select both of the fields!")
			 if(validateReasonBtnForm()){
				  
			 }else{
				 modal.style.display = "none";
				 
		}
		}
			
		});
		
		function validateReasonBtnForm(){
			var items = $('#reasonForm .validateMe').find(".ui-combobox-input,input[type='text']");
			 
			var al=0;
			for (var i = 0; i < items.length; i++)
			            {
			              var b= $.trim(items[i].value);
			               
			             if(b=="Select"|| b==""){
			            	 
			            	$(items[i]).closest('.form-group').addClass('warning');	 
			            	al=al+1;
			             }else{
			            	 
			            		$(items[i]).closest('.form-group').removeClass('warning');
			            }
			      }	
			return al;
    }
     
    function validateForm1(){
			var items = $('#rmsForm1 .validateMe').find(".ui-combobox-input,input[type='text']");
			var al=0;
			for (var i = 0; i < items.length; i++)
			            {
			              var b= $.trim(items[i].value);
			             if(b=="Select"|| b==""){
			            	 
			            	$(items[i]).closest('.form-group').addClass('warning');	 
			            	al=al+1;
			             }else{
			            		$(items[i]).closest('.form-group').removeClass('warning');
			            }
			             
			      }	
			return al;
   	
    }
		

																$
																		.ajax({
																			type : 'GET',
																			dataType : 'text',
																			url : '/rms/requests/getProjects/'
																					+ clientID,
																			contentType : "application/json; charset=utf-8",
																			cache : false,
																			success : function(
																					data) {
																				stopProgress();
																				var optionForProjects = "";
																				$(
																						'#projectNameSelect')
																						.empty();
																				response = JSON
																						.parse(data);
																				for (i = 0; i < response.length; i++) {
																					var index = response[i];
																					optionForProjects = "<option value= '" + index[2] + "'>"
																							+ index[10]
																							+ "</option>"
																					$(
																							"#projectNameSelect")
																							.append(
																									optionForProjects);
																				}

																			},
																			error : function(
																					error) {
																				stopProgress();
																				showError("Something happend wrong!!");
																				//window.location.reload();
																			}
																		});

															},
															error : function(
																	error) {
																stopProgress();
																showError("Something happend wrong!!");
																//window.location.reload();
															}
														});
											}

										});

						$('#clientGroupSelect')
								.on(
										'change',
										function() {
											customerGroupName = $(
													'#clientGroupSelect option:selected')
													.text()

										})

						$('#projectNameSelect')
								.on(
										'change',
										function() {

											projectName = $(
													'#projectNameSelect option:selected')
													.text();
											projectID = $('#projectNameSelect')
													.val()
											$
													.ajax({
														type : 'GET',
														dataType : 'text',
														url : '/rms/requests/getBUHByProjectId/'
																+ projectID,
														contentType : "application/json; charset=utf-8",
														cache : false,
														success : function(data) {
															stopProgress();
															var parsedData = JSON
																	.parse(data);
															console
																	.log(parsedData)
															if (parsedData.firstName != null) {
																dataForBUH = parsedData.firstName
																		+ " "
																		+ parsedData.lastName;
																$(
																		'#BUHNameSelect')
																		.val(
																				dataForBUH);
																requirements.parameters
																		.push({
																			"BUHName" : parsedData.employeeId
																		});
															}

															$
																	.ajax({
																		type : 'GET',
																		dataType : 'text',
																		url : '/rms/requests/getBGHByProjectId/'
																				+ projectID,
																		contentType : "application/json; charset=utf-8",
																		cache : false,
																		success : function(
																				data) {
																			stopProgress();
																			var parsedData = JSON
																					.parse(data);
																			console
																					.log(parsedData)
																			if (parsedData.firstName != null) {
																				dataForBUH = parsedData.firstName
																						+ " "
																						+ parsedData.lastName;
																				$(
																						'#BGHNameSelect')
																						.val(
																								dataForBUH);
																				requirements.parameters
																						.push({
																							"BGHName" : parsedData.employeeId
																						});
																			}

	$('#projectShiftTypeSelect').on('change', function(){
		
		if($('#projectShiftTypeSelect option:selected').text() != 'General'){ // mandatory only when shift type is not general
			$("#projectShiftDetails").closest('.form-group').addClass('validateMe');
		}else{
			$("#projectShiftDetails").closest('.form-group').removeClass('validateMe');
		}
	
	});
	$('.display-edit').on('click',function(){
		var modal = document.getElementById('myModal');
		modal.style.display = "block";
		$('#reasonForm .form-group').removeClass('warning');
	})
	
	$('#resourceTypeSelect').on('change', function(){
		var modal = document.getElementById('myModal');
		var span = document.getElementsByClassName("close")[0];
		var resourceReplacementVal = $('#resourceForReplacement').val();	
		var reasonVal = $('#reasonForReplacement').val();
		 
		if($('#resourceTypeSelect option:selected').text() == 'Replacement'){
			modal.style.display = "block";
			$('#reasonForm .form-group').removeClass('warning');
			$('.display-edit').show();
		}
		else
		{
			$('.display-edit').hide();
		}
		span.onclick = function() {
				if(resourceReplacementVal==null && reasonVal ==null){
					$('#resourceTypeSelect').closest('.form-group').find('.ui-combobox-input').val('');
				}
		    modal.style.display = "none";
		}
		
		window.onclick = function(event) {
		    if (event.target == modal) {
		    	if(resourceReplacementVal==null && reasonVal ==null){
					$('#resourceTypeSelect').closest('.form-group').find('.ui-combobox-input').val('');
				}
		        modal.style.display = "none";
		    }
		}
		
	});
	
	$('#previewBtnClicked').on('click', function(){
		var modal = document.getElementById('myPreviewModal');
		var span = document.getElementById("close-icon");
		var close = document.getElementById("close-btn");
		modal.style.display = "block";
		
		
	
		$('#previewHiringBGBU').text($('#hiringbgBuSelect option:selected').val());
		$('#previewRequestorsBGBU').text($('#bgBuSelect option:selected').val());
		$('#previewRequestDate').text($('#requestedDate1').val());
		$('#previewDeliveryPOC').text($('#deliverPOCSelect option:selected').text());
		$('#previewRequestorsName').text($('#requestorSelect option:selected').text());
		$('#previewRequirementType').text($('#resourceTypeSelect option:selected').text());
		$('#previewRequiredFor').text($('#requiredForSelect option:selected').val());
		$('#previewRequiredDate').text($('#resourceRequiredDate').val());
		$('#previewClientType').text($('#clientTypeSelect option:selected').val());
		if($('#buttonFlagId').val()){ //check for edit RRF.
			$('#previewClientName').text($('#clientNameSelectOnEdit').val());
			$('#previewProjectName').text($('#projectNameSelectOnEdit').val());
			$('#previewCompetency').text($('#res_skills_onEdit').val());
			$('.hold').css('display' , 'block')
			$('.lost').css('display' , 'block')
			$('#previewHold').text($('#resOnHold').val());
			$('#previewLost').text($('#resLost').val());
			
		}else{
			$('#previewClientName').text($('#clientNameSelect option:selected').text());
			$('#previewProjectName').text($('#projectNameSelect option:selected').text());
			$('#previewCompetency').text($('#res_skills option:selected').text());
		}
		//Following fix include clientGroup name while user edit rrf and click preview.
		if($('#buttonFlagId').val()){ //check for edit RRF.

			if($('#clientGroupSelectOnEdit').val() != ""){
				$('#previewClientGroup').text($('#clientGroupSelectOnEdit').val());
			}else{
				$('.previewGrp').css('display', 'none');
			}
			//Added new field AM Job Code
			if($('#amJobCode').val() != ""){
				$('#previewAmJobCode').text($('#amJobCode').val());
			}else{
				$('#previewAmJobCode').text("-");
			}
		}else {
			if($('#clientGroupSelect option:selected').text() != ""){
				$('#previewClientGroup').text($('#clientGroupSelect option:selected').text());
			}else{
				$('#previewClientGroup').text("-");
			}
			//Added new field AM Job Code
			if($('#amJobCode').val() != ""){
				$('#previewAmJobCode').text($('#amJobCode').val());
			}else{
				$('#previewAmJobCode').text("-");
			}
		}
		
		/* if($('#clientGroupSelectOnEdit').val() != ""){
			$('#previewClientGroup').text($('#clientGroupSelectOnEdit').val());
		}else{
			$('.previewGrp').css('display', 'none');
		}
		if($('#clientGroupSelect option:selected').text() != ""){
			$('#previewClientGroup').text($('#clientGroupSelect option:selected').text());
		}else{
			$('#previewClientGroup').text("-");
		}
 */		
		
		$('#previewProjectType').text($('#projectTypeSelect option:selected').val());
		$('#previewPositions').text($('#numberOfPosition').val());
		$('#previewDesignation').text($('#designationSelect option:selected').text());
		
		if($('#experienceRequiredSelect option:selected').val() == 'Others'){
			$('#previewExpRequired').text($('#expOtherDetails').val());
		}else{
			$('#previewExpRequired').text($('#experienceRequiredSelect option:selected').val());
		}
		
		$('#previewAllocationType').text($('#allocationTypeSelect option:selected').text());
		$('#previewLocation').text($('#locationTypeSelect option:selected').text());
		$('#previewProjectDuration').text($('#projectDuration').val());
		$('#previewShiftType').text($('#projectShiftTypeSelect option:selected').text());
		
		if($('#projectShiftDetails').val() != ""){
			$('#previewShiftDetails').text($('#projectShiftDetails').val());
		}
		
		$('#previewClientInterview').text($('#clientInterviewRequiredSelect option:selected').text());
		$('#previewBGVRequired').text($('#bgvRequiredSelect option:selected').text());
		
		if($('#keyInterviewer1').val() != ""){
			$('#previewRound1').text($('#keyInterviewer1').val());
		}
	
		if($('#keyInterviewer2').val() != ""){
			$('#previewRound2').text($('#keyInterviewer2').val());
		}
		
		
		$('#previewSkillsToEval').text($('#skillSetRequired').val());
		if($('#primarySkills').val().trim() != ""){
			$('#previewPrimarySkills').text($('#primarySkills').val().trim());
		}
		
		if($('#additionalSkills').val() != ""){
			$('#previewAddSkills').text($('#additionalSkills').val());
		}
		
		if($('#rolesAndResponsibilities').val() != ""){
			$('#previewRoles').text($('#rolesAndResponsibilities').val());
		}
		
		if($('#additionalComment').val() != ""){
			$('#previewAdditionalComments').text($('#additionalComment').val());
		}
		
		if($('#sendMailTo_val').val() != ""){
			$('#previewMailTo').text($('#sendMailTo_val').val());
		}
		
		if($('#notifyToIds_val').val() != ""){
			$('#previewNotifyTo').text($('#notifyToIds_val').val());
		}
		
		if($('#pdlMailIds_val').val() != ""){
			$('#previewPDl').text($('#pdlMailIds_val').val());
		}
		
		/* $('#previewPriority').text($('#prioritySelect option:selected').text()); */
		
		span.onclick = function() {
		    modal.style.display = "none";
		}
		close.onclick = function() {
		    modal.style.display = "none";
		}
		
		window.onclick = function(event) {
		    if (event.target == modal) {
		        modal.style.display = "none";
		    }
		}
		
	});
	
	
	
	
	
});

											if ($('#projectShiftDetails').val() != "") {
												$('#previewShiftDetails')
														.text(
																$(
																		'#projectShiftDetails')
																		.val());
											}

											$('#previewClientInterview')
													.text(
															$(
																	'#clientInterviewRequiredSelect option:selected')
																	.text());
											$('#previewBGVRequired')
													.text(
															$(
																	'#bgvRequiredSelect option:selected')
																	.text());

											if ($('#keyInterviewer1').val() != "") {
												$('#previewRound1').text(
														$('#keyInterviewer1')
																.val());
											}

											if ($('#keyInterviewer2').val() != "") {
												$('#previewRound2').text(
														$('#keyInterviewer2')
																.val());
											}

											$('#previewSkillsToEval').text(
													$('#skillSetRequired')
															.val());
											if ($('#primarySkills').val()
													.trim() != "") {
												$('#previewPrimarySkills')
														.text(
																$(
																		'#primarySkills')
																		.val()
																		.trim());
											}

											if ($('#additionalSkills').val() != "") {
												$('#previewAddSkills').text(
														$('#additionalSkills')
																.val());
											}

											if ($('#rolesAndResponsibilities')
													.val() != "") {
												$('#previewRoles')
														.text(
																$(
																		'#rolesAndResponsibilities')
																		.val());
											}

											if ($('#additionalComment').val() != "") {
												$('#previewAdditionalComments')
														.text(
																$(
																		'#additionalComment')
																		.val());
											}

											if ($('#sendMailTo_val').val() != "") {
												$('#previewMailTo').text(
														$('#sendMailTo_val')
																.val());
											}

											if ($('#notifyToIds_val').val() != "") {
												$('#previewNotifyTo').text(
														$('#notifyToIds_val')
																.val());
											}

											if ($('#pdlMailIds_val').val() != "") {
												$('#previewPDl').text(
														$('#pdlMailIds_val')
																.val());
											}

											/* $('#previewPriority').text($('#prioritySelect option:selected').text()); */

											span.onclick = function() {
												modal.style.display = "none";
											}
											close.onclick = function() {
												modal.style.display = "none";
											}

											window.onclick = function(event) {
												if (event.target == modal) {
													modal.style.display = "none";
												}
											}

										});

					});
</script>

<body>
	<div class="alertWrapper success">
		<div class="alert alert-success" role="alert"></div>
	</div>
	<div class="alertWrapper danger">
		\
		<div class="alertlistWrapper">
			<span class="closemeAlert">X</span>
			<div class="alert alert-danger" role="alert"></div>
		</div>
	</div>

	<div class="content-wrapper">
		<h4 class="pageHeading">Resource Requisition Form</h4>
		<div class="container-fluid outerCard">			
			<ul class="nav nav-pills tab-border resource-request-nav nav-hover-none">
				<li class="nav_step1 active">
					<a href="javascript:void(0);">
						<span class="stepContent">Delivery Information</span>
						<span class="numberCircle">Step 1</span>						
					</a>
				</li>
				<li class="nav_step2">
					<a href="javascript:void(0);">
						<span class="stepContent">Customer &amp; Project Information</span>
						<span class="numberCircle">Step 2</span>
					</a>
				</li>
				<li class="nav_step3">
					<a href="javascript:void(0);">
						<span class="stepContent">Hiring/Job Description</span>
						<span class="numberCircle">Step 3</span>
					</a>
				</li>
				<li class="nav_step4">
					<a href="javascript:void(0);">
						<span class="stepContent">Notify &amp; Submit</span>
						<span class="numberCircle">Step 4</span>
					</a>
				</li>
			</ul>

			<div class="tab-content">
				<div id="rmsForm1" class="tab-pane fade in active">
					<div class="process-steps">
						<span><strong>1</strong> out of 4</span>
					</div>
					<form>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe autoSelect">
									<label for="hiringbgBuSelect"><span class="required">*</span><span
										data-toggle="tooltip" data-placement="top" title=""
										data-original-title="We limit this to BG4-BU5">Hiring
											BG/BU</span><span class="text-inside-brackets">(Please don't
											change)</span></label>
									<div class="positionRel">
										<select class="form-control required comboselect check"
											id="hiringbgBuSelect">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.requestRequisition.hiringBGBU}"
														class="selected-option">${dataList.requestRequisition.hiringBGBU}</option>
													<c:forEach var="bu" items="${bus}">
														<option value="${bu.parentId.name}-${bu.name}">${bu.parentId.name}-${bu.name}</option>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<option value="BG4-BU5" class="selected-option">BG4-BU5</option>
													<c:forEach var="bu" items="${bus}">
														<c:if
															test="${bu.parentId.name != 'BG4' && bu.name != 'BU5'}">
															<option value="${bu.parentId.name}-${bu.name}">${bu.parentId.name}-${bu.name}</option>
														</c:if>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="inputPassword4"><span class="required">*</span>Request
										Date</label>
									<div class="positionRel">
										<div class='input-group'>
											<c:choose>
												<c:when test="${buttonFlag}">
													<input type='text' disabled="disabled"
														value="${dataList.requestRequisition.date}"
														id="requestedDate1" autocomplete="off"
														class="form-control" />
												</c:when>
												<c:otherwise>
													<input type='text' id="requestedDate1" class="form-control" />
												</c:otherwise>
											</c:choose>
											<label class="input-group-addon" for="requestedDate1">
												<span class="glyphicon glyphicon-calendar"></span>
											</label>
										</div>

									</div>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="bgBuSelect"><span class="required">*</span>Requestor's
										BG/BU</label>
									<div class="positionRel">
										<select class="form-control required comboselect check"
											id="bgBuSelect">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.requestRequisition.projectBU}">${dataList.requestRequisition.projectBU}</option>
													<c:forEach var="bu" items="${bus}">
														<option value="${bu.parentId.name}-${bu.name}">${bu.parentId.name}-${bu.name}</option>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="bu" items="${bus}">
														<option value="${bu.parentId.name}-${bu.name}">${bu.parentId.name}-${bu.name}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe autoSelect">
									<label for="requestorSelect"><span class="required">*</span>Requestor's
										Name</label>
									<div class="positionRel">
										<select class="form-control required comboselect check"
											id="requestorSelect">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option
														value="${dataList.requestRequisition.requestor.employeeId}"
														class="selected-option">${dataList.requestRequisition.requestor.firstName}
														${dataList.requestRequisition.requestor.lastName} -
														${dataList.requestRequisition.requestor.yashEmpId}</option>
													<c:forEach var="user" items="${ActiveUserList}">
														<c:if
															test="${dataList.requestRequisition.requestor.employeeId != user.employeeId }">
															<option value="${user.employeeId}">${user.firstName}
																${user.lastName} - ${user.yashEmpId}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<option value="${currentLoggedinResource.employeeId}"
														class="selected-option">${currentLoggedinResource.firstName}
														${currentLoggedinResource.lastName} -
														${currentLoggedinResource.yashEmpId}</option>
													<c:forEach var="user" items="${ActiveUserList}">
														<c:if
															test="${currentLoggedinResource.employeeId != user.employeeId}">
															<option value="${user.employeeId}">${user.firstName}
																${user.lastName} - ${user.yashEmpId}</option>
														</c:if>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="form-group col-md-4 autoSelect">
									<label for="requestorGradeSelect"><span
										class="required">*</span>Requestor Grade</label>
									<c:choose>
										<c:when test="${buttonFlag}">
											<input type="text" value="${requestorDetails.grade}"
												disabled="disabled" class="form-control"
												id="requestorGradeSelect">
										</c:when>
										<c:otherwise>
											<input type="text" disabled="disabled"
												value="${currentLoggedinResource.gradeId.grade}"
												class="form-control" id="requestorGradeSelect">
										</c:otherwise>
									</c:choose>
								</div>
								<div class="form-group col-md-4 autoSelect">
									<label for="requestorDesignationSelect"><span
										class="required">*</span>Requestor Designation</label>
									<c:choose>
										<c:when test="${buttonFlag}">
											<input type="text"
												value="${requestorDetails.designationName}"
												disabled="disabled" class="form-control"
												id="requestorDesignationSelect">
										</c:when>
										<c:otherwise>
											<input type="text" disabled="disabled"
												value="${currentLoggedinResource.designationId.designationName}"
												class="form-control" id="requestorDesignationSelect">
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe">
									<label for="deliverPOCSelect"><span class="required">*</span><span
										data-toggle="tooltip" data-placement="left" title=""
										data-original-title="POC for RMG Team (will be notified through email)">Delivery
											POC</span></label>
									<div class="positionRel">
										<select class="form-control required comboselect check"
											id="deliverPOCSelect">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option
														value="${dataList.requestRequisition.deliveryPOC.employeeId}">${dataList.requestRequisition.deliveryPOC.firstName}
														${dataList.requestRequisition.deliveryPOC.lastName} -
														${dataList.requestRequisition.deliveryPOC.yashEmpId}</option>
													<c:forEach var="user" items="${ActiveUserList}">
														<c:if
															test="${user.employeeId != dataList.requestRequisition.deliveryPOC.employeeId}">
															<option value="${user.employeeId}">${user.firstName}
																${user.lastName} - ${user.yashEmpId}</option>
														</c:if>

													</c:forEach>
												</c:when>
												<c:otherwise>

													<c:forEach var="user" items="${ActiveUserList}">
														<option value="${user.employeeId}">${user.firstName}
															${user.lastName} - ${user.yashEmpId}</option>
													</c:forEach>

												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
							</div>
						</div>


						<div class="">
							<button type="button" id="nextFirst"
								class="btn btn-primary next-button changeToProjectInfo">Next</button>
						</div>
						<div>
							<input type="text" id="requirementName"
								value="${requirementName}" style="display: none;" />
						</div>
						<div>
							<input type="text" id="buttonFlagId" value="${buttonFlag}"
								style="display: none;" />
						</div>
						<div>
							<input type="text" id="requisitionDbId"
								value="${requestRequisitionSkillId}" style="display: none;" />
						</div>

					</form>
				</div>
				<div id="rmsForm2" class="tab-pane fade">
					<div class="process-steps">
						<span><strong>2</strong> out of 4</span>
					</div>
					<form>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe">
									<label for="resourceTypeSelect"><span class="required">*</span>Requirement
										Type <c:if test="${buttonFlag}">
											<a style="cursor: pointer;"><span
												class="glyphicon glyphicon-edit display-edit"></span></a>
										</c:if> </label> <select class="form-control required comboselect check"
										id="resourceTypeSelect">
										<c:choose>
											<c:when test="${buttonFlag}">
												<c:if test="${dataList.type == 1 }">
													<option selected="selected" value="1">New
														Requirement</option>
													<option value="0">Replacement</option>
													<option value="2">Heads-Up</option>
													<option value="3">Pool</option>
												</c:if>
												<c:if test="${dataList.type == 0 }">
													<option selected="selected" value="0">Replacement</option>
													<option value="1">New Requirement</option>
													<option value="2">Heads-Up</option>
													<option value="3">Pool</option>
												</c:if>
												<c:if test="${dataList.type == 2 }">
													<option selected="selected" value="2">Heads-Up</option>
													<option value="1">New Requirement</option>
													<option value="0">Replacement</option>
													<option value="3">Pool</option>
												</c:if>
												<c:if test="${dataList.type == 3 }">
													<option selected="selected" value="3">Pool</option>
													<option value="1">New Requirement</option>
													<option value="0">Replacement</option>
													<option value="2">Heads-Up</option>
												</c:if>
											</c:when>
											<c:otherwise>

												<option value="1">New Requirement</option>
												<option value="0">Replacement</option>
												<option value="2">Heads-Up</option>
												<option value="3">Pool</option>

											</c:otherwise>
										</c:choose>

									</select>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="requiredForSelect"><span class="required">*</span><span
										data-toggle="tooltip" data-placement="left" title=""
										data-original-title="For Staffing, approvals are not mandatory">Required for</span></label>
									<div class="positionRel">
										<select class="form-control required comboselect check"
											id="requiredForSelect">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option selected="selected"
														value="${dataList.requestRequisition.requiredFor}">${dataList.requestRequisition.requiredFor}</option>
													<c:forEach var="requiredFor" items="${resourceRequiredFor}">
														<c:if
															test="${dataList.requestRequisition.requiredFor != requiredFor.type}">
															<option value="${requiredFor.type}">${requiredFor.type}
															</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="requiredFor" items="${resourceRequiredFor}">
														<option value="${requiredFor.type}">${requiredFor.type}
														</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label><span class="required">*</span><span
										data-toggle="tooltip" title="" data-placement="left"
										data-original-title="Should be greater than today's date">Required Date</span></label>
									<div class="positionRel">
										<div class='input-group'>
											<c:choose>
												<c:when test="${buttonFlag}">
													<input type="text"
														value="${dataList.requestRequisition.resourceRequiredDate}"
														class="form-control" id="resourceRequiredDate">
												</c:when>
												<c:otherwise>
													<!-- <input type="text" onchange="dateCheck(this)" id="resourceRequiredDate" class="form-control"> -->
													<input type="text" id="resourceRequiredDate"
														class="form-control">
												</c:otherwise>
											</c:choose>

											<label class="input-group-addon" for="resourceRequiredDate">
												<span class="glyphicon glyphicon-calendar"></span>
											</label>
										</div>
									</div>
								</div>
							</div>
						</div>						
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe">
									<label for="clientTypeSelect"><span class="required">*</span>Client
										Type</label>

									<div class="positionRel">
										<select class="form-control required comboselect check"
											id="clientTypeSelect">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.requestRequisition.clientType}">${dataList.requestRequisition.clientType}</option>
													<c:forEach var="clientType" items="${clientTypeList}">
														<c:if
															test="${dataList.requestRequisition.clientType != clientType.type}">
															<option value="${clientType.type}">${clientType.type}
															</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>

													<c:forEach var="clientType" items="${clientTypeList}">

														<option value="${clientType.type}">${clientType.type}
														</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="clientNameSelect"><span class="required">*</span><span
										data-toggle="tooltip" data-placement="left" title=""
										data-original-title="In case of no Client Group, it will be hidden">Client
											Name</span></label>
									<div class="positionRel">
										<c:choose>
											<c:when test="${buttonFlag}">
												<input type="text" id="clientNameSelect" hidden="true"
													disabled="disabled"
													value="${dataList.requestRequisition.customer.id}" />
												<input type="text" id="clientNameSelectOnEdit"
													class="form-control" disabled="disabled"
													value="${dataList.requestRequisition.customer.customerName}" />
											</c:when>
											<c:otherwise>
												<select class="form-control required comboselect check"
													id="clientNameSelect">

													<c:forEach var="client" items="${allCustomers}">
														<option value="${client.id}">${client.customerName}</option>
													</c:forEach>
												</select>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="form-group col-md-4" id="grpdiv">
									<label for="clientGroupSelect"><span class="required">*</span>Client
										Group<span class="text-inside-brackets">(If Applicable)</span></label>
									<div class="positionRel">
										<c:choose>
											<c:when test="${buttonFlag}">
												<input type="text" id="clientGroupSelect" hidden="true"
													disabled="disabled"
													value="${dataList.requestRequisition.group.groupId}" />
												<input type="text" id="clientGroupSelectOnEdit"
													class="form-control" disabled="disabled"
													value="${dataList.requestRequisition.group.customerGroupName}" />
											</c:when>
											<c:otherwise>
												<select class="form-control  required comboselect check"
													id="clientGroupSelect">
												</select>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe">
									<label for="numberOfPosition"><span class="required">*</span>Number
										of Position(s)</label>
									<c:choose>
										<c:when test="${buttonFlag}">
											<input type="number" min="1"
												value="${dataList.noOfResources}" disabled="disabled"
												class="form-control" id="numberOfPosition">
										</c:when>
										<c:otherwise>
											<input type="number" min="1" class="form-control"
												id="numberOfPosition">
										</c:otherwise>
									</c:choose>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="allocationTypeSelect"><span
										class="required">*</span>Allocation Type</label>
									<div class="positionRel">
										<select class="form-control  required comboselect check"
											id="allocationTypeSelect">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.allocationType.id}">${dataList.allocationType.aliasAllocationName}</option>
													<c:forEach var="allocation" items="${resourceAllocation}">
														<c:if
															test="${dataList.allocationType.id != allocation.id}">
															<option value="${allocation.id}">${allocation.aliasAllocationName}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>

													<c:forEach var="allocation" items="${resourceAllocation}">

														<option value="${allocation.id}"
															data-obj="${allocation.bghMandatoryFlag}">${allocation.aliasAllocationName}</option>

													</c:forEach>



												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="experienceRequiredSelect"><span
										class="required">*</span><span data-toggle="tooltip"
										data-placement="left" title=""
										data-original-title="You can select others, in case required experience not available">Experience
											Required</span></label>
									<div class="positionRel">
										<select class="form-control  required comboselect check"
											id="experienceRequiredSelect">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.experience}" selected="selected">${dataList.experience}</option>
													<c:forEach var="experience" items="${experienceList}">
														<c:if
															test="${dataList.experience != experience.experienceRange}">
															<option value="${experience.experienceRange}">${experience.experienceRange}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>

													<c:forEach var="experience" items="${experienceList}">
														<option value="${experience.experienceRange}">${experience.experienceRange}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe">
									<label for="designationSelect"><span class="required">*</span><span
										data-toggle="tooltip" data-placement="left" title=""
										data-original-title="Designation of the resource(s) required">Designation</span></label>
									<div class="positionRel">
										<select class="form-control  required comboselect check"
											id="designationSelect">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.designation.id}">${dataList.designation.designationName}</option>
													<c:forEach var="designationObject" items="${designation}">
														<c:if
															test="${dataList.designation.id != designationObject.id}">
															<option value="${designationObject.id}">${designationObject.designationName}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>

													<c:forEach var="designationObject" items="${designation}">
														<option value="${designationObject.id}">${designationObject.designationName}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>

										</select>
									</div>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="projectTypeSelect"><span class="required">*</span>Project
										Type</label>
									<div class="positionRel">
										<select class="form-control required comboselect check"
											id="projectTypeSelect">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.requestRequisition.projectType}">${dataList.requestRequisition.projectType}</option>
													<c:forEach var="projectType" items="${projectTypeList}">
														<c:if
															test="${dataList.requestRequisition.projectType != projectType.type}">
															<option value="${projectType.type}">${projectType.type}
															</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>

													<c:forEach var="projType" items="${projectTypeList}">
														<option value="${projType.type}">${projType.type}
														</option>
													</c:forEach>

												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="projectNameSelect"><span class="required">*</span>Project
										Name</label>
									<div class="positionRel">
										<c:choose>
											<c:when test="${buttonFlag}">
												<input type="text" id="projectNameSelect" hidden="true"
													disabled="disabled"
													value="${dataList.requestRequisition.project.id}" />
												<input type="text" id="projectNameSelectOnEdit"
													class="form-control" disabled="disabled"
													value="${dataList.requestRequisition.project.projectName}" />
											</c:when>
											<c:otherwise>
												<select class="form-control  required comboselect check"
													id="projectNameSelect">
												</select>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe">
									<label for="projectDuration"><span class="required">*</span>Project
										Duration<span class="text-inside-brackets">(in months)</span></label>
									<c:choose>
										<c:when test="${buttonFlag}">
											<input type="number"
												value="${dataList.requestRequisition.projectDuration}"
												min="1" class="form-control" id="projectDuration">
										</c:when>
										<c:otherwise>
											<input type="number" min="1" class="form-control"
												id="projectDuration">
										</c:otherwise>
									</c:choose>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="projectShiftTypeSelect"><span
										class="required">*</span>Project Shift Type</label>
									<div class="positionRel">
										<select class="form-control required comboselect check"
											id="projectShiftTypeSelect">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.requestRequisition.shiftType.id}">${dataList.requestRequisition.shiftType.shiftTimings}</option>
													<c:forEach var="shiftType" items="${shiftTypeList}">
														<c:if
															test="${dataList.requestRequisition.shiftType.id != shiftType.id }">
															<option value="${shiftType.id}">${shiftType.shiftTimings}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>

													<c:forEach var="shiftType" items="${shiftTypeList}">
														<option value="${shiftType.id}">${shiftType.shiftTimings}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>

										</select>
									</div>
								</div>
								<div class="form-group col-md-12">
									<label for="projectShiftDetails"><span
										data-toggle="tooltip" title="" data-placement="left"
										data-original-title="Not Mandatory for General shift">Project
											Shift Details</span></label><span class="text-inside-brackets">(Based on Project Shift Type)</span>
									<c:choose>
										<c:when test="${buttonFlag}">
											<!-- <input type="text" class="form-control" value="${dataList.requestRequisition.projectShiftOtherDetails}" id="projectShiftDetails">-->
											<textarea class="form-control" id="projectShiftDetails">${dataList.requestRequisition.projectShiftOtherDetails}</textarea>
										</c:when>
										<c:otherwise>
											<textarea class="form-control" id="projectShiftDetails"></textarea>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe">
									<label for="res_skills"><span class="required">*</span><span
										data-toggle="tooltip" data-placement="left" title=""
										data-original-title="Major Skill for which you are creating a request">Competency/Skill
											Family</span></label>
									<div class="positionRel">
										<c:choose>
											<c:when test="${buttonFlag}">
												<input type="text" hidden="true" id="res_skills"
													value="${dataList.skill.id}" />
												<input type="text" id="res_skills_onEdit"
													class="form-control" disabled="disabled"
													value="${dataList.skill.skill}">
											</c:when>
											<c:otherwise>
												<select class="form-control  required comboselect check"
													id="res_skills">

													<c:forEach var="skill" items="${secondrySkills}">
														<c:if test="${skill.skill != 'Not Avbl'}">
															<option value="${skill.id}">${skill.skill}</option>
														</c:if>
													</c:forEach>
												</select>
											</c:otherwise>
										</c:choose>
									</div>
								</div>								
								<div class="form-group col-md-4 validateMe">
									<label for="locationTypeSelect"><span class="required">*</span>Job
										Location</label>
									<div class="positionRel">
										<select class="form-control required comboselect check"
											id="locationTypeSelect">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.location.id}">${dataList.location.location}</option>
													<c:forEach var="location" items="${locations}">
														<c:if test="${dataList.location.id != location.id}">
															<option value="${location.id}">${location.location}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>

													<c:forEach var="location" items="${locations}">
														<option value="${location.id}">${location.location}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>

										</select>
									</div>
								</div>
								<div class="form-group col-md-4 customFileInput">
									<label for="buhApprovalSelect"><span class="required">*</span><span
										data-toggle="tooltip" data-placement="left" title=""
										data-original-title="Mandatory for ODC">BUH Approval</span><span
										class="text-inside-brackets">(Attachment only)</span></label>


									<div class="positionRel">
										<div class="input-group input-file">
											<c:choose>
												<c:when test="${buttonFlag}">
													<!-- 	<input type="file" class="form-control" id="buhApprovalSelect" placeholder='Choose a file...' /> -->
													<c:if
														test="${dataList.requestRequisition.BUHApprovalFileName != '' }">
														<input type="text" class="inputfile" id="buhFileName"
															value="${dataList.requestRequisition.BUHApprovalFileName}"
															readonly />
														<!-- <input type="text" class="form-control"  id="buhApprovalSelect" value="${dataList.requestRequisition.BUHApprovalFileName}"  style="display: none;"> -->
														<input type="file" class="form-control"
															id="buhApprovalSelect" placeholder='Choose a file...'
															style="display: none;" />
														<label class="input-group-addon btn-choose"
															for="buhApprovalSelect"> <span
															class="glyphicon glyphicon-paperclip"></span>
														</label>
														<!-- <input type="text" class="form-control"  id="buhApprovalSelect" value="buhFileUploadedAlready"  style="display: none;"> 
                                                <span class="glyphicon glyphicon-paperclip"></span>-->
													</c:if>
													<c:if
														test="${dataList.requestRequisition.BUHApprovalFileName == '' }">
														<input type="text" class="form-control" value=""
															style="display: none;">
														<!-- <input type="file" class="form-control" id="buhApprovalSelect" placeholder='Choose a file...' /> -->
														<span class="glyphicon glyphicon-paperclip"></span>
													</c:if>
												</c:when>
												<c:otherwise>
													<input type="text" id="buhFileName" class="inputfile"
														readonly />
													<input type="file" class="form-control"
														id="buhApprovalSelect" placeholder='Choose a file...'
														style="display: none;" />
													<label class="input-group-addon btn-choose"
														for="buhApprovalSelect"> <span
														class="glyphicon glyphicon-paperclip"></span> </label>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 customFileInput">
									<label for="bghApprovalSelect"><span
										data-toggle="tooltip" title="" data-placement="left"
										data-original-title="Mandatory for contingent and Investment">BGH
											Approval</span> <span class="text-inside-brackets">(<span
											class="required">*</span> for Non-Billable)
									</span></label>
									<div class="positionRel">
										<div class="input-group input-file">
											<c:choose>
												<c:when test="${buttonFlag}">

													<c:if
														test="${dataList.requestRequisition.BGHApprovalFileName != '' }">
														<input type="text" class="inputfile" id="bghFileName"
															value="${dataList.requestRequisition.BGHApprovalFileName}"
															readonly />
														<input type="file" class="form-control"
															id="bghApprovalSelect" placeholder='Choose a file...'
															style="display: none" />
														<label class="input-group-addon btn-choose"
															for="bghApprovalSelect"> <span
															class="glyphicon glyphicon-paperclip"></span>
														</label>
													</c:if>
													<c:if
														test="${dataList.requestRequisition.BUHApprovalFileName == '' }">
														<input type="text" class="form-control" value=""
															style="display: none;">
														<!-- <input type="file" class="form-control" id="bghApprovalSelect" placeholder='Choose a file...' /> -->
														<span class="glyphicon glyphicon-paperclip"></span>
													</c:if>
												</c:when>
												<c:otherwise>
													<input type="text" class="inputfile" id="bghFileName"
														readonly />
													<input type="file" class="form-control"
														id="bghApprovalSelect" placeholder='Choose a file...'
														style="display: none" />
													<label class="input-group-addon btn-choose"
														for="bghApprovalSelect"> <span
														class="glyphicon glyphicon-paperclip"></span>
													</label>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
									<!-- <input type="file" disabled="disabled" class="form-control" id="bghApprovalSelect">-->
								</div>
								<!-- AM Job Code Start -->
								<div class="form-group col-md-4 validateMe">
									<label for="amJobCode">AM Job Code<span
										class="text-inside-brackets">(<span class="required">*</span>
											for Staffing)
									</span></label>
									<div class="positionRel">
										<c:choose>
											<c:when test="${buttonFlag}">
												<input type="text" class="form-control"
													value="${dataList.requestRequisition.amJobCode}"
													id="amJobCode" disabled="disabled">
											</c:when>
											<c:otherwise>
												<input type="text" class="form-control" value=""
													id="amJobCode" disabled="disabled">
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<!-- AM Job Code End -->
							</div>
						</div>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4" id="otherDetailsTextBox"
									style="display: none">
									<label for="expOtherDetails">If other, please specify</label>
									<c:choose>
										<c:when test="${buttonFlag}">

											<!--  <c:if test="${dataList.experience == 'Others'}"> -->

											<input type="text" class="form-control" id="expOtherDetails"
												value="${dataList.requestRequisition.expOtherDetails}" />
											<!-- </c:if> -->

											<c:if test="${dataList.experience != 'Others'}">
												<input type="text" class="form-control" id="expOtherDetails" />
											</c:if>

										</c:when>

										<c:otherwise>
											<input type="text" class="form-control" id="expOtherDetails" />
										</c:otherwise>
									</c:choose>
								</div>
								<div class="form-group col-md-3 autoSelect"
									style="display: none;">
									<label for="BUHNameSelect"><span class="required">*</span>BUH
										Name</label>
									<div class="positionRel">
										<c:choose>
											<c:when test="${buttonFlag}">
												<input type="text" id="BUHNameSelect" hidden="true"
													disabled="disabled"
													value="${dataList.requestRequisition.customer.id}" />
												<input type="text" class="form-control" disabled="disabled"
													value="${dataList.requestRequisition.customer.customerName}" />
											</c:when>
											<c:otherwise>
												<input class="form-control" disabled="disabled"
													id="BUHNameSelect">
												<%--  <option value="${BUHead.employeeId}">${BUHead.firstName} ${BUHead.lastName} - ${BUHead.yashEmpId} </option>
	                                   <c:forEach var="user" items="${ActiveUserList}">
	                                  
		                                	<c:if  test="${BUHead.employeeId != user.employeeId}">
		                                		<option value="${user.employeeId}">${user.firstName} ${user.lastName} - ${user.yashEmpId} </option>
		                                	</c:if>
										</c:forEach> --%>

											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="form-group col-md-3 validateMe"
									style="display: none;">
									<label for="BGHNameSelect"><span class="required">*</span>BGH
										Name</label>
									<div class="positionRel">
										<c:choose>
											<c:when test="${buttonFlag}">
												<input type="text" id="clientNameSelect" hidden="true"
													disabled="disabled"
													value="${dataList.requestRequisition.customer.id}" />
												<input type="text" class="form-control" disabled="disabled"
													value="${dataList.requestRequisition.customer.customerName}" />
											</c:when>
											<c:otherwise>
												<input class="form-control" disabled="disabled"
													id="BGHNameSelect">
											</c:otherwise>
										</c:choose>
									</div>
                                <!-- <input type="file" disabled="disabled" class="form-control" id="bghApprovalSelect">-->
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-row">
                         <div class="form-group col-md-4 validateMe">
                                <label for="allocationTypeSelect"><span class="required">*</span>Allocation Type</label>
                                 <div class="positionRel">
                                <select class="form-control  required comboselect check"  id="allocationTypeSelect">
                                 <c:choose>
                                 	<c:when test="${buttonFlag}">
	                                 	<option value="${dataList.allocationType.id}">${dataList.allocationType.aliasAllocationName}</option>
	                                 	<c:forEach var="allocation" items="${resourceAllocation}">
	                                 		<c:if test="${dataList.allocationType.id != allocation.id}">
	                                 		<option value="${allocation.id}">${allocation.aliasAllocationName}</option>
	                                 		</c:if>
										</c:forEach>
                                 	</c:when>
                                 	<c:otherwise>
		                                
		                                  <c:forEach var="allocation" items="${resourceAllocation}">
		                               
											<option value="${allocation.id}" data-obj="${allocation.bghMandatoryFlag}">${allocation.aliasAllocationName}</option>
											
										  </c:forEach>
		                                
		                                
		                                  
                                 </c:otherwise>
                                 </c:choose>
                                </select>
                                </div>
                            </div>
                            <div class="form-group col-md-4 validateMe">
                                <label for="locationTypeSelect"><span class="required">*</span>Job Location</label>
                                 <div class="positionRel">
	                                <select class="form-control required comboselect check" id="locationTypeSelect">
	                                <c:choose>
	                                 	<c:when test="${buttonFlag}">
	                                 		<option value="${dataList.location.id}">${dataList.location.location}</option>
	                                 		<c:forEach var="location" items="${locations}">
	                                 			<c:if test="${dataList.location.id != location.id}">
	                                 			<option value="${location.id}">${location.location}</option>
	                                 			</c:if>
										    </c:forEach>
	                                 	</c:when>
	                                 	<c:otherwise>
		                                 	 
		                                    <c:forEach var="location" items="${locations}">
													<option value="${location.id}">${location.location}</option>
											</c:forEach>
	                                 	</c:otherwise>
                                 	</c:choose>
	                                  
	                                </select>
	                                </div>
                            </div>
                            <div class="form-group col-md-4 validateMe">
                                <label for="projectDuration"><span class="required">*</span>Project Duration<span class="text-inside-brackets">(in months)</span></label>
                                  <c:choose>
	                                 	<c:when test="${buttonFlag}">
	                                 		 <input type="number" value="${dataList.requestRequisition.projectDuration}" min="1" class="form-control" id="projectDuration">
	                                 	</c:when>
										<c:otherwise>
											 <input type="number" min="1" class="form-control" id="projectDuration">
										</c:otherwise>	                                 
                               </c:choose>
                            </div>
                            
                            
                          
                            
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-row">                           
                            <div class="form-group col-md-4 validateMe"> 
                                <label for="projectShiftTypeSelect"><span class="required">*</span>Project Shift Type</label>
                                <div class="positionRel">
                                <select class="form-control required comboselect check" id="projectShiftTypeSelect">
                                <c:choose>
	                                 	<c:when test="${buttonFlag}">
		                                 	<option value="${dataList.requestRequisition.shiftType.id}">${dataList.requestRequisition.shiftType.shiftTimings}</option>
		                                 	 <c:forEach var="shiftType" items="${shiftTypeList}">
			                                 	 <c:if test="${dataList.requestRequisition.shiftType.id != shiftType.id }">
			                                 	 	<option value="${shiftType.id}">${shiftType.shiftTimings}</option>
			                                 	 </c:if>
											</c:forEach>
	                                 	</c:when>
	                                 	<c:otherwise>
	                                 	  
	                                     <c:forEach var="shiftType" items="${shiftTypeList}">
												<option value="${shiftType.id}">${shiftType.shiftTimings}</option>
										</c:forEach>
	                                 	</c:otherwise>
	                             </c:choose>
                                 
                                </select>
                                </div>
                            </div>
                            <div class="form-group col-md-8 ">
                                <label for="projectShiftDetails"><span data-toggle="tooltip" title="" data-placement="left" data-original-title="Not Mandatory for General shift">Project Shift Details</span></label><span class="text-inside-brackets">(Based on Project Shift Type)</span>
                                <c:choose>
	                                 	<c:when test="${buttonFlag}">
                               			<!-- <input type="text" class="form-control" value="${dataList.requestRequisition.projectShiftOtherDetails}" id="projectShiftDetails">-->
                               			<textarea class="form-control" id="projectShiftDetails">${dataList.requestRequisition.projectShiftOtherDetails}</textarea>
                                		</c:when>
                              		  <c:otherwise>
                                 		 <textarea class="form-control" id="projectShiftDetails"></textarea>
                                	</c:otherwise>
                                </c:choose>
                            </div>
                            
                            
                        </div>
                    </div>
                    <center>
                        <button type="button" id="prevToFirst" class="btn btn-secondary next-button changeToHiringInfo">Back</button>
                        <button type="button" id="nextSecond" class="btn btn-primary next-button changeToHiringInfo">Next</button>
                    </center>

                </form>
            </div>
            <div id="rmsForm3" class="tab-pane fade">
            <div class="process-steps"><span><strong>3</strong> out of 4</span></div>
                <form>
                    <div class="row">
                        <div class="form-row">
                            <div class="form-group col-xs-6 col-md-4 validateMe">
                                <label for="clientInterviewRequiredSelect"><span class="required">*</span><span data-toggle="tooltip" data-placement="left" title="" data-original-title="Default Yes for Staffing">Client Interview Required</span></label>
                                 <div class="positionRel">
                                <select class="form-control required comboselect check" id="clientInterviewRequiredSelect">
                                 <c:choose>
		                              <c:when test="${buttonFlag}">
	                                		<c:choose>
			                                		<c:when test="${dataList.requestRequisition.isClientInterviewRequired == 'Y' }">
			                                			<option selected="selected" value="${dataList.requestRequisition.isClientInterviewRequired}">Yes</option>
			                                			<option value="N">No</option>
			                                		</c:when>
			                                	
			                                		<c:when test="${dataList.requestRequisition.isClientInterviewRequired == 'N' }">
	                                					<option selected="selected" value="${dataList.requestRequisition.isClientInterviewRequired}">No</option>
	                                					<option value="Y">Yes</option>
	                                				</c:when>
	                                				
	                                				<c:otherwise>
	                                					 
	                                    					<option value="Y">Yes</option>
	                                    					<option value="N">No</option>
	                                				</c:otherwise>
	                                		</c:choose>
	                                  </c:when>
	                                  
	                                  <c:otherwise>
	                                     
	                                    <option value="Y">Yes</option>
	                                    <option value="N">No</option>
	                                 </c:otherwise>
                                 </c:choose>
                                </select>
                                </div>
                            </div>
                            <div class="form-group col-xs-6 col-md-4 validateMe autoSelect">
                                <label for="bgvRequiredSelect"><span class="required">*</span><span data-toggle="tooltip"   data-placement="left" title="" data-original-title="Default No">Client BGV Required</span></label>
                                 <div class="positionRel">
                                <select class="form-control required comboselect check" id="bgvRequiredSelect">
                                
                                  <c:choose>
		                              <c:when test="${buttonFlag}">
	                                	<c:choose>
	                                		<c:when test="${dataList.requestRequisition.isBGVrequired == 'Y' }">
		                                		<option class="selected-option" value="${dataList.requestRequisition.isBGVrequired}">Yes</option>
		                                		<option value="N">No</option>
	                                		</c:when>
	                                		<c:when test="${dataList.requestRequisition.isBGVrequired == 'N' }">
		                                		<option class="selected-option"value="${dataList.requestRequisition.isBGVrequired}">No</option>
		                                		<option value="Y">Yes</option>
	                                		</c:when>
	                                		 <c:otherwise>
	                                   		 	 
			                                    <option value="Y" >Yes</option>
			                                    <option value="N">No</option>
			                                 </c:otherwise>
	                                	</c:choose>	
	                                  </c:when>
		                                  <c:otherwise>
		                                     
		                                    <option value="Y" >Yes</option>
		                                    <option value="N" selected="selected" class="selected-option">No</option>
		                                 </c:otherwise>
                                 </c:choose>
                                </select>
                                </div>
                            </div>
                          </div>
                          </div>
                          <div class="row">
                          <div class="form-row" style="display: none;">  
                            <div class="form-group col-xs-6 col-md-2">
                                <label for="salaryRangeSelect">Curreny Type</label>
                                 <div class="form-group">
                                 <select class="form-control" id="currencyType">
                                    <option value="dollor">Doller ($)</option>
                                    <option value="pound">Pound (£)</option>
                                </select>
                                </div>
                            </div>
                            <div class="form-group col-xs-6 col-md-3">
                            <fieldset disabled> 
                                <label for="salaryRangeSelect">Salary Range<span class="text-inside-brackets">(per annum)</span></label>
                                 <div class="form-group">
                                 <select class="form-control" id="salaryRangeSelect">
                                    
                                </select>
                                </div>
                              </fieldset>  
                            </div>
                            <div class="form-group  col-xs-6 col-md-3">
                                <label for="billRate">Bill Rate/hr</label>
                                <input type="text" class="form-control" id="billRate">

										<div class="form-group col-md-4 validateMe">
											<label for="resLost">Lost</label> <input type="number"
												min="0" value="${dataList.lost}" class="form-control"
												id="resLost">
										</div>
									</c:when>
									<c:otherwise>
										<div class="form-group col-md-4 validateMe"
											style="display: none;">
											<label for="resOnHold">On Hold</label> <input type="number"
												min="0" class="form-control" value="0" id="resOnHold">
										</div>
										<div class="form-group col-md-4" style="display: none;">
											<label for="resLost">Lost</label> <input type="number"
												min="0" class="form-control" value="0" id="resLost">
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div>
							<button type="button" id="prevToFirst"
								class="btn btn-secondary next-button changeToHiringInfo">Back</button>
							<button type="button" id="nextSecond"
								class="btn btn-primary next-button changeToHiringInfo">Next</button>
						</div>
					</form>
				</div>
				<div id="rmsForm3" class="tab-pane fade">
					<div class="process-steps">
						<span><strong>3</strong> out of 4</span>
					</div>
					<form>
						<div class="row">
							<div class="col-md-12">
								<h5 class="form-heading">Hiring Information</h5>
							</div>
						</div>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-xs-6 col-md-4 validateMe">
									<label for="clientInterviewRequiredSelect"><span
										class="required">*</span><span data-toggle="tooltip"
										data-placement="left" title=""
										data-original-title="Default Yes for Staffing">Client
											Interview Required</span></label>
									<div class="positionRel">
										<select class="form-control required comboselect check"
											id="clientInterviewRequiredSelect">
											<c:choose>
												<c:when test="${buttonFlag}">
													<c:choose>
														<c:when
															test="${dataList.requestRequisition.isClientInterviewRequired == 'Y' }">
															<option selected="selected"
																value="${dataList.requestRequisition.isClientInterviewRequired}">Yes</option>
															<option value="N">No</option>
														</c:when>

														<c:when
															test="${dataList.requestRequisition.isClientInterviewRequired == 'N' }">
															<option selected="selected"
																value="${dataList.requestRequisition.isClientInterviewRequired}">No</option>
															<option value="Y">Yes</option>
														</c:when>

														<c:otherwise>

															<option value="Y">Yes</option>
															<option value="N">No</option>
														</c:otherwise>
													</c:choose>
												</c:when>

												<c:otherwise>

													<option value="Y">Yes</option>
													<option value="N">No</option>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="form-group col-xs-6 col-md-4 validateMe autoSelect">
									<label for="bgvRequiredSelect"><span class="required">*</span><span
										data-toggle="tooltip" data-placement="left" title=""
										data-original-title="Default No">Client BGV Required</span></label>
									<div class="positionRel">
										<select class="form-control required comboselect check"
											id="bgvRequiredSelect">

											<c:choose>
												<c:when test="${buttonFlag}">
													<c:choose>
														<c:when
															test="${dataList.requestRequisition.isBGVrequired == 'Y' }">
															<option class="selected-option"
																value="${dataList.requestRequisition.isBGVrequired}">Yes</option>
															<option value="N">No</option>
														</c:when>
														<c:when
															test="${dataList.requestRequisition.isBGVrequired == 'N' }">
															<option class="selected-option"
																value="${dataList.requestRequisition.isBGVrequired}">No</option>
															<option value="Y">Yes</option>
														</c:when>
														<c:otherwise>

															<option value="Y">Yes</option>
															<option value="N">No</option>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>

													<option value="Y">Yes</option>
													<option value="N" selected="selected"
														class="selected-option">No</option>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
							</div>
						</div>
						<hr class="divideHr" />
						<div class="row">
							<div class="form-row" style="display: none;">
								<div class="form-group col-xs-6 col-md-2">
									<label for="salaryRangeSelect">Curreny Type</label>
									<div class="form-group">
										<select class="form-control" id="currencyType">
											<option value="dollor">Doller ($)</option>
											<option value="pound">Pound (£)</option>
										</select>
									</div>
								</div>
								<div class="form-group col-xs-6 col-md-3">
									<fieldset disabled>
										<label for="salaryRangeSelect">Salary Range<span
											class="text-inside-brackets">(per annum)</span></label>
										<div class="form-group">
											<select class="form-control" id="salaryRangeSelect">

											</select>
										</div>
									</fieldset>
								</div>
								<div class="form-group  col-xs-6 col-md-3">
									<label for="billRate">Bill Rate/hr</label> <input type="text"
										class="form-control" id="billRate">

								</div>

							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<h5 class="form-heading">
									<span class="required">*</span>Interview Panels <span
										class="text-inside-brackets">(preferrably E3 and above)</span>
								</h5>
							</div>
						</div>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-12">
									<div class="col-md-4">
										<label for="round1Select"><span data-toggle="tooltip"
											title="" data-placement="left"
											data-original-title="Select Interviwer(s) for round 1, mandatory for ODC">Round
												1 </span> <span class="text-inside-brackets">(Multiselect)</span></label>
										<div class="positionRel combo-multiselet">
											<select class="form-control required" id="round1Select"
												name="reqID" multiple="multiple">	
												<c:choose>
													<c:when test="${buttonFlag}">
														<c:forEach var="mail" items="${ActiveUserList}">
															<c:choose>
																<c:when
																	test="${fn:contains(dataList.keyInterviewersOne,mail.employeeId)}">
																	<c:set var="mailIds"
																		value="${fn:split(dataList.keyInterviewersOne, ',')}" />
																	<c:forEach var="empid" items="${mailIds}">
																		<fmt:parseNumber var="eid" type="number"
																			value="${fn:trim(empid)}" />
																		<c:if test="${eid==mail.employeeId}">
																			<option selected="selected" value="${mail.employeeId}">${mail.firstName}
																				${mail.lastName}</option>
																		</c:if>
																	</c:forEach>
																</c:when>
																<c:otherwise>
																	<option value="${mail.employeeId}">${mail.firstName}
																		${mail.lastName}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:forEach var="user" items="${ActiveUserList}">
															<option value="${user.employeeId}">${user.firstName}
																${user.lastName}</option>
														</c:forEach>
													</c:otherwise>
												</c:choose>
											</select>
										</div>
									</div>
									<div class="col-md-8">
										<label for="keyInterviewer1"><span>&nbsp;</span></label>
										<textarea id="keyInterviewer1" class="col-md-12" disabled></textarea>
									</div>
								</div>
								<div class="form-group col-md-12">
									<div class="col-md-4">
										<label for="round2Select"><span data-toggle="tooltip"
											title="" data-placement="left"
											data-original-title="Select Interviwer(s) for round 2, mandatory for ODC">Round
												2 </span><span class="text-inside-brackets">(Multiselect)</span></label>
										<div class="positionRel combo-multiselet">
											<select class="form-control required" id="round2Select"
												name="reqID" multiple="multiple">
	
												<c:choose>
													<c:when test="${buttonFlag}">
														<c:forEach var="mail" items="${ActiveUserList}">
															<c:choose>
																<c:when
																	test="${fn:contains(dataList.keyInterviewersTwo,mail.employeeId)}">
																	<c:set var="mailIds"
																		value="${fn:split(dataList.keyInterviewersTwo, ',')}" />
																	<c:forEach var="empid" items="${mailIds}">
																		<fmt:parseNumber var="eid" type="number"
																			value="${fn:trim(empid)}" />
																		<c:if test="${eid==mail.employeeId}">
																			<option selected="selected" value="${mail.employeeId}">${mail.firstName}
																				${mail.lastName}</option>
																		</c:if>
																	</c:forEach>
																</c:when>
																<c:otherwise>
																	<option value="${mail.employeeId}">${mail.firstName}
																		${mail.lastName}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:forEach var="user" items="${ActiveUserList}">
															<option value="${user.employeeId}">${user.firstName}
																${user.lastName}</option>
														</c:forEach>
													</c:otherwise>
												</c:choose>
											</select>
										</div>
									</div>
									<div class="col-md-8">
										<label for="keyInterviewer2"><span>&nbsp;</span></label>
										<textarea id="keyInterviewer2" class="col-md-12" disabled></textarea>
									</div>
								</div>
							</div>
						</div>
						<hr class="divideHr" />
						<div class="row">
							<div class="col-md-12">
								<h5 class="form-heading">
									List the technical skills to be evaluated by Panels <span
										class="text-inside-brackets">*(SF Mandate)</span>
								</h5>
							</div>
						</div>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe skill1select_wrap">
									<label for="skill1Select"><span class="required">*</span><span
										data-toggle="tooltip" data-placement="left" title=""
										data-original-title="Select list of topics to evaluate resource(s) upon">Key
											Scanners</span></label>

									<!-- <div class="positionRel combo-multiselet"> -->
									<div class="positionRel combo-multiselet">
										<select class="form-control required" id="skill1Select"
											name="reqID" multiple="multiple">
											<c:choose>
												<c:when test="${buttonFlag}">
													<c:forEach var="skill" items="${skillsList}">
														<c:choose>
															<c:when
																test="${fn:contains(dataList.skillsToEvaluate,skill.skill)}">
																<c:set var="skillObjects"
																	value="${fn:split(dataList.skillsToEvaluate, ',')}" />
																<c:forEach var="skillObject" items="${skillObjects}">
																	<%--   <fmt:parseNumber var="eid" type="number" value="${fn:trim(empid)}" /> --%>
																	<c:if test="${skillObject==skill.skill}">
																		<option selected="selected" value="${skill.skill}">${skill.skill}</option>
																	</c:if>
																</c:forEach>
															</c:when>
															<c:otherwise>
																<option value="${skill.skill}">${skill.skill}</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="skill" items="${skillsList}">
														<option value="${skill.skill}">${skill.skill}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>



								</div>
								<div class="form-group col-md-8">
									<label>SkillSet</label>
									<textarea id="skillSetRequired" class="col-md-12" disabled></textarea>
								</div>
							</div>
						</div>
						<hr class="divideHr" />
						<div class="row">
							<div class="col-md-12">
								<h5 class="form-heading">
									<span class="required">*</span>Job Description
								</h5>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-6 validateMe">
								<label for="primarySkills"><span class="required">*</span>Primary
									Skills</label>
								<c:choose>
									<c:when test="${buttonFlag}">
										<textarea class="form-control" id="primarySkills">${dataList.primarySkills}</textarea>
									</c:when>
									<c:otherwise>
										<textarea class="form-control" id="primarySkills"
											placeholder="Please enter details"></textarea>
									</c:otherwise>
								</c:choose>

							</div>

							<div class="form-group col-md-6">
								<label for="additionalSkills">Additional Skills</label>
								<c:choose>
									<c:when test="${buttonFlag}">
										<textarea class="form-control" class="form-control"
											id="additionalSkills">${dataList.desirableSkills}</textarea>
									</c:when>
									<c:otherwise>
										<textarea class="form-control" id="additionalSkills"
											placeholder="Please enter details"></textarea>
									</c:otherwise>
								</c:choose>
							</div>

						</div>
						<div class="row">
							<div class="form-group col-md-6">

								<label for="rolesAndResponsibilities">Roles and
									Responsibilities</label>
								<c:choose>
									<c:when test="${buttonFlag}">
										<textarea class="form-control" id="rolesAndResponsibilities">${dataList.responsibilities}</textarea>
									</c:when>
									<c:otherwise>
										<textarea class="form-control" id="rolesAndResponsibilities"
											placeholder="Please enter details"></textarea>
									</c:otherwise>
								</c:choose>

							</div>

							<div class="form-group col-md-6">

								<label for="additionalComment">Additional Comment</label>
								<c:choose>
									<c:when test="${buttonFlag}">
										<textarea class="form-control" id="additionalComment">${dataList.requestRequisition.comments}</textarea>
									</c:when>
									<c:otherwise>
										<textarea class="form-control" id="additionalComment"
											placeholder="Please enter details"></textarea>
									</c:otherwise>
								</c:choose>

							</div>
						</div>
						<div>
							<button type="button" id="prevToSecond"
								class="btn btn-secondary next-button changeToHiringInfo">Back</button>
							<button type="button" id="nextFourth"
								class="btn btn-primary next-button changeToHiringInfo">Next</button>
						</div>

						<!--<center>
                        <button type="button" id="prevToThird" class="btn btn-secondary next-button changeToHiringInfo">Prev</button>
                        <button type="button" id="nextFifth" class="btn btn-primary next-button changeToHiringInfo">Next</button>
                    </center>-->

					</form>
				</div>
				<div id="rmsForm4" class="tab-pane fade">
					<div class="process-steps">
						<span><strong>4</strong> out of 4</span>
					</div>
					<form>
						<div class="row">
							<div class="form-group col-md-12 validateMe">
								<div class="col-md-4">
									<label for="sendMailTo">Mail to: (TO)</label>
									<div class="positionRel combo-multiselet">
										<select id="sendMailTo" class="required" name="reqID"
											multiple="multiple">
											<c:choose>
												<c:when test="${buttonFlag}">
													<c:forEach var="mail" items="${ActiveUserList}">
														<c:choose>
															<c:when
																test="${fn:contains(dataList.requestRequisition.sentMailTo,mail.employeeId)}">
																<c:set var="mailIds"
																	value="${fn:split(dataList.requestRequisition.sentMailTo, ',')}" />
																<c:forEach var="empid" items="${mailIds}">
																	<fmt:parseNumber var="eid" type="number"
																		value="${fn:trim(empid)}" />
																	<c:if test="${eid==mail.employeeId}">
																		<option selected="selected" value="${mail.employeeId}">${mail.firstName}
																			${mail.lastName}</option>
																	</c:if>
																</c:forEach>
															</c:when>
															<c:otherwise>
																<option value="${mail.employeeId}">${mail.firstName}
																	${mail.lastName}</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="user" items="${ActiveUserList}">
														<option value="${user.employeeId}">${user.firstName}
															${user.lastName}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="col-md-8">
									<label for="sendMailTo_val">&nbsp;</label>
									<textarea id="sendMailTo_val" class="col-md-12" disabled="disabled"></textarea>
								</div>
							</div>
							<div class="form-group col-md-12 validateMe">
								<div class="col-md-4">
									<label for="notifyToIds">Notify to: (CC) </label>
									<div class="positionRel combo-multiselet">
										<select id="notifyToIds" class="required" name="reqID"
											multiple="multiple">
											<c:choose>
												<c:when test="${buttonFlag}">
													<c:forEach var="mail" items="${ActiveUserList}">
														<c:choose>
															<c:when
																test="${fn:contains(dataList.requestRequisition.notifyMailTo,mail.employeeId)}">
																<c:set var="mailIds"
																	value="${fn:split(dataList.requestRequisition.notifyMailTo, ',')}" />
																<c:forEach var="empid" items="${mailIds}">
																	<fmt:parseNumber var="eid" type="number"
																		value="${fn:trim(empid)}" />
																	<c:if test="${eid==mail.employeeId}">
																		<option selected="selected" value="${mail.employeeId}">${mail.firstName}
																			${mail.lastName}</option>
																	</c:if>
																</c:forEach>
															</c:when>
															<c:otherwise>
																<option value="${mail.employeeId}">${mail.firstName}
																	${mail.lastName}</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="mail" items="${mailingList}">
														<option value="${mail.employeeId}">${mail.firstName}
															${mail.lastName}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="col-md-8">
									<label for="sendMailTo_val">&nbsp;</label>
									<textarea id="notifyToIds_val" class="col-md-12" disabled="disabled"></textarea>
								</div>
							</div>
							<div class="form-group col-md-12 validateMe">
								<div class="col-md-4">
									<label for="pdlMailIds">PDL (CC)</label>
									<div class="positionRel combo-multiselet">
										<select id="pdlMailIds" class="required" name="reqID"
											multiple="multiple">
											<c:choose>
												<c:when test="${buttonFlag}">
													<c:forEach var="pdl" items="${pdlList}">
														<c:choose>
															<c:when
																test="${fn:contains(dataList.requestRequisition.pdlEmailIds,pdl.id)}">
																<c:set var="mailIds"
																	value="${fn:split(dataList.requestRequisition.pdlEmailIds, ' ,')}" />
																<c:forEach var="pdlid" items="${mailIds}">
																	<%--  <fmt:parseNumber var="pid" value="${fn:trim(pdlmailid)}" /> --%>
																	<c:if test="${pdlid==pdl.id}">
																		<option selected="selected" value="${pdl.id}">${pdl.pdlEmailId}</option>
																	</c:if>
																</c:forEach>
															</c:when>
															<c:otherwise>
																<option value="${pdl.id}">${pdl.pdlEmailId}</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="pdl" items="${pdlList}">
														<c:choose>
															<c:when test="${pdl.pdlEmailId=='RMG-NonSap@yash.com'}">
																<option selected="selected" value="${pdl.id}">${pdl.pdlEmailId}</option>
															</c:when>
															<c:otherwise>
																<option value="${pdl.id}">${pdl.pdlEmailId}</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach var="pdl" items="${pdlList}">
												<c:choose>
													<c:when test="${pdl.pdlEmailId=='RMG-NonSap@yash.com'}">
														<option selected="selected"  value="${pdl.id}">${pdl.pdlEmailId}>${pdl.pdlEmailId}</option>
													</c:when>
													<c:otherwise>
														<option value="${pdl.id}">${pdl.pdlEmailId}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								
								
								
							</select>
                               </div>
                                <textarea id="pdlMailIds_val" class="col-md-12" disabled="disabled"></textarea>
                        </div>
                    </div>
                      
                    <center>
                        <c:choose>
                        <c:when test="${buttonFlag}">
                        <button type="button" id="prevToThird" class="btn btn-secondary next-button changeToHiringInfo">Back</button>
                        <button type="button" id="previewBtnClicked" class="btn btn-primary next-button">Preview</button>
                        <button type="button" id="submitForm" class="btn btn-primary next-button">Update</button>
                        </c:when>
                        <c:otherwise>
                        <button type="button" id="prevToThird" class="btn btn-secondary next-button changeToHiringInfo">Back</button>
                        <button type="button" id="previewBtnClicked" class="btn btn-primary next-button">Preview</button>
                        <button type="button" id="submitForm" class="btn btn-primary next-button">Submit</button>
                        </c:otherwise>
                        </c:choose>
                          
                        
                    </center>
                    </form>
               </div>
        </div>
    <div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content" id="reasonForm">
    <span class="close">&times;</span>
<table class="table">
<tr><td><div class="positionRel validateMe" >
        <label for="resourceReplacing" ><span class="required">*</span>Please select resource you are replacing :</label>
  	 </div></td><td>
  	 <div class="form-group positionRel validateMe" >
        <select class="form-control required comboselect check" id="resourceForReplacement" >
        <c:choose>
	        <c:when test="${buttonFlag}">
	         	<option value="${dataList.requestRequisition.replacementResource.employeeId}">${dataList.requestRequisition.replacementResource.firstName} ${dataList.requestRequisition.replacementResource.lastName} </option>
		                 <c:forEach var="mail" items="${ActiveUserList}">
			                    <c:if test="${mail.employeeId != dataList.requestRequisition.replacementResource.employeeId}">
			                          <option value="${mail.employeeId}">${mail.firstName} ${mail.lastName}</option>
			                    </c:if>
														
						 </c:forEach>
	     	        </c:when>
	        <c:otherwise>
	        	<c:forEach var="mail" items="${ActiveUserList}">
					<option value="${mail.employeeId}">${mail.firstName} ${mail.lastName}</option>	
				</c:forEach>
	        </c:otherwise>
        </c:choose>
	    	
		</select>
		<div class="required-resource">Required*</div>
  	 </div>
  	 </td></tr>
<tr><td>
<div class="validateMe">
	    <label for="reasonReplacing" ><span class="required">*</span> Please select reason for replacement:</label>
	   </div>
</td><td>
<div class="form-group validateMe"><select class="form-control required comboselect check" id="reasonForReplacement">
	    <c:choose>
	    	<c:when test="${buttonFlag}">
	    			<c:forEach var="reasonObj" items="${reasonList}">
	    			<option value="${dataList.requestRequisition.reason.id}">${dataList.requestRequisition.reason.reason}</option>
	    			<c:if test="${dataList.requestRequisition.reason.id != reasonObj.id}">
	    				<option value="${reasonObj.id}">${reasonObj.reason}</option>
	    			</c:if>
	    			
	    			</c:forEach>
	    	
	    	
	    	</c:when>
	    	<c:otherwise>
	    		 <c:forEach var="reasonObj" items="${reasonList}">
		    		<option value="${reasonObj.id}">${reasonObj.reason}</option>
		   		 </c:forEach>
	    	</c:otherwise>
	    
	    </c:choose>
		   
	    </select>
	    <div class="required-resource">Required*</div>
	   </div>
</td></tr></table>

    
     <button type="button" id="reasonButton" class="btn btn-secondary next-button">Save Reason</button>
    
  </div>

</div>
    
    <div id="myPreviewModal" class="modal">

  <!--My Preview Modal content -->
  <div class="modal-content" style="width:80%;">
    <span class="close" id="close-icon">&times;</span>
    
    <ul class="nav nav-pills tab-border resource-request-nav">
            <li class="nav_step1">
                <a><h5><div class="numberCircle">1</div>Delivery Information</h5></a>
            </li>
        </ul>
        
        <div class="row mar-row">
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <label>Hiring BG/BU</label><BR/>
                                <div id="previewHiringBGBU">val</div>
                            </div>
                          <!--   <div class="form-group col-md-4">
                                <label>Priority</label><br>
                                <div id="previewPriority"></div>
                            </div> -->
							<div class="form-group col-md-4">
								<label>Requestor's BG/BU</label>
								<div id="previewRequestorsBGBU">val</div>
							</div>
							<div class="form-group col-md-4">
								<label>Request Date</label>
								<div id="previewRequestDate">val</div>
							</div>

							<div class="form-group col-md-4">
								<label>Delivery POC</label><BR />
								<div id="previewDeliveryPOC">val</div>
							</div>
							<div class="form-group col-md-4">
								<label>Requestor's Name</label>
								<div id="previewRequestorsName">val</div>
							</div>
							<!-- <div class="form-group col-md-4">
                                <label>Requestor Grade</label>
                                <div id="previewRequestorsName">val</div>
                            </div>
                            <div class="form-group col-md-4">
                                <label>Requestor Designation</label>
                                <div>val</div>
                            </div> -->
						</div>
					</div>
					<ul class="nav nav-pills tab-border resource-request-nav">
						<li class="nav_step2"><a href="javascript:void(0);">
									<span class="stepContent">Customer &amp; Project Information</span>
									<span class="numberCircle">2</span>
								</a></li>
					</ul>
					<div class="row mar-row">
						<div class="form-row">
							<div class="form-group col-md-4">
								<label>Requirement Type</label>
								<div id="previewRequirementType"></div>
							</div>
							<div class="form-group col-md-4">
								<label>Resource Required for</label>
								<div id="previewRequiredFor"></div>
							</div>
							<!-- Added new Field AM job Code Start -->
							<div class="form-group col-md-4">
								<label>AM Job Code</label>
								<div id="previewAmJobCode">-</div>
							</div>
							<!-- Added new Field AM job Code End -->
							<div class="form-group col-md-4">
								<label>Resource Required Date</label>
								<div id="previewRequiredDate"></div>
							</div>
							<div class="form-group col-md-4">
								<label>Client Type</label>
								<div id="previewClientType"></div>
							</div>
							<div class="form-group col-md-4">
								<label>Client Name</label>
								<div id="previewClientName"></div>
							</div>
							<div class="form-group col-md-4 previewGrp">
								<label>Client Group</label>
								<div id="previewClientGroup">-</div>
							</div>
							<div class="form-group col-md-4">
								<label>Project Type</label>
								<div id="previewProjectType"></div>
							</div>
							<div class="form-group col-md-4">
								<label>Project Name</label>
								<div id="previewProjectName"></div>
							</div>
							<div class="form-group col-md-4">
								<label>Competency/Skill Family</label>
								<div id="previewCompetency"></div>
							</div>
							<div class="form-group col-md-4">
								<label>Number of Position(s)</label>
								<div id="previewPositions"></div>
							</div>
							<div class="form-group col-md-4 hold" style="display: none;">
								<label>Hold</label>
								<div id="previewHold"></div>
							</div>
							<div class="form-group col-md-4 lost" style="display: none;">
								<label>Lost</label>
								<div id="previewLost"></div>
							</div>
							<div class="form-group col-md-4">
								<label>Designation</label>
								<div id="previewDesignation"></div>
							</div>
							<div class="form-group col-md-4">
								<label>Experience Required</label>
								<div id="previewExpRequired"></div>
							</div>
							<div class="form-group col-md-4">
								<label>Allocation Type</label>
								<div id="previewAllocationType"></div>
							</div>

							<div class="form-group col-md-4">
								<label>Job Location</label>
								<div id="previewLocation"></div>
							</div>
							<div class="form-group col-md-4">
								<label>Project Duration(in months)</label>
								<div id="previewProjectDuration"></div>
							</div>
							<div class="form-group col-md-4">
								<label>Project Shift Type</label>
								<div id="previewShiftType"></div>
							</div>
							<div class="form-group col-md-4">
								<label>Project Shift Details(Based on Project Shift
									Type)</label>
								<div id="previewShiftDetails">-</div>
							</div>
						</div>
					</div>
					<ul class="nav nav-pills tab-border resource-request-nav">
						<li class="nav_step2"><a href="javascript:void(0);">
									<span class="stepContent">Hiring/Job Description</span>
									<span class="numberCircle">3</span>
								</a></li>
					</ul>
					<div class="row mar-row">
						<div class="form-row">
							<div class="form-group col-md-4">
								<label>Client Interview Required</label>
								<div id="previewClientInterview"></div>
							</div>
							<div class="form-group col-md-4">
								<label>Client BGV Required</label>
								<div id="previewBGVRequired"></div>
							</div>
							<div style="clear: both;"></div>
							<div class="form-group col-md-12 text-decoration-ul">
								<strong>Interview Panels (preferrably E3 and above) : </strong>
							</div>
							<div class="form-group col-md-4">
								<label>Round 1 (Multiselect)</label>
								<div id="previewRound1">-</div>
							</div>
							<div class="form-group col-md-4">
								<label>Round 2 (Multiselect)</label>
								<div id="previewRound2">-</div>
							</div>
							<div style="clear: both;"></div>
							<div class="form-group col-md-12 text-decoration-ul">
								<strong>List the technical skills to be evaluated by
									Panels: </strong>
							</div>
							<div class="form-group col-md-4">
								<label>Key Scanners</label>
								<div id="previewSkillsToEval"></div>
							</div>
							<!--  <div class="form-group col-md-4">
                                <label>SkillSet</label>
                                  <div id="previewShiftDetails"></div>
                            </div> -->

							<div style="clear: both;"></div>
							<div class="form-group col-md-12 text-decoration-ul">
								<strong>Job Description </strong>
							</div>
							<div class="form-group col-md-4">
								<label>Primary Skills</label>
								<div id="previewPrimarySkills"></div>
							</div>
							<div class="form-group col-md-4">
								<label>Additional Skills</label>
								<div id="previewAddSkills">-</div>
							</div>
							<div class="form-group col-md-4">
								<label>Roles and Responsibilities</label>
								<div id="previewRoles">-</div>
							</div>
							<div class="form-group col-md-4">
								<label>Additional Comment</label>
								<div id="previewAdditionalComments">-</div>
							</div>
						</div>
					</div>
					<ul class="nav nav-pills tab-border resource-request-nav">
						<li class="nav_step2"><a href="javascript:void(0);">
									<span class="stepContent">Notify &amp; Submit</span>
									<span class="numberCircle">4</span>
								</a></li>
					</ul>
					<div class="row mar-row">
						<div class="form-row">
							<div class="form-group col-md-4">
								<label>Mail to: (Send Mail To)</label>
								<div id="previewMailTo">-</div>
							</div>
							<div class="form-group col-md-4">
								<label>Notify to: (CC)</label>
								<div id="previewNotifyTo">-</div>
							</div>
							<div class="form-group col-md-4">
								<label>PDL (CC)</label>
								<div id="previewPDl">-</div>
							</div>
						</div>
					</div>
					<button type="button" id="close-btn"
						class="btn btn-secondary next-button">Close</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="resources/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript"
		src="resources/js/locales/bootstrap-datetimepicker.fr.js"
		charset="UTF-8"></script>
	<script type="text/javascript" src="resources/js/resource-requests.js"></script>
</body>
</html>
