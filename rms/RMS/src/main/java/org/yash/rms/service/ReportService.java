package org.yash.rms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReportService {

	public Map<String, Object> getPLReport(List<Integer> buId, Date date, Date date2, Integer projectTypeId, boolean includeResource);
	
	//Report Count 
	public Integer getReportCount();
	//Update Report Count 
	public boolean updateJavaReportsCount(Integer countId);

}
