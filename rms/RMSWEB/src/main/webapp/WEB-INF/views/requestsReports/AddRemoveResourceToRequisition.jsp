<%@ page import="org.yash.rms.util.Constants"%>
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
<link href="${style_css}" rel="stylesheet" type="text/css">
</link>

<spring:url
	value="resources/js-framework/datatables/ZeroClipboard.js?ver=${app_js_ver}"
	var="ZeroClipboard_js" />
<script src="${ZeroClipboard_js}" type="text/javascript">
</script>
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
<script src="${multiselect_filter_js}" type="text/javascript"></script>

<spring:url
	value="/resources/js-framework/jquery-ui/jquery-ui-1.8.22.custom.min.js?ver=${app_js_ver}"
	var="jquery_ui_1_8_21_custom_min_js" />
<script src="${jquery_ui_1_8_21_custom_min_js}" type="text/javascript">
  <!-- required for FF3 and Opera 
  -->
</script>
<spring:url value="/resources/css/select2.min.css?ver=${app_js_ver}"
	var="select2_css" />
<link href="${select2_css}" rel="stylesheet" type="text/css">
</link>
<spring:url value="/resources/js/select2.min.js?ver=${app_js_ver}"
	var="select2_js" />
<script src="${select2_js}" type="text/javascript"></script>
<style>
#internal .select2-container {
	width: 208px !important;
}

.notify-icons-main input[type='checkbox'].checkId:checked:after {
	background: linear-gradient(to bottom, rgba(235, 233, 249, 1) 0%,
		rgba(216, 208, 239, 1) 50%, rgba(206, 199, 236, 1) 51%,
		rgba(193, 191, 234, 1) 100%);
}

.notify-icons-main input[type='checkbox'].checkId:hover:after,
	.notify-icons-main input[type='checkbox'].checkId:checked:hover:after {
	background: linear-gradient(to bottom, rgba(235, 233, 249, 1) 0%,
		rgba(216, 208, 239, 1) 50%, rgba(206, 199, 236, 1) 51%,
		rgba(193, 191, 234, 1) 100%);
}

.modal-body {
	/*max-height: calc(90vh - 250px); */
	overflow-y: auto;
}

.pointer {
	cursor: pointer;
}

.comment {
	float: left;
}

form select {
	width: 130px;
	height: 25px;
}

form input[type="text"] {
	height: 25px;
}

.modal-dialog {
	width: 65%;
}

.request-reporttable th, .request-reporttable td, .report-status>table td,
	.report-status>table th {
	padding: 8px;
	border: 1px solid #ccc !important;
	vertical-align: top;
}

/* .request-reporttable td:first-child, .request-reporttable th:first-child
                {
                width: 20%
} 
 */
.request-reporttable td input[type="text"] {
	min-height: 27px;
	margin-right: 3px;
	line-height: 27px;
	display: inline-block;
	padding: 0 2px;
	float: left;
	width: 80px;
	margin: 0 3px 0 0;
}

.sub-row {
	clear: both;
	margin: 8px 0 0 0;
	display: table;
	width: 100%;
}

.sub-row:first-child {
	margin-top: 0;
}

.report-status {
	margin-top: 10px;
}

.request-reporttable .remove_field {
	padding-top: 5px;
	display: inline-block;
}

.new-value {
	float: left;
}

.blocked-select {
	float: left;
}

.new-value textarea {
	width: 100%;
	min-height: 45px;
}

.well {
	background-color: #f6f6f6;
	border: 1px solid #e3e3e3;
	border-radius: 4px;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.05) inset;
	margin-bottom: 20px;
	min-height: 20px;
	padding: 19px;
}

td table tbody tr td {
	border: none !important;
}

.full-width {
	margin-right: 0px;
	margin-left: 0px;
}

.main-content {
	margin: 0 auto;
}

.modal-header {
	border-bottom-color: #f4f4f4;
}

#selection-table {
	border: transparent !important;
	text-align: center;
}

table td:first-child {
	font-weight: 600;
}

#add-resource table, td, th {
	border: 1px solid #ddd;
	text-align: left;
}

table {
	border-collapse: collapse;
	width: 100% !important;
}

#add-resource th, td {
	padding: 10px;
}

textarea {
	resize: none !important;
}

textarea {
	width: 100%;
}

.new-value textarea {
	min-height: 45px;
}

#resume-upload input {
	position: absolute;
	font-size: 15px;
	opacity: 0;
	right: 465px;
	top: 45px;
}

.upload-resume-btn {
	display: inline-block !important;
	padding: 4px 10px 4px;
	margin-bottom: 0;
	font-size: 13px;
	line-height: 18px;
	width: 100%;
	color: #333333;
	text-align: center;
	text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
	position: relative;
	overflow: hidden;
}

#resource-details table, td, th {
	border: 1px solid #ddd;
	text-align: left;
}

#resource-details th, td {
	padding: 10px;
}

.sub-row:first-child {
	margin-top: 0;
}

.sub-row {
	clear: both;
	margin: 8px 0 0 0;
	display: table;
	width: 100%;
}

.inputText {
	margin-top: 5px;
}

#btnReset {
	margin-top: 10px;
	width: 165px;
}

.fileRequired {
	position: absolute;
	color: red;
}

/* input[type=checkbox] {
  display: none;
  }
  input[type=checkbox] + label:before {
  content: "\2714";
  border: 0.1em solid #01498B;
  border-radius: 0.2em;
  display: inline-block;
  width: 15px;
  height: 15px;
  padding-left: 0.1em;
  padding-right: 0.1em;
  padding-bottom: 0.5em;
  margin-right: 0.3em;
  vertical-align: bottom;
  color: transparent;
  transition: .2s;
  }
  input[type=checkbox] + label:active:before {
  transform: scale(0);
  }
  input[type=checkbox]:checked + label:before {
  background-color:#01498B !important;
  border-color: #01498B !important;
  color: #fff !important;
} */
.instStep {
	margin: 10px 2px;
	background-color: #cadfea;
	width: 70%;
	float: left;
	padding: 10px;
	margin-left: 15%;
}

.instStep .instStepHead {
	background-color: #7ecada;
	text-align: center;
	padding: 5px;
	width: 70%;
	margin-left: 10%;
	border-radius: 10px;
	font-weight: bold;
}

.instStep .panel-group {
	margin-bottom: 0px;
}

.instStep .panel-body {
	padding: 5px 25px;
}

.instStep .panel-title {
	font-size: 14px;
}

.instStep .panel-heading {
	padding: 5px;
	background-color: #01498B;
	color: #fff;
}

.instStep .panel-heading a:hover, a:active, a:focus {
	color: inherit;
}

.commentIcon {
	color: #139f72;
	font-size: 24px;
	margin: 1px 10px;
	cursor: pointer;
}

.modal-content.commentmodal {
	width: 80%;
	margin-top: 15%;
}

.commentmodal>.modal-header {
	background-color: #0067a5 !important;
	color: #fff !important;
	padding: 10px;
	border-bottom: 1px solid #4f92d7;
}

.commentmodal>.modal-header>.close {
	opacity: 1;
	color: #fff;
	box-shadow: none;
}

.commentmodal>.modal-header>.close:hover {
	background-color: transparent;
}

.commentmodal .modal-body {
	min-height: 100px;
	max-height: 250px;
	overflow-x: auto;
}

.commentmodal .commentBody>.commentText {
	word-wrap: break-word;
	padding: 2px 5px;
	margin: 0 0 6px;
	border-top-right-radius: 10px;
	padding-bottom: 10px;
	font-size: 14px;
	background-color: #d7e8f6;
	border-left: 2px solid #72A1C9;
	border-right: 2px solid #72A1C9;
}

.commentBody>.commentText footer {
	display: block;
	font-size: 80%;
	line-height: 0.5;
	color: #777;
}

.commentmodal .commentbox {
	border-top: 1px solid #e5e5e5;
	padding: 5px 10px;
	background-color: #e0eeee;
	border-right: 2px solid #72A1C9;
	box-shadow: 0px 0px 4px #333;
}

.JDate, .IDate, .interviewDate, .joinDate {
	display: none;
}

//
\*********************************
  .sk-fading-circle {
	margin: 100px auto;
	width: 40px;
	height: 40px;
	position: relative;
}

.sk-fading-circle .sk-circle {
	width: 100px;
	height: 100px;
	position: absolute;
	left: 0;
	right: 0;
	top: 40%;
	display: none;
	z-index: 10000 !important;
}

.sk-fading-circle .sk-circle:before {
	content: '';
	display: block;
	margin: 0 auto;
	width: 15%;
	height: 15%;
	background-color: #01498B;
	border-radius: 100%;
	-webkit-animation: sk-circleFadeDelay 1.2s infinite ease-in-out both;
	animation: sk-circleFadeDelay 1.2s infinite ease-in-out both;
}

.sk-fading-circle .sk-circle2 {
	-webkit-transform: rotate(30deg);
	-ms-transform: rotate(30deg);
	transform: rotate(30deg);
}

.sk-fading-circle .sk-circle3 {
	-webkit-transform: rotate(60deg);
	-ms-transform: rotate(60deg);
	transform: rotate(60deg);
}

.sk-fading-circle .sk-circle4 {
	-webkit-transform: rotate(90deg);
	-ms-transform: rotate(90deg);
	transform: rotate(90deg);
}

.sk-fading-circle .sk-circle5 {
	-webkit-transform: rotate(120deg);
	-ms-transform: rotate(120deg);
	transform: rotate(120deg);
}

.sk-fading-circle .sk-circle6 {
	-webkit-transform: rotate(150deg);
	-ms-transform: rotate(150deg);
	transform: rotate(150deg);
}

.sk-fading-circle .sk-circle7 {
	-webkit-transform: rotate(180deg);
	-ms-transform: rotate(180deg);
	transform: rotate(180deg);
}

.sk-fading-circle .sk-circle8 {
	-webkit-transform: rotate(210deg);
	-ms-transform: rotate(210deg);
	transform: rotate(210deg);
}

.sk-fading-circle .sk-circle9 {
	-webkit-transform: rotate(240deg);
	-ms-transform: rotate(240deg);
	transform: rotate(240deg);
}

.sk-fading-circle .sk-circle10 {
	-webkit-transform: rotate(270deg);
	-ms-transform: rotate(270deg);
	transform: rotate(270deg);
}

.sk-fading-circle .sk-circle11 {
	-webkit-transform: rotate(300deg);
	-ms-transform: rotate(300deg);
	transform: rotate(300deg);
}

.sk-fading-circle .sk-circle12 {
	-webkit-transform: rotate(330deg);
	-ms-transform: rotate(330deg);
	transform: rotate(330deg);
}

.sk-fading-circle .sk-circle2:before {
	-webkit-animation-delay: -1.1s;
	animation-delay: -1.1s;
}

.sk-fading-circle .sk-circle3:before {
	-webkit-animation-delay: -1s;
	animation-delay: -1s;
}

.sk-fading-circle .sk-circle4:before {
	-webkit-animation-delay: -0.9s;
	animation-delay: -0.9s;
}

.sk-fading-circle .sk-circle5:before {
	-webkit-animation-delay: -0.8s;
	animation-delay: -0.8s;
}

.sk-fading-circle .sk-circle6:before {
	-webkit-animation-delay: -0.7s;
	animation-delay: -0.7s;
}

.sk-fading-circle .sk-circle7:before {
	-webkit-animation-delay: -0.6s;
	animation-delay: -0.6s;
}

.sk-fading-circle .sk-circle8:before {
	-webkit-animation-delay: -0.5s;
	animation-delay: -0.5s;
}

.sk-fading-circle .sk-circle9:before {
	-webkit-animation-delay: -0.4s;
	animation-delay: -0.4s;
}

.sk-fading-circle .sk-circle10:before {
	-webkit-animation-delay: -0.3s;
	animation-delay: -0.3s;
}

.sk-fading-circle .sk-circle11:before {
	-webkit-animation-delay: -0.2s;
	animation-delay: -0.2s;
}

.sk-fading-circle .sk-circle12:before {
	-webkit-animation-delay: -0.1s;
	animation-delay: -0.1s;
}

@
-webkit-keyframes sk-circleFadeDelay { 0%, 39%, 100% {
	opacity: 0;
}

40%
{
opacity


:

 

1;
}
}
@
keyframes sk-circleFadeDelay { 0%, 39%, 100% {
	opacity: 0;
}

40%
{
opacity


:

 

1;
}
}

/****  ***/
.fileinput-button-yash {
	position: relative;
	overflow: hidden;
	display: inline-block;
}

.fileinput-button-yash input {
	position: absolute;
	top: 0;
	right: 0;
	margin: 0;
	opacity: 0;
	-ms-filter: 'alpha(opacity=0)';
	font-size: 200px !important;
	direction: ltr;
	cursor: pointer;
	height: 34px;
}

/* Fixes for IE < 8 */
@media screen\9 {
	.fileinput-button-yash input {
		filter: alpha(opacity = 0);
		font-size: 100%;
		height: 100%;
	}
}

.action_btn {
	background: #eeeae7;
	color: #3376bc !important;
	padding: 6px 9px;
	border-radius: 50px;
	width: 30px;
	height: 30px;
	display: inline-block;
	transition: all 0.5s;
	cursor: pointer;
}

.action_btn:hover {
	color: #fff !important;
	background: #3376bc;
}

.action_btn:hover.delete_btn {
	color: #fff !important;
	background: #ff0000;
}

.action_btn i {
	font-size: 14px;
	cursor: pointer;
}

.action_btn .fileRequired {
	top: 0;
	right: 0;
}

.align-center {
	text-align: center !important;
}

.color-red {
	color: red !important;
	background: #ff000040;
	padding: 5px 8px;
	border-radius: 40px;
	transition: 0.5s all;
}

.color-red:hover {
	color: #fff !important;
	background: red;
}

.color-green {
	color: green !important;
	background: #00800040 !important;
	padding: 5px 8px !important;
	border-radius: 40px !important;
	transition: 0.5s all;
}

.color-green:hover {
	color: #fff !important;
	background: green;
}

.color-blue {
	color: blue !important;
	background: #0000ff40;
	padding: 5px 8px;
	border-radius: 40px;
	transition: 0.5s all;
}

.color-blue:hover {
	color: #fff !important;
	background: blue;
}

.color-orange {
	color: orange !important;
	background: #ffa50040;
	padding: 5px 8px;
	border-radius: 40px;
	transition: 0.5s all;
}

.color-orange:hover {
	color: #fff !important;
	background: orange;
}

.wrap-div {
	white-space: normal;
	min-width: 200px;
}

.pd-20 {
	padding: 20px;
}

pd-l-r-20 {
	padding: 0 20px !important;
}

.headingNewStyle {
	background: #24464e;
	color: #fff;
	padding: 10px 20px;
}

.bg-none {background-none;
	
}

.close-right {
	display: inline-block;
	width: 100%;
	text-align: right;
}

#modal_data_table_wrapper table.dataTbl thead th {
	width: 180px !important;
}

/*Datatable css changes*/
.resourceSubmission {
	color: green;
}

.resourceShortlisted {
	color: orange;
}

.resourceJoined {
	color: blue;
}

.resourceRejected {
	color: red;
}

.orange-batch {
	width: 10px;
	height: 10px;
	background: orange;
	display: inline-block;
	border-radius: 40px;
	position: relative;
	top: -10px;
	right: 5px;
}

.forward-rrf {
	position: relative;
}

.forward-rrf button {
	width: 230px !important;
	text-indent: 8px;
}

.forward-rrf .ui-multiselect-menu {
	top: inherit !important;
	left: 7px !important;
	position: absolute !important;
}

.blockUI {
	z-index: 10000 !important;
}

#toast-container {
	z-index: 10000 !important;
}

.toasterBgDiv {
	position: fixed;
}

.btn-primary[disabled] {
	background-color: #6fa7f5;
}

.btn-primary[disabled]:hover {
	background-color: #6fa7f5;
}

.sk-fading-circle {
	z-index: 9999;
	position: relative;
}

.blockUI.blockOverlay {
	z-index: 9999 !important;
}

.blockUI.blockMsg.blockPage {
	z-index: 9999 !important;
}

/*Sumit Jain style changes*/
.select2-container--default .select2-selection--multiple .select2-selection__choice
	{
	color: #000;
	background-color: #ddd;
}

.select2-container {
	width: 469px !important;
}
/*Added on 6-8-19 by kratika*/
.text-mrgin {
	margin-bottom: 20px;
}

.padding-prop {
	padding-left: 4px;
}

#contactPersonDiv .select2-container {
	width: 230px !important;
}

form input[type="date"],form input[type="time"],form input[type="number"]  {
    margin-top: 3px;
    font-size: 12px;
    border: 1px solid #CCCCCC;
    height: 25px;
}
input[type="date"],input[type="time"],input[type="number"] {
    font-size: 11px;
    border: 1px solid #CCCCCC;
    border-radius: 5px;
}
#interviewModal select{
    font-size: 12px;
    border: 1px solid #CCCCCC;
    height: 28px;
    border-radius: 5px;
}
</style>

<script>
  function dynamicTable() {
    $("#saveResource").attr("disabled", false);
    $("#add_internal_table").find('tr').remove();
    var res = $('#resourceBlocked').val();
    var names = $("#InternalResourceText").val();
    var spli = names.split(",");
    if (res != null) {
      for (var z = 0; z < spli.length; z++) {
        var resourceArr = res[z].split('/');
        var resId = resourceArr[0];
        var filename = resourceArr[1];
        var mailContact = resourceArr[3];
        var mailEmail = resourceArr[2];
        var mailExperience = resourceArr[4];
        var mailSkills = resourceArr[5];
        var resourceID = document.getElementById(resId);
        //if(!resourceID){
        var details =
          "<tr>" +
          "<td class='cust-spli'>" +
          spli[z] +
          "</td>" +
          "<td id='emailId_'>" + mailEmail + "</td>" +
          "<td id= 'totalExperience_'>" + mailExperience + "</td>" +
          "<td id='skills_'>" + mailSkills + "</td>" +
          "<td id='contactNumber_'>" + mailContact + "</td>" +
          "<td>" + (filename == '' ? "<span class='fileRequired'>*</span>" : "<span> </span>") +
          "<input type='file' class='resume' onchange='internalResource(event)' id='file_" + z +
          "' name='file' value='" + filename + "' />" +
          "<span style='display:none;color:red' id='internalspan_" + z + "'>" + 'Required' + "</span>" +
          "</td>" +
          "<td>" +
          "<input type='hidden'  id='hiddenfile" + z + "' value='" + filename + "' />" +
          "<input type='hidden' class='resourceid' data-index='" + z + "' id='resource" + z + "' value='" + resId +
          "' />" +
          "<input type='hidden'  id='" + resId + "' value='" + resId + "' />" +
          filename +
          "</td>" +
          "<td class='align-center'>" +
          "<a href='#' style='border: none;' class='remove_field btn btn-danger action_btn delete_btn' ><i class='fa fa-remove'></i></a>" +
          "</td>" +
          "</tr>";
        $("#add_internal_table").append(details);
        //}                                          
        $("#selection-table").show();
      }
    } else {
      showError("Please select atleast one internal resource to submit");
      $("#saveResource").attr("disabled", true);
    }
  }
  $(function () {
    /*                                 $('#resourceBlocked').multiselect({
                                                    includeSelectAllOption : true,
                                                    id : 'resourceBlocked_id'
                                    }).multiselectfilter();
                                    $('#resourceBlocked1').multiselect({
                                                    includeSelectAllOption : true,
                                                    id : 'resourceBlocked_id1'
                                    }).multiselectfilter(); */


    $("span#resourceBlocked_id").next().on("click", function () {
      //alert("keyInterviewersTwo_one") 
      $(".resourceBlocked_id").val('');
    });
    $("span#resourceBlocked_id1").next().on("click", function () {
      //alert("keyInterviewersTwo_one") 
      $(".resourceBlocked_id1").val('');
    });

    $('#resourceBlocked').bind('change', function () {

      var selectedText;

      /*sarang added start*/
      var selMulti = $.map($(this).find('option:selected'),
        function (el, i) {
          var res = $('#resourceBlocked').val();
          selectedText = $(el).text();
          $("#InternalResourceText").val(selectedText);


          return $(el).text();

        });
      if (selMulti == "") {
        $('#InternalResourceText').html('');
      }
      $("#InternalResourceText").val(selMulti.join(", "));
      addResourceInternal = $(this).val();
    });

    $('#resourceBlocked1').bind('change', function () {
      var selectedText1;


      var selMulti1 = $.map($(this).find('option:selected'),
        function (el, i) {
          var res1 = $('#resourceBlocked1').val();
          selectedText1 = $(el).text();
          $("#InternalResourceText1").val(selectedText1);

          return $(el).text();

        });
      if (selMulti1 == "") {
        $('#InternalResourceText1').html('');
      }
      $("#InternalResourceText1").val(selMulti1.join(", "));
      addResourceInternal1 = $(this).val();
    });
    $("span.ui-icon-closethick").next().on("click", function () {
      //$("#InternalResourceText").empty();
      $('#InternalResourceText').html('');
      var par = $("#add_internal_table td").parent();
      par.remove(); //delete by dynamic button
    });

    //for add dynamic external resource 

    var wrapper = $(".new-value"); //Fields wrapper
    var add_button = $(".add_field_button"); //Add button ID
    var values = [];
    var x = 0; //initlal text box count
    $(add_button).click(function (e) {
      $("#saveResource").attr("disabled", false);
      var item = $("#add_external_table tr:last").find('input').attr('id');
      if (item == 'undefined' || item == null)
        x = 0;
      else
        x = parseInt(item.split('_')[1]) + 1;

      var max_fields = $("#noOfResources").text(); //maximum input boxes allowed
      e.preventDefault();


      var external_details =
        "<tr>" +
        "<td>" +
        "<div class='external-resource'><input   required type='text' id='externalName_" + x +
        "' maxlength='50' onKeyPress='clearExtnernalValidation(event)' name='externalButtonAdd[]'/>" +
        "<span style='display:none;color:red' id='externalName_span_" + x + "'>" + 'Required' + "</span>" +
        "</div>" +
        "</td>" +
        "<td>" +
        "<div class='external-resource'><input   required type='text' id='location_" + x +
        "' onKeyPress='clearExtnernalValidation(event)' name='externalButtonAdd[]'/>" +
        "<span style='display:none;color:red' id='location_span_" + x + "'>" + 'Required' + "</span>" +
        "</div>" +
        "</td>" +
        "<td>" +
        "<div class='external-resource'><input   required type='text' id='noticePeriod_" + x +
        "' onKeyPress='clearExtnernalValidation(event)' name='externalButtonAdd[]'/>" +
        "<span style='display:none;color:red' id='noticePeriod_span_" + x + "'>" + 'Required' + "</span>" +
        "</div>" +
        "</td>" +
        "<td>" +
        "<div class='external-resource'><input   required type='text' id='emailId_" + x +
        "' onKeyPress='clearExtnernalValidation(event)' name='externalButtonAdd[]'/>" +
        "<span style='display:none;color:red' id='emailId_span_" + x + "'>" + 'Enter Valid Email Id' +
        "</span>" +
        "</div>" +
        "</td>" +
        "<td>" +
        "<div class='external-resource'><input   required type='text' id='totalExperience_" + x +
        "' onKeyPress='clearExtnernalValidation(event)' name='externalButtonAdd[]'/>" +
        "<span style='display:none;color:red' id='totalExperience_span_" + x + "'>" + 'Enter Valid Experience' +
        "</span>" +
        "</div>" +
        "</td>" +
        "<td>" +
        "<div class='external-resource'><input   required type='text' id='skills_" + x +
        "' maxlength='100' onKeyPress='clearExtnernalValidation(event)' name='externalButtonAdd[]'/>" +
        "<span style='display:none;color:red' id='skills_span_" + x + "'>" + 'Required' + "</span>" +
        "</div>" +
        "</td>" +
        "<td>" +
        "<div class='external-resource'><input   required type='text' id='contactNumber_" + x +
        "' onKeyPress='clearExtnernalValidation(event)' name='externalButtonAdd[]'/>" +
        "<span style='display:none;color:red' id='contactNumber_span_" + x + "'>" + 'Enter Valid Contact No.' +
        "</span>" +
        "</div>" +
        "</td>" +
        "<td class='align-center'>" +
        "<div class=' column external fileinput-button-yash'><i class='action_btn fa fa-upload'></i><span class='fileRequired' style='position: absolute;right: 0;top: 0px;' >*</span><input type='file' onchange='externalFiles(event)' name='file' id='externalresume_" +
        x + "' class='file upload-resume-btn' value='Upload' />" +
        "<span style='display:none;color:red' id='externalspan_" + x + "'>" + 'Required' + "</span>" +
        "</div>" +
        "</td>" +
        "<td class='align-center'>" +
        "<div class=' column external fileinput-button-yash'><i class='action_btn fa fa-upload'></i><span class='fileRequired' style='position: absolute;right: 0;top: 0px;'>*</span><input type='file' onchange='tacFunction(event)' class='tac upload-resume-btn' id='tac_" +
        x + "' name='tac' />" +
        "<span style='display:none;color:red' id='externaltac_" + x + "'>" + 'Required' + "</span>" +
        "</div>" +
        "</td>" +
        "<td class='align-center'>" +
        "<a href='#' style='border: none;' class='remove_field btn btn-danger action_btn delete_btn' ><i class='fa fa-remove'></i></a>" +
        "</td>" +
        "</tr>";
      $("#add_external_table").append(external_details);
      $("#add_external_table").closest("table").show();
      x++;


    });

    $("#add_external_table").on("click", ".remove_field", function (e) {
      e.preventDefault();
      var par = $(this).parent().parent();
      //var intialIdTac=$(this).parent().parent().find('.tac').attr('id').split('_')[0];
      var intialIdResume = $(this).parent().parent().find('.file').attr('id').split('_')[1];
      var len = $(this).parents('table')[0].rows.length - 1;
      var i = 1 + parseInt(intialIdResume);
      for (; i < len; i++) {
        $(this).parent().parent().parent().find('input#externalresume_' + i).attr('id', 'externalresume_' + (i -
          1));
        $(this).parent().parent().parent().find('input#tac_' + i).attr('id', 'tac_' + (i - 1));
        //$('#tac_'+i).attr('id','tac_'+(i-1));
        $('#externalspan_' + i).attr('id', 'externalspan_' + (i - 1));
        $('#externaltac_' + i).attr('id', 'externaltac_' + (i - 1));
        $('#externalName_' + i).attr('id', 'externalName_' + (i - 1));
        $('#externalName_span_' + i).attr('id', 'externalName_span_' + (i - 1));
        $('#location_span_' + i).attr('id', 'location_span_' + (i - 1));
        $('#location_' + i).attr('id', 'location_span_' + (i - 1));
        $('#noticePeriod_' + i).attr('id', 'noticePeriod_span_' + (i - 1));
        $('#noticePeriod_span_' + i).attr('id', 'noticePeriod_span_' + (i - 1));
        $('#emailId_' + i).attr('id', 'emailId_span_' + (i - 1));
        $('#emailId_span_' + i).attr('id', 'emailId_span_' + (i - 1));
        $('#totalExperience_' + i).attr('id', 'totalExperience_span_' + (i - 1));
        $('#totalExperience_span_' + i).attr('id', 'totalExperience_span_' + (i - 1));
        $('#skills_' + i).attr('id', 'skills_span_' + (i - 1));
        $('#skills_span_' + i).attr('id', 'skills_span_' + (i - 1));
        $('#contactNumber_' + i).attr('id', 'contactNumber_span_' + (i - 1));
        $('#contactNumber_span_' + i).attr('id', 'contactNumber_span_' + (i - 1));

      }

      par.remove(); //delete by dynamic button
      if ($("#add_external_table").find("tr").length === 0) {
        $("#saveResource").attr("disabled", true);
        if ($("#add_internal_table").find("tr").length !== 0) {
          $("#saveResource").attr("disabled", false);
        }
        $("#add_external_table").closest("table").hide();
        $(".externalTable").hide();
      } else {
        $("#saveResource").attr("disabled", false);
      }
    });
    $("#add_internal_table").on("click", ".remove_field", ".internal_remove_field", function (e) {
      e.preventDefault();
      var par = $(this).parent().parent();
      par.remove(); //delete by dynamic button
      //get value for uncheck dropdown
      var childvalue = $(this).parent().siblings(":first").text() //get value for uncheck dropdown
      $('.ui-multiselect-checkboxes li').find("label input[type='checkbox']:checked").each(function (idx, el) {
        if (childvalue.trim() == el.title.trim()) {
          $(el).click();
        }
      });
      if ($("#add_internal_table").find("tr").length === 0) {
        $("#saveResource").attr("disabled", true);
        if ($("#add_external_table").find("tr").length !== 0) {
          $("#saveResource").attr("disabled", false);
        }
        $('#resourceBlocked option:selected').each(function () {
          $(this).prop('selected', false);
        });
        $('#resourceBlocked').multiselect('refresh');
        $(".internalTable").hide();
      } else {
        $("#saveResource").attr("disabled", false);
      }
    });


  });

  temparr = [];
  var arr = [];
  var arrOfFileNames = [];

  function internalResource(event) {
    var id = event.target.id;
    var i = id.split('_').pop();
    var file = $('#' + id).prop('files');
    if (file != null) {
      var ext = file[0].name.split('.').pop();
      var sizeofdoc = file[0].size;
      //var filesizeMB = ((sizeofdoc/1024)/1024).toFixed(4);
      if ((ext == "pdf" || ext == "docx" || ext == "doc" || ext == "PDF" || ext == "DOCX" || ext == "DOC") &&
        sizeofdoc < 5000000) {
        $("#" + i).css("border", "solid 1px #d5d5d5");
        $("#internalspan_" + i).hide();
      } else {
        alert("Upload only doc or pdf files with size less than 5MB");
        input = $('#' + id);
        input.val('').clone(true);
      }
    }
  }

  function tacFunction(event) {
    var id = event.target.id;
    var i = id.split('_').pop();
    var tacfile = $('#tac_' + i).prop('files');
    var ext = tacfile[0].name.split('.').pop();
    var sizeofdoc = tacfile[0].size;
    //var filesizeMB = ((sizeofdoc/1024)/1024).toFixed(4);
    if ((ext == "pdf" || ext == "docx" || ext == "doc" || ext == "PDF" || ext == "DOCX" || ext == "DOC") && sizeofdoc <
      5000000) {
      $("#tac_" + i).css("border", "solid 1px #d5d5d5");
      $("#externaltac_" + i).hide();
    } else {
      alert("Upload only doc or pdf files with size less than 5MB");
      input = $('#tac_' + i);
      input.val('').clone(true);
    }
  }


  externalResumeArray = [];
  tempExterArr = [];

  function modalPopupOpen() {
    $("#myModal1").show();
  };

  function externalFiles(event) {
    var id = event.target.id;
    var i = id.split('_').pop();

    var externalFile = $('#externalresume_' + i).prop('files')[0];
    var extension = externalFile.name.split('.').pop();
    var sizeofdoc = externalFile.size;
    var ext = externalFile.name.split('.').pop();
    if ((ext == "pdf" || ext == "docx" || ext == "doc" || ext == "PDF" || ext == "DOCX" || ext == "DOC") && sizeofdoc <
      5000000) {
      $("#externalresume_" + i).css("border", "solid 1px #d5d5d5");
      $("#externalspan_" + i).hide();
    } else {
      alert("Upload only doc or pdf files with size less than 5MB");
      input = $('#externalresume_' + i);
      input.val('').clone(true);
    }
  }
  function clearExtnernalValidation(event) {
    var id = event.target.id;
    var i = id.split('_').pop();
    var externalName = $("#externalName_" + i).val().trim();
    if (null != externalName) {
      $("#externalName_" + i).css("border", "solid 1px #d5d5d5");
      $("#externalName_span_" + i).hide();
    }
    var location = $("#location_" + i).val().trim();
    if (null != location) {
      $("#location_" + i).css("border", "solid 1px #d5d5d5");
      $("#location_span_" + i).hide();
    }
    var noticePeriod = $("#noticePeriod_" + i).val().trim();
    if (null != noticePeriod) {
      $("#noticePeriod_" + i).css("border", "solid 1px #d5d5d5");
      $("#noticePeriod_span_" + i).hide();
    }
    var emailId = $("#emailId_" + i).val().trim();
    if (null != emailId) {
      $("#emailId_" + i).css("border", "solid 1px #d5d5d5");
      $("#emailId_span_" + i).hide();
    }
    var totalExperience = $("#totalExperience_" + i).val().trim();
    if (null != totalExperience) {
      $("#totalExperience_" + i).css("border", "solid 1px #d5d5d5");
      $("#totalExperience_span_" + i).hide();
    }
    var skills = $("#skills_" + i).val().trim();
    if (null != skills) {
      $("#skills_" + i).css("border", "solid 1px #d5d5d5");
      $("#skills_span_" + i).hide();
    }
    var contactNumber = $("#contactNumber_" + i).val().trim();
    if (null != contactNumber) {
      $("#contactNumber_" + i).css("border", "solid 1px #d5d5d5");
      $("#contactNumber_span_" + i).hide();
    }
  }


  finalExternalNamesArr = [];
  externalNamesArr = [];
  tempNamesarr = [];
  
  var resourceId = "";





  $(document).ready(function () {
		$('#interviewdate').datepicker();

    $("#resourceBlocked").select2({
      tags: true,
      openOnEnter: false,
      ajax: {
        url: "/rms/requestsReports/activeUserList",
        dataType: 'json',
        data: function (params) {
          return {
            userInput: params.term || '',
          };
        },
        processResults: function (data, params) {
          return {
            results: formatData1(JSON.parse(data.activeUserList)),
          };
        },
      },
      minimumInputLength: 3,
      allowClear: true,
      createTag: function (params) {
        return undefined;
      },
      placeholder: 'Select Resource..',
    });
    $("#resourceBlocked1").select2({
      tags: true,
      openOnEnter: false,
      ajax: {
        url: "/rms/requestsReports/activeUserList",
        dataType: 'json',
        data: function (params) {
          return {
            userInput: params.term || '',
          };
        },
        processResults: function (data, params) {
          return {
            results: formatData2(JSON.parse(data.activeUserList)),
          };
        },
      },
      minimumInputLength: 3,
      allowClear: true,
      createTag: function (params) {
        return undefined;
      },
      placeholder: 'Select Users..',
    });
    $("#internalUserIds").select2({
      tags: true,
      openOnEnter: false,
      ajax: {
        url: "/rms/requestsReports/activeUserList",
        dataType: 'json',
        data: function (params) {
          return {
            userInput: params.term || '',
          };
        },
        processResults: function (data, params) {
          return {
            results: formatData3(JSON.parse(data.activeUserList)),
          };
        },
      },
      minimumInputLength: 3,
      allowClear: true,
      createTag: function (params) {
        return undefined;
      }
    });
    
    $("#contactPerson").select2({
        tags: true,
        openOnEnter: false,
        ajax: {
          url: "/rms/requestsReports/activeUserList",
          dataType: 'json',
          data: function (params) {
            return {
              userInput: params.term || '',
            };
          },
          processResults: function (data, params) {
            return {
              results: formatData4(JSON.parse(data.activeUserList)),
            };
          },
        },
        minimumInputLength: 3,
        allowClear: true,
        createTag: function (params) {
          return undefined;
        }
      });
    
    $("#rrfids").select2({
        openOnEnter: false,
        ajax: {
          url: "/rms/requestsReports/activeRRFsList",
          dataType: 'json',
          data: function (params) {
            return {
              userInput: params.term || '',
            };
          },
          processResults: function (data, params) {
            return {
              results: formatDataForRRFIds(JSON.parse(data.activeUserList)),
            };
          },
        },
        minimumInputLength: 3,
        allowClear: true,
        createTag: function (params) {
          return undefined;
        },
        placeholder: 'Select RRF To Map..',
      });
    
    function formatDataForRRFIds(userList) {
        $.map(userList, function (item, idx) {
        	item.id = item.id;
            item.text = item.requirementId ;
        });

        return userList;
      }
    function formatData1(userList) {
      $.map(userList, function (item, idx) {
        item.id = item.employeeId + "/" + item.uploadResumeFileName + "/" + item.emailId + "/" + item
          .contactNumber + "/" + item.totalExper + "/" + item.competency.skill;
        item.text = item.firstName + " " + item.lastName + "- (" + item.yashEmpId + ")";
      });

      return userList;
    }

    function formatData2(userList) {
      $.map(userList, function (item, idx) {
        item.id = item.employeeId;
        item.text = item.firstName + " " + item.lastName + "- (" + item.yashEmpId + ")";
      });

     return userList;
    }

    function formatData3(userList) {
      $.map(userList, function (item, idx) {
        item.id = item.emailId;
        item.text = item.firstName + " " + item.lastName;
      });

      return userList;
    }
    function formatData4(userList) {
        $.map(userList, function (item, idx) {
          item.id = item.emailId;
          item.text = item.firstName + " " + item.lastName;
        });

        return userList;
      }

    /* $('#internalUserIds').multiselect({
                   id: 'internalUserIds',
    }).multiselectfilter(); */

    $('#notifyTo1').prop('checked', true); //on load keep checkboxes checked
    $('#requestSentTo1').prop('checked', true);
    $('#pdl1').prop('checked', true);

    $(".notify").toggle(true);
    $(".request").toggle(true);
    $(".pdlGroup").toggle(true);


    $('.commentClassForAjax').on('click', function () {
      //alert("commentClassForAjax");
      resourceId = $(this).closest('tbody tr').find('input[type="hidden"]').val();
      var resourceName = $(this).closest('tbody tr').find('td:eq(1)').text();
      $('#getResultDiv').html('');
      $.ajax({
        type: 'GET',
        url: 'loadResourceCommentByResourceId',
        dataType: "text",
        data: {
          "resourceId": resourceId
        },
        success: function (response) {
          arrob = JSON.parse(response);
          var comment = "";
          //var customer = "- Customer with Id = " + i + ", firstname = " + customer.firstname + ", lastName = " + customer.lastname + "<br>";     
          $.each(arrob, function (intIndex, objComment) {
            comment = "<div class='commentText'><p>" + objComment.resourceComment +
              "</p> <footer>From " + objComment.commentBy + " : " + " " + objComment.comment_Date +
              "</footer></div>";
            $('#getResultDiv').append(comment)
          });
          var requirementID = $("#requirementID").val();
          //alert(requirementID);
          $("#reqNameId").text($('.ReqID').text().split(':')[1] + " :: " + resourceName); //+ resourceId
          $("#myModal1").removeClass("fade");
          $("#myModal1").show();
        },
        error: function (response) {
          //stopProgress();
          //showError("Something wrong happened!!");
        },
        complete: function () {
          $('.commentmodal .modal-body').scrollTop($('.commentmodal .modal-body')[0].scrollHeight);
          $('#usr').focus();
        }
      });
    });

    
    $('#mapButton').on('click', function () {
    	skillRequestId = $('#skillReqId').val();
    	var newRRFId = $('#rrfids').val();
    	var resourceIdToMap =  $('#selectResourceId').val();
    	var data =  {
    			"skillRequestId" : skillRequestId, 
	        	"newRRFId" : newRRFId, 
	        	"resourceIdToMap" : resourceIdToMap
    	}
    	  $.ajax({
    		  type: "POST",
    	        url: 'mapResourceToRRF',
    	        data: JSON.stringify(data),
    	        contentType: false,
    	        cache: false,
    	        success: function () {
    	          stopProgress();
    	          showSuccess("Data saved Successfully!");
    	          //$("#myModal").modal('hide');
    	          setTimeout(function () {
    	            window.location.reload();
    	          }, 1000);
    	          //$('.sk-fading-circle .sk-circle').show();
    	        },
    	        error: function (e) {
    	          stopProgress();
    	          showSuccess("Your Data is Saved Successfully!");
    	          //$("#myModal").modal('hide');
    	          setTimeout(function () {
    	            window.location.reload();
    	          }, 1000);
    	          //$('.sk-fading-circle .sk-circle').show();
    	        }
    	      });
    });

    $("#strCSubmit").on('click', function () {

      var strComment = $("#usr").val().trim();
      var intResourceId = resourceId; //$("#reqNameId").text().split("::")[1];

      var resourceComment = {
        "resourceComment": strComment,
        "resourceId": intResourceId
      }

      if ($("#usr").val() != "") {
        if ($("#usr").val().length <= 500) {

          $.ajax({
            type: "POST",
            url: 'postResourceComment',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(resourceComment),
            success: function () {
              //showSuccess("Your Comment is Saved Successfully!");                             
            },
            complete: function () {
              callGetAjax(intResourceId);
            }
          });
          //showSuccess("Your Comment is Saved Successfully!");            
        } else
          showError("Please enter character less than or equal to 500 characters");
      } else
        showError("Please add comment!");
      $("#strCSubmit").attr('disabled', true);
      setTimeout(function () {
        $("#strCSubmit").attr('disabled', false);
        $('.toast-close').click();
      }, 2000);

      $("#usr").val('');


    });



    // close popup comment box.
    $("#clsComment").click(function (event) {
      document.getElementById("strCSubmit").disabled = false;
      $("#usr").val('');
      //$("#myModal1").addClass("fade");
      $("#myModal1").hide();
    });



  });




  var callGetAjax = function (id) {
    var resourceId = id;
    var resourceName = $('#reqNameId').text().split(':: ')[1].trim();
    $('#getResultDiv').html('');
    $.ajax({
      type: 'GET',
      url: 'loadResourceCommentByResourceId',
      dataType: "text",
      data: {
        "resourceId": resourceId
      },
      success: function (response) {
        arrob = JSON.parse(response);
        var comment = "";
        //var customer = "- Customer with Id = " + i + ", firstname = " + customer.firstname + ", lastName = " + customer.lastname + "<br>";              
        $.each(arrob, function (intIndex, objComment) {
          comment = "<div class='commentText'><p>" + objComment.resourceComment + "</p> <footer>From " +
            objComment.commentBy + " : " + " " + objComment.comment_Date + "</footer></div>";
          $('#getResultDiv').append(comment)
        });
        var requirementID = $("#requirementID").val();
        //alert(requirementID);
        $("#reqNameId").text($('.ReqID').text().split(':')[1] + " :: " + resourceName); //+ resourceId
        $("#myModal1").removeClass("fade");
        $("#myModal1").show();
      },
      error: function (response) {
        //stopProgress();
        //showError("Something wrong happened!!");
      },
      complete: function () {
        $('.commentmodal .modal-body').scrollTop($('.commentmodal .modal-body')[0].scrollHeight);
        $('#usr').focus();
      }
    });
  }




















  $(document).ready(function () {
    $("#saveResource").click(function (event) {
      var arrPostData = new Array();
      //stop submit the form, we will post it manually.          
      event.preventDefault();
      var fileinputs = $("#add_internal_table").find("input[type=file]");
      var isError = false;
      if (fileinputs.length > 0) {
        var resourceID = $(".resourceid").map(function () {
          return $(this).data('index');
        }).get();
        for (var j = 0; j < fileinputs.length; j++) {
          var i = resourceID[j];
          var file1 = $('#file_' + i).prop('files');
          // var file1 = fileinputs[i];
          if (file1 == null || typeof file1[0] === 'undefined') {
            arrPostData.push({
              'resourceIds': $("#resource" + i).val(),
              'emailId': $("#emailId_" + i).val(),
              'totalExperience': $("#totalExperience_" + i).val(),
              'skills': $("#skills_" + i).val(),
              'contactNumber': $("#contactNumber_" + i).val(),
              'file': file1,
              'resourceType': "I"
            });
            if ($("#file_" + i).val() == "" && $("#hiddenfile" + i).val() == "") {
              $("#file_" + i).css("border-color", "red");
              $("#internalspan_" + i).show();
              isError = true;
            }
          } else {
            var file = $('#file_' + i).prop('files');
            arrPostData.push({
              'resourceIds': $("#resource" + i).val(),
              'emailId': $("#emailId_" + i).val(),
              'totalExperience': $("#totalExperience_" + i).val(),
              'skills': $("#skills_" + i).val(),
              'contactNumber': $("#contactNumber_" + i).val(),
              'file': file,
              'resourceType': "I"
            });
          }
        }
      }

      //  var tacfileinputs = $("#add_external_table").find("input.tac[type=file]");

      /* if(tacfileinputs.length > 0){
                 for(var i = 0; i< tacfileinputs.length ; i++){
                                 var tacFile =$('#tac_'+i).prop('files')[0];
                                 
                                arrPostData.push({ 'resourceIds' : $("#resource"+i).val() , 'file': tacFile ,'resourceType':"E", 'tacType':"T" , 'externalResourceName':$("#externalName_"+i).val()});
                                
                                 if( $("#tac_"+i).val()==""){
                                               $("#tac_"+i).css("border-color", "red");
                                               $("#externaltac_"+i).show();
                                                isError = true;
                                } 
                                
                 }
       }*/


      //External resource
      var externalFileinputs = $("#add_external_table").find('input.file[type=file]');
      if (externalFileinputs.length > 0) {
        for (var i = 0; i < externalFileinputs.length; i++) {
          var externalFile = $('#externalresume_' + i).prop('files')[0];
          var tacFile = $('#tac_' + i).prop('files')[0];
          var regExpEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
          var regExpExperience = /^\s*([0-9]{0,2})?([\.][0-9]{0,2})?\s*$/;
          var toCheckPrepandRegex = /^([\.])([0-9]{0,2})\s*$/;
          var toDropPostDot = /^\s*([0-9]{0,2})([\.])\s*$/;
          var onlyDot = /^\.$/;

          var regExpPhone =
          /^\s*([0|\+[0-9]{1,5})?(-{0,1})?(\s*)?([0-9]{10})\s*$/; ///^\+(?:[0-9] ?){6,14}[0-9]$/;///([+]?\d{1,2}[.-\s]?)?(\d{3}[.-]?){2}\d{4}/;

          if ($("#totalExperience_" + i).val()) {
            var trimmedValue = $("#totalExperience_" + i).val().trim();
            if (!trimmedValue.match(regExpExperience)) {

              $("#totalExperience_" + i).css("border", "solid 1px red");
              $("#totalExperience_span_" + i).show();
              isError = true;
            } else {
              if (trimmedValue.match(onlyDot)) {
                $("#totalExperience_" + i).css("border", "solid 1px red");
                $("#totalExperience_span_" + i).show();
                isError = true;
              } else if (trimmedValue.match(toDropPostDot)) {
                var numS = trimmedValue.toString();
                var decPos = numS.indexOf('.');
                var trimmedResult = numS.substr(0, decPos);
                $("#totalExperience_" + i).val(trimmedResult);
                $("#totalExperience_" + i).css("border", "solid 1px #cccccc");
                $("#totalExperience_span_" + i).hide();

              } else {

                var prePandVal = '0' + trimmedValue;
                if (trimmedValue.match(toCheckPrepandRegex)) {
                  $("#totalExperience_" + i).val(prePandVal);
                }
                $("#totalExperience_" + i).css("border", "solid 1px #cccccc");
                $("#totalExperience_span_" + i).hide();
              }
            }
          }
          arrPostData.push({
            'file': externalFile,
            'resourceType': "E",
            'tacfile': tacFile,
            'externalResourceName': $("#externalName_" + i).val().trim(),
            'location': $("#location_" + i).val().trim(),
            'noticePeriod': $("#noticePeriod_" + i).val().trim(),
            'emailId': $("#emailId_" + i).val().trim(),
            'totalExperience': $("#totalExperience_" + i).val().trim(),
            'skills': $("#skills_" + i).val().trim(),
            'contactNumber': $("#contactNumber_" + i).val().trim()
          });
          if ($("#externalresume_" + i).val().trim() == "") {
            $("#externalresume_" + i).css("border", "solid 1px red");
            $("#externalspan_" + i).show();

            isError = true;
          }

          if ($("#emailId_" + i).val()) {
            if (!$("#emailId_" + i).val().trim().match(regExpEmail)) {

              $("#emailId_" + i).css("border", "solid 1px red");
              $("#emailId_span_" + i).show();
              isError = true;
            } else {
              $("#emailId_" + i).css("border", "solid 1px #cccccc");
              $("#emailId_span_" + i).hide();
            }
          }


          if ($("#contactNumber_" + i).val()) {
            if (!$("#contactNumber_" + i).val().trim().match(regExpPhone)) {

              $("#contactNumber_" + i).css("border", "solid 1px red");
              $("#contactNumber_span_" + i).show();
              isError = true;
            } else {
              $("#contactNumber_" + i).css("border", "solid 1px #cccccc");
              $("#contactNumber_span_" + i).hide();
            }
          }

          if ($("#tac_" + i).val() == "") {
            $("#tac_" + i).css("border", "solid 1px red");
            $("#externaltac_" + i).show();

           isError = true;
          }


          if ($("#externalName_" + i).val().trim() == "") {
            $("#externalName_" + i).css("border", "solid 1px red");
            $("#externalName_span_" + i).show();

            isError = true;
          }
        }

      }
      if (isError == true) {
        return;
      } else {
        fire_ajax_submit(arrPostData);
        $("#myModal").modal('hide');
      }
    });
  });

  function fire_ajax_submit(fileArr) {
    var form = $('#formFile')[0];
    var data = new FormData(form);
    if (fileArr.length > 0) {
      for (i = 0; i < fileArr.length; i++) { //to append multiple resume files on click.
        var file = null;
        if (typeof fileArr[i].file[0] != "undefined") {
          file = (fileArr[i].file[0]) ? fileArr[i].file[0] : null;
        } else if (typeof fileArr[i].file != "array") {
          file = (typeof fileArr[i].file != "undefined") ? fileArr[i].file : null;
        }
        data.append("resourceFileDTO[" + i + "].file", file);
        data.append("resourceFileDTO[" + i + "].resourceIds", fileArr[i].resourceIds);
        data.append("resourceFileDTO[" + i + "].resourceType", fileArr[i].resourceType);
        data.append("resourceFileDTO[" + i + "].tacFile", fileArr[i].tacfile);
        data.append("resourceFileDTO[" + i + "].externalResourceName", fileArr[i].externalResourceName);
        data.append("resourceFileDTO[" + i + "].location", fileArr[i].location);
        data.append("resourceFileDTO[" + i + "].noticePeriod", fileArr[i].noticePeriod);
        data.append("resourceFileDTO[" + i + "].emailId", fileArr[i].emailId);
        data.append("resourceFileDTO[" + i + "].totalExperience", fileArr[i].totalExperience);
        data.append("resourceFileDTO[" + i + "].skills", fileArr[i].skills);
        data.append("resourceFileDTO[" + i + "].contactNumber", fileArr[i].contactNumber);
      }
      startProgress();
      $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "saveRequest",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        //timeout: 600000,
        success: function () {
          stopProgress();
          showSuccess("Data saved Successfully!");
          //$("#myModal").modal('hide');
          setTimeout(function () {
            window.location.reload();
          }, 1000);
          //$('.sk-fading-circle .sk-circle').show();
        },
        error: function (e) {
          stopProgress();
          showSuccess("Your Data is Saved Successfully!");
          //$("#myModal").modal('hide');
          setTimeout(function () {
            window.location.reload();
          }, 1000);
          //$('.sk-fading-circle .sk-circle').show();
        }
      });
    } else {
      showError("Please submit atleast one Profile to Proceed Further");
    }
  }

  $(window).load(function () {
    $('.sk-fading-circle .sk-circle').hide();
  });

  function fileDownload(id, resourceType) {
    $.ajax({
      url: 'downloadfiles',
      contentType: "application/json",
      async: false,
      data: {
        "id": id,
        "resourceType": resourceType
      },
      success: function (response) {
        if (response == "") {

          showAlert("\u2022 File not found for id " + id + " ! <br />");

        } else {
          if (id !== '') {
            window.location.href = 'downloadfiles?id=' + id + '&resourceType=' + resourceType;
          } else {
            showAlert("\u2022 File not found for id " + id + " ! <br />");
          }
        }
      },
      error: function (data) {
        showAlert("\u2022 File not found for id " + id + " ! <br />");
      }
    })
  }
  function tacDownload(id) {
    $.ajax({
      url: 'downloadTac',
      contentType: "application/json",
      async: false,
      data: {
        "id": id
      },
      success: function (response) {
        if (response == "") {

          showAlert("\u2022 File not found for id " + id + " ! <br />");

        } else {
          if (id !== '') {
            window.location.href = 'downloadTac?id=' + id;
          } else {
            showAlert("\u2022 File not found for id " + id + " ! <br />");
          }
        }
      },
      error: function (data) {
        showAlert("\u2022 File not found for id " + id + " ! <br />");
      }
    })
  }
  function showDeletePopup(id, skillId, row) {
    $('#deletePopup1').attr('data-resource-id', id);
    $('#deletePopup1').modal('show');
    $(".close-btn1").click(function () {
      $('#deletePopup1').modal('hide');

    });
  }

  function deleteData(id, skillId) {
    var element = $(this);
    var idValue = id;
    var info = 'id=' + idValue;
    startProgress();
    $.ajax({
      type: 'POST',
      url: 'deleteSkillRequestResource',
      dataType: "text",
      data: {
        "id": idValue,
        "skillId": skillId
      },
      success: function (data) {
        $("#delete-res-" + id).parent().parent().remove();
        showSuccess("Resource removed Successfully!");
        stopProgress();
        $('#deletePopup1').modal('hide');
      },
      error: function (response) {
        stopProgress();
        showError("Something wrong happened!!");
        $('#deletePopup1').modal('hide');
      },
    });
    return false;
  }


  //Ragini
  $(document).ready(function () {

    //Start - 29April as per new requirement Delete should be available for Joined status the below code is not required anymore
    //15 May - reverting back for now
    $("#updateResouceForm #stTypeInternal").each(function (index) {
      if ($(this).val() === "8") {
        //$(this).closest('tr').find('.btn-danger').prop("disabled", true);
        $(this).closest('tr').find('.btn-danger').hide();
      } //else{
      //$(this).closest('tr').find('.btn-danger').show();
      //}
    });
    //Display join date on loading window
    $('.allocated_resource_table > tbody').find('tr').each(function (i, el) {
      var strResourceType = $(this).find(".resourcetype").text().trim();
      var intTypeId = $(this).find("#stTypeInternal option:selected").val();
      if (intTypeId == 8) {
        // $(this).find('.fromdatepicker').show();
        $(this).find("#stTypeInternal").prop("disabled", true);
      }
    });
    $("select[id='stTypeInternal']").each(function () {
      var selected = $(this).val();
      if (selected == 8) {
        $(this).parent().parent().find('.JDate').show();
        $(this).parent().parent().find('.IDate').show();
      } else if (selected == 14) {
        $(this).parent().parent().find('.JDate').hide();
        $(this).parent().parent().find('.IDate').show();
      } else {
        $(this).parent().parent().find('.JDate').hide();
        $(this).parent().parent().find('.IDate').hide();
      }
    });
    
    $('#mailTo').on ('change', function (){
  	  
  	  if($('#mailTo').prop("checked") == true){
  		  $('#mailtoTextArea').val(mailtodata);
  	  } else {
  		  $('#mailtoTextArea').val('');
  	  }
  	
  	});
    
  $('#pdlsTo').on ('change', function (){
  	  
  	  if($('#pdlsTo').prop("checked") == true){
  		  $('#pdlTextArea').val(pdldata);
  	  } else {
  		  $('#pdlTextArea').val('');
  	  }
  	
  	});
  	
  $('#notifyTo').on ('change', function (){
  	  if($('#notifyTo').prop("checked") == true){
  		  $('#notifyToTextArea').val(notifydata);
  	  } else {
  		  $('#notifyToTextArea').val('');
  	  }
  	
  	});
  });
  $(document).on('change', 'select[id="stTypeInternal"]', function () {
    var selected = $(this).val();
    if (selected == 2) {
      $(this).parent().parent().find('.joinDate').show();
      $(this).parent().parent().find('.interviewDate').hide();
    } else if (selected == 9) {
      $(this).parent().parent().find('.joinDate').hide();
      $(this).parent().parent().find('.interviewDate').show();
    } else {
      $(this).parent().parent().find('.joinDate').hide();
      $(this).parent().parent().find('.interviewDate').hide();
    }
    if (selected == 8) {
      $(this).parent().parent().find('.JDate').show();
      $(this).parent().parent().find('.IDate').show();
    } else if (selected == 14) {
      $(this).parent().parent().find('.JDate').hide();
      $(this).parent().parent().find('.IDate').show();
    } else {
      $(this).parent().parent().find('.JDate').hide();
      $(this).parent().parent().find('.IDate').hide();
    }
  });
  //Ragini
var selectedResourceId;
  function changStatusType(sel, resourceId) {
	  $('#mapResourceDiv').css('display', 'none');
    $("#updateResourceStatus").attr("disabled", false);
    var inputResourceType = $(sel).closest('td').siblings().find('.resourcetype').text().trim();

    /* if(sel.value == 4)
                {
                                $(jQuery(sel).parent().parent()).each(function (i, row) {
                                                  var tableRow = $(row);
                                                  if(tableRow.find('#stTypeInternal').val() != 0){
                                                                 tableRow.find('.fromdatepicker').show(); 
                                                                  tableRow.find('#allocationIdInternal').show(); 
                                                  }
                                     });
                }else */
    if (sel.value == 8 && inputResourceType == 'External') {
      $(jQuery(sel).parent().parent()).each(function (i, row) {
        var tableRow = $(row);
        //tableRow.find('#stTypeInternal').prop("disabled", true)
        if (tableRow.find('#stTypeInternal').val() != 0) {
          tableRow.find('.fromdatepicker').show();
        }
      });
    }
    if (sel.value == 2) {
      $(jQuery(sel).parent().parent()).each(function (i, row) {
        var tableRow = $(row);
        //tableRow.find('#stTypeInternal').prop("disabled", true)
        if (tableRow.find('#stTypeInternal').val() != 0) {
          tableRow.find('.fromdatepicker').show();
        }
      });
    }
    if (sel.value == 21  ) {
    	  $('#selectResourceId').val(resourceId);
    	  $('#mapResourceDiv').css('display', 'block');
        $(jQuery(sel).parent().parent()).each(function (i, row) {
          var tableRow = $(row);
          //tableRow.find('#stTypeInternal').prop("disabled", true)
          if (tableRow.find('#stTypeInternal').val() != 0) {
            tableRow.find('.fromdatepicker').show();
          }
        });
      }
    if (sel.value == 9) {
      $(jQuery(sel).parent().parent()).each(function (i, row) {
        var tableRow = $(row);
        //tableRow.find('#stTypeInternal').prop("disabled", true)
        if (tableRow.find('#stTypeInternal').val() != 0) {
          tableRow.find('.fromdatepicker').show();
        }
      });
    } else {
      $(jQuery(sel).parent().parent()).each(function (i, row) {
        var tableRow = $(row);
        tableRow.find('.fromdatepicker').hide();
        tableRow.find('#allocationIdInternal').hide();
      });
    }

  }

  $('.fromdatepicker').datepicker({
    beforeShow: function () {}
  });
  function updateResourceStatus() {
    if ($("#InternalResourceText").val().length > 0 || $('input[name="externalButtonAdd[]"]').length > 0) {
      if (!confirm("Do you want to continue without save resource")) {
        $("#saveResource").css("border-color", "#b42020");
        return false;
      }
    }
    //if(confirm("Sure you want to update Resource status? There is NO undo!"))
    if (confirm) {
      $('#updateResourceStatusPopup').modal('show');
      $("#updateSatusFinalBtn").click(function () {
        $("#updateResouceForm").submit();
      });
    }
  }
  function updateResourceStatus1() {
    if ($("#InternalResourceText").val().length > 0 || $('input[name="externalButtonAdd[]"]').length > 0) {
      if (!confirm("Do you want to continue without save resource")) {
        $("#saveResource").css("border-color", "#b42020");
        return false;
      }
    }
    if (confirm("Sure you want to update Resource status? There is NO undo!")) {
      var requirements = {
        parameters: [],
      };
      requestSkillResourceId = [];
      var statusType = [];
      var allocationId = [];
      var resourceId = [];
      var allocationDate = [];
      var interviewId = [];
      var interviewDate = [];
      var location = [];
      var noticePeriod = [];
      var emailId = [];
      var totalExperience = [];
      var skills = [];
      var contactNumber = [];
      skillId = window.skillReqId.value;
      reuestId = window.reqId.value;
      projId = $("#projectId").val()

      $('.allocated_resource_table > tbody tr').each(function (i, row) {
        var tableRow = $(row);

        if (tableRow.find('input[name*="skillResourceId"]').val() != null) {
          requestSkillResourceId.push(tableRow.find('input[name*="skillResourceId"]').val());
        }

        if (tableRow.find('select[name*="stType"]').val() != null) {
          statusType.push(tableRow.find('select[name*="stType"]').val());
        }
        if (tableRow.find('select[name*="allocationId"]').val() != null) {
          allocationId.push(tableRow.find('select[name*="allocationId"]').val());
          allocationDate.push(JSON.stringify(tableRow.find('input[name*="weekEndDateSel"]').val()));
        }
        if (tableRow.find('select[name*="interviewId"]').val() != null) {
          interviewId.push(tableRow.find('select[name*="interviewId"]').val());
          interviewDate.push(JSON.stringify(tableRow.find('input[name*="weekEndDateSel1"]').val()));
        }
        if (tableRow.find('input[name*="skillResourceNameId"]').val() != null && tableRow.find(
            'input[name*="skillResourceNameId"]').val() != "") {
          resourceId.push(tableRow.find('input[name*="skillResourceNameId"]').val());
        }
        if (tableRow.find('input[name*="location"]').val() != null && tableRow.find('input[name*="location"]')
        .val() != "") {
          location.push(tableRow.find('input[name*="location"]').val());
        }
        if (tableRow.find('input[name*="noticePeriod"]').val() != null && tableRow.find(
            'input[name*="noticePeriod"]').val() != "") {
          noticePeriod.push(tableRow.find('input[name*="noticePeriod"]').val());
        }
        if (tableRow.find('input[name*="emailId"]').val() != null && tableRow.find('input[name*="emailId"]')
        .val() != "") {
          location.push(tableRow.find('input[name*="emailId"]').val());
        }
        if (tableRow.find('input[name*="totalExperience"]').val() != null && tableRow.find(
            'input[name*="totalExperience"]').val() != "") {
          noticePeriod.push(tableRow.find('input[name*="totalExperience"]').val());
        }
        if (tableRow.find('input[name*="skills"]').val() != null && tableRow.find('input[name*="skills"]').val() !=
          "") {
          location.push(tableRow.find('input[name*="skills"]').val());
        }
        if (tableRow.find('input[name*="contactNumber"]').val() != null && tableRow.find(
            'input[name*="contactNumber"]').val() != "") {
          noticePeriod.push(tableRow.find('input[name*="contactNumber"]').val());
        }
      });
      requirements.parameters.push({
        "reqId": reuestId,
        "skillReqId": skillId,
        "projectId": projId,
        "requestResourceId": requestSkillResourceId,
        "statusType": statusType,
        "allocationId": allocationId,
        "resourceId": resourceId,
        "allocationDate": allocationDate,
        "interviewDate": interviewDate,
        "location": location,
        "noticePeriod": noticePeriod,
        "emailId": emailId,
        "totalExperience": totalExperience,
        "skills": skills,
        "contactNumber": contactNumber
      });
      var arrayLength = statusType.length;
      var count = 0;
      /*  if(arrayLength>resourcesRequired){
       for (var i = 0; i < arrayLength; i++) {
           if(statusType[i]==4||statusType[i]==12||statusType[i]==22)
           {
               count++;
           }
       }
       if(count>resourcesRequired){
               if(remainingResources==1)
               {
                               alert("Resource requisition limit exceeds, need to fulfill request for only "+remainingResources+" resource.");
              }
               else if(remainingResources==0)
               {
                               alert("Resource requisition is completed.");
                               
               }
               else{
                               
                               alert("Resource requisition limit exceeds, need to fulfill request for only "+remainingResources+" resources.");
               }
              return false;
              }
       } */
      startProgress();
      $.ajax({
        type: 'POST',
        url: 'updateResourceRequestWithStatus',
        contentType: "application/json; charset=utf-8",
        data: $("#updateResouceForm").serialize(),
        cache: false,
        async: false,
        dataType: 'text',
        success: function (data) {
          stopProgress();
          showSuccess("Updated Successfully! ");
          setTimeout(function () {
            window.location.reload();
          }, 1000);
        },
        error: function (error) {
          stopProgress();
          showError("Something happend wrong!!");
          //window.location.reload();
        }
      });
    }
  }

  $(document).on("click", ".checkId", function () {

    if ((this).value == 1) {
      $(".notify").toggle(this.checked);
    }
    if ((this).value == 2) {
      $(".request").toggle(this.checked);
    }
    if ((this).value == 3) {
      $(".pdlGroup").toggle(this.checked);
    }
    if ($(".request").is(":visible")) {
      $(".pdlGroup").css("height", $(".request").height());
    } else {
      if ($(".notify").is(":visible")) {
        $(".pdlGroup").css("height", $(".notify").height());
      } else
        $(".pdlGroup").css("height", '55px');
    }

  });


  $(function () {
    $(".datepicker").datepicker();

  });

  function backToDashBoard() {
    $(this).attr('target', '_blank');
    window.location = "../requestsReports/positionDashboard";
  }

  function resetPopup() {
    $("#saveResource").attr("disabled", true);

    /*  $('#resourceBlocked option:selected').each(function() {
                                                   $(this).prop('selected', false);
                                               });
                                               $('#resourceBlocked').multiselect('refresh'); */

    $("#add_internal_table").find("tr").remove();
    $("#add_external_table").find("tr").remove();
    $("#selection-table").hide();
    $(".externalTable").hide();
  }

  $(document).on("click", ".sendEmailclass", function () {

    if ((this).value == "notifyTo") {
      $(".notify").toggle(this.checked);
    }
    if ((this).value == 2) {
      $(".request").toggle(this.checked);
    }
    if ((this).value == 3) {
      $(".pdlGroup").toggle(this.checked);
    }
    if ($(".request").is(":visible")) {
      $(".pdlGroup").css("height", $(".request").height());
    } else {
      if ($(".notify").is(":visible")) {
        $(".pdlGroup").css("height", $(".notify").height());
      } else
        $(".pdlGroup").css("height", '55px');
    }

  });

  function confirmPopup() {
    if ($("#add_internal_table").find('tr').length > 0 || $("#add_external_table").find('tr').length > 0) {
      if (confirm("Do you want to cancel without save resource")) {
        $('#myModal').modal('hide');
      } else {
        return false;
      }
    } else {
      $('#myModal').modal('hide');
    }


  }

var  mailtodata; 
var  pdldata;
var notifydata;

  function scheduleInterview(skillid, requestResourceId) {
	  
  
    var modal = document.getElementById('interviewModal');
    modal.style.display = "block";
    document.getElementById('requestResourceId').value = requestResourceId;
    $.ajax({
        type: 'GET',
        url: 'getDataForScheduleInterview/'+skillid+'/'+requestResourceId,
       
        cache: false,
        contentType: "application/json; charset=utf-8",
        cache: false,
        success: function (response) {
              var parsedData = JSON.parse(response);
              $('#bgbu').val(parsedData.requestorsBGBU);
              $('#reqid').val(parsedData.requirementID);
              $('#customerName').val(parsedData.customerName);
              $('#location').val(parsedData.jobLocation);
              $('#resourcename').val(parsedData.resourceName);
              $('#employeeid').val(parsedData.resourceEmpId);
              $('#interviewdate').val(parsedData.interviewDate);
              $('#interviewtime').val(parsedData.interviewTime);
              $('#interviewmode').val(parsedData.interviewMode);
              $('#deliveryPOCName').val(parsedData.deliveryPOCName);
              $('#deliveryPOCCont').val(parsedData.deliveryPOCContact);
              $('#amPOCName').val(parsedData.aMPOCName);
              $('#amPOCCont').val(parsedData.aMPOContact);
              $('#custPOCName').val(parsedData.customerPOCName);
              $('#custPOCCont').val(parsedData.customerPOCContact);
              $('#venue').val(parsedData.venue);
              $('#gatenumber').val(parsedData.gatePassNumber);
              $('#skill').val(parsedData.skillCategory);
              $('#jobdesc').val(parsedData.jobDescription);
              mailtodata = parsedData.mailsToMailIds; 
               
              notifydata= parsedData.notifyToMailIds; 
              pdldata  = parsedData.pdlsToMailIds; 
              
  				  $('#mailTo, #notifyTo, #pdlsTo').prop('checked', true).trigger('change');      
         
        },
        error: function (error) {
          showError("Something went wrong!!");
        }
      });
    
    
  }
  function closeInterviewPopup() {
    $("#internalUserIds option:selected").prop("selected", false);
    $("#contactPerson option:selected").prop("selected", false);
    $('#mailTo').prop('checked', false);
    $('#notifyTo').prop('checked', false);
    $('#pdlsTo').prop('checked', false);
    $("#internalUserIds").multiselect('refresh');
    $("#contactPerson").multiselect('refresh');
    // document.getElementById("forward-btn").disabled = false;
    var modal = document.getElementById('interviewModal');
    $('#mailText').val('');
    modal.style.display = "none";

    $('#internalUserIds').val(null).trigger('change');
    $('#contactPerson').val(null).trigger('change');
  }

  $(document).on('change', '#discussionType', function () {
	  var value = $('#discussionType').val();
	  if(value == 'yashInternal'){
		  $('#contactPersonDiv').css("display", "block" );
		  $('#contactPersonNumbDiv').css( "display", "block" );
		  
		  $('#custPOCNameDiv').hide();
		  $('#venueDiv').hide();
		  $('#gatenumberDiv').hide();
		  $('#custPOCContDiv').hide();
	  } else {
		  $('#custPOCNameDiv').show();
		  $('#venueDiv').show();
		  $('#gatenumberDiv').show();
		  $('#custPOCContDiv').show();
		  
		  $('#contactPersonDiv').css("display", "none" );
		  $('#contactPersonNumbDiv').css( "display", "none" );
	  }
	  
  });
  
  
  function onScheduleInterviewSubmit() {
    var internalUserIds = document.getElementById("internalUserIds");
    var notifyTo = document.getElementById("notifyTo");
    var sendMailTo = document.getElementById("mailTo");
    var pdlsTo = document.getElementById("pdlsTo");
    var mailText = document.getElementById("mailText").value;
    var requestResourceId = document.getElementById('requestResourceId').value;
    
    var interviewDate =  $('#interviewdate').val();
    var interviewMode = $('#interviewmode').val();
    var interviewTime = $('#interviewtime').val();
    var amPOCName = $('#amPOCName').val();
    var amPOCCont = $('#amPOCCont').val();
    var custPOCName = $('#custPOCName').val();
    var venue = $('#venue').val();
    var gatenumber = $('#gatenumber').val();
    var jobdesc = $('#jobdesc').val();
       var custPOCCont = $('#custPOCCont').val();
	var discussionType = $('#discussionType').val();
	var contactPersonNumber = $('#contactPersonNumber').val();
    
    var validationFlag = true;
    if (!notifyTo.checked && !sendMailTo.checked && !pdlsTo.checked && !sendMailTo.checked && internalUserIds.value =="") {
      showError("Please select atleast one email address");
      validationFlag = false;
    }
    
    if(discussionType==null){
    	 showError("Please select one discussion Type");
         validationFlag = false;
    }
    
    if(discussionType=="client" && venue==""  ){
    	showError("Please input venue details as the discussion is with client!");
        validationFlag = false;
    }
    if(discussionType=="yashInternal" && $('#contactPerson').val() == null  ){
    	showError("Please input contact person details as the discussion is Internally in Yash!");
        validationFlag = false;
    }
    if(interviewDate==""){
    	showError("Please provide Interview Date to proceed further!");
        validationFlag = false;
    }
    if(interviewTime==""){
    	showError("Please provide Interview Time to proceed further!");
        validationFlag = false;
    }
    if(interviewMode==null){
    	showError("Please provide Interview Mode to proceed further!");
        validationFlag = false;
    }
    
    if (notifyTo.checked) {
      notifyTo = true;
    } else {
      notifyTo = false;
    }
    if (sendMailTo.checked) {
      sendMailTo = true;
    } else {
      sendMailTo = false;
    }
    if (pdlsTo.checked) {
      pdlsTo = true;
    } else {
      pdlsTo = false;
    }
   /*  if (mailText.length <= 0) {
      showError("Email cannot be sent without content");
      validationFlag = false;
    } */
    if (mailText.length >= 5000) {
      showError("Word Limit Exceeds 5000 characters");
      validationFlag = false;
    }
    if(amPOCCont.length>10 ){
        showError("Please enter valid phone number");
         validationFlag = false;
    }
    if(custPOCCont.length>10){
        showError("Please enter valid phone number");
        validationFlag = false;
   }
    

   /*  if (!(/\S/.test(mailText))) {
      showError("Email cannot be sent without content");
      validationFlag = false;
    } */

    var internalUserEmailIds = "";
    $("#internalUserIds option:selected").each(function () {
      var id = $(this).val();
      internalUserEmailIds = id + "," + internalUserEmailIds;
    });
    
    var contactPersonIds = "";
    $("#contactPerson option:selected").each(function () {
      var id = $(this).val();
      contactPersonIds = id + "," + contactPersonIds;
    });
    var data = {
      "userIdList": internalUserEmailIds,
      "notifyTo": notifyTo,
      "sendMailTo": sendMailTo,
      "pdlsTo": pdlsTo,
      "requestResourceId": requestResourceId,
      "mailText": mailText, 
      "jobdesc" : jobdesc,
      "interviewDate" : interviewDate,
      "interviewMode" : interviewMode,
      "interviewTime" : interviewTime,
      "amPOCName" : amPOCName,
      "amPOCCont" : amPOCCont,
      "custPOCName" : custPOCName,
      "venue" : venue,
      "gatenumber" : gatenumber,
      "customerPOCContact" : custPOCCont, 
      "contactPersonIds" : contactPersonIds, 
      "discussionType" : discussionType, 
      "contactPersonNumber" : contactPersonNumber
    }
    if (validationFlag) {
      $.ajax({
        type: 'POST',
        url: 'sendemailforinterview',
        data: JSON.stringify(data),
        cache: false,
        contentType: 'application/json',
        success: function (response) {
          $("#internalUserIds option:selected").prop("selected", false);
          $('#mailTo').prop('checked', false);
          $('#notifyTo').prop('checked', false);
          $('#pdlsTo').prop('checked', false);
          var modal = document.getElementById('interviewModal');
          $('#mailText').val('');
          modal.style.display = "none";
          showSuccess("Email Of The Interview Schedule Sent Successfully!");
          $("#internalUserIds").val(null).trigger('change');
          $("#contactPerson").val(null).trigger('change');
        },
        error: function (error) {
          showError("Something went wrong!!");
        }
      });
    }
    }
</script>

<!-- popup RRF start  -->
<div class="content-wrapper" style="overflow: auto;"
	onload="myFunction()">
	<div class="botMargin">
		<h1 class="ReqID">Submit Resource for :
			${requestRequisitionResourceFormDTO.requestRequisitionResources[0].requirementId}
		</h1>
	</div>
	<div class="modal-content">
		<div class="modal-header">

			<h4 class="modal-title">Add Resource For Skill Request</h4>
			<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
				data-target="#myModal" id="resetBtn" onclick="resetPopup()">
				+ Add Resource</button>
		</div>

		<div id="myModal" class="modal fade" role="dialog">
			<div class="modal-dialog modal-lg" style="width: 84%; margin-top: 6%">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<a href="javascript:void(0);" type="button" class="close"
							onClick="confirmPopup()">&times;</a>
						<h4 class="modal-title">Add Resource For Skill Request</h4>
					</div>
<div class="modal-body">
            <p>
            <div>
              <form:form class="notify-icons-main" style="margin-top:10px" id="formFile"
                modelAttribute="requestRequisitionDashboardInputDTO" enctype="multipart/form-data">
                <table class="request-reporttable well">
                  <tr>
                    <th>Resource Type</th>
                    <th>Below Resources are Blocked/Proposed</th>
                    <!-- sarang change -->
                  </tr>
                  <tr>
                    <th valign="top" class="cust-color-th"><label for="internal">Internal</label></th>
                    <td valign="top">
                      <div id="internal">
                        <div class="row full-width">
                          <div class="full-width blocked-select positionRel" style="width:100%;">
                            <div class="col-md-6">
                              <%-- <form:select id="resourceBlocked" class="required internalResourceClass1" name="users[]"
                                multiple="multiple"
                                path="internalResourceIds"> --%>
                              <form:select id="resourceBlocked" class="commonSelectClass" name="users1[]"
                                data-type="resourceBlocked_1" multiple="multiple" path="internalResourceIds">
                                <%-- <c:forEach var="mail"
                                  items="${requestRequisitionResourceFormDTO.activeUserList}">
                                  <option path="resource.id"
                                                                  value="${mail.employeeId}/${mail.uploadResumeFileName}/${mail.emailId}/${mail.contactNumber}/${mail.totalExper}/${mail.competency.skill}">${mail.firstName} ${mail.lastName}-(${mail.yashEmpId})</option>
                                  </c:forEach> --%>
                              </form:select>
                              <%-- <c:forEach var="hiddenfiles" items="${requestRequisitionResourceFormDTO.activeUserList}"  varStatus="myIndex">
                                <input id="hiddenfileindex_${myIndex.index}" type="file"  value="${hiddenfiles.uploadResume}"/>
                                </c:forEach> --%>
                            </div>
                            <div class="col-md-4">
                              <input id="btnReset" type="button" title="click to upload resume for selected resources"
                                onclick="dynamicTable()" value="Add Internal Resource" style="margin-top: -1px;
                                margin-left: 30px;" />
                            </div>
                            <div style="margin-top: 10px;">
                              <div class="row full-width">
                                <table id="selection-table" class="internalTable" style="display:none;">
                                  <thead>
                                    <tr>
                                      <th>Name</th>
                                      <th>Email</th>
                                      <th>Exp(Total IT Exp)</th>
                                      <th>Skills</th>
                                      <th>Contact</th>
                                      <th class="align-center">Resume</th>
                                      <th>Available Resume</th>
                                      <th>Remove</th>
                                    </tr>
                                  </thead>
                                  <tbody id="add_internal_table">
                                    <tr class="tr_for_hidden">
                                      <div hidden class="new-value">
                                        <textarea id="InternalResourceText" name="InternalResourceText"
                                          class="resourceBlocked_id" readonly></textarea>
                                      </div>
                                    </tr>
                                  </tbody>
                                </table>
                              </div>
                            </div>
                          </div>
                        </div>
                    </td>
                  </tr>
                  <tr>
                  <th class="cust-color-th"><label for="external">External</label></th>
                  <td>
                  <div class="row full-width">
                  <div class="col-md-12">
                  <div class="row full-width">
                  <table id="selection-table" class="externalTable" style="display:none;">
                  <thead>
                  <tr>
                  <th>Name</th>
                  <th>Location</th>
                  <th>Notice(in Days)</th>
                  <th>Email</th>
                  <th>Exp(Total IT Exp)</th>
                  <th>Skills</th>
                  <th>Contact</th>
                  <th>Resume</th>
                  <th>TEF</th>
                  <th>Remove</th>
                  </tr>
                  </thead>
                  <tbody id="add_external_table">
                  </tbody>
                  </table>
                  </div>
                  </div>
                  <div class="col-md-3">
                  <div id="externalResourceId" class="external" name="externalResourceName">
                  <div class="sub-row">
                  <button class="add_field_button"
                    title="Click to add External Resource and upload resume and TEF">Add External
                  Resource </button>
                  </div>
                  </div>
                  </div>
                  </div>
                  </td>
                  </tr>
                  <%-- <tr>
                    <th class="cust-color-th"><label for=comment>Comment</label></th>
                    <td>
                                    <div>
                                                    <div class="comment">
                                                                    <div class="sub-row">
                                                                                    <form:input type="text" id="skillReqComment"
                                                                                                    name="skillReqComment" path="comments" />
                                                                                    <br>
                    
                                                                    </div>
                                                    </div>
                                    </div>
                    </td>
                    </tr> --%>
                  <tr>
                  <th id="mailToCheckBox" class="cust-color-th"><label for=mailTo>Mail To</label></th>
                  <td>
                  <div class="mailTo">
                  <div class="row full-width">
                  <div class="col-md-2 mailToAdj" style="width: 191px;">
                  <form:checkbox value="1" class="checkId" name="checkId" path="notifyTo" />
                  <span class="mailToSpan">Notify To</span>
                  </div>
                  <div class="v-Line"></div>
                  <div class="col-md-2 mailToAdj" style="width: 191px;">
                  <form:checkbox value="2" class="checkId" name="checkId" path="requestSentTo" />
                  <span class="mailToSpan">Request Sent to</span>
                  </div>
                  <div class="v-Line"></div>
                  <div class="col-md-2 mailToAdj" style="width: 189px;">
                  <form:checkbox value="3" class="checkId" name="checkId" path="pdl" />
                  <span class="mailToSpan">PDL</span>
                  </div>
                  <div class="v-Line"></div>
                  <div class="col-md-3">
                  Other Users to Notify (if any):
                  <div>
                  <div id="internal">
                  <div class="row full-width">
                  <div class="col-md-3 blocked-select positionRel">
                  <%-- <form:select id="resourceBlocked1" class="required"
                    multiple="multiple" path="extraMailTo"> --%>
                  <form:select id="resourceBlocked1" class="commonSelectClass" name="users2[]"
                    multiple="multiple" path="extraMailTo" data-type="resourceBlocked_2">
                  <%--  <c:forEach var="mail"
                    items="${requestRequisitionResourceFormDTO.activeUserList}">
                     <option path="resource.id"
                                     value="${mail.employeeId}">${mail.firstName}
                                     ${mail.lastName}-(${mail.yashEmpId})</option>
                    </c:forEach> --%>
                  </form:select>
                  </div>
                  </div>
                  </div>
                  </div>
                  </div>
                  </div>
                  </div>
                  </td>
                  </tr>
                  <tr>
                  <td class="cust-color-th"></td>
                  <td class="td-cust-MailTo">
                  <div class="row full-width">
                  <div class="col-md-3">
                  <div class="notify" style="display: none;">
                  <c:forEach var="email" items="${requestRequisitionResourceFormDTO.emailsToNotify}">
                  <span class="notify-span" style="word-break: break-word;display: inherit;"><i
                    class="fa fa-angle-double-right" style="display:inline;" aria-hidden="true"></i>
                  ${email}</span>
                  </c:forEach>
                  </div>
                  </div>
                  <div class="col-md-3">
                  <div class="request" style="display: none;">
                  <c:forEach var="email" items="${requestRequisitionResourceFormDTO.emailsToRequestTo}">
                  <span class="request-span" style="word-break:break-word;display: inherit;"><i
                    class="fa fa-angle-double-right" style="display:inline;" aria-hidden="true"></i>
                  ${email}</span>
                  </c:forEach>
                  </div>
                  </div>
                  <div class="col-md-3">
                  <div class="pdlGroup " style="display: none;">
                  <c:forEach var="email" items="${requestRequisitionResourceFormDTO.pdlGroup}">
                  <span class="request-span" style="word-break:break-word;display: inherit;"><i
                    class="fa fa-angle-double-right" style="display:inline;" aria-hidden="true"></i>
                  ${email}</span>
                  </c:forEach>
                  </div>
                  </div>
                  <div class="col-md-3">
                  <div class="internalResourceText">
                  <textarea id="InternalResourceText1" rows="4" name="InternalResourceText1"
                    class="resourceBlocked_id1 cust-block_id1" readonly></textarea>
                  </div>
                  </div>
                  </div>
                  </td>
                  </tr>
                  <tr>
                  <td class="cust-color-th"></td>
                  <td colspan="2">
                  <div>
                  <button id="saveResource" disabled="disabled"
                    title="Click to Submit the added Internal/External Profiles" type="submit"
                    class="btn btn-primary">Submit
                  Profiles</button>
                  <a href="javascript:void(0);" id="cancelBtn" onClick="confirmPopup()"
                    class="btn btn-default">Cancel</a>
                  </div>
                  </td>
                  </tr>
                  <tr hidden>
                  <td hidden>
                  <div>
                  <span id="noOfResources"
                    value="${requestRequisitionResourceFormDTO.requestRequisitionResources[0].noOfResources}">
                  ${requestRequisitionResourceFormDTO.requestRequisitionResources[0].noOfResources}
                  </span>
                  </div>
                  </td>
                  </tr>
                  <tr hidden>
                  <td hidden>
                  <form:input type="hidden" id="reqId"
                    value="${requestRequisitionResourceFormDTO.requestRequisitionResources[0].requestRequisition.id}"
                    path="reqId" />
                  </div>
                  </td>
                  </tr>
                  <tr hidden>
                    <td hidden>
                      <form:input type="hidden" id="skillReqId"
                        value="${requestRequisitionResourceFormDTO.requestRequisitionResources[0].id}" path="skillReqId" />
            </div>
            </td>
            </tr>
            <tr hidden>
            <td hidden>
            <form:input type="hidden" id="fulfilled"
              value="${requestRequisitionResourceFormDTO.requestRequisitionResources[0].fulfilled}"
              path="fullfillResLen" />
          </div>
          </td>
          </tr>
          <tr hidden>
          <td hidden>
          <form:input type="hidden" id="projectId"
            value="${requestRequisitionResourceFormDTO.requestRequisitionResources[0].requestRequisition.project.id}"
            path="projectId" />
        </div>
        </td>
        </tr>
        </table>
        </form:form>
      </div>
      </p>
    </div>
				</div>

			</div>
		</div>


		<div class="modal-body">

			<div class="form-group">
				<div>

					<div id="imgContainer"></div>
				</div>
				<div class="instStep" style="margin-top: 10px; display: none;">
					<p class="instStepHead">Follow Below Steps for submitting
						Profiles and Maintaining Status</p>
					<div class="panel-group" id="accordion">
						<div class="panel panel-default">
							<a data-toggle="collapse" data-parent="#accordion" href="#Step1">
								<div class="panel-heading">
									<h4 class="panel-title">Add Internal Resource Profile</h4>
								</div>
							</a>
							<div id="Step1" class="panel-collapse collapse">
								<div class="panel-body">
									<ul>
										<li>Click on the select options dropdown. Select profiles
											that are employees of Yash.</li>
										<li>Click on 'Add Internal Resource'</li>
										<li>Table with selected profiles will be visible.</li>
										<li>You can upload a new resume or use the available
											resume for the selected profile.</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<a data-toggle="collapse" data-parent="#accordion" href="#Step2">
								<div class="panel-heading">
									<h4 class="panel-title">Add External Resource Profiles</h4>
								</div>
							</a>
							<div id="Step2" class="panel-collapse collapse">
								<div class="panel-body">
									<ul>
										<li>This option can be used, when you want to submit a
											profile for external resource.</li>
										<li>Click on Add External Resource Button, enter Name and
											upload resume and TEF Form.</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<a data-toggle="collapse" data-parent="#accordion" href="#Step3">
								<div class="panel-heading">
									<h4 class="panel-title">Comment</h4>
								</div>
							</a>
							<div id="Step3" class="panel-collapse collapse">
								<div class="panel-body">
									<ul>
										<li>You can give an extra comment, if required.</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<a data-toggle="collapse" data-parent="#accordion" href="#Step4">
								<div class="panel-heading">
									<h4 class="panel-title">Mail To</h4>
								</div>
							</a>
							<div id="Step4" class="panel-collapse collapse">
								<div class="panel-body">
									<ul>
										<li>You can select whom to send these profile
											informations. Check any of the check box options for the
											same.</li>
										<li>You can also, select other resources to notify about
											the profile information by selecting from the dropdown.</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<a data-toggle="collapse" data-parent="#accordion" href="#Step5">
								<div class="panel-heading">
									<h4 class="panel-title">Submit Profile</h4>
								</div>
							</a>
							<div id="Step5" class="panel-collapse collapse">
								<div class="panel-body">
									<ul>


										<li>Submit profiles (External/Internal) by clicking on
											Submit Profiles button.</li>
										<li>Profiles submitted are visible in the table at the
											bottom of this page with Default Profile status as
											'Submitted'.</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<a data-toggle="collapse" data-parent="#accordion" href="#Step6">
								<div class="panel-heading">
									<h4 class="panel-title">Profile status</h4>
								</div>
							</a>
							<div id="Step6" class="panel-collapse collapse">
								<div class="panel-body">
									<ul>
										<li>You can select profile status for each of the
											profiles(External/Internal) from the dropdown.</li>
										<li>For external profiles, if you select Joined status,
											you can give joining date.</li>
										<li>Click on the resume/TEF hyperlink, to download the
											resume/TEF.</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				<%-- <div>
                                                                                                                <form:form style="margin-top:10px" id="formFile"
                                                                                                                                modelAttribute="requestRequisitionDashboardInputDTO"
                                                                                                                                enctype="multipart/form-data">

                                                                                                                                <table class="request-reporttable well">
                                                                                                                                                <tr>
                                                                                                                                                                <th>Resource Type</th>
                                                                                                                                                                <th>Below Resources are Blocked/Proposed</th>
                                                                                                                                                                <!-- sarang change -->
                                                                                                                                                </tr>
                                                                                                                                                <tr>
                                                                                                                                                                <th valign="top" class="cust-color-th"><label
                                                                                                                                                                                for="internal">Internal</label></th>
                                                                                                                                                                <td valign="top">
                                                                                                                                                                                <div id="internal">
                                                                                                                                                                                                <div class="row full-width">
                                                                                                                                                                                                                <div class="col-md-12 blocked-select positionRel">
                                                                                                                                                                                                                                <div style="float:left">
                                                                                                                                                                                                                                <form:select id="resourceBlocked" class="required"
                                                                                                                                                                                                                                                name="reqID" multiple="multiple"
                                                                                                                                                                                                                                                path="internalResourceIds">
                                                                                                                                                                                                                                                <c:forEach var="mail"
                                                                                                                                                                                                                                                                items="${requestRequisitionResourceFormDTO.activeUserList}">
                                                                                                                                                                                                                                                                <option path="resource.id"
                                                                                                                                                                                                                                                                                                value="${mail.employeeId}/${mail.uploadResumeFileName}/${mail.emailId}/${mail.contactNumber}/${mail.totalExper}/${mail.competency.skill}">${mail.firstName} ${mail.lastName}-(${mail.yashEmpId})</option>
                                                                                                                                                                                                                                                <input id="internalResourceDetails" style="display:none" value="${mail}"/>
                                                                                                                                                                                                                                                </c:forEach>
                                                                                                                                                                                                                                </form:select>
                                                                                                                                                                                                                                <div >
                                                                                                                                                                                                                                                <input id="btnReset" type="button" title="click to upload resume for selected resources"
                                                                                                                                                                                                                                                                onclick="dynamicTable()" value="Add Internal Resource">
                                                                                                                                                                                                                                </div>
                                                                                                                                                                                                                                                <c:forEach var="hiddenfiles"
                                                                                                                                                                                                                items="${requestRequisitionResourceFormDTO.activeUserList}"  varStatus="myIndex">
                                                                                                                                                                                                                <input id="hiddenfileindex_${myIndex.index}" type="file"  value="${hiddenfiles.uploadResume}"/>
                                                                                                                                                                                                </c:forEach>
                                                                                                                                                                                                                </div>

                                                                                                                                                                                                                <div>
                                                                                                                                                                                                                                <div class="row full-width">
                                                                                                                                                                                                                                                <table id="selection-table">
                                                                                                                                                                                                                                                                <thead>
                                                                                                                                                                                                                                                                                <tr>
                                                                                                                                                                                                                                                                                                <th>Name</th>
                                                                                                                                                                                                                                                                                                <th>Email</th>
                                                                                                                                                                                                                                                                                                <th>Exp(Total IT Exp)</th>
                                                                                                                                                                                                                                                                                                <th>Skills</th>
                                                                                                                                                                                                                                                                                                <th>Contact</th>
                                                                                                                                                                                                                                                                                                <th class="align-center">Resume</th>
                                                                                                                                                                                                                                                                                                <th>Available Resume</th>

                                                                                                                                                                                                                                                                                </tr>
                                                                                                                                                                                                                                                                </thead>
                                                                                                                                                                                                                                                                <tbody id="add_internal_table">
                                                                                                                                                                                                                                                                                <tr>
                                                                                                                                                                                                                                                                                                <div hidden class="new-value">
                                                                                                                                                                                                                                                                                                                <textarea id="InternalResourceText"
                                                                                                                                                                                                                                                                                                                                name="InternalResourceText"
                                                                                                                                                                                                                                                                                                                                class="resourceBlocked_id" readonly></textarea> 
                                                                                                                                                                                                                                                                                </div>
                                                                                                                                                                                                                                                                                </tr>
                                                                                                                                                                                                                                                                </tbody>
                                                                                                                                                                                                                                                </table>
                                                                                                                                                                                                                                </div>

                                                                                                                                                                                                                </div>

                                                                                                                                                                                                </div>
                                                                                                                                                                                </div>
                                                                                                                                                                </td>
                                                                                                                                                </tr>
                                                                                                                                                <tr>
                                                                                                                                                                <th class="cust-color-th"><label for="external">External</label></th>
                                                                                                                                                                <td>
                                                                                                                                                                                <div class="row full-width">
                                                                                                                                                                                                <div class="col-md-12">
                                                                                                                                                                                                                <div class="row full-width">
                                                                                                                                                                                                                                <table id="selection-table">
                                                                                                                                                                                                                                                <thead>
                                                                                                                                                                                                                                                                <tr>
                                                                                                                                                                                                                                                                                <th>Name</th>
                                                                                                                                                                                                                                                                                <th>Location</th>
                                                                                                                                                                                                                                                                               <th>Notice(in Days)</th>
                                                                                                                                                                                                                                                                                <th>Email</th>
                                                                                                                                                                                                                                                                                <th>Exp(Total IT Exp)</th>
                                                                                                                                                                                                                                                                                <th>Skills</th>
                                                                                                                                                                                                                                                                                <th>Contact</th>
                                                                                                                                                                                                                                                                                <th>Resume</th>
                                                                                                                                                                                                                                                                                <th>TEF</th>
                                                                                                                                                                                                                                                                                <th>Remove</th>
                                                                                                                                                                                                                                                                </tr>
                                                                                                                                                                                                                                                </thead>
                                                                                                                                                                                                                                                <tbody id="add_external_table">

                                                                                                                                                                                                                                                </tbody>
                                                                                                                                                                                                                                </table>
                                                                                                                                                                                                                </div>
                                                                                                                                                                                                </div>
                                                                                                                                                                                                <div class="col-md-3">
                                                                                                                                                                                                                <div id="externalResourceId" class="external"
                                                                                                                                                                                                                                name="externalResourceName">
                                                                                                                                                                                                                                <div class="sub-row">

                                                                                                                                                                                                                                                <button class="add_field_button" title="Click to add External Resource and upload resume and TEF">Add External
                                                                                                                                                                                                                                                                Resource </button>
                                                                                                                                                                                                                                </div>

                                                                                                                                                                                                                </div>
                                                                                                                                                                                                </div>


                                                                                                                                                                                </div>
                                                                                                                                                                </td>
                                                                                                                                                </tr>
                                                                                                                                                <tr>
                                                                                                                                                                <th class="cust-color-th"><label for=comment>Comment</label></th>
                                                                                                                                                                <td>
                                                                                                                                                                                <div>
                                                                                                                                                                                                <div class="comment">
                                                                                                                                                                                                                <div class="sub-row">
                                                                                                                                                                                                                                <form:input type="text" id="skillReqComment"
                                                                                                                                                                                                                                                name="skillReqComment" path="comments" />
                                                                                                                                                                                                                                <br>

                                                                                                                                                                                                                </div>
                                                                                                                                                                                                </div>
                                                                                                                                                                                </div>
                                                                                                                                                                </td>
                                                                                                                                                </tr>
                                                                                                                                                <tr>
                                                                                                                                                                <th id="mailToCheckBox" class="cust-color-th"><label
                                                                                                                                                                                for=mailTo>Mail To</label></th>
                                                                                                                                                                <td>

                                                                                                                                                                               <div class="mailTo">
                                                                                                                                                                                                <div class="row full-width">
                                                                                                                                                                                                                <div class="col-md-3 mailToAdj">
                                                                                                                                                                                                                                <form:checkbox value="1" class="checkId" name="checkId"
                                                                                                                                                                                                                                                path="notifyTo" />
                                                                                                                                                                                                                                <span class="mailToSpan">Notify To</span>

                                                                                                                                                                                                                </div>

                                                                                                                                                                                                                <div class="v-Line"></div>

                                                                                                                                                                                                                <div class="col-md-3 mailToAdj">
                                                                                                                                                                                                                                <form:checkbox value="2" class="checkId" name="checkId"
                                                                                                                                                                                                                                                path="requestSentTo" />
                                                                                                                                                                                                                                <span class="mailToSpan">Request Sent to</span>
                                                                                                                                                                                                                </div>

                                                                                                                                                                                                                <div class="v-Line"></div>

                                                                                                                                                                                                                <div class="col-md-3 mailToAdj">
                                                                                                                                                                                                                                <form:checkbox value="3" class="checkId" name="checkId" 
                                                                                                                                                                                                                                                path="pdl" />
                                                                                                                                                                                                                                <span class="mailToSpan">PDL</span>
                                                                                                                                                                                                                </div>

                                                                                                                                                                                                                <div class="v-Line"></div>

                                                                                                                                                                                                                <div class="col-md-3">
                                                                                                                                                                                                                                Other Users to Notify (if any):
                                                                                                                                                                                                                                <div>
                                                                                                                                                                                                                                                <div id="internal">
                                                                                                                                                                                                                                                                <div class="row full-width">
                                                                                                                                                                                                                                                                                <div class="col-md-3 blocked-select positionRel">
                                                                                                                                                                                                                                                                                                <form:select id="resourceBlocked1" class="required"
                                                                                                                                                                                                                                                                                                                name="reqID" multiple="multiple" path="extraMailTo">
                                                                                                                                                                                                                                                                                                                <c:forEach var="mail"
                                                                                                                                                                                                                                                                                                                                items="${requestRequisitionResourceFormDTO.activeUserList}">
                                                                                                                                                                                                                                                                                                                                <option path="resource.id"
                                                                                                                                                                                                                                                                                                                                                value="${mail.employeeId}">${mail.firstName}
                                                                                                                                                                                                                                                                                                                                                ${mail.lastName}-(${mail.yashEmpId})</option>
                                                                                                                                                                                                                                                                                                                </c:forEach>
                                                                                                                                                                                                                                                                                                </form:select>

                                                                                                                                                                                                                                                                                </div>
                                                                                                                                                                                                                                                                </div>
                                                                                                                                                                                                                                                </div>

                                                                                                                                                                                                                                </div>
                                                                                                                                                                                                                </div>
                                                                                                                                                                                                </div>

                                                                                                                                                                                </div>
                                                                                                                                                                </td>
                                                                                                                                                </tr>
                                                                                                                                                <tr>
                                                                                                                                                                <td class="cust-color-th"></td>
                                                                                                                                                                <td class="td-cust-MailTo">

                                                                                                                                                                                <div class="row full-width">
                                                                                                                                                                                                <div class="col-md-3">
                                                                                                                                                                                                                <div class="notify" style="display: none;">
                                                                                                                                                                                                                                <c:forEach var="email"
                                                                                                                                                                                                                                                items="${requestRequisitionResourceFormDTO.emailsToNotify}">
                                                                                                                                                                                                                                                <span class="notify-span"><i
                                                                                                                                                                                                                                                                class="fa fa-angle-double-right" aria-hidden="true"></i>
                                                                                                                                                                                                                                                                ${email}</span>
                                                                                                                                                                                                                                </c:forEach>
                                                                                                                                                                                                                </div>


                                                                                                                                                                                                </div>

                                                                                                                                                                                                <div class="col-md-3">
                                                                                                                                                                                                                <div class="request" style="display: none;">
                                                                                                                                                                                                                                <c:forEach var="email"
                                                                                                                                                                                                                                                items="${requestRequisitionResourceFormDTO.emailsToRequestTo}">
                                                                                                                                                                                                                                                <span class="request-span"><i
                                                                                                                                                                                                                                                                class="fa fa-angle-double-right" aria-hidden="true"></i>
                                                                                                                                                                                                                                                                ${email}</span>
                                                                                                                                                                                                                                </c:forEach>
                                                                                                                                                                                                                </div>
                                                                                                                                                                                                </div>

                                                                                                                                                                                                <div class="col-md-3">
                                                                                                                                                                                                                <div class="pdlGroup " style="display: none;">
                                                                                                                                                                                                                                <c:forEach var="email"
                                                                                                                                                                                                                                                items="${requestRequisitionResourceFormDTO.pdlGroup}">
                                                                                                                                                                                                                                                <span class="request-span"><i
                                                                                                                                                                                                                                                                class="fa fa-angle-double-right" aria-hidden="true"></i>
                                                                                                                                                                                                                                                                ${email}</span>
                                                                                                                                                                                                                                </c:forEach>
                                                                                                                                                                                                                </div>
                                                                                                                                                                                                </div>

                                                                                                                                                                                                <div class="col-md-3">
                                                                                                                                                                                                                <div class="internalResourceText">
                                                                                                                                                                                                                                <textarea id="InternalResourceText1" rows="4"
                                                                                                                                                                                                                                                name="InternalResourceText1"
                                                                                                                                                                                                                                                class="resourceBlocked_id1 cust-block_id1" readonly></textarea>
                                                                                                                                                                                                                </div>
                                                                                                                                                                                                </div>

                                                                                                                                                                                </div>





                                                                                                                                                                </td>
                                                                                                                                                </tr>
                                                                                                                                                <tr>
                                                                                                                                                                <td class="cust-color-th"></td>
                                                                                                                                                                <td colspan="2">
                                                                                                                                                                                <div>
                                                                                                                                                                                                <button id="saveResource"
                                                                                                                                                                                                                title="Click to Submit the added Internal/External Profiles"
                                                                                                                                                                                                                type="submit" class="btn btn-primary">Submit
                                                                                                                                                                                                                Profiles</button>
                                                                                                                                                                                </div>
                                                                                                                                                                </td>
                                                                                                                                                </tr>
                                                                                                                                                <tr hidden>
                                                                                                                                                                <td hidden>
                                                                                                                                                                                <div>
                                                                                                                                                                                                <span id="noOfResources"
                                                                                                                                                                                                                value="${requestRequisitionResourceFormDTO.requestRequisitionResources[0].noOfResources}">
                                                                                                                                                                                                                ${requestRequisitionResourceFormDTO.requestRequisitionResources[0].noOfResources}
                                                                                                                                                                                                </span>

                                                                                                                                                                                </div>
                                                                                                                                                                </td>
                                                                                                                                                </tr>
                                                                                                                                                <tr hidden>
                                                                                                                                                                <td hidden><form:input type="hidden" id="reqId"
                                                                                                                                                                                                value="${requestRequisitionResourceFormDTO.requestRequisitionResources[0].requestRequisition.id}"
                                                                                                                                                                                                path="reqId" />
                                                                                                                                                                                </div></td>
                                                                                                                                                </tr>
                                                                                                                                                <tr hidden>
                                                                                                                                                                <td hidden><form:input type="hidden" id="skillReqId"
                                                                                                                                                                                                value="${requestRequisitionResourceFormDTO.requestRequisitionResources[0].id}"
                                                                                                                                                                                                path="skillReqId" />
                                                                                                                                                                                </div></td>
                                                                                                                                                </tr>
                                                                                                                                                <tr hidden>
                                                                                                                                                                <td hidden><form:input type="hidden" id="fulfilled"
                                                                                                                                                                                                value="${requestRequisitionResourceFormDTO.requestRequisitionResources[0].fulfilled}"
                                                                                                                                                                                                path="fullfillResLen" />
                                                                                                                                                                                </div></td>
                                                                                                                                                </tr>
                                                                                                                                                <tr hidden>
                                                                                                                                                                <td hidden><form:input type="hidden" id="projectId"
                                                                                                                                                                                                value="${requestRequisitionResourceFormDTO.requestRequisitionResources[0].requestRequisition.project.id}"
                                                                                                                                                                                                path="projectId" />
                                                                                                                                                                                </div></td>
                                                                                                                                                </tr>
                                                                                                                                </table>
                                                                                                                </form:form>
                                                                                                </div> --%>
    <!-- ******************************************************************* -->
    <div class="sk-fading-circle">
      <div class="sk-circle1 sk-circle"></div>
      <div class="sk-circle2 sk-circle"></div>
      <div class="sk-circle3 sk-circle"></div>
      <div class="sk-circle4 sk-circle"></div>
      <div class="sk-circle5 sk-circle"></div>
      <div class="sk-circle6 sk-circle"></div>
      <div class="sk-circle7 sk-circle"></div>
      <div class="sk-circle8 sk-circle"></div>
      <div class="sk-circle9 sk-circle"></div>
      <div class="sk-circle10 sk-circle"></div>
      <div class="sk-circle11 sk-circle"></div>
      <div class="sk-circle12 sk-circle"></div>
    </div>
    <!-- ******************************************************************* -->
    <div class="report-status well">
      <form:form method="post" modelAttribute="requestStatusForm" id="updateResouceForm" style="overflow-x:auto"
        action="../requestsReports/updateResourceRequestWithStatus">
        <form:input type="hidden" id="requestRequisitionSkillId" size="16" maxlength="80"
          value="${requestStatusForm.requestRequisitionSkillId}" path="requestRequisitionSkillId" />
        <table class="allocated_resource_table table">
          <thead>
            <tr>
              <!-- <th>CheckBox <input type="checkbox" id="selectall" /></th> -->
              <th>Schedule Interview</th>
              <th>Resource Type</th>
              <th>Resource Blocked</th>
              <th>Profile Status</th>
              <!-- sarang change -->
              <th>Joining Date</th>
              <th>Interview Date</th>
              <th>Location</th>
              <th>Notice Period</th>
              <!--        <th>Allocation Type</th> -->
              <th>Email</th>
              <th>Exp(Total IT Exp)</th>
              <th>Skills</th>
              <th>Contact</th>
              <th>Resume</th>
              <th>TEF</th>
              <th>Delete</th>


								</tr>
							</thead>
							<tbody>
								<tr>
									<c:forEach var="resource"
										items="${requestStatusForm.resourceStatuslist}" varStatus="i"
										begin="0">
										<tr>
											<form:input type="hidden" size="16" maxlength="80"
												value="${resource.resourceId}"
												path="resourceStatuslist[${i.index}].resourceId" />
												<td style=" display: none;"><p id="selectResourceId" ></p></td>
											<td class="text-center"><i
												class="fa fa-lg fa-clock-o fa-4" aria-hidden="true"
												onclick="scheduleInterview(${ requestStatusForm.requestRequisitionSkillId }, ${resource.resourceId})"></i></td>
											<td id="resourceTypeId">
												<%-- <c:if test="${resource.externalResourceName ne null}">
                                                                                                                                                                                External
                                                                                                                                                                                </c:if>   
                                                                                                                                                                                <c:if test="${ resource.externalResourceName eq null }"> Internal</c:if> --%>
												<span class="resourcetype"> ${resource.resourceType}</span>
											</td>

											<td name="skillResourceNameId">
												<%-- <c:if test="${resource.externalResourceName ne null}">
                                                                                                                                                                                                <span> ${resource.externalResourceName}</span>
                                                                                                                                                                                </c:if>   
                                                                                                                                                                                <c:if test="${ resource.externalResourceName eq null }">
                                                                                                                                                                                                <span> ${resource.resource.firstName} ${resource.resource.lastName}   </span>
                                                                                                                                                                                </c:if> --%>
												<span> ${resource.resourceName}</span>
											</td>

											<td><form:select onchange="changStatusType(this,${resource.resourceId})"
													inp="stType" id="stTypeInternal"
													path="resourceStatuslist[${i.index}].profileStaus">
													<option value="0" disabled>Select1212</option>
													<c:forEach var="statusType"
														items="${requestRequisitionResourceFormDTO.requestRequisitionResourceStatusList}">'
                    <option
															<c:if test="${resource.profileStaus eq statusType.id}">selected="selected"</c:if>
															value="${ statusType.id }">${ statusType.statusName}</option>
													</c:forEach>
												</form:select> <i class="fa fa-commenting commentIcon commentClassForAjax"
												aria-hidden="true" data-toggle="modal"></i> <!-- data-target="#myModal1" -->
											</td>
											<td><c:if
													test="${ resource.allocationStartDate ne null}">
													<div style="width: 170px" id="joinDate" class='JDate'>
														<label>Joining Date</label> <span> ${ resource.allocationStartDate}
														</span>
													</div>
												</c:if> 
												<div class="joinDate">
													Joining Date:
													<form:input type="text" size="16" maxlength="80" value=""
														path="resourceStatuslist[${i.index}].allocationStartDate"
														class=" datepicker" id="" />
												</div>
												
											</td>
											<td>
											<div class="interviewDate">
											<!-- data-target="Interview Date" --> 
											<c:if
													test="${ resource.interviewDate ne null}">
													<div style="width: 170px" id="interviewDate" class='IDate'>
														<label>Interview Date</label> <span> ${ resource.interviewDate}
														</span>
													</div>
												</c:if>
													Interview Date:
													<form:input type="text" size="16" maxlength="80" value=""
														path="resourceStatuslist[${i.index}].interviewDate"
														class=" datepicker" id="" />
												</div>
											</td>
											<%-- <td>
                                                                                                                                                                                <c:if test="${resource.allocationType eq 0}">
                                                                                                                                                                                                                <form:select id="allocationIdInternal" name="allocationId" 
                                                                                                                                                                                style="display: none;" path="resourceStatuslist[${i.index}].allocationType" >
                                                                                                                                                                                                <option value="0"> Select </option>
                                                                                                                                                                                <c:forEach var="allocationTypeObject" items="${requestRequisitionResourceFormDTO.allocationTypeList}">'
                                                                                                                                                                                               <option <c:if test="${resource.allocationType eq allocationTypeObject.id}">selected="selected"</c:if> value="${ allocationTypeObject.id}"> ${ allocationTypeObject.allocationType}</option>
                                                                                                                                                                                </c:forEach>
                                                                                                                                                                                </form:select>
                                                                                                                                                                                </c:if>
                                                                                                                                                                                <c:if test="${resource.allocationType gt 0}">
                                                                                                                                                                                <form:select id="allocationIdInternal" name="allocationId" 
                                                                                                                                                                                 path="resourceStatuslist[${i.index}].allocationType" >
                                                                                                                                                                                                <option value="0"> Select </option>
                                                                                                                                                                                <c:forEach var="allocationTypeObject" items="${requestRequisitionResourceFormDTO.allocationTypeList}">'
                                                                                                                                                                                               <option <c:if test="${resource.allocationType eq allocationTypeObject.id}">selected="selected"</c:if> value="${ allocationTypeObject.id}"> ${ allocationTypeObject.allocationType}</option>
                                                                                                                                                                                </c:forEach>
                                                                                                                                                                                </form:select>
                                                                                                                                                                                </c:if>
                                                                                                                                                                </td> --%>

											<%-- <td>
                                                                                                                                                                                <c:if test="${resource.allocationType eq 0}">
                                                                                                                                                                                                                <form:select id="allocationIdInternal" name="allocationId" 
                                                                                                                                                                                
                                                                                                                                                                                style="display: none;" path="resourceStatuslist[${i.index}].allocationType" >
                                                                                                                                                                                                <option value="0"> Select </option>
                                                                                                                                                                                <c:forEach var="allocationTypeObject" items="${requestRequisitionResourceFormDTO.allocationTypeList}">'
                                                                                                                                                                                               <option <c:if test="${resource.allocationType eq allocationTypeObject.id}">selected="selected"</c:if> value="${ allocationTypeObject.id}"> ${ allocationTypeObject.allocationType}</option>
                                                                                                                                                                                </c:forEach>
                                                                                                                                                                                </form:select>
                                                                                                                                                                                </c:if>
                                                                                                                                                                                <c:if test="${resource.allocationType gt 0}">
                                                                                                                                                                                <form:select id="allocationIdInternal" name="allocationId" 
                                                                                                                                                                                
                                                                                                                                                                                 path="resourceStatuslist[${i.index}].allocationType" >
                                                                                                                                                                                                <option value="0"> Select </option>
                                                                                                                                                                                <c:forEach var="allocationTypeObject" items="${requestRequisitionResourceFormDTO.allocationTypeList}">'
                                                                                                                                                                                               <option <c:if test="${resource.allocationType eq allocationTypeObject.id}">selected="selected"</c:if> value="${ allocationTypeObject.id}"> ${ allocationTypeObject.allocationType}</option>
                                                                                                                                                                                </c:forEach>
                                                                                                                                                                                </form:select>
                                                                                                                                                                                </c:if>
                                                                                                                                                                                
                                                                                                                                                                </td> --%>
											<td name="location"><span> ${resource.location}</span></td>
											<td name="noticePeriod"><span>
													${resource.noticePeriod}</span></td>
											<td name="email"><span> ${resource.emailId} </span></td>
											<td name="experience"><span>
													${resource.totalExperience}</span></td>
											<td name="Skills"><span> ${resource.skills}</span></td>
											<td name="contact"><span>
													${resource.contactNumber}</span></td>

											<td style="text-align: center;"><c:if
													test="${resource.resourceType eq 'Internal'}">
													<a title="Click to Download Resume"
														onclick="fileDownload('${ resource.employeeId }','${ resource.resourceType }')"
														href="javascript:void(0);"><i
														class='action_btn fa fa-download'></i></a>
												</c:if> <c:if test="${resource.resourceType eq 'External'}">
													<a title="Click to Download Resume"
														onclick="fileDownload('${ resource.resourceId }','${ resource.resourceType }')"
														href="javascript:void(0);"><i
														class='action_btn fa fa-download'></i></a>
												</c:if></td>

											<td style="text-align: center;"><c:if
													test="${resource.resourceType eq 'External'}">
													<a title="Click to Download TEF"
														onclick="tacDownload('${ resource.resourceId }')"
														href="javascript:void(0);"><i
														class='action_btn fa fa-download'></i></a>
												</c:if></td>

											<td style="text-align: center;"><a style="border: none;"
												class="remove_field btn btn-danger action_btn delete_btn"
												id="delete-res-${ resource.resourceId }"
												onclick="showDeletePopup('${ resource.resourceId }','${ requestStatusForm.requestRequisitionSkillId }',this)"
												href="javascript:void(0);"><i class='fa fa-remove'></i></a></td>

										</tr>
									</c:forEach>

								</tr>
							</tbody>
						</table>
					</form:form>
				</div>

			</div>
			<div id="mapResourceDiv" class="report-status well" style="display: none">
			<h4>Map Resources to Following RRF</h4>
			<select id="rrfids" name="internalUserIds" >
			
			</select>
			<button id="mapButton" onclick="mapResourceToRRF()">MAP</button>
			</div>
		</div>
		<div class="modal-footer well">
			<div style='float: left;'>
				<button onclick="backToDashBoard()" class="btn btn-success" style="">Back
					To Dashboard</button>
			</div>
			<div style='float: right;'>



				<button id="updateResourceStatus" name="updateResourceStatus"
					disabled="disabled" onclick="updateResourceStatus()"
					class="btn btn-primary">Update Resource Status</button>
			</div>

		</div>

	</div>
</div>
</div>

<div id="sendModal" class="modal" role="dialog" data-backdrop="static"
	data-keyboard="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close modelclose" data-dismiss="modal"
					onclick="Closemodel()">&times;</button>
				<h4 class="modal-title">Upload Resume</h4>
				<div class="table-responsive">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>Name</th>
								<th>Resume</th>

							</tr>
						</thead>
						<tbody id="uploadResume" name="uploadResume">

						</tbody>
					</table>
					<div>

						<!-- <button id="submitForm" type="submit">Save Now</button> -->
						</td>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>
<div class="modal" id="myModal1" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content commentmodal">
			<div class="modal-header">
				<button type="button" id="clsComment" class="close"
					data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="reqNameId"></h4>
			</div>
			<div class="modal-body commentBody" id="getResultDiv"></div>
			<div class="commentbox">
				<label for="usr" style="display: block;">Comment:</label>
				<textarea class="form-control" id="usr" cols="20" wrap="hard"
					style="display: inline-block; width: 86%;"></textarea>
				<button type="button" id="strCSubmit" class="btn btn-primary"
					style="position: absolute; margin-top: 26px; margin-left: 25px; padding: 4px 3px;">
					Submit <i class="fa fa-comment" aria-hidden="true"
						style="font-size: 18px"></i>
				</button>
			</div>
		</div>

	</div>
</div>


</div>

<!-- start: Update resource status popup   -->


<div id="updateResourceStatusPopup" class="modal">
	<div class="modal-content"
		style="width: 30%; margin-top: 40px; display: table;">
		<a href="#" data-dismiss="modal"> <span class="close fa fa-close"
			id="close-icon"
			style="right: 20px; top: 22px; position: relative; font-size: 20px;"></span>
		</a>
		<h1 style="padding: 20px;">
			<span class="positionStatus positionStatusPlaceHolder"> <span
				class="resourceSubmissionss">Update Resource Status</span>
			</span>
		</h1>
		<div class="col-md-12"
			style="background: #f5faff; padding: 20px 22px 15px;">
			<section class="sidebar" style="height: auto;">Are you sure
				you want to update the resource status?</section>
		</div>
		<div class="close-right"
			style="display: inline-block; width: 100%; text-align: right;">

			<button type="button" id="updateSatusFinalBtn"
				class="btn btn-secondary next-button">OK</button>
			<button type="button" id="close-btn"
				class="btn btn-secondary next-button"
				style="margin: 10px 24px 10px 10px;" data-dismiss="modal">Close</button>

		</div>
	</div>

</div>

<!-- start: Delete popup -->

<div id="deletePopup1" class="modal">
	<div class="modal-content"
		style="width: 30%; margin-top: 40px; display: table;">
		<a href="javascript:void(0)" class="close-btn1"> <span
			class="close fa fa-close" id="close-icon"
			style="right: 20px; top: 22px; position: relative; font-size: 20px;"></span>
		</a>
		<h1 style="padding: 20px;">
			<span class="positionStatus positionStatusPlaceHolder"> <span
				class="resourceSubmissionss">Delete Resource</span>
			</span>
		</h1>
		<div class="col-md-12"
			style="background: #f5faff; padding: 20px 22px 15px;">
			<section class="sidebar" style="height: auto;">Do you
				Really want to delete this record ?</section>
		</div>
		<div class="close-right"
			style="display: inline-block; width: 100%; text-align: right;">
			<button type="button" id="deleteFinalBtnNew"
				onclick="deleteData($('#deletePopup1').attr('data-resource-id'),'${ requestStatusForm.requestRequisitionSkillId }')"
				class="btn btn-secondary next-button ">OK</button>
			<button type="button" id="close-btn"
				class="btn btn-secondary next-button close-btn1"
				style="margin: 10px 24px 10px 10px;">Close</button>

		</div>
	</div>

</div>
<!-- schedule interview pop up start -->
<div id="interviewModal" class="modal">
  <div class="modal-content" style="width: 530px; margin-top: 40px;">
    <a href="#" data-dismiss="modal" onclick="closeRRFPopup()"><span class="close" id="close-icon"
        style="right: 20px; top: 18px;"></span></a>
    <h1 class="pd-20">
      <span id="forward_placeholder">Schedule Discussion/Interview</span>
      <button type="button" id="forward-btn" class="btn next-button" style="margin: 6px 3px 2px 51px;" onclick="onScheduleInterviewSubmit()">Send Email</button>
      <a href="javascript:void(0);" type="button" class="close" onclick="closeInterviewPopup()">×</a>
    </h1>
    <div class="dataTables_wrapper pd-20 bg-none" role="grid">

			<div class="col-xs-12">
				<form>
					<div class="row">
						<div class="col-md-4 form-group">
							<label>Discussion With</label> <select id="discussionType"
								class="form-control">
								<option selected disabled hidden style='display: none' value="selected"></option>
								<option value="client">Client Interview</option>
								<option value="yashInternal">Yash Internal Interview</option>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4 form-group">
							<label>Interview Mode</label> <select id="interviewmode"
								class="form-control">
								<option value="telephonic">Telephonic</option>
								<option value="faceToFace">Face To Face</option>
								<option value="f2fClient">Face to Face client Site</option>
								<option value="skype">Skype</option>
							</select>
						</div>
						<div class="col-md-4 form-group">
							<label>Interview Date</label> <input type="text"
								id="interviewdate" class="form-control" />
						</div>
						<div class="col-md-4 form-group">
							<label>Interview Time</label> <input type="time"
								id="interviewtime" class="form-control" />
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 form-group">
							<label>BG-BU</label> <input type="text" disabled="true" id="bgbu"
								class="form-control" />
						</div>
						<div class="col-md-6 form-group">
							<label>Requirement ID</label> <input type="text" disabled="true"
								id="reqid" class="form-control" />
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 form-group">
							<label>Customer Name</label> <input type="text" disabled="true"
								id="customerName" class="form-control" />
						</div>
						<div class="col-md-6 form-group">
							<label>Location</label> <input type="text" disabled="true"
								id="location" class="form-control" />
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 form-group">
							<label>Resource Name</label> <input type="text" disabled="true"
								id="resourcename" class="form-control" />
						</div>
						<div class="col-md-6 form-group">
							<label>Employee Id</label> <input type="text" disabled="true"
								id="employeeid" class="form-control" />
						</div>
					</div>

					<div class="row">
						<div class="col-md-6 form-group">
							<label>Delivery POC Name</label> <input type="text"
								disabled="true" id="deliveryPOCName" class="form-control" />
						</div>
						<div class="col-md-6 form-group">
							<label>Mobile Number</label> <input type="number"
								id="deliveryPOCCont" class="form-control" disabled />
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 form-group">
							<label>AM POC Name</label> <input type="text" id="amPOCName"
								class="form-control" />
						</div>
						<div class="col-md-6 form-group">
							<label>Mobile Number</label> <input type="number" id="amPOCCont"
								class="form-control" />
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 form-group" id="custPOCNameDiv"
							style="display: none;">
							<label>Customer POC Name</label> <input type="text"
								id="custPOCName" class="form-control" />
						</div>
						<div class="col-md-6 form-group" id="custPOCContDiv"
							style="display: none;">
							<label>Mobile Number</label> <input type="number"
								id="custPOCCont" class="form-control" />
						</div>
						<div class="col-md-6 form-group" id="contactPersonDiv"
							style="display: none;">
							<label>Contact Person</label> <select id="contactPerson"
								name="users3[]" data-type="internalUserIds_1"
								name="internalUserIds" class="commonSelectClass"
								multiple="multiple">
								<%--  <c:forEach var="activeUsers" items="${activeUserList}">
                <option value="${activeUsers.emailId}">${activeUsers.firstName}
                                ${activeUsers.lastName}</option>
                </c:forEach> --%>
							</select>
						</div>
						<div class="col-md-6 form-group" id="contactPersonNumbDiv"
							style="display: none;">
							<label>Contact Person Number</label> <input type="number"
								id="contactPersonNumber" class="form-control" />
						</div>

					</div>
					<div class="row">
						<div class="col-md-8 form-group" id="venueDiv"
							style="display: none;">
							<label>Venue</label>
							<textarea id="venue" class="form-control"></textarea>
						</div>
						<div class="col-md-4 form-group" id="gatenumberDiv"
							style="display: none;">
							<label>Gate Pass No.</label> <input type="text" id="gatenumber"
								class="form-control" />
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 form-group">
							<label>Skill Category</label> <input type="text" disabled="true"
								id="skill" class="form-control" />
						</div>
						<div class="col-md-12 form-group">
							<label>Job Description</label>
							<textarea id="jobdesc" class="form-control rounded"></textarea>
						</div>
						<!-- <div class="clearfix col-md-12">
                <em>* All above information are mandatory</em>
              </div> -->
            </div>
        </form>
      </div>

			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Select Others</th>
						<!--  <th>Mail To</th>
            <th>Notify To</th>
            <th>PDLs To</th> -->
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="forward-rrf">
							<!-- <select id="internalUserIds" 
                name="internalUserIds" class="required" multiple="multiple"> -->
							<select id="internalUserIds" name="users3[]"
							data-type="internalUserIds_1" name="internalUserIds"
							class="commonSelectClass" multiple="multiple">
								<%--  <c:forEach var="activeUsers" items="${activeUserList}">
                <option value="${activeUsers.emailId}">${activeUsers.firstName}
                                ${activeUsers.lastName}</option>
                </c:forEach> --%>
						</select>
						</td>
						<!--  <td><input type="checkbox" class="sendEmailclass" id="mailTo" value="mailToChecked" /></td>
            <td><input type="checkbox" class="sendEmailclass" id="notifyTo" value="notifyToChecked" /></td>
            <td><input type="checkbox" class="sendEmailclass" id="pdlsTo" value="pdlsToChecked" /></td> -->
					</tr>
				</tbody>
			</table>
			<div class="col-md-12 text-mrgin form-group">
				<span><input type="checkbox" class="sendEmailclass"
					id="mailTo" value="mailToChecked" /></span><label class="padding-prop">Mail
					To</label>
				<textarea class="col-md-12  form-control rounded" disabled="disabled" id="mailtoTextArea"></textarea>
			</div>
			<div class="col-md-12 text-mrgin form-group">
				<span><input type="checkbox" class="sendEmailclass"
					id="notifyTo" value="notifyToChecked" /></span><label
					class="padding-prop">Notify to(cc)</label>
				<textarea class="col-md-12  form-control rounded" disabled="disabled" id="notifyToTextArea"></textarea>
			</div>
			<div class="col-md-12 text-mrgin form-group">
				<span><input type="checkbox" class="sendEmailclass"
					id="pdlsTo" value="pdlsToChecked" /></span><label class="padding-prop">PDLs
					To</label>
				<textarea class="col-md-12  form-control rounded" disabled="disabled" id="pdlTextArea"></textarea>
			</div>

			<input name="requestResourceId" id="requestResourceId" type="hidden" />
			<label>Additional Information : </label>
			<textarea id="mailText" class="form-control rounded" rows="3"></textarea>
		</div>
		<div class="close-right">
			<button type="button" id="close-btn"
				class="btn btn-secondary next-button" style=""
				onclick="closeInterviewPopup()">Close</button>
			<button type="button" id="forward-btn" class="btn next-button"
				style="margin: 10px 28px 10px 10px;"
				onclick="onScheduleInterviewSubmit()">Send Email</button>
		</div>
	</div>
</div>
<!-- schedule interview pop up end -->

