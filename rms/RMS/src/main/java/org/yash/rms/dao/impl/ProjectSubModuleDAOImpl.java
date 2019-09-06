package org.yash.rms.dao.impl;

import java.math.BigInteger;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ProjectSubModuleDAO;
import org.yash.rms.dao.ResourceAllocationDao;
import org.yash.rms.domain.BgBuView;
import org.yash.rms.domain.Customer;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.ProjectModule;
import org.yash.rms.domain.ProjectSubModule;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.util.ProjectSubModuleSearchCriteria;
import org.yash.rms.util.UserUtil;

@Repository("projectSubModuleDAO")
public class ProjectSubModuleDAOImpl implements ProjectSubModuleDAO {

	private static final Logger logger = LoggerFactory.getLogger(ProjectSubModuleDAOImpl.class);

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
	private ResourceAllocationDao resourceAllocDao;


	
	public List<Object[]> findActiveProjectSubModulesByModuleId(String moduleName,Integer resAllocId) {
		
		 Session session = (Session) getEntityManager().getDelegate();
		 ResourceAllocation resAlloc =  resourceAllocDao.findById(resAllocId);
			Integer projectId = resAlloc.getProjectId().getId();
		 
		 Query query = session.getNamedQuery("ProjectSubModule.findActiveSubmodulesByModuleAndProjectId");
		 	query.setParameter(0, moduleName);
		 	query.setParameter(1, projectId);
		 	List<Object[]> activeProjectSubModules = query.list(); 
		

		return activeProjectSubModules;
	}
	
	@Transactional(propagation = Propagation.MANDATORY)
	public List<ProjectSubModule> findAllProjectSubModuleByModuleId(Integer moduleId) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query = null;

		query = currentSession.createSQLQuery("SELECT * FROM project_sub_module WHERE module_id=:moduleId ORDER BY sub_module_name").addEntity(ProjectSubModule.class)
				.setParameter("moduleId", moduleId);
		List<ProjectSubModule> projectsubModule = (List<ProjectSubModule>) query.list();
		return projectsubModule;
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public List<ProjectSubModule> findProjectSubModuleByModuleIdAndStatus(Integer moduleId, String status) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query = null;
		query = currentSession
				.createSQLQuery(
						"SELECT psm.`id`, psm.`sub_module_name`,psm.`module_id`,psm.`active`,psm.`created_id`,psm.`creation_timestamp`,psm.`lastupdated_id`,psm.`lastupdated_timestamp` FROM project_sub_module psm WHERE psm.module_id=:moduleId and psm.active=:status ORDER BY psm.sub_module_name")
				.addEntity(ProjectSubModule.class).setParameter("moduleId", moduleId).setParameter("status", status);
		List<ProjectSubModule> projectSubModule = query.list();
		return projectSubModule;
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public ProjectSubModule findProjectSubModuleById(Integer id) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query = null;
		query = currentSession.createSQLQuery("SELECT * FROM project_sub_module WHERE id=:id").addEntity(ProjectSubModule.class).setParameter("id", id);
		ProjectSubModule projectSubModule = (ProjectSubModule) query.uniqueResult();
		
		
		
		return projectSubModule;
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public boolean delete(int id) {
		return false;
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public boolean saveOrupdate(ProjectSubModule projectSubModule) {
		if (null == projectSubModule)
			return false;

		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess = true;
		try {
			if (projectSubModule.getId() == null) {

				projectSubModule.setCreatedId(UserUtil.getUserContextDetails().getUserName());
				projectSubModule.setCreationTimestamp(new Date());

			}

			currentSession.saveOrUpdate(projectSubModule);

		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while saving Activity " + projectSubModule.getSubModuleName() + e.getMessage());
			throw e;
		} finally {
		}
		logger.info("------ProjectActivityDaoImpl saveOrUpate method end-----");
		return isSuccess;
	}

	public List<ProjectSubModule> findAll() {
		return null;
	}

	public List<ProjectSubModule> findByEntries(int firstResult, int sizeNo) {
		return null;
	}

	public long countTotal() {
		return 0;
	}


	public List<ProjectSubModule> findActiveModuleList(int page, int size, ProjectSubModuleSearchCriteria projectSubModuleSearchCriteria, String status, String sSearch) {

		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query = null;
		String searchInput = null;
		String fiterSubModuleQuery;
		if(status.equals("0"))
		  fiterSubModuleQuery="pm.`active` = 0||pm.`active` = 1";
		else
		  fiterSubModuleQuery="pm.`active` = 1";
		
		List<ProjectSubModule> projectSubModulesList = new ArrayList<ProjectSubModule>();
		List<Object[]> projectModuleList = new ArrayList<Object[]>();
		if (sSearch.length() > 0) {
	        if (projectSubModuleSearchCriteria.getColandTableNameMap()
	            .get(projectSubModuleSearchCriteria.getRefColName()) != null) {
	          if (projectSubModuleSearchCriteria.getRefColName().equals("moduleName")) {
	            searchInput = projectSubModuleSearchCriteria.getSearchValue();
	            searchInput = searchInput.replace(" ", "%");
	            query = currentSession.createSQLQuery(" SELECT pm.`id`, pm.`module_name`,p.`project_name`, b.`name`, c.`customer_name` FROM project_module pm  "
	                + " LEFT JOIN project p ON pm.project_id = p.id LEFT JOIN bu b ON b.`id` = p.`bu_id` LEFT JOIN `customer` c "
	                + " ON c.`id` = p.`customer_name_id` WHERE pm.`module_name` like '%"+searchInput +"%' And (p.`project_end_date` >= CURRENT_DATE OR p.`project_end_date` IS NULL)" + " AND ("+fiterSubModuleQuery+")  LIMIT "+ size + " OFFSET "+ page );
	          }
	          if (projectSubModuleSearchCriteria.getRefColName().equals("projectName")) {
                searchInput = projectSubModuleSearchCriteria.getSearchValue();
                searchInput = searchInput.replace(" ", "%");
                query = currentSession.createSQLQuery(" SELECT pm.`id`, pm.`module_name`,p.`project_name`, b.`name`, c.`customer_name` FROM project_module pm  "
                    + " LEFT JOIN project p ON pm.project_id = p.id LEFT JOIN bu b ON b.`id` = p.`bu_id` LEFT JOIN `customer` c "
                    + " ON c.`id` = p.`customer_name_id` WHERE p.`project_name` like '%"+searchInput +"%' And (p.`project_end_date` >= CURRENT_DATE OR p.`project_end_date` IS NULL)" + " AND ("+fiterSubModuleQuery+")  LIMIT "+ size + " OFFSET "+ page );
              }
	          
	          if (projectSubModuleSearchCriteria.getRefColName().equals("customerName")) {
                searchInput = projectSubModuleSearchCriteria.getSearchValue();
                searchInput = searchInput.replace(" ", "%");
                query = currentSession.createSQLQuery(" SELECT pm.`id`, pm.`module_name`,p.`project_name`, b.`name`, c.`customer_name` FROM project_module pm  "
                    + " LEFT JOIN project p ON pm.project_id = p.id LEFT JOIN bu b ON b.`id` = p.`bu_id` LEFT JOIN `customer` c "
                    + " ON c.`id` = p.`customer_name_id` WHERE c.`customer_name` like '%"+searchInput +"%' And (p.`project_end_date` >= CURRENT_DATE OR p.`project_end_date` IS NULL)" + " AND ("+fiterSubModuleQuery+")  LIMIT "+ size + " OFFSET "+ page );
              }
	          
	         if (projectSubModuleSearchCriteria.getBaseTableCol().equals("BuCode")) {
                  List<String> bgBuNames =
                      searchByBgBu(projectSubModuleSearchCriteria.getSearchValue());
                  if (!bgBuNames.isEmpty()){
                    query = currentSession.createSQLQuery(" SELECT pm.`id`, pm.`module_name`,p.`project_name`, b.`name`, c.`customer_name` FROM project_module pm  "
                        + " LEFT JOIN project p ON pm.project_id = p.id LEFT JOIN bu b ON b.`id` = p.`bu_id` LEFT JOIN `customer` c "
                        + " ON c.`id` = p.`customer_name_id` WHERE b.`name` in(:bgbu) And (p.`project_end_date` >= CURRENT_DATE OR p.`project_end_date` IS NULL)" + " AND ("+fiterSubModuleQuery+")  LIMIT "+ size + " OFFSET "+ page ).setParameterList("bgbu", bgBuNames);
                }
	          }
	        }
	        }
		else{
       
		  if (projectSubModuleSearchCriteria.getColandTableNameMap()
		        .get(projectSubModuleSearchCriteria.getRefColName()) != null) {

		      if (projectSubModuleSearchCriteria.getBaseTableCol().equals("moduleName")) {
		        if (projectSubModuleSearchCriteria.getRefColName().equals("moduleName")) {
		          String orderStyle=projectSubModuleSearchCriteria.getOrderStyle();
                  query = currentSession.createSQLQuery(" SELECT pm.`id`, pm.`module_name`,p.`project_name`, b.`name`, c.`customer_name` FROM project_module pm  "
                      + " LEFT JOIN project p ON pm.project_id = p.id LEFT JOIN bu b ON b.`id` = p.`bu_id` LEFT JOIN `customer` c "
                      + " ON c.`id` = p.`customer_name_id` WHERE (p.`project_end_date` >= CURRENT_DATE OR p.`project_end_date` IS NULL)" + " AND ("+fiterSubModuleQuery+")  ORDER BY pm.`module_name` "+orderStyle +" LIMIT "+ size + " OFFSET "+ page);
		        }
		        
		      }
		      
		      if (projectSubModuleSearchCriteria.getBaseTableCol().equals("projectName")) {
                if (projectSubModuleSearchCriteria.getRefColName().equals("projectName")) {
                  String orderStyle=projectSubModuleSearchCriteria.getOrderStyle();
                  query = currentSession.createSQLQuery(" SELECT pm.`id`, pm.`module_name`,p.`project_name`, b.`name`, c.`customer_name` FROM project_module pm  "
                      + " LEFT JOIN project p ON pm.project_id = p.id LEFT JOIN bu b ON b.`id` = p.`bu_id` LEFT JOIN `customer` c "
                      + " ON c.`id` = p.`customer_name_id` WHERE (p.`project_end_date` >= CURRENT_DATE OR p.`project_end_date` IS NULL)" + " AND ("+fiterSubModuleQuery+")  ORDER BY p.`project_name` "+orderStyle +" LIMIT "+ size + " OFFSET "+ page);
             }
            }
		      
		      if (projectSubModuleSearchCriteria.getBaseTableCol().equals("customerName")) {
                if (projectSubModuleSearchCriteria.getRefColName().equals("customerName")) {
                  String orderStyle=projectSubModuleSearchCriteria.getOrderStyle();
                  query = currentSession.createSQLQuery(" SELECT pm.`id`, pm.`module_name`,p.`project_name`, b.`name`, c.`customer_name` FROM project_module pm  "
                      + " LEFT JOIN project p ON pm.project_id = p.id LEFT JOIN bu b ON b.`id` = p.`bu_id` LEFT JOIN `customer` c "
                      + " ON c.`id` = p.`customer_name_id` WHERE (p.`project_end_date` >= CURRENT_DATE OR p.`project_end_date` IS NULL)" + " AND ("+fiterSubModuleQuery+")  ORDER BY c.`customer_name` "+orderStyle +" LIMIT "+ size + " OFFSET "+ page);
             }
            }
		    }
		  
		  else{
		    query = currentSession.createSQLQuery(" SELECT pm.`id`, pm.`module_name`,p.`project_name`, b.`name`, c.`customer_name` FROM project_module pm  "
	                + " LEFT JOIN project p ON pm.project_id = p.id LEFT JOIN bu b ON b.`id` = p.`bu_id` LEFT JOIN `customer` c "
	                + " ON c.`id` = p.`customer_name_id` WHERE (p.`project_end_date` >= CURRENT_DATE OR p.`project_end_date` IS NULL)" + " AND ("+fiterSubModuleQuery+")  LIMIT "+ size + " OFFSET "+ page );
	       }
		
         }
		
		if(query!=null)
		projectModuleList = query.list();

		if (!projectModuleList.isEmpty()) {

			for (Object[] row : projectModuleList) {

				ProjectSubModule projectSubModule = new ProjectSubModule();
				ProjectModule projectModule = new ProjectModule();
				Project project = new Project();
				Customer customer = new Customer();

				projectSubModule.setId((Integer) row[0]);
				projectModule.setModuleName((String) row[1]);

				project.setProjectName((String) row[2]);
				projectSubModule.setBgbu((String) row[3]);

				customer.setCustomerName((String) row[4]);

				project.setCustomerNameId(customer);

				projectModule.setProjectId(project);

				projectSubModule.setModule(projectModule);

				projectSubModulesList.add(projectSubModule);
			}
		}
		return projectSubModulesList;
	}

  public long totalCountOfsubModule(String status) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		String fiterSubModuleQuery;
		if(status.equals("0"))
          fiterSubModuleQuery="pm.`active` = 0||pm.`active` = 1";
        else
          fiterSubModuleQuery="pm.`active` = 1";
		BigInteger count = (BigInteger) currentSession.createSQLQuery("SELECT COUNT(*) FROM project_module pm  "
				+ " LEFT JOIN project p ON pm.project_id = p.id LEFT JOIN bu b ON b.`id` = p.`bu_id` LEFT JOIN `customer` c "
				+ " ON c.`id` = p.`customer_name_id` WHERE (p.`project_end_date` >= CURRENT_DATE OR p.`project_end_date` IS NULL)" + " AND ("+fiterSubModuleQuery+")").uniqueResult();
       return count.longValue();
	}
	
	private List<String> searchByBgBu(String projectAllocationSearchCriteria) {
	    Session session = (Session) getEntityManager().getDelegate();
	    List<BgBuView> bgBuIdFromView = new ArrayList<BgBuView>();
	    List<String> bgBuNames = new ArrayList<String>();
	    Criteria criteria = session.createCriteria(BgBuView.class);
	    criteria.add(Restrictions.like("name", "%" + projectAllocationSearchCriteria + "%"));
	    bgBuIdFromView = criteria.list();

	    if (bgBuIdFromView != null) {
	      for (BgBuView bgBuView : bgBuIdFromView) {
	         bgBuNames.add(bgBuView.getName());
	      }
	    }

	    return bgBuNames;
	  }

  public long totalCountOfSubModule(String status,
      ProjectSubModuleSearchCriteria projectSubModuleSearchCriteria, String sSearch) {
    Session currentSession = (Session) getEntityManager().getDelegate();
    String searchInput = null;
    BigInteger count = null;
    long totalCount = 0;
    String fiterSubModuleQuery;
    if(status.equals("0"))
      fiterSubModuleQuery="pm.`active` = 0||pm.`active` = 1";
    else
      fiterSubModuleQuery="pm.`active` = 1";
    if (sSearch.length() > 0) {
        if (projectSubModuleSearchCriteria.getColandTableNameMap()
            .get(projectSubModuleSearchCriteria.getRefColName()) != null) {
          if (projectSubModuleSearchCriteria.getRefColName().equals("moduleName")) {
            searchInput = projectSubModuleSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");
            count = (BigInteger) currentSession.createSQLQuery(" SELECT count(*) FROM project_module pm "
                + " LEFT JOIN project p ON pm.project_id = p.id LEFT JOIN bu b ON b.`id` = p.`bu_id` LEFT JOIN `customer` c "
                + " ON c.`id` = p.`customer_name_id` WHERE pm.`module_name` like '%"+searchInput +"%' And (p.`project_end_date` >= CURRENT_DATE OR p.`project_end_date` IS NULL)" + " AND ("+fiterSubModuleQuery+")").uniqueResult();
          }
          if (projectSubModuleSearchCriteria.getRefColName().equals("projectName")) {
            searchInput = projectSubModuleSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");
            count = (BigInteger) currentSession.createSQLQuery(" SELECT count(*) FROM project_module pm  "
                + " LEFT JOIN project p ON pm.project_id = p.id LEFT JOIN bu b ON b.`id` = p.`bu_id` LEFT JOIN `customer` c "
                + " ON c.`id` = p.`customer_name_id` WHERE p.`project_name` like '%"+searchInput +"%' And (p.`project_end_date` >= CURRENT_DATE OR p.`project_end_date` IS NULL)" + " AND ("+fiterSubModuleQuery+")").uniqueResult();
          }
          
          if (projectSubModuleSearchCriteria.getRefColName().equals("customerName")) {
            searchInput = projectSubModuleSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");
            count = (BigInteger) currentSession.createSQLQuery(" SELECT count(*) FROM project_module pm  "
                + " LEFT JOIN project p ON pm.project_id = p.id LEFT JOIN bu b ON b.`id` = p.`bu_id` LEFT JOIN `customer` c "
                + " ON c.`id` = p.`customer_name_id` WHERE c.`customer_name` like '%"+searchInput +"%' And (p.`project_end_date` >= CURRENT_DATE OR p.`project_end_date` IS NULL)" + " AND ("+fiterSubModuleQuery+")").uniqueResult();
          }
          
         if (projectSubModuleSearchCriteria.getBaseTableCol().equals("BuCode")) {
              List<String> bgBuNames =
                  searchByBgBu(projectSubModuleSearchCriteria.getSearchValue());
              if (!bgBuNames.isEmpty()){
                count = (BigInteger) currentSession.createSQLQuery(" SELECT count(*) FROM project_module pm  "
                    + " LEFT JOIN project p ON pm.project_id = p.id LEFT JOIN bu b ON b.`id` = p.`bu_id` LEFT JOIN `customer` c "
                    + " ON c.`id` = p.`customer_name_id` WHERE b.`name` in(:bgbu) And (p.`project_end_date` >= CURRENT_DATE OR p.`project_end_date` IS NULL)" + " AND ("+fiterSubModuleQuery+")").setParameterList("bgbu", bgBuNames).uniqueResult();
            }
          }
        }
     }
    
    if(count!=null)
    totalCount = count.longValue();
    
    return totalCount;
  }
  
  //fetches the active sub modules using :moduleName and :projectId
  public List<ProjectSubModule> findActiveProjectSubModulesByModuleNameAndProjectId(String moduleName,Integer projectId) throws Exception {

	  List<Object[]> activeProjectSubModules=null;
	  List<ProjectSubModule> subModuleList=new  ArrayList<ProjectSubModule>();
	  ProjectSubModule subModule = null;

	  try {
		  Session session = (Session) getEntityManager().getDelegate();

		  Query query = session.getNamedQuery("ProjectSubModule.findActiveSubmodulesByModuleAndProjectId");
		  query.setParameter(0, moduleName);
		  query.setParameter(1, projectId);

		  activeProjectSubModules = query.list(); 

		  for (Object[] objects : activeProjectSubModules) {

			  subModule = new ProjectSubModule();
			  subModule.setId((Integer)objects[0]);
			  subModule.setSubModuleName((String)objects[1]);

			  subModuleList.add(subModule);
		  }

	  } catch (Exception e) {
		  e.printStackTrace();
		  throw e;
	  }
	  return subModuleList;
  }
  
}
