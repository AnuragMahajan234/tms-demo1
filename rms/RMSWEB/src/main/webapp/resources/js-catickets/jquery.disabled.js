$(document).on("change", "#reqCompleteFlagId", function(){
							var newVal = $(this).val();
							if(newVal == "No"){
								$("#requiredCompletedDateId").attr("disabled", "disabled").val('');
								$("#analysisCompletedDateId").attr("disabled", "disabled").val('');
								//$("#analysisCompleteDivId").attr("disabled", "disabled");
//								$("#solutionreViewDateId").attr("disabled", "disabled").val('');
//								$("#solutionReviewDivId").attr("disabled", "disabled");
								$("#solutiondevelopedDateId").attr("disabled", "disabled").val('');
								//$("#solutionDevelopedDivId").attr("disabled", "disabled");
								$("#solutionAcceptedDateId").attr("disabled", "disabled").val('');
								//$("#solutionAcceptedDivId").attr("disabled", "disabled");
								$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled").val('');
								
								document.getElementById("reqCompleteFlagId").disabled=false;
								document.getElementById("analysisCompleteFlagId").disabled=true;
								document.getElementById("solutionDevelopedFlagId").disabled=true;
								document.getElementById("solutionAcceptedFlagId").disabled=true;
								document.getElementById("customerApprovalFlagId").disabled=true;
								
								//$("#closePendingCustomerApprovalDivId").attr("disabled", "disabled");
								
								document.getElementById("analysisCompleteFlagId").value='No';
								document.getElementById("solutionDevelopedFlagId").value='No';
								document.getElementById("solutionAcceptedFlagId").value='No';
								document.getElementById("customerApprovalFlagId").value='No';
								
								$("#reqCompleteFlagId1").val('No');
								$("#analysisCompleteFlagId1").val('No');
								$("#solutionDevelopedFlagId1").val('No');
								$("#solutionAcceptedFlagId1").val('No');
								$("#customerApprovalFlagId1").val('No');
								
								
//								solutionReview.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
//								solutionReview.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
								
							}else if(newVal == "Yes"){
								$("#requiredCompletedDateId").removeAttr("disabled", "disabled");
								document.getElementById("analysisCompleteFlagId").disabled=false;
								$("#reqCompleteFlagId1").val('Yes');
							}else if(newVal == "N.A."){
								$("#requiredCompletedDateId").attr("disabled", "disabled").val('');
								document.getElementById("analysisCompleteFlagId").disabled=false;
								$("#reqCompleteFlagId1").val('N.A.');
							}
						});

$(document).on("change", "#analysisCompleteFlagId", function(){
	
	var newVal = $(this).val();
	//alert(newVal);
	var analysisComplete = $("#analysisCompleteFlagId").next("span");
	//var solutionReview = $("#solutionReviewFlagId").next("span");
	var solutionDeveloped = $("#solutionDevelopedFlagId").next("span");
	var solutionAccepted = $("#solutionAcceptedFlagId").next("span");
	var closedPending = $("#customerApprovalFlagId").next("span");
	if(newVal == "No"){
		$("#analysisCompletedDateId").attr("disabled", "disabled").val('');
		$("#solutiondevelopedDateId").attr("disabled", "disabled").val('');
		//$("#solutionDevelopedDivId").attr("disabled", "disabled");
//		$("#solutionreViewDateId").attr("disabled", "disabled").val('');
//		$("#solutionReviewDivId").attr("disabled", "disabled");
		$("#solutionAcceptedDateId").attr("disabled", "disabled").val('');
		//$("#solutionAcceptedDivId").attr("disabled", "disabled");
		$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled").val('');
		//$("#closePendingCustomerApprovalDivId").attr("disabled", "disabled");
		
		document.getElementById("solutionDevelopedFlagId").disabled=true;
		document.getElementById("solutionAcceptedFlagId").disabled=true;
		document.getElementById("customerApprovalFlagId").disabled=true;
		
		document.getElementById("solutionDevelopedFlagId").value='No';
		document.getElementById("solutionAcceptedFlagId").value='No';
		document.getElementById("customerApprovalFlagId").value='No';
		
		$("#analysisCompleteFlagId1").val('No');
		$("#solutionDevelopedFlagId1").val('No');
		$("#solutionAcceptedFlagId1").val('No');
		$("#customerApprovalFlagId1").val('No');
		
//		solutionReview.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
//		solutionReview.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		
	}else if(newVal == "Yes"){
		$("#analysisCompletedDateId").removeAttr("disabled", "disabled");
		document.getElementById("solutionDevelopedFlagId").disabled=false;
		$("#analysisCompleteFlagId1").val('Yes');
	}else if(newVal == "N.A."){
		$("#analysisCompletedDateId").attr("disabled", "disabled").val('');
		/*analysisComplete.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		analysisComplete.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");*/
		document.getElementById("solutionDevelopedFlagId").disabled=false;
		$("#analysisCompleteFlagId1").val('N.A.');
	}	
});

$(document).on("change", "#solutionDevelopedFlagId", function(){
	
	var newVal = $(this).val();
	//alert(newVal);
	var solutionDeveloped = $("#solutionDevelopedFlagId").next("span");
	var solutionReadyForReview = $("#solutionReadyForReviewId").next("span");
	var solutionAccepted = $("#solutionAcceptedFlagId").next("span");
	var closedPending = $("#customerApprovalFlagId").next("span");
	if(newVal == "No"){
		$("#solutiondevelopedDateId").attr("disabled", "disabled").val('');
//		$("#solutionreViewDateId").attr("disabled", "disabled").val('');
//		$("#solutionReviewDivId").attr("disabled", "disabled");
		$("#solutionAcceptedDateId").attr("disabled", "disabled").val('');
		//$("#solutionAcceptedDivId").attr("disabled", "disabled");
		$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled").val( );
		//$("#closePendingCustomerApprovalDivId").attr("disabled", "disabled");
		
		document.getElementById("solutionReadyForReviewId").disabled=true;
		document.getElementById("solutionAcceptedFlagId").disabled=true;
		document.getElementById("customerApprovalFlagId").disabled=true;
		
		document.getElementById("solutionReadyForReviewId").value='No';
		document.getElementById("solutionAcceptedFlagId").value='No';
		document.getElementById("customerApprovalFlagId").value='No';
		
		$("#solutionReadyForReviewId1").val('No');
		$("#solutionDevelopedFlagId1").val('No');
		$("#solutionAcceptedFlagId1").val('No');
		$("#customerApprovalFlagId1").val('No');
		
	}else if(newVal == "Yes"){
		$("#solutiondevelopedDateId").removeAttr("disabled", "disabled");

		document.getElementById("solutionReadyForReviewId").disabled=false;
		$("#solutionDevelopedFlagId1").val('Yes');
		
	}else if(newVal == "N.A."){
		$("#solutiondevelopedDateId").attr("disabled", "disabled").val('');

		document.getElementById("solutionReadyForReviewId").disabled=false;
		$("#solutionDevelopedFlagId1").val('N.A.');
	}
});

/*$(document).on("change", "#solutionReviewFlagId", function(){
	
	var newVal = $(this).val();
	//alert(newVal);
	var solutionDeveloped = $("#solutionDevelopedFlagId").next("span");
	var solutionReview = $("#solutionReviewFlagId").next("span");
	var solutionAccepted = $("#solutionAcceptedFlagId").next("span");
	var closedPending = $("#customerApprovalFlagId").next("span");
	if(newVal == "No"){
		$("#solutionreViewDateId").attr("disabled", "disabled").val('');
		$("#solutionAcceptedDateId").attr("disabled", "disabled").val('');
		$("#solutionAcceptedDivId").attr("disabled", "disabled");
		$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled").val('');
		$("#closePendingCustomerApprovalDivId").attr("disabled", "disabled");
		solutionAccepted.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		solutionAccepted.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		closedPending.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		closedPending.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		
	}else if(newVal == "Yes"){
		$("#solutionreViewDateId").removeAttr("disabled", "disabled");
		$("#solutiondevelopedDateId").removeAttr("disabled");
		$("#solutionAcceptedDateId").removeAttr("disabled");
		$("#closePendingCustomerApprovalDateId").removeAttr("disabled");
		solutionDeveloped.find("input, a").removeAttr("disabled").removeClass("disableInpDd");
		solutionDeveloped.find("div#ddIcon").remove();
		solutionAccepted.find("input, a").removeAttr("disabled").removeClass("disableInpDd");
		solutionAccepted.find("div#ddIcon").remove();
		closedPending.find("input, a").removeAttr("disabled").removeClass("disableInpDd");
		closedPending.find("div#ddIcon").remove();
	}else if(newVal == "N.A."){
		$("#solutionreViewDateId").attr("disabled", "disabled").val('');
		solutionReview.find("input, a").attr("disabled", "disabled").addClass("disableInpDd");
		solutionReview.append("<div id='ddIcon' style='width:30px; height:29px; position:absolute; right:-30px; top:0px; background:transparent'></div>");
		solutionAccepted.find("input, a").removeAttr("disabled").removeClass("disableInpDd");
		solutionAccepted.find("div#ddIcon").remove();
	}
});*/

$(document).on("change", "#solutionReadyForReviewId", function(){
	
	var newVal = $(this).val();
	//alert(newVal);
	/*var solutionDeveloped = $("#solutionDevelopedFlagId").next("span");*/
	//var solutionReview = $("#solutionReviewFlagId").next("span");
	var solutionAccepted = $("#solutionAcceptedFlagId").next("span");
	var closedPending = $("#customerApprovalFlagId").next("span");
	if(newVal == "No"){
//		$("#solutionreViewDateId").attr("disabled", "disabled").val('');
		$("#solutionAcceptedDateId").attr("disabled", "disabled").val('');
		//$("#solutionAcceptedDivId").attr("disabled", "disabled");
		$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled").val('');
		//$("#closePendingCustomerApprovalDivId").attr("disabled", "disabled");
		
		document.getElementById("solutionAcceptedFlagId").disabled=true;
		document.getElementById("customerApprovalFlagId").disabled=true;
		
		document.getElementById("solutionAcceptedFlagId").value='No';
		document.getElementById("customerApprovalFlagId").value='No';
		
		$("#solutionAcceptedFlagId1").val('No');
		$("#customerApprovalFlagId1").val('No');
		
		//$("#solutionReviewMainId").hide();
		
		
	}else if(newVal == "Yes"){
		if($("#solutionreViewDateId").val()!=null && $("#solutionreViewDateId").val()!=''){
			document.getElementById("solutionAcceptedFlagId").disabled=false;
		}
		//$("#solutionReviewMainId").show();
	}else if(newVal == "N.A."){
		
		if($("#solutionreViewDateId").val()!=null && $("#solutionreViewDateId").val()!=''){
			document.getElementById("solutionAcceptedFlagId").disabled=false;
		}
		//$("#solutionReviewMainId").hide();
	}
});

$(document).on("change", "#solutionAcceptedFlagId", function(){
	
	var newVal = $(this).val();
	//alert(newVal);
	var solutionAccepted = $("#solutionAcceptedFlagId").next("span");
	var closedPending = $("#customerApprovalFlagId").next("span");
	if(newVal == "No"){
		$("#solutionAcceptedDateId").attr("disabled", "disabled").val('');
		$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled").val('');
		//$("#closePendingCustomerApprovalDivId").attr("disabled", "disabled");
		
		document.getElementById("customerApprovalFlagId").disabled=true;
		document.getElementById("customerApprovalFlagId").value='No';
		
		$("#solutionAcceptedFlagId1").val('No');
		$("#customerApprovalFlagId1").val('No');
		
	}else if(newVal == "Yes"){
		$("#solutionAcceptedDateId").removeAttr("disabled", "disabled");
		/*$("#closePendingCustomerApprovalDateId").removeAttr("disabled");*/
		document.getElementById("customerApprovalFlagId").disabled=false;
		$("#solutionAcceptedFlagId1").val('Yes');
	}else if(newVal == "N.A."){
		$("#solutionAcceptedDateId").attr("disabled", "disabled").val('');

		document.getElementById("customerApprovalFlagId").disabled=false;
		$("#solutionAcceptedFlagId1").val('N.A.');
	}
});

$(document).on("change", "#customerApprovalFlagId", function(){
	
	var newVal = $(this).val();
	if(newVal == "No"){
		$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled").val('');
		$("#customerApprovalFlagId1").val('No');
		
	}else if(newVal == "Yes"){
		$("#closePendingCustomerApprovalDateId").removeAttr("disabled");
		$("#customerApprovalFlagId1").val('Yes');
	}else if(newVal == "N.A."){
		$("#closePendingCustomerApprovalDateId").attr("disabled", "disabled").val('');
		$("#customerApprovalFlagId1").val('N.A.');
	}
});

/*$(document).on("change", "#solutionReadyForReviewId", function(){
	
	var newVal = $(this).val();
	//alert(newVal);
	if(newVal == "Yes"){
		$( "#solutionReviewMainId" ).show();
	}else{
		$( "#solutionReviewMainId" ).hide();
	}
});*/

$(document).on("change", "#problemManagementId", function(){
	var newVal = $(this).val();
	//alert(newVal);
	if(newVal == "No"){
		$( "#problemManagementDivId" ).hide();
		
	}else{
		$( "#problemManagementDivId" ).show();
	}
});

$(document).on("change", "#t3ContributionId", function(){
	var newVal = $(this).val();
	//alert(newVal);
	if(newVal == "Yes"){
	
		$( "#t3ContributionDivId" ).show();
	}else{
		$( "#t3ContributionDivId" ).hide();
	}
});

$(document).on("change", "#solutionReviewId", function(){
	var newVal = $(this).val();
	//alert(newVal);
	if(newVal == "Yes"){
		$( "#solutionReviewDetaileDivId" ).show();
	}else{
		
		$( "#solutionReviewDetaileDivId" ).hide();
	}
});

$(document).on("change", "#defectLogId", function(){
	var newVal = $(this).val();
	//alert(newVal);
	if(newVal == "Yes"){
		$( "#defectLogDivId" ).show();
	}else{
		$( "#defectLogDivId" ).hide();
		
	}
});

$(document).on("change", "#cropId", function(){
	var newVal = $(this).val();
	//alert(newVal);
	if(newVal == "Yes"){
		$( "#cropDivId" ).show();
	}else{
		$( "#cropDivId" ).hide();
		
	}
});

$(document).on("change", "#reworkId", function(){
	var newVal = $(this).val();
	//alert(newVal);
	if(newVal == "Yes"){
		$( "#reworkDivId" ).show();
	}else{
		$( "#reworkDivId" ).hide();
		
	}
});


/*$(document).on("change", "#problemManagementId", function(){
	
	var newVal = $(this).val();
	//alert(newVal);
	if(newVal == "No"){
		$( "#problemManagementMainId" ).hide();
	}else{
		$( "#problemManagementMainId" ).show();
	}
});*/