<html>
<head>
  <meta name="generator" content="HTML Tidy for Linux/x86 (vers 11 February 2007), see www.w3.org" />
  <title>Loan Transfer Acknowledgment</title>
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
			<tr >
	<td style="padding-left:10px; padding-top: 0px; padding-right:0px; padding-bottom:10px;" >
		<h5> Hi ${previousIRM}, <br/>
		Requesting you to please update highlighted information in Infogram for below resource.
		</h5>
		
	</td>
</tr>
<tr>
	<td style="padding-left:10px; padding-top: 10px; padding-right:10px; padding-bottom:10px;background-color:#FEEFB3; color:#9F6000; border:1px solid #9F6000; font-weight:bold;">	
		Resource Data  for ${userName} has been updated.
		<br />
	</td>
</tr>
</table>

<table  style="width:650px; margin-top:10px; margin-right:10px; margin-bottom:10px; margin-left:10px;  background-color:#fff; color:#000;" cellspacing="4" cellpadding="2">
		<#if basicInfo?has_content>
			<tr style="font-weight:bold;" >
				<td style="background-color:#DEE7EE; font-weight:bold;">${basicInfo.getEmployeeName()}</td>
				<td style="background-color:#DEE7EE; font-weight:bold;">${basicInfo.getYashEmpId()}</td>
				<td style="background-color:#DEE7EE; font-weight:bold;">${basicInfo.getEmailId()}</td>
			</tr>
		</#if>
</table>

<#if information?has_content>
<table  style="width:650px; margin-top:10px; margin-right:10px; margin-bottom:10px; margin-left:10px;  background-color:#fff; color:#000;"   border="1" >
<tr style="background-color:#CEC9AB; font-weight:bold;">
<td>Attribute</td>
<td>Prior Info</td>
<td>New/Updated Info</td>

<#list information?keys as key> 
 
<tr>
<td style="background-color:#DEE7EE; font-weight:bold;"> ${key}</td>
<td>
${information[key][0]}

</td>


<#assign first=information[key][0]>
<#assign second=information[key][1]>
<#if first ==second>
<td>
${information[key][1]}
</td>
<#else>
<td style=" background-color: yellow;">
${information[key][1]}
</td>
</#if>


 
</tr>
</#list> 
</table>
<table> 
   </#if>
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