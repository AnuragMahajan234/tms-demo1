/**
 * 
 */
package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTimeComparator;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.DefaultProject;
import org.yash.rms.domain.EngagementModel;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Ownership;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.TimehrsView;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.dto.TimehrsViewsDTO;
import org.yash.rms.helper.EmailHelper;
import org.yash.rms.helper.ResourceAllocationHelper;
import org.yash.rms.helper.UserActivityHelper;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.json.mapper.ResourceAllocationMapper;
import org.yash.rms.service.DefaultProjectService;
import org.yash.rms.service.EngagementModelService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ProjectAllocationService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.service.TimeHoursService;
import org.yash.rms.service.UserActivityService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DozerMapperUtility;
import org.yash.rms.util.ResourceAllocationSearchCriteria;
import org.yash.rms.util.UserUtil;

/**
 * @author bhakti.barve
 * 
 */

@Controller
@RequestMapping("/resourceallocations")
public class ResourceAllocationController {

	@Autowired
	@Qualifier("ResourceAllocationService")
	ResourceAllocationService resourceAllocationService;

	@Autowired
	@Qualifier("ResourceService")
	ResourceService resourceService;

	@Autowired
	@Qualifier("TimeHoursService")
	TimeHoursService timehrsService;

	@Autowired
	@Qualifier("allocatioTypeService")
	RmsCRUDService<AllocationType> allocationTypeService;

	@Autowired
	@Qualifier("OwnershipService")
	RmsCRUDService<Ownership> ownershipService;

	@Autowired
	DozerMapperUtility dozerMapperUtility;

	@Autowired
	@Qualifier("ProjectAllocationService")
	ProjectAllocationService projectAllocationService;

	@Autowired
	@Qualifier("ProjectService")
	ProjectService projectService;

	@Autowired
	private EmailHelper emailHelper;

	@SuppressWarnings("rawtypes")
	@Autowired
	JsonObjectMapper jsonObjectMapper;
	@Autowired
	private UserActivityHelper userActivityHelper;

	@Autowired
	@Qualifier("UserActivityService")
	UserActivityService userActivityService;

	@Autowired
	@Qualifier("ResourceAllocationMapper")
	ResourceAllocationMapper resourceAllocationMapper;

	@Autowired
	@Qualifier("DefaultProjectService")
	DefaultProjectService defaultProjectService;
	
	@Autowired
	EngagementModelService engagementModelService;

	private static final Logger logger = LoggerFactory.getLogger(ResourceAllocationController.class);

	boolean allCopied = true;

	ResponseEntity<String> responseEntity;

	@Autowired
	private UserUtil userUtil;

	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;

	@Autowired
	JsonObjectMapper<OrgHierarchy> orgHierarchyjsonMapper;

	@RequestMapping(method = RequestMethod.GET)
	public String load(Model uiModel, @RequestParam(value = "empDateOfJoining", required = false) String empDateOfJoining, @RequestParam(value = "empId", required = false) Integer empId,
			@RequestParam(value = "yashEmpId", required = false) Integer yashEmpId, @RequestParam(value = "empName", required = false) String empName) throws Exception {
		Resource resource = userUtil.getLoggedInResource();
		boolean isCurrentUserBusinessGroupAdmin = UserUtil.isCurrentUserIsBusinessGroupAdmin();
		boolean isCurrentUserIsHr =  UserUtil.isCurrentUserIsHr();
		uiModel.addAttribute(Constants.ALLOCATIONTYPE, allocationTypeService.findAll());
		List<Project> projects = new ArrayList<Project>();
		List<Project> projectList = new ArrayList<Project>();
		List<OrgHierarchy> buList = new ArrayList<OrgHierarchy>();
		buList = UserUtil.getCurrentResource().getAccessRight();
		if (isCurrentUserBusinessGroupAdmin||isCurrentUserIsHr) {
			// projects = projectService.findProjectsForBGAdmin(buList);
			projectList = projectService.findActiveProjectsForBGAdmin(buList);
		} else
			// Commented by Nitin
			// projects = projectService.findAll();
			// Added by Nitin
			projectList = projectService.findAllActiveProjects();
		// Added for task # 350 - Start

		// Commented by Nitin
		// List<Project> projectList = new ArrayList<Project>();
		/*
		 * for (Iterator iterator = projects.iterator(); iterator.hasNext();) {
		 * Project project = (Project) iterator.next(); if (project != null) {
		 * if (project.getProjectEndDate() == null ||
		 * !project.getProjectEndDate().before(new Date())) {
		 * projectList.add(project); } } }
		 */
		// Added for task # 350 - End
		Collections.sort(projects, new Project.ProjectNameComparator());
		// uiModel.addAttribute("allCopied", allCopied);
		// uiModel.addAttribute(Constants.OWNERSHIPS,
		// ownershipService.findAll());
		// uiModel.addAttribute("listActiveOrAll", activeOrAll);
		uiModel.addAttribute(Constants.PROJECTS, projectList);
		// uiModel.addAttribute(Constants.RATES, billingScaleService.findAll());
		// uiModel.addAttribute(Constants.TEAMS, teamService.findAll());
		/*
		 * List eligibleResources = ResourceAllocationHelper
		 * .getEligibleResourcesForProject(resourceAllocationService,
		 * resourceService);
		 */
		// Commented by Nitin
		// uiModel.addAttribute(Constants.ELIGIBLE_RESOURCES,
		// eligibleResources);

		// Set Header values...

		try {
			if (resource.getUploadImage() != null &&resource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			} else {
				uiModel.addAttribute("UserImage", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		uiModel.addAttribute("firstName",resource.getFirstName() + " " +resource.getLastName());
		uiModel.addAttribute("designationName",resource.getDesignationId().getDesignationName());
		Calendar cal = Calendar.getInstance();
		cal.setTime(resource.getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		uiModel.addAttribute("DOJ", months + "-" + year);
		uiModel.addAttribute("ROLE",resource.getUserRole());
		// End

		if (empId != null) {
			uiModel.addAttribute("empId", empId);
			uiModel.addAttribute("yashEmpId", yashEmpId);
			uiModel.addAttribute("empDateOfJoining", empDateOfJoining);
			uiModel.addAttribute("empName", empName);
			uiModel.addAttribute("navigationFlag", true);
		} else {
			uiModel.addAttribute("navigationFlag", false);
		}

		return "resourceallocations/list";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list/{ActiveOrAll}", method = RequestMethod.GET)
	public ResponseEntity<String> list(Model uiModel, @PathVariable("ActiveOrAll") String activeOrAll, HttpServletRequest request) throws Exception {
		logger.info("------ResourceAllocationController list method start------");
		JSONObject resultJSON = new JSONObject();
		HttpHeaders headers = new HttpHeaders();
		long totalCount = 0;
		headers.add("Content-Type", "application/json; charset=utf-8");
		try {
			List<OrgHierarchy> buList = null;
			boolean isCurrentUserBusinessGroupAdmin = UserUtil.isCurrentUserIsBusinessGroupAdmin();

			Integer currentLoggedInUserId = UserUtil.getCurrentResource().getEmployeeId();
			boolean isCurrentUserAdmin = UserUtil.isCurrentUserIsAdmin();
			boolean isCurrentUserHr = UserUtil.isCurrentUserIsHr();
			boolean isCurrentUserDelManager = UserUtil.isCurrentUserIsDeliveryManager();
			// added
			Integer page = Integer.parseInt(request.getParameter("iDisplayStart"));
			Integer size = Integer.parseInt(request.getParameter("iDisplayLength"));

			// get column position
			String iSortCol = request.getParameter("iSortCol_0");
			
			// for sorting direction
			String sSortDir = request.getParameter("sSortDir_0");
			String columnName = "";
			boolean search = false;
			Integer iColumns = Integer.parseInt(request.getParameter("iColumns"));
			String sSearch = request.getParameter("sSearch");
			ResourceAllocationSearchCriteria resourceAllocSearchCriteria = new ResourceAllocationSearchCriteria();
			String[] searchColumns = resourceAllocSearchCriteria.getResAllocCols();

			boolean activeOrAllStatus;

			if (activeOrAll.equals("active")) {
				activeOrAllStatus = true;
			} else
				activeOrAllStatus = false;

			if ((iSortCol != null || iSortCol != "") && (sSortDir != null || sSortDir != "")) {
				resourceAllocSearchCriteria.setOrderStyle(sSortDir);
				resourceAllocSearchCriteria.setOrederBy(resourceAllocSearchCriteria.getResAllocCols()[Integer.parseInt(iSortCol)]);

				resourceAllocSearchCriteria.setSortTableName(resourceAllocSearchCriteria.getColandTableNameMap().get(resourceAllocSearchCriteria.getResAllocCols()[Integer.parseInt(iSortCol)]));

				resourceAllocSearchCriteria.setRefTableName(resourceAllocSearchCriteria.getColandTableNameMap().get(resourceAllocSearchCriteria.getResAllocCols()[Integer.parseInt(iSortCol)]));
					
				resourceAllocSearchCriteria.setRefColName(resourceAllocSearchCriteria.getResAllocCols()[Integer.parseInt(iSortCol)]);
			}

			for (int i = 0; i < iColumns; i++) {
				sSearch = request.getParameter("sSearch_" + i);
				// execute if there is any value in any of the search box
				if (sSearch.length() > 0) {
					search = true;
					columnName = searchColumns[i];
					resourceAllocSearchCriteria.setRefTableName(resourceAllocSearchCriteria.getColandTableNameMap().get(columnName));
					resourceAllocSearchCriteria.setRefColName(columnName);
					resourceAllocSearchCriteria.setSearchValue(sSearch);
					break;
				}
			}
			List<Integer> addedResourceList = null;
			if (currentLoggedInUserId != null) {
				List<TimehrsViewsDTO> allResources = new ArrayList<TimehrsViewsDTO>();
				List<TimehrsView> adminList = null;
				if (isCurrentUserAdmin) {
					adminList = timehrsService.resourceAllocationPagination(page, size, resourceAllocSearchCriteria, activeOrAll, search);
					if (search) {
						totalCount = timehrsService.countSearch(resourceAllocSearchCriteria, activeOrAll, null, null,null, false, true);
					} else {
						if (activeOrAll.equals("all")) {

							totalCount = resourceService.countTotal();

						}
						if (activeOrAll.equals("active")) {
							totalCount = resourceService.countActive();

						}
					}
				}

				else {

					if (isCurrentUserBusinessGroupAdmin || isCurrentUserHr) {
						buList = new ArrayList<OrgHierarchy>();
						buList = UserUtil.getCurrentResource().getAccessRight();
						addedResourceList = new ArrayList<Integer>();
						List<Integer> projectIdList = projectAllocationService.getProjectIdsForBGAdmin(buList);
						if (search) {
							addedResourceList = resourceService.findReourceIdsByBusinessGroupPagination(resourceAllocSearchCriteria, projectIdList, buList, page, size, activeOrAllStatus, true,false);
							totalCount = timehrsService.countSearch(resourceAllocSearchCriteria, activeOrAll, buList, projectIdList ,userUtil.getCurrentLoggedInUseId(), false, true);
							
						} else 

						{
							addedResourceList = resourceService.findReourceIdsByBusinessGroupPagination(resourceAllocSearchCriteria, projectIdList, buList, page, size, activeOrAllStatus, false,false);
							totalCount = resourceService.findActiveReourceIdsByBusinessGroup(buList, projectIdList,activeOrAll,false).size();
						}
					}
					
					else if (isCurrentUserDelManager) {
						addedResourceList = new ArrayList<Integer>();

						/*
						 * if (search) { addedResourceList = resourceService
						 * .findReourceIdsByRM1RM2Pagination(
						 * resourceAllocSearchCriteria,
						 * userUtil.getCurrentLoggedInUseId(), page, size,
						 * activeOrAllStatus, true); totalCount =
						 * timehrsService.countSearch(
						 * resourceAllocSearchCriteria, activeOrAll, null,
						 * userUtil.getCurrentLoggedInUseId(), false, true); }
						 * else
						 * 
						 * { addedResourceList = resourceService
						 * .findReourceIdsByRM1RM2Pagination(
						 * resourceAllocSearchCriteria,
						 * userUtil.getCurrentLoggedInUseId(), page, size,
						 * activeOrAllStatus, false); totalCount =
						 * timehrsService.countSearch(
						 * resourceAllocSearchCriteria, activeOrAll, null,
						 * userUtil.getCurrentLoggedInUseId(), false, false); }
						 */
						List<Integer> projectIds = projectAllocationService.getProjectIdsForManager(currentLoggedInUserId, activeOrAll);
						if (search) {
							addedResourceList = resourceAllocationService.findAllocatedResourceEmployeeIdByProjectIdsPagination(page, size, projectIds, activeOrAll, resourceAllocSearchCriteria, true,
									userUtil.getCurrentLoggedInUseId());
							totalCount = resourceAllocationService.countAllocatedResourceEmployeeIdByProjectIdsPagination(projectIds, activeOrAll, resourceAllocSearchCriteria, true,
									userUtil.getCurrentLoggedInUseId());
						} else {
							addedResourceList = resourceAllocationService.findAllocatedResourceEmployeeIdByProjectIdsPagination(page, size, projectIds, activeOrAll, resourceAllocSearchCriteria,
									false, userUtil.getCurrentLoggedInUseId());
							totalCount = resourceAllocationService.countAllocatedResourceEmployeeIdByProjectIdsPagination(projectIds, activeOrAll, resourceAllocSearchCriteria, false,
									userUtil.getCurrentLoggedInUseId());
						}

					}
					
					// Added for Including those resources which are under
					// the
					// projects for which he is Offshore Manager
					if (!isCurrentUserDelManager && !isCurrentUserBusinessGroupAdmin  && !isCurrentUserHr) {

						List<Integer> projectIds = projectAllocationService.getProjectIdsForManager(currentLoggedInUserId, activeOrAll);

						/*
						 * List resourceIds = resourceAllocationService
						 * .findAllocatedResourceEmployeeIdByProjectIds
						 * (projectIds,activeOrAll);
						 */

						if (search) {
							addedResourceList = resourceAllocationService.findAllocatedResourceEmployeeIdByProjectIdsPagination(page, size, projectIds, activeOrAll, resourceAllocSearchCriteria, true,
									userUtil.getCurrentLoggedInUseId());
							totalCount = resourceAllocationService.countAllocatedResourceEmployeeIdByProjectIdsPagination(projectIds, activeOrAll, resourceAllocSearchCriteria, true,
									userUtil.getCurrentLoggedInUseId());
						} else {
							addedResourceList = resourceAllocationService.findAllocatedResourceEmployeeIdByProjectIdsPagination(page, size, projectIds, activeOrAll, resourceAllocSearchCriteria,
									false, userUtil.getCurrentLoggedInUseId());
							totalCount = resourceAllocationService.countAllocatedResourceEmployeeIdByProjectIdsPagination(projectIds, activeOrAll, resourceAllocSearchCriteria, false,
									userUtil.getCurrentLoggedInUseId());
						}
					}

					adminList = timehrsService.managerViewPagination(addedResourceList, page, size, resourceAllocSearchCriteria, search);

				}

				// common code for admin and manager
				allResources = dozerMapperUtility.convertTimehrsViewDomainToDTOList(adminList);

				resultJSON.put("aaData", TimehrsView.toJsonArrayResAlloc(allResources));

				resultJSON.put("iTotalRecords", totalCount);
				resultJSON.put("iTotalDisplayRecords", totalCount);

				// uiModel.addAttribute(Constants.RESOURCES, allResources);
				// end of common code for admin and manager

			}

		} catch (RuntimeException e) {
			logger.error("RuntimeException occured in list method of Resource allocation controller:" + e);
			throw e;
		} catch (Exception e) {
			logger.error("RuntimeException occured in list method of Resource allocation controller:" + e);
			throw e;
		}

		logger.info("------ResourceAllocationController list method end------");
		// return "resourceallocations/list";
		return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);

	}

	@RequestMapping(params = "find=ByResourceEmployeeId", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> findResourceAllocationsByResourceEmployeeId(@RequestParam("employeeId") Integer employeeId) throws Exception {
		logger.info("------ResourceAllocationController FindResourceAllocationsByResourceEmployeeId method start------");
		HttpHeaders headers;
		List<org.yash.rms.domain.ResourceAllocation> resAlloc = null;
		headers = new HttpHeaders();
		try {
			headers.add("Content-Type", "application/json; charset=utf-8");
			Calendar lastActivitycalender = Calendar.getInstance();
			Calendar firstActivitycalender = Calendar.getInstance();
			Resource resource = new Resource();
			resource.setEmployeeId(employeeId);
			resAlloc = resourceAllocationService.findResourceAllocationsByEmployeeId(resource);
			if (resAlloc.size() != 0) {
				for (org.yash.rms.domain.ResourceAllocation alloc : resAlloc) {
					List<Object[]> lastUserActivity = resourceAllocationService.findLastUseractivity(alloc.getId());
					/*
					 * List<Object[]> lastUserActivity = new
					 * ArrayList<Object[]>();//
					 * resourceAllocationService.findLastUseractivity
					 * (alloc.getId());
					 * 
					 * Object[] objects = new Object[8]; objects[7] = new
					 * Date(); lastUserActivity.add(0, objects);
					 */

					List<Object[]> firstUserActivity = resourceAllocationService.findFirstUseractivity(alloc.getId());

					/*
					 * List<Object[]> firstUserActivity = new
					 * ArrayList<Object[]>();/
					 * /resourceAllocationService.findFirstUseractivity
					 * (alloc.getId()); objects = new Object[8]; objects[7] =
					 * new Date(); firstUserActivity.add(0, objects);
					 */

					if (lastUserActivity != null && lastUserActivity.size() != 0) {
						// int j = 6;
						// change value of j from 6 to 5 because calendar date
						// should be disabled from LastUserActivityDate
						int j = 5;

						Object[] useractivity = lastUserActivity.get(0);
						Date weekEndDate = (Date) useractivity[8];
						if (weekEndDate != null) {
							lastActivitycalender.setTime(weekEndDate);
							if ((Double) useractivity[6] != null) {
								alloc.setLastUserActivityDate(weekEndDate);
							} else {
								for (int i = 5; i >= 0;) {
									if ((Double) useractivity[i] != null) {
										lastActivitycalender.add(Calendar.DAY_OF_WEEK, i - j);
										alloc.setLastUserActivityDate(lastActivitycalender.getTime());
										break;
									} else {
										i--;
									}
								}
							}
						}
					}
					if (firstUserActivity != null && firstUserActivity.size() != 0) {
						int j = 0;
						Object[] useractivity = firstUserActivity.get(0);
						Date weekStartDate = (Date) useractivity[7];
						if (weekStartDate != null) {
							firstActivitycalender.setTime(weekStartDate);
							if ((Double) useractivity[0] != null) {
								alloc.setFirstUserActivityDate(weekStartDate);
							} else {
								for (int i = 1; i < 7;) {
									if ((Double) useractivity[i] != null) {
										firstActivitycalender.add(Calendar.DAY_OF_WEEK, j + i);
										alloc.setFirstUserActivityDate(firstActivitycalender.getTime());
										break;
									} else {
										i++;
									}
								}
							}
						}
					}
				}
			}
		} catch (RuntimeException e) {
			logger.error("RuntimeException occured in FindResourceAllocationsByResourceEmployeeId method of Resource allocation controller:" + e);
			throw e;
		} catch (Exception exception) {
			logger.error("RuntimeException occured in FindResourceAllocationsByResourceEmployeeId method of Resource allocation controller:" + exception);
			throw exception;
		}

		logger.info("------ResourceAllocationController FindResourceAllocationsByResourceEmployeeId method end------");
		return new ResponseEntity<String>(resourceAllocationMapper.toJsonArray(resAlloc), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) throws Exception {
		logger.info("------ResourceAllocationController createFromJson method start------");

		boolean mailFlag = false;
		String employeeName = null;
		String yashEmpId = null;
		JSONObject jsonObject = new JSONObject(json);
		if (jsonObject.has("mailFlag")) {
			mailFlag = jsonObject.getBoolean("mailFlag");
		}
		if (jsonObject.has("employeeName")) {
			employeeName = jsonObject.getString("employeeName");
		}
		if (jsonObject.has("yashEmpId")) {
			yashEmpId = jsonObject.getString("yashEmpId");
		}

		org.yash.rms.domain.ResourceAllocation resourceAllocation = (org.yash.rms.domain.ResourceAllocation) resourceAllocationMapper.fromJsonToObject(json,org.yash.rms.domain.ResourceAllocation.class);

		if (!(resourceAllocation.getProjectEndRemarks().equals("NA"))) {
			Format formatter = new SimpleDateFormat(Constants.DATE_PATTERN_4);
			String currentDate = formatter.format(new Date());
			if (!(resourceAllocation.getProjectEndRemarks().startsWith(Constants.FEEDBACK_PREFIX)))
				resourceAllocation.setProjectEndRemarks(Constants.FEEDBACK_PREFIX + userUtil.getLoggedInResource().getUsername() + " :" + Constants.FEEDBACK_DATE + ": " + currentDate + " </b><br/>"
						+ resourceAllocation.getProjectEndRemarks());
		}
		
		HttpHeaders headers = new HttpHeaders();
		boolean flag = true;

		if (null == resourceAllocation.getAllocationTypeId()) {
			return new ResponseEntity<String>("{ \"error\" : \"" + "Please select the Allocation Type" + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		/** Added the below condition to fix defect #173 */
		if (null == resourceAllocation.getProjectId()) {
			return new ResponseEntity<String>("{ \"error\" : \"" + "Please select the Project Name" + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// Get all allocations for the resource for which new allocation is
		// being created
		try {
			List<org.yash.rms.domain.ResourceAllocation> allResourceAllocations = resourceAllocationService.findResourceAllocationsByEmployeeId(resourceAllocation.getEmployeeId());
			List<org.yash.rms.domain.ResourceAllocation> activeResourceAllocations = resourceAllocationService.findResourceAllocationByEmployeeId(resourceAllocation.getEmployeeId().getEmployeeId());
			/** Start - Modified the code to fix defect #133 */
			/*
			 * Check if any existing project allocation for employee overlaps
			 * with this new allocation of same project
			 */
			for (org.yash.rms.domain.ResourceAllocation allocation : allResourceAllocations) {
				Date newstartdate = resourceAllocation.getAllocStartDate();
				Date newEndDate = resourceAllocation.getAllocEndDate();
				if ((!(allocation.getId().equals(resourceAllocation.getId())) && allocation.getProjectId().getId().equals(resourceAllocation.getProjectId().getId()))
						&& allocation.isOverlaps(newstartdate, newEndDate)) {
					flag = false;
					break;
				}
			}
			for (org.yash.rms.domain.ResourceAllocation allocation : allResourceAllocations) {
				if (!(allocation.getId().equals(resourceAllocation.getId()))) {

					if (allocation.getCurProj() != null && allocation.getCurProj() == true && resourceAllocation.getCurProj() != null && resourceAllocation.getCurProj() == true) {
						allocation.setCurProj(false);
						resourceAllocationService.saveOrupdate(allocation);
						/*
						 * projectName.append(allocation.getProjectId().
						 * getProjectName()); count++;
						 */
					}
				}
			}
			//String message="";
			boolean save=true;
			if (flag) {
				if (resourceAllocation.getId() != null) {

					ResourceAllocation resAlloc = resourceAllocationService.findResourceAllocation(resourceAllocation.getId());
					if (resAlloc.getDesignation() != null) {
						resourceAllocation.setDesignation(resAlloc.getDesignation());
					}
					if (resAlloc.getGrade() != null) {
						resourceAllocation.setGrade(resAlloc.getGrade());

					}

					if (resAlloc.getLocation() != null) {
						resourceAllocation.setLocation(resAlloc.getLocation());

					}
					if (resAlloc.getBuId() != null) {
						resourceAllocation.setBuId(resAlloc.getBuId());
					}
					if (resAlloc.getCurrentBuId() != null) {
						resourceAllocation.setCurrentBuId(resAlloc.getCurrentBuId());
					}
					if (resAlloc.getOwnershipTransferDate() != null) {
						resourceAllocation.setOwnershipTransferDate(resAlloc.getOwnershipTransferDate());
					}
					if (resAlloc.getCurrentReportingManager() != null) {
						resourceAllocation.setCurrentReportingManager(resAlloc.getCurrentReportingManager());
					}
					if (resAlloc.getCurrentReportingManagerTwo() != null) {
						resourceAllocation.setCurrentReportingManagerTwo(resAlloc.getCurrentReportingManagerTwo());
					}
					resourceAllocation.setUpdatedBy(UserUtil.userContextDetailsToResource(UserUtil.getCurrentResource()));

					resourceAllocation.setLastupdatedTimestamp(new Date());
					/*
					 * if(resourceAllocation.getTeam().getId() == -1){
					 * resourceAllocation.setTeam(null); }
					 */
					//changes to prevent end of allocation on current date if the project is primary--start-- Madhuri
					if(resourceAllocation.getAllocEndDate()!=null){
						
						DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();
						int date = 	dateTimeComparator.compare(resourceAllocation.getAllocEndDate(), new Date());
						System.out.println("date"+date);
						if(date<0)
						{
						resourceAllocation.setCurProj(false);
						}
				//	int date = resourceAllocation.getAllocEndDate().compareTo(new Date());
//					if (date == -1) {
//						if (resourceAllocation.getCurProj() == true) {
//							save = false;
//							//message = "Please check End date to set Primary Project value";
//						}
//					}
					}
					if(save){
					resourceAllocationService.saveOrupdate(resourceAllocation);
					}
					//end
					
				} 
				else {
					// Start: New enhancement of ending defaultproject as soon
					// as new project is assigned:Pratyoosh//

					Resource resource = resourceService.find(resourceAllocation.getEmployeeId().getEmployeeId());
					if (null != activeResourceAllocations && activeResourceAllocations.size() > 0) {
						ResourceAllocation allocation = activeResourceAllocations.get(0);
						// DefaultProject defaultProjects =
						// defaultProjectService.getDefaultProjectbyProjectId(allocation.getProjectId());
						DefaultProject defaultProjects = defaultProjectService.getDefaultProjectbyProjectForBU(resource.getCurrentBuId());
						if (null != defaultProjects && (defaultProjects.getProjectId() == allocation.getProjectId())) {

							if ((resourceAllocation.getAllocStartDate().equals(allocation.getAllocStartDate())) || (resourceAllocation.getAllocStartDate().before(allocation.getAllocStartDate()))) {
								allocation.setAllocEndDate(allocation.getAllocStartDate());
							} else {
								Calendar c = Calendar.getInstance();
								c.setTime(resourceAllocation.getAllocStartDate());
								c.add(Calendar.DATE, -1);
								Date allocEndDate = c.getTime();
								allocation.setAllocEndDate(allocEndDate);
							}
							resourceAllocationService.saveOrupdate(allocation);
						}
					}
					// End: New enhancement of ending defaultproject as soon as
					// new project is assigned:Pratyoosh//

					resourceAllocation.setAllocatedBy(UserUtil.userContextDetailsToResource(UserUtil.getCurrentResource()));
					resourceAllocation.setUpdatedBy(UserUtil.userContextDetailsToResource(UserUtil.getCurrentResource()));

					resourceAllocation.setDesignation(resource.getDesignationId());
					resourceAllocation.setGrade(resource.getGradeId());
					resourceAllocation.setBuId(resource.getBuId());
					resourceAllocation.setCurrentBuId(resource.getCurrentBuId());
					resourceAllocation.setLocation(resource.getLocationId());
					resourceAllocation.setCurrentReportingManager(resource.getCurrentReportingManager());
					resourceAllocation.setCurrentReportingManagerTwo(resource.getCurrentReportingManagerTwo());
					resourceAllocation.setOwnershipTransferDate(resource.getTransferDate());
					/*
					 * if(resourceAllocation.getTeam().getId() == -1){
					 * resourceAllocation.setTeam(null); }
					 */
					resourceAllocationService.saveOrupdate(resourceAllocation);
					
					// this call ends all open allocation of all those projects which Engagement model is pool.
					EngagementModel engagementModel = engagementModelService.findEngagementModelByProjectId(resourceAllocation.getProjectId().getId());
					if(engagementModel == null || !engagementModel.getEngagementModelName().contains("Pool")) {
						resourceAllocationService.closeResourceAllocationInPoolProject(resourceAllocation.getId(), resourceAllocation.getAllocStartDate(), resource.getEmployeeId(), true);
					}
				}
				if(save)
				{
					updateAllocationSeqForResourceAllocations(resourceAllocation.getEmployeeId());
					headers.add("Content-Type", "application/json");
					logger.info("------ResourceAllocationController createFromJson method end------");
					responseEntity = new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.CREATED);
				}
				//changes to prevent end of allocation on current date if the project is primary--- start
				else
				{
					headers.add("Content-Type", "application/json");
					logger.info("------ResourceAllocationController createFromJson method end------");
					responseEntity = new ResponseEntity<String>("{ \"error\" : \"" +  "Allocation of Primary Project cannot end on Current date or Previous date" + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);	
				}
				//end

			} else {
				headers.add("Content-Type", "application/json");
				logger.info("------ResourceAllocationController createFromJson method end------");
				responseEntity = new ResponseEntity<String>("{ \"error\" : \"" + "Same Project can't be allocated in same time frame" + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			if (mailFlag && resourceAllocation.getAllocEndDate() != null) {
				Map<String, Object> model = new HashMap<String, Object>();
				Set<String[]> emailIds = new HashSet<String[]>();
				String[] emailTo = { resourceAllocation.getProjectId().getOffshoreDelMgr().getEmailId() };
				// String [] emailTo = {"deepti.gupta@yash.com"};
				emailIds.add(emailTo);
				resourceAllocation.getEmployeeId().setYashEmpId(yashEmpId);
				userActivityHelper.setEmailContentForClosingAllocation(model, resourceAllocation.getEmployeeId(), resourceAllocation.getAllocEndDate(), resourceAllocation.getProjectId()
						.getProjectName(), employeeName);

				try {
					emailHelper.sendEmailNotification(model, emailIds, null);
				} catch (Exception ex) {
					logger.error("Could not Send Mail to " + userUtil.getUserContextDetails().getUsername() + "due to :: " + ex.getMessage());
				}
			}
		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in createFromJson method of Resource allocation controller:" + exception);
			throw exception;
		} catch (Exception e) {
			logger.error("Exception occured in createFromJson method of Resource allocation controller:" + e);
			throw e;
		}

		return responseEntity;
	}

	public void updateAllocationSeqForResourceAllocations(Resource employeeId) throws Exception {
		logger.info("------ResourceAllocationController updateAllocationSeqForResourceAllocations method start------");
		try {
			List<org.yash.rms.domain.ResourceAllocation> resourceAllocation = resourceAllocationService.findResourceAllocationsByEmployeeId(employeeId);
			if (resourceAllocation != null && !resourceAllocation.isEmpty()) {
				Collections.sort(resourceAllocation, new org.yash.rms.domain.ResourceAllocation.ResourceAllocationTimeComparator());
				for (int i = 1; i <= resourceAllocation.size(); i++) {
					org.yash.rms.domain.ResourceAllocation raObj = resourceAllocation.get(i - 1);
					raObj.setAllocationSeq(i);
					resourceAllocationService.saveOrupdate(raObj);

				}

			}
		} catch (RuntimeException e) {
			logger.error("RuntimeException occured in updateAllocationSeqForResourceAllocations method of Resource allocation controller:" + e);
			throw e;
		} catch (Exception exception) {
			logger.error("RuntimeException occured in updateAllocationSeqForResourceAllocations method of Resource allocation controller:" + exception);
			throw exception;
		}
		logger.info("------ResourceAllocationController updateAllocationSeqForResourceAllocations method end------");
	}

	@RequestMapping(value = "/approveDetails/{id}/{empId}/{projId}", method = RequestMethod.POST, produces = "text/html")
	public ResponseEntity<String> approveDetails(@PathVariable("id") Integer id, @PathVariable("empId") Integer empId, @PathVariable("projId") Integer projId, HttpServletResponse response)
			throws Exception {
		logger.info("------ResourceAllocationController approveDetails method start------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Boolean check = true;
		Integer count = 0;
		Resource resource = new Resource();
		Project project = new Project();
		try {
			project.setId(projId);
			resource.setEmployeeId(empId);

			/*
			 * List<org.yash.rms.domain.ResourceAllocation> resourceAllocation =
			 * resourceAllocationService
			 * .findResourceAllocationsByEmployeeIdAndProjectId(resource,
			 * project);
			 */

			ResourceAllocation resourceAllocation = resourceAllocationService.findById(id);
			List<org.yash.rms.domain.UserActivity> userActivity = userActivityService.findUserActivitysByResourceAllocId(resourceAllocation);

			/*
			 * List<org.yash.rms.domain.UserActivity>
			 * userActivity=userActivityService
			 * .findUserActivitysByResourceAllocId(resourceAllocation);
			 */
			if (userActivity != null && userActivity.size() > 0) {
				for (org.yash.rms.domain.UserActivity userActivity2 : userActivity) {
					if (userActivity2.getApproveStatus() == 1) {
						count++;
					}
				}
				if (userActivity.size() == count) {
					check = true;
					logger.info("------ResourceAllocationController approveDetails method end------");
					responseEntity = new ResponseEntity<String>("{ \"check\":" + check + "}", headers, HttpStatus.OK);
				} else {

					check = false;
					logger.info("------ResourceAllocationController approveDetails method end------");
					responseEntity = new ResponseEntity<String>("{ \"check\":" + check + "}", headers, HttpStatus.OK);
				}
				// UserActivity activity=userActivity.get(0);
			} else {
				check = true;
				logger.info("------ResourceAllocationController approveDetails method end------");
				responseEntity = new ResponseEntity<String>("{ \"check\":" + check + "}", headers, HttpStatus.OK);

			}
		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in approveDetails method of Resource allocation controller:" + exception);
			throw exception;
		} catch (Exception e) {
			logger.error("RuntimeException occured in approveDetails method of Resource allocation controller:" + e);
			throw e;
		}

		return responseEntity;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, headers = "Accept=text/html")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) throws Exception {
		logger.info("------ResourceAllocationController deleteFromJson method start------");
		HttpHeaders headers = new HttpHeaders();
		try {
			org.yash.rms.domain.ResourceAllocation resourceAllocation = resourceAllocationService.findResourceAllocation(id);
			Resource employeeId = resourceAllocation.getEmployeeId();

			headers.add("Content-Type", "application/json");

			if (resourceAllocation == null) {
				return new ResponseEntity<String>("{ \"status\":\"FALSE\"}", headers, HttpStatus.NOT_FOUND);
			}
			resourceAllocationService.delete(id);
			updateAllocationSeqForResourceAllocations(employeeId);
		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in deleteFromJson method of Resource allocation controller:" + exception);
			throw exception;
		} catch (Exception e) {
			logger.error("RuntimeException occured in deleteFromJson method of Resource allocation controller:" + e);
			throw e;
		}
		logger.info("------ResourceAllocationController deleteFromJson method end------");
		return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
	}

	/**
	 * Method for copying allocation of project to a resource to other resources
	 * 
	 * @param id
	 * @param ids
	 * @return String
	 * @author bhakti.barve
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/{selectedResourceIds}", method = RequestMethod.POST, produces = "text/html", headers = "Accept=application/json")
	public String copyFromJson(@PathVariable("id") Integer id, @PathVariable("selectedResourceIds") String ids) throws Exception {
		logger.info("------ResourceAllocationController copyFromJson method start------");
		try {
			org.yash.rms.domain.ResourceAllocation resourceAllocation = resourceAllocationService.findResourceAllocation(id);
			ResourceAllocationHelper resourceAllocationHelper = new ResourceAllocationHelper();
			allCopied = resourceAllocationHelper.copyResourceAllocations(resourceAllocation, ids, resourceAllocationService, resourceService);
		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in copyFromJson method of Resource allocation controller:" + exception);
			throw exception;
		} catch (Exception e) {
			logger.error("RuntimeException occured in copyFromJson method of Resource allocation controller:" + e);
			throw e;
		}
		logger.info("------ResourceAllocationController copyFromJson method end------");
		return "redirect:/resourceallocations";

	}

	@RequestMapping(params = "find=ByActiveTypeResourceEmployeeId", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> findResourcesByActiveType(@RequestParam("employeeId") Integer employeeId) throws Exception {
		logger.info("------ResourceAllocationController findResourcesByActiveType method start------");
		HttpHeaders headers;
		List<org.yash.rms.domain.ResourceAllocation> resAlloc = null;
		headers = new HttpHeaders();
		try {
			headers.add("Content-Type", "application/json; charset=utf-8");
			Calendar lastActivitycalender = Calendar.getInstance();
			Calendar firstActivitycalender = Calendar.getInstance();
			Resource resource = new Resource();

			resource.setEmployeeId(employeeId);
			resAlloc = resourceAllocationService.findResourceAllocationByActiveTypeEmployeeId(resource);

			if (resAlloc.size() != 0) {
				for (org.yash.rms.domain.ResourceAllocation alloc : resAlloc) {
					List<Object[]> lastUserActivity = resourceAllocationService.findLastUseractivity(alloc.getId());
					/*
					 * List<Object[]> lastUserActivity = new
					 * ArrayList<Object[]>();//
					 * resourceAllocationService.findLastUseractivity
					 * (alloc.getId());
					 * 
					 * Object[] objects = new Object[8]; objects[7] = new
					 * Date(); lastUserActivity.add(0, objects);
					 */

					List<Object[]> firstUserActivity = resourceAllocationService.findFirstUseractivity(alloc.getId());

					/*
					 * List<Object[]> firstUserActivity = new
					 * ArrayList<Object[]>();/
					 * /resourceAllocationService.findFirstUseractivity
					 * (alloc.getId()); objects = new Object[8]; objects[7] =
					 * new Date(); firstUserActivity.add(0, objects);
					 */

					if (lastUserActivity != null && lastUserActivity.size() != 0) {
						// int j = 6;
						// change value of j from 6 to 5 because calendar date
						// should be disabled from LastUserActivityDate
						int j = 5;

						Object[] useractivity = lastUserActivity.get(0);
						Date weekEndDate = (Date) useractivity[8];
						if (weekEndDate != null) {
							lastActivitycalender.setTime(weekEndDate);
							if ((Double) useractivity[6] != null) {
								alloc.setLastUserActivityDate(weekEndDate);
							} else {
								for (int i = 5; i >= 0;) {
									if ((Double) useractivity[i] != null) {
										lastActivitycalender.add(Calendar.DAY_OF_WEEK, i - j);
										alloc.setLastUserActivityDate(lastActivitycalender.getTime());
										break;
									} else {
										i--;
									}
								}
							}
						}
					}
					if (firstUserActivity != null && firstUserActivity.size() != 0) {
						int j = 0;
						Object[] useractivity = firstUserActivity.get(0);

						Date weekStartDate = (Date) useractivity[7];
						if (weekStartDate != null) {
							firstActivitycalender.setTime(weekStartDate);
							if ((Double) useractivity[0] != null) {
								alloc.setFirstUserActivityDate(weekStartDate);
							} else {
								for (int i = 1; i < 7;) {
									if ((Double) useractivity[i] != null) {
										firstActivitycalender.add(Calendar.DAY_OF_WEEK, j + i);
										alloc.setFirstUserActivityDate(firstActivitycalender.getTime());
										break;
									} else {
										i++;
									}
								}
							}
						}
					}
				}
			}
		} catch (RuntimeException e) {
			logger.error("RuntimeException occured in findResourcesByActiveType method of Resource allocation controller:" + e);
			throw e;
		} catch (Exception exception) {
			logger.error("RuntimeException occured in findResourcesByActiveType method of Resource allocation controller:" + exception);
			throw exception;
		}

		logger.info("------ResourceAllocationController findResourcesByActiveType method end------");
		if (allCopied)
			return new ResponseEntity<String>(resourceAllocationMapper.toJsonArray(resAlloc), headers, HttpStatus.OK);
		else {
			allCopied = true;
			return new ResponseEntity<String>("{ \"error\" : \"" + "Some of the Allocations already have a Primary Project" + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// DE2997(Added by Maya):START : Function to get Resource Release Date
	@RequestMapping(params = "find=getResourceByEmployeeId", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity getResourceByEmployeeId(@RequestParam("employeeId") Integer employeeId) throws Exception {
		logger.info("------ResourceAllocationController getResourceByEmployeeId method start------");
		Resource selectedResource = null;
		Date releaseDate = null;
		String relDate = null;
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.add("Content-Type", "application/json; charset=utf-8");
			selectedResource = resourceService.find(employeeId);
			releaseDate = selectedResource.getReleaseDate();
			if (releaseDate != null)
				relDate = releaseDate.toString();
		} catch (Exception exception) {
			logger.error("Exception occured in getResourceByEmployeeId method of ResourceAllocationController controller:" + exception);
			throw exception;
		}
		JSONObject obj = new JSONObject();
		obj.put("relDate", relDate);
		logger.info("------ResourceAllocationController getResourceByEmployeeId method end------");
		return new ResponseEntity(obj.toString(), headers, HttpStatus.OK);
	}

	// DE2997(Added by Maya):END

	@RequestMapping(value = "/readonlyEmployeeDetails/{employeeid}", produces = "text/html")
	public String employeeDetails(@PathVariable("employeeid") Integer employeeId, Model uiModel) throws Exception {
		logger.info("------ResourceAllocationController employeeDetails method start------");

		try {
			Resource selectedResource = resourceService.find(employeeId);

			String employeeName = selectedResource.getFirstName() + " " + selectedResource.getLastName();

			String employeeRM1Name = selectedResource.getCurrentReportingManager().getFirstName() + " " + selectedResource.getCurrentReportingManager().getLastName();

			String employeeSRM = selectedResource.getCurrentReportingManagerTwo().getFirstName() + " " + selectedResource.getCurrentReportingManagerTwo().getLastName();
																									// by
																									// shreya
																									// for
																									// Enhancement
																									// no:
																									// 3
			String employeeCurrentBU = selectedResource.getCurrentBuId().getParentId().getName() + "-" + selectedResource.getCurrentBuId().getName();

			String employeeParentBU = selectedResource.getBuId().getParentId().getName() + "-" + selectedResource.getBuId().getName();
			String employeeCompetancy = selectedResource.getCompetency().getSkill();

			String currentLocation = selectedResource.getLocationId().getLocation();

			String baseLocation = selectedResource.getDeploymentLocation().getLocation();

			if (selectedResource != null) {
				uiModel.addAttribute(Constants.RESOURCE_NAME, employeeName);
				uiModel.addAttribute(Constants.SELECTED_RESOURCE_RM1, employeeRM1Name);
				uiModel.addAttribute(Constants.SELECTED_RESOURCE_PARENT_BU, employeeParentBU);
				uiModel.addAttribute(Constants.SELECTED_RESOURCE_CURRENT_BU, employeeCurrentBU);
				uiModel.addAttribute(Constants.SELECTED_RESOURCE_RM2, employeeSRM); // added
																					// by
																					// shreya
																					// for
																					// Enhancement
																					// no:
																					// 3
				uiModel.addAttribute(Constants.RESOURCE_COMPETANCY, employeeCompetancy);
				uiModel.addAttribute(Constants.LOCATIONS, currentLocation);

				uiModel.addAttribute(Constants.LOCATION, baseLocation);
			}
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in employeeDetails method of ResourceAllocationController  controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in employeeDetails method of ResourceAllocationController controller:" + exception);
			throw exception;
		}
		logger.info("------ResourceAllocationController employeeDetails method end------");
		return "resourceallocations/readonlyEmployeeDetails";
	}

	@RequestMapping(value = "/getAllocs/{selectedprojectId}", method = RequestMethod.POST)
	public ResponseEntity<String> getAllocations(@PathVariable("selectedprojectId") Integer projId) throws Exception {
		String allocs = "";

		for (ResourceAllocation resourceAllocation : resourceAllocationService.getAllocations(projId)) {
			allocs = allocs + resourceAllocation.getEmployeeId().getEmployeeId() + ",";
		}
		// allocs = allocs.replace(allocs.substring(allocs.length()-1), "");

		return new ResponseEntity<String>(allocs, HttpStatus.OK);
	}

	// US3090(Added by Maya):START: Future timesheet delete functionality

	@RequestMapping(value = "/getTimesheetStatus/{resAllocId}/{weekEndDate}", method = RequestMethod.POST)
	public ResponseEntity<String> checkForTimesheetGreaterThanAllocEndDate(@PathVariable("resAllocId") Integer resourceAllocId, @PathVariable("weekEndDate") Date allocWeekEndDate,
			HttpServletResponse response) throws Exception {

		logger.info("------ResourceAllocationController checkForTimesheetGreaterThanAllocEndDate method start------");
		HttpHeaders headers = new HttpHeaders();
		JSONObject resultJSON, resultJSON2 = null;
		JSONArray jsonArray = null;
		List<UserActivity> userActivityList = resourceAllocationService.isFutureTimesheetpresent(resourceAllocId, allocWeekEndDate);
		if (userActivityList.size() > 0) {

			jsonArray = new JSONArray();
			for (int i = 0; i < userActivityList.size(); i++) {
				resultJSON = new JSONObject();

				resultJSON.put("userActivityId", userActivityList.get(i).getId());
				jsonArray.put(resultJSON);
			}
			resultJSON2 = new JSONObject();
			resultJSON2.put("status", "false");
			jsonArray.put(resultJSON2);
			return new ResponseEntity<String>(jsonArray.toString(), headers, HttpStatus.OK);
		}

		logger.info("------ResourceAllocationController checkForTimesheetGreaterThanAllocEndDate method end------");
		return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteFutureTimesheet", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> deleteFutureTimesheet(@RequestParam(value = "userActivityId") String userActivityId, @RequestParam(value = "resourceAllocId") String resourceAllocId,
			@RequestParam(value = "weekEndDate") String weekEndDate, HttpServletResponse response) throws Exception {

		logger.info("------ResourceAllocationController deleteFutureTimesheet method start------");
		HttpHeaders headers = new HttpHeaders();
		JSONArray arr = new JSONArray(userActivityId);
		List userActivityIdList = new ArrayList();
		for (int i = 0; i < arr.length() - 1; ++i) {
			JSONObject rec = arr.getJSONObject(i);
			Integer userActivityIdtest = rec.getInt("userActivityId");
			userActivityIdList.add(userActivityIdtest);
		}

		/*
		 * SimpleDateFormat formatter = new
		 * SimpleDateFormat("MMM dd, yyyy HH:mm:ss a"); Date date =
		 * formatter.parse(weekEndDate);
		 */

		/*
		 * SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); Date
		 * parsed = format.parse(weekEndDate); java.sql.Date sql = new
		 * java.sql.Date(parsed.getTime());
		 */

		String datearr[] = weekEndDate.split("-");
		weekEndDate = datearr[0] + "-" + datearr[1] + "-" + datearr[2];

		resourceAllocationService.deleteFutureTimesheet(userActivityIdList, resourceAllocId, weekEndDate);

		logger.info("------ResourceAllocationController deleteFutureTimesheet method end------");
		return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
	}

	// US3090(Added by Maya): END: Future timesheet delete functionality

	// added by purva for US3088 Starts
	@RequestMapping(value = "/checkIfAllocationIsOpen/{resourceAllocId}/{weekDate}", method = RequestMethod.POST)
	public ResponseEntity<String> checkIfAllocationIsOpen(@PathVariable("resourceAllocId") Integer resourceAllocId, @PathVariable("weekDate") Date weekDate, HttpServletResponse response)
			throws Exception {
		logger.info("------ResourceAllocationController checkForTimesheetGreaterThanAllocEndDate method start------");
		HttpHeaders headers = new HttpHeaders();
        
		boolean status = resourceAllocationService.checkIfAllocationIsOpen(resourceAllocId, weekDate);
		if (status == true) {
			return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
		}

		logger.info("------ResourceAllocationController checkForTimesheetGreaterThanAllocEndDate method end------");
		return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.OK);
	}

	// added by purva for US3088 Ends

	@RequestMapping(value = "/getAllBusForBGADMIN", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public ResponseEntity<String> getAllBusForBGADMIN() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String jsonArray = "";
		List<OrgHierarchy> allBuTypes = buService.findAllBusForBGADMIN();
		// uiModel.addAttribute("allocation", allocationTypeService.findAll());
		jsonArray = orgHierarchyjsonMapper.toJsonArrayOrg(allBuTypes);
		return new ResponseEntity<String>(jsonArray, headers, HttpStatus.OK);
		// return "mailconfiguration/defaultProject";
	}
	
	@RequestMapping(value = "/setDefaultProjectforBlockedResource", method = RequestMethod.GET)
	@ResponseBody
	public String setDefaultProjectforBlockedResource(){
		logger.info("------ResourceAllocationController changeAllocationTypeStatusofResource method Start------");
		resourceAllocationService.setDefaultProjectforBlockedResource();
		return "SUCCESS";
	}
	
}
