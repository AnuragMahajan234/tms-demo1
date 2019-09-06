<%@ page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<spring:url value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}" 	var="ColReorder_js" />
<spring:url value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}" 	var="ColVis_js" />
<spring:url value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}" var="multiselect_js" />
<script src="${multiselect_js}" type="text/javascript"></script>

<spring:url value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}" 	var="multiselect_filter_js" />
<%-- <script src="${multiselect_filter_js}" type="text/javascript"></script> --%>
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}" var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
	
<spring:url value="/resources/images/spinner.gif" var="spinner" />

<script src="${multiselect_filter_js}" type="text/javascript"></script>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />

</head>
<body>
<style type="text/css" title="currentStyle">

thead input {
	width: 100%
}


input.search_init {
	color: #999
}

.projecttoolbar {
	border: -65px solid red;
	width: 50%;
	float: left;
}

.blue_link {
	left: -200
}

#defaultTableId {
	
}

#defaultTableId thead th {
	width: 120px;
}

#defaultTableId td {
	word-wrap: break-word;
}

.dtable td {
	background: #ebebeb;
}

.rRReportFilter td {
	padding: 10px;
}

.activeReportTab {
	background: #84DFC1;
}

.datatable-scroll {
	max-width: 2000px;
	overflow-x: auto;
	position:relative;
	
}
div.dataTables_wrapper {
	width: 100%;
	margin: 0 auto;
	position: relative;
}

.pointer {
	cursor: pointer;
}

</style>
 <style>
    .ui-datepicker-calendar {
        display: none;
    }
    </style>
<script>

var startDate;
var endDate;

$(document).ready(function() {
	
	
	$(function() {
		$("#enddate").datepicker({
			maxDate : 0,
			dateFormat: 'dd-mm-yy'
		});
		
		$("#startdate").datepicker({
			maxDate : 0,
			//dateFormat: 'dd-mm-yy',
			dateFormat: 'mm-yy',
			//dateFormat: 'MM-yy',
			changeMonth: true,
            changeYear: true,
            //showButtonPanel: true,
            //dateFormat: 'MM yy',
            onClose: function(dateText, inst) { 
                $(this).datepicker('setDate', new Date(inst.selectedYear, inst.selectedMonth, 1));
            }
		});
	});
	
});

function getMonthFromString(mon){
   var d = Date.parse(mon + "1, 2012");
   if(!isNaN(d)){
      return new Date(d).getMonth() + 1;
   }
   return -1;
 }

var skillRequestReportPDF = function() {
	var startDate = document.getElementById("startdate").value;
	var dateSplit = startDate.split("-");
	var firstCompleteDate = new Date(parseInt(dateSplit[1]),parseInt(dateSplit[0])-1, 1);
	var lastCompleteDate = new Date(parseInt(dateSplit[1]),(parseInt(dateSplit[0])-1) + 1, 0);
	var FistDate = firstCompleteDate.getDate();
	var FistMonth = firstCompleteDate.getMonth() + 1; 
	var FistYear = firstCompleteDate.getFullYear();
	var LastDate = lastCompleteDate.getDate();
	
	var CompleteFirstDate = FistDate + '-' + FistMonth + '-' + FistYear; 
	var CompletelastDate = LastDate + '-' + FistMonth + '-' + FistYear;
	
	var endDate = CompletelastDate;
	
	var errmsg = "Please select:";
	var validationFlag = true;
	
	var selectedBG_BU = [];
	
	$("#BG_BU option:selected").each(function () {
		var id = $(this).val();
		selectedBG_BU.push(id);
 	});
	
	if(document.getElementById("BG_BU").value == ""){
		validationFlag = false;
		errmsg = errmsg + " BG-BU";
	}
	else if(document.getElementById("startdate").value == ""){
		validationFlag = false;
		errmsg = errmsg + " Start Date";
	}
	/* else if(document.getElementById("enddate").value == ""){
		validationFlag = false;
		errmsg = errmsg + " End Date";
	} */
	else if(CompleteFirstDate != "" && CompletelastDate.value != "")
		{
			startDate = CompleteFirstDate;
			endDate = CompletelastDate;
			var startDateArray = startDate.split("-");
			var endDateArray = endDate.split("-");
			var stDate = new Date(startDateArray[1]+"-"+startDateArray[0]+"-"+startDateArray[2]);
			var enDate = new Date(endDateArray[1]+"-"+endDateArray[0]+"-"+endDateArray[2]);
			
			if (stDate > enDate) {
				errmsg = errmsg
						+ " Correct End Date"
						+ "<br>End Date should be greater that Start Date ";
				validationFlag = false;
			}
		}
	else{
		validationFlag = true;
		errmsg="";
	}
	
	if (validationFlag) {
		$(this).attr('target', '_blank');
    	window.location = "../rmgReport/showPDFReport?startDate="+startDate+"&endDate="+endDate+"&selectedBG_BU="+selectedBG_BU;
    	/* setTimeout(function() {
        	showSuccess("Download ");
        	location.reload(true);
    	}, 1000); */
	}
	else {
		stopProgress();
		if (errmsg.length > 0)
			showError(errmsg);
		return;
	}
		
}

$(function() {
	$('#BG_BU').multiselect({
		includeSelectAllOption: true
	}).multiselectfilter();
});
</script>

	<div class="content-wrapper">
		<div class="botMargin">
			<h1>RMG - Resource Management Group Report</h1>
		</div>
		<div class="tab_seaction">
			<ul class='tabs'>
				<li><a href='#tab1' id="tab1id">List</a></li>

			</ul>
			<div id='tab1' class="tab_div">
				<div class="form">
					<form name="defaultFormName" method="post" id="defaultForm">
					 	<!-- <div class="ui-widget-header ui-corner-all ui-multiselect-header ui-helper-clearfix ui-multiselect-hasfilter"> -->
						<div  class="tblBorderDiv">
							<table id="mail" cellpadding="5" cellspacing="5" class="display dataTable" style="border-collapse:collapse;width:100%; margin-top:0px;">
							<tr>
								<input type="hidden" id="formHiddenId" name="id"></input>
							</tr>
							
					 		<tr> 
					 			<td align="right" width="10%"><strong>BG-BU<span style="color: red;">*</span> : </strong></td>

								<td align="left" width="10%">
										<div class="positionRel">
											<select id="BG_BU" class="required" multiple="multiple">
												<!-- <option value="-1"></option> -->
												<c:forEach items="${orgHierarchyList}" var="orgHierarchy">
													<option value="${orgHierarchy.parentId.name},${orgHierarchy.name}">${orgHierarchy.parentId.name}-${orgHierarchy.name}</option>
												</c:forEach>
											</select>
										</div>
								</td>
										
			 					<td align="right" width="10%"><strong>Select Duration <span style="color: red;">*</span>: </strong></td>
								<td align="left" width="10%">
									 <div class="stDtEnDt tsstDtEnDtWrapper">
									 <span style="height: 131px"> <input type="text"
											name="startdate" value="" id="startdate" size="35"
											maxlength="80" readonly="readonly" /></span>
									</div>
			 					</td>
			 					<td align="center" width="10%">
									<a class="pointer"
										onclick="skillRequestReportPDF()" title="Download RMG">Download <i class="fa fa-download" aria-hidden="true"></i></a>
			 					
					 		</tr>	
						 </table>
						</div>
					</form>
				</div>
				<div class="tbl"></div>
				<div class="clear"></div>
			</div>
		</div>

	
		
	</div>
</body>
</html>

