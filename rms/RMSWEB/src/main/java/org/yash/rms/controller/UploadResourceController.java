package org.yash.rms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.Grade;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Ownership;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.SkillResourcePrimary;
import org.yash.rms.domain.SkillResourceSecondary;
import org.yash.rms.domain.Visa;
import org.yash.rms.excel.ExcelUtil;
import org.yash.rms.forms.FileUploadBean;
import org.yash.rms.proxy.ResourceExcelMapping;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.service.SkillsService;
import org.yash.rms.util.Constants;

@Controller
@RequestMapping("/uploadresources")
public class UploadResourceController {

	@Autowired
	@Qualifier("ResourceService")
	RmsCRUDService<Resource> resourceService;
	@Autowired @Qualifier("SkillsService")
	SkillsService skillsService;

	List<String> roles = Arrays.asList("ROLE_ADMIN", "ROLE_MANAGER",
			"ROLE_USER", "ROLE_BEHALF_MANAGER");
	private static final Logger logger = LoggerFactory
			.getLogger(UploadResourceController.class);

	// ,@ModelAttribute("errorList") List<String> errors
	@RequestMapping(value = "/upload", produces = "text/html")
	public String uploadResources(@Valid FileUploadBean fileUploadBean,
			BindingResult bindingResult,
			@RequestParam(value = "error", required = false) String error) {
		logger.info("------UploadResourceController uploadResources method start------");
		return "uploadresources/uploadresources";
	}

	@RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
	public String uploadExcelResources(HttpServletRequest request,
			@Valid FileUploadBean fileUploadBean, BindingResult bindingResult,
			Model uiModel, FileUpload content,
			HttpServletRequest httpServletRequest) throws Exception {
		logger.info("------UploadResourceController uploadExcelResources method start------");
		List<String> errorList = new ArrayList<String>();
		//Added for defect #269
		String fileName = "";
		if (fileUploadBean != null) {
			fileName = fileUploadBean.getFile().getOriginalFilename();
			System.out.println("file Name ------------"+fileName);
		}
		/*fix for #314- check for file format*/
		String ext="";
		int mid= fileName.lastIndexOf(".");
		ext=fileName.substring(mid+1,fileName.length());  
		System.out.println("fileNAme.............."+fileName);
		
		try {			
			if (ServletFileUpload.isMultipartContent(request)) {
				List<ResourceExcelMapping> list = null;
				// Added to solve Bugs #271 ****
				try {
					/*fix for #314- check for file format*/
					if (ext != null && !ext.equals("")) {
						if (!ext.trim().equals("xlsx") && !ext.trim().equals("xls")) {
							errorList.add("Please upload the file in Excel format.");
							uiModel.addAttribute(Constants.ERROR_LIST, errorList);
							uiModel.addAttribute(Constants.FILE_NAME, fileName);
							logger.info("------UploadResourceController uploadExcelResources method end------");
							return "uploadresources/uploadresources";
						}
					}
					
					
					list = ExcelUtil.readExcel(ResourceExcelMapping.class,
							fileUploadBean.getFile().getInputStream(),
							fileUploadBean.getFile().getOriginalFilename(),errorList);
					//Added for defect #306
					if (list == null || list.size()==0 || list.isEmpty()) {
						errorList.add("This is a blank file, Please select a valid file.");
						uiModel.addAttribute(Constants.ERROR_LIST, errorList);
						uiModel.addAttribute(Constants.FILE_NAME, fileName);
						logger.info("------UploadResourceController uploadExcelResources method end------");
						return "uploadresources/uploadresources";
					}
					
				} catch (Exception e) {
					logger.error("Upload Exception" + e);
				}
				// ********//
				//System.out.println(" Resource List Size >" + list.size());
				int count = 1;
				if (list != null && !list.isEmpty() && list.size() > 0 ) {
					int i=2;
					for (ResourceExcelMapping resourceExcelMapping : list) {
						Resource resource = new Resource();
						/*
						 * System.out.println(" Yash ID >" +
						 * resourceExcelMapping.getYashEmpId());
						 */
						if (resourceExcelMapping.getYashEmpId() != null
								&& !resourceExcelMapping.getYashEmpId().isEmpty()) {
							
							if (validateAlphaNumeric(resourceExcelMapping.getYashEmpId())) {
								resource.setYashEmpId(resourceExcelMapping.getYashEmpId());
							}else{
								errorList.add("Please enter Alpha numeric value for Yash Employee ID "+resourceExcelMapping.getYashEmpId());
							}
							
							
						} else {
							errorList.add("Please enter Yash Employee ID in row "+ count);
						}

						if (resourceExcelMapping.getFirstName() != null
								&& !resourceExcelMapping.getFirstName().isEmpty()) {
							if (validateString(resourceExcelMapping.getFirstName())) {
								resource.setFirstName(resourceExcelMapping.getFirstName());
							}else{
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter String value for First Name for yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter String value for First Name in row "+ count);
							}
							
						} else {
							if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
								errorList.add("Please enter First Name for yash employee Id "+resourceExcelMapping.getYashEmpId());
							}
							errorList.add("Please enter First Name in row "+ count);
						}
						
						if (resourceExcelMapping.getMiddleName() != null
								&& !resourceExcelMapping.getMiddleName().isEmpty()) {
							
							if (validateString(resourceExcelMapping.getMiddleName())) {
								resource.setMiddleName(resourceExcelMapping.getMiddleName());
							}else{
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter String value for Middle name for yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter String value for Middle name in row "+ count);
							}
							
						} 
						
						
						if (resourceExcelMapping.getLastName() != null
								&& !resourceExcelMapping.getLastName().isEmpty()) {
							if (validateString(resourceExcelMapping.getLastName())) {
								resource.setLastName(resourceExcelMapping.getLastName());
							}else{
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter String value for last name for yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter String value for last name in row "+ count);
							}
							
						} else {
							if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
								errorList.add("Please enter Last Name For yash employee Id "+resourceExcelMapping.getYashEmpId());
							}
							errorList.add("Please enter Last Name in row "+ count);
						}
						if (resourceExcelMapping.getEmailId() != null
								&& !resourceExcelMapping.getEmailId().isEmpty()) {

							String email = resourceExcelMapping.getEmailId();
							String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
							Boolean b = email.matches(emailreg);
							if (b == true) {
								resource.setEmailId(resourceExcelMapping
										.getEmailId());
							} else {
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Invalid Email ID in yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Invalid Email ID in row "+ count);
							}
						} else {
							if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
								errorList.add("Please enter Email ID for yash employee Id "+resourceExcelMapping.getYashEmpId());
							}
							errorList.add("Please enter Email ID for row "+ count);
						}
						if (resourceExcelMapping.getDateOfJoining() != null
								&& !resourceExcelMapping.getDateOfJoining().equals(
										"")) {
							resource.setDateOfJoining(resourceExcelMapping
									.getDateOfJoining());

						} else {
							if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
								errorList.add("Please enter Date Of Joining for yash employee Id "+resourceExcelMapping.getYashEmpId());
							}
							errorList.add("Please enter Date Of Joining for row "+ count);
						}
						/*if (resourceExcelMapping.getActualCapacity() != null
								&& !resourceExcelMapping.getActualCapacity()
										.equals("")) {
							if (validateIntegerDigits(resourceExcelMapping
									.getActualCapacity())) {
								resource.setActualCapacity(resourceExcelMapping
										.getActualCapacity());
							} else {
								errorList
										.add("Please enter Integer for Actual Capacity in yash employee Id "+resourceExcelMapping.getYashEmpId());
							}
						} else {
							errorList.add("Please enter Actual Capacity for yash employee Id "+resourceExcelMapping.getYashEmpId());
						}
						if (resourceExcelMapping.getPlannedCapacity() != null
								&& !resourceExcelMapping.getPlannedCapacity()
										.equals("")) {
							if (validateIntegerDigits(resourceExcelMapping
									.getPlannedCapacity())) {
								resource.setPlannedCapacity(resourceExcelMapping
										.getPlannedCapacity());
							} else {
								errorList
										.add("Please enter Integer for Planned Capacity in yash employee Id "+resourceExcelMapping.getYashEmpId());
							}
						} else {
							errorList.add("Please enter Planned Capacity for yash employee Id "+resourceExcelMapping.getYashEmpId());
						}*/
						if (resourceExcelMapping.getDesignation_Id() != null
								&& !resourceExcelMapping.getDesignation_Id()
										.equals("")) {

							if (validateIntegerDigits(resourceExcelMapping
									.getDesignation_Id())) {
								Designation designationsObject = new Designation();
								designationsObject.setId(resourceExcelMapping
										.getDesignation_Id());
								resource.setDesignationId(designationsObject);
							} else {
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter Integer for Designation ID in yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter Integer for row "+count);
							}
						} else {
							if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
								errorList.add("Please enter Designation ID for yash employee Id "+resourceExcelMapping.getYashEmpId());
							}
							errorList.add("Please enter Designation ID for row "+count);
						}
						if (resourceExcelMapping.getGrade_Id() != null
								&& !resourceExcelMapping.getGrade_Id().equals("")) {
							if (validateIntegerDigits(resourceExcelMapping
									.getGrade_Id())) {
								Grade gradeObject = new Grade();
								gradeObject.setId(resourceExcelMapping
										.getGrade_Id());
								resource.setGradeId(gradeObject);
							} else {
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter Grade ID for yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter Integer for row "+count);
							}
						} else {
							if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
								errorList.add("Please enter Grade ID for yash employee Id "+resourceExcelMapping.getYashEmpId());
							}
							errorList.add("Please enter Grade ID for row "+count);
						}
						if (resourceExcelMapping.getVisaId() != null
								&& !resourceExcelMapping.getVisaId().equals("")) {

							if (validateIntegerDigits(resourceExcelMapping
									.getVisaId())) {
								Visa visa = new Visa();
								visa.setId(resourceExcelMapping.getVisaId());
								resource.setVisaId(visa);
							} else {
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter Integer for Visa ID in yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter Integer for Visa ID in row "+count);
							}

						}

						if (resourceExcelMapping.getOwnership_Id() != null
								&& !resourceExcelMapping.getOwnership_Id().equals(
										"")) {
							if (validateIntegerDigits(resourceExcelMapping
									.getOwnership_Id())) {
								Ownership ownershipObject = new Ownership();
								ownershipObject.setId(resourceExcelMapping
										.getOwnership_Id());
								resource.setOwnership(ownershipObject);
							} else {
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter Integer for Ownership ID in yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter Integer for Ownership ID in row "+count);
							}
						} else {
							if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
								errorList.add("Please enter Ownership ID in yash employee Id "+resourceExcelMapping.getYashEmpId());
							}
							errorList.add("Please enter Ownership ID in row "+count);
						}
						if (resourceExcelMapping.getLocation_id() != null
								&& !resourceExcelMapping.getLocation_id()
										.equals("")) {

							if (validateIntegerDigits(resourceExcelMapping
									.getLocation_id())) {
								Location location = new Location();
								location.setId(resourceExcelMapping
										.getLocation_id());
								resource.setLocationId(location);
							} else {
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter Integer for Location ID in yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter Integer for Location ID in row "+count);
							}
						} else {
							if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
								errorList.add("Please enter Location ID for yash employee Id "+resourceExcelMapping.getYashEmpId());
							}
							errorList.add("Please enter Location ID for row "+count);
						}

						if (resourceExcelMapping.getDeploymentLocation_id() != null
								&& !resourceExcelMapping.getDeploymentLocation_id()
										.equals("")) {
							if (validateIntegerDigits(resourceExcelMapping
									.getDeploymentLocation_id())) {
								Location payRollLocation = new Location();
								payRollLocation.setId(resourceExcelMapping
										.getDeploymentLocation_id());
								resource.setDeploymentLocation(payRollLocation);
							} else {
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter Integer for PayRoll Location ID in yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter Integer for PayRoll Location ID in row "+count);
							}

						}
						if (resourceExcelMapping.getBuId() != null
								&& !resourceExcelMapping.getBuId().equals("")) {

							if (validateIntegerDigits(resourceExcelMapping
									.getBuId())) {
//							Bu bu = new Bu();
//							bu.setId(resourceExcelMapping.getBuId());
								OrgHierarchy hierarchy = new OrgHierarchy();
								hierarchy.setId(resourceExcelMapping.getBuId());
								resource.setBuId(hierarchy);
							} else {
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter Integer for BU ID in yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter Integer for BU ID in row "+count);
							}

						} else {
							if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
								errorList.add("Please enter BU ID for yash employee Id"+resourceExcelMapping.getYashEmpId());
							}
							errorList.add("Please enter BU ID for row "+count);
						}
						if (resourceExcelMapping.getCurrentBuId() != null
								&& !resourceExcelMapping.getCurrentBuId()
										.equals("")) {

							if (validateIntegerDigits(resourceExcelMapping
									.getCurrentBuId())) {
//							Bu bu = new Bu();
//							bu.setId(resourceExcelMapping.getCurrentBuId());
								OrgHierarchy hierarchy = new OrgHierarchy();
								hierarchy.setId(resourceExcelMapping.getCurrentBuId());
								resource.setCurrentBuId(hierarchy);
							} else {
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter Integer for Current BU ID in yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter Integer for Current BU ID in row "+count);
							}
						} else {
							if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
								errorList.add("Please enter Current BU ID for yash employee Id "+resourceExcelMapping.getYashEmpId());
							}
							errorList.add("Please enter Current BU ID for row "+count);
						}
						if (resourceExcelMapping.getCurrentProjectId() != null
								&& !resourceExcelMapping.getCurrentProjectId()
										.equals("")) {
							if (validateIntegerDigits(resourceExcelMapping
									.getCurrentProjectId())) {
								Project project = new Project();
								project.setId(resourceExcelMapping
										.getCurrentProjectId());
								resource.setCurrentProjectId(project);
							} else {
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter Integer for Current Project ID in yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter Integer for Current Project ID in row "+count);
							}
						}
						if (resourceExcelMapping.getCurrentReportingManager() != null
								&& !resourceExcelMapping
										.getCurrentReportingManager().equals("")) {
							if (validateIntegerDigits(resourceExcelMapping
									.getCurrentReportingManager())) {
								Resource currentManager = new Resource();
								currentManager.setEmployeeId(resourceExcelMapping
										.getCurrentReportingManager());
								resource.setCurrentReportingManager(currentManager);
							} else {
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter Integer for Current Reporting Manager ID in yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter Integer for Current Reporting Manager ID in row "+count);
							}
						}
						if (resourceExcelMapping.getCurrent_reporting_manager_two() != null
								&& !resourceExcelMapping
										.getCurrent_reporting_manager_two().equals(
												"")) {
							if (validateIntegerDigits(resourceExcelMapping
									.getCurrent_reporting_manager_two())) {
								Resource currentManager2 = new Resource();
								currentManager2.setEmployeeId(resourceExcelMapping
										.getCurrent_reporting_manager_two());
								resource.setCurrentReportingManagerTwo(currentManager2);
							} else {
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter Integer for Current Reporting Manager Two ID in yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter Integer for Current Reporting Manager Two ID in row "+count);
							}

						}
						if (resourceExcelMapping.getContactNumber() != ""
								&& resourceExcelMapping.getContactNumber() != null) {
							if (validateMobileNumber(resourceExcelMapping
									.getContactNumber())) {
								resource.setContactNumber(resourceExcelMapping
										.getContactNumber());
							} else {
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter correct format for Contact Number in yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter correct format for Contact Number for row "+count);
							}
						}
						if (resourceExcelMapping.getContactNumberTwo() != ""
								&& resourceExcelMapping.getContactNumberTwo() != null) {
							if (validateMobileNumber(resourceExcelMapping
									.getContactNumberTwo())) {
								resource.setContactNumberTwo(resourceExcelMapping
										.getContactNumberTwo());
							} else {
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter correct format for Contact Number Two in yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter correct format for Contact Number Two for row "+count);
							}
						}
						if (resourceExcelMapping.getTransferDate() != null
								&& resourceExcelMapping.getTransferDate()
										.toString() != "") {
							resource.setTransferDate(resourceExcelMapping
									.getTransferDate());
							/*
							 * if
							 * (validateDate(resourceExcelMapping.getTransferDate(
							 * ))) { } else { errorList .add(
							 * "Please enter correct format for Tranfer Date MM-DD-YYYY"
							 * ); }
							 */
						}
						if (resourceExcelMapping.getVisaValidDate() != null
								&& resourceExcelMapping.getVisaValidDate()
										.toString() != "") {
							resource.setVisaValid(resourceExcelMapping
									.getVisaValidDate());
							/*
							 * if (validateDate(resourceExcelMapping
							 * .getVisaValidDate())) { } else { errorList .add(
							 * "Please enter correct format for Visa Date MM-DD-YYYY"
							 * ); }
							 */
						}
						if (resourceExcelMapping.getLastAppraisalDate() != null
								&& resourceExcelMapping.getLastAppraisalDate()
										.toString() != "") {
							resource.setLastAppraisal(resourceExcelMapping
									.getLastAppraisalDate());
							/*
							 * if (validateDate(resourceExcelMapping
							 * .getLastAppraisalDate())) { } else { errorList .add(
							 * "Please enter correct format for Last Appraisal Date MM-DD-YYYY"
							 * ); }
							 */
						}
						if (resourceExcelMapping.getPenultimateAppraisalDate() != null
								&& resourceExcelMapping
										.getPenultimateAppraisalDate().toString() != "") {
							resource.setPenultimateAppraisal(resourceExcelMapping
									.getPenultimateAppraisalDate());
							/*
							 * if (validateDate(resourceExcelMapping
							 * .getPenultimateAppraisalDate())) { } else { errorList
							 * .add(
							 * "Please enter correct format for Penultimate Appraisal Date MM-DD-YYYY"
							 * ); }
							 */
						}

						if (resourceExcelMapping.getConfirmationDate() != null
								&& resourceExcelMapping.getConfirmationDate()
										.toString() != "") {
							resource.setConfirmationDate(resourceExcelMapping
									.getConfirmationDate());
							/*
							 * if (validateDate(resourceExcelMapping
							 * .getConfirmationDate())) { } else { errorList .add(
							 * "Please enter correct format for Confirmation Date MM-DD-YYYY"
							 * ); }
							 */
						}

						resource.setCustomerIdDetail(resourceExcelMapping
								.getCustomerIdDetail());
						
						if(resourceExcelMapping.getPrimarySkills()!="" && resourceExcelMapping.getPrimarySkills() != null)
						{
							if(validateIntegerDigits(resourceExcelMapping.getPrimarySkills())){
								SkillResourcePrimary primary= new SkillResourcePrimary();
								primary.setSkillId((skillsService.find(Integer.parseInt(resourceExcelMapping.getPrimarySkills()))));
								Set<SkillResourcePrimary> mySet = new HashSet<SkillResourcePrimary>(); 
								mySet.add(primary);
								resource.setSkillResourcePrimaries(mySet);	
							}else{
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter Integer values for Primary skills in yash employee Id "+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter a valid Integer values for Primary skills for row "+count);
							}
							
						}else{
							if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
								errorList.add("Please enter Primary Skills for yash employee Id "+resourceExcelMapping.getYashEmpId());
							}
							errorList.add("Please enter a valid Primary Skills for row "+count);
						}
						
						if(resourceExcelMapping.getSecondarySkills()!="" && resourceExcelMapping.getSecondarySkills() != null)
						{
							if(validateIntegerDigits(resourceExcelMapping.getSecondarySkills())){
								SkillResourceSecondary secondary= new SkillResourceSecondary();
								secondary.setSkillId(skillsService.find((Integer.parseInt(resourceExcelMapping.getSecondarySkills()))));
							 	Set<SkillResourceSecondary> secondarySet= new HashSet<SkillResourceSecondary>();
								secondarySet.add(secondary);
							   resource.setSkillResourceSecondaries(secondarySet);
							}else{
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Please enter Integer values for Secondary skills in yash employee Id"+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Please enter a valid Integer values for Secondary skills for row "+count);
							}
							
						}else{
							if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
								errorList.add("Please enter Secondary Skills for yash employee Id "+resourceExcelMapping.getYashEmpId());
							}
							errorList.add("Please enter a valid Secondary Skills for row "+count);
						}
						
						
					   
						if (resourceExcelMapping.getReleaseDate() != null
								&& resourceExcelMapping.getReleaseDate().toString() != "") {
							resource.setReleaseDate(resourceExcelMapping
									.getReleaseDate());
							/*
							 * if
							 * (validateDate(resourceExcelMapping.getReleaseDate()))
							 * { } else { errorList .add(
							 * "Please enter correct format for Release Date MM-DD-YYYY"
							 * ); }
							 */
						}
						resource.setAwardRecognition(resourceExcelMapping
								.getAwardRecognition());
						resource.setProfitCentre(resourceExcelMapping
								.getProfitCentre());

						if (resourceExcelMapping.getUserRole() != null
								&& resourceExcelMapping.getUserRole() != "") {
							if (roles.contains(resourceExcelMapping.getUserRole())) {
								resource.setUserRole(resourceExcelMapping
										.getUserRole());
							} else {
								if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
									errorList.add("Roles should be ROLE_ADMIN,ROLE_MANAGER,ROLE_USER,ROLE_BEHALF_MANAGER in yash employee Id"+resourceExcelMapping.getYashEmpId());
								}
								errorList.add("Roles should be ROLE_ADMIN,ROLE_MANAGER,ROLE_USER,ROLE_BEHALF_MANAGER for row "+count);
							}
						} else {
							if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
								errorList.add("Please enter UserRole for yash employee Id"+resourceExcelMapping.getYashEmpId());
							}
							errorList.add("Please enter a valid UserRole for row "+count);
						}

						if (resourceExcelMapping.getUserName() != null
								&& resourceExcelMapping.getUserName() != "") {
							resource.setUserName(resourceExcelMapping.getUserName());
						} else {
							if (resourceExcelMapping.getYashEmpId() != null && !resourceExcelMapping.getYashEmpId().isEmpty()) {
								errorList.add("Please enter UserName for yash employee Id"+resourceExcelMapping.getYashEmpId());
							}
							errorList.add("Please enter a valid UserName for row "+count);
						}

						/*
						 * resource.setCreatedId(new UserContextDetails()
						 * .getEmployeeId().toString());
						 */
						try {
							if (errorList.size() == 0) {
								
								resourceService.saveOrupdate(resource);
								/*uiModel.addAttribute(Constants.ERROR_LIST, errorList);
								
								return "uploadresources/uploadresources";*/
							} 
						} catch(org.springframework.dao.DataIntegrityViolationException ex){
							logger.info("------Exception occured while uploading the resources "+ex);
							errorList.add(ex.getCause().getMessage());
						}
						catch (Exception exception) {
							logger.info("------Exception occured while uploading the resources "+exception);
							errorList.add(exception.getMessage());
						}
count++;
					}
				}else{
					if (errorList.size() > 0) {
						
						uiModel.addAttribute(Constants.ERROR_LIST, errorList);
						logger.info("------UploadResourceController uploadExcelResources method end------");
						return "uploadresources/uploadresources";
					} 
				}
			}
			logger.info("------UploadResourceController uploadExcelResources method end------");
		} catch (NumberFormatException e) {
			logger.error("Exception occured in uploadExcelResources method of uploadResource controller:"+e);
			throw e;
		}catch (Exception e) {
			logger.error("Exception occured in uploadExcelResources method of uploadResource controller:"+e);
			throw e;
		}
		
		if (errorList.size() > 0) {
			uiModel.addAttribute(Constants.ERROR_LIST, errorList);
			return "uploadresources/uploadresources";
		}else{
				return "redirect:/resources";
		}
	}

	boolean validateMobileNumber(String mobileNumber) {
		logger.info("------UploadResourceController validateMobileNumber method start------");
		Pattern p = Pattern.compile("^(?:[0-9]{5}|[0-9]{10}|)$");
		Matcher m = p.matcher(mobileNumber);
		if (m.matches()) {
			logger.info("------UploadResourceController validateMobileNumber method end------");
			return true;
		}
		logger.info("------UploadResourceController validateMobileNumber method end------");
		return false;
	}

	boolean validateIntegerDigits(Integer integerValue) {
		logger.info("------UploadResourceController validateIntegerDigits method start------");
		String str = integerValue.toString();
		if (str.matches("^\\d+$")) {
			logger.info("------UploadResourceController validateIntegerDigits method end------");
			return true;
		}
		logger.info("------UploadResourceController validateIntegerDigits method end------");
		return false;
	}

	boolean validateIntegerDigits(String stringValue) {
		logger.info("------UploadResourceController validateIntegerDigits method start------");
		String str = stringValue.toString();
		if (str.matches("^\\d+$")) {
			logger.info("------UploadResourceController validateIntegerDigits method end------");
			return true;
		}
		logger.info("------UploadResourceController validateIntegerDigits method end------");
		return false;
	}
	
	boolean validateDate(Date dateToValidate) {
		logger.info("------UploadResourceController validateDate method start------");
		String currentDate = dateToValidate.toString();
		SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yyyy");
		sdf.setLenient(false);
		try {
			// if not valid, it will throw ParseException
			Date date = sdf.parse(currentDate);
			// System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("RuntimeException occured in showJson method of UploadResourceController controller:"+e);
			return false;
		}
		logger.info("------UploadResourceController validateDate method end------");
		return true;
	}

	
	boolean validateString(String stringValue) {
		logger.info("------UploadResourceController validateString method start------");
		String str = stringValue.toString();
		if (str.matches("[a-zA-Z]+")) {		
			return true;
		}
		logger.info("------UploadResourceController validateStringDigits method end------");
		return false;
	}
	
	boolean validateAlphaNumeric(String stringValue) {
		logger.info("------UploadResourceController validateAlphaNumeric method start------");
		String str = stringValue.toString();
		if (str.matches("[a-zA-Z0-9]+")) {		
			return true;
		}
		logger.info("------UploadResourceController validateAlphaNumeric method end------");
		return false;
	}
	
}