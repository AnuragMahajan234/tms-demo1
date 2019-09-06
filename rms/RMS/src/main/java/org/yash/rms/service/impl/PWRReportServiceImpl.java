package org.yash.rms.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.PWRReportDao;
import org.yash.rms.report.dto.PWRReport;
import org.yash.rms.service.PWRReportService;

@Service("pwrReportService")
@SuppressWarnings("rawtypes")
public class PWRReportServiceImpl implements PWRReportService {

	@Autowired
	@Qualifier("pwrReportDao")
	PWRReportDao pwrReportDao;

	public Set<PWRReport> getPWRReportVeiw1(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, List<Integer> ownership, Collection date, int view) {

		return pwrReportDao.getPWRReportView1(orgIdList, locIdList, projIdList, ownership, date, view);
	}

	public Map<String, List<PWRReport>> getPWRReportVeiw2(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, List<Integer> ownership, Collection date, int view) {

		return pwrReportDao.getPWRReportView2(orgIdList, locIdList, projIdList, ownership, date, view);
	}

}
