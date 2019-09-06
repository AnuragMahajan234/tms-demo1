/**
 * 
 */
package org.yash.rms.json.mapper;

import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.util.Constants;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * @author apurva.sinha
 * 
 */
@Component("ProjectAllocationMapper")
public class ProjectAllocationMapper {
	
	public String toJsonArray(Collection<ResourceAllocation> collection) {
		try {
			// System.out.println("In toJsonArray of Mapper of Project Alocation");
			return new JSONSerializer()
					.include("resourceType","employeeId.employeeName", "id", "behalfManager", "employeeId.employeeId", "employeeId.yashEmpId", "employeeId.currentBuId.id", "employeeId", "projectId.id",
							"projectId.projectName", "projectId.orgHierarchy.id", "rateId.id", "rateId.billingRate", "allocationTypeId.id", "allocationTypeId.allocationType", "allocationSeq",
							"allocStartDate", "allocEndDate", "allocBlock", "allocRemarks", "curProj", "billable", "allocHrs", "allocatedBy.employeeId", "updatedBy.employeeId", "ownershipId.id",
							"lastUserActivityDate", "firstUserActivityDate", "team.teamName", "team.id", "employeeId.isReleasedIndicator", "managerReadonly", "ownershipId.ownershipName",
							"employeeId.dateOfJoining", "projectId.projectKickOff", "projectId.projectEndDate", "projectEndRemarks","allocPercentage")
					.transform(new DateTransformer(Constants.DATE_PATTERN_4), Date.class).exclude("*").serialize(collection);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public ResourceAllocation fromJsonToObject(String json, Class<ResourceAllocation> resourceAllocationClass) {
		return new JSONDeserializer<ResourceAllocation>().use(null, resourceAllocationClass).use(Date.class, new DateTransformer(Constants.DATE_PATTERN_4)).deserialize(json);
	}

	public static org.yash.rms.domain.ResourceAllocation fromJsonToResourceAllocation(String json) {
		return new JSONDeserializer<ResourceAllocation>().use(null, ResourceAllocation.class).use(Date.class, new DateTransformer(Constants.DATE_PATTERN_4)).deserialize(json);
	}
}
