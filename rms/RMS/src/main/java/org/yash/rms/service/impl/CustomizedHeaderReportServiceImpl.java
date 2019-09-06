
package org.yash.rms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.CustomizedHeaderReportDao;
import org.yash.rms.report.dto.MuneerReport;
import org.yash.rms.service.CustomizedHeaderReportService;

@Service("customizedHeaderReportService")
public class CustomizedHeaderReportServiceImpl implements CustomizedHeaderReportService {

	@Autowired
	@Qualifier("customizedHeaderReportDao")
	CustomizedHeaderReportDao customizedHeaderReportDao;

	public Set<MuneerReport> getSelectedHeaderReport(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, String roleId, String reportId, Date weekDate, List<String> sortableList) {

		return customizedHeaderReportDao.getHeaderReport(orgIdList, locIdList, projIdList, roleId, reportId, weekDate, sortableList);
	}

}
