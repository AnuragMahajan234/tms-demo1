package org.yash.rms.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.DashBoardDao;
import org.yash.rms.dao.ProjectAllocationDao;
import org.yash.rms.dao.ProjectDao;
import org.yash.rms.dao.ResourceAllocationDao;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.domain.BGAdmin_Access_Rights;
import org.yash.rms.domain.DashboardWidgets;
import org.yash.rms.domain.Dashboard_temp;
import org.yash.rms.domain.Dashboardtempsecond;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.ProjectDTO;
import org.yash.rms.dto.ResourceDashBoardDTO;
import org.yash.rms.report.dto.ManagerTimeSheetGraphDTO;
import org.yash.rms.report.dto.UserTimeSheetGraphDTO;
import org.yash.rms.service.DashBoardService;
import org.yash.rms.service.ProjectAllocationService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.ProjectAllocationSearchCriteria;
import org.yash.rms.util.UserUtil;

@Service("DashBoardService")
public class DashBoardServiceImpl implements DashBoardService {

	@Autowired
	private DashBoardDao dashBoardDao;

	@Autowired
	ProjectAllocationService projectAllocationService;

	@Autowired
	@Qualifier("ResourceDao")
	ResourceDao resourceDao;

	@Autowired
	@Qualifier("ProjectAllocationDao")
	ProjectAllocationDao projectAllocationDao;

	@Autowired
	@Qualifier("ResourceAllocationDao")
	ResourceAllocationDao resourceAllocationDao;

	@Autowired
	@Qualifier("projectDaoImpl")
	ProjectDao projectDao;
	
	//TODO : will be added after 9th August 2017 deployment.
	/*@Autowired	
	@Qualifier("platformDetailsDao")
	PlatformDetailsDao platformDetailsDao;*/

	public List<Dashboard_temp> getDatabyBGBU(List<String> project_bg_bu) {
		return dashBoardDao.getDatabyBGBU(project_bg_bu);
	}

	public List<Dashboard_temp> getDatabyProjectName(List<String> projectNameList) {
		return dashBoardDao.getDatabyProjectName(projectNameList);
	}

	public List<Dashboardtempsecond> getBillingStatusListbyBGBU(List<String> project_bg_bu) {
		return dashBoardDao.getBillingStatusListbyBGBU(project_bg_bu);
	}

	public List<Dashboardtempsecond> getBillingStatusListbyProjectName(List<String> projectNameList) {
		return dashBoardDao.getBillingStatusListbyProjectName(projectNameList);
	}

	public List<Dashboardtempsecond> getBillingStatusListbyManagerID(int deliveryManagerID) {
		return dashBoardDao.getBillingStatusListbyManagerID(deliveryManagerID);
	}

	public ResourceDashBoardDTO findTimeSheet(int empID) {

		List<Object[]> userTimeStatus = dashBoardDao.findUserTimeSheet(empID);

		ResourceDashBoardDTO userActivityDTO = new ResourceDashBoardDTO();
		Iterator iter = userTimeStatus.iterator();
		while (iter.hasNext()) {
			Object[] result = (Object[]) iter.next();
			String status = result[1].toString();
			if (status.trim().equalsIgnoreCase("P")) {

				// for hyper link
				String temp = result[0].toString();
				String[] words = temp.split("-");

				String year = words[0];
				String maonth = words[1];
				String day = words[2];
				String date = maonth + "/" + day + "/" + year;
				userActivityDTO.getPending().add(date); // date hor hyperlink

			} else if (status.trim().equalsIgnoreCase("A")) {

				// for hyper link
				String temp = result[0].toString();
				String[] words = temp.split("-");

				String year = words[0];
				String maonth = words[1];
				String day = words[2];
				String date = maonth + "/" + day + "/" + year;
				userActivityDTO.getApproved().add(date); // date hor hyperlink

			} else if (status.trim().equalsIgnoreCase("R")){

				// for hyper link
				String temp = result[0].toString();
				String[] words = temp.split("-");

				String year = words[0];
				String maonth = words[1];
				String day = words[2];
				String date = maonth + "/" + day + "/" + year;
				userActivityDTO.getRejected().add(date); // date hor hyperlink
			}
		}
		userTimeStatus = dashBoardDao.findUsertodolist(empID);
		iter = userTimeStatus.iterator();
		while (iter.hasNext()) {
			Date date = (Date) iter.next();

			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			String dateInString = formatter.format(date);

			userActivityDTO.getNotSubmitted().add(dateInString);

		}
		return userActivityDTO;
	}

	public List<UserTimeSheetGraphDTO> findUserstatusoflastFourMonth(int empID) {

		List<Object[]> userTimeSheetStatus = dashBoardDao.findUserstatusoflastFourMonth(empID);

		List<UserTimeSheetGraphDTO> userTimeSheetGraphDTOList = new ArrayList<UserTimeSheetGraphDTO>();

		if (userTimeSheetStatus != null && userTimeSheetStatus.size() > 0) {

			UserTimeSheetGraphDTO userTimeSheetGraphDTO;

			Iterator itr = userTimeSheetStatus.iterator();
			try {
				while (itr.hasNext()) {

					userTimeSheetGraphDTO = new UserTimeSheetGraphDTO();

					Object[] result = (Object[]) itr.next();

					Date date = (Date) result[0];
					String datecalender = date.toString();

					userTimeSheetGraphDTO.setWeekEndDate(datecalender);
					userTimeSheetGraphDTO.setApprovedCount(result[1].toString());
					userTimeSheetGraphDTO.setPendingCount(result[2].toString());
					userTimeSheetGraphDTO.setNotSubmittedCount(result[3].toString());
					userTimeSheetGraphDTO.setRejectedCount(result[4].toString());

					userTimeSheetGraphDTOList.add(userTimeSheetGraphDTO);
				}

			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}

		return userTimeSheetGraphDTOList;
	}

	public List<ManagerTimeSheetGraphDTO> getGraphDataForManager(String userRole, int employeeId) {

		List<Dashboard_temp> deliveryManagerBillingStatus = null;
		List<String> listOfProject = null;

		List<String> newProjectList = new ArrayList<String>();
		List<ProjectDTO> projectDisplay = new ArrayList<ProjectDTO>();

		List<OrgHierarchy> buList = null;

		ManagerTimeSheetGraphDTO managerTimeSheetDTO = null;

		List<ManagerTimeSheetGraphDTO> managerTimeSheetDtoList = new ArrayList<ManagerTimeSheetGraphDTO>();

		if (userRole.equalsIgnoreCase(Constants.ROLE_DEL_MANAGER)) {

			List<Integer> empIdList = new ArrayList<Integer>();
			List<String> projectNameList = new ArrayList<String>();
			projectNameList = projectAllocationService.getProjectNameForManager("active", employeeId);
			listOfProject = projectAllocationService.findCountProjectNameForDashboardBillingStatus(empIdList, "active", projectNameList);
			deliveryManagerBillingStatus = getDatabyProjectName(listOfProject);

		} else if (userRole.equalsIgnoreCase(Constants.ROLE_BG_ADMIN)) {
			buList = UserUtil.getCurrentResource().getAccessRight();
			listOfProject = projectAllocationService.findAllActiveProjectsForBGAdminDashboard(buList, null);
			deliveryManagerBillingStatus = getDatabyProjectName(listOfProject);

		} else {
			listOfProject = new ArrayList<String>();
			ProjectAllocationSearchCriteria projectSearchCriteria = new ProjectAllocationSearchCriteria();
			projectSearchCriteria.setOrderStyle("asc");
			;
			projectSearchCriteria.setOrederBy("id");
			projectSearchCriteria.setRefColName("id");
			projectDisplay = projectAllocationService.findAllActiveProjects(0, 50, projectSearchCriteria, "active", "", null, employeeId);
			Iterator itrx = projectDisplay.iterator();

			while (itrx.hasNext()) {
				ProjectDTO listProject = (ProjectDTO) itrx.next();
				listOfProject.add(listProject.getProjectName());

			}
			deliveryManagerBillingStatus = getDatabyProjectName(listOfProject);

		}

		if (deliveryManagerBillingStatus != null && deliveryManagerBillingStatus.size() > 0) {
			Iterator itr = deliveryManagerBillingStatus.iterator();

			try {

				while (itr.hasNext()) {
					managerTimeSheetDTO = new ManagerTimeSheetGraphDTO();
					Dashboard_temp result = (Dashboard_temp) itr.next();
					newProjectList.add(result.getProject_name());
					managerTimeSheetDTO.setProjectName(result.getProject_name());
					if (result.getBillable_resources() != null) {
						managerTimeSheetDTO.setBillable(result.getBillable_resources());
					}

					else {
						managerTimeSheetDTO.setBillable(0);
					}

					if (result.getNon_billable_resources() != null) {
						managerTimeSheetDTO.setOthers(result.getNon_billable_resources());
					}

					else {
						managerTimeSheetDTO.setOthers(0);
					}
					managerTimeSheetDtoList.add(managerTimeSheetDTO);
					managerTimeSheetDTO = null;
					// obj.put(list);
				}

				for (int i = 0; i < listOfProject.size(); i++) {
					for (int j = 0; j < newProjectList.size(); j++) {
						if (newProjectList.contains(listOfProject.get(i))) {
							break;
						} else {
							managerTimeSheetDTO = new ManagerTimeSheetGraphDTO();
							JSONObject list1 = new JSONObject();
							managerTimeSheetDTO.setProjectName(listOfProject.get(i));
							managerTimeSheetDTO.setBillable(0);
							managerTimeSheetDTO.setOthers(0);

							managerTimeSheetDtoList.add(managerTimeSheetDTO);

							break;
						}

					}

				}

			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} else {

			managerTimeSheetDTO = new ManagerTimeSheetGraphDTO();
			managerTimeSheetDTO.setProjectName("None");
			managerTimeSheetDTO.setBillable(0);
			managerTimeSheetDTO.setOthers(0);
			managerTimeSheetDtoList.add(managerTimeSheetDTO);
		}

		return managerTimeSheetDtoList;
	}

	public ResourceDashBoardDTO findTimeSheetForUserRole(Integer employeeId) {

		ResourceDashBoardDTO userTimeSheetDTO = new ResourceDashBoardDTO();

		Object userRole = resourceDao.getCurrentResourceUserRole(employeeId);

		System.out.println("------------------------------------------>>>>>>>> " + userRole);

		if (userRole.equals("ROLE_BG_ADMIN")) {

			boolean managerFlag = false;

			List<Integer> empIdList = new ArrayList<Integer>();

			List<DashboardWidgets> dashBoardWidgetList = null;

			Resource resource = resourceDao.findByEmployeeId(employeeId);

			Set<BGAdmin_Access_Rights> bgAdminAccessRightlist = resource.getBgAdminAccessRightlist();

			Set<Integer> bgAdminList = new HashSet<Integer>();

			if (null != bgAdminAccessRightlist && !bgAdminAccessRightlist.isEmpty()) {

				Iterator<BGAdmin_Access_Rights> iterator = bgAdminAccessRightlist.iterator();

				while (iterator.hasNext()) {

					BGAdmin_Access_Rights access_Rights = iterator.next();

					if (!(access_Rights.getOrgId().getParentId().getName().equalsIgnoreCase("ORGANIZATION"))) {
						bgAdminList.add(access_Rights.getOrgId().getId());
					}

				}
			}

			List<OrgHierarchy> selectbuList = new ArrayList<OrgHierarchy>();
			List<OrgHierarchy> buList = null;

			buList = resourceDao.getBUListForBGADMIN(resource); // buList =
																// UserUtil.getCurrentResource().getAccessRight()
			Iterator<OrgHierarchy> itrs = buList.iterator();

			while (itrs.hasNext()) {

				OrgHierarchy org = itrs.next();

				if (!bgAdminList.equals("")) {

					List<Set<Integer>> items = Arrays.asList(bgAdminList);

					Iterator iteratorItems = items.iterator();

					while (iteratorItems.hasNext()) {

						Set<Integer> x = (Set<Integer>) iteratorItems.next();

						if (x.equals(org.getId())) {

							System.out.println("value of x inside submitLoadCustomWidgetsStatus" + x);

							selectbuList.add(org);
						}
					}

					managerFlag = true;
				}
			}

			if (managerFlag) {

				empIdList = resourceDao.findReourceIdsByBusinessGroupDashboard(selectbuList);
			} else {
				empIdList = resourceDao.findActiveResourceIdByRM2RM1(employeeId);
			}

			dashBoardWidgetList = dashBoardDao.getWidgetDataByEmployeeID(empIdList);

			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

			for (DashboardWidgets dashboardWidgets : dashBoardWidgetList) {

				String status = dashboardWidgets.getStatus();
				Set<Integer> itemIds = new HashSet<Integer>();

				if (status.trim().equalsIgnoreCase("P")) {

					Integer itemId = dashboardWidgets.getEmployee_count();
					itemIds.add(itemId);

					String weekEndDate = dateFormat.format(dashboardWidgets.getWeek_end_date());

					userTimeSheetDTO.getPending().add(weekEndDate + " : " + dashboardWidgets.getEmployee_count() + " : " + weekEndDate);

				} else if (status.trim().equalsIgnoreCase("A")) {

					String weekEndDate = dateFormat.format(dashboardWidgets.getWeek_end_date());

					userTimeSheetDTO.getApproved().add(weekEndDate + " : " + dashboardWidgets.getEmployee_count() + " : " + weekEndDate);

				} else if (status.trim().equalsIgnoreCase("R")) {

					String weekEndDate = dateFormat.format(dashboardWidgets.getWeek_end_date());

					userTimeSheetDTO.getRejected().add(weekEndDate + " : " + dashboardWidgets.getEmployee_count() + " : " + weekEndDate);
				}

			}

			List<Object[]> userTimeSheetCompliance = dashBoardDao.findBGAdminTimeSheetCompliance(empIdList);

			if (userTimeSheetCompliance != null && !userTimeSheetCompliance.isEmpty()) {
				Iterator itrtsc = userTimeSheetCompliance.iterator();
				while (itrtsc.hasNext()) {
					Object[] itr = (Object[]) itrtsc.next();

					// for hyper link
					String temp = itr[0].toString();
					String[] words = temp.split("-");

					String yearHyper = words[0];
					String maonth = words[1];
					String day = words[2];
					String date = maonth + "/" + day + "/" + yearHyper;

					int noOfResources = Math.abs(empIdList.size() - Integer.parseInt(itr[1].toString()));

					userTimeSheetDTO.getTimeSheetComplaince().add(date + ":" + noOfResources);

				}

			}

		} else if (userRole.equals("ROLE_DEL_MANAGER") || userRole.equals("ROLE_MANAGER")) {

			boolean isBehalfManager = false;

			List<Integer> empIdList = new ArrayList<Integer>();
			List<Integer> projectIds = new ArrayList<Integer>();

			projectIds = projectAllocationDao.getProjectIdsForManager(employeeId, "active");

			isBehalfManager = resourceAllocationDao.isResourceBehalfManager(employeeId);

			if (isBehalfManager) {

				List<Integer> pList = projectDao.findProjectsByBehalfManagar(employeeId, "active");
				projectIds.addAll(pList);
			}

			empIdList = resourceAllocationDao.findAllocatedResourceEmployeeIdByProjectIdsDashboard(projectIds, employeeId);

			List<Object[]> userTimeStatus = dashBoardDao.findManagerTimeSheetStatus(empIdList, employeeId);

			Iterator iter = userTimeStatus.iterator();
			while (iter.hasNext()) {
				Object[] result = (Object[]) iter.next();
				String status = result[1].toString();
				if (status.trim().equalsIgnoreCase("P")) {
					String tempForPending = result[0].toString();
					String[] words = tempForPending.split("-");

					String year = words[0];
					String maonth = words[1];
					String day = words[2];
					String date = maonth + "/" + day + "/" + year;
					// date hor hyperlink

					System.out.println("date" + tempForPending);

					userTimeSheetDTO.getPending().add(result[0].toString() + " :" + result[2].toString() + " :" + date);

				} else if (status.trim().equalsIgnoreCase("A")) {
					String tempForApproved = result[0].toString();
					String[] words = tempForApproved.split("-");

					String year = words[0];
					String maonth = words[1];
					String day = words[2];
					String date = maonth + "/" + day + "/" + year;
					// date hor hyperlink

					userTimeSheetDTO.getApproved().add(result[0].toString() + " :" + result[2].toString() + " :" + date);
				} else if (status.trim().equalsIgnoreCase("R")) {

					String tempForRejected = result[0].toString();
					String[] words = tempForRejected.split("-");

					String year = words[0];
					String maonth = words[1];
					String day = words[2];
					String date = maonth + "/" + day + "/" + year;
					// date hor hyperlink

					userTimeSheetDTO.getRejected().add(result[0].toString() + " :" + result[2].toString() + " :" + date);
				}
			}

			List<Object[]> userTimeSheetCompliance = dashBoardDao.findBGAdminTimeSheetCompliance(empIdList);

			if (userTimeSheetCompliance != null && !userTimeSheetCompliance.isEmpty()) {

				Iterator itrtsc = userTimeSheetCompliance.iterator();
				while (itrtsc.hasNext()) {
					Object[] itr = (Object[]) itrtsc.next();

					// for hyper link
					String temp = itr[0].toString();
					String[] words = temp.split("-");

					String yearHyper = words[0];
					String maonth = words[1];
					String day = words[2];
					String date = maonth + "/" + day + "/" + yearHyper;

					int noOfResources = Math.abs(empIdList.size() - Integer.parseInt(itr[1].toString()));

					userTimeSheetDTO.getTimeSheetComplaince().add(date + ":" + noOfResources);

				}

			}

		}
		return userTimeSheetDTO;
	}

	public Object getCurrentResourceUserRole(Integer employeeId) {
		return resourceDao.getCurrentResourceUserRole(employeeId);
	}
	
	//TODO : will be added after 9th August 2017 deployment.
	/*public PlatformDetails findPlatformDetails(String platformName) {		
		return platformDetailsDao.findPlatformDetailsByOSType(platformName);
	}*/

}
