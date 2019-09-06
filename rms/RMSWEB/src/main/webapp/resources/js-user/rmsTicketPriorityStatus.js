/*-----------------function for container width---------------------*/
function helloAlert(){
	alert("hello......");
}

function getTicketPriorityAndStatusHTML(data,key){
	var htmlVar = "";
	htmlVar += '<td valign="middle">';
	   console.log("html var : "+htmlVar);
		var approval;
		if(data[key].ticketNo!=""){
	   		if(data[key].ticketPriority!=null){   			
	   			if(data[key].ticketPriority!='-1' && data[key].ticketPriority!=''){
	   				htmlVar += '<input type="text" id="ticketPriorities'+key+'"  name="entries['+key+'].ticketPriority" value="'+data[key].ticketPriority+'"';
	   			}else{
	   				htmlVar += '<input type="text" id="ticketPriorities'+key+'"  name="entries['+key+'].ticketPriority" value=""';
	   			}
	   			if(data[key].approveStatus == 'A' || data[key].approveStatus == 'P'){
		           	htmlVar += ' readonly ';
		        }else{		        	
		        		htmlVar += 'onfocus="getTicketPriorityByProjectName('+"'"+data[key].resourceAllocId.projectId.projectName+ "'"+','+ key+','+"'"+data[key].ticketPriority+"'"+');"';		        	
		   		}          	
		   		htmlVar += '>';
	   		}else{
	   			htmlVar += '<input readonly type="text" id="ticketPriorities'+key+'"  name="entries['+key+'].ticketPriority" value=""';
	   		}  
		}else {
			htmlVar+="<select disabled name='entries["+key+"].ticketPriority' class='dd_cmn comboselect' id='ticketPriorities"+key+"'>";
			htmlVar+="<option  value='' >Please Select</option>"; 
           
			htmlVar+="</select>";  
	}
  htmlVar += '</td>';
  
  htmlVar += '<td valign="middle">';
  if(data[key].ticketNo!=""){
      if(data[key].ticketStatus!=null){
	      	if(data[key].ticketStatus!='-1' && data[key].ticketStatus!=''){
		   			htmlVar += '<input type="text" id="ticketStatus'+key+'"  name="entries['+key+'].ticketStatus" value="'+data[key].ticketStatus+'"';
	      	}else{
	      		htmlVar += '<input type="text" id="ticketStatus'+key+'"  name="entries['+key+'].ticketStatus" value=""';
	      	}
	   		if(data[key].approveStatus == 'A' || data[key].approveStatus == 'P'){
	           	htmlVar += ' readonly ';	        
	        }else{
	        		htmlVar += 'onfocus="getTicketStatusByProjectName('+"'"+data[key].resourceAllocId.projectId.projectName+ "'"+','+ key+','+"'"+data[key].ticketStatus+"'"+');"';	        	
	   		}	          	
	   		htmlVar += '>';
      }else{
 			htmlVar += '<input readonly type="text" id="ticketStatus'+key+'"  name="entries['+key+'].ticketStatus" value="" >';
 	  }
  }else{
  	htmlVar+="<select disabled name='entries["+key+"].ticketStatus' class='dd_cmn comboselect' id='ticketStatus"+key+"'>";
  	htmlVar+="<option  value='' >Please Select</option>";
              
  	htmlVar+="</select>";
  }
  htmlVar += '</td>';
  console.log("htmlvar for ticketpriorityandstatus : "+htmlVar);
  return htmlVar;
}



/**
 * by @Pankaj Birla for ticket priority dropdown acc to project id......
 */
 
 function getTicketPriorityAndStatus(id){
	 var ticketNoVal = $('#ticketNo'+id).val();
	 var resourceAllocatedId = $('#resourceAllocIdrow'+id).val();
	 console.log("ticket val : "+ticketNoVal);
	 
	 if(ticketNoVal.trim()!=''){	
       	  $.getJSON("projectTickets/getActiveTicketPrioritiesByResourceAllocationId/"+resourceAllocatedId, function(data){
       			  console.log("SUCCESS ticketPriority: ", JSON.stringify(data));
       			   var ticketPriorityFlag = false;
       			   var ticketPrioritySelect = "";
   	        	   $.each(data, function(j, projectTicketPrior) {
   	        		   if(j==0){
   	        			   ticketPriorityFlag = true;
   	        			   var ticketPriorElementType = $("#ticketPriorities"+id).attr('type');
   	        			   if(ticketPriorElementType == 'text'){
   	        				   var newRow = "<select name='entries["+id+"].ticketPriority' disabled class='dd_cmn comboselect' id='ticketPriorities"+id+"'></select>";
   	        				$("#ticketPriorities" + id).replaceWith(newRow);
   	        			   }
   	        			   ticketPrioritySelect = $('#ticketPriorities'+id);
   	        			   $('#ticketPriorities' + id).prop('disabled', false);
   		        		   $("#ticketPriorities" + id).empty();
   		        		   $("#ticketPriorities" + id).append(
   		        					 $('<option></option>').val("").html("Please Select")
   		        		    );
   	        		   }
   	        		   ticketPrioritySelect.append(
   	        	           $('<option></option>').val(projectTicketPrior.priority).html(projectTicketPrior.priority)
   	        	       );
   	        	   });
   	        	   if(!ticketPriorityFlag){   	        		   		
   	        		   	var ticketPriorElementType = $("#ticketPriorities"+id).attr('type');
	        			if(ticketPriorElementType == 'text'){
	        			    var newRow = "<select name='entries["+id+"].ticketPriority' disabled class='dd_cmn comboselect' id='ticketPriorities"+id+"'></select>";
	        				$("#ticketPriorities" + id).replaceWith(newRow);
	        			}
   	        		   
	   	        		 $("#ticketPriorities" + id).empty();
		   	    		 $("#ticketPriorities" + id).append(
		   	    				 $('<option></option>').val("").html("Please Select")
		   	    	     );
		   	    		 $('#ticketPriorities'+id).prop('disabled', true);		   	    		 
   	        	   }
       	   });	           	  
      		
       	   $.getJSON("projectStatus/getActiveTicketStatusByResourceAllocationId/"+resourceAllocatedId,function(data){
       			  console.log("SUCCESS ticketStatus :", JSON.stringify(data));
       			   var ticketStatusFlag = false;
   	        	   var ticketPriorityStatus = "";
   	        	   $.each(data, function(j, projectTicketStatus) {
   	        		   if(j==0){
   	        			   ticketStatusFlag = true;   	        			   
   	        			   var ticketStatusElementType = $("#ticketStatus"+id).attr('type');
 	        			   if(ticketStatusElementType == 'text'){
 	        				   var newRow = "<select name='entries["+id+"].ticketStatus' disabled class='dd_cmn comboselect' id='ticketStatus"+id+"'></select>";
 	        				   $("#ticketStatus" + id).replaceWith(newRow);
 	        			   }
 	        			  ticketPriorityStatus = $('#ticketStatus'+id);
   	        			   
   		        		   $('#ticketStatus' + id).prop('disabled', false);
   		        		   $("#ticketStatus" + id).empty();
   		        		   $("#ticketStatus" + id).append(
   		        					 $('<option></option>').val("").html("Please Select")
   		        		    );
   	        		   }
   	        		   ticketPriorityStatus.append(
   	        	           $('<option></option>').val(projectTicketStatus.status).html(projectTicketStatus.status)
   	        	       );
   	        	   });
   	        	  if(!ticketStatusFlag){
   	        		   var ticketStatusElementType = $("#ticketStatus"+id).attr('type');
	      			   if(ticketStatusElementType == 'text'){
	      				   var newRow = "<select name='entries["+id+"].ticketStatus' disabled class='dd_cmn comboselect' id='ticketStatus"+id+"'></select>";
	      				   $("#ticketStatus" + id).replaceWith(newRow);
	      			   }
	   	    		 $("#ticketStatus" + id).empty();		 
	   	    		 $("#ticketStatus" + id).append(
	   	      	           $('<option></option>').val("").html("Please Select")
	   	    	      );	   	    		 
	   	    		 $('#ticketStatus'+id).prop('disabled', true);
  	        	  }
       	   });

	 }else{
		 var newRow="";
		 newRow+="";
		 
		 var ticketPriorElementType = $("#ticketPriorities"+id).attr('type');
		 if(ticketPriorElementType == 'text'){
			var tempRow = "<select name='entries["+id+"].ticketPriority' disabled class='dd_cmn comboselect' id='ticketPriorities"+id+"'></select>";
		    $("#ticketPriorities" + id).replaceWith(tempRow);
		 }
	     
		 newRow+="<option  value='' >Please Select</option>";
		 $("#ticketPriorities" + id).empty();
		 $("#ticketPriorities" + id).append(
				 $('<option></option>').val("").html("Please Select")
	      );
		 
		 var ticketStatusElementType = $("#ticketStatus"+id).attr('type');
		 if(ticketStatusElementType == 'text'){
		    var tempRow = "<select name='entries["+id+"].ticketStatus' disabled class='dd_cmn comboselect' id='ticketStatus"+id+"'></select>";
		    $("#ticketStatus" + id).replaceWith(tempRow);
		 }
		 
		 $("#ticketStatus" + id).empty();		 
		 $("#ticketStatus" + id).append(
  	           $('<option></option>').val("").html("Please Select")
	      );
		 
		 $('#ticketPriorities'+id).prop('disabled', true);
		 $('#ticketStatus'+id).prop('disabled', true);
	 }	 
 }

function getTicketPriorityAndStatusByProjectIdOnProjectChange(projectId, id){
//	alert("row id : "+id+" and project id : "+projectId);
	var ticketNoVal = $('#ticketNo'+id).val();
	console.log("ticket number : "+ticketNoVal);
	$('#ticketNo'+id).val("");
	console.log("ticket no after null : "+$('#ticketNo'+id).val());
	getTicketPriorityAndStatus(id);	
}


function getTicketStatusByProjectName(projectName, id, ticketStatusName){
	var found = false;
	var resourceAllocatedId = $('#resourceAllocIdrow'+id).val();
	var newRow = "<select name='entries["+id+"].ticketStatus' class='dd_cmn comboselect' id='ticketStatus"+id+"'>";
	    newRow+="<option  value='' >Please Select</option>";   
	
		$.getJSON("projectStatus/getActiveTicketStatusByResourceAllocationId/"+resourceAllocatedId,function(data){
	           $.each(data, function(i, ticketStat){
	        	   found = true;
	        	   if(ticketStat.status==ticketStatusName){
	        		   newRow+="<option  value='" + ticketStat.status+"' selected=selected>"+ ticketStat.status+"</option>";
	        	   }else{
	        	   newRow+="<option  value='" + ticketStat.status+"' >"+ ticketStat.status+"</option>";
	        	   }
	           });
	           newRow+= "</select>";
	       	if(found){
	       		$("#ticketStatus" + id).empty();
	       		$("#ticketStatus" + id).replaceWith(newRow);
	       	}else{
	       		$('#ticketStatus'+id).empty();
	       		var readOnlyProp = "";
	       		if(ticketStatusName == '' || ticketStatusName == null){
	       			readOnlyProp = 'readonly';
	       		}
	       		var htmlVar = '<input type="text" '+readOnlyProp+' id="ticketStatus'+id+'" name="entries['+id+'].ticketStatus"  value="'+ticketStatusName+'"></input>';
	       	      $("#ticketStatus" + id)
	       		      .replaceWith(htmlVar);		
	       	}
	 });
	 
} 
	
	function getTicketPriorityByProjectName(projectName, id, ticketPriorityName){
		var found = false;
		var resourceAllocatedId = $('#resourceAllocIdrow'+id).val();
		var newRow = "<select name='entries["+id+"].ticketPriority' class='dd_cmn comboselect' id='ticketPriorities"+id+"'>";
		    newRow+="<option  value='' >Please Select</option>";   
		
		    $.getJSON("projectTickets/getActiveTicketPrioritiesByResourceAllocationId/"+resourceAllocatedId, function(data){		      
		           $.each(data, function(i, ticketPrior){
		        	   found = true;
		        	   if(ticketPrior.priority==ticketPriorityName){
		        		   newRow+="<option  value='" + ticketPrior.priority+"' selected=selected>"+ ticketPrior.priority+"</option>";
		        	   }else{
		        		   newRow+="<option  value='" + ticketPrior.priority+"' >"+ ticketPrior.priority+"</option>";
		        	   }
		        	   
		           });
		           newRow+= "</select>";
		        if(found){
		   			$("#ticketPriorities" + id).empty();
		   			$("#ticketPriorities" + id).replaceWith(newRow);
		   		}else{
		   			$('#ticketPriorities'+id).empty();
		   			var readOnlyProp = "";
		       		if(ticketPriorityName == '' || ticketPriorityName == null){
		       			readOnlyProp = 'readonly';
		       		}
		   			var htmlVar = '<input type="text" '+readOnlyProp+' id="ticketPriorities'+id+'" name="entries['+id+'].ticketPriority"  value="'+ticketPriorityName+'"></input>';
		   		      $("#ticketPriorities" + id)
		   			      .replaceWith(htmlVar);		
		   		}	
		 	});	  
		
} 

	// Ticket Priority and status validation by pankaj
	function validateTicketPriorityAndStatus(rowCount){
		var ticketPriorityFlag = false;
		var ticketStatusFlag = false;
		for (var i = 0; i<rowCount; i++) { 
		       var selectTicketPriority="#ticketPriorities"+i; 
	           var selectTicketStatus="#ticketStatus"+i;             
	           var ticketPriorityValue= $(selectTicketPriority).val(); 
	           var ticketStatusValue= $(selectTicketStatus).val();
	           console.log("ticket priority and status value is : "+ticketPriorityValue+" and "+ticketStatusValue);
	           var selectedText = $(selectTicketPriority).find('option:selected').text(); 
	           
	           var ticketPriorityLength = $(selectTicketPriority).find('option').length;
	           console.log("tickPriority length "+ticketPriorityLength+" and ticketPriority selected text : "+ticketPriorityValue);
	          
	           var ticketStatusLength = $(selectTicketStatus).find('option').length;
	           console.log("tickStatus length "+ticketStatusLength);
	          
	         	if(ticketPriorityLength > 1){
	         		if(ticketPriorityValue==null||ticketPriorityValue==''||ticketPriorityValue=='-1'){    
	         			ticketPriorityFlag = true;
	  					$(selectTicketPriority).css("border", "1px solid #ff0000");
	         	   	}else{
	         	   		$(selectTicketPriority).css("border", "");
	         	   	}
	         	} 
	          	if(ticketStatusLength > 1){
	          		if(ticketStatusValue==null||ticketStatusValue==''||ticketStatusValue=='-1'){    
	          			ticketStatusFlag = true;
	   					 $(selectTicketStatus).css("border", "1px solid #ff0000");
	          	   	}else{
	         	   		$(selectTicketStatus).css("border", "");
	         	   	}
	          	}

			}

	      if(ticketPriorityFlag) {
	    	    console.log("inside if when tciektpriorityFlag is : "+ticketPriorityFlag)        	
	        	stopProgress();
	        	var errorMsg = "Please Enter TicketPriority";
	    		showError(errorMsg);
	            return false;
	      }
	      if(ticketStatusFlag) {
	      	console.log("inside if when tciektStatusFlag is : "+ticketStatusFlag)        	
	      	stopProgress();
	      	var errorMsg = "Please Enter TicketStatus";
	  		showError(errorMsg);
	        return false;
	  	  }
	      
	      return true;
	}
/*-----------------function for container width end------------------*/