package org.yash.rms.quartzscheduler.service.impl;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.quartzscheduler.dao.IQuartzSchedulerDao;
import org.yash.rms.quartzscheduler.service.IQuartzSchedulerService;
import org.yash.rms.quartzscheduler.utils.QrtzTriggersWrapper;
import org.yash.rms.quartzscheduler.utils.QuartzSchedulerUtils;
import org.yash.rms.util.Constants;


/**
 * The Class QuartzSchedulerServiceImpl.
 */
@Service
@Transactional
public class QuartzSchedulerServiceImpl implements IQuartzSchedulerService {
	
	/** The logger. */
	private static Logger logger=LoggerFactory.getLogger(QuartzSchedulerServiceImpl.class);
	
	/** The quartz scheduler dao. */
	@Autowired
	private IQuartzSchedulerDao quartzSchedulerDao;
	
	/**
	 * Gets the all qrtz trigger list.
	 *
	 * @return the all qrtz trigger list
	 * @see com.inn.decent.quartzscheduler.service.IQuartzSchedulerService.getAllQrtzTriggerList()
	 */
	public List<QrtzTriggersWrapper> getAllQrtzTriggerList () {
		return quartzSchedulerDao.getAllQrtzTriggerList(null); // pass null because want to all data
	}
	
	/**
	 * Creates the trigger.
	 *
	 * @param jobName the job name
	 * @param groupName the group name
	 * @param triggerName the trigger name
	 * @param className the class name
	 * @param methodName the method name
	 * @param cronExpression the cron expression
	 * @param priority the priority
	 * @return the string
	 * @see com.inn.decent.quartzscheduler.service.IQuartzSchedulerService.createTrigger(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public String createTrigger(String schedulerName, String jobName,String groupName,String triggerName,String className,String methodName,String cronExpression,Integer priority,String executeClassName) {
    	 QuartzSchedulerUtils quartzSchedulerUtils = new QuartzSchedulerUtils();
    	 try {
    		 quartzSchedulerUtils.add(schedulerName,jobName, groupName, triggerName, className, methodName, cronExpression,priority,executeClassName);
    		 return "{ \"status\" : \""+Constants.SUCCESS+"\"}";
		} catch (Exception e) {
			logger.error("Error occured inside @class "+this.getClass().getCanonicalName() + " @method createTrigger" ,e);
			logger.error("Error occured inside @class "+this.getClass().getName()+" @error :"+e.getLocalizedMessage());
			return "{ \"status\" : \""+Constants.FAILURE+"\"}";
		}
     }
	
	/**
	 * Removes the trigger.
	 *
	 * @param triggerName the trigger name
	 * @param groupName the group name
	 * @return the string
	 * @see com.inn.decent.quartzscheduler.service.IQuartzSchedulerService.removeTrigger(java.lang.String, java.lang.String)
	 */
	public String removeTrigger(String triggerName,String groupName) {
		QuartzSchedulerUtils quartzSchedulerUtils = new QuartzSchedulerUtils();
		try {
			quartzSchedulerUtils.remove(triggerName, groupName);
			return "{ \"status\" : \""+Constants.SUCCESS+"\"}";
		} catch (Exception e) {
			logger.error("Error occured inside @class "+this.getClass().getCanonicalName() + " @method removeTrigger" ,e);
			logger.error("Error occured inside @class "+this.getClass().getName()+" @error :"+e.getLocalizedMessage());
			return "{ \"status\" : \""+Constants.FAILURE+"\"}";
		}
	}
	
	/**
	 * Paused trigger.
	 *
	 * @param triggerName the trigger name
	 * @param groupName the group name
	 * @return the string
	 * @see com.inn.decent.quartzscheduler.service.IQuartzSchedulerService.pausedTrigger(java.lang.String, java.lang.String)
	 */
	public String pausedTrigger(String triggerName,String groupName) {
		QuartzSchedulerUtils quartzSchedulerUtils = new QuartzSchedulerUtils();
		try {
			quartzSchedulerUtils.pausedTrigger(triggerName, groupName);
			return "{ \"status\" : \""+Constants.SUCCESS+"\"}";
		} catch (Exception e) {
			logger.error("Error occured inside @class "+this.getClass().getCanonicalName() + " @method pausedTrigger" ,e);
			logger.error("Error occured inside @class "+this.getClass().getName()+" @error :"+e.getLocalizedMessage());
			return "{ \"status\" : \""+Constants.FAILURE+"\"}";
		}
	}
	
	/**
	 * Resume trigger.
	 *
	 * @param triggerName the trigger name
	 * @param groupName the group name
	 * @return the string
	 * @see com.inn.decent.quartzscheduler.service.IQuartzSchedulerService.resumeTrigger(java.lang.String, java.lang.String)
	 */
	public String resumeTrigger(String triggerName,String groupName) {
		QuartzSchedulerUtils quartzSchedulerUtils = new QuartzSchedulerUtils();
		try {
			quartzSchedulerUtils.resumeTrigger(triggerName, groupName);
			return "{ \"status\" : \""+Constants.SUCCESS+"\"}";
		} catch (Exception e) {
			logger.error("Error occured inside @class "+this.getClass().getCanonicalName() + " @method resumeTrigger" ,e);
			logger.error("Error occured inside @class "+this.getClass().getName()+" @error :"+e.getLocalizedMessage());
			return "{ \"status\" : \""+Constants.FAILURE+"\"}";
		}
	}
	
	/**
	 * Re schedule trigger.
	 *
	 * @param triggerName the trigger name
	 * @param groupName the group name
	 * @param cronExpression the cron expression
	 * @return the string
	 * @see com.inn.decent.quartzscheduler.service.IQuartzSchedulerService.reScheduleTrigger(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String reScheduleTrigger(String triggerName,String groupName,String cronExpression) {
		QuartzSchedulerUtils quartzSchedulerUtils = new QuartzSchedulerUtils();
		try {
			quartzSchedulerUtils.reScheduleTrigger(triggerName, groupName, cronExpression);
			return "{ \"status\" : \""+Constants.SUCCESS+"\"}";
		} catch (Exception e) {
			logger.error("Error occured inside @class "+this.getClass().getCanonicalName() + " @method reScheduleTrigger" ,e);
			logger.error("Error occured inside @class "+this.getClass().getName()+" @error :"+e.getLocalizedMessage());
			return "{ \"status\" : \""+Constants.FAILURE+"\"}";
		}
	}

	/**
	 * Gets the filtered data.
	 *
	 * @param processName the process name
	 * @param moduleName the module name
	 * @return the filtered data
	 * @see com.inn.decent.quartzscheduler.service.IQuartzSchedulerService.getFilteredData(java.lang.String, java.lang.String)
	 */
	public List<QrtzTriggersWrapper> getFilteredData(String processName, String moduleName) {
		String whereString  = " where ";
		if(processName!=null && !processName.equalsIgnoreCase("")) {
			whereString+= "t.TRIGGER_NAME like '%" +processName +"%'";
		}
		if(moduleName!=null && !moduleName.equalsIgnoreCase("")) {
			whereString+= " t.TRIGGER_GROUP like '%" +moduleName+"%'";
		}
		if(whereString.equalsIgnoreCase(" where ")) {
			 whereString = null;
		}
		return quartzSchedulerDao.getAllQrtzTriggerList(whereString);
	}

	/**
	 * Gets the method names by class name.
	 *
	 * @param className the class name
	 * @return the method names by class name
	 * @throws ClassNotFoundException the class not found exception
	 * @see com.inn.decent.quartzscheduler.service.IQuartzSchedulerService.getMethodNamesByClassName(java.lang.String)
	 */
	public List<String> getMethodNamesByClassName(String className) throws ClassNotFoundException {
		List<String> methodNames = new ArrayList<String>();
		Class<?> clazz = Class.forName(className);
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if(method.getParameterTypes().length==0) {
				methodNames.add(method.getName());
			}
		}
		return methodNames;
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		Class<?> clazz = Class.forName("org.yash.rms.quartzscheduler.service.impl.SchedularServiceImpl");
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			
			
		}
	}

	/**
	 * Gets the all classes from package.
	 *
	 * @return the all classes from package
	 * @see com.inn.decent.quartzscheduler.service.IQuartzSchedulerService.getAllClassesFromPackage()
	 */
	public List<String> getAllClassesFromPackage() {
		List<String> classNames = new ArrayList<String>();
		String pkg= "org.yash.rms.quartzscheduler.service.impl";
		String relPath = pkg.replace('.', '/');
	    URL resource = Thread.currentThread().getContextClassLoader().getResource(relPath);
	    if (resource == null) {
	        throw new RuntimeException("Unexpected problem: No resource for "
	                + relPath);
	        
	    }
	    File f = new File(resource.getPath());

	    String[] files = f.list();
	    for (int i = 0; i < files.length; i++) {

	        String fileName = files[i];
	        String className = null;
	        String fileNm = null;

	        if (fileName.endsWith(".class")) {

	            fileNm = fileName.substring(0, fileName.length() - 6);
	            className = pkg + '.' + fileNm;
	        }

	        if (className != null) {
	        	classNames.add(className);
	        }
	    }
		return classNames;
	}

}
