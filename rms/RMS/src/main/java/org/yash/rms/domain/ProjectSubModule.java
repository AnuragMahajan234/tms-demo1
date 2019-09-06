package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
@Entity
@Table(name = "project_sub_module")
@NamedQueries({
	@NamedQuery(name = "ProjectSubModule.findActiveSubmodulesByModuleAndProjectId", 
			query = "SELECT id,subModuleName FROM ProjectSubModule "
					+ "WHERE active=1 and module.id IN (SELECT id FROM ProjectModule WHERE active=1 AND moduleName=? AND projectId.id=?)")
})
public class ProjectSubModule implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "sub_module_name", length = 256, unique = true)
	//@NotNull
	private String subModuleName;

	@Column(name = "created_id", updatable = false)
	private String createdId;

	@Column(name = "creation_timestamp", updatable = false, insertable = true)
	private Date creationTimestamp;

	@Column(name = "lastupdated_id")
	private String lastUpdatedId;

	@Column(name = "lastupdated_timestamp")
	private Date lastupdatedTimestamp;

	@Column(name = "active")
	@NotNull
	private String active;

	@Transient
	private String bgbu;

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "module_id", referencedColumnName = "id", nullable = false, updatable=false)
	private ProjectModule module;

	public ProjectModule getModule() {
		return module;
	}

	public void setModule(ProjectModule module) {
		this.module = module;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getSubModuleName() {
		return subModuleName;
	}

	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}

	public String getBgbu() {
		return bgbu;
	}

	public void setBgbu(String bgbu) {
		this.bgbu = bgbu;
	}

	@Override
	public String toString() {
		return "ProjectSubModule [id=" + id + ", subModuleName=" + subModuleName + ", createdId=" + createdId + ", creationTimestamp=" + creationTimestamp + ", lastUpdatedId=" + lastUpdatedId
				+ ", lastupdatedTimestamp=" + lastupdatedTimestamp + ", activeIndicator=" + active + ", bgbu=" + bgbu + ", module=" + module + "]";
	}

}
