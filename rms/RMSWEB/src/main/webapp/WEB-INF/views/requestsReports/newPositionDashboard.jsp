<%@ page import="org.yash.rms.util.Constants"%>
<%@ page import="org.yash.rms.util.UserUtil"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<spring:message code="application_js_version" var="app_js_ver"
	htmlEscape="false" />
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>
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
<spring:url value="/resources/js-framework/blockUI/jquery.blockUI.js?ver=${app_js_ver}"	var="blockUI" />
<script src="${blockUI}" type="text/javascript"></script>


<!-- Style for page -->
<style type="text/css" title="currentStyle">
table.tablesorter tr td a {
	color: black;
	text-decoration: none;
	font-size: 12px;
}
#records_table {
	overflow-x: scroll;
	overflow-y : scroll;
	max-width: 100%;
	display: block;
	white-space: nowrap;
	min-height: 150px;
	max-height:370px;
	cursor: pointer;
	margin-top: 25px;
}
#records_table tbody tr:nth-child(10) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}
 
#records_table tbody tr:nth-child(9) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}
 
#records_table tbody tr:nth-child(8) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}
 
#records_table tbody tr:nth-child(7) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
} 
#records_table tbody tr:nth-child(25) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}
 
#records_table tbody tr:nth-child(24) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}
 
#records_table tbody tr:nth-child(23) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}
 
#records_table tbody tr:nth-child(22) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}
#records_table tbody tr:nth-child(49) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}
 
#records_table tbody tr:nth-child(50) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}
#records_table tbody tr:nth-child(99) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}
 
#records_table tbody tr:nth-child(100) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}

#records_table .hover-area { 
    width: 20px;
    display: inline-block;
}
#records_table tr {
    height: 34px;
}
#records_table  .dropdown-menu.dropdown-content {
    left: 23px;
}
.dataTables_scrollBody {
	height: 751px !important;
}
.dataTables_info {
	padding: inherit;
}

.dataTables_filter{
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
</style>

<!-- Javascript for page -->
<script type="text/javascript" charset="utf-8">
var oTable="";
	//Ready function start
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
		
		//Code to populate DataTable - Start
		oTable = $('#records_table').dataTable( {
			"bProcessing": true,
	        "bServerSide": true,
	         "sPaginationType" : "full_numbers",	      
	        "scrollY": 200,
	        "scrollX": true,
	        "bSortCellsTop": true,
	        "sDom":"ltipr",
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
	                		
	                		return "" +id;
	                		
      	      		},"bSortable": false
      	      	 },
      	      	{ mData:'id',mRender:function ( id, type, row ){	     
	        		 
	                		var status = row.status;
	                		var requestRequisitionId = row.requestRequisition.id;
	                		var sentToList = row.sent_req_id;
	                		var pdlList = row.pdl_list;
	                		var resourcesCount = row.submissions;
	                		var reqSkillName = row.requirementId;
	                		
	                		return prepareAction(id, status, requestRequisitionId, sentToList, pdlList, resourcesCount, reqSkillName );
     	      		},"bSortable": false
     	      	 }
	        	 ,
      	      	 { mData:'requirementId',mRender:function ( requirementId, type, row ){
           		 	//This is RRF ID
      	      		 return ""+requirementId;
           		 	
	      			},"bSortable": false
	      	    },
	      	  { mData:'designation.designationName',mRender:function ( designation, type, row ){
       		 	return ""+designation;
       		 	//return prepareAction();
	      			},"bSortable": false
	      	    },
	      	  { mData:'skill.skill',mRender:function ( skill, type, row ){
         		 	return ""+skill;
         		 	//return prepareAction();
	      			},"bSortable": false
	      	    },
	      	  { mData:'noOfResources',mRender:function ( noOfResources, type, row ){
         		 	return ""+noOfResources;
	      			},"bSortable": false
	      	    },
	      	  { mData:'open',mRender:function ( open, type, row ){
       		 	return ""+open;
	      			},"bSortable": false
	      	    },
	      	  { mData:'submissions',mRender:function ( submissions, type, row ){
	       		 	return ""+submissions;
		      			},"bSortable": false
		      	    },
		      	  { mData:'shortlisted',mRender:function ( shortlisted, type, row ){
		       		 	return ""+shortlisted;
			      			},"bSortable": false
			      	    },
	      	  { mData:'closed',mRender:function ( closed, type, row ){
       		 	return ""+closed;
	      			},"bSortable": false
	      	    },
	      	  
	      	 { mData:'notFitForRequirement',mRender:function ( notFitForRequirement, type, row ){
	       		 	return ""+notFitForRequirement;
		      			},"bSortable": false
		      	    },
		      	  { mData:'hold',mRender:function ( hold, type, row ){
	         		 	return ""+hold;
		      			},"bSortable": false
		      	    },
		      	  { mData:'lost',mRender:function ( lost, type, row ){
	         		 	return ""+lost;
		      			},"bSortable": false
		      	    },
	      	  { mData:'allocationType.aliasAllocationName',mRender:function ( aliasAllocationName, type, row ){
	       		 	return ""+aliasAllocationName;
		      			},"bSortable": false
		      	    },
		      	  { mData:'requestRequisition.resource.firstName',sDefaultContent: "NA" ,mRender:function ( firstName, type, row ){
		      		  var lastName = row.requestRequisition.resource.lastName;
		      		  
		      		  if(lastName!='' || lastName != 'undefined'){
		      			return ""+firstName + " " + lastName;
		      		  }else
		      			  return ""+firstName;
			      	},"bSortable": false
			       },
		      	  { mData:'desirableSkills',mRender:function ( additionalComments, type, row ){
		       		 	return ""+additionalComments;
			      			},"bSortable": false
			      	    },
			      	  { mData:'status',mRender:function ( status, type, row ){
			       		 	return ""+status;
				      			},"bSortable": false
				      	    }
	        	
	        ]
		});
		//Code to populate DataTable - Stop
	    $("thead input").keyup( function(i){
	    	if(i.which==13||(i.which==8 && this.value.length==0)){
	    		//alert(this.value)
		    	oTable.fnFilter( this.value, oTable.oApi._fnVisibleToColumnIndex(oTable.fnSettings(), $("thead input").index(this) ) );
	    	}
	    });
	});
	//Ready function stop
	
	//PrepareAction Start
	function prepareAction(id, status, requestRequisitionId, sentToList, pdlList, resourcesCount, reqSkillName ){
			
			
			
			var tdData='';
			
			return tdData;
		}
		
	//PrepareAction End
	
	
</script>

<!-- Design for page -->
<div class="content-wrapper">
	<div class="botMargin">
		<h1>Resource Request</h1>
	</div>
	<div class="tab_seaction">
		<div id='tab1' class="tab_div">
			<div class="tbl">
				<table class="dataTbl display tablesorter addNewRow alignCenter"
					id="records_table">
					<thead>
						<tr>
							<th align="center" valign="middle">Id</th>
							<th align="center" valign="middle">Actions</th>
							<th align="center" valign="middle">RRF ID</th>
							<th align="center" valign="middle">Designation</th>
							<th align="center" valign="middle">Required Skill</th>
							<th align="center" valign="middle">Positions</th>
							<th align="center" valign="middle">Open</th>
							<th align="center" valign="middle">Submissions</th>
							<th align="center" valign="middle">ShortListed</th>
							<th align="center" valign="middle">Closed</th>
							<th align="center" valign="middle">Not Fit</th>
							<th align="center" valign="middle">Hold</th>
							<th align="center" valign="middle">Lost</th>
							 <th align="center" valign="middle">Allocation Type</th>
							  <th align="center" valign="middle">Requested By</th>
							 <th align="center" valign="middle">Additional Comments</th>
							 <th align="center" valign="middle">Status</th>
						</tr>
						<tr>
						<td><input type="text" name="search_id"
								placeholder="Id" class="search_init" disabled="disabled" style="display: none"/></td>
						<td><input type="text" name="search_action"
								placeholder="Action" class="search_init" disabled="disabled" style="display: none"/></td>
						<td><input type="text" name="search_rrfId"
								placeholder="RRF ID" class="search_init" /></td>
				
						<td><input type="text" name="search_designation"
								placeholder="Designation" class="search_init" /></td>
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
										 <td><input type="text" name="search_allocationType"
								placeholder="Allocation Type" class="search_init" /></td>
								<td><input type="text" name="search_requestedBy"
								placeholder="Requested By" class="search_init" disabled="disabled"/></td>
								<td><input type="text" name="search_additionalComments"
								placeholder="Additional Comments" class="search_init" /></td>
								<td><input type="text" name="search_status"
								placeholder="Status" class="search_init" /></td>  
						</tr>
						
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>