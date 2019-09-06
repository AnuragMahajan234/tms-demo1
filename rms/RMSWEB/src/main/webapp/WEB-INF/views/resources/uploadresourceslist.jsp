<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript">
	function fileUpload(fileName) {
	  if(fileName.value!="")
	 {	
		var sizeofdoc = fileName.files[0].size;
		//alert(sizeofdoc + "bytes");
		if (!/(\.xlsx|\.xls)$/i.test(fileName.value) | sizeofdoc > 358400) {
			//alert("Upload only xlsx or xls files");
			// Added for task # 216 - Start
			var text="Upload only xlsx or xls files";
			showAlert(text);
			// Added for task # 216 - End
			/* document.forms['newEmployee'].file.value = "";
			fileName.focus(); */
			return false;
		} else {
			return true;
		}
	 }else
		{
		   // alert('Please select xlsx or xls file');
		   // Added for task # 216 - Start
			var text="Please select xlsx or xls file";
			showAlert(text);
			// Added for task # 216 - End
		     return false;
		}
	}
	
	/*---------------------------user notification-------------------------------------*/	    
 	function getUserNotification(){
 		var emp_id = 1;
 		var msg = "<ul>";
 		$.getJSON("usernotifications", {find:"ByEmployeeIdAndIsReadNotUpdated"}, function(json){
 			  var items = [];
 			  $.each(json, function(key, val) {
 				 msg = msg+"<li>"+ val.msg + "</li>";
 			  });
 				
 			 if(msg.length>4){
 				msg = msg + "</ul>"
 				 //showError(msg);
 				showSuccess(msg);
 	 		}
 		});
 	}   
 	window.setInterval("getUserNotification()", 60000);
/*--------------------------------end user notification-----------------------------------*/
	
	
</script>
<div class="mid_section">
	<div class="botMargin">
		<h1>Resource</h1>
	</div>
<spring:hasBindErrors name="fileUploadBean">
        
        <h4  style="color: red;">Errors</h4>
         <ul>
            <c:forEach var="error" items="${errors.allErrors}">
                <li style="color: red;">${error.defaultMessage}</li>
            </c:forEach>
            </ul>
</spring:hasBindErrors>

<form action="resources/uploadExcel" onsubmit="return  fileUpload(this.file)"  enctype="multipart/form-data" method="post">
		<p>
			11233Upload Resource :<input type="file" id="file" name="file"><br>
			<br> <input type="submit" value="Upload" >
		</p>
</form>

<table><sec:authorize access="hasAnyRole('ROLE_ADMIN')"/>
<spring:url value="/resources/downloadResourceExcel" var="url"/>
<tr><td>Resource Excel: <a href="${url}">Download</a></td></tr>
</table>

<div class="clear"></div>
</div>
