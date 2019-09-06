package org.yash.rms.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
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
@NamedQueries({
	@NamedQuery(name = "RequestRequisitionSkill.FIND_SKILLRESOURCES", query = "SELECT o FROM RequestRequisitionSkill AS o WHERE o.id IN (:id)"),
	@NamedQuery(name = "RequestRequisitionSkill.findByRequestRequisitionSkillId", query = "SELECT requestRequisitionSkill FROM RequestRequisitionSkill AS requestRequisitionSkill WHERE requestRequisitionSkill.id = :id")	
})

@FilterDefs({@FilterDef(name = RequestRequisitionSkill.IS_DELETED, parameters=@ParamDef(name="isDeleted", type="boolean")),
	/*@FilterDef(name = RequestRequisitionSkill.PDLEMAILID, parameters=@ParamDef(name="pdlEmailId", type="string")),*/
	@FilterDef(name = RequestRequisitionSkill.SENDEMAILID, parameters=@ParamDef(name="sendEmailId", type="integer")),
	@FilterDef(name = RequestRequisitionSkill.TONOTIFYID, parameters=@ParamDef(name="toNotifyId", type="integer")),
	@FilterDef(name = RequestRequisitionSkill.RRFEMPLOYEEID, parameters=@ParamDef(name="employeeId", type="integer")),
	@FilterDef(name = RequestRequisitionSkill.COMMON_OR_CONDITION, parameters={@ParamDef(name="projectId", type="integer"),@ParamDef(name="employeeId", type="integer"),@ParamDef(name="toNotifyId", type="integer")}),
	@FilterDef(name = RequestRequisitionSkill.COMMON_OR_CONDITION_WITH_SENTEMAIL_IN_AND, parameters={@ParamDef(name="projectId", type="integer"),@ParamDef(name="employeeId", type="integer"),@ParamDef(name="toNotifyId", type="integer"),@ParamDef(name="sendEmailId", type="integer")}),
	@FilterDef(name = RequestRequisitionSkill.REQ_PROJ_ID,parameters={@ParamDef(name="projectId", type="integer"),@ParamDef(name="isDeleted", type="boolean")})
})
@Filters({
		@Filter(name = RequestRequisitionSkill.IS_DELETED, condition = "is_deleted=:isDeleted"),
		@Filter(name = RequestRequisitionSkill.SENDEMAILID, condition = "request_id in (select r.id from request_requisition r where EXISTS (SELECT FIND_IN_SET(:sendEmailId, REPLACE(r.sent_mail,' ','')))) "),
		@Filter(name = RequestRequisitionSkill.TONOTIFYID, condition = "request_id in (select r.id from request_requisition r where r.notify_to = (:toNotifyId)) "),
		@Filter(name = RequestRequisitionSkill.RRFEMPLOYEEID, condition = "request_id in (select r.id from request_requisition r where r.emp_id = (:employeeId)) "),
		@Filter(name = RequestRequisitionSkill.REQ_PROJ_ID, condition = " request_id in (select r.id from request_requisition r where r.project_id in (:projectId))"),
		@Filter(name = RequestRequisitionSkill.COMMON_OR_CONDITION, condition = " request_id in (select r.id from request_requisition r where EXISTS (SELECT FIND_IN_SET(:toNotifyId, REPLACE(r.notify_to,' ',''))) or r.emp_id =:employeeId or r.project_id in (:projectId))"),
		@Filter(name = RequestRequisitionSkill.COMMON_OR_CONDITION_WITH_SENTEMAIL_IN_AND, condition = "request_id in (select r.id from request_requisition r where (SELECT FIND_IN_SET(:sendEmailId, REPLACE(r.sent_mail,' ',''))) or (SELECT pdlrm.pdl_id FROM pdl_resource_mapping pdlrm  WHERE pdlrm.resource_id=:sendEmailId AND (SELECT FIND_IN_SET(pdlrm.pdl_id,REPLACE(r.pdl_email,' ',''))))  or (SELECT FIND_IN_SET(:toNotifyId, REPLACE(r.notify_to,' ',''))) or r.emp_id =:employeeId or r.project_id in (:projectId))")
})
public class RequestRequisitionSkill implements Serializable {

	public final static String IS_DELETED = "IS_DELETED";
	public final static String REQ_PROJ_ID = "REQ_PROJ_ID";
	public final static String SENDEMAILID = "SENDEMAILID";
	public final static String TONOTIFYID = "TONOTIFYID";
	public final static String PDLEMAILID = "PDLEMAILID";
	public final static String RRFEMPLOYEEID = "RRFEMPLOYEEID";
	public final static String COMMON_OR_CONDITION = "COMMON_OR_CONDITION";
	public final static String COMMON_OR_CONDITION_WITH_SENTEMAIL_IN_AND = "COMMON_OR_CONDITION_WITH_SENTEMAIL_IN_AND";
	public final static String RRFID = "id";
	// public final static String SEARCH_ACTIVITY_BASED_ON_ID =
	// "SEARCH_ACTIVITY_BASED_ON_ID";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills", referencedColumnName = "id", nullable = false)
	private Skills skill;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "request_id", referencedColumnName = "id", nullable = false)
	private RequestRequisition requestRequisition;

	@Column(name = "req_resources")
	private Integer noOfResources;

	@Column(name = "fulfilled")
	private Integer fulfilled;

	@Column(name = "remaining")
	private String remaining;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "designation", referencedColumnName = "id", nullable = false)
	private Designation designation;

	@Column(name = "experience")
	private String experience;
    //AllocationType
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "billable", referencedColumnName = "id", nullable = false)
	private AllocationType allocationType;
	
	
	@OneToMany(mappedBy="requestRequisitionSkill",fetch=FetchType.LAZY)
	private List<RequestRequisitionResource> requestRequisitionResources = new ArrayList<RequestRequisitionResource>();
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "location_id", referencedColumnName="id")
	//@NotNull
	private Location location;

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
	
	@Column(name = "req_id")
	private String requirementId;
	
	@Column(name = "on_hold")
	private Integer hold;
	
	@Column(name = "open")
	private Integer open;
	
	@Column(name = "closed")
	private Integer closed;
	
	@Column(name = "not_fit_for_requirement")
	private Integer notFitForRequirement;
	
	@Column(name = "shortlisted")
	private Integer shortlisted;
	
	@Column(name = "submissions")
	private Integer submissions;
	
	// Added a new column for lost status
	@Column(name = "lost")
	private Integer lost;
	
	@Column(name = "sent_req_to")
	private String sent_req_id;
	 
	@Column(name = "pdl_list")
	private String pdl_list;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "skills_to_evaluate")
	private String skillsToEvaluate;
	
	@Column(name="closure_date")
	private Date closureDate;
	
	@Column(name="rrf_forward_comment")
	private String rrfForwardComment;
	
	@Column(name="is_deleted", columnDefinition = "boolean default false", nullable = false)
	private Boolean isDeleted =false;
	
	@Column(name="requirementArea")	
	private String requirementArea;
	
	public Integer getLost() {
		return lost;
	}

	public void setLost(Integer lost) {
		this.lost = lost;
	}

	public String getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}
	
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

	

	public AllocationType getAllocationType() {
		return allocationType;
	}

	public void setAllocationType(AllocationType billable) {
		this.allocationType = billable;
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

	public RequestRequisition getRequestRequisition() {
		return requestRequisition;
	}

	public void setRequestRequisition(RequestRequisition requestRequisition) {
		this.requestRequisition = requestRequisition;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Skills getSkill() {
		return skill;
	}

	public void setSkill(Skills skill) {
		this.skill = skill;
	}

	public Integer getNoOfResources() {
		return noOfResources;
	}

	public void setNoOfResources(Integer noOfResources) {
		this.noOfResources = noOfResources;
	}

	public Integer getFulfilled() {
		return fulfilled;
	}

	public void setFulfilled(Integer fulfilled) {
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
	
	public Integer getHold() {
		return hold;
	}

	public void setHold(Integer hold) {
		this.hold = hold;
	}

	public Integer getOpen() {
		return open;
	}

	public void setOpen(Integer open) {
		this.open = open;
	}

	public Integer getClosed() {
		return closed;
	}

	public void setClosed(Integer closed) {
		this.closed = closed;
	}

	public Integer getNotFitForRequirement() {
		return notFitForRequirement;
	}

	public void setNotFitForRequirement(Integer notFitForRequirement) {
		this.notFitForRequirement = notFitForRequirement;
	}

	public Integer getShortlisted() {
		return shortlisted;
	}

	public void setShortlisted(Integer shortlisted) {
		this.shortlisted = shortlisted;
	}

	public Integer getSubmissions() {
		return submissions;
	}

	public void setSubmissions(Integer submissions) {
		this.submissions = submissions;
	}

	public static String toJsonArray(
			Collection<org.yash.rms.domain.RequestRequisitionSkill> collection) {
		return new JSONSerializer()
				
				.include("id","requestRequisition.id","requestRequisition.resource.firstName","requestRequisition.resource.lastName","requestRequisitionResource.id","requestRequisitionResource.externalResourceName","requestRequisitionResource.requestRequisitionSkill.id","requestRequisitionResource.resource.employeeId","requestRequisitionResource.resource.firstName","requestRequisitionResource.resource.lastName","requestRequisition.project.id","requestRequisitionResource.requestRequisitionResourceStatus.id","requestRequisitionResource.allocationType.id","requestRequisitionResource.allocationDate",
						"submissions","shortlisted","closed","open","notFitForRequirement","status","requirementId")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN),
						Date.class).serialize(collection);
	}

	public List<RequestRequisitionResource> getRequestRequisitionResources() {
		return requestRequisitionResources;
	}

	public void setRequestRequisitionResources(List<RequestRequisitionResource> requestRequisitionResources) {
		this.requestRequisitionResources = requestRequisitionResources;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getSent_req_id() {
		return sent_req_id;
	}

	public void setSent_req_id(String sent_req_id) {
		this.sent_req_id = sent_req_id;
	}

	public String getPdl_list() {
		return pdl_list;
	}

	public void setPdl_list(String pdl_list) {
		this.pdl_list = pdl_list;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getSkillsToEvaluate() {
		return skillsToEvaluate;
	}
	
	public void setSkillsToEvaluate(String skillsToEvaluate) {
		this.skillsToEvaluate = skillsToEvaluate;
	}
	public Date getClosureDate() {
		return closureDate;
	}
	public void setClosureDate(Date closureDate) {
		this.closureDate = closureDate;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public String getRrfForwardComment() {
		return rrfForwardComment;
	}
	
	public void setRrfForwardComment(String rrfForwardComment) {
		this.rrfForwardComment = rrfForwardComment;
	}
	
	public String getRequirementArea() {
		return requirementArea;
	}

	public void setRequirementArea(String requirementArea) {
		this.requirementArea = requirementArea;
	}

	public static  String toJsonArrayRequestRequisitionSkill(Collection<RequestRequisitionSkill> collection) {
		return new JSONSerializer()
				.include("id", "skill.*", "requestRequisition.projectBU", "requestRequisition.hiringBGBU","requestRequisition.id","requestRequisition.requestor.firstName","requestRequisition.requestor.lastName", "noOfResources", "fulfilled", "remaining", "designation.*","experience", "allocationType.*","requestRequisitionResources","location","type",
						"timeFrame","comments","primarySkills","desirableSkills","responsibilities","careerGrowthPlan","targetCompanies","keyScanners","keyInterviewersOne",
						"keyInterviewersTwo","requestRequisition.comments","requirementId","hold","open","closed","notFitForRequirement",
						"shortlisted","submissions","lost","sent_req_id","pdl_list","status","skillsToEvaluate","closureDate","rrfForwardComment","isDeleted", "requestRequisition.successFactorId", "requirementArea").exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN_4), Date.class).serialize(collection);
		/*return new JSONSerializer()
				.include("id","requirementId","lost","isDeleted","").exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN_4), Date.class).serialize(collection);*/
	}
	public static  String toJsonRequestRequisitionSkill(Collection<RequestRequisitionSkill> collection) {
		return new JSONSerializer()
				.include("id","requirementId","status").exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN_4), Date.class).serialize(collection);
		
	}
}
