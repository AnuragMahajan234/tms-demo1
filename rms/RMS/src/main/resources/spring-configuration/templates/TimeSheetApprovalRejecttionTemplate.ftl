 


<html>
<head>
  <meta name="generator" content="HTML Tidy for Linux/x86 (vers 11 February 2007), see www.w3.org" />
  <title>RMS Timesheet ${status}</title>
   <style type="text/css">
	 *
	{
		margin: 0px;padding: 0px; 
	}
	body{margin:0; padding:0; background-color:#fff; color:#000000;font-size:12px; font-family:Tahoma,Verdana, Arial, Helvetica, sans-serif;}

 </style>
</head>
<body>
<table style="width:675px; margin:0px auto; background-color:#3377BB; color:#ffffff;" cellspacing="1" cellpadding="2" id="tblActivity">
	<tr>
		<td>
			<table style="width:650px; margin-top:10px; margin-right:10px; margin-bottom:10px; margin-left:10px;  background-color:#fff; color:#000;" cellspacing="4" cellpadding="2">
			<tr>
	<td style="padding-left:10px; padding-top: 0px; padding-right:0px; padding-bottom:10px;">
		<h5> Hi ${firstName}, </h5>
	</td>
</tr>
<tr>
	<td style="padding-left:10px; padding-top: 10px; padding-right:10px; padding-bottom:10px;background-color:#FEEFB3; color:#9F6000; border:1px solid #9F6000; font-weight:bold;">	
		Your Time Sheet for the week of ${weekStartDate?date} to ${weekEndDate?date} has been ${status} by ${userName}
		<br />
		<#if (remarks)?has_content >
		 Remarks : ${remarks}
		</#if>
	</td>
</tr>
	


<tr>
	<td style="padding-left:10px; padding-top: 10px; padding-right:10px; padding-bottom:10px;">
		<h5>Thanks and regards,<br/>
		RMS Team</h5>
	</td>
</tr>
			</table>
		</td>
	</tr>
	
	
</table>

</body>
</html>