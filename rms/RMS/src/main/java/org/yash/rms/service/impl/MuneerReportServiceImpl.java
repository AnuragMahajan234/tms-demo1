
package org.yash.rms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.MuneerReportDao;
import org.yash.rms.report.dto.MuneerReport;
import org.yash.rms.report.dto.MuneerReportDto;
import org.yash.rms.service.MuneerReportService;

@Service("muneerReportService")
public class MuneerReportServiceImpl implements MuneerReportService {

	@Autowired
	@Qualifier("muneerReportDao")
	MuneerReportDao muneerReportDao;

	public Map<String, MuneerReportDto> getMuneerClientReport(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, String roleId, String reportId, Date weekDate) {

		return muneerReportDao.getMuneerClientReport(orgIdList, locIdList, projIdList, roleId, reportId, weekDate);
	}

	public Set<MuneerReport> getMuneerReport(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, String roleId, String reportId, Date weekDate) {

		return muneerReportDao.getMuneerReport(orgIdList, locIdList, projIdList, roleId, reportId, weekDate);
	}

}
