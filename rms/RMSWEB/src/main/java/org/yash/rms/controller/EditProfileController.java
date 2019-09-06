package org.yash.rms.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.SkillResourcePrimary;
import org.yash.rms.domain.SkillResourceSecondary;
import org.yash.rms.domain.Skills;
import org.yash.rms.domain.SkillsMapping;
import org.yash.rms.domain.UserProfile;
import org.yash.rms.dto.EditProfileDTO;
import org.yash.rms.dto.ProjectDTO;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.form.UserProfileForm;
import org.yash.rms.helper.ResourceHelper;
import org.yash.rms.service.EditProfileService;
import org.yash.rms.service.LocationService;
import org.yash.rms.service.RatingService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.SkillResourcePrimaryService;
import org.yash.rms.service.SkillResourceSecondaryService;
import org.yash.rms.service.SkillsService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DozerMapperUtility;
import org.yash.rms.util.UserUtil;

import flexjson.JSON;


@Controller
@RequestMapping ("/editProfile")
public class EditProfileController {
	
	@Autowired
	@Qualifier("editProfileService")
	EditProfileService editProfileService;
	
	@Autowired
	@Qualifier("ratingService")
	RatingService ratingService;
	
	@Autowired
	@Qualifier("ResourceService")
	ResourceService resourceService;
	
	@Autowired
	@Qualifier("SkillsService")
	SkillsService skillsService;
	
	@Autowired
	ResourceHelper resourceHelper;
	
	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	DozerMapperUtility dozerMapperUtility;
	
	
	@Autowired
	@Qualifier("skillResourcePrimaryService")
	SkillResourcePrimaryService skillResourcePrimaryService ;
	
	@Autowired
	@Qualifier("skillResourceSecondaryService")
	SkillResourceSecondaryService skillResourceSecondaryService;
	
	@Autowired
	@Qualifier("LocationService")
	LocationService locationService;
	
	private static boolean isProfileUpdateSucessfully=false;
	
	private static final Logger logger = LoggerFactory.getLogger(EditProfileController.class);
	
	@RequestMapping (value="/showProfile",method = RequestMethod.GET)
	public String showProfile(Model uiModel) throws Exception {
		logger.info("------EditProfileController showProfile method start----");
		try {
		
			//Resource currentLoggedInRes = userUtil.getLoggedInResource();
			String loggedInResource=userUtil.getLoggedInResource().getUserName();
			
			UserContextDetails currentLoggedInResource=userUtil.getCurrentResource(loggedInResource);
			
			EditProfileDTO editProfileDTO = resourceService.getResourceDetailsByEmployeeId(currentLoggedInResource.getEmployeeId());
		
			Constants.IMAGE_UPLOAD=editProfileDTO.getUploadImage();
		
			uiModel.addAttribute(Constants.CURRENT_LOGGED_IN_RESOURCE,editProfileDTO);
			
			uiModel.addAttribute(Constants.USER_PROFILE_PRIMARY_SKILL_LIST,editProfileDTO.getUserProfilePrimarySkillList());
			
			uiModel.addAttribute(Constants.USER_PROFILE_SECONDARY_SKILL_LIST,editProfileDTO.getUserProfileSecondarySkillList());
			
			uiModel.addAttribute(Constants.PRIMARY_SKILLS, editProfileDTO.getPrimarySkillsList());
			
			uiModel.addAttribute(Constants.SECONDARY_SKILLS, editProfileDTO.getSecondarySkillsList());
			
			uiModel.addAttribute(Constants.RATING,  editProfileDTO.getRatingList());
			
			uiModel.addAttribute(Constants.LOCATIONS, locationService.findAll());
			
			 /* Download Resume on Dashboard Edit Profile */
			uiModel.addAttribute(Constants.USER_RESUME_FILE_NAME,  currentLoggedInResource.getUploadResumeFileName()); 
			uiModel.addAttribute(Constants.USER_EMP_ID,currentLoggedInResource.getEmployeeId());
			uiModel.addAttribute("firstName", currentLoggedInResource.getFirstName() + " " + currentLoggedInResource.getLastName());
			uiModel.addAttribute("designationName", currentLoggedInResource.getDesignation());
			uiModel.addAttribute("ROLE", currentLoggedInResource.getUserRole());
			
				if (currentLoggedInResource.getUploadImage() != null
						&& currentLoggedInResource.getUploadImage().length > 0) {
					byte[] encodeBase64UserImage = Base64.encodeBase64(currentLoggedInResource.getUploadImage());
					String base64EncodedUser = new String(
							encodeBase64UserImage, "UTF-8");
					uiModel.addAttribute("UserImage", base64EncodedUser);
				} else {
					uiModel.addAttribute("UserImage", "");
				}
			
			if(isProfileUpdateSucessfully){
				uiModel.addAttribute("success","Your profile has been updated successfully");
				isProfileUpdateSucessfully = false;
			}else{
				uiModel.addAttribute("success","");
			}
			logger.info("------EditProfileController showProfile method end----");
			return "resources/userProfile";
		} catch(RuntimeException runtimeException) {				
			logger.error("RuntimeException occured in showProfile method of EditProfileController:"+runtimeException);				
			throw runtimeException;
		} catch(Exception exception){
			logger.error("Exception occured in list showProfile of EditProfileController:"+exception);				
			throw exception;
		}
	}
	
	@RequestMapping(value="/editProfile/saveProfile",method = RequestMethod.POST)
	public String saveProfile(@ModelAttribute UserProfileForm userProfileForm, Model uiModel, HttpServletRequest httpServletRequest) throws Exception {
		logger.info("------EditProfileController saveProfile method start----");
		try {
			
			EditProfileDTO editProfileDTO = dozerMapperUtility.convertUserProfileFormToEditProfileDTO(userProfileForm);
			
			//This code for get Employee YashExprience
			Resource currentLoggedInResource = userUtil.getLoggedInResource();
			EditProfileDTO editProfileDTO1 = resourceService.getResourceDetailsByEmployeeId(currentLoggedInResource.getEmployeeId());
            String yashExp=editProfileDTO1.getYashExp();
			editProfileDTO.setYashExp(yashExp);
			Location location   = locationService.findLocationByName(editProfileDTO.getPreferredLocation().getLocation());
			editProfileDTO.setPreferredLocation(location);
			isProfileUpdateSucessfully = editProfileService.saveOrUpdateUserProfile(editProfileDTO, false);
			
			logger.info("------EditProfileController saveProfile method end----");
			
			return "redirect:/editProfile/showProfile";
		
		} catch(RuntimeException runtimeException) {				
			logger.error("RuntimeException occured in saveProfile method of EditProfileController:"+runtimeException);				
			throw runtimeException;
		} catch(Exception exception) {
			logger.error("Exception occured in list saveProfile of EditProfileController:"+exception);				
			throw exception;
		}
	}
	
	@RequestMapping(value="resumeFormat")
	public void  getResumeFormat(HttpServletRequest  request, HttpServletResponse response) {
		logger.info("------EditProfileController getResumeFormat method start----");
		String fileName="resume.doc";
		String fileDirectory = request.getRealPath("/WEB-INF/resume-template/");
	    Path file = Paths.get(fileDirectory, fileName);
	    if (Files.exists(file)){
	            response.setContentType("application/msword");
	            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
	            try{
	                Files.copy(file, response.getOutputStream());
	                response.getOutputStream().flush();
	            }
	            catch (IOException exception) {
	            	logger.error("Exception occured in getResumeFormat method of EditProfileController:"+exception);	
	            }
	        }
	    logger.info("------EditProfileController getResumeFormat method end----");
	}
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws ServletException {
		binder.registerCustomEditor(byte[].class,new ByteArrayMultipartFileEditor());
		
	}
	
	
	 /* Download Resume on Dashboard Edit Profile Controller Call */
	
	@RequestMapping(value = "/downloadResume")
	public ResponseEntity<String> downloadResumeFile(HttpServletRequest request, @RequestParam("reqid") Integer id, HttpServletResponse response) {
		ResponseEntity<String> jspResponse = null;
		HttpHeaders headers = new HttpHeaders();
		Blob blobUploadFile = null;

		Map<String, Object> map = editProfileService.downloadResume(id); //EmpoyeeId
		blobUploadFile = (Blob) map.get("file"); 
		response.setHeader("Content-Disposition", "attachment;filename=" + map.get("fileName"));
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setContentType("application/force-download");
		try {
			if (null != blobUploadFile) {
				InputStream inStream = blobUploadFile.getBinaryStream();
				FileCopyUtils.copy(inStream, response.getOutputStream());
			} else {
				return jspResponse = new ResponseEntity<String>("File Not Found", headers, HttpStatus.EXPECTATION_FAILED);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jspResponse;
	}
	
	//Add user primary and secondary skills
	@RequestMapping(value = "/addUserSkill")
	public ResponseEntity<String> addUserSkill(Model uiModel, HttpServletRequest request,
			@RequestParam("skillType") String skillType, @RequestParam("employeeId") Integer employeeId,
			@RequestParam("skillId") Integer skillId, @RequestParam("exprienceVal") Integer exprienceVal,
			@RequestParam("rating") Integer rating, HttpServletResponse response) {
		
		logger.info("------EditProfileController addUserSkill method start------");

		ResponseEntity<String> addSkillResponse = null;
		HttpHeaders headers = new HttpHeaders();
		JSONObject resultJSON = new JSONObject();
		JSONArray priarray = new JSONArray();
		JSONArray secarray = new JSONArray();
		boolean addUserSkill = false;

		try {
			Resource currentLoggedInResource = userUtil.getLoggedInResource();
			Skills skill = skillsService.find(skillId);
			addUserSkill = editProfileService.addUserSkill(skillType, currentLoggedInResource, skill, exprienceVal,rating);
			if (addUserSkill) {
				List<SkillResourcePrimary> primaries = skillResourcePrimaryService.findSkillResourcesByResourceId(currentLoggedInResource.getEmployeeId());
				List<SkillResourceSecondary> secondaries = skillResourceSecondaryService.findSkillResourcesByResourceId(currentLoggedInResource.getEmployeeId());

				if (primaries != null) {
					for (SkillResourcePrimary primaryskills : primaries) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("id", primaryskills.getId());
						jsonObject.put("skillName", primaryskills.getSkillId().getSkill());
						jsonObject.put("skillId", primaryskills.getSkillId().getId());
						jsonObject.put("rating", primaryskills.getRating());
						jsonObject.put("experience", primaryskills.getPrimaryExperience());
						priarray.put(jsonObject);
					}
				}

				if (secondaries != null) {
					for (SkillResourceSecondary secondaryskills : secondaries) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("id", secondaryskills.getId());
						jsonObject.put("skillName", secondaryskills.getSkillId().getSkill());
						jsonObject.put("skillId", secondaryskills.getSkillId().getId());
						jsonObject.put("rating", secondaryskills.getRating());
						jsonObject.put("experience", secondaryskills.getSecondaryExperience());
						secarray.put(jsonObject);
					}
				}

				resultJSON.put("PrimarySkillList", priarray);
				resultJSON.put("SecondarySkillList", secarray);
				
				logger.info("------EditProfileController addUserSkill method end------");
				return addSkillResponse = new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
			} else {
				return addSkillResponse = new ResponseEntity<String>("", headers, HttpStatus.NOT_MODIFIED);
			}

		} catch (Exception e) {
			logger.error("Exception occured in addUserSkill method of EditProfileController:" + e);
		}
		return addSkillResponse;

	}

	
	//update user primary and secondary skills
	@RequestMapping(value = "/updateUserSkill", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updateUserSkill(Model uiModel, HttpServletRequest request,
			@RequestParam String skillType, @RequestParam Integer pId, @RequestParam Integer priExprVal,
			@RequestParam Integer priRatingVal, HttpServletResponse response) throws Exception {
		
		logger.info("------EditProfileController updateUserSkill method start------");
		
		ResponseEntity<String> updateSkillResponse = null;
		HttpHeaders headers = new HttpHeaders();
		JSONObject resultJSON = new JSONObject();
		boolean addUserSkill = false;
		try {
			addUserSkill = editProfileService.updateUserSkill(skillType, pId, priExprVal, priRatingVal);
			if (addUserSkill) {
				if (skillType.equalsIgnoreCase("primary")) {
					SkillResourcePrimary primaries = skillResourcePrimaryService.findById(pId);
					resultJSON.put("id", primaries.getId());
					resultJSON.put("skillName", primaries.getSkillId().getSkill());
					resultJSON.put("rating", primaries.getRating());
					resultJSON.put("experience", primaries.getPrimaryExperience());
				}

				else if (skillType.equalsIgnoreCase("secondary")) {
					SkillResourceSecondary secondaries = skillResourceSecondaryService.findById(pId);
					resultJSON.put("id", secondaries.getId());
					resultJSON.put("skillName", secondaries.getSkillId().getSkill());
					resultJSON.put("rating", secondaries.getRating());
					resultJSON.put("experience", secondaries.getSecondaryExperience());
				}
				
				logger.info("------EditProfileController updateUserSkill method end------");
				return updateSkillResponse = new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
			} else {
				return updateSkillResponse = new ResponseEntity<String>("", headers,HttpStatus.NOT_MODIFIED);
			}
		} catch (Exception e) {
			logger.error("Exception occured in updateUserSkill method of EditProfileController:" + e);
		}
		return updateSkillResponse;
	}
	
	    //delete user primary and secondary skill 
	@RequestMapping(value = "/deleteUserSkill", method = RequestMethod.DELETE)
	@ResponseBody

	public ResponseEntity<String> deleteUserSkill(Model uiModel, HttpServletRequest request,
			@RequestParam Integer pSkillId, @RequestParam String skillType, HttpServletResponse response)
			throws Exception {
		
		logger.info("------EditProfileController deleteUserSkill method start------");

		ResponseEntity<String> deleteskillResponse = null;
		HttpHeaders headers = new HttpHeaders();
		JSONObject resultJSON = new JSONObject();
		boolean deleteSkillsById = false;
		
		try {
			if(skillType.equalsIgnoreCase("primary")) {
			deleteSkillsById = skillResourcePrimaryService.delete(pSkillId);
			}else {
				deleteSkillsById = skillResourceSecondaryService.delete(pSkillId);
			}
			
			if (deleteSkillsById) {
				resultJSON.put("id", pSkillId);
				resultJSON.put("skillType", skillType);

				logger.info("------EditProfileController deleteUserSkill method end------");
				return deleteskillResponse = new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
			} else {
				return deleteskillResponse = new ResponseEntity<String>("", headers,
						HttpStatus.NOT_MODIFIED);
			}

		} catch (Exception e) {
			logger.error("Exception occured in deleteUserSkill method of EditProfileController:" + e);
		}
		return deleteskillResponse;
	}
	
}
