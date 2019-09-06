<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
   uri="http://www.springframework.org/security/tags"%>
   <spring:message code="application_js_version" var="app_js_ver" htmlEscape="false" />
<spring:url value="/resources/styles/popupstyle.css?ver=${app_js_ver}" var="popupstyle_css" />
<link href="${popupstyle_css}" rel="stylesheet" type="text/css">
</link>
<spring:url value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}" var="ColReorder_js" />
<spring:url value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}" var="ColVis_js" />
<spring:url value="../resources/styles/style.css?ver=${app_js_ver}" var="style_css" />
<%-- <link href="${style_css}" rel="stylesheet" type="text/css"></link> --%>

<link href="/rms/resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="/rms/resources/css/styles.css?ver=${app_js_ver}">

<script type="text/javascript" src="resources/js/bootstrap-datetimepicker.js?ver=${app_js_ver}" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/locales/bootstrap-datetimepicker.fr.js?ver=${app_js_ver}" charset="UTF-8"></script>
<script type="text/javascript" src="resources/js/resource-requests.js?ver=${app_js_ver}"></script>

<!-- Code for single select - start -->
	<spring:url value="/resources/css/select2.min.css?ver=${app_js_ver}"
    var="select2_css" />
<link href="${select2_css}" rel="stylesheet" type="text/css"></link>

<spring:url value="/resources/js/select2.min.js?ver=${app_js_ver}"
    var="select2_js" />
<script src="${select2_js}" type="text/javascript"></script>
<!-- Code for single select - end -->
</head>

<body>
	<div id="rrfYash" class="alertWrapper success">
		<div class="alert alert-success" role="alert"></div>
	</div>
	<div class="alertWrapper danger">
		<div class="alertlistWrapper">
			<span class="closemeAlert">X</span>
			<div class="alert alert-danger" role="alert"></div>
		</div>
	</div>
	<div class="content-wrapper">
		<h4 class="theme-h1">Resource Requisition Form</h4>
		<div class="container-fluid outerCard" id="rrf_page">
			<ul class="nav nav-pills tab-border resource-request-nav nav-hover-none">
				<li class="nav_step1 active disabled">
					<a href="#rmsForm1" data-toggle="pill">
						<span class="stepContent">Delivery Information</span>
						<label><span class="numberCircle">Step 1 - </span><span class="status">in progress</span></label>
					</a>
				</li>
				<li class="nav_step2 disabled">
					<a href="#rmsForm2" data-toggle="pill">
						<span class="stepContent">Customer &amp; Project Information</span>
						<!-- <span class="numberCircle">Step 2</span> -->
						<label><span class="numberCircle">Step 2 - </span><span class="status">pending</span></label>
					</a>
				</li>
				<li class="nav_step3 disabled">
					<a href="#rmsForm3" data-toggle="pill">
						<span class="stepContent">Hiring/Job Description</span>
						<!-- <span class="numberCircle">Step 3</span> -->
						<label><span class="numberCircle">Step 3 - </span><span class="status">pending</span></label>
					</a>
				</li>
				<li class="nav_step4 disabled">
					<a href="#rmsForm4" data-toggle="pill">
						<span class="stepContent">Notify &amp; Submit</span>
						<!-- <span class="numberCircle">Step 4</span> -->
						<label><span class="numberCircle">Step 4 - </span><span class="status">pending</span></label>
					</a>
				</li>
			</ul>
			<div class="tab-content requisition_form_content">
				<div id="rmsForm1" class="tab-pane fade in active">
				 <c:if test="${buttonFlag}">
                  <h5 style="font-size: large;">RRF ID - ${dataList.requirementId}</h5>
                  </c:if>
					<form>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe autoSelect">
									<label for="hiringbgBuSelect"><span class="required">*</span><span data-toggle="tooltip" data-placement="top"
										 title="" data-original-title="We limit this to BG4-BU5">Hiring BG/BU</span><span class="text-inside-brackets">
											(Recommended not to change)</span></label>
									<div class="positionRel" id="hiringBgBu">
										<select class="form-control required comboselect check" id="hiringbgBuSelect" placeholder="Select BG/BU">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.requestRequisition.hiringBGBU}" class="selected-option">${dataList.requestRequisition.hiringBGBU}</option>
													<c:forEach var="bu" items="${bus}">
														<option value="${bu.parentId.name}-${bu.name}">${bu.parentId.name}-${bu.name}</option>
													</c:forEach>
												</c:when>
												<c:when test="${copyRRFFlag}">
													<option value="${copyRRFData.requestRequisition.hiringBGBU}" class="selected-option">${copyRRFData.requestRequisition.hiringBGBU}</option>
													<c:forEach var="bu" items="${bus}">
														<option value="${bu.parentId.name}-${bu.name}">${bu.parentId.name}-${bu.name}</option>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<option value="BG4-BU5" class="selected-option">BG4-BU5</option>
													<c:forEach var="bu" items="${bus}">
													 <c:if test="${not(bu.parentId.name == 'BG4' && bu.name == 'BU5')}">
														<option value="${bu.parentId.name}-${bu.name}">${bu.parentId.name}-${bu.name}</option>
													</c:if>
													 </c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="inputPassword4"><span class="required">*</span>Request
										Date</label>
									<div class="positionRel">
										<div class='input-group'>
											<c:choose>
												<c:when test="${buttonFlag}">
													<input type='text' disabled="disabled" value="${dataList.requestRequisition.date}" id="requestedDate1"
													 autocomplete="off" class="form-control" />
												</c:when>
												<c:otherwise>
													<input type='text' id="requestedDate1" class="form-control" placeholder="Date" />
												</c:otherwise>
											</c:choose>
											<label class="input-group-addon" for="requestedDate1">
												<span class="glyphicon glyphicon-calendar"></span>
											</label>
										</div>
									</div>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="bgBuSelect"><span class="required">*</span>Requestor's BG/BU</label>
									<div class="positionRel" id="requestorBgBu">
										<select class="form-control required comboselect check" id="bgBuSelect" placeholder="Select BG/BU">
											<c:choose>
												<c:when test="${copyRRFFlag}">
													<option  value="${copyRRFData.requestRequisition.projectBU}">${copyRRFData.requestRequisition.projectBU}</option>
													<c:forEach var="bu" items="${bus}">
														<option value="${bu.parentId.name}-${bu.name}">${bu.parentId.name}-${bu.name}</option>
													</c:forEach>
												</c:when>
												<c:when test="${buttonFlag}">
													<option value="${dataList.requestRequisition.projectBU}">${dataList.requestRequisition.projectBU}</option>
													<c:forEach var="bu" items="${bus}">
														<option value="${bu.parentId.name}-${bu.name}">${bu.parentId.name}-${bu.name}</option>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="bu" items="${bus}">
														<option value="${bu.parentId.name}-${bu.name}">${bu.parentId.name}-${bu.name}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe autoSelect">
									<label for="requestorSelect"><span class="required">*</span>Requestor's Name</label>
									<div class="positionRel" id="requestorName">
										<select class="form-control required " id="requestorSelect" name="requestorSelect" placeholder="Select name">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.requestRequisition.requestor.employeeId}" class="selected-option">${dataList.requestRequisition.requestor.firstName} ${dataList.requestRequisition.requestor.lastName} - ${dataList.requestRequisition.requestor.yashEmpId}</option>
													<%-- <c:forEach var="user" items="${ActiveUserList}">
														<c:if test="${dataList.requestRequisition.requestor.employeeId != user.employeeId }">
															<option value="${user.employeeId}">${user.firstName} ${user.lastName} - ${user.yashEmpId}</option>
														</c:if>
													</c:forEach> --%>
												</c:when>
												<c:when test="${copyRRFFlag}">
													<option value="${copyRRFData.requestRequisition.requestor.employeeId}" class="selected-option">${copyRRFData.requestRequisition.requestor.firstName} ${copyRRFData.requestRequisition.requestor.lastName} - ${copyRRFData.requestRequisition.requestor.yashEmpId}</option>
													<%-- <c:forEach var="user" items="${ActiveUserList}">
														<c:if test="${dataList.requestRequisition.requestor.employeeId != user.employeeId }">
															<option value="${user.employeeId}">${user.firstName} ${user.lastName} - ${user.yashEmpId}</option>
														</c:if>
													</c:forEach> --%>
												</c:when>
												<c:otherwise>
													<option value="${currentLoggedinResource.employeeId}" class="selected-option"> ${currentLoggedinResource.firstName} ${currentLoggedinResource.lastName} - ${currentLoggedinResource.yashEmpId}</option>
												<%-- 	<c:forEach var="user" items="${ActiveUserList}">
														<c:if test="${currentLoggedinResource.employeeId != user.employeeId}">
															<option value="${user.employeeId}">${user.firstName} ${user.lastName} - ${user.yashEmpId}</option>
														</c:if>
													</c:forEach> --%>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="form-group col-md-4 autoSelect">
									<label for="requestorGradeSelect"><span class="required">*</span>Requestor Grade</label>
									<c:choose>
										<c:when test="${buttonFlag}">
											<input type="text" value="${requestorDetails.grade}" disabled="disabled" class="form-control" id="requestorGradeSelect"
											 placeholder="Enter grade">
										</c:when>
										<c:otherwise>
											<input type="text" disabled="disabled" value="${currentLoggedinResource.gradeId.grade}" class="form-control"
											 id="requestorGradeSelect" placeholder="Enter grade">
										</c:otherwise>
									</c:choose>
								</div>
								<div class="form-group col-md-4 autoSelect">
									<label for="requestorDesignationSelect"><span class="required">*</span>Requestor Designation</label>
									<c:choose>
										<c:when test="${buttonFlag}">
											<input type="text" value="${requestorDetails.designationName}" disabled="disabled" class="form-control" id="requestorDesignationSelect"
											 placeholder="Enter designation">
										</c:when>
										<c:otherwise>
											<input type="text" disabled="disabled" value="${currentLoggedinResource.designationId.designationName}"
											 class="form-control" id="requestorDesignationSelect" placeholder="Enter designation">
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe">
									<label for="deliverPOCSelect"><span class="required">*</span><span data-toggle="tooltip" data-placement="right"
										 title="" data-original-title="POC for RMG Team (will be notified through email)">Delivery
											POC</span></label>
									<div class="positionRel" id="deliveryPoc">
									
										<select class="form-control required  " id="deliverPOCSelect" name="deliverPOCSelect" placeholder="Select delivery POC">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.requestRequisition.deliveryPOC.employeeId}" class="selected-option">${dataList.requestRequisition.deliveryPOC.firstName} ${dataList.requestRequisition.deliveryPOC.lastName} - ${dataList.requestRequisition.deliveryPOC.yashEmpId}
													</option>
													<%-- <c:forEach var="user" items="${ActiveUserList}">
														<c:if test="${user.employeeId != dataList.requestRequisition.deliveryPOC.employeeId}">
															<option value="${user.employeeId}">${user.firstName} ${user.lastName} - ${user.yashEmpId}</option>
														</c:if>
													</c:forEach> --%>
												</c:when>
												<c:when test="${copyRRFFlag}">
													<option value="${copyRRFData.requestRequisition.deliveryPOC.employeeId}" class="selected-option">${copyRRFData.requestRequisition.deliveryPOC.firstName} ${copyRRFData.requestRequisition.deliveryPOC.lastName} - ${copyRRFData.requestRequisition.deliveryPOC.yashEmpId}
													</option>
													<%-- <c:forEach var="user" items="${ActiveUserList}">
														<c:if test="${user.employeeId != dataList.requestRequisition.deliveryPOC.employeeId}">
															<option value="${user.employeeId}">${user.firstName} ${user.lastName} - ${user.yashEmpId}</option>
														</c:if>
													</c:forEach> --%>
												</c:when>
												<c:otherwise>
													<%-- <c:forEach var="user" items="${ActiveUserList}">
														<option value="${user.employeeId}">${user.firstName} ${user.lastName} - ${user.yashEmpId}</option>
													</c:forEach> --%>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								
								
								<div class="form-group col-md-4">
									 <label for="RMGPocSelect"><span data-toggle="tooltip" title="" data-placement="right" data-original-title="Select list of RMG POC">RMG POC</span></label>
										<div class="positionRel combo-multiselet">
											<!-- <select class="form-control required" id="RMGPocSelect" name="RMGPocSelect" multiple="multiple" placeholder="Press Ctrl and click to select mulitple"> -->
											<select class="form-control required" id="RMGPocSelect" name="users[]" multiple="multiple" placeholder="Press Ctrl and click to select mulitple">
												<c:choose>
													<c:when test="${buttonFlag}">
																	<c:forEach var="pocId" items="${dataList.requestRequisition.rmgPOCList}">
																			<option selected="selected" value="${pocId.employeeId}">${pocId.firstName} ${pocId.lastName} - ${pocId.yashEmpId}</option>
																	</c:forEach> 
													</c:when>
													<c:when test="${copyRRFFlag}">
																	<c:forEach var="pocId" items="${copyRRFData.requestRequisition.rmgPOCList}">
																			<option selected="selected" value="${pocId.employeeId}">${pocId.firstName} ${pocId.lastName} - ${pocId.yashEmpId}</option>
																	</c:forEach> 
													</c:when>
													<c:otherwise>
														<%-- <c:forEach var="rmgPoc" items="${RMGPocList}">
															<option value="${rmgPoc.employeeId}">${rmgPoc.firstName} ${rmgPoc.lastName} - ${rmgPoc.yashEmpId}</option>
														</c:forEach> --%>
													</c:otherwise>
												</c:choose>
											</select>
										</div>
									</div>
																
								<div class="form-group col-md-4">
									<label for="TecTeamSelect"><span data-toggle="tooltip" title="" data-placement="right" data-original-title="Select list of TAC Team POC">TAC Team POC</span></label>
										<div class="positionRel combo-multiselet">
											<select class="form-control required" id="TecTeamSelect" name="TecTeamSelect[]" multiple="multiple" placeholder="Press Ctrl and click to select mulitple">
												<c:choose>
													<c:when test="${buttonFlag}">
																		<c:forEach var="tecTeamId" items="${dataList.requestRequisition.techTeamPocList}">
																				<option selected="selected" value="${tecTeamId.employeeId}">${tecTeamId.firstName} ${tecTeamId.lastName} - ${tecTeamId.yashEmpId}</option>
																		</c:forEach>
													</c:when>
													<c:when test="${copyRRFFlag}">
																		<c:forEach var="tecTeamId" items="${copyRRFData.requestRequisition.techTeamPocList}">
																				<option selected="selected" value="${tecTeamId.employeeId}">${tecTeamId.firstName} ${tecTeamId.lastName} - ${tecTeamId.yashEmpId}</option>
																		</c:forEach>
													</c:when>
													<c:otherwise>
														<%-- <c:forEach var="tecTeam" items="${tecTeamPocList}">
															<option value="${tecTeam.employeeId}">${tecTeam.firstName} ${tecTeam.lastName} - ${tecTeam.yashEmpId}</option>
														</c:forEach> --%>
													</c:otherwise> 
												</c:choose>
											</select>
										</div>
									</div>
							</div>
						</div>
						
						<div class="clearfix mrT2">
							<button type="button" id="nextFirst" class="btn btn-primary next-button changeToProjectInfo">NEXT</button>
							<!-- <button type="button" class="btn btn-default">CANCEL</button> -->
						</div>
						<div>
							<input type="text" id="requirementName" value="${requirementName}" style="display: none;" />
						</div>
						<div>
							<input type="text" id="buttonFlagId" value="${buttonFlag}" style="display: none;" />
						</div>
						
						<div>
							<input type="text" id="requisitionDbId" value="${requestRequisitionSkillId}" style="display: none;" />
						</div>
						
					</form>
				</div>
				<div id="rmsForm2" class="tab-pane fade">
					<!-- <div class="process-steps">
                  <span><strong>2</strong> out of 4</span>
                  </div> -->
                   <c:if test="${buttonFlag}">
                  <h5 style="font-size: large;">RRF ID - ${dataList.requirementId}</h5>
                  </c:if>
					<form>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe">
									<label for="resourceTypeSelect">
										<span class="required">*</span>Requirement Type
										<c:if test="${buttonFlag}">
											<a style="cursor: pointer;"><span class="glyphicon glyphicon-edit display-edit"></span></a>
										</c:if>
									</label>
									<div class="positionRel" id="requirementType">
										<select class="form-control required comboselect check" id="resourceTypeSelect" placeholder="Select required type">
											<c:choose>
												<c:when test="${buttonFlag}">
													<c:if test="${dataList.type == 1 }">
														<option selected="selected" value="1">New
															Requirement
														</option>
														<option value="0">Replacement</option>
														<option value="2">Heads-Up</option>
														<option value="3">Pool</option>
													</c:if>
													<c:if test="${dataList.type == 0 }">
														<option selected="selected" value="0">Replacement</option>
														<option value="1">New Requirement</option>
														<option value="2">Heads-Up</option>
														<option value="3">Pool</option>
													</c:if>
													<c:if test="${dataList.type == 2 }">
														<option selected="selected" value="2">Heads-Up</option>
														<option value="1">New Requirement</option>
														<option value="0">Replacement</option>
														<option value="3">Pool</option>
													</c:if>
													<c:if test="${dataList.type == 3 }">
														<option selected="selected" value="3">Pool</option>
														<option value="1">New Requirement</option>
														<option value="0">Replacement</option>
														<option value="2">Heads-Up</option>
													</c:if>
												</c:when>
												<c:when test="${copyRRFFlag}">
													<c:if test="${copyRRFData.type == 1 }">
														<option selected="selected" value="1">New
															Requirement
														</option>
														<option value="0">Replacement</option>
														<option value="2">Heads-Up</option>
														<option value="3">Pool</option>
													</c:if>
													<c:if test="${copyRRFData.type == 0 }">
														<option selected="selected" value="0">Replacement</option>
														<option value="1">New Requirement</option>
														<option value="2">Heads-Up</option>
														<option value="3">Pool</option>
													</c:if>
													<c:if test="${copyRRFData.type == 2 }">
														<option selected="selected" value="2">Heads-Up</option>
														<option value="1">New Requirement</option>
														<option value="0">Replacement</option>
														<option value="3">Pool</option>
													</c:if>
													<c:if test="${copyRRFData.type == 3 }">
														<option selected="selected" value="3">Pool</option>
														<option value="1">New Requirement</option>
														<option value="0">Replacement</option>
														<option value="2">Heads-Up</option>
													</c:if>
												</c:when>
												<c:otherwise>
													<option value="1">New Requirement</option>
													<option value="0">Replacement</option>
													<option value="2">Heads-Up</option>
													<option value="3">Pool</option>
												</c:otherwise>
											</c:choose>
										</select>
									</div>

								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="requiredForSelect"><span class="required">*</span><span data-toggle="tooltip" data-placement="right"
										 title="" data-original-title="For Staffing, approvals are not mandatory">Required for</span></label>
									<div class="positionRel" id="requiredFor">
										<select class="form-control required comboselect check" id="requiredForSelect" placeholder="Select required for ">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option selected="selected" value="${dataList.requestRequisition.requiredFor}">${dataList.requestRequisition.requiredFor}</option>
													<c:forEach var="requiredFor" items="${resourceRequiredFor}">
														<c:if test="${dataList.requestRequisition.requiredFor != requiredFor.type}">
															<option value="${requiredFor.type}">${requiredFor.type}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:when test="${copyRRFFlag}">
													<option selected="selected" value="${copyRRFData.requestRequisition.requiredFor}">${copyRRFData.requestRequisition.requiredFor}</option>
													<c:forEach var="requiredFor" items="${resourceRequiredFor}">
														<c:if test="${copyRRFData.requestRequisition.requiredFor != requiredFor.type}">
															<option value="${requiredFor.type}">${requiredFor.type}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="requiredFor" items="${resourceRequiredFor}">
														<option value="${requiredFor.type}">${requiredFor.type}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>

								
							<!-- AM Job Code Start -->
							<div class="form-group col-md-4 validateMe">
								<label for="amJobCode">AM Job Code<span class="text-inside-brackets">(<span class="required">*</span>
										for Staffing)
									</span></label>
								<div class="positionRel">
									<c:choose>
										<c:when test="${buttonFlag}">
											<input type="text" class="form-control" value="${dataList.requestRequisition.amJobCode}" id="amJobCode"
											 disabled="disabled">
										</c:when>
										<c:when test="${copyRRFFlag}">
											<input type="text" class="form-control" value="${copyRRFData.requestRequisition.amJobCode}" id="amJobCode"
											 disabled="disabled">
										</c:when>
										<c:otherwise>
											<input type="text" class="form-control" value="" id="amJobCode" disabled="disabled">
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<!-- AM Job Code End -->

								<div class="form-group col-md-4 validateMe">
									<label><span class="required">*</span><span data-toggle="tooltip" title="" data-placement="right"
										 data-original-title="Should be greater than today's date">Required Date</span></label>
									<div class="positionRel">
										<div class='input-group'>
											<c:choose>
												<c:when test="${buttonFlag}">
													<input type="text" value="${dataList.requestRequisition.resourceRequiredDate}" class="form-control" id="resourceRequiredDate"
													 placeholder="Select date">
												</c:when>
												<%-- <c:when test="${copyRRFFlag}">
													<input type="text" value="${dataList.requestRequisition.resourceRequiredDate}" class="form-control" id="resourceRequiredDate"
													 placeholder="Select date">
												</c:when> --%>
												<c:otherwise>
													<!-- <input type="text" onchange="dateCheck(this)" id="resourceRequiredDate" class="form-control"> -->
													<input type="text" id="resourceRequiredDate" class="form-control" placeholder="Select date">
												</c:otherwise>
											</c:choose>
											<label class="input-group-addon" for="resourceRequiredDate">
												<span class="glyphicon glyphicon-calendar"></span>
											</label>
										</div>
									</div>
								</div>

								<div class="form-group col-md-4 validateMe">
									<label for="clientTypeSelect"><span class="required">*</span>Client
										Type</label>
									<div class="positionRel" id="clientType">
										<select class="form-control required comboselect check" id="clientTypeSelect" placeholder="Select client type">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.requestRequisition.clientType}">${dataList.requestRequisition.clientType}</option>
													<c:forEach var="clientType" items="${clientTypeList}">
														<c:if test="${dataList.requestRequisition.clientType != clientType.type}">
															<option value="${clientType.type}">${clientType.type}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:when test="${copyRRFFlag}">
													<option class="selected-option" value="${copyRRFData.requestRequisition.clientType}">${copyRRFData.requestRequisition.clientType}</option>
													<c:forEach var="clientType" items="${clientTypeList}">
														<c:if test="${copyRRFData.requestRequisition.clientType != clientType.type}">
															<option value="${clientType.type}">${clientType.type}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="clientType" items="${clientTypeList}">
														<option value="${clientType.type}">${clientType.type}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>

								<div class="form-group col-md-4 validateMe" >
									<label for="clientNameSelect"><span class="required">*</span><span data-toggle="tooltip" data-placement="right"
										 title="" data-original-title="In case of no Client Group, it will be hidden">Client Name</span></label>
									<div class="positionRel" id="clientName">
									
										<c:choose>
												<c:when test="${buttonFlag}">
													<input type="text" id="clientNameSelect" hidden="true" disabled="disabled" value="${dataList.requestRequisition.customer.id}"
														placeholder="Enter client name" />
													<input type="text" id="clientNameSelectOnEdit" class="form-control" disabled="disabled" value="${dataList.requestRequisition.customer.customerName}"
														placeholder="Enter client name" />
												</c:when>
												<%--  <c:when test="${copyRRFFlag}">
												 <select class="form-control required comboselect check" id="clientNameSelect" placeholder="Select client name"> 
												 <option class="selected-option" id="clientNameSelect" value="${copyRRFData.requestRequisition.customer.id}">${copyRRFData.requestRequisition.customer.customerName}</option> 
													<c:forEach var="client" items="${allCustomers}">
														<c:if test="${copyRRFData.requestRequisition.customer.id != client.id}"> 
															<option value="${client.id}">${client.customerName}</option>
														 </c:if> 
													</c:forEach>
												 </select> 
											</c:when>  --%>
											<c:otherwise>
													<select class="form-control required comboselect check" id="clientNameSelect" placeholder="Select client name">
														<option value="">none selected</option>
														<c:forEach var="client" items="${allCustomers}">
														<option value="${client.id}">${client.customerName}</option>
													</c:forEach>
												</select>
											</c:otherwise>
										</c:choose>
										
									</div>
								</div>
								<div class="form-group col-md-4" id="grpdiv" style="display: none">
										<label for="clientGroupSelect"><span class="required">*</span>Client Group<span class="text-inside-brackets">(If
												Applicable)</span></label>
										<div class="positionRel">
											<c:choose>
												<c:when test="${buttonFlag}">
													<input type="text" id="clientGroupSelect" hidden="true" disabled="disabled" value="${dataList.requestRequisition.group.groupId}"
														placeholder="Enter client group" />
													<input type="text" id="clientGroupSelectOnEdit" class="form-control" disabled="disabled" value="${dataList.requestRequisition.group.customerGroupName}"
														placeholder="Enter client group" />
												</c:when>
											<c:when test="${copyRRFFlag}">
												<select class="form-control  required comboselect check" id="clientGroupSelect" placeholder="Select client group">												
														<option class="selected-option" id="clientNameSelect" value="${copyRRFData.requestRequisition.customer.id}">${copyRRFData.requestRequisition.customer.customerName}</option>
															<c:forEach var="client" items="${allCustomers}">
																<c:if test="${copyRRFData.requestRequisition.customer.id != client.id}">
																	<option value="${client.id}">${client.customerName}</option>
																</c:if>
															</c:forEach>
												</select>
											</c:when>
												
												<c:otherwise>
												<select class="form-control  required comboselect check" id="clientGroupSelect" placeholder="Select client group">												
												</select>													
												</c:otherwise>
											</c:choose>
										
										</div>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="numberOfPosition"><span class="required">*</span>Number of Position(s)</label>
									<c:choose>
										<c:when test="${buttonFlag}">
											<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
												<input type="number" min="1"
													value="${dataList.noOfResources}" class="form-control"
													id="numberOfPosition">
											</sec:authorize>
											<sec:authorize access="!hasAnyRole('ROLE_ADMIN')">
												<input type="number" min="1"
													value="${dataList.noOfResources}" disabled="disabled"
													class="form-control" id="numberOfPosition">
											</sec:authorize>
										</c:when>
										<c:when test="${copyRRFFlag}">
											<input type="number" min="1"
													value="${copyRRFData.noOfResources}" class="form-control"
													id="numberOfPosition">
										</c:when>
										<c:otherwise>
											<input type="number" min="1" class="form-control" id="numberOfPosition" placeholder="Enter no. of position">
										</c:otherwise>
									</c:choose>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="allocationTypeSelect"><span class="required">*</span>Resource Allocation As</label>
									<div class="positionRel" id="resourceAllocation">
										<select class="form-control  required comboselect check" id="allocationTypeSelect" placeholder="Select resource allocated as">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.allocationType.id}">${dataList.allocationType.aliasAllocationName}</option>
													<c:forEach var="allocation" items="${resourceAllocation}">
														<c:if test="${dataList.allocationType.id != allocation.id}">
															<option value="${allocation.id}">${allocation.aliasAllocationName}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:when test="${copyRRFFlag}">
													<option class="selected-option" value="${copyRRFData.allocationType.id}">${copyRRFData.allocationType.aliasAllocationName}</option>
													<c:forEach var="allocation" items="${resourceAllocation}">
														<c:if test="${copyRRFData.allocationType.id != allocation.id}">
															<option value="${allocation.id}">${allocation.aliasAllocationName}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="allocation" items="${resourceAllocation}">
														<option value="${allocation.id}" data-obj="${allocation.bghMandatoryFlag}">${allocation.aliasAllocationName}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="experienceRequiredSelect"><span class="required">*</span><span data-toggle="tooltip"
										 data-placement="right" title="" data-original-title="You can select others, in case required experience not available">Experience
											Required</span></label>
									<div class="positionRel" id="experienceReq">
										<select class="form-control  required comboselect check" id="experienceRequiredSelect" placeholder="Select experience level required">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.experience}" selected="selected">${dataList.experience}</option>
													<c:forEach var="experience" items="${experienceList}">
														<c:if test="${dataList.experience != experience.experienceRange}">
															<option value="${experience.experienceRange}">${experience.experienceRange}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:when test="${copyRRFFlag}">
													<option value="${copyRRFData.experience}" selected="selected">${copyRRFData.experience}</option>
													<c:forEach var="experience" items="${experienceList}">
														<c:if test="${copyRRFData.experience != experience.experienceRange}">
															<option value="${experience.experienceRange}">${experience.experienceRange}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="experience" items="${experienceList}">
														<option value="${experience.experienceRange}">${experience.experienceRange}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="form-group col-md-4" id="otherDetailsTextBox" style="display: none">
									<label for="expOtherDetails">If other, please specify</label>
									<c:choose>
										<c:when test="${buttonFlag}">
											<!--  <c:if test="${dataList.experience == 'Others'}"> -->
											<input type="text" class="form-control" id="expOtherDetails" value="${dataList.requestRequisition.expOtherDetails}" />
											<!-- </c:if> -->
											<c:if test="${dataList.experience != 'Others'}">
												<input type="text" class="form-control" id="expOtherDetails" />
											</c:if>
										</c:when>
										<c:when test="${copyRRFFlag}">
											<!--  <c:if test="${dataList.experience == 'Others'}"> -->
											<input type="text" class="form-control" id="expOtherDetails" value="${copyRRFData.requestRequisition.expOtherDetails}" />
											<!-- </c:if> -->
											<c:if test="${copyRRFData.experience != 'Others'}">
												<input type="text" class="form-control" id="expOtherDetails" />
											</c:if>
										</c:when>
										<c:otherwise>
											<input type="text" class="form-control" id="expOtherDetails" />
										</c:otherwise>
									</c:choose>
								</div>

								<div class="form-group col-md-4 validateMe">
										<label for="designationSelect"><span class="required">*</span><span data-toggle="tooltip" data-placement="right"
											 title="" data-original-title="Designation of the resource(s) required">Designation</span></label>
										<div class="positionRel" id="designation">
											<select class="form-control  required comboselect check" id="designationSelect" placeholder="Select designation">
												<c:choose>
													<c:when test="${buttonFlag}">
														<option value="${dataList.designation.id}">${dataList.designation.designationName}</option>
														<c:forEach var="designationObject" items="${designation}">
															<c:if test="${dataList.designation.id != designationObject.id}">
																<option value="${designationObject.id}">${designationObject.designationName}</option>
															</c:if>
														</c:forEach>
													</c:when>
													<c:when test="${copyRRFFlag}">
														<option value="${copyRRFData.designation.id}">${copyRRFData.designation.designationName}</option>
														<c:forEach var="designationObject" items="${designation}">
															<c:if test="${copyRRFData.designation.id != designationObject.id}">
																<option value="${designationObject.id}">${designationObject.designationName}</option>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:forEach var="designationObject" items="${designation}">
															<option value="${designationObject.id}">${designationObject.designationName}</option>
														</c:forEach>
													</c:otherwise>
												</c:choose>
											</select>
										</div>
									</div>
									
									
							<!-- Success Factor Id Start -->
							<div class="form-group col-md-4">
								<label for="successFactorId">Success Factor Id</label>
								<div class="positionRel">
									<c:choose>
										<c:when test="${buttonFlag}">
											<input type="text" class="form-control" value="${dataList.requestRequisition.successFactorId}" id="successFactorId">
										</c:when>
										<c:when test="${copyRRFFlag}">
											<input type="text" class="form-control" value="${copyRRFData.requestRequisition.successFactorId}" id="successFactorId">
										</c:when>
										<c:otherwise>
											<input type="text" class="form-control" value="" id="successFactorId">
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<!-- Success Factor Id End -->
							
							<!-- Requirement Area Start -->
							<div class="form-group col-md-4 validateMe">
								<label for="requirementAreaSelect"><span class="required">*</span>Requirement Area</label>
								<div class="positionRel" id="requirementArea">		
									<select class="form-control required comboselect check" id="requirementAreaSelect" placeholder="Select Requirement Area">
											<c:choose>
												<c:when test="${buttonFlag}">
													<c:choose>
													<c:when test="${dataList.requirementArea == 'NON_SAP'}">
														<option value="SAP">SAP</option>
														<option selected="selected" value="NON_SAP">Non SAP</option>
													</c:when>
													<c:when test="${dataList.requirementArea == 'SAP'}">
														<option selected="selected" value="SAP">SAP</option>
														<option value="NON_SAP">Non SAP</option>
													</c:when>
													<c:otherwise>
														<!-- <option value="-1">SELECT</option> -->
														<option value="${dataList.requirementArea}">${dataList.requirementArea}</option>
														<option value="SAP">SAP</option>
														<option value="NON_SAP">Non SAP</option>
													</c:otherwise>
													</c:choose>
												</c:when>
												<c:when test="${copyRRFFlag}">
													<c:choose>
														<c:when test="${copyRRFData.requirementArea == 'NON_SAP'}">
															<option value="SAP">SAP</option>
															<option selected="selected" value="NON_SAP">Non SAP</option>
														</c:when>
														<c:when test="${copyRRFData.requirementArea == 'SAP'}">
															<option selected="selected" value="SAP">SAP</option>
															<option value="NON_SAP">Non SAP</option>
														</c:when>
														<c:otherwise>
															<!-- <option value="-1">SELECT</option> -->
															<option value="${copyRRFData.requirementArea}">${copyRRFData.requirementArea}</option>
															<option value="SAP">SAP</option>
															<option value="NON_SAP">Non SAP</option>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<option value="SAP">SAP</option>
													<option value="NON_SAP">Non SAP</option>
												</c:otherwise>
											</c:choose>
										</select>
								</div>
							</div>
							<!-- Requirement Area End -->
							
						</div>
						</div>
						<div class="row">
							<div class="form-row">

							</div>
						</div>
						<div class="row">
							<div class="form-row">
								<c:choose>
									<c:when test="${buttonFlag}">
										<div class="form-group col-md-4 ts validateMe">
											<label for="resOnHold">On Hold</label> <input type="number"
												min="0" value="${dataList.hold}" class="form-control"
												id="resOnHold">
										</div>

										<div class="form-group col-md-4 validateMe">
											<label for="resLost">Lost</label> <input type="number"
												min="0" value="${dataList.lost}" class="form-control"
												id="resLost">
										</div>
									</c:when>
									<c:when test="${copyRRFFlag}">
										<div class="form-group col-md-4 validateMe"
											style="display: none;">
											<label for="resOnHold">On Hold</label> <input type="number"
												min="0" class="form-control" value="0" id="resOnHold">
										</div>
										<div class="form-group col-md-4" style="display: none;">
											<label for="resLost">Lost</label> <input type="number"
												min="0" class="form-control" value="0" id="resLost">
										</div>
									</c:when>
									<c:otherwise>
										<div class="form-group col-md-4 validateMe"
											style="display: none;">
											<label for="resOnHold">On Hold</label> <input type="number"
												min="0" class="form-control" value="0" id="resOnHold">
										</div>
										<div class="form-group col-md-4" style="display: none;">
											<label for="resLost">Lost</label> <input type="number"
												min="0" class="form-control" value="0" id="resLost">
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<hr />
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe">
									<label for="projectTypeSelect"><span class="required">*</span>Project Type</label>
									<div class="positionRel" id="projectType">
										<select class="form-control required comboselect check" id="projectTypeSelect" placeholder="Select project type">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.requestRequisition.projectType}">${dataList.requestRequisition.projectType}</option>
													<c:forEach var="projectType" items="${projectTypeList}">
														<c:if test="${dataList.requestRequisition.projectType != projectType.type}">
															<option value="${projectType.type}">${projectType.type}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:when test="${copyRRFFlag}">
													<option class="selected-option" value="${copyRRFData.requestRequisition.projectType}">${copyRRFData.requestRequisition.projectType}</option>
													<c:forEach var="projectType" items="${projectTypeList}">
														<c:if test="${copyRRFData.requestRequisition.projectType != projectType.type}">
															<option value="${projectType.type}">${projectType.type}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="projType" items="${projectTypeList}">
														<option value="${projType.type}">${projType.type}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="projectNameSelect"><span class="required">*</span>Project Name</label>
									<div class="positionRel" id="projectName">
										<c:choose>
											<c:when test="${buttonFlag}">
												<input type="text" id="projectNameSelect" hidden="true" disabled="disabled" value="${dataList.requestRequisition.project.id}"
												 placeholder="Enter project name" />
												<input type="text" id="projectNameSelectOnEdit" class="form-control" disabled="disabled" value="${dataList.requestRequisition.project.projectName}"
												 placeholder="Enter project name" />
											</c:when>
											<%--  <c:when test="${copyRRFFlag}">
												<input type="text" id="projectNameSelect" hidden="true" disabled="disabled" value="${copyRRFData.requestRequisition.project.id}"
												 placeholder="Enter project name" />
												<input type="text" id="projectNameSelectOnEdit" class="form-control" value="${copyRRFData.requestRequisition.project.projectName}"
												 placeholder="Enter project name" />
											</c:when>  --%>
											<c:otherwise>
												<select class="form-control  required comboselect check" id="projectNameSelect" placeholder="Select project name">
												</select>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="projectDuration"><span class="required">*</span>Project Duration<span class="text-inside-brackets">(in
											months)</span></label>
									<c:choose>
										<c:when test="${buttonFlag}">
											<input type="number" value="${dataList.requestRequisition.projectDuration}" min="1" class="form-control" id="projectDuration"
											 placeholder="Enter project duration">
										</c:when>
										<c:when test="${copyRRFFlag}">
											<input type="number" value="${copyRRFData.requestRequisition.projectDuration}" min="1" class="form-control" id="projectDuration"
											 placeholder="Enter project duration">
										</c:when>
										<c:otherwise>
											<input type="number" min="1" class="form-control" id="projectDuration" placeholder="Enter project duration">
										</c:otherwise>
									</c:choose>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="projectShiftTypeSelect"><span class="required">*</span>Project Shift Type</label>
									<div class="positionRel" id="projectShift">
										<select class="form-control required comboselect check" id="projectShiftTypeSelect" placeholder="Enter project shift type">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.requestRequisition.shiftType.id}">${dataList.requestRequisition.shiftType.shiftTimings}</option>
													<c:forEach var="shiftType" items="${shiftTypeList}">
														<c:if test="${dataList.requestRequisition.shiftType.id != shiftType.id }">
															<option value="${shiftType.id}">${shiftType.shiftTimings}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:when test="${copyRRFFlag}">
													<option class="selected-option" value="${copyRRFData.requestRequisition.shiftType.id}">${copyRRFData.requestRequisition.shiftType.shiftTimings}</option>
													<c:forEach var="shiftType" items="${shiftTypeList}">
														<c:if test="${copyRRFData.requestRequisition.shiftType.id != shiftType.id }">
															<option value="${shiftType.id}">${shiftType.shiftTimings}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="shiftType" items="${shiftTypeList}">
														<option value="${shiftType.id}">${shiftType.shiftTimings}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="form-group col-md-8">
									<label for="projectShiftDetails"><span data-toggle="tooltip" title="" data-placement="right"
										 data-original-title="Not Mandatory for General shift">Project Shift Details</span></label>
									<!-- <span class="text-inside-brackets">(Based on Project Shift Type)</span> -->
									<c:choose>
										<c:when test="${buttonFlag}">
											<!-- <input type="text" class="form-control" value="${dataList.requestRequisition.projectShiftOtherDetails}" id="projectShiftDetails">-->
											<textarea class="form-control" id="projectShiftDetails" placeholder="Enter project shift detials">${dataList.requestRequisition.projectShiftOtherDetails}</textarea>
										</c:when>
										<c:when test="${copyRRFFlag}">
											<!-- <input type="text" class="form-control" value="${dataList.requestRequisition.projectShiftOtherDetails}" id="projectShiftDetails">-->
											<textarea class="form-control" id="projectShiftDetails" placeholder="Enter project shift detials">${copyRRFData.requestRequisition.projectShiftOtherDetails}</textarea>
										</c:when>
										<c:otherwise>
											<textarea class="form-control" id="projectShiftDetails" placeholder="Enter project shift detials"></textarea>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<hr />
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe">
									<label for="res_skills"><span class="required">*</span><span data-toggle="tooltip" data-placement="right"
										 title="" data-original-title="Major Skill for which you are creating a request">Skill Family</span></label>
									<div class="positionRel" id="skillFamily">
										<c:choose>
											<c:when test="${buttonFlag}">
												<input type="text" hidden="true" id="res_skills" value="${dataList.skill.id}" />
												<input type="text" id="res_skills_onEdit" class="form-control" disabled="disabled" value="${dataList.skill.skill}"
												 placeholder="Enter job category">
											</c:when>
											<c:when test="${copyRRFFlag}">
												<select class="form-control  required comboselect check" id="res_skills" placeholder="Select job category">
												<option class="selected-option"  value="${copyRRFData.skill.id}">${copyRRFData.skill.skill}</option>
													<c:forEach var="skill" items="${skillsList}">
														<c:if test="${copyRRFData.skill.id!= skill.id}">
																<c:if test="${skill.skill != 'Not Avbl'}">
																	<option value="${skill.id}">${skill.skill}</option>
																</c:if>
														</c:if>
													</c:forEach>
												</select>
											</c:when>
											<c:otherwise>
												<select class="form-control  required comboselect check" id="res_skills" placeholder="Select job category">
													<c:forEach var="skill" items="${skillsList}">
														<c:if test="${skill.skill != 'Not Avbl'}">
															<option value="${skill.id}">${skill.skill}</option>
														</c:if>
													</c:forEach>
												</select>
											</c:otherwise>
										</c:choose>
										
									</div>
								</div>
								<div class="form-group col-md-4 validateMe">
									<label for="locationTypeSelect"><span class="required">*</span>Primary Job Location</label>
									<div class="positionRel" id="primaryJobLocation">
										<select class="form-control required comboselect check" id="locationTypeSelect" placeholder="Select primary job location">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.location.id}">${dataList.location.location}</option>
													<c:forEach var="location" items="${locations}">
														<c:if test="${dataList.location.id != location.id}">
															<option value="${location.id}">${location.location}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:when test="${copyRRFFlag}">
													<option class="selected-option"  value="${copyRRFData.location.id}">${copyRRFData.location.location}</option>
													<c:forEach var="location" items="${locations}">
														<c:if test="${copyRRFData.location.id != location.id}">
															<option value="${location.id}">${location.location}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="location" items="${locations}">
														<option value="${location.id}">${location.location}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<!-- new field introduced 
								<div class="form-group col-md-4 validateMe">
									<label for="locationTypeSelect"><span class="required">*</span>Secondary Job Location</label>
									<div class="positionRel">
										<select class="form-control required comboselect check" id="locationTypeSelect" placeholder="Select secondary job location">
											<c:choose>
												<c:when test="${buttonFlag}">
													<option value="${dataList.location.id}">${dataList.location.location}</option>
													<c:forEach var="location" items="${locations}">
														<c:if test="${dataList.location.id != location.id}">
															<option value="${location.id}">${location.location}</option>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="location" items="${locations}">
														<option value="${location.id}">${location.location}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								-->
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 customFileInput">
									<label for="buhApprovalSelect"><span class="required">*</span><span data-toggle="tooltip" data-placement="right"
										 title="" data-original-title="Mandatory for ODC">BUH Approval</span></label>
									<!-- <span class="text-inside-brackets">(Attachment only)</span></label> -->
									<div class="positionRel">
										<div class="input-group input-file">
											<c:choose>
												<c:when test="${buttonFlag}">
													<!-- 	<input type="file" class="form-control" id="buhApprovalSelect" placeholder='Choose a file...' /> -->
													<c:if test="${dataList.requestRequisition.BUHApprovalFileName != '' }">
														<input type="text" class="inputfile" id="buhFileName" value="${dataList.requestRequisition.BUHApprovalFileName}"
														 readonly placeholder="Choose and upload file" />
														<!-- <input type="text" class="form-control"  id="buhApprovalSelect" value="${dataList.requestRequisition.BUHApprovalFileName}"  style="display: none;"> -->
														<input type="file" class="form-control" id="buhApprovalSelect" placeholder='Choose and upload file' style="display: none;" />
														<label class="input-group-addon btn-choose" for="buhApprovalSelect">
															<!-- <span class="glyphicon glyphicon-paperclip"></span> -->
															Browse
														</label>
														<!-- <input type="text" class="form-control"  id="buhApprovalSelect" value="buhFileUploadedAlready"  style="display: none;"> 
                                             <span class="glyphicon glyphicon-paperclip"></span>-->
													</c:if>
													<c:if test="${dataList.requestRequisition.BUHApprovalFileName == '' }">
														<input type="text" class="form-control" value="" style="display: none;" placeholder="Choose and upload file">
														<!-- <input type="file" class="form-control" id="buhApprovalSelect" placeholder='Choose a file...' /> -->
														<!-- <span class="glyphicon glyphicon-paperclip"></span> -->
														Browse
													</c:if>
												</c:when>
												<c:otherwise>
													<input type="text" id="buhFileName" class="inputfile" readonly placeholder='Choose and upload file' />
													<input type="file" class="form-control" id="buhApprovalSelect" placeholder='Choose and upload file' style="display: none;" />
													<label class="input-group-addon btn-choose" for="buhApprovalSelect">
														<!-- <span class="glyphicon glyphicon-paperclip"></span> -->
														Browse
													</label>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</div>
								<div class="form-group col-md-4 customFileInput">
									<label for="bghApprovalSelect"><span data-toggle="tooltip" title="" data-placement="right" data-original-title="Mandatory for contingent and Investment">BGH
											Approval</span>
										<!-- <span class="text-inside-brackets">(<span class="required">*</span> for Non-Billable) -->
										</span></label>
									<div class="positionRel">
										<div class="input-group input-file">
											<c:choose>
												<c:when test="${buttonFlag}">
													<c:if test="${dataList.requestRequisition.BGHApprovalFileName != '' }">
														<input type="text" class="inputfile" id="bghFileName" value="${dataList.requestRequisition.BGHApprovalFileName}"
														 readonly placeholder="Choose and upload file" />
														<input type="file" class="form-control" id="bghApprovalSelect" placeholder='Choose and upload file' style="display: none" />
														<label class="input-group-addon btn-choose" for="bghApprovalSelect">
															<!-- <span class="glyphicon glyphicon-paperclip"></span> -->
															Browse
														</label>
													</c:if>
													<c:if test="${dataList.requestRequisition.BUHApprovalFileName == '' }">
														<input type="text" class="form-control" value="" style="display: none;" placeholder="Choose and upload file">
														<!-- <input type="file" class="form-control" id="bghApprovalSelect" placeholder='Choose a file...' /> -->
														<!-- <span class="glyphicon glyphicon-paperclip"></span> -->
														Browse
													</c:if>
												</c:when>
												<c:otherwise>
													<input type="text" class="inputfile" id="bghFileName" readonly placeholder="Choose and upload file" />
													<input type="file" class="form-control" id="bghApprovalSelect" placeholder='Choose and upload file' style="display: none" />
													<label class="input-group-addon btn-choose" for="bghApprovalSelect">
														<!-- <span class="glyphicon glyphicon-paperclip"></span> -->
														Browse
													</label>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
									<!-- <input type="file" disabled="disabled" class="form-control" id="bghApprovalSelect">-->
								</div>
							</div>
						</div>

						
							<div class="row">
							<div class="form-row">

							</div>
						</div> 

						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-3 autoSelect" style="display: none;">
									<label for="BUHNameSelect"><span class="required">*</span>BUH
										Name</label>
									<div class="positionRel">
										<c:choose>
											<c:when test="${buttonFlag}">
												<input type="text" id="BUHNameSelect" hidden="true" disabled="disabled" value="${dataList.requestRequisition.customer.id}" />
												<input type="text" class="form-control" disabled="disabled" value="${dataList.requestRequisition.customer.customerName}" />
											</c:when>
											<c:otherwise>
												<input class="form-control" disabled="disabled" id="BUHNameSelect">
												<%--  <option value="${BUHead.employeeId}">${BUHead.firstName} ${BUHead.lastName} - ${BUHead.yashEmpId} </option>
                                       <c:forEach var="user" items="${ActiveUserList}">
                                       
                                       <c:if  test="${BUHead.employeeId != user.employeeId}">
                                       <option value="${user.employeeId}">${user.firstName} ${user.lastName} - ${user.yashEmpId} </option>
                                       </c:if>
                                       </c:forEach> --%>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="form-group col-md-3 validateMe" style="display: none;">
									<label for="BGHNameSelect"><span class="required">*</span>BGH
										Name</label>
									<div class="positionRel">
										<c:choose>
											<c:when test="${buttonFlag}">
												<input type="text" id="clientNameSelect" hidden="true" disabled="disabled" value="${dataList.requestRequisition.customer.id}" />
												<input type="text" class="form-control" disabled="disabled" value="${dataList.requestRequisition.customer.customerName}" />
											</c:when>
											<c:otherwise>
												<input class="form-control" disabled="disabled" id="BGHNameSelect">
											</c:otherwise>
										</c:choose>
									</div>
									<!-- <input type="file" disabled="disabled" class="form-control" id="bghApprovalSelect">-->
								</div>
							</div>
						</div>

						<div class="mrT2 clearfix">
							<button type="button" id="prevToFirst" class="btn btn-secondary next-button changeToHiringInfo">Back</button>
							<button type="button" id="nextSecond" class="btn btn-primary next-button changeToHiringInfo">Next</button>
							<!-- <button type="button" class="btn btn-default">Cancel</button> -->
						</div>
					</form>
				</div>
				<div id="rmsForm3" class="tab-pane fade">
					 <c:if test="${buttonFlag}">
                  <h5 style="font-size: large;">RRF ID - ${dataList.requirementId}</h5>
                  </c:if>
					<h4 class="form-heading">Hiring Information</h4>
					<form>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-xs-6 col-md-4 validateMe">
									<label for="clientInterviewRequiredSelect"><span class="required">*</span><span data-toggle="tooltip"
										 data-placement="right" title="" data-original-title="Default Yes for Staffing">Client Interview Required</span></label>
									<div class="positionRel" id="clientInterviewReq">
										<select class="form-control required comboselect check" id="clientInterviewRequiredSelect">
											<c:choose>
												<c:when test="${buttonFlag}">
													<c:choose>
														<c:when test="${dataList.requestRequisition.isClientInterviewRequired == 'Y' }">
															<option selected="selected" value="${dataList.requestRequisition.isClientInterviewRequired}">Yes</option>
															<option value="N">No</option>
														</c:when>
														<c:when test="${dataList.requestRequisition.isClientInterviewRequired == 'N' }">
															<option selected="selected" value="${dataList.requestRequisition.isClientInterviewRequired}">No</option>
															<option value="Y">Yes</option>
														</c:when>
														<c:otherwise>
															<option value="Y">Yes</option>
															<option value="N">No</option>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:when test="${copyRRFFlag}">
													<c:choose>
														<c:when test="${copyRRFData.requestRequisition.isClientInterviewRequired == 'Y' }">
															<option selected="selected" value="${copyRRFData.requestRequisition.isClientInterviewRequired}">Yes</option>
															<option value="N">No</option>
														</c:when>
														<c:when test="${copyRRFData.requestRequisition.isClientInterviewRequired == 'N' }">
															<option selected="selected" value="${copyRRFData.requestRequisition.isClientInterviewRequired}">No</option>
															<option value="Y">Yes</option>
														</c:when>
														<c:otherwise>
															<option value="Y">Yes</option>
															<option value="N">No</option>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<option value="Y">Yes</option>
													<option value="N">No</option>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="form-group col-xs-6 col-md-4 validateMe autoSelect">
									<label for="bgvRequiredSelect"><span class="required">*</span><span data-toggle="tooltip" data-placement="right"
										 title="" data-original-title="Default No">Client BGV Required</span></label>
									<div class="positionRel" id="clientBgvReq">
										<select class="form-control required comboselect check" id="bgvRequiredSelect">
											<c:choose>
												<c:when test="${buttonFlag}">
													<c:choose>
														<c:when test="${dataList.requestRequisition.isBGVrequired == 'Y' }">
															<option class="selected-option" value="${dataList.requestRequisition.isBGVrequired}">Yes</option>
															<option value="N">No</option>
														</c:when>
														<c:when test="${dataList.requestRequisition.isBGVrequired == 'N' }">
															<option class="selected-option" value="${dataList.requestRequisition.isBGVrequired}">No</option>
															<option value="Y">Yes</option>
														</c:when>
														<c:otherwise>
															<option value="Y">Yes</option>
															<option value="N">No</option>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:when test="${copyRRFFlag}">
													<c:choose>
														<c:when test="${copyRRFData.requestRequisition.isBGVrequired == 'Y' }">
															<option class="selected-option" value="${copyRRFData.requestRequisition.isBGVrequired}">Yes</option>
															<option value="N">No</option>
														</c:when>
														<c:when test="${copyRRFData.requestRequisition.isBGVrequired == 'N' }">
															<option class="selected-option" value="${copyRRFData.requestRequisition.isBGVrequired}">No</option>
															<option value="Y">Yes</option>
														</c:when>
														<c:otherwise>
															<option value="Y">Yes</option>
															<option value="N">No</option>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<option value="Y">Yes</option>
													<option value="N" selected="selected" class="selected-option">No</option>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
							</div>
						</div>
						<hr />
						<!-- <div class="row">
							<div class="col-md-12">
								<h5 class="form-heading">
									<span class="required">*</span>Interview Panels <span class="text-inside-brackets">(preferrably E3 and above)</span>
								</h5>
							</div>
						</div> -->
						<div class="row">
							<div class="form-row">
								<div class="form-group clearfix">
									<label class="col-md-12 mrB2">Interview Panels <span class="text-inside-brackets">(preferrably E3 and above)</span></label>
									<div class="col-md-4">
										<label for="round1Select"><span data-toggle="tooltip" title="" data-placement="right" data-original-title="Select Interviwer(s) for round 1, mandatory for ODC">Key
												Interviewer(s) Round 1</span></label>
										<div class="positionRel combo-multiselet" id="keyInterview1">
											<select class="form-control required" id="round1Select" name="round1Select[]" multiple="multiple" placeholder="Press Ctrl and click to select mulitple">
												<!-- our code start -->
												<c:choose>
													<c:when test="${buttonFlag}">
																		<c:forEach var="mail" items="${dataList.keyInterviewersOneList}">
																				<option selected="selected" value="${mail.employeeId}">${mail.firstName} ${mail.lastName}</option>
																		</c:forEach>
													</c:when>
													<c:when test="${copyRRFFlag}">
																		<c:forEach var="mail" items="${copyRRFData.keyInterviewersOneList}">
																				<option selected="selected" value="${mail.employeeId}">${mail.firstName} ${mail.lastName}</option>
																		</c:forEach>
													</c:when>
													<c:otherwise>
														<%-- <c:forEach var="user" items="${ActiveUserList}">
															<option value="${user.employeeId}">${user.firstName} ${user.lastName}</option>
														</c:forEach> --%>
													</c:otherwise> 
												</c:choose>
											</select>
										</div>
									</div>
									<div class="col-md-8">
										<label for="keyInterviewer1"><span>&nbsp;</span></label>
										<!-- <input type="text" class="form-control col-md-12 disabled_val" disabled id="keyInterviewer1" /> -->
										<textarea  class="col-md-12 disabled_val form-control" id="keyInterviewer1" disabled></textarea>
									</div>
								</div>
								<div class="form-group clearfix">
									<div class="col-md-4">
										<label for="round2Select"><span data-toggle="tooltip" title="" data-placement="right" data-original-title="Select Interviwer(s) for round 2, mandatory for ODC">Key
												Interviewer(s) Round 2</span></label>
										<div class="positionRel combo-multiselet" id="keyInterview2">
											<select class="form-control required" id="round2Select" name="round2Select[]" multiple="multiple" placeholder="Press Ctrl and click to select mulitple">
												<!-- our code start -->
												<c:choose>
													<c:when test="${buttonFlag}">
																		<c:forEach var="mail" items="${dataList.keyInterviewersTwoList}">
																				<option selected="selected" value="${mail.employeeId}">${mail.firstName} ${mail.lastName}</option>
																		</c:forEach>
													</c:when>
													<c:when test="${copyRRFFlag}">
																		<c:forEach var="mail" items="${copyRRFData.keyInterviewersTwoList}">
																				<option selected="selected" value="${mail.employeeId}">${mail.firstName} ${mail.lastName}</option>
																		</c:forEach>
													</c:when>
													<c:otherwise>
														<%-- <c:forEach var="user" items="${ActiveUserList}">
															<option value="${user.employeeId}">${user.firstName} ${user.lastName}</option>
														</c:forEach> --%>
													</c:otherwise> 
												</c:choose>
												
											</select>
										</div>
									</div>
									<div class="col-md-8">
										<label for="keyInterviewer2"><span>&nbsp;</span></label>
										<!-- <input type="text" class="form-control col-md-12 disabled_val" disabled id="keyInterviewer2" /> -->
										<!-- <textarea id="keyInterviewer2" class="col-md-12" disabled></textarea> -->
										<textarea  class="col-md-12 disabled_val form-control" id="keyInterviewer2" disabled></textarea>
									</div>
								</div>
							</div>
						</div>
						<hr />

						<div class="row">
							<div class="col-md-12">
								<h5 class="form-heading">
									List the technical skills to be evaluated by Panels <span
										class="text-inside-brackets">*(SF Mandate)</span>
								</h5>
							</div>
						</div>
						<div class="row">
							<div class="form-row">
								<div class="form-group col-md-4 validateMe skill1select_wrap">
									<label for="skill1Select"><span class="required">*</span><span
										data-toggle="tooltip" data-placement="right" title=""
										data-original-title="Select list of topics to evaluate resource(s) upon">Key
											Scanners</span></label>

									<!-- <div class="positionRel combo-multiselet"> -->
									<div class="positionRel combo-multiselet" id="keyScanner">
										<select class="form-control required" id="skill1Select"
											name="reqID" multiple="multiple">
											<c:choose>
												<c:when test="${buttonFlag}">
													<c:forEach var="skill" items="${skillsList}">
														<c:choose>
															<c:when
																test="${fn:contains(dataList.skillsToEvaluate,skill.skill)}">
																<c:set var="skillObjects"
																	value="${fn:split(dataList.skillsToEvaluate, ',')}" />
																<c:forEach var="skillObject" items="${skillObjects}">
																	<%--   <fmt:parseNumber var="eid" type="number" value="${fn:trim(empid)}" /> --%>
																	<c:if test="${skillObject==skill.skill}">
																		<option selected="selected" value="${skill.skill}">${skill.skill}</option>
																	</c:if>
																</c:forEach>
															</c:when>
															<c:otherwise>
																<option value="${skill.skill}">${skill.skill}</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:when>
												<c:when test="${copyRRFFlag}">
													<c:forEach var="skill" items="${skillsList}">
														<c:choose>
															<c:when
																test="${fn:contains(copyRRFData.skillsToEvaluate,skill.skill)}">
																<c:set var="skillObjects"
																	value="${fn:split(copyRRFData.skillsToEvaluate, ',')}" />
																<c:forEach var="skillObject" items="${skillObjects}">
																	<%--   <fmt:parseNumber var="eid" type="number" value="${fn:trim(empid)}" /> --%>
																	<c:if test="${skillObject==skill.skill}">
																		<option selected="selected" value="${skill.skill}">${skill.skill}</option>
																	</c:if>
																</c:forEach>
															</c:when>
															<c:otherwise>
																<option value="${skill.skill}">${skill.skill}</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="skill" items="${skillsList}">
														<option value="${skill.skill}">${skill.skill}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>



								</div>
								<div class="form-group col-md-8">
									<label>SkillSet</label>
									<textarea id="skillSetRequired" class="col-md-12 form-control disabled_val" disabled></textarea>
								</div>
							</div>
						</div>
						<hr class="divideHr" />
						<div class="row">
							<div class="col-md-12">
								<h4 class="form-heading">Job Description</h4>
							</div>
						</div>
						<div class="row mrT2">
							<div class="form-group col-md-6 validateMe">
								<label for="primarySkills"><span class="required">*</span>Primary
									Skills</label>
								<c:choose>
									<c:when test="${buttonFlag}">
										<textarea class="form-control" id="primarySkills">${dataList.primarySkills}</textarea>
									</c:when>
									<c:when test="${copyRRFFlag}">
										<textarea class="form-control" id="primarySkills">${copyRRFData.primarySkills}</textarea>
									</c:when>
									<c:otherwise>
										<textarea class="form-control" id="primarySkills"
											placeholder="Please enter details"></textarea>
									</c:otherwise>
								</c:choose>

							</div>

							<div class="form-group col-md-6">
								<label for="additionalSkills">Additional Skills</label>
								<c:choose>
									<c:when test="${buttonFlag}">
										<textarea class="form-control" class="form-control"
											id="additionalSkills">${dataList.desirableSkills}</textarea>
									</c:when>
									<c:when test="${copyRRFFlag}">
										<textarea class="form-control" class="form-control"
											id="additionalSkills">${copyRRFData.desirableSkills}</textarea>
									</c:when>
									<c:otherwise>
										<textarea class="form-control" id="additionalSkills"
											placeholder="Please enter details"></textarea>
									</c:otherwise>
								</c:choose>
							</div>

						</div>
						<div class="row">
							<div class="form-group col-md-6">

								<label for="rolesAndResponsibilities">Roles and
									Responsibilities</label>
								<c:choose>
									<c:when test="${buttonFlag}">
										<textarea class="form-control" id="rolesAndResponsibilities">${dataList.responsibilities}</textarea>
									</c:when>
									<c:when test="${copyRRFFlag}">
										<textarea class="form-control" id="rolesAndResponsibilities">${copyRRFData.responsibilities}</textarea>
									</c:when>
									<c:otherwise>
										<textarea class="form-control" id="rolesAndResponsibilities"
											placeholder="Please enter details"></textarea>
									</c:otherwise>
								</c:choose>

							</div>

							<div class="form-group col-md-6">

								<label for="additionalComment">Additional Comment</label>
								<c:choose>
									<c:when test="${buttonFlag}">
										<textarea class="form-control" id="additionalComment">${dataList.requestRequisition.comments}</textarea>
									</c:when>
									<c:when test="${copyRRFFlag}">
										<textarea class="form-control" id="additionalComment">${copyRRFData.requestRequisition.comments}</textarea>
									</c:when>
									<c:otherwise>
										<textarea class="form-control" id="additionalComment"
											placeholder="Please enter details"></textarea>
									</c:otherwise>
								</c:choose>

							</div>
						</div>
						<!-- new field introduced
						<hr />
						<div class="row">
							<label>List the technical skills to be evaluated by Panels * (SF Mandate)</label>
							<div class="form-row">
								<div class="form-group  col-md-6 col-sm-12 col-xs-12">
									<label>Primary Skills <span class="text-inside-brackets">(Must Have)</span></label>
									<textarea></textarea>
								</div>

								<div class="form-group  col-md-6 col-sm-12 col-xs-12">
									<label>Secondary Skills <span class="text-inside-brackets">(Good to have)</span></label>
									<textarea placeholder="Enter additional or good to have skills"></textarea>
								</div>
							</div>
						</div>
						<hr />
						<div class="row">
							<div class="col-xs-12">
								<h4 class="form-heading">Hiring Information</h4>
							</div>
							<div class="form-row">
								<div class="clearfix">
									<div class="form-group col-md-6">
										<label>Primary Skills</label>
										<textarea placeholder="Enter primary skills" class="form-control"></textarea>
									</div>
									<div class="form-group col-md-6">
										<label>Additional Skills</label>
										<textarea placeholder="Enter additional skills" class="form-control"></textarea>
									</div>
								</div>
								<div class="clearfix">
									<div class="form-group col-md-6">
										<label>Roles & Responsibilities</label>
										<textarea placeholder="Enter your roles and responsibilities here" class="form-control"></textarea>
									</div>
									<div class="form-group col-md-6">
										<label>Comments</label>
										<textarea placeholder="Enter comments here" class="form-control"></textarea>
									</div>
								</div>
							</div>
						</div>
						-->
						<!-- Below code is not used in RRF and it gives error because data not coming from backend -->
						<%-- <div class="row">
							<div class="form-row" style="display: none;">
								<div class="form-group col-xs-6 col-md-2">
									<label for="salaryRangeSelect">Curreny Type</label>
									<div class="form-group">
										<select class="form-control" id="currencyType">
											<option value="dollor">Doller ($)</option>
											<option value="pound">Pound (£)</option>
										</select>
									</div>
								</div>
								<div class="form-group col-xs-6 col-md-3">
									<fieldset disabled>
										<label for="salaryRangeSelect">Salary Range<span class="text-inside-brackets">(per annum)</span></label>
										<div class="form-group">
											<select class="form-control" id="salaryRangeSelect">
											</select>
										</div>
									</fieldset>
								</div>
								<div class="form-group  col-xs-6 col-md-3">
									<label for="billRate">Bill Rate/hr</label>
									<input type="text" class="form-control" id="billRate">
									<c:choose>
										<c:when test="${dataList.lost}">
											<div class="form-group col-md-4 validateMe">
												<label for="resLost">Lost</label> <input type="number" min="0" value="${dataList.lost}" class="form-control"
												 id="resLost">
											</div>
										</c:when>
										<c:otherwise>
											<div class="form-group col-md-4 validateMe" style="display: none;">
												<label for="resOnHold">On Hold</label> <input type="number" min="0" class="form-control" value="0" id="resOnHold">
											</div>
											<div class="form-group col-md-4" style="display: none;">
												<label for="resLost">Lost</label> <input type="number" min="0" class="form-control" value="0" id="resLost">
											</div>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div> --%>
						<div class="clearfix mrT2">
							<button type="button" id="prevToSecond" class="btn btn-secondary next-button changeToHiringInfo">Back</button>
							<button type="button" id="nextFourth" class="btn btn-primary next-button changeToHiringInfo">Next</button>
							<!-- <button type="button" id="cancelBtn" class="btn btn-default">Cancel</button> -->
						</div>
					</form>
				</div>
				<div id="rmsForm4" class="tab-pane fade">
					<!-- <div class="process-steps">
                  <span><strong>4</strong> out of 4</span>
                  </div> -->
                  <c:if test="${buttonFlag}">
                  <h5 style="font-size: large;">RRF ID - ${dataList.requirementId}</h5>
                  </c:if>
                  	
					<form>
						
							<div class="row">
								<div class="form-group col-md-4">
									<input name="chkEmailTrigger" id="chkEmailTrigger"
										type="checkbox" class="checkboxclass">Do you want to
									restrict Email Notification ?
								</div>
							</div>
						
						<div class="row">
							<div class="form-group col-md-12 validateMe">
								<div class="row">
									<div class="col-md-4">
										<label for="sendMailTo"><span>Mail to (TO)</span></label>
										<div class="positionRel combo-multiselet">
										<select class="form-control required" id="sendMailTo" name="sendMailTo[]" multiple="multiple" placeholder="Press Ctrl and click to select mulitple">
												<!-- our code start -->
												<c:choose>
													<c:when test="${buttonFlag}">
																		<c:forEach var="empid" items="${dataList.requestRequisition.sentMailToList}">
																				<option selected="selected" value="${empid.employeeId}">${empid.firstName} ${empid.lastName}</option>
																		</c:forEach>
													</c:when>
													<c:when test="${copyRRFFlag}">
																		<c:forEach var="empid" items="${copyRRFData.requestRequisition.sentMailToList}">
																				<option selected="selected" value="${empid.employeeId}">${empid.firstName} ${empid.lastName}</option>
																		</c:forEach>
													</c:when>
													<c:otherwise>
														<%-- <c:forEach var="user" items="${ActiveUserList}">
															<option value="${user.employeeId}">${user.firstName} ${user.lastName}</option>
														</c:forEach> --%>
													</c:otherwise> 
												</c:choose>
											</select>											
										</div>
									</div>
									<div class="col-md-8">
										<label for="sendMailTo_val">&nbsp;</label>
										<!-- <input type="text" class="col-md-12" id="sendMailTo_val" disabled="disabled" /> -->
										<textarea id="sendMailTo_val" class="col-md-12  form-control disabled_val" disabled="disabled"></textarea>
									</div>
								</div>

							</div>
							<div class="form-group col-md-12 validateMe">
								<div class="row">
									<div class="col-md-4">
										<label for="notifyToIds"><span>Notify to (CC)</span></label>
										<div class="positionRel combo-multiselet">
										<select class="form-control required" id="notifyToIds" name="notifyToIds[]" multiple="multiple" placeholder="Press Ctrl and click to select mulitple">
												<!-- our code start -->
												<c:choose>
													<c:when test="${buttonFlag}">
																		<c:forEach var="empid" items="${dataList.requestRequisition.notifyMailToList}">
																				<option selected="selected" value="${empid.employeeId}">${empid.firstName} ${empid.lastName}</option>
																		</c:forEach>
													</c:when>
													<c:when test="${copyRRFFlag}">
																		<c:forEach var="empid" items="${copyRRFData.requestRequisition.notifyMailToList}">
																				<option selected="selected" value="${empid.employeeId}">${empid.firstName} ${empid.lastName}</option>
																		</c:forEach>
													</c:when>
													<c:otherwise>
														<%-- <c:forEach var="user" items="${ActiveUserList}">
															<option value="${user.employeeId}">${user.firstName} ${user.lastName}</option>
														</c:forEach> --%>
													</c:otherwise> 
												</c:choose>
											</select>
										</div>
									</div>
									<div class="col-md-8">
										<label for="notifyToIds_val">&nbsp;</label>
										<!-- <input type="text" class="col-md-12 form-control disabled_val" id="notifyToIds_val" disabled="disabled" /> -->
										<textarea id="notifyToIds_val" class="col-md-12 form-control disabled_val" disabled="disabled"></textarea>
									</div>
								</div>
							</div>
							<div class="form-group col-md-12 validateMe">
								<div class="row">
									<div class="col-md-4">
										<label for="pdlMailIds"><span>PDL (CC)</span></label>
										<div class="positionRel combo-multiselet">
											<select id="pdlMailIds" class="required" name="reqID" multiple="multiple">
												<c:choose>
													<c:when test="${buttonFlag}">
														<c:forEach var="pdl" items="${pdlList}">
															<c:choose>
																<c:when
																	test="${fn:contains(dataList.requestRequisition.pdlEmailIds,pdl.id)}">
																	<c:set var="mailIds"
																		value="${fn:split(dataList.requestRequisition.pdlEmailIds, ' ,')}" />
																	<c:forEach var="pdlid" items="${mailIds}">
																		<%--  <fmt:parseNumber var="pid" value="${fn:trim(pdlmailid)}" /> --%>
																		<c:if test="${pdlid==pdl.id}">
																			<option selected="selected" value="${pdl.id}">${pdl.pdlEmailId}</option>
																		</c:if>
																	</c:forEach>
																</c:when>
																<c:otherwise>
																	<option value="${pdl.id}">${pdl.pdlEmailId}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</c:when>
													<c:when test="${copyRRFFlag}">
														<c:forEach var="pdl" items="${pdlList}">
															<c:choose>
																<c:when
																	test="${fn:contains(copyRRFData.requestRequisition.pdlEmailIds,pdl.id)}">
																	<c:set var="mailIds"
																		value="${fn:split(copyRRFData.requestRequisition.pdlEmailIds, ' ,')}" />
																	<c:forEach var="pdlid" items="${mailIds}">
																		<%--  <fmt:parseNumber var="pid" value="${fn:trim(pdlmailid)}" /> --%>
																		<c:if test="${pdlid==pdl.id}">
																			<option selected="selected" value="${pdl.id}">${pdl.pdlEmailId}</option>
																		</c:if>
																	</c:forEach>
																</c:when>
																<c:otherwise>
																	<option value="${pdl.id}">${pdl.pdlEmailId}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:forEach var="pdl" items="${pdlList}">
															<c:choose>
																<c:when test="${pdl.pdlEmailId=='RMG-NonSap@yash.com'}">
																	<option selected="selected" value="${pdl.id}">${pdl.pdlEmailId}</option>
																</c:when>
																<c:otherwise>
																	<option value="${pdl.id}">${pdl.pdlEmailId}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</c:otherwise>
												</c:choose>
											</select>
										</div>
									</div>
									<div class="col-md-8">
										<label>&nbsp;</label>
										<textarea id="pdlMailIds_val" class="col-md-12  form-control disabled_val" disabled="disabled"></textarea>
									</div>
								</div>
							</div>
							<div class="mrT2 clearfix col-md-12">
							<input type="text" id="copyFlagButton" value="${copyFlag}" style=" display: none;">
								<c:choose>
									<c:when test="${buttonFlag}">
										<c:if test="${copyFlag eq 'copyFlagTrue'}">
											<button type="button" id="prevToThird" class="btn btn-secondary next-button changeToHiringInfo">Back</button>
											<button type="button" id="previewBtnClicked" class="btn btn-primary next-button">Preview</button>
											<button type="button" id="submitForm" class="btn btn-primary next-button">Save COPY</button>
											<button type="button" id="discardButton" onclick="discardCopy()" class="btn btn-primary next-button">Discard</button>
										</c:if>
										<c:if test="${copyFlag ne 'copyFlagTrue'}">
											<button type="button" id="prevToThird" class="btn btn-secondary next-button changeToHiringInfo">Back</button>
											<button type="button" id="previewBtnClicked" class="btn btn-primary next-button">Preview</button>
											<button type="button" id="submitForm" class="btn btn-primary next-button">Update</button>
										</c:if>
									</c:when>
									
									<c:otherwise>
										<button type="button" id="prevToThird" class="btn btn-secondary next-button changeToHiringInfo">Back</button>
										<button type="button" id="previewBtnClicked" class="btn btn-primary next-button">Preview</button>
										<button type="button" id="submitForm" class="btn btn-primary next-button">Submit</button>
									</c:otherwise>
								</c:choose>
							</div>
					</form>
				</div>
			</div>
			<div id="myModal" class="modal">
				<!-- Modal content -->
				<div class="modal-content" id="reasonForm">
					<span class="close">&times;</span>
					<table class="table">
						<tr>
							<td>
								<div class="positionRel validateMe">
									<label for="resourceReplacing"><span class="required">*</span>Please select resource you are replacing :</label>
								</div>
							</td>
							<td>
								<div class="form-group validateMe">
									<div class="positionRel">
									
											<select class="form-control required" id="resourceForReplacement" name="resourceForReplacement" placeholder="Select resources">
												<c:choose>
													<c:when test="${buttonFlag}">
														<option class="selected-option" value="${dataList.requestRequisition.replacementResource.employeeId}">${dataList.requestRequisition.replacementResource.firstName} ${dataList.requestRequisition.replacementResource.lastName}</option>
														<%-- <c:forEach var="mail" items="${ActiveUserList}">
															<c:if test="${mail.employeeId != dataList.requestRequisition.replacementResource.employeeId}">
																<option value="${mail.employeeId}">${mail.firstName} ${mail.lastName}</option>
															</c:if>
														</c:forEach> --%>
													</c:when>
													<c:otherwise>
														<%-- <c:forEach var="mail" items="${ActiveUserList}">
															<option value="${mail.employeeId}">${mail.firstName} ${mail.lastName}</option>
														</c:forEach> --%>
													</c:otherwise>
												</c:choose>
											</select>
									</div>
									
									<div class="required-resource">Required*</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="validateMe">
									<label for="reasonReplacing"><span class="required">*</span> Please select reason for replacement:</label>
								</div>
							</td>
							<td>
								<div class="form-group validateMe">
									<div class="positionRel">
											<select class="form-control required comboselect check" id="reasonForReplacement">
												<c:choose>
													<c:when test="${buttonFlag}">
														<c:forEach var="reasonObj" items="${reasonList}">
															<option value="${dataList.requestRequisition.reason.id}">${dataList.requestRequisition.reason.reason}</option>
															<c:if test="${dataList.requestRequisition.reason.id != reasonObj.id}">
																<option value="${reasonObj.id}">${reasonObj.reason}</option>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:forEach var="reasonObj" items="${reasonList}">
															<option value="${reasonObj.id}">${reasonObj.reason}</option>
														</c:forEach>
													</c:otherwise>
												</c:choose>
											</select>
									</div>
									<div class="required-resource">Required*</div>
								</div>
							</td>
						</tr>
					</table>
					<button type="button" id="reasonButton" class="btn btn-secondary next-button">Save Reason</button>
				</div>
			</div>
			<div id="myPreviewModal" class="modal">
				<!--My Preview Modal content -->
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title">Resource Requisition Form <small><em>(Preview Mode)</em></small></h4>
							<button type="button" class="close" data-dismiss="modal" aria-label="Close" id="close-icon">
							<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<!-- <span class="close" id="close-icon">&times;</span> -->
						<div class="modal-body">
								<div class="">
									<h5>Step 1 Delivery Information</h5>
									<div class="form-row">
										<div class="form-group row">
											<div class="col-md-4 col-xs-12">
												<label >Hiring BG/BU</label>
											</div>
											<div id="previewHiringBGBU" class="col-md-8 col-xs-12">val</div>
										</div>
										<!--   <div class="form-group col-md-4">
									<label>Priority</label><br>
									<div id="previewPriority"></div>
									</div> -->
										<div class="form-group row">
											<div class="col-md-4 col-xs-12">
											<label>Requestor's BG/BU</label>
											</div>
											<div id="previewRequestorsBGBU" class="col-md-8 col-xs-12">val</div>
										</div>
										<div class="form-group row">
											<div class="col-md-4 col-xs-12">
												<label>Request Date</label>
											</div>
											<div id="previewRequestDate" class="col-md-8 col-xs-12">val</div>
										</div>
										<div class="form-group row">
											<div class="col-md-4 col-xs-12">
												<label>Delivery POC</label>
											</div>
											<div id="previewDeliveryPOC" class="col-md-8 col-xs-12">val</div>
										</div>
										<div class="form-group row">
											<div class="col-md-4 col-xs-12">
											<label>Requestor's Name</label>
											</div>
											<div id="previewRequestorsName"  class="col-md-8 col-xs-12">val</div>
										</div>
										<div class="form-group row">
											<div class="col-md-4 col-xs-12">
											<label>RMG POC</label>
											</div>
											<div id="previewRMGPoc"  class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row">
											<div class="col-md-4 col-xs-12">
											<label>TAC Team POC</label>
											</div>
											<div id="previewTecTeamPoc"  class="col-md-8 col-xs-12"></div>
										</div>
										<!-- <div class="form-group col-md-4">
									<label>Requestor Grade</label>
									<div id="previewRequestorsName">val</div>
									</div>
									<div class="form-group col-md-4">
									<label>Requestor Designation</label>
									<div>val</div>
									</div> -->
									</div>
								</div>
								<div class="">
									<h5>Step 2 Customer & Project Information</h5>
									<div class="form-row">
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Requirement Type</label>
												</div>
											<div id="previewRequirementType" class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Resource Required for</label>
												</div>
											<div id="previewRequiredFor" class="col-md-8 col-xs-12"></div>
										</div>
										<!-- Added new Field AM job Code Start -->
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>AM Job Code</label>
												</div>
											<div id="previewAmJobCode" class="col-md-8 col-xs-12">-</div>
										</div>
										<!-- Added new Field AM job Code End -->
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Resource Required Date</label>
												</div>
											<div id="previewRequiredDate" class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Client Type</label>
												</div>
											<div id="previewClientType" class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Client Name</label>
												</div>
											<div id="previewClientName" class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row previewGrp">
												<div class="col-md-4 col-xs-12">
											<label>Client Group</label>
												</div>
											<div id="previewClientGroup" class="col-md-8 col-xs-12">-</div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Project Type</label>
												</div>
											<div id="previewProjectType" class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Project Name</label>
												</div>
											<div id="previewProjectName" class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Competency/Skill Family</label>
												</div>
											<div id="previewCompetency" class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Number of Position(s)</label>
												</div>
											<div id="previewPositions"  class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row hold" style="display: none;">
												<div class="col-md-4 col-xs-12">
											<label>Hold</label>
												</div>
											<div id="previewHold" class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row lost" style="display: none;">
												<div class="col-md-4 col-xs-12">
											<label>Lost</label>
												</div>
											<div id="previewLost" class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Designation</label>
												</div>
											<div id="previewDesignation" class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Success Factor Id</label>
												</div>
											<div id="previewSuccessFactorId" class="col-md-8 col-xs-12">-</div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Experience Required</label>
												</div>
											<div id="previewExpRequired" class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Resource Allocation Type</label>
												</div>
											<div id="previewAllocationType"  class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Primary Job Location</label>
												</div>
											<div id="previewLocation" class="col-md-8 col-xs-12"></div>
										</div>
										<!-- <div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Secondary Job Location</label>
												</div>
											<div id="previewLocation" class="col-md-8 col-xs-12"></div>
										</div> -->
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Project Duration <small><em>(in months)</em></small></label>
												</div>
											<div id="previewProjectDuration" class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Project Shift Type</label>
												</div>
											<div id="previewShiftType" class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row">
											<div class="col-md-4 col-xs-12">
											<label>Project Shift Details</label>
											</div>
											<div id="previewShiftDetails"  class="col-md-8 col-xs-12">-</div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Requirement Area</label>
												</div>
											<div id="previewRequirementArea" class="col-md-8 col-xs-12"></div>
										</div>
									</div>
								</div>
								<div class="">
									<h5>Step 3 Hiring/Job Description</h5>
									<div class="form-row">
										<div class="form-group row">
											<div class="col-md-4 col-xs-12">
											<label>Client Interview Required</label>
											</div>
											<div id="previewClientInterview" class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row">
											<div class="col-md-4 col-xs-12">
											<label>Client BGV Required</label>
											</div>
											<div id="previewBGVRequired" class="col-md-8 col-xs-12"></div>
										</div>
										<div style="clear: both;"></div>
										<h4 class="mrT2">Interview Panels <small><em>(preferrably E3 and above)</em></small> :</h4>
										<div class="form-group row mrT2">
											<div class="col-md-4 col-xs-12">
											<label>Round 1 <small><em>(Multiselect)</em></small></label>
											</div>
											<div id="previewRound1" class="col-md-8 col-xs-12">-</div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Round 2 <small><em>(Multiselect)</em></small></label>
												</div>
											<div id="previewRound2" class="col-md-8 col-xs-12">-</div>
										</div>
										<div style="clear: both;"></div>
										<h4 class="mrT2">List the technical skills to be evaluated by Panels:</h4>
										<div class="form-group row mrT2">
												<div class="col-md-4 col-xs-12">
											<label>Key Scanners</label>
												</div>
											<div id="previewSkillsToEval" class="col-md-8 col-xs-12">-</div>
										</div>
										<!--  <div class="form-group row">
									<label>SkillSet</label>
										<div id="previewShiftDetails"></div>
									</div> -->
										<div style="clear: both;"></div>
										<h4 class="mrT2">Job Description</h4>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Primary Skills</label>
												</div>
											<div id="previewPrimarySkills" class="col-md-8 col-xs-12"></div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Additional Skills</label>
												</div>
											<div id="previewAddSkills" class="col-md-8 col-xs-12">-</div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Roles and Responsibilities</label>
												</div>
											<div id="previewRoles" class="col-md-8 col-xs-12">-</div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Additional Comment</label>
												</div>
											<div id="previewAdditionalComments"  class="col-md-8 col-xs-12">-</div>
										</div>
									</div>
								</div>
								<div class="">
										<h5>Step 4 Notify & Submit</h5>
									<div class="form-row">
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Mail to: (Send Mail To)</label>
												</div>
											<div id="previewMailTo" class="col-md-8 col-xs-12">-</div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>Notify to: (CC)</label>
												</div>
											<div id="previewNotifyTo" class="col-md-8 col-xs-12">-</div>
										</div>
										<div class="form-group row">
												<div class="col-md-4 col-xs-12">
											<label>PDL (CC)</label>
												</div>
											<div id="previewPDl" class="col-md-8 col-xs-12">-</div>
										</div>
									</div>
								</div>
								<button type="button" id="close-btn" class="btn btn-primary next-button mrT2">Close</button>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
 
	<script>
		if (${copyRRFFlag}) {
			setTimeout(function() {
					$('#rrf_page .comboselect').combobox('destroy').combobox()
			}, 300);
		}	
		</script>
</body>

</html>