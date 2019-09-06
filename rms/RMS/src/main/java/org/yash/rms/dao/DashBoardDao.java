package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.DashboardComplianceWidget;
import org.yash.rms.domain.DashboardWidgets;
import org.yash.rms.domain.Dashboard_temp;
import org.yash.rms.domain.Dashboardtempsecond;
import org.yash.rms.domain.OrgHierarchy;

public interface DashBoardDao {

	public List<Object[]> findUserdashboard(int empID);
	
	public List<Object[]> findUserTimeSheet(int empID);
	
	
	public List<Object[]> findUserstatusoflastFourMonth(int empID);  
	
	public List<Object[]> findUsertodolist(int empID);
	
	public List<Object[]> findManagerBillingStatusList(int empID);

	public List<Object[]> findDeliveryManagerBillingStatusList(List<String> projectName); 
	
	public List<Object[]> findDeliveryManagerResourceBillingStatus(List<String> projectName);
	
	public List<Object[]> findDeliveryManagerResourceBillingStatusList(int empID);

	public List<Object[]> findManagerTimeSheetStatus(List<Integer> empIds, Integer employeeId);

	public List<Object[]> findManagerTimeSheetCompliance(List<Integer> empIds);
	
	public List<Object[]> findBGAdminTimeSheetCompliance(List<Integer> empIds);
	
	public List<Dashboard_temp> getDatabyBGBU(List<String> project_bg_bu);
	
	public List<Dashboard_temp> getDatabyProjectName(List<String> projectNameList);
	
	public List<Dashboardtempsecond> getBillingStatusListbyBGBU(List<String> project_bg_bu);
	
	public List<Dashboardtempsecond> getBillingStatusListbyProjectName(List<String> projectNameList);
	
	public List<Dashboardtempsecond> getBillingStatusListbyManagerID(int deliveryManagerID);
	
	public List<DashboardWidgets> getWidgetDataByEmployeeID(List<Integer> empIDList);
	
	public List<DashboardComplianceWidget> getComplianceWidgetDataByEmployeeID(List<Integer> empIDList);
}
