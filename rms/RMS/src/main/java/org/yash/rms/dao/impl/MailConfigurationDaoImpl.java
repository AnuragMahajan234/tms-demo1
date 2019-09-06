package org.yash.rms.dao.impl;

import java.util.ArrayList;
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
import org.yash.rms.dao.MailConfigurationDao;
import org.yash.rms.domain.MailConfiguration;
import org.yash.rms.domain.Project;

@Repository("MailConfigurationDao")
@Transactional
public class MailConfigurationDaoImpl implements MailConfigurationDao {


@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(MailConfigurationDaoImpl.class);

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		logger.info("------------MailCofigurationDaoImpl delete method start------------");
		boolean isSuccess=true;
		try {
			 Query query = ((Session) getEntityManager().getDelegate()).createQuery("DELETE MailConfiguration a where a.id = :id").setInteger("id", id);
			 int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while deleting Activity "+id+e.getMessage());
			 throw e;
		}  finally {
//			currentSession.close();
		}
		logger.info("------------MailCofigurationDaoImpl delete method end------------");
		return isSuccess;
	}

	public boolean saveOrupdate(MailConfiguration t) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<MailConfiguration> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MailConfiguration> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<String> findById(int id) {
		// TODO Auto-generated method stub
		Session currentSession = (Session) getEntityManager().getDelegate();
		List<MailConfiguration> conf =new ArrayList<MailConfiguration>();
		List<String> s = new ArrayList<String>();
	conf=currentSession.createCriteria(MailConfiguration.class).add(Restrictions.eq("projectId.id", id)).list();

	for(MailConfiguration mail:conf)
	{ 	StringBuilder	idArray = new StringBuilder();
			idArray.append(mail.getConfgId().getId());
		idArray.append("_");
		idArray.append(mail.getRoleId().getId());
		idArray.append("_");
		if(mail.isTo())
		idArray.append("mailto");
		if(mail.isCc())
			idArray.append("mailcc");
		if(mail.isBcc())
			idArray.append("mailbcc");
		s.add(idArray.toString());
		
	}
	 return s;
	}

	public boolean saveConfigurations(List<MailConfiguration> list) {
		// TODO Auto-generated method stub
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		if (list != null && list.size() > 0) {
			for (MailConfiguration confg : list) {
			 	currentSession.merge(confg);
				
		}
		
	}
	
		return false;
	}

	
	
	
	public List<MailConfiguration> findByProjectId(int id,int confg) {
		// TODO Auto-generated method stub
		Session currentSession = (Session) getEntityManager().getDelegate();
		List<MailConfiguration> conf =new ArrayList<MailConfiguration>();
		List<String> s = new ArrayList<String>();
	conf=currentSession.createCriteria(MailConfiguration.class).add(Restrictions.eq("projectId.id", id)).add(Restrictions.eq("confgId.id", confg)).list();

	 
	 return conf;
	}

	public MailConfiguration getMailConfg(int projectId, int confg, int roleId) {
		// TODO Auto-generated method stub
		MailConfiguration mailConf = null;
		Session currentSession = (Session) getEntityManager().getDelegate();
		List<MailConfiguration> conf =new ArrayList<MailConfiguration>();
		List<String> s = new ArrayList<String>();
	conf=currentSession.createCriteria(MailConfiguration.class).add(Restrictions.eq("projectId.id", projectId)).add(Restrictions.eq("confgId.id", confg)).add(Restrictions.eq("roleId.id", roleId)).list();
		if(conf.size()>0)
		 
			mailConf =  conf.get(0);
		 return mailConf;
	}

	public boolean delete(int projectId, int confgId) {
		// TODO Auto-generated method stub
		logger.info("------------MailCofigurationDaoImpl delete method start------------");
		boolean isSuccess=true;
		try {
			 Query query = ((Session) getEntityManager().getDelegate()).createQuery("DELETE MailConfiguration a where a.projectId = :projectId and a.confgId = :confgId").setInteger("projectId", projectId).setInteger("confgId", confgId);
			 int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while deleting MailConfiguration "+confgId+e.getMessage());
			 throw e;
		}  finally {
//			currentSession.close();
		}
		logger.info("------------MailCofigurationDaoImpl delete method end------------");
		return isSuccess;
	}
	
	public void saveDefaultConfigs(Project project) {
		 Project proj=getProjectByName(project.getProjectName());
		 for(int j=4;j<10;j++){
		 for(int i=5;i<9;i++){
			 Query query = ((Session) getEntityManager().getDelegate()).createSQLQuery("INSERT INTO mails_configuration (project_id,confg_id,role_id,mail_to,mail_cc,mail_bcc) VALUE(?,?,?,?,?,?)");
			 query.setInteger(0, proj.getId());
			 query.setInteger(1,j);
			 query.setInteger(2, i);
			 query.setInteger(3, 1);
			 query.setInteger(4, 0);
			 query.setInteger(5, 0);
			 query.executeUpdate();	 
		 }
		 
		 }
	}
	
	private Project getProjectByName(String projName){
		return (Project) ((Session) getEntityManager().getDelegate()).createCriteria(Project.class).add(Restrictions.eq("projectName", projName)).uniqueResult();
	}
	
	
}
