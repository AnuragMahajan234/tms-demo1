<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />
<div class="headSection">
<div class="header">
<h1 align="center" style="color:#72A1C9 ">RMS Guide</h1>
</div></div>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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

<form action="help/pptDownload" id="myForm">

<table align="center">

<input type="hidden" name="fileName" id="fileName" />

<tr><td><a href="#" name="" class="fileName" id="GuideforRole_BG_Admin">Guide for Role_BG_Admin</a></td></tr>

<tr><td><a href="#" name="" class="fileName" id="GuideforRole_Del_Manager">Guide for Role_Del_Manager</a></td></tr>

<tr><td><a href="#" name="" class="fileName" id="GuideforRole_Manager">Guide for Role_Manager</a></td></tr>

<tr><td><a href="#" name="" class="fileName" id="GuideforRole_User">Guide for Role_User</a></td></tr>

</table>

</form>
</body>
</html>