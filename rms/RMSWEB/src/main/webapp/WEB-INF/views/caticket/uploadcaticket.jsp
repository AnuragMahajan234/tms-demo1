<!DOCTYPE HTML>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!--[if gte IE 8]>
<style>
  .fileinputBrowseButton input[type=file]{
     filter: alpha(opacity=0);
}
</style>
<![endif]-->

<script type="text/javascript">
	$(document).ready(function() {
		$("#fileBrowse").on("change", function() {
			var file = $('input[type="file"]#fileBrowse').val();
			//alert("fileName"+file);
			$('#BrowseInput').val(file);

			var fileValue = $('#BrowseInput').val();

			//alert(fileValue);

		});

		$('#BrowseInput').on("click", function() {
			$('input[type="file"]#fileBrowse').click();
		});
	});

	function fileUpload(fileName) {
		if (fileName.value != "") {
			var sizeofdoc = fileName.files[0].size;
			//alert(sizeofdoc + "bytes");
			if (!/(\.xlsx|\.xls)$/i.test(fileName.value) /* | sizeofdoc > 358400 */) {
				showError("Upload only xlsx or xls files");
				/* document.forms['newEmployee'].file.value = "";
				fileName.focus(); */
				return false;
			} else {
				return true;
			}
		} else {
			showError('Please select xlsx or xls file');
			return false;
		}
	}

	/*---------------------------user notification-------------------------------------*/
	function getUserNotification() {
		var emp_id = 1;
		var msg = "<ul>";
		$.getJSON("usernotifications", {
			find : "ByEmployeeIdAndIsReadNotUpdated"
		}, function(json) {
			var items = [];
			$.each(json, function(key, val) {
				msg = msg + "<li>" + val.msg + "</li>";
			});

			if (msg.length > 4) {
				msg = msg + "</ul>"
				//showError(msg);
				showSuccess(msg);
			}
		});
	}
	window.setInterval("getUserNotification()", 60000);
	/*--------------------------------end user notification-----------------------------------*/
</script>


<script type="text/javascript">
	//Upload form submission
	$(document)
			.ready(
					function() {
						$('#uploadFormId11')
								.submit(
										function(e) {
											startProgress();
											var frm = $('#uploadFormId');
											e.preventDefault();

											var fd = new FormData();
											fd.append("file", $("#fileBrowse")
													.val());
											$
													.ajax({
														url : '${pageContext.request.contextPath}/tickets/doUpload',
														type : 'POST',
														data : fd,
														contentType : false,
														processData : false,
														success : function(
																response) {
															stopProgress();
															showSuccess("Ticket has been Uploaded Successfully successfully!!");
														},
														error : function() {
															stopProgress();
														}
													});
											e.preventDefault();
										});
					});
</script>

<div class="mid_section">
	<div class="botMargin">
		<h1>Upload Tickets</h1>
	</div>
	<%-- <spring:hasBindErrors name="fileUploadBean">
        
        <h4  style="color: red;">Errors</h4>
         <ul>
            <c:forEach var="error" items="${errors.allErrors}">
                <li style="color: red;">${error.defaultMessage}</li>
            </c:forEach>
            </ul>
</spring:hasBindErrors> --%>
	<c:if test="${not empty errorList}">
		<h4 style="color: red;">Errors</h4>
		<ul>
			<c:forEach var="errorDetails" items="${errorList}">
				<li style="color: red;"><c:out value="${errorDetails}" /></li>
			</c:forEach>
		</ul>
	</c:if>
	<br>
	<form:form action="${pageContext.request.contextPath}/caticket/doUpload"
		enctype="multipart/form-data" method="post" id="uploadFormId"
		modelAttribute="fileUploadBean">
		<p>
			<span style="float: left; padding: 6px 6px 0 0;">Upload
				Ticket(s) :</span>
		<div class="BrowseUploadcontainer">
			<div>
				<input type="text" id="BrowseInput" name="resumeFileName"
					value="${fileName}" /> <span id="lblSize"></span>
			</div>
			<div class="browseBtnDiv">
				<span class="fileinputBrowseButton btnUploadBrowse btn"> <span
					class="BrowseText">Browse</span> <input id="fileBrowse"
					class="fileUploadCheck" type="file" name="file" multiple="multiple"
					style="height: 28px;">

				</span>
			</div>

		</div>

		<span style="display: inline-block; margin-left: 10px;"><input
			type="submit" value="Upload" onclick="return fileUpload(this.file)"></span>

		</p>
		<br>
	</form:form>

	<table>
		<sec:authorize access="hasAnyRole('ROLE_ADMIN')" />
		<spring:url value="/caticket/downloadTicketExcel" var="url" />
		<tr>
			<td>Upload Excel: <a href="${url}">Download</a></td>
		</tr>
	</table>
<br>
<h>Note:* Please take a note that all incorrect records will be displayed in Discrepency Records.</h>
	<div class="clear"></div>
</div>
