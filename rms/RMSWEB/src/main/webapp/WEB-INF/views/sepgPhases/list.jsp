<%@page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>

<%-- <spring:url value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}" var="ColReorder_js" />
<spring:url value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}" var="ColVis_js" />
<spring:url value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}" var="multiselect_js" />

<script src="${multiselect_js}" type="text/javascript"></script>
<script src="${ColReorder_js}" type="text/javascript"></script>
<script src="${ColVis_js}" type="text/javascript"></script> --%>

<style>
.element-count {
	background-color: #dff0d8;
	color: #3c763d;
}

.element-count-0 {
	background-color: #f2dede !important;
	color: #a94442 !important;
}
</style>



<%-- <div id="dialog" class="maintainanceDialog"
	style="background: white; width =400; height =400; color: red">
	
	<div id="dialogText">
		<b>Please Select Engagement Model
			</h3>
	</div>


<div id="">

		<c:if test="${not empty engagementmodels}">
			<select name="engagementmodelId" id="engagementmodelId"  multiple="multiple">
				<c:forEach var="engagementmodel" items="${engagementmodels}">
					<option value="${engagementmodel.id}">${engagementmodel.engagementModelName}</option>
				</c:forEach>
			</select>
		</c:if>
	</div>
</div> --%>




<div class="content-wrapper">
	<!--right section-->
	<div class="botMargin">
		<h1>SEPG PHASES</h1>
	</div>
	<div class="tab_section">
		<ul class='tabs'>
			<li><a href='#tab1' id="#tab1">SEPG List</a></li>
			<li><a class="MaintenanceTab" id="MaintenanceTabInactive"
				href='#tab3'>Maintenance</a></li>
		</ul>
		<div id='tab1' class="tab_div">
			<div class="search_filter_outer">
				<div class="search_filter search_filterLeft" style="left: 362px"></div>
				<div class="btnIcon" id="add">
					<a id="addNew" class="blue_link" href="javascript:void(0);"> <img
						width="16" height="22" src="resources/images/addUser.gif">
						Add New
					</a>

					<div class="clear"></div>
				</div>
			</div>
			<div class="tbl" id="tab1">
				<table
					class="display dataTable addNewRow alignCenter dataTbl marginTop40"
					id="example">
					<thead>
						<tr>
							<th width="20%" align="center" valign="middle">S. No.</th>
							<th width="20%" align="center" valign="middle">Phases</th>
							<th width="25%" align="center" valign="middle">Engagement
								Model</th>
							<th width="25%" align="center" valign="middle">Map Activity</th>
							<th width="10%" align="center" valign="middle">Edit</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="sepgPhase" varStatus="eng" items="${sepgPhases}">
							<tr id="${sepgPhase.id}">
								<td align="center" valign="middle"><c:if
										test="${not empty sepgPhase.id}"> ${eng.index+1} </c:if></td>
								<td align="center" valign="middle"><c:if
										test="${empty sepgPhase.phasesName}">N.A.</c:if> <c:if
										test="${not empty sepgPhase.phasesName}">${sepgPhase.phasesName}</c:if>
								</td>
								<td align="center" valign="middle"
									class="element-count element-count-${engagementModalCount[eng.index]}"><a
									href="" id="engagementModelId" class="engagementModel">Engagement
										Model (<c:if test="${not empty engagementModalCount}">${engagementModalCount[eng.index]}</c:if>)
								</a></td>
								<td align="center" valign="middle"
									class="element-count element-count-${activityCount[eng.index]}">
									<a href="" id="engagementModelId" class="engagementModel">Map
										Activity (<c:if test="${not empty activityCount}">${activityCount[eng.index]}</c:if>)
								</a>
								</td>
								<td align="center" valign="middle"><a class="edit" href="">Edit
										Phase</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="clear"></div>
		</div>

	</div>



	<!-- start of tab2 -->

	<div id='tab2' class="tab_div" style="display: none;">
		<table class="display dataTable addNewRow alignCenter" id="example">
			<thead>
				<tr>
					<th width="20%" align="center" valign="middle">Add Phases</th>
					<th width="25%" align="center" valign="middle">Create</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td align="center" valign="middle"><input type="text"
						name="PhasesName" id="PhasesNames"> <input type="hidden"
						name="phaseId" id="phaseId"></td>

					<td align="center" valign="middle"><a class="createPh"
						id="createPhase" href="#">Save</a></td>


				</tr>
			</tbody>
		</table>


	</div>

	<!-- end of tab2		 -->



	<div id='tab3' class="tab_div" style="display: none;">
		<table width="100%" border="0" cellspacing="1" cellpadding="0">
			<tr>
				<td><form name="form1" method="post" action="">


						<!-- Second part end-->
						<table width="99%" border="0" align="center" cellpadding="1"
							cellspacing="1">
							<tr>
								<td width="50%" valign="top">
									<table width="100%" border="0" cellpadding="2" cellspacing="1">
										<tr>
											<td width="58%" valign="top"><table border="0"
													align="left" cellpadding="2" cellspacing="1"
													class="borderlightgray">
													<tr align="center" valign="middle">
														<td align="left" valign="top"
															class="ent_elevenred defaultActivityHeading">SEPG
															Engagement Model From</td>
														<td align="left" valign="top" class="text">&nbsp;</td>
														<td align="left" valign="top"
															class="ent_elevenred defaultActivityHeading">SEPG
															Engagement Model To</td>
														<td align="left" valign="top" class="text">&nbsp;</td>
													</tr>
													<tr align="center" valign="middle">
														<td valign="top" class="text"><span
															id="selectionView2"> <select name=pdo1 size="7"
																multiple class="textField textFieldWidth" id="pdo1">
																	<c:forEach var="engagementModels" varStatus="Eng"
																		items="${engagementmodels}">
																		<option value="${engagementModels.id}">${engagementModels.engagementModelName}</option>

																	</c:forEach>
																	<%--  <c:forEach var="defaultActivity"
										items="${defaultActivity}">
										<option value="${defaultActivity.id}">${defaultActivity.activityName}</option>
									</c:forEach> --%>
															</select>
														</span></td>
														<td class="text"><table width="60%" border="0"
																cellpadding="2" cellspacing="7">
																<tr>
																	<td align="center" valign="middle"><input
																		name="btn_Exit" type="button" class="ent_button"
																		id="btn_Exit" value="&gt;&gt;"
																		onClick="move_item(pdo1, listMenu1)"></td>
																</tr>
																<tr>
																	<td align="center" valign="middle"><input
																		name="btn_Exit" type="button" class="ent_button"
																		id="btn_Exit" value="&lt;&lt;"
																		onClick="move_item(listMenu1,pdo1)"></td>
																</tr>
															</table></td>
														<td class="text"><table cellpadding="0"
																cellspacing="0" width="100%">
																<tr>
																	<td width="72%" valign="top"><span
																		id="selectionView"> <select name="listMenu1"
																			size="7" multiple class="textField textFieldWidth"
																			id="listMenu1" style="width: 150">
																		</select>
																	</span></td>
																</tr>
															</table></td>

													</tr>
												</table></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>

						<br> <br> <br>
						<!-- Second part end-->

						<!-- Third part start-->
						<table width="99%" border="0" align="center" cellpadding="1"
							cellspacing="1">
							<tr>
								<td width="50%" valign="top">
									<table width="100%" border="0" cellpadding="2" cellspacing="1">
										<tr>
											<td width="58%" valign="top"><table border="0"
													align="left" cellpadding="2" cellspacing="1"
													class="borderlightgray">
													<tr align="center" valign="middle">
														<td align="left" valign="top"
															class="ent_elevenred defaultActivityHeading">SEPG
															Map Activity From</td>
														<td align="left" valign="top" class="text">&nbsp;</td>
														<td align="left" valign="top"
															class="ent_elevenred defaultActivityHeading">SEPG
															Map Activity To</td>
														<td align="left" valign="top" class="text">&nbsp;</td>
													</tr>
													<tr align="center" valign="middle">
														<td valign="top" class="text"><span
															id="selectionView1"> <select name=pdo2 size="7"
																multiple class="textField textFieldWidth" id="pdo2">
																	<c:forEach var="activity" varStatus="act"
																		items="${activities}">

																		<option value="${activity.id}">${activity.activityName}</option>

																	</c:forEach>
																	<%--  <c:forEach var="defaultActivity"
										items="${defaultActivity}">
										<option value="${defaultActivity.id}">${defaultActivity.activityName}</option>
									</c:forEach> --%>
															</select>
														</span></td>
														<td class="text"><table width="60%" border="0"
																cellpadding="2" cellspacing="7">
																<tr>
																	<td align="center" valign="middle"><input
																		name="btn_Exit" type="button" class="ent_button"
																		id="btn_Exit" value="&gt;&gt;"
																		onClick="move_item(pdo2, listMenu2)"></td>
																</tr>
																<tr>
																	<td align="center" valign="middle"><input
																		name="btn_Exit" type="button" class="ent_button"
																		id="btn_Exit" value="&lt;&lt;"
																		onClick="move_item(listMenu2,pdo2)"></td>
																</tr>
															</table></td>
														<td class="text"><table cellpadding="0"
																cellspacing="0" width="100%">
																<tr>
																	<td width="72%" valign="top"><span
																		id="selectionView"> <select name="listMenu2"
																			size="7" multiple class="textField textFieldWidth"
																			id="listMenu2" style="width: 150">
																		</select>
																	</span></td>
																</tr>
															</table></td>

													</tr>
												</table></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>

						<!-- Third part end-->
						<br> <br> <br> <input type="button" id="saveSEPGid"
							value="Save SEPG" onclick="submitSepgForm()" /> <input
							type="button" id="updateSEPGid" value="Save/Update"
							onClick="updateSepgForm()" /> <input type="button"
							id="cancelSEPGid" value="Cancel" onclick="cancelSepgForm()" />
						<!-- <input type="button" id="deleteSEPGid" value="Delete" onClick="deleteSepgForm()" /> -->
					</form></td>
			</tr>
		</table>
	</div>

	<div class="clear"></div>

</div>







<script type="text/javascript" charset="utf-8">
	var oTable;
	var saveOpen;

	$(document)
			.ready(
					function() {

						$("#dialog").dialog({
							autoOpen : false
						});
						$("#dialog").dialog("option", "modal", true);
						$("#dialog").dialog("option", "title", "RMS");
						$("#dialog").dialog("option", "buttons", [ {
							text : "Ok",
							click : function() {
								$(this).dialog("close");

							}
						} ]);

						$(".tab_div").hide();
						$('ul.tabs a').click(function() {
							$('#MaintenanceTabInactive').text('Maintenance');
							$('#MaintenanceTabInactive').off('click');
							$(".tab_div").hide().filter(this.hash).show();
							$("ul.tabs a").removeClass('active');
							$('a[href$="tab2"]').addClass('MaintenanceTab');
							$(this).addClass('active');
							return false;
						}).filter(':first').click();

						$('#MaintenanceTabInactive').off('click');
						$(document).on("click", '#addNew', function() {
							$("#PhasesNames").val("");
							$("ul.tabs a").removeClass('active');
							$('a[href$="tab2"]').removeClass('MaintenanceTab');
							$(".tab_div").hide().next("#tab2".hash).show();
							$('a[href$="tab2"]').addClass('active');
							$("#tab2").show();
							$("#tab3").hide();

						});

						$(document).on("click", '#createPhase', function() {

							savephase();

						});

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

						$('#example')
								.delegate(
										'a.engagementModel',
										'click',
										function(e) {
											var nRow = $(this).parents('tr')[0];
											var id = $(nRow).attr('id');
											e.preventDefault();
											$
													.ajax({
														type : 'GET',
														url : 'sepgPhases/sepgListByPhaseId/'
																+ id,
														contentType : "application/json",
														dataType : "json",
														success : function(data) {
															 
															if (typeof data.sepgPhases.engageFrom != "undefined") {
																var htmlVarEngageFrom = '<select name=pdo1 size="7" multiple class="textField textFieldWidth" id="pdo1">';
																$
																		.each(
																				data.sepgPhases.engageFrom,
																				function(
																						key,
																						value) {
																					htmlVarEngageFrom += '<option value='+key+'>'
																							+ value
																							+ '</option>';
																				});
																htmlVarEngageFrom += '</select>';
																$(
																		'#selectionView2')
																		.html(
																				htmlVarEngageFrom);
															}

															if (typeof data.sepgPhases.engageTo != "undefined") {
																var htmlVarEngageTo = '';
																$
																		.each(
																				data.sepgPhases.engageTo,
																				function(
																						key,
																						value) {
																					htmlVarEngageTo += '<option value='+key+'>'
																							+ value
																							+ '</option>';
																				});
																$('#listMenu1')
																		.html(
																				htmlVarEngageTo);
															}

															if (typeof data.sepgPhases.activityFrom != "undefined") {
																var htmlVarActivityFrom = '<select name=pdo2 size="7" multiple class="textField textFieldWidth" id="pdo2">';
																$
																		.each(
																				data.sepgPhases.activityFrom,
																				function(
																						key,
																						value) {
																					htmlVarActivityFrom += '<option value='+key+'>'
																							+ value
																							+ '</option>';
																				});
																htmlVarActivityFrom += '</select>';
																$(
																		'#selectionView1')
																		.html(
																				htmlVarActivityFrom);
															}

															if (typeof data.sepgPhases.activityTo != "undefined") {
																var htmlVarActivityTo = '';
																$
																		.each(
																				data.sepgPhases.activityTo,
																				function(
																						key,
																						value) {
																					htmlVarActivityTo += '<option value='+key+'>'
																							+ value
																							+ '</option>';
																				});
																$('#listMenu2')
																		.html(
																				htmlVarActivityTo);
															}

															$("ul.tabs a")
																	.removeClass(
																			'active');
															$(".tab_div")
																	.hide()
																	.next(
																			"#tab3".hash)
																	.show();
															$('a[href$="tab3"]')
																	.addClass(
																			'active');
															$("#tab1").hide();
															$("#saveSEPGid")
																	.hide();
															$("#updateSEPGid")
																	.show();
															$(
																	"#MaintenanceTabInactive")
																	.html(
																			data.phaseName);
															$("#phaseId").val(
																	id);

														},
														error : function(
																errorResponse) {
															showError(errorResponse);
														}

													});

										});

						function editRow(oTable, nRow, add) {
							//alert("erow");
							startProgress();
							var aData = oTable.fnGetData(nRow);
							var jqTds = $('>td', nRow);
							jqTds[0].innerHTML = '<input type="text" value="'
									+ aData[0]
									+ '"  readonly="readonly" onfocus="this.blur() >';
							jqTds[1].innerHTML = '<input type="text" value="'+aData[1]+'">';
							jqTds[2].innerHTML = aData[2];
							jqTds[3].innerHTML = aData[3];
							jqTds[4].innerHTML = '<a class="edit" href="">Save</a>/<a class="cancel" href="">Cancel</a>';
							stopProgress();

						}

						$('#example')
								.delegate(
										'a.cancel',
										'click',
										function(e) {
											startProgress();
											saveOpen = false;
											nEditing = null;
											var nRow = $(this).parents('tr')[0];
											var aData = oTable.fnGetData(nRow);

											var jqTds = $('>td', nRow);
											jqTds[0].innerHTML = aData[0];
											jqTds[1].innerHTML = aData[1];
											jqTds[2].innerHTML = aData[2];
											jqTds[3].innerHTML = aData[3];
											jqTds[4].innerHTML = '<a class="edit" href="">Edit Phase</a>';
											stopProgress();
											e.preventDefault();
										});
						$('#example')
								.delegate(
										'a.edit',
										'click',
										function(e) {
											if (saveOpen
													&& this.innerHTML != "Save") {
												// Added for task # 216 - Start
												alert("Please enter and save the data");
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
												/* alert("ifff 2"); */
												/* Currently editing - but not this row - restore the old before continuing to edit mode */
												restoreRow(oTable, nEditing);
												var add = false;
												editRow(oTable, nRow, add);
												nEditing = nRow;

											} else if (nEditing == nRow
													&& this.innerHTML == "Save") {
												/* alert("ifff 33"); */
												/* Editing this row and want to save it */
												saveRow(oTable, nEditing);
												//saveOpen = false;
												//nEditing = null;
											} else {
												/* No edit in progress - let's start one */
												var add = false;
												editRow(oTable, nRow, add);
												nEditing = nRow;
											}

										});

						function saveRow(oTable, nRow) {

							/* alert("save row function"); */
							var id = $(nRow).attr('id');
							var jqInputs = $('input', nRow);
							var aData = jqInputs;
							for (var i = 0; i < jqInputs.length; i++) {
								aData[i] = jqInputs[i].value;
							}
							var sData = $('*', oTable.fnGetNodes())
									.serializeArray();
							sData.push({
								name : "PhasesName",
								value : aData[0]
							});
							$
									.ajax({
										type : 'POST',
										url : 'sepgPhases/editSEPG/' + id + '/'
												+ aData[0],
										contentType : "application/json",
										dataType : "json",
										success : function(data) {
											 
											if (data.result == true) {
												showSuccess("Phase "
														+ data.phaseName
														+ " has been saved successfully.");
												saveOpen = false;
												nEditing = null;
												var aData = oTable
														.fnGetData(nRow);
												var jqTds = $('>td', nRow);
												jqTds[0].innerHTML = aData[0];
												aData[1] = data.phaseName;
												jqTds[1].innerHTML = aData[1];
												jqTds[2].innerHTML = aData[2];
												jqTds[3].innerHTML = aData[3];
												jqTds[4].innerHTML = '<a class="edit" href="">Edit Phase</a>';
											} else {
												saveOpen = true;
												showError("Phase "
														+ data.phaseName
														+ " already Exist Please enter another SEPG Phase");
											}
											/* if(typeof data.sepgPhases.engageFrom != "undefined"){
											 var htmlVarEngageFrom = '<select name=pdo1 size="7" multiple class="textField textFieldWidth" id="pdo1">';
											 $.each(data.sepgPhases.engageFrom, function(key,value){
												    htmlVarEngageFrom+='<option value='+key+'>'+value+'</option>';
												});  
											 htmlVarEngageFrom+='</select>';
											 $('#selectionView2').html(htmlVarEngageFrom);
											}
											
											if(typeof data.sepgPhases.engageTo != "undefined"){
											 var htmlVarEngageTo = '';
											 $.each(data.sepgPhases.engageTo, function(key,value){
												    htmlVarEngageTo+='<option value='+key+'>'+value+'</option>';
												});  
											 $('#listMenu1').html(htmlVarEngageTo);
											}
											
											if(typeof data.sepgPhases.activityFrom != "undefined"){
											 var htmlVarActivityFrom = '<select name=pdo2 size="7" multiple class="textField textFieldWidth" id="pdo2">';
											 $.each(data.sepgPhases.activityFrom, function(key,value){
												    htmlVarActivityFrom+='<option value='+key+'>'+value+'</option>';
												});  
											 htmlVarActivityFrom+='</select>';
											 $('#selectionView1').html(htmlVarActivityFrom);
											}
											
											 if(typeof data.sepgPhases.activityTo != "undefined"){
											 var htmlVarActivityTo = '';
											 $.each(data.sepgPhases.activityTo, function(key,value){
												    htmlVarActivityTo+='<option value='+key+'>'+value+'</option>';
												});  
											 $('#listMenu2').html(htmlVarActivityTo);
											 } */

											/* $("ul.tabs a")
													.removeClass('active');
											$(".tab_div").hide().next(
													"#tab3".hash).show();
											$('a[href$="tab3"]').addClass(
													'active');
											$("#tab1").hide();
											$("#saveSEPGid").hide();
											$("#MaintenanceTabInactive").html(
													aData[0]);
											$("#phaseId").val(id); */

										},
										error : function(errorResponse) {
											 
											showError(errorResponse);
										}

									});

						}
						//Added by Pratyoosh//

						oTable = $('#example').dataTable({
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
							} ],
							"bSortCellsTop" : true,
						});
					});
	
	//Added by Pratyoosh//
	/* ------------------------------------------------- */

	//Start Wicket palette 		
	function move_item(from, to) {

		var f;
		var SI;
		if (from.options.length > 0) {
			for (i = 0; i < from.length; i++) {
				if (from.options[i].selected) {
					SI = from.selectedIndex;
					f = from.options[SI].index;
					to.options[to.length] = new Option(from.options[SI].text,
							from.options[SI].value);
					from.options[f] = null;
					i--;
				}
			}
		}
	}

	function moveUp(lst) {
		if (lst.selectedIndex > 0) {
			var SI = lst.selectedIndex;
			var tempOpt = new Option(lst.options[SI].text,
					lst.options[SI].value);
			lst.options[SI].text = lst.options[SI - 1].text;
			lst.options[SI].value = lst.options[SI - 1].value;
			lst.options[SI - 1].text = tempOpt.text;
			lst.options[SI - 1].value = tempOpt.value;
			lst.options[SI - 1].selected = true;
		}
	}

	function moveDown(lst) {
		if (lst.selectedIndex < lst.options.length - 1) {
			var SI = lst.selectedIndex;
			var tempOpt = new Option(lst.options[SI].text,
					lst.options[SI].value);
			lst.options[SI].text = lst.options[SI + 1].text;
			lst.options[SI].value = lst.options[SI + 1].value;
			lst.options[SI + 1].text = tempOpt.text;
			lst.options[SI + 1].value = tempOpt.value;
			lst.options[SI + 1].selected = true;
		}

	}
	//End

	function savephase() {
		var phaseName = $('#PhasesNames').val();
		if (phaseName != '') {
			var successMsg = "Phase '" + phaseName + "' saved successfully";
			var phaseId = '';
			$
					.ajax({
						type : 'POST',
						url : 'sepgPhases/savePhase/' + phaseName,
						contentType : "application/json; charset=utf-8",
						data : {
							"phaseName" : phaseName
						},

						success : function(data) {
							 
							if (data.result == true) {

								if (typeof data.sepgPhases.engageFrom != "undefined") {
									var htmlVarEngageFrom = '<select name=pdo1 size="7" multiple class="textField textFieldWidth" id="pdo1">';
									$
											.each(
													data.sepgPhases.engageFrom,
													function(key, value) {
														htmlVarEngageFrom += '<option value='+key+'>'
																+ value
																+ '</option>';
													});
									htmlVarEngageFrom += '</select>';
									$('#selectionView2')
											.html(htmlVarEngageFrom);
								}

								if (typeof data.sepgPhases.engageTo != "undefined") {
									var htmlVarEngageTo = '';
									$
											.each(
													data.sepgPhases.engageTo,
													function(key, value) {
														htmlVarEngageTo += '<option value='+key+'>'
																+ value
																+ '</option>';
													});
									$('#listMenu1').html(htmlVarEngageTo);
								}

								if (typeof data.sepgPhases.activityFrom != "undefined") {
									var htmlVarActivityFrom = '<select name=pdo2 size="7" multiple class="textField textFieldWidth" id="pdo2">';
									$
											.each(
													data.sepgPhases.activityFrom,
													function(key, value) {
														htmlVarActivityFrom += '<option value='+key+'>'
																+ value
																+ '</option>';
													});
									htmlVarActivityFrom += '</select>';
									$('#selectionView1').html(
											htmlVarActivityFrom);
								}

								if (typeof data.sepgPhases.activityTo != "undefined") {
									var htmlVarActivityTo = '';
									$
											.each(
													data.sepgPhases.activityTo,
													function(key, value) {
														htmlVarActivityTo += '<option value='+key+'>'
																+ value
																+ '</option>';
													});
									$('#listMenu2').html(htmlVarActivityTo);
								}
								phaseId = data.phaseId;
								 
								var rowIndex = $('#example')
										.dataTable()
										.fnAddData(
												[
														'' + data.rawCount + '',
														'' + data.phaseName
																+ '',
														'<a href="javascript:void(0)" id="engagementModelId" class="engagementModel">Engagement Model (0)</a>',
														'<a href="javascript:void(0)" id="engagementModelId" class="engagementModel">Map Activity (0)</a>',
														'<a class="edit" href="">Edit Phase</a>' ]);
								var row = $('#example').dataTable().fnGetNodes(
										rowIndex);
								$(row).attr('id', data.phaseId);
								$("ul.tabs a").removeClass('active');
								$('a[href$="tab2"]').removeClass(
										'MaintenanceTab');
								$(".tab_div").hide().next("#tab2".hash).show();
								$('a[href$="tab2"]').addClass('active');
								$("#tab3").show();
								$("#tab2").hide();
								$("#deleteSEPGid").hide();
								$("#updateSEPGid").hide();
								$("#saveSEPGid").show();
								$("#MaintenanceTabInactive").html(
										data.phaseName);
								$("#phaseId").val(data.phaseId);
								showSuccess(successMsg);
							} else {
								showError("Phase "
										+ data.phaseName
										+ " already Exist Please enter another SEPG Phase");
								$('#PhasesName').focus();
							}
						},
						error : function(errorResponse) {
							showError(errorResponse);
						}
					});

		} else {
			showError("Phase can not be blank");
		}
	}

	function deleteSepgForm() {

		var id = $("#phaseId").val();

		$.ajax({
			type : 'POST',
			url : 'sepgPhases/deleteSEPG/' + id,
			contentType : "application/json; charset=utf-8",
			success : function() {
				showSuccess("Phase" + id + "deleted succesfully");
				window.location.reload();

			},
			error : function(errorResponse) {
				showError(errorResponse);
			}
		});

	}

	function updateSepgForm() {

		// get engagementmodAl list
		var engagement = document.getElementById("listMenu1");
		var engagementId = new Array(engagement.options.length);
		var engagementText = new Array(engagement.options.length);

		if (engagement.options.length > 0) {
			for (i = 0; i < engagement.length; i++) {
				engagementText[i] = engagement.options[i].text;
				engagementId[i] = engagement.options[i].value;
			}

		} else {
			alert("Please Select Enagagement Modal");
			return false;
		}
		//end

		// get activity modal list
		var activity = document.getElementById("listMenu2");
		var activityId = new Array(activity.options.length);
		var activityText = new Array(activity.options.length);

		if (activity.options.length > 0) {
			for (i = 0; i < activity.length; i++) {
				activityText[i] = activity.options[i].text;
				activityId[i] = activity.options[i].value;
			}

		} /* else {
					alert("Please Select SEPG Activity");
					return false;
				} */

		// end

		var phaseId = $("#phaseId").val();
		$.ajax({

			url : 'sepgPhases/updateSEPG',
			contentType : "application/json; charset=utf-8",
			async : false,
			data : "phaseId=" + phaseId + "&engagementIdList=" + engagementId
					+ "&activityIdList=" + activityId,

			success : function(data) {
				showSuccess("Phase" + phaseId + "updated Sucessfully");
				window.location.reload();
			},
			error : function(errorResponse) {
				showError(errorResponse);
			}

		});
	}

	//start form submit
	function submitSepgForm() {

		// get engagementmodAl list
		var engagement = document.getElementById("listMenu1");
		var engagementId = new Array(engagement.options.length);
		var engagementText = new Array(engagement.options.length);

		if (engagement.options.length > 0) {
			for (i = 0; i < engagement.length; i++) {
				engagementText[i] = engagement.options[i].text;
				engagementId[i] = engagement.options[i].value;
			}

		} else {
			alert("Please Select Enagagement Modal");
			return false;
		}
		//end

		// get activity modal list
		var activity = document.getElementById("listMenu2");
		var activityId = new Array(activity.options.length);
		var activityText = new Array(activity.options.length);

		if (activity.options.length > 0) {
			for (i = 0; i < activity.length; i++) {
				activityText[i] = activity.options[i].text;
				activityId[i] = activity.options[i].value;
			}

		}/*  else {
							alert("Please Select SEPG Activity");
							return false;
						} */

		// end

		//start ajax call 
		var phaseId = $("#phaseId").val();
		var successMsg = "SEPG details have been saved successfully";
		$.ajax({

			url : 'sepgPhases/saveSEPG',
			contentType : "application/json; charset=utf-8",
			async : false,
			data : "phaseId=" + phaseId + "&engagementIdList=" + engagementId
					+ "&activityIdList=" + activityId,

			success : function(data) {
				showSuccess(successMsg);
				window.location.reload();
			},
			error : function(errorResponse) {
				showError(errorResponse);
			}

		});

		//end ajax call
	}

	function cancelSepgForm() {

		var phaseId = $("#phaseId").val();
		//alert(phaseId);
		$.ajax({

			url : 'sepgPhases/cancelSepg',
			contentType : "application/json; charset=utf-8",
			async : false,
			data : "phaseId=" + phaseId,

			success : function(data) {

				window.location.reload();

			},
			error : function(errorResponse) {
				showError(errorResponse);
			}

		});

	}
</script>