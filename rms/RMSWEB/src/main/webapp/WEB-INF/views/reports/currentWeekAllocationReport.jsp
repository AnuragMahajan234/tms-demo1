<%@ page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

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

#CWAReportFormTable td {
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
</style>

<script>
	var recordTable;
	var oValue = new Array();
	var lValue = new Array();
	var pValue = new Array();
	var exportToExcelServerCallRequired = false;

	$(function() {
		$('#orgHierarchy, #locationId, #projectId,  #resource, #resourceOrgHierarchy, #Ownership').multiselect({
			includeSelectAllOption : true,
		}).multiselectfilter();

	});

	$(document).ready(function() {
		
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
				/* var oValue = $('#resourceOrgHierarchy').val();
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

		

		$('#locationId').multiselect({
			checkAll : function() {
				/* serverCallRequired = true;
				pids = $('#projectId').val();
				bids = $('#orgHierarchy').val();
				lids = $('#locationId').val();
				if (serverCallRequired && bids && pids && lids && $('#resource').empty()) {
						$.getJSON("/rms/UtilizationReport/getResource/" + bids+'/'+pids+'/'+lids, {}, showResource);
					} */
			},
			uncheckAll : function() {
				/* clearResource(); */
			},
			close : function(event) {

				/* pids = $('#projectId').val();
				bids = $('#orgHierarchy').val();
				lids = $('#locationId').val();
				if (serverCallRequired && bids && pids && lids && $('#resource').empty()) {
						$.getJSON("/rms/UtilizationReport/getResource/" + bids+'/'+pids+'/'+lids, {}, showResource);
					}
				serverCallRequired = false; */
			}
		});

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
					 
				},
				uncheckAll : function() {
					/* clearLocation();
					clearResource(); */
				},
				close : function(event) {
					/* oValue = $('#orgHierarchy').val();
					oValue1 = $('#projectId').val();
					if (serverCallRequired && oValue && $('#locationId').empty()) {
						$.getJSON("/rms/UtilizationReport/getLocation/" + oValue, {}, showLocation);
					}
					serverCallRequired = false; */
				}
			});
		}

		$('#resource').multiselect({
			checkAll : function() {
			},
			uncheckAll : function() {
			},			
		});
		
		/* $('#locationId > option:selected').each(function(i) {
			lValue[i] = $(this).val();
		}); */
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

		$('#orgHierarchy, #projectId ,#locationId, #resource, #startDatepicker, #endDatepicker').on('change', function(e) {
			//$("#exportToExcel").hide();
		});
		
		 /* $('#locationId').change(function() {
				//$("#exportToExcel").hide();
					serverCallRequired = true;
					pids = $('#projectId').val();
					bids = $('#orgHierarchy').val();
					lids = $('#locationId').val();
					if (serverCallRequired && bids && pids && lids && $('#resource').empty()) {
							$.getJSON("/rms/UtilizationReport/getResource/" + bids+'/'+pids+'/'+lids, {}, showResource);
						}			
				serverCallRequired = false;
			});  */
		 
		 
			/* $('#projectId').change(function() {
			serverCallRequired = true;
			oValue = $('#orgHierarchy').val();
			oValue1 = $('#projectId').val();

			if (serverCallRequired && oValue && $('#locationId').empty()) {
				$.getJSON("/rms/UtilizationReport/getLocation/" + oValue, {}, showLocation);
			}
			clearResource();
			serverCallRequired = false;
		}); */
	});
	
	
	

/* 	function onProjectChange() {
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
	} */

	function exportDataToExcel() {
		
			var requestParam = "?";
			var orgIdList = $('#orgHierarchy').val();
			var locIdList = $('#locationId').val();
			var projIdList = $('#projectId').val();
			var ownershipList = $('#Ownership').val();
			var resourceBUList = $('#resourceOrgHierarchy').val();
			
		    var errmsg = "Please select:";
			var validationFlag = true;
			 if (document.getElementById("endDatepicker").value == "" ) {
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
			if (document.getElementById("Ownership").value == "") {
				errmsg = errmsg + "<br> Ownership ";
				validationFlag = false;
			}
			if (document.getElementById("resourceOrgHierarchy").value == "") {
				errmsg = errmsg + "<br> resourceOrgHierarchy ";
				validationFlag = false;
			}
			if (!validationFlag) {
				stopProgress();
				if (errmsg.length > 0)
					showError(errmsg);
				return false;
			} else { 
			 
					
					requestParam = requestParam + "orgIdList=" + orgIdList + "&locIdList=" + locIdList + "&projIdList=" + projIdList + "&ownershipList=" + ownershipList + "&resourceBUList=" + resourceBUList;
					requestParam = requestParam + "&endDate=" + document.getElementById("endDatepicker").value;
					 
					var res = "getCurrentWeekAllocationReport";  
					$('#exportToExcel').attr("href", res + requestParam)
					$("#RMReportForm").submit();
					exportToExcelServerCallRequired = false; 
					stopProgress(); 
		}
	}	
			
	/* function clearLocation() {

		$("#locationId").multiselect("uncheckAll");
		$('#locationId').empty();
		$('#locationId').multiselect('refresh').multiselect();
	}

	function clearProject() {

		$("#projectId").multiselect("uncheckAll");
		$('#projectId').empty();
		$('#projectId').multiselect('refresh').multiselect();
	}
	
	function clearResource() {

		$("#resource").multiselect("uncheckAll");
		$('#resource').empty();
		$('#resource').multiselect('refresh').multiselect();
	} */
	
	
	


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
				<form id="CWAReportForm" name="CWAReportForm" action="CurrentWeekAllocationReportController">

					<table id="CWAReportFormTable" cellspacing="10" cellpadding="10">
						<tr>
								
								<td>Project BG-BU :</td>
								<td><div class="positionRel">
										<select name="orgHierarchy.id" id="orgHierarchy" multiple="multiple" class="dropdown-search">
											<c:forEach items="${orgHierarchy}" var="orgHierarchy">
												<option value="${orgHierarchy.id}">${orgHierarchy.parentId.name}-${orgHierarchy.name}</option>
											</c:forEach>
										</select>
									</div>
								</td>
								
								<td>Location :</td>
								<td>
									<div class="positionRel"> 
									<select name="locations.id" id="locationId" multiple="multiple" class="dropdown-search" >
											<c:forEach items="${locations}" var="locations">
												<option value="${locations.id}">${locations.location}</option>
											</c:forEach>
										</select>
									</div>
								</td>
								
							</tr>
							<tr>						
							<td>Project :</td>
								<td>
									<div class="positionRel">
										<select name="projects.id" id="projectId" multiple="multiple" class="dropdown-search" >
											<c:forEach items="${projects}" var="projects">
												<option value="${projects.id}">${projects.projectName}</option>
											</c:forEach>
										</select>
									</div>
								</td>	
								
								<td align="left">End Date :</td>
								<td align="left"><input type="text" id="endDatepicker" path="endDate" name="endDate"></td>
								</tr>
								
								<tr>
								<td>Ownership Name :</td>
								<td ><div class="positionRel">
										<select name="Ownership.id" id="Ownership" multiple="multiple">
										<c:forEach items="${ownerships}" var="Ownership">
											<option value="${Ownership.id}">${Ownership.ownershipName}</option>
										</c:forEach>

									</select>
									</div>
								</td>
								
								<td>Resource BU :</td>
								<td><div class="positionRel">
										<select name="resourceOrgHierarchy.id" id="resourceOrgHierarchy" multiple="multiple" class="dropdown-search">
											<c:forEach items="${resourceOrgHierarchy}" var="resourceOrgHierarchy">
												<option value="${resourceOrgHierarchy.id}">${resourceOrgHierarchy.parentId.name}-${resourceOrgHierarchy.name}</option>
											</c:forEach>
										</select>
									</div>
								</td>
								</tr>
								
								<tr>
								<td><a href="#" class="blue_link" id="exportToExcel" onclick="exportDataToExcel()">
								<i class="fa fa-file-excel-o" style="font-size: 15px"></i> Export To Excel </a></td>
							</tr>
							<tr>
								<td colspan="5">&nbsp;</td>
								<td colspan="3"><font color="#FF0000">** NOTE: Please click to "View Reports" button first to download the Excel **</font></td>
							</tr>
						</table>
					</form>
				</div>
				<div class="tbl"></div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
</body>
</html>