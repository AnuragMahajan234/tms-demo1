package org.yash.rms.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.search.SearchCondition;
import org.apache.cxf.jaxrs.ext.search.SearchConditionVisitor;
import org.apache.cxf.jaxrs.ext.search.SearchContext;
import org.apache.cxf.jaxrs.ext.search.jpa.JPATypedQueryVisitor;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ProjectAllocationDao;
import org.yash.rms.dao.ResourceAllocationDao;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.domain.BGAdmin_Access_Rights;
import org.yash.rms.domain.BgBuView;
import org.yash.rms.domain.DefaultProject;
import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.SkillResourcePrimary;
import org.yash.rms.domain.SkillResourceSecondary;
import org.yash.rms.domain.Skills;
import org.yash.rms.domain.TimehrsView;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.exception.DaoRestException;
import org.yash.rms.helper.ResourceResumeHelper;
import org.yash.rms.rest.dao.generic.impl.HibernateGenericDao;
import org.yash.rms.rest.utils.ExceptionUtil;
import org.yash.rms.rest.utils.QueryObject;
import org.yash.rms.util.AppContext;
import org.yash.rms.util.ConcatenableIlikeCriterion;
import org.yash.rms.util.Constants;
import org.yash.rms.util.HibernateUtil;
import org.yash.rms.util.ResourceAllocationSearchCriteria;
import org.yash.rms.util.ResourceSearchCriteria;
import org.yash.rms.util.UserUtil;

@Repository("ResourceDao")
@Transactional
public class ResourceDaoImpl extends HibernateGenericDao<Integer, Resource> implements ResourceDao {

	
	public ResourceDaoImpl() {
		super(Resource.class);
	}
	
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
	@Qualifier("resumeUploadPath")
	private ResourceResumeHelper resourceHelper;
	
	@Autowired
	private ProjectAllocationDao projectAllocationDao;

	private static final Logger logger = LoggerFactory.getLogger(ResourceDaoImpl.class);

	 
	public boolean delete(int id) {
		return false;
	}

	 
	
	public boolean saveOrupdate(Resource resource) {
		logger.info("--------ResourceDaoImpl saveOrupdate method start-------");
		if (null == resource)
			return false;

		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess = true;

		if (resource.getCurrentProjectId() != null) {
			if ((resource.getCurrentProjectId().getId() == null) || (resource.getCurrentProjectId().getId().intValue() <= 0))
				resource.setCurrentProjectId(null);
		}
		if (resource.getCurrentReportingManager() != null) {
			if (resource.getCurrentReportingManager().getEmployeeId() < 0)
				resource.setCurrentReportingManager(null);
		}

		if (resource.getCurrentReportingManagerTwo() != null) {
			if (resource.getCurrentReportingManagerTwo().getEmployeeId() < 0)
				resource.setCurrentReportingManagerTwo(null);
		}

		if (resource.getUserRole() == null) {
			resource.setUserRole("ROLE_USER");
		}
		
		if (resource.getDeploymentLocation() != null && resource.getDeploymentLocation().getId() == 0) {
			resource.setDeploymentLocation(null);
		}

		if (resource.getEmployeeId() == null) {

			resource.setCreationTimestamp(new Date());
			resource.setLastUpdatedId(UserUtil.getCurrentResource().getUserName());
			resource.setCreatedId(UserUtil.getCurrentResource().getUserName());
			if ((resource.getUserName() == null || StringUtils.isEmpty(resource.getUserName())) && (resource.getEmailId() != null && StringUtils.isNotEmpty(resource.getEmailId()))) {
				resource.setUserName(StringUtils.substringBefore(resource.getEmailId(), "@"));
			}
			Set<SkillResourcePrimary> resPrimarySkills = resource.getSkillResourcePrimaries();
			if (resPrimarySkills != null && !resPrimarySkills.isEmpty())
				for (SkillResourcePrimary skillResPrimary : resPrimarySkills) {
					if (skillResPrimary.getSkillId() != null) {
						skillResPrimary.setResourceId(resource);
					} else {
						resource.getSkillResourcePrimaries().remove(skillResPrimary);

					}
				}
			Set<SkillResourceSecondary> resSecSkills = resource.getSkillResourceSecondaries();
			if (resSecSkills != null && !resSecSkills.isEmpty()) {
				for (SkillResourceSecondary skillResSecondry : resSecSkills) {
					if (skillResSecondry.getSkillId() != null) {
						skillResSecondry.setResourceId(resource);
					} else {
						resource.getSkillResourceSecondaries().remove(skillResSecondry);

					}
				}
			}
			Set<BGAdmin_Access_Rights> bgAdminAccessRightlist = resource.getBgAdminAccessRightlist();
			if (null != bgAdminAccessRightlist && !bgAdminAccessRightlist.isEmpty()) {
				for (BGAdmin_Access_Rights admin_Access_Rights : bgAdminAccessRightlist) {
					admin_Access_Rights.setResourceId(resource);
				}
			}

			if (resource.getUploadImage() != null) {
				System.out.println("In ResourceDaoImpl saveOrUpdate method -----for employeeId-"+resource.getYashEmpId() +" and image is-"+resource.getUploadImage().toString());
			}
		}

		UserContextDetails contextDetails = UserUtil.getCurrentResource();
		String userName = Constants.SYSTEM_UPDATE;
		if(contextDetails != null){
			userName  = contextDetails.getUserName();
		}
		
		if (resource.getEmployeeId() != null) {
			resource.setLastUpdatedId(userName);
			Resource res = findByEmployeeId(resource.getEmployeeId());
			// Added by Nitin
			if (resource.getUsername() != null && StringUtils.isNotEmpty(resource.getUserName())) {

			} else {
				resource.setUserName(res.getUserName());
			}

			// End Added by Nitin

			// Commented by Nitin
			// resource.setUserName(res.getUserName());
			resource.setLastupdatedTimestamp(new Date());
			Set<Skills> skillsSet = findAllPrimarySkillsByResource(resource);
			Set<Skills> secSkillSet = findAllSecondarySkillsByResource(resource);
			// geting primary skills
			Set<SkillResourcePrimary> resPrimarySkills = resource.getSkillResourcePrimaries();
			Set<Skills> skillsToDelete = new HashSet<Skills>();
			Set<Integer> resSkills = new HashSet<Integer>();
			Set<Integer> resAllSecSkills = new HashSet<Integer>();
			if (resPrimarySkills != null && !resPrimarySkills.isEmpty()) {

				Iterator<SkillResourcePrimary> skillResPrimary = resPrimarySkills.iterator();

				while (skillResPrimary.hasNext()) {
					SkillResourcePrimary resourcePrimarySkills = skillResPrimary.next();
					if (resourcePrimarySkills.getSkillId() != null && resourcePrimarySkills.getSkillId().getId() > 0) {

						resourcePrimarySkills.setResourceId(resource);

						resSkills.add(resourcePrimarySkills.getSkillId().getId());

					} else
						skillResPrimary.remove();
				}
			} else {
				
			}

			if (skillsSet != null && !skillsSet.isEmpty()) {
				if (resSkills != null && !resSkills.isEmpty()) {
					for (Skills skillsSets : skillsSet) {
						if (!resSkills.contains(skillsSets.getId().intValue())) {
							SkillResourcePrimary skillPrimRes = findResourcePrimarySkillsByskillId(resource, skillsSets);
							resource.getSkillResourcePrimaries().remove(skillPrimRes);
						}
					}
				}
			}

			Set<SkillResourceSecondary> resSecSkills = resource.getSkillResourceSecondaries();
			if (resSecSkills != null && !resSecSkills.isEmpty()) {
				Iterator<SkillResourceSecondary> skillResourceSecondary = resSecSkills.iterator();
				while (skillResourceSecondary.hasNext()) {
					SkillResourceSecondary secondarySkill = skillResourceSecondary.next();
					if (secondarySkill.getSkillId() != null)

					{
						secondarySkill.setResourceId(resource);
						resAllSecSkills.add(secondarySkill.getSkillId().getId());
					}
				}

			} else {

			}
			if (secSkillSet != null && !secSkillSet.isEmpty()) {
				if (!resAllSecSkills.isEmpty() && resAllSecSkills != null) {
					for (Skills secondraySkill : secSkillSet) {
						if (!resAllSecSkills.contains(secondraySkill.getId().intValue())) {
							SkillResourceSecondary secondarySkillRes = findResourceSecondarySkillsByskillId(resource, secondraySkill);
							currentSession.delete(secondarySkillRes);
						}
					}
				}
			}
			
			if (resource.getUploadResumeFileName() != null && !resource.getUploadResumeFileName().isEmpty()) {
				resource.setUploadResumeFileName(resource.getUploadResumeFileName());
			} else {
				resource.setUploadResumeFileName(res.getUploadTEFFileName());
			}
			
			if (resource.getUploadTEFFileName() != null && !resource.getUploadTEFFileName().isEmpty()) {
				resource.setUploadTEFFileName(resource.getUploadTEFFileName());
			} else {
				resource.setUploadTEFFileName(res.getUploadTEFFileName());
			}
			
			if(resource.getUploadResume()!=null && resource.getUploadResume().length>0) {
				resource.setUploadResume(resource.getUploadResume());
			} else {
				resource.setUploadResume(res.getUploadResume());
			}
			
			if(resource.getUploadTEF()!=null && resource.getUploadTEF().length>0) {
				resource.setUploadTEF(resource.getUploadTEF());
			} else {
				resource.setUploadTEF(res.getUploadTEF());
			}

		}
		// resource.setActualCapacity(40);
		// resource.setPlannedCapacity(40);
		if (resource.getResumeFileName() != null && resource.getFile() != null) {
			if (resource.getResumeFileName().equals("Select File") && resource.getFile().getSize() == 0) {
				resource.setResumeFileName(null);
			}
		}

		if (resource.getReleaseDate() != null) {
			resource.setUserName(resource.getUserName() + "_r");
		}
		
		if (resource.getEmployeeId() != null && resource.getFile() != null && resource.getFile().getSize() != 0)
			resourceHelper.uploadResume(resource.getEmployeeId(), resource.getResumeFileName(), resource.getFile().getBytes());
		try {
			if (resource.getEmployeeId() != null) {
				Resource mergedResource = (Resource) currentSession.merge(resource);
				if (null == mergedResource) {
					return false;
				}

			} else { // for new resource also add allocation For US3119
						// starts...
				Resource mergedResource = (Resource) currentSession.merge(resource);
				if (null == mergedResource) {
					return false;
				}
				DefaultProject defaultProject = null;
				Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(DefaultProject.class);

				defaultProject = (DefaultProject) criteria.add(Restrictions.eq("orgHierarchy", mergedResource.getBuId())).uniqueResult();
				if (defaultProject != null) { // /////////***********************************
					ResourceAllocation allocation = new ResourceAllocation();
						allocation.setEmployeeId(mergedResource);
						allocation.setAllocStartDate(mergedResource.getDateOfJoining());
						allocation.setBuId(defaultProject.getOrgHierarchy());
						allocation.setAllocationTypeId(defaultProject.getAllocationTypeId());
						allocation.setProjectId(defaultProject.getProjectId());
						allocation.setCurrentReportingManager(mergedResource.getCurrentReportingManager());
						allocation.setCurrentBuId(defaultProject.getOrgHierarchy());
						allocation.setCurProj(true);
						allocation.setAllocationSeq(1);
					mergedResource.setCurrentProjectId(defaultProject.getProjectId());
					Serializable mergedAllocation = currentSession.save(allocation);
					if (null == mergedAllocation) {
						return false;
					}
				}
			}// For US3119 ends...

		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception occured in saveOrupdate method at DAO layer:-" + e);
			throw e;
		} catch (RuntimeException ex) {
			isSuccess = false;
			ex.printStackTrace();
			logger.error("Exception occured in saveOrupdate method at DAO layer:-" + ex);
			throw ex;
		}
		logger.info("--------ResourceDaoImpl saveOrupdate method start-------");
		return isSuccess;
	}

	@SuppressWarnings("unchecked")
	public List<Resource> findAll() {
		logger.info("--------ResourceDaoImpl findAll method start-------");
		List<Resource> resourceList = new ArrayList<Resource>();
		Session session = (Session) getEntityManager().getDelegate();
		try {

			Query query = session.createQuery("FROM Resource");
			resourceList = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in findAll method at DAO layer:-" + e);
			throw e;
		}
		logger.info("--------ResourceDaoImpl findAll method end-------");
		return resourceList;
	}

	public List<Resource> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);
		Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return count;
	}



	public Resource findResourcesByEmailIdEquals(String emailId) {
		logger.info("--------ResourceDaoImpl findResourcesByEmailIdEquals method start-------");
		Resource resource = null;
		try {
			Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);
			criteria.add(Restrictions.eq("emailId", emailId));
			List<Resource> resourceList=criteria.list();
			if (resourceList != null && resourceList.size() > 0) {
				resource = (Resource) resourceList.get(0);
			}
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourcesByEmailIdEquals method at DAO layer:-" + ex);
			throw ex;
		}
		logger.info("--------ResourceDaoImpl findResourcesByEmailIdEquals method start-------");
		return resource;
	}

	
	public Resource findResourcesByYashEmpIdEquals(String yashEmpId){
		logger.info("--------ResourceDaoImpl findResourcesByYashEmpIdEquals method start-------");
		Resource resource = null;
		
		try {
			Criteria criteria = ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);
			resource = (Resource) criteria.add(Restrictions.eq("yashEmpId", yashEmpId)).uniqueResult();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourcesByYashEmpIdEquals method at DAO layer:-" + ex);
			throw ex;
		}
		logger.info("--------ResourceDaoImpl findResourcesByYashEmpIdEquals method end-------");

		return resource;
	}
	
	
	public Resource findResourcesByYashEmpIdEquals(String yashEmpId,SearchContext ctx){
		logger.info("--------ResourceDaoImpl findResourcesByYashEmpIdEquals method start-------");
		Resource resource = null;
		
		try {
			
			
			SearchCondition<Resource> sc=ctx.getCondition(Resource.class);
			JPATypedQueryVisitor<Resource> visitor = new JPATypedQueryVisitor<Resource>(
					getEntityManager(), Resource.class);
			sc.accept(visitor);
			visitor.visit(sc);
			TypedQuery<Resource> typedQuery  =visitor.getTypedQuery();
			//visitor.getQuery();
			//System.out.println("	visitor.getQuery();"+	visitor.getQuery());
			
		
			resource = typedQuery.getResultList().get(0);
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourcesByYashEmpIdEquals method at DAO layer:-" + ex);
			throw ex;
		}
		logger.info("--------ResourceDaoImpl findResourcesByYashEmpIdEquals method end-------");

		return resource;
	}

	public Character getTimesheetComment(Integer employeeId) {
		logger.info("--------ResourceDaoImpl getTimesheetComment method start-------");
		Character comment = null;
		try {
			Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);
			Resource resource = (Resource) criteria.add(Restrictions.eq("employeeId", employeeId)).uniqueResult();
			comment = resource.getTimesheetCommentOptional();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("EXPCEPTION OCCURRED IN GETTING TIMESHEET COMMENT : " + ex.getMessage());
			throw ex;
		}
		logger.info("--------ResourceDaoImpl getTimesheetComment method end-------");
		return comment;
	}

	public Resource getReportUserId(int id) {
		logger.info("--------ResourceDaoImpl find method start-------");
		try {
			Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);
			logger.info("--------ResourceDaoImpl find method end-------");
			return (Resource) criteria.add(Restrictions.eq("employeeId", id)).uniqueResult();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in find method at DAO layer:-" + ex);
			throw ex;
		}
	}

	public Resource findByEmployeeId(int id) {
		logger.info("--------ResourceDaoImpl find method start-------");
		try {
			

			Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);
			logger.info("--------ResourceDaoImpl find method end-------");
			return (Resource) criteria.add(Restrictions.eq("employeeId", id)).uniqueResult();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in find method at DAO layer:-" + ex);
			throw ex;
		}
	}
	
	public Resource findById(Integer id) {
		logger.info("--------ResourceDaoImpl findById method start-------");
		try {
				return super.findByPk(id);
			
			
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findById method at DAO layer:-" + ex);
			throw ex;
		} catch (DaoRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			logger.info("--------ResourceDaoImpl findById method end-------");
		}
		return null;
	}

	public Resource getCurrentResource(String userName) {
		logger.info("--------ResourceDaoImpl getCurrentResource method start-------");
		try {
			  
			Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);
			criteria.add(Restrictions.eq("userName", userName));
			logger.info("--------ResourceDaoImpl getCurrentResource method end-------");
			return (Resource) criteria.uniqueResult();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in getCurrentResource method at DAO layer:-" + ex);
			throw ex;
		}
	}

	public Object getCurrentResourceUserRole(Integer employeeId) {
		logger.info("--------ResourceDaoImpl getCurrentResourceUserRole method start-------");

		try {
			Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class).setProjection(Projections.property("userRole"));
			criteria.add(Restrictions.eq("employeeId", employeeId));
			logger.info("--------ResourceDaoImpl getCurrentResource method end-------");
			return criteria.uniqueResult();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in getCurrentResourceUserRole method at DAO layer:-" + ex);
			throw ex;
		}
	}

	public Set<Skills> findAllPrimarySkillsByResource(Resource resource) {
		logger.info("--------ResourceDaoImpl findAllPrimarySkillsByResource method start-------");
		try {
			Session session = (Session) getEntityManager().getDelegate();
			Set<Skills> resourcePrimarySkillsSet = new HashSet<Skills>();
			Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(SkillResourcePrimary.class);

			Query query = session.createQuery("select s.skillId from SkillResourcePrimary s where s.resourceId=:resource_id");
			query.setParameter("resource_id", resource);
			resourcePrimarySkillsSet.addAll(query.list());
			logger.info("--------ResourceDaoImpl findAllPrimarySkillsByResource method end-------");
			return resourcePrimarySkillsSet;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findAllPrimarySkillsByResource method at DAO layer:-" + ex);
			throw ex;
		}
	}

	public SkillResourcePrimary findResourcePrimarySkillsByskillId(Resource resource, Skills skill) {
		// TODO Auto-generated method stub
		logger.info("--------ResourceDaoImpl findResourcePrimarySkillsByskillId method start-------");
		try {

			Session session = (Session) getEntityManager().getDelegate();
			Query query = session.createQuery("from SkillResourcePrimary s where s.skillId=:skill_id and s.resourceId=:resource_id");
			query.setParameter("skill_id", skill);
			query.setParameter("resource_id", resource);
			SkillResourcePrimary primarySkill = (SkillResourcePrimary) query.list().get(0);
			logger.info("--------ResourceDaoImpl findResourcePrimarySkillsByskillId method end-------");
			return primarySkill;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourcePrimarySkillsByskillId method at DAO layer:-" + ex);
			throw ex;
		}
	}

	public Set<Skills> findAllSecondarySkillsByResource(Resource resource) {
		// TODO Auto-generated method stub
		logger.info("--------ResourceDaoImpl findAllSecondarySkillsByResource method start-------");
		try {

			Session session = (Session) getEntityManager().getDelegate();
			Set<Skills> resourceSecondarySkillsSet = new HashSet<Skills>();
			// Criteria criteria =
			//  ((Session) getEntityManager().getDelegate()).createCriteria(SkillResourcePrimary.class);
			// Set<SkillResourcePrimary>
			// resourcePrimarySkillsSet=(SkillResourcePrimary)criteria.add(Restrictions.eq("resourceId",
			// resource)).uniqueResult();
			Query query = session.createQuery("select s.skillId from SkillResourceSecondary s where s.resourceId=:resource_id");
			query.setParameter("resource_id", resource);
			resourceSecondarySkillsSet.addAll(query.list());
			logger.info("--------ResourceDaoImpl findAllSecondarySkillsByResource method end-------");
			return resourceSecondarySkillsSet;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findAllSecondarySkillsByResource method at DAO layer:-" + ex);
			throw ex;
		}
	}

	public SkillResourceSecondary findResourceSecondarySkillsByskillId(Resource resource, Skills skill) {
		// TODO Auto-generated method stub
		logger.info("--------ResourceDaoImpl findResourceSecondarySkillsByskillId method start-------");
		try {

			Session session = (Session) getEntityManager().getDelegate();
			Query query = session.createQuery("from SkillResourceSecondary s where s.skillId=:skill_id and s.resourceId=:resource_id");
			query.setParameter("skill_id", skill);
			query.setParameter("resource_id", resource);
			SkillResourceSecondary secondarySkill = (SkillResourceSecondary) query.list().get(0);
			logger.info("--------ResourceDaoImpl findResourceSecondarySkillsByskillId method end-------");
			return secondarySkill;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourceSecondarySkillsByskillId method at DAO layer:-" + ex);
			throw ex;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Integer> findResourceIdByRM2RM1(Integer employeeId) {
		// TODO Auto-generated method stub
		logger.info("--------ResourceDaoImpl findResourceIdByRM2RM1 method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		List<Integer> resourcesIds = new ArrayList<Integer>();
		try {
			Criteria criteria = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
			criteria.add(Restrictions.disjunction().add(Restrictions.eq("currentReportingManagerTwo.employeeId", employeeId)).add(Restrictions.eq("currentReportingManager.employeeId", employeeId)));
			criteria.add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));
			resourcesIds = criteria.list();

		} catch (HibernateException hibernateException) {
			logger.error("Exception occured in findResourceIdByRM2RM1 method at DAO layer:-" + hibernateException);
			throw hibernateException;
		}
		logger.info("--------ResourceDaoImpl findResourceIdByRM2RM1 method start-------");
		return resourcesIds;

	}

	@SuppressWarnings("unchecked")
	public List<Integer> findActiveResourceIdByRM2RM1(Integer employeeId) {
		// TODO Auto-generated method stub
		logger.info("--------ResourceDaoImpl findResourceIdByRM2RM1 method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		List<Integer> resourcesIds = new ArrayList<Integer>();
		List<Integer> employeeIds = new ArrayList<Integer>();
		
		List<Integer> projectIds=projectAllocationDao.getProjectListByOffshoreAndDelManager(true);
	
		if(projectIds!=null && projectIds.size()>0){
		ResourceAllocationDao resourceAllocationDao=AppContext.getApplicationContext().getBean(ResourceAllocationDao.class);
		employeeIds=resourceAllocationDao.findAllocatedResourceEmployeeIdByProjectIds(projectIds, "active");
		}
		try {
			Criteria criteria = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
			if(employeeIds==null || employeeIds.isEmpty() || employeeIds.size()==0)
			{
			
			criteria.add(Restrictions.disjunction().add(Restrictions.eq("currentReportingManagerTwo.employeeId", employeeId)).add(Restrictions.eq("currentReportingManager.employeeId", employeeId)));
			}
			else
			{
				criteria.add(Restrictions.or(Restrictions.disjunction().add(Restrictions.eq("currentReportingManagerTwo.employeeId", employeeId)).add(Restrictions.eq("currentReportingManager.employeeId", employeeId)),Restrictions.in("employeeId",employeeIds)));
				
			}
			criteria.add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));
			
			resourcesIds = criteria.list();
			if(!resourcesIds.isEmpty())
			{
			List<Integer> finalList = new ArrayList<Integer>();
			Resource resource = new Resource();
			resource.setEmployeeId(employeeId);
			List<Resource> resources = findResourcesByRM1RM2(resource, null, null, null, true);
			for (Resource resource1 : resources) {
				for (Integer id : resourcesIds) {
					if (resource1.getEmployeeId().equals(id)) {
						finalList.add(id);
					}
				}
			}
			resourcesIds = finalList;
			}
		} catch (HibernateException hibernateException) {
			logger.error("Exception occured in findResourceIdByRM2RM1 method at DAO layer:-" + hibernateException);
			throw hibernateException;
		}
		logger.info("--------ResourceDaoImpl findResourceIdByRM2RM1 method start-------");
		return resourcesIds;

	}

	public List<Resource> findResourcesByCurrentReportingManagerTwo(Resource currentReportingManagerTwo, String activeOrAll) {
		logger.info("--------ResourceDaoImpl findResourcesByCurrentReportingManagerTwo method start-------");
		try {

			if (currentReportingManagerTwo == null)
				throw new IllegalArgumentException("The currentReportingManagerTwo argument is required");
			Session session = (Session) getEntityManager().getDelegate();
			Query query = null;
			if (activeOrAll.equalsIgnoreCase("all")) {
				query = session.createQuery("FROM Resource WHERE currentReportingManagerTwo = :currentReportingManagerTwo");
			} else {
				query = session.createQuery("FROM Resource WHERE currentReportingManagerTwo = :currentReportingManagerTwo and (releaseDate>=current_date() or releaseDate is null)");
			}

			query.setParameter("currentReportingManagerTwo", currentReportingManagerTwo);
			List<Resource> results = query.list();
			logger.info("--------ResourceDaoImpl findResourcesByCurrentReportingManagerTwo method end-------");
			return results;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourcesByCurrentReportingManagerTwo method at DAO layer:-" + ex);
			throw ex;
		}
	}

	public List<Resource> findResourceEntries(int firstResult, int maxResults, boolean active) {
		logger.info("--------ResourceDaoImpl findResourceEntries method start-------");
		return findResourcesEntries(firstResult, maxResults, null, active);
	}

	// internally called by other methods
	public List<Resource> findResourcesEntries(int firstResult, int maxResults, ResourceSearchCriteria resourceSearchCriteria, boolean active) {
		try {
			logger.info("--------ResourceDaoImpl findResourcesEntries method start-------");

			Session session = (Session) getEntityManager().getDelegate();
			Criteria criteria = session.createCriteria(Resource.class);
			if (!active)
				criteria.setFirstResult(firstResult).setMaxResults(maxResults);
			else
				criteria.add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate"))).setFirstResult(firstResult).setMaxResults(maxResults);

			tableSort(resourceSearchCriteria, criteria);
			logger.info("--------ResourceDaoImpl findResourcesEntries method end-------");
			return criteria.list();
			
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourceEntries method at DAO layer:-" + ex);
			throw ex;
		}
	}

	// method called when sorting has to be done on datatable without entering
	// any value in search box
	protected void tableSort(ResourceSearchCriteria resourceSearchCriteria, Criteria criteria) {

		// check whether it is reference column or not
		if (resourceSearchCriteria.getColandTableNameMap().get(resourceSearchCriteria.getRefColName()) != null) {

			// check for current reporting manager
			if (resourceSearchCriteria.getBaseTableCol().equals("CM")) {
				resourceSearchCriteria.setOrederBy("userName");
				if (resourceSearchCriteria.getRefColName().equals("currentReportingManager"))
					criteria.createAlias("currentReportingManager", "table");
				else
					criteria.createAlias("currentReportingManagerTwo", "table");
			} else {
				criteria.createAlias(resourceSearchCriteria.getBaseTableCol(), // return
																				// reference
																				// column
																				// name
						"table");
			}

			sortCriteria(resourceSearchCriteria, criteria);
		} else {
			if (resourceSearchCriteria.getRefColName().equals("employeeName")) {
				if (resourceSearchCriteria.getOrderStyle().equals("asc")) {
					criteria.addOrder(Order.asc("userName"));
				} else {
					criteria.addOrder(Order.desc("userName"));
				}
			} else {
				if (resourceSearchCriteria.getOrderStyle().equals("asc")) {
					criteria.addOrder(Order.asc(resourceSearchCriteria.getOrederBy()));
				} else {
					criteria.addOrder(Order.desc(resourceSearchCriteria.getOrederBy()));
				}
			}
		}
	}

	// method called when sorting is required on searched result
	protected void applySort(ResourceSearchCriteria resourceSearchCriteria, Criteria criteria) {

		// check whether it is reference column or not
		if (resourceSearchCriteria.getColandTableNameMap().get(resourceSearchCriteria.getOrederBy()) != null) {
			// check for current reporting manager

			criteria.createAlias(resourceSearchCriteria.getTableColumnForSort(), "table1");

			if (resourceSearchCriteria.getOrderStyle().equals("asc")) {
				criteria.addOrder(Order.asc("table1." + resourceSearchCriteria.getOrederBy()));
			} else {
				criteria.addOrder(Order.desc("table1." + resourceSearchCriteria.getOrederBy()));
			}

		} else {
			// check for user name
			if (resourceSearchCriteria.getRefColName().equals("employeeName")) {
				if (resourceSearchCriteria.getOrderStyle().equals("asc")) {
					criteria.addOrder(Order.asc("userName"));
				} else {
					criteria.addOrder(Order.desc("userName"));
				}
			} else {
				if (resourceSearchCriteria.getOrderStyle().equals("asc")) {
					if (resourceSearchCriteria.getOrederBy().equals("employeeName")) {
						criteria.addOrder(Order.asc("userName"));
					} else {
						criteria.addOrder(Order.asc(resourceSearchCriteria.getOrederBy()));
					}
				}
					if (resourceSearchCriteria.getOrderStyle().equals("desc")) {
						if (resourceSearchCriteria.getOrederBy().equals("employeeName")) {
							criteria.addOrder(Order.desc("userName"));
						} else {
							criteria.addOrder(Order.desc(resourceSearchCriteria.getOrederBy()));
						}
					}
				
			}
		}
	}

	// called from resource controller when user role is ADMIN
	public List<Resource> findResourceEntriesPagination(int firstResult, int maxResults, ResourceSearchCriteria resourceSearchCriteria, boolean active) {
		logger.info("--------ResourceDaoImpl findResourceEntriesPagination method start-------");
		return findResourcesEntries(firstResult, maxResults, resourceSearchCriteria, active);
	}

	public List<Resource> findResourcesByCurrentReportingManager(Resource currentReportingManager, String activeOrAll) {
		logger.info("--------ResourceDaoImpl findResourcesByCurrentReportingManager method start-------");
		try {

			if (currentReportingManager == null)
				throw new IllegalArgumentException("The currentReportingManager argument is required");

			Session session = (Session) getEntityManager().getDelegate();
			Query query = null;
			if (activeOrAll.equalsIgnoreCase("active")) {
				query = session.createQuery("FROM Resource WHERE currentReportingManager = :currentReportingManager and (releaseDate >=current_date() or releaseDate is null)");
			} else {
				query = session.createQuery("FROM Resource WHERE currentReportingManager = :currentReportingManager");

			}
			query.setParameter("currentReportingManager", currentReportingManager);
			List<Resource> results = query.list();
			logger.info("--------ResourceDaoImpl findResourcesByCurrentReportingManager method end-------");
			return results;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourcesByCurrentReportingManager method at DAO layer:-" + ex);
			throw ex;
		}
	}

	/*public Resource findResource(Integer employeeId) {
		logger.info("--------ResourceDaoImpl findResource method start-------");
		try {

			if (employeeId == null)
				return null;
			Resource resource = (Resource) sessionFactory.getCurrentSession().createCriteria(Resource.class).add(Restrictions.eq("employeeId", employeeId)).uniqueResult();
			logger.info("--------ResourceDaoImpl findResource method end-------");
			return resource;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResource method at DAO layer:-" + ex);
			throw ex;
		}

	}*/

	public List<Resource> findResourcesByBusinessGroup(List<OrgHierarchy> businessGroup, boolean active, boolean isCurrentUserHr ) {
		
		logger.info("--------ResourceDaoImpl findResourcesByBusinessGroup method start-------");
		return findResourcesByBusinessGroups(businessGroup, null, null, null, null, active,isCurrentUserHr);

	}

	public List<Resource> findResourcesByBusinessGroups(List<OrgHierarchy> businessGroup, List<Integer> projectIdList, Integer page, Integer size, ResourceSearchCriteria resourceSearchCriteria,
			boolean active, boolean isCurrentUserHr) {

		logger.info("--------ResourceDaoImpl findResourcesByBusinessGroups method start-------");
		try {
			Session session = (Session) getEntityManager().getDelegate();
			Criteria criteria = session.createCriteria(Resource.class);
			List<Integer> employeeIds = new ArrayList<Integer>();
			Integer loggedInUser=0;
			if(UserUtil.getUserContextDetails()!=null)
			{
				loggedInUser=UserUtil.getUserContextDetails().getEmployeeId();
			}
			if (projectIdList != null && !projectIdList.isEmpty()) {
				Query query = session.createSQLQuery("Select employee_id from resource_allocation where project_id in (:projectIdList) AND (alloc_end_date IS NULL OR  alloc_end_date>=CURDATE())");
				query.setParameterList("projectIdList", projectIdList);
				employeeIds = query.list();
			}
			Criterion criterion1 = null;

			if (!active) {
				if (!employeeIds.isEmpty() && !businessGroup.isEmpty()) {
					criterion1 = Restrictions.or(Restrictions.or(Restrictions.in("currentBuId", businessGroup), 
												 Restrictions.in("employeeId", employeeIds)),
							Restrictions.or(Restrictions.eq("currentReportingManager.employeeId", loggedInUser),
															Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser)));
				} else if (!businessGroup.isEmpty()) {
					criterion1 = Restrictions.or(Restrictions.in("currentBuId", businessGroup),	Restrictions.or(Restrictions.eq("currentReportingManager.employeeId", loggedInUser),
							Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser)));
				} else if (!employeeIds.isEmpty()) {
					criterion1 = Restrictions.or(Restrictions.in("employeeId", employeeIds),	Restrictions.or(Restrictions.eq("currentReportingManager.employeeId", loggedInUser),
							Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser)));
				}
				if (criterion1 != null)
					criteria.add(criterion1);

			} else {
				if (!employeeIds.isEmpty() && !businessGroup.isEmpty()) {
					criterion1 = Restrictions.or(Restrictions.and(Restrictions.or(Restrictions.in("currentBuId", businessGroup), Restrictions.in("employeeId", employeeIds)),
							Restrictions.or(Restrictions.ge("releaseDate", new Date()), Restrictions.isNull("releaseDate"))),
							Restrictions.or(Restrictions.eq("currentReportingManager.employeeId", loggedInUser),
									Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser)));
				} else if (!businessGroup.isEmpty()) {
					criterion1 = Restrictions.or(Restrictions.and(Restrictions.in("currentBuId", businessGroup),
							Restrictions.or(Restrictions.ge("releaseDate", new Date()), Restrictions.isNull("releaseDate"))),
							Restrictions.or(Restrictions.eq("currentReportingManager.employeeId", loggedInUser),
									Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser)));
				} else if (!employeeIds.isEmpty()) {
					criterion1 = Restrictions.or(Restrictions.and(Restrictions.or(Restrictions.in("employeeId", employeeIds)),
							Restrictions.or(Restrictions.ge("releaseDate", new Date()), Restrictions.isNull("releaseDate"))),
							Restrictions.or(Restrictions.eq("currentReportingManager.employeeId", loggedInUser),
									Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser)));
				}
				if (criterion1 != null)
					criteria.add(criterion1);
				criteria.add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));

			}
			if (resourceSearchCriteria != null) {
				if (resourceSearchCriteria.getOrderStyle() != "" && resourceSearchCriteria.getOrederBy() != "") {

					tableSort(resourceSearchCriteria, criteria);
				}
			}

			if (page != null || size != null) {
				criteria.setFirstResult(page).setMaxResults(size);
			}
			logger.info("--------ResourceDaoImpl findResourcesByBusinessGroups method end-------");
			return criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourcesByBusinessGroups method at DAO layer:-" + ex);
			throw ex;
		}
	}

	// called from resource controller when user role is BG_ADMIN
	public List<Resource> findResourcesByBusinessGroupsPagination(List<OrgHierarchy> businessGroup, List<Integer> projectNameList, Integer page, Integer size,
			ResourceSearchCriteria resourceSearchCriteria, boolean active, boolean isCurrentUserHr) {
		return findResourcesByBusinessGroups(businessGroup, projectNameList, page, size, resourceSearchCriteria, active, isCurrentUserHr);
	}

	public List<OrgHierarchy> getBUListForBGADMIN(Resource resourceIds) {
		// TODO Auto-generated method stub
		logger.info("--------ResourceDaoImpl getBUListForBGADMIN method start-------");
		try {

			Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(BGAdmin_Access_Rights.class);
			criteria.add(Restrictions.eq("resourceId", resourceIds));
			List<OrgHierarchy> buAccessList = new ArrayList<OrgHierarchy>();
			List<BGAdmin_Access_Rights> list = criteria.list();
			for (BGAdmin_Access_Rights accessRight : list) {
				buAccessList.add(accessRight.getOrgId());
			}
			logger.info("--------ResourceDaoImpl getBUListForBGADMIN method end-------");
			return buAccessList;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in getBUListForBGADMIN method at DAO layer:-" + ex);
			throw ex;
		}
	}

	public List<Integer> findReourceIdsByBusinessGroup(List<OrgHierarchy> businessGroup) {
		// TODO Auto-generated method stub
		logger.info("--------ResourceDaoImpl findReourceIdsByBusinessGroup method start-------");
		Integer loggedInUser=0;
		if(UserUtil.getUserContextDetails()!=null)
		{
			loggedInUser=UserUtil.getUserContextDetails().getEmployeeId();
		}
		Session session = (Session) getEntityManager().getDelegate();
		List<Integer> resourcesIds = new ArrayList<Integer>();
		try {
			Criteria criteria = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
			if (businessGroup != null && !businessGroup.isEmpty()) {
				criteria.add(Restrictions.or(Restrictions.in("currentBuId", businessGroup),
						Restrictions.or(Restrictions.eq("currentReportingManager.employeeId", loggedInUser),
								Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser))
						
						));
			}
			resourcesIds = criteria.list();

		} catch (HibernateException hibernateException) {
			logger.error("Exception occured in findReourceIdsByBusinessGroup method at DAO layer:-" + hibernateException);
			throw hibernateException;
		}
		logger.info("--------ResourceDaoImpl findReourceIdsByBusinessGroup method end-------");
		return resourcesIds;
	}

	public List<Integer> findReourceIdsByBusinessGroupPagination(ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, List<Integer> projectIdList, List<OrgHierarchy> businessGroup,
			int firstResult, int maxResults, boolean activeOrAll, boolean search, boolean isCurrentUserHr) {
		// TODO Auto-generated method stub
		logger.info("--------ResourceDaoImpl findReourceIdsByBusinessGroupPagination method start-------");

		Session session = (Session) getEntityManager().getDelegate();
		ResourceSearchCriteria resourceSearchCriteria = new ResourceSearchCriteria();
		resourceSearchCriteria.setRefTableName(resourceAllocationSearchCriteria.getRefTableName());
		resourceSearchCriteria.setRefColName(resourceAllocationSearchCriteria.getRefColName());
		resourceSearchCriteria.setSortTableName(resourceAllocationSearchCriteria.getSortTableName());
		resourceSearchCriteria.setOrderStyle(resourceAllocationSearchCriteria.getOrderStyle());
		resourceSearchCriteria.setOrederBy(resourceAllocationSearchCriteria.getOrederBy());

		List<Integer> resourcesIds = new ArrayList<Integer>();
		List<Integer> employeeIds = new ArrayList<Integer>();
		Criterion criterion1 = null;
		Integer loggedInUser=0;
		if(UserUtil.getUserContextDetails()!=null)
		{
			loggedInUser=UserUtil.getUserContextDetails().getEmployeeId();
		}
		try {
			if (resourceAllocationSearchCriteria.getOrederBy() == "totalPlannedHrs" || resourceAllocationSearchCriteria.getOrederBy() == "totalReportedHrs"
					|| resourceAllocationSearchCriteria.getOrederBy() == "totalBilledHrs") {
				Criteria timeHrscriteria = session.createCriteria(TimehrsView.class).setProjection(Projections.property("employeeId"));
				timeHrscriteria.setFirstResult(firstResult).setMaxResults(maxResults);
				if (resourceAllocationSearchCriteria.getOrderStyle().equals("asc")) {
					timeHrscriteria.addOrder(Order.asc(resourceAllocationSearchCriteria.getOrederBy()));
				} else {
					timeHrscriteria.addOrder(Order.desc(resourceAllocationSearchCriteria.getOrederBy()));
				}
				resourcesIds = timeHrscriteria.list();
			} else {
				Criteria criteria = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
				if (projectIdList != null && !projectIdList.isEmpty()) {
					Query query = session
							.createSQLQuery("Select employee_id from resource_allocation where project_id in (:projectIdList) and (alloc_end_date is null or alloc_end_date>=CURRENT_DATE)");
					query.setParameterList("projectIdList", projectIdList);
					employeeIds = query.list();
				}
				if (!employeeIds.isEmpty() && businessGroup != null && !businessGroup.isEmpty()) {
					criterion1 = Restrictions.or(Restrictions.in("currentBuId", businessGroup), Restrictions.in("employeeId", employeeIds),Restrictions.or(Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser),Restrictions.eq("currentReportingManager.employeeId", loggedInUser)));
				} else if (businessGroup != null && !businessGroup.isEmpty()) {
					criterion1 = Restrictions.or(Restrictions.in("currentBuId", businessGroup),
							Restrictions.or(Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser),Restrictions.eq("currentReportingManager.employeeId", loggedInUser)));
				} else if (!employeeIds.isEmpty()) {
					criterion1 = Restrictions.or(Restrictions.in("employeeId", employeeIds),
							Restrictions.or(Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser),Restrictions.eq("currentReportingManager.employeeId", loggedInUser)));
				}
				if (criterion1 != null) {
					criteria.add(Restrictions.disjunction().add(criterion1));
				}
				criteria.setFirstResult(firstResult).setMaxResults(maxResults);
				if (activeOrAll) {
					criteria.add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));
				}
				if (search) {
					if (resourceAllocationSearchCriteria.getColandTableNameMap().get(resourceAllocationSearchCriteria.getRefColName()) == null) {
						// check for user name
						if (resourceAllocationSearchCriteria.getRefColName().equals("employeeName")) {
							String searchInput = resourceAllocationSearchCriteria.getSearchValue();
							searchInput = searchInput.replace(" ", "%");
							criteria.add(ConcatenableIlikeCriterion.ilike(searchInput, MatchMode.ANYWHERE, "firstName", "middleName", "lastName"));
						} else {
							if (resourceAllocationSearchCriteria.getIntegerValue() != 0) {
								criteria.add(Restrictions.like(resourceAllocationSearchCriteria.getRefColName(), resourceAllocationSearchCriteria.getIntegerValue()));
							}

							else {
								criteria.add(Restrictions.like(resourceAllocationSearchCriteria.getRefColName(), "%" + resourceAllocationSearchCriteria.getSearchValue() + "%"));
							}
						}

						applySort(resourceSearchCriteria, criteria);
					} else {
						
						if (resourceAllocationSearchCriteria.getBaseTableCol().equals("currentBuId")) {
		    				
		    				List<OrgHierarchy> bgBuId = searchByBgBu(resourceAllocationSearchCriteria.getSearchValue());
							
							if (!bgBuId.isEmpty()) 
								criteria.add(Restrictions.in(resourceAllocationSearchCriteria.getBaseTableCol(), bgBuId));
							else
								return resourcesIds;
						} 
						else{
						criteria.createAlias(resourceSearchCriteria.getBaseTableCol(), "table");
						criteria.add(Restrictions.like("table." + resourceSearchCriteria.getRefColName(), "%" + resourceAllocationSearchCriteria.getSearchValue() + "%"));
						}
						if (resourceSearchCriteria.getRefColName().equals(resourceSearchCriteria.getOrederBy())) {
							sortCriteria(resourceSearchCriteria, criteria);
						} else {
							applySort(resourceSearchCriteria, criteria);
						}

					}

				} else {

					tableSort(resourceSearchCriteria, criteria);

					/*
					 * else
					 * criteria.setFirstResult(firstResult).setMaxResults(maxResults
					 * );
					 */

				}

				resourcesIds = criteria.list();
			}

		} catch (HibernateException hibernateException) {
			logger.error("Exception occured in findReourceIdsByBusinessGroupPagination method at DAO layer:-" + hibernateException);
			throw hibernateException;
		}
		logger.info("--------ResourceDaoImpl findReourceIdsByBusinessGroupPagination method end-------");
		return resourcesIds;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> findReourceIdsByBusinessGroupDashboard(List<OrgHierarchy> businessGroup) {
		// TODO Auto-generated method stub
		logger.info("--------ResourceDaoImpl findReourceIdsByBusinessGroupPagination method start-------");

		Session session = (Session) getEntityManager().getDelegate();

		List<Integer> resourcesIds = new ArrayList<Integer>();
		List<Integer> resourcesIdSProjects = new ArrayList<Integer>();
		List<Integer> projectIds = new ArrayList<Integer>();
		try {
			Criteria criteria = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
			if (businessGroup != null && !businessGroup.isEmpty()) {
				criteria.add(Restrictions.disjunction().add(Restrictions.in("currentBuId", businessGroup)));
			}
			criteria.add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));
			resourcesIds = criteria.list();

			Criteria projectCriteria = session.createCriteria(Project.class).setProjection(Projections.property("id"));
			if (businessGroup != null && !businessGroup.isEmpty()) {
				projectCriteria.add(Restrictions.disjunction().add(Restrictions.in("orgHierarchy", businessGroup)));
			}
			projectIds = projectCriteria.list();

			String ty = projectIds.toString();
			int index = ty.length() - 1;
			String ty1 = ty.substring(1, index);

			Query projects = session.createSQLQuery("SELECT employee_id FROM resource_allocation WHERE project_id IN (:projectIds) AND ((alloc_end_date >= NOW()) OR (alloc_end_date IS NULL))");
			projects.setParameter("projectIds", ty1);

			resourcesIdSProjects = projects.list();

			resourcesIds.addAll(resourcesIdSProjects);

		} catch (HibernateException hibernateException) {
			logger.error("Exception occured in findReourceIdsByBusinessGroupPagination method at DAO layer:-" + hibernateException);
			throw hibernateException;
		}
		logger.info("--------ResourceDaoImpl findReourceIdsByBusinessGroupPagination method end-------");
		return resourcesIds;
	}

	public List<Integer> findReourceIdsByRM1RM2Pagination(ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, Integer employeeId, int firstResult, int maxResults, boolean activeOrAll,
			boolean search) {
		// TODO Auto-generated method stub
		logger.info("--------ResourceDaoImpl findReourceIdsByRM1RM2Pagination method start-------");

		Session session = (Session) getEntityManager().getDelegate();
		ResourceSearchCriteria resourceSearchCriteria = new ResourceSearchCriteria();
		resourceSearchCriteria.setRefTableName(resourceAllocationSearchCriteria.getRefTableName());
		resourceSearchCriteria.setRefColName(resourceAllocationSearchCriteria.getRefColName());
		resourceSearchCriteria.setSortTableName(resourceAllocationSearchCriteria.getSortTableName());
		resourceSearchCriteria.setOrderStyle(resourceAllocationSearchCriteria.getOrderStyle());
		resourceSearchCriteria.setOrederBy(resourceAllocationSearchCriteria.getOrederBy());

		List<Integer> resourcesIds = new ArrayList<Integer>();
		try {

			if (resourceAllocationSearchCriteria.getOrederBy() == "totalPlannedHrs" || resourceAllocationSearchCriteria.getOrederBy() == "totalReportedHrs"
					|| resourceAllocationSearchCriteria.getOrederBy() == "totalBilledHrs") {
				Criteria timeHrscriteria = session.createCriteria(TimehrsView.class).setProjection(Projections.property("employeeId"));
				timeHrscriteria.setFirstResult(firstResult).setMaxResults(maxResults);
				if (resourceAllocationSearchCriteria.getOrderStyle().equals("asc")) {
					timeHrscriteria.addOrder(Order.asc(resourceAllocationSearchCriteria.getOrederBy()));
				} else {
					timeHrscriteria.addOrder(Order.desc(resourceAllocationSearchCriteria.getOrederBy()));
				}
				resourcesIds = timeHrscriteria.list();
			} else {
				Criteria criteria = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));

				criteria.setFirstResult(firstResult).setMaxResults(maxResults);
				criteria.add(Restrictions.disjunction().add(Restrictions.eq("currentReportingManagerTwo.employeeId", employeeId))
						.add(Restrictions.eq("currentReportingManager.employeeId", employeeId)));
				if (activeOrAll) {
					criteria.add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));
				}

				if (search) {
					if (resourceAllocationSearchCriteria.getColandTableNameMap().get(resourceAllocationSearchCriteria.getRefColName()) == null) {
						// check for user name
						if (resourceAllocationSearchCriteria.getRefColName().equals("employeeName")) {
							String searchInput = resourceAllocationSearchCriteria.getSearchValue();
							searchInput = searchInput.replace(" ", "%");
							criteria.add(ConcatenableIlikeCriterion.ilike(searchInput, MatchMode.ANYWHERE, "firstName", "middleName", "lastName"));
						} else {
							if (resourceAllocationSearchCriteria.getIntegerValue() != 0) {
								criteria.add(Restrictions.like(resourceAllocationSearchCriteria.getRefColName(), resourceAllocationSearchCriteria.getIntegerValue()));
							}

							else {
								criteria.add(Restrictions.like(resourceAllocationSearchCriteria.getRefColName(), "%" + resourceAllocationSearchCriteria.getSearchValue() + "%"));
							}
						}

						applySort(resourceSearchCriteria, criteria);
					} else {

						criteria.createAlias(resourceSearchCriteria.getBaseTableCol(), "table");
						criteria.add(Restrictions.like("table." + resourceSearchCriteria.getRefColName(), "%" + resourceAllocationSearchCriteria.getSearchValue() + "%"));

						if (resourceSearchCriteria.getRefColName().equals(resourceSearchCriteria.getOrederBy())) {
							sortCriteria(resourceSearchCriteria, criteria);
						} else {
							applySort(resourceSearchCriteria, criteria);
						}

					}

				} else {

					tableSort(resourceSearchCriteria, criteria);

					/*
					 * else
					 * criteria.setFirstResult(firstResult).setMaxResults(maxResults
					 * );
					 */

				}
				resourcesIds = criteria.list();
			}

		} catch (HibernateException hibernateException) {
			logger.error("Exception occured in findReourceIdsByRM1RM2Pagination method at DAO layer:-" + hibernateException);
			throw hibernateException;
		}
		logger.info("--------ResourceDaoImpl findReourceIdsByRM1RM2Pagination method end-------");
		return resourcesIds;
	}

	public List<Integer> findActiveReourceIdsByBusinessGroup(List<OrgHierarchy> businessGroup, List<Integer> projectIdList,String activeOrAll,boolean isCurrentUserHr) {
		// TODO Auto-generated method stub
		logger.info("--------ResourceDaoImpl findReourceIdsByBusinessGroup method start-------");

		Session session = (Session) getEntityManager().getDelegate();
		Integer loggedInUser=0;
		if(UserUtil.getUserContextDetails()!=null)
		{
			loggedInUser=UserUtil.getUserContextDetails().getEmployeeId();
		}
		List<Integer> resourcesIds = new ArrayList<Integer>();
		List<Integer> employeeIds = new ArrayList<Integer>();
		try {
			Criteria criteria = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
			Criterion criterion1 = null;
			if (projectIdList != null && !projectIdList.isEmpty()) {
				Query query = session.createSQLQuery("Select employee_id from resource_allocation where project_id in (:projectIdList) and (alloc_end_date is null or alloc_end_date>=CURRENT_DATE)");
				query.setParameterList("projectIdList", projectIdList);
				employeeIds = query.list();
			}
			if(activeOrAll!=null && activeOrAll.equalsIgnoreCase("active")){
				criteria.add(Restrictions.disjunction()
	    				.add(Restrictions.ge("releaseDate", new Date()))
	    				.add(Restrictions.isNull("releaseDate")));
	    	}
		
			
			if (!employeeIds.isEmpty() && businessGroup != null && !businessGroup.isEmpty()) {
				criterion1 = Restrictions.or(Restrictions.in("currentBuId", businessGroup), Restrictions.in("employeeId", employeeIds),Restrictions.or(Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser),Restrictions.eq("currentReportingManager.employeeId", loggedInUser)));
			} 
			
			 else if (businessGroup != null && !businessGroup.isEmpty()) {
				criterion1 = Restrictions.or(Restrictions.in("currentBuId", businessGroup),Restrictions.or(Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser),Restrictions.eq("currentReportingManager.employeeId", loggedInUser)));
			} else if (!employeeIds.isEmpty()) {
				criterion1 = Restrictions.or(Restrictions.in("employeeId", employeeIds),
						 Restrictions.in("employeeId", employeeIds),Restrictions.or(Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser),Restrictions.eq("currentReportingManager.employeeId", loggedInUser)));
			}
			if (criterion1 != null)
				criteria.add(Restrictions.disjunction().add(criterion1));
			//criteria.add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));
			resourcesIds = criteria.list();
		} catch (HibernateException hibernateException) {
			logger.error("Exception occured in findReourceIdsByBusinessGroup method at DAO layer:-" + hibernateException);
			throw hibernateException;
		}
		logger.info("--------ResourceDaoImpl findReourceIdsByBusinessGroup method end-------");
		return resourcesIds;
	}

	public boolean copyUserProfileToResource(Resource resource) {
		// TODO Auto-generated method stub
		logger.info("--------ResourceDaoImpl copyUserProfileToResource method start-------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess = true;
		try {
			currentSession.saveOrUpdate(resource);
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception occured in copyUserProfileToResource method at DAO layer:-" + e);

		}
		logger.info("--------ResourceDaoImpl copyUserProfileToResource method end-------");
		return isSuccess;

	}

	public byte[] viewUploadedResume(Integer employeeId, String resumeFileName) {
		logger.info("--------ResourceDaoImpl viewUploadedResume method -------");
		return resourceHelper.viewuploadedResume(employeeId, resumeFileName);

	}

	public List<Resource> findResourceByRM1RM2(Resource employeeId, boolean active) {
		logger.info("--------ResourceDaoImpl findResourceByRM1RM2 method start-------");
		return findResourcesByRM1RM2(employeeId, null, null, null, active);
	}

	public List<Resource> findResourcesByRM1RM2(Resource employeeId, Integer page, Integer size, ResourceSearchCriteria resourceSearchCriteria, boolean active) {
		logger.info("--------ResourceDaoImpl findResourcesByRM1RM2 method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		List<Resource> resources = new ArrayList<Resource>();
		List<Integer> employeeIdList=new ArrayList<Integer>();

		ResourceAllocationDao resourceAllocationDao=AppContext.getApplicationContext().getBean(ResourceAllocationDao.class);
		employeeIdList=resourceAllocationDao.findAllocatedEmployeeInProjectByCurrentUser(employeeId.getEmployeeId(),true);
		if(employeeIdList==null || employeeIdList.isEmpty())
		{
		employeeIdList.add(0);
		}
		try {
			Criteria criteria = session.createCriteria(Resource.class);
			if (!active)
			{
				criteria.add(Restrictions.disjunction().add(Restrictions.eq("currentReportingManagerTwo", employeeId)).add(Restrictions.eq("currentReportingManager", employeeId)).add(Restrictions.in("employeeId", employeeIdList)));
				
				//List<Integer> employeeId=resourceAllocationDao.findAllocatedEmployeeInProjectByCurrentUser(employeeId.getEmployeeId(),false);
			}
				else
				{
					
				criteria.add(Restrictions.disjunction().add(Restrictions.eq("currentReportingManagerTwo", employeeId)).add(Restrictions.eq("currentReportingManager", employeeId)).add(Restrictions.in("employeeId", employeeIdList))).add(
						Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));
			
				}
			// for resource sorting
			if (resourceSearchCriteria != null) {
				if (resourceSearchCriteria.getOrderStyle() != "" && resourceSearchCriteria.getOrederBy() != "") {

					tableSort(resourceSearchCriteria, criteria);
				}
			}

			if (page != null || size != null) {
				criteria.setFirstResult(page).setMaxResults(size);
			}

			resources = criteria.list();

		} catch (HibernateException hibernateException) {
			logger.error("HibernateException occured in findResourcesByRM1RM2 method at DAO layer:-" + hibernateException);
			throw hibernateException;
		}
		logger.info("--------ResourceDaoImpl findResourcesByRM1RM2 method end-------");
		return resources;
	}

	// called from resource controller when user role is DEL_MANAGER
	public List<Resource> findResourceByRM1RM2Pagination(Resource employeeId, Integer page, Integer size, ResourceSearchCriteria resourceSearchCriteria, boolean active) {
		logger.info("--------ResourceDaoImpl findResourceByRM1RM2Pagination method start-------");
		return findResourcesByRM1RM2(employeeId, page, size, resourceSearchCriteria, active);
	}

	// for searching records in database
	public List<Resource> searchResources(ResourceSearchCriteria resourceSearchCriteria, List<OrgHierarchy> businessGroup,Resource currentResource, List<Integer> projectIdList, Integer page, Integer size, boolean active, boolean isCurrentUserDelManager) {
		logger.info("--------ResourceDaoImpl searchResources method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(Resource.class);
		List<Resource> resources = new ArrayList<Resource>();
		Criterion c2=null;
		Criterion c3=null;
		// search code
		if (resourceSearchCriteria.getColandTableNameMap().get(resourceSearchCriteria.getRefColName()) == null) {
			// check for user name
			if (resourceSearchCriteria.getRefColName().equals("employeeName")) {
				String searchInput = resourceSearchCriteria.getSearchValue();
				searchInput = searchInput.replace(" ", "%");
				criteria.add(ConcatenableIlikeCriterion.ilike(searchInput, MatchMode.ANYWHERE, "firstName", "middleName", "lastName"));
			} else {
				if (resourceSearchCriteria.getIntegerValue() != 0) {
					criteria.add(Restrictions.like(resourceSearchCriteria.getRefColName(), resourceSearchCriteria.getIntegerValue()));
				}

				else {
					criteria.add(Restrictions.like(resourceSearchCriteria.getRefColName(), "%" + resourceSearchCriteria.getSearchValue() + "%"));
				}
			}

			applySort(resourceSearchCriteria, criteria);
		}

		else {
			if (resourceSearchCriteria.getBaseTableCol().equals("CM")) {

				criteria.createCriteria(resourceSearchCriteria.getRefColName(), "table").add(
						ConcatenableIlikeCriterion.ilike(resourceSearchCriteria.getSearchValue(), MatchMode.ANYWHERE, "firstName", "lastName"));
			} else {
				// if its CurrentBGBU or ParentBGBU, modify the search column as
				// name...

				if ((resourceSearchCriteria.getBaseTableCol().equals("currentBuId")) || (resourceSearchCriteria.getBaseTableCol().equals("buId"))) {
					List<OrgHierarchy> bgBuId = searchByBgBu(resourceSearchCriteria.getSearchValue());
					/*
					 * criteria.createAlias(resourceSearchCriteria.getBaseTableCol
					 * (), "table");
					 */
					if (!bgBuId.isEmpty()) {
						criteria.add(Restrictions.in(resourceSearchCriteria.getBaseTableCol(), bgBuId));
					} else {
						return resources;
					}
				} else {
					criteria.createAlias(resourceSearchCriteria.getBaseTableCol(), "table");
					criteria.add(Restrictions.like("table." + resourceSearchCriteria.getRefColName(), "%" + resourceSearchCriteria.getSearchValue() + "%"));
				}

			}

			if (resourceSearchCriteria.getRefColName().equals(resourceSearchCriteria.getOrederBy())) {
				sortCriteria(resourceSearchCriteria, criteria);
			} else {
				applySort(resourceSearchCriteria, criteria);
			}

		}
		
	
		List<Integer> employeeIds = new ArrayList<Integer>();
		if (projectIdList != null && !projectIdList.isEmpty()) {
			Query query = session.createSQLQuery("Select employee_id from resource_allocation where project_id in (:projectIdList)  AND (alloc_end_date IS NULL OR  alloc_end_date>=CURDATE())");
			query.setParameterList("projectIdList", projectIdList);
			employeeIds = query.list();
		}

		// for resourse drop down
		if (active) {
			criteria.add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));
		}

		// if else for different user role
		if(businessGroup != null &&  !employeeIds.isEmpty()){
					
			criteria.add(Restrictions.disjunction().add(Restrictions.in("currentBuId", businessGroup)).add(Restrictions.in("employeeId", employeeIds)).add(Restrictions.disjunction().add(Restrictions.eq("currentReportingManagerTwo", currentResource)).add(Restrictions.eq("currentReportingManager", currentResource))));
		}
		else if (businessGroup != null) {
			Criterion c1=(Criterion) criteria.add(Restrictions.disjunction().add(Restrictions.eq("currentReportingManagerTwo", currentResource)).add(Restrictions.eq("currentReportingManager", currentResource)));
			criteria.add(Restrictions.disjunction().add(Restrictions.in("currentBuId", businessGroup)).add(c1));
		}else if (currentResource != null) {
			 c2=(Criterion) Restrictions.disjunction().add(Restrictions.eq("currentReportingManagerTwo", currentResource)).add(Restrictions.eq("currentReportingManager", currentResource));
		}
		
		if(isCurrentUserDelManager)
		{
			List<Integer> employeeIdList=new ArrayList<Integer>();
			
			ResourceAllocationDao resourceAllocationDao=AppContext.getApplicationContext().getBean(ResourceAllocationDao.class);
			employeeIdList=resourceAllocationDao.findAllocatedEmployeeInProjectByCurrentUser(currentResource.getEmployeeId(),true);
			if(employeeIdList.isEmpty())
			{
				employeeIdList.add(0);
			}
			 c3= Restrictions.disjunction().add(Restrictions.in("employeeId", employeeIdList));
		}
		if(c2!=null&&c3!=null)
			criteria.add(Restrictions.disjunction().add(c2).add(c3));
		else if(c2!=null)
			criteria.add(c2);
			

		// for pagination
		if (page != null || size != null) {
			criteria.setFirstResult(page).setMaxResults(size);
		}

		// applySort(resourceSearchCriteria, criteria);
		resources = criteria.list();
		logger.info("--------ResourceDaoImpl searchResources method end-------");
		return resources;
	}

	private List<OrgHierarchy> searchByBgBu(String resourceSearchCriteria) {
		// TODO Auto-generated method stub
		Session session = (Session) getEntityManager().getDelegate();
		List<BgBuView> bgBuIdFromView = new ArrayList<BgBuView>();
		List<OrgHierarchy> bgBuIdList = new ArrayList<OrgHierarchy>();

		Criteria criteria = session.createCriteria(BgBuView.class);
		criteria.add(Restrictions.like("name", "%" + resourceSearchCriteria + "%"));
		bgBuIdFromView = criteria.list();

		if (bgBuIdFromView != null) {
			for (BgBuView bgBuView : bgBuIdFromView) {
				OrgHierarchy hierarchy = new OrgHierarchy();
				hierarchy.setId(bgBuView.getId());
				bgBuIdList.add(hierarchy);
			}
		}

		/*
		 * DetachedCriteria dc = DetachedCriteria.forClass(OrgHierarchy.class);
		 * dc.add(Restrictions.like("name", "%"+resourceSearchCriteria+"%"));
		 * dc.setProjection(Property.forName("id")); Criteria c =
		 * session.createCriteria(OrgHierarchy.class);
		 * 
		 * Criterion criterion = Restrictions.like("name",
		 * "%"+resourceSearchCriteria+"%"); Criterion
		 * criterion2=Property.forName("parentId").in(dc);
		 * c.add(Restrictions.or(criterion,criterion2));
		 * 
		 * bgBuId=c.list();
		 */

		return bgBuIdList;

	}

	protected void sortCriteria(ResourceSearchCriteria resourceSearchCriteria, Criteria criteria) {
		/*if (resourceSearchCriteria.getOrderStyle().equals("asc")) {
			criteria.addOrder(Order.asc("table." + resourceSearchCriteria.getOrederBy()));
		} else {
			criteria.addOrder(Order.desc("table." + resourceSearchCriteria.getOrederBy()));
		}*/
		if (resourceSearchCriteria.getOrderStyle().equals("asc")) {
			if (resourceSearchCriteria.getOrederBy().equals("employeeName")) {
				criteria.addOrder(Order.asc("table." + "userName"));
			} else {
				criteria.addOrder(Order.asc("table." + resourceSearchCriteria.getOrederBy()));
			}
		}
			if (resourceSearchCriteria.getOrderStyle().equals("desc")) {
				if (resourceSearchCriteria.getOrederBy().equals("employeeName")) {
					criteria.addOrder(Order.desc("table." + "userName"));
				} else {
					criteria.addOrder(Order.desc("table." + resourceSearchCriteria.getOrederBy()));
				}
			}
		
	
	}

	public Boolean authenticateResource(String userName, String password) {
		logger.info("--------ResourceDaoImpl authenticateResource method start-------");
		try {
			Session session = (Session) getEntityManager().getDelegate();
			SQLQuery authenticateQuery = session.createSQLQuery("select * from resource where user_name = :user_name and pass = :password");
			authenticateQuery.setParameter("user_name", userName);
			authenticateQuery.setParameter("password", password);
			logger.info("--------ResourceDaoImpl authenticateResource method end-------");
			return authenticateQuery.list().size() > 0;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in authenticateResource method at DAO layer:-" + ex);
			throw ex;
		}

	}

	public List<Resource> findActiveResources() {
		// TODO Auto-generated method stub
		List<Resource> res = new ArrayList<Resource>();
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(Resource.class);
		// criteria.add(Restrictions.ge("releaseDate", new Date()));
		criteria.add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));
		res = criteria.list();
		return res;
	}

	public Long countActive() {
		// TODO Auto-generated method stub
		Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);
		Long count = (Long) criteria.add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate"))).setProjection(Projections.rowCount())
				.uniqueResult();
		return count;

	}

	/*
	 * public static ConcatenableIlikeCriterion ilike(String value, MatchMode
	 * match, String... columns) { return new ConcatenableIlikeCriterion(value,
	 * match, columns); }
	 */

	public Long countResourceForBGADMIN(List<OrgHierarchy> businessGroup, List<Integer> projectIdList, boolean active, boolean isCurrentUserHr) {
		// TODO Auto-generated method stub
		logger.info("--------ResourceDaoImpl countResourceForBGADMIN method start-------");
		try {
			List<Integer> employeeIds = new ArrayList<Integer>();
			Session session = (Session) getEntityManager().getDelegate();
			Criteria criteria = session.createCriteria(Resource.class).setProjection(Projections.rowCount());
			
			Integer loggedInUser=0;
			if(UserUtil.getUserContextDetails()!=null)
			{
				loggedInUser=UserUtil.getUserContextDetails().getEmployeeId();
			}
			Long count = 0l;
			Criterion c1 = null;
			Criterion c2;
			if (projectIdList != null && !projectIdList.isEmpty()) {
				Query query = session.createSQLQuery("Select employee_id from resource_allocation where project_id in (:projectIdList)  AND (alloc_end_date IS NULL OR  alloc_end_date>=CURDATE())");
				query.setParameterList("projectIdList", projectIdList);
				employeeIds = query.list();
			}
			if (businessGroup != null) {
				if (!active) {
					if (!employeeIds.isEmpty() && !businessGroup.isEmpty()) {
						c1 = Restrictions.or(Restrictions.or(Restrictions.in("currentBuId", businessGroup), 
											 Restrictions.in("employeeId", employeeIds)),
								Restrictions.or(Restrictions.eq("currentReportingManager.employeeId", loggedInUser),
										Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser)))
								;
						count = (Long) criteria.add(c1).uniqueResult();
					} else if (!employeeIds.isEmpty()) {
						c1 = Restrictions.or(Restrictions.in("employeeId", employeeIds),Restrictions.or(Restrictions.eq("currentReportingManager.employeeId", loggedInUser),
								Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser)));
						count = (Long) criteria.add(c1).uniqueResult();
					} else if (!businessGroup.isEmpty()) {
						c1 = Restrictions.or(Restrictions.in("currentBuId", businessGroup),
								Restrictions.or(Restrictions.eq("currentReportingManager.employeeId", loggedInUser),
										Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser)));
						count = (Long) criteria.add(c1).uniqueResult();
					}

				} else {
					if (!employeeIds.isEmpty() && !businessGroup.isEmpty()) {
						c1 = Restrictions.or(Restrictions.and(Restrictions.or(Restrictions.in("currentBuId", businessGroup), Restrictions.in("employeeId", employeeIds)),Restrictions.or(Restrictions.ge("releaseDate", new Date()), Restrictions.isNull("releaseDate"))),
								Restrictions.or(Restrictions.eq("currentReportingManager.employeeId", loggedInUser),
										Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser)));
					} else if (!employeeIds.isEmpty()) {
						c1 = Restrictions.or(Restrictions.and(Restrictions.in("currentBuId", businessGroup),
								Restrictions.or(Restrictions.ge("releaseDate", new Date()), Restrictions.isNull("releaseDate"))),
								Restrictions.or(Restrictions.eq("currentReportingManager.employeeId", loggedInUser),
										Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser)));
						//c1 = Restrictions.in("employeeId", employeeIds);
					} else if (!businessGroup.isEmpty()) {
						c1 = Restrictions.in("currentBuId", businessGroup);
					}
					c2 = Restrictions.or(Restrictions.ge("releaseDate", new Date()), Restrictions.isNull("releaseDate"));
					if (c1 != null)
						criteria.add(c1);
					count = (Long) criteria.add(c2).uniqueResult();

				}
			}

			logger.info("--------ResourceDaoImpl countResourceForBGADMIN method end-------");
			return count;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in countResourceForBGADMIN method at DAO layer:-" + ex);
			throw ex;
		}
	}

	public Long countResourcesByRM1RM2(Resource employeeId, boolean active) {
		logger.info("--------ResourceDaoImpl countResourcesByRM1RM2 method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		List<Resource> resources = new ArrayList<Resource>();
		Long count = 0l;

		
		List<Integer> employeeIdList=new ArrayList<Integer>();
		
		ResourceAllocationDao resourceAllocationDao=AppContext.getApplicationContext().getBean(ResourceAllocationDao.class);
		employeeIdList=resourceAllocationDao.findAllocatedEmployeeInProjectByCurrentUser(employeeId.getEmployeeId(),true);
		if(employeeIdList.isEmpty())
		{
			employeeIdList.add(0);
		}
		try {
			Criteria criteria = session.createCriteria(Resource.class);
			if (!active)
			{
				criteria.add(Restrictions.disjunction().add(Restrictions.eq("currentReportingManagerTwo", employeeId)).add(Restrictions.eq("currentReportingManager", employeeId)).add(Restrictions.in("employeeId", employeeIdList)));
				count =(Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
				//List<Integer> employeeId=resourceAllocationDao.findAllocatedEmployeeInProjectByCurrentUser(employeeId.getEmployeeId(),false);
			}
				else
				{
					
				criteria.add(Restrictions.disjunction().add(Restrictions.eq("currentReportingManagerTwo", employeeId)).add(Restrictions.eq("currentReportingManager", employeeId)).add(Restrictions.in("employeeId", employeeIdList))).add(
						Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));
				count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
				}
	
		} catch (HibernateException hibernateException) {
			logger.error("HibernateException occured in countResourcesByRM1RM2 method at DAO layer:-" + hibernateException);
			throw hibernateException;
		}
		logger.info("--------ResourceDaoImpl countResourcesByRM1RM2 method end-------");
		return count;
	}

	public List<Integer> findActiveReourceIds() {
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
		// criteria.add(Restrictions.ge("releaseDate", new Date()));
		criteria.add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));

		return criteria.list();
	}

	public Long countReourceIdsByBusinessGroup(List<OrgHierarchy> businessGroup, boolean ActivrOrAll) {
		// TODO Auto-generated method stub
		logger.info("--------ResourceDaoImpl countReourceIdsByBusinessGroup method start-------");
		Long count = 0l;
		Session session = (Session) getEntityManager().getDelegate();
		List<Integer> resourcesIds = new ArrayList<Integer>();
		try {
			Criteria criteria = session.createCriteria(Resource.class);
			if (ActivrOrAll)
				count = (Long) criteria.add(Restrictions.disjunction().add(Restrictions.in("currentBuId", businessGroup))).add(Restrictions.ge("releaseDate", new Date()))
						.add(Restrictions.isNull("releaseDate")).setProjection(Projections.rowCount()).uniqueResult();
			else

				count = (Long) criteria.add(Restrictions.disjunction().add(Restrictions.in("currentBuId", businessGroup))).setProjection(Projections.rowCount()).uniqueResult();

		} catch (HibernateException hibernateException) {
			logger.error("Exception occured in countReourceIdsByBusinessGroup method at DAO layer:-" + hibernateException);
			throw hibernateException;
		}
		logger.info("--------ResourceDaoImpl countReourceIdsByBusinessGroup method end-------");
		return count;
	}

	
	public List<Object[]> simlateUserList(List<OrgHierarchy> businessGroup, Resource currenLoggedInUser,  boolean activeUserFlag) {
		// TODO Auto-generated method stub
		logger.info("--------ResourceDaoImpl findAll method start-------");
		List<Object[]> resourceList = new ArrayList<Object[]>();
		Session session = (Session) getEntityManager().getDelegate();
		try {

			// Query query = session.createQuery("FROM Resource");
			session = (Session) getEntityManager().getDelegate();
			Criteria criteria = session.createCriteria(Resource.class);
			if (businessGroup != null) {
				criteria.add(Restrictions.in("currentBuId", businessGroup));
			}
			if (currenLoggedInUser != null) {
				criteria.add(Restrictions.disjunction().add(Restrictions.eq("currentReportingManagerTwo", currenLoggedInUser)).add(Restrictions.eq("currentReportingManager", currenLoggedInUser)));
			}
			if(activeUserFlag)
				criteria.add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.property("employeeId"));
			projList.add(Projections.property("firstName"));
			projList.add(Projections.property("middleName"));
			projList.add(Projections.property("lastName"));
			projList.add(Projections.property("userName"));
			projList.add(Projections.property("yashEmpId"));
			criteria.setProjection(Projections.distinct(projList));
			resourceList = criteria.list();
			// resourceList = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in findAll method at DAO layer:-" + e);
			throw e;
		}
		logger.info("--------ResourceDaoImpl findAll method end-------");
		return resourceList;
	}

	public List<Resource> findResourcesByHRROLE() {

		logger.info("--------ResourceDaoImpl findResourcesByHRROLE method start-------");
		try {

			Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);

			criteria.add(Restrictions.eq("userRole", "ROLE_HR")).add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));

			logger.info("--------ResourceDaoImpl findResourcesByHRROLE method end-------");
			return criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourcesByHRROLE method at DAO layer:-" + ex);
			throw ex;
		}

	}

	public List<Resource> findResourcesByBGADMINROLE() {

		logger.info("--------ResourceDaoImpl findResourcesByBGADMINROLE method start-------");
		try {

			Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);

			criteria.add(Restrictions.eq("userRole", "ROLE_BG_ADMIN")).add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));

			logger.info("--------ResourceDaoImpl findResourcesByBGADMINROLE method end-------");
			return criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourcesByBGADMINROLE method at DAO layer:-" + ex);
			throw ex;
		}

	}

	public List<Resource> findResourcesByADMINROLE() {

		logger.info("--------ResourceDaoImpl findResourcesByADMINROLE method start-------");
		try {

			Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);

			criteria.add(Restrictions.eq("userRole", "ROLE_ADMIN")).add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));

			logger.info("--------ResourceDaoImpl findResourcesByADMINROLE method end-------");
			return criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourcesByADMINROLE method at DAO layer:-" + ex);
			throw ex;
		}

	}

	public List getEmailIds(String role, List buId) {
		String[] emailIds = null;
		Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class).setProjection(Projections.property("emailId"));
		if (role.equals("ADMIN")) {
			criteria.add(Restrictions.eq("userRole", "ROLE_ADMIN"));
		}
		if (role.equals("BG_ADMIN")) {
			Criteria criteria1 =  ((Session) getEntityManager().getDelegate()).createCriteria(BGAdmin_Access_Rights.class).setProjection(Projections.property("resourceId.id"));

			List list1 = criteria1.add(Restrictions.in("orgId.id", buId)).list();
			criteria.add(Restrictions.eq("userRole", "ROLE_BG_ADMIN")).add(Restrictions.in("employeeId", list1));
		}
		/*
		 * if(role.equals("BG_ADMIN")) {
		 * 
		 * criteria.add(Restrictions.eq("userRole",
		 * "ROLE_BG_ADMIN")).add(Restrictions.in("bgAdminAccessRightlist",
		 * buId)); }
		 */
		List<String> list = criteria.list();
		return list;
	}

	// for account manager drop down.
	public List<Resource> findAllAccountManager() {
		logger.info("--------ResourceDaoImpl findAllAccountManager method start-------");
		List<Resource> accountManagerList = new ArrayList<Resource>();
		Session session = (Session) getEntityManager().getDelegate();

		try {
			List<String> roles = new ArrayList<String>();
			roles.add("ROLE_ADMIN");
			roles.add("ROLE_BG_ADMIN");
			roles.add("ROLE_DEL_MANAGER");
			roles.add("ROLE_MANAGER");

			Criteria criteria = session.createCriteria(Resource.class).add(Restrictions.in("userRole", roles));
			accountManagerList = criteria.list();

		} catch (HibernateException hibernateException) {
			logger.error("Exception occured in findAllAccountManager method at DAO layer:-" + hibernateException);
			throw hibernateException;
		}

		logger.info("------------ResourceDaoImpl findAllAccountManager method end------------");
		return accountManagerList;
	}

	public List<String> findAllBUs() {
		logger.info("--------ResourceDaoImpl findAllBUs method start-------");
		List<String> bu = new ArrayList<String>();
		Session session = (Session) getEntityManager().getDelegate();

		Criteria criteria = session.createCriteria(BgBuView.class);

		// criteria.setProjection(Projections.property("id"));
		criteria.setProjection(Projections.property("name"));
		bu = criteria.list();
		logger.info("--------ResourceDaoImpl findAllBUs method end-------");
		return bu;
	}

	public List<String> findAllProjectsOfBU(String bu, String role) {
		logger.info("--------ResourceDaoImpl findAllProjectsOfBU method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		Date date = new Date(System.currentTimeMillis());
		/* Project Name list of active projects should be fetched: defect fixed */
		org.hibernate.Query projects = null;
		if (role.equalsIgnoreCase("ROLE_ADMIN")) {
			projects = session.createSQLQuery("SELECT project_Name FROM Project project INNER JOIN bu bu " + "ON project.bu_id = bu.id WHERE (project_end_date is null or project_end_date >= :date)");
			projects.setParameter("date", date);
		} else {
			projects = session.createSQLQuery("SELECT project_Name FROM Project project INNER JOIN bu bu "
					+ "ON project.bu_id = bu.id WHERE NAME = :bu and (project_end_date is null or project_end_date >= :date)");
			projects.setParameter("bu", bu);
			projects.setParameter("date", date);
		}

		logger.info("--------ResourceDaoImpl findAllProjectsOfBU method end-------");
		return projects.list();
	}

	public List<Resource> getEligibleResourcesForCopy(Date alocationStartDate, List<ResourceAllocation> allocations) {
		List<Resource> resources;
		logger.info("--------ResourceDaoImpl findResourcesByEmailIdEquals method start-------");
		List<Integer> empIds = new ArrayList<Integer>();
		try {
			for (ResourceAllocation resEmpId : allocations) {

				empIds.add(resEmpId.getEmployeeId().getEmployeeId());
			}
			Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);
			criteria.add(Restrictions.le("dateOfJoining", alocationStartDate));
			/*
			 * .add(Restrictions.isNull("releaseDate"))
			 * .add(Restrictions.ge("releaseDate", new Date()));
			 */
			criteria.add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));
			criteria.add(Restrictions.not(Restrictions.in("employeeId", empIds)));
			ProjectionList projection = Projections.projectionList();
			projection.add(Projections.property("employeeId"));
			projection.add(Projections.property("firstName"));
			projection.add(Projections.property("lastName"));
			projection.add(Projections.property("yashEmpId"));
			criteria.setProjection(projection);
			resources = criteria.list();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourcesByEmailIdEquals method at DAO layer:-" + ex);
			throw ex;
		}
		logger.info("--------ResourceDaoImpl findResourcesByEmailIdEquals method start-------");

		return resources;
	}

	// added by neha for rejoining issue - start
	public Resource findResourcesByEmailIdEqualsForValidVal(String emailId, int employeeId) {
		logger.info("--------ResourceDaoImpl findResourcesByEmailIdEqualsForValidVal method start-------");
		Resource resource = null;
		Resource resource2 = null;
		try {
			Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);

			if (employeeId > 0) { // for rejoinee because employeeId is greater
									// than 0
				criteria.add(Restrictions.eq("emailId", emailId)).add(Restrictions.eq("employeeId", employeeId));
			} else { // for new resource because employeeId is 0
				criteria.add(Restrictions.eq("emailId", emailId));
			}
			List<Resource> resourcelist=(List<Resource>) criteria.list();
			if (resourcelist != null && resourcelist.size() > 0) {				
				resource = (Resource) resourcelist.get(0);
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourcesByEmailIdEqualsForValidVal method at DAO layer:-" + ex);
			throw ex;
		}
		logger.info("--------ResourceDaoImpl findResourcesByEmailIdEqualsForValidVal method end-------");
		return resource;
	}

	// added by neha for rejoining issue - end

	public Set<BGAdmin_Access_Rights> getSavedPreferences(int employeeId) {
		Set<BGAdmin_Access_Rights> bgAdminAccessRightlist = null;
		List<BGAdmin_Access_Rights> bgAdminAccessRightlisttoConvert = null;
		Resource res = new Resource();
		res.setEmployeeId(employeeId);
		try {
			Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(BGAdmin_Access_Rights.class);

			bgAdminAccessRightlisttoConvert = criteria.add(Restrictions.eq("resourceId", res)).add(Restrictions.eq("status", 1)).list();

			bgAdminAccessRightlist = new HashSet<BGAdmin_Access_Rights>(bgAdminAccessRightlisttoConvert);
		} catch (HibernateException ex) {
			System.out.println("Exception is here...");
			ex.printStackTrace();
		}
		return bgAdminAccessRightlist;
	}

	public void savePreference(List<OrgHierarchy> list, int employeeId) {
		try {
			clearPreference(employeeId);
			Session currentSession = (Session) getEntityManager().getDelegate();
			Query query = currentSession.createQuery("UPDATE BGAdmin_Access_Rights SET status = :status WHERE org_id IN (:list) AND resource_id = :employeeId");
			query.setParameter("status", 1);
			query.setParameterList("list", list);
			query.setParameter("employeeId", employeeId);
			query.executeUpdate();
			System.out.println("Update Success");
		} catch (HibernateException ex) {
			ex.printStackTrace();
			System.out.println("Update Failure");
		}
	}

	public void clearPreference(int employeeId) {
		try {
			Session currentSession = (Session) getEntityManager().getDelegate();
			Query query = currentSession.createQuery("UPDATE BGAdmin_Access_Rights SET status = :status WHERE resource_id = :employeeId AND status = 1");
			query.setParameter("status", 0);
			query.setParameter("employeeId", employeeId);
			query.executeUpdate();
			// System.out.println("Update Success");
		} catch (HibernateException ex) {
			ex.printStackTrace();
			System.out.println("Update Failure");
		}
	}

	public Set<Location> getLocationListByBusinessGroup(List<Integer> buIds) {

		logger.info("--------ResourceDaoImpl getLocationListByBusinessGroup method start-------");
		
		Set<Location> locationList = new HashSet<Location>();
		Session session = (Session) getEntityManager().getDelegate();
		
		try {
			
			Query query = session.createSQLQuery("SELECT DISTINCT id,location FROM location l INNER JOIN resource r ON l.id = r.location_id WHERE r.bu_id IN (:ids) OR r.current_bu_id IN (:ids)");
				query.setParameterList("ids", buIds);
			
			List<Object[]> objectList = query.list();
			
			if (!objectList.isEmpty()) {
				for (Object[] obj : objectList) {
					
					Location location = new Location();
						location.setId((Integer) obj[0]);
						location.setLocation((String) obj[1]);
					
					locationList.add(location);
				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("EXCEPTION CAUSED IN GETTING OrgId By Id " + buIds + e.getMessage());
			throw e;
		} finally {
			// session.close();
		}
		logger.info("------ResourceDaoImpl getLocationListByBusinessGroup method end-----");
		return locationList;
	}

	/* sarang added */
	public List<Resource> findAllHeads() {
		
		logger.info("--------ResourceDaoImpl findAllHeads method start-------");
		
		List<Resource> resources = new ArrayList<Resource>();
		Session session = (Session) getEntityManager().getDelegate();

		String namedQuery = "SELECT * FROM resource WHERE grade_id IN(5,6,8,13,14)";
		
		SQLQuery query = session.createSQLQuery(namedQuery);
			query.addEntity(Resource.class);
			
		resources = query.list();
		logger.info("--------ResourceDaoImpl findAllHeads method end-------");

		return resources;
	}

	public List<String[]> getResourceNameById(List<Integer> employeeIds) {
		
		logger.info("--------ResourceDaoImpl getResourceNameById method start-------");
		
		List<String[]> resourceFullNameList = new ArrayList<String[]>();
		Session session = (Session) getEntityManager().getDelegate();
		
		try {
			Query query = session.createSQLQuery("SELECT first_name,middle_name,last_name,employee_id  FROM  resource r WHERE r.employee_id IN (:ids) ");
				query.setParameterList("ids", employeeIds);
			
			List<Object[]> objectList = query.list();
			
			if (!objectList.isEmpty()) {
				
				for (Object[] object : objectList) {
					
					StringBuilder fullName = new StringBuilder();
						fullName.append((String) object[0] + " ");
					
					if (null != object[1]) fullName.append((String) object[1] + " ");
					
					fullName.append((String) object[2]);
					
					String s1[] = { fullName.toString(), String.valueOf(object[3]) };
					
					resourceFullNameList.add(s1);
				}
			}
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			logger.error("EXCEPTION CAUSED IN GETTING OrgId By Id " + employeeIds + hibernateException.getMessage());
			throw hibernateException;
		} finally {
			// session.close();
		}
		
		logger.info("--------ResourceDaoImpl getResourceNameById method end-------");
		return resourceFullNameList;
	}

	public List<Integer> findReourceIdsOfOwnProject(Integer employeeId) {
		
		logger.info("--------ResourceDaoImpl findReourceIdsOfOwnProject method start-------");
		
		List<Integer> resourceEmployeeIds = new ArrayList<Integer>();
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createSQLQuery("SELECT employee_id FROM resource WHERE `current_project_id` IN(SELECT id FROM Project WHERE `offshore_del_mgr`=:employeeId or `delivery_mgr`=:employeeId1  )");
			query.setParameter("employeeId", employeeId);
			query.setParameter("employeeId1", employeeId);
		
		resourceEmployeeIds = query.list();
		
		logger.info("--------ResourceDaoImpl findAllHeads method end-------");
		return resourceEmployeeIds;
	}

	public List<Integer> findReourceIdsOfOwnProject(List<OrgHierarchy> buList, Integer employeeId) {
		
		logger.info("--------ResourceDaoImpl findReourceIdsOfOwnProject method start-------");
		
		List<Integer> resourceEmployeeIds = new ArrayList<Integer>();
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createSQLQuery("SELECT employee_id FROM resource WHERE `current_project_id` IN(SELECT id FROM Project WHERE `offshore_del_mgr`=:employeeId or`delivery_mgr`=:employeeId1 ) and current_bu_id not in(:buList)");
			query.setParameter("employeeId", employeeId);
			query.setParameter("employeeId1", employeeId);
			query.setParameterList("buList", buList);
		
		resourceEmployeeIds = query.list();
			
		logger.info("--------ResourceDaoImpl findReourceIdsOfOwnProject method end-------");
		return resourceEmployeeIds;
	}

	public List<Integer> findActiveReourceIdsOfOwnProject(List<OrgHierarchy> buList, Integer employeeId) {
		
		logger.info("--------ResourceDaoImpl findActiveReourceIdsOfOwnProject method start-------");
		List<Integer> resourceEmployeeIds = new ArrayList<Integer>();
		
		Session session = (Session) getEntityManager().getDelegate();
		
		Query query = session.createSQLQuery("SELECT employee_id FROM resource WHERE `current_project_id` "
				+ "IN(SELECT id FROM Project WHERE `offshore_del_mgr`=:employeeId or `delivery_mgr`=:employeeId1) and current_bu_id not in(:buList) and release_date IS NULL or release_date >= :date");
			query.setParameter("date", new Date());
			query.setParameter("employeeId", employeeId);
			query.setParameter("employeeId1", employeeId);
			query.setParameterList("buList", buList);
		
		resourceEmployeeIds = query.list();
		
		logger.info("--------ResourceDaoImpl findActiveReourceIdsOfOwnProject method end-------");
		return resourceEmployeeIds;
	}

	public Set<Location> getLocationListByBusinessGroupAndProjectIds(List<Integer> ids) {

		logger.info("--------ResourceDaoImpl getLocationListByBusinessGroup method start-------");

		Set<Location> locationList = new TreeSet<Location>(new Location.LocationNameComparator());

		Session session = (Session) getEntityManager().getDelegate();

		try {

			Query query = session
					.createSQLQuery("SELECT DISTINCT l.id, l.location FROM location l, resource r, project p WHERE r.location_id = l.id AND r.`current_project_id` = p.id AND (r.bu_id IN (:ids) OR r.current_bu_id IN (:ids) OR p.bu_id IN (:ids))");

			query.setParameterList("ids", ids);

			List<Object[]> objectList = query.list();

			if (!objectList.isEmpty()) {

				for (Object[] obj : objectList) {

					Location location2 = new Location();

					location2.setId((Integer) obj[0]);

					location2.setLocation((String) obj[1]);

					locationList.add(location2);
				}
			}
		} catch (HibernateException e) {

			e.printStackTrace();

			logger.error("EXCEPTION CAUSED IN GETTING OrgId By Id " + ids + e.getMessage());

			throw e;
		} finally {

			// session.close();
		}

		logger.info("------ResourceDaoImpl getLocationListByBusinessGroup method end-----");

		return locationList;
	}

	public List<Integer> findAllEmployeeIds() {
		logger.info("--------ResourceDaoImpl findAllEmployeeIds method start-------");
		List<Integer> empIdsList = new ArrayList<Integer>();
		Session session = (Session) getEntityManager().getDelegate();
		try {

			Query query = session.createQuery("SELECT employeeId FROM Resource");
			empIdsList = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in findAll method at DAO layer:-" + e);
			throw e;
		}
		logger.info("--------ResourceDaoImpl findAllEmployeeIds method end-------");
		return empIdsList;
	}
	
	public Set<Resource> findActiveReourceIdsByBusinessGroupAndProjectId(List<Integer> businessGroup, List<Integer> projectIdList,List<Integer> locationIdList) {
		
		logger.info("--------ResourceDaoImpl findActiveReourceIdsByBusinessGroupAndProjectId method start-------");

		Session session = (Session) getEntityManager().getDelegate();
		Set<Resource> resourcesIds = new TreeSet<Resource>();

		try {
			
			if (projectIdList != null && !projectIdList.isEmpty()) {
				Query query = session.createSQLQuery("SELECT DISTINCT r.first_name,r.last_name,r.employee_id, r.yash_emp_id FROM resource_allocation re, resource r WHERE r.employee_id = re.`employee_id` AND project_id IN (:projectIdList) ORDER BY r.first_name ");
				query.setParameterList("projectIdList", projectIdList);		
				
				
				List<Object[]> objectList = query.list();
				if (!objectList.isEmpty()) {
					for (Object[] obj : objectList) {
						Resource resource = new Resource();
						resource.setFirstName((String)obj[0]);
						resource.setLastName((String) obj[1]);
						resource.setEmployeeId((Integer)obj[2]);
						resource.setYashEmpId((String)obj[3]);
						resourcesIds.add(resource);
					}
				}
				
			}
		
		} catch (HibernateException hibernateException) {
			logger.error("Exception occured in findReourceIdsByBusinessGroup method at DAO layer:-" + hibernateException);
			throw hibernateException;
		}
		logger.info("--------ResourceDaoImpl findReourceIdsByBusinessGroup method end-------");
		return resourcesIds;
	}

	public List<Integer> findActiveReourceIdsByPojectId(
			List<Integer> projectIdList) {
		logger.info("--------ResourceDaoImpl findActiveReourceIdsByBusinessGroupAndProjectId method start-------");

		Session session = (Session) getEntityManager().getDelegate();
		List<Integer> resourcesIds = new ArrayList<Integer>();

		try {
			
			if (projectIdList != null && !projectIdList.isEmpty()) {
				Query query = session.createSQLQuery("SELECT DISTINCT r.employee_id FROM resource_allocation re, resource r WHERE r.employee_id = re.`employee_id` AND project_id IN (:projectIdList) ORDER BY r.first_name ");
				query.setParameterList("projectIdList", projectIdList);		
				resourcesIds = query.list();			
			}
		
		} catch (HibernateException hibernateException) {
			logger.error("Exception occured in findReourceIdsByBusinessGroup method at DAO layer:-" + hibernateException);
			throw hibernateException;
		}
		logger.info("--------ResourceDaoImpl findReourceIdsByBusinessGroup method end-------");
		return resourcesIds;
	}
	// Changes for Image issue of resource start
	public Resource findExistingResourcesByEmailId(String emailId) {
		logger.info("--------ResourceDaoImpl findExistingResourcesByEmailId method start-------");
		Resource resource = null;
		try {
			Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);
			criteria.add(Restrictions.eq("emailId", emailId));
			criteria.addOrder(Order.desc("lastupdatedTimestamp"));
			if (criteria.list() != null && criteria.list().size() > 0) {
				resource = (Resource) criteria.list().get(0);
			}
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findExistingResourcesByEmailId method at DAO layer:-" + ex);
			throw ex;
		}
		logger.info("--------ResourceDaoImpl findExistingResourcesByEmailId method start-------");
		return resource;
	}
	//end
	
	public List<String> findEmailById(ArrayList<Integer> al) {
		logger.info("--------ResourceDaoImpl find method start-------");
		try {
			Session session = (Session) getEntityManager().getDelegate();
			Query query = session.createSQLQuery("SELECT email_id from resource where employee_id IN (:al) ");
			query.setParameterList("al", al);		
			List<String> l = query.list();	
			/*Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);
			logger.info("--------ResourceDaoImpl find method end-------");
			List<String> l= (List<String>) criteria.add(Restrictions.in("employeeId", al)).list();*/
			return l;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in find method at DAO layer:-" + ex);
			throw ex;
		}
	}
	
	public void updateResumeOfResource(Resource resource)throws Exception {
		
		logger.info("--------ResourceDaoImpl updateResumeOfResource method start-------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		try {
			currentSession.saveOrUpdate(resource);
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in updateResumeOfResource method at DAO layer:-" + e);

		}
		logger.info("--------ResourceDaoImpl copyUserProfileToResource method end-------");
		
	}

	public List<Resource> findResourcesByDeliveryManagerROLE() {

		logger.info("--------ResourceDaoImpl findResourcesByDeliveryManagerROLE method start-------");
		try {

			Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);

			criteria.add(Restrictions.eq("userRole", "ROLE_DEL_MANAGER")).add(Restrictions.disjunction()
					.add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));

			logger.info("--------ResourceDaoImpl findResourcesByDeliveryManagerROLE method end-------");
			return criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourcesByDeliveryManagerROLE method at DAO layer:-" + ex);
			throw ex;
		}

	}

	public List<Resource> findResourcesByEmailIds(List<String> emailList) {
		logger.info("--------ResourceDaoImpl findResourcesByEmailIds method start-------");
		List<Resource> resourcesList = null;
		 Session session = (Session) getEntityManager().getDelegate();
		try {
			Query query = session.createQuery("SELECT res FROM Resource res where res.emailId in (:emailIds)");
			query.setParameterList("emailIds", emailList);
			logger.info("--------ResourceDaoImpl findResourcesByEmailIds method end-------");
			resourcesList = query.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourcesByEmailIds method at DAO layer:-" + ex);
			throw ex;
		}
		return resourcesList;
	}
	
	
	//Resource count here
	
		public Long getResourceCount() {
			logger.info("--------ResourceDaoImpl getResourceCount method start-------");
			Long count = 0L;
			try {
				Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);
				criteria.add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));
				criteria.setProjection(Projections.rowCount());
				count = (Long)criteria.uniqueResult();
			} catch (HibernateException exception) {
				exception.printStackTrace();
				logger.error("Exception occured in getResourceCount method at DAO layer:-" + exception);
				throw exception;

			}
			return count;
     }

		public List<Resource> findResourceByEmployeeIds(List<Integer> employeeIds) {
			logger.info("--------FindResourceByEmployeeIds method start-------");
			List<Resource> resources = null;
			if(employeeIds == null || employeeIds.isEmpty()){
				return resources;
			}
			try{
				Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);
				criteria.add(Restrictions.in("employeeId",employeeIds));
				return criteria.list();				
			}
			catch (HibernateException exception) {
				logger.error("HibernateException occured in FindResourceByEmployeeIds method at DAO layer:-" + exception.getMessage());
				exception.printStackTrace();
			}
			catch (Exception exception) {
				logger.error("Exception occured in FindResourceByEmployeeIds method at DAO layer:-" + exception.getMessage());
				exception.printStackTrace();
			}
			return resources;
		}
		
		public List<Resource> findAllResources(Integer lowerLimit, Integer upperLimit,SearchContext context) throws DaoRestException {
			try {
				return super.search(context, upperLimit, lowerLimit);
			}catch(Exception e) {
				logger.error("Error Inside  @class :"+this.getClass().getName()+" @Method :searchWithLimit"+e.getMessage());
				throw new DaoRestException(ExceptionUtil.generateExceptionCode("Dao","ResourceDao",e));
			}
			
			/*logger.info("--------ResourceDaoImpl findAllResources method start-------");
			List<Resource> resourceList = new ArrayList<Resource>();
			Session session = (Session) getEntityManager().getDelegate();

			try {
				int maxResult = upperLimit - lowerLimit;

				Criteria criteria = session.createCriteria(Resource.class);
				criteria.add(Restrictions.in("employeeId",208)).setFirstResult(lowerLimit).setMaxResults(maxResult);
				resourceList = criteria.list();

			} catch (HibernateException hibernateException) {
				logger.error("Exception occured in findAllResources method at DAO layer:-" + hibernateException);
				throw hibernateException;
			}

			logger.info("------------ResourceDaoImpl findAllResources method end------------"+resourceList.size());
			return resourceList;*/
		}

		public List<Resource> findResourceByROLE(final String role) {
			logger.info("--------ResourceDaoImpl findResourcesByHRROLE method start-------");
			try {
				Criteria criteria =  ((Session) getEntityManager().getDelegate()).createCriteria(Resource.class);
				criteria.add(Restrictions.eq("userRole", role)).add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date())).add(Restrictions.isNull("releaseDate")));
				logger.info("--------ResourceDaoImpl findResourcesByHRROLE method end-------");
				List<Resource> resources = criteria.list();
				if(resources != null)
					return resources;
				else
				return new ArrayList<Resource>();
			} catch (HibernateException ex) {
				ex.printStackTrace();
				logger.error("Exception occured in findResourcesByHRROLE method at DAO layer:-" + ex);
				throw ex;
			}
		}
}
