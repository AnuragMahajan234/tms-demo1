<%@ page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<spring:url
	value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}"
	var="multiselect_js" />
<script src="${multiselect_js}" type="text/javascript"></script>


<spring:url
	value="/resources/js-framework/jquery.datetimepicker.js?ver=${app_js_ver}"
	var="datetimepicker_js" />
<script src="${datetimepicker_js }" type="text/javascript"></script>
<spring:url value="/resources/styles/jquery.datetimepicker.css"
	var="datetimepicker_style_css" />
<link href="${datetimepicker_style_css}" rel="stylesheet"
	type="text/css"></link>

<spring:url
	value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}"
	var="multiselect_filter_js" />

<spring:url
	value="/resources/styles/skin/ui.dynatree.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<!-- <link href="resources/styles/skin/ui.dynatree.css" rel="stylesheet" type="text/css" id="skinSheet"> -->
<spring:url
	value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url
	value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />
<spring:url
	value="resources/js-framework/jquery.cookie.js?ver=${app_js_ver}"
	var="js_cookie" />
<spring:url
	value="/resources/js-framework/jquery.dynatree.js?ver=${app_js_ver}"
	var="js_dynatree" />
<script src="${js_cookie}" type="text/javascript"></script>
<script src="${js_dynatree}" type="text/javascript"></script>

<!--   <script src="resources/js-framework/jquery.cookie.js" type="text/javascript"></script>
  <script src="resources/js-framework/jquery.dynatree.js" type="text/javascript"></script> -->

<script src="${multiselect_filter_js}" type="text/javascript"></script>
<style type="text/css" title="currentStyle">
thead input {
	width: 100%
}

input.search_init {
	color: #999
}
</style>
<script type="text/javascript">
	function loadTickets() {
		var htmlVar = '';
		$.ajax({
			url : '${pageContext.request.contextPath}/caticket/getTicketsByPhase',
			contentType : 'application/json',
			 async : false, 
			 dataType: 'json',
			/* data : {
				"resourceid" : $("select#resourceId").val()
			}, */
			success : function(response) {
				 
				htmlVar += '<tbody id="ticketsDetailBody">'; 
				$.each(response, function(index,item) {        
					htmlVar += '<tr>';
					htmlVar += '<td>'+item.caTicketNo+'</td>';
					htmlVar += '<td>'+item.module+'</td>';
					htmlVar += '<td>'+item.priority+'</td>';
					htmlVar += '<td>'+item.landscape+'</td>';
					htmlVar += '<td>'+item.region+'</td>';
					htmlVar += '<td>'+item.aging+'</td>';
					htmlVar += '<td>'+item.slaMissed+'</td>';
					htmlVar += '<td>'+item.daysOpen+'</td>';
					htmlVar += '</tr>';
				});
				htmlVar += '</tbody>';
				$('#tableResourceData').append(htmlVar);
				/* $('#tableResourceData').DataTable({
					 "bDestroy": true,
					 aaSorting: [[0, 'desc']],
					 "bAutoWidth": false
					
				 }); */
     			/* $("#tableResourceData > tbody:last").append($("#resourceAllocationRows").render(resourceHistoryData)); */     
     			//$("#tableResourceDataInfo > tbody:last").append($("#resourceAllocationInfo").render(resourceHistoryData));   
     			containerWidth();
     			$("#dialoglt").dialog('open');
     			$("#tableResourceData").show();
    			$("#dialogText").show();
				/* if (resourceHistoryData == "") {
					alert('Resource Loan or Transfer History data not found');
				} else {

					$("#resourceHData").html("");
					$("#tableResourceData > tbody:last").append(
							$("#resourceAllocationRows").render(
									resourceHistoryData));
					//$("#tableResourceDataInfo > tbody:last").append($("#resourceAllocationInfo").render(resourceHistoryData));   
					containerWidth();
					$("#dialoglt").dialog('open');
					$("#tableResourceData").show();
					$("#dialogText").show();
				} */
			},
			error : function(errorResponse) { 
				showError(errorResponse);
			}
		});
	}
</script>
<script type="text/javascript">
    $(document).ready(function () {
                $("#dialoglt").dialog({ autoOpen: false });
			    $("#dialoglt" ).dialog( "option", "modal", true );
		        $("#dialoglt" ).dialog( "option", "title", "Report" );
		        $("#dialoglt" ).dialog( "option", "buttons", [ { text: "Ok", click: function() { $( this ).dialog( "close" ); } } ] );
		        $("#dialoglt" ).dialog({ closeText: "hide" });
		        $("#dialoglt" ).dialog({ dialogClass: "alert" });
		        $("#dialoglt" ).dialog({ minHeight:250,width: 860,height: 350});
		        $("#dialoglt" ).dialog( "option", "hide", { effect: "explode", duration: 1000 } );		        
		        $("#dialoglt" ).dialog({ show: { effect: "explode", duration: 800 } });		      
    });    
</script>
<div class="mid_section">
	<h1>My Queue</h1>
	<br>
	<div class="center_div">
		<div class="form">
			<input type="button" name="filter" value="Filter"
				style="background-color: #d3d3d3; color: #37B; float: right;" />
			<table cellspacing="10" cellpadding="10">
				<tr>
					<td width="10%">LandScape</td>
					<td><select name="landscapeName"
						class="required comboselect check" id="landscapeId">
							<option value="-1">Please Select</option>
							<option value="LandScape1">LandScape1</option>
							<option value="LandScape2">LandScape2</option>
							<option value="LandScape3">LandScape3</option>
							<option value="LandScape4">LandScape4</option>
					</select></td>
					<td width="10%">Priority</td>
					<td><select name="priorityName"
						class="required comboselect check" id="priorityId">
							<option value="-1">Please Select</option>
							<option value="Priority1">Priority1</option>
							<option value="Priority2">Priority2</option>
							<option value="Priority3">Priority3</option>
							<option value="Priority4">Priority4</option>
					</select></td>
					<td width="10%">Region</td>
					<td><select name="regionName"
						class="required comboselect check" id="regionId">
							<option value="-1">Please Select</option>
							<option value="Region1">Region1</option>
							<option value="Region2">Region2</option>
							<option value="Region3">Region3</option>
							<option value="Region4">Region4</option>
					</select></td>


				</tr>
				<tr>
					<td>Module</td>
					<td><select name="moduleName"
						class="required comboselect check" id="moduleId">
							<option value="-1">Please Select</option>
							<option value="Module1">Module1</option>
							<option value="Module2">Module2</option>
							<option value="Module3">Module3</option>
							<option value="Module4">Module4</option>
					</select></td>
					<td>Reviewer/IRM Name</td>
					<td><select name="reviewerOrIRMName"
						class="required comboselect check" id="reviewerOrIRMId">
							<option value="-1">Please Select</option>
							<option value="ReviewerName1">ReviewerName1</option>
							<option value="ReviewerName2">ReviewerName2</option>
							<option value="ReviewerName3">ReviewerName3</option>
							<option value="ReviewerName4">ReviewerName4</option>
					</select></td>
					<td>Assignee Name</td>
					<td><select name="assigneeName"
						class="required comboselect check" id="assigneeId">
							<option value="-1">Please Select</option>
							<option value="AssigneeName1">AssigneeName1</option>
							<option value="AssigneeName2">AssigneeName2</option>
							<option value="AssigneeName3">AssigneeName3</option>
							<option value="AssigneeName4">AssigneeName4</option>
					</select></td>
				</tr>

			</table>

			<table class="dataTbl display tablesorter dataTable"
				id="ticketDetailTable"
				style="margin-top: 0px; margin-left: 0px !important;">
				<thead>
					<tr>
						<th align="center" valign="middle">Module</th>
						<th align="center" valign="middle">Assignee Name</th>
						<th align="center" valign="middle">Not Acknowledged</th>
						<th align="center" valign="middle">Problem Management</th>
						<th align="center" valign="middle">In Requirement</th>
						<th align="center" valign="middle">In Analysis</th>
						<th align="center" valign="middle">In Testing</th>
						<th align="center" valign="middle">For Review</th>
						<th align="center" valign="middle">For Closure</th>
					</tr>

				</thead>
				<tbody id="ticketDetailTableBody">
					<td align="center">demo</td>
					<td align="center">demo</td>
					<td align="center"><a href="javascript:void(0);"
						onclick="loadTickets();">3</a></td>
					<td align="center">4</td>
					<td align="center">5</td>
					<td align="center">5</td>
					<td align="center">5</td>
					<td align="center">5</td>
					<td align="center">5</td>



					<c:forEach var="caticket" items="${catickets}">
						<tr>
							<td align="center" valign="middle"><a
								href="${pageContext.request.contextPath}/caticket/getTicketbyTicketNumber/${caticket.caTicketNo}"><c:out
										value="${caticket.caTicketNo}" /></a></td>
							<td align="center" valign="middle"><c:out
									value="${caticket.module}" /></td>
							<td align="center" valign="middle"><c:out
									value="${caticket.priority}" /></td>
							<td align="center" valign="middle"><c:out
									value="${caticket.landscape}" /></td>
							<td align="center" valign="middle"><c:out
									value="${caticket.region}" /></td>
							<td align="center" valign="middle"><c:out
									value="${caticket.aging}" /></td>
							<td align="center" valign="middle"><c:out
									value="${caticket.slaMissed}" /></td>
							<td align="center" valign="middle"><c:out
									value="${caticket.daysOpen}" /></td>
							<!-- <td>1234567</td>
							<td>1234567</td>
							<td>1234567</td>
							<td>1234567</td>
							<td>1234567</td>
							<td>1234567</td>
							<td>1234567</td>
							<td>1234567</td>
							<td>1234567</td>
							<td>1234567</td> -->
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<div id="dialoglt"
	style="background: white; width =860; height =350; color: #3377BB;">
	<div id="dialogText" align="center" style="">
		<b>Ticket Details</b>
	
			</h3>
 	</div>

	<div>

		<table id="tableResourceData"
			class="dataTbl display tablesorter addNewRow">
			<thead>
				<tr style="color: red;font-weight: bold">
							<th width="15" align="center" valign="middle">Ticket No</th>
						    <th width="15%" align="center" valign="middle">Module</th>
							<th width="15%" align="center" valign="middle">Priority</th>
							<th width="15%" align="center" valign="middle">LandScape</th>							
							<th width="15%" align="center" valign="middle">Region</th>
							<th width="15%" align="center" valign="middle">Aging</th>
							<th width="15%" align="center" valign="middle">SLA Missed</th>
							<th width="15%" align="center" valign="middle">Days Open</th>
				</tr>
			</thead>
			<tbody id="ticketsDetailBody">
			</tbody>
		</table>

	</div>
</div>
<!-- file alert-->
<div id="fileAlert">
	<p></p>
	<div class="fileAlertBtm">
		<input type="button" name="Ok" value="Ok" id="ok" />
	</div>
</div>
