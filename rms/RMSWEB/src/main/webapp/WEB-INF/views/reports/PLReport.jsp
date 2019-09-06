<%@page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<spring:url
	value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url
	value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<style>
table#dtbl thead tr:first-child th:nth-child(even) {
	background-color: #3c8dbc;
}

table#dtbl thead tr:first-child th:nth-child(odd) {
	background-color: #01498B;
}
</style>
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<script src="${multiselect_filter_js}" type="text/javascript"></script>
<script src="resources/js-user/validations.js"></script>
<!-- /RMSWEB/src/main/webapp/resources/js-user/validations.js -->
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
#plr { padding-bottom:10px;}

#defaultTableId thead th {
	width: 120px;
}

#defaultTableId td {
	word-wrap: break-word;
}

.dtable td {
	background: #ebebeb;
}

.plReportFilter td {
	padding: 10px 0px; 
}
.plReportFilter .navbar-form {
    padding: 0px!important;
    margin: 0px!important;
}
.plReportFilter .form select{width:auto!important;}
.activeReportTab {
	background: #84DFC1;
}

.datatable-scroll {
	max-width: 2000px;
	overflow-x: auto;
	position:relative;
	
}

/* .records-data-table {
	padding-top: 41px;
} */
/* div.dataTables_wrapper {
	/* width: 100%; */
	/* margin: 0 auto; */
	/* position: relative;
	display: inline-block;
	bDestroy : true;
	margin: 0 auto; */
	/* z-index: 2; */
	/* padding : 0px; */
    /* margin-left: 0px;
    display: inline; */
   /*  border: 0px; */
/*} */
</style>

<body>
	<div class="content-wrapper">
		<div class="botMargin">
			<h1>PL Reports</h1>
		</div>
		<div class="tab_seaction">
			<ul class='tabs'>
				<li><a href='#tab1' id="tab1id">List</a></li>

			</ul>
			<div id='tab1' class="tab_div">


				<div class="form">

					<form name="defaultFormName" method="post" id="defaultForm">
						<table id="defaultTable" class="plReportFilter">
							<tr>
								<input type="hidden" id="formHiddenId" name="id"></input>
							</tr>
							<tr>
								<td align="right">BG-BU :</td>
								<td align="right">
										<div class='navbar-form navbar-left'>
										<%-- <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')"> --%>
											<form id="evalForm" method="post" class='form-inline'>

												<select name="buId" id="orgHierarchy" multiple="multiple" class="dropdown-search">
													<c:forEach items="${orgHierarchy}" var="orgHierarchy">
														<option value="${orgHierarchy.id}">${orgHierarchy.parentId.name}-${orgHierarchy.name}</option>
													</c:forEach>
												</select>
											</form>
										<%-- 	</sec:authorize> --%>
										</div>
										</td>									
									
								<td align="left">
									<select name="projectId" id="projectId"
									style="display: none;">

									</select>
								</td>


								<td align="center">Start Date :<span class="astric">*</span></td>
								<td align="center"><input type="text" id="startdate" name="startDate" readonly="readonly" /></td>



								<td align="left">End Date :<span class="astric">*</span></td>
								<td align="left"><input type="text" id="enddate"
									name="endDate" readonly="readonly" /></td>
									
									
								<td>Project Type :</td>
								<td align="left"><select name="ProjectType.id"
									id="ProjectTypeId" class="orderselect">
										<option value="-1">All
										<option value="1">Primary</option>
										<option value="0">Non Primary</option>										
								</select></td>


								<td><a href="#" class="blue_link viewreportbtn" id="submit"><span
										class="glyphicon glyphicon-list-alt"></span> View Report </a></td>




								<td><a href="plReportExcel" class="blue_link"
									id="exportToExcel"><i class="fa fa-file-excel-o"
										style="font-size: 15px"></i> Export To Excel </a></td>



							</tr>
							<tr>
								<td colspan="6">&nbsp;</td>
								<td><INPUT TYPE="checkbox" name="includeResource" id="includeResource" style="width: 20px;">Include Resources of selected BG-BU</input></td>
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
		<div id="plr">

			<a href="#" id="showContractID" class="btn"><font color="#01498B">Contract
					Resource PLReport</font></a> <a href="#" id="showBUresourceID" class="btn"><font
				color="#01498B">BU Resource PLReport</font></a> <a href="#"
				id="showresignedID" class="btn"><font color="#01498B">Resigned
					Resource PLReport</font></a> <a href="#" id="showBorrowedresourceID"
				class="btn"><font color="#01498B">Borrowed Resource
					PLReport </font></a>

		</div>


		<div class="datatable-scroll">


			<table id="buResourcePLReportID" class="dataTable dtable"></table>

		</div>

		<div class="datatable-scroll">

			<table id="resignedResourcePLReportID" class="dataTable dtable"></table>

		</div>

		<div class="datatable-scroll">

			<table id="contractResourcePLReportID" class="dataTable dtable"></table>

		</div>

		<div class="datatable-scroll">

			<table id="borrowedResourcePLReportID" class="dataTable dtable"></table>

		</div>

	</div>

	<script>
	
		$(function() {
	
			$('#orgHierarchy').multiselect({
				includeSelectAllOption : true
			}).multiselectfilter();
	
		});
		$(function() {

			$('#showBUresourceID').click(function() {
				$('#contractResourcePLReportID').hide();
				$('#contractResourcePLReportID').closest( ".datatable-scroll" ).hide();
				$('#resignedResourcePLReportID').hide();
				$('#resignedResourcePLReportID').closest( ".datatable-scroll" ).hide();
				$('#buResourcePLReportID').show();
				$('#buResourcePLReportID').closest( ".datatable-scroll" ).show();
				$('#borrowedResourcePLReportID').hide();
				$('#borrowedResourcePLReportID').closest( ".datatable-scroll" ).hide();
				$('#buResourcePLReportID').DataTable({

					"sPaginationType" : "full_numbers",
					"retrieve" : true,
					"bDestroy" : true

				});

				$('#buResourcePLReportID_info').show();
				$('#resignedResourcePLReportID_info').hide();
				$('#borrowedResourcePLReportID_info').hide();
				$('#contractResourcePLReportID_info').hide();

				$('#buResourcePLReportID_paginate').show();
				$('#resignedResourcePLReportID_paginate').hide();
				$('#borrowedResourcePLReportID_paginate').hide();
				$('#contractResourcePLReportID_paginate').hide();

				$('table td:contains("null")').html('');
				return false;
			});

			$('#showresignedID').click(function() {
				$('#contractResourcePLReportID').hide();
				$('#contractResourcePLReportID').closest( ".datatable-scroll" ).hide();
				$('#resignedResourcePLReportID').show();
				$('#resignedResourcePLReportID').closest( ".datatable-scroll" ).show();
				$('#buResourcePLReportID').hide();
				$('#buResourcePLReportID').closest( ".datatable-scroll" ).hide();
				$('#borrowedResourcePLReportID').hide();
				$('#borrowedResourcePLReportID').closest( ".datatable-scroll" ).hide();
				$('#resignedResourcePLReportID').DataTable({

					"sPaginationType" : "full_numbers",
					"retrieve" : true,
					"bDestroy" : true

				});

				$('#buResourcePLReportID_info').hide();
				$('#resignedResourcePLReportID_info').show();
				$('#borrowedResourcePLReportID_info').hide();
				$('#contractResourcePLReportID_info').hide();

				$('#buResourcePLReportID_paginate').hide();
				$('#resignedResourcePLReportID_paginate').show();
				$('#borrowedResourcePLReportID_paginate').hide();
				$('#contractResourcePLReportID_paginate').hide();

				$('table td:contains("null")').html('');
				return false;
			});

			$('#showContractID').click(function() {

				$('#resignedResourcePLReportID').hide();
				$('#resignedResourcePLReportID').closest( ".datatable-scroll" ).hide();
				$('#contractResourcePLReportID').show();
				$('#contractResourcePLReportID').closest( ".datatable-scroll" ).show();
				$('#buResourcePLReportID').hide();
				$('#buResourcePLReportID').closest( ".datatable-scroll" ).hide();
				$('#borrowedResourcePLReportID').hide();
				$('#borrowedResourcePLReportID').closest( ".datatable-scroll" ).hide();
				$('#contractResourcePLReportID').DataTable({
					"sPaginationType" : "full_numbers",
					"retrieve" : true,
					"bDestroy" : true

				});

				$('#buResourcePLReportID_info').hide();
				$('#resignedResourcePLReportID_info').hide();
				$('#borrowedResourcePLReportID_info').hide();
				$('#contractResourcePLReportID_info').show();

				$('#buResourcePLReportID_paginate').hide();
				$('#resignedResourcePLReportID_paginate').hide();
				$('#borrowedResourcePLReportID_paginate').hide();
				$('#contractResourcePLReportID_paginate').show();
				$('table td:contains("null")').html('');
				return false;
			});

			$('#showBorrowedresourceID').click(function() {
				$('#resignedResourcePLReportID').hide();
				$('#resignedResourcePLReportID').closest( ".datatable-scroll" ).hide();
				$('#contractResourcePLReportID').hide();
				$('#contractResourcePLReportID').closest( ".datatable-scroll" ).hide();
				$('#buResourcePLReportID').hide();
				$('#buResourcePLReportID').closest( ".datatable-scroll" ).hide();
				$('#borrowedResourcePLReportID').show();
				$('#borrowedResourcePLReportID').closest( ".datatable-scroll" ).show();
				$('#borrowedResourcePLReportID').DataTable({

					"sPaginationType" : "full_numbers",
					"retrieve" : true,
					"bDestroy" : true

				});

				$('#buResourcePLReportID_info').hide();
				$('#resignedResourcePLReportID_info').hide();
				$('#borrowedResourcePLReportID_info').show();
				$('#contractResourcePLReportID_info').hide();

				$('#buResourcePLReportID_paginate').hide();
				$('#resignedResourcePLReportID_paginate').hide();
				$('#borrowedResourcePLReportID_paginate').show();
				$('#contractResourcePLReportID_paginate').hide();
				$('table td:contains("null")').html('');
				return false;
			});

			$('#plr > .btn').click(function() {
				$('.btn').each(function() {
					$(this).removeClass('activeReportTab');
				});

				$(this).addClass('activeReportTab');
			});

		});

		$(function() {
			$("#enddate").datepicker({
				//minDate : -210,
				maxDate : 0,
			});
			$("#startdate").datepicker({
				//minDate : -210,
				maxDate : 0,
			});
		});

		$(document).ready(
				function() {
					$('#plr').hide();
					$('#exportToExcel').hide();
					$('#orgHierarchy').multiselect({
											
						close : function(event) {
							var oValue = $('#orgHierarchy').val();

							if ((oValue != null || oValue != -1)) {

								var aUrl = "/rms/ResourceMovementReports/getProject/" + oValue;
								//$.getJSON("/rms/ResourceMovementReports/getLocation/" + oValue, {}, showLocation);
								$.ajax({
									url : aUrl,
									method : 'GET',
									success : function(data) {
										showProject(data);
									}
								});
							}

							//serverCallRequired = false;
						
						}
					});
					/* $('#orgHierarchy').on(
							'change',
							function() {
								//alert();
								var count = 0;
								if (count > 0) {
									$('#projectId').val(-1);

								}
								var value;
								value = $(this).val();
								if (value != -1) {
									$.getJSON(
											"/rms/projects/getAllProjectByBUid/"
													+ value, {}, showProject);
								}
								count++;
							}); */

					// show project in dropdown
					function showProject(data) {

						var htmlVar = '<option value="-1">All</option>';
						var select = $('#projectId');
						select.empty().append(htmlVar);

						if (data.length != 0) {

							$.each(data, function(key, val) {

								htmlVar += '<option value="'+data[key].id+'">'
										+ data[key].projectName + '</option> ';
							});

							select.empty().append(htmlVar);
						} else {
							showError("Projects not found for this BG-BU");
						}
					}

					$('#orgHierarchy, #startdate ,#enddate, #projectTypeId','#includeResource').on('change',
							function(e) {
								$("#exportToExcel").hide();
							});
					$('#includeResource').change(function() {
						$("#exportToExcel").hide();
					});
					$('#orgHierarchy').multiselect({
						checkAll : function() {
							$("#exportToExcel").hide();				
						},
						uncheckAll : function() {
							$("#exportToExcel").hide();
						}
					});					
				});

		$('#submit')
				.on('click', function() {

							var startDate = document
									.getElementById("startdate").value;
							var endDate = document.getElementById("enddate").value;

							var errmsg = "Please select:";
							var validationFlag = true;

							if (document.getElementById("orgHierarchy").value == "") {
								errmsg = errmsg + " <br>  BG-BU ";
								validationFlag = false;
							}
							if (document.getElementById("startdate").value == "") {
								errmsg = errmsg + "<br> Start date ";
								validationFlag = false;
							}
							if (document.getElementById("enddate").value == "") {
								errmsg = errmsg + "<br> End date ";

								validationFlag = false;
							}

							if ((Date.parse(startDate) > Date.parse(endDate))) {

								document.getElementById("enddate").value = "";

								errmsg = errmsg
										+ " Correct End Date"
										+ "<br>End Date should be greater that Start Date ";
								validationFlag = false;
							}

							if (!validationFlag) {

								stopProgress();
								if (errmsg.length > 0)

									showError(errmsg);

								return;
							} else {

								$("#exportToExcel").show();
								$('#plr').show();

								var buid = $('#orgHierarchy').val();
								var sdate = $('#startdate').val();

								var edate = $('#enddate').val();

								var projId = $('#projectId').val();
								
								var projectTypeId = $("#ProjectTypeId :selected").val();
								
								var includeResource;
								if($('#includeResource').prop("checked"))
									includeResource = true
								else
									includeResource = false
											
								
								var requestParam = "buid=" + buid + "&projectId=" + projId + "&startDate=" + sdate + "&endDate=" + edate + "&projectTypeId=" + projectTypeId +"&includeResource=" + includeResource;

								$.ajax({
											type : 'GET',
											url : '/rms/plReports/getPLReport',
											contentType : "application/json; charset=utf-8",
											async : false,
											data : requestParam,
											dataType : 'json',
											async : true,
											success : function(data) {
 
												if (data.length != 0) {

													$
															.each(
																	data,
																	function(
																			key,
																			val) {

																		if (key == "bUResourcesPLReportList") {

																			var buResourcePLReportTable = $('#buResourcePLReportID');
																			var htmlVar = "<thead><tr><th colspan='2'>From Payroll Records (E No, Name)</th><th colspan='3'>Division By Business Groups</th><th colspan='1'>Division By Skills</th><th colspan='2'>Division By Location</th><th colspan='3'>Division By Allocation/Billability</th><th colspan='2'>Worked during the Month</th><th colspan='1'>For your Reference</th><th colspan='2'>For your Reference - Project Allocation</th><th colspan='2' rowspan='1'>Resource Utilization</th></tr><tr><th>Employee ID</th><th>Employee Name</th><th>Designation</th><th>BG-BU</th><th>Project</th><th>Skills</th><th>Base Location</th><th>Current Location</th><th>Customer</th><th>Date Of Joining (YY-MM-DD)</th><th>Grade</th><th>Allocation Start Date (YY-MM-DD)</th><th>Allocation End Date (YY-MM-DD)</th><th>Allocation type</th><th>Start Date</th><th>End Date</th><th>Actual Res Utilization</th></tr></thead>";

																			for (i = 0; i < val.length; i++) {
																				htmlVar += "<tr>";
																				var objectData = val[i];
																				var employeeid = objectData[0];
																				htmlVar += "<td>"
																						+ employeeid
																						+ "</td>";
																				var Name = objectData[1];
																				htmlVar += "<td>"
																						+ Name
																						+ "</td>";
																				var Designation = objectData[2];
																				htmlVar += "<td>"
																						+ Designation
																						+ "</td>";
																				var bgbu = objectData[3];
																				htmlVar += "<td>"
																						+ bgbu
																						+ "</td>";
																				var Project = objectData[4];
																				htmlVar += "<td>"
																						+ Project
																						+ "</td>";

																				var Skills = objectData[5];
																				htmlVar += "<td>"
																						+ Skills
																						+ "</td>";
																				var baselocation = objectData[6];
																				htmlVar += "<td>"
																						+ baselocation
																						+ "</td>";
																				var currentlocation = objectData[7];
																				htmlVar += "<td>"
																						+ currentlocation
																						+ "</td>";
																				var customer = objectData[8];
																				htmlVar += "<td>"
																						+ customer
																						+ "</td>";
																				var dateofjoining = objectData[9];
																				htmlVar += "<td>"
																						+ dateofjoining
																						+ "</td>";
																				var grade = objectData[10];
																				htmlVar += "<td>"
																						+ grade
																						+ "</td>";
																				var startallocationdate = objectData[11];
																				htmlVar += "<td>"
																						+ startallocationdate
																						+ "</td>";

																				var endallocationdate = objectData[12];
																				htmlVar += "<td>"
																						+ endallocationdate
																						+ "</td>";

																				var allocationtype = objectData[13];
																				htmlVar += "<td>"
																						+ allocationtype
																						+ "</td>";

																				var dummystart = objectData[14];

																				htmlVar += "<td>"
																						+ dummystart
																						+ "</td>";
																				var dummyend = objectData[15];

																				htmlVar += "<td>"
																						+ dummyend
																						+ "</td>";
																				var actualResUti = objectData[16];
                                                                                htmlVar += "<td>"
                                                                                        + actualResUti
                                                                                        + "</td>";
                                                                                htmlVar += "</tr>";

																			}

																			buResourcePLReportTable
																					.empty()
																					.append(
																							htmlVar);
																			buResourcePLReportTable
																					.hide();
																		}

																		if (key == "resignedResourcePLReportList") {
																			var resignedResourcePLReportTable = $('#resignedResourcePLReportID');
																			var htmlVar = "<thead><tr><th>Employee ID</th><th>Employee Name (As per Payroll Records)</th><th>Designation</th><th>BG_BU</th><th>Project</th><th>Grade</th><th>From (YY-MM-DD)</th><th>To (YY-MM-DD)</th><th>Released Date (YY-MM-DD)</th><th>Actual Res Utilization</th></tr></thead>";

																			for (i = 0; i < val.length; i++) {
																				htmlVar += "<tr>";
																				var objectData = val[i];

																				var employeeid = objectData[0];
																				htmlVar += "<td>"
																						+ employeeid
																						+ "</td>";
																				var Name = objectData[1];
																				htmlVar += "<td>"
																						+ Name
																						+ "</td>";
																				var Designation = objectData[2];
																				htmlVar += "<td>"
																						+ Designation
																						+ "</td>";
																				var BG_BU = objectData[3];
																				htmlVar += "<td>"
																						+ BG_BU
																						+ "</td>";
																				var Project = objectData[4];
																				htmlVar += "<td>"
																						+ Project
																						+ "</td>";

																				var Grade = objectData[5];
																				htmlVar += "<td>"
																						+ Grade
																						+ "</td>";
																				var From = objectData[6];
																				htmlVar += "<td>"
																						+ From
																						+ "</td>";
																				var To = objectData[7];
																				htmlVar += "<td>"
																						+ To
																						+ "</td>";
																				var ReleaseDate = objectData[8];
																				htmlVar += "<td>"
																						+ ReleaseDate
																						+ "</td>";
																				var actualResUti  = objectData[9];
                                                                                htmlVar += "<td>"
                                                                                        + actualResUti 
                                                                               		+ "</td>";

																				htmlVar += "</tr>";

																			}
																			resignedResourcePLReportTable
																					.empty()
																					.append(
																							htmlVar);
																			resignedResourcePLReportTable
																					.hide();
																		}

																		if (key == "contractResourcePLReportList") {

																			var contractResourcePLReportTable = $('#contractResourcePLReportID');

																			var htmlVar = "<thead><tr><th>S.No</th><th>Employee ID</th><th>Employee name as per Payroll Record</th><th>Department</th><th>Skills</th><th>Designation</th><th>Date Of Joining (YY-MM-DD)</th><th>Location</th><th>Project</th><th colspan='2'>Worked during the Month</th><th>Actual Res Utilization</th></tr><tr><th colspan='9'>&nbsp;</th><th>From (YY-MM-DD)</th><th>To (YY-MM-DD)</th><th colspan='9'>&nbsp;</th></tr></thead>";

																			for (i = 0; i < val.length; i++) {
																				htmlVar += "<tr>";
																				var objectData = val[i];
																				htmlVar += "<td>"
																						+ (i + 1)
																						+ "</td>";
																				var employeeid = objectData[0];
																				htmlVar += "<td>"
																						+ employeeid
																						+ "</td>";
																				var candidatename = objectData[1];
																				htmlVar += "<td>"
																						+ candidatename
																						+ "</td>";
																				var buname = objectData[2];
																				htmlVar += "<td>"
																						+ buname
																						+ "</td>";
																				var skill = objectData[3];
																				htmlVar += "<td>"
																						+ skill
																						+ "</td>";
																				var designation = objectData[4];
																				htmlVar += "<td>"
																						+ designation
																						+ "</td>";
																				var DOJ = objectData[5];
																				htmlVar += "<td>"
																						+ DOJ
																						+ "</td>";
																				var location = objectData[6];
																				htmlVar += "<td>"
																						+ location
																						+ "</td>";
																				var project = objectData[7];
																				htmlVar += "<td>"
																						+ project
																						+ "</td>";
																				var startdate = objectData[8];
																				htmlVar += "<td>"
																						+ startdate
																						+ "</td>";
																				var enddate = objectData[9];
																				htmlVar += "<td>"
																						+ enddate
																						+ "</td>";
																				var actualResUti = objectData[10];
                                                                                htmlVar += "<td>"
                                                                                        + actualResUti
                                                                                        + "</td>";
                                                                                                     
																				htmlVar += "</tr>";

																			}
																			contractResourcePLReportTable
																					.empty()
																					.append(
																							htmlVar);
																			contractResourcePLReportTable
																					.show();

																			$(
																					'#contractResourcePLReportID')
																					.DataTable(
																							{
																								"sPaginationType" : "full_numbers",
																								"retrieve" : true,
																								"bDestroy" : true

																							});

																			$(
																					'#buResourcePLReportID_info')
																					.hide();
																			$(
																					'#resignedResourcePLReportID_info')
																					.hide();
																			$(
																					'#borrowedResourcePLReportID_info')
																					.hide();
																			$(
																					'#contractResourcePLReportID_info')
																					.show();

																			$(
																					'#buResourcePLReportID_paginate')
																					.hide();
																			$(
																					'#resignedResourcePLReportID_paginate')
																					.hide();
																			$(
																					'#borrowedResourcePLReportID_paginate')
																					.hide();
																			$(
																					'#contractResourcePLReportID_paginate')
																					.show();
																			$(
																					'table td:contains("null")')
																					.html(
																							'');

																			$(
																					'#showContractID')
																					.addClass(
																							'activeReportTab');

																		}

																		if (key == "borrowedResourcesPLReportList") {

																			var borrowedResourcePLReportTable = $('#borrowedResourcePLReportID');
																			var htmlVar = "<thead><tr><th>Employee ID</th><th>Name</th><th>Project</th><th>Buisness Unit</th><th>Bowrrowed Buisness Unit</th><th>Designation</th><th>Skills</th><th>Date Of Joining (YY-MM-DD)</th><th>Grade</th><th>Allocation Type</th><th>Allocation Start Date (YY-MM-DD)</th><th>Allocation End Date (YY-MM-DD)</th><th>Customer</th><th>Base Location</th><th>Current Location</th><th>Actual Res Utilization</th></tr></thead>";

																			for (i = 0; i < val.length; i++) {
																				htmlVar += "<tr>";
																				var objectData = val[i];
																				var employeeid = objectData[0];
																				htmlVar += "<td>"
																						+ employeeid
																						+ "</td>";
																				var Name = objectData[1];
																				htmlVar += "<td>"
																						+ Name
																						+ "</td>";
																				var Project = objectData[2];
																				htmlVar += "<td>"
																						+ Project
																						+ "</td>";
																				var BUName = objectData[3];
																				htmlVar += "<td>"
																						+ BUName
																						+ "</td>";
																				var BorrowedBUName = objectData[4];
																				htmlVar += "<td>"
																						+ BorrowedBUName
																						+ "</td>";
																				var Designation = objectData[5];
																				htmlVar += "<td>"
																						+ Designation
																						+ "</td>";
																				var Skills = objectData[6];
																				htmlVar += "<td>"
																						+ Skills
																						+ "</td>";
																				var Grade = objectData[7];
																				htmlVar += "<td>"
																						+ Grade
																						+ "</td>";
																				var employeeid = objectData[8];
																				htmlVar += "<td>"
																						+ employeeid
																						+ "</td>";
																				var DOJ = objectData[9];
																				htmlVar += "<td>"
																						+ DOJ
																						+ "</td>";
																				var Grade = objectData[10];
																				htmlVar += "<td>"
																						+ Grade
																						+ "</td>";
																				var Grade = objectData[11];
																				htmlVar += "<td>"
																						+ Grade
																						+ "</td>";
																				var Grade = objectData[12];
																				htmlVar += "<td>"
																						+ Grade
																						+ "</td>";
																				var Grade = objectData[13];
																				htmlVar += "<td>"
																						+ Grade
																						+ "</td>";
																				var Grade = objectData[14];
																				htmlVar += "<td>"
																						+ Grade
																						+ "</td>";
																				var actualResUti = objectData[15];
                                                                                htmlVar += "<td>"
                                                                                        + actualResUti
                                                                                        + "</td>";
                                                                                htmlVar += "</tr>";

																			}
																			borrowedResourcePLReportTable
																					.empty()
																					.append(
																							htmlVar);
																			borrowedResourcePLReportTable
																					.hide();
																		}

																	});

												}
											}

										});
							}
							$('table:contains("null")').html('');
						});//closing of submit click

		function showReports(data) {
			$.each(data, function(key, val) {});
		}
	</script>
</body>