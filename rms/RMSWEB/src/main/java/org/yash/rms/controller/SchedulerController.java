package org.yash.rms.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yash.rms.service.SchedulerService;

@Controller
@RequestMapping("/scheduler")
public class SchedulerController {
	
	private static final Logger logger = LoggerFactory.getLogger(SchedulerController.class);
	
	@Autowired
	SchedulerService schedulerService;
	
	@RequestMapping(value = "/infoScheduler",method = RequestMethod.GET)	
	public ResponseEntity<String> runInfoScheduler() {

		logger.info("--------runInfoScheduler method starts--------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		try{
		
			schedulerService.getAllInfoActiveResourceScheduler();
		}
		catch(Exception e ){
			e.printStackTrace();
		}
		

        logger.info("--------runInfoScheduler method ends--------");
        return new ResponseEntity<String>("Info_Active Scheduler completed", headers, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/infouptrecstatus",method = RequestMethod.GET)	
	public ResponseEntity<String> runUpdateRecStatusScheduler() {

		logger.info("--------runUpdateRecStatusScheduler method starts--------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		try{
		
			schedulerService.checkingAllResStatusScheduler();
		}
		catch(Exception e ){
			e.printStackTrace();
		}
		

        logger.info("--------runUpdateRecStatusScheduler method ends--------");
        return new ResponseEntity<String>("Update_Record_Status Scheduler completed", headers, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/infoInactiveScheduler",method = RequestMethod.GET)	
	public ResponseEntity<String> runInfoInactiveScheduler() {

		logger.info("--------run infoInactiveScheduler method starts--------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		try{
		
			schedulerService.getAllInfoInactiveResourceScheduler();
		}
		catch(Exception e ){
			e.printStackTrace();
		}
		

        logger.info("--------run infoInactiveScheduler method ends--------");
        return new ResponseEntity<String>("infoInactive Scheduler completed", headers, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/projectgoingtoclosescheduler", method = RequestMethod.GET)
	public ResponseEntity<String> runProjectGoingtoCloseScheduler() {
		logger.info("--------run runProjectGoingtoCloseScheduler method starts--------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String message="";
		try{
			schedulerService.runProjectGoingtoCloseScheduler();
			message="projectgoingtoclose Scheduler completed successfully";
		}
		catch(Exception e){
			logger.error("--------some error occured in runProjectGoingtoCloseScheduler method --------");
			e.printStackTrace();
			message="something went wrong with projectgoingtoclose Scheduler";
			
		}
		logger.info("--------run runPojectGoingtoCloseScheduler method end--------");
		 return new ResponseEntity<String>(message, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/blockedResourceReportEmail", method = RequestMethod.GET)
	@ResponseBody
	public String thirtyDaysblockedResourceReportEmail(@RequestParam(value="days", required=false, defaultValue = "0") Integer days,
			@RequestParam(value="tillDate", required=false) @DateTimeFormat(pattern="dd-MMM-yyyy") Date tillDate){
		logger.info("------SchedulerController @ThirtyDaysblockedResourceReportEmail method Start------");
		String message = schedulerService.thirtyDaysblockedResourceReportEmailFromController(days, tillDate);
		try{
			message = "Success | Email Sent | Resource size ="+message;
			logger.info("------SchedulerController @ThirtyDaysblockedResourceReportEmail method End------");
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception in Schedular Contorller while sending mail for blocked resource report");
			message="failure";
		}
		return message;
	}
}
