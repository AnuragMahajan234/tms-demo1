<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.yash.rms.util.Constants"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<div class="headSection">
<div class="header">
<h1 align="center" style="color:#72A1C9 ">Release Notes</h1>
</div></div>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />
<!--[if IE]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<![endif]-->
<spring:url value="/resources/images/favicon.ico" var="favicon"/>
    <spring:url value="/resources/js-framework/jquery-1.7.2.min.js?ver=${app_js_ver}" var="jquery_url"/>
	<spring:url value="/resources/js-framework/jquery-ui/jquery-ui-1.8.22.custom.min.js?ver=${app_js_ver}" var="jquery_ui_1_8_21_custom_min_js"/>
	<spring:url value="/resources/js-framework/jquery-ui/combobox.js?ver=${app_js_ver}" var="combobox_js"/>
	<spring:url value="/resources/js-framework/form2js/form2js.js?ver=${app_js_ver}" var="form2js_js"/>
	<spring:url value="/resources/js-framework/form2js/jquery.toObject.js?ver=${app_js_ver}" var="jquery_toObject_js"/>
	<spring:url value="/resources/js-framework/form2js/js2form.js?ver=${app_js_ver}" var="js2form_js"/>
	<spring:url value="/resources/js-framework/form2js/json2.js?ver=${app_js_ver}" var="json2_js"/>
	<spring:url value="/resources/js-framework/validate/jquery.validate.min.js?ver=${app_js_ver}" var="jquery_validate_min_js"/>
	<spring:url value="/resources/js-framework/validate/jquery.validVal-4.0.3-packed.js?ver=${app_js_ver}" var="jquery_validVal"/>
	<spring:url value="/resources/js-framework/validate/additional-methods.min.js?ver=${app_js_ver}" var="additional_methods_min_js"/>
	<spring:url value="/resources/js-framework/jsrender/jsrender.js?ver=${app_js_ver}" var="jsrender_js"/>
	<spring:url value="/resources/js-framework/jquery.autogrowtextarea.js?ver=${app_js_ver}" var="jquery_autogrowtextarea_js"/>
	<spring:url value="/resources/js-user/rmsCommon.js?ver=${app_js_ver}" var="rmsCommon_js"/>
	<spring:url value="/resources/js-user/validations.js?ver=${app_js_ver}" var="validations_js"/>
	<script src="${jquery_url}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${jquery_ui_1_8_21_custom_min_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${combobox_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
	<script src="${json2_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
	<script src="${form2js_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${jquery_toObject_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${js2form_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
	<script src="${jquery_validate_min_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${additional_methods_min_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${jquery_validVal}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${jsrender_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
     <script src="${jquery_autogrowtextarea_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    
    <!-- required for common functions -->
    <script src="${rmsCommon_js}" type="text/javascript"></script>
  	<!-- required for common validations -->
  	<script src="${validations_js}" type="text/javascript"></script>
 </head>
 <script type="text/javascript">
 $(document).ready(function(){
 $('.fileName').live(
		    'click',
		    function(e) {
	 
	 var id = $(this).attr('id');
     document.getElementById('fileName').value=id;	 
     
     document.getElementById("myForm").submit();
		    });
 });
</script>
<body>
<%-- <<spring:url value="/help/pptDownload" var="url"/> --%>
<div class="container">
					<div class="contentArea" style="padding-top: 8px">
<form   id="myForm">

<table border="1">
<thead>
						<tr>
							<th width="1%" align="center" valign="middle">S. No.</th>
							<th width="1%" align="center" valign="middle" id="name">Release Version
								</th>
								<th width="1%" align="center" valign="middle">Type</th>
							<th width="1%" align="center" valign="middle">Release Date</th>
							<th width="10%" align="center" valign="middle">Description</th>
							
							
						 
						</tr>
					</thead>
	<c:forEach var="releasenotes" varStatus="counter" items="${releasenotes}">
	 
	<tr>
  <td align="center" valign="middle">${counter.index+1}</td>
  <td align="center" valign="middle"><c:if
										test="${empty releasenotes.releaseNumber}">N.A.</c:if> <c:if
										test="${not empty releasenotes.releaseNumber}">${releasenotes.releaseNumber}</c:if>
								</td>  
								
								 <td align="center" valign="middle"><c:if
										test="${empty releasenotes.type}">N.A.</c:if> <c:if
										test="${not empty releasenotes.type}">${releasenotes.type}</c:if>
								</td> 
   <td align="center" valign="middle"><c:if
										test="${empty releasenotes.releaseDate}">N.A.</c:if> 
										<c:if
										test="${not empty releasenotes.releaseDate}">
										<fmt:formatDate value='${releasenotes.releaseDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>
										
										</c:if>
										 
								</td> 
								 <td align="left" valign="middle"><c:if
										test="${empty releasenotes.description}">N.A.</c:if> <c:if
										test="${not empty releasenotes.description}">${releasenotes.description}</c:if>
								</td>
								 
<!-- <tr><td><a href="#" name="" class="fileName" id="ResourceAllocation" >Resource Allocation </a></td></tr> -->
</tr>
 
</c:forEach>
</table>

</form>
</div>
</div>
</body>
</html>