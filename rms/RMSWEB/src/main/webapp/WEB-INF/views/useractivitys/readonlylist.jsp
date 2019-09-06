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

<script>
</script>
<script type="text/javascript">
    var weekStart;
    var weekEnd;
    var dateString;
    var count=0;
    var countForComment=0;
    var tempId=0;
	$(document).ready(function(){
		 function tally (selector) {
			$(selector).each(function () {
				var total = 0,
					column = $(this).siblings(selector).andSelf().index(this);
				$(this).parents().prevUntil(':has(' + selector + ')').each(function () {
					total += parseFloat($('td.rowSum:eq(' + column + ')', this).html()) || 0;
				});
				$(this).html(total);
			});
		}
		 //tally('td.subtotal');
		 	weektotal=0;
			$("table#reportedHrs ").find("td.subtotal").each(function(){
				weektotal += Number($(this).html());
			});
			$("#weekHrs").html(weektotal).css("font-weight","bold");
	});
	
	function sumColumn(oTable,start,end) {
		
		var dataArray = oTable._('td:nth-child(3)', {"filter": "applied"});  
		var sum = 0; 
		for (var i=0, len=dataArray.length; i < len; i++) {
			if(i >= start && i < end) {
				sum += +dataArray[i];   
			}
		 }  
			$("#sumFilteredHrs").html(sum); 
		}
	
	 function initTable(table)
		{
			
 	 return  $(table).dataTable({
				"sPaginationType": "full_numbers",
				"bDestroy":true,
				 "fnFooterCallback": function ( row, data, start, end, display ) {  
					  sumColumn(this,start,end);
				 } 
			}); 

		}
	 
</script>

<div class="mid_section">
	<!--right section-->
	<sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_ADMIN','ROLE_BEHALF_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
	 <div align="left">
		<table  align="left" "id="readonlylistofuseractivity">
			<table align="left">
				<tr>
				<td>
					<th align="left" >
					  ${employeeName} 
					</th>
					<th align="left" >
					  : ${designation}
					</th>
				</td>
				</tr>
				
				<tr>
				<td>
					<th align="left">
				  		Project 
					</th>
					<th align="left">
					  : ${projectName}
					</th>
				</td>
				</tr>
				
				<tr>
				<td>
					<th align="left">
					  Week Ending 
					</th>
					<th align="left">
					 : ${weekEndDate}
					</th>
				</td>
				</tr>
			</table>
			
			<table align="right">
				<tr>
				<td>
					<th align="left" >
					  Productive Hours 
					</th>
					<th align="left" >
					: ${totalProductiveHours}
					</th>
				</td>
				</tr>
				
				<tr>
				<td>
					<th align="left">
					  Non Productive Hours 
					</th>
					<th align="left">
						: ${totalNonProductiveHours}
					</th>
				</td>
				</tr>
				
				<tr>
				<td>
					<th align="left">
					  Vacation/Leaves Hours 
					</th>
					<th align="left">
					  : ${totalVacationHours}
					</th>
				</td>
				</tr>
				<tr>
				<td>
					<th align="left">
					  Total Weekly Hours
					</th>
					<th align="left">
					  : <span id="weekHrs"></span>
					</th>
				</td>
				</tr>
			</table>
		</table>
		
	</div>

		
					<div id="widthset"></div>
				

		<div class="tab_seaction">
		
				 <div class="tbl" style="{width :100% !important;}">
				 <!-- below table is used to set minimum width because some class is conflicting so minimum width was not setting properly -->
				 <table   width="100%" >
				 <tr>
				   <td>
				   <div id="widthset">
				   </div>
				   </td>
				 </tr>
				 <tr>
				 	<td>
				 					<table  id="reportedHrs" class="display" >
					<thead>
						<tr class="totalColumn">
							<th width="10%" align="center" valign="middle">Date</th>
							<th width="15%" align="center" valign="middle">Project Name</th>
							<th width="10%" align="center" valign="middle">Hours</th>
							<th width="15%" align="center" valign="middle">Activity</th>
							<th width="10%" align="center" valign="middle">Module</th>
							<th width="10%" align="center" valign="middle">Sub - Module</th>
							<th width="10%" align="center" valign="middle">Ticket No.</th>
							<th width="10%" align="center" valign="middle">Ticket Priority</th>
							<th width="10%" align="center" valign="middle">Ticket Status</th>
							<th width="40%" align="center" valign="middle">Comments</th>
							
						</tr>
					</thead>
						<c:forEach var="userActivity" items="${readonlyuseractivities}">
						  <c:if test="${not empty userActivity.d1Comment or userActivity.d1Hours > 0}">						  
							<tr class="even">
								<td align="center" valign="middle">${d1Header}</td>
								<td align="center" valign="middle"></td>
								<td align="center" valign="middle" class="subtotal">${userActivity.d1Hours}</td>
								<td align="center" valign="middle">${userActivity.activityId.activityName}</td>
								<c:choose>
           						<c:when test="${userActivity.module=='-1'}"> 
           						<td align="center" valign="middle"></td>
           						</c:when> 
           						<c:otherwise>
           						<td align="center" valign="middle">${userActivity.module}</td>
           						</c:otherwise>
            					</c:choose>
            					<c:choose>
           						<c:when test="${userActivity.subModule =='-1'}"> 
           						<td align="center" valign="middle"></td>
           						</c:when> 
           						<c:otherwise>
           						<td align="center" valign="middle">${userActivity.subModule}</td>
           						</c:otherwise>
            					</c:choose>
								<td align="center" valign="middle">${userActivity.ticketNo}</td>
								<td align="center" valign="middle">${userActivity.ticketPriority}</td>
        						<td align="center" valign="middle">${userActivity.ticketStatus}</td>
								<td align="center" valign="middle">${userActivity.d1Comment}</td>																
							</tr>
						  </c:if>
						  <c:if test="${not empty userActivity.d2Comment or userActivity.d2Hours > 0}">						  
							<tr class="even">
								<td align="center" valign="middle">${d2Header}</td>
								<td align="center" valign="middle">${projectName}</td>
								<td align="center" valign="middle" class="subtotal">${userActivity.d2Hours}</td>
								<td align="center" valign="middle">${userActivity.activityId.activityName}</td>
								<c:choose>
           						<c:when test="${userActivity.module=='-1'}"> 
           						<td align="center" valign="middle"></td>
           						</c:when> 
           						<c:otherwise>
           						<td align="center" valign="middle">${userActivity.module}</td>
           						</c:otherwise>
            					</c:choose>
            					<c:choose>
           						<c:when test="${userActivity.subModule =='-1'}"> 
           						<td align="center" valign="middle"></td>
           						</c:when> 
           						<c:otherwise>
           						<td align="center" valign="middle">${userActivity.subModule}</td>
           						</c:otherwise>
            					</c:choose>
								<td align="center" valign="middle">${userActivity.ticketNo}</td>
								<td align="center" valign="middle">${userActivity.ticketPriority}</td>
        						<td align="center" valign="middle">${userActivity.ticketStatus}</td>
								<td align="center" valign="middle">${userActivity.d2Comment}</td>																
							</tr>
						  </c:if>
						  <c:if test="${not empty userActivity.d3Comment or userActivity.d3Hours > 0}">						  
							<tr class="even">
								<td align="center" valign="middle">${d3Header}</td>
								<td align="center" valign="middle">${projectName}</td>
								<td align="center" valign="middle" class="subtotal">${userActivity.d3Hours}</td>
								<td align="center" valign="middle">${userActivity.activityId.activityName}</td>
								<c:choose>
           						<c:when test="${userActivity.module=='-1'}"> 
           						<td align="center" valign="middle"></td>
           						</c:when> 
           						<c:otherwise>
           						<td align="center" valign="middle">${userActivity.module}</td>
           						</c:otherwise>
            					</c:choose>
            					<c:choose>
           						<c:when test="${userActivity.subModule =='-1'}"> 
           						<td align="center" valign="middle"></td>
           						</c:when> 
           						<c:otherwise>
           						<td align="center" valign="middle">${userActivity.subModule}</td>
           						</c:otherwise>
            					</c:choose>
								<td align="center" valign="middle">${userActivity.ticketNo}</td>
								<td align="center" valign="middle">${userActivity.ticketPriority}</td>
        						<td align="center" valign="middle">${userActivity.ticketStatus}</td>
								<td align="center" valign="middle">${userActivity.d3Comment}</td>																
							</tr>
						  </c:if>
						  <c:if test="${not empty userActivity.d4Comment or userActivity.d4Hours > 0}">						  
							<tr class="even">
								<td align="center" valign="middle">${d4Header}</td>
								<td align="center" valign="middle">${projectName}</td>
								<td align="center" valign="middle" class="subtotal">${userActivity.d4Hours}</td>
								<td align="center" valign="middle">${userActivity.activityId.activityName}</td>
								<c:choose>
           						<c:when test="${userActivity.module=='-1'}"> 
           						<td align="center" valign="middle"></td>
           						</c:when> 
           						<c:otherwise>
           						<td align="center" valign="middle">${userActivity.module}</td>
           						</c:otherwise>
            					</c:choose>
            					<c:choose>
           						<c:when test="${userActivity.subModule =='-1'}"> 
           						<td align="center" valign="middle"></td>
           						</c:when> 
           						<c:otherwise>
           						<td align="center" valign="middle">${userActivity.subModule}</td>
           						</c:otherwise>
            					</c:choose>
								<td align="center" valign="middle">${userActivity.ticketNo}</td>
								<td align="center" valign="middle">${userActivity.ticketPriority}</td>
        						<td align="center" valign="middle">${userActivity.ticketStatus}</td>
								<td align="center" valign="middle">${userActivity.d4Comment}</td>																
							</tr>
						  </c:if>
						  <c:if test="${not empty userActivity.d5Comment or userActivity.d5Hours > 0}">						  
							<tr class="even">
								<td align="center" valign="middle">${d5Header}</td>
								<td align="center" valign="middle">${projectName}</td>
								<td align="center" valign="middle" class="subtotal">${userActivity.d5Hours}</td>
								<td align="center" valign="middle">${userActivity.activityId.activityName}</td>
								<c:choose>
           						<c:when test="${userActivity.module=='-1'}"> 
           						<td align="center" valign="middle"></td>
           						</c:when> 
           						<c:otherwise>
           						<td align="center" valign="middle">${userActivity.module}</td>
           						</c:otherwise>
            					</c:choose>
            					<c:choose>
           						<c:when test="${userActivity.subModule =='-1'}"> 
           						<td align="center" valign="middle"></td>
           						</c:when> 
           						<c:otherwise>
           						<td align="center" valign="middle">${userActivity.subModule}</td>
           						</c:otherwise>
            					</c:choose>
								<td align="center" valign="middle">${userActivity.ticketNo}</td>
								<td align="center" valign="middle">${userActivity.ticketPriority}</td>
        						<td align="center" valign="middle">${userActivity.ticketStatus}</td>
								<td align="center" valign="middle">${userActivity.d5Comment}</td>																
							</tr>
						  </c:if>
						  <c:if test="${not empty userActivity.d6Comment or userActivity.d6Hours > 0}">						  
							<tr class="even">
								<td align="center" valign="middle">${d6Header}</td>
								<td align="center" valign="middle">${projectName}</td>
								<td align="center" valign="middle" class="subtotal">${userActivity.d6Hours}</td>
								<td align="center" valign="middle">${userActivity.activityId.activityName}</td>
								<c:choose>
           						<c:when test="${userActivity.module=='-1'}"> 
           						<td align="center" valign="middle"></td>
           						</c:when> 
           						<c:otherwise>
           						<td align="center" valign="middle">${userActivity.module}</td>
           						</c:otherwise>
            					</c:choose>
            					<c:choose>
           						<c:when test="${userActivity.subModule =='-1'}"> 
           						<td align="center" valign="middle"></td>
           						</c:when> 
           						<c:otherwise>
           						<td align="center" valign="middle">${userActivity.subModule}</td>
           						</c:otherwise>
            					</c:choose>
								<td align="center" valign="middle">${userActivity.ticketNo}</td>
								<td align="center" valign="middle">${userActivity.ticketPriority}</td>
        						<td align="center" valign="middle">${userActivity.ticketStatus}</td>
								<td align="center" valign="middle">${userActivity.d6Comment}</td>																
							</tr>
						  </c:if>
						  <c:if test="${not empty userActivity.d7Comment or userActivity.d7Hours > 0}">						  
							<tr class="even">
								<td align="center" valign="middle">${d7Header}</td>
								<td align="center" valign="middle">${projectName}</td>
								<td align="center" valign="middle" class="subtotal">${userActivity.d7Hours}</td>
								<td align="center" valign="middle">${userActivity.activityId.activityName}</td>
								<c:choose>
           						<c:when test="${userActivity.module=='-1'}"> 
           						<td align="center" valign="middle"></td>
           						</c:when> 
           						<c:otherwise>
           						<td align="center" valign="middle">${userActivity.module}</td>
           						</c:otherwise>
            					</c:choose>
            					<c:choose>
           						<c:when test="${userActivity.subModule =='-1'}"> 
           						<td align="center" valign="middle"></td>
           						</c:when> 
           						<c:otherwise>
           						<td align="center" valign="middle">${userActivity.subModule}</td>
           						</c:otherwise>
            					</c:choose>
								<td align="center" valign="middle">${userActivity.ticketNo}</td>
								<td align="center" valign="middle">${userActivity.ticketPriority}</td>
        						<td align="center" valign="middle">${userActivity.ticketStatus}</td>
								<td align="center" valign="middle">${userActivity.d7Comment}</td>																
							</tr>
						  </c:if>
						</c:forEach>
				</table>
				<table   width="100%" >				
				 <tr>
				 	<td>
							<!-- <table style="width: 100%;"  id="reportedHrs"  class="dataTable" >
								<tr>							
									<td align="center" align="right" valign="middle" colspan="2" width="28%"><strong>Total current page hours:</strong>&nbsp;<strong><span id="sumFilteredHrs"></span></strong></td>
									<td align="center" align="middle" valign="middle" width="7%">&nbsp;</td> <strong><span id="sumFilteredHrs"></span></strong> 
									<td align="center" valign="middle" valign="middle" width="15%"></td>
									<td align="center" valign="middle" valign="middle" colspan="4" width="15%"></td>
									<td align="right" valign="middle" valign="middle" colspan="2" width="25%"><strong>Total Weekly Hours:</strong>&nbsp;<strong><span id="weekHrs"></span></strong></td>
									<td align="center" valign="middle" valign="middle"  width="10%"></td>	id="weekHrs"  						
								</tr>
							</table> -->
				 	</td>
				 </tr>
				 
				  <tr>
				   <td>
				   <div id="anotherwidthset">
				   </div>
				   </td>
				 </tr>
				 </table>

			</div>
			<div class="clear"></div>
		</div>
	</sec:authorize>

</div>

<script>
initTable("#reportedHrs");
var spaceString="&nbsp";
for(var i=0;i<250;i++) {
	spaceString+="&nbsp;";
}
$("#widthset").html(spaceString);
$("#anotherwidthset").html(spaceString);
</script>