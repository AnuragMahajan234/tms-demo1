package org.yash.rms.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReportDao {

	public Map<String, Object> getPLReport(List<Integer> buId, Date startDate, Date endDate, List<Integer> projectTypeIds, boolean includeResource);

	//Report Count 
	public Integer getReportCount();
	//Update Report Count 
	public boolean updateJavaReportsCount(Integer countId);

}