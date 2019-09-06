package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "project_category")
@NamedQueries({ @NamedQuery(name = ProjectCategory.DELETE_PROJECTCATEGORY_BASED_ON_ID, query = ProjectCategory.QUERY_PROJECTCATEGORY_DELETE_BASED_ON_ID) })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProjectCategory implements Serializable {
	
	  @OneToMany(mappedBy = "projectCategoryId", cascade = CascadeType.ALL)
	 private Set<Project> projects;
	 

	public final static String DELETE_PROJECTCATEGORY_BASED_ON_ID = "DELETE_PROJECTCATEGORY_BASED_ON_ID";
	public final static String QUERY_PROJECTCATEGORY_DELETE_BASED_ON_ID = "DELETE ProjectCategory p where p.id = :id";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "category", length = 256)
	private String category;

	@Column(name = "created_id", updatable = false)
	private String createdId;

	@Column(name = "creation_timestamp",updatable = false, insertable = true)
	private Date creationTimestamp;

	@Column(name = "lastupdated_id")
	private String lastUpdatedId;

	@Column(name = "lastupdated_timestamp")
	private Date lastupdatedTimestamp;

	
		 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
}
