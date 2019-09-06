<%@ page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<spring:url value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}" 	var="ColReorder_js" />
<spring:url value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}" 	var="ColVis_js" />
<spring:url value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}" var="multiselect_js" />
<script src="${multiselect_js}" type="text/javascript"></script>

<spring:url value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}" 	var="multiselect_filter_js" />
<%-- <script src="${multiselect_filter_js}" type="text/javascript"></script> --%>
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}" var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
	
<spring:url value="/resources/images/spinner.gif" var="spinner" />

<script src="${multiselect_filter_js}" type="text/javascript"></script>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />

<style type="text/css" title="currentStyle">
.close {
  position: absolute;
  right: 32px;
  top: 32px;
  width: 32px;
  height: 32px;
  opacity: 0.3;
}
.close:hover {
  opacity: 1;
}
.close:before, .close:after {
  position: absolute;
  left: 15px;
  content: ' ';
  height: 15px;
  width: 2px;
  background-color: #333;
}
.close:before {
  transform: rotate(45deg);
}
.close:after {
  transform: rotate(-45deg);
}


.toolbar {
    float: left;
}

.closeButton{
	float: right;
}

thead input {
	width: 100%
}

#records_tableId {
	table-layout: fixed;
	background: transparent;
	border: 0;
}

#records_tableId thead th {
	width: 120px;
	word-wrap: break-word;
}

#records_tableId td {
	word-wrap: break-word;
}


input.search_init {
	color: #999
}

.projecttoolbar {
	border: -65px solid red;
	width: 50%;
	float: left;
}

.blue_link {
	left: -200
}

#defaultTableId {
	
}

#defaultTableId thead th {
	width: 120px;
}

#defaultTableId td {
	word-wrap: break-word;
}

.dtable td {
	background: #ebebeb;
}

.rRReportFilter td {
	padding: 10px;
}

.activeReportTab {
	background: #84DFC1;
}

.datatable-scroll {
	max-width: 2000px;
	overflow-x: auto;
	position:relative;
	
}
div.dataTables_wrapper {
	width: 100%;
	margin: 0 auto;
	position: relative;
}
</style>

<script>

$(function() {
	$('#client').multiselect({
		includeSelectAllOption: true
	}).multiselectfilter();
});

$(function() {
	$('#group').multiselect({
		includeSelectAllOption: true
	}).multiselectfilter();
});

var getGroupForCustomer =  function(){
	 startProgress();
	 var clientIds = [];
	 
	 $("#client option:selected").each(function () {
			var id = $(this).val();
			clientIds.push(id);
	 }); 
	 
	 if(clientIds.length<=0){
		 clientIds.push(0);
	 }
	 
	$.ajax({
		type: 'GET',
		dataType: 'json',
       url: '/rms/customers/'+clientIds+'/groups/', 
       cache: false,
       success: function(response) { 
       	stopProgress();
			var grpDrpDwn="";
			 $('#group').empty();
			 for(var i=0; i<response.length; i++){
					 var populateGrp = response[i];
	                grpDrpDwn = grpDrpDwn + "<option value='"+populateGrp.groupId+"'>"+populateGrp.groupName+"</option>";     
	            }
			 $("#group").append(grpDrpDwn);
			 $('#group').multiselect('refresh');
    	},
    	error: function(error){
    		stopProgress();
    		showError("Something happend wrong!!");
    	}
	})
};

$(document).ready(function() {
	 $('#records_tableId').DataTable( {
		   "sDom": 'RC<"clear"><"top"lfp>rt<"bottom"ip<"clear">', 
		   "sPaginationType": "full_numbers",
		   "oLanguage": {"sSearch": "Search all columns:"},
	       'aoColumns': [{'aTargets': 0,'sClass': ''},
	        			{'aTargets': 1,'sClass': ''},
	        			{'aTargets': 2,'sClass': ''},
	        			{'aTargets': 3,'sClass': ''},
	        			{'aTargets': 4,'sClass': ''},
	        			{'aTargets': 5,'sClass': ''},
	        			{'aTargets': 6,'sClass': ''},
	        	        {'aTargets': 7,'sClass': 'shortlisted-resources'},
	        	        {'aTargets': 8,'sClass': 'closed-resources'},
			            {'aTargets': 9,'sClass': 'notfit-resources'},
			            {'aTargets': 10,'sClass': ''},
			            {'aTargets': 11,'sClass': ''},
			            {'aTargets': 12,'sClass': 'submission-resources'},
			            {'aTargets': 13, 'bVisible': false }]
	});
	 
	$("#client").on('change', function(){
		getGroupForCustomer();
	})
});


function prepareTableDataElement(statusId, id , resourcesCount){
	if( resourcesCount != null && resourcesCount >0 ){
		return '<a href="#" > '+resourcesCount+'</a>'
	}else{
		return '0';
	}
}

$(document).on('click','#submit',function() {
	$("#records_tableId").hide();
	$("#NoAllocMessage").hide();
	
	var startDate = "";
	var endDate ="";
	var errmsg = "Please select:";
	var validationFlag = true;
	var customerIds = "";
	var groupIds = "";
	var statusIds ="";
	var customerIds = [];
	var groupIds = [];
	var hiringUnits = [];
	var reqUnits = [];
	
	$("#client option:selected").each(function () {
		var id = $(this).val();
		customerIds.push(id);
 	}); 
	
	if(customerIds.length <=0){
		errmsg = errmsg + " Client";
		validationFlag = false;
	}
	
	
	if (!validationFlag) {
		stopProgress();
		if (errmsg.length > 0)
			showError(errmsg);
		return;
	} else {
		
		$("#group option:selected").each(function () {
			var id = $(this).val();
			groupIds.push(id);
	 	}); 
		
		$.ajax({
			type : 'GET',
			url : '/rms/requestRequisitionReport/getRequestRequisitionReport',
			contentType : "text/html; charset=utf-8",
			async : false,
			data : "&customerIds="+customerIds
					+"&groupIds="+groupIds
					+"&statusIds="+statusIds
					+ "&hiringUnits="+hiringUnits 
					+ "&reqUnits="+reqUnits,
					
					success : function(data) {
						if (data.length != 0) {
							$('#records_tableId').show();
							$("#NoAllocMessage").hide();
							$("#requestRequisitionReportTable").show();
							$('#records_tableId').dataTable().fnClearTable();
							var i = 1;
							$.each(data,function(key,val) {
								$('#records_tableId').show();
								$('#records_tableId').dataTable().fnAddData(
									[	
										val.requirementId,
										val.requestedBy,
										val.status,
										val.noOfResources,
										val.skill,
										val.designationName,
										val.open,
										prepareTableDataElement(2,val.id,val.shortlisted),
										prepareTableDataElement(8,val.id, val.closed),
										prepareTableDataElement(3,val.id, val.notFitForRequirement),
										val.hold,
										val.lost,
										prepareTableDataElement(0,val.id,val.submissions),
										val.id+'',
									]
								)												                                             
							});
						}
					},
					error : function(errorResponse) {
						//showError("Records Not Found");
						$('#records_tableId').hide();
						var htmlVarObj = '<div id="NoAllocMessage" class="NoAllocMessage">No Record Exist for selected filters.</div>';
						$('#NoAllocMessage').empty().append(htmlVarObj);
						$("#NoAllocMessage").show();
						$("#requestRequisitionReportTable").hide();

					}
		});
	}
});


$(document).ready(function() {
	
	function closeSubTable(){
		 lastTableObject.removeClass( 'details' );
       	 lastTableObject.fnClose(lastNTr);
         detailRows.splice(lastIdx, 1 );
	}
    
    function fnFormatDetails(table_id, html) {
	    var sOut = "<table id=\"sub_record_table_"+table_id+"\" class=\"dataTbl display tablesorter addNewRow alignCenter\">";
	    sOut = sOut+"<thead><tr><th>Type</th><th>Resource Name</th><th>Skill</th><th>Designation</th><th>Status</th><th>Date</th></tr></thead>";
	    sOut += html;
	    sOut += "</table>";
	    return sOut;
	}
    
    var oInnerTable;
    var lastTableObject;
    var lastNTr;
    var lastIdx;
    
    function showSubTable(dt, tr, nTr, reqId, status, rrfName,stausName){
    	 var TableHtml;
    	 var detailRows = [];
    	 var row = dt.$('tr');
         var idx = $.inArray( tr.attr('id'), detailRows );
         var responseData;
         
         if(lastTableObject != null){
	       	 lastTableObject.removeClass( 'details' );
	       	 lastTableObject.fnClose(lastNTr);
	         detailRows.splice(lastIdx, 1 );
         }
         
        lastNTr =nTr;
        lastIdx = idx
        lastTableObject = dt;
         
       	TableHtml = "<tbody>";
       	getResources(status, reqId, function(data){
       	responseData = data;
       		var val;
       		 for(var i=0; i<responseData.length; i++){
       			 val = responseData[i];
       			 TableHtml = TableHtml+'<tr><td>'+val.resourceType+'</td>'
       			 				+'<td>'+val.resourceName+'</td>'
       			 				+'<td>'+val.skill+'</td>'
       			 				+'<td>'+val.designation+'</td>'
       			 				+'<td>'+val.status+'</td>'
       			 				+'<td>'+val.allocationDate+'</td></tr>';     
            }
       		TableHtml = TableHtml+"</tbody>"
            	dt.fnOpen(nTr, fnFormatDetails(reqId, TableHtml), 'details');
               oInnerTable = $("#sub_record_table_"+reqId).dataTable({
               	"bPaginate": false,
               	"bFilter": false,
                "bInfo": false,
                "sDom": '<"toolbar">frtip',
                });
               $("div.toolbar").html('<b>'+rrfName+' : '+stausName+'</b>');
       	});
    }
    
    $('#records_tableId tbody').on( 'click', 'tr td.closed-resources', function () {
    	var dt = $('#records_tableId').dataTable();
        var tr = $(this).closest('tr');
        var nTr = $(this).parents('tr')[0];
        var position = dt.fnGetPosition(nTr);
        var reqId = dt.fnGetData(position)[13];
        var status = 8;
        var stausName = 'Joined Resources';
        var rrfName = dt.fnGetData(position)[0];
        var selectedColumnValue =  dt.fnGetData(position)[8];
         if(selectedColumnValue !='0' && selectedColumnValue !=''){
        	showSubTable(dt, tr, nTr, reqId, status,rrfName,stausName );
          }
    } );
    
    $('#records_tableId tbody').on( 'click', 'tr td.shortlisted-resources', function () {
    	var dt = $('#records_tableId').dataTable();
        var tr = $(this).closest('tr');
        var nTr = $(this).parents('tr')[0];
        var position = dt.fnGetPosition(nTr);
        var reqId = dt.fnGetData(position)[13];
        var status = 2;
        var stausName = 'Shortlisted Resources';
        var rrfName = dt.fnGetData(position)[0];
        var selectedColumnValue =  dt.fnGetData(position)[7];
        if(selectedColumnValue !='0' && selectedColumnValue !=''){
       		showSubTable(dt, tr, nTr, reqId, status,rrfName,stausName );
         }
    } );
    
    $('#records_tableId tbody').on( 'click', 'tr td.notfit-resources', function () {
    	var dt = $('#records_tableId').dataTable();
        var tr = $(this).closest('tr');
        var nTr = $(this).parents('tr')[0];
        var position = dt.fnGetPosition(nTr);
        var reqId = dt.fnGetData(position)[13];
        var status = '3,6';
        var stausName = 'Not Fit Resources';
        var rrfName = dt.fnGetData(position)[0];
        var selectedColumnValue =  dt.fnGetData(position)[9];
        if(selectedColumnValue !='0' && selectedColumnValue !=''){
       		showSubTable(dt, tr, nTr, reqId, status,rrfName,stausName );
         }
    } );
    
    $('#records_tableId tbody').on( 'click', 'tr td.submission-resources', function () {
    	var dt = $('#records_tableId').dataTable();
        var tr = $(this).closest('tr');
        var nTr = $(this).parents('tr')[0];
        var position = dt.fnGetPosition(nTr);
        var reqId = dt.fnGetData(position)[13];
        var status = 0;
        var stausName = 'Submitted Resources';
        var rrfName = dt.fnGetData(position)[0];
        var selectedColumnValue =  dt.fnGetData(position)[12];
        if(selectedColumnValue !='0' && selectedColumnValue !=''){
       		showSubTable(dt, tr, nTr, reqId, status,rrfName,stausName );
         }
    } );
    
    function getResources(status,skillRequisitionId, responseData){
    	$.ajax({
    		type: 'GET',
    		dataType: 'json',
           url: '/rms/requestsReports/requestRequisitionSkills/'+skillRequisitionId+'/resources', 
           cache: false,
           data : "&resourceStatusIds="+status,
           success: function(response) { 
    	       	stopProgress();
    	       	responseData(response);
        	},
        	error: function(error){
        		stopProgress();
        		showError("Something happend wrong!!");
        	}
    	})
    }
} );


</script>


<body>
	<div class="content-wrapper">
		<div class="botMargin">
			<h1>Position Dashboard</h1>
		</div>
		<div class="tab_seaction">
			<ul class='tabs'>
				<li><a href='#tab1' id="tab1id">List</a></li>

			</ul>
			<div id='tab1' class="tab_div">
				<div class="form">
					<form name="defaultFormName" method="post" id="defaultForm">
					 	<!-- <div class="ui-widget-header ui-corner-all ui-multiselect-header ui-helper-clearfix ui-multiselect-hasfilter"> -->
						<div  class="tblBorderDiv">
							<table id="mail" cellpadding="5" cellspacing="5" class="display dataTable" style="border-collapse:collapse;width:100%; margin-top:0px;">
							<tr>
								<input type="hidden" id="formHiddenId" name="id"></input>
							</tr>
							<tr>
								<td align="right" width="20%"><strong>Client <span style="color: red;">*</span> : </strong></td>
								<td align="left" width="20%">
								 <div  class="positionRel">
									<select id="client" class="required" multiple="multiple">
										<c:forEach var="customer" items="${customerList}">
												<option value="${customer.id}">${customer.customerName}</option>										
										</c:forEach>
									</select> 
			 					</div>
			 					</td>
			 					
			 	 				<td align="right" width="20%"><strong>Group : </strong></td>
								<td align="left" width="20%">
									 <div  class="positionRel">
										<select id="group" class="required" multiple="multiple">
											<option value='Select'>Select</option>
										</select>
									 </div> 
			 					</td> 
					 		</tr>
					 		<tr>
			 					<td align="center" width="20%" colspan="4">
									<input type="button" class="request-form-style" style="font-size: medium;" id="submit" value="Filter"/> 
									&nbsp;&nbsp;	
			 					</td>
					 		</tr>	
						 </table>
						</div>
					</form>
				</div>
				<div class="tbl"></div>
				<div class="clear"></div>
			

		<div style="max-width: 1090px; overflow: auto;" id="requestRequisitionReportTable">
			<table
				class="dataTbl display tablesorter addNewRow alignCenter"
				id="records_tableId" style="display: block;overflow: auto;height: 295px;" >
				<thead>
					<tr class="content">
					<%-- <th align="center" valign="middle">S.NO</th> --%>
						<th align="center" valign="middle">RRF ID</th>
						<th align="center" valign="middle">Requested By</th>
						<th align="center" valign="middle">Status</th>
						<th align="center" valign="middle">Positions</th>
						<th align="center" valign="middle">Required Skill</th>
						<th align="center" valign="middle">Designation</th>
						<th align="center" valign="middle">Open</th>
						<th align="center" valign="middle">ShortListed</th>
						<th align="center" valign="middle">Closed</th>
						<th align="center" valign="middle">Not Fit <br/> For <br/> Requirement</th>
						<th align="center" valign="middle">On Hold</th>
						<th align="center" valign="middle">Lost</th>
						<th align="center" valign="middle">Submissions</th>
						<th align="center" valign="middle">requirementId</th>
					</tr>
				</thead>
				<tbody>
					
					<c:forEach var="reportData" items="${reportDataList}">
						<tr class="content"  class="abc even odd">
							<td> ${reportData.requirementId}</td>
							<td> ${reportData.requestedBy} </td>
							<td> ${reportData.status}</td>
							<td> ${reportData.noOfResources}</td>
							<td> ${reportData.skill}</td>
							<td> ${reportData.designationName}</td>
							<td>${reportData.open} </td>
							<td class="shortlisted-resources">
							<c:choose>
							    <c:when test="${reportData.shortlisted !=null && reportData.shortlisted !='0'}">
							        <a href="#" onclick=""> ${reportData.shortlisted}</a>
							    </c:when>
							    <c:otherwise>
							        ${reportData.shortlisted}
							    </c:otherwise>
							</c:choose>
							</td>
							<td class="closed-resources">
							<c:choose>
							    <c:when test="${reportData.closed !=null && reportData.closed !='0'}">
							        <a href="#" onclick="">  ${reportData.closed} </a>
							    </c:when>
							    <c:otherwise>
							         ${reportData.closed}
							    </c:otherwise>
							</c:choose>
							</td>
							<td class="notfit-resources">
							<c:choose>
							    <c:when test="${reportData.notFitForRequirement !=null && reportData.notFitForRequirement !='0'}">
							        <a href="#" onclick="">  ${reportData.notFitForRequirement} </a>  
							    </c:when>
							    <c:otherwise>
							         ${reportData.notFitForRequirement}
							    </c:otherwise>
							</c:choose>
							</td>
							<td> ${reportData.hold}</td>
							<td> ${reportData.lost}</td>
							<td class="submission-resources">
								<c:choose>
							    <c:when test="${reportData.submissions !=null && reportData.submissions !='0'}">
							        <a href="#" onclick="">  ${reportData.submissions} </a>   
							    </c:when>
							    <c:otherwise>
							         ${reportData.submissions}
							    </c:otherwise>
							</c:choose>
						 </td>
						 <td>
						 	${reportData.id}
						 </td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div>
			<table id="NoAllocMessage"></table>
		</div>
	 </div>
	</div>
</div>
</body>
</html>