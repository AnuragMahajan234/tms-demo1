<%@page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<spring:url
	value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url
	value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<style>
table#dtbl thead tr:first-child th:nth-child(even) {
	background-color: #3c8dbc;
}

table#dtbl thead tr:first-child th:nth-child(odd) {
	background-color: #01498B;
}
</style>
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<script src="${multiselect_filter_js}" type="text/javascript"></script>
<script src="resources/js-user/validations.js"></script>
<!-- /RMSWEB/src/main/webapp/resources/js-user/validations.js -->
<style type="text/css" title="currentStyle">
thead input {
	width: 100%
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
#plr { padding-bottom:10px;}

#defaultTableId thead th {
	width: 120px;
}

#defaultTableId td {
	word-wrap: break-word;
}

.dtable td {
	background: #ebebeb;
}

.plReportFilter td {
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

</style>

<script type="text/javascript">

$(document).ready(function(){
	var dt = new Date();  
	var curr_year = dt.getFullYear();  
	
	    for(i = 0; i <= (curr_year-2000) ; i++)
	    {
	        document.getElementById('YearTypeId').options[i] = new Option(curr_year-i,curr_year-i);
	    }
	    
	});
	

function downloadExcel() {
	
		var year = $("#YearTypeId :selected").val();
		var requestParam = "?";
		
		requestParam = requestParam + "&year=" + year;
		var res = "customizeReportExcel";
		$('#exportToExcel').attr("href", res + requestParam)
		$("#CustomizedReport").submit();
		
	}
</script>
<html>
<body>
	<div class="content-wrapper">
		<div class="botMargin">
			<h1>Customized Report</h1>
		</div>
		<div class="tab_seaction">
			<div id='tab1' class="tab_div">
				<div class="form">
					<form  id="CustomizedReport" name="CustomizedReport" action="UtilizationReportController">
						<table id="defaultTable" class="plReportFilter">
							<tr>
								<td align="left">Year : <span class="astric">*</span>
								<select name="YearType.id"
									id="YearTypeId">
										<option value=-1>Select year</option>
								</select></td>
								
							<td align="right">
									<a href="#" class="blue_link" id="exportToExcel" onclick="downloadExcel()">
								<i class="fa fa-file-excel-o" style="font-size: 15px"></i> Export To Excel </a>
							</td>
							</tr>
							
						</table>
					</form>
				</div>
				<div class="tbl"></div>
				<div class="clear"></div>
			</div>
		</div>
	</div>

</body>
</html>