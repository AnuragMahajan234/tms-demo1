/**
 * 
 */
package org.yash.rms.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.DateFormatUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.yash.rms.dao.RequestRequisitionDao;
import org.yash.rms.domain.Activity;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.CATicket;
import org.yash.rms.domain.Competency;
import org.yash.rms.domain.Currency;
import org.yash.rms.domain.CustomActivity;
import org.yash.rms.domain.CustomerGroup;
import org.yash.rms.domain.CustomerPo;
import org.yash.rms.domain.DefaultProject;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.EmployeeCategory;
import org.yash.rms.domain.EngagementModel;
import org.yash.rms.domain.Event;
import org.yash.rms.domain.Grade;
import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.domain.InfogramInactiveResource;
import org.yash.rms.domain.InvoiceBy;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.Module;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Ownership;
import org.yash.rms.domain.PDLEmailGroup;
import org.yash.rms.domain.Priority;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.ProjectCategory;
import org.yash.rms.domain.ProjectMethodology;
import org.yash.rms.domain.ReasonForReplacement;
import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.RequestRequisitionResource;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.ResourceComment;
import org.yash.rms.domain.ResourceLoanTransfer;
import org.yash.rms.domain.Skills;
import org.yash.rms.domain.SkillsMapping;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.domain.UserNotification;
import org.yash.rms.domain.UserProfile;
import org.yash.rms.domain.Visa;
import org.yash.rms.dto.ActivitiesDTO;
import org.yash.rms.dto.AllocationTypeDTO;
import org.yash.rms.dto.BillingScaleDTO;
import org.yash.rms.dto.CompetencyDTO;
import org.yash.rms.dto.CustomActivityDTO;
import org.yash.rms.dto.CustomerDTO;
import org.yash.rms.dto.CustomerGroupDTO;
import org.yash.rms.dto.DesignationDTO;
import org.yash.rms.dto.EditProfileDTO;
import org.yash.rms.dto.GradeDTO;
import org.yash.rms.dto.InfogramActiveResourceDTO;
import org.yash.rms.dto.InfogramInactiveResourceDTO;
import org.yash.rms.dto.LocationDTO;
import org.yash.rms.dto.OrgHierarchyDTO;
import org.yash.rms.dto.PDLEmailGroupDTO;
import org.yash.rms.dto.PriorityDTO;
import org.yash.rms.dto.ProjectDTO;
import org.yash.rms.dto.ReasonForReplacementDTO;
import org.yash.rms.dto.ReleaseNotes;
import org.yash.rms.dto.RequestRequisitionDTO;
import org.yash.rms.dto.RequestRequisitionFormDTO;
import org.yash.rms.dto.RequestRequisitionSkillDTO;
import org.yash.rms.dto.RequisitionResourceDTO;
import org.yash.rms.dto.ResourceAllocationDTO;
import org.yash.rms.dto.ResourceCommentDTO;
import org.yash.rms.dto.ResourceDTO;
import org.yash.rms.dto.Roles;
import org.yash.rms.dto.SkillsDTO;
import org.yash.rms.dto.SkillsMappingDTO;
import org.yash.rms.dto.TimehrsViewDTO;
import org.yash.rms.dto.TimesheetSubmissionDTO;
import org.yash.rms.dto.UserActivityViewDTO;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.form.CATicketForm;
import org.yash.rms.form.DefaultProjectForm;
import org.yash.rms.form.EventDataForm;
import org.yash.rms.form.NewEmployee;
import org.yash.rms.form.NewTimeSheet;
import org.yash.rms.form.ResourceLoanTransferForm;
import org.yash.rms.form.TimeSheet;
import org.yash.rms.form.UserProfileForm;
import org.yash.rms.helper.UserActivityHelper;
import org.yash.rms.mapper.RequestRequisitionFormMapper;
import org.yash.rms.mapper.RequestRequisitionSkillMapper;
import org.yash.rms.service.ResourceService;
import org.yash.rms.dto.TimehrsViewsDTO;

/**
 * @author arpan.badjatiya
 * 
 */
@Component
public class DozerMapperUtility {
	@Autowired
	@Qualifier("mapper")
	DozerBeanMapper mapper;
	
	@Autowired
	private UserActivityHelper userActivityHelper;

	@Autowired
	RequestRequisitionFormMapper requisitionFormMapper;
	
	@Autowired
	ResourceService resourceService;
	
	@Autowired
	RequestRequisitionDao requestRequisitionDao;
	
	@Autowired
	RequestRequisitionSkillMapper requestRequisitionSkillMapper;
	
	@Autowired
	UserUtil userUtil;
	
	public org.yash.rms.domain.BillingScale convertDTOObjectToDomain(BillingScaleDTO billingScale) {
		return mapper.map(billingScale, org.yash.rms.domain.BillingScale.class);
	}

	public BillingScaleDTO convertDTOObjectToDomain(org.yash.rms.domain.BillingScale billingScale) {
		return mapper.map(billingScale, BillingScaleDTO.class);
	}

	public List<BillingScaleDTO> convertDomainListToDTOList(List<org.yash.rms.domain.BillingScale> billingScale) {
		List<BillingScaleDTO> billingScaleList = new ArrayList<BillingScaleDTO>();
		for (org.yash.rms.domain.BillingScale billing : billingScale) {
			billingScaleList.add(mapper.map(billing, BillingScaleDTO.class));
		}
		return billingScaleList;
	}

	public org.yash.rms.domain.Activity convertDTOObjectToDomain(Activity activity) {
		return mapper.map(activity, org.yash.rms.domain.Activity.class);
	}

	public ActivitiesDTO convertDomainToDTO(org.yash.rms.domain.Activity activity) {
		return mapper.map(activity, ActivitiesDTO.class);
	}

	public org.yash.rms.domain.Grade convertDTOObjectToDomain(Grade grade) {
		return mapper.map(grade, org.yash.rms.domain.Grade.class);
	}

	public org.yash.rms.domain.Event convertDTOObjectToDomain(EventDataForm eventTrigger) {
		return mapper.map(eventTrigger, org.yash.rms.domain.Event.class);
	}

	public org.yash.rms.domain.Event convertDTOObjectToDomain(Event event) {
		return mapper.map(event, org.yash.rms.domain.Event.class);
	}

	public org.yash.rms.domain.CustomActivity convertDTOObjectToDomain(CustomActivity projectActivity) {
		return mapper.map(projectActivity, org.yash.rms.domain.CustomActivity.class);
	}
	
	public CustomActivityDTO convertDomainToDto(CustomActivity projectActivity) {
		return mapper.map(projectActivity, CustomActivityDTO.class);
	}

	public GradeDTO convertDomainToDTO(org.yash.rms.domain.Grade grade) {
		return mapper.map(grade, GradeDTO.class);
	}

	public List<Grade> convertGradeDomainListToDTOList(List<org.yash.rms.domain.Grade> grade) {
		List<Grade> gradeList = new ArrayList<Grade>();
		for (org.yash.rms.domain.Grade grade1 : grade) {
			gradeList.add(mapper.map(grade1, Grade.class));
		}
		return gradeList;
	}

	public List<CustomActivity> convertProjectActivityDomainListToDTOList(List<org.yash.rms.domain.CustomActivity> projectActivities) {
		List<CustomActivity> projectActivityList = new ArrayList<CustomActivity>();
		for (org.yash.rms.domain.CustomActivity projectActivity1 : projectActivities) {
			projectActivityList.add(mapper.map(projectActivity1, CustomActivity.class));
		}
		return projectActivityList;
	}

	public List<Event> convertEventDomainListToDTOList(List<org.yash.rms.domain.Event> event) {
		List<Event> eventList = new ArrayList<Event>();
		for (org.yash.rms.domain.Event event1 : event) {
			eventList.add(mapper.map(event1, Event.class));
		}
		return eventList;
	}

	public org.yash.rms.domain.AllocationType convertDTOObjectToDomain(AllocationType allocationType) {
		return mapper.map(allocationType, org.yash.rms.domain.AllocationType.class);
	}

	public AllocationTypeDTO convertDomainToDTO(org.yash.rms.domain.AllocationType allocationType) {
		return mapper.map(allocationType, AllocationTypeDTO.class);
	}

	public List<AllocationType> convertAllocationTypeDomainListToDTOList(List<org.yash.rms.domain.AllocationType> AllocationType) {
		List<AllocationType> allocationTypeList = new ArrayList<AllocationType>();
		for (org.yash.rms.domain.AllocationType allocationType2 : AllocationType) {
			allocationTypeList.add(mapper.map(allocationType2, AllocationType.class));
		}
		return allocationTypeList;
	}

	public org.yash.rms.domain.Currency convertDTOObjectToDomain(Currency currency) {
		return mapper.map(currency, org.yash.rms.domain.Currency.class);
	}

	public Currency convertDomainToDTO(org.yash.rms.domain.Currency currency) {
		return mapper.map(currency, Currency.class);
	}

	public List<Currency> convertCurrencyDomainListToDTOList(List<org.yash.rms.domain.Currency> currencies) {
		List<Currency> currencyList = new ArrayList<Currency>();
		for (org.yash.rms.domain.Currency currency : currencies) {
			currencyList.add(mapper.map(currency, Currency.class));
		}
		return currencyList;
	}

	public org.yash.rms.domain.Designation convertDTOObjectToDomain(Designation designation) {
		return mapper.map(designation, org.yash.rms.domain.Designation.class);
	}

	public DesignationDTO convertDomainToDTO(org.yash.rms.domain.Designation designation) {
		return mapper.map(designation, DesignationDTO.class);
	}

	public List<Designation> convertDesignationDomainListToDTOList(List<org.yash.rms.domain.Designation> designations) {
		List<Designation> designationList = new ArrayList<Designation>();
		for (org.yash.rms.domain.Designation designation : designations) {
			designationList.add(mapper.map(designation, Designation.class));
		}
		return designationList;
	}

	public org.yash.rms.domain.EngagementModel convertDTOObjectToDomain(EngagementModel engagementModel) {
		return mapper.map(engagementModel, org.yash.rms.domain.EngagementModel.class);
	}

	public EngagementModel convertDomainToDTO(org.yash.rms.domain.EngagementModel engagementModel) {
		return mapper.map(engagementModel, EngagementModel.class);
	}

	public List<EngagementModel> convertengagementModelDomainListToDTOList(List<org.yash.rms.domain.EngagementModel> engagementModels) {
		List<EngagementModel> engagementModelList = new ArrayList<EngagementModel>();
		for (org.yash.rms.domain.EngagementModel engagementModel : engagementModels) {
			engagementModelList.add(mapper.map(engagementModel, EngagementModel.class));
		}
		return engagementModelList;
	}

	public LocationDTO convertDomainToDTO(org.yash.rms.domain.Location location) {
		return mapper.map(location, LocationDTO.class);
	}

	public org.yash.rms.domain.Location convertDTOObjectToDomain(Location location) {
		return mapper.map(location, org.yash.rms.domain.Location.class);
	}

	public List<Location> convertlocationListToDTOList(List<org.yash.rms.domain.Location> locations) {
		List<Location> locationlList = new ArrayList<Location>();
		for (org.yash.rms.domain.Location location : locations) {
			locationlList.add(mapper.map(location, Location.class));
		}
		return locationlList;
	}

	public org.yash.rms.domain.Module convertDTOObjectToDomain(Module module) {
		return mapper.map(module, org.yash.rms.domain.Module.class);
	}

	public Module convertDomainToDTO(org.yash.rms.domain.Module module) {
		return mapper.map(module, Module.class);
	}

	public List<Module> convertModuleDomainListToDTOList(List<org.yash.rms.domain.Module> modules) {
		List<Module> invoiceBylList = new ArrayList<Module>();
		for (org.yash.rms.domain.Module module : modules) {
			invoiceBylList.add(mapper.map(module, Module.class));
		}
		return invoiceBylList;
	}

	public org.yash.rms.domain.InvoiceBy convertDTOObjectToDomain(InvoiceBy invoiceBy) {
		return mapper.map(invoiceBy, org.yash.rms.domain.InvoiceBy.class);
	}

	public InvoiceBy convertDomainToDTO(org.yash.rms.domain.InvoiceBy invoiceBy) {
		return mapper.map(invoiceBy, InvoiceBy.class);
	}

	public List<InvoiceBy> convertInvoiceDomainListToDTOList(List<org.yash.rms.domain.InvoiceBy> invoiceBies) {
		List<InvoiceBy> invoiceBylList = new ArrayList<InvoiceBy>();
		for (org.yash.rms.domain.InvoiceBy invoiceBy : invoiceBies) {
			invoiceBylList.add(mapper.map(invoiceBy, InvoiceBy.class));
		}
		return invoiceBylList;
	}

	public org.yash.rms.domain.Ownership convertDTOObjectToDomain(Ownership ownership) {

		return mapper.map(ownership, org.yash.rms.domain.Ownership.class);
	}

	public Ownership convertDomainToDTO(org.yash.rms.domain.Ownership ownership) {
		return mapper.map(ownership, Ownership.class);
	}

	public List<Ownership> convertOwnershipDomainListToDTOList(List<org.yash.rms.domain.Ownership> ownerships) {
		List<Ownership> ownershiplList = new ArrayList<Ownership>();
		for (org.yash.rms.domain.Ownership ownership : ownerships) {
			ownershiplList.add(mapper.map(ownership, Ownership.class));
		}
		return ownershiplList;
	}

	public org.yash.rms.domain.ProjectCategory convertDTOObjectToDomain(ProjectCategory category) {

		return mapper.map(category, org.yash.rms.domain.ProjectCategory.class);
	}

	public ProjectCategory convertDomainToDTO(org.yash.rms.domain.ProjectCategory projectCategory) {
		return mapper.map(projectCategory, ProjectCategory.class);
	}

	public List<ProjectCategory> convertprojectCategoryDomainListToDTOList(List<org.yash.rms.domain.ProjectCategory> categories) {
		List<ProjectCategory> projectcategorylList = new ArrayList<ProjectCategory>();
		for (org.yash.rms.domain.ProjectCategory category : categories) {
			projectcategorylList.add(mapper.map(category, ProjectCategory.class));
		}
		return projectcategorylList;
	}

	public org.yash.rms.domain.ProjectMethodology convertDTOObjectToDomain(ProjectMethodology ProjectMethodology) {

		return mapper.map(ProjectMethodology, org.yash.rms.domain.ProjectMethodology.class);
	}

	public ProjectMethodology convertDomainToDTO(org.yash.rms.domain.ProjectMethodology projectCategory) {
		return mapper.map(projectCategory, ProjectMethodology.class);
	}

	public List<ProjectMethodology> convertprojectMethodologyDomainListToDTOList(List<org.yash.rms.domain.ProjectMethodology> categories) {
		List<ProjectMethodology> projectmethodologylList = new ArrayList<ProjectMethodology>();
		for (org.yash.rms.domain.ProjectMethodology category : categories) {
			projectmethodologylList.add(mapper.map(category, ProjectMethodology.class));
		}
		return projectmethodologylList;
	}

	public org.yash.rms.domain.Skills convertDTOObjectToDomain(Skills skills) {

		return mapper.map(skills, org.yash.rms.domain.Skills.class);
	}

	public Skills convertDomainToDTO(org.yash.rms.domain.Skills skills) {
		return mapper.map(skills, Skills.class);
	}

	public List<Skills> convertskillsDomainListToDTOList(List<org.yash.rms.domain.Skills> skills) {
		List<Skills> skillsList = new ArrayList<Skills>();
		for (org.yash.rms.domain.Skills category : skills) {
			skillsList.add(mapper.map(category, Skills.class));
		}
		return skillsList;
	}

	public org.yash.rms.domain.Visa convertDTOObjectToDomain(Visa visa) {

		return mapper.map(visa, org.yash.rms.domain.Visa.class);
	}

	public org.yash.rms.dto.Visa convertDomainToDTO(org.yash.rms.domain.Visa visa) {
		return mapper.map(visa, org.yash.rms.dto.Visa.class);
	}

	public List<Visa> convertvisaDomainListToDTOList(List<org.yash.rms.domain.Visa> visas) {
		List<Visa> visaList = new ArrayList<Visa>();
		for (org.yash.rms.domain.Visa category : visas) {
			visaList.add(mapper.map(category, Visa.class));
		}
		return visaList;
	}

	public List<CustomerDTO> convertCustomerDomainListToDTOList(List<org.yash.rms.domain.Customer> customers) {
		List<CustomerDTO> customerList = new ArrayList<CustomerDTO>();
		for (org.yash.rms.domain.Customer customer : customers) {
			customerList.add(mapper.map(customer, CustomerDTO.class));
		}
		return customerList;
	}


	public org.yash.rms.domain.Customer convertCustomerDTOObjectToDomain(CustomerDTO customer) {
		org.yash.rms.domain.Customer customerDomain = null;

		if (null != customer) {
			customerDomain = mapper.map(customer, org.yash.rms.domain.Customer.class);

			/*
			 * to avoid HibernateException A collection with
			 * cascade=all-delete-orphan was no longer referenced by the
			 * owning entity instance org.yash.rms.domain.Customer.CustomerPo.
			 * Since Customer is not owning entity so need to add customerPo
			 * explicitly
			 */

			Set<CustomerPo> costomerPOSet = new HashSet<CustomerPo>();
			CustomerPo customerPo = new CustomerPo();
			customerPo.setCustomerId(customerDomain);
			customerDomain.setCustomerPoes(costomerPOSet);
			if(customer.getCustGroups()!=null)
			{
			List<CustomerGroupDTO> cutGroupDtoList=customer.getCustGroups();
			List<CustomerGroup> custGroupList=new ArrayList<CustomerGroup>();
			for(CustomerGroupDTO custGroupDto : cutGroupDtoList)
			{
				CustomerGroup custGroup=convertCustomerGroupDTOToCustomerGroup(custGroupDto);
				custGroupList.add(custGroup);
		}
			customerDomain.setCustGroups(custGroupList);
			}
		}
		return customerDomain;
	}

	public CustomerDTO convertCustomerDomainToDTO(org.yash.rms.domain.Customer customerDomain) {
		CustomerDTO customerDto = new CustomerDTO();
		customerDto = mapper.map(customerDomain, CustomerDTO.class);
		List<CustomerGroupDTO> custGroupDtoList=new ArrayList<CustomerGroupDTO>();
		List<CustomerGroup> custGroupList=customerDomain.getCustGroups();
		for(CustomerGroup custGroup : custGroupList)
		{
			CustomerGroupDTO custGroupDto=convertCustomerGroupToCustomerGroupDTO(custGroup);
			custGroupDtoList.add(custGroupDto);
		}
		customerDto.setCustGroups(custGroupDtoList);
		return customerDto;
	}

	public List<UserNotification> convertUserNotificationDomainToDTOList(List<org.yash.rms.domain.UserNotification> UserNotification) {
		List<UserNotification> userNotificationList = new ArrayList<UserNotification>();
		for (org.yash.rms.domain.UserNotification userNotification : UserNotification) {
			userNotificationList.add(mapper.map(userNotification, UserNotification.class));
		}
		return userNotificationList;
	}

	public Resource convertResourceFormDTOResourceDomain(NewEmployee resourceForm) {
		Resource resource = null;
		if (resourceForm != null) {
			resource = mapper.map(resourceForm, Resource.class);
		}
		if (resource.getDeploymentLocation().getId() == -1) {
			resource.setDeploymentLocation(null);
		}
		resource.setFile(resourceForm.getFile());
		return resource;
	}

	// added by purva for US3119
	public DefaultProject convertDefaultProjectFormTODefaultProjectDomain(DefaultProjectForm projectForm) {
		DefaultProject defaultProject = null;
		if (projectForm != null) {
			defaultProject = mapper.map(projectForm, DefaultProject.class);
		}

		return defaultProject;
	}

	public ProjectDTO convertProjectDomaintoDto(org.yash.rms.domain.Project project) {

		ProjectDTO project2 = new ProjectDTO();

		if (project.getId() != null) {
			project2.setProjectId(project.getId().intValue());
		}
		if (project.getInvoiceById() != null) {
			project2.setInvoiceByName(project.getInvoiceById().getName());
		}

		if (project.getCustomerNameId() != null) {
			project2.setCustomerCode(project.getCustomerNameId().getCode());
			project2.setCustomerName(project.getCustomerNameId().getCustomerName());
		}
		project2.setProjectName(project.getProjectName().trim());

		project2.setCustomerType(project.isDeere());

		project2.setOffshoreManager(project.getOffshoreDelMgr().getFirstName() + " " + project.getOffshoreDelMgr().getLastName());
		if(project.getDeliveryMgr()!=null)
			project2.setDeliveryManager(project.getDeliveryMgr().getFirstName() + " " + project.getDeliveryMgr().getLastName());
		project2.setOnsiteManager(project.getOnsiteDelMgr());
		if (project.getEngagementModelId() != null) {
			project2.setEngagementMode(project.getEngagementModelId().getEngagementModelName());
		}
		if (project.getProjectKickOff() != null) {
			// String projectKickOffDate =
			// df.format(project.getProjectKickOff());
			project2.setProjectKickOffDate(project.getProjectKickOff().toString());
		}
		if (project.getPlannedProjSize() != null) {
			project2.setPlannedProjectSize(project.getPlannedProjSize());
		}
		if (project.getProjectTrackingCurrencyId() != null) {
			project2.setCurrencyName(project.getProjectTrackingCurrencyId().getCurrencyName());
		}
		if (project.getPlannedProjCost() != null) {
			project2.setPlannedProjectCost(project.getPlannedProjCost());
		}

		if (project.getOrgHierarchy() != null) {
			project2.setOrgHierarchy(convertOrgHierarchyDomainToDTO(project.getOrgHierarchy()));
		}
		if (project.isManagerReadonly()) {
			project2.setManagerReadonly(true);
		}else
		{
			project2.setManagerReadonly(false);
		}

		if (project.getOrgHierarchy() != null) {
			project2.setBuCode(project.getOrgHierarchy().getParentId().getName() + "-" + project.getOrgHierarchy().getName());
		}
		return project2;
	}

	public Set<OrgHierarchyDTO> convertOrgHierarchyDomainToOrgHierarchyDTO(Set<OrgHierarchy> orgHierarchies) {
		Set<OrgHierarchyDTO> orgHierarchyDTOs = new HashSet<OrgHierarchyDTO>();

		for (OrgHierarchy orgHierarchy : orgHierarchies) {
			OrgHierarchyDTO hierarchyDTO = new OrgHierarchyDTO();
			hierarchyDTO.setActive(orgHierarchy.isActive());

			if (orgHierarchy.getDescription() != null)
				hierarchyDTO.setDescription(orgHierarchy.getDescription());

			if (orgHierarchy.getEmployeeId() != null)
				hierarchyDTO.setEmployeeId(convertResourceDomainToResourceDTO(orgHierarchy.getEmployeeId()));

			if (orgHierarchy.getId() != null)
				hierarchyDTO.setId(orgHierarchy.getId());

			if (orgHierarchy.getName() != null)
				hierarchyDTO.setName(orgHierarchy.getName());

			if (orgHierarchy.getParentId() != null)
				hierarchyDTO.setParentId(convertOrgHierarchyDomainToDTO(orgHierarchy.getParentId()));

			if (orgHierarchy.getUser() != null)
				hierarchyDTO.setUser(orgHierarchy.getUser());

			orgHierarchyDTOs.add(hierarchyDTO);
		}

		return orgHierarchyDTOs;
	}

	public OrgHierarchyDTO convertOrgHierarchyDomainToDTO(OrgHierarchy orgHierarchy) {
		OrgHierarchyDTO hierarchyDTO = new OrgHierarchyDTO();

		if (orgHierarchy.getDescription() != null)
			hierarchyDTO.setDescription(orgHierarchy.getDescription());

		if (orgHierarchy.getEmployeeId() != null)
			hierarchyDTO.setEmployeeId(convertResourceDomainToResourceDTO(orgHierarchy.getEmployeeId()));

		if (orgHierarchy.getId() != null)
			hierarchyDTO.setId(orgHierarchy.getId());

		if (orgHierarchy.getName() != null)
			hierarchyDTO.setName(orgHierarchy.getName());

		if (orgHierarchy.getParentId() != null)
			hierarchyDTO.setParentId(convertOrgHierarchyDomainToDTO(orgHierarchy.getParentId()));

		if (orgHierarchy.getUser() != null)
			hierarchyDTO.setUser(orgHierarchy.getUser());

		return hierarchyDTO;
	}

	public ResourceDTO convertResourceDomainToResourceDTO(Resource resource) {
		ResourceDTO resourceDTO = new ResourceDTO();

		if (resource.getDesignationId() != null) {
			resourceDTO.setDesignationId(convertDomainToDTO(resource.getDesignationId()));
		}

		if (resource.getEmployeeId() != null)
			resourceDTO.setEmployeeId(resource.getEmployeeId());

		if (resource.getFirstName() != null)
			resourceDTO.setFirstName(resource.getFirstName());

		if (resource.getLastName() != null)
			resourceDTO.setLastName(resource.getLastName());

		if (resource.getMiddleName() != null)
			resourceDTO.setMiddleName(resource.getMiddleName());

		if (resource.getUserRole() != null)
			resourceDTO.setUserRole(resource.getUserRole());

		if (resource.getYashEmpId() != null)
			resourceDTO.setYashEmpId(resource.getYashEmpId());

		if (resource.getEmployeeName() != null) {
			resourceDTO.getEmployeeName();
		}

		return resourceDTO;
	}

	public org.yash.rms.domain.Project convertProjectDtotoDomain(ProjectDTO project) {
		org.yash.rms.domain.Project project2 = new org.yash.rms.domain.Project();
		project2 = mapper.map(project, org.yash.rms.domain.Project.class);
		return project2;

	}

	public ProjectDTO convertProjectDomainToDTO(Project project) {
		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO = mapper.map(project, ProjectDTO.class);
		return projectDTO;
	}

	public List<ProjectDTO> convertMgrViewDomaintoDto(List<org.yash.rms.domain.Project> projectList) {
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		Iterator iterator = projectList.iterator();
		List<OrgHierarchy> buList = UserUtil.getCurrentResource().getAccessRight();
		UserContextDetails currentResource = UserUtil.getCurrentResource();
	
		Integer id=0;
		if(currentResource!=null)
		{
		id = currentResource.getEmployeeId();
		}
		while (iterator.hasNext()) {
			org.yash.rms.domain.Project pro = (org.yash.rms.domain.Project) iterator.next();

			ProjectDTO project = new ProjectDTO();
			project.setProjectId(pro.getId());
			project.setProjectCode(pro.getProjectCode());
			project.setProjectName(pro.getProjectName());
			buList.contains(pro.getOrgHierarchy());
			if ((pro.getOffshoreDelMgr().getEmployeeId().equals(id) || pro.getDeliveryMgr().getEmployeeId().equals(id)) || buList.contains(pro.getOrgHierarchy()) ) { //enable all project for delivery manager instead of one
				project.setManagerReadonly(false);
			}else
			{
				project.setManagerReadonly(true);
			}
			project.setCustomerName(pro.getCustomerNameId().getCustomerName());
			project.setCustomerType(pro.isDeere());
			project.setOffshoreManager(pro.getOffshoreDelMgr().getEmployeeName());
			if(pro.getDeliveryMgr()!=null)
				project.setDeliveryManager(pro.getDeliveryMgr().getEmployeeName());
			project.setCustomerCode(pro.getCustomerNameId().getCode());
			// project.setCurrencyName(pro.getProjectTrackingCurrencyId().getCurrencyName());
			project.setOrgHierarchy(convertOrgHierarchyDomainToDTO(pro.getOrgHierarchy()));
			if (pro.getInvoiceById() != null) {
				project.setInvoiceByName(pro.getInvoiceById().getName());
			}
			if (pro.getOrgHierarchy() != null) {
				project.setBuCode(pro.getOrgHierarchy().getParentId().getName() + "-" + pro.getOrgHierarchy().getName());
			}

			if (pro.getOnsiteDelMgr() != null) {
				project.setOnsiteManager(pro.getOnsiteDelMgr());
			}
			if (pro.getProjectKickOff() != null) {
				project.setProjectKickOffDate(pro.getProjectKickOff().toString());
			}
			if (pro.getPlannedProjSize() != null) {
				project.setPlannedProjectSize(pro.getPlannedProjSize());
			}
			if (pro.getPlannedProjCost() != null) {
				project.setPlannedProjectCost(pro.getPlannedProjCost());
			}
			if (pro.getProjectEndDate() != null) {
				project.setProjectEndDate(pro.getProjectEndDate().toString());
			}

			if (pro.getEngagementModelId() != null) {
				project.setEngagementMode(pro.getEngagementModelId().getEngagementModelName());
			}

			list.add(project);

		}
		return list;

	}

	public Set<ProjectDTO> convertProjectDomaintoDtoSet(Set<org.yash.rms.domain.Project> projectList) {
		Set<ProjectDTO> projectSet = new HashSet<ProjectDTO>();
		Iterator iterator = projectList.iterator();

		while (iterator.hasNext()) {
			org.yash.rms.domain.Project pro = (org.yash.rms.domain.Project) iterator.next();

			ProjectDTO project = new ProjectDTO();
			project.setProjectId(pro.getId());
			;
			project.setProjectCode(pro.getProjectCode());
			project.setProjectName(pro.getProjectName());
			project.setCustomerName(pro.getCustomerNameId().getCustomerName());
			project.setCustomerType(pro.isDeere());
			project.setOffshoreManager(pro.getOffshoreDelMgr().getEmployeeName());
			project.setDeliveryManager(pro.getDeliveryMgr().getEmployeeName());
			project.setCustomerCode(pro.getCustomerNameId().getCode());
			// project.setCurrencyName(pro.getProjectTrackingCurrencyId().getCurrencyName());
			project.setOrgHierarchy(convertOrgHierarchyDomainToDTO(pro.getOrgHierarchy()));
			if (pro.getInvoiceById() != null) {
				project.setInvoiceByName(pro.getInvoiceById().getName());
			}
			if (pro.getOrgHierarchy() != null) {
				project.setBuCode(pro.getOrgHierarchy().getParentId().getName() + "-" + pro.getOrgHierarchy().getName());
			}

			if (pro.getOnsiteDelMgr() != null) {
				project.setOnsiteManager(pro.getOnsiteDelMgr());
			}
			if (pro.getProjectKickOff() != null) {
				project.setProjectKickOffDate(pro.getProjectKickOff().toString());
			}
			if (pro.getPlannedProjSize() != null) {
				project.setPlannedProjectSize(pro.getPlannedProjSize());
			}
			if (pro.getPlannedProjCost() != null) {
				project.setPlannedProjectCost(pro.getPlannedProjCost());
			}
			if (pro.getProjectEndDate() != null) {
				project.setProjectEndDate(pro.getProjectEndDate().toString());
			}

			if (pro.getEngagementModelId() != null) {
				project.setEngagementMode(pro.getEngagementModelId().getEngagementModelName());
			}

			projectSet.add(project);

		}
		return projectSet;

	}

	//We added ReleaseDate as a variable in TimehrsViewsDTO for identify the Active/InActive Resource in ResourceAllocation
	public List<TimehrsViewsDTO> convertTimehrsViewDomainToDTOList(List<org.yash.rms.domain.TimehrsView> adminList) {
		List<TimehrsViewsDTO> allResources = new ArrayList<TimehrsViewsDTO>();
		Iterator iterator = adminList.iterator();
		while (iterator.hasNext()) {

			org.yash.rms.domain.TimehrsView obj = (org.yash.rms.domain.TimehrsView) iterator.next();
			TimehrsViewsDTO view = new TimehrsViewsDTO();
				view.setEmployeeId(obj.getEmployeeId());
				view.setYashEmpId(obj.getYashEmpId());
				view.setEmployeeName(obj.getEmployeeName());
				view.setDesignation_name(obj.getDesignationName());
				view.setGrade(obj.getGrade());
				view.setCurBgBu(obj.getCurrentBgBu());
				view.setLocName(obj.getLocation());
			// view.setPlannedCapacity(obj.getPlannedCapacity());
			// view.setActualCapacity(obj.getActualCapacity());
			if (obj.getTotalPlannedHrs() != null) {
				view.setTotalPlannedHrs(obj.getTotalPlannedHrs());
			}
			if (obj.getTotalReportedHrs() != null) {
				view.setTotalReportedHrs(obj.getTotalReportedHrs());
			}
			if (obj.getTotalBilledHrs() != null) {
				view.setTotalBilledHrs(obj.getTotalBilledHrs());
			}
			view.setDateOfJoining(obj.getDateOfJoining());
			view.setCurrentProject(obj.getCurrentProject());
			view.setReleaseDate(obj.getReleaseDate());
			allResources.add(view);
		}

		return allResources;
	}

	public TimeSheet covertUserActivityToTimeSheet(UserActivity ua) {
		// TODO Auto-generated method stub
		return mapper.map(ua, TimeSheet.class);
	}

	public ResourceLoanTransfer convertResourceLoanTransferToDomain(ResourceLoanTransferForm resourceLoanTransferForm) {
		ResourceLoanTransfer resourceLoanTransfer = null;
		if (resourceLoanTransferForm != null) {
			resourceLoanTransfer = mapper.map(resourceLoanTransferForm, ResourceLoanTransfer.class);
		}
		return resourceLoanTransfer;
	}

	public org.yash.rms.domain.ReleaseNotes convertDTOObjectToDomain(ReleaseNotes releaseNote) {
		return mapper.map(releaseNote, org.yash.rms.domain.ReleaseNotes.class);
	}

	public ReleaseNotes convertDomainToDTO(org.yash.rms.domain.ReleaseNotes releaseNote) {
		return mapper.map(releaseNote, ReleaseNotes.class);
	}

	public List<ReleaseNotes> convertReleaseNotesDomainListToDTOList(List<org.yash.rms.domain.ReleaseNotes> releaseNoteList) {
		List<ReleaseNotes> releasenoteList = new ArrayList<ReleaseNotes>();
		for (org.yash.rms.domain.ReleaseNotes releaseNote : releaseNoteList) {
			releasenoteList.add(mapper.map(releaseNote, ReleaseNotes.class));
		}
		return releasenoteList;
	}

	public org.yash.rms.domain.EmployeeCategory convertDTOObjectToDomain(EmployeeCategory employeeCategory) {

		return mapper.map(employeeCategory, org.yash.rms.domain.EmployeeCategory.class);
	}

	public EmployeeCategory convertDomainToDTO(org.yash.rms.domain.EmployeeCategory employeeCategory) {
		return mapper.map(employeeCategory, EmployeeCategory.class);
	}

	public List<EmployeeCategory> convertEmployeeCategoryDomainListToDTOList(List<org.yash.rms.domain.EmployeeCategory> employeeCategories) {
		List<EmployeeCategory> employeeCategorylList = new ArrayList<EmployeeCategory>();
		for (org.yash.rms.domain.EmployeeCategory employeeCategory : employeeCategories) {
			employeeCategorylList.add(mapper.map(employeeCategory, EmployeeCategory.class));
		}
		return employeeCategorylList;
	}

	public org.yash.rms.domain.Competency convertDTOObjectToDomain(Competency competency) {

		return mapper.map(competency, org.yash.rms.domain.Competency.class);
	}

	public Competency convertDomainToDTO(org.yash.rms.domain.Competency competency) {
		return mapper.map(competency, Competency.class);
	}

	public List<Competency> convertCompetencyDomainListToDTOList(List<org.yash.rms.domain.Competency> competencies) {
		List<Competency> competencylList = new ArrayList<Competency>();
		for (org.yash.rms.domain.Competency competency : competencies) {
			competencylList.add(mapper.map(competency, Competency.class));
		}
		return competencylList;
	}

	public List<Roles> convertRoleDomainListToDTOList(List<org.yash.rms.domain.Roles> roles) {
		List<Roles> roleList = new ArrayList<Roles>();
		for (org.yash.rms.domain.Roles role : roles) {
			roleList.add(mapper.map(role, Roles.class));
		}
		return roleList;
	}

	public org.yash.rms.domain.Roles convertDTOObjectToDomain(org.yash.rms.domain.Roles role) {
		return mapper.map(role, org.yash.rms.domain.Roles.class);
	}

	public List<org.yash.rms.domain.Project> convertProjectDTOToDomainList(List<ProjectDTO> projects) {
		List<org.yash.rms.domain.Project> prjList = new ArrayList<org.yash.rms.domain.Project>();
		// iterate list
		for (ProjectDTO project2 : projects) {
			org.yash.rms.domain.Project prj = new org.yash.rms.domain.Project();
			prj = mapper.map(project2, org.yash.rms.domain.Project.class);
			prjList.add(prj);
		}
		return prjList;

	}

	public CATicket convertCATicketFormDTOCATicketDomain(CATicketForm caticketform) {
		CATicket caTicket = new CATicket();
		// org.yash.rms.domain.Project project = new
		// org.yash.rms.domain.Project();
		// Resource resource = new Resource();
		try {

			if (caticketform != null) {

				/*
				 * caTicket.setCaTicketNo(caticketform.getCaTicketNo());
				 * caTicket.setDescription(caticketform.getDescription());
				 * caTicket.setPriority(caticketform.getPriority());
				 * project.setId(caticketform.getModuleId().getId());
				 * caTicket.setModuleId(project);
				 * caTicket.setLandscape(caticketform.getLandscape());
				 * resource.setEmployeeId
				 * (caticketform.getAssigneeId().getEmployeeId());
				 * caTicket.setAssigneeId(resource);
				 */

				caTicket = mapper.map(caticketform, CATicket.class);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return caTicket;
	}
	
	/**
	   * This method maps UserActivity list to TimesheetSubmissionDTOList
	   * 
	   * @param userActivityList
	   * @param employeeId
	   * @return List<TimesheetSubmissionDTO>
	   */
	  public static List<TimesheetSubmissionDTO> convertUserActivityDomainToTimesheetSubmissionDTO(List<UserActivity> userActivityList, Integer employeeId){
		
		  List<TimesheetSubmissionDTO> timesheetSubmissionDTOs = new ArrayList<TimesheetSubmissionDTO>();
		  
		  for (UserActivity userActivity : userActivityList) {
			  
			  TimesheetSubmissionDTO timesheetSubmissionDTO = new TimesheetSubmissionDTO();
			  	timesheetSubmissionDTO.setId(userActivity.getId());
			  	timesheetSubmissionDTO.setEmployeeId(employeeId);
			  	timesheetSubmissionDTO.setProjectId(userActivity.getResourceAllocId().getProjectId().getId());
			  	timesheetSubmissionDTO.setProjectName(userActivity.getResourceAllocId().getProjectId().getProjectName());
			  	timesheetSubmissionDTO.setResourceAllocationId(userActivity.getResourceAllocId().getId());
			  	timesheetSubmissionDTO.setStatus(userActivity.getStatus().toString());
			  	timesheetSubmissionDTO.setTotalHours(userActivity.getRepHrsForProForWeekEndDate());
			  	timesheetSubmissionDTO.setWeekEndDate(userActivity.getWeekEndDate());
			  	timesheetSubmissionDTO.setWeekStartDate(userActivity.getWeekStartDate());
			  timesheetSubmissionDTOs.add(timesheetSubmissionDTO);
		  }
		  
		  return timesheetSubmissionDTOs;
		  
	  }
	  
	/**
	 * Maps UserActivity Domain to NewtimeSheet DTO.
	 * 
	 * @param userActivityList
	 * @param minWeekStartDate
	 * @param maxWeekStartDate
	 * @param employeeId
	 * @return List<NewTimeSheet>
	 */
	public static List<NewTimeSheet> convertUserActivityDomainToNewTimeSheet(List<UserActivity> userActivityList, Date minWeekStartDate, Date maxWeekStartDate, Integer employeeId) {
		
		List<NewTimeSheet> newTimeSheets = new ArrayList<NewTimeSheet>();
			
		for (UserActivity userActivity : userActivityList) {
			
			NewTimeSheet newTimeSheet = new NewTimeSheet();
				newTimeSheet.setId(userActivity.getId());
				if(userActivity.getActivityId()!=null)
				 {
				  newTimeSheet.setActivityId(Integer.valueOf(userActivity.getActivityId().getId()).toString());
				  newTimeSheet.setActivityType(userActivity.getActivityId().getActivityType());
				 }
				newTimeSheet.setModule(userActivity.getModule());
				newTimeSheet.setSubModule(userActivity.getSubModule());
				newTimeSheet.setTicketNo(userActivity.getTicketNo());
				newTimeSheet.setApproveStatus(userActivity.getApproveStatus());
				newTimeSheet.setEmployeeId(userActivity.getEmployeeId().getEmployeeId());
				newTimeSheet.setRejectionRemarks(userActivity.getRejectionRemarks());
				newTimeSheet.setRepHrsForProForWeekEndDate(userActivity.getRepHrsForProForWeekEndDate());
				newTimeSheet.setProjectName(userActivity.getResourceAllocId().getProjectId().getProjectName());
				newTimeSheet.setResourceAllocEndDate(userActivity.getResourceAllocId().getAllocEndDate());
				newTimeSheet.setResourceAllocStartDate(userActivity.getResourceAllocId().getAllocStartDate());
				newTimeSheet.setYashEmpId(userActivity.getEmployeeId().getYashEmpId());
				newTimeSheet.setEmployeeName(userActivity.getEmployeeId().getEmployeeName());
				if (userActivity.getCustomActivityId() != null) {
					newTimeSheet.setCustomActivityId(userActivity.getCustomActivityId().getId());
				}
				
				if (userActivity.getApproveStatus() == 'N'){
					newTimeSheet.setSubmitStatus(false);
				} else { 
					newTimeSheet.setSubmitStatus(true);
				}
				
				if (userActivity.getResourceAllocId() != null) {
					newTimeSheet.setResourceAllocId(userActivity.getResourceAllocId().getId());
				}
				
				newTimeSheet.setWeekStartDate(userActivity.getWeekStartDate());
				newTimeSheet.setWeekEndDate(userActivity.getWeekEndDate());
				
				newTimeSheet.setD1Hours(userActivity.getD1Hours());
				newTimeSheet.setD2Hours(userActivity.getD2Hours());
				newTimeSheet.setD3Hours(userActivity.getD3Hours());
				newTimeSheet.setD4Hours(userActivity.getD4Hours());
				newTimeSheet.setD5Hours(userActivity.getD5Hours());
				newTimeSheet.setD6Hours(userActivity.getD6Hours());
				newTimeSheet.setD7Hours(userActivity.getD7Hours());
				
				newTimeSheet.setD1Comment(userActivity.getD1Comment());
				newTimeSheet.setD2Comment(userActivity.getD2Comment());
				newTimeSheet.setD3Comment(userActivity.getD3Comment());
				newTimeSheet.setD4Comment(userActivity.getD4Comment());
				newTimeSheet.setD5Comment(userActivity.getD5Comment());
				newTimeSheet.setD6Comment(userActivity.getD6Comment());
				newTimeSheet.setD7Comment(userActivity.getD7Comment());
				newTimeSheet.setTicketPriority(userActivity.getTicketPriority());
			    newTimeSheet.setTicketStatus(userActivity.getTicketStatus());
						
			newTimeSheets.add(newTimeSheet);
		}
		
		return newTimeSheets;
	}

	public List<UserActivityViewDTO> convertUserActivityDomainToUserActivityDTOList(List<UserActivity> userActivities) {
		List<UserActivityViewDTO> userActivityDTOs = new ArrayList<UserActivityViewDTO>();
		//UserActivityHelper.prepareTotalWeekHours(userActivities );
		for (UserActivity userActivity : userActivities) {
			UserActivityViewDTO activityDTO = new UserActivityViewDTO();

			if (userActivity.getApproveCode() != null) {
				activityDTO.setApproveCode(userActivity.getApproveCode());
			}

			if (userActivity.getBilledHrs() != null) {
				activityDTO.setBilledHrs(userActivity.getBilledHrs());
			}

			if (userActivity.getEmployeeId() != null) {
				activityDTO.setEmployeeId(convertResourceDomainToResourceDTO(userActivity.getEmployeeId()));
			}

			if (userActivity.getId() != null) {
				activityDTO.setId(userActivity.getId());
			}

			if (userActivity.getPlannedHrs() != null) {
				activityDTO.setPlannedHrs(userActivity.getPlannedHrs());
			}

			if (userActivity.getResourceAllocId() != null) {
				activityDTO.setResourceAllocId(fromresourceAllocationDomainToDtoForTimesheetApproval(userActivity.getResourceAllocId()));

			}

			if (userActivity.getRejectCode() != null) {
				activityDTO.setRejectCode(userActivity.getRejectCode());
			}

			if (userActivity.getRejectionRemarks() != null) {
				activityDTO.setRejectionRemarks(userActivity.getRejectionRemarks());
			}else{
				activityDTO.setRejectionRemarks("");
			}

			if (userActivity.getRemarks() != null) {
				activityDTO.setRemarks(userActivity.getRemarks());
			}else{
				activityDTO.setRemarks("");
			}

			if (userActivity.getRepHrsForProForWeekEndDate() != null) {
				activityDTO.setRepHrsForProForWeekEndDate(userActivity.getRepHrsForProForWeekEndDate());
			}

			if (userActivity.getApproveStatus() != null) {
				activityDTO.setStatus(userActivity.getApproveStatus());
			}

			if (userActivity.getTimeHrsId() != null) {
				activityDTO.setTimeHrsId(userActivity.getTimeHrsId());
			}

			if (userActivity.getWeekEndDate() != null) {
				activityDTO.setWeekEndDate(userActivity.getWeekEndDate());
			}

			if (userActivity.getWeekStartDate() != null) {
				activityDTO.setWeekStartDate(userActivity.getWeekStartDate());
			}
			activityDTO.setViewFlag(userActivity.isViewFlag());
		
			if(userActivity.getPlannedHrs() != null){
				activityDTO.setPlannedHrs(userActivity.getPlannedHrs());
			}else{
				activityDTO.setPlannedHrs(0.0);
			}
			
			if(userActivity.getBilledHrs() != null){
				activityDTO.setBilledHrs(userActivity.getBilledHrs());
			}else{
				activityDTO.setBilledHrs(0.0);
			}
			
			if(userActivity.getActivityId() != null){
				activityDTO.setActivityId(convertActivityDomainToDTO(userActivity.getActivityId()));
			}
			
			if(userActivity.getCustomActivityId() != null){
				activityDTO.setCustomActivityId(convertCustomActivityDomainToDTO(userActivity.getCustomActivityId()));
			}
			userActivityDTOs.add(activityDTO);
		}

		return userActivityDTOs;
	}
	
	
	public CustomActivityDTO convertCustomActivityDomainToDTO(CustomActivity customActivity){
		CustomActivityDTO customActivityDTO = new CustomActivityDTO();
		
		if(customActivity.getId() != 0){
			customActivityDTO.setId(customActivity.getId());
		}
		
		if(customActivity.getActivityName() != null){
			customActivityDTO.setActivityName(customActivity.getActivityName());
		}
		
		if(customActivity.getFormat() != null){
			customActivityDTO.setFormat(customActivity.getFormat());
		}
		
		if(customActivity.getMax() == 0){
			customActivityDTO.setMax(customActivity.getMax());
		}
		
		customActivityDTO.setActive(customActivity.isActive());
		customActivityDTO.setProductive(customActivity.isProductive());
		customActivityDTO.setMandatory(customActivity.isMandatory());
		
		
		return customActivityDTO;
		
	}
	
	public ActivitiesDTO convertActivityDomainToDTO(Activity activity){
		ActivitiesDTO activitiesDTO = new ActivitiesDTO();
		
		if(activity.getId() != 0){
			activitiesDTO.setActivityId(activity.getId());
		}
		
		if(activity.getActivityName() != null){
			activitiesDTO.setActivityName(activity.getActivityName());
		}
		
		if(activity.getFormat() != null){
			activitiesDTO.setFormat(activity.getFormat());
		}
		
		if(activity.getMax() == 0){
			activitiesDTO.setMax(activity.getMax());
		}
		
		activitiesDTO.setType(activity.getType());
		activitiesDTO.setProductive(activity.isProductive());
		activitiesDTO.setMandatory(activity.isMandatory());
		return activitiesDTO;
		
	}
	

	// duplicate method for timesheet approval web api
	public ResourceAllocationDTO fromresourceAllocationDomainToDtoForTimesheetApproval(ResourceAllocation domain) {
		ResourceAllocationDTO allocationDto = new ResourceAllocationDTO();
		allocationDto.setProjectId(convertProjectDomainToDtoForTimesheetApproval(domain.getProjectId()));
		allocationDto.setId(domain.getId());
		allocationDto.setResourceAllocationEndDate(domain.getAllocEndDate());

		return allocationDto;

	}

	public ProjectDTO convertProjectDomainToDtoForTimesheetApproval(Project project) {
		ProjectDTO projectDTO = new ProjectDTO();

		projectDTO.setProjectId(project.getId());
		projectDTO.setProjectName(project.getProjectName());

		return projectDTO;
	}
	
	public List<UserActivityViewDTO> convertNewTimeSheetToUserActivityViewDTOList(List<NewTimeSheet> newTimeSheets){
		List<UserActivityViewDTO> userActivityViewDTOs = new ArrayList<UserActivityViewDTO>();
		
		for (NewTimeSheet newTimeSheet : newTimeSheets) {
			UserActivityViewDTO activityViewDTO = new UserActivityViewDTO();
			
			if(newTimeSheet.getActivityId() != null){
				ActivitiesDTO activityDTO = new ActivitiesDTO();
				activityDTO.setActivityId(Integer.parseInt(newTimeSheet.getActivityId()));
				activityDTO.setActivityType(newTimeSheet.getActivityType());
				activityViewDTO.setActivityId(activityDTO);
			}
			
			activityViewDTO.setD1Comment(newTimeSheet.getD1Comment());
			activityViewDTO.setD2Comment(newTimeSheet.getD2Comment());
			activityViewDTO.setD3Comment(newTimeSheet.getD3Comment());
			activityViewDTO.setD4Comment(newTimeSheet.getD4Comment());
			activityViewDTO.setD5Comment(newTimeSheet.getD5Comment());
			activityViewDTO.setD6Comment(newTimeSheet.getD6Comment());
			activityViewDTO.setD7Comment(newTimeSheet.getD7Comment());
			
			activityViewDTO.setD1Hours(newTimeSheet.getD1Hours());
			activityViewDTO.setD2Hours(newTimeSheet.getD2Hours());
			activityViewDTO.setD3Hours(newTimeSheet.getD3Hours());
			activityViewDTO.setD4Hours(newTimeSheet.getD4Hours());
			activityViewDTO.setD5Hours(newTimeSheet.getD5Hours());
			activityViewDTO.setD6Hours(newTimeSheet.getD6Hours());
			activityViewDTO.setD7Hours(newTimeSheet.getD7Hours());
			
			ResourceDTO resourceDTO = new ResourceDTO();
			
			resourceDTO.setEmployeeId(newTimeSheet.getEmployeeId());
			resourceDTO.setYashEmpId(newTimeSheet.getYashEmpId());
		//	resourceDTO.setEmployeeName(newTimeSheet.getEmployeeName());
			
			activityViewDTO.setEmployeeId(resourceDTO);
			
			activityViewDTO.setRepHrsForProForWeekEndDate(newTimeSheet.getRepHrsForProForWeekEndDate());
			activityViewDTO.setId(newTimeSheet.getId());
			if(newTimeSheet.getModule() == null || newTimeSheet.getModule().isEmpty() || newTimeSheet.getModule().equalsIgnoreCase("null")) {
				newTimeSheet.setModule("");
			}
			activityViewDTO.setModule(newTimeSheet.getModule());
			activityViewDTO.setSubModule(newTimeSheet.getSubModule());
			
			if(newTimeSheet.getViewFlag() != null){
				activityViewDTO.setViewFlag(newTimeSheet.getViewFlag());	
			}
			
			activityViewDTO.setTicketNo(newTimeSheet.getTicketNo());
			activityViewDTO.setWeekEndDate(newTimeSheet.getWeekEndDate());
			activityViewDTO.setWeekStartDate(newTimeSheet.getWeekStartDate());
			
			ResourceAllocationDTO allocationDTO = new ResourceAllocationDTO();
			ProjectDTO projectDTO = new ProjectDTO();
			
			projectDTO.setProjectName(newTimeSheet.getProjectName());
			allocationDTO.setProjectId(projectDTO);
			allocationDTO.setId(newTimeSheet.getResourceAllocId());
			allocationDTO.setResourceAllocationEndDate(newTimeSheet.getResourceAllocEndDate());
			allocationDTO.setResourceAllocationStartDate(newTimeSheet.getResourceAllocStartDate());
			
			activityViewDTO.setResourceAllocId(allocationDTO);
			activityViewDTO.setStatus(newTimeSheet.getApproveStatus());
			activityViewDTO.setRejectionRemarks(newTimeSheet.getRejectionRemarks());
			activityViewDTO.setTicketPriority(newTimeSheet.getTicketPriority());
			activityViewDTO.setTicketStatus(newTimeSheet.getTicketStatus());
			
			userActivityViewDTOs.add(activityViewDTO);
			
		}
		
		return userActivityViewDTOs;
	}
	public EditProfileDTO convertUserProfileFormToEditProfileDTO(UserProfileForm userProfileForm) {
		
		UserProfile userProfile = userProfileForm.getUserProfile(); 
		
		EditProfileDTO editProfileDTO = new EditProfileDTO();
			editProfileDTO.setYashEmployeeId(userProfile.getYashEmpId());
			editProfileDTO.setFirstName(userProfile.getFirstName());
			editProfileDTO.setLastName(userProfile.getLastName());
			editProfileDTO.setMiddleName(userProfile.getMiddleName());
			editProfileDTO.setEmailId(userProfile.getEmailId());
			editProfileDTO.setContactNumberOne(userProfile.getContactNumberOne());
			editProfileDTO.setContactNumberTwo(userProfile.getContactNumberTwo());
			editProfileDTO.setCustomerIdDetail(userProfile.getCustomerIdDetail());
			editProfileDTO.setRelevantExp(userProfile.getRelevantExp());
			editProfileDTO.setTotalExp(userProfile.getTotalExp());
			System.out.println("----DozermapperUtility - "+userProfile.getYashEmpId()+" Resource's image ----" + userProfile.getUploadImage());
			editProfileDTO.setUploadImage(userProfile.getUploadImage());
			
			ResourceDTO resourceDTO = userProfileForm.getResource();
			editProfileDTO.setUploadResume(resourceDTO.getUploadResume());
			editProfileDTO.setUploadResumeFileName(resourceDTO.getUploadResumeFileName());
			editProfileDTO.setPreferredLocation(resourceDTO.getPreferredLocation());
			
		  List<SkillsMappingDTO> userSelectedPrimarySkillsMappingDTO = new ArrayList<SkillsMappingDTO>();
		  List<SkillsMappingDTO> userSelectedSecondarySkillsMappingDTO = new ArrayList<SkillsMappingDTO>();
		
		List<SkillsMapping> skillsMappings = userProfileForm.getEntries();
		SkillsMappingDTO skillsMappingDTO = null;
			
		for (SkillsMapping skillsMapping : skillsMappings) {
			
			 skillsMappingDTO = new SkillsMappingDTO();
			
			//TODO: PrimarySkill_Type should also set.
			if (skillsMapping.getPrimarySkill_Id() != null && skillsMapping.getPrimarySkill_Type() != null && skillsMapping.getPrimarySkill_Type().equals("Primary")) {
				skillsMappingDTO.setPrimarySkillId(skillsMapping.getPrimarySkill_Id());
				skillsMappingDTO.setPrimarySkillPKId(skillsMapping.getPrimarkSkillPKId());
				skillsMappingDTO.setPrimarySkillRatingId(skillsMapping.getPrimarySkillRating_Id());
				skillsMappingDTO.setPrimarySkillType(skillsMapping.getPrimarySkill_Type());
				
				skillsMappingDTO.setPrimaryExperience(skillsMapping.getPrimaryExperience());
				userSelectedPrimarySkillsMappingDTO.add(skillsMappingDTO);
			}
			
			if (skillsMapping.getSecondarySkill_Id() != null && skillsMapping.getSecondarySkill_Type() != null && skillsMapping.getSecondarySkill_Type().equals("Secondary")) {
				skillsMappingDTO.setSecondarySkillId(skillsMapping.getSecondarySkill_Id());
				skillsMappingDTO.setSecondarySkillPKId(skillsMapping.getSecondarySkillPKId());
				skillsMappingDTO.setSecondarySkillRatingId(skillsMapping.getSecondarySkillRating_Id());
				skillsMappingDTO.setSecondarySkillType(skillsMapping.getSecondarySkill_Type());
				skillsMappingDTO.setExperience(skillsMapping.getSecondaryExperience());
				userSelectedSecondarySkillsMappingDTO.add(skillsMappingDTO);
			}
		}
		
		editProfileDTO.setUserProfilePrimarySkillList(userSelectedPrimarySkillsMappingDTO);
		editProfileDTO.setUserProfileSecondarySkillList(userSelectedSecondarySkillsMappingDTO);
		
		return editProfileDTO;
	}
	
	public List<RequestRequisitionSkillDTO> requestRequisitionDomainToDTOList(List<RequestRequisitionSkill> requestRequisitionSkillDomainList, boolean isLoadSubTable){
		
		
		List<RequestRequisitionSkillDTO> requestRequisitionSkillDTOList = new ArrayList<RequestRequisitionSkillDTO>();
		
		for (RequestRequisitionSkill domain : requestRequisitionSkillDomainList) {
			RequestRequisitionSkillDTO dto = new RequestRequisitionSkillDTO();
			dto.setAdditionalComments(domain.getAdditionalComments());
			dto.setCareerGrowthPlan(domain.getCareerGrowthPlan());
			dto.setComments(domain.getComments());
			
			dto.setDesirableSkills(domain.getDesirableSkills());
			dto.setExperience(domain.getExperience());
			dto.setFulfilled(domain.getFulfilled());
			dto.setHold(domain.getHold());
			dto.setLost(domain.getLost());
			dto.setId(domain.getId());
			dto.setKeyInterviewersOne(domain.getKeyInterviewersOne());
			dto.setKeyInterviewersTwo(domain.getKeyInterviewersTwo());
			dto.setKeyScanners(domain.getKeyScanners());
			dto.setNoOfResources(domain.getNoOfResources());
			dto.setPrimarySkills(domain.getPrimarySkills());
			dto.setRemaining(domain.getRemaining());
			
			// mapper.map(, requestRequisitionDTO); //Custom Mapper 
			
			dto.setRequirementId(domain.getRequirementId());
			dto.setResponsibilities(domain.getResponsibilities());
			
			dto.setTargetCompanies(domain.getTargetCompanies());
			dto.setTimeFrame(domain.getTimeFrame());
			dto.setType(domain.getType());
			dto.setOpen(domain.getOpen());
			dto.setNotFitForRequirement(domain.getNotFitForRequirement());
			dto.setSubmissions(domain.getSubmissions());
			dto.setShortlisted(domain.getShortlisted());
			dto.setClosed(domain.getClosed());
			
			dto.setSent_req_id(domain.getSent_req_id());
			dto.setPdl_list(domain.getPdl_list());
				dto.setStatus(domain.getStatus());
				dto.setLocationName(domain.getLocation().getLocation());
			dto.setClientName(domain.getRequestRequisition().getCustomer().getCustomerName());
				dto.setBusinessGroupName(domain.getRequestRequisition().getResource().getCurrentBuId().getParentId().getName() + "-" + domain.getRequestRequisition().getResource().getCurrentBuId().getName());
			dto.setRequestedBy(domain.getRequestRequisition().getResource().getEmployeeName());
				dto.setRequestRequisition(requisitionFormMapper.convertDomainToDTO(domain.getRequestRequisition()));
			AllocationTypeDTO allocationTypeDTO = new AllocationTypeDTO();
				mapper.map(domain.getAllocationType(), allocationTypeDTO);
				dto.setAllocationType(allocationTypeDTO);
			DesignationDTO designationDTO = new DesignationDTO();
				mapper.map(domain.getDesignation(), designationDTO);
				dto.setDesignation(designationDTO);
				
			SkillsDTO skillsDTO = new SkillsDTO();
				mapper.map(domain.getSkill(), skillsDTO);
				dto.setSkill(skillsDTO);
			if (domain.getOpen()!=null && domain.getOpen() == 0) {
					dto.setRecordStatus("Inactive");
			}else{
				dto.setRecordStatus("Active");
			}
			
			dto.setRequirementArea(domain.getRequirementArea());
			if( isLoadSubTable && domain.getRequestRequisitionResources().size()>0){
				List<RequisitionResourceDTO> requisitionResourceDTOList = new ArrayList<RequisitionResourceDTO>();
				for(RequestRequisitionResource requestRequisitionResource :  domain.getRequestRequisitionResources()){
					RequisitionResourceDTO requisitionResourceDTO =  requisitionFormMapper.covertDomainToDTO(requestRequisitionResource);
					requisitionResourceDTOList.add(requisitionResourceDTO);
				}
				dto.setRequisitionResourceList(requisitionResourceDTOList);
			}
			requestRequisitionSkillDTOList.add(dto);
		}
		return requestRequisitionSkillDTOList;
	}
	
	public CustomerGroupDTO convertCustomerGroupToCustomerGroupDTO(CustomerGroup customerGroup) {
		CustomerGroupDTO customerGroupDTO = new CustomerGroupDTO();
		customerGroupDTO.setGroupId(customerGroup.getGroupId());
		customerGroupDTO.setGroupName(customerGroup.getCustomerGroupName());
		customerGroupDTO.setGroupEmail(customerGroup.getCustGroupemailId());
		customerGroupDTO.setActive(customerGroup.getActive());
		return customerGroupDTO;
	}
	public CustomerGroup convertCustomerGroupDTOToCustomerGroup(CustomerGroupDTO customerGroupDto) {
		CustomerGroup customerGroup = new CustomerGroup();
		customerGroup.setGroupId(customerGroupDto.getGroupId());
		customerGroup.setCustomerGroupName(customerGroupDto.getGroupName());
		customerGroup.setCustGroupemailId(customerGroupDto.getGroupEmail());
		customerGroup.setActive(customerGroupDto.getActive());
		return customerGroup;
	}
	
		public static List<ResourceCommentDTO> convertResourceCommentToResourceCommentDTO(
			List<ResourceComment> resourceCommentList) {
		
		
		List<ResourceCommentDTO> dtoList = new ArrayList<ResourceCommentDTO>();
		
		for (ResourceComment domain : resourceCommentList) {
			ResourceCommentDTO dto = new ResourceCommentDTO();
			//SimpleDateFormat foramtter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			/*Calendar calendar = new GregorianCalendar();
			TimeZone timeZone = calendar.getTimeZone();*/
			//foramtter.setTimeZone(TimeZone.getTimeZone("IST"));
			
			//String date = foramtter.format(domain.getComment_Date());
			dto.setComment_Date(DateFormatUtils.format(domain.getComment_Date(), "yyyy-MM-dd HH:mm:SS"));
			System.out.println(DateFormatUtils.format(domain.getComment_Date(), "yyyy-MM-dd HH:mm:SS"));
			dto.setCommentBy(domain.getCommentBy());
			dto.setResourceComment(domain.getResourceComment());
			dto.setResourceCommentId(domain.getResourceCommentId());
			dto.setResourceId(domain.getResourceId());
			dtoList.add(dto);
		}
		return dtoList;
	}
		public PDLEmailGroup convertDTOObjectToDomain(PDLEmailGroupDTO pdlEmailGroupDto) {
			return mapper.map(pdlEmailGroupDto, PDLEmailGroup.class);
		}

		public PDLEmailGroupDTO convertDomainToDTO(PDLEmailGroup pdlEmailGroup) {
			return mapper.map(pdlEmailGroup, PDLEmailGroupDTO.class);
		}

		public List<PDLEmailGroupDTO> convertPDLDomainListToDTOList(List<PDLEmailGroup> pdlList) 
		{
			List<PDLEmailGroupDTO> pdlDtoList = new ArrayList<PDLEmailGroupDTO>();
			for (PDLEmailGroup pdl : pdlList) {
				pdlDtoList.add(mapper.map(pdl, PDLEmailGroupDTO.class));
			}
			return pdlDtoList;
		}
		
		public List<ReasonForReplacementDTO> convertReasonsDomainToDTO(List<ReasonForReplacement> domainList){
			
			List<ReasonForReplacementDTO> dtoList = new ArrayList<ReasonForReplacementDTO>();
				for (ReasonForReplacement domain : domainList) {
					dtoList.add(mapper.map(domain, ReasonForReplacementDTO.class));
				}
			
			return dtoList;	
			
		}
		
	public List<InfogramActiveResourceDTO> convertInfogramActiveResourceDomainListToDTO(List<InfogramActiveResource> domainList, String allExistNewVar) {

		List<InfogramActiveResourceDTO> dtoList = new ArrayList<InfogramActiveResourceDTO>();
		
		for (InfogramActiveResource domain : domainList) {
			InfogramActiveResourceDTO dto = new InfogramActiveResourceDTO();
			dto.setBaseLocation(domain.getBaseLocation());
			dto.setBghEmailId(domain.getBghEmailId());
			dto.setBghEmployeeId(domain.getBghEmployeeId());
			dto.setBghName(domain.getBghName());
			dto.setBuhEmailId(domain.getBuhEmailId());
			dto.setBuhEmployeeId(domain.getBuhEmployeeId());
			dto.setBuhName(domain.getBuhName());
			dto.setBusinessGroup(domain.getBusinessGroup());
			dto.setBusinessUnit(domain.getBusinessUnit());
			dto.setCreatedTime(domain.getCreationTimestamp());
			dto.setCurrentLocation(domain.getCurrentLocation());
			dto.setDateOfBirth(domain.getDateOfBirth());
			dto.setDateOfJoining(domain.getDateOfJoining());
			dto.setDesignation(domain.getDesignation());
			dto.setEmailId(domain.getEmailId());
			dto.setEmployeeCategory(domain.getEmployeeCategory());
			dto.setEmployeeType(domain.getEmployeeType());
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setGender(domain.getGender());
			dto.setGrade(domain.getGrade());
			dto.setHrbpEmailId(domain.getHrbpEmailId());
			dto.setHrbpEmployeeId(domain.getHrbpEmployeeId());
			dto.setHrbpName(domain.getHrbpName());
			dto.setId(domain.getId());
			dto.setIrmEmailId(domain.getIrmEmailId());
			dto.setIrmEmployeeId(domain.getIrmEmployeeId());
			dto.setIrmName(domain.getIrmName());
			dto.setLocationDivision(domain.getLocationDivision());
			dto.setName(domain.getName());
			dto.setResignedDate(domain.getResignedDate());
			dto.setSrmEmailId(domain.getSrmEmailId());
			dto.setSrmEmployeeId(domain.getSrmEmployeeId());
			dto.setSrmName(domain.getSrmName());
			dto.setStatus(domain.getStatus());
			dto.setProcessStatus(domain.getProcessStatus());
			dto.setFailureReason(domain.getFailureReason());

//			Resource resource =  resourceService.findResourcesByYashEmpIdEquals(domain.getEmployeeId());
//			if(null != resource) {
////				dto.setResourceType(Constants.RESOURCE_TYPE_OLD);
////				dto.setLocationInRMS(resource.getLocationId());
////				dto.setIrmInRMS(resource.getCurrentReportingManager());
////				dto.setSrmInRMS(resource.getCurrentReportingManagerTwo());
////				dto.setBuIdInRMS(resource.getBuId());
//			} else {
//				dto.setResourceType(Constants.RESOURCE_TYPE_NEW);
//			}
			Resource resource =  resourceService.findResourcesByYashEmpIdEquals(domain.getEmployeeId());
            if(allExistNewVar.equalsIgnoreCase(Constants.RESOURCE_TYPE_NEW)) {
            	dto.setResourceType(Constants.RESOURCE_TYPE_NEW);
            } else if(allExistNewVar.equalsIgnoreCase(Constants.RESOURCE_TYPE_EXISTING)) {
            	if(resource!=null) {
            		dto.setResourceType(Constants.RESOURCE_TYPE_OLD);
                    if(resource.getLocationId()!=null) {
            		dto.setLocationInRMS(resource.getLocationId().getLocation());
                    }else {
                    	dto.setLocationInRMS("NA");
                    	//System.out.println("dto.setLocationInRMS "+ dto.getLocationInRMS());
                    }
                    dto.setIrmInRMS(resource.getCurrentReportingManager().getEmployeeName());
                    dto.setSrmInRMS(resource.getCurrentReportingManagerTwo().getEmployeeName());
                    dto.setRmsBu(resource.getBuId().getName());
                    dto.setRmsBg(resource.getBuId().getParentId().getName());
                    String bgbu = resource.getBuId().getParentId().getName() + "" + resource.getBuId().getName();
                    dto.setBuIdInRMS(bgbu);	
            	}
            	
            } else {
            	if(resource!=null) {
            		dto.setResourceType(Constants.RESOURCE_TYPE_OLD);
                    dto.setLocationInRMS(resource.getLocationId().getLocation());
                    dto.setIrmInRMS(resource.getCurrentReportingManager().getEmployeeName());
                    dto.setSrmInRMS(resource.getCurrentReportingManagerTwo().getEmployeeName());
                    dto.setRmsBu(resource.getBuId().getName());
                    dto.setRmsBg(resource.getBuId().getParentId().getName());
                    String bgbu = resource.getBuId().getParentId().getName() + "" + resource.getBuId().getName();
                    dto.setBuIdInRMS(bgbu);
            	} else {
            		dto.setResourceType(Constants.RESOURCE_TYPE_NEW);
            	}
            } 

			dtoList.add(dto);
		}

		return dtoList;
	}
		public List<PriorityDTO> mapPriorityDomainToDTO(List<Priority> domainList){
			List<PriorityDTO> dtoList = new ArrayList<PriorityDTO>();
			
			for (Priority domain : domainList) {
				dtoList.add(mapper.map(domain, PriorityDTO.class));
			}
			
			return dtoList;
			
		}
		
		public List<InfogramActiveResource> convertInfogramActiveResourceDTOListToDomainList(List<InfogramActiveResourceDTO> dtoList) {

			List<InfogramActiveResource> domainList = new ArrayList<InfogramActiveResource>();
			
			for (InfogramActiveResourceDTO activeDTO : dtoList) {
				InfogramActiveResource activeDomain = new InfogramActiveResource();
				activeDomain.setBaseLocation(activeDTO.getBaseLocation());
				activeDomain.setBghEmailId(activeDTO.getBghEmailId());
				activeDomain.setBghEmployeeId(activeDTO.getBghEmployeeId());
				activeDomain.setBghName(activeDTO.getBghName());
				activeDomain.setBuhEmailId(activeDTO.getBuhEmailId());
				activeDomain.setBuhEmployeeId(activeDTO.getBuhEmployeeId());
				activeDomain.setBuhName(activeDTO.getBuhName());
				activeDomain.setBusinessGroup(activeDTO.getBusinessGroup());
				activeDomain.setBusinessUnit(activeDTO.getBusinessUnit());
				activeDomain.setCreationTimestamp(activeDTO.getCreatedTime());
				activeDomain.setCurrentLocation(activeDTO.getCurrentLocation());
				activeDomain.setDateOfBirth(activeDTO.getDateOfBirth());
				activeDomain.setDateOfJoining(activeDTO.getDateOfJoining());
				activeDomain.setDesignation(activeDTO.getDesignation());
				activeDomain.setEmailId(activeDTO.getEmailId());
				activeDomain.setEmployeeCategory(activeDTO.getEmployeeCategory());
				activeDomain.setEmployeeType(activeDTO.getEmployeeType());
				activeDomain.setEmployeeId(activeDTO.getEmployeeId());
				activeDomain.setGender(activeDTO.getGender());
				activeDomain.setGrade(activeDTO.getGrade());
				activeDomain.setHrbpEmailId(activeDTO.getHrbpEmailId());
				activeDomain.setHrbpEmployeeId(activeDTO.getHrbpEmployeeId());
				activeDomain.setHrbpName(activeDTO.getHrbpName());
				activeDomain.setId(activeDTO.getId());
				activeDomain.setIrmEmailId(activeDTO.getIrmEmailId());
				activeDomain.setIrmEmployeeId(activeDTO.getIrmEmployeeId());
				activeDomain.setIrmName(activeDTO.getIrmName());
				activeDomain.setLocationDivision(activeDTO.getLocationDivision());
				activeDomain.setName(activeDTO.getName());
				activeDomain.setResignedDate(activeDTO.getResignedDate());
				activeDomain.setSrmEmailId(activeDTO.getSrmEmailId());
				activeDomain.setSrmEmployeeId(activeDTO.getSrmEmployeeId());
				activeDomain.setSrmName(activeDTO.getSrmName());
				activeDomain.setStatus(activeDTO.getStatus());
				activeDomain.setProcessStatus(activeDTO.getProcessStatus());
				activeDomain.setFailureReason(activeDTO.getFailureReason());
				domainList.add(activeDomain);
			}

			return domainList;
		}
		
		public List<InfogramInactiveResourceDTO> convertInfogramInactiveResourceDomainToDTO(List<InfogramInactiveResource> domainList){
			List<InfogramInactiveResourceDTO> dtos = new ArrayList<InfogramInactiveResourceDTO>();
			for (InfogramInactiveResource domain : domainList) {
				dtos.add(mapper.map(domain, InfogramInactiveResourceDTO.class));
			}
			return dtos;
		}

		public RequestRequisitionSkill copyRequestRequisitionSkillEntityToAnotherEntity(RequestRequisitionSkill requestRequisitionSkill) {
		
			RequestRequisitionSkill copiedObject = new RequestRequisitionSkill();
			copiedObject.setAdditionalComments(requestRequisitionSkill.getAdditionalComments());
			copiedObject.setCareerGrowthPlan(requestRequisitionSkill.getCareerGrowthPlan());
			copiedObject.setComments(requestRequisitionSkill.getComments());
			copiedObject.setDesirableSkills(requestRequisitionSkill.getDesirableSkills());
			copiedObject.setExperience(requestRequisitionSkill.getExperience());
			copiedObject.setFulfilled(requestRequisitionSkill.getFulfilled());
			copiedObject.setHold(0);
			copiedObject.setLost(0);
			copiedObject.setKeyInterviewersOne(requestRequisitionSkill.getKeyInterviewersOne());
			copiedObject.setKeyInterviewersTwo(requestRequisitionSkill.getKeyInterviewersTwo());
			copiedObject.setKeyScanners(requestRequisitionSkill.getKeyScanners());
			copiedObject.setNoOfResources(requestRequisitionSkill.getNoOfResources());
			/*String requirementId = requestRequisitionSkill.getRequirementId();
			int lastIndexOfHyphen = requirementId.lastIndexOf('-');
			String trimmedRequirementId = requirementId.substring(0, lastIndexOfHyphen+1);
			copiedObject.setRequirementId(trimmedRequirementId);*/
			copiedObject.setPrimarySkills(requestRequisitionSkill.getPrimarySkills());
			copiedObject.setRemaining(requestRequisitionSkill.getRemaining());
			copiedObject.setResponsibilities(requestRequisitionSkill.getResponsibilities());
			copiedObject.setTargetCompanies(requestRequisitionSkill.getTargetCompanies());
			copiedObject.setTimeFrame(requestRequisitionSkill.getTimeFrame());
			copiedObject.setType(requestRequisitionSkill.getType());
			copiedObject.setOpen(requestRequisitionSkill.getNoOfResources());
			copiedObject.setNotFitForRequirement(0);
			copiedObject.setSubmissions(0);
			copiedObject.setShortlisted(0);
			copiedObject.setClosed(0);
			//copiedObject.setSent_req_id(requestRequisitionSkill.getSent_req_id());
			//copiedObject.setPdl_list(requestRequisitionSkill.getPdl_list());
			copiedObject.setStatus(Constants.OPEN);
			copiedObject.setLocation(requestRequisitionSkill.getLocation());
			RequestRequisition requestRequisition = requisitionFormMapper.convertRequestRequisitionEntityToDomainForCopyRRF(requestRequisitionSkill.getRequestRequisition());
			requestRequisitionDao.save(requestRequisition);
			copiedObject.setRequestRequisition(requestRequisition);
			copiedObject.setAllocationType(requestRequisitionSkill.getAllocationType());
			copiedObject.setDesignation(requestRequisitionSkill.getDesignation());
			copiedObject.setSkill(requestRequisitionSkill.getSkill());
			copiedObject.setSkillsToEvaluate(requestRequisitionSkill.getSkillsToEvaluate());
			copiedObject.setRequirementArea(requestRequisitionSkill.getRequirementArea());
			
		return copiedObject;
	}

		public RequestRequisitionFormDTO convertRequestRequisitionSkillToRequestRequisitionFormDTO(
			RequestRequisitionSkill requestRequisitionSkill) {
		RequestRequisitionFormDTO requestRequisitionFormDTO = new RequestRequisitionFormDTO();
		
		RequestRequisitionDTO requestRequisitionDto = requisitionFormMapper.convertDomainToDTO(requestRequisitionSkill.getRequestRequisition());
		
		if (requestRequisitionDto != null) {
			requestRequisitionFormDTO.setRequestRequisitionDto(requestRequisitionDto);
		}
		Resource loggedInResource = userUtil.getLoggedInResource();
		requestRequisitionDto.setSendMailFrom(loggedInResource.getEmailId());
		List<RequestRequisitionSkill> list = new ArrayList<RequestRequisitionSkill>();
		list.add(requestRequisitionSkill);
		List<RequestRequisitionSkillDTO> requestRequisitionSkillDTOs = requestRequisitionSkillMapper
				.convertDomainsToDTOs(list);
		
		if (!requestRequisitionSkillDTOs.isEmpty()) {
			requestRequisitionFormDTO.setRequestRequisitionSkillDto(requestRequisitionSkillDTOs);
		}
		
		if(requestRequisitionDto.getCcMailIds()==null && requestRequisitionDto.getNotifyMailToList()!=null) {
			List<Resource> ccMailsResources =  requestRequisitionDto.getNotifyMailToList();
			String[] ccMailIds = new String[ccMailsResources.size()];
			for (int j = 0; j < ccMailIds.length; j++) {
				if(!ccMailsResources.get(j).getEmailId().isEmpty())
				ccMailIds[j] = ccMailsResources.get(j).getEmailId();
				requestRequisitionDto.setCcMailIds(ccMailIds);
			}
		}
		
		if (requestRequisitionDto.getToMailIds() == null && requestRequisitionDto.getSentMailToList() != null) {
			List<Resource> toMailResources = requestRequisitionDto.getSentMailToList();
			String[] toMailIds = new String[toMailResources.size() + 1];

			if (loggedInResource.getEmailId() != null) {
				toMailIds[0] = loggedInResource.getEmailId();
			}

			for (int j = 1, k=0; j < toMailIds.length; j++, k++) {
				if (!toMailResources.get(k).getEmailId().isEmpty())
					toMailIds[j] = toMailResources.get(k).getEmailId();
				requestRequisitionDto.setToMailIds(toMailIds);
				
			}

		} else {
			String[] toMailId = new String[1];
			if (loggedInResource.getEmailId() != null) {
				toMailId[0] = loggedInResource.getEmailId();
				requestRequisitionDto.setToMailIds(toMailId);
			}
		}
		
		return requestRequisitionFormDTO;
	}
		
			
}
