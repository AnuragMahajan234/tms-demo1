package org.yash.rms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
@Entity
@Table(name="GRADE")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQueries({
	
	@NamedQuery(name = Grade.DELETE_GRADE_BASED_ON_ID, query = Grade.QUERY_GRADE_DELETE_BASED_ON_ID) })

public class Grade {
	public final static String DELETE_GRADE_BASED_ON_ID="DELETE_GRADE_BASED_ON_ID";
	public final static String QUERY_GRADE_DELETE_BASED_ON_ID="DELETE Grade b where b.id = :id";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="grade")
	private String grade;
	
	@Column(name="created_id", updatable = false)
	private String createdId;
	
	@Column(name="creation_timestamp",updatable = false, insertable = true)
	private java.util.Date creationTimeStamp;
	
	@Column(name="lastupdated_id")
	private String lastUpdatedId;
	
	@Column(name="lastupdated_timestamp")
	private java.util.Date lastUpdatedTimeStamp;

	public String getCreatedId() {
		return createdId;
	}
	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}
	public java.util.Date getCreationTimeStamp() {
		return creationTimeStamp;
	}
	public void setCreationTimeStamp(java.util.Date creationTimeStamp) {
		this.creationTimeStamp = creationTimeStamp;
	}
	public String getLastUpdatedId() {
		return lastUpdatedId;
	}
	public void setLastUpdatedId(String lastUpdatedId) {
		this.lastUpdatedId = lastUpdatedId;
	}
	public java.util.Date getLastUpdatedTimeStamp() {
		return lastUpdatedTimeStamp;
	}
	public void setLastUpdatedTimeStamp(java.util.Date lastUpdatedTimeStamp) {
		this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
	}
	public String getGrade() {
		return grade;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}


}
