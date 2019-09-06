package org.yash.rms.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.json.JSONArray;





















public class ProjectDTO
{
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Integer projectId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String invoiceByName;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private OrgHierarchyDTO orgHierarchy;
  @JsonIgnore
  private String BuCode;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String customerCode;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String projectName;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String customerName;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
  private boolean customerType;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String offshoreManager;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String onsiteManager;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String engagementMode;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String projectKickOffDate;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String projectEndDate;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Integer plannedProjectSize;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String currencyName;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Integer plannedProjectCost;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private TeamDTO team;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
  public boolean managerReadonly;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private List<ActivitiesDTO> activities;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private List<ModuleDTO> moduleDTO;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String projectCode;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Integer resourceAllocationId;
  private List<ProjectTicketPriorityDTO> ticketPriorities;
  private List<ProjectTicketStatusDTO> ticketStatus;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String deliveryManager;
  
  public ProjectDTO() {}
  
  public List<ProjectTicketPriorityDTO> getTicketPriorities()
  {
    return ticketPriorities;
  }
  
  public void setTicketPriorities(List<ProjectTicketPriorityDTO> ticketPriorities) {
    this.ticketPriorities = ticketPriorities;
  }
  
  public List<ProjectTicketStatusDTO> getTicketStatus() {
    return ticketStatus;
  }
  
  public void setTicketStatus(List<ProjectTicketStatusDTO> ticketStatus) {
    this.ticketStatus = ticketStatus;
  }
  
  public boolean isManagerReadonly() {
    return managerReadonly;
  }
  
  public void setManagerReadonly(boolean managerReadonly) {
    this.managerReadonly = managerReadonly;
  }
  
  public Integer getProjectId() {
    return projectId;
  }
  
  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }
  
  public String getInvoiceByName() {
    return invoiceByName;
  }
  
  public void setInvoiceByName(String invoiceByName) {
    this.invoiceByName = invoiceByName;
  }
  
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  public String getBuCode() {
    return BuCode;
  }
  
  public void setBuCode(String buCode) {
    BuCode = buCode;
  }
  
  public String getCustomerCode() {
    return customerCode;
  }
  
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }
  
  @JsonIgnore
  public String getProjectCode()
  {
    return 
      (getInvoiceByName() == null ? "" : getInvoiceByName()) + ((orgHierarchy == null) || (orgHierarchy.getName() == null) ? "" : orgHierarchy.getName()) + getCustomerCode() + getProjectId();
  }
  
  public String getProjectName() {
    return projectName;
  }
  
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }
  
  public String getCustomerName() {
    return customerName;
  }
  
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }
  
  public boolean isCustomerType() {
    return customerType;
  }
  
  public void setCustomerType(boolean customerType) {
    this.customerType = customerType;
  }
  
  public String getOffshoreManager() {
    return offshoreManager;
  }
  
  public void setOffshoreManager(String offshoreManager) {
    this.offshoreManager = offshoreManager;
  }
  
  public String getOnsiteManager() {
    return onsiteManager;
  }
  
  public void setOnsiteManager(String onsiteManager) {
    this.onsiteManager = onsiteManager;
  }
  
  public String getEngagementMode() {
    return engagementMode;
  }
  
  public void setEngagementMode(String engagementMode) {
    this.engagementMode = engagementMode;
  }
  
  public String getProjectKickOffDate() {
    return projectKickOffDate;
  }
  
  public void setProjectKickOffDate(String projectKickOffDate) {
    this.projectKickOffDate = projectKickOffDate;
  }
  
  public Integer getPlannedProjectSize() {
    return plannedProjectSize;
  }
  
  public void setPlannedProjectSize(Integer plannedProjectSize) {
    this.plannedProjectSize = plannedProjectSize;
  }
  
  public String getCurrencyName() {
    return currencyName;
  }
  
  public void setCurrencyName(String currencyName) {
    this.currencyName = currencyName;
  }
  
  public Integer getPlannedProjectCost() {
    return plannedProjectCost;
  }
  
  public void setPlannedProjectCost(Integer plannedProjectCost) {
    this.plannedProjectCost = plannedProjectCost;
  }
  
  public OrgHierarchyDTO getOrgHierarchy() {
    return orgHierarchy;
  }
  
  public void setOrgHierarchy(OrgHierarchyDTO orgHierarchy) {
    this.orgHierarchy = orgHierarchy;
  }
  
  public String getProjectEndDate() {
    return projectEndDate;
  }
  
  public void setProjectEndDate(String projectEndDate) {
    this.projectEndDate = projectEndDate;
  }
  
  public TeamDTO getTeam()
  {
    return team;
  }
  
  public void setTeam(TeamDTO team)
  {
    this.team = team;
  }
  
  public List<ActivitiesDTO> getActivities()
  {
    return activities;
  }
  
  public void setActivities(List<ActivitiesDTO> activities)
  {
    this.activities = activities;
  }
  
  public List<ModuleDTO> getModuleDTO() {
    return moduleDTO;
  }
  
  public void setModuleDTO(List<ModuleDTO> moduleDTO) {
    this.moduleDTO = moduleDTO;
  }
  
  public void setProjectCode(String projectCode) {
    this.projectCode = projectCode;
  }
  
  public Integer getResourceAllocationId() {
    return resourceAllocationId;
  }
  
  public void setResourceAllocationId(Integer resourceAllocationId) {
    this.resourceAllocationId = resourceAllocationId;
  }
  
  public String getDeliveryManager() {
	return deliveryManager;
}

public void setDeliveryManager(String deliveryManager) {
	this.deliveryManager = deliveryManager;
}

public static List<JSONArray> toJsonArray(Collection<ProjectDTO> projects)
  {
    DateFormat dateFormatOld = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat dateFormatNew = new SimpleDateFormat("dd-MMM-yyyy");
    
    List dataArray = new ArrayList();
    
    for (ProjectDTO project : projects) {
      JSONArray object = new JSONArray();
      object.put(0, Integer.toString(project.getProjectId().intValue()));
      object.put(1, project.getProjectCode());
      object.put(2, project.getProjectName());
      object.put(3, project.getCustomerName());
      object.put(4, project.getOffshoreManager());
      object.put(5, project.getOnsiteManager());
      object.put(6, project.getBuCode());
      object.put(7, project.getEngagementMode());
      
      String formattedProjectKickOffDate = "";
      try
      {
        Date projectKickOffDate = dateFormatOld.parse(project.getProjectKickOffDate());
        formattedProjectKickOffDate = dateFormatNew.format(projectKickOffDate);
      } catch (Exception e) {
        formattedProjectKickOffDate = "";
      }
      
      object.put(8, formattedProjectKickOffDate);
      
      if (project.getPlannedProjectSize() != null) {
        object.put(9, Integer.toString(project.getPlannedProjectSize().intValue()));
      }
      
      String formattedProjectEndDate = "";
      try
      {
        Date projectEndDate = dateFormatOld.parse(project.getProjectEndDate());
        formattedProjectEndDate = dateFormatNew.format(projectEndDate);
      } catch (Exception e) {
        formattedProjectEndDate = "";
      }
      
      object.put(10, formattedProjectEndDate);
      object.put(11, project.getCurrencyName());
      object.put(12, project.isManagerReadonly());
      object.put(13, project.getResourceAllocationId());
      object.put(14, project.getDeliveryManager());
      dataArray.add(object);
    }
    
    return dataArray;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ProjectDTO other = (ProjectDTO)obj;
    if (projectName == null) {
      if (projectName != null)
        return false;
    } else if (!projectName.equals(projectName))
      return false;
    return true;
  }
}
