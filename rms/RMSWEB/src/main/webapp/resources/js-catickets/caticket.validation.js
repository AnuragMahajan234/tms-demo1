function validateForm(isEditableMode, url) {
	// alert("Url: "+url);
	var error = "";
	var trueFalse = "";
	var caTicketNoID = document.forms["caTicketFormId"]["caTicketNoID"].value;
	if ((isNaN(caTicketNoID))) {
		error += "\u2022 Ticket No. must be a number! <br/>";
	} else if (caTicketNoID == null || caTicketNoID == "") {
		error += "\u2022 Ticket No. must be filled out! <br/>";
	}
	var description = document.forms["caTicketFormId"]["description"].value;
	if (description == null || description == "") {
		error += "\u2022 Description must be filled out! <br/>";
	}
	var priority = document.forms["caTicketFormId"]["priority"].value;
	if (priority == null || priority == "" || priority == -1) {
		// errorMsg=errorMsg+"\u2022 Employee Id should not be blank ! <br/>";
		error += "\u2022 Priority must be filled out! <br/>";
	}
	var moduleId = document.forms["caTicketFormId"]["moduleId.id"].value;
	if (moduleId == null || moduleId == "" || moduleId == -1) {
		error += "\u2022 Module must be filled out! <br/>";
	}
	var landscape = document.forms["caTicketFormId"]["landscape"].value;
	if (landscape == null || landscape == "" || landscape == -1) {
		error += "\u2022 Landscape must be filled out! <br/>";
	}
	var unit = document.forms["caTicketFormId"]["unit"].value;
	if (unit == null || unit == "" || unit == -1) {
		error += "\u2022 Unit must be filled out! <br/>";
	}
	var assigneeNameId = document.forms["caTicketFormId"]["assigneeNameId"].value;
	if (assigneeNameId == null || assigneeNameId == "" || assigneeNameId == -1) {
		error += "\u2022 AssigneeName must be filled out! <br/>";
	}
	var reviewer = document.forms["caTicketFormId"]["reviewer"].value;
	if (reviewer == null || reviewer == "" || reviewer == -1) {
		error += "\u2022 Reviewer must be filled out! <br/>";
	}
	var region = document.forms["caTicketFormId"]["regionId"].value;
	if (region == null || region == "" || region == -1) {
		error += "\u2022 Region must be filled out! <br/>";
	}
	var solutionReadyForReviewId = document.forms["caTicketFormId"]["solutionReadyForReviewId"].value;
	if (solutionReadyForReviewId == null || solutionReadyForReviewId == ""
			|| solutionReadyForReviewId == "-1") {
		error += "\u2022 Solution Ready For Review must be filled out! <br/>";
	}

	var reopenTicketFrequencyId = document.forms["caTicketFormId"]["reopenTicketFrequencyId"].value;
	var reasonForReopenId = document.forms["caTicketFormId"]["reasonForReopenId"].value;
	if (reopenTicketFrequencyId > 0) {
		if (reasonForReopenId == null || reasonForReopenId == ""
				|| reasonForReopenId == "-1") {
			error += "\u2022 Reason for reopen must be filled out! <br/>";
		}
	}

	var slaMissedId = document.forms["caTicketFormId"]["slaMissedId"].value;
	var reasonForSLAMissedId = document.forms["caTicketFormId"]["reasonForSLAMissedId"].value;
	if (slaMissedId == 'Yes') {
		if (reasonForSLAMissedId == null || reasonForSLAMissedId == ""
				|| reasonForSLAMissedId == "-1") {
			error += "\u2022 Reason for SLA Missed must be filled out! <br/>";
		}
	}

	var groupId = document.forms["caTicketFormId"]["groupId"].value;
	if (groupId == null || groupId == "" || groupId == "-1") {
		error += "\u2022 Group must be filled out! <br/>";
	}
	var creationDate = document.forms["caTicketFormId"]["creationDate"].value;
	if (creationDate == null || creationDate == "") {
		error += "\u2022 Creation Date must be filled out! <br/>";
	}
	/*
	 * if (isEditableMode == true) { var acknowledgedDate =
	 * document.forms["caTicketFormId"]["acknowledgedDate"].value; if
	 * (acknowledgedDate == null || acknowledgedDate == "") { error += "\u2022
	 * Acknowledged Date must be filled out! <br/>"; } }
	 */
	var reqCompleteFlagId = document.forms["caTicketFormId"]["reqCompleteFlagId"].value;
	if (reqCompleteFlagId == "Yes") {
		var requiredCompletedDateId = document.forms["caTicketFormId"]["requiredCompletedDateId"].value;
		if (requiredCompletedDateId == null || requiredCompletedDateId == "") {
			error += "\u2022 Required CompletedDate must be filled out! <br/>";
		}
	}
	var analysisCompleteFlagId = document.forms["caTicketFormId"]["analysisCompleteFlagId"].value;
	if (analysisCompleteFlagId == "Yes") {
		var analysisCompletedDateId = document.forms["caTicketFormId"]["analysisCompletedDateId"].value;
		if (analysisCompletedDateId == null || analysisCompletedDateId == "") {
			error += "\u2022 Analysis Completed Date must be filled out! <br/>";
		}
	}
	var solutionDevelopedFlagId = document.forms["caTicketFormId"]["solutionDevelopedFlagId"].value;
	var solutiondevelopedDateId = document.forms["caTicketFormId"]["solutiondevelopedDateId"].value;
	if (solutionDevelopedFlagId == "Yes") {

		if (solutiondevelopedDateId == null || solutiondevelopedDateId == "") {
			error += "\u2022 Solution Developed Date Completed Date must be filled out! <br/>";
		}
	}
	/*
	 * var solutionReviewFlagId =
	 * document.forms["caTicketFormId"]["solutionReviewFlagId"].value; if
	 * (solutionReviewFlagId == "Yes") { var solutionreViewDateId =
	 * document.forms["caTicketFormId"]["solutionreViewDateId"].value; if
	 * (solutionreViewDateId == null || solutionreViewDateId == "") { error +=
	 * "\u2022 Solution Review Date must be filled out! <br/>"; } }
	 */
	var solutionAcceptedFlagId = document.forms["caTicketFormId"]["solutionAcceptedFlagId"].value;
	if (solutionAcceptedFlagId == "Yes") {
		var solutionAcceptedDateId = document.forms["caTicketFormId"]["solutionAcceptedDateId"].value;
		if (solutionAcceptedDateId == null || solutionAcceptedDateId == "") {
			error += "\u2022 Solution Accepted Date must be filled out! <br/>";
		}
	}
	var customerApprovalFlagId = document.forms["caTicketFormId"]["customerApprovalFlagId"].value;
	if (customerApprovalFlagId == "Yes") {
		var closePendingCustomerApprovalDateId = document.forms["caTicketFormId"]["closePendingCustomerApprovalDateId"].value;
		if (closePendingCustomerApprovalDateId == null
				|| closePendingCustomerApprovalDateId == "") {
			error += "\u2022 Close Pending Customer Approval Date must be filled out! <br/>";
		}
	}
	var problemManagementId = document.forms["caTicketFormId"]["problemManagementId"].value;
	if (problemManagementId == "Yes" || problemManagementId == "N.A.") {
		var processId = document.forms["caTicketFormId"]["processId"].value;
		if (processId == null || processId == "" || processId == "-1") {
			error += "\u2022 Please select Process! <br/>";
		}
		var subProcess = document.forms["caTicketFormId"]["subProcess"].value;
		if (subProcess == null || subProcess == "" || subProcess == "-1") {
			error += "\u2022 Please select Subprocess! <br/>";
		}
		var rootCause = document.forms["caTicketFormId"]["rootCauseId"].value;
		if (rootCause == null || rootCause == "" || rootCause == "-1") {
			error += "\u2022 Please select Root cause! <br/>";
		}

		var ZREQNo = document.forms["caTicketFormId"]["ZREQNo"].value;

		if (isNaN(ZREQNo)) {
			error += "\u2022 ZREQNo must be a number! <br/>";
		} else if (ZREQNo == null || ZREQNo == "") {

			error += "\u2022 ZREQNo must be filled out! <br/>";
		}
		var parentTicketNo = document.forms["caTicketFormId"]["parentTicketNo"].value;
		if (isNaN(parentTicketNo)) {
			error += "\u2022 Parent Ticket must be a number! <br/>";
		} else if (parentTicketNo == null || parentTicketNo == "") {

			error += "\u2022 Parent Ticket No must be filled out! <br/>";
		}

		var comment = document.forms["caTicketFormId"]["comment"].value;
		if (comment == null || comment == "") {
			error += "\u2022 Comment must be filled out! <br/>";
		}
		if (document.getElementById("roleId").value == 'ROLE_ADMIN'
				|| document.getElementById("roleId").value == 'ROLE_DEL_MANAGER'
				|| document.getElementById("roleId").value == 'ROLE_BG_ADMIN'
				|| document.getElementById("roleId").value == 'ROLE_MANAGER'
				|| document.getElementById("isReviewerId").value == true) {
			var justifiedProblemManagement = document.forms["caTicketFormId"]["justifiedProblemManagement"].value;
			if (justifiedProblemManagement == null
					|| justifiedProblemManagement == ""
					|| justifiedProblemManagement == "-1") {
				error += "\u2022 Justified Problem Management must be filled out! <br/>";
			}
		}

	}

	if (solutionReadyForReviewId == "Yes" || solutionReadyForReviewId == "N.A.") {
		if (solutionDevelopedFlagId == "Yes" || solutionDevelopedFlagId == "No") {
			if (solutiondevelopedDateId == null
					|| solutiondevelopedDateId == "") {
				error += "\u2022 Solution Developed Date must be filled out.. If Solution is ready for review has been selected as Yes or N.A. ! <br/>";
			}
		}
	}

	if (isEditableMode) {
		
		// validating hours
		var priority = document.forms["caTicketFormId"]["priority"].value;
		    if(priority == "2 - High"){
		    	var requirementAnalysisHours = document.forms["caTicketFormId"]["requirementAnalysisHoursId"].value;
		    	var unitTestingHours = document.forms["caTicketFormId"]["unitTestingHoursId"].value;
				var numericReg = /^\d*[0-9](|.\d*[0-9]|,\d*[0-9])?$/;
				
				if (requirementAnalysisHours == null || requirementAnalysisHours == "") {
					error += "\u2022 Requirement Analysis Hours must be filled out! <br/>";
				}else if(!numericReg.test(requirementAnalysisHours)) {
			    	error += "\u2022 Requirement Analysis Hours should be Integer or Float only!!! <br/>";
			    }
				
				if (unitTestingHours == null || unitTestingHours == "") {
					error += "\u2022 Unit Testing Hours must be filled out! <br/>";
				}else if(!numericReg.test(unitTestingHours)) {
			    	error += "\u2022 Unit Testing Hours should be Integer or Float only!!! <br/>";
			    }
		    }
		
		var closePendingCustomerApprovalDateId = document.forms["caTicketFormId"]["closePendingCustomerApprovalDateId"].value;
		var customerApprovalFlagId = document.forms["caTicketFormId"]["customerApprovalFlagId"].value;
		var t3ContributionId = document.forms["caTicketFormId"]["t3ContributionId"].value;
		if (t3ContributionId == "Yes") {
			var contributionTrLength = $("#contributionTableBody tr").length;
			var contributionDataValue = $("#contributionTableBody tr").find(
					"td:first").html();
			if (contributionTrLength == 1
					&& contributionDataValue == 'No data available in table') {
				error += "\u2022 Please add atleast one record in T3 Contribution before saving Yes to T3 Contribution. Either select No or N.A. ! <br/>";
			}
		}
		if ((closePendingCustomerApprovalDateId != null
				|| closePendingCustomerApprovalDateId != "") && customerApprovalFlagId == 'Yes' ) {
			if (t3ContributionId == "No") {
				error += "\u2022 T3 Contribution should be filled Yes or N.A. before closing! <br/>";
			}
		}

		var solutionReviewId = document.forms["caTicketFormId"]["solutionReadyForReviewId"].value;
		if (document.getElementById("roleId").value == 'ROLE_ADMIN'
				|| document.getElementById("roleId").value == 'ROLE_DEL_MANAGER'
				|| document.getElementById("roleId").value == 'ROLE_BG_ADMIN'
				|| document.getElementById("roleId").value == 'ROLE_MANAGER'
				|| document.getElementById("isReviewerId").value == true) {
			/*if (solutionReviewId == "Yes") {
				var solutionReviewTableBodyLength = $("#solutionReviewTableBody tr").length;
				var solutionReviewDataValue = $("#solutionReviewTableBody tr")
						.find("td:first").html();
				if (solutionReviewTableBodyLength == 1
						&& solutionReviewDataValue == 'No data available in table') {
					error += "\u2022 Please add atleast one record in Solution Review before saving Yes to Solution Review. Either select No or N.A. ! <br/>";
				}
			}*/
			if ((closePendingCustomerApprovalDateId != null
					|| closePendingCustomerApprovalDateId != "") && customerApprovalFlagId == 'Yes') {
				if (solutionReviewId == "No") {
					error += "\u2022 Solution Review should be filled Yes or N.A. before closing! <br/>";
				}
			}
		}

		if (document.getElementById("roleId").value == 'ROLE_ADMIN'
				|| document.getElementById("roleId").value == 'ROLE_DEL_MANAGER'
				|| document.getElementById("roleId").value == 'ROLE_BG_ADMIN'
				|| document.getElementById("roleId").value == 'ROLE_MANAGER'
				|| document.getElementById("isReviewerId").value == true) {
			var defectLogId = document.forms["caTicketFormId"]["defectLogId"].value;
			if (defectLogId == "Yes") {
				var defectLogTableBody = $("#defectLogTableBody tr").length;
				var defectLogDataValue = $("#defectLogTableBody tr").find(
						"td:first").html();
				// alert("defectLogDataValue: " + defectLogDataValue);
				if (defectLogTableBody == 1
						&& defectLogDataValue == 'No data available in table') {
					error += "\u2022 Please add atleast one record in Defect Log before saving Yes to Defect Log. Either select No or N.A. ! <br/>";
				}
			}

			if ((closePendingCustomerApprovalDateId != null
					|| closePendingCustomerApprovalDateId != "") && customerApprovalFlagId == 'Yes') {
				if (defectLogId == "No") {
					error += "\u2022 Defect Log should be filled Yes or N.A. before closing! <br/>";
				}
			}

			var cropId = document.forms["caTicketFormId"]["cropId"].value;
			if (cropId == "Yes") {
				var cropTableBody = $("#cropTableBody tr").length;
				var cropDataValue = $("#cropTableBody tr").find("td:first")
						.html();
				if (cropTableBody == 1
						&& cropDataValue == 'No data available in table') {
					error += "\u2022 Please add atleast one record in Crop before saving Yes to Crop. Either select No or N.A. ! <br/>";
				}
			}

			if ((closePendingCustomerApprovalDateId != null
					|| closePendingCustomerApprovalDateId != "") && customerApprovalFlagId == 'Yes') {
				if (cropId == "No") {
					error += "\u2022 Crop should be filled Yes or N.A. before closing! <br/>";
				}
			}

		}
		var reworkId = document.forms["caTicketFormId"]["reworkId"].value;
		if (reworkId == "Yes") {
			var reworkTableBody = $("#reworkTableBody tr").length;
			var reworkDataValue = $("#reworkTableBody tr").find("td:first")
					.html();
			if (reworkTableBody == 1
					&& reworkDataValue == 'No data available in table') {
				error += "\u2022 Please add atleast one record in Rework before saving Yes to Rework. Either select No or N.A. ! <br/>";
			}
		}
		if ((closePendingCustomerApprovalDateId != null
				|| closePendingCustomerApprovalDateId != "") && customerApprovalFlagId == 'Yes') {
			if (reworkId == "No") {
				error += "\u2022 Rework should be filled Yes or N.A. before closing! <br/>";
			}
		}

		var problemManagementId = document.forms["caTicketFormId"]["problemManagementId"].value;
		if ((closePendingCustomerApprovalDateId != null
				|| closePendingCustomerApprovalDateId != "") && customerApprovalFlagId == 'Yes') {
			if (problemManagementId == "No") {
				error += "\u2022 Problem Management should be filled Yes or N.A. before closing! <br/>";
			}
		}

	}

	if (error.length == 0) {

		$("#solutionReadyForReviewId1").val(
				document.getElementById("solutionReadyForReviewId").value);
		$("#reqCompleteFlagId1").val(
				document.getElementById("reqCompleteFlagId").value);
		$("#analysisCompleteFlagId1").val(
				document.getElementById("analysisCompleteFlagId").value);
		$("#solutionDevelopedFlagId1").val(
				document.getElementById("solutionDevelopedFlagId").value);
		$("#solutionAcceptedFlagId1").val(
				document.getElementById("solutionAcceptedFlagId").value);
		$("#customerApprovalFlagId1").val(
				document.getElementById("customerApprovalFlagId").value);

		if (isEditableMode == false) {
			var frm = $('#caTicketFormId');
			$.ajax({
				type : 'POST',
				// url : frm.attr('action'),
				// url : '../caticket/isTicketAlreadyExist',
				url : url,
				data : "ticketNumber=" + caTicketNoID,
				success : function(data) {
					 
					if (data[0].status == 'true') {
						error += "\u2022 Ticket already exist\n";
						showError(error);
					} else {
						$("#caTicketFormId").submit();

					}
				},
				error : function(response) {
					error += "\u2022 Something happend wrong!! \n";
					showError(error);
				},
			});
		} else {
			return true;
		}
	} else {
		showError(error);
		return false;
	}

}
function validate(div) {
	var error = "";
	if (div == "SR") {
		var solutionReadyForReviewId = document.forms["caTicketFormId"]["solutionReadyForReviewId"].value;
		if (solutionReadyForReviewId == "Yes") {
			
			var reqCompleteFlagId = document.forms["caTicketFormId"]["reqCompleteFlagId"].value;
			var analysisCompleteFlagId = document.forms["caTicketFormId"]["analysisCompleteFlagId"].value;
			var solutionDevelopedFlagId = document.forms["caTicketFormId"]["solutionDevelopedFlagId"].value;
			var solutionAcceptedFlagId = document.forms["caTicketFormId"]["solutionAcceptedFlagId"].value;
			var customerApprovalFlagId = document.forms["caTicketFormId"]["customerApprovalFlagId"].value;
			
			var acknowledgedDateId = document.forms["caTicketFormId"]["acknowledgedDateId"].value;
			if (acknowledgedDateId == null || acknowledgedDateId == "") {
				error += "\u2022 Acknowledged Date must be filled out! <br/>";
			}
			
			if(reqCompleteFlagId != "N.A."){
				var requiredCompletedDateId = document.forms["caTicketFormId"]["requiredCompletedDateId"].value;
				if (requiredCompletedDateId == null
						|| requiredCompletedDateId == "") {
					error += "\u2022 Requirement Completed Date must be filled out or flag should be N.A.! <br/>";
				}
			}
			
			if(analysisCompleteFlagId != "N.A."){
				var analysisCompletedDateId = document.forms["caTicketFormId"]["analysisCompletedDateId"].value;
				if (analysisCompletedDateId == null
						|| analysisCompletedDateId == "") {
					error += "\u2022 Analysis Completed Date must be filled out or flag should be N.A.! <br/>";
				}
			}
			
			
			var solutiondevelopedDateId = document.forms["caTicketFormId"]["solutiondevelopedDateId"].value;
			//var solutionDevelopedFlagId = document.forms["caTicketFormId"]["solutionDevelopedFlagId"].value;
			
			if(solutionDevelopedFlagId != "N.A."){
				if ((solutiondevelopedDateId == null || solutiondevelopedDateId == "")/*
																						 * ||
																						 * solutionDevelopedFlagId ==
																						 * 'No'
																						 */) {
					error += "\u2022 Solution Developed Date must be filled out.. If Solution is ready for review. or flag should be N.A.! <br/>";
					// if (solutionDevelopedFlagId != 'N.A.') {
					// error += "\u2022 Solution Developed Date must be filled
					// out.. If Solution is ready for review. ! <br/>";
					//					}
				}
			}
			var solutionReviewTableBodyLength = $('#solutionReviewTable tbody tr').length
			for (var i = solutionReviewTableBodyLength - 1; i < solutionReviewTableBodyLength; i++) {
				var reViewDateIdSR0 = document.forms["caTicketFormId"]["reViewDateIdSR"
						+ i].value;
				if (reViewDateIdSR0 == null || reViewDateIdSR0 == "") {
					error += "\u2022 Solution Review Date must be filled out<br/>";
				}
				var issueUnderstandingSR = document.forms["caTicketFormId"]["issueUnderstandingSR"
						+ i].value;
				if (issueUnderstandingSR == null || issueUnderstandingSR == ""
						|| issueUnderstandingSR == "-1") {
					error += "\u2022 Please select issue understanding<br/>";
				}
				var alternateSolSR0 = document.forms["caTicketFormId"]["alternateSolSR"
						+ i].value;
				if (alternateSolSR0 == null || alternateSolSR0 == "") {
					error += "\u2022 Alternate Solution must be filled out<br/>";
				}
				var isSolAppropriateSR = document.forms["caTicketFormId"]["isSolAppropriateSR"
						+ i].value;
				if (isSolAppropriateSR == null || isSolAppropriateSR == ""
						|| isSolAppropriateSR == "-1") {
					error += "\u2022 Please select isAppropriate solution<br/>";
				}
				var rcaSR = document.forms["caTicketFormId"]["rcaSR" + i].value;
				if (rcaSR == null || rcaSR == "" || rcaSR == "-1") {
					error += "\u2022 Please select Agree With RCA<br/>";
				}
				var ratingSR = document.forms["caTicketFormId"]["ratingSR" + i].value;
				if (ratingSR == null || ratingSR == "" || ratingSR == "-1") {
					error += "\u2022 Please select Rating<br/>";
				}
				var commentsSR = document.forms["caTicketFormId"]["commentsSR"
						+ i].value;
				if (commentsSR == null || commentsSR == "") {
					error += "\u2022 Comments must be filled out<br/>";
				}
			}
			
			var cropId = document.forms["caTicketFormId"]["cropId"].value;
			var defectLogId = document.forms["caTicketFormId"]["defectLogId"].value;
			if (cropId == "Yes") {
				var cropTableBody = $("#cropTableBody tr").length;
				var cropDataValue = $("#cropTableBody tr").find("td:first")
						.html();
				if (cropTableBody == 1
						&& cropDataValue == 'No data available in table') {
					/*error += "\u2022 Please add atleast one record in Crop before saving Solution review!!! <br/>";*/
					error += "\u2022 Before saving, CROP should be either NA or Yes!!! <br/>";
				}
			}else if(cropId == "No"){
				error += "\u2022 Before saving, CROP should be either NA or Yes!!! <br/>";
			}
			
			if (defectLogId == "Yes") {
				var defectLogTableBody = $("#defectLogTableBody tr").length;
				var defectLogDataValue = $("#defectLogTableBody tr").find("td:first")
						.html();
				if (defectLogTableBody == 1
						&& defectLogDataValue == 'No data available in table') {
					error += "\u2022 Before saving, Defect Log should be either NA or Yes!!! <br/>";
				}
			}else if(defectLogId == "No"){
				error += "\u2022 Before saving, Defect Log should be either NA or Yes!!! <br/>";
			}
			
		}
	}
	if (div == "DL") {
		var defectLogId = document.forms["caTicketFormId"]["defectLogId"].value;
		if (defectLogId == "Yes") {
			var defectLogTableBody = $("#defectLogTableBody tr").length;
			if (defectLogTableBody > 0) {
				for (var i = 1; i < defectLogTableBody; i++) {
					var defectTypeId = document.forms["caTicketFormId"]["defectTypeId"
							+ i].value;
					if (defectTypeId == null || defectTypeId == ""
							|| defectTypeId == "-1") {
						error += "\u2022 Defect Type must be selected<br/>";
					}
					var defectDescriptionId = document.forms["caTicketFormId"]["defectDescriptionId"
							+ i].value;
					if (defectDescriptionId == null
							|| defectDescriptionId == "") {
						error += "\u2022 Please enter defect description<br/>";
					}
					var internalExternalId = document.forms["caTicketFormId"]["internalExternalId"
							+ i].value;
					if (internalExternalId == null || internalExternalId == ""
							|| internalExternalId == "-1") {
						error += "\u2022 Please select Internal-External<br/>";
					}
					var defectCategoryId = document.forms["caTicketFormId"]["defectCategoryId"
							+ i].value;
					if (defectCategoryId == null || defectCategoryId == ""
							|| defectCategoryId == "-1") {
						error += "\u2022 Please select Defect Catagory<br/>";
					}
					var sevirityId = document.forms["caTicketFormId"]["sevirityId"
							+ i].value;
					if (sevirityId == null || sevirityId == ""
							|| sevirityId == "-1") {
						error += "\u2022 Please select Severity<br/>";
					}
					var defectStatusId = document.forms["caTicketFormId"]["defectStatusId"
							+ i].value;
					if (defectStatusId == null || defectStatusId == ""
							|| defectStatusId == "-1") {
						error += "\u2022 Please select Defect Status<br/>";
					}
					var identifiedDateId = document.forms["caTicketFormId"]["identifiedDateId"
							+ i].value;
					if (identifiedDateId == null || identifiedDateId == "") {
						error += "\u2022 Identified Date must be filled out<br/>";
					}
					var identifiedPhaseId = document.forms["caTicketFormId"]["identifiedPhaseId"
							+ i].value;
					if (identifiedPhaseId == null || identifiedPhaseId == ""
							|| identifiedPhaseId == "-1") {
						error += "\u2022 Please select Identified phase<br/>";
					}
					var injectedPhaseId = document.forms["caTicketFormId"]["injectedPhaseId"
							+ i].value;
					if (injectedPhaseId == null || injectedPhaseId == ""
							|| injectedPhaseId == "-1") {
						error += "\u2022 Please select Injected Phase<br/>";
					}
					var workProductId = document.forms["caTicketFormId"]["workProductId"
							+ i].value;
					if (workProductId == null || workProductId == "") {
						error += "\u2022 Please select Work Product Name<br/>";
					}
					var reopenedId = document.forms["caTicketFormId"]["reopenedId"
							+ i].value;
					if (reopenedId == null || reopenedId == ""
							|| reopenedId == "-1") {
						error += "\u2022 Please select Respond<br/>";
					}
					var defectRootCauseId = document.forms["caTicketFormId"]["defectRootCauseId"
							+ i].value;
					if (defectRootCauseId == null || defectRootCauseId == "") {
						error += "\u2022 Defect Root Cause must be filled out<br/>";
					}
					var categoryRootCauseId = document.forms["caTicketFormId"]["categoryRootCauseId"
							+ i].value;
					if (categoryRootCauseId == null
							|| categoryRootCauseId == ""
							|| categoryRootCauseId == "-1") {
						error += "\u2022 Please select Root Cause Catagory<br/>";
					}
					var resolveByNameId = document.forms["caTicketFormId"]["resolvedById"
							+ i].value;
					if (resolveByNameId == null || resolveByNameId == "") {
						error += "\u2022 Resolved By must be filled out<br/>";
					}
					var resolvedDateId = document.forms["caTicketFormId"]["resolvedDateId"
							+ i].value;
					if (resolvedDateId == null || resolvedDateId == "") {
						error += "\u2022 Resolved Date must be filled out<br/>";
					}
					var closedDateId = document.forms["caTicketFormId"]["closedDateId"
							+ i].value;
					if (closedDateId == null || closedDateId == "") {
						error += "\u2022 Closed Date must be filled out<br/>";
					}
				}
			}
		}
	}
	if (div == "CROP") {

		var cropId = document.forms["caTicketFormId"]["cropId"].value;
		if (cropId == "Yes") {
			var cropTableBody = $("#cropTableBody tr").length;
			if (cropTableBody > 0) {
				for (var i = 1; i < cropTableBody; i++) {
					var cropTitle = document.forms["caTicketFormId"]["cropTitle"
							+ i].value;
					if (cropTitle == null || cropTitle == "") {
						error += "\u2022 Crop Title must be filled out<br/>";
					}
					var cropDesc = document.forms["caTicketFormId"]["cropDesc"
							+ i].value;
					if (cropDesc == null || cropDesc == "") {
						error += "\u2022 Crop Description must be filled out<br/>";
					}
					var sourceId = document.forms["caTicketFormId"]["sourceId"
							+ i].value;
					if (sourceId == null || sourceId == "") {
						error += "\u2022 Please select Source<br/>";
					}
					var benefitTypeId = document.forms["caTicketFormId"]["benefitTypeId"
							+ i].value;
					if (benefitTypeId == null || benefitTypeId == ""
							|| benefitTypeId == "-1") {
						error += "\u2022 Please select Benifit Type<br/>";
					}
					var totalBusinessHrsId = document.forms["caTicketFormId"]["totalBusinessHrsId"
							+ i].value;
					if (isNaN(totalBusinessHrsId)) {
						error += "\u2022 Total Business Hours must be a number<br/>";
					} else {

						if (totalBusinessHrsId < 0) {
							error += "\u2022 Total Business Hours must be filled out<br/>";
						}
					}
					var totalITHrsId = document.forms["caTicketFormId"]["totalITHrsId"
							+ i].value;
					if (isNaN(totalITHrsId)) {
						error += "\u2022 Total IT Hours must be a number<br/>";
					} else {
						if (totalITHrsId < 0) {
							error += "\u2022 Total IT Hours must be filled out<br/>";
						}
					}
					var savingUSD = document.forms["caTicketFormId"]["savingUSD"
							+ i].value;
					if (savingUSD == null || savingUSD == "") {
						error += "\u2022 Saving must be filled out<br/>";
					}
					var justifiedCropId = document.forms["caTicketFormId"]["justifiedCropId"
							+ i].value;
					if (justifiedCropId == null || justifiedCropId == ""
							|| justifiedCropId == "-1") {
						error += "\u2022 Please select Justifid<br/>";
					}
				}
			}
		}
	}
	if (div == "RW") {
		var reworkId = document.forms["caTicketFormId"]["reworkId"].value;
		if (reworkId == "Yes") {
			var reworkTableBody = $("#reworkTableBody tr").length;
			if (reworkTableBody > 0) {
				for (var i = 1; i < reworkTableBody; i++) {
					var reworkTypeId = document.forms["caTicketFormId"]["reworkTypeId"
							+ i].value;
					if (reworkTypeId == null || reworkTypeId == ""
							|| reworkTypeId == "-1") {
						error += "\u2022 Please select Rework Type<br/>";
					}
					var startDateRW = document.forms["caTicketFormId"]["startDateRW"
							+ i].value;
					if (startDateRW == null || startDateRW == "") {
						error += "\u2022 Start Date Timestamp must be filled out<br/>";
					}
					var endDateRW = document.forms["caTicketFormId"]["endDateRW"
							+ i].value;
					if (endDateRW == null || endDateRW == "") {
						error += "\u2022 End Date Timestamp must be filled out<br/>";
					}
					var justifiedIdRW = document.forms["caTicketFormId"]["justifiedIdRW"
							+ i].value;
					if (justifiedIdRW == null || justifiedIdRW == ""
							|| justifiedIdRW == "-1") {
						error += "\u2022 Please select Justified<br/>";
					}
				}

			}
		}
	}
	if (div == "T3") {
		var t3ContributionId = document.forms["caTicketFormId"]["t3ContributionId"].value;
		if (t3ContributionId == "Yes") {
			var contributionTrLength = $("#contributionTableBody tr").length;
			if (contributionTrLength > 0) {
				for (var i = 1; i < contributionTrLength; i++) {
					var dateContId = document.forms["caTicketFormId"]["dateContId"
							+ i].value;
					if (dateContId == null || dateContId == "") {
						error += "\u2022 DateContacted must be filled out<br/>";
					}
					var description = document.forms["caTicketFormId"]["description"
							+ i].value;
					if (description == null || description == "") {
						error += "\u2022 T3contribution Description must be filled out<br/>";
					}
					var reasonHelpId = document.forms["caTicketFormId"]["reasonHelpId"
							+ i].value;
					if (reasonHelpId == null || reasonHelpId == "") {
						error += "\u2022 T3contribution Reason For Help must be filled out<br/>";
					}
					var hrTakenId = document.forms["caTicketFormId"]["hrTakenId"
							+ i].value;
					if (isNaN(hrTakenId)) {
						error += "\u2022 T3contribution Hours Taken must be a number<br/>";
					} else {
						if (hrTakenId == null || hrTakenId == "") {
							error += "\u2022 T3contribution Hours Taken must be filled out<br/>";
						}
					}
					var justifiedId = document.forms["caTicketFormId"]["justifiedId"
							+ i].value;
					if (justifiedId == null || justifiedId == "") {
						error += "\u2022 T3contribution Justified must be filled out<br/>";
					}
				}
			}
		}
	}
	if (error.length == 0) {
		return true;
	} else {
		showError(error);
		return false;
	}

}
