package org.yash.rms.quartzscheduler.utils;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.yash.rms.quartzscheduler.service.Generic.QuartzSchedularGenericImpl;
import org.yash.rms.util.AppContext;


/**
 * The Class QuartzSchedulerUtils.
 *
 */
@Service
public class QuartzSchedulerUtils {
	
	/** The logger. */
	private static final Logger logger = LoggerFactory.getLogger(QuartzSchedulerUtils.class);
	
  /**
   * Adds the.
   *
   * @param jobName the job name
   * @param groupName the group name
   * @param triggerName the trigger name
   * @param className the class name
   * @param methodName the method name
   * @param cronExpression the cron expression
   * @param priority the priority
   * @throws Exception the exception
   */
	
	public void add(String schedulerName, String jobName, String groupName, String triggerName, String className,
			String methodName, String cronExpression, Integer priority,String executeClassName) throws Exception {

		logger.info("Inside class " + this.getClass().getCanonicalName() + " method : " + methodName + " triggerName : "
				+ triggerName + " schedulerName :" + schedulerName + " groupName : " + groupName + " jobName :"
				+ jobName + " className :" + className + " methodName :" + methodName + " cronExpression :"
				+ cronExpression + " priority :" + priority);

		try {
			StdScheduler sf = (StdScheduler) AppContext.getApplicationContext().getBean("QuartzScheduler",StdScheduler.class);
			JobDetail job = newJob(QuartzSchedularGenericImpl.class)
					        .usingJobData("className", className)
					        .usingJobData("methodName", methodName)
					        .usingJobData("executeClassName", executeClassName)
					        .withIdentity(jobName, groupName).build();

			Trigger trigger = newTrigger().withIdentity(triggerName, groupName)
					          .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
					          .withPriority(priority).build();
			
			// for get (quartz-schedular.xml) path
			// ApplicationContext context=new  ClassPathXmlApplicationContext("classpath*:/spring-configuration/quartz-schedular.xml");
			
			sf.scheduleJob(job, trigger);
			//sf.shutdown(true);
		} catch (Exception e) {
			logger.error("-----------Schedular Exception inside QuartzSchedulerUtils add method-----------"+ e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
  
 /**
  * Adds the custom report trigger.
  *
  * @param jobName the job name
  * @param groupName the group name
  * @param triggerName the trigger name
  * @param cronExpression the cron expression
  * @param triggerDescription the trigger description
  * @throws Exception the exception
  */
 public void addCustomReportTrigger(String jobName,String groupName,String triggerName,String cronExpression,String triggerDescription) throws Exception {
	  
	  logger.info("Inside @class "+this.getClass().getCanonicalName() + " @method addCustomReportTrigger @param triggerName : "+triggerName + 
			  " groupName : "+groupName + 
			  " cronExpression"+cronExpression+" triggerDescription"+triggerDescription);

    
    SchedulerFactory sf = new StdSchedulerFactory();
    Scheduler sched = sf.getScheduler();
    
    /*JobDetail job = newJob(CreateCustomReport.class)
    		.withIdentity(jobName, groupName).build();*/
    
    
    Trigger trigger = newTrigger().withIdentity(triggerName, groupName).withDescription(triggerDescription)
    		.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .build();
  //  sched.scheduleJob(job,trigger);
    
    
    sched.shutdown(true);
  }

  /**
   * The main method.
   *
   * @param args the arguments
   * @throws Exception the exception
   */
  public static void main(String[] args) throws Exception {

    QuartzSchedulerUtils example = new QuartzSchedulerUtils();
   
    
  }
  
  /**
   * Removes the.
   *
   * @param triggerName the trigger name
   * @param groupName the group name
   * @throws Exception the exception
   */
  public void remove(String triggerName, String groupName) throws Exception{
	  logger.info("Inside @class "+this.getClass().getCanonicalName() + " @method remove @param triggerName : "+triggerName + " groupName : "+groupName );
	 // StdScheduler sdScheduler = (StdScheduler) ApplicationContext.getContext().getBean("QuartzScheduler");
	 
	  ApplicationContext context=new ClassPathXmlApplicationContext("classpath*:spring-configuration/quartz-schedular.xml");
	  StdScheduler sdScheduler = (StdScheduler)context.getBean("QuartzScheduler");
	  
	  TriggerKey triggerKey = new TriggerKey(triggerName, groupName);
	  sdScheduler.unscheduleJob(triggerKey);
  }
  
  /**
   * Paused trigger.
   *
   * @param triggerName the trigger name
   * @param groupName the group name
   * @throws Exception the exception
   */
  public void pausedTrigger(String triggerName, String groupName) throws Exception{
	  logger.info("Inside @class "+this.getClass().getCanonicalName() + " @method pausedTrigger @param triggerName : "+triggerName + " groupName : "+groupName );
	  //StdScheduler sdScheduler = (StdScheduler) ContextProvider.getContext().getBean("QuartzScheduler");
	  
	  ApplicationContext context=new ClassPathXmlApplicationContext("classpath*:spring-configuration/quartz-schedular.xml");
	  StdScheduler sdScheduler = (StdScheduler)context.getBean("QuartzScheduler");
	  
	  TriggerKey triggerKey = new TriggerKey(triggerName, groupName);
	  sdScheduler.pauseTrigger(triggerKey);
  }
  
  /**
   * Resume trigger.
   *
   * @param triggerName the trigger name
   * @param groupName the group name
   * @throws Exception the exception
   */
  public void resumeTrigger(String triggerName, String groupName) throws Exception{
	  logger.info("Inside @class "+this.getClass().getCanonicalName() + " @method resumeTrigger @param triggerName : "+triggerName + " groupName : "+groupName );
	  
	  //StdScheduler sdScheduler = (StdScheduler).ContextProvider. getContext().getBean("QuartzScheduler");
	  
	  ApplicationContext context=new ClassPathXmlApplicationContext("classpath*:spring-configuration/quartz-schedular.xml");
	  StdScheduler sdScheduler = (StdScheduler)context.getBean("QuartzScheduler");
	 
	  TriggerKey triggerKey = new TriggerKey(triggerName, groupName);
	  sdScheduler.resumeTrigger(triggerKey);
  }
 
  /**
   * Daily at hour and minute.
   *
   * @param hour the hour
   * @param minute the minute
   * @return the string
   */
  public String dailyAtHourAndMinute(int hour, int minute) {     
	  logger.info("Inside @class "+this.getClass().getCanonicalName() + " @method dailyAtHourAndMinute");
      return String.format("0 %d %d ? * *", minute, hour);
  }
  
  /**
   * At hour and minute on given days of week.
   *
   * @param hour the hour
   * @param minute the minute
   * @param daysOfWeek the days of week
   * @return the string
   */
  public String atHourAndMinuteOnGivenDaysOfWeek(int hour, int minute, Integer daysOfWeek[]) {

	  logger.info("Inside @class "+this.getClass().getCanonicalName() + " @method atHourAndMinuteOnGivenDaysOfWeek");
	        if (daysOfWeek == null || daysOfWeek.length == 0){
	            throw new IllegalArgumentException(
	                    "You must specify at least one day of week.");
	        }
	        for (int dayOfWeek : daysOfWeek){
	            DateBuilder.validateDayOfWeek(dayOfWeek);
	        }
	        DateBuilder.validateHour(hour);
	        DateBuilder.validateMinute(minute);

	        String cronExpression = String.format("0 %d %d ? * %d", minute, hour,
	                daysOfWeek[0]);

	        for (int i = 1; i < daysOfWeek.length; i++){
	            cronExpression = cronExpression + "," + daysOfWeek[i];
	        }

      return cronExpression;
  }
  
  /**
   * Weekly on day and hour and minute.
   *
   * @param dayOfWeek the day of week
   * @param hour the hour
   * @param minute the minute
   * @return the string
   */
  public String weeklyOnDayAndHourAndMinute(int dayOfWeek, int hour, int minute) {
	  logger.info("Inside @class "+this.getClass().getCanonicalName() + " @method weeklyOnDayAndHourAndMinute");
      DateBuilder.validateDayOfWeek(dayOfWeek);
      DateBuilder.validateHour(hour);
      DateBuilder.validateMinute(minute);

      String cronExpression = String.format("0 %d %d ? * %d", minute, hour,
              dayOfWeek);

      return cronExpression;
  }
  
  /**
   * Monthly on day and hour and minute.
   *
   * @param dayOfMonth the day of month
   * @param hour the hour
   * @param minute the minute
   * @return the string
   */
  public String monthlyOnDayAndHourAndMinute(int dayOfMonth, int hour, int minute) {
	  logger.info("Inside @class "+this.getClass().getCanonicalName() + " @method monthlyOnDayAndHourAndMinute");
      DateBuilder.validateDayOfMonth(dayOfMonth);
      DateBuilder.validateHour(hour);
      DateBuilder.validateMinute(minute);

      String cronExpression = String.format("0 %d %d %d * ?", minute, hour,
              dayOfMonth);
      return cronExpression;
  }
  
  /**
   * Re schedule trigger.
   *
   * @param triggerName the trigger name
   * @param groupName the group name
   * @param cronExpression the cron expression
   * @throws Exception the exception
   */
  public void reScheduleTrigger(String triggerName,String groupName,String cronExpression) throws Exception {
		 logger.info("Inside @class "+this.getClass().getCanonicalName()+ " @method  reScheduleTrigger");
		  logger.info("TriggerName :" +triggerName+" groupName : "+groupName+" cronExpression :"+cronExpression);
		 try {
		  StdScheduler sdScheduler = (StdScheduler) AppContext.getApplicationContext().getBean("QuartzScheduler",StdScheduler.class);
		  Trigger newTrigger = newTrigger().withIdentity(triggerName, groupName).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
		            .build();
		  TriggerKey triggerKey = new TriggerKey(triggerName, groupName);
		  sdScheduler.rescheduleJob(triggerKey, newTrigger);
		 } catch (Exception e) {
				logger.error("-----------Schedular Exception inside QuartzSchedulerUtils reScheduleTrigger method-----------"+ e.getMessage());
				e.printStackTrace();
				throw e;
			}
	  }
  
  /**
   * Re schedule custom report trigger.
   *
   * @param triggerName the trigger name
   * @param groupName the group name
   * @param cronExpression the cron expression
   * @param triggerDescription the trigger description
   * @throws Exception the exception
   */
  public void reScheduleCustomReportTrigger(String triggerName,String groupName,String cronExpression,String triggerDescription) throws Exception {
	  logger.info("Inside @class "+this.getClass().getCanonicalName()+ " @method  reScheduleTrigger");
	 // StdScheduler sdScheduler = (StdScheduler) ContextProvider.getContext().getBean("QuartzScheduler");
	  
	  ApplicationContext context=new ClassPathXmlApplicationContext("classpath*:spring-configuration/quartz-schedular.xml");
	  StdScheduler sdScheduler = (StdScheduler)context.getBean("QuartzScheduler");
	  
	  Trigger newTrigger = newTrigger().withIdentity(triggerName, groupName).withDescription(triggerDescription).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
	            .build();
	  TriggerKey triggerKey = new TriggerKey(triggerName, groupName);
	  sdScheduler.rescheduleJob(triggerKey, newTrigger);
	  
  }
 
}
