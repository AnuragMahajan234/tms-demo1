package org.yash.rms.form;



public class TimeSheet {
	
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
	private String ticketNo;
	private String ticketPriority;
	private String ticketStatus;
	private Integer activityId;
	private Integer customActivityId;
	private Integer id;
	
	
	
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
	
	public TimeSheet() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getActivityId() {
		return activityId;
	}
	
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	
	public String getD1Comment() {
		return d1Comment;
	}
	
	public void setD1Comment(String d1Comment) {
		this.d1Comment = d1Comment;
	}
	
	public String getD2Comment() {
		return d2Comment;
	}
	
	public void setD2Comment(String d2Comment) {
		this.d2Comment = d2Comment;
	}
	
	public String getD3Comment() {
		return d3Comment;
	}
	
	public void setD3Comment(String d3Comment) {
		this.d3Comment = d3Comment;
	}
	
	public String getD4Comment() {
		return d4Comment;
	}
	
	public void setD4Comment(String d4Comment) {
		this.d4Comment = d4Comment;
	}
	
	public String getD5Comment() {
		return d5Comment;
	}
	
	public void setD5Comment(String d5Comment) {
		this.d5Comment = d5Comment;
	}
	
	public String getD6Comment() {
		return d6Comment;
	}

	public void setD6Comment(String d6Comment) {
		this.d6Comment = d6Comment;
	}

	public String getD7Comment() {
		return d7Comment;
	}

	public void setD7Comment(String d7Comment) {
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

}
