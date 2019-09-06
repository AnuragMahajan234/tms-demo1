<%@ page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<spring:url value="/resources/styles/style.css?ver=${app_js_ver}" var="style_css" /> 
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
	.customselect{position:relative;margin-bottom: 0;margin-top: 3px;}
	.customselect button.ui-multiselect{padding-left: 4px!important;padding-right: 3px!important;border-radius: 4px!important;}
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

		$('#orgHierarchy,#projectId').multiselect({
			includeSelectAllOption : true,
		}).multiselectfilter();

	});

	$(document).ready(function() {
		
		$('.ui-multiselect-all').trigger('click');

	//	$('#exportToExcel').hide();
	$('#exportToExcel').show();
	/* $("#startDatepicker").datepicker({
			dateFormat : 'yy-mm-dd',
			maxDate : 0,
			beforeShowDay : function(date) {
				return [ date.getDay() == 0, "" ]
			}
	}); */
	$("#endDatepicker").datepicker({
			dateFormat : 'yy-mm-dd',
			maxDate : 0,
			beforeShowDay : function(date) {
				return [ date.getDay() == 6, "" ]
			}
	});

	$('#records_tableId').hide();

	var serverCallRequired = false; 

	$('#orgHierarchy').change(function() {

		serverCallRequired = true;

		//clearLocation();
		clearProject();
		//clearResource();

	});
		
	$('#projectId').change(function() {
		serverCallRequired = true;
		oValue = $('#orgHierarchy').val();
		oValue1 = $('#projectId').val();

		/* if (serverCallRequired && oValue && $('#locationId').empty()) {
			$.getJSON("/rms/UtilizationReport/getLocation/" + oValue, {}, showLocation);
		}
		clearResource(); */
		serverCallRequired = false;
		
	});
	
	$('#orgHierarchy').multiselect({
		checkAll : function() {

			serverCallRequired = true;
		//	clearLocation();
			clearProject();
			//clearResource();
		},
		uncheckAll : function() {

			//clearLocation();
			//clearLocation();
			clearProject();
			//clearResource();
			
		},
		close : function(event) {
			var oValue = $('#orgHierarchy').val();

			if (serverCallRequired && (oValue != null || oValue != -1)) {

				/* $('#locationId').empty();
				$('#locationId').multiselect('refresh').multiselect(); */

				$('#projectId').empty();
				$('#projectId').multiselect('refresh').multiselect();
				
				/* $('#resource').empty();
				$('#resource').multiselect('refresh').multiselect(); */

				var aUrl = "/rms/UtilizationReport/getProject/" + oValue;
				//$.getJSON("/rms/UtilizationReport/getLocation/" + oValue, {}, showLocation);
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
	/* function showLocation(data) {

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
	} */
	
	// show Resource in dropdown
	/* function showResource(data) {
		if (data.length != 0) {
			var htmlVar = '';
			$.each(data, function(key, val) {
				htmlVar += '<option value="'+data[key].employeeId+'">' + data[key].firstName + data[key].lastName  +"[" +  data[key].yashEmpId +"]" + '</option> ';
			});
			$('#resource').append(htmlVar);
			$('#resource').multiselect('refresh').multiselect();
		} else {
			showError("Location not found for this BG-BU");
		}
	} */

	$('#orgHierarchy, #projectId , #endDatepicker').on('change', function(e) {
	});
		
		
	/*  $('#locationId').change(function() {
			serverCallRequired = true;
			pids = $('#projectId').val();
			bids = $('#orgHierarchy').val();
			lids = $('#locationId').val();
			
			if (serverCallRequired && bids && pids && lids && $('#resource').empty()) {
					$.getJSON("/rms/UtilizationReport/getResource/" + bids+'/'+pids+'/'+lids, {}, showResource);
				}			
		serverCallRequired = false;
	});  */

/* 	$('#locationId').multiselect({
		checkAll : function() {
			serverCallRequired = true;
			pids = $('#projectId').val();
			bids = $('#orgHierarchy').val();
			lids = $('#locationId').val();
			if (serverCallRequired && bids && pids && lids && $('#resource').empty()) {
					$.getJSON("/rms/UtilizationReport/getResource/" + bids+'/'+pids+'/'+lids, {}, showResource);
				}
		},
		uncheckAll : function() {
			clearResource();
		},
		close : function(event) {

			pids = $('#projectId').val();
			bids = $('#orgHierarchy').val();
			lids = $('#locationId').val();
			if (serverCallRequired && bids && pids && lids && $('#resource').empty()) {
					$.getJSON("/rms/UtilizationReport/getResource/" + bids+'/'+pids+'/'+lids, {}, showResource);
				}

			serverCallRequired = false;
		}
	}); */

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
					serverCallRequired = true;
					oValue = $('#orgHierarchy').val();
					oValue1 = $('#projectId').val();
					/* clearResource();
					if (serverCallRequired && oValue && $('#locationId').empty()) {
						$.getJSON("/rms/UtilizationReport/getLocation/" + oValue, {}, showLocation);
					} */
				},
				uncheckAll : function() {
					//clearLocation();
					//clearResource();
				},
				close : function(event) {

					oValue = $('#orgHierarchy').val();
					oValue1 = $('#projectId').val();
					
					/* if (serverCallRequired && oValue && $('#locationId').empty()) {

						$.getJSON("/rms/UtilizationReport/getLocation/" + oValue, {}, showLocation);
					} */
					serverCallRequired = false;
				}
			});
		} 

		/* $('#locationId > option:selected').each(function(i) {
			lValue[i] = $(this).val();
		});
		
		
		$('#resource').multiselect({
			checkAll : function() {
			},
			uncheckAll : function() {
			},			
		}); */
	});

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

	
	function exportDataToExcel() {
			
			var requestParam = "?";
			var orgIdList = $('#orgHierarchy').val();
			var locIdList ;//= $('#locationId').val();
			var projIdList = $('#projectId').val();
			var customerList ="";//= $('#customer').val();
			var employeeList ="";//= $('#resource').val();
			
		    var errmsg = "Please select:";
			var validationFlag = true;
			
			if (document.getElementById("endDatepicker").value == "") {
				errmsg = errmsg + "  End Date ";
				validationFlag = false;
			}
			if (document.getElementById("orgHierarchy").value == "") {
				errmsg = errmsg + "<br> Project BG-BU ";
				validationFlag = false;
			}
			/* if (document.getElementById("locationId").value == "") {
				errmsg = errmsg + "<br> Location ";
				validationFlag = false;
			} */
			if ($("#projectId").val() == null) {
				errmsg = errmsg + "<br> Project ";
				validationFlag = false;
			}
			/* if (document.getElementById("resource").value == "") {
				errmsg = errmsg + "<br> Employee ";
				validationFlag = false;
			} */
			/* if (document.getElementById("startDatepicker").value == "") {
				errmsg = errmsg + "<br> Start Date ";
				validationFlag = false;
			} else if (document.getElementById("endDatepicker").value != "" && document.getElementById("endDatepicker").value < document.getElementById("startDatepicker").value) {
				errmsg = "<br> End Date cannot be after Start Date";
				validationFlag = false;
			}*/ 
			if (!validationFlag) {

				stopProgress();
				if (errmsg.length > 0)

					showError(errmsg);
				return false;
				
			} else { 
			 
					if (document.getElementById("endDatepicker").value != "") {
						requestParam = requestParam + "endDate=" + document.getElementById("endDatepicker").value;
					}
					
					/* if (document.getElementById("startDatepicker").value != "") {
						requestParam = requestParam + "&startDate=" + document.getElementById("startDatepicker").value;
					} */
			
				/* 	var selectedCount = parseInt($($('select#resource').next('button').find('span')[1]).text().split(' ')[0]);
					if($('select#resource').find('option').length === selectedCount || selectedCount>100){
						employeeList = ["0"];
						}  */
				 	requestParam = requestParam + "&exportToExcelRequired=" + false;//exportToExcelServerCallRequired;
					//requestParam = requestParam + "&orgIdList=" + '20' + "&locIdList=" + '3' + "&projIdList=" + '134'  + "&employeeList=" + '1';
		
					requestParam = requestParam + "&orgIdList=" + orgIdList + "&locIdList=" + " " + "&projIdList=" + projIdList + "&employeeList=" + " ";
					
					var _href = "getTimeSheetDetailReport";  //$('#exportToExcel').attr('href');
					var res = _href.substring(0, 30);
					$('#exportToExcel').attr("href", res + requestParam)
					$("#RMReportForm").submit();
				
					exportToExcelServerCallRequired = false; 
			stopProgress(); 
		}
	}	
			
	function clearLocation() {

		$("#locationId").multiselect("uncheckAll");
		$('#locationId').empty();
		$('#locationId').multiselect('refresh').multiselect();
		/* $("#exportToExcel").hide(); */
	}

	function clearProject() {

		$("#projectId").multiselect("uncheckAll");
		$('#projectId').empty();
		$('#projectId').multiselect('refresh').multiselect();
		/* $("#exportToExcel").hide(); */
	}
	
	function clearResource() {

		$("#resource").multiselect("uncheckAll");
		$('#resource').empty();
		$('#resource').multiselect('refresh').multiselect();
		/* $("#exportToExcel").hide(); */
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
			<h1>Time Sheet Hours Report</h1>
		</div>
		<div class="tab_seaction">
			<ul class='tabs'>
				<li><a href='#tab1' id="tab1id">List</a></li>
			</ul>
			<div id='tab1' class="tab_div">
				<div class="form">
				<form id="RMReport" name="RMReport" action="TimeSheetReportController">

					<table id="RMReportTable" cellspacing="10" cellpadding="10">
						<tr>
							<!-- <td align="right">WeekStart Date :</td>&nbsp;
							<td align="left"><input type="text" id="startDatepicker" path="startDate" name="startDate"></td> -->
							<td align="right">WeekEnd Date :</td>&nbsp;
							<td align="left"><input type="text" id="endDatepicker" path="endDate" name="endDate"></td>
								
							<td align="right">Project BG-BU:</td>
							<td align="left">
							<div class="positionRel" >
									<select name="orgHierarchy.id" id="orgHierarchy" multiple="multiple" class="dropdown-search" >
										<c:forEach items="${orgHierarchy}" var="orgHierarchy">
											<option value="${orgHierarchy.id}">${orgHierarchy.parentId.name}-${orgHierarchy.name}</option>
										</c:forEach>
									</select>
									</div>
								
							</td>
						</tr>
						
						<tr>						
							<td>Project :</td>
							<td>
								<div class="customselect">
									<!-- select name="project.id" id="projectId" multiple="multiple" onchange="onProjectChange()" class="dropdown-search"></select-->
									<select name="projects.id" id="projectId" multiple="multiple" class="dropdown-search" onchange="onProjectChange()">
										<c:forEach items="${projects}" var="projects">
											<option value="${projects.id}">${projects.projectName}</option>
										</c:forEach>
									</select>
								</div>
							</td>	
							<%-- <td>Location :</td>
							<td>
								<div class="positionRel"> 
								<select name="locations.id" id="locationId" multiple="multiple" class="dropdown-search" onchange="onLocationChangeFunction()">
										<c:forEach items="${locations}" var="locations">
											<option value="${locations.id}">${locations.location}</option>
										</c:forEach>
									</select>
									<!-- select name="location.id" id="locationId" multiple="multiple" onchange="onLocationChangeFunction()" class="dropdown-search"></select-->
								</div>
							</td> --%>							
																
															
							<%-- <td>Employee Name :</td>
							<td><div class="positionRel" >
									<select name="resources.employeeId" id="resource" multiple="multiple" class="dropdown-search" hidden ="true">
										<c:forEach items="${resources}" var="resources">
											<option value="${resources.employeeId}">${resources.firstName}${resources.lastName}[${resources.yashEmpId}]</option>
										</c:forEach>
									</select>
								</div>
							</td> --%>
							<td></td>
							<td><a href="#" class="blue_link" id="exportToExcel" onclick="exportDataToExcel()">
							<i class="fa fa-file-excel-o" style="font-size: 15px"></i> Export To Excel </a></td>
							</tr>
							<tr>
								<td colspan="4">&nbsp;</td>
								<td colspan="4"><font color="#FF0000">** NOTE: Please click to "View Reports" button first to download the Excel **</font></td>
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
						<!-- <th align="center" valign="middle">WEEK END DATE</th>
						<th align="center" valign="middle">BILLED HOURS</th>
						<th align="center" valign="middle">PLANNED HOURS</th>
						<th align="center" valign="middle">TOTAL ACTUAL HOURS</th>
						<th align="center" valign="middle">PRODUCTIVE HOURS</th>
						<th align="center" valign="middle">NON PRODUCTIVE HOURS</th>
						<th align="center" valign="middle">UTILIZATION</th>						
						<th align="center" valign="middle">DATE</th>
						<th align="center" valign="middle">ACTUAL HOUR</th>
						<th align="center" valign="middle">ACTIVITY</th>
						<th align="center" valign="middle">MODULE</th>
						<th align="center" valign="middle">COMMENT</th> -->
						<th align="center" valign="middle">EMPLOYEE EMAIL</th>
						<th align="center" valign="middle">REPORTING MANAGER</th>
						<th align="center" valign="middle">LOCATION</th>
						<th align="center" valign="middle">PROJECT NAME</th>
						<th align="center" valign="middle">BU</th>					
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>