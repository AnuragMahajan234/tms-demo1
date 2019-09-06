<html>
<head>
  <meta name="generator" content="HTML Tidy for Linux/x86 (vers 11 February 2007), see www.w3.org" />
  <title>RMS Timesheet Status</title>
   <style type="text/css">
	 *
	{
		margin: 0px;padding: 0px; 
	}
	body{margin:0; padding:0; background-color:#fff; color:#000000;font-size:12px; font-family:Tahoma,Verdana, Arial, Helvetica, sans-serif;}
	a{color:#02498B}
 </style>
</head>
<body>
<table style="width:675px; margin:0px auto; background-color:#3377BB; color:#ffffff;" cellspacing="1" cellpadding="2" id="tblActivity">
	<tr>
		<td>
			<table style="width:650px; margin-top:10px; margin-right:10px; margin-bottom:10px; margin-left:10px;  background-color:#fff; color:#000;" cellspacing="4" cellpadding="2">
			<tr>
				<td colspan="3">
					
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<h5> User ${userName} (${userEmpId}) had edited profile on ${currentDate?string("MM-dd-yyyy HH:mm:ss")} </h5>
				</td>
	</tr>
	<tr>	
		<td colspan="3">
			<strong>You can either Approve or Deny by clicking any of the following URLs.</strong>
		</td>
	<tr>  	
		<td align="right" valign="middle" width="30%"><strong>Approval Link:</strong> </td>
		<td>${approvalURL}</td> 
	</tr>
	<tr>  	
		<td align="right" valign="middle" width="30%"><strong>Denied Link:</strong> </td>
		<td> ${denyURL}</td>
	</tr>
	<tr>  	
		<td align="left" valign="middle" colspan="3"><h4 style="color:#3377BB">User Profile Details </h4></td>
	</tr>
	<tr>  	
		<td align="right" valign="middle"><strong>First Name: </strong></td>
		<td align="left">
			<#if profile.getFirstName()??>
				${profile.getFirstName()} 
			</#if>
		</td>
	</tr>
	<tr>		
		<td align="right" valign="middle"><strong>Last Name: </strong></td>
		<td align="left">	
			<#if profile.getLastName()??>
				${profile.getLastName()} 
			</#if>
		</td>
		</tr>
	<tr>
		<td align="right" valign="middle"><strong>EMail ID:</strong> </td>	
		<td align="left">
			<#if profile.getEmailId()??>
				${profile.getEmailId()} 
			</#if>
		</td>	
		</tr>
	
	<tr>  	
		<td align="right" valign="middle"><strong>Contact Number 1: </strong></td>
		<td align="left">
			<#if profile.getContactNumberOne()??>
				${profile.getContactNumberOne()} 
			</#if>
		</td>
	</tr>
	<tr>		
		<td align="right" valign="middle"><strong>Contact Number 2:  </strong></td>
		<td align="left">
			<#if profile.getContactNumberTwo()??>
				${profile.getContactNumberTwo()} 
			</#if>  
		</td>
	</tr>
	<tr>	
		<td align="right" valign="middle"><strong>Customer User ID Details:</strong></td> 
		<td align="left">
			<#if profile.getCustomerIdDetail()??>
				${profile.getCustomerIdDetail()} 
			</#if>
		</td>	  
		</tr>
		
		<tr>  
		<td align="right" valign="middle"><strong>Primary Skills: </strong></td>
			<td>
				<#assign seq = primarySkills!>
				<#list seq as x>
				<#assign skill = x.getSkillId()!>
				  ${x_index + 1}. ${skill.skill}<#if x_has_next>,</#if>
				</#list>
			</td> 
		</tr>
	
		<tr>  
		<td align="right" valign="middle"><strong>Secondary Skills: </strong></td>
			<td>
				<#assign seq1 = secondarySkills!>
		<#list seq1 as x>
		<#assign skillSec = x.getSkillId()!>
		  ${x_index + 1}. ${skillSec.skill}<#if x_has_next>,</#if>
		</#list> 
			</td>
		</tr>
			</table>
		</td>
	</tr>
	
	
</table>
</body>
</html>