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

.datatable-scroll {
	max-width: 2000px;
	overflow-x: auto;
	position: relative;
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

		$('#orgHierarchy, #locationId, #projectId, #Ownership').multiselect({
			includeSelectAllOption : true
		}).multiselectfilter();

	});

	$(document).ready(function() {

						$('#exportToExcel').hide();

						$("#endDatepicker").datepicker({
							dateFormat : 'yy-mm-dd'
						});

						$('#records_tableId_1').hide();
						$('#records_tableId_2').hide();

						$('#AllocationType').multiselect();

						var serverCallRequired = false;
						$('#orgHierarchy').change(function() {

							serverCallRequired = true;

							clearLocation();
							clearProject();

						});

						$('#orgHierarchy').multiselect(
										{

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

													$.getJSON("/rms/pwrReports/getLocation/"
																	+ oValue,
															{}, showLocation);
												}

												serverCallRequired = false;
											}
										});

						// show location in dropdown
						function showLocation(data) {

							if (data.length != 0) {

								var htmlVar = '';

								$.each(data,function(key, val) {

													htmlVar += '<option value="'+data[key].id+'">'
															+ data[key].location
															+ '</option> ';

												});

								$('#locationId').append(htmlVar);

								$('#locationId').multiselect('refresh').multiselect();

							} else {

								showError("Location not found for this BG-BU");

							}
						}

						$('#orgHierarchy, #projectId ,#locationId, #AllocationType, #startDatepicker').on('change', function(e) {

									$("#exportToExcel").hide();

								});

						$('#locationId').change(function() {

							serverCallRequired = true;

							clearProject();

						});

						$('#locationId').multiselect(
										{

											checkAll : function() {

												serverCallRequired = true;
												clearProject();
											},
											uncheckAll : function() {

												clearProject();
											},
											close : function(event) {

												oValue = $('#orgHierarchy').val();
												lValue = $('#locationId').val();

												if (serverCallRequired && oValue && lValue) {

													$('#projectId').empty();
													$('#projectId').multiselect('refresh').multiselect();

													var aUrl = "/rms/pwrReports/getProject/"
															+ oValue
															+ "/"
															+ lValue;

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

								$.each(data,function(key, val) {
									
									htmlVar += '<option value="'+data[key].id+'">'
															+ data[key].projectName
															+ '</option> ';
												});

								$('#projectId').append(htmlVar);

								$('#projectId').multiselect('refresh').multiselect();

							} else {

								showError("Projects not found for this BG-BU And Location");
							}
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
				$("#projectId").multiselect("widget").find(
						":checkbox[value='" + valArr[i] + "']").attr("checked",
						"checked");
				$("#projectId option[value='" + valArr[i] + "']").attr(
						"selected", true);
				$("#projectId").multiselect("refresh");
			}

		}

		$('#projectId').multiselect({
			close : function(event) {
				pValue = $('#projectId').val();

			}

		});

		$('#projectId > option:selected').each(function(i) {
			lValue[i] = $(this).val();
		});
	}

	$('#Ownership').multiselect({
		close : function(event) {
			pValue = $('#Ownership').val();

		}

	});

	function submitDetail() {

		var orgIdList = $('#orgHierarchy').val();
		var locIdList = $('#locationId').val();
		var projIdList = $('#projectId').val();

		var ownership = $("#Ownership").val();

		var date = $('#endDatepicker').val();
		var successMsg = "successfully";

		var view = $("#view :selected").val();

		if (view == 1) {
			$('#records_tableId_1').show();
			$('#records_tableId_2').hide();
		}
		if (view == 2) {
			$('#records_tableId_1').hide();
			$('#records_tableId_2').show();
		}

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

		if (document.getElementById("endDatepicker").value == "") {
			errmsg = errmsg + "<br> End Date ";
			validationFlag = false;
		}

		if (document.getElementById("Ownership").value == "") {
			errmsg = errmsg + "<br> Ownership ";
			validationFlag = false;
		}

		if (document.getElementById("view").value == "-1") {
			errmsg = errmsg + "<br> View ";
			validationFlag = false;
		}

		if (!validationFlag) {

			stopProgress();
			if (errmsg.length > 0)

				showError(errmsg);

			return;
		} else {

			$("#exportToExcel").show();

			$
					.ajax({

						url : '/rms/pwrReports/getPWRReport',
						contentType : "application/json; charset=utf-8",
						async : false,
						data : "orgIdList=" + orgIdList + "&locIdList="
								+ locIdList + "&projIdList=" + projIdList
								+ "&ownership=" + ownership + "&date=" + date
								+ "&view=" + view,

						dataType : 'json',

						success : function(data) {

							if (data.length != 0) {

								if (view == 1) {

									$('#records_tableId_1 tbody').html('');

									var row = '';
									var i = 1;

									$.each(data,function(key, val) {

														row += '<tr><td>'
																+ i++
																+ '</td>'
																+ '<td>'
																+ val.projectName
																+ '</td>'
																+ '<td>'
																+ val.total
																+ '</td>'
																+ '<td>'
																+ val.billableFullTimeCount
																+ '</td>'
																+ '<td>'
																+ val.billablePartiaSharedpoolFixbidprojectsCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillableAbscondingCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillableAccountManagementCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillableBlockedCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillableContingentCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillableDeliveryManagementCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillableInsidesaleCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillableInvestmentCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillableLongleaveCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillableManagementCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillableOperationsCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillablePmoCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillablePresaleCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillableSalesCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillableShadowCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillableTraineeCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillableTransitionCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillableUnallocatedCount
																+ '</td>'
																+ '<td>'
																+ val.nonBillableExitreleaseCount
																+ '</td>'
																+ '<td>'
																+ val.pipCount
																+ '</td>' +

																'</td></tr>';

													});
									$('#records_tableId_1 tbody').append(row);

								} else {

									$('#records_tableId_2 tbody').html('');

									var row = '';
									var j = 1;

									$.each(data,function(key, val) {

														var project = key;
														
														var total = val.length;
													
														row += '<tr><td>' + j++
																+ '</td>'
																+ '<td>'
																+ project
																+ '</td>';

														row += '<td>' + total
																+ '</td>';

														for (i = 0; i < val.length; i++) {

															var objectData = val[i];

															if (i > 0) {
																row += '<td></td><td></td><td></td>';
															}

															row += '<td>'
																	+ objectData.employeeName
																	+ '</td>'
																	+ '<td>'
																	+ objectData.allocationType
																	+ '</td>';

															row += '</tr><tr>';
														}

														row += '</tr>';

													});

									$('#records_tableId_2 tbody').append(row);

								}
							}
						},
						error : function(errorResponse) {
							var row = '                                                                 No Data Available           ';
							
							$('#records_tableId_2 tbody').append(row);
							$('#records_tableId_2 tbody').append(row);
						}

					});
		}
	}

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
div.dataTables_wrapper {
	width: 800px;
	margin: 0 auto;
}
</style>
<html>
<body>
	<div class="spinner-wrap">
		<span class="spinner"></span>
	</div>

	<div class="content-wrapper">
		<div class="botMargin">
			<h1>Project Wise Resource Reports</h1>
		</div>
		<div class="tab_seaction">
			<ul class='tabs'>
				<li><a href='#tab1' id="tab1id">List</a></li>

			</ul>
			<div id='tab1' class="tab_div">


				<div class="form">


					<table id="RMReportTable" cellspacing="10" cellpadding="10">

						<tr>
							<td align="left">Project BG-BU :</td>
							<td align="left"><div class="positionRel floatR">
									<select name="orgHierarchy.id" id="orgHierarchy"
										multiple="multiple">
										<c:forEach items="${orgHierarchy}" var="orgHierarchy">
											<option value="${orgHierarchy.id}">${orgHierarchy.parentId.name}-${orgHierarchy.name}</option>
										</c:forEach>

									</select>
								</div></td>

							<td align="left">Location :</td>

							<td align="left"><div class="positionRel floatR">
									<select name="location.id" id="locationId" multiple="multiple"
										onchange="onLocationChange()" style="height: 26px;">

									</select>
								</div></td>

						</tr>
						<tr>

							<td align="left">Project :</td>
							<td align="left"><div class="positionRel floatR">
									<select name="project.id" id="projectId" multiple="multiple"
										onchange="onProjectChange()" style="height: 26px;">
									</select>
								</div></td>



							<td align="left">Ownership :</td>
							<td align="left"><div class="positionRel floatR">
									<select name="Ownership.id" id="Ownership" multiple="multiple">
										<c:forEach items="${Ownership}" var="Ownership">
											<option value="${Ownership.id}">${Ownership.ownershipName}</option>
										</c:forEach>

									</select>
								</div></td>


						</tr>

						<tr>
							<td align="left">Week End Date :</td>

							<td align="left"><input type="text" id="endDatepicker"></td>

							<td align="left">Show View :</td>
							<td align="left"><select id='view'>
									<option value="-1">View</option>
									<option value="1">View 1</option>
									<option value="2">View 2</option>
							</select></td>


							<td><a href="#" class="blue_link" id="submit"
								onclick="submitDetail()"> <span
									class="glyphicon glyphicon-list-alt"></span> View Report
							</a></td>

							<td><a href="exportToExcel" class="blue_link"
								id="exportToExcel"><i class="fa fa-file-excel-o"
									style="font-size: 15px"></i> Export To Excel </a></td>

						</tr>

						<tr>
							<td colspan="4">&nbsp;</td>
							<td colspan="3"><font color="#FF0000">** NOTE: Please
									click to "View Report" button first to download the Excel, </font></td>
						</tr>
						<tr>
							<td colspan="5">&nbsp;</td>
							<td colspan="1"><font color="#FF0000">Excel will be
									Downloaded for both the Views **</font></td>

						</tr>
					</table>

				</div>

				<div class="tbl"></div>
				<div class="clear"></div>

			</div>
		</div>


		<div class="datatable-scroll">
			<!-- for view 1 -->

			<table id="records_tableId_1" class="dataTable dtable">
				<thead>
					<tr>
						<th align="center" valign="middle">S.NO</th>
						<th align="center" valign="middle">Project</th>
						<th align="center" valign="middle">Total</th>
						<th align="center" valign="middle">Billable (Full Time (FTE))</th>
						<th align="center" valign="middle">Billable (Partial / Shared
							Pool / Fix Bid Projects)</th>

						<th align="center" valign="middle">Non-Billable (Absconding)</th>
						<th align="center" valign="middle">Non-Billable (Account
							Management)</th>
						<th align="center" valign="middle">Non-Billable (Blocked)</th>
						<th align="center" valign="middle">Non-Billable (Contingent)</th>
						<th align="center" valign="middle">Non-Billable (Delivery
							Management)</th>
						<th align="center" valign="middle">Non-Billable (InsideSale)</th>
						<th align="center" valign="middle">Non-Billable (Investment)</th>
						<th align="center" valign="middle">Non-Billable (Long leave)</th>

						<th align="center" valign="middle">Non-Billable (Management)</th>
						<th align="center" valign="middle">Non-Billable (Operations)</th>
						<th align="center" valign="middle">Non-Billable (PMO)</th>
						<th align="center" valign="middle">Non-Billable (PreSale)</th>
						<th align="center" valign="middle">Non-Billable (Sales)</th>
						<th align="center" valign="middle">Non-Billable (Shadow)</th>
						<th align="center" valign="middle">Non-Billable (Trainee)</th>
						<th align="center" valign="middle">Non-Billable (Transition)</th>
						<th align="center" valign="middle">Non-Billable (Unallocated)</th>
						<th align="center" valign="middle">OUTBOUND (Exit/Release)</th>
						<th align="center" valign="middle">PIP</th>

					</tr>

				</thead>
				<tbody>
				</tbody>
			</table>
		</div>


		<div class="datatable-scroll">
			<!-- for view 2 -->
			<table id="records_tableId_2" class="dataTable dtable">
				<thead>
					<tr>
						<th align="center" valign="middle">S.NO</th>
						<th align="center" valign="middle">PROJECT</th>
						<th align="center" valign="middle">TOTAL</th>
						<th align="center" valign="middle">EMPLOYEE</th>
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

