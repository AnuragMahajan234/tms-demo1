package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.SEPGPhasesDao;
import org.yash.rms.domain.SEPGPhases;

/**
 * 
 * @author dinesh.kumar
 * 
 */

@Repository("SEPGPhaseDao")
@Transactional
public class SEPGPhasesDaoImpl implements SEPGPhasesDao {

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory.getLogger(SEPGPhasesDaoImpl.class);

	
	public List<SEPGPhases> findAll() {
		logger.info("----SEPGPhasesDaoImpl start finall method-------------");
		List<SEPGPhases> sepgList = new ArrayList<SEPGPhases>();
		Session currentSession=(Session) getEntityManager().getDelegate();
		try {
			sepgList = currentSession.createQuery("FROM SEPGPhases").list(); 
		} catch (HibernateException hibernateException) {
			 logger.error("Exception occured in findAll method at DAO layer:-"+hibernateException.getMessage());
			 hibernateException.printStackTrace(); 
	     }
		logger.info("----SEPGPhasesDaoImpl end finall method-------------");
		return (((sepgList!=null) && (!sepgList.isEmpty())?sepgList:new ArrayList<SEPGPhases>()));
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean saveOrupdate(SEPGPhases t) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<SEPGPhases> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void save(List<SEPGPhases> sepgPhasesList) {
		logger.info("----SEPGPhasesDaoImpl start save method-------------");		
		Session currentSession = (Session) getEntityManager().getDelegate();
		if(currentSession==null)
		throw new RuntimeException("Session Object Cannot be null");
		try { 
			 if (sepgPhasesList != null && sepgPhasesList.size() > 0){	
							for (SEPGPhases sepgPhases : sepgPhasesList) {
								currentSession.save(sepgPhases);
								
				
							}
			 }
		  } catch (Exception e) {
			 logger.error("Exception occured in findAll method at DAO layer:-"+e.getMessage());
			e.printStackTrace();
			currentSession.close();
		} finally{
			currentSession.flush();
			
		}
		logger.info("----SEPGPhasesDaoImpl end save method-------------");		
		
	}

	public void delete(SEPGPhases sepgPhases) {
		logger.info("----SEPGPhasesDaoImpl start delete method-------------");		
		Session currentSession = (Session) getEntityManager().getDelegate();
		if(currentSession==null)
		throw new RuntimeException("Session Object Cannot be null");
			
		try {
			currentSession.delete(sepgPhases);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 logger.error("EXCEPTION CAUSED IN Deleting Phase " + e.getMessage());
			 currentSession.close();
		}finally{
			currentSession.flush();
			
		}
		logger.info("----SEPGPhasesDaoImpl end delete method-------------");
		
	}

	public void update(SEPGPhases sepgPhases) {
		
		logger.info("----SEPGPhasesDaoImpl start update method-------------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		if(currentSession==null)
		throw new RuntimeException("Session Object Cannot be null");
			
		try {
			currentSession.update(sepgPhases);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("EXCEPTION CAUSED IN update Phase " + e.getMessage());
			 currentSession.close();
		}finally{
			currentSession.flush();
		}
		logger.info("----SEPGPhasesDaoImpl end update method-------------");
		
	}

	public SEPGPhases findSEPGPhaseById(int id) {
		
		logger.info("----SEPGPhasesDaoImpl start findSEPGPhaseById method-------------");	
		Session currentSession = (Session) getEntityManager().getDelegate();
		if(currentSession==null)
		throw new RuntimeException("Session Object Cannot be null");
		SEPGPhases sepgphase = (SEPGPhases)currentSession.get(SEPGPhases.class, id);
		logger.info("----SEPGPhasesDaoImpl end findSEPGPhaseById method-------------");	
		return ((sepgphase!=null)?sepgphase:new SEPGPhases());
		
	}

	public List<SEPGPhases> findByPhaseId(int id) {

		logger.info("----SEPGPhasesDaoImpl start findByPhaseId method-------------");	
		Session currentSession = (Session) getEntityManager().getDelegate();
		List<SEPGPhases> sepgList = new ArrayList<SEPGPhases>();
		if(currentSession==null)
		throw new RuntimeException("Session Object Cannot be null");
		Query query = currentSession.getNamedQuery(SEPGPhases.GETSEPG_BY_PHASEId).setInteger("id", id);
		sepgList = (List<SEPGPhases>)query.list();
		logger.info("----SEPGPhasesDaoImpl end findByPhaseId method-------------");	
		return (((sepgList!=null) && (!sepgList.isEmpty())?sepgList:new ArrayList<SEPGPhases>()));
		
	}

}
