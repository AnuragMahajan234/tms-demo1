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
@Table(name = "t3_contribution_screen_ost")
public class T3Contribution implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 535492055521032519L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ca_ticket_id")
	private CATicket caTicket;

	public CATicket getCaTicket() {
		return caTicket;
	}

	public void setCaTicket(CATicket caTicket) {
		this.caTicket = caTicket;
	}

	@Column(name = "ticket_no")
	private int ticketNumber;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "module") private Project module;
	 */

	@Column(name = "date_contacted")
	private Date dateContacted;

	@Column(name = "description")
	private String description;

	@Column(name = "reason_for_help_from_t3")
	private String reasonForHelp;

	@Column(name = "no_of_hours_taken")
	private int noOfhoursTaken;

	@Column(name = "justified")
	private String justified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(int ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	/*
	 * public Project getModule() { return module; }
	 * 
	 * public void setModule(Project module) { this.module = module; }
	 */

	public Date getDateContacted() {
		return dateContacted;
	}

	public void setDateContacted(Date dateContacted) {
		this.dateContacted = dateContacted;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReasonForHelp() {
		return reasonForHelp;
	}

	public void setReasonForHelp(String reasonForHelp) {
		this.reasonForHelp = reasonForHelp;
	}

	public int getNoOfhoursTaken() {
		return noOfhoursTaken;
	}

	public void setNoOfhoursTaken(int noOfhoursTaken) {
		this.noOfhoursTaken = noOfhoursTaken;
	}

	public String getJustified() {
		return justified;
	}

	public void setJustified(String justified) {
		this.justified = justified;
	}

	public static String toJsonArray(
			Collection<org.yash.rms.domain.T3Contribution> collection) {
		return new JSONSerializer()
				.include("id", "dateContacted", "description", "reasonForHelp",
						"noOfhoursTaken", "justified")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN_NEW),
						Date.class).serialize(collection);
	}

}
