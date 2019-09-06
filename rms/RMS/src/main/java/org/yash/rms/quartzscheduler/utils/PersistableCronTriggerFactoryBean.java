package org.yash.rms.quartzscheduler.utils;

import org.springframework.scheduling.quartz.CronTriggerFactoryBean;


/**
 * The Class PersistableCronTriggerFactoryBean.
 */
public class PersistableCronTriggerFactoryBean extends CronTriggerFactoryBean {

	/**
	 * Instantiates a new persistable cron trigger factory bean.
	 */
	public PersistableCronTriggerFactoryBean() {
	}
	
	/**
	 * After properties set.
	 *
	 * @see org.springframework.scheduling.quartz.CronTriggerFactoryBean.afterPropertiesSet()
	 */
	//@Override
	//public void afterPropertiesSet() {
		//super.afterPropertiesSet();
		//getJobDataMap().remove(JobDetailAwareTrigger.JOB_DETAIL_KEY);
	//}
	@Override
	public void afterPropertiesSet() {
		try {
			super.afterPropertiesSet();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}
