/**
 * 
 */
package org.yash.rms.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.yash.rms.domain.BGAdmin_Access_Rights;
import org.yash.rms.domain.Competency;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.EmployeeCategory;
import org.yash.rms.domain.Grade;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Ownership;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.ResourceLoanTransfer;
import org.yash.rms.domain.SkillResourcePrimary;
import org.yash.rms.domain.SkillResourceSecondary;
import org.yash.rms.domain.Skills;
import org.yash.rms.domain.Visa;
import org.yash.rms.form.UserActivityForm;
import org.yash.rms.helper.EmailHelper;
import org.yash.rms.helper.ResourceHelper;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.LocationService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ProjectAllocationService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.service.ResourceLoanAndTransferService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.service.SkillsService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.GenericSearch;
import org.yash.rms.util.ResourceSearchCriteria;
import org.yash.rms.util.UserUtil;

import com.google.gson.Gson;

import flexjson.JSONSerializer;

/**
 * @author arpan.badjatiya
 * 
 */
@Controller
@RequestMapping("/resources")
public class ResourceController {
	byte[] uploadImageTemporary = null;// upload image

	@Autowired
	@Qualifier("ResourceService")
	ResourceService resourceService;
	@Autowired
	@Qualifier("DesignationService")
	RmsCRUDService<Designation> designationService;
	@Autowired
	@Qualifier("gradeService")
	RmsCRUDService<Grade> gradeService;

	@Autowired
	@Qualifier("LocationService")
	RmsCRUDService<Location> locationService;
	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;

	@Autowired
	@Qualifier("VisaService")
	RmsCRUDService<Visa> visaService;

	@Autowired
	@Qualifier("OwnershipService")
	RmsCRUDService<Ownership> ownershipService;

	@Autowired
	@Qualifier("EmployeeCategoryService")
	RmsCRUDService<EmployeeCategory> employeeCategoryService;

	@Autowired
	@Qualifier("CompetencyService")
	RmsCRUDService<Competency> competencyService;

	@Autowired
	@Qualifier("ResourceLoanAndTransferService")
	ResourceLoanAndTransferService resourceLoanAndTransferService;

	@Autowired
	@Qualifier("ResourceAllocationService")
	ResourceAllocationService resourceAllocationService;

	@Autowired
	@Qualifier("ProjectAllocationService")
	ProjectAllocationService projectAllocationService;

	@Autowired
	@Qualifier("SkillsService")
	SkillsService skillsService;
	@Autowired
	JsonObjectMapper<Resource> jsonMapper;

	@Autowired
	EmailHelper emailHelper;

	@Autowired
	ResourceHelper resourceHelper;

	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	@Qualifier("ProjectService")
	ProjectService projectService;

	@Autowired
	GenericSearch genericSearch;
	
	@Autowired
	@Qualifier("LocationService")
	LocationService locService;
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		logger.info("------ResourceController initBinder method start------");

		try {
			binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
			binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(Constants.DATE_PATTERN_4), true));

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in initBinder method of ResourceController:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in initBinder method of ResourceController:" + exception);
			throw exception;
		}
		logger.info("------ResourceController initBinder method end------");
	}

	@RequestMapping(method = RequestMethod.GET)
	public String load(Model uiModel) throws Exception {
		uiModel.addAttribute(Constants.DESIGNATION, designationService.findAll());
		// Set Header values...
		Resource currentLoggedInResource=userUtil.getLoggedInResource();
		
		try {
			if (currentLoggedInResource.getUploadImage() != null && currentLoggedInResource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(currentLoggedInResource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			} else {
				uiModel.addAttribute("UserImage", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		uiModel.addAttribute("firstName", currentLoggedInResource.getFirstName() + " " + currentLoggedInResource.getLastName());
		uiModel.addAttribute("designationName", currentLoggedInResource.getDesignationId().getDesignationName());

		
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentLoggedInResource.getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		uiModel.addAttribute("DOJ", months + "-" + year);
		uiModel.addAttribute("ROLE", currentLoggedInResource.getUserRole());
		// End

		uiModel.addAttribute(Constants.GRADE, gradeService.findAll());
		uiModel.addAttribute(Constants.BUS, buService.findAllBu());
		Set<OrgHierarchy> childOrgHierarchies = null != buService.findOrgHierarchyList() ? (null != buService.findOrgHierarchyList().getChildOrgHierarchies() ? buService.findOrgHierarchyList()
				.getChildOrgHierarchies() : new HashSet<OrgHierarchy>()) : new HashSet<OrgHierarchy>();
		uiModel.addAttribute(Constants.BGS, childOrgHierarchies);
		uiModel.addAttribute(Constants.LOCATIONS, locationService.findAll());
		uiModel.addAttribute(Constants.VISA, visaService.findAll());
		uiModel.addAttribute(Constants.EMPLOYEECATEGORY, employeeCategoryService.findAll());
		uiModel.addAttribute(Constants.COMPETENCY, competencyService.findAll());
		uiModel.addAttribute(Constants.OWNER, ownershipService.findAll());
		uiModel.addAttribute(Constants.PRIMARY_SKILLS, skillsService.findPrimarySkills());
		uiModel.addAttribute(Constants.SECONDRY_SKILLS, skillsService.findSecondarySkills());
		//uiModel.addAttribute(Constants.RESOURCES_RM, resourceService.findAll());
		return "resources/list";
	}

	@RequestMapping(value = "/list/{ActiveOrAll}", method = RequestMethod.GET)
	public ResponseEntity<String> list(@PathVariable("ActiveOrAll") String ActiveOrAll, HttpServletRequest request, Model uiModel) throws Exception {
		logger.info("------ResourceController list method start------");
		JSONObject resultJSON = new JSONObject();
		try {
			Integer currentLoggedInUserId = UserUtil.getCurrentResource().getEmployeeId();

			boolean isCurrentUserAdmin = UserUtil.isCurrentUserIsAdmin();
			boolean isCurrentUserDelManager = UserUtil.isCurrentUserIsDeliveryManager();
			Resource currentResource = resourceService.find(currentLoggedInUserId);
			List<Resource> resources = null;
			boolean isCurrentUserBusinessGroupAdmin = UserUtil.isCurrentUserIsBusinessGroupAdmin();
			// HR
			boolean isCurrentUserHr = UserUtil.isCurrentUserIsHr();

			Integer page = Integer.parseInt(request.getParameter("iDisplayStart"));
			Integer size = Integer.parseInt(request.getParameter("iDisplayLength"));

			// get column position
			String iSortCol = request.getParameter("iSortCol_0");

			// for sorting direction
			String sSortDir = request.getParameter("sSortDir_0");
			String columnName = "";

			Integer iColumns = Integer.parseInt(request.getParameter("iColumns"));
			ResourceSearchCriteria resSearchCriteria = new ResourceSearchCriteria();
			String[] searchColumns = resSearchCriteria.getCols();
			String sSearch = "";

			// used in case of sorting
			if ((iSortCol != null || iSortCol != "") && (sSortDir != null || sSortDir != "")) {
				resSearchCriteria.setOrderStyle(sSortDir);
				resSearchCriteria.setOrederBy(resSearchCriteria.getCols()[Integer.parseInt(iSortCol)]);

				resSearchCriteria.setSortTableName(resSearchCriteria.getColandTableNameMap().get(resSearchCriteria.getCols()[Integer.parseInt(iSortCol)]));

				resSearchCriteria.setRefTableName(resSearchCriteria.getColandTableNameMap().get(resSearchCriteria.getCols()[Integer.parseInt(iSortCol)]));

				resSearchCriteria.setRefColName(resSearchCriteria.getCols()[Integer.parseInt(iSortCol)]);
			}

			for (int i = 0; i < iColumns; i++) {
				sSearch = request.getParameter("sSearch_" + i);
				// execute if there is any value in any of the search box
				if (sSearch.length() > 0) {
					columnName = searchColumns[i];
					resSearchCriteria.setRefTableName(resSearchCriteria.getColandTableNameMap().get(columnName));
					resSearchCriteria.setRefColName(columnName);
					resSearchCriteria.setSearchValue(sSearch);
					break;
				}
			}
			// totalResources == used to get total entries in database so that
			// it can be set in iTotalRecords
			List<Resource> totalResources = new ArrayList<Resource>();
			long totalCount = 0;

			if (isCurrentUserAdmin) {

				if (sSearch.length() > 0) {
					if (ActiveOrAll.equals("all")) {
						totalResources = resourceService.searchResources(resSearchCriteria, null, null, null,null, null, false, isCurrentUserDelManager);
						resources = resourceService.searchResources(resSearchCriteria, null, null, null,page, size, false, isCurrentUserDelManager);
					} else if (ActiveOrAll.equals("active")) {
						totalResources = resourceService.searchResources(resSearchCriteria, null, null, null, null, null, true, isCurrentUserDelManager);
						resources = resourceService.searchResources(resSearchCriteria, null, null, null, page, size, true, isCurrentUserDelManager);
					}

				} else {
					if (ActiveOrAll.equals("all")) {
						totalCount = resourceService.countTotal();
						resources = resourceService.findResourceEntriesPagination(page, size, resSearchCriteria, false);
					}
					if (ActiveOrAll.equals("active")) {
						totalCount = resourceService.countActive();
						resources = resourceService.findResourceEntriesPagination(page, size, resSearchCriteria, true);
					}
				}
			}

			else if (isCurrentUserBusinessGroupAdmin || isCurrentUserHr) {

				List<OrgHierarchy> buList = UserUtil.getCurrentResource().getAccessRight();
				List<Integer> projectIdList = projectAllocationService.getProjectIdsForBGAdmin(buList);

				if (sSearch.length() > 0) {
					if (ActiveOrAll.equals("all")) {
						totalResources = resourceService.searchResources(resSearchCriteria, buList,currentResource ,projectIdList, null, null, false, isCurrentUserDelManager);
						resources = resourceService.searchResources(resSearchCriteria, buList, currentResource,projectIdList, page, size, false, isCurrentUserDelManager);
					} else if (ActiveOrAll.equals("active")) {
						totalResources = resourceService.searchResources(resSearchCriteria, buList, currentResource,projectIdList, null, null, true, isCurrentUserDelManager);
						resources = resourceService.searchResources(resSearchCriteria, buList,currentResource ,projectIdList, page, size, true, isCurrentUserDelManager);
					}

				} else {

					if (ActiveOrAll.equals("all")) {
						totalCount = resourceService.countResourceForBGADMIN(buList, projectIdList, false, false);
						resources = resourceService.findResourcesByBusinessGroupsPagination(buList, projectIdList, page, size, resSearchCriteria, false, false);
					}
					if (ActiveOrAll.equals("active")) {

						totalCount = resourceService.countResourceForBGADMIN(buList, projectIdList, true, false);
						resources = resourceService.findResourcesByBusinessGroupsPagination(buList, projectIdList, page, size, resSearchCriteria, true, false);
					}

				}
			}
			else if (isCurrentUserDelManager) {
				if (sSearch.length() > 0) {
					if (ActiveOrAll.equals("all")) {
						totalResources = resourceService.searchResources(resSearchCriteria, null, currentResource, null,null, null, false, isCurrentUserDelManager);
						resources = resourceService.searchResources(resSearchCriteria, null, currentResource, null,page, size, false, isCurrentUserDelManager);
					} else if (ActiveOrAll.equals("active")) {
						totalResources = resourceService.searchResources(resSearchCriteria, null, currentResource, null,null, null, true, isCurrentUserDelManager);
						resources = resourceService.searchResources(resSearchCriteria, null, currentResource, null,page, size, true, isCurrentUserDelManager);
					}

				} else {
					if (ActiveOrAll.equals("all")) {
						totalCount = resourceService.countResourceByRM1RM2(currentResource, false);
						resources = resourceService.findResourceByRM1RM2Pagination(currentResource, page, size, resSearchCriteria, false);
					} else {
						totalCount = resourceService.countResourceByRM1RM2(currentResource, true);
						resources = resourceService.findResourceByRM1RM2Pagination(currentResource, page, size, resSearchCriteria, true);
					}
				}
			}

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			if (resources == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
			logger.info("------ResourceController List method end----");
			resultJSON.put("aaData", Resource.toJsonArray(resources));
			if (sSearch.length() > 0) {
				resultJSON.put("iTotalRecords", totalResources.size());
				resultJSON.put("iTotalDisplayRecords", totalResources.size());
			} else {
				resultJSON.put("iTotalRecords", totalCount);
				resultJSON.put("iTotalDisplayRecords", totalCount);
			}

			return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in list method of ResourceController:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in list method of ResourceController:" + exception);
			throw exception;
		}
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=text/html")
	public String create(@ModelAttribute org.yash.rms.form.NewEmployee newEmployee, @RequestParam(value = "bgAdminListOfBu", required = false) String bgAdminListOfBu,
			@RequestParam(value = "resourcePrimaryskillsList", required = false) String resourcePrimaryskillsList,
			@RequestParam(value = "resourceSecondaryskillsList", required = false) String resourceSecondaryskillsList, HttpServletRequest request, Model uiModel, HttpServletResponse response,
			BindingResult bindingResult) throws Exception {
		logger.info("------ResourceController create method start------");

		try {
			if (null == newEmployee) {
				throw new IllegalArgumentException("Employee Form Object Cannot be null");
			}

			if (bindingResult.hasErrors()) {
				throw new RuntimeException("Cannot Save Resource due to " + bindingResult.getAllErrors());
			}

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			Set<BGAdmin_Access_Rights> bgAdmin_Access_Rights = new HashSet<BGAdmin_Access_Rights>();
			Set<SkillResourcePrimary> primarySkill = new HashSet<SkillResourcePrimary>();
			Set<SkillResourceSecondary> secondarySkill = new HashSet<SkillResourceSecondary>();

			// Adding access rights for HR_ROLE
			if ((newEmployee.getUserRole().equals("ROLE_BG_ADMIN") || newEmployee.getUserRole().equals("ROLE_HR")) && null != bgAdminListOfBu && !bgAdminListOfBu.isEmpty()) {
				String[] bgAdminList = bgAdminListOfBu.split(",");

				for (String buId : bgAdminList) {
					// Setting bu id
					BGAdmin_Access_Rights access_Rights = new BGAdmin_Access_Rights();
					OrgHierarchy hierarchy = new OrgHierarchy();
					hierarchy.setId(Integer.parseInt(buId.trim()));
					access_Rights.setOrgId(hierarchy);

					if (null != newEmployee.getEmployeeId()) {
						Resource resource = new Resource();
						resource.setEmployeeId(newEmployee.getEmployeeId());
						access_Rights.setResourceId(resource);
					}
					bgAdmin_Access_Rights.add(access_Rights);

				}
			}
			if (null != resourcePrimaryskillsList && !resourcePrimaryskillsList.isEmpty()) {
				String[] resourcePrimaryskills = resourcePrimaryskillsList.split(",");
				for (String resourcePrimarySkill : resourcePrimaryskills) {
					SkillResourcePrimary primarySkillResource = new SkillResourcePrimary();
					Skills skill = new Skills();
					skill.setId(Integer.parseInt(resourcePrimarySkill));
					primarySkillResource.setSkillId(skill);
					primarySkill.add(primarySkillResource);
				}
			}
			if (null != resourceSecondaryskillsList && !resourceSecondaryskillsList.isEmpty()) {
				String[] resourceSecondaryskills = resourceSecondaryskillsList.split(",");
				for (String resourceSecondarySkill : resourceSecondaryskills) {
					Skills skill = new Skills();
					SkillResourceSecondary secondarySkillResource = new SkillResourceSecondary();
					skill.setId(Integer.parseInt(resourceSecondarySkill));
					secondarySkillResource.setSkillId(skill);
					secondarySkill.add(secondarySkillResource);

				}
			}
			// String[] resourceSecondaryskills =
			// resourceSecondaryskillsList.split(",");
			newEmployee.setBgAdminAccessRightlist(bgAdmin_Access_Rights);
			newEmployee.setSkillResourcePrimaries(primarySkill);
			newEmployee.setSkillResourceSecondaries(secondarySkill);

			Location location   = locService.findLocationByName(newEmployee.getPreferredLocation().getLocation());
			newEmployee.setPreferredLocation(location);
			
			String sendMailTo = newEmployee.getEmailId();
			String userName = newEmployee.getFirstName();
			newEmployee.setUploadImage(uploadImageTemporary); // upload image
			if(newEmployee.getReportUserId()!=null && newEmployee.getReportUserId() == 1) {
				newEmployee.setReportUserId(Constants.REPORT_ACCESS);
			}else {
				newEmployee.setReportUserId(Constants.REPORT_ACCESS3);
			}
						
			/** Start- Added By Anjana for Resume and TEF upload Download **/
			if (null != newEmployee.getUploadResumeFileName() && !newEmployee.getUploadResumeFileName().isEmpty()) {
				String[] splitResumeparts = newEmployee.getUploadResumeFileName().split("\\\\");
				// System.out.println("getUploadResumeFileName" +
				// splitResumeparts[splitResumeparts.length-1]);
				if (null != splitResumeparts[splitResumeparts.length - 1] && !splitResumeparts[splitResumeparts.length - 1].isEmpty())
					newEmployee.setUploadResumeFileName(splitResumeparts[splitResumeparts.length - 1]);
			}
			if (null != newEmployee.getUploadTEFfileName() && !newEmployee.getUploadTEFfileName().isEmpty()) {
				String[] splitTEFparts = newEmployee.getUploadTEFfileName().split("\\\\");
				// System.out.println("getUploadTEFfileName" +
				// splitTEFparts[splitTEFparts.length-1]);
				if (null != splitTEFparts[splitTEFparts.length - 1] && !splitTEFparts[splitTEFparts.length - 1].isEmpty())
					newEmployee.setUploadTEFfileName(splitTEFparts[splitTEFparts.length - 1]);
			}
			/** End- Added By Anjana for Resume and TEF upload Download **/
			/*Start - Remove yash exp while saving experiences */
			String yearDiff= Resource.getCalYearDiff(newEmployee.getDateOfJoining(), newEmployee.getReleaseDate());
			double diff=Double.parseDouble(yearDiff);
			double totalExp = Double.parseDouble(Resource.getCalcDiffTotalAndYash(newEmployee.getTotalExper(), diff));
			newEmployee.setTotalExper(totalExp);
			double relevantExp = Double.parseDouble(Resource.getCalcDiffTotalAndYash(newEmployee.getRelevantExper(), diff));
			newEmployee.setRelevantExper(relevantExp);
			/*End - Remove yash exp while saving experiences*/
			boolean result = resourceService.save(newEmployee);
			String empId = newEmployee.getYashEmpId();
			Resource selectedResource = resourceService.findResourcesByYashEmpIdEquals(empId);

			if (newEmployee.getEmployeeId() == null) {
				System.out.println("***** New Resource *****");
				Resource resourceRM1 = resourceService.find(newEmployee.getCurrentReportingManager().getEmployeeId());
				Resource resourceRM2 = resourceService.find(newEmployee.getCurrentReportingManagerTwo().getEmployeeId());
				String currentBU = selectedResource.getCurrentBuId().getParentId().getName() + "-" + selectedResource.getCurrentBuId().getName();
				String parentBU = selectedResource.getBuId().getParentId().getName() + "-" + selectedResource.getBuId().getName();

				String rm1Name = resourceRM1.getFirstName() + " " + resourceRM1.getLastName();
				String rm2Name = resourceRM2.getFirstName() + " " + resourceRM2.getLastName();
				String rm1EmailId = resourceRM1.getEmailId();
				String rm2EmailId = resourceRM2.getEmailId();
				Character userProfile = 'Y';
				String details[] = { rm1Name, rm2Name, rm1EmailId, rm2EmailId, currentBU, parentBU };

				if (result) {
					String[] parts = parentBU.split("-");
					String bg = parts[0]; // 004
					// String bu = parts[1]; // 034556
					logger.info("Parent BG Name: " + bg);
					if (bg.contains("BG4")) {
						Map<String, Object> model = new HashMap<String, Object>();
						resourceHelper.setEmailContentNewResource(model, sendMailTo, userName, request, details, userProfile);
						emailHelper.sendEmail(model, sendMailTo);
					}
				}
				System.out.println("***** End New Resource *****");
			}

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in create method of ResourceController:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in create method of ResourceController:" + exception);
			throw exception;
		}
		logger.info("------ResourceController create method end------");
		// #605
		return "redirect:/resources";
	}

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> validateData(@RequestParam(value = "name") String name, @RequestParam(value = "value") String value, @RequestParam(value = "employeeId") int employeeId,
			@RequestParam(required = false, value = "rejoiningFlag") boolean rejoiningFlag, HttpServletResponse response) throws Exception {
		logger.info("------ResourceController validateData method start----");
		boolean result = true;
		HttpHeaders headers = new HttpHeaders();
		Resource resource = null;
		Resource resource2 = null;
		try {
			headers.add("Content-Type", "application/json; charset=utf-8");
			if ((value != null) && (value.trim().length() > 0)) {

				if ((name != null) && (name.trim().equalsIgnoreCase("emailId"))) {
					resource = resourceService.findResourcesByEmailIdEqualsForValidVal(value, employeeId);
					resource2 = resourceService.findResourcesByEmailIdEquals(value);
					if (resource == null && resource2 != null) {
						if (resource2.getEmailId().equals(value)) {
							if (rejoiningFlag == true) {
								if (resource.getReleaseDate() != null) {
									if (resource.getReleaseDate().before(new Date())) {
										result = true;
									} else {
										result = false;
									}
								} else {
									result = false;
								}

							} else {
								result = false;
							}
						}
					}
				} else if ((name != null) && (name.trim().equalsIgnoreCase("yashEmpId"))) {
					resource = resourceService.findResourcesByYashEmpIdEquals(value);
				}

				if (resource != null) {
					if (employeeId > 0) {

						if (employeeId != resource.getEmployeeId().intValue()) {
							result = false;
						}

					} else if (resource.getEmailId().toLowerCase().equals(value.toLowerCase()) || resource.getYashEmpId().toLowerCase().equals(value.toLowerCase())) {

						if (resource.getEmailId().toLowerCase().equals(value.toLowerCase())) {
							if (rejoiningFlag == true) {
								if (resource.getReleaseDate() != null) {
									if (resource.getReleaseDate().before(new Date())) {
										result = true;
									} else {
										result = false;
									}
								} else {
									result = false;
								}
							} else {
								result = false;
							}
						}

						if (resource.getYashEmpId().toLowerCase().equals(value.toLowerCase())) {
							result = false;
						}
					}
				}
			}

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in validateData method of ResourceController:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in validateData method of ResourceController:" + exception);
			throw exception;
		}
		logger.info("------ResourceController validateData method end----");
		return new ResponseEntity<String>("{ \"result\":" + result + "}", headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{employeeId}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("employeeId") Integer employeeId, Model uiModel) throws Exception {
		logger.info("------ResourceController showJson method start----");
		try {
			Resource resource = resourceService.find(employeeId);
			uploadImageTemporary = resource.getUploadImage(); // upload image
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			if (resource == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
			logger.info("------ResourceController showJson method end----");
			/*Start - Code to add yash exp in Total and Relevant While DISPLAY ONLY*/
			String yearDiff= Resource.getCalYearDiff(resource.getDateOfJoining(), resource.getReleaseDate());
			resource.setYearDiff(yearDiff);			
			double diff=Double.parseDouble(yearDiff);
			
			double totalExp = Double.parseDouble(Resource.calcYear(resource.getTotalExper(), diff));
			double relevantExp = Double.parseDouble(Resource.calcYear(resource.getRelevantExper(), diff));
			
			resource.setTotalExper(totalExp);
			resource.setRelevantExper(relevantExp);
			/*End-Code to add yash exp in Total and Relevant While DISPLAY ONLY*/
			String resourceJson = resource.toJson();
			JSONObject json = new JSONObject(resource.toJson());
			if (resource.getCurrentReportingManager() != null) {
				if (resource.getEmployeeId() == resource.getCurrentReportingManager().getEmployeeId()) {
					JSONObject jsonObj = new JSONObject("{employeeId:" + resource.getEmployeeId() + "}");
					if (resource.getEmployeeId().equals(resource.getCurrentReportingManager().getEmployeeId())) {
						json.put("currentReportingManager", jsonObj);
					}
				}
			}

			if (resource.getCurrentReportingManagerTwo() != null) {
				if (resource.getEmployeeId() == resource.getCurrentReportingManagerTwo().getEmployeeId()) {
					JSONObject jsonObj = new JSONObject("{employeeId:" + resource.getEmployeeId() + "}");
					if (resource.getEmployeeId().equals(resource.getCurrentReportingManagerTwo().getEmployeeId())) {
						json.put("currentReportingManagerTwo", jsonObj);
					}
				}
			}
			
			/*JSONArray selectedProjectJsonArray = new JSONArray();
			JSONArray unSelectedProjectJsonArray = new JSONArray();
			 String reportAccessProjectIds = projectService.findReportAccessForEmployee(Integer.parseInt(resource.getYashEmpId()));
			 List<String> projectIds = new ArrayList<String>(Arrays.asList(reportAccessProjectIds.split(",")));
				List<Project> projects =    projectService.findAllActiveProjects();
				for (Project project : projects) {
					if(projectIds.contains(project.getId().toString())){
						JSONObject selectedProjectJson = new JSONObject();
						selectedProjectJson.put("id", project.getId());
						selectedProjectJson.put("projectName", project.getProjectName());
						selectedProjectJsonArray.put(selectedProjectJson);
					}else{
						JSONObject unSelectedProjectJson = new JSONObject();
						unSelectedProjectJson.put("id", project.getId());
						unSelectedProjectJson.put("projectName", project.getProjectName());
						unSelectedProjectJsonArray.put(unSelectedProjectJson);
					}
				}
				json.put("selectedProjectJsonArray",  selectedProjectJsonArray);  
				json.put("unSelectedProjectJsonArray",  unSelectedProjectJsonArray);  
				
				

			
			JSONArray projectJsonArray = new JSONArray();
			List<Project> projectList = projectService.findAllActiveProjects();
			for(Project p : projectList) {
		        JSONObject projectJson = new JSONObject();
		        projectJson.put("id", p.getId());
		        projectJson.put("projectName", p.getProjectName());
		        projectJsonArray.put(projectJson);
		    }
			json.put("projects",  projectJsonArray);  
			*/
			if (json != null) {
				/** Start- Added By Anjana for Resume and TEF upload Download **/
				JSONObject jsonObj1 = new JSONObject("{uploadResume:" + null + "}");
				JSONObject jsonObj2 = new JSONObject("{uploadTEF:" + null + "}");
				json.put("uploadResume", jsonObj1);
				json.put("uploadTEF", jsonObj2);
				/** End- Added By Anjana for Resume and TEF upload Download **/
				resourceJson = json.toString();
			}
			return new ResponseEntity<String>(resourceJson, headers, HttpStatus.OK);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in showJson method of ResourceController:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in list showJson of ResourceController:" + exception);
			throw exception;
		}
	}

	@RequestMapping(params = "userAction=ajaxCallForSessionTimeout")
	public ResponseEntity<String> ajaxCallForSessionTimeout() {

		System.out.println("--------------ajaxCallForSessionTimeout----------- Resource Controller---------");
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(params = "find=ByAllBuForGroupAdmin", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getBuListForGroupAdmin(@RequestParam("employeeId") Integer employeeId) throws Exception {
		logger.info("------ResourceController getBuListForGroupAdmin method start----");

		Resource resource = resourceService.find(employeeId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String bu = "";
		try {
			if (resource == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
			// bu = "";
			Set<BGAdmin_Access_Rights> bgAdminAccessRightlist = resource.getBgAdminAccessRightlist();
			StringBuilder builder = new StringBuilder();
			if (null != bgAdminAccessRightlist && !bgAdminAccessRightlist.isEmpty()) {
				Iterator<BGAdmin_Access_Rights> iterator = bgAdminAccessRightlist.iterator();
				while (iterator.hasNext()) {
					BGAdmin_Access_Rights access_Rights = iterator.next();
					builder.append(access_Rights.getOrgId().getId()).append(",");
				}
				bu = builder.toString();
				bu = bu.length() > 0 ? bu.substring(0, bu.length() - 1) : bu;
				bu = new JSONSerializer().serialize(bu);
			}
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in getBuListForGroupAdmin method of ResourceController:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in list getBuListForGroupAdmin of ResourceController:" + exception);
			throw exception;
		}

		logger.info("------ResourceController getBuListForGroupAdmin method end----");
		return new ResponseEntity<String>(bu, headers, HttpStatus.OK);
	}

	@RequestMapping(params = "find=FindResourceByResourceId", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> findTimeHours(@RequestParam("resourceId") Integer resourceId) throws Exception {
		logger.info("------ResourceController findTimeHours method start----");
		HttpHeaders headers = new HttpHeaders();
		Resource resource = new Resource();
		resource.setEmployeeId(resourceId);

		try {
			Resource res = resourceService.find(resourceId);

			headers.add("Content-Type", "application/json; charset=utf-8");
			logger.info("------ResourceController findTimeHours method end----");
			return new ResponseEntity<String>(res.toJson(), headers, HttpStatus.OK);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in findTimeHours method of ResourceController controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in findTimeHours method of ResourceController controller:" + exception);
			throw exception;
		}
	}

	@RequestMapping(value = "/downloadResourceExcel")
	protected void handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("------ResourceController handleRequestInternal method start----");
		ServletContext servletContext = request.getSession().getServletContext();
		String fullPath = servletContext.getRealPath("/WEB-INF//ResourceTemplate.xlsx");
		File file = new File(fullPath);
		response.setContentType("application/xlsx");
		response.setHeader("Content-Disposition", "attachment; filename=ResourceTemplate.xlsx");
		try {
			FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
			logger.info("------ResourceController handleRequestInternal method end----");
		} catch (IOException e) {
			logger.error("IOException occured in handleRequestInternal method of ResourceController controller:" + e);
			throw e;
		}
		return;

	}

	@RequestMapping(value = "/files/{id}", method = RequestMethod.GET)
	public void getFile(@PathVariable("id") Integer employeeId,// @PathVariable("fileName")
																// String
																// fileName,
			HttpServletResponse response) {
		try {
			// get your file as InputStream
			Resource resource = resourceService.find(employeeId);
			byte[] is = resourceService.viewUploadedResume(employeeId, resource.getResumeFileName());
			response.setHeader("Content-Disposition", "attachment; filename=" + resource.getResumeFileName());
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setContentType("application/force-download");
			response.setContentLength(is.length);

			// copy it to response's OutputStream
			ByteArrayInputStream bais = new ByteArrayInputStream(is);
			IOUtils.copy(bais, response.getOutputStream());

			response.flushBuffer();
		} catch (IOException ex) {
			throw new RuntimeException("IOError writing file to output stream");
		}

	}

	/**
	 * Start- Added By Anjana for Resume and TEF upload Download
	 * 
	 * @return
	 **/
	@RequestMapping(value = "/downloadfiles")
	public ResponseEntity<String> getDownloadResumeAndTEFfiles(HttpServletRequest request, @RequestParam("id") Integer employeeId, @RequestParam("downloadFileFlag") String downloadFileFlag,
			HttpServletResponse response) {

		ResponseEntity<String> jspResponse = null;
		HttpHeaders headers = new HttpHeaders();
		Resource resource = resourceService.find(employeeId);
		Blob blobUploadFile = null;
		byte[] uploadResume = null;
		byte[] uploadTEF = null;
		String fileName = null;

		if (null != resource.getUploadResume()) {
			uploadResume = resource.getUploadResume();
		}
		if (null != resource.getUploadTEF()) {
			uploadTEF = resource.getUploadTEF();
		}

		try {
			if ((null != employeeId && !employeeId.equals("")) && null != downloadFileFlag && !downloadFileFlag.equals("")) {

				if ((null != uploadResume && !uploadResume.equals("")) || (null != uploadTEF && !uploadTEF.equals(""))) {
					if (downloadFileFlag.equals("resume") || downloadFileFlag == "resume") {
						blobUploadFile = new javax.sql.rowset.serial.SerialBlob(uploadResume);
						if (resource.getUploadResumeFileName() != null) {
							fileName = resource.getUploadResumeFileName().trim();
						}
					}
					if (downloadFileFlag.equals("TEF") || downloadFileFlag == "TEF") {
						blobUploadFile = new javax.sql.rowset.serial.SerialBlob(uploadTEF);
						if (resource.getUploadTEFFileName() != null) {
							fileName = resource.getUploadTEFFileName().trim();
						}
					}
				}

				response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
				response.setHeader("Content-Transfer-Encoding", "binary");
				response.setContentType("application/force-download");
				if (null != blobUploadFile) {
					InputStream inStream = blobUploadFile.getBinaryStream();
					FileCopyUtils.copy(inStream, response.getOutputStream());
				} else {
					return jspResponse = new ResponseEntity<String>("File Not Found", headers, HttpStatus.EXPECTATION_FAILED);
				}
			}
		} catch (IOException ex) {
			throw new RuntimeException("IOError writing file to output stream");
		} catch (SerialException e) {
			throw new RuntimeException("Serial Exception occured in downloadfiles method of ResourceController" + e);
		} catch (SQLException e) {
			throw new RuntimeException("SQLException occured in downloadfiles method of ResourceController:" + e);
		}
		return jspResponse;
	}

	/** End- Added By Anjana for Resume and TEF upload Download **/

	@RequestMapping(value = "/loadHistory")
	@ResponseBody
	public ResponseEntity<String> loadHistory(@RequestParam(value = "resourceid") int resourceid) throws Exception {
		logger.info("------ResourceController loadHistory method start------");
		ResponseEntity response = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		try {

			List<ResourceLoanTransfer> currentResourceList = resourceLoanAndTransferService.find(resourceid);

			if (currentResourceList.size() == 0) {
				Resource resource = resourceService.find(resourceid);
				ResourceLoanTransfer loanTransfer = new ResourceLoanTransfer();
				loanTransfer = ResourceLoanTransfer.toResourceLoanTransfer(resource);
				currentResourceList.add(loanTransfer);
			}

			// for each event check if its null
			/*
			 * for (ResourceLoanTransfer resourceLoanTransfer :
			 * currentResourceList) { if (resourceLoanTransfer.getEventId() ==
			 * null || resourceLoanTransfer.getEventId().getId()<=0) {
			 * resourceLoanTransfer.setEventId(null); } }
			 */
			response = new ResponseEntity<String>(ResourceLoanTransfer.toJsonArray(currentResourceList), headers, HttpStatus.OK);
		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in loadHistory method of ResourceController:" + exception);
			throw exception;
		} catch (Exception e) {
			logger.error("Exception occured in loadHistory method of ResourceController:" + e);
			throw e;
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html", params = "userAction=redirectToLoanTransfer")
	public String redirectToLoanTransfer(@ModelAttribute UserActivityForm form, Model uiModel, HttpServletRequest httpServletRequest,
			@RequestParam(value = "employeeId", required = false) Integer employeeId) throws Exception {
		logger.info("------ResourceController saveOrUpdate method start------");
		return "redirect:/loanAndTransfer?employeeId=" + employeeId;

	}

	// ------------- this method is added to check for validating release dates
	// on the basis of Allocation End Date------------
	@RequestMapping(value = "/validateReleaseDate")
	@ResponseBody
	public ResponseEntity<String> validateReleaseDate(@RequestParam(value = "releaseDate") String releaseDate, @RequestParam(value = "employeeId") Integer employeeId) {
		logger.info("------ResourceController validateReleaseDate method start------");
		boolean result = true;
		try {

			if (employeeId != null) {
				Resource resource2 = new Resource();
				resource2.setEmployeeId(employeeId);
				List<ResourceAllocation> allocations = resourceAllocationService.findResourceAllocationsByEmployeeId(resource2);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_PATTERN_4);
				Date date;
				if (!(releaseDate.equals(""))) {
					date = simpleDateFormat.parse(releaseDate);

					// Date date = new
					for (ResourceAllocation allocation : allocations) {
						if (allocation.getAllocEndDate() == null || allocation.getAllocEndDate().after(date)) {
							result = false;
							break;
						}
					}
				}
				if (!result) {
					logger.info("------ResourceController validateReleaseDate method start------");
					return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("------ResourceController validateReleaseDate method start------");
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/getResource/{resourceId}", method = RequestMethod.POST)
	public ResponseEntity<String> getAllocations(@PathVariable("resourceId") Integer resourceId) throws Exception {

		Resource resource = resourceService.find(resourceId);
		return new ResponseEntity<String>(resource.getFirstName() + " " + resource.getLastName(), HttpStatus.OK);
	}

	@RequestMapping(value = "/getEligibleResourcesForCopy/{employeeId}/{projectId}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> getEligibleResourcesForCopy(@PathVariable("employeeId") Integer employeeId, @PathVariable("projectId") Integer projectId,
			@RequestParam(value = "alocationStartDate") Date alocationStartDate) throws Exception {

		List<Resource> eligibleResources = resourceService.getEligibleResourcesForCopy(employeeId, projectId, alocationStartDate);
		Gson gson = new Gson();
		String jsonEligibleResources = gson.toJson(eligibleResources);
		return new ResponseEntity<String>(jsonEligibleResources, HttpStatus.OK);
	}
	
	/*public static void main(String[] args) throws Exception {
		
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("classpath*:spring-configuration/applicationContext-root-Test.xml");
		
		RequestRequisitionService key=context.getBean(RequestRequisitionService.class);
		key.createTransection();
	}*/

	@RequestMapping(value="/activeUserList", method = RequestMethod.GET)
	public ResponseEntity<String> getActiveUserList(@RequestParam(value="userInput", required=false) String userInput)  {
		logger.info("--------getActiveUserList method starts--------");
		System.out.println("-userInput - " + userInput + "-----------");
		
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		JSONObject resultJSON = new JSONObject();
		
		List<Resource> resourceList = genericSearch.getObjectsWithSearchAndPaginationForResource(userInput);
		resultJSON.put("activeUserList", Resource.toJsonString(resourceList));
		
        logger.info("--------getActiveUserList method ends--------");
        return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
		
	}
	
}
