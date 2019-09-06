<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<spring:url value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}" 	var="ColReorder_js" />
<spring:url value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}" 	var="ColVis_js" />
<spring:url value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}" var="multiselect_js" />
<script src="${multiselect_js}" type="text/javascript"></script>
<spring:url value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}" 	var="multiselect_filter_js" />

<spring:url value="/resources/styles/style.css?ver=${app_js_ver}" var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<spring:url value="/resources/images/spinner.gif" var="spinner" />
<script src="${multiselect_filter_js}" type="text/javascript"></script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script type="text/javascript">

$( window ).on( "load", function() { 
	$("#processStatusId").hide();	
});

$(document).ready(function() {
	function startProcess(){
		var username=document.getElementById("userid").value;
		var password=document.getElementById("password").value;
		var data ={"password":password,"username":username};
		$.ajax({
			type: 'POST',
			dataType: 'json',
			contentType: "application/json",
	        url: '/rms/process/synch/pdl',
	        data:JSON.stringify(data),
	        cache: false,
	        success: function(response) {
	        		if(response.status=='success'){
	        			showSuccess("PDL Group synched successfully!");	
	        		}else if(response.status='unauthenticate'){
	        			showError("Please check your credential!");
	        		}else{
	        			showError("Something went wrong in PDL synch!");	
	        		}
	        		$('input[type="button"]').prop('disabled', false);
	        		$("#processStatusId").hide();
		     	},
		     	error: function(error){
		     		$('input[type="button"]').prop('disabled', false);
		     		$("#processStatusId").hide();	
		     		showError("Something went wrong!!");
		     	}
			});
		};

	$(document).on('click','#submit',function() {
		var pass = document.getElementById("password").value;
		if(pass ==='' || pass.trim().length <=0){
			showError("Please enter password!");
			return false;
		}
		$('input[type="button"]').prop('disabled', true);
		$("#processStatusId").show();
		startProcess();
	});
});

</script>
<body>
	<div class="content-wrapper">
		<div class="botMargin">
			<h1>Processes</h1>
			<div class="form">
					<div  class="tblBorderDiv">
						<table id="mail" cellpadding="5" cellspacing="5" class="display dataTable" style="border-collapse:collapse;width:100%; margin-top:0px;">
							<tr>
							<td align="right" width="50%"><strong>Process <span style="color: red;">*</span> : </strong></td>
							<td align="left" width="50%">
								 <div  class="positionRel">
									<select id="processId" class="required">
										<option value="pdlSynch">PDL Synch</option>										
									</select> 
			 					</div>
		 					</td>
				 		   </tr>
				 		   <tr>
							<td align="right" width="50%"><strong>Yash Password <span style="color: red;">*</span> : </strong></td>
							<td align="left" width="50%">
								 <div  class="positionRel">
									 <input type="password" name="password" id="password"/>
									 <input type="hidden" value="${username}" name="userid" id="userid">
			 					</div>
		 					</td>
				 		   </tr>
				 		   <tr>
				 		   	<td align="center" width="100%" colspan="2">
				 		   		<input type="button" class="request-form-style" style="font-size: medium;" id="submit" value="Start Process"/>
				 		   	</td>
				 		   </tr>
				 		    <tr>
				 		   	<td align="center" width="100%" colspan="2" >
				 		   		<div id="processStatusId">PDL Synch Process Running....</div>	
				 		   	</td>
				 		   </tr>
						</table>
					</div>	
			</div>
		</div>
	</div>	
</body>
</html>