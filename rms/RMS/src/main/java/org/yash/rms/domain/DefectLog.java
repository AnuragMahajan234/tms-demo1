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
@Table(name = "defect_log_ost")
public class DefectLog implements Serializable {

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

	@Column(name = "zreq_ca_no")
	private int ZREQNo;

	@Column(name = "defect_type")
	private String defectType;

	@Column(name = "defect_description")
	private String defectDescription;

	@Column(name = "internal_external")
	private String internalExternal;

	@Column(name = "defect_category")
	private String defectCategory;

	@Column(name = "severity")
	private String severity;

	@Column(name = "defect_status")
	private String defectStatus;

	@Column(name = "identified_date")
	private Date identifiedDate;

	@Column(name = "identified_by")
	private String identifiedBy;

	@Column(name = "identified_phase")
	private String identifiedPhase;

	@Column(name = "module_name")
	private String moduleName;

	@Column(name = "injected_phase")
	private String injectedPhase;

	@Column(name = "work_product_name")
	private String workProductName;

	@Column(name = "reopened")
	private String reopened;

	@Column(name = "requirement_id_ticket")
	private String requirementIdTicket;

	@Column(name = "defect_root_cause")
	private String defectRootCause;

	@Column(name = "category_of_root_cause")
	private String categoryOfRootCause;

	@Column(name = "resolved_by")
	private String resolvedBy;

	@Column(name = "resolved_date")
	private Date resolvedDate;

	@Column(name = "closed_date")
	private Date closedDate;

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

	public int getZREQNo() {
		return ZREQNo;
	}

	public void setZREQNo(int zREQNo) {
		ZREQNo = zREQNo;
	}

	public String getDefectType() {
		return defectType;
	}

	public void setDefectType(String defectType) {
		this.defectType = defectType;
	}

	public String getDefectDescription() {
		return defectDescription;
	}

	public void setDefectDescription(String defectDescription) {
		this.defectDescription = defectDescription;
	}

	public String getInternalExternal() {
		return internalExternal;
	}

	public void setInternalExternal(String internalExternal) {
		this.internalExternal = internalExternal;
	}

	public String getDefectCategory() {
		return defectCategory;
	}

	public void setDefectCategory(String defectCategory) {
		this.defectCategory = defectCategory;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getDefectStatus() {
		return defectStatus;
	}

	public void setDefectStatus(String defectStatus) {
		this.defectStatus = defectStatus;
	}

	public Date getIdentifiedDate() {
		return identifiedDate;
	}

	public void setIdentifiedDate(Date identifiedDate) {
		this.identifiedDate = identifiedDate;
	}

	public String getIdentifiedBy() {
		return identifiedBy;
	}

	public void setIdentifiedBy(String identifiedBy) {
		this.identifiedBy = identifiedBy;
	}

	public String getIdentifiedPhase() {
		return identifiedPhase;
	}

	public void setIdentifiedPhase(String identifiedPhase) {
		this.identifiedPhase = identifiedPhase;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getInjectedPhase() {
		return injectedPhase;
	}

	public void setInjectedPhase(String injectedPhase) {
		this.injectedPhase = injectedPhase;
	}

	public String getWorkProductName() {
		return workProductName;
	}

	public void setWorkProductName(String workProductName) {
		this.workProductName = workProductName;
	}

	public String getReopened() {
		return reopened;
	}

	public void setReopened(String reopened) {
		this.reopened = reopened;
	}

	public String getRequirementIdTicket() {
		return requirementIdTicket;
	}

	public void setRequirementIdTicket(String requirementIdTicket) {
		this.requirementIdTicket = requirementIdTicket;
	}

	public String getDefectRootCause() {
		return defectRootCause;
	}

	public void setDefectRootCause(String defectRootCause) {
		this.defectRootCause = defectRootCause;
	}

	public String getCategoryOfRootCause() {
		return categoryOfRootCause;
	}

	public void setCategoryOfRootCause(String categoryOfRootCause) {
		this.categoryOfRootCause = categoryOfRootCause;
	}

	public String getResolvedBy() {
		return resolvedBy;
	}

	public void setResolvedBy(String resolvedBy) {
		this.resolvedBy = resolvedBy;
	}

	public Date getResolvedDate() {
		return resolvedDate;
	}

	public void setResolvedDate(Date resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public static String toJsonArray(
			Collection<org.yash.rms.domain.DefectLog> collection) {
		return new JSONSerializer()
				.include("id","defectType", "defectDescription", "internalExternal",
						"defectCategory", "severity", "defectStatus",
						"identifiedDate", "identifiedBy", "identifiedPhase",
						"injectedPhase", "workProductName", "reopened",
						"defectRootCause", "categoryOfRootCause", "resolvedBy",
						"resolvedDate", "closedDate")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN_NEW),
						Date.class).serialize(collection);
	}
}
