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

<%-- <spring:url value="/resources/styles/themes/default/style.min.css" var="style_css"/>
<link href="${style_css}" rel="stylesheet" type="text/css"></link>

<spring:url value="/resources/js-framework/jstree/jquery.jstree.js"
	var="js_tree" />
	<spring:url value="/resources/js-framework/jstree.min.js"
	var="jstree_min_js" />
<script src="${js_tree}" type="text/javascript"></script>
<script src="${jstree_min_js}" type="text/javascript"></script> --%>
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
	$(document)
			.ready(
					function() {
						$('#ticketDetailTable')
								.dataTable(
										{
											"bProcessing" : true,
											"bAutoWidth" : false,
											"bScrollCollapse" : true,
											"bPaginate" : true,
											"bDestory" : true,
											"bRetrieve" : true,
											"bStateSave": true,
											//  "sDom": '<"projecttoolbar">lfrtip',
											"aaSorting" : [ [ 0, 'asc' ] ],
											"oLanguage" : {
												"sLengthMenu" : 'Show <SELECT>'
														+ '<OPTION value=10>10</OPTION>'
														+ '<OPTION value=25>25</OPTION>'
														+ '<OPTION value=50>50</OPTION>'
														+ '<OPTION value=100>100</OPTION>'
														+ '<OPTION value=200>200</OPTION></SELECT> entries'
											},

											"bSortCellsTop" : true,

											//"sDom": '<"projecttoolbar">lfrtip'
											"sDom" : '<"projecttoolbar">lfip<"top">rt<"bottom"<"#refresh">ip><"clear">'
										// "sDom": '<"top"i>rt<"bottom"<"#refresh">flp><"clear">'
										});
					});
</script>
 
<script>

function getEmployeeByModule() {
	
	var projectId = $('#transferFunModuleId').val();
	
	$.ajax({
		url : "../caticket/getEmployeeByModule/",
		data : "projectId=" + projectId,
		success : function(response) {

			var option = '';
			$('#transferFunAssigneeName').find('option').remove();
			
			option = '<option value="-1">Please Select</option>';
			$('#transferFunAssigneeName').append(option);
			for (var i = 0; i < response.length; i++) {
				var obj = response[i];

					option = "<option value='"+obj.employeeId+"'>"+obj.employeeName+"</option>";

				$('#transferFunAssigneeName').append(option);
				
				
			}
			
			var option = '';
			$('#transferFunIRMNameId').find('option').remove();
			
			option = '<option value="-1">Please Select</option>';
			$('#transferFunIRMNameId').append(option); 
			
			$("#transferFunIRMNameId").parent().find("input").val("Please Select");
			for (var i = 0; i < response.length; i++) {
				var obj = response[i];

					option = "<option value='"+obj.employeeId+"'>"+obj.employeeName+"</option>";

				$('#transferFunIRMNameId').append(option);
				
				
			}
			
		},
		error : function(data) {
		}
	});	

}

	function saveTransferFunctionality() {
		var ticketId = $('#transferFunTicketIdHidden').val();
		var moduleId = $('#transferFunModuleId').val();
		var assigneeName = $('#transferFunAssigneeName').val();
		var reviewer = $('#transferFunIRMNameId').val();
		var justified = $('#transferFunJustifiedId').val();
		var reasonForHopping = $('#transferFunReasonId').val();
		if (ticketId == -1 || assigneeName == -1 || reviewer == -1 || justified == -1 ) {
			showError("Please Select all options");
			return false;
		}
		if (justified == "Yes" && (reasonForHopping == "-1" || reasonForHopping == null)){
			showError("Please give reason for Hopping");
			return false;
		}
		$
				.ajax({
					url : "../caticket/saveTransferFunctionality",
					data : "ticketId=" + ticketId + "&moduleId=" + moduleId
							+ "&assigneeName=" + assigneeName + "&reviewer="
							+ reviewer + "&justified=" + justified + "&reason="
							+ reasonForHopping,
					success : function(data) {
						$('#changeTransferFunctionalityDialog').parent().hide();
						showSuccess("Updated Successfully");
						window.location.reload();
					},
					error : function() {

						showError("Failed to Save changes.");
					}
				});

		$
				.ajax({
					url : "../caticket/getModuleByEmployee",
					data : "employeeId=" + employeeId,
					success : function(response) {

						var option = '';
						$('#transferFunModuleId').find('option').remove();
						option = '<option value="-1" selected=selected>Please Select</option>';
						$('#transferFunModuleId').append(option);
						for (var i = 0; i < response.length; i++) {
							var obj = response[i];
							option = '<option value="' +obj.id + '">'
									+ obj.moduleName + '</option>';
							$('#transferFunModuleId').append(option);
						}

					},
					error : function(data) {
					}
				});
	}

	function saveChangeFunctionality() {
		var ticketId = $('#changeFunTicketIdHidden').val();
		var assigneeName = $('#ChangeFunAssigneeName').val();
		var priority = $('#changeFunPriorityId').val();
		var reviewer = $('#changeFunReviewerId').val();
		var moduleId=$('#changeFunModuleHidden').val();
		
		if (ticketId == -1 || assigneeName == -1 || priority == -1
				|| reviewer == -1) {
			showError("Please Select all options");
			return false;
		}
		$.ajax({
			url : "../caticket/saveChangeFunctionality",
			data : "ticketId=" + ticketId + "&assigneeName=" + assigneeName
					+ "&priority=" + priority + "&reviewer=" + reviewer + "&moduleId=" + moduleId,
			success : function(data) {
				$('#changeFunctionalityDialog').parent().hide();
				showSuccess("Updated Successfully");
				window.location.reload();
			},
			error : function() {
			}
		});

	}

	function changeFunPopulateIRM() {
		var employeeId = $('#ChangeFunAssigneeName').val();
		$.ajax({
			url : "../caticket/getResource/",
			data : "employeeId=" + employeeId,
			success : function(data) {
				var data = JSON.parse(data);
				$("#changeFunIRMNameId").children().remove().end().append(
						"<option value='"+data.resourceData[0].id+"'>"
								+ data.resourceData[0].irmName + "</option>");
			},
			error : function(data) {
			}
		});

	}
	function transferFunPopulateIRM() {
		/* var employeeId = $('#transferFunAssigneeName').val();
		$.ajax({
			url : "../caticket/getResource",
			data : "employeeId=" + employeeId,
			success : function(data) {
				var data = JSON.parse(data);

				$("#transferFunIRMNameId").parent().find("input").val(
						data.resourceData[0].irmName);
				$("#transferFunIRMNameId").children().remove().end().append(
						"<option selected='selected' value='"+data.resourceData[0].id+"'>"
								+ data.resourceData[0].irmName + "</option>");

				 $("#transferFunIRMNameId").children().remove().end().append(
						"<option value='"+data.resourceData[0].id+"'>"
								+ data.resourceData[0].irmName + "</option>"); 
			},
			error : function(data) {
			}
		}); */
	}

	function changeFunctionality(ticketId, caTicketNumber, assingeeName,
			reviewer, priority, projectId) {
		$('#changeFunReviewerId').find('option').remove();
		$("#changeFunModuleHidden").val(projectId);
		$("#changeFunctionalityDialog").dialog({
			width : 400,
			height : 350
		});
		$("#changeFunctionalityDialog").dialog("option", "hide", {
			effect : "explode",
			duration : 200
		});
		$("#changeFunctionalityDialog").dialog({
			show : {
				effect : "explode",
				duration : 200
			}
		});
		$('#changeFunctionalityDialog').dialog().show();
		
		$.ajax({
			url : "../caticket/getEmployeeByModule/",
			data : "projectId=" + projectId,
			   success : function(response) {
				   
				   for (var i = 0; i < response.length; i++) {
						var obj = response[i];
						if (obj.employeeId == reviewer) {
							
							option = "<option selected='selected' value='"+reviewer+"'>"+obj.employeeName+"</option>";
						} else {
							option = "<option value='"+obj.employeeId+"'>"+ obj.employeeName + "</option>";
						}

						$('#changeFunReviewerId').append(option);
					}   
				   
			   },
			   error : function() {

			    showError("Failed to Save changes.");
			   }
			  });
		

		$.ajax({
			url : "../caticket/getEmployeeByModule/",
			data : "projectId=" + projectId,
			success : function(response) {

				var option = '';

				for (var i = 0; i < response.length; i++) {
					var obj = response[i];
					if (obj.employeeId == assingeeName) {

						option = "<option selected='selected' value='"+assingeeName+"'>"+obj.employeeName+"</option>";
					} else {
						option = "<option value='"+obj.employeeId+"'>"+ obj.employeeName + "</option>";
					}

					$('#ChangeFunAssigneeName').append(option);
				}
			},
			error : function(data) {
			}
		});

		$.ajax({
			url : "../caticket/getResource",
			data : "employeeId=" + assingeeName,
			success : function(data) {
				var data = JSON.parse(data);

				$('#changeFunTicketId').val(caTicketNumber);
				$('#changeFunTicketIdHidden').val(ticketId);

				$("#changeFunPriorityId option[value='" + priority + "']")
						.prop('selected', true);
				$("#changeFunPriorityId").parent().find("input").val(priority);

				$("#changeFunIRMNameId").parent().find("input").val(
						data.resourceData[0].irmName);

				$("#changeFunIRMNameId").append(
						"<option selected='selected' value='"+data.resourceData[0].id+"'>"
								+ data.resourceData[0].irmName + "</option>");

			},
			error : function() {

				showError("Failed to Save changes.");
			}
		});

	}
	function changeTransferFunctionality(ticketId, caTicketNumber, projectId,
			assingeeName, reviewerName, reasonForHop, justifiedHop, moduleName) {
				$('#transferFunIRMNameId').find('option').remove();
		$("#changeTransferFunctionalityDialog").dialog({
			width : 450,
			height : 380
		});
		$("#changeTransferFunctionalityDialog").dialog("option", "hide", {
			effect : "explode",
			duration : 200
		});
		$("#changeTransferFunctionalityDialog").dialog({
			show : {
				effect : "explode",
				duration : 200
			}
		});
		$('#changeTransferFunctionalityDialog').dialog().show();

		
		 $.ajax({
			 url : "../caticket/getEmployeeByModule/",
				data : "projectId=" + projectId,
			   success : function(response) {
				   for (var i = 0; i < response.length; i++) {
						var obj = response[i];
						if (obj.employeeId == reviewerName) {

							option = "<option selected='selected' value='"+obj.employeeId+"'>"+obj.employeeName+"</option>";
						} else {
							option = "<option value='"+obj.employeeId+"'>"
									+ obj.employeeName + "</option>";
						}

						$('#transferFunIRMNameId').append(option);
					}   
				   
			   },
			   error : function() {

			    showError("Failed to Save changes.");
			   }
			  }); 
		
		
		$.ajax({
			url : "../caticket/getEmployeeByModule/",
			data : "projectId=" + projectId,
			success : function(response) {
				$('#transferFunAssigneeName').find('option').remove();
				var option = '';

				for (var i = 0; i < response.length; i++) {
					var obj = response[i];
					if (obj.employeeId == assingeeName) {

						option = "<option selected='selected' value='"+obj.employeeId+"'>"+obj.employeeName+"</option>";
					} else {
						option = "<option value='"+obj.employeeId+"'>"
								+ obj.employeeName + "</option>";
					}

					$('#transferFunAssigneeName').append(option);
				}
			},
			error : function(data) {
			}
		});
		
		
						$('#transferFunTicketId').val(caTicketNumber);
						$("#transferFunModuleId option[value=" + projectId + "]")
								.prop('selected', true);
						$("#transferFunModuleId").parent().find("input").val(
								moduleName);

						$("#transferFunJustifiedId option[value="
										+ justifiedHop + "]").prop('selected',
								true);
						$("#transferFunJustifiedId").parent().find("input")
								.val(justifiedHop);

						$('#transferFunReasonId').val(reasonForHop);

						$('#transferFunTicketIdHidden').val(ticketId);

	}
</script>

<div class="mid_section">

	<c:choose>
		<c:when test="${reviewerPage == true}">
			<h1>For My Review</h1>
		</c:when>
		<c:otherwise>
			<h1>My Ticket Queue</h1>
		</c:otherwise>
	</c:choose>
	<br>
	<div class="center_div">
		<div class="" style="width: 1290px;">
			<div class="btnIcon" style="top:0px !important;">

				<a id="addNew" class="blue_link" href="createTicketHome"> <img
					width="16" height="22"
					src="${pageContext.request.contextPath}/resources/images/addUser.gif">
					Add New
				</a>
			</div>
			<%-- <sec:authorize
				access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN', 'ROLE_MANAGER')">
				<div align="right" id="toolTip" style="color: red">*
					Highlighted row indicates tickets which are to be reviewed.</div>
			</sec:authorize> --%>
			<div class="clear"></div>
			<br> <br>
			<%-- <div align="center" class="addNewContribution">
				<span class="addNewContainer"><a href="createTicketHome"
					class="blue_link" id="addNewContrbutionId"><img
						src="${pageContext.request.contextPath}/resources/images/addUser.gif"
						width="16" height="22"> Add New </a></span>
			</div>&nbsp;&nbsp; --%>
			<table class="dataTbl display tablesorter dataTable"
				id="ticketDetailTable"
				style="margin-top: 0px; margin-left: 0px !important;">
				<thead>
					<tr>
						<th align="center" valign="middle" rowspan="2">Change</th>
						<th align="center" valign="middle" rowspan="2">Transfer</th>
						<th align="center" valign="middle" rowspan="2">Ticket No.</th>
						<th align="center" valign="middle" rowspan="2">Module</th>
						<th align="center" valign="middle" rowspan="2">Priority</th>
						<th align="center" valign="middle" rowspan="2">LandScape</th>

						<th align="center" valign="middle" rowspan="2">Region</th>
						<th align="center" valign="middle" rowspan="2">Aging</th>
						<th align="center" valign="middle" rowspan="2">SLA Missed</th>
						<th align="center" valign="middle" rowspan="2">Days Open</th>

					</tr>
					<tr>
						<th align="center" valign="middle">Ack</th>
						<th align="center" valign="middle">Phase</th>
						<th align="center" valign="middle">Prob Mgmt</th>
						<th align="center" valign="middle">T3 Sol</th>
						<th align="center" valign="middle">Sol Review</th>
						<th align="center" valign="middle">Defect Log</th>
						<th align="center" valign="middle">CROP</th>
						<th align="center" valign="middle">ReWork</th>
					</tr>
				</thead>


				<tbody id="ticketDetailTableBody">
					<c:forEach var="caticket" items="${catickets}">
						<sec:authorize
							access="hasAnyRole('ROLE_ADMIN','ROLE_DEL_MANAGER','ROLE_BG_ADMIN', 'ROLE_MANAGER')">
							<c:choose>
								<c:when test="${caticket.reviewer.employeeId == LoginId}">
									<tr class="redColor">
								</c:when>
								<c:otherwise>
									<tr>
								</c:otherwise>
							</c:choose>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('ROLE_USER')">
							<tr>
						</sec:authorize>
						
						<c:choose>
						<c:when test="${empty caticket.closePendingCustomerApprovalDate }">
						<td align="center" valign="middle" >
						<img title="Change"
							src="${pageContext.request.contextPath}/resources/images/change.png"
							onclick="changeFunctionality('${caticket.id}',' ${caticket.caTicketNo}','${caticket.assigneeId.employeeId}','${caticket.reviewer.employeeId}','${caticket.priority}','${caticket.moduleId.id}')" />
							</td>
						<td align="center" valign="middle"><img title="Transfer"
							src="${pageContext.request.contextPath}/resources/images/transfer.png"
							onclick="changeTransferFunctionality('${caticket.id}','${caticket.caTicketNo}','${caticket.moduleId.id}','${caticket.assigneeId.employeeId}','${caticket.reviewer.employeeId}','${caticket.reasonForHopping.id}','${caticket.justifiedHopping}','${caticket.moduleId.moduleName}')" /></td>
						</c:when>
						<c:otherwise>
						<td align="center" valign="middle" >Not Applicable</td>
						<td align="center" valign="middle" >Not Applicable</td>
						</c:otherwise>
						</c:choose>
						
						<td align="center" valign="middle"><a
							href="${pageContext.request.contextPath}/caticket/getTicketById/${caticket.id}"><c:out
									value="${caticket.caTicketNo}" /></a></td>
						<td align="center" valign="middle"><c:out
								value="${caticket.moduleId.moduleName}" /></td>
						<td align="center" valign="middle"><c:out
								value="${caticket.priority}" /></td>
						<td align="center" valign="middle"><c:out
								value="${caticket.landscapeId.landscapeName}" /></td>
						<td align="center" valign="middle"><c:out
								value="${caticket.region.regionName}" /></td>
						<td align="center" valign="middle"><c:out
								value="${caticket.aging}" /></td>
						<c:choose>
							<c:when test="${caticket.slaMissed == 'Yes'}">
								<td class="redColorResource" align="center" valign="middle">
									<c:out value="${caticket.slaMissed}" />
								</td>
							</c:when>
							<c:otherwise>
								<td align="center" valign="middle"><c:out
										value="${caticket.slaMissed}" /></td>
							</c:otherwise>
						</c:choose>
						<%-- <td align="center" valign="middle"><c:out
								value="${caticket.slaMissed}" /></td> --%>
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

						<c:choose>
							<c:when test="${not empty caticket.acknowledgedDate}">
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/approve.png" /></td>
							</c:when>
							<c:otherwise>
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/reject_timesheet.png" /></td>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${empty caticket.acknowledgedDate}">
								<td align="center" valign="middle"><img title="Reject"
									src="${pageContext.request.contextPath}/resources/images/reject_timesheet.png" />
									<br> Not Acknowledged (<c:out
										value="${caticket.daysOpenForPhase}"></c:out>)
								</td>
							</c:when>
							<c:when test="${empty caticket.requiredCompletedDate && caticket.reqCompleteFlag == 'No'}">
								<td align="center" valign="middle"><img title="Reject"
									src="${pageContext.request.contextPath}/resources/images/reject_timesheet.png" />
									<br> Req Pending (<c:out
										value="${caticket.daysOpenForPhase}"></c:out>)
								</td>
							</c:when>
							<c:when test="${empty caticket.analysisCompletedDate && caticket.analysisCompleteFlag == 'No'}">
								<td align="center" valign="middle"><img title="Reject"
									src="${pageContext.request.contextPath}/resources/images/reject_timesheet.png" />
									<br> Not Analysed (<c:out
										value="${caticket.daysOpenForPhase}"></c:out>)
								</td>
							</c:when>
							<c:when test="${empty caticket.solutiondevelopedDate && caticket.solutionDevelopedFlag == 'No'}">
								<td align="center" valign="middle"><img title="Reject"
									src="${pageContext.request.contextPath}/resources/images/reject_timesheet.png" />
									<br> Sol not developed (<c:out
										value="${caticket.daysOpenForPhase}"></c:out>)
								</td>
							</c:when>
							<c:when test="${empty caticket.solutionreViewDate && caticket.solutionReadyForReview == 'No'}">
								<td align="center" valign="middle"><img title="Reject"
									src="${pageContext.request.contextPath}/resources/images/reject_timesheet.png" />
									<br> Sol not reviewed (<c:out
										value="${caticket.daysOpenForPhase}"></c:out>)
								</td>
							</c:when>
							<c:when test="${empty caticket.solutionreViewDate && caticket.solutionReadyForReview == 'Yes'}">
								<td align="center" valign="middle"><img title="Reject"
									src="${pageContext.request.contextPath}/resources/images/reject_timesheet.png" />
									<br> Sol not reviewed (<c:out
										value="${caticket.daysOpenForPhase}"></c:out>)
								</td>
							</c:when>
							<c:when test="${empty caticket.solutionAcceptedDate && caticket.solutionAcceptedFlag == 'No'}">
								<td align="center" valign="middle"><img title="Reject"
									src="${pageContext.request.contextPath}/resources/images/reject_timesheet.png" />
									<br> In Testing (<c:out
										value="${caticket.daysOpenForPhase}"></c:out>)
								</td>
							</c:when>
							<c:when test="${empty caticket.closePendingCustomerApprovalDate && caticket.customerApprovalFlag == 'No'}">
								<td align="center" valign="middle"><img title="Reject"
									src="${pageContext.request.contextPath}/resources/images/reject_timesheet.png" />
									<br> In Closing (<c:out
										value="${caticket.daysOpenForPhase}"></c:out>)
								</td>
								
							</c:when>
							<c:otherwise>
							<td align="center" valign="middle"><img title="Approve"
									src="${pageContext.request.contextPath}/resources/images/approve.png" />
									</td>
							</c:otherwise>
						</c:choose>


						<c:choose>
							<c:when test="${caticket.problemManagementFlag == 'Yes'}">
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/approve.png" /></td>
							</c:when>
							<c:when test="${caticket.problemManagementFlag == 'N.A.'}">
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/NAIcon.png" /></td>
							</c:when>
							<c:otherwise>
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/reject_timesheet.png" /></td>
							</c:otherwise>
						</c:choose>


						<c:choose>
							<c:when test="${caticket.t3StatusFlag == 'Yes'}">
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/approve.png" /></td>
							</c:when>
							<c:when test="${caticket.t3StatusFlag == 'N.A.'}">
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/NAIcon.png" /></td>
							</c:when>
							<c:otherwise>
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/reject_timesheet.png" /></td>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${caticket.solutionReadyForReview == 'Yes' && not empty caticket.solutionreViewDate}">
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/approve.png" /></td>
							</c:when>
							<c:when test="${caticket.solutionReadyForReview == 'N.A.'}">
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/NAIcon.png" /></td>
							</c:when>
							<c:otherwise>
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/reject_timesheet.png" /></td>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${caticket.defectStatusFlag == 'Yes'}">
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/approve.png" /></td>
							</c:when>
							<c:when test="${caticket.defectStatusFlag == 'N.A.'}">
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/NAIcon.png" /></td>
							</c:when>
							<c:otherwise>
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/reject_timesheet.png" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${caticket.cropStatusFlag == 'Yes'}">
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/approve.png" /></td>
							</c:when>
							<c:when test="${caticket.cropStatusFlag == 'N.A.'}">
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/NAIcon.png" /></td>
							</c:when>
							<c:otherwise>
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/reject_timesheet.png" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${caticket.reworkStatusFlag == 'Yes'}">
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/approve.png" /></td>
							</c:when>
							<c:when test="${caticket.reworkStatusFlag == 'N.A.'}">
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/NAIcon.png" /></td>
							</c:when>
							<c:otherwise>
								<td align="center" valign="middle"><img title="Approved"
									src="${pageContext.request.contextPath}/resources/images/reject_timesheet.png" /></td>
							</c:otherwise>
						</c:choose>


						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<!-- file alert-->
<div id="fileAlert">
	<p></p>
	<div class="fileAlertBtm">
		<input type="button" name="Ok" value="Ok" id="ok" />
	</div>
</div>

<div id="changeFunctionalityDialog"
	style="background: white; #3377 BB; display: none;"
	title="Change Assignee">
	<input type="hidden" id="changeFunTicketIdHidden">
	<input type="hidden" id="changeFunModuleHidden">
	<table
		style="margin-left: 2%; padding-top: 20px; border-collapse: separate; border-spacing: 15px;">
		<tr>
			<td style="color: black;">Ticket No :</td>
			<td><input type="text" id="changeFunTicketId"
				style="border: solid 1px #d5d5d5; width: 140px; height: 25px;"
				disabled="disabled"></td>
		</tr>
		<tr>
			<td style="color: black;">Assignee Name :</td>
			<%-- <td><c:if test="${not empty eligibleResources}">

					<select id="ChangeFunAssigneeName" class=""
						style="width: 140px;" onchange="changeFunPopulateIRM()">
						<option value="-1" selected="selected">Please Select</option>
						<c:forEach var="eligibleResources" items="${eligibleResources}">
							<option value="${eligibleResources.employeeId}">
								${eligibleResources.firstName} ${eligibleResources.lastName}</option>
						</c:forEach>
					</select>
				</c:if></td> --%>
			<td><select id="ChangeFunAssigneeName" class=""
				style="width: 140px;" onchange="changeFunPopulateIRM()">
					<%--<option value="-1" selected="selected">Please Select</option>
						 <c:forEach var="resources" items="${resources}">
							<option value="${resources.employeeId}">
								${resources.firstName} ${resources.lastName}</option>
						</c:forEach> --%>
			</select></td>
		</tr>
		<tr>
			<td style="color: black;">Priority :</td>
			<td><select class="" id="changeFunPriorityId"><option
						value="-1">Please Select</option>
					<option value="1 - Urgent">1 - Urgent</option>
					<option value="2 - High">2 - High</option>
					<option value="3 - Medium">3 - Medium</option>
					<option value="4 - Low">4 - Low</option>
					<option value="5 - Project">5 - Project</option></select></td>
		</tr>
		<tr>
			<td style="color: black;">IRM Name :</td>
			<td><select id="changeFunIRMNameId" disabled="disabled"
				style="width: 140px; height: 25px;"></select></td>
		</tr>
		<tr>
  			<td style="color: black;">Reviewer Name :</td>
  			<td><select id="changeFunReviewerId" class=""
    			style="width: 140px; height: 25px;"></select></td>
  		</tr>
	</table>
	<div style="float: right;">
		<button onclick="saveChangeFunctionality()">Save</button>
	</div>


</div>


<div id="changeTransferFunctionalityDialog"
	style="background: white; #3377 BB; display: none;"
	title="Transfer Ticket">
	<div class="mid_section">
		<input type="hidden" id="transferFunTicketIdHidden">
		<table
			style="margin-left: 2%; padding-top: 20px; border-collapse: separate; border-spacing: 15px;">
			<tr>
				<td style="color: black;">Ticket No :</td>
				<td><input type="text"
					style="border: solid 1px #d5d5d5; width: 140px; height: 25px;"
					disabled="disabled" id="transferFunTicketId"></td>
			</tr>
			<tr>
				<td style="color: black;">Module :</td>
				<td><select class="" id="transferFunModuleId" onchange="getEmployeeByModule()">
						<option value="-1">Please Select</option>
						<c:forEach var="project" items="${projects}">
							<option value="${project.id}">${project.moduleName}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td style="color: black;">Assignee Name :</td>
				<%-- <td><c:if test="${not empty eligibleResources}">

						<select id="transferFunAssigneeName" style="width: 140px;"
							class="" onchange="transferFunPopulateIRM()">
							<option value="-1" selected="selected">Please Select</option>
							<c:forEach var="eligibleResources" items="${eligibleResources}">
								<option value="${eligibleResources.employeeId}">
									${eligibleResources.firstName} ${eligibleResources.lastName}</option>
							</c:forEach>
						</select>
					</c:if></td> --%>
				<td><%-- <c:if test="${not empty resources}"> --%>

						<select id="transferFunAssigneeName" style="width: 140px;"
							class="" onchange="transferFunPopulateIRM()">
							<!-- <option value="-1" selected="selected">Please Select</option> -->
							<%-- <c:forEach var="resources" items="${resources}">
								<option value="${resources.employeeId}">
									${resources.firstName} ${resources.lastName}</option>
							</c:forEach> --%>
						</select>
					<%-- </c:if> --%></td>

			</tr>
			<tr>
				<td style="color: black;">Reviewer Name :</td>
				<td><Select class="" id="transferFunIRMNameId"
					style="width: 141px; height: 25px;"></Select></td>
			</tr>
			<tr>
				<td style="color: black;">Justified Hopping :</td>
				<td><select class="" id="transferFunJustifiedId"
					selected="selected"><option value="-1">Please
							Select</option>
						<option value="Yes">Yes</option>
						<option value="No">No</option>
						<option value="N.A.">N.A.</option></select></td>
			</tr>
			<tr>
				<td style="color: black;">Reason For Hopping :</td>
				<!-- <td><input type="text" class="" id="transferFunReasonId"
					style="width: 140px; height: 25px;"></td> -->
					<td><select id="transferFunReasonId" style="width: 140px; height: 25px;"><option value="-1" selected="selected">Please Select</option>
							<c:forEach var="hop" items="${reasonForHopping}">
								<option value="${hop.id}">
									${hop.reason}</option>
							</c:forEach></select></td>
			</tr>
		</table>
		<div style="float: right;">
			<button onclick="saveTransferFunctionality()">Save</button>
		</div>

	</div>
</div>