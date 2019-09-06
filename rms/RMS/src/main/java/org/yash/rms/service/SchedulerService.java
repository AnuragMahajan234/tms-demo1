package org.yash.rms.service;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.InfogramActiveResourceDTO;
import org.yash.rms.exception.RMSServiceException;
import org.yash.rms.util.SearchCriteriaGeneric;

public interface SchedulerService {
	
	public void getAllInfoActiveResourceScheduler() throws Exception;
	public void  checkingAllResStatusScheduler();
	public void getAllInfoInactiveResourceScheduler() throws Exception ;
	//public void setDefaultProjectforBlockedResource();
	
	public void thirtyDaysblockedResourceReportEmail() ;
	
	public String thirtyDaysblockedResourceReportEmailFromController(Integer days , Date date);
	
	public void runProjectGoingtoCloseScheduler();
}

