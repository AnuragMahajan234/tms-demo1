<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customized Module Report</title>
</head>
<body>

<div class="botMargin">
	<h1 id="h1" class="custom-head"></h1>
</div>

<div class="tbl dataTables_wrapper">

	<table width="100%" class="tablesorter dataTbl addNewRow_thm fl dataTable" cellspacing="1" cellpadding="2" id="example" style="width: 100%;">
		<thead>
			<tr>
				<th width="7%">Module Name</th>
				<th width="7%">Project Name</th>
				<th width="4%">Week Start Date</th>
				<th width="4%">Week End Date</th>
				<th width="7%">Employee Name</th>
				<th width="7%">Employee Id</th>
				<th width="7%">Non Productive Hours</th>
				<th width="9%">Productive Hours</th>
				<th width="9%">Planned Hours</th>
				<th width="9%">Billed Hours</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${moduleReportList}" var="moduleReportData">
			<tr>
				<td>${moduleReportData.moduleName}</td>
				<td>${moduleReportData.projectName}</td>
				<td>${moduleReportData.weekStartDate}</td>
				<td>${moduleReportData.weekEndDate}</td>
				<td>${moduleReportData.employeeName}</td>
				<td>${moduleReportData.yashEmpId}</td>
				<td>${moduleReportData.nonProductiveHours}</td>
				<td>${moduleReportData.productiveHours}</td>
				<td>${moduleReportData.plannedHours}</td>
				<td>${moduleReportData.billedHours}</td>
			</tr>
			</c:forEach>	
		</tbody>
	</table>
</div>	
			 
</body>
</html>