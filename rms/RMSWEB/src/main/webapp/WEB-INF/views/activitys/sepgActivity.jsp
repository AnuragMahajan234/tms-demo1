<%@ page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>


<script type="text/javascript" charset="utf-8">
	var global = null;

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
		var inpSelect = $.trim(($('#' + tableId + ' >tbody >tr')
				.find("td:nth-child(" + columnId + ") input")).val());
		if (inpSelect == oldActivity) {
			flag = true;
		} else {
			<c:forEach var="activity" varStatus="" items="${activitys}">
			var activity = "${activity.activityName}";
			if (activity.toUpperCase() == inpSelect.toUpperCase()) {
				flag = false;
			}
			</c:forEach>
		}

		if (flag == false) {
			var errorMsg = screenId + " \"" + inpSelect
					+ "\" is already present. Please provide different "
					+ screenId;
			showError(errorMsg);
		}
		return flag;
	}

	$(document)
			.ready(
					function() {
						$("#example_filter").find('input').val(null);
						var oTable;

						/* Add the events etc before DataTables hides a column */
						$("thead input")
								.keyup(
										function() {
											/* Filter on the column (the index) of this element */
											oTable
													.fnFilter(
															this.value,
															oTable.oApi
																	._fnVisibleToColumnIndex(
																			oTable
																					.fnSettings(),
																			$(
																					"thead input")
																					.index(
																							this)));
										});

						/*
						 * Support functions to provide a little bit of 'user friendlyness' to the textboxes
						 */
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
//Added by Pratyoosh//
						oTable = $('#example').dataTable({
							//"sDom" : 'RC<"clear">lfrtip',//
							"sDom": 'RC<"clear">lfrtip<"top">rt<"bottom"ip<"clear">',
							"bStateSave" : true,
							"sPaginationType": "full_numbers",
							"aoColumnDefs" : [ {
								"bVisible" : false,
								"aTargets" : []
							} ],
							"oLanguage" : {
								"sSearch" : "Search:"
							},
							"aoColumns" : [ {}, {}, {}, {}, {
								"bSortable" : false
							}, {
								"bSortable" : false
							} ],
							"bSortCellsTop" : true,
						});

						//Added by Pratyoosh//						// 				var nEditing = null;
						// 				var saveOpen = false;

						$("#example_filter").find('input').bind(
								'change paste keyup',
								function() {
									var srch = $("#example_filter").find(
											'input').val();
									if (null == srch || $.trim(srch) == '') {
										$("#add").show();
									} else {
										$("#add").hide();
									}

								});

						$('#addNew')
								.click(
										function(e) {
											if (saveOpen) {

												// Added for task # 216 - Start
												/* alert("Please enter and save the data"); */
												var text = "Please enter and save the data";
												showAlert(text);
												// Added for task # 216 - End
												e.preventDefault();
												return;
											}
											e.preventDefault();

											var aiNew = oTable
													.fnAddData([
															'',
															'',
															'',
															'',
															'<a class="edit" href="">Edit</a>',
															'<a class="delete" href="">Delete</a>' ]);

											var nRow = oTable
													.fnGetNodes(aiNew[0]);
											global = nRow;
											editRow(oTable, nRow);
											nEditing = nRow;
											saveOpen = true;

											//$("table.dataTbl tbody tr").find("td:nth-child(1) input").removeAttr("readonly");

										});
						$(document).on(
								'click',
								'#example a.cancel',
								function(e) {
									$("#example tbody tr").find("td").attr(
											"align", "center");
									// cancelDataRow();	
									addNewAlloc = false;
									editAlloc = false;
									saveOpen = false;
									e.preventDefault();
									var nRow = $(this).parents('tr')[0];
									nEditing = nRow;
									restoreRow(oTable, nEditing);
								});

						function cancelDataRow(id, empId, projId) {
							if (id == undefined || id == null || id == '') {
								oTable.fnDeleteRow(global);
								$("table#example tbody").find("tr:first").find(
										"input[type=text]").blur();
								saveOpen = false;
								nEditing = null;
								$('.toasterBgDiv').remove();
								return;
							}
							$('.toasterBgDiv').remove();
							//startProgress();
						}
						$(document).on('click', '.delAddedRow', function() {

							cancelDataRow(null, null, null);
						});

						$('#example')
								.delegate(
										'a.delete',
										'click',
										function(e) {

											e.preventDefault();
											var nRow = $(this).parents('tr')[0];
											var id = $(nRow).attr('id');
											//redmine task 417
											if (saveOpen
													&& !(id == undefined
															|| id == null || id == '')) {

												var text = "Please enter and save the data";
												showAlert(text);

												e.preventDefault();
												return;
											}
											var url = "../activitys/" + id;
											var aData = oTable.fnGetData(nRow);
											var text = "Are you sure you want to delete Activity \""
													+ aData[1] + "\" ?";
											var successMessage = "Activity \""
													+ aData[1]
													+ "\" has been successfully deleted.";
											if (id == undefined || id == null
													|| id == '') {
												text = "Are you sure you want to delete this row ?";
												successMessage = "Activity has been successfully deleted.";
											}
											deleteRow(oTable, nRow, url, text,
													successMessage);
											$(
													'<div class="toasterBgDiv"></div>')
													.appendTo($('body'));
										});

						$('#example')
								.delegate(
										'a.edit',
										'click',
										function(e) {
											if (saveOpen
													&& this.innerHTML != "Save") {
												// Added for task # 216 - Start
												/* alert("Please enter and save the data"); */
												var text = "Please enter and save the data";
												showAlert(text);
												// Added for task # 216 - End
												e.preventDefault();
												return;
											}
											saveOpen = true;
											e.preventDefault();

											/* Get the row as a parent of the link that was clicked on */
											var nRow = $(this).parents('tr')[0];

											if (nEditing !== null
													&& nEditing != nRow) {
												/* Currently editing - but not this row - restore the old before continuing to edit mode */
												restoreRow(oTable, nEditing);
												editRow(oTable, nRow);
												nEditing = nRow;

											} else if (nEditing == nRow
													&& this.innerHTML == "Save") {
												/* Editing this row and want to save it */
												// Provide the required parameters in the below functions to validate the inputs
												if (!validateInputs('.tbl')
														|| !valideDuplicates(
																'example',
																'Activity', 2)) {
													return;
												}
												saveRow(oTable, nEditing);
												saveOpen = false;
												nEditing = null;
											} else {
												/* No edit in progress - let's start one */
												editRow(oTable, nRow);
												nEditing = nRow;
											}
										});

						function editRow(oTable, nRow) {

							/* 	 startProgress(); */
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

							jqTds[2].innerHTML = '<select id ="mandatory'
									+ getInputValue(aData[2]) + '">'
									+ '<option value="True">Yes</option>'
									+ '<option value="False">No</option>'
									+ '</select>';

							jqTds[3].innerHTML = '<select id ="productive'
									+ getInputValue(aData[3]) + '">'
									+ '<option value="True">Yes</option>'
									+ '<option value="False">No</option>'
									+ '</select>';

							jqTds[4].innerHTML = '<a class="edit" href="#">Save</a> / <a class="cancel" href="#">Cancel</a>';

							//Start Added to solve default values in drop down
							var value = getInputValue(aData[2]);
							if (aData[2].toLowerCase() == 'yes') {
								$("#mandatory" + value).val("True");
							} else {
								$("#mandatory" + value).val("False");
							}
							value = getInputValue(aData[3]);
							if (aData[3].toLowerCase() == 'yes') {
								$("#productive" + value).val("True");
							} else {
								$("#productive" + value).val("False");
							}

							$("#example tbody tr:first").find("td").find(
									"a.cancel").addClass("delAddedRow");

							stopProgress();
						}

						function saveRow(oTable, nRow) {

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
							/* if (spclCharacterValidation(stringName)) {
								showError("Please Enter valid activity name");
								stopProgress();
								return false;

							}
							 */
							var rowDatabaseId = $(nRow).attr('id');
							bData[0] = jqSelects[0].value;
							bData[1] = jqSelects[1].value;
							var sData = $('*', oTable.fnGetNodes())
									.serializeArray();
							if (rowDatabaseId != null && rowDatabaseId != '')
								sData.push({
									name : "id",
									value : rowDatabaseId
								});
							aData[1] = capitaliseFirstLetter(aData[1]);
							sData.push({
								name : "activityName",
								value : aData[1]
							});
							sData.push({
								name : "mandatory",
								value : bData[0]
							});
							sData.push({
								name : "type",
								value : "SEPG"
							});
							sData.push({
								name : "max",
								value : maxtype
							});
							sData.push({
								name : "format",
								value : maxtype
							});

							sData.push({
								name : "productive",
								value : bData[1]
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

							// alert("jsonData============"+jsonData);
							if (aData[0] == null || $.trim(aData[0]) == '') {

								$
										.postJsonData(
												"../activitys",
												jsonData,
												function(data) {
													nEditing = null;
													stopProgress();
													var successMsg = "New Activity \""
															+ aData[1]
															+ "\" has been inserted successfully";
													showSuccess(successMsg);
													setTimeout(function() {
														location.reload();
													}, 1000);
												}, 'json');

							} else {
								$
										.putJsonData(
												"../activitys",
												jsonData,
												function(data) {
													nEditing = null;
													stopProgress();
													var successMsg = "Activity \""
															+ aData[1]
															+ "\" details have been saved successfully";
													showSuccess(successMsg);
													setTimeout(function() {
														location.reload();
													}, 1000);
												}, 'json');
							}
						}

					});
</script>
<script>
	var data = {
		getCostRowId : function(arg) {
			return $("table#addPoTbl tbody").find("tr").length;
		}

	}

	$.views.helpers({
		GetRowId : data.getCostRowId
	});
	$.templates("poTableRowsTempl", {
		markup : "#poTableRows"
	});
	$.templates("poTableRowsWithValuesTempl", {
		markup : "#poTableRowsWithValues"

	});
</script>

<div class="content-wrapper">
	<!--right section-->
	<div class="botMargin">
		<h1>SEPG Activity</h1>
	</div>
	<div class="tab_section">
		<ul class='tabs'>
			<li><a href='#tab1'>List</a></li>
		</ul>
		<div id='tab1' class="tab_div">
			<div class="search_filter_outer">
				<div class="search_filter search_filterLeft"></div>
				<div class="btnIcon" id="add">
					<a id="addNew" class="blue_link" href="javascript:void(0);"><img
						width="16" height="22" src="/rms/resources/images/addUser.gif">
						Add New </a>

					<div class="clear"></div>
				</div>
			</div>
			<div class="tbl">
				<table class="dataTbl display tablesorter addNewRow alignCenter"
					id="example">
					<thead>
						<tr>
							<th width="2%" align="center" valign="middle">S. No.</th>
							<th width="8%" align="center" valign="middle" id="name">SEPG
								Activity Name</th>
							<th width="3%" align="center" valign="middle">Mandatory</th>
							<th width="3%" align="center" valign="middle">Productive</th>
							<th width="3%" align="center" valign="middle">Edit</th>
							<th width="3%" align="center" valign="middle">Delete</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="activity" varStatus="act" items="${activitys}">
							<tr id="${activity.id}">
								<td align="center" valign="middle"><c:if
										test="${not empty activity.id}">${act.index+1}</c:if></td>
								<td align="center" valign="middle"><c:if
										test="${empty activity.activityName}">N.A.</c:if> <c:if
										test="${not empty activity.activityName}">${activity.activityName}</c:if>
								</td>
								<td align="center" valign="middle"><c:if
										test="${empty activity.mandatory}">
										<select name="mandatory">
											<option value="yes">Yes</option>
											<option value="no">No</option>
										</select>
									</c:if> <c:if test="${activity.mandatory=='false'}">No</c:if> <c:if
										test="${activity.mandatory=='true'}">Yes</c:if></td>

								<td align="center" valign="middle"><c:if
										test="${empty activity.productive}">
										<select name="productive">
											<option value="yes">Yes</option>
											<option value="no">No</option>
										</select>
									</c:if> <c:if test="${activity.productive=='false'}">No</c:if> <c:if
										test="${activity.productive=='true'}">Yes</c:if></td>
								<td align="center" valign="middle"><a class="edit" href="">Edit</a>
								</td>
								<td align="center" valign="middle"><a class="delete"
									href="">Delete</a></td>
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