package org.yash.rms.json.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.dto.ResourceAllocationDTO;
import org.yash.rms.helper.DateTransformUtility;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DozerMapperUtility;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * @author apurva.sinha
 * 
 */
@Component("ResourceAllocationMapper")
public class ResourceAllocationMapper  {
	
	@Autowired
	private DozerMapperUtility mapper;
	
	public static String toJsonArray(
			Collection<ResourceAllocation> collection) {
		//System.out.println("In toJsonArray of Mapper");
		return new JSONSerializer()
				.include("employeeId.employeeName", "id", "behalfManager",
						"employeeId.employeeId", "employeeId.yashEmpId",
						"employeeId","projectId.id", "projectId.projectName","projectId.orgHierarchy.id",
						/*"rateId.id", "rateId.billingRate",*/ "allocationTypeId.id",
						"allocationTypeId.allocationType", "allocationSeq",
						"allocStartDate", "allocEndDate", "allocBlock",
						"allocRemarks", "curProj", "billable", 
						"allocatedBy.employeeId", "updatedBy.employeeId","employeeId.currentBuId.id",
						"ownershipId.id","lastUserActivityDate","firstUserActivityDate","team.id", "team.teamName","employeeId.isReleasedIndicator","employeeId.dateOfJoining","projectEndRemarks","allocPercentage","employeeId.designationId.designationName","employeeId.currentBuId.parentId.name","employeeId.currentBuId.name","employeeId.currentReportingManager.firstName","employeeId.currentReportingManager.middleName","employeeId.currentReportingManager.lastName","resourceReleaseSummary.id",
						"resourceReleaseSummary.primarySkills","resourceReleaseSummary.overAllRating","resourceReleaseSummary.ktStatus","resourceReleaseSummary.reasonForRelease","resourceReleaseSummary.teamHandleExperiance","resourceReleaseSummary.trainingName","resourceReleaseSummary.jobKnowledgeRating","resourceReleaseSummary.workQualityRating","resourceReleaseSummary.attandanceRating","resourceReleaseSummary.intiativeRating","resourceReleaseSummary.communicationRating",
						"resourceReleaseSummary.listingSkillsRating","resourceReleaseSummary.dependabilityRating","resourceReleaseSummary.overAllRating","resourceReleaseSummary.pipStatus"
				        ,"projectId.customerNameId.customerName","resourceType").transform(new DateTransformer(Constants.DATE_PATTERN_4),
	                        Date.class).exclude("*").serialize(collection);
						
	}
	
	public ResourceAllocation fromJsonToObject(String json, Class<ResourceAllocation> resourceAllocationClass) {
		return new JSONDeserializer<ResourceAllocation>().use(null, resourceAllocationClass).use(Date.class, new DateTransformer(Constants.DATE_PATTERN_4)).deserialize(json);
	}

	public static Collection<ResourceAllocation> fromJsonArrayToResourceAllocations(
			String json) {
		return new JSONDeserializer<List<ResourceAllocation>>()
				.use(null, ArrayList.class)
				.use("values", ResourceAllocation.class)
				.use(Date.class, new DateTransformer(Constants.DATE_PATTERN_4))
				.deserialize(json);
	}
	
	public ResourceAllocationDTO fromresourceAllocationDomainToDto(ResourceAllocation domain, ResourceAllocationDTO dto){
		
		if(domain.getEmployeeId() != null)
		dto.setEmployeeId(mapper.convertResourceDomainToResourceDTO(domain.getEmployeeId()));
		
		if(domain.getAllocationTypeId() != null)
		dto.setAllocationType(mapper.convertDomainToDTO(domain.getAllocationTypeId()));
		
		if(domain.getProjectId() != null)
		dto.setProjectId(mapper.convertProjectDomaintoDto(domain.getProjectId()));
		
		if(domain.getAllocatedBy() != null)
		dto.setAllocatedBy(mapper.convertResourceDomainToResourceDTO(domain.getAllocatedBy()));
		
		if(domain.getAllocStartDate()!=null)
		 dto.setAllocStartDate(DateTransformUtility.convertFromDateToString(domain.getAllocStartDate()));
		
		if(domain.getAllocEndDate()!=null)
		 dto.setAllocEndDate(DateTransformUtility.convertFromDateToString(domain.getAllocEndDate()));
		
		if(domain.getAllocationSeq() != null)
		dto.setAllocationSeq(domain.getAllocationSeq());
		
		if(domain.getAllocRemarks() != null)
		dto.setAllocRemarks(domain.getAllocRemarks());
		
		if(domain.getCurrentBuId() != null)
		dto.setCurrentBuId(mapper.convertOrgHierarchyDomainToDTO(domain.getCurrentBuId()));
		
		if(domain.getCreatedId() != null)
		dto.setCreatedId(domain.getCreatedId());
		
		if(domain.getCurProj()!=null)
		 dto.setCurProj(domain.getCurProj());
		
		if(domain.getCurrentReportingManager() != null)
		dto.setCurrentReportingManager(mapper.convertResourceDomainToResourceDTO(domain.getCurrentReportingManager()));
		
		if(domain.getCurrentReportingManagerTwo() != null)
		dto.setCurrentReportingManagerTwo(mapper.convertResourceDomainToResourceDTO(domain.getCurrentReportingManagerTwo()));
		
		if(domain.getDesignation() != null)
		dto.setDesignation(mapper.convertDomainToDTO(domain.getDesignation()));
		dto.setId(domain.getId());
		
		
		return dto;
		
	}
	
}
