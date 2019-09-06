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
<script src="${multiselect_js}" type="text/javascript"></script>
<spring:url
	value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}"
	var="multiselect_filter_js" />
<link rel="stylesheet"
	href="/rms/resources/dashboardscript/plugins/morris/morris.css" />
<link rel="bootStylesheet"
	href="/resources/dashboardscript/bootstrap/css/bootstrap.css" />
<spring:url
	value="/resources/js/moment.min.js?ver=${app_js_ver}" var="moment_js" />
<script src="${moment_js}" type="text/javascript"></script>	
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

<script src="${multiselect_filter_js}" type="text/javascript"></script>
<style type="text/css" title="currentStyle">
thead input {
	width: 100%
}

input.search_init {
	color: #999
}

#records_tableId {
	table-layout: initial;
	background: transparent;
	border: 0;
}

#records_tableId thead th {
	word-wrap: break-word;
	text-align: center;
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

.records-data-table {
	padding-top: 41px;
}

div.dataTables_wrapper {
	width: 100%;
	margin: 0 auto;
	position: relative;
}
</style>

<script>
	var recordTable;
	var oValue = new Array();
	var lValue = new Array();
	var pValue = new Array();

	$(function() {
		$(function() {
			$('#orgHierarchy, #locationId, #projectId').multiselect({
				includeSelectAllOption : true
			}).multiselectfilter();

		});
	});

	$(document).ready(function() {

		$('#exportToExcel').hide();
		
		//$("#weekStartDate").datepicker();
		$('#weekStartDate').val(moment(Date()).format('YYYY-MM-DD'));
		
		$('#records_tableId').hide();

		var serverCallRequired = false;

		$('#orgHierarchy').change(function() {

			serverCallRequired = true;

			clearLocation();
			clearProject();

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

					var aUrl = "/rms/muneerReports/getProject/" + oValue;
					$.getJSON("/rms/muneerReports/getLocation/" + oValue, {}, showLocation);
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

					if (serverCallRequired && oValue && $('#locationId').empty()) {
						
						$.getJSON("/rms/muneerReports/getLocation/" + oValue, {}, showLocation);
						
					}
				},
				uncheckAll : function() {

					clearLocation();
				},
				close : function(event) {

					oValue = $('#orgHierarchy').val();

					if (serverCallRequired && oValue && $('#locationId').empty()) {

						$.getJSON("/rms/muneerReports/getLocation/" + oValue, {}, showLocation);
					}

					serverCallRequired = false;
				}
			});
		}
		
		$('#projectId').change(function() {

			serverCallRequired = true;
			oValue = $('#orgHierarchy').val();

			if (serverCallRequired && oValue && $('#locationId').empty()) {
				
				$.getJSON("/rms/muneerReports/getLocation/" + oValue, {}, showLocation);
				
			}

			serverCallRequired = false;
			
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

		$('#orgHierarchy, #projectId ,#locationId, #RoleId, #ReportTypeId').on('change', function(e) {

			$("#exportToExcel").hide();

		});
		
		$('#locationId').multiselect({
			close : function(event) {

				oValue = $('#orgHierarchy').val();
				lValue = $('#locationId').val();

				 

				serverCallRequired = false;
			}
		});


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

	function onRoleChange() {
		var roleId = $("#RoleId :selected").val();
		if (roleId == "Release_Date_IS_Not_NULL") {
			$("#ReportTypeId").attr("disabled", "disabled");
			$("#weekStartDate").attr("disabled", "disabled");
			
			
		} else {
			//$("#weekStartDate").removeAttr("disabled", "disabled");
			$("#weekStartDate").attr("disabled", "disabled");
			$("#ReportTypeId").removeAttr("disabled", "disabled");
		}
	}

	$(document).on('click','#submit',function() {

		var orgIdList = $('#orgHierarchy').val();
		var locIdList = $('#locationId').val();
		var projIdList = $('#projectId').val();
		var weekStartDate = $("#weekStartDate").val();

		var roleId = $("#RoleId :selected").val();
		var reportId = $("#ReportTypeId :selected").val();

		var records_table;

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

		if($("#RoleId :selected").val() != "Release_Date_IS_Not_NULL") {
			if (document.getElementById("weekStartDate").value == "") {
				errmsg = errmsg + "<br> End Date ";
				validationFlag = false;
			}
		}
		

		if (document.getElementById("RoleId").value == "-1") {
			errmsg = errmsg + "<br> Role ";
			validationFlag = false;
		}

		if ($("#RoleId :selected").val() != "Release_Date_IS_Not_NULL") {
			if (reportId == -1) {
				errmsg = errmsg + "<br> Report Type ";
				validationFlag = false;
			}
		}

		if (!validationFlag) {

			stopProgress();
			if (errmsg.length > 0)

				showError(errmsg);

			return;
		} else {

			$('#records_tableId').show();
			$("#exportToExcel").show();

		$.ajax({
			
						url : '/rms/muneerReports/getMuneerReport',
						contentType : "text/html; charset=utf-8",
						async : false,
						data : "orgIdList=" + orgIdList + "&locIdList="
								+ locIdList + "&projIdList=" + projIdList
								+ "&roleId=" + roleId + "&reportId=" + reportId
								+ "&weekDate=" + weekStartDate,

						success : function(data) {
							
							if (data.length != 0) {
								
								$('#records_tableId').dataTable().fnClearTable();

								var i = 1;
								$.each(data,function(key, val) {

													val['SrNo'] = '' + i;
													i++;

													var releaseDate = val.releaseDate;
													var transferDate = val.transferDate;
													if (releaseDate == null) {
														releaseDate = "";
													}
													if (transferDate == null) {
														transferDate = "";
													}
													var currentBu = "", currentBg = "", project = "", subProject = "";
													if (val.ownership == "Loan") {
														currentBg = val.currentBgName;
														currentBu = val.currentBuName;
													}
													if (null != val.teamName
															&& val.teamName != "") {
														project = val.teamName;
														subProject = val.primaryProject;
													} else {
														project = val.primaryProject;
													}
													var percentAlloc = "";
													if(val.percentageAllocation!='undefined' && val.percentageAllocation!=null){
														percentAlloc = val.percentageAllocation;
													}else{
														percentAlloc = "0";
													}

													$('#records_tableId').dataTable().fnAddData(
																	[
																			val.SrNo,
																			val.yashEmpID,
																			val.employeeName,
																			val.bg,
																			val.bu,
																			val.ownership,
																			currentBg,
																			currentBu,
																			val.parentBu,
																			val.competency,
																			val.baseLocation,
																			val.currentLocation,
																			val.allocationType,
																			val.allocationStartDate,
																			"'" + val.allocatedSince + "'",
																			val.billable,
																			val.customerName,
																			project,
																			subProject,
																			"",
																			val.remarks,
																			val.designation,
																			val.grade,
																			val.primarySkill,
																			val.secondarySkill,																			
																			"" + percentAlloc + ""]);
												});

							}
						},
						error : function(errorResponse) {
							showError("No Data Available");
							stopProgress();
							$('#records_tableId').dataTable().fnClearTable();
						}

					});

		}
	});

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

<html>
<body>
	<div class="content-wrapper">
		<div class="botMargin">
			<h1>Management - Resource List </h1>
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
							
								<td>BG-BU :</td>
								<td><div class="positionRel floatR">
										<select name="orgHierarchy.id" id="orgHierarchy"
											multiple="multiple" class="dropdown-search">
											<c:forEach items="${orgHierarchy}" var="orgHierarchy">
												<option value="${orgHierarchy.id}">${orgHierarchy.parentId.name}-${orgHierarchy.name}</option>
											</c:forEach>

										</select>
								</div></td>
								
								<td>Project :</td>
								<td><div class="positionRel floatR">
										<select name="project.id" id="projectId" multiple="multiple"
											onchange="onProjectChange()" style="height: 26px;"
											class="orderselect">
										</select>
								</div></td>

								<td>Location :</td>
								<td><div class="positionRel floatR">
										<select name="location.id" id="locationId" multiple="multiple"
											style="height: 26px;"
											class="orderselect">

										</select>
								</div></td>

							</tr>
							<tr>

								<td>Role :</td>
								<td><select name="OnRole.id" id="RoleId"
									onchange="onRoleChange()" class="orderselect">
										<option value="-1">Select Role</option>
										<option value="Release_Date_IS_NULL">Active</option>
										<option value="Release_Date_IS_Not_NULL">InActive</option>

								</select></td>



								<!-- <td>Week End Date :</td> -->
								<td>Current Date :</td>

								<td>&nbsp;&nbsp;&nbsp;<input type="text" name="weekStartDate" id="weekStartDate" readonly="readonly" disabled="disabled"/></td>



								<td>ReportType :</td>
								<td>&nbsp;&nbsp;&nbsp;<select name="ReportType.id"
									id="ReportTypeId" class="orderselect">
										<option value="-1">Select ReportType
										<option value="1">Primary</option>
										<option value="2">Discrepancy</option>
								</select></td>

							</tr>
							<tr>

								<td><a href="#" class="blue_link"
									id="submit"><span
										class="glyphicon glyphicon-list-alt"></span> View Report </a></td>

								<td><a href="exportToExcel"
									class="blue_link" id="exportToExcel"><i
										class="fa fa-file-excel-o" style="font-size: 15px"></i> Export
										To Excel </a></td>
							</tr>
							<tr>
								<td colspan="3">&nbsp;</td>
								<td colspan="3"><font color="#FF0000">** NOTE:
										Please click to "View Report" button first to download the
										Excel **</font></td>
							</tr>
							<tr>
								<td colspan="3">&nbsp;</td>
								<td colspan="3"><font color="#FF0000">** Management Report is as on date report **</font></td>
							</tr>
						</table>
					</form>
				</div>
				<div class="tbl"></div>
				<div class="clear"></div>
			</div>



		</div>

		<div style="overflow: auto;">

			<table
				class="dataTbl display tablesorter dataTable dtable records-data-table "
				id="records_tableId">
				<thead>
					<tr>
						<th align="center"></th>
						<th align="center" colspan="2">From Payroll Records ENo/Ename</th>
						<th align="center" colspan="2">Business Groups</th>
						<th align="center"></th>
						<th align="center" colspan="2">If On Loan Or Loaned</th>
						<th align="center" colspan="2">Division By Skill</th>
						<th align="center" colspan="2">Division By Location</th>
						<th align="center" colspan="8">Division By Allocation / Billability</th>
						<th align="center"></th>
						<th align="center"></th>
						<th align="center"></th>
						<th align="center"></th>
						<th align="center"></th>
						<th align="center">Percentage Allocation</th>
					</tr>

					<tr>
						<th align="center">S.NO</th>
						<th align="center">HRIMS Code</th>
						<th align="center">Name</th>
						<th align="center">BG</th>
						<th align="center">BU</th>
						<th align="center">Ownership</th>
						<th align="center">BG</th>
						<th align="center">BU</th>
						<th align="center">Practise</th>
						<th align="center">Competency</th>
						<th align="center">Base Location</th>
						<th align="center">Deployment Location</th>
						<th align="center">Allocation</th>
						<th align="center">Start Date</th>
						<th align="center">Allocated Since(No. Of Days)</th>
						<th align="center">Billable</th>
						<th align="center">Client</th>
						<th align="center">Project</th>
						<th align="center">SubProject</th>
						<th align="center">Available to</th>
						<th align="center">Remark From Delivery Unit / Business Unit</th>
						<th align="center">Designation</th>
						<th align="center">Grade</th>
						<th align="center">Primary Skill</th>
						<th align="center">Secondary Skill</th>
						<th align="center">Percentage Allocation</th>
					</tr>

				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>

