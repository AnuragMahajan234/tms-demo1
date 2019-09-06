package org.yash.rms.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.SkillResourcePrimaryDao;
import org.yash.rms.dao.SkillResourceSecondaryDao;
import org.yash.rms.dao.UserProfileDao;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.SkillResourcePrimary;
import org.yash.rms.domain.SkillResourceSecondary;
import org.yash.rms.domain.Skills;
import org.yash.rms.domain.SkillsMapping;
import org.yash.rms.domain.UserProfile;
import org.yash.rms.helper.ResourceHelper;
import org.yash.rms.namedquery.RmsNamedQuery;
import org.yash.rms.service.AdminActiveService;
import org.yash.rms.service.EditProfileService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.SkillsService;
import org.yash.rms.util.UserUtil;

@Repository("adminActiveService")
@Transactional
public class AdminActiveServiceImpl implements AdminActiveService{
	
	@Autowired @Qualifier("editProfileService")
	EditProfileService editProfileService;
	
	@Autowired@Qualifier("ResourceService")
	ResourceService resourceService;
	
	@Autowired @Qualifier("skillResourcePrimary")
	SkillResourcePrimaryDao skillResourcePrimaryDao; 
	
 @Autowired @Qualifier("skillResourceSecondary")
	SkillResourceSecondaryDao skillResourceSecondaryDao;
 
	@Autowired @Qualifier("SkillsService")
	private SkillsService skillsService;
	
	@Autowired
	UserProfileDao userProfileDao;
	
	@Autowired
	UserUtil userUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(AdminActiveServiceImpl.class);

	public String doApproveOrDenyProfile(Integer adminId, String resourceId,String approve, String code, HttpSession session,HttpServletRequest httpServletRequest) {
//		String approveStatus = "N";
	logger.info("----------AdminActiveServiceImpl doApproveOrDenyProfile method start---------- ");
		String userName = userUtil.getCurrentUserName();
		if ((userName == null) || (userName.trim().length() == 0) || (userName.trim().equalsIgnoreCase("anonymousUser"))) {
			   StringBuffer urlBuffer =  new StringBuffer();
			   urlBuffer.append(httpServletRequest.getServletPath());
			   urlBuffer.append("?").append(httpServletRequest.getQueryString());
			   session.setAttribute("adminURL", urlBuffer.toString());
			   return "login";
		}
		if( ((resourceId != null) && (resourceId.trim().length() > 0)) && ((approve != null) && (approve.trim().length()> 0)) && ((code != null) && (code.trim().length()> 0))) {
			List<UserProfile> userProfileList = editProfileService.findUserProfilesByYashEmpIdEquals(resourceId.trim());
			if ( (userProfileList != null) && (!userProfileList.isEmpty())){
				UserProfile userProfile =  userProfileList.get(0);
				 if (userProfile != null) {
					 Resource resource = resourceService.findResourcesByYashEmpIdEquals(resourceId.trim());
					 if (resource != null) {
						  String urlCode = null;
						   if (approve.trim().equalsIgnoreCase("Y")) {
							   urlCode = userProfile.getApprovalCode();
							   if ((urlCode != null) && (urlCode.trim().equalsIgnoreCase(code.trim())) ) {
								   try{
								   List<Object[]> listPrimaryResource = editProfileService.getSkillsMapping(String.valueOf(resource.getEmployeeId()), RmsNamedQuery.getPrimarySkillByResourceQuery());
								   List<Object[]> listSecondaryResource = editProfileService.getSkillsMapping(String.valueOf(resource.getEmployeeId()), RmsNamedQuery.getSecondarySkillByResourceQuery());
								   if(listPrimaryResource!=null&&listPrimaryResource.size()>0){
										List<SkillsMapping> skillsMappingList = ResourceHelper.getPrimarySkillSMapping(listPrimaryResource);
										if(skillsMappingList!=null&&skillsMappingList.size()>0){
											for(SkillsMapping skillsMapping:skillsMappingList){
												 if (skillsMapping.getPrimarkSkillPKId() != null && skillsMapping.getPrimarkSkillPKId() > 0) 
													skillResourcePrimaryDao.delete(skillsMapping.getPrimarkSkillPKId());//changes for SPring MVC
 
											}
										}
								   }
								   if(listSecondaryResource!=null && listSecondaryResource.size()>0){
									   List<SkillsMapping> skillsMappingList = ResourceHelper.getSecondarySkillSMapping(listSecondaryResource);
									   if(skillsMappingList!=null&&skillsMappingList.size()>0){
											for(SkillsMapping skillsMapping:skillsMappingList){
												 if (skillsMapping.getSecondarySkillPKId() != null && skillsMapping.getSecondarySkillPKId() > 0)
													skillResourceSecondaryDao.delete(skillsMapping.getSecondarySkillPKId());
												 
											}
										 }
								   }
//								   approveStatus = "Y";
								   // this.copyObject(userProfile, resource);
								   resourceService.copyUserProfileToResource((this.copyObject(userProfile, resource)));
								   //resource.getSkillResourcePrimaries().clear();resource.getSkillResourceSecondaries().clear();
								   userProfile.setApprovalCode(null);
								   userProfile.setLogicalDelete(approve);
								   userProfileDao.saveOrupdate(userProfile);
								   httpServletRequest.setAttribute("approveStatus", approve);
							   }
								   catch (HibernateException e) {
										logger.error("Exception Occurred in doApproveOrDenyProfile method of AdminActiveServiceImpl DAO "+e.getMessage());
										 throw e;
									} 
								   
						   }
					 }else if (approve.trim().equalsIgnoreCase("N")) {
						 //#104- Correction- Adding code for profile rejection...
							urlCode = userProfile.getDenyCode();
							if ((urlCode != null) && (urlCode.trim().equalsIgnoreCase(code.trim())) ) {
								//if userProfile is rejected then do nothing...
								  httpServletRequest.setAttribute("approveStatus", approve);
							}
					 }
						
						 /* // this.copyObject(userProfile, resource);
						   resourceService.copyUserProfileToResource((this.copyObject(userProfile, resource)));
						   //resource.getSkillResourcePrimaries().clear();resource.getSkillResourceSecondaries().clear();
						  userProfile.setApprovalCode(null);
						   userProfile.setLogicalDelete(approve);
						   userProfileDao.merge(userProfile);
						   httpServletRequest.setAttribute("approveStatus", approve);*/
				 }
			}
		}
		
		}
		
		logger.info("----------AdminActiveServiceImpl doApproveOrDenyProfile method end---------- ");
		return "success";
	}
		
		private Resource copyObject(UserProfile userProfile,Resource resource ) {
			logger.info("----------AdminActiveServiceImpl copyObject method start---------- ");
			try {
				Resource resourceCopy = resource;
				Resource resource1=null;
				resource.setEmailId(userProfile.getEmailId());
				resource.setContactNumber(userProfile.getContactNumberOne());
				resource.setContactNumberTwo(userProfile.getContactNumberTwo());
				resource.setFirstName(userProfile.getFirstName());
				resource.setLastName(userProfile.getLastName());
				resource.setResumeFileName(userProfile.getResumeFileName());
//			byte [] resume = userProfile.getResume();
//			if ((resume != null) && (resume.length > 0)){
//				resource.setResume(resume);
//			} 
				//resource.setSkillResourcePrimaries(userProfile.getSkillProfilePrimaries());
				//resource.setSkillResourceSecondaries(userProfile.getSkillProfileSecondaries());
				resource.setCustomerIdDetail(userProfile.getCustomerIdDetail());
				Set <SkillResourcePrimary>skillResourcePrimaries = new HashSet <SkillResourcePrimary> ();
				Set <SkillResourceSecondary> skillResourceSecondaries = new HashSet <SkillResourceSecondary> ();
				List<Object[]> listPrimarySkill = editProfileService.getSkillsMapping(resource.getYashEmpId(), RmsNamedQuery.getPrimarySkillByUserProfileQuery());
				List<Object[]> listSecondarySkill = editProfileService.getSkillsMapping(resource.getYashEmpId(), RmsNamedQuery.getSecondarySkillByUserProfilesQuery());
				if (listPrimarySkill != null && !listPrimarySkill.isEmpty()) {
					List<SkillsMapping> skillsMappingList = ResourceHelper.getPrimarySkillSMapping(listPrimarySkill);
					//Set <SkillResourcePrimary>skillResourcePrimaries = new HashSet <SkillResourcePrimary> ();
					if (skillsMappingList != null && skillsMappingList.size() > 0) {
						SkillsMapping skillsMapping = null;
						SkillResourcePrimary skillResourcePrimary = null;
						for (int i=0;i<skillsMappingList.size();i++) {
							skillsMapping = skillsMappingList.get(i);
							skillResourcePrimary = new SkillResourcePrimary();
							skillResourcePrimary = this.populateSkillResourcePrimary(skillsMapping,skillResourcePrimary);
							skillResourcePrimary.setResourceId(resourceCopy);
							skillResourcePrimaries.add(skillResourcePrimary);
							
						}
						
						 
					}
				}
				 
				if (listSecondarySkill != null) {
					List<SkillsMapping> skillsMappingList = ResourceHelper.getSecondarySkillSMapping(listSecondarySkill);
					//Set <SkillResourceSecondary> skillResourceSecondaries = new HashSet <SkillResourceSecondary> ();
					if (skillsMappingList != null && skillsMappingList.size() > 0) {
						SkillsMapping skillsMapping = null;
						SkillResourceSecondary skillResourceSecondary = null;
						for (int i=0;i<skillsMappingList.size();i++) {
							skillsMapping = skillsMappingList.get(i);
							skillResourceSecondary = new SkillResourceSecondary();
							skillResourceSecondary = this.populateSkillResourceSecondary(skillsMapping,skillResourceSecondary);
							skillResourceSecondary.setResourceId(resourceCopy);
							skillResourceSecondaries.add(skillResourceSecondary);
							
						}
					}
					//resource.getSkillResourcePrimaries().clear();
				
				}
				resource.getSkillResourcePrimaries().clear();
				resource.getSkillResourcePrimaries().addAll((skillResourcePrimaries));
				resource.getSkillResourceSecondaries().clear();
				resource.getSkillResourceSecondaries().addAll((skillResourceSecondaries)) ;
			} catch (HibernateException hibernateException) {
		         logger.error("Exception occured in copyObject method at AdminActiveServiceImpl DAO layer:-"+hibernateException);
		         throw hibernateException;
		     }
			logger.info("----------AdminActiveServiceImpl copyObject method end---------- ");
			return resource;
		
		}
		
		private SkillResourcePrimary populateSkillResourcePrimary( SkillsMapping skillsMapping, SkillResourcePrimary skillResourcePrimary) {
			logger.info("----------AdminActiveServiceImpl populateSkillResourcePrimary method start---------- ");
			//skillResourcePrimary.setId(skillsMapping.getPrimarySkill_Id());
			try {
				skillResourcePrimary.setRating(skillsMapping.getPrimarySkillRating_Id());
				Skills Skillsid = skillsService.find(skillsMapping.getPrimarySkill_Id());
				skillResourcePrimary.setSkillId(Skillsid);
			} catch (HibernateException hibernateException) {
		         logger.error("Exception occured in populateSkillResourcePrimary method at AdminActiveServiceImpl DAO layer:-"+hibernateException);
		         throw hibernateException;
		     }
			//skillResourcePrimary.setSkillId(.);
			logger.info("----------AdminActiveServiceImpl populateSkillResourcePrimary method end---------- ");
			return skillResourcePrimary;
		}
		
		private SkillResourceSecondary populateSkillResourceSecondary( SkillsMapping skillsMapping, SkillResourceSecondary skillResourceSecondary) {
			logger.info("----------AdminActiveServiceImpl populateSkillResourceSecondary method start---------- ");
			//skillResourceSecondary.setId(skillsMapping.getSecondarySkill_Id());
			try {
				skillResourceSecondary.setRating(skillsMapping.getSecondarySkillRating_Id());
				Skills Skillsid = skillsService.find(skillsMapping.getSecondarySkill_Id());
				skillResourceSecondary.setSkillId(Skillsid);
			}catch (HibernateException hibernateException) {
		         logger.error("Exception occured in populateSkillResourceSecondary method at AdminActiveServiceImpl DAO layer:-"+hibernateException);
		         throw hibernateException;
		     }
			logger.info("----------AdminActiveServiceImpl populateSkillResourceSecondary method end---------- ");
			return skillResourceSecondary;
		}
}
