package org.yash.rms.quartzscheduler.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yash.rms.quartzscheduler.dao.IQuartzSchedulerDao;
import org.yash.rms.quartzscheduler.utils.QrtzTriggersWrapper;


/**
 * The Class QuartzSchedulerDaoImpl.
 */
@Service
public class QuartzSchedulerDaoImpl  implements IQuartzSchedulerDao {
	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Instantiates a new quartz scheduler dao impl.
	 */
	public QuartzSchedulerDaoImpl() {
	
	}

	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(QuartzSchedulerDaoImpl.class);
	
	
	/**
	 * Gets the all qrtz trigger list.
	 *
	 * @param whereString the where string
	 * @return the all qrtz trigger list
	 * @see com.inn.decent.quartzscheduler.dao.IQuartzSchedulerDao.getAllQrtzTriggerList(java.lang.String)
	 */
	public List<QrtzTriggersWrapper> getAllQrtzTriggerList(String whereString) {
		logger.info("Inside @clas "+this.getClass().getCanonicalName() + " @method getAllQrtzTriggerList");
		try {
		String query = "select t.SCHED_NAME as schedulerName,t.TRIGGER_NAME as triggerName,t.TRIGGER_GROUP as triggerGroup,t.JOB_NAME as jobName,t.JOB_GROUP as jobGroup,t.DESCRIPTION as description,NEXT_FIRE_TIME as nextFireTime,PREV_FIRE_TIME as prevFireTime,PRIORITY as priority,TRIGGER_STATE as triggerStatus,TRIGGER_TYPE as triggerType,START_TIME as startTime,END_TIME as endTime,CALENDAR_NAME as calendarName,MISFIRE_INSTR as misfireInstr,jd.JOB_DATA as jobData,c1.CRON_EXPRESSION as cronExpression from QRTZ_TRIGGERS t inner join QRTZ_CRON_TRIGGERS c1 on (concat(t.TRIGGER_NAME,t.TRIGGER_GROUP)=concat(c1.TRIGGER_NAME,c1.TRIGGER_GROUP)) inner join QRTZ_JOB_DETAILS jd on (concat(t.JOB_NAME,t.JOB_GROUP)=concat(jd.JOB_NAME,jd.JOB_GROUP))";
		if(whereString!=null && !whereString.equalsIgnoreCase("")) {
			query+=whereString;
		}
		//Session session = (Session) getEntityManager().getDelegate();
		Session session = (Session) getEntityManager().getDelegate();
		Query queryString  = session.createSQLQuery(query).addScalar("schedulerName", StandardBasicTypes.STRING)
				.addScalar("triggerName",StandardBasicTypes.STRING)
				.addScalar("triggerGroup",StandardBasicTypes.STRING)
				.addScalar("jobName",StandardBasicTypes.STRING)
				.addScalar("jobGroup",StandardBasicTypes.STRING)
				.addScalar("description",StandardBasicTypes.STRING)
				.addScalar("nextFireTime",StandardBasicTypes.STRING)
				.addScalar("prevFireTime",StandardBasicTypes.STRING)
				.addScalar("priority",StandardBasicTypes.STRING)
				.addScalar("triggerStatus",StandardBasicTypes.STRING)
				.addScalar("triggerType",StandardBasicTypes.STRING)
				.addScalar("startTime",StandardBasicTypes.STRING)
				.addScalar("endTime",StandardBasicTypes.STRING)
				.addScalar("calendarName",StandardBasicTypes.STRING)
				.addScalar("misfireInstr",StandardBasicTypes.STRING)
				.addScalar("jobData", StandardBasicTypes.STRING)
				.addScalar("cronExpression", StandardBasicTypes.STRING).setResultTransformer(new AliasToBeanResultTransformer(QrtzTriggersWrapper.class));
			List<QrtzTriggersWrapper> qrtzTriggersWrappers = queryString.list();
			logger.info("qrtzTriggersWrappers list size is  " +qrtzTriggersWrappers.size());
			return qrtzTriggersWrappers;
		}catch(Exception e) {
			logger.error("Error occured in method getAllQrtzTriggerList ",e);
			logger.error("Error occured inside @class "+this.getClass().getName()+" @error :"+e.getLocalizedMessage());
			return null;
		}
	}
	
}
