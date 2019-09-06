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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.ProjectCategory;
import org.yash.rms.util.UserUtil;

@Repository("ProjectCategoryDao")
@Transactional
public class ProjectCategoryDaoImpl implements RmsCRUDDAO<ProjectCategory> {

	private static final Logger logger = LoggerFactory.getLogger(ProjectCategoryDaoImpl.class);
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
		logger.info("------------ProjectCategoryDaoImpl delete method end------------");
				boolean isSuccess = true;
				try {
					Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(
							ProjectCategory.DELETE_PROJECTCATEGORY_BASED_ON_ID)
							.setInteger("id", id);
					int totalRowsDeleted = query.executeUpdate();
					System.out.println("Total Rows Deleted::" + totalRowsDeleted);
				}  catch (HibernateException e) {
					isSuccess = false;
					logger.error("HibernateException occured in delete method at DAO layer:-"+e);
					throw e;
				}  
				logger.info("------------ProjectCategoryDaoImpl delete method end------------");
				return isSuccess;
	}

	public boolean saveOrupdate(ProjectCategory projectCategory) {
		logger.info("------------ProjectCategoryDaoImpl delete method end------------");
		if (null == projectCategory)
			return false;

		boolean isSuccess = true;
		try {
			if(projectCategory.getId()==null)
			{
				projectCategory.setCreationTimestamp(new Date());
				projectCategory.setCreatedId(UserUtil.getCurrentResource().getUsername());
				
			}
			projectCategory.setLastUpdatedId(UserUtil.getCurrentResource().getUsername());
			 ((Session) getEntityManager().getDelegate()).saveOrUpdate(projectCategory);
		}  catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------ProjectCategoryDaoImpl delete method end------------");
		return isSuccess;
	}

	@SuppressWarnings("unchecked")
	public List<ProjectCategory> findAll() {
		logger.info("------------ProjectCategoryDaoImpl findAll method end------------");
		List<ProjectCategory> category = new ArrayList<ProjectCategory>();
		try{
		category = ((Session) getEntityManager().getDelegate()).createQuery("FROM ProjectCategory")
				.list();}
		catch (HibernateException e) {
			logger.error("HibernateException occured in delete method at DAO layer:-"+e);
			throw e;
		}
		return category;
	}

	public List<ProjectCategory> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}
}
