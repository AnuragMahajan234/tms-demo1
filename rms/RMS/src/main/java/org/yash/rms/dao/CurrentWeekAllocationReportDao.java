package org.yash.rms.dao;

import java.util.Date;
import java.util.List;


public interface CurrentWeekAllocationReportDao {

	List<Object[]> getCurrentWeekAllocationReport(List<Integer> buIds, List<Integer> locationIds, List<Integer> projIds, List<Integer> ownerShipIds, Date endDate,  Date startDate, List<Integer> currentBUIds);

	
}
