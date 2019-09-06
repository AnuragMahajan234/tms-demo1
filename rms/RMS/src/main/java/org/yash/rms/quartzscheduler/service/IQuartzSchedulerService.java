package org.yash.rms.quartzscheduler.service;

import java.util.List;

import org.yash.rms.quartzscheduler.utils.QrtzTriggersWrapper;


/**
 * The Interface IQuartzSchedulerService.
 */
public interface IQuartzSchedulerService {

	/**
	 * Gets the all qrtz trigger list.
	 *
	 * @return the all qrtz trigger list
	 */
	List<QrtzTriggersWrapper> getAllQrtzTriggerList();

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
	 */
	String createTrigger(String schedulerName, String jobName, String groupName, String triggerName, String className, String methodName,
			String cronExpression,Integer priority,String executeClassName);

	/**
	 * Removes the trigger.
	 *
	 * @param triggerName the trigger name
	 * @param groupName the group name
	 * @return the string
	 */
	String removeTrigger(String triggerName, String groupName);

	/**
	 * Paused trigger.
	 *
	 * @param triggerName the trigger name
	 * @param groupName the group name
	 * @return the string
	 */
	String pausedTrigger(String triggerName, String groupName);

	/**
	 * Resume trigger.
	 *
	 * @param triggerName the trigger name
	 * @param groupName the group name
	 * @return the string
	 */
	String resumeTrigger(String triggerName, String groupName);

	/**
	 * Re schedule trigger.
	 *
	 * @param triggerName the trigger name
	 * @param groupName the group name
	 * @param cronExpression the cron expression
	 * @return the string
	 */
	String reScheduleTrigger(String triggerName, String groupName, String cronExpression);

	/**
	 * Gets the filtered data.
	 *
	 * @param processName the process name
	 * @param moduleName the module name
	 * @return the filtered data
	 */
	List<QrtzTriggersWrapper> getFilteredData(String processName, String moduleName);

	/**
	 * Gets the method names by class name.
	 *
	 * @param className the class name
	 * @return the method names by class name
	 * @throws ClassNotFoundException the class not found exception
	 */
	List<String> getMethodNamesByClassName(String className) throws ClassNotFoundException;

	/**
	 * Gets the all classes from package.
	 *
	 * @return the all classes from package
	 */
	List<String> getAllClassesFromPackage();

}
