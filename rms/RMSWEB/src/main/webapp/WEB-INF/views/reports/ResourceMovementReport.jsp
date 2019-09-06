<%@ page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" /> 
<link href="${style_css}" rel="stylesheet" type="text/css"></link>

<spring:url
	value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}"
	var="multiselect_js" />

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
<spring:url
	value="/resources/js-framework/datatables/dataTables.rowsGroup.js?ver=${app_js_ver}"
	var="rowsGroup_js" />
<script src="${js_cookie}" type="text/javascript"></script>
<script src="${js_dynatree}" type="text/javascript"></script>

<!--   <script src="resources/js-framework/jquery.cookie.js" type="text/javascript"></script>
  <script src="resources/js-framework/jquery.dynatree.js" type="text/javascript"></script> -->

<script src="${multiselect_js}" type="text/javascript"></script>
<script src="${multiselect_filter_js}" type="text/javascript"></script>

<style type="text/css" title="currentStyle">
thead input {
	width: 100%
}

input.search_init {
	color: #999
}

#records_tableId {
	table-layout: fixed;
}

#records_tableId thead th {
	width: 120px;
}

#records_tableId td {
	word-wrap: break-word;
}

#RMReportTable td {
	padding: 10px;
}

.dtable td {
	background: #ebebeb;
}

.datatable-scroll {
	max-width: 2000px;
	overflow-x: auto;
	position: relative;
}
</style>

<script>
	var recordTable;
	var oValue = new Array();
	var lValue = new Array();
	var pValue = new Array();
	var exportToExcelServerCallRequired = false;

	$(function() {

		$('#orgHierarchy, #locationId, #projectId, #AllocationType').multiselect({
			includeSelectAllOption : true
		}).multiselectfilter();

	});

	$(document).ready(function() {

		$('#exportToExcel').hide();
		$("#startDatepicker").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("#endDatepicker").datepicker({
			dateFormat : 'yy-mm-dd'
		});

		$('#AllocationType').multiselect({
			checkAll : function() {
				$("#exportToExcel").hide();				
			},
			uncheckAll : function() {
				$("#exportToExcel").hide();
				
			}
		});

		$('#records_tableId').hide();

		var serverCallRequired = false;

		$('#orgHierarchy').change(function() {

			serverCallRequired = true;

			clearLocation();
			clearProject();

		});
		
		$('#projectId').change(function() {
			$("#exportToExcel").hide();
			serverCallRequired = true;
			oValue = $('#orgHierarchy').val();

			if (serverCallRequired && oValue && $('#locationId').empty()) {
				
				$.getJSON("/rms/ResourceMovementReports/getLocation/" + oValue, {}, showLocation);
				
			}

			serverCallRequired = false;
			
		});
		$('#orgHierarchy').multiselect({
			checkAll : function() {

				serverCallRequired = true;
				clearLocation();
				clearProject();
			},
			uncheckAll : function() {

				clearLocation();
				clearProject();
			},
			close : function(event) {
				var oValue = $('#orgHierarchy').val();

				if (serverCallRequired && (oValue != null || oValue != -1)) {

					$('#locationId').empty();
					$('#locationId').multiselect('refresh').multiselect();

					$('#projectId').empty();
					$('#projectId').multiselect('refresh').multiselect();

					var aUrl = "/rms/ResourceMovementReports/getProject/" + oValue;
					$.getJSON("/rms/ResourceMovementReports/getLocation/" + oValue, {}, showLocation);
					$.ajax({
						url : aUrl,
						method : 'GET',
						success : function(data) {
							showProject(data);
						}
					});
				}

				serverCallRequired = false;
			}
		});

		// show location in dropdown
		function showLocation(data) {

			if (data.length != 0) {

				var htmlVar = '';

				$.each(data, function(key, val) {

					htmlVar += '<option value="'+data[key].id+'">' + data[key].location + '</option> ';

				});

				$('#locationId').append(htmlVar);

				$('#locationId').multiselect('refresh').multiselect();

			} else {
				showError("Location not found for this BG-BU");
			}
		}

		$('#orgHierarchy, #projectId ,#locationId, #AllocationType, #startDatepicker, #endDatepicker').on('change', function(e) {
			$("#exportToExcel").hide();
		});

		$('#locationId').multiselect({
			checkAll : function() {
				$("#exportToExcel").hide();				
			},
			uncheckAll : function() {
				$("#exportToExcel").hide();
				
			},
			close : function(event) {

				oValue = $('#orgHierarchy').val();
				lValue = $('#locationId').val();

				 

				serverCallRequired = false;
			}
		});

		// show project in dropdown
		function showProject(data) {

			if (data.length != 0) {
				var htmlVar = '';

				$.each(data, function(key, val) {

					htmlVar += '<option value="'+data[key].id+'">' + data[key].projectName + '</option> ';
				});

				 

				$('#projectId').append(htmlVar);

				$('#projectId').multiselect('refresh').multiselect();

			} else {

				showError("Projects not found for this BG-BU And Location");
			}

			$('#projectId').multiselect({
				checkAll : function() {
					$("#exportToExcel").hide();
					serverCallRequired = true;
					oValue = $('#orgHierarchy').val();

					if (serverCallRequired && oValue && $('#locationId').empty()) {
						
						$.getJSON("/rms/ResourceMovementReports/getLocation/" + oValue, {}, showLocation);
						
					}
				},
				uncheckAll : function() {
					$("#exportToExcel").hide();
					clearLocation();
				},
				close : function(event) {

					oValue = $('#orgHierarchy').val();

					if (serverCallRequired && oValue && $('#locationId').empty()) {

						$.getJSON("/rms/ResourceMovementReports/getLocation/" + oValue, {}, showLocation);
					}

					serverCallRequired = false;
				}
			});
		}

		$('#locationId > option:selected').each(function(i) {
			lValue[i] = $(this).val();
		});

	})

	function onProjectChange() {

		if (pValue.length != 0 || pValue != null) {
			var valArr = pValue;
			var size = valArr.length;
			for (var i = 0; i < size; i++) {
				$("#projectId").multiselect("widget").find(":checkbox[value='" + valArr[i] + "']").attr("checked", "checked");
				$("#projectId option[value='" + valArr[i] + "']").attr("selected", true);
				$("#projectId").multiselect("refresh");
			}

		}

		$('#projectId').multiselect({
			close : function(event) {
				pValue = $('#projectId').val();

			}

		});

		$('#projectId > option:selected').each(function(i) {
			pValue[i] = $(this).val();
		});
	}

	function exportDataToExcel(event) {
		
			var requestParam = "?";
			var orgIdList = $('#orgHierarchy').val();
			var locIdList = $('#locationId').val();
			var projIdList = $('#projectId').val();
			var AllocationType = $('#AllocationType').val();
			
			if (document.getElementById("endDatepicker").value != "") {
				requestParam = requestParam + "endDate=" + document.getElementById("endDatepicker").value;
			}
	
			if (document.getElementById("startDatepicker").value != "") {
				requestParam = requestParam + "&startDate=" + document.getElementById("startDatepicker").value;
			}
	
			requestParam = requestParam + "&exportToExcelRequired=" + exportToExcelServerCallRequired;
			
			requestParam = requestParam + "&orgIdList=" + orgIdList + "&locIdList=" + locIdList + "&projIdList=" + projIdList + "&AllocationTypeID=" + AllocationType;
			
			var _href = $('#exportToExcel').attr('href');
			var res = _href.substring(0, 30);
			$('#exportToExcel').attr("href", res + requestParam)
			$("#RMReportForm").submit();
		
		exportToExcelServerCallRequired = false;
	}

	function submitDetail() {
		var orgIdList = $('#orgHierarchy').val();
		var locIdList = $('#locationId').val();
		var projIdList = $('#projectId').val();
		var AllocationType = $('#AllocationType').val();
		var date = $('#startDatepicker').val();
		var endDate = $('#endDatepicker').val();

		var errmsg = "Please select:";
		var validationFlag = true;

		if (document.getElementById("orgHierarchy").value == "") {
			errmsg = errmsg + "<br> BG-BU ";
			validationFlag = false;
		}
		if (document.getElementById("locationId").value == "") {
			errmsg = errmsg + "<br> Location ";
			validationFlag = false;
		}
		if ($("#projectId").val() == null) {
			errmsg = errmsg + "<br> Project ";
			validationFlag = false;
		}
		if (document.getElementById("AllocationType").value == "") {
			errmsg = errmsg + "<br> Allocation Type ";
			validationFlag = false;
		}
		if (document.getElementById("startDatepicker").value == "") {
			errmsg = errmsg + "<br> Start Date ";
			validationFlag = false;
		} else if (document.getElementById("endDatepicker").value != "" && document.getElementById("endDatepicker").value < document.getElementById("startDatepicker").value) {
			errmsg = "<br> End Date cannot be after Start Date";
			validationFlag = false;
		}
		if (!validationFlag) {

			stopProgress();
			if (errmsg.length > 0)

				showError(errmsg);

			return;
		} else {

			$('#records_tableId').show();
			$('#records_tableId').dataTable().fnClearTable();

			var requestParam = "orgIdList=" + orgIdList + "&locIdList=" + locIdList + "&projIdList=" + projIdList + "&AllocationTypeID=" + AllocationType + "&date=" + date;

			if (document.getElementById("endDatepicker").value != "") {
				requestParam = requestParam + "&endDate=" + endDate
			}
			startProgress();
			$.ajax({

				url : '/rms/ResourceMovementReports/getResourceMovementReport',
				contentType : "application/json; charset=utf-8",
				async : false,
				data : requestParam,
				dataType : 'json',
				success : function(data) {

					if (data.length != 0) {

						$("#exportToExcel").show();
						exportToExcelServerCallRequired = true;
						$('#records_tableId').dataTable().fnClearTable();

						var i = 1;

						$.each(data, function(key, val) {

							val['SrNo'] = '' + i;

							i++;

							$('#records_tableId').show();

							$('#records_tableId').dataTable().fnAddData([ val.SrNo, val.yashEmpID, val.employeeName, val.BGBUName, val.startDate, val.projectName, val.allocationType ]);
						});
					} else {

						exportToExcelServerCallRequired = false;
						$("#exportToExcel").hide();
						$('#records_tableId').dataTable().fnClearTable();
					}
				},
				error : function(errorResponse) {
					$("#exportToExcel").hide();
					var row = '<tr><td>No Data Available</td></tr>';
					 
					$('#records_tableId tbody').append(row);
				}
			});
		}
		stopProgress();
	}

	function clearLocation() {

		$("#locationId").multiselect("uncheckAll");
		$('#locationId').empty();
		$('#locationId').multiselect('refresh').multiselect();
		$("#exportToExcel").hide();
	}

	function clearProject() {

		$("#projectId").multiselect("uncheckAll");
		$('#projectId').empty()
		$('#projectId').multiselect('refresh').multiselect();
		$("#exportToExcel").hide();
	}
</script>
<!-- <style>
div.dataTables_wrapper {
	width: 800px;
	margin: 0 auto;
}
</style> -->
<html>
<body>
	<div class="content-wrapper">
		<div class="botMargin">
			<h1>Resource Movement Report</h1>
		</div>
		<div class="tab_seaction">
			<ul class='tabs'>
				<li><a href='#tab1' id="tab1id">List</a></li>
			</ul>
			<div id='tab1' class="tab_div">
				<div class="form">
					<form id="RMReport" name="RMReport" action="RMReportController">

						<table id="RMReportTable" cellspacing="10" cellpadding="10">
							<tr>
								<td>Project BG-BU :</td>
								<td align="center"><div class="positionRel floatR">
										<select name="orgHierarchy.id" id="orgHierarchy" multiple="multiple" class="dropdown-search">
											<c:forEach items="${orgHierarchy}" var="orgHierarchy">
												<option value="${orgHierarchy.id}">${orgHierarchy.parentId.name}-${orgHierarchy.name}</option>
											</c:forEach>
										</select>
									</div></td>

								<td>Project :</td>
								<td>
									<div class="positionRel floatR">
										<select name="project.id" id="projectId" multiple="multiple" onchange="onProjectChange()" class="dropdown-search"></select>
									</div>
								</td>

								<td>Location :</td>
								<td>
									<div class="positionRel">
										<select name="location.id" id="locationId" multiple="multiple" onchange="onLocationChangeFunction()" class="dropdown-search"></select>
									</div>
								</td>

							</tr>
							<tr>
								<td align="left">Allocation Type :</td>
								<td align="left">
									<div class="positionRel">
										<select name="AllocationType.id" id="AllocationType"
											multiple="multiple">
											<c:forEach items="${AllocationType}" var="AllocationType">
												<option value="${AllocationType.id}">${AllocationType.allocationType}-${AllocationType.id}</option>
											</c:forEach>
										</select>
									</div>
								</td>
								<td align="left">Start Date :</td>
								<td align="left"><input type="text" id="startDatepicker" path="startDate" name="startDate"></td>
								<td align="left">End Date :</td>
								<td align="left"><input type="text" id="endDatepicker" path="endDate" name="endDate"></td>
								<td><a href="#" class="blue_link" id="submit" onclick="submitDetail()"> 
								<span class="glyphicon glyphicon-list-alt"></span> View Report </a></td>
								<td><a href="getResourceMovementReportExcel" class="blue_link" id="exportToExcel" onclick="exportDataToExcel(this)">
								<i class="fa fa-file-excel-o" style="font-size: 15px"></i> Export To Excel </a></td>
							</tr>
							<tr>
								<td colspan="5">&nbsp;</td>
								<td colspan="3"><font color="#FF0000">** NOTE: Please click to "View Reports" button first to download the Excel **</font></td>
							</tr>
						</table>
					</form>
				</div>
				<div class="tbl"></div>
				<div class="clear"></div>
			</div>
		</div>
		<div class="datatable-scroll">
			<table id="records_tableId" class="dataTable dtable">
				<thead>
					<tr>
						<th align="center" valign="middle">S.NO</th>
						<th align="center" valign="middle">EMPLOYEE ID</th>
						<th align="center" valign="middle">EMPLOYEE NAME</th>
						<th align="center" valign="middle">EMP. PARENT BG-BU</th>
						<th align="center" valign="middle">ALLOCATION DATE</th>
						<th align="center" valign="middle">PROJECT</th>
						<th align="center" valign="middle">ALLOCATION TYPE</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>