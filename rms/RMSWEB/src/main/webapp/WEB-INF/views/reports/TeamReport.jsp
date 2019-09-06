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
	var recordTable;
	var oValue = new Array();
	var lValue = new Array();
	var pValue = new Array();

	//search team name
	$(function() {

		$('#team').multiselect({
			includeSelectAllOption : true
		}).multiselectfilter();
		
	});

	$(document).ready(function() {

		$('#exportToExcel').hide();

		$(function() {
			$("#weekStartDate").datepicker({
					maxDate : 0,
					beforeShowDay : function(date) {
						return [ date.getDay() == 0, "" ]
					}
				});
			$("#weekEndDate").datepicker({
					maxDate : 0,
					beforeShowDay : function(date) {
						return [ date.getDay() == 6, "" ]
					}
				});
		});

		$('#team, #weekStartDate ,#weekEndDate').on('change', function(e) {
			$("#exportToExcel").hide();
		});

	});

	function getReport() {

		var weekStartDate = document.getElementById("weekStartDate");
		var weekEndDate = document.getElementById("weekEndDate");
		$('#team > option:selected').each(function(i) {
			lValue[i] = $(this).val();
		});

	}

	$(document).on('click','#submit',function() {

						var weekStartDate = $("#weekStartDate").val();
						var weekEndDate = $("#weekEndDate").val();
						lValue = $("#team").val();

						var startDate = document.getElementById("weekStartDate").value;
						var endDate = document.getElementById("weekEndDate").value;

						var errmsg = "Please select:";
						var validationFlag = true;

						if ($("#team").val() == null) {
							errmsg = errmsg + "<br> Team ";

							validationFlag = false;
						}

						if (document.getElementById("weekStartDate").value == "") {
							errmsg = errmsg + "<br> Start date ";
							validationFlag = false;
						}
						if (document.getElementById("weekEndDate").value == "") {
							errmsg = errmsg + "<br> End date ";

							validationFlag = false;
						}

						if ((Date.parse(weekStartDate) >= Date.parse(weekEndDate))) {

							document.getElementById("weekEndDate").value = "";

							errmsg = errmsg
									+ "<br>Correct End Date"
									+ "<br>(End Date should be greater that Start Date)";
							validationFlag = false;
						}

						if (!validationFlag) {

							stopProgress();
							if (errmsg.length > 0)

								showError(errmsg);

							return;
						} else {

							$.ajax({

										type : 'GET',
										url : '/rms/teamReports/' + lValue,
										data : {
											find : "getTeamReport",
											"teamId" : lValue,

											"weekStartDate" : weekStartDate,
											"weekEndDate" : weekEndDate
										},

										async : true,
										success : function(data) {

											if (jQuery.isEmptyObject(data)) {

												$("#NoAllocMessage").show();
												$('#TeamReportTable').hide();

												var htmlVarObj = '<div id="NoAllocMessage" class="NoAllocMessage">No Record Exist for selected team in selected week</div>';

												$('#NoAllocMessage').empty().append(htmlVarObj);

											} else {

												$("#exportToExcel").show();
												$('#TeamReportTable').show();
												$("#NoAllocMessage").hide();

												var TeamName = [], projectName = [], projectMap, weekStart = [], weekEnd = [], z = 0;

												var totalProjectHours = [], teamHours = [], totalTeamHour = [];

												var map = [], hoursArray = [], hours = [];
												var monthNames = ["Jan", "Feb", "Mar",
												                  "Apr", "May", "Jun", "Jul",
												                  "Aug", "Sep", "Oct",
												                  "Nov", "Dec"];

												var htmlVar = "<table><thead><tr><th rowspan=2>Team Name</th><th rowspan=2> Project Name</th><th rowspan=2>Employee Name</th>";

												$.each(data,function(k4, v4) {

													var projectHours = 0;

														$.each(v4,function(k5,v5) {

															var teamList1 = v5;

															for (var i = 0; i < teamList1.length; i++) {

															var weeks = teamList1[i].weeklyData; 
															

																for (var y = 0; y < weeks.length; y++) {

																	hours = new Object();

																		if (weekStart.indexOf(weeks[y].weekStartDate) == -1) {
																			
																			 
																			
																			if (weeks[y].weekStartDate != undefined) {
																				
																				weekStart.push(weeks[y].weekStartDate);
																				
																			}
																			
																		}

																		if (weekEnd.indexOf(weeks[y].weekEndDate) == -1) {
																			
																			 
																			
																			if (weeks[y].weekEndDate != undefined) {
																				
																				weekEnd.push(weeks[y].weekEndDate);
																				
																			}
																			
																		}

																	hours.prodHrs = weeks[y].productiveHours;
																	hours.nonProdHrs = weeks[y].nonProductiveHours;
																	
																	if (weeks[y].plannedHours == undefined) {
																		
																		hours.plannedHrs = 0;
																		
																	} else {
																		
																		hours.plannedHrs = weeks[y].plannedHours;
																		
																	}
																	
																	if (weeks[y].billedHours == undefined) {
																		
																		hours.billedHrs = 0;
																		
																	} else {
																		
																		hours.billedHrs = weeks[y].billedHours;
																		
																	}

																	hours.weekEndDate = weeks[y].weekEndDate;
																	hoursArray.push(hours);
																	hours = [];
																	
																}
																
															}
															
															totalProjectHours.push(hoursArray);
															hoursArray = [];
															
														});

														teamHours.push(totalProjectHours);
														totalProjectHours = [];
														
												});

												//Populate Week header
												for (var i = 0; i < weekStart.length; i++) {
													
													if (weekStart[i] != "undefined" && weekEnd[i] != "undefined") {
														
														var dateObjStartDate = new Date(weekStart[i]);
														
														var month = monthNames[dateObjStartDate.getMonth()] ; //months from 1-12
														var day = dateObjStartDate.getUTCDate();
														var year = dateObjStartDate.getUTCFullYear();
														
														newStartDate = day + "-" + month + "-" + year;
														
														//weekStart.push(newStartDate);
														
														var dateObjEndDate = new Date(weekEnd[i]);
																				
														var month = monthNames[dateObjEndDate.getMonth()] ; //months from 1-12
														var day = dateObjEndDate.getUTCDate();
														var year = dateObjEndDate.getUTCFullYear();
																				
														newEndDate = day + "-" + month + "-" + year;
																				
														//weekEnd.push(newEndDate);
														
														htmlVar += "<th colspan =4>"
																+ newStartDate
																+ " To "
																+ newEndDate
																+ "</th>";
													}
												}

												htmlVar += "</tr><tr>"

												for (var i = 0; i < weekStart.length; i++) {
													
													if (weekStart[i] != "undefined" && weekEnd[i] != "undefined") {
														
														htmlVar += "<th>Planned Hours</th> <th>Reported - Productive Hours</th> <th>Reported - Non Productive Hours</th> <th>Billed Hours</th> "
													}

												}

												htmlVar += "</tr>";

												var teamlength = 0, u = 0;

												$.each(data,function(k, v) {

													map = new Object();
													
													TeamName = k;

													projectMap = v;
													
													map[z++] = v;

													var projectHour = teamHours[u++];

													var teamList, projectWiseResource = 0, x = 0, prjHours1 = [], finalTotalTeamHour = [];

													for (var h = 0; h < projectHour.length; h++) {

														var hourarr = projectHour[h];
														var hrsSum1 = new Object();

														for (var r = 0; r < weekEnd.length; r++) {

															var prodhrs3 = 0, nonProd3 = 0, billedhrs3 = 0, plannedHrs3 = 0;

															for (var g = 0; g < hourarr.length; g++) {

																if (hourarr[g].weekEndDate == weekEnd[r]) {

																	prodhrs3 = prodhrs3 + hourarr[g].prodHrs;

																	nonProd3 = nonProd3 + hourarr[g].nonProdHrs;

																		if (hourarr[g].billedHrs != undefined) {

																			billedhrs3 = billedhrs3 + hourarr[g].billedHrs;
																			
																		}

																		if (hourarr[g].plannedHrs != undefined) {
																			
																			plannedHrs3 = plannedHrs3 + hourarr[g].plannedHrs;
																			
																		}
																		
																}
																
															}

															hrsSum1.prodHrs = prodhrs3;
															hrsSum1.nonProdHrs = nonProd3;
															hrsSum1.plannedHrs = plannedHrs3;
															hrsSum1.billedHrs = billedhrs3;
															hrsSum1.weekEnd = weekEnd[r];

															prjHours1.push(hrsSum1);
															hrsSum1 = [];
															
														}
														
													}

											var teamHoursObject = [];
											
											for (var r = 0; r < weekEnd.length; r++) {
												
												teamHoursObject = new Object();
												
												var prodhrs1 = 0, nonProd1 = 0, billedhrs1 = 0, plannedHrs1 = 0;
												
													for (var n = 0; n < prjHours1.length; n++) {
														
														if (prjHours1[n].weekEnd == weekEnd[r]) {
															
															var obj = prjHours1[n];
															
															prodhrs1 = prodhrs1 + obj.prodHrs;
															nonProd1 = nonProd1 + obj.nonProdHrs;
															
															plannedHrs1 = plannedHrs1 + obj.plannedHrs;
															billedhrs1 = billedhrs1 + obj.billedHrs;
															
														}
														
													}
												teamHoursObject.prodHrs = prodhrs1;
												teamHoursObject.nonProdHrs = nonProd1;
												teamHoursObject.plannedHrs = plannedHrs1;
												teamHoursObject.billedHrs = billedhrs1;
												totalTeamHour.push(teamHoursObject);
												teamHoursObject = [];
												
											}

										finalTotalTeamHour.push(totalTeamHour);

										$.each(projectMap,function(k1,v1) {

											projectName = k1;
											teamList = v1;

											projectWiseResource = projectWiseResource + teamList.length + 1;
											
										});

										teamlength = projectWiseResource + 1;

										htmlVar += "<tr><td rowspan ="+teamlength+">"
												+ TeamName
												+ "</td><td></td><td style='font-weight:bold'>Total Hours Team</td> ";
												
										for (var f = 0; f < totalTeamHour.length; f++) {
											
											htmlVar += " <td style='font-weight:bold'>";
											htmlVar += totalTeamHour[f].plannedHrs;
											htmlVar += "</td>";

											htmlVar += " <td style='font-weight:bold'>  ";
											htmlVar += totalTeamHour[f].prodHrs;
																		htmlVar += "</td>";

											htmlVar += " <td style='font-weight:bold'>";
											htmlVar += totalTeamHour[f].nonProdHrs;
											htmlVar += "</td>";

											htmlVar += " <td style='font-weight:bold'>";
											htmlVar += totalTeamHour[f].billedHrs;
											htmlVar += "</td>";
											
										}


										htmlVar += "</tr>";
										totalTeamHour = [];

										$.each(map,function(k2,v2) {

											$.each(v2,function(k3,v3) {

												var len = v3.length + 1;
												var hourarr = projectHour[x++];

												var prjHours = [], hrsSum = new Object();

												//Retirving Data to Calculate total Project hours

													for (var r = 0; r < weekEnd.length; r++) {

														var prodhrs = 0, nonProd = 0, billedhrs = 0, plannedHrs = 0

														for (var g = 0; g < hourarr.length; g++) {
															
															if (hourarr[g].weekEndDate == weekEnd[r]) {
																
																prodhrs = prodhrs+ hourarr[g].prodHrs;
																nonProd = nonProd+ hourarr[g].nonProdHrs;

																	if (hourarr[g].billedHrs != undefined) {
																			billedhrs = billedhrs+ hourarr[g].billedHrs;
																			
																	}

																	if (hourarr[g].plannedHrs != undefined) {
																		plannedHrs = plannedHrs+ hourarr[g].plannedHrs;
																		
																	}
																	
															}
															
														}

														hrsSum.prodHrs = prodhrs;
														hrsSum.nonProdHrs = nonProd;
														hrsSum.plannedHrs = plannedHrs;
														hrsSum.billedHrs = billedhrs;
														hrsSum.weekEnd = weekEnd[r];

														prjHours.push(hrsSum);
														hrsSum = [];
														
													}

													htmlVar += "<tr><td rowspan="+len+">" + k3 + "</td><td>Total Hours Project</td>";

													//Populating Total Project Hours

													for (var d = 0; d < prjHours.length; d++) {
														
														htmlVar += " <td>";
														htmlVar += prjHours[d].plannedHrs;
														htmlVar += "</td>";

														htmlVar += " <td>  ";
														htmlVar += prjHours[d].prodHrs;
														htmlVar += "</td>";

														htmlVar += " <td>";
														htmlVar += prjHours[d].nonProdHrs;
														htmlVar += "</td>";																												

														htmlVar += " <td>";
														htmlVar += prjHours[d].billedHrs;
														htmlVar += "</td>";
														
													}

													htmlVar += "</tr>";

													for (var f = 0; f < v3.length; f++) {

														var weekData = v3[f].weeklyData;

														//Populating Employee Name

														htmlVar += "<tr><td>" + v3[f].employeeName + "</td>";

														//Populating Indivisual Hours		

														for (var r = 0; r < weekEnd.length; r++) {

															for (var s = 0; s < weekData.length; s++) {

																if (weekEnd[r] == weekData[s].weekEndDate) {

																	if (weekData[s].plannedHours != undefined){
																		
																		htmlVar += " <td>" + weekData[s].plannedHours + "</td>";
																		
																	} else{
																		
																		htmlVar += " <td> 0 </td>";
																		
																	}
																		
																	htmlVar += " <td>" + weekData[s].productiveHours + "</td>";

																	htmlVar += " <td>" + weekData[s].nonProductiveHours + "</td>";

																	if (weekData[s].billedHours != undefined)
																			htmlVar += " <td>" + weekData[s].billedHours + "</td>";
																	else
																			htmlVar += " <td> 0 </td>";
																			
																}
																
															}
															
														}
														
														htmlVar += " </tr>";
														
													}
													
											});
											
										});
										
										map = [];
										
									});
									
									htmlVar += " </table>";
									$('#TeamReportTable').empty().append(htmlVar);
									
											}
											
										}
										
							});
							
						}});
</script>

<html>
<body>

	<div class="spinner-wrap">
		<span class="spinner"></span>
	</div>


	<div class="content-wrapper">
		<div class="botMargin">
			<h1>Team Report</h1>
		</div>
		<div class="tab_seaction">
			<ul class='tabs'>
				<li><a href='#tab1' id="tab1id">List</a></li>

			</ul>
			<div id='tab1' class="tab_div">


				<div class="form">

					<form>
						<table id="ReportTable">

							<tr>

								<td>
									<div class="positionRel floatR">
										<label>Team :<span class="astric">*</span></label> <select
											name="team.id" id="team" multiple="multiple" class="required">

											<c:forEach items="${teamList}" var="team">
												<option value="${team.id}">${team.teamName}</option>

											</c:forEach>

										</select>
									</div>
								</td>

								<td>
									<div class="stDtEnDt tsstDtEnDtWrapper">

										<label>Start Date :<span class="astric">*</span></label><span
											style="height: 131px"> <input type="text"
											name="weekStartDate" value="" id="weekStartDate" size="35"
											maxlength="80" readonly="readonly" />

										</span>&nbsp; &nbsp; <label>End Date :<span class="astric">*</span>

										</label><span style="height: 131px"> <input type="text"
											name="weekEndDate" id="weekEndDate" size="35" maxlength="80"
											value="" readonly="readonly" />
										</span>

									</div>

								</td>


								<td><a href="#" class="blue_link" id="submit"><i
										class="fa fa-file-excel-o"></i> View Report </a></td>

								<td><a href="exportToExcel" class="blue_link"
									id="exportToExcel"><i class="fa fa-file-excel-o"></i>
										Export To Excel </a></td>
							</tr>

							<tr>
								<td colspan="2">&nbsp;</td>
								<td colspan="2"><font color="#FF0000">** NOTE:
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
		<div class="datatable-scroll">


			<table id="TeamReportTable" class="dataTable dtable"></table>

		</div>

		<div>

			<table id="NoAllocMessage"></table>

		</div>

	</div>
</body>
</html>

