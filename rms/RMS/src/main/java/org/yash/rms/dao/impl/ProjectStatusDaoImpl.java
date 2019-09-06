package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ProjectStatusDao;
import org.yash.rms.domain.ProjectTicketPriority;
import org.yash.rms.domain.ProjectTicketStatus;

/**
 * @author pratyoosh.tripathi
 *
 */
@Repository("ProjectStatusDao")
@Transactional
public class ProjectStatusDaoImpl implements ProjectStatusDao{
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectStatusDaoImpl.class);
	
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
		logger.info("------------ProjectStatusDaoImpl delete method start------------");
		
		boolean isSuccess=true;
		try {
			boolean isExistInRecord = false;
			if(isExistInRecord){
				System.out.println(" cannot be deleted, as timesheet for this activity is filled. ");
				return false;
			}else{
				Session currentSession = (Session) getEntityManager().getDelegate();
				ProjectTicketStatus statusObj = (ProjectTicketStatus) currentSession.get(ProjectTicketStatus.class, id);
				currentSession.delete(statusObj);
			}
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while deleting Activity "+id+e.getMessage());
			 throw e;
		}  finally {
		}
		
		logger.info("------------ProjectStatusDaoImpl delete method end------------");
		return isSuccess;
	}

	public boolean saveOrupdate(ProjectTicketStatus status) {
		logger.info("------ProjectStatusDaoImpl saveOrUpate method start-----");
		
		if(null == status) return false;
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			if(status.getId() != null) {
				ProjectTicketStatus obj = (ProjectTicketStatus) currentSession.get(ProjectTicketStatus.class, status.getId());
				status.setActive(obj.getActive());
				currentSession.evict(obj);
			}
			currentSession.saveOrUpdate(status);
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while saving status "+status.getStatus()+e.getMessage());
			 throw e;
		}   finally {
		}
		
		logger.info("------ProjectStatusDaoImpl saveOrUpate method end-----");
		return isSuccess;
	}

	public List<ProjectTicketStatus> findAll() {
		return null;
	}

	public List<ProjectTicketStatus> findByEntries(int firstResult, int sizeNo) {
		return null;
	}

	public long countTotal() {
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProjectTicketStatus> getConfiguredTicketStatus(int projectId) {
		logger.info("--------ProjectStatusDaoImpl getConfiguredTicketStatus method start-------");
		
		Session session=(Session) getEntityManager().getDelegate();
		Criteria criteria =  session.createCriteria(ProjectTicketStatus.class).add(Restrictions.eq("projectId.id", projectId));
		criteria.add(Restrictions.or(Restrictions.like("active",0)));
		List<ProjectTicketStatus> list = criteria.list();
		
		logger.info("--------ProjectStatusDaoImpl getConfiguredTicketStatus method end-------");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProjectTicketStatus> getSelectedConfiguredTicketStatus(int projectId) {
		logger.info("--------ProjectStatusDaoImpl getSelectedConfiguredTicketStatus method start-------");
		
		Session session=(Session) getEntityManager().getDelegate();
		Criteria criteria =  session.createCriteria(ProjectTicketStatus.class).add(Restrictions.eq("projectId.id", projectId));
		criteria.add(Restrictions.or(Restrictions.like("active",1)));
		List<ProjectTicketStatus> list = criteria.list();
		
		logger.info("--------ProjectStatusDaoImpl getSelectedConfiguredTicketStatus method end-------");
		return list;
	}
	
	public List<ProjectTicketStatus> findById(int id, List<Integer> activityIds) {
		logger.info("--------ProjectStatusDaoImpl findById method start-------");
		
		List<ProjectTicketStatus> status = null;
		Session session = (Session) getEntityManager().getDelegate();
		try {
			status = new ArrayList<ProjectTicketStatus>();
			Criteria criteria =  session.createCriteria(ProjectTicketStatus.class).add(Restrictions.eq("projectId.id", id)); 
			
			status = criteria.list();
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING ACTIVITY By Id " +id+ e.getMessage());
	         throw e;	
	     }finally {
	         //session.close(); 
	      }
		
		logger.info("------ProjectStatusDaoImpl findById method end-----");
		return status;
	}
	

	public Integer setInactiveByProjectId(Integer projectId){
		logger.info("--------ProjectStatusDaoImpl setInactiveByProjectId method start-------");
		Integer entryUpdated = null;
		Session session = (Session) getEntityManager().getDelegate();
		try {
			String hqlUpdate = "update ProjectTicketStatus c set c.active = :afterUpdateActive where c.projectId = :projectId and c.active = :beforeUpdateActive";
			entryUpdated = session.createQuery( hqlUpdate ).setInteger("afterUpdateActive", 0).setInteger("projectId", projectId).setInteger("beforeUpdateActive", 1).executeUpdate();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception Occurred while setInactiveByProjectId "+e.getMessage());
			throw e;
		}
		logger.info("--------ProjectStatusDaoImpl setInactiveByProjectId method end-------");
		return entryUpdated;
	}
	public boolean saveProjectStatus(List<String> statusList, Integer projectId) {
		logger.info("--------ProjectStatusDaoImpl saveProjectStatus method start-------");
		
		Session session = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			
			/*String hqlUpdate = "update ProjectTicketPriority c set c.active = :active where c.projectId = :projectId";
			int updatedEntities = session.createQuery( hqlUpdate ).setInteger("active", 0).setInteger("projectId", projectId).executeUpdate();*/
			setInactiveByProjectId(projectId);
			for(String statusId : statusList) {
				ProjectTicketStatus obj = (ProjectTicketStatus) session.get(ProjectTicketStatus.class, Integer.parseInt(statusId));
				obj.setActive(1);
				session.saveOrUpdate(obj);
			}
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while saving saveProjectStatus "+statusList+e.getMessage());
			 throw e;
		}   finally {
		}
		
		logger.info("--------ProjectStatusDaoImpl saveProjectStatus method end-------");
		return isSuccess;
	}

	public ProjectTicketStatus findProjectTicketStatusById(
			Integer ticketStatusId) {
		logger.info("Inside findProjectTicketStatusById for ticketStatusId "+ticketStatusId+" on "+this.getClass().getCanonicalName());
		ProjectTicketStatus projectTicketStatus=null;
		Session session = (Session) getEntityManager().getDelegate();
		try {
			Criteria criteria =  session.createCriteria(ProjectTicketPriority.class).add(Restrictions.eq("id", ticketStatusId)); 			
			projectTicketStatus = (ProjectTicketStatus) criteria.uniqueResult();
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("Exception occured in findProjectTicketStatusById for ticketStatusId " +ticketStatusId
	        		 + " Error Msg is :-"+e.getMessage());
	         throw e;	
	     }finally {
	         //session.close(); 
	      }
		logger.info("------"+this.getClass().getCanonicalName()+" findProjectTicketStatusById method end-----");
		return projectTicketStatus;
	}

	public List<ProjectTicketStatus> getActiveProjectTicketStatusForProjectId(
			Integer projectId) {
		logger.info("Inside getActiveProjectTicketStatusForProjectId for projectId "+projectId+" on "+this.getClass().getCanonicalName());
		List<ProjectTicketStatus> projectTicketStatusList=new ArrayList<ProjectTicketStatus>();
		Session session = (Session) getEntityManager().getDelegate();
		try {
			Criteria criteria =  session.createCriteria(ProjectTicketStatus.class)
					.add(Restrictions.and(Restrictions.eq("projectId.id", projectId),Restrictions.eq("active", 1))); 			
			projectTicketStatusList =  criteria.list();
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("Exception occured in getActiveProjectTicketStatusForProjectId for projectId " +projectId
	        		 + " Error Msg is :-"+e.getMessage());
	         throw e;	
	     }finally {
	         //session.close(); 
	      }
		logger.info("------"+this.getClass().getCanonicalName()+" getActiveProjectTicketStatusForProjectId method end-----");
		return projectTicketStatusList;
	}
	
}
