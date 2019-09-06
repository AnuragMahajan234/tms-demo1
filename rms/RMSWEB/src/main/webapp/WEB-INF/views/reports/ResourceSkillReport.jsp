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

#ResourceSkillReportTable td {
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
			$('#orgHierarchy,  #AllocationType').multiselect({
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
			//clearAllocationType();


		});
		
		
		$('#orgHierarchy').multiselect({
			checkAll : function() {

				serverCallRequired = true;
			//	clearAllocationType();
				
			},
			uncheckAll : function() {

			//	clearAllocationType();
		
			}
		});

		
		$('#orgHierarchy, #AllocationType').on('change', function(e) {

				$("#exportToExcel").hide();
				
				});


	})

	

	

	$(document).on('click','#submit',function() {

						var orgIdList = $('#orgHierarchy').val();
						var allocationTypeIds = $('#AllocationType').val();
					
						var errmsg = "Please select:";
						var validationFlag = true;

						if (orgIdList.length == 0) {
							errmsg = errmsg + "<br> BG-BU ";
							validationFlag = false;
						}
						
					
						if (!validationFlag) {

							stopProgress();
							if (errmsg.length > 0)

								showError(errmsg);

							return;
						} else {

							$("#exportToExcel").show();

							$.ajax({

										url : '/rms/resourceSkillReport/getResourceSkillReport',
										contentType : "text/html; charset=utf-8",
										async : false,
										data : "orgIdList=" + orgIdList+"&allocationTypeIds="+allocationTypeIds,

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
																							val.competency,
																							val.primarySkill,
																							val.secondarySkill,
																							val.grade,
																							val.currentLocation,
																							val.primaryProject,
																							val.allocationType,
																							val.emailId,
																							val.designation,
																							val.parentBu,
																							val.currentBu
																							]);
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

	 function clearAllocationType() {

		$("#AllocationType").multiselect("uncheckAll");
		$('#AllocationType').empty();
		$('#AllocationType').multiselect('refresh').multiselect();
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
			<h1>Resource Skill Detail</h1>
		</div>
		<div class="tab_seaction">
			<ul class='tabs'>
				<li><a href='#tab1' id="tab1id">List</a></li>

			</ul>
			<div id='tab1' class="tab_div">


				<div class="form">

					<form id="ResourceSkillReport" name="ResourceSkillReport" action="ResourceSkillReportController">
						<table id="ResourceSkillReportTable" cellspacing="10" cellpadding="10">

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
									
									 <td align="left">Allocation Type :</td>

								<td align="left">
								<%-- <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_HR')"> --%>

										<%-- <form id="evalForm" method="post" class='form-inline'> --%>
										<div class="positionRel">
											<select name="AllocationType.id" id="AllocationType"
												multiple="multiple">
												<!-- <option value="-1"></option> -->
												<c:forEach items="${AllocationType}" var="AllocationType">
													<option value="${AllocationType.id}">${AllocationType.allocationType}-${AllocationType.id}</option>
												</c:forEach>
											</select>
										</div>
									<%-- </sec:authorize> --%>
									</td>
									 
									 	<td colspan="8">&nbsp;</td>
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
						<th align="center" valign="middle">Competency</th>
						<th align="center" valign="middle">Primary Skill</th>
						<th align="center" valign="middle">Secondary Skill</th>
						<th align="center" valign="middle">Grade</th>
						<th align="center" valign="middle">Current Location</th>
						<th align="center" valign="middle">Primary_Project</th>
						<th align="center" valign="middle">Allocation Type</th>
						<th align="center" valign="middle">E-Mail</th>
						<th align="center" valign="middle">Designation</th>
						<th align="center" valign="middle">Parent BG-BU</th>
						<th align="center" valign="middle">Current BG-BU</th>
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

