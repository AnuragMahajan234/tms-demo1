<%@ page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<spring:url value="resources/js-framework/datatables/TableTools.js?ver=${app_js_ver}" var="tableTools_js"/>
<script src="${tableTools_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
</script>
<spring:url
		value="js-framework/datatables/jquery.dataTables.min.js?ver=${app_js_ver}"
		var="jquery_dataTables_min_js" />
<spring:url value="resources/js-framework/datatables/ZeroClipboard.js?ver=${app_js_ver}" var="ZeroClipboard_js"/>
<script src="${ZeroClipboard_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
</script>

<spring:url value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
    var="ColReorder_js" />
<spring:url value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
    var="ColVis_js" />

<script type="text/javascript">
            $(document).ready(function(){
                $("#projectDetails tr:odd").addClass("odd");
                $("#projectDetails tr:not(.odd)").addClass("even");  
            });
</script>


<div class="mid_section">
	<!--right section-->
	<sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_ADMIN','ROLE_BEHALF_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
	 <div align="left">
	 	
	 	<table  align="left"  cellpadding="5" cellspacing="5">
			<table align="left">
				<tr>
				<td>
					<th align="left" >
					  Employee Name 
					</th>
					<th align="left" >
					: ${resourceName}
					</th>
				</td>
				</tr>
				<tr>
				<td>
					<th align="left" >
					  IRM 
					</th>
					<th align="left" >
					: ${resourceRM1}
					</th>
				</td>
				</tr>
				<tr>
				<td>
					<th align="left" >
					  SRM 
					</th>
					<th align="left" >
					: ${resourceRM2}
					</th>
				</td>
				
				</tr>
				
				  <!-- for current location and base location enhancement by Pratyoosh -->
				<tr>
				<td>
					<th align="left" >
					  Current Location
					</th>
					<th align="left" >
					: ${locations}
					</th>
				</td>
				</tr>
				
		</table>
		
		<table align="right">
				<tr>
				<td>
					<th align="left" >
					  Current BU 
					</th>
					<th align="left" >
					: ${resourceCurrentBu}
					</th>
				</td>
				</tr>
				<tr>
				<td>
					<th align="left" >
					  Parent BU 
					</th>
					<th align="left" >
					: ${resourceParentBu}
					</th>
				</td>
				</tr>
					<tr>
				<td>
					<th align="left" >
					  Skill
					</th>
					<th align="left" >
					: ${resourceCompetancy}
					</th>
				</td>
				</tr>
				
			 <!-- for current location and base location enhancement by Pratyoosh -->			
				<tr>
				<td>
					<th align="left" >
					  Base Location
					</th>
					<th align="left" >
					: ${location}
					</th>
				</td>
				</tr>
			 
		</table>	
	</div>
	</sec:authorize>
</div>

<div style="clear: both;"></div>
<div class="tab_seaction" style="margin-top:20px;">
	<sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_ADMIN','ROLE_BEHALF_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
	 <div align="left">
		 <table id= "projectDetails" cellpadding="5" cellspacing="5" class="display dataTable" style="width:600px;">
		 	<thead> 
				<tr>
							<th width="15%" align="center" >Project Name</th>
							<th width="10%" align="center" >Allocation Type</th>
							<th width="15%" align="center">Allocation Start Date</th>
							<!-- <th width="10%" align="center" valign="middle">Allocation End Date</th> -->
							<th width="40%" align="center" >Allocation Remarks</th>
							<th width="40%" align="center">Primary Project</th>
							
				</tr>
			</thead>
			<tbody>
					<c:if test="${not empty allocationList}">
						<c:forEach var="allocationList" items="${allocationList}">
							<tr>
								<td align="center" valign="middle">${allocationList.projectId.projectName}</td>
								<td align="center" valign="middle">${allocationList.allocationTypeId.allocationType}</td>
								
								<%-- <fmt:parseDate value="${allocationList.allocStartDate}" pattern="yyyy-mm-dd" var="myDate"/> --%>
								<fmt:formatDate value="${allocationList.allocStartDate}" var="myDate" pattern="dd-MMM-yyyy" type = "date"/>
								
								<td align="center" valign="middle">${myDate}</td>
								<%-- <td align="center" valign="middle">${allocationList.allocEndDate}</td> --%>
								<td align="center" valign="middle">${allocationList.allocRemarks}</td>
								<c:if test="${allocationList.curProj == 'true' }"> 
									<td align="center" valign="middle">Yes</td>
								</c:if>
								<c:if test="${allocationList.curProj == 'false' }"> 
									<td align="center" valign="middle">No</td>
								</c:if>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
	 		<%-- <c:if test="${not empty allocationList}">

				<c:forEach var="allocationList" items="${allocationList}">
						${allocationList.projectId.projectName}
				</c:forEach>
				<c:forEach var="allocationList" items="${allocationList}">
						${allocationList.allocationTypeId.allocationType}
				</c:forEach>
				
				
				{{if curProj}}
									Yes
								  {{else}}
									  No
								{{/if}}
				
				
			</c:if> --%>
	 </table>
	</div>
	</sec:authorize>

		
</div>

