package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Entity
@Table(name = "rework_ost")
public class Rework implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3608801893156658311L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ca_ticket_id")
	private CATicket caTicket;

	@Column(name = "rework_type")
	private String reworkType;

	@Column(name = "start_date_timestamp")
	private Date startDateTimestamp;

	@Column(name = "end_date_timestamp")
	private Date endDateTimestamp;

	@Column(name = "hours")
	private int hourse;

	@Column(name = "justified")
	private String justified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CATicket getCaTicket() {
		return caTicket;
	}

	public void setCaTicket(CATicket caTicket) {
		this.caTicket = caTicket;
	}

	public String getReworkType() {
		return reworkType;
	}

	public void setReworkType(String reworkType) {
		this.reworkType = reworkType;
	}

	public Date getStartDateTimestamp() {
		return startDateTimestamp;
	}

	public void setStartDateTimestamp(Date startDateTimestamp) {
		this.startDateTimestamp = startDateTimestamp;
	}

	public Date getEndDateTimestamp() {
		return endDateTimestamp;
	}

	public void setEndDateTimestamp(Date endDateTimestamp) {
		this.endDateTimestamp = endDateTimestamp;
	}

	public int getHourse() {
		return hourse;
	}

	public void setHourse(int hourse) {
		this.hourse = hourse;
	}

	public String getJustified() {
		return justified;
	}

	public void setJustified(String justified) {
		this.justified = justified;
	}

	public static String toJsonArray(
			Collection<org.yash.rms.domain.Rework> collection) {
		return new JSONSerializer()
				.include("id", "reworkType", "startDateTimestamp",
						"endDateTimestamp", "hourse", "justified")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN_NEW),
						Date.class).serialize(collection);
	}
}
