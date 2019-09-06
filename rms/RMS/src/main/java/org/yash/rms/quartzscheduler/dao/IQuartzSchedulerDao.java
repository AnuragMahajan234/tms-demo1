package org.yash.rms.quartzscheduler.dao;

import java.util.List;

import org.yash.rms.quartzscheduler.utils.QrtzTriggersWrapper;

/**
 * The Interface IQuartzSchedulerDao.
 */
public interface IQuartzSchedulerDao {

	/**
	 * Gets the all qrtz trigger list.
	 *
	 * @param whereString the where string
	 * @return the all qrtz trigger list
	 */
	List<QrtzTriggersWrapper> getAllQrtzTriggerList(String whereString);

}
