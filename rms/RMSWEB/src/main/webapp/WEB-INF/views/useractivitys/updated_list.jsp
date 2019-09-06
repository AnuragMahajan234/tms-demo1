<%@page
	import="org.yash.rms.util.Constants,org.yash.rms.domain.Resource,java.lang.Character"%>
<%@page import="org.yash.rms.util.UserUtil"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="currentEmployeeId"
	value="<%=new UserUtil().getCurrentLoggedInUseId()%>" />
<%-- <c:set var="timesheetCommentOptional" value="<%=UserUtil.getCurrentResource().getTimesheetCommentOptional()%>"/> --%>
<spring:url value="/resources/js-framework/date.js?ver=${app_js_ver}"
	var="jquery_date_js" />
<spring:url
	value="/resources/js-user/rmsTicketPriorityStatus.js?ver=${app_js_ver}"
	var="rms_htmls_js" />
<spring:url value="/resources/js-user/validations.js?ver=${app_js_ver}"
	var="validations_js" />
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="${rms_htmls_js}"></script>
<script type="text/javascript">
     function getProjectsByEmployeeID(empId){
       	projectDetails = new Array();
       	        $.getJSON("useractivitys",{find:"findProjectsByEmployeeID" ,employeeId:empId}, function(data){
       			   
       			   $.each(data, function(i, item){
       			        //alert("PUSHING ITEM " );
       		    	 projectDetails.push(item);
       			   });
       			   activityProductivityStatus = (projectDetails[0].activities[0].productive == true)? "Productive": "Non Productive"; 
       			    $.getJSON("useractivitys",{find:"ByWeekStartDateBetweenAndEmployeeId_json" ,employeeId:empId,minWeekStartDate:weekStartingDate,maxWeekStartDate:weekEndingDate}, showUserActivities);
       	        });
       	}
</script>

<script>
var isButtonDisabled=false;
var newProjectId= new Array();
var projectEndDate= new Array();

var projectStartDate= new Array();
var addNewvar= false;
var previouvalue= false;
var firstTimeProjectChange = true;
var previousValue;
var previousId;
var projAllocId = 1;
var doj;
var weekStartingDate;
var weekEndingDate;
var allocStartFlag = 0;
var checkHours = 0; 
var userActivityTable;
var mStartDate;
var mEndDate;
var approveForNewLink = false;
var firstTime = true;
var empid='${currentEmployeeId}';

var count=0;
var countForComment=0;
var isAllTimesheetSubmittedOrApproved = [];
var currentModuleName;
var projectDetails = new Array(); 

$(document).ready(function(){

	// detect for ctrl + c to copy comment
	document.getElementById("tblActivity").addEventListener("keydown",function(e){
		e = e || window.event;
		var key = e.which || e.keyCode; // keyCode detection
		var ctrl = e.ctrlKey ? e.ctrlKey : ((key === 17) ? true : false); // ctrl detection
		
		if ( key == 67 && ctrl ) {
			copiedComment = e.target.value;
			if (e.target.value) {
				copyComment(e.target.id);
			}
			
		}
		
	});
	
	getProjectsByEmployeeID(empid);

	$("#comment").hide();
	$.fancybox.close();
	
	$("#submit").attr("disabled", true).addClass("ui-button-disabled");
	
	$("#dateSearch").datepicker({
        buttonImageOnly: true,
        changeMonth: true,
        changeYear: true,
        selectWeek:true,
        dateFormat: 'MM yy',
        onClose: function() {
                        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                        var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                        $(this).datepicker('setDate', new Date(year, month, 1));
        
                        var mMonth = parseInt(month)+1;
                        if(mMonth=="12" || mMonth=="10" || mMonth=="8"|| mMonth=="7"|| mMonth=="8"|| mMonth=="5"
                            || mMonth=="3"|| mMonth=="1"){
                            mStartDate = mMonth+"/"+"1"+"/"+year;
                            mEndDate = mMonth+"/"+"31"+"/"+year;
                        }
                        else{
                            if(mMonth=="2"){
                                if(year%4==0){
                                    mStartDate = mMonth+"/"+"1"+"/"+year;
                                    mEndDate = mMonth+"/"+"29"+"/"+year;
                                }else{
                                    mStartDate = mMonth+"/"+"1"+"/"+year;
                                    mEndDate = mMonth+"/"+"28"+"/"+year;
                                }
                            }else{
                                mStartDate = mMonth+"/"+"1"+"/"+year;
                                mEndDate = mMonth+"/"+"30"+"/"+year;
                            }
                        }
                
        },

        beforeShow: function() {
                       if ((selDate = $(this).val()).length > 0)
                   {
                          iYear = selDate.substring(selDate.length - 4, selDate.length);
                          iMonth = jQuery.inArray(selDate.substring(0, selDate.length - 5),
                                   $(this).datepicker('option', 'monthNames'));
                          $(this).datepicker('option', 'defaultDate', new Date(iYear, iMonth, 1));
                          $(this).datepicker('setDate', new Date(iYear, iMonth, 1));
                   }
               }
    });
	
	var monthSD='${param.minWeekStartDate}';
	$('#dateSearch').datepicker( "setDate" , new Date(monthSD) );   
	setStartEndDate();

    $("#dateSearch").focus(function () {
          $(".ui-datepicker-calendar").hide();
          $("#ui-datepicker-div").position({
            my: "center top",
            at: "center bottom",
            of: $(this)
          });
      });
    $('#view').click(function(){
        if(mStartDate!=null && mEndDate!=null ){
        	$('#dateSearch').datepicker( "setDate" , new Date(mStartDate) );
        	startProgress();
        //	//alert("mStartDate="+mStartDate+"mEndDate="+mEndDate+"empid="+empid);
         getUserActivitiesForMonth(mStartDate,mEndDate,empid);
        }
        else {
         ////alert('Please Select Month');
            var errorMsg ="Please Select Month"; 
              showError(errorMsg);
        }
    });
    
    function setStartEndDate() {
   	 var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
        var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
        $(this).datepicker('setDate', new Date(year, month, 1));

        var mMonth = parseInt(month)+1;
        if(mMonth=="12" || mMonth=="10" || mMonth=="8"|| mMonth=="7"|| mMonth=="8"|| mMonth=="5"
            || mMonth=="3"|| mMonth=="1"){
            mStartDate = mMonth+"/"+"1"+"/"+year;
            mEndDate = mMonth+"/"+"31"+"/"+year;
        }
        else{
            if(mMonth=="2"){
                if(year%4==0){
                    mStartDate = mMonth+"/"+"1"+"/"+year;
                    mEndDate = mMonth+"/"+"29"+"/"+year;
                }else{
                    mStartDate = mMonth+"/"+"1"+"/"+year;
                    mEndDate = mMonth+"/"+"28"+"/"+year;
                }
            }else{
                mStartDate = mMonth+"/"+"1"+"/"+year;
                mEndDate = mMonth+"/"+"30"+"/"+year;
            }
        }
   	 }
    
    $("#weekEndDate").datepicker({
        showOn: "button",
        buttonImage: "resources/images/calendar.gif",
        buttonImageOnly: true,
        changeMonth: true,
        changeYear: true,
        selectWeek:true,
        onSelect: function(dateText, inst) {
             $('#tblActivity > tbody').find('tr').remove();
             getWeekDates(dateText);
             var WeeklyDateStart = document.getElementById("weekStartDateHidden").value;
             var WeeklyDateEnd = document.getElementById("weekEndDateHidden").value;
             if(WeeklyDateStart != 0){
                $('#tHours, #addNewDiv').fadeIn(200);
             }
             var row = createTableRow(WeeklyDateStart);
             $("#dates").html('');
             $("#dates").append(row);
             var empId = document.getElementById("employeeIdHidden").value;
             $.getJSON("useractivitys",{find:"ByWeekStartDateBetweenAndEmployeeId_json" ,employeeId:empId,minWeekStartDate:WeeklyDateStart,maxWeekStartDate:WeeklyDateEnd}, showUserActivities);
             setTimeout(function(){  copyButton(); }, 600);
        },
        onClose: function() {
           var trLength = $('#tblActivity > tbody').find('tr').length;
            if(trLength == ""){
                /* $('#fa-ctotal_hrs > tfoot tr th').find('input').val('0'); */
                // Added by Nitin
                $('#total_hrs > tfoot tr th').find('input').val('');
               }
        }
    });
    
    addDataTableSearch($('#userActivityTableId'));
    
    $(".tab_div").hide();
    
    $('ul.tabs a').click(function () {
	    $(".tab_div").hide().filter(this.hash).show();
	    $("ul.tabs a").removeClass('active');
	    $('a[href$="tab2"]').addClass('MaintenanceTab');
	    $(this).addClass('active');                 
	    return false;
    }).filter(':first').click();
     
   
    $(document).on("click",'#addUpdate', hyper);
    
    function hyper(){
    
    	/* For task #1384: If project not allocated to resource, Display message! */
    	$("#NoAllocMessage").hide();
    	$("#saveValues").show();
		$("#submit").show();
    	/* checkAllocation(); */
    	
    	/* if(projAllocId == 0){
    		$("#tblActivity").hide();
    		$("#NoAllocMessage").show();
    		////alert("no allocation!");
    	} */
    	$("#comment").hide();
        //$.fancybox.close();
        //#427
        $("#submit").attr("disabled", true);
        $("#submit").addClass("ui-state-disabled");
  		$("ul.tabs a").removeClass('active');
  		$('a[href$="tab2"]').removeClass('MaintenanceTab');
        $(".tab_div").hide().next("#tab2".hash).show();
        $('a[href$="tab2"]').addClass('active');
		setTimeout(function(){  copyButton(); }, 600);
		$('#collapse_all').prop('checked', true);
        // Commented by Nitin
        
       	/* var sunTotalHours="";
       	var monTotalHours="";
    	var tueTotalHours="";
    	var wedTotalHours="";
    	var thuTotalHours="";
    	var friTotalHours="";
    	var satTotalHours="";
    	document.getElementById('sunTtl').value=sunTotalHours;
    	document.getElementById('monTtl').value=monTotalHours;
    	document.getElementById('tueTtl').value=tueTotalHours;
    	document.getElementById('wedTtl').value=wedTotalHours;
    	document.getElementById('thuTtl').value=thuTotalHours;
    	document.getElementById('friTtl').value=friTotalHours;
    	document.getElementById('satTtl').value=satTotalHours;
    	document.getElementById('weekHrs' ).value=parseFloat(sunTotalHours)+parseFloat(monTotalHours)+parseFloat(tueTotalHours)+parseFloat(wedTotalHours)+parseFloat(thuTotalHours)+parseFloat(friTotalHours)+parseFloat(satTotalHours); */
    	
    	// Added by Nitin
    	var sunTotalHours=0;
       	var monTotalHours=0;
    	var tueTotalHours=0;
    	var wedTotalHours=0;
    	var thuTotalHours=0;
    	var friTotalHours=0;
    	var satTotalHours=0;
    	document.getElementById('sunTtl').value="";
    	document.getElementById('monTtl').value="";
    	document.getElementById('tueTtl').value="";
    	document.getElementById('wedTtl').value="";
    	document.getElementById('thuTtl').value="";
    	document.getElementById('friTtl').value="";
    	document.getElementById('satTtl').value="";
    	document.getElementById('weekHrs' ).value=parseFloat(sunTotalHours)+parseFloat(monTotalHours)+parseFloat(tueTotalHours)+parseFloat(wedTotalHours)+parseFloat(thuTotalHours)+parseFloat(friTotalHours)+parseFloat(satTotalHours);

        weekStartEndDate();
        checkSendForApproval();
    }
    
	if(tempHyper==1){          //check HyperLink Flag
	 hyper();
	 }
    
    $(document).on("click", '.remove', function(e){
	    e.preventDefault();
   	 	e.stopPropagation();
    if(approveForNewLink) {
        ////alert("Time sheet for specified week  has already been approved, so can't update record");
        var warningMsg ="Time sheet for specified week has already been approved, so record(s) can't be updated"; 
          showWarning(warningMsg);
        return ;
    }
    var nRow = $(this).parents('tr')[0];
    
    var text = "Are you sure you want to delete this row ?";
    var rowID=$(this).parent().parent().attr('id');
	 
    var rrowID="r"+rowID;
    var obj = document.getElementById(rrowID);
    var id;
    
    if(obj != null) {
    id = document.getElementById(rrowID).value;
    }
    /* var WeeklyDateStart = document.getElementById("weekStartDateHidden").value;
    var WeeklyDateEnd = document.getElementById("weekEndDateHidden").value; */
    var empId = document.getElementById("employeeIdHidden").value;
    
    var thisObj = $(this);
      noty({
               text: text,
               type: 'confirm',
               dismissQueue: false,
               layout: 'center',
               theme: 'defaultTheme',
               buttons: [
                 {addClass: 'btn btn-primary', text: 'Ok', onClick: function($noty) {
                	 $("#saveValues").removeClass();
                	    $("#submit").removeClass();
                        $.noty.closeAll();
                    //Start Validate Code
                if(obj == null) {
       
                	var rowCountBeforeDelete=$('#tblActivity >tbody >tr:not(".expandable_row")').length;
                         //$(this).parent().parent().remove();
						// thisObj.parent().parent().remove();
						var currentRowId = $(thisObj).parent().parent()[0].id.substring(3);
						$('#row' + currentRowId).remove();
						$('#row_timesheet_' + currentRowId).remove();
                         $("table#tblActivity tbody").find("tr:first").find("input[type=text]").blur();
                         var rowCount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
                          
                         onDelete(rowCountBeforeDelete);
                            if(rowCount==0){
                                $('#fa-ctotal_hrs > tfoot tr th').find('input').val('');
                            	window.location.reload();
                            } 
                            $('.copy').unbind('click', false);
                            copyFlag=false;
                         return;
                }else {
               
                        var res = ($.ajax({
                            type: 'POST',
                            url: 'useractivitys/validate/'+id,
                             contentType: "application/json; charset=utf-8",
                             async:false,
                             success: function(msg) {
                                 if(msg=='Pass')
                                {
                                     
                                    if(obj==null){
                                        
										var currentRowId = $(thisObj).parent().parent()[0].id.substring(3);
										$('#row' + currentRowId).remove();
										$('#row_timesheet_' + currentRowId).remove();
                                         $('.copy').unbind('click', false);
                                         copyFlag=false;
                                         return false;
                                    }
                                    
                                    id = document.getElementById(rrowID).value;
                                    if(id!= null && id > 0){
                                  //   startProgress();
                                      //$.deleteJson_('useractivitys/delete?employeeId='+empId+'&minStartDate='+ mStartDate+'&maxStartDate='+mEndDate+'&ID='+id,{}, function(data){
                                    	  $.deleteJson_('useractivitys/delete?employeeId='+empId+'&minStartDate='+ mStartDate+'&maxStartDate='+mEndDate+'&ID='+id,{}, function(data){
                                    	 showSuccess(successMsg);
                                        //  refreshGrid();
                                          var htmlVar='';
                                         $('#userActivityTableId').empty();
                      					htmlVar += '<tbody>'; 
										
                      					for (var i = 0; i < data.length; i++) {
            	
                      						var obj = data[i];
                      						 
                      						htmlVar += "<tr onclick=openMaintainance("+obj.employeeId.employeeId+","+obj.resourceAllocId.id+",'"+obj.weekStartDate+"') >";  <%-- //,'${obj.resourceAllocId}',<fmt:formatDate value="'+${obj.weekStartDate}+'" type='"date"' "pattern="<%=Constants.DATE_PATTERN %>"/>);">'; --%>
                      						htmlVar += '<td align="center"><a href="#">'+obj.resourceAllocId.projectId.projectName+'</td>';
                      						htmlVar += '<td align="center">'+obj.weekEndDate+'</td>';
                      						htmlVar += '<td align="center">'+obj.repHrsForProForWeekEndDate+'</td>';
                      						if(obj.approveStatus ==  'N'){
												//<img title="Not Submitted" src="resources/images/notsubmitted.png" />
                      							htmlVar += '<td align="center" valign="middle"><i class="fa fa-check-circle saved" title="Not submitted"></i></td>';
                      						}
                      						if(obj.approveStatus ==  'P'){
												  //<img title="Not Approved" src="resources/images/pending.png" />
                      							htmlVar += '<td align="center" valign="middle"><i class="fa fa-exclamation-circle review" title="Review Pending"></i></td>';
                      						}
                      						if(obj.approveStatus ==  'R'){
												  //<img title="Rejected" src="resources/images/reject_timesheet.png" />
                      							htmlVar += '<td align="center" valign="middle"><i class="fa fa-times-circle rejected" title="Rejected"></i></td>';
                      						}
                      						htmlVar += "</tr>";
                      					}
                      					
                      					htmlVar += '</tbody>';
                      					$('#userActivityTableId').append(htmlVar);

                      					  $('#userActivityTableId').DataTable(
                  								{
                  									"bProcessing" : true,
                  									"bAutoWidth" : false,
                  									"bScrollCollapse" : true,
                  									"bPaginate" : true,
                  									"bDestory" : true,
                  									"bRetrieve" : true,
                  									"bStateSave": true,
                  									//  "sDom": '<"projecttoolbar">lfrtip',
                  									"aaSorting" : [ [ 0, 'asc' ] ],
                  									"bDeferRender" : true,
                  									"oLanguage" : {
                  										"sLengthMenu" : 'Show <SELECT>'
                  												+ '<OPTION value=10>10</OPTION>'
                  												+ '<OPTION value=25>25</OPTION>'
                  												+ '<OPTION value=50>50</OPTION>'
                  												+ '<OPTION value=100>100</OPTION>'
                  												+ '<OPTION value=200>200</OPTION></SELECT> entries'
                  									},

                  									"bSortCellsTop" : true,

                  									//"sDom": '<"projecttoolbar">lfrtip'
                  									"sDom" : '<"projecttoolbar">lfip<"top">rt<"bottom"<"#refresh">ip><"clear">'
                  								// "sDom": '<"top"i>rt<"bottom"<"#refresh">flp><"clear">'
                  					 }); 
                    					 $('#userActivityTableId').DataTable({
                   						 "bDestroy": true,
                   						"bStateSave": true,
                   						 aaSorting: [[0, 'desc']],							 
                   						 "bAutoWidth": false,
                   					 });  
                    					 
									   }, 'json');
									   
									  var currentRowId = $(thisObj).parent().parent()[0].id.substring(3);
										$('#row' + currentRowId).remove();
										$('#row_timesheet_' + currentRowId).remove();

                                      $("table#tblActivity tbody").find("tr:first").find("input[type=text]").blur();
                                      var rowCount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
                                        if(rowCount==0){
                                            $('#fa-ctotal_hrs > tfoot tr th').find('input').val('');
                                          
                                        }    
                                        $('.copy').unbind('click', false);
                                      	copyFlag=false;
                                        stopProgress();
                                        var successMsg ="Time Sheet Entry has been successfully deleted";
//                                         showSuccess(successMsg);
										var rowCountNew = $('#tblActivity >tbody >tr').length;
										if(rowCountNew == 0)
											{
											  window.location.reload();
											}
                                        
                                    }
                                }else{
                                    var warningMsg = "Time sheet(s) for specified Week End Date and project has already been processed by Manager so can't remove all time sheet(s) for the specified week end date and project.";
                                    showWarning(warningMsg);
                                    return false;
                            }
                                 
                           }
                    }));
            }
        //End Validate Code
                 }
                 },
                 {addClass: 'btn btn-danger', text: 'Cancel', onClick: function($noty) {
                     $noty.close();
                  }
                 }
               ],
            closeWith:['Button']
             });


     //take first row of table, fire blur event on all columns of that row
    $("table#tblActivity tbody").find("tr:first").find("input[type=text]").blur();
	});
   
   // $("#comment").hide();
	//$.fancybox.close();
	//#427
	////alert("ready 3");
	// $("#submit").attr("disabled", true).addClass("ui-button-disabled");
       	 //$("#buttons").find("input").attr("disabled","disabled");
      		 //$("#submit").find('input[type="button"]').button({disabled:true});
    		 //$("#submit").removeClass();
	 $("#buttons").find('input[type="button"]').button({disabled:true});
	$('#ok').on('click', function(){
		$.fancybox.close();
	});
		
    var copyData= new Array();
    $('#tblActivity').on('click','.copy',function(e) { 
    /* $('.copy').on('click',function(e) { */  
					$("#comment").hide();
					$.fancybox.close();
					e.preventDefault();
					var rows = $(this).closest('tr');
					var nRow = $(this).parents('tr')[0];
			 
					copyData=[];
					var selectValues=[];
					var textInputValues=[];
					var textAreaValues=[];
					var newRow;
					var $options;
				     var idActivity;
				     var idModule;
				     var idSubModule;
				     var $moduleoptions;
				     var $submoduleoptions;
				     var idTicketPriority;
				     var idTicketStatus;
				     var $ticketPriorityOptions;
					 var $ticketStatusOptions;
					 var WeeklyDateStart=document.getElementById("weekStartDateHidden").value;
					var logInputValues=[];
					//tempId=$("table#tblActivity tbody").find("tr").length;
					var testId=$('table#tblActivity > tbody > tr:not(".expandable_row"):last').attr('id').substring(3);
					tempId=+testId+1;
					//  var newRow="<tr title='' id='row"+tempId+"'>";
					 $(this).closest('tr').find('td').each(function(){
					 
						var selectIds = $(this).find("select").attr("id");
						
						if(selectIds!=undefined){
						       if(selectIds.indexOf('activityId')!=-1){
						        idActivity=selectIds;
						        }
						       if(selectIds.indexOf('module')!=-1){
						    	   idModule=selectIds;
							   }

							    if(selectIds.indexOf('subModule')!=-1){
						    	   idSubModule=selectIds;
							   } 
							if(selectIds.indexOf('ticketPriorities')!=-1){
							    	idTicketPriority=selectIds;
							}
							if(selectIds.indexOf('ticketStatus')!=-1){
							    	idTicketStatus=selectIds;
							};							    
						}
						
						if(selectIds!=null){
							if(selectIds.indexOf('resourceAllocIdrow')!=-1){
								selectValues[0]=document.getElementById(selectIds).value;
							}
							if(selectIds.indexOf('activityId')!=-1){
								selectValues[1]=document.getElementById(selectIds).value;
						    }
						    if(selectIds.indexOf('module')!=-1){
						    	selectValues[2]=document.getElementById(selectIds).value;
							}
							
							if(selectIds.indexOf('subModule')!=-1){
								selectValues[3]=document.getElementById(selectIds).value;
							}
							if(selectIds.indexOf('ticketPriorities')!=-1){
								selectValues[4]=document.getElementById(selectIds).value;
							}
							if(selectIds.indexOf('ticketStatus')!=-1){
								selectValues[5]=document.getElementById(selectIds).value;
							};							
						}		

						var textInput=$(this).find("input").attr("id");
						if(textInput!=null)
						{
							textInputValues.push(document.getElementById(textInput).value);
						}
						
				   });
				var selectedChildRowId = nRow.id.substring(3);
				$('#row_timesheet_' + selectedChildRowId ).find('.input_log').each(function(){
					var textInput=$(this).attr("id");
					 if(textInput!=null)
						 {
							logInputValues.push(document.getElementById(textInput).value);
						 }
				 });

				 $('#row_timesheet_' + selectedChildRowId ).find('.comments_textarea').each(function(){
					 var textArea=$(this).attr("id");
					 if(textArea!=null)
						{
							textAreaValues.push(document.getElementById(textArea).value);
						}
				 });

					//Added for status NULL	  
					// var value2='${activities[0].productive}';
				    // var varValue2;
				    // var projectName="";
				    // if(value2==false || value2=="false"){
				   	//    varValue2="Productive";
				    // }else{
				   	//    varValue2="Non Productive";
				    // }
				    // setTimeout(function(){  document.getElementById("activityDesc"+tempId).innerHTML =varValue2; }, 100); 
			    							 
				  var newRow="<tr title='' id='row"+tempId+"'>"; 
				  newRow+=" <td><div id='activityDesc"+tempId+"' align='left' style='background: white; width =860; height =350; color: #3377BB;font-weight: bold' >"+varValue2+"</div></td>"; 
				  //End
					     newRow+=" <td><select class='chzn-select dd_cmn comboselect' name='entries["+tempId+"].resourceAllocId' onchange='getActiveActivityByProjectId(value, id);getActiveModulesByProjectIdOnProjectChange(value,"+tempId+");getTicketPriorityAndStatusByProjectIdOnProjectChange(value,"+tempId+")' id='resourceAllocIdrow"+tempId+"'>,/td>";
					     <c:forEach var='projects' items='${resourceAllocation}'> 
					        var dte="<fmt:formatDate value='${projects.allocEndDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"
					        var allocEndDate = Date.parse(dte);
					        var dte="<fmt:formatDate value='${projects.allocEndDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"
					        var strdte="<fmt:formatDate value='${projects.allocStartDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"

						        var allocEndDate = Date.parse(dte); 
						        var allocStartDate = Date.parse(strdte); 
						        if(validateDateFunctionForNew('${projects.allocStartDate}', '${projects.allocEndDate}')) {
						             projectEndDate.push(allocEndDate);
						             projectStartDate.push(allocStartDate);
						               newProjectId.push(eval('${projects.id}'));    
						               if(selectValues[0]=='${projects.id}'){
						             newRow+="<option  value='${projects.id}' selected='selected' >${projects.projectId.projectName}</option>";
						             projectName = '${projects.projectId.projectName}';
						              
						               }else{
						                 newRow+="<option  value='${projects.id}'  >${projects.projectId.projectName}</option>";
						               }
						         }
						        </c:forEach>
						        newRow+="</select></td>"; 
						        newRow+="<td valign='middle'>";
						        newRow+="<select name='entries["+tempId+"].activityId' class='dd_cmn comboselect' id='activityId"+tempId+"'>";
						        /* if(selectValues[1]=='${activity.id}')
						        newRow+="<option value='${activity.id}' selected='selected'>${activity.activityName}</option>";
						        else */
						        	/* newRow+="<option value='${activity.id}'>${activity.activityName}</option>"; */
						        	$options = $("#"+idActivity+" > option").clone();
						        	if($("#"+idActivity).val()==null)
						        	{
						        		showError("Activity not set for this project, Please connect to your PM.");
						                return;
						        	}
									newRow+="</select></td>";
									
									newRow+="<td valign='middle' align='center'>";
						        	var flag= 0;
						        	var flag1=0;
						        	 var mystr = "";
						        	if(selectValues[2]!= undefined && selectValues[2]!='' ){
						        		flag=1;
						        		mystr = '"'+selectValues[2]+'"';
						        		newRow+="<select name='entries["+tempId+"].module' class='dd_cmn comboselect' id='module"+tempId+"' onchange=getActiveSubModulesByModuleId(this.value,"+ tempId+");>";
						        		$moduleoptions = $("#"+idModule+" > option").clone();
						        		newRow+="</select></td>";
						        		} 
						        	else{
						        		newRow+="<input type='text'  name='entries["+tempId+"].module'  id='module"+tempId+"' value='"+textInputValues[0]+"' onfocus=getActiveModulesByProjectId("+selectValues[0]+","+tempId+");></td>";
						        		mystr = '"'+textInputValues[0]+'"';
						        	}
						        
						           newRow+="<td valign='middle' align='center'>";
						               if(selectValues[3]!= undefined && selectValues[3]!='' ){
						        		flag1=1;
						        		newRow+="<select name='entries["+tempId+"].subModule' class='dd_cmn comboselect' id='subModule"+tempId+"'>";
						        		$submoduleoptions = $("#"+idSubModule+" > option").clone();
						        		newRow+="</select></td>";
						        		} 
						        	else{
						        	   var addFocusFunc = "onfocus='getActiveSubModulesByModuleId("+mystr+","+ tempId+",value,2);'";
						        	   if(flag==1)
						        	   {
						        		   newRow+="<input type='text' "+addFocusFunc+"  name='entries["+tempId+"].subModule'  id='subModule"+tempId+"' value='"+textInputValues[0]+"'></td>";
						        	   }
						        	   else{
						        		   newRow+="<input type='text' "+addFocusFunc+" name='entries["+tempId+"].subModule'  id='subModule"+tempId+"' value='"+textInputValues[1]+"'></td>";
						        	   }						        	   
						        	   	
						        	}   
						        
						        var index=2;
						        if(flag==1)
						        {
						        	index=1;
						        }
						        
						        if(flag==1 && flag1==1)
						        {
						        		index=0;
						        }
						        var flag2=0;
						        newRow+="<td valign='middle' align='center'><input type='text'  name='entries["+tempId+"].ticketNo'  id='ticketNo"+tempId+"' value='"+textInputValues[index]+"' onChange='getTicketPriorityAndStatus("+tempId +")';></td>";
						        // adding ticketPriority and ticketStatus on copy of row
						         
						        newRow+="<td valign='middle' align='center'>";
						               if(selectValues[4]!= undefined){
							        		flag2=1;
							        		var disabled="";
							        		if(selectValues[4]== ''){
							        			disabled="disabled";
							        		}
							        		newRow+="<select "+disabled+" name='entries["+tempId+"].ticketPriority' class='dd_cmn comboselect' id='ticketPriorities"+tempId+"'>";
							        		$ticketPriorityOptions = $("#"+idTicketPriority+" > option").clone();
							        		newRow+="</select></td>";
						        		}else{		        		   
							        	 	index = parseInt(index)+1;
							        	 	var priorVal = '"'+textInputValues[index]+'"';
							        	 	newRow+="<input type='text'  name='entries["+tempId+"].ticketPriority'  id='ticketPriorities"+tempId+"' value='"+textInputValues[index]+"' onfocus='getTicketPriorityByProjectName(1,"+tempId+","+priorVal+")';></td>";
						        		} 	
						       newRow+="<td valign='middle' align='center'>";
						               if(selectValues[5]!= undefined){
							        		flag2=1;
							        		var disabled="";
							        		if(selectValues[5]== ''){
							        			disabled="disabled";
							        		}
							        		newRow+="<select "+disabled+" name='entries["+tempId+"].ticketStatus' class='dd_cmn comboselect' id='ticketStatus"+tempId+"'>";
							        		$ticketStatusOptions = $("#"+idTicketStatus+" > option").clone();
							        		newRow+="</select></td>";
						        		}else{
						        			index = parseInt(index)+1;
						        			var statusVal = '"'+textInputValues[index]+'"';
						        			newRow+="<input type='text'  name='entries["+tempId+"].ticketStatus'  id='ticketStatus"+tempId+"' value='"+textInputValues[index]+"' onfocus='getTicketStatusByProjectName(1,"+tempId+","+statusVal+")';></td>";						        									        	   
							        	}	
								/* commented by mustafa
						        newRow+="<td width='5%' valign='middle' align='left' class='sunT'>";
						        newRow+="<div class='poRel'>";
						        newRow+="<input type='text'  name='entries["+tempId+"].d1Hours' onkeyup='checkSendForApproval();'  onfocus='checkSendForApproval();' onblur='validateHour(this);checkHour(this);checkSendForApproval();' maxlength='5' id='d1Hoursrow"+tempId+"' class='small' value='"+textInputValues[index+1]+"'/><img src='resources/images/comments.png' width='24' height='23' class='comments' />";
						        newRow+="<div class='comments_div'>";
						        newRow+="<textarea name='entries["+tempId+"].d1Comment' id='d1Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''>"+textAreaValues[0]+"</textarea>";
						        newRow+="<div class='clear'></div>";
						        newRow+="<div class='btns'>";
						        newRow+="<input type='button' value='Ok' class='okBtn' />";
						        //newRow+="<input type='reset' value='Cancel' class='btn desCancel' />";
						        newRow+="</div>";
						        newRow+="</div>";
						        newRow+="</div></td>";
						        newRow+="<td width='5%' class='monT' valign='middle' align='left'><div class='poRel'>";
						        newRow+="<input type='text' class='small' maxlength='5' onkeyup='checkSendForApproval();'  onfocus='checkSendForApproval();' onblur='validateHour(this);checkHour(this);(this);checkSendForApproval();' name='entries["+tempId+"].d2Hours'  id='d2Hoursrow"+tempId+"' value='"+textInputValues[index+2]+"' />";
						        newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
						        newRow+="<div class='comments_div'>";
						        newRow+="<textarea name='entries["+tempId+"].d2Comment' id='d2Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''>"+textAreaValues[1]+"</textarea>";
						        newRow+="<div class='clear'></div>";
						        newRow+="<div class='btns'>";
						        newRow+="<input type='button' value='Ok' class='okBtn' />";
						        //newRow+="<input type='button' value='Cancel' class='btn desCancel' />";
						        newRow+="</div></div></div></td>";
						        newRow+="<td width='5%' class='tueT' valign='middle' align='left'><div class='poRel'>";
						        newRow+="<input type='text' class='small' name='entries["+tempId+"].d3Hours'   maxlength='5' onkeyup='checkSendForApproval();'  onfocus='checkSendForApproval();' onblur='validateHour(this);checkHour(this);(this);checkSendForApproval();' id='d3Hoursrow"+tempId+"' value='"+textInputValues[index+3]+"' />";
						        newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
						        newRow+="<div class='comments_div'>";
						        newRow+="<textarea name='entries["+tempId+"].d3Comment' id='d3Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''>"+textAreaValues[2]+"</textarea>";
						        newRow+="<div class='clear'></div>";
						        newRow+="<div class='btns'>";
						        newRow+="<input type='button' value='Ok' class='okBtn' />";
						        //newRow+="<input type='button' value='Cancel'  class='btn desCancel' />";
						        newRow+="</div></div></div></td>";
						        newRow+="<td width='5%' class='wedT' valign='middle' align='left'><div class='poRel'>";
						        newRow+="<input type='text' class='small' name='entries["+tempId+"].d4Hours'  maxlength='5' onkeyup='checkSendForApproval();' onfocus='checkSendForApproval();' onblur='validateHour(this);checkHour(this);(this);checkSendForApproval();' id='d4Hoursrow"+tempId+"' value='"+textInputValues[index+4]+"' />";
						        newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
						        newRow+="<div class='comments_div'>";
						        newRow+="<textarea name='entries["+tempId+"].d4Comment' id='d4Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''>"+textAreaValues[3]+"</textarea>";
						        newRow+="<div class='clear'></div>";
						        newRow+="<div class='btns'>";
						        //newRow+="<input type='button' value='Ok' class='okBtn' /><input type='button' value='Cancel' class='btn desCancel' />";
						        newRow+="<input type='button' value='Ok' class='okBtn' />";
						        newRow+="</div></div></div></td>";
						        newRow+="<td class='thuT' width='5%' valign='middle' align='left'><div class='poRel'>";
						        newRow+="<input type='text' class='small' name='entries["+tempId+"].d5Hours'  maxlength='5' onkeyup='checkSendForApproval();'  onfocus='checkSendForApproval();' onblur='validateHour(this);checkHour(this);(this);checkSendForApproval();' id='d5Hoursrow"+tempId+"' value='"+textInputValues[index+5]+"' />";
						        newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
						        newRow+="<div class='comments_div'>";
						        newRow+="<textarea name='entries["+tempId+"].d5Comment' id='d5Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''>"+textAreaValues[4]+"</textarea>";
						        newRow+="<div class='clear'></div>";
						        newRow+="<div class='btns'><input type='button' value='Ok' class='okBtn' /></div></div>";
						        //<input type='button' value='Cancel' class='btn desCancel' /></div></div>";
						        newRow+="</div></td>";
						        newRow+="<td width='5%' valign='middle' align='left' class='friT'><div class='poRel'>";
						        newRow+="<input type='text' class='small' name='entries["+tempId+"].d6Hours'  maxlength='5' onkeyup='checkSendForApproval();'  onfocus='checkSendForApproval();' onblur='validateHour(this);checkHour(this);(this);checkSendForApproval();' id='d6Hoursrow"+tempId+"' value='"+textInputValues[index+6]+"' />";
						        newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
						        newRow+="<div class='comments_div'>";
						        newRow+="<textarea name='entries["+tempId+"].d6Comment' id='d6Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''>"+textAreaValues[5]+"</textarea>";
						        newRow+="<div class='clear'></div>";
						        newRow+="<div class='btns'>";
						        newRow+="<input type='button' value='Ok' class='okBtn' />";
						        newRow+="</div></div></div></td>";
						        //newRow+="<input type='button' value='Cancel' class='btn desCancel' /></div></div></div></td>";
						        newRow+="<td width='5%' valign='middle' align='left' class='satT'><div class='poRel'>";
						        newRow+="<input type='text' class='small' name='entries["+tempId+"].d7Hours'  maxlength='5'  onblur='validateHour(this);checkHour(this);' id='d7Hoursrow"+tempId+"' value='"+textInputValues[index+7]+"' />";
						        newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
						        newRow+="<div class='comments_div'>";
						        newRow+="<textarea name='entries["+tempId+"].d7Comment' id='d7Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''>"+textAreaValues[6]+"</textarea>";
						        newRow+="<div class='clear'></div>";
						        newRow+="<div class='btns'>";
						        //newRow+="<input type='button' value='Ok' class='okBtn' /><input type='button' value='Cancel' class='btn desCancel' />";
						        newRow+="<input type='button' value='Ok' class='okBtn' />";
						        newRow+="</div></div></div></td>";
						        newRow+="<input type='hidden' id='approveField"+tempId+"' value='N'/>";
						        newRow+="<td valign='middle' align='center'><img width='16' height='16' class='remove' src='resources/images/remove.png'></td>";
								newRow+= "<td valign='middle' align='center'> <a href='' class='copy' >Copy</a></td>";
								*/
						        newRow+="<input type='hidden' id='approveField"+tempId+"' value='N'/>";

								newRow+="<td valign='middle' align='center' class='action'><i class='fa fa-chevron-circle-down' onclick='toggleChildRow(this)' title='expand'></i> <i class='copy fa fa-copy' title='copy record'></i><i class='fa fa-trash remove' title='delete record'></i></td>";

								newRow+="</td>"; 
								newRow+="</tr>";
								
								newRow+="<tr id='row_timesheet_"+(tempId)+"' class='expandable_row'><td colspan="+9+">";
								newRow+='<table cellpadding="0" cellspacing="0" border="0" style="width: 100%;">'+
								'<tr><td>Day</td><td align="center">Sunday</td><td align="center">Monday</td><td align="center">Tuesday</td><td align="center">Wednesday</td><td align="center">Thursday</td><td align="center">Friday</td><td align="center">Saturday</td><td  align="center">Log Time</td></tr>'+
								'<tr><td>Date</td>'+createTableRowNew(WeeklyDateStart)+'</tr>' +
								'<tr class="logHrs"><td>Log Time (in hours)</td>'+createTimeLog(tempId, logInputValues, '')+'</tr>' +	 
								'<tr><td>Comments</td>'+createLogComments(tempId,textAreaValues, '')+'</tr>' +	 	 
								'</table>';
								newRow+="</td></tr>"; 						   
					 
						//   //alert(newRow);
						    $("#tblActivity > tbody").append(newRow);
						    $('#activityId'+tempId).append($options);
						    $('#module'+tempId).append($moduleoptions);
						    $('#subModule'+tempId).append($submoduleoptions);
						    $('#ticketPriorities'+tempId).append($ticketPriorityOptions);
						    $('#ticketStatus'+tempId).append($ticketStatusOptions);
							$("#tblActivity>tbody").find("tr:even").addClass("even");
							$("#tblActivity>tbody").find("tr:odd").addClass("odd");
						    checkHour(null);
						    changeCommentIcon();
						    checkSendForApproval();
						 // commenting this whole section: requirement -need to fill timesheet for future dates
							//checkCurrentDate(eval('${today}'));

						    var value2=$('#activityId' + tempId).val().split('_')[2];
						    var varValue2;
						    if(value2==false || value2=="false"){
						   	   varValue2="Non Productive";
						    }else{
						   	   varValue2="Productive";
							}
							
						    setTimeout(function(){  document.getElementById("activityDesc"+tempId).innerHTML =varValue2; }, 100);
					
					
			     });
	
    $(document).on("click",'#addNewRow1', function(){
		isButtonDisabled=false; 
    	$("#comment").hide();
    	//$.fancybox.close();
    	document.getElementById("comment").style.display = "none";
    	document.getElementById("comment").style.visibility = "hidden";

    	var weekEndDateVal = $("#weekStartDate").val();
        if(weekEndDateVal == '') {
            ////alert('Please select End Date');
            // Added for task # 216 - Start
      var text="Please select End Date";
      showAlert(text);
      // Added for task # 216 - End
        }
        
        var startTime = Date.parse(weekEndDateVal).getTime();
        var currentTime = new Date().getTime();
     // commenting this whole section: requirement -need to fill timesheet for future dates
       /*  if(startTime > currentTime) {
            ////alert("Can't add time sheet for future week.");
            var errorMsg = "Sorry! You can't add time sheet(s) for future week(s).";
            showError(errorMsg);
            return;
        } */
         if(approveForNewLink) {
            ////alert("Time sheet for specified week has already been approved, so can't update record");
            var warningMsg = "Time sheet for specified week has already been approved, so can't update record";
            showWarning(warningMsg);
            return ;
        } 
         addNewvar=true;
     
        // tempId= $("table#tblActivity tbody").find("tr").length;
        
       //raghuraj start from here 12/july/2017        
      if($('table#tblActivity tbody tr:last').attr('id') !== 'undefined')
    	  {
	    	  var lastRow = $('table#tblActivity tbody tr:last').attr('id');
	          tempId = parseInt(lastRow.replace ( /[^\d.]/g, '' ))+1;
    	  }else{
    		  tempId = 0;
    	  }
      
       //raghuraj end from here 12/july/2017  
       
        var newRow="<tr title='' id='row"+tempId+"'>"; 
        newRow+=" <td><div id='activityDesc"+tempId+"' align='left' style='background: white; width =860; height =350; color: #3377BB;font-weight: bold' ></div></td>" ;
        newRow+=" <td><select class='chzn-select dd_cmn comboselect' name='entries["+tempId+"].resourceAllocId' onchange='getActiveActivityByProjectId(value, id);getActiveModulesByProjectIdOnProjectChange(value,"+tempId+");getTicketPriorityAndStatusByProjectIdOnProjectChange(value,"+tempId +");' id='resourceAllocIdrow"+tempId+"'>";
        <c:forEach var='projects' items='${resourceAllocation}'> 
        var dte="<fmt:formatDate value='${projects.allocEndDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"
        var allocEndDate = Date.parse(dte);
        var dte="<fmt:formatDate value='${projects.allocEndDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"
        var strdte="<fmt:formatDate value='${projects.allocStartDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"
        var allocEndDate = Date.parse(dte);        
        var allocStartDate = Date.parse(strdte); 
        if(validateDateFunctionForNew('${projects.allocStartDate}', '${projects.allocEndDate}')) {
             projectEndDate.push(allocEndDate);
             projectStartDate.push(allocStartDate);
               newProjectId.push(eval('${projects.id}'));     
             newRow+="<option  value='${projects.id}' >${projects.projectId.projectName}</option>";
             
         }
        </c:forEach>
        newRow+="</select></td>";
        newRow+="<td valign='middle'>";
        newRow+="<select name='entries["+tempId+"].activityId' class='dd_cmn comboselect' id='activityId"+tempId+"'></select></td>";
       var value1='${activities[0].productive}';
       var varValue;
       if(value1==false || value1=="false"){
    	   varValue="Productive";
       }else{
    	   varValue="Non Productive";
       }
       
       
       newRow+="<td valign='middle' align='center'>"
      <c:forEach var='projects' items='${resourceAllocation}'> 
      var dte="<fmt:formatDate value='${projects.allocEndDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"
      var allocEndDate = Date.parse(dte);
      var dte="<fmt:formatDate value='${projects.allocEndDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"
      var strdte="<fmt:formatDate value='${projects.allocStartDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"
      var allocEndDate = Date.parse(dte);        
      var allocStartDate = Date.parse(strdte); 
      if(validateDateFunctionForNew('${projects.allocStartDate}', '${projects.allocEndDate}')) {
           var activeModuleFlag=false;
           var moduleExist=false; 
           var tempHtml='';
           <c:choose>
           <c:when test='${not empty projects.projectId.module}'> 
           moduleExist=true;
           tempHtml+="<select name='entries["+tempId+"].module' class='dd_cmn comboselect' id='module"+tempId+"' onChange=getActiveSubModulesByModuleId(this.value,"+tempId+");>";
           tempHtml+="<option  value='-1' >Please Select</option>";
           <c:forEach var='module' items='${projects.projectId.module}'> 
           <c:if test="${module.active == true}">
           tempHtml+="<option  value='${module.moduleName}' >${module.moduleName}</option>";
           activeModuleFlag=true;
           </c:if>
           </c:forEach>
           tempHtml+="</select></td>";
           </c:when> 
           <c:otherwise>
           tempHtml+="<input type='text'  name='entries["+tempId+"].module'  id='module"+tempId+"'></td>";
           </c:otherwise>
            </c:choose>
            if(moduleExist==true && activeModuleFlag == false)
            {   tempHtml="";
                tempHtml+="<input type='text'  name='entries["+tempId+"].module'  id='module"+tempId+"'></td>";
            }
            newRow+=tempHtml;  
      }
      </c:forEach>
 
       newRow+="<td valign='middle'>";
       var isModuleExist=$("#module"+tempId+" option:eq(1)").val();
       if(typeof isModuleExist != "undefined"){
       //if(moduleExist==true && activeModuleFlag == true){
       newRow+="<select name='entries["+tempId+"].subModule' class='dd_cmn comboselect' id='subModule"+tempId+"'><option  value='-1' >Please Select</option></select></td>";
       }else{  
    	 newRow+="<input type='text'  name='entries["+tempId+"].subModule'  id='subModule"+tempId+"'></td>";
       }
        newRow+="<td valign='middle' align='center'><input type='text'  name='entries["+tempId+"].ticketNo'  id='ticketNo"+tempId+"' onChange='getTicketPriorityAndStatus("+tempId +")';></td>";
//      adding ticket priority and and ticket status in current row @pankaj birla
        newRow+="<td valign='middle' align='center'>";
        newRow+="<div id='divticketPriorities_"+tempId+"'>";  
        newRow+="<select disabled name='entries["+tempId+"].ticketPriority' class='dd_cmn comboselect' id='ticketPriorities"+tempId+"'>";
        newRow+="<option  value='' >Please Select</option>"; 
        newRow+="</select>";
        newRow+="</div>";
        newRow+="</td>";
        newRow+="<td valign='middle' align='center'>";
        newRow+="<div id='divticketStatus_"+tempId+"'>"; 
        newRow+="<select disabled name='entries["+tempId+"].ticketStatus' class='dd_cmn comboselect' id='ticketStatus"+tempId+"'>";
        newRow+="<option  value='' >Please Select</option>";
        newRow+="</select>";
        newRow+="</div>";
        newRow+="</td>";
        newRow+="<td width='5%' valign='middle' align='left' class='sunT'>";
        newRow+="<div class='poRel'>";
        newRow+="<input type='text'  name='entries["+tempId+"].d1Hours' onkeyup='checkSendForApproval();'  onfocus='checkSendForApproval();'  onblur='validateHour(this);checkHour(this);checkSendForApproval();' maxlength='5' id='d1Hoursrow"+tempId+"' class='small' value=''/><img src='resources/images/comments.png' width='24' height='23' class='comments' />";
        newRow+="<div class='comments_div'>";
        newRow+="<textarea name='entries["+tempId+"].d1Comment' id='d1Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''></textarea>";
        newRow+="<div class='clear'></div>";
        newRow+="<div class='btns'>";
        newRow+="<input type='button' value='Ok' class='okBtn' />";
        //newRow+="<input type='reset' value='Cancel' class='btn desCancel' />";
        newRow+="</div>";
        newRow+="</div>";
        newRow+="</div></td>";
        newRow+="<td width='5%' class='monT' valign='middle' align='left'><div class='poRel'>";
        newRow+="<input type='text' class='small' maxlength='5' onblur='validateHour(this);checkHour(this);(this);checkSendForApproval();' name='entries["+tempId+"].d2Hours'  id='d2Hoursrow"+tempId+"' value='' />";
        newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
        newRow+="<div class='comments_div'>";
        newRow+="<textarea name='entries["+tempId+"].d2Comment' id='d2Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''></textarea>";
        newRow+="<div class='clear'></div>";
        newRow+="<div class='btns'>";
        newRow+="<input type='button' value='Ok' class='okBtn' />";
        //newRow+="<input type='button' value='Cancel' class='btn desCancel' />";
        newRow+="</div></div></div></td>";
        newRow+="<td width='5%' class='tueT' valign='middle' align='left'><div class='poRel'>";
        newRow+="<input type='text' class='small' name='entries["+tempId+"].d3Hours'   maxlength='5' onkeyup='checkSendForApproval();'  onfocus='checkSendForApproval();' onblur='validateHour(this);checkHour(this);(this);checkSendForApproval();' id='d3Hoursrow"+tempId+"' value='' />";
        newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
        newRow+="<div class='comments_div'>";
        newRow+="<textarea name='entries["+tempId+"].d3Comment' id='d3Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''></textarea>";
        newRow+="<div class='clear'></div>";
        newRow+="<div class='btns'>";
        newRow+="<input type='button' value='Ok' class='okBtn' />";
        //newRow+="<input type='button' value='Cancel'  class='btn desCancel' />";
        newRow+="</div></div></div></td>";
        newRow+="<td width='5%' class='wedT' valign='middle' align='left'><div class='poRel'>";
        newRow+="<input type='text' class='small' name='entries["+tempId+"].d4Hours'  maxlength='5' onkeyup='checkSendForApproval();' onfocus='checkSendForApproval();' onblur='validateHour(this);checkHour(this);(this);checkSendForApproval();' id='d4Hoursrow"+tempId+"' value='' />";
        newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
        newRow+="<div class='comments_div'>";
        newRow+="<textarea name='entries["+tempId+"].d4Comment' id='d4Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''></textarea>";
        newRow+="<div class='clear'></div>";
        newRow+="<div class='btns'>";
        //newRow+="<input type='button' value='Ok' class='okBtn' /><input type='button' value='Cancel' class='btn desCancel' />";
        newRow+="<input type='button' value='Ok' class='okBtn' />";
        newRow+="</div></div></div></td>";
        newRow+="<td class='thuT' width='5%' valign='middle' align='left'><div class='poRel'>";
        newRow+="<input type='text' class='small' name='entries["+tempId+"].d5Hours'  maxlength='5' onkeyup='checkSendForApproval();' onfocus='checkSendForApproval();' onblur='validateHour(this);checkHour(this);(this);checkSendForApproval();' id='d5Hoursrow"+tempId+"' value='' />";
        newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
        newRow+="<div class='comments_div'>";
        newRow+="<textarea name='entries["+tempId+"].d5Comment' id='d5Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''></textarea>";
        newRow+="<div class='clear'></div>";
        newRow+="<div class='btns'><input type='button' value='Ok' class='okBtn' /></div></div>";
        //<input type='button' value='Cancel' class='btn desCancel' /></div></div>";
        newRow+="</div></td>";
        newRow+="<td width='5%' valign='middle' align='left' class='friT'><div class='poRel'>";
        newRow+="<input type='text' class='small' name='entries["+tempId+"].d6Hours'  maxlength='5' onkeyup='checkSendForApproval();' onfocus='checkSendForApproval();' onblur='validateHour(this);checkHour(this);(this);checkSendForApproval();' id='d6Hoursrow"+tempId+"' value='' />";
        newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
        newRow+="<div class='comments_div'>";
        newRow+="<textarea name='entries["+tempId+"].d6Comment' id='d6Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''></textarea>";
        newRow+="<div class='clear'></div>";
        newRow+="<div class='btns'>";
        newRow+="<input type='button' value='Ok' class='okBtn' />";
        newRow+="</div></div></div></td>";
        //newRow+="<input type='button' value='Cancel' class='btn desCancel' /></div></div></div></td>";
        newRow+="<td width='5%' valign='middle' align='left' class='satT'><div class='poRel'>";
        newRow+="<input type='text' class='small' value='' name='entries["+tempId+"].d7Hours'  maxlength='5'  onblur='validateHour(this);checkHour(this);' id='d7Hoursrow"+tempId+"' />";
        newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
        newRow+="<div class='comments_div'>";
        newRow+="<textarea name='entries["+tempId+"].d7Comment' id='d7Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''></textarea>";
        newRow+="<div class='clear'></div>";
        newRow+="<div class='btns'>";
        //newRow+="<input type='button' value='Ok' class='okBtn' /><input type='button' value='Cancel' class='btn desCancel' />";
        newRow+="<input type='button' value='Ok' class='okBtn' />";
        newRow+="</div></div></div></td>";
        newRow+="<input type='hidden' id='approveField"+tempId+"' value='N'/>";
        newRow+="<td valign='middle' align='center'><img width='16' height='16' class='remove' src='resources/images/remove.png'></td>";
        newRow+= "<td valign='middle' align='center'> <a href='' class='copy' >Copy</a></td>";
        newRow+="</tr>"; 
   //  checkAllocation();
    $("#tblActivity tbody").append(newRow);
    disableAll();
    $("#tblActivity tbody").find("tr:even").addClass("even");
    $(".tab_div").hide().next("#tab2".hash).show(); 
    $("#tblActivity tbody").find("tr:odd").addClass("odd");
 // commenting this whole section: requirement -need to fill timesheet for future dates
    //checkCurrentDate(eval('${today}'));
    arrayComparison();
    var tbodyLength = $("#tblActivity tbody tr").length;
    
    var id = $("#resourceAllocIdrow"+tempId).val();
    //var id = tempId;
	  getActiveActivityByProjectId(id, tempId);
	  checkSendForApproval();
	setTimeout(function(){  document.getElementById("activityDesc"+tempId).innerHTML =varValue; }, 100);
	  
    });
    
    var commentsDivBg = $("<div id = 'comments_div_bg' />");
    
    /*---------------commnets-----------------*/
    $(document).on("click", '.comments' , function(){
            
            $("body").append(commentsDivBg);
            $("#comments_div_bg").css("display","block");
            $('.comments_div').fadeOut(200);
             $(this).next('div').fadeIn(200);
             $(this).parent("div.poRel").find('div.comments_div').find("textarea").focus();
    });
    
    $('.calendarIcon').click(function(){
        $("body").append(commentsDivBg);
        $("#comments_div_bg").css("display","block");
    });
    
    /*--------------value of dates----------*/

	/*--------------value of dates end----------*/
	
	/*--------------comments close----------*/
    
    $(document).on("click" ,  '.okBtn' , function(){
        var tblTrLength = $("table#tblActivity tbody").find("tr").length; 
        if(tblTrLength>0){
            /*for changing comment icon*/
            var commentVal = $.trim($(this).parent().parent('div.comments_div').find("textarea").val());
            // To remove the space and save the trimmed value
                if(null == commentVal || commentVal==''){
                    $(this).closest("div.poRel").find(".comments").attr("src","resources/images/comments.png");    
                 }else {
                     $(this).closest("div.poRel").find(".comments").attr("src","resources/images/hascomments.png");
                }
        }
        
        
        var txtarea = $(this).parent().parent('div').find("textarea").val().length;
         if(txtarea>256){
            var errorMsg = "comment can't be more than 256 characters";
            showError(errorMsg);
            return;
         }
         $(this).parent().parent('div').fadeOut(200);
         $("#comments_div_bg").remove();
    });
    
    /*--------------Bg Div close----------*/
    $(document).on("click",'.weeksel',function(){
         $("#comments_div_bg").remove();
    });
    
    /* $(".tab_div").hide();
    
    $('ul.tabs a').click(function () {
	    $(".tab_div").hide().filter(this.hash).show();
	    $("ul.tabs a").removeClass('active');
	           
	    $(this).addClass('active');
	    //containerWidth();                                            
	    return false;
    }).filter(':first').click(); */
    
    $('#update').click(function(){
	    $("ul.tabs a").removeClass('active');
	    $(".tab_div").hide().next("#tab2".hash).show();
	    $('a[href$="tab2"]').addClass('active');
	    //containerWidth();
    });
    
    $('#MaintenanceTabInactive').off('click');	    
    
});

var weekStart;
var weekEnd;
var dateString;
var count=0;
var countForComment=0;
var tempId=0;
var message= false;
//check HyperLink Flag US3093 Akash Datir
var hyperLinkFlag=${i};
var tempHyper=hyperLinkFlag;                       

if(tempHyper==1){
	  <c:set var="i" scope="session" value="0"/> }
//check HyperLink Flag US3093 Akash Datir
/**Function to select the Start and End dates as current week */
function weekStartEndDate(){    
	
	var current;
	
	if(tempHyper==1){                                  //for HyperLink
		var dateStr="${maxWeekStartDate}";
		var res=dateStr.toString().split(" ");           //convert date into requir format mm/yy/dd
		var ddd=res[1]+" "+res[5]+" "+res[2];
	 	current = new Date(ddd);
		var first = current.getDate() - current.getDay();
	    var last = first+6;
	    var firstday = new Date(new Date(ddd).setDate(first));
	    var lastday = new Date(new Date(ddd).setDate(last));

	}
	else{                                               		
		current = new Date();
	    var first = current.getDate() - current.getDay();
	    var last = first+6;
	    var firstday = new Date(new Date().setDate(first));
	    var lastday = new Date(new Date().setDate(last));
	  }
 
    var startDate = firstday.getDate();
    var startMonth = firstday.getMonth()+1;
    var startYear = firstday.getFullYear() ;

    var endDate = lastday.getDate();
    var endMonth = lastday.getMonth()+1;
    var endYear = lastday.getFullYear();

    var weekSDate = startMonth+"/"+startDate+"/"+startYear;
    var weekEDate = endMonth+"/"+endDate+"/"+endYear;
	
    var row = createTableRow(weekSDate);
    $("#dates").html('');
    $("#dates").append(row);
    $("#weekStartDate").val(weekSDate);
    $("#weekEndDate").val(weekEDate);
    $('#tblActivity > tbody').find('tr').remove();        
    weekEnd = weekEDate;     
    getWeekDates(weekEDate);         
    var empId = document.getElementById("employeeIdHidden").value;
    $.getJSON("useractivitys",{find:"ByWeekStartDateBetweenAndEmployeeId_json" ,employeeId:empId,minWeekStartDate:weekSDate,maxWeekStartDate:weekEDate}, showUserActivities);
}

function isJoiningWeek(weekStartingDate, weekEndingDate, doj){
	if((new Date(doj) <= new Date(weekEndingDate)) && (new Date(doj) > new Date(weekStartingDate))){
		return true;
	} 
	else{
		return false;
	}
}

function isWeekStartsBeforeProjectAllocation(weekStartingDate, weekEndingDate, allocationStartDate){
	/* if((new Date(doj) <= new Date(weekEndingDate)) && (new Date(doj) > new Date(weekStartingDate))){
		return true;
	} 
	else{
		return false;
	} */
	if((new Date(weekStartingDate) < new Date(allocationStartDate)) && (new Date(weekEndingDate) > new Date(allocationStartDate))){
		return true;
	}else{
		return false; 
	}
}

function isProjectAllocationEndBeforeWeekEnd(weekStartingDate, weekEndingDate, allocationEndDate){
	/* if((new Date(doj) <= new Date(weekEndingDate)) && (new Date(doj) > new Date(weekStartingDate))){
		return true;
	} 
	else{
		return false;
	} */
	if((new Date(allocationEndDate) < new Date(weekEndingDate)) && (new Date(allocationEndDate) > new Date(weekStartingDate))){
		return true;
	}else{
		return false; 
	}
}

function disableAll(){

	/* <c:if test="${empty resourceAllocation}">
	projAllocId = 0;
    </c:if> */
    
	/* var startdate = $("#weekStartDate").val();
	var weekStart = Date.parse(startdate).getTime(); */
	var enddate= $("#weekEndDate").val();
 	var weekEnd= Date.parse(enddate).getTime();
 	/* var dOfj = Date.parse(doj).getTime(); */
 	
 	if(projectStartDate!=null && projectStartDate.length>0){
 		for(var i=0;i<projectStartDate.length;i++){
 			if(weekEnd < new Date(projectStartDate[i]).getTime())
 				allocStartFlag++;
 		}
 	}
 	/* if(projectEndDate!=null && projectEndDate.length>0){
 		for(var i=0;i<projectEndDate.length;i++){
 			if(weekStart > new Date(projectEndDate[i]).getTime())
 				allocEndFlag++;
 		}
 	} */
 	/* if(allocStartFlag == projectStartDate.length){
 		checkAllocation();     		
 	} */
}

function createNewRow()
{

	isButtonDisabled=false;
	$("#comment").hide();  
	$.fancybox.close();
	 var weekEndDateVal = $("#weekStartDate").val();
	 var WeeklyDateStart=document.getElementById("weekStartDateHidden").value;
	 var logValues = ['', '', '', '', '', '', '', ''];
	 var commentValues = ['', '', '', '', '', '', ''];
     if(weekEndDateVal == '') {
       //  //alert('Please select End Date');
       // Added for task # 216 - Start
 	var text="Please select End Date";
	showAlert(text);
// Added for task # 216 - End
     }
     
     var startTime = Date.parse(weekEndDateVal).getTime();
     var currentTime = new Date().getTime();
  // commenting this whole section: requirement -need to fill timesheet for future dates
    /*  if(startTime > currentTime) {
         ////alert("Can't add time sheet for future week.");
         var errorMsg = "Sorry! You can't add time sheet(s) for future week(s).";
         showError(errorMsg);
         return;
     } */
      if(approveForNewLink) {
         ////alert("Time sheet for specified week has already been approved, so can't update record");
         var warningMsg = "Time sheet for specified week has already been approved, so can't update record";
         showWarning(warningMsg);
         return ;
     } 
      addNewvar=true;
  
      //tempId=$("table#tblActivity tbody").find("tr").length;
      
      //raghuraj start from here 12/july/2017 
      if($('table#tblActivity > tbody > tr:not(".expandable_row"):last').attr('id') !== undefined)
    	  {
	    	  var lastRow = $('table#tblActivity > tbody > tr:not(".expandable_row"):last').attr('id');
	          tempId = parseInt(lastRow.replace ( /[^\d.]/g, '' ))+1;
    	  }else{
    		  tempId = 0;
    	  }
      
       
       //raghuraj end from here 12/july/2017  
       
      document.getElementById("userAction").value="update";
     var newRow="<tr title='' id='row"+tempId+"'>"; 
     newRow+="<td valign='middle'><div id='activityDesc" + tempId +"' align='left' style='background: white; width =860; height =350; color: #3377BB;font-weight: bold' ></div></td>";
     newRow+=" <td><select class='chzn-select dd_cmn comboselect' name='entries["+tempId+"].resourceAllocId' onchange='getActiveActivityByProjectId(value,id);getActiveModulesByProjectIdOnProjectChange(value,"+tempId+");getTicketPriorityAndStatusByProjectIdOnProjectChange(value,"+tempId +");' id='resourceAllocIdrow"+tempId+"'>";
     <c:forEach var='projects' items='${resourceAllocation}'> 
     var dte="<fmt:formatDate value='${projects.allocEndDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"
     var allocEndDate = Date.parse(dte);
     var dte="<fmt:formatDate value='${projects.allocEndDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"
     var strdte="<fmt:formatDate value='${projects.allocStartDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"

     var allocEndDate = Date.parse(dte);        
     var allocStartDate = Date.parse(strdte); 
     if(validateDateFunctionForNew('${projects.allocStartDate}', '${projects.allocEndDate}')) {
          projectEndDate.push(allocEndDate);
          projectStartDate.push(allocStartDate);
            newProjectId.push(eval('${projects.id}'));     
          newRow+="<option  value='${projects.id}' >${projects.projectId.projectName}</option>";
     }
     </c:forEach>
     newRow+="</select></td>";
  	 newRow+="<td valign='middle'>";
     newRow+="<select name='entries["+tempId+"].activityId' class='dd_cmn comboselect' id='activityId"+tempId+"'><c:forEach var='activity' items='${submodules}'><option value='${submodule.subModuleName}'>${submodule.subModuleName}</option></c:forEach></select></td>";
	 newRow+="<td valign='middle' align='center'>"
     var moduleExist=false; 
     var activeModuleFlag=false;
     <c:forEach var='projects' items='${resourceAllocation}'> 
     var dte="<fmt:formatDate value='${projects.allocEndDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"
     var allocEndDate = Date.parse(dte);
     var dte="<fmt:formatDate value='${projects.allocEndDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"
     var strdte="<fmt:formatDate value='${projects.allocStartDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"
	 var allocEndDate = Date.parse(dte);        
     var allocStartDate = Date.parse(strdte); 
     if(validateDateFunctionForNew('${projects.allocStartDate}', '${projects.allocEndDate}')) {
          var tempHtml='';
          moduleExist=false;
          <c:choose>
          <c:when test='${not empty projects.projectId.module}'> 
          moduleExist=true;
          tempHtml+="<select name='entries["+tempId+"].module' class='dd_cmn comboselect' id='module"+tempId+"' onChange=getActiveSubModulesByModuleId(this.value,"+tempId+");>";
          tempHtml+="<option  value='-1' >Please Select</option>";
          <c:forEach var='module' items='${projects.projectId.module}'> 
          <c:if test="${module.active == true}">
          tempHtml+="<option  value='${module.moduleName}' >${module.moduleName}</option>";
          activeModuleFlag=true;
          </c:if>
          </c:forEach>
          tempHtml+="</select></td>";
          </c:when> 
          <c:otherwise>
          tempHtml+="<input type='text'  name='entries["+tempId+"].module'  id='module"+tempId+"'></td>";
          </c:otherwise>
           </c:choose>
           if(moduleExist==true && activeModuleFlag == false)
           {  
        	   tempHtml="";
           	   tempHtml+="<input type='text'  name='entries["+tempId+"].module'  id='module"+tempId+"'></td>";
           }
           newRow+=tempHtml;  
     }
     </c:forEach>
     newRow+="<td valign='middle'>";
     var isModuleExist=$("#module"+tempId+" option:eq(1)").val();
     if(typeof isModuleExist != "undefined"){
     //if(moduleExist==true && activeModuleFlag == true){
    	 newRow+="<select name='entries["+tempId+"].subModule' class='dd_cmn comboselect' id='subModule"+tempId+"'><option  value='-1' >Please Select</option></select></td>";}
		 else{ 
	     newRow+="<input type='text'  name='entries["+tempId+"].subModule'  id='subModule"+tempId+"'></td>";
	   }
     
     //newRow+="<td valign='middle' align='center'><input type='text'  name='entries["+tempId+"].module'  id='module"+tempId+"'></td>";
//   adding ticket priority and and ticket status in current row @pankaj birla
	  newRow+="<td valign='middle' align='center'><input type='text'  name='entries["+tempId+"].ticketNo'  id='ticketNo"+tempId+"' onChange='getTicketPriorityAndStatus("+tempId +")';></td>";	
	  newRow+="<td valign='middle' align='center'>";
	  newRow+="<div id='divticketPriorities_"+tempId+"'>";  
	  newRow+="<select disabled name='entries["+tempId+"].ticketPriority' class='dd_cmn comboselect' id='ticketPriorities"+tempId+"'>";
	  newRow+="<option  value='' >Please Select</option>"; 
	  newRow+="</select>";
	  newRow+="</div>";
	  newRow+="</td>";
	  newRow+="<td valign='middle' align='center'>";
	  newRow+="<div id='divticketStatus_"+tempId+"'>"; 
	  newRow+="<select disabled name='entries["+tempId+"].ticketStatus' class='dd_cmn comboselect' id='ticketStatus"+tempId+"'>";
	  newRow+="<option  value='' >Please Select</option>";
	  newRow+="</select>";
	  newRow+="</div>";
	  newRow+="</td>";   
	  
    /*  newRow+="<td width='5%' valign='middle' align='left' class='sunT'>";
     newRow+="<div class='poRel'>";
     newRow+="<input type='text'  name='entries["+tempId+"].d1Hours'   onblur='validateHour(this);checkHour(this);' maxlength='5' id='d1Hoursrow"+tempId+"' class='small' value=''/><img src='resources/images/comments.png' width='24' height='23' class='comments' />";
     newRow+="<div class='comments_div'>";
     newRow+="<textarea name='entries["+tempId+"].d1Comment' id='d1Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''></textarea>";
     newRow+="<div class='clear'></div>";
     newRow+="<div class='btns'>";
     newRow+="<input type='button' value='Ok' class='okBtn' />";
     //newRow+="<input type='reset' value='Cancel' class='btn desCancel' />";
     newRow+="</div>";
     newRow+="</div>";
     newRow+="</div></td>";
     newRow+="<td width='5%' class='monT' valign='middle' align='left'><div class='poRel'>";
     newRow+="<input type='text' class='small' maxlength='5' onkeyup='checkSendForApproval();'  onfocus='checkSendForApproval();' onblur='validateHour(this);checkHour(this);checkSendForApproval();' name='entries["+tempId+"].d2Hours'  id='d2Hoursrow"+tempId+"' value='' />";
     newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
     newRow+="<div class='comments_div'>";
     newRow+="<textarea name='entries["+tempId+"].d2Comment' id='d2Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''></textarea>";
     newRow+="<div class='clear'></div>";
     newRow+="<div class='btns'>";
     newRow+="<input type='button' value='Ok' class='okBtn' />";
     //newRow+="<input type='button' value='Cancel' class='btn desCancel' />";
     newRow+="</div></div></div></td>";
     newRow+="<td width='5%' class='tueT' valign='middle' align='left'><div class='poRel'>";
     newRow+="<input type='text' class='small' name='entries["+tempId+"].d3Hours'   maxlength='5' onkeyup='checkSendForApproval();' onfocus='checkSendForApproval();'   onblur='validateHour(this);checkHour(this);checkSendForApproval(); ' id='d3Hoursrow"+tempId+"' value='' />";
     newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
     newRow+="<div class='comments_div'>";
     newRow+="<textarea name='entries["+tempId+"].d3Comment' id='d3Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''></textarea>";
     newRow+="<div class='clear'></div>";
     newRow+="<div class='btns'>";
     newRow+="<input type='button' value='Ok' class='okBtn' />";
     //newRow+="<input type='button' value='Cancel'  class='btn desCancel' />";
     newRow+="</div></div></div></td>";
     newRow+="<td width='5%' class='wedT' valign='middle' align='left'><div class='poRel'>";
     newRow+="<input type='text' class='small' name='entries["+tempId+"].d4Hours'  maxlength='5' onkeyup='checkSendForApproval();'   onfocus='checkSendForApproval();' onblur='validateHour(this);checkHour(this);checkSendForApproval();' id='d4Hoursrow"+tempId+"' value='' />";
     newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
     newRow+="<div class='comments_div'>";
     newRow+="<textarea name='entries["+tempId+"].d4Comment' id='d4Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''></textarea>";
     newRow+="<div class='clear'></div>";
     newRow+="<div class='btns'>";
     //newRow+="<input type='button' value='Ok' class='okBtn' /><input type='button' value='Cancel' class='btn desCancel' />";
     newRow+="<input type='button' value='Ok' class='okBtn' />";
     newRow+="</div></div></div></td>";
     newRow+="<td class='thuT' width='5%' valign='middle' align='left'><div class='poRel'>";
     newRow+="<input type='text' class='small' name='entries["+tempId+"].d5Hours'  maxlength='5' onkeyup='checkSendForApproval();'  onfocus='checkSendForApproval();' onblur='validateHour(this);checkHour(this);checkSendForApproval();' id='d5Hoursrow"+tempId+"' value='' />";
     newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
     newRow+="<div class='comments_div'>";
     newRow+="<textarea name='entries["+tempId+"].d5Comment' id='d5Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''></textarea>";
     newRow+="<div class='clear'></div>";
     newRow+="<div class='btns'><input type='button' value='Ok' class='okBtn' /></div></div>";
     //<input type='button' value='Cancel' class='btn desCancel' /></div></div>";
     newRow+="</div></td>";
     newRow+="<td width='5%' valign='middle' align='left' class='friT'><div class='poRel'>";
     newRow+="<input type='text' class='small' name='entries["+tempId+"].d6Hours'  maxlength='5' onkeyup='checkSendForApproval();'  onfocus='checkSendForApproval();' onblur='validateHour(this);checkHour(this);checkSendForApproval();' id='d6Hoursrow"+tempId+"' value='' />";
     newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
     newRow+="<div class='comments_div'>";
     newRow+="<textarea name='entries["+tempId+"].d6Comment' id='d6Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''></textarea>";
     newRow+="<div class='clear'></div>";
     newRow+="<div class='btns'>";
     newRow+="<input type='button' value='Ok' class='okBtn' />";
     newRow+="</div></div></div></td>";
     //newRow+="<input type='button' value='Cancel' class='btn desCancel' /></div></div></div></td>";
     newRow+="<td width='5%' valign='middle' align='left' class='satT'><div class='poRel'>";
     newRow+="<input type='text' class='small' value='' name='entries["+tempId+"].d7Hours'  maxlength='5' onkeyup='checkSendForApproval();'   onblur='validateHour(this);checkHour(this);' id='d7Hoursrow"+tempId+"' />";
     newRow+="<img src='resources/images/comments.png' width='24' height='23' class='comments' />";
     newRow+="<div class='comments_div'>";
     newRow+="<textarea name='entries["+tempId+"].d7Comment' id='d7Commentrow"+tempId+"' cols='' class='comments_textarea' rows=''></textarea>";
     newRow+="<div class='clear'></div>";
     newRow+="<div class='btns'>";
     //newRow+="<input type='button' value='Ok' class='okBtn' /><input type='button' value='Cancel' class='btn desCancel' />";
     newRow+="<input type='button' value='Ok' class='okBtn' />";
     newRow+="</div></div></div></td>";
     newRow+="<input type='hidden' id='approveField"+tempId+"' value='N'/>";
     newRow+="<td valign='middle' align='center'><img width='16' height='16' class='remove' src='resources/images/remove.png'></td>";
     newRow+= "<td valign='middle' align='center'> <a href='javascript:void(0);' class='copy' >Copy</a></td>";
	 newRow+="</tr>";  */
     newRow+="<input type='hidden' id='approveField"+tempId+"' value='N'/>";	 
	 newRow+="<td valign='middle' align='center' class='action'><i class='fa fa-chevron-circle-down' onclick='toggleChildRow(this)' title='expand'></i> <i class='fa fa-copy copy' title='copy record'></i><i class='fa fa-trash remove' title='delete record'></i></td>";

	newRow+="</td>"; 
	 newRow+="</tr>";
     newRow+="<tr id='row_timesheet_"+(tempId)+"' class='expandable_row'><td colspan="+9+">";
     newRow+='<table cellpadding="0" cellspacing="0" border="0" style="width: 100%;">'+
     '<tr><td>Day</td><td align="center">Sunday</td><td align="center">Monday</td><td align="center">Tuesday</td><td align="center">Wednesday</td><td align="center">Thursday</td><td align="center">Friday</td><td align="center">Saturday</td><td  align="center">Log Time</td></tr>'+
	 '<tr><td>Date</td>'+createTableRowNew(WeeklyDateStart)+'</tr>' +
	 '<tr class="logHrs"><td>Log Time (in hours)</td>'+createTimeLog(tempId, logValues, '')+'</tr>' +	 
     '<tr><td>Comments</td>'+createLogComments(tempId, commentValues, '')+'</tr>' +	 	 
	 '</table>';
     newRow+="</td></tr>"; 

     $("#tblActivity > tbody").append(newRow);
 var tbodyLength = $("#tblActivity> tbody > tr:not('.expandable_row')").length -1;
 var id = $("#resourceAllocIdrow"+tbodyLength).val();
	 getActiveActivityByProjectId(id, tbodyLength);
 $("#tblActivity>tbody").find("tr:even").addClass("even");
 $(".tab_div").hide().next("#tab2".hash).show(); 
 $("#tblActivity>tbody").find("tr:odd").addClass("odd");
// commenting this whole section: requirement -need to fill timesheet for future dates
 //checkCurrentDate(eval('${today}'));
 $("#comment").hide();
 $.fancybox.close();
 arrayComparison();
 //Added by Nitin
 checkSendForApproval();
}
function isExist(submodule)
{	
	if(submodule!="" ||submodule!= [])
	return true;
	else return false;
}

function arrayComparison(){
    
    //var projectId = ;
    var rowCount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
     
    var resVal=document.getElementById('resourceAllocIdrow'+tempId).value;
    //var resId=resourceAllocId0
    for(var count=0;count<rowCount;count++){
        for(var i=0; i<newProjectId.length; i++){
             if(newProjectId[i] == resVal){
                var index = newProjectId.indexOf(newProjectId[i]);
              }    
            }
         }
     
    var data=projectEndDate[index];
    var startDateData=projectStartDate[index];
    var len =rowCount-1;
     var currentrow = "row"+len;
      
    checkWithAllocEndDate(data,currentrow,startDateData);
}

function checkWithAllocEndDate(allocEndDate,count,allocStartDate){

var dateArray = dateString.split(',');
var rowCount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;

//var nRow = $(this).parents('tr')[0];

 for(var g=0;g<7;g++)
	 {
	  if(new Date(allocStartDate).getTime() > Date.parse(dateArray[g]).getTime()   ){
          
		// commenting this whole section: requirement -need to fill timesheet for future dates
        /*   
          document.getElementById('d'+(g+1)+"Hours"+count).setAttribute("readonly","true"); 
         document.getElementById('d'+(g+1)+"Comment"+count).setAttribute("readonly","true");
        */
         
      }
 
	 }
  
for(var j=0;j<7;j++){
     
    /* //alert('jn checkWjthAllocEndDate ');
    //alert("btw"+allocEndDate.getTime());
    //alert("diff"+Date.parse(dateArray[j]).getTime())
    //alert("gfhf"+ eval("${today}")); */
    if(allocEndDate == null || allocEndDate == ''){
         if(Date.parse(dateArray[j]).getTime() <= eval("${today}")){
                   
              document.getElementById('d'+(j+1)+"Hours"+count).readOnly=false;
              document.getElementById('d'+(j+1)+"Comment"+count).readOnly=false; 
           }else{
        	// commenting this whole section: requirement -need to fill timesheet for future dates
              /* document.getElementById('d'+(j+1)+"Hours"+count).setAttribute("readonly","true");
              document.getElementById('d'+(j+1)+"Comment"+count).setAttribute("readonly","true");
               */ 
           }
    }else{
        
       if(allocEndDate.getTime() < Date.parse(dateArray[j]).getTime() && Date.parse(dateArray[j]).getTime() <= eval("${today}") ||allocStartDate.getTime() > Date.parse(dateArray[j]).getTime()){
           
    	// commenting this whole section: requirement -need to fill timesheet for future dates
          /*  document.getElementById('d'+(j+1)+"Hours"+count).setAttribute("readonly","true"); 
          document.getElementById('d'+(j+1)+"Comment"+count).setAttribute("readonly","true"); */
       }else{
          
           if(Date.parse(dateArray[j]).getTime() <= eval("${today}")){
              
        	// commenting this whole section: requirement -need to fill timesheet for future dates
               document.getElementById('d'+(j+1)+"Hours"+count).readOnly= false;
              document.getElementById('d'+(j+1)+"Comment"+count).readOnly= false; 
           }else{
             
        	// commenting this whole section: requirement -need to fill timesheet for future dates
             /*  document.getElementById('d'+(j+1)+"Hours"+count).setAttribute("readonly","true");
              document.getElementById('d'+(j+1)+"Comment"+count).setAttribute("readonly","true"); */
           }
          
       }
    }
   
}
}

function getUserActivitiesForMonth(monthStart,monthEnd,empId){
	////alert("On date selection ");
    $.getJSON("useractivitys",{find:"ByWeekStartDateBetweenAndEmployeeIdUpdated" ,employeeId:empId,minWeekStartDate:monthStart,maxWeekStartDate:monthEnd}, showUserActivitiesForMonth);
}
//added by purva for 3088 start
function showUserActivitiesForMonth(data){
	 

    if(userActivityTable != null)userActivityTable.fnClearTable();
	var htmlVar='';

	for (var i = 0; i < data.length; i++) {
		
		 
			var obj = data[i];
		
				//added by purva for 3088 start																														
			htmlVar += "<tr id="+i+"  >";  <%-- //,'${obj.resourceAllocId}',<fmt:formatDate value="'+${obj.weekStartDate}+'" type='"date"' "pattern="<%=Constants.DATE_PATTERN %>"/>);">'; --%>
									
			htmlVar += '<td align="center" id="oldRow"  onclick=openMaintainance("'+obj.employeeId+'","'+obj.resourceAllocationId+'","'+obj.weekStartDate+'",this);><a href="#">'+obj.projectName+'</td>';
			htmlVar += '<td align="center">'+obj.weekEndDate+'</td>';
			
			htmlVar += '<td align="center">'+obj.totalHours+'</td>';
			if(obj.status ==  'N'){
				htmlVar += '<td align="center" valign="middle"><i class="fa fa-check-circle saved" title="Not submitted"></i><a href="#" onclick=addrow('+obj.employeeId+','+obj.resourceAllocationId+',"'+obj.weekStartDate+'");> <b>Copy this Timesheet </b></a></td>';
			}
			if(obj.status ==  'P'){
				htmlVar += '<td align="center" valign="middle"><i class="fa fa-exclamation-circle review" title="Review Pending"></i><a href="#" onclick=addrow('+obj.employeeId+','+obj.resourceAllocationId+',"'+obj.weekStartDate+'");> <b> Copy this Timesheet </b></a></td>';
			}
			if(obj.status ==  'R'){
				htmlVar += '<td align="center" valign="middle"><i class="fa fa-times-circle rejected" title="Rejected"></i> &nbsp <b>Rejected Timesheet</b></td>';
			}
			if(obj.status ==  'A'){
				htmlVar += '<td align="center" valign="middle"><i class="fa fa-check-circle approved" title="Approved"></i><a href="#" onclick=addrow('+obj.employeeId+','+obj.resourceAllocationId+',"'+obj.weekStartDate+'");><b> Copy this Timesheet </b> </a></td>';
			}
			htmlVar += "</tr>";
		}//added by purva for 3088 end
	
    $("#userActivityTableId > tbody:last").append(htmlVar);
    addDataTableSearch($('#userActivityTableId')); 
    stopProgress();
}

function addDataTableSearch(table){
    userActivityTable = $(table).dataTable({
        "bStateSave": true,
        "sDom": 'lfrtip',
        "sPaginationType": "full_numbers",
        "bDestroy": true,
        "aoColumns": [
                      null,
                      { "asSorting": [ "asc","desc" ] },
                      null, 
                      null
					],			
		"oLanguage": {
        	"sEmptyTable": "No Data Available"
    	}
    });
}

/*-------------------function for table scroll------------------------*/
function getActiveActivityByProjectId(projId, id){   
	var temId = id;	
	if(isNaN(id)){
		temId = id.substring(18);
		}
	  $.getJSON("useractivitys",{find:"ActiveActivityByProjectId" ,projectId:projId}, function(data){ 
              
            	   if(data.length==0){
                 	  
            		 $('#activityId'+temId).empty();
                 	  
                 	 $("#row"+temId).find("td").find('input').attr("readonly","readonly");
                 	 
                     $("#row"+temId).find("td").find(".poRel").find(".comments_div").find(".comments_textarea").attr("readonly","readonly");
                 	 
                    
                     $("#row"+temId).find("td").find('input[type=text]').val('');
                    
                     $("#row"+temId).find("td").find(".poRel").find(".comments_div").find(".comments_textarea").val('');
                     
                     
                     /* if(temId!=0){
                    	 $("#row"+temId).remove();
                     } */
                 	  
                 	 var thisObj = $(this); 
                 	  
                 	 thisObj.parent().parent().remove();
                     $("table#tblActivity tbody").find("tr:first").find("input[type=text]").blur();
                 	  
                 	 var rowCount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
                     if(rowCount==0){
                         $('#fa-ctotal_hrs > tfoot tr th').find('input').val('');
                       
                     }
                 	  
                 	   showError("Activity not set for this project, Please connect to your PM.");
      		    	   stopProgress();
                    }else{   
            	   
            	   
            	    $('#activityId'+temId).empty();
            	    $("#row"+temId).find("td").find('input').attr("readonly",false);
                	 
                    $("#row"+temId).find("td").find(".poRel").find(".comments_div").find(".comments_textarea").attr("readonly",false);
               //	<c:forEach var='activity' items='${activities}'><option value='${activity.id}'>${activity.activityName}</option></c:forEach>
               var value;
               if(data[0].productive=="true" || data[0].productive==true){
            	   value="Productive";
               }else{
            	   value="Non Productive";
               }
               if(document.getElementById("activityDesc0")!=null){
                 if(temId==0){
                   document.getElementById("activityDesc0").innerHTML =value;
                 }  
               } 
               if(document.getElementById("activityDesc"+temId)!=null && temId!=0){
            	   document.getElementById("activityDesc"+temId).innerHTML =value;
               }
               
               var htmlVar='';
               for(var i=0;i<data.length;i++){
               		     
            	   		 $('#activityId'+temId).append('<option value="'+data[i].id+"_"+data[i].activityType+"_"+data[i].productive+"_"+data[i].mandatory+'">'+data[i].activityName+'</option>');
               		     htmlVar += '<input type="hidden" name="activityStatusName" id="'+data[i].id+"_"+data[i].activityType+"_"+data[i].productive+"_"+data[i].mandatory+'" value="'+data[i].productive+'">';
               		 }
               	 
               	 $('#activityStatusDiv').append(htmlVar);
				/*  */
               }
              });
                                        
}


function getActiveModulesByProjectIdOnProjectChange(projId,id,status,flag){
	$.getJSON("useractivitys",{find:"ActiveModulesByProjectId" ,projectId:projId}, function(data){
	   var html=' ';
		var modelValue=$('#module'+id).val();
		if(!isEmpty(data)){
		$('#module'+id).empty();
		html+= "<select name='entries["+id+"].module' class='dd_cmn comboselect' id='module"+id+"' onchange='getActiveSubModulesByModuleId(this.value,"+id+");'>"
		html+='<option value="-1">Please Select</option>';
		for(var i=0;i<data.length;i++){
			if(data[i].moduleName==modelValue)
			html+='<option value="'+data[i].moduleName+'" selected=selected>'+data[i].moduleName+'</option>';
	        else
	        html+='<option value="'+data[i].moduleName+'">'+data[i].moduleName+'</option>';
		} 
		html+="</select>";
		}
		else
		{
			var moduleValue;
			if(flag==1)
			{
				moduleValue = $('#module'+id).val();
			}
			
			else
			{
			moduleValue = "";
			}
			
			if(moduleValue==-1)
			{//alert("if 1");
				html+='<input type=text name=entries['+id+'].module id="module'+id+'" value="" onfocus=getActiveModulesByProjectId("'+projId+'",value,value,1);>';
			}
			else
			{//alert("else 1");
				html+='<input type=text name=entries['+id+'].module id="module'+id+'" value="'+moduleValue+'" onfocus=getActiveModulesByProjectId("'+projId+'",value,value,1);>';
			}
			
			if(status=='P'||status=='A')
			  {
				  tempHtml.splice(17, 0, "readonly ");    
			  }
		}
		
		$('#module'+id).replaceWith(html);
			
		
		html="";
		
		currentModuleName=$("#module"+id+" option:eq(1)").val();
		if(typeof currentModuleName === "undefined"){
			html+='<input type=text name=entries['+id+'].subModule id="subModule'+id+'" value="">';	
		}
		
		else if(currentModuleName!=-1){
			html+= "<select name='entries["+id+"].subModule' class='dd_cmn comboselect' id='subModule"+id+"'>"
			html+='<option value="-1">Please Select</option>';
	     }
		
		$('#subModule'+id).replaceWith(html);
    });
}

function getActiveModulesByProjectId(projId,id,status,flag){
	$.getJSON("useractivitys",{find:"ActiveModulesByProjectId" ,projectId:projId}, function(data){
		   var html=' ';
			var modelValue=$('#module'+id).val();
			if(!isEmpty(data)){
				//currentModuleName=data[0].moduleName;
			$('#module'+id).empty();//alert("in getActiveModulesByProjectId");
			html+= "<select name='entries["+id+"].module' class='dd_cmn comboselect' id='module"+id+"' onchange='getActiveSubModulesByModuleId(this.value,"+id+",value,1);'>"
			html+='<option value="-1">Please Select</option>';
			for(var i=0;i<data.length;i++){
					
				if(data[i].moduleName==modelValue)
				html+='<option value="'+data[i].moduleName+'" selected=selected>'+data[i].moduleName+'</option>';
		        else
		        html+='<option value="'+data[i].moduleName+'">'+data[i].moduleName+'</option>';
			} 
			html+="</select>";
			
			}
			else
			{
				var moduleValue;
				if(flag==1)
				{
					moduleValue = $('#module'+id).val();
				}
				
				else
				{
				moduleValue = "";
				}
				
				if(moduleValue==-1)
				{//alert("if 2");
					html+='<input type=text name=entries['+id+'].module id="module'+id+'" value="" onfocus=getActiveModulesByProjectId("'+projId+'",value,value,1);>';
				}
				else
				{//alert("else 2");
					html+='<input type=text name=entries['+id+'].module id="module'+id+'" value="'+moduleValue+'" onfocus=getActiveModulesByProjectId("'+projId+'",value,value,1);>';
				}
				
				if(status=='P'||status=='A')
				  {
					  tempHtml.splice(17, 0, "readonly ");    
				  }
			}
			
			$('#module'+id).replaceWith(html);
				
			
			
			
			
			//$('#subModule'+id).replaceWith(html);
			
			/* currentModuleName=$("#module"+tempId).val();
				//alert('modulennn'+currentModuleName);
				if(currentModuleName!=-1){
			     getActiveSubModulesByModuleId(currentModuleName,id);
			     }
				else{
				if($("#module"+tempId+" option:eq(1)").val()!=undefined){	
				html+= "<select name='entries["+id+"].subModule' class='dd_cmn comboselect' id='subModule"+id+"'>"
				html+='<option value="-1">Please Select</option>';
				}
					else {
				    	//alert('in else part');
				    	 getActiveSubModulesByModuleId(null,id);	 
				    	 html+='<input type=text name=entries['+id+'].subModule id="subModule'+id+'" value="">';
				       }
				}
			     
			    //alert(html);
			$('#subModule'+id).replaceWith(html); */
			
	    });
	}

function getActiveModulesByProjectIdBySubmodule(projId,id,status,flag){
	$.getJSON("useractivitys",{find:"ActiveModulesByProjectId" ,projectId:projId}, function(data){
		   var html=' ';
			var modelValue=$('#module'+id).val();
			if(!isEmpty(data)){
				//currentModuleName=data[0].moduleName;
			$('#module'+id).empty();
			html+= "<select name='entries["+id+"].module' class='dd_cmn comboselect' id='module"+id+"' onchange='getActiveSubModulesByModuleId(this.value,"+id+");'>"
			html+='<option value="-1">Please Select</option>';
			for(var i=0;i<data.length;i++){
					
				if(data[i].moduleName==modelValue)
				html+='<option value="'+data[i].moduleName+'" selected=selected>'+data[i].moduleName+'</option>';
		        else
		        html+='<option value="'+data[i].moduleName+'">'+data[i].moduleName+'</option>';
			} 
			html+="</select>";
			
			}
			else
			{
				var moduleValue;
				if(flag==1)
				{
					moduleValue = $('#module'+id).val();
				}
				
				else
				{
				moduleValue = "";
				}
				
				if(moduleValue==-1)
				{//alert("if 3");
					html+='<input type=text name=entries['+id+'].module id="module'+id+'" value="" onfocus=getActiveModulesByProjectId("'+projId+'",value,value,1);>';
				}
				else
				{//alert("else 3");
					html+='<input type=text name=entries['+id+'].module id="module'+id+'" value="'+moduleValue+'" onfocus=getActiveModulesByProjectId("'+projId+'",value,value,1);>';
				}
				
				if(status=='P'||status=='A')
				  {
					  tempHtml.splice(17, 0, "readonly ");    
				  }
			}
			
			$('#module'+id).replaceWith(html);
			/* html="";
			if($("#module"+tempId+" option:eq(1)").val()!=undefined){
				 //currentModuleName=$("#module"+tempId+" option:eq(1)").val();
				 currentModuleName=$("#module"+tempId).val();
				////alert(currentModuleName);
			    getActiveSubModulesByModuleId(currentModuleName,id);
				html+= "<select name='entries["+id+"].subModule' class='dd_cmn comboselect' id='subModule"+id+"'>"
				html+='<option value="-1">Please Select</option>';
			    
			     }else{
			    	 getActiveSubModulesByModuleId(null,id);	 
			    	 html+='<input type=text name=entries['+id+'].subModule id="subModule'+id+'" value="">';
			       }
			$('#subModule'+id).replaceWith(html); */
			
	    });
	}

/////////////////Added for Sub Module
function getActiveSubModulesByModuleId(moduleName,id,status,flag){
	var html="";
	if(moduleName==null || moduleName==undefined){
    	html+='<input type="text" name=entries['+id+'].subModule id="subModule'+id+'" value="">';
   		$('#subModule'+id).replaceWith(html);
	return;
	}
	var resAllocId=document.getElementById("resourceAllocIdrow"+id).value
	var subModuleValue=$('#subModule'+id).val();
	$.getJSON("useractivitys",{find:"activeSubModulesByModuleId" ,moduleName:moduleName,resAllocId:resAllocId}, function(data){
			var html=' ';
			var subModuleValue=$('#subModule'+id).val();
			
			if(!isEmpty(data)){
				$('#subModule'+id).empty();
				html+= "<select name='entries["+id+"].subModule' class='dd_cmn comboselect' id='subModule"+id+"'>"
				html+='<option value="-1">Please Select</option>';
				for(var i=0;i<data.length;i++){
					 if(data[i].subModuleName==subModuleValue)
						html+='<option value="'+data[i].subModuleName+'" selected=selected>'+data[i].subModuleName+'</option>';
			        else 
			        	html+='<option value="'+data[i].subModuleName+'">'+data[i].subModuleName+'</option>';
				} 

				html+="</select>";
			}
			else{

			
					var myNewstr = subModuleValue;
				  //  subModuleValue = "";
				    
				    if(subModuleValue ==  -1 || subModuleValue == '' || flag == 1){
						html+='<input type=text name=entries['+id+'].subModule id="subModule'+id+'" value="">';
					}else{
						html+='<input type=text name=entries['+id+'].subModule id="subModule'+id+'" value="'+myNewstr+'">';
					}
			
			 /* if(subModuleValue != '' )
				{
					 if(subModuleValue ==  -1 ){
							html+='<input type=text name=entries['+id+'].subModule id="subModule'+id+'" value="">';
						}else{
							var mystr3 = "'"+moduleName.toString().trim()+"'";
							 html+='<input type=text name=entries['+id+'].subModule id="subModule'+id+'" value="'+subModuleValue+'" onfocus="getActiveSubModulesByModuleId('+mystr3+','+id+');" />';
						}
					 

				}else{ 
					if(subModuleValue ==  -1 || subModuleValue == '' ){
						html+='<input type=text name=entries['+id+'].subModule id="subModule'+id+'" value="">';
					}else{
						html+='<input type=text name=entries['+id+'].subModule id="subModule'+id+'" value="'+myNewstr+'">';
					}
					
				} */
				
			}
			
			
			/* if($('#module'+id).val()==-1){
				html="";
				html+= "<select name='entries["+id+"].subModule' class='dd_cmn comboselect' id='subModule"+id+"'>"
				html+='<option value="-1">Please Select</option>';
		     }
			
 */			
			$('#subModule'+id).replaceWith(html);
	    });
	}
	/////////////////////////////
function getAllActivityByProjectId(projId, id, activityId){
	$.getJSON("useractivitys",{find:"AllActivityByProjectId" ,projectId:projId, activityId:activityId}, function(data){
    	$('#activityId'+id).empty();

    	for(var i=0;i<data.length;i++){
   	 		var actId = data[i].id+"_"+data[i].activityType;
    		 	if(actId==activityId){
    		 		$('#activityId'+id).append('<option value="'+data[i].id+"_"+data[i].activityType+"_"+data[i].productive+"_"+data[i].mandatory+'" selected="selected">'+data[i].activityName+'</option>');
    		 	}else{
    		    	$('#activityId'+id).append('<option value="'+data[i].id+"_"+data[i].activityType+"_"+data[i].productive+"_"+data[i].mandatory+'">'+data[i].activityName+'</option>');
    		    	
    		 		
    		 	}
    		 	
    		 }
    
    });
                             
}

function openMaintainance(employeeId,resourceAllocId,date,td){
	
	
	 $("#div").html("<input type='hidden' name='relocc' id='reloccHidden' value="+resourceAllocId+">");

		
	 	var tdId1=$(td).attr('id');
	  
	    if (tdId1=="oldRow") {
	    	$('.fancybox-overlay').remove();
	    	getWeekDates(date);	
	    	 document.getElementById("employeeIdHidden").value = employeeId;
	 	    $('#tHours, #addNewDiv').fadeIn(200);
	 	    var row = createTableRow(weekStart);
	 	    $('#dates').html('');
	 	    $("#dates").append(row);
	 	    firstTime = false;
	 	    getUserActivitiesByWeekStartDateAndEmployeeId(employeeId,weekStart,weekEnd);
	 	   checkSendForApproval();
	 	  //common code  for both actual and copied rows  ends added for #3088
			  $("#comment").hide();
			    $.fancybox.close();
			    $('#tblActivity > tbody').find('tr').remove();
			    displayMaintainance();
	    	}
	    
	    else{ // for copied rows  added by purva for #3088
	    	
	    	 var weekDate=new Date($("#inputdate").val());
	    	   
	    	 $.ajax({
					type: 'POST',
				    url: 'resourceallocations/checkIfAllocationIsOpen/'+resourceAllocId +"/"+weekDate,
				    contentType: "application/json; charset=utf-8",
				    async:false,
				    success: function(data) {
				    	var jsonData1 = JSON.parse(data);	
				    	if(jsonData1.status == "true")
			    		{ $('#dialog').dialog('close'); $('.fancybox-overlay').remove();
				    		  //code to check if the allocation is still open
					    	 // get week dates of current week
					        getWeekDates($("#inputdate").val());
					        document.getElementById("employeeIdHidden").value = employeeId;
						    $('#tHours, #addNewDiv').fadeIn(200);
						    var row = createTableRowNew(weekStart); // create table with current week's dates added for #3088
						    $('#dates').html('');
						    $("#dates").append(row);
						    firstTime = false;
						    getWeekDates(date);	// gate week date of the actual week
						 //   //alert(weekStart);
						 getUserActivitiesByWeekStartDateAndEmployeeId1(employeeId,weekStart,weekEnd); // find values of actual row nd populate in table added for #3088
						    getWeekDates($("#inputdate").val()); /// get current weeks's calendar dates 
						   checkSendForApproval();	
						   //common code  for both actual and copied rows  ends added for #3088
							  $("#comment").hide();
							    $.fancybox.close();
							    $('#tblActivity > tbody').find('tr').remove();
							    displayMaintainance();
				    		
				    
			    		}else{
			    			
			    		showError("Cannot copy!! <br> you are not  allocated to  this project for this week");
			    		stopProgress();
			    		  
			    		}
				    	
				    }
				    	   
				    	   });
	    
		}
	   }
		
function copyButton(){
	 var approveStatus=$('input[id^=approveStatus]').val();
	
	 var rId=$('input[id^=rrow]').val();
     if (typeof rId  !== "undefined" &&  approveStatus !== "undefined" &&  approveStatus != "R"){
     
    	 $("#copyTimesheet").show();
     }else {
    	 $("#copyTimesheet").hide();
     }		
}

function displayMaintainance(){
//	//alert( $("#maitainanceId").val);

    $("#maitainanceId").css("display","inline-block");
     $("ul.tabs a").removeClass('active');
     $(".tab_div").hide().next("#tab2".hash).show();
     $('a[href$="tab2"]').removeClass('MaintenanceTab');
     $('a[href$="tab2"]').addClass('active');
     $("#comment").hide();
     $.fancybox.close();
     setTimeout(function(){  copyButton(); }, 600);
    
    
}



function getWeekDates(udate){
	
    var form = document.forms['frmSelect'];
    var form1= document.forms['userActivityForm'];
    var date = Date.parse(udate);
    if(date.is().monday()){
           weekStart = date.add(-1).day().toString('MM/dd/yyyy');
           weekEnd=date.next().saturday().toString('MM/dd/yyyy');
    }
    else if(date.is().sunday()){
           weekStart = date.toString('MM/dd/yyyy');
           weekEnd = date.next().saturday().toString('MM/dd/yyyy');
    }
    else{
           weekStart = date.last().sunday().toString('MM/dd/yyyy');
           weekEnd = date.next().saturday().toString('MM/dd/yyyy');
    }
          form.weekStartDate.value = weekStart;
          form.weekEndDate.value =  weekEnd;
          form1.weekStartDateHidden.value = weekStart;
          form1.weekEndDateHidden.value =  weekEnd;
}

function createTableRow(udate){
    var date = Date.parse(udate);
    var sun = date.toString('MM/dd/yyyy');
    var mon = date.add(1).day().toString('MM/dd/yyyy');
    var tue = date.add(1).day().toString('MM/dd/yyyy');
    var wed = date.add(1).day().toString('MM/dd/yyyy');
    var thus = date.add(1).day().toString('MM/dd/yyyy');
    var fri = date.add(1).day().toString('MM/dd/yyyy');
    var sat = date.add(1).day().toString('MM/dd/yyyy');
    
    var str = '';
        str += 
        '<th class="center" align="center">'+sun+'</th>'+
        '<th class="center" align="center">'+mon+'</th>'+
        '<th class="center" align="center">'+tue+'</th>'+
        '<th class="center" align="center">'+wed+'</th>'+
        '<th class="center" align="center">'+thus+'</th>'+
        '<th class="center" align="center">'+fri+'</th>'+
        '<th class="center" align="center">'+sat+'</th>';
        dateString = sun+","+mon+","+tue+","+wed+","+thus+","+fri+","+sat;
        return str;
}

function createTableRowNew(udate){
    var date = Date.parse(udate);
    var sun = date.toString('MM/dd/yyyy');
    var mon = date.add(1).day().toString('MM/dd/yyyy');
    var tue = date.add(1).day().toString('MM/dd/yyyy');
    var wed = date.add(1).day().toString('MM/dd/yyyy');
    var thus = date.add(1).day().toString('MM/dd/yyyy');
    var fri = date.add(1).day().toString('MM/dd/yyyy');
    var sat = date.add(1).day().toString('MM/dd/yyyy');
    
    var str = '';
        str += 
        '<td class="center" align="center">'+sun+'</td>'+
        '<td class="center" align="center">'+mon+'</td>'+
        '<td class="center" align="center">'+tue+'</td>'+
        '<td class="center" align="center">'+wed+'</td>'+
        '<td class="center" align="center">'+thus+'</td>'+
        '<td class="center" align="center">'+fri+'</td>'+
        '<td class="center" align="center">'+sat+'</td>';
        dateString = sun+","+mon+","+tue+","+wed+","+thus+","+fri+","+sat;
        return str;
}

function createTimeLog(tempId, logValues, isReadOnly) {
	var str = '';
	var weekDays = ['sunT', 'monT', 'tueT', 'wedT', 'thuT', 'friT', 'satT'];
	for(i=0;i <= weekDays.length-1; i++) {
		var num = i+1;
		if (logValues[i] == null || logValues[i] == 'null') {
			logValues[i] = ''
		}
		str+= `<td  valign='middle' align='center' class='` + weekDays[i]+ `'><input type='text'  name='entries[`+tempId+`].d`+ num +`Hours' id='d`+ num +`Hoursrow`+tempId+`' onkeyup='checkSendForApproval();'  onfocus='checkSendForApproval();showCommentBox("d`+ num +`Commentrow`+tempId+`")' onblur='validateHour(this);checkHour(this);checkSendForApproval();' maxlength='5'  class='form-control input_log' value='` + logValues[i] +`' ` + isReadOnly +` /></td>`;		
	}
	var activ_total = logValues.reduce((a,b) => Number(a)+Number(b));
	str+= `<td  valign='middle' align='center'><input type='text' class='form-control input_log' data-activity_total="` + tempId +`" value='` + activ_total +`' readonly/></td>`;	
	return str;
}

function createLogComments(tempId, commentValues, isReadOnly) {
	var str = '';
	var hidenCls = '';
	var weekDays = ['sunT', 'monT', 'tueT', 'wedT', 'thuT', 'friT', 'satT', ''];
	var days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', ''];
	for(i=0;i <= weekDays.length-1; i++) {
		var num = i+1;
		if (i>0) {
			hidenCls = ' hide';
		}
		if (commentValues[i] == null || commentValues[i] == 'null') {
			commentValues[i] = ''
		}
		//name='entries[`+tempId+`].d`+ num +`Comment'
		str+= `<td  valign='middle' align='left' class='` + hidenCls + `' colspan='8'>
				<div class="input-group"> 
					<textarea name='entries[`+ tempId +`].d`+num+`Comment' id='d`+ num +`Commentrow`+tempId+`' class='comments_textarea form-control' ` + isReadOnly + ` placeholder="Comment for ` + days[i] +`" onkeyup="commentChange(this)"  maxlength="250">` + commentValues[i] + `</textarea>
					<span class="input-group-btn">
							<button class="btn btn-default" type="button" onclick="copyComment('d`+ num +`Commentrow`+tempId+`')" title="copy comment"><i class="fa fa-files-o"></i></button>
							<button class="btn btn-default paste-btn" type="button" onclick="pasteComment('d`+ num +`Commentrow`+tempId+`')" title="paste comment"><i class="fa fa-clipboard"></i></button>	
					</span>
				</div>
			</td>`;		
			// <textarea  rows='1'></textarea>
	}
	return str;
}

function toggleChildRow(elem) {
	var selectedRow = $(elem).closest('tr');
	var childRow = $(selectedRow).next('.expandable_row');
	$(childRow).toggleClass('hide');
}

function showCommentBox(id) {
	var selectedRowId = id.split('row')[1];
	$('#row_timesheet_' +selectedRowId+ ' .comments_textarea').closest('.input-group').parent().addClass('hide');
	$('#'+id).closest('.input-group').parent().removeClass('hide');
	$('.comments_textarea').removeClass('active');
	$('#'+id).addClass('active');
}
var copiedComment = '';
function copyComment(id) {
	copiedComment = $('#'+ id).val();
	$('.paste-btn').addClass('btn-info');
	
}

function pasteComment(id) {
	$('#'+ id).val(copiedComment);
	
}

function toggleRow(ev) {
	ev ? $('.expandable_row').removeClass('hide') : $('.expandable_row').addClass('hide');
}

function commentChange(ele) {
	if (ele.value) {
		$(ele).next().children('.btn:nth-child(1)').addClass('btn-warning');
	} else {
		$(ele).next().children('.btn:nth-child(1)').removeClass('btn-warning');
	}
	
}

// for actual rows
function getUserActivitiesByWeekStartDateAndEmployeeId(id,weekStart,weekEnd){
	$.getJSON("useractivitys",{find:"ByWeekStartDateBetweenAndEmployeeId_json" ,employeeId:id,minWeekStartDate:weekStart,maxWeekStartDate:weekEnd}, showUserActivities);
}
//for copied rows added by purva for #3088
function getUserActivitiesByWeekStartDateAndEmployeeId1(id,weekStart,weekEnd){
	$.getJSON("useractivitys",{find:"ByWeekStartDateBetweenAndEmployeeId_json" ,employeeId:id,minWeekStartDate:weekStart,maxWeekStartDate:weekEnd}, showUserActivities1);
}

//for actual rows
function showUserActivities(data){
	$("#tblActivity").show();
	$("#saveValues").show();
	$("#submit").show();
	$("#NoAllocMessage").hide();
	/* checkAllocation(); */
	$("#saveValues").removeAttr("disabled", false);
    $("#submit").removeAttr("disabled", false);
    //$("#buttons").find('input[type="button"]').button({disabled:false});
    $("#saveValues").removeClass();
    $("#submit").removeClass();
    $("#comment").hide();
    $.fancybox.close();
    $('#addNewRow').show();
    if(data!=''){
    	
    	var activityFalseCount=0;
		var activityTrueCount=0;    
       	$('#tblActivity > tbody').find('tr').remove();
     // for actual rows
       	userActivityTableRows(data);
       	//$("#tblActivity > tbody").append($("#userActivityTableRows").render(data));
       	//$("table#tblActivity tbody").find("tr:first").find("input[type=text]").keyup();
        document.getElementById("userAction").value="update";
        $('.chzn-select').each(function(){
	        var theValue = $(this).find("option:selected").text();
	        var theId =$(this).val();
	        $(this).data("value", theValue);
	       	$(this).data("id", theId);
	  	});
        isAllTimesheetSubmittedOrApproved = [];
        var rsalIdAndappStArray = [];
        var selDropDownArray = [];
        var flag = 0;
        var subStatusArray = [];
        var rowCount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
        
        if(rowCount>0){
        $.each(data, function(key, val) {
        	rsalIdAndappStArray.push(val.resourceAllocId.id+":"+val.approveStatus);
            subStatusArray.push(val.resourceAllocId.id+":"+val.approveStatus);
       	
            if(val.approveStatus == 'A' || val.approveStatus == 'P'){
            	activityTrueCount++;    
            }  
            
            isAllTimesheetSubmittedOrApproved.push(val.approveStatus);
        });
      
        var rowval=0;
        $("#tblActivity > tbody > tr:not('.expandable_row')").each(function(){
        	var selectVal = $(this).find("td:eq(1) select").val();
               
            selDropDownArray.push('row'+rowval+":"+selectVal);
            rowval++;
       });
        
        var check = false;
        if(subStatusArray.length==selDropDownArray.length){
              for (var i=0;i<selDropDownArray.length;i++){ 
                var subDbVal=subStatusArray[i].split(":");
                
                var rsUserval=selDropDownArray[i].split(":");
                if(subDbVal[0]==rsUserval[1]){
                   var statusCheck=subDbVal[1];
                if('N'==statusCheck){
               	 check = true;
                }
                   if('P'==statusCheck){
                 
                   var rowid=rsUserval[0];
                   var inputs = $("input, textarea");
                   
                // commenting this whole section: requirement -need to fill timesheet for future dates
                  /*  $("#"+rowid).find("td").find('input').attr("readonly","readonly");
                   $("#"+rowid).find("td").find("select").attr("disabled","disabled"); */
                   
                   $("#buttons").find("input").attr("disabled","disabled");
                   $("#buttons").find('input[type="button"]').button({disabled:true});
                  // $('.remove').click(function () {return false;});
                   //$('#saveValues').append('<div style="position: absolute;top:0;left:0;width: 100%;height:100%;z-index:2;opacity:0.4;filter: alpha(opacity = 50)"></div>');
                   $('#addNewRow').hide(); // #25 Approved time sheet should not have Add New Link
                    } 
                  
               }  
               
            }
          }
        	
         
        		if(check){
        	          var flag = true;
        	           var rowcount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
        	           
        	           /* code to check the value of total hours field  */
        	           
        	           if(document.getElementById('monTtl').value=="0"){
            	            
          	              flag=false;
          	        
          	             
          	            }
          	          if(document.getElementById('tueTtl').value=="0"){
              	            
          	              flag=false;
          	         
          	             
          	            }
          	          
          	          if(document.getElementById('wedTtl').value=="0"){
            	            
          	              flag=false;
          	           
          	             
          	            }
          	          if(document.getElementById('thuTtl').value=="0"){
            	            
          	              flag=false;
          	            
          	             
          	            }
          	          if(document.getElementById('friTtl').value=="0"){
            	            
          	              flag=false;
          	              
          	          }
          	          
          	          /* ........................ */
        	              
        	            if(flag==false){
        	             
        	                    // $("#saveValues").prop("disabled", true);
        	                     $("#submit").prop("disabled", true);
        	                     $("#buttons").find('input[id="submit"]').button({disabled:true});
        	                  }
        	            
          	          /* if all days of week are filled properly then enable "send for approval " button. */
          	          
        	            else{
        	           $("#saveValues").removeAttr("disabled", false);
        	                $("#submit").removeAttr("disabled", false);
        	                $("#buttons").find('input[type="button"]').button({disabled:false});
        	                $("#saveValues").removeClass();
        	                $("#submit").removeClass();
        	            }
        	         }
        	
       
        if(rsalIdAndappStArray.length==selDropDownArray.length){
          for (var i=0;i<selDropDownArray.length;i++){ 
            var rsDBval=rsalIdAndappStArray[i].split(":");
           
            var rsUserval=selDropDownArray[i].split(":");
            if(rsDBval[0]==rsUserval[1]){
               var appstatusCheck=rsDBval[1];
                
               if('A'==appstatusCheck){
               	 
               var rowid=rsUserval[0];
               var inputs = $("input, textarea");
                
            // commenting this whole section: requirement -need to fill timesheet for future dates
             /*   $("#"+rowid).find("td").find('input').attr("readonly","readonly");
                  $("#"+rowid).find("td").find("select").attr("disabled","disabled"); */
           //    $('#saveValues').append('<div style="position: absolute;top:0;left:0;width: 100%;height:100%;z-index:2;opacity:0.4;filter: alpha(opacity = 50)"></div>');
                  $("#buttons").find("input").attr("disabled","disabled");//.addClass("disableBtn");
                  $("#buttons").find('input[type="button"]').button({disabled:true});
                 // $('#buttons').find("input").css('color', '#ccc'); // grey out
                
                   $("buttons").find('input[type="button"]').mouseover(function() {
   						$('buttons').find('input[type="button"]').removeClass("ui-state-hover");
 					});
                   $('buttons').find('input').removeClass("hover");
                   $('buttons').find('input').removeClass("ui-state-hover");
  					$('#buttons').click(function() { return false; });
                  $('.remove').click(function () {return false;});
                  $('#addNewRow').hide();
                  
                }
               if('R'==appstatusCheck){
          	 $("#saveValues").removeAttr("disabled", false);
               $("#submit").removeAttr("disabled", false);
               $("#buttons").find('input[type="button"]').button({disabled:false});
               $("#saveValues").removeClass();
               $("#submit").removeClass();
              
          }
           }      
        }
      }
       if(activityTrueCount==rowCount){
       }else{
        
            /* $("#saveValues").removeAttr("disabled", "");
            $("#submit").removeAttr("disabled", ""); */
          	$("#saveValues").removeAttr("disabled", false);
            $("#submit").removeAttr("disabled", false);
            $("#buttons").find('input[type="button"]').button({disabled:false});
            $("#saveValues").removeClass();
            $("#submit").removeClass();
            $('#addNewRow').show();
           }
        }
        
        if( (document.getElementById("monTtl").value <=0) ||
    			(document.getElementById("tueTtl").value <=0) ||  
    			(document.getElementById("wedTtl").value <=0) ||
                (document.getElementById("thuTtl").value <=0) || 
                (document.getElementById("friTtl").value <=0))
   			{
			$("#buttons").find('input[type="button"]').button({disabled:false});
			$("#submit").attr("disabled",true);
			$("#submit").addClass(" copyInactive");
   			}
        
       changeCommentIcon();
       validateHour(this);
       checkHour(this);
       //
       checkSendForApproval();
       stopProgress(); 
   
     }
     else{
    	 
    	 disableAll();
    	  	document.getElementById("comment").style.display = "none";
    		document.getElementById("comment").style.visibility = "hidden";

    $("#buttons").find('input[type="button"]').button({disabled:true});
    timeSheetStatus = "N";
		 createNewRow();
		 $('#expand_all').prop('checked', true);
    	changeCommentIcon();
    	stopProgress(); 
    
    }
  var tbodyrowlength =  $("#tblActivity > tbody > tr:not('.expandable_row')").length;
  var weekhrsvalue =  $("#weekHrs").val();
  if(tbodyrowlength==1 && weekhrsvalue ==0){
	  var id = $("#resourceAllocIdrow0").val();
	  if(id == null || id == ''){
		  checkAllocation();
	  }
	  /* $.each(data, function(key, val) {
		  var id = $("#resourceAllocIdrow"+key).val();
		  var actId = val.activityId.id+"_"+val.activityId.activityType;
		  //getActiveActivityByProjectId(id, 0, key, actId);
		  getAllActivityByProjectId(id, key, actId);
      });*/ 
  	}
  if(tbodyrowlength>=1 && weekhrsvalue>=0){
	  $.each(data, function(key, val) {
		  var id = $("#resourceAllocIdrow"+key).val();
		  var actId = val.activityId.activityId+"_"+val.activityId.activityType;
		  getAllActivityByProjectId(id, key, actId);
      });
   }
}
//for copied rows added by purva for #3088
function showUserActivities1(data){
	////alert(td);
////alert(JSON.stringify(data));

 
	$("#tblActivity").show();
	$("#saveValues").show();
	$("#submit").show();
	$("#NoAllocMessage").hide();
	/* checkAllocation(); */
	$("#saveValues").removeAttr("disabled", false);
    $("#submit").removeAttr("disabled", false);
    //$("#buttons").find('input[type="button"]').button({disabled:false});
    $("#saveValues").removeClass();
    $("#submit").removeClass();
    $("#comment").hide();
    $.fancybox.close();
    $('#addNewRow').show();
 
    if(data!=''){ 
    	var activityFalseCount=0;
		var activityTrueCount=0;    
       	$('#tblActivity > tbody').find('tr').remove();
     // for copied rows
       	userActivityTableRows1(data);
      /*  	//$("#tblActivity > tbody").append($("#userActivityTableRows").render(data));
       	//$("table#tblActivity tbody").find("tr:first").find("input[type=text]").keyup(); */
        document.getElementById("userAction").value="update";
        $('.chzn-select').each(function(){
	        var theValue = $(this).find("option:selected").text();
	        var theId =$(this).val();
	        $(this).data("value", theValue);
	       	$(this).data("id", theId);
	  	});
        isAllTimesheetSubmittedOrApproved = [];
        var rsalIdAndappStArray = [];
        var selDropDownArray = [];
        var flag = 0;
        var subStatusArray = [];
        var rowCount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
        
        if(rowCount>0){
        $.each(data, function(key, val) {
        	rsalIdAndappStArray.push(val.resourceAllocId.id+":"+val.approveStatus);
            subStatusArray.push(val.resourceAllocId.id+":"+val.approveStatus);
       	
            if(val.approveStatus == 'A' || val.approveStatus == 'P'){
            	activityTrueCount++;    
            }  
            
            isAllTimesheetSubmittedOrApproved.push(val.approveStatus);
        });
      
        var rowval=0;
        $("#tblActivity > tbody > tr:not('.expandable_row')").each(function(){
        	var selectVal = $(this).find("td:eq(1) select").val();
            selDropDownArray.push('row'+rowval+":"+selectVal);
            rowval++;
       });
        var check = false;
        if(subStatusArray.length==selDropDownArray.length){
              for (var i=0;i<selDropDownArray.length;i++){ 
                var subDbVal=subStatusArray[i].split(":");
                
                var rsUserval=selDropDownArray[i].split(":");
                if(subDbVal[0]==rsUserval[1]){
                   var statusCheck=subDbVal[1];
                if('N'==statusCheck){
               	 check = true;
                }
                   if('P'==statusCheck){
                 
                   var rowid=rsUserval[0];
                   var inputs = $("input, textarea");
                   
                 } 
                  
               }  
               
            }
          }
        	
         
        		if(check){
        	          var flag = true;
        	           var rowcount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
        	           
        	           /* code to check the value of total hours field  */
        	           
        	           if(document.getElementById('monTtl').value=="0"){
            	            
          	              flag=false;
          	        
          	             
          	            }
          	          if(document.getElementById('tueTtl').value=="0"){
              	            
          	              flag=false;
          	         
          	             
          	            }
          	          
          	          if(document.getElementById('wedTtl').value=="0"){
            	            
          	              flag=false;
          	           
          	             
          	            }
          	          if(document.getElementById('thuTtl').value=="0"){
            	            
          	              flag=false;
          	            
          	             
          	            }
          	          if(document.getElementById('friTtl').value=="0"){
            	            
          	              flag=false;
          	              
          	          }
          	          
          	          /* ........................ */
        	              
        	            if(flag==false){
        	             
        	                    // $("#saveValues").prop("disabled", true);
        	                     $("#submit").prop("disabled", true);
        	                     $("#buttons").find('input[id="submit"]').button({disabled:true});
        	                  }
        	            
          	          /* if all days of week are filled properly then enable "send for approval " button. */
          	          
        	            else{
        	           $("#saveValues").removeAttr("disabled", false);
        	                $("#submit").removeAttr("disabled", false);
        	                $("#buttons").find('input[type="button"]').button({disabled:false});
        	                $("#saveValues").removeClass();
        	                $("#submit").removeClass();
        	            }
        	         }
        	
       
        if(rsalIdAndappStArray.length==selDropDownArray.length){
          for (var i=0;i<selDropDownArray.length;i++){ 
            var rsDBval=rsalIdAndappStArray[i].split(":");
           
            var rsUserval=selDropDownArray[i].split(":");
            if(rsDBval[0]==rsUserval[1]){
               var appstatusCheck=rsDBval[1];
                
               if('A'==appstatusCheck){
               	 
               var rowid=rsUserval[0];
               var inputs = $("input, textarea");
                
            // commenting this whole section: requirement -need to fill timesheet for future dates
             /*   $("#"+rowid).find("td").find('input').attr("readonly","readonly");
                  $("#"+rowid).find("td").find("select").attr("disabled","disabled"); */
           //    $('#saveValues').append('<div style="position: absolute;top:0;left:0;width: 100%;height:100%;z-index:2;opacity:0.4;filter: alpha(opacity = 50)"></div>');
                  $("#buttons").find("input").attr("disabled","disabled");//.addClass("disableBtn");
                  $("#buttons").find('input[type="button"]').button({disabled:true});
                 // $('#buttons').find("input").css('color', '#ccc'); // grey out
                
                   $("buttons").find('input[type="button"]').mouseover(function() {
   						$('buttons').find('input[type="button"]').removeClass("ui-state-hover");
 					});
                   $('buttons').find('input').removeClass("hover");
                   $('buttons').find('input').removeClass("ui-state-hover");
  					$('#buttons').click(function() { return false; });
                  $('.remove').click(function () {return false;});
                  $('#addNewRow').hide();
                  
                }
               if('R'==appstatusCheck){
          	 $("#saveValues").removeAttr("disabled", false);
               $("#submit").removeAttr("disabled", false);
               $("#buttons").find('input[type="button"]').button({disabled:false});
               $("#saveValues").removeClass();
               $("#submit").removeClass();
              
          }
           }      
        }
      }
       if(activityTrueCount==rowCount){
       }else{
        
            $("#saveValues").removeAttr("disabled", false);
            $("#submit").removeAttr("disabled", false);
            $("#buttons").find('input[type="button"]').button({disabled:false});
            $("#saveValues").removeClass();
            $("#submit").removeClass();
            $('#addNewRow').show();
           }
        }
        
        if( (document.getElementById("monTtl").value <=0) ||
    			(document.getElementById("tueTtl").value <=0) ||  
    			(document.getElementById("wedTtl").value <=0) ||
                (document.getElementById("thuTtl").value <=0) || 
                (document.getElementById("friTtl").value <=0))
   			{
			$("#buttons").find('input[type="button"]').button({disabled:false});
			$("#submit").attr("disabled",true);
			$("#submit").addClass(" copyInactive");
   			}
        
       changeCommentIcon();      
	   checkSendForApproval();
	   validateHour(this);
	   checkHour(this);
	   $('.input_log').trigger('blur');
	   
       stopProgress(); 
   
     }
     else{
    	 
    	 disableAll();
    	  	document.getElementById("comment").style.display = "none";
    		document.getElementById("comment").style.visibility = "hidden";

    $("#buttons").find('input[type="button"]').button({disabled:true});
    timeSheetStatus = "N";
	createNewRow();
	$('#expand_all').prop('checked', true);
    	changeCommentIcon();
    	stopProgress(); 
    
    }
    var tbodyrowlength =  $("#tblActivity tbody > tr:not('.expandable_row')").length;
    var weekhrsvalue =  $("#weekHrs").val();
    if(tbodyrowlength==1 && weekhrsvalue ==0){
  	  var id = $("#resourceAllocIdrow0").val();
  	  if(id == null || id == ''){
  		  checkAllocation();
  	  }
  	  
    }
    if(tbodyrowlength>=1 && weekhrsvalue>=0){
  	  $.each(data, function(key, val) {
  		  var id = $("#resourceAllocIdrow"+key).val();
  		  var actId = val.activityId.activityId+"_"+val.activityId.activityType;
  		  getAllActivityByProjectId(id, key, actId);
        });
     }
}
function checkAllocation(){
	//if(projAllocId == 0 || allocStartFlag == projectStartDate.length){
		$("#tblActivity").hide();
		$("#saveValues").hide();
		$("#submit").hide();
		$("#NoAllocMessage").show();
	//}
}
//for actual rows
function userActivityTableRows(data, copy){
	
	 
	var htmlVar = '';
	var activityNotSet =false;
	$.each(data, function(key,val){ 
	var row = data[key];
	var commentValues = [row.d1Comment, row.d2Comment, row.d3Comment, row.d4Comment, row.d5Comment, row.d6Comment, row.d7Comment];
	var logValues = [row.d1Hours, row.d2Hours, row.d3Hours, row.d4Hours, row.d5Hours, row.d6Hours, row.d7Hours, ''];
	var WeeklyDateStart = document.getElementById("weekStartDateHidden").value;

		
        htmlVar += '<tr id="row'+key+'" title="" class="even">';
        
        if(data[key].approveStatus == 'R'){
        	isButtonDisabled=false;
        	htmlVar += '<td align="center" valign="middle">';
        	 htmlVar += '<a href="#comment" id="comment_timehrsList">';
        	 htmlVar += '<img src="resources/images/reject_timesheet.png" title="Click to see Reject comments" onclick="getRejectionRemarks(\'' + data[key].rejectionRemarks + '\');" />';
        	 htmlVar += '</a>';
        	 htmlVar += '</td>';
        }
        
        if(data[key].approveStatus == 'A'){
        	
        	isButtonDisabled=true;
        	htmlVar += '<td align="center" valign="middle"><i class="fa fa-check-circle approved" title="Approved"></i></td>';
        }
        if(data[key].approveStatus == 'N'){
        	isButtonDisabled=false;
        	htmlVar += '<td align="center" valign="middle"><i class="fa fa-check-circle saved" title="Not submitted"></i></td>';
        }
        
        if(data[key].approveStatus == 'P'){
        	        	isButtonDisabled=true;
        	htmlVar += '<td align="center" valign="middle"><i class="fa fa-exclamation-circle review" title="Review Pending"></i></td>';
        }
      
        htmlVar += '<input type="hidden" name="entries['+key+'].employeeId" id="employeeId" value="'+data[key].employeeId.employeeId+'">';
		if (!copy) {
			htmlVar += '<input type="hidden" name="entries['+key+'].id" id="rrow'+key+'" value="'+data[key].id+'">';
			htmlVar += '<input type="hidden" name="statusTimesheet" id="approveStatus'+key+'" value="'+data[key].approveStatus+'">';
		}
        htmlVar += '<td>';
        if(data[key].approveStatus == 'A' || data[key].approveStatus == 'P'){
	        htmlVar += '<select tabindex="2" disabled style="width: 150px;"  class="chzn-select comboselect" id="resourceAllocIdrow'+key+'"   name="entries['+key+'].resourceAllocId">';
	        htmlVar += '<c:forEach var="resourceAllocation" items="${resourceAllocation}">';
	        if(validateDateFunction('${resourceAllocation.allocStartDate}', '${resourceAllocation.allocEndDate}')){
	        	if(data[key].resourceAllocId.id == '${resourceAllocation.id}'){
					htmlVar += '<option value="${resourceAllocation.id}" selected="selected">${resourceAllocation.projectId.projectName}</option>';
	        	}else{
					htmlVar += '<option value="${resourceAllocation.id}">${resourceAllocation.projectId.projectName}</option>';
	        	}
	        }
	        htmlVar += '</c:forEach>';
	        htmlVar += '</select>';
        }else{
        	
        	isButtonDisabled=false;
        	htmlVar += '<select tabindex="2" style="width: 150px;"  class="chzn-select comboselect" onchange=getActiveActivityByProjectId(value,'+key+');getActiveModulesByProjectIdOnProjectChange(value,'+key+');getTicketPriorityAndStatusByProjectIdOnProjectChange(value,'+key +'); id="resourceAllocIdrow'+key+'"   name="entries['+key+'].resourceAllocId">';
			
			htmlVar += '<c:forEach var="resourceAllocation" items="${resourceAllocation}">';
			if(validateDateFunction('${resourceAllocation.allocStartDate}', '${resourceAllocation.allocEndDate}')){
	        	if(data[key].resourceAllocId.id == '${resourceAllocation.id}'){
					htmlVar += '<option value="${resourceAllocation.id}" selected="selected">${resourceAllocation.projectId.projectName}</option>';
	        	}else{
					htmlVar += '<option value="${resourceAllocation.id}">${resourceAllocation.projectId.projectName}</option>';
	        	}
	        }
			htmlVar += '</c:forEach>';
	        htmlVar += '</select>';
        }
        htmlVar += '</td>';
        
        htmlVar += '<td valign="middle">';
        if(data[key].approveStatus == 'A' || data[key].approveStatus == 'P'){
        	htmlVar += '<select id="activityId'+key+'" disabled class="comboselect" name="entries['+key+'].activityId">';
        	htmlVar += '<c:forEach var="activity" items="${activities}">';
        	if(data[key].activityId.id == '${activity.id}'){
        		htmlVar += '<option value="${activity.id}" selected="selected">${activity.activityName}</option>';
        	}else{
        		htmlVar += '<option value="${activity.id}">${activity.activityName}</option>';
        	}
        	htmlVar += '</c:forEach>';
	        htmlVar += '</select>';
        }else{
        	htmlVar += '<select id="activityId'+key+'" class="comboselect" name="entries['+key+'].activityId">';
        	htmlVar += '<c:forEach var="activity" items="${activities}">';
        	if(data[key].activityId!=null){
	        	if(data[key].activityId.id == '${activity.id}'){
	        		htmlVar += '<option value="${activity.id}" selected="selected">${activity.activityName}</option>';
	        	}else{
	        		htmlVar += '<option value="${activity.id}">${activity.activityName}</option>';
	        	}
        	}else{
        		//htmlVar += '<option value="${activity.id}">${activity.activityName}</option>';
        		activityNotSet=true;
        	}
        	htmlVar += '</c:forEach>';
	        htmlVar += '</select>';
        }
		htmlVar += '</td>';
		
        htmlVar += '<td valign="middle">';
    	if(data[key].module!=null)
    	{
    		var approval
    		if(data[key].module=='-1')
    		{htmlVar += '<input type="text" id="module'+key+'" name="entries['+key+'].module" value=""';}
    		else
    		{htmlVar += '<input type="text" id="module'+key+'"  name="entries['+key+'].module" value="'+data[key].module+'"';}
    		if(data[key].approveStatus == 'A' || data[key].approveStatus == 'P'){
            	htmlVar += 'readonly';
            }
    		else
    		{
    			//alert("else alone");
    			htmlVar += 'onfocus=getActiveModulesByProjectId("'+data[key].resourceAllocId.id+'",'+key+',value,1);';
    		}
           	
    		htmlVar += '>';
            htmlVar += '</td>';
    	}
    	
    	////////////Adding SubModule
    	if(data[key].subModule==null || data[key].subModule=='null')
        	data[key].subModule = "";
    	
    	if(data[key].subModule!=null)
    	{
    		 
    		htmlVar += '<td valign="middle">';
    		var approval;
    		if(data[key].subModule=='-1')
    		{htmlVar += '<input type="text" id="subModule'+key+'" name="entries['+key+'].subModule" value=""';}
    		else
    		{htmlVar += '<input type="text" id="subModule'+key+'" name="entries['+key+'].subModule" value="'+data[key].subModule+'"';}
    		 if(data[key].approveStatus == 'A' || data[key].approveStatus == 'P'){
            	htmlVar += 'readonly';
            }
    		else
    		{
    			var mystr = "'"+data[key].module.toString().trim()+"'";
    			htmlVar += 'onfocus="getActiveSubModulesByModuleId('+mystr+','+ key+',value,2);"';
    		
    		} 
           	
    		htmlVar += '>';
            htmlVar += '</td>';
    	}
    	//////////////////////////////
		if(data[key].ticketNo==null){
    		data[key].ticketNo="";
    	}
        
        htmlVar += '<td valign="middle" align="center">';
        htmlVar += '<input type="text" onChange="getTicketPriorityAndStatus('+key+');" id="ticketNo'+key+'" name="entries['+key+'].ticketNo"  value="'+data[key].ticketNo+'"';
        if(data[key].approveStatus == 'A' || data[key].approveStatus == 'P'){
        	htmlVar += 'readonly';
        }
        htmlVar += '>';
		htmlVar += '</td>';

        /*
        Ticket priority and status is added @Pankaj Birla  
        getTicketPriorityAndStatusHTML() - function is at rmsTicketPriorityStatus.js
        and all function related to ticket priority and status is also there.
        */      
         htmlVar += getTicketPriorityAndStatusHTML(data,key); 
         // ticket priority and status done.......
		
		 /* commented by mustafa 
        htmlVar += '<td width="5%" valign="middle" align="left" class="sunT">';
        htmlVar += '<div class="poRel">';
        if(data[key].d1Hours!=null){
        	htmlVar += '<input type="text" class="small"   onblur="(this);checkHour(this); " id="d1Hoursrow'+key+'" name="entries['+key+'].d1Hours" maxlength="5" value="'+data[key].d1Hours+'"';
        }else{
        	htmlVar += '<input type="text" class="small"   onblur="(this);checkHour(this); " id="d1Hoursrow'+key+'" name="entries['+key+'].d1Hours" maxlength="5" value=""';
        }
        if(getDateFunction(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')||activityNotSet==true){
        	htmlVar += 'readonly';
        }
        htmlVar += '><img width="24" height="23" class="comments" src="resources/images/comments.png"/>';
        htmlVar += '<div class="comments_div">';
        htmlVar += '<textarea rows="" cols="" id="d1Commentrow'+key+'" class="comments_textarea" name="entries['+key+'].d1Comment"';
        if(isCommentDisabled(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')||activityNotSet==true){
        	htmlVar += 'readonly';
        }
        htmlVar += '>'+data[key].d1Comment+'</textarea>';
        htmlVar += '<div class="clear"></div>';
        htmlVar += '<div class="btns">';
        htmlVar += '<input type="button" class="okBtn" value="Ok">';
        htmlVar += '</div>';
        htmlVar += '</div>';
        htmlVar += '</div></td>';
        
        htmlVar += '<td width="5%" valign="middle" align="left" class="monT" ><div class="poRel">';
        if(data[key].d2Hours!=null){
        	htmlVar += '<input type="text" id="d2Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();"  onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d2Hours" class="small" value="'+data[key].d2Hours+'"';
        }else{
        	htmlVar += '<input type="text" id="d2Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();"  onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d2Hours" class="small" value=""';
        }
        if(getDateFunction(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')||activityNotSet==true){
        	htmlVar += 'readonly';
        }
        htmlVar += '><img width="24" height="23" class="comments" src="resources/images/comments.png" />';
        htmlVar += '<div class="comments_div">';
        htmlVar += '<textarea rows="" cols="" id="d2Commentrow'+key+'" class="comments_textarea" name="entries['+key+'].d2Comment"';
        if(isCommentDisabled(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')||activityNotSet==true){
        	htmlVar += 'readonly';
        }
        htmlVar += '>'+data[key].d2Comment+'</textarea>';
        htmlVar += '<div class="clear"></div>';
        htmlVar += '<div class="btns">';
        htmlVar += '<input type="button" class="okBtn" value="Ok">';
        htmlVar += '</div>';
        htmlVar += '</div>';
        htmlVar += '</div></td>';
        
        htmlVar += '<td width="5%" valign="middle" align="left" class="tueT"><div class="poRel">'
        if(data[key].d3Hours!=null){
        	htmlVar += '<input type="text" id="d3Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();"  onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d3Hours" class="small" value="'+data[key].d3Hours+'"';
        }else{
        	htmlVar += '<input type="text" id="d3Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();"  onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d3Hours" class="small" value=""';
        }
        if(getDateFunction(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')||activityNotSet==true){
        	htmlVar += 'readonly';
        }
        htmlVar += '><img width="24" height="23" class="comments" src="resources/images/comments.png" />';
        htmlVar += '<div class="comments_div">';
        htmlVar += '<textarea rows="" cols="" id="d3Commentrow'+key+'" class="comments_textarea" name="entries['+key+'].d3Comment"';
        if(isCommentDisabled(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')||activityNotSet==true){
        	htmlVar += 'readonly';
        }
        htmlVar += '>'+data[key].d3Comment+'</textarea>';
        htmlVar += '<div class="clear"></div>';
        htmlVar += '<div class="btns">';
        htmlVar += '<input type="button" class="okBtn" value="Ok">';
        htmlVar += '</div>';
        htmlVar += '</div>';
        htmlVar += '</div></td>';
        
        htmlVar += '<td width="5%" valign="middle" align="left" class="wedT"><div class="poRel">';
        if(data[key].d4Hours!=null){
        	htmlVar += '<input type="text" id="d4Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();" onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval(); " name="entries['+key+'].d4Hours" class="small" value="'+data[key].d4Hours+'"';
        }else{
        	htmlVar += '<input type="text" id="d4Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();" onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval(); " name="entries['+key+'].d4Hours" class="small" value=""';
        }
        if(getDateFunction(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')||activityNotSet==true){
        	htmlVar += 'readonly';
        }
        htmlVar += '><img width="24" height="23" class="comments" src="resources/images/comments.png" />';
        htmlVar += '<div class="comments_div">';
        htmlVar += '<textarea rows="" cols="" id="d4Commentrow'+key+'" class="comments_textarea" name="entries['+key+'].d4Comment"';
        if(isCommentDisabled(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')||activityNotSet==true){
        	htmlVar += 'readonly';
        }
        htmlVar += '>'+data[key].d4Comment+'</textarea>';
        htmlVar += '<div class="clear"></div>';
        htmlVar += '<div class="btns">';
        htmlVar += '<input type="button" class="okBtn" value="Ok">';
        htmlVar += '</div>';
        htmlVar += '</div>';
        htmlVar += '</div></td>';
        
        htmlVar += '<td width="5%" valign="middle" align="left" class="thuT"><div class="poRel">';
        if(data[key].d5Hours!=null){
        	htmlVar += '<input type="text" id="d5Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();" onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d5Hours" class="small" value="'+data[key].d5Hours+'"';
        }else{
        	htmlVar += '<input type="text" id="d5Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();" onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d5Hours" class="small" value=""';
        }
        if(getDateFunction(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')||activityNotSet==true){
        	htmlVar += 'readonly';
        }
        htmlVar += '><img width="24" height="23" class="comments" src="resources/images/comments.png" />';
        htmlVar += '<div class="comments_div">';
        htmlVar += '<textarea rows="" cols="" id="d5Commentrow'+key+'" class="comments_textarea" name="entries['+key+'].d5Comment"';
        if(isCommentDisabled(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')||activityNotSet==true){
        	htmlVar += 'readonly';
        }
        htmlVar += '>'+data[key].d5Comment+'</textarea>';
        htmlVar += '<div class="clear"></div>';
        htmlVar += '<div class="btns">';
        htmlVar += '<input type="button" class="okBtn" value="Ok">';
        htmlVar += '</div>';
        htmlVar += '</div>';
        htmlVar += '</div></td>';
        
        htmlVar += '<td width="5%" valign="middle" align="left" class="friT"><div class="poRel">';
        if(data[key].d6Hours!=null){
        	htmlVar += '<input type="text" id="d6Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();"  onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d6Hours" class="small" value="'+data[key].d6Hours+'"';
        }else{
        	htmlVar += '<input type="text" id="d6Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();"  onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d6Hours" class="small" value=""';
        }
        if(getDateFunction(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')||activityNotSet==true){
        	htmlVar += 'readonly';
        }
        htmlVar += '><img width="24" height="23" class="comments" src="resources/images/comments.png" />';
        htmlVar += '<div class="comments_div">';
        htmlVar += '<textarea rows="" cols="" id="d6Commentrow'+key+'" class="comments_textarea" name="entries['+key+'].d6Comment"';
        if(isCommentDisabled(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')||activityNotSet==true){
        	htmlVar += 'readonly';
        }
        htmlVar += '>'+data[key].d6Comment+'</textarea>';
        htmlVar += '<div class="clear"></div>';
        htmlVar += '<div class="btns">';
        htmlVar += '<input type="button" class="okBtn" value="Ok">';
        htmlVar += '</div>';
        htmlVar += '</div>';
        htmlVar += '</div></td>';
        
        htmlVar += '<td width="5%" valign="middle" align="left" class="satT"><div class="poRel">';
        if(data[key].d7Hours!=null){
        	htmlVar += '<input type="text" id="d7Hoursrow'+key+'" maxlength="5" onblur="validateHour(this);checkHour(this);" name="entries['+key+'].d7Hours" class="small"  value="'+data[key].d7Hours+'"';
        }else{
        	htmlVar += '<input type="text" id="d7Hoursrow'+key+'" maxlength="5" onblur="validateHour(this);checkHour(this);" name="entries['+key+'].d7Hours" class="small"  value=""';
        }
        if(getDateFunction(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')||activityNotSet==true){
        	htmlVar += 'readonly';
        }
        htmlVar += '><img width="24" height="23" class="comments" src="resources/images/comments.png" />';
        htmlVar += '<div class="comments_div">';
        htmlVar += '<textarea rows="" cols="" id="d7Commentrow'+key+'" class="comments_textarea" name="entries['+key+'].d7Comment"';
        if(isCommentDisabled(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')||activityNotSet==true){
        	htmlVar += 'readonly';
        }
        htmlVar += '>'+data[key].d7Comment+'</textarea>';
        htmlVar += '<div class="clear"></div>';
        htmlVar += '<div class="btns">';
        htmlVar += '<input type="button" class="okBtn" value="Ok">';
        htmlVar += '</div>';
        htmlVar += '</div>';
        htmlVar += '</div></td>';
        
        htmlVar += '<td valign="middle" align="center">';
        htmlVar += '<input type="hidden" id="approveField'+key+'" value="'+data[key].approveStatus+'" name="entries['+key+'].approveStatus" />';
        if(data[key].approveStatus == 'N' || data[key].approveStatus == 'R'){
        	htmlVar += '<img width="16" height="16" src="resources/images/remove.png" class="remove" />';
        }else{
        	htmlVar += '<img width="16" height="16" src="resources/images/remove.png" class="removeInactive" />';
        }
        htmlVar += '</td>';
        
        htmlVar += '<td valign="middle" align="center">';
        if(data[key].approveStatus == 'N' || data[key].approveStatus == 'R'){
        	htmlVar += '<a href="javascript:void(0);" class="copy" id="copyHtml" >Copy</a>';
        }else{
        	htmlVar += '<a href="#" class="copyInactive">Copy</a>';
        }
		htmlVar += '</td>';

		commented by mustafa */
		
        
        htmlVar += '<input type="hidden" id="approveField'+key+'" value="'+data[key].approveStatus+'" name="entries['+key+'].approveStatus" />';

		htmlVar+="<td valign='middle' align='center' class='action'><i class='fa fa-chevron-circle-down' onclick='toggleChildRow(this)' title='expand'></i>";

        if(data[key].approveStatus == 'N' || data[key].approveStatus == 'R'){
			htmlVar += "<i class='copy fa fa-copy' id='copyHtml' title='copy record'></i>";
        }else{
        	htmlVar += '<i class="fa fa-copy copyInactive" title="copy record"></i>';
		}

		if(data[key].approveStatus == 'N' || data[key].approveStatus == 'R'){
			htmlVar +="<i class='fa fa-trash remove' title='delete record'></i>";
        }else{
			htmlVar +="<i class='fa fa-trash removeInactive' title='delete record'></i>";
		}
		htmlVar+='</td>';

		htmlVar+="</td>"; 
		htmlVar+="</tr>";
		var logDisabled = '';
		var commentDisabled = '';
		if(getDateFunction(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')||activityNotSet==true){
        	logDisabled = 'readonly';
		}
		
		if(isCommentDisabled(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')||activityNotSet==true){
        	commentDisabled = 'readonly';
        }

		htmlVar+="<tr id='row_timesheet_"+(key)+"' class='expandable_row hide'><td colspan="+9+">";
		htmlVar+='<table cellpadding="0" cellspacing="0" border="0" style="width: 100%;">'+
		'<tr><td>Day</td><td align="center">Sunday</td><td align="center">Monday</td><td align="center">Tuesday</td><td align="center">Wednesday</td><td align="center">Thursday</td><td align="center">Friday</td><td align="center">Saturday</td><td  align="center">Log Time</td></tr>'+
		'<tr><td>Date</td>'+createTableRowNew(WeeklyDateStart)+'</tr>' +
		'<tr class="logHrs"><td>Log Time (in hours)</td>'+createTimeLog(key, logValues, logDisabled)+'</tr>' +	 
		'<tr><td>Comments</td>'+createLogComments(key,commentValues, commentDisabled)+'</tr>' +	 	 
		'</table>';
		htmlVar+="</td></tr>"; 
    });
	$("#tblActivity > tbody").append(htmlVar);
}

//for copied rows added by purva for #3088
function userActivityTableRows1(data){
	var htmlVar = '';
	var temp = JSON.stringify(data);
	$.each(data, function(key,val){
	var row = data[key];
	var commentValues = [row.d1Comment, row.d2Comment, row.d3Comment, row.d4Comment, row.d5Comment, row.d6Comment, row.d7Comment];
	var logValues = [row.d1Hours, row.d2Hours, row.d3Hours, row.d4Hours, row.d5Hours, row.d6Hours, row.d7Hours, ''];
	var WeeklyDateStart = document.getElementById("weekStartDateHidden").value;

        htmlVar += '<tr id="row'+key+'" title="" class="even">';
        data[key].approveStatus='N'; //added for 3088 for setting status as N to enable send buttons 
        if(data[key].approveStatus == 'R'){
        	 htmlVar += '<td align="center" valign="middle">';
        	 htmlVar += '<a href="#comment" id="comment_timehrsList">';
        	 htmlVar += '<img src="resources/images/reject_timesheet.png" title="Click to see Reject comments" onclick="getRejectionRemarks(\'' + data[key].rejectionRemarks + '\');" />';
        	 htmlVar += '</a>';
        	 htmlVar += '</td>';
        }
        
        if(data[key].approveStatus == 'A'){
        	htmlVar += '<td align="center" valign="middle"><i class="fa fa-check-circle approved" title="Approved"></i></td>';
        }

        if(data[key].approveStatus == 'N'){
        	htmlVar += '<td align="center" valign="middle"><i class="fa fa-check-circle saved" title="Not submitted"></i></td>';
        }
        
        if(data[key].approveStatus == 'P'){
        	htmlVar += '<td align="center" valign="middle"><i class="fa fa-exclamation-circle review" title="Review Pending"></i></td>';
        }
        // commented for #3088
     //   //alert(data[key].id);
     //   htmlVar += '<input type="hidden" name="entries['+key+'].id" id="rrow'+key+'" value="'+data[key].id+'">';
        htmlVar += '<input type="hidden" name="entries['+key+'].employeeId" id="employeeId" value="'+data[key].employeeId.employeeId+'">';
        
        htmlVar += '<td>';
        if(data[key].approveStatus == 'A' || data[key].approveStatus == 'P'){
	        htmlVar += '<select tabindex="2" disabled style="width: 150px;"  class="chzn-select comboselect" id="resourceAllocIdrow'+key+'"   name="entries['+key+'].resourceAllocId">';
	        htmlVar += '<c:forEach var="resourceAllocation" items="${resourceAllocation}">';
	        if(validateDateFunction('${resourceAllocation.allocStartDate}', '${resourceAllocation.allocEndDate}')){
	        	
	        	if(data[key].resourceAllocId.id == '${resourceAllocation.id}' ){
					htmlVar += '<option value="${resourceAllocation.id}" selected="selected">${resourceAllocation.projectId.projectName}</option>';
	        	}else{
	        		
	        		
	        		htmlVar += '<option value="${resourceAllocation.id}">${resourceAllocation.projectId.projectName}</option>';
	        	}
	        }/* else{
	        	htmlVar += '<option value="'+data[key].resourceAllocId.id+'">'+data[key].resourceAllocId.projectId.projectName+'</option>';
	        } */
	        htmlVar += '</c:forEach>';
	        htmlVar += '</select>';
        }else{//alert("else alone 2");
			htmlVar += '<select tabindex="2" style="width: 150px;"  class="chzn-select comboselect" onchange=getActiveActivityByProjectId(value,'+key+');getActiveModulesByProjectId(value,'+key+');getTicketPriorityAndStatusByProjectIdOnProjectChange(value,'+key +'); id="resourceAllocIdrow'+key+'"   name="entries['+key+'].resourceAllocId">';
			/* var isValidate=false; */
			htmlVar += '<c:forEach var="resourceAllocation" items="${resourceAllocation}">';
			if(validateDateFunction('${resourceAllocation.allocStartDate}', '${resourceAllocation.allocEndDate}')){
				if(data[key].resourceAllocId.id == '${resourceAllocation.id}' || data[key].resourceAllocId.projectId.projectName=='${resourceAllocation.projectId.projectName}'){
					htmlVar += '<option value="${resourceAllocation.id}" selected="selected">${resourceAllocation.projectId.projectName}</option>';
	        	}else{
					htmlVar += '<option value="${resourceAllocation.id}">${resourceAllocation.projectId.projectName}</option>';
	        	}
	        }/* else{
	        	if(!isValidate){
	        	htmlVar += '<option value="'+data[key].resourceAllocId.id+'">'+data[key].resourceAllocId.projectId.projectName+'</option>';
	        	}
	        	isValidate = true;
	        } */
			htmlVar += '</c:forEach>';
	        htmlVar += '</select>';
        }
        htmlVar += '</td>';
        
        htmlVar += '<td valign="middle">';
        if(data[key].approveStatus == 'A' || data[key].approveStatus == 'P'){
        	htmlVar += '<select id="activityId'+key+'" disabled class="comboselect" name="entries['+key+'].activityId">';
        	htmlVar += '<c:forEach var="activity" items="${activities}">';
        	if(data[key].activityId.id == '${activity.id}'){
        		htmlVar += '<option value="${activity.id}" selected="selected">${activity.activityName}</option>';
        	}else{
        		htmlVar += '<option value="${activity.id}">${activity.activityName}</option>';
        	}
        	htmlVar += '</c:forEach>';
	        htmlVar += '</select>';
        }else{
        	htmlVar += '<select id="activityId'+key+'" class="comboselect" name="entries['+key+'].activityId">';
        	htmlVar += '<c:forEach var="activity" items="${activities}">';
        	if(data[key].activityId!=null){
	        	if(data[key].activityId.id == '${activity.id}'){
	        		htmlVar += '<option value="${activity.id}" selected="selected">${activity.activityName}</option>';
	        	}else{
	        		htmlVar += '<option value="${activity.id}">${activity.activityName}</option>';
	        	}
        	}else{
        		htmlVar += '<option value="${activity.id}">${activity.activityName}</option>';
        	}
        	htmlVar += '</c:forEach>';
	        htmlVar += '</select>';
        }
        htmlVar += '</td>';
			
        htmlVar += '<td valign="middle" align="center">';
        if(data[key].module!=null && data[key].module!=-1){//alert("if 4");
        	htmlVar += '<input type="text" id="module'+key+'" name="entries['+key+'].module"  value="'+data[key].module+'" onfocus="getActiveModulesByProjectId('+data[key].resourceAllocId.id+','+key+',value,1);"';
        }        
        else
        {//alert("else 4");
        	htmlVar += '<input type="text" id="module'+key+'" name="entries['+key+'].module"  value="" onfocus="getActiveModulesByProjectId('+data[key].resourceAllocId.id+','+key+',value,1);"';
        }
        if(data[key].approveStatus == 'A' || data[key].approveStatus == 'P'){
        	htmlVar += 'readonly';
        }
        htmlVar += '>';
        htmlVar += '</td>';

        ///////////Adding for subModule
        htmlVar += '<td valign="middle" align="center">';
        
        if(data[key].subModule!=null && data[key].subModule!=-1){
        	var mystr1 = "'"+data[key].resourceAllocId.id.toString().trim()+"'";
        
        	htmlVar += '<input type="text" id="subModule'+key+'" name="entries['+key+'].subModule"  value="'+data[key].subModule+'" onfocus="getActiveSubModulesByModuleId('+mystr1+','+key+');"';

        } else{
        	
        	var mystr2 = "'"+data[key].resourceAllocId.id.toString().trim()+"'";
        	htmlVar += '<input type="text" id="subModule'+key+'" name="entries['+key+'].subModule"  value="" onfocus="getActiveSubModulesByModuleId('+mystr2+','+key+');"';
        }
        
        if(data[key].approveStatus == 'A' || data[key].approveStatus == 'P'){
        	htmlVar += 'readonly';
        }
        htmlVar += '>';
        htmlVar += '</td>';
        ///////////////////////////////

		htmlVar += '<td valign="middle" align="center">';
        htmlVar += '<input type="text" onChange="getTicketPriorityAndStatus('+key+');" id="ticketNo'+key+'" name="entries['+key+'].ticketNo"  value="'+data[key].ticketNo+'"';
        if(data[key].approveStatus == 'A' || data[key].approveStatus == 'P'){
        	htmlVar += 'readonly';
        }
        htmlVar += '>';
		htmlVar += '</td>';

        
		htmlVar += getTicketPriorityAndStatusHTML(data,key);
		/* commented by mustafa 
        htmlVar += '<td width="5%" valign="middle" align="left" class="sunT">';
        htmlVar += '<div class="poRel">';
        if(getDateFunction(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')){
        	
        	
            	htmlVar += '<input type="text" class="small"   onblur="(this);checkHour(this); " id="d1Hoursrow'+key+'" name="entries['+key+'].d1Hours" maxlength="5" value=""';
           
        	
        	htmlVar += 'readonly';
        }
        else{ 
	        if(data[key].d1Hours!=null){
	        	htmlVar += '<input type="text" class="small"   onblur="(this);checkHour(this); " id="d1Hoursrow'+key+'" name="entries['+key+'].d1Hours" maxlength="5" value="'+data[key].d1Hours+'"';
	        }else{
	        	htmlVar += '<input type="text" class="small"   onblur="(this);checkHour(this); " id="d1Hoursrow'+key+'" name="entries['+key+'].d1Hours" maxlength="5" value=""';
	        }
        
        }
        htmlVar += '><img width="24" height="23" class="comments" src="resources/images/comments.png"/>';
        htmlVar += '<div class="comments_div">';
        htmlVar += '<textarea rows="" cols="" id="d1Commentrow'+key+'" class="comments_textarea" name="entries['+key+'].d1Comment"';
        if(isCommentDisabled(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')){
        	htmlVar += 'readonly';
        }
        htmlVar += '>'+data[key].d1Comment+'</textarea>';
        htmlVar += '<div class="clear"></div>';
        htmlVar += '<div class="btns">';
        htmlVar += '<input type="button" class="okBtn" value="Ok">';
        htmlVar += '</div>';
        htmlVar += '</div>';
        htmlVar += '</div></td>';
        
        htmlVar += '<td width="5%" valign="middle" align="left" class="monT" ><div class="poRel">';
        if(getDateFunction(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')){
        	htmlVar += '<input type="text" id="d2Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();"  onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d2Hours" class="small" value=""';
        	htmlVar += 'readonly';
        }
        else{
        	if(data[key].d2Hours!=null){
            	htmlVar += '<input type="text" id="d2Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();"  onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d2Hours" class="small" value="'+data[key].d2Hours+'"';
            }else{
            	htmlVar += '<input type="text" id="d2Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();"  onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d2Hours" class="small" value=""';
            }
        }
        htmlVar += '><img width="24" height="23" class="comments" src="resources/images/comments.png" />';
        htmlVar += '<div class="comments_div">';
        htmlVar += '<textarea rows="" cols="" id="d2Commentrow'+key+'" class="comments_textarea" name="entries['+key+'].d2Comment"';
        if(isCommentDisabled(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')){
        	htmlVar += 'readonly';
        }
        htmlVar += '>'+data[key].d2Comment+'</textarea>';
        htmlVar += '<div class="clear"></div>';
        htmlVar += '<div class="btns">';
        htmlVar += '<input type="button" class="okBtn" value="Ok">';
        htmlVar += '</div>';
        htmlVar += '</div>';
        htmlVar += '</div></td>';
        
        htmlVar += '<td width="5%" valign="middle" align="left" class="tueT"><div class="poRel">'
        if(getDateFunction(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')){
        	htmlVar += '<input type="text" id="d3Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();"  onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d3Hours" class="small" value=""';
        	htmlVar += 'readonly';
        }
        else{
        	if(data[key].d3Hours!=null){
            	htmlVar += '<input type="text" id="d3Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();"  onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d3Hours" class="small" value="'+data[key].d3Hours+'"';
            }else{
            	htmlVar += '<input type="text" id="d3Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();"  onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d3Hours" class="small" value=""';
            }
        }
        htmlVar += '><img width="24" height="23" class="comments" src="resources/images/comments.png" />';
        htmlVar += '<div class="comments_div">';
        htmlVar += '<textarea rows="" cols="" id="d3Commentrow'+key+'" class="comments_textarea" name="entries['+key+'].d3Comment"';
        if(isCommentDisabled(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')){
        	htmlVar += 'readonly';
        }
        htmlVar += '>'+data[key].d3Comment+'</textarea>';
        htmlVar += '<div class="clear"></div>';
        htmlVar += '<div class="btns">';
        htmlVar += '<input type="button" class="okBtn" value="Ok">';
        htmlVar += '</div>';
        htmlVar += '</div>';
        htmlVar += '</div></td>';
        
        htmlVar += '<td width="5%" valign="middle" align="left" class="wedT"><div class="poRel">';
        if(getDateFunction(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')){
        	htmlVar += '<input type="text" id="d4Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();" onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval(); " name="entries['+key+'].d4Hours" class="small" value=""';
        	htmlVar += 'readonly';
        }
        else{
        	  if(data[key].d4Hours!=null){
              	htmlVar += '<input type="text" id="d4Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();" onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval(); " name="entries['+key+'].d4Hours" class="small" value="'+data[key].d4Hours+'"';
              }else{
              	htmlVar += '<input type="text" id="d4Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();" onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval(); " name="entries['+key+'].d4Hours" class="small" value=""';
              }
        }
        htmlVar += '><img width="24" height="23" class="comments" src="resources/images/comments.png" />';
        htmlVar += '<div class="comments_div">';
        htmlVar += '<textarea rows="" cols="" id="d4Commentrow'+key+'" class="comments_textarea" name="entries['+key+'].d4Comment"';
        if(isCommentDisabled(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')){
        	htmlVar += 'readonly';
        }
        htmlVar += '>'+data[key].d4Comment+'</textarea>';
        htmlVar += '<div class="clear"></div>';
        htmlVar += '<div class="btns">';
        htmlVar += '<input type="button" class="okBtn" value="Ok">';
        htmlVar += '</div>';
        htmlVar += '</div>';
        htmlVar += '</div></td>';
        
        htmlVar += '<td width="5%" valign="middle" align="left" class="thuT"><div class="poRel">';
        if(getDateFunction(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')){
        	htmlVar += '<input type="text" id="d5Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();" onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d5Hours" class="small" value=""';
        	htmlVar += 'readonly';
        }
        else{
        	if(data[key].d5Hours!=null){
            	htmlVar += '<input type="text" id="d5Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();" onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d5Hours" class="small" value="'+data[key].d5Hours+'"';
            }else{
            	htmlVar += '<input type="text" id="d5Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();" onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d5Hours" class="small" value=""';
            }
        }
        htmlVar += '><img width="24" height="23" class="comments" src="resources/images/comments.png" />';
        htmlVar += '<div class="comments_div">';
        htmlVar += '<textarea rows="" cols="" id="d5Commentrow'+key+'" class="comments_textarea" name="entries['+key+'].d5Comment"';
        if(isCommentDisabled(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')){
        	htmlVar += 'readonly';
        }
        htmlVar += '>'+data[key].d5Comment+'</textarea>';
        htmlVar += '<div class="clear"></div>';
        htmlVar += '<div class="btns">';
        htmlVar += '<input type="button" class="okBtn" value="Ok">';
        htmlVar += '</div>';
        htmlVar += '</div>';
        htmlVar += '</div></td>';
        
        htmlVar += '<td width="5%" valign="middle" align="left" class="friT"><div class="poRel">';
       
        if(getDateFunction(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')){
        	htmlVar += '<input type="text" id="d6Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();"  onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d6Hours" class="small" value=""';
        	htmlVar += 'readonly';
        }else{
        	 if(data[key].d6Hours!=null){
             	htmlVar += '<input type="text" id="d6Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();"  onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d6Hours" class="small" value="'+data[key].d6Hours+'"';
             }else{
             	htmlVar += '<input type="text" id="d6Hoursrow'+key+'" maxlength="5" onkeyup="checkSendForApproval();"  onfocus="checkSendForApproval();" onblur="validateHour(this);checkHour(this);checkSendForApproval();" name="entries['+key+'].d6Hours" class="small" value=""';
             }
        }
        htmlVar += '><img width="24" height="23" class="comments" src="resources/images/comments.png" />';
        htmlVar += '<div class="comments_div">';
        htmlVar += '<textarea rows="" cols="" id="d6Commentrow'+key+'" class="comments_textarea" name="entries['+key+'].d6Comment"';
        if(isCommentDisabled(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')){
        	htmlVar += 'readonly';
        }
        htmlVar += '>'+data[key].d6Comment+'</textarea>';
        htmlVar += '<div class="clear"></div>';
        htmlVar += '<div class="btns">';
        htmlVar += '<input type="button" class="okBtn" value="Ok">';
        htmlVar += '</div>';
        htmlVar += '</div>';
        htmlVar += '</div></td>';
        
        htmlVar += '<td width="5%" valign="middle" align="left" class="satT"><div class="poRel">';
        
        if(getDateFunction(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')){
        	htmlVar += '<input type="text" id="d7Hoursrow'+key+'" maxlength="5" onblur="validateHour(this);checkHour(this);" name="entries['+key+'].d7Hours" class="small"  value=""';
        	htmlVar += 'readonly';
        }else{
        	if(data[key].d7Hours!=null){
            	htmlVar += '<input type="text" id="d7Hoursrow'+key+'" maxlength="5" onblur="validateHour(this);checkHour(this);" name="entries['+key+'].d7Hours" class="small"  value="'+data[key].d7Hours+'"';
            }else{
            	htmlVar += '<input type="text" id="d7Hoursrow'+key+'" maxlength="5" onblur="validateHour(this);checkHour(this);" name="entries['+key+'].d7Hours" class="small"  value=""';
            }
        }
        htmlVar += '><img width="24" height="23" class="comments" src="resources/images/comments.png" />';
        htmlVar += '<div class="comments_div">';
        htmlVar += '<textarea rows="" cols="" id="d7Commentrow'+key+'" class="comments_textarea" name="entries['+key+'].d7Comment"';
        if(isCommentDisabled(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')){
        	htmlVar += 'readonly';
        }
        htmlVar += '>'+data[key].d7Comment+'</textarea>';
        htmlVar += '<div class="clear"></div>';
        htmlVar += '<div class="btns">';
        htmlVar += '<input type="button" class="okBtn" value="Ok">';
        htmlVar += '</div>';
        htmlVar += '</div>';
        htmlVar += '</div></td>';
        
        htmlVar += '<td valign="middle" align="center">';
        htmlVar += '<input type="hidden" id="approveField'+key+'" value="'+data[key].approveStatus+'" name="entries['+key+'].approveStatus" />';
        if(data[key].approveStatus == 'N' || data[key].approveStatus == 'R'){
        	htmlVar += '<img width="16" height="16" src="resources/images/remove.png" class="remove" />';
        }else{
        	htmlVar += '<img width="16" height="16" src="resources/images/remove.png" class="removeInactive" />';
        }
        htmlVar += '</td>';
        
        htmlVar += '<td valign="middle" align="center">';
        if(data[key].approveStatus == 'N' || data[key].approveStatus == 'R'){
        	htmlVar += '<a href="javascript:void(0);" class="copy" >Copy</a>';
        }else{
        	htmlVar += '<a href="#" class="copyInactive">Copy</a>';
        }
        htmlVar += '</td>';
        
		htmlVar += '</tr>'; */
		

		htmlVar += '<input type="hidden" id="approveField'+key+'" value="'+data[key].approveStatus+'" name="entries['+key+'].approveStatus" />';

		htmlVar+="<td valign='middle' align='center' class='action'><i class='fa fa-chevron-circle-down' onclick='toggleChildRow(this)' title='expand'></i>";

        if(data[key].approveStatus == 'N' || data[key].approveStatus == 'R'){
			htmlVar += "<i class='copy fa fa-copy' id='copyHtml' title='copy record'></i>";
        }else{
        	htmlVar += '<i class="fa fa-copy copyInactive" title="copy record"></i>';
		}

		if(data[key].approveStatus == 'N' || data[key].approveStatus == 'R'){
			htmlVar +="<i class='fa fa-trash remove' title='delete record'></i>";
        	// htmlVar += '<img width="16" height="16" src="resources/images/remove.png" class="remove" />';
        }else{
			htmlVar +="<i class='fa fa-trash removeInactive' title='delete record'></i>";
        	// htmlVar += '<img width="16" height="16" src="resources/images/remove.png" class="removeInactive" />';
		}
		htmlVar+='</td>';
			//  <i class='copy fa fa-copy'></i><i class='fa fa-trash remove'></i></td>";

		htmlVar+="</td>"; 
		htmlVar+="</tr>";
		var logDisabled = '';
		var commentDisabled = '';
		if(getDateFunction(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')){
        	logDisabled = 'readonly';
		}
		
		if(isCommentDisabled(data[key].resourceAllocId.allocStartDate,data[key].resourceAllocId.allocEndDate,data[key].approveStatus,'${today}')){
        	commentDisabled = 'readonly';
        }

		htmlVar+="<tr id='row_timesheet_"+(key)+"' class='expandable_row'><td colspan="+9+">";
		htmlVar+='<table cellpadding="0" cellspacing="0" border="0" style="width: 100%;">'+
		'<tr><td>Day</td><td align="center">Sunday</td><td align="center">Monday</td><td align="center">Tuesday</td><td align="center">Wednesday</td><td align="center">Thursday</td><td align="center">Friday</td><td align="center">Saturday</td><td  align="center">Log Time</td></tr>'+
		'<tr><td>Date</td>'+createTableRowNew(WeeklyDateStart)+'</tr>' +
		'<tr class="logHrs"><td>Log Time (in hours)</td>'+createTimeLog(key, logValues, logDisabled)+'</tr>' +	 
		'<tr><td>Comments</td>'+createLogComments(key,commentValues, commentDisabled)+'</tr>' +	 	 
		'</table>';
		htmlVar+="</td></tr>"; 
    });
	 
	$("#tblActivity > tbody").append(htmlVar);
}


function validateDateFunction(startDt,endDt){
    
    var allocStartDt = new Date.parse(startDt);
    var weekEndDt = new Date(weekEnd);
    var weekStartDt = new Date(weekStart);
    var allocEndDt = 0;
    if(endDt!=null && endDt !='') { 
        allocEndDt = new Date.parse(endDt);        
    }
    if(weekEndDt >= allocStartDt && allocEndDt == 0) {
        return true;
    }else if(weekEndDt >= allocStartDt && weekStartDt <= allocEndDt) {
        return true;
    }
    return false;
   
}

function validateDateFunctionForNew (startDt,endDt){
	
	var allocStartDt = new Date.parse(startDt);
       var allocEndDt = 0;
       if(endDt!=null && endDt !='') { 
           allocEndDt = new Date.parse(endDt);    
       }
       var weekEndDt = new Date(weekEnd);
       var weekStartDt = new Date(weekStart);
       var check = allocEndDt - weekEndDt;
       if(weekEndDt >= allocStartDt && allocEndDt == 0) {
           return true;
       }
       
       else if(allocStartDt <= weekEndDt && weekStartDt <= allocEndDt) {
           
           return true;
       } 
       return false;
}

function getDateFunction(startdate,udate,status,today){
    var allocEndDate = new Date(udate).getTime();  
    var allocStartDate=new Date(startdate).getTime();
    var dateArray = dateString.split(',');
    if(count<7)
       count++;
    else
       count=1;
    var currentColumnDate=Date.parse(dateArray[count-1]).getTime();
   // //alert(currentColumnDate);
   timeSheetStatus = status;
    if(status=='A' || status == 'P')
        return true;
    else {
    	if(allocStartDate>currentColumnDate)
    		{
    		return true;
    		}
    	
    	/* else{
    		if((allocEndDate != null && allocEndDate != 0 && allocEndDate<currentColumnDate) || currentColumnDate >= today){
        return true;
        
    		}
    		else
                return false;
    	} */
    }
    if(status =='R' || status =='N'){
    	//checkSendForApproval();
    }
    
}

function isCommentDisabled(startdate,udate,status,today){
    var allocEndDate = new Date(udate).getTime();
    var allocStartDate=new Date(startdate).getTime();
    var dateArray = dateString.split(',');
    if(countForComment<7)
        countForComment++;
    else
        countForComment=1;
    var currentColumnDate=Date.parse(dateArray[count-1]).getTime();
     
    if(status=='A' || status =='P')
        {
        return true;}
    else {
    	////alert("allocStartDate=="+allocStartDate);
    	if(allocStartDate>currentColumnDate)
		{
////alert("today date" +today);
		return true;
		}
    	/*else{
    	 if((allocEndDate != null && allocEndDate != 0 && allocEndDate<currentColumnDate) || currentColumnDate >= today){
        return true;
    }
    else
        return false;} */
    }
}

/*for changing comment icon*/
function changeCommentIcon(){
	var trLengthCheck = $("table#tblActivity tbody").find("tr").length;
   if(trLengthCheck>0){
           $("table#tblActivity tbody tr td").find(".poRel").find(".comments_div").find(".comments_textarea").each(function(){
           var commentVal = $.trim($(this).val());
               // To remove the space and save the trimmed value
               if(null == commentVal || commentVal==''){
                   $(this).closest("div.poRel").find(".comments").attr("src","resources/images/comments.png");    
                   }else {
                       $(this).closest("div.poRel").find(".comments").attr("src","resources/images/hascomments.png");
                    }   
           });
   }
    
}

function getRejectionRemarks(remarks) {
	$("body").append("<div class='fancybox-overlay fancybox-overlay-fixed' style='display:block;z-index: 1000 !important;'></div>");
	 $( "#comment" ).dialog({
	 close: function(event, ui) {
	        
			 	$('body').find('.fancybox-overlay').remove(); 
			    
	        
	    }	
	}); 
	 $("#comment").show();

	////alert("remarks: "+remarks);
	if(null != remarks && !remarks==''){////alert("in if");
		document.getElementById('rejectionRemarks').innerHTML = remarks;
	}
	else {////alert("in else");
		document.getElementById('rejectionRemarks').innerHTML = "No remarks";
	}
	
	$('#cancel').on("click", function(){
		$("#rejectionRemarks").val(remarks);
		$('#dialog').dialog('close');
		$.fancybox.close();
		 $("#comment").hide();
	});
}
</script>

<!-- Scripts for calculate hours -->
<script>
var checkHoursFlagArray = [];
var copyFlag = false;
function validateHour(attr){
	if(copyFlag == true){
		
		return;
	}
	
	var rowcount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
	var oneDot=/(^\d+$)|(^\d+\.\d+$)|(^\s*$)/;
	if (attr.value) {
		attr.value = attr.value.replace(/\s/g, '');
	}
	
	if(!oneDot.test(attr.value)){
		getFocusOnInvalidHours(attr);
		onDelete(rowcount);
	}else if(isDouble(attr.value)){
		attr.style.border = "";
		checkHoursFlagArray.length=0;
	 }else{
		getFocusOnInvalidHours(attr);
		onDelete(rowcount);
	}
	/* if(attr.value==0)
	{
	getFocusOnInvalidZeroHours(attr);
	} */

	// calculate activity total hour
	var selectedRow = $(attr).parent().parent();
	var totalHrs = 0;	
	var logTime = $(selectedRow).find('[data-activity_total]');
	$(selectedRow[0]).find('.input_log:not([data-activity_total])').each(function(i, elem) {
		totalHrs+= Number($(elem).val()) || 0;
	});
	$(logTime).val(totalHrs);
	$('.comments_textarea').removeClass('active');
	// calculate activity total hour

    	
}

function isDouble(val){
    if(val>23.59)return false;
    else{
        var dotPos = val.indexOf('.');
        if(dotPos!=-1){
          var minute = val.slice(dotPos,val.length);
          if(minute>=.6)return false;
          if(minute.length>3)return false;
        }
    }
    return true;
}

function checkHour(attr){
	if(copyFlag==true){
		return;
	}
	var rowcount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
	var sunTotalHours=0,monTotalHours=0,tueTotalHours=0,wedTotalHours=0,thuTotalHours=0,friTotalHours=0,satTotalHours=0;
	
	/* commented by mustafa due to no use of below code
	
	for(z=1;z<=rowcount;z++){
		if(document.getElementById('d1Hoursrow'+(z-1))!=null){
			if(document.getElementById('d1Hoursrow'+(z-1)).value!=""){
				sunTotalHours = sunTotalHours+ parseFloat(document.getElementById('d1Hoursrow'+(z-1)).value);
			}else{
				sunTotalHours=sunTotalHours;
			}
		}
		/* if(sunTotalHours >24){
			document.getElementById('sunTtl').value="";
			//getFocusOnInvalidHours(attr);
			getFocusOnInvalidHours(sunTotalHours);
		} *
		/
					 
		if(document.getElementById('d2Hoursrow'+(z-1))!=null){
			if(document.getElementById('d2Hoursrow'+(z-1)).value!=""){
				monTotalHours = monTotalHours+ parseFloat(document.getElementById('d2Hoursrow'+(z-1)).value);
			}else{
				monTotalHours = monTotalHours;
			}
		}
					 
		/* if(monTotalHours >24){
			document.getElementById('monTtl').value="";
			getFocusOnInvalidHours(monTotalHours); //Added for task # 464/465/466/468 by Neha
		} *
		/
					 
		if(document.getElementById('d3Hoursrow'+(z-1))!=null){
			if( document.getElementById('d3Hoursrow'+(z-1)).value!=""){
				tueTotalHours = tueTotalHours+ parseFloat(document.getElementById('d3Hoursrow'+(z-1)).value);
			}else{
				tueTotalHours = tueTotalHours;
			}
		}
					 
		/* if(tueTotalHours > 24){
			document.getElementById('tueTtl').value="";
			getFocusOnInvalidHours(tueTotalHours); //Added for task # 464/465/466/468 by Neha
		} *
		/
		
		if(document.getElementById('d4Hoursrow'+(z-1))!=null){
			if(document.getElementById('d4Hoursrow'+(z-1)).value){
				wedTotalHours = wedTotalHours+ parseFloat(document.getElementById('d4Hoursrow'+(z-1)).value);
			}
			else
				wedTotalHours = wedTotalHours;
		}
					 
		/* if(wedTotalHours > 24){
			document.getElementById('wedTtl').value="";
			getFocusOnInvalidHours(wedTotalHours);//Added for task # 464/465/466/468 by Neha
		} *
		/
		
		if(document.getElementById('d5Hoursrow'+(z-1))!=null){
			if(document.getElementById('d5Hoursrow'+(z-1)).value!=""){
				thuTotalHours = thuTotalHours+ parseFloat(document.getElementById('d5Hoursrow'+(z-1)).value);
			}
			else{
				thuTotalHours = thuTotalHours;
			}
		}
					 
		/* if(thuTotalHours > 24){
			document.getElementById('thuTtl').value="";
			getFocusOnInvalidHours(thuTotalHours); //Added for task # 464/465/466/468 by Neha
		} *
		/
		
		if(document.getElementById('d6Hoursrow'+(z-1))!=null){
			if(document.getElementById('d6Hoursrow'+(z-1)).value!=""){
				friTotalHours = friTotalHours+ parseFloat(document.getElementById('d6Hoursrow'+(z-1)).value);
			}
			else
			{
				friTotalHours = friTotalHours;
			}
		}
					 
		/* if(friTotalHours > 24){
			document.getElementById('friTtl').value=" ";
			getFocusOnInvalidHours(friTotalHours); //Added for task # 464/465/466/468 by Neha
						 
		} *
		/
		if(document.getElementById('d7Hoursrow'+(z-1))!=null){
			if(document.getElementById('d7Hoursrow'+(z-1)).value!="")
			{
				satTotalHours = satTotalHours+ parseFloat(document.getElementById('d7Hoursrow'+(z-1)).value);
			}
			else
			{
				satTotalHours = satTotalHours;
			}
		}
					 
		/* if(satTotalHours > 24){
			document.getElementById('satTtl').value="";
			getFocusOnInvalidHours(satTotalHours); //Added for task # 464/465/466/468 by Neha
		} *
		/
	}
	commented by mustafa due to no use of above code*/
	onDelete(rowcount);
}

function onDelete(rowcount){
	var sunTotalHours=0;
	var monTotalHours= '';
	var tueTotalHours= '';
	var wedTotalHours= '';
	var thuTotalHours= '';
	var friTotalHours= '';
	var satTotalHours=0;
		  
	/* commented by mustafa to rewrite logic 
	$('#tblActivity tbody tr.logHrs').each(function() {
		if($(this).find('td:eq(8)').find('input[type=text]').val()!=null && $(this).find('td:eq(0)').find('input[type=text]').val()!=""){
			sunTotalHours = sunTotalHours+ parseFloat($(this).find('td:eq(8)').find('input[type=text]').val());
		}else{
			sunTotalHours=sunTotalHours;
		}
		
		if($(this).find('td:eq(9)').find('input[type=text]').val()!=null && $(this).find('td:eq(9)').find('input[type=text]').val()!=""){
			if(monTotalHours== ""){
				monTotalHours=0;
			}
			monTotalHours = monTotalHours+ parseFloat($(this).find('td:eq(9)').find('input[type=text]').val());
		}else{
			monTotalHours = monTotalHours;
		}
		
		if($(this).find('td:eq(10)').find('input[type=text]').val()!=null && $(this).find('td:eq(10)').find('input[type=text]').val()!=""){
			if(tueTotalHours== ""){
				tueTotalHours=0;
			}
			tueTotalHours = tueTotalHours+ parseFloat($(this).find('td:eq(10)').find('input[type=text]').val());
		}else{
			tueTotalHours = tueTotalHours;
		}
		
		if($(this).find('td:eq(11)').find('input[type=text]').val()!=null && $(this).find('td:eq(11)').find('input[type=text]').val()!=""){
			if(wedTotalHours== ""){
				wedTotalHours=0;
			}
			wedTotalHours = wedTotalHours+ parseFloat($(this).find('td:eq(11)').find('input[type=text]').val());
		}else{
			wedTotalHours = wedTotalHours;
		}
		
		if($(this).find('td:eq(12)').find('input[type=text]').val()!=null && $(this).find('td:eq(12)').find('input[type=text]').val()!=""){
			if(thuTotalHours== ""){
				thuTotalHours=0;
			}
			thuTotalHours = thuTotalHours+ parseFloat($(this).find('td:eq(12)').find('input[type=text]').val());
		}else{
			thuTotalHours = thuTotalHours;
		}
		
		if($(this).find('td:eq(13)').find('input[type=text]').val()!=null && $(this).find('td:eq(13)').find('input[type=text]').val()!=""){
			if(friTotalHours== ""){
				friTotalHours=0;
			}
			friTotalHours = friTotalHours+ parseFloat($(this).find('td:eq(13)').find('input[type=text]').val());
		}else{
			friTotalHours = friTotalHours;
		}
		
		if($(this).find('td:eq(14)').find('input[type=text]').val()!=null && $(this).find('td:eq(14)').find('input[type=text]').val()!=""){
			satTotalHours = satTotalHours+ parseFloat($(this).find('td:eq(14)').find('input[type=text]').val());
		}else{
			satTotalHours = satTotalHours;
		}
	});
	commented by mustafa */

	$('#tblActivity tbody .logHrs td').each(function(i,elem) {
		var day = elem.className;
		var val = $(elem).find('input').val();
		if (!val || val < 0 ) {
			return;
		}

		switch(day) {
			case 'sunT':
				sunTotalHours = Number(sunTotalHours) + Number(val);
				break;
			case 'monT':
				monTotalHours = Number(monTotalHours) + Number(val);
				break;
			case 'tueT':
				tueTotalHours = Number(tueTotalHours) + Number(val);
				break;
			case 'wedT':
				wedTotalHours = Number(wedTotalHours) + Number(val);
				break;
			case 'thuT':
				thuTotalHours = Number(thuTotalHours) + Number(val);
				break;
			case 'friT':
				friTotalHours = Number(friTotalHours) + Number(val);
				break;
			case 'satT':
				satTotalHours = Number(satTotalHours) + Number(val);
				break;
			default:
				break;
		}
	});
	

	document.getElementById('sunTtl').value=sunTotalHours;
	document.getElementById('monTtl').value=monTotalHours;
	document.getElementById('tueTtl').value=tueTotalHours;
	document.getElementById('wedTtl').value=wedTotalHours;
	document.getElementById('thuTtl').value=thuTotalHours;
	document.getElementById('friTtl').value=friTotalHours;
	document.getElementById('satTtl').value=satTotalHours;
	
	/* commented by mustafa due to rewrite the below logic
	if(sunTotalHours!=null && sunTotalHours!=""){
		sunTotalHours=parseFloat(sunTotalHours);
	}else{
		sunTotalHours = 0;
	}
	
	if(monTotalHours!=null && monTotalHours!=""){
		monTotalHours=parseFloat(monTotalHours);
	}else{
		monTotalHours = 0;
	}
	
	if(tueTotalHours!=null && tueTotalHours!=""){
		tueTotalHours=parseFloat(tueTotalHours);
	}else{
		tueTotalHours = 0;
	}
	
	if(wedTotalHours!=null && wedTotalHours!=""){
		wedTotalHours=parseFloat(wedTotalHours);
	}else{
		wedTotalHours = 0;
	}
	
	if(thuTotalHours!=null && thuTotalHours!=""){
		thuTotalHours=parseFloat(thuTotalHours);
	}else{
		thuTotalHours = 0;
	}
	
	if(friTotalHours!=null && friTotalHours!=""){
		friTotalHours=parseFloat(friTotalHours);
	}else{
		friTotalHours = 0;
	}
	
	if(satTotalHours!=null && satTotalHours!=""){
		satTotalHours=parseFloat(satTotalHours);
	}else{
		satTotalHours = 0;
	}
	commented by mustafa	*/
	document.getElementById('weekHrs' ).value=Number(sunTotalHours)+Number(monTotalHours)+Number(tueTotalHours)+Number(wedTotalHours)+Number(thuTotalHours)+Number(friTotalHours)+Number(satTotalHours);
}

function getFocusOnInvalidHours(attr){

		var decimalRegx=/^.[0-9]/;//Added for task # 464/465/466/468 by Neha
		//var decimalRegx=/^[0-9]+([\,\.][0-9]+)?$/g;
		// Added for task # 217 -start
		var regxAlphabet=/^[A-Za-z]/;

		 if(regxAlphabet.test(attr)) //Added for task # 464/465/466/468 by Neha
			{
		
			 validateHours(attr);
			} 
		// Added for task # 217 -end
		
		 //Added for task # 466 - start
		if(decimalRegx.test(attr)){
			////alert("attr : "+attr);
		}
		////alert("attr----"+attr.value);
	   if(attr>24){
		   ////alert("attr : "+attr);
			checkHours = checkHours+1;
			/* var errorMsg = "Please Enter Less Than 24 Hours In a Day";
			showError(errorMsg); */
		 	$('.copy').bind('click', false);
		    copyFlag=false;
		}else{ 
			// //alert("attr--n --"+attr.value);
//		var errorMsg = "Please Enter Hours Properly ";//(when user enters value like - 4sg)
//	    showError(errorMsg);
//	     attr.value="";  
	    $('.copy').bind('click', true);
	    copyFlag=false;
	   // checkHoursFlagArray.push(attr);
	    //Added for task # 466 - end
	   
	    //attr.style.border = "1px red solid";
	   // attr.value="";
	    //setTimeout(function(){attr.focus();}, 10);
	    
		} 
	}
	
Date.prototype.addHours = function(h) {    
	   this.setTime(this.getTime() + (h*60*60*1000)); 
	   return this;   
}
	
function checkSendForApproval() {
	var isMondayFail=false,	isTuesdayFail=false,isWednesdayFail=false,isThursdayFail=false,isFridayFail=false;
	////alert("timeSheetStatus: "+timeSheetStatus);
	
	for(var i = 0; i<isAllTimesheetSubmittedOrApproved.length; i++){
		if(isAllTimesheetSubmittedOrApproved[i]=='N' || isAllTimesheetSubmittedOrApproved[i]=='R'){
			
			isButtonDisabled=false; 
			break;
		}else{
			isButtonDisabled=true;
		}
	}
	isAllTimesheetSubmittedOrApproved = [];
	/* if(timeSheetStatus !='undefined' && timeSheetStatus != 'A' && timeSheetStatus != 'P' ){ */
	   if(!isButtonDisabled){
			weekStartingDate = $("#weekStartDate").val();
			weekEndingDate = $("#weekEndDate").val();
			if(isJoiningWeek(weekStartingDate, weekEndingDate, doj)){
			
				var now = new Date(doj); 
				var day = new Date(now.getUTCFullYear(), now.getUTCMonth(), now.getUTCDate(),  now.getUTCHours()+5, now.getUTCMinutes()+30, now.getUTCSeconds()).getDay();
 
				 if(day == 1){
					 var rowcount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
					 for(var z=0;z<=rowcount-1;z++){
							document.getElementById('d1Hoursrow'+z).disabled=true;

							document.getElementById('d1Commentrow'+z).disabled=true;
						}
							
					 document.getElementById("sunTtl").disabled=true;
					 
					 if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
						 $("#buttons").find('input[type="button"]').button({disabled:false});
					     $("#saveValues").removeAttr( "aria-disabled" );
					     $("#saveValues").attr("disabled",false);
					     $("#submit").attr("disabled",true);
						 $("#submit").addClass(" copyInactive");
					 }else if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
						 $("#buttons").find('input[type="button"]').button({disabled:false});
					     $("#saveValues").removeAttr( "aria-disabled" );
					     $("#saveValues").attr("disabled",false);
					     $("#submit").attr("disabled",true);
						 $("#submit").addClass(" copyInactive");
					 }else if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
						 $("#buttons").find('input[type="button"]').button({disabled:false});
					     $("#saveValues").removeAttr( "aria-disabled" );
					     $("#saveValues").attr("disabled",false);
					     $("#submit").attr("disabled",true);
						 $("#submit").addClass(" copyInactive");
					 }else if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
						 $("#buttons").find('input[type="button"]').button({disabled:false});
					     $("#saveValues").removeAttr( "aria-disabled" );
					     $("#saveValues").attr("disabled",false);
					     $("#submit").attr("disabled",true);
						 $("#submit").addClass(" copyInactive");
					 }else if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
						 $("#buttons").find('input[type="button"]').button({disabled:false});
					     $("#saveValues").removeAttr( "aria-disabled" );
					     $("#saveValues").attr("disabled",false);
					     $("#submit").attr("disabled",true);
						 $("#submit").addClass(" copyInactive");
					 }
					 
					//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
					if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value == ""){
				 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
							if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
								if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
								 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
								 		$("#buttons").find('input[type="button"]').button({disabled:true});
								 	}
								}
							}
				 		}
					}
					
					if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
				 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
							if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
								if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
								 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
								 		$("#buttons").find('input[type="button"]').button({disabled:false});
								 		$("#saveValues").removeAttr( "aria-disabled" );
									    $("#saveValues").attr("disabled",false);
									    $("#submit").attr("disabled",false);
										$("#submit").removeClass(" copyInactive");
								 	}
								}
							}
				 		}
					}
						//Added End By Nitin
				}
				
				/* For Tuesday */
				 if(day == 2){
					 var rowcount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
					 for(var z=0;z<=rowcount-1;z++){
							document.getElementById('d1Hoursrow'+z).disabled=true;
							document.getElementById('d2Hoursrow'+z).disabled=true;
							document.getElementById('d1Hoursrow'+z).value='';
							document.getElementById('d2Hoursrow'+z).value='';


							document.getElementById('d1Commentrow'+z).disabled=true;
							document.getElementById('d2Commentrow'+z).disabled=true;
							document.getElementById('d1Commentrow'+z).value='';
							document.getElementById('d2Commentrow'+z).value='';
						}
							
					 document.getElementById("sunTtl").disabled=true;
					 document.getElementById("monTtl").disabled=true;
					 document.getElementById("sunTtl").value='';
					 document.getElementById("monTtl").value='';
					 
					 if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
						 $("#buttons").find('input[type="button"]').button({disabled:false});
					     $("#saveValues").removeAttr( "aria-disabled" );
					     $("#saveValues").attr("disabled",false);
					     $("#submit").attr("disabled",true);
						 $("#submit").addClass(" copyInactive");
					 }else if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
						 $("#buttons").find('input[type="button"]').button({disabled:false});
					     $("#saveValues").removeAttr( "aria-disabled" );
					     $("#saveValues").attr("disabled",false);
					     $("#submit").attr("disabled",true);
						 $("#submit").addClass(" copyInactive");
					 }else if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
						 $("#buttons").find('input[type="button"]').button({disabled:false});
					     $("#saveValues").removeAttr( "aria-disabled" );
					     $("#saveValues").attr("disabled",false);
					     $("#submit").attr("disabled",true);
						 $("#submit").addClass(" copyInactive");
					 }else if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
						 $("#buttons").find('input[type="button"]').button({disabled:false});
					     $("#saveValues").removeAttr( "aria-disabled" );
					     $("#saveValues").attr("disabled",false);
					     $("#submit").attr("disabled",true);
						 $("#submit").addClass(" copyInactive");
					 }
					 
					//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
			 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
						if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
							if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
							 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
							 		$("#buttons").find('input[type="button"]').button({disabled:true});
							 	}
							}
						}
			 		}
					
			 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
						if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
							if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
							 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
							 		$("#buttons").find('input[type="button"]').button({disabled:false});
							 		$("#saveValues").removeAttr( "aria-disabled" );
								    $("#saveValues").attr("disabled",false);
								    $("#submit").attr("disabled",false);
									$("#submit").removeClass(" copyInactive");
							 	}
							}
						}
			 		}
					//Added End By Nitin
				}
				/* --- Wednesday --- */
				 if(day == 3){
					 var rowcount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
					 for(var z=0;z<=rowcount-1;z++){
							document.getElementById('d1Hoursrow'+z).disabled=true;
							document.getElementById('d2Hoursrow'+z).disabled=true;
							document.getElementById('d3Hoursrow'+z).disabled=true;
							document.getElementById('d1Hoursrow'+z).value='';
							document.getElementById('d2Hoursrow'+z).value='';
							document.getElementById('d3Hoursrow'+z).value='';

							document.getElementById('d1Commentrow'+z).disabled=true;
							document.getElementById('d2Commentrow'+z).disabled=true;
							document.getElementById('d3Commentrow'+z).disabled=true;
							document.getElementById('d1Commentrow'+z).value='';
							document.getElementById('d2Commentrow'+z).value='';
							document.getElementById('d3Commentrow'+z).value='';
						}
							
					 document.getElementById("sunTtl").disabled=true;
					 document.getElementById("monTtl").disabled=true;
					 document.getElementById("tueTtl").disabled=true;
					 document.getElementById("sunTtl").value='';
					 document.getElementById("monTtl").value='';
					 document.getElementById("tueTtl").value='';
					 
					 if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
						 $("#buttons").find('input[type="button"]').button({disabled:false});
					     $("#saveValues").removeAttr( "aria-disabled" );
					     $("#saveValues").attr("disabled",false);
					     $("#submit").attr("disabled",true);
						 $("#submit").addClass(" copyInactive");
					 }else if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
						 $("#buttons").find('input[type="button"]').button({disabled:false});
					     $("#saveValues").removeAttr( "aria-disabled" );
					     $("#saveValues").attr("disabled",false);
					     $("#submit").attr("disabled",true);
						 $("#submit").addClass(" copyInactive");
					 }else if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
						 $("#buttons").find('input[type="button"]').button({disabled:false});
					     $("#saveValues").removeAttr( "aria-disabled" );
					     $("#saveValues").attr("disabled",false);
					     $("#submit").attr("disabled",true);
						 $("#submit").addClass(" copyInactive");
					 }
					 
					//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
					if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
						if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
						 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
						 		$("#buttons").find('input[type="button"]').button({disabled:true});
						 	}
						}
					}
					
					if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
						if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
						 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
						 		$("#buttons").find('input[type="button"]').button({disabled:false});
						 		$("#saveValues").removeAttr( "aria-disabled" );
							    $("#saveValues").attr("disabled",false);
							    $("#submit").attr("disabled",false);
								$("#submit").removeClass(" copyInactive");
						 	}
						}
					}
					//Added End By Nitin
				}
						/*---For thursday-----*/
				 if(day == 4){
					 var rowcount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
					 for(var z=0;z<=rowcount-1;z++){
							document.getElementById('d1Hoursrow'+z).disabled=true;
							document.getElementById('d2Hoursrow'+z).disabled=true;
							document.getElementById('d3Hoursrow'+z).disabled=true;
							document.getElementById('d4Hoursrow'+z).disabled=true;
							
							document.getElementById('d1Hoursrow'+z).value='';
							document.getElementById('d2Hoursrow'+z).value='';
							document.getElementById('d3Hoursrow'+z).value='';
							document.getElementById('d4Hoursrow'+z).value='';

							document.getElementById('d1Commentrow'+z).disabled=true;
							document.getElementById('d2Commentrow'+z).disabled=true;
							document.getElementById('d3Commentrow'+z).disabled=true;
							document.getElementById('d4Commentrow'+z).disabled=true;

							
							document.getElementById('d1Commentrow'+z).value='';
							document.getElementById('d2Commentrow'+z).value='';
							document.getElementById('d3Commentrow'+z).value='';
							document.getElementById('d4Commentrow'+z).value='';
						}
							
					 document.getElementById("sunTtl").disabled=true;
					 document.getElementById("monTtl").disabled=true;
					 document.getElementById("tueTtl").disabled=true;
					 document.getElementById("wedTtl").disabled=true;
					 
					 document.getElementById("sunTtl").value='';
					 document.getElementById("monTtl").value='';
					 document.getElementById("tueTtl").value='';
					 document.getElementById("wedTtl").value='';
					 
					 if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
						 $("#buttons").find('input[type="button"]').button({disabled:false});
					     $("#saveValues").removeAttr( "aria-disabled" );
					     $("#saveValues").attr("disabled",false);
					     $("#submit").attr("disabled",true);
						 $("#submit").addClass(" copyInactive");
					 }else if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
						 $("#buttons").find('input[type="button"]').button({disabled:false});
					     $("#saveValues").removeAttr( "aria-disabled" );
					     $("#saveValues").attr("disabled",false);
					     $("#submit").attr("disabled",true);
						 $("#submit").addClass(" copyInactive");
					 }
					 
					//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
					if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
					 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
					 		$("#buttons").find('input[type="button"]').button({disabled:true});
					 	}
					}
					
					if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
					 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
					 		$("#buttons").find('input[type="button"]').button({disabled:false});
					 		$("#saveValues").removeAttr( "aria-disabled" );
						    $("#saveValues").attr("disabled",false);
						    $("#submit").attr("disabled",false);
							$("#submit").removeClass(" copyInactive");
					 	}
					}
					//Added End By Nitin
				}
					/* --- For Friday --- */
				 if(day == 5){
					 var rowcount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
					 for(var z=0;z<=rowcount-1;z++){
							document.getElementById('d1Hoursrow'+z).disabled=true;
							document.getElementById('d2Hoursrow'+z).disabled=true;
							document.getElementById('d3Hoursrow'+z).disabled=true;
							document.getElementById('d4Hoursrow'+z).disabled=true;
							document.getElementById('d5Hoursrow'+z).disabled=true;

							document.getElementById('d1Hoursrow'+z).value='';
							document.getElementById('d2Hoursrow'+z).value='';
							document.getElementById('d3Hoursrow'+z).value='';
							document.getElementById('d4Hoursrow'+z).value='';
							document.getElementById('d5Hoursrow'+z).value='';

							document.getElementById('d1Commentrow'+z).disabled=true;
							document.getElementById('d2Commentrow'+z).disabled=true;
							document.getElementById('d3Commentrow'+z).disabled=true;
							document.getElementById('d4Commentrow'+z).disabled=true;
							document.getElementById('d5Commentrow'+z).disabled=true;

							document.getElementById('d1Commentrow'+z).value='';
							document.getElementById('d2Commentrow'+z).value='';
							document.getElementById('d3Commentrow'+z).value='';
							document.getElementById('d4Commentrow'+z).value='';
							document.getElementById('d5Commentrow'+z).value='';
						}
							
					 document.getElementById("sunTtl").disabled=true;
					 document.getElementById("monTtl").disabled=true;
					 document.getElementById("tueTtl").disabled=true;
					 document.getElementById("wedTtl").disabled=true;
					 document.getElementById("thuTtl").disabled=true;

					 document.getElementById("sunTtl").value='';
					 document.getElementById("monTtl").value='';
					 document.getElementById("tueTtl").value='';
					 document.getElementById("wedTtl").value='';
					 document.getElementById("thuTtl").value='';
					 
					 if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
						 $("#buttons").find('input[type="button"]').button({disabled:false});
					     $("#saveValues").removeAttr( "aria-disabled" );
					     $("#saveValues").attr("disabled",false);
					     $("#submit").attr("disabled",true);
						 $("#submit").addClass(" copyInactive");
					 }
					 
					//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
				 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
				 		$("#buttons").find('input[type="button"]').button({disabled:true});
				 	}
					
				 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
				 		$("#buttons").find('input[type="button"]').button({disabled:false});
				 		$("#saveValues").removeAttr( "aria-disabled" );
					    $("#saveValues").attr("disabled",false);
					    $("#submit").attr("disabled",false);
						$("#submit").removeClass(" copyInactive");
				 	}
					//Added End By Nitin
				}
			} // End if condition for joining week
			else{
		 		if(!isJoiningWeek(weekStartingDate, weekEndingDate, doj)){
		 			var rowcount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
		 			
		 			isMondayFail=false,	isTuesdayFail=false,isWednesdayFail=false,isThursdayFail=false,isFridayFail=false;
					for(var z=0;z<=rowcount-1;z++){
						////alert("z: "+z);
						var selectedRow = $('#tblActivity >tbody >tr:not(".expandable_row")').eq(z);
						var selectedId = selectedRow[0].id.substr(3);
			 			var resVal=document.getElementById('resourceAllocIdrow'+selectedId).value;
			 			var allocationEndDate=findProject(resVal,rowcount);
			 			var allocationStartDate=findprojectStartDate(resVal,rowcount);
			 			var isWeekStartsBeforeProjectAllocation1 = isWeekStartsBeforeProjectAllocation(weekStartingDate, weekEndingDate, allocationStartDate);
			 			var isProjectAllocationEndBeforeWeekEnd1 = isProjectAllocationEndBeforeWeekEnd(weekStartingDate, weekEndingDate, allocationEndDate);
			 			var hourInput = $('#d1Hoursrow' + z);
						 validateHour(hourInput);
			 			// If Project allocation start and end in between the week..
			 			if(isWeekStartsBeforeProjectAllocation1 && isProjectAllocationEndBeforeWeekEnd1){
			 				var allocationStartDay = new Date(allocationStartDate).getDay();
			 				var allocationEndDay = new Date(allocationEndDate).getDay();
			 				////alert("allocationStartDay: "+allocationStartDay+" allocationEndDay: "+allocationEndDay);
			 				if(allocationStartDay == 1){
			 					document.getElementById("sunTtl").disabled=true;
								 document.getElementById("satTtl").disabled=true;
								 
								 document.getElementById("sunTtl").value='';
								 document.getElementById("satTtl").value='';
								 
			 					document.getElementById('d7Hoursrow'+selectedId).value='';
			 					document.getElementById('d1Hoursrow'+selectedId).value='';
			 					document.getElementById('d7Commentrow'+selectedId).value='';
								document.getElementById('d1Commentrow'+selectedId).value='';
			 					// For Friday
			 					if(allocationEndDay == 5){
			 						
			 						document.getElementById('d6Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d5Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d4Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d3Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d2Hoursrow'+selectedId).disabled=false;
				 					document.getElementById('d6Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d5Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d4Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d3Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d2Commentrow'+selectedId).disabled=false;
									
			 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }else if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }else if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }else if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }else if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }
			 						 
			 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
			 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value == ""){
			 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
			 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
			 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
			 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
			 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
			 									 	}
			 									}
			 								}
			 					 		}
			 						}
			 						
			 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
			 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
			 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
			 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
			 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
			 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
			 									 		$("#saveValues").removeAttr( "aria-disabled" );
			 										    $("#saveValues").attr("disabled",false);
			 										    $("#submit").attr("disabled",false);
			 											$("#submit").removeClass(" copyInactive");
			 									 	}
			 									}
			 								}
			 					 		}
			 						}
			 					}
								
								/* For Thursday */
								if(allocationEndDay==4){		
										document.getElementById('d6Hoursrow'+selectedId).disabled=true;
										document.getElementById('d6Commentrow'+selectedId).disabled=true;
										document.getElementById("friTtl").disabled=true;

										document.getElementById('d6Hoursrow'+selectedId).value='';
										document.getElementById('d6Commentrow'+selectedId).value='';
										document.getElementById("friTtl").value='';
										
				 						document.getElementById('d5Hoursrow'+selectedId).disabled=false;
				 						document.getElementById('d4Hoursrow'+selectedId).disabled=false;
				 						document.getElementById('d3Hoursrow'+selectedId).disabled=false;
				 						document.getElementById('d2Hoursrow'+selectedId).disabled=false;
					 					document.getElementById('d5Commentrow'+selectedId).disabled=false;
					 					document.getElementById('d4Commentrow'+selectedId).disabled=false;
					 					document.getElementById('d3Commentrow'+selectedId).disabled=false;
					 					document.getElementById('d2Commentrow'+selectedId).disabled=false;
					 					
									//Added by Nitin
										if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
				 							 $("#buttons").find('input[type="button"]').button({disabled:false});
				 						     $("#saveValues").removeAttr( "aria-disabled" );
				 						     $("#saveValues").attr("disabled",false);
				 						     $("#submit").attr("disabled",true);
				 							 $("#submit").addClass(" copyInactive");
				 						 }else if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
				 							 $("#buttons").find('input[type="button"]').button({disabled:false});
				 						     $("#saveValues").removeAttr( "aria-disabled" );
				 						     $("#saveValues").attr("disabled",false);
				 						     $("#submit").attr("disabled",true);
				 							 $("#submit").addClass(" copyInactive");
				 						 }else if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
				 							 $("#buttons").find('input[type="button"]').button({disabled:false});
				 						     $("#saveValues").removeAttr( "aria-disabled" );
				 						     $("#saveValues").attr("disabled",false);
				 						     $("#submit").attr("disabled",true);
				 							 $("#submit").addClass(" copyInactive");
				 						 }else if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
				 							 $("#buttons").find('input[type="button"]').button({disabled:false});
				 						     $("#saveValues").removeAttr( "aria-disabled" );
				 						     $("#saveValues").attr("disabled",false);
				 						     $("#submit").attr("disabled",true);
				 							 $("#submit").addClass(" copyInactive");
				 						 }
				 						 
				 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
				 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value == ""){
				 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
				 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
				 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
				 									 	$("#buttons").find('input[type="button"]').button({disabled:true});
				 									}
				 								}
				 					 		}
				 						}
				 						
				 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
				 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
				 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
				 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
			 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
			 									 		$("#saveValues").removeAttr( "aria-disabled" );
			 										    $("#saveValues").attr("disabled",false);
			 										    $("#submit").attr("disabled",false);
			 											$("#submit").removeClass(" copyInactive");
				 									}
				 								}
				 					 		}
				 						}
								}
								/* --- Wednesday --- */
								if(allocationEndDay==3){		
										document.getElementById('d6Hoursrow'+selectedId).disabled=true;
										document.getElementById('d5Hoursrow'+selectedId).disabled=true;
										
										document.getElementById('d6Commentrow'+selectedId).disabled=true;
										document.getElementById('d5Commentrow'+selectedId).disabled=true;

										document.getElementById('d6Hoursrow'+selectedId).value='';
										document.getElementById('d5Hoursrow'+selectedId).value='';
										
										document.getElementById('d6Commentrow'+selectedId).value='';
										document.getElementById('d5Commentrow'+selectedId).value='';
										
				 						document.getElementById('d4Hoursrow'+selectedId).disabled=false;
				 						document.getElementById('d3Hoursrow'+selectedId).disabled=false;
				 						document.getElementById('d2Hoursrow'+selectedId).disabled=false;
					 					document.getElementById('d4Commentrow'+selectedId).disabled=false;
					 					document.getElementById('d3Commentrow'+selectedId).disabled=false;
					 					document.getElementById('d2Commentrow'+selectedId).disabled=false;
					 					
									document.getElementById("friTtl").disabled=true;
									document.getElementById("thuTtl").disabled=true;
									document.getElementById("friTtl").value='';
									document.getElementById("thuTtl").value='';
											
									if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }else if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }else if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }
			 						 
			 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
			 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value == ""){
			 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
			 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
	 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
			 								}
			 					 		}
			 						}
			 						
			 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
			 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
			 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
	 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
	 									 		$("#saveValues").removeAttr( "aria-disabled" );
	 										    $("#saveValues").attr("disabled",false);
	 										    $("#submit").attr("disabled",false);
	 											$("#submit").removeClass(" copyInactive");
			 								}
			 					 		}
			 						}
								}
										/*---For Tuesday-----*/
								if (allocationEndDay==2){		
									// var rowcount = $('#tblActivity >tbody >tr').length;
									//for(var z=1;z<=rowcount;z++){ */
										document.getElementById('d6Hoursrow'+selectedId).disabled=true;
										document.getElementById('d5Hoursrow'+selectedId).disabled=true;
										document.getElementById('d4Hoursrow'+selectedId).disabled=true;
										
										document.getElementById('d6Commentrow'+selectedId).disabled=true;
										document.getElementById('d5Commentrow'+selectedId).disabled=true;
										document.getElementById('d4Commentrow'+selectedId).disabled=true;

										document.getElementById('d6Hoursrow'+selectedId).value='';
										document.getElementById('d5Hoursrow'+selectedId).value='';
										document.getElementById('d4Hoursrow'+selectedId).value='';
										
										document.getElementById('d6Commentrow'+selectedId).value='';
										document.getElementById('d5Commentrow'+selectedId).value='';
										document.getElementById('d4Commentrow'+selectedId).value='';
										
									document.getElementById("friTtl").disabled=true;
									document.getElementById("thuTtl").disabled=true;
									document.getElementById("wedTtl").disabled=true;

									document.getElementById("friTtl").value='';
									document.getElementById("thuTtl").value='';
									document.getElementById("wedTtl").value='';
									
			 						document.getElementById('d3Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d2Hoursrow'+selectedId).disabled=false;
				 					document.getElementById('d3Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d2Commentrow'+selectedId).disabled=false;
				 					
									if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }else if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }
			 						 
			 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
			 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value == ""){
			 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
			 					 		}
			 						}
			 						
			 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
			 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
 									 		$("#saveValues").removeAttr( "aria-disabled" );
 										    $("#saveValues").attr("disabled",false);
 										    $("#submit").attr("disabled",false);
 											$("#submit").removeClass(" copyInactive");
			 					 		}
			 						}
									
								}
									/* --- For Monday --- */
								if(allocationEndDay==1){
									/* var rowcount = $('#tblActivity >tbody >tr').length;
									for(var z=1;z<=rowcount;z++){ */	
										////alert("Monday");
										document.getElementById('d6Hoursrow'+selectedId).disabled=true;
										document.getElementById('d5Hoursrow'+selectedId).disabled=true;
										document.getElementById('d4Hoursrow'+selectedId).disabled=true;
										document.getElementById('d3Hoursrow'+selectedId).disabled=true;
										
										document.getElementById('d6Hoursrow'+selectedId).value='';
										document.getElementById('d5Hoursrow'+selectedId).value='';
										document.getElementById('d4Hoursrow'+selectedId).value='';
										document.getElementById('d3Hoursrow'+selectedId).value='';
										
										document.getElementById('d6Commentrow'+selectedId).disabled=true;
										document.getElementById('d5Commentrow'+selectedId).disabled=true;
										document.getElementById('d4Commentrow'+selectedId).disabled=true;
										document.getElementById('d3Commentrow'+selectedId).disabled=true;

										document.getElementById('d6Commentrow'+selectedId).value='';
										document.getElementById('d5Commentrow'+selectedId).value='';
										document.getElementById('d4Commentrow'+selectedId).value='';
										document.getElementById('d3Commentrow'+selectedId).value='';
									/* } */
									
									document.getElementById("friTtl").disabled=true;
									document.getElementById("thuTtl").disabled=true;
									document.getElementById("wedTtl").disabled=true;
									document.getElementById("tueTtl").disabled=true;
									
									document.getElementById("friTtl").value='';
									document.getElementById("thuTtl").value='';
									document.getElementById("wedTtl").value='';
									document.getElementById("tueTtl").value='';
											
			 						document.getElementById('d2Hoursrow'+selectedId).disabled=false;
				 					document.getElementById('d2Commentrow'+selectedId).disabled=false;
				 					
									if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }
			 						 
			 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
			 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value == ""){
			 							$("#buttons").find('input[type="button"]').button({disabled:true});
			 						}
			 						
			 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
								 		$("#buttons").find('input[type="button"]').button({disabled:false});
								 		$("#saveValues").removeAttr( "aria-disabled" );
									    $("#saveValues").attr("disabled",false);
									    $("#submit").attr("disabled",false);
										$("#submit").removeClass(" copyInactive");
			 						}
								}
			 				}
								
							// For Tuesday
			 				if(allocationStartDay == 2){
			 					document.getElementById("sunTtl").disabled=true;
			 					document.getElementById("satTtl").disabled=true;
			 					document.getElementById("monTtl").disabled=true;
			 					document.getElementById('d7Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d1Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d2Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d7Commentrow'+selectedId).disabled=true;
								document.getElementById('d1Commentrow'+selectedId).disabled=true;
								document.getElementById('d2Commentrow'+selectedId).disabled=true;


								document.getElementById("sunTtl").value='';
			 					document.getElementById("satTtl").value='';
			 					document.getElementById("monTtl").value='';
			 					document.getElementById('d7Hoursrow'+selectedId).value='';
			 					document.getElementById('d1Hoursrow'+selectedId).value='';
			 					document.getElementById('d2Hoursrow'+selectedId).value='';
			 					document.getElementById('d7Commentrow'+selectedId).value='';
								document.getElementById('d1Commentrow'+selectedId).value='';
								document.getElementById('d2Commentrow'+selectedId).value='';

			 					// For Friday
			 					if(allocationEndDay == 5){
			 						
			 						document.getElementById('d6Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d5Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d4Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d3Hoursrow'+selectedId).disabled=false;
				 					document.getElementById('d6Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d5Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d4Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d3Commentrow'+selectedId).disabled=false;
				 					
			 						if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }else if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }else if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }else if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }
			 						 
			 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
		 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
		 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
		 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
		 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
		 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
		 									 	}
		 									}
		 								}
		 					 		}
			 						
		 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
		 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
		 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
		 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
		 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
		 									 		$("#saveValues").removeAttr( "aria-disabled" );
		 										    $("#saveValues").attr("disabled",false);
		 										    $("#submit").attr("disabled",false);
		 											$("#submit").removeClass(" copyInactive");
		 									 	}
		 									}
		 								}
		 					 		}
			 					}
								 
								
								/* For Thursday */
								if(allocationEndDay==4){		
										document.getElementById('d6Hoursrow'+selectedId).disabled=true;
										document.getElementById('d6Commentrow'+selectedId).disabled=true;
										document.getElementById("friTtl").disabled=true;

										document.getElementById('d6Hoursrow'+selectedId).value='';
										document.getElementById('d6Commentrow'+selectedId).value='';
										document.getElementById("friTtl").value='';
									//Added by Nitin
									
			 						document.getElementById('d5Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d4Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d3Hoursrow'+selectedId).disabled=false;
				 					document.getElementById('d5Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d4Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d3Commentrow'+selectedId).disabled=false;
				 					
										if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
				 							 $("#buttons").find('input[type="button"]').button({disabled:false});
				 						     $("#saveValues").removeAttr( "aria-disabled" );
				 						     $("#saveValues").attr("disabled",false);
				 						     $("#submit").attr("disabled",true);
				 							 $("#submit").addClass(" copyInactive");
				 						 }else if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
				 							 $("#buttons").find('input[type="button"]').button({disabled:false});
				 						     $("#saveValues").removeAttr( "aria-disabled" );
				 						     $("#saveValues").attr("disabled",false);
				 						     $("#submit").attr("disabled",true);
				 							 $("#submit").addClass(" copyInactive");
				 						 }else if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
				 							 $("#buttons").find('input[type="button"]').button({disabled:false});
				 						     $("#saveValues").removeAttr( "aria-disabled" );
				 						     $("#saveValues").attr("disabled",false);
				 						     $("#submit").attr("disabled",true);
				 							 $("#submit").addClass(" copyInactive");
				 						 }
				 						 
				 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
			 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
			 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
			 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
		 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
			 									}
			 								}
			 					 		}
				 						
			 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
			 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
			 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
		 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
		 									 		$("#saveValues").removeAttr( "aria-disabled" );
		 										    $("#saveValues").attr("disabled",false);
		 										    $("#submit").attr("disabled",false);
		 											$("#submit").removeClass(" copyInactive");
			 									}
			 								}
			 					 		}
								}
								/* --- Wednesday --- */
								if(allocationEndDay==3){		
										document.getElementById('d6Hoursrow'+selectedId).disabled=true;
										document.getElementById('d5Hoursrow'+selectedId).disabled=true;

										document.getElementById('d6Hoursrow'+selectedId).value='';
										document.getElementById('d5Hoursrow'+selectedId).value='';
										
										document.getElementById('d6Commentrow'+selectedId).disabled=true;
										document.getElementById('d5Commentrow'+selectedId).disabled=true;
										
										document.getElementById('d6Commentrow'+selectedId).value='';
										document.getElementById('d5Commentrow'+selectedId).value='';
											
									document.getElementById("friTtl").disabled=true;
									document.getElementById("thuTtl").disabled=true;
									document.getElementById("friTtl").value='';
									document.getElementById("thuTtl").value='';
											
			 						document.getElementById('d4Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d3Hoursrow'+selectedId).disabled=false;
				 					document.getElementById('d4Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d3Commentrow'+selectedId).disabled=false;
				 					
									if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }else if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }
			 						 
			 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
		 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
		 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
		 								}
		 					 		}
			 						
		 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
		 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
 									 		$("#saveValues").removeAttr( "aria-disabled" );
 										    $("#saveValues").attr("disabled",false);
 										    $("#submit").attr("disabled",false);
 											$("#submit").removeClass(" copyInactive");
		 								}
		 					 		}
								}
										/*---For Tuesday-----*/
								if (allocationEndDay==2){		
									// var rowcount = $('#tblActivity >tbody >tr').length;
									//for(var z=1;z<=rowcount;z++){ */
									document.getElementById('d6Hoursrow'+selectedId).disabled=true;
									document.getElementById('d5Hoursrow'+selectedId).disabled=true;
									document.getElementById('d4Hoursrow'+selectedId).disabled=true;
									
									document.getElementById('d6Commentrow'+selectedId).disabled=true;
									document.getElementById('d5Commentrow'+selectedId).disabled=true;
									document.getElementById('d4Commentrow'+selectedId).disabled=true;


									document.getElementById('d6Hoursrow'+selectedId).value='';
									document.getElementById('d5Hoursrow'+selectedId).value='';
									document.getElementById('d4Hoursrow'+selectedId).value='';
									
									document.getElementById('d6Commentrow'+selectedId).value='';
									document.getElementById('d5Commentrow'+selectedId).value='';
									document.getElementById('d4Commentrow'+selectedId).value='';
										
									document.getElementById("friTtl").disabled=true;
									document.getElementById("thuTtl").disabled=true;
									document.getElementById("wedTtl").disabled=true;

									document.getElementById("friTtl").value='';
									document.getElementById("thuTtl").value='';
									document.getElementById("wedTtl").value='';

			 						document.getElementById('d3Hoursrow'+selectedId).disabled=false;
				 					document.getElementById('d3Commentrow'+selectedId).disabled=false;
				 					
									if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }
			 						 
			 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
		 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
									 		$("#buttons").find('input[type="button"]').button({disabled:true});
		 					 		}
			 						
		 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
								 		$("#buttons").find('input[type="button"]').button({disabled:false});
								 		$("#saveValues").removeAttr( "aria-disabled" );
									    $("#saveValues").attr("disabled",false);
									    $("#submit").attr("disabled",false);
										$("#submit").remsoveClass(" copyInactive");
		 					 		}
									
								}
			 				}
										
			 			// For Wednesday
			 				if(allocationStartDay == 3){
			 					document.getElementById("sunTtl").disabled=true;
			 					document.getElementById("satTtl").disabled=true;
			 					document.getElementById("monTtl").disabled=true;
			 					document.getElementById("tueTtl").disabled=true;
			 					document.getElementById('d7Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d1Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d2Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d3Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d7Commentrow'+selectedId).disabled=true;
								document.getElementById('d1Commentrow'+selectedId).disabled=true;
								document.getElementById('d2Commentrow'+selectedId).disabled=true;
								document.getElementById('d3Commentrow'+selectedId).disabled=true;

								document.getElementById("sunTtl").val='';
			 					document.getElementById("satTtl").val='';
			 					document.getElementById("monTtl").val='';
			 					document.getElementById("tueTtl").val='';
			 					document.getElementById('d7Hoursrow'+selectedId).val='';
			 					document.getElementById('d1Hoursrow'+selectedId).val='';
			 					document.getElementById('d2Hoursrow'+selectedId).val='';
			 					document.getElementById('d3Hoursrow'+selectedId).val='';
			 					document.getElementById('d7Commentrow'+selectedId).val='';
								document.getElementById('d1Commentrow'+selectedId).val='';
								document.getElementById('d2Commentrow'+selectedId).val='';
								document.getElementById('d3Commentrow'+selectedId).val='';
			 					// For Friday
			 					if(allocationEndDay == 5){
											
			 						document.getElementById('d6Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d5Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d4Hoursrow'+selectedId).disabled=false;
				 					document.getElementById('d6Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d5Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d4Commentrow'+selectedId).disabled=false;
				 					
			 						if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }else if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }else if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }
			 						 
			 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
	 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
	 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
	 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
	 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
	 									 	}
	 									}
	 								}
			 						
	 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
	 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
	 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
	 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
	 									 		$("#saveValues").removeAttr( "aria-disabled" );
	 										    $("#saveValues").attr("disabled",false);
	 										    $("#submit").attr("disabled",false);
	 											$("#submit").removeClass(" copyInactive");
	 									 	}
	 									}
	 								}
								}
								 
								
								/* For Thursday */
								if(allocationEndDay==4){		
										document.getElementById('d6Hoursrow'+selectedId).disabled=true;
										document.getElementById('d6Commentrow'+selectedId).disabled=true;
										document.getElementById("friTtl").disabled=true;

										document.getElementById('d6Hoursrow'+selectedId).value='';
										document.getElementById('d6Commentrow'+selectedId).value='';
										document.getElementById("friTtl").value='';
									//Added by Nitin
									
			 						document.getElementById('d5Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d4Hoursrow'+selectedId).disabled=false;
				 					document.getElementById('d5Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d4Commentrow'+selectedId).disabled=false;
				 					
										if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
				 							 $("#buttons").find('input[type="button"]').button({disabled:false});
				 						     $("#saveValues").removeAttr( "aria-disabled" );
				 						     $("#saveValues").attr("disabled",false);
				 						     $("#submit").attr("disabled",true);
				 							 $("#submit").addClass(" copyInactive");
				 						 }else if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
				 							 $("#buttons").find('input[type="button"]').button({disabled:false});
				 						     $("#saveValues").removeAttr( "aria-disabled" );
				 						     $("#saveValues").attr("disabled",false);
				 						     $("#submit").attr("disabled",true);
				 							 $("#submit").addClass(" copyInactive");
				 						 }
				 						 
				 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
		 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
		 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
	 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
		 									}
		 								}
				 						
		 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
		 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
	 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
	 									 		$("#saveValues").removeAttr( "aria-disabled" );
	 										    $("#saveValues").attr("disabled",false);
	 										    $("#submit").attr("disabled",false);
	 											$("#submit").removeClass(" copyInactive");
		 									}
		 								}
								}
								/* --- Wednesday --- */
								if(allocationEndDay==3){		
									document.getElementById('d6Hoursrow'+selectedId).disabled=true;
									document.getElementById('d5Hoursrow'+selectedId).disabled=true;
									
									document.getElementById('d6Commentrow'+selectedId).disabled=true;
									document.getElementById('d5Commentrow'+selectedId).disabled=true;
											
									document.getElementById("friTtl").disabled=true;
									document.getElementById("thuTtl").disabled=true;

									document.getElementById('d6Hoursrow'+selectedId).value='';
									document.getElementById('d5Hoursrow'+selectedId).value='';
									
									document.getElementById('d6Commentrow'+selectedId).value='';
									document.getElementById('d5Commentrow'+selectedId).value='';
											
									document.getElementById("friTtl").value='';
									document.getElementById("thuTtl").value='';
											
			 						document.getElementById('d4Hoursrow'+selectedId).disabled=false;
				 					document.getElementById('d4Commentrow'+selectedId).disabled=false;
				 					
									if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }
			 						 
			 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
	 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
								 		$("#buttons").find('input[type="button"]').button({disabled:true});
	 								}
			 						
	 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
								 		$("#buttons").find('input[type="button"]').button({disabled:false});
								 		$("#saveValues").removeAttr( "aria-disabled" );
									    $("#saveValues").attr("disabled",false);
									    $("#submit").attr("disabled",false);
										$("#submit").removeClass(" copyInactive");
	 								}
			 					}
			 				}
			 			
			 			// For Thursday
			 				if(allocationStartDay == 4){
			 					document.getElementById("sunTtl").disabled=true;
			 					document.getElementById("satTtl").disabled=true;
			 					document.getElementById("monTtl").disabled=true;
			 					document.getElementById("tueTtl").disabled=true;
			 					document.getElementById("wedTtl").disabled=true;
			 					document.getElementById('d7Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d1Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d2Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d3Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d4Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d7Commentrow'+selectedId).disabled=true;
								document.getElementById('d1Commentrow'+selectedId).disabled=true;
								document.getElementById('d2Commentrow'+selectedId).disabled=true;
								document.getElementById('d3Commentrow'+selectedId).disabled=true;
								document.getElementById('d4Commentrow'+selectedId).disabled=true;


								document.getElementById("sunTtl").value='';
			 					document.getElementById("satTtl").value='';
			 					document.getElementById("monTtl").value='';
			 					document.getElementById("tueTtl").value='';
			 					document.getElementById("wedTtl").value='';
			 					document.getElementById('d7Hoursrow'+selectedId).value='';
			 					document.getElementById('d1Hoursrow'+selectedId).value='';
			 					document.getElementById('d2Hoursrow'+selectedId).value='';
			 					document.getElementById('d3Hoursrow'+selectedId).value='';
			 					document.getElementById('d4Hoursrow'+selectedId).value='';
			 					document.getElementById('d7Commentrow'+selectedId).value='';
								document.getElementById('d1Commentrow'+selectedId).value='';
								document.getElementById('d2Commentrow'+selectedId).value='';
								document.getElementById('d3Commentrow'+selectedId).value='';
								document.getElementById('d4Commentrow'+selectedId).value='';
			 					// For Friday
			 					if(allocationEndDay == 5){
											
			 						document.getElementById('d6Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d5Hoursrow'+selectedId).disabled=false;
				 					document.getElementById('d6Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d5Commentrow'+selectedId).disabled=false;
				 					
			 						if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }else if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }
			 						 
			 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
 									 	}
 									}
			 						
 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
 									 		$("#saveValues").removeAttr( "aria-disabled" );
 										    $("#saveValues").attr("disabled",false);
 										    $("#submit").attr("disabled",false);
 											$("#submit").removeClass(" copyInactive");
 									 	}
 									}
								}
								 
								
								/* For Thursday */
								if(allocationEndDay==4){		
										document.getElementById('d6Hoursrow'+selectedId).disabled=true;
										document.getElementById('d6Commentrow'+selectedId).disabled=true;
										document.getElementById("friTtl").disabled=true;
										

										document.getElementById('d6Hoursrow'+selectedId).value='';
										document.getElementById('d6Commentrow'+selectedId).value='';
										document.getElementById("friTtl").value='';
									//Added by Nitin
									
			 						document.getElementById('d5Hoursrow'+selectedId).disabled=false;
				 					document.getElementById('d5Commentrow'+selectedId).disabled=false;
				 					
										if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
				 							 $("#buttons").find('input[type="button"]').button({disabled:false});
				 						     $("#saveValues").removeAttr( "aria-disabled" );
				 						     $("#saveValues").attr("disabled",false);
				 						     $("#submit").attr("disabled",true);
				 							 $("#submit").addClass(" copyInactive");
				 						 }
				 						 
				 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
	 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
	 									}
				 						
	 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
 									 		$("#saveValues").removeAttr( "aria-disabled" );
 										    $("#saveValues").attr("disabled",false);
 										    $("#submit").attr("disabled",false);
 											$("#submit").removeClass(" copyInactive");
	 									}
								}
			 				}
			 			// End Thursday
			 			
			 			// For Friday
			 				if(allocationStartDay == 5){
			 					document.getElementById("sunTtl").disabled=true;
			 					document.getElementById("satTtl").disabled=true;
			 					document.getElementById("monTtl").disabled=true;
			 					document.getElementById("tueTtl").disabled=true;
			 					document.getElementById("wedTtl").disabled=true;
			 					document.getElementById("thuTtl").disabled=true;
			 					document.getElementById('d7Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d1Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d2Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d3Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d4Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d5Hoursrow'+selectedId).disabled=true;
			 					document.getElementById('d7Commentrow'+selectedId).disabled=true;
								document.getElementById('d1Commentrow'+selectedId).disabled=true;
								document.getElementById('d2Commentrow'+selectedId).disabled=true;
								document.getElementById('d3Commentrow'+selectedId).disabled=true;
								document.getElementById('d4Commentrow'+selectedId).disabled=true;
								document.getElementById('d5Commentrow'+selectedId).disabled=true;


								document.getElementById("sunTtl").value='';
			 					document.getElementById("satTtl").value='';
			 					document.getElementById("monTtl").value='';
			 					document.getElementById("tueTtl").value='';
			 					document.getElementById("wedTtl").value='';
			 					document.getElementById("thuTtl").value='';
			 					document.getElementById('d7Hoursrow'+selectedId).value='';
			 					document.getElementById('d1Hoursrow'+selectedId).value='';
			 					document.getElementById('d2Hoursrow'+selectedId).value='';
			 					document.getElementById('d3Hoursrow'+selectedId).value='';
			 					document.getElementById('d4Hoursrow'+selectedId).value='';
			 					document.getElementById('d5Hoursrow'+selectedId).value='';
			 					document.getElementById('d7Commentrow'+selectedId).value='';
								document.getElementById('d1Commentrow'+selectedId).value='';
								document.getElementById('d2Commentrow'+selectedId).value='';
								document.getElementById('d3Commentrow'+selectedId).value='';
								document.getElementById('d4Commentrow'+selectedId).value='';
								document.getElementById('d5Commentrow'+selectedId).value='';
			 					// For Friday
			 					if(allocationEndDay == 5){
									
			 						document.getElementById('d6Hoursrow'+selectedId).disabled=false;
				 					document.getElementById('d6Commentrow'+selectedId).disabled=false;
				 					
			 						if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
			 							 $("#buttons").find('input[type="button"]').button({disabled:false});
			 						     $("#saveValues").removeAttr( "aria-disabled" );
			 						     $("#saveValues").attr("disabled",false);
			 						     $("#submit").attr("disabled",true);
			 							 $("#submit").addClass(" copyInactive");
			 						 }
			 						 
			 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
								 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
								 		$("#buttons").find('input[type="button"]').button({disabled:true});
								 	}
		 						
								 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
								 		$("#buttons").find('input[type="button"]').button({disabled:false});
								 		$("#saveValues").removeAttr( "aria-disabled" );
									    $("#saveValues").attr("disabled",false);
									    $("#submit").attr("disabled",false);
										$("#submit").removeClass(" copyInactive");
								 	}
								}
								
			 				}
			 			// End Friday
			 			
			 			}else if(isWeekStartsBeforeProjectAllocation1){
			 				var allocationStartDay = new Date(allocationStartDate).getDay();
			 				
			 			// For Monday
			 				if(allocationStartDay == 1){
			 					document.getElementById('d1Hoursrow'+selectedId).disabled=true;
								
								document.getElementById('d1Commentrow'+selectedId).disabled=true;
								
								document.getElementById("sunTtl").disabled=true;

								document.getElementById('d1Hoursrow'+selectedId).value='';
								
								document.getElementById('d1Commentrow'+selectedId).value='';
								
								document.getElementById("sunTtl").value='';
								
								document.getElementById('d7Hoursrow'+selectedId).disabled=false;
								document.getElementById('d6Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d5Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d4Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d3Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d2Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d7Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d6Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d5Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d4Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d3Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d2Commentrow'+selectedId).disabled=false;
			 					
								if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }
		 						 
		 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
		 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value == ""){
		 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
		 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
		 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
		 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
		 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
		 									 	}
		 									}
		 								}
		 					 		}
		 						}
		 						
		 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
		 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
		 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
		 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
		 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
		 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
		 									 		$("#saveValues").removeAttr( "aria-disabled" );
		 										    $("#saveValues").attr("disabled",false);
		 										    $("#submit").attr("disabled",false);
		 											$("#submit").removeClass(" copyInactive");
		 									 	}
		 									}
		 								}
		 					 		}
		 						}
			 				}
			 			
			 			// For Tuesday
			 				if(allocationStartDay == 2){
			 					document.getElementById('d1Hoursrow'+selectedId).disabled=true;
								document.getElementById('d2Hoursrow'+selectedId).disabled=true;
								
								document.getElementById('d1Commentrow'+selectedId).disabled=true;
								document.getElementById('d2Commentrow'+selectedId).disabled=true;
								
								document.getElementById("sunTtl").disabled=true;
								document.getElementById("monTtl").disabled=true;

								document.getElementById('d1Hoursrow'+selectedId).value='';
								document.getElementById('d2Hoursrow'+selectedId).value='';
								
								document.getElementById('d1Commentrow'+selectedId).value='';
								document.getElementById('d2Commentrow'+selectedId).value='';
								
								document.getElementById("sunTtl").value='';
								document.getElementById("monTtl").value='';
								
								document.getElementById('d7Hoursrow'+selectedId).disabled=false;
								document.getElementById('d6Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d5Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d4Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d3Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d7Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d6Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d5Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d4Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d3Commentrow'+selectedId).disabled=false;
			 					
								if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }
		 						 
		 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
	 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
	 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
	 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
	 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
	 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
	 									 	}
	 									}
	 								}
	 					 		}
		 						
	 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
	 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
	 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
	 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
	 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
	 									 		$("#saveValues").removeAttr( "aria-disabled" );
	 										    $("#saveValues").attr("disabled",false);
	 										    $("#submit").attr("disabled",false);
	 											$("#submit").removeClass(" copyInactive");
	 									 	}
	 									}
	 								}
	 					 		}
			 				}
			 			
			 				// For Wednesday
			 				if(allocationStartDay == 3){
			 					document.getElementById('d1Hoursrow'+selectedId).disabled=true;
								document.getElementById('d2Hoursrow'+selectedId).disabled=true;
								document.getElementById('d3Hoursrow'+selectedId).disabled=true;
								
								document.getElementById('d1Commentrow'+selectedId).disabled=true;
								document.getElementById('d2Commentrow'+selectedId).disabled=true;
								document.getElementById('d3Commentrow'+selectedId).disabled=true;
								
								document.getElementById("sunTtl").disabled=true;
								document.getElementById("monTtl").disabled=true;
								document.getElementById("tueTtl").disabled=true;



								document.getElementById('d1Hoursrow'+selectedId).value='';
								document.getElementById('d2Hoursrow'+selectedId).value='';
								document.getElementById('d3Hoursrow'+selectedId).value='';
								
								document.getElementById('d1Commentrow'+selectedId).value='';
								document.getElementById('d2Commentrow'+selectedId).value='';
								document.getElementById('d3Commentrow'+selectedId).value='';
								
								document.getElementById("sunTtl").value='';
								document.getElementById("monTtl").value='';
								document.getElementById("tueTtl").value='';
								
								document.getElementById('d7Hoursrow'+selectedId).disabled=false;
								document.getElementById('d6Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d5Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d4Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d7Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d6Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d5Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d4Commentrow'+selectedId).disabled=false;
			 					
								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }
		 						 
		 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
 									 	}
 									}
 								}
		 						
 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
 									 		$("#saveValues").removeAttr( "aria-disabled" );
 										    $("#saveValues").attr("disabled",false);
 										    $("#submit").attr("disabled",false);
 											$("#submit").removeClass(" copyInactive");
 									 	}
 									}
 								}
			 				}
			 				
			 				// For Thursday
			 				if(allocationStartDay == 4){
			 					document.getElementById('d1Hoursrow'+selectedId).disabled=true;
								document.getElementById('d2Hoursrow'+selectedId).disabled=true;
								document.getElementById('d3Hoursrow'+selectedId).disabled=true;
								document.getElementById('d4Hoursrow'+selectedId).disabled=true;
								
								document.getElementById('d1Commentrow'+selectedId).disabled=true;
								document.getElementById('d2Commentrow'+selectedId).disabled=true;
								document.getElementById('d3Commentrow'+selectedId).disabled=true;
								document.getElementById('d4Commentrow'+selectedId).disabled=true;
								
								document.getElementById("sunTtl").disabled=true;
								document.getElementById("monTtl").disabled=true;
								document.getElementById("tueTtl").disabled=true;
								document.getElementById("wedTtl").disabled=true;


								document.getElementById('d1Hoursrow'+selectedId).value='';
								document.getElementById('d2Hoursrow'+selectedId).value='';
								document.getElementById('d3Hoursrow'+selectedId).value='';
								document.getElementById('d4Hoursrow'+selectedId).value='';
								
								document.getElementById('d1Commentrow'+selectedId).value='';
								document.getElementById('d2Commentrow'+selectedId).value='';
								document.getElementById('d3Commentrow'+selectedId).value='';
								document.getElementById('d4Commentrow'+selectedId).value='';
								
								document.getElementById("sunTtl").value='';
								document.getElementById("monTtl").value='';
								document.getElementById("tueTtl").value='';
								document.getElementById("wedTtl").value='';
								
								document.getElementById('d7Hoursrow'+selectedId).disabled=false;
								document.getElementById('d6Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d5Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d7Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d6Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d5Commentrow'+selectedId).disabled=false;
			 					
								if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }
		 						 
		 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
								if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
								 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
								 		$("#buttons").find('input[type="button"]').button({disabled:true});
								 	}
								}
		 						
								if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
								 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
								 		$("#buttons").find('input[type="button"]').button({disabled:false});
								 		$("#saveValues").removeAttr( "aria-disabled" );
									    $("#saveValues").attr("disabled",false);
									    $("#submit").attr("disabled",false);
										$("#submit").removeClass(" copyInactive");
								 	}
								}
			 				}
			 				
			 				// For Friday
			 				if(allocationStartDay == 5){
			 					document.getElementById('d1Hoursrow'+selectedId).disabled=true;
								document.getElementById('d2Hoursrow'+selectedId).disabled=true;
								document.getElementById('d3Hoursrow'+selectedId).disabled=true;
								document.getElementById('d4Hoursrow'+selectedId).disabled=true;
								document.getElementById('d5Hoursrow'+selectedId).disabled=true;
								
								document.getElementById('d1Commentrow'+selectedId).disabled=true;
								document.getElementById('d2Commentrow'+selectedId).disabled=true;
								document.getElementById('d3Commentrow'+selectedId).disabled=true;
								document.getElementById('d4Commentrow'+selectedId).disabled=true;
								document.getElementById('d5Commentrow'+selectedId).disabled=true;
								
								document.getElementById("sunTtl").disabled=true;
								document.getElementById("monTtl").disabled=true;
								document.getElementById("tueTtl").disabled=true;
								document.getElementById("wedTtl").disabled=true;
								document.getElementById("thuTtl").disabled=true;


								document.getElementById('d1Hoursrow'+selectedId).value='';
								document.getElementById('d2Hoursrow'+selectedId).value='';
								document.getElementById('d3Hoursrow'+selectedId).value='';
								document.getElementById('d4Hoursrow'+selectedId).value='';
								document.getElementById('d5Hoursrow'+selectedId).value='';
								
								document.getElementById('d1Commentrow'+selectedId).value='';
								document.getElementById('d2Commentrow'+selectedId).value='';
								document.getElementById('d3Commentrow'+selectedId).value='';
								document.getElementById('d4Commentrow'+selectedId).value='';
								document.getElementById('d5Commentrow'+selectedId).value='';
								
								document.getElementById("sunTtl").value='';
								document.getElementById("monTtl").value='';
								document.getElementById("tueTtl").value='';
								document.getElementById("wedTtl").value='';
								document.getElementById("thuTtl").value='';
								
								document.getElementById('d7Hoursrow'+selectedId).disabled=false;
								document.getElementById('d6Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d7Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d6Commentrow'+selectedId).disabled=false;
			 					
								if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }
		 						 
		 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
							 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
							 		$("#buttons").find('input[type="button"]').button({disabled:true});
							 	}
		 						
							 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
							 		$("#buttons").find('input[type="button"]').button({disabled:false});
							 		$("#saveValues").removeAttr( "aria-disabled" );
								    $("#saveValues").attr("disabled",false);
								    $("#submit").attr("disabled",false);
									$("#submit").removeClass(" copyInactive");
							 	}
			 				}
			 				
			 			}else if(isProjectAllocationEndBeforeWeekEnd1){
			 				////alert("isProjectAllocationEndBeforeWeekEnd: "+isProjectAllocationEndBeforeWeekEnd1);
			 				var allocationEndDay = new Date(allocationEndDate).getDay();
			 				/* for Friday */
							 if(allocationEndDay == 5){
								 
									document.getElementById('d6Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d5Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d4Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d3Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d2Hoursrow'+selectedId).disabled=false;
			 						document.getElementById('d1Hoursrow'+selectedId).disabled=false;
				 					document.getElementById('d6Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d5Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d4Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d3Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d2Commentrow'+selectedId).disabled=false;
				 					document.getElementById('d1Commentrow'+selectedId).disabled=false;
								 
								 if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }
		 						 
		 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
		 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value == ""){
		 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
		 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
		 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
		 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
		 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
		 									 	}
		 									}
		 								}
		 					 		}
		 						}
		 						
		 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
		 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
		 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
		 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
		 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
		 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
		 									 		$("#saveValues").removeAttr( "aria-disabled" );
		 										    $("#saveValues").attr("disabled",false);
		 										    $("#submit").attr("disabled",false);
		 											$("#submit").removeClass(" copyInactive");
		 									 	}
		 									}
		 								}
		 					 		}
		 						}
							 }
							
							/* For Thursday */
							if(allocationEndDay==4){		
								document.getElementById('d7Hoursrow'+selectedId).disabled=true;
								document.getElementById('d6Hoursrow'+selectedId).disabled=true;
								
								document.getElementById('d7Commentrow'+selectedId).disabled=true;
								document.getElementById('d6Commentrow'+selectedId).disabled=true;
									
								document.getElementById("friTtl").disabled=true;
								document.getElementById("satTtl").disabled=true;

								document.getElementById('d7Hoursrow'+selectedId).value='';
								document.getElementById('d6Hoursrow'+selectedId).value='';
								
								document.getElementById('d7Commentrow'+selectedId).value='';
								document.getElementById('d6Commentrow'+selectedId).value='';
									
								document.getElementById("friTtl").value='';
								document.getElementById("satTtl").value='';
									
		 						document.getElementById('d5Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d4Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d3Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d2Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d1Hoursrow'+selectedId).disabled=false;
			 					document.getElementById('d5Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d4Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d3Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d2Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d1Commentrow'+selectedId).disabled=false;
								//Added by Nitin
								if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }
		 						 
		 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
		 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value == ""){
		 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
		 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
		 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
	 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
		 									}
		 								}
		 					 		}
		 						}
		 						
		 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
		 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
		 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
		 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
	 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
	 									 		$("#saveValues").removeAttr( "aria-disabled" );
	 										    $("#saveValues").attr("disabled",false);
	 										    $("#submit").attr("disabled",false);
	 											$("#submit").removeClass(" copyInactive");
		 									}
		 								}
		 					 		}
		 						}
							}
							/* --- Wednesday --- */
							if(allocationEndDay==3){		
								document.getElementById('d7Hoursrow'+selectedId).disabled=true;
								document.getElementById('d6Hoursrow'+selectedId).disabled=true;
								document.getElementById('d5Hoursrow'+selectedId).disabled=true;
								
								document.getElementById('d7Commentrow'+selectedId).disabled=true;
								document.getElementById('d6Commentrow'+selectedId).disabled=true;
								document.getElementById('d5Commentrow'+selectedId).disabled=true;
										
								document.getElementById("satTtl").disabled=true;
								document.getElementById("friTtl").disabled=true;
								document.getElementById("thuTtl").disabled=true;


								document.getElementById('d7Hoursrow'+selectedId).value='';
								document.getElementById('d6Hoursrow'+selectedId).value='';
								document.getElementById('d5Hoursrow'+selectedId).value='';
								
								document.getElementById('d7Commentrow'+selectedId).value='';
								document.getElementById('d6Commentrow'+selectedId).value='';
								document.getElementById('d5Commentrow'+selectedId).value='';
										
								document.getElementById("satTtl").value='';
								document.getElementById("friTtl").value='';
								document.getElementById("thuTtl").value='';
								
		 						document.getElementById('d4Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d3Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d2Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d1Hoursrow'+selectedId).disabled=false;
			 					document.getElementById('d4Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d3Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d2Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d1Commentrow'+selectedId).disabled=false;
			 					
								if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }
		 						 
		 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
		 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value == ""){
		 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
		 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
		 								}
		 					 		}
		 						}
		 						
		 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
		 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
		 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
 									 		$("#saveValues").removeAttr( "aria-disabled" );
 										    $("#saveValues").attr("disabled",false);
 										    $("#submit").attr("disabled",false);
 											$("#submit").removeClass(" copyInactive");
		 								}
		 					 		}
		 						}
							}
							
							/*---For Tuesday-----*/
							if (allocationEndDay==2){		
								document.getElementById('d7Hoursrow'+selectedId).disabled=true;
								document.getElementById('d6Hoursrow'+selectedId).disabled=true;
								document.getElementById('d5Hoursrow'+selectedId).disabled=true;
								document.getElementById('d4Hoursrow'+selectedId).disabled=true;
								
								document.getElementById('d7Commentrow'+selectedId).disabled=true;
								document.getElementById('d6Commentrow'+selectedId).disabled=true;
								document.getElementById('d5Commentrow'+selectedId).disabled=true;
								document.getElementById('d4Commentrow'+selectedId).disabled=true;
									
								document.getElementById("satTtl").disabled=true;
								document.getElementById("friTtl").disabled=true;
								document.getElementById("thuTtl").disabled=true;
								document.getElementById("wedTtl").disabled=true;



								document.getElementById('d7Hoursrow'+selectedId).value='';
								document.getElementById('d6Hoursrow'+selectedId).value='';
								document.getElementById('d5Hoursrow'+selectedId).value='';
								document.getElementById('d4Hoursrow'+selectedId).value='';
								
								document.getElementById('d7Commentrow'+selectedId).value='';
								document.getElementById('d6Commentrow'+selectedId).value='';
								document.getElementById('d5Commentrow'+selectedId).value='';
								document.getElementById('d4Commentrow'+selectedId).value='';
									
								document.getElementById("satTtl").value='';
								document.getElementById("friTtl").value='';
								document.getElementById("thuTtl").value='';
								document.getElementById("wedTtl").value='';



		 						document.getElementById('d3Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d2Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d1Hoursrow'+selectedId).disabled=false;
			 					document.getElementById('d3Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d2Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d1Commentrow'+selectedId).disabled=false;
			 					
								if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }else if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }
		 						 
		 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
		 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value == ""){
		 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
								 		$("#buttons").find('input[type="button"]').button({disabled:true});
		 					 		}
		 						}
		 						
		 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
		 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
								 		$("#buttons").find('input[type="button"]').button({disabled:false});
								 		$("#saveValues").removeAttr( "aria-disabled" );
									    $("#saveValues").attr("disabled",false);
									    $("#submit").attr("disabled",false);
										$("#submit").removeClass(" copyInactive");
		 					 		}
		 						}
								
							}
								/* --- For Monday --- */
							if(allocationEndDay==1){
								document.getElementById('d7Hoursrow'+selectedId).disabled=true;
								document.getElementById('d6Hoursrow'+selectedId).disabled=true;
								document.getElementById('d5Hoursrow'+selectedId).disabled=true;
								document.getElementById('d4Hoursrow'+selectedId).disabled=true;
								document.getElementById('d3Hoursrow'+selectedId).disabled=true;
								
								document.getElementById('d7Commentrow'+selectedId).disabled=true;
								document.getElementById('d6Commentrow'+selectedId).disabled=true;
								document.getElementById('d5Commentrow'+selectedId).disabled=true;
								document.getElementById('d4Commentrow'+selectedId).disabled=true;
								document.getElementById('d3Commentrow'+selectedId).disabled=true;
								
								document.getElementById("satTtl").disabled=true;
								document.getElementById("friTtl").disabled=true;
								document.getElementById("thuTtl").disabled=true;
								document.getElementById("wedTtl").disabled=true;
								document.getElementById("tueTtl").disabled=true;



								document.getElementById('d7Hoursrow'+selectedId).value='';
								document.getElementById('d6Hoursrow'+selectedId).value='';
								document.getElementById('d5Hoursrow'+selectedId).value='';
								document.getElementById('d4Hoursrow'+selectedId).value='';
								document.getElementById('d3Hoursrow'+selectedId).value='';
								
								document.getElementById('d7Commentrow'+selectedId).value='';
								document.getElementById('d6Commentrow'+selectedId).value='';
								document.getElementById('d5Commentrow'+selectedId).value='';
								document.getElementById('d4Commentrow'+selectedId).value='';
								document.getElementById('d3Commentrow'+selectedId).value='';
								
								document.getElementById("satTtl").value='';
								document.getElementById("friTtl").value='';
								document.getElementById("thuTtl").value='';
								document.getElementById("wedTtl").value='';
								document.getElementById("tueTtl").value='';

										
		 						document.getElementById('d2Hoursrow'+selectedId).disabled=false;
		 						document.getElementById('d1Hoursrow'+selectedId).disabled=false;
			 					document.getElementById('d2Commentrow'+selectedId).disabled=false;
			 					document.getElementById('d1Commentrow'+selectedId).disabled=false;
								//Added by Nitin
								if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
		 							 $("#buttons").find('input[type="button"]').button({disabled:false});
		 						     $("#saveValues").removeAttr( "aria-disabled" );
		 						     $("#saveValues").attr("disabled",false);
		 						     $("#submit").attr("disabled",true);
		 							 $("#submit").addClass(" copyInactive");
		 						 }
		 						 
		 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
		 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value == ""){
							 		$("#buttons").find('input[type="button"]').button({disabled:true});
		 						}
		 						
		 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
							 		$("#buttons").find('input[type="button"]').button({disabled:false});
							 		$("#saveValues").removeAttr( "aria-disabled" );
								    $("#saveValues").attr("disabled",false);
								    $("#submit").attr("disabled",false);
									$("#submit").removeClass(" copyInactive");
		 						}
							}
			 				
			 			}else{
			 				document.getElementById('d7Hoursrow'+selectedId).disabled=false;
			 				document.getElementById('d6Hoursrow'+selectedId).disabled=false;
	 						document.getElementById('d5Hoursrow'+selectedId).disabled=false;
	 						document.getElementById('d4Hoursrow'+selectedId).disabled=false;
	 						document.getElementById('d3Hoursrow'+selectedId).disabled=false;
	 						document.getElementById('d2Hoursrow'+selectedId).disabled=false;
	 						document.getElementById('d1Hoursrow'+selectedId).disabled=false;
	 						document.getElementById('d7Commentrow'+selectedId).disabled=false;
		 					document.getElementById('d6Commentrow'+selectedId).disabled=false;
		 					document.getElementById('d5Commentrow'+selectedId).disabled=false;
		 					document.getElementById('d4Commentrow'+selectedId).disabled=false;
		 					document.getElementById('d3Commentrow'+selectedId).disabled=false;
		 					document.getElementById('d2Commentrow'+selectedId).disabled=false;
		 					document.getElementById('d1Commentrow'+selectedId).disabled=false;
		 					
			 				if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
	 							 $("#buttons").find('input[type="button"]').button({disabled:false});
	 						     $("#saveValues").removeAttr( "aria-disabled" );
	 						     $("#saveValues").attr("disabled",false);
	 						     $("#submit").attr("disabled",true);
	 							 $("#submit").addClass(" copyInactive");
	 						 }else if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
	 							 $("#buttons").find('input[type="button"]').button({disabled:false});
	 						     $("#saveValues").removeAttr( "aria-disabled" );
	 						     $("#saveValues").attr("disabled",false);
	 						     $("#submit").attr("disabled",true);
	 							 $("#submit").addClass(" copyInactive");
	 						 }else if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
	 							 $("#buttons").find('input[type="button"]').button({disabled:false});
	 						     $("#saveValues").removeAttr( "aria-disabled" );
	 						     $("#saveValues").attr("disabled",false);
	 						     $("#submit").attr("disabled",true);
	 							 $("#submit").addClass(" copyInactive");
	 						 }else if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
	 							 $("#buttons").find('input[type="button"]').button({disabled:false});
	 						     $("#saveValues").removeAttr( "aria-disabled" );
	 						     $("#saveValues").attr("disabled",false);
	 						     $("#submit").attr("disabled",true);
	 							 $("#submit").addClass(" copyInactive");
	 						 }else if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
	 							 $("#buttons").find('input[type="button"]').button({disabled:false});
	 						     $("#saveValues").removeAttr( "aria-disabled" );
	 						     $("#saveValues").attr("disabled",false);
	 						     $("#submit").attr("disabled",true);
	 							 $("#submit").addClass(" copyInactive");
	 						 }
	 						 
	 						//Added By Nitin because if no single value is filled in second row but total hours is not 0 in all fields 
	 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value == ""){
	 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value == ""){
	 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value == ""){
	 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value == ""){
	 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value == ""){
	 									 		$("#buttons").find('input[type="button"]').button({disabled:true});
	 									 	}
	 									}
	 								}
	 					 		}
	 						}
	 						
	 						if (document.getElementById("monTtl").value!= null && document.getElementById("monTtl").value != ""){
	 					 		if (document.getElementById("tueTtl").value!= null && document.getElementById("tueTtl").value != ""){
	 								if (document.getElementById("wedTtl").value!= null && document.getElementById("wedTtl").value != ""){
	 									if (document.getElementById("thuTtl").value!= null && document.getElementById("thuTtl").value != ""){
	 									 	if (document.getElementById("friTtl").value!= null && document.getElementById("friTtl").value != ""){
	 									 		$("#buttons").find('input[type="button"]').button({disabled:false});
	 									 		$("#saveValues").removeAttr( "aria-disabled" );
	 										    $("#saveValues").attr("disabled",false);
	 										    $("#submit").attr("disabled",false);
	 											$("#submit").removeClass(" copyInactive");
	 									 	}
	 									}
	 								}
	 					 		}
	 						}	
			 			}
		 		}
		 			
		 				
		  	
		 }
		}
		
				// $("#submit").removeClass();
			  	/* $("#buttons").find('input[type="button"]').button({disabled:false});
			  	$("#saveValues").removeAttr( "aria-disabled" );
			  	$("#saveValues").attr("disabled",false);
				$("#submit").attr("disabled",true); 
				$("#submit").addClass(" copyInactive"); */
		}else{
			$("#buttons").find('input[type="button"]').button({disabled:true});
		}
		
	}

</script>

<script>

function findProject(resVal, rowCount)
{
var data;
for(var count=0;count<rowCount;count++){
	for(var i=0; i<newProjectId.length; i++){
		if(newProjectId[i] == resVal){
		var index = newProjectId.indexOf(newProjectId[i]);
		}    
	}
}
data=projectEndDate[index];
return data;
}

function findprojectStartDate(resVal, rowCount)
{	   
for(var count=0;count<rowCount;count++){
	for(var i=0; i<newProjectId.length; i++){
		if(newProjectId[i] == resVal){
		var index = newProjectId.indexOf(newProjectId[i]);
		}    
	}
}
var startDateData=projectStartDate[index];
return startDateData;
}


$(document).ready(function(){
	
<c:if test="${empty resourceAllocation}">
projAllocId = 0;
</c:if>
		 
<c:forEach var='projects' items='${resourceAllocation}'>
	doj = '${projects.employeeId.dateOfJoining}';
	var dte="<fmt:formatDate value='${projects.allocEndDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"
	var strdte="<fmt:formatDate value='${projects.allocStartDate}' type='date' pattern='<%=Constants.DATE_PATTERN%>'/>"
	var allocEndDate = Date.parse(dte);
	var allocStartDate=Date.parse(strdte);
	projectEndDate.push(allocEndDate);
	projectStartDate.push(allocStartDate); 
	// projectEndDate.push(allocEndDate);
	newProjectId.push(eval('${projects.id}'));     
</c:forEach>

$(document).on("change", '.chzn-select', function(ev){
	if(firstTimeProjectChange)
	{
		previousValue = $(this).data("value");
		previousId=$(this).data("id");
		firstTimeProjectChange = false;
	}
	var rowCount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
	var nRow=   $(this).closest("tr").attr("id");
	var resVal=document.getElementById('resourceAllocId'+nRow).value;
	addNewvar=false;
	var data=findProject(resVal,rowCount);
	var projectStartDate=findprojectStartDate(resVal,rowCount);
	 
	//var nRow = $(this).parents('tr')[0].attr('id');
	     
	arrayComparison1(nRow,data,projectStartDate);
	if(previouvalue){
	    	  
		//$(this).data('lastSelected').attr("selected", true);
		$(this).val(previousId);
		resVal=document.getElementById('resourceAllocId'+nRow).value;
		    	   
		data=findProject(resVal,rowCount);
		projectStartDate=findprojectStartDate(resVal,rowCount);
	    	 
	}
	checkWithAllocEndDate(data,nRow,projectStartDate);
	previousId = $(this).val();
	checkSendForApproval();   	 
});  
      

function arrayComparison1(rowNum,data,startDate){
	var endDatewithinThisWeek= false;
	

	var startDateWithinThisWeek=false;
	var dayOfWeek= "sun,mon,tue,wed,thu,fri,sat";
	var dayArray = dayOfWeek.split(',');
	var dateArray = dateString.split(',');
	
    //var projectId = ;
    var rowCount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
    //document.getElementById('resourceAllocId'+rowNum).value("test");
    //document.getElementById('resourceAllocId'+rowNum).value="test";
   // var resVal=document.getElementById('resourceAllocId'+rowNum).value;
    
    
    var projectName=$('#resourceAllocId'+rowNum).find("option:selected").text();
     
    //var resId=resourceAllocId0
    
    if(startDate!=null)
    	{
    	for(var k=0;k<7;k++)
   	 {
   	 if(startDate.getTime()==new Date(dateArray[k]).getTime())
   	 { 
   		startDateWithinThisWeek = true;
   		 break;
   	 }
   	 }
    	if(startDateWithinThisWeek==true)
   	 {
    		 for(var j=0;j<7;j++){
                 
            	 if(startDate.getTime() > Date.parse(dateArray[j]).getTime() && document.getElementById('d'+(j+1)+"Hours"+rowNum).value!=""){
            		 if (confirm('Do you want to loose changes made before'+startDate+' as this is allocation start date of "'+projectName+'"')) {
            			 for(var z=k-1;z>=0;z--){
            			 var hour =  document.getElementById('d'+(z+1)+"Hours"+rowNum).value;
            			 var total=document.getElementById(dayArray[z]+'Ttl' ).value;
            			 var weeklyHour =document.getElementById('weekHrs' ).value;
            		 document.getElementById('d'+(z+1)+"Hours"+rowNum).value="";
            		 document.getElementById('d'+(z+1)+"Comment"+rowNum).value="";
            		 ////alert(total-hour);
            		 document.getElementById(dayArray[z]+'Ttl' ).value=total-hour;
            		 document.getElementById('weekHrs' ).value=weeklyHour-hour;
            		 changeCommentIcon();
            			 }
            		 previouvalue=false;
            		
            		 }
            		 else
            		 {
            	        	previouvalue=true;
            		 }
            		 break;
            		 
            	 }
            }
   	 }
    	}
      if(data!=null){
     for(var j=0;j<7;j++)
    	 {
    	 if(data.getTime()==new Date(dateArray[j]).getTime())
    	 { 
    		 endDatewithinThisWeek = true;
    		 break;
    	 }
    	 }
  
    
     
     

    if(endDatewithinThisWeek==true){
                
                
                for(var j=0;j<7;j++){
                 
                	 if(data.getTime() < Date.parse(dateArray[j]).getTime() && document.getElementById('d'+(j+1)+"Hours"+rowNum).value!=""){
                		 if (confirm('Do you want to loose changes made after'+dateArray[j-1]+' as this is allocation end date of "'+projectName+'"')) {
                			 for(var z=j;z<7;z++){
                			 var hour =  document.getElementById('d'+(z+1)+"Hours"+rowNum).value;
                			 var total=document.getElementById(dayArray[z]+'Ttl' ).value;
                			 var weeklyHour =document.getElementById('weekHrs' ).value;
                		 document.getElementById('d'+(z+1)+"Hours"+rowNum).value="";
                		 document.getElementById('d'+(z+1)+"Comment"+rowNum).value="";
                		 ////alert(total-hour);
                		 document.getElementById(dayArray[z]+'Ttl' ).value=total-hour;
                		 document.getElementById('weekHrs' ).value=weeklyHour-hour;
                		 changeCommentIcon();
                			 }
                		 previouvalue=false;
                		
                		 }
                		 else
                		 {
                	        	previouvalue=true;
                		 }
                		 break;
                		 
                	 }
                }
            	
               
                }}
    else
    	{
    	previouvalue=false;
    	}
    	
}

$('#submit').click(function() {
     /* startProgress(); */
     var moduleFlag = false; //SARANG ADDED
     var submitFlag = true;
        var cmtflag=true;
        var rowCount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
       
        var projectflag = true;
        var activityModuleFlage=true;
        var selectChkAr = [];
        var hourGreater = 0;
        
        if(!isJoiningWeek){
        	if( (document.getElementById("monTtl").value <=0) ||
        			(document.getElementById("tueTtl").value <=0) ||  
        			(document.getElementById("wedTtl").value <=0) ||
                    (document.getElementById("thuTtl").value <=0) || 
                    (document.getElementById("friTtl").value <=0))
       			{
            	showError("Please fill Hours from Monday to Friday");
         		//stopProgress();
           return;
       			}
        }
        
        //** Added for #2090 **//
        if(document.getElementById("monTtl").value >24){
        	hourGreater++;
        }else if(document.getElementById("tueTtl").value >24){
        	hourGreater++;
        }else if(document.getElementById("wedTtl").value >24){
        	hourGreater++;
        }else if(document.getElementById("thuTtl").value >24){
        	hourGreater++;
        }else if(document.getElementById("friTtl").value >24){
        	hourGreater++;
        }else if(document.getElementById("sunTtl").value >24){
        	hourGreater++;
        }else if(document.getElementById("satTtl").value >24){
        	hourGreater++;
        }
        
        if(hourGreater!=0){
    	   	var errorMsg = "Please Enter Less Than 24 Hours In a Day";
    		showError(errorMsg); 
    		//stopProgress();
       		return;
        }
        
        //** Code ends here  **//
        
        var index;
        var flagForAlphaInHours = true;
        
        for( index=0; index<rowCount; index++){
        	
        	var actId = $("#activityId"+index).val();
        	
        	if(actId==null)
            { 
               
        		 $('#tblActivity >tbody').find("[id=row"+index+"]").remove();
        		//var warningMsg = "You are not allocated to any activity for Project: "+$('#resourceAllocIdrow'+index+' :selected').text()+" in specified week.";
                //showError(warningMsg);
                //stopProgress();
                //return;
                index++;
                continue;
             }else if(actId==undefined){
             
             //rowCount++;
	            //	 continue;
	             }
        	var d1HoursRowVal = document.getElementById('d1Hoursrow'+(index)).value;
        	var d2HoursRowVal = document.getElementById('d2Hoursrow'+(index)).value;
        	var d3HoursRowVal = document.getElementById('d3Hoursrow'+(index)).value;
        	var d4HoursRowVal = document.getElementById('d4Hoursrow'+(index)).value;
        	var d5HoursRowVal = document.getElementById('d5Hoursrow'+(index)).value;
        	var d6HoursRowVal = document.getElementById('d6Hoursrow'+(index)).value;
        	var d7HoursRowVal = document.getElementById('d7Hoursrow'+(index)).value;
        	
        	if(!checkHourValueForAlphabets(d1HoursRowVal)){
				$('#d1Hoursrow' + index).css("border", "1px solid #ff0000");
				flagForAlphaInHours = false;
			}
			if(!checkHourValueForAlphabets(d2HoursRowVal)){
				$('#d2Hoursrow' + index).css("border", "1px solid #ff0000");
				flagForAlphaInHours = false;
			}
			if(!checkHourValueForAlphabets(d3HoursRowVal)){
				$('#d3Hoursrow' + index).css("border", "1px solid #ff0000");
				flagForAlphaInHours = false;
			}
			if(!checkHourValueForAlphabets(d4HoursRowVal)){
				$('#d4Hoursrow' + index).css("border", "1px solid #ff0000");
				flagForAlphaInHours = false;
			}
			if(!checkHourValueForAlphabets(d5HoursRowVal)){
				$('#d5Hoursrow' + index).css("border", "1px solid #ff0000");
				flagForAlphaInHours = false;
			}
			if(!checkHourValueForAlphabets(d6HoursRowVal)){
				$('#d6Hoursrow' + index).css("border", "1px solid #ff0000");
				flagForAlphaInHours = false;
			}
			if(!checkHourValueForAlphabets(d7HoursRowVal)){
				$('#d7Hoursrow' + index).css("border", "1px solid #ff0000");
				flagForAlphaInHours = false;
			}
        }
        if(!flagForAlphaInHours){
        	var errorMsg = "Please enter numeric value in Hours! ";
            showError(errorMsg);
        	stopProgress();
        	return;
        }
        
        startProgress();
        // Commented by Nitin because added same condition above 
        
		/* for(var index=0; index<rowCount; index++){
			var actId = $("#activityId"+index).val();
		
			 if(actId==null && actId!=undefined){ 
	         	var warningMsg = "No activity defined for Project. Please contact Project Manager." //: "+$('#resourceAllocIdrow'+index+' :selected').text()+" in specified week.";
	            showError(warningMsg);
	            stopProgress();
	            return;
			}else if(actId==undefined){
            	 continue;
             }
		} */ 
        var confirmForSubmit = confirm("Are you sure you want to Submit");
        if(!confirmForSubmit) {
            stopProgress();
            return;
        }
        	////alert("monTtl"+document.getElementById("monTtl").value);
         document.getElementById("submitStatusHidden").value="submit";
         
         
        /* Sarang Code Start introduced For Checking Manadtory Modules submit button*/
        var rowCount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
        
        for (var i = 0; i<rowCount; i++) { 
        	 var selectId="#activityId"+i; 
             var seleModuleId="#module"+i; 
             var selectedSubModuleId = "#subModule"+i; 
             var moduleValue= $(seleModuleId).val(); 
             var subModuleValue= $(selectedSubModuleId).val(); 
             var selectedText = $(selectId).find('option:selected').text(); 
            // //alert(selectedText);
             $(selectId+' option').each(function(index,element){
				 var optionVal = element.value;
            	if($.trim(this.text) == $.trim(selectedText)) {	
         	   	 if (/true/i.test(optionVal)){
         	   		if(moduleValue==null||moduleValue==''||moduleValue=='-1'){    
         	   		 moduleFlag = true;
					 $(seleModuleId).css("border", "1px solid #ff0000");
         	   		} else {
							$(seleModuleId).removeAttr("style");            	  
						}
         	   		}
         	   	 }
            	 });
        
               }
         	 	 // end of for loop (rowCount)
      /* Sarang Code End introduced For Checking Manadtory Modules */
       
        
 
         //sarang original commented below
         /* <c:forEach var="activity" items="${activities}">
         
         if ('${activity.mandatory}' == 'true') {
             var activityValue='${activity.id}'+';'+'${activity.max}'+';'+'${activity.format}';
            
             selectChkAr.push(activityValue);
         }
         else
       	 </c:forEach> */
         //sarang original commented above
        	
        
            /** Call function to check the Module entered or not for the required Activity */
            //sarang original commented below
             /* var ticketFlag = validateActivityOnBasesModuleTicketDtl(selectChkAr);
                if(!ticketFlag) {
                    stopProgress();
                    return;
                }  */
            //sarang original commented above 
            
               /* Sarang Code added start For sticker in mandatory cases*/
                if(moduleFlag) {
                	////alert("inside new flag ")
                	
                	stopProgress();
                	var errorMsg = "Please Enter Module";
            		showError(errorMsg);
                    return;
                }
                /* Sarang Code added end For sticker in mandatory cases*/
                
                /*Pankaj Birla code added and function is available on rmsTicketPriorityStatus.js file......*/
            	var flag = validateTicketPriorityAndStatus(rowCount);
            	if(!flag){
                	return;
                }
                var timesheetCommentOptional = '${timesheetCommentOptional}';
//                 //alert ("timesheetCommentOptional" + timesheetCommentOptional);
                /** To check the comments entered or not for each day */
                if ( (timesheetCommentOptional == null) || (timesheetCommentOptional == '') || (timesheetCommentOptional != 'Y')) {
                    $('.comments_textarea').each(function(){
                        var comment = $.trim($(this).val());
                        var comment1 = $(this).val().length;
                
                        // To remove the space and save the trimmed value
                        $(this).val(comment);
						var enteredHours = 0;
						var hourInputId = this.id.split('Commentrow');
						var hourInput = $('#' + hourInputId[0] + 'Hoursrow' + hourInputId[1]);
						enteredHours = $(hourInput).val();
						var currentRow = this.id.split('Commentrow')[1];
                        // enteredHours = $(this).parent().parent(".poRel").find(".small").val();
                        var isDisabled = $(this).prop('disabled');
                        if(!isDisabled && enteredHours >= 0 && enteredHours != '') {
                            if(null == comment || comment==''||comment==0){
                                submitFlag = false;
								 $(hourInput, this).css("border", "1px solid #ff0000");
								 $('#row_timesheet_' + currentRow).removeClass('hide');
                             } else {
                                 if(comment1>256){
                                    cmtflag=false;
									//submitFlag = false;
									$(hourInput, this).css("border", "1px solid #ff0000");
									$('#row_timesheet_' + currentRow).removeClass('hide');

                                    //  $(this).parent().parent(".poRel").find(".comments").css("border", "1px solid #ff0000");
                                 } else {
										$(hourInput, this).css("border", "1px solid #D5D5D5");
									//  $(this).parent().parent(".poRel").find(".comments").css("border", "1px solid #D5D5D5");                 
									 $('#row_timesheet_' + currentRow).removeClass('hide');									 
                                }
                             
                                 //$(this).parent().parent(".poRel").find(".comments").css("border", "1px solid #D5D5D5");                 
                            }
                            
                        
                        }
                    });    
                }
            if(cmtflag==false){
                    stopProgress();
                    var errorMsg = "Comments can't be more than 256 characters";
                    showError(errorMsg);
                    return;
        }    
        if(submitFlag==false){
            stopProgress();
            var errorMsg = "Please Enter Comments";
            showError(errorMsg);
            return;
        }
        $('.chzn-select').each(function(){
            var valueObj = $(this);
            if(valueObj==null || $(valueObj).val()==null) {
                ////alert("You are not allocated to any project in specified week. ");
                var warningMsg = "You are not allocated to any project in specified week.";
                showWarning(warningMsg);
                $(this).css("border", "1px solid #ff0000");
                projectflag=false;
                        
            }
        });
        if(projectflag == false) {
            stopProgress();
            return;
        }
        if(checkFilledHours() == false){
            showError("Please put hours for atleast one day for each row");
        
            stopProgress();
              return false;
          
      }
        if(checkHoursFlagArray.length>0){
            submitFlag=false;
               stopProgress();
        }
        if(rowCount>0){
        var isSeleCount=isSeletedProjectNotApproved(rowCount);
        if(isSeleCount>0)
        { 
            var warningMsg = "Please select not approved project";
            showError(warningMsg);
            submitFlag=false;
            stopProgress();
         }
        }
        
        /* sarang comment Need to avoid check by ticketFlag */ 
        if(submitFlag /* && ticketFlag */) {
             
            if(rowCount>0){
                getDisabledSelectedField(rowCount);
            } 
            document.getElementById('userActivityForm').submit();
        }
        
    /*  startProgress();
     document.getElementById("submitStatusHidden").value="submit";
     document.getElementById('userActivityForm').submit(); */
//     stopProgress();
     
 });
 
 
 function checkHourValueForAlphabets(hourValue){
	 ////alert("hourValue ::"+hourValue);
	 var regex = /^[0-9]*(\.[0-9]+)?$/;
	 if(!hourValue.match(regex) && hourValue!= ""){
		 return false; 
	 }else{
		 return true;
	 }
 }
 
 function checkFilledHours()
 {      
     var rowcount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;                
                for(var z=0;z<=rowcount;z++){    
                	var actId = $("#activityId"+z).val();
                	if(actId==undefined){
		            	 continue;
					 }
                      if((document.getElementById('d1Hoursrow'+(z)).value!="") ||  (document.getElementById('d2Hoursrow'+(z)).value!="") || (document.getElementById('d3Hoursrow'+(z)).value!="") || (document.getElementById('d4Hoursrow'+(z)).value!="") || (document.getElementById('d5Hoursrow'+(z)).value!="") || (document.getElementById('d6Hoursrow'+(z)).value!="") || (document.getElementById('d7Hoursrow'+(z)).value!=""))
                      {
                      
                       }else{
                            return false;
                      }
               } 
 }


 //saveValues function 123
$('#saveValues').click(function() {
    
	var ticketPriorityFlag = false;
	var ticketStatusFlag = false;
	var moduleFlag = false; //SARAMG ADDED
    var submitFlag = true;
    var cmtflag=true;
    var rowCount = $('#tblActivity >tbody >tr:not(".expandable_row")').length;
    var projectflag = true;
    var activityModuleFlage=true;
    var selectChkAr = [];
    var hourGreater=0;
     document.getElementById("submitStatusHidden").value="save";
    
     for(var index=0; index<rowCount; index++){
			var actId = $("#activityId"+index).val();
			
			 //if(actId==null&&actId!=undefined)
			 if(actId==null)
	            { 
				 
				 $('#tblActivity >tbody').find("[id=row"+index+"]").remove();
				 //  var warningMsg = "You are not allocated to any activity for Project: "+$('#resourceAllocIdrow'+index+' :selected').text()+" in specified week.";
	              ///  showError(warningMsg);
	               // stopProgress();
	              //  return;
	             }else if(actId==undefined){
	            	 //rowCount++;
	             
	            	 //continue;
	             }
		}
   
     startProgress();
   //** Added for #2090 **//
     if(document.getElementById("monTtl").value >24){
     	hourGreater++;
     }else if(document.getElementById("tueTtl").value >24){
     	hourGreater++;
     }else if(document.getElementById("wedTtl").value >24){
     	hourGreater++;
     }else if(document.getElementById("thuTtl").value >24){
     	hourGreater++;
     }else if(document.getElementById("friTtl").value >24){
     	hourGreater++;
     }else if(document.getElementById("sunTtl").value >24){
     	hourGreater++;
     }else if(document.getElementById("satTtl").value >24){
     	hourGreater++;
     }
     
     if(hourGreater!=0){
 	   	var errorMsg = "Please Enter Less Than 24 Hours In a Day";
 		showError(errorMsg); 
 		stopProgress();
    		return;
     }
     
     //** Code ends here  **//
     
     //** Added for #2152 **//
     var index;
     var flagForAlphaInHours = true;
     
     for( index=0; index<=rowCount; index++){
    	 var actId = $("#activityId"+index).val();
		 if(actId==undefined){
            	 continue;
             }
     	var d1HoursRowVal = document.getElementById('d1Hoursrow'+(index)).value;
     	var d2HoursRowVal = document.getElementById('d2Hoursrow'+(index)).value;
     	var d3HoursRowVal = document.getElementById('d3Hoursrow'+(index)).value;
     	var d4HoursRowVal = document.getElementById('d4Hoursrow'+(index)).value;
     	var d5HoursRowVal = document.getElementById('d5Hoursrow'+(index)).value;
     	var d6HoursRowVal = document.getElementById('d6Hoursrow'+(index)).value;
     	var d7HoursRowVal = document.getElementById('d7Hoursrow'+(index)).value;
     	
     	if(!checkHourValueForAlphabets(d1HoursRowVal)){
			$('#d1Hoursrow' + index).css("border", "1px solid #ff0000");
     		flagForAlphaInHours = false;
     	}
     	if(!checkHourValueForAlphabets(d2HoursRowVal)){
			$('#d2Hoursrow' + index).css("border", "1px solid #ff0000");
     		flagForAlphaInHours = false;
     	}
     	if(!checkHourValueForAlphabets(d3HoursRowVal)){
			$('#d3Hoursrow' + index).css("border", "1px solid #ff0000");
     		flagForAlphaInHours = false;
     	}
     	if(!checkHourValueForAlphabets(d4HoursRowVal)){
			$('#d4Hoursrow' + index).css("border", "1px solid #ff0000");
     		flagForAlphaInHours = false;
     	}
     	if(!checkHourValueForAlphabets(d5HoursRowVal)){
			$('#d5Hoursrow' + index).css("border", "1px solid #ff0000");
     		flagForAlphaInHours = false;
     	}
     	if(!checkHourValueForAlphabets(d6HoursRowVal)){
			$('#d6Hoursrow' + index).css("border", "1px solid #ff0000");
     		flagForAlphaInHours = false;
     	}
     	if(!checkHourValueForAlphabets(d7HoursRowVal)){
			$('#d7Hoursrow' + index).css("border", "1px solid #ff0000");
     		flagForAlphaInHours = false;
     	}
     }
     if(!flagForAlphaInHours){
     	var errorMsg = "Please enter numeric value in Hours! ";
            showError(errorMsg);
     	stopProgress();
     	return;
     }
     
   //** Code ends here  **//
   
   /* Sarang Code Start introduced For Checking Manadtory Modules for save button */
       
        for (var i = 0; i<rowCount; i++) { 
        	 var selectId="#activityId"+i; 
             var seleModuleId="#module"+i;
             var selectedSubModuleId = "#subModule"+i;  
             var moduleValue= $(seleModuleId).val();
             var subModuleValue = $(selectedSubModuleId).val();
             var selectedText = $(selectId).find('option:selected').text(); 
             $(selectId+' option').each(function(index,element){
            	 var optionVal = element.value;
            	  if($.trim(this.text) == $.trim(selectedText)) {
            		 if (/true/i.test(optionVal)){
	         	   		if(moduleValue==null||moduleValue==''||moduleValue=='-1'){    
	         	   		 moduleFlag = true;
						 $(seleModuleId).css("border", "1px solid #ff0000");
	         	   		} else {
							$(seleModuleId).removeAttr("style");            	  
						}
         	   		}
         	   	 }
            });
            var selectedSubModule = $(selectedSubModuleId).find('option:selected').text(); 
            $(selectedSubModuleId+' option').each(function(index,element){
				 var optionVal = element.value;
            	  if($.trim(this.text) == $.trim(selectedSubModule)) {
            		 if (/true/i.test(optionVal)){
	         	   		if(subModuleValue==null||subModuleValue==''||subModuleValue=='-1'){    
	         	   		 moduleFlag = true;
						 $(seleModuleId).css("border", "1px solid #ff0000");
	         	   		} else {
							$(seleModuleId).removeAttr("style");            	  
						}
         	   		}
         	   	 }
            });
             
        
      }  // end of for loop (rowCount)
      
      if(moduleFlag) {
          	////alert("inside new flag ")
          	
          	stopProgress();
          	var errorMsg = "Please Enter Module";
      		showError(errorMsg);
              return;
      }
    
      /*Pankaj Birla code added and function is available on rmsTicketPriorityStatus.js file......*/
	  	var flag = validateTicketPriorityAndStatus(rowCount);
	  	if(!flag){
	      	return;
	    }      
      
      if(cmtflag==false){
              stopProgress();
              var errorMsg = "Comments can't be more than 256 characters";
              showError(errorMsg);
              return;
      }    
	  $('.chzn-select').each(function(){
        var valueObj = $(this);
        if(valueObj==null || $(valueObj).val()==null) {
            ////alert("You are not allocated to any project in specified week. ");
            var warningMsg = "You are not allocated to any project in specified week.";
            showWarning(warningMsg);
            $(this).css("border", "1px solid #ff0000");
            projectflag=false;
                    
        }
      });
    if(projectflag == false) {
        stopProgress();
        return;
    }
    if(checkFilledHours() == false){
        showError("Please put hours for atleast one day for each row");
    
        stopProgress();
          return false;
	}
    if(checkHoursFlagArray.length>0){
        submitFlag=false;
           stopProgress();
    }
    if(rowCount>0){
    	var isSeleCount=isSeletedProjectNotApproved(rowCount);
	    if(isSeleCount>0)
	    { 
	        var warningMsg = "Please select not approved project";
	        showError(warningMsg);
	        submitFlag=false;
	        stopProgress();
	     }
    }
    
    /* sarang comment Need to avoid check by ticketFlag */ 
    if(submitFlag /* && ticketFlag */) {
        if(rowCount>0){
            getDisabledSelectedField(rowCount);
        } 
        document.getElementById('userActivityForm').submit();
    }
    
});

function getPKAndStatusArray(rowCount)
{
     var appStatusAndPkArray=[];
     
         for (var i = 0; i<=rowCount; i++){
             var disRowId='row'+i;
             
             var approveFieldId='approveField'+i;
             var rowInputID='r'+disRowId;
             
               var rowValuePK=$("#"+rowInputID).val();
               if(rowValuePK==undefined){
            	   continue;
               }
             
             var appStsForRowId=$("#"+approveFieldId).val();
         
             if(rowValuePK!=null&&appStsForRowId!=null)
                 
             appStatusAndPkArray.push(rowValuePK+':'+appStsForRowId);
         }
    return     appStatusAndPkArray; 
}
function getRSIDAndStatusArray(rowCount)
{
     var appStatusAndPkArray=[];
         for (var i = 0; i<=rowCount; i++){
             var selectid="resourceAllocId"+i;
             var approveFieldId='approveField'+i;
             var rowValuePK= $("#"+selectid+" option:selected").val();
             if(rowValuePK==undefined){
            	 continue;
             }
             var appStsForRowId=$("#"+approveFieldId).val();
              
             if(rowValuePK!=null&&appStsForRowId!=null&&appStsForRowId=='1')
             appStatusAndPkArray.push(rowValuePK+':'+appStsForRowId);
         }
    return     appStatusAndPkArray; 
}

function getDisabledSelectedField(rowCount) 
{
     var appStatusAndPkArray=[];
         for (var i = 0; i<=rowCount; i++){
             
             var disRowId='row'+i;
             var selctbox =$("#"+disRowId).find("td").find("select");
             if(selctbox==undefined){
            	 continue;
             }
             selctbox.removeAttr("disabled", "");
           }
         appStatusAndPkArray=getPKAndStatusArray(rowCount);
         if(appStatusAndPkArray!=null&&appStatusAndPkArray.length>0){
        	 $("#userActivityForm").append("<input type='hidden' name='apprValsStatusArray' value='"+appStatusAndPkArray+"'/>");
         }
 }
   
function isSeletedProjectNotApproved(rowCount) 
{
     var appStatusAndPkArray=[];
     var isProjectFlag=0;
        appStatusAndPkArray=getRSIDAndStatusArray(rowCount);
         if(appStatusAndPkArray!=null&&appStatusAndPkArray.length>0)
         {
            
         for (var i = 0; i<=rowCount; i++){
         var selectid="resourceAllocId"+i;
         var activityValue = $("#"+selectid+" option:selected").val();
         if(activityValue==undefined){
        	 continue;
         }
             for(var count=0 ;count<appStatusAndPkArray.length ; count++ ) {
                var testArray=appStatusAndPkArray[count].split(":");
                 if(testArray.length==2 && testArray[0]==activityValue){
                var approveFieldId='approveField'+i;            
                var appStsForRowId=$("#"+approveFieldId).val();
                  if(testArray[1]!=appStsForRowId){     
                      isProjectFlag++;
                     $('#'+selectid).css("border", "1px solid #ff0000");
                   }
                 }
               }
                
             }
             return isProjectFlag;
         }
  }

/* Below Function validateActivityOnBasesModuleTicketDtl not Required for module check */
function validateActivityOnBasesModuleTicketDtl(selectChkAr){
    var userActivityblckAlertFlag=false;
    var userActivityMaxlengthFlag=false;
    var userActivityRegexFlag=false;
    var alphaRegex = /^([a-zA-Z]){2}([ ])([0-9]){7}$/;
    var rowCount = $('#tblActivity >tbody >tr:not(".expandable_row"):not(".expandable_row")').length;
    ////alert('rowCount='+rowCount);
    
    ///activityId[{{:#index}}]
    if(rowCount>0)
    {
    for (var i = 0; i<=rowCount; i++){
        var selectid="activityId"+i;
        var seleModuleId="module"+i;
        var moduleValue= $("#"+seleModuleId).val();
        var activityValue = $("#"+selectid+" option:selected").val();
        if(activityValue==undefined){
        	continue;
        }
        var moduleCheck="";
        for (var j=0 ;j<selectChkAr.length;j++){ 
            var test3=$("#"+seleModuleId).attr('class');
          if(moduleCheck!=seleModuleId)
           {
               $("#"+seleModuleId).css("border", "1px solid #D5D5D5");
           }       
            var activityTblValue=selectChkAr[j].split(";");
            var mandtoryString=activityTblValue[0];
            var maxLnString=activityTblValue[1];
            var regexString=activityTblValue[2];
            if(mandtoryString!=null&&activityValue==mandtoryString){
                if(moduleValue==null||moduleValue==''){
                    userActivityblckAlertFlag=true;
                 }    
                else if(maxLnString!=null&& moduleValue!=''&& maxLnString!=''){
                    /* var lengchk=moduleValue.length;
                    //var lengchk1 = new String(lengchk);
                    var numlen=Number(lengchk);
                    var numChk=Number(maxLnString);
                    var numChkStr=lengchk.toString();
                    var test=parseInt(numlen)!=parseInt(numChk);
                    if(test==false){
                    userActivityMaxlengthFlag=true; 
                    }*/
                }    
                else if(regexString!=null && regexString!=''&& moduleValue!=''&& regexString.test(moduleValue)){
                    userActivityRegexFlag=true;
              }
            if(userActivityblckAlertFlag||userActivityMaxlengthFlag||userActivityRegexFlag){
                moduleCheck=seleModuleId;
                $("#"+seleModuleId).css("border", "1px solid #ff0000");
              }    
          }
        }
    }
     if(userActivityblckAlertFlag||userActivityMaxlengthFlag||userActivityRegexFlag){
        var errorMsg = "Please Enter Mandatory Field";
        showError(errorMsg);
        return false;
     }
     return true;
  }
    return false;    
}

});
</script>

<div class="content-wrapper">
	<!--right section-->
	<input type="hidden" id="employeeId" />
	<input type="hidden" id="relocId" />
	<input type="hidden" id="date" />

	<div class="botMargin">
		<h1 class="fl">Timesheet Submission</h1>
		<div class="fr positionRel">
			<img src="resources/images/helpIcon.png" id="helpIcon" />
			<div class="helpContent" style="display: none;">
				<h3>To enter time sheet.</h3>
				<table>
					<tr>
						<td>
							<ol class="orderList">
								<li>Make sure you are using firefox and your browser is not
									idle for a long time.</li>
								<li>Click on Maintenance Tab/Add New Button</li>
								<li>Select Week Ending Date for the Timesheet Entry using
									Calendar provided.</li>
								<li>Once Week Ending Date (Saturday for that Week) is
									selected, use Add New button to insert timesheet rows for that
									weekâ€™s activities.</li>
								<li>Module is a free text field, but user can utilize this
									to enter Ticket #, Defect #, Task # or Release # based on
									activity chosen.</li>
								<li><font color="red">Save Button : This will allow
										user to save the time sheet and can update it later if needed
										and it will also avoid admin as well as manager to approve the
										time sheet.</font></li>
								<li><font color="red">Send For Approval Button :
										Once user clicks on Send For Approval Button,the time sheet is
										send to manager for approval and can not be updated.</font></li>
							</ol>
						</td>
					</tr>
				</table>
				<div class="closeHelp"></div>
				<img src="resources/images/arrowIcon.png" class="arrowIcon" />
			</div>
		</div>
	</div>
	<div class="clear"></div>

	<div class="breadcrums">
		<a class="breadcrumbslink" href="#">Employee Details</a> <img
			src="resources/images/imgBreadCrumsArrow.gif" width="10" height="11" />
		<strong>Page Name</strong>
	</div>
	<div class="tab_seaction">
		<ul class='tabs'>
			<li><a href='#tab1' id="tab1id">List</a></li>
			<li><a class="MaintenanceTab" id="MaintenanceTabInactive"
				href='#tab2'>Maintenance</a></li>

		</ul>

		<div id='tab1' class="tab_div">

			<div class="search_filter_outer ">
				<div
					class="search_filter search_filterLeft timesheetSub_search_filter">


					<div class="fr">
						&nbsp;&nbsp;
						<!-- <span id="addUpdate"> -->
						<a href="javascript:void(0);" class="blue_link" id="addUpdate">
							<img src="resources/images/addUser.gif" width="16" height="22" />
							Add Current/Past Week's Timesheet
						</a>

					</div>
					<div class="btnIconView TimesheetSubView">
						<a href="#" class="blue_link" id="view"> <img
							src="resources/images/view.png" width="14" height="14" /> View
							Timesheets
						</a>
					</div>

					<form id="frmSelectDt" name="frmSelectDt" method="post" action=""
						class="frmSelect">
						<div class="fl">
							Select Month : <input type="text" name="dateSearch"
								id="dateSearch" readonly="readonly"> &nbsp;
						</div>
					</form>
					<div class="clear"></div>
				</div>
			</div>
			<div class="tbl">
				<table>
					<tr>
						<td>&nbsp;&nbsp;</td>
					</tr>
				</table>
				<!--    //purva -->
				<div id="timesheetSubmissionTbl">
				<table width="100%"
					class="tablesorter dataTbl fl my_table positiondashboard-table white-sort-icons"
					cellspacing="1" id="userActivityTableId" cellpadding="2">
					<thead>

						<tr>
							<th align="center" valign="middle">Project</th>
							<th align="center" valign="middle">Week End Date</th>
							<th align="center" valign="middle">Total (Hrs)</th>
							<th align="center" valign="middle">Approve Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="userActivityView" items="${useractivityViews}"
							varStatus="theCount">
							<c:out value="${useractivityView.employeeId}" />
							<tr class="even" id="${theCount.index}">
								<td align="center" valign="middle" id="oldRow"
									onclick="openMaintainance(${userActivityView.employeeId},${userActivityView.resourceAllocationId},'<fmt:formatDate value="${userActivityView.weekStartDate}" type="date" pattern="<%=Constants.DATE_PATTERN %>"/>',this);">
									<a href="#">${userActivityView.projectName}</a>
								</td>
								<td align="center" valign="middle"><fmt:formatDate
										value="${userActivityView.weekEndDate}" type="date"
										pattern="<%=Constants.DATE_PATTERN_4 %>" /></td>
								<td align="center" valign="middle">${userActivityView.totalHours}</td>
								<td align="center" valign="middle"><c:if
										test="${fn:contains(userActivityView.status,'A')}">
										<!-- <img title="Approved" src="resources/images/approve.png" /> -->
										<i class="fa fa-check-circle approved" title="Approved"></i>
										<a href="#"
											onclick="addrow(${userActivityView.employeeId},${userActivityView.resourceAllocationId},'<fmt:formatDate value="${userActivityView.weekStartDate}" type="date" pattern="<%=Constants.DATE_PATTERN %>"/>');">
											<b> Copy this Timesheet</b>
										</a>
									</c:if> <c:if test="${fn:contains(userActivityView.status,'N')}">
										<!-- <img title="Not Submitted"
											src="resources/images/notsubmitted.png" /> -->
										<i class="fa fa-check-circle saved" title="Not submitted"></i>
										<a href="#"
											onclick="addrow(${userActivityView.employeeId},${userActivityView.resourceAllocationId},'<fmt:formatDate value="${userActivityView.weekStartDate}" type="date" pattern="<%=Constants.DATE_PATTERN %>"/>');">
											<b> Copy this Timesheet</b>
										</a>
									</c:if> <c:if test="${fn:contains(userActivityView.status,'P')}">
										<!-- <img title="Not Approved" src="resources/images/pending.png" /> -->
										<i class="fa fa-exclamation-circle review" title="Review Pending"></i>
										<a href="#"
											onclick="addrow(${userActivityView.employeeId},${userActivityView.resourceAllocationId},'<fmt:formatDate value="${userActivityView.weekStartDate}" type="date" pattern="<%=Constants.DATE_PATTERN %>"/>');">
											<b> Copy this Timesheet</b>
										</a>
									</c:if> <c:if test="${fn:contains(userActivityView.status,'R')}">
										<!-- <img title="Rejected"
											src="resources/images/reject_timesheet.png" />  -->
										<i class="fa fa-times-circle rejected" title="Rejected"></i>
											Timesheet Rejected
									</c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<div id='tab2' class="tab_div">
			<div class="search_filter_outer">
				<div class="search_filter cust_search_filter">
					<div class="">
					<div class="col-md-5">
						<form id="frmSelect" name="frmSelect" method="post" action=""
							class="frmSearch">
							<div class="stDtEnDt tsstDtEnDtWrapper">
								<div class="row">
								<div class="form-group col-md-6 pull-left">
									<label>Start Date:</label> 
									<input type="text" name="weekStartDate" value="" id="weekStartDate"
									size="35" maxlength="80" readonly="readonly" class="form-control" />
									
								</div>
								<div class="form-group col-md-6 pull-left">
									 <label>End Date:</label>
										<input type="text" name="weekEndDate" id="weekEndDate" size="35" maxlength="80"
									value="" readonly="readonly" class="form-control" />
								</div>
								</div>
							</div>
							<div class="botMargin" id="addNewDiv">
								<!-- id="addNewRow" -->
							 	<div class="clear">*To Submit /Save past or Future week timesheet. Please select date accordingly and clicks on 'Add New'</div>
							</div>
						</form>
					</div>

					<div class="col-md-7 pull-right">
						<div class="pull-left expand-toggle">
							<input type="radio"	name="expandRow" onclick="toggleRow(true)" id="expand_all" />
							<label for="expand_all">Expand All</label>
							
							<input type="radio"	name="expandRow" onclick="toggleRow(false)" id="collapse_all" checked/>
							<label for="collapse_all">Collapse All</label>
						</div>
						
						<div class="pull-right">
						
						<!-- submit btn -->
						<div class="pull-left" id="buttons">
							<!-- <input type="button"  value="Submit" onclick="document.getElementById('userActivityForm').submit()" /> -->

							<input type="button" id="saveValues" value="Save" name="saveValues" class="btn-primary_theme btn_theme" />
							<input type="button" id="submit"
							class="ui-button-disabled ui-state-disabled btn-primary_theme btn_theme"
							value="Send for Approvals" name="submit" disabled="true"  />
						</div>
						<div class="pull-left">
								<input type='button' id="copyTimesheet"
								value='Copy this Timesheet ' name='copy'
								class="ui-button ui-widget ui-state-default ui-corner-all ui-state-hover btn-primary_theme btn_theme" />
						</div>
						</div>
						<div class="btns pull-right clear">
							<a href="javascript:void(0);" class="btn-primary_theme btn_theme btn" onclick="createNewRow()" id="addNewRow">Add New</a> 
						</div>
						<!-- submit btn end -->
					</div>
				</div>
					<div class="clear"></div>
				</div>
			</div>
			<div class="dataTables_scroll">
				<div class="form" id="tHours">

					<div class="tbl">

						<form action="useractivitys" name="userActivityForm" method="post"
							id="userActivityForm" autocomplete="off">

							<input type="hidden" name="userAction" id="userAction"> <input
								type="hidden" name="weekStartDate" id="weekStartDateHidden">
							<input type="hidden" name="weekEndDate" id="weekEndDateHidden">

							<input type="hidden" name="employeeId" id="employeeIdHidden"
								value="${currentEmployeeId}"> <input type="hidden"
								name="submitStatus" id="submitStatusHidden">
							<table width="100%" class="tablesorter dataTbl dataTable"
								cellpadding="0" cellspacing="0" border="0" id="tblActivity">
								<thead>
									<tr>
										<th align="center" valign="middle" width="80px;">Status</th>
										<th align="center" valign="middle">Project</th>
										<th align="center" valign="middle">Activity</th>
										<th align="center" valign="middle" width="100px;">Module</th>
										<th align="center" valign="middle" width="100px;">SubModule</th>
										<th align="center" valign="middle" width="100px;">Ticket No.</th>										
										<th align="center" valign="middle" width="100px;">Priority</th>
										<th align="center" valign="middle" width="100px;">Status</th>
											<th align="center" valign="middle" width="100px;">Action</th>
										<!-- <th colspan="7" align="center" valign="middle" width="81%">Week</th>
										<th width="2%" align="center" valign="middle">&nbsp;</th>
										<th width="2%" align="center" valign="middle">&nbsp;</th> -->
									</tr>
									<!-- <tr>
										<th align="center" valign="middle">Sunday</th>
										<th align="center" valign="middle">Monday</th>
										<th align="center" valign="middle">Tuesday</th>
										<th align="center" valign="middle">Wednesday</th>
										<th align="center" valign="middle">Thursday</th>
										<th align="center" valign="middle">Friday</th>
										<th align="center" valign="middle">Saturday</th>

									</tr> -->
									<!-- <tr id="dates">
									</tr> -->
								</thead>
								<tbody>

								</tbody>
							</table>
							<table width="100%" class="tablesorter dataTbl dataTable"
							cellpadding="0" cellspacing="0" border="0" id="total_hrs">
								<!-- <thead>
									<tr>
										<th align="center" valign="middle">Days</th>
										<th align="center" valign="middle">Sunday</th>
										<th align="center" valign="middle">Monday</th>
										<th align="center" valign="middle">Tuesday</th>
										<th align="center" valign="middle">Wednesday</th>
										<th align="center" valign="middle">Thursday</th>
										<th align="center" valign="middle">Friday</th>
										<th align="center" valign="middle">Saturday</th>
										<th align="center" valign="middle">Weekly Hours</th>
									</tr>	
								</thead> -->
								<tfoot>
									<tr>
										<th align="left">Total Hours : &nbsp;</th>
										<th align="center">Sunday<input type="text" value=""
											class="small" id="sunTtl" readonly="readonly" /></th>
										<th align="center">Monday<input type="text" value=""
											class="small" id="monTtl" readonly="readonly" /></th>
										<th align="center">Tuesday<input type="text" value=""
											class="small" id="tueTtl" readonly="readonly" /></th>
										<th align="center">Wednesday<input type="text" value=""
											class="small" id="wedTtl" readonly="readonly" /></th>
										<th align="center">Thursday<input type="text" value=""
											class="small" id="thuTtl" readonly="readonly" /></th>
										<th align="center">Friday<input type="text" value=""
											class="small" id="friTtl" readonly="readonly" /></th>
										<th align="center">Saturday<input type="text" value=""
											class="small" id="satTtl" readonly="readonly" /></th>
										<th align="center">Weekly Hours<input type="text" value=""
											class="weekTtlSmall" id="weekHrs" readonly="readonly" /></th>
									</tr>
									<!-- 
									<tr>
										<th align="right">Total Weekly Hours : &nbsp;</th>
										<th align="center" colspan="6"></th>

										<th align="left"></th>
										<th align="left" class="weekTtlSmall">&nbsp;</th>
									</tr> -->
								</tfoot>
							</table>
							<!-- added by pratyoosh -->
							<div>
								<ul class='tabs'>
									<br>
									<li><span style="color: #F80000;">Rule:</span> </br> <span
										style="color: #0029FF;">'Send to Approval' button will
											get enable when all the working week days (Monday to Friday)
											are filled. Please fill “0” hrs against activity ‘Non Prod –
											Full day leave’ or ‘Non-Prod half day leave’, if on
											holiday/leave. </span></li>
								</ul>
							</div>
							<!-- added by pratyoosh -->
						</form>
					</div>
				</div>
				<div class="clear"></div>
				
				
				<div id="NoAllocMessage" class="NoAllocMessage">Project is not
					allocated. Please contact your IRM/SRM.</div>
			</div>
		</div>
		<input type="hidden" name="projectId" value="">

	</div>
	<!--right section-->
</div>
<div id="comment" align="center" name="comment" style="display: none;">
	<table>
		<tr>
			<td align="left">Comments:-</td>

		</tr>
		<tr>
			<td align="center"><textarea rows="4" cols="30"
					id=rejectionRemarks readonly="readonly"></textarea></td>

		</tr>
		<tr>
		</tr>
		</td>
		</tr>
	</table>
</div>

<div id="dialog" style="display: none;">
	Please select date/weekend date for which timesheet is to be copied 
			<input type='text' id='inputdate' class='fromdatepicker' readonly="readonly"
		style="margin-right: 5px; position: relative; top: 2px;" />

	


	<div style="text-align: center; margin-top: 10px;">
		<input type="button" value="ok" onclick="showTimesheet()" />
	</div>
</div>
<div id="div"></div>

<div id="activityStatusDiv"></div>
<script>
$(document).ready(function(){
	 	//$("#inputdate").datepicker({changeMonth: true,changeYear: true});
	    $("#ui-datepicker-div").hide();
	    $(document).on('change','[id^=activityId]',function(e) { 
		var statusValue;
		var selectedElemId = '#' + e.target.id;
		var status= $(selectedElemId).val().split('_')[2];
		
		if(status=="true" || status ==true){
			statusValue="Productive";
		}else{
			statusValue="Non Productive";
		}
		  var id=this.id;
		var a=id.substring(10,id.length);
		
	      if(id=="activityId0"){
	    	  document.getElementById("activityDesc0").innerHTML =statusValue;
	      }else{
	    	  document.getElementById("activityDesc"+a).innerHTML =statusValue;
	      }
	     
		}).trigger('change');  
	    	
		$('#tab1id').click(function(){
			toggleRow(false);
			$('#collapse_all').prop('checked',true);
		});
	       	
	});
	
function showTimesheet(){

	var employeeId= ${currentEmployeeId};

var date=$("#inputdate").val();


	$('#expand_all').prop('checked',true);
	
//$.unblockUI();
	if(date!=null && date!='' && date!=""){
		
		getWeekDates(date);
	
		
		 $.ajax({
			type: 'GET',
		    url: 'useractivitys/',
		    data: {
		    	find:"ByWeekStartDateBetweenAndEmployeeId_json",
		        "minWeekStartDate":weekStart, 
		        "maxWeekStartDate":weekEnd, 
		        "employeeId": employeeId
		    },
		    contentType: "application/json; charset=utf-8",
		    async:true,
		    success: function(data) { 
		    	if(!data.length==0){ //if timsheet for current week is filled
		    		var obj = data[0];
		    
		    	  
		    		if(obj.approveStatus=="P" || obj.approveStatus=="A") {
		    			// if timsheet for current week is pending or approved 
		    			// if timsheet for current week is pending or approved 
				    	
		    			showError("Cannot Copy!! <br>  timesheet for  this week is already submitted");  stopProgress();
		   	    
		    		
		    		}else if (obj.approveStatus=="R"){
		    			showError("Cannot Copy!! <br> Your  timesheet for  this week is already Rejected, Please first update and save it");  stopProgress();
		    		
		    		}
						else{	 $('#dialog').dialog('close');
						openMaintainance($("#employeeId").val(),$("#relocId").val(),$("#date").val(),this);
						$('.fancybox-overlay').remove();
						$('#expand_all').prop('checked', true);
						}
		    		
		    		
		    	}else{ 	
				openMaintainance($("#employeeId").val(),$("#relocId").val(),$("#date").val(),this);
					$('#expand_all').prop('checked', true);
				}
		    }
		    	,error:function(){
		    		ShowError("Some error occured");
		    	}
		    	
		    	});
	}
		    	else{
		    		
		    	
		showError("select date");
		stopProgress();
	}
}


/* addRow function STARTS... added by purva for #3088 */
function addrow(employeeId,relocId,date){ // tke id of row which is clicked for copy 
	$('#employeeId').val(employeeId);
	$('#relocId').val(relocId);
	$('#date').val(date);

	//  $("p").append("<input type='hidden'  id='employeeId' value="+employeeId+" />");
	//  $("p").append("<input type='hidden'  id='relocId' value="+relocId+"  />");
	//  $("p").append("<input type='hidden'  id='date' value="+date+"  />");
	/*  $.blockUI({}) */
	
	$("body").append("<div class='fancybox-overlay fancybox-overlay-fixed' style='display:block;z-index: 1000 !important;'></div>");
	 
	$( "#dialog" ).dialog({
		close: function(event, ui) {
	        //$(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
	        
			 	$('body').find('.fancybox-overlay').remove();
	    }	
	});
	
	 $("#ui-datepicker-div").hide();
	$("#inputdate").blur(); 
	
	$("#inputdate").datepicker({
		changeMonth: true,changeYear: true,
		showOn: "button",
        buttonImage: "resources/images/calendar.gif",
        buttonImageOnly: true
	});
	
	//$("p").append("<b>Appended text</b>");
}

function isEmpty(obj) {
	return !Object.keys(obj).length > 0;
}

/* addRow function ENDS... added by purva for #3088  */

			
	//added for US3088 by purva starts		
			$('#copyTimesheet').on('click', function() {
				  var reloccHidden= $("#reloccHidden").val();
				  var weekStartHidden= $("#weekStartDateHidden").val();
				addrow(${currentEmployeeId},reloccHidden,weekStartHidden);
			
			});
			
			//added for US3088 by purva ends	
 
 
 $('body').on("click",'#clearDate',function(){ 
	 
    $("#weekEndDate").datepicker('hide');
 $("#dateSearch").datepicker("hide");
 //$('#weekEndDate').datepicker('setDate', null);
 $('#dateSearch').datepicker('setDate', null);

    }); 
</script>

<!--START: //alert: Added by Pratyoosh Tripathi -->
<div class="notification-bar">
	<!-- <div class="close closeIconPosition">close</div> -->
	<span class="toast-close close"><span title="Close Notification">&nbsp;&nbsp;</span></span>
	<!-- <div class="notification-text">Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.Hello World, you can hide this notification by clicking the close button.</div> -->
	<sec:authorize
		access="hasAnyRole('ROLE_MANAGER','ROLE_DEL_MANAGER','ROLE_BG_ADMIN','ROLE_ADMIN','ROLE_USER','ROLE_HR')">
		<div class="notification-text">You can copy your earlier
			timesheet using mentioned "Copy" link.</div>


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
	z-index: 9;
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

/*updated table css-23-5-19*/
.dataTables_paginate.paging_full_numbers {
	margin-bottom: 8px;
	float: left !important;
	margin-left: 165px !important;
	margin-top: 9px;
}

.paging_full_numbers a {
	margin: 0 !important;
	padding: 10px 14px !important;
	color: #999 !important;
	background-color: #fff;
}

.paging_full_numbers a.paginate_active {
	color: #fff !important;
	background-color: var(- -nav-active) !important;
	font-weight: bold;
	border-radius: 50%;
	border: 1px solid var(- -nav-active);
}

.paging_full_numbers a.paginate_button {
	background-color: #fff !important;
}
.bottom {
	padding: 15px;
	position: relative;
}
#timesheetSubmissionTbl .dataTables_wrapper .dataTables_info {
	width: 20%;
	margin-top: 9px;
}
div.dataTables_wrapper {
	background: #fff;
}
#userActivityTableId_length label select {
	padding: 5px;
	width: 60px;
	border-radius: 50px;
}
.dataTables_filter {
    left: 138px;
    top: 7px; 
}
.dataTables_filter input {
    border-radius: 50px !important;
}
.input-group {
	width: 100%;
}
#tblActivity, #total_hrs {
	table-layout: fixed;
	margin: 0;
}
#tblActivity input, #tblActivity select, #total_hrs input {
	width: 100% !important;
}
.action i {
    padding: 0 6px !important;
    margin: 0 !important;
	cursor: pointer;
	width: inherit !important;
}
#tblActivity thead th,  #total_hrs tfoot th{
	background: #fff;
	border: 0;
	color: #000;
	border-radius: 0px;
	padding: 7px;
	border-bottom: 2px solid #ddd;
	border-top: 1px solid #ddd;
	text-align: center;
}
#total_hrs tfoot th {
	background: var(--brand-bg) !important;
	color: var(--brand-text);
	
}
#total_hrs tfoot th input {
	border: 0;
	box-shadow: none;
	color: var(--brand-text) !important;
}
#tblActivity > tbody > tr.even {
	background: transparent;
}
#tblActivity > tbody > tr.even td {
	border: 0;
	border-bottom: 2px solid #ddd;
	border-radius: 0;
	padding: 7px;
}
.expandable_row {
	background: #ebebeb;
}
.comments_textarea {
    height: 28px !important;
    margin: 0 !important;
}
.input_log {
    text-align: center;
}
.expandable_row td:first-child {
    border-right: 1px solid #ddd !important;
}
#tblActivity input:read-only, #total_hrs input:read-only {
    background-color: transparent;
    color: graytext;
}
#total_hrs input {
    text-align: center;
}
#tab2 .ui-datepicker-trigger {
	bottom: 5px;
    position: absolute;
	right: -10px;
	cursor: pointer;
}
.search_filter {
	top:0;
	width: 100%;
}
.cust_search_filter {
	margin-left: 0;
}
.stDtEnDt label {
	margin: 0;
}
.btn_theme {
    text-shadow: none !important;
    border-radius: 4px;
	box-shadow: none !important;
	font-weight: 400;	
	margin: 0 4px;
}
.btn-primary_theme {
	background: var(--bgColor) !important;
    color: var(--baseColor) !important;
    border: 1px solid var(--bgColor) !important;
}
.btn-primary_outline {
	background: #fff !important;
    color: var(--bgColor) !important;
    border: 1px solid var(--bgColor) !important;
}
.btn-primary_outline:hover {
	background: var(--bgColor) !important;
    color: var(--baseColor) !important;
    border: 1px solid var(--bgColor) !important;
}
.expand-toggle {
    display: table;
    background: #fff;
    border-radius: 4px;
    border: 1px solid #ddd;
}
.expand-toggle > label {
	width: 100px;
    /* background: #eee; */
    margin: 0;
    border: 0;
    padding: 6px 7px;
    border-radius: 0;
	text-align: center;
	font-weight: normal;
	cursor: pointer;	
}
.expand-toggle input {
    position: absolute;
    opacity: 0 !important;
}
.expand-toggle input:checked + label {
    background: #565656;
    color: #fff;
}
#addNewDiv {
    color: var(--nav-active);
}
input[type=button] {
	cursor: pointer;
}
.ui-state-disabled {
	opacity: .5;
}
.active.comments_textarea {
	-moz-transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(82, 168, 236, 0.6);
    -moz-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(82, 168, 236, 0.6);
    -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(82, 168, 236, 0.6);
    border-color: #3c8dbc !important;
}
.saved,.review,.rejected,.approved {
	font-size: 20px;
	vertical-align: middle;
}
.saved {
	color: #afafaf;
}
.review {
	color: #f29f29;
}
.rejected {
	color: #c02900;
}
.approved {
	color: #149611;
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

<!--END: //alert: Added by Pratyoosh Tripathi -->