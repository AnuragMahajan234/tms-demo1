package org.yash.rms.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.yash.rms.dao.CustomerGroupDao;
import org.yash.rms.domain.CustomerGroup;
import org.yash.rms.util.HibernateUtil;
import org.yash.rms.util.UserUtil;

@Repository("CustomerGroupDao")
//@Transactional
public class CustomerGroupDaoImpl implements CustomerGroupDao {


	private static final Logger logger = LoggerFactory.getLogger(CustomerGroupDaoImpl.class);
	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	public boolean saveOrupdate(CustomerGroup customerGroup) {
		logger.info("--------CustomerGroupDaoImpl saveOrUpdate method start-------");
		if(null == customerGroup) return false;
		if (customerGroup.getGroupId() == 0) // yaha 0 tha
        {
 
            customerGroup.setCreatedId(UserUtil.getUserContextDetails().getUserName());
            customerGroup.setCreationTimestamp(new Date());
            customerGroup.setActive("Y");
 
        }
		
		customerGroup.setLastUpdatedId( UserUtil.getUserContextDetails().getUserName());
	
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			isSuccess = currentSession.merge(customerGroup)!=null?true:false;
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while saving CustomerGroup "+ customerGroup.getCustomerGroupName()+e.getMessage());
			throw e;
		}  finally {
//			currentSession.close();
		}
		logger.info("--------CustomerGroupDaoImpl saveOrUpdate method end-------");

		return isSuccess;
	}

	public boolean create(CustomerGroup invoice) {
		if (null == invoice)
			return false;
		logger.info("--------CustomerDaoImpl create method start-------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			 if (invoice.getCreationTimestamp() == null) {
				 invoice.setCreationTimestamp(new Date());
				   }
			currentSession.saveOrUpdate(invoice);
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while saving CustomerGroup "+ invoice.getCustomerGroupName()+e.getMessage());
			throw e;
		}  finally {
//			currentSession.close();
		}
		logger.info("--------CustomerDaoImpl create method end-------");
		return isSuccess;
	}
	

	@SuppressWarnings("unchecked")
	public List<CustomerGroup> findAll() {
		logger.info("--------CustomerGroupDaoImpl findAll method start-------");
		List<CustomerGroup> customerGroupList = new ArrayList<CustomerGroup>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			customerGroupList = session.createQuery("FROM CustomerGroup").list(); 
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING CustomerGroup LIST " + e.getMessage());
	         throw e;
	     }finally {
	         //session.close(); 
	      }
		logger.info("--------CustomerGroupDaoImpl findAll method end-------");
		return customerGroupList ;
	}
	
	@SuppressWarnings("unchecked")
	public List<CustomerGroup> findCustomerGroupByCustomerId(int customerId) {
		logger.info("--------customergroupDaoImpl findCustomerGroupByCustomerId method started----");
		List<CustomerGroup> customerList = new ArrayList();

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session
				.createSQLQuery("SELECT * FROM  customer_group p where customer_id = :customer_name_id and active ='Y' ");

		query.setParameter("customer_name_id", customerId);
		 customerList = query.list();
	    
	   logger.info("--------customergroupDaoImpl findCustomerGroupByCustomerId method started----");
	   return customerList;
	}

	public CustomerGroup findByGroupId(int groupId) {

		Criteria criteria = ((Session) getEntityManager().getDelegate())
				.createCriteria(CustomerGroup.class);
		criteria.add(Restrictions.eq("groupId", groupId));
		CustomerGroup customerGroup = (CustomerGroup) criteria.uniqueResult();
		logger.info("--------customergroupDaoImpl find by id method started----");
		return customerGroup;

		/*
		 * Session session=(Session) getEntityManager().getDelegate();
		 * CustomerGroup customer ; Query query =
		 * session.createQuery("From CustomerGroup c where c.groupId = :groupId"
		 * ); query.setParameter("groupId", groupId);
		 * 
		 * customer = (CustomerGroup) query.uniqueResult();
		 * 
		 * logger.info("--------customergroupDaoImpl find by id method started----"
		 * ); return customer;
		 */
	}
	
	

	public List<CustomerGroup> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}
	


	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<CustomerGroup> findAllActiveCustomerGroup(int customerId) {
		logger.info("--------customergroupDaoImpl find by id method started----");
		Session session = (Session) getEntityManager().getDelegate();
		List<CustomerGroup> customerActiveList = new ArrayList();
		Query query = session
				.createQuery("From CustomerGroup c where c.customerId.id = :customerId and c.active=:active");

		query.setParameter("customerId", customerId);
		query.setParameter("active", "Y");
		customerActiveList = query.list();

		logger.info("--------customergroupDaoImpl find by id method started----");
		return customerActiveList;

	}

	public boolean delete(int groupId) {
		// TODO Auto-generated method stub
		logger.info("------CustomerGroupDaoImpl delete method start------");
		boolean isSuccess = true;
		try {

			// Query query = HibernateUtil.getSession(sessionFactory)
			// .getNamedQuery(CustomerGroup.DELETE_CUSTOMER_GROUP_BASED_ON_ID).setInteger("groupId",
			// groupId);
			Query query = ((Session) getEntityManager().getDelegate()).createQuery(
					"delete From CustomerGroup c where c.groupId = :groupId");
			query.setParameter("groupId", groupId);

			int totalRowsDeleted = query.executeUpdate();

		} catch (HibernateException e) {

			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-" + e);
			e.printStackTrace();
			throw e;
		} catch (Exception ex) {

			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-" + ex);
			ex.printStackTrace();
		} finally {

		}

		logger.info("------CustomerGroupDaoImpl delete method end------");
		return isSuccess;
	}

	public List<CustomerGroup> findById(int customerId) {
		logger.info("--------customergroupDaoImpl find by id method started----");
	    Session session=(Session) getEntityManager().getDelegate();
	    List<CustomerGroup> customerList = new ArrayList();
	    

		Query query = session
				.createQuery("From CustomerGroup c where c.customerId.id = :customerId");
		query.setParameter("customerId", customerId);

		customerList = query.list();

		logger.info("--------customergroupDaoImpl find by id method started----");
		return customerList;
	}

	public List<CustomerGroup> findCustomerGroupByCustomerIds(
			List<Integer> customerIds) {
		logger.info("--------findCustomerGroupByCustomerIds method started----");

		List<CustomerGroup> customerList = new ArrayList<CustomerGroup>();
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session
				.createQuery("FROM  CustomerGroup p where p.customerId.id IN (:customer_name_id) and p.active ='Y' ");
		query.setParameterList("customer_name_id", customerIds);
		customerList = query.list();
	    
	   logger.info("--------findCustomerGroupByCustomerIds ended----");
	   return customerList;
	}
	
	
	// performing Active/DeActive action with single function
		public boolean activateOrDeactivateCustomerGroup(int id,
				String groupStatus, int custId, String custGroupName) {
			// TODO Auto-generated method stub
			logger.info("------------customergroupDaoImpl activateOrDeactivateCustomerGroup method start------------");
			boolean isSuccess = true;
			try {
				Query query = null;
				int totalRowsDeleted=0;
				if(groupStatus.equals("Y")){
				
				query =((Session) getEntityManager().getDelegate())
						.createQuery("UPDATE CustomerGroup b set b.active=:activate where b.groupId=:groupId AND b.customerId.id=:customerId AND b.customerGroupName=:customerGroupName");
				query.setParameter("activate", "N");
				query.setParameter("groupId", id);
				query.setParameter("customerId", custId);
				query.setParameter("customerGroupName", custGroupName);
				

			     totalRowsDeleted = query.executeUpdate();
				}else if(groupStatus.equals("N")){
					
					query = ((Session) getEntityManager().getDelegate())
							.createQuery("UPDATE CustomerGroup b set b.active=:activate where b.groupId=:groupId AND b.customerId.id=:customerId AND b.customerGroupName=:customerGroupName");
					query.setParameter("activate", "Y");
					query.setParameter("groupId", id);
					query.setParameter("customerId", custId);
					query.setParameter("customerGroupName", custGroupName);
					

				     totalRowsDeleted = query.executeUpdate();
					}
				
				System.out.println("No. of rows updated In CustomerGroup table"
						+ totalRowsDeleted);
               } catch (HibernateException e) {
				isSuccess = false;
				e.printStackTrace();
				logger.error("Exception Occurred while softdeleting CustomerGroup "
						+ id + e.getMessage());
				throw e;
			} finally {
				// currentSession.close();
			}
			logger.info("------------customergroupDaoImpl activateOrDeactivateCustomerGroup method end------------");
			return isSuccess;

		}
	
	public boolean validateCustGroupName(int custId, String custGroupName)
	{
		Session session = (Session) getEntityManager().getDelegate();
		CustomerGroup custGroup=null;
		Criteria criteria = session.createCriteria(CustomerGroup.class);
		criteria.add(Restrictions
				.conjunction()
				.add(Restrictions
						.eq("customerGroupName",
								custGroupName))
				.add(Restrictions.eq("customerId.id",
						custId)))
				.add(Restrictions.eq("active", "Y"));
		if(criteria.list().size()>0)
			custGroup= (CustomerGroup) criteria.list().get(0);
		if(custGroup!=null)
			return true;
		else
			return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<CustomerGroup> findCustGroupByCustomerId(int customerId) {
		logger.info("--------customergroupDaoImpl findCustGroupByCustomerId method started----");
		List<CustomerGroup> customerList = new ArrayList<CustomerGroup>();

		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createSQLQuery(
				"SELECT * FROM  customer_group p where customer_id = :customer_name_id").addEntity(CustomerGroup.class);

		query.setParameter("customer_name_id", customerId);
		customerList = query.list();
		logger.info("--------customergroupDaoImpl findCustGroupByCustomerId method end----");
		return customerList;
	}

	// SaveOrUpdate CustomerGroup
	public boolean saveUpdateCustomer(String poGroupId, String poGroupName,
			String mCusotmerId) {
		logger.info("------------customergroupDaoImpl saveUpdateCustomer method start------------");
		boolean isSuccess = true;
		try {
			String userName = UserUtil.getUserContextDetails().getUserName();
			
			String pattern = "YYYY-MM-DD HH:mm:ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String creation_timestamp = simpleDateFormat.format(new Date());
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			int result = 0;
			Query query = null;
			if (poGroupId.equals("null") || poGroupId == null) {
				
				query = ((Session) getEntityManager().getDelegate())
						.createSQLQuery("INSERT INTO customer_group(customer_id,group_name,created_id,creation_timestamp,lastupdated_id,lastupdated_timestamp,active) VALUES ('"
						+ mCusotmerId
						+"','"
						+ poGroupName
						+ "','"
						+ userName
						+ "','"
						+ timestamp
						+ "','"
						+ userName
						+ "','" + timestamp + "','Y')");
						result = query.executeUpdate();
			}
			else{
				
				query = ((Session) getEntityManager().getDelegate())
						.createSQLQuery("UPDATE customer_group cg SET cg.group_name=:group_name,cg.lastupdated_id=:lastupdated_id,cg.lastupdated_timestamp=:lastupdated_timestamp WHERE group_id='"+poGroupId+"' ");
						 
				query.setParameter("group_name", poGroupName);
                query.setParameter("lastupdated_id", userName);
                query.setParameter("lastupdated_timestamp", timestamp);
			    result = query.executeUpdate();
				
			}
			if (result > 0) {
				return true;
			} else{
				return false;
			}
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			throw e;
		} finally {
		}
	}

}
