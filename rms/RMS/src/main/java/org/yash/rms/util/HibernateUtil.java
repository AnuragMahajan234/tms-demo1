/**
 * 
 */
package org.yash.rms.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author arpan.badjatiya
 * 
 */
public class HibernateUtil {

/*	private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
	
	public static Session getSession(SessionFactory sessionFactory) {
		if (null == sessionFactory) {
			return null;
		}

		Session session = null;
		try {
			session= sessionFactory.getCurrentSession();
		} catch (HibernateException exception) {
			logger.error("ERROR OCCURED IN GETTING CURRENT SESISON");
			session = sessionFactory.openSession();
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("ERROR OCCURED IN GETTING CURRENT SESISON");
		}
		return session;
	}*/
}
