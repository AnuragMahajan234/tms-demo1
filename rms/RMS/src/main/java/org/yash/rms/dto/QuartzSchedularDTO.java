package org.yash.rms.dto;

public class QuartzSchedularDTO {
private String schedularName;
private String jobName;
private String groupName;
private String triggerName;
private String className;
private String methodName;
private String cronExpression;
private Integer priority;
private String executeClassName;

public String getSchedularName() {
	return schedularName;
}
public void setSchedularName(String schedularName) {
	this.schedularName = schedularName;
}
public String getJobName() {
	return jobName;
}
public void setJobName(String jobName) {
	this.jobName = jobName;
}
public String getGroupName() {
	return groupName;
}
public void setGroupName(String groupName) {
	this.groupName = groupName;
}
public String getTriggerName() {
	return triggerName;
}
public void setTriggerName(String triggerName) {
	this.triggerName = triggerName;
}
public String getClassName() {
	return className;
}
public void setClassName(String className) {
	this.className = className;
}
public String getMethodName() {
	return methodName;
}
public void setMethodName(String methodName) {
	this.methodName = methodName;
}
public String getCronExpression() {
	return cronExpression;
}
public void setCronExpression(String cronExpression) {
	this.cronExpression = cronExpression;
}
public Integer getPriority() {
	return priority;
}
public void setPriority(Integer priority) {
	this.priority = priority;
}
public String getExecuteClassName() {
	return executeClassName;
}
public void setExecuteClassName(String executeClassName) {
	this.executeClassName = executeClassName;
}

}
