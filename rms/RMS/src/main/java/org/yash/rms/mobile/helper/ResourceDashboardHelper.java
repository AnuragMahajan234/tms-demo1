package org.yash.rms.mobile.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yash.rms.domain.Rating;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.SkillResourcePrimary;
import org.yash.rms.domain.SkillResourceSecondary;
import org.yash.rms.domain.Skills;
import org.yash.rms.domain.SkillsMapping;
import org.yash.rms.dto.DesignationDTO;
import org.yash.rms.dto.EditProfileDTO;
import org.yash.rms.dto.ProjectDTO;
import org.yash.rms.dto.RatingDTO;
import org.yash.rms.dto.ResourceAllocationDTO;
import org.yash.rms.dto.ResourceDTO;
import org.yash.rms.dto.ResourceManagerDTO;
import org.yash.rms.dto.SkillResourcePrimaryDTO;
import org.yash.rms.dto.SkillResourceSecondaryDTO;
import org.yash.rms.dto.SkillsDTO;
import org.yash.rms.dto.SkillsMappingDTO;
import org.yash.rms.helper.ResourceHelper;
import org.yash.rms.service.DashBoardService;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.util.DozerMapperUtility;

@Component(value = "ResourceDashboardHelper")
public class ResourceDashboardHelper {

	@Autowired
	ResourceAllocationService resourceAllocationService;

	@Autowired
	DashBoardService dashBoardService;

	@Autowired
	private DozerMapperUtility mapper;

	public ResourceDTO convertResourceDomainToResourceDTO(Resource resource) {

		ResourceDTO resourceDTO = null;

		if (resource != null) {

			resourceDTO = new ResourceDTO();

			List<ResourceAllocation> resourceAllocationList = resourceAllocationService.findResourceAllocationByActiveTypeEmployeeId(resource);

			if (resource.getEmployeeId() != null) {
				resourceDTO.setEmployeeId(resource.getEmployeeId());
			}
			if (resource.getFirstName() != null) {
				resourceDTO.setFirstName(resource.getFirstName());
			}
			if (resource.getLastName() != null) {
				resourceDTO.setLastName(resource.getLastName());
			}
			if (resource.getMiddleName() != null) {
				resourceDTO.setMiddleName(resource.getMiddleName());
			}
			if (resource.getYashEmpId() != null) {
				resourceDTO.setYashEmpId(resource.getYashEmpId());
			}
			if (resource.getUserRole() != null) {
				resourceDTO.setUserRole(resource.getUserRole());
			}
			if (resource.getDateOfJoining() != null) {
				resourceDTO.setDateOfJoining(resource.getDateOfJoining());
			}
			if (resource.getEmailId() != null) {
				resourceDTO.setEmailId(resource.getEmailId());
			}
			if (resource.getUserName() != null) {
				resourceDTO.setUserName(resource.getUserName());
			}
			if (resource.getLocationId() != null) {
				resourceDTO.setPrimaryLocation(resource.getLocationId().getLocation());
			}
			if (resource.getDeploymentLocation() != null) {
				resourceDTO.setDeploymentLocation(resource.getDeploymentLocation().getLocation());
			}
			if (resource.getCurrentBuId() != null) {
				resourceDTO.setCurrentBuId(resource.getBuId().getParentId().getName() + "-" + resource.getBuId().getName());
			}
			if (resource.getBuId() != null) {
				resourceDTO.setParentBuId(resource.getBuId().getParentId().getName() + "-" + resource.getBuId().getName());
			}
			if (resource.getCurrentReportingManager() != null) {

				ResourceManagerDTO currentReportingManagerDto = new ResourceManagerDTO();

				currentReportingManagerDto.setYashEmployeeId(resource.getYashEmpId());

				currentReportingManagerDto.setEmployeeName(resource.getCurrentReportingManager().getFirstName().trim() + " " + resource.getCurrentReportingManager().getLastName().trim());

				currentReportingManagerDto.setDesignation(resource.getCurrentReportingManager().getDesignationId().getDesignationName());

				resourceDTO.setImmediateReportingManager(currentReportingManagerDto);
			}

			if (resource.getCurrentReportingManagerTwo() != null) {

				ResourceManagerDTO currentReportingManagerTwoDto = new ResourceManagerDTO();

				currentReportingManagerTwoDto.setYashEmployeeId(resource.getYashEmpId());

				currentReportingManagerTwoDto.setEmployeeName(resource.getCurrentReportingManagerTwo().getFirstName().trim() + " " + resource.getCurrentReportingManagerTwo().getLastName().trim());

				currentReportingManagerTwoDto.setDesignation(resource.getCurrentReportingManagerTwo().getDesignationId().getDesignationName());

				resourceDTO.setSeniorReportingManager(currentReportingManagerTwoDto);
			}

			if (resource.getGradeId() != null) {
				resourceDTO.setGrade(resource.getGradeId().getGrade());
			}

			if (resource.getOwnership() != null) {
				resourceDTO.setOwnership(resource.getOwnership().getOwnershipName());
			}
			if (resource.getCompetency() != null) {
				resourceDTO.setCompetency(resource.getCompetency().getSkill());
			}
			if (resource.getEmployeeCategory() != null) {
				resourceDTO.setEmployeeCategory(resource.getEmployeeCategory().getEmployeecategoryName());
			}
			if (resource.getDeploymentLocation() != null) {
				resourceDTO.setDeploymentLocation(resource.getDeploymentLocation().getLocation());
			}
			if (resource.getDesignationId() != null) {

				DesignationDTO dto2 = new DesignationDTO();

				dto2.setDesignationName(resource.getDesignationId().getDesignationName());

				resourceDTO.setDesignationId(dto2);

			}
			if (resource.getConfirmationDate() != null) {
				resourceDTO.setConfirmationDate(resource.getConfirmationDate());
			}
			if (resource.getReleaseDate() != null) {
				resourceDTO.setReleaseDate(resource.getReleaseDate());
			}

			if (resource.getSkillResourcePrimaries() != null) {

				List<SkillResourcePrimaryDTO> dtos = new ArrayList<SkillResourcePrimaryDTO>();

				Set<SkillResourcePrimary> skillResourcePrimaries = resource.getSkillResourcePrimaries();

				for (SkillResourcePrimary skillResourcePrimary : skillResourcePrimaries) {
					SkillResourcePrimaryDTO resourcePrimaryDTO = new SkillResourcePrimaryDTO();
					resourcePrimaryDTO.setPrimarySkillId(skillResourcePrimary.getId());
					resourcePrimaryDTO.setPrimarySkillName(skillResourcePrimary.getSkillId().getSkill());
					dtos.add(resourcePrimaryDTO);
				}

				resourceDTO.setResourcePrimarySkills(dtos);

			}

			if (resource.getSkillResourceSecondaries() != null) {

				List<SkillResourceSecondaryDTO> dtos = new ArrayList<SkillResourceSecondaryDTO>();

				Set<SkillResourceSecondary> skillResourceSecondaries = resource.getSkillResourceSecondaries();

				for (SkillResourceSecondary skillResourceSecondary : skillResourceSecondaries) {
					SkillResourceSecondaryDTO resourceSecondaryDTO = new SkillResourceSecondaryDTO();
					resourceSecondaryDTO.setSecondarySkillId(skillResourceSecondary.getId());
					resourceSecondaryDTO.setSecondarySkillName(skillResourceSecondary.getSkillId().getSkill());
					dtos.add(resourceSecondaryDTO);
				}

				resourceDTO.setResourceSecondarySkills(dtos);

			}

			if (null != resourceAllocationList) {

				List<ResourceAllocationDTO> resourceAllocationDTOList = new ArrayList<ResourceAllocationDTO>();

				for (ResourceAllocation resourceAllocation : resourceAllocationList) {

					if (null != resourceAllocation) {

						if (resourceAllocation.getProjectId().getId() != null) {

							ResourceAllocationDTO resourceAllocationDTO = new ResourceAllocationDTO();

							ProjectDTO dto = new ProjectDTO();

							dto.setProjectId(resourceAllocation.getProjectId().getId());
							dto.setProjectName(resourceAllocation.getProjectId().getProjectName());

							resourceAllocationDTO.setProjectId(dto);

							resourceAllocationDTO.setResourceAllocationStartDate(resourceAllocation.getAllocStartDate());
							resourceAllocationDTO.setResourceAllocationEndDate(resourceAllocation.getAllocEndDate());
							resourceAllocationDTO.setId(resourceAllocation.getId());
							resourceAllocationDTO.setPrimayProjectIndicator(resourceAllocation.getCurProj());

							resourceAllocationDTOList.add(resourceAllocationDTO);
						}
					}

				}
				resourceDTO.setResourceAllocations(resourceAllocationDTOList);

			}

		}
		return resourceDTO;

	}

	public EditProfileDTO convertResourceDomainToEditProfileDTO(Resource resource, List<Object[]> primarySkillsMappingList, List<Object[]> secondarySkillsMappingList, List<Rating> ratingList,List<Skills> primarySkillsList, List<Skills> secondarySkillsList) {

		EditProfileDTO editProfileDTO = new EditProfileDTO();
		List<SkillsMappingDTO> skillsMappingDTOListPrimary = new ArrayList<SkillsMappingDTO>();
		List<SkillsMappingDTO> skillsMappingDTOListSecondary = new ArrayList<SkillsMappingDTO>();
		List<SkillsDTO> SkillsDTOListPrimary = new ArrayList<SkillsDTO>();
		List<SkillsDTO> SkillsDTOListSecondary = new ArrayList<SkillsDTO>();
		List<RatingDTO> RatingDTOList = new ArrayList<RatingDTO>();

		if (resource != null) {
			editProfileDTO.setEmployeeId(resource.getEmployeeId());
			editProfileDTO.setYashEmployeeId(resource.getYashEmpId());
			editProfileDTO.setFirstName(resource.getFirstName());
			editProfileDTO.setMiddleName(resource.getMiddleName());
			editProfileDTO.setLastName(resource.getLastName());
			editProfileDTO.setEmailId(resource.getEmailId());
			editProfileDTO.setContactNumberOne(resource.getContactNumber());
			editProfileDTO.setContactNumberTwo(resource.getContactNumberTwo());
			editProfileDTO.setCustomerIdDetail(resource.getCustomerIdDetail());
			editProfileDTO.setGrade(resource.getGradeId().getGrade());
			editProfileDTO.setDesignationName(resource.getDesignationId().getDesignationName());
			
			editProfileDTO.setPreferredLocation(resource.getPreferredLocation());
			
			editProfileDTO.setUploadResumeFileName((resource.getUploadResumeFileName()!=null)?resource.getUploadResumeFileName():"");
			
			if(null != resource.getCurrentReportingManager() ){
				editProfileDTO.setResourceManager1(resource.getCurrentReportingManager().getEmployeeName());
			}
			
			if(null != resource.getCurrentReportingManagerTwo()){
				editProfileDTO.setResourceManager2(resource.getCurrentReportingManagerTwo().getEmployeeName());
			}
			
			editProfileDTO.setUploadImage(resource.getUploadImage());
			/*editProfileDTO.setTotalExp(resource.getTotalExper());
			editProfileDTO.setRelevantExp(resource.getRelevantExper());*/
			String yearDiff= Resource.getCalYearDiff(resource.getDateOfJoining(), resource.getReleaseDate());
			double diff=Double.parseDouble(yearDiff);
			editProfileDTO.setYashExp(yearDiff);
			/*Start - Code to add yash exp in Total and Relevant While DISPLAY ONLY*/
			double totalExp = Double.parseDouble(Resource.calcYear(resource.getTotalExper(), diff));
			double relevantExp = Double.parseDouble(Resource.calcYear(resource.getRelevantExper(), diff));
			
			editProfileDTO.setTotalExp(totalExp);
			editProfileDTO.setRelevantExp(relevantExp);
			/*End - Code to add yash exp in Total and Relevant*/
			if (resource.getUploadImage() != null) {
				System.out.println("----ResourceDashboardHelper -"+resource.getYashEmpId()+" Resource's image for ---"+resource.getUploadImage().toString());
			}
			
		}

		if (primarySkillsMappingList != null && primarySkillsMappingList.size() > 0) {
			List<SkillsMapping> skillsMappingListPrimary = ResourceHelper.getPrimarySkillSMapping(primarySkillsMappingList);

			if (skillsMappingListPrimary != null) {
				for (SkillsMapping skillsMapping : skillsMappingListPrimary) {
					SkillsMappingDTO SkillsMappingDTO = new SkillsMappingDTO();
					SkillsMappingDTO.setRatingId(skillsMapping.getRating_Id());
					SkillsMappingDTO.setSkillId(skillsMapping.getSkill_Id());
					SkillsMappingDTO.setPrimarySkillId(skillsMapping.getPrimarySkill_Id());
					SkillsMappingDTO.setSecondarySkillId(skillsMapping.getSecondarySkill_Id());
					SkillsMappingDTO.setPrimarySkillRatingId(skillsMapping.getPrimarySkillRating_Id());
					SkillsMappingDTO.setSecondarySkillRatingId(skillsMapping.getSecondarySkillRating_Id());
					SkillsMappingDTO.setSkillName(skillsMapping.getSkill_Name());
					SkillsMappingDTO.setPrimarySkillType(skillsMapping.getPrimarySkill_Type());
					SkillsMappingDTO.setSecondarySkillType(skillsMapping.getSecondarySkill_Type());
					SkillsMappingDTO.setRatingName(skillsMapping.getRating_Name());
					SkillsMappingDTO.setPrimarySkillPKId(skillsMapping.getPrimarkSkillPKId());
					SkillsMappingDTO.setSecondarySkillPKId(skillsMapping.getSecondarySkillPKId());
					SkillsMappingDTO.setPrimaryExperience(skillsMapping.getPrimaryExperience());
					skillsMappingDTOListPrimary.add(SkillsMappingDTO);
				}
			}
			editProfileDTO.setUserProfilePrimarySkillList(skillsMappingDTOListPrimary);
		}

		if (secondarySkillsMappingList != null && secondarySkillsMappingList.size() > 0) {
			List<SkillsMapping> skillsMappingListSecondary = ResourceHelper.getSecondarySkillSMapping(secondarySkillsMappingList);

			if (skillsMappingListSecondary != null) {
				for (SkillsMapping skillsMapping : skillsMappingListSecondary) {
					SkillsMappingDTO SkillsMappingDTO = new SkillsMappingDTO();
					SkillsMappingDTO.setRatingId(skillsMapping.getRating_Id());
					SkillsMappingDTO.setSkillId(skillsMapping.getSkill_Id());
					SkillsMappingDTO.setPrimarySkillId(skillsMapping.getPrimarySkill_Id());
					SkillsMappingDTO.setSecondarySkillId(skillsMapping.getSecondarySkill_Id());
					SkillsMappingDTO.setPrimarySkillRatingId(skillsMapping.getPrimarySkillRating_Id());
					SkillsMappingDTO.setSecondarySkillRatingId(skillsMapping.getSecondarySkillRating_Id());
					SkillsMappingDTO.setSkillName(skillsMapping.getSkill_Name());
					SkillsMappingDTO.setPrimarySkillType(skillsMapping.getPrimarySkill_Type());
					SkillsMappingDTO.setSecondarySkillType(skillsMapping.getSecondarySkill_Type());
					SkillsMappingDTO.setRatingName(skillsMapping.getRating_Name());
					SkillsMappingDTO.setPrimarySkillPKId(skillsMapping.getPrimarkSkillPKId());
					SkillsMappingDTO.setSecondarySkillPKId(skillsMapping.getSecondarySkillPKId());
					SkillsMappingDTO.setExperience(skillsMapping.getSecondaryExperience());
					skillsMappingDTOListSecondary.add(SkillsMappingDTO);
				}
			}
			editProfileDTO.setUserProfileSecondarySkillList(skillsMappingDTOListSecondary);
		}

		if (primarySkillsList != null) {
			for (Skills skill : primarySkillsList) {
				SkillsDTO skillDTO = new SkillsDTO();
				skillDTO.setId(skill.getId());
				skillDTO.setSkill(skill.getSkill());
				skillDTO.setSkillType(skill.getSkillType());
				skillDTO.setCreatedId(skill.getCreatedId());
				skillDTO.setCreationTimestamp(skill.getCreationTimestamp());
				skillDTO.setLastUpdatedId(skill.getLastUpdatedId());
				skillDTO.setLastupdatedTimestamp(skill.getLastupdatedTimestamp());
				SkillsDTOListPrimary.add(skillDTO);
			}
		}
		editProfileDTO.setPrimarySkillsList(SkillsDTOListPrimary);

		if (secondarySkillsList != null) {
			for (Skills skill : secondarySkillsList) {
				SkillsDTO skillDTO = new SkillsDTO();
				skillDTO.setId(skill.getId());
				skillDTO.setSkill(skill.getSkill());
				skillDTO.setSkillType(skill.getSkillType());
				skillDTO.setCreatedId(skill.getCreatedId());
				skillDTO.setCreationTimestamp(skill.getCreationTimestamp());
				skillDTO.setLastUpdatedId(skill.getLastUpdatedId());
				skillDTO.setLastupdatedTimestamp(skill.getLastupdatedTimestamp());
				SkillsDTOListSecondary.add(skillDTO);
			}
		}
		editProfileDTO.setSecondarySkillsList(SkillsDTOListSecondary);

		if (ratingList != null) {
			for (Rating rating : ratingList) {
				RatingDTO ratingDTO = new RatingDTO();
				ratingDTO.setName(rating.getName());
				ratingDTO.setDescription(rating.getDescription());
				ratingDTO.setId(rating.getId());
				ratingDTO.setCreatedId(rating.getCreatedId());
				ratingDTO.setCreationTimestamp(rating.getCreationTimestamp());
				ratingDTO.setLastUpdatedId(rating.getLastUpdatedId());
				ratingDTO.setLastupdatedTimestamp(rating.getLastupdatedTimestamp());
				RatingDTOList.add(ratingDTO);
			}
		}
		editProfileDTO.setRatingList(RatingDTOList);

		return editProfileDTO;
	}
}
