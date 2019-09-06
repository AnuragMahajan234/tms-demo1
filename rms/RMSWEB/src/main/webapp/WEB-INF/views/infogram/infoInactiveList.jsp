<%@ page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<spring:message code="application_js_version" var="app_js_ver" htmlEscape="false" />
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
<spring:url
	value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url
	value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />
<spring:url value="/resources/js-framework/blockUI/jquery.blockUI.js?ver=${app_js_ver}"	var="blockUI" />
<spring:url	value="/resources/js/moment.min.js?ver=${app_js_ver}" var="moment_js" />
<script src="${moment_js}" type="text/javascript"></script>	
<script src="${blockUI}" type="text/javascript"></script>

<style type="text/css" title="currentStyle">

#info_table {
    overflow-x: scroll;
    max-width: 100%;
    white-space: nowrap;
    cursor: pointer;
    min-height: 170px;
	max-height:370px;
	margin-top:25px;
}
.dataTables_info {
    padding: inherit;
}
.dataTables_filter {
     top: 0px;
}

.dropdown-content {
    display: none;
    position: absolute;
    z-index: 1;
    top: -5px;
    left: 105%;
    box-shadow: 5px 10px 8px #888888;
    border-style: double;
}
#info_table tbody tr:nth-child(10) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}

#info_table tbody tr:nth-child(9) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}

#info_table tbody tr:nth-child(8) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important;
}
#info_table tbody tr:nth-child(25) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important;
}

#info_table tbody tr:nth-child(24) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important;
}

#info_table tbody tr:nth-child(23) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important;
}

#info_table tbody tr:nth-child(22) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important;
}
#info_table tbody tr:nth-child(49) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important;
}

#info_table tbody tr:nth-child(50) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important;
}
#info_table tbody tr:nth-child(99) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important;
	
}

#info_table tbody tr:nth-child(100) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}

#info_table .hover-area { 
    width: 20px;
    display: inline-block;
}
#info_table tr {
    height: 34px;
}

.blue-link {
    color: #062bb7!important;
    font-weight: normal;
    text-decoration: underline!important;
}
.borderHover:hover {
    background: #f2f2f2;
}
	/* td.stop_click.action-dropdown.dropdown.dropbtn.stop_click.align-center.action-dropdown.dropdown :hover {
	    background: #fff;
	} */
	i.fa.fa-ellipsis-v :hover {
		background: #fff;
		color: #fff;
	}
	/* .dropdown-content a:hover {background-color: #ddd;} */
	.dropdown:hover .dropdown-content {
		display: block;
	}
	
	.dropdown:hover .dropbtn {
		background-color: #3e8e41;
	}
	
	.table#records_tableId {
		min-height: 700px;
	}
	
	#records_tableId tbody tr:nth-child(10) td.dropdowns .dropdown-content {
		top: auto !important;
		bottom: 0px !important
	}
	
	#records_tableId tbody tr:nth-child(9) td.dropdowns .dropdown-content {
		top: auto !important;
		bottom: 0px !important
	}
	
	#records_tableId tbody tr:nth-child(8) td.dropdowns .dropdown-content {
		top: auto !important;
		bottom: 0px !important
	}
	
	#records_tableId tbody tr:nth-child(8) td.dropdowns .dropdown-content {
		top: auto !important;
		bottom: 0px !important
	}
	
	#records_tableId tbody tr:nth-child(7) td.dropdowns .dropdown-content {
		top: auto !important;
		bottom: 0px !important
	}
	
	.dropdown-menu.dropdown-content {
		border-color: #F4F4F4;
	}
	.dropdown-menu{ 
	   min-width: 108px !important;
	 }
	.dropdown.dropleft.dropDownHover:hover {
		border-radius: 50%;
		background: #6e9ece;
		color: white;
		padding: 4px 1px 2px 1px;
	}
	.dataTables_scrollBody {
	    height: 751px !important;
	}

	a.dropdown-item {
		color: black !important;
		font-size: 0.9em;
		line-height: 22px;
		font-size: 12px;
	}
	.my_submit_btn {
		min-width: 16px !important;
		border-radius: 50px !important;
		width: 10px !important;
		padding: 0px !important;
	}
	
	
	
	i.fa.fa-user-plus {
	margin: 4px 10px 4px 10px;;
		color: #333333;
		font-size: 15px;
		
	}
	i.fa.fa-times-circle-o {
 margin: 4px 10px 4px 10px;;
		color: #333333;
		font-size: 15px;
		
	}
	i.fa.fa-pencil-square-o {
	margin: 4px 10px 4px 10px;;
		color: #333333;
		
		
		 font-size: 15px;
	}
			
			
			.dropdown.dropleft.dropDownHover:hover {
			
			width :26px !important;
			height :26px !important;
			}												
						
						   table.tablesorter tr td a {
    color: black;
    text-decoration: none;
    font-size: 12px;
}

.ui-datepicker #clearDate{
display:none !important;
}				

.create-employee-modal {
text-align:left !important;
}

.employee-edit-detail .form-group.col-md-3.autoSelect {
display :grid;
}
.employee-edit-detail .form-group.col-md-3.autoSelect input {
text-align: left !important;
}
.employee-edit-detail .form-group.col-md-3.autoSelect label {
width:100%;
}


/* .employee-edit-detail .form-group.col-md-3.validateMe .positionRel .ui-combobox {
width: 96% !important;
}

.employee-edit-detail .form-group.col-md-3.validateMe .positionRel .ui-combobox input {
padding : 6px !important;
} */

/* .ui-combobox {
width: 96% !important;
}

.ui-combobox input {
padding : 6px !important;
}  */

.required {display:none;}

       .datepickeremp .positionRel .input-group {width : 96%;    margin-left: 0;}
       .datepickeremp .positionRel .input-group label {border-top-right-radius: 5px;
    border-bottom-right-radius: 5px;}
    
    .create-employee-modal .modal-header {
    border-bottom-color: #9E9E9E !important;
}

.create-employee-modal .modal-body .modal-footer {
    border-top-color: #9E9E9E !important;
}

.create-employee-modal .modal-header .modal-title {
    color: #001a31 !important;
    font-weight: 600;
}
.listInactiveProcessStatus select{
	width :100% !important;
}
</style>
<script type="text/javascript" charset="utf-8">

const FAILURE = "FAILURE";
const SUCCESS = "SUCCESS";

$( function() {
   $( "#relievingDate_td #relievingDate" ).datepicker({
    		autoclose:true,
		 dateFormat: "dd-M-yy" ,
		 changeMonth: true,
		 changeYear: true,
		 clearBtn: false, 
		 onClose: function(selectedDate) {
			 
			 var e = $.Event("keyup");
			 e.which = 13;
			 $('#relievingDate').trigger(e); 
		      }
		 
		});
   
   $( "#resignedDate_td #resignedDate" ).datepicker({
		autoclose:true,
	 dateFormat: "dd-M-yy" ,
	 changeMonth: true,
	 changeYear: true,
 
	 onClose: function(selectedDate) {
		 
		 var e = $.Event("keyup");
		 e.which = 13;
		 $('#resignedDate').trigger(e);
		// $("#resignedDate").blur();
	      }
	 
	});
  } );
	$(document).ready(function() {
						 
						/*Start custom Loader*/
				    	function startProgress(){
							  $.blockUI({ message: '</br></br><div class="circle"></div><div class="circle1"></div><center>Just a moment</center>' }); 
						  }
						  
						 function stopProgress(){
							 $.unblockUI();	
						 }
						 stopProgress();
						/*End custom Loader*/
						
						oTable = $('#info_table').dataTable( {
				        "bProcessing": true,
				        "bServerSide": true,
				         /* "scrollY": 200, */
				      	"sPaginationType" : "full_numbers",
				        "scrollY": 200,
				        "scrollX": true,
				        "bSortCellsTop": true,
				        "sDom": 'ltipr',
				        "sAjaxSource": 'getinfograminactiveresources',
	        	        "aoColumns": [
	                	 {mData:'id',mRender:function ( id, type, row )  {
	                		 if(row.processStatus!=SUCCESS)
            				 	return prepareAction(id);
            				  else
           					 	 return "";
           					 
	 	        	      },"bSortable": false },
	                	 {mData:'employeeId',sDefaultContent: "NA" },
	                	 {mData:'employeeName', sDefaultContent: "NA" },
	                	 /* {mData:'processStatus', sDefaultContent: "NA","bSortable": false }, */
	                	 {mData:'processStatus', sDefaultContent: "NA",  mRender:function ( processStatus, type, row )  {
	                			var dtData="";
	                			var failureReason =row.failureReason;

	                			if(processStatus==FAILURE){	 

 	 								dtData=dtData + '<div ><a class="blue-link" data-toggle="modal" data-target="#failModal" onClick="showFailReason(`'+failureReason+'`)">Failure</a>';
 	 								return dtData;
 	 							}
 	 						  return processStatus;
 	 						  }
	                	 },
	                	 {mData:'resignedDate',sDefaultContent: "NA" , mRender:function ( resignedDate, type, row )  {
	                		 					if(resignedDate == "NA"){
	                		 						return "NA";
	                		 					}
	                		 					var formattedDate = moment(resignedDate).format('DD-MMM-YYYY');	   
	                		 					 return formattedDate;
	                	 						  }},
	                	{mData:'releasedDate',sDefaultContent: "NA" , mRender:function ( releasedDate, type, row )  {
	                				if(releasedDate == "NA"){
		 								return "NA";
		 							}
				                			var formattedDate = moment(releasedDate).format('DD-MMM-YYYY');	   
					 					 return formattedDate;
	 	                	}}
	 	                	
	                	
	                	 ]
	    			} );
						
						 $("thead input").keyup( function(i){
						    	
						    	if(i.which==13||(i.which==8 && this.value.length==0))
						    		{
						    		
						    			oTable.fnFilter( this.value, oTable.oApi._fnVisibleToColumnIndex(oTable.fnSettings(), $("thead input").index(this) ) );
						    		}
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
						
				
		});
	 $(document.body).on('change','#listInactiveProcessStatus',function(){
	
		$("#search_process_status").text(this.value);
	
		oTable.fnFilter( this.value, oTable.oApi._fnVisibleToColumnIndex(oTable.fnSettings(), $("#search_process_status").closest('td').index()));
		}); 
	 
	 function showFailReason(failureReason)
	 {
			document.getElementById('failId').innerHTML=failureReason;
	 }
	 
	function getEmployee(id){
		var employee;
		var employees=[];
		
		<c:forEach var="info" varStatus="infoState"	items="${infogramInactiveList}">
			employees.push({'eId':'${info.id}','employeeName':'${info.employeeName}','employeeId':'${info.employeeId}','resignedDate':'${info.resignedDate}','releasedDate':'${info.releasedDate}'
			
			});
		</c:forEach>
		
		for(i=0;i<=employees.length;i++){
			employee = employees[i];
			if(employee.eId == id){
						break;
			}
		}
		
		return employee;
	}	
	

	 function prepareAction(eid)
	 {
		 var dtData="";
		 
		 dtData= '<span class="dropdown dropleft dropDownHover hover-area" id="dropDownOpen">  <i class="fa fa-ellipsis-v" style="font-size: 15px;"></i>';
		 dtData= dtData + '<div class="dropdown-menu dropdown-content">  <div>';
		

			dtData=dtData + '<div class="borderHover"><a class="dropdown-item" onClick="moveEmployee('+eid+')"><i class="fa fa-user-plus my_submit_btn"	></i>Update</a></div>';

			/* dtData=dtData + '<div class="borderHover"><a class="dropdown-item" data-toggle="modal" data-target="#employeeModal"	onClick="editEmployee('+eid+')">';
			dtData=dtData + '<i	class="fa fa-pencil-square-o my_submit_btn"></i>Edit Employee</a>'; */
			dtData=dtData+ '<div class="borderHover"><a class="dropdown-item" onClick="discardEmployee('+eid+')"><i class="fa fa-times-circle-o my_submit_btn"></i>Discard</a></div></span>';
         
   	
		return dtData;
		 
	 }
	
 
	 function editEmployee(id){
		
		var employee = getEmployee(id);
		document.getElementById('updatedEmpId').value=employee.eId;
		document.getElementById("employeeId").value=employee.employeeId;
		document.getElementById("employeeName").value=employee.employeeName;
		/* document.getElementById("statusId").value=employee.empStatus; */
		/* $("#releaseDate").datepicker().datepicker("setDate", new Date((employee.releasedDate).replace("IST",""))) */;
		$("#resingeDate").datepicker().datepicker("setDate", new Date((employee.resignedDate).replace("IST","")));
		
	}  
	function moveEmployee(id){
		
		startProgress();
	    /* $.post("updateexitdetails/"+id, function(data, status){
	    	
	    	showSuccess("Employeee Moved in RMS successfully !");
	    	stopProgress();
	    	oTable.fnClearTable();
	    	}); */
		$.ajax({
			type : 'POST',
			url : 'updateexitdetails/' + id,
			dataType : 'text json',
			headers : {
				'Accept' : 'application/json'
			},

			success : function(response) {
				if (null != response) {
					showSuccess("Employeee Moved in RMS successfully !" + " "
							+ response.errMsg);
				} else {
					showError("Something happend wrong!! ");
				}
				stopProgress();
				oTable.fnClearTable();
			},
			error : function(response) {
				stopProgress();
				if (null != response) {
					showError(((JSON.parse(response.responseText))['errMsg']));
				} else {
					showError("Something happend wrong!! ");
				}
				oTable.fnClearTable();
			},
		});
	}
	
	function discardEmployee(id){
		startProgress();
		$.post("discardinactiveresource/"+id, function(data, status){
			showSuccess("Employeee discarded in RMS successfully !");
			stopProgress();
			//window.location.href="getinfogramainactiveresources";
			oTable.fnClearTable();
		});
	}
	
	function updateEmployee()
	{
		
		var id=document.getElementById('updatedEmpId').value;
		
		var employeeData = [];
		var employees = getEmployee(id); 
		var name =document.getElementById("employeeName").value;
		var releasedDate= $('#releasedDate').val();
		var resingedate= $('#resingedate').val();
	/* 	var statusId = document.getElementById("statusId").value; */
		
		employeeData.push({'eId':employees.eId,'employeeName':name,'employeeId':employees.employeeId,
			'releasedDate':releasedDate,'resingedate':resingedate
		}); 
		  $.ajax({
				type: 'POST',
		        url: 'saveeditedinfogramactiveresource',
		       // contentType: 'application/json',
		        dataType: 'json',
		        async:false,
		        processData: false,
		     	data: JSON.stringify(employeeData),
		     	success: function(response) { 			     			
		     		
		     		showSuccess("Employeee updated in RMS successfully !");
					setTimeout(function() {	location.reload();}, 1000);
		     			  			 
		     	},
		     	 error:function(data){
		     		
		     		 showError("Some thing went wrong !");
		     		 
			    }   	
		 	});  
		
		
		
	}
	
	
	
</script><script>
	var data = {
		getCostRowId : function(arg) {
			return $("table#addPoTbl tbody").find("tr").length;
		}
	}

	$.views.helpers({
		GetRowId : data.getCostRowId
	});
	$.templates("poTableRowsTempl", {
		markup : "#poTableRows"
	});
	$.templates("poTableRowsWithValuesTempl", {
		markup : "#poTableRowsWithValues"

	});
</script>
<div class="content-wrapper">
    <!--right section-->
    <div class="botMargin">
        <h1>Infogram Inactive Employee Details</h1></div>
    <div class="tab_section">
        <ul class='tabs'>
            <li>
                <a href='#tab1'>List</a>
            </li>
        </ul>
        <div id='tab1' class="tab_div">
             <div class="search_filter_outer">
                <div class="search_filter search_filterLeft"></div>
            </div> 
            <div class="tbl">
                <table class="dataTbl display tablesorter addNewRow alignCenter" id="info_table">
                    <thead>
                     <tr> <td colspan="6" align="right"> <a href="infogramInActiveResourceReport" class="blue_link" id="exportToExcel"><i class="fa fa-file-excel-o" style="font-size: 15px"></i> <b> Export To Excel </b></a></td></tr>
                        <tr>
                            <!-- <th align="center" valign="middle">S. No.</th> -->
                            <th align="center" valign="middle">Action</th>
                            <th align="center" valign="middle">EMP Id</th>
                            <th align="center" valign="middle">Emp Name</th>
                            <th align="center" valign="middle">Process Status</th>
                            <th align="center" valign="middle">Resigned Date</th>
                            <th align="center" valign="middle">Relieving Date</th>
                        </tr>
                         <tr class="">
					  		<td><input type="text" name="search_id"	placeholder="Emp 123" class="search_init" disabled="disabled" style="display: none"/></td>
							<td><input type="text" name="search_empID"	placeholder="Emp ID" class="search_init" /></td>
							<td><input type="text" name="search_empName" placeholder="Emp Name" class="search_init" /></td>
							<td style="display:none"><input id="search_process_status" type="text" name="search_pStatus" class="search_init" disabled="disabled" hidden=true/></td>
							<td><select id="listInactiveProcessStatus" name="search_p1Status" style="width:100% !important">
								<option value="" selected="selected">All</option>
								<option value="DISCARD">Discard</option>
								<option value="SUCCESS">Success</option>
								<option value="Pending">Pending</option>
								<option value="FAILURE">Failure</option>
							</select>
							</td>
							<td id="resignedDate_td"> <input id ="resignedDate" type="text" name="search_resignedDate" placeholder="Resigned Date"	class="search_init" readonly="readonly"/> </td> 
						    <td id="relievingDate_td"> <input id="relievingDate" type="text" name="search_relievingDate" placeholder="Relieving Date"	 class="search_init" readonly="readonly"/> </td> 
                    </thead>
                   <%--  <tbody>
                        <c:forEach var="info" varStatus="infoState" items="${infogramInactiveList}">
                            <tr id="${info.id}">
                                       <td class="stop_click action-dropdown dropbtn dropdowns">
                                        <div class="dropdown dropleft dropDownHover" id="dropDownOpen">
                                            <i class="fa fa-ellipsis-v" style="font-size: 15px;" class="btn dropdown-toggle"></i>
                                            <div class="dropdown-menu dropdown-content">
                                                <div class="borderHover">
                                                    <a class="dropdown-item" onClick="moveEmployee('${info.id}')"> <i class="fa fa-user-plus my_submit_btn" href="javascript:void(0)"></i>Update Employee
                                                    </a>
                                                 </div>
                                                <div class="borderHover">
                                                    <a class="dropdown-item" onClick="discardEmployee('${info.id}')"><i class="fa fa-times-circle-o my_submit_btn"></i>Discard Employee
                                                    </a>
                                                </div>
                                                <div class="borderHover">
                                                    <a class="dropdown-item" data-toggle="modal" data-target="#employeeModal" onClick="editEmployee('${info.id}')">
                                                        <i class="fa fa-pencil-square-o my_submit_btn"></i>Edit Employee
                                                    </a>
                                                    </br>
                                                </div>
                                            </div>
                                        </div>
                                     </td>
                                    <td align="center" valign="middle">${info.employeeId}</td>
                                    <td align="center" valign="middle">${info.employeeName}</td>
                                    <td align="center" valign="middle">${info.processStatus}</td>
                                    <td align="center" valign="middle">
                                    <fmt:formatDate type="date" pattern="dd-MMM-yyyy" value="${info.resignedDate}" />
                                    </td>
                                    <td align="center" valign="middle">
                                   	 <fmt:formatDate type="date" pattern="dd-MMM-yyyy" value="${info.releasedDate}" />
                                    </td>
                            </tr>
                        </c:forEach>
                    </tbody>
  --%>               </table>
                </div>
                <!-- failModal Start -->
			<div class="modal fade" id="failModal" role="dialog">
				<div class="modal-dialog">

					<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Failure Reason</h4>
						</div>
						<div class="modal-body">
							<p id="failId">Some text in the modal.</p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
					</div>

				</div>
			</div>
			<!-- failModal End -->
			<!-- Modal -->
                                        <div id="employeeModal" class="modal fade" role="dialog">
                                            <div class="modal-dialog modal-lg">
                                                <!-- Modal content-->
                                                <div class="modal-content create-employee-modal">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                        <h4 class="modal-title">Edit Employee</h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="row ">
                                                            <div class="form-row employee-edit-detail col-sm-12">
                                                                <div class="form-group col-md-3 autoSelect">
                                                                    <label for="requestorGradeSelect">
                                                                        <span class="required">*</span>Emp Id
                                                                    </label>
                                                                    <input id="employeeId" type="text" value="" class="form-control">
                                                                </div>
                                                                <div class="form-group col-md-3 autoSelect">
                                                                    <label for="requestorGradeSelect">
                                                                        <span class="required">*</span>Emp Name
                                                                    </label>
                                                                    <input id="employeeName" type="text" value="" class="form-control">
                                                                    <input name="updatedEmpId" id="updatedEmpId" type="hidden" />
                                                                </div>
                                                                <!--  <div class="form-group col-md-3 autoSelect"><label for="requestorGradeSelect"><span class="required">*</span>Status</label><input id="statusId" type="text" value=""  class="form-control"></div> -->
                                                                <div class="form-group col-md-3 validateMe datepickeremp">
                                                                    <label for="inputPassword4">
                                                                        <span class="required">*</span>Resigned Date
                                                                    </label>
                                                                    <div class="positionRel">
                                                                        <div class='input-group'>
                                                                            <input type='text' value="" id="resingedate" autocomplete="off" class="form-control" />
                                                                            <label class="input-group-addon" for="requestedDate1">
                                                                                <span class="glyphicon glyphicon-calendar"></span>
                                                                            </label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-row col-sm-12 employee-edit-detail ">
                                                                <div class="form-group col-md-3 validateMe datepickeremp">
                                                                    <label for="inputPassword4">
                                                                        <span class="required">*</span>Relieving Date
                                                                    </label>
                                                                    <div class="positionRel">
                                                                        <div class='input-group'>
                                                                            <input type='text' value="" id="releasedate" autocomplete="off" class="form-control" />
                                                                            <label class="input-group-addon" for="requestedDate1">
                                                                                <span class="glyphicon glyphicon-calendar"></span>
                                                                            </label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" data-dismiss="modal" class="btn">Cancel</button>
                                                            <button id="updateBtnId" type="button" data-dismiss="modal" onClick="updateEmployee()" class="btn btn-primary">Update</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </div>
                <div class="clear"></div>
            </div>
        </div>
        <!--right section-->
    </div>
    <div class="clear"></div>