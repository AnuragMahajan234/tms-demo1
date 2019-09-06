/**
 * 
 */
package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeComparator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.dto.BillingScaleDTO;
import org.yash.rms.dto.ProjectDTO;
import org.yash.rms.dto.ResourceAllocationDTO;
//import org.yash.rms.dto.ResourceReleaseSummaryDTO;
import org.yash.rms.helper.ProjectHelper;
import org.yash.rms.helper.ResourceAllocationHelper;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.json.mapper.ProjectAllocationMapper;
import org.yash.rms.json.mapper.ResourceAllocationMapper;
import org.yash.rms.service.AllocationTypeService;
import org.yash.rms.service.DefaultProjectService;
import org.yash.rms.service.OwnershipService;
import org.yash.rms.service.ProjectAllocationService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.service.TimeHoursService;
import org.yash.rms.service.UserActivityService;
import org.yash.rms.service.impl.BillingScaleServiceImpl;
import org.yash.rms.util.Constants;
import org.yash.rms.util.GenericSearch;
import org.yash.rms.util.ProjectAllocationSearchCriteria;
import org.yash.rms.util.UserUtil;

/**
 * This class is used to handle all operations related to the project
 * allocation.
 */
@Controller
@RequestMapping("/projectallocations")
@Transactional
public class ProjectAllocationController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectAllocationController.class);

	@Autowired
	GenericSearch genericSearch;
	
	@Autowired
	@Qualifier("ProjectAllocationService")
	private ProjectAllocationService projectAllocationService;

	@Autowired
	@Qualifier("ResourceService")
	private ResourceService resourceServiceImpl;

	@Autowired
	@Qualifier("allocatioTypeService")
	private AllocationTypeService allocationTypeServiceImpl;

	@Autowired
	@Qualifier("allocatioTypeService")
	RmsCRUDService<AllocationType> allocationTypeService;

	@Autowired
	@Qualifier("OwnershipService")
	private OwnershipService ownershipServiceImpl;

	@Autowired
	@Qualifier("TimeHoursService")
	private TimeHoursService timehrsService;

	@Autowired
	@Qualifier("BillingScaleService")
	private RmsCRUDService<BillingScaleDTO> billingScaleService;
	
	@Autowired
	@Qualifier("ResourceAllocationHelper")
	private ResourceAllocationHelper resourceAllocationHelper;

	@Autowired
	@Qualifier("ResourceAllocationService")
	private ResourceAllocationService resourceAllocationService;

	@Autowired
	@Qualifier("ProjectAllocationMapper")
	private ProjectAllocationMapper jsonObjectMapper;

	@Autowired
	@Qualifier("UserActivityService")
	private UserActivityService userActivityService;

	@Autowired
	@Qualifier("ResourceService")
	private ResourceService resourceService;

	@Autowired
	@Qualifier("ProjectService")
	private ProjectService projectService;

	@Autowired
	@Qualifier("DefaultProjectService")
	private DefaultProjectService defaultProjectService;

	@Autowired
	@Qualifier("ResourceAllocationMapper")
	private ResourceAllocationMapper resourceAllocationMapper;

	ResponseEntity<String> responseEntity;

	boolean allCopied = true;

	@Autowired
	private UserUtil userUtil;

	@RequestMapping(method = RequestMethod.GET)
	public String loadProjectAllocations(Model uiModel, @RequestParam(value = "projectId", required = false) Integer projectId,
        @RequestParam(value = "projectKickOffDate", required = false) String projectKickOffDate, @RequestParam(value = "projectEndDate", required = false) String projectEndDate, @RequestParam(value = "projectName", required = false) String projectName, @RequestParam(value = "isExistPendingTimesheets", required = false) boolean isExistPendingTimesheets ) throws Exception {

		uiModel.addAttribute("allCopied", allCopied);
		//uiModel.addAttribute(Constants.RESOURCES, resourceServiceImpl.findActiveResources());
		uiModel.addAttribute(Constants.ALLOCATIONTYPE, allocationTypeServiceImpl.findAll());
		uiModel.addAttribute(Constants.OWNERSHIPS, ownershipServiceImpl.findAll());
		//uiModel.addAttribute(Constants.RATES, billingScaleService.findAll());
		//uiModel.addAttribute(Constants.ELIGIBLE_RESOURCES, ResourceAllocationHelper.getEligibleResourcesForProject(resourceAllocationService, resourceServiceImpl));
		Resource resource = userUtil.getLoggedInResource();
		try {
			if (resource.getUploadImage() != null && resource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			} else {
				uiModel.addAttribute("UserImage", "");
			}
		} catch (Exception exception) {
			logger.error("Exception occured in load ProjectAllocations method of ProjectAllocationController:" + exception);
			throw exception;
		}

		uiModel.addAttribute("firstName", resource.getFirstName() + " " + resource.getLastName());
		uiModel.addAttribute("designationName", resource.getDesignationId().getDesignationName());
		Calendar cal = Calendar.getInstance();
		cal.setTime(resource.getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		uiModel.addAttribute("DOJ", months + "-" + year);
		uiModel.addAttribute("ROLE", resource.getUserRole());
        if(projectId!=null){
          uiModel.addAttribute("projectId",projectId);
          uiModel.addAttribute("projectKickOffDate",projectKickOffDate);
          uiModel.addAttribute("projectEndDate",projectEndDate);
          uiModel.addAttribute("projectName",projectName);
          uiModel.addAttribute("isExistPendingTimesheets",isExistPendingTimesheets);
          uiModel.addAttribute("projectAllocNavigationFlag",true);
         }else{
          uiModel.addAttribute("projectAllocNavigationFlag",false);  
         }
     
		return "projectallocations/list";

	}

	@RequestMapping(value = "/list/{ActiveOrAll}", method = RequestMethod.GET)
	public ResponseEntity<String> getProjectList(@PathVariable("ActiveOrAll") String activeOrAll, HttpServletRequest request) throws Exception {

		logger.info("------ProjectAllocationController list method start------");
		
		JSONObject resultJSON = new JSONObject();
		HttpHeaders headers = new HttpHeaders();
		long totalCount = 0;
		
		headers.add("Content-Type", "application/json; charset=utf-8");
		
		try {
			
			List<OrgHierarchy> buList = null;
			Integer currentLoggedInUserId = UserUtil.getUserContextDetails().getEmployeeId();
			List<ProjectDTO> projectDisplay = new ArrayList<ProjectDTO>();
			boolean isCurrentUserAdmin = UserUtil.isCurrentUserIsAdmin();
			boolean isCurrentUserDelManager = UserUtil.isCurrentUserIsDeliveryManager();
			boolean isCurrentUserIsHr=UserUtil.isCurrentUserIsHr();
			boolean isCurrentUserBusinessGroupAdmin = UserUtil.isCurrentUserIsBusinessGroupAdmin();
			Integer page = Integer.parseInt(request.getParameter("iDisplayStart"));
			Integer size = Integer.parseInt(request.getParameter("iDisplayLength"));

			// get column position
			String iSortCol = request.getParameter("iSortCol_0");

			// for sorting direction
			String sSortDir = request.getParameter("sSortDir_0");
			String columnName;

			Integer iColumns = Integer.parseInt(request.getParameter("iColumns"));
			ProjectAllocationSearchCriteria projectSearchCriteria = new ProjectAllocationSearchCriteria();
			String[] searchColumns = projectSearchCriteria.getCols();
			String sSearch = "";

			if ((iSortCol != null || iSortCol != "") && (sSortDir != null || sSortDir != "")) {
				projectSearchCriteria.setOrderStyle(sSortDir);

				projectSearchCriteria.setOrederBy(projectSearchCriteria.getCols()[Integer.parseInt(iSortCol)]);

				projectSearchCriteria.setSortTableName(projectSearchCriteria.getColandTableNameMap().get(projectSearchCriteria.getCols()[Integer.parseInt(iSortCol)]));

				projectSearchCriteria.setRefTableName(projectSearchCriteria.getColandTableNameMap().get(projectSearchCriteria.getCols()[Integer.parseInt(iSortCol)]));

				projectSearchCriteria.setRefColName(projectSearchCriteria.getCols()[Integer.parseInt(iSortCol)]);
			}

			for (int i = 0; i < iColumns; i++) {
				sSearch = request.getParameter("sSearch_" + i);
				
				if (sSearch.length() > 0) {
					columnName = searchColumns[i];
					projectSearchCriteria.setRefTableName(projectSearchCriteria.getColandTableNameMap().get(columnName));
					projectSearchCriteria.setRefColName(columnName);
					projectSearchCriteria.setSearchValue(sSearch);
					break;
				}
			}

			if (currentLoggedInUserId != null) {
				if (isCurrentUserDelManager) {
					String objects;
					List<Integer> empIdList;
					List<String> projectNameList;
					empIdList = resourceServiceImpl.findResourceIdByRM2RM1(currentLoggedInUserId);
					projectNameList = projectAllocationService.getProjectNameForManager(activeOrAll, currentLoggedInUserId);
					List<org.yash.rms.domain.Project> list=null;
						//Fix for proper search
					if (sSearch.length() > 0) {
						
						 list= projectAllocationService.findCountProjectName(empIdList, null, null, activeOrAll, projectNameList, currentLoggedInUserId);
						}
					else
					{
								 list = projectAllocationService.findCountProjectName(empIdList, page, size, activeOrAll, projectNameList, currentLoggedInUserId);
						
					}
					if (list != null && !list.isEmpty()) {
						for (Iterator i = list.iterator(); i.hasNext();) {
							objects = (String) i.next();
							projectDisplay.addAll(projectAllocationService.findActiveProjectsByProjectNameEquals(objects, sSearch, projectSearchCriteria, activeOrAll));
						}
					}

					ProjectHelper.sortProjects(projectSearchCriteria.getRefColName(), projectSearchCriteria.getOrderStyle(), projectDisplay);

					if (sSearch.length() > 0) {
						//projectDisplay = projectAllocationService.findAllActiveProjects(page, size, projectSearchCriteria, activeOrAll, sSearch, buList, null);
						totalCount = projectDisplay.size();
					} else {
						totalCount = projectAllocationService.findCountProjectName(empIdList, null, null, activeOrAll, projectNameList, currentLoggedInUserId).size();
					}

				}

				else if (isCurrentUserAdmin) {
					projectDisplay = projectAllocationService.findAllActiveProjects(page, size, projectSearchCriteria, activeOrAll, sSearch, buList, null);
					if (sSearch.length() > 0) {

						totalCount = projectAllocationService.countSearch(projectSearchCriteria, activeOrAll, sSearch, null, null);
					} else {
						totalCount = projectAllocationService.countActive(activeOrAll, isCurrentUserAdmin, null, null);
					}
				} else if (isCurrentUserBusinessGroupAdmin||isCurrentUserIsHr) {
					buList = UserUtil.getCurrentResource().getAccessRight();
					projectDisplay = projectAllocationService.findAllActiveProjects(page, size, projectSearchCriteria, activeOrAll, sSearch, buList, currentLoggedInUserId);
					if (sSearch.length() > 0) {

						totalCount = projectAllocationService.countSearch(projectSearchCriteria, activeOrAll, sSearch, buList, currentLoggedInUserId);
					} else {

						totalCount = projectAllocationService.countActive(activeOrAll, isCurrentUserAdmin, buList, currentLoggedInUserId);
					}

				} else {
					projectDisplay = projectAllocationService.findAllActiveProjects(page, size, projectSearchCriteria, activeOrAll, sSearch, null, currentLoggedInUserId);
					if (sSearch.length() > 0) {

						totalCount = projectAllocationService.countSearch(projectSearchCriteria, activeOrAll, sSearch, null, currentLoggedInUserId);
					} else {
						totalCount = projectAllocationService.countActive(activeOrAll, isCurrentUserAdmin, buList, currentLoggedInUserId);
					}
				}
			}

			//resultJSON.put("aaData", projectDisplay);
			resultJSON.put("aaData", ProjectDTO.toJsonArray(projectDisplay));
			resultJSON.put("iTotalRecords", totalCount);
			resultJSON.put("iTotalDisplayRecords", totalCount);

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in list method of ProjectAllocationController:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in list method of ProjectAllocationController:" + exception);
			throw exception;
		}
		logger.info("------ProjectAllocationController list method end------");

		return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}/{selectedResourceIds}", method = RequestMethod.POST, produces = "text/html", headers = "Accept=application/json")
	public String copyProjectAllocationsFromJson(@PathVariable("id") Integer id, @PathVariable("selectedResourceIds") String ids) throws Exception {
		logger.info("------ProjectAllocationController copyFromJson method start------");
		try {
			ResourceAllocation resourceAllocation = resourceAllocationService.findResourceAllocation(id);
			allCopied = resourceAllocationHelper.copyResourceAllocations(resourceAllocation, ids, resourceAllocationService, resourceServiceImpl);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in copyFromJson method of ProjectAllocationController:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in copyFromJson method of ProjectAllocationController:" + exception);
			throw exception;
		}
		logger.info("------ProjectAllocationController copyFromJson method end------");
		return "redirect:/projectallocations";

	}

	@RequestMapping(params = "find=allocationByProjectId", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> findResourceAllocationsByProjectId(@RequestParam("projectId") Integer projectId) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		logger.info("------ProjectAllocationController findResourceAllocationsByProjectId method start------");
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<ResourceAllocation> resAlloc = null;
		try {

			resAlloc = resourceAllocationService.findResourceAllocationsByProjectId(projectId);
			Calendar lastActivitycalender = Calendar.getInstance();
			Calendar firstActivitycalender = Calendar.getInstance();
			if (!resAlloc.isEmpty()) {
				for (ResourceAllocation alloc : resAlloc) {
					List<Object[]> lastUserActivity = resourceAllocationService.findLastUseractivity(alloc.getId());

					if (lastUserActivity != null && !lastUserActivity.isEmpty()) {
						int j = 6;

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
						int k = 0;

						Date weekStartDate = (Date) useractivity[7];
						if (weekStartDate != null) {
							firstActivitycalender.setTime(weekStartDate);
							if ((Double) useractivity[0] != null) {
								alloc.setFirstUserActivityDate(weekStartDate);
							} else {
								for (int i = 1; i < 7;) {
									if ((Double) useractivity[i] != null) {
										firstActivitycalender.add(Calendar.DAY_OF_WEEK, k + i);
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

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in findResourceAllocationsByProjectId method of ProjectAllocationController:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in findResourceAllocationsByProjectId method of ProjectAllocationController:" + exception);
			throw exception;
		}
		logger.info("------ProjectAllocationController findResourceAllocationsByProjectId method end------");
		return new ResponseEntity<String>(jsonObjectMapper.toJsonArray(resAlloc), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "checkForExistingAllocation", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> checkForExistingProjectAllocation(@RequestBody String json) throws Exception {
		logger.info("------ProjectAllocationController checkForExistingAllocation method Start------");
		try {
			ResourceAllocation resourceAllocation = ProjectAllocationMapper.fromJsonToResourceAllocation(json);

			HttpHeaders headers = new HttpHeaders();
			// Get all allocations for the resource for which new allocation is
			// being created
			Resource resourceWithBU = resourceService.find(resourceAllocation.getEmployeeId().getEmployeeId());
			List<ResourceAllocation> allocationsForPopup = projectAllocationService.findOpenAllocationsOfResource(resourceAllocation.getEmployeeId().getEmployeeId(),
					resourceAllocation.getAllocStartDate(), resourceWithBU.getCurrentBuId());
			List<ResourceAllocation> allResourceAllocations = resourceAllocationService.findResourceAllocationsByEmployeeId(resourceAllocation.getEmployeeId());

			boolean flag = true;
			if (null == allResourceAllocations && allResourceAllocations.isEmpty()) {
				responseEntity = new ResponseEntity<String>("{ \"openflag\" : \"" + "false" + "\"}", headers, HttpStatus.OK);

			} else {

				for (ResourceAllocation allocation : allResourceAllocations) {

					Date newstartdate = resourceAllocation.getAllocStartDate();
					Date newEndDate = resourceAllocation.getAllocEndDate();
					if (allocation.getAllocEndDate() == null || allocation.getAllocEndDate().after(new Date()))
						if ((!(allocation.getId().equals(resourceAllocation.getId())) && allocation.getProjectId().getId().equals(resourceAllocation.getProjectId().getId()))
								&& allocation.isOverlaps(newstartdate, newEndDate)) {

							flag = false;
							break;
						}
				}

				if (!flag) {
					headers.add("Content-Type", "application/json");
					logger.info("------ProjectAllocationController checkForExistingAllocation method end------");
					responseEntity = new ResponseEntity<String>("{ \"error\" : \"" + "Same Resource can't be allocated in same time frame" + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
				} else {
					if (!allocationsForPopup.isEmpty()) {
						responseEntity = new ResponseEntity<String>("{ \"openflag\" : \"" + "true" + "\"}", headers, HttpStatus.OK);
					} else {
						responseEntity = new ResponseEntity<String>("{ \"openflag\" : \"" + "false" + "\"}", headers, HttpStatus.OK);
					}

				}
			}

		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in createFromJson method of ProjectAllocationController:" + exception);
			throw exception;
		} catch (Exception e) {
			logger.error("Exception occured in createFromJson method of ProjectAllocationController:" + e);
			throw e;
		}

		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createProjectAllocationFromJson(@RequestBody String json) throws Exception {
		logger.info("------ProjectAllocationController createFromJson method Start------");
		String message = "";
		Resource resource = userUtil.getLoggedInResource();
		try {
			ResourceAllocation resourceAllocation = ProjectAllocationMapper.fromJsonToResourceAllocation(json);

			HttpHeaders headers = new HttpHeaders();
			
			if (!(resourceAllocation.getProjectEndRemarks().equals("NA"))) {
				
	            Format formatter = new SimpleDateFormat(Constants.DATE_PATTERN_4);
	            String currentDate = formatter.format(new Date());
	            
	            if (!(resourceAllocation.getProjectEndRemarks().startsWith(Constants.FEEDBACK_PREFIX)))
	                resourceAllocation.setProjectEndRemarks(Constants.FEEDBACK_PREFIX + resource.getUsername() + " :" + Constants.FEEDBACK_DATE + ": " + currentDate + " </b><br/>"
	                        + resourceAllocation.getProjectEndRemarks());
	        }

			List<ResourceAllocation> allResourceAllocations = resourceAllocationService.findResourceAllocationsByEmployeeId(resourceAllocation.getEmployeeId());

			boolean flag = true;
			boolean save = true;
			for (ResourceAllocation allocation : allResourceAllocations) {
				Date newstartdate = resourceAllocation.getAllocStartDate();
				Date newEndDate = resourceAllocation.getAllocEndDate();
				if ((!(allocation.getId().equals(resourceAllocation.getId())) && allocation.getProjectId().getId().equals(resourceAllocation.getProjectId().getId()))
						&& allocation.isOverlaps(newstartdate, newEndDate)) {

					flag = false;
					break;
				}
			}

			DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();
			int date = 	dateTimeComparator.compare(resourceAllocation.getAllocEndDate(), new Date());
			System.out.println("date"+date);
			if (null == resourceAllocation.getAllocEndDate() || date>0) {
				for (org.yash.rms.domain.ResourceAllocation allocation : allResourceAllocations) {
					if (!(allocation.getId().equals(resourceAllocation.getId()))) {

						if (allocation.getCurProj() != null && allocation.getCurProj() && resourceAllocation.getCurProj() != null && resourceAllocation.getCurProj()) {
							allocation.setCurProj(false);
							resourceAllocationService.saveOrupdate(allocation);
						}
					}
				}

			} else {
				
			
				System.out.println("date"+date);
				if(date<0)
				{
				resourceAllocation.setCurProj(false);
				}
			//	resourceAllocation.setCurProj(false);
				//int date = resourceAllocation.getAllocEndDate().compareTo(new Date());
//				if (date == -1) {
//					if (resourceAllocation.getCurProj() == true) {
//						save = false;
//						message = "Please check End date to set Primary Project value";
//					}
//				}
			}

			if (flag) {
				if (resourceAllocation.getId() != null && save) {
					resourceAllocation.setUpdatedBy(UserUtil.userContextDetailsToResource(UserUtil.getUserContextDetails()));
					resourceAllocationService.saveOrupdate(resourceAllocation);
				} else {

					if (save) {
						resourceAllocation.setAllocatedBy(UserUtil.userContextDetailsToResource(UserUtil.getUserContextDetails()));
						resourceAllocation.setUpdatedBy(UserUtil.userContextDetailsToResource(UserUtil.getUserContextDetails()));
						resourceAllocationService.saveOrupdate(resourceAllocation);
					}
				}

				updateAllocationSeqForResourceAllocations(resourceAllocation.getEmployeeId());
				if (resourceAllocation.getCurProj() == null)
					resourceAllocation.setCurProj(false);
				if (resourceAllocation.getCurProj() == true) {
					for (org.yash.rms.domain.ResourceAllocation allocation : allResourceAllocations) {
						resourceAllocationService.saveOrupdate(allocation);
					}
				}

				headers.add("Content-Type", "application/json");
				logger.info("------ProjectAllocationController createFromJson method end------");
				if (("").equals(message)) {
					responseEntity = new ResponseEntity<String>("{ \"success\" : \"" + "Created Succesfully" + "\"}", headers, HttpStatus.CREATED);
				} else {
					responseEntity = new ResponseEntity<String>("{ \"error\" : \"" + message + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
				}

			} else {
				headers.add("Content-Type", "application/json");
				logger.info("------ProjectAllocationController createFromJson method end------");
				responseEntity = new ResponseEntity<String>("{ \"error\" : \"" + "Same Resource can't be allocated in same time frame" + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in createFromJson method of ProjectAllocationController:" + exception);
			throw exception;
		} catch (Exception e) {
			logger.error("Exception occured in createFromJson method of ProjectAllocationController:" + e);
			throw e;
		}

		return responseEntity;

	}

	public void updateAllocationSeqForResourceAllocations(Resource employeeId) throws Exception {
		logger.info("------ProjectAllocationController updateAllocationSeqForResourceAllocations method start------");
		List<org.yash.rms.domain.ResourceAllocation> resourceAllocation = resourceAllocationService.findResourceAllocationsByEmployeeId(employeeId);
		try {
			if (resourceAllocation != null && !resourceAllocation.isEmpty()) {
				Collections.sort(resourceAllocation, new org.yash.rms.domain.ResourceAllocation.ResourceAllocationTimeComparator());
				for (int i = 1; i <= resourceAllocation.size(); i++) {
					org.yash.rms.domain.ResourceAllocation resourceAllocationObj = resourceAllocation.get(i - 1);
					resourceAllocationObj.setAllocationSeq(i);
					resourceAllocationService.saveOrupdate(resourceAllocationObj);

				}

			}
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in updateAllocationSeqForResourceAllocations method of ProjectAllocationController:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in updateAllocationSeqForResourceAllocations method of ProjectAllocationController:" + exception);
			throw exception;
		}
		logger.info("------ProjectAllocationController updateAllocationSeqForResourceAllocations method end------");

	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, headers = "Accept=text/html")
	public ResponseEntity<String> deleteResourceAllocationFromJson(@PathVariable("id") Integer id, HttpServletResponse response) throws Exception {
		logger.info("------ProjectAllocationController deleteFromJson method start------");
		HttpHeaders headers = new HttpHeaders();
		try {
			ResourceAllocation resourceAllocation = resourceAllocationService.findResourceAllocation(id);
			Resource employeeId = resourceAllocation.getEmployeeId();

			response.setContentType("text/html");

			resourceAllocationService.delete(id);
			updateAllocationSeqForResourceAllocations(employeeId);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in deleteFromJson method of ProjectAllocationController:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in deleteFromJson method of ProjectAllocationController:" + exception);
			throw exception;
		}
		logger.info("------ProjectAllocationController deleteFromJson method end------");

		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/getStatus")
	public String loadResourceData(@RequestParam(value = "resourceid") int resourceid) throws Exception {
		logger.info("------ProjectAllocation getStatus method start------");

		try {
			Resource currentResource = resourceService.find(resourceid);
			return currentResource.getOwnership().getOwnershipName();

		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in loadResourceData method of ResourceLoanAndTransferController:" + exception);
			throw exception;
		} catch (Exception e) {
			logger.error("Exception occured in loadResourceData method of ResourceLoanAndTransferController:" + e);
			throw e;
		}

	}

	@RequestMapping(value = "/approveDetails/{id}/{empId}/{projId}", method = RequestMethod.POST, produces = "text/html")
	public ResponseEntity<String> approveDetails(@PathVariable("id") Integer id, @PathVariable("empId") Integer empId, @PathVariable("projId") Integer projId, HttpServletResponse response)
			throws Exception {
		logger.info("------ProjectAllocationController approveDetails method start------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Boolean check = true;
		Integer count = 0;

		try {
			ResourceAllocation resourceAllocation = resourceAllocationService.findById(id);

			List<UserActivity> userActivities = userActivityService.findUserActivitysByResourceAllocId(resourceAllocation);

			if (userActivities != null && !userActivities.isEmpty()) {
				for (UserActivity userActivity2 : userActivities) {
					if (userActivity2.getApproveStatus() == 1) {
						count++;
					}
				}
				logger.info("------ProjectAllocationController approveDetails method start------");
				if (userActivities.size() == count) {
					check = true;
					responseEntity = new ResponseEntity<String>("{ \"check\":" + check + "}", headers, HttpStatus.OK);
				} else {
					check = false;
					responseEntity = new ResponseEntity<String>("{ \"check\":" + check + "}", headers, HttpStatus.OK);
				}
			} else {
				check = true;
				logger.info("------ProjectAllocationController approveDetails method end------");
				responseEntity = new ResponseEntity<String>("{ \"check\":" + check + "}", headers, HttpStatus.OK);

			}
		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in approveDetails method of ProjectAllocationController:" + exception);
			throw exception;
		} catch (Exception e) {
			logger.error("Exception occured in approveDetails method of ProjectAllocationController:" + e);
			throw e;
		}

		return responseEntity;
	}

	@RequestMapping(params = "find=ByActiveProjectId", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> findActiveResourceAllocationsByProjectId(@RequestParam("projectId") Integer projectId) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		logger.info("------ProjectAllocationController findActiveResourceAllocationsByProjectId method start------");
		List<ResourceAllocation> resAlloc = null;
		try {
			resAlloc = resourceAllocationService.findActiveResourceAllocationsByProjectId(projectId);
			Calendar lastActivitycalender = Calendar.getInstance();
			Calendar firstActivitycalender = Calendar.getInstance();
			if (!resAlloc.isEmpty()) {
				for (ResourceAllocation alloc : resAlloc) {
					List<Object[]> lastUserActivity = resourceAllocationService.findLastUseractivity(alloc.getId());

					if (lastUserActivity != null && lastUserActivity.size() != 0) {
						int j = 6;

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

						int k = 0;

						Date weekStartDate = (Date) useractivity[7];
						if (weekStartDate != null) {
							firstActivitycalender.setTime(weekStartDate);
							if ((Double) useractivity[0] != null) {
								alloc.setFirstUserActivityDate(weekStartDate);
							} else {
								for (int i = 1; i < 7;) {
									if ((Double) useractivity[i] != null) {
										firstActivitycalender.add(Calendar.DAY_OF_WEEK, k + i);
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

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in findResourceAllocationsByProjectId method of ProjectAllocationController:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in findResourceAllocationsByProjectId method of ProjectAllocationController:" + exception);
			throw exception;
		}
		logger.info("------ProjectAllocationController findResourceAllocationsByProjectId method end------");
		if (allCopied)
			return new ResponseEntity<String>(jsonObjectMapper.toJsonArray(resAlloc), headers, HttpStatus.OK);
		else {
			allCopied = true;
			return new ResponseEntity<String>("{ \"error\" : \"" + "Some of the Allocations already have a Primary Project" + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/readonlyProjectDetails/{employeeid}", produces = "text/html")
	public String getProjectDetails(@PathVariable("employeeid") Integer employeeId, Model uiModel) throws Exception {
		logger.info("------ProjectAllocationController   projectDetails method start------");

		try {
			Resource selectedResource = resourceService.find(employeeId);
			List<ResourceAllocation> allocationList = resourceAllocationService.findResourceAllocationByActiveTypeEmployeeId(selectedResource);

			String employeeName = selectedResource.getFirstName() + " " + selectedResource.getLastName();
			String employeeCurrentBU = selectedResource.getCurrentBuId().getParentId().getName() + "-" + selectedResource.getCurrentBuId().getName();
			String employeeParentBU = selectedResource.getBuId().getParentId().getName() + "-" + selectedResource.getBuId().getName();
			String employeeCompetancy = selectedResource.getCompetency().getSkill();

			String employeeLocation = selectedResource.getLocationId().getLocation();
			Location deploymentLocation = null;
			String employeeLocation1 = null;
			deploymentLocation = selectedResource.getDeploymentLocation();
			if (deploymentLocation != null) {
				employeeLocation1 = selectedResource.getDeploymentLocation().getLocation();
				if (employeeLocation1 == null || employeeLocation1.isEmpty()) {
					employeeLocation1 = employeeLocation;
				}
			} else {
				employeeLocation1 = employeeLocation;
			}
			Resource CurrentReportingManager1=null;
			Resource CurrentReportingManager2=null;
			String CurrentReportingManagerOne=null;
			String CurrentReportingManagerTwo=null;
			
			CurrentReportingManager1 = selectedResource.getCurrentReportingManager();
			if (CurrentReportingManager1 != null) {
				CurrentReportingManagerOne = selectedResource.getCurrentReportingManager().getFirstName() + " "
						+ selectedResource.getCurrentReportingManager().getLastName();
				if (CurrentReportingManagerOne == null || CurrentReportingManagerOne.isEmpty()) {
					CurrentReportingManagerOne = "Not Available";
				}else {
					CurrentReportingManagerOne = selectedResource.getCurrentReportingManager().getFirstName() + " "
							+ selectedResource.getCurrentReportingManager().getLastName();
				}
			}else {
				CurrentReportingManagerOne = "Not Available";
			}

			CurrentReportingManager2 = selectedResource.getCurrentReportingManagerTwo();
			if (CurrentReportingManager2 != null) {
				CurrentReportingManagerTwo = selectedResource.getCurrentReportingManagerTwo().getFirstName() + " "
						+ selectedResource.getCurrentReportingManagerTwo().getLastName();
				if (CurrentReportingManagerTwo == null || CurrentReportingManagerTwo.isEmpty()) {
					CurrentReportingManagerTwo = "Not Available";
				}else {
					CurrentReportingManagerTwo = selectedResource.getCurrentReportingManagerTwo().getFirstName() + " "
							+ selectedResource.getCurrentReportingManagerTwo().getLastName();
				}
			}else {
				CurrentReportingManagerTwo = "Not Available";
			}
			
			if (selectedResource != null) {
				uiModel.addAttribute(Constants.RESOURCE_NAME, employeeName);
				uiModel.addAttribute(Constants.SELECTED_RESOURCE_RM1,CurrentReportingManagerOne);
				uiModel.addAttribute(Constants.SELECTED_RESOURCE_RM2,CurrentReportingManagerTwo);
				uiModel.addAttribute(Constants.SELECTED_RESOURCE_PARENT_BU, employeeParentBU);
				uiModel.addAttribute(Constants.SELECTED_RESOURCE_CURRENT_BU, employeeCurrentBU);
				uiModel.addAttribute(Constants.ALLOCATION_LIST, allocationList);
				uiModel.addAttribute(Constants.RESOURCE_COMPETANCY, employeeCompetancy);
				uiModel.addAttribute(Constants.LOCATIONS, employeeLocation);

				uiModel.addAttribute(Constants.LOCATION, employeeLocation1);
			}

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in projectDetails method of ProjectAllocationController  controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in projectDetails method of ProjectAllocationController   controller:" + exception);
			throw exception;
		}
		logger.info("------ProjectAllocationController projectDetails method end------");
		return "projectallocations/readonlyProjectDetails";
	}

	// US3090: START: Future time sheet delete functionality

	@RequestMapping(value = "/getTimesheetStatus/{resAllocId}/{weekEndDate}", method = RequestMethod.POST)
	public ResponseEntity<String> checkForTimesheetGreaterThanAllocEndDate(@PathVariable("resAllocId") Integer resourceAllocId, @PathVariable("weekEndDate") Date allocWeekEndDate,
			HttpServletResponse response) throws Exception {

		logger.info("------ProjectAllocationController checkForTimesheetGreaterThanAllocEndDate method start------");
		HttpHeaders headers = new HttpHeaders();
		JSONObject resultJSON;
		JSONObject resultJSON2;
		JSONArray jsonArray = null;
		List<UserActivity> userActivityList = resourceAllocationService.isFutureTimesheetpresent(resourceAllocId, allocWeekEndDate);
		if (!userActivityList.isEmpty()) {
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

		logger.info("------ProjectAllocationController checkForTimesheetGreaterThanAllocEndDate method end------");
		return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteFutureTimesheet", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> deleteFutureTimesheet(@RequestParam(value = "userActivityId") String userActivityId, @RequestParam(value = "resourceAllocId") String resourceAllocId,
			@RequestParam(value = "weekEndDate") String weekEndDate) throws Exception {

		logger.info("------ResourceAllocationController deleteFutureTimesheet method start------");
		HttpHeaders headers = new HttpHeaders();
		JSONArray jsonArray = new JSONArray(userActivityId);
		List<Integer> userActivityIdList = new ArrayList<Integer>();
		for (int i = 0; i < jsonArray.length() - 1; ++i) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			userActivityIdList.add(jsonObject.getInt("userActivityId"));
		}

		resourceAllocationService.deleteFutureTimesheet(userActivityIdList, resourceAllocId, weekEndDate);

		logger.info("------ResourceAllocationController deleteFutureTimesheet method end------");
		return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
	}

	// US3090: END: Future timesheet delete functionality

	// US3091/US3092: START
	@RequestMapping(value = "/findOpenAllocationsOfResource/{empId}/{startDate}", method = RequestMethod.GET)
	public String findOpenAllocationsOfResource(@PathVariable("empId") Integer empId, @PathVariable("startDate") Date startDate, Model uiModel) throws Exception {

		List<ResourceAllocation> allocationsForPopup;
		List<org.yash.rms.dto.ResourceAllocationDTO> allocationDtoList = new ArrayList<org.yash.rms.dto.ResourceAllocationDTO>();
		Resource resourceWithBU = resourceService.find(empId);
		allocationsForPopup = projectAllocationService.findOpenAllocationsOfResource(empId, startDate, resourceWithBU.getCurrentBuId());

		if (!allocationsForPopup.isEmpty())
			for (int i = 0; i < allocationsForPopup.size(); i++) {
				ResourceAllocationDTO resourceAllocationDto = new ResourceAllocationDTO();
				allocationDtoList.add(resourceAllocationMapper.fromresourceAllocationDomainToDto(allocationsForPopup.get(i), resourceAllocationDto));
		}

		uiModel.addAttribute("allocations", allocationDtoList);
		uiModel.addAttribute(Constants.ALLOCATIONTYPE, allocationTypeService.findAll());
		
		return "projectallocations/allocationPopup";
	}

	// Below two methods added for Copy Allocation Frame enhancement

	@RequestMapping(value = "/checkForExistingOpenAllocations/{selectedResourceIds}", method = RequestMethod.GET)
	public ResponseEntity<String> checkForExistingOpenAllocations(@PathVariable("selectedResourceIds") Integer[] empIds, Model uiModel) throws Exception {
		logger.info("------ProjectAllocationController checkForExistingOpenAllocations method start.");
		List<ResourceAllocation> resourceAllocationList = projectAllocationService.findOpenAllocationsOfCopiedResource(empIds);
		HttpHeaders headers = new HttpHeaders();

		if (resourceAllocationList.size() > 0) {
			responseEntity = new ResponseEntity<String>("{ \"openflag\" : \"" + "true" + "\"}", headers, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<String>("{ \"openflag\" : \"" + "false" + "\"}", headers, HttpStatus.OK);
		}
		logger.info("------ProjectAllocationController checkForExistingOpenAllocations method end.");
		return responseEntity;
	}

	@RequestMapping(value = "/findOpenAllocationsOfCopiedResource/{empId}", method = RequestMethod.GET)
	public String findOpenAllocationsOfAllResource(@PathVariable("empId") Integer[] empId, Model uiModel) throws Exception {

		Map<String, List<ResourceAllocationDTO>> allocationsForPopup = new HashMap<String, List<ResourceAllocationDTO>>();
		List<org.yash.rms.dto.ResourceAllocationDTO> allocationDtoList = new ArrayList<org.yash.rms.dto.ResourceAllocationDTO>();
		List<ResourceAllocation> resourceAllocationList = projectAllocationService.findOpenAllocationsOfCopiedResource(empId);
		if (null != resourceAllocationList && !resourceAllocationList.isEmpty()) {
			for (int i = 0; i < resourceAllocationList.size(); i++) {
				ResourceAllocationDTO resourceAllocationDto = new ResourceAllocationDTO();
				allocationDtoList.add(resourceAllocationMapper.fromresourceAllocationDomainToDto(resourceAllocationList.get(i), resourceAllocationDto));

			}
			for (Integer employeeId : empId) {
				List<ResourceAllocationDTO> resAllocation = new ArrayList<ResourceAllocationDTO>();
				for (ResourceAllocationDTO resAlloc : allocationDtoList) {
					if (resAlloc.getEmployeeId().getEmployeeId().intValue() == employeeId.intValue()) {
						resAllocation.add(resAlloc);
					}
				}
				if (null != resAllocation && !resAllocation.isEmpty())
					allocationsForPopup.put(resAllocation.get(0).getEmployeeId().getEmployeeName(), resAllocation);
				resAllocation = null;

			}
		}

		uiModel.addAttribute("allocations", allocationsForPopup);
		uiModel.addAttribute(Constants.ALLOCATIONTYPE, allocationTypeService.findAll());
		return "projectallocations/copiedResourceAllocationPopup";
	}
	
	
	@RequestMapping(value = "/findResourceDetailsForReleaseSummary/employees/{employeeId}/projects/{projectId}/allocationId/{allocationId}/joiningDate/{joiningDate}/startDates/{allocStartDate}/endDates/{allocEndDate}", method = RequestMethod.GET)
	public String resourceDetailsForReleaseSummary(@PathVariable("employeeId") Integer employeeId,
			@PathVariable("projectId") Integer projectId, @PathVariable("allocationId") Integer allocationId,
			@PathVariable("joiningDate") String joiningDate, @PathVariable("allocStartDate") String allocStartDate,
			@PathVariable("allocEndDate") String allocEndDate, Model uiModel) throws Exception {		
		logger.info("------ProjectAllocationController resourceDetailsForReleaseSummary method start.");
		
		if (employeeId != null && projectId != null && allocationId!= null) {
			Resource resource = resourceService.find(employeeId);			
			uiModel.addAttribute(Constants.EMP_NAME, resource.getEmployeeName());
			uiModel.addAttribute(Constants.EMP_ID, resource.getEmployeeId());			
			uiModel.addAttribute(Constants.EMP_YASH_ID, resource.getYashEmpId());
			uiModel.addAttribute(Constants.EMP_DESINGNATION, resource.getDesignationId().getDesignationName());
			uiModel.addAttribute(Constants.PROJECT_ID, projectId);	
			uiModel.addAttribute(Constants.RESOURCE_ALLOCATION_ID, allocationId);
			uiModel.addAttribute(Constants.SELECTED_RESOURCE_CURRENT_BU, resource.getCurrentBuId().getParentId().getName() + "-" + resource.getCurrentBuId().getName());
			uiModel.addAttribute(Constants.SELECTED_RESOURCE_RM1, resource.getCurrentReportingManager().getFirstName() + " " + resource.getCurrentReportingManager().getLastName());
			uiModel.addAttribute(Constants.RESOURCE_JOINING_DATE, joiningDate);
			uiModel.addAttribute(Constants.RESOURCE_AllOC_START_DATE, allocStartDate);
			uiModel.addAttribute(Constants.RESOURCE_AllOC_END_DATE, allocEndDate);		
		}
		logger.info("------ProjectAllocationController resourceDetailsForReleaseSummary method end.");
		return "projectallocations/releaseFeedbackSummary";
	}
	
	//TODO : will be added after 9th August 2017 deployment.
	/*@RequestMapping(value="/saveResourceDetailsOfReleaseSummary",method = RequestMethod.POST)
	public ResponseEntity<String> saveResourceDetailsOfReleaseSummary(@ModelAttribute ResourceReleaseSummaryDTO resourceReleaseSummaryForm, Model uiModel, HttpServletRequest httpServletRequest,HttpServletResponse response) throws Exception {
		logger.info("------ProjectAllocationController resourceDetailsForReleaseSummary method start.");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		
		try {
			
			String message ="";
			ResourcesReleaseSummary resourcesReleaseSummary = jsonObjectMapper.fromResourceReleaseSummaryFormToDtoResourceReleaseSummary(resourceReleaseSummaryForm);
			resourceAllocationService.saveResourceDetailsOfReleaseSummary(resourcesReleaseSummary);				
			logger.info("------ProjectAllocationController resourceDetailsForReleaseSummary method End.");

			if (("").equals(message)) {
				return new ResponseEntity<String>("{ \"success\" : \"" + "Created Succesfully" + "\"}" , headers, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("{ \"error\" : \"" + message + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch(RuntimeException runtimeException) {				
			logger.error("RuntimeException occured in resourceDetailsForReleaseSummary of ProjectAllocationController:"+runtimeException);				
			throw runtimeException;
		} catch(Exception exception) {
			logger.error("Exception occured in resourceDetailsForReleaseSummary of ProjectAllocationController::"+exception);				
			throw exception;
		}
		
	}*/
	
	@RequestMapping(value="/activeUserList", method = RequestMethod.GET)
	public ResponseEntity<String> getActiveUserList(@RequestParam(value="userInput", required=false) String userInput)  {
		logger.info("--------getActiveUserList method starts--------");
		System.out.println("-userInput - " + userInput + "-----------");
		
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		JSONObject resultJSON = new JSONObject();
		
		List<Resource> resourceList = genericSearch.getObjectsWithSearchAndPaginationForResource(userInput);
		resultJSON.put(Constants.RESOURCES, Resource.toJsonString(resourceList));
		
        logger.info("--------getActiveUserList method ends--------");
        return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/saveNewAllocationFromPopUp",method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> saveNewAllocationFromPopUp(@RequestBody String json) throws Exception {
		logger.info("--------@SaveNewAllocationFromPopUp method starts--------");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		ResponseEntity<String> responseEntity = null;
		String message = "";
		
		if(!StringUtils.isEmpty(json)) {
			JSONObject jsonObject = new JSONObject(json.trim());
			if(jsonObject != null) {
				Date currentAllocEnddate = null;
				String currentAllocEnddateStr = "";
				if(jsonObject.has("currentAllocEnddate")) {
					try{
						currentAllocEnddateStr = (String) jsonObject.get("currentAllocEnddate");
						currentAllocEnddate = Constants.DATE_FORMAT_NEW.parse(currentAllocEnddateStr);
					}
					catch (ParseException exception) {
						message  = "Invalid End Date Found";
						logger.error("Invalid Date in @SaveNewAllocationFromPopUp method of ProjectAllocationController:" + exception);
						responseEntity = new ResponseEntity<String>("{ \"error\" : \"" + message + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
						throw exception;
					} 
				}
				else{
					message = "Allocation end Date Not Found";
					responseEntity = new ResponseEntity<String>("{ \"error\" : \"" + message + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
					return responseEntity;
				}
				
				int resourceAllocId = jsonObject.getInt("currentAllocId");
				String currentAllocFeedback = (String) jsonObject.get("currentAllocFeedback");
				
				Resource resource = userUtil.getLoggedInResource();

				List<ResourceAllocation> resourceAllocations = new ArrayList<ResourceAllocation>();
				ResourceAllocation oldResourceAllocation = resourceAllocationService.findResourceAllocation(resourceAllocId);
				if(oldResourceAllocation != null) {
					oldResourceAllocation.setAllocEndDate(currentAllocEnddate);
					oldResourceAllocation.setProjectEndRemarks(currentAllocFeedback);
					resourceAllocations.add(oldResourceAllocation);
				}
				ResourceAllocation newResourceAllocation = ProjectAllocationMapper.fromJsonToResourceAllocation(json);
				resourceAllocations.add(newResourceAllocation);
				
				for(int index = 0; index < resourceAllocations.size() ; index++) {
					
					ResourceAllocation resourceAllocation = resourceAllocations.get(index);
					try {
						if (!(resourceAllocation.getProjectEndRemarks().equals("NA"))) {
							
				            Format formatter = new SimpleDateFormat(Constants.DATE_PATTERN_4);
				            String currentDate = formatter.format(new Date());
				            
				            if (!(resourceAllocation.getProjectEndRemarks().startsWith(Constants.FEEDBACK_PREFIX)))
				                resourceAllocation.setProjectEndRemarks(Constants.FEEDBACK_PREFIX + resource.getUsername() + " :" + Constants.FEEDBACK_DATE + ": " + currentDate + " </b><br/>"
				                        + resourceAllocation.getProjectEndRemarks());
				        }

						List<ResourceAllocation> allResourceAllocations = resourceAllocationService.findResourceAllocationsByEmployeeId(resourceAllocation.getEmployeeId());

						boolean flag = true;
						boolean save = true;
						try{
							for (ResourceAllocation allocation : allResourceAllocations) {
								Date newstartdate = resourceAllocation.getAllocStartDate();
								Date newEndDate = resourceAllocation.getAllocEndDate();
								if ((!(allocation.getId().equals(resourceAllocation.getId())) && allocation.getProjectId().getId().equals(resourceAllocation.getProjectId().getId()))
										&& allocation.isOverlaps(newstartdate, newEndDate)) {

									flag = false;
									break;
								}
							}
						}
						
						catch (Exception ex) {
							logger.error("Exception occured in @SaveNewAllocationFromPopUp method of ProjectAllocationController:" + ex);
							if(ex instanceof IllegalArgumentException){
								message = ex.getMessage();
								responseEntity = new ResponseEntity<String>("{ \"error\" : \"" + message + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
								return responseEntity;
							}
							else{
								responseEntity = new ResponseEntity<String>("{ \"error\" : \"" + message + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
								throw ex;
							}
						}
						DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();
						int date = 	dateTimeComparator.compare(resourceAllocation.getAllocEndDate(), new Date());
						if (null == resourceAllocation.getAllocEndDate() || date>0) {
							for (org.yash.rms.domain.ResourceAllocation allocation : allResourceAllocations) {
								if (!(allocation.getId().equals(resourceAllocation.getId()))) {

									if (allocation.getCurProj() != null && allocation.getCurProj() && resourceAllocation.getCurProj() != null && resourceAllocation.getCurProj()) {
										allocation.setCurProj(false);
										resourceAllocationService.saveOrupdate(allocation);
									}
								}
							}

						} else {
							
							if(date<0) {
								resourceAllocation.setCurProj(false);
							}
						}

						if (flag) {
							
							if (resourceAllocation.getId() != null && save) {
								
								resourceAllocation.setUpdatedBy(UserUtil.userContextDetailsToResource(UserUtil.getUserContextDetails()));
								boolean isDeleteTimeSheet = jsonObject.getBoolean("isDeleteTimeSheet");
								if(isDeleteTimeSheet){
									List<UserActivity> userActivityList = resourceAllocationService.isFutureTimesheetpresent(resourceAllocation.getId(), resourceAllocation.getAllocEndDate());
									if (userActivityList != null && !userActivityList.isEmpty()) {
										List<Integer> userActivityIdList = new ArrayList<Integer>();
										Iterator<UserActivity> iterator = userActivityList.iterator();
										while (iterator.hasNext()) {
											UserActivity userActivity = (UserActivity) iterator.next();
											userActivityIdList.add(userActivity.getId());
										}
									resourceAllocationService.deleteFutureTimesheet(userActivityIdList, String.valueOf(resourceAllocId), currentAllocEnddateStr);
									}
								}
								
								resourceAllocation.setLastupdatedTimestamp(new Date());
								resourceAllocationService.saveOrupdate(resourceAllocation);
							} else {

								if (save) {
									resourceAllocation.setAllocatedBy(UserUtil.userContextDetailsToResource(UserUtil.getUserContextDetails()));
									resourceAllocation.setUpdatedBy(UserUtil.userContextDetailsToResource(UserUtil.getUserContextDetails()));
									resourceAllocationService.saveOrupdate(resourceAllocation);
								}
							}

							updateAllocationSeqForResourceAllocations(resourceAllocation.getEmployeeId());

							logger.info("------ProjectAllocationController @SaveNewAllocationFromPopUp method end------");
							if (("").equals(message)) {
								responseEntity = new ResponseEntity<String>("{ \"success\" : \"" + "Created Succesfully" + "\"}", headers, HttpStatus.CREATED);
							} else {
								responseEntity = new ResponseEntity<String>("{ \"error\" : \"" + message + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
							}

						} else {
							headers.add("Content-Type", "application/json");
							logger.info("------ProjectAllocationController @SaveNewAllocationFromPopUp method end------");
							responseEntity = new ResponseEntity<String>("{ \"error\" : \"" + "Same Resource can't be allocated in same time frame" + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
						}
					}catch (Exception e) {
						logger.error("Exception occured in createFromJson method of ProjectAllocationController:" + e);
						responseEntity = new ResponseEntity<String>("{ \"error\" : \"" + message + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
						throw e;
					}
				}
			}
		}
		else{
			message = "No Record Data found";
			responseEntity = new ResponseEntity<String>("{ \"error\" : \"" + message + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(responseEntity == null) {
			responseEntity = new ResponseEntity<String>("{ \"error\" : \"" + message + "\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	@RequestMapping(value = "checkForExistingAllocation", method = RequestMethod.GET, params = {"find=existingAllocation"})
	public ResponseEntity<String> checkForExistingProjectAllocation(@RequestParam(value="oldResourceAllocId", required=true) int oldResourceAllocId,
			@RequestParam(value="employeeId", required=true) int employeeId,
			@RequestParam(value="allocStartDate", required=true) @DateTimeFormat(pattern="dd-MMM-yyyy") Date allocStartDate,
			@RequestParam(value="allocEndDate", required=false) @DateTimeFormat(pattern="dd-MMM-yyyy") Date allocEndDate,
			@RequestParam(value="projectId", required=true) int projectId) throws Exception {
		logger.info("------ProjectAllocationController CheckForExistingAllocation method Start------");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<String> responseEntity = null;
		JSONObject jsonObject = new JSONObject();
		try {
			// Get all allocations for the resource for which new allocation is
			// being created
			Resource resourceWithBU = resourceService.find(employeeId);
			List<ResourceAllocation> allocationsForPopup = projectAllocationService.findOpenAllocationsOfResource(employeeId, allocStartDate, resourceWithBU.getCurrentBuId());
			List<ResourceAllocation> allResourceAllocations = resourceAllocationService.findResourceAllocationsByEmployeeId(resourceWithBU);
			boolean flag = true;
			if (allResourceAllocations == null && allResourceAllocations.isEmpty()) {
				jsonObject.put("openflag", "false");
				jsonObject.put("message", "Allocation required");
				responseEntity = new ResponseEntity<String>(jsonObject.toString(), headers, HttpStatus.OK);
			} 
			else {

				for (ResourceAllocation allocation : allResourceAllocations) {

					if (allocation.getAllocEndDate() == null || allocation.getAllocEndDate().after(new Date()))
						if (allocation.getProjectId().getId().equals(projectId)	&& allocation.isOverlaps(allocStartDate, allocEndDate)
								&& oldResourceAllocId != allocation.getId()) {
							flag = false;
							break;
						}
				}

				if (!flag) {
					headers.add("Content-Type", "application/json");
					logger.info("------ProjectAllocationController checkForExistingAllocation method end------");
					jsonObject.put("error", "Same Resource can't be allocated in same time frame");
					responseEntity = new ResponseEntity<String>(jsonObject.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
				} else {
					if (!allocationsForPopup.isEmpty()) {
						jsonObject.put("openflag", "true");
						jsonObject.put("message", "Same allocation not found in same project and in same time frame.");
						responseEntity = new ResponseEntity<String>(jsonObject.toString(), headers, HttpStatus.OK);
					} else {
						jsonObject.put("openflag", "false");
						jsonObject.put("message", "Something went wrong !");
						responseEntity = new ResponseEntity<String>(jsonObject.toString(), headers, HttpStatus.OK);
					}
				}
			}

		} 
		catch (Exception e) {
			logger.error("Exception occured in createFromJson method of ProjectAllocationController:" + e);
			e.printStackTrace();
			jsonObject.put("error", "Something went wrong !");
			responseEntity = new ResponseEntity<String>(jsonObject.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		return responseEntity;
	}
}
