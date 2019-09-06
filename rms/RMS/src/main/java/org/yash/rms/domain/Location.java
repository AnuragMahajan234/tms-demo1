/**
 * 
 */
package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * @author ankita.shukla
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "LOCATION")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQueries({
		@NamedQuery(name = Location.SEARCH_LOCATION_BASED_ON_ID, query = Location.QUERY_FOR_SEARCH_LOCATION_BASED_ON_ID),
		@NamedQuery(name = Location.DELETE_LOCATION_BASED_ON_ID, query = Location.QUERY_FOR_DELETE_LOCATION_BASED_ON_ID) })
public class Location implements Serializable {

	public final static String SEARCH_LOCATION_BASED_ON_ID = "SEARCH_LOCATION_BASED_ON_ID";
	public final static String QUERY_FOR_SEARCH_LOCATION_BASED_ON_ID = "from Location l where l.id = :id";

	public final static String DELETE_LOCATION_BASED_ON_ID = "DELETE_LOCATION_BASED_ON_ID";
	public final static String QUERY_FOR_DELETE_LOCATION_BASED_ON_ID = "DELETE Location l where l.id = :id";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "locationhr_email_id", length = 256)
	@NotNull
	private String locationHrEmailId;

	@Column(name = "location")
	private String location;

	@Column(name = "created_id", updatable = false)
	private String createdId;

	@Column(name = "creation_timestamp", updatable = false, insertable = true)
	private Date creationTimestamp;

	@Column(name = "lastupdated_id")
	private String lastUpdatedId;

	@Column(name = "lastupdated_timestamp")
	private Date lastupdatedTimestamp;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the createdId
	 */
	public String getCreatedId() {
		return createdId;
	}

	/**
	 * @param createdId
	 *            the createdId to set
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
	 * @param creationTimestamp
	 *            the creationTimestamp to set
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
	 * @param lastUpdatedId
	 *            the lastUpdatedId to set
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
	 * @param lastupdatedTimestamp
	 *            the lastupdatedTimestamp to set
	 */
	public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
		this.lastupdatedTimestamp = lastupdatedTimestamp;
	}

	public String getLocationHrEmailId() {
		return locationHrEmailId;
	}

	public void setLocationHrEmailId(String locationHrEmailId) {
		this.locationHrEmailId = locationHrEmailId;
	}

	public static String toJsonArray(Collection<Location> collection) {
		return new JSONSerializer()
				.include("id", "location")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN),
						Date.class).serialize(collection);
	}
	
	public static class LocationNameComparator implements Comparator<Location> {
		
	    public int compare(Location location1, Location location2) {
	    	
	      if (location1 == location2) {
	    	  
	        return 0;
	        
	      } else {
	    	  
	        if (location1.getLocation() != null) {
	        	
	          return location1.getLocation().compareTo(location2.getLocation());
	          
	        } else if (location2.getLocation() != null) {
	        	
	          return location2.getLocation().compareTo(location1.getLocation());
	          
	        } else {
	        	
	          return 0;
	        }
	      }
	    }
	  }

}
