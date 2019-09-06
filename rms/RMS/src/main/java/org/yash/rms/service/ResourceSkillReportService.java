package org.yash.rms.service;

import java.util.List;
import java.util.Set;

import org.yash.rms.report.dto.ResourceSkillReport;

public interface ResourceSkillReportService {
	
	Set<ResourceSkillReport> getResourceSkillReport(List<Integer> orgIdList, List<Integer> allocationTypes);

}
