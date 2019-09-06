package org.yash.rms.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yash.rms.report.dto.PWRReport;

@SuppressWarnings("rawtypes")
public interface PWRReportService {

	Map<String, List<PWRReport>> getPWRReportVeiw2(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, List<Integer> ownership, Collection date, int view);

	Set<PWRReport> getPWRReportVeiw1(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, List<Integer> ownership, Collection date, int view);

}
