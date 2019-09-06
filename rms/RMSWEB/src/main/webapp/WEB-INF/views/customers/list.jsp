<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<!-- Add javaScript file for DataTable -->

<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>

<spring:url
	value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url
	value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />

<script type="text/javascript" charset="utf-8">


/* var idArray = new Array();
$('.groupTbl').on("click", ".removePo", function(){
	 	alert("Inside Delete");
		 var conf=confirm("Are you sure you want to delete?");	 
		 var id =  $(this).closest("tr").attr('id');
		 idArray.push(id);
		 
		  if(conf){
			 $(this).closest("tr").remove();	
			 
		 } 
		 return;				    	
	
 }); */
</script>


<script>
		// Wait until the DOM has loaded before querying the document
		var grpTableLength;
		var custId;
		var oTable;
		$(document).ready(function(){

			/* Add the events etc before DataTables hides a column */
			$("thead input")
					.keyup(
							function() {
								/* Filter on the column (the index) of this element */
								oTable
										.fnFilter(
												this.value,
												oTable.oApi
														._fnVisibleToColumnIndex(
																oTable
																		.fnSettings(),
																$(
																		"thead input")
																		.index(
																				this)));
							});
			
			

			/*
			 * Support functions to provide a little bit of 'user friendlyness' to the textboxes
			 */
			$("thead input").each(function(i) {
				this.initVal = this.value;
			});

			$("thead input").focus(function() {
				if (this.className == "search_init") {
					this.className = "";
					this.value = "";
				}
			});

			$("thead input").blur(function(i) {
				if (this.value == "") {
					this.className = "search_init";
					this.value = this.initVal;
				}
			});
			 //DataTable page state remain on previous state Issue Solved (Arun)
			oTable = $('#addGroupTbl')
					.DataTable();
			
			/*for customer manger*/
			$(".errorNumeric").hide();
			$(document).on("keydown",".numericInp",function(event){  
				$(".errorNumeric").hide();
                if(event.shiftKey)
                    event.preventDefault();                    
                if (event.keyCode == 46 || event.keyCode == 8) {
                	
                } else {
                 
                	if (event.keyCode < 95) {  
                        if (event.keyCode<48 || event.keyCode>57) {
                        	$(this).parent().parent("td").find(".errorNumeric").fadeIn("slow");
                            event.preventDefault();
                        }
                        else
                        	{
                         
                        		$(".errorNumeric").fadeOut("fast");
                        	}
                    } else {  
                    	
                        if (event.keyCode<96 || event.keyCode>105) {
                        	 
                            event.preventDefault();
                        }
                    }
                }
			});
			
			$(".errorNumeric").hide();
			$(document).on("keydown",".stringInp",function(event){  
				$(".errorNumeric").hide();
                if(event.shiftKey)
                    event.preventDefault();                    
                if (event.keyCode == 46 || event.keyCode == 8) {
                	
                } else {
                 
                	if (event.keyCode > 95) {  
                        if (event.keyCode>48 || event.keyCode<57) {
                        	$(this).parent().parent("td").find(".errorNumeric").fadeIn("slow");
                            event.preventDefault();
                        }
                        else
                        	{
                         
                        		$(".errorNumeric").fadeOut("fast");
                        	}
                    } else {  
                    	
                        if (event.keyCode>96 || event.keyCode<105) {
                        	 
                            event.preventDefault();
                        }
                    }
                }
			});
			
			
			
			
			
			
			
			
			$("input.placeHolder").focus(function(){
				if ($(this).val()=='Only Numeric Numbers'){
					//$(this).val('0');
					$(this).val('').css({"color":"#000", "font-size":"13px"});	
				}
				}).blur(function() {
		        if($(this).val()==''){
						$(this).val('Only Numeric Numbers').css({"color":"#373737","font-size":"12px"});
				}
	   		});
			
			
		$(".tab_div").hide();
		$('ul.tabs a').click(function () {
			$(".tab_div").hide().filter(this.hash).show();
			$("ul.tabs a").removeClass('active');
			$('a[href$="tab2"]').addClass('MaintenanceTab');
			$(this).addClass('active');
			return false;
		}).filter(':first').click();
		
		var customerTable = $('#customerTableId').dataTable({
			"bStateSave": false,
			"sScrollX": "100%",
			"sScrollY": "350",
			"bScrollCollapse": true,
	        "paging":         false,
			"sPaginationType": "full_numbers",
			"sDom" : '<"top"lfi>rt<"bottom"ip<"clear">',
			// "sDom": '<"projecttoolbar">lfrtip<"top">rt<"bottom"ip<"clear">'
		});
		
		/* var addGroupTbl = $('#addGroupTbl').dataTable({
			"bStateSave": true,
			"sPaginationType": "full_numbers",
		//"sDom": '<"projecttoolbar">lfrtip'
			"sDom": '<"projecttoolbar">lfrtip<"top">rt<"bottom"ip<"clear">'
		}); */
		
		/* function initTable()
		{
			 
			
			$('#customerTableId').dataTable({
				"bStateSave": true,
				"sPaginationType": "full_numbers",
				"sDom": '<"projecttoolbar">lfrtip<"top">rt<"bottom"ip<"clear">',
				
				//"sDom": '<"projecttoolbar">lfrtip<"top"lfip>rt<"bottom"ip<"clear">',
				"bDestroy": true
			});
			<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
			$("div.projecttoolbar").html(
					'<div class="btnIcon CustomerAddIcon">'+
						'<a href="#" class="blue_link" id="addNew" >'+
							'<img src="resources/images/addUser.gif" width="16" height="22" /> '+
						'Add New </a>'+
					'</div>');
			</sec:authorize>
			
			 
			 
		} */
		
		
		
		/*function addTotal(){
			total=0;
			//var clsName=$(this).parent().parent().attr('class');
			$("table#addPoTbl tbody tr td").find("input[type=text].inpCost").each(function(){
				total += Number($(this).val());
			});
			
		};
		
		*/
		
		<sec:authorize access="hasAnyRole('ROLE_ADMIN')"> 
		$("div.projecttoolbar").html(
				'<div class="btnIcon customerBtnIcon">'+
					'<a href="#" class="blue_link" id="addNew" >'+
						'<img src="resources/images/addUser.gif" width="16" height="22" /> '+
					'Add New </a>'+
				'</div>');
		</sec:authorize>
	  		customerTable.$('tr').dblclick( function () {
	  		    var sData = customerTable.fnGetData(this);
	  		    openMaintainance(($(this).attr('id')));
	  		    	
	  		});
	  		
	  		 $('a[href$="tab1"]').click(function(){
	  			$(".dataTables_length").css({"display":"block"});
		 		$(".dataTables_filter").css({"display":"block"}); 
		 		isAddEnable=true;
		 		isEditable=true;
			}); 
	  		 
	  		/*$('a[href$="tab2"]').click(function(){
				$("#customerForm").reset();
				$(':input','#customerForm')
				 .not(':button, :submit, :reset, :hidden')
				 .val('')
				 .removeAttr('checked')
				 .removeAttr('selected');
				});*/
				
				
				
				$('a[href$="tab2"]').click(function(){			
					$("#customerForm").reset();
				
					$("table#addPoTbl tbody").html("");
					
				});

				$('#MaintenanceTabInactive').off('click');		
				
				
				
				
				
				
	  		    $('#addNew, #update').on("click",function(){
	  		    	
	  		    $("#addGroupMainWithTooltip").css({"display":"block"});
	  		  	$("#addGroupMainWithoutTooltip").css({"display":"none"}); 
	  		    $(".dataTables_length").css({"display":"none"});
			 	$(".dataTables_filter").css({"display":"none"});
				$("#customerForm").reset();
 				$("#customerForm input[name=id]").val(-1);
				$("table#addGroupTbl tbody").html("");
				displayMaintainance();
				
				/*$(':input','#customerForm')
				 .not(':button, :submit, :reset, :hidden')
				 .val('')
				 .removeAttr('checked')
				 .removeAttr('selected');
			*/
			});

	  		
	  		/*---------------Place Holder-------------*/
	  		$("input.placeHolderInp").focus(function(){
				if ($(this).val()=='Only Numeric Value'){
					$(this).val('');	
				}
				//alert($(this).attr())
				
			}).blur(function() {
	        if($(this).val()==''){
					$(this).val('Only Numeric Value');
			}
	        //$(this).text('password', $(this).val(''));
	    });
	  		
	  		
$( "#save" ).click(function(event) {
	  			
	            document.getElementById('mCusotmerID').value= $.trim($("#mCusotmerID").val());
				document.getElementById('mGeography').value= $.trim($("#mGeography").val());
				document.getElementById('mAaccountManager').value= $.trim($("#mAaccountManager").val());
                document.getElementById('mCusotmerName').value= $.trim($("#mCusotmerName").val());
                document.getElementById('mCustomerEmail').value= $.trim($("#mCustomerEmail").val());
                document.getElementById('mAccountManagerContactNumber').value= $.trim($("#mAccountManagerContactNumber").val());
                
                 var mCusotmerName = document.getElementById('mCusotmerName').value= $.trim($("#mCusotmerName").val());
                var mCusotmerID = document.getElementById('mCusotmerID').value= $.trim($("#mCusotmerID").val());
				var mGeography = document.getElementById('mGeography').value= $.trim($("#mGeography").val());
				var mAaccountManager = document.getElementById('mAaccountManager').value= $.trim($("#mAaccountManager").val());
			    var custEmail= document.getElementById('mCustomerEmail').value= $.trim($("#mCustomerEmail").val());
              
			    if(mCusotmerID == null || mCusotmerID == ""){
			    	var errorMsg = "Please enter Customer Code";
        			showError(errorMsg);
        			return false;
			    }
			    else if(mCusotmerName == null || mCusotmerName =="" ){
	        		var errorMsg = "Please enter CustomerName";
	        			showError(errorMsg);
	        			return false;
	        	}
			    else if(mGeography == null || mGeography == "" ){
			    	var errorMsg = "Please enter GeographyName";
        			showError(errorMsg);
        			return false;
			    }
			    	else if( mAaccountManager == null || mAaccountManager == "" ){
			    		var errorMsg = "Please enter AccountManagerName";
	        			showError(errorMsg);
	        			return false;
			    	}
			   
                 	if($("input.placeHolder").val()=='Only Numeric Numbers'){
					$("input.placeHolder").val('0').css({"color":"#000", "font-size":"13px"});	
				}
	  	    
	  		    var accountNumber = document.getElementById("mAccountManagerContactNumber").value;
	  			accountNumber=accountNumber.trim();
	  			accountNumber=accountNumber.trim();
	  			 var phoneRegExp = 
	  		        /^((\+)?[1-9]{1,2})?([-\s\.])?((\(\d{1,4}\))|\d{1,4})(([-\s\.])?[0-9]{1,12}){1,2}$/;
	  		        
	  		   if(accountNumber != null && accountNumber != "" && accountNumber != 0){
	  			 if(!phoneRegExp.test(accountNumber)){
	  				document.getElementById('mAccountManagerContactNumber').style.border = "solid 1px red";
	  				 showError("Please enter only numeric values into Account Manager Contact number field");
	  				 return false; 
	  			 } if(accountNumber.length<10 || accountNumber.length>10){
	  				document.getElementById('mAccountManagerContactNumber').style.border = "solid 1px red";
	  				 showError(" Account Manager Contact number field should be 10 digits");
	  				 return false; 
	  			 }
	  		   }     
	  			
	  		   
	  		   if(idArray.length > 0){
	  				for(var i=0; i<idArray.length; i++){
	  				var id = idArray[i];
	  			  $.deleteJson_('customerpoes/'+id,{}, function(data){
						 //refreshGrid();
					   }, 'json');
	  				}	
	  				}
	  		   
	  		    var successMsg ="Customer details have been saved successfully";
				var regExp = /^[a-zA-Z ]+$/;
	  			var nameRegExp = /^[ A-Za-z0-9_@./#&$%!*:;`+\-,\[\]\(\)/\\/']*$/;
	  			var regExpEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	  		    
	  			if(mCusotmerName != null || mCusotmerName != "" || mGeography != null || mGeography != "" || mAaccountManager != null || mAaccountManager != ""){
	  				if(!mCusotmerName.match(nameRegExp)){
	  					var errorMsg = "Please enter Customer Name properly";
	  					showError(errorMsg);
	  					return false;
	  				}else
	  				 if((custEmail != null && custEmail != "") && !custEmail.match(regExpEmail)){
	  					var errorMsg = "Please enter Customer Email properly";
	  					showError(errorMsg);
	  					return false;
	  				}else
	  				 if(!mGeography.match(regExp) ){
	  					var errorMsg = "Please enter Geography properly";
	  					showError(errorMsg);
	  					return false;
	  				}else
	  				 if(!mAaccountManager.match(nameRegExp) ){
	  					var errorMsg = "Please enter Account manager properly";
	  					showError(errorMsg);
	  					return false;
	  				}

	  			
	  			 if(emailid != null){
                emailid=emailid.trim();
                }
                if(custEmail != null){
                	custEmail=custEmail.trim();
                }
                if((emailid != custEmail)){
                if (custEmail != null && custEmail != '') {
                   if (!validateDuplicates(custEmail)) {
						return;
					} 
                  }
                }
                   $.putJson('customers', $("#customerForm").toDeepJson() , function(data) {
						 if(data!= null && data.status == "FALSE"){
						var errorMsg ="Customer Code already exist, please provide different Customer Code";
						showError(errorMsg); 
						stopProgress();
					} else {	
						stopProgress();
						 showSuccess(successMsg); 
						 location.reload();	
					} 			
				}, 'json');
                	 
                 }
	  		
      });
	  		
	  	$("#addGroup").on("click",function(){
	  			if(isAddEnable && isEditable){
	  			var mCusotmerID = document.getElementById('mCusotmerID').value= $.trim($("#mCusotmerID").val());
				var mGeography = document.getElementById('mGeography').value= $.trim($("#mGeography").val());
				var mAaccountManager = document.getElementById('mAaccountManager').value= $.trim($("#mAaccountManager").val());
	            var mCusotmerName = document.getElementById('mCusotmerName').value= $.trim($("#mCusotmerName").val());
	           
	  				grpTableLength=$("table#addGroupTbl tbody").find("tr").length+1;
				    $(".groupTbl table tbody:last").append($("#groupTableRows").render());
					//$(".groupTbl table tbody").prepend($("#groupTableRows").render());
				    
					var x = 2;
					if (x % 2 == 0){
						$(".groupTbl table tbody tr:even").addClass('even');
					}
					//add flage for enable/disable editing
					isAddEnable=false;
					//isEditable=false;
					//document.getElementById('rowNo').innerHTML=grpTableLength;		
	  			
	  			}
	  			else{
	  				var errorMsg = "Please Save/Cancel before add another row";
					showError(errorMsg);	
				}
			});
	  		
	  		
	  		//This code was for DeActive CustomerGroup
	  		//This code Commented because we are using single Action for Active/DeActive CustomerGroup
	  		
	  		/* $('#addGroupTbl').delegate('a.deactivate', 'click', function(e) {
				e.preventDefault();
				
				var nRow = $(this).parents('tr')[0];

				var id = $(nRow).attr('id');
				
				var custGroupEmailColumn = $(nRow).find("td:eq(2)").html();
				var custGroupEmail=getInputValue(custGroupEmailColumn);
				
				/* if (saveOpen) {

					var text = "Please enter and save the data";
					showAlert(text);

					e.preventDefault();
					return;
				} 
				
				//DeActive Customer Group pdl mail deactive
                  $.ajax({
					async : true,

					contentType : "application/json",
					//For deActive custGroupEmail in PDL table --(Arun)
                    //url : "custgroups/" + "N" + "/" + id,
					url : "custgroups/" + "N" + "/" + id,
					data: "custGroupEmail="+custGroupEmail,
				
					//data:   id ,
					success : function(data) {
						showSuccess("Successfully DeActivated");
						openMaintainance(custId);

					}
				});

			}); */
	  		
			
			//This code was for DeActive CustomerGroup
	  		//This code Commented because we are using single Action for Active/DeActive CustomerGroup
	  		
	  		/* $('#addGroupTbl').delegate('a.activate', 'click', function(e) {
				e.preventDefault();
                var nRow = $(this).parents('tr')[0];
			 	var id = $(nRow).attr('id');
				
				/* if (saveOpen) {

					var text = "Please enter and save the data";
					showAlert(text);

					e.preventDefault();
					return;
				} */
				
				//Active DeActive Customer Group - Issue solved(Arun) 
				/* var isCustGroupExist = false;
				var custGroupColumn = $(nRow).find("td:eq(1)").html();
				var custGroupName=getInputValue(custGroupColumn); */
			
				// We Are remove GroupEmail field 
			    //var custGroupEmailColumn = $(nRow).find("td:eq(2)").html();
			   //var custGroupEmail=getInputValue(custGroupEmailColumn);
			   
			/* 	var parentId=custId.toString();
				alert("parentId________"+parentId);
				$.ajax({
					async : false, // !important
					url : "custgroups/validate",
					data : "custGroupName=" + custGroupName + "&custId=" + parentId,
					//+ "&custGroupEmail=" + custGroupEmail,
					success : function(data) {
						var data1 = JSON.parse(data);
						if (data1.result == true) {
							isCustGroupExist = true;
						}
					}
				});
				
				if(!isCustGroupExist)
				{
				$.ajax({
					async : true,

					contentType : "application/json",
					url : "custgroups/" + "Y" + "/" + id,
					//data:   id ,
					success : function(data) {
						showSuccess("Successfully Activated");
						openMaintainance(custId);

					}
				});
				}
				else
				{
					showError(" Cannot activate as CustomerGoup Name"
							+ " \""
							+ custGroupName
							+ "\" is already present.");

				}
			});  */
			
			
			
			//The below code for Active/DeActive customerGroup
			
			$('#addGroupTbl').delegate('a.groupStatusValue', 'click', function(e) {
				e.preventDefault();
			 if (isEditable == false || isAddEnable == false ) {
				  var text = "Please Save/Cancel before Active/DeActive";
				  showError(text);	
				  return false;
				  stopProgress();
				}
				else{
				var nRow = $(this).parents('tr')[0];
				var id = $(nRow).attr('id');
				var custGroupColumn = $(nRow).find("td:eq(1)").html();
				var groupStatusValue = $(nRow).find("td:eq(3)").html();
				var groupStatus=getInputValue(groupStatusValue);
				var custGroupName=getInputValue(custGroupColumn);
			    var parentId=custId.toString();
			    
			  	$.ajax({
					 async : true, 
					 contentType : "application/json",
					 url : "custgroups/" +groupStatus+ "/" + id,
				     data: "custGroupName="+custGroupName+"&custId=" + parentId,
				     success : function(data) {
				    	if(groupStatus=="N"){
				    		showSuccess("Successfully Activated");
							openMaintainance(custId);
				    	}else
						showSuccess("Successfully DeActivated");
						openMaintainance(custId);
                     }
				});
				
			}
			});
	  		
	  		function getInputValue(str) {
				var index1 = str.indexOf("<INPUT");
		 
				var index = str.indexOf("<input");
				if (index < 0 && index1 < 0) {
					return null;
				}

				if (index < 0) {
					str = str.substr(index1, str.length);
					return $(str).val();
				}

				if (index1 < 0) {
					str = str.substr(index, str.length);
					return $(str).val();
				}
			}
	  		
	  		var idArray = new Array();
	  		$('.groupTbl').on("click", ".delete", function(){
	  				 var conf=confirm("Are you sure you want to delete?");	 
	  				 var id =  $(this).closest("tr").attr('id');
	  				 idArray.push(id);
	  				 
	  				  if(conf){
	  					 $(this).closest("tr").remove();	
	  					isAddEnable=true;
	  				 } 
	  				 return;				    	
	  			
	  		 });

		});
		
	
		
		/*--------------------PO table-------------*/
		$("#addPo").on("click",function(){				
				$(".poTbl table tbody:last").append($.render.poTableRowsTempl());
				
				var x = 2;
				if (x % 2 == 0)
				{
					$(".poTbl table tbody tr:even").addClass('even');
				}
		});
		
		
		$("#addPoTbl").find("input[type=text].inpCost").on("blur", function(){
			/*var cost = $(this).val();
			if(cost != "" && !cost.match(/^[-+]?[0-9]+$/)) {
				alert("Cost should be numeric value only");
				$(this).focus();
				return;
			}*/
		
		});
		
		
		/* delete PO */
		
		/*
		$('.removePo').live("click", function(){				
				 var conf=confirm("Are you sure you want to delete this PO?");	 
				 var id =  $(this).closest("tr").attr('id');
				 if(conf){
					 startProgress();
					 $(this).closest("tr").remove();							 
					  //$.deleteJson("customerPo/"+id,'json');
					  $.deleteJson_('customerpoes/'+id,{}, function(data){
							 //refreshGrid();
						   }, 'json');
					  stopProgress();
				 }
				 return;
				 				    	
			});
		
		
		*/
		
		
		function openMaintainance(id){
			custId=id;
			  startProgress();
			getCustomerById(id);
			displayMaintainance();
			stopProgress();
		}
		
		function getCustomerById(id){
			$.getJSON("customers/"+id,{}, showCustomer);
			
		}
		var emailid=null;
		function showCustomer(data){
			$("#addGroupMainWithTooltip").css({"display":"none"});
	  		$("#addGroupMainWithoutTooltip").css({"display":"block"}); 
			$("#customerForm").populate(data, {debug:false, resetForm:true});
			$(".poTbl table tbody").find("tr").remove();
			$(".poTbl table tbody:last").append($("#poTableRowsWithValues").render(data.customerPoes));
			$(".groupTbl table tbody").find("tr").remove();
			//$(".groupTbl table tbody:last").append($.render.groupTableRowsWithValuesTempl(data.custGroups));
			$(".groupTbl table tbody:last").append($("#groupTableRowsWithValues").render(data.custGroups));
			$(".dataTable > tbody > tr:even").addClass("even");
	 		$(".dataTable > tbody > tr:odd").addClass("odd");
	 		if($("input.placeHolder").val()=='0'){
				$("input.placeHolder").val('Only Numeric Numbers').css({"color":"#000", "font-size":"10px"});	
			}
	 		 $("#addGroupTbl_length").css({"display":"none"});
	 		$("#addGroupTbl_filter").css({"display":"none"});
	 		$(".dataTables_info").css({"display":"none"});
	 		$("#addGroupTbl_paginate").css({"display":"none"}); 
	 		
	 		 emailid = document.getElementById('mCustomerEmail').value;
		}
		
		function displayMaintainance(){
			$("ul.tabs a").removeClass('active');
			$(".tab_div").hide().next("#tab2".hash).show();
			$('a[href$="tab2"]').addClass('active');
			$('a[href$="tab2"]').removeClass('MaintenanceTab');
			<sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER')">
			$('form#customerForm input[type="text"]  ,textarea').attr("readonly","readonly");
			$('form#customerForm input[type="number"]').attr("readonly","readonly");
			$('form#customerForm input[type=checkbox]').attr("disabled","disabled");
			</sec:authorize>
			
		}
		
		
// 		function saveCustomer(){
// 			alert("hiii");
// 			var pk = $("#customerForm input[name=id]").val();
// 			alert ("PK : " + pk);
// 			var form_data = $( "#customerForm" ).trigger( "submitForm" );
// 		   alert ("form_data : " + form_data);
// 			//not doing any validation .. We will use jquery validation framework to do that for us before form gets submitted
// 			 var successMsg ="Customer details have been saved successfully";
// 		//	 validationFlag = false;
// 			 alert ("successMsg : " + successMsg);
// 			 var poNumberArray = new Array();
// 				var rowCount = $('#addPoTbl >tbody >tr ').length;
// 				alert(rowCount);
// 				var temp=0;
// 				var inpSelect = $('#addPoTbl >tbody >tr ').find("td:nth-child(1) input");
// 				$(inpSelect).each(function(){
// 					var tdInpVal = $(this).val();
// 					alert(tdInpVal);
// 					poNumberArray.push(tdInpVal);
// 				});
		
			
// 				 /*	if(rowCount>1){
// 					alert(rowCount);
// 					for(var count=0 ;count<poNumberArray.length ; count++ ){
// 						for(var count1=count+1;count1<poNumberArray.length ; count1++ ){
// 							if(poNumberArray[count] && poNumberArray[count1]){
// 								if(poNumberArray[count] == poNumberArray[count1]){
// 								 	temp++;
// 							    }
// 							 }
// 						 }
// 					 }
// 					if(temp > 0){				
// 						validationFlag = false;
// 						errorMsg =  errorMsg+"\u2022 Duplicate entries of PO Number is not Allowed<br /> ";
// 						//showError(errorMsg);
// 						//stopProgress();
// 					}
// 				}  */ 
			
// 			if(form_data){
// 				if(checkGeographyCustomerName()){
// 					alert ("checkCustomerCode ");
// 					if(checkCustomerCode()){
// 						alert ("checkCustomerCode ");
// 				startProgress();
// 			if( pk != null && pk > 0){
// 				alert ("inside pk if  ");
// 				 $.putJson('customers', $("#customerForm").toDeepJson() , function(data) {					
// 				if(data!= null && data.status == "FALSE"){
// 					var errorMsg ="Customer Code already exist, please provide different Customer Code";
// 					showError(errorMsg); 
// 					stopProgress();
// 				} else {
// 					 stopProgress();
// 					 showSuccess(successMsg); 
// 					 location.reload();	
// 				} 			
// 			}, 'json');
// 			} else {
// 				alert ("inside pk else  ");
// 				 $("#customerForm input[name=id]").val();
// 				 alert ("inside pk else  11111 ");
// 				 $.postJson('customers',$("#customerForm").toDeepJson() , function(data){
// 					 alert ("postJson ");
// 					 //should call refreshGrid instead of location reload, and update customerTableId table.
// 					 if(data!= null && data.status == "FALSE"){
// 						var errorMsg ="Customer Code already exist, please provide different Customer Code";
// 						showError(errorMsg);
// 						stopProgress();
// 					 } else {
// 						 stopProgress();
// 						 showSuccess(successMsg); 
// 						 location.reload();	
// 					}
// 				}, 'json'); 				
// 			}
// 				}
// 		}
			
// 	}
//}
		
		function refreshGrid(){
			$.getJSON("customers", function(data){
				
			});
		}
</script>


<script id="poTableRows" type="text/x-jsrender">
 		           <tr class="costRow" >
			         <td >
                       <div class="positionRel">
		                 <input type="text" id="customerNumber" name="customerPoes[{{:~GetRowId()}}].customerNumber" class="required string numericInp"/>
		                 <span class="errorNumeric" style="display: none;">Please Enter Numeric Numbers Only <img src="images/errorAerrow.png"></span>
                       </div>				
			        </td>
	    	        <td width="40%">
				       <input type="text" id="poPosition[{{:~GetRowId()}}]" name="customerPoes[{{:~GetRowId()}}].position" class="string required"/>
				       <span class="errorNumeric" style="display: none;">Please Enter alphabets Only <img src="images/errorAerrow.png"></span>
			       </td>
			       <td width="20%">
				      <input type="text" id="poDescription" name="customerPoes[{{:~GetRowId()}}].description" class="string required" />
			      </td>
			      <td>

<select id="poStatus" name="customerPoes[{{:~GetRowId()}}].status" >
								
										<option value="Open">Open</option>
										<option value="Deffered">Deffered</option>
									<option value="Closed">Closed</option>
									 
							</select>



			
				
			</td>
		<td width="5%" align="center">
				<a href="javaScript:void(0)"><img width="16" height="16" class="removePo" src="resources/images/remove.png"></a>
			</td>
			
	    </tr>

</script>

<script>

function getCustomerPoById(id){
	//alert("getCustomerPoById");
	$.getJSON("/customerPos"+id,{}, showcustomerPO);
}
function showcustomerPO(data){
	//alert(showcustomerPO);
	$("#customerForm").reset();
	$("#poTableRowsWithValues").render(data.customerPoes);
	

}

</script>


<script>

var data={
		getCostRowId: function(arg){
			return 	$("table#addGroupTbl tbody").find("tr").length;			
		}
};

$.views.helpers({ GetRowId: data.getCostRowId });
$.templates("poTableRowsTempl", {
    markup: "#poTableRows"
});
$.templates("groupTableRowsTempl", {
    markup: "#groupTableRows"
});
$.templates("groupTableRowsWithValuesTempl", {
    markup: "#groupTableRowsWithValues"
    
});

</script>


<script id="poTableRowsWithValues" type="text/x-jsrender">
<tr class="costRow" id="{{>id}}">

							<td>
<div class="positionRel">
							<input type="text" id="customerNumber" name="customerPoes[{{:#index}}].customerNumber"   value="{{>customerNumber}}" class="required string numericInp"/>
							<span class="errorNumeric" style="display: none;">Please Enter Numeric Numbers Only <img src="images/errorAerrow.png"></span>
</div>
							</td>

	                		<td width="40%">
								<input type="hidden" id="poId" name="customerPoes[{{:#index}}].id" value="{{>id}}"/>
								<input type="text" id="poPosition[{{:#index}}]" name="customerPoes[{{:#index}}].position"  value="{{>position}}" class="string required"/>
								<span class="errorNumeric" style="display: none;">Please Enter alphabets Only <img src="images/errorAerrow.png"></span>
							</td>
							<td width="20%">
								<input type="text" id="poDescription" name="customerPoes[{{:#index}}].description"   value="{{>description}}" class="string required"/>
								
							</td>
							<td>

<select id="poStatus" name="customerPoes[{{:#index}}].status" >
								
										<option value="{{>status}}">{{>status}}</option>
								<option value="Open">Open</option>
										<option value="Deffered">Deffered</option>
									<option value="Closed">Closed</option>
									 
							</select>


							
							</td>
							
													
							<td width="5%" align="center"><a href="javaScript:void(0)"><img width="16" height="16" class="removePo" src="resources/images/remove.png"></a></td>
	                	</tr>
 					
</script>

<script id="groupTableRows" type="text/x-jsrender">
 		<tr id="{{:~GetRowId()+1}}" class="costRow">
			<td width="10%" id="rowNo" align="center"><div class="positionRel">
				</div>{{:~GetRowId()+1}}
			</td>
	    	<td width="30%">
				<input type="text" id="poGroupName" name="custGroups[{{:~GetRowId()}}].groupName" style="width: 100%;"/>
			</td>
            <!-- We Are remove GroupEmail field -->
			<!-- <td width="30%">
				<input type="text" id="poGroupEmail" name="custGroups[{{:~GetRowId()}}].groupEmail" style="width: 100%;"/>
			</td> -->
<td width="18%" align="center">
				<a href="javaScript:void(0)" class="edit" id="save">Save</a>
			</td>
<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')">
			<td width="18%" align="center">
				<a href="javaScript:void(0)" class="delete">Delete</a>
			</td>
</sec:authorize>
	    </tr>

</script>

<script id="groupTableRowsWithValues" type="text/x-jsrender">
 						<tr class="costRow" id="{{>groupId}}">
	`					 <td width="10%" align="center">{{:#index+1}}
							<input type="hidden" id="poGroupId" name="custGroups[{{:#index}}].groupId" value="{{>groupId}}"/>
							</td>
	                		<td width="30%">
								<input style="display: none;font-weight:100" type="text" id="poGroupName" name="custGroups[{{:#index}}].groupName" value="{{>groupName}}" style="width: 100%;"/>
								<label style="font-weight:100" id="customerGroupName" name="customerGroupName">{{>groupName}}</label>
							</td>
                           <!-- We Are remove GroupEmail field -->
							<!-- <td width="30%">
								<input style="display: none;font-weight:100" type="text" id="poGroupEmail" name="custGroups[{{:#index}}].groupEmail" value="{{>groupEmail}}" style="width: 100%;"/>
								<label style="font-weight:100" id="customerGroupEmail" name="customerGroupEmail">{{>groupEmail}}</label>
							</td> -->
                      		<td width="18%" align="center" >
							<div id="editTableRow">
								<input type="hidden" id="edit" name="" value="{{>edit}}"/>
								<a style="display: block;" class="edit editbtn" href="" id="{{>groupId}}" name="">Edit</a>
								<a style="display: none;" id="save" class="edit" href="javascript:void(0);"  >Save</a><a style="display: none;" href="javascript:void(0);" id="cancel" class="cancel">/ Cancel</a>
							</div>
							</td>

          <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')">
							<td width="18%" align="center">
							<input type="hidden" id="groupStatus" name="custGroups[{{:#index}}].active" value="{{>active}}"/>
								{{if active == "N"}}
			  						<a class="groupStatusValue" href="" id="{{>groupId}}" name="custGroups[{{:#index}}].active">Activate</a>
								{{else}}
		 							<a class="groupStatusValue" href="" id="{{>groupId}}" name="custGroups[{{:#index}}].active">DeActivate</a>			              
								{{/if}}
							</td>
							</sec:authorize>
	                	</tr>

</script>

<script>
function checkCustomerCode(){
	var regex = /^[a-zA-Z0-9 ]+$/;
	var custCode = document.getElementById("mCusotmerID").value;
	if(custCode != null || custCode != ""){
		if(!custCode.match(regex)){
			var errorMsg = "Please enter Customer Code properly";
			showError(errorMsg);
			return false;
		}
		else
		{
			return true;
		}
	}
	return false;
}
function checkGeographyCustomerName(){
	//var regExp = /^[a-zA-Z ]+$/;
	var regExp = /^[ A-Za-z0-9_@./#&+-]*$/;
	var regExpEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	var custName = document.getElementById("mCusotmerName").value;
	
	var geo = document.getElementById("mGeography").value;
	
	var accountManager = document.getElementById("mAaccountManager").value;
	
	var custEmail = document.getElementById("mCustomerEmail").value;
	if(custName != null || custName != "" || geo != null || geo != "" || accountManager != null || accountManager != ""){
		/* if(!custName.match(regExp) || !geo.match(regExp) || !accountManager.match(regExp) ){
			var errorMsg = "Please enter Customer Name or Geography properly or Account manager";
			showError(errorMsg);
			return false;
		} */
		if(!custName.match(regExp)){
			var errorMsg = "Please enter Customer Name properly";
			showError(errorMsg);
			return false;
		}
		 if((custEmail != null && custEmail != "") && !custEmail.match(regExpEmail)){
			var errorMsg = "Please enter Customer Email properly";
			showError(errorMsg);
			return false;
		} 
		
		 if(!geo.match(regExp) ){
			var errorMsg = "Please enter Geography properly";
			showError(errorMsg);
			return false;
		}
		 if(!accountManager.match(regExp) ){
			var errorMsg = "Please enter Account manager properly";
			showError(errorMsg);
			//return false;
		}

		else if($('input[type="radio"]:checked').length <= 0) 
		{
			return true;
		}else{
			return true;
		}
	}
	return false;
}
</script>

<div class="content-wrapper">
	<!--right section-->
	<div class="botMargin">
		<h1>Customers</h1>
	</div>
	<div class="tab_seaction">
		<ul class='tabs'>
			<li><a href='#tab1'>List</a></li>
			<li><a class="MaintenanceTab" id="MaintenanceTabInactive"
				href='#tab2'>Maintenance</a></li>
			<li><span style="color: #0029FF;">* To Add new customer
					please request to ebiz_rmssupport@yash.com</span></li>
		</ul>
		<div id='tab1' class="tab_div">

			<sec:authorize access="hasAnyRole('ROLE_ADMIN')">

				<div class="btnIcon" id="add">

					<a id="addNew" class="blue_link" href="#">
						+ Add New
					</a> 
				</div>
				<!-- $("div.projecttoolbar").html(
								'<div class="btnIcon customerBtnIcon">'+
									'<a href="#" class="blue_link" id="addNew" >'+
										'<img src="resources/images/addUser.gif" width="16" height="22" /> '+
									'Add New </a>'+
								'</div>'); -->
			</sec:authorize>
			<div class="tbl"></div>
			<div class="clear"></div>
			<div id="customerTbl">
				<table class="dataTbl display tablesorter dataTable addNewRow alignCenter white-sort-icons my_table positiondashboard-table"
				 id="customerTableId" style="overflow: auto; display: inline-block;margin-top: 0px;">
					<thead>
						<tr>
							<th align="center" valign="middle">Customer Code</th>
							<th align="center" valign="middle">Customer Name</th>
							<th align="center" valign="middle">Geography</th>

							<th align="center" valign="middle">Account
								Manager</th>
							<th align="center" valign="middle">AC Manager
								Contact Number</th>
							<th  align="center" valign="middle">Customer
								Address</th>
							<th align="center" valign="middle">Customer
								Email</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach var="customer" items="${customers}">
							<tr id="${customer.id}">
								<td align="center" valign="middle"><a href="#"
									onclick="openMaintainance(${customer.id});">${customer.code}</a>
								</td>
								<td align="center" valign="middle"><c:if
										test="${empty customer.customerName}">N.A.</c:if> <c:if
										test="${not empty customer.customerName}">${customer.customerName}</c:if>

								</td>
								<td align="center" valign="middle"><c:if
										test="${empty customer.geography}">N.A.</c:if> <c:if
										test="${not empty customer.geography}">${customer.geography}</c:if>
								</td>


								<td align="center" valign="middle"><c:if
										test="${empty customer.accountManager}">N.A.</c:if> <c:if
										test="${not empty customer.accountManager}">${customer.accountManager}</c:if>
								</td>

								<td align="center" valign="middle"><c:if
										test="${empty customer.accountManagerContactNumber}">N.A.</c:if>
									<c:if test="${not empty customer.accountManagerContactNumber}">${customer.accountManagerContactNumber}</c:if>
								</td>
								<td align="center" valign="middle"><c:if
										test="${empty customer.customerAddress}">N.A.</c:if> <c:if
										test="${not empty customer.customerAddress}">${customer.customerAddress}</c:if>
								</td>
								<td align="center" valign="middle"><c:if
										test="${empty customer.customerEmail}">N.A.</c:if> <c:if
										test="${not empty customer.customerEmail}">${customer.customerEmail}</c:if>

								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<div class="clear"></div>
		</div>
		<div id='tab2' class="tab_div">
			<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
				<div class="search_filter">
					<div align="right">
						<a href="#" class="blue_link" id="save"> <img
							src="resources/images/save.png" name="save" width="16"
							height="22" /> Save
						</a>
					</div>
				</div>
			</sec:authorize>
			<div class="form">
				<form id="customerForm" name="customerForm" action="customers">
					<table border="0" cellpadding="0" cellspacing="5" width="100%">
						<tr>
							<td width="15%" align="left">Customer Code:<span
								class="astric">*</span>
							</td>
							<td width="18%" align="left"><input id="mCusotmerId"
								type="hidden" value="" name="id" /> <input id="mCusotmerID"
								type="text" value="" name="code" class="required string" /></td>
							<td width="15%" align="left">Customer name:<span
								class="astric">*</span></td>
							<td width="15%" align="left"><input id="mCusotmerName"
								type="text" value="" name="customerName" class="required"
								maxlength="100" /></td>
							<td width="18%" align="left">Geography:<span class="astric">*</span></td>
							<td width="20%" align="left"><input type="text"
								id="mGeography" value="" name="geography" class="required"
								maxlength="50" /></td>
						</tr>
						<tr>

							<td align="left">Account Manager:<span class="astric">*</span></td>
							<td align="left"><input type="text" value=""
								id="mAaccountManager" name="accountManager" class="required"
								maxlength="50" /></td>
							<td align="left">AC Manager Contact Number:</td>
							<td align="left">
								<div class="positionRel">
									<input type="text" id="mAccountManagerContactNumber"
										name="accountManagerContactNumber" maxlength="15"
										class="placeHolder" value="Only Numeric Numbers" />
								</div>
							</td>
						</tr>
						<tr>
							<td align="left">Customer Address:</td>
							<td align="left"><textarea rows="5" cols=""
									id="mCustomerAddress" name="customerAddress" maxlength="256"></textarea>
							</td>
							<td align="left">Customer Email:<span class="astric"></span></td>
							<td align="left">
								<div class="positionRel">
									<input type="email" id="mCustomerEmail" name="customerEmail"
										maxlength="256" value="" />
								</div>
							</td>
						</tr>
						<tr>

						</tr>
					</table>



					<div class="clear"></div>
					<!--<div class="poTbl">
						<a href="javaScript:void(0)" id="addPo" class="blue_link fr"><img
							height="22" width="16" src="resources/images/addUser.gif">Open 
							Position </a>
						<div class="clear"></div>
						<table class="tablesorter dataTable" id="addPoTbl">
							<thead>
								<tr>
									<th>Customer Number</th>
									<th>Position</th>
									<th>Description</th>
									<th>Status</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tbody>
								
							</tbody>
						</table>
						
					</div>
					<table><tbody id="details"></tbody></table>
					
					
					
					-->


				</form>
			</div>
			<div class="groupTbl">
			 
				<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
					<div id="addGroupMainWithTooltip">
					
					<a class="blue_link fr" data-toggle="tooltip" title="Please enter Customer details and save after then add Customer Group !"> 
						<img height="22" width="16" src="resources/images/addUser.gif"  />Add Group </a>
					</div>
					<div id="addGroupMainWithoutTooltip">
					
					<a href="javaScript:void(0)" id="addGroup" class="blue_link fr"> 
						<img height="22" width="16" src="resources/images/addUser.gif"  />Add Group </a>
					</div>
					
				</sec:authorize>

				<div class="clear"></div>
				<table class="dataTbl display tablesorter addNewRow alignCenter"
					id="addGroupTbl">
					<thead>
						<tr>
							<th>S No.</th>
							<th>Customer Group Name</th>
							<!-- We Are remove GroupEmail field -->
							<!-- <th>Customer Group Email</th> -->
							<th>Edit</th>
							<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')">
								<th>Delete</th>
							</sec:authorize>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</div>
			<!--<form name="sheetform" action="/report" id="sheetform">
		<input type="submit"   value="Download to excel">
		</form>-->
			<div class="clear"></div>
		</div>
		<script type="text/javascript">
		$("#customerForm").validVal({
			
				form: {
					onInvalid: function( $fields, language ) {
					
				   
				}
		 }
			});
		</script>
	</div>
	<!--right section-->
</div>
<!--START: Alert: Added by Pratyoosh Tripathi -->
<div class="notification-bar">
	<!-- <div class="close closeIconPosition">close</div> -->
	<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
	<!-- <div class="notification-text">Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.</div> -->
	<sec:authorize access="hasAnyRole('ROLE_BG_ADMIN')">
		<div class="notification-text">You can able to view the details
			of a Customers clicking on its Customer Id but unable to add a new.</div>


	</sec:authorize>

	<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
		<div class="notification-text">You can able to view or add a
			Customer into RMS clicking on "Add new link".</div>


	</sec:authorize>




</div>

<style>
.notification-bar {
	width: 400px;
	background: rgba(30, 159, 180, .8);
	padding: 10px;
	position: fixed;
	bottom: 30px;
	right: 25px;
}

.closeIconPosition {
	position: absolute;
	top: -10px;
	right: -20px;
}

.toast-close {
	float: right;
	display: block;
	width: 13px;
	height: 13px;
	padding: 0px 4px;
	border-radius: 3px;
	background: #000 url(/rms/resources/images/cross_icon_white.png)
		no-repeat center !important;
	z-index: 1000;
	position: absolute;
	top: -5px;
	right: -5px;
	opacity: .7 !important;
}

.notification-text {
	font-size: 14px;
	color: #fff;
	text-align: justify;
}
/*updated css added by kratika-06-5-19*/
table.dataTbl.display.tablesorter.addNewRow.alignCenter.my_table.tablefixed.dataTable
	{
	table-layout: auto;
}

table.dataTbl.display.tablesorter.addNewRow.alignCenter.my_table.dataTable
	{
	max-height: 751px;
	display: table !important;
}

table.dataTbl.display.tablesorter.addNewRow.alignCenter.my_table.dataTable
	{
	table-layout: fixed;
}

.my_table {
	border-collapse: collapse !important;
}

.my_table tbody tr.content:hover td {
	background: #c99e4d !important;
	cursor: pointer;
	transition: 0.5s;
}

.my_table tbody tr td.active {
	background: #dddd !important;
	color: #5189c2 !important;
}

.my_table thead th {
	background: #fff !important;
	color: #333 !important;
}

.positiondashboard-table .paging_full_numbers a.paginate_button,
	.paging_full_numbers a.paginate_active {
	/* border: 1px solid #aaa; */
	padding: 2px 5px;
	margin: 0 3px;
	cursor: pointer;
	*cursor: hand;
	color: #333 !important;
}

.positiondashboard-table .paging_full_numbers a.paginate_button,
	.paging_full_numbers a.paginate_active {
	/* border: 1px solid #aaa; */
	padding: 2px 5px;
	margin: 0 3px;
	cursor: pointer;
	*cursor: hand;
	color: #333 !important;
}

.positiondashboard-table {
	position: relative;
	border: 1px solid rgba(0, 0, 0, .12);
	border-collapse: collapse;
	font-size: 13px;
	background-color: #fff;
}

.positiondashboard-table thead {
	padding-bottom: 3px;
}

.positiondashboard-table th {
	vertical-align: middle;
	text-overflow: ellipsis;
	text-align: left !important;
	font-weight: 700;
	line-height: 24px;
	border-right: 0px solid #FFFFFF !important;
	letter-spacing: 0;
	border-radius: 0 !important;
	font-size: 12px;
	color: rgba(0, 0, 0, .54);
	padding-bottom: 8px;
	background: #fff !important;
	color: #333 !important;
	border: none;
	padding-left: 24px !important;
}

.positiondashboard-table td, .positiondashboard-table th {
	position: relative;
	height: 48px;
	box-sizing: border-box;
}

.positiondashboard-table td {
	border-top: 1px solid rgba(0, 0, 0, .12);
	border-bottom: 1px solid rgba(0, 0, 0, .12);
	padding-top: 12px;
	vertical-align: middle;
	text-align: left !important;
	background: #fff !important;
	border: 0px solid #FFFFFF !important;
	border-top: 1px solid rgba(0, 0, 0, .12) !important;
	border-bottom: 1px solid rgba(0, 0, 0, .12) !important;
	border-radius: 0 !important;
	padding-left: 24px !important;
	padding-right: 18px !important;
}

.positiondashboard-table tbody tr:hover {
	background-color: #ddd;
}

#customerTbl .dataTables_scroll {
	overflow-x: hidden !important;
}

#customerTbl .dataTables_scrollHead {
	width: 100% !important;
	 
}

#customerTbl .dataTables_wrapper .dataTables_info {
	width: 20%;
	margin-top: 9px;
}

.paging_full_numbers a.paginate_active {
    color: #fff !important;
    background-color: var(--nav-active) !important;
    font-weight: bold;
    border-radius: 50%;
    border: 1px solid var(--nav-active);
}

.paging_full_numbers a {
    margin: 0 !important;
    padding: 10px 14px !important;
    background: #fff !important;
    color: #999 !important;
}
.bottom {
    padding: 15px;
    background: #fff;
    position: relative;
}

 
div.dataTables_wrapper {
    background: #fff;
}
#customerTbl .btnIcon {
    left: 800px !important;
    position: absolute;
    top: 1px !important;
    z-index: 9;
}
.dataTables_paginate.paging_full_numbers {
    margin-bottom: 8px;
    float: left !important;
    margin-left: 165px !important;
}
#customerTbl #customerTableId_length.dataTables_length label select {
    padding: 5px;
    width: 60px;
    border-radius: 50px;
}
#customerTableId_length{
    padding-top: 11px;
}
.ProjectStatusdrdwn select, .ResourceStatusdrdwn select {
   padding: 5px;
    border-radius: 50px;
}
#tab1 a.blue_link{
    background: var(--nav-active);
    border: 1px solid var(--nav-active);
    border-radius: 50px;
    padding: 5px 26px;
    margin-right: 10px;
    color: #fff !important;
    text-decoration: none;
    margin-top: 5px;
    font-size: 13px;
    font-weight: 500;
}
#adminResourceTbl .projecttoolbar{
    float: right;
    width: calc(100% - 145px);
    position: relative;
    top: -21px;
}
.resource-status{
    float: left;
}
#resourceTableId_length {
     border-right:none !important;
}
#tab1 .btnIcon {
       position: static;
    float: right;
}
#customerTbl .dataTables_filter {
    position: absolute;
    border-left: 1px solid #ccc;
    left: 144px;
    top: 11px;
    z-index: 2;
    padding-left: 10px;
}
.dataTables_filter input {
    width: 300px !important;
    padding: 7px 18px 5px 15px !important;
    border-radius: 50px !important;
    box-shadow: unset;
    color: #999 !important;
    vertical-align: middle;
    border: 1px solid #ccc;
    position: relative;
    background: transparent;
    font-size: 12px !important;
}
#customerTableId_wrapper .dataTables_scrollBody {
    max-height: 700px;
}

</style>
<script type="text/javascript">
$( document ).ready(function() {
	
	//$('[data-toggle="tooltip"]').tooltip();
	
	//setInterval(function(){ $('[data-toggle="tooltip"]').fadeToggle(500); }, 1000);
	
	<%Boolean flag = false;
			flag = (Boolean) session.getAttribute("notificationbarflag");%>
	
	
		$('.close').click(function(){
		    $('.notification-bar').hide();
		   });
		});
          //add flage for enable/disable editing
           var isEditable=true;
           var isAddEnable=true;
           
          //On Click Cancel called here
          
	      $(document).on('click','#addGroupTbl a.cancel', function(e) {
		  var $row = $(this).closest('tr');
		  $row.find('#poGroupName').css("display", "none");
		  $row.find('#customerGroupName').css("display", "block");
		  $row.find('#poGroupName').val('');
		  $row.find('#poGroupName').val($row.find('#customerGroupName').text());
		  $(".editbtn").css("display", "block");
    	  $row.find('#save').css("display", "none");
    	  $row.find('#cancel').css("display", "none");
    	  $row.find('#poGroupName').css("border", "");
    	  isEditable=true;
    	  isAddEnable=true;
	    });
 
          
	    //CustomerGroup Save/Edit Action code is below
        
	    $('#addGroupTbl').delegate(
		         'a.edit','click', function(e) {
			
		   	//for getting the closest row value 
			var $row = $(this).closest('tr');
			var mCusotmerId = $("#customerForm input[name=id]").val();
			var poGroupName = $row.find('#poGroupName').val();
            var poGroupId = $row.find('#poGroupId').val();
            poGroupName=poGroupName.trim();
             if (this.innerHTML == "Save"  && this.innerHTML != "Edit") {
            	 var customerGroupName = $row.find('#customerGroupName').text();
            	     customerGroupName= customerGroupName.trim();
            	     poGroupName = poGroupName.trim();
				if(poGroupName ==''){
			    var text = "Please enter and Save the data otherwise Delete the row";
					showError(text);	
					$row.find('#poGroupName').css("border", "1px solid #ff0000");
					return false;
					stopProgress();
				}else if (poGroupName == customerGroupName ){
					var errorMsg ="\""
					+ customerGroupName
					+ "\" is already present. Please Edit and Save 'OR' Cancel ";
					showError(errorMsg);	
					$row.find('#poGroupName').css("border", "1px solid #ff0000");
					return false;
					stopProgress();
				}
				else{
					//put your ajax here
					if (typeof poGroupId  == "undefined"){
						poGroupId=null;
					}
					isEditable=true;
					isAddEnable=true;
				  
					$.ajax({
						async : true,

						contentType : "application/json",
						url : "custgroups/saveUpdateCustomer",
						data: "poGroupId="+poGroupId+"&mCusotmerId="+mCusotmerId+"&poGroupName="+poGroupName,
					
						success : function(data) {
							showSuccess("Successfully Saved");
							openMaintainance(custId);
		
						}
					}); 
				}
				
			 e.preventDefault();
				return;
			}

			saveOpen = true;
			e.preventDefault();

		 if(isEditable && isAddEnable) {
				var $row = $(this).closest('tr');
	            var customerGroupName = $row.find('#customerGroupName');
	            var poGroupName = $row.find('#poGroupName');
	                poGroupName.css("display", "block");
	                customerGroupName.css("display", "none");
	           if (saveOpen && this.innerHTML != "Save" ) {
	            	$row.find('.editbtn').css("display", "none");
	            	$row.find('#save').css("display", "block");
	            	$row.find('#cancel').css("display", "block");	
	            }
	            isEditable=false;
			}
			else{
				var errorMsg = "Please Save/Cancel before editing another row";
				showError(errorMsg);	
				
			}
			
		});
	    
	

	
		
		// Below javascript code check the duplicate competency	
	 	function validateDuplicates(mCustomerEmail) {
			var flag = true;
			<c:forEach var="customer"  items="${customers}">
			var customerEmailId = "${customer.customerEmail}";
			 if (customerEmailId.toUpperCase() == mCustomerEmail.toUpperCase()) {
				flag = false;

			}
			</c:forEach>
			if (flag == false) {
					var errorMsg = "Email Id already exists. Please enter unique Email Id";
					showError(errorMsg);
					return flag;
				}else{
					flag = true;
					return flag;
				}
			return flag;
		} 
		
		
</script>

<!--END: Alert: Added by Pratyoosh Tripathi -->