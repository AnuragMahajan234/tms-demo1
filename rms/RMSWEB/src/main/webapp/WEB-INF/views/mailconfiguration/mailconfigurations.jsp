<%@page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<style type="text/css" title="currentStyle">
thead input {
	width: 100%
}

table.dataTable {
	margin: 10px auto 0;
}

input.search_init {
	color: #999
}

#dialog, .ui-dialog {
	overflow: visible;
}

#projectTableId_wrapper, #projectTableId_wrapper .dataTables_scrollHeadInner,
	#projectTableId_wrapper .dataTable {
	width: 100% !important;
}

#projectTableId {
	table-layout: fixed;
}

#projectTableId thead th {
	width: 120px;
}

#projectTableId td {
	word-wrap: break-word;
}

#projectTableId_wrapper .dataTables_scrollBody {
	max-height: 240px;
}

#projectTableId_wrapper {
	/* margin-top:-35px; */
	
}

.proj_alloc_note_msg {
	margin-left: 650px;
	display: inline-block;
	text-align: justify;
	font-size: 12px;
}

.proj_alloc_resourcetocopy_msg {
	display: inline-block;
	text-align: justify;
	font-size: 12px;
	width: 570px;
	vertical-align: middle;
	margin-top: 6px;
}
</style>

<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>

<script>
var idArray = new Array();
var status = "";
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
				"sDom": '<"projecttoolbar">lfrtip',
				"sDom": '<"top"lfip>rt<"bottom"ip<"clear">',
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
		}
		function initTable()
		{
			
			$('#projectTableId').dataTable({
				"bStateSave": true,
				"sPaginationType": "full_numbers",
				"sDom": '<"projecttoolbar">lfrtip',
				"sDom": '<"top"lfip>rt<"bottom"ip<"clear">',
				"bDestroy": true
			});
			<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')"> 
			$("div.projecttoolbar").html(
					'<div class="btnIcon projectBtnIcon">'+
						'<span class="addNewContainer"><a href="#" class="blue_link" id="addNew" >'+
							'<img src="resources/images/addUser.gif" width="16" height="22" /> '+

						'Add New </a></span>'+'&nbsp;&nbsp; <span class="ProjectStatusdrdwn"><label class="blue_link" title="Status"> Status</label> <select id="projectActive">'+'<option value="all">All</option>'+'<option selected="selected" value="active">Active</option>'+'</select>'+

					'</span></div>');
			</sec:authorize>
		}
		function addTotal(){
			total=0;
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
		
		var DELAY = 700, 
		clicks = 0, 
		timer = null;
		clickable = true
		$(document).ready(function(){
	     projectTable = $('#projectTableId').dataTable({
                "bProcessing": true,
                "bServerSide": true,       
                "sAjaxSource": '/rms/projectallocations/list/'+"active",
                "sPaginationType": 'full_numbers',
                "pagingType": "full_numbers",
                "bAutoWidth" : false,
                "sScrollX": "100%",
                "sScrollY": "350",
                "bScrollCollapse": true,
                "bPaginate": true,
               
                "bDestory": true,
                "bRetrieve": true,
              
                "aaSorting": [ [0,'asc']],
                "oLanguage": {
                                "sLengthMenu": 'Show <SELECT>'+ '<OPTION value=10>10</OPTION>'+ '<OPTION value=25>25</OPTION>'+ '<OPTION value=50>50</OPTION>'+ '<OPTION value=100>100</OPTION>'+ '<OPTION value=200>200</OPTION></SELECT> entries'
                },
               
         		"bSortCellsTop": true,
                "fnServerData": function ( sSource, aoData, fnCallback ) {
                    sSource = sSource + '?noCache=' + new Date().getTime();
                    callJSONWithErrorCheck(sSource,aoData,null,function (json) {  
                           fnCallback(json); 
                    });                           
				    },
				    "fnDrawCallback": function() {
				                    handlePaginationButtons(projectTable, "projectTableId");
				                    },
				    "fnInitComplete": function() {
				    },
				    "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
				    	var projectName = aData[1];
				    	 if(projectName == ""||projectName == null){
						 $('td:eq(1)', nRow).html("N.A");
						 }
				    	 var customerName = aData[3];
				    	 if(customerName == ""||customerName == null){
							 $('td:eq(2)', nRow).html("N.A");
						 }
				    	 
				    	 var projectManager = aData[4];
				    	 if(projectManager == ""|| projectManager == null){
							 $('td:eq(3)', nRow).html("N.A");
						 }
				    	 
				    	 var projectKickOffDate = aData[8];
				    	 if(projectKickOffDate == "" ||projectKickOffDate == null){
							 $('td:eq(4)', nRow).html("N.A");
						 }
				    	 
				    	 <sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
			                var managerReadonly = aData[12];	
			                if(managerReadonly==false){
			                	$('td:eq(0)', nRow).html('<a href="#" onclick="openMaintainance('+aData[0]+', \'' + aData[1] + '\');">'+ aData[1]+'</a>');
			                	return nRow;
			                }
			                
			                else
			                {
			                	$('td:eq(0)', nRow).html('<a href="javascript:void(0)" class="copyInactive" disabled="disabled">'+ aData[1]+'</a>');
			                	return nRow;
			                }
						 </sec:authorize>
					 	 
						 <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_MANAGER')">
						 $('td:eq(0)', nRow).html('<a href="#" onclick="openMaintainance('+aData[0]+',\'' + aData[1] + '\');"> '+ aData[1]+'</a>');	
						 </sec:authorize>
				    	 return nRow; 
				    },
			        
				    "aoColumns": [ 
									{ 
										sName: "id",
										sWidth : "",
										bSortable: false,
										bVisible:false
									},
									{ 
										sName: "projectCode",
										sWidth : "",
										bSortable: false
										
									},
									{ 
										sName: "projectName",
										sWidth : "",
										bSortable: true
									},
									{ 
										sName: "customerName",
										sWidth : "",
										bSortable: true
									},
									{ 
										sName: "offshoreDelMgr",
										sWidth : "",
										bSortable: true,
									},
									{ 
										sName: "onsiteDelMgr",
										sWidth : "",
										bSortable: true,
										bVisible:false
									},
									{ 
										sName: "BuCode",
										sWidth : "",
										bSortable: false,
										bVisible:false
									},
									
									{ 
										sName: "engagementModelId",
										sWidth : "",
										bSortable: true,
										bVisible:false
									},
									 
									{ 
										sName: "projectKickOffDate",
										sWidth : "",
										bSortable: true
									},
									
									
									{ 
										sName: "projectEndDate",
										sWidth : "",
										bSortable: true,
										bVisible:false
									}
									
                      ],
                      
                      "sDom": '<"projecttoolbar">lfip<"top">rt<"bottom"<"#refresh">ip><"clear">'
                    	 
			});
	     
	     	$("thead input").keyup( function(){
	    	 projectTable.fnFilter( this.value, projectTable.oApi._fnVisibleToColumnIndex(projectTable.fnSettings(), $("thead input").index(this)));
	    	});	

	    	$("thead input").each( function(i){
	    		this.initVal = this.value;
	    	});

	    	$("thead input").focus( function () {
	    		if ( this.className == "search_init" ){
	    			this.className = "";
	    			this.value = "";
	    		}
	    	});

	    	$("thead input").blur( function (i) {
	    		if ( this.value == "" ){
	    			this.className = "search_init";
	    			this.value = this.initVal;
	    		}
	    	});
			
			$("#projectTableId_filter").hide();
			
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
		
		$(".tab_div").hide();
		$('ul.tabs a.listTab').click(function () {
			$('#MaintenanceTabInactive').off('click');
			$(".tab_div").hide().filter(this.hash).show();
			$("ul.tabs a.listTab").removeClass('active');
			$('a[href$="tab2"]').addClass('MaintenanceTab');
			$('a#MaintenanceTabInactive').text("Configure Mail");
			$(this).addClass('active');
			return false;
		}).filter(':first').click();
		
		$('a[href$="tab2"]').click(function(){
		});

		$('a[href$="tab1"]').click(function(){
  			$("div.errorNumericLast").hide();
  			$(".required").removeClass("brdrRed");
  			$("a").removeClass("brdrRed");
  			 
		});
		
		function callJSONWithErrorCheck( sSource, aoData, callback1, callback2) {
			if (typeof (callback1) != 'function' && callback1 != null) {
		                    return callback2(callback1);
		    }
		    if (callback1 == null) {
		                    callback1 = callback2;
		    }
		    try {        
		        $.ajax({
		           url: sSource,
		           type : "GET",
		                       dataType: 'json',
		                       data: aoData,
		                       success:  function (json) {  
		                           if (json != null && json != "") {
		                              callback1(json);
		                           } else {
			                         // Added for task # 216 - Start
			        					var text="Error Occured - Nothing returned from server";
			        					showAlert(text);
			        					// Added for task # 216 - End
		                           }
		           },
		                       error:  function (XMLHttpRequest, textStatus, errorThrown)  { 
		                           alert("Error Occured. \nText: " + textStatus + "\nError: " + errorThrown + "\nHTTP Status: " + XMLHttpRequest.responseText);
		                       }
		                    });   
		    } catch (err) {
		       txt="There was an error on this page.\n\n";
		       txt+="Error description: " + err.description + "\n\n";
		       txt+="Click OK to continue.\n\n";
		    }
}  

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
			$(":checkbox").on('change', function () {
			    $('[name="' + $(this).attr('name') + '"]').not(this).prop('checked', false);
			});
			
	$( ".save" ).click(function( event ) {
	 var nRow = $(this).parents('tr')[0];
	  var employees = {
	      accounting: []
	  };
	  var i =1;
	  
	 //US3089(Added by Maya): START 
	 var errorCount=0;
	 var delErrorCount=0;
	 $("table#dtbl > tbody").find('tr').each(function ()
	 {
	  var noselected = true;
	  employees.accounting=[];
	  $(this).find('td').filter(':has(:checkbox:checked)').each(function()
	  { 
		 
		  noselected = false;
		  var sData =[];
		  var to = false;
		  var cc = false;
		  var bcc = false;
		 // alert(this.id);
		  var str = (this.id).split("_");
		  if(str[2]=="to"){
			  to = true;
		  }
		  
		  if(str[2]=="cc"){
			  cc = true;
		  }
		 
		  if(str[2]=="bcc"){
			  bcc = true;
		  }
		 
		  employees.accounting.push({ 
	          "projectId" : projectId,
	          "confgId"  :  str[0] ,
	          "roleId"   :  str[1]  ,
	          "to"	: to,
	          "cc"	: cc,
	          "bcc"	: bcc
		  
		  });
	 
	  });
	  if(noselected)
		  {  
	         var confgId= $(this).find('td').attr("id");
	         $.ajax({
					type: 'DELETE',
			        url: 'mailConfiguration/'+projectId+'/'+confgId,
			     	contentType: "text/html",
			     	async:false,
			     	success: function(succeResponse) {
			     	},
			     	error: function(errorResponse)
			 	    {
			 	         delErrorCount++;
			 	    }
			 });  
		  }
	  
	 	else{  
        	   $.ajax({
						type: 'PUT',
				        url: 'mailConfiguration',
				     	contentType: "application/json",
				     	data: JSON.stringify(employees),
				     	async:false,
				     	success: function(succeResponse) { 
				     		 
				     	},
				     	error: function(errorResponse)
				 	    {
				 	        errorCount++;
				 	    }
				 }); 
				 
			}
	 });
	      if(errorCount!=0){	
		      showError("MailConfiguration cannot be  updated.");
		  }
		  else if(errorCount==0){
              showSuccess(" Mail has been configured successfully.");
		  }	
		  else if(delErrorCount!=0){	
		      showError("MailConfiguration cannot be  deleted.");
		  }
		  else if(delErrorCount==0){
              showSuccess(" MailConfiguration has been successfully deleted.");
		  }	
		//US3089(Added by Maya): END  
	});			
	
	
  });//end of document.ready function
		function getJsonString(name, value){
			if(name.indexOf(".") > -1){
				var items = name.split("." , 2);
				var jsonInner = getJsonString(items[1],value);
				var json = '"'+items[0]+ '":{'+jsonInner+'}';
				return json;
			}
			if((value == true || value == false ) &&(value != 1 || value != 0 ))
			return '"'+name+ '":'+value;
			else
				return '"'+name+ '":"'+value+'"';
			
		}
		function openMaintainance(id, projectName){
			startProgress();
			getProjectById(id);
			projectId = id;
			displayMaintainance(projectName);
			stopProgress();			
			
			
		}
		
		function showProject(data){
			
			$('.checkboxclass').each(function () {
	    		this.checked = false;
			});
	
			var id;
			for(var j =0;j<data.aaData.length;j++){
				id =data.aaData[j];
				document.getElementById(id).checked = true;
				
			}
			
		}
		
$("#projectTableId a.copyInactive,#projectTableId td").on("click", function(event){
	        
			clicks++;  //count clicks

	        if(clicks === 1) {

	            timer = setTimeout(function() {
	            	
	            	event.stopPropagation();
	    			clickable = true;
	    		    clicks = 0;  //after action performed, reset counter

	            }, DELAY);

	        } else {

	            clearTimeout(timer);  //prevent single-click action
	            
	            event.stopPropagation();
				clickable = false;
				setTimeout(function(){clickable = true;  }, 800);

	            clicks = 0;  //after action performed, reset counter
	        }

	    }).on("dblclick", function(e){
	    	event.stopPropagation();  //cancel system double-click event
	    	
	    	clickable = true;
	    });

			
		
		function getProjectById(id){
			$.getJSON("mailConfiguration/"+id,{}, showProject);
		}
		
		
			function displayMaintainance(projectName){
				
				if(clickable != true){
				}
				else{
					
					$("ul.tabs a.listTab").removeClass('active');
					$(".tab_div").hide().next("#tab2".hash).show();
					$('a#MaintenanceTabInactive').text(projectName);
					$('a[href$="tab2"]').removeClass('MaintenanceTab');
					$('a[href$="tab2"]').addClass('active');
				}
			 
		}
		
		// End To fix #189	
		
		 function handlePaginationButtons(dataTableObj, dataTableId){
	            if (dataTableObj != null) {
	                            var oSettings = dataTableObj.fnSettings();
	                            if (oSettings._iDisplayLength >= oSettings.fnRecordsDisplay()) {
	                                            $('#'+dataTableId+'_paginate').fadeTo(0,.1);
	                                            $('#'+dataTableId+'_first').css('cursor','default');
	                                            $('#'+dataTableId+'_last').css('cursor','default');
	                                            $('#'+dataTableId+'_next').css('cursor','default');
	                                            $('#'+dataTableId+'_previous').css('cursor','default');
	                                            $('.paginate_active').css('cursor','default');
	                            } else {
	                                            $('#'+dataTableId+'_paginate').fadeTo(0,1);
	                                            $('#'+dataTableId+'_first').css('cursor','pointer');
	                                            $('#'+dataTableId+'_last').css('cursor','pointer');
	                                            $('#'+dataTableId+'_next').css('cursor','pointer');
	                                            $('.paginate_active').css('cursor','pointer');
	                            }
	            }
		}
		
		function checkcust(){
			var projectCat= document.getElementById('pCId').options[document.getElementById('pCId').selectedIndex].value;
			var customerName= document.getElementById('customerNameId.id').options[document.getElementById('customerNameId.id').selectedIndex].value ;
			
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
		
		
</script>
<script id="projectTableRows" type="text/x-jsrender">
 						<tr id="{{>id}}">
  						<sec:authorize access="hasAnyRole('ROLE_DEL_MANAGER')">
			                <td align="center" valign="middle"> 
								{{if managerReadonly}}
			                	<a href="#" onclick="openMaintainance({{>id}},'{{>projectName}}');"> {{>projectCode}}</a>
								{{else}}
								<a href="javascript:void(0)" class="copyInactive" disabled="disabled"> {{>projectCode}}</a>
							{{/if}}
			                </td>
							</sec:authorize>
					 <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN','ROLE_MANAGER')">
						<td align="center" valign="middle">
   							<a href="#" onclick="openMaintainance({{>id}},'{{>projectName}}');"> {{>projectCode}}</a>
						</td>
						</sec:authorize>		
			                <td align="center" valign="middle">
							{{if projectName}}
									{{>projectName}}
								  {{else}}
									  N.A.
								{{/if}}
							</td>
			                <td align="center" valign="middle">
								{{if customerNameId}}
									{{>customerNameId.customerName}}
								  {{else}}
									  N.A.
								{{/if}}
							</td>
			               
			                 <td align="center" valign="middle">
								{{if offshoreDelMgr.employeeName}}
									{{>offshoreDelMgr.employeeName}}
								  {{else}}
									  N.A.
								{{/if}}
							</td>
			             
			             
			                 <td align="center" valign="middle">
								{{if projectKickOff}}
									{{>projectKickOff}}
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
			<td width="5%" align="center">
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
							<td width="5%" align="center"><a href="javaScript:void(0)"><img width="16" height="16" class="removePo" src="resources/images/remove.png" class="readOnlyinput" ></a></td>
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
<style>
.dataTables_filter {
	border-right: 0 !important;
}
</style>
<div class="content-wrapper">
	<!--right section-->
	<div class="botMargin">
		<h1>Mail Configuration</h1>

		<div class="fr positionRel">
			<img src="resources/images/helpIcon.png" id="helpIcon" />
			<div class="helpContent" style="display: none;">
				<h3>To enter time sheet</h3>
				<table>
					<tr>
						<td>
							<ol class="orderList">
								<li>ADMIN – If checked, Mail will be received for all
									resources.</li>
								<li>
									<ol>
										BG-ADMIN – If checked, mail will be received if
										<li class="spcleft">Resources who Submitted their
											timesheets for the project which belongs to BG Admin’s unit</li>
									</ol>
								</li>
								<li>DEL-MANAGER – If checked, Mail will be received for
									those resources which are visible on his Time Sheet Approval
									Page</li>
								<li>ROLE-MANAGER– If checked, Mail will be received for
									those resources which are visible on his Time Sheet Approval
									Page</li>
								<li>
									<ol>
										IRM – If checked, Mail will be routed to IRM of the resources:
										<br>
										<b>Case: Time sheet submission mail</b>
										<li class="spcleft">If IRM = DEL_MANAGER or BG-ADMIN or
											ADMIN, mail received with Approve / Reject link</li>
										<li class="spcleft">If IRM <> DEL_MANAGER or BG-ADMIN or
											ADMIN, mail received without Approve / Reject link. I.e.
											Information mail only</li>
									</ol>
								</li>
								<li>SRM – Same as IRM</li>
								<li>Project Manager – If checked, mail will be received for
									those resources who Submitted their timesheets for the project
									for which he is project manager</li>
								<li>User – if checked, Information mail will be received to
									the user who submitted time sheet.</li>
							</ol>
						</td>
					</tr>
				</table>
				<div class="closeHelp"></div>
				<img src="resources/images/arrowIcon.png" class="arrowIcon" />
			</div>
		</div>
	</div>
	<div class="tab_seaction">
		<ul class='tabs'>
			<li><a href='#tab1' class="listTab">List</a></li>
			<li><a class="MaintenanceTab listTab"
				id="MaintenanceTabInactive" href='#tab2'>Configure Mail</a></li>
		</ul>
		<div id='tab1' class="tab_div">
			<div class="tbl">
				<table class="dataTbl display tablesorter dataTable"
					id="projectTableId">
					<thead>
						<tr>

							<th align="center" valign="middle">Project ID</th>
							<th align="center" valign="middle">Project ID</th>
							<th align="center" valign="middle">Project Name</th>
							<th align="center" valign="middle">Customer Name</th>
							<th align="center" valign="middle">Project Manager</th>
							<th align="center" valign="middle">Onsite Manager</th>
							<th align="center" valign="middle">Current BG-BU</th>
							<th align="center" valign="middle">Engagement Model</th>
							<th align="center" valign="middle">Project Kick off Date</th>
							<th align="center" valign="middle">Project End Date</th>
						</tr>
						<tr class="">
							<td><input type="text" name="search_projectID"
								value="Project ID" class="search_init" hidden="true" /></td>
							<td><input type="text" name="search_projectCode"
								value="Project ID" class="search_init" disabled="disabled" /></td>
							<td><input type="text" name="search_projectName"
								value="Project Name" class="search_init" /></td>
							<td><input type="text" name="search_customerName"
								value="Customer Name" class="search_init" /></td>
							<td><input type="text" name="search_offshoreManager"
								value="Offshore Manager" class="search_init" /></td>
							<td><input type="text" name="search_onsiteManager"
								value="Onsite Manager" class="search_init" /></td>
							<td><input type="text" name="search_currentBG-BU"
								value="Current BG-BU" class="search_init" /></td>
							<td><input type="text" name="search_engagementModel"
								value="Engagement Model" class="search_init" /></td>
							<td><input type="text" name="search_projectKickoffDate"
								value="Project Kick off Date" class="search_init"
								disabled="disabled" /></td>
							<td><input type="text" name="search_projectEndDate"
								disabled="disabled" value="Project End Date" class="search_init" /></td>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			<div class="clear"></div>
		</div>
		<div id='tab2' class="tab_div">
			<%-- <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_BG_ADMIN')"> --%>
			<div class="search_filter">
				<div align="right">
					<a href="#" class="blue_link save" id="save"> <img
						src="resources/images/save.png" name="save" width="16" height="22" />
						Save
					</a>
				</div>
			</div>
			<%-- </sec:authorize> --%>
			<div style="overflow-x: scroll;">

				<table id="dtbl" class="dataTable dataTbl" width="100%">
					<thead>
						<tr>

							<th rowspan="2">Mail Type</th>
							<c:forEach var="roles" items="${roleList}">
								<th colspan="3" align="center" id="${roles.id}">${roles.role}
								</th>
							</c:forEach>

						</tr>

						<tr>

							<th align="center">To</th>
							<th align="center">CC</th>
							<th align="center">Bcc</th>
							<th align="center">To</th>
							<th align="center">CC</th>
							<th align="center">Bcc</th>
							<th align="center">To</th>
							<th align="center">CC</th>
							<th align="center">Bcc</th>
							<th align="center">To</th>
							<th align="center">CC</th>
							<th align="center">Bcc</th>
							<th align="center">To</th>
							<th align="center">CC</th>
							<th align="center">Bcc</th>
							<th align="center">To</th>
							<th align="center">CC</th>
							<th align="center">Bcc</th>
							<th align="center">To</th>
							<th align="center">CC</th>
							<th align="center">Bcc</th>
							<th align="center">To</th>
							<th align="center">CC</th>
							<th align="center">Bcc</th>
							<!-- <th></th> -->
						</tr>

					</thead>
					<tbody>







						<c:forEach var="confg" items="${confgList}" varStatus="status">
							<tr class="noInpWidth">
								<td width="15%" id="${confg.id}">${confg.name}</td>
								<c:forEach var="roles" items="${roleList}">
									<td align="center" width="7%" id="${confg.id}_${roles.id}_to"><input
										type="checkbox" class="checkboxclass"
										name="mailConf_${roles.id}_${confg.id}"
										value="${confg.id}_${roles.id}_mailto"
										id="${confg.id}_${roles.id}_mailto"></td>
									<td align="center" width="7%" id="${confg.id}_${roles.id}_cc"><input
										type="checkbox" class="checkboxclass"
										name="mailConf_${roles.id}_${confg.id}"
										value="${confg.id}_${roles.id}_mailcc"
										id="${confg.id}_${roles.id}_mailcc"></td>
									<td align="center" width="7%" id="${confg.id}_${roles.id}_bcc"><input
										type="checkbox" class="checkboxclass"
										name="mailConf_${roles.id}_${confg.id}"
										value="${confg.id}_${roles.id}_mailbcc"
										id="${confg.id}_${roles.id}_mailbcc"></td>
								</c:forEach>
								<!-- US3089(Added by Maya): START -->
								<c:choose>
									<c:when test="${status.count eq 5}">
										<td align="center" width="8%"></td>
									</c:when>
									<c:otherwise>
										<td hidden="hidden"></td>
									</c:otherwise>
								</c:choose>
								<!-- US3089(Added by Maya): END -->
							</tr>
						</c:forEach>

					</tbody>
				</table>
				<!--right section-->
			</div>
			<div class="clear"></div>
		</div>
	</div>
</div>



<!--START: Alert: Added by Pratyoosh Tripathi -->
<div class="notification-bar">
	<!-- <div class="close closeIconPosition">close</div> -->
	<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
	<!-- <div class="notification-text">Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.</div> -->
	<sec:authorize
		access="hasAnyRole('ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN')">
		<div class="notification-text">You can configure mail settings
			regarding your project clicking on its project id.</div>


	</sec:authorize>
	<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
		<div class="notification-text">You can configure mail settings
			regarding any project clicking on its project id.</div>


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
</style>
<script type="text/javascript">
$( document ).ready(function() {
	
	$('[data-toggle="tooltip"]').tooltip();
	
	setInterval(function(){ $('[data-toggle="tooltip"]').fadeToggle(500); }, 1000);
	
	<%Boolean flag = false;
			flag = (Boolean) session.getAttribute("notificationbarflag");%>
	
	
$('.close').click(function(){
    $('.notification-bar').hide();
   });
});
</script>

<!--END: Alert: Added by Pratyoosh Tripathi -->





<script>
$(document).ready(function(){
	var t=0;

	$('body').find('#dtbl').find('tbody').find('tr.noInpWidth').find('td:not(:first-child):not(:last-child)').each(function( i ) {	
		if(t<3)
			{
				$(this).css({background:'#9ed4e1'});
			}
		else if(t<6){
			$(this).css({background:'#71a1b7'});
			}
		 t=t+1;
		if(t==6)
			t=0;
			
	});
});
</script>



