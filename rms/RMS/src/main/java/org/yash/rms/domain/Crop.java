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
@Table(name = "crop_ost")
public class Crop implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7388154179837401630L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ca_ticket_id")
	private CATicket caTicket;

	@Column(name = "title")
	private String title;

	@Column(name = "module")
	private String module;

	@Column(name = "description")
	private String description;

	@Column(name = "source")
	private String source;

	@Column(name = "benefit_type")
	private String benefitType;

	@Column(name = "total_business_hrs_saved")
	private int totalBusinessHrsSaved;

	@Column(name = "total_IT_hours_saved")
	private int totalITHoursSaved;

	@Column(name = "savings_in_USD")
	private String savingsInUSD;

	@Column(name = "justified")
	private String justified;

	@Column(name = "updated_date")
	private Date updatedDate;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getBenefitType() {
		return benefitType;
	}

	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}

	public int getTotalBusinessHrsSaved() {
		return totalBusinessHrsSaved;
	}

	public void setTotalBusinessHrsSaved(int totalBusinessHrsSaved) {
		this.totalBusinessHrsSaved = totalBusinessHrsSaved;
	}

	public int getTotalITHoursSaved() {
		return totalITHoursSaved;
	}

	public void setTotalITHoursSaved(int totalITHoursSaved) {
		this.totalITHoursSaved = totalITHoursSaved;
	}

	public String getSavingsInUSD() {
		return savingsInUSD;
	}

	public void setSavingsInUSD(String savingsInUSD) {
		this.savingsInUSD = savingsInUSD;
	}

	public String getJustified() {
		return justified;
	}

	public void setJustified(String justified) {
		this.justified = justified;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public static String toJsonArray(
			Collection<org.yash.rms.domain.Crop> collection) {
		return new JSONSerializer()
				.include("id", "title", "description", "source", "benefitType",
						"totalBusinessHrsSaved", "totalITHoursSaved",
						"savingsInUSD", "justified")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN),
						Date.class).serialize(collection);
	}
}
