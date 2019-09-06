package org.yash.rms.dao;

import java.util.List;
import java.util.Set;

import org.yash.rms.report.dto.ResourceSkillReport;

public interface ResourceSkillReportDao {
	Set<ResourceSkillReport> getResourceSkillReport(List<Integer> orgIdList, List<Integer> allocationTypes);

}
