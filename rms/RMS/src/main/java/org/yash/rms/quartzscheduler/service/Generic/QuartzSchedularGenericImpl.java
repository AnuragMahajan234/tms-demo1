package org.yash.rms.quartzscheduler.service.Generic;

import java.sql.Timestamp;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.service.SchedulerService;
import org.yash.rms.util.Constants;


@Component
public class QuartzSchedularGenericImpl implements Job{
	private static final Logger logger = LoggerFactory.getLogger(QuartzSchedularGenericImpl.class);

	@Autowired
	private SchedulerService schedulerService;
	
	@Autowired
	private ResourceAllocationService resourceAllocationService;
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
	    String methodName = (String) context.getJobDetail().getJobDataMap().get("methodName");
		Timestamp CurrentTime = new Timestamp(System.currentTimeMillis());
		logger.info("Schedular Method Name Is :-> " + methodName + "And Start Time Is :-> "+CurrentTime);
		try {

			JobDataMap dataMap = context.getJobDetail().getJobDataMap();
			Class clazz = Class.forName(dataMap.getString("executeClassName"));
    		Object  object  = clazz.newInstance();
    		
    		//clazz.getMethod(dataMap.getString("methodName")).invoke(object,new Object[0]);
			//schedulerService.thirtyDaysblockedResourceReportEmail();
			
			executeMethodByName(methodName);
    	} catch(Exception ex)
		{
    		ex.printStackTrace();
		}
	}

	void executeMethodByName(String methodName)
	{
		
		logger.info("Execute method named as " + methodName + " time of execution is " + new Date());
		try {
			if (methodName.equalsIgnoreCase(Constants.QRTZ_THIRTYDAYSBLOCKEDRESOURCEREPORTEMAIL)) {
				logger.info("inside QRTZ_THIRTYDAYSBLOCKEDRESOURCEREPORTEMAIL");
				schedulerService.thirtyDaysblockedResourceReportEmail();
			}
			if (methodName.equalsIgnoreCase(Constants.QRTZ_SETDEFAULTPROJECTFORBLOCKEDRESOURCE)) {
				logger.info("inside QRTZ_SETDEFAULTPROJECTFORBLOCKEDRESOURCE");
				resourceAllocationService.setDefaultProjectforBlockedResource();
			}
			if (methodName.equalsIgnoreCase(Constants.QRTZ_RUNPROJECTGOINGTOCLOSESCHEDULER)) {
				logger.info("inside QRTZ_RUNPROJECTGOINGTOCLOSESCHEDULER");
				schedulerService.runProjectGoingtoCloseScheduler();
			}
			if (methodName.equalsIgnoreCase(Constants.QRTZ_CHECKINGALLRESSTATUSSCHEDULER)) {
				logger.info("inside QRTZ_CHECKINGALLRESSTATUSSCHEDULER");
				schedulerService.checkingAllResStatusScheduler();
			}
			if (methodName.equalsIgnoreCase(Constants.QRTZ_GETALLINFOINACTIVERESOURCESCHEDULER)) {
				logger.info("inside QRTZ_GETALLINFOINACTIVERESOURCESCHEDULER");
				schedulerService.getAllInfoInactiveResourceScheduler();
			}
			if (methodName.equalsIgnoreCase(Constants.QRTZ_GETALLINFOACTIVERESOURCESCHEDULER)) {
				logger.info("inside QRTZ_GETALLINFOACTIVERESOURCESCHEDULER");
				schedulerService.getAllInfoActiveResourceScheduler();
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("------------Schedular Method Exception---------" + e.getMessage());
		}
	}
}
