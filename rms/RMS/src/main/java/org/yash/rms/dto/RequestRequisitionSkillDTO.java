package org.yash.rms.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import org.yash.rms.dto.DesignationDTO;
import org.yash.rms.domain.Experience;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.AllocationTypeDTO;
import org.yash.rms.dto.CompetencyDTO;
import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class RequestRequisitionSkillDTO {

	private int id;

	private LocationDTO location;
	
	private SkillsDTO skill;

	private RequestRequisitionDTO requestRequisition;

	private DesignationDTO designation;

	private Integer noOfResources;

	private Integer fulfilled;

	private String remaining;

	private String experience;

	private AllocationTypeDTO allocationType;

	private Integer type;

	private String timeFrame;

	private String comments;
	
	private String primarySkills;

	private String desirableSkills;

	private String responsibilities;

	private String careerGrowthPlan;

	private String targetCompanies;

	private String keyScanners;

	private String keyInterviewersOne;

	private String keyInterviewersTwo;

	private String additionalComments;
	
	private String requirementId;
	
	private Integer hold;
	
	private Integer open;
	
	private Integer closed;
	
	private Integer notFitForRequirement;
	
	private Integer shortlisted;
	
	private Integer submissions;
	
	// Added new field for showing position status on Position Dashboard.
	private String status;
	
	// Added a new column for lost status
	private Integer lost;
	
    private String recordStatus;
    
    private String locationName;
	
	private String clientName;
	
	private String businessGroupName;
    
    private List<RequisitionResourceDTO> requisitionResourceList;
    
    private String requestedBy;
    
    private String sent_req_id;

	private String pdl_list;
	
	private String skillsToEvaluate;
	
	private List<Resource> keyInterviewersOneList;
	
	private List<Resource> keyInterviewersTwoList;
	
	private String requirementArea; 
	
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

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	
	public Integer getLost() {
		return lost;
	}

	public void setLost(Integer lost) {
		this.lost = lost;
	}
									
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SkillsDTO getSkill() {
		return skill;
	}

	public void setSkill(SkillsDTO skill) {
		this.skill = skill;
	}

	public RequestRequisitionDTO getRequestRequisition() {
		return requestRequisition;
	}

	public void setRequestRequisition(RequestRequisitionDTO requestRequisition) {
		this.requestRequisition = requestRequisition;
	}

	public DesignationDTO getDesignation() {
		return designation;
	}

	public void setDesignation(DesignationDTO designation) {
		this.designation = designation;
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

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public AllocationTypeDTO getAllocationType() {
		return allocationType;
	}

	public void setAllocationType(AllocationTypeDTO allocationType) {
		this.allocationType = allocationType;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public String getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
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

	
	
	public List<RequisitionResourceDTO> getRequisitionResourceList() {
		return requisitionResourceList;
	}

	public void setRequisitionResourceList(List<RequisitionResourceDTO> requisitionResourceList) {
		this.requisitionResourceList = requisitionResourceList;
	}

	public static String toJson(List<RequestRequisitionSkillDTO> report) {
		return new JSONSerializer()
				.include("requirementId", "remaining", "open", "shortlisted", "closed", "notFitForRequirement",
						"status", "hold", "lost", "submissions" , "skill" ,"designation" )
				.exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN), Date.class)
				.serialize(report);
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getBusinessGroupName() {
		return businessGroupName;
	}

	public void setBusinessGroupName(String businessGroupName) {
		this.businessGroupName = businessGroupName;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public String getSkillsToEvaluate() {
		return skillsToEvaluate;
	}
	public void setSkillsToEvaluate(String skillsToEvaluate) {
		this.skillsToEvaluate = skillsToEvaluate;
	}

	public List<Resource> getKeyInterviewersOneList() {
		return keyInterviewersOneList;
	}

	public void setKeyInterviewersOneList(List<Resource> keyInterviewersOneList) {
		this.keyInterviewersOneList = keyInterviewersOneList;
	}

	public List<Resource> getKeyInterviewersTwoList() {
		return keyInterviewersTwoList;
	}

	public void setKeyInterviewersTwoList(List<Resource> keyInterviewersTwoList) {
		this.keyInterviewersTwoList = keyInterviewersTwoList;
	}

	public String getRequirementArea() {
		return requirementArea;
	}

	public void setRequirementArea(String requirementArea) {
		this.requirementArea = requirementArea;
	}
	
	
	
}
