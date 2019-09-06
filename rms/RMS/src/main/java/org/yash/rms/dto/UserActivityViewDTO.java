package org.yash.rms.dto;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import java.util.Collection;
import java.util.Date;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;



































public class UserActivityViewDTO
{
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Integer id;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String module;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String subModule;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String ticketNo;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Double d1Hours;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Double d2Hours;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Double d3Hours;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Double d4Hours;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Double d5Hours;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Double d6Hours;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Double d7Hours;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String d1Comment;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String d2Comment;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String d3Comment;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String d4Comment;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String d5Comment;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String d6Comment;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String d7Comment;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Date weekStartDate;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Date weekEndDate;
  @JsonProperty("resourceAllocation")
  private ResourceAllocationDTO resourceAllocId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  @JsonProperty("activity")
  private ActivitiesDTO activityId;
  @JsonProperty("customActivity")
  private CustomActivityDTO customActivityId;
  @JsonProperty("resource")
  private ResourceDTO employeeId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Character status;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Double repHrsForProForWeekEndDate;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Double plannedHrs;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Double billedHrs;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String remarks;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Integer timeHrsId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
  private boolean viewFlag;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String rejectionRemarks;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String approveCode;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String rejectCode;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String ticketPriority;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String ticketStatus;
  
  public UserActivityViewDTO() {}
  
  public String getTicketPriority()
  {
    return ticketPriority;
  }
  
  public void setTicketPriority(String ticketPriority)
  {
    this.ticketPriority = ticketPriority;
  }
  
  public String getTicketStatus()
  {
    return ticketStatus;
  }
  
  public void setTicketStatus(String ticketStatus)
  {
    this.ticketStatus = ticketStatus;
  }
  
  public Integer getId()
  {
    return id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public ResourceDTO getEmployeeId() {
    return employeeId;
  }
  
  public void setEmployeeId(ResourceDTO employeeId)
  {
    this.employeeId = employeeId;
  }
  
  public Double getRepHrsForProForWeekEndDate() {
    return repHrsForProForWeekEndDate;
  }
  
  public void setRepHrsForProForWeekEndDate(Double repHrsForProForWeekEndDate) {
    this.repHrsForProForWeekEndDate = repHrsForProForWeekEndDate;
  }
  
  public Double getPlannedHrs() {
    return plannedHrs;
  }
  
  public void setPlannedHrs(Double plannedHrs) {
    this.plannedHrs = plannedHrs;
  }
  
  public Double getBilledHrs() {
    return billedHrs;
  }
  
  public void setBilledHrs(Double billedHrs) {
    this.billedHrs = billedHrs;
  }
  
  public String getRemarks() {
    return remarks;
  }
  
  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
  
  public Integer getTimeHrsId() {
    return timeHrsId;
  }
  
  public void setTimeHrsId(Integer timeHrsId) {
    this.timeHrsId = timeHrsId;
  }
  
  public boolean isViewFlag() {
    return viewFlag;
  }
  
  public void setViewFlag(boolean viewFlag) {
    this.viewFlag = viewFlag;
  }
  
  public String getRejectionRemarks() {
    return rejectionRemarks;
  }
  
  public void setRejectionRemarks(String rejectionRemarks) {
    this.rejectionRemarks = rejectionRemarks;
  }
  
  public String getApproveCode() {
    return approveCode;
  }
  
  public void setApproveCode(String approveCode) {
    this.approveCode = approveCode;
  }
  
  public String getRejectCode() {
    return rejectCode;
  }
  
  public void setRejectCode(String rejectCode) {
    this.rejectCode = rejectCode;
  }
  
  public Date getWeekStartDate() {
    return weekStartDate;
  }
  
  public void setWeekStartDate(Date weekStartDate) {
    this.weekStartDate = weekStartDate;
  }
  
  public Date getWeekEndDate() {
    return weekEndDate;
  }
  
  public void setWeekEndDate(Date weekEndDate) {
    this.weekEndDate = weekEndDate;
  }
  
  public String getModule() {
    return module;
  }
  
  public void setModule(String module) {
    this.module = module;
  }
  
  public String getSubModule() {
    return subModule;
  }
  
  public void setSubModule(String subModule) {
    this.subModule = subModule;
  }
  
  public String getTicketNo() {
    return ticketNo;
  }
  
  public void setTicketNo(String ticketNo) {
    this.ticketNo = ticketNo;
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
  
  public ActivitiesDTO getActivityId()
  {
    return activityId;
  }
  
  public void setActivityId(ActivitiesDTO activityId)
  {
    this.activityId = activityId;
  }
  
  public CustomActivityDTO getCustomActivityId()
  {
    return customActivityId;
  }
  
  public void setCustomActivityId(CustomActivityDTO customActivityId)
  {
    this.customActivityId = customActivityId;
  }
  
  public Character getApproveStatus() {
    return status;
  }
  
  public void setStatus(Character approveStatus) {
    status = Character.valueOf(approveStatus == null ? 'N' : approveStatus.charValue());
  }
  
  public static String toJsonArray(Collection<UserActivityViewDTO> collection)
  {
    return 
    


      new JSONSerializer().include(new String[] { "id", "employeeIdHidden", "employeeId.employeeId", "employeeId.yashEmpId", "resourceAllocId.id", "resourceAllocId.allocEndDate", "resourceAllocId.projectId.projectName", "employeeId.employeeId", "employeeId.employeeName", "behalfManager", "employeeId.designationId.designationName", "weekEndDate", "plannedHrs", "submitStatus", "billedHrs", "remarks", "approveStatus", "repHrsForProForWeekEndDate", "approved", "activityId.id", "customActivityId.id", "weekStartDate", "userAction", "weekStartDateHidden", "weekEndDateHidden", "timeHrsId", "viewFlag", "rejectionRemarks" }).exclude(new String[] { "*" }).transform(new DateTransformer("MM/dd/yyyy"), new Class[] { Date.class }).serialize(collection);
  }
  
  public ResourceAllocationDTO getResourceAllocId()
  {
    return resourceAllocId;
  }
  
  public void setResourceAllocId(ResourceAllocationDTO resourceAllocId)
  {
    this.resourceAllocId = resourceAllocId;
  }
}
