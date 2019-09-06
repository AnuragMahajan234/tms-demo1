<%@page import="org.yash.rms.util.UserUtil"%>
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

<spring:url value="/moduleReports/customModuleReport" var="customModuleReport" />

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
	var plannedHoursCheck;
	var billedHoursCheck;
	var productiveHoursCheck;
	var nonProductiveHoursCheck;
	var data;
	//search team name
	$(function() {

		$('#project').multiselect({
			includeSelectAllOption : true
		}).multiselectfilter();
	});

	$(document).ready(function() {
		
		$("#project").change(function () {
			 $('.noWidth').each(function(){
	                this.checked = true;
	            });
	    });
		

		$('#exportToExcel').hide();

		$(function() {
			$("#weekEndDate").datepicker({
				  maxDate: new Date(), 
				
				beforeShowDay : function(date) {
					return [ date.getDay() > 0, "" ]
				} 

			});
			$("#weekStartDate").datepicker({
				maxDate: new Date()	,
				/* maxDate : 0,
				beforeShowDay : function(date) {
					return [ date.getDay() == 0, "" ]
				} ,*/
					
				onSelect: function(date){            
		            var date1 = $('#weekStartDate').datepicker('getDate');           
		            var date = new Date( Date.parse( date1 ) ); 
		            date.setDate( date.getDate() + 1 );        
		            var newDate = date.toDateString(); 
		            newDate = new Date( Date.parse( newDate ) );                      
		            $('#weekEndDate').datepicker("option","minDate",newDate);            
		        }
			});
		});

		$('#team, #weekStartDate ,#weekEndDate').on('change', function(e) {
			$("#exportToExcel").hide();
		});

		// table update on checkbox change
		$('.noWidth').on('click', function() {
			//alert(data);
			if (data != null && data != undefined && data != "") {
				plannedHoursCheck = document.getElementById("plannedHours").checked;
				billedHoursCheck = document.getElementById("billedHours").checked;
				productiveHoursCheck = document.getElementById("productiveHours").checked;
				nonProductiveHoursCheck = document.getElementById("nonProductiveHours").checked;

				showReport(data, plannedHoursCheck, billedHoursCheck, productiveHoursCheck, nonProductiveHoursCheck);
			}
		});

	});

	function getReport() {

		var weekStartDate = document.getElementById("weekStartDate");
		var weekEndDate = document.getElementById("weekEndDate");
		$('#team > option:selected').each(function(i) {
			lValue[i] = $(this).val();
		});

	}

	// send data in request param
	function exportDataToExcel(event) {

		var requestParam = "?";

		requestParam = requestParam + "plannedHours=" + plannedHoursCheck + "&billedHours=" + billedHoursCheck + "&productiveHours=" + productiveHoursCheck
				+ "&nonProductiveHours=" + nonProductiveHoursCheck;

		var _href = $('#exportToExcel').attr('href');
		var res = _href.substring(0, 13);
		$('#exportToExcel').attr("href", res + requestParam);
		$("#moduleReportForm").submit();

	}

	$(document).on('click', '#submit', function() {

		var weekStartDate = $("#weekStartDate").val();
		var weekEndDate = $("#weekEndDate").val();
		lValue = $("#project").val();

		plannedHoursCheck = document.getElementById("plannedHours").checked;
		billedHoursCheck = document.getElementById("billedHours").checked;
		productiveHoursCheck = document.getElementById("productiveHours").checked;
		nonProductiveHoursCheck = document.getElementById("nonProductiveHours").checked;
		var startDate = document.getElementById("weekStartDate").value;
		var endDate = document.getElementById("weekEndDate").value;

		var errmsg = "Please select:";
		var validationFlag = true;

		if ($("#project").val() == null) {
			errmsg = errmsg + "<br> project ";

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

			errmsg = errmsg + "<br>Correct End Date" + "<br>(End Date should be greater that Start Date)";
			validationFlag = false;
		}
		
		if($('input.noWidth:checked').length < 1){
			errmsg = errmsg + "<br>At least one amongst - planned hours, billed hours, productive hours or non-productive hours to view report";
			
			validationFlag = false;
		}
		
		

		if (!validationFlag) {

			stopProgress();
			if (errmsg.length > 0)

				showError(errmsg);

			return;
		} else {

			$.ajax({

				type : 'POST',
				url : '/rms/moduleReports/' + lValue,
				data : {
					find : "getModuleReport",
					"projectId" : lValue,

					"weekStartDate" : weekStartDate,
					"weekEndDate" : weekEndDate
				},

				async : true,
				success : function(reportData) {
					 
					if (jQuery.isEmptyObject(reportData)) {

						$("#NoAllocMessage").show();
						$('#ModuleReportTable').hide();

						var htmlVarObj = '<div id="NoAllocMessage" class="NoAllocMessage">No Record Exist for selected project in selected week</div>';

						$('#NoAllocMessage').empty().append(htmlVarObj);

					} else {

						data = reportData;
						showReport(data, plannedHoursCheck, billedHoursCheck, productiveHoursCheck, nonProductiveHoursCheck);

					}
				}
			});

		}
	});
	
	// function to get customized module report data on jsp --- start
	$(document).on('click', '#customReport', function() {
		
		lValue = $("#project").val();
		var weekStartDate = $("#weekStartDate").val();
		var weekEndDate = $("#weekEndDate").val();
		
		$.fancybox.open({
			autoSize : false,
			closeClick : true,
			autoDimensions : true,
			transitionIn : 'fade',
			transitionOut : 'fade',
			openEffect : 'easingIn',
			closeEffect : 'easingOut',
			type : 'iframe',
			href : '${customModuleReport}'+"/projectIds/"+lValue+"?weekStartDate="+weekStartDate+"&weekEndDate="+weekEndDate,
			'width' : '100%',
			'height' : '563px',
			preload : true,
			beforeShow : function() {
				var thisH = this.height - 35;
				$(".fancybox-iframe").contents().find('html').find(".midSection").css('height', thisH);		
			
			},
			helpers : {
				overlay : {
					closeClick : true
				}
			// prevents closing when clicking OUTSIDE fancybox 
			},
		});
	});
	// function to get customized module report data on jsp --- end
	
	// function to show report on jsp
	function showReport(data, plannedHoursCheck, billedHoursCheck, productiveHoursCheck, nonProductiveHoursCheck) {

		if($('input.noWidth:checked').length < 1){
			
			errmsg = "Please select at least one amongst - planned hours, billed hours, productive hours or non-productive hours to view Report";
			
			showError(errmsg);
			$("#exportToExcel").hide();
			$('#ModuleReportTable').hide();
			$("#NoAllocMessage").show();
			data = "";
		}else{
			$("#exportToExcel").show();
			$('#ModuleReportTable').show();
			$("#NoAllocMessage").hide();
			var moduleName = [], projectName = [], projectMap, weekStart = [], weekEnd = [], z = 0;

			var totalProjectHours = [], moduleHours = [], totalModuleHour = [];

			var map = [], hoursArray = [], hours = [];
			
			var monthNames = ["Jan", "Feb", "Mar",
			                  "Apr", "May", "Jun", "Jul",
			                  "Aug", "Sep", "Oct",
			                  "Nov", "Dec"];

			var htmlVar = "<table><thead><tr><th rowspan=2> Module Name</th><th rowspan=2> Project Name</th><th rowspan=2>Employee Name</th>";

			$.each(data, function(k4, v4) {

				var projectHours = 0;

				$.each(v4, function(k5, v5) {

					var moduleList1 = v5;

					for (var i = 0; i < moduleList1.length; i++) {

						var weeks = moduleList1[i].weeklyData;

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

				moduleHours.push(totalProjectHours);
				totalProjectHours = [];

			});

			//	alert($('input.noWidth:checked').length);
			var dateSpan = $('input.noWidth:checked').length;
			weekStart.sort();
			weekEnd.sort();
			//Populate 
			for (var i = 0; i < weekStart.length; i++) {

				if (weekStart[i] != "undefined" && weekEnd[i] != "undefined") {
					var dateObjStartDate = new Date(weekStart[i]);
					
					var month = monthNames[dateObjStartDate.getMonth()] ; //months from 1-12
					var day = dateObjStartDate.getUTCDate();
					var year = dateObjStartDate.getUTCFullYear();
					
					newStartDate = day + "-" + month + "-" + year;
					
					var dateObjEndDate = new Date(weekEnd[i]);
					
					var month = monthNames[dateObjEndDate.getMonth()] ; //months from 1-12
					var day = dateObjEndDate.getUTCDate();
					var year = dateObjEndDate.getUTCFullYear();
											
					newEndDate = day + "-" + month + "-" + year;
					
					htmlVar += "<th colspan ="+dateSpan+" style='text-align:center'>" + newStartDate + " To " + newEndDate + "</th>";

					 
				}
			}

			htmlVar += "</tr><tr>"

			for (var i = 0; i < weekStart.length; i++) {
				if (weekStart[i] != "undefined" && weekEnd[i] != "undefined") {
					if (plannedHoursCheck) {
						htmlVar += "<th>Planned Hours</th>";
					}
					if (productiveHoursCheck) {
						htmlVar += "<th>Reported - Productive Hours</th>";
					}
					if (nonProductiveHoursCheck) {
						htmlVar += "<th>Reported - Non Productive Hours</th>";
					}
					if (billedHoursCheck) {
						htmlVar += "<th>Billed Hours</th>";
					}
				}

			}

			htmlVar += "</tr>";

			var teamlength = 0, u = 0;

			$.each(data, function(k, v) {

				map = new Object();
				moduleName = k;

				projectMap = v;
				map[z++] = v;

				var projectHour = moduleHours[u++];

				var teamList, projectWiseResource = 0, x = 0, prjHours1 = [], finaltotalModuleHour = [];

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

				var moduleHoursObject = [];
				for (var r = 0; r < weekEnd.length; r++) {
					moduleHoursObject = new Object();
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
					moduleHoursObject.prodHrs = prodhrs1;
					moduleHoursObject.nonProdHrs = nonProd1;
					moduleHoursObject.plannedHrs = plannedHrs1;
					moduleHoursObject.billedHrs = billedhrs1;
					totalModuleHour.push(moduleHoursObject);
					moduleHoursObject = [];
				}

				finaltotalModuleHour.push(totalModuleHour);

				$.each(projectMap, function(k1, v1) {

					projectName = k1;
					teamList = v1;

					projectWiseResource = projectWiseResource + teamList.length + 1;

				});

				teamlength = projectWiseResource + 1;

				htmlVar += "<tr><td rowspan ="+teamlength+">" + moduleName + "</td><td></td><td style='font-weight:bold'>Total Module Hours</td> ";
				for (var f = 0; f < totalModuleHour.length; f++) {
					if (plannedHoursCheck) {
						htmlVar += " <td style='font-weight:bold' align='center' >";
						htmlVar += totalModuleHour[f].plannedHrs;
						htmlVar += "</td>";
					}
					if (productiveHoursCheck) {
						htmlVar += " <td style='font-weight:bold' align='center'>  ";
						htmlVar += totalModuleHour[f].prodHrs;
						htmlVar += "</td>";
					}
					if (nonProductiveHoursCheck) {
						htmlVar += " <td style='font-weight:bold' align='center'>";
						htmlVar += totalModuleHour[f].nonProdHrs;
						htmlVar += "</td>";
					}
					if (billedHoursCheck) {
						htmlVar += " <td style='font-weight:bold' align='center'>";
						htmlVar += totalModuleHour[f].billedHrs;
						htmlVar += "</td>";
					}
				}
				htmlVar += "</tr>";
				totalModuleHour = [];

				$.each(map, function(k2, v2) {

					$.each(v2, function(k3, v3) {

						var len = v3.length + 1;
						var hourarr = projectHour[x++];

						var prjHours = [], hrsSum = new Object();

						//Retirving Data to Calculate total Project hours

						for (var r = 0; r < weekEnd.length; r++) {

							var prodhrs = 0, nonProd = 0, billedhrs = 0, plannedHrs = 0

							for (var g = 0; g < hourarr.length; g++) {
								if (hourarr[g].weekEndDate == weekEnd[r]) {
									prodhrs = prodhrs + hourarr[g].prodHrs;

									nonProd = nonProd + hourarr[g].nonProdHrs;

									if (hourarr[g].billedHrs != undefined) {
										billedhrs = billedhrs + hourarr[g].billedHrs;
									}

									if (hourarr[g].plannedHrs != undefined) {
										plannedHrs = plannedHrs + hourarr[g].plannedHrs;
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

						htmlVar += "<tr><td rowspan="+len+" align = 'center'>" + k3 + "</td><td>Total Hours Project</td>";

						//Populating Total Project Hours

						for (var d = 0; d < prjHours.length; d++) {

							if (plannedHoursCheck) {
								htmlVar += " <td align = 'center'>";
								htmlVar += prjHours[d].plannedHrs;
								htmlVar += "</td>";
							}
							if (productiveHoursCheck) {
								htmlVar += " <td align = 'center'>  ";
								htmlVar += prjHours[d].prodHrs;
								htmlVar += "</td>";
							}
							if (nonProductiveHoursCheck) {
								htmlVar += " <td align = 'center'>";
								htmlVar += prjHours[d].nonProdHrs;
								htmlVar += "</td>";
							}
							if (billedHoursCheck) {
								htmlVar += " <td align = 'center'>";
								htmlVar += prjHours[d].billedHrs;
								htmlVar += "</td>";
							}

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

										if (plannedHoursCheck) {
											if (weekData[s].plannedHours != undefined)
												htmlVar += " <td align = 'center'>" + weekData[s].plannedHours + "</td>";
											else
												htmlVar += " <td align = 'center'> 0 </td>";
										}
										if (productiveHoursCheck) {
											htmlVar += " <td align = 'center'>" + weekData[s].productiveHours + "</td>";
										}
										if (nonProductiveHoursCheck) {
											htmlVar += " <td align = 'center'>" + weekData[s].nonProductiveHours + "</td>";
										}
										if (billedHoursCheck) {
											if (weekData[s].billedHours != undefined)
												htmlVar += " <td align = 'center'>" + weekData[s].billedHours + "</td>";
											else
												htmlVar += " <td align = 'center'> 0 </td>";
										}
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
			$('#ModuleReportTable').empty().append(htmlVar);
		}
	}
</script>

<html>
<body>

	<div class="spinner-wrap">
		<span class="spinner"></span>
	</div>


	<div class="content-wrapper">
		<div class="botMargin">
			<h1>Module Report</h1>
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
										<label>Project :<span class="astric">*</span></label> <select
											name="project.id" id="project" multiple="multiple"
											class="required">

											<c:forEach items="${projectList}" var="project">
												<option value="${project.id}">${project.projectName}</option>

											</c:forEach>

										</select>
									</div>
								</td>

								<td colspan="2"><label>Start Date :<span
										class="astric">*</span></label><span style="height: 131px"> <input
										type="text" name="weekStartDate" value="" id="weekStartDate"
										maxlength="80"  />

								</span></td>
								<td colspan="2"><label>End Date :<span
										class="astric">*</span>

								</label><span style="height: 131px"> <input type="text"
										name="weekEndDate" id="weekEndDate" maxlength="80" value=""
										 />
								</span></td>

							</tr>
							<tr>

								<td><label>Planned Hours : </label><span
									style="height: 131px"> <input type="checkbox"
										class="noWidth" id="plannedHours" name="plannedHours" value=""
										checked />
								</span></td>

								<td><label>Billed Hours : </label><span
									style="height: 131px"> <input type="checkbox"
										class="noWidth" id="billedHours" name="billedHours" value=""
										checked />
								</span></td>

								<td><label>Productive Hours : </label><span
									style="height: 131px"> <input type="checkbox"
										class="noWidth" id="productiveHours" name="productiveHours"
										value="" checked />
								</span></td>

								<td><label>Non-productive Hours : </label><span
									style="height: 131px"> <input type="checkbox"
										class="noWidth" id="nonProductiveHours"
										name="nonProductiveHours" value="" checked />
								</span></td>


							</tr>

							<tr>

								<td><a href="#" class="blue_link" id="submit"><i
										class="fa fa-file-excel-o"></i> View Report </a></td>

								<td><a href="exportToExcel" class="blue_link"
									id="exportToExcel" onclick="exportDataToExcel(this)"><i
										class="fa fa-file-excel-o"></i> Export To Excel </a></td>

                              <%-- Added for Prachi Rao , zoheb, mayank maheshwari: Module Report access only --%>
							  <c:if test="<%=UserUtil.getCurrentResource().getEmployeeId()==454 || UserUtil.getCurrentResource().getEmployeeId()==4334 || UserUtil.getCurrentResource().getEmployeeId()==627%>"> 
							   	<td><a href="#" class="blue_link" id="customReport">
										<i class="fa fa-file-excel-o"></i> Customized Module Report Data</a></td>
							  </c:if>

							</tr>

							<tr>
								<td colspan="2">&nbsp;</td>

								<td colspan="2"><font color="#FF0000">** NOTE: Please click to "View Report" button first to download the Excel **</font></td>
							</tr>

						</table>

					</form>
				</div>

				<div class="tbl"></div>
				<div class="clear"></div>
			</div>



		</div>
		<div class="datatable-scroll">


			<table id="ModuleReportTable" class="dataTable dtable"></table>

		</div>

		<div>

			<table id="NoAllocMessage"></table>

		</div>

	</div>
</body>
</html>

