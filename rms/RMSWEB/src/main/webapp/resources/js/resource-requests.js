var triggerEmailFlag = true;
$('.date').datetimepicker({
	//language:  'fr',
	weekStart: 1,
	todayBtn: 1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	forceParse: 0,
	showMeridian: 1
});


function showSuccessAlert(message) {
	$('.alertWrapper>.alert-success').text(message);
	$('.alertWrapper.success').show().delay(5000).fadeOut('slow');
}

function showErrorAlert(message) {
	$('.alertWrapper .alert-danger').append('<p>' + message + '</p>');
	$('.alertWrapper.danger').show();
}

function discardCopy(){
	var reqid = document.getElementById('requisitionDbId').value;
	
	$.ajax({
		type : 'POST',
		url: 'requestsReports/hardDeleteSkillRequest?skillRequestId='+reqid+'&copyFlag=copyRRFTrue',
    	contentType: "application/json",
    	async:false,
    	dataType: "text",
    	
		success: function (data) {
			stopProgress();
			showSuccess("Copy is discarded");
			window.location.href  = "requestsReports/positionDashboard";
		},
		error: function (error) {
			stopProgress();
			showError("Something happend wrong!!");
			//window.location.reload();
		}
	});	
	}	
function dateCheck(data) {

	var requestDate = new Date($('#requestedDate1').val());
	var resourceRequiredDate = new Date($('#resourceRequiredDate').val());
	if (requestDate > resourceRequiredDate) {
		showErrorAlert("Resource Required date cannot be less than resource requested Date!");
		$('#resourceRequiredDate').val('');
	}
}

$(document).on('click', '.closemeAlert', function () {
	$('.alert-danger').empty();
	$('.alertWrapper').fadeOut("slow")
});

function showSuccess(msg) {
	toastr.success(msg, "Success")
}

function showMessage(msg) {
	toastr.options.timeOut = 12000;
	toastr.error(msg, " ")
}

function showError(msg) {
	toastr.options.timeOut = 12000;
	toastr.error(msg, "Error")
}

function showWarning(msg) {
	toastr.warning(msg, "Warning")
}

$(document).ready(function () {
	//Code for single user list - start
	
	
	$("#requestorSelect").select2({
		openOnEnter: false,
		ajax: {
			url: "/rms/requests/activeUserList",
			dataType: 'json',
			data: function (params) { 
				return {
					userInput: params.term || '',
				};
			},
			processResults: function (data, params) {
				return {
					results: formatData(JSON.parse(data.activeUserList)),
				};
			},
		},	
		minimumInputLength: 3,
		allowClear: true,
		createTag: function(params) {
            return undefined;
	   },
	   placeholder: 'Search Recipient'
	});
	
	function formatData(userList) {
		$.map(userList, function(item, idx) {
			item.id = item.employeeId;
		 	item.text = item.firstName + " " + item.lastName + "(" + item.yashEmpId + ")";
		});

		return userList;
	}
	
	$("#deliverPOCSelect").select2({
		openOnEnter: false,
		ajax: {
			url: "/rms/requests/activeUserList",
			dataType: 'json',
			data: function (params) { 
				return {
					userInput: params.term || '',
				};
			},
			processResults: function (data, params) {
				return {
					results: formatData(JSON.parse(data.activeUserList)),
				};
			},
		},	
		minimumInputLength: 3,
		allowClear: true,
		createTag: function(params) {
            return undefined;
	   },
	   placeholder: 'Search Recipient(s)'
	});
	
	$("#RMGPocSelect").select2({
		tags : true,
		openOnEnter: false,
		ajax: {
			url: "/rms/requests/activeUserList",
			dataType: 'json',
			data: function (params) { 
				return {
					userInput: params.term || '',
				};
			},
			processResults: function (data, params) {
				return {
					results: formatData(JSON.parse(data.activeUserList)),
				};
			},
		},	
		minimumInputLength: 3,
		allowClear: true,
		createTag: function(params) {
            return undefined;
	   },
	   placeholder: 'Search Recipient(s)'
	});
	
	$("#TecTeamSelect").select2({
		tags : true,
		openOnEnter: false,
		ajax: {
			url: "/rms/requests/activeUserList",
			dataType: 'json',
			data: function (params) { 
				return {
					userInput: params.term || '',
				};
			},
			processResults: function (data, params) {
				return {
					results: formatData(JSON.parse(data.activeUserList)),
				};
			},
		},	
		minimumInputLength: 3,
		allowClear: true,
		createTag: function(params) {
            return undefined;
	   },
	   placeholder: 'Search Recipient(s)'
	});
	
	
	function formatRoundSelect(userList) {
		$.map(userList, function(item, idx) {
			item.id = item.employeeId;
		 	item.text = item.firstName + " " + item.lastName;
		});

		return userList;
	}
	
	$("#resourceForReplacement").select2({
		ajax: {
			url: "/rms/requests/activeUserList",
			dataType: 'json',
			data: function (params) {
				return {
					userInput: params.term || '',
				};
			},
			processResults: function (data, params) {
				return {
					results: formatRoundSelect(JSON.parse(data.activeUserList)),
				};
			},
		},	
		minimumInputLength: 3,
		allowClear: true,
		createTag: function(params) {
            return undefined;
	   },
	   placeholder: 'Search Recipient'
	});
	
	
	$("#round1Select").select2({
		tags : true,
		openOnEnter: false,
		ajax: {
			url: "/rms/requests/activeUserList",
			dataType: 'json',
			data: function (params) { 
				return {
					userInput: params.term || '',
				};
			},
			processResults: function (data, params) {
				return {
					results: formatRoundSelect(JSON.parse(data.activeUserList)),
				};
			},
		},	
		minimumInputLength: 3,
		allowClear: true,
		createTag: function(params) {
            return undefined;
	   },
	   placeholder: 'Search Recipient(s)'
	});
	
	
	$("#round2Select").select2({
		tags : true,
		openOnEnter: false,
		ajax: {
			url: "/rms/requests/activeUserList",
			dataType: 'json',
			data: function (params) { 
				return {
					userInput: params.term || '',
				};
			},
			processResults: function (data, params) {
				return {
					results: formatRoundSelect(JSON.parse(data.activeUserList)),
				};
			},
		},	
		minimumInputLength: 3,
		allowClear: true,
		createTag: function(params) {
            return undefined;
	   },
	   placeholder: 'Search Recipient(s)'
	});
	
		// for mailTO
	
	$("#sendMailTo").select2({
		tags : true,
		openOnEnter: false,
		ajax: {
			url: "/rms/requests/activeUserList",
			dataType: 'json',
			data: function (params) { 
				return {
					userInput: params.term || '',
				};
			},
			processResults: function (data, params) {
				return {
					results: formatRoundSelect(JSON.parse(data.activeUserList)),
				};
			},
		},	
		minimumInputLength: 3,
		allowClear: true,
		createTag: function(params) {
            return undefined;
	   },
	   placeholder: 'Search Recipient(s)'
	});
	
	//For notifyTo
	$("#notifyToIds").select2({
		tags : true,
		openOnEnter: false,
		ajax: {
			url: "/rms/requests/activeUserList",
			dataType: 'json',
			data: function (params) {
				return {
					userInput: params.term || '',
				};
			},
			processResults: function (data, params) {
				return {
					results: formatRoundSelect(JSON.parse(data.activeUserList)),
				};
			},
		},	
		minimumInputLength: 3,
		allowClear: true,
		createTag: function(params) {
            return undefined;
	   },
	   placeholder: 'Search Recipient(s)'
	});
	

	
	//Code for single user list - end
	
	
	// $(".comboselect").combobox();
	$('#reasonForReplacement').val('');
	$('#resourceForReplacement').val('');
	/*$('#requestorSelect option[value=0]').attr('selected','selected');
	$('#requestorSelect option[value!=0]').removeAttr('selected');
	var s_sel=$('#clientInterviewRequiredSelect option[value="N"]').text();
	$('#clientInterviewRequiredSelect').closest('.positionRel').find('.ui-combobox-input').val(s_sel); */

	var daysToAdd = 1;
	if ($('#buttonFlagId').val() == false) {
		//console.log($('#copyRRFData').val());
		$("#requestedDate1").datepicker().datepicker("setDate", new Date());
		$("#requestedDate1").datepicker("option", "minDate", new Date());
		$("#requestedDate1").datepicker("option", "maxDate", new Date())
	}

	/* $("#requestedDate1").datepicker({
		
	onSelect: function (selected) {
	var dtMax = new Date(selected);
	dtMax.setDate(dtMax.getDate() + daysToAdd); 
	var dd = dtMax.getDate();
	var mm = dtMax.getMonth() + 1;
	var y = dtMax.getFullYear();
	var dtFormatted = mm + '/'+ dd + '/'+ y;
	$("#resourceRequiredDate").datepicker("option", "minDate", dtFormatted);
	}
	}); */

	/* $("#resourceRequiredDate").datepicker({
	onSelect: function (selected) {
	var dtMax = new Date(selected);
	dtMax.setDate(dtMax.getDate() - daysToAdd); 
	var dd = dtMax.getDate();
	var mm = dtMax.getMonth() + 1;
	var y = dtMax.getFullYear();
	var dtFormatted = mm + '/'+ dd + '/'+ y;
	$("#requestedDate1").datepicker("option", "maxDate", dtFormatted)
	}
	}); */

	// $('[data-toggle="popover"]').popover();   
	// $('#requestedDate').val(new Date()) jab wo bole for change
	var validation = 0;
	var requirements = {
		parameters: []
	};

	var requestorsBGBU = '';
	selectedRequestorID = $('#requestorSelect').val();
	$(document).on('change', '#requestorSelect', function () {
		
		selectedRequestorID = $('#requestorSelect').val(); //requestor emp ID
		//requirements.parameters.push({"requestorID" : selectedRequestorID});
		 
		if (selectedRequestorID == "select" || selectedRequestorID ==null) {
			showErrorAlert("Please select a Requestor!");
		} else {
			startProgress();
			$.ajax({
				type: 'GET',
				dataType: 'text',
				url: '/rms/requests/getEmployeeDetailsById/' +
					selectedRequestorID,
				contentType: "application/json; charset=utf-8",
				cache: false,
				success: function (data) {
					stopProgress();
					var parsedData = JSON
						.parse(data);
					$('#requestorGradeSelect').val(parsedData.grade);
					$('#requestorDesignationSelect').val(parsedData.designationName);

				},
				error: function (error) {
					stopProgress();
					showError("Something happend wrong!!");
					//window.location.reload();
				}
			});

		}

	});

	customerGroupName = "";

	$(document).on('change', '#clientNameSelect', function () {
		var clientID = $('#clientNameSelect').val(); //client ID
		clientName = $('#clientNameSelect option:selected').text();
		customerGroupName = "";
		$('#clientGroupSelect').closest('.form-group').find('.ui-combobox-input').val('');
		//$('#clientGroupSelect').closest('.form-group').removeClass('validateMe');
		//$('#clientGroupSelect').empty();
		$('#projectNameSelect').closest('.form-group').find('.ui-combobox-input').val('');
		/* $('#projectNameSelect').val(''); */
		// requirements.parameters.push({"clientId" : clientID});
		if (clientID == "select" || clientID == 0) {
			showErrorAlert("Please Select a Client!");

		} else {
			$('#clientGroupSelect').text('');
			startProgress();
			$.ajax({
				type: 'GET',
				dataType: 'text',
				url: '/rms/requests/getGroupBasedOnCustomerId/' +
					clientID,
				contentType: "application/json; charset=utf-8",
				cache: false,
				success: function (data) {
					stopProgress();
					var optionForClientGroup = "";
					$('#clientGroupSelect').empty();
					var response = JSON.parse(data)
					if (!response.length) {
						$('#clientGroupSelect').closest('.form-group').removeClass('validateMe');
						$("#grpdiv").css('display', 'none');
					} else {
						$('#clientGroupSelect').closest('.form-group').addClass('validateMe');
						$("#grpdiv").css('display', 'block');
					}
					for (i = 0; i < response.length; i++) {
						var index = response[i]; 
						optionForClientGroup = "<option value= '" + index[0] + "'>" +
							index[2] +
							"</option>"
						$(
								"#clientGroupSelect")
							.append(
								optionForClientGroup);
					}

					$.ajax({
						type: 'GET',
						dataType: 'text',
						url: '/rms/requests/getProjects/' +
							clientID,
						contentType: "application/json; charset=utf-8",
						cache: false,
						success: function (
							data) {
							stopProgress();
							var optionForProjects = "";
							$('#projectNameSelect').empty();
							response = JSON.parse(data);
							for (i = 0; i < response.length; i++) {
								var index = response[i];
								optionForProjects = "<option value= '" + index[2] + "'>" +
									index[10] +
									"</option>"
								$("#projectNameSelect").append(optionForProjects);
							}

						},
						error: function (
							error) {
							stopProgress();
							showError("Something happend wrong!!");
							//window.location.reload();
						}
					});

				},
				error: function (
					error) {
					stopProgress();
					showError("Something happend wrong!!");
					//window.location.reload();
				}
			});
		}

	});

	$('#clientGroupSelect').on('change', function () {
		customerGroupName = $('#clientGroupSelect option:selected').text()
	});

	$('#projectNameSelect')
		.on(
			'change',
			function () {

				projectName = $(
						'#projectNameSelect option:selected')
					.text();
				projectID = $('#projectNameSelect')
					.val()
				$
					.ajax({
						type: 'GET',
						dataType: 'text',
						url: '/rms/requests/getBUHByProjectId/' +
							projectID,
						contentType: "application/json; charset=utf-8",
						cache: false,
						success: function (data) {
							stopProgress();
							var parsedData = JSON
								.parse(data); 
							if (parsedData.firstName != null) {
								dataForBUH = parsedData.firstName +
									" " +
									parsedData.lastName;
								$(
										'#BUHNameSelect')
									.val(
										dataForBUH);
								requirements.parameters
									.push({
										"BUHName": parsedData.employeeId
									});
							}

							$
								.ajax({
									type: 'GET',
									dataType: 'text',
									url: '/rms/requests/getBGHByProjectId/' +
										projectID,
									contentType: "application/json; charset=utf-8",
									cache: false,
									success: function (
										data) {
										stopProgress();
										var parsedData = JSON
											.parse(data); 
										if (parsedData.firstName != null) {
											dataForBUH = parsedData.firstName +
												" " +
												parsedData.lastName;
											$(
													'#BGHNameSelect')
												.val(
													dataForBUH);
											requirements.parameters
												.push({
													"BGHName": parsedData.employeeId
												});
										}

									},
									error: function (
										error) {
										stopProgress();
										showError("Something happend wrong!!");
										//window.location.reload();
									}
								});

						},
						error: function (error) {
							stopProgress();
							showError("Something happend wrong!!");
							//window.location.reload();
						}
					});

			})

	buhArray = [];
	var arr1 = [];
	tempExterArr1 = [];
	$('#buhApprovalSelect')
		.on(
			'change',
			function () {
				buhFile = $('#buhApprovalSelect')
					.val();

				var file = $('#buhApprovalSelect')
					.prop('files')[0];
				$('#buhApprovalSelect').prev(
						'.inputfile')
					.val(file.name);
				var ext = file.name.split('.')
					.pop();
				var sizeofdoc = file.size;

				if (sizeofdoc < 5000000) {

					$("#buhApprovalSelect").css(
						"border",
						"solid 1px #d5d5d5");
					tempExterArr1.push(file);
					var popval = tempExterArr1
						.pop();
					var start_index = 0
					var number_of_elements_to_remove = 1;
					var removed_elements = buhArray
						.splice(
							start_index,
							number_of_elements_to_remove,
							popval);
					arr1.push(buhArray.pop());

				} else {
					alert("Upload only doc or pdf files with size less than 5MB");
					input = $('#buhApprovalSelect');
					input.val('').clone(true);
					$('#buhFileName').val('');
				}
				requirements.parameters.push({
					"buhFile": file
				});
			})

	bghFileArray = [];
	var bghArr = [];
	tempArr = [];

	$('#bghApprovalSelect')
		.on(
			'change',
			function () {
				bghFile = $('#bghApprovalSelect')
					.val();
				var file = $('#bghApprovalSelect')
					.prop('files')[0];
				$('#bghApprovalSelect').prev(
						'.inputfile')
					.val(file.name);
				var ext = file.name.split('.')
					.pop();
				var sizeofdoc = file.size;
				if (sizeofdoc < 5000000) {

					$("#bghApprovalSelect").css(
						"border",
						"solid 1px #d5d5d5");
					tempArr.push(file);
					var popval = tempArr.pop();
					var start_index = 0
					var number_of_elements_to_remove = 1;
					var removed_elements = bghFileArray
						.splice(
							start_index,
							number_of_elements_to_remove,
							popval);
					bghArr.push(bghFileArray.pop());
				} else {
					alert("Upload only doc or pdf files with size less than 5MB");
					input = $('#bghApprovalSelect');
					input.val('').clone(true);
					$('#bghFileName').val('');
				}
			});

	$('#res_skills').on('change', function () {

		skillName = $('#res_skills option:selected').text()

	});

	$('#resourceRequiredDate')
		.on(
			'change',
			function () {

				if (new Date($('#requestedDate1')
						.val()).getTime() > new Date(
						$('#resourceRequiredDate')
						.val()).getTime()) {
					showErrorAlert("Resource Required date cannot be less than resource requested Date!");
					$('#resourceRequiredDate').val(
						'');
				}
			})

	$('#allocationTypeSelect')
		.on(
			'change',
			function () {

				var allocationType = $(
						'#allocationTypeSelect option:selected')
					.text();

				if ($(this).find(':selected').data(
						'obj') == 'Y') { //string contains BGH approval mandatory flag
					$('#bghApprovalSelect')
						.closest('.form-group')
						.addClass('validateMe');

				} else {
					$('#bghApprovalSelect')
						.closest('.form-group')
						.removeClass(
							'validateMe');
				}
			})
			
	/** $('#clientTypeSelect').on('change', function(){
	 clientType = $('#clientTypeSelect').val();
			
	 //$('#projectTypeSelect option').remove();
	 if(clientType=='New'){
	 $("#projectTypeSelect").children("option[value^=" + $(this).val() + "]").show()
	 /* ddForProjectType = "<option value='New'>New<option>";
	 $('#projectTypeSelect').append(ddForProjectType);  
	 }else{
	 $("#projectTypeSelect").children("option[value^=" + $(this).val() + "]").show()
	 }
	 });
	 */

	$('#skill1Select').multiselect({
		includeSelectAllOption: true,
		id: 'skill1Select'
	}).multiselectfilter();

	var skillsList = [];
	$('#skill1Select').bind(
		'change multiselectuncheckall',
		function () {

			var selectedText;

			/*sarang added start*/
			var selMulti = $.map($(this).find(
					'option:selected'),
				function (el, i) {
					skillsList = $('#skill1Select')
						.val();

					 
					selectedText = $(el).text();
					 
					$("#skillSetRequired").val(
						selectedText);
					return $(el).text();

				});
			if (selMulti == "") {
				$('#skillSetRequired').html('');
			}
			$("#skillSetRequired").val(
				selMulti.join(", "));
			addResourceInternal = $(this).val();
		});

	if ($("#round1Select option:selected").length) {
		var round1data = '';
		$
			.each(
				$("#round1Select option:selected"),
				function () {
					if ($("#round1Select option:selected").length == 1) {
						round1data = $(this).text()
					} else {
						round1data = round1data +
							$(this).text() +
							', ';
					}
				})
		$("#keyInterviewer1").val(round1data);
	}

	if ($("#round2Select option:selected").length) {
		var round1data = '';
		$
			.each(
				$("#round2Select option:selected"),
				function () {
					if ($("#round2Select option:selected").length == 1) {
						round1data = $(this).text()
					} else {
						round1data = round1data +
							$(this).text() +
							', ';
					}
				})
		$("#keyInterviewer2").val(round1data);
	}

	if ($("#sendMailTo option:selected").length) {
		var round1data = '';
		$
			.each(
				$("#sendMailTo option:selected"),
				function () {
					if ($("#sendMailTo option:selected").length == 1) {
						round1data = $(this).text()
					} else {
						round1data = round1data +
							$(this).text() +
							', ';
					}
				})
		$("#sendMailTo_val").val(round1data);
	}

	if ($("#notifyToIds option:selected").length) {
		var round1data = '';
		$
			.each(
				$("#notifyToIds option:selected"),
				function () {
					if ($("#notifyToIds option:selected").length == 1) {
						round1data = $(this).text()
					} else {
						round1data = round1data +
							$(this).text() +
							', ';
					}
				})
		$("#notifyToIds_val").val(round1data);
	}

	if ($("#skill1Select option:selected").length) {
		var round1data = '';
		$.each($("#skill1Select option:selected"),
			function () {
				round1data += $(this).text() + ', ';
				 
			})
		$("#skillSetRequired").val(round1data);
	}

	var empIdForRound1 = [];
	if ($("#round1Select option:selected").length) {
		$.each($("#round1Select option:selected"),
			function () {
			empIdForRound1.push($(this).val());
		})
	}
	
	/*$('#round1Select').multiselect({
		includeSelectAllOption: true,
		id: 'round1Select'
	}).multiselectfilter();*/

	$('#round1Select').bind(
		'change multiselectcheckall multiselectuncheckall',
		function (ev) {
			var selMulti = $.map($(this).find('option'),
					function (el, i) {
					if (el.selected) {
						empIdForRound1 = $('#round1Select').val();
						$("#keyInterviewer1").val($(el).text());
						return $(el).text();
					}
				});
			
			if ((ev && ev.type === 'multiselectuncheckall') || !selMulti.length) {
				empIdForRound1 = []
			}
			if (selMulti == "") {
				$('#keyInterviewer1').html('');
			}
			$("#keyInterviewer1").val(selMulti.join(", "));
		});

		/*$('#round2Select').multiselect({
			includeSelectAllOption: true,
			id: 'round2Select'
		}).multiselectfilter();*/

	var empIdForRound2 = [];
	if ($("#round2Select option:selected").length) {
		$.each($("#round2Select option:selected"),
			function () {
			empIdForRound2.push($(this).val());
		})
	}
	
	$('#round2Select').bind(
		'change multiselectcheckall multiselectuncheckall',
		function (ev) {
			var selMulti = $.map($(this).find('option'),
					function (el, i) {
					if (el.selected) {
						empIdForRound2 = $('#round2Select').val();
						$("#keyInterviewer2").val($(el).text());
						return $(el).text();
					}
				});
			if ((ev && ev.type === 'multiselectuncheckall') || !selMulti.length) {
				empIdForRound2 = []
			}
			if (selMulti == "") {
				$('#keyInterviewer2').html('');
			}
			$("#keyInterviewer2").val(selMulti.join(", "));
		});

	if ($("#round1Select option:selected").length) {
		var round1Seldata = '';
		$.each($("#round1Select option:selected"),
			function () {
				round1Seldata += $(this).text() + ', ';
			})
		$("#keyInterviewer1").val(round1Seldata);
	}

	if ($("#round2Select option:selected").length) {
		var round2Seldata = '';
		$.each($("#round2Select option:selected"),
			function () {
				round2Seldata += $(this).text() + ', ';
			})
		$("#keyInterviewer2").val(round2Seldata);
	}

	/*$('#sendMailTo').multiselect({
		includeSelectAllOption: true,
		id: 'sendMailTo',
		noneSelectedText: 'Select individual or group to mail to'
	}).multiselectfilter({
		options: {
			label: 'Search:',
			width: null,  override default width set in css file (px). null will inherit 
			placeholder: 'Enter keywords',
			autoReset: false,
			debounceMS: 250
			}
	});*/

	var sendMailToIds = [];
	$('#sendMailTo')
		.bind(
			'change multiselectcheckall multiselectuncheckall',
			function () {

				var selectedText;

				/*sarang added start*/
				var selMulti = $
					.map(
						$(this)
						.find(
							'option:selected'),
						function (el, i) {
							sendMailToIds = $(
									'#sendMailTo')
								.val();

							// console
							// 	.log(
							// 		'send mail to: ',
							// 		sendMailToIds); // send mail to multiselect ids
							selectedText = $(
									el)
								.text();
							 
							//$("#keyInterviewer2").val(selectedText);
							return $(el)
								.text();

						});
				if (selMulti == "") {
					$('#sendMailTo_val').html('');
				}
				$("#sendMailTo_val").val(
					selMulti.join(", "));
				addResourceInternal = $(this).val();
			});

	/*$('#notifyToIds').multiselect({
		includeSelectAllOption: true,
		id: 'notifyToIds',
		noneSelectedText: 'Select individual or group to notify to'
	}).multiselectfilter();*/

	var notifyMailToIds = $('#notifyToIds').val();
	$('#notifyToIds').bind(
		'change',
		function () {

			var selectedText;

			/*sarang added start*/
			var selMulti = $.map($(this).find(
					'option:selected'),
				function (el, i) {
					notifyMailToIds = $('#notifyToIds').val();  
					selectedText = $(el).text();
					 
					return $(el).text();

				});
			if (selMulti == "") {
				$('#notifyToIds_val').html('');
			}
			$("#notifyToIds_val").val(
				selMulti.join(", "));
			addResourceInternal = $(this).val();
		});

	$('#pdlMailIds').multiselect({
		includeSelectAllOption: true,
		id: 'pdlMailIds'
	}).multiselectfilter();

	var pdlMailToIds = [];
	pdlMailToIds = $('#pdlMailIds').val();
	
	$('#pdlMailIds_val').html(
		$('#pdlMailIds option:selected').text());
	$('#pdlMailIds')
		.bind(
			'change multiselectcheckall multiselectuncheckall',
			function () {

				var selectedText;

				/*sarang added start*/
				var selMulti = $.map($(this).find(
						'option:selected'),
					function (el, i) {
						pdlMailToIds = $(
								'#pdlMailIds')
							.val();

						 
						selectedText = $(el)
							.text();
						 
						return $(el).text();

					});
				if (selMulti == "") {
					$('#pdlMailIds_val')
						.html(
							$(
								'#pdlMailIds option:selected')
							.text());
				}
				$("#pdlMailIds_val").val(
					selMulti.join(", "));
				addResourceInternal = $(this).val();
			});

	/*$('#pdlMailIds').multiselect({
		includeSelectAllOption : true,
		id : 'pdlMailIds'
	}).multiselectfilter();
	 */

	$('#reasonButton')
		.on(
			'click',
			function () {
				var modal = document
					.getElementById('myModal');
				resourceReplacement = $(
						'#resourceForReplacement')
					.val();
				reason = $('#reasonForReplacement')
					.val();

				if (resourceReplacement != null > 0 &&
					reason != null) {

					requirements.parameters
						.push({
							"resourceReplacement": resourceReplacement
						});
					requirements.parameters.push({
						"reason": reason
					});
					modal.style.display = "none";
					$('#resourceForReplacement')
						.val('');
					$('#reasonForReplacement').val(
						'');

				} else {
					//showErrorAlert("Please select both of the fields!")
					if (validateReasonBtnForm()) {
						 
					} else {
						modal.style.display = "none";
						 
					}
				}

			});

	function validateReasonBtnForm() {
		var items = $('#reasonForm .validateMe').find(
			".ui-combobox-input,input[type='text']");
		 
		var al = 0;
		for (var i = 0; i < items.length; i++) {
			var b = $.trim(items[i].value);
			 
			if (b == "Select" || b == "") {
				 
				$(items[i]).closest('.form-group')
					.addClass('warning');
				al = al + 1;
			} else {
				 
				$(items[i]).closest('.form-group')
					.removeClass('warning');
			}
		}
		return al;
	}

	function validateForm1() {
		var items = $('#rmsForm1 .validateMe').find(
			".ui-combobox-input,input[type='text'], select");
		var al = 0;
		for (var i = 0; i < items.length; i++) {
			var b = $.trim(items[i].value);
			if (b == "Select" || b == "") {
				 
				$(items[i]).closest('.form-group')
					.addClass('warning');
				al = al + 1;
			} else {
				$(items[i]).closest('.form-group')
					.removeClass('warning');
			}

		}
		return al;

	}


	function validateForm2() {
		var al1 = 0;
		var items = $('#rmsForm2 .validateMe')
			.find(
				".ui-combobox-input,input[type='text'],input[type='number'],textarea");
		for (var i = 0; i < items.length; i++) {
			var b = $.trim(items[i].value);
			if (b < 0) {
				$(items[i]).closest('.form-group')
					.addClass('warning');
				al1 = al1 + 1;

			} else if (b == "Select" || b == "") {
				 
				$(items[i]).closest('.form-group')
					.addClass('warning');
				al1 = al1 + 1;
			} else {
				$(items[i]).closest('.form-group')
					.removeClass('warning');
			}

		}

		var onHold = $('#resOnHold').val();
		var onlost = $('#resLost').val();
		var positions = $('#numberOfPosition').val()
		var projectOtherDetails = $('#projectShiftDetails')
			.val();

		if ((parseInt(onHold) + parseInt(onlost)) > parseInt(positions)) {
			showErrorAlert("No. of hold and lost exceeds total number of positions!");
			$('#resLost').val('');
			$('#resOnHold').val('');
			al1 = al1 + 1;
		} else if (parseInt(onlost) > parseInt(positions)) {
			showErrorAlert("Please enter number of lost less than total no. of positions required!");
			$('#resLost').val('');
			al1 = al1 + 1;
		} else if (parseInt(onHold) > parseInt(positions)) {
			showErrorAlert("Please enter number of hold less than total no. of positions required!");
			$('#resOnHold').val('');
			al1 = al1 + 1;
		}
		var x = $('#projectDuration').val();
		if (x.includes('.')) {
			showErrorAlert("Please enter project duration without any decimal!");
			al1 = al1 + 1;
		}

		if (positions <= 0 && positions != '') {
			showErrorAlert("Please enter No. of positions more than or equal to 1! ");
			al1 = al1 + 1;
		}

		if (projectOtherDetails.length > 1000) {
			showErrorAlert("Project shift Other details should not be more than 1000 characters!");
			al1 = al1 + 1;
		}
		//Added code Am Job Code length check of 50 characters
		if ($("#amJobCode").val().trim().length > 50) {
			showErrorAlert("Am Job Code cannot hold more than 50 characters");
			$("#amJobCode").closest('.form-control')
				.addClass('warning');
			al1 = al1 + 1;
		} else {
			$("#amJobCode").closest('.form-control')
				.removeClass('warning');
		}
		
		if ($("#successFactorId").val() != ""  &&  $("#successFactorId").val().trim().length > 50) {
			showErrorAlert("Success Factor Id cannot hold more than 50 characters");
			$("#successFactorId").closest('.form-control')
				.addClass('warning');
			al1 = al1 + 1;
		} else {
			$("#successFactorId").closest('.form-control')
				.removeClass('warning');
		}
			
		return al1;

	}

	$('#nextSecond').on('click', function () {

		 
		if (validateForm2()) { 
			setTabStatus('in progress', 2);
			return;
		} else {
			$('.nav_step2').removeClass('active');
			$('.nav_step3').addClass('active');
			$('#rmsForm2').removeClass('in active');
			$('#rmsForm3').addClass('in active');
			setTabStatus('completed', 2);
			setTabStatus('in progress', 3);
		}
	});

	$('#prevToFirst').on('click', function () {

		//firstPageValidationFlag = 0;
		$('.nav_step2').removeClass('active');
		$('.nav_step1').addClass('active');
		$('#rmsForm2').removeClass('in active');
		$('#rmsForm1').addClass('in active');

	});
	$('#prevToSecond').on('click', function () {

		//firstPageValidationFlag = 0;
		$('.nav_step3').removeClass('active');
		$('.nav_step2').addClass('active');
		$('#rmsForm3').removeClass('in active');
		$('#rmsForm2').addClass('in active');

	});
	$('#prevToThird').on('click', function () {

		//firstPageValidationFlag = 0;
		$('.nav_step4').removeClass('active');
		$('.nav_step3').addClass('active');
		$('#rmsForm4').removeClass('in active');
		$('#rmsForm3').addClass('in active');

	});
	$('#prevToFourth').on('click', function () {

		//firstPageValidationFlag = 0;
		$('.nav_step5').removeClass('active');
		$('.nav_step4').addClass('active');
		$('#rmsForm5').removeClass('in active');
		$('#rmsForm4').addClass('in active');

	});

	function thirdPageValidation() {
		var al = 0;
		var items = $('#rmsForm3 .validateMe')
			.find(
				".ui-combobox-input,input[type='text'],input[type='number'],textarea,select");
		var al = 0;
		for (var i = 0; i < items.length; i++) {
			var b = items[i].value;
			if (b < 0) {
				$(items[i]).closest('.form-group')
					.addClass('warning');
				al = al + 1;

			} else if (b == "Select" || b == "" ||
				b.trim() == '') {
				 
				$(items[i]).closest('.form-group')
					.addClass('warning');
				$(items[i]).closest('.form-control')
					.addClass('warning');
				al = al + 1;
			} else {
				$(items[i]).closest('.form-group')
					.removeClass('warning');
				$(items[i]).closest('.form-control')
					.removeClass('warning');
			}
		}
		if ($('#primarySkills').val().trim().length > 40000) {
			showErrorAlert("Primary Skills cannot hold more than 40000 characters");
			$("#primarySkills").closest('.form-control')
				.addClass('warning');
			al = al + 1;
		} else {
			$("#primarySkills").closest('.form-control')
				.removeClass('warning');
		}
		if ($('#additionalSkills').val().trim().length > 5000) {
			showErrorAlert("Additional Skills cannot hold more than 5000 characters");
			$("#additionalSkills").closest('.form-control')
				.addClass('warning');
			al = al + 1;
		} else {
			$("#additionalSkills").closest('.form-control')
				.removeClass('warning');
		}
		if ($('#rolesAndResponsibilities').val().trim().length > 5000) {
			showErrorAlert("Roles And Responsibilities cannot hold more than 5000 characters");
			$("#rolesAndResponsibilities").closest(
				'.form-control').addClass('warning');
			al = al + 1;
		} else {
			$("#rolesAndResponsibilities").closest(
				'.form-control').removeClass('warning');
		}
		if ($('#additionalComment').val().trim().length > 5000) {
			showErrorAlert("Additional Comment cannot hold more than 5000 characters");
			$("#additionalComment")
				.closest('.form-control').addClass(
					'warning');
			al = al + 1;
		} else {
			$("#additionalComment")
				.closest('.form-control').removeClass(
					'warning');
		}

		return al;
	}

	function fourthPageValidation() {
		var al = 0;
		var items = $('#rmsForm4 .validateMe')
			.find(
				".ui-combobox-input,input[type='text'],input[type='number'],textarea");
		var al = 0;
		for (var i = 0; i < items.length; i++) {
			var b = items[i].value;
			if (b < 0) {
				$(items[i]).closest('.form-group')
					.addClass('warning');
				al = al + 1;

			} else if (b == "Select" || b == "") {
				 
				$(items[i]).closest('.form-group')
					.addClass('warning');
				al = al + 1;
			} else {
				$(items[i]).closest('.form-group')
					.removeClass('warning');
			}

		}
		return al;
	}

	function fifthPageValidation() {
		var al = 0;
		var items = $('#rmsForm5 .validateMe')
			.find(
				".ui-combobox-input,input[type='text'],input[type='number'],textarea");
		var al = 0;
		for (var i = 0; i < items.length; i++) {
			var b = items[i].value;
			if (b < 0) {
				$(items[i]).closest('.form-group')
					.addClass('warning');
				al = al + 1;

			} else if (b == "Select" || b == "") {
				 
				$(items[i]).closest('.form-group')
					.addClass('warning');
				al = al + 1;
			} else {
				$(items[i]).closest('.form-group')
					.removeClass('warning');
			}

		}
		if (skillsList.length <= 0 &&
			$("#skill1Select option:selected").length <= 0) {
			//showErrorAlert("Please select atleast 1 Skill Before Submitting the Form!");
			$("#skill1Select").closest('.form-group')
				.addClass('warning')
			al = al + 1;
		}
		if (sendMailToIds.length <= 0 &&
			$("#sendMailTo option:selected").length <= 0 && notifyMailToIds!=null &&
			notifyMailToIds.length <= 0 &&
			$("#notifyToIds option:selected").length <= 0 &&
			pdlMailToIds.length <= 0 &&
			$("#pdlMailIds option:selected").length <= 0) {
			showErrorAlert("Please select atleast 1 Email Before Submitting the Form!");
			al = al + 1;
		}
		return al;
	}

	$('#nextFourth').on('click', function () {
		if ($('#requiredForSelect').val() == 'ODC'){
			$("#round1Select").closest('.form-group').addClass('validateMe'); //key interviewers mandatory in case of ODC
			$("#round2Select").closest('.form-group').addClass('validateMe');
		}
		//firstPageValidationFlag = 0;
		if (thirdPageValidation()) {
			setTabStatus('in progress', 3);
			return;
		} else {
			$('.nav_step3').removeClass('active');
			$('.nav_step4').addClass('active');
			$('#rmsForm3').removeClass('in active');
			$('#rmsForm4').addClass('in active');
			setTabStatus('completed', 3);
			setTabStatus('in progress', 4);
		}
	});
	$('#nextFifth').on('click', function () {

		//firstPageValidationFlag = 0;
		if (fourthPageValidation()) {
			 
		} else {
			$('.nav_step4').removeClass('active');
			$('.nav_step5').addClass('active');
			$('#rmsForm4').removeClass('in active');
			$('#rmsForm5').addClass('in active');
		}
	});

	$(document)
		.on(
			'click',
			'#submitForm',
			function () {

				if (fifthPageValidation()) {
					 
					event.preventDefault()
				} else {
					 
					bgbu = $('#bgBuSelect').val();
					bgbu_sel_text = $('#bgBuSelect')
						.closest('.form-group')
						.find(
							'.ui-combobox-input')
						.val();

					if (bgbu != "select") {
						requirements.parameters
							.push({
								"bgbu": bgbu
							});
					} else {
						validation++;
					}

					hiringbgBu = $(
							'#hiringbgBuSelect')
						.val();
					if (hiringbgBu != "select") {
						requirements.parameters
							.push({
								"hiringBgBu": hiringbgBu
							});
					} else {
						validation++;
					}

					/* priority = $('#prioritySelect').val();
					requirements.parameters.push({"priority" : priority}); */

					deliveryPOCID = $(
							'#deliverPOCSelect')
						.val(); //delivery poc empid
					if (deliveryPOCID != "select") {
						requirements.parameters
							.push({
								"deliveryPOCId": deliveryPOCID
							});
					} else {
						validation++;
					}

					selectedRequestorID = $('#requestorSelect').val(); //requestor emp ID
					 
					if (selectedRequestorID != "select") {
						requirements.parameters
							.push({
								"requestorID": selectedRequestorID
							});
					} else {
						validation++;
					}

					requestedDate = $(
							'#requestedDate1')
						.val();
					if (requestedDate != "") {
						requirements.parameters
							.push({
								"requestedDate": requestedDate
							});
					} else {
						validation++;
					}

					var clientID = $(
							'#clientNameSelect')
						.val(); //client ID
					if (clientID != "select") {
						requirements.parameters
							.push({
								"clientId": clientID
							});
					} else {
						validation++;
					}

					customerGrpId = $(
							'#clientGroupSelect')
						.val(); //client ID
					if (customerGrpId != null &&
						customerGrpId != "select") {
						requirements.parameters
							.push({
								"customerGrpId": customerGrpId
							});
					} else {
						var emptyString = "Select";
						requirements.parameters
							.push({
								"customerGrpId": emptyString
							});
					}

					allocationTypeId = $(
							'#allocationTypeSelect')
						.val(); //client ID
					if (allocationTypeId != "select") {
						requirements.parameters
							.push({
								"allocationTypeId": allocationTypeId
							});
					} else {
						validation++;
					}

					locationID = $(
							'#locationTypeSelect')
						.val();
					if (locationID != "select") {
						requirements.parameters
							.push({
								"locationId": locationID
							});
					} else {
						validation++;
					}

					resourceRequiredDate = $(
							'#resourceRequiredDate')
						.val()
					if (resourceRequiredDate != "select") {
						requirements.parameters
							.push({
								"resourceRequiredDate": resourceRequiredDate
							});
					} else {
						validation++;
					}

					positions = $(
							'#numberOfPosition')
						.val();
					if (positions != "select") {
						requirements.parameters
							.push({
								"positions": positions
							});
					} else {
						validation++;
					}

					onHold = $('#resOnHold').val();
					if (onHold != "select") {
						requirements.parameters
							.push({
								"hold": onHold
							});
					} else {
						validation++;
					}

					lost = $('#resLost').val();
					if (lost != "select") {
						requirements.parameters
							.push({
								"lost": lost
							});
					} else {
						validation++;
					}

					projOtherDetails = $(
							'#projectShiftDetails')
						.val();
					if (projOtherDetails != "select") {
						requirements.parameters
							.push({
								"projectShiftDetails": projOtherDetails
							});
					} else {
						validation++;
					}

					expOtherDetails = $(
							'#expOtherDetails')
						.val();
					if (expOtherDetails != "select" &&
						expOtherDetails != "") {

						requirements.parameters
							.push({
								"expOtherDetails": expOtherDetails
							});
					} else {
						validation++;
					}

					clientInterviewRequired = $(
							'#clientInterviewRequiredSelect')
						.val();
					if (clientInterviewRequired != "select") {
						requirements.parameters.push({
							"clientInterviewRequired": clientInterviewRequired
						});
					} else {
						validation++;
					}

					clientType = $('#clientTypeSelect').val();
					if (clientType != "select") {
						requirements.parameters.push({
							"clientType": clientType
						});
					} else if (clientType == "New") {

					} else {
						validation++;
					}

					projectType = $(
							'#projectTypeSelect')
						.val();
					if (projectType != "select") {
						requirements.parameters.push({
							"projectType": projectType
						});
					} else {
						validation++;
					}

					projectID = $('#projectNameSelect').val();
					if (projectID != "select") {
						requirements.parameters.push({
							"projectID": projectID
						});
					} else {
						validation++;
					}

					projectDuration = $(
							'#projectDuration')
						.val();
					if (projectDuration != "select") {
						requirements.parameters.push({
							"projectDuration": projectDuration
						});
					} else {
						validation++;
					}

					skill = $('#res_skills').val();
					if (skill != "select") {
						skillName = $('#res_skills option:selected').text()
						requirements.parameters.push({
							"skill": skill
						});
					} else {
						validation++;
					}

					bgvRequired = $('#bgvRequiredSelect').val();
					if (bgvRequired != "select") {
						requirements.parameters.push({
							"bgvRequired": bgvRequired
						});
					} else {
						validation++;
					}

					test = $('#experienceRequiredSelect').val()
					if (test != "select") {
						requirements.parameters.push({
							"experienceId": test
						});
					} else {
						validation++;
					}
					shiftTypeId = $('#projectShiftTypeSelect').val();
					if (shiftTypeId != "select") {
						requirements.parameters.push({
							"shiftTypeId": shiftTypeId
						});
					} else {
						validation++;
					}

					resourceTypeId = $('#resourceTypeSelect').val();
					if (resourceTypeId != "select") {
						requirements.parameters.push({
							"resourceTypeId": resourceTypeId
						});
					} else {
						validation++;
					}
					requirementAreaId = $('#requirementAreaSelect').val();
					if (requirementAreaId != "-1") {
						requirements.parameters.push({
							"requirementAreaId": requirementAreaId
						});
					} else {
						validation++;
					}

					primarySkills = $('#primarySkills').val().trim();
					if (primarySkills != "select" && '' != primarySkills) {
						requirements.parameters.push({
							"primarySkills": primarySkills
						});
					} else {
						validation++;
					}

					additionalSkills = $(
							'#additionalSkills')
						.val();
					if (additionalSkills != "select") {
						requirements.parameters
							.push({
								"additionalSkills": additionalSkills
							});
					} else {
						validation++;
					}

					rolesAndResponsibilities = $(
							'#rolesAndResponsibilities')
						.val();
					if (rolesAndResponsibilities != "select") {
						requirements.parameters
							.push({
								"rolesAndResponsibilities": rolesAndResponsibilities
							});
					} else {
						validation++;
					}

					requiredFor = $(
							'#requiredForSelect')
						.val();
					if (requiredFor != "select") {
						requirements.parameters
							.push({
								"requiredFor": requiredFor
							});
					} else {

					}

					additionalComment = $(
							'#additionalComment')
						.val();
					if (additionalComment != "select") {
						requirements.parameters
							.push({
								"additionalComment": additionalComment
							});
					} else {
						validation++;
					}

					//Added code for Am Job Code
					amJobCodeVal = $('#amJobCode')
						.val();
					if (amJobCodeVal != "select") {
						requirements.parameters
							.push({
								"amJobCode": amJobCodeVal
							});
					} else {
						validation++;
					}

					designationId = $(
							'#designationSelect')
						.val();
					if (designationId != "select") {
						requirements.parameters
							.push({
								"designationId": designationId
							});
					} else {
						errorMsg = "Please select a designation!"
						validation++;
					}

					if (empIdForRound1.length) {
						var stringForRound1 = empIdForRound1.join(",");
						requirements.parameters.push({"round1empId": stringForRound1});
					} else {
						requirements.parameters.push({"round1empId": ""});
						validation++;
					}

					if (empIdForRound2.length) {
						var stringForRound2 = empIdForRound2.join(",");
						requirements.parameters.push({"round2empId": stringForRound2});
					} else {
						requirements.parameters.push({"round2empId": ""});
						validation++;
					}

					if ($('#skill1Select').val().length > 0) {
						//skillsList = $('#skill1Select').val()
						var skills = $(
								'#skill1Select')
							.val().join(",");
						requirements.parameters
							.push({
								"skill1name": skills
							});
					} else {
						validation++;
					}
				
					if (notifyMailToIds!=null && notifyMailToIds.length > 0) {
						requirements.parameters
							.push({
								"notifyMailIds": notifyMailToIds
							});
					} else {
						var emptyNotify = "";
						requirements.parameters
							.push({
								"notifyMailIds": emptyNotify
							});
					}

					if (pdlMailToIds.length > 0) {
						requirements.parameters
							.push({
								"pdlIds": pdlMailToIds
							});
					} else {
						var emptyNotify = "";
						requirements.parameters
							.push({
								"pdlIds": emptyNotify
							});
					}

					if (sendMailToIds.length > 0) {
						requirements.parameters
							.push({
								"mailTo": sendMailToIds
							});
					} else {
						var emptyNotify = "";
						requirements.parameters
							.push({
								"mailTo": emptyNotify
							});
					}
					
				if (rmgPocIds.length > 0) {
					requirements.parameters.push({"rmgPoc": rmgPocIds.join(",")});
				} else {
					requirements.parameters.push({"rmgPoc": ""});
				}
				
				if (tecTeamIds.length > 0) {
					requirements.parameters.push({"tecTeamPoc": tecTeamIds.join(",")});
				} else {
					requirements.parameters.push({"tecTeamPoc": ""});
				}
				
				requirements.parameters.push({"successFactorId": $('#successFactorId').val()});
				
				submitAjaxCall();
				}
			})

	// Date picker bind
	$('#requestedDate1').datepicker({
		uiLibrary: 'bootstrap4'

	});
	$('#resourceRequiredDate').datepicker({
		uiLibrary: 'bootstrap4'
	});

	function stopProgress() {
		$.unblockUI();
	}

	function submitAjaxCall() {
		var data = new FormData();
		
		
		if ($('#buttonFlagId').val() == false) {
			if (customerGroupName != "") {
				var requirementId = clientName + "-" +
					customerGroupName + "-" +
					projectName + "-" + skillName +
					"-";
			} else {
				var requirementId = clientName + "-" +
					projectName + "-" + skillName +
					"-";
			}
			requirements.parameters.push({
				"requirementName": requirementId
			});

		} else {
			var name = $('#requirementName').val()
			var reqId = $('#requisitionDbId').val()
			requirements.parameters.push({
				"requirementName": name
			});
			requirements.parameters.push({
				"skillRequestId": reqId
			});
		}

		for (i = 0; i < arr1.length; i++) { //to append multiple resume files on click. 
			data.append("buhFile", arr1[i]);
			//arrOfFileNames.push(arr[i].name);	
		}
		for (i = 0; i < bghArr.length; i++) { //to append multiple resume files on click. 
			data.append("bghFile", bghArr[i]);
			//arrOfFileNames.push(arr[i].name);	
		}
		
		if($('#copyFlagButton').val()=="copyFlagTrue"){
			console.log()
			data.append("copyFlagButton","copyFlagTrue");
		}
		 
		//Code To trigger email or not - specifically for admin - start		
		data.append("triggerEmail",triggerEmailFlag);
		//Code To trigger email or not - specifically for admin - end
		
		 $('#sendMailTo').val(null).trigger('change');
		 $('#notifyToIds').val(null).trigger('change');
		 $('#round1Select').val(null).trigger('change');
		 $('#round2Select').val(null).trigger('change');
		 $('#RMGPocSelect').val(null).trigger('change');
		 $('#TecTeamSelect').val(null).trigger('change');
		 	
		
		data.append("json", JSON.stringify(requirements));
		startProgress();
		$.ajax({
				type: 'POST',
				dataType: 'text',
				url: '/rms/requests/saveRRF/',
				contentType: false,
				data: data,
				cache: false,
				processData: false,
				success: function (data) {
					
					stopProgress();

					if (data == '') {
						showSuccessAlert("Your Request is updated!");
					} else {
						showSuccessAlert("Your request is saved, Please note your RRF ID : " +
							data);
					}
					window.location.href = "/rms/requestsReports/positionDashboard";
				},
				error: function (data) {
					 
					stopProgress();
					showErrorAlert("Something went wrong, Please re-validate your RRF !");

				}
			});

	}

	//multiselect with data filter bind

	//file input box custom js
	/* $(".input-file").before(
	function() {
		if ( ! $(this).prev().hasClass('input-ghost') ) {
			var element = $("<input type='file' class='input-ghost' id='buhApprovalSelect' style='visibility:hidden; height:0'>");
			element.attr("name",$(this).attr("name"));
			element.change(function(){
				element.next(element).find('input').val((element.val()).split('\\').pop());
			});
			$(this).find(".btn-choose").click(function(){
				element.click();
			});
			
			$(this).find('input').css("cursor","pointer");
			$(this).find('input').mousedown(function() {
				$(this).parents('.input-file').prev().click();
				return false;
			});
			return element;
		}
	}
	);*/



	$('#nextFirst').on('click', function () {
		if (validateForm1()) {
			setTabStatus('in progress', 1);
			return;
		} else {
			$('.nav_step1').removeClass('active');
			if (window.location.search.includes('reqId') && $('#clientGroupSelectOnEdit').val()) {
				$('#grpdiv').show();
			}
			$('.nav_step2').addClass('active');
			$('#rmsForm1').removeClass('in active');
			$('#rmsForm2').addClass('in active');
			setTabStatus('completed', 1);
			setTabStatus('in progress', 2);
			$('#requiredForSelect').trigger('change');
		}
	});
	 
	if ($('#buttonFlagId').val() == 'true') {
		var experienceId = "";
		experienceId = $('#experienceRequiredSelect').val();
		if (experienceId == "Others") {
			$("#otherDetailsTextBox")
				.closest('.form-group').addClass(
					'validateMe');
			$("#otherDetailsTextBox").show();
		} else {
			$("#otherDetailsTextBox")
				.closest('.form-group').removeClass(
					'validateMe');
			$("#otherDetailsTextBox")
				.css("display", "none");
		}
		if ($('#requiredForSelect option:selected').text() != 'ODC') { //means staffing then enable
			$("#amJobCode").closest('.form-group')
				.addClass('validateMe');
			document.getElementById("amJobCode").disabled = false;
		} else {
			$("#amJobCode").closest('.form-group')
				.removeClass('validateMe');
			document.getElementById("amJobCode").disabled = true;
			$("#amJobCode").val('');

		}
	}

	$('#experienceRequiredSelect')
		.on(
			'change',
			function () {
				if ($('#buttonFlagId').val() == 'true') {
					var experienceId = "";
					experienceId = $(
							'#experienceRequiredSelect')
						.val();
					if (experienceId == "Others") {
						$("#otherDetailsTextBox")
							.closest(
								'.form-group')
							.addClass(
								'validateMe');
						$("#otherDetailsTextBox")
							.show();
					} else {
						$("#otherDetailsTextBox")
							.closest(
								'.form-group')
							.removeClass(
								'validateMe');
						$("#otherDetailsTextBox")
							.css("display",
								"none");
					}
				} else {
					experienceId = $(
							'#experienceRequiredSelect')
						.val();
					 
					if (experienceId == "Others") {
						 
						$("#otherDetailsTextBox")
							.closest(
								'.form-group')
							.addClass(
								'validateMe');
						$("#otherDetailsTextBox")
							.css("display",
								"block");
					} else {
						// $("#otherDetailsTextBox")
						// 	.closest('.row')
						// 	.find('.form-group')
						// 	.removeClass(
						// 		'col-md-3')
						// 	.addClass(
						// 		'col-md-4');
						$("#otherDetailsTextBox")
							.closest(
								'.form-group')
							.removeClass(
								'validateMe');
						$("#otherDetailsTextBox")
							.css("display",
								"none");
					}
				}
			});

	if ($('#buttonFlagId').val() == false) {
		$('.ui-state-default').val('');
	}

	$(".autoSelect .ui-combobox-input")
		.each(
			function () {
				var s = $(this)
					.closest('.autoSelect')
					.find(
						'select option.selected-option')
					.text();
				$(this).closest('.autoSelect')
					.find('.ui-combobox-input')
					.val(s);
			});

	$('#requiredForSelect')
		.change(
			function () {
				if ($(this).val() == 'ODC') {

					$(
							'#clientInterviewRequiredSelect option[value="N"]')
						.attr('selected',
							'selected');
					$(
							'#clientInterviewRequiredSelect option[value="Y"]')
						.removeAttr('selected');

					var s_sel = $(
							'#clientInterviewRequiredSelect option[value="N"]')
						.text();
					$(
							'#clientInterviewRequiredSelect')
						.closest('.positionRel')
						.find(
							'.ui-combobox-input')
						.val(s_sel);

					$("#round1Select").closest(
							'.form-group')
						.addClass('validateMe'); //key interviewers mandatory in case of ODC
					$("#round2Select").closest(
							'.form-group')
						.addClass('validateMe');

					$("#buhApprovalSelect")
						.closest('.form-group')
						.addClass('validateMe'); //on the fly, odc case buh  mandatory

					$("#amJobCode").closest(
							'.form-group')
						.removeClass(
							'validateMe');
					document
						.getElementById("amJobCode").disabled = true;
					$("#amJobCode").val('');

				} else {

					$(
							'#clientInterviewRequiredSelect option[value="Y"]')
						.attr('selected',
							'selected');
					$(
							'#clientInterviewRequiredSelect option[value="N"]')
						.removeAttr('selected');

					var s_sel = $(
							'#clientInterviewRequiredSelect option[value="Y"]')
						.text();
					$(
							'#clientInterviewRequiredSelect')
						.closest('.positionRel')
						.find(
							'.ui-combobox-input')
						.val(s_sel);

					$("#round1Select").closest(
							'.form-group')
						.removeClass(
							'validateMe'); //key interviewers mandatory in case of ODC
					$("#round2Select").closest(
							'.form-group')
						.removeClass(
							'validateMe');

					$("#buhApprovalSelect")
						.closest('.form-group')
						.removeClass(
							'validateMe'); //on the fly, staffing case buh bgh not mandatory
					$("#bghApprovalSelect")
						.closest('.form-group')
						.removeClass(
							'validateMe');

					$("#amJobCode").closest(
							'.form-group')
						.addClass('validateMe');
					document
						.getElementById("amJobCode").disabled = false;
				}
			});

	$('#projectShiftTypeSelect')
		.on(
			'change',
			function () {

				if ($(
						'#projectShiftTypeSelect option:selected')
					.text() != 'General') { // mandatory only when shift type is not general
					$("#projectShiftDetails")
						.closest('.form-group')
						.addClass('validateMe');
				} else {
					$("#projectShiftDetails")
						.closest('.form-group')
						.removeClass(
							'validateMe');
				}

			});
	$('.display-edit').on(
		'click',
		function () {
			var modal = document
				.getElementById('myModal');
			modal.style.display = "block";
			$('#reasonForm .form-group').removeClass(
				'warning');
		})

	$('#resourceTypeSelect')
		.on(
			'change',
			function () {
				var modal = document
					.getElementById('myModal');
				var span = document
					.getElementsByClassName("close")[0];
				var resourceReplacementVal = $(
						'#resourceForReplacement')
					.val();
				var reasonVal = $(
						'#reasonForReplacement')
					.val();
				 
				if ($(
						'#resourceTypeSelect option:selected')
					.text() == 'Replacement') {
					modal.style.display = "block";
					$('#reasonForm .form-group')
						.removeClass('warning');
					$('.display-edit').show();
				} else {
					$('.display-edit').hide();
				}

				span.onclick = function () {
					if (resourceReplacementVal == null &&
						reasonVal == null) {
						$('#resourceTypeSelect')
							.closest(
								'.form-group')
							.find(
								'.ui-combobox-input')
							.val('');
					}
					modal.style.display = "none";
				}

				window.onclick = function (event) {
					if (event.target == modal) {
						if (resourceReplacementVal == null &&
							reasonVal == null) {
							$('#resourceTypeSelect')
								.closest(
									'.form-group')
								.find(
									'.ui-combobox-input')
								.val('');
						}
						modal.style.display = "none";
					}
				}

			});
	/*
	 * RMG-ERP set - start
	 */
	$('#requirementAreaSelect').bind('change', function (e) {
		const area = ['SAP','NON_SAP'];
		 var selectedVal;
		if (e && e.target.value === area[0]) {
			selectedVal = $("#pdlMailIds").find("option:contains('RMG-ERP@yash.com')").val();
			$('#pdlMailIds').val([selectedVal]).multiselect('refresh').trigger('change');
		} else {
			selectedVal = $("#pdlMailIds").find("option:contains('RMG-NonSap@yash.com')").val();
			$('#pdlMailIds').val([selectedVal]).multiselect('refresh').trigger('change');
		}
	});
	/*
	 * RMG-ERP set - end
	 */
$('#previewBtnClicked')
		.on(
			'click',
			function () {
				var modal = document
					.getElementById('myPreviewModal');
				var span = document
					.getElementById("close-icon");
				var close = document
					.getElementById("close-btn");
				modal.style.display = "block";

				$('#previewHiringBGBU')
					.text(
						$(
							'#hiringbgBuSelect option:selected')
						.val());
				$('#previewRequestorsBGBU')
					.text(
						$(
							'#bgBuSelect option:selected')
						.val());
				$('#previewRequestDate').text(
					$('#requestedDate1').val());
				$('#previewDeliveryPOC')
					.text(
						$(
							'#deliverPOCSelect option:selected')
						.text());
				$('#previewRequestorsName')
					.text(
						$(
							'#requestorSelect option:selected')
						.text());
				
				if ($(RMGPocSelectData.length)) {
					$('#previewRMGPoc').text(RMGPocSelectData.join(', '));
				}
				else{
					$('#previewRMGPoc').text('');
				}
				
				if ($(techTeamPocSelectData).length) {
					$('#previewTecTeamPoc').text(techTeamPocSelectData.join(', '));
				}
				else{
					$('#previewTecTeamPoc').text('');
				}
				
				$('#previewRequirementType')
					.text(
						$(
							'#resourceTypeSelect option:selected')
						.text());
				$('#previewRequirementArea')
				.text(
					$(
						'#requirementAreaSelect option:selected')
					.text());
				$('#previewRequiredFor')
					.text(
						$(
							'#requiredForSelect option:selected')
						.val());
				$('#previewRequiredDate').text(
					$('#resourceRequiredDate')
					.val());
				$('#previewClientType')
					.text(
						$(
							'#clientTypeSelect option:selected')
						.val());
				if ($('#buttonFlagId').val()) { //check for edit RRF.
					$('#previewClientName')
						.text(
							$(
								'#clientNameSelectOnEdit')
							.val());
					$('#previewProjectName')
						.text(
							$(
								'#projectNameSelectOnEdit')
							.val());
					$('#previewCompetency').text(
						$('#res_skills_onEdit')
						.val());
					$('.hold').css('display',
						'block')
					$('.lost').css('display',
						'block')
					$('#previewHold').text(
						$('#resOnHold').val());
					$('#previewLost').text(
						$('#resLost').val());

				} else {
					$('#previewClientName')
						.text(
							$(
								'#clientNameSelect option:selected')
							.text());
					$('#previewProjectName')
						.text(
							$(
								'#projectNameSelect option:selected')
							.text());
					$('#previewCompetency')
						.text(
							$(
								'#res_skills option:selected')
							.text());
				}
				//Following fix include clientGroup name while user edit rrf and click preview.
				if ($('#buttonFlagId').val()) { //check for edit RRF.

					if ($(
							'#clientGroupSelectOnEdit')
						.val() != "") {
						$('#previewClientGroup')
							.text(
								$(
									'#clientGroupSelectOnEdit')
								.val());
					} else {
						$('.previewGrp').css(
							'display', 'none');
					}
					//Added new field AM Job Code
					if ($('#amJobCode').val() != "") {
						$('#previewAmJobCode')
							.text(
								$(
									'#amJobCode')
								.val());
					} else {
						$('#previewAmJobCode')
							.text("-");
					}
				} else {
					if ($(
							'#clientGroupSelect option:selected')
						.text() != "") {
						$('#previewClientGroup')
							.text(
								$(
									'#clientGroupSelect option:selected')
								.text());
					} else {
						$('#previewClientGroup')
							.text("-");
					}
					//Added new field AM Job Code
					if ($('#amJobCode').val() != "") {
						$('#previewAmJobCode')
							.text(
								$(
									'#amJobCode')
								.val());
					} else {
						$('#previewAmJobCode')
							.text("-");
					}
				}

				/* if($('#clientGroupSelectOnEdit').val() != ""){
					$('#previewClientGroup').text($('#clientGroupSelectOnEdit').val());
				}else{
					$('.previewGrp').css('display', 'none');
				}
				if($('#clientGroupSelect option:selected').text() != ""){
					$('#previewClientGroup').text($('#clientGroupSelect option:selected').text());
				}else{
					$('#previewClientGroup').text("-");
				}
				 */
				$('#previewProjectType')
					.text(
						$(
							'#projectTypeSelect option:selected')
						.val());
				$('#previewPositions').text(
					$('#numberOfPosition')
					.val());
				$('#previewDesignation')
					.text(
						$(
							'#designationSelect option:selected')
						.text());

				if ($(
						'#experienceRequiredSelect option:selected')
					.val() == 'Others') {
					$('#previewExpRequired').text(
						$('#expOtherDetails')
						.val());
				} else {
					$('#previewExpRequired')
						.text(
							$(
								'#experienceRequiredSelect option:selected')
							.val());
				}

				$('#previewAllocationType')
					.text(
						$(
							'#allocationTypeSelect option:selected')
						.text());
				$('#previewLocation')
					.text(
						$(
							'#locationTypeSelect option:selected')
						.text());
				$('#previewProjectDuration')
					.text(
						$(
							'#projectDuration')
						.val());
				$('#previewShiftType')
					.text(
						$(
							'#projectShiftTypeSelect option:selected')
						.text());

				if ($('#projectShiftDetails').val() != "") {
					$('#previewShiftDetails')
						.text(
							$(
								'#projectShiftDetails')
							.val());
				}

				$('#previewClientInterview')
					.text(
						$(
							'#clientInterviewRequiredSelect option:selected')
						.text());
				$('#previewBGVRequired')
					.text(
						$(
							'#bgvRequiredSelect option:selected')
						.text());

				if ($('#keyInterviewer1').val()) {
					$('#previewRound1').text($('#keyInterviewer1').val());
				} else {
					$('#previewRound1').text('-');
				}

				if ($('#keyInterviewer2').val()) {
					$('#previewRound2').text($('#keyInterviewer2').val());
				} else {
					$('#previewRound2').text('-');
				}

				$('#previewSkillsToEval').text(
					$('#skillSetRequired')
					.val());
				if ($('#primarySkills').val()
					.trim() != "") {
					$('#previewPrimarySkills')
						.text(
							$(
								'#primarySkills')
							.val()
							.trim());
				}

				if ($('#additionalSkills').val() != "") {
					$('#previewAddSkills').text(
						$('#additionalSkills')
						.val());
				}

				if ($('#rolesAndResponsibilities')
					.val() != "") {
					$('#previewRoles')
						.text(
							$(
								'#rolesAndResponsibilities')
							.val());
				}

				if ($('#additionalComment').val() != "") {
					$('#previewAdditionalComments')
						.text(
							$(
								'#additionalComment')
							.val());
				}

				if ($('#sendMailTo_val').val() != "") {
					$('#previewMailTo').text(
						$('#sendMailTo_val')
						.val());
				}

				if ($('#notifyToIds_val').val() != "") {
					$('#previewNotifyTo').text(
						$('#notifyToIds_val')
						.val());
				}

				if ($('#pdlMailIds_val').val() != "") {
					$('#previewPDl').text(
						$('#pdlMailIds_val')
						.val());
				}

				/* $('#previewPriority').text($('#prioritySelect option:selected').text()); */
				if($('#successFactorId').val()) {
					$('#previewSuccessFactorId').text($('#successFactorId').val());
				}
				else{
					$('#previewSuccessFactorId').text("-");
				}
				
				span.onclick = function () {
					modal.style.display = "none";
				}
				close.onclick = function () {
					modal.style.display = "none";
				}

				window.onclick = function (event) {
					if (event.target == modal) {
						modal.style.display = "none";
					}
				}

			});
	//Email Trigger Check for Admin- start
		$('#chkEmailTrigger').change(function(ev) {
			if (!ev.target.checked) {
				triggerEmailFlag = true;
			} else {
				triggerEmailFlag = false;
			}
			
		});
	//Email Trigger Check for Admin- end
		
	// RMG Poc and Tec Team Poc new Changes start
		
		var RMGPocSelectData = [];
		var rmgPocIds = [];
		if ($("#RMGPocSelect option:selected").length) {
			$.each($("#RMGPocSelect option:selected"),
				function () {
					rmgPocIds.push($(this).val());
					RMGPocSelectData.push($(this).text());
			})
		}
		/*$('#RMGPocSelect').multiselect({
			includeSelectAllOption: true,
			id: 'RMGPocSelect',
			noneSelectedText: 'Select RMG POC(s)'
		}).multiselectfilter();*/

		$('#RMGPocSelect').bind(
			'change multiselectcheckall multiselectuncheckall',
			function (ev) {
				RMGPocSelectData = $.map($(this).find('option'),
					function (el, i) {
					if (el.selected) {
						rmgPocIds = $('#RMGPocSelect').val();
						return $(el).text();
					}
				});
				if ((ev && ev.type === 'multiselectuncheckall') || !RMGPocSelectData.length) {
					rmgPocIds = []
				}
		});
		
		var techTeamPocSelectData = [];
		var tecTeamIds = [];
		if ($("#TecTeamSelect option:selected").length) {
			$.each($("#TecTeamSelect option:selected"),
				function () {
				tecTeamIds.push($(this).val());
				techTeamPocSelectData.push($(this).text());
			})
		}
		/*$('#TecTeamSelect').multiselect({
			includeSelectAllOption: true,
			id: 'TecTeamSelect',
			noneSelectedText: 'Select TAC Team POC(s)'
		}).multiselectfilter();*/


		$('#TecTeamSelect').bind(
			'change multiselectcheckall multiselectuncheckall',
			function (ev) {
				techTeamPocSelectData = $.map($(this).find('option'),
					function (el, i) {
					if (el.selected) {
						tecTeamIds = $('#TecTeamSelect').val();
						return $(el).text();
					}
				});
				if ((ev && ev.type === 'multiselectuncheckall') || !techTeamPocSelectData.length) {
					tecTeamIds = []
				}
		});
	
	// RMG Poc and Tec Team Poc new Changes End
});



function setTabStatus(status, tabIdx) {
	$('.nav_step' + tabIdx + ' a .status').text(status);
	if (status === 'pending') {
		$('.nav_step' + tabIdx).addClass("disabled");
	} else {
		$('.nav_step' + tabIdx).removeClass("disabled");
	}
	// $('.nav_step'+tabIdx + ' a .status').text(status);
} 