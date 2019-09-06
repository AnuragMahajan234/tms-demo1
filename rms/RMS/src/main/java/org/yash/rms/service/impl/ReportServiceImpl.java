package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ReportDao;
import org.yash.rms.dao.impl.ReportDaoImpl;
import org.yash.rms.service.ReportService;

@Service("ReportService")
public class ReportServiceImpl implements ReportService {

	@Autowired
	@Qualifier("reportDao")
	ReportDao reportDao;

	private static final Logger logger = LoggerFactory
			.getLogger(ReportDaoImpl.class);

	public Map<String, Object> getPLReport(List<Integer> buId, Date startDate,
			Date endDate, Integer projectTypeId, boolean includeResource) {

		logger.info("--------ReportServiceImpl getPLReport method start-------");
		
		List<Integer> projectTypeIds = new ArrayList<Integer>();
		if (projectTypeId != null && projectTypeId == -1) {
			projectTypeIds.add(0);
			projectTypeIds.add(1);
		} else {
			projectTypeIds.add(projectTypeId);
		}
		Map<String, Object> report = reportDao.getPLReport(buId, startDate, endDate, projectTypeIds, includeResource);
	
		logger.info("--------ReportServiceImpl getPLReport method end-------");

		return report;

	}

	//Report Count 
		public Integer getReportCount(){
		return reportDao.getReportCount();
		}
		
		//Update Report Count 
		public boolean updateJavaReportsCount(Integer countId){
			return reportDao.updateJavaReportsCount(countId);
		}
	
}
