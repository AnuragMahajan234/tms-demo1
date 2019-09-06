package org.yash.rms.service;

import java.util.List;
import java.util.Map;

import org.yash.rms.report.dto.BUResourcesPLReport;

public interface CustomizeReportService {

	public Map<String, Object> getResourceForCustomizeReport(Integer year);
	public List<BUResourcesPLReport> getResourceOnBenchStartOfMonthReport(Integer year);
	public List<BUResourcesPLReport> getResourceOnBenchEndOfMonthReport(Integer year);
	public List<BUResourcesPLReport> getResourceOnBenchMaxOfMonthReport(Integer year);
	//public List<BUResourcesPLReport> getBillableResourceForReport(Integer year);
	public List<BUResourcesPLReport> getNewJoineeDirectAllocatedForReport(Integer year);
	public List<BUResourcesPLReport> getNewJoineeOnBenchForReport(Integer year);

}
