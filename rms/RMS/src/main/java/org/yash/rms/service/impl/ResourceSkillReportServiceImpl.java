package org.yash.rms.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ResourceSkillReportDao;
import org.yash.rms.report.dto.ResourceSkillReport;
import org.yash.rms.service.ResourceSkillReportService;

@Service("ResourceSkillReportService")
public class ResourceSkillReportServiceImpl implements ResourceSkillReportService {
	
	@Autowired @Qualifier("ResourceSkillReportDao")
	ResourceSkillReportDao resourceSkillReportDao;

	public Set<ResourceSkillReport> getResourceSkillReport(List<Integer> orgIdList, List<Integer> allocationTypes) {

		return resourceSkillReportDao.getResourceSkillReport(orgIdList, allocationTypes);
	}

}
