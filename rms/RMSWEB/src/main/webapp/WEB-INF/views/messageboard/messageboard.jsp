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
	value="/resources/js/rmsUIValidation.js"
	var="rmsUIValidation_js" />
<script src="${rmsUIValidation_js}" type="text/javascript"></script>
 

<style type="text/css" title="currentStyle">

#messages_table td{
    white-space: initial;
    text-align: left;
        word-break: break-word;
   }
#messages_table {
	overflow-x: scroll;
	overflow-y : scroll;
    max-width: 100%;
    white-space: nowrap;
    cursor: pointer;
    min-height: 150px;
	max-height:370px;
	margin-top:25px;
}

#messages_table tbody tr:nth-child(10) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}

#messages_table tbody tr:nth-child(9) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}

#messages_table tbody tr:nth-child(8) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important;
}
#messages_table tbody tr:nth-child(25) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important;
}

#messages_table tbody tr:nth-child(24) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important;
}

#messages_table tbody tr:nth-child(23) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important;
}

#messages_table tbody tr:nth-child(22) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important;
}
#messages_table tbody tr:nth-child(49) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important;
}

#messages_table tbody tr:nth-child(50) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important;
}
#messages_table tbody tr:nth-child(99) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important;
	
}

#messages_table tbody tr:nth-child(100) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}

#messages_table .hover-area { 
    width: 20px;
    display: inline-block;
    text-align: center;
}
#messages_table tr {
    height: 34px;
}

.msg-messageStatus{
	font-weight: bold;
	position: absolute;
	left: 330px;
	z-index: 9;
	top: 20px;
}
.dataTables_length  {
top: 10px;
}
#messages_table  .dropdown-menu.dropdown-content {
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

i.fa.fa-ellipsis-v :hover {
	background: #fff;
	color: #fff;
}
.dropdown:hover .dropdown-content {
	display: block;
}
 
.dropdown:hover .dropbtn {
	background-color: #3e8e41;
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
.search_filterLeft {
width: inherit !important;
}

i.fa.fa-edit {
margin: 0px 0px 0px 0px;
}
i.fa.fa-trash {
margin: 0px 0px 0px 0px;
}
#messageBoardMain .close {
    position: relative;
    right: 0px;
    top: 5px;
    width: 29px;
    height: 16px;
    opacity: 0.3;
    z-index: 5;
   }
   
#messageBoardMain .close:hover {
	 opacity: .5;
	background: #fff;
	color: #000;
}   
 

 
</style>

<script type="text/javascript" charset="utf-8">

var oTable="";
var allmessageStatusVar = '';
const NEW_STATUS ="NEW";
const REJECTED_STATUS ="REJECTED";
const APPROVED_STATUS ="APPROVED";

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
	
	/*Fetch values */
	allmessageStatusVar = $('#messageStatus').val();
	if(allmessageStatusVar=='undefined' || allmessageStatusVar==''){
		allmessageStatusVar ='All';
	}
	
	/*populate datatable*/
	 oTable = $('#messages_table').dataTable( {
		 		"bProcessing": true,
	        	"bServerSide": true,
	        	"sPaginationType" : "full_numbers", 
	        	"bSortCellsTop": true,
	        	"sDom":"ltipr",
	        	"sAjaxSource": 'getallmessagelist/'+allmessageStatusVar,
	        	"aoColumns": [
	        				{mData:'id',mRender:function ( id, type, row ){
	                		 	var messageid = ""+id;	 
	                		 	return prepareAction(messageid,row.messageStatus);
	        	      		},"bSortable": false},
	        	      		{mData:'text',sDefaultContent: "NA" },
	        	      		{mData:'messageStatus',sDefaultContent: "NA" },
	        	      		{mData:'modifiedName', sDefaultContent: "NA" },
	        	      		{mData:'createdName', sDefaultContent: "NA" }
	        	]
	 });
	
	 $("thead input").keyup( function(i){
	    	if(i.which==13||(i.which==8 && this.value.trim().length==0)){
		    	oTable.fnFilter( this.value.trim(), oTable.oApi._fnVisibleToColumnIndex(oTable.fnSettings(), $("thead input").index(this) ) );
	    	}
	    });
	 $('#addNew').click(function (e) {		 
		 var modal = document.getElementById('addModal');
		 modal.style.display = "block";
		 document.getElementById("add-btn").disabled = false;
		 
		});
	 $('#addMessageText').bind("keypress", function (event) {
			var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
			if(regExForMsgTxtFailed(key)){
			        event.preventDefault();
			        return false;
			    }
		 });
	 $('#updatedMessageText').bind("keypress", function (event) {
		 var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
		 if(regExForMsgTxtFailed(key)){
		        event.preventDefault();
		        return false;
		    }
	 });
});
	/* End of Ready function */
	
	$(document.body).on(
			'change',
			'#messageStatus',
			function() {
				allmessageStatusVar = this.value;
				var oSettings = oTable.fnSettings();
				oSettings.sAjaxSource = "getallmessagelist/"
						+ allmessageStatusVar;
				oTable.fnDraw();
			});
	function showActionDropdown(ele) {
	
		/* $('#messages_table .dropdown-content').classList.toggle("show");
		$(ele).children().next('.dropdown-content').classList.toggle("hide"); */
		$('#messages_table .dropdown-content').css('display', 'none');
		$(ele).children().next('.dropdown-content').css('display', 'block');
	}
	
	$(document).on('click', function(e) {
		if (!event) { var event = window.event; }
		event.stopPropagation();
		var src = event.srcElement ? event.srcElement : event.target;
		if(($(src).hasClass('dropdown-content')==false) && $(src).hasClass('dropDownHover')==false && $(src).hasClass('fa-ellipsis-v')==false)
		{ 
			$('#messages_table .dropdown-content').hide();
		}
	});

	function prepareAction(messageid,messageStatus) {

		var dtData = "";
		dtData = '<div class="stop_click action-dropdown dropbtn dropdowns">';
		dtData = '<span class="dropdown dropleft dropDownHover  hover-area" onclick="showActionDropdown(this)">  <i class="fa fa-ellipsis-v" style="font-size: 15px;"  class="btn dropdown-toggle"></i>';
		dtData = dtData + '<div class="dropdown-menu dropdown-content">';
		//if(messageStatus==NEW_STATUS){
		dtData = dtData
				+ '<div class="borderHover"><a class="dropdown-item" data-toggle="modal" data-target="#editMessage" onClick="updateMessage('
				+ messageid
				+ ')"><i class="fa fa-edit my_submit_btn"></i>Update</a></div>';
		//}
		dtData = dtData
		+ '<div class="borderHover"><a class="dropdown-item" title="Delete Message" onClick="deleteMessage('
		+ messageid
		+ ')"><i class="fa fa-trash my_submit_btn"></i>Delete</a></div>';
		if(messageStatus==NEW_STATUS || messageStatus==REJECTED_STATUS){
		dtData = dtData
				+ '<div class="borderHover"><a class="dropdown-item" onClick="approveMessage('
				+ messageid
				+ ')"><i class="fa fa-check-circle my_submit_btn"></i>Approve</a></div>';
		}
		if(messageStatus==APPROVED_STATUS || messageStatus==NEW_STATUS){
		dtData = dtData
				+ '<div class="borderHover"><a class="dropdown-item" onClick="rejectMessage('
				+ messageid
				+ ')"><i class="fa fa-times-circle-o my_submit_btn"></i>Reject</a></div>';
		}
		
		
		dtData = dtData + '</div>'+ '</span>' + '</div>';
		return dtData;

	}
	function showMessage(data){
		 var text = data.messageBoardObjText;
		 if(text!=null && text!='undefined'){
			 document.getElementById('updatedMessageText').value = text;
		 }
		 else{
			 showError("Error in Message retrieval");
		 }
	 }
	function updateMessage(id){
		console.log("id ", id);
		document.getElementById('msgId').value=id;
		$.getJSON("getMessage/"+id, showMessage);
		var modal = document.getElementById('updateModal');
		modal.style.display = "block";
		document.getElementById("update-btn").disabled = false;
	}
	function onUpdateMessage(id){
		startProgress();
		var messageText =  document.getElementById('updatedMessageText').value;
		var messageId=document.getElementById('msgId').value;
		messageText = messageText.trim();
		if(messageText.length == 0){
			showError("Please enter message!");
			stopProgress();
			return false;
		}
		if(regExForMsgTxtFailed(messageText)){
			showError("Please enter alphanumeric characters (a-zA-Z0-9-_!@#$%^&*()~ and space)!");
			stopProgress();
			return false;
		}
		if(messageText.length > 500){
			showError("Your message exceeds 500 characters!");
			stopProgress();
			return false;
		}
		
		document.getElementById("update-btn").disabled = true;
		$("#updatedMessageText").val('');		 
		var modal = document.getElementById('updateModal');
		modal.style.display = "none";
		
		$.ajax({
			type : 'POST',			
			url : 'saveEditedMessage',
			data: {"id" : messageId, "text" :  messageText},						
			success : function(response) {
				if (null != response) {
					showSuccess("Message updated Successfully!!");
				} else {
					showError("Something happend wrong!! ");
				}
				stopProgress();
				oTable.fnClearTable();
				
			},
			error : function(xhr, status, thrownError) {
				stopProgress();
				if (xhr.responseText.startsWith("<!DOCTYPE")) {
					//Session has Expired,redirect to login page
					window.location.href = "/rms/login";
				} else {
					if (xhr.status != 200) {
						showError(((JSON.parse(xhr.responseText))['errMsg']));

					} else {
						showError(((JSON.parse(xhr.responseText))['errMsg']));
					}
					oTable.fnClearTable();
				}
			}
		});		
	}

	
	function approveMessage(id) {
		startProgress();

		$.ajax({
			type : 'POST',
			url : 'approveMessage',
			data : {
				"id" : id
			},
			success : function(response) {
				if (null != response) {
					showSuccess("Message Approved Successfully!");
				} else {
					showError("Something happend wrong!! ");
				}
				stopProgress();
				//window.location.reload();
				oTable.fnClearTable();
			},

			error : function(xhr, status, thrownError) {
				stopProgress();
				if (xhr.responseText.startsWith("<!DOCTYPE")) {
					//Session has Expired,redirect to login page
					window.location.href = "/rms/login";
				} else {
					if (xhr.status != 200) {
						showError(((JSON.parse(xhr.responseText))['errMsg']));

					} else {
						showError(((JSON.parse(xhr.responseText))['errMsg']));
					}
					oTable.fnClearTable();
				}
			}
		});
	}

	function rejectMessage(id) {

		startProgress();
		$.ajax({
			type : 'POST',
			url : 'rejectMessage',
			data : {
				"id" : id
			},
			success : function(response) {
				if (null != response) {
					showSuccess("Message Rejected Successfully!");
				} else {
					showError("Something happend wrong!! ");
				}
				stopProgress();
				//window.location.reload();
				oTable.fnClearTable();
			},

			error : function(xhr, status, thrownError) {
				stopProgress();
				if (xhr.responseText.startsWith("<!DOCTYPE")) {
					//Session has Expired,redirect to login page
					window.location.href = "/rms/login";
				} else {
					if (xhr.status != 200) {
						showError(((JSON.parse(xhr.responseText))['errMsg']));

					} else {
						showError(((JSON.parse(xhr.responseText))['errMsg']));
					}
					oTable.fnClearTable();
				}
			}
		});
	}

	function deleteMessage(id) {

		startProgress();

		$.ajax({

			type : 'POST',
			url : 'deleteMessage',
			data : {
				"id" : id
			},
			success : function(response) {
				if (null != response) {
					showSuccess("Message Deleted Successfully!");
				} else {
					showError("Something happend wrong!! ");
				}
				stopProgress();
				//window.location.reload();
				oTable.fnClearTable();
			},

			error : function(xhr, status, thrownError) {
				stopProgress();
				if (xhr.responseText.startsWith("<!DOCTYPE")) {
					//Session has Expired,redirect to login page
					window.location.href = "/rms/login";
				} else {
					if (xhr.status != 200) {
						showError(((JSON.parse(xhr.responseText))['errMsg']));

					} else {
						showError(((JSON.parse(xhr.responseText))['errMsg']));
					}
					oTable.fnClearTable();
				}
			}
		});
	}

	function closeUpdateMessagePopup() {
		$("#updatedMessageText").val('');
		document.getElementById("update-btn").disabled = false;
		var modal = document.getElementById('updateModal');
		modal.style.display = "none";
	}
	function closeAddMessagePopup() {
		$("#addMessageText").val('');
		document.getElementById("add-btn").disabled = false;
		var modal = document.getElementById('addModal');
		modal.style.display = "none";
	}

	function onAddMessage() {

		startProgress();
		var addMessageText = document.getElementById('addMessageText').value;
		addMessageText = addMessageText.trim();
		if (addMessageText.length == 0) {
			showError("Please enter message!");
			stopProgress();
			return false;
		}
		if (regExForMsgTxtFailed(addMessageText)) {
			showError("Please enter alphanumeric characters (a-zA-Z0-9-_!@#$%^&*()~ and space)!");
			stopProgress();
			return false;
		}
		if (addMessageText.length > 500) {
			showError("Your message exceeds 500 characters!");
			stopProgress();
			return false;
		}

		document.getElementById("add-btn").disabled = true;
		$("#addMessageText").val('');
		var modal = document.getElementById('addModal');
		modal.style.display = "none";

		$
				.ajax({
					type : 'POST',
					url : 'addMessage',
					data : {
						"text" : addMessageText
					},
					success : function(response) {
						if (null != response) {
							showSuccess("Your Message has been Successfully sent to Admin!");
						} else {
							showError("Something happend wrong!! ");
						}
						stopProgress();
						oTable.fnClearTable();
					},
					error : function(xhr, status, thrownError) {
						stopProgress();
						if (xhr.responseText.startsWith("<!DOCTYPE")) {
							//Session has Expired,redirect to login page
							window.location.href = "/rms/login";
						} else {
							if (xhr.status != 200) {
								showError(((JSON.parse(xhr.responseText))['errMsg']));

							} else {
								showError(((JSON.parse(xhr.responseText))['errMsg']));
							}
							oTable.fnClearTable();
						}
					}
				});
	}
</script>

<div class="content-wrapper" id="messageBoardMain">
	<div class="botMargin">
		<h1>Message Board</h1>
	</div>
	
	<div id='tab2' class="tab_div clearfix">
			<div class="search_filter_outer">
				<div class="search_filter search_filterLeft">
					<div class="btnIcon1 newMsgBtnIcon">
						<span> <a href="javascript:void(0);"
							class="blue_link blueBtnYash" id="addNew"> + Add New Message</a>
						</span>
					</div>
				</div>
			</div>
			<!-- Message Status -->
			<div class="msg-messageStatus">
				<span>&nbsp; Message Status : &nbsp;&nbsp;</span> <select
					id="messageStatus">
					<option value="All" selected="selected">All</option>
					<option value="REJECTED">Rejected</option>
					<option value="APPROVED">Approved</option>
					<option value="NEW">New</option>
				</select>
			</div>
			<!-- Table Design Start -->
			<div class="tbl">
				<table class="dataTbl display tablesorter addNewRow alignCenter"
					id="messages_table">
					<thead>
						<tr>
							<th align="center" valign="middle">Action</th>
							<th align="center" valign="middle">Message Text</th>
							<th align="center" valign="middle">Message Status</th>
							<th align="center" valign="middle">Modified By</th>
							<th align="center" valign="middle">Created By</th>
						</tr>
						<tr class="">
							<td><input type="text" name="search_id"
								placeholder="Message 123" class="search_init" disabled="disabled"
								style="display: none" /></td>
							<td><input type="text" name="search_text"
								placeholder="Text" class="search_init" /></td>
							<td><input type="text" name="search_messageStatus"
								placeholder="Status" class="search_init"
								disabled="disabled" /></td>
							<td><input type="text" name="search_modifiedName"
								value="Modified By" class="search_init" disabled="disabled" /></td>							
							<td><input type="text" name="search_createdName"
								placeholder="Created By" class="search_init" disabled="disabled" /></td>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		

		
		<!-- start: update Popup  -->

                   <div id="updateModal" class="modal">
                          <div class="modal-content" style="width: 530px; margin-top: 40px;">
                                
                                <div class="modal-header">
                                                          <button type="button" class="close" data-dismiss="modal" onclick="closeUpdateMessagePopup()">&times;</button>
                                                          <h4 class="modal-title">Updated Message</h4>
                                </div>
                                
                                <div class="modal-body">
                                                          <div class="form-group">
                                                                <label for="newMsg">Update Message:</label>
                                                                <textarea id="updatedMessageText" name="updatedMessageText" class="form-control" maxlength="500" style="resize: none; height: 120px;"></textarea>
                                                                <input name="msgId" id="msgId" type="hidden" />
                                                          </div>
                                                   </div>
                                                   <div class="modal-footer">
                                                   <button type="button" id="close-btn"
                                            class="btn btn-secondary next-button" style=""
                                             data-dismiss="modal" onclick="closeUpdateMessagePopup()">Close</button>
                                                          <button type="button" class="btn next-button" id="update-btn"
                                                                onclick="onUpdateMessage()">Submit</button>
                                                   </div>
                                
                          </div>
                   </div>
			
			<!-- start: Add Popup  -->

			<div id="addModal" class="modal my-message-modal-main"> 
				<div class="modal-content" style="width: 530px; margin-top: 40px;">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" onclick="closeAddMessagePopup()">&times;</button>
									<h4 class="modal-title">Add New Message</h4>
								</div>
								<div class="modal-body">
									<div class="form-group">
									<div class="bg-none" role="grid">
										<form id="addMessageForm" action="#">
										<label for="addMessageText">Message:</label>
										<textarea id="addMessageText" class="form-control" maxlength="500" style="resize: none; height: 120px;"></textarea>
										 
											<input name="addMsgId" id="addMsgId" type="hidden" />
									</form></div>
									</div>
								</div>
								<div class="modal-footer">
								<button type="button" id="close-btn"
							class="btn btn-secondary next-button" style=""
							data-dismiss="modal" onclick="closeAddMessagePopup()">Close</button>
									<button type="button" class="btn next-button" id="add-btn"
										onclick="onAddMessage()">Submit</button>
								</div>
							</div>
			</div>
</div>