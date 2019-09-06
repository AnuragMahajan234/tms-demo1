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

 </style>
</head>
<body>

<h5>User ${userName} (${userEmpId}) had ${status} the following time-sheet on ${currentDate?string("MM-dd-yyyy HH:mm:ss")} for Project ${projectName} (please take care of future dates or dated timesheets (before past 2 weeks), when you approve/ reject)</h5></font>

<table border="0" cellspacing="0" cellpadding="0">
	<tr>
	  	<th width="50%">Week Start Date: </th> ${weekStartDate?date}
		<th width="50%">Week End Date: </th> ${weekEndDate?date}
	</tr>
	<tr>
		<td>To Approve this timesheet Click: <a href="${approvalURL}"> <img src="${approveImageUrl}" /> </a></td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td>To Reject this Timesheet Click:<a href="${denyURL}"> <img src="${rejectImageUrl}"/> </a></td>
	</tr>
	<tr><td>&nbsp</td></tr>
	<tr><th colspan="2"></th></tr>
</table>
<table width="80%" cellspacing="1" cellpadding="2" id="tblActivity" style="background-color: #FFFFFF; border: 1px solid #9B9B9B; text-align: left; width: 100%;">
<thead>
<tr>
	<th width="5%" rowspan="2" align="left" valign="middle" style="background-color: #3377BB;  color: #FFFFFF; cursor: pointer; font-size: 11px; font-weight: bold; padding: 3px 18px 3px 10px;">Project</th>
	<th width="5%" rowspan="2" align="left" valign="middle" style="background-color: #3377BB;  color: #FFFFFF; cursor: pointer; font-size: 11px; font-weight: bold; padding: 3px 18px 3px 10px;">Activity</th>
	<th width="5%" rowspan="2" align="left" valign="middle" style="background-color: #3377BB;  color: #FFFFFF; cursor: pointer; font-size: 11px; font-weight: bold; padding: 3px 18px 3px 10px;">Module</th>
	<th width="5%" rowspan="2" align="left" valign="middle" style="background-color: #3377BB;  color: #FFFFFF; cursor: pointer; font-size: 11px; font-weight: bold; padding: 3px 18px 3px 10px;">Ticket No.</th>
	<th width="5%" rowspan="2" align="left" valign="middle" style="background-color: #3377BB;  color: #FFFFFF; cursor: pointer; font-size: 11px; font-weight: bold; padding: 3px 18px 3px 10px;">Ticket Priority</th>
	<th width="5%" rowspan="2" align="left" valign="middle" style="background-color: #3377BB;  color: #FFFFFF; cursor: pointer; font-size: 11px; font-weight: bold; padding: 3px 18px 3px 10px;">Ticket Status</th>
	<th colspan="9" align="center" valign="middle" style="background-color: #3377BB; border-bottom: 0 solid #fff; color: #FFFFFF; cursor: pointer; font-size: 11px; font-weight: bold; padding: 3px 18px 3px 10px;">Week</th>
</tr>
<tr>
	<th width="2%" align="center" valign="middle" style="background-color: #3377BB; color: #FFFFFF; cursor: pointer; font-size: 11px; font-weight: bold; padding: 3px 18px 3px 10px;">Sunday</th>
	<th width="2%" align="center" valign="middle" style="background-color: #3377BB;  color: #FFFFFF; cursor: pointer; font-size: 11px; font-weight: bold; padding: 3px 18px 3px 10px;">Monday</th>
	<th width="2%" align="center" valign="middle" style="background-color: #3377BB;  color: #FFFFFF; cursor: pointer; font-size: 11px; font-weight: bold; padding: 3px 18px 3px 10px;">Tuesday</th>
	<th width="2%" align="center" valign="middle" style="background-color: #3377BB;  color: #FFFFFF; cursor: pointer; font-size: 11px; font-weight: bold; padding: 3px 18px 3px 10px;">Wednesday</th>
	<th width="2%" align="center" valign="middle" style="background-color: #3377BB;  color: #FFFFFF; cursor: pointer; font-size: 11px; font-weight: bold; padding: 3px 18px 3px 10px;">Thursday</th>
	<th width="2%" align="center" valign="middle" style="background-color: #3377BB;  color: #FFFFFF; cursor: pointer; font-size: 11px; font-weight: bold; padding: 3px 18px 3px 10px;">Friday</th>
	<th width="2%" align="center" valign="middle" style="background-color: #3377BB;  color: #FFFFFF; cursor: pointer; font-size: 11px; font-weight: bold; padding: 3px 18px 3px 10px;">Saturday</th>
</tr>
</thead>

 <#list activityList as activity>
<tbody>
   <tr style="background-color:#DEE7EE; border-bottom:1px solid #ffffff"> 
   
   
     <td align="left">${activity.getResourceAllocId().getProjectId().getProjectName()}</td>
     <td align="left">${activity.getActivityId().getActivityName()}</td>

    <#if activity.getModule()?has_content>
     <td align="left">${activity.getModule()}</td>
   <#else>
    <td>N/A</td>
   </#if>
   
   <#if activity.getTicketNo()?has_content>
     <td align="left">${activity.getTicketNo()}</td>
   <#else>
    <td>N/A</td>
   </#if>
   
   <#if activity.getTicketPriority()?has_content>
     <td align="left">${activity.getTicketPriority()}</td>
   <#else>
    <td>N/A</td>
   </#if>
   <#if activity.getTicketStatus()?has_content>
     <td align="left">${activity.getTicketStatus()}</td>
   <#else>
    <td>N/A</td>
   </#if>

   <#if activity.getD1Hours()?has_content>
    <td align="center" valign="middle">${activity.getD1Hours()}  (${activity.getD1Comment()})</td>
   <#else>
    <td align="center" valign="middle"> 0 </td>
   </#if>
   
   <#if activity.getD2Hours()?has_content>
    <td align="center" valign="middle">${activity.getD2Hours()}  (${activity.getD2Comment()})</td>
   <#else>
    <td align="center" valign="middle"> 0  </td>
   </#if>
  
   <#if activity.getD3Hours()?has_content>
    <td align="center" valign="middle">${activity.getD3Hours()}  (${activity.getD3Comment()})</td>
   <#else>
    <td align="center" valign="middle"> 0  </td>
   </#if>
  
   <#if activity.getD4Hours()?has_content>
    <td align="center" valign="middle">${activity.getD4Hours()}  (${activity.getD4Comment()})</td>
   <#else>
    <td align="center" valign="middle"> 0  </td>
   </#if>
  
   <#if activity.getD5Hours()?has_content>
    <td align="center" valign="middle">${activity.getD5Hours()}  (${activity.getD5Comment()})</td>
   <#else>
    <td align="center" valign="middle"> 0  </td>
   </#if>
  
   <#if activity.getD6Hours()?has_content>
    <td align="center" valign="middle">${activity.getD6Hours()}  (${activity.getD6Comment()})</td>
   <#else>
    <td align="center" valign="middle"> 0  </td>
   </#if>
  
   <#if activity.getD7Hours()?has_content>
    <td align="center" valign="middle">${activity.getD7Hours()}  (${activity.getD7Comment()})</td>
   <#else>
    <td align="center" valign="middle"> 0  </td>
   </#if>

   </tr>
    </#list>
</tbody>


</table>
* Note: Default value of Billable and Planned Hours will be 40.

<! -- Common Functions -->
<#function zebra index>
  <#if (index % 2) == 0>
    <#return "#DEE7EE" />
  <#else>
    <#return "#EBEBEB" />
  </#if>
</#function>
</body>
</html>