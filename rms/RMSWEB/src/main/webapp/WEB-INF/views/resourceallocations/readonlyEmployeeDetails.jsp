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
	value="resources/js-framework/datatables/TableTools.js?ver=${app_js_ver}"
	var="tableTools_js" />
<script src="${tableTools_js}" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<spring:url
	value="js-framework/datatables/jquery.dataTables.min.js?ver=${app_js_ver}"
	var="jquery_dataTables_min_js" />
<spring:url
	value="resources/js-framework/datatables/ZeroClipboard.js?ver=${app_js_ver}"
	var="ZeroClipboard_js" />
<script src="${ZeroClipboard_js}" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>

<spring:url
	value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url
	value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />


<div class="mid_section">
	<!--right section-->
	<sec:authorize
		access="hasAnyRole('ROLE_MANAGER','ROLE_ADMIN','ROLE_BEHALF_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
		<div align="left">
			<table id="readonlylistofResource">
				<table cellpadding="5" cellspacing="5" class="display dataTable"
					style="width: 400px;">
					<thead>
						<tr class="totalColumn">
							<th colspan="2" align="center">Resource Profile</th>
						</tr>
					</thead>
					<tbody>
						<tr class="odd">
							<td>Resource Name</td>
							<td>${resourceName}</td>
						</tr>
						<tr class="even">
							<td>IRM</td>
							<td>${resourceRM1}</td>
						</tr>
						<tr class="odd">
							<td>SRM</td>
							<td>${resourceRM2}</td>
						</tr>
						<tr class="even">
							<td>Current BU</td>
							<td>${resourceCurrentBu}</td>
						</tr>
						<tr class="odd">
							<td>Parent BU</td>
							<td>${resourceParentBu}</td>
						</tr>
						<tr class="even">
							<td>Skill</td>
							<td>${resourceCompetancy}</td>
						</tr>
   <!-- for current location and base location enhancement by Pratyoosh -->
						<tr class="odd">
							<td>Current Location</td>
							<td>${locations}</td>
						</tr>
						<tr class="even">
							<td>Base Location</td>
							<td>${location}</td>
						</tr>
   <!-- for current location and base location enhancement by Pratyoosh -->
					</tbody>
				</table>



			</table>

		</div>
	</sec:authorize>


</div>

