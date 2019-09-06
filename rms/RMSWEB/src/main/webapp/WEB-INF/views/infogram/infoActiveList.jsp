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
<spring:url
	value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}"
	var="multiselect_js" />
<script src="${multiselect_js}" type="text/javascript"></script>
<spring:url value="/resources/js-framework/blockUI/jquery.blockUI.js?ver=${app_js_ver}"	var="blockUI" />
<script src="${blockUI}" type="text/javascript"></script>
<spring:url
	value="/resources/js/moment.min.js?ver=${app_js_ver}" var="moment_js" />
<script src="${moment_js}" type="text/javascript"></script>	
<spring:url
	value="resources/js/rmsUIValidation.js"
	var="rmsUIValidation_js" />
<script src="${rmsUIValidation_js}" type="text/javascript"></script>
<style type="text/css" title="currentStyle">
 
#info_table {
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
.ui-datepicker #clearDate{
display:none !important;
}
.rmsheadercolor {
    background: #f0ad4e!important;
}
.dataTables_info {
	padding: inherit;
}
/* .background-green{
    background-color: #72af4cad;
    text-align: center;
}
.background-red{
	    text-align: center;
    background-color: #ff0000cf;
} */
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
	bottom: 0px !important
}
 
#info_table tbody tr:nth-child(7) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
} 
#info_table tbody tr:nth-child(25) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}
 
#info_table tbody tr:nth-child(24) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}
 
#info_table tbody tr:nth-child(23) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}
 
#info_table tbody tr:nth-child(22) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}
#info_table tbody tr:nth-child(49) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}
 
#info_table tbody tr:nth-child(50) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}
#info_table tbody tr:nth-child(99) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
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
#info_table  .dropdown-menu.dropdown-content {
    left: 23px;
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
	width: 26px !important;
	height: 26px !important;
}
 
table.tablesorter tr td a {
	color: black;
	text-decoration: none;
	font-size: 12px;
}
 
.create-employee-modal {
	text-align: left !important;
	
}
.emp-status {
	position: absolute;
	font-weight: bold;
	left: 150px;
	z-index: 9;
	top: 13px;"
}

.emp-processStatus{
	font-weight: bold;
	position: absolute;
	left: 330px;
	z-index: 9;
	top: 13px;"
} 
.rec-status {
	position: absolute;
	font-weight: bold;
	left: 520px;
	z-index: 9;
	top: 13px;"
}
.border-green {
border: 5px solid #00a65a !important;
}
.bg-green {
background-color: #4CAF50!important
}
.border-red {
border: 5px solid #e91e27;
}
.employee-edit-detail .form-group.col-md-3.autoSelect {
	display: grid;
}
 
.employee-edit-detail .form-group.col-md-3.autoSelect input {
	text-align: left !important;
}
 
.employee-edit-detail .form-group.col-md-3.autoSelect label {
	width: 100%;
}
 
 
.required {
	display: none;
}
 
.datepickeremp .positionRel .input-group {
	width: 96%;
	margin-left: 0;
}
 
.datepickeremp .positionRel .input-group label {
	border-top-right-radius: 5px;
	border-bottom-right-radius: 5px;
}
 
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
</style>

<script type="text/javascript" charset="utf-8">
	var dtTable = "";
	var oTable="";
	var dddd="";
	var aaa="";
	const EXISTING = "Existing";
	const NEWJOINEE = "New Joinee";
	const FAILURE = "FAILURE";
	const NEW = "New";
	const SUCCESS = "SUCCESS";
	const DISCARD = "DISCARD";
	const BOTH = "BOTH";
	const NA = "NA";
	const ID = "ID";
	const RED = "red";
	const GREEN = "green";
	const BGBU = "bgbu";
	const BG = "bg";
	const BU = "bu";
	
	 $( function() {
		    dddd=$( "#doj" ).datepicker({
		    		autoclose:true,
				 dateFormat: "dd-M-yy" ,
				 changeMonth: true,
				 defaultDate: null,
				 setDate:null,
				 changeYear: true,			 
				 onClose: function(selectedDate) {
					 
					 var e = $.Event("keyup");
					 e.which = 13;
					 $('#doj').trigger(e);
					 $("#doj").blur();
				      }
				 
				});
		  } );
	 $( function() {
		    aaaa=$( "#createdTime" ).datepicker({
		    		autoclose:true,
				 dateFormat: "dd-M-yy" ,
				 changeMonth: true,
				 defaultDate: null,
				 setDate:null,
				 changeYear: true,			 
				 onClose: function(selectedDate) {
					 
					 var e = $.Event("keyup");
					 e.which = 13;
					 $('#createdTime').trigger(e);
					 $("#createdTime").blur();
				      }
				 
				});
		  } );
	 var allExistNewVar = '';
	 var allDiscardSuccessVar = '';
	 var allOkVar="";
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
		allExistNewVar = $('#listActiveOrAll').val();
		allDiscardSuccessVar = $('#listProcessStatus').val();
		allOkVar=$('#listRecordStatus').val();
		if(allExistNewVar=='undefined' || allExistNewVar==''){
			allExistNewVar ='All';
		}
		if(allDiscardSuccessVar=='undefined' || allDiscardSuccessVar==''){
			allDiscardSuccessVar ='All';
		}
		if(allOkVar=='undefined' || allOkVar==''){
			allOkVar ='All';
		}
	    oTable = $('#info_table').dataTable( {
	        "bProcessing": true,
	        "bServerSide": true,
	         "sPaginationType" : "full_numbers",
	      
	        "scrollY": 200,
	        "scrollX": true,
	        "bSortCellsTop": true,
	        "sDom":"ltipr",
	        "sAjaxSource": 'getallinfogramactiveresourcesList/'+allExistNewVar+'/'+allDiscardSuccessVar+'/'+allOkVar,	        	 
	                 "aoColumns": [
	                	 {mData:'id',mRender:function ( id, type, row )  
                		 	{
		                		 var moveId = ""+id;	  
		                		 
		                			 if(row.processStatus!=SUCCESS)
		                				 {
		                		 			return prepareAction(row.resourceType, moveId, row.processStatus);
		                				 }
		                			 else
		               					 {
		               						 return "";
		               					 }
		                		
 	        	      		},"bSortable": false},
	                	 {mData:'employeeId',sDefaultContent: "NA" },
	                	 {mData:'name', sDefaultContent: "NA" },
	                	 {mData:'resourceType', sDefaultContent: "NA" , mRender:function ( resourceType, type, row )  {
	                	 	        	          var valueOfResType = "";
	                	 						   if(resourceType=="New"){
	                	 							 valueOfResType = NEWJOINEE;
	                	 						  }else {
	                	 							 valueOfResType=  EXISTING;
	                	 						  } 
	                	 						  return valueOfResType;
	                	 	        	      },"bSortable": false}, 
	                	 {mData:'processStatus', sDefaultContent: "NA",  mRender:function ( processStatus, type, row )  {
	                			var dtData="";
	                			var failureReason =row.failureReason;

	                			if(processStatus==FAILURE){	 

 	 								dtData=dtData + '<div ><a class="blue-link" data-toggle="modal" data-target="#failModal" onClick="showFailReason(`'+failureReason+'`)">Failure</a>';
 	 								return dtData;
 	 							}
 	 						  return processStatus;
 	 						  },"bSortable": false },
	                	 {mData:'dateOfJoining',sDefaultContent: "NA" , mRender:function ( dateOfJoining, type, row )  {
	                		
	                	 						var formattedDate = moment(dateOfJoining).format('DD-MMM-YYYY');	                	 	        	          
	                	 						  return formattedDate;
	                	 						  }},
	                	 {mData:'emailId', sDefaultContent: "NA" },
	                	 {mData:'designation', sDefaultContent: "NA" },
	                	 {mData:'rmsDesignation', sDefaultContent: "NA", mRender:function ( rmsDesignation, type, row )  {
	                		 
	                		if(row.resourceType==EXISTING){
	                			if(rmsDesignation.replace(/\s/g, "").toUpperCase()==row.designation.replace(/\s/g, "").toUpperCase() && rmsDesignation!="NA")
        							return '<div class="bg-green">'+rmsDesignation+'</div>';
        					else
        							return '<div class="bg-red">'+rmsDesignation+'</div>';
	                		 }
	                		
					  },"bSortable": false},
	                	
	                	 {mData:'baseLocation', sDefaultContent: "NA" },
	                	 {mData:'locationInRMS', sDefaultContent: "NA", mRender:function ( locationInRMS, type, row )  {
	                		 if(row.resourceType==EXISTING){
         					/* if(locationInRMS==row.baseLocation && locationInRMS!="NA")
        						return '<div class="bg-green">'+locationInRMS+'</div>';
        					else
        						return '<div class="bg-red">'+locationInRMS+'</div>'; */
	                			 var color="";
	                			 color=colorCoding(locationInRMS,locationInRMS,row.baseLocation);
	                			 if(color==GREEN){
	                				 return '<div class="bg-green">'+locationInRMS+'</div>'
	                			 }else if(color==RED){
	                				 return '<div class="bg-red">'+locationInRMS+'</div>';
	                			 }
	                		 }
	                		// return locationInRMS;
					  },"bSortable": false},
				 	  {mData:'currentLocation', sDefaultContent: "NA" },
				 	 {mData:'currentLocInRMS', sDefaultContent: "NA", mRender:function ( currentLocInRMS, type, row )  {
                		 if(row.resourceType==EXISTING){
  							/* if(currentLocInRMS==row.currentLocation && currentLocInRMS!="NA")
 								return '<div class="bg-green">'+currentLocInRMS+'</div>';
 							else
 								return '<div class="bg-red">'+currentLocInRMS+'</div>'; */
                			 var color="";
                			 color=colorCoding(currentLocInRMS,currentLocInRMS,row.currentLocation);
                			 if(color==GREEN){
                				 return '<div class="bg-green">'+currentLocInRMS+'</div>'
                			 }else if(color==RED){
                				 return '<div class="bg-red">'+currentLocInRMS+'</div>';
                			 }
                		 }
                		
				  },"bSortable": false},
				  {mData:'businessGroup',sDefaultContent: "NA",  mRender:function ( businessGroup, type, row )  {
	                	
						 if(row.resourceType==EXISTING){
							 var colorbg="";
                			 var colorbgbu="";
                			 colorbg=colorCoding(businessGroup,row.infoProjectBgBu,BG);
                			 colorbgbu=colorCoding(businessGroup,row.infoProjectBgBu,BGBU);
                			if(colorbg==GREEN || colorbgbu==GREEN)
                			 	return '<div class="bg-green">'+businessGroup+'</div>';
                			 else if(colorbg==RED || colorbgbu==RED) 
                				return '<div class="bg-red">'+businessGroup+'</div>';
                				else
                					 return '<div>'+businessGroup+'</div>';
                			 }
						 else{
							 return '<div>'+businessGroup+'</div>';
						}
          			
          			                		 
          	 },"bSortable": false},
        
	             
	                	 {mData:'businessUnit',sDefaultContent: "NA",  mRender:function ( businessUnit, type, row )  {
			                	
	                		 if(row.resourceType==EXISTING){
	                			 var colorbg="";
	                			 var colorbgbu="";
	                			 colorbu=colorCoding(businessUnit,row.infoProjectBgBu,BU);
	                			 colorbgbu=colorCoding(businessUnit,row.infoProjectBgBu,BGBU);
	                			if(colorbu==GREEN || colorbgbu==GREEN)
	                			 	return '<div class="bg-green">'+businessUnit+'</div>'
	                			 	else if(colorbu==RED || colorbgbu==RED)
	                					return '<div class="bg-red">'+businessUnit+'</div>';
	                				else 	return '<div>'+businessUnit+'</div>'
	                			 }
		                		 else{
								 return '<div>'+businessUnit+'</div>';
								 }
	                			                		 
	                	 },"bSortable": false},
	                	 {mData:'rmsBg',sDefaultContent: "NA",  mRender:function ( rmsBg, type, row )  {
	                		
	                		 if(row.resourceType==EXISTING){
	                			 var colorbg="";
	                			 var colorbgbu="";
	                			 colorbg=colorCoding(rmsBg,row.RMSProjectBgBu,BG);
	                			 colorbgbu=colorCoding(rmsBg,row.RMSProjectBgBu,BGBU);
	                			if(colorbg==GREEN || colorbgbu==GREEN)
	                			 	return '<div class="bg-green">'+rmsBg+'</div>'
	                			 else if(colorbg==RED || colorbgbu==RED)
	                				return '<div class="bg-red">'+rmsBg+'</div>';
	                				else
		                				return '<div>'+rmsBg+'</div>'
	                			 }                		 
	                	 },"bSortable": false},
	                	 {mData:'rmsBu',sDefaultContent: "NA",mRender:function ( rmsBu, type, row )  {
	                		 if(row.resourceType==EXISTING){
	                			 var colorbu="";
	                			 var colorbgbu="";
	                			 colorbu=colorCoding(rmsBu,row.RMSProjectBgBu,BU);
	                			 colorbgbu=colorCoding(rmsBu,row.RMSProjectBgBu,BGBU)
		                			if(colorbu==GREEN || colorbgbu==GREEN)
		                			 	return '<div class="bg-green">'+rmsBu+'</div>'
		                			 else if(colorbu==RED || colorbgbu==RED)
		                				return '<div class="bg-red">'+rmsBu+'</div>';
		                			else
		                				return '<div>'+rmsBu+'</div>';
		                			  
	                		 }
					  },"bSortable": false },
	                
					  {mData:'irmName',sDefaultContent: "NA",mRender:function ( irmName, type, row )  {
						   var data="";
						   data= getRMSData(row, irmName, row.irmEmployeeId, row.infoIRMBgBu );
						   return getRepoManager(row, data, row.rmsIrmYashEmpId, row.irmEmployeeId);
						  
						}
					  },
	                	 {mData:'irmInRMS',sDefaultContent: "NA", mRender:function ( irmInRMS, type, row )  {
	                		var data="";
	                		return getRMSData(row, irmInRMS, row.rmsIrmYashEmpId, row.IRMBgBu);
	                		//return getRepoManager(row, data, row.rmsIrmYashEmpId, row.irmEmployeeId);
	                		 
	                		
					  },"bSortable": false },
	             
					  {mData:'srmName',sDefaultContent: "NA",mRender:function ( srmName, type, row )  {
						  var data="";
						  data= getRMSData(row, srmName, row.srmEmployeeId, row.infoSRMBgBu);
						  return getRepoManager(row, data, row.rmsSrmYashEmpId, row.srmEmployeeId);
						
					  		}
					  },
	                	 {mData:'srmInRMS',sDefaultContent: "NA",mRender:function ( srmInRMS, type, row )  {
	                		
	                		 return getRMSData(row, srmInRMS, row.rmsSrmYashEmpId, row.SRMBgBu );
	                		 //return getRepoManager(row, data, row.rmsSrmYashEmpId, row.srmEmployeeId);
	                		 
						 
	                	 },"bSortable": false }, 
	                	
	                	 {mData:'creationTimestamp',sDefaultContent: "NA" , mRender:function ( creationTimestamp, type, row )  {
		                		
 	 						var formattedDate = moment(creationTimestamp).format('DD-MMM-YYYY');	                	 	        	          
 	 						  return formattedDate;
 	 						  }},
 	 						{mData:'modifiedName', sDefaultContent: "NA" }
	                	 
	                	 ]
	    } );
 		$("thead input").keyup( function(i){
	    	
	    	if(i.which==13||(i.which==8 && this.value.length==0))
	    		{
	    		
	    	oTable.fnFilter( this.value, oTable.oApi._fnVisibleToColumnIndex(oTable.fnSettings(), $("thead input").index(this) ) );
	    		}
	    		});
 		function getRepoManager(row, data, rmsId, infoId)
 		{
 			
			 var color="";
 			 if(row.resourceType==EXISTING){
    			
    			 color=colorCoding(rmsId,rmsId,infoId);
    			 
    			 
    			 if(color==GREEN){
    				 //if(data.includes("bg-red"))
    				 	//data=data.replace("bg-red","bg-red border-green");
    				// else
    					//data=data.replace("bg-green","bg-green border-green");
    				 return data;
    				
    			 }else if(color==RED){
    				 if(data.includes("bg-red"))
     				 	data=data.replace("bg-red","bg-red border-red");
     				 else
     					data=data.replace("bg-green","bg-green border-red");
    			 }
    			
    		 }
 			return data;
 			
 		}
 		function getRMSData(row, name, id, bgbu)
 		{
 			 var displayVal ="";
			 var color="";
 			  if(row.resourceType==EXISTING){
    			
    			 color=colorCoding(name,bgbu,BGBU);
    			 displayVal = displayValueOfRmsField(name,id);
    			 
    			 if(color==GREEN){
    				 if(displayVal==BOTH){
    				 	//return '<div class="bg-green">'+name+" ("+id+" )"+'</div>';
    					return '<div  class="bg-green">'+name+' ('+id+')</div>';
    				 }else if(displayVal==ID){
    					// return '<div   class="bg-green">'+id+'</div>';
    					return '<div  class="bg-green">'+id+'</div>';
    				 }else if(displayVal==NA){
    					 //return '<div>'+'NA'+'</div>';
    					 return '<div  class="bg-green">'+NA+'</div>';
    				 }
    			 }else if(color==RED){
    				 if(displayVal==BOTH){
        				 	//return '< class="bg-red">'+name+" ("+id+" )"+'</div>';
    						 return '<div  class="bg-red">'+name+' ('+id+')</div>';
        				 }else if(displayVal==ID){
        					// return '<div class="bg-red">'+id+'</div>';
        					 return '<div  class="bg-red">'+id+'</div>';
        				 }else if(displayVal==NA){
        					 //return '<div>'+'NA'+'</div>';
        					 return '<div  class="bg-red">'+NA+'</div>';
        				 }
    			 }
    			 else{
    				 if(displayVal==BOTH){
        				 	//return '<div>'+name+" ("+id+" )"+'</div>';
    					   return '<div >'+name+' ('+id+')</div>';
        				 }else if(displayVal==ID){
        					// return '<div id="'+recId+'+'row.id'">'+id+'</div>';
        					 return '<div>'+id+'</div>';
        				 }else if(displayVal==NA){
        					//return '<div>'+'NA'+'</div>';
        					 return '<div  ">'+NA+'</div>';
        				 }
    			 }
    		 }
 			 else{
 				 displayVal = displayValueOfRmsField(name,id);
					if(displayVal==BOTH){
 				 	//return '<div>'+name+" ("+id+" )"+'</div>';
						return '<div >'+name+' ('+id+')</div>';
 				 }else if(displayVal==ID){
 					 //return '<div>'+id+'</div>';
 					 return '<div >'+id+'</div>';
 				 }else if(displayVal==NA){
 					// return '<div>'+'NA'+'</div>';
 					 return '<div  ">'+NA+'</div>';
 				 }
 			 }  
 		}

 		
	} );
	
	 $(document.body).on('change','#listActiveOrAll',function(){
		 allExistNewVar=this.value;
		 var oSettings = oTable.fnSettings();
		 oSettings.sAjaxSource  = "getallinfogramactiveresourcesList/"+allExistNewVar+'/'+allDiscardSuccessVar+'/'+allOkVar;
		 //oTable.fnClearTable();
		 oTable.fnDraw();
		});
	 
	 $(document.body).on('change','#listProcessStatus',function(){
		 allDiscardSuccessVar=this.value;
		 var oSettings = oTable.fnSettings();
		 oSettings.sAjaxSource  = "getallinfogramactiveresourcesList/"+allExistNewVar+'/'+allDiscardSuccessVar+'/'+allOkVar;
		 //oTable.fnClearTable();
		 oTable.fnDraw();
		});
	 $(document.body).on('change','#listRecordStatus',function(){
		 allOkVar=this.value;
		 var oSettings = oTable.fnSettings();
		 oSettings.sAjaxSource  = "getallinfogramactiveresourcesList/"+allExistNewVar+'/'+allDiscardSuccessVar+'/'+allOkVar;
		 oTable.fnDraw();
		});
	 
	 function prepareAction(resourceType, eid, processStatus)
	 {
		 
		 var dtData="";
		 
		 if(processStatus==SUCCESS || processStatus=="NA"){
			 return dtData;
		 }
		 dtData= '<span class="dropdown dropleft dropDownHover hover-area" id="dropDownOpen">  <i class="fa fa-ellipsis-v" style="font-size: 15px;"></i>';
		 dtData= dtData + '<div class="dropdown-menu dropdown-content">  <div>';
		 if(resourceType=="New"){
			 dtData=dtData+'<div class="borderHover"> <a class="dropdown-item" onClick="moveEmployee('+eid+')"> <i class="fa fa-user-plus my_submit_btn"></i>Move</a></div>';
			
		 }
		 else{ 
			dtData=dtData + '<div class="borderHover"><a class="dropdown-item" onClick="moveEmployee('+eid+')"><i class="fa fa-user-plus my_submit_btn"	></i>Update</a></div>';
			/* dtData=dtData + '<div class="borderHover"><a class="dropdown-item" data-toggle="modal" data-target="#employeeModal"	onClick="editEmployee('+eid+')">';
			dtData=dtData + '<i	class="fa fa-pencil-square-o my_submit_btn"></i>Edit Employee</a>' ;*/
		}
		 
		 if(processStatus!=DISCARD){
			dtData=dtData+ '<div class="borderHover"><a class="dropdown-item" onClick="discardEmployee('+eid+')"><i class="fa fa-times-circle-o my_submit_btn"></i>Discard</a></div></span>';
		 } else{
			 
			// dtData=dtData+ '<div class="borderHover"><a class="dropdown-item" onClick="return false" disabled="disabled"><i class="fa fa-times-circle-o my_submit_btn"></i>Discard Employee </a></div></span>';
		 } 
          
    	
		return dtData;
		 
	 }
	 function showFailReason(failureReason)
	 {
			
			document.getElementById('failId').innerHTML=failureReason;
	 }
	 
	 function prepareResourceType(resourceType){
		 if(resourceType=="New")
			 return "New Joinee";
		else
		return "Existing";
		 
	 }
	 
	 function prepareStatus(status, id){
		 
		 var dtData="";
		 dtData='<span id="emp'+id+'">'+status+'</span>';
		 return dtData;
	 }
	 
	 function prepareDOJ(dateOfJoining){
		
		 var doj= new Date(dateOfJoining);
		 var dd = doj.getDate();
		 var mm = doj.getMonth()+1; 
		 var yyyy = doj.getFullYear();
	
		 if(dd<10) 
		 {
		     dd='0'+dd;
		 } 
 
		 if(mm<10) 
		 {
		     mm='0'+mm;
		 } 
		 doj = dd+'-'+mm+'-'+yyyy;
		
		return doj;
	 }
	
	 function getEmployees(empType) {
		
		 var employees = [];
			if(empType=="New"){
				<c:forEach var="info" varStatus="infoState"	items="${infogramList}">
				<c:choose>
					<c:when test="${info.resourceType=='New'}"> 
					     employees.push({
						'eId' : '${info.id}',
						'name' : '${info.name}',
						'employeeId' : '${info.employeeId}',
						'emailId' : '${info.emailId}',
						'designId' : '${info.designation}',
						'locId' : '${info.baseLocation}',
						'rmsLocId':'${info.locationInRMS.location}',
						'irmEmployeeId' : '${info.irmEmployeeId}',
						'srmEmployeeId' : '${info.srmEmployeeId}',
						'IRMNameId' : '${info.irmName}',
						'rmsIRMNameId':'${info.irmInRMS.firstName} ${info.irmInRMS.middleName} ${info.irmInRMS.lastName}',
						'SRMNameId' : '${info.srmName}',
						'rmsSRMNameId':'${info.srmInRMS.firstName} ${info.srmInRMS.middleName} ${info.srmInRMS.lastName}',
						'resourceType' : '${info.resourceType}',
						'statusId' : '${info.processStatus}',
						'dateOfJoining' : '${info.dateOfJoining}',
						'rmsBusinessUnit' : '${info.buIdInRMS.name}',
						'businessUnit' : '${info.businessUnit}',
						'businessGroup' : '${info.businessGroup}',
						'rmsBusinessGroup' : '${info.buIdInRMS.parentId.name}'
					});
	            	</c:when>
	            </c:choose>
	           </c:forEach> 
			}
			if(empType=="Old"){
				 <c:forEach var="info" varStatus="infoState"	items="${infogramList}">
					<c:choose>
						<c:when test="${info.resourceType=='Old'}">
			             employees.push({
			            		'eId' : '${info.id}',
								'name' : '${info.name}',
								'employeeId' : '${info.employeeId}',
								'emailId' : '${info.emailId}',
								'designId' : '${info.designation}',
								'locId' : '${info.baseLocation}',
								'rmsLocId':'${info.locationInRMS.location}',
								'irmEmployeeId' : '${info.irmEmployeeId}',
								'srmEmployeeId' : '${info.srmEmployeeId}',
								'IRMNameId' : '${info.irmName}',
								'rmsIRMNameId':'${info.irmInRMS.firstName} ${info.irmInRMS.middleName} ${info.irmInRMS.lastName}',
								'SRMNameId' : '${info.srmName}',
								'rmsSRMNameId':'${info.srmInRMS.firstName} ${info.srmInRMS.middleName} ${info.srmInRMS.lastName}',
								'resourceType' : '${info.resourceType}',
								'statusId' : '${info.processStatus}',
								'dateOfJoining' : '${info.dateOfJoining}',
								'rmsBusinessUnit' : '${info.buIdInRMS.name}',
								'businessUnit' : '${info.businessUnit}',
								'businessGroup' : '${info.businessGroup}',
								'rmsBusinessGroup' : '${info.buIdInRMS.parentId.name}'
						});
		            	</c:when>
		            </c:choose>
		            </c:forEach>  
				
			}
			if(empType=="All"){
				  <c:forEach var="info" varStatus="infoState"	items="${infogramList}">
					
			             employees.push({
			            		'eId' : '${info.id}',
								'name' : '${info.name}',
								'employeeId' : '${info.employeeId}',
								'emailId' : '${info.emailId}',
								'designId' : '${info.designation}',
								'locId' : '${info.baseLocation}',
								'rmsLocId':'${info.locationInRMS.location}',
								'irmEmployeeId' : '${info.irmEmployeeId}',
								'srmEmployeeId' : '${info.srmEmployeeId}',
								'IRMNameId' : '${info.irmName}',
								'rmsIRMNameId':'${info.irmInRMS.firstName} ${info.irmInRMS.middleName} ${info.irmInRMS.lastName}',
								'SRMNameId' : '${info.srmName}',
								'rmsSRMNameId':'${info.srmInRMS.firstName} ${info.srmInRMS.middleName} ${info.srmInRMS.lastName}',
								'resourceType' : '${info.resourceType}',
								'statusId' : '${info.processStatus}',
								'dateOfJoining' : '${info.dateOfJoining}',
								'rmsBusinessUnit' : '${info.buIdInRMS.name}',
								'businessUnit' : '${info.businessUnit}',
								'businessGroup' : '${info.businessGroup}',
								'rmsBusinessGroup' : '${info.buIdInRMS.parentId.name}'
						});
			            </c:forEach>  
				    	
			}
				
 
			return employees;
		}
	 
	
	
	
	//getting selected employee from employee list	
	function getEmployee(id) {
		 
		var employee;
		var employees = [];
 
		<c:forEach var="info" varStatus="infoState"	items="${infogramList}">
 
		employees.push({
			'eId' : '${info.id}',
			'name' : '${info.name}',
			'employeeId' : '${info.employeeId}',
			'emailId' : '${info.emailId}',
			'locId' : '${info.baseLocation}',
			'irmEmployeeId' : '${info.irmEmployeeId}',
			'srmEmployeeId' : '${info.srmEmployeeId}',
			'BUHId' : '${info.buhEmployeeId}',
			'BGHId' : '${info.bghEmployeeId}',
			'HRBPId' : '${info.hrbpEmployeeId}',
			'IRMNameId' : '${info.irmName}',
			'SRMNameId' : '${info.srmName}',
			'BUHNameId' : '${info.buhName}',
			'BGHNameId' : '${info.bghName}',
			'HRBPNameId' : '${info.hrbpName}',
			'genderId' : '${info.gender}',
			'designId' : '${info.designation}',
			'gradeId' : '${info.grade}',
			'businessGroup' : '${info.businessGroup}',
			'emptypeId' : '${info.employeeType}',
			'statusId' : '${info.processStatus}',
			'dateOfJoining' : '${info.dateOfJoining}',
			'businessUnit' : '${info.businessUnit}',
			'businessGroup' : '${info.businessGroup}'
		});
		</c:forEach>
		
		for (i = 0; i <= employees.length; i++) {
			employee = employees[i];
			if (employee.eId == id) {
				break;
			}
		}
		 
		return employee;
	}
 
	//editing selected employee
	function editEmployee(id) {
		 
		var employee = getEmployee(id);
		document.getElementById('updatedEmpId').value = employee.eId;
		document.getElementById("employeeId").value = employee.employeeId;
		document.getElementById("employeeName").value = employee.name;
		document.getElementById("locId").value = employee.locId;
		
		document.getElementById("IRMNameId").value = employee.IRMNameId;
		document.getElementById("SRMNameId").value = employee.SRMNameId;
		document.getElementById("emptypeId").value = employee.emptypeId;
		
		
	 	document.getElementById("buId").value = employee.businessUnit;
		document.getElementById("bgId").value = employee.businessGroup;
	/*	document.getElementById("BUHNameId").value = employee.BUHNameId;
		document.getElementById("emailId").value = employee.emailId;
		document.getElementById("BGHNameId").value = employee.BGHNameId;
		document.getElementById("HRBPNameId").value = employee.HRBPNameId;
 
		document.getElementById("genderId").value = employee.genderId;
		document.getElementById("designId").value = employee.designId;
		document.getElementById("gradeId").value = employee.gradeId;
		$("#dateofjoining").datepicker().datepicker("setDate",
				new Date(employee.dateOfJoining));
 */
	}
	//Moving employee from info
	function moveEmployee(id) {
		
		startProgress();
	     $.ajax({
           type: 'POST',
           url: 'saveinfogramactiveresource/'+id,
           dataType: 'text json',
           headers:{
                 'Accept': 'application/json'
           },   
         
           success: function(response){
                  if(null!=response){                                       
                        showSuccess("Resource Moved Successfully!" + " "+ response.errMsg);
            }else{   
               showError("Something happend wrong!! ");
            }
                 stopProgress();
                // window.location.reload();
                 oTable.fnClearTable();
           },
           error: function (xhr, status, thrownError) {
         	  stopProgress();
         	   if (xhr.responseText.startsWith("<!DOCTYPE")) {
                 //Session has Expired,redirect to login page
                    window.location.href  ="/rms/login";
             } else {
            		if(xhr.status!=200){
                 		showError(((JSON.parse(xhr.responseText))['errMsg'])); 
                 	 	
                 	}else
                 		{
                 		showError(((JSON.parse(xhr.responseText))['errMsg'])); 
                 		}
                 	oTable.fnClearTable();
                 }}
    });
		
	
	}
 
	function discardEmployee(id) {
		startProgress();
		   $.ajax({
            type: 'POST',
            url: 'discardinfogramresource/'+id,
            dataType: 'text json',
            headers:{
                  'Accept': 'application/json'
            },   
          
            success: function(response){
                    if(null!=response){                                       
                         showSuccess("Resource Discarded Successfully!" + " "+ response.errMsg);
             }else{   
                showError("Something happend wrong!! ");
             }
                  stopProgress();
                  //window.location.reload();
                  oTable.fnClearTable();
            },
            
            error: function (xhr, status, thrownError) {
            	  stopProgress();
            	   if (xhr.responseText.startsWith("<!DOCTYPE")) {
                    //Session has Expired,redirect to login page
                       window.location.href  ="/rms/login";
                } else {
                	if(xhr.status!=200){
                 		showError(((JSON.parse(xhr.responseText))['errMsg'])); 
                 	 	
                 	}else
                 		{
                 		showError(((JSON.parse(xhr.responseText))['errMsg'])); 
                 		}
                 	oTable.fnClearTable();
                    }}
     });
	}
 
	function updateEmployee() {
 
		var id = document.getElementById('updatedEmpId').value;
 
		var employeeData = [];
		var employees = getEmployee(id);
		var name = document.getElementById("employeeName").value;
		var emailId = document.getElementById("emailId").value;
		var locId = document.getElementById("locId").value;
		var IRMNameId = document.getElementById("IRMNameId").value;
		var SRMNameId = document.getElementById("SRMNameId").value;
		var businessGroup = document.getElementById("bgId").value;
		var businessUnit = document.getElementById("buId").value;
		
	/* 	var BUHNameId = document.getElementById("BUHNameId").value;
		var BGHNameId = document.getElementById("BGHNameId").value;
		var HRBPNameId = document.getElementById("HRBPNameId").value;
		var genderId = document.getElementById("genderId").value;
		var designId = document.getElementById("designId").value;
		var gradeId = document.getElementById("gradeId").value;
		var emptypeId = document.getElementById("emptypeId").value;
		var dateOfJoining = $('#dateofjoining').val(); */
 
		employeeData.push({
			'id' : employees.eId,
			'name' : name,
			'employeeId' : employees.employeeId,
			
			'baseLocation' : locId,
 
			'irmEmployeeId' : employees.irmEmployeeId,
			'srmEmployeeId' : employees.srmEmployeeId,
			'irmName' : IRMNameId,
			'srmName' : SRMNameId,
			
			'businessGroup':businessGroup,
			'businessUnit' :businessUnit,
			
			
		/* 	'emailId' : emailId,
			'buhEmployeeId' : employees.buhEmployeeId,
			'bghEmployeeId' : employees.bghEmployeeId,
			'hrbpEmployeeId' : employees.hrbpEmployeeId,
 
			
			'buhName' : BUHNameId,
			'bghName' : BGHNameId,
			'hrbpName' : HRBPNameId,
 
			'gender' : genderId,
			'designation' : designId,
			'grade' : gradeId,
			'businessGroup' : businessGroup,
			'emptypeId' : emptypeId,
			
			'dateOfJoining' : dateOfJoining */
			
 
		});
 
		
		//$.post("saveeditedinfogramactiveresource",employeeData, function(data, status){});
 
		$.ajax({
			type : 'POST',
			url : 'saveeditedinfogramactiveresource',
			contentType : 'application/json',
			dataType : 'json',
			async : false,
			processData : false,
			data : JSON.stringify(employeeData),
			success : function(response) {
 
				showSuccess("Employeee updated in RMS successfully !");
				setTimeout(function() {
					location.reload();
				}, 1000);
 
			},
			error : function(data) {
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
</script><div class="content-wrapper"><!--right section--><div class="botMargin">
<h1>Infogram Active Employees Details</h1></div>
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
  <div  class="emp-status">
    <span>&nbsp; Status : &nbsp;&nbsp;</span>
    <select id="listActiveOrAll">
   	 <option value="All" selected="selected">All</option>
      <option value="Existing">Existing</option>
      <option value="New">New Joinee</option>
    </select>
    <!-- <input type="Submit" value="Search"  class="projectSearch"></input> -->
  </div>
  <!-- All option will contain pending and failure process status -->
  <div  class="emp-processStatus">
    <span>&nbsp; Process Status : &nbsp;&nbsp;</span>
    <select id="listProcessStatus">
   	 <option value="All" selected="selected">All</option>
      <option value="DISCARD">Discard</option>
      <option value="SUCCESS">Success</option>
      <option value="Pending">Pending</option>
      <option value="FAILURE">Failure</option>
    </select>
   <!--  <a href="infogramActiveResourceReport" class="blue_link" id="exportToExcel"><i class="fa fa-file-excel-o" style="font-size: 15px"></i> Export To Excel </a> -->
  </div>
   <div  class="rec-status">
    <span>&nbsp; Record Status : &nbsp;&nbsp;</span>
    <select id="listRecordStatus">
   	 <option value="All" selected="selected">All</option>
      <option value="AllOk">All oK</option>
    </select>
  <a href="infogramActiveResourceReport" class="blue_link" id="exportToExcel"><i class="fa fa-file-excel-o" style="font-size: 15px"></i> Export To Excel </a>
  </div>
  <div class="tbl">
    <table class="dataTbl display tablesorter addNewRow alignCenter" id="info_table"> 
    <!-- <table class="dataTbl display tablesorter addNewRow alignCenter" id="test_table"> -->
      <thead>
        <tr>
          <th align="center" valign="middle">Action</th>
          <th align="center" valign="middle">EMP Id</th>
          <th align="center" valign="middle">Emp Name</th>
          <th align="center" valign="middle">Employee Status</th>
           <th align="center" valign="middle">Process Status</th> 
          <th align="center" valign="middle">DOJ</th>
          <!-- <th align="center" valign="middle">Emp Type</th> -->
          <!-- <th align="center" valign="middle">Emp Category</th> -->
          <!-- <th align="center" valign="middle">Grade</th> -->
           <th align="center" valign="middle">EmailId</th>
          <th align="center" valign="middle">Designation</th>
           <th align="center" class="rmsheadercolor" valign="middle">RMS Designation</th>
          <!-- <th align="center" valign="middle">Gender</th> -->
        
          <th align="center" valign="middle">Base Location</th>
           <th align="center" class="rmsheadercolor" valign="middle">RMS Base Location</th>
            <th align="center" valign="middle">Current Location</th>
           <th align="center" class="rmsheadercolor" valign="middle">RMS Current Location</th>
           <th align="center" valign="middle">BG</th>
           <th align="center" valign="middle">BU</th>
           <th align="center" class="rmsheadercolor" valign="middle">RMS BG</th>
           <th align="center" class="rmsheadercolor"  valign="middle">RMS BU</th>
          <th align="center" valign="middle">IRM Name</th>
           <th align="center" class="rmsheadercolor" valign="middle">RMS IRM Name</th>
          <th align="center" valign="middle">SRM Name</th>
           <th align="center" class="rmsheadercolor" valign="middle">RMS SRM Name</th>
           <th align="center" valign="middle">Created Time</th>
           <th align="center" valign="middle">Modified By</th>
          <!-- <th align="center" valign="middle">BUH Name</th>
          <th align="center" valign="middle">BGH Name</th>
          <th align="center" valign="middle">HRBP Name</th> -->
          <!-- <th align="center" valign="middle">Resigned Date</th>   -->
        </tr>
		        <tr class="">
					  		<td><input type="text" name="search_id"
								placeholder="Emp 123" class="search_init" disabled="disabled" style="display: none"/></td>
							<td><input type="text" name="search_empID"
								placeholder="Emp ID" class="search_init" /></td>
							<td><input type="text" name="search_empName"
								placeholder="Emp Name" class="search_init" /></td>
							<td><input type="text" name="search_emp status"
								placeholder="Employee Status" class="search_init" disabled="disabled"/></td>
							<td><input type="text" name="search_grade"
								value="Process Status" class="search_init" disabled="disabled"/></td>
							<td> <input type="text" onfocus="this.value=''" name="search_DOJ" placeholder="DOJ"
								class="search_init" id="doj" readonly="readonly"/> </td> 
								 <td><input type="text" name="search_EmailId" placeholder="Email Id"
								class="search_init" /></td> 
							<td> <input type="text" name="search_Designation" placeholder="Designation"
								class="search_init" /></td> 
								
								<td> <input type="text" name="search_RMSDesignation" placeholder="RMS Designation"
								class="search_init" disabled="disabled"/></td> 
							
							<td><input type="text" name="search_location" placeholder="Location"
								class="search_init"/></td>
							<td><input type="text" name="search_rmsLocation"
								placeholder="RMS Location" class="search_init" disabled="disabled"/></td>
								
							<td><input type="text" name="search_curlocation" placeholder="Current Location"
								class="search_init"/></td>
							<td><input type="text" name="search_rmsCurLocation"
								placeholder="RMS Current Location" class="search_init" disabled="disabled"/></td>	
							
							<td><input type="text" name="search_bg" placeholder="BG"
								class="search_init" /></td> 
							<td><input type="text" name="search_bu" placeholder="BU"
								class="search_init" /></td>
							<td><input type="text" name="search_RMSBG" placeholder="RMS BG"
								class="search_init" disabled="disabled"/></td>
								<td><input type="text" name="search_RMSBU"
								placeholder="RMS BU" class="search_init"disabled="disabled"/></td>
								<td><input type="text" name="search_irmName"
								placeholder="IRM Name" class="search_init" /></td>
 
 
 
							<td><input type="text" name="search_" placeholder="RMS IRM Name"
								class="search_init" disabled="disabled"/></td>
 
							<td><input type="text" name="search_srmName"
								placeholder="SRM Name" class="search_init" /></td>
							<td><input type="text" name="search_rmssrmName" placeholder="RMS SRM Name"
								class="search_init" disabled="disabled"/></td> 
								
							<td> <input type="text" onfocus="this.value=''" name="search_createdTime" placeholder="Created Time"
								class="search_init" id="createdTime" readonly="readonly"/> </td>  
												  
							<td><input type="text" name="search_modifiedby" placeholder="Modified By"
								class="search_init"/></td>
						
							
 
						</tr> 
      </thead>
<%--       <tbody>
        <c:forEach var="info" varStatus="infoState"	items="${infogramList}">
          <tr>
            <td class="stop_click action-dropdown dropbtn dropdowns">
              <div class="dropdown dropleft dropDownHover" id="dropDownOpen">
                <i class="fa fa-ellipsis-v" style="font-size: 15px;" class="btn dropdown-toggle"></i>
                <div class="dropdown-menu dropdown-content">
                <div>
                    <c:choose>
                    <c:when test="${info.resourceType=='New'}">
                    <div class="borderHover"><a class="dropdown-item" onClick="moveEmployee('${info.id}')">
                        <i	class="fa fa-user-plus my_submit_btn"></i>Move Employee</a>
                    </div>
                  </c:when>
                  <c:when test="${info.resourceType=='Old'}">
                  <div class="borderHover"><a class="dropdown-item" onClick="moveEmployee('${info.id}')">
                      <i class="fa fa-user-plus my_submit_btn"></i>Move Employee</a>
                  </div> 
                  <div class="borderHover">
                      <a class="dropdown-item" data-toggle="modal"	data-target="#employeeModal" onClick="editEmployee('${info.id}')">
                        <i class="fa fa-pencil-square-o my_submit_btn"></i>Edit Employee</a>
                  </div> 
                 </c:when>
            	 </c:choose>
          	  </div>
	          <div class="borderHover">
	            <a class="dropdown-item" onClick="discardEmployee('${info.id}')">
	              <i class="fa fa-times-circle-o my_submit_btn"></i>Discard	Employee</a>
	         </div>
	    	</div>
  			</div>
            </td>
            <td align="center" valign="middle">${info.employeeId}</td>
            <td align="center" valign="middle">${info.name}</td>
                        
            <c:choose>
              <c:when test="${info.resourceType=='New'}">
                <td   align="center" valign="middle" class="newJoinee">New Joinee</td>
              </c:when>
              <c:when test="${info.resourceType=='Old'}">
                <td   align="center" valign="middle" class="existing">Existing</td>
              </c:when>
            </c:choose>
            <td id="emp${info.id}" align="center" valign="middle">${info.processStatus}</td>
            <td align="center" valign="middle">
              <fmt:formatDate type="date" pattern="dd-MMM-yyyy" value="${info.dateOfJoining}" />
            </td> 
            <td align="center" valign="middle">${info.employeeType}</td> --%><%-- 
            <td align="center" valign="middle">${info.employeeCategory}</td> --%><%-- 
            <td align="center" valign="middle">${info.grade}</td>
            <td align="center" valign="middle">${info.designation}</td>
            <td align="center" valign="middle">${info.gender}</td>
            <td align="center" valign="middle">${info.emailId}</td>
            <td align="center" valign="middle">${info.baseLocation}</td>
             <td align="center" valign="middle">${info.locationInRMS.location}</td>
            <td align="center" valign="middle">${info.businessUnit}</td>
            <td align="center" valign="middle">${info.buIdInRMS.name}</td>
            <td align="center" valign="middle">${info.businessGroup}</td>
             <td align="center" valign="middle">${info.buIdInRMS.parentId.name}</td>
            <td align="center" valign="middle">${info.irmName}</td>
            <td align="center" valign="middle">${info.irmInRMS.firstName} ${info.irmInRMS.middleName} ${info.irmInRMS.lastName}</td>
            <td align="center" valign="middle">${info.srmName}</td>
            <td align="center" valign="middle">${info.srmInRMS.firstName} ${info.srmInRMS.middleName} ${info.srmInRMS.lastName}</td>
             <td align="center" valign="middle">${info.buhName}</td>
            <td align="center" valign="middle">${info.bghName}</td>
            <td align="center" valign="middle">${info.hrbpName}</td>
            </tr>
            </c:forEach>
            </tbody> --%>
            </table>
            </div>
  <div class="container">
  
  <!-- Trigger the modal with a button -->
   <!-- Modal -->
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
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  
</div>     
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
                <label for="requestorGradeSelect"><span	class="required">*</span>Emp Id </label>
                <input id="employeeId" type="text" value="" class="form-control" disabled>
              </div>
                <div class="form-group col-md-3 autoSelect">
                  <label for="requestorGradeSelect"><span class="required">*</span>Emp Name </label>
                  <input id="employeeName" type="text" value=""	class="form-control" disabled>
                </div>
                  <!--  <div class="form-group col-md-3 validateMe"><label for="bgBuSelect"><span class="required">*</span>Status</label><div class="positionRel"><select class="form-control required comboselect check empdetail-select"><option value="Active">Active</option><option value="In-Active">In-Active</option></select></div></div> -->
                  <div class="form-group col-md-3 validateMe datepickeremp">
                    <label for="inputPassword4"><span class="required">*</span>Date of Joining</label>
                    <div class="positionRel">
                      <div class='input-group'><input type='text' value="" id="dateofjoining" autocomplete="off" class="form-control" disabled />
                        <label class="input-group-addon" for="requestedDate1">
                          <span class="glyphicon glyphicon-calendar"></span>
                        </label>
                      </div>
                    </div>
                  </div>
                  
                    <div class="form-group col-md-3 autoSelect">
                    <label for="requestorGradeSelect"><span	class="required">*</span>Business Unit </label>
                    <input id="buId" type="text" value="" class="form-control">
                    </div>
            </div>
                  <!-- <div class="form-row col-sm-12"> -->
            <div class="form-row col-sm-12 employee-edit-detail">
                    <!--    <div class="form-group col-md-3 validateMe"><label for="bgBuSelect"><span class="required">*</span>Emp Type</label><div class="positionRel"><select class="form-control required comboselect check empdetail-select"><option value="Permanent">Permanent</option><option value="Contract">Contract</option></select></div></div> -->
                    <div class="form-group col-md-3 autoSelect"><label for="requestorGradeSelect">
                        <span class="required">*</span>Business Group </label>
                        <input id="bgId" type="text" value="" class="form-control">
                    </div>
                      <!--   <div class="form-group col-md-3 validateMe"><label for="bgBuSelect"><span class="required">*</span>Emp Category</label><div class="positionRel"><select class="form-control required comboselect check"><option value="Confirmed">Confirmed</option><option value="Probation">Probation</option></select></div></div> -->
                    <div class="form-group col-md-3 autoSelect"> <label for="requestorGradeSelect">
                          <span class="required">*</span>Emp Type </label>
                        <input id="emptypeId" type="text" value="" class="form-control" disabled>
                    </div>
                        <!--   <div class="form-group col-md-3 validateMe"><label for="bgBuSelect"><span class="required">*</span>Grade</label><div class="positionRel"><select class="form-control required comboselect check"><option value="E1">E1</option><option value="E2">E2</option></select></div></div> -->
                    <div class="form-group col-md-3 autoSelect"><label for="requestorGradeSelect">
                            <span class="required">*</span>Grade</label>
                          <input id="gradeId" type="text" value="" class="form-control" disabled>
                    </div>
                    <!--   <div class="form-group col-md-3 validateMe"><label for="bgBuSelect"><span class="required">*</span>Designation</label><div class="positionRel"><select class="form-control required comboselect check"><option value="Software Engineer">Software Engineer</option><option value="Manager">Manager</option></select></div></div> -->
                     <div class="form-group col-md-3 autoSelect"> <label for="requestorGradeSelect">
                              <span class="required">*</span>Designation</label>
                            <input id="designId" type="text" value="" class="form-control" disabled>
                     </div>
                     </div>
                      <div class="form-row col-sm-12 employee-edit-detail">
                      <!--   <div class="form-group col-md-3 validateMe"><label for="bgBuSelect"><span class="required">*</span>Gender</label><div class="positionRel"><select class="form-control required comboselect check"><option value="Male">Male</option><option value="Female">Female</option></select></div></div> -->
                      <div class="form-group col-md-3 autoSelect"> <label for="requestorGradeSelect">
                                <span class="required">*</span>Gender</label>
                                <input id="genderId" type="text" value="" class="form-control" disabled>
                      </div>
                              <div class="form-group col-md-3 autoSelect"><label for="requestorGradeSelect">
                                  <span class="required">*</span>Email Id </label>
                                <input id="emailId"	type="text" value="" class="form-control" disabled>
                               </div>
                                <div class="form-group col-md-3 autoSelect"><label for="requestorGradeSelect">
                                    <span class="required">*</span>Location </label>
                                  <input id="locId" type="text" value="" class="form-control">
                                 </div>
                                  <div class="form-group col-md-3 autoSelect"><label for="requestorGradeSelect">
                                      <span class="required">*</span>IRM Name</label>
                                    <input id="IRMNameId" type="text" value="" class="form-control">
                                  </div>
                                  </div>
                                <div class="form-row col-sm-12 employee-edit-detail">
                                    <div class="form-group col-md-3 autoSelect"><label for="requestorGradeSelect">
                                        <span class="required">*</span>SRM Name</label>
                                      <input id="SRMNameId" type="text" value="" class="form-control">
										 <input name="updatedEmpId" id="updatedEmpId" type="hidden" />
                                      </div>
                                      <!-- <div class="form-group col-md-3 autoSelect"> <label for="requestorGradeSelect">
                                          <span class="required">*</span>BUH Name </label>
                                        <input id="BUHNameId" type="text" value="" class="form-control">
                                        </div>
                                        <div class="form-group col-md-3 autoSelect"> <label for="requestorGradeSelect">
                                            <span class="required">*</span>BGH Name
                                          </label>
                                          <input id="BGHNameId" type="text" value="" class="form-control">
                                          </div>
                                          <div class="form-group col-md-3 autoSelect"><label for="requestorGradeSelect">
                                              <span class="required">*</span>HRBP Name
                                            </label>
                                            <input id="HRBPNameId" type="text" value="" class="form-control">
                                              <input name="updatedEmpId" id="updatedEmpId" type="hidden" />
                                            </div> -->
                                          </div>
                                        <!--   <div class="form-row col-sm-12 employee-edit-detail ">
                                            <div class="form-group col-md-3 validateMe datepickeremp">
                                              <label for="inputPassword4">
                                                <span class="required">*</span>Resigned Date
                                              </label>
                                              <div class="positionRel">
                                                <div class='input-group'>
                                                  <input type='text' value="" id="resignationdate"	autocomplete="off" class="form-control" />
                                                  <label class="input-group-addon" for="requestedDate1">
                                                    <span class="glyphicon glyphicon-calendar"></span>
                                                  </label>
                                                </div>
                                              </div>
                                            </div>
                                          </div> -->
                                        </div>
                                        <div class="modal-footer">
                                          <button type="button" data-dismiss="modal" class="btn">Cancel</button>
                                          <button id="updateBtnId" type="button"
															data-dismiss="modal" onClick="updateEmployee()"
															class="btn btn-primary">Update</button>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
								    </div>
			<div>Note : 1) All employee is appearing with Pending and Failure Status by default.</br>
						   2) Click on process Status as Failure to see Failure reason.</div>
              <div class="clear"></div>                  
                 
  <div class="clear"></div>
</div></div><!--right section--></div><div class="clear"></div>