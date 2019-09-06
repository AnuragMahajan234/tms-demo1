<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
<spring:url
	value="/resources/js-framework/fancy-box/jquery.fancybox.pack.js?ver=${app_js_ver}"
	var="jquery_fancybox" />


<html>
<head>
<script src="${multiselect_js}" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />
<title>Insert title here</title>

<style>
.dataTables_filter{
top:-4px !important;
}

</style>


<script id="orgTableRows" type="text/x-jsrender">
 					
{{for orgHierarchies}}

<tr id="{{>id}}">

<td align="center" valign="middle">
<input type="hidden" value="{{>name}}"  id="name"/>
<a href="#" class="child" >{{>name}}</a>
 <input type="hidden" value="{{>id}}" id="myid"/>

</td> 
<td align="center" valign="middle">
{{>description}}
</td>

<td align="center" valign="middle">
{{if employeeId != null}}
<input type="hidden" value="{{>employeeId.employeeId}}" id="empId"/>{{>employeeId.employeeName}}
{{/if}}
</td>

<td align="center" valign="middle">
 {{>creationDate}}
</td >
<td align="center" valign="middle">
			                <a class="edit" href="#">Edit</a>
			                </td>
			                <td align="center" valign="middle">
			               
			              {{if parentDeactive}}
							N.A.
   {{else}}
                            {{if active == false}}
			                <a class="activate" href="" id="${tableColumn.id}">Activate</a>
			             {{else}}
		 
			                <a class="deactivate" href="" id="${tableColumn.id}">DeActivate</a>
			              
			              {{/if}}
{{/if}}
			                </td>
<td align="center" valign="middle">

{{if moveLink == false}}
			                N.A.
{{else}}
			               <a href="orghierarchys/showList?id={{>id}}&orgName={{>name}} " class="moveOrg">Move</a>

 
{{/if}}
			                </td>
    </tr>
 

 
 {{/for}}

<input type="hidden" value="{{>id}}" id="parentid" name="parentid"/>	

 
 
			        

</script>
<script id="movieTemplate" type="text/x-jquery-tmpl">
<li id="{{>id}}">
<a href="">{{>name}}</a>  
</li>
</script>

</head>
<body>
	<form id="orgForm" class="content-wrapper">
		<div class="botMargin">
			<h1>BG/BU</h1>
		</div>

		<div id="change"><font color="blue"> ORGANIZATION</font></div>
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

				<table class="dataTable dataTbl cust-dataTbl" id="orgTable">
					<thead>
						<tr>
							<th width="4%" valign="middle" style="text-align:center">Name</th>
							<th width="4%" valign="middle" style="text-align:center">Description</th>
							<!-- Start - Heading change for BG and BU respectively - Digdershika -->
							<!-- <th width="4%" align="center" valign="middle">BG/BU Head</th> -->							
							<th width="4%" id="isBG" valign="middle" style="text-align:center">BG Head</th>
							<th width="4%" id="isBU" valign="middle" style="text-align:center">BU Head</th>
							<!-- Start - Heading change for BG and BU respectively - Digdershika -->
							<th width="3%" valign="middle" style="text-align:center">Creation Date</th>
							<th width="4%" valign="middle" style="text-align:center">Edit</th>
							<th width="4%" valign="middle" style="text-align:center">Activate/Deactivate</th>
							<th width="4%" valign="middle" style="text-align:center">Move</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach var="tableColumn" items="${orglist.orgHierarchies}">

							<tr id="${tableColumn.id}">


								<td width="4%" align="center" valign="middle"><input
									type="hidden" value="${tableColumn.name}" id="name" />  <a href="#" class="child"> 
									${tableColumn.name} </a>  <input type="hidden"
									value="${tableColumn.id}" id="myid" /></td>
								<td width="4%" align="center" valign="middle">${tableColumn.description}</td>
								<td width="4%" align="center" valign="middle">
								<input type="hidden" value="${tableColumn.employeeId.employeeId}" id="empId"/>${tableColumn.employeeId.employeeName}</td> <!-- sarang added -->
								<fmt:formatDate value="${tableColumn.creationDate}"
									var="parsedCreationDate" pattern="yyyy-MM-dd hh:mm:ss a" />
								<td width="3%" align="center" valign="middle">${parsedCreationDate}</td>
								<td width="4%" align="center" valign="middle"><a
									class="edit" href="#">Edit</a></td>
								<td width="4%" align="center" valign="middle"><c:choose>
										<c:when test="${tableColumn.active==false}">
											<a class="activate" href="" id="${tableColumn.id}">Activate</a>
										</c:when>
										<c:otherwise>
											<a class="deactivate" href="" id="${tableColumn.id}">DeActivate</a>
										</c:otherwise>
									</c:choose></td>
								<td width="4%" align="center" valign="middle"><c:choose>
										<c:when test="${tableColumn.moveLink==false}">
			                N.A.
    </c:when>

										<c:otherwise>
											<a
												href="orghierarchys/showList?id=${tableColumn.id}&orgName=${tableColumn.name}"
												class="moveOrg"> Move</a>
										</c:otherwise>
									</c:choose></td>
							</tr>

						</c:forEach>
					</tbody>
				</table>
				<input type="hidden" value="${orglist.id}" id="parentid"
					name="parentid" /> <input type="hidden" value="${orglist.name}"
					id="parentName" name="parentName" />
			</div>
			<div class="clear"></div>
		</div>

	</form>
</body>

<script type="text/javascript">
	var addNewAlloc = false;
	var allDataJSON;
	var childArray = new Array();
	var childIdArray = new Array();
	function getPath(obj) {
		var s = "";
		if (obj.parentId != null) {
			s = getPath(obj.parentId) + '<a href="#" id=' + obj.parentId.id
					+ ' onclick="getdat(' + obj.parentId.id + ')">' + ">"
					+ obj.parentId.name + '</a>';
		}
		return s;

	}
	function getCurrentPath(data) {
		return getPath(data);
	}
	function generateList(data) {
		//alert("caleed" + data);
		'<select><option value=">' + data + '"</select>';

	}
	$('#moveLink').on("click", function() {
		//alert("My testing2");
		var id = $(this).next("input#ids").val();
		var DropDownList;
		//alert("id==" + id);

		$.ajax({
			async : false, // !important
			url : "allList",
			data : "id=" + id,
			success : function(json) {
				alert("success" + id);

				$("#dropDown_" + id).append($("#movieTemplate").render(json));

				// .appendTo( "#dropDown_"+id );

			},
			error : function(json) {
				alert("erro");
			}
		});

	});

	var oTable;
	var breadcrumb;

	var indexChild = 0;
	function getDropDownValues(allDataJSONVar) {
		alert("called" + allDataJSONVar.id);
		if (allDataJSONVar != null) {
			alert(allDataJSONVar.name);
		}
		if (allDataJSONVar == null) {

			return;
		}
		if (allDataJSONVar.orgHierarchies != null) {
			alert(allDataJSONVar.orgHierarchies.length);
			for (var i = 0; i < allDataJSONVar.orgHierarchies.length; i++) {
				childIdArray[indexChild] = allDataJSONVar.orgHierarchies[i].id;
				childArray[indexChild] = allDataJSONVar.orgHierarchies[i].name;
				alert("ddddd" + childIdArray[indexChild]);
				getDropDownValues(allDataJSONVar.orgHierarchies[i]);
				indexChild++;
			}
		}

	}
	function showOrganization(data) {
		alert("success");
	}

	function showOrganizationHierarchies(data) {

		document.getElementById("change").innerHTML = getCurrentPath(data)
				+ "<font color='blue'> " + data.name;
		allDataJSON = data;
		var test = data;
		if (data.parentId == null || data.parentId.id == null) {

			allDataJSON = data;
		}

		indexChild = 0;

		var settings = $.fn.dataTableSettings;

		for (var i = 0, iLen = settings.length; i < iLen; i++) {

			if (settings[i].nTable == $("#orgTable")[0]) {

			} else
				alert("no");

		}

		if (oTable != null) {
			oTable.fnClearTable();

		}

		document.getElementById("parentid").value = data.id;

		$('#orgTable').dataTable().fnDestroy();
		$("#orgTable > tbody:last").append($("#orgTableRows").render(test));
		oTable = $('#orgTable').dataTable({
			//"sDom": 'RC<"clear">lfrtip<"top">rt<"bottom"ip<"clear">',
			"sPaginationType" : "full_numbers",
			"bStateSave" : false, 
			"aoColumnDefs" : [
			//{ "bVisible": false, "aTargets": [] }
			],
			"oLanguage" : {
				"sSearch" : "Search:"
			},
			"aoColumns" : [ {}, {}, {}, {}, { // sarang  one extra added
				"bSortable" : false
			}, {
				"bSortable" : false
			}, {
				"bSortable" : false
			} ],
			"bSortCellsTop" : true,

		});
		/* sarang code added to have uniform funtion of search input box for hiding add new div while using search filter*/
		$("#orgTable_filter").find('input').bind(
				'change paste keyup',
				function() {
					var srch = $("#orgTable_filter").find('input')
							.val();
					if (null == srch || $.trim(srch) == '') {
						$("#add").show();
					} else {
						$("#add").hide();
					}

				});
		/* sarang code added end*/
	}
	
	$(document).ready(
			function() {

				oTable = $('#orgTable').dataTable({
					//"sDom": 'RC<"clear">lfrtip<"top">rt<"bottom"ip<"clear">',
					"sDom" : '<"top"lfip>rt<"bottom"ip<"clear">',
					"sPaginationType" : "full_numbers",
					"bStateSave" : false,
					"aoColumnDefs" : [
					//{ "bVisible": false, "aTargets": [] }
					],
					"oLanguage" : {
						"sSearch" : "Search:"
					},
					"aoColumns" : [ {}, {}, {}, {}, {  // sarang  one extra added
						"bSortable" : false
					}, {
						"bSortable" : false
					}, {
						"bSortable" : false
					} ],
					"bSortCellsTop" : true,

				});

				$(".various").fancybox({
					fitToView : true,
					autoSize : true,
					closeClick : true,
					type : 'ajax'
				});

				//	alert("gdgd"+$("#orgTable").fnSettings().nTBody);
				
				/* sarang code added*/
				
				$("#orgTable_filter").find('input').bind(
						'change paste keyup',
						function() {
							var srch = $("#orgTable_filter").find('input')
									.val();
							if (null == srch || $.trim(srch) == '') {
								$("#add").show();
							} else {
								$("#add").hide();
							}

						});
			});

	function getInputValue(str) {
		// for internet explorer
		var index1 = str.indexOf("<INPUT");

		// for mozilla
		var index = str.indexOf("<input");
		/* if(index < 0)return null;
		str = str.substr(index , str.length);
		return $(str).val(); */
		if (index < 0 && index1 < 0) {
			return "";
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

	var oldDescription = "";
	var oldBGName = "";
	var cells = [];
	function editRow(oTable, nRow) {
		cells = [];
		var rows = $("#orgTable").dataTable().fnGetNodes();
		for (var i = 0; i < rows.length; i++) {
			// Get HTML of 3rd column (for example)
			cells.push(getInputValue($(rows[i]).find("td:eq(0)").html()));
		}
		startProgress();

		$('#orgTable tr td').each(function() {
			// alert("this is ---"+($(this).html()));
		});
		// oTable.fnReloadAjax()

		var aData = oTable.fnGetData(nRow);
		oldDescription = aData[1];
		var jqTds = $(">td", nRow);
		var curIndex = getInputValue(aData[2]);
		//alert(curIndex);
		if (jqTds.length < 1)
			return;
		//added by sarang start
		if(addNewAlloc==true) {
			jqTds[0].innerHTML = '<center><input type="text" value="'
				+ getInputValue(aData[0]) + '"></center>'; //made center aligned as per Task#205
			oldBGName = getInputValue(aData[0]);
			jqTds[1].innerHTML = '<center><input type="text" value="'+aData[1]+'"></center>';
			
			jqTds[2].innerHTML = "<select  class='comboselect changeTest ' id =\"employeeNameEditId"+curIndex+"\" name=\"employeeId.employeeId\">"+ 
				<c:forEach var="resource" items="${resources}">
					"<option value=\"${resource.employeeId}\">${resource.employeeName}[${resource.yashEmpId}]</option>"+
				</c:forEach>
		 	+"</select>";
		 	jqTds[4].innerHTML = '<center><a class="edit" href="#">Save</a>/<a id="cancel" class="delAddedRow" href="">Cancel</a><center>';
			
		} else {
			/* original code keep outside your start*/
			//alert(employeeId.employeeId);
			jqTds[0].innerHTML = '<center><input type="text" value="'
				+ getInputValue(aData[0]) + '"></center>'; //made center aligned as per Task#205
			oldBGName = getInputValue(aData[0]);
			
			jqTds[1].innerHTML = '<center><input type="text" value="'+aData[1]+'"></center>'; //sarang added
			
			jqTds[2].innerHTML = "<select  class='comboselect changeTest' id =\"employeeNameEditId"+curIndex+"\" name=\"employeeId.employeeId\">"+ 
			<c:forEach var="resource" items="${resources}">
				"<option value=\"${resource.employeeId}\">${resource.employeeName}[${resource.yashEmpId}]</option>"+
			</c:forEach>
	 		+"</select>"; //sarang added
	 		
	 		/* '<input id="employeeId.employeeId" type="hidden" value="'+curIndex+'">' */
			
			/* for (var i = 1; i < aData.length - 4; i++) {
			jqTds[i].innerHTML = '<center><input type="text" value="'+aData[i]+'"></center>'; //made center aligned as per Task#205
			} */
			jqTds[4].innerHTML = '<center><a class="edit" href="#">Save</a>/<a id="cancel" class="delAddedRow" href="">Cancel</a><center>';//made center aligned as per Task#205
			/* original code keep outside your end*/
			
		}
		//added by sarang end
		//setTimeout(function(){  ; },200);
		$("#employeeNameEditId"+curIndex).val(curIndex);
		$( ".comboselect" ).combobox();
		stopProgress();
	}

	function getdat(val) {

		$.getJSON("orghierarchys/" + val, showOrganizationHierarchies);

	}
	/* Start - isBU and isBG are th ids and to display different heading display based on click of row - Digdershika*/
	$("#isBU").css("display","none");
	$('#orgTable').delegate('.child',"click", function() {
		$("#isBU").css("display","table-cell");
		$("#isBG").css("display","none");
		var id = $(this).next("input#myid").val();

		$.getJSON("orghierarchys/" + id, showOrganizationHierarchies);

	});
	/* End - isBU and isBG are th ids and to display different heading display based on click of row - Digdershika*/
	$(".dataTabl tbody").find("tr:even").addClass("even");
	$(".dataTabl tbody").find("tr:odd").addClass("odd");

	$('#anchor').on("click", function() {
		//alert("My testing1");
		var id = $(this).next("input#myid").val();
		//alert("id testing");

		$.getJSON(id, showOrganizationHierarchies);

	});

	$('#orgTable').delegate('a.activate', 'click', function(e) {

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
			url : "orghierarchys/" + true + "/" + id,
			//data:   id ,
			success : function(data) {
				var parentId = document.getElementById("parentid").value;

				refreshGrid(parentId);
				showSuccess("successfully Activated");

			}
		});

	});

	$('#orgTable')
			.delegate(
					'a.deactivate',
					'click',
					function(e) {

						var resourceList = true;
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
								.getJSON(
										"orghierarchys/findResourceList/" + id,
										function(data) {

											if (!data.result) {

												$
														.ajax({
															async : true,

															contentType : "application/json",
															url : "orghierarchys/"
																	+ false
																	+ "/" + id,

															success : function(
																	data) {
																var parentId = document
																		.getElementById("parentid").value;
																refreshGrid(parentId);
																showSuccess("successfully Deactivated");

															}
														});

											} else
												showError("Cannot deactivate this, as it has references to existing resources");
										});

					});

	$('#orgTable')
			.delegate(
					'a.delete',
					'click',
					function(e) {

						e.preventDefault();
						var nRow = $(this).parents('tr')[0];
						var id = $(nRow).attr('id');
						//var url = "activitys/"+id;
						if (saveOpen
								&& !(id == undefined || id == null || id == '')) {
							// Added for task # 216 - Start
							/* alert("Please enter and save the data"); */
							var text = "Please enter and save the data";
							showAlert(text);
							// Added for task # 216 - End
							e.preventDefault();
							return;
						}
						var aData = oTable.fnGetData(nRow);
						var text = "Are you sure you want to delete BG/BU \""
								+ aData[1] + "\" ?";
						var successMessage = "BG/BU \"" + aData[1]
								+ "\" has been successfully deleted.";
						if (id == undefined || id == null || id == '') {
							text = "Are you sure you want to delete this row ?";
							successMessage = "Are you sure you want to delete this row ?";
						}
						deleteRow(oTable, nRow, text, successMessage);
						$('<div class="toasterBgDiv"></div>').appendTo(
								$('body'));
					});

	$('#orgTable').delegate('a.edit', 'click', function(e) {
		if (saveOpen && this.innerHTML != "Save") {
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
		//alert("jfjdfdjf"+ $(nRow).attr('id'));
		if (nEditing !== null && nEditing != nRow) {
			/* Currently editing - but not this row - restore the old before continuing to edit mode */
			restoreRow(oTable, nEditing);
			editRow(oTable, nRow);
			nEditing = nRow;

		} else if (nEditing == nRow && this.innerHTML == "Save") {
			/* Editing this row and want to save it */
			// Provide the required parameters in the below functions to validate the inputs
			if (!validateInputs('.tbl')) {
				return;
			}
			
			var aData = oTable.fnGetData(nRow);
			var curIndex = getInputValue(aData[2]);
			
			if(curIndex == '' || curIndex == null) {
				if($("#employeeNameEditId").val() == null){
					
					$("#employeeNameEditId").css("border", "1px solid #ff0000");
					
					showError("Please Select BG/BU Head");
					return;
				}	
			}
			
			//sarang
			saveRow(oTable, nEditing);
			saveOpen = false;
			//nEditing = null;
		}

		else {
			/* No edit in progress - let's start one */

			editRow(oTable, nRow);
			nEditing = nRow;
		}
	});

	$('#addNew').on(
			"click",
			function(e) {
				addNewAlloc = true;
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

				var aiNew = oTable.fnAddData([ '', '', '', '',  // added one extra column val by sarang for new row
						'<center><a class="edit" href="#">Edit</a>/<a id="cancel" class="delAddedRow" href="">Cancel</a><center>', //sarang added made center aligned as per Task#205
						'<center><a class="delete" href="">Delete</a><center>',//made center aligned as per Task#205
						'<center>N.A.<center>' ]);//made center aligned as per Task#205
				var nRow = oTable.fnGetNodes(aiNew[0]);
				editRow(oTable, nRow);
				nEditing = nRow;
				saveOpen = true;
				$( ".comboselect" ).combobox();
			});

	
	/* sarang added start */
	$(document).on('click', '.delAddedRow', function(){
		oTable.fnDeleteRow(global);
	});
	
	
	
	/* sarang added end */
		
	function saveRow(oTable, nRow) {
		startProgress();
		var jqInputs = $('input', nRow);
		// var jqSelects = $('select', nRow);
		// alert("jqInputs=="+jqInputs);
		var aData = jqInputs;
		//var bData = jqSelects;
		for (var i = 0; i < jqInputs.length; i++) {
			aData[i] = jqInputs[i].value;
		}

		var stringName = aData[0];

		if (spclCharacterValidation(stringName)) {
			showError("Please Enter valid BG/BU name");
			stopProgress();
			return false;

		}

		var isBGExist = false;

		var parentId = document.getElementById("parentid").value;

		$.ajax({
			async : false, // !important
			url : "orghierarchys/validate",
			data : "name=" + aData[0] + "&parentId=" + parentId,
			success : function(data) {
				var data1 = JSON.parse(data);
				if (data1.result == true) {
					isBGExist = true;
				}
			}
		});

		var rowDatabaseId = $(nRow).attr('id');

		var sData = $('*', oTable.fnGetNodes()).serializeArray();

		if (rowDatabaseId != null && rowDatabaseId != '') {
			sData.push({
				name : "id",
				value : rowDatabaseId
			});
		}

		sData.push({
			name : "parentId.id",
			value : parentId
		});

		sData.push({
			name : "Name",
			value : aData[0]
		});
		sData.push({
			name : "Description",
			value : aData[1]
		});
		
		/* sarang added code */
		/* if (rowDatabaseId != null && rowDatabaseId != '') {
		 sData.push({
			name : "employeeId",
			value : rowDatabaseId
		 }); 
		} */
		/* sarang added code end */
		
		var jsonData = '{'
		$.each(sData, function(i, item) {
			var jsonString = getJsonString(item.name, item.value);
			if (jsonString != null && $.trim(jsonString) != '')

				jsonData += getJsonString(item.name, item.value) + ",";

		});
		jsonData = jsonData.slice(0, -1);
		jsonData += '}';

		if (rowDatabaseId == null) {

			if (!isBGExist) {
				$.ajax({
					async : true,
					type : "POST",
					contentType : "application/json",
					url : "orghierarchys",
					data : jsonData,
					success : function(data) {
						refreshGrid(parentId);
						showSuccess("successfully saved");
						//isDuplicateProjectName = true;

					}
				});
			} else {

				showError("Organization"
						+ " \""
						+ aData[0]
						+ "\" is already present. Please provide different Organization");

			}

		} else {

			if (stringName != oldBGName) {
				for (var i = 0; i < cells.length; i++) {
					if (cells[i] == stringName) {
						showError("Organization"
								+ " \""
								+ aData[0]
								+ "\" is already present. Please provide different Organization");
						stopProgress();
						return;
					}
				}
			}

			$.ajax({
				async : true,
				type : "PUT",
				contentType : "application/json",
				url : "orghierarchys",
				data : jsonData,
				success : function(data) {
					refreshGrid(parentId);
					showSuccess("successfully updated");
					//isDuplicateProjectName = true;

				}
			});

		}

		stopProgress();

	}

	function refreshGrid(id) {
		//alert('parentId'+id);
		if (oTable != null)
			oTable.fnClearTable();
		//$.getJSON("orghierarchys/" + id, showOrganizationHierarchies);
		location.reload();
	}
	$(".moveOrg").fancybox(
			{
				//fitToView	: false,
				autoSize : false,
				closeClick : true,
				//autoScale   : true,
				autoDimensions : true,
				transitionIn : 'fade',
				transitionOut : 'fade',
				openEffect : 'easingIn',
				closeEffect : 'easingOut',
				type : 'iframe',
				onClosed : function() {
					parent.location.reload(true);
				},
				'width' : '100%',
				preload : true,
				/*'height':400,*/
				beforeShow : function() {
					var thisH = this.height - 35;
					$(".fancybox-iframe").contents().find('html').find(
							".midSection").css('height', thisH);
				}

			});
</script>
 
        

</html>
