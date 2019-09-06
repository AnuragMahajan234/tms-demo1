<%@page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<spring:url value="/resources/styles/style.css?ver=${app_js_ver}" var="style_css"/>
	<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<style>

.projecttoolbar {border: 0px solid red; width: 50%; float:left;}
#projectTableId_info {}
</style>
<script>
var idArray = new Array();
var status = "Active";
var projectId;

$('.removePo').on("click", function(){	
	 				
		 var conf=confirm("Are you sure you want to delete this PO?");	 
		 var id =  $(this).closest("tr").attr('id');
		 idArray.push(id);
		 
		  if(conf){
			 $(this).closest("tr").remove();	
			 
		 } 
		 return;				    	
 });


		// Wait until the DOM has loaded before querying the document
		var projectTable;
		var isNew;
		function appendUsDeere()
		{
			var test=$("#buId option[value='5']").text();
	 		//alert(test);
	 	    if(test==""){
	 			$("#buId").append('<option value="5">US-Deere</option>');
	 		}	 
	 	  	
		}
		function appendInternal()
		{
			var test=$("#pCId option[value='3']").text();
	 		 if(test==""){
	 			$("#pCId").append('<option value="3">Internal</option>');
	 		}	  
	 	  	
		}	
		function addDataTableSearch(table){
			projectTable = $(table).dataTable({
				

				"bStateSave": true,
			//	"sDom": '<"projecttoolbar">lfrtip<"top">rt<"bottom"ip<"clear">',
			     "sDom": '<"projecttoolbar">lfrtip',
				"bDestroy": true,
				"sPaginationType": "full_numbers", 
				"fnCookieCallback": function (sName, oData, sExpires, sPath) {
				      // Customise oData or sName or whatever else here
				      if(isNew=='true') {
				      	return sName + "="+JSON.stringify(oData)+"; expires=" + new Date() +"; path=" + sPath;
				    }else {
				    	return sName + "="+JSON.stringify(oData)+"; expires=" + sExpires +"; path=" + sPath;
				    }
				}
			});
			isNew='false';
			/* projectTable.$('tr').dblclick( function () {
	  		    var sData = projectTable.fnGetData(this);
	  		    openMaintainance(($(this).attr('id')),"Test"); 
	  		}); */
			
		}
		function initTable()
		{
			
			$('#projectTableId').dataTable({
				"bStateSave": true,
				"sPaginationType": "full_numbers",
				"sDom": '<"projecttoolbar">lfrtip<"top">rt<"bottom"ip<"clear">',
				"bDestroy": true
			});
			<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')"> 
			$("div.projecttoolbar").html(
					'<div class="">'+
						'<span class="ProjectStatusdrdwn"><label class="blue_link" title="Status"> Status</label> <select id="projectActive">'+'<option value="active">Active</option>'+'<option value="all">All</option>'+'</select>'+

					'</span></div>');
			</sec:authorize>
		/* 	oTable = $('#example').dataTable({
				"sDom" : 'RC<"clear">lfrtip',
				"bStateSave": true,
				"aoColumnDefs" : [ {
					"bVisible" : false,
					"aTargets" : []
				} ],
				"oLanguage" : {
					"sSearch" : "Search:"
				},
				"aoColumns" : [ {}, {}, {}, {}, {}, {}, {
					"bSortable" : false
				}, {
					"bSortable" : false
				} ],
				"bSortCellsTop" : true,
			}); */
			
						
		}
		function addTotal(){
			total=0;
			//var clsName=$(this).parent().parent().attr('class');
			$("table#addPoTbl tbody tr td").find("input[type=text].inpCost").each(function(){
				total += Number($(this).val());
			});
			$("#planProBudg").html(total);
		};
		function addDatePicker(){
			addPoLength=$("table#addPoTbl tbody").find("tr").length;
			for(var i =0; i<addPoLength; i++){
				 $("#issueDate"+i).datepicker({
				    	onClose: function( selectedDate ) {
			                $( "#validUptoDate"+i).datepicker( "option", "minDate", selectedDate );
			            }
				    });
				 $("#validUptoDate"+i).datepicker({
				    	onClose: function( selectedDate ) {
			                $( "#issueDate"+i ).datepicker( "option", "maxDate", selectedDate );
			            }
				    });
			}
		}
		
		$(document).ready(function(){
			
			refreshGrid();
			/*--------------------------Numeric Validation----------------------------------------------------*/
			$(".errorNumeric").hide();
			$(document).on("keydown",".numericInp",function(event){  
				$(".errorNumeric").hide();
                if(event.shiftKey)
                    event.preventDefault();                    
                if (event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9) {
                	
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
			$("#endDate").datepicker({changeMonth: true,changeYear: true});
						
		 $(".dd_Custom_BU").change(function() {
			 
			businessUnitValidate(this.value);
			 
			}); 
          $(".dd_customer").change(function() {
			 
			 checkcust();
			 
		}); 
		$(".dd_project").change(function() {
			 
			 checkcust();
			
		      projectCategoryValidate(this.value); 
		      
		});
	
			
    		/*----------po table------------*/
			$("#addPo").on("click",function(){				
				$(".poTbl table tbody:last").append($.render.poTableRowsTempl());
				
				var x = 2;
				if (x % 2 == 0)
				{
					$(".poTbl table tbody tr:even").addClass('even');
				}
				var len=$("table#addPoTbl tbody").find("tr").length -1;
				 $("#issueDate"+len).datepicker({
				    	onClose: function( selectedDate ) {
			                $( "#validUptoDate"+len).datepicker( "option", "minDate", selectedDate );
			            }
				    });
				 $("#validUptoDate1"+len).datepicker({
				    	onClose: function( selectedDate ) {
			                $( "#issueDate"+len ).datepicker( "option", "maxDate", selectedDate );
			            }
				    });
				addDatePicker();
				addTotal();
			});
			 
			
		
    		//Added
    			$(".various").fancybox({
    				onClose:function () {
    					 
    					var selectDropdownDefault =  document.getElementById("pdo2");
    					var selectDropdownDefaultTo =  document.getElementById("listMenu2");
    					var skillIdOptionDefault=[];
    					var skillOptionDefault=[];
    					
    					 <c:forEach var="defaultActivityList"
								items="${activitys}">
					 
						 
		 
		 
								 
									skillIdOptionDefault.push('${defaultActivityList.id}');
									skillOptionDefault.push('${defaultActivityList.activityName}');
								 
								
																defaultmanadatory.push("${defaultActivityList.mandatory}");
																 
																 
															 
							
							</c:forEach>
							 for(i=0;i<skillIdOptionDefault.length;i++)
		 						{
		 						   option = document.createElement('option');
		 						   option.value=skillIdOptionDefault[i];
		 						   option.text=skillOptionDefault[i];
		 						   option.selected=false;
		 						   
		 						   if(defaultmanadatory[i]=="true"){
		 							   
		 							 
		 							 selectDropdownDefaultTo.appendChild(option);
		 						   }
		 						   else
		 							  selectDropdownDefault.appendChild(option);
		 							 
		 						}
    	        },
    				fitToView	: true,
    				autoSize	: true,
    				//closeClick	: true,
    				type : 'ajax'
    			});
    		
    			 $("#customActivity").on("click",function(){
    		//	$(".customActivityTable").show();
    			var r =$(this).attr("data-href");//this.href;
    			//alert(r)
    			var Href = r+'?projectId='+projectId;
    	
    			$("#customActivity").fancybox(
						{
							autoSize : false,
							closeClick : true,
							//autoScale   : true,
							autoDimensions : true,
							transitionIn : 'fade',
							transitionOut : 'fade',
							openEffect : 'easingIn',
							closeEffect : 'easingOut',
							type : 'iframe',
							href : Href,
							'width' : '100%',
							preload : true,
							beforeShow : function() {
								var thisH = this.height - 35;
								$(".fancybox-iframe").contents().find(
										'html').find(".midSection")
										.css('height', thisH);
							},
							afterClose: function(){
								$.getJSON("projects/populateInactiveModules/" + projectId, function(data){
									$('#pdo2').html(optionGenerateHTML(data));
								  });
								$.getJSON("projects/populateActiveModules/" + projectId, function(data){
										$('#listMenu2').html(optionGenerateHTML(data));
								 });
							  	$("#tab2").load();
								  	
							}
						});
    			 
    			 $(".fancybox-iframe body").css("background", "#fff");
    		});
		     
			function editRow(oTable, nRow) {
				
				startProgress();
				var aData = oTable.fnGetData(nRow);
				var jqTds = $(">td", nRow);
				if (jqTds.length < 1)
					return;
				oldActivity = aData[1];

				for (var i = 1; i < aData.length - 2; i++) {
					jqTds[i].innerHTML = '<input type="text" value="'+aData[i]+'">';
				}
				jqTds[0].innerHTML = '<input type="text" value="'
						+ aData[0]
						+ '" readonly="readonly" onfocus="this.blur()">';
				jqTds[2].innerHTML = '<select id ="mandatory'
						+ getInputValue(aData[2]) + '">'
						
						+ '<option value="True">Yes</option>'
						+ '<option value="False">No</option>'
						+ '</select>';
				//changes for bug 47 			 
				jqTds[3].innerHTML = 
					
					'Project';
					//changes for bug 47 			 
					jqTds[4].innerHTML = '<input type="text" value="'+aData[4]+'">';
					//changes for bug 47 
				//changes for bug 47 
				jqTds[6].innerHTML = '<select id ="productive'
						+ getInputValue(aData[6]) + '">'
						+ '<option value="True">Yes</option>'
						+ '<option value="False">No</option>'
						+ '</select>';
				jqTds[7].innerHTML = '<a class="edit" href="">Delete</a>';

				//Start Added to solve default values in drop down
				var value = getInputValue(aData[2]);
				if (aData[2].toLowerCase() == 'yes') {
					$("#mandatory" + value).val("True");
				} else {
					$("#mandatory" + value).val("False");
				}
				value = getInputValue(aData[6]);
				if (aData[6].toLowerCase() == 'yes') {
					$("#productive" + value).val("True");
				} else {
					$("#productive" + value).val("False");
				}

				stopProgress();
			}
			function getInputValue(str) {
				var index = str.indexOf("<input");
				if (index < 0)
					return null;
				str = str.substr(index, str.length);
				return $(str).val();
			}
    				$("#addActivity").on("click",function(){		
    					
    				 
    					
    					$("ul.tabs a").removeClass('active');
    					$(".tab_div").hide().next("#tab2".hash).show();
    					$('a[href$="tab2"]').removeClass('ProjectActivityTab');
    					$('a[href$="tab2"]').addClass('active');
    					$("#tab1").hide();
    		});
    		
    		//End
    		
    		
    		
    		
    		
    		
			$("#addPoTbl").find("input[type=text].inpCost").on("blur", function(){
				/*var cost = $(this).val();
				if(cost != "" && !cost.match(/^[-+]?[0-9]+$/)) {
					alert("Cost should be numeric value only");
					$(this).focus();
					return;
				}*/
				addTotal();
			});
			/*-----------delete row------------*/
			
			/* $('.removePo').live("click", function(){				
				 var conf=confirm("Are you sure you want to delete this PO?");	 
				 var id =  $(this).closest("tr").attr('id');
				 if(conf){
					 startProgress();
					 $(this).closest("tr").remove();							 
					  $.deleteJson("projectPo/"+id, function(data){
						  addTotal();						
					  }, 'json');
					  stopProgress();
				 }
				 return;
				 				    	
			}); */
		$(".tab_div").hide();
			$(".tab11_div").hide();
		 
		$('ul.tabs a[href="#tab1"]').click(function () {
			$('#ProjectActivityTabInactive').off('click');
			$('#ProjectActivityTabInactive').removeClass('active').addClass('MaintenanceTab');
			$(this).addClass('active').removeClass('MaintenanceTab');
			$(".tab_div").hide().filter(this.hash).show();
			$(".tab11_div").hide();
			$("ul.tabs a").removeClass('active');
			//$('a[href$="tab2"]').addClass('MaintenanceTab');
			$('a[href$="tab2"]').addClass('ProjectActivityTab');
			$('a#ProjectActivityTabInactive').text("Project Module");
			$(this).addClass('active');
			return false;
		}).filter(':first').click();
		
		addDataTableSearch($('#projectTableId'));
	    
		
		$('a[href$="tab2"]').click(function(){
			$("#projectForm").reset();
			appendInternal();
			appendUsDeere();
			$("table#addPoTbl tbody").html("");
			$("#planProBudg").html("");
		});

		
		
		$('a[href$="tab1"]').click(function(){
			$('#projectTableId').dataTable().fnFilter('');
  			$("div.errorNumericLast").hide();
  			$(".required").removeClass("brdrRed");
  			$("a").removeClass("brdrRed");
  			initTable();
  			//$('#projectActive>option:eq(1)').prop('selected', true);
  			//document.getElementById('projectActive').value=status;
		});
		
function businessUnitValidate(businessUnit){
			if(businessUnit == "5"){
				 	 $("#pCId option").each(function(){
				 		 if("3"==$(this).val()){
			 			    $(this).remove();
			  			 }	  
			 	  });
			 	}
			 	else{
			 		appendInternal();
			 	  }	
	}
		
		function projectCategoryValidate(projectCategory){
	 if(projectCategory == "3"){
	 	 $("#buId option").each(function()
	 	  {
	 		  if("5"==$(this).val()) 
	 			 {
	 				$(this).remove();
	 			  }	  
	 	  });
	 	}
	 	else
	 	{
	 		appendUsDeere();
	 	}	
	}
		function validateCombo(comboBoxArray){
  			var varcount = 0;
  			$(comboBoxArray).each(function(index) {
  				var selectDdVal =$(this).val();
  				if(selectDdVal == ''){
					$(this).addClass("brdrRed").next("a").addClass("brdrRed");
					varcount++;
				}else{
					$(this).removeClass("brdrRed").next("a").removeClass("brdrRed");
				}
  			});
  			if(varcount>0)return false;
  			else return true;
			
  			
  		}

		$("#ok").click(function(){
			 $(this).parent().parent().hide();
			 $("#bgDiv").remove();
		 });
		 var comboBoxBlur = $("select.required").parent("td").find("span.ui-combobox input");
			$(comboBoxBlur).each(function(){
				$(this).addClass("required");
			});
			$(comboBoxBlur).blur(function(){
				if($(this).hasClass("required")){
					if($(this).val()==''){
						$(this).next("a").addClass("brdrRed");
					}else{
						$(this).next("a").removeClass("brdrRed");
						$(this).removeClass("brdrRed");
					}
				}
			});
			/* var customeRadio = $(".radio_b").find(".required");
			$(customeRadio).change(function(){
				if($(this).attr("checked","true")){
					$(this).parent().parent().parent("td").find(".radio_b").removeClass("invalid");
				}
			}); */
	
			$(document.body).on('change','#projectActive',function(){
			  
				status = document.getElementById('projectActive').value;
				
			    $.ajax({
					
					url: "projects/projectActive",
					//Issue #448- changed data field from projectActive to status- Neha 
					data: "projectActive=" + status,
					 success: function( data ) {if(projectTable != null) projectTable.fnClearTable();
						 $("#projectTableId > tbody:last").append(
									$("#projectTableRows").render(data)
								);
						 addDataTableSearch($("#projectTableId"));
							$(".tab_div").hide();
							$('ul.tabs a').click(function () {
								$(".tab_div").hide().filter(this.hash).show();
								$("ul.tabs a").removeClass('active');
								$(this).addClass('active');
								return false;
							}).filter(':first').click();
							document.getElementById('projectActive').value=status;
						} 
					
				});
			});
			
			
			
	$( "#save" ).click(function( event ) {
		var validationFlag = validateCombo(comboBoxBlur);
		if(validationFlag){
			startProgress();	
		}
		
		addTotal();
		/* if(document.getElementById('nondeere').checked){
            document.getElementById('deere').value = false;
    	 }else{
            document.getElementById('deere').value = true;
     	} */

		if(idArray.length > 0){
		for(var i=0; i<idArray.length; i++){
		var id = idArray[i];
			$.deleteJson("projects/"+id, function(data){
				  addTotal();						
			  }, 'json');
		}	
		}
		var poNumberArray = new Array();
		var rowCount = $('#addPoTbl >tbody >tr ').length;
		var temp=0;
		var inpSelect = $('#addPoTbl >tbody >tr ').find("td:nth-child(1) input");
		$(inpSelect).each(function(){
			var tdInpVal = $(this).val();
			poNumberArray.push(tdInpVal);
		});
		
		
		event.preventDefault();
		$("#projectForm").validVal({
			customValidations: {
			"server-validation": function(val) {
				var projectName="";
				var projectCode="";
				var projectResult = true;
				projectName=$.trim($("#projectForm input[name=projectName]").val());
				projectCode=$.trim($("#projectCode").val());
				/* if(projectName!=null || projectName!=undefined || projectName!="") {
					projectName=null;
				} */
					$.ajax({
						async:false,
						url: "projects/validate",
						data: "projectName=" + projectName + "&projectCode=" + projectCode,
						success: function( data ) {
							if ( data.projectResult != true ) {
								projectResult = false;
								//isDuplicateProjectName = true;
							} 
						}
					});
					if(projectResult==false){
						$(this).next("div.errorNumericLast").show();
					}else{
						$(this).next("div.errorNumericLast").hide();
						return projectResult;
					}
					 return projectResult;
			}
		}
	});
		
		
		
		
		
		var pk = $("#projectForm input[name=id]").val();
		var form_data = $( "#projectForm" ).triggerHandler( "submitForm" );
	    addPoLength=$("table#addPoTbl tbody").find("tr").length;
	    var errorMsg="";
	    var flag = true;
		var projectKickOffDate = document.getElementById("projectKickOff").value;
		var projectEndDate = document.getElementById("endDate").value;
		var plannedprojsize = document.getElementById("plannedProjSize").value;
		if(plannedprojsize != "" && !plannedprojsize.match(/^[-+]?[0-9]+$/)) {
			validationFlag = false;
			errorMsg =  errorMsg+"\u2022 Please enter only numeric characters in Planned Project Size<br /> ";			
	   	}
	  	if(!(validDates(projectKickOffDate, projectEndDate))) {
	     	validationFlag = false;
			//document.getElementById("endDate").focus();
			errorMsg =  errorMsg+"\u2022 End Date should be greater than Project Kick off Date<br /> ";
	   	}
	  	if(rowCount>1){
			for(var count=0 ;count<poNumberArray.length ; count++ ){
				for(var count1=count+1;count1<poNumberArray.length ; count1++ ){
					if(poNumberArray[count] && poNumberArray[count1]){
						if(poNumberArray[count] == poNumberArray[count1]){
						 	temp++;
					    }
					 }
				 }
			 }
			if(temp > 0){				
				validationFlag = false;
				errorMsg =  errorMsg+"\u2022 Duplicate entries of PO Number is not Allowed<br /> ";
				//showError(errorMsg);
				//stopProgress();
			}
		}  
		for(var i =0; i<addPoLength; i++){
			var issueDate =	$("#issueDate"+i).val();
			var validUptoDate =	$("#validUptoDate"+i).val();
			if(!(validDates(issueDate, validUptoDate))) {
				validationFlag = false;
				errorMsg = errorMsg+"\u2022 ValidUpto Date should be greater than Issue Date<br /> ";
				//$("#validUptoDate"+i).val("");
			}
		}
		
		if(!validationFlag) {
			stopProgress();
			if(errorMsg.length > 0) 							
				showError(errorMsg);							
			return;
		}	
		else if(form_data){
			 startProgress();
			 var successMsg ="Project details have been saved successfully";
			 $("#planProBudgId").val(total);
			 
			if( pk != null && pk > 0){
				 $.putJson('projects', $("#projectForm").toDeepJson() , function(data){
					refreshGrid();
					stopProgress();
		  			showSuccess(successMsg);
				 } , 'json');
			}else{
				$("#projectForm input[name=id]").val("");
				 $.postJson('projects',$("#projectForm").toDeepJson() , function(data){
					 isNew='true';
					 refreshGrid();
					 stopProgress();
					 showSuccess(successMsg);
				}, 'json');
			}				
		}else{
			stopProgress();
			return;
			}
		window.location.reload();
	   	
	 });
	
			
	
	
		});//end of document.ready function
	
		 
		
	function optionGenerateHTML(obj) {
 		var optionsHTML = '';
 		$.each(obj,function(ind,elm){
 			optionsHTML += '<option value="'+elm.id+'">'+elm.moduleName+'</option>';
 		});
		return optionsHTML;
	}
		
	function openMaintainance(id,projectName){
			startProgress();
			 
			displayMaintainance(projectName);
			 	projectId=id;

			 	 
			 	$('#pdo2').empty(); $('#listMenu2').empty();
			 	$("#customTable").show();
			 	
			  
			 
			  $.getJSON("projects/populateInactiveModules/" + projectId, function(data){
				  $('#pdo2').html(optionGenerateHTML(data));
			  });
			 
			  
			 $.getJSON("projects/populateActiveModules/" + projectId, function(data){
			 		$('#listMenu2').html(optionGenerateHTML(data)); 
			  });
			stopProgress();	
			status=document.getElementById('projectActive').value;
		}
		
	
		function displayMaintainance(projectName){
			 
			$('a#ProjectActivityTabInactive').text(projectName);
			$("ul.tabs a").removeClass('active').addClass('MaintenanceTab');
			 
			$(".tab_div").hide().next("#tab2".hash).show();
			$('#ProjectActivityTabInactive').addClass('active').removeClass('MaintenanceTab');
			
			
		}
		
		// End To fix #189	
		
	
		function refreshGrid(){
			if(projectTable != null) projectTable.fnClearTable();
				$.getJSON("projects/updatedlist", function(data) {
					$("#projectTableId > tbody:last").append(
					$("#projectTableRows").render(data)
				);
				addDataTableSearch($("#projectTableId"));
				$(".tab_div").hide();
				$('ul.tabs a').click(function () {
					$(".tab_div").hide().filter(this.hash).show();
					$("ul.tabs a").removeClass('active');
					$(this).addClass('active');
					return false;
				}).filter(':first').click();
			});
			
		}
		
		function checkcust(){
			var projectCat= document.getElementById('pCId').options[document.getElementById('pCId').selectedIndex].value;
			var customerName= document.getElementById('customerNameId.id').options[document.getElementById('customerNameId.id').selectedIndex].value ;
			/* if(projectCat==3 && customerName==7){
				document.getElementById('nondeere').checked=true; 
			}
			else{
				document.getElementById('deere').checked=true;
			} */
			
			var select=document.getElementById('customerNameId.id');
			
			for (i=0;i<select.length;i++) {
				if(projectCat==1){
				   if (select.options[i].value==7) {
					   select.options[i].text='';
				   }
				}else{
					select.options[7].text="Yash Internal";
				}
			} 
			
		 }
		
		
		//Start Wicket palette 
		
		function move_item(from, to)
{
  var f;
  var SI; 
  //alert(from.value);
  if(from.options.length>0)
  {
    for(i=0;i<from.length;i++)
    {
      if(from.options[i].selected)
      {
        SI=from.selectedIndex;
        f=from.options[SI].index;
        to.options[to.length]=new Option(from.options[SI].text,from.options[SI].value);
        from.options[f]=null;
        i--; 
      }
    }
  }
}

function moveUp(lst){
	if (lst.selectedIndex>0){
		var SI = lst.selectedIndex;
		var tempOpt = new Option(lst.options[SI].text,lst.options[SI].value);
		lst.options[SI].text = lst.options[SI-1].text;
		lst.options[SI].value = lst.options[SI-1].value;
		lst.options[SI-1].text = tempOpt.text;
		lst.options[SI-1].value = tempOpt.value;
		lst.options[SI-1].selected=true;
	}
}

function moveDown(lst){
	if (lst.selectedIndex<lst.options.length-1){
		var SI = lst.selectedIndex;
		var tempOpt = new Option(lst.options[SI].text,lst.options[SI].value);
		lst.options[SI].text = lst.options[SI+1].text;
		lst.options[SI].value = lst.options[SI+1].value;
		lst.options[SI+1].text = tempOpt.text;
		lst.options[SI+1].value = tempOpt.value;
		lst.options[SI+1].selected=true;
	}
		
		
}		
		
		
		//End
		
		
		function show(){
		  	
		  $('#tab1').show();
		  $('#tab2').hide();
			$('#ProjectActivityTabInactive').off('click');
			$('#ProjectActivityTabInactive').removeClass('active').addClass('MaintenanceTab');
		      $('#list1').addClass('active').removeClass('MaintenanceTab');
		}
		
		function saveactivity()
		{ 
			var projectModuleNotMapped = [];
		    //var projectActivitySEPGJson =[];
		    var projectModuleMapped =[];
			/* $('#listMenu option').each(function(i) { 
				projectActivity.push( $(this).attr('value') );
				 
			}); */
				
			$('#pdo2 option').each(function(i){ 
				projectModuleNotMapped.push( $(this).attr('value') );
			});
		
			$('#listMenu2 option').each(function(i){ 
				projectModuleMapped.push($(this).attr('value'));
				});
			
		    /* 
			if(projectActivity.length == 0){
				projectActivity.push("0");
				
			}
			
			if(projectActivitySEPGJson.length == 0){
				projectActivitySEPGJson.push("0");
				
			} */
			
			/* if(custmActivityJson.length == 0){
				custmActivityJson.push("0");
				
			} */
	
			/* if(custmActivityJson==0){
				showError("Please Select atleast one Project Module");
		} *///else{
		 $.ajax({
			        url: 'projects/changeProjectModuleStatus',
			      	contentType: "application/json",
			      	type:"GET",
			     	data: "projectId=" + projectId +"&projectModuleMapped="+projectModuleMapped+"&projectModuleNotMapped="+projectModuleNotMapped,
			     	success: function(succeResponse) { 
			     		showSuccess("Project Modules has been saved successfully ");
			     		$("#tab1").show();
			     		$("#tab2").hide();
			     		$('#ProjectActivityTabInactive').off('click');
						$('#ProjectActivityTabInactive').removeClass('active').addClass('MaintenanceTab');
					    $('#list1').addClass('active').removeClass('MaintenanceTab');
			     		$('a#ProjectActivityTabInactive').text("Project Module");
			         	},
			     	error: function(errorResponse)
			 	    {
			 	    	showError("Project Modules cannot be  updated.");
			 	    }
			 });  
			
		}
//}
		
		
</script>
<script id="projectTableRows" type="text/x-jsrender">
 						<tr id="{{>id}}">
			               <sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER')">
			                <td align="left" valign="middle">
							{{if managerReadonly}}
			                	<a href="#" onclick="openMaintainance({{>id}},'{{>projectName}}');"> {{>projectCode}}</a>
							{{else}}
								<a href="#"  class="copyInactive"> {{>projectCode}}</a>
							{{/if}}
 							</td>
					</sec:authorize>
			            <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_MANAGER')">
						<td align="left" valign="middle">
   							<a href="#" onclick="openMaintainance({{>id}},'{{>projectName}}');"> {{>projectCode}}</a>
						</td>
						</sec:authorize>		
				
			                <td align="left" valign="middle">
							{{if projectName}}
									{{>projectName}}
								  {{else}}
									  N.A.
								{{/if}}
							</td>
			                <td align="left" valign="middle">
								{{if customerNameId}}
									{{>customerNameId.customerName}}
								  {{else}}
									  N.A.
								{{/if}}
							</td>
			               
			                 <td align="left" valign="middle">
								{{if offshoreDelMgr.employeeName}}
									{{>offshoreDelMgr.employeeName}}
								  {{else}}
									  N.A.
								{{/if}}
							</td>
			                 <td align="left" valign="middle">
								{{if onsiteDelMgr}}
									{{>onsiteDelMgr}}
								  {{else}}
									  N.A.
								{{/if}}
							 </td>
			                 <td align="left" valign="middle">
								{{if engagementModelId}}
									{{>engagementModelId.engagementModelName}}
								  {{else}}
									  N.A.
								{{/if}}
								
							</td>
			                 <td align="left" valign="middle">
								{{if projectKickOff}}
									{{>projectKickOff}}
								  {{else}}
									  N.A.
								{{/if}}
							</td>
			                 <td align="left" valign="middle">
								{{if plannedProjSize}}
									{{>plannedProjSize}}
								  {{else}}
									  N.A.
								{{/if}}
							</td>
			              </tr>

</script>
<script id="poTableRows" type="text/x-jsrender">
 		<tr class="costRow" >
			<td ><div class="positionRel" id="">
				<input type="text" id="poNumber" name="projectPoes[{{:~GetRowId()}}].poNumber"  class="string required numericInp readOnlyinput"  />
				<span class="errorNumeric" style="display: none;">Please Enter Numeric Numbers Only <img src="images/errorAerrow.png"></span>
				</div>
			</td>
	    	<td width="40%">
				<input type="text" id="poAcName" name="projectPoes[{{:~GetRowId()}}].accountName" class="string required readOnlyinput"  />
			</td>
			<td width="20%"><div class="positionRel">
				<input type="text" id="poCost" name="projectPoes[{{:~GetRowId()}}].cost" class="required inpCost numericInp" />
				<span class="errorNumeric" style="display: none;">Please Enter Numeric Numbers Only <img src="images/errorAerrow.png"></span>
			</div></td>
			<td>
				<input type="text" id="issueDate{{:~GetRowId()}}" name="projectPoes[{{:~GetRowId()}}].issueDate" />
			</td>
			<td>
				<input type="text" id="validUptoDate{{:~GetRowId()}}" name="projectPoes[{{:~GetRowId()}}].validUptoDate" />
			</td>
<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')">
			<td width="5%" align="left">
				<a href="javaScript:void(0)"><img width="16" height="16" class="removePo" src="resources/images/remove.png"></a>
			</td>
</sec:authorize>
	    </tr>

</script>

<script id="poTableRowsWithValues" type="text/x-jsrender">
 						<tr class="costRow" id="{{>id}}">
							<td><div class="positionRel">
							<input type="text" id="poNumber" name="projectPoes[{{:#index}}].poNumber"  class="required string numericInp readOnlyinput" value="{{>poNumber}}"/>
							<span class="errorNumeric" style="display: none;">Please Enter Numeric Numbers Only <img src="images/errorAerrow.png"></span>
							</div></td>
	                		<td width="40%">
								<input type="hidden" id="poId" name="projectPoes[{{:#index}}].id" value="{{>id}}"/>
								<input type="text" id="poAcName" name="projectPoes[{{:#index}}].accountName" class="required string readOnlyinput" value="{{>accountName}}"/>
							</td>
							<td width="20%"><div class="positionRel">
								<input type="text" id="poCost" name="projectPoes[{{:#index}}].cost" class="required inpCost numericInp readOnlyinput"   value="{{>cost}}" />
								<span class="errorNumeric" style="display: none;">Please Enter Numeric Numbers Only <img src="images/errorAerrow.png"></span>
							</div></td>
							<td>
							<input type="text" id="issueDate{{:#index}}" name="projectPoes[{{:#index}}].issueDate" value="{{>issueDate}}" class="readOnlyinput"/>
							</td>
							<td>
							<input type="text" id="validUptoDate{{:#index}}" name="projectPoes[{{:#index}}].validUptoDate" value="{{>validUptoDate}}" class="readOnlyinput" />
							</td>
<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')">
							<td width="5%" align="left"><a href="javaScript:void(0)"><img width="16" height="16" class="removePo" src="resources/images/remove.png" class="readOnlyinput" ></a></td>
</sec:authorize>
	                	</tr>

</script>

<script>

var data={
		getCostRowId: function(arg){
			return 	$("table#addPoTbl tbody").find("tr").length;			
		}
};
$.views.helpers({ GetRowId: data.getCostRowId });
 
$.templates("poTableRowsTempl", {
    markup: "#poTableRows"
});
$.templates("poTableRowsWithValuesTempl", {
    markup: "#poTableRowsWithValues"
    
});

</script>
<div class="content-wrapper">
	<!--right section-->
	<div class="botMargin">
		<h1>Project Module</h1>
	</div>
	<div class="tab_seaction">
		<ul class='tabs'>
			<li><a id=list1 href='#tab1'>List</a></li>
			 
			<li><a class="ProjectActivityTab MaintenanceTab" id="ProjectActivityTabInactive"  href='#tab2'>Project Module
					</a></li>
		</ul>
		<div id='tab1' class="tab_div">
			<div class="tbl">
				<table class="dataTable dataTbl" id="projectTableId" width="100%">
					<thead>
						<tr>
							<th width="7%" align="left" valign="middle">Project ID</th>
							<th width="8%" align="left" valign="middle">Project Name</th>
							<th width="8%" align="left" valign="middle">Customer Name</th>
							<!-- <th width="6%" align="center" valign="middle">Customer Type</th> -->
							<th width="10%" align="left" valign="middle">Manager</th>
							<th width="10%" align="left" valign="middle">Customer Relationship Manager</th>
							<th width="10%" align="left" valign="middle">Engagement Mode</th>
							<th width="10%" align="left" valign="middle">Project Kick off Date</th>
							<th width="10%" align="left" valign="middle">Planned Project Size</th>
							<!-- <th width="10%" align="center" valign="middle">Project
								tracking Currency</th>
							<th width="10%" align="center" valign="middle">Planned
								Project Cost</th> -->
						</tr>
					</thead>
					<tbody>

						<c:forEach var="project" items="${projects}">
							<tr id="${project.id}">
								<td align="left" valign="middle"><a href="#"
									onclick="openMaintainance(${project.id},'${project.projectName}');">
										${project.projectCode}</a></td>
								<td align="left" valign="middle"><c:if
										test="${empty project.projectName}">N.A.</c:if> <c:if
										test="${not empty project.projectName}">${project.projectName}</c:if>
								</td>
								<td align="left" valign="middle"><c:if
										test="${empty project.customerNameId}">N.A.</c:if> <c:if
										test="${not empty project.customerNameId.customerName}">${project.customerNameId.customerName}</c:if>
								</td>
								<%-- <td align="center" valign="middle"><c:if
										test="${project.deere}">Deere</c:if> <c:if
										test="${not project.deere}">Non-Deere</c:if></td> --%>
								<td align="left" valign="middle"><c:if
										test="${empty project.offshoreDelMgr}">N.A.</c:if> <c:if
										test="${not empty project.offshoreDelMgr}">${project.offshoreDelMgr.employeeName}</c:if>
								</td>
								<td align="left" valign="middle"><c:if
										test="${empty project.onsiteDelMgr}">N.A.</c:if> <c:if
										test="${not empty project.onsiteDelMgr}">${project.onsiteDelMgr}</c:if>
								</td>
								<td align="left" valign="middle"><c:if
										test="${empty project.engagementModelId}">N.A.</c:if> <c:if
										test="${not empty project.engagementModelId.engagementModelName}">${project.engagementModelId.engagementModelName}</c:if>
								</td>
								<td align="left" valign="middle"><c:if
										test="${empty project.projectKickOff}">N.A.</c:if> <c:if
										test="${not empty project.projectKickOff}">
										<fmt:formatDate value="${project.projectKickOff}" type="date"
											pattern="<%=Constants.DATE_PATTERN %>" />
									</c:if></td>
								<td align="left" valign="middle"><c:if
										test="${empty project.plannedProjSize}">N.A.</c:if> <c:if
										test="${not empty project.plannedProjSize}">${project.plannedProjSize}</c:if>
								</td>
								<%-- <td align="center" valign="middle"><c:if
										test="${empty project.projectTrackingCurrencyId}">N.A.</c:if>
									<c:if
										test="${not empty project.projectTrackingCurrencyId.currencyName}">${project.projectTrackingCurrencyId.currencyName}</c:if>
								</td>
								<td align="center" valign="middle"><c:if
										test="${empty project.plannedProjCost}">N.A.</c:if> <c:if
										test="${not empty project.plannedProjCost}">${project.plannedProjCost}</c:if>
								</td> --%>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="clear"></div>
		</div>
		
	
		<div id='tab2' class="tab_div" style="display:none;">
		<%-- <table width="100%" border="0" cellspacing="1" cellpadding="0">
      <tr>
    <td><form name="form1" method="post" action="">
      <table width="99%" border="0" align="center" cellpadding="1" cellspacing="1">
       
        <tr>
          <td width="50%"  valign="top"><table width="100%" border="0" cellpadding="2" cellspacing="1">
              <tr>
                <td width="58%" valign="top">
                <div class="defaultActivityWrapper">
                <table border="0" align="left" cellpadding="2" cellspacing="1" class="borderlightgray">
                 <th align="left">Map Default</th>
                  <tr align="center" valign="middle">
                    <td align="left" valign="top" class="ent_elevenred defaultActivityHeading">Default Activity</td>
                    <td align="left" valign="top" class="text">&nbsp;</td>
                    <td align="left" valign="top" class="ent_elevenred defaultActivityHeading">Default Activity Mapped</td>
                    <td align="left" valign="top" class="text">&nbsp;</td>
                  </tr>
                  <tr align="center" valign="middle">
                    <td valign="top" class="text"><span id="selectionView" >
                      <select name=pdo size="7" multiple class="textField textFieldWidth" id="pdo" style="width:150">
                       <c:forEach var="defaultActivity"
										items="${defaultActivity}">
										<option value="${defaultActivity.id}">${defaultActivity.activityName}</option>
									</c:forEach>
                      </select>
                    </span></td>
                    <td class="text"><table width="60%" border="0" cellpadding="2" cellspacing="7" >
                        <tr>
                          <td align="center" valign="middle"><input name="btn_Exit" type="button" class="ent_button" id="btn_Exit" value="&gt;&gt;" onClick="move_item(pdo, listMenu)"></td>
                        </tr>
                        <tr>
                          <td align="center" valign="middle"><input name="btn_Exit" type="button" class="ent_button" id="btn_Exit" value="&lt;&lt;" onClick="move_item(listMenu,pdo)"></td>
                        </tr>
                    </table></td>
                    <td class="text"><table cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                          <td width="72%" valign="top"><span id="selectionView" >
                            <select name="listMenu" size="7" multiple  class="textField textFieldWidth" id="listMenu"  style="width:150" >
                            </select>
                          </span> </td>
                        </tr>
                    </table></td>
                   
                  </tr>
                </table>
                </DIV>
                </td>
              </tr>
          </table></td>
        </tr>
        <tr></tr>
          <tr></tr>
      </table>
      <br>
         
       <!-- second part start-->
       <table width="99%" border="0" align="center" cellpadding="1" cellspacing="1">
        <tr>
          <td width="50%"  valign="top">
          <table width="100%" border="0" cellpadding="2" cellspacing="1">
           
              <tr>
                <td width="58%" valign="top"><table border="0" align="left" cellpadding="2" cellspacing="1" class="borderlightgray">
                  <th align="left">Map SEPG</th><tr align="center" valign="middle">
                    <td align="left" valign="top" class="ent_elevenred defaultActivityHeading">SEPG Activity</td>
                    <td align="left" valign="top" class="text">&nbsp;</td>
                    <td align="left" valign="top" class="ent_elevenred defaultActivityHeading">SEPG Activity Mapped</td>
                    <td align="left" valign="top" class="text">&nbsp;</td>
                  </tr>
                  <tr align="center" valign="middle">
                    <td valign="top" class="text"><span id="selectionView" >
                      <select name=pdo1 size="7" multiple class="textField textFieldWidth" id="pdo1">
                       <c:forEach var="defaultActivity"
										items="${defaultActivity}">
										<option value="${defaultActivity.id}">${defaultActivity.activityName}</option>
									</c:forEach>
                      </select>
                    </span></td>
                    <td class="text"><table width="60%" border="0" cellpadding="2" cellspacing="7" >
                        <tr>
                          <td align="center" valign="middle"><input name="btn_Exit" type="button" class="ent_button" id="btn_Exit" value="&gt;&gt;" onClick="move_item(pdo1, listMenu1)"></td>
                        </tr>
                        <tr>
                          <td align="center" valign="middle"><input name="btn_Exit" type="button" class="ent_button" id="btn_Exit" value="&lt;&lt;" onClick="move_item(listMenu1,pdo1)"></td>
                        </tr>
                    </table></td>
                    <td class="text"><table cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                          <td width="72%" valign="top"><span id="selectionView" >
                            <select name="listMenu1" size="7" multiple  class="textField textFieldWidth" id="listMenu1"  style="width:150" >
                            </select>
                          </span> </td>
                        </tr>
                    </table></td>
                   
                  </tr>
                </table></td>
              </tr>
          </table></td>
        </tr>
      </table>
      
        <br> --%>
          <table width="99%" border="0" align="center" cellpadding="1" cellspacing="1">
        <tr>
          <td width="50%"  valign="top">
          <table width="100%" border="0" cellpadding="2" cellspacing="1" style="display: none" id="customTable">
        	 
              <tr>
                <td width="58%" valign="top"><table border="0" align="left" cellpadding="2" cellspacing="1" class="borderlightgray">
                <th align="left">Map Module</th>
                  <tr align="center" valign="middle">
                    <td align="left" valign="top" class="ent_elevenred defaultActivityHeading">Project Module</td>
                    <td align="left" valign="top" class="text">&nbsp;</td>
                    <td align="left" valign="top" class="ent_elevenred defaultActivityHeading">Project Module Mapped</td>
                    <td align="left" valign="top" class="text">&nbsp;</td>
                  </tr>
                  <tr align="center" valign="middle">
                    <td valign="top" class="text"><span id="selectionView" >
                      <select name=pdo2 size="7" multiple class="textField textFieldWidth" id="pdo2">
                       <c:forEach var="defaultActivity"
										items="${defaultActivity}">
										<option value="${defaultActivity.id}">${defaultActivity.activityName}</option>
									</c:forEach>
                      </select>
                 
                    </span></td>
                    <td class="text"><table width="60%" border="0" cellpadding="2" cellspacing="7" >
                        <tr>
                          <td align="center" valign="middle"><input name="btn_Exit" type="button" class="ent_button" id="btn_Exit" value="&gt;&gt;" onClick="move_item(pdo2, listMenu2)"></td>
                        </tr>
                        <tr>
                          <td align="center" valign="middle"><input name="btn_Exit" type="button" class="ent_button" id="btn_Exit" value="&lt;&lt;" onClick="move_item(listMenu2,pdo2)"></td>
                        </tr>
                    </table></td>
                    <td class="text"><table cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                          <td width="72%" valign="top"><span id="selectionView hello" >
                            <select name="listMenu2" size="7" multiple  class="textField textFieldWidth" id="listMenu2"  style="width:150" >
                            </select>
                          </span> </td>
                        </tr>
                    </table></td>
                   
                  </tr>
                </table></td>
              </tr>
          </table></td>
        </tr>
      </table> 
         
         <br>   
         
     <!-- <a href ="#" data-href="projectactivitys/customActivities"  id="customActivity" class="various"> Add Project Module</a> -->
         <a href ="#" data-href="projects/projectmodules"  id="customActivity" class="various"> Add Project Module</a>
          <br><br>
     <!--  <input type="button" value="Save Activities" onclick="saveactivity()" /> -->
     <div class="search_filter">
					<div align="right">
						<a href="#" class="blue_link" onclick="saveactivity()"> <img
							src="/rms/resources/images/save.png" name="save" width="16"
							height="22" /> Save Module/
						</a>
						
						<a class="blue_link" onclick="show()" href="#" >Cancel</a>
					</div>
				</div>
     
     
    </form></td>
  </tr>
</table>
		</div>
		
	</div>
	<!--right section-->
</div>
<div class="clear"></div>
<!-- Alert: Added by Pratyoosh Tripathi -->

<div class="notification-bar">
<!-- <div class="close closeIconPosition">close</div> -->
<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
  <!-- <div class="notification-text">Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.</div> -->
  <sec:authorize
				access="hasAnyRole('ROLE_ADMIN')">
				<div class="notification-text">You can add/map modules for any projects clicking on its project id.</div>		
	</sec:authorize>
  <sec:authorize
				access="hasAnyRole('ROLE_MANAGER','ROLE_DEL_MANAGER', 'ROLE_BG_ADMIN')">
				<div class="notification-text"> You can add/map modules for your projects clicking on its project id.</div>			
	</sec:authorize>
	
  
</div>
    
   <style>
   .notification-bar{
       width: 400px;
       background: rgba(30, 159, 180,.8);
    padding: 10px;
    position: fixed;
    bottom: 30px;
    right: 25px;
   
   }
   
   .closeIconPosition{    position: absolute;
    top: -10px;
    right: -20px;}
    
    .toast-close {
    float: right;
    display: block;
    width: 13px;
    height: 13px;
    padding: 0px 4px;
    border-radius: 3px;
    background: #000 url(/rms/resources/images/cross_icon_white.png) no-repeat center !important;
    z-index: 1000;
    position: absolute;
    top: -5px;
    right: -5px;
    opacity: .7 !important;
}

.notification-text{    font-size: 14px;
    color: #fff;
    text-align: justify;}
    
   </style>
    <script type="text/javascript">
$( document ).ready(function() {
	
	$('[data-toggle="tooltip"]').tooltip();
	
	setInterval(function(){ $('[data-toggle="tooltip"]').fadeToggle(500); }, 1000);
	
	<%
		Boolean flag=false;
		flag=(Boolean)session.getAttribute("notificationbarflag");
	%>
	
	
$('.close').click(function(){
    $('.notification-bar').hide();
   });
});
</script>
			<!-- Alert: Added by Pratyoosh Tripathi -->


