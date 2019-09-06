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
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.PDLEmailGroupDao;
import org.yash.rms.domain.PDLEmailGroup;
import org.yash.rms.util.HibernateUtil;
import org.yash.rms.util.UserUtil;

@Repository("pdlEmailDao")
@Transactional
public class PDLEmailGroupDaoImpl implements PDLEmailGroupDao {
	  
	private static final Logger logger = LoggerFactory.getLogger(PDLEmailGroupDaoImpl.class);

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	 
	public List<PDLEmailGroup> getPdlEmails() throws Exception{
		logger.info("--------PDLEmailGroupDaoImpl getPdlEmails method start-------");
		List<PDLEmailGroup> pdlEmailGroupList = new ArrayList<PDLEmailGroup>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			pdlEmailGroupList = session.createCriteria(PDLEmailGroup.class).list(); 
		} catch (HibernateException e) {
			logger.error("Exception occured in findAll method at DAO layer:-"+e);
	         e.printStackTrace(); 
	     }finally {
	         //session.close(); 
	      }
		logger.info("------------PDLEmailGroupDaoImpl getPdlEmails method end------------");
		return pdlEmailGroupList ;
	}
	//For Get Only Active PDL Email for RRF PDL Mail
	@SuppressWarnings("unchecked")
	public List<PDLEmailGroup> getActivePdlEmails() throws Exception{
		logger.info("--------PDLEmailGroupDaoImpl getActivePdlEmails method start-------");
		List<PDLEmailGroup> pdlEmailGroupList = new ArrayList<PDLEmailGroup>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			pdlEmailGroupList = session.createCriteria(PDLEmailGroup.class).add(Restrictions.eq("active","Y")).list();
		} catch (HibernateException e) {
			logger.error("Exception occured in getActivePdlEmails method at DAO layer:-"+e);
	         e.printStackTrace(); 
	     }finally {
	     
	     }
		logger.info("------------PDLEmailGroupDaoImpl getActivePdlEmails method end------------");
		return pdlEmailGroupList ;
	}
	
	/*Sonali Added*/
	
	public List<String> findPdl_nameByIds(ArrayList<Integer> pdlList){
		
		logger.info("--------PDLEmailGroupDaoImpl findPdlByIds method start-------");
		try {
			Session session = (Session) getEntityManager().getDelegate();
			Query query = session.createSQLQuery("SELECT pdl_name from pdl_email_group where id IN (:pdlList) ");
			query.setParameterList("pdlList", pdlList);		
			List<String> emailList = query.list();	
			return emailList;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findPdlByIds method at PDLEmailGroupDaoImpl :-" + ex);
			throw ex;
		}
	}
	/*Sonali Added*/

	public List<String> findPdlByIds(ArrayList<Integer> pdlList) {
		
		logger.info("--------PDLEmailGroupDaoImpl findPdlByIds method start-------");
		try {
			Session session = (Session) getEntityManager().getDelegate();
			Query query = session.createSQLQuery("SELECT pdl_email_id from pdl_email_group where id IN (:pdlList) ");
			query.setParameterList("pdlList", pdlList);		
			List<String> emailList = query.list();	
			return emailList;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findPdlByIds method at PDLEmailGroupDaoImpl :-" + ex);
			throw ex;
		}
	}
	
	//Update pdlEmailId according to customerId
	public List<PDLEmailGroup> updatePdlEmail(String custEmail,
			String NewcustEmail) {

		try {
			Query query = null;

			query = ((Session) getEntityManager().getDelegate())
					.createQuery(
							"UPDATE PDLEmailGroup b set b.pdlEmailId= :pdlEmailId where b.pdlEmailId = :custEmail");
			query.setParameter("custEmail", custEmail);
			query.setParameter("pdlEmailId", NewcustEmail);
			int totalRowsDeleted = query.executeUpdate();
			System.out.println("No. of rows updated " + totalRowsDeleted);

		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		} finally {
			// currentSession.close();
		}
		logger.info("------------pdlEmailIdDaoImpl updatePdlEmail method end------------");
		return null;

	}
	
	
	
	
	

	public boolean delete(int id) {
		logger.info("------PDLEmailGroupDaoImpl delete method start------");
        boolean isSuccess = true;
        try {
 
//          Query query = HibernateUtil.getSession(sessionFactory)
//                  .getNamedQuery(CustomerGroup.DELETE_CUSTOMER_GROUP_BASED_ON_ID).setInteger("groupId", groupId);
             Query query =
             ((Session) getEntityManager().getDelegate()).createQuery("delete From PDLEmailGroup p where p.id = :id");
             query.setParameter("id", id);
 
             
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
 
        logger.info("------PDLEmailGroupDaoImpl delete method end------");
        return isSuccess;
	}

	public boolean saveOrupdate(PDLEmailGroup pDLEmailGroup) {
		logger.info("------------ownershipDaoImpl saveOrupdate method end------------");
		if(null == pDLEmailGroup) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			if(pDLEmailGroup.getId()==0)
			{
				pDLEmailGroup.setCreatedTimestamp(new Date());
				pDLEmailGroup.setCreated_by(UserUtil.getCurrentResource().getUsername());
				
			}
			pDLEmailGroup.setModifiedBy(UserUtil.getCurrentResource().getUsername());
			pDLEmailGroup.setModifiedTimestamp(new Date());
			currentSession.saveOrUpdate(pDLEmailGroup);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}
		return isSuccess;
	}

	public List<PDLEmailGroup> findAll() {
		logger.info("------------pdlEmailGroupDaoImpl findAll method start------------");
		List<PDLEmailGroup> pdlList = new ArrayList<PDLEmailGroup>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			pdlList = session.createQuery("FROM PDLEmailGroup").list(); 
		} catch (HibernateException e) {
			logger.error("HibernateException occured in findAll method at DAO layer:-"+e);
			throw e; 
	      }
		return pdlList ;
	}

	public List<PDLEmailGroup> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean activateOrDeactivatePDLEmailGroup(int id, String activateStatus) {
		logger.info("------------pdlEmailGroupDaoImpl activateOrDeactivatePDLEmailGroup method start------------");
		boolean isSuccess = true;
		try {
				Query query=null;

			 query = ((Session) getEntityManager().getDelegate()).createQuery(
					" UPDATE PDLEmailGroup b set b.active= :activate where b.id = :id");
			query.setParameter("activate", activateStatus);
			query.setParameter("id", id);	
			int totalRowsDeleted = query.executeUpdate();
			System.out.println("No. of rows updated "+totalRowsDeleted);

		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while softdeleting PDL Email Group "+id+e.getMessage());
			 throw e;
		}  finally {
			// currentSession.close();
		}
		logger.info("------------pdlEmailGroupDaoImpl activateOrDeactivatePDLEmailGroup method end------------");
		return isSuccess;
	}

	

}
