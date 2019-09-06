package org.yash.rms.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yash.rms.report.dto.PWRReport;

@SuppressWarnings("rawtypes")
public interface PWRReportDao {

	Set<PWRReport> getPWRReportView1(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, List<Integer> ownership, Collection date, int view);

	Map<String, List<PWRReport>> getPWRReportView2(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, List<Integer> ownership, Collection date, int view);

}
