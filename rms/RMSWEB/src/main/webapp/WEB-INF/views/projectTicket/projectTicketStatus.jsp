<%@ page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<spring:message code="application_js_version" var="app_js_ver" htmlEscape="false" />
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}" var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<spring:url value="/resources/images/favicon.ico" var="favicon" />
<spring:url value="/resources/js-framework/jquery-1.7.2.min.js?ver=${app_js_ver}" var="jquery_url" />
<spring:url value="/resources/js-framework/jquery-ui/jquery-ui-1.8.22.custom.min.js?ver=${app_js_ver}" var="jquery_ui_1_8_21_custom_min_js" />
<spring:url value="/resources/js-framework/jquery-ui/combobox.js?ver=${app_js_ver}" var="combobox_js" />
<spring:url value="/resources/js-framework/form2js/form2js.js?ver=${app_js_ver}" var="form2js_js" />
<spring:url value="/resources/js-framework/form2js/jquery.toObject.js?ver=${app_js_ver}" var="jquery_toObject_js" />
<spring:url value="/resources/js-framework/form2js/js2form.js?ver=${app_js_ver}" var="js2form_js" />
<spring:url value="/resources/js-framework/form2js/json2.js?ver=${app_js_ver}" var="json2_js" />
<spring:url value="/resources/js-framework/validate/jquery.validate.min.js?ver=${app_js_ver}" var="jquery_validate_min_js" />
<spring:url value="/resources/js-framework/validate/jquery.validVal-4.0.3-packed.js?ver=${app_js_ver}" var="jquery_validVal" />
<spring:url value="/resources/js-framework/validate/additional-methods.min.js?ver=${app_js_ver}" var="additional_methods_min_js" />
<spring:url value="/resources/js-framework/jsrender/jsrender.js?ver=${app_js_ver}" var="jsrender_js" />
<spring:url value="/resources/js-framework/jquery.autogrowtextarea.js?ver=${app_js_ver}" var="jquery_autogrowtextarea_js" />
<spring:url value="/resources/js-user/rmsCommon.js?ver=${app_js_ver}" var="rmsCommon_js" />
<spring:url value="/resources/js-user/validations.js?ver=${app_js_ver}" var="validations_js" />
<spring:url value="/resources/js-framework/fancy-box/jquery.fancybox.pack.js?ver=${app_js_ver}" var="jquery_fancybox" />
<spring:url value="/resources/js-framework/toastr/toastr.js?ver=${app_js_ver}" var="toastr_js" />
<spring:url value="/resources/js-framework/blockUI/jquery.blockUI.js?ver=${app_js_ver}" var="blockUI" />
<spring:url value="/resources/js-framework/noty/jquery.noty.js?ver=${app_js_ver}" var="noty" />
<spring:url value="/resources/js-framework/noty/layouts/center.js?ver=${app_js_ver}" var="noty_center" />
<spring:url value="/resources/js-framework/noty/themes/default.js?ver=${app_js_ver}" var="noty_default" />
<!-- Get the user local from the page context (it was set by Spring MVC's locale resolver) -->
<c:set var="userLocale">
	<c:set var="plocale">${pageContext.response.locale}</c:set>
	<c:out default="en" value="${fn:replace(plocale, '_', '-')}" />
</c:set>
<!-- required for FF3 and Opera Below Things -->
<script src="${jquery_url}" type="text/javascript"></script>
<script src="${jquery_ui_1_8_21_custom_min_js}" type="text/javascript"></script>
<script src="${combobox_js}" type="text/javascript"></script>
<script src="${json2_js}" type="text/javascript"></script>
<script src="${form2js_js}" type="text/javascript"></script>
<script src="${jquery_toObject_js}" type="text/javascript"></script>
<script src="${js2form_js}" type="text/javascript"></script>
<script src="${jquery_validate_min_js}" type="text/javascript"></script>
<script src="${additional_methods_min_js}" type="text/javascript"></script>
<script src="${jquery_validVal}" type="text/javascript"></script>
<script src="${jsrender_js}" type="text/javascript"></script>
<script src="${jquery_autogrowtextarea_js}" type="text/javascript"></script>
<!-- required for common functions -->
<script src="${rmsCommon_js}" type="text/javascript"></script>
<!-- required for common validations -->
<script src="${validations_js}" type="text/javascript"></script>
<!-- required for FF3 and Opera Below Things -->
<script src="${noty}" type="text/javascript"></script>
<script src="${noty_default}" type="text/javascript"></script>
<script src="${noty_center}" type="text/javascript"></script>

<spring:url value="/resources/js-framework/datatables/jquery.dataTables.min.js?ver=${app_js_ver}" var="jquery_dataTables_min_js" />
<spring:url value="/resources/js-framework/populate/jquery.populate.pack.js?ver=${app_js_ver}" var="jquery_populate_pack_js" />
<!-- DataTables & required for FF3 and Opera -->
<script src="${jquery_dataTables_min_js}" type="text/javascript"></script>
<!-- Populate -->
<script src="${jquery_populate_pack_js}" type="text/javascript"></script>
<script src="${jquery_fancybox}" type="text/javascript"></script>
<script src="${toastr_js}" type="text/javascript"></script>
<script src="${blockUI}" type="text/javascript"></script>

<script type="text/javascript" charset="utf-8">
var global = null;

function startProgress() {
	$.blockUI({	message : '</br></br><div class="circle"></div><div class="circle1"></div><center>Just a moment</center>'});
}

function stopProgress() {
	$.unblockUI();
}

jQuery.extend({
	deleteJson_ : function(url, data, callback, type) {
		return _ajax_request(url, data, callback, type, 'DELETE');
	}
});

function _ajax_request(url, data, callback, type, method) {
	if (jQuery.isFunction(data)) {
		callback = data;
		data = {};
	}
}

var oldActivity = "";

function getInputValue(str) {
	var index = str.indexOf("<input");
	if (index < 0)
		return null;
	str = str.substr(index, str.length);
	return $(str).val();
}

function valideDuplicates(tableId, screenId, columnId) {
	var flag = true;
	var inpSelect = $.trim(($('#' + tableId + ' >tbody >tr').find("td:nth-child(" + columnId + ") input")).val());
	if (inpSelect == oldActivity) {
		flag = true;
	} else {
		<c:forEach var="activity" varStatus="" items="${activitys}">
		var activity = "${activity.status}";
		if (activity.toUpperCase() == inpSelect.toUpperCase()) {
			flag = false;
		}
		</c:forEach>
	}
	if (flag == false) {
		var errorMsg = screenId + " \"" +inpSelect+ "\" is already present. Please provide different "+ screenId;
		showError(errorMsg);
	}
		return flag;
}

function showError(msg) {
	toastr.options.timeOut = 12000;
	toastr.error(msg, "Error");
}

function reloadFunction(){
	setTimeout(function(){
		window.location.reload(1);
	}, 2000);	
}

$(document).ready(
	function() {
		$("#example_filter").find('input').val(null);
		var oTable;
		/* Add the events etc before DataTables hides a column */
		$("thead input").keyup(
			function() {
			/* Filter on the column (the index) of this element */
			oTable.fnFilter(
				this.value,
				oTable.oApi._fnVisibleToColumnIndex(oTable.fnSettings(),$("thead input").index(this)));
			});
			/*Support functions to provide a little bit of 'user friendlyness' to the textboxes*/
			$("thead input").each(function(i) {
				this.initVal = this.value;
			});
			$("thead input").focus(function() {
				if (this.className == "search_init") {
					this.className = "";
					this.value = "";
				}
			});
			$("thead input").blur(function(i) {
				if (this.value == "") {
					this.className = "search_init";
					this.value = this.initVal;
				}
			});
			oTable = $('#example').dataTable({
				"sDom" : 'RC<"clear">lfrtip',
				"bStateSave" : true,
				"aoColumnDefs" : [{
					"bVisible" : false,
					"aTargets" : []
				}],
				"oLanguage" : {
					"sSearch" : "Search:"
				},
				"aoColumns" : [{}, {},  {"bSortable" : false}, {"bSortable" : false}],
				"bSortCellsTop" : true,
			});
			// 				var nEditing = null;
			// 				var saveOpen = false;
			$("#example_filter").find('input').bind('change paste keyup',function() {
											var srch = $("#example_filter").find('input').val();
											if (null == srch || $.trim(srch) == '') {
												$("#add").show();
											} else {
												$("#add").hide();
											}
			});
			$('#addNew').click(function(e) {
				if (saveOpen) {
					var text = "Please enter and save the data";
					showAlert(text);
					e.preventDefault();
					return;
				}
				e.preventDefault();
				var aiNew = oTable.fnAddData(['','','','<a class="delete" href="">Delete</a>' ]);
			
				var nRow = oTable.fnGetNodes(aiNew[0]);
				global = nRow;
				//alert(nRow);//
				editRow(oTable, nRow);
				nEditing = nRow;
				saveOpen = true;
			//	$("table.dataTbl tbody tr").find("td:nth-child(1) input").removeAttr("readonly");
			});

			$(document).on('click','#example a.cancel',
					function(e) {
						$("#example tbody tr").find("td").attr("align", "center");
						saveOpen = false;
						e.preventDefault();
						var nRow = $(this).parents('tr')[0];
						nEditing = nRow;
						restoreRow(oTable, nEditing);
					});

			function cancelDataRow(id, empId, projId) {
				if (id == undefined || id == null || id == '') {
					oTable.fnDeleteRow(global);
					$("table#example tbody").find("tr:first").find("input[type=text]").blur();
					saveOpen = false;
					nEditing = null;
					$('.toasterBgDiv').remove();
				return;
				}
				$('.toasterBgDiv').remove();
				}
				$(document).on('click', '.delAddedRow', function() {
					cancelDataRow(null, null, null);
				});
				$('#example a.delete').live('click',
					function(e) {
						e.preventDefault();
						var nRow = $(this).parents('tr')[0];
						var aData = oTable.fnGetData(nRow);
						var id = $(nRow).attr('id');
						if (saveOpen && !(id == undefined || id == null || id == '')) {
							var text = "Please enter and save the data";
							showAlert(text);
							e.preventDefault();
							return;
						}
						if (id == undefined || id == null || id == '') {
							   var msg = "Project Ticket Status has been Successfully deleted";
						       cancelDataRow(null, null, null);
						       toastr.options.timeOut = 1200;
						       toastr.success(msg, "Success");
						} else {
							jQuery.ajax({
								type : 'DELETE',
								url : "/rms/projectStatus/deleteStatus/"+id,
								data : data,
								contentType : "application/json; charset=utf-8",
								success : function(data) {
									stopProgress();
						            var successMsg1 = "Project Ticket Status  have been deleted successfully";
						           showSuccess(successMsg1);
						           reloadFunction();
								},
								error : function(data) {
									stopProgress();
								}
							});
						}

				});

				$('#example a.edit').live('click',
					function(e) {
						if (saveOpen && this.innerHTML != "Save") {
							var text = "Please enter and save the data";
							showAlert(text);
							e.preventDefault();
						return;
						}
						saveOpen = true;
						e.preventDefault();
						/* Get the row as a parent of the link that was clicked on */
						var nRow = $(this).parents('tr')[0];
						if (nEditing !== null && nEditing != nRow) {
						/* Currently editing - but not this row - restore the old before continuing to edit mode */
							restoreRow(oTable, nEditing);
							editRow(oTable, nRow);
							nEditing = nRow;
						} else if (nEditing == nRow && this.innerHTML == "Save") {
						/* Editing this row and want to save it */
						// Provide the required parameters in the below functions to validate the inputs
							if (!validateInputs('.tbl') || !valideDuplicates('example', 'Status', 2)) {
								return;
							}
							saveRow(oTable, nEditing);
							saveOpen = false;
						} else {
							/* No edit in progress - let's start one */
							editRow(oTable, nRow);
							nEditing = nRow;
						}
						});

						function showSuccess(msg) {
							toastr.options.timeOut = 120000;
							toastr.success(msg, "Success");
						}
						
						function editRow(oTable, nRow) {
							startProgress();
							var aData = oTable.fnGetData(nRow);
							
							var jqTds = $(">td", nRow);
							if (jqTds.length < 1)
								return;
							oldActivity = aData[1];
							for (var i = 1; i < aData.length - 2; i++) {
								jqTds[i].innerHTML = '<input type="text" value="'+aData[i]+'">';
							}
							jqTds[0].innerHTML = '<input type="text" value="'
									+ aData[0]
									+ '" readonly="readonly" onfocus="this.blur()">';
							/* jqTds[2].innerHTML = '<select id ="mandatory'
									+ getInputValue(aData[2]) + '">'
									+ '<option value="True">Yes</option>'
									+ '<option value="False">No</option>'
									+ '</select>'; */
							jqTds[2].innerHTML = '<a class="edit" href="">Save</a>/<a class="cancel" href="#">Cancel</a>';
							//Start Added to solve default values in drop down
							var value = getInputValue(aData[2]);
							/* if (aData[2].toLowerCase() == 'yes') {
								$("#mandatory" + value).val("True");
							} else {
								$("#mandatory" + value).val("False");
							} */
							$("#example tbody tr:first").find("td").find("a.cancel").addClass("delAddedRow");
							stopProgress();
						}

						function spclCharacterValidation(name) {
							if (/^[a-zA-Z0-9- ]*$/.test(name) == false) {
								return true;
								stopProgress();
							}
						}
						
						function saveRow(oTable, nRow) {
							var projectId = "${projectId}";
							startProgress();
							var jqInputs = $('input', nRow);
							var jqSelects = $('select', nRow);
							var aData = jqInputs;
							var bData = jqSelects;
							var maxtype = "0";
							for (var i = 0; i < jqInputs.length; i++) {
								aData[i] = jqInputs[i].value;
							}

							var stringName = aData[1];
							if (stringName == "") {
								showError("Please Enter Status name");
								stopProgress();
								return false;
							} else if (spclCharacterValidation(stringName)) {
								showError("Please Enter valid Status name");
								stopProgress();
								return false;

							}

							var rowDatabaseId = $(nRow).attr('id');
							//bData[0] = jqSelects[0].value;
							var sData = $('*', oTable.fnGetNodes()).serializeArray();
							if (rowDatabaseId != null && rowDatabaseId != '')
								sData.push({
									name : "id",
									value : rowDatabaseId
								});
							aData[1] = capitaliseFirstLetter(aData[1]);
							sData.push({
								name : "projectId.id",
								value : projectId
							});
							sData.push({
								name : "status",
								value : aData[1]
							});
							/* sData.push({
								name : "mandatory",
								value : bData[0]
							}); */

							sData.push({
								name : "max",
								value : maxtype
							});
							sData.push({
								name : "format",
								value : maxtype
							});
						
							var jsonData = '{'
							$.each(sData, function(i, item) {
								var jsonString = getJsonString(item.name,
										item.value);
								if (jsonString != null
										&& $.trim(jsonString) != '')
									jsonData += getJsonString(item.name,
											item.value)
											+ ",";

							});
							jsonData = jsonData.slice(0, -1);
							jsonData += '}';
							if (aData[0] == null || $.trim(aData[0]) == '') {
								jQuery.ajax({
									type : 'POST',
									url : "/rms/projectStatus/createProjectStatus",
									data : jsonData,
									contentType : "application/json",
									success : function(data) {
										stopProgress();
										var successMsg = "Project Status \""+ aData[1] + "\" have been saved successfully";
											showSuccess(successMsg);
											reloadFunction();
									},
									error : function(data) {
										stopProgress();
										showError("Status"+ " \""+ aData[1]+ "\" is already present for this project. Please provide different name");
									}
								});
							} else {
								jQuery.ajax({
									type : 'PUT',
									url : "/rms/projectStatus/updateTicketStatus",
									data : jsonData,
									contentType : "application/json",
										success : function(data) {
											stopProgress();
											var successMsg = "Project Status \""+ aData[1] + "\" have been saved successfully";
												showSuccess(successMsg);
												reloadFunction();
											},
											error : function(data) {
												stopProgress();
												showError("Project Status}"+ " \""+ aData[1] + "\" is already present. Please provide different name");
											}
								});
							}
							}
				});
</script>
<script>
	var data = {getCostRowId : function(arg) {return $("table#addPoTbl tbody").find("tr").length;}}
	$.views.helpers({GetRowId : data.getCostRowId});
	$.templates("poTableRowsTempl", {markup : "#poTableRows"});
	$.templates("poTableRowsWithValuesTempl", {markup : "#poTableRowsWithValues"});
</script>
<style>
body {
	background-color: #fff !important;
}
</style>
<div class="mid_section">
	<!--right section-->
	<div class="botMargin">
		<h1>Ticket Status</h1>
	</div>
	<div class="tab_section">
		<ul class='tabs'>
			<li><a href='#tab1'>List</a></li>
		</ul>
		<div id='tab1' class="tab_div">
			<div class="search_filter_outer">
				<div class="search_filter search_filterLeft"></div>
				<div class="btnIcon" id="add">
					<a id="addNew" class="blue_link" href="javascript:void(0);"><img width="16" height="22" src="/rms/resources/images/addUser.gif"> Add New </a>
					<div class="clear"></div>
				</div>
			</div>
			<div class="tbl">
				<table class="dataTbl display tablesorter addNewRow alignCenter" id="example">
					<thead>
						<tr>
							<th width="2%" align="center" valign="middle">S. No.</th>
							<th width="8%" align="center" valign="middle" id="name">Status</th>
							<!-- <th width="3%" align="center" valign="middle">Mandatory</th> -->
							<th width="3%" align="center" valign="middle">Edit</th>
							<th width="3%" align="center" valign="middle">Delete</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="status" varStatus="act" items="${activitys}">
							
							<tr id="${status.id}">
								<td align="center" valign="middle"><c:if test="${not empty status.id}">${act.index+1}</c:if></td>
								<td align="center" valign="middle">
									<c:if test="${empty status.status}">N.A.</c:if> 
									<c:if test="${not empty status.status}">${status.status}</c:if>
								</td>
								<%-- <td align="center" valign="middle">
									<c:if test="${empty status.mandatory}">
										<select name="mandatory">
											<option value="yes">Yes</option>
											<option value="no">No</option>
										</select>
									</c:if>
									<c:if test="${status.mandatory=='false'}">No</c:if>
									<c:if test="${status.mandatory=='true'}">Yes</c:if>
								</td> --%>
								<td align="center" valign="middle"><a class="edit" href="">Edit</a>
								</td>
								<td align="center" valign="middle"><c:set var="flag" value="false" /> 
									<a class="delete" href="">Delete </a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<!--right section-->
</div>
<div class="clear"></div>