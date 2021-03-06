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
<spring:url
	value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}"
	var="multiselect_js" />
<script src="${multiselect_js}" type="text/javascript"></script>

<script type="text/javascript" charset="utf-8">
	$(document)
			.ready(
					function() {
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
                             //DataTable page satate remain on privious state Issue Solved (Arun) 
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
											"aoColumns" : [ {}, {}, {},{
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
												var text = "Please enter and save the data";
												showAlert(text);
												e.preventDefault();
												return;
											}
											//var url = "pdls/" + id;
											var aData = oTable.fnGetData(nRow);
											var text = "Are you sure you want to delete PDL Email Group \""
													+ aData[1] + "\" ?";
											var successMessage = "PDL Email Group \""
													+ aData[1]
													+ "\" has been successfully deleted.";
											if (id == undefined || id == null
													|| id == '') {
												text = "Are you sure you want to delete this row ?";
												successMessage = "PDL Email Group has been successfully deleted.";
											}
											deleteRow(oTable, nRow, text,
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
												var flag = true;
												// Provide the required parameters in the below functions to validate the inputs
												if (!validateInputs('.tbl')
														|| !validateDuplicates(
																'example',
																'PDL Email', 2)) {
													return;
												}
												
												if (!validateInputs('.tbl')
														|| !validateDuplicates(
																'example',
																'Group Name', 3)) {
													return;
												}
												
												saveRow(oTable, nEditing);
												saveOpen = true;
												//nEditing = null;
											} else {
												/* No edit in progress - let's start one */
												editRow(oTable, nRow);
												nEditing = nRow;
											}
										});

						$('#example')
								.delegate(
										'a.deactivate',
										'click',
										function(e) {
											//alert("Inside DeActivate function");
											e.preventDefault();
											var nRow = $(this).parents('tr')[0];
											var id = $(nRow).attr('id');
											if (saveOpen) {
												var text = "Please enter and save the data";
												showAlert(text);
												e.preventDefault();
												return;
											}
											$
													.ajax({
														async : true,

								contentType : "application/json",
								url : "pdls/" + "N" + "/" + id,
								//data:   id ,
								success : function(data) {
									showSuccess("Successfully DeActivated");
									setTimeout(function() {
										location.reload();
									}, 1000);
									
								}
							});

						});
						
						$('#example').delegate('a.activate', 'click', function(e) {
							//alert("Inside DeActivate function");
							e.preventDefault();

							var nRow = $(this).parents('tr')[0];

							var id = $(nRow).attr('id');
							if (saveOpen) {

								var text = "Please enter and save the data";
								showAlert(text);

								e.preventDefault();
								return;
							}
							$.ajax({
								async : true,

								contentType : "application/json",
								url : "pdls/" + "Y" + "/" + id,
								//data:   id ,
								success : function(data) {
									showSuccess("Successfully Activated");
									setTimeout(function() {
										location.reload();
									}, 1000);
									
								}
							});

						});
						
						//Add Final Validation on PDL (Arun)
						function saveRow(oTable, nRow) {
							startProgress();
							var jqInputs = $('input', nRow);
							var aData = jqInputs;
							//added by Barkha
							var status = $(nRow).closest('tr').find('td:eq(4)').text().trim();
							//
							for (var i = 0; i < jqInputs.length; i++) {
								aData[i] = jqInputs[i].value;
							}

							aData[1] = aData[1].trim();
							aData[2] = aData[2].trim();

							var rowDatabaseId = $(nRow).attr('id');
							var sData = $('*', oTable.fnGetNodes())
									.serializeArray();
							//added by Barkha
							if (rowDatabaseId != null && rowDatabaseId != ''){
								sData.push({
									name : "id",
									value : rowDatabaseId
								});
								if(status=="Activate")
									sData.push({
										name : "active",
										value : "N"
									});
								else
									sData.push({
										name : "active",
										value : "Y"
									});
							}
								
							else
								sData.push({
									name : "active",
									value : "Y"
								});
							//
							sData.push({
								name : "pdlEmailId",
								value : aData[1]
							});
							
							sData.push({
								name : "pdlName",
								value : aData[2]
							});
							//added by Barkha
							var email=aData[1];
							var name=aData[2];
							var validEmailCheck = null;
							var PdlEmailName = $('#example >tbody >tr ').find(
									"td:nth-child(2) input");
							$(PdlEmailName).each(function() {
							//The below commented code was for check email Domain ("@yash.com") 
							/*   var PdlEmailNameVal = $(this).val();
								  if (!validateEmail(email.trim())) {
									validEmailCheck = PdlEmailNameVal;
									validationFlag = false;
									$(this).addClass("brdrRed");
								} else {
									$(this).removeClass("brdrRed");
								}   
								     */
								
								var PdlEmailNameVal = $(this).val().trim();
								 var emailReg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
                                    if (emailReg.test(PdlEmailNameVal) == false) 
							        {
                                    	validEmailCheck = PdlEmailNameVal;
							        	validationFlag = false;
										$(this).addClass("brdrRed");
							        }else {
										$(this).removeClass("brdrRed");
									}

								
							});
							if (validEmailCheck != null) {
							//	var errorMsg = "\u2022 Domain of E-mail should be '@yash.com' ! <br />"; //(Old PDL email error msg)
								var errorMsg = "\u2022 Please enter correct email  ! <br />";
								showError(errorMsg);
								 stopProgress();
								return false;
							}

							var validPdlNameCheck = null;
							var PdlName = $('#example >tbody >tr ').find(
									"td:nth-child(3) input");
							$(PdlName).each(function() {
								var PdlNameVal = $(this).val();

								if (spclCharacterValidation(name)) {
									validPdlNameCheck = PdlNameVal;
									validationFlag = false;
									$(this).addClass("brdrRed");
								} else {
									$(this).removeClass("brdrRed");
								}
							});
							if (validPdlNameCheck != null) {
								var errorMsg = "Please Enter valid Name name";
								showError(errorMsg);
								stopProgress();
								return false;
							}

							function spclCharacterValidation(name) {
								var res = name.charAt(0);
								if (res != null && res.match(/[a-zA-Z]/i)) {
								} else {
									return true;
									stopProgress();
								}
							}

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
								$
										.postJsonData(
												"pdls",
												jsonData,
												function(data) {
													saveOpen = false;
													nEditing = null;
													stopProgress();
													var successMsg = "PDL Email \""
															+ aData[1]
															+ "\" has been inserted successfully";
													showSuccess(successMsg);
													setTimeout(function() {
														location.reload();
													}, 1000);
												}, "json");
							} else {
								$
										.putJsonData(
												"pdls",
												jsonData,
												function(data) {
													//alert("hjgjgj"+data);
													saveOpen = false;
													nEditing = null;
													stopProgress();
													var successMsg = "PDL Email Group \""
															+ aData[1]
															+ "\" details have been saved successfully";
													showSuccess(successMsg);
													setTimeout(function() {
														location.reload();
													}, 1000);
												}, "json");
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
		<h1>PDL</h1>
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
							<th width="8%" align="center" valign="middle">PDL Email</th>
							<th width="8%" align="center" valign="middle">Group Name</th>
							<th width="4%" align="center" valign="middle">Edit</th>
							<th width="4%" align="center" valign="middle">Delete</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="pdl" varStatus="pdlState" items="${pdlList}">
							<tr id="${pdl.id}">
								<td align="center" valign="middle"><c:if
										test="${not empty pdl.id}">${pdlState.index+1}</c:if></td>
								<td align="center" valign="middle"><c:if
										test="${empty pdl.pdlEmailId}">N.A.</c:if> <c:if
										test="${not empty pdl.pdlEmailId}">${pdl.pdlEmailId}</c:if>
								</td>
								<!-- addded by sonali  -->
								 <td align="center" valign="middle"><c:if
										test="${empty pdl.pdlName}">N.A.</c:if> <c:if
										test="${not empty pdl.pdlName}">${pdl.pdlName}</c:if>
								</td>  
								
								<!-- addded by sonali  -->
								<td align="center" valign="middle"><a class="edit" href="">Edit</a>
								</td>
								<td align="center" valign="middle">
									<c:if test="${pdl.active=='N'}"><a class="activate" href="">Activate</a></c:if>
									<c:if test="${pdl.active=='Y'}"><a class="deactivate" href="">DeActivate</a></c:if>
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
