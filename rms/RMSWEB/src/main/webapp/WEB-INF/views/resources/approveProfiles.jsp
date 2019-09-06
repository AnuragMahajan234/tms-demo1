<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />
<title>Insert title here</title>
<script type="text/javascript">
  $(document).ready(function() {
	
 	 pendingTable= $('#pendingAppprovalProfilesTable').dataTable({
		 "sAjaxDataProp": "",
		 "iDisplayLength":5,
			"bPaginate": true,
	 		"bProcessing": true,
	 		"bStateSave": false,
	 		"bPaginate": true,
	 		"sAjaxSource": "/rms/resources/unapprovedProfiles",
	 		"aoColumns": [
	 		      /*          { "mRender" : function ( data, type, full ) {
          	   return '<input type="checkbox"  name="yashEmpId" value="'+full.yashEmpId+'">'+full.yashEmpId+'</input>' ;}
			    	// return '<select name="status"><option value="approve">Approve</option><option value="reject">Reject</option><option value="review">Review</option></select> ';}
			       },  */ 
	 	 
	 		 { 
	 			'mDataProp': 'yashEmpId'
	 		}, 
	 		{ 
	 			'mDataProp': 'employeeName'
	 		},
	 		{ 
	 			'mDataProp': 'designationId.designationName'
	 		},
	 		{ 
	 			'mDataProp': 'currentReportingManager.employeeName'
	 		},
	 		 { "mRender" : function ( data, type, full ) {
           	  //  return '<input type="checkbox"  name="status" value="approve">Approve</input>   <input type="checkbox" name="status" value="reject">Reject</input>';}
	 			  
		    	 return ' <input type="button" value="Approve" class="approveprofile"  > ';}
		       }
	 		],
	 		"fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull) {
	 			 
	    	    $(nRow).attr("id", aData.yashEmpId);
	    	 
	    	    return nRow;
	    	  }
	 } );  
 	$('.approveprofile').live('click', function (e) {
 		
 		var nRow = $(this).parents('tr')[0];
		var yashEmpId = $(nRow).attr('id');
		 
		 $.ajax({
				async:true,
				type: "POST",
			     
				url: "/rms/resources/approveProfile/"+yashEmpId,
			 
				success: function( data ) {
					showSuccess(yashEmpId+" Profile successfully approved");
					pendingTable.fnDeleteRow( nRow );
					//var ss = pendingTable.fnDeleteRow( nRow,null,true );
						//	alert(ss);	 
				},
				error: function(data)
				{
					showError("Profile not approved.");
					
				}
			});
		
		
	});
 		
/* 	 $(".approveprofile").click(function(event) {
		 alert("inside class");
			var nRow = $(this).parents('tr')[0];
			var yashEmpId = $(nRow).attr('id');
			alert("tr"+yashEmpId);
			 $.ajax({
					async:true,
					type: "POST",
				     
					url: "/rms/resources/approveProfile/"+yashEmpId,
				 
					success: function( data ) {
						alert("table..."+nRow);
						_row = $(this).parents()[0];
						alert("_row"+_row);
						pendingTable.fnDeleteRow(_row);
						//var ss = pendingTable.fnDeleteRow( nRow,null,true );
							//	alert(ss);	 
					}
				});
		}); */
	
});
function approveCheckedProfile()
{
	var yashEmpId=[];
	//alert("called");
	$(':checkbox:checked').each(function(i){
		yashEmpId[i] = $(this).val();
	     // alert(yashEmpId[i]);
	    });
	}
/*   function approveProfile(yashEmpId)
	 {
		// alert("Hello"+yashEmpId);
		 $.ajax({
				async:true,
				type: "POST",
			     
				url: "/rms/resources/approveProfile/"+yashEmpId,
			 
				success: function( data ) {
					// alert("table..."+nRow);
					_row = $(this).parents()[0];
					//alert("_row"+_row);
					
					pendingTable.fnDeleteRow(_row); 
					//var ss = pendingTable.fnDeleteRow( nRow,null,true );
						//	alert(ss);	 
				}
			});
		 
	 } */
</script>
</head>
<body>
<table id="pendingAppprovalProfilesTable" class="dataTable dataTbl"   cellpadding="0" cellspacing="0" border="0" >
 <thead>
 
 <tr>
 
 <th >Yash Employee ID</th>
  <th>Employee Name</th>
   <th>Designation</th>
   <th>Current RM</th>
   <th>Approve Profiles</th>
 </tr>
 </thead>
 <tbody>
 
 
 </tbody>
 </table>
<!--  <input type="submit" value="Approve Checked Profile"   onclick="approveCheckedProfile()"> -->
</body>
</html>