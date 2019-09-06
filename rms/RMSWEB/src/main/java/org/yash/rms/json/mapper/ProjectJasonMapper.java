package org.yash.rms.json.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.yash.rms.domain.Project;
import org.yash.rms.util.Constants;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Component
public class ProjectJasonMapper {
	 public String toJson(Project project) {
	        return new JSONSerializer().include("id", "projectTrackingCurrencyId.currencyName","projectMethodologyId.id","projectTrackingCurrencyId.id","offshoreDelMgr.employeeName","offshoreDelMgr.yashEmpId", "offshoreDelMgr.employeeId","deliveryMgr.employeeId","deliveryMgr.yashEmpId","deliveryMgr.employeeName","engagementModelId.engagementModelName", "engagementModelId.id", "customerNameId.id"  ,"customerNameId.customerName", "customerGroup.customerGroupName","customerGroup.groupId","orgHierarchy.id", "projectCategoryId.id", "invoiceById.id", "customerContacts", "deere","traineeProject", "onsiteDelMgr", "plannedProjCost", "plannedProjSize", "projectKickOff", "projectName", "projectEndDate", "description", "projectId.id", "projectPoes", "projectPoes.id","projectPoes.cost", "projectPoes.accountName", "projectPoes.poNumber", "projectPoes.issueDate", "projectPoes.validUptoDate", "projectCode", "team.id", "team.teamName","managerReadonly","orgHierarchy.name","engagementModelId.coding","engagementModelId.coding","engagementModelId.testing","engagementModelId.delivery","engagementModelId.design","engagementModelId.estimationAndPlaning","engagementModelId.requierement","orgHierarchy.parentId.name","projectNameSOW").exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN_4), Date.class).serialize(project);
	    }

	    public org.yash.rms.domain.Project fromJsonToProject(String json) {
	        return new JSONDeserializer<org.yash.rms.domain.Project>().use(null, org.yash.rms.domain.Project.class).use(Date.class, new DateTransformer(Constants.DATE_PATTERN_4)).deserialize(json);
	    }

	    public String toJsonArray(Collection<org.yash.rms.domain.Project> collection) {
	        return new JSONSerializer().include("id","projectTrackingCurrencyId.currencyName", "projectTrackingCurrencyId.id", "offshoreDelMgr.employeeId","offshoreDelMgr.employeeName","deliveryMgr.employeeId","deliveryMgr.employeeName", "engagementModelId.engagementModelName","engagementModelId.id", "customerNameId.id","customerNameId.customerName","customerGroup.customerGroupName","customerGroup.groupId","orgHierarchy.id", "projectCategoryId.id", "invoiceById.id", "customerContacts", "deere", "onsiteDelMgr", "plannedProjCost", "plannedProjSize", "projectKickOff", "projectName", "projectEndDate", "description", "projectId.id", "projectPoes", "projectPoes.cost", "projectPoes.accountName", "projectPoes.poNumber", "projectPoes.issueDate", "projectPoes.validUptoDate", "projectCode","managerReadonly","orgHierarchy.name","orgHierarchy.parentId.name","projectNameSOW").exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN_4), Date.class).serialize(collection);
	    }

	    public Collection<org.yash.rms.domain.Project> fromJsonArrayToProjects(String json) {
	        return new JSONDeserializer<List<org.yash.rms.domain.Project>>().use(null, ArrayList.class).use("values", Project.class).use(Date.class, new DateTransformer(Constants.DATE_PATTERN_4)).deserialize(json);
	}

}
