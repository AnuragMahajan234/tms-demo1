 <!DOCTYPE HTML>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
<head>
<!--[if IE]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<![endif]-->
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="pragma" content="no-cache" />
	
	
	<spring:url value="styles/style.css?ver=${app_js_ver}" var="style_css"/>
	<link href="${style_css}" rel="stylesheet" type="text/css"></link>
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
	
	<spring:url value="/resources/js-framework/fancy-box/jquery.fancybox.pack.js?ver=${app_js_ver}" var="jquery_fancybox"/>
	<spring:url value="/resources/js-framework/toastr/toastr.js?ver=${app_js_ver}" var="toastr_js"/>
	<spring:url value="/resources/js-framework/blockUI/jquery.blockUI.js?ver=${app_js_ver}" var="blockUI"/>
	<spring:url value="/resources/js-framework/noty/jquery.noty.js?ver=${app_js_ver}" var="noty"/>
	<spring:url value="/resources/js-framework/noty/layouts/center.js?ver=${app_js_ver}" var="noty_center"/>
	<spring:url value="/resources/js-framework/noty/themes/default.js?ver=${app_js_ver}" var="noty_default"/>
    <!-- Get the user local from the page context (it was set by Spring MVC's locale resolver) -->
    <c:set var="userLocale">
      <c:set var="plocale">${pageContext.response.locale}</c:set>
      <c:out default="en" value="${fn:replace(plocale, '_', '-')}"/>
    </c:set>
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
  	
  	 <script src="${noty}" type="text/javascript">
    <!-- required for FF3 and Opera -->
    </script>
    <script src="${noty_default}" type="text/javascript">
    <!-- required for FF3 and Opera -->
    </script>
    <script src="${noty_center}" type="text/javascript">
    <!-- required for FF3 and Opera -->
    </script>
    
	<spring:url
		value="js-framework/datatables/jquery.dataTables.min.js?ver=${app_js_ver}"
		var="jquery_dataTables_min_js" />
	<spring:url
		value="js-framework/populate/jquery.populate.pack.js?ver=${app_js_ver}"
		var="jquery_populate_pack_js" />
	<!-- DataTables CSS -->
	<!-- DataTables -->
	<script src="${jquery_dataTables_min_js}" type="text/javascript">
	        <!-- required for FF3 and Opera -->
	</script>
	
	<!-- Populate -->
	<script src="${jquery_populate_pack_js}" type="text/javascript">
	        <!-- required for FF3 and Opera -->
	</script>
	<script src="${jquery_fancybox}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${toastr_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
    <script src="${blockUI}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>
 <script type="text/javascript">
 
 $(document).ready(function() {
	 if($("#test").val() == -1 ){
	  $("#move").attr('disabled','disabled');
	 } else
	  {
	  $("#move").removeAttr("disabled");
	  }
	 $("#test").change(function() {
		
		 if($("#test").val() == -1 ){
			 
			  $("#move").attr('disabled','disabled');
			 } else
			  {
			  $("#move").removeAttr("disabled");
			  }
		 });
	  });
 

function cancel()
{
	
	
	parent.$.fancybox.close();
//parent.location.reload(true);
	}
 /* updated by Himanshu */ 	

 function moveChild(moveData)
 {
	 var number = $('#test :selected').val();
	 var newParent=  $('#test :selected').text();
	 var message= confirm("Do you really want to move? ");
	 if(message==true){
	
		 //alert(number);
		 
		 
	  	 $.ajax({
				async:false,
				type: "GET",
			    contentType: "application/json",
				url: "moveOrgTo?itemid="+moveData+"&newParentid="+number,
				 
				success: function( data ) {
				 
					//alert("successfully moved");
					// Added for task # 216 - Start
						/* alert("Please enter and save the data"); */
						var text="Successfully moved to"+ newParent;
						showAlert(text);
						// Added for task # 216 - End
						//isDuplicateProjectName = true;
					 
				}
			});  
	 }
	 else
		 {
		 
		// alert("you cancelled");
		// Added for task # 216 - Start
						/* alert("You cancelled"); */
						var text="You cancelled";
						showAlert(text);
						// Added for task # 216 - End
		 }
	 
 }
 
 
  
  
 </script>
 



 </head>

 
<body>
  <h2>Move  ${movename} From ${oldParent} To :</h2>

 
<div align="center">
<%-- <select id="my_select" >Move Organization
 <c:forEach var="tableColumn" items="${orglists}"> --%>
 <select  id="test" name="my_select" class="comboselect">
									<option  value="-1">SELECT</option>
									 <c:forEach var="tableColumn" items="${orgMap}">
										<option value="${tableColumn.key}">${tableColumn.value}</option>
									</c:forEach>
							</select> 
<%--  <option  value='${tableColumn.id}' id="test">${tableColumn.parentId.id}>${tableColumn.id}</option> --%>
 
  
<%--  </c:forEach>
 </select> --%>
</div>
<br>
<input type="submit" onclick="moveChild(${moveid})" id="move" value="Move" align="centre"/>
 <input type="submit" onclick="cancel()"    id="Cancel"  value="Cancel"  /> 

</body>
 
</html>