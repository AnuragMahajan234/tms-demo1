<%@ page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<spring:url value="/resources/styles/style.css?ver=${app_js_ver}" var="style_css"/>
	<link href="${style_css}" rel="stylesheet" type="text/css"></link>
	
<spring:url value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />
<spring:url value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}"
	var="multiselect_js" />

<script src="${multiselect_js}" type="text/javascript"></script>

 <!-- noty -->
 <spring:url value="/resources/js-framework/noty/jquery.noty.js?ver=${app_js_ver}"	var="jquery_noty_js" />
 <spring:url value="/resources/js-framework/noty/layouts/bottom.js?ver=${app_js_ver}"	var="bottom_js" />
 <spring:url value="/resources/js-framework/noty/layouts/bottomCenter.js?ver=${app_js_ver}"	var="bottomCenter_js" />
 <spring:url value="/resources/js-framework/noty/layouts/bottomLeft.js?ver=${app_js_ver}"	var="bottomLeft_js" />
 <spring:url value="/resources/js-framework/noty/layouts/bottomRight.js?ver=${app_js_ver}"	var="bottomRight_js" />
 <spring:url value="/resources/js-framework/noty/layouts/center.js?ver=${app_js_ver}"	var="center_js" />
 <spring:url value="/resources/js-framework/noty/layouts/centerLeft.js?ver=${app_js_ver}"	var="centerLeft_js" />
 <spring:url value="/resources/js-framework/noty/layouts/centerRight.js?ver=${app_js_ver}"	var="centerRight_js" />
 <spring:url value="/resources/js-framework/noty/layouts/inline.js?ver=${app_js_ver}"	var="inline_js" />
 <spring:url value="/resources/js-framework/noty/layouts/top.js?ver=${app_js_ver}"	var="top_js" />
 <spring:url value="/resources/js-framework/noty/layouts/topCenter.js?ver=${app_js_ver}" var="topCenter_js" />
 <spring:url value="/resources/js-framework/noty/layouts/topLeft.js?ver=${app_js_ver}"	var="topLeft_js" />
 <spring:url value="/resources/js-framework/noty/layouts/topRight.js?ver=${app_js_ver}" var="topRight_js" />
 <spring:url value="/resources/js-framework/noty/themes/default.js?ver=${app_js_ver}"	var="default_js" />
 
 <script src="${jquery_noty_js}" type="text/javascript"></script>
 <script src="${bottom_js}" type="text/javascript"></script>
 <script src="${bottomCenter_js}" type="text/javascript"></script>
 <script src="${bottomLeft_js}" type="text/javascript"></script>
 <script src="${bottomRight_js}" type="text/javascript"></script>
 <script src="${center_js}" type="text/javascript"></script>
 <script src="${centerLeft_js}" type="text/javascript"></script>
 <script src="${centerRight_js}" type="text/javascript"></script>
 <script src="${inline_js}" type="text/javascript"></script>
 <script src="${top_js}" type="text/javascript"></script>
 <script src="${topCenter_js}" type="text/javascript"></script>
 <script src="${topLeft_js}" type="text/javascript"></script>
 <script src="${topRight_js}" type="text/javascript"></script>
 <script src="${default_js}" type="text/javascript"></script>
 
<style>
.dataTables_scrollBody {
   /*  height: 350px !important; */
    width: 99% !important;
}
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
			$(document).ready(function() {
				var oTable;
				
				/* Add the events etc before DataTables hides a column */
				$("thead input").keyup( function () {
					/* Filter on the column (the index) of this element */
					oTable.fnFilter( this.value, oTable.oApi._fnVisibleToColumnIndex( 
						oTable.fnSettings(), $("thead input").index(this) ) );
				} );
				
				/*
				 * Support functions to provide a little bit of 'user friendlyness' to the textboxes
				 */
				$("thead input").each( function (i) {
					this.initVal = this.value;
				} );
				
				$("thead input").focus( function () {
					if ( this.className == "search_init" )
					{
						this.className = "";
						this.value = "";
					}
				} );
				
				$("thead input").blur( function (i) {
					if ( this.value == "" )
					{
						this.className = "search_init";
						this.value = this.initVal;
					}
				} );
				
				oTable = $('#example').dataTable( {
					"sDom" : '<"top"lfip>rt<"bottom"ip<"clear">',
					"sPaginationType": "full_numbers",
					"bStateSave": true,
					"sScrollX": "100%",
					"sScrollY": "350",
					"bScrollCollapse": true,
					"aoColumnDefs": [
						{ "bVisible": false, "aTargets": [] }
					],
					"oLanguage": {
						"sSearch": "Search:"
					},
					"aoColumns": [{},{},{ "bSortable": false },{ "bSortable": false }],
					"bSortCellsTop": true,
				} ); 
				
				
// 				var nEditing = null;
// 				var saveOpen = false;
				$("#example_filter").find('input').bind('change paste keyup', function(){
							var srch =$("#example_filter").find('input').val();
							if(null == srch || $.trim(srch) == '' ){
								$("#add").show();
							}else{
								$("#add").hide();
							}
								
        				});
				$('#addNew').click(function (e) {
					
					/*Shantanu- Start*/
					if(saveOpen){
						// Added for task # 216 - Start
						//alert("Please enter and save the data"); 
						var text="Please enter and save the data";
						showAlert(text);
						// Added for task # 216 - End
						e.preventDefault();
						return;
					}
					e.preventDefault();
					
					var aiNew = oTable.fnAddData( [ '', '',
						'<a class="edit" href="">Edit</a>', '<a class="delete" href="">Delete</a>' ] );
					var nRow = oTable.fnGetNodes(aiNew[0]);
					editRow( oTable, nRow );
					nEditing = nRow;
					saveOpen = true;
				//	$("table.dataTbl tbody tr").find("td:nth-child(1) input").removeAttr("readonly");
				} );
				
			
				$('#example').delegate('a.delete','click', function (e) {
					e.preventDefault();
					var nRow = $(this).parents('tr')[0];
					var id = $(nRow).attr('id');
					if (saveOpen && !(id == undefined || id == null
							|| id == '')) {
						var text="Please enter and save the data";
						showAlert(text);
						e.preventDefault();
						return;
					}
					var url = "designationses/"+id;
					var aData = oTable.fnGetData(nRow);
					var text = "Are you sure you want to delete Designation \"" + aData[1] +"\" ?";
					var successMessage = "Designation \"" + aData[1] +"\" has been successfully deleted.";
					if (id == undefined || id == null || id == '') {
	 	            	text = "Are you sure you want to delete this row ?";
	 	            	successMessage = "Designation has been successfully deleted.";
	 	            }
					deleteRow(oTable,nRow,url,text,successMessage);
					$('<div class="toasterBgDiv"></div>').appendTo($('body'));
				} );


				$('#example').delegate('a.edit','click', function (e) {
					if (saveOpen && this.innerHTML != "Save") {
						// Added for task # 216 - Start
						/* alert("Please enter and save the data"); */
						var text="Please enter and save the data";
						showAlert(text);
						// Added for task # 216 - End
						e.preventDefault();
						return;
					}

					saveOpen=true;
					e.preventDefault();
					
					/* Get the row as a parent of the link that was clicked on */
					var nRow = $(this).parents('tr')[0];
					
					if ( nEditing !== null && nEditing != nRow ) {
						/* Currently editing - but not this row - restore the old before continuing to edit mode */
						restoreRow( oTable, nEditing );
						editRow( oTable, nRow );
						nEditing = nRow;

					}
					else if ( nEditing == nRow && this.innerHTML == "Save" ) {
						/* Editing this row and want to save it */
						// Provide the required parameters in the below functions to validate the inputs
						if(!validateInputs('.tbl') || !validateDuplicates('example', 'Designation', 2)) {
							return;
						}
						saveRow( oTable, nEditing );
						saveOpen=false;
						//nEditing = null;
					}
					else {
						/* No edit in progress - let's start one */
						editRow( oTable, nRow );
						nEditing = nRow;
					}
					} );

		function saveRow (oTable, nRow) {
        startProgress();
        var jqInputs = $('input', nRow);
		var aData=jqInputs;
		for(var i=0; i<jqInputs.length; i++){
			aData[i] =  jqInputs[i].value;
		}
		var rowDatabaseId = $(nRow).attr('id');
		var sData = $('*', oTable.fnGetNodes()).serializeArray();
		if(rowDatabaseId != null && rowDatabaseId != '')
			sData.push({name:"id",value:rowDatabaseId});
		aData[1] = capitaliseFirstLetter(aData[1]);
		sData.push({name:"designationName",value:aData[1]});
		
		var stringName = aData[1] ;
		 if(spclCharacterValidation(stringName)){
			showError("Please Enter valid Designation name");
			 stopProgress();
			return false;
			
		} 
		var jsonData ='{'
		$.each(sData, function(i, item) {
				var jsonString = getJsonString(item.name, item.value);
				if(jsonString != null && $.trim(jsonString) != '')
					jsonData += getJsonString(item.name, item.value) +",";			
			
		});
		jsonData = jsonData.slice(0, -1);
		jsonData +='}';
		
		if(aData[0] == null || $.trim(aData[0]) == ''){
			$.postJsonData("designationses", jsonData, function(data) {
				saveOpen = false;
				nEditing= null;
    	        stopProgress();
				var successMsg ="Designation \"" + aData[1] +"\" has been inserted successfully";
				showSuccess(successMsg);
				setTimeout(function() {
					location.reload();	
				}, 1000);		 	}, "json");
		} else {
			$.putJsonData("designationses", jsonData, function(data) {
				saveOpen = false;
				nEditing= null;
				stopProgress();
				var successMsg ="Designation \"" + aData[1] +"\" details have been saved successfully";
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

var data={
		getCostRowId: function(arg){
			return 	$("table#addPoTbl tbody").find("tr").length;			
		}

}

$.views.helpers({ GetRowId: data.getCostRowId });
$.templates("poTableRowsTempl", {
    markup: "#poTableRows"
});
$.templates("poTableRowsWithValuesTempl", {
    markup: "#poTableRowsWithValues"
    
});


</script>
        <div class="content-wrapper"> 
          <!--right section-->
          <div class="botMargin">
            <h1>Designation</h1>
          </div>
          <div class="tab_section">
            <ul class='tabs'>
              <li><a href='#tab1'>List</a></li>             
            </ul>
            <div id='tab1' class="tab_div">
            <div class="search_filter_outer">
				<div class="search_filter search_filterLeft"></div>
					<div class="btnIcon" id="add">
							<a id="addNew" class="blue_link" href="javascript:void(0);">
							<img width="16" height="22" src="resources/images/addUser.gif">
							Add New
						   </a>		
					
					<div class="clear"></div>
				</div>
			</div>
              <div class="tbl">
                <table  class="dataTbl display tablesorter addNewRow alignCenter" id="example" >
                  <thead>
                    <tr>
                      <th width="7%" align="center" valign="middle">S. No.</th>
                      <th width="8%" align="center" valign="middle">Designation Name</th>
                      <th width="4%" align="center" valign="middle">Edit</th>
						<th width="4%" align="center" valign="middle">Delete</th>                     
                    </tr>
                  </thead>
                  <tbody>                  
                  <c:forEach var="designation" varStatus="des" items="${designationses}">
			         <tr id="${designation.id}">
			                <td align="center" valign="middle">
			                <c:if test="${not empty designation.id}">${des.index+1}</c:if>
			                </td>
			                <td align="center" valign="middle">
			                	<c:if test="${empty designation.designationName}">N.A.</c:if> 
			                	<c:if test="${not empty designation.designationName}">${designation.designationName}</c:if>
			                </td>
			                <td align="center" valign="middle">
			                <a class="edit" href="">Edit</a>
			                </td>
			                <td align="center" valign="middle">
			                <a class="delete" href="">Delete</a>
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
        