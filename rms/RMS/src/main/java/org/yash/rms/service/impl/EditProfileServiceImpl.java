package org.yash.rms.service.impl;

import java.sql.Blob;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.EditProfileDao;
import org.yash.rms.dao.UserProfileDao;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.SkillResourcePrimary;
import org.yash.rms.domain.SkillResourceSecondary;
import org.yash.rms.domain.Skills;
import org.yash.rms.domain.UserProfile;
import org.yash.rms.dto.EditProfileDTO;
import org.yash.rms.dto.SkillsMappingDTO;
import org.yash.rms.service.EditProfileService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.service.SkillResourcePrimaryService;
import org.yash.rms.service.SkillResourceSecondaryService;
import org.yash.rms.service.SkillsService;
import org.yash.rms.util.UserUtil;

@Service("editProfileService")
public class EditProfileServiceImpl implements EditProfileService,RmsCRUDService<UserProfile> {
	
	@Autowired 
	@Qualifier("editProfileDao")
	private EditProfileDao editProfileDao;
	
	@Autowired
	@Qualifier("SkillsService")
	private SkillsService skillsService;
	
	@Autowired 
	@Qualifier("userProfileDao")
	private UserProfileDao userProfileDao;
	
	@Autowired
	@Qualifier("ResourceService")
	private ResourceService resourceService;
	
	@Autowired
	@Qualifier("skillResourcePrimaryService")
	private SkillResourcePrimaryService skillResourcePrimaryService;
	
	@Autowired
	@Qualifier("skillResourceSecondaryService")
	private SkillResourceSecondaryService skillResourceSecondaryService;
	
	private static final Logger logger = LoggerFactory.getLogger(EditProfileServiceImpl.class);
	
	public List<UserProfile> findUserProfilesByLogicalDeleteEqualsAndYashEmpIdEquals(String logicalDelete, String yashEmpId) {
		return editProfileDao.findUserProfilesByLogicalDeleteEqualsAndYashEmpIdEquals(logicalDelete, yashEmpId);
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean saveOrupdate(UserProfile t) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<UserProfile> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserProfile> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<UserProfile> findUserProfilesByYashEmpIdEquals(String yashEmpId) {
		return editProfileDao.findUserProfilesByYashEmpIdEquals(yashEmpId);
	}

	public List<Object[]> getSkillsMapping(String yashEmpId,String query) {
		return editProfileDao.getSkillsMapping(yashEmpId,query);
	}

	/*Resume Download on dashboard edit profile ServiceImpl */
	@SuppressWarnings("unchecked")
	public Map<String, Object> downloadResume(Integer requestId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map = (Map<String, Object>) editProfileDao.getResumeById(requestId);
		
		byte[] file = (byte[]) map.get("file");
		Blob blobUploadFile = null;
		byte[] ResumeFile = null;

		if (null != file) {
			ResumeFile = file;
		}
		try {
			if ((null != ResumeFile && !ResumeFile.equals(""))) {
				blobUploadFile = new javax.sql.rowset.serial.SerialBlob(ResumeFile);
			}
		} catch (Exception e) {

		}
		map.put("file", blobUploadFile);
		return map;

	}
	
	public boolean saveOrUpdateUserProfile(EditProfileDTO editProfileDTO, boolean fromRestService){
		
		boolean isSuccess = true;
		SkillResourcePrimary skillResourcePrimary = null;
		SkillResourceSecondary skillResourceSecondary = null;
		Set<SkillResourcePrimary> skillResourcePrimaryList = new HashSet<SkillResourcePrimary>();
		Set<SkillResourceSecondary> skillResourceSecondaryList = new HashSet<SkillResourceSecondary>();
		
		Resource resource = resourceService.findResourcesByYashEmpIdEquals(editProfileDTO.getYashEmployeeId());
			resource.setContactNumber(editProfileDTO.getContactNumberOne());
			resource.setContactNumberTwo(editProfileDTO.getContactNumberTwo());
			resource.setCustomerIdDetail(editProfileDTO.getCustomerIdDetail());
			
			resource.setPreferredLocation(editProfileDTO.getPreferredLocation());
			
			/*Start - Remove yash exp from relevant experience while saving experiences*/
			//resource.setRelevantExper(editProfileDTO.getRelevantExp());
			String yearDiff= Resource.getCalYearDiff(resource.getDateOfJoining(), resource.getReleaseDate());
			double diff=Double.parseDouble(yearDiff);
			
			double relevantExp = Double.parseDouble(Resource.getCalcDiffTotalAndYash(editProfileDTO.getRelevantExp(), diff));
			resource.setRelevantExper(relevantExp);
			double totalExp = Double.parseDouble(Resource.getCalcDiffTotalAndYash(editProfileDTO.getTotalExp(), diff));
			resource.setTotalExper(totalExp);
			/*End - Remove yash exp from relevant experience while saving experiences*/
			
			if (fromRestService) resource.setLastUpdatedId("REST_API");
		
		/*List<SkillsMappingDTO> userSelectedPrimarySkillsList = editProfileDTO.getUserProfilePrimarySkillList();
		List<SkillsMappingDTO> userSelectedSecondarySkillsList = editProfileDTO.getUserProfileSecondarySkillList();
		
		if (userSelectedPrimarySkillsList != null) {
			
			for (SkillsMappingDTO skillsMapping : userSelectedPrimarySkillsList) {
				if (skillsMapping.getPrimarySkillId() != null) {
					
					skillResourcePrimary = new SkillResourcePrimary();
					skillResourcePrimary = this.populateSkillResourcePrimary(skillsMapping,skillResourcePrimary);
					
					skillResourcePrimary.setResourceId(resource);
					
					skillResourcePrimaryList.add(skillResourcePrimary);
				}
			}
		}
		
		if (userSelectedSecondarySkillsList != null) {
			
			for (SkillsMappingDTO skillsMapping : userSelectedSecondarySkillsList) {
				if (skillsMapping.getSecondarySkillId() != null) {
					
					skillResourceSecondary = new SkillResourceSecondary();
					skillResourceSecondary = this.populateSkillResourceSecondary(skillsMapping, skillResourceSecondary);
					
					skillResourceSecondary.setResourceId(resource);
					
					skillResourceSecondaryList.add(skillResourceSecondary);
				}
			}
		}
		
		resource.getSkillResourcePrimaries().clear();
		resource.getSkillResourcePrimaries().addAll((skillResourcePrimaryList));
		
		resource.getSkillResourceSecondaries().clear();
		resource.getSkillResourceSecondaries().addAll((skillResourceSecondaryList)) ;
		*/
		if (editProfileDTO.getUploadImage() != null && editProfileDTO.getUploadImage().length > 0) {
			System.out.println("----EditProfileServiceImpl - "+editProfileDTO.getYashEmployeeId()+" Resource's image ----" + editProfileDTO.getUploadImage().toString());
			resource.setUploadImage(editProfileDTO.getUploadImage());
		} /*else {
			resource.setUploadImage(null);
		}*/
		
		if(editProfileDTO.getUploadResume() !=null && editProfileDTO.getUploadResume().length > 0 ){
			resource.setUploadResume(editProfileDTO.getUploadResume());
			resource.setUploadResumeFileName(editProfileDTO.getUploadResumeFileName());
		}
		
		logger.info("Resource employee id while updating User Profile: "+resource.getEmployeeId());
		resourceService.copyUserProfileToResource(resource);
		
		return isSuccess;
	}
		
	
	private SkillResourcePrimary populateSkillResourcePrimary(SkillsMappingDTO skillsMapping, SkillResourcePrimary skillResourcePrimary) {
		logger.info("----------AdminActiveServiceImpl populateSkillResourcePrimary method start---------- ");
		try {
			Skills skills = skillsService.find(skillsMapping.getPrimarySkillId());
			
			skillResourcePrimary.setSkillId(skills);
			skillResourcePrimary.setRating(skillsMapping.getPrimarySkillRatingId());
			System.out.println(skillsMapping.getPrimarySkillRatingId());
			int primaryExperience=skillsMapping.getPrimaryExperience()==null?0:skillsMapping.getPrimaryExperience();
			System.out.println(primaryExperience);
			skillResourcePrimary.setPrimaryExperience(primaryExperience);
			System.out.println(primaryExperience);
			
		} catch (HibernateException hibernateException) {
	         logger.error("Exception occured in populateSkillResourcePrimary method at AdminActiveServiceImpl DAO layer:-"+hibernateException);
	         throw hibernateException;
	     }
		logger.info("----------AdminActiveServiceImpl populateSkillResourcePrimary method end---------- ");
		return skillResourcePrimary;
	}
		
	private SkillResourceSecondary populateSkillResourceSecondary(SkillsMappingDTO skillsMapping, SkillResourceSecondary skillResourceSecondary) {
		logger.info("----------AdminActiveServiceImpl populateSkillResourceSecondary method start---------- ");
		try {
			Skills skills = skillsService.find(skillsMapping.getSecondarySkillId());
			
			skillResourceSecondary.setSkillId(skills);
			skillResourceSecondary.setRating(skillsMapping.getSecondarySkillRatingId());
			skillResourceSecondary.setSecondaryExperience(skillsMapping.getExperience());
			
		}catch (HibernateException hibernateException) {
	         logger.error("Exception occured in populateSkillResourceSecondary method at AdminActiveServiceImpl DAO layer:-"+hibernateException);
	         throw hibernateException;
	     }
		logger.info("----------AdminActiveServiceImpl populateSkillResourceSecondary method end---------- ");
		return skillResourceSecondary;
	}
	public boolean addUserSkill(String skillType, Resource currentLoggedInResource,Skills skill, Integer exprienceVal, Integer rating) {
	
			SkillResourcePrimary skillProfilePrimary = null;
			SkillResourceSecondary skillResourceSecondary = null;
			if (skillType.equalsIgnoreCase("primary")) {
				skillProfilePrimary = new SkillResourcePrimary();
				skillProfilePrimary.setResourceId(currentLoggedInResource);
				skillProfilePrimary.setSkillId(skill);
				skillProfilePrimary.setRating(rating);
				skillProfilePrimary.setPrimaryExperience(exprienceVal);
				skillProfilePrimary.setCreationTimestamp(new Date());
				skillProfilePrimary.setCreatedId(UserUtil.getCurrentResource().getUsername());
				return editProfileDao.UpdateUserPrimarySkill(skillProfilePrimary);
				
			} else if (skillType.equalsIgnoreCase("secondary")) {
				skillResourceSecondary = new SkillResourceSecondary();
				skillResourceSecondary.setResourceId(currentLoggedInResource);
				skillResourceSecondary.setSkillId(skill);
				skillResourceSecondary.setRating(rating);
				skillResourceSecondary.setSecondaryExperience(exprienceVal);
				skillResourceSecondary.setCreationTimestamp(new Date());
				skillResourceSecondary.setCreatedId(UserUtil.getCurrentResource().getUsername());
				return editProfileDao.UpdateUserSecondarySkill(skillResourceSecondary);
				
			}
			
		return false;
		
	}
	
	public boolean updateUserSkill(String skillType, Integer pId, Integer priExprVal, Integer priRatingVal) {
	if (skillType.equalsIgnoreCase("primary")) {
			SkillResourcePrimary primary = skillResourcePrimaryService.findById(pId);
			primary.setPrimaryExperience(priExprVal);
			primary.setRating(priRatingVal);
			primary.setLastupdatedTimestamp(new Date());
			primary.setLastUpdatedId(UserUtil.getCurrentResource().getUsername());
			return editProfileDao.UpdateUserPrimarySkill(primary);
			
		}else if (skillType.equalsIgnoreCase("secondary")) {
			SkillResourceSecondary  secondary = skillResourceSecondaryService.findById(pId);
			secondary.setSecondaryExperience(priExprVal);
			secondary.setRating(priRatingVal);
			secondary.setLastupdatedTimestamp(new Date());
			secondary.setLastUpdatedId(UserUtil.getCurrentResource().getUsername());
			return editProfileDao.UpdateUserSecondarySkill(secondary);
	}
		else return false;
	}
}
