/**
 * 
 */
package org.yash.rms.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ProjectAllocationDao;
import org.yash.rms.dao.ResourceAllocationDao;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.domain.BgBuView;
import org.yash.rms.domain.DMProjectVisibilityView;
import org.yash.rms.domain.DefaultProject;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.TimehrsEmployeeIdProjectView;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.util.AppContext;
import org.yash.rms.util.ConcatenableIlikeCriterion;
import org.yash.rms.util.Constants;
import org.yash.rms.util.HibernateUtil;
import org.yash.rms.util.ProjectAllocationSearchCriteria;
import org.yash.rms.util.UserUtil;

/**
 * @author sarita.todawat
 * @copyright Yash Technologies Pvt. Ltd.
 * @email sarita.todawat@yash.com
 * @date Mar 29, 2017
 * @purpose This class is used to handle all DB operations of the project allocation.
 */

@Repository("ProjectAllocationDao")
@Transactional
public class ProjectAllocationDaoImpl implements ProjectAllocationDao {
  private static final Logger logger = LoggerFactory.getLogger(ProjectAllocationDaoImpl.class);

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
  


  public boolean delete(int id) {
    return false;
  }

  public boolean saveOrupdate(Project project) {
    return false;
  }

  public List<Project> findAll() {
    logger.info("--------ProjectAllocationDaoImpl findAll method start-------");
    Session session = (Session) getEntityManager().getDelegate();
    List<Project> projects = new ArrayList<Project>();
    try {

      projects = session
          .createQuery(
              "SELECT p.id, invBy.name,  c.code, p.projectName, c.customerName,p.deere,res.firstName, res.lastName, p.onsiteDelMgr, em.engagementModelName,p.projectKickOff,p.plannedProjSize,cur.currencyName,p.plannedProjCost,res.middleName,p.projectEndDate,p.orgHierarchy FROM Project p, Customer c, Resource res, EngagementModel em, Currency cur, InvoiceBy invBy,org_hierarchy o WHERE p.customerNameId = c.id AND p.offshoreDelMgr = res.employeeId AND p.projectTrackingCurrencyId = cur.id AND p.engagementModelId = em.id AND p.invoiceById = invBy.id AND p.bu_id=o.id ")
          .list();
      logger.info("--------ProjectAllocationDaoImpl findAll method ends-------");
    } catch (HibernateException hibernateException) {
      logger.error("Exception occured in findAll method at DAO layer:-" + hibernateException);
      throw hibernateException;
    }
    return projects;
  }

  public List<Project> findAllActiveProjectsByManager(Integer currentLoggedInUserId) {

    logger.info("--------ProjectAllocationDaoImpl findAll method start-------");
    Session session = (Session) getEntityManager().getDelegate();
    Criteria criteria = session.createCriteria(Project.class);
    List<Project> projects = new ArrayList<Project>();
    try {
      criteria.add(Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date()))
          .add(Restrictions.isNull("projectEndDate")));

      if (currentLoggedInUserId != null) {
        criteria.add(Restrictions.eq("offshoreDelMgr.employeeId", currentLoggedInUserId));
      }

      projects = criteria.list();

    } catch (HibernateException hibernateException) {
      logger.error("Exception occured in findAll method at DAO layer:-" + hibernateException);
      throw hibernateException;
    }
    return projects;
  }

  public List<Project> findAllActiveProjects(Integer page, Integer size,
      ProjectAllocationSearchCriteria projectAllocationSearchCriteria, String activeOrAll,
      String sSearch, List<OrgHierarchy> businessGroup, Integer currentLoggedInUserId) {

    logger.info("--------ProjectAllocationDaoImpl findAll method start-------");
    Session session = (Session) getEntityManager().getDelegate();
    Criteria criteria = session.createCriteria(Project.class);
    List<Project> projects = new ArrayList<Project>();
    List<Integer> subempProjects= new ArrayList<Integer>();
    Criterion isActive=null;
    Criterion isCurrentLoggedInUser=null;
    Criterion isBusinessGroup=null;
    if(currentLoggedInUserId!=null)
    {
    	ResourceDao resourceDao=AppContext.getApplicationContext().getBean(ResourceDao.class);
    	List<Integer> employeeIds=resourceDao.findActiveResourceIdByRM2RM1(currentLoggedInUserId);
    	subempProjects=resourceAllocDao.findAllocatedActiveProjectByEmployeeId(employeeIds, activeOrAll.equals("active"));
    }
    try {

     

      if (activeOrAll.equals("active")) {
    	
    	  isActive=Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date()))
            .add(Restrictions.isNull("projectEndDate"));
      }
      
      if(currentLoggedInUserId!=null)
      {
    	  if(!subempProjects.isEmpty())
    	  {
    		  System.out.println("subempProjects is not empty");
    	  isCurrentLoggedInUser= Restrictions.or(Restrictions.eq("offshoreDelMgr.employeeId",currentLoggedInUserId),
    			  (Restrictions.eq("deliveryMgr.employeeId", currentLoggedInUserId)),Restrictions.in("id", subempProjects));
    	  }
    	  else
    	  {
    		  System.out.println("subempProjects is empty");
    		  isCurrentLoggedInUser= Restrictions.or(Restrictions.eq("offshoreDelMgr.employeeId",currentLoggedInUserId),
        			  (Restrictions.eq("deliveryMgr.employeeId", currentLoggedInUserId)));
      
    	  }
      }
        if (businessGroup != null && !businessGroup.isEmpty()) {
        	isBusinessGroup=Restrictions.in("orgHierarchy", businessGroup);
	      }
        
        if(isCurrentLoggedInUser!=null && isBusinessGroup!=null && isActive!=null)
        {
        criteria.add(Restrictions.and(Restrictions.or(isCurrentLoggedInUser,isBusinessGroup),isActive));
        }
        else if(isCurrentLoggedInUser!=null && isBusinessGroup!=null && isActive==null)
        {
        	criteria.add(Restrictions.or(isCurrentLoggedInUser,isBusinessGroup));
        }else if((isCurrentLoggedInUser==null || isBusinessGroup==null) && isActive!=null)
        {
        	criteria.add(isActive);
        }
      
      if (sSearch.length() > 0) {
        if (projectAllocationSearchCriteria.getColandTableNameMap()
            .get(projectAllocationSearchCriteria.getRefColName()) != null) {
          if (projectAllocationSearchCriteria.getRefColName().equals("offshoreDelMgr")) {
            String searchInput = projectAllocationSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");
            criteria.createCriteria(projectAllocationSearchCriteria.getRefColName(), "table")
                .add(ConcatenableIlikeCriterion.ilike(searchInput, MatchMode.ANYWHERE, "firstName",
                    "middleName", "lastName"));
          }
          else if (projectAllocationSearchCriteria.getRefColName().equals("deliveryMgr")) {
              
                String searchInput = projectAllocationSearchCriteria.getSearchValue();
                searchInput = searchInput.replace(" ", "%");
                criteria.createCriteria(projectAllocationSearchCriteria.getRefColName(), "table")
                .add(ConcatenableIlikeCriterion.ilike(searchInput, MatchMode.ANYWHERE, "firstName",
                    "middleName", "lastName"));
              
            }

          else if (projectAllocationSearchCriteria.getBaseTableCol().equals("customerName")) {
            if (projectAllocationSearchCriteria.getRefColName().equals("customerName")) {
              criteria.createAlias("customerNameId", "customer");
              String searchInput = projectAllocationSearchCriteria.getSearchValue();
              searchInput = searchInput.replace(" ", "%");
              criteria.add(Restrictions.like("customer.customerName", "%" + searchInput + "%"));
            }
          }

          else if (projectAllocationSearchCriteria.getBaseTableCol().equals("BuCode")) {
            List<OrgHierarchy> bgBuId =
                searchByBgBu(projectAllocationSearchCriteria.getSearchValue());
            if (!bgBuId.isEmpty())
              criteria.add(Restrictions.in("orgHierarchy", bgBuId));
            else {
              return projects;
            }
          }

          else if (projectAllocationSearchCriteria.getBaseTableCol().equals("engagementModelId")) {
            if (projectAllocationSearchCriteria.getRefColName().equals("engagementMode")) {
              criteria.createAlias("engagementModelId", "engagementModel");
              String searchInput = projectAllocationSearchCriteria.getSearchValue();
              searchInput = searchInput.replace(" ", "%");
              criteria.add(Restrictions.like("engagementModel.engagementModelName",
                  "%" + searchInput + "%"));
            }
          }

          else if (projectAllocationSearchCriteria.getRefColName().equals("plannedProjectSize")) {
            String searchInput = projectAllocationSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");
            criteria.add(Restrictions.eq("plannedProjSize", Integer.parseInt(searchInput)));
          }

          else {
            if (projectAllocationSearchCriteria.getIntegerValue() != 0) {
              criteria.add(Restrictions.like(projectAllocationSearchCriteria.getRefColName(),
                  projectAllocationSearchCriteria.getIntegerValue()));
            } else {
              criteria.createAlias(projectAllocationSearchCriteria.getBaseTableCol(), "table");
              criteria
                  .add(Restrictions.like("table." + projectAllocationSearchCriteria.getRefColName(),
                      "%" + projectAllocationSearchCriteria.getSearchValue() + "%"));
            }
          }
        } else {
          if (projectAllocationSearchCriteria.getRefColName().equals("projectName")) {
            String searchInput = projectAllocationSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");
            criteria.add(Restrictions.like("projectName", "%" + searchInput + "%"));

          }

          if (projectAllocationSearchCriteria.getRefColName().equals("onsiteDelMgr")) {
            String searchInput = projectAllocationSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");
            criteria.add(Restrictions.like("onsiteDelMgr", "%" + searchInput + "%"));
          }

          if (projectAllocationSearchCriteria.getRefColName().equals("offshoreDelMgr")) {
            String searchInput = projectAllocationSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");
            criteria.createCriteria(projectAllocationSearchCriteria.getRefColName(), "table")
                .add(ConcatenableIlikeCriterion.ilike(searchInput, MatchMode.ANYWHERE, "firstName",
                    "middleName", "lastName"));
          }

          if (projectAllocationSearchCriteria.getRefColName().equals("projectKickOff")) {
            String searchInput = projectAllocationSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");
            criteria.add(Restrictions.like("projectKickOff", "%" + searchInput + "%"));
          }
        }

      } else {

        tableSort(projectAllocationSearchCriteria, criteria);
      }

      if (page != null || size != null) {
        criteria.setFirstResult(page).setMaxResults(size);
      }

      projects = criteria.list();

    } catch (HibernateException hibernateException) {
      logger.error("Exception occured in findAll method at DAO layer:-" + hibernateException);
      throw hibernateException;
    }
    return projects;
  }

  public List<String> findAllActiveProjectsForBGAdminDashboard(List<OrgHierarchy> businessGroup,
      Integer currentLoggedInUserId) {

    logger.info("--------ProjectAllocationDaoImpl findAll method start-------");
    Session session = (Session) getEntityManager().getDelegate();
    Criteria criteria = session.createCriteria(Project.class);
    ProjectionList list = Projections.projectionList();
    list.add(Projections.groupProperty("projectName"));

    List<String> projectNameList = new ArrayList<String>();
    criteria.setProjection(Property.forName("projectName"));
    try {

      if (businessGroup != null && businessGroup.size() > 0) {
        criteria.add(Restrictions.in("orgHierarchy", businessGroup));
      }

      criteria.add(Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date()))
          .add(Restrictions.isNull("projectEndDate")));

      if (currentLoggedInUserId != null) {
        criteria.add(Restrictions.eq("offshoreDelMgr.employeeId", currentLoggedInUserId));
      }

      projectNameList = criteria.list();

    } catch (HibernateException hibernateException) {
      logger.error("Exception occured in findAll method at DAO layer:-" + hibernateException);
      throw hibernateException;
    }
    return projectNameList;
  }

  public List<Project> findByEntries(int firstResult, int sizeNo) {
    Session session = (Session) getEntityManager().getDelegate();
    logger.info("--------ProjectAllocationDaoImpl findByEntries method start-------");
    List<Project> projects = new ArrayList<Project>();
    try {
      projects = session.createQuery("From Project").setFirstResult(firstResult)
          .setMaxResults(sizeNo).list();
      logger.info("--------ProjectAllocationDaoImpl findByEntries method ends-------");
    } catch (HibernateException hibernateException) {
      logger.error("Exception occured in findByEntries method at DAO layer:-" + hibernateException);
      throw hibernateException;
    }
    return projects;
  }

  public long countTotal() {
    return 0;
  }

  public List<Project> findProjectsByProjectNameEquals(String projectName) {
    Session session = (Session) getEntityManager().getDelegate();
    logger.info(
        "--------ProjectAllocationDaoImpl findProjectsByProjectNameEquals method start-------");
    List<Project> projects = new ArrayList<Project>();
    try {
      Criteria criteria = session.createCriteria(Project.class);
      criteria.add(Restrictions.eq("projectName", projectName));
      projects = criteria.list();
      if (null != projects && projects.size() > 0) {

        for (Project project : projects)

          if (project.getOrgHierarchy() != null)
            project.getOrgHierarchy().getName();

      }
      logger.info(
          "--------ProjectAllocationDaoImpl findProjectsByProjectNameEquals method ends-------");
    } catch (HibernateException hibernateException) {
      logger.error("Exception occured in findProjectsByProjectNameEquals method at DAO layer:-"
          + hibernateException);
      throw hibernateException;
    }
    return projects;

  }

  public List<Project> findActiveProjectsByProjectNameEquals(String projectName, String sSearch,
      ProjectAllocationSearchCriteria projectAllocationSearchCriteria, String activeOrAll) {
    Session session = (Session) getEntityManager().getDelegate();
    logger.info(
        "--------ProjectAllocationDaoImpl findProjectsByProjectNameEquals method start-------");
    List<Project> projects = new ArrayList<Project>();
    try {
      Criteria criteria = session.createCriteria(Project.class);
      criteria.add(Restrictions.eq("projectName", projectName));
      if (activeOrAll.equals("active")) {
        criteria.add(Restrictions.disjunction()
            .add(Restrictions.ge("projectEndDate", new Date(System.currentTimeMillis())))
            .add(Restrictions.isNull("projectEndDate")));
      }
      if (sSearch.length() > 0) {
        if (projectAllocationSearchCriteria.getColandTableNameMap()
            .get(projectAllocationSearchCriteria.getRefColName()) != null) {
          if (projectAllocationSearchCriteria.getRefColName().equals("offshoreDelMgr")) {
            String searchInput = projectAllocationSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");
            criteria.createCriteria(projectAllocationSearchCriteria.getRefColName(), "table")
                .add(ConcatenableIlikeCriterion.ilike(searchInput, MatchMode.ANYWHERE, "firstName",
                    "middleName", "lastName"));
          } 
          else if (projectAllocationSearchCriteria.getRefColName().equals("deliveryMgr")) {
              
              String searchInput = projectAllocationSearchCriteria.getSearchValue();
              searchInput = searchInput.replace(" ", "%");
              criteria.createCriteria(projectAllocationSearchCriteria.getRefColName(), "table")
              .add(ConcatenableIlikeCriterion.ilike(searchInput, MatchMode.ANYWHERE, "firstName",
                  "middleName", "lastName"));
            
          }
          else if (projectAllocationSearchCriteria.getBaseTableCol().equals("customerName")) {
            if (projectAllocationSearchCriteria.getRefColName().equals("customerName")) {
              criteria.createAlias("customerNameId", "customer");
              String searchInput = projectAllocationSearchCriteria.getSearchValue();
              searchInput = searchInput.replace(" ", "%");
              criteria.add(Restrictions.like("customer.customerName", "%" + searchInput + "%"));
            }
          }

          else if (projectAllocationSearchCriteria.getBaseTableCol().equals("BuCode")) {
            List<OrgHierarchy> bgBuId =
                searchByBgBu(projectAllocationSearchCriteria.getSearchValue());
            if (!bgBuId.isEmpty())
              criteria.add(Restrictions.in("orgHierarchy", bgBuId));
            else {
              return projects;
            }
          }

          else if (projectAllocationSearchCriteria.getBaseTableCol().equals("engagementModelId")) {
            if (projectAllocationSearchCriteria.getRefColName().equals("engagementMode")) {
              criteria.createAlias("engagementModelId", "engagementModel");
              String searchInput = projectAllocationSearchCriteria.getSearchValue();
              searchInput = searchInput.replace(" ", "%");
              criteria.add(Restrictions.like("engagementModel.engagementModelName",
                  "%" + searchInput + "%"));
            }
          }

          else if (projectAllocationSearchCriteria.getRefColName().equals("plannedProjectSize")) {
            String searchInput = projectAllocationSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");
            criteria.add(Restrictions.eq("plannedProjSize", Integer.parseInt(searchInput)));
          }
          
          else {
            if (projectAllocationSearchCriteria.getIntegerValue() != 0) {
              criteria.add(Restrictions.like(projectAllocationSearchCriteria.getRefColName(),
                  projectAllocationSearchCriteria.getIntegerValue()));
            } else {
              criteria.add(Restrictions.like(projectAllocationSearchCriteria.getRefColName(),
                  "%" + projectAllocationSearchCriteria.getSearchValue() + "%"));
            }
          }
        } else {
          if (projectAllocationSearchCriteria.getRefColName().equals("projectName")) {
            String searchInput = projectAllocationSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");
            criteria.add(Restrictions.like("projectName", "%" + searchInput + "%"));

          }

          if (projectAllocationSearchCriteria.getRefColName().equals("onsiteDelMgr")) {
            String searchInput = projectAllocationSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");
            criteria.add(Restrictions.like("onsiteDelMgr", "%" + searchInput + "%"));
          }
          
        }
      } else {

        tableSort(projectAllocationSearchCriteria, criteria);
      }

      projects = criteria.list();

      logger.info(
          "--------ProjectAllocationDaoImpl findProjectsByProjectNameEquals method ends-------");
    } catch (HibernateException hibernateException) {
      logger.error("Exception occured in findProjectsByProjectNameEquals method at DAO layer:-"
          + hibernateException);
      throw hibernateException;
    }
    return projects;

  }

  public List<Project> managerView(Resource currentLoggedInUserId, String activeOrAll) {
    Session session = (Session) getEntityManager().getDelegate();
    List projects = new ArrayList();
    logger.info("--------ProjectAllocationDaoImpl managerView method start-------");
    Criteria criteria = session.createCriteria(Project.class);

    try {
      if (activeOrAll.equalsIgnoreCase("active")) {
        criteria.add(Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date()))
            .add(Restrictions.isNull("projectEndDate")));
      }
      criteria.createAlias("customerNameId", "customer").createAlias("offshoreDelMgr", "resource")
          .createAlias("projectTrackingCurrencyId", "currency")
          .createAlias("engagementModelId", "em").createAlias("invoiceById", "invoice")
          .add(Restrictions.eq("offshoreDelMgr", currentLoggedInUserId));

      projects = criteria.list();
      logger.info("--------ProjectAllocationDaoImpl managerView method ends-------");
    } catch (HibernateException hibernateException) {
      logger.error("Exception occured in managerView method at DAO layer:-" + hibernateException);
      throw hibernateException;
    }
    return projects;
  }

  public List<Integer> getProjectIdsForManager(Integer currentLoggedInUserId, String activeOrAll) {
    Session session = (Session) getEntityManager().getDelegate();
    Criteria criteria =
        session.createCriteria(Project.class).setProjection(Projections.property("id"));;
    List projects = new ArrayList();
    logger.info("--------ProjectAllocationDaoImpl managerView method start-------");

    try {
      if (activeOrAll.equalsIgnoreCase("all"))

        criteria.add(Restrictions.eq("offshoreDelMgr.employeeId", currentLoggedInUserId));

      else {
        criteria.add(Restrictions.eq("offshoreDelMgr.employeeId", currentLoggedInUserId))
            .add(Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date()))
                .add(Restrictions.isNull("projectEndDate")));
      }

      projects = criteria.list();
      logger.info("--------ProjectAllocationDaoImpl managerView method ends-------");
    } catch (HibernateException hibernateException) {
      logger.error("Exception occured in managerView method at DAO layer:-" + hibernateException);
      throw hibernateException;
    }
    return projects;
  }

  protected void sortCriteria(ProjectAllocationSearchCriteria searchCriteria, Criteria criteria) {
    if (searchCriteria.getOrderStyle().equals("asc")) {
      criteria.addOrder(Order.asc(searchCriteria.getOrederBy()));
    } else {
      criteria.addOrder(Order.desc(searchCriteria.getOrederBy()));
    }
  }

  public Long countActive(String activeOrAll, boolean isCurrentUserAdmin, List<OrgHierarchy> buList,
      Integer currentLoggedInUserId) {
    Long count = 0L;
    Session session = (Session) getEntityManager().getDelegate();
    Criteria criteria = session.createCriteria(Project.class);
    List<Integer> subempProjects= new ArrayList<Integer>();
    Criterion isActive=null;
    Criterion isCurrentLoggedInUser=null;
    Criterion isBusinessGroup=null;
    if(currentLoggedInUserId!=null)
    {
    	ResourceDao resourceDao=AppContext.getApplicationContext().getBean(ResourceDao.class);
    	List<Integer> employeeIds=resourceDao.findActiveResourceIdByRM2RM1(currentLoggedInUserId);
    	subempProjects=resourceAllocDao.findAllocatedActiveProjectByEmployeeId(employeeIds, activeOrAll.equals("active"));
    }
    try {

     

      if (activeOrAll.equals("active")) {
    	
    	  isActive=Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date()))
            .add(Restrictions.isNull("projectEndDate"));
      }
      
      if(currentLoggedInUserId!=null)
      {
    	  if(!subempProjects.isEmpty())
    	  {
    		  System.out.println("subempProjects is not empty");
    	  isCurrentLoggedInUser= Restrictions.or(Restrictions.eq("offshoreDelMgr.employeeId",currentLoggedInUserId),
    			  (Restrictions.eq("deliveryMgr.employeeId", currentLoggedInUserId)),Restrictions.in("id", subempProjects));
    	  }
    	  else
    	  {
    		  System.out.println("subempProjects is empty");
    		  isCurrentLoggedInUser= Restrictions.or(Restrictions.eq("offshoreDelMgr.employeeId",currentLoggedInUserId),
        			  (Restrictions.eq("deliveryMgr.employeeId", currentLoggedInUserId)));
      
    	  }
      }
        if (buList != null && !buList.isEmpty()) {
        	isBusinessGroup=Restrictions.in("orgHierarchy", buList);
	      }
        
        if(isCurrentLoggedInUser!=null && isBusinessGroup!=null && isActive!=null)
        {
        criteria.add(Restrictions.and(Restrictions.or(isCurrentLoggedInUser,isBusinessGroup),isActive));
        }
        else if(isCurrentLoggedInUser!=null && isBusinessGroup!=null && isActive==null)
        {
        	criteria.add(Restrictions.or(isCurrentLoggedInUser,isBusinessGroup));
        }else if((isCurrentLoggedInUser==null || isBusinessGroup==null) && isActive!=null)
        {
        	criteria.add(isActive);
        }
    }catch(HibernateException ex)
    {
    	 logger.error(
    	          "HibernateException occured in countSearch of TimeHoursDao:-" + ex);
    	      throw ex;
    }
    
    
          count =(Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
          return count;

  }

  public long countSearch(ProjectAllocationSearchCriteria projectAllocationSearchCriteria,
      String activeOrAll, String search, List<OrgHierarchy> businessGroup,
      Integer currentLoggedInUserId) {
	  Long count = 0L;
	    Session session = (Session) getEntityManager().getDelegate();
	    Criteria criteria = session.createCriteria(Project.class);
	    List<Integer> subempProjects= new ArrayList<Integer>();
	    Criterion isActive=null;
	    Criterion isCurrentLoggedInUser=null;
	    Criterion isBusinessGroup=null;
	    if(currentLoggedInUserId!=null)
	    {
	    	ResourceDao resourceDao=AppContext.getApplicationContext().getBean(ResourceDao.class);
	    	List<Integer> employeeIds=resourceDao.findActiveResourceIdByRM2RM1(currentLoggedInUserId);
	    	subempProjects=resourceAllocDao.findAllocatedActiveProjectByEmployeeId(employeeIds, activeOrAll.equals("active"));
	    }
	    try {

	     

	      if (activeOrAll.equals("active")) {
	    	
	    	  isActive=Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date()))
	            .add(Restrictions.isNull("projectEndDate"));
	      }
	      
	      if(currentLoggedInUserId!=null)
	      {
	    	  if(!subempProjects.isEmpty())
	    	  {
	    		  System.out.println("subempProjects is not empty");
	    	  isCurrentLoggedInUser= Restrictions.or(Restrictions.eq("offshoreDelMgr.employeeId",currentLoggedInUserId),
	    			  (Restrictions.eq("deliveryMgr.employeeId", currentLoggedInUserId)),Restrictions.in("id", subempProjects));
	    	  }
	    	  else
	    	  {
	    		  System.out.println("subempProjects is empty");
	    		  isCurrentLoggedInUser= Restrictions.or(Restrictions.eq("offshoreDelMgr.employeeId",currentLoggedInUserId),
	        			  (Restrictions.eq("deliveryMgr.employeeId", currentLoggedInUserId)));
	      
	    	  }
	      }
	        if (businessGroup != null && !businessGroup.isEmpty()) {
	        	isBusinessGroup=Restrictions.in("orgHierarchy", businessGroup);
		      }
	        
	        if(isCurrentLoggedInUser!=null && isBusinessGroup!=null && isActive!=null)
	        {
	        criteria.add(Restrictions.and(Restrictions.or(isCurrentLoggedInUser,isBusinessGroup),isActive));
	        }
	        else if(isCurrentLoggedInUser!=null && isBusinessGroup!=null && isActive==null)
	        {
	        	criteria.add(Restrictions.or(isCurrentLoggedInUser,isBusinessGroup));
	        }else if((isCurrentLoggedInUser==null || isBusinessGroup==null) && isActive!=null)
	        {
	        	criteria.add(isActive);
	        }
      if (search.length() > 0) {
        if (projectAllocationSearchCriteria.getColandTableNameMap()
            .get(projectAllocationSearchCriteria.getRefColName()) != null) {
          // check for off shore manager
          if (projectAllocationSearchCriteria.getRefColName().equals("offshoreDelMgr")) {
            String searchInput = projectAllocationSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");

            criteria.createCriteria(projectAllocationSearchCriteria.getRefColName(), "table")
                .add(ConcatenableIlikeCriterion.ilike(searchInput, MatchMode.ANYWHERE, "firstName",
                    "middleName", "lastName"));
          }
          else if (projectAllocationSearchCriteria.getRefColName().equals("deliveryMgr")) {
              String searchInput = projectAllocationSearchCriteria.getSearchValue();
              searchInput = searchInput.replace(" ", "%");

              criteria.createCriteria(projectAllocationSearchCriteria.getRefColName(), "table")
                  .add(ConcatenableIlikeCriterion.ilike(searchInput, MatchMode.ANYWHERE, "firstName",
                      "middleName", "lastName"));
            }

          else if (projectAllocationSearchCriteria.getBaseTableCol().equals("customerName")) {
            if (projectAllocationSearchCriteria.getRefColName().equals("customerName"))
              criteria.createAlias("customerNameId", "customer");
            String searchInput = projectAllocationSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");
            criteria.add(Restrictions.like("customer.customerName", "%" + searchInput + "%"));
          }

          else if (projectAllocationSearchCriteria.getBaseTableCol().equals("BuCode")) {
            List<OrgHierarchy> bgBuId =
                searchByBgBu(projectAllocationSearchCriteria.getSearchValue());
            if (!bgBuId.isEmpty())
              criteria.add(Restrictions.in("orgHierarchy", bgBuId));
            else
              return count;
          }

          else if (projectAllocationSearchCriteria.getBaseTableCol().equals("engagementModelId")) {
            if (projectAllocationSearchCriteria.getRefColName().equals("engagementMode")) {
              criteria.createAlias("engagementModelId", "engagementModel");
              projectAllocationSearchCriteria.setOrederBy("engagementModel.engagementModelName");
            }
          }

          else if (projectAllocationSearchCriteria.getBaseTableCol().equals("plannedProjSize")) {
            if (projectAllocationSearchCriteria.getRefColName().equals("plannedProjectSize")) {
              projectAllocationSearchCriteria.setOrederBy("plannedProjSize");
            }
          }

          else {
            if (projectAllocationSearchCriteria.getIntegerValue() != 0) {
              criteria.add(Restrictions.like(projectAllocationSearchCriteria.getRefColName(),
                  projectAllocationSearchCriteria.getIntegerValue()));
            }

            else {
              criteria.add(Restrictions.like(projectAllocationSearchCriteria.getRefColName(),
                  "%" + projectAllocationSearchCriteria.getSearchValue() + "%"));
            }
          }
        } else {

          if (projectAllocationSearchCriteria.getRefColName().equals("projectName")) {
            String searchInput = projectAllocationSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");
            criteria.add(Restrictions.like("projectName", "%" + searchInput + "%"));

          }

          if (projectAllocationSearchCriteria.getRefColName().equals("onsiteDelMgr")) {
            String searchInput = projectAllocationSearchCriteria.getSearchValue();
            searchInput = searchInput.replace(" ", "%");
            criteria.add(Restrictions.like("onsiteDelMgr", "%" + searchInput + "%"));
          }

        }
        if (activeOrAll.equalsIgnoreCase("active")) {
          criteria.add(Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date()))
              .add(Restrictions.isNull("projectEndDate")));
        }
      }

      count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();

    } catch (HibernateException hibernateException) {
      logger.error(
          "HibernateException occured in countSearch of TimeHoursDao:-" + hibernateException);
      throw hibernateException;
    }
    logger.info("-------TimeHoursDaoImpl countSearch method end-------- ");
    return count;
  }

  protected void tableSort(ProjectAllocationSearchCriteria projectAllocationSearchCriteria,
      Criteria criteria) {

    if (projectAllocationSearchCriteria.getColandTableNameMap()
        .get(projectAllocationSearchCriteria.getRefColName()) != null) {

      if (projectAllocationSearchCriteria.getBaseTableCol().equals("offshoreDelMgr")) {
        if (projectAllocationSearchCriteria.getRefColName().equals("offshoreDelMgr")) {
          criteria.createAlias("offshoreDelMgr", "table");
          projectAllocationSearchCriteria.setOrederBy("table.firstName");
        }
      }
      else if (projectAllocationSearchCriteria.getBaseTableCol().equals("deliveryMgr")) {
          if (projectAllocationSearchCriteria.getRefColName().equals("deliveryMgr")) {
            criteria.createAlias("deliveryMgr", "table");
            projectAllocationSearchCriteria.setOrederBy("table.firstName");
          }
        }

      else if (projectAllocationSearchCriteria.getBaseTableCol().equals("engagementModelId")) {
        if (projectAllocationSearchCriteria.getRefColName().equals("engagementMode")) {
          criteria.createAlias("engagementModelId", "engagementModel");
          projectAllocationSearchCriteria.setOrederBy("engagementModel.engagementModelName");
        }
      }

      else if (projectAllocationSearchCriteria.getBaseTableCol().equals("customerName")) {
        if (projectAllocationSearchCriteria.getRefColName().equals("customerName")) {
          criteria.createAlias("customerNameId", "table");
          projectAllocationSearchCriteria.setOrederBy("table.customerName");
        }
      }

      else if (projectAllocationSearchCriteria.getBaseTableCol().equals("plannedProjSize")) {
        if (projectAllocationSearchCriteria.getRefColName().equals("plannedProjectSize")) {
          projectAllocationSearchCriteria.setOrederBy("plannedProjSize");
        }
      }

      else {
        criteria.createAlias(projectAllocationSearchCriteria.getBaseTableCol(), "table");
      }
      sortCriteria(projectAllocationSearchCriteria, criteria);
    }

    else if (projectAllocationSearchCriteria.getRefColName().equals("projectName")) {
      if (projectAllocationSearchCriteria.getOrderStyle().equals("asc")) {
        criteria.addOrder(Order.asc("projectName"));
      } else {
        criteria.addOrder(Order.desc("projectName"));
      }
    }

    else {
      if (projectAllocationSearchCriteria.getOrderStyle().equals("asc")) {
        criteria.addOrder(Order.asc(projectAllocationSearchCriteria.getOrederBy()));
      } else {
        criteria.addOrder(Order.desc(projectAllocationSearchCriteria.getOrederBy()));
      }
    }
  }

  private List<OrgHierarchy> searchByBgBu(String projectAllocationSearchCriteria) {
    Session session = (Session) getEntityManager().getDelegate();
    List<BgBuView> bgBuIdFromView = new ArrayList<BgBuView>();
    List<OrgHierarchy> bgBuIdList = new ArrayList<OrgHierarchy>();

    Criteria criteria = session.createCriteria(BgBuView.class);
    criteria.add(Restrictions.like("name", "%" + projectAllocationSearchCriteria + "%"));
    bgBuIdFromView = criteria.list();

    if (bgBuIdFromView != null) {
      for (BgBuView bgBuView : bgBuIdFromView) {
        OrgHierarchy hierarchy = new OrgHierarchy();
        hierarchy.setId(bgBuView.getId());
        bgBuIdList.add(hierarchy);
      }
    }

    return bgBuIdList;
  }

  public List<Project> findCountProjectName(List<Integer> empIdList, Integer page, Integer size,
	      String activeOrAll, List<String> projectId,Integer currentLoggedInUserId) {
	    Session session = (Session) getEntityManager().getDelegate();
	    Criteria criteria = session.createCriteria(DMProjectVisibilityView.class);
	    Criterion criterion1 = null;
	    Criterion criterion2 = null;
	    Criterion criterion3 = null;
	    ProjectionList list = Projections.projectionList();
	    list.add(Projections.groupProperty("projectName"));

	    List<Project> projectNameList = new ArrayList<Project>();
	    criteria.setProjection(Property.forName("projectName"));

	    if (activeOrAll.equals("active")) {
	     
	    	criterion1=Restrictions.or(Restrictions.isNull("allocEndDate"),
	          Restrictions.ge("allocEndDate", new Date()));
	    }
	    criteria.setProjection(list);

	    criteria.addOrder(Order.asc("projectName"));

	    if (page != null && size != null) {
	      criteria.setFirstResult(page).setMaxResults(size);
	    }
	    
	    criterion2=Restrictions.and( Restrictions.or(Restrictions.eq("offshoreDelMgr",currentLoggedInUserId),Restrictions.eq("deliveryMgr",currentLoggedInUserId)),(Restrictions.or(Restrictions.isNull("projectEndDate"),
	            Restrictions.ge("projectEndDate", new Date()))));
	    
	    if (empIdList != null && empIdList.size() > 0 || projectId != null && projectId.size() > 0) {
	    	if (!empIdList.isEmpty() && !projectId.isEmpty()) {
		    	criterion3 = Restrictions.or(Restrictions.or(Restrictions.in("employeeId", empIdList),Restrictions.or(Restrictions.eq("offshoreDelMgr",currentLoggedInUserId),Restrictions.eq("deliveryMgr",currentLoggedInUserId))),
		          Restrictions.in("projectName", projectId));
	    	} else if(!empIdList.isEmpty())
	    	{
	    		criterion3=Restrictions.or(Restrictions.in("employeeId", empIdList),Restrictions.or(Restrictions.eq("offshoreDelMgr",currentLoggedInUserId),Restrictions.eq("deliveryMgr",currentLoggedInUserId)));
	    	}
	    		else if (!projectId.isEmpty()) {
	        	  criterion3 = Restrictions.in("projectName", projectId);
	          }
	    }
	    
	    if(criterion1!=null && criterion2!=null && criterion3!=null){
	     //criteria.add(Restrictions.or(criterion2,Restrictions.and(criterion1,criterion3)));
	    	
	    	criteria.add(Restrictions.and(criterion1,Restrictions.or(criterion2,criterion3)));
	    }else if(criterion1==null && criterion2!=null && criterion3!=null){
	       criteria.add(Restrictions.or(criterion2,criterion3));	
	    }else if(criterion1!=null && criterion2==null && criterion3!=null){
	        criteria.add(Restrictions.and(criterion1,criterion3));	
	    }else if(criterion1!=null && criterion2!=null && criterion3==null){
	    	//Start-Fix for display No data available if no reportees and no project as manager
	    	//criteria.add(Restrictions.or(criterion2,criterion1));
	    	criteria.add(Restrictions.and(criterion2,criterion1));
	    	//End-Start-Fix for display No data available if no reportees and no project as manager
	    }else if(criterion1==null && criterion2==null && criterion3!=null){
	    	criteria.add(criterion3);
	    }else if(criterion1==null && criterion2!=null && criterion3==null){
	    	criteria.add(criterion2);
	    }else if(criterion1!=null && criterion2==null && criterion3==null){
	    	criteria.add(criterion1);
	    }
	    
	    projectNameList = criteria.list();
	    
	    return projectNameList;
	  }

  public List<String> findCountProjectNameForDashboardBillingStatus(List<Integer> empIdList,
      String activeOrAll, List<String> projectId) {
    Session session = (Session) getEntityManager().getDelegate();
    Criteria criteria = session.createCriteria(TimehrsEmployeeIdProjectView.class);
    Criterion criterion = null;
    ProjectionList list = Projections.projectionList();
    list.add(Projections.groupProperty("projectName"));

    List<String> projectNameList = new ArrayList<String>();
    criteria.setProjection(Property.forName("projectName"));

    if (activeOrAll.equals("active")) {
      criteria.add(Restrictions.or(Restrictions.isNull("allocEndDate"),
          Restrictions.ge("allocEndDate", new Date())));
    }
    criteria.setProjection(list);

    criteria.addOrder(Order.asc("projectName"));

    if ((empIdList != null && !empIdList.isEmpty())
        || (projectId != null && !projectId.isEmpty())) {
      if (!empIdList.isEmpty() && !projectId.isEmpty()) {
        criterion = Restrictions.or(Restrictions.in("employeeId", empIdList),
            Restrictions.in("projectName", projectId));
        criteria.add(criterion);
        projectNameList = criteria.list();
      } else if (!empIdList.isEmpty()) {
        criterion = Restrictions.in("employeeId", empIdList);
        criteria.add(criterion);
        projectNameList = criteria.list();
      } else if (!projectId.isEmpty()) {
        criterion = Restrictions.in("projectName", projectId);
        criteria.add(criterion);
        projectNameList = criteria.list();
      }

    }
    return projectNameList;
  }

  public List<String> getProjectNameForManager(String activeOrAll, Integer currentLoggedInUserId) {

    Session session = (Session) getEntityManager().getDelegate();
    Criteria criteria =
        session.createCriteria(Project.class).setProjection(Projections.property("projectName"));;
    List<String> projects = new ArrayList<String>();
    logger.info("--------ProjectAllocationDaoImpl managerView method start-------");

    try {
      if (activeOrAll.equalsIgnoreCase("all"))

        criteria.add(Restrictions.eq("offshoreDelMgr.employeeId", currentLoggedInUserId));

      else {
        criteria.add(Restrictions.eq("offshoreDelMgr.employeeId", currentLoggedInUserId))
            .add(Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date()))
                .add(Restrictions.isNull("projectEndDate")));
      }

      projects = criteria.list();
      logger.info("--------ProjectAllocationDaoImpl managerView method ends-------");
    } catch (HibernateException hibernateException) {
      logger.error("Exception occured in managerView method at DAO layer:-" + hibernateException);
      throw hibernateException;
    }
    return projects;

  }

  public Set<Project> getProjectNameForManagerOnProject(String activeOrAll,
      Integer currentLoggedInUserId) {

    Session session = (Session) getEntityManager().getDelegate();
    Criteria criteria =
        session.createCriteria(Project.class).setProjection(Projections.property("projectName"));;
    Set<Project> projects = new HashSet<Project>();
    logger.info("--------ProjectAllocationDaoImpl managerView method start-------");

    try {
      if (activeOrAll.equalsIgnoreCase("all"))

        criteria.add(Restrictions.eq("offshoreDelMgr.employeeId", currentLoggedInUserId));

      else {
        criteria.add(Restrictions.eq("offshoreDelMgr.employeeId", currentLoggedInUserId))
            .add(Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date()))
                .add(Restrictions.isNull("projectEndDate")));
      }

      projects.addAll(criteria.list());
      logger.info("--------ProjectAllocationDaoImpl managerView method ends-------");
    } catch (HibernateException hibernateException) {
      logger.error("Exception occured in managerView method at DAO layer:-" + hibernateException);
      throw hibernateException;
    }
    return projects;

  }

  public List<Integer> getProjectIdsForBGAdmin(List<OrgHierarchy> businessGroup) {

    Session session = (Session) getEntityManager().getDelegate();
    Integer currentLoggedInUserId=0;
    currentLoggedInUserId=UserUtil.getUserContextDetails().getEmployeeId();
    Criteria criteria =
        session.createCriteria(Project.class).setProjection(Projections.property("id"));;
    List<Integer> projectIds = new ArrayList<Integer>();
    logger.info("--------ProjectAllocationDaoImpl getProjectIdsForBGAdmin method start-------");

    try {
      if (!businessGroup.isEmpty()) {
    	  
    	  Criterion c1=Restrictions.or(Restrictions.in("orgHierarchy", businessGroup),Restrictions.or(Restrictions.eq("offshoreDelMgr.employeeId",currentLoggedInUserId),
    			  (Restrictions.eq("deliveryMgr.employeeId", currentLoggedInUserId))));
     
            Criterion c2=Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date()))
                .add(Restrictions.isNull("projectEndDate"));
            criteria.add(Restrictions.and(c1,c2));
      }
      projectIds = criteria.list();
      logger.info("--------ProjectAllocationDaoImpl getProjectIdsForBGAdmin method ends-------");
    } catch (HibernateException hibernateException) {
      logger.error("Exception occured in getProjectIdsForBGAdmin method at DAO layer:-"
          + hibernateException);
      throw hibernateException;
    }
    return projectIds;
  }
  
  public List<Integer> getProjectListByOffshoreAndDelManager(Boolean isActive) {

	    Session session = (Session) getEntityManager().getDelegate();
	    Integer currentLoggedInUserId=0;
	    if(UserUtil.getUserContextDetails()!=null)
	    {
	    currentLoggedInUserId=UserUtil.getUserContextDetails().getEmployeeId();
	    }
	    Criteria criteria =
	        session.createCriteria(Project.class).setProjection(Projections.property("id"));;
	    List<Integer> projectIds = new ArrayList<Integer>();
	    logger.info("--------ProjectAllocationDaoImpl getProjectIdsForBGAdmin method start-------");

	    try {
	    	  Criterion c1=Restrictions.or(Restrictions.or(Restrictions.eq("offshoreDelMgr.employeeId",currentLoggedInUserId),
	    			  (Restrictions.eq("deliveryMgr.employeeId", currentLoggedInUserId))));
	     if(isActive) {
	            Criterion c2=Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date()))
	                .add(Restrictions.isNull("projectEndDate"));
	            criteria.add(Restrictions.and(c1,c2));
	     }
	     else
	    	 criteria.add(c1);
	      
	      projectIds = criteria.list();
	      logger.info("--------ProjectAllocationDaoImpl getProjectIdsForBGAdmin method ends-------");
	    } catch (HibernateException hibernateException) {
	      logger.error("Exception occured in getProjectIdsForBGAdmin method at DAO layer:-"
	          + hibernateException);
	      throw hibernateException;
	    }
	    return projectIds;
	  }
  

  // US3091/US3092: START
  public List<ResourceAllocation> findOpenAllocationsOfResource(Integer empId, Date startDate,OrgHierarchy currentBuId) {

    logger.info("--------DefaultProjectDaoImpl findAll method start-------");

    List<ResourceAllocation> resourceList = new ArrayList<ResourceAllocation>();
    List<ResourceAllocation> resourceAllocationList = new ArrayList<ResourceAllocation>();
    List<DefaultProject> defaultProjectList = new ArrayList<DefaultProject>();
    Session session = (Session) getEntityManager().getDelegate();
    try {

    
      Criteria criteria =
          ((Session) getEntityManager().getDelegate()).createCriteria(ResourceAllocation.class);
      Criterion criterion1 = null;
      criterion1 = Restrictions.and(Restrictions.eq("employeeId.employeeId", empId), Restrictions
          .or(Restrictions.isNull("allocEndDate"), Restrictions.gt("allocEndDate", new Date())));

      criteria.add(criterion1).addOrder(Order.desc("allocationSeq"));
      resourceList = criteria.list();
    
      Query query = session.createQuery(" FROM DefaultProject where orgHierarchy.id=:id");
      query.setParameter("id", currentBuId.getId());
      defaultProjectList = query.list();

      Date defaultProjectStartDate = null;
      Date endDate = null;
      for (int i = 0; i < resourceList.size(); i++) {
        int count = 0;
        for (int j = 0; j < defaultProjectList.size(); j++) {
          if (resourceList.get(i).getProjectId().getId() == defaultProjectList.get(j).getProjectId().getId()) {
            defaultProjectStartDate = resourceList.get(i).getAllocStartDate();
            endDate=resourceList.get(i).getAllocEndDate();
            if(startDate!=null && endDate==null)
				 saveOrupdate(resourceList.get(i),defaultProjectStartDate,startDate);
				 count++;
          }
        }
        if (count == 0) {
          resourceAllocationList.add(resourceList.get(i));
        }
      }

    } 
    catch (HibernateException e) {
      logger.error("Exception occured in findAll method at DAO layer:-" + e);
      throw e;
    }
    logger.info("--------DefaultProjectDaoImpl findAll method end-------");
    return resourceAllocationList;

  }

  public boolean saveOrupdate(ResourceAllocation resourceAllocation, Date defaultProjectStartDate, Date startDate) {

    logger.info("--------ResourceAllocationDaoImpl saveOrUpdate method start-------");
    if (null == resourceAllocation)
      return false;

    Session currentSession = (Session) getEntityManager().getDelegate();

    boolean dateFlag = defaultProjectStartDate.before(startDate);

    resourceAllocation.setLastUpdatedId(UserUtil.getUserContextDetails().getUserName());
    // removed Allocation Hours so setting Allocation Hours default to 0
    resourceAllocation.setAllocHrs(0);

    boolean isSuccess = true;
   
    try 
    {
      Calendar cal = Calendar.getInstance();
      cal.setTime(startDate);
      cal.add(Calendar.DATE, -1); // minus number would decrement the days
      Date priviousDate = cal.getTime();

      if (dateFlag) 
    	  resourceAllocation.setAllocEndDate(priviousDate);
      else
    	  resourceAllocation.setAllocEndDate(defaultProjectStartDate);

      // Future timesheet deletion : Start

      int resAllocationId = resourceAllocation.getId();
      Date weekEndDate = resourceAllocation.getAllocEndDate();

      List<UserActivity> userActivityList = resourceAllocDao.isFutureTimesheetpresent(resAllocationId, weekEndDate);
      List<Integer> userActivityIdlist = new ArrayList<Integer>();

      if (!userActivityList.isEmpty()) {
        for (int i = 0; i < userActivityList.size(); i++) {
          userActivityIdlist.add(userActivityList.get(i).getId());
        }
      }
      
      DateFormat df = new SimpleDateFormat(Constants.DATE_PATTERN_4);
      String weekEndDateFormatted = df.format(weekEndDate);

      if (!userActivityIdlist.isEmpty()) {
        resourceAllocDao.deleteFutureTimesheet(userActivityIdlist, "" + resAllocationId, weekEndDateFormatted);
      }
      // Future timesheet deletion : End
      
     
      currentSession.update(resourceAllocation);
    } 
    catch (HibernateException e) {
      isSuccess = false;
      logger.error("Exception occured in saveOrUpdate method at DAO layer:-" + e);
      throw e;
    }
    catch (Exception ex) {
      isSuccess = false;
      logger.error("Exception occured in saveOrUpdate method at DAO layer:-" + ex);
    }
    
    logger.info("--------ResourceAllocationDaoImpl saveOrUpdate method End-------");
    return isSuccess;
  }

  @SuppressWarnings("unchecked")
  public List<ResourceAllocation> findOpenAllocationsOfCopiedResource(Integer[] empId) {
    logger.info("-------- findOpenAllocationsOfCopiedResource method start-------");
    List<ResourceAllocation> resourceAllocationList = new ArrayList<ResourceAllocation>();
    Map<String, List<ResourceAllocation>> resourceAllocationMap =
        new HashMap<String, List<ResourceAllocation>>();
    try {

      Criteria criteria =
          ((Session) getEntityManager().getDelegate()).createCriteria(ResourceAllocation.class);
      Criterion criterion1 = null;
      criterion1 = Restrictions.and(Restrictions.in("employeeId.employeeId", empId), Restrictions
          .or(Restrictions.isNull("allocEndDate"), Restrictions.gt("allocEndDate", new Date())));
      criteria.add(criterion1);
      resourceAllocationList = criteria.list();

      if (null != resourceAllocationList && !resourceAllocationList.isEmpty()) {

        for (Integer employeeId : empId) {
          List<ResourceAllocation> resourceAllocation = new ArrayList<ResourceAllocation>();
          for (ResourceAllocation resAlloc : resourceAllocationList) {
            if (resAlloc.getEmployeeId().getEmployeeId().intValue() == employeeId.intValue()) {
              resourceAllocation.add(resAlloc);
            }
          }
          if (null != resourceAllocation && !resourceAllocation.isEmpty())
            resourceAllocationMap.put(resourceAllocation.get(0).getEmployeeId().getEmployeeName(),
                resourceAllocation);
          resourceAllocation = null;

        }
      }

    } catch (HibernateException e) {
      logger.error("Exception occured in findAll method at DAO layer:-" + e);
      throw e;
    }
    logger.info("--------DefaultProjectDaoImpl findAll method end-------");
    return resourceAllocationList;
  }

  public List<Project> getProjectsForUser( List<OrgHierarchy> businessGroup, Integer currentLoggedInUserId, String activeOrAll) {

	    logger.info("--------ProjectAllocationDaoImpl findAll method start-------");
	    Session session = (Session) getEntityManager().getDelegate();
	    Criteria criteria = session.createCriteria(Project.class);
	    Criterion isActive=null;
	    Criterion isCurrentLoggedInUser=null;
	    Criterion isBusinessGroup=null;
	    List<Project> projects = new ArrayList<Project>();
	    try {

	      if (businessGroup != null && !businessGroup.isEmpty()) {
	        //criteria.add(Restrictions.in("orgHierarchy", businessGroup));
	       isBusinessGroup=Restrictions.in("orgHierarchy", businessGroup);
	      }

	      if (activeOrAll.equals("active")) {
	        /*criteria.add(Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date()))
	            .add(Restrictions.isNull("projectEndDate")));*/
	        isActive=Restrictions.or(Restrictions.ge("projectEndDate", new Date()),(Restrictions.isNull("projectEndDate")));
	        
	      }

	      if (currentLoggedInUserId != null) {
		        //criteria.add(Restrictions.or(Restrictions.eq("deliveryMgr.employeeId", currentLoggedInUserId),Restrictions.eq("offshoreDelMgr.employeeId", currentLoggedInUserId)));
		        isCurrentLoggedInUser=Restrictions.or(Restrictions.eq("offshoreDelMgr.employeeId",currentLoggedInUserId),
		    			  (Restrictions.eq("deliveryMgr.employeeId", currentLoggedInUserId)));
		    }
	      
	      if(isCurrentLoggedInUser!=null && isBusinessGroup!=null && isActive!=null)//gor bgadmin role
	        {
	        criteria.add(Restrictions.and(Restrictions.or(Restrictions.in("orgHierarchy", businessGroup),Restrictions.or(Restrictions.eq("offshoreDelMgr.employeeId",currentLoggedInUserId),
	    			  (Restrictions.eq("deliveryMgr.employeeId", currentLoggedInUserId)))),Restrictions.or(Restrictions.ge("projectEndDate", new Date()),(Restrictions.isNull("projectEndDate")))));
	        }
	      else if(isCurrentLoggedInUser==null && isBusinessGroup!=null && isActive!=null)//for del mgr or mgr role
	        {
	        	System.err.println("inside del manager 222222222222");
	        	criteria.add(Restrictions.and(isBusinessGroup,isActive));
	        }
	        else if(isCurrentLoggedInUser!=null && isBusinessGroup!=null && isActive==null)
	        {
	        	criteria.add(Restrictions.or(isCurrentLoggedInUser,isBusinessGroup));
	        }else if((isCurrentLoggedInUser!=null && isBusinessGroup==null) && isActive!=null)//for del mgr or mgr role
	        {
	        	System.out.println("inside del manager");
	        	criteria.add(Restrictions.and(isCurrentLoggedInUser,isActive));
	        }else if((isCurrentLoggedInUser==null || isBusinessGroup==null) && isActive!=null)
	        {
	        	criteria.add(isActive);
	        }
	      projects = criteria.list();

	    } catch (HibernateException hibernateException) {
	      logger.error("Exception occured in findAll method at DAO layer:-" + hibernateException);
	      throw hibernateException;
	    }
	    return projects;
	  }
  
  // US3091/US3092: END
}
