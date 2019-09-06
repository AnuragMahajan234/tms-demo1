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
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "project_module")
public class ProjectModule implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "module_name", length = 256, unique = true)
    @NotNull
    private String moduleName;
    
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
    private boolean active;
    
    public boolean isActive() {
      return active;
    }

    public void setActive(boolean active) {
      this.active = active;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private Project projectId;

    public Project getProjectId() {
      return projectId;
    }

    public void setProjectId(Project projectId) {
      this.projectId = projectId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
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

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public String toString() {
      return "ProjectModule [id=" + id + ", moduleName=" + moduleName + ", createdId=" + createdId
          + ", creationTimestamp=" + creationTimestamp + ", lastUpdatedId=" + lastUpdatedId
          + ", lastupdatedTimestamp=" + lastupdatedTimestamp + ", active=" + active + ", projectId="
          + projectId + "]";
    }
    
    

}
