package org.yash.rms.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.RMReportDao;
import org.yash.rms.report.dto.RMReport;
import org.yash.rms.service.RMReportService;
@Service("rmReportService")
public class RMReportServiceImpl implements RMReportService {
	
	@Autowired @Qualifier("rmReportDao")
    RMReportDao rmReportDao;
	
	public Set<RMReport> getRMReport(List<Integer> orgIdList,
			List<Integer> locIdList, List<Integer> projIdList, String roleId,
			String reportId) {
		return rmReportDao.getRMReport(orgIdList, locIdList, projIdList, roleId, reportId);
		 
	}

}
