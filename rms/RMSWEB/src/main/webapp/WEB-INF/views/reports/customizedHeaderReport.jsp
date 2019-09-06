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


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>

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

#CHReportFormTable td {
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

#sortable1, #sortable2 {
	list-style-type: none;
	margin: 0;
	padding: 0 0 2.5em;
	float: left;
	margin-right: 10px;
}

#sortable1 li, #sortable2 li {
	margin: 0 5px 5px 5px;
	padding: 5px;
	font-size: 1.2em;
}

#sortable2 {
	height: 406px;
}

#sortable2 {
	overflow: hidden;
	overflow-y: scroll;
}

#sortable1 {
	height: 406px;
}

#sortable1 {
	overflow: hidden;
	overflow-y: scroll;
}

div>table {
	float: left
	width: 50%;
}
#container2 {
    float: left;
    width: 50%;
    }
    
    #container1 {
    float: left;
      width: 50%;
    border: solid;
    height: 416px;
    }
    #dragDropColumn{
        border-right: 2px dotted;
    }
    
</style>

<script>
	var recordTable;
	var oValue = new Array();
	var lValue = new Array();
	var pValue = new Array();
	var exportToExcelServerCallRequired = false;

	$(function() {
		$(
				'#orgHierarchy, #locationId, #projectId,  #resource, #resourceOrgHierarchy, #Ownership')
				.multiselect({
					includeSelectAllOption : true,
				}).multiselectfilter();

	});

	$(document)
			.ready(
					function() {

						var order1;
						var order2;
						$(function() {
							$("#sortable1, #sortable2").sortable(
									{
										connectWith : ".connectedSortable",
										update : function() {
											order1 = $('#sortable1').sortable(
													'toArray').toString();
											order2 = $('#sortable2').sortable(
													'toArray').toString();
											//alert("Order 1:" + order1 + "\n Order 2:" + order2);
										}
									}).disableSelection();
						});

						//alert("Order 1:" + order1 + "\n Order 2:" + order2);

						$('.ui-multiselect-all').trigger('click');

						$('#exportToExcel').show();
						$("#endDatepicker").datepicker({
							dateFormat : 'yy-mm-dd'
						});

						$('#records_tableId').hide();

						var serverCallRequired = false;

						$('#orgHierarchy').multiselect({
							checkAll : function() {
								/* serverCallRequired = true;
								clearLocation();
								clearProject();
								clearResource() */;
							},
							uncheckAll : function() {
								/* clearLocation();
								clearProject();
								clearResource(); */
							},
							close : function(event) {
								/* var oValue = $('#orgHierarchy').val();
								if (serverCallRequired && (oValue != null || oValue != -1)) {
									$('#locationId').empty();
									$('#locationId').multiselect('refresh').multiselect();
									$('#projectId').empty();
									$('#projectId').multiselect('refresh').multiselect();
									$('#resource').empty();
									$('#resource').multiselect('refresh').multiselect();
									var aUrl = "/rms/UtilizationReport/getProject/" + oValue;
									$.getJSON("/rms/UtilizationReport/getLocation/" + oValue, {}, showLocation);
									$.ajax({
										url : aUrl,
										method : 'GET',
										success : function(data) {
											showProject(data);
										}
									});
								}
								serverCallRequired = false; */
							}
						});

						$('#resourceOrgHierarchy').multiselect({
							checkAll : function() {
								/* serverCallRequired = true;
								clearLocation();
								clearProject();
								clearResource(); */
							},
							uncheckAll : function() {
								/* clearLocation();
								clearProject();
								clearResource(); */
							},
							close : function(event) {
							}
						});

						$('#locationId').multiselect({
							checkAll : function() {
							},
							uncheckAll : function() {
							},
							close : function(event) {

							}
						});

						function showProject(data) {
							if (data.length != 0) {
								var htmlVar = '';
								$
										.each(
												data,
												function(key, val) {
													htmlVar += '<option value="'+data[key].id+'">'
															+ data[key].projectName
															+ '</option> ';
												});
								 
								$('#projectId').append(htmlVar);
								$('#projectId').multiselect('refresh')
										.multiselect();
							} else {
								showError("Projects not found for this BG-BU And Location");
							}

							$('#projectId').multiselect({
								checkAll : function() {
								},
								uncheckAll : function() {
								},
								close : function(event) {
								}
							});
						}

						$('#resource').multiselect({
							checkAll : function() {
							},
							uncheckAll : function() {
							},
						});

						$(
								'#orgHierarchy, #projectId ,#locationId, #resource, #startDatepicker, #endDatepicker')
								.on('change', function(e) {
									//$("#exportToExcel").hide();
								});

						/* clearLocation();
						clearProject();
						clearResource(); */
						$("#locationId").multiselect("uncheckAll");
						$("#projectId").multiselect("uncheckAll");
						$("#orgHierarchy").multiselect("uncheckAll");
					});

	function exportDataToExcel() {

		var requestParam = "?";
		var orgIdList = $('#orgHierarchy').val();
		var locIdList = $('#locationId').val();
		var projIdList = $('#projectId').val();
		var endDatepicker = $('#endDatepicker').val();
		var ReportTypeId = $('#ReportTypeId').val();
		var RoleId = $('#RoleId').val();
		var sortableList = $('#sortable2').sortable('toArray').toString();

		var errmsg = "Please select:";
		var validationFlag = true;
		if (document.getElementById("endDatepicker").value == "") {
			errmsg = "<br> End Date cannot be empty";
			validationFlag = false;
		}
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
		if (document.getElementById("ReportTypeId").value == "") {
			errmsg = errmsg + "<br> ReportTypeId ";
			validationFlag = false;
		}
		if (document.getElementById("RoleId").value == "") {
			errmsg = errmsg + "<br> RoleId ";
			validationFlag = false;
		}
		if (!validationFlag) {
			stopProgress();
			if (errmsg.length > 0)
				showError(errmsg);
			return false;
		} else {
			var orgIdList = $('#orgHierarchy').val();
			var locIdList = $('#locationId').val();
			var projIdList = $('#projectId').val();
			var endDatepicker = $('#endDatepicker').val();
			var ReportTypeId = $('#ReportTypeId').val();
			var RoleId = $('#RoleId').val();
			var sortableList = $('#sortable2').sortable('toArray').toString();

			requestParam = requestParam + "orgIdList=" + orgIdList
					+ "&locIdList=" + locIdList + "&projIdList=" + projIdList
					+ "&reportTypeId=" + ReportTypeId + "&roleId=" + RoleId
					+ "&endDate=" + endDatepicker + "&sortableList="
					+ sortableList;
			//requestParam = requestParam + "&endDate="+ endDatepicker + "&sortableList="	+ sortableList;  
			 
			var res = "report";
			$('#exportToExcel').attr("href", res + requestParam)
			$("#CHReportForm").submit();
			exportToExcelServerCallRequired = false;
			stopProgress();

		}
	}
</script>



<html>
<body>
	<div class="content-wrapper">
		<div class="botMargin">
			<h1>Current Week Allocation Report</h1>
		</div>
		<div class="tab_seaction">
			<ul class='tabs'>
				<li><a href='#tab1' id="tab1id">List</a></li>
			</ul>
			<div id='tab1' class="tab_div">
				<div class="form">
					<form id="CHReportForm" name="CHReportForm"
						action="CustomizeHeaderReportController">
						<div id="container1">
							<table id="sortableTable" cellspacing="10" cellpadding="10">
								<tr>
									<td>Input Report Header :</td>
									<td id="dragDropColumn">
										<div class="positionRel">
											<ul id="sortable1" class="connectedSortable ui-sortable">
												<li class="ui-state-highlight" id="Employee_Name">Employee
													Name</li>
												<li class="ui-state-highlight" id="Designation">Designation</li>
												<li class="ui-state-highlight" id="Currnet_Bu">Current
													BG - BU</li>
												<li class="ui-state-highlight" id="Primary_Project">Project</li>
												<li class="ui-state-highlight" id="Grade">Grade</li>
												<li class="ui-state-highlight" id="Allocation_Start_Date">Allocation
													Start Date</li>
												<li class="ui-state-highlight"
													id="Resource_Allocation_End_Date">Allocation End Date</li>
												<li class="ui-state-highlight" id="Release_Date">Release
													Date</li>
												<li class="ui-state-highlight" id="Primary_skill">Skills</li>
												<li class="ui-state-highlight" id="Base_Location">Base
													Location</li>
												<li class="ui-state-highlight" id="Date_Of_Joining">Date
													Of Joining (DOJ)</li>
												<li class="ui-state-highlight" id="Allocation_Type">Allocation
													Type</li>
												<li class="ui-state-highlight" id="Current_Location">Current
													Location</li>
											</ul>
										</div>
									</td>

									<td>Selected Report Header :</td>
									<td>
										<div class="positionRel">
											<ul id="sortable2" class="connectedSortable ui-sortable">
												<li class="ui-state-highlight" id="Yash_Emp_Id">Yash
													Employee ID</li>
											</ul>
										</div>
									</td>
								</tr>
							</table>
						</div>
						<div id="container2">
							<table id="CHReportFormTable" cellspacing="10" cellpadding="10">
								<tr>

									<td>Project BG-BU :</td>
									<td><div class="positionRel">
											<select name="orgHierarchy.id" id="orgHierarchy"
												multiple="multiple" class="dropdown-search">
												<c:forEach items="${orgHierarchy}" var="orgHierarchy">
													<option value="${orgHierarchy.id}">${orgHierarchy.parentId.name}-${orgHierarchy.name}</option>
												</c:forEach>
											</select>
										</div></td>

									<td>Location :</td>
									<td>
										<div class="positionRel">
											<select name="locations.id" id="locationId"
												multiple="multiple" class="dropdown-search">
												<c:forEach items="${locations}" var="locations">
													<option value="${locations.id}">${locations.location}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								<tr>
									<td>Project :</td>
									<td>
										<div class="positionRel">
											<select name="projects.id" id="projectId" multiple="multiple"
												class="dropdown-search">
												<c:forEach items="${projects}" var="projects">
													<option value="${projects.id}">${projects.projectName}</option>
												</c:forEach>
											</select>
										</div>
									</td>

									<td align="left">End Date :</td>
									<td align="left"><input type="text" id="endDatepicker"
										path="endDate" name="endDate"></td>
								</tr>

								<tr>
									<td>ReportType :</td>
									<td><div class="positionRel">
											<select name="ReportType.id" id="ReportTypeId"
												class="orderselect">
												<option value="-1">Select ReportType</option>
												<option value="1">Primary</option>
												<option value="2">Discrepancy</option>
											</select>
										</div></td>

									<td>Role :</td>
									<td><div class="positionRel">
											<select name="OnRole.id" id="RoleId"
												onchange="onRoleChange()" class="orderselect">
												<option value="-1">Select Role</option>
												<option value="Release_Date_IS_NULL">Active</option>
												<option value="Release_Date_IS_Not_NULL">InActive</option>
											</select>
										</div></td>
								</tr>

								<tr>
								<td></td><td></td><td></td>
									<td ><a href="#" class="blue_link" id="exportToExcel"
										onclick="exportDataToExcel()" align="right"> <i
											class="fa fa-file-excel-o" style="font-size: 15px"></i>
											Export To Excel
									</a></td>
								</tr>
								<!-- <tr>
									<td colspan="3">&nbsp;</td>
									<td ><font color="#FF0000">** NOTE:
											Please click to "View Reports" button first to download the
											Excel **</font></td>
								</tr> -->
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