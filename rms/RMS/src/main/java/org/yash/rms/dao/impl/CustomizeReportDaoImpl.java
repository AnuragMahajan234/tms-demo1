package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.yash.rms.dao.CustomizeReportDao;
import org.yash.rms.namedquery.RmsNamedQuery;
import org.yash.rms.report.dto.BUResourcesPLReport;
import org.yash.rms.report.dto.PLReport;

@Repository("customizeReportDao")
public class CustomizeReportDaoImpl implements CustomizeReportDao {

	private static final Logger logger = LoggerFactory
			.getLogger(CustomizeReportDaoImpl.class);

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	



	@SuppressWarnings("unchecked")
	public List<BUResourcesPLReport> getResourceOnBenchReport(Date startDate,
			Date endDate) {
		logger.info("--------CustomizeReportImpl getPLReport method start-------");

		
		List<BUResourcesPLReport> bUResourcesPLReportList = new ArrayList<BUResourcesPLReport>();
		

		PLReport report = new PLReport();
		Query buResourcesQuery;
		Session session = (Session) getEntityManager().getDelegate();
		
		try {
			buResourcesQuery = session
					.createSQLQuery(RmsNamedQuery.getResourcesForCustomizeReportQuery())
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate);

			bUResourcesPLReportList = buResourcesQuery.list();
			
			} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in getPLReport method at DAO layer:-"
					+ e);
			throw e;
		}

		logger.info("--------CustomizeReportImpl getPLReport method end-------");

		System.out.println("--Inside DAO--" + report);
		return bUResourcesPLReportList;

	}

	public List<BUResourcesPLReport> getBillableResources(Date startDate,
			Date endDate) {
		logger.info("--------CustomizeReportImpl getPLReport method start-------");

		
		List<BUResourcesPLReport> bUResourcesPLReportList = new ArrayList<BUResourcesPLReport>();
		

		PLReport report = new PLReport();
		Query buResourcesQuery;
		Session session = (Session) getEntityManager().getDelegate();
		
		try {
			buResourcesQuery = session
					.createSQLQuery(RmsNamedQuery.getBillableResourcesForCustomizeReportQuery())
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate);

			bUResourcesPLReportList = buResourcesQuery.list();
			
			} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in getPLReport method at DAO layer:-"
					+ e);
			throw e;
		}

		logger.info("--------CustomizeReportImpl getPLReport method end-------");

		System.out.println("--Inside DAO--" + report);
		return bUResourcesPLReportList;

	}
	
	public List<BUResourcesPLReport> getNewJoinee(Date startDate,
			Date endDate,String allocationType) {
		logger.info("--------CustomizeReportImpl getPLReport method start-------");

		
		List<BUResourcesPLReport> bUResourcesPLReportList = new ArrayList<BUResourcesPLReport>();
		

		PLReport report = new PLReport();
		Query buResourcesQuery;
		Session session = (Session) getEntityManager().getDelegate();
		
		try {
			buResourcesQuery = session
					.createSQLQuery(RmsNamedQuery.getNewJoineeResourcesForCustomizeReportQuery(allocationType))
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate);

			bUResourcesPLReportList = buResourcesQuery.list();
			
			} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in getPLReport method at DAO layer:-"
					+ e);
			throw e;
		}

		logger.info("--------CustomizeReportImpl getPLReport method end-------");

		System.out.println("--Inside DAO--" + report);
		return bUResourcesPLReportList;

	}
	
	public List<BUResourcesPLReport> getResourcesBenchToProject(Date startDate,
			Date endDate) {
		logger.info("--------CustomizeReportImpl getPLReport method start-------");

		
		List<BUResourcesPLReport> bUResourcesPLReportList = new ArrayList<BUResourcesPLReport>();
		

		PLReport report = new PLReport();
		Query buResourcesQuery;
		Session session = (Session) getEntityManager().getDelegate();
		
		try {
			buResourcesQuery = session
					.createSQLQuery(RmsNamedQuery.getResourcesFromBenchToProject())
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate);

			bUResourcesPLReportList = buResourcesQuery.list();
			
			} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in getPLReport method at DAO layer:-"
					+ e);
			throw e;
		}

		logger.info("--------CustomizeReportImpl getPLReport method end-------");

		System.out.println("--Inside DAO--" + report);
		return bUResourcesPLReportList;

	}

	
	public List<BUResourcesPLReport> getReleasedResourcesOfBG4BU5(Date startDate,
			Date endDate) {
		logger.info("--------CustomizeReportImpl getPLReport method start-------");

		
		List<BUResourcesPLReport> bUResourcesPLReportList = new ArrayList<BUResourcesPLReport>();
		

		PLReport report = new PLReport();
		Query buResourcesQuery;
		Session session = (Session) getEntityManager().getDelegate();
		
		try {
			buResourcesQuery = session
					.createSQLQuery(RmsNamedQuery.getReleasedResourcesOfBG4BU5())
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate);

			bUResourcesPLReportList = buResourcesQuery.list();
			
			} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in getPLReport method at DAO layer:-"
					+ e);
			throw e;
		}

		logger.info("--------CustomizeReportImpl getPLReport method end-------");

		System.out.println("--Inside DAO--" + report);
		return bUResourcesPLReportList;

	}

	public List<BUResourcesPLReport> getResourceTransferToBG4BU5(Date startDate, Date endDate) {
		logger.info("--------CustomizeReportImpl getResourceTransferToBG4BU5 method start-------");
		List<BUResourcesPLReport> bUResourcesPLReportList = new ArrayList<BUResourcesPLReport>();
		PLReport report = new PLReport();
		Query buResourcesQuery;
		Session session = (Session) getEntityManager().getDelegate();
		try {
			buResourcesQuery = session
					.createSQLQuery(RmsNamedQuery.getResourceTransferToBG4BU5())
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate);
			bUResourcesPLReportList = buResourcesQuery.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in getResourceTransferToBG4BU5 method at DAO layer:-"
					+ e);
			throw e;
		}
		logger.info("--------CustomizeReportImpl getResourceTransferToBG4BU5 method end-------");
		if(!bUResourcesPLReportList.isEmpty())
			System.out.println();
		return bUResourcesPLReportList;

	}

	public List<BUResourcesPLReport> getResourceTransferFromBG4BU5(Date startDate, Date endDate) {
		logger.info("--------CustomizeReportImpl getResourceTransferFromBG4BU5 method start-------");

		List<BUResourcesPLReport> bUResourcesPLReportList = new ArrayList<BUResourcesPLReport>();
		PLReport report = new PLReport();
		Query buResourcesQuery;
		Session session = (Session) getEntityManager().getDelegate();
		try {
			buResourcesQuery = session
					.createSQLQuery(RmsNamedQuery.getResourceTransferFromBG4BU5())
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate);

			bUResourcesPLReportList = buResourcesQuery.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in getResourceTransferFromBG4BU5 method at DAO layer:-"
					+ e);
			throw e;
		}
		logger.info("--------CustomizeReportImpl getResourceTransferFromBG4BU5 method end-------");
		if(!bUResourcesPLReportList.isEmpty())
			System.out.println();
		return bUResourcesPLReportList;

	}

		
}
