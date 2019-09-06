<%@ page import="org.yash.rms.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<spring:url
	value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url
	value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />
<spring:url
	value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}"
	var="multiselect_js" />
<spring:url
	value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}"
	var="multiselect_filter_js" />
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />

<spring:url value="/resources/images/spinner.gif" var="spinner" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Joiner Tracker Dashboard</title>

<!-- Code For disabling Submit button  -->
<script>
	$(document).ready(function() {
		$("#exceluploadbtn").prop('disabled', true);

		$("#exceluploadId").change(function() {
			$("#exceluploadbtn").prop('disabled', false);
		})
	});
</script>
<script src="${multiselect_js}" type="text/javascript"></script>
<script src="${multiselect_filter_js}" type="text/javascript"></script>
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<script>
$(document).ready(function() {
    $('#newjoinertrackertableId').DataTable( {
    	"sDom": 'RC<"clear"><"top"lfp>rt<"bottom"ip<"clear">',
    	"sPaginationType": "full_numbers",
    	"bAutoWidth" : false,
        /* "sScrollX": "100%",
        "sScrollY": "350", */
        "bScrollCollapse": true
    } );
    jQuery('.dataTable').wrap('<div class="dataTables_scroll" />');
} );
</script>
<style>
.dataTables_length {
	top: 3px;
}

.datatable-scroll {
	max-width: 2000px;
	overflow-x: auto;
	position: relative;
}

.dataTables_wrapper {
	width: 100%;
	margin: 0 auto;
	position: relative;
}

#newjoinertrackertableId {
	table-layout: fixed;
	background: transparent;
	border: 0;
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

#newjoinertrackertableId>thead>tr>th {
	width: 120px;
	word-wrap: break-word;
}

#newjoinertrackertableId >tbody>td {
	width: 120px;
	word-wrap: break-word;
}

.dtable td {
	background: #ebebeb;
}

.dataTables_info {
	margin-top: 6px;
}

.content-wrapper {
	min-height: 179px !important;
}
.dataTables_scroll
{
    overflow:auto;
}
</style>
</head>
<body>


	<div class="content-wrapper">
		<div class="botMargin">
			<h1>
				<a href="/rms/newjoinertracker/">New Joiner Tracker Dashboard</a>
			</h1>
		</div>

		<div id="uploadExcel">
			<form method="post" enctype="multipart/form-data"
				action="processExcel">
				<table align="center" class="display dataTable"
					style="border-collapse: collapse; width: 100%; margin-top: 0px;">
					<tr>
						<td colspan="2" align="right" width="100%"><a
							href="/rms/newjoinertracker/downloadSampleExcel">
								<button type="button" class="btn btn-info btn-sm">
									<span class="glyphicon glyphicon-download"></span> Download
									Sample Excel Sheet
								</button>
						</a></td>
					</tr>
					<tr>
						<td align="right" width="50%"><b>New Joiner Tracker File</b>
						</td>
						<td><input type="file" name="ExcelUpload" id="exceluploadId"
							accept=".csv,.xls,.xlsx"></td>
					</tr>
					<tr>
						<td align="center" colspan="2" width="100%"><input
							type="submit" id="exceluploadbtn"></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><c:if
								test="${messageType == 'error'}">
								<span style="color: red; font-size: 107%"><strong>${message}</strong></span>
							</c:if>
							<c:if
								test="${messageType == 'success'}">
								<span style="color: green; font-size: 107%"><strong>${message}</strong></span>
							</c:if>
						</td>
					</tr>
				</table>
			</form>

			<!-- <div id="toast-container" class="toast-top-right">
				<div class="toast toast-error" style="">
					<span class="toast-close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
					<div class="toast-title">Error</div>
					<div class="toast-message">Please select: Client</div>
				</div>
			</div> -->


		</div>



		<div id="newjoinertrackerdiv" class="tbl" style="margin-top: 3%; overflow:auto">
			<table id="newjoinertrackertableId"
				class="dataTbl display tablesorter dataTable dtable records-data-table">
				<thead>
					<tr>
						<th align="center" valign="middle">SR.NO.</th>
						<th align="center" valign="middle">BUSINESS GROUP</th>
						<th align="center" valign="middle">BUSINESS UNIT</th>
						<th align="center" valign="middle">BUSINESS GROUP HEAD</th>
						<th align="center" valign="middle">BUSINESS UNIT HEAD</th>
						<th align="center" valign="middle">REPORTING TO</th>
						<th align="center" valign="middle">NATURE OF HIRING</th>
						<th align="center" valign="middle">CLIENT</th>
						<th align="center" valign="middle">HIRE NAME</th>
						<th align="center" valign="middle">SKILLS</th>
						<th align="center" valign="middle">DESIGNATION</th>
						<th align="center" valign="middle">GRADE</th>
						<th align="center" valign="middle">EXPECTED DATE OF JOINING(DD-MM-YYYY)</th>
						<th align="center" valign="middle">BASE LOCATION</th>
						<th align="center" valign="middle">STATUS</th>
						<th align="center" valign="middle">REPORTING LOCATION</th>
						<th align="center" valign="middle">LWD(DD-MM-YYYY)</th>
						<th align="center" valign="middle">REMARK</th>
						<!-- <th align="center" valign="middle">UPDATE RESUME</th> -->
					</tr>
				</thead>
				<tbody>
					<c:forEach var="newJoiner" items="${newJoinerTrkrList}">
						<tr>
							<td><c:out value="${newJoiner.serialNumber}"></c:out></td>
							<td><c:out value="${newJoiner.businessGroup}"></c:out></td>
							<td><c:out value="${newJoiner.businessUnit}"></c:out></td>
							<td><c:out value="${newJoiner.businessGroupHead}"></c:out></td>
							<td><c:out value="${newJoiner.businessUnitHead}"></c:out></td>
							<td><c:out value="${newJoiner.reportingTo}"></c:out></td>
							<td><c:out value="${newJoiner.natureOfHiring}"></c:out></td>
							<td><c:out value="${newJoiner.client}"></c:out></td>
							<td><c:out value="${newJoiner.hireName}"></c:out></td>
							<td><c:out value="${newJoiner.skills}"></c:out></td>
							<td><c:out value="${newJoiner.designation}"></c:out></td>
							<td><c:out value="${newJoiner.grade}"></c:out></td>
							<td><c:out value="${newJoiner.expectedDateOfJoining}"></c:out></td>
							<td><c:out value="${newJoiner.baseLocation}"></c:out></td>
							<td><c:out value="${newJoiner.status}"></c:out></td>
							<td><c:out value="${newJoiner.reportingLocation}"></c:out></td>
							<td><c:out value="${newJoiner.lwd}"></c:out></td>
							<td><c:out value="${newJoiner.remarks}"></c:out></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>


</body>
</html>