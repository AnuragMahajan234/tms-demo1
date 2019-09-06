package org.yash.rms.form;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.yash.rms.dto.Module;
public class NewTimeSheet {

	private String d1Comment;
	private String d2Comment;
	private String d3Comment;
	private String d4Comment;
	private String d5Comment;
	private String d6Comment;
	private String d7Comment;
	
	private Double d1Hours;
	private Double d2Hours;
	private Double d3Hours;
	private Double d4Hours;
	private Double d5Hours;
	private Double d6Hours;
	private Double d7Hours;
	private Character approveStatus;
	private boolean submitStatus;
	

	private Integer resourceAllocId;
	private String module;

	private String subModule;

	private String ticketNo;
	private String activityId;
	private String activityType;
	private Integer customActivityId;
	private Integer employeeId;
	private Integer id;
	
	private String rejectionRemarks;
	
	private Double repHrsForProForWeekEndDate;
	
	private Date resourceAllocEndDate;
	
	private Date resourceAllocStartDate;
	
	private String projectName;
	
	private Date weekEndDate;
	
	private Date weekStartDate;
	
	private String yashEmpId;
	
	private String employeeName;

	// Ticket Priority and Status Changes Start
	private String ticketPriority;

	private String ticketStatus;
	
	public String getTicketPriority() {
		return ticketPriority;
	}

	public void setTicketPriority(String ticketPriority) {
		this.ticketPriority = ticketPriority;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	
	
	// Ticket Priority and Status Changes END
	


	private Boolean viewFlag;
	public Integer getResourceAllocId() {
		return resourceAllocId;
	}
	
	public Character getApproveStatus() {
		return approveStatus;
	}
	
	public void setApproveStatus(Character approveStatus) {
		 this.approveStatus = approveStatus == null ? 'N' : approveStatus;
		//this.approveStatus = approveStatus;
	}
	
	public void setResourceAllocId(Integer resourceAllocId) {
		this.resourceAllocId = resourceAllocId;
	}
	
	public NewTimeSheet() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getActivityId() {
		return activityId;
	}
	
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	public String getD1Comment() {
		return d1Comment;
	}
	
	public void setD1Comment(String d1Comment) {

		if(d1Comment!=null && !d1Comment.isEmpty() )
		  this.d1Comment = d1Comment.trim(); 
		else
		  this.d1Comment = d1Comment;
	}
	
	public String getD2Comment() {
		return d2Comment;
	}
	
	public void setD2Comment(String d2Comment) {
		if(d2Comment!=null && !d2Comment.isEmpty() )
			  this.d2Comment = d2Comment.trim(); 
		else
		      this.d2Comment = d2Comment;
	}
	
	public String getD3Comment() {
		return d3Comment;
	}
	
	public void setD3Comment(String d3Comment) {

		if(d3Comment!=null && !d3Comment.isEmpty() )
			  this.d3Comment = d3Comment.trim(); 
		else
		      this.d3Comment = d3Comment;
	}
	
	public String getD4Comment() {
		return d4Comment;
	}
	
	public void setD4Comment(String d4Comment) {

		if(d4Comment!=null && !d4Comment.isEmpty() )
			  this.d4Comment = d4Comment.trim(); 
		else
		      this.d4Comment = d4Comment;
	}
	
	public String getD5Comment() {
		return d5Comment;
	}
	
	public void setD5Comment(String d5Comment) {

		if(d5Comment!=null && !d5Comment.isEmpty() )
			  this.d5Comment = d5Comment.trim(); 
		else
		      this.d5Comment = d5Comment;
	}
	
	public String getD6Comment() {
		return d6Comment;
	}

	public void setD6Comment(String d6Comment) {

		if(d6Comment!=null && !d6Comment.isEmpty() )
			  this.d6Comment = d6Comment.trim(); 
		else
		      this.d6Comment = d6Comment;
	}

	public String getD7Comment() {
		return d7Comment;
	}

	public void setD7Comment(String d7Comment) {

		if(d7Comment!=null && !d7Comment.isEmpty() )
			  this.d7Comment = d7Comment.trim(); 
		else
		      this.d7Comment = d7Comment;
	}

	public Double getD1Hours() {
		return d1Hours;
	}

	public void setD1Hours(Double d1Hours) {
		this.d1Hours = d1Hours;
	}

	public Double getD2Hours() {
		return d2Hours;
	}

	public void setD2Hours(Double d2Hours) {
		this.d2Hours = d2Hours;
	}

	public Double getD3Hours() {
		return d3Hours;
	}

	public void setD3Hours(Double d3Hours) {
		this.d3Hours = d3Hours;
	}

	public Double getD4Hours() {
		return d4Hours;
	}

	public void setD4Hours(Double d4Hours) {
		this.d4Hours = d4Hours;
	}

	public Double getD5Hours() {
		return d5Hours;
	}

	public void setD5Hours(Double d5Hours) {
		this.d5Hours = d5Hours;
	}

	public Double getD6Hours() {
		return d6Hours;
	}

	public void setD6Hours(Double d6Hours) {
		this.d6Hours = d6Hours;
	}

	public Double getD7Hours() {
		return d7Hours;
	}

	public void setD7Hours(Double d7Hours) {
		this.d7Hours = d7Hours;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public boolean isSubmitStatus() {
		return submitStatus;
	}

	public void setSubmitStatus(boolean submitStatus) {
		this.submitStatus = submitStatus;
	}

	public Integer getCustomActivityId() {
		return customActivityId;
	}

	public void setCustomActivityId(Integer customActivityId) {
		this.customActivityId = customActivityId;
	}

	public String getSubModule() {

		return subModule;
	}

	public void setSubModule(String subModule) {

		this.subModule = subModule;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getRejectionRemarks() {
		return rejectionRemarks;
	}

	public void setRejectionRemarks(String rejectionRemarks) {
		this.rejectionRemarks = rejectionRemarks;
	}

	public Double getRepHrsForProForWeekEndDate() {
		return repHrsForProForWeekEndDate;
	}

	public void setRepHrsForProForWeekEndDate(Double repHrsForProForWeekEndDate) {
		this.repHrsForProForWeekEndDate = repHrsForProForWeekEndDate;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getWeekEndDate() {
		return weekEndDate;
	}

	public void setWeekEndDate(Date weekEndDate) {
		this.weekEndDate = weekEndDate;
	}

	public Date getWeekStartDate() {
		return weekStartDate;
	}

	public void setWeekStartDate(Date weekStartDate) {
		this.weekStartDate = weekStartDate;
	}

	public Date getResourceAllocEndDate() {
		return resourceAllocEndDate;
	}

	public void setResourceAllocEndDate(Date resourceAllocEndDate) {
		this.resourceAllocEndDate = resourceAllocEndDate;
	}

	public Date getResourceAllocStartDate() {
		return resourceAllocStartDate;
	}

	public void setResourceAllocStartDate(Date resourceAllocStartDate) {
		this.resourceAllocStartDate = resourceAllocStartDate;
	}

	public String getYashEmpId() {
		return yashEmpId;
	}

	public void setYashEmpId(String yashEmpId) {
		this.yashEmpId = yashEmpId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Boolean getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(Boolean viewFlag) {
		this.viewFlag = viewFlag;
	}
}
