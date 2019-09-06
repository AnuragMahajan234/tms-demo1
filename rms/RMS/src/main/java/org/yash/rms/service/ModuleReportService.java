/**
 * 
 */
package org.yash.rms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.yash.rms.report.dto.ModuleReport;

/**
 * @author bhakti.barve
 *
 */
public interface ModuleReportService {
	
	public Map<String, Map<String, List<ModuleReport>>> getModuleReport(List<Integer> projectId, Date weekStartDate, Date weekEndDate);
		
	public List<ModuleReport> getCustomizedModuleReport(List<Integer> projectIds, Date startDate, Date endDate);
}
