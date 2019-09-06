/**
 * 
 */
package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * @author arpan.badjatiya
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PROJECT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Project implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "customer_contacts", length = 256)
  private String customerContacts;

  @Column(name = "deere")
  @NotNull
  private boolean deere;

  @Column(name = "onsite_del_mgr", length = 256)
  private String onsiteDelMgr;

  @Column(name = "planned_proj_cost")
  private Integer plannedProjCost;

  @Column(name = "planned_proj_size")
  private Integer plannedProjSize;

  @Column(name = "project_kick_off")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(style = "M-")
  private Date projectKickOff;

  @Column(name = "project_name", length = 256, unique = true)
  @NotNull
  private String projectName;

  @Column(name = "project_end_date")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(style = "M-")
  private Date projectEndDate;

  @Column(name = "description", length = 256)
  private String description;

  @Column(name = "created_id", updatable = false)
  private String createdId;

  @Column(name = "creation_timestamp", updatable = false, insertable = true)
  private Date creationTimestamp;//

  @Column(name = "lastupdated_id")
  private String lastUpdatedId;//

  @Column(name = "lastupdated_timestamp")
  private Date lastupdatedTimestamp;
  
  @Column(name = "module_ost", length = 256) 
  private String moduleName;
  
  @OneToMany(mappedBy = "projectId") // , fetch = FetchType.LAZY,cascade =// CascadeType.ALL,orphanRemoval=true)
  @JsonIgnore
  private Set<ProjectModule> module;                                  
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_tracking_currency_id", referencedColumnName = "id")
  @JsonBackReference
  private Currency projectTrackingCurrencyId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "offshore_del_mgr", referencedColumnName = "employee_id")
  @JsonBackReference
  private Resource offshoreDelMgr;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "delivery_mgr", referencedColumnName = "employee_id")
  @JsonBackReference
  private Resource deliveryMgr;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "engagement_model_id", referencedColumnName = "id")
  @JsonBackReference
  private EngagementModel engagementModelId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_name_id", referencedColumnName = "id", nullable = false)
  @JsonBackReference
  private Customer customerNameId;

  public DefaultProject getDefaultProject() {
    return defaultProject;
  }

  public void setDefaultProject(DefaultProject defaultProject) {
    this.defaultProject = defaultProject;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bu_id", referencedColumnName = "id")
  @JsonBackReference
  private OrgHierarchy orgHierarchy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", referencedColumnName = "id")
  @JsonBackReference
  private Team team;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_category_id", referencedColumnName = "id")
  @JsonBackReference
  private ProjectCategory projectCategoryId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "invoice_by_id", referencedColumnName = "id")
  @JsonBackReference
  private InvoiceBy invoiceById;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_methodology_id", referencedColumnName = "id")
  @JsonBackReference
  private ProjectMethodology projectMethodologyId;

  @OneToMany(mappedBy = "projectId", fetch = FetchType.LAZY)
  @JsonBackReference
  private Set<ProjectPo> projectPoes;


  @OneToOne(mappedBy = "projectId", cascade = CascadeType.ALL,orphanRemoval=true)
  @JsonBackReference
  private DefaultProject defaultProject;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cust_group_id", referencedColumnName="group_id")
  @JsonBackReference
  private CustomerGroup customerGroup;

  @Column(name = "is_trainee_project") 
  @NotNull
  private boolean traineeProject;
  
  @Column(name="project_name_sow")
  private String projectNameSOW;
 
  public CustomerGroup getCustomerGroup() {
		return customerGroup;
  }
	
  public void setCustomerGroup(CustomerGroup customerGroup) {
		this.customerGroup = customerGroup;
  }

  @Transient
  private boolean managerReadonly;

  // User Story #2731
  @Transient
  private boolean defaultActivitiesId;


  public boolean isDefaultActivitiesId() {
    return defaultActivitiesId;
  }

  public void setDefaultActivitiesId(boolean defaultActivitiesId) {
    this.defaultActivitiesId = defaultActivitiesId;
  }

  // End User Story #2731
  public boolean isManagerReadonly() {
    return managerReadonly;
  }

  public void setManagerReadonly(boolean managerReadonly) {
    this.managerReadonly = managerReadonly;
  }

  public Project(Integer id) {}

  public Project() {
    super();
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCustomerContacts() {
    return customerContacts;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public void setCustomerContacts(String customerContacts) {
    this.customerContacts = customerContacts;
  }

  public boolean isDeere() {
    return deere;
  }

  public void setDeere(boolean deere) {
    this.deere = deere;
  }

  public String getOnsiteDelMgr() {
    return onsiteDelMgr;
  }

  public void setOnsiteDelMgr(String onsiteDelMgr) {
    this.onsiteDelMgr = onsiteDelMgr;
  }

  public Integer getPlannedProjCost() {
    return plannedProjCost;
  }

  public void setPlannedProjCost(Integer plannedProjCost) {
    this.plannedProjCost = plannedProjCost;
  }

  public Integer getPlannedProjSize() {
    return plannedProjSize;
  }

  public void setPlannedProjSize(Integer plannedProjSize) {
    this.plannedProjSize = plannedProjSize;
  }

  public Date getProjectKickOff() {
    return projectKickOff;
  }

  public void setProjectKickOff(Date projectKickOff) {
    this.projectKickOff = projectKickOff;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public Date getProjectEndDate() {
    return projectEndDate;
  }

  public void setProjectEndDate(Date projectEndDate) {
    this.projectEndDate = projectEndDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Currency getProjectTrackingCurrencyId() {
    return projectTrackingCurrencyId;
  }

  public void setProjectTrackingCurrencyId(Currency projectTrackingCurrencyId) {
    this.projectTrackingCurrencyId = projectTrackingCurrencyId;
  }

  public Resource getOffshoreDelMgr() {
    return offshoreDelMgr;
  }

  public void setOffshoreDelMgr(Resource offshoreDelMgr) {
    this.offshoreDelMgr = offshoreDelMgr;
  }

  public EngagementModel getEngagementModelId() {
    return engagementModelId;
  }

  public void setEngagementModelId(EngagementModel engagementModelId) {
    this.engagementModelId = engagementModelId;
  }

  public Customer getCustomerNameId() {
    return customerNameId;
  }

  public void setCustomerNameId(Customer customerNameId) {
    this.customerNameId = customerNameId;
  }

  public ProjectCategory getProjectCategoryId() {
    return projectCategoryId;
  }

  public void setProjectCategoryId(ProjectCategory projectCategoryId) {
    this.projectCategoryId = projectCategoryId;
  }

  public InvoiceBy getInvoiceById() {
    return invoiceById;
  }

  
   public String getModuleName() { return moduleName; }
    
    public void setModuleName(String moduleName) { this.moduleName = moduleName; }
   

  public void setInvoiceById(InvoiceBy invoiceById) {
    this.invoiceById = invoiceById;
  }

  public ProjectMethodology getProjectMethodologyId() {
    return projectMethodologyId;
  }

  public void setProjectMethodologyId(ProjectMethodology projectMethodologyId) {
    this.projectMethodologyId = projectMethodologyId;
  }

  public Set<ProjectPo> getProjectPoes() {
    return projectPoes;
  }

  public void setProjectPoes(Set<ProjectPo> projectPoes) {
    this.projectPoes = projectPoes;
  }

  public String getProjectCode() {
    return ((this.getInvoiceById() == null || this.getInvoiceById().getName() == null) ? ""
        : this.getInvoiceById().getName())
        + ((this.getOrgHierarchy() == null || this.getOrgHierarchy().getName() == null) ? ""
            : this.getOrgHierarchy().getName())
        + this.getCustomerNameId().getCode() + this.getId();
  }

  public OrgHierarchy getOrgHierarchy() {
    return orgHierarchy;
  }

  public void setOrgHierarchy(OrgHierarchy orgHierarchy) {
    this.orgHierarchy = orgHierarchy;
  }

  public String getCreatedId() {
    return createdId;
  }

  public void setCreatedId(String createdId) {
    this.createdId = createdId;
  }

  public Date getCreationTimestamp() {
    return creationTimestamp;
  }

  public void setCreationTimestamp(Date creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }

  public String getLastUpdatedId() {
    return lastUpdatedId;
  }

  public void setLastUpdatedId(String lastUpdatedId) {
    this.lastUpdatedId = lastUpdatedId;
  }

  public Date getLastupdatedTimestamp() {
    return lastupdatedTimestamp;
  }

  public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
    this.lastupdatedTimestamp = lastupdatedTimestamp;
  }

  public static String toJsonArray(Collection<Project> collection) {
    return new JSONSerializer().include("id", "projectName").exclude("*")
        .transform(new DateTransformer(Constants.DATE_PATTERN), Date.class).serialize(collection);
  }

  public Set<ProjectModule> getModule() {
    return module;
  }

  public void setModule(Set<ProjectModule> module) {
    this.module = module;
  }
  
	public boolean getTraineeProject() {
		return traineeProject;
	}

	public void setTraineeProject(boolean traineeProject) {
		this.traineeProject = traineeProject;
	}
	

	public String getProjectNameSOW() {
		return projectNameSOW;
	}

	public void setProjectNameSOW(String projectNameSOW) {
		this.projectNameSOW = projectNameSOW;
	}

@Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Project other = (Project) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }



  public static class ProjectIdComparator implements Comparator<Project> {

    public int compare(Project o1, Project o2) {
      if (o1 == o2) {
        return 0;
      } else {
        if (o1 != null && o2 != null) {
          return o2.getId() - o1.getId();
        }
        return 0;
      }
    }
  }

  public static class ProjectNameComparator implements Comparator<Project> {

    public int compare(Project o1, Project o2) {
      if (o1 == o2) {
        return 0;
      } else {
        if (o1.getProjectName() != null) {
          return o1.getProjectName().compareTo(o2.getProjectName());
        } else if (o2.getProjectName() != null) {
          return o2.getProjectName().compareTo(o1.getProjectName());
        } else {
          return 0;
        }
      }
    }
  }

public Resource getDeliveryMgr() {
	return deliveryMgr;
}

public void setDeliveryMgr(Resource deliveryMgr) {
	this.deliveryMgr = deliveryMgr;
}
}
