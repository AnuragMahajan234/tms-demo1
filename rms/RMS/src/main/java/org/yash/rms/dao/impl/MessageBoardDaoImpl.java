package org.yash.rms.dao.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.MessageBoardDao;
import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.domain.MessageBoard;
import org.yash.rms.domain.Resource;
import org.yash.rms.exception.DAOException;
import org.yash.rms.exception.DaoRestException;
import org.yash.rms.rest.dao.generic.impl.HibernateGenericDao;
import org.yash.rms.util.Constants;
import org.yash.rms.util.GenericCriteria;
import org.yash.rms.util.HibernateUtil;
import org.yash.rms.util.SearchCriteriaGeneric;
import org.yash.rms.util.messageBoardStatusConstants;

@Repository
public class MessageBoardDaoImpl extends HibernateGenericDao<Integer, MessageBoard> implements MessageBoardDao {

	public MessageBoardDaoImpl() {

		super(MessageBoard.class);

	}

	private static final Logger logger = LoggerFactory.getLogger(MessageBoardDaoImpl.class);

	public List<MessageBoard> getAllMessageList(String messageStatus, SearchCriteriaGeneric searchCriteriaGeneric) {
		logger.info("-----------getAllMessageList method Start-----------------------");
		List<MessageBoard> messageList = new ArrayList<MessageBoard>();
		Session session = (Session) getEntityManager().getDelegate();
		try {
			String sortColumnName = searchCriteriaGeneric.getISortColumn();
			if (sortColumnName.equalsIgnoreCase(MessageBoard.MESSAGEBOARD)) {
				sortColumnName = MessageBoard.MODIFIEDTIME;
				searchCriteriaGeneric.setiSortDir(Constants.DESC);
			}
			searchCriteriaGeneric.setISortColumn(sortColumnName);

			Criteria criteria = session.createCriteria(MessageBoard.class);

			// Filter for messageStatus - Approved,New,rejected
			if (messageStatus.equalsIgnoreCase(Constants.ALL)) {
				Criterion approved = Restrictions.ilike(Constants.MESSAGESTATUS,
						messageBoardStatusConstants.APPROVED.toString());
				Criterion rejected = Restrictions.ilike(Constants.MESSAGESTATUS,
						messageBoardStatusConstants.REJECTED.toString());
				Criterion newStatus = Restrictions.ilike(Constants.MESSAGESTATUS,
						messageBoardStatusConstants.NEW.toString());
				criteria.add(Restrictions.or(approved, rejected, newStatus));

			} else {
				session.enableFilter(MessageBoard.WHICH_STATUS).setParameter(Constants.MESSAGESTATUS, messageStatus);
			}
			session.enableFilter(MessageBoard.IS_DELETED).setParameter(Constants.IS_DELETED, false);
			criteria = GenericCriteria.createCriteria(searchCriteriaGeneric, criteria);

			messageList = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured in getAllMessageList", e);
		}
		logger.info("-----------getAllMessageList method End-----------------------");
		return messageList;
	}

	@Transactional(readOnly = true)
	public Long getTotalCountForMessagesList(String messageStatus, SearchCriteriaGeneric searchCriteriaGeneric) {
		logger.info("-----------getTotalCountForMessagesList method Starts-----------------------");
		Long totalCount = 0L;
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(MessageBoard.class);

		// Filter for messageStatus - Approved,New,rejected
		if (messageStatus.equalsIgnoreCase(Constants.ALL)) {
			Criterion approved = Restrictions.ilike(Constants.MESSAGESTATUS,
					messageBoardStatusConstants.APPROVED.toString());
			Criterion rejected = Restrictions.ilike(Constants.MESSAGESTATUS,
					messageBoardStatusConstants.REJECTED.toString());
			Criterion newStatus = Restrictions.ilike(Constants.MESSAGESTATUS,
					messageBoardStatusConstants.NEW.toString());
			criteria.add(Restrictions.or(approved, rejected, newStatus));

		} else {
			session.enableFilter(MessageBoard.WHICH_STATUS).setParameter(Constants.MESSAGESTATUS, messageStatus);
		}
		session.enableFilter(MessageBoard.IS_DELETED).setParameter(Constants.IS_DELETED, false);
		try {
			searchCriteriaGeneric.setPage(null);
			searchCriteriaGeneric.setSize(null);
			searchCriteriaGeneric.setISortColumn(null);
			searchCriteriaGeneric.setiSortDir(null);
			criteria = GenericCriteria.createCriteria(searchCriteriaGeneric, criteria);
		} catch (ParseException e1) {
			e1.printStackTrace();
			logger.error("Exception Occurred while counting messages " + e1.getMessage());
			return totalCount;
		}
		criteria.setProjection(Projections.rowCount());
		totalCount = (Long) criteria.uniqueResult();
		logger.info("-----------getTotalCountForMessagesList method End-----------------------");
		return totalCount;
	}

	public void updateMessageObject(MessageBoard messageBoardObj) throws DAOException {

		logger.info("-----------updateMessageObject method Start-----------------------");
		Session session = (Session) getEntityManager().getDelegate();
		try {
			session.saveOrUpdate(messageBoardObj);
		} catch (Exception e) {
			logger.error("----------Exception in updateMessageObject----------------", e);
			throw new DAOException("512", e.getMessage());
		}
		logger.info("-----------updateMessageObject method End-----------------------");

	}

	public MessageBoard findById(Integer id) throws DAOException {

		logger.info("-----------findById method Start-----------------------");

		try {

			Criteria criteria = ((Session) getEntityManager().getDelegate()).createCriteria(MessageBoard.class);
			logger.info("--------MessageBoardDaoImpl findById method end-------");
			return (MessageBoard) criteria.add(Restrictions.eq("id", id)).uniqueResult();
		} catch (HibernateException ex) {
			logger.error("----------Exception MessageBoardDaoImpl findById----------------", ex);
			throw new DAOException("512", ex.getMessage());
		}

	}

	@Override
	public MessageBoard create(MessageBoard messageBoard) throws DAOException {
		try {
			return super.create(messageBoard);
		} catch (DaoRestException e) {
			// TODO Auto-generated catch block
			throw new DAOException("503", e.getMessage());
		}
	}

	@Override
	public MessageBoard update(MessageBoard messageBoard) throws DAOException {
		try {
			return super.update(messageBoard);
		} catch (DaoRestException e) {
			// TODO Auto-generated catch block
			throw new DAOException("503", e.getMessage());
		}
	}

	public List<MessageBoard> getApprovedMessages() {
		logger.info("-----------getApprovedMessages method Start-----------------------");

		List<MessageBoard> approvedMessageList = new ArrayList<MessageBoard>();

		/*
		 * Query query=getEntityManager().
		 * createQuery("from MessageBoard as mb where mb.isDeleted=false and "
		 * +messageBoardStatusConstants.APPROVED.toString());
		 * approvedMessageList=query.getResultList();
		 */
		Session session = ((Session) getEntityManager().unwrap(Session.class));
		Criteria criteria = session.createCriteria(MessageBoard.class);

		session.enableFilter(MessageBoard.IS_DELETED).setParameter(Constants.IS_DELETED, false);
		criteria.add(Restrictions.eq(Constants.MESSAGESTATUS, messageBoardStatusConstants.APPROVED.toString()));
		criteria.addOrder(Order.desc(MessageBoard.MODIFIEDTIME));
		approvedMessageList = criteria.list();

		logger.info("-----------getApprovedMessages method End-----------------------");
		return approvedMessageList;
	}

	public Resource findByEmployeeId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
