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

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author deepti.gupta
 *
 */
@SuppressWarnings("serial")

@Entity
@Table(name="release_note")
@NamedQueries({
		@NamedQuery(name = ReleaseNotes.SEARCH_RELEASENOTES_BASED_ON_ID, query = ReleaseNotes.QUERY_RELEASENOTES_SEARCH_BASED_ON_ID)
		  })
public class ReleaseNotes implements Serializable{
	
	public final static String SEARCH_RELEASENOTES_BASED_ON_ID="SEARCH_RELEASENOTES_BASED_ON_ID";
	public final static String QUERY_RELEASENOTES_SEARCH_BASED_ON_ID="from ReleaseNotes c where c.id = :id";
	
	 
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="ReleaseDate")
	private Date releaseDate;
	
	@Column(name="ReleaseNumber")
	private String releaseNumber;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="Type")
	private String	type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getReleaseNumber() {
		return releaseNumber;
	}

	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}