package org.yash.rms.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.dozer.DozerBeanMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.yash.rms.dao.ProjectDao;
import org.yash.rms.dao.RequestDao;
import org.yash.rms.dao.RequestReportDao;
import org.yash.rms.dao.RequestRequisitionDao;
import org.yash.rms.dao.RequestRequisitionResourceDao;
import org.yash.rms.dao.RequestRequisitionSkillDao;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.Competency;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.PDLEmailGroup;
import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.RequestRequisitionResource;
import org.yash.rms.domain.RequestRequisitionResourceStatus;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceComment;
import org.yash.rms.dto.CustomerDTO;
import org.yash.rms.dto.EditProfileDTO;
import org.yash.rms.dto.RequestRequisitionDashboardDTO;
import org.yash.rms.dto.RequestRequisitionDashboardInputDTO;
import org.yash.rms.dto.RequestRequisitionFormDTO;
import org.yash.rms.dto.RequestRequisitionResourceFormDTO;
import org.yash.rms.dto.RequestRequisitionSkillDTO;
import org.yash.rms.dto.ResourceCommentDTO;
import org.yash.rms.dto.ResourceFileDTO;
import org.yash.rms.dto.ResourceStatusDTO;
import org.yash.rms.exception.DAOException;
import org.yash.rms.exception.RMSServiceException;
import org.yash.rms.helper.ByteConvHelper;
import org.yash.rms.helper.EmailHelper;
import org.yash.rms.helper.ResourceHelper;
import org.yash.rms.mapper.RequestRequisitionFormMapper;
import org.yash.rms.mapper.RequestRequisitionSkillMapper;
import org.yash.rms.report.dto.RequestRequisitionReport;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.DesignationService;
import org.yash.rms.service.LocationService;
import org.yash.rms.service.PDLEmailGroupService;
import org.yash.rms.service.ProjectAllocationService;
import org.yash.rms.service.RequestRequisitionDashboardService;
import org.yash.rms.service.RequestRequisitionResourceService;
import org.yash.rms.service.RequestRequisitionResourceStatusService;
import org.yash.rms.service.RequestRequisitionService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DataTableParser;
import org.yash.rms.util.DateUtil;
import org.yash.rms.util.DozerMapperUtility;
import org.yash.rms.util.GeneralFunction;
import org.yash.rms.util.RequestRequisitionStatusConstants;
import org.yash.rms.util.SearchCriteriaGeneric;
import org.yash.rms.util.UserUtil;
import org.yash.rms.wrapper.RequestRequisitionWrapper;
import org.yash.rms.wrapper.ResourceInterviewWrapper;

import flexjson.JSONDeserializer;


@Service("requestRequisitionDashboardService")
@Transactional
public class RequestRequisitionDashboardServiceImpl implements RequestRequisitionDashboardService {

	@Autowired
	@Qualifier("RequestReportDao")
	RequestReportDao requestReportDao;
	
	/*@Autowired
	@Qualifier("pDLAndResourceMappingDao")
	PDLAndResourceMappingDao pDLAndResourceMappingDao;*/

	@Autowired
	@Qualifier("ProjectAllocationService")
	private ProjectAllocationService projectAllocationService;
	
	@Autowired
	private RequestRequisitionSkillDao requestRequisitionSkillDao;

	@Autowired
	@Qualifier("RequestDao")
	RequestDao requestDao;

	@Autowired
	@Qualifier("projectDaoImpl")
	ProjectDao projectDao;

	@Autowired
	ResourceService resourceService;
	
	@Autowired
	LocationService locationService;

	@Autowired
	ResourceHelper resourceHelper;

	@Autowired
	EmailHelper emailHelper;

	@Autowired
	@Qualifier("requestRequisitionResourceDao")
	RequestRequisitionResourceDao requestRequisitionResourceDao;

	@Autowired
	@Qualifier("allocatioTypeService")
	RmsCRUDService<AllocationType> allocationTypeService;

	@Autowired
	ResourceDao resourceDao;

	@Autowired
	@Qualifier("RequestRequisitionService")
	RequestRequisitionService requestRequisitionService;
	
	@Autowired
	@Qualifier("requestRequisitionResourceService")
	RequestRequisitionResourceService requestRequisitionResourceService;

	@Autowired
	@Qualifier("CustomerService")
	CustomerService customerService;

	@Autowired
	@Qualifier("mapper")
	DozerBeanMapper mapper;

	@Autowired
	PDLEmailGroupService pdlEmailGroupService;
	
	@Autowired
	RequestRequisitionDao requestRequisitionDao;
	
	@Autowired
	RequestRequisitionSkillMapper requestRequisitionSkillMapper;
	@Autowired
	UserUtil userUtil;
	
	@Autowired
	@Qualifier("requestRequisitionFormMapper")
	private RequestRequisitionFormMapper requestRequisitionFormMapper;
	
	@Autowired
	DesignationService designationService;
	
	@Autowired
	@Qualifier("requestRequisitionResourceStatusService")
	RequestRequisitionResourceStatusService requestRequisitionResourceStatusService;
	
	private static final Logger logger = LoggerFactory.getLogger(RequestRequisitionDashboardServiceImpl.class);

	@Autowired
	DozerMapperUtility dozerMapperUtility;

	public void saveSkillRequestReport(String requestID, MultipartFile[] files, String skillRequestId, Integer fullfillResLen, String anyComments, String resourceIds, String[] externalResourceNames,
			String[] resumeNameWithIdentifiers, String[] mailTo, String[] extraMailTo) {

		/*
		 * // Iterator<Map<String, Object>> iterator = items.iterator(); Integer
		 * skillReqId = Integer.parseInt(skillRequestId); // String comments =
		 * anyComments; // Integer totalLen = 0; Integer fillResLen =
		 * fullfillResLen; MultipartFile[] resumeFiles = files;
		 * RequestRequisition request = new RequestRequisition();
		 * RequestRequisitionSkill skillRequest = new RequestRequisitionSkill();
		 * RequestRequisitionResource skillResource;
		 * RequestRequisitionResourceStatus resourceStatusType = new
		 * RequestRequisitionResourceStatus(); List<String> skillReqIds = null;
		 * List<String> requestResourceId = new ArrayList<String>();
		 * List<String> internal = new ArrayList<String>(); // List<String>
		 * external = new ArrayList<String>(); // List<String> intextList = new
		 * ArrayList<String>(); List<Integer> statusType = new
		 * ArrayList<Integer>(); // List<MultipartFile> resumes = new
		 * ArrayList<MultipartFile>(); // List<String>
		 * resumeNamesWithIdentifiers = new ArrayList<String>(); //
		 * 
		 * for internal resources String[] resIDsInString =
		 * (resourceIds.split(","));
		 * 
		 * for (String resId : resIDsInString) { if (!("null".equals(resId)) &&
		 * (null != resId)) { internal.add(resId); } }
		 * intextList.addAll(internal); for external resources String[]
		 * extResInString = externalResourceNames;
		 * 
		 * if(extResInString != null){ for (String string : extResInString) { if
		 * (!("".equals(string)) && !(string.isEmpty()) &&
		 * !("undefined".equals(string))) { external.add(string); } } }
		 * 
		 * if (!(external.isEmpty())) { intextList.addAll(external); }
		 * 
		 * totalLen = internal.size() + external.size(); // total resources
		 * alloted
		 * 
		 * for uploaded resumes for (MultipartFile file : resumeFiles) {
		 * resumes.add(file); }
		 * 
		 * 
		 * for resume names with identifier(id for internal resource and 'ex'
		 * for external resource)
		 * 
		 * 
		 * String[] resumesNamesArr = resumeNameWithIdentifiers;
		 * 
		 * for (String item : resumesNamesArr) {
		 * resumeNamesWithIdentifiers.add(item); }
		 * 
		 * skillRequest.setFulfilled(String.valueOf(fillResLen));
		 * skillRequest.setId(skillReqId); skillRequest.setComments(comments);
		 * 
		 * List<RequestRequisitionSkill> skillRequests = new
		 * ArrayList<RequestRequisitionSkill>();
		 * List<RequestRequisitionResource> skillResources = new
		 * ArrayList<RequestRequisitionResource>(); try { for (int i = 0; i <
		 * totalLen; i++) { skillResource = new RequestRequisitionResource(); //
		 * AllocationType allocationId = new AllocationType(); //
		 * SkillResourceStatusType statusId = new // SkillResourceStatusType();
		 * 
		 * if (requestResourceId != null && requestResourceId.size() > 0 && i <
		 * requestResourceId.size() && internal.size() > 0) {
		 * skillResource.setId(Integer.parseInt(requestResourceId.get(i) )); }
		 * 
		 * if (internal != null && internal.size() > 0 && i < internal.size()) {
		 * if (i <= internal.size()) { Resource resource = new Resource();
		 * resource.setEmployeeId(Integer.parseInt(internal.get(i)));
		 * skillResource.setResource(resource);
		 * skillResource.setResumeFileName(resumeNamesWithIdentifiers.get(i));
		 * skillResource.setUploadResume(resumes.get(i).getBytes());
		 * 
		 * } } else if (external != null && external.size() > 0) {
		 * skillResource.setExternalResourceName(intextList.get(i));
		 * skillResource.setResumeFileName(resumeNamesWithIdentifiers.get(i));
		 * skillResource.setUploadResume(resumes.get(i).getBytes()); }
		 * 
		 * skillResource.setRequestRequisitionSkill(skillRequest);
		 * skillResources.add(skillResource);
		 * 
		 * } } catch (IOException exception) {
		 * 
		 * }
		 * 
		 * skillRequests.add(skillRequest);
		 * //skillRequest.setRequestRequisitionResource(skillResources);
		 * requestRequisitionResourceDao.saveRequestRequisitionResource(
		 * skillResources, skillRequest);
		 * //requestReportDao.saveSkillRequestReport(skillRequest, skillReqId,
		 * skillResources);
		 * 
		 * // code for sending mail List<RequestRequisitionSkill> list =
		 * requestReportDao.addResourceWithSkillReqId(skillRequest.getId());
		 * RequestRequisitionSkill skill = list.get(0); List<String> ccMail =
		 * new ArrayList<String>(); ArrayList<String[]> stringArray = new
		 * ArrayList<String[]>(); if (Arrays.asList(mailTo).contains("1")) { if
		 * (skill.getRequestRequisition().getNotifyMailTo() != null) {
		 * stringArray.add(skill.getRequestRequisition().getNotifyMailTo().split
		 * (",")); } else {
		 * stringArray.add(skill.getRequestRequisition().getSentMailTo().split(
		 * ",")); } } if (Arrays.asList(mailTo).contains("2")) {
		 * stringArray.add(skill.getRequestRequisition().getSentMailTo().split(
		 * ",")); }
		 * 
		 * if(extraMailTo != null && extraMailTo.length > 0){
		 * stringArray.add(extraMailTo); }
		 * 
		 * if (stringArray != null && stringArray.size() > 0) {
		 * ArrayList<Integer> intArray = new ArrayList<Integer>(); for (int i =
		 * 0; i < stringArray.size(); i++) { String[] numberAsStringArr =
		 * stringArray.get(i); for (int j = 0; j < numberAsStringArr.length;
		 * j++) { String numberAsString = numberAsStringArr[j].trim();
		 * intArray.add(Integer.parseInt(numberAsString)); } } ccMail =
		 * (resourceService.findEmailById(intArray)); } String[] sentMailCCTo =
		 * null; String[] tomailIDsList = null; String mailToList = null; if
		 * (ccMail != null) { sentMailCCTo = new
		 * HashSet<String>(ccMail).toArray(new String[0]); }
		 * 
		 * if (skill.getKeyInterviewersOne() != null &&
		 * skill.getKeyInterviewersTwo() == null) { mailToList =
		 * skill.getKeyInterviewersOne(); tomailIDsList = mailToList.split(",");
		 * tomailIDsList = new
		 * HashSet<String>(Arrays.asList(tomailIDsList)).toArray(new String[0]);
		 * } else if (skill.getKeyInterviewersOne() == null &&
		 * skill.getKeyInterviewersTwo() != null) { mailToList =
		 * skill.getKeyInterviewersTwo(); tomailIDsList = mailToList.split(",");
		 * tomailIDsList = new
		 * HashSet<String>(Arrays.asList(tomailIDsList)).toArray(new String[0]);
		 * } else if (skill.getKeyInterviewersOne() != null &&
		 * skill.getKeyInterviewersTwo() != null) { mailToList =
		 * skill.getKeyInterviewersOne() + ", " + skill.getKeyInterviewersTwo();
		 * tomailIDsList = mailToList.split(","); tomailIDsList = new
		 * HashSet<String>(Arrays.asList(tomailIDsList)).toArray(new String[0]);
		 * }
		 * 
		 * Integer currentLoggedInUserId =
		 * UserUtil.getCurrentResource().getEmployeeId(); Resource
		 * currentResource = resourceService.find(currentLoggedInUserId); String
		 * currentBU = currentResource.getCurrentBuId().getParentId().getName()
		 * + "-" + currentResource.getCurrentBuId().getName(); String
		 * sendMailFrom = currentResource.getEmailId(); String sender =
		 * currentResource.getFirstName() + " " + currentResource.getLastName();
		 * Map<String, Object> model = new HashMap<String, Object>();
		 * resourceHelper.setEmailContentForInternalRequest(model,
		 * tomailIDsList, sentMailCCTo, sender,
		 * skill.getRequestRequisition().getProject().getProjectName(),
		 * currentBU, sendMailFrom, requestRequisitionService.
		 * findRequestRequisitionSkillsByRequestRequisitionId(skill.
		 * getRequestRequisition().getId()), comments,
		 * skill.getRequestRequisition().getCustomer().getCustomerName(),
		 * skill.getRequirementId(),skill.getRequestRequisition().getGroup().
		 * getCustomerGroupName()); emailHelper.sendEmail(model, tomailIDsList,
		 * sentMailCCTo, resumeFiles);
		 */
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public List<RequestRequisitionResource> updateResourceRequestWithStatus(String requestJSON) {
		List<Map<String, Object>> items = new JSONDeserializer<Map<String, List<Map<String, Object>>>>().use("values.values", RequestRequisitionResource.class).deserialize(requestJSON, String.class)
				.get("parameters");

		Iterator<Map<String, Object>> iterator = items.iterator();
		// RequestRequisitionResourceStatus resourceStatusType = new
		// RequestRequisitionResourceStatus();
		List<String> skillReqIds = null;
		List<Integer> requestResourceId = new ArrayList<Integer>();
		List<Integer> resourceId = new ArrayList<Integer>();
		List<Integer> allocationId = new ArrayList<Integer>();
		List<Integer> statusTypeId = new ArrayList<Integer>();
		List<String> allocationDate = new ArrayList<String>();
		List<RequestRequisitionResource> list = new ArrayList<RequestRequisitionResource>();
		Integer reqId;
		Integer skillReqId = 0;
		Integer projectId = 0;
		while (iterator.hasNext()) {
			Map map = iterator.next();
			Set set = map.entrySet();
			Iterator setIterator = set.iterator();

			while (setIterator.hasNext()) {

				Map.Entry entry = (Map.Entry) setIterator.next();
				String key = (String) entry.getKey();
				if (key.equals("reqId")) {
					reqId = Integer.parseInt((String) entry.getValue());
					// request.setId(reqId);
				} else if (key.equals("skillReqId")) {

					skillReqId = Integer.parseInt((String) entry.getValue());
				} else if (key.equals("requestResourceId")) {
					String str = entry.getValue().toString();
					System.out.println("str==requestResourceId==" + str);
					if (str != null && !str.equals("")) {
						JSONArray jsonArray = new JSONArray(str);
						// requestResourceId = new ArrayList<String>();
						for (int i = 0; i < jsonArray.length(); i++) {
							requestResourceId.add(jsonArray.getInt(i));
						}
					}
				} else if (key.equals("statusType")) {
					String str = entry.getValue().toString();
					if (str != null && !str.equals("")) {
						JSONArray jsonArray = new JSONArray(str);
						for (int i = 0; i < jsonArray.length(); i++) {
							statusTypeId.add(jsonArray.getInt(i));
						}
					}
				} else if (key.equals("allocationId")) {
					String str = entry.getValue().toString();
					if (str != null && !str.equals("")) {
						JSONArray jsonArray = new JSONArray(str);
						for (int i = 0; i < jsonArray.length(); i++) {
							allocationId.add(jsonArray.getInt(i));
						}
					}
				} else if (key.equals("resourceId")) {
					String str = entry.getValue().toString();
					if (str != null && !str.equals("") && !str.equals(" ")) {
						JSONArray jsonArray = new JSONArray(str);
						for (int i = 0; i < jsonArray.length(); i++) {
							resourceId.add(jsonArray.getInt(i));
						}
					}
				} else if (key.equals("allocationDate")) {
					String str = entry.getValue().toString();
					if (str != null && !str.equals("")) {
						JSONArray jsonArray = new JSONArray(str);
						for (int i = 0; i < jsonArray.length(); i++) {
							allocationDate.add(jsonArray.getString(i));
						}
					}
				} else if (key.equals("projectId")) {
					projectId = Integer.parseInt((String) entry.getValue());
					System.out.println("projectId==" + projectId);
				}
			} // first while
		} // second while
		System.out.println("requestResourceId==" + requestResourceId);
		System.out.println("statusTypeId=" + statusTypeId);
		System.out.println("resourceId=" + resourceId);
		System.out.println("allocationId==" + allocationId);
		System.out.println("allocationDate==" + allocationDate);
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		if (requestResourceId != null) {
			for (int i = 0; i < requestResourceId.size(); i++) {
				RequestRequisitionResource skillResource = new RequestRequisitionResource();
				RequestRequisitionResourceStatus statusType = new RequestRequisitionResourceStatus();
				AllocationType allocationType = new AllocationType();
				RequestRequisitionSkill sklrequest = new RequestRequisitionSkill();
				sklrequest.setId(skillReqId);

				if (requestResourceId != null && requestResourceId.get(i) != null) {
					skillResource.setId(requestResourceId.get(i));
				}
				if (statusTypeId != null && statusTypeId.size() > 0 && statusTypeId.get(i) != null) {
					statusType.setId(statusTypeId.get(i));
				}
				if (allocationId != null && allocationId.size() > 0 && allocationId.get(i) != null) {
					allocationType.setId(allocationId.get(i));
				}
				if (statusType != null) {
					skillResource.setRequestRequisitionResourceStatus(statusType);
				}
				if (allocationType != null) {
					skillResource.setAllocation(allocationType);
				}
				try {
					if (allocationDate != null && allocationDate.size() > 0 && allocationDate.get(i) != null && !allocationDate.get(i).equals("")) {
						skillResource.setAllocationDate(df.parse(allocationDate.get(i)));
					}
					skillResource.setRequestRequisitionSkill(sklrequest);
				} catch (ParseException e) {

					e.printStackTrace();
				}
				list.add(skillResource);
			}
		}
		List<RequestRequisitionResource> Reslist = requestRequisitionResourceDao.updateResourceRequestWithStatus(list);
		boolean updateStatus = requestReportDao.reduceFullfilledSkillRequest(skillReqId);
		System.out.println("====Reslist====" + Reslist.size());
		System.out.println("====updateStatus of fullfill====" + updateStatus);
		return Reslist;

	}

	public boolean saveReport(String requestJSON) {
		List items = new JSONDeserializer<Map<String, List<Map<String, Object>>>>().use("values.values", RequestRequisitionSkill.class).deserialize(requestJSON, String.class).get("parameters");

		ListIterator<Map<String, Object>> iterator = items.listIterator();

		List<RequestRequisition> requests = new ArrayList<RequestRequisition>();
		while (iterator.hasNext()) {
			Integer skillId = null;
			Map map = iterator.next();
			Set set = map.entrySet();
			Iterator setIterator = set.iterator();
			RequestRequisition request = new RequestRequisition();
			ArrayList<String> skillReqIds = null;
			ArrayList<String> requestResourceId = new ArrayList<String>();
			ArrayList<String> fulfilled = null;
			ArrayList<String> remRes = null;
			ArrayList<String> comments = null;
			ArrayList<String> internal = new ArrayList<String>();
			ArrayList<String> external = new ArrayList<String>();
			ArrayList<String> intextList = new ArrayList<String>();
			while (setIterator.hasNext()) {

				Map.Entry entry = (Map.Entry) setIterator.next();
				String key = (String) entry.getKey();
				if (key.equals("reqId")) {
					int i = (Integer.parseInt((String) entry.getValue()));
					request.setId(i);
				} else if (key.equals("skillReqId")) {
					skillReqIds = new ArrayList<String>((ArrayList) (entry.getValue()));
				} else if (key.equals("requestResourceId")) {
					String str = entry.getValue().toString();

					System.out.println("str==requestResourceId==" + str);
					if (str != null && !str.equals("")) {
						JSONArray jsonArray = new JSONArray(str);
						// requestResourceId = new ArrayList<String>();
						for (int i = 0; i < jsonArray.length(); i++) {
							requestResourceId.add(jsonArray.getInt(i) + "");
						}
					}
					// requestResourceId = new ArrayList<String>((ArrayList)
					// (entry.getValue()));
				} else if (key.equals("fulfilled")) {
					fulfilled = new ArrayList<String>((ArrayList) (entry.getValue()));
				} else if (key.equals("remRes")) {
					remRes = new ArrayList<String>((ArrayList) (entry.getValue()));
				} else if (key.equals("comments")) {
					comments = new ArrayList<String>((ArrayList) (entry.getValue()));
				} else if (key.equals("internal")) {
					String str = entry.getValue().toString();
					System.out.println("str====" + str);

					if (str != null && !str.equals("")) {

						JSONArray jsonArray = new JSONArray(str);
						// internal = new ArrayList<String>();
						for (int i = 0; i < jsonArray.length(); i++) {
							internal.add(jsonArray.getInt(i) + "");
						}
						intextList.addAll(internal);
					}
				} else if (key.equals("external")) {
					String str = entry.getValue().toString();
					if (str != null && !str.equals("")) {
						JSONArray jsonArray = new JSONArray(str);
						// external = new ArrayList<String>();
						for (int i = 0; i < jsonArray.length(); i++) {
							external.add(jsonArray.getString(i));
						}
						intextList.addAll(external);
					}
				}
				if (!(fulfilled == null) && !(remRes == null) && !(comments == null) && !(skillReqIds == null) && !(requestResourceId == null) && (requestResourceId.isEmpty())) {
					List<RequestRequisitionSkill> skillRequests = new ArrayList<RequestRequisitionSkill>();
					List<RequestRequisitionResource> skillResources = new ArrayList<RequestRequisitionResource>();
					RequestRequisitionResource skillResource = null;
					Integer fullFilledResource = internal.size() + external.size();
					for (int i = 0; i < fulfilled.size(); i++) {
						skillId = Integer.parseInt(skillReqIds.get(i));
						RequestRequisitionSkill skillRequest = new RequestRequisitionSkill();
						skillRequest.setId(Integer.parseInt(skillReqIds.get(i)));
						skillRequest.setRemaining(remRes.get(i));
						skillRequest.setComments(comments.get(i));
						for (int j = 0; j < fullFilledResource; j++) {
							skillResource = new RequestRequisitionResource();
							if (requestResourceId != null && requestResourceId.size() > 0 && j < requestResourceId.size()) {
								skillResource.setId(Integer.parseInt(requestResourceId.get(j)));
							}
							if (internal != null && internal.size() > 0 && j < internal.size()) {
								Resource resource = new Resource();
								resource.setEmployeeId(Integer.parseInt(intextList.get(j)));
								skillResource.setResource(resource);
							} else if (external != null && external.size() > 0) {
								skillResource.setExternalResourceName(intextList.get(j));
							}
							skillResource.setRequestRequisitionSkill(skillRequest);
							skillResources.add(skillResource);
						}

						System.out.println("==fullFilledResource===" + fullFilledResource);
						skillRequest.setFulfilled(fullFilledResource);
						skillRequests.add(skillRequest);
						// skillRequest.setRequestRequisitionResource(skillResources);
					}
					// request.setRequestRequisitionSkill(skillRequests);
				}

				// 1003958 START[]

				// 1003958 ENDS[]

			}
			requestReportDao.saveReport(request, skillId);
		}

		return true;
	}

	public List<RequestRequisitionSkill> getReport(String opt, Integer eid, String role) {

		List<RequestRequisitionSkill> requestReportList = requestReportDao.getReport(opt, eid, role);
		return requestReportList;

	}

	public boolean deleteSkillRequestResource(Integer id, Integer skillId) {
		
		
		RequestRequisitionResource requestRequisitionResource = requestRequisitionResourceDao.getResourceBySkillIdAndResourceId(id,skillId);
		RequestRequisitionSkill requestRequisitionSkill = requestRequisitionResource.getRequestRequisitionSkill();
		int open=0;
		int closed =requestRequisitionSkill.getClosed()!=null?requestRequisitionSkill.getClosed():0;
		int shortlisted=requestRequisitionSkill.getShortlisted()!=null?requestRequisitionSkill.getShortlisted():0;
		
		requestRequisitionSkill.setSubmissions((requestRequisitionSkill.getSubmissions()-1));
		
		if (requestRequisitionResource.getRequestRequisitionResourceStatus().getStatusName().equalsIgnoreCase(RequestRequisitionStatusConstants.SELECTED.toString())) {
			requestRequisitionSkill.setShortlisted((requestRequisitionSkill.getShortlisted()-1));
			requestRequisitionSkill.setFulfilled((requestRequisitionSkill.getFulfilled()-1));
			//requestRequisitionSkill.setOpen((requestRequisitionSkill.getOpen()+1));
			open = open+requestRequisitionSkill.getNoOfResources() 
			-(closed+shortlisted+(requestRequisitionSkill.getHold()!=null?requestRequisitionSkill.getHold():0)
					+(requestRequisitionSkill.getLost()!=null?requestRequisitionSkill.getLost():0));
			requestRequisitionSkill.setOpen(open+1);
		} else if (requestRequisitionResource.getRequestRequisitionResourceStatus().getStatusName().equalsIgnoreCase(RequestRequisitionStatusConstants.REJECTED.toString()) ) {
			requestRequisitionSkill.setNotFitForRequirement((requestRequisitionSkill.getNotFitForRequirement()-1));
			open = open+requestRequisitionSkill.getNoOfResources() 
			-(closed+shortlisted+(requestRequisitionSkill.getHold()!=null?requestRequisitionSkill.getHold():0)
					+(requestRequisitionSkill.getLost()!=null?requestRequisitionSkill.getLost():0));
			requestRequisitionSkill.setOpen(open+1);
		} else if (requestRequisitionResource.getRequestRequisitionResourceStatus().getStatusName().equalsIgnoreCase(RequestRequisitionStatusConstants.NOT_SHORTLISTED.toString()) ) {
			requestRequisitionSkill.setNotFitForRequirement((requestRequisitionSkill.getNotFitForRequirement()-1));
			open = open+requestRequisitionSkill.getNoOfResources() 
			-(closed+shortlisted+(requestRequisitionSkill.getHold()!=null?requestRequisitionSkill.getHold():0)
					+(requestRequisitionSkill.getLost()!=null?requestRequisitionSkill.getLost():0));
			requestRequisitionSkill.setOpen(open+1);
		}
		requestRequisitionSkill.setStatus(getRequestRequisitionSkillStatus(requestRequisitionSkill));
		if(requestRequisitionSkill.getNoOfResources() == (requestRequisitionSkill.getClosed()+requestRequisitionSkill.getHold()+requestRequisitionSkill.getLost())) {
			requestRequisitionSkill.setClosureDate(new Date());
		}else requestRequisitionSkill.setClosureDate(null);		
		boolean result = requestReportDao.deleteSkillRequestResource(id);
		return result;
	}

	public boolean updateSkillRequestStatus(Integer id, Integer statusType) {
		boolean result = requestReportDao.deleteSkillRequestResource(id);
		return result;
	}

	public List<RequestRequisitionSkill> addResourceWithSkillReqId(Integer id) {
		List<RequestRequisitionSkill> requestReportList = requestReportDao.getRequestRequisitionSkillById(id);
		return requestReportList;
	}
	public List<RequestRequisitionSkill> getRequestRequisitionSkillByReqSkillId(Integer reqSkillId)
	{
		List<RequestRequisitionSkill> requestReportList = requestReportDao.getRequestRequisitionSkillById(reqSkillId);
		return requestReportList;
	}

	public List<RequestRequisitionSkillDTO> getRequestRequisitionSkillBySkillId(Integer id) {
		List<RequestRequisitionSkill> requestReportList = requestReportDao.getRequestRequisitionSkillById(id);
		List<RequestRequisitionSkillDTO> rrsDTO = requestRequisitionSkillMapper.convertDomainsToDTOs(requestReportList);
		
		
		for (RequestRequisitionSkillDTO requestRequisitionSkillDto : rrsDTO) {
			String keyinterviewer1String = requestRequisitionSkillDto.getKeyInterviewersOne();
			
			String keyinterviewer2String = requestRequisitionSkillDto.getKeyInterviewersTwo();
			
			if(keyinterviewer1String!= null && !keyinterviewer1String.isEmpty()){
				String[] keyInterviewersinArray = keyinterviewer1String.split(",");
				
				ArrayList<Integer> interviewersRound1 = new ArrayList<Integer>();
				
				for (int i = 0; i < keyInterviewersinArray.length; i++) {
					interviewersRound1.add(
							Integer.parseInt(keyInterviewersinArray[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
				}
				
				List<String> round1Emails = resourceService.findEmailById(interviewersRound1);
				
				HashSet<String> hashSetRound1 = new HashSet<String>(round1Emails);
				String round1 = hashSetRound1.toString().replaceAll("\\[", "").replaceAll("\\]", "").trim();
				requestRequisitionSkillDto.setKeyInterviewersOne(round1);
			}
			
			if(keyinterviewer2String!= null && !keyinterviewer2String.isEmpty()){
				String[] keyInterviewers2inArray = keyinterviewer2String.split(",");
				ArrayList<Integer> interviewersRound2 = new ArrayList<Integer>();

				for (int i = 0; i < keyInterviewers2inArray.length; i++) {
					interviewersRound2.add(Integer
							.parseInt(keyInterviewers2inArray[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
				}

				List<String> round2Emails = resourceService.findEmailById(interviewersRound2);
				
				HashSet<String> hashSetRound2 = new HashSet<String>(round2Emails);
				String round2 = hashSetRound2.toString().replaceAll("\\[", "").replaceAll("\\]", "").trim();
				requestRequisitionSkillDto.setKeyInterviewersTwo(round2);
			}
			
		}
		
		return rrsDTO;
		
	}
	
	
	public List<RequestRequisitionSkill> getSkillRequestReport(Integer id, String role) {
		List<RequestRequisitionSkill> requestReportList = new ArrayList<RequestRequisitionSkill>();
		requestReportList = requestReportDao.getSkillRequestReport(id, role);
		return requestReportList;
	}

	public boolean reduceFullfilledSkillRequest(int id) {
		boolean result = requestReportDao.reduceFullfilledSkillRequest(id);
		return result;
	}

	public List<RequestRequisitionResourceStatus> getSkillResourceStatusList() {

		List<RequestRequisitionResourceStatus> requestReportList = new ArrayList<RequestRequisitionResourceStatus>();
		requestReportList = requestReportDao.getSkillResourceStatusList();
		return requestReportList;

	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public boolean delete(int id) {
		return requestReportDao.delete(id);
	}

	public boolean saveOrupdate(RequestRequisitionSkill t) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<RequestRequisitionSkill> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<RequestRequisitionSkill> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> downloadSelectedResume(Integer id, String resourceType) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != resourceType && resourceType.equalsIgnoreCase("Internal")) {
			map = (Map<String, Object>) requestReportDao.getResumeFromResourceById(id);
		} else {
			map = (Map<String, Object>) requestReportDao.getResumeById(id);
		}
		byte[] file = (byte[]) map.get("file");

		Blob blobUploadFile = null;
		byte[] uploadResume = null;

		if (null != file) {
			uploadResume = file;
		}
		try {
			if ((null != uploadResume && !uploadResume.equals(""))) {
				blobUploadFile = new javax.sql.rowset.serial.SerialBlob(uploadResume);
			}
		} catch (Exception e) {

		}
		map.put("file", blobUploadFile);
		return map;

	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> downloadSelectedBUHApproval(Integer requestId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map = (Map<String, Object>) requestRequisitionDao.getBUHApprovalById(requestId);
		
		byte[] file = (byte[]) map.get("file");

		Blob blobUploadFile = null;
		byte[] buhApprovalFile = null;

		if (null != file) {
			buhApprovalFile = file;
		}
		try {
			if ((null != buhApprovalFile && !buhApprovalFile.equals(""))) {
				blobUploadFile = new javax.sql.rowset.serial.SerialBlob(buhApprovalFile);
			}
		} catch (Exception e) {

		}
		map.put("file", blobUploadFile);
		return map;

	}

	public RequestRequisitionDashboardDTO getRequestRequisitionDashboard(Integer id, String role) throws Exception {
		
		RequestRequisitionDashboardDTO requestRequisitionDashboardDTO = new RequestRequisitionDashboardDTO();
		List<RequestRequisitionSkill> requestRequisitionSkillDomainList = new ArrayList<RequestRequisitionSkill>();
		List<RequestRequisitionSkillDTO> requestRequisitionSkillDTOList = new ArrayList<RequestRequisitionSkillDTO>();
		List<Resource> activeUserList = new ArrayList<Resource>();
		List<PDLEmailGroup> pdlGroups = new ArrayList<PDLEmailGroup>();
		
		try {
			requestRequisitionSkillDomainList = requestReportDao.getSkillRequestReport(id, role);
			requestRequisitionSkillDTOList = dozerMapperUtility.requestRequisitionDomainToDTOList(requestRequisitionSkillDomainList,false);
		
			activeUserList = resourceService.findActiveResources();
			pdlGroups = pdlEmailGroupService.getPdlEmails();
			
			requestRequisitionDashboardDTO.setRequestRequisitionDashboardList(requestRequisitionSkillDTOList);
			requestRequisitionDashboardDTO.setActiveUserList(activeUserList);
			
			requestRequisitionDashboardDTO.setPdlGroup(pdlGroups);
			requestRequisitionDashboardDTO.setCustomerList(customerService.findAll());

			//setPositionStatus(requestRequisitionDashboardDTO);
			
			//return requestRequisitionDashboardDTO;
		
		} catch (Exception e) {
			logger.error("-------error in getRequestRequisitionDashboard ------", e);
		}

		return requestRequisitionDashboardDTO;
	}

	/**
	 * Created by Vidhika Jain
	 * Method to set Position Status on  dashboard
	*/
	private void setPositionStatus(RequestRequisitionDashboardDTO requestRequisitionDashboardDTO) {
		List<RequestRequisitionResource> requestRequisitionResourceList = requestRequisitionResourceDao.getAllSelectedRequestRequisitionResourceList();
		for (RequestRequisitionSkillDTO skill : requestRequisitionDashboardDTO.getRequestRequisitionDashboardList()) {
			int selected = 0;
			for(RequestRequisitionResource requestRequisitionResource : requestRequisitionResourceList){
				if(requestRequisitionResource.getRequestRequisitionSkill().getId() == skill.getId()){
					selected++;
				}
			}
			
			String noOfPositions = skill.getRemaining();
			
			
			int totalStatus = 0;
			
			if(skill.getClosed() != null){
				totalStatus = skill.getClosed(); 
			}
			if(skill.getLost() != null){
				totalStatus = totalStatus + skill.getLost();
			}
			if(skill.getHold() != null){
				totalStatus =  totalStatus + skill.getHold();
			}

			
			if(noOfPositions != null){
				int totalNoOfPosition = Integer.parseInt(noOfPositions);
				if(totalNoOfPosition == totalStatus){
					skill.setRecordStatus("Inactive");
				}else{
					skill.setRecordStatus("Active");
				}
			}
			
			if (skill.getClosed() != null && noOfPositions.equals(String.valueOf(skill.getClosed()))) {
				skill.setStatus("Closed");
			} else if(skill.getLost() != null && noOfPositions.equals(String.valueOf(skill.getLost()))){
				skill.setStatus("Lost");
			}else if (skill.getHold() != null && noOfPositions.equals(String.valueOf(skill.getHold()))) {
				skill.setStatus("Hold");
			} else if (selected > 0 && noOfPositions.equals(String.valueOf(selected))) {
				skill.setStatus("Fulfilled");
			}			
			
			if(skill.getStatus() == null){
				boolean isOnlyOpen = true;
				StringBuilder status = new StringBuilder();
				if((skill.getHold() != null && skill.getHold() > 0) || (skill.getLost() != null && skill.getLost() > 0)){
				if(skill.getOpen() != null && skill.getOpen() > 0){
					status.append(" Open : "+skill.getOpen());
					status.append(" ");
				}
				if(skill.getClosed() != null && skill.getClosed() > 0){
					isOnlyOpen = false;
					status.append(" Closed : "+skill.getClosed());
					status.append(" ");
				}
				if(skill.getHold() != null && skill.getHold() > 0){
					isOnlyOpen = false;
					status.append(" Hold : "+skill.getHold());
					status.append(" ");
				}
				if(skill.getLost() != null && skill.getLost() > 0){
					isOnlyOpen = false;
					status.append(" Lost : "+skill.getLost());
					status.append(" ");
				}
				if(selected > 0){
					isOnlyOpen = false;
					status.append(" Selected : "+selected);
				}
				}
				if(isOnlyOpen){
					skill.setStatus(" Open");
				}else{
					skill.setStatus(status.toString());
				}
			}
		}
	}

	public RequestRequisitionResourceFormDTO getRequestRequisitionResourceDTO(Integer requestRequisitionSkillId) {
		
		RequestRequisitionResourceFormDTO requestRequisitionResourceDTO = new RequestRequisitionResourceFormDTO();

		List<RequestRequisitionResource> requestRequisitionResourceList = requestRequisitionResourceDao.getDataForAddDeleteResourcebySkillRequestID(requestRequisitionSkillId);
		
		List<Resource> bgAdmin = resourceService.findResourcesByBGADMINROLE();

		List<Resource> Admin = resourceService.findResourcesByADMINROLE();

		List<Resource> mailList = new ArrayList<Resource>();
		mailList.addAll(bgAdmin);
		mailList.addAll(Admin);

		List<AllocationType> allocationTypeList = allocationTypeService.findAll();

		ArrayList<Integer> resIdToSendMailTo = new ArrayList<Integer>(); // request
																			// Sent
																			// to
		ArrayList<Integer> resIdToNotifyMailTo = new ArrayList<Integer>();
		ArrayList<Integer> pdlList = new ArrayList<Integer>();

		ArrayList<String[]> sendMailTo = new ArrayList<String[]>();
		ArrayList<String[]> requestMailTo = new ArrayList<String[]>();

		List<RequestRequisitionSkill> requestRequisitionSkills = requestReportDao.getRequestRequisitionSkillById(requestRequisitionSkillId);
		RequestRequisitionSkill skill = requestRequisitionSkills.get(0);
		sendMailTo.add(skill.getRequestRequisition().getSentMailTo().split(","));
		requestMailTo.add(skill.getRequestRequisition().getNotifyMailTo().split(","));

		for (String[] string : sendMailTo) {
			for (String stringToInteger : string) {
				if (null != stringToInteger && !"".equals(stringToInteger)) {
					resIdToSendMailTo.add(Integer.parseInt(stringToInteger.trim()));
				}
			}
		}

		for (String[] string : requestMailTo) {
			for (String stringToInteger : string) {
				if (null != stringToInteger && !"".equals(stringToInteger)) {
					resIdToNotifyMailTo.add(Integer.parseInt(stringToInteger.trim()));
				}
			}
		}
		if (skill.getRequestRequisition().getPdlEmailIds() != null && !skill.getRequestRequisition().getPdlEmailIds().isEmpty()) {
			for (String pdl : skill.getRequestRequisition().getPdlEmailIds().split(",")) {
				if (null != pdl && !"".equals(pdl)) {
					if(pdl.contains("-")){ // check for previously added pdl in id-emailid format
						String pdlID = pdl.substring(0, 1);
						pdlList.add(Integer.parseInt(pdlID.trim()));
					}else{
						pdlList.add(Integer.parseInt(pdl.trim()));
					}
				}
			}
		}
		List<String> resEmailsToSendMail = new ArrayList<String>();
		if (null != resIdToSendMailTo && resIdToSendMailTo.size() > 0) {
			resEmailsToSendMail = resourceService.findEmailById(resIdToSendMailTo);
		}
		List<String> resEmailsNotifyTo = new ArrayList<String>();
		if (null != resIdToNotifyMailTo && resIdToNotifyMailTo.size() > 0) {
			resEmailsNotifyTo = resourceService.findEmailById(resIdToNotifyMailTo);
		}
		List<String> pdlGroup = new ArrayList<String>();
		if (null != pdlList && pdlList.size() > 0) {
			//pdlGroup = resourceService.findEmailById(pdlList);
			pdlGroup = pdlEmailGroupService.findPdlByIds(pdlList);
		}

		requestRequisitionResourceDTO.setEmailsToNotify(resEmailsNotifyTo);
		requestRequisitionResourceDTO.setEmailsToRequestTo(resEmailsToSendMail);
		requestRequisitionResourceDTO.setPdlGroup(pdlGroup);

		requestRequisitionResourceDTO.setMailingList(mailList);

		requestRequisitionResourceDTO.setRequestRequisitionResourceStatusList(requestReportDao.getSkillResourceStatusList());

//		requestRequisitionResourceDTO.setActiveUserList(resourceService.findActiveResources());

		requestRequisitionResourceDTO.setRequestRequisitionResources(requestReportDao.getRequestRequisitionSkillById(requestRequisitionSkillId));

		requestRequisitionResourceDTO.setRequisitionResourceList(requestRequisitionResourceList);

		requestRequisitionResourceDTO.setAllocationTypeList(allocationTypeList);

		return requestRequisitionResourceDTO;
	}

	@Transactional
	public Integer saveSkillRequestReport(RequestRequisitionDashboardInputDTO dto) throws Exception {

		Integer skillRequestId = 0;
		RequestRequisitionSkill skillRequest = new RequestRequisitionSkill();
		RequestRequisitionResource skillResource;

		// resourceCount = resourceList.size() ; // total resources alloted

		skillRequest.setFulfilled(dto.getFullfillResLen());
		skillRequest.setId(dto.getSkillReqId());
		skillRequest.setComments(dto.getComments());

		// List<RequestRequisitionSkill> skillRequests = new
		// ArrayList<RequestRequisitionSkill>();
		List<RequestRequisitionResource> skillResources = new ArrayList<RequestRequisitionResource>();
		try {
			for (ResourceFileDTO resourceFileDTO : dto.getResourceFileDTO()) {
				skillResource = new RequestRequisitionResource();
				if (null != resourceFileDTO.getResourceIds() && !"".equals(resourceFileDTO.getResourceIds()) && "I".equals(resourceFileDTO.getResourceType())) {
					Resource resource = resourceDao.findByEmployeeId(Integer.parseInt(resourceFileDTO.getResourceIds()));

					if (null != resourceFileDTO.getFile() && null != resourceFileDTO.getFile().getBytes()) {
						resource.setUploadResume(resourceFileDTO.getFile().getBytes());
						resource.setUploadResumeFileName(resourceFileDTO.getFile().getOriginalFilename());
						resourceDao.updateResumeOfResource(resource);
					}
					else
					{
						if(resource.getUploadResume() !=null && resource.getUploadResume().length>0){
						 ByteConvHelper byteConvHelper=new ByteConvHelper(resource.getUploadResume(),resource.getUploadResumeFileName());
						 resourceFileDTO.setFile(byteConvHelper);
						}
					}
					skillResource.setResource(resource);

				} else if (resourceFileDTO.getExternalResourceName() != null && !"".equals(resourceFileDTO.getExternalResourceName()) && "E".equals(resourceFileDTO.getResourceType())) {
					skillResource.setExternalResourceName(resourceFileDTO.getExternalResourceName());
					skillResource.setLocation(resourceFileDTO.getLocation());
					skillResource.setNoticePeriod(resourceFileDTO.getNoticePeriod());
					skillResource.setEmailId(resourceFileDTO.getEmailId());
					skillResource.setTotalExperience(resourceFileDTO.getTotalExperience());
					skillResource.setContactNumber(resourceFileDTO.getContactNumber());
					skillResource.setSkills(resourceFileDTO.getSkills());
					skillResource.setResumeFileName(resourceFileDTO.getFile().getOriginalFilename());
					skillResource.setUploadResume(resourceFileDTO.getFile().getBytes());
					skillResource.setUploadTacFileName(resourceFileDTO.getTacFile().getOriginalFilename());
					skillResource.setUploadTacFile(resourceFileDTO.getTacFile().getBytes());
				}

				skillResource.setRequestRequisitionSkill(skillRequest);
				skillResources.add(skillResource);

			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		List<RequestRequisitionSkill> requestRequisitionResources = requestReportDao.getRequestRequisitionSkillById(dto.getSkillReqId());
		requestRequisitionResourceDao.saveRequestRequisitionResource(skillResources, skillRequest,requestRequisitionResources);
		skillRequestId = requestReportDao.saveSkillRequestReport(skillRequest, dto.getSkillReqId(), skillResources);
		return skillRequestId;

	}

	@SuppressWarnings("null")
	@Transactional
public void sendEmailForSkillRequestReport(RequestRequisitionDashboardInputDTO dto, Integer skillRequestId, List<ResourceStatusDTO> resourceStatuslistOld, List<ResourceStatusDTO> resourceStatusNewList, RequestRequisitionResourceFormDTO requestRequisitionResourceFormDTO) throws Exception {

		// code for sending mail
		List<RequestRequisitionSkill> list = requestReportDao.getRequestRequisitionSkillById(skillRequestId);
		List<MultipartFile> fileList = new ArrayList<MultipartFile>();
		RequestRequisitionSkill skill = list.get(0);
		RequestRequisition requestRequisition = skill.getRequestRequisition();
		List<String> ccMail = new ArrayList<String>();
		List<String> toMail = new ArrayList<String>();
		ArrayList<String[]> stringArray = new ArrayList<String[]>();
		ArrayList<String[]> pdlArray = new ArrayList<String[]>();
		ArrayList<String[]> toMailArray = new ArrayList<String[]>();
		if (null != dto.getNotifyTo()) {
			if (requestRequisition.getNotifyMailTo() != null && !"".equals(requestRequisition.getNotifyMailTo())) {
				stringArray.add(requestRequisition.getNotifyMailTo().split(","));
			} else {
				stringArray.add(requestRequisition.getSentMailTo().split(","));
			}
		}
		if (null != dto.getRequestSentTo()) {
			toMailArray.add(requestRequisition.getSentMailTo().split(","));
		}
		if (null != dto.getPdl()) {
			if(!requestRequisition.getPdlEmailIds().isEmpty() && requestRequisition.getPdlEmailIds()!=""){
				pdlArray.add(skill.getRequestRequisition().getPdlEmailIds().split(","));
			} 
			//stringArray.add(skill.getRequestRequisition().getPdlEmailIds().split(","));
		}
		if (dto.getExtraMailTo() != null && dto.getExtraMailTo().size() > 0) {
			String[] arr = dto.getExtraMailTo().toArray(new String[dto.getExtraMailTo().size()]);
			stringArray.add(arr);
		}
		if (stringArray != null && stringArray.size() > 0) {
			ArrayList<Integer> intArray = new ArrayList<Integer>();
			for (int i = 0; i < stringArray.size(); i++) {
				String[] numberAsStringArr = stringArray.get(i);
				for (int j = 0; j < numberAsStringArr.length; j++) {
					String numberAsString = numberAsStringArr[j].trim();
					intArray.add(Integer.parseInt(numberAsString));
				}
			}
			intArray.add(skill.getRequestRequisition().getResource().getEmployeeId());
			ccMail = (resourceService.findEmailById(intArray));
		}
		
		
		if (pdlArray != null && pdlArray.size() > 0) { //For Pdl Emails : Samiksha
			//ArrayList<String> pdlemail = new ArrayList<String>();
			ArrayList<Integer> pdlemail = new ArrayList<Integer>();
			for (int i = 0; i < pdlArray.size(); i++) {
				String[] pdlEmailwithid = pdlArray.get(i);
				for (int j = 0; j < pdlEmailwithid.length; j++) {
					String numberAsString = pdlEmailwithid[j].trim();
					
					pdlemail.add(Integer.parseInt(numberAsString.substring(numberAsString.indexOf("-")+1, numberAsString.length())));
				}
			}
			
			//ccMail.addAll(pdlemail);
			ccMail.addAll(pdlEmailGroupService.findPdlByIds(pdlemail));
		}

		if (toMailArray != null && toMailArray.size() > 0) {
			ArrayList<Integer> intToMailArray = new ArrayList<Integer>();
			for (int i = 0; i < toMailArray.size(); i++) {
				String[] numberAsStringArr = toMailArray.get(i);
				for (int j = 0; j < numberAsStringArr.length; j++) {
					String numberAsString = numberAsStringArr[j].trim();
					if(numberAsString!=null && numberAsString.trim().length()>0){
					intToMailArray.add(Integer.parseInt(numberAsString));
					}
				}
			}
			if(intToMailArray != null && intToMailArray.size()>0){
				toMail.addAll(resourceService.findEmailById(intToMailArray));
			}
		}
		
		String[] toMailInterviewers = null;
		String[] sentMailCCTo = null;
		String[] tomailIDsList = null;
		String mailToList = null;
		
		if (ccMail != null && !ccMail.isEmpty()) { //empty check : Samiksha
			sentMailCCTo = new HashSet<String>(ccMail).toArray(new String[0]);
		}
		

		/*if (skill.getKeyInterviewersOne() != null && skill.getKeyInterviewersTwo() == null) {
			mailToList = skill.getKeyInterviewersOne();
			toMailInterviewers = mailToList.split(",");
			Collections.addAll(toMail, toMailInterviewers);
			tomailIDsList = new HashSet<String>(Arrays.asList(tomailIDsList)).toArray(new String[0]);
		} else if (skill.getKeyInterviewersOne() == null && skill.getKeyInterviewersTwo() != null) {
			mailToList = skill.getKeyInterviewersTwo();
			toMailInterviewers = mailToList.split(",");
			Collections.addAll(toMail, toMailInterviewers);
			tomailIDsList = new HashSet<String>(Arrays.asList(tomailIDsList)).toArray(new String[0]);
		} else if (skill.getKeyInterviewersOne() != null && skill.getKeyInterviewersTwo() != null && !"".equals(skill.getKeyInterviewersOne()) && !"".equals(skill.getKeyInterviewersTwo())) { //blank string check : Samiksha
			mailToList = skill.getKeyInterviewersOne() + ", " + skill.getKeyInterviewersTwo();
			toMailInterviewers = mailToList.split(",");
			Collections.addAll(toMail, toMailInterviewers);
		}*/
		
		if(toMail != null && !toMail.isEmpty()){ //toMail list created : Samiksha
			tomailIDsList = new HashSet<String>(toMail).toArray(new String[0]);
		}

		Integer currentLoggedInUserId = UserUtil.getCurrentResource().getEmployeeId();
		Resource currentResource = resourceService.find(currentLoggedInUserId);
		String currentBU = currentResource.getCurrentBuId().getParentId().getName() + "-" + currentResource.getCurrentBuId().getName();
		String sendMailFrom = currentResource.getEmailId();
		String sender = currentResource.getFirstName() + " " + currentResource.getLastName();		
		Map<String, Object> model = new HashMap<String, Object>();
		
		if(tomailIDsList != null){
			for (String tomail : tomailIDsList) {
				logger.info("---------to mails in resource submission :-----------" + tomail);
			}
		}else{
			tomailIDsList = new String[1];
			tomailIDsList[0]=requestRequisition.getResource().getEmailId();
		}
		
		if(sentMailCCTo != null){
			for (String ccmail : sentMailCCTo) {
				logger.info("---------cc mail in resource submission :------------" +ccmail);	
			}
		}else{
			sentMailCCTo = new String[1];
			sentMailCCTo[0]= requestRequisition.getResource().getEmailId();
		}
		
		String groupName = ""; //check for group name
		if(requestRequisition.getGroup()!= null && "".equals(requestRequisition.getGroup().getCustomerGroupName())){
			groupName = requestRequisition.getGroup().getCustomerGroupName();
		}
		String indentBy=requestRequisition.getRequestor().getFirstName()+" "+requestRequisition.getRequestor().getLastName();		
		String rrfBgBu = requestRequisition.getProjectBU();
		List<ResourceStatusDTO> nowAddedresourceStatuslist = new ArrayList<ResourceStatusDTO>(); ;
		nowAddedresourceStatuslist = comareListToGetNewReocords(resourceStatuslistOld, resourceStatusNewList, nowAddedresourceStatuslist);	
		String sapPdlOrNonSap = Constants.RMG_PDL_EMAIL;
		String requirementArea = skill.getRequirementArea();
		if(requirementArea!=null) {
			if(requirementArea.equalsIgnoreCase(Constants.NON_SAP)) {
				requirementArea = Constants.NON_SAP_STRING;
			}else {
				sapPdlOrNonSap = Constants.RMG_ERP_PDL_EMAIL;
			}
		}else {
			requirementArea = Constants.NON_SAP_STRING;
		}
		resourceHelper.setEmailContentForInternalRequest(model, tomailIDsList, sentMailCCTo, sender, requestRequisition.getProject().getProjectName(), 
				currentBU, sapPdlOrNonSap,	dto.getComments(), requestRequisition.getCustomer().getCustomerName(), skill.getRequirementId(),
				skill.getNoOfResources(), skill.getDesignation().getDesignationName(), indentBy, requestRequisition.getAmJobCode(), 
				groupName, resourceStatuslistOld, nowAddedresourceStatuslist , requestRequisitionResourceFormDTO,
				requestRequisitionService.findRequestRequisitionSkillsByRequestRequisitionId(requestRequisition.getId()),
				rrfBgBu, requestRequisition.getSuccessFactorId(), requirementArea);
		for (ResourceFileDTO resourceFileDTO : dto.getResourceFileDTO()) {
			if(resourceFileDTO.getResourceType().equalsIgnoreCase("I")){
				if(resourceFileDTO.getFile()!=null && !resourceFileDTO.getFile().isEmpty()){
					fileList.add(resourceFileDTO.getFile());
				}
			} else {
				if((resourceFileDTO.getFile()!=null && !resourceFileDTO.getFile().isEmpty()) || (resourceFileDTO.getTacFile()!=null && !resourceFileDTO.getTacFile().isEmpty())){
					fileList.add(resourceFileDTO.getFile());
					fileList.add(resourceFileDTO.getTacFile());  //Was implemented earlier!! Adding the same code again : Samiksha. 
				}
			}
			
		}
		logger.info("-----------total number of files including tac--------------"+  fileList.size());
		
		MultipartFile[] resume = fileList.toArray(new MultipartFile[fileList.size()]);
		emailHelper.sendEmail(model, tomailIDsList, sentMailCCTo, resume);

	}

	private List<ResourceStatusDTO> comareListToGetNewReocords(List<ResourceStatusDTO> resourceStatuslistOld, List<ResourceStatusDTO> resourceStatuslistNew, List<ResourceStatusDTO> nowAddedresourceStatuslist) {
		
		for (ResourceStatusDTO newResourceStatusDTO : resourceStatuslistNew) {
	        boolean found=false;
	        for (ResourceStatusDTO oldResourceStatusDTO : resourceStatuslistOld) {
	        	if((newResourceStatusDTO.getResourceType().equals("External") && newResourceStatusDTO.getEmployeeId()==null)		        			
	        			&& (newResourceStatusDTO.getAllocationStartDate().equals(oldResourceStatusDTO.getAllocationStartDate()))
	            		&& (newResourceStatusDTO.getAllocationType().equals(oldResourceStatusDTO.getAllocationType()))
	            		&& (newResourceStatusDTO.getProfileStaus().equals(oldResourceStatusDTO.getProfileStaus()))
	            		&& (newResourceStatusDTO.getResourceId().equals(oldResourceStatusDTO.getResourceId()))
	            		&& (newResourceStatusDTO.getResourceName().equals(oldResourceStatusDTO.getResourceName()))
	            		&& (newResourceStatusDTO.getResourceType().equals(oldResourceStatusDTO.getResourceType()))) {
	        		 found=true;
		                break;		        		
	        	}
	        	else if ((newResourceStatusDTO.getResourceType().equalsIgnoreCase("Internal") && newResourceStatusDTO.getEmployeeId()!=null)
	        			&&(newResourceStatusDTO.getEmployeeId().equals(oldResourceStatusDTO.getEmployeeId())) && (newResourceStatusDTO.getAllocationStartDate().equals(oldResourceStatusDTO.getAllocationStartDate()))
	            		&& (newResourceStatusDTO.getAllocationType().equals(oldResourceStatusDTO.getAllocationType()))
	            		&& (newResourceStatusDTO.getProfileStaus().equals(oldResourceStatusDTO.getProfileStaus()))
	            		&& (newResourceStatusDTO.getResourceId().equals(oldResourceStatusDTO.getResourceId()))
	            		&& (newResourceStatusDTO.getResourceName().equals(oldResourceStatusDTO.getResourceName()))
	            		&& (newResourceStatusDTO.getResourceType().equals(oldResourceStatusDTO.getResourceType()))) {
	                found=true;
	                break;
	            }
	        }
	        if(!found){		           
				nowAddedresourceStatuslist.add(newResourceStatusDTO);
	        }
	        
	    }
		return nowAddedresourceStatuslist;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public void updateResourceRequestWithStatus(int requestRequisitionSkillId, List<ResourceStatusDTO> resourceStatuslist) {
		List<ResourceStatusDTO> changedStatusResourceList=new ArrayList<ResourceStatusDTO>();
		int previousStatusOfResource = 0;
		int currentStatusOfResource = 0;
		String previousStatusOfResourceString = "";
		String currentStatusOfResourceString = "";
		ResourceStatusDTO resourceStatusDTOObj = null;
		Map<Integer, ResourceStatusDTO> resourceMap = new HashMap<Integer, ResourceStatusDTO>();
		RequestRequisitionResourceStatus reqStatus = new RequestRequisitionResourceStatus();
		List<String> resourceIds=new ArrayList<String>();
		String[] resources = null;
		String list;
		//Initialize status count with 0
		Map<String, Integer> statusCountMap = new HashMap<String, Integer>();
		statusCountMap.put(Constants.SUBMITTEDSTATUS, 0);
		statusCountMap.put(Constants.REJECTEDSTATUS, 0);
		
		for (ResourceStatusDTO dto : resourceStatuslist) {
			resourceMap.put(dto.getResourceId(), dto);
		}
		List<RequestRequisitionResource> requestRequisitionResourceList = requestRequisitionResourceDao.getDataForAddDeleteResourcebySkillRequestID(requestRequisitionSkillId);

		for (RequestRequisitionResource requestRequisitionResource : requestRequisitionResourceList) {
			ResourceStatusDTO dto = resourceMap.get(requestRequisitionResource.getId());
			setDatesForProfileStatus(dto,requestRequisitionResource);
			if (null != dto.getProfileStaus() && dto.getProfileStaus() != 0) {

				//Code to Check status Submitted to AM team and Rejected by POC - Start
				
				previousStatusOfResource = requestRequisitionResource.getRequestRequisitionResourceStatus().getId();
				previousStatusOfResourceString =requestRequisitionResourceStatusService.findById(previousStatusOfResource).getStatusName(); 
				currentStatusOfResource = dto.getProfileStaus();
				currentStatusOfResourceString = requestRequisitionResourceStatusService.findById(currentStatusOfResource).getStatusName();
				System.out.println("Previous status of Resource :: " + previousStatusOfResourceString);
				System.out.println("Current status of Resource :: " + currentStatusOfResourceString);
				/*if((currentStatusOfResource==22 && previousStatusOfResource!=22) || (currentStatusOfResource==19 && previousStatusOfResource!=19)){
					//add dto to changedList
					changedStatusResourceList.add(dto);
				}*/
				//Code to Check status Submitted to AM team and Rejected by POC - End
				
				reqStatus = requestRequisitionResourceDao.getRequestRequisitionResourceStatusById(dto.getProfileStaus());
				
				/*if(reqStatus.getStatusName().trim().equals(RequestRequisitionStatusConstants.SUBMITTED_TO_AM_TEAM.toString().trim()) && dto.getProfileStaus()!=requestRequisitionResource.getRequestRequisitionResourceStatus().getId()) {
					RequestRequisitionSkill requisitionSkill = requestRequisitionSkillDao.findRequestRequisitionSkillBySkillId(requestRequisitionSkillId);
					Set<String[]> emailIds = new HashSet<String[]>();
					String [] emailTo = new String[1];
					emailIds.add(emailTo);
					Resource resource = userUtil.getLoggedInResource();
						if(null!=requisitionSkill.getRequestRequisition().getGroup()requisitionSkill.getRequestRequisition().getCustomer().getCustomerName())
						{
							emailTo[0] = requisitionSkill.getRequestRequisition().getGroup().getCustGroupemailId();
						}else {
							emailTo[0] = requisitionSkill.getRequestRequisition().getCustomer().getCustomerEmail();
						}
						sendEmail(resources, emailTo, Integer.toString(requestRequisitionSkillId), resource, Constants.SUBMITTED_TO_AM_TEAM); // template needs to be updated wrt to the flag.
				}*/
				requestRequisitionResource.setRequestRequisitionResourceStatus(reqStatus);
				list= requestRequisitionResource.getId().toString();
				resourceIds.add(list);
				resources= resourceIds.toArray(new String[0]);
				
				
			}

				if(requestRequisitionResource.getAllocationDate()!=null){
					//continue;
					if((null != dto.getAllocationStartDate() && dto.getAllocationStartDate().trim().length()>0)) {
						if(dto.getAllocationStartDate().trim().matches(Constants.ALLOCATION_DATE_PATTERN)){
							requestRequisitionResource.setAllocationDate(DateUtil.getDate(dto.getAllocationStartDate(),Constants.DATE_PATTERN_4));
						}else{
					requestRequisitionResource.setAllocationDate(DateUtil.getDate(dto.getAllocationStartDate()));
						}
				}else
						continue;
				}else{
					if (null != dto.getAllocationStartDate() && !dto.getAllocationStartDate().equals("")) {
						//requestRequisitionResource.setAllocationDate(DateUtil.getDate(dto.getAllocationStartDate()));
						if((null != dto.getAllocationStartDate() && dto.getAllocationStartDate().trim().length()>0)) {
							if(dto.getAllocationStartDate().trim().matches(Constants.ALLOCATION_DATE_PATTERN)){
								requestRequisitionResource.setAllocationDate(DateUtil.getDate(dto.getAllocationStartDate(),Constants.DATE_PATTERN_4));
							}else{
						requestRequisitionResource.setAllocationDate(DateUtil.getDate(dto.getAllocationStartDate()));
							}
					}
						
					}
				}
			
				if(dto.getInterviewDate()!=null && dto.getInterviewDate().trim().length()>0){
					if(dto.getInterviewDate().trim().matches(Constants.INTERVIEW_DATE_PATTERN)){
						requestRequisitionResource.setInterviewDate(DateUtil.getDate(dto.getInterviewDate(),Constants.DATE_PATTERN_4));
					}else{
						requestRequisitionResource.setInterviewDate(DateUtil.getDate(dto.getInterviewDate()));
					}
				}
				logger.debug("currentStatusOfResource :: " + currentStatusOfResourceString + " And previousStatusOfResource :: " + previousStatusOfResourceString);
				if ((currentStatusOfResourceString.equalsIgnoreCase(Constants.SUBMITTEDTOAMTEAMSTATUS)
						&& !previousStatusOfResourceString.equalsIgnoreCase(Constants.SUBMITTEDTOAMTEAMSTATUS))) {
						//increase count for submitted to am team status
					statusCountMap.put(Constants.SUBMITTEDSTATUS, 1);
					}
				if ((currentStatusOfResourceString.equalsIgnoreCase(Constants.REJECTEDBYPOCSTATUS)
								&& !previousStatusOfResourceString.equalsIgnoreCase(Constants.REJECTEDBYPOCSTATUS))) {
					//increase count for rejected by POC status
					statusCountMap.put(Constants.REJECTEDSTATUS, 1);
					}
			if ((currentStatusOfResourceString.equalsIgnoreCase(Constants.SUBMITTEDTOAMTEAMSTATUS)
					&& !previousStatusOfResourceString.equalsIgnoreCase(Constants.SUBMITTEDTOAMTEAMSTATUS))
					|| (currentStatusOfResourceString.equalsIgnoreCase(Constants.REJECTEDBYPOCSTATUS)
							&& !previousStatusOfResourceString.equalsIgnoreCase(Constants.REJECTEDBYPOCSTATUS))) {
					//add dto to changedList
					resourceStatusDTOObj = getResourceStatusDTO(requestRequisitionResource);
					changedStatusResourceList.add(resourceStatusDTOObj);
				}
		}
		updateCounterByStatusChange(requestRequisitionResourceList, reqStatus, requestRequisitionSkillId, resourceStatuslist);
		requestRequisitionResourceDao.updateResourceRequestWithStatus(requestRequisitionResourceList);
		
		//Shoot email if changedStatusResourceList (for status Submitted to AM team and Rejected by POC) is not empty
		if(changedStatusResourceList!=null && !changedStatusResourceList.isEmpty()) {
			sendEmailForRRFResourceStatusEmail(requestRequisitionSkillId,changedStatusResourceList,statusCountMap);		
		}
		
		//Code to Check status Submitted to AM team and Rejected by POC - End
		
	}
	

	private ResourceStatusDTO getResourceStatusDTO(RequestRequisitionResource requestRequisitionResource) {
		logger.info("RequestRequisitionDashboardServiceImpl: getResourceStatusDTO method starts");
		ResourceStatusDTO resourceStatusDTOObj =null;
		try {
			resourceStatusDTOObj = new ResourceStatusDTO();

			if(null!=requestRequisitionResource.getExternalResourceName()){
				resourceStatusDTOObj.setResourceType("External");
				resourceStatusDTOObj.setEmailId(requestRequisitionResource.getEmailId());
				resourceStatusDTOObj.setTotalExperience(requestRequisitionResource.getTotalExperience());
				resourceStatusDTOObj.setContactNumber(requestRequisitionResource.getContactNumber());
				resourceStatusDTOObj.setSkills(requestRequisitionResource.getSkills());
				resourceStatusDTOObj.setResourceName(requestRequisitionResource.getExternalResourceName());
			}else{
				Resource resourceInternal = requestRequisitionResource.getResource();
				
				resourceStatusDTOObj.setResourceType("Internal");
				
				resourceStatusDTOObj.setEmployeeId(null!= resourceInternal ? requestRequisitionResource.getResource().getEmployeeId(): 0);
				resourceStatusDTOObj.setResourceName(resourceInternal.getFirstName() + " " + resourceInternal.getLastName());
				
				//Resource resourceInternal = resourceService.findResourcesByYashEmpIdEquals(resource.getResource().getYashEmpId().toString());
				if(!StringUtils.isEmpty(resourceInternal.getEmailId()))
					resourceStatusDTOObj.setEmailId(resourceInternal.getEmailId());
				if(resourceInternal.getTotalExper()!=0)
					resourceStatusDTOObj.setTotalExperience(Double.toString(resourceInternal.getTotalExper()));
				if(!StringUtils.isEmpty(resourceInternal.getContactNumber()));
				resourceStatusDTOObj.setContactNumber(resourceInternal.getContactNumber());
					Competency competency=resourceInternal.getCompetency();
				if(competency!=null)
				{
					resourceStatusDTOObj.setSkills(competency.getSkill());
				}
			}
			
			resourceStatusDTOObj.setLocation(requestRequisitionResource.getLocation()!=null ? requestRequisitionResource.getLocation() : "NA");
			resourceStatusDTOObj.setNoticePeriod(requestRequisitionResource.getNoticePeriod()!=null ? requestRequisitionResource.getNoticePeriod() : "NA");
			resourceStatusDTOObj.setResourceId(requestRequisitionResource.getId()!=null ? requestRequisitionResource.getId() : 0);
			resourceStatusDTOObj.setAllocationType(null != requestRequisitionResource.getAllocation() ? requestRequisitionResource.getAllocation().getId(): 0);
			resourceStatusDTOObj.setProfileStaus(null !=requestRequisitionResource.getRequestRequisitionResourceStatus() ? requestRequisitionResource.getRequestRequisitionResourceStatus().getId() : 0);
			resourceStatusDTOObj.setProfileStausName(null !=requestRequisitionResource.getRequestRequisitionResourceStatus() ? requestRequisitionResource.getRequestRequisitionResourceStatus().getStatusName() : "NA");
			resourceStatusDTOObj.setAllocationStartDate(DateUtil.getDateInDD_MMM_yyyyFormat(requestRequisitionResource.getAllocationDate()));
			resourceStatusDTOObj.setInterviewDate(DateUtil.getDateInDD_MMM_yyyyFormat(requestRequisitionResource.getInterviewDate()));
			logger.debug("RequestRequisitionDashboardServiceImpl: getResourceStatusDTO method resourceStatusDTOObj.ProfileStausName " +resourceStatusDTOObj.getProfileStausName());
		
		}catch(Exception ex) {
			logger.error("Exception occured RequestRequisitionDashboardServiceImpl getResourceStatusDTO: " + ex.getMessage());
		}
		logger.info("RequestRequisitionDashboardServiceImpl: getResourceStatusDTO method ends");
		return resourceStatusDTOObj;
	}

	/**
	 * API use to set the dates accordingly profile status change
	 * @param dto
	 * @param requestRequisitionResource
	 */
	private void setDatesForProfileStatus(ResourceStatusDTO dto, RequestRequisitionResource requestRequisitionResource){
		if(dto.getProfileStaus() !=null && dto.getProfileStaus() != requestRequisitionResource.getRequestRequisitionResourceStatus().getId()){
			if(dto.getProfileStaus()==1){// submitted to POC
				requestRequisitionResource.setSubmittedToPocDate(new Date());
			}else if(dto.getProfileStaus()==2){// selected 
				requestRequisitionResource.setShortlistedDate(new Date());
			}else if(dto.getProfileStaus()==3){//rejection
				requestRequisitionResource.setRejectionDate(new Date());
			}else if(dto.getProfileStaus()==17 || dto.getProfileStaus() == 22){// submitted to AM team
				requestRequisitionResource.setSubmittedToAmDate(new Date());
			}else if(dto.getProfileStaus()==8){ // joined
				requestRequisitionResource.setJoinedDate(new Date());
			}
		}
	}

	private void updateCounterByStatusChange(List<RequestRequisitionResource> requestRequisitionResources, RequestRequisitionResourceStatus resourceUpdatedStatus, int requestRequisitionSkillId,
			List<ResourceStatusDTO> resourceWithStatus) {

		RequestRequisitionSkill requisitionSkill = requestRequisitionSkillDao.findRequestRequisitionSkillBySkillId(requestRequisitionSkillId);
		
		int fulfilled = 0, open = 0, closed = 0, submitted = 0, notFulfilled, notFit = 0, onHold = 0, shortlisted = 0;

		for (RequestRequisitionResource resource : requestRequisitionResources) {
			submitted++;
			if (resource.getRequestRequisitionResourceStatus().getStatusName().equalsIgnoreCase(RequestRequisitionStatusConstants.SELECTED.toString())) {
				shortlisted++;
				fulfilled++;
			} else if (resource.getRequestRequisitionResourceStatus().getStatusName().equalsIgnoreCase(RequestRequisitionStatusConstants.JOINED.toString())) {
				closed++;
			} else if (resource.getRequestRequisitionResourceStatus().getStatusName().equalsIgnoreCase(RequestRequisitionStatusConstants.REJECTED.toString())
					|| resource.getRequestRequisitionResourceStatus().getStatusName().equalsIgnoreCase(RequestRequisitionStatusConstants.NOT_SHORTLISTED.toString())) {
				notFit++;
				open++;
			}
		}
		notFulfilled = requisitionSkill.getNoOfResources() - fulfilled;
		open = requisitionSkill.getNoOfResources() 
				-(closed+ shortlisted +(requisitionSkill.getHold()!=null?requisitionSkill.getHold():0)
						+(requisitionSkill.getLost()!=null?requisitionSkill.getLost():0));
		
		/*open = open+requisitionSkill.getNoOfResources() 
				-(closed+shortlisted+(requisitionSkill.getHold()!=null?requisitionSkill.getHold():0)
						+(requisitionSkill.getLost()!=null?requisitionSkill.getLost():0));*/
		if(open<=0)
			open=0;
		requisitionSkill.setFulfilled(fulfilled);
		requisitionSkill.setOpen(open);
		requisitionSkill.setClosed(closed);
		requisitionSkill.setNotFitForRequirement(notFit);
		requisitionSkill.setSubmissions(submitted);
		requisitionSkill.setShortlisted(shortlisted);
		requisitionSkill.setStatus(getRequestRequisitionSkillStatus(requisitionSkill));
		if(requisitionSkill.getNoOfResources() <= (requisitionSkill.getClosed()+requisitionSkill.getHold()+requisitionSkill.getLost())) {
			if(requisitionSkill.getClosed()!=null && requisitionSkill.getClosed()>0) {
			requisitionSkill.setClosureDate(new Date());
			sendRRFClosureMail(requestRequisitionResources, requestRequisitionSkillId, requisitionSkill);
			}
		}else requisitionSkill.setClosureDate(null);
	}

	public String[] getAllStakeHolderEmailList(int requestRequisitionSkillId) {
		String[] allStakeHoldersEmail=null;
		String sentMailTo="", notifyMailTo="", pdlEmailIds="";
		RequestRequisition requestRequisitionObj = null;
		RequestRequisitionSkill requestRequisitionSkillObj = null;
		try {
			requestRequisitionSkillObj = requestRequisitionSkillDao.findRequestRequisitionSkillBySkillId(requestRequisitionSkillId);
			requestRequisitionObj = requestRequisitionSkillObj.getRequestRequisition();
			sentMailTo = requestRequisitionObj.getSentMailTo();
			notifyMailTo = requestRequisitionObj.getNotifyMailTo();
			pdlEmailIds = requestRequisitionObj.getPdlEmailIds();
			allStakeHoldersEmail = getEmailArray(sentMailTo,notifyMailTo, pdlEmailIds);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return allStakeHoldersEmail;
	}
	
	public void sendRRFClosureMail(List<RequestRequisitionResource> requestRequisitionResources,
			int requestRequisitionSkillId, RequestRequisitionSkill requisitionSkill) {
		Map<String, Object> model = new HashMap<String, Object>();
		String [] emailTo = new String[1];
		String projectBu = "";
		Resource resource = userUtil.getLoggedInResource();
		RequestRequisition requestRequisition = requisitionSkill.getRequestRequisition(); 
		//emailTo[0] = requisitionSkill.getRequestRequisition().getRequestor().getEmailId();
		ArrayList<Integer> emailToIds = new ArrayList<Integer>();
		emailToIds.add(requestRequisition.getRequestor().getEmployeeId());
		List<String> emailToArray = resourceService.findEmailById(emailToIds);
		emailTo[0] = emailToArray.get(0).toString();
		
		//Resource resourceObj=resourceService.getCurrentResource(resource.getUserName());
		//String currentBU = resourceObj.getCurrentBuId().getParentId().getName() + "-" + resourceObj.getCurrentBuId().getName();
		if(requestRequisition.getProjectBU()!=null && !requestRequisition.getProjectBU().isEmpty()) {
			 projectBu = requestRequisition.getProjectBU();
		}

		ArrayList<String[]> notifyArray = new ArrayList<String[]>();
		ArrayList<String[]> requestToArray = new ArrayList<String[]>();
		ArrayList<String[]> pdlArray = new ArrayList<String[]>();
		ArrayList<Integer> sentMailCCTo = new ArrayList<Integer>();
		
		if (!StringUtils.isEmpty(requestRequisition.getSentMailTo())) {
			notifyArray.add(requestRequisition.getNotifyMailTo().split(","));
		}
		if (!StringUtils.isEmpty(requestRequisition.getSentMailTo())) {
			requestToArray.add(requestRequisition.getSentMailTo().split(","));
		}
		if (!StringUtils.isEmpty(requestRequisition.getPdlEmailIds())) {
			pdlArray.add(requestRequisition.getPdlEmailIds().split(","));
		}
		
		
		for (String[] string : notifyArray) {
			for (String stringToInteger : string) {
				if (null != stringToInteger && !"".equals(stringToInteger)) {
					sentMailCCTo.add(Integer.parseInt(stringToInteger.trim()));
				}
			}
		}

		for (String[] string : requestToArray) {
			for (String stringToInteger : string) {
				if (null != stringToInteger && !"".equals(stringToInteger)) {
					sentMailCCTo.add(Integer.parseInt(stringToInteger.trim()));
				}
			}
		}
		
		
		ArrayList<Integer> pdlList = new ArrayList<Integer>();
		if (requestRequisition.getPdlEmailIds() != null && !requestRequisition.getPdlEmailIds().isEmpty()) {
			for (String pdl : requestRequisition.getPdlEmailIds().split(",")) {
				if (null != pdl && !"".equals(pdl)) {
					if(pdl.contains("-")){ // check for previously added pdl in id-emailid format
						String pdlID = pdl.substring(0, 1);
						pdlList.add(Integer.parseInt(pdlID.trim()));
					}else{
						pdlList.add(Integer.parseInt(pdl.trim()));
					}
				}
			}
		}
		
		List<String> ccEmailsToSendMail = new ArrayList<String>();
		
		List<String> pdlGroup = new ArrayList<String>();
		if (null != pdlList && pdlList.size() > 0) {
			pdlGroup = pdlEmailGroupService.findPdlByIds(pdlList);
		}
		if(!CollectionUtils.isEmpty(ccEmailsToSendMail)) {
		ccEmailsToSendMail = resourceService.findEmailById(sentMailCCTo);
		ccEmailsToSendMail.addAll(pdlGroup);
		}
		String[] ccTo = ccEmailsToSendMail.toArray(new String[ccEmailsToSendMail.size()]);
		String indentBy=requestRequisition.getRequestor().getFirstName()+" "+requestRequisition.getRequestor().getLastName();
		List<ResourceStatusDTO> resourceList=new ArrayList<ResourceStatusDTO>();
		List<ResourceStatusDTO> resourceStatusList = requestRequisitionResourceService.getRequestRequisitionResources(null, null, requestRequisitionSkillId);
		for(ResourceStatusDTO resourceStatusDto:resourceStatusList){
			if(null!=resourceStatusDto.getEmployeeId()){
				Resource resourceFromDb=resourceDao.findByEmployeeId(resourceStatusDto.getEmployeeId());
				resourceStatusDto.setTotalExperience(Double.toString(resourceFromDb.getTotalExper()));
				resourceStatusDto.setContactNumber(resourceFromDb.getContactNumber());
				resourceStatusDto.setEmailId(resourceFromDb.getEmailId());
				resourceStatusDto.setSkills(resourceFromDb.getCompetency().getSkill());
			}
			resourceList.add(resourceStatusDto);
		}
		String requirementArea = requisitionSkill.getRequirementArea();
		if(requirementArea!=null) {
			if(requirementArea.equalsIgnoreCase(Constants.NON_SAP))
				requirementArea = Constants.NON_SAP_STRING;
		}else {
			requirementArea = Constants.NON_SAP_STRING;
		}
		if(emailTo[0] != null && ccTo!=null) {
		resourceHelper.setEmailContentForRRFClosureDate(model, emailTo,ccTo /*sentMailCCTo*/,resource.getFirstName()+" "+ resource.getLastName()/*sender*/, 
				requisitionSkill.getRequirementId(), (null==requestRequisition.getCustomer().getCustomerName())?"NA":requestRequisition.getCustomer().getCustomerName(),
				(null==requestRequisition.getGroup())?"NA":requestRequisition.getGroup().getCustomerGroupName(),
						requestRequisition.getProject().getProjectName(),projectBu, resource.getEmailId(), requisitionSkill.getNoOfResources(), 
						requisitionSkill.getClosed(), requisitionSkill.getHold(), requisitionSkill.getLost(), requisitionSkill.getDesignation().getDesignationName(),
						indentBy, requestRequisition.getAmJobCode(), requestRequisitionResources,resourceList,
						requestRequisitionService.findRequestRequisitionSkillsByRequestRequisitionId(requestRequisitionSkillId),requestRequisition.getSuccessFactorId(), requirementArea);
		
		
		emailHelper.sendEmail(model, emailTo, ccTo, null);
		}
	}

	public Map<String, Object> downloadSelectedTac(Integer id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map = (Map<String, Object>) requestReportDao.getTacById(id);
	
		byte[] file = (byte[]) map.get("file");

		Blob blobUploadFile = null;
		byte[] uploadResume = null;

		if (null != file) {
			uploadResume = file;
		}
		try {
			if ((null != uploadResume && !uploadResume.equals(""))) {
				blobUploadFile = new javax.sql.rowset.serial.SerialBlob(uploadResume);
			}
		} catch (Exception e) {

		}
		map.put("file", blobUploadFile);
		return map;
	}

	
	public void sendEmail(String[] resourceIds, String[] pdlIds, String skillReqId,Resource resource, String mailType, String rrfForwardComment) {
		//	TODO Auto-generated method stub
			Map<String, Object> map =new HashMap<String, Object>();
		/*	for (String resourceid : resourceIds) {
				logger.info("-------------sendEmail resource id------------" + resourceid);
			}*/
			String[] ids=null;
			int len=0;
			
			RequestRequisitionSkill requestRequistionSkill =  requestRequisitionSkillDao.updateRequestRequisitionSkillBySkillId(Integer.parseInt(skillReqId),resourceIds,pdlIds, rrfForwardComment);
			RequestRequisition requestRequisition = requestRequistionSkill.getRequestRequisition();
			logger.info("------------sent request to ids after update method call : -----------------"+requestRequistionSkill.getSent_req_id());
			
			List<RequestRequisitionSkill> requestRequistionSkills=new ArrayList<RequestRequisitionSkill>();
			requestRequistionSkills.add(requestRequistionSkill);
			
			Map<String, Object> model = new HashMap<String, Object>();
				
			// new code for adding attachment in mail
			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("dataList", requestRequistionSkill);
				
			File tempFile = createTempFile("RRF", ".pdf");
			
			System.out.println("Temp file : " + tempFile.getAbsolutePath());
			
			tempFile = createAttachment(modelMap, tempFile);
			String projectBU=requestRequisition.getProjectBU()!=null && !requestRequisition.getProjectBU().isEmpty() ? requestRequisition.getProjectBU() : "NA";
			//String projectBU = resource.getCurrentBuId().getParentId().getName() + "-"+ resource.getCurrentBuId().getName();
			String custGrpName = "Not Available";
				if(null != requestRequisition.getGroup()){
					 custGrpName = requestRequisition.getGroup().getCustomerGroupName();
				}
			Resource  resourcePoc=resourceService.find(requestRequisition.getDeliveryPOC().getEmployeeId());
			Location location = locationService.findById(requestRequistionSkill.getLocation().getId());
			String pocName=resourcePoc.getFirstName()+" "+resourcePoc.getLastName();
			String pocBU=resourcePoc.getCurrentBuId().getParentId().getName()+"-"+resourcePoc.getCurrentBuId().getName();
			
			String requestorName = null;
			if(requestRequisition.getRequestor()!=null){
				requestorName = requestRequisition.getRequestor().getFirstName()
						+" "+requestRequisition.getRequestor().getLastName();
			}
			
			String rmgPoc = StringUtils.trimToEmpty(requestRequisition.getRmgPoc()); 
			String tecTeamPoc = StringUtils.trimToEmpty(requestRequisition.getTecTeamPoc());
			 
			if(!rmgPoc.isEmpty()) {
                List<String> stringList = Arrays.asList(rmgPoc.split(","));
                List<Integer> integerList = (List<Integer>) GeneralFunction.convertStringListToIntList(stringList);
                List<Resource> resources = resourceService.findResourceByEmployeeIds(integerList);
                List<String> rmgPocList = new ArrayList<String>();
                Iterator<Resource> iterator = resources.iterator();
                while(iterator.hasNext()){
                       Resource resource2 =  (Resource) iterator.next();
                       StringBuilder str = new StringBuilder();
                       rmgPocList.add(str.append(resource2.getFirstName()).append(" ").append(resource2.getLastName()).append(" [")
                       .append(resource2.getYashEmpId()).append("]").toString());
                }
                rmgPoc = StringUtils.join(rmgPocList, " ,  ");
          }
          
          if(!tecTeamPoc.isEmpty()) {
                List<String> stringList = Arrays.asList(tecTeamPoc.split(","));
                List<Integer> integerList = (List<Integer>) GeneralFunction.convertStringListToIntList(stringList);
                List<Resource> resources = resourceService.findResourceByEmployeeIds(integerList);
                List<String> tecPocList = new ArrayList<String>();
                Iterator<Resource> iterator = resources.iterator();
                while(iterator.hasNext()){
                       Resource resource2 =  (Resource) iterator.next();
                       StringBuilder str = new StringBuilder();
                       tecPocList.add(str.append(resource2.getFirstName()).append(" ").append(resource2.getLastName()).append(" [")
                       .append(resource2.getYashEmpId()).append("]").toString());
                }
                tecTeamPoc = StringUtils.join(tecPocList, " ,  ");
          }
          
          /*if(mailType.equalsIgnoreCase(Constants.SUBMITTED_TO_AM_TEAM))
          resourceHelper.setEmailContentForRequestForSubmittedToAMTeam(model, resourceIds, pdlIds, resource.getFirstName()+" "+resource.getLastName(), requestRequistionSkill.getRequestRequisition().getProject().getProjectName(),
                       projectBU, resource.getEmailId(), requestRequistionSkills, requestRequistionSkill.getComments(), 
                       requestRequistionSkill.getRequestRequisition().getCustomer().getCustomerName(),requestRequistionSkill.getRequirementId() , custGrpName ); */ 
          if(mailType.equalsIgnoreCase(Constants.FORWARD_RRF))
          resourceHelper.setEmailContentForRequest(model, resourceIds, pdlIds, requestorName,requestRequisition.getProject().getProjectName(),
                       projectBU, resource.getEmailId(), requestRequistionSkills, requestRequisition.getComments(),requestRequisition.getAmJobCode(),
                       requestRequisition.getCustomer().getCustomerName(),requestRequistionSkill.getRequirementId() , custGrpName,pocName,0,false,0,false,false,
                       requestRequistionSkill.getNoOfResources(),location.getLocation(),pocBU, requestRequisition.getResource(),
                       requestRequisition.getSuccessFactorId(),requestRequistionSkill.getRequirementArea(),rmgPoc,tecTeamPoc, mailType, resource);

			// TODO : RRF Closure Mail
			/*if(mailType.equalsIgnoreCase(Constants.RRF_CLOSURE_MAIL))
				resourceHelper.setEmailContentForRRFClosureMail(model, resourceIds, pdlIds, resource.getFirstName()+" "+resource.getLastName(), requestRequistionSkill.getRequestRequisition().getProject().getProjectName(),
						projectBU, resource.getEmailId(), requestRequistionSkills, requestRequistionSkill.getComments(), 
						requestRequistionSkill.getRequestRequisition().getCustomer().getCustomerName(),requestRequistionSkill.getRequirementId() , custGrpName );*/
			
			
			if(resourceIds!=null && pdlIds!=null) {	
				ids=new String[resourceIds.length+pdlIds.length];
				len=resourceIds.length;
		}
	
			else if (resourceIds==null ) {
				ids=new String[pdlIds.length];
				len=pdlIds.length;
	
			}
			else
			{
				ids=new String[resourceIds.length];
				len=resourceIds.length;
			}
			
			for(int i=0;i<ids.length;i++)
			{
				if(i<len)
				{
					if(resourceIds==null)
						ids[i]=pdlIds[i];
					else
						ids[i]=resourceIds[i];
				}
				else
					ids[i]=pdlIds[i-len];
			}
			for(int i=0; i<ids.length ; i++){
			logger.info("------------------emails to forward RRfs  : "+ids[i]);
			}
			
			emailHelper.sendEmail(model, ids, null, tempFile,requestRequistionSkill.getRequirementId());
		}
	
	
	public File createTempFile(String prefix, String suffix){
		return requestDao.createTempFile(prefix, suffix);
	}
	
	public File createAttachment(Map<String, Object> model, File file) {
		return requestDao.createAttachment(model,file);
	}
	
	
	public RequestRequisitionDashboardDTO getRequestRequisitionReportForDateCriteria(Integer id, String role, Date startDate,
			Date endDate){
		
		RequestRequisitionDashboardDTO requestRequisitionDashboardDTO = new RequestRequisitionDashboardDTO();
		List<RequestRequisitionSkill> requestRequisitionSkillDomainList = new ArrayList<RequestRequisitionSkill>();
		List<RequestRequisitionSkillDTO> requestRequisitionSkillDTOList = new ArrayList<RequestRequisitionSkillDTO>();
		
		try {
			requestRequisitionSkillDomainList = requestReportDao.getSkillRequestReport(id, role,startDate,endDate);
			requestRequisitionSkillDTOList = dozerMapperUtility.requestRequisitionDomainToDTOList(requestRequisitionSkillDomainList,true);
			
			requestRequisitionDashboardDTO.setRequestRequisitionDashboardList(requestRequisitionSkillDTOList);
			setPositionStatus(requestRequisitionDashboardDTO);
			return requestRequisitionDashboardDTO;
		
		} catch (Exception e) {
			logger.error("-------Error in getRequestRequisitionReportForDateCriteria ------", e);
		}

		return requestRequisitionDashboardDTO;
	}
	
	public RequestRequisitionDashboardDTO getRequestRequisitionReport(Integer id, String role, Date startDate,
			Date endDate, List<Integer> customerList, List<Integer> groupList, String status){
		
		RequestRequisitionDashboardDTO requestRequisitionDashboardDTO = new RequestRequisitionDashboardDTO();
		List<RequestRequisitionSkill> requestRequisitionSkillDomainList = new ArrayList<RequestRequisitionSkill>();
		List<RequestRequisitionSkill> inActiveRequestRequisitionSkillDomainList = new ArrayList<RequestRequisitionSkill>();
		List<RequestRequisitionSkillDTO> requestRequisitionSkillDTOList = new ArrayList<RequestRequisitionSkillDTO>();
		try {
			if(status == null){
				requestRequisitionSkillDomainList = requestReportDao.getSkillRequestReport(id, role,startDate,endDate,customerList, groupList,"A");
				inActiveRequestRequisitionSkillDomainList = requestReportDao.getSkillRequestReport(id, role,startDate,endDate,customerList, groupList,"I");
				requestRequisitionSkillDomainList.addAll(inActiveRequestRequisitionSkillDomainList);
			}else{
				requestRequisitionSkillDomainList = requestReportDao.getSkillRequestReport(id, role,startDate,endDate,customerList, groupList,status);
			}
			requestRequisitionSkillDTOList = dozerMapperUtility.requestRequisitionDomainToDTOList(requestRequisitionSkillDomainList,true);
			requestRequisitionDashboardDTO.setRequestRequisitionDashboardList(requestRequisitionSkillDTOList);
			return requestRequisitionDashboardDTO;
		} catch (Exception e) {
			logger.error("-------Error in getRequestRequisitionReport ------", e);
		}
		return requestRequisitionDashboardDTO;
	}
	
	public List<RequestRequisitionReport>  getDashBoardDataReport(Integer id, String role,List<Integer> customerList, 
			List<Integer> groupList, String status, List<String> hiringUnits, List<String> reqUnits) {
		List<RequestRequisitionReport>  dbReportList = new ArrayList<RequestRequisitionReport>();
		List<RequestRequisitionReport>  dbReportInactiveList = new ArrayList<RequestRequisitionReport>();
		try {
				dbReportList = requestReportDao.getSkillRequestDBTabReport(id, role, customerList, groupList,status, hiringUnits, reqUnits);
				return dbReportList;
		} catch (Exception e) {
			logger.error("-------Error in getDashBoardDataReport ------", e);
		}
		return dbReportList;
	}
	private String getRequestRequisitionSkillStatus(RequestRequisitionSkill requestRequisitionSkill){
		
		String noOfPositions = String.valueOf(requestRequisitionSkill.getNoOfResources());
		boolean positionFlag = true;
		
		int noOfPos=requestRequisitionSkill.getNoOfResources();
		int closed=requestRequisitionSkill.getClosed();
		int fulfilled=requestRequisitionSkill.getFulfilled();
		int hold=requestRequisitionSkill.getHold();
		int lost=requestRequisitionSkill.getLost();
		StringBuilder posStatus = new StringBuilder();
		if (requestRequisitionSkill.getClosed() != null && noOfPositions.equals(String.valueOf(requestRequisitionSkill.getClosed()))) {
			requestRequisitionSkill.setStatus("Closed");
			positionFlag = false;
		}/*else if(requestRequisitionSkill.getLost() != null && noOfPositions.equals(String.valueOf(requestRequisitionSkill.getLost()))){
			requestRequisitionSkill.setStatus("Lost");
			positionFlag = false;
		}else if (requestRequisitionSkill.getHold() != null && noOfPositions.equals(String.valueOf(requestRequisitionSkill.getHold()))) {
			requestRequisitionSkill.setStatus("Hold");
			positionFlag = false;
		}else if (requestRequisitionSkill.getFulfilled() > 0 && noOfPositions.equals(String.valueOf(requestRequisitionSkill.getFulfilled()))) {
			requestRequisitionSkill.setStatus("Fulfilled");
			positionFlag = false;
		}*/	
		else if(noOfPos<=lost)
		{
				requestRequisitionSkill.setStatus("Lost");
				requestRequisitionSkill.setOpen(0);
				positionFlag = false;
		}
		else if(noOfPos<=hold)
		{
			requestRequisitionSkill.setStatus("Hold");
			requestRequisitionSkill.setOpen(0);
			positionFlag = false;
		}
		else if(closed<noOfPos&&hold<noOfPos&&lost<noOfPos&&((noOfPos<=closed+fulfilled+lost+hold)||(noOfPos<=closed+fulfilled+lost)||(noOfPos<=closed+fulfilled)))
		{
			if(!(noOfPos<=closed+hold+lost))
				posStatus.append("Fulfilled,");
			
			if(closed>0)
				posStatus.append(" Closed : "+requestRequisitionSkill.getClosed());
			if(hold>0)
				posStatus.append(", Hold : "+requestRequisitionSkill.getHold());
			if(lost>0)
				posStatus.append(", Lost : "+requestRequisitionSkill.getLost());
			
			if(noOfPos<=lost+hold+closed)
				requestRequisitionSkill.setOpen(0);
			
			
			requestRequisitionSkill.setStatus(posStatus.toString());
			positionFlag = false;
		}			
		
		if(requestRequisitionSkill.getStatus() == null || positionFlag){
				boolean isOnlyOpen = true;
				StringBuilder status = new StringBuilder();
				
				if(requestRequisitionSkill.getOpen() != null && requestRequisitionSkill.getOpen() > 0){
					status.append(" Open : "+requestRequisitionSkill.getOpen());
					status.append(" ");
				}
				if(requestRequisitionSkill.getClosed() != null && requestRequisitionSkill.getClosed() > 0){
					isOnlyOpen = false;
					status.append(" Closed : "+requestRequisitionSkill.getClosed());
					status.append(" ");
				}
				if(requestRequisitionSkill.getHold() != null && requestRequisitionSkill.getHold() > 0){
					isOnlyOpen = false;
					status.append(" Hold : "+requestRequisitionSkill.getHold());
					status.append(" ");
				}
				if(requestRequisitionSkill.getLost() != null && requestRequisitionSkill.getLost() > 0){
					isOnlyOpen = false;
					status.append(" Lost : "+requestRequisitionSkill.getLost());
					status.append(" ");
				}
				if(requestRequisitionSkill.getFulfilled() > 0){
					isOnlyOpen = false;
					status.append(" Selected : "+requestRequisitionSkill.getFulfilled());
				}
			
			if(isOnlyOpen){
				requestRequisitionSkill.setStatus("Open");
			}else{
				requestRequisitionSkill.setStatus(status.toString());
			}
		}
		return requestRequisitionSkill.getStatus();
	}
	public String getRequestRequisitionSkillStatus1(RequestRequisitionSkill requestRequisitionSkill){
		
		Integer positions = 0;
		Integer open = 0;
		Integer closed = 0;
		Integer fulfilled = 0;
	
		
		positions = requestRequisitionSkill.getNoOfResources();
		
		open = requestRequisitionSkill.getOpen();
		
		closed = requestRequisitionSkill.getClosed();
		
		fulfilled = requestRequisitionSkill.getFulfilled();
		
		if(open >= 1 ){
			requestRequisitionSkill.setStatus(Constants.OPEN);
		} else if (fulfilled == open){
			requestRequisitionSkill.setStatus(Constants.FULFILLED);
		} else if (closed == open) {
			requestRequisitionSkill.setStatus(Constants.CLOSED);
		}
		
		return requestRequisitionSkill.getStatus();
	}
	
	public void saveResourceComment(ResourceComment resourceComment) {
		requestReportDao.saveResourceComment(resourceComment);
	}

	public List<ResourceCommentDTO> getAllResourceCommentByResourceId(
			int resourceId) {
		List<ResourceComment> resourceCommentList = requestReportDao
				.getAllResourceCommentByResourceId(resourceId);
		SimpleDateFormat foramtter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<ResourceCommentDTO> dtoList = new ArrayList<ResourceCommentDTO>();
		
		dtoList = DozerMapperUtility.convertResourceCommentToResourceCommentDTO(resourceCommentList);
		
		System.out.println(dtoList);
		return dtoList;
	}
	
	public List<ResourceCommentDTO> getAllResourceCommentsBySkillRequestRequisitionID(Integer skillId){
		List<ResourceComment> domainList = requestReportDao.getAllResourceCommentsBySkillRequestRequisitionID(skillId);
		for (ResourceComment resourceComment : domainList) {
			System.out.println(resourceComment);
		}
		List<ResourceCommentDTO> dtoList = new ArrayList<ResourceCommentDTO>();
		
		dtoList = DozerMapperUtility.convertResourceCommentToResourceCommentDTO(domainList);
		return dtoList;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> downloadSelectedBGHApproval(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map = (Map<String, Object>) requestRequisitionDao.getBGHApprovalById(id);
		
		byte[] file = (byte[]) map.get("file");

		Blob blobUploadFile = null;
		byte[] buhApprovalFile = null;

		if (null != file) {
			buhApprovalFile = file;
		}
		try {
			if ((null != buhApprovalFile && !buhApprovalFile.equals(""))) {
				blobUploadFile = new javax.sql.rowset.serial.SerialBlob(buhApprovalFile);
			}
		} catch (Exception e) {

		}
		map.put("file", blobUploadFile);
		return map;
	}

	@Transactional
	public RequestRequisitionSkill findRequestRequisitionSkillBySkillId(int id) {
		return requestRequisitionSkillDao.findRequestRequisitionSkillBySkillId(id);
	}
	 
	
	public void sendEmailForInterview(ResourceInterviewWrapper resourceInterviewWrapper, Resource loggedInResource) throws Exception {
		String[] sendMailCC = new String[20];
		String[] sendMailTo = new String[20];
		String emailContent = "";
		String loggedInResourceName = "";
		int resourceRequestId = 0;
		String loggedInResourceEmail = "";
		Integer skillId = 0;
		
		if(loggedInResource!=null) {
			loggedInResourceName = loggedInResource.getEmployeeName()!=null && !loggedInResource.getEmployeeName().isEmpty() ?loggedInResource.getEmployeeName() : "System";
			loggedInResourceEmail = loggedInResource.getEmailId()!=null && !loggedInResource.getEmailId().isEmpty() ? loggedInResource.getEmailId() : "";
		}
		
		if(resourceInterviewWrapper.getRequestResourceId()!=null) {
			resourceRequestId = resourceInterviewWrapper.getRequestResourceId();
		}
		RequestRequisitionSkill requestRequisitionSkill = new RequestRequisitionSkill();
		RequestRequisitionResource requestRequisitionResource = requestRequisitionResourceService.getRequestRequisitionResourceById(resourceRequestId);
		RequestRequisition requestRequisition = new RequestRequisition();
		if(requestRequisitionResource.getRequestRequisitionSkill()!=null) {
			requestRequisitionSkill = requestRequisitionResource.getRequestRequisitionSkill();
			skillId = requestRequisitionResource.getRequestRequisitionSkill().getId();
			if(requestRequisitionResource.getRequestRequisitionSkill().getRequestRequisition() != null) {
				requestRequisition = requestRequisitionService.findRequestRequisitionById(requestRequisitionResource.getRequestRequisitionSkill().getRequestRequisition().getId());	
			}
		}
		
		String requirementArea = requestRequisitionSkill.getRequirementArea();
		String sendMailFrom = "";
		if(requirementArea.equalsIgnoreCase(Constants.SAP)) {
			sendMailFrom = Constants.RMG_ERP_PDL_EMAIL;
		} else {
			sendMailFrom = Constants.RMG_PDL_EMAIL;
		}
		String resourceName = "";
		String resourceEmail = "";
		Resource resource = new Resource();
		if(requestRequisitionResource.getResource() != null) {
			resource = resourceService.find(requestRequisitionResource.getResource().getEmployeeId()); 
			resourceName = resource.getEmployeeName();
			resourceEmail = resource.getEmailId();
		} else {
			resourceName = requestRequisitionResource.getExternalResourceName();
			resourceEmail = requestRequisitionResource.getEmailId()!=null && !requestRequisitionResource.getEmailId().isEmpty() ? requestRequisitionResource.getEmailId() : Constants.NOT_AVAILABLE;
		}
		
		if(!resourceInterviewWrapper.getUserIdList().isEmpty() && resourceInterviewWrapper.getUserIdList()!=null) {
			sendMailCC = resourceInterviewWrapper.getUserIdList().split(",");	
		}
		
		if(resourceInterviewWrapper.isNotifyTo()) {
			String notify = requestRequisition.getNotifyMailTo();
			if(notify!=null && !notify.isEmpty()  ) {
				String[] notifyids = notify.split(",");
				
				for (int i=0; i<notifyids.length ; i++) {
					notifyids[i] = notifyids[i].replaceAll("\\[", "").replaceAll("\\]", "").trim();
					System.out.println("mail ids from front end :"+notifyids[i]);
				}
				
				ArrayList<String> notifyToMailIdsInStringFormat = new ArrayList<String>();
				ArrayList<Integer> notifyToMailIdsIninteger = new ArrayList<Integer>();
				for (String string : notifyids) {
					notifyToMailIdsIninteger.add(Integer.parseInt(string.trim()));
					notifyToMailIdsInStringFormat.add(string);
				}
			
				List<String> emailsForNotifyToMail = resourceService.findEmailById(notifyToMailIdsIninteger);
				
				String[] emailsArrayForToMail = new String[emailsForNotifyToMail.size()];
				
				for (int i=0; i<emailsForNotifyToMail.size(); i++) {
					emailsArrayForToMail[i] = emailsForNotifyToMail.get(i);
				}
				sendMailCC = ArrayUtils.addAll(sendMailCC, emailsArrayForToMail);
			} else {
				sendMailCC[sendMailCC.length] = resource.getEmailId()!=null && !resource.getEmailId().isEmpty() ? resource.getEmailId() : "";
			}
			
			
		}
		
		if(resourceInterviewWrapper.isPdlsTo()) {
			String pdls = requestRequisition.getPdlEmailIds();
			if(pdls!=null && !pdls.isEmpty()  ) {
				String[] pdlIds = pdls.split(",");
				
				ArrayList<Integer> pdlInintegers = new ArrayList<Integer>();
				ArrayList<String> pdlIDsInString = new ArrayList<String>();
				
				for (String string : pdlIds) {
					if(string!=null && !string.contains("null"))
					{
						String s1=string.replaceAll("\\[", "").replaceAll("\\]", "").trim();
						if(!s1.equalsIgnoreCase("null"))
						{
					pdlInintegers.add(Integer.parseInt(string.replaceAll("\\[", "").replaceAll("\\]", "").trim()));
					pdlIDsInString.add(string.replaceAll("\\[", "").replaceAll("\\]", "").trim());
						}
					}
				}
				List<String> pdlMailsTo = pdlEmailGroupService.findPdlByIds(pdlInintegers);
				String[] pdlMailsInArrayFormat = new String[pdlMailsTo.size()];
				
				for (int i = 0; i < pdlMailsInArrayFormat.length; i++) {
					pdlMailsInArrayFormat[i] = pdlMailsTo.get(i);
				}
				sendMailCC = ArrayUtils.addAll(sendMailCC, pdlMailsInArrayFormat);
				
			} else {
				sendMailCC[sendMailCC.length] = resource.getEmailId()!=null && !resource.getEmailId().isEmpty() ? resource.getEmailId() : "";
			}
			
		}
		
		if(resourceInterviewWrapper.isSendMailTo()) {
			List<String> emailsForToMail = new ArrayList<String>();
			String mailTo = requestRequisition.getSentMailTo();
			if(mailTo!=null && !mailTo.isEmpty()  ) {
			String[] mailToArray = mailTo.split(",");
			
			for (int i=0; i<mailToArray.length ; i++) {
				mailToArray[i] = mailToArray[i].replaceAll("\\[", "").replaceAll("\\]", "").trim();
				System.out.println("mail ids from front end :"+mailToArray[i]);
			}
			
			ArrayList<String> sendToMailIdsInStringFormat = new ArrayList<String>();
			ArrayList<Integer> sendToMailIdsIninteger = new ArrayList<Integer>();
			for (String string : mailToArray) {
				sendToMailIdsIninteger.add(Integer.parseInt(string.trim()));
				sendToMailIdsInStringFormat.add(string);
			}
		
			emailsForToMail = resourceService.findEmailById(sendToMailIdsIninteger);
			
			String[] emailsArrayForToMail = new String[emailsForToMail.size()];
			
			for (int i=0; i<emailsForToMail.size(); i++) {
				emailsArrayForToMail[i] = emailsForToMail.get(i);
			}
			sendMailTo = emailsArrayForToMail;
			} else {
				sendMailTo[emailsForToMail.size()]= loggedInResourceEmail;
			}
		}
		
		String contactPersonNames = "";
		
		if(resourceInterviewWrapper.getContactPersonIds()!=null && !resourceInterviewWrapper.getContactPersonIds().isEmpty()) {
			List<String> emailsForToMail = new ArrayList<String>();
			String contactPersons = resourceInterviewWrapper.getContactPersonIds();
			if( contactPersons!=null && !contactPersons.isEmpty()) {
			String[] mailToArray = contactPersons.split(",");
			
			for (int i=0; i<mailToArray.length ; i++) {
				mailToArray[i] = mailToArray[i].replaceAll("\\[", "").replaceAll("\\]", "").trim();
				System.out.println("mail ids from front end :"+mailToArray[i]);
			}
			
			sendMailCC = ArrayUtils.addAll(sendMailCC, mailToArray);
			
			
			
			ArrayList<String> sendToMailIdsInStringFormat = new ArrayList<String>();
			ArrayList<Integer> sendToMailIdsIninteger = new ArrayList<Integer>();
			for (String string : mailToArray) {
				
				sendToMailIdsInStringFormat.add(string);
			}
		
			//emailsForToMail = resourceService.findEmailById(sendToMailIdsIninteger);
			List<Resource> resources = resourceService.findResourcesByEmialIds(sendToMailIdsInStringFormat);
			String[] resourcesName = new String[resources.size()];
			
			for (int i=0; i<resourcesName.length; i++) {
				String contactNumber = "";
				resourcesName[i] = resources.get(i).getEmployeeName();
				if(resourceInterviewWrapper.getContactPersonNumber().isEmpty()) {
					 contactNumber = resources.get(i).getContactNumber()!=null && !resources.get(i).getContactNumber().isEmpty() ? resources.get(i).getContactNumber() : Constants.NOT_AVAILABLE;
				} else {
					 contactNumber = resourceInterviewWrapper.getContactPersonNumber();
				}
				
				String nameAndContact = resources.get(i).getEmployeeName() +"-"+ contactNumber;
				contactPersonNames = nameAndContact  + " / " + contactPersonNames;
			}
			
			
			} else {
				sendMailTo[emailsForToMail.size()]= loggedInResourceEmail;
			}
		}
		
		
		if(!resourceInterviewWrapper.getMailText().isEmpty() && resourceInterviewWrapper.getMailText()!=null) {
			emailContent = resourceInterviewWrapper.getMailText();
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		if(sendMailCC.length <= 0) {
			sendMailCC[0] = loggedInResourceEmail;
		}
		
		sendMailTo = ArrayUtils.add(sendMailTo, resourceEmail);
		
		
		System.out.println("to mail ids --- "+ Arrays.asList(sendMailTo));

		System.out.println("to cc mail ids ---" + Arrays.asList(sendMailCC));
		
		RequestRequisitionWrapper requestRequisitionWrapper =   getDataForScheduleInterviewByskillId(skillId.toString(), resourceRequestId);
		resourceHelper.setEmailContentForResourceInterviewDetails(model, sendMailTo, sendMailCC, loggedInResourceName, emailContent, resourceName, resourceInterviewWrapper, requestRequisitionWrapper,contactPersonNames, sendMailFrom);
		emailHelper.sendEmailForInterview(model, sendMailTo, sendMailCC);
		
	}
	
	

	private Map<String, Object> sendEmailForRRFResourceStatusEmail(int requestRequisitionSkillId,List<ResourceStatusDTO> changedStatusResourceList, Map<String, Integer> statusCountMap) {
		logger.info(" RequestRequisitionDashboardServiceImpl sendEmailForRRFResourceStatusEmail method starts");
		Map<String, Object> returnModel =null;
		String[] allStakeHoldersEmail=null;
		RequestRequisition requestRequisitionObj = null;
		RequestRequisitionSkill requestRequisitionSkillObj = null;
		Location locationObj = null;
		Designation designationObj = null;
		CustomerDTO customerObj = null;
		
		String sender="",projectName="",projectBu="",sendMailFrom="", requirementId="", location="", designation="",clientName = "", subject = "";
		try {
			returnModel =  new HashMap<String, Object>();	
			requestRequisitionSkillObj = requestRequisitionSkillDao.findRequestRequisitionSkillBySkillId(requestRequisitionSkillId);
			requestRequisitionObj = requestRequisitionSkillObj.getRequestRequisition();
			
			allStakeHoldersEmail = getAllStakeHolderEmailList(requestRequisitionSkillId);
			
			//Code to set other data
			Integer currentLoggedInUserId = UserUtil.getCurrentResource().getEmployeeId();
			Resource currentResource = resourceService.find(currentLoggedInUserId);
			if(currentResource!=null)
				sender = currentResource.getFirstName() + " " + currentResource.getLastName();
			if(requestRequisitionObj.getProject()!=null)
				projectName = requestRequisitionObj.getProject().getProjectName();
			projectBu = requestRequisitionObj.getProjectBU();
			sendMailFrom = currentResource.getEmailId();
			if(requestRequisitionSkillObj.getLocation()!=null)
				locationObj = locationService.findById(requestRequisitionSkillObj.getLocation().getId());
			if(locationObj!=null)
				location = locationObj.getLocation();
			if(requestRequisitionSkillObj.getDesignation()!=null)
				designationObj = designationService.findById(requestRequisitionSkillObj.getDesignation().getId());
			if(designationObj!=null)
				designation = designationObj.getDesignationName();
			if(requestRequisitionObj.getCustomer()!=null)
				customerObj = customerService.findCustomer(requestRequisitionObj.getCustomer().getId());
			if(customerObj!=null)
				clientName = customerObj.getCustomerName();
			
			//Now set content for email
			String currentDate=org.yash.rms.util.DateUtil.getStringDate(new Date());
			String currentTime=org.yash.rms.util.DateUtil.getStringTime(new Date());
			
			//Code for Group Name
			String groupName = ""; //check for group name
			if(requestRequisitionObj.getGroup()!= null && !"".equals(requestRequisitionObj.getGroup().getCustomerGroupName())){
				groupName = requestRequisitionObj.getGroup().getCustomerGroupName();
			}
			String indentBy = ""; //check for indent name
			if(requestRequisitionObj.getRequestor()!= null && !"".equals(requestRequisitionObj.getRequestor().getFirstName())){
				//indentBy=requestRequisitionObj.getResource().getFirstName()+" "+requestRequisitionObj.getResource().getLastName();
				indentBy=requestRequisitionObj.getRequestor().getFirstName()+" "+requestRequisitionObj.getRequestor().getLastName();
			}
			
			String rrfBgBu = requestRequisitionObj.getProjectBU();
			String successFactorId = "";
			if(requestRequisitionObj.getSuccessFactorId()!= null) {
				successFactorId = requestRequisitionObj.getSuccessFactorId();
			}
			String amJobCode = "";
			if(requestRequisitionObj.getAmJobCode()!= null) {
				amJobCode = requestRequisitionObj.getAmJobCode();
			}
			returnModel.put(Constants.FILE_NAME, Constants.RRF_RESOURCE_STATUS_MAIL_HTML);
			//Code For setting subject - start
			if(statusCountMap.get(Constants.SUBMITTEDSTATUS)>0 && statusCountMap.get(Constants.REJECTEDSTATUS)>0) {
				subject = Constants.SUBJECT_HEADING_FOR_RESOURCE_STATUS;
			}else if(statusCountMap.get(Constants.SUBMITTEDSTATUS)>0) {
				subject = Constants.SUBJECT_HEADING_FOR_SUBMITTED_TO_AM_TEAM;
			}else if(statusCountMap.get(Constants.REJECTEDSTATUS)>0) {
				subject = Constants.SUBJECT_HEADING_FOR_REJECTED_BY_POC;
			}
			requirementId = requestRequisitionSkillObj.getRequirementId();
			if(null!=amJobCode && !amJobCode.isEmpty()) {
				subject = subject +  requirementId+" - [ "+amJobCode+" ]";
			}else {
				subject = subject +  requirementId;
			}
			returnModel.put(Constants.SUBJECT, subject);
			//Code For setting subject - end
			returnModel.put("currentDate", currentDate);
			returnModel.put("currentTime", currentTime);
			returnModel.put("projectName", projectName);
			returnModel.put("sendMailFrom", sendMailFrom); 
			returnModel.put("projectBU", projectBu);
			returnModel.put(Constants.INDENTER, sender);
			returnModel.put(Constants.TOMAILIDSLIST, allStakeHoldersEmail);
			returnModel.put(Constants.CCMAILIDSLIST, null);
			returnModel.put("location",location);
			returnModel.put("designationName", designation);
			returnModel.put("clientName",clientName);
			returnModel.put("rrfName",requirementId);
			returnModel.put("groupName", groupName);
			returnModel.put("indentBy", indentBy);
			if(null!=rrfBgBu && !rrfBgBu.isEmpty()) {
				returnModel.put("rrfBgBu", rrfBgBu);
			}else {
				returnModel.put("rrfBgBu", "NA");
			}
			returnModel.put("amJobCode", amJobCode);
			returnModel.put("successFactorId", successFactorId);
			returnModel.put("noOfResources", requestRequisitionSkillObj.getNoOfResources());
			returnModel.put(Constants.NEW_RESOURCE_STATUS_LIST, changedStatusResourceList);
			
			String requirementArea = requestRequisitionSkillObj.getRequirementArea();
			if(requirementArea!=null) {
				if(requirementArea.equalsIgnoreCase("NON_SAP")) {
					requirementArea = Constants.NON_SAP_STRING;
				}
			}else {
				requirementArea = Constants.NON_SAP_STRING;
			}
			returnModel.put("requirementArea",requirementArea);
			
			if(returnModel!=null) {
				emailHelper.sendEmail(returnModel, allStakeHoldersEmail, null, null, null);
			}
		}catch (Exception exception) {
			returnModel = null;
			logger.error("Exception occured RequestRequisitionDashboardServiceImpl sendEmailForRRFResourceStatusEmail setting data to send in email while RRF resource status update: " + exception.getMessage());
		}
		logger.info(" RequestRequisitionDashboardServiceImpl sendEmailForRRFResourceStatusEmail method end");
		return returnModel;
	}
	private Map<String, Object> setContentForDeleteRRFEmail(int req_reqistionID, int skill_req_requisitionID,RequestRequisitionSkill requestRequisitionSkillObj) {
		logger.info(" RequestRequisitionDashboardServiceImpl setContentForDeleteRRFEmail method starts");
		
		String reqId = Integer.toString(skill_req_requisitionID);
		Map<String, Object> returnModel =null;
		String sentMailTo="", notifyMailTo="", pdlEmailIds="",sender="",projectName="",projectBu="",sendMailFrom="", requirementId="", location="", designation="",clientName = "";
		
		String[] allStakeHoldersEmail=null;
		
		RequestRequisition requestRequisitionObj = null;
		Location locationObj = null;
		Designation designationObj = null;
		CustomerDTO customerObj = null;
		try {
			returnModel =  new HashMap<String, Object>();
			requestRequisitionObj = requestRequisitionSkillObj.getRequestRequisition();
			requirementId = requestRequisitionSkillObj.getRequirementId();
			
			//Code for fetching emailIDs of all stakeholders - start
			sentMailTo = requestRequisitionObj.getSentMailTo();
			notifyMailTo = requestRequisitionObj.getNotifyMailTo();
			pdlEmailIds = requestRequisitionObj.getPdlEmailIds();
			
			//Code to get email ids and form string array from sentMailTo, notifyMailTo, pdlEmailIds
			allStakeHoldersEmail = getEmailArray(sentMailTo,notifyMailTo, pdlEmailIds);
			//Code for fetching emailIDs of all stakeholders - start
			
			//Code to set other data
			Integer currentLoggedInUserId = UserUtil.getCurrentResource().getEmployeeId();
			Resource currentResource = resourceService.find(currentLoggedInUserId);
			if(currentResource!=null)
				sender = currentResource.getFirstName() + " " + currentResource.getLastName();
			if(requestRequisitionObj.getProject()!=null)
				projectName = requestRequisitionObj.getProject().getProjectName();
			projectBu = requestRequisitionObj.getProjectBU();
			sendMailFrom = currentResource.getEmailId();
			if(requestRequisitionSkillObj.getLocation()!=null)
				locationObj = locationService.findById(requestRequisitionSkillObj.getLocation().getId());
			if(locationObj!=null)
				location = locationObj.getLocation();
			if(requestRequisitionSkillObj.getDesignation()!=null)
				designationObj = designationService.findById(requestRequisitionSkillObj.getDesignation().getId());
			if(designationObj!=null)
				designation = designationObj.getDesignationName();
			if(requestRequisitionObj.getCustomer()!=null)
				customerObj = customerService.findCustomer(requestRequisitionObj.getCustomer().getId());
			if(customerObj!=null)
				clientName = customerObj.getCustomerName();
			
			//Now set content for email
			String currentDate=org.yash.rms.util.DateUtil.getStringDate(new Date());
			String currentTime=org.yash.rms.util.DateUtil.getStringTime(new Date());
			
			//Code for Group Name
			String groupName = ""; //check for group name
			if(requestRequisitionObj.getGroup()!= null && !"".equals(requestRequisitionObj.getGroup().getCustomerGroupName())){
				groupName = requestRequisitionObj.getGroup().getCustomerGroupName();
			}
			String indentBy = ""; //check for group name
			if(requestRequisitionObj.getResource()!= null && !"".equals(requestRequisitionObj.getResource().getFirstName())){
				indentBy=requestRequisitionObj.getResource().getFirstName()+" "+requestRequisitionObj.getResource().getLastName();
			}
			
			String rrfBgBu = requestRequisitionObj.getProjectBU();
			String successFactorId = "";
			if(requestRequisitionObj.getSuccessFactorId()!= null) {
				successFactorId = requestRequisitionObj.getSuccessFactorId();
			}
			String amJobCode = "";
			if(requestRequisitionObj.getAmJobCode()!= null) {
				amJobCode = requestRequisitionObj.getAmJobCode();
			}
			
			String requirementArea = requestRequisitionSkillObj.getRequirementArea();
			if(requirementArea!=null) {
				if(requirementArea.equalsIgnoreCase("NON_SAP")) {
					requirementArea = Constants.NON_SAP_STRING;
				}
			}else {
				requirementArea = Constants.NON_SAP_STRING;
			}
			returnModel.put(Constants.FILE_NAME, Constants.RRF_DELETE_HTML);
			returnModel.put(Constants.SUBJECT, Constants.SUBJECT_HEADING + requirementId);
			returnModel.put(Constants.TOMAILIDSLIST, allStakeHoldersEmail); 
			returnModel.put(Constants.CCMAILIDSLIST, null);
			returnModel.put("currentDate", currentDate);
			returnModel.put("currentTime", currentTime);
			returnModel.put("projectName", projectName);
			returnModel.put("sendMailFrom", sendMailFrom); 
			returnModel.put("projectBU", projectBu);
			returnModel.put(Constants.INDENTER, sender);
			returnModel.put("location",location);
			returnModel.put("designationName", designation);
			returnModel.put("clientName",clientName);
			returnModel.put("rrfName",requirementId);
			returnModel.put("groupName", groupName);
			returnModel.put("indentBy", indentBy);
			if(null!=rrfBgBu && !rrfBgBu.isEmpty()) {
				returnModel.put("rrfBgBu", rrfBgBu);
			}else {
				returnModel.put("rrfBgBu", "NA");
			}
			returnModel.put("amJobCode", amJobCode);
			returnModel.put("successFactorId", successFactorId);
			returnModel.put("noOfResources", requestRequisitionSkillObj.getNoOfResources());
			returnModel.put("requirementArea",requirementArea);
		}catch (Exception exception) {
			returnModel = null;
			logger.error("Exception occured RequestRequisitionDashboardServiceImpl setContentForDeleteRRFEmail setting data to send in email while delete RRF: " + exception.getMessage());
		}
		logger.info(" RequestRequisitionDashboardServiceImpl setContentForDeleteRRFEmail method ends");
		return returnModel;
	}
	public Boolean sendDeleteRRFEmail(int id,int rrfId){
		logger.info(" RequestRequisitionDashboardServiceImpl sendDeleteRRFEmail method starts");
		RequestRequisitionSkill requestRequisitionSkillObj  =  getRequestRequisitionSkillByReqSkillId(rrfId).get(0);
		String reqId = Integer.toString(rrfId);
		String[] tomailIDsList =null;
		String[] ccmailIDsList =null;
		Boolean isSuccess =false;
		Map<String, Object> returnModel = null;
		
		
		
		try{
			returnModel = setContentForDeleteRRFEmail(id, rrfId, requestRequisitionSkillObj);
			
			//Code to get toMailIds and cc MailIds
			if(returnModel!=null) {
				tomailIDsList = (String[]) returnModel.get(Constants.TOMAILIDSLIST);
				ccmailIDsList = (String[]) returnModel.get(Constants.CCMAILIDSLIST);
				if(tomailIDsList!=null) {
					emailHelper.sendEmail(returnModel, tomailIDsList, ccmailIDsList, null, reqId);
					isSuccess = true;
				}else {
					isSuccess = false;
					logger.info("------RequestRequisitionDashboardServiceImpl sendDeleteRRFEmail Failed as toMailIds are not available");
				}
			}else {
				isSuccess = false;
			}
		}catch (Exception exception) {
			isSuccess= false;
			logger.info("------RequestRequisitionDashboardServiceImpl sendDeleteRRFEmail Failed with exception " + exception.getMessage());
		} 
		logger.info(" RequestRequisitionDashboardServiceImpl sendDeleteRRFEmail method ends");
		return isSuccess;
	}
	
	private String[] getEmailArray(String sentMailTo, String notifyMailTo, String pdlEmailIds){
		logger.info(" RequestRequisitionDashboardServiceImpl getEmailArray method Start");
		String[] allStakeHoldersEmail = null;
		ArrayList<Integer> sentMailToArray = null;
		ArrayList<Integer> notifyMailToArray= null;
		ArrayList<Integer> pdlEmailIdsArray= null;
		Set<String> emailIdsOfSent =new HashSet<String>();
		try {
			sentMailToArray = new ArrayList<Integer>();
			notifyMailToArray  = new ArrayList<Integer>();
			pdlEmailIdsArray = new ArrayList<Integer>();
			
			sentMailToArray = parseEmailIds(sentMailTo);
			notifyMailToArray = parseEmailIds(notifyMailTo);
			pdlEmailIdsArray = parseEmailIds(pdlEmailIds);
			
			if(sentMailToArray!=null && !sentMailToArray.isEmpty()) {
				emailIdsOfSent.addAll(resourceService.findEmailById(sentMailToArray));
			}
			if(notifyMailToArray!=null && !notifyMailToArray.isEmpty()) {
				emailIdsOfSent.addAll(resourceService.findEmailById(notifyMailToArray));
			}
			if(pdlEmailIdsArray!=null && !pdlEmailIdsArray.isEmpty()) {
				
			
				emailIdsOfSent.addAll(pdlEmailGroupService.findPdlByIds(pdlEmailIdsArray));
			}
			
			logger.debug("emailIdsOfSent :: "+ emailIdsOfSent.toArray());
			if(!emailIdsOfSent.isEmpty()) {
				allStakeHoldersEmail = new String[emailIdsOfSent.size()];
				emailIdsOfSent.toArray(allStakeHoldersEmail);
			}
			
		}catch(Exception exception) {
			allStakeHoldersEmail = null;
			logger.error(" RequestRequisitionDashboardServiceImpl getEmailArray method ends with exception " +exception.getMessage());
		}
		logger.info(" RequestRequisitionDashboardServiceImpl getEmailArray method ends");
		return allStakeHoldersEmail;
	}
	
	private ArrayList<Integer> parseEmailIds(String emailIds){
		logger.info(" RequestRequisitionDashboardServiceImpl parseEmailIds method start");
		ArrayList<Integer> emailIdList= null;
		
		String splitIds[]= null;
		if(emailIds!=null) {
			splitIds = emailIds.split(",");
		}
		if(splitIds!=null) {
			emailIdList = new ArrayList<Integer>();
			for(String ss:splitIds)
			{
				if(!ss.trim().isEmpty())
				{
					try {
						emailIdList.add(Integer.parseInt(ss.trim()));
					}catch(Exception exception) {
				           
						logger.info("------RequestRequisitionDashboardServiceImpl parseEmailIds Failed with exception " + exception.getMessage());
						
					}
				}
			
			}
		}
		logger.info(" RequestRequisitionDashboardServiceImpl parseEmailIds method ends");
		return emailIdList;
	}
	
	@Transactional
	public void updateInterviewPanels(final String requestJson) {

		try {
			JSONObject jsonObject = new JSONObject(requestJson);
			int skillRequestId = jsonObject.getInt("skillRequestId");
			RequestRequisitionSkill requestRequisitionSkill = findRequestRequisitionSkillBySkillId(skillRequestId);
			if (requestRequisitionSkill != null) {
				if (jsonObject.has("keyInterviewPanelOne"))
					requestRequisitionSkill.setKeyInterviewersOne(jsonObject.getString("keyInterviewPanelOne"));
				if (jsonObject.has("keyInterviewPanelTwo"))
					requestRequisitionSkill.setKeyInterviewersTwo(jsonObject.getString("keyInterviewPanelTwo"));
				if (!requestRequisitionSkillDao.saveOrUpdate(requestRequisitionSkill))
					throw new RMSServiceException(DAOException._UPDATE_FAILED, "Invalid RRF Id");
			} else {
				throw new RMSServiceException(DAOException._UPDATE_FAILED, "Invalid RRF Id");
			}
		} catch (JSONException jsx) {
			logger.error("--------Json Parsing Exception-------- >" + jsx.getMessage());
			jsx.printStackTrace();
			throw new RMSServiceException(DAOException._UPDATE_FAILED, "Something went wrong !");
		} catch (Exception ex) {
			RMSServiceException rmsServiceException = new RMSServiceException();
			if (ex instanceof DAOException) {
				rmsServiceException.setMessage(((DAOException) ex).getErrCode());
				rmsServiceException.setErrCode(ex.getMessage());
			} else {
				logger.error("--------Exception while UpdateInterviewPanels-------- >" + ex.getMessage());
				rmsServiceException.setMessage("Something Went Wrong !");
				rmsServiceException.setErrCode("404");
				ex.printStackTrace();
			}
			throw rmsServiceException;
		}
	}

	public List<RequestRequisitionSkill> getRequestRequisitionList(Integer userId, String userRole, HttpServletRequest httpRequest) {
		logger.info("------------------getRequestRequisitionList for dataTable method start-----------");
		List<Integer> projectList = getProjectList(userId,userRole);
		SearchCriteriaGeneric searchCriteriaGeneric=DataTableParser.getSerchCriteria(httpRequest,new RequestRequisitionSkill());
		List<RequestRequisitionSkill> rrfList = requestRequisitionSkillDao.getRequestRequisitionList(userId,userRole,projectList,httpRequest,searchCriteriaGeneric);
		logger.info("------------------getRequestRequisitionList for dataTable method end-----------");
		return rrfList;
	}
	
	public Long getTotalCountRRF(Integer userId, String userRole,HttpServletRequest httpRequest) throws Exception{
		Long count = 0L;
		
		SearchCriteriaGeneric searchCriteriaGeneric=DataTableParser.getSerchCriteria(httpRequest,new RequestRequisitionSkill());
		try {
			List<Integer> projectList = getProjectList(userId,userRole);
		count =  requestRequisitionSkillDao.getTotalCountRRF(userId,userRole,projectList,searchCriteriaGeneric);
		
		}catch (Exception e) {

			count = 0L;
			e.printStackTrace();
			logger.error("Exception Occurred while counting in Request Requisition "+ e.getMessage());
			throw e;
		}
		return count;
	}
	private List<Integer> getProjectList(Integer userId,String userRole){
		logger.info("-----------getProjectList DaoImpl method Start-----------------------");
		List<Integer> projectList = null;
		try{
			
			
			if(userRole.equalsIgnoreCase(Constants.ROLE_DEL_MANAGER) || userRole.equalsIgnoreCase(Constants.ROLE_MANAGER)
					|| userRole.equalsIgnoreCase(Constants.ROLE_BG_ADMIN) || userRole.equalsIgnoreCase(Constants.ROLE_HR)){
				 projectList =  projectAllocationService.getProjectsIdsForUser(userId, userRole, "active");
				/*if(projectDTOList != null){
					projectList = new ArrayList<Integer>();
					for(ProjectDTO projectDTO : projectDTOList){
						projectList.add(projectDTO.getProjectId());
					}
				}*/
				
			}
			
		}catch (Exception e) {
			logger.info("-----------getProjectList DaoImpl method Exception-----------------------");
			e.printStackTrace();
		}
		
		logger.info("-----------getProjectList DaoImpl method Start-----------------------");
		return projectList;
	}
	
	@Transactional
	public RequestRequisitionSkillDTO copyRRFBySkillId(int skillRequestId) {
		logger.info("-------------copyRRFBySkillId method start--------------");
		
		List<RequestRequisitionSkill> list = new ArrayList<RequestRequisitionSkill>();
		List<RequestRequisitionSkillDTO> requestRequisitionSkillDTOs = new ArrayList<RequestRequisitionSkillDTO>();
		RequestRequisitionSkillDTO requestRequisitionSkillDTO = new RequestRequisitionSkillDTO();
		
		try {
			RequestRequisitionSkill requestRequisitionSkill = findRequestRequisitionSkillBySkillId(skillRequestId);
			if (requestRequisitionSkill != null) {
				RequestRequisitionSkill copiedObject = dozerMapperUtility.copyRequestRequisitionSkillEntityToAnotherEntity(requestRequisitionSkill);
				
				if (copiedObject != null) {
					list.add(copiedObject);
				}
				
				requestRequisitionSkillDTOs = requestRequisitionSkillMapper.convertDomainsToDTOs(list);
				if (!requestRequisitionSkillDTOs.isEmpty()) {
					requestRequisitionSkillDTO = requestRequisitionSkillDTOs.get(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("-------------copyRRFBySkillId method end--------------");
		return requestRequisitionSkillDTO;
	}

	private void sendEmailAfterCopyingRRF(RequestRequisitionSkill copiedObject) {
		
		RequestRequisitionFormDTO requestRequisitionFormDTO= dozerMapperUtility.convertRequestRequisitionSkillToRequestRequisitionFormDTO(copiedObject);
		requestRequisitionService.sendEmail(requestRequisitionFormDTO,false,false,false,true);
		
	}

	public boolean hardDeleteSkillRequest(Integer skillId) {
		boolean isDeleted = requestReportDao.hardDeleteSkillRequest(skillId);
		return isDeleted;
	}

	public RequestRequisitionWrapper getDataForScheduleInterviewByskillId(String skillid, Integer skillResourceId) {
		
		List<RequestRequisitionSkill>  list = getRequestRequisitionSkillByReqSkillId(Integer.parseInt(skillid));
		
		RequestRequisition requestRequisition = requestRequisitionDao.findRequestRequisitionById(list.get(0).getRequestRequisition().getId());
		RequestRequisitionSkill requisitionSkill = list.get(0);
		RequestRequisitionWrapper requestRequisitionWrapper = new RequestRequisitionWrapper();
		RequestRequisitionResource requestRequisitionResource = new RequestRequisitionResource();
		try {
			requestRequisitionResource = requestRequisitionResourceDao.getRequestResourceById(skillResourceId);
		
		requestRequisitionWrapper.setCustomerName(requestRequisition.getCustomer().getCustomerName());
		requestRequisitionWrapper.setDeliveryPOCName(requestRequisition.getDeliveryPOC().getEmployeeName());
		requestRequisitionWrapper.setDeliveryPOCContact(requestRequisition.getDeliveryPOC().getContactNumber());
		requestRequisitionWrapper.setJobDescription(requisitionSkill.getPrimarySkills());
		requestRequisitionWrapper.setRequestorsBGBU(requestRequisition.getProjectBU());
		requestRequisitionWrapper.setRequirementID(requisitionSkill.getRequirementId());
		requestRequisitionWrapper.setSkillCategory(requisitionSkill.getSkill().getSkill());
		requestRequisitionWrapper.setJobLocation(requisitionSkill.getLocation().getLocation());
		
		if(requestRequisitionResource!=null && requestRequisitionResource.getExternalResourceName()==null) {
			EditProfileDTO resource  = resourceService.getResourceDetailsByEmployeeId(requestRequisitionResource.getResource().getEmployeeId());
			requestRequisitionWrapper.setResourceName(resource.getFirstName().concat(" ").concat(resource.getLastName()));
			requestRequisitionWrapper.setResourceContactNumber(resource.getContactNumberOne());
			requestRequisitionWrapper.setResourceEmpId(resource.getYashEmployeeId());
		} else {
			requestRequisitionWrapper.setResourceName(requestRequisitionResource.getExternalResourceName());
			requestRequisitionWrapper.setResourceContactNumber(requestRequisitionResource.getContactNumber());
		}
		
		
		if(requestRequisition.getNotifyMailTo()!=null && !requestRequisition.getNotifyMailTo().isEmpty() ) {
			String notify = requestRequisition.getNotifyMailTo();
			if(!notify.isEmpty() && notify!=null) {
				String[] notifyids = notify.split(",");
				
				for (int i=0; i<notifyids.length ; i++) {
					notifyids[i] = notifyids[i].replaceAll("\\[", "").replaceAll("\\]", "").trim();
					System.out.println("mail ids from front end :"+notifyids[i]);
				}
				
				ArrayList<String> notifyToMailIdsInStringFormat = new ArrayList<String>();
				ArrayList<Integer> notifyToMailIdsIninteger = new ArrayList<Integer>();
				for (String string : notifyids) {
					notifyToMailIdsIninteger.add(Integer.parseInt(string.trim()));
					notifyToMailIdsInStringFormat.add(string);
				}
			String notifyMails = "";
				List<String> emailsForNotifyToMail = resourceService.findEmailById(notifyToMailIdsIninteger);
				for (String mailId : emailsForNotifyToMail) {
					notifyMails = notifyMails + "," + mailId;
				}
				notifyMails = notifyMails.substring(1);
				requestRequisitionWrapper.setNotifyToMailIds(notifyMails);
			} 
			
			
		}
		
		if(requestRequisition.getPdlEmailIds()!=null && !requestRequisition.getPdlEmailIds().isEmpty() )  {
			String pdls = requestRequisition.getPdlEmailIds();
			if(!pdls.isEmpty() && pdls!=null) {
				String[] pdlIds = pdls.split(",");
				ArrayList<Integer> pdlInintegers = new ArrayList<Integer>();
				ArrayList<String> pdlIDsInString = new ArrayList<String>();
				
				for (String string : pdlIds) {
					if(string!=null && !string.contains("null"))
					{
						String s1=string.replaceAll("\\[", "").replaceAll("\\]", "").trim();
						if(!s1.equalsIgnoreCase("null"))
						{
					pdlInintegers.add(Integer.parseInt(string.replaceAll("\\[", "").replaceAll("\\]", "").trim()));
					pdlIDsInString.add(string.replaceAll("\\[", "").replaceAll("\\]", "").trim());
						}
					}
				}
				
				List<String> pdlMailsTo = pdlEmailGroupService.findPdlByIds(pdlInintegers);
				
				String pdlEmailIds = "";
				for (String mailId : pdlMailsTo) {
					pdlEmailIds = pdlEmailIds + ","+ mailId;
				}
				pdlEmailIds =pdlEmailIds.substring(1);
				requestRequisitionWrapper.setPdlsToMailIds(pdlEmailIds);
			}
			
		}
		
		if(requestRequisition.getSentMailTo()!=null && !requestRequisition.getSentMailTo().isEmpty() ){
			List<String> emailsForToMail = new ArrayList<String>();
			String mailTo = requestRequisition.getSentMailTo();
			if(!mailTo.isEmpty() && mailTo!=null) {
			String[] mailToArray = mailTo.split(",");
			
			for (int i=0; i<mailToArray.length ; i++) {
				mailToArray[i] = mailToArray[i].replaceAll("\\[", "").replaceAll("\\]", "").trim();
				System.out.println("mail ids from front end :"+mailToArray[i]);
			}
			
			ArrayList<String> sendToMailIdsInStringFormat = new ArrayList<String>();
			ArrayList<Integer> sendToMailIdsIninteger = new ArrayList<Integer>();
			for (String string : mailToArray) {
				sendToMailIdsIninteger.add(Integer.parseInt(string.trim()));
				sendToMailIdsInStringFormat.add(string);
			}
		
			emailsForToMail = resourceService.findEmailById(sendToMailIdsIninteger);
			
			String mailsToIds = "";
			for (String string : emailsForToMail) {
				mailsToIds = mailsToIds + "," + string;
			}
			mailsToIds =  mailsToIds.substring(1);
			requestRequisitionWrapper.setMailsToMailIds(mailsToIds);
			}
		}
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return requestRequisitionWrapper;
	}
	
	
}
