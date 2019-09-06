package org.yash.rms.dao.impl;

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
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.GradeDao;
import org.yash.rms.domain.Grade;
import org.yash.rms.util.UserUtil;
@Transactional
@Repository("gradeDao")
public class GradeDaoImpl implements GradeDao {
	private static final Logger logger = LoggerFactory.getLogger(GradeDaoImpl.class);

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
	public List<Grade> findAll() {
		logger.info("--------GradeDaoImpl findAll method start-------");

		List<Grade> gradeList = new ArrayList<Grade>();
		
		Session session=(Session) getEntityManager().getDelegate();
		
		try {
			
			gradeList = session.createQuery("FROM Grade").list(); 
		
		} catch (HibernateException e) {
			 logger.error("Exception occured in findAll method at DAO layer:-"+e);
	         e.printStackTrace(); 
	      }finally {
	          
	      }
		logger.info("------------GradeDaoImpl findAll method end------------");

		return gradeList ;
	}

		@SuppressWarnings("unused")
		public boolean saveOrupdate(Grade grade) {
			logger.info("------GradeDaoImpl saveOrUpdate method start------");
			if(null == grade) return false;
			
			Session currentSession = (Session) getEntityManager().getDelegate();
			Transaction transaction=null;
			boolean isSuccess=true;
			try {
				
				if(grade.getId()==0)
				{
					
					grade.setCreatedId( UserUtil.getUserContextDetails().getUserName());
					grade.setCreationTimeStamp(new Date());
					
				}
				
				grade.setLastUpdatedId( UserUtil.getUserContextDetails().getUserName());
			
				isSuccess = currentSession.merge(grade)!=null?true:false;
			} catch (HibernateException e) {
				if (transaction != null) {
				}
				isSuccess = false;
				logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);

				e.printStackTrace();
			} catch (Exception ex) {
				if (transaction != null) {
				}
				isSuccess = false;
				logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+ex);

				ex.printStackTrace();
			} finally {

			}
			logger.info("------GradeDaoImpl saveOrUpate method end-----");
			return isSuccess;
		}
		
		public boolean delete(int id) {
			logger.info("------GradeDaoImpl delete method start------");
			boolean isSuccess=true;
			try {

				 Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(Grade.DELETE_GRADE_BASED_ON_ID).setInteger("id", id);
				 int totalRowsDeleted = query.executeUpdate();
				System.out.println("Total Rows Deleted::" + totalRowsDeleted);

			} catch (HibernateException e) {
			
				isSuccess = false;
				logger.error("HibernateException occured in delete method at DAO layer:-"+e);
				e.printStackTrace();
				 throw e;
			} catch (Exception ex) {
				
				isSuccess = false;
				logger.error("HibernateException occured in delete method at DAO layer:-"+ex);
				ex.printStackTrace();
			} finally {

			}
			logger.info("------GradeDaoImpl delete method end------");
			return isSuccess;
		}
		


		public List<Grade> findByEntries(int firstResult, int sizeNo) {
			return null;
		}

		public long countTotal() {
			return 0;
		}

		public Grade findById(int id) {
			// TODO Auto-generated method stub
			Grade grade = null;
			logger.info("--------ActivityDaoImpl findById method start-------");
			Session session=(Session) getEntityManager().getDelegate();
			try {
				grade = (Grade)session.createCriteria(Grade.class).add(Restrictions.eq("id", id)).uniqueResult(); 
			} catch (HibernateException e) {
		         e.printStackTrace(); 
		         logger.error("EXCEPTION CAUSED IN GETTING GRADE By Id " +id+ e.getMessage());
		         throw e;
		     }finally {
		         //session.close(); 
		      }
			logger.info("------GradeDaoImpl findById method end-----");
			return grade ;
		}

		public Grade findByName(String name) {
			logger.info("------GradeDaoImpl findByName method start-----");
			
			Session session = (Session) getEntityManager().getDelegate();
			Grade grade = new Grade();
			try {
				Criteria criteria = session.createCriteria(Grade.class);
				criteria.add(Restrictions.eq("grade", name));
				grade =  (Grade) criteria.uniqueResult();
				
			} catch (Exception e) {
				logger.error("Error in GradeDaoImpl findByName method", e);
			}
			
			logger.info("------GradeDaoImpl findByName method end-----");
			return grade;
		}
		
	}

