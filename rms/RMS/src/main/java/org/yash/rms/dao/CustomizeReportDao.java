package org.yash.rms.dao;

import java.util.Date;
import java.util.List;

import org.yash.rms.report.dto.BUResourcesPLReport;

public interface CustomizeReportDao {

	public List<BUResourcesPLReport> getResourceOnBenchReport(Date startDate, Date endDate);
	
	public List<BUResourcesPLReport> getBillableResources(Date startDate,
			Date endDate);

	public List<BUResourcesPLReport> getNewJoinee(Date startDate, Date endDate,
			String allocationType);
	
	public List<BUResourcesPLReport> getResourcesBenchToProject(Date startDate, Date endDate);

	public List<BUResourcesPLReport> getReleasedResourcesOfBG4BU5(
			Date startDate, Date endDate);

	public List<BUResourcesPLReport> getResourceTransferToBG4BU5(Date startDate, Date endDate);

	public List<BUResourcesPLReport> getResourceTransferFromBG4BU5(Date startDate, Date endDate);

}