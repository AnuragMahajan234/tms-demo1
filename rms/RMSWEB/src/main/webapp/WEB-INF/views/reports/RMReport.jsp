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
.spinner-wrap {
	position: absolute;
	width: 100%;
	height: 100%;
	z-index: 9999;
	display: none;
	top: 0;
	left: 0;
}

.spinner {
	background:
		url('/rms/resources/dashboardscript/bootstrap/img/spinner.gif')
		no-repeat center center;
	width: 66px;
	height: 66px;
	position: absolute;
	top: 50%;
	left: 50%;
	display: block;
	margin-top: -35px
}

thead input {
	width: 100%
}

input.search_init {
	color: #999
}

#records_tableId {
	table-layout: fixed;
	background: transparent;
	border: 0;
}

#records_tableId thead th {
	width: 120px;
	word-wrap: break-word;
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
		
		$('#records_tableId').hide();

		var serverCallRequired = false;

		$('#orgHierarchy').change(function() {

			serverCallRequired = true;

			clearLocation();
			clearProject();

		});
		
		$('#projectId').change(function() {

			serverCallRequired = true;
			oValue = $('#orgHierarchy').val();

			if (serverCallRequired && oValue && $('#locationId').empty()) {
				
				$.getJSON("/rms/rmReports/getLocation/" + oValue, {}, showLocation);
				
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

					var aUrl = "/rms/rmReports/getProject/" + oValue;
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
						
						$.getJSON("/rms/rmReports/getLocation/" + oValue, {}, showLocation);
						
					}
				},
				uncheckAll : function() {

					clearLocation();
				},
				close : function(event) {

					oValue = $('#orgHierarchy').val();

					if (serverCallRequired && oValue && $('#locationId').empty()) {

						$.getJSON("/rms/rmReports/getLocation/" + oValue, {}, showLocation);
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

	function onRoleChange() {
		var roleId = $("#RoleId :selected").val();
		if (roleId == "Release_Date_IS_Not_NULL") {
			$('#ReportTypeId').val(-1)
			$("#ReportTypeId").attr("disabled", "disabled");
		} else {
			$("#ReportTypeId").removeAttr("disabled", "disabled");
		}
	}

	$(document).on('click','#submit',function() {

						var orgIdList = oValue;
						var locIdList = $('#locationId').val();
						var projIdList = $('#projectId').val();

						var roleId = $("#RoleId :selected").val();
						var reportId = $("#ReportTypeId :selected").val();
						//var records_table;

						var errmsg = "Please select:";
						var validationFlag = true;

						if (orgIdList.length == 0) {
							errmsg = errmsg + "<br> BG-BU ";
							validationFlag = false;
						}
						
						if (locIdList == null) {
							errmsg = errmsg + "<br> Location ";

							validationFlag = false;
						}
						if (projIdList == null) {
							errmsg = errmsg + "<br> Project ";

							validationFlag = false;
						}

						if (roleId == -1) {
							errmsg = errmsg + "<br> Role ";

							validationFlag = false;
						}

						if ($("#RoleId :selected").val() != "Release_Date_IS_Not_NULL") {
							if (reportId == -1) {
								errmsg = errmsg + "<br> Report ";

								validationFlag = false;
							}
						}

						if (!validationFlag) {

							stopProgress();
							if (errmsg.length > 0)

								showError(errmsg);

							return;
						} else {

							$("#exportToExcel").show();

							$.ajax({

										url : '/rms/rmReports/getRMReport',
										contentType : "text/html; charset=utf-8",
										async : false,
										data : "orgIdList=" + orgIdList
												+ "&locIdList=" + locIdList
												+ "&projIdList=" + projIdList
												+ "&roleId=" + roleId
												+ "&reportId=" + reportId,

										success : function(data) {

											if (data.length != 0) {
												$("#NoAllocMessage").hide();
												$('#records_tableId').show();
												$("#rmdatatable").show();
												$('#records_tableId').dataTable().fnClearTable();

												var i = 1;
												$.each(data,function(key,val) {

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

																	$('#records_tableId').show();
																	$('#records_tableId').dataTable().fnAddData(
																					[
																							val.SrNo,
																							val.yashEmpID,
																							val.employeeName,
																							val.emailId,
																							val.designation,
																							val.grade,
																							val.dateOfJoining,
																							releaseDate,
																							val.baseLocation,
																							val.currentLocation,
																							val.parentBu,
																							val.currentBu,
																							val.ownership,
																							val.currentRM1,
																							val.currentRM2,
																							val.primaryProject,
																							val.currentProjectIndicator,
																							val.customerName,
																							val.projectBu,
																							val.allocationStartDate,
																							val.allocationType,
																							val.resourceType,
																							transferDate,
																							val.visa,
																							val.competency,
																							val.primarySkill,
																							val.secondarySkill,
																							val.customerId,
																							val.lastUpdateBy,
																							val.lastupdateTimeStamp,
																							val.deliveryManager]);
																});

											}

										},
										error : function(errorResponse) {

											//showError("Records Not Found");
											$('#records_tableId').hide();
											var htmlVarObj = '<div id="NoAllocMessage" class="NoAllocMessage">No Record Exist for selected BG-BU </div>';

											$('#NoAllocMessage').empty().append(htmlVarObj);

											$("#NoAllocMessage").show();
											$("#rmdatatable").hide();

										}

									});

						}
					});

	function clearLocation() {

		$("#locationId").multiselect("uncheckAll");
		$('#locationId').empty();
		$('#locationId').multiselect('refresh').multiselect();
	}

	function clearProject() {

		$("#projectId").multiselect("uncheckAll");
		$('#projectId').empty()
		$('#projectId').multiselect('refresh').multiselect();
	}
</script>
<style>
</style>
<html>
<body>

	<div class="spinner-wrap">
		<span class="spinner"></span>
	</div>


	<div class="content-wrapper">
		<div class="botMargin">
			<h1>RM Detalis of Employee</h1>
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
								<td align="left">BG-BU : <span class="astric">*</span>
								</td>

								<td align="left">
								<%-- <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')"> --%>

										<%-- <form id="evalForm" method="post" class='form-inline'> --%>
										<div class="positionRel">
											<select name="orgHierarchy.id" id="orgHierarchy"
												multiple="multiple">
												<!-- <option value="-1"></option> -->
												<c:forEach items="${orgHierarchy}" var="orgHierarchy">
													<option value="${orgHierarchy.id}">${orgHierarchy.parentId.name}-${orgHierarchy.name}</option>
												</c:forEach>
											</select>
										</div>
										<%-- </form> --%>

									<%-- </sec:authorize> --%>
								</td>
									
								<td align="left">Project : <span class="astric">*</span></td>
								<td align="left">
									<div class="positionRel">
										<select name="project.id" id="projectId" multiple="multiple"
											onchange="onProjectChange()" style="height: 26px;">
										</select>
									</div>
								</td>
								
								<td align="left">Location : <span class="astric">*</span></td>
								<td align="left">
									<div class="positionRel">
										<select name="location.id" id="locationId" multiple="multiple"
											style="height: 26px;">

										</select>
									</div>
								</td>

							</tr>
							<tr>

								<td align="left">Role : <span class="astric">*</span></td>
								<td align="left"><select name="OnRole.id" id="RoleId"
									onchange="onRoleChange()">
										<option value=-1>Select Role</option>
										<option value="Release_Date_IS_NULL">Active</option>
										<option value="Release_Date_IS_Not_NULL">InActive</option>
										<!-- 	<option value="Both_onRole_offRole">Both</option> -->
								</select></td>

								<td align="left">ReportType : <span class="astric">*</span></td>
								<td align="left"><select name="ReportType.id"
									id="ReportTypeId">
										<option value=-1>Select ReportType
										<option value="Primary">Primary</option>
										<option value="Multiple">Multiple</option>
										<option value="Discrepancy">Discrepancy</option>
								</select></td>


								<td><a href="#" class="blue_link" id="submit"><span
										class="glyphicon glyphicon-list-alt"></span> View Report </a></td>

								<td><a href="exportToExcel" class="blue_link"
									id="exportToExcel"><i class="fa fa-file-excel-o"
										style="font-size: 15px"></i> Export To Excel </a></td>
							</tr>
							<tr>
								<td colspan="3">&nbsp;</td>
								<td colspan="3"><font color="#FF0000">** NOTE:
										Please click to "View Report" button first to download the
										Excel **</font></td>
							</tr>
						</table>
					</form>
				</div>
				<div class="tbl"></div>
				<div class="clear"></div>
			</div>



		</div>

		<div style="max-width: 1090px; overflow: auto;" id="rmdatatable">

			<table class="dataTbl display tablesorter dataTable dtable records-data-table" id="records_tableId">
				<thead>
					<tr>
						<th align="center" valign="middle">S.NO</th>
						<th align="center" valign="middle">Yash Emp ID</th>
						<th align="center" valign="middle">Emp Name</th>
						<th align="center" valign="middle">E-Mail</th>
						<th align="center" valign="middle">Designation</th>
						<th align="center" valign="middle">Grade</th>
						<th align="center" valign="middle">DOJ</th>
						<th align="center" valign="middle">Release Date</th>
						<th align="center" valign="middle">Base Location</th>
						<th align="center" valign="middle">Current Location</th>
						<th align="center" valign="middle">Parent BG-BU</th>
						<th align="center" valign="middle">Current BG-BU</th>
						<th align="center" valign="middle">Ownership</th>
						<th align="center" valign="middle">IRM</th>
						<th align="center" valign="middle">SRM</th>
						<th align="center" valign="middle">Primary_Project</th>
						<th align="center" valign="middle">Current Project Flag</th>
						<th align="center" valign="middle">Customer_Name</th>
						<th align="center" valign="middle">Project_Bu</th>
						<th align="center" valign="middle">Allocation Start Date</th>
						<th align="center" valign="middle">Allocation Type</th>
						<th align="center" valign="middle">Resource Type</th>
						<th align="center" valign="middle">Transfer Date</th>
						<th align="center" valign="middle">Visa</th>
						<th align="center" valign="middle">Competency</th>
						<th align="center" valign="middle">Primary Skill</th>
						<th align="center" valign="middle">Secondary Skill</th>
						<th align="center" valign="middle">CustomerId</th>
						<th align="center" valign="middle">Last UpdatedID</th>
						<th align="center" valign="middle">Last UpdatedTimeStamp</th>
						<th align="center" valign="middle">Delivery Manager</th>
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

