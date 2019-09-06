package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class denotes table in db. Such that, it maps pdl_ids from pdl table and
 * list of resource_id that are associated with it.
 * 
 * @author samiksha.sant
 *
 */



@SuppressWarnings("serial")
@Entity(name= "")
@Table(name = "pdl_resource_mapping")
public class PDLAndResourceMapping implements Serializable{

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name= "pdl_id")
	private Integer pdl_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_id", referencedColumnName = "employee_id")
	private Resource resourceID;
	
	@Column(name= "created_ts")
	private Date createdDate;
	
	@Column(name= "created_by")
	private Integer createdBy;

	public Integer getPdl_id() {
		return pdl_id;
	}

	public void setPdl_id(Integer pdl_id) {
		this.pdl_id = pdl_id;
	}

	public Resource getResourceID() {
		return resourceID;
	}

	public void setResourceID(Resource resourceID) {
		this.resourceID = resourceID;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

}
