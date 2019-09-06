<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>REQUEST REQUISITION</title>
</head>

<body style="background:url(http://www.yash.com/images/RnR/mail_background.png);">
<table style="width:600px" align="center" cellpadding="0" cellspacing="0">
<tr>
	<td  style="text-align:center;border-bottom: 4px solid #3377bb;color: #0070c0;font-weight:bold;font-family: 'calibri';font-size:32px;background: #FFF;">
    	<img src="http://www.yash.com/images/partners-logo/YASH-slogan.png">	
    </td>
</tr>
<tr>
<td>
<table cellpadding="20" cellspacing="0" border="0" style="border-collapse:collapse;border:1px solid #d8d8d8; width:600px">

<tr  style="padding:0px 20px;background:#F9F9F9;border: 1px solid #d8d8d8;">
<td>
	
    <table cellpadding="0" cellspacing="0" width="100%">
    	<tr>
        	<td style="font-size:14px;font-weight:bold;font-family: 'calibri';color:#787878;padding:10px 0px;margin:0;">Hello, </td>
        </tr>
        <tr style="border: 1px solid #d8d8d8;border-radius:3px;background:#fff;margin-top:10px;">
        	<td>
            <table cellpadding="5" cellspacing="5" width="100%">
            	<tr>
                	<td  style="color: #3377bb;text-align:center;font-family: 'calibri';font-size:22px;font-weight:normal;border-bottom: 1px solid #3377bb;padding: 10px 0;margin: 0;">Request Requisition</td>
                </tr>
                <tr>
                	<td  style="font-family: 'calibri';font-size:14px;line-height:1.5; color:#787878;padding:10px 20px; border-bottom: 1px solid #d8d8d8;margin:0;"><b>Indent By: </b>${sender} </td>
                </tr>
                <tr>
                	<td  style="font-family: 'calibri';font-size:14px;line-height:1.5; color:#787878;padding:10px 20px; border-bottom: 1px solid #d8d8d8;margin:0;"><b>ProjectName: </b>${projectName} </td>
                </tr>
                <tr>
                	<td  style="font-family: 'calibri';font-size:14px;line-height:1.5; color:#787878;padding:10px 20px; border-bottom: 1px solid #d8d8d8;margin:0;"><b>Project Unit : </b>${projectBU} </td>
                </tr>
                <tr>
                	<td  style="font-family: 'calibri';font-size:14px;line-height:1.5; color:#787878;padding:10px 20px; border-bottom: 1px solid #d8d8d8;margin:0;"><b>Client Name : </b>${clientName} </td>
                </tr>
                 <tr>
                	<td  style="font-family: 'calibri';font-size:14px;line-height:1.5; color:#787878;padding:10px 20px; border-bottom: 1px solid #d8d8d8;margin:0;"><b>Group Name : </b>${groupName} </td>
                </tr>
                
            </table>
            
            <table cellpadding="5" cellspacing="5" width="100%">
            	<tr>
	            	<th style="color: #3377bb;text-align:center;font-family: 'calibri';font-size:16px;font-weight:bold;border-bottom: 1px solid #3377bb;padding: 10px 0;margin: 0;">Skills</th>
	            	<th style="color: #3377bb;text-align:center;font-family: 'calibri';font-size:16px;font-weight:bold;border-bottom: 1px solid #3377bb;padding: 10px 0;margin: 0;">No. of Resources  </th>
	            	<th style="color: #3377bb;text-align:center;font-family: 'calibri';font-size:16px;font-weight:bold;border-bottom: 1px solid #3377bb;padding: 10px 0;margin: 0;">Designation</th>
	            	<th style="color: #3377bb;text-align:center;font-family: 'calibri';font-size:16px;font-weight:bold;border-bottom: 1px solid #3377bb;padding: 10px 0;margin: 0;">Experience</th>
	            	<th style="color: #3377bb;text-align:center;font-family: 'calibri';font-size:16px;font-weight:bold;border-bottom: 1px solid #3377bb;padding: 10px 0;margin: 0;">Billable</th>
	            	<th style="color: #3377bb;text-align:center;font-family: 'calibri';font-size:16px;font-weight:bold;border-bottom: 1px solid #3377bb;padding: 10px 0;margin: 0;">Type</th>
	            	<th style="color: #3377bb;text-align:center;font-family: 'calibri';font-size:16px;font-weight:bold;border-bottom: 1px solid #3377bb;padding: 10px 0;margin: 0;">Customized Requirement ID</th>
	                <th style="color: #3377bb;text-align:center;font-family: 'calibri';font-size:16px;font-weight:bold;border-bottom: 1px solid #3377bb;padding: 10px 0;margin: 0;">Primary Skills</th>
	            	
            	</tr>
            	
            	<#list requestMailList as req>
				<tr>
				    	
				    	<td  style="font-family: 'calibri';text-align:center;font-size:14px;line-height:1.5; color:#787878;padding:10px 20px; border-bottom: 1px solid #d8d8d8;margin:0;">
				    		${req.getSkill().getSkill()}
						</td>
				    	<td  style="font-family: 'calibri';text-align:center;font-size:14px;line-height:1.5; color:#787878;padding:10px 20px; border-bottom: 1px solid #d8d8d8;margin:0;">
				    		${req.getNoOfResources()}
				    	</td>
				    	<td  style="font-family: 'calibri';text-align:center;font-size:14px;line-height:1.5; color:#787878;padding:10px 20px; border-bottom: 1px solid #d8d8d8;margin:0;">
				    		${req.getDesignation().getDesignationName()}
						</td>
				    	<td  style="font-family: 'calibri';text-align:center;font-size:14px;line-height:1.5; color:#787878;padding:10px 20px; border-bottom: 1px solid #d8d8d8;margin:0;">
							${req.getExperience()}
						</td>
 						<td  style="font-family: 'calibri';text-align:center;font-size:14px;line-height:1.5; color:#787878;padding:10px 20px; border-bottom: 1px solid #d8d8d8;margin:0;">
							
							</td>
						<td  style="font-family: 'calibri';text-align:center;font-size:14px;line-height:1.5; color:#787878;padding:10px 20px; border-bottom: 1px solid #d8d8d8;margin:0;">
							
								${req.allocationType.allocationType}
							
						</td>
				    	<td  style="font-family: 'calibri';text-align:center;font-size:14px;line-height:1.5; color:#787878;padding:10px 20px; border-bottom: 1px solid #d8d8d8;margin:0;">
								${req.getRequirementId()}
						</td>
						<td  style="font-family: 'calibri';text-align:center;font-size:14px;line-height:1.5; color:#787878;padding:10px 20px; border-bottom: 1px solid #d8d8d8;margin:0;">
							${req.getPrimarySkills()} </td>
						</tr>
				</#list>
   		</table>
   			<tr>
   			<#if comments??>
                	<td  style="font-family: 'calibri';font-size:14px;line-height:1.5; color:#787878;padding:10px 20px; border-bottom: 1px solid #d8d8d8;margin:0;"><b>Comments : </b> ${comments}  </td>
			</#if>                
                </tr>
   		
				
				
   				
         <tr>
   <td style="font-family: 'calibri';color:#787878;font-size:14px;margin-top:10px;padding:10px 0px 0;min-height:30px;">Regards,</td>
   </tr>     

<tr>
   <td style="font-family: 'calibri';color:#787878;font-size:14px;margin:0;padding:0 0 10px;min-height:30px;"> ${sender} </td>
   </tr> 
        
    </table>
    
    
 </td>

 </tr>  
    	
</table>


</td>
</tr>
</table>
</body>
</html>
