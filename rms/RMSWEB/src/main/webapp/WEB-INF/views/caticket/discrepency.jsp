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
<%-- <spring:url value="/resources/styles/jquery.datetimepicker.css"
	var="datetimepicker_style_css" />
<link href="${datetimepicker_style_css}" rel="stylesheet"
	type="text/css"></link> --%>

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
						$('#discrepencyTicketDetailTable')
								.dataTable(
										{
											"bProcessing" : true,
											"bAutoWidth" : false,
											"bScrollCollapse" : true,
											"bPaginate" : true,
											"bDestory" : true,
											"bRetrieve" : true,
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


<div class="mid_section">

	<h1>Discrepency Tickets</h1>
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
			<div class="clear"></div>
			<br> <br>
			<table class="dataTbl display tablesorter dataTable"
				id="discrepencyTicketDetailTable"
				style="margin-top: 0px; margin-left: 0px !important;">
				<thead>
					<tr>
						<th align="center" valign="middle">Ticket No.</th>
						<th align="center" valign="middle">Module</th>
						<th align="center" valign="middle">Assignee Name</th>
						<th align="center" valign="middle">Reviewer Name</th>
						<th align="center" valign="middle">Priority</th>
						<th align="center" valign="middle">Landscape</th>
						<th align="center" valign="middle">Region</th>
						<th align="center" valign="middle">Description</th>

					</tr>
				</thead>


				<tbody id="discrepencyTicketDetailTableBody">
					<c:forEach var="discTicket" items="${discTickets}">
						<tr>
							<td align="center" valign="middle"><a
								href="${pageContext.request.contextPath}/caticket/getDiscrepenciesTicketById/${discTicket.id}"><c:out
										value="${discTicket.caTicketNo}" /></a></td>
							<td align="center" valign="middle"><c:out
									value="${discTicket.moduleId.moduleName}" /></td>
							<td align="center" valign="middle"><c:out
									value="${discTicket.assigneeId.employeeName}" /></td>
							<td align="center" valign="middle"><c:out
									value="${discTicket.reviewer.employeeName}" /></td>
							<td align="center" valign="middle"><c:out
									value="${discTicket.priority}" /></td>
							<td align="center" valign="middle"><c:out
									value="${discTicket.landscapeId.landscapeName}" /></td>
							<td align="center" valign="middle"><c:out
									value="${discTicket.region.regionName}" /></td>

							<td align="center" valign="middle"><c:out
									value="${discTicket.description}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

