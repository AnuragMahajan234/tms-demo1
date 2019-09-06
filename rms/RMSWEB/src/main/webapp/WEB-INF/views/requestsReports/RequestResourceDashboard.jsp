
<%@ page import="org.yash.rms.util.Constants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>

<spring:url
	value="resources/js-framework/datatables/ZeroClipboard.js?ver=${app_js_ver}"
	var="ZeroClipboard_js" />
<script src="${ZeroClipboard_js}" type="text/javascript">
</script>
<spring:url
	value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url
	value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />
<spring:url
	value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}"
	var="multiselect_js" />
<script src="${multiselect_js}" type="text/javascript"></script>

<spring:url
	value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}"
	var="multiselect_filter_js" />
<script src="${multiselect_filter_js}" type="text/javascript"></script>

<spring:url
	value="/resources/js-framework/jquery-ui/jquery-ui-1.8.22.custom.min.js?ver=${app_js_ver}"
	var="jquery_ui_1_8_21_custom_min_js" />
<script src="${jquery_ui_1_8_21_custom_min_js}" type="text/javascript">
        <!-- required for FF3 and Opera -->
    </script>

<style>
table.dataTbl tr.selected td{
	background: #a4e3f1;
}
table.dataTable tr.selected td.sorting_1 {
	background: #a4e3f1;
}
th, td {
    white-space: nowrap;
}
.dataTables_length{
	top: 30px;
}
.dataTables_length label, .dataTables_filter label{
	margin-bottom: 0px;
}
.dataTables_filter{
	display: none;
}
.dataTables_length select{
	width: 50px;
}
.dataTables_info{
	display: none;
}
.bottom .dataTables_info{
	display: block;
}
.dataTables_scrollHeadInner,.dataTable {
	width: 100% !important;
}
tfoot {
    display: table-header-group;
}
tfoot input[type="text"]{
	height: 22px;
    padding: 0 2px;
    font-size: 11px;
}
table.tablesorter tfoot tr{
	background: transparent;
}
table.display tfoot th {
    padding: 2px 5px;
    border-top: 1px solid #ccc;
    font-weight: normal;
    text-align: left;
}
.dataTables_paginate{
	margin-top: 0px;
}
/* .dataTables_scrollBody {
	max-height: 260px;
} */
.dataTables_wrapper{
	padding-top: 0px;
}
table.display{
	margin-top: 0px;
}
.modal-body {
	/*max-height: calc(90vh - 250px); */
	overflow-y: auto;
	/* padding-top: 190px !important; */
}

.pointer {
	cursor: pointer;
}

.comment {
	float: left;
}

form select {
	width: 130px;
	height: 25px;
}

form input[type="text"] {
	height: 25px;
}

.modal-dialog {
	margin-top:14%;
	width: 50%;
}

.request-reporttable th, .request-reporttable td, .report-status>table td,
	.report-status>table th {
	padding: 8px;
	border: 1px solid #ccc;
	vertical-align: top;
}

.request-reporttable td:first-child, .request-reporttable th:first-child
	{
	width: 20%
}

.request-reporttable td input[type="text"] {
	min-height: 27px;
	margin-right: 3px;
	line-height: 27px;
	display: inline-block;
	padding: 0 2px;
	float: left;
	width: 200px;
	margin: 0 3px 0 0;
}

.sub-row {
	clear: both;
	margin: 8px 0 0 0;
	display: table;
	width: 100%;
}

.sub-row:first-child {
	margin-top: 0;
}

.report-status {
	margin-top: 10px;
}

.request-reporttable  .remove_field {
	padding-top: 5px;
	display: inline-block;
}

.new-value {
	width: 63%;
	float: left;
}

.blocked-select {
	width: 35%;
	float: left;
}

.new-value textarea {
	width: 100%;
	min-height: 45px;
}

.well {
	background-color: #f6f6f6;
	border: 1px solid #e3e3e3;
	border-radius: 4px;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.05) inset;
	margin-bottom: 20px;
	min-height: 20px;
	padding: 19px;
}
.acted {
    color: green!important;
    font-size: 20px!important;
}

td table tbody tr td {
	border: none !important;
}

.ui-multiselect-menu {
    display: none;
    padding: 3px;
    position: absolute;
    z-index: 10000;
    text-align: left; 
}

form#forwardRRFForm table .ui-multiselect-menu {
    position: inherit;
    margin: 0px;
}
.commentIcon{
    position: static;
    color: #139f72;
    font-size: 23px;
    margin: -11px -6px;
    cursor: pointer
}
.modal-content.commentmodal {
    width: 80%;
    margin-top: 15%;
}
.commentmodal > .modal-header{
background-color: #0067a5 !important;
color: #fff !important;
padding: 10px;
border-bottom: 1px solid #4f92d7;
}
.commentmodal > .modal-header > .close{
  opacity: 1;
  color: #fff;
  box-shadow: none;
}
.modal-header .close {
    margin-top: -11px;
}
.commentmodal > .modal-header > .close:hover{
background-color:transparent;
}
.commentmodal .modal-body{
  min-height: 100px;
  max-height: 250px;
  overflow-x: auto;
}
.commentmodal .commentBody > .commentText {
	word-wrap: break-word;
    padding: 1px 8px;
    margin: 3px -2px 6px;
    margin-right: -2px;
    border-top-right-radius: 10px;
    padding-bottom: 10px;
    font-size: 14px;
    background-color: #d7e8f6;
    border-left: 2px solid #72A1C9;
    border-right: 2px solid #72A1C9;
    margin-left: -2px;
}
.commentBody > .commentText footer{
    display: block;
    font-size: 75%;
    color: #777;
}
.commentmodal .commentbox{
  border-top: 1px solid #e5e5e5;
  padding:5px 10px;
  background-color: #e0eeee;
  border-right: 2px solid #72A1C9;
    box-shadow: 0px 0px 4px #333;
}
.row {
    margin-right: 1px;
    margin-left: -10px;
}
</style>
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script>
var reportTable;


function editrequest(obj,id) {
    $(this).attr('target', '_blank');
    window.location = "../rms/requests?reqId="+id;
}

function skillRequestReportPDF(obj,id) {
    $(this).attr('target', '_blank');
    window.location = "../rms/requestsReports/showPDFReport?reqId="+id;
}

function addRemoveResourceInRequestRequisition(id){
	window.location = "../rms/requestsReports/resources?requestRequisitionId="+id;
}

function display(date, projectName, experience,reqID, location, allocationType) {
	
	 $('#reqDate').text(date);  
	 $('#prjName').text(projectName);  
	// $('#skillName').text(skills);  
	 $('#exp').text(experience);  
	 $('#reqid').text(reqID);
	 $('#workLocation').text(location);
	 $('#allocationType').text(allocationType);
	 $('#readOnlyDetails').modal('show');
	}

function skillRequestReportDelete(requestId,skillRequestId){
    if(confirm("Sure you want to delete this update? There is NO undo!"))
       {
              $.ajax({
                     type: 'POST',
                     url: '${pageContext.request.contextPath}/requestsReports/deleteSkillRequest',
                     dataType: "text",
                     data: {"requestId":requestId,"skillRequestId":skillRequestId},
                     success: function(data){
                            showSuccess("Resource removed Successfully!");
                            window.location.reload();
                     },
                           error:function(response){
                                  stopProgress();
                               showError("Something happend wrong!!");
                           },
              });
               
       }
       return false;
    }
    
function checkRecordStatus($row){
	var status=$("#status").val();
 	var recordStatus = $row.find('#recordStatus').val();
 	if(status != "All"){
     	if (recordStatus !== status) {
             //$row.hide();
     		reportTable.column(2).search("^" + recordStatus + "$", true, false, true).draw();
         }
         else {
        	 reportTable.column(2).search("^" + recordStatus + "$", true, false, true).draw();
         }	
     }else{
    	 reportTable.column(2).search("^" + status + "$", true, false, true).draw();
     }
 } 

function filterTable(clientId) {
	$("#resourcerequestTable > tbody#resourcerequestTableBody > tr").each(function(index) {
            $row = $(this);

            var rowClientId = $row.find('#clientId').val();
            var projectClientId = $row.find('#projectClientId').val(); 

            if (rowClientId !== clientId && projectClientId !== clientId) {
                $row.hide();
            }
            else {
            	checkRecordStatus($row)
            }
        
    });	
 }
$(document).ready(function (){
	
	$('.forward').click(function(event){
		event.preventDefault();
		$('#SelRowID').val($(this).closest('tr').attr('id'));
	});
	var role = $('#role').text();
	
	$('#resourcerequestTable tfoot th').each( function () {
        var title = $(this).text();
        $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
    } );
	
	var reportTable = $('#resourcerequestTable').DataTable( {
		"dom": '<"top"flp<"clear">>rt<"bottom"ifp<"clear">>',
        'info': true,			
		"destroy": true,
		"retrieve": true,
		"smart": false,
		"paginationType": "full_numbers",
		//"scrollY": "250px",        
        //"scrollX": "100%",
        "scrollCollapse": true,
        "paginate": true,
        "autoWidth": false,
		"language": {
			"sSearch": "Search all columns:"
		},
		"sortCellsTop": true,
		"columns": [
		    {width: "50px",sortable: false},
		    /* {width: "50px",sortable: false,visible: true}, */
		    {width: "50px",sortable: false,type: "numeric",visible: false},
		    {width: "50px",sortable: false,visible: false},
		    {width: "50px",sortable: false,visible: false},
		    {width: "150px",sortable: false},
		    {width: "150px",sortable: false},
		    {width: "150px",sortable: false},
		    {width: "150px"},
		    {width: "150px"},
		    {width: "50px"},
		    {width: "50px"},
		    {width: "50px"},
		    {width: "50px"},
		    {width: "50px"},
		    {width: "50px"},
		    {width: "50px"},
		    {width: "50px"},
		    {width: "50px"},
		    {width: "50px"},
		    {width: "50px"},
		    
		    {width: "50px"},
		    {width: "50px",sortable: false},
		    {width: "50px",sortable: false}
		],
	 });
	 
	//check user role if admin visible delete column otherwise visible false
	$('#resourcerequestTable> tfoot > tr > th:eq(0) input').prop("disabled", true);
	$('#resourcerequestTable> tfoot > tr > th:eq(1) input').prop("disabled", true);
	$('#resourcerequestTable> tfoot > tr > th:eq(2) input').prop("disabled", true);
	$('#resourcerequestTable> tfoot > tr > th:eq(3) input').prop("disabled", true);
	$('#resourcerequestTable> tfoot > tr > th:eq(18) input').prop("disabled", true);
	$('#resourcerequestTable> tfoot > tr > th:eq(19) input').prop("disabled", true);
	$('.dataTbl td.allocationtype:contains(Billable)').parent('tr').find('td:nth-child(5)').css("background-color", "#c1d4ee");	
	
	//alert(role);
	if(role == "ROLE_ADMIN") {
		reportTable.columns(20).visible(true);
	} else {
		reportTable.columns(20).visible(false);
	} 

	 $('#resourcerequestTable tbody').on( 'click', 'tr', function () {
	     if ( $(this).hasClass('selected') ) {
	         $(this).removeClass('selected');
	     }
	     else {
	    	 reportTable.$('tr.selected').removeClass('selected');
	         $(this).addClass('selected');
	     }
	 });
	 reportTable.columns().every( function () {
        var that = this;
        $( 'input', this.footer() ).on( 'keyup change', function () {
            if ( that.search() !== this.value ) {
                that.search( this.value ).draw();
            }
        });
     });
	 
	 
	 function getCustomerGroup(flag){
		 var clientIds = [];
		 $("#client option:selected").each(function () {
			var id = $(this).val();
			clientIds.push(id);
		 }); 

		if(clientIds.length > 0 && flag) {
			startProgress();
			$.ajax({
				type: 'GET',
				dataType: 'json',
				 url: '/rms/customers/'+clientIds+'/groups/', 
		         cache: false,
		         success: function(response) {
		         	stopProgress();
						var grpDrpDwn="" ;
						 $('#group').empty();
						 for(var i=0; i<response.length; i++){
							 var populateGrp = response[i];
				             grpDrpDwn = grpDrpDwn + "<option value='"+populateGrp.groupId+"'>"+populateGrp.groupName+"</option>";  
				                
								//var populateGrp = response[i];
				                //grpDrpDwn = grpDrpDwn + "<option value='"+populateGrp[0] + "'>"+populateGrp[2]  + "</option>";     
				            }
						 $("#group").append(grpDrpDwn);	
						 $('#group').multiselect('refresh');
						 //filterTable(clientId);						 
						 //reportTable.column(1).search("^" + clientId + "$", true, false, true).draw();
						 
						 var search = [];
				         $.each($('#client option:selected'), function(){
				        	search.push($(this).val());
				         });
				         var values = search.join('|');
				         reportTable.search('').columns().search('');
						 reportTable.column(1).search("^" + values + "$", true, false, true).draw();
						 setTimeout($('.dataTbl td.allocationtype:contains(Billable)').parent('tr').find('td:nth-child(5)').css("background-color", "#c1d4ee"), 4000);
			     	},
			     	error: function(error){
			     		stopProgress();
			     		showError("Something happend wrong!!");
			     	}
			});
		} else {
			 var search = [];
	         $.each($('#client option:selected'), function(){
	        	search.push($(this).val());
	         });
	         var values = search.join('|');
			 reportTable.column(1).search("^" + values + "$", true, false, true).draw();
			
			$('#group').empty();
			$('#group').multiselect('refresh');
		}
	}
	 
		
		$('#client').multiselect({
			checkAll: function(e) {
				getCustomerGroup(true);	
		    	var options = e.target.options;
		    	  var value = [];
		    	  for (var i = 0, l = options.length; i < l; i++) {
		    	    if (options[i].selected) {
		    	      value.push(options[i].value);
		    	    }
		    	  }
		    	  var value = value.join('|');	 
		    	  reportTable.search('').columns().search('');
		    	  reportTable.column(1).search("^" + value + "$", true, false, true).draw();
		    	 // reportTable.column(1).search("^" + search + "$", true, false, true).draw();
		    },
		    uncheckAll: function(e) {
		    	getCustomerGroup(false);
		    	var values = "";    
		    	reportTable.column(1).search("^" + values + "$", true, false, true).draw();
		    }
		}).multiselectfilter();
		
		$('#status').multiselect({
		    checkAll: function(e) {
		    	var options = e.target.options;
		    	  var value = [];
		    	  for (var i = 0, l = options.length; i < l; i++) {
		    	    if (options[i].selected) {
		    	      value.push(options[i].value);
		    	    }
		    	  }
		    	  var values = value.join('|');	    
		    	  reportTable.column(2).search("^" + values + "$", true, false, true).draw();
		    },
		    uncheckAll: function(e) {
		    	var values = "";    
		    	reportTable.column(2).search("^" + values + "$", true, false, true).draw();
		    }
		}).multiselectfilter();
		
		$('#group').multiselect({
			checkAll: function(e) {
		    	var options = e.target.options;
		    	  var value = [];
		    	  for (var i = 0, l = options.length; i < l; i++) {
		    	    if (options[i].selected) {
		    	      value.push(options[i].value);
		    	    }
		    	  }
		    	  var values = value.join('|');	 
		    	  reportTable.column(3).search("^" + values + "$", true, false, true).draw();
		    },
		    uncheckAll: function(e) {
		    	var values = "";    
		    	reportTable.column(1).search("^" + values + "$", true, false, true).draw();
		    }
		}).multiselectfilter();
		
		$('#activeuser').multiselect({
			 /* includeSelectAllOption : true, 
			 id : 'activeuser_id', */
			 placeholder : "Please select Ids"
			
		}).multiselectfilter();
		
		$('#resourceBlocked1').multiselect({
			includeSelectAllOption : true,
			id : 'resourceBlocked1_id'
		}).multiselectfilter(); 
		
		
		SetForward = function(id){
			$('#getforward').val(id);
		} 
	 
	 
	$("#client").on('change', function(){
		getCustomerGroup(true);		
	}); 
	
	$(document).on('click','.paginate_button,#resourcerequestTable > thead th', function(){
		setTimeout($('.dataTbl td.allocationtype:contains(Billable)').parent('tr').find('td:nth-child(5)').css("background-color", "#c1d4ee"), 2000);
	});
	
	$(document).on('change keyup', 'select[name="resourcerequestTable_length"],.dataTbl tfoot th input',function(){
		$('.dataTbl td.allocationtype:contains(Billable)').parent('tr').find('td:nth-child(5)').css("background-color", "#c1d4ee");		
	});
    $("#group").on('change', function(){
	 	 
    	var search = [];
        $.each($('#group option:selected'), function(){
        	search.push($(this).val());
        });
       
        var values = search.join('|');
		reportTable.column(3).search("^" + values + "$", true, false, true).draw();
		setTimeout($('.dataTbl td.allocationtype:contains(Billable)').parent('tr').find('td:nth-child(5)').css("background-color", "#c1d4ee"), 4000);
       
 	});
 
  	$('#status').on('change', function() {
 		var search = [];
        $.each($('#status option:selected'), function(){
            search.push($(this).val());
        });
        search = search.join('|');	    
		reportTable.column(2).search("^" + search + "$", true, false, true).draw();
		setTimeout($('.dataTbl td.allocationtype:contains(Billable)').parent('tr').find('td:nth-child(5)').css("background-color", "#c1d4ee"), 4000);
	});
  	$("#status").val('Active').trigger('change');
    
}); 

function checkClientAndGroupStatus($row){
	var clientId = $("#client").val();
	var groupId = $("#group").val();
	if(clientId !==  "all"){
		var rowClientId = $row.find('#clientId').val();
     var projectClientId = $row.find('#projectClientId').val(); 
  	if (rowClientId !== clientId && projectClientId !== clientId) {
          $row.hide();
      }else{
      	var groupId = $("#group").val();
      	if(groupId != "All"){
      		var rowGroupId =  $row.find('.groupId').val();
      		if (rowGroupId !== groupId) {
	                $row.hide();
	            }else{
	            	$row.show();	
	            }
      	}else{
      		$row.show();
      	}
      }
	}else{
 	$row.show();
	}
}


$(function() {
	

})

function checkList()
{
	var selected =document.getElementById("activeuser");
	
	var selectedpdl =document.getElementById("resourceBlocked1");
	var send =document.getElementById("sendId");
	var len =selected.selectedOptions.length+selectedpdl.selectedOptions.length;
	var skillReqId=document.getElementById('getforward').value;
	
	if(selected.value=="" && selectedpdl.value=="")
	{
		send.disabled=true;		   
	    showError("Please select atleast one EmailId");
	    setTimeout(function(){ send.disabled=false;	
        $('.toast-close').click();}, 2000);
		return false;
	}
	
	 if(len>10)
	{
		send.disabled=true;	
		showError("Please don't select total more than 10 EmailIds");
		setTimeout(function(){ send.disabled=false;	
        $('.toast-close').click();}, 2000);
		return false;
	}
	 if(selected.value!="" || selectedpdl.value!=""||len<=10)
	 {
		 var list=null;
		 var pdlList=null;
		 if($('#activeuser').val()!=null)
		 	list=$('#activeuser').val().toString();
		 if($('#resourceBlocked1').val()!=null)
		  pdlList=$('#resourceBlocked1').val().toString();
		 sendmail(list,pdlList,skillReqId);
	 }
	return true;

}
function sendmail(list,pdlList,skillReqId){
	var selrowID='#'+$('#SelRowID').val();	
	          $.ajax({
                     type: 'POST',
                     url: '${pageContext.request.contextPath}/requestsReports/forwardRRF',
                     dataType: "text",
                     data: {"list":list,"pdlList":pdlList,"skillReqId":skillReqId},
                     success: function(data){
                    	 showSuccess("Successfull");
                    	 $("#myModal,.modal-backdrop").hide();
                    	 //$('#myModal').modal('toggle');
                    	 $(selrowID).find('.fa-share').addClass('acted');
                    	                      },
                     error:function(response){
                                  stopProgress();
                                  showError("Something happend wrong!!");
                           },
              });
}
	
$(document).on('click','.commentClassForAjax',function(){
	$('#internalDiv,#externalDiv').html('');
	$("#myModal1").removeClass("fade");
	$("#myModal1").show();  
		var id = $(this).parent().parent().find('td:eq(0) > a').attr("onclick").split(',')[1].split(')')[0];
			 $("#reqNameId").text( $(this).parent().parent().find('td:eq(6) > a').text()); 
		$.ajax({
	    	   type: 'GET',
	    	   url: 'requestsReports/getLatestCommentOnResource',
	    	   dataType: "text",
	    	   data: {"id":id},
	    	   success: function(response){
	    		   arrob = JSON.parse(response);
	    		   var comment = "";					
					$.each(arrob, function(intIndex, objComment){
						 
						 comment = "<div class='commentText' > <h3>"+objComment['resourceType']+"</h3> <p>"
							+objComment['resourceName']+"</p><p>"+ objComment['resourceComment']+"</p> <footer>From "
		    			   +objComment['commentBy'] + " : "+" " + objComment['comment_Date'] + "</footer></div>";
		    			   if(objComment['resourceType'].trim()=='Internal' || objComment['resourceType'].trim()=='INTERNAL'){
								$('#internalDiv').append(comment);
		    			   }else{
		    				   $('#externalDiv').append(comment);	
		    			   }
					});    	   	    
	    	   },
		   }); 
	    $("#clsComment").click(function(event){ 
	    	$("#myModal1").addClass("fade");
			$("#myModal1").hide();  
	    });
});
	
</script>
<div class="content-wrapper">
		<!--  style="overflow:auto;" -->
		<div class="botMargin">
			<h1><strong>Profile Submission</strong></h1>
		</div>
		<div class="tab_seaction">
			<ul class='tabs'>
				<li><a href='#tab1'>List</a></li>
			</ul>
			<div id='tab1' class="tab_div" style="display: inline-block;width: 100%;padding: 5px;">
				<div class="tbl">
					<div style="display: inline-block;width: 100%;margin-bottom: 5px;">
						
						
						<div style="padding-left: 0px; float: left; ">
							<label>Client</label> &nbsp;<select multiple id="client">
								<!-- <option selected="selected" value='all'>All</option> -->
								<c:forEach var="client" items="${requestRequisitionDashboardDTO.customerList}">			
										<c:if test="${client.id != 0}">
											<option value="${client.id}">${client.customerName}</option>				
										</c:if>				
							    </c:forEach>
								    
							</select>
						</div>
						
						<div style="margin-left: 30px; float: left;position: relative;">
							<label>Group</label> &nbsp;<select multiple id="group">
							
										<!-- <option  value="All">All</option>  -->
								</select>
						</div>
	
						<div style="margin-left: 30px; float: left;position:relative">
							<label>Status</label> &nbsp;<select multiple id="status">
								<!-- <option value="All">All</option> -->
								<option value="Active" selected='selected'>Active</option>
								<option value="Inactive">Inactive</option>
							</select>
						</div>
					</div>
					
					
					<div class="clear"></div>
					<form:form action="post" modelAttribute="requestRequisitionDashboardDTO">
						<p id="role" value="${ROLE}" style="visibility: hidden;">${ROLE}</p>
						<table class="dataTbl display tablesorter addNewRow alignCenter" id="resourcerequestTable"
						style="overflow:auto;display:inline-block;height: 310px">
						<!-- style="overflow:auto;display:inline-block;height: 300px" -->
							<thead>
								<tr>
									<th align="center" valign="middle">RRF</th>
									<!-- <th align="center" valign="middle">Resource ID</th> -->
									<!-- <th align="center" valign="middle"  width="12%">Forward</th>  -->
									<th align="center" valign="middle" >Client ID</th>
									<th align="center" valign="middle" >Record Status</th>
									<th align="center" valign="middle" >Group ID</th>
									<th align="center" valign="middle">Forward RRF</th>
									<th align="center" valign="middle" width="1%">Submit Profile</th>
									<th align="center" valign="middle" width="1%">Comment </th>
									<th align="center" valign="middle">Status</th> <!-- Position Status -->
									<th align="center" valign="middle">RRF ID</th>
									<th align="center" valign="middle" width="10%">Allocation Type</th>
									<th align="center" valign="middle" width="1%">Designation</th>
									<th align="center" valign="middle">Required Skill</th>
									
									<th align="center" valign="middle">Total Positions</th>  <!-- earlier it was 'need to fulfill' -->
									<th align="center" valign="middle" width="1%">Open</th>
									<th align="center" valign="middle" width="1%">Submissions</th>
									<!-- <th align="center" valign="middle" width="1%">Resource(s) Req</th> -->
									
									<th align="center" valign="middle" width="1%">ShortListed</th> <!-- earlier it was 'Request Fulfilled' -->
									<th align="center" valign="middle" width="1%">Closed/Joined</th>
									<th align="center" valign="middle" width="1%">Not Fit</th>
									<th align="center" valign="middle" width="1%">On Hold</th>
									<th align="center" valign="middle" width="1%">Lost</th>
									<!-- <th align="center" valign="middle" width="7%">BU</th> -->
									<!-- <th align="center" valign="middle" width="10%">Indentor's Name</th> -->
									<!-- <th align="center" valign="middle" width="10%">Proposed Desig.</th> -->
									<!-- <th align="center" valign="middle" width="3%">Discussion</th> -->									
									<!--  <th align="center" valign="middle" width="3%">Shortlisted</th> -->
									<!-- <th align="center" valign="middle"  width="15%">Comments</th> -->
									<!--   <th align="center" valign="middle"  width="32%">Comments</th>  -->
								
									<th align="center" valign="middle">Requested By</th>
									<th align="center" valign="middle" width="5%">Delete</th>
									<!--1003958 Starts[]-->
									<th align="center" valign="middle" width="5%">Edit</th>
									<!--1003958 Ends[] -->
								</tr>
							</thead>
							<tfoot>
								<tr>
									<th>RRF</th>
									<!-- <th>Resource ID</th> -->
									<th >Client ID</th>
									<th >Record Status</th>
									<th >Group ID</th>
									<th>Forward RRF</th>
									<th>Submit Profile</th>
								    <th>Comment</th>
									<th>Status</th>
									<th>RRF ID</th>
									<th>Allocation Type</th>
									<th>Designation</th>
									<th>Required Skill</th>
									<th>Total Positions</th>
									<th>Open</th>
									<th>Submissions</th>
									<th>ShortListed</th>
									<th>Closed/Joined</th>
									<th>Not Fit For Requirement</th>
									<th>Position On Hold</th>
									<th>Lost</th>
								
									<th>Requested By</th>
									<th>Delete</th>
									<th>Edit</th>
								</tr>
							</tfoot>
							<tbody id="resourcerequestTableBody">
								<c:set var="count" value="0" scope="page" />

								<c:forEach var="reportList" items="${requestRequisitionDashboardDTO.requestRequisitionDashboardList}">

									<!-- <td></td> -->
									<tr id="${reportList.requestRequisition.id}" class="abc even odd">
										<td><a class="pointer"
											onclick="skillRequestReportPDF(this,${reportList.id})" title="Download RRF"><i class="fa fa-download" aria-hidden="true"></i></a>
										</td>
										
									<%-- 	<td id="resourceObject">${reportList.requestRequisition.resource.userRole} </td> --%>
										<%-- <td id="resourceObject">${ROLE} </td> --%>
										
										<!-- <td><a class="pointer" data-toggle="modal" data-target="#myModal" >Forward</a></td>-->
										<td >${reportList.requestRequisition.customer.id}</td>
										<td >${reportList.recordStatus}</td>
										<td >${reportList.requestRequisition.group.groupId}</td>
										<c:choose>
											 <c:when test = "${not empty reportList.sent_req_id || not empty reportList.pdl_list}">  
												 <td class="tooltip_link"  title="Forward Mail" >
													 <a class="pointer forward" data-toggle="modal" data-target="#myModal" onclick="SetForward(${reportList.id})">
												  		<i class="fa fa-share" aria-hidden="true" style=" color:  green;  font-size: 20px;"></i> 
												  	</a>
												 </td>	
											  </c:when>
         								     <c:otherwise> 
	         								     <td class="tooltip_link"  title="Forward Mail" >
	         								     	<a class="pointer forward" data-toggle="modal" data-target="#myModal" onclick="SetForward(${reportList.id})"> 
	         								      		<i class="fa fa-share" aria-hidden="true" style=" color:  #ff8f00d4;  font-size: 17px;"></i>
	         								      	</a>
	         								      </td>	
         								      </c:otherwise>
									   </c:choose>
										   <td>
											<a onclick="addRemoveResourceInRequestRequisition(${reportList.id})"
												data-target="#addResource" id="${reportList.id}"
												href="javascript:void(0)"> 
												<img  src="resources/images/addUser.gif" id="addIcon" class="addIcon" inp="add"/>
											</a>
											<input type="hidden" readonly="readonly"
												id="resourceBlockedIntText${reportList.id}"
												class="resourceBlockedIntText"
												name="resourceBlockedIntText" inp="resourceBlockedIntText"
												value="" />
											<input type="hidden" readonly="readonly"
												id="resourceBlockedExtText${reportList.id}"
												class="resourceBlockedExtText"
												name="resourceBlockedExtText" inp="resourceBlockedExtText"
												value="" />
											</td>
											
											<td><i class="fa fa-commenting commentIcon commentClassForAjax" aria-hidden="true"
                        												data-toggle="modal"></i></td>
											<td>${reportList.status}</td>
											
										<td>
											<input type="hidden" value ="${reportList.requestRequisition.project.customerNameId.id}" class="projectClientId" id="projectClientId"></input>
											<c:choose>
												<c:when test="${reportList.requestRequisition.comments}">
													<a  class="pointer" title="Click For More Details"  OnClick="display('${reportList.requestRequisition.date}','${reportList.requestRequisition.project.projectName}','${reportList.experience}','${reportList.requestRequisition.comments}' );" >
														${reportList.requestRequisition.comments} </a>
												</c:when>
												<c:otherwise>
													<a   class="pointer" title="Click For More Details"  OnClick="display('${reportList.requestRequisition.date}','${reportList.requestRequisition.project.projectName}','${reportList.experience}','${reportList.requirementId}','${reportList.locationName}','${reportList.allocationType.allocationType}' );" >
														${reportList.requirementId} </a>
												</c:otherwise>
											</c:choose><!-- for hidden skill Resource Id -->
												<table>
													<c:forEach var="skillResources"
														items="${requestRequisitionResource.requestRequisitionSkill}">
														<tr class="skillResourcesId">
															<td><input type="hidden"
																value="${requestRequisitionSkill.id}" /></td>
														</tr>
													</c:forEach>
												</table>

										</td>
										<c:choose>
											 <c:when test = "${reportList.allocationType.id==2 || reportList.allocationType.id==3}"><td  >${reportList.allocationType.aliasAllocationName}</td></c:when>
											 <c:otherwise><td class="allocationtype" >${reportList.allocationType.aliasAllocationName}</td></c:otherwise>
										</c:choose>
										
										<td>${reportList.designation.designationName}</td> 	
										<td>${reportList.skill.skill}</td>
										
										<%-- <td class="bg-success "><a   class="pointer forward" data-toggle="modal" data-target="#myModal" onclick="SetForward(${reportList.id})"> Forward</a> --%> 
										
										<td>${reportList.noOfResources}</td>
											<%-- <td type="hidden"><table type="hidden">
												<tr>
													<td id="noofresources">${reportList.noOfResources}</td>
												</tr>
												<tr style="height: 10px">
													<input type="hidden" id="noOfRes${reportList.id}"
														value="${reportList.noOfResources}" />
												</tr>
											</table type="hidden"></td>
											 --%>
										
									<td>${reportList.open}</td>
									<td>${reportList.submissions}</td> 
									<td>
										<input class="ff" id="fulfilledRes${reportList.id}" readonly="readonly"
										name="fulfilledRes" type="hidden" value="${reportList.shortlisted}"<%--onkeyup="setRemaining()" --%> >
										
										${reportList.shortlisted}
									</td>
									<td>
										<input type="hidden" value="${reportList.id}">
										${reportList.closed}
									</td>
									<td>${reportList.notFitForRequirement}</td>									
									<td>${reportList.hold}</td>
									<td>${reportList.lost}</td>
										<%-- <td id="firstTd"><center>${reportList.requestId.projectBU}</center></td> --%>
										<%--  <td><center>${reportList.requestId.employeeId.firstName}
                                                                            ${reportList.requestRequisition.employeeId.lastName}</center></td>--%>
										

										

										<%--   <td><table>
                                                                           <tr>
                                                                                  <td>${reportList.designation.designationName}</td>
                                                                           </tr>
                                                                           <tr style="height: 10px"></tr>
                                                                     </table></td> --%>
										<!-- <td><table>
												<tr>
													<td>Discussion</td>
												</tr>
												<tr style="height: 10px"></tr>
											</table></td> -->
									
									<%-- 	<td><table>
												<tr>${reportList.shortlisted}</tr>
												<tr style="height: 10px"></tr>
											</table></td> --%>
										    
															

										<%-- <td><table>
                                                                           <tr class="cmnts">
                                                                                  <td>${reportList.comments}<input id="comments" type="hidden" value="${reportList.comments}"/>
                                                                                  </td>
                                                                           </tr>
                                                                           
                                                              </table></td> --%>

										<%-- <td>${reportList.requestRequisition.comments}
                                                       <!-- for hidden skill Resource Id -->
                                                       <table>
                                                              <c:forEach var="skillResources" items="${reportList.skillResources}">
                                                                    <tr class="skillResourcesId"> 
                                                                         <td> <input type="hidden" value="${skillResources.id}"/></td>
                                                                    </tr>
                                                              </c:forEach>
                                                       </table>      
                                                       
                                                       </td>  --%>
                                                       
                                                       
                                        <td>${reportList.requestRequisition.resource.firstName} ${reportList.requestRequisition.resource.lastName}</td>
									

									
											<td>	
											<%-- <sec:authorize access="!hasRole('ROLE_ADMIN')">									
											<c:if test="${reportList.fulfilled <= 0 && reportList.status !='Closed' && reportList.closed <= 0}"> --%>
													<a class="pointer"
														onclick="skillRequestReportDelete(${reportList.requestRequisition.id},${reportList.id})"><img
														alt="Delete" height="20px" width="20px"
														src="resources/images/delete.png"></a>
												<%-- </c:if>
												</sec:authorize> --%>
											</td>
										
										<!--1003958 Starts[]-->
										<td> 
										<sec:authorize access="hasRole('ROLE_ADMIN')">
											<c:if test="${reportList.status !='Closed'}">
                                                                                  <a onclick="editrequest(this,${reportList.id})"
                                                                                         href="javascript:void(0);">Edit</a>
											</c:if>
										</sec:authorize>
										</td>
										<!--1003958 Ends[]-->
									</tr>
									<c:set var="count" value="${count + 1}" scope="page" />
								</c:forEach>
							</tbody>
						</table>
					</form:form>
				</div>
			</div>
		</div>
	
	<div id="readOnlyDetails" class="modal" role="dialog"
		data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog cust-modalDialog">
			<div class="modal-content">
				<div class="modal-header">
				<h3> Details For Requirement ID : <span id="reqid"></span></h3>
				<span class="close modelclose" data-dismiss="modal">&times;</span>
				</div>
				<div class="modal-body">

					<table class="table table-bordered">
						<thead>
							<tr>
								
								<th>Requested Date</th>
								<th>Project Name</th>
								<!-- <th>Skills</th> -->
								<th>Experience</th>
								<th>Work Location</th>
								<th>Allocation Type</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								
								<td><span id="reqDate"></span></td>
								<td><span id="prjName"></span></td>
							<!-- 	<td><span id="skillName"></span></td> -->
								<td><span id="exp"></span></td>
								<td><span id="workLocation"></span></td>
								<td><span id="allocationType"></span></td>
							</tr>

						</tbody>
					</table>


				</div>


			</div>
		</div>
	</div>
	<!-- Comment start -->
	<div class="modal fade" id="myModal1" role="dialog">
       <div class="modal-dialog">

         <!-- Modal content-->
         <div class="modal-content commentmodal">
           <div class="modal-header">
             <button type="button" id="clsComment" class="close" data-dismiss="modal">&times;</button>
             <h4 class="modal-title" id="reqNameId"></h4>
           </div>
           <div class="modal-body commentBody row">
         
           <div class="col-md-6 commentBody row" id='internalDiv'>
           
           </div>
      
           <div class="col-md-6 commentBody row" id='externalDiv'>
           
           </div>
           
           </div>
         
         </div>

       </div>
     </div>
	<!-- comment end -->
	
	 <div id="myModal" class="modal" role="dialog"
		data-backdrop="static" data-keyboard="false" >
		<div class="modal-dialog cust-modalDialog">
			<div class="modal-content">
				<div class="modal-header">
				<h3>Send RRF</h3>
				<span class="close modelclose" data-dismiss="modal">&times;</span>
				</div>
				<div class="modal-body">
				<form:form modelAttribute="resourceAndPDLInputDTO"   id="forwardRRFForm" action="../rms/requestsReports/forwardRRF"> 	
					<table class="table table-bordered">
						<thead>
							 <tr>
								<th>Select Sent Req to</th>
								<th>PDL List</th>
							</tr> 
						</thead> 
						<tbody>
						<tr>
						<td>
				             <form:select id="activeuser" name="internalResourceIds" class="required" path="resourceIds" multiple="multiple">
				            			<%--  <form:option value="0" label="Select" />  --%>
				            			<
				            			<c:forEach var="activeUsers" items="${requestRequisitionDashboardDTO.activeUserList}">
												<option value="${activeUsers.emailId}">${activeUsers.firstName} ${activeUsers.lastName}</option>
										</c:forEach>
									</form:select>
									
										
									
									</td>
									
									<td>
									<form:select id="resourceBlocked1"  name="internalResourceIds1" class="required" multiple="multiple" path="pdlIds">
										<c:forEach var="pdlGrp" items="${requestRequisitionDashboardDTO.pdlGroup}">
												<option value="${pdlGrp.pdlEmailId}">${pdlGrp.pdlEmailId}</option>
										</c:forEach>
									</form:select>
									</td>
        			</tr>
        			<tr>
        			  <!--<form:input path="skillRequestID" type="hidden"  value="${requestRequisitionDashboardDTO.requestRequisitionDashboardList[0].id}" />-->
        			   <form:input path="skillRequestID" type="hidden"  id="getforward" /> 
						
											<button  id="sendId" type="button" onclick="return checkList()" class="btn btn-primary">Send</button>
        			</tr>
        			
											
									
							
						</tbody>
					</table>
					</form:form>
		
				<input type="hidden" id="SelRowID"/>
				

				</div>


			</div>
		</div>
	</div> 
</div>	
