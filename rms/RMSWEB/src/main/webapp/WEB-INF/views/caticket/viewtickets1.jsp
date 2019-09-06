<!DOCTYPE html>
<html>
<head>


<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />

<!-- 
[if IE]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<![endif]
	-->

<link href="dashBoard_files/style.css" rel="stylesheet" type="text/css">

<!-- Get the user local from the page context (it was set by Spring MVC's locale resolver) -->

<script src="dashBoard_files/jquery-1.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="dashBoard_files/jquery-ui-1.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="dashBoard_files/combobox.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="dashBoard_files/json2.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="dashBoard_files/form2js.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="dashBoard_files/jquery_002.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="dashBoard_files/js2form.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="dashBoard_files/jquery_008.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="dashBoard_files/additional-methods.js"
	type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="dashBoard_files/jquery.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="dashBoard_files/jsrender.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="dashBoard_files/jquery_005.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>

<!-- required for common functions -->
<script src="dashBoard_files/rmsCommon.js" type="text/javascript"></script>
<!-- required for common validations -->
<script src="dashBoard_files/validations.js" type="text/javascript"></script>

<script src="dashBoard_files/jquery_004.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="dashBoard_files/default.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="dashBoard_files/center.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>

<!-- DataTables CSS -->
<!-- DataTables -->
<script src="dashBoard_files/jquery_007.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>

<!-- Populate -->
<script src="dashBoard_files/jquery_009.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="dashBoard_files/jquery_006.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="dashBoard_files/toastr.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>
<script src="dashBoard_files/jquery_010.js" type="text/javascript">
<!-- required for FF3 and Opera -->
	
</script>

<title>Welcome to RMS</title>
<link href="http://inidsoasrv01:8080/rms/resources/images/favicon.ico"
	rel="SHORTCUT ICON">
<link href="" media="screen" rel="stylesheet" type="text/css">

</head>
<body>

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				<td>
					<div class="container">
						<div class="contentArea">
							<div class="centerContent">
								<div class="topSection">

									<div class="fl left"></div>
									<div class="fr right"></div>
									<div class="mid"></div>
									<div class="clear"></div>
								</div>
								<div class="midSection" style="min-height: 450px;">
									<script src="dashBoard_files/jquery_003.js"
										type="text/javascript"></script>
									<form id="orgForm">
										<div class="botMargin">
											<h1>Ticket Details :</h1>
										</div>
										<div>
											<div class="tbl">
												<div id="orgTable_wrapper" class="dataTables_wrapper"
													role="grid">
													<table aria-describedby="orgTable_info"
														class="dataTable dataTbl" id="">
														<thead>
															<tr role="row">
																<th width="4%" colspan="1" rowspan="2" align="center"
																	valign="middle" class="sorting_asc"
																	style="width: 179px;" aria-controls="orgTable"
																	aria-label="TicketNo: activate to sort column descending"
																	aria-sort="ascending" role="columnheader" tabindex="0">Ticket
																	No.</th>
																<th width="4%" colspan="1" rowspan="2" align="center"
																	valign="middle" class="sorting" style="width: 209px;"
																	aria-controls="orgTable"
																	aria-label="Description: activate to sort column ascending"
																	role="columnheader" tabindex="0">Description</th>
																<th width="4%" colspan="1" rowspan="2" align="center"
																	valign="middle" class="sorting" style="width: 157px;"
																	aria-controls="orgTable"
																	aria-label="Priority: activate to sort column ascending"
																	role="columnheader" tabindex="0">Priority</th>
																<th width="4%" colspan="1" rowspan="2" align="center"
																	valign="middle" class="sorting_disabled"
																	style="width: 200px;" aria-label="Phase"
																	role="columnheader">Module</th>
																<th width="4%" colspan="1" rowspan="2" align="center"
																	valign="middle" class="sorting_disabled"
																	style="width: 261px;" aria-label="LandScape"
																	role="columnheader">LandScape</th>
																<th width="4%" colspan="1" rowspan="2" align="center"
																	valign="middle" class="sorting_disabled"
																	style="width: 176px;" aria-label="Unit"
																	role="columnheader">Unit</th>
																<th width="4%" colspan="1" rowspan="2" align="center"
																	valign="middle" class="sorting_disabled"
																	style="width: 176px;" aria-label="Unit"
																	role="columnheader">Region</th>
																<th width="4%" colspan="1" rowspan="2" align="center"
																	valign="middle" class="sorting_disabled"
																	style="width: 176px;" aria-label="Unit"
																	role="columnheader">Aging</th>
																<th width="4%" colspan="1" rowspan="2" align="center"
																	valign="middle" class="sorting_disabled"
																	style="width: 176px;" aria-label="Unit"
																	role="columnheader">SLA Missed</th>
																<th width="4%" colspan="1" rowspan="2" align="center"
																	valign="middle" class="sorting_disabled"
																	style="width: 176px;" aria-label="Unit"
																	role="columnheader">Days Open</th>
																<th width="4%" rowspan="2" align="center"
																	valign="middle" class="sorting_disabled"
																	aria-label="AssignName" role="columnheader">AssignName</th>
																<th width="4%" rowspan="2" align="center"
																	valign="middle" class="sorting_disabled"
																	aria-label="Reviewer" role="columnheader">Reviewer</th>
																<th colspan="3" align="center" valign="middle"
																	class="sorting_disabled" aria-label="Reviewer"
																	role="columnheader">Phase</th>
																<th colspan="5" align="center" valign="middle"
																	class="sorting_disabled" aria-label="Reviewer"
																	role="columnheader">Problem Management</th>
															</tr>
															<tr role="row">
																<th aria-label="Reviewer" role="columnheader"
																	class="sorting_disabled" align="center" valign="middle"
																	width="4%">Solution Reviews</th>
																<th aria-label="Reviewer" role="columnheader"
																	class="sorting_disabled" align="center" valign="middle"
																	width="4%">Defect Log</th>
																<th aria-label="Reviewer" role="columnheader"
																	class="sorting_disabled" align="center" valign="middle"
																	width="4%">T3 Solutions</th>
																<th aria-label="Reviewer" role="columnheader"
																	class="sorting_disabled" align="center" valign="middle"
																	width="4%">Process</th>
																<th aria-label="Reviewer" role="columnheader"
																	class="sorting_disabled" align="center" valign="middle"
																	width="4%">Sub-Process</th>
																<th aria-label="Reviewer" role="columnheader"
																	class="sorting_disabled" align="center" valign="middle"
																	width="4%">Root Cause</th>
																<th aria-label="Reviewer" role="columnheader"
																	class="sorting_disabled" align="center" valign="middle"
																	width="4%">CROP</th>
																<th aria-label="Reviewer" role="columnheader"
																	class="sorting_disabled" align="center" valign="middle"
																	width="4%">T3</th>
															</tr>
														</thead>

														<tbody aria-relevant="all" aria-live="polite" role="alert">
															<tr class="odd" id="101">
																<td class="  sorting_1" align="center" valign="middle"
																	width="4%"><a id=""
																	href="file:///C:/Users/gaurav.chopdar/Desktop/rms/createTicket.html"
																	style="text-decoration: none">link</a></td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo
																</td>
																<td class=" " align="center" valign="middle" width="4%">demo
																</td>
																<td class=" " align="center" valign="middle" width="4%">demo
																</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%"><img
																	alt="SucessImage" src="${pageContext.request.contextPath}/resources/images/done.png" /></td>
																<td class=" " align="center" valign="middle" width="4%"><img
																	alt="FailImage" src="${pageContext.request.contextPath}/resources/images/cross.png" /></td>
																<td class=" " align="center" valign="middle" width="4%"><img
																	alt="SucessImage" src="${pageContext.request.contextPath}/resources/images/done.png" /></td>
																<td class=" " align="center" valign="middle" width="4%"><img
																	alt="SucessImage" src="${pageContext.request.contextPath}/resources/images/done.png" /></td>
																<td class=" " align="center" valign="middle" width="4%"><img
																	alt="FailImage" src="${pageContext.request.contextPath}/resources/images/cross.png" /></td>
																<td class=" " align="center" valign="middle" width="4%"><img
																	alt="SucessImage" src="${pageContext.request.contextPath}/resources/images/done.png" /></td>
																<td class=" " align="center" valign="middle" width="4%"><img
																	alt="FailImage" src="${pageContext.request.contextPath}/resources/images/cross.png" /></td>
																<td class=" " align="center" valign="middle" width="4%"><img
																	alt="FailImage" src="${pageContext.request.contextPath}/resources/images/cross.png" /></td>

															</tr>
															<tr class="odd" id="3">


																<td class="  sorting_1" align="center" valign="middle"
																	width="4%"><a id=""
																	href="file:///C:/Users/gaurav.chopdar/Desktop/rms/createTicket.html"
																	style="text-decoration: none">link</a></td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%">demo</td>
																<td class=" " align="center" valign="middle" width="4%"><img
																	alt="SucessImage" src="${pageContext.request.contextPath}/resources/images/done.png" /></td>
																<td class=" " align="center" valign="middle" width="4%"><img
																	alt="FailImage" src="${pageContext.request.contextPath}/resources/images/cross.png" /></td>
																<td class=" " align="center" valign="middle" width="4%"><img
																	alt="FailImage" src="${pageContext.request.contextPath}/resources/images/cross.png" /></td>
																<td class=" " align="center" valign="middle" width="4%"><img
																	alt="FailImage" src="${pageContext.request.contextPath}/resources/images/cross.png" /></td>
																<td class=" " align="center" valign="middle" width="4%"><img
																	alt="FailImage" src="${pageContext.request.contextPath}/resources/images/cross.png" /></td>
																<td class=" " align="center" valign="middle" width="4%"><img
																	alt="FailImage" src="${pageContext.request.contextPath}/resources/images/cross.png" /></td>
																<td class=" " align="center" valign="middle" width="4%"><img
																	alt="FailImage" src="${pageContext.request.contextPath}/resources/images/cross.png" /></td>
																<td class=" " align="center" valign="middle" width="4%"><img
																	alt="SucessImage" src="${pageContext.request.contextPath}/resources/images/done.png" /></td>
															</tr>
														</tbody>
													</table>

													<input value="27" id="parentid" name="parentid"
														type="hidden"> <input value="ORGANIZATION"
														id="parentName" name="parentName" type="hidden">
												</div>
												<div class="clear"></div>
											</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>