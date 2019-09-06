package org.yash.rms.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.CustomerDAO;
import org.yash.rms.domain.Customer;
import org.yash.rms.domain.CustomerGroup;
import org.yash.rms.util.UserUtil;

/**
 * @author sumit.paul
 *
 */

@Repository("CustomerDao")
@Transactional
public class CustomerDaoImpl implements CustomerDAO {	

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);
	
	
	@SuppressWarnings("unchecked")
	public List<Customer> findAll() {
		logger.info("--------CustomerDaoImpl findAll method start-------");
		List<Customer> customerList = new ArrayList<Customer>();
		/*Criteria criteria = HibernateUtil.getSession(sessionFactory)
				.createCriteria(Customer.class);*/
		Session session = (Session) getEntityManager().getDelegate();
		try {
			customerList = session.createQuery("FROM Customer").list();
			
			//customerList = session.createQuery("FROM Customer").setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			/*customerList = session.createCriteria(Customer.class).setProjection( Projections.projectionList()
		        .add( Projections.distinct(Projections.property("id")))).list();*/
			
			//customerList = session.createCriteria(Customer.class).setProjection(Customer.property("employeeId.employeeId")).setProjection(Projections.distinct(Projections.property("employeeId.employeeId")));
			
		} catch (HibernateException hibernateException) {
	         logger.error("Exception occured in findAll method at DAO layer:-"+hibernateException);
	         throw hibernateException;
	     }
		
		logger.info("------------CustomerDaoImpl findAll method end------------");
		return customerList ;
	}

	
	public Customer findCustomer(int id){
		
		logger.info("-----------CustomerDaoImpl findCustomer method start---------");
		Session session = (Session) getEntityManager().getDelegate();
		Customer customer = null;
		Criteria criteria= session.createCriteria(Customer.class);
		try{
		 //customer = (Customer)session.load(Customer.class, id);
		 customer= (Customer) criteria.add(Restrictions.eq("id", id)).uniqueResult();
		 
		}catch(HibernateException hibernateException){
			logger.error("Exception occured in findCustomer method at DAO layer:-"+hibernateException);
			throw hibernateException;
		}		
		
		logger.info("--------CustomerDaoImpl findAll method end-------");
		System.out.println(customer.getId());
		//customer.setAccountManager(accountManager);
		return customer;		
	}
	
	public boolean delete(int id) {		
		return false;
	}

	public boolean saveOrupdate(Customer customer){
		
		logger.info("------CustomerDaoImpl saveOrUpdate method start------");
		
		if(null == customer) 
			return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			if (customer.getId() == -1) {
				 customer.setCreationTimestamp(new Date());
				 customer.setCreatedId(UserUtil.getCurrentResource().getUsername());
			}	
			else{
			customer.setLastUpdatedId(UserUtil.getCurrentResource().getUsername());
			}
			
			//isSuccess = currentSession.merge(customer)!=null?true:false;
			if (customer.getId() == -1) {
				Serializable id = currentSession.save(customer);
				if (null != id) {
					customer.setId((Integer) id);
				}
			} else {
				Object mergedCustomer = currentSession.merge(customer);
				if (null == mergedCustomer) {
					return false;
				}
			}
			
			if (customer.getCustGroups() != null) {
				for (CustomerGroup custGroup : customer.getCustGroups()) {
					custGroup.setCustomerId(customer);
					if (custGroup.getGroupId()!=0) {
						custGroup.setLastUpdatedId(UserUtil.getCurrentResource().getUsername());
						custGroup.setLastupdatedTimestamp(new Date());					
						isSuccess = null != currentSession.merge(custGroup) ? true : false;
					} else {
						if(custGroup.getCustomerGroupName()!=null)
						{
							custGroup.setCreatedId(UserUtil.getCurrentResource().getUsername());
							custGroup.setCreationTimestamp(new Date());
							custGroup.setActive("Y");
							isSuccess = null != currentSession.save(custGroup) ? true : false;
						}
					}
				}
			}
			
		} catch (HibernateException hibernateException) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+hibernateException);
			throw hibernateException;
			
		} 
		
		logger.info("------CustomerDaoImpl saveOrUpate method end-----");
		return isSuccess;
	}

	public List<Customer> findByEntries(int firstResult, int sizeNo) {		
		return null;
	}

	public long countTotal() {
		return 0;
	}
	

	//Customer Count here
		public Long getcustomerCount()	{
		logger.info("--------customerDaoImpl getcustomerCount method start-------");
				Long count = 0L;
				try {
					Criteria criteria = ((Session) getEntityManager().getDelegate()).createCriteria(Customer.class);
					
					criteria.setProjection(Projections.rowCount());
					count = (Long)criteria.uniqueResult();
				} catch (HibernateException exception) {
					exception.printStackTrace();
					logger.error("Exception occured in getcustomerCount method at DAO layer:-" + exception);
					throw exception;

				}
				return count;
	}
}
