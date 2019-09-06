package org.yash.rms.report.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.yash.rms.domain.Resource;
import org.yash.rms.dto.AllocationTypeDTO;
import org.yash.rms.dto.RequisitionResourceDTO;
import org.yash.rms.dto.ResourceStatusDTO;
import org.yash.rms.util.Constants;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class RequestRequisitionReport {
	
	private int id;

	private Integer requestRequisitionId;
	
	private String requirementId;

	private String remaining;
	
	private String hold;

	private String open;

	private String closed;

	private String notFitForRequirement;

	private String shortlisted;

	private String submissions;

	private String status;

	private String lost;
	
	private String designationName;
	
	private String skill;
	
	private String locationName;
	
	private String clientName;
	
	private String businessGroupName;
	
	private String requestedBy;
	
	private String noOfResources;
	
	private String allocationType;
	
	private String aliasAllocationName;
	
	private AllocationTypeDTO allocationDTO;
	
	private String requestedDate;
	
	private String addtionalComments;
	
	List<ResourceStatusDTO> resourceStatusList = new ArrayList<ResourceStatusDTO>();
	
	List<RequisitionResourceDTO> requisitionResourceList = new ArrayList<RequisitionResourceDTO>();
	
	private String sentToList;
	
	private String pdlList;
	
	private String reportStatus;
	
	private String rrfCloserDate;	
	
	private String requirementType;
	
	private String custGroup;
	
	private String amJobCode; 
	
	private String hiringUnit;
	
	private String reqUnit; 
	
	private String tacTeamPOC;
	
	private String rmgPOC;
	
	private String requirementArea;
	
	public String getHiringUnit() {
		return hiringUnit;
	}
	public void setHiringUnit(String hiringUnit) {
		this.hiringUnit = hiringUnit;
	}
	public String getReqUnit() {
		return reqUnit;
	}
	public void setReqUnit(String reqUnit) {
		this.reqUnit = reqUnit;
	}

	
	public String getRrfCloserDate() {
        return rrfCloserDate;
    }
    public void setRrfCloserDate(String rrfCloserDate) {
        this.rrfCloserDate = rrfCloserDate;
    }
    public String getAddtionalComments() {
		return addtionalComments;
	}
	public void setAddtionalComments(String addtionalComments) {
		this.addtionalComments = addtionalComments;
	}

	public List<ResourceStatusDTO> getResourceStatusList() {
		return resourceStatusList;
	}



	public void setResourceStatusList(List<ResourceStatusDTO> resourceStatusList) {
		this.resourceStatusList = resourceStatusList;
	}



	public String getRequirementId() {
		return requirementId;
	}



	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}



	public String getRemaining() {
		return remaining;
	}



	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}



	public String getHold() {
		return hold;
	}



	public void setHold(String hold) {
		this.hold = hold;
	}



	public String getOpen() {
		return open;
	}



	public void setOpen(String open) {
		this.open = open;
	}



	public String getClosed() {
		return closed;
	}



	public void setClosed(String closed) {
		this.closed = closed;
	}



	public String getNotFitForRequirement() {
		return notFitForRequirement;
	}



	public void setNotFitForRequirement(String notFitForRequirement) {
		this.notFitForRequirement = notFitForRequirement;
	}



	public String getShortlisted() {
		return shortlisted;
	}



	public void setShortlisted(String shortlisted) {
		this.shortlisted = shortlisted;
	}



	public String getSubmissions() {
		return submissions;
	}



	public void setSubmissions(String submissions) {
		this.submissions = submissions;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getLost() {
		return lost;
	}



	public void setLost(String lost) {
		this.lost = lost;
	}



	public String getDesignationName() {
		return designationName;
	}



	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}



	public String getSkill() {
		return skill;
	}



	public void setSkill(String skill) {
		this.skill = skill;
	}

	public static String toJson(List<RequestRequisitionReport> report) {
		return new JSONSerializer()
				.include("id","requirementId","requestedBy","noOfResources", "remaining", "open", "shortlisted", "closed", "notFitForRequirement",
						"status", "hold", "lost", "submissions" ,"skill" , "designationName","clientName","allocationType","locationName",
						"businessGroupName","requestRequisitionId","aliasAllocationName","sentToList","pdlList", "reportStatus", "addtionalComments", "hiringUnit", "reqUnit", "tacTeamPOC", "rmgPOC", "requirementArea","amJobCode" ,"custGroup")
				.exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN), Date.class)
				.serialize(report);
	}



	public List<RequisitionResourceDTO> getRequisitionResourceList() {
		return requisitionResourceList;
	}



	public void setRequisitionResourceList(List<RequisitionResourceDTO> requisitionResourceList) {
		this.requisitionResourceList = requisitionResourceList;
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



	public String getNoOfResources() {
		return noOfResources;
	}



	public void setNoOfResources(String noOfResources) {
		this.noOfResources = noOfResources;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getAllocationType() {
		return allocationType;
	}

	public void setAllocationType(String allocationType) {
		this.allocationType = allocationType;
	}



	public String getRequestedDate() {
		return requestedDate;
	}



	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}



	public AllocationTypeDTO getAllocationDTO() {
		return allocationDTO;
	}



	public void setAllocationDTO(AllocationTypeDTO allocationDTO) {
		this.allocationDTO = allocationDTO;
	}
	
	public Integer getRequestRequisitionId() {
		return requestRequisitionId;
	}
	
	public void setRequestRequisitionId(Integer requestRequisitionId) {
		this.requestRequisitionId = requestRequisitionId;
	}
	public String getAliasAllocationName() {
		return aliasAllocationName;
	}
	public void setAliasAllocationName(String aliasAllocationName) {
		this.aliasAllocationName = aliasAllocationName;
	}
	public String getSentToList() {
		return sentToList;
	}
	public void setSentToList(String sentToList) {
		this.sentToList = sentToList;
	}
	public String getPdlList() {
		return pdlList;
	}
	public void setPdlList(String pdlList) {
		this.pdlList = pdlList;
	}
	public String getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getRequirementType() {
		return requirementType;
	}
	public void setRequirementType(String requirementType) {
		if(requirementType.equals("0"))
			this.requirementType = "Replacement";
		else if(requirementType.equals("1"))
			this.requirementType = "New Requirement";
		else if(requirementType.equals("2"))
			this.requirementType = "Heads-Up";
		else if(requirementType.equals("3"))
			this.requirementType = "Pool";
		
	}
	public String getCustGroup() {
		return custGroup;
	}
	public void setCustGroup(String custGroup) {
		this.custGroup = custGroup;
	}
	public String getAmJobCode() {
		return amJobCode;
	}
	public void setAmJobCode(String amJobCode) {
		this.amJobCode = amJobCode;
	}
	
	public String getRmgPOC() {
		return rmgPOC;
	}
	
	public String getTacTeamPOC() {
		return tacTeamPOC;
	}
	
	public void setRmgPOC(String rmgPOC) {
		this.rmgPOC = rmgPOC;
	}
	
	public void setTacTeamPOC(String tacTeamPOC) {
		this.tacTeamPOC = tacTeamPOC;
	}
	public String getRequirementArea() {
		return requirementArea;
	}
	public void setRequirementArea(String requirementArea) {
		this.requirementArea = requirementArea;
	}
	
}
