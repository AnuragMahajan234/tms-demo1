<%@ page import="org.yash.rms.util.Constants"%>
<%@ page import="org.yash.rms.util.UserUtil"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<spring:message code="application_js_version" var="app_js_ver" htmlEscape="false" />

<spring:url
	value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url
	value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />
	
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>

<spring:url value="/resources/images/spinner.gif" var="spinner" />

<!-- Start custom loader -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/js/jquery.nice-select.min.js?ver=${app_js_ver}"></script>
<!-- End custom loader -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />

<spring:url value="/resources/css/select2.min.css?ver=${app_js_ver}"
	var="select2_css" />
<link href="${select2_css}" rel="stylesheet" type="text/css"></link>

<spring:url value="/resources/js/select2.min.js?ver=${app_js_ver}"
	var="select2_js" />
<script src="${select2_js}" type="text/javascript"></script>
<style type="text/css" title="currentStyle">
#positionDashboardMain .my_table thead th {
	background: #fff url('../resources/images/sort_both.png') no-repeat center right !important;
	color: #333 !important;
	    border-bottom: 1px solid #ccc !important;
}

#positionDashboardMain .my_table .sorting {
	background:#fff url('../resources/images/sort_both.png') no-repeat center right !important;
}

#positionDashboardMain .my_table .sorting_asc {
	background:#fff url('../resources/images/sort_asc.png') no-repeat center right !important;
}

#positionDashboardMain .my_table .sorting_desc {
	background:#fff url('../resources/images/sort_desc.png') no-repeat center right !important;
}

#positionDashboardMain .my_table .sorting_asc_disabled {
	background:#fff url('../resources/images/sort_asc_disabled.png') no-repeat center right !important;
}

#positionDashboardMain .my_table .sorting_desc_disabled {
	background:#fff url('../resources/images/sort_desc_disabled.png') no-repeat center right !important;
}
</style>
 
<script>
	
var dataTablepd=""; 
	$(document).ready(function() {
		$('#interviewdate').datepicker();
		  
	/*Start custom Loader*/
	function startProgress(){
	
		  $.blockUI({ message: '</br></br><div class="circle"></div><div class="circle1"></div><center>Just a moment</center>' }); 
	  }
	  
	 function stopProgress(){
	
		 $.unblockUI();	
	 }
	 stopProgress();
	/*End custom Loader*/	 
	var flag = true;
	
/* 	$(function() {
		$('#internalUserIds').multiselect({
			includeSelectAllOption: true,
			noneSelectedText: "Select Recipient",
		}).multiselectfilter();
	}); */
	
	$(function() {
		$('#pdlGroupIds').multiselect({
			includeSelectAllOption: true,
			noneSelectedText: "Select PDL",
		}).multiselectfilter();
	});
		
	$(function() {
		$('#client').multiselect({
			includeSelectAllOption: true,
			noneSelectedText: "Select Client",
		})
		
		$('#client').multiselectfilter({
			placeholder: 'Filter..',
		});


	$(document).ready(function() {
  $('.form-control.comboselect.check').niceSelect();
  $('#example-sort').niceSelect();
  
});
	});
	
	$(function() {
		$('#group').multiselect({
			includeSelectAllOption: true,
			noneSelectedText: "Select Group",
		});
		$('#group').multiselectfilter({
			placeholder: 'Filter..',
		});

});
	
	$('#client')
	.multiselect({
		checkAll : function() {
			getGroupForCustomer();
			$("#group").multiselect("disable");
		},
		uncheckAll : function() {
			flag= false;
			$("#group").multiselect("enable");
			getGroupForCustomer();
	}});
	
	
	var getGroupForCustomer =  function(){
		 var clientIds = [];
		 
		 $("#client option:selected").each(function(){
				var id = $(this).val();
				clientIds.push(id);
		 });

		 
		 
		if(flag == true && clientIds.length>0){
			startProgress();
			flag = true;
			$.ajax({
				type: 'GET',
				dataType: 'json',
		        url: '/rms/customers/'+clientIds+'/groups/', 
		        cache: false,
		        success: function(response) { 
		        	stopProgress();
						var grpDrpDwn="";
						 $('#group').empty();
						 for(var i=0; i<response.length; i++){
								 var populateGrp = response[i];
				                grpDrpDwn = grpDrpDwn + "<option value='"+populateGrp.groupId+"'>"+populateGrp.groupName+"</option>";     
				            }
						 $("#group").append(grpDrpDwn);
						 $('#group').multiselect('refresh');
			     	},
			     	error: function(error){
			     		stopProgress();
			     		showError("Something went wrong!!");
			     	}
				});
			}else{
				
				 flag = true;
				 $('#group').empty();	
				 $('#group').multiselect('refresh');
			}
		};
	
		
	
		$('#modal_data_table').DataTable({
	       	"bPaginate": false,
	       	"bFilter": false,
	        "scrollX":        true,
	        "scrollY":        200,
	       	"aaSorting": [],
	        "bInfo": false,
	        'bStyle':'overflow: auto; display: inline-block; height: 200px; border: 1px solid #ccc;',
	    	 'aoColumns': [ 
		   			{'aTargets': 0, 'sClass': 'stop_click stop_click align-left','sWidth':'60px' }, // numeric id
		   			{'aTargets': 1,'sClass': ''},	
		   			{'aTargets': 2,'sClass': ''},
	 				{'aTargets': 3,'sClass': ''}, // required skill
	 				{'aTargets': 4,'sClass': ''},
	 				{'aTargets': 5,'sClass': ''},
	 				{'aTargets': 6,'sClass': ''},
	 				{'aTargets': 7,'sClass': ''},
	 				{'aTargets': 8,'sClass': ''},
	 				{'aTargets': 9,'sClass': ''},
	 				{'aTargets': 10,'sClass': ''},
	 				{'aTargets': 11,'sClass': ''},
	 	        
		           ]
	 	});
		
		
		 function createAction(row){
			var status = row.status;
     		var requestRequisitionId = row.requestRequisition.id;
     		var sentToList = row.sent_req_id;
     		var pdlList = row.pdl_list;
     		var resourcesCount = row.submissions;
     		var reqSkillName = row.requirementId;
     		
     		var statusName= 'All Resources';
      		var status = '0';
  			var rrfStatus = row.status.toLowerCase();
      		var rowId = row.id;
      		var reqId = row.requirementId;
     		var submissions = row.submissions;
     		
			 return `<div class="stop_click action-dropdown dropbtn dropdowns">
     		<span title="Click here" class="dropdown dropleft dropDownHover hover-area" onclick="showActionDropdown(this)">
     		<i class="fa fa-ellipsis-v" style="font-size: 15px;" class="btn dropdown-toggle"></i>
     		<div class="dropdown-menu dropdown-content">
     		<c:if
				test='<%=UserUtil.getCurrentResource().getEmployeeId() == 230
					|| UserUtil.getCurrentResource().getEmployeeId() == 4648
					|| UserUtil.getCurrentResource().getEmployeeId() == 5456
					|| UserUtil.getCurrentResource().getEmployeeId() == 5929
					|| UserUtil.getCurrentResource().getEmployeeId() == 5493
					|| UserUtil.getCurrentResource().getEmployeeId() == 211
					|| UserUtil.getCurrentResource().getEmployeeId() == 353
					|| UserUtil.getCurrentResource().getEmployeeId() == 4623
					|| UserUtil.getCurrentResource().getEmployeeId() == 7569
					|| UserUtil.getCurrentResource().getEmployeeId() == 4211
					|| UserUtil.getCurrentResource().getEmployeeId() == 6085
					 || UserUtil.getCurrentResource().getEmployeeId() ==7434 || UserUtil.getCurrentResource().getEmployeeId() ==6176 || UserUtil.getCurrentResource().getEmployeeId() ==7488 || UserUtil.getCurrentResource().getEmployeeId() ==669
					|| UserUtil.getCurrentResource().getUserRole().equalsIgnoreCase("ROLE_ADMIN")%>'>
					<div class="borderHover">
						<a class="dropdown-item"
							onclick="addRemoveResourceInRequestRequisition(`+ rowId +`)" data-target="#addResource" id="`+ rowId +`" href="javascript:void(0)"> <i class="fa fa-plus-square-o my_submit_btn"></i>Submit Resources</a></br></a>
					</div>
					</c:if>
			<div class="borderHover">
				<a class="dropdown-item" data-toggle="modal"
				data-target="#myModalShowAll" title="Show all resources" 
				onclick="onClickResourceProfile('`+statusName+`','`+status+`',`+rowId+`,'`+reqId+`',`+submissions+`)">
				<i class="fa fa-users"></i>View all resources</a></br>
			</div>
			<div class="borderHover">
				<a class="dropdown-item" data-toggle="modal"
					data-target="#view-resources-PD"
					onclick="skillRequestReportPDF(this,`+rowId+`)"
					title="Download RRF"> <i class="fa fa-download"></i>Download RRF</a></br>
			</div>
			<div class="borderHover">
				<a class="dropdown-item" href="#" onclick="editrequest(this,`+rowId+`)"
					title="Edit RRF"> <i class="fa fa-edit" aria-hidden="true"> </i>Edit RRF</a></br>
			</div> ` + checkSentToAndPdlList(row) + postEmailListDiv(row) + deleteDiv(row) + stakeHolderDiv(row) + SFIdDiv(row) + postDeleteDiv(row);
		 }
		
		function checkSentToAndPdlList(row){
			
			var status = row.status;
    		var requestRequisitionId = row.requestRequisition.id;
    		var sentToList = row.sent_req_id;
    		var pdlList = row.pdl_list;
    		var resourcesCount = row.submissions;
    		var reqSkillName = row.requirementId;
    		
    		var statusName= 'All Resources';
     		 	var status = '0';
 				var rrfStatus = row.status.toLowerCase();
     		 	var rowId = row.id;
     			var reqId = row.requirementId;
    		var submissions = row.submissions;
    		
			
			
			 if(sentToList!=null || pdlList!=null){

				 return `<div class="borderHover">
					<a class="dropdown-item" data-toggle="modal"
						data-target="#forwrd-rrf-PD"
						onClick="forwardRRF(` + rowId + `)" title="Forward">
						<i class="fa fa-share" aria-hidden="true"><span
							class="orange-batch"
							id="forwardOrangeId` + rowId +`"></span></i>Forward
					</a></br>
				</div>`;
			 }
			 else {
				 return `<div class="borderHover">
					<a class="dropdown-item" data-toggle="modal"
					data-target="#forwrd-rrf-PD"
					onClick="forwardRRF(` + rowId +`)" title="Forward">
					<i class="fa fa-share" aria-hidden="true"><span
						id="forwardOrangeId` + rowId +`"></span></i>Forward
				</a></br>
			</div>`;
			}
		 } 
		
		function postEmailListDiv(row){
			 var status = row.status;
	     		var requestRequisitionId = row.requestRequisition.id;
	     		var sentToList = row.sent_req_id;
	     		var pdlList = row.pdl_list;
	     		var resourcesCount = row.submissions;
	     		var reqSkillName = row.requirementId;
	     		
	     		var statusName= 'All Resources';
	      		 	var status = '0';
	  				var rrfStatus = row.status.toLowerCase();
	      		 	var rowId = row.id;
	      			var reqId = row.requirementId;
	     		var submissions = row.submissions;
	     		
	     		return `<div class="borderHover">
			<a class="dropdown-item" data-toggle="modal"
				data-target="#myCommentsModal"
				onclick="getLatestCommentOnResource(`+ rowId +`)"
				title="Comments"> <i class="fa fa-comments"
				aria-hidden="true"> </i>Comment</a></br>
		</div>
		
		<div class="borderHover">
			<a class="dropdown-item" data-toggle="modal"
		
			onclick="copyRRFMethod(`+ rowId +`)" 
			title="Copy RRF"><i class="fa fa-copy" 
			aria-hidden="true"> </i>Copy RRF</a></br>
		</div>

		<div class="borderHover">
			<a class="dropdown-item" href="#"
				onclick="BGHfileDownload(`+ requestRequisitionId +`)"
				title="Download BGH Approval"> <i
				class="fa fa-download" aria-hidden="true"> </i>BGH approval</a></br>
		</div>
		<div class="borderHover">
			<a class="dropdown-item" href="#"
				onclick="BUHfileDownload(`+ requestRequisitionId +`)"
				title="Download BUH Approval"> <i
				class="fa fa-download" aria-hidden="true"> </i>BUH approval</a></br>
		</div>`;
	} 
function deleteDiv(row){
		 var status = row.status;
    		var requestRequisitionId = row.requestRequisition.id;
    		var sentToList = row.sent_req_id;
    		var pdlList = row.pdl_list;
    		var resourcesCount = row.submissions;
    		var reqSkillName = row.requirementId;
    		
    		var statusName= 'All Resources';
     		 	var status = '0';
 				var rrfStatus = row.status.toLowerCase();
     		 	var rowId = row.id;
     			var reqId = row.requirementId;
    		var submissions = row.submissions;
    		 
    		var h ;
    		
   		 
    		if(rrfStatus!='closed'){
    			h = `<sec:authorize access="hasRole('ROLE_ADMIN')">
        			<div class="borderHover">
        			<a
        				onclick="skillRequestReportDelete(` + requestRequisitionId+ `,` + rowId +`)" title="Delete RRF" class="dropdown-item" href="#"> <i class="fa fa-trash" aria-hidden="true"> </i>Delete
        			</a></br>
        		</div>
        		</sec:authorize>`;
    		}else{
    			h =``;
    		}
    		return h;
}
	function postDeleteDiv(row){
		var status = row.status;
		var requestRequisitionId = row.requestRequisition.id;
		var sentToList = row.sent_req_id;
		var pdlList = row.pdl_list;
		var resourcesCount = row.submissions;
		var reqSkillName = row.requirementId;
		
		var statusName= 'All Resources';
 		var status = '0';
		var rrfStatus = row.status.toLowerCase();
 		var rowId = row.id;
 		var reqId = row.requirementId;
		var submissions = row.submissions;
		return `<div class="borderHover"> <a class="dropdown-item" data-toggle="modal"
			data-target="#InterviewPanelModal" data-backdrop="static" onclick="getInterviewPanel(`+ rowId +`)"
			title="Add Interview Panel"> <i class="fa fa-users" aria-hidden="true"> </i>Add Interviewer</a></br></div>
		</div>
		</span>
		</div>`;
	}
		 
	function stakeHolderDiv(row) {
		
		var requestRequisitionId = row.requestRequisition.id;
		return  `<div class="borderHover"> 
		       <a class="dropdown-item" data-toggle="modal" data-target="#stakeHolderModal" data-backdrop="static" onclick="getStakeHolder(`+requestRequisitionId+`)" title="Add Stake Holder">
			   <i class="fa fa-users" aria-hidden="true"></i>Add StakeHolder</a></br>
               </div>`
	}
	
	function SFIdDiv(row) {
		
		var requestRequisitionId = row.requestRequisition.id;
		return  `<div class="borderHover"> 
		       <a class="dropdown-item" data-toggle="modal" data-target="#SFIdModal" data-backdrop="static" onclick="getSFId(`+requestRequisitionId+`)" title="Update Success Factor Id">
			   <i class="fa fa-pencil" aria-hidden="true"></i>Update SF ID</a></br>
               </div>`
	}
	
		 dataTablepd=  $('#records_tableId').dataTable( {
			"bProcessing": true,
	        "bServerSide": true,
	         "sPaginationType" : "full_numbers",	      
	        "sScrollX": "100%",
	        "sScrollY": "250",
	        "sDom": 'RC<"clear"><"top"lp>rt<"bottom"ip<"clear">',
	        "sAjaxSource": 'populatedRRFTable',	     
	       "fnServerData": function ( sSource, aoData, fnCallback,oSettings  ) {
	        	$.ajax( {
	        	"dataType": 'json', 
	        	"type": "GET", 
	        	"url": sSource, 
	        	"data": aoData, 
	        	 "success": function(data){
	        		 
	        		 var parsedAAData = JSON.parse(data.aaData);
	        		 
	        		data.aaData = parsedAAData;	        		
		        	fnCallback(data);
	        		}
	        	} ); 
	        },
	        "aoColumns": [
	        	 
      	      	{ mData:'id',mRender:function ( id, type, row ){	     
	        		 
	                		var status = row.status;
	                		var requestRequisitionId = row.requestRequisition.id;
	                		var sentToList = row.sent_req_id;
	                		var pdlList = row.pdl_list;
	                		var resourcesCount = row.submissions;
	                		var reqSkillName = row.requirementId;
	                		
	                		var statusName= 'All Resources';
	       	      		 	var status = '0';
       	      				var rrfStatus = row.status.toLowerCase();
	       	      		 	var rowId = row.id;
	       	      			var reqId = row.requirementId;
	                		var submissions = row.submissions;	                		 
	                		 var actiondiv = `<div class="stop_click action-dropdown dropbtn dropdowns"></div>`;
	                		 return createAction(row);
						
     	      		},"bSortable": false
     	      	 }
	        	 ,
      	      	 { mData:'requirementId',mRender:function ( requirementId, type, row ){
           		 	//This is RRF ID
      	      		return '<div class="textwraps">'+requirementId+'</div>';
           		 	
	      			},"bSortable": false
	      	    },
	      	  { mData:'allocationType.aliasAllocationName',mRender:function ( aliasAllocationName, type, row ){
	       		 	return ""+aliasAllocationName;
		      			},"bSortable": false
		      	    },
		      { mData:'requestRequisition.successFactorId',sDefaultContent: "NA", mRender:function ( successFactorId, type, row ){
		      			return "" + successFactorId;
			    	},"bSortable": false
			 	 },
	      	  { mData:'designation.designationName',mRender:function ( designation, type, row ){
       		 	return ""+designation;
	      			},"bSortable": false
	      	    },
	      	  { mData:'requestRequisition.hiringBGBU',sDefaultContent: "NA",mRender:function ( hiringBGBU, type, row ){
	      		  if(hiringBGBU=="NA")
	      			return "";
	       		 	return ""+hiringBGBU;
		      			},"bSortable": false
		      	    },
	      	  { mData:'requestRequisition.projectBU',sDefaultContent: "NA",mRender:function ( projectBU, type, row ){
	      		  if(projectBU=="NA")
	      			return "";
	       		 	return ""+projectBU;
		      			},"bSortable": false
		      	    },
	      	  { mData:'skill.skill',mRender:function ( skill, type, row ){
         		 	return ""+skill;
	      			},"bSortable": false
	      	    },
	      	  { mData:'noOfResources',mRender:function ( noOfResources, type, row ){
         		 return '<div class="align-center stop_click"><span class="open_pos_popup align-center">'+noOfResources+'</span></div>';	
	      			},"bSortable": false
	      	    },
	      	  { mData:'open',mRender:function ( open, type, row ){
	      		return '<div class="align-center"><span class="open_pos_popup stop_click">'+open+'</span></div>';
	      			},"bSortable": false
	      	    },
	      	  { mData:'submissions',mRender:function ( submissions, type, row ){
	      		var statusName= 'Submitted Resources';
	      		 var status = '0';
	      		 var rowId = row.id;
	      		var reqId = row.requirementId;
	      		return '<div class="align-center"><a href="#myPreviewModal" onclick="onClickResourceProfile(\''+statusName+'\',\''+status+'\','+rowId+',\''+reqId+'\',\''+submissions+'\')"><span class="open_pos_popup align-center color-lightblue">'+submissions+'</span></a></div>';
		      			},"bSortable": false
		      	    },
		      	  { mData:'shortlisted',mRender:function ( shortlisted, type, row ){
		      		var statusName= 'Shortlisted Resources';
		      		 var status = '2';
		      		 var rowId = row.id;
		      		var reqId = row.requirementId;
		      		return '<div class="align-center"><a href="#myPreviewModal" onclick="onClickResourceProfile(\''+statusName+'\',\''+status+'\','+rowId+',\''+reqId+'\',\''+shortlisted+'\')"><span class="open_pos_popup align-center color-orange">'+shortlisted+'</span></a></div>';
			      			},"bSortable": false
			      	    },
	      	  { mData:'closed',mRender:function ( closed, type, row ){
	      		 var statusName= 'Joined Resources';
	      		 var status = '8';
	      		 var rowId = row.id;
	      		var reqId = row.requirementId;
	      		return '<div class="align-center"><a href="#myPreviewModal" onclick="onClickResourceProfile(\''+statusName+'\',\''+status+'\','+rowId+',\''+reqId+'\',\''+closed+'\')"><span class="open_pos_popup align-center color-brown">'+closed+'</span></a></div>';
	      			},"bSortable": false
	      	    },
	      	  
	      	 { mData:'notFitForRequirement',mRender:function ( notFitForRequirement, type, row ){
	      		 var statusName= 'Not Fit Resources';
	      		 var status = '3,6';
	      		 var rowId = row.id;
	      		var reqId = row.requirementId;
	      		return '<div class="align-center"><a href="#myPreviewModal" onclick="onClickResourceProfile(\''+statusName+'\',\''+status+'\','+rowId+',\''+reqId+'\',\''+notFitForRequirement+'\')"> <span class="open_pos_popup align-center color-grey">'+notFitForRequirement+'</span></a></div>';
		      			},"bSortable": false
		      	    },
		      	  { mData:'hold',mRender:function ( hold, type, row ){
		      		return '<div class="align-center"><span class="open_pos_popup">'+hold+'</span></div>';
		      			},"bSortable": false
		      	    },
		      	  { mData:'lost',mRender:function ( lost, type, row ){
	         		 	return '<div class="align-center"><span class="open_pos_popup">'+lost+'</span></div>';
		      			},"bSortable": false
		      	    },
		      	  { mData:'requestRequisition.requestor.firstName',sDefaultContent: "NA" ,mRender:function ( firstName, type, row ){
		      		  var lastName = row.requestRequisition.requestor.lastName;
		      		  
		      		  if(lastName!='' || lastName != 'undefined'){
		      			return '<div style="text-align:left !important;">'+firstName + " " + lastName+'</div>';
		      		  }else
		      			return '<div style="text-align:left !important;">'+firstName+'</div>';
		      		 
			      	},"bSortable": false
			       },
		      	  { mData:'requestRequisition.comments',mRender:function ( additionalComments, type, row ){
		      		var rowId = row.id;
		      		<!-- Modal -->
					
		      		return '<div class="textwrap" data-toggle="modal" data-target="#myModal'+rowId+'">'+additionalComments+'</div>'+
		      		'<div id="myModal'+rowId+'" class="modal fade" '+
					'role="dialog">'+
					'<div class="modal-dialog modal-sm">'+

						<!-- Modal content-->
					'	<div class="modal-content">'+
					'		<a href="#" onclick="closePopup() data-dismiss="modal""><span class="close"'+
					'			data-dismiss="modal" id="close-icon"'+
					'			style="right: 20px; top: 18px;"></span></a>'+
					'		<div class="modal-header headerStyle">'+
					'			<h4 class="modal-title">Additional Comments</h4>'+
					'		</div>'+
					'		<div class="modal-body model-padding">'+
					'			<p class="p-whitespace">'+additionalComments+'</p>'+
					'		</div>'+
					'		<div class="modal-footer">'+
					'			<button type="button" class="btn btn-default"'+
					'				data-dismiss="modal">Close</button>'+
					'		</div>'+
					'	</div>'+
					'</div>'+
				'</div>';
		      		  
			      			},"bSortable": false
			      	    },
			      	  { mData:'status',mRender:function ( status, type, row ){
			      		
			      		 // Calculate RRF Status start
			      		  var position = row.noOfResources;
			      		 var open = row.open;
			      		 var closed = row.closed;
			      		 var hold = row.hold;
			      		 var lost = row.lost;
			      		 var shortlisted = row.shortlisted;
			      		  
			      		 if(position - (hold + lost + shortlisted + closed) > 0){
			      			status = 'OPEN'; 
			      		 }else if((position - lost) == 0){
			      			status = 'LOST';
			      		 }else if(((position -hold) == 0) || ((position-(hold+lost) == 0)) && (hold != 0)){
			      			status = 'HOLD';
			      		 }else if((position - (hold + lost + closed)) <= 0 ){
			      			status = 'CLOSED';
			      		}else if((position - (hold + lost + shortlisted + closed)) <= 0){
			      			status = 'FULLFILLED';
			      		} 
			      		
			      		 // Calculate RRF Status end
			      		 
			      		return ""+status;
				      			},"bSortable": false
				      	    },
				      	    {
				      	    	mData:'requirementArea', mRender:function ( requirementArea, type, row ){
				      	    		if(requirementArea!=null){
				      	    			return requirementArea;
				      	    		}else{
				      	    			return "NON_SAP";
				      	    		}
				      	    	},"bSortable": false
				      	    }
	        	
	        ]
		});
	$("thead input").keyup( function(i){
	    	
	    	if(i.which==13||(i.which==8 && this.value.length==0))
	    		{
	    		
	    		dataTablepd.fnFilter( this.value.trim(), dataTablepd.oApi._fnVisibleToColumnIndex(dataTablepd.fnSettings(), $("thead input").index(this) ) );
	    		}
	    		});
		//Code to populate DataTable - Stop
		
		$('#example-sort').change(function() {

    var col = $(this).val();
    dataTablepd.fnSort([ [ col, 'asc'] ]);


});    
	
	
		$("#client").on('change', function(){
			getGroupForCustomer();
			if($('#client').multiselect("widget").find("input:checked").length > 1 ){
				 $("#group").multiselect("disable"); 
			 }else{
				 $("#group").multiselect("enable");  
			 }
		})
		var isClick=true; 
		$(".action-dropdown").on('click', function(){
			if(isClick){
				//$(this).css("background", "#d9ebff");
				//$(this).css("cursor", "pointer");
				//$(this).css("transition", "0.5s");
				//$(this).css("color", "#5189c2");
				//for apply the style to sibblings
				//$(this).siblings().css("background", "#d9ebff");
				//$(this).siblings().css("cursor", "pointer");
				//$(this).siblings().css("transition", "0.5s");
				//$(this).siblings().css("color", "#5189c2");
				isClick=false;
			}
			else{
				$(this).css("background", "");
				$(this).css("cursor", "");
				$(this).css("transition", "");
				$(this).css("color", "");
				//for apply the style to sibblings
				$(this).siblings().css("background", "");
				$(this).siblings().css("cursor", "");
				$(this).siblings().css("transition", "");
				$(this).siblings().css("color", "");
				isClick=true;
			}
		});
		
		$(".additionalDataClick").bind('click', function(){		
		});

		$("#internalUserIds").select2({
			tags: true,
			openOnEnter: false,
			ajax: {
				url: "activeUserList",
				dataType: 'json',
				data: function (params) {
					 
					return {
						userInput: params.term || '',
					};
				},
				processResults: function (data, params) {
					return {
						results: formatDataForForwardRRF(JSON.parse(data.activeUserList)),
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
		

		function formatDataForForwardRRF(userList) {
			$.map(userList, function(item, idx) {
				item.id = item.emailId;
			 	item.text = item.firstName + " " + item.lastName;
			});

			return userList;
		}
		$("#internalUserIdsInterview").select2({
			tags: true,
			openOnEnter: false,
			ajax: {
				url: "activeUserList",
				dataType: 'json',
				data: function (params) {
					 
					return {
						userInput: params.term || '',
					};
				},
				processResults: function (data, params) {
					return {
						results: formatDataForScheduleInterview(JSON.parse(data.activeUserList)),
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
		

		function formatDataForScheduleInterview(userList) {
			$.map(userList, function(item, idx) {
				item.id = item.emailId;
			 	item.text = item.firstName + " " + item.lastName;
			});

			return userList;
		}
		
		function formatData(userList) {
			$.map(userList, function(item, idx) {
				item.id = item.employeeId;
			 	item.text = item.firstName + " " + item.lastName;
			});

			return userList;
		}
		
		function formatDataForForwardRRF(userList) {
			$.map(userList, function(item, idx) {
				item.id = item.emailId;
			 	item.text = item.firstName + " " + item.lastName;
			});

			return userList;
		}
	});
	

	
	function commentClassForAjaxClick(resourceId, resourceName, skillReqName){
		
		$('#resCMId').val(resourceId);
		var resourceName=resourceName;
		$('#getResultDiv').html('');
		$.ajax({
	    	   type: 'GET',
	    	   url: 'loadResourceCommentByResourceId',
	    	   dataType: "text",
	    	   data: {"resourceId":resourceId},
	    	   success: function(response){
	    		   arrob = JSON.parse(response);
	    		   var comment = "";
					//var customer = "- Customer with Id = " + i + ", firstname = " + customer.firstname + ", lastName = " + customer.lastname + "<br>";	
					$.each(arrob, function(intIndex, objComment){
						var date=new Date(objComment.comment_Date);
						var day=date.getDate();
						var month=date.getMonth() + 1;
						var year =date.getFullYear();
						var hours =date.getHours();
						var minutes =date.getMinutes();
						var seconds =date.getSeconds();
						
						
						if (month.toString().length < 2)
							month = '0' + month;
					    if (day.toString().length < 2) 
					    	day = '0' + day;
					    
					    if (hours.toString().length < 2) 
					    	hours = '0' + hours;
					   
					    if (minutes.toString().length < 2) 
					    	minutes = '0' + minutes;
					    
					    if (seconds.toString().length < 2) 
					    	seconds = '0' + seconds;
										
						comment = "<div class='commentText'><p>"+objComment.resourceComment+"</p> <footer>From " + objComment.commentBy + " : " +" " + day+"-"+month+"-"+year+" " +hours+":"+minutes+":"+seconds+"</footer></div>";
											
						
						$('#getResultDiv').append(comment) 
					});
					
					//var requirementID = $("#requirementID").val();
					//alert(requirementID);
				    
					$("#reqNameId").text( skillReqName + " : " + resourceName);//+ resourceId
					$("#resourceCommentModal").removeClass("fade");
					$("#resourceCommentModal").show();  
	    	   },
			   error:function(response){
				  
			   },
			   complete:function(){
				   $('.commentmodal .modal-body').scrollTop($('.commentmodal .modal-body')[0].scrollHeight);
				   $('#usr').focus();
			   }
		   });
	}
	var  mailtodata; 
	var  pdldata;
	var notifydata;

	  function scheduleInterview(skillid, requestResourceId) {
	   
	  
	    var modal = document.getElementById('interviewModal');
	    modal.style.display = "block";
	    document.getElementById('requestResourceId').value = requestResourceId;
	    $.ajax({
	        type: 'GET',
	        url: 'getDataForScheduleInterview/'+skillid+'/'+requestResourceId,
	       
	        cache: false,
	        contentType: "application/json; charset=utf-8",
	        cache: false,
	        success: function (response) {
	              
	              var parsedData = JSON.parse(response);
	              $('#bgbu').val(parsedData.requestorsBGBU);
	              $('#reqid').val(parsedData.requirementID);
	              $('#customerName').val(parsedData.customerName);
	              $('#location').val(parsedData.jobLocation);
	              $('#resourcename').val(parsedData.resourceName);
	              $('#employeeid').val(parsedData.resourceEmpId);
	              $('#interviewdate').val(parsedData.interviewDate);
	              $('#interviewtime').val(parsedData.interviewTime);
	              $('#interviewmode').val(parsedData.interviewMode);
	              $('#deliveryPOCName').val(parsedData.deliveryPOCName);
	              $('#deliveryPOCCont').val(parsedData.deliveryPOCContact);
	              $('#amPOCName').val(parsedData.aMPOCName);
	              $('#amPOCCont').val(parsedData.aMPOContact);
	              $('#custPOCName').val(parsedData.customerPOCName);
	              $('#custPOCCont').val(parsedData.customerPOCContact);
	              $('#venue').val(parsedData.venue);
	              $('#gatenumber').val(parsedData.gatePassNumber);
	              $('#skill').val(parsedData.skillCategory);
	              $('#jobdesc').val(parsedData.jobDescription);
	              mailtodata = parsedData.mailsToMailIds; 
	               
	              notifydata = parsedData.notifyToMailIds; 
	              pdldata  = parsedData.pdlsToMailIds; 
				  
				  $('#mailTo, #notifyTo, #pdlsTo').prop('checked', true).trigger('change');
	        
	         
	        },
	        error: function (error) {
	          showError("Something went wrong!!");
	        }
	      });
	    
	    
	  }
	  function closeInterviewPopup() {
	    $("#internalUserIdsInterview option:selected").prop("selected", false);
	    $("#contactPerson option:selected").prop("selected", false);
	    $('#mailTo').prop('checked', false);
	    $('#notifyTo').prop('checked', false);
	    $('#pdlsTo').prop('checked', false);
	    $("#internalUserIdsInterview").multiselect('refresh');
	    $("#contactPerson").multiselect('refresh');
	    // document.getElementById("forward-btn").disabled = false;
	    var modal = document.getElementById('interviewModal');
	    $('#mailText').val('');
	    modal.style.display = "none";

	    $('#internalUserIdsInterview').val(null).trigger('change');
	    $('#contactPerson').val(null).trigger('change');
	  }

	  $(document).on('change', '#discussionType', function () {
		  var value = $('#discussionType').val();
		  if(value == 'yashInternal'){
			  $('#contactPersonDiv').css("display", "block" );
			  $('#contactPersonNumbDiv').css( "display", "block" );
			  
			  $('#custPOCNameDiv').hide();
			  $('#venueDiv').hide();
			  $('#gatenumberDiv').hide();
			  $('#custPOCContDiv').hide();
		  } else {
			  $('#custPOCNameDiv').show();
			  $('#venueDiv').show();
			  $('#gatenumberDiv').show();
			  $('#custPOCContDiv').show();
			  
			  $('#contactPersonDiv').css("display", "none" );
			  $('#contactPersonNumbDiv').css( "display", "none" );
		  }
		  
	  });
	  
	  
	  function onScheduleInterviewSubmit() {
	    var internalUserIds = document.getElementById("internalUserIdsInterview");
	    var notifyTo = document.getElementById("notifyTo");
	    var sendMailTo = document.getElementById("mailTo");
	    var pdlsTo = document.getElementById("pdlsTo");
	    var mailText = document.getElementById("mailText").value;
	    var requestResourceId = document.getElementById('requestResourceId').value;
	    
	    var interviewDate =  $('#interviewdate').val();
	    var interviewMode = $('#interviewmode').val();
	    var interviewTime = $('#interviewtime').val();
	    var amPOCName = $('#amPOCName').val();
	    var amPOCCont = $('#amPOCCont').val();
	    var custPOCName = $('#custPOCName').val();
	    var venue = $('#venue').val();
	    var gatenumber = $('#gatenumber').val();
	    var jobdesc = $('#jobdesc').val();
	       var custPOCCont = $('#custPOCCont').val();
		var discussionType = $('#discussionType').val();
	    
	    var validationFlag = true;
	    if (!notifyTo.checked && !sendMailTo.checked && !pdlsTo.checked && !sendMailTo.checked && internalUserIds.value =="") {
	      showError("Please select atleast one email address");
	      validationFlag = false;
	    }
	    
	    if(discussionType==null){
	    	 showError("Please select one discussion Type");
	         validationFlag = false;
	    }
	    
	    if(discussionType=="client" && venue==""  ){
	    	showError("Please input venue details as the discussion is with client!");
	        validationFlag = false;
	    }
	    if(discussionType=="yashInternal" && $('#contactPerson').val() == null  ){
	    	showError("Please input contact person details as the discussion is Internally in Yash!");
	        validationFlag = false;
	    }
	    if(interviewDate==""   ){
	    	showError("Please provide Interview date to proceed further");
	        validationFlag = false;
	    }
	    if(interviewMode==null ){
	    	showError("Please provide Interview Mode to proceed further");
	        validationFlag = false;
	    }
	    if(interviewTime=="" ){
	    	showError("Please provide Interview Time to proceed further");
	        validationFlag = false;
	    }
	    
	    if (notifyTo.checked) {
	      notifyTo = true;
	    } else {
	      notifyTo = false;
	    }
	    if (sendMailTo.checked) {
	      sendMailTo = true;
	    } else {
	      sendMailTo = false;
	    }
	    if (pdlsTo.checked) {
	      pdlsTo = true;
	    } else {
	      pdlsTo = false;
	    }
	   /*  if (mailText.length <= 0) {
	      showError("Email cannot be sent without content");
	      validationFlag = false;
	    } */
	    if (mailText.length >= 5000) {
	      showError("Word Limit Exceeds 5000 characters");
	      validationFlag = false;
	    }
	    if(amPOCCont.length>10 ){
	        showError("Please enter valid phone number");
	         validationFlag = false;
	    }
	    if(custPOCCont.length>10){
	        showError("Please enter valid phone number");
	        validationFlag = false;
	   }
	    

	   /*  if (!(/\S/.test(mailText))) {
	      showError("Email cannot be sent without content");
	      validationFlag = false;
	    } */

	    var internalUserEmailIds = "";
	    $("#internalUserIdsInterview option:selected").each(function () {
	      var id = $(this).val();
	      internalUserEmailIds = id + "," + internalUserEmailIds;
	    });
	    
	    var contactPersonIds = "";
	    $("#contactPerson option:selected").each(function () {
	      var id = $(this).val();
	      contactPersonIds = id + "," + contactPersonIds;
	    });
	    var data = {
	      "userIdList": internalUserEmailIds,
	      "notifyTo": notifyTo,
	      "sendMailTo": sendMailTo,
	      "pdlsTo": pdlsTo,
	      "requestResourceId": requestResourceId,
	      "mailText": mailText, 
	      "jobdesc" : jobdesc,
	      "interviewDate" : interviewDate,
	      "interviewMode" : interviewMode,
	      "interviewTime" : interviewTime,
	      "amPOCName" : amPOCName,
	      "amPOCCont" : amPOCCont,
	      "custPOCName" : custPOCName,
	      "venue" : venue,
	      "gatenumber" : gatenumber,
	      "customerPOCContact" : custPOCCont, 
	      "contactPersonIds" : contactPersonIds, 
	      "discussionType" : discussionType
	    }
	    //console.log(data);
	    if (validationFlag) {
	      $.ajax({
	        type: 'POST',
	        url: 'sendemailforinterview',
	        data: JSON.stringify(data),
	        cache: false,
	        contentType: 'application/json',
	        success: function (response) {
	          $("#internalUserIdsInterview option:selected").prop("selected", false);
	          $('#mailTo').prop('checked', false);
	          $('#notifyTo').prop('checked', false);
	          $('#pdlsTo').prop('checked', false);
	          var modal = document.getElementById('interviewModal');
	          $('#mailText').val('');
	          modal.style.display = "none";
	          showSuccess("Email Of The Interview Schedule Sent Successfully!");
	          $("#internalUserIdsInterview").val(null).trigger('change');
	          $("#contactPerson").val(null).trigger('change');
	        },
	        error: function (error) {
	          showError("Something went wrong!!");
	        }
	      });
	    }
	  }
	
	
	function strCSubmit(){
	  var strComment = $("#usr").val().trim();
	  var intResourceId = $("#resCMId").val().trim();
	  
	  var headerVal= $('#reqNameId').text().split(': ')[0].trim();
	 
	  var resourceComment = {
			  "resourceComment" : strComment,
			  "resourceId" : intResourceId
	  }
	  
	  if($("#usr").val()!= "" ){
		  if($("#usr").val().length <= 500){
	  
	  $.ajax({
	        type: "POST",
	        url: 'postResourceComment',
	        contentType:'application/json; charset=utf-8',
	        dataType:'json',
	        data: JSON.stringify(resourceComment),    
	        success: function () {  
	     	   //showSuccess("Your Comment is Saved Successfully!");	        	  
	        },
	        complete: function(){
	     	 callGetAjax(intResourceId, headerVal);        	  
	        }
	    });
	  //showSuccess("Your Comment is Saved Successfully!");	 
		  }
	  else
		   showError("Please enter character less than or equal to 500 characters");		  
	  }
	  else
		   showError("Please add comment!");
	  $("#strCSubmit").attr('disabled',true);
	    	  setTimeout(function(){
	    		  $("#strCSubmit").attr('disabled',false);
	    		  $('.toast-close').click();
	    	  }, 2000);	  
	  
	    $("#usr").val('');
			
		  
	}
		
	var callGetAjax = function(id, headerVal){
	  var resourceId = id;
	  var resourceName = $('#reqNameId').text().split(': ')[1].trim();
	  $('#getResultDiv').html('');
	  $.ajax({
	   	   type: 'GET',
	   	   url: 'loadResourceCommentByResourceId',
	   	   dataType: "text",
	   	   data: {"resourceId":resourceId},
	   	   success: function(response){
		   		   arrob = JSON.parse(response);
		   		   var comment = "";
					//var customer = "- Customer with Id = " + i + ", firstname = " + customer.firstname + ", lastName = " + customer.lastname + "<br>";	
					$.each(arrob, function(intIndex, objComment){
						var date=new Date(objComment.comment_Date);
						
						var day=date.getDate();
						var month=date.getMonth() + 1;
						var year =date.getFullYear();
						var hours =date.getHours();
						var minutes =date.getMinutes();
						var seconds =date.getSeconds();
						
						if (month.toString().length < 2)
							month = '0' + month;
					    if (day.toString().length < 2) 
					    	day = '0' + day;
					    
					    if (hours.toString().length < 2) 
					    	hours = '0' + hours;
					   
					    if (minutes.toString().length < 2) 
					    	minutes = '0' + minutes;
					    
					    if (seconds.toString().length < 2) 
					    	seconds = '0' + seconds;
										
						comment = "<div class='commentText'><p>"+objComment.resourceComment+"</p> <footer>From " + objComment.commentBy + " : " +" " + day+"-"+month+"-"+year+" " +hours+":"+minutes+":"+seconds+"</footer></div>";
						
						$('#getResultDiv').append(comment) 
					});
					var requirementID = $("#requirementID").val();
					//alert(requirementID);
			       // $("#reqNameId").text( $('.ReqID').text().split(':')[1] + " :: " + resourceName);//+ resourceId
			        $("#reqNameId").text( headerVal + " : " + resourceName);//+ resourceId
					$("#resourceCommentModal").removeClass("fade");
					$("#resourceCommentModal").show();  
	   	   		},
			   error:function(response){
				  
				  
			   },
			   complete: function(){
				   $('.commentmodal .modal-body').scrollTop($('.commentmodal .modal-body')[0].scrollHeight);
				   $('#usr').focus();
			   }
		   });
	}
	
	
	 
	 // close popup comment box.
	
	function onClickclsComment(){
	
		document.getElementById("strCSubmit").disabled = false;
		$("#usr").val('');
		$("#resourceCommentModal").addClass("fade");
		$("#resourceCommentModal").hide();  
	}
	
	
	
	var statusOption = [];
	var statusLabel = [];
	
	var resourceCounter = 0;
	
	function intializeVariable(){
		$.ajax({
			type: 'GET',
	        url: 'reportStatusList',
	        contentType: 'application/json',
	        dataType: 'json',
	        async:false,
	     	success: function(response) { 	
	     		var data  = JSON.parse(response.reportStatusList);
	     		createDataFromintializeVariable(data);
	     	}, 
	     	error : function(response){
	     		showAlert("Err.. Looks like there is an error! Please try again after sometime. ")
	     	}
		});
	}
	function createDataFromintializeVariable(data){
		var initializeList = data;
		if(initializeList!=null){
			if(statusOption.length<=0){
			for(var i = 0;i<initializeList.length;i++){
				var initValueObj = initializeList[i];
				var initValueObjID = initValueObj.id;
				var initValueObjStatusName = initValueObj.statusName;
				
				
						statusOption.push(initValueObjID);
						statusLabel.push(initValueObjStatusName);
				
			}	
			}
		}
		
	};
	
function updateResourceStatus()
	{
		var resourceStatus = [];
		var e ="";
		var profileStaus ="";
		var interviewDate="";
		var resourceId="";
		var skillReqId="";
		var allocationStartDate="";
		
		for(var i = 0; i< resourceCounter;i++){
			
			 e = document.getElementById("statusId"+i);
			 profileStaus = e.options[e.selectedIndex].value;
					
			//interviewDate=document.getElementById("dateStatus"+i).value;
			resourceId=document.getElementById("resourceId"+i).value;
			skillReqId=document.getElementById("skillReqId"+i).value;
			
			interviewDate="";
			if(profileStaus==9)
			{
				if(document.getElementById("statusChange"+i)!=null)
					interviewDate=document.getElementById("statusChange"+i).value; 
			}
			allocationStartDate="";
			
			if(profileStaus==2){
				if(document.getElementById("statusChange"+i)!=null)
					allocationStartDate=document.getElementById("statusChange"+i).value; 
				else
					allocationStartDate="";
			}
			
			if(profileStaus==8){
				if(document.getElementById("statusChange"+i)!=null)
					allocationStartDate=document.getElementById("statusChange"+i).value; 
			}
			
				
			
			resourceStatus.push({"resourceId":new Number(resourceId),"profileStaus" :new Number(profileStaus) ,"interviewDate" :interviewDate,"allocationStartDate":allocationStartDate, "skillReqId":skillReqId});
			
		}
		
		
		$.ajax({
			type: 'POST',
	        url: 'updateResourcesWithStatus',
	        contentType: 'application/json',
	        dataType: 'json',
	        async:false,
	        processData: false,
	     	data: JSON.stringify(resourceStatus),
	     	success: function(response) { 	
	     			closePopup();
	     			showSuccess("Resource Status updated successfully !");
	     			
	     			 window.location.reload();
	     			/* if(typeof document.getElementById("openId"+response[0].id).childNodes[0]!='undefined'&&typeof document.getElementById("openId"+response[0].id).childNodes[0]!='null'){
	     				document.getElementById("openId"+response[0].id).childNodes[0].innerHTML=response[0].open;	
	     			}
	     			if(typeof document.getElementById("closedId"+response[0].id).childNodes[1]!='undefined'&&typeof document.getElementById("closedId"+response[0].id).childNodes[1]!='null'){
	     				document.getElementById("closedId"+response[0].id).childNodes[1].childNodes[1].innerHTML=response[0].closed;	
	     				document.getElementById("closedId"+response[0].id).childNodes[1].onclick = function() {
		      			 	onClickResourceProfile('Joined Resources','8',response[0].id,response[0].requirementId,response[0].closed);
		      			 };
	     			}
	     			if(typeof document.getElementById("shortlistedId"+response[0].id).childNodes[1]!='undefined'&&typeof document.getElementById("shortlistedId"+response[0].id).childNodes[1]!='null'){
	     				document.getElementById("shortlistedId"+response[0].id).childNodes[1].childNodes[1].innerHTML=response[0].shortlisted;
	     				document.getElementById("shortlistedId"+response[0].id).childNodes[1].onclick = function() {	      					
		      			 	 onClickResourceProfile('Shortlisted Resources','2',response[0].id,response[0].requirementId,response[0].shortlisted)
		      			 };
	     			}
	   				if(typeof document.getElementById("rejectId"+response[0].id).childNodes[1]!='undefined'&&typeof document.getElementById("rejectId"+response[0].id).childNodes[1]!='null'){
	   					document.getElementById("rejectId"+response[0].id).childNodes[1].childNodes[1].innerHTML=response[0].notFitForRequirement;
	   					document.getElementById("rejectId"+response[0].id).childNodes[1].onclick = function() {
		   			 		onClickResourceProfile('Not Fit Resources','3,6',response[0].id,response[0].requirementId,response[0].notFitForRequirement)
		   			 	 };
	   				}
	   				document.getElementById("statusId"+response[0].id).innerHTML=response[0].status;
	      			 $('#updateResPopUp').modal('hide'); 
 */	      			
	      			 
	     	},
	     	 error:function(data){
	     		 showError("Some thing went wrong !");
	     		 $('#updateResPopUp').modal('hide'); 
	     		 //window.location.href  = "positionDashboard";
		    }   	
	 	}); 
		
		
	}
	function prepareEmailIdTD(emailId){
		if(emailId!="" && emailId!=null && emailId!="undefined")
			return emailId;
		return "";
	}
	function prepareContactNumTD(contactNum){
		if(contactNum!="" && contactNum!=null && contactNum!="undefined")
			return contactNum;
		return "";
	}
	function prepareLocationTD(location){
		if(location!="" && location!=null && location!="undefined")
			return location;
		return "";
	}
	function prepareSkillTD(skill){
		if(skill!="" && skill!=null && skill!="undefined")
			return skill;
		return "";
	}
	
	function prepareActionComments(resourceId, internalResId, resourceType, resourceName, skillReqName, skillReqId){
		
		var tdData = "";
		
		tdData='<a href="#" class="pointer action_btn" onclick="fileDownload('+internalResId+',`'+resourceType+'`)" title="Download Resume"><i class="fa fa-download" aria-hidden="true"></i></a>';
		
		if(resourceType=="External")
			tdData = tdData+'<a  href="#" class="pointer action_btn" onclick="tacDownload('+resourceId+')" title="Download TEF"><i class="fa fa-download" aria-hidden="true"></i></a>';
		
		tdData += '<a class="pointer action_btn" data-toggle="modal"  title="Comments" onClick="commentClassForAjaxClick('+resourceId+',`'+resourceName+'`,`'+skillReqName+'`)"> <i class="fa fa-commenting  commentClassForAjax" aria-hidden="true"></i></a>';
		tdData += '<a class="pointer action_btn" data-toggle="modal"  title="Click To Schedule Interview" onClick="scheduleInterview('+skillReqId+','+resourceId+')"> <i class="fa fa-lg fa-clock-o fa-4" aria-hidden="true"></i></a>';
		
		
		return tdData;
	
	}
	function preparenoticePeriod( noticePeriod){
		var tdData = "";
		if(noticePeriod!="" && noticePeriod!=null )
			tdData= "<center>"+noticePeriod+"</center>";
		return tdData;
	}
	function prepareTotalExp(totalExp){
		var tdData = "";
		if(totalExp!="" && totalExp!=null )
			tdData= "<center>"+totalExp+"</center>";
		return tdData;
	}
	function prepareProfileStatus(id, selectedStatus,joinDate,interviewDate, resourceCounter, resourceName, skillReqName){
		intializeVariable();
		var tdData = "";
			tdData = "<input>"	
		if(selectedStatus=="JOINED")
			tdData = '<select id="statusId'+resourceCounter+'" onchange="statusChange(this.value,'+resourceCounter+',`'+joinDate+'`,`'+interviewDate+'`)" style="width: 50px !important; min-width: 150px;" disabled>';
		else{
			
			tdData = '<select id="statusId'+resourceCounter+'" onchange="statusChange(this.value,'+resourceCounter+',`'+joinDate+'`,`'+interviewDate+'`)" style="width: 50px !important; min-width: 150px;">';
		}
			
		for(var i = 0; i< statusLabel.length;i++){
			if(selectedStatus==statusLabel[i]){
				tdData = tdData+'<option value="'+statusOption[i]+'" selected>'+statusLabel[i]+'</option>';
						
			}else{
				tdData = tdData+'<option value="'+statusOption[i]+'">'+statusLabel[i]+'</option>';
			}
		}
		tdData = tdData+'</select><br/>';
		
		//tdData = tdData+ '<a class="pointer action_btn" data-toggle="modal"  onClick="commentClassForAjaxClick('+id+',`'+resourceName+'`,`'+skillReqName+'`)"> <i class="fa fa-commenting  commentClassForAjax" aria-hidden="true"></i></a>';
	 	
		tdData = tdData + '<input id="resourceId'+resourceCounter+'" type="hidden" value="'+id+'">';
		
		return tdData;
	} 
	
	function prepareDate(id,selectedStatus,joinDate,interviewDate, resourceCounter,skillReqId){
	 	
		var tdData ="";
	 		
	 	if(selectedStatus=="JOINED")
	 		tdData='<div id="dateStatus'+resourceCounter+'"> Joining Date '+joinDate+'</br> Interview Date '+interviewDate+'</br></div>';
	 	else if(selectedStatus=="INTERVIEW DONE - FEEDBACK PENDING")
	 		tdData='<div id="dateStatus'+resourceCounter+'"> Interview Date '+interviewDate+'</br></div>';
	 	
	 	else if(selectedStatus=="INTERVIEW LINED UP"){
	 		//tdData='<div id="dateStatus'+resourceCounter+'"> Interview Date '+interviewDate+'</br></div>';
	 		tdData='<div id="dateStatus'+resourceCounter+'" >';//onclick='datePicker("+resourceCounter+",`"+interviewDate+"`)'
	 		tdData = tdData+ "<input type='text' class='text-width' value=' Interview Date "+interviewDate+"' >";
	 		tdData = tdData+"<span class='action_btn'><i class='fa fa-calendar commentClassForAjax' data-id='statusChange"+resourceCounter+"' onclick='datePicker("+resourceCounter+",`"+interviewDate+"`)' aria-hidden='true'></i></span></div>";
	 		//tdData = "<input type='text' onclick='datepickernew("+id+")' value='"+interviewDate+"' id='dateStatus"+resourceCounter+"' class='datepickernew'>";
	 	}
	 	else if(selectedStatus=="SELECTED"){
	 	 	//tdData='<div id="dateStatus'+resourceCounter+'" onclick="datePicker('+resourceCounter+','+joinDate+')" > Joining Date '+joinDate+'</br></div>';
	 	 	tdData = '<div id="dateStatus'+resourceCounter+'">';//onclick='datePicker("+resourceCounter+",`"+joinDate+"`)' 
	 	 	tdData = tdData+"<input type='text'   class='text-width' value=' Joining Date "+joinDate+"' >";
	 	 	tdData = tdData+"<span class='action_btn'><i class='fa fa-calendar commentClassForAjax' data-id='statusChange"+resourceCounter+"' onclick='datePicker("+resourceCounter+",`"+joinDate+"`)' aria-hidden='true'></i></span></div>";
	 	}
	 	 	 
	 	else
	 		tdData = '<div id="dateStatus'+resourceCounter+'"></div>';
	 	
	 		tdData = tdData+ '<input id="resourceId'+resourceCounter+'" type="hidden" value="'+id+'">';
	 		
	 		tdData = tdData+ '<input id="skillReqId'+resourceCounter+'" type="hidden" value="'+skillReqId+'">';
		
	 	return tdData; 
	}
	function statusChange(statusId, id, joinDate, interviewDate){
		 
		
		var tdData= document.getElementById("dateStatus"+id);
		
		if(statusId==2){
		 
			 
			
			//tdData.innerHTML = "Joining Date: </br><input id='fromdatepicker' type='text' size='8' value='"+ joinDate + "'/>";
			// tdData.innerHTML = "<input type='text' onclick='datepickernew("+id+")' id='statusChange"+id+"' class='datepickernew' value='"+joinDate+"'>";
			tdData.innerHTML = "<input type='text'  id='statusChange"+id+"' class='datepickernew text-width' value='"+joinDate+"'>"+"<span class='action_btn'><i class='fa fa-calendar commentClassForAjax' data-id='statusChange"+id+"' onclick='datepickernew("+id+")' aria-hidden='true'></i></span>";
			
		}
		else if (statusId==9)
			//tdData.innerHTML = "Interview Line Up Date: </br><input type='text' size='8' value='"+interviewDate+"'/>";
			//tdData.innerHTML = "<input type='text' onclick='datepickernew("+id+")' id='statusChange"+id+"' class='datepickernew'>";
			tdData.innerHTML = "<input type='text' id='statusChange"+id+"' class='datepickernew text-width'>"+"<span class='action_btn'><i class='fa fa-calendar commentClassForAjax' data-id='statusChange"+id+"' onclick='datepickernew("+id+")' aria-hidden='true'></i></span>";
		
		else if (statusId==8)
			tdData.innerHTML = "Joining Date: "+joinDate+"<br/> Interview Date:  "+interviewDate;
		
		else if(statusId==14)
			//tdData.innerHTML = "Interview Done - FeedBack Pending: "+interviewDate+"<br/>";
			tdData.innerHTML = "Interview Date: "+interviewDate+"<br/>";
		
		else
			tdData.innerHTML="";
	
	}
	
	$(document).on("click", ".hasDatepicker, .fa-calendar", function(){
	    var test= $(this).attr("id") || $(this).data("id");
	    $("#clearDate").click(function(){
	        $("#"+test).val("");
	        $("#"+test).datepicker('hide');
	    });
	});
	
	function datepickernew(id, date)
	{
		$("#statusChange"+id).datepicker({
			dateFormat: 'd-M-yy',
			setDate:date
		});
		$("#statusChange"+id).datepicker('show');
	}
	
	function datePicker(id, date)
	{
		var tdData= document.getElementById("dateStatus"+id);
		//tdData.innerHTML = "<input type='text' onclick='datepickernew("+id+")' id='statusChange"+id+"' class='datepickernew' value='"+date+"'>";
		tdData.innerHTML = "<input type='text' id='statusChange"+id+"' class='datepickernew text-width' value='"+date+"'>"+"<span class='action_btn'><i class='fa fa-calendar commentClassForAjax' data-id='statusChange"+id+"' onclick='datepickernew("+id+")' aria-hidden='true'></i></span>";
		datepickernew(id, date);
	}
	
	
	 
	function prepareResume(id, resourceType){
		
		var tdData = '';
		
		tdData='<a href="#" class="pointer action_btn" onclick="fileDownload('+id+',`'+resourceType+'`)" title="Download Resume"><i class="fa fa-download" aria-hidden="true"></i></a>';
		
		return tdData;
	}
	function prepareTEF(id,resourceType){
		var tdData = '';
		if(resourceType=="External")
			tdData='<a  href="#" class="pointer action_btn" onclick="tacDownload('+id+')" title="Download TEF"><i class="fa fa-download" aria-hidden="true"></i></a>';
		
		return tdData;
	}
	function fileDownload(id,resourceType){	
		
	 	$.ajax({				
	        url: 'downloadfiles',
	     	contentType: "application/json",
	     	async:false,
	     	data: {"id":id,
					"resourceType":resourceType
				},  	
	     	success: function(response) { 			     			
	     		if(response==""){
	     			showAlert("\u2022 File not found for id " + id + " ! <br />");
	     		}else{     	     			     			     		
	     		    if(id !== ''){
						window.location.href = 'downloadfiles?id='+id+'&resourceType='+resourceType;
					} 
					else{				 
						showAlert("\u2022 File not found for id " + id + " ! <br />");
					}
	    		}
	     	},
	     	 error:function(data){
	     		showAlert("\u2022 File not found for id " + id + " ! <br />");
		    }   	
	 	}) 
	}
	
	function tacDownload(id){	
		 	$.ajax({				
		        url: 'downloadTac',
		     	contentType: "application/json",
		     	async:false,
		     	data: {"id":id				
					},  	
		     	success: function(response) { 			     			
		     		if(response==""){
		     			showAlert("\u2022 File not found for id " + id + " ! <br />");
		     		}else{     	     			     			     		
		     		    if(id !== ''){
							window.location.href = 'downloadTac?id='+id;
						} 
						else{				 
							showAlert("\u2022 File not found for id " + id + " ! <br />");
						}
		    		}
		     	},
		     	 error:function(data){
		     		showAlert("\u2022 File not found for id " + id + " ! <br />");
			    }   	
		 	}) 
		}
	
	
	function prepareTableDataElement(statusId, id , resourcesCount, reqSkillName){
			var tdData = '';
			if(statusId==0){	
				tdData = '<a href="#myPreviewModal" onclick="onClickResourceProfile(`Submitted Resources`,`0`,`'+id+'`,`'+reqSkillName+'`,`'+resourcesCount+'`)">';
				tdData= tdData+'<span class="open_pos_popup align-center color-lightblue">' ;
			}else if(statusId==2){
				tdData = '<a href="#myPreviewModal" onclick="onClickResourceProfile(`Shortlisted Resources`,`2`,`'+id+'`,`'+reqSkillName+'`,`'+resourcesCount+'`)">';
				tdData = tdData+'<span class="open_pos_popup align-center color-orange">';
			}else if(statusId==8){
				tdData = '<a href="#myPreviewModal" onclick="onClickResourceProfile(`Joined Resources`,`8`,`'+id+'`,`'+reqSkillName+'`,`'+resourcesCount+'`)">';
				tdData = tdData+'<span class="open_pos_popup align-center color-brown">';
			}else {
				tdData = '<a href="#myPreviewModal" onclick="onClickResourceProfile(`Not Fit Resources`,`3,6`,`'+id+'`,`'+reqSkillName+'`,`'+resourcesCount+'`)">';
				tdData = tdData+'<span class="open_pos_popup align-center color-grey">';
			}
			tdData = tdData+resourcesCount+'</span>';
			return tdData;
	}
	function prepareAdditionalComments(addComments,id){
		var tdData ="";
		//tdData=  '<div class="tooltip"><div class="textwrap">'+addComments+'</div><span class="tooltiptext">'+addComments+'</span></div>';
		//tdData=  '<div class="textwrap"><a href="#" data-toggle="tooltip" style="margin-left:0" title="${reportData.addtionalComments}">${reportData.addtionalComments}</a></div>';
		/* <div class="textwrap" data-toggle="modal" data-target="#myModal${reportData.id}">${reportData.addtionalComments}</div> */
		tdData='<div class="textwrap" data-toggle="modal" data-target="#myModal'+id+'">'+addComments+'</div><div id="myModal'+id+'" class="modal fade" role="dialog"><div class="modal-dialog modal-sm"><div class="modal-content"><a href="#" onclick="closePopup()"><span class="close" data-dismiss="modal" id="close-icon" style="right: 20px; top: 18px;"></span></a><div class="modal-header headerStyle"><h4 class="modal-title">Additional Comments</h4></div><div class="modal-body model-padding"><p class="p-whitespace">'+addComments+'</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">Close</button></div></div></div></div>';
		return tdData;
	}
	function prepareActions(id, status, requestRequisitionId, sentToList, pdlList, resourcesCount, reqSkillName ){
		
		
		
		var tdData='<div class="dropdown dropleft dropDownHover" onclick="showActionDropdown(this)"><i class="fa fa-ellipsis-v" style="font-size:15px" class="btn dropdown-toggle"></i><div class="dropdown-menu">';
		
		 <sec:authorize access="hasRole('ROLE_ADMIN')">
		tdData = tdData+ '<div class="borderHover"><a class="dropdown-item" onclick="addRemoveResourceInRequestRequisition('+id+')" data-target="#addResource" id="'+id+'" href="javascript:void(0)" >'+
		 '<i class="fa fa-plus-square-o my_submit_btn"></i>Submit Resources</a></br></div>';
	
	</sec:authorize> 
		
	tdData =tdData+'<div class="borderHover"><a class="dropdown-item" data-toggle="modal" data-target="#myModalShowAll" title="Show all resources"onclick="onClickResourceProfile(`All Resources`,`0`,`'+id+'`,`'+reqSkillName+'`,`'+resourcesCount+'`)" >'+
	'<i class="fa fa-users"></i>View all resources</a></br></div>';
		
		tdData=tdData+ '<div class="borderHover"><a class="dropdown-item"  onclick="skillRequestReportPDF(this,'+id+')" title="Download RRF"><i class="fa fa-download"></i>Download RRF</a></br></div>';
		tdData=tdData+'<div class="borderHover"><a class="dropdown-item" href="#" onclick="editrequest(this,'+id+')" title="Edit RRF"><i class="fa fa-edit" aria-hidden="true"> </i>Edit RRF</a></br></div>';
			
		if((sentToList != '' && sentToList != null) || (pdlList != '' && pdlList != null)){         
	    	tdData=tdData+ '<div class="borderHover"><a class="dropdown-item" data-toggle="modal" data-target="#forwrd-rrf-PD"  onClick="forwardRRF('+id+')" title="Forward">'+
	        '<i class="fa fa-share" aria-hidden="true"><span class="orange-batch" id="forwardOrangeId'+id+'"></span></i>Forward</a></br></div>';
	    }else{
	    	tdData = tdData+'<div class="borderHover"><a class="dropdown-item" data-toggle="modal" data-target="#forwrd-rrf-PD"  onClick="forwardRRF('+id+')" title="Forward">'+
	        '<i class="fa fa-share" aria-hidden="true"><span id="forwardOrangeId'+id+'"></span></i>Forward</a></br></div>';
	    }
		tdData = tdData+'<div class="borderHover"><a class="dropdown-item" data-toggle="modal" data-target="#myCommentsModal" onclick="getLatestCommentOnResource('+id+')" title="Comments">'+
		 	'<i class="fa fa-comments" aria-hidden="true"> </i>Comment</a></br></div>';
		 	  <sec:authorize access="hasRole('ROLE_ADMIN')">
		 	tdData = tdData+'<div class="borderHover"><a class="dropdown-item" data-toggle="modal"  onclick="copyRRFMethod('+id+')" title="Copy RRF">'+
		 	'<i class="fa fa-copy" aria-hidden="true"> </i>Copy RRF</a></br></div>';
		 	</sec:authorize>
		 	tdData = tdData+'<div class="borderHover"><a class="dropdown-item" href="#" onclick="BGHfileDownload('+requestRequisitionId+')" title="Download BGH Approval">'+
	    	 '<i class="fa fa-download" aria-hidden="true"> </i>BGH approval</a></br></div>'+
	     	 '<div class="borderHover"><a class="dropdown-item" href="#" onclick="BUHfileDownload('+requestRequisitionId+')" title="Download BUH Approval">'+
	           '<i class="fa fa-download" aria-hidden="true"> </i>BUH approval</a></br></div>';
	           status =status.toLowerCase();
	    <sec:authorize access="hasRole('ROLE_ADMIN')">
			if(status != 'closed'){
				tdData = tdData+'<div class="borderHover"><a onclick="skillRequestReportDelete('+requestRequisitionId+','+id+')" title="Delete RRF"class="dropdown-item" href="#">'+
		        '<i class="fa fa-trash" aria-hidden="true"> </i>Delete</a></br></div>';
			}
		</sec:authorize>	
		/* tdData = tdData+'<div class="borderHover"><a data-toggle="modal" data-target="#myInterviewerModal" onclick="getInterviewer(${reportData.requestRequisitionId},${reportData.id})" title="Interviewer"class="dropdown-item" href="#">'+
	    			'<i class="fa fa-user" aria-hidden="true"> </i>Interviewer</a></br></div>'; */
		<c:if test="<%=UserUtil.getCurrentResource().getEmployeeId() == 230 || UserUtil.getCurrentResource().getEmployeeId() == 4648 || UserUtil.getCurrentResource().getEmployeeId() == 5456 ||
				UserUtil.getCurrentResource().getEmployeeId() == 5929 ||UserUtil.getCurrentResource().getEmployeeId() == 5493 ||
				UserUtil.getCurrentResource().getEmployeeId() == 211 ||UserUtil.getCurrentResource().getEmployeeId() == 353 ||
				UserUtil.getCurrentResource().getEmployeeId() == 4623  
						|| UserUtil.getCurrentResource().getEmployeeId() == 7569
						|| UserUtil.getCurrentResource().getEmployeeId() == 4211
						|| UserUtil.getCurrentResource().getEmployeeId() == 6085
						|| UserUtil.getCurrentResource().getEmployeeId() ==7434 || 
				UserUtil.getCurrentResource().getEmployeeId() ==6176 || UserUtil.getCurrentResource().getEmployeeId() ==7488 || 
				UserUtil.getCurrentResource().getEmployeeId() ==669 %>" >
	 	tdData = tdData+  '<div class="borderHover"><a onclick="addRemoveResourceInRequestRequisition('+id+')" data-target="#addResource" id="'+id+'" href="javascript:void(0)">'+
	 	'<input type="button" class="request-form-style my_btn my_submit_btn" style="font-size: medium;" id="submit" value="S" title="Submit"/></a></div>';
	 	</c:if> 
		
		tdData = tdData+ '</div></div>';
		
		return tdData;
	}
	
	function prepareRrfId(id){
		var tdData='<div class="textwraps" title="'+id+'" data-original-title="'+id+'">'+id+'</div>';
		return tdData;
	}
	/* var requestSkillName="";
	function prepareShowResource(id, resourcesCount, reqSkillName){ 
		
		requestSkillName=reqSkillName;
		var tdData = '<a class="pointer action_btn" data-toggle="modal" data-target="#myModalShowAll" title="show all resources"'+
		' onclick="onClickResourceProfile(`All Resources`,`0`,`'+id+'`,`'+reqSkillName+'`,`'+resourcesCount+'`)" ><i class="fa fa-group" aria-hidden="true"></i></a>';
		return tdData;
	} */
	
	function prepareBGHApproval(id){
		
		var tdData = '<a class="pointer action_btn" onclick="BGHfileDownload(${reportData.requestRequisitionId})" title="Download BGH Approval"><i class="fa fa-download" aria-hidden="true"></i></a>';
		return tdData;
	}
	
	function prepareBUHApproval(id){
		
		var tdData = '<a class="pointer action_btn" onclick="BUHfileDownload(${reportData.requestRequisitionId})" title="Download BUH Approval"><i class="fa fa-download" aria-hidden="true"></i></a>';
		return tdData;
	}
	
	function prepareComments(id){
		var tdData = '<a class="pointer action_btn" href="#myPreviewModalForComment" data-toggle="modal" onclick="getAllComments(${reportData.id})" data-target="#myModalComments" title="Comments"><i class="fa fa-commenting  commentClassForAjax" aria-hidden="true"></i></a>';
		return tdData;
	}
	
	/* function prepareSubmitActions(id){
		var tdData =  '<a onclick="addRemoveResourceInRequestRequisition('+id+')" data-target="#addResource" id="'+id+'" href="javascript:void(0)">'+
			'<input type="button" class="request-form-style my_btn my_submit_btn" style="font-size: medium;" id="submit" value="S" title="Submit"/></a>';
		return tdData;
	} */
	
	function editrequest(obj,id) {
	    $(this).attr('target', '_blank');
	    window.location = "../requests?reqId="+id;
	}
	
	function copyRRFOpenOnCreate(obj,id,copyflag) {
	    $(this).attr('target', '_blank');
	    window.location = "../requests?skillId="+id+ "&copyflag="+copyflag;
	}

	function createPdl(data){
		var pdlList = data;
		
		if(pdlList!=null){
		
			for(var i = 0;i<pdlList.length;i++){
				var pdlObj = pdlList[i];
				var pdlEmail = pdlObj.pdlEmailId;
				var populatePdl = "<option value='" + pdlEmail + "'>" + pdlEmail + "</option>";
				$("#pdlGroupIds").append(populatePdl);
				$('#pdlGroupIds').multiselect('refresh');
			}			
		}
		
	};
	
	function getPdlList(){
		 $.ajax({
           type: 'GET',
           url: 'pdlUserList',
           dataType: "application/json",
           
           success: function(data){
                  var parsedAAData = JSON.parse(data.pdlGroups);
	        		 	
	        		data.pdlGroups = parsedAAData;	
	        		createPdl(data);
		        	//fnCallback(data);
           },
                 error:function(response){
                     var parsedAAData = JSON.parse(response.responseText);
                     var actualResultArray = JSON.parse(parsedAAData.pdlGroups);
  	        		 	
  	        		createPdl(actualResultArray);
  		        	 stopProgress();
                    
                 },
    });
	}
	function forwardRRF(skillRequestId){
		var modal = document.getElementById('forwardModal');
		modal.style.display = "block";
		document.getElementById('skillRequestId').value=skillRequestId;
		getPdlList();
	}
	
	function skillRequestReportPDF(obj,id) {
	    $(this).attr('target', '_blank');
	    window.location = "../requestsReports/showPDFReport?reqId="+id;
	}
	
	function addRemoveResourceInRequestRequisition(id){
		window.location = "../requestsReports/resources?requestRequisitionId="+id;
	}
	
	function skillRequestReportDelete(requestId,skillRequestId){
	    if(confirm("Sure you want to delete this RRF? There is NO undo!"))
	       {
	              $.ajax({
	                     type: 'POST',
	                     url: '${pageContext.request.contextPath}/requestsReports/deleteSkillRequest',
	                     dataType: "text json",
	                     data: {"requestId":requestId,"skillRequestId":skillRequestId},
	                     success: function(data){
	                            showSuccess("RRF has been deleted Successfully! " + data.errMsg);
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
	    
	function BUHfileDownload(id){
	 	$.ajax({				
	        url: 'downloadBUHApproval',
	     	contentType: "application/json",
	     	async:false,
	     	data: {
	     			"reqid":id
	     		  
			},  	
	     	success : function(response) { 			     			
	     		if(response==""){
	     			
	     			showAlert("\u2022 File not found for id " + id + " ! <br />");
	     			
	     		}else{     	     			     			     		
	     		    if(id !== ''){
						window.location.href = 'downloadBUHApproval?reqid='+id;
					} 
					else{				 
		
						showAlert("\u2022 File not found for id " + id + " ! <br />");
					}
	    		}
	     	},
	     	 error:function(data){
	     		showAlert("\u2022 File not found for id " + id + " ! <br />");
		    }   	
	 	});
	}
	
	function BGHfileDownload(id){
		 
		$.ajax({				
	       url: 'downloadBGHApproval',
	    	contentType: "application/json",
	    	async:false,
	    	data: {
	    			"reqid":id
	    		  
			},  	
	    	success : function(response) { 			     			
	    		if(response==""){
	    			
	    			showAlert("\u2022 File not found for id " + id + " ! <br />");
	    			
	    		}else{     	     			     			     		
	    		    if(id !== ''){
						window.location.href = 'downloadBGHApproval?reqid='+id;
					} 
					else{				 
		
						showAlert("\u2022 File not found for id " + id + " ! <br />");
					}
	   		}
	    	},
	    	 error:function(data){
	    		showAlert("\u2022 File not found for id " + id + " ! <br />");
		    }   	
		});
	}
	
	function getAllComments(skillid){
		$.ajax({				
		       url: 'getAllComments',
		    	contentType: "application/json",
		    	async:false,
		    	data: {
		    			"id":skillid
		    		  
				}, 
				success : function(response){ 
				var data = JSON.stringify(response);
					
				},
				error : function(data){
				}
				});
		
	}
	
	function formatDateTime(dateTime)
	{
		var date=new Date(dateTime);
		
		var day=date.getDate();
		var month=date.getMonth() + 1;
		var year =date.getFullYear();
		var hours =date.getHours();
		var minutes =date.getMinutes();
		var seconds =date.getSeconds();
		
		
		if (month.toString().length < 2)
			month = '0' + month;
	    if (day.toString().length < 2) 
	    	day = '0' + day;
	    
	    if (hours.toString().length < 2) 
	    	hours = '0' + hours;
	   
	    if (minutes.toString().length < 2) 
	    	minutes = '0' + minutes;
	    
	    if (seconds.toString().length < 2) 
	    	seconds = '0' + seconds;
	    
	    return day+'-'+month+'-'+year+' '+hours+':'+minutes+':'+seconds;
	}
	
	function getLatestCommentOnResource(skillid){
		$.ajax({				
		        url: 'getLatestCommentOnResource',
		    	contentType: "application/json",
		    	async:false,
		    	dataType: "text",
		    	data: {
		    			"id":skillid
				}, 
				success : function(response){ 
						var resource_id;
					 	var arrObj = JSON.parse(response);
					    var ulist = document.getElementById("dynamic-list");
					    var li1,a,i,ul,a1,li,div;
						while (ulist.firstChild) {
					    	ulist.removeChild(ulist.firstChild);
					    }
					    if(arrObj.length === 0) {
					    	var li = document.createElement("li");
							li.setAttribute('class','comments-detail');
							li.appendChild(document.createTextNode('No Comments Available'));
							ulist.appendChild(li);
					    }
					    	$.each(arrObj, function(intIndex, objComment){
								if(resource_id == objComment['resourceId'])
					    		{
					    			li = document.createElement("li");
								    li.setAttribute('class','comments-detail','id','comment_list');
								    div = document.createElement("div");
								    div.setAttribute('class','comments-date');
								    li.appendChild(document.createTextNode(objComment['resourceComment']));
									div.appendChild(document.createTextNode('from '+objComment['commentBy'] +' : '+formatDateTime(objComment['comment_Date'])));
									ul.appendChild(li);
									li.appendChild(div);
									ulist.appendChild(li1);
									
					    		}else
					    		{
					    			resource_id = objComment['resourceId'];
					    			li1 = document.createElement("li");
									li1.setAttribute('class','treeview');
									a = document.createElement("a");
									a.setAttribute('href','javascript:void(0);');
									a.setAttribute('class','bgclass');
									li1.appendChild(a);
									a.appendChild(document.createTextNode(objComment['resourceName']));
									i = document.createElement("i");
									i.setAttribute('class','fa fa-angle-left pull-right');
									a.appendChild(i);
									ul = document.createElement("ul");
									ul.setAttribute('class','treeview-menu');
									a1 = document.createElement("a");
									a1.setAttribute('href','javascript:void(0);');
									li1.appendChild(ul);
									ul.appendChild(a1);
								 	li = document.createElement("li");
								    li.setAttribute('class','comments-detail','id','comment_list');
								    div = document.createElement("div");
								    div.setAttribute('class','comments-date');
								    li.appendChild(document.createTextNode(objComment['resourceComment']));
									div.appendChild(document.createTextNode('from '+objComment['commentBy'] +' : '+formatDateTime(objComment['comment_Date'])));
									ul.appendChild(li);
									li.appendChild(div);
									ulist.appendChild(li1);
									/* ul.appendChild(li);
									li.appendChild(div);  */
								}
								});
									},
				error : function(data){	
				}
				});
	}
	
	
	function  closeCopyRRFModal(){
		var modal = document.getElementById('copyRRFModal');
		modal.style.display = "none";
		$('#numberOfresources').val('');
	}
	
	function copyRRFMethod(skillId){
		document.getElementById('skillRequisitionId').value = skillId;
		copyRRFOpenOnCreate(this,skillId, "copyFlagTrue");
	}
	
	var validationCopyRRF = true;
	
	function copyRRFSubmit(){
		var skillId = document.getElementById('skillRequisitionId').value;
		//var requiredResources = document.getElementById('numberOfresources').value;
		/* 
		if(requiredResources <=0){
			showAlert("Please submit valid number of Resources");
			validationCopyRRF = false;
		} */
		var copyFlag; 
		
		copyRRFOpenOnCreate(this,skillId, "copyFlagTrue");
		/* if(validationCopyRRF){
			 startProgress();
			$.ajax({				
		        url: 'copyRRFBySkillID',
		    	contentType: "application/json",
		    	async:false,
		    	dataType: "text",
		    	data: {
		    			"id":skillId
		    			//"requiredResources" : requiredResources
				}, 
				success : function(response){
					stopProgress();
					showSuccess("RRF is copied Successfully!");
					closeCopyRRFModal();
					var data = JSON.parse(response); 
					var copyFlag  = data.copyFlag;
					var requestId = data.requestId;
					editrequest(this,requestId, "copyFlagTrue");
					//window.location.reload();
				},
				error : function(data){	
					closeCopyRRFModal();
					showAlert("Something is wrong, please connect with ebiz_rmssupport@yash.com")
				}
			});
		}  */
		
	}
	
	function setInterviewPanelInTextArea() {
	    if ($("#round1Select option:selected").length) {
			var round1data = '';
			$.each(	$("#round1Select option:selected"),
				function () {
					if ($("#round1Select option:selected").length == 1) {
						round1data = $(this).text()
					} else {
						round1data = round1data + $(this).text() + ', ';
					}
			})
		$("#keyInterviewer1").val(round1data);
		}
	    
	    if ($("#roundTwoSelect option:selected").length) {
			var round2data = '';
			$.each($("#roundTwoSelect option:selected"),
				function () {
					if ($("#roundTwoSelect option:selected").length == 1) {
						round2data = $(this).text()
					} else {
						round2data = round2data + $(this).text() +	', ';
					}
				})
			$("#keyInterviewer2").val(round2data);
		}
    }
	
	function getInterviewPanel(skillid) {
		document.getElementById('skillRequestId').value=skillid;
		var round1Selectvariable = $('#round1Select');
		var round2Selectvariable = $('#roundTwoSelect');
		$.ajax({				
			url: '/rms/requestsReports/getKeyInterviewPanels',
		   	contentType: "application/json",
		  	async:false,
		   	data: {"skillRequestId":skillid }, 
			success : function(response){ 
				var data = response;
				if(data){
					data.keyInterviewPanelOne = JSON.parse(data.keyInterviewPanelOne);
					data.keyInterviewPanelTwo = JSON.parse(data.keyInterviewPanelTwo);
					var option;
					
					$.each(data.keyInterviewPanelOne, function(indx, item) {
						option = new Option(item.firstName + " " + item.lastName , item.employeeId, true, true);
						round1Selectvariable.append(option);
					});
					
				    $.each(data.keyInterviewPanelTwo, function(indx, item) {
						option = new Option(item.firstName + " " + item.lastName , item.employeeId, true, true);
						round2Selectvariable.append(option);
					});
				    
				    $(round1Selectvariable, round2Selectvariable).trigger('change');
   				    // manually trigger the `select2:select` event
				    round1Selectvariable.trigger({	
				        type: 'select2:select',
				        params: {
				            data: data
				        }
				    });
				    
				    round2Selectvariable.trigger({
				        type: 'select2:select',
				        params: {
				            data: data
				        }
				    });
					/* formatDataForKeyInterviewers(data);
					$('#round1Select').val(data.keyInterviewPanelOne);
					$('#roundTwoSelect').val(data.keyInterviewPanelTwo);
					$('#round1Select').multiselect('refresh');
					$('#roundTwoSelect').multiselect('refresh');
					empIdForRound1 = data.keyInterviewPanelOne;
					empIdForRound2 = data.keyInterviewPanelTwo;
					  */
				 	requiredFor = data.requiredFor;
					if (requiredFor == 'ODC'){
						$("#round1Select, #roundTwoSelect").closest('.form-group').addClass('validateMe'); //key interviewers mandatory in case of ODC
				        $('#keyInterviewer1,#keyInterviewer2').addClass('validateMe');
					}
					else {
					     $("#round1Select, #roundTwoSelect").closest('.form-group').removeClass('validateMe');
					     $('#keyInterviewer1,#keyInterviewer2').removeClass('validateMe');//key interviewers not mandatory other than ODC
					}
					setInterviewPanelInTextArea(); 
				 }
			},
			error : function(data){
			}
		});		
	}
	
	function formatDataForKeyInterviewers(userList) {
		$.map(userList, function(item, idx) {
			item.id = item.employeeId;
		 	item.text = item.firstName + " " + item.lastName + "(" + item.yashEmpId + ")";
		});

		return userList;
	}
	
	$(document).on('click','#submit',function() {
		var startDate = "";
		var endDate ="";
		var errmsg = "Please select:";
		var validationFlag = true;
		var customerIds = "";
		var groupIds = "";
		var statusIds ="";
		var customerIds = [];
		var groupIds = [];
		var hiringUnits = [];
		var reqUnits = [];
		/* setTimeout(function(){ startProgress(); }, 3000); */
		startProgress();
		$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
		$("#client option:selected").each(function () {
			var id = $(this).val();
			customerIds.push(id);
	 	}); 
		
		if(customerIds.length <=0){
			errmsg = errmsg + " Client";
			validationFlag = true;
		}
		
		
		if (!validationFlag) {
			stopProgress();
			if (errmsg.length > 0)
				showError(errmsg);
			return;
		} else {
			
			$("#group option:selected").each(function () {
				var id = $(this).val();
				groupIds.push(id);
		 	});
			$.ajax({
				type : 'GET',
				url : '/rms/requestRequisitionReport/getRequestRequisitionReport',
				contentType : "text/html; charset=utf-8",
				async : false,
				data :  "&customerIds="+customerIds
						+"&groupIds="+groupIds
						+"&statusIds="+statusIds
						+ "&hiringUnits=" +hiringUnits
						+ "&reqUnits=" +reqUnits,
						
						success : function(data) {
							if (data.length != 0) {
								$('#records_tableId').show();
								$("#NoAllocMessage").hide();
								$("#requestRequisitionReportTable").show();
								$('#records_tableId').dataTable().fnClearTable();
								var i = 1;
								stopProgress();
								$.each(data,function(key,val) {
									$('#records_tableId').show();
									$('#records_tableId').dataTable().fnAddData(
										[	
											val.id+'',
											prepareActions(val.id, val.status, val.requestRequisitionId, val.sentToList, val.pdlList, val.submissions, val.requirementId),
											//val.status,(replace status with reportStatus for showing only single status)
											prepareRrfId(val.requirementId),
											val.aliasAllocationName,
											val.successFactorId,
											val.designationName,
											val.skill,
											val.noOfResources,
											val.open,
											prepareTableDataElement(0,val.id,val.submissions,val.requirementId),
											prepareTableDataElement(2,val.id,val.shortlisted,val.requirementId),
											prepareTableDataElement(8,val.id, val.closed,val.requirementId),
											prepareTableDataElement(3,val.id, val.notFitForRequirement,val.requirementId),
											val.hold,
											val.lost,
											val.requestedBy,
											prepareAdditionalComments(val.addtionalComments,val.id),
											val.reportStatus
										]
									)												                                             
								});
							}
						},
						error : function(errorResponse) {
							//showError("Records Not Found");
							$('#records_tableId').hide();
							var htmlVarObj = '<div id="NoAllocMessage" class="NoAllocMessage">No Record Exist for selected filters.</div>';
							$('#NoAllocMessage').empty().append(htmlVarObj);
							$("#NoAllocMessage").show();
							$("#requestRequisitionReportTable").hide();
	
						}
			});
		}
		
		var isClick=true; 
		$(".action-dropdown").on('click', function(){
			if(isClick){
				//$(this).css("background", "#d9ebff");
				//$(this).css("cursor", "pointer");
				//$(this).css("transition", "0.5s");
				//$(this).css("color", "#5189c2");
				//for apply the style to sibblings
				//$(this).siblings().css("background", "#d9ebff");
				//$(this).siblings().css("cursor", "pointer");
				//$(this).siblings().css("transition", "0.5s");
				//$(this).siblings().css("color", "#5189c2");
				isClick=false;
			}
			else{
				$(this).css("background", "");
				$(this).css("cursor", "");
				$(this).css("transition", "");
				$(this).css("color", "");
				//for apply the style to sibblings
				$(this).siblings().css("background", "");
				$(this).siblings().css("cursor", "");
				$(this).siblings().css("transition", "");
				$(this).siblings().css("color", "");
				isClick=true;
			}
		});
		$(".odd").addClass("content");
		$(".even").addClass("content");
		/* $(".odd").addClass("dropdown");
		$(".even").addClass("dropdown"); */ 
		$(".action-dropdown").addClass("dropdowns");
		$(".action-dropdown").addClass("dropbtn");
		$(".dropdown-menu").addClass("dropdown-content");
	});
	var maxHeight = 400;
	
	$(".dropdown > li").hover(function() {
	     var $container = $(this),
	         $list = $container.find("ul"),
	         $anchor = $container.find("a"),
	         height = $list.height() * 1.1,       // make sure there is enough room at the bottom
	         multiplier = height / maxHeight;     // needs to move faster if list is taller
	    
	    // need to save height here so it can revert on mouseout            
	    $container.data("origHeight", $container.height());
	    
	    // so it can retain it's rollover color all the while the dropdown is open
	    $anchor.addClass("hover");
	    
	    // make sure dropdown appears directly below parent list item    
	    $list
	        .show()
	        .css({
	            paddingTop: $container.data("origHeight")
	        });
	    
	    // don't do any animation if list shorter than max
	    if (multiplier > 1) {
	        $container
	            .css({
	                height: maxHeight,
	                overflow: "hidden"
	            })
	            .mousemove(function(e) {
	                var offset = $container.offset();
	                var relativeY = ((e.pageY - offset.top) * multiplier) - ($container.data("origHeight") * multiplier);
	                if (relativeY > $container.data("origHeight")) {
	                    $list.css("top", -relativeY + $container.data("origHeight"));
	                };
	            });
	    }
	    
	}, function() {
	
	    var $el = $(this);
	    
	    // put things back to normal
	    $el
	        .height($(this).data("origHeight"))
	        .find("ul")
	        .css({ top: 0 })
	        .hide()
	        .end()
	        .find("a")
	        .removeClass("hover");
	
	});
	
	// Add down arrow only to menu items with submenus
	$(".dropdown > li:has('ul')").each(function() {
	    $(this).find("a:first").append("<img src='images/down-arrow.png' />");
	});
	var oInnerTable;
	var lastTableObject;
	var lastNTr;
	var lastIdx;
		
		$('.close').on('click', function() {
			closeSubTable();
		})
		
		function closeSubTable(){
			 lastTableObject.removeClass( 'details' );
	       	 lastTableObject.fnClose(lastNTr);
		}
		
		function onClickResourceProfile(stausName,status, reqId, rrfName, resourceCount){
			//alert("stausName "+ stausName + " status "+status+" reqId "+ reqId + " rrfName "+ rrfName + " resourceCount "+ resourceCount);
			if(resourceCount>0){
				showSubTable(reqId, status, rrfName,stausName);
			}
			if(resourceCount<=0 && stausName=='All Resources'){
				showError("No Resource found !");
			}
	    }
	    
		function getResources(status,skillRequisitionId, responseData){
	    	$.ajax({
	    		type: 'GET',
	    		dataType: 'json',
	            
	           url: '/rms/requestsReports/requestRequisitionSkills/'+skillRequisitionId+'/resources',
	           cache: false,
	           data : "&resourceStatusIds="+status,
	           success: function(response) { 
	    	       	stopProgress();
	    	       	responseData(response);
	        	},
	        	error: function(error){
	        		stopProgress();
	        		showError("Something happend wrong!!");
	        	}
	    	})
	    }
		
		function showSubTable(reqId, status, rrfName,stausName){
			resourceCounter = 0;
			var modal = document.getElementById('myPreviewModal');
			modal.style.display = "block";
		 	var responseData;
		 	
		 	$('#resource-update-btn').hide();
		 	if(stausName ==='All Resources'){
		 		$('#resource-update-btn').show();
		 		//$('#resource-update-btn').prop("disabled", true);
		 	}
		 	
		 	if(status == '0'){
		 		stausName = '<span class="resourceSubmission">'+stausName+" : "+rrfName+'</span>';
		 	}else if(status =='2'){
		 		stausName = '<span class="resourceShortlisted">'+stausName+" : "+rrfName+'</span>';
		 	}else if(status =='8'){
		 		stausName = '<span class="resourceJoined">'+stausName+" : "+rrfName+'</span>';
		 	}else{
		 		stausName = '<span class="resourceRejected">'+stausName+" : "+rrfName+'</span>';
		 	}
		 	
		 	$('.positionStatus').html('');
		 	$('.positionStatusPlaceHolder').html(stausName);
		 	//alert("status "+ status + " reqId "+ reqId + " stausName "+ stausName);
	    	getResources(status, reqId, function(data){
	    	responseData = data;
	    	if (data.length > 0) {
				$('#modal_data_table').dataTable().fnClearTable();
				var i = 1;
				$.each(data,function(key,val) {
					var dateValue=null;
					
					
					if(val.status=="SELECTED")
						dateValue=val.allocationDate;
					
					if(val.status=="JOINED")
						dateValue=val.allocationDate;
					
					else if(val.status=="REJECTED")
						dateValue=val.resourceRejectedDate;
					
					else if(val.status=="SUBMITTED TO POC")
						dateValue=val.resourcePOCsubmittedDate;
					
					else if(val.status=="SUBMITTED TO AM TEAM")
						dateValue=val.resourceAMsubmittedDate;
					
					else if(val.status=="INTERVIEW LINED UP")
						dateValue=val.interviewDate;
					
					$('#modal_data_table').show();
					$('#modal_data_table').dataTable().fnAddData(
						[
							prepareActionComments(val.resourceId, val.internalResId, val.resourceType,  val.resourceName, val.skillReqName, val.skillReqId),
							val.resourceType,
							val.resourceName,
							/* val.skill, */
							prepareSkillTD(val.skill),
							val.designation,
							/* val.status,
							dateValue, */
							prepareProfileStatus(val.resourceId,val.status,val.joiningDate,val.interviewDate, resourceCounter,val.resourceName, val.skillReqName),
							prepareDate(val.resourceId,val.status,val.joiningDate,val.interviewDate,resourceCounter,val.skillReqId),
							/* val.location, */
							prepareLocationTD(val.location),
							preparenoticePeriod(val.noticePeriod),
							prepareTotalExp(val.totalExperince),
							//val.noticePeriod,
							//val.totalExperince,
							/* val.contactNum, */
							prepareContactNumTD(val.contactNum),
							/* val.emailId */ 
							prepareEmailIdTD(val.emailId)
							//prepareResume(val.internalResId, val.resourceType),
							//prepareTEF(val.resourceId,  val.resourceType)
						]
					);
					resourceCounter++
				 });
			  }else{
					$('#modal_data_table').show();
					$('#modal_data_table').dataTable().fnClearTable();  
			  }
	     });
		}
		
		function closePopup(){
			var modal = document.getElementById('myPreviewModal');
			modal.style.display = "none";
			 $('#modal_data_table').hide();
			 $('#updateResPopUp').modal('hide'); 
			 $('#modal_data_table').dataTable().fnClearTable();
		}
		
		function closeRRFPopup(){
			 $("#internalUserIds option:selected").prop("selected", false);
			 $("#pdlGroupIds option:selected").prop("selected", false);
			 $("#pdlGroupIds").multiselect('refresh'); 
			 $("#internalUserIds").multiselect('refresh'); 
			 $("#commentForwardRRF").val('');
			 $('#internalUserIds').val(null).trigger('change');
			 $('#pdlGroupIds').val(null).trigger('change');
			 document.getElementById("forward-btn").disabled = false;
			var modal = document.getElementById('forwardModal');
			modal.style.display = "none";
		}

		function closeInterviewPanelPopUp() {
			 var modal = document.getElementById('InterviewPanelModal');
			 modal.style.display = "none";
			 $("#round1Select option, #roundTwoSelect option").remove();
			 $("#round1Select, #roundTwoSelect").val(null).trigger('change'); 
			 $("#keyInterviewer1, #keyInterviewer2").val('');
			 
			 $("#round1Select, #roundTwoSelect").closest('.form-group').removeClass('validateMe').removeClass('invalid_input');
		     $('#keyInterviewer1, #keyInterviewer2').removeClass('invalid_input');//key interviewers not mandatory other than ODC
			 requiredFor = '';
			 $('#InterviewPanelModal').modal('hide');
		}
		
		function closeStakeHolderPopUp() {
			 var modal = document.getElementById('stakeHolderModal');
			 modal.style.display = "none";
			 $("#stakeHolderSelect option").remove();
			 $("#stakeHolderSelect").val(null).trigger('change'); 
			 $("#stakeHoldertxtarea").val('');
			 
			 $("#stakeHolderSelect").closest('.form-group').removeClass('validateMe').removeClass('invalid_input');
		     $('#stakeHoldertxtarea').removeClass('invalid_input');
			 $('#stakeHolderModal').modal('hide');
		}
		
		function closeSFIdModal() {
			 var modal = document.getElementById('SFIdModal');
			 modal.style.display = "none";
			 $("#sfID").val('');
			 $('#SFIdModal').modal('hide');
		}

		function onForwardRRF(){
			var internalUserIds =document.getElementById("internalUserIds");
			var pdlGroupIds =document.getElementById("pdlGroupIds");
			var send =document.getElementById("sendId");
			var userComment =  document.getElementById('commentForwardRRF').value;
			var len =internalUserIds.selectedOptions.length+pdlGroupIds.selectedOptions.length;
			var skillReqId=document.getElementById('skillRequestId').value;
			if(internalUserIds.value=="" && pdlGroupIds.value==""){
			    showError("Please select atleast one email address.");
				return false;
			}
			 if(len>10){
				showError("Please don't select more than 10 emailIds");
				return false;
			}
			 
			if(userComment.length > 250){
				showError("Your comment exceeds 250 characters!");
				return false;
			}
			 var pdlGroupEmailIds="";
			 $("#pdlGroupIds option:selected").each(function(){
					var id = $(this).val();
					pdlGroupEmailIds =id+","+pdlGroupEmailIds;
			 });
			 
			 var internalUserEmailIds="";
			 $("#internalUserIds option:selected").each(function(){
					var id = $(this).val();
					internalUserEmailIds =id+","+internalUserEmailIds;
			 });
			 
			var data = {
						"userIdList":internalUserEmailIds,
						"pdlList":pdlGroupEmailIds,
						
						
						"skillReqId":skillReqId, 
						"commentForwardRRF" : userComment
				}
			document.getElementById("forward-btn").disabled = true;
			$.ajax({
					type: 'POST',
			        url: '/rms/requestsReports/forwardRRF',
			        data: JSON.stringify(data),
			        cache: false,
			        contentType :'application/json',
			        success: function(response) { 
			    		 $("#internalUserIds option:selected").prop("selected", false);
			    		 $("#pdlGroupIds option:selected").prop("selected", false);
			    		 $("#pdlGroupIds").multiselect('refresh'); 
			    		 $("#internalUserIds").multiselect('refresh'); 
						 $("#commentForwardRRF").val('');
						 $('#internalUserIds').val(null).trigger('change');
			        	 var modal = document.getElementById('forwardModal');
						 modal.style.display = "none";
			        	 showSuccess("RRF has been forwarded successfully !");
			        	 document.getElementById("forward-btn").disabled = false;
			        	 var element = document.getElementById("forwardOrangeId"+skillReqId);
			        	 element.classList.add("orange-batch");
				     	},
				     	error: function(error){
				     		document.getElementById("forward-btn").disabled = false;
				     		showError("Something went wrong!!");
				     	}
			});
		}

	var empIdForRound2 = [];
	var empIdForRound1 = [];
	
	var notifyMailVal = [];
	
	var requiredFor = "";	
	$(document).ready(function() {
		
	    function fnFormatDetails(table_id, html) {
		    var sOut = "<div><div class='close_sub_table'><a href='#"+table_id+"' onclick='closeSubTable();' class='close'></a></div><table id=\"sub_record_table_"+table_id+"\" class=\"dataTbl display tablesorter addNewRow alignLeft\" style='width:60%'>";
		    sOut = sOut+"<thead><tr><th align='center' valign='middle' width='1%'>Type</th><th>Resource Name</th><th>Skill</th><th>Designation</th><th>Status</th><th>Date</th></tr></thead>";
		    sOut += html;
		    sOut += "</table></div>";
		    return sOut;
		}

	    $("#stakeHolderSelect").select2({
			tags: true,
			ajax: {
				url: "/rms/requestsReports/activeUserList",
				dataType: 'json',
				data: function (params) {
					return {
						userInput: params.term || '',
					};
				},
				processResults: function (data, params) {
					return {
						results: populateRound1Select(JSON.parse(data.activeUserList)),
					};
				},
			},	
			minimumInputLength: 3,
			allowClear: true,
			createTag: function(params) {
	        	return undefined;
			},
			placeholder: 'Select Recipient..',
		});
	    
	    
	    $("#round1Select").select2({
			tags: true,
			ajax: {
				url: "/rms/requestsReports/activeUserList",
				dataType: 'json',
				data: function (params) {
					return {
						userInput: params.term || '',
					};
				},
				processResults: function (data, params) {
					return {
						results: populateRound1Select(JSON.parse(data.activeUserList)),
					};
				},
			},	
			minimumInputLength: 3,
			allowClear: true,
			createTag: function(params) {
	        	return undefined;
			},
			placeholder: 'Select Recipient..',
		});
		$("#roundTwoSelect").select2({
			tags: true,
			ajax: {
				url: "/rms/requestsReports/activeUserList",
				dataType: 'json',
				data: function (params) {
					return {
						userInput: params.term || '',
					};
				},
				processResults: function (data, params) {
					return {
						results: populateRound2Select(JSON.parse(data.activeUserList)),
					};
				},
			},	
			minimumInputLength: 3,
			allowClear: true,
			createTag: function(params) {
				return undefined;
			},
			placeholder: 'Select Recipient..',
		});
		  $("#contactPerson").select2({
		        tags: true,
		        openOnEnter: false,
		        ajax: {
		          url: "/rms/requestsReports/activeUserList",
		          dataType: 'json',
		          data: function (params) {
		            return {
		              userInput: params.term || '',
		            };
		          },
		          processResults: function (data, params) {
		            return {
		              results: formatData4(JSON.parse(data.activeUserList)),
		            };
		          },
		        },
		        minimumInputLength: 3,
		        allowClear: true,
		        createTag: function (params) {
		          return undefined;
		        }
		      });
		function populateRound1Select(userList) {
			$.map(userList, function(item, idx) {
				item.id = item.employeeId;
			 	item.text = item.firstName + " " + item.lastName;
			});

			return userList;
		}
		function populateRound2Select(userList) {
			$.map(userList, function(item, idx) {
				item.id = item.employeeId;
			 	item.text = item.firstName + " " + item.lastName;
			});

			return userList;
		}
		 function formatData4(userList) {
		        $.map(userList, function (item, idx) {
		          item.id = item.emailId;
		          item.text = item.firstName + " " + item.lastName;
		        });

		        return userList;
		      }

	  
	    $('#records_tableId tbody').on( 'click', 'tr td.closed-resources', function () {
	    	var dt = $('#records_tableId').dataTable();
	        var tr = $(this).closest('tr');
	        var nTr = $(this).parents('tr')[0];
	        var position = dt.fnGetPosition(nTr);
	        var reqId = dt.fnGetData(position)[0];
	        var status = 8;
	        var stausName = 'Joined Resources';
	        var rrfName = dt.fnGetData(position)[3];
	        var selectedColumnValue =  dt.fnGetData(position)[9];
	         if(selectedColumnValue !='0' && selectedColumnValue !=''){
	        	showSubTable(dt, tr, nTr, reqId, status,rrfName,stausName );
	          }
	    } );
	    
	    $('#records_tableId tbody').on( 'click', 'tr td.shortlisted-resources', function () {
	    	var dt = $('#records_tableId').dataTable();
	        var tr = $(this).closest('tr');
	        var nTr = $(this).parents('tr')[0];
	        var position = dt.fnGetPosition(nTr);
	        var reqId = dt.fnGetData(position)[0];
	        var status = 2;
	        var stausName = 'Shortlisted Resources';
	        var rrfName = dt.fnGetData(position)[3];
	        var selectedColumnValue =  dt.fnGetData(position)[8];
	        if(selectedColumnValue !='0' && selectedColumnValue !=''){
	       		showSubTable(dt, tr, nTr, reqId, status,rrfName,stausName );
	         }
	    } );
	    
	    $('#records_tableId tbody').on( 'click', 'tr td.notfit-resources', function () {
	    	var dt = $('#records_tableId').dataTable();
	        var tr = $(this).closest('tr');
	        var nTr = $(this).parents('tr')[0];
	        var position = dt.fnGetPosition(nTr);
	        var reqId = dt.fnGetData(position)[0];
	        var status = '3,6';
	        var stausName = 'Not Fit Resources';
	        var rrfName = dt.fnGetData(position)[3];
	        var selectedColumnValue =  dt.fnGetData(position)[10];
	        if(selectedColumnValue !='0' && selectedColumnValue !=''){
	       		showSubTable(dt, tr, nTr, reqId, status,rrfName,stausName );
	         }
	    } );
	  
	     
	    $('#records_tableId tbody').on( 'click', 'td.stop_btn_click', function (e) {
	    	e.stopPropagation();
	    	return false;
	    
		});
		
	 	
		
		/* $('#round1Select').multiselect({
			includeSelectAllOption: true,
			id: 'round1Select'
		}).multiselectfilter(); */
		
		
		$('#round1Select').bind(
				'change',
					function (ev) {
						empIdForRound1 = []
						var selMulti = $.map($(this).find('option'),
							function (el, i) {
								if (el.selected) {
									empIdForRound1 = $('#round1Select').val();
									$("#keyInterviewer1").val($(el).text());
									return $(el).text();
								}
							});
					if (!selMulti.length) {
				  		empIdForRound1 = [];
					}
					if (selMulti == "") {
						$('#keyInterviewer1').html('');
			  	 	}
					$("#keyInterviewer1").val(selMulti.join(", "));
			});
		 
		
		/* $('#roundTwoSelect').multiselect({
			includeSelectAllOption: true,
			id: 'roundTwoSelect'
		}).multiselectfilter(); */


		
		$('#roundTwoSelect').bind(
			'change',
				function (ev) {
					empIdForRound2 = [];
					var selMulti = $.map($(this).find('option'),
						function (el, i) {
							if (el.selected) {
								empIdForRound2 = $('#roundTwoSelect').val();
								$("#keyInterviewer2").val($(el).text());
								return $(el).text();
							}
						});
				if (!selMulti.length) {
					empIdForRound2 = [];
				}
				if (selMulti == "") {
					$('#keyInterviewer2').html('');
		  	 	}
				$("#keyInterviewer2").val(selMulti.join(", "));
		});
		
		$('#stakeHolderSelect').bind('change',function (ev) {
			notifyMailVal = [];
			
			 var selMulti = $.map($('#stakeHolderSelect').find('option:selected'), function (el, i) {
				 
				 notifyMailVal = $(el).val();
			     
				 return $(el).text();
			    });
			 
			if (!selMulti.length) {
				notifyMailVal = [];
			}
			if (selMulti == "") {
				$('#stakeHoldertxtarea').html('');
			}
			$("#stakeHoldertxtarea").val(selMulti.join(", "));
		});
		
		
	    $('#mailTo').on ('change', function (){
	    	  
	    	  if($('#mailTo').prop("checked") == true){
	    		  $('#mailtoTextArea').val(mailtodata);
	    	  } else {
	    		  $('#mailtoTextArea').val('');
	    	  }
	    	
	    	});
	      
	    $('#pdlsTo').on ('change', function (){
	    	  
	    	  if($('#pdlsTo').prop("checked") == true){
	    		  $('#pdlTextArea').val(pdldata);
	    	  } else {
	    		  $('#pdlTextArea').val('');
	    	  }
	    	
	    	});
	    	
	    $('#notifyTo').on ('change', function (){
	    	  if($('#notifyTo').prop("checked") == true){
	    		  $('#notifyToTextArea').val(notifydata);
	    	  } else {
	    		  $('#notifyToTextArea').val('');
	    	  }
	    	
	    	});
		
	} );
	
	
	function validateODC() {
		if (requiredFor !== 'ODC') {
			return 0;
		}
		var al = 0;
		var items = $('#InterviewPanelModal').find(".ui-combobox-input,input[type='text'],input[type='number'],textarea,select");
		var al = 0;
		for (var i = 0; i < items.length; i++) {
			var b = items[i].value;
			if (b < 0) {
				$(items[i]).closest('.form-group').addClass('invalid_input');
				al = al + 1;

			} else if (b == "Select" || b == "" ||	b.trim() == '') {
				$(items[i]).closest('.form-group').addClass('invalid_input');
				$(items[i]).closest('.form-control').addClass('invalid_input');
				al = al + 1;
			} else {
				$(items[i]).closest('.form-group').removeClass('invalid_input');
				$(items[i]).closest('.form-control').removeClass('invalid_input');
			}
		}
		return al;
	}
	
	function submitInterviewPanels() {
		var isNotValidated = validateODC();
		if (isNotValidated) {
			return;
		}

		empIdForRound1 = $('#round1Select').val();
		empIdForRound2 = $('#roundTwoSelect').val();
				
		var skillRequestId = document.getElementById('skillRequestId').value;
		var stringForRound1 = "" ,stringForRound2="";
		
		if (empIdForRound1!=null && empIdForRound1.length) {
			 stringForRound1 = empIdForRound1.join(",");
		} 
		
		if (empIdForRound2!=null && empIdForRound2.length) {
			stringForRound2 = empIdForRound2.join(",");
		}

		var data = {"skillRequestId":skillRequestId,"keyInterviewPanelOne":stringForRound1,"keyInterviewPanelTwo":stringForRound2}
		startProgress();
		
		$.ajax({
			type: 'POST',
	        url: '/rms/requestsReports/saveInterviewPanel',
	        dataType: 'text json',
	        data: JSON.stringify(data),
	        cache: false,
	    	async : false,
	    	contentType: "application/json; charset=utf-8",
            success: function(response) { 
            	stopProgress();
            	closeInterviewPanelPopUp();
            	showSuccess("Interview Panel Added Successfully");
            },
	 		error : function(error){
	 			stopProgress();
	 			$('#InterviewPanelModal').modal('hide');
	 			if(error.errCode == 417 || error.errCode == 2)
		 			showError(error.message);
	 			else 
	 				showError("Something went wrong !");
	 		}
		});
	}
	
	function updateResourcePopup() {
		$('#updateResPopUp').modal('show');
		$(".close-btn1").click(function(){
				$('#updateResPopUp').modal('hide');
			 
		 });
	}
	$('#dataTbl').on( 'page.dt', function () {
	    var info = table.page.info();
    });
	
	function showActionDropdown(ele) {
		$('#requestRequisitionReportTable .dropdown-content').css('display', 'none');
		$(ele).children().next('.dropdown-content').css('display', 'block');
	}

	$(document).on('click', function(e) {
		if (!event) { var event = window.event; }
		event.stopPropagation();
		var src = event.srcElement ? event.srcElement : event.target;
		if(($(src).hasClass('dropdown-content')==false) && $(src).hasClass('dropDownHover')==false && $(src).hasClass('fa-ellipsis-v')==false)
		{ 
			$('#requestRequisitionReportTable .dropdown-content').hide();
		}
	});
	
	
	function getStakeHolder(requestRequisitionId) {
		
		document.getElementById('requestRequisitionId').value = requestRequisitionId;
		var stakeholdersSelectVariable = $('#stakeHolderSelect');
		
		$.ajax({				
			url: '/rms/requestsReports/getStakeHolder/'+requestRequisitionId,
			type: "get", 
		   	contentType: "application/json",
		  	async:false,
			success : function(response) { 
				var data = response;
				if(data.errorCode == 200) {
					data.stakeHolders = JSON.parse(data.stakeHolders);
					
					var option;
					$.each(data.stakeHolders, function(index, item) {
						option = new Option(item.firstName + " " + item.lastName , item.employeeId, true, true);
						stakeholdersSelectVariable.append(option);
					});
					
				    $(stakeholdersSelectVariable).trigger('change');
   				    // manually trigger the `select2:select` event
				    stakeholdersSelectVariable.trigger({	
				        type: 'select2:select',
				        params: {
				            data: data
				        }
				    });
				 	
				    if ($("#stakeHolderSelect option:selected").length) {
						 var stakeHoldertxtarea = $.map($('#stakeHolderSelect').find('option:selected'),function (el, i) {
						        return $(el).text();
						    });
						 $("#stakeHoldertxtarea").val(stakeHoldertxtarea.join(", "));
					}
				    
				 }
				else if(data.errorCode == 416){
					showError(data.message);
				}
			},
			error : function(data){
				console.log("error data ---------" , data.message);
				showError("something went wrong !");
			}
		});		
	}
	
	function submitStakeHolders() {
		
		var notifyMailVal = $('#stakeHolderSelect').val();
		
		if(notifyMailVal == null || notifyMailVal.length == 0){
			return showError("Please select atleast one Notify Resource");
		}
		
		var requestRequisitionId = document.getElementById('requestRequisitionId').value;

		notifyMailValue = notifyMailVal.join(",");
		
		var data = {"requestRequisitionId":requestRequisitionId,"notifyMailValue":notifyMailValue}
		startProgress();
		
		$.ajax({
			type: 'PUT',
	        url: '/rms/requestsReports/saveStakeHolder',
	        dataType: 'text json',
	        data: JSON.stringify(data),
	        cache: false,
	    	async : false,
	    	contentType: "application/json; charset=utf-8",
            success: function(response) { 
            	stopProgress();
            	closeStakeHolderPopUp();
            	showSuccess(response.message);
            },
	 		error : function(error){
	 			stopProgress();
	 			console.log("error occured ---------" ,error.message);
	 			showError("Something went wrong !");
	 		}
		});
	}
	
	function getSFId(requestRequisitionId) {
		var reqid = requestRequisitionId;
		document.getElementById('requestRequisitionId').value = reqid;
		
		$.ajax({				
			url: '/rms/requestsReports/getSFId/'+requestRequisitionId,
			type: "get", 
		   	contentType: "application/json",
		  	async:false,
			success : function(response) { 
				var data = response;
				if(data.errorCode == 200) {
					var sfId = data.sfId;
					$('#sfID').val(sfId);
				 }
				else if(data.errorCode == 416){
					showError(data.message);
				}
			},
			error : function(data){
				console.log("error data ---------" , data.message);
				showError("something went wrong !");
			}
		});	
		
	}
	
	function submitSFId() {
		
		var sfID = $('#sfID').val();
		
		if (sfID != ""  && sfID.trim().length > 50) {
			showError("Success Factor Id can not hold more than 50 characters");
			return ;
		} 
		
		if(sfID == null || sfID.trim().length == 0) {
			sfID = "";
		}
		
		var requestRequisitionId = document.getElementById('requestRequisitionId').value;

		var data = {"requestRequisitionId":requestRequisitionId,"sfID":sfID}
		startProgress();
		
		$.ajax({
			type: 'PUT',
	        url: '/rms/requestsReports/saveSFId',
	        dataType: 'text json',
	        data: JSON.stringify(data),
	        cache: false,
	    	async : false,
	    	contentType: "application/json; charset=utf-8",
            success: function(response) { 
            	stopProgress();
            	closeSFIdModal();
            	if(response.errCode == 200) {
            	 showSuccess(response.message);
            	 dataTablepd.fnDraw();
            	}
            	else if (response.errCode == 417) {
            		showError(response.message);
            	}
            },
	 		error : function(error){
	 			stopProgress();
	 			console.log("Error occured ---------" ,error.message);
	 			showError("Something went wrong !");
	 		}
		});
	}
	
	function isValidSFId(event) {
		var character = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if(isSpecialCharMatched(character))  {
	       event.preventDefault();
	    }
	}

	function isSpecialCharMatched(str) {
	    var regex = new RegExp("^[a-zA-Z0-9 _-]+$");
		return !regex.test(str);
	} 
	
	</script>
<body>
	<div id="positionDashboardMain" class="content-wrapper">
		<div class="botMargin">
		<h1>Resource Request</h1>
		</div>
		
		<div class="tab_seaction">
			<!-- <ul class='tabs'>
					<li><a href='#tab1' id="tab1id">List</a></li>
	
				</ul> -->
			<div id='tab1' class="tab_div">
				<div class="form my_form">
					<form name="defaultFormName" method="post" id="defaultForm">
						<div class="tblBorderDiv">
							<table id="mail" cellpadding="5" cellspacing="5"
								class="display dataTable filter-table-ps"
								style="border-collapse: collapse; width: 100%; margin-top: 0px;">
								<tr>
									<td><input type="hidden" id="formHiddenId" name="id"></input></td>

									<td align="right">
										<!-- <div class="sortby-filtercontain">
											<label class="sortby-filter"> Sort By</label> <select
												id="example-sort">
												<option value="3">Status</option>
												<option value="4">RRF ID</option>
												<option value="5">Allocation Type</option>
												<option value="6">Designation</option>
												<option value="15">Requested By</option>
											</select>
										</div> -->
										<a href="/rms/requests" class="btn next-button">+ Add RRF</a>
										
									</td>
								</tr>
							</table>
						</div>
					</form>
				</div>
				<div class="tbl"></div>
				<div class="clear"></div>
				<div id="requestRequisitionReportTable">
					<table
						class="dataTbl display tablesorter addNewRow alignCenter my_table positiondashboard-table"
						id="records_tableId"
						style="overflow: auto; display: inline-block;">
						<thead>
						<tr class="content">
							<!-- <th class="width110" align="center" valign="middle">Id</th> -->
							<th class="width71 no-sort" style="width:71px !important; background: #fff !important;"  align="center" valign="middle">Actions</th>
							<th class="width110 no-sort" style="width:185px !important;background: #fff !important;" align="center" valign="middle">RRF ID</th>
							 <th class="width110" align="center" valign="middle">Allocation Type</th>
							  <th class="width110" align="center" valign="middle">Success Factor ID</th>
							<th class="width110" align="center" valign="middle">Designation</th>
							<th class="width110" align="center" valign="middle">Hiring BG-BU</th>
							<th class="width110" align="center" valign="middle">Requestor BG-BU</th>
							<th class="width110" align="center" valign="middle">Required Skill</th>
							<th class="width80" align="center" valign="middle">Positions</th>
							<th class="width80" align="center" valign="middle">Open</th>
							<th class="width125" align="center" valign="middle">Submissions</th>
							<th class="width125" align="center" valign="middle">ShortListed</th>
							<th class="width80" align="center" valign="middle">Closed</th>
							<th class="width125" align="center" valign="middle">Not Fit</th>
							<th class="width80" align="center" valign="middle">Hold</th>
							<th class="width80" align="center" valign="middle">Lost</th>
							  <th class="width110" align="left" style="text-align:left !important;" valign="middle">Requested By</th>
							 <th class="width110" align="center" valign="middle">Additional Comments</th>
							 <th class="width125" align="center" valign="middle">Status</th>
							 <th class="width125" align="center" valign="middle">Requirement Area</th>
						</tr>
						<tr>
						<!-- <td><input type="text" name="search_id" 
								placeholder="Id" class="search_init align-center" disabled="disabled" style="display: none"/></td> -->
						<td><input type="text" name="search_action"
								placeholder="Action" class="search_init" disabled="disabled" style="display: none"/></td>
						<td><input type="text" name="search_rrfId"
								placeholder="RRF ID" class="search_init" /></td>
				 <td><input type="text" name="search_allocationType"
								placeholder="Allocation Type" class="search_init" /></td>
				<td><input type="text" name="search_successFactorId"
							placeholder="Success Factor ID" class="search_init" /></td>
						<td><input type="text" name="search_designation"
								placeholder="Designation" class="search_init" /></td>
						<td><input type="text" name="search_HiringBGBU"
								placeholder="Hiring BGBU" class="search_init" /></td>
						<td><input type="text" name="search_Requestor'sBGBU"
								placeholder="Requestor's BGBU" class="search_init" /></td>
						<td><input type="text" name="search_requiredSkill"
								placeholder="Required Skill" class="search_init" /></td>
								<td><input type="text" name="search_positions"
								placeholder="Positions" class="search_init" /></td>
								<td><input type="text" name="search_open"
								placeholder="Open" class="search_init" /></td>
								<td><input type="text" name="search_submissions"
								placeholder="Submissions" class="search_init" /></td>
								<td><input type="text" name="search_shortListed"
								placeholder="ShortListed" class="search_init" /></td>
						<td><input type="text" name="search_closed"
								placeholder="Closed" class="search_init" /></td>
						<td><input type="text" name="search_notFit"
								placeholder="Not Fit" class="search_init" /></td>
								<td><input type="text" name="search_hold"
								placeholder="Hold" class="search_init" /></td>
									<td><input type="text" name="search_lost"
								placeholder="Lost" class="search_init" /></td>
								<td><input type="text" name="search_requestedBy"
								placeholder="Requested By" class="search_init" disabled="disabled"/></td>
								<td><input type="text" name="search_additionalComments"
								placeholder="Additional Comments" class="search_init" disabled="disabled"/></td>
								<td><input type="text" name="search_status"
								placeholder="Status" class="search_init" /></td>
								<td><input type="text" name="search_requirementArea"
								placeholder="Requirement Area" class="search_init"  disabled="disabled"/></td>
						</tr>
					</thead>
					</table>
				</div>
				<div>
					<table id="NoAllocMessage"></table>
				</div>
			</div>
			<div id="myPreviewModal" class="modal">
				<div class="modal-content" style="width: 92%; margin-top: 40px;">
					<a href="#" onclick="closePopup()"><span class="close"
						id="close-icon" style="right: 20px; top: 18px;"></span></a>
					<h1 class="pd-20">
						<span class="positionStatus positionStatusPlaceHolder"></span>
					</h1>
					<div class="dataTables_wrapper pd-20 bg-none" role="grid">
						<table
							class="dataTbl display tablesorter addNewRow alignCenter my_table tablefixed"
							id="modal_data_table"
							style="overflow: auto; display: inline-block; height: 200px; border: 1px solid #ccc;">
							<thead>
								<tr class="content">
									<!-- <th align="center" valign="middle">RRF ID</th> -->
									<th align="center" valign="middle" class="no-sort" style="background:#fff !important;">Actions</th>
									<th align="center" valign="middle">Type</th>
									<th align="center" valign="middle" width="1%">Resource
										Name</th>
									<th align="center" valign="middle">Skill</th>
									<th align="center" valign="middle">Designation</th>
									<!-- <th align="center" valign="middle">Status</th> -->
									<!-- <th align="center" valign="middle">Date</th>  -->
									<th align="center" valign="middle">Profile Status</th>
									<!-- <th align="center" valign="middle">Comments</th> -->
									<th align="center" valign="middle">Joining Date /
										Interview Date</th>
									<th align="center" valign="middle">Location</th>
									<th align="center" valign="middle">Notice Period</th>
									<th align="center" valign="middle">Exp(Total IT Exp)</th>
									<th align="center" valign="middle">Contact</th>
									<th align="center" valign="middle">Email</th>
									<!-- <th align="center" valign="middle">Resume</th>
										<th align="center" valign="middle">TEF</th>  -->

								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					<div class="close-right">
						<button type="button" id="resource-update-btn"
							class="btn btn-secondary next-button" style="margin: 20px"
							onclick="updateResourcePopup()">Update Resource Status</button>
						<button type="button" id="close-btn"
							class="btn btn-secondary next-button" style="margin: 20px"
							onclick="closePopup()">Close</button>
					</div>
					<!-- <div class="close-right">
							<button type="button" id="close-btn"
								class="btn btn-secondary next-button" style="margin: 20px" onclick="closePopup()">Close</button>
						</div> -->
				</div>
			</div>

			<div id="myCommentsModal" class="modal">
				<div class="modal-content"
					style="width: 80%; margin-top: 40px; display: table;">
					<a href="#" data-dismiss="modal"><span class="close"
						id="close-icon" style="right: 20px; top: 18px;"></span></a>
					<h1 class="pd-20">
						<span class="positionStatus positionStatusPlaceHolder"><span
							class="resourceSubmissionss">Comments</span></span>
					</h1>
					<div class="col-md-12 my-comments-modal"
						style="max-height: 400px; overflow: auto;">
						<section class="sidebar" style="height: auto;">
							<!-- sidebar menu: : style can be found in sidebar.less -->
							<div id="resourceCommentId">
								<ul class="sidebar-menu" id="dynamic-list">
									<!-- <li class="treeview" >
										<a href="javascript:void(0);"> <span id="CandidateName">Name of candidate</span>
											<i 	class="fa fa-angle-left pull-right"></i>
										</a>
										  <ul class="treeview-menu" id="dynamic-list">
												<a href="javascript:void(0);"></a>
										</ul>  
									</li> -->
								</ul>

							</div>
						</section>
					</div>
					<div class="close-right">
						<button type="button" id="close-btn"
							class="btn btn-secondary next-button" style="margin: 20px"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>

			<div id="myPreviewModalForComment" class="modal">
				<div class="modal-content" style="width: 80%; margin-top: 40px;">
					<a href="#" onclick="closePopup()"><span class="close"
						id="close-icon" style="right: 20px; top: 18px;"></span></a>
					<h1 class="pd-20">
						<span class="positionStatus positionStatusPlaceHolder"></span>
					</h1>
					<div class="dataTables_wrapper pd-20 bg-none" role="grid">
						<table
							class="dataTbl display tablesorter addNewRow alignCenter my_table"
							id="modal_data_table"
							style="overflow: auto; display: inline-block; height: 200px; border: 1px solid #ccc;">
							<thead>
								<tr class="content">

									<th align="center" valign="middle" width="1%">Resource
										Name</th>
									<th align="center" valign="middle">Skill</th>
									<th align="center" valign="middle">Designation</th>
									<th align="center" valign="middle">Status</th>
									<th align="center" valign="middle">Date</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					<div class="close-right">
						<button type="button" id="close-btn"
							class="btn btn-secondary next-button" style="margin: 20px"
							onclick="closePopup()">Close</button>
					</div>
				</div>
			</div>

			<!-- start: Forward Popup  -->

			<div id="forwardModal" class="modal">
				<div class="modal-content" style="width: 530px; margin-top: 40px;">
					<a href="#" data-dismiss="modal" onclick="closeRRFPopup()"><span
						class="close" id="close-icon" style="right: 20px; top: 18px;"></span></a>
					<h1 class="pd-20">
						<span id="forward_placeholder">Forward RRF</span>
					</h1>
					<div class="dataTables_wrapper pd-20 bg-none" role="grid">
						<form id="forwardRRFForm" action="#">
							
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td>Select Recipient</td>
										<td>
											<select id="internalUserIds"  name="users[]" multiple="multiple">
												<%-- <c:forEach var="activeUsers" items="${activeUserList}">
													<option value="${activeUsers.emailId}">${activeUsers.firstName}
														${activeUsers.lastName}</option>
												</c:forEach> --%>
										</select>
										</td>
									</tr>
									<tr>
											<td>Select PDL Group</td>
											<td class="positionRel">
												<select id="pdlGroupIds" name="pdlGroupIds[]" multiple="multiple">
														<%-- <c:forEach var="pdlGrp" items="${pdlGroups}">
															<option value="${pdlGrp.pdlEmailId}">${pdlGrp.pdlEmailId}</option>
														</c:forEach> --%>
												</select>				
											</td>
										</tr>
										<tr>
											<td>RRF Forward Comment (if Any):</td>
											<td>
													<input type="text" id="commentForwardRRF"  name="commentForwardRRF" >
													<input name="skillRequestId"
														id="skillRequestId" type="hidden" />
											</td>
										</tr>
								</tbody>
							</table>
						</form>
					</div>
					<div class="close-right">
						<button type="button" id="close-btn"
							class="btn btn-secondary next-button" style=""
							data-dismiss="modal" onclick="closeRRFPopup()">Close</button>
						<button type="button" id="forward-btn" class="btn next-button"
							style="margin: 10px 28px 10px 10px;" data-dismiss="modal"
							onclick="onForwardRRF()">Submit</button>
					</div>
				</div>
			</div>

			<div class="modal fade" id="resourceCommentModal" role="dialog">
				<div class="modal-dialog" style="margin-top: 0;">

					<!-- Modal content-->
					<div class="modal-content commentmodal">
						<div class="modal-header"
							style="background: #fff !important; border-bottom: 1px solid #ccc;">
							<button type="button" onclick="onClickclsComment()"
								id="clsComment" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title" style="color: #333; font-weight: bold;"
								id="reqNameId"></h4>
						</div>
						<div class="modal-body commentBody" id="getResultDiv"></div>
						<div class="commentbox"
							style="z-index: 1; position: relative; background: #f5faff; border: 0;">

							<label for="usr" style="display: block;">Comment:</label>
							<textarea class="form-control" id="usr" cols="20" wrap="hard"
								style="display: inline-block; width: 82%;"></textarea>
							<input id="resCMId" type="hidden" value="" />
							<button onclick="strCSubmit()" type="button" id="strCSubmit"
								class="btn my_btn"
								style="position: absolute; margin-top: 14px; margin-left: 12px; padding: 4px 3px;">Submit</button>
						</div>
					</div>

				</div>
			</div>

						
			<div id="InterviewPanelModal" class="modal">
				<div class="modal-content" style="width: 750px;padding:0 30px; margin-top: 40px;">
					<div class="row">
					<h1 style="padding: 20px 0 20px 10px;">Add Interview Panels
						<span class="text-inside-brackets title-sub-txt">(preferrably E3 and above)</span>
						<a href="#" onclick="closeInterviewPanelPopUp()"><span
						class="close" id="close-icon" style="right: 0px;top: 6px;z-index: 999;float: right;width: 30px;height: 16px;background: none;"></span></a>
					</h1> 
							<div class="form-row">
								<div class="form-group clearfix">																	 
									<div class="col-md-4">
										<label for="round1Select"><span data-toggle="tooltip" title="" data-placement="right" data-original-title="Select Interviwer(s) for round 1, mandatory for ODC">Key
												Interviewer(s) Round 1</span></label>
										<div class="positionRel">											
											<select id="round1Select"  name="usersForRound1Select[]" multiple="multiple">
												<%-- <c:forEach var="activeUsers" items="${activeUserList}"> 
													<option value="${activeUsers.employeeId}">${activeUsers.firstName} ${activeUsers.lastName}</option>
												</c:forEach> --%>
											</select>
										</div>
									</div>
									<div class="col-md-8">
										<label for="keyInterviewer1"><span>&nbsp;</span></label>
										<textarea  class="col-md-12 disabled_val form-control" id="keyInterviewer1" disabled></textarea>
									</div>
								</div>
								<div class="form-group clearfix">
									<div class="col-md-4">
										<label for="roundTwoSelect"><span data-toggle="tooltip" title="" data-placement="right" data-original-title="Select Interviwer(s) for round 2, mandatory for ODC">Key
												Interviewer(s) Round 2</span></label>
										<div class="positionRel">											
											<select id="roundTwoSelect"  name="usersForRound2Select[]" multiple="multiple">
												<%-- <c:forEach var="activeUsers" items="${activeUserList}"> 
													<option value="${activeUsers.employeeId}">${activeUsers.firstName} ${activeUsers.lastName}</option>
												</c:forEach> --%>
											</select>
										</div>
									</div>
									<div class="col-md-8">
										<label for="keyInterviewer2"><span>&nbsp;</span></label> 
										<textarea  class="col-md-12 disabled_val form-control" id="keyInterviewer2" disabled></textarea>
									</div>
								</div>
								
								<div>
								  <input name="skillRequestId" id="skillRequestId" type="hidden" value=""/> </div>
							</div>
						</div>
						
					<div class="close-right" style="margin:10px;">
						<button type="button" id="close-btn" class="btn btn-secondary next-button" style="background: #ccc;color: #000 !important;border: none;padding: 5px 26px;"
							onclick="closeInterviewPanelPopUp()">Close</button>
						<button type="submit" id="submit-btn" class="btn next-button" style="padding: 5px 26px;border: none;" onclick="submitInterviewPanels()">Submit</button>
					</div>
				</div>
			</div>
			
		</div>
	
	<!-- start: Delete popup -->

	<div id="updateResPopUp" class="modal">
		<div class="modal-content"
			style="width: 30%; margin-top: 40px; display: table;">
			<a href="javascript:void(0)" class="close-btn1"> <span
				class="close fa fa-close" id="close-icon"
				style="right: 20px; top: 22px; position: relative; font-size: 20px;"></span>
			</a>


			<h1 style="padding: 20px;">
				<span class="positionStatus positionStatusPlaceHolder"> <span
					class="resourceSubmissionss">Delete Resource</span>
				</span>
			</h1>

			<div class="col-md-12"
				style="background: #f5faff; padding: 20px 22px 15px;">

				<section class="sidebar" style="height: auto;">Do you
					Really want to change Resource Status ?</section>


			</div>
			<div class="close-right"
				style="display: inline-block; width: 100%; text-align: right;">

				<button type="button" id="deleteFinalBtnNew"
					onclick="updateResourceStatus()"
					class="btn btn-secondary next-button ">OK</button>
				<button type="button" id="close-btn"
					class="btn btn-secondary next-button close-btn1"
					style="margin: 10px 24px 10px 10px;">Close</button>

			</div>
		</div>

	</div>
	
	<div id="copyRRFModal" class="modal">
				<div class="modal-content"
					style="width: 340px; margin-top: 218px;">
					<h1 class="pd-20">Copy RRF
						<a href="#" onclick="closeCopyRRFModal()"><span class="close" style="right: 0px;top: 6px;z-index: 999;float: right;width: 30px;height: 16px;background: none;"></span></a>
					</h1>
					<div class="pd-20 my-comments-modal">
						<label class="">No. of Resources</label>
						<input type="number" id="numberOfresources" name="numberOfresources" class="form-control" onkeypress="onInputChange(event)">
					</div>
					
					<div>
					 	<input name="skillRequisitionId" id="skillRequisitionId" type="hidden" value=""/> 
					</div>


					<div class="close-right form-group">
						<button type="submit" id="submit-btn" class="btn next-button"
							style="padding: 5px 26px; border: none;" onclick="copyRRFSubmit()">Submit</button>
						<button type="button" id="close-btn"
						class="btn btn-secondary next-button" onclick="closeCopyRRFModal()"
						data-dismiss="modal">Close</button>
					</div>
					
				</div>
	</div>
	
	
	<div id="stakeHolderModal" class="modal">
				<div class="modal-content" style="width: 660px; padding:0 30px; margin-top: 40px;">
					<div class="row">
					<h1 style="padding: 20px 0 20px 10px;">Add Stake Holder
						<a href="#" onclick="closeStakeHolderPopUp()">
						<span class="close" id="close-icon" style="right: 0px;top: 6px;z-index: 999;float: right;width: 30px;height: 16px;background: none;"></span></a>
					</h1> 
							<div class="form-row">
								<div class="form-group clearfix">																	 
									<div class="col-md-4">
										<label for="stakeHolderSelectLable"><span data-toggle="tooltip" title="" data-placement="right">Notify to (CC)</span></label>
										<div class="positionRel">											
											<select id="stakeHolderSelect" name="usersForStakeHolderSelect[]" multiple="multiple"></select>
										</div>
									</div>
									<div class="col-md-8">
										<label for="stakeHoldertxtareaLable"><span>&nbsp;</span></label>
										<textarea class="col-md-12 disabled_val form-control rounded" id="stakeHoldertxtarea" disabled></textarea>
									</div>
								</div>
								<div>
								  <input name="skillRequestId" id="skillRequestId" type="hidden" value=""/> </div>
								  <input name="requestRequisitionId" id="requestRequisitionId" type="hidden" value=""/> </div>
							</div>
						<div class="close-right" style="margin:10px;">
							<button type="button" id="close-btn" class="btn btn-secondary next-button" style="background: #ccc;color: #000 !important;border: none;padding: 5px 26px;"
							onclick="closeStakeHolderPopUp()">Close</button>
							<button type="submit" id="submit-btn" class="btn next-button" style="padding: 5px 26px;border: none;" onclick="submitStakeHolders()">Submit</button>
					   </div>
				</div>
						
					
			</div>
			
			<!-- Model to add SF Id start-->
			<div id="SFIdModal" class="modal">
				<div class="modal-content" 	style="width: 340px; margin-top: 218px;">
					<h1 class="pd-20">Success Factor ID
						<a href="#" onclick="closeSFIdModal()"><span class="close" style="right: 0px;top: 6px;z-index: 999;float: right;width: 30px;height: 16px;background: none;"></span></a>
					</h1>
					<div class="pd-20 my-comments-modal">
						<label class="">SF ID</label>

						<input  class="form-control" type="text" id="sfID" name="sfID"  maxlength="50" >

					</div>
					
					<div><input name="requestRequisitionId" id="requestRequisitionId" type="hidden" value=""/> </div>


					<div class="close-right form-group">
						<button type="submit" id="submit-btn" class="btn next-button" style="padding: 5px 26px; border: none;" onclick="submitSFId()">Submit</button>
						<button type="button" id="close-btn" class="btn btn-secondary next-button" onclick="closeSFIdModal()" data-dismiss="modal">Close</button>
					</div>
				</div>
					
			</div>
	    
	    
	    <!-- Model to add SF Id end-->
	    <!-- Schedule interview modal start -->
	    <div id="interviewModal" class="modal">
			<!-- style="width: 530px; margin-top: 40px;" -->
  <div class="modal-content modal-lg">
    <!-- <a href="#" data-dismiss="modal" onclick="closeRRFPopup()"><span class="close" id="close-icon"
        style="right: 20px; top: 18px;"></span></a> -->
    <h1 class="pd-20">
      <span id="forward_placeholder">Schedule Discussion/Interview</span>
	  <a href="javascript:void(0);" type="button" class="close" onclick="closeInterviewPopup()"></a>
	<button type="button" id="forward-btn" class="btn next-button pull-right" onclick="onScheduleInterviewSubmit()">Send Email</button>
	  
	</h1>
	
    <div class="dataTables_wrapper pd-20 bg-none clearfix" role="grid">

			<div class="col-xs-12">
				<form>
					<div class="row">
						<div class="col-md-3 form-group">
							<label>Discussion With</label> <select id="discussionType"
								class="form-control">
								<option selected disabled hidden style='display: none' value="selected"></option>
								<option value="client">Client Interview</option>
								<option value="yashInternal">Yash Internal Interview</option>
							</select>
						</div>
						<div class="col-md-3 form-group">
							<label>Interview Mode</label>
							<select id="interviewmode"
								class="form-control">
								<option value="telephonic">Telephonic</option>
								<option value="faceToFace">Face To Face</option>
								<option value="f2fClient">Face to Face client Site</option>
								<option value="skype">Skype</option>
							</select>
						</div>
						<div class="col-md-3 form-group">
							<label>Interview Date</label> <input
								id="interviewdate" class="form-control" />
						</div>
						<div class="col-md-3 form-group">
							<label>Interview Time</label> <input type="time"
								id="interviewtime" class="form-control" />
						</div>
					</div>
					
					<div class="row">
						
						
					</div>
					<div class="row">
						<div class="col-md-3 form-group">
							<label>BG-BU</label> <input type="text" disabled="true" id="bgbu"
								class="form-control" />
						</div>
						<div class="col-md-3 form-group">
							<label>Requirement ID</label> <input type="text" disabled="true"
								id="reqid" class="form-control" />
						</div>
						<div class="col-md-3 form-group">
							<label>Customer Name</label> <input type="text" disabled="true"
								id="customerName" class="form-control" />
						</div>
						<div class="col-md-3 form-group">
							<label>Location</label> <input type="text" disabled="true"
								id="location" class="form-control" />
						</div>
					</div>
					<!-- <div class="row">
						
					</div> -->
					<div class="row">
						<div class="col-md-3 form-group">
							<label>Resource Name</label> <input type="text" disabled="true"
								id="resourcename" class="form-control" />
						</div>
						<div class="col-md-3 form-group">
							<label>Employee Id</label> <input type="text" disabled="true"
								id="employeeid" class="form-control" />
						</div>
						<div class="col-md-3 form-group">
								<label>Delivery POC Name</label> <input type="text"
									disabled="true" id="deliveryPOCName" class="form-control" />
							</div>
							<div class="col-md-3 form-group">
								<label>Mobile Number</label> <input type="text"
									id="deliveryPOCCont" disabled="disabled" class="form-control" />
							</div>
					</div>

					<!-- <div class="row">
						
					</div> -->
					<div class="row">
						<div class="col-md-3 form-group">
							<label>AM POC Name</label> <input type="text" id="amPOCName"
								class="form-control" />
						</div>
						<div class="col-md-3 form-group">
							<label>Mobile Number</label> <input type="text" id="amPOCCont"
								class="form-control" />
						</div>

						<div class="col-md-3 form-group" id="custPOCNameDiv"
							style="display: none;">
							<label>Customer POC Name</label> <input type="text"
								id="custPOCName" class="form-control" />
						</div>
						<div class="col-md-3 form-group" id="custPOCContDiv"
							style="display: none;">
							<label>Mobile Number</label> <input type="number"
								id="custPOCCont" class="form-control" />
						</div>
						<div class="col-md-3 form-group" id="contactPersonDiv"
							style="display: none;">
							<label>Contact Person</label> <select id="contactPerson"
								name="users3[]" data-type="internalUserIds_1"
								name="internalUserIds" class="commonSelectClass"
								multiple="multiple">
								<%--  <c:forEach var="activeUsers" items="${activeUserList}">
                <option value="${activeUsers.emailId}">${activeUsers.firstName}
                                ${activeUsers.lastName}</option>
                </c:forEach> --%>
							</select>
						</div>
						<div class="col-md-3 form-group" id="contactPersonNumbDiv"
							style="display: none;">
							<label>Contact Person Number</label> <input type="number"
								id="contactPersonNumber" class="form-control" />
						</div>

					</div>

					<!-- <div class="row">
					</div> -->

					<div class="row">
						<div class="col-md-3 form-group" id="venueDiv"
							style="display: none;">
							<label>Venue</label>
							<textarea id="venue" class="form-control"></textarea>
						</div>
						<div class="col-md-3 form-group" id="gatenumberDiv"
							style="display: none;">
							<label>Gate Pass No.</label> <input type="text" id="gatenumber"
								class="form-control" />
						</div>
					</div>
					<div class="row">
						<div class="col-md-3 form-group">
							<label>Skill Category</label> <input type="text" disabled="true"
								id="skill" class="form-control" />
						</div>
						<div class="col-md-9 form-group">
							<label>Job Description</label>
							<textarea id="jobdesc" class="form-control"></textarea>
						</div>
						<!-- <div class="clearfix col-md-12">
                <em>* All above information are mandatory</em>
              </div> -->
            </div>
        </form>
	  </div>
	  <hr />
	  <div class="col-xs-12">
		  <div class="row">
			  <div class="col-md-3 form-group">
			  <label>Select Others</label>
			  <select id="internalUserIdsInterview" name="users3[]"
							data-type="internalUserIds_1" name="internalUserIds"
							class="commonSelectClass" multiple="multiple">
								<%--  <c:forEach var="activeUsers" items="${activeUserList}">
                <option value="${activeUsers.emailId}">${activeUsers.firstName}
                                ${activeUsers.lastName}</option>
                </c:forEach> --%>
				</select>
				</div>
		  </div>
		  <div class="row">
				<div class="col-md-4 text-mrgin form-group">
					<span><input type="checkbox" class="sendEmailclass"
						id="mailTo" value="mailToChecked" checked /></span><label class="padding-prop">Mail
						To</label>
					<textarea class="col-md-12  form-control" disabled="disabled" id="mailtoTextArea"></textarea>
				</div>

				<div class="col-md-4 text-mrgin form-group">
					<span><input type="checkbox" class="sendEmailclass"
						id="notifyTo" value="notifyToChecked" checked /></span><label
						class="padding-prop">Notify to(cc)</label>
					<textarea class="col-md-12  form-control" disabled="disabled" id="notifyToTextArea"></textarea>
				</div>

				<div class="col-md-4 text-mrgin form-group">
					<span><input type="checkbox" class="sendEmailclass"
						id="pdlsTo" value="pdlsToChecked" checked /></span><label class="padding-prop">PDLs
						To</label>
					<textarea class="col-md-12  form-control" disabled="disabled" id="pdlTextArea"></textarea>
				</div>
		  </div>

		  <div class="row">
			  <div class="col-md-8">
				<label>Additional Information : </label>
				<textarea id="mailText" class="form-control" rows="2"></textarea>
				</div>
			</div>
		  <input name="requestResourceId" id="requestResourceId" type="hidden" />

	  </div>


		</div>
		<div class="close-right">
			<button type="button" id="close-btn"
				class="btn btn-secondary next-button" style=""
				onclick="closeInterviewPopup()">Close</button>
			<button type="button" id="forward-btn" class="btn next-button"
				style="margin: 10px 28px 10px 10px;"
				onclick="onScheduleInterviewSubmit()">Send Email</button>
		</div>
	</div>
</div>
<!-- Schedule interview modal end -->
	    
		</div>
	
	</div>

	<script type="text/javascript">
		function onInputChange(evt) {
			if (evt.which != 8 && evt.which != 0 && evt.which < 48 || evt.which > 57)
			{
				evt.preventDefault();
			}
		}
	</script>
</body>
</html>