package org.yash.rms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 
 * @author samiksha.sant
 *
 */
@Entity
@Table(name = "shift_types")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShiftTypes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "shift_timings")
	private String shiftTimings;
	
	@Column(name = "timezone_name")
	private String timezone;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShiftTimings() {
		return shiftTimings;
	}

	public void setShiftTimings(String shiftTimings) {
		this.shiftTimings = shiftTimings;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	
	

}
