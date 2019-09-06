package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ProjectTicketsDao;
import org.yash.rms.domain.ProjectTicketPriority;

/**
 * @author sarang.patil
 *
 */
@Repository("ProjectTicketsDao")
@Transactional
public class ProjectTicketsDaoImpl implements ProjectTicketsDao{
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectTicketsDaoImpl.class);
	
	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	public boolean delete(int id) {
		logger.info("------------ProjectTicketsDaoImpl delete method start------------");
		
		boolean isSuccess=true;
		try {
			boolean isExistInRecord = false;
			if(isExistInRecord){
				System.out.println(" cannot be deleted, as timesheet for this activity is filled. ");
				return false;
			}else{
				Session currentSession = (Session) getEntityManager().getDelegate();
				ProjectTicketPriority priorityObj = (ProjectTicketPriority) currentSession.get(ProjectTicketPriority.class, id);
				currentSession.delete(priorityObj);
			}
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while deleting Activity "+id+e.getMessage());
			 throw e;
		}  finally {
		}
		
		logger.info("------------ProjectTicketsDaoImpl delete method end------------");
		return isSuccess;
	}

	public boolean saveOrupdate(ProjectTicketPriority priority) {
		logger.info("------ProjectTicketsDaoImpl saveOrUpate method start-----");
		
		if(null == priority) return false;
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			if(priority.getId() != null) {
				ProjectTicketPriority obj = (ProjectTicketPriority) currentSession.get(ProjectTicketPriority.class, priority.getId());
				priority.setActive(obj.getActive());
				currentSession.evict(obj);
			}
			currentSession.saveOrUpdate(priority);
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while saving priority "+priority.getPriority()+e.getMessage());
			 throw e;
		}   finally {
		}
		
		logger.info("------ProjectTicketsDaoImpl saveOrUpate method end-----");
		return isSuccess;
	}

	public List<ProjectTicketPriority> findAll() {
		return null;
	}

	public List<ProjectTicketPriority> findByEntries(int firstResult, int sizeNo) {
		return null;
	}

	public long countTotal() {
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProjectTicketPriority> getConfiguredTicketPriorities(int projectId) {
		logger.info("--------ProjectTicketsDaoImpl getConfiguredTicketPriorities method start-------");
		
		Session session=(Session) getEntityManager().getDelegate();
		Criteria criteria =  session.createCriteria(ProjectTicketPriority.class).add(Restrictions.eq("projectId.id", projectId));
		criteria.add(Restrictions.or(Restrictions.like("active",0)));
		List<ProjectTicketPriority> list = criteria.list();
		
		logger.info("--------ProjectTicketsDaoImpl getConfiguredTicketPriorities method end-------");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProjectTicketPriority> getSelectedConfiguredTicketPriorities(int projectId) {
		logger.info("--------ProjectTicketsDaoImpl getSelectedConfiguredTicketPriorities method start-------");
		
		Session session=(Session) getEntityManager().getDelegate();
		Criteria criteria =  session.createCriteria(ProjectTicketPriority.class).add(Restrictions.eq("projectId.id", projectId));
		criteria.add(Restrictions.or(Restrictions.like("active",1)));
		List<ProjectTicketPriority> list = criteria.list();
		
		logger.info("--------ProjectTicketsDaoImpl getSelectedConfiguredTicketPriorities method end-------");
		return list;
	}
	
	public List<ProjectTicketPriority> findById(int id, List<Integer> activityIds) {
		logger.info("--------ProjectTicketsDaoImpl findById method start-------");
		
		List<ProjectTicketPriority> priorities = null;
		Session session = (Session) getEntityManager().getDelegate();
		try {
			priorities = new ArrayList<ProjectTicketPriority>();
			Criteria criteria =  session.createCriteria(ProjectTicketPriority.class).add(Restrictions.eq("projectId.id", id)); 
			
			priorities = criteria.list();
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING ACTIVITY By Id " +id+ e.getMessage());
	         throw e;	
	     }finally {
	         //session.close(); 
	      }
		
		logger.info("------ProjectTicketsDaoImpl findById method end-----");
		return priorities;
	}
	

	public Integer setInactiveByProjectId(Integer projectId){
		logger.info("--------ProjectTicketsDaoImpl setInactiveByProjectId method start-------");
		Integer entryUpdated = null;
		Session session = (Session) getEntityManager().getDelegate();
		try {
			
			String hqlUpdate = "update ProjectTicketPriority c set c.active = :afterUpdateActive where c.projectId = :projectId and c.active = :beforeUpdateActive";
			entryUpdated = session.createQuery( hqlUpdate ).setInteger("afterUpdateActive", 0).setInteger("projectId", projectId).setInteger("beforeUpdateActive", 1).executeUpdate();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception Occurred while setInactiveByProjectId "+e.getMessage());
			throw e;
		}
		
		logger.info("--------ProjectTicketsDaoImpl setInactiveByProjectId method end-------");
		return entryUpdated;
	}
	public boolean saveProjectPriorities(List<String> prioritiesList, Integer projectId) {
		logger.info("--------ProjectTicketsDaoImpl saveProjectPriorities method start-------");
		
		Session session = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			
			/*String hqlUpdate = "update ProjectTicketPriority c set c.active = :active where c.projectId = :projectId";
			int updatedEntities = session.createQuery( hqlUpdate ).setInteger("active", 0).setInteger("projectId", projectId).executeUpdate();*/
			setInactiveByProjectId(projectId);
			for(String priorityId : prioritiesList) {
				ProjectTicketPriority obj = (ProjectTicketPriority) session.get(ProjectTicketPriority.class, Integer.parseInt(priorityId));
				obj.setActive(1);
				session.saveOrUpdate(obj);
			}
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while saving prioritylist "+prioritiesList+e.getMessage());
			 throw e;
		}   finally {
		}
		
		logger.info("--------ProjectTicketsDaoImpl saveProjectPriorities method end-------");
		return isSuccess;
	}

	/* (non-Javadoc)
	 * Method for getting Project ticket  priority by id
	 */
	public ProjectTicketPriority findProjectTicketPriorityById(
			Integer ticketPriorityId) {
		logger.info("Inside findProjectTicketPriorityById for ticketPriorityId "+ticketPriorityId+" on "+this.getClass().getCanonicalName());
		ProjectTicketPriority projectTicketPriority=null;
		Session session = (Session) getEntityManager().getDelegate();
		try {
			Criteria criteria =  session.createCriteria(ProjectTicketPriority.class).add(Restrictions.eq("id", ticketPriorityId)); 			
			projectTicketPriority = (ProjectTicketPriority) criteria.uniqueResult();
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("Exception occured in findProjectTicketPriorityById for ticketPriorityId " +ticketPriorityId
	        		 + " Error Msg is :-"+e.getMessage());
	         throw e;	
	     }finally {
	         //session.close(); 
	      }
		logger.info("------"+this.getClass().getCanonicalName()+" findProjectTicketPriorityById method end-----");
		return projectTicketPriority;
	}

	public List<ProjectTicketPriority> getActiveProjectTicketPriorityForProjectId(
			Integer projectId) {
		logger.info("Inside getActiveProjectTicketPriorityForProjectId for projectId "+projectId+" on "+this.getClass().getCanonicalName());
		List<ProjectTicketPriority> projectTicketPriorityList=new ArrayList<ProjectTicketPriority>();
		Session session = (Session) getEntityManager().getDelegate();
		try {
			Criteria criteria =  session.createCriteria(ProjectTicketPriority.class)
					.add(Restrictions.and(Restrictions.eq("projectId.id", projectId),Restrictions.eq("active", 1))); 		
			projectTicketPriorityList =  criteria.list();
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("Exception occured in getActiveProjectTicketPriorityForProjectId for projectId " +projectId
	        		 + " Error Msg is :-"+e.getMessage());
	         throw e;	
	     }finally {
	         //session.close(); 
	      }
		logger.info("------"+this.getClass().getCanonicalName()+" getActiveProjectTicketPriorityForProjectId method end-----");
		return projectTicketPriorityList;
	}
	
}
