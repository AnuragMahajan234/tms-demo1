/**
 * 
 */
package org.yash.rms.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.yash.rms.report.dto.ModuleReport;

public interface ModuleReportDao {
	
	public List<ModuleReport> getModuleReport(List<Integer> projectId, Map<String, Date> dates);
	
	public List<Object[]> getCustomizedModuleReport(List<Integer> projectId, Date startDate, Date endDt);
}
