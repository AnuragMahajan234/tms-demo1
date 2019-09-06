package org.yash.rms.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
 import javax.persistence.Table;
 import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "module")
@NamedQueries({
		@NamedQuery(name = Module.SEARCH_MODULE_BASED_ON_ID, query = Module.MODULE_QUERY_FOR_SEARCH_BASED_ON_ID),
		@NamedQuery(name = Module.MODULE_DELETE_BASED_ON_ID, query = Module.MODULE_QUERY_FOR_DELETE_BASED_ON_ID) })
public class Module {
	public final static String SEARCH_MODULE_BASED_ON_ID = "SEARCH_MODULE_BASED_ON_ID";
	public final static String MODULE_QUERY_FOR_SEARCH_BASED_ON_ID = "from Module b where b.id = :id";

	public final static String MODULE_DELETE_BASED_ON_ID = "DELETE_MODULE_BASED_ON_ID";
	public final static String MODULE_QUERY_FOR_DELETE_BASED_ON_ID = "DELETE Module b where b.id = :id";

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

}
