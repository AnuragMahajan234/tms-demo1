
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
.modal-body {
       /*max-height: calc(90vh - 250px); */
       overflow-y: auto;
}
.pointer{
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
       width: 65%;
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

td table tbody tr td {
       border: none !important;
}
</style>
<script>
       function saveTable() {
              var option = $('#status').find('option:selected').text();
              $(this).attr('target', '_blank');
              window.location = "../rms/requestsReports/saveReport?option="+option;
       }
       function skillRequestReportPDF(obj,id) {
              $(this).attr('target', '_blank');
              window.location = "../rms/requestsReports/showPDFReport?reqId="+id;
       }
       //mu21686
       function editrequest(obj,id) {
              $(this).attr('target', '_blank');
              
              window.location = "../rms/requests?reqId="+id;
       }
       
       function fileDownload(id, downloadFileFlag){    
             $.ajax({                          
               url: 'downloadfiles',
              contentType: "application/json",
              async:false,
              data: {"id":id, "downloadFileFlag":downloadFileFlag},         
              success: function(response) {                                        
                     if(response==""){
                            
                            showAlert("\u2022 File not found for id " + id + " ! <br />");
                            
                     }else{                                                                
                         if(id !== '' && downloadFileFlag !== ''){
                                         window.location.href = 'downloadfiles?id='+id+'&downloadFileFlag='+downloadFileFlag+'';
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
       //mu21686
       var resourceAagaya ; 
    var getValidationid;
    var getValidationCount;
    var maxRecord;
    var existRecord;
       var remainingResources;
       var resourcesRequired; 
       var savedInternalResources=[];
       $(document).ready(function() {           
               var addResourceInternal = [];
              var skillId;
              var reuestId;
              var projId;
              var fullfillResLen;
            
              $('#status').on('change',  function() {
                                                                          var status=$("#status").val();
                                                                           var htmlVar = '';
                                                                           $.ajax({
                                                                                                type : 'GET',
                                                                                                data : "option="+ status,
                                                                                                url : 'requestsReports/findActiveOrAll',
                                                                                                cache : false,
                                                                                                success : function(data) {
                                                                                                       $("#resourcerequestTable").empty();
                                                                                                       htmlVar += '<tbody id="resourcerequestTableBody">'; 
                                                                                                       var trId=0;
                                                                                                       for (var i = 0; i < data.length; i++) {
                                                                                                              var obj = data[i];
                                                                                                              htmlVar += '<tr id="'+obj.requestRequisition.id+'" class="abc">';
                                                                                                              htmlVar += '<td ><a onclick="skillRequestReportPDF(this,'+obj.id+')">RRF</a></td>';
                                                                                                              htmlVar += '<td>';
                                                                                                              var resourceRequired='<table>';
                                                                                                              //for(var j=0;j<obj.skillRequest.length;j++){
                                                                                                                     resourceRequired += '<tr><td id="noofresources">'+obj.noOfResources+'</td></tr>';
                                                                                                                     resourceRequired +=  '<tr><input type="hidden"  id="noOfRes'+obj.id+'" value="'+obj.noOfResources+'" /></tr>';
                                                                                                                     //}
                                                                                                              resourceRequired += '</table>';
                                                                                                              htmlVar += resourceRequired+'</td>';
                                                                                                              htmlVar += '<td>';
                                                                                                              var addDeleteResource='<table>';
                                                                                                              addDeleteResource += ' <tr><td><a onclick="openPopUpModelWithAjax(this,'+obj.id+','+obj.noOfResources+','+obj.remaining+')"  data-target="#addResource" id="'+obj.id+'" href="javascript:void(0)" >Add/Delete<br>Resource</a>'
                                                                                                          +'<input type="hidden" id="resourceBlockedIntText'+obj.id+'" class="resourceBlockedIntText" name="resourceBlockedIntText" inp="resourceBlockedIntText" value="" />'
                                                                                                       +'<input type="hidden" id="resourceBlockedExtText'+obj.id+'" class="resourceBlockedExtText" name="resourceBlockedExtText" inp="resourceBlockedExtText" value="" /></td></tr>';
                                                                                               addDeleteResource+='</table>';
                                                                                               htmlVar += addDeleteResource+'</td>';
                                                                                               htmlVar += '<td>';
                                                                                                              var requestFullfilled='<table id="fulfilledResources">';
                                                                                                              //for(var j=0;j<obj.skillRequest.length;j++){
                                                                                                                     requestFullfilled += '<tr class="ffres"><td><input class="ff" id="fulfilledRes'+obj.id+'" name="fulfilledRes" type="hidden" value="'+obj.fulfilled+'"/></td></tr>';
                                                                                                                     requestFullfilled += '<tr><td>'+obj.fulfilled+'</td></tr>';
                                                                                                              //}
                                                                                                              requestFullfilled += '</table>';
                                                                                                              htmlVar += requestFullfilled+'</td>';
                                                                                                              
                                                                                                              htmlVar += '<td>';
                                                                                                              var needToFullFilled='<table>';
                                                                                                              //for(var j=0;j<obj.skillRequest.length;j++){
                                                                                                                     needToFullFilled += '<tr class="remResources"><td id="remainingRes">'+obj.remaining+' </td></tr>';
                                                                                                              //}
                                                                                                              needToFullFilled += '</table>';
                                                                                                              htmlVar += needToFullFilled+'</td>';
                                                                                                              htmlVar += '<td>'+obj.requestRequisition.date+'</td>';
                                                                                                              htmlVar += '<td id="firstTd">'+obj.requestRequisition.projectBU+'</td>';
                                                                                                              htmlVar += '<td>'+obj.requestRequisition.employeeId.userName+'</td>';
                                                                                                              htmlVar += '<td>'+obj.requestRequisition.project.projectName+'</td>';
                                                                                                              
                                                                                                              htmlVar += '<td>';
                                                                                                              var skill='<table id="skillRequestId">';
                                                                                                              //for(var j=0;j<obj.skillRequest.length;j++){
                                                                                                              skill +='<tr class="skillReqId"><td><input type="hidden" value="'+obj.id+'">'+obj.skill.skill+'</td></tr>';
                                                                                                              //}
                                                                                                              skill += '</table>';
                                                                                                              htmlVar += skill+'</td>';
                                                                                                              
                                                                                                              htmlVar += '<td>';
                                                                                                              var designaiton='<table>';
                                                                                                              //for(var j=0;j<obj.skillRequest.length;j++){
                                                                                                                     designaiton += '<tr><td>'+obj.designation.designationName+'</td></tr>';
                                                                                                              //}
                                                                                                              designaiton += '</table>';
                                                                                                              htmlVar += designaiton+'</td>';
                                                                                                              
                                                                                                              htmlVar += '<td>';
                                                                                                              var experience='<table>';
                                                                                                              //for(var j=0;j<obj.skillRequest.length;j++){
                                                                                                                     experience += '<tr><td><span>'+obj.experience+'</span></td></tr>';
                                                                                                              //}
                                                                                                              experience += '</table>';
                                                                                                              htmlVar += experience+'</td>';
                                                                                                              
                                                                                                              htmlVar += '<td>';
                                                                                                              var billable='<table>';
                                                                                                              //for(var j=0;j<obj.skillRequest.length;j++){
                                                                                                                            billable += '<tr><td>'+obj.billable.allocationType+'</td></tr>';
                                                                                                              //}
                                                                                                              billable += '</table>';
                                                                                                              htmlVar += billable+'</td>';
                                                                                                              
                                                                                                              htmlVar += '<td>';
                                                                                                              var type='<table>';
                                                                                                              //for(var j=0;j<obj.skillRequest.length;j++){
                                                                                                                     if(obj.type==1){
                                                                                                                            type += '<tr><td>New Requirment</td></tr>';
                                                                                                                     }else{
                                                                                                                            type += '<tr><td>Replacement</td></tr>';
                                                                                                                     }
                                                                                                                     
                                                                                                              //}
                                                                                                              type += '</table>';
                                                                                                              htmlVar += type+'</td>';
                                                                                                              
                                                                                                              htmlVar += '<td>';
                                                                                                              var timeFrame='<table>';
                                                                                                              //for(var j=0;j<obj.skillRequest.length;j++){
                                                                                                                    timeFrame += '<tr><td id="timeFrame">'+obj.timeFrame+'</td></tr>';
                                                                                                              //}
                                                                                                              timeFrame += '</table>';
                                                                                                              htmlVar += timeFrame+'</td>';
                                                                                                              
                                                                                                              
                                                                                                              
                                                                                                              
                                                                                                              if(obj.requestRequisition.comments != null){
                                                                                                                     htmlVar += '<td>'+obj.requestRequisition.comments+'</td>';
                                                                                                              }else{
                                                                                                              htmlVar += '<td></td>';
                                                                                                              }
                                                                                                              if(obj.fulfilled <=0){
                                                                                                              htmlVar += '<td><a onclick="skillRequestReportDelete('+obj.requestRequisition.id+','+obj.id+')"><img alt="Delete" height="20px" width="20px" src="resources/images/delete.png"></a></td>';
                                                                                                              }
                                                                                                              else{
                                                                                                                     htmlVar += '<td></td>';
                                                                                                                     }
                                                                                                              htmlVar += '</tr>';
                                                                                                         trId++;
                                                                                                       }
                                                                                                       
                                                                                                       htmlVar += '</tbody>';
                                                                                                       $('#resourcerequestTable').append(htmlVar);
                                                                                                       $('#resourcerequestTable').DataTable({
                                                                                                              "bDestroy": true,
                                                                                                              "sPaginationType" : "full_numbers",
                                                                                                              /* "aaSorting": [[0, 'asc']], */
                                                                                                              "bAutoWidth": false,
                                                                                                              //"bSort": false
                                                                                                              
                                                                                                       });
                                                                                                },
                                                                                         });
                                                                           
                                                                     });

                                         var dtable= $('#resourcerequestTable').DataTable({
                                                "bDestroy": true,
                                                "sPaginationType" : "full_numbers",
                                                /* "aaSorting": [[0, 'asc']],    */    
                                                //"bSort": true,
                                                "bAutoWidth": false,
                                                // "sDom":'fptip'
                                         });
                                         
                                         
                                         
                                         $(function() {
                                                $('#resourceBlocked').multiselect({
                                                       includeSelectAllOption: true,
                                                       id:'resourceBlocked_id'
                                                }).multiselectfilter();
                                                
                                                $("span#resourceBlocked_id").next().on("click",function(){ 
                                                       //alert("keyInterviewersTwo_one") 
                                                       $(".resourceBlocked_id").val(''); 
                                                });
                                                
                                                
                                                $('#resourceBlocked').bind('change', function() { 
                                                       var selectedText;
                                                              
                                                              /*sarang added start*/
                                                              var selMulti = $.map($(this).find('option:selected'), function (el, i) {
                                                                     var res = $('#resourceBlocked').val();
                                                                     
                                                                    selectedText = $(el).text();
                                                                    
                                                                     return $(el).text();
                                                                     
                                                            });
                                                              if(selMulti==""){
                                                                     $('#InternalResourceText').html('');
                                                              }
                                                              //$("#InternalResourceText").text(selMulti.join(", "));
                                                              //$("#InternalResourceText").html(selMulti.join(", "));
                                                              $("#InternalResourceText").val(selMulti.join(", ")); 
                                                              
                                                              
                                                              
                                                              //$("#InternalResourceText").text(selMulti.join(", "));
                                                           /*sarang added ends*/
                                                            
                                                              //$('#InternalResourceText').val($(this).find('option:selected').text()); //sarang commented
                                                              addResourceInternal = $(this).val();
                                                      });
                                           $("span.ui-icon-closethick").next().on("click",function(){
                                                //$("#InternalResourceText").empty();
                                                $('#InternalResourceText').html('');
                                         });
                                                
                                         });
                                         
                                       //for add dynamic external resource 
                                     
                                         var wrapper         = $(".external"); //Fields wrapper
                                      var add_button      = $(".add_field_button"); //Add button ID
                                      var values = [];
                                         var  x = 0; //initlal text box count
                                      
                                     $(add_button).click(function(e){ /*  alert("X=="+x); */
                                         //alert($("#noOfRes"+id).val()+"===test ");
                                                //alert("skillId==="+window.skillId);
                                         var max_fields      = $("#noOfRes"+window.skillId).val(); //maximum input boxes allowed
                                                e.preventDefault();
                                                if(x < max_fields){ //max input box allowed
                                                       x++; //text box increment
                                              $(wrapper).append('<div class="inputText"><div class=u"sub-row"><input class=" addExternalId required"  type="text" id="externalButtonAdd" name="externalButtonAdd[]"/><a href="#" class="remove_field">Remove</a></div></div>'); //add input box
                                                }
                                      });
                                     
                                      
                                  $(wrapper).on("click",".remove_field", function(e){ //user click on remove text
                                          e.preventDefault();
                                          $(this).parent('div').remove(); 
                                          x--;
                                      });
                                
                                   // for reset model
                                    
                                  $('#addResource').on('hidden.bs.modal', function(){
                                 // $(this).find('form')[0].reset();
                            });  
                                  
                                 var text;
                                 var InternalResourceText;
                                  
                                  //when click save button
                             
                           /*     -------------------------------------------------------------------------------------->>     */           
                           function clearFilter()
								{
									$('.filterText').val('');
									$('.content').show();
								}
                           
                           $('#filterByCustomer').on('change',  function(){
                        	   
                          		var rex = new RegExp($('#filterByCustomer').val());
                        	   
                          		if(rex =="/all/"){
                          			clearFilter();
                          			}else{
	                          			$('.content').hide();
	                          			$('.content').filter(function() {
	                          				for(i = 0 ; i< customerListByRequestId.length ; i++){
	                          					
	                          				}
	                          			return email.test(VAL);
                          			}).show();
                          	}
                           });
	
							
							                           
                           
                           
                                  $('#updateResourceStatus').on('click', function() {
                                         
                                         if($("#InternalResourceText").val().length > 0 || $('input[name="externalButtonAdd[]"]').length > 0)
                                         {
                                                if(!confirm("Do you want to continue without save resource"))
                                                {
                                                  $("#saveResource").css("border-color","#b42020");
                                                   return false;    
                                                }
                                                
                                         }
                                         if(confirm("Sure you want to update Resource status? There is NO undo!"))
                                            {
                                                var requirements = {
                                                       parameters : [],
                                                };
                                         /* var checked = []
                                         $("input[name='resourceCheckBox[]']:checked").each(function ()
                                        {
                                                alert("parseInt($(this).val())"+parseInt($(this).text()));
                                             checked.push(parseInt($(this).text()));
                                         });
                                         alert("checked=="+checked);
*/                                
                                     requestSkillResourceId = [];
                              var statusType =[];
                              var allocationId =[];
                              var resourceId=[]; 
                              var allocationDate=[]; 
                              skillId= window.skillId;
                               reuestId= window.reuestId;
                               projId=window.projId;
                               //alert("skillId"+skillId+"===="+reuestId+"===projId="+projId);
                                      $('.allocated_resource_table > tbody tr').each(function (i, row) {
                                     var tableRow = $(row);
                                    
                                     if(tableRow.find('input[name*="skillResourceId"]').val()!= null){
                                           
                                            requestSkillResourceId.push(tableRow.find('input[name*="skillResourceId"]').val()); 
                                     }
                                      
                                      if(tableRow.find('select[name*="stType"]').val()!= null){
                                               statusType.push(tableRow.find('select[name*="stType"]').val());
                                      }
                                     /*  if(tableRow.find('input[name*="weekEndDateSel"]').val()!= null && tableRow.find('input[name*="weekEndDateSel"]').val()!= ""){
                                               allocationDate.push(JSON.stringify(tableRow.find('input[name*="weekEndDateSel"]').val()));
                                      } */
                                      if(tableRow.find('select[name*="allocationId"]').val()!= null){
                                          allocationId.push(tableRow.find('select[name*="allocationId"]').val());
                                          allocationDate.push(JSON.stringify(tableRow.find('input[name*="weekEndDateSel"]').val()));
                                      }
                                      if(tableRow.find('input[name*="skillResourceNameId"]').val()!= null && tableRow.find('input[name*="skillResourceNameId"]').val()!= ""){
                                          resourceId.push(tableRow.find('input[name*="skillResourceNameId"]').val());
                                      }
                                       
                                 });
                                          requirements.parameters
                                                       .push({
                                                              "reqId" : reuestId,
                                                              "skillReqId" : skillId,
                                                              "projectId" : projId,
                                                              "requestResourceId" : requestSkillResourceId,
                                                              "statusType":   statusType,
                                                              "allocationId" : allocationId,
                                                              "resourceId" : resourceId,
                                                              "allocationDate" : allocationDate, 
                                                              
                                                       });
                                           var arrayLength =statusType.length;
                                           var count=0;
                                           if(arrayLength>resourcesRequired){
                                           for (var i = 0; i < arrayLength; i++) {
                                               if(statusType[i]==4||statusType[i]==12||statusType[i]==22)
                                               {
                                                 count++;
                                               }
                                           }
                                           if(count>resourcesRequired){
                                                 if(remainingResources==1)
                                                 {
                                                        alert("Resource requisition limit exceeds, need to fulfill request for only "+remainingResources+" resource.");
                                                 }
                                                 else if(remainingResources==0)
                                                 {
                                                        alert("Resource requisition is completed.");
                                                        
                                                 }
                                                 else{
                                                        
                                                        alert("Resource requisition limit exceeds, need to fulfill request for only "+remainingResources+" resources.");
                                                 }
                                                return false;
                                                }
                                           }
                                           startProgress();
                                           $.ajax({
                                                                           type : 'POST',
                                                                           url : 'requestsReports/updateResourceRequestWithStatus',
                                                                           contentType : "application/json; charset=utf-8", 
                                                                           data : JSON.stringify(requirements),
                                                                           cache : false,
                                                                           async : false,
                                                                           dataType: 'text',
                                                                           success : function(data) {
                                                                                  stopProgress();
                                                                                  showSuccess("Updated Successfully! ");
                                                                                  setTimeout(function(){
                                                                                         window.location.reload();
                                                                                  },1000); 
                                                                            },
                                                                           error: function(error){
                                                                            stopProgress();
                                                                            showError("Something happend wrong!!");
                                                                            //window.location.reload();
                                                                     }
                                                                     });
                                            }
                                     
                                  });
                                  
                                  $('#closebtn,.close').on('click', function() { 
                                         //$("inputText").empty();
                                         $(".inputText").remove();
                                         x = 0;
                                         
                                         //$("#externalButtonAdd[]").empty();
                                  });
                                 /*  $("#remarkButton").click(function() {
                                     alert("test");
                                      var $item = $(this).closest("tr").find("#stType") .text();         // Retrieves the text within <td>
                          alert("$item"+$item+"== item=="+item);
                                     // $("#resultas").append($item);       // Outputs the answer
                                  });  */
                                  
                                  });


        //when open pop up Model   
    var countRes;
   //$('a[data-toggle="modal"]').on('click', function(e) 
              function openPopUpModelWithAjax(rowVale ,skillIdValue,resourcesNeedToFulfill,remaining,reuestIdValue,projectId,fillResLen)   
        {
           
                     resourcesRequired=resourcesNeedToFulfill;
                     remainingResources=remaining; 
           if(true/* confirm("Sure you want to add this update? There is NO undo!") */) //sarang added
                        {
          var target_modal = $(rowVale).data('target');
          skillId = skillIdValue; 
          requestID = reuestIdValue;
          reuestId = reuestIdValue; 
          projId=projectId
          fullfillResLen=fillResLen;
         // alert("open model pop up skill fullfillResLen len"+fullfillResLen);
           var trHTML = '';
          var htmlVar = ''; 
           getValidationid = "noOfRes"+skillIdValue;
                 jQuery.ajax({
                        type: 'POST',
                        url: '${pageContext.request.contextPath}/requestsReports/addResourceWithSkillReqId',
                        data: {"id":skillId},
                        dataType: 'json',
                        success: function(response){
                               $(target_modal).modal();
                                   for(var i=0;i<response.length;i++){
                                         var obj=response[i];
                                         //countRes=obj.skillResources.length;
                                         //getValidationCount = countRes;
                                         $.each(obj.skillResources, function(index, data) { 
                                               
                                                if(data.externalResourceName!=null){// for External
                                                       
                                                       htmlVar += '<tr>';
                                                       /* htmlVar += '<td><input type="checkbox" id="resourceCheckBox" name="resourceCheckBox[]" ></td>'; */
                                                       htmlVar += '<td>External</td>';
                                                       htmlVar += '<td><span>'+data.externalResourceName+'</span></td>';
                                                       
                                                       htmlVar += '<input type="hidden" value="'+data.id+'" id="skillResourceId" name="skillResourceId">';
                                                       htmlVar += '<input type="hidden" id="skillResourceNameId" name="skillResourceNameId">';
                                                       htmlVar += '<input type="hidden" id="resourceName" name="resourceName" value="">';
                                                       htmlVar += '<input type="hidden" value="'+skillId+'" id="skillRequestId" name="skillRequestId">';
                                                       if(data.statusId != null && data.statusId.id== "12"){
                                                              htmlVar += '<td  align="center">'  
                                                            +'<select  disabled  name="stType" inp="stType" id="stType"><option value="0">Select</option>'
                                                            +'<c:forEach var="statusType" items="${skillResStatusList}">'
                                                            +'<c:if test="${statusType.statusType eq '2'}">';
                                                            if(data.statusId != null && "${statusType.id}" == data.statusId.id)
                                                                 {
                                                                   htmlVar +='<option selected="selected" value="${statusType.id}">${statusType.statusName}</option>';
                                                                 }
                                                           
                                                                  htmlVar +='</c:if></c:forEach></select></td>';
                                                       }else{
                                                       htmlVar += '<td  align="center">'  
                                                            +'<select   name="stType" inp="stType" id="stType"><option value="0">Select</option>'
                                                            +'<c:forEach var="statusType" items="${skillResStatusList}">'
                                                            +'<c:if test="${statusType.statusType eq '2'}">';
                                                            if(data.statusId != null && "${statusType.id}" == data.statusId.id)
                                                                 {
                                                                   htmlVar +='<option selected="selected" value="${statusType.id}">${statusType.statusName}</option>';
                                                                 }
                                                           else{
                                                                   htmlVar += '<option  value="${statusType.id}">${statusType.statusName}</option>';
                                                               } 
                                                                  htmlVar +='</c:if></c:forEach></select></td>';
                                                       }
                                                htmlVar +=  '<td ><input style="display: none;" type="text" name="weekEndDateSel"  size="16" maxlength="80" value="" >'
                                                                       +'</td>';
                                                   
                                                   htmlVar +='<td ><select style="display: none;"  class="required comboselect check" id ="allocationId" name="allocationId" >'
                                                                                  +'<option value="0">Select</option></select></td>';  
                                          if(data.statusId != null && data.statusId.id== "12"){
                                               
                                          htmlVar += '<td></td>';
                                         
                                          }
                                         else{
                                               htmlVar += '<td><a class="pointer" onclick="fileDownload('+data.id+')"  "href="javascript:void(0);">Resume</a></td>';
                                               htmlVar += '<td><a class="pointer"  data-id="'+data.id+'"  onclick="deleteData('+data.id+','+skillId+',this);" href="javascript:void(0);"><img alt="Delete" height="20px" width="20px" src="resources/images/delete.png"></a></td>';// Raghvendra Added Image      
                                         }                
                                                    htmlVar += '</tr>';
                                                }else{// for Internal
                                                       htmlVar += '<tr>';
                                                       /* htmlVar += '<td><input type="checkbox" id="resourceCheckBox" name="resourceCheckBox[]" ></td>'; */
                                                       htmlVar += '<td>Internal</td>';
                                                       htmlVar += '<td><span>'+data.resourceId.firstName+'</span> <span>'+data.resourceId.lastName+'</span></td>';
                                                       savedInternalResources.push(data.resourceId.firstName+" "+data.resourceId.lastName);
                                                       htmlVar += '<input type="hidden" value="'+data.id+'" id="skillResourceId" name="skillResourceId">';
                                                       htmlVar += '<input type="hidden" id="skillResourceNameId" name="skillResourceNameId" value="'+data.resourceId.employeeId+'">';
                                                       htmlVar += '<input type="hidden" id="resourceName" name="resourceName" value="'+data.resourceId.firstName+' '+data.resourceId.lastName+'">';
                                                       htmlVar += '<input type="hidden" value="'+skillId+'" id="skillRequestId" name="skillRequestId">';
                                                       
                                                       if((data.statusId != null && data.statusId.id== "4") || (data.statusId != null && data.statusId.id== "22")){
                                                              htmlVar += '<td  align="center">'  
                                                                  +'<select disabled    name="stType" inp="stType" id="stTypeInternal"><option value="0">Select</option>'
                                                                  +'<c:forEach var="statusType" items="${skillResStatusList}">'
                                                                  +'<c:if test="${statusType.statusType eq '1'}">';
                                                                  if("${statusType.id}" == data.statusId.id)
                                                                 {
                                                                   htmlVar +='<option selected="selected" value="${statusType.id}">${statusType.statusName}</option>';
                                                                 }
                                                                  htmlVar +='</c:if></c:forEach></select></td>';
                                                               }
                                                       else{
                                                                htmlVar += '<td  align="center">'  
                                                                  +'<select onchange="changStatusType(this);"   name="stType" inp="stType" id="stTypeInternal"><option value="0">Select</option>'
                                                                  +'<c:forEach var="statusType" items="${skillResStatusList}">'
                                                                  +'<c:if test="${statusType.statusType eq '1'}">';
                                                                  if(data.statusId != null && "${statusType.id}" == data.statusId.id)
                                                                        {
                                                                          htmlVar +='<option selected="selected" value="${statusType.id}">${statusType.statusName}</option>';
                                                                        }
                                                                  else{
                                                                          htmlVar += '<option  value="${statusType.id}">${statusType.statusName}</option>';
                                                                      } 
                                                                  
                                                                        htmlVar +='</c:if></c:forEach></select></td>';
                                                           }
                                                                        if(data.allocationDate!= null){
                                                                               htmlVar  +=  '<td><input type="text" name="weekEndDateSel" size="16" maxlength="80" value="'+data.allocationDate+'" readonly >'
                                                                            +'</td>';       
                                                                        }
                                                                        else{
                                                                               htmlVar  +=  '<td><input type="text" name="weekEndDateSel" size="16" maxlength="80" value=""  onClick="hasDatepicker();" class="fromdatepicker">'
                                                                            +'</td>';              
                                                                        }
                                                                                                   
                                                                      if( data.allocationId != null){
                                                                            
                                                                            htmlVar +='<td><select class="required comboselect check" id ="allocationId" name="allocationId" disabled>'
                                                                         +'<option value="0">Select</option><c:forEach var="allocationtype" items="${resourceAllocation}">';
                                                                            if("${allocationtype.id}" == data.allocationId.id){
                                                                                   htmlVar +='<option  value="${allocationtype.id}" selected="selected">${allocationtype.allocationType}</option>';
                                                                            }
                                                                           htmlVar +='</c:forEach> </select></td>'      ; 
                                                                      }
                                                                    else{
                                                                            htmlVar +='<td><select class="required comboselect check" id ="allocationIdInternal" name="allocationId" >'
                                                                      +'<option value="0">Select</option><c:forEach var="allocationtype" items="${resourceAllocation}">';
                                                                             htmlVar +='<option value="${allocationtype.id}">${allocationtype.allocationType}</option>';
                                                                            htmlVar +='</c:forEach> </select></td>'      ;      
                                                                    }
                                                                     if(true){
                                                                           htmlVar += '<td><a  class="pointer" onclick="fileDownload('+data.id+')"  "href="javascript:void(0);">Resume</a></td>'; 
                                                                      }
                                                              if((data.statusId != null && data.statusId.id== "4") || (data.statusId != null && data.statusId.id== "22")){
                                                                           htmlVar += '<td></td>';
                                                                    }
                                                                    else{
                                                                            htmlVar += '<td><a class="pointer"   data-id="'+data.id+'" onclick="deleteData('+data.id+','+skillId+',this);" "href="javascript:void(0);"><img alt="Delete" height="20px" width="20px" src="resources/images/delete.png"></a></td>';//Raghvendra Added Image
                                                                           //Raghvendra Added Image
                                                                    }
                                                              
                                                                            htmlVar += '</tr>';
                                                    }
                                                countRes++;
                                     });
                                   }
                                   
                            $('.allocated_resource_table tbody').empty().html(htmlVar);   
                             $('.fromdatepicker').datepicker({ 
                                             onOpen :function(){  // alert("Hii open"); 
                                             },
                                         onClose: function( selectedDate ) {
                                                 //changedAllocStartdate = selectedDate;
                                           if(this.value == ""){
                                                       $(this).css("border", "1px solid #ff0000");
                                                       }
                                                      else
                                                      {
                                                              $(this).css("border", "1px solid #D5D5D5");
                                                      }
                                       },
                                       beforeShow: function(){
       
                                          }
                                      }); 
                                   $('.allocated_resource_table > tbody tr').each(function (i, row) {
                                         
                                    var tableRow = $(row);
                                   // alert(" test=="+tableRow.find('#stTypeInternal').val());
                                     if(tableRow.find('#stTypeInternal').val() != 4){
                                           
                                            tableRow.find('.fromdatepicker').hide(); 
                                            tableRow.find('#allocationIdInternal').hide(); 
                                     }
                                 });
                        },
                              error:function(response){
                                     stopProgress();
                                  showError("Somthing happends wrong!!");
                              },
                 });
                        }
          }
   
              function changStatusType(sel)
              {
                  if(sel.value == 4)
              {
                     $(jQuery(sel).parent().parent()).each(function (i, row) {
                              var tableRow = $(row);
                              if(tableRow.find('#stTypeInternal').val() != 0){
                                     tableRow.find('.fromdatepicker').show(); 
                                     tableRow.find('#allocationIdInternal').show(); 
                              }
                          });
              }else
              {
                     $(jQuery(sel).parent().parent()).each(function (i, row) {
                             var tableRow = $(row);
                             tableRow.find('.fromdatepicker').hide(); 
                             tableRow.find('#allocationIdInternal').hide(); 
                             
                          });
              }
                 
              }
       // for delete content in table
       function clearText()  
       {
              document.getElementById('resourceBlocked').value = "";
                
              $('#InternalResourceText').html('');
              
       }
       
       function fileDownload(id){ 
                    $.ajax({                          
                      url: 'requestsReports/downloadfiles',
                     contentType: "application/json",
                     async:false,
                     data: {"id":id},     
                     success: function(response) {                                        
                            if(response==""){
                                   
                                   showAlert("\u2022 File not found for id " + id + " ! <br />");
                                   
                            }else{                                                                
                                if(id !== ''){
                                                window.location.href = 'requestsReports/'+'downloadfiles?id='+id;
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
       
       function deleteData(id,skillId,row){
              var i=row.parentNode.parentNode.rowIndex;
              var element = $(this);
            var idValue = id;
            var info = 'id=' + idValue;
            $(row).parent().parent().remove();
          if(confirm("Sure you want to delete this update? There is NO undo!"))
          {
                 $.ajax({
                        type: 'POST',
                        url: '${pageContext.request.contextPath}/requestsReports/deleteSkillRequestResource',
                        dataType: "text",
                        data: {"id":idValue,"skillId":skillId},
                        success: function(data){
                               showSuccess("Resource removed Successfully!");
                               window.location.reload();
                        },
                              error:function(response){
                                     stopProgress();
                                  showError("Somthing happends wrong!!");
                              },
                 });
                  
          }
          return false;
       }//Raghvendra Delete Request Function
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
       
       parameters = [];
       
       $(document).ready(function() {
              $('#saveResource').click(function(){
                     
                     
                     var external = $('#externalButtonAdd').val();
                     
                     var sel = $('#InternalResourceText').val();
                     var names = $("#InternalResourceText").val(); 
                      var res = $('#resourceBlocked').val();
                     var spli = names.split(",");
                      
                     parameters = [];
                     
                     
                     if(spli.length > 0){
                           for (var z = 0; z <= spli.length-1; z++) {
                                  var details = "<tr>" + 
                                  "<td>" + spli[z] + "</td>" +
                               "<td><input type='file' name='files' class='resume' id='"+z+"' onchange='myFunction(event)'></td>" +
                                "</tr>";
                               $("#uploadResume").append(details) ;
                                  }
                     }      
                            
               
                
                if (external !== undefined && external!= '') {
                           var details = "<tr>" +   
                       "<td>" + external + "</td>" +
                       "<td><input type='file' value = 'upload' class='external_resume' name='files' id='resume_fileEx'  ></td>" +
                       "</tr>";

                      $("#uploadResume").append(details);
                     } 

                     
              });
              
              
});
       
       var arr = [];
       var arrOfFileNames = [];
       function myFunction(event){
              
              var id = event.target.id;
              var x = document.getElementsByClassName('resume');
              for (var i = 0; i < x.length; i++){
                     if(i == id) {
                           var select = document.getElementById(i);
                           var file =$('#'+i).prop('files')[0];
                           var ext = file.name.split('.').pop();
                           if(ext=="pdf" || ext=="docx" || ext=="doc"){
                                  arrOfFileNames.push(file.name);
                                  arr.push(file);
                           }
                           else{
                                  alert("Upload only doc or pdf files");
                           }
                           
                      }
              }
              
       }
              
       function externalResume(){
              var x = document.getElementsByClassName('external_resume');
              
              if(x.length != 0 ){
                     var externalFile = $('#resume_fileEx').prop('files')[0];
                     var ext = externalFile.name.split('.').pop();
                     if(ext=="pdf" || ext=="docx" || ext=="doc"){
                           arrOfFileNames.push(externalFile.name);
                           arr.push(externalFile);
                     }
              }
       }      
              
            

                     function fileUpload(fileName) {
                                  if (fileName.value != "") {
                                         var sizeofdoc = fileName.files[0].size;
                                         //alert(sizeofdoc + "bytes");
                                         if (!/(\.doc|\.docx|\.pdf)$/i.test(fileName.value) /* | sizeofdoc > 358400 */) {
                                                showError("Upload only doc or pdf files");
                                                return false;
                                         } else {
                                                return true;
                                         }
                                  } else {
                                         showError('Please select pdf or doc file');
                                         return false;
                                  }
                     }
                     
                      function Closemodel() {
                            
                                         var element = document.getElementsByClassName("table-responsive")
                                         element.parentNode.removeChild(element);
                     }
                     
                     $(document).ready(function() {
                           
                           $(".modelclose").click(function() {
                                  $(".table-responsive").empty();
                           })
                           
                     })
                     
       //Start: Alphabetvalidation:Added by Pratyoosh Tripathi//
/*     function alphabeticvalidationExp(text) {
       
       var letters = /^[A-Za-z]+$/;

       if (!text.value.match(letters)) {
              

              var newText = text.value.toString().slice(0,-1);
              text.value =  newText;
            $("add_button").val(newText);
              showError('Please enter alphabet characters only');    
              return false;
              
       
       }else{
              
       return true;
       }
       }  */
               
       //END: Alphabetvalidation:Added by Pratyoosh Tripathi//
    
       

/*   
    $(document).ready(function () {
  
      $('.btn1').click(function () {
        $('#div1').html('<button id="btnNew">Add Button</button>');
      });
         
     

    }); */
/*  --------------------------------------------------------------------------------------------------------------------------------------------------------------- */
   

    $(document).ready(function () {
        $("#submitForm").click(function (event) {
            //stop submit the form, we will post it manually.
            
            event.preventDefault();
            fire_ajax_submit();
        });
    });
    
    function fire_ajax_submit() {
       externalResume();
        var res = $('#resourceBlocked').val();
        var externalResourceName = $("#externalButtonAdd").val();
        var skillReqComment=$('input[name="skillReqComment"]').val();
        var form = $('#formFile')[0];
        var data = new FormData(form);
        var mailTo = [];
        $.each($("input[name='checkId']:checked"), function(){            
        	mailTo.push($(this).val());
        });
        mailTo.join(",");
        data.append("reqID", requestID);
        data.append("skillReqId", skillId);
        data.append("fullfillResLen", fullfillResLen);
        data.append("comments", skillReqComment);
        data.append("resourceId", res);
        data.append("externalResourceName", externalResourceName);
        data.append("mailTo", mailTo);
               for (i = 0; i<arr.length; i++ ){                                                        //to append multiple resume files on click. 
                      data.append("resumes",arr[i]);
               }
              for (i = 0; i<arrOfFileNames.length; i++ ){                               //to append multiple resume file names. 
                     data.append("resumeNames", arrOfFileNames[i])
               }
               
        $("#submitForm").prop("disabled", true);
		startProgress();
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "requestsReports/saveRequest",
            data: data,    
            processData: false,                                                                 //prevent jQuery from automatically transforming the data into a query string
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function () {
                
                $("#submitForm").prop("disabled", false); 
            },
            error: function (e) {
            	 stopProgress();
            	 showSuccess("Data Saved Successfully!");
                 window.location.reload();
               
            }
        });

    }
    
  
              
</script>
<div class="content-wrapper" onload="myFunction()">
       <div class="mid_section" style="overflow: auto;">
              <!--  style="overflow:auto;" -->
              <div class="botMargin">
                     <h1>Resource Report DashBoard</h1>
              </div>
              <div class="tab_seaction">
                     <ul class='tabs'>
                           <li><a href='#tab1'>List</a></li>
                     </ul>
                     <div id='tab1' class="tab_div"
                           style="float: left; width: 100%; overflow: auto; padding: 10px 0px; height: 500px;">
                           <div class="tbl" style="width: 125%;">
                                  <div style="float: right">
                                         <!-- <button id="save">Save</button> -->
                                         <button onClick="saveTable()">Save As Excel</button>
                                  </div>
                                 
                                  
                                  <div style="padding-left: 378px; float: left;">
                                         <label>Status</label> &nbsp;<select id="status">
                                                <option value="active">Active</option>
                                                <option value="all">All</option>
                                         </select>
                                  </div>
                                  
                                     <div class="blocked-select positionRel">
	                                  	<select id='filterByCustomer'  class="required"  >
		                                  	<option disabled selected>Select</option>
		                                  	<c:forEach var="customerName" items="${customerNames}">
												<option value="${customerName}" >${customerName}</option>
											</c:forEach>
											<option value='all'>All</option>
										</select>
                                    </div>     
                                         
                            

                                  <div class="clear"></div>
                                  <table class="dataTbl display tablesorter addNewRow alignCenter"
                                         id="resourcerequestTable"
                                         style="margin-top: 0px; margin-left: 0px !important; width: 100% !important">
                                         <thead>
                                                <tr  class="content">
                                                       <th align="center" valign="middle" width="5%">RRF</th>
                                                       <th align="center" valign="middle" width="10%">Requirement
                                                              Name</th>
                                                       <th align="center" valign="middle" width="1%">Resource(s)
                                                              Req</th>
                                                       <th align="center" valign="middle" width="5%">Resource
                                                              Allocation</th>
                                                       <th align="center" valign="middle" width="5%">Request
                                                              fulfilled</th>
                                                       <th align="center" valign="middle" width="5%">Need To
                                                              Fulfill</th>
                                                       <th align="center" valign="middle" width="5%">Requested
                                                              Date</th>
                                                       <!-- <th align="center" valign="middle" width="7%">BU</th> -->
                                                      <!--  <th align="center" valign="middle" width="10%">Indentor's
                                                              Name</th> -->
                                                       <th align="center" valign="middle" width="7%">Project Name</th>
                                                       <th align="center" valign="middle" width="7%">Skills</th>
                                                       <!-- <th align="center" valign="middle" width="10%">Proposed
                                                              Desig.</th> -->
                                                       <th align="center" valign="middle" width="3%">Exp.</th>
                                                       <!-- <th align="center" valign="middle" width="3%">Allocation
                                                              Type</th> -->
                                                      <!--  <th align="center" valign="middle" width="3%">Type</th> -->
                                                      <!--  <th align="center" valign="middle" width="3%">Time-Frame</th> -->
                                                       <!-- <th align="center" valign="middle"  width="15%">Comments</th> -->
                                                       <!--   <th align="center" valign="middle"  width="32%">Comments</th>  -->
                                                       <th hidden>customer</th>
                                                       <sec:authorize access="hasAnyRole('ROLE_ADMIN')"> <th align="center" valign="middle" width="5%">Delete</th></sec:authorize>
                                                       <!--1003958 Starts[]-->
                                                       <th align="center" valign="middle" width="5%">Edit</th>
                                                       <!--1003958 Ends[] -->
                                                </tr>
                                         </thead>
                                         <tbody  class="content" id="resourcerequestTableBody">
                                                <c:set var="count" value="0" scope="page" />

                                                <c:forEach var="reportList" items="${reportList}">

                                                       <!-- <td></td> -->
                                                       <tr id="${reportList.requestRequisition.id}" class="abc">
                                                              <td><a
                                                                     onclick="skillRequestReportPDF(this,${reportList.id})">RRF</a></td>

                                                              <td>${reportList.requestRequisition.comments}<!-- for hidden skill Resource Id -->
                                                                     <table>
                                                                           <c:forEach var="skillResources"
                                                                                  items="${requestRequisitionResource.requestRequisitionSkill}">
                                                                                  <tr class="skillResourcesId">
                                                                                         <td><input type="hidden" value="${requestRequisitionSkill.id}" /></td>
                                                                                  </tr>
                                                                           </c:forEach>
                                                                     </table>

                                                              </td>

                                                              <td><table>
                                                                           <tr>
                                                                                  <td id="noofresources">${reportList.noOfResources}</td>
                                                                           </tr>
                                                                           <tr style="height: 10px">
                                                                                  <input type="hidden" id="noOfRes${reportList.id}"
                                                                                         value="${reportList.noOfResources}" />
                                                                           </tr>
                                                                     </table></td>
                                                              <td>
                                                                     <table id="addResModal">
                                                                           <tr id="addResModaltr">
                                                                                  <a
                                                                                         onclick="openPopUpModelWithAjax(this,${reportList.id},${reportList.noOfResources},${reportList.remaining},${reportList.requestRequisition.id},${reportList.requestRequisition.project.id} ,${reportList.fulfilled})"
                                                                                         data-target="#addResource" id="${reportList.id}"
                                                                                         href="javascript:void(0)">Add/Delete<br>Resource
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
                                                                                  <%--  <input type="text" readonly="readonly" id="skillRequestCommentText${reportList.id}" class="skillRequestComment" name="skillRequestComment"  value="" /> --%>
                                                                            </tr>
                                                                     </table>
                                                              </td>

                                                              <td>
                                                                     <table id="fulfilledResources">
                                                                           <tr class="ffres">
                                                                                  <td><input class="ff" id="fulfilledRes${reportList.id}"
                                                                                         readonly="readonly" name="fulfilledRes" type="hidden"
                                                                                         value="${reportList.fulfilled}"<%--onkeyup="setRemaining()" --%> ></td>
                                                                           </tr>
                                                                           <tr style="height: 10px">
                                                                                  <td>${reportList.fulfilled}</td>
                                                                           </tr>
                                                                     </table>
                                                              </td>

                                                              <td><table>
                                                                           <tr class="remResources">
                                                                                  <td id="remainingRes">${reportList.remaining}</td>
                                                                           </tr>
                                                                           <tr style="height: 10px"></tr>
                                                                     </table></td>
                                                              <td><center>
                                                                           <fmt:formatDate value="${reportList.requestRequisition.date}"
                                                                                  pattern="MM-dd-yyyy" />
                                                                     </center></td>
                                                              <%-- <td id="firstTd"><center>${reportList.requestId.projectBU}</center></td> --%>
                                                             <%--  <td><center>${reportList.requestId.employeeId.firstName}
                                                                            ${reportList.requestRequisition.employeeId.lastName}</center></td>--%>
                                                              <td><center>${reportList.requestRequisition.project.projectName}</center></td> 

                                                              <td><table id="skillRequestId">
                                                                           <tr class="skillReqId">
                                                                                  <td><input type="hidden" value="${reportList.id}">
                                                                                         <center>${reportList.skill.skill}</center></td>
                                                                           </tr>
                                                                           <tr style="height: 10px"></tr>
                                                                     </table></td>

                                                            <%--   <td><table>
                                                                           <tr>
                                                                                  <td>${reportList.designation.designationName}</td>
                                                                           </tr>
                                                                           <tr style="height: 10px"></tr>
                                                                     </table></td> --%>
                                                              <td><table>
                                                                           <tr>
                                                                                  <td>${reportList.experience}</td>
                                                                           </tr>
                                                                           <tr style="height: 10px"></tr>
                                                                     </table></td>
                                                              <%-- <td><table>
                                                                           <tr>
                                                                                  <td>${reportList.billable.allocationType}</td>
                                                                           </tr>
                                                                           <tr style="height: 10px"></tr>
                                                                     </table></td> --%>
                                                              <%-- <td><table>
                                                                           <tr>
                                                                                  <c:if test="${reportList.type == 1}">
                                                                                         <td>New Requirment</td>
                                                                                  </c:if>
                                                                                  <c:if test="${reportList.type == 0}">
                                                                                         <td>Replacement</td>
                                                                                  </c:if>
                                                                           </tr>
                                                                           <tr style="height: 10px"></tr>
                                                                     </table></td> --%>
                                                          <%--     <td> <table>
                                                                           <tr>
                                                                                  <td id="timeFrame">${reportList.timeFrame}</td>
                                                                           </tr>
                                                                           <tr style="height: 10px"></tr>
                                                                     </table></td> 
															 --%>

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
                                                       <td  hidden>
                                                       	<select  hidden >
						                                  	<c:forEach var="name" items="${customerListByRequestId}">
																<option id="customersByRequests" value="${name}" hidden>${name}</option>
															</c:forEach>
														</select>
                                                       </td>
                                                       
                                                       <sec:authorize access="hasAnyRole('ROLE_ADMIN')">      
                                                        <td>
	                                                        <c:if test="${reportList.fulfilled <= 0}">
	                                                                           <a onclick="skillRequestReportDelete(${reportList.requestRequisition.id},${reportList.id})"><img
	                                                                                  alt="Delete" height="20px" width="20px"
	                                                                                  src="resources/images/delete.png"></a>
	                                                         </c:if>
                                                          </td>
                                                         </sec:authorize>
                                                              <!--1003958 Starts[]-->
                                                              <td><c:if test="${reportList.fulfilled <= 0}">
                                                                           <a onclick="editrequest(this,${reportList.id})"
                                                                                  href="javascript:void(0);">Edit</a>
                                                                     </c:if></td>
                                                              <!--1003958 Ends[]-->
                                                       </tr>
                                                       <c:set var="count" value="${count + 1}" scope="page" />
                                                </c:forEach>
                                         </tbody>
                                  </table>
                           </div>
                     </div>
              </div>
       </div>
</div>

<!-- popup RRF start  -->
<div class="modal fade" id="addResource" role="dialog"
       data-backdrop="static" data-keyboard="false">
       <div class="modal-dialog">

              Add Resource For Skill Request
              <div class="modal-content">
                     <div class="modal-header">
                           <button type="button" class="close" data-dismiss="modal">&times;</button>
                           <h4 class="modal-title">Add Resource For Skill Request</h4>
                     </div>

                     <div class="modal-body">

                           <div class="form-group">
                                  <div>

                                         <div id="imgContainer"></div>
                                  </div>
                                  <%-- <form action="requestsReports/fileUpload" method="post"
                                         enctype="multipart/form-data">
                                         <div class="form-group">
                                                <label>Select File</label> <input class="form-control"
                                                       type="file" name="file">
                                         </div>
                                         <div class="form-group">
                                                <button class="btn btn-primary" type="submit">Upload</button>
                                         </div>
                                  </form> --%>
                                  <div>
                                         <form class="form" id="formFile" 
                                                enctype="multipart/form-data">
                                                <table border="0" width="100%" class="request-reporttable well">
                                                       <tr>
                                                              <th>Resource Type</th>
                                                              <th>Below Resources are Blocked/Proposed</th>
                                                              <!-- sarang change -->
                                                       </tr>
                                                       <tr>
                                                              <td valign="top"><label for="internal">Internal</label></td>
                                                              <td valign="top">
                                                                     <div id="internal">
                                                                           <div class="blocked-select positionRel">
                                                                                  <select id="resourceBlocked" class="required" name="reqID"
                                                                                         multiple="multiple">
                                                                                         <c:forEach var="mail" items="${ActiveUserList}">
                                                                                                <option value="${mail.employeeId}">${mail.firstName} ${mail.lastName}</option>
                                                                                         </c:forEach>
                                                                                  </select>

                                                                           </div>
                                                                           <div class="new-value">
                                                                                  <textarea id="InternalResourceText"
                                                                                         name="InternalResourceText" class="resourceBlocked_id"
                                                                                         readonly></textarea>
                                                                           </div>
                                                                     </div>
                                                              </td>
                                                       </tr>
                                                       <tr>
                                                              <td><label for="external">External</label></td>
                                                              <td>
                                                                     <div>
                                                                           <div id="externalResourceId" class="external"
                                                                                  name="externalResourceName">
                                                                                  <div class="sub-row">
                                                                                         <!--   <input type="text" id="externalButtonAdd[]" name="externalButtonAdd[]"/>-->
                                                                                         <button class="add_field_button">Add External
                                                                                                Resource:</button>
                                                                                  </div>
                                                                           </div>
                                                                     </div>
                                                              </td>
                                                       </tr>
                                                       <tr>
                                                              <td><label for=comment>Comment</label></td>
                                                              <td>
                                                                     <div>
                                                                           <div class="comment">
                                                                                  <div class="sub-row">
                                                                                         <input type="text" id="skillReqComment"
                                                                                                name="skillReqComment"> <br>

                                                                                  </div>
                                                                           </div>
                                                                     </div>
                                                              </td>
                                                       </tr>
                                                        <tr>
                                                              <td><label for=mailTo>Mail To</label></td>
                                                              <td>
                                                                     <div>
											                                 <div class="mailTo">
																		     	<input type="checkbox" value="1" name="checkId">Notify To
																		      	<input type="checkbox" value="2" name="checkId">Request Sent to
																		    </div>
                                                                     </div>
                                                              </td>
                                                       </tr>
                                                       <tr>
                                                              <td></td>
                                                              <td colspan="2"><div>
                                                                           <input type="button" id="saveResource" name="saveResource"
                                                                                  data-toggle="modal" data-target="#uploadResumeModal"
                                                                                  class="btn btn-default" value="Save Resources" />
                                                                     </div></td>
                                                       </tr>
                                                </table>
                                  </div>
                                  <div class="report-status well">
                                         <table class="allocated_resource_table" width="100%">
                                                <thead>
                                                       <tr>
                                                              <!-- <th>CheckBox <input type="checkbox" id="selectall" /></th> -->
                                                              <th>Resource Type</th>
                                                              <th>Resource Blocked</th>
                                                              <th>Profile Status</th>
                                                              <!-- sarang change -->
                                                              <th>Allocation Start Date</th>
                                                              <th>Allocation Type</th>
                                                              <th>Resume</th>
                                                              <th>Delete</th>
                                                              

                                                       </tr>
                                                </thead>
                                                <tbody>
                                         </table>
                                  </div>

                           </div>

                     </div>
                     <div class="modal-footer well">
                           <button id="updateResourceStatus" name="updateResourceStatus"
                                  class="btn btn-default">Update Resource
                                  Status</button>
                           <button type="button" id="closebtn" class="btn btn-default"
                                  data-dismiss="modal" value="Reset" onclick="clearText()">Close</button>
                     </div>

              </div>
       </div>
</div>
<!-- <div class="skillRequestPDF">
<table>
<tr><td> this is raghvendra Singh</td> </tr>
</table>
</div> -->
<div id="uploadResumeModal" class="modal" role="dialog"
       data-backdrop="static" data-keyboard="false">
       <div class="modal-dialog">
              <div class="modal-content">
                     <div class="modal-header">
                           <button type="button" class="close modelclose" data-dismiss="modal"
                                  onclick="Closemodel()">&times;</button>
                           <h4 class="modal-title">Upload Resume</h4>
                           <div class="table-responsive">
                                  <table class="table table-bordered">
                                         <thead>
                                                <tr>
                                                       <th>Name</th>
                                                       <th>Resume</th>

                                                </tr>
                                         </thead>
                                         <tbody id="uploadResume" name="uploadResume">

                                         </tbody>



                                  </table>
                                  <div>

                                         <button id="submitForm" type="submit">Save Now</button>
                                         </td>
                                  </div>
                           </div>
                     </div>
              </div>
       </div>
</div>
</form>

<div class="clear"></div>
