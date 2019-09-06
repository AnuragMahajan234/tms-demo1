package org.yash.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.yash.rms.domain.Dashboard_temp;
import org.yash.rms.domain.Dashboardtempsecond;
import org.yash.rms.dto.ResourceDashBoardDTO;
import org.yash.rms.report.dto.ManagerTimeSheetGraphDTO;
import org.yash.rms.report.dto.UserTimeSheetGraphDTO;

@Service("DashBoardService")
public interface DashBoardService {
	
	public ResourceDashBoardDTO findTimeSheet(int employeeId);
	
	public List<UserTimeSheetGraphDTO> findUserstatusoflastFourMonth(int employeeId);

	public ResourceDashBoardDTO findTimeSheetForUserRole(Integer employeeId);

	public List<Dashboard_temp> getDatabyBGBU(List<String> project_bg_bu_list);

	public List<Dashboard_temp> getDatabyProjectName(List<String> projectNames);

	public List<Dashboardtempsecond> getBillingStatusListbyBGBU(List<String> project_bg_bu_list);

	public List<Dashboardtempsecond> getBillingStatusListbyProjectName(List<String> projectNames);

	public List<Dashboardtempsecond> getBillingStatusListbyManagerID(int deliveryManagerID);

	public List<ManagerTimeSheetGraphDTO> getGraphDataForManager(String userRole, int employeeId);
	
	public Object getCurrentResourceUserRole(Integer employeeId);

	//TODO : will be added after 9th August 2017 deployment.
	//public PlatformDetails findPlatformDetails(String platform);
}
