package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * @author arpan.badjatiya
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "skill_request_requisition")
public class SkillRequest implements Serializable {

	// public final static String SEARCH_ACTIVITY_BASED_ON_ID =
	// "SEARCH_ACTIVITY_BASED_ON_ID";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "skills", referencedColumnName = "id", nullable = false)
	private Competency skill;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "request_id", referencedColumnName = "id", nullable = false)
	private Request requestId;

	@Column(name = "req_resources")
	private Integer noOfResources;

	@Column(name = "fulfilled")
	private String fulfilled;

	@Column(name = "remaining")
	private String remaining;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "designation", referencedColumnName = "id", nullable = false)
	private Designation designation;

	@Column(name = "experience")
	private String experience;
    //AllocationType
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "billable", referencedColumnName = "id", nullable = false)
	private AllocationType billable;

	@Column(name = "type")
	private Integer type;

	@Column(name = "time_frame")
	private String timeFrame;

	@Column(name = "comments")
	private String comments;
	
	@Column(name = "primary_skills")
	private String primarySkills;

	@Column(name = "desirable_skills")
	private String desirableSkills;

	@Column(name = "responsibilities")
	private String responsibilities;

	@Column(name = "career_growth_plan")
	private String careerGrowthPlan;

	@Column(name = "target_companies")
	private String targetCompanies;

	@Column(name = "key_scanners")
	private String keyScanners;

	@Column(name = "key_interviewers_one")
	private String keyInterviewersOne;

	@Column(name = "key_interviewers_two")
	private String keyInterviewersTwo;

	@Column(name = "additional_comments")
	private String additionalComments;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "skillRequestId")
	private List<SkillResource> skillResources;

	public String getPrimarySkills() {
		return primarySkills;
	}

	public void setPrimarySkills(String primarySkills) {
		this.primarySkills = primarySkills;
	}

	public String getDesirableSkills() {
		return desirableSkills;
	}

	public void setDesirableSkills(String desirableSkills) {
		this.desirableSkills = desirableSkills;
	}

	public String getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}

	public String getCareerGrowthPlan() {
		return careerGrowthPlan;
	}

	public void setCareerGrowthPlan(String careerGrowthPlan) {
		this.careerGrowthPlan = careerGrowthPlan;
	}

	public String getTargetCompanies() {
		return targetCompanies;
	}

	public void setTargetCompanies(String targetCompanies) {
		this.targetCompanies = targetCompanies;
	}

	public String getKeyScanners() {
		return keyScanners;
	}

	public void setKeyScanners(String keyScanners) {
		this.keyScanners = keyScanners;
	}

	public String getKeyInterviewersOne() {
		return keyInterviewersOne;
	}

	public void setKeyInterviewersOne(String keyInterviewersOne) {
		this.keyInterviewersOne = keyInterviewersOne;
	}

	public String getKeyInterviewersTwo() {
		return keyInterviewersTwo;
	}

	public void setKeyInterviewersTwo(String keyInterviewersTwo) {
		this.keyInterviewersTwo = keyInterviewersTwo;
	}

	public String getAdditionalComments() {
		return additionalComments;
	}

	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	

	public AllocationType getBillable() {
		return billable;
	}

	public void setBillable(AllocationType billable) {
		this.billable = billable;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTimeFrame() {
		return timeFrame;
	}

	public void setTimeFrame(String timeFrame) {
		this.timeFrame = timeFrame;
	}

	public Request getRequestId() {
		return requestId;
	}

	public void setRequestId(Request requestId) {
		this.requestId = requestId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public Competency getSkill() {
		return skill;
	}

	public void setSkill(Competency skill) {
		this.skill = skill;
	}

	public Integer getNoOfResources() {
		return noOfResources;
	}

	public void setNoOfResources(Integer noOfResources) {
		this.noOfResources = noOfResources;
	}

	public String getFulfilled() {
		return fulfilled;
	}

	public void setFulfilled(String fulfilled) {
		this.fulfilled = fulfilled;
	}

	public String getRemaining() {
		return remaining;
	}

	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<SkillResource> getSkillResources() {
		return skillResources;
	}

	public void setSkillResources(List<SkillResource> skillResources) {
		this.skillResources = skillResources;
	}
	public static String toJsonArray(
			Collection<org.yash.rms.domain.SkillRequest> collection) {
		return new JSONSerializer()
				//.include("id", "noOfResources", "fulfilled","remaining", "experience", "billable", "type", "timeFrame", "comments", "primarySkills", "desirableSkills", "responsibilities", "careerGrowthPlan", "targetCompanies","keyScanners","keyInterviewersOne","keyInterviewersTwo","additionalComments","skill.id","requestId.id","requestId.employeeId.id","requestId.employeeId.firstName","requestId.employeeId.lastName","skillResources.id","skillResources.skillRequestId","skillResources.resourceId","skillResources.externalResourceName","designation.id","designation.designationName")
				.include("id","requestId.id","requestId.employeeId.firstName","requestId.employeeId.lastName","skillResources.id","skillResources.externalResourceName","skillResources.skillRequestId.id","skillResources.resourceId.employeeId","skillResources.resourceId.firstName","skillResources.resourceId.lastName","requestId.projectId.id","skillResources.statusId.id","skillResources.allocationId.id","skillResources.allocationDate")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN),
						Date.class).serialize(collection);
	}

}
