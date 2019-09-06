
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

<style type="text/css" title="currentStyle">
#tab1 table.dataTable {
	margin: 0 auto;
	clear: both;
	width: 100%;
	border: 0px solid #9b9b9b;
	border-collapse: separate;
	border-spacing: 1px;
}
</style>

<script type="text/javascript" charset="utf-8">
	var selectedEvent;
	$(document)
			.ready(
					function() {
						var oTable;
						//	$("#example > tbody:last").append($("#eventRows").render(data));

						function editRow(oTable, nRow) {
							//alert("in editRow");
							//displayMaintainance();
							var aData = oTable.fnGetData(nRow);
							var jqTds = $(">td", nRow);
							if (jqTds.length < 1)
								return;
							//alert("getInputValue(aData[0]);----"+ getInputValue(aData[0]));
							/* jqTds[0].innerHTML = getInputValue(aData[0]); */
							jqTds[0].innerHTML = $(aData[0]).text();
							jqTds[1].innerHTML = '<input type="text" value="'+aData[1]+'">';
							jqTds[2].innerHTML = '<input type="text" value="'+aData[2]+'">';
							jqTds[3].innerHTML = '<a class="edit" href="">Save</a>';
							jqTds[4].innerHTML = '<a class="delete" href="javascript:void(0);">Delete</a>';

							//alert("edit end");
						}

						function getInputValue(str) {
							// for internet explorer
							var index1 = str.indexOf("<INPUT");

							// for firefox
							var index = str.indexOf("<input");
							/* if(index < 0)return null;
							str = str.substr(index , str.length);
							return $(str).val(); */
							if (index < 0 && index1 < 0) {
								return null;
							}

							if (index < 0) {
								str = str.substr(index1, str.length);
								return $(str).val();
							}

							if (index1 < 0) {
								str = str.substr(index, str.length);
								return $(str).val();
							}
						}
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

						oTable = $('#example')
								.dataTable(
										{
											"sDom" : '<"top"lfip>rt<"bottom"ip<"clear">',
											"sPaginationType" : "full_numbers",
											"bStateSave" : false,
											"aoColumnDefs" : [ {
												"bVisible" : false,
												"aTargets" : []
											} ],
											"oLanguage" : {
												"sSearch" : "Search:"
											},
											"aoColumns" : [ {}, {}, {}, {
												"bSortable" : false
											}, {
												"bSortable" : false
											} ],
											"bSortCellsTop" : true,
											"sScrollX": "100%",
											"sScrollY": "350",
											"bScrollCollapse": true,
										});

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
						// 				var nEditing = null;
						// 				var saveOpen = false;

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
															'0',
															'',
															'',
															'<a class="edit" href="">Edit</a>',
															'<a class="delete" href="javascript:void(0);">Delete</a>' ]);
											var nRow = oTable
													.fnGetNodes(aiNew[0]);
											editRow(oTable, nRow);
											nEditing = nRow;
											saveOpen = true;
											//$("table.dataTbl tbody tr").find("td:nth-child(1) input").removeAttr("readonly");
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
											var url = "events/" + id;
											var aData = oTable.fnGetData(nRow);
											var text = "Are you sure you want to delete Event \""
													+ aData[1] + "\" ?";
											var successMessage = "Event \""
													+ aData[1]
													+ "\" has been successfully deleted.";
											if (id == undefined || id == null
													|| id == '') {
												text = "Are you sure you want to delete this row ?";
												successMessage = "Event has been successfully deleted.";
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
											//alert("a.....edit");
											if (saveOpen
													&& this.innerHTML != "Save") {
												//alert("a.edit");
												// Added for task # 216 - Start
												/* alert("Please enter and save the data"); */
												var text = "Please enter and save the data";
												showAlert(text);
												// Added for task # 216 - End
												e.preventDefault();
												return;
											}
											//alert("a1...edit");
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
														|| !validateDuplicates(
																'example',
																'Event', 2)) {
													return;
												}
												saveRow(oTable, nEditing);
												saveOpen = false;
												nEditing = null;
											} else {
												editRow(oTable, nRow);
												nEditing = nRow;
											}
										});

						/*---JS For Tabbing---*/
						//alert("edit end");
						$(".tab_div").hide();
						$('#MaintenanceTabInactive').off('click');

						$('ul.tabs a').click(function() {
							$(".tab_div").hide().filter(this.hash).show();
							$("ul.tabs a").removeClass('active');
							$('a[href$="tab2"]').addClass('MaintenanceTab');
							$(this).addClass('active');
							containerWidth();
							$('#MaintenanceTabInactive').off('click');
							return false;
						}).filter(':first').click();

						$('a[href$="tab2"]').click(function(e) {
							$('#MaintenanceTabInactive').off('click');
							e.preventDefault();

						});

						$('a[href$="tab1"]').click(function(e) {
							location.reload();
						});

						$('#eventDataTable')
								.delegate(
										'a.saveRules',
										'click',
										function(e) {
											//alert("save" + selectedEvent);
											//var id= document.getElementById('myEventId').value;
											$("#eventDataForm")
													.append(
															"<input type='hidden' name='myEventId' value='"+selectedEvent+"'/>");
											//$("#eventDataForm").append("<input type='hidden' name='eventTxt' value='"+document.getElementById('eventTxt').value+"'/>");
											$("#eventDataForm").submit();
										});

						$(document).on(
								'click',
								'#example a.cancel',
								function(e) {
									$("#example tbody tr").find("td").attr(
											"align", "center");
									//Updated for Issue #46
									saveOpen = false;
									var nRow = $(this).parents('tr')[0];
									//Updated for Issue #46
									e.preventDefault();
									restoreRow(oTable, nRow);
								});

						function saveRow(oTable, nRow) {
							startProgress();
							var jqInputs = $('input', nRow);
							var aData = jqInputs;
							for (var i = 0; i < jqInputs.length; i++) {
								aData[i] = jqInputs[i].value;
								//alert(i+"-----"+aData[i]);
							}
							var rowDatabaseId = $(nRow).attr('id');
							var sData = $('*', oTable.fnGetNodes())
									.serializeArray();
							//alert("rowDatabaseId...null"+rowDatabaseId);
							//alert("rowDatabaseId"+rowDatabaseId);
							if (rowDatabaseId != null && rowDatabaseId != '')
								sData.push({
									name : "id",
									value : rowDatabaseId
								});
							//aData[1] = capitaliseFirstLetter(aData[1]);
							sData.push({
								name : "event",
								value : aData[0]
							});
							sData.push({
								name : "description",
								value : aData[1]
							});
							var stringName = aData[0];
							/* if(spclCharacterValidation(stringName)){
								showError("Please Enter valid event name");
								 stopProgress();
								return false;
							}  */
							//alert("Hiiii");
							var jsonData = '{'
							$.each(sData, function(i, item) {
								var jsonString = getJsonString(item.name,
										item.value);
								//alert("item.name-----"+item.name)
								if (item.name != "myEventId") {
									if (jsonString != null
											&& $.trim(jsonString) != '')
										jsonData += getJsonString(item.name,
												item.value)
												+ ",";
								}
							});
							jsonData = jsonData.slice(0, -1);
							jsonData += '}';
							//alert(jsonData);
							if (aData[0] == null || $.trim(aData[0]) == '') {
								//alert("in postJsonData");
								$
										.postJsonData(
												"events",
												jsonData,
												function(data) {
													saveOpen = false;
													nEditing = null;
													stopProgress();
													var successMsg = "Event \""
															+ aData[0]
															+ "\" has been inserted successfully";
													showSuccess(successMsg);

													openMaintainance(data.id);
													$('#example')
															.dataTable()
															.fnAddData(
																	[
																			''
																					+ data.id
																					+ '',
																			''
																					+ data.event
																					+ '',
																			''
																					+ data.description
																					+ '',
																			'<a href="javascript:void(0)" class="edit">Edit</a>',
																			'<a href="javascript:void(0)" class="delete">Delete</a>' ]);

												}, "json");
							} else {
								//alert("in putJsonData"+jsonData);
								$
										.putJsonData(
												"events",
												jsonData,
												function(data) {
													saveOpen = false;
													nEditing = null;
													stopProgress();
													var successMsg = "Event \""
															+ aData[0]
															+ "\" details have been saved successfully";
													showSuccess(successMsg);
													/* 			  setTimeout(function() {
																	location.reload();	
																}, 1000);  */

													openMaintainance(data.id);
													$('#example')
															.dataTable()
															.fnAddData(
																	[
																			''
																					+ data.id
																					+ '',
																			''
																					+ data.event
																					+ '',
																			''
																					+ data.description
																					+ '',
																			'<a href="javascript:void(0)" class="edit">Edit</a>',
																			'<a href="javascript:void(0)" class="delete">Delete</a>' ]);

												}, "json");
							}
						}

					});

	function getJsonString(name, value) {
		if (name.indexOf(".") > -1) {
			var items = name.split(".", 2);
			var jsonInner = getJsonString(items[1], value);
			var json = '"' + items[0] + '":{' + jsonInner + '}';
			return json;
		}
		//alert("value--"+value.val());
		/* if(value.toLowerCase() == 'Y' )return '"'+name+ '":' + "Y";
		if(value.toLowerCase() =="N")return '"'+name+ '":' + "N";
		if($.trim(value) == '' || $.trim(value).toLowerCase() == 'null')return ''; */
		return '"' + name + '":"' + value + '"';

	}

	function restoreRow(oTable, nRow) {
		var aData = oTable.fnGetData(nRow);
		var jqTds = $('>td', nRow);

		for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
			oTable.fnUpdate(aData[i], nRow, i, true);
		}
		oTable.fnDraw();
	}

	function openMaintainance(id) {
		startProgress();
		getEventById(id);

		displayMaintainance(id);
		//var nRow = $(this).parents('tr')[0];
		//alert(nRow);
		//editRow( oTable, nRow );
		stopProgress();
	}

	function displayMaintainance(id) {

		selectedEvent = id;
		//alert("before ID" + selectedEvent);
		//document.getElementById("event"+id).value = id;
		//alert("Name----document.getElementById(eventTxt)-----"+document.getElementById("eventTxt").value);
		//alert("----Name-----"+document.getElementById("eventName").value);

		$("#eventDataTable").show();
		$("ul.tabs a").removeClass('active');
		$(".tab_div").hide().next("#tab2".hash).show();
		$('a[href$="tab2"]').removeClass('MaintenanceTab');
		$('a[href$="tab2"]').addClass('active');
		$('#MaintenanceTabInactive').off('click');
		containerWidth();
	}

	function getEventById(id) {
		$.ajax({
			url : 'events/loadEventData',
			contentType : "application/json",
			async : false,
			data : {
				"eventId" : id
			},
			success : function(response) {
				eventData = response;
				if (eventData == "") {
					var text = "Event data not found, Add access rights";
					showAlert(text);
					//to.do.. add new;
				} else {
					$("#eventData").html("");
					$("#eventDataTable> tbody:last").append(
							$("#eventDataRows").render(eventData));
					containerWidth();
					$(".comboselect").combobox();
					//alert('${eventData.event}');
					//description.innerHTML = '${eventData.event}';
				}
			},
			error : function(errorResponse) {
				showError(errorResponse);
			}
		})
	}
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

	function openNew() {

	}
</script>

<script id="eventDataRows" type="text/x-jquery-tmpl">		
<tr>




<td colspan="2" align="center"><h1>{{>event}}</h1></td>

<td colspan="4" align="right">
<div>
					<form id="eventDataForm" name="eventDataForm" method="post" >
						
						<table id="eventDataTable" width="100%" cellpadding="10" cellspacing="10">
						<div class="search_filter">
							<div align="right">
							<tr align="right">
							
							<td align="right" colspan="6">
								<a href="#" class="saveRules blue_link" id="saveRules"> <img
						src="resources/images/save.png" name="save" width="16" height="22" />
										Save
								</a>
							</td>
							</tr>
							</div>
						</div>
							<tbody align="left" id="eventData">
							
							</tbody>
						</table>
					</form>
					</div>
</td>

</tr>
 					 <tr>
					
					<td align="right">Employee Category :</td>
						<td align="left">
                           <select name="employeeCategory" id="employeeCategory" class="comboselect">
								{{if employeeCategory =='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if employeeCategory =='N'}}
									<option value='N' selected="selected">No</option>
	                                <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
					<td></td>
					<td></td>
<td align="right">Grade :</td>
						<td align="left">
                           <select name="gradeId" id="gradeId" class="comboselect">
								{{if  gradeId=='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if  gradeId=='N'}}
									<option value='N' selected="selected">No</option>
		                            <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>	
					</tr>
					<tr>	
						 <td align="right">Ownership :</td>
						<td align="left">
                           <select name="ownership" id="ownership" class="comboselect">
								{{if ownership =='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if ownership =='N'}}
									<option value='N' selected="selected">No</option>
	                                <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
					
						<td align="right">Designation :</td>
						<td align="left">
                           <select name="designationId" id="designationId" class="comboselect">
								{{if designationId =='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if  designationId=='N'}}
									<option value='N' selected="selected">No</option>
	                                 <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>								
						<td align="right">Parent BG-BU :</td>
						<td align="left">
                           <select name="buId" id="buId" class="comboselect">
								{{if buId=='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if  buId=='N'}}
									<option value='N' selected="selected">No</option>
	                              	<option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>		
</tr>
<tr>	
						<td align="right">Base Location :</td>
						<td align="left">
                           <select name="payrollLocation" id="payrollLocation" class="comboselect">
								{{if  payrollLocation=='Y'}}
									<option value='Y' selected="selected">Yes</option>
                                <option value="N">No</option>
								{{/if}}
								{{if  payrollLocation=='N'}}
									<option value='N' selected="selected">No</option>
                                 <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>		
					
						<td align="right">Current Location :</td>
						<td align="left">
                           <select name="locationId" id="locationId" class="comboselect">
								{{if  locationId=='Y'}}
									<option value='Y' selected="selected">Yes</option>
									 <option value="N">No</option>
								{{/if}}
								{{if  locationId=='N'}}
									<option value='N' selected="selected">No</option>
	                                <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>	
						<td align="right">SRM :</td>
						<td align="left">
                           <select name="currentReportingTwo" id="currentReportingTwo" class="comboselect">
								{{if  currentReportingTwo=='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if  currentReportingTwo=='N'}}
									<option value='N' selected="selected">No</option>
	                                <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
</tr>
                    <tr>
						<td align="right">Current BG-BU :</td>
						<td align="left">
                           <select name="currentBuId" id="currentBuId" class="comboselect">
									{{if  currentBuId=='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if currentBuId =='N'}}
									<option value='N' selected="selected">No</option>
	                                 <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>		
						
						<td align="right">IRM :</td>
						<td align="left">
                            <select name="currentReportingManager" id="currentReportingManager" class="comboselect">
								{{if  currentReportingManager=='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if  currentReportingManager=='N'}}
									<option value='N' selected="selected">No</option>
	                                 <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>		
							<td align="right">Contact Number 2 :</td>
						<td align="left">
                          <select name="contactNumberTwo" id="contactNumberTwo" class="comboselect">
								{{if contactNumberTwo=='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if contactNumberTwo=='N'}}
									<option value='N' selected="selected">No</option>
	                                 <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
</tr>
                    <tr>
						<td align="right">Email Id :</td>
						<td align="left">
                           <select name="emailId" id="emailId" class="comboselect">
								{{if emailId=='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if emailId=='N'}}
									<option value='N' selected="selected">No</option>
	                                <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
					
						<td align="right">Contact Number 1 :</td>
						<td align="left">
                            <select name="contactNumber" id="contactNumber" class="comboselect">
								{{if contactNumber=='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if contactNumber=='N'}}
									<option value='N' selected="selected">No</option>
	                                 <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
					<td align="right">Appraisal Date 1 :</td>
						<td align="left">
                           <select name="lastAppraisal" id="lastAppraisal" class="comboselect">
								{{if lastAppraisal=='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if lastAppraisal=='N'}}
									<option value='N' selected="selected">No</option>
	                                 <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
</tr>
					<tr>
						<td align="right">DOJ :</td>
						<td align="left">
                          <select name="dateOfJoining" id="dateOfJoining" class="comboselect">
								{{if dateOfJoining=='Y'}}
									<option value='Y' selected="selected">Yes</option>
                                	<option value="N">No</option>
								{{/if}}
								{{if dateOfJoining=='N'}}
									<option value='N' selected="selected">No</option>
	                                 <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
					
						<td align="right">Confirmation Date :</td>
						<td align="left">
                           <select name="confirmationDate" id="confirmationDate" class="comboselect">
								{{if confirmationDate=='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if confirmationDate=='N'}}
									<option value='N' selected="selected">No</option>
	                                 <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
						<td align="right">Loan/Transfer Date From:</td>
						<td align="left">
                           <select name="transferDate" id="transferDate" class="comboselect">
								{{if transferDate=='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if transferDate=='N'}}
									<option value='N' selected="selected">No</option>
	                                <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
</tr>
								
					<tr>
						<td align="right">Appraisal Date 2 :</td>
						<td align="left">
                          <select name="penultimateAppraisal" id="penultimateAppraisal" class="comboselect">
								{{if penultimateAppraisal=='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if penultimateAppraisal=='N'}}
									<option value='N' selected="selected">No</option>
	                                <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
					
						<td align="right">Release Date :</td>
						<td align="left">
                           <select name="releaseDate" id="releaseDate" class="comboselect">
								{{if releaseDate=='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if releaseDate=='N'}}
									<option value='N' selected="selected">No</option>
                                 	<option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>	
						<td align="right">Loan/Transfer Date To:</td>
						<td align="left">
                           <select name="endTransferDate" id="endTransferDate" class="comboselect">
								{{if endTransferDate=='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if endTransferDate=='N'}}
									<option value='N' selected="selected">No</option>
	                                <option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
						
					</tr>	
							
					<tr>
						<td align="right">BGH Name :</td>
						<td align="left">
                           <select name="bGHName" id="bGHName" class="comboselect">
								{{if bGHName=='Y'}}
									<option value='Y' selected="selected">Yes</option>
									<option value="N">No</option>
								{{/if}}
								{{if bGHName=='N'}}
									<option value='N' selected="selected">No</option>
									<option value="Y">Yes</option>
								{{/if}}
                           </select>
						<td align="right">BUH Name:</td>
						<td align="left">
                          <select name="bUHName" id="bUHName" class="comboselect">
								{{if bUHName=='Y'}}
									<option value='Y' selected="selected">Yes</option>
	                                <option value="N">No</option>
								{{/if}}
								{{if bUHName=='N'}}
									<option value='N' selected="selected">No</option>
                                 	<option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
						<td align="right">HR Name:</td>
						<td align="left">
                           <select name="hRName" id="hRName" class="comboselect">
								{{if hRName=='Y'}}
									<option value='Y' selected="selected">Yes</option>
                                	<option value="N">No</option>
								{{/if}}
								{{if hRName=='N'}}
									<option value='N' selected="selected">No</option>
									 <option value="Y">Yes</option>								
								{{/if}}
                           </select>
                        </td>
					</tr>
					<tr>
						 <td align="right">BGH Comments:</td>
						 <td align="left">
                           <select name="bGHComments" id="bGHComments" class="comboselect">
								{{if bGHComments=='Y'}}
									<option value='Y' selected="selected">Yes</option>
                                	<option value="N">No</option>
								{{/if}}
								{{if bGHComments=='N'}}
									<option value='N' selected="selected">No</option>
                                 	<option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
						<td align="right">BUH Comments:</td>
						<td align="left">
                          <select name="bUHComments" id="bUHComments" class="comboselect">
								{{if bUHComments=='Y'}}
									<option value='Y' selected="selected">Yes</option>
                                	<option value="N">No</option>
								{{/if}}
								{{if bUHComments=='N'}}
									<option value='N' selected="selected">No</option>
 									<option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
						<td align="right">HR Comments:</td>
						<td align="left">
                           <select name="hRComments" id="hRComments" class="comboselect">
								{{if hRComments=='Y'}}
									<option value='Y' selected="selected">Yes</option>
									<option value="N">No</option>
								{{/if}}
								{{if hRComments=='N'}}
									<option value='N' selected="selected">No</option>
                                 	<option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
					</tr>
							
					<tr>
						<td align="right">BGH Comments Date:</td>
						<td align="left">
                           <select name="bGCommentsTimestamp" id="bGCommentsTimestamp" class="comboselect">
								{{if bGCommentsTimestamp=='Y'}}
									<option value='Y' selected="selected">Yes</option>
                                	<option value="N">No</option>
								{{/if}}
								{{if bGCommentsTimestamp=='N'}}
									<option value='N' selected="selected">No</option>
									<option value="Y">Yes</option>
								{{/if}}
                                 

                           </select>
                        </td>
						<td align="right">BU Comments Date:</td>
						<td align="left">
                           <select name="bUCommentsTimestamp" id="bUCommentsTimestamp" class="comboselect">
								{{if bUCommentsTimestamp=='Y'}}
									<option value='Y' selected="selected">Yes</option>
									<option value="N">No</option>
								{{/if}}
								{{if bUCommentsTimestamp=='N'}}
									<option value='N' selected="selected">No</option>
                                	<option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
						<td align="right">HR Comments Date:</td>
						<td align="left">
                           <select name="hRCommentsTimestamp" id="hRCommentsTimestamp" class="comboselect">
								{{if hRCommentsTimestamp=='Y'}}
									<option value='Y' selected="selected">Yes</option>
                                <option value="N">No</option>
								{{/if}}
								{{if hRCommentsTimestamp=='N'}}
									<option value='N' selected="selected">No</option>
									<option value="Y">Yes</option>
								{{/if}}
                           </select>
                        </td>
								
							</tr>
							
							<tr>
								<td>&nbsp;</td>
							</tr>





</script>



<div class="content-wrapper">
	<!--right section-->
	<div class="botMargin">
		<h1>Event</h1>
	</div>
	<div class="tab_section">
		<ul class='tabs'>
			<li><a href='#tab1'>List</a></li>
			<li><a class="MaintenanceTab" id="MaintenanceTabInactive"
				href='#tab2'>Event: Apply rules</a></li>
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
							<th width="8%" align="center" valign="middle">Event</th>
							<th width="8%" align="center" valign="middle">Description</th>
							<th width="8%" align="center" valign="middle">Edit</th>
							<th width="8%" align="center" valign="middle">Delete</th>
						</tr>
					</thead>
					<tbody id="example">
						<c:forEach var="eventColumn" varStatus="eventState"
							items="${event}">
							<tr id="${eventColumn.id}">
								<td align="center" valign="middle"><c:if
										test="${not empty eventColumn.id}">
										<a href="javascript:void(0)"
											onClick="openMaintainance('${eventColumn.id}');">${eventState.index+1}</a>
										<%--  <input type="hidden" value="${eventColumn.id}" id="myEventId" name="myEventId"></input> --%>
									</c:if></td>
								<%-- 
			               <td align="center" valign="middle">
			                 <input type="hidden"  value="${event.event}" id="eventTxt" name="eventTxt"/>
			               <%--  <c:if test="${not empty eventColumn.id}"> 
			                <input type="hidden" value="${eventColumn.id}" id="myEventId" name="myEventId"/>
			                <a href="javascript:void(0)" onClick="openMaintainance('${eventColumn.id}');" >${eventState.index+1}</a></c:if>
			                </td> --%>
								<td align="center" valign="middle"><c:if
										test="${empty eventColumn.event}">N.A.</c:if> <c:if
										test="${not empty eventColumn.event}">${eventColumn.event}</c:if>
								</td>

								<td align="center" valign="middle"><c:if
										test="${empty eventColumn.description}">N.A.</c:if> <c:if
										test="${not empty eventColumn.description}">${eventColumn.description}</c:if>
								</td>

								<td align="center" valign="middle"><a class="edit" href="">Edit</a>
								</td>
								<td align="center" valign="middle"><a class="delete"
									href="javascript:void(0);">Delete</a></td>

								<!-- adding checkBoxes for selecting options -->
								<!--  <td align="center" valign="niddle"> <input type="checkbox" class="noWidth"
								id="yashEmpId" name="yashEmpId" disabled="disabled"/>
							</td>   -->

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="clear"></div>
		</div>
		<div id="tab2" class="tab_div">
			<div align="right">

				<div class="form">
					<form id="eventDataForm" name="eventDataForm" method="post">

						<table id="eventDataTable" width="100%" cellpadding="4"
							cellspacing="4">
							<!-- <div class="search_filter">
							<div align="right">
							<tr align="right">
							
							<td align="right" colspan="6">
								<a href="#" class="saveRules blue_link" id="saveRules"> <img
						src="resources/images/save.png" name="save" width="16" height="22" />
										Save
								</a>
							</td>
							</tr>
							</div>
						</div> -->
							<tbody align="left" id="eventData">

							</tbody>
						</table>
					</form>
				</div>
			</div>
			<div class="clear"></div>
			<div></div>
		</div>
	</div>
	<!--right section-->
</div>
<div class="clear"></div>
 
        
