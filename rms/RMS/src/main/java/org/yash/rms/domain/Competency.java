/**
 * 
 */
package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


@SuppressWarnings("serial")

@Entity
@Table(name="competency")
@NamedQueries({
	@NamedQuery(
			name = Competency.SEARCH_Competency_BASED_ON_ID,
			query = Competency.QUERY_FOR_SEARCH_Competency_BASED_ON_ID
			),
			@NamedQuery(
					name = Competency.DELETE_Competency_BASED_ON_ID,
					query = Competency.QUERY_FOR_DELETE_Competency_BASED_ON_ID
					)
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Competency implements Serializable{
	
	public final static String SEARCH_Competency_BASED_ON_ID="SEARCH_Competency_BASED_ON_ID";
	public final static String QUERY_FOR_SEARCH_Competency_BASED_ON_ID="from Competency o where o.id = :id";
	
	public final static String DELETE_Competency_BASED_ON_ID="DELETE_Competency_BASED_ON_ID";
	public final static String QUERY_FOR_DELETE_Competency_BASED_ON_ID="DELETE Competency o where o.id = :id";
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="skill")
	private String skill;
	
	

	@Column(name="created_id", updatable = false)
	private String createdId;
	
	@Column(name="creation_timestamp",updatable = false, insertable = true)
	private Date creationTimestamp;
	
	@Column(name="lastupdated_id")
	private String	lastUpdatedId;
	
	@Column(name="lastupdated_timestamp")
	private Date lastupdatedTimestamp;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the createdId
	 */
	

	
	
	public String getCreatedId() {
		return createdId;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	/**
	 * @param createdId the createdId to set
	 */
	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}


	/**
	 * @return the creationTimestamp
	 */
	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	/**
	 * @param creationTimestamp the creationTimestamp to set
	 */
	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	/**
	 * @return the lastUpdatedId
	 */
	public String getLastUpdatedId() {
		return lastUpdatedId;
	}

	/**
	 * @param lastUpdatedId the lastUpdatedId to set
	 */
	public void setLastUpdatedId(String lastUpdatedId) {
		this.lastUpdatedId = lastUpdatedId;
	}

	/**
	 * @return the lastupdatedTimestamp
	 */
	public Date getLastupdatedTimestamp() {
		return lastupdatedTimestamp;
	}

	/**
	 * @param lastupdatedTimestamp the lastupdatedTimestamp to set
	 */
	public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
		this.lastupdatedTimestamp = lastupdatedTimestamp;
	}

}
