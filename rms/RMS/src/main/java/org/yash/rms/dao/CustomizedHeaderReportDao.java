
package org.yash.rms.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.yash.rms.report.dto.MuneerReport;

public interface CustomizedHeaderReportDao {

	public Set<MuneerReport> getHeaderReport(List<Integer> orgIdList,List<Integer> locIdList, List<Integer> projIdList, String roleId,
			String reportId, Date weekDate, List<String> sortableList);

}
