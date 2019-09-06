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
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<spring:url
	value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}"
	var="multiselect_js" />
<script src="${multiselect_js}" type="text/javascript"></script>
<spring:url
	value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}"
	var="multiselect_filter_js" />
<link rel="stylesheet"
	href="/rms/resources/dashboardscript/plugins/morris/morris.css" />
<link rel="bootStylesheet"
	href="/resources/dashboardscript/bootstrap/css/bootstrap.css" />
<spring:url
	value="/resources/styles/skin/ui.dynatree.css?ver=${app_js_ver}"
	var="ui_dynatree_css" />
<link href="${ui_dynatree_css}" rel="stylesheet" type="text/css"></link>
<!-- <link href="resources/styles/skin/ui.dynatree.css" rel="stylesheet" type="text/css" id="skinSheet"> -->
<spring:url
	value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url
	value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />
<spring:url
	value="resources/js-framework/jquery.cookie.js?ver=${app_js_ver}"
	var="js_cookie" />
<spring:url
	value="/resources/js-framework/jquery.dynatree.js?ver=${app_js_ver}"
	var="js_dynatree" />
<script src="${js_cookie}" type="text/javascript"></script>
<script src="${js_dynatree}" type="text/javascript"></script>
<spring:url value="/resources/images/spinner.gif" var="spinner" />
<!--   <script src="resources/js-framework/jquery.cookie.js" type="text/javascript"></script>
  <script src="resources/js-framework/jquery.dynatree.js" type="text/javascript"></script> -->


<script src="${multiselect_filter_js}" type="text/javascript"></script>

<style type="text/css" title="currentStyle">
#ReportTable td {
	padding: 10px;
}

.dtable td {
	background: #ebebeb;
	/* cell-padding:"0";
    cell-spacing:"0";
	margin: 12px 12px 12px 12px;
	border-width: 12px;
	border-style: solid; */
}

.datatable-scroll {
	overflow-x: auto;
	max-width: 100%;
	display: inline-block;
}
</style>
<script>
$(document).ready(function() {
	
	$("#resource").change(function () {
		 $('.noWidth').each(function(){
                this.checked = true;
            });
    });
	
	$('#resourceReleaseSummaryReportTable').hide();
});

/* click on view report */
	$(document).on('click', '#submit', function() {

		lValue = $("#resource").val();
		var errmsg = "Please select:";
		var validationFlag = true;
		if ($("#resource").val() == null) {
			errmsg = errmsg + "<br> resource ";
			validationFlag = false;
		}	

		if (!validationFlag) {
			stopProgress();
			if (errmsg.length > 0)
				showError(errmsg);
			return;
		} else {
			var resourceId=$('#resource').val();
			$.ajax({
				type : 'GET',
				url : '/rms/releasefeedback/'+resourceId,
				success : function(reportData) {
					if (!JSON.parse(reportData).length) {
						$("#NoAllocMessage").show();
						$('#resourceReleaseSummaryReportTable').hide();
						var htmlVarObj = '<div id="NoAllocMessage" class="NoAllocMessage">No Record Exist for selected resource</div>';
						$('#NoAllocMessage').empty().append(htmlVarObj);

					} else {
						$("#NoAllocMessage").hide();
						showReport(reportData);
					} 
				}
			});
		}
	});
	
	function exportToPDF(event)
	{
		var resourceId = $('#resource').val();
		$.ajax({
			type : 'GET',
			url : '/rms/releasefeedback/'+resourceId,
			success : function(reportData) {
				if (!JSON.parse(reportData).length) {
					$("#NoAllocMessage").show();
					$('#resourceReleaseSummaryReportTable').hide();
					var htmlVarObj = '<div id="NoAllocMessage" class="NoAllocMessage">No Record Exist for selected resource</div>';
					$('#NoAllocMessage').empty().append(htmlVarObj);
				} else {
					window.location.href='exportResourceReleaseSummaryToPdf/?resourceId='+resourceId;
					return false;
				} 
			}
		});
	}
	
	function showReport(resourceSummaries) {
		$('#resourceReleaseSummaryReportTable').show();
		var htmlVar = "";
		var resourceSummaries=JSON.parse(resourceSummaries);
		$.each(resourceSummaries, function(i, resourceSummary)
				  {
					htmlVar+="<tr>";	
					htmlVar+="<td>"+resourceSummary.employeeId+"</td>";
					htmlVar+="<td>"+resourceSummary.employeeName+"</td>";
					htmlVar+="<td>"+resourceSummary.jobTitle+"</td>";
					htmlVar+="<td>"+resourceSummary.joiningDate+"</td>";
					htmlVar+="<td>"+resourceSummary.currentBGBU+"</td>";
					htmlVar+="<td>"+resourceSummary.managerName+"</td>";
					htmlVar+="<td>"+resourceSummary.allocStartDate+"</td>";
					htmlVar+="<td>"+resourceSummary.allocEndDate+"</td>";
					htmlVar+="<td>"+resourceSummary.primarySkills+"</td>";
					htmlVar+="<td>"+resourceSummary.clientName+"</td>";
					htmlVar+="<td>"+resourceSummary.trainingName+"</td>";
					htmlVar+="<td>"+resourceSummary.teamHandleExperiance+"</td>";
					htmlVar+="<td>"+resourceSummary.ktStatus+"</td>";
					htmlVar+="<td>"+resourceSummary.reasonForRelease+"</td>";
					htmlVar+="<td>"+resourceSummary.pipStatus+"</td>";
					htmlVar+="<td>"+resourceSummary.jobKnowledgeRating+"</td>";
					htmlVar+="<td>"+resourceSummary.workQualityRating+"</td>";
					htmlVar+="<td>"+resourceSummary.attandanceRating+"</td>";
					htmlVar+="<td>"+resourceSummary.intiativeRating+"</td>";
					htmlVar+="<td>"+resourceSummary.communicationRating+"</td>";
					htmlVar+="<td>"+resourceSummary.listingSkillsRating+"</td>";
					htmlVar+="<td>"+resourceSummary.dependabilityRating+"</td>";
					htmlVar+="<td>"+resourceSummary.overAllRating+"</td>"; 
					htmlVar+="</tr>";
			}); 
		htmlVar += " </table>";
		$("#resourceReleaseSummaryReportTable tbody").html(htmlVar);
		}
</script>

<html>
<body>

	<div class="spinner-wrap">
		<span class="spinner"></span>
	</div>


	<div class="content-wrapper">
		<div class="botMargin">
			<h1>Resource Feedback Report</h1>
		</div>
		<div class="tab_seaction">
			<ul class='tabs'>
				<li><a href='#tab1' id="tab1id">List</a></li>

			</ul>
			<div id='tab1' class="tab_div">


				<div class="form">

					<form method="post" id="moduleReportForm" name="moduleReportForm">
						<table id="ReportTable">

							<tr>
								<td>
									<div class="positionRel floatR">
										<label>Resource :<span class="astric">*</span></label> 
										<select
											name="resource.employeeId" class="comboselect" id="resource"
											class="required">

											<c:forEach items="${resourceList}" var="resource">
												<option value="${resource.employeeId}">${resource.firstName} ${resource.lastName}[${resource.yashEmpId}]</option>

											</c:forEach>

										</select>
									</div>
								</td>

							</tr>
							<tr>
								<td><a href="#" class="black_link" id="submit"><i
										class="fa fa-file-excel-o"></i> View Report </a></td>
										
								<td><a href="javascript:void(0);" class="black_link"
									id="exportToPdf" onclick="exportToPDF(this)"><i class="fa fa-file-excel-o"></i> Export To Pdf </a></td>
							</tr>

						</table>

					</form>
				</div>

				<div class="tbl"></div>
				<div class="clear"></div>
			</div>
		</div>
		<div class="datatable-scroll">
	<table id="resourceReleaseSummaryReportTable" class="dataTable dtable">
	<thead>
	<tr>
    <th rowspan="2" align="center">Employee Id Number</th>
    <th rowspan="2" align="center">Employee Name</th>
    <th rowspan="2" align="center">Job Title</th>
    <th rowspan="2" align="center" >Date</th>
    <th rowspan="2" align="center">Department/Unit</th>
    <th rowspan="2" align="center">Manager Name</th>
    <th rowspan="2" align="center">Review Period From</th>
    <th rowspan="2" align="center">Review Period To</th>
    <th colspan="7" align="center">Skill Detail</th>
    <th colspan="8" align="center">ON THE SCALE OF 1 TO 5(1 BEING LOWEST AND 5 BEING HIGHEST)</th> 
  </tr>
  <tr>
    <th align ="center">Primary Skill</th>
    <th align="center">Client Name</th>
    <th align="center">Training If give any</th>
    <th align="center">Team Handling Experience</th>
    <th align="center">KT Status For Previous Project</th>
    <th align="center">Reason For release</th>
    <th align="center">PIP IF Any Last 6 Months</th>
    <th align="center">Job Knowledge Domain</th>
    <th align="center">Work Quality</th>
    <th align="center">Attendence/Punctuality</th>
    <th align="center">Initiative/Pro-Activeness</th>
    <th align="center">Communication</th>
    <th align="center">Listening Skill</th>
    <th align="center">Dependability</th>
    <th align="center">Overall Rating</th>
  </tr>
  </thead>
  <tbody>
  </tbody>
</table>
	</div>
		<div>
			<table id="NoAllocMessage"></table>
		</div>

	</div>
</body>
</html>
