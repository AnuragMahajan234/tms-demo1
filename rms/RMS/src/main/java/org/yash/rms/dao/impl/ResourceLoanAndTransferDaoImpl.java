/**
 * 
 */
package org.yash.rms.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.dao.ResourceLoanAndTransferDao;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceLoanTransfer;
import org.yash.rms.util.HibernateUtil;

/**
 * @author varun.haria
 * 
 */
@Repository("ResourceLoanAndTransferDao")
@Transactional
public class ResourceLoanAndTransferDaoImpl implements ResourceLoanAndTransferDao {


@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	@Autowired
	@Qualifier("ResourceDao")
	ResourceDao resourceDao;
	
	private static final Logger logger = LoggerFactory
			.getLogger(ResourceLoanAndTransferDaoImpl.class);

	public boolean delete(int id) {
		return false;
	}

	public boolean saveOrupdate(ResourceLoanTransfer resourceLoanTransfer) {
		logger.info("--------ResourceLoanAndTransferDaoImpl saveOrupdate method start-------");
		if (null == resourceLoanTransfer)
			return false;
		Resource resource=null;
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess = true;	
		try {
			List<ResourceLoanTransfer> resourceLoanTransfer2 = find(resourceLoanTransfer.getResourceId().getEmployeeId());
			resource=(Resource)currentSession.get(Resource.class, resourceLoanTransfer.getResourceId().getEmployeeId());
			
			if (resource.getUploadImage() != null) {
				System.out.println("--------ResourceLoanAndTransferDaoImpl saveOrupdate(at start) method for employeeId-------"+resource.getYashEmpId()+" and imae is "+resource.getUploadImage().toString());
			}
			
			if(resourceLoanTransfer2.size()==0){
				ResourceLoanTransfer loanTransfer = ResourceLoanTransfer.toResourceLoanTransfer(resource);
				currentSession.save(loanTransfer);
				currentSession.flush();
			}
			
			if (resourceLoanTransfer.getbGHName() != null) {
				if (resourceLoanTransfer.getbGHName().getEmployeeId() != null) {
					if (resourceLoanTransfer.getbGHName().getEmployeeId() < 0)
						resourceLoanTransfer.setbGHName(null);
				}else {
					resourceLoanTransfer.setbGHName(null);
				}
			}
			
			if (resourceLoanTransfer.getbUHName() != null) {
				if (resourceLoanTransfer.getbUHName().getEmployeeId() != null) {
					if (resourceLoanTransfer.getbUHName().getEmployeeId() < 0)
						resourceLoanTransfer.setbUHName(null);
				}else {
					resourceLoanTransfer.setbUHName(null);
				}
			}
			
			if (resourceLoanTransfer.gethRName() != null) {
				if (resourceLoanTransfer.gethRName().getEmployeeId() != null) {
					if (resourceLoanTransfer.gethRName().getEmployeeId() < 0)
						resourceLoanTransfer.sethRName(null);
				}else {
					resourceLoanTransfer.sethRName(null);
				}
			}
			
			/*if (resourceLoanTransfer.getEventId() != null) {
				if (resourceLoanTransfer.getEventId().getId() < 0){
						resourceLoanTransfer.setEventId(null);
				}else {
					resourceLoanTransfer.setEventId(null);
				}
			}*/
			
			populateResource(resource,resourceLoanTransfer);			
			resourceDao.saveOrupdate(resource);
			currentSession.save(resourceLoanTransfer);
			
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception occured in saveOrupdate method at DAO layer:-"
					+ e);
			throw e;
		} catch (RuntimeException ex) {
			isSuccess = false;
			ex.printStackTrace();
			logger.error("Exception occured in saveOrupdate method at DAO layer:-"
					+ ex);
			throw ex;
		}
		logger.info("--------ResourceLoanAndTransferDaoImpl saveOrupdate method start-------");
		return isSuccess;
	}

	
	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}


	public List<ResourceLoanTransfer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<ResourceLoanTransfer> find(int resourceId) {		
		logger.info("--------ResourceLoanAndTransferDaoImpl find method start-------");
		try {			
			Criteria criteria =((Session) getEntityManager().getDelegate())
					.createCriteria(ResourceLoanTransfer.class);
			logger.info("--------ResourceLoanAndTransferDaoImpl find method end-------"+resourceId);
			criteria.addOrder(Order.desc("id"));
			return (List<ResourceLoanTransfer>)criteria.add(Restrictions.eq("resourceId.id", resourceId)).list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in find method at DAO layer:-" + ex);
			throw ex;
		}
	}
	public List<ResourceLoanTransfer> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}
	private void populateResource(Resource resource,ResourceLoanTransfer resourceLoanTransfer)
	{
		resource.setBuId(resourceLoanTransfer.getBuId());		
		resource.setCurrentBuId(resourceLoanTransfer.getCurrentBuId());
		resource.setCurrentReportingManager(resourceLoanTransfer.getCurrentReportingManager());
		resource.setCurrentReportingManagerTwo(resourceLoanTransfer.getCurrentReportingManagerTwo());
		resource.setDesignationId(resourceLoanTransfer.getDesignationId());
		resource.setEmployeeId(resourceLoanTransfer.getResourceId().getEmployeeId());
		resource.setContactNumber(resourceLoanTransfer.getContactNumber());
		resource.setContactNumberTwo(resourceLoanTransfer.getContactNumberTwo());
		resource.setEmailId(resourceLoanTransfer.getEmailId());
		resource.setYashEmpId(resourceLoanTransfer.getYashEmpId());
		resource.setGradeId(resourceLoanTransfer.getGradeId());
		resource.setLocationId(resourceLoanTransfer.getLocationId());
		resource.setEmployeeCategory(resourceLoanTransfer.getEmployeeCategory());
		resource.setOwnership(resourceLoanTransfer.getOwnership());
		resource.setDeploymentLocation(resourceLoanTransfer.getDeploymentLocationId());
		resource.setTransferDate(resourceLoanTransfer.getTransferDate());
		// added new field EndTransferDate
		resource.setEndTransferDate(resourceLoanTransfer.getEndTransferDate());
		resource.getSkillResourcePrimaries();
		resource.getSkillResourceSecondaries();
		resource.getBgAdminAccessRightlist();
		resource.setDateOfJoining(resourceLoanTransfer.getDateOfJoining());
		resource.setConfirmationDate(resourceLoanTransfer.getConfirmationDate());
		resource.setLastAppraisal(resourceLoanTransfer.getLastAppraisal());
		resource.setPenultimateAppraisal(resourceLoanTransfer.getPenultimateAppraisal());
		resource.setReleaseDate(resourceLoanTransfer.getReleaseDate());
		resource.setbGHComments(resourceLoanTransfer.getbGHComments());
		resource.setbGHName(resourceLoanTransfer.getbGHName());
		resource.setbUHComments(resourceLoanTransfer.getbUHComments());
		resource.setbUHName(resourceLoanTransfer.getbUHName());
		resource.sethRComments(resourceLoanTransfer.gethRComments());
		resource.sethRName(resourceLoanTransfer.gethRName());
		resource.setbGCommentsTimestamp(resourceLoanTransfer.getbGCommentsTimestamp());
		resource.setbUCommentTimestamp(resourceLoanTransfer.getbUCommentsTimestamp());
		resource.sethRCommentTimestamp(resourceLoanTransfer.gethRCommentsTimestamp());
		resource.setEventId(resourceLoanTransfer.getEventId());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		//System.out.println(Second savetimestamp);
		resource.setCreationTimestamp(timestamp);
		if (resource.getUploadImage() != null) {
			System.out.println("--------ResourceLoanAndTransferDaoImpl populateResource method for employeeId------"+ resource.getYashEmpId() +" and image is"+ resource.getUploadImage().toString());
		}
	}
}
