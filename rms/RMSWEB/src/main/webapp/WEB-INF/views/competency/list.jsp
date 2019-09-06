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

<spring:url
	value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url
	value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />

<script type="text/javascript" charset="utf-8">
//Issue:- On click data table edit option It send to first page 
//Solution:- We have declare (var oTable;) as a globally 
var oTable;
	$(document)
			.ready(
					function() {
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
						 //DataTable page state remain on previous state Issue Solved (Arun)
						oTable = $('#example')
								.dataTable(
										{
											"sDom" : 'RC<"clear">lfrtip<"top">rt<"bottom"ip<"clear">',
											"sPaginationType" : "full_numbers",
											"bStateSave" : false,
											"aoColumnDefs" : [ {
												"bVisible" : false,
												"aTargets" : []
											} ],
											"oLanguage" : {
												"sSearch" : "Search:"
											},
											"aoColumns" : [ {}, {}, {
												"bSortable" : false
											}, {
												"bSortable" : false
											} ],
											"bSortCellsTop" : true,
										});

						// 				var nEditing = null;
						// 				var saveOpen = false;
						$("#example_filter").find('input').bind(
								'change paste keyup',
								function() {
									var srch = $("#example_filter").find(
											'input').val();
									if (null == srch || $.trim(srch) == '') {
										$("#add").show();
									} else {
									// For Show Add button After Search (Arun)
									//	$("#add").show();
										$("#add").hide();
									}

								});
						$('#addNew')
								.click(
										function(e) {
											if (saveOpen) {
												// Added for task # 216 - Start
												/* alert("Please enter and save the data"); */
												var text = "Please enter and save the data OR delete row";
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
															'<a class="edit" href="">Edit</a>',
															'<a class="delete" href="">Delete</a>' ]);
											var nRow = oTable
													.fnGetNodes(aiNew[0]);
											editRow(oTable, nRow);
											nEditing = nRow;
											saveOpen = true;
											//	$("table.dataTbl tbody tr").find("td:nth-child(1) input").removeAttr("readonly");
										});

						$('#example')
								.delegate(
										'a.delete',
										'click',
										function(e) {

											e.preventDefault();
											var nRow = $(this).parents('tr')[0];
											var id = $(nRow).attr('id');
											if (saveOpen
													&& !(id == undefined
															|| id == null || id == '')) {
												var text = "Please save OR cancel the row"; 
												showAlert(text);
												e.preventDefault();
												return;
											}
											var url = "competencies/" + id;
											var aData = oTable.fnGetData(nRow);
											var text = "Are you sure you want to delete Competency \""
													+ aData[1] + "\" ?";
											var successMessage = "Competency \""
													+ aData[1]
													+ "\" has been successfully deleted.";
											if (id == undefined || id == null
													|| id == '') {
												text = "Are you sure you want to delete this row ?";
												successMessage = "Competency has been successfully deleted.";
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
												var text = "Please enter and save the data OR delete row";
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
												editRows(oTable, nRow);
												nEditing = nRow;

											} else if (nEditing == nRow
													&& this.innerHTML == "Save") {
												/* Editing this row and want to save it */
												var flag = true;
												// Provide the required parameters in the below functions to validate the inputs
												if (!validateInputs('.tbl')
														|| !validateDuplicates(
																'example',
																'Competency', 2)) {
													return;
												}
												saveRow(oTable, nEditing);
												saveOpen = true;
												//nEditing = null;
											} else {
												/* No edit in progress - let's start one */
												editRows(oTable, nRow);
												nEditing = nRow;
											}
										});

						// Below javascript code check the duplicate competency	
						function validateDuplicates(tableId, screenId, columnId) {
							var flag = true;

							var inpSelect = $.trim(($('#' + tableId
									+ ' >tbody >tr').find("td:nth-child("
									+ columnId + ") input")).val());

							inpSelect=inpSelect.trim();
							
							<c:forEach var="competencies" varStatus="competency" items="${competency}">
							var skill = "${competencies.skill}";
							skill=skill.trim();
							if (skill.toUpperCase() == inpSelect.toUpperCase()) {
								flag = false;

							}
							</c:forEach>
							/* var labelSelect = $('#' + tableId + ' >tbody >tr').find("td:nth-child("+columnId+")");
							$(labelSelect).each(function() {
							if ($(this).text() != '') {
								tdInpVal = $.trim($(this).text());
								
								competency
								
								labelArray.push(tdInpVal);
							} else {
								inputArray.push(inpSelect);
							}
							}); */
							/* if (rowCount > 1) {
								for ( var i = 0; i < inputArray.length; i++) {
									var arrlen = labelArray.length;
									for ( var j = 0; j < arrlen; j++) {
										if (inputArray[i].toUpperCase() == labelArray[j].toUpperCase()) {
											flag = false;
											break;
										}
									}
								}
								
							} */

							//Apply red border style on input field when error comes
							if (flag == false) {
								var errorMsg = screenId
										+ " \""
										+ inpSelect
										+ "\" is already present. Please Edit and Save "
										+ screenId + " 'OR' Cancel ";
								showError(errorMsg);
								$(".competency_name").css("border", "1px solid #ff0000");
							}
							else{
								$(".competency_name").css("border", "");
							}
							
							
							return flag;
						}

				// check the duplicate competency end here	
		
		       // editRows fuction called when we edit already exit competency
		    function editRows(oTable, nRow) {
			startProgress();
			var aData = oTable.fnGetData(nRow);
			var jqTds = $(">td", nRow);
			if (jqTds.length < 1)
				return;

							jqTds[0].innerHTML = '<input type="text" value="'
									+ aData[0]
									+ '" readonly="readonly" onfocus="this.blur()">';
							jqTds[1].innerHTML = '<input type="text" class="competency_name" value="'+aData[1]+'">';
							//added for #3102

			//ADDED for DE4449 by Pratyoosh
			//added for #3102
			jqTds[2].innerHTML = '<a id="save" class="edit" href="javascript:void(0);"  >Save</a> / <a href="javascript:void(0);" class="cancel">Cancel</a>';
			stopProgress();
		}

						function saveRow(oTable, nRow) {
							startProgress();
							var jqInputs = $('input', nRow);
							var aData = jqInputs;
							for (var i = 0; i < jqInputs.length; i++) {
								aData[i] = jqInputs[i].value;
							}
							var rowDatabaseId = $(nRow).attr('id');
							var sData = $('*', oTable.fnGetNodes())
									.serializeArray();
							if (rowDatabaseId != null && rowDatabaseId != '')
								sData.push({
									name : "id",
									value : rowDatabaseId
								});
							aData[1] = capitaliseFirstLetter(aData[1]);
							sData.push({
								name : "skill",
								value : aData[1].trim()
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
				$.postJsonData("competencies",jsonData,function(data) {
					saveOpen = false;
					nEditing = null;
					stopProgress();
					var successMsg = "competency \""
							+ aData[1]
							+ "\" has been inserted successfully";
					showSuccess(successMsg);
					setTimeout(function() {
						location.reload();
					}, 1000);
				}, "json");
			} else {$.putJsonData("competencies",jsonData,function(data) {
					saveOpen = false;
					nEditing = null;
					stopProgress();
					var successMsg = "competency \""
							+ aData[1]
							+ "\" details have been saved successfully";
					showSuccess(successMsg);
					setTimeout(function() {
						location.reload();
					}, 1000);
				}, "json");
			}
		}

		// On Click Cancel called here
		$(document).on('click','#example a.cancel', function(e) {
			$("#example tbody tr").find("td").attr(
					"align", "center");
			saveOpen = false;
			e.preventDefault();
			var nRow = $(this).parents('tr')[0];
			restoreRow(oTable, nRow);
		});
	});

	function restoreRow(oTable_, nRow) {
		var aData = oTable.fnGetData(nRow);
		var jqTds = $('>td', nRow);
		for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
			oTable.fnUpdate(aData[i], nRow, i, false);
		}
		oTable.fnDraw(false);
	}
	
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
		<h1>Competency</h1>
	</div>
	<div class="tab_section">
		<ul class='tabs'>
			<li><a href='#tab1'>List</a></li>
		</ul>
		<div id='tab1' class="tab_div">
			<div class="search_filter_outer">
				<div class="search_filter search_filterLeft"></div>
				<div class="btnIcon" id="add">
					<a id="addNew" class="blue_link" href="javascript:void(0);"> <img
						width="16" height="22" src="resources/images/addUser.gif">
						Add New
					</a>

					<div class="clear"></div>
				</div>
			</div>
			<div class="tbl">
				<table class="dataTbl display tablesorter addNewRow alignCenter"
					id="example">
					<thead>
						<tr>
							<th width="7%" align="center" valign="middle">S. No.</th>
							<th width="8%" align="center" valign="middle">Competency
								Name</th>
							<th width="4%" align="center" valign="middle">Edit</th>
							<th width="4%" align="center" valign="middle">Delete</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="competency" varStatus="com" items="${competency}">
							<tr id="${competency.id}">
								<td align="center" valign="middle"><c:if
										test="${not empty competency.id}">${com.index+1}</c:if></td>
								<td align="center" valign="middle"><c:if
										test="${empty competency.id}">N.A.</c:if> <c:if
										test="${not empty competency.id}">
								  ${competency.skill}
 								  </c:if></td>
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
