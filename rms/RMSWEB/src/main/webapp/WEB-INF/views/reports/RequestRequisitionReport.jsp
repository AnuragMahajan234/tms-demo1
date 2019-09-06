<%@ page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<spring:url value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}" 	var="ColReorder_js" />
<spring:url value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}" 	var="ColVis_js" />
<spring:url value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}" var="multiselect_js" />
<script src="${multiselect_js}" type="text/javascript"></script>

<spring:url value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}" 	var="multiselect_filter_js" />
<%-- <script src="${multiselect_filter_js}" type="text/javascript"></script> --%>
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}" var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
	
<spring:url value="/resources/images/spinner.gif" var="spinner" />

<script src="${multiselect_filter_js}" type="text/javascript"></script>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />

</head>
<body>
<style type="text/css" title="currentStyle">

.dataTableHader150 {
	width: 150px;
}

thead input {
	width: 100%
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

#defaultTableId thead th {
	width: 120px;
}

#defaultTableId td {
	word-wrap: break-word;
}

.dtable td {
	background: #ebebeb;
}

.rRReportFilter td {
	padding: 10px;
}

.activeReportTab {
	background: #84DFC1;
}

.datatable-scroll {
	max-width: 2000px;
	overflow-x: auto;
	position:relative;
	
}
div.dataTables_wrapper {
	width: 100%;
	margin: 0 auto;
	position: relative;
}
</style>

<script>

var flag = true;
var getGroupForCustomer =  function(){
		 var clientIds = [];
		 
		 $("#client option:selected").each(function(){
				var id = $(this).val();
				clientIds.push(id);
		 });
		 
		if(flag == true && clientIds.length>0){
			startProgress();
			flag = true;
			
			$.ajax({
				type: 'GET',
				dataType: 'json',
		        url: '/rms/customers/'+clientIds+'/groups/', 
		        cache: false,
		        success: function(response) { 
		        	stopProgress();
					var grpDrpDwn="";
					 $('#group').empty();
					 for(var i=0; i<response.length; i++){
							 var populateGrp = response[i];
			                grpDrpDwn = grpDrpDwn + "<option value='"+populateGrp.groupId+"'>"+populateGrp.groupName+"</option>";     
			            }
					 $("#group").append(grpDrpDwn);
					 $('#group').multiselect('refresh');
		     	},
		     	error: function(error){
		     		stopProgress();
		     		showError("Something happend wrong!!");
		     	}
			});
		}else{
			 flag = true;
			 $('#group').empty();	
			 $('#group').multiselect('refresh');
		}
		
	};

$(document).ready(function() {
	
	$('#exportToExcel').hide();
	$("#records_tableId").hide();
	$("#NoAllocMessage").hide();
	$(function() {
		$("#enddate").datepicker({
			maxDate : 0,
		});
		
		$("#startdate").datepicker({
			maxDate : 0,
		});
	});

	$('#startdate ,#enddate').on('change', function(e) {
		 if(document.getElementById("reportType").value=="W"){
				$('#submit').hide();
				$("#exportToExcel").show();
			}else{
		$("#exportToExcel").hide();
		$("#NoAllocMessage").hide();
			}
	});
	
	$("#client").on('change', function(){
		getGroupForCustomer();
		if($('#client').multiselect("widget").find("input:checked").length > 1 ){
			 $("#group").multiselect("disable"); 
		 }
		else{
			 $("#group").multiselect("enable");  
		 }
		 if(document.getElementById("reportType").value=="W"){
			 $("#group").multiselect("disable"); 
			 $("#status").multiselect("disable"); 
			 $("#hiringUnit").multiselect("disable"); 
			 $("#requestorUnit").multiselect("disable"); 
			$('#submit').hide();
			$("#exportToExcel").show();
			
		}
	})
	
	$("#group").on('change', function(){
		 if(document.getElementById("reportType").value=="W"){
			 $("#group").multiselect("disable"); 
			 $("#status").multiselect("disable"); 
			 $("#hiringUnit").multiselect("disable"); 
			 $("#requestorUnit").multiselect("disable"); 
			$('#submit').hide();
			$("#exportToExcel").show();
		}else{
			 $("#group").multiselect("enable");  
			 $("#status").multiselect("enable"); 
			 $("#hiringUnit").multiselect("enable"); 
			 $("#requestorUnit").multiselect("enable"); 
			$('#submit').show();
		 $("#exportToExcel").hide();
		}
	})
	
	$("#status").on('change', function(){
		 if(document.getElementById("reportType").value=="W"){
			 $("#group").multiselect("disable"); 
			 $("#status").multiselect("disable");
			 $("#hiringUnit").multiselect("disable"); 
			 $("#requestorUnit").multiselect("disable"); 
			$('#submit').hide();
			$("#exportToExcel").show();
		}else{
			 $("#group").multiselect("enable");  
			 $("#status").multiselect("enable"); 
			 $("#hiringUnit").multiselect("enable"); 
			 $("#requestorUnit").multiselect("enable"); 
			$('#submit').show();
		 $("#exportToExcel").hide();
		}
	})
	
	$('#status')
	.multiselect({
		checkAll : function() {
			 if(document.getElementById("reportType").value != "W"){
				 $("#exportToExcel").hide();
			 }
		},
		uncheckAll : function() {
			 if(document.getElementById("reportType").value != "W"){
			 	$("#exportToExcel").hide();
			 }
	}});
	
	$("#reportType").on('change', function(){		
		 if(document.getElementById("reportType").value=="W"){
			 $("#group").multiselect("disable"); 
			 $("#status").multiselect("disable"); 
			 $("#hiringUnit").multiselect("disable"); 
			 $("#requestorUnit").multiselect("disable"); 
			$('#submit').hide();
			$("#exportToExcel").show();
			 $("#startdate").removeAttr("disabled"); 
			 $("#enddate").removeAttr("disabled"); 
		}else{
			 $("#group").multiselect("enable");  
			 $("#status").multiselect("enable"); 
			 $("#hiringUnit").multiselect("enable"); 
			 $("#requestorUnit").multiselect("enable"); 
			 $('#submit').show();
			 $("#exportToExcel").hide();
			 $("#startdate").val(""); 
			 $("#enddate").val("");
			 $("#startdate").attr("disabled", "disabled"); 
	         $("#enddate").attr("disabled", "disabled"); 
		}
	})
	$('#client')
	.multiselect({
		checkAll : function() {
			//$('#exportToExcel').hide();
 			$("#group").multiselect("disable"); 
			getGroupForCustomer();
		},
		uncheckAll : function() {
			flag= false;
			$('#exportToExcel').hide();
			 $("#group").multiselect("enable");
			getGroupForCustomer();
	}});
	
	$('#requestorUnit')
	.multiselect({
		checkAll : function() {
			 if(document.getElementById("reportType").value != "W"){
				 $("#exportToExcel").hide();
			 }
		},
		uncheckAll : function() {
			 if(document.getElementById("reportType").value != "W"){
			 	$("#exportToExcel").hide();
			 }
	}});
	
	$('#hiringUnit')
	.multiselect({
		checkAll : function() {
			 if(document.getElementById("reportType").value != "W"){
				 $("#exportToExcel").hide();
			 }
		},
		uncheckAll : function() {
			 if(document.getElementById("reportType").value != "W"){
			 	$("#exportToExcel").hide();
			 }
	}});
	
	$("#hiringUnit").on('change', function(){
		$('#submit').show();
		 	$("#exportToExcel").hide();
	})
	
	
	$("#requestorUnit").on('change', function() {
		$('#submit').show();
			$("#exportToExcel").hide();
     })

						$('#records_tableId')
								.DataTable(
										{
											"sDom" : 'RC<"clear"><"top"lfp>rt<"bottom"ip<"clear">',
											"aaSorting" : [],
											"sPaginationType" : "full_numbers",
											"oLanguage" : {
												"sSearch" : "Search all columns:"
											},
											"bDestroy" : true,
											"bRetrieve" : true,
										});

						$("#requestRequisitionReportTable").hide();

					});

	$(document)
			.on(
					'click',
					'#exportToExcel',
					function(e) {

						var errmsg = "Please select:";
						var validationFlag = true;
						var customerIds = "";
						var groupIds = "";
						var status = "";

						var customerIds = [];
						var groupIds = [];
						var statusIds = [];

						if (document.getElementById("reportType").value == "W") {

							if (document.getElementById("reportType").value == "-1") {
								errmsg = errmsg + "<br> Report Type ";
								validationFlag = false;

							}

							$("#client option:selected").each(function() {
								var id = $(this).val();
								customerIds.push(id);
							});

							if (customerIds.length <= 0) {
								errmsg = errmsg + " Client  ";
								validationFlag = false;
							}

							if (document.getElementById("startdate").value != ""
									&& document.getElementById("startdate").value != "") {

								startDate = document
										.getElementById("startdate").value;
								endDate = document.getElementById("enddate").value;

								var startDt = new Date(startDate);
								var endDt = new Date(endDate);
								if (Date.parse(startDt) >= Date.parse(endDate)) {
									errmsg = errmsg
											+ " Correct End Date"
											+ "<br>End Date should be greater that Start Date ";
									validationFlag = false;
								}
								if (!validationFlag) {
									if (Date.parse(startDt) === Date
											.parse(endDate)) {
										validationFlag = true;
										errmsg = "";
									}
								}
							}
							if (document.getElementById("startdate").value == "") {
								errmsg = errmsg + "Start Date ";
								validationFlag = false;

							}
							if (document.getElementById("enddate").value == "") {
								errmsg = errmsg + "End Date ";
								validationFlag = false;

							}
						}
						if (!validationFlag) {
							stopProgress();
							if (errmsg.length > 0)
								showError(errmsg);
							e.preventDefault();
							return;
						} else {

							var startDate = document
									.getElementById("startdate").value;
							var endDate = document.getElementById("enddate").value;
							var customerIds = [];
							var groupIds = [];
							var statusIds = [];
							var reportType = [];

							$("#client option:selected").each(function() {
								var id = $(this).val();
								customerIds.push(id);
							});

							$("#group option:selected").each(function() {
								var id = $(this).val();
								groupIds.push(id);
							});

							$("#status option:selected").each(function() {
								var id = $(this).val();
								statusIds.push(id);
							});
							$("#reportType option:selected").each(function() {
								var id = $(this).val();
								reportType.push(id);
							});
							var requestParam = "?";
							requestParam = requestParam + "&startDate="
									+ startDate + "&endDate=" + endDate
									+ "&customerIds=" + customerIds
									+ "&groupIds=" + groupIds + "&statusIds="
									+ statusIds + "&reportType=" + reportType;

							var res = "requestRequisitionExcelReport";

							$('#exportToExcel')
									.attr("href", res + requestParam)
							$("#CustomizedReport").submit();
						}
					});

	$(function() {
		$('#client').multiselect({
			includeSelectAllOption : true
		}).multiselectfilter();
	});

	$(function() {
		$('#group').multiselect({
			includeSelectAllOption : true
		}).multiselectfilter();
	});

	$(function() {
		$('#status').multiselect({
			includeSelectAllOption : true
		}).multiselectfilter();
	});

	$(function() {
		$('#hiringUnit').multiselect({
			includeSelectAllOption : true
		}).multiselectfilter();
	});
	$(function() {
		$('#requestorUnit').multiselect({
			includeSelectAllOption : true
		}).multiselectfilter();
	});

	$(document)
			.on(
					'click',
					'#submit',
					function() {

						var startDate = "";
						var endDate = "";
						var errmsg = "Please select:";
						var validationFlag = true;
						var customerIds = "";
						var groupIds = "";
						var status = "";

						var customerIds = [];
						var groupIds = [];
						var statusIds = [];
						var hiringUnits = [];
						var reqUnits = [];

						if (document.getElementById("reportType").value == "-1") {
							errmsg = errmsg + "<br> Report Type ";
							validationFlag = false;

						}

						$("#client option:selected").each(function() {
							var id = $(this).val();
							customerIds.push(id);
						});

						if (customerIds.length <= 0) {
							errmsg = errmsg + " Client";
							validationFlag = false;
						}

						$("#status option:selected").each(function() {
							var id = $(this).val();
							statusIds.push(id);
						});

						if (statusIds.length <= 0) {
							errmsg = errmsg + " Status";
							validationFlag = false;
						}

						$("#hiringUnit option:selected").each(function() {
							var id = $(this).val();
							hiringUnits.push(id);
						});

						$("#requestorUnit option:selected").each(function() {
							var id = $(this).val();
							reqUnits.push(id);
						});

						if (!validationFlag) {
							stopProgress();
							if (errmsg.length > 0)
								showError(errmsg);
							return;
						} else {

							$("#exportToExcel").show();

							$("#group option:selected").each(function() {
								var id = $(this).val();
								groupIds.push(id);
							});

							$
									.ajax({
										type : 'GET',
										url : '/rms/requestRequisitionReport/getRequestRequisitionReport',
										contentType : "text/html; charset=utf-8",
										async : false,
										data : "&customerIds=" + customerIds
												+ "&groupIds=" + groupIds
												+ "&statusIds=" + statusIds
												+ "&hiringUnits=" + hiringUnits
												+ "&reqUnits=" + reqUnits,

										success : function(data) {

											if (data.length != 0) {

												$('#records_tableId').show();
												$("#NoAllocMessage").hide();
												$(
														"#requestRequisitionReportTable")
														.show();
												$('#records_tableId')
														.dataTable()
														.fnClearTable();

												var i = 1;
												$
														.each(
																data,
																function(key,
																		val) {
																	var rmgPoc="NA";
																	var tacPoc="NA";
																	var requirementArea="NON_SAP";
																	if(val.rmgPOC!=null && val.rmgPOC!="" ){
																		rmgPoc=val.rmgPOC;
																	}
																	if(val.tacTeamPOC!=null && val.tacTeamPOC!="" ){
																		tacPoc=val.tacTeamPOC;
																	}
																	if(val.requirementArea!=null){
																		requirementArea=val.requirementArea;
																	}

																	val['SrNo'] = ''
																			+ i;
																	i++;
																	$(
																			'#records_tableId')
																			.show();
																	$(
																			'#records_tableId')
																			.dataTable()
																			.fnAddData(
																					[
																							val.SrNo,
																							val.clientName,
																							val.requirementId,
																							val.reportStatus,
																							val.allocationType,
																							val.locationName,
																							val.noOfResources,
																							val.open,
																							val.shortlisted,
																							val.closed,
																							val.notFitForRequirement,
																							val.hold,
																							val.lost,
																							val.submissions,
																							val.skill,
																							val.designationName,
																							val.requestedBy,
																							val.businessGroupName,
																							val.hiringUnit,
																							val.reqUnit,
																							rmgPoc,
																							tacPoc,
																							requirementArea,
																							val.amJobCode,
																							val.custGroup 
																							]);
																	
																});
											}
										},
										error : function(errorResponse) {

											//showError("Records Not Found");
											$('#records_tableId').hide();
											var htmlVarObj = '<div id="NoAllocMessage" class="NoAllocMessage">No Record Exist for selected filters.</div>';

											$('#NoAllocMessage').empty()
													.append(htmlVarObj);
											$("#exportToExcel").hide();
											$("#NoAllocMessage").show();
											$("#requestRequisitionReportTable")
													.hide();

										}
									});
						}
					});
</script>

	<div class="content-wrapper">
		<div class="botMargin">
			<h1>Resource Requisition Report</h1>
		</div>
		<div class="tab_seaction">
			<ul class='tabs'>
				<li><a href='#tab1' id="tab1id">List</a></li>

			</ul>
			<div id='tab1' class="tab_div">
				<div class="form">
					<form name="defaultFormName" method="post" id="defaultForm">
					 	<!-- <div class="ui-widget-header ui-corner-all ui-multiselect-header ui-helper-clearfix ui-multiselect-hasfilter"> -->
						<div  class="tblBorderDiv">
							<table id="mail" cellpadding="5" cellspacing="5" class="display dataTable" style="border-collapse:collapse;width:100%; margin-top:0px;">
							<tr>
								<input type="hidden" id="formHiddenId" name="id"></input>
							</tr>
							<tr>
								<td align="right" width="15%"><strong>Report Type : </strong></td>
									<td align="left" width="15%">
										 <div  class="positionRel">
											<select  id="reportType" >
											<option value="-1">Select Type</option>
												<option value='W'>Weekly Report</option>
												<option value='D'>As On Date Report</option>
												<option value='R'>RRF Submission Report</option>
											</select>
										 </div> 
				 					</td> 							
								<td align="right" width="15%"><strong>Client <span style="color: red;">*</span> : </strong></td>
								<td align="left" width="15%">
								 <div  class="positionRel">
									<select id="client" class="required" multiple="multiple">
										<!-- 1003958 START[] --> 
										<c:forEach var="customer" items="${customerList}">
												<option value="${customer.id}">${customer.customerName}</option>										
										</c:forEach>
										<!-- 1003958 ENDS[] -->  
									</select> 
			 					</div>
			 					</td>
			 					
			 	 				<td align="right" width="15%"><strong>Group : </strong></td>
								<td align="left" width="15%">
									 <div  class="positionRel">
										<select id="group" class="required" multiple="multiple">
											<option value='Select'>Select</option>
										</select>
									 </div> 
			 					</td> 
			 					
			 					<td align="right" width="15%"><strong>Status<span style="color: red;">*</span> : </strong></td>
								<td align="left" width="15%">
								<!--  	 <div  class="positionRel">
										<select  id="status" class="required" multiple="multiple">
											<option value='A'>Active</option>
											<option value='I'>InActive</option>
										</select>
									 </div> -->
									 <div  class="positionRel">
										<select  id="status" class="required" multiple="multiple">
											<option value="'OPEN'">Open</option>
											<option value="'CLOSED'">Closed</option>
											<option value="'HOLD'">Hold</option>
											<option value="'LOST'">Lost</option>
											<option value="'FULLFILLED'">Fullfilled</option>
										</select>
									 </div>
			 					</td> 
			 					
					 		</tr>
					 		<tr>
					 				<td align="right"  width="15%"><strong>Hiring Unit : </strong></td>
					 				
					 				
								<td align="left" width="15%">
								<%-- <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')"> --%>
									<div  class="positionRel">
										<select id="hiringUnit" class="required" multiple="multiple">
											<c:forEach var="unit" items="${hiringUnit}">
												<option value="${unit.parentId.name}-${unit.name}">${unit.parentId.name}-${unit.name}</option>										
											</c:forEach>
										</select> 
			 						</div>
								<%-- </sec:authorize> --%>
								</td>							
									
									<td align="right" width="15%"><strong>Requesting Unit : </strong></td>
								<td align="left" width="15%">
								<%-- <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')"> --%>
									<div  class="positionRel">
										<select id="requestorUnit" class="required" multiple="multiple">
											<c:forEach var="unit" items="${requestorUnit}">
												<option value="${unit.parentId.name}-${unit.name}">${unit.parentId.name}-${unit.name}</option>										
											</c:forEach>
										</select> 
			 						</div>
								<%-- </sec:authorize> --%>
								</td>									
									
					 		
					 		
					 			<td align="right" width="15%"><strong>Start Date : </strong></td>
								<td align="left" width="15%">
									<div class="stDtEnDt tsstDtEnDtWrapper">
										<span style="height: 131px"> <input type="text"
											name="startdate" value="" id="startdate" size="35"
											maxlength="80" readonly="readonly" /></span>
									 </div> 
			 					</td> 
			 					<td align="right" width="15%"><strong>End Date : </strong></td>
								<td align="left" width="15%">
									<div class="stDtEnDt tsstDtEnDtWrapper">
										<span style="height: 131px"> <input type="text"
											name="enddate" id="enddate" size="35" maxlength="80"
											value="" readonly="readonly" /></span>
									 </div> 
			 					</td> 
			 				</tr>
			 				<tr>
			 				<td colspan="6"></td>	
			 				<td align="left" width="15%">
									<a href="#" class="blue_link" id="submit"><span
										class="glyphicon glyphicon-list-alt"></span> View Report </a>
									&nbsp;&nbsp;	
			 					</td>
			 					<td align="left" width="15%">
									<a href="exportToExcel" class="blue_link"
									id="exportToExcel"><i class="fa fa-file-excel-o"
										style="font-size: 15px"></i> Export To Excel </a>
			 					</td> 
					 		</tr>	
						 </table>
						</div>
					</form>
				</div>
				<div class="tbl"></div>
				<div class="clear"></div>
			</div>
		</div>

		<div style="max-width: 1090px; overflow: auto;"
			id="requestRequisitionReportTable">
			<table
				class="dataTbl display tablesorter dataTable dtable records-data-table"
				id="records_tableId" style="overflow:auto;display:inline-block">
				<thead>
					<tr>
						<th align="center" valign="middle">S.NO</th>
						<th align="center" valign="middle">Client</th>
						<th align="center" valign="middle" style="width: 200px;">RRF ID</th>
						<th align="center" valign="middle">Status</th>
						<th align="center" valign="middle">Allocation Type</th>
						<th align="center" valign="middle">Work Location</th>
						<th align="center" valign="middle">Total Positions</th>
						<th align="center" valign="middle">Open</th>
						<th align="center" valign="middle" style="width: 150px;">ShortListed</th>
						<th align="center" valign="middle">Closed</th>
						<th align="center" valign="middle" style="width: 150px;">Not Fit For Requirement</th>
						<th align="center" valign="middle">On Hold</th>
						<th align="center" valign="middle">Lost</th>
						<th align="center" valign="middle" style="width: 150px;">Submissions</th>
						<th align="center" valign="middle">Required Skill</th>
						<th align="center" valign="middle" style="width: 150px;">Designation</th>
						<th align="center" valign="middle">Requested By</th>
						<th align="center" valign="middle" style="width: 150px;">Requesting Practice</th>
						<th align="center" valign="middle">Hiring Unit</th>
						<th align="center" valign="middle">Requestor Unit</th>
						<!-- Addition of 2 columns RMG POC and TAC Team POC - Start -->
						<th align="center" valign="middle">RMG POC</th>
						<th align="center" valign="middle">TAC Team POC</th>
						<!-- Addition of 2 columns RMG POC and TAC Team POC - End -->
						<th align="center" valign="middle">Requirement Area</th>
						<th align="center" valign="middle">AM Job Code</th>
						<th align="center" valign="middle">Customer Group </th>
						
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

