<%@page import="org.yash.rms.util.Constants"%>
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
<spring:url
	value="resources/js/rmsUIValidation.js"
	var="rmsUIValidation_js" />
<script src="${rmsUIValidation_js}" type="text/javascript"></script>

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
					"bStateSave": false,
					"sScrollX": "100%",
					"sScrollY": "350",
					"bScrollCollapse": true,
					"aoColumnDefs": [
						{ "bVisible": false, "aTargets": [] }
					],
					"oLanguage": {
						"sSearch": "Search:"
					},
					"aoColumns": [{},{},{},{ "bSortable": false },{ "bSortable": false }],
					"bSortCellsTop": true,
				} ); 
				
				$("#example_filter").find('input').bind('change paste keyup', function(){
					var srch =$("#example_filter").find('input').val();
					if(null == srch || $.trim(srch) == '' ){
						$("#add").show();
					}else{
						$("#add").hide();
					}
						
				});
// 				var nEditing = null;
// 				var saveOpen = false;
				
				$('#addNew').click(function (e) {
					
					/*Shantanu- Start*/
					if(saveOpen){
						// Added for task # 216 - Start
						/* alert("Please enter and save the data"); */
						var text="Please enter and save the data";
						showAlert(text);
						// Added for task # 216 - End
						e.preventDefault();
						return;
					}
					e.preventDefault();
					
					var aiNew = oTable.fnAddData( [ '', '','',
						'<a class="edit" href="">Edit</a>', '<a class="delete" href="">Delete</a>' ] );
					var nRow = oTable.fnGetNodes(aiNew[0]);
					editRow( oTable, nRow );
					nEditing = nRow;
					saveOpen = true;
					//$("table.dataTbl tbody tr").find("td:nth-child(1) input").removeAttr("readonly");
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
					var url = "locations/"+id;
					var aData = oTable.fnGetData(nRow);
					var text = "Are you sure you want to delete Location \"" + aData[1] +"\" ?";
					var successMessage = "Location \"" + aData[1] +"\" has been successfully deleted.";
					if (id == undefined || id == null || id == '') {
	 	            	 text = "Are you sure you want to delete this row ?";
	 	            	 successMessage = "Location has been successfully deleted.";
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

					//startProgress();
					saveOpen=true;
					e.preventDefault();
					
					/* Get the row as a parent of the link that was clicked on */
					var nRow = $(this).parents('tr')[0];
					
					if ( nEditing !== null && nEditing != nRow ) {
						/* Currently editing - but not this row - restore the old before continuing to edit mode */
						restoreRow( oTable, nEditing );
						editRow( oTable, nRow );
						nEditing = nRow;
						//stopProgress();
					}					
					else if ( nEditing == nRow && this.innerHTML == "Save" ) {
						/* Editing this row and want to save it */
						// Provide the required parameters in the below functions to validate the inputs
						if(!validateInputs('.tbl') || !validateDuplicates('example', 'Location', 2)) {
							return;
						}
						saveRow( oTable, nEditing );
						saveOpen=false;
						//nEditing = null;						
					} else {
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
		sData.push({name:"location",value:aData[1]});
		sData.push({name:"locationHrEmailId",value:aData[2]});
		
		var stringName = aData[1] ;
		if(spclCharacterValidationForLocation(stringName)){
			showError("Please Enter valid location");
			 stopProgress();
			return false;
			
		}
		var stringName1 = aData[2] ;
		
	
		 if (!(validateEmail(stringName1))){
			var errorMsg = "\u2022 Domain of E-mail should be '@yash.com' ! <br />";
			showError(errorMsg);
			 stopProgress();
			return false;
			}
		 //added by aakanksha//
		var commaSeparatedRegex =  /^(([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5}){1,25})+([;.](([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5}){1,25})+)*$/;
		if(commaSeparatedRegex.test(stringName1)){         
			
				}else{   
				 
		    var errorMsg = "\u2022 Please use SEMI-COLON separated email addresses only ! <br />";
				showError(errorMsg);
				 stopProgress();
				return false;
		    }

		//added by pratyoosh//	
		 var pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
		    if(pattern.test(stringName1)){         
				
				}else{   
				 
            var errorMsg = "\u2022 Please put valid email address ! <br />";
				showError(errorMsg);
				 stopProgress();
				return false;
		    }
		  //added by pratyoosh//	
		

		
		
			
		 
		var jsonData ='{'
		$.each(sData, function(i, item) {
				var jsonString = getJsonString(item.name, item.value);
				if(jsonString != null && $.trim(jsonString) != '')
					jsonData += getJsonString(item.name, item.value) +",";			
			
		});
		jsonData = jsonData.slice(0, -1);
		jsonData +='}';
		if(aData[0] == null || $.trim(aData[0]) == '') {
			$.postJsonData("locations", jsonData, function(data) {
				saveOpen = false;
				nEditing= null;
			    stopProgress();
				var successMsg ="Location \"" + aData[1] +"\" has been inserted successfully";
				showSuccess(successMsg);
				setTimeout(function() {
					location.reload();	
				}, 1000);
		 	}, "json" );
		} else {
			$.putJsonData("locations", jsonData, function(data) {
				saveOpen = false;
				nEditing= null;
	            stopProgress();
				var successMsg ="Location \"" + aData[1] +"\" details have been saved successfully"; 
				showSuccess(successMsg);
				setTimeout(function() {
					location.reload();	
				}, 1000);
		 	}, "json" );
		}
	}
});
			
		
	
</script>











        <div class="content-wrapper"> 
          <!--right section-->
          <div class="botMargin">
            <h1>Location</h1>
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
                      <th width="8%" align="center" valign="middle">Location Name</th>
                      <th width="8%" align="center" valign="middle" >Location HR </th>
                      <th width="4%" align="center" valign="middle">Edit</th>
						<th width="4%" align="center" valign="middle">Delete</th>                     
                    </tr>
                  </thead>
                  <tbody>                  
                  <c:forEach var="location" varStatus="loc" items="${locations}">
			         <tr id="${location.id}">
			                <td align="center" valign="middle">
			                <c:if test="${not empty location.id}"> ${loc.index+1}</c:if>
			                </td>
			                <td id="locationName" align="center" valign="middle">
			                <c:if test="${empty location.location}">N.A.</c:if> <c:if
								  test="${not empty location.location}">${location.location}</c:if>
			                </td>
			                
			                <td id="locationHrEmailId" align="center" valign="middle">
			                <c:if test="${empty location.locationHrEmailId}">N.A.</c:if> <c:if
								  test="${not empty location.locationHrEmailId}">${location.locationHrEmailId}</c:if>
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
        
        
        


  
