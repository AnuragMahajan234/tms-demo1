package org.yash.rms.domain;

import java.io.Serializable;

import javax.persistence.FetchType;

import java.util.Comparator;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.OneToMany;
@SuppressWarnings("serial")
@SQLDelete(sql = "UPDATE org_hierarchy SET active = '0' WHERE id= ?")
@FilterDef(name = "getActiverecord", parameters = @ParamDef(name = "active", type = "boolean"))
@Filter(name = "getActiverecord", condition = "active=:active")
@Entity
@Table(name = "org_hierarchy")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrgHierarchy implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	@JsonBackReference
	private OrgHierarchy parentId;

	@Column(name = "name", length = 256)
	@NotNull
	private String name;
	
	
	@OneToMany(mappedBy = "parentId", fetch = FetchType.LAZY)
	@Filter(name = "getActiverecord", condition = "active=:active")
	@JsonIgnore
	private Set<OrgHierarchy> orgHierarchies;
	 
	@OneToMany()
	@Filter(name = "getActiverecord", condition = "active=:active")
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	@JsonIgnore
	private Set<OrgHierarchy> childOrgHierarchies;

	@Column(name = "description", length = 256)
	private String description;
	
	@Column(name = "creation_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Date creationDate;

	public Set<OrgHierarchy> getOrgHierarchies() {
		return orgHierarchies;
	}

	public void setOrgHierarchies(Set<OrgHierarchy> orgHierarchies) {
		this.orgHierarchies = orgHierarchies;
	}

	@Column(name = "active")
	@NotNull
	private boolean active;

	@Column(name = "user")
	private Character user;

	@Column(name = "created_id", updatable = false)
	private String createdId;
	
	@Column(name = "lastupdated_id")
	private String lastUpdatedId;

	@Column(name = "lastupdated_timestamp")
	private Date lastupdatedTimestamp;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bussiness_head", referencedColumnName = "employee_id")
	@JsonBackReference
	private Resource employeeId;

	public Resource getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Resource employeeId) {
		this.employeeId = employeeId;
	}

	@Transient
	private boolean moveLink;
	

	@Transient
	private boolean parentDeactive;

	public boolean isParentDeactive() {
		return parentDeactive;
	}

	public void setParentDeactive(boolean parentDeactive) {
		this.parentDeactive = parentDeactive;
	}

	public boolean isMoveLink() {
		return moveLink;
	}

	public void setMoveLink(boolean moveLink) {
		this.moveLink = moveLink;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonIgnore
	public OrgHierarchy getParentId() {
		return parentId;
	}

	public void setParentId(OrgHierarchy parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Character getUser() {
		return user;
	}

	public void setUser(Character user) {
		this.user = user;
	}

	public String getCreatedId() {
		return createdId;
	}

	public void setCreatedId(String createdId) {
		this.createdId = createdId;
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

	public Set<OrgHierarchy> getChildOrgHierarchies() {
		return childOrgHierarchies;
	}

	public void setChildOrgHierarchies(Set<OrgHierarchy> childOrgHierarchies) {
		this.childOrgHierarchies = childOrgHierarchies;
	}
	
	public static class OrgHierarchyComparator implements Comparator<OrgHierarchy> {

	    public int compare(OrgHierarchy orgHierarchy1, OrgHierarchy orgHierarchy2) {
	    	
	    	String orgHierarchyStr1 = "";
	    	String orgHierarchyStr2 = "";
	    	
	      if (orgHierarchy1 == orgHierarchy2) {
	    	  
	        return 0;
	        
	      } else {
	        
	    	  if (orgHierarchy1.getName() != null) {
	    		  
	    		  orgHierarchyStr1 = orgHierarchy1.getParentId().getName() + "-" + orgHierarchy1.getName();
	    		  
	    		  orgHierarchyStr2 = orgHierarchy2.getParentId().getName() + "-" + orgHierarchy2.getName();
	          
	    		  return orgHierarchyStr1.compareTo(orgHierarchyStr2);
	        
	    	  } else if (orgHierarchy2.getName() != null) {
	    		  
	    		  orgHierarchyStr1 = orgHierarchy1.getParentId().getName() + "-" + orgHierarchy1.getName();
	    		  
	    		  orgHierarchyStr2 = orgHierarchy2.getParentId().getName() + "-" + orgHierarchy2.getName();
	          
	    		  return orgHierarchyStr2.compareTo(orgHierarchyStr1);
	        
	    	  } else {
	          
	    		  return 0;
	        }
	      }
	    }
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
		OrgHierarchy other = (OrgHierarchy) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
