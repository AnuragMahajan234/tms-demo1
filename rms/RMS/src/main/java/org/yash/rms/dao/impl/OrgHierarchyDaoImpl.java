package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.OrgHierarchyDao;
import org.yash.rms.domain.BGAdmin_Access_Rights;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;
import org.yash.rms.util.HibernateUtil;
import org.yash.rms.util.UserUtil;

@Repository("OrgHierarchyDaoImpl")
@Transactional
public class OrgHierarchyDaoImpl implements OrgHierarchyDao {


@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory.getLogger(OrgHierarchyDaoImpl.class);
	

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		logger.info("------------OrgHierarchyDaoImpl delete method start------------");
		boolean isSuccess = true;
		try {

			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(Resource.class);

			criteria.add(Restrictions.eq("currentBuId.id", id));
			if(criteria.list().size()==0){
			Query query = ((Session) getEntityManager().getDelegate()).createQuery(
					" UPDATE OrgHierarchy b set b.active=0 where b.id = :id");
			query.setParameter("id", id);

			int totalRowsDeleted = query.executeUpdate();
			}
			else
				isSuccess = false;

		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while softdeleting OrgHierarchy "+id+e.getMessage());
			 throw e;
		}  finally {
			// currentSession.close();
		}
		logger.info("------------OrgHierarchyDaoImpl delete method end------------");
		return isSuccess;
	}

	public boolean saveOrupdate(OrgHierarchy orgHierarchy) {
		// TODO Auto-generated method stub
		logger.info("--------OrgHierarchyDaoImpl saveOrUpdate method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		if (null == orgHierarchy)
			return false;
		orgHierarchy.setCreationDate(new Date());
			OrgHierarchy orghierforactive =null;
		 OrgHierarchy parent = findOrgHierarchiesById(orgHierarchy.getParentId().getId());
	        orgHierarchy.setParentId(parent);
	        if (orgHierarchy.getId() == null) {
	        orgHierarchy.setActive(true);
	        }else{
	        	orghierforactive = findOrgHierarchiesById(orgHierarchy.getId());
	        	orgHierarchy.setActive(orghierforactive.isActive());
	        	
	        }
	        List<OrgHierarchy> childHierarchies = new ArrayList<OrgHierarchy>();
			if (orgHierarchy.getId() != null) {
			 Criteria criteria = session.createCriteria(OrgHierarchy.class);
			criteria.add(Restrictions.eq("parentId", orgHierarchy));
			 childHierarchies=criteria.list();
			 Set<OrgHierarchy> childorgHierarchies = new HashSet<OrgHierarchy>();
			 if(childHierarchies!=null)
				 for(OrgHierarchy hierarchy: childHierarchies){
					 childorgHierarchies.add(hierarchy);
			}
			 orgHierarchy.setChildOrgHierarchies(childorgHierarchies);}
			boolean isSuccess=true;
			try {

			if (orgHierarchy.getId() == null) {
				orgHierarchy.setCreationDate(new Date());
				orgHierarchy.setCreatedId(UserUtil.getCurrentResource().getUsername());
			}
			orgHierarchy.setLastUpdatedId(UserUtil.getCurrentResource().getUsername());			
				
				isSuccess =session.merge(orgHierarchy)!=null?true:false;
						
			} catch (HibernateException e) {
				isSuccess = false;
				e.printStackTrace();
				logger.error("Exception Occurred while saving OrgHierarchy "+orgHierarchy.getName()+e.getMessage());
				 throw e;
			}   finally {
//				currentSession.close();
		}
			logger.info("------OrgHierarchyDaoImpl saveOrUpate method end-----");
		return isSuccess;
	}

	public List<OrgHierarchy> findAll() {
		// TODO Auto-generated method stub
		logger.info("--------OrgHierarchyDaoImpl findAll method start-------");
		List<OrgHierarchy> orgHierarchyList = new ArrayList<OrgHierarchy>();
		Session session = (Session) getEntityManager().getDelegate();
		try {
			orgHierarchyList = session.createCriteria(OrgHierarchy.class)
					.list();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			 logger.error("EXCEPTION CAUSED IN GETTING ORGHIERARCHY LIST " + e.getMessage());
			 throw e;
		} finally {

		}
		logger.info("--------OrgHierarchyDaoImpl findAll method end-------");
		return orgHierarchyList;

	}

	public List<OrgHierarchy> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public OrgHierarchy findOrgHierarchiesById(Integer id) {
		// TODO Auto-generated method stub
		logger.info("--------OrgHierarchyDaoImpl findOrgHierarchiesById method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		OrgHierarchy orgHierarchy=null;
		//session.enableFilter("getActiverecord").setParameter("active", true);
		try{
		Criteria criteria = session.createCriteria(OrgHierarchy.class);
		criteria.add(Restrictions.eq("id", id));
		  orgHierarchy = (OrgHierarchy) criteria.uniqueResult();
		  if(orgHierarchy.getChildOrgHierarchies()!=null){
		  Set<OrgHierarchy> childSet= orgHierarchy.getChildOrgHierarchies();
		  if(orgHierarchy.getParentId()==null)
		  {
			
			 for(OrgHierarchy childs:childSet)
			 {
				 childs.setMoveLink(false);
			 }
		  }
		  else
			  for(OrgHierarchy childs:childSet)
				 {
					 childs.setMoveLink(true);
				 }
		  
		  if(!orgHierarchy.isActive())
		  {
			  for(OrgHierarchy childs:childSet)
				 {
					 childs.setParentDeactive(true);
				 }
		  }
		  else
		  {
			  for(OrgHierarchy childs:childSet)
				 {
					 childs.setParentDeactive(false);
				 }
		  }
		  }
		}
		catch(HibernateException e){
			e.printStackTrace();
			logger.error("EXCEPTION CAUSED IN GETTING OrgHierarchy By Id " +id+ e.getMessage());
			 throw e;
		}
		 finally{
			
		}
		return orgHierarchy;
	}

	public boolean move(int id, int newParentId) {
		// TODO Auto-generated method stub
		logger.info("--------OrgHierarchyDaoImpl move method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		boolean isSuccess = false;
		OrgHierarchy orgHierarchy = findOrgHierarchiesById(id);
		OrgHierarchy oldParentOrg = orgHierarchy.getParentId();
		orgHierarchy.setParentId(findOrgHierarchiesById(newParentId));
		try {
			isSuccess = session.merge(orgHierarchy) != null ? true : false;
		} catch (HibernateException ex) {
			isSuccess = false;
			ex.printStackTrace();
			logger.error("EXCEPTION CAUSED IN MOVING OrgHierarchy " +id+"From "+oldParentOrg.getId()+"to "+newParentId+ ex.getMessage());
			 throw ex;
		}  finally {
			// currentSession.close();
		}
		return isSuccess;
	}

	public Map<Integer, String> showHierarhicalPath(int id) {
		// TODO Auto-generated method stub
		logger.info("--------OrgHierarchyDaoImpl showHierarhicalPath method start-------");
		OrgHierarchy bu=findOrgHierarchiesById(id);
		Session session = (Session) getEntityManager().getDelegate();
		
		Criteria criteria1 = session.createCriteria(OrgHierarchy.class);
		criteria1.add(Restrictions.isNull("parentId"));
		OrgHierarchy organization = (OrgHierarchy) criteria1.uniqueResult();
		session.enableFilter("getActiverecord").setParameter("active", true);
		Map<Integer, String> pathList = new HashMap<Integer, String>();
		try{
			
		Criteria criteria = session.createCriteria(OrgHierarchy.class);
		
		criteria.add(Restrictions
				.conjunction().add(
		//Restrictions.like("name", "BG%")).add(Restrictions.ne("id", bu.getParentId().getId())));
		Restrictions.eq("parentId",organization )).add(Restrictions.ne("id", bu.getParentId().getId())));
		//List<OrgHierarchy> list = findAllBGs();
		
		List<OrgHierarchy> list=criteria.list();
		
		Iterator<OrgHierarchy> iterator = list.iterator();
		while (iterator.hasNext()) {
			String path = "";
			OrgHierarchy orgHierarchy = (OrgHierarchy) iterator.next();
			if(!validateBG(orgHierarchy.getId(), bu.getName().toUpperCase())){
			path = path + getPath(orgHierarchy) + "/" + orgHierarchy.getName();
			pathList.put(orgHierarchy.getId(), path);}
		}
		}catch(HibernateException ex)
		{
			ex.printStackTrace();
			logger.error("EXCEPTION CAUSED IN showHierarhicalPath method..."+ ex.getMessage());
			 throw ex;
		}
		 
		return pathList;
	}

	public String getPath(OrgHierarchy orghierarchy) {

		String str = "";
		if (orghierarchy.getParentId() != null) {
			str = str + orghierarchy.getParentId().getName();
			if (orghierarchy.getParentId().getId() != null) {
				str = getPath(orghierarchy.getParentId()) + "/" + str;
			}

		}

		return str;
	}

	public List<OrgHierarchy> findAllBus() {
		logger.info("------- OrgHierarchyDaoImpl findAllBus method start--------");
		Session session = (Session) getEntityManager().getDelegate();
		session.enableFilter("getActiverecord").setParameter("active", true);
		List<OrgHierarchy> list= new ArrayList<OrgHierarchy>();
		try{
		Criteria criteria = session.createCriteria(OrgHierarchy.class);
		criteria.add(Restrictions.eq("name", "ORGANIZATION"));
		//criteria.add(Restrictions.like("name", "BU%"));
		list=criteria.list();
		criteria = session.createCriteria(OrgHierarchy.class);
		criteria.add(Restrictions.in("parentId", list));
		list=criteria.list();
		criteria = session.createCriteria(OrgHierarchy.class);
		criteria.add(Restrictions.in("parentId", list));
		list=criteria.list();
		}
		catch(HibernateException ex)
		{
			ex.printStackTrace();
			logger.error("EXCEPTION CAUSED IN GETTING BUSLIST  " + ex.getMessage());
			 throw ex;
		}
		logger.info("------- OrgHierarchyDaoImpl findAllBus method end--------");
		return list;
	}
	
	public List<OrgHierarchy> findAllBGs(){
		logger.info("------- OrgHierarchyDaoImpl findAllBGs method start--------");
		List<OrgHierarchy> orgHierarchies=null; 
		try{
			Session session = (Session) getEntityManager().getDelegate();
			session.enableFilter("getActiverecord").setParameter("active", true);
			Criteria criteria = session.createCriteria(OrgHierarchy.class);
			criteria.add(Restrictions.eq("name", "ORGANIZATION"));
			//criteria.add(Restrictions.like("name", "BU%"));
			orgHierarchies=criteria.list();
			criteria = session.createCriteria(OrgHierarchy.class);
			criteria.add(Restrictions.in("parentId", orgHierarchies));
			//list=criteria.list();
			
			//	criteria.add(Restrictions.like("name", "BG%"));
			orgHierarchies = criteria.list();
		}catch(HibernateException ex){
			ex.printStackTrace();
			logger.error("HibernateException occured in findAllBGs method at DAO layer:-"+ex);
			throw ex;
		}catch(RuntimeException re){
			re.printStackTrace();
			logger.error("RuntimeException occured in findAllBGs method at DAO layer:-"+re);
			throw re;
		}
		logger.info("------- OrgHierarchyDaoImpl findAllBGs method end--------");
		return orgHierarchies;
	}

	public boolean validateBG(Integer parentId, String BGName) {
		// TODO Auto-generated method stub
		Session session = (Session) getEntityManager().getDelegate();
		OrgHierarchy orgHierarachy=null;
		Criteria criteria = session.createCriteria(OrgHierarchy.class);
		criteria.add(Restrictions
				.conjunction()
				.add(Restrictions
						.eq("name",
								BGName))
				.add(Restrictions.eq("parentId.id",
						parentId)));
		if(criteria.list().size()>0)
		orgHierarachy= (OrgHierarchy) criteria.list().get(0);
		if(orgHierarachy!=null)
		return true;
		else
			return false;
	}

	public boolean activateOrDeactivateOrgHierarchyChild(int id,
			boolean activateStatus) {
		// TODO Auto-generated method stub
		logger.info("------------OrgHierarchyDaoImpl delete method start------------");
		boolean isSuccess = true;
		try {

			 
 
				Query query=null;
				if(activateStatus){
			 query = ((Session) getEntityManager().getDelegate()).createQuery(
					" UPDATE OrgHierarchy b set b.active= :actiavte where b.id = :id");
			query.setParameter("actiavte", activateStatus);
			query.setParameter("id", id);
			
				}
				else
				{
					query=((Session) getEntityManager().getDelegate()).createQuery(" UPDATE OrgHierarchy b set b.active= :actiavte where b.id = :id or b.parentId.id= :parentId");
					query.setParameter("actiavte", activateStatus);
					query.setParameter("id", id);
					query.setParameter("parentId", id);
				}
			int totalRowsDeleted = query.executeUpdate();
			 

		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while softdeleting OrgHierarchy "+id+e.getMessage());
			 throw e;
		}  finally {
			// currentSession.close();
		}
		logger.info("------------OrgHierarchyDaoImpl delete method end------------");
		return isSuccess;
	}

	public Long countResource(int id) {
		// TODO Auto-generated method stub
		logger.info("--------ResourceDaoImpl findResourcesByBusinessGroup method start-------");
		try {
			OrgHierarchy org=findOrgHierarchiesById(id);
			Long totalResult=0L;
			if(org.getChildOrgHierarchies()!=null && org.getChildOrgHierarchies().size()>0){
Set<OrgHierarchy> buList=org.getChildOrgHierarchies();
  totalResult = (Long)((Session) getEntityManager().getDelegate())
.createCriteria(Resource.class).add(Restrictions.in("currentBuId", buList)).setProjection(Projections.rowCount()).uniqueResult();

			}
			else{
		  totalResult = (Long)((Session) getEntityManager().getDelegate())
				.createCriteria(Resource.class).add(Restrictions.eq("currentBuId.id", id)).setProjection(Projections.rowCount()).uniqueResult();
			}
		//criteria.add(Restrictions.eq("currentBuId.id", id));
		System.out.print(totalResult);
		logger.info("--------ResourceDaoImpl findResourcesByBusinessGroup method end-------");
		return totalResult;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourcesByBusinessGroup method at DAO layer:-"
					+ ex);
			throw ex;
		}
	}

	public OrgHierarchy findOrgHierarchyList() {
		// TODO Auto-generated method stub
		
		Session session = (Session) getEntityManager().getDelegate();
		OrgHierarchy orgHierarchy=null;
		try{
			Criteria criteria = session.createCriteria(OrgHierarchy.class);
			criteria.add(Restrictions.isNull("parentId"));
			orgHierarchy = (OrgHierarchy) criteria.uniqueResult();
			 //Set<OrgHierarchy> childSet= orgHierarchy.getChildOrgHierarchies();
			/*for(OrgHierarchy childs:childSet)
			 {
				 childs.setMoveLink(false);
			 }*/
			}
		catch(HibernateException ex)
		{
			logger.error("Exception occured in findResourcesByBusinessGroup method at DAO layer:-"
					+ ex);
			throw ex;
		
		}
		return orgHierarchy;
	}
	
	
	//added for US:3119
	
	public List<OrgHierarchy> findAllBusForBGADMIN() {
		logger.info("------- OrgHierarchyDaoImpl findAllBus method start--------");
		Session session = (Session) getEntityManager().getDelegate();
		session.enableFilter("getActiverecord").setParameter("active", true);
	//	List<OrgHierarchy> tempList= new ArrayList<OrgHierarchy>();
		List<OrgHierarchy> list= new ArrayList<OrgHierarchy>();
		List<BGAdmin_Access_Rights> access_Rights= new ArrayList<BGAdmin_Access_Rights>();
		try{
			if (UserUtil.isCurrentUserIsBusinessGroupAdmin()) {
			Integer currentLoggedInUserId = UserUtil.getUserContextDetails().getEmployeeId();
			/*Criteria criteria1 = session.createCriteria(BGAdmin_Access_Rights.class);
			criteria1.add(Restrictions.eq("resourceId", currentLoggedInUserId));
			access_Rights=criteria1.list();*/
			Query query = session.createQuery("Select orgId.id   FROM BGAdmin_Access_Rights WHERE resourceId.employeeId = :employeeId");
			query.setParameter("employeeId", currentLoggedInUserId);
			access_Rights= query.list();
			
			
			
			
		Criteria criteria = session.createCriteria(OrgHierarchy.class);
		criteria.add(Restrictions.eq("name", "ORGANIZATION"));
		//criteria.add(Restrictions.like("name", "BU%"));
		list=criteria.list();
		criteria = session.createCriteria(OrgHierarchy.class);
		criteria.add(Restrictions.in("parentId", list));
		list=criteria.list();
		criteria = session.createCriteria(OrgHierarchy.class);
		criteria.add(Restrictions.in("parentId", list)).add(Restrictions.in("id", access_Rights));
		list=criteria.list();
		
			}else{
				
				Criteria criteria = session.createCriteria(OrgHierarchy.class);
				criteria.add(Restrictions.eq("name", "ORGANIZATION"));
				//criteria.add(Restrictions.like("name", "BU%"));
				list=criteria.list();
				criteria = session.createCriteria(OrgHierarchy.class);
				criteria.add(Restrictions.in("parentId", list));
				list=criteria.list();
				criteria = session.createCriteria(OrgHierarchy.class);
				criteria.add(Restrictions.in("parentId", list));
				list=criteria.list();
			}
		
		
		
		
		
		}
		catch(HibernateException ex)
		{
			ex.printStackTrace();
			logger.error("EXCEPTION CAUSED IN GETTING BUSLIST  " + ex.getMessage());
			 throw ex;
		}
		logger.info("------- OrgHierarchyDaoImpl findAllBus method end--------");
		return list;
	}

	public OrgHierarchy getOrgHierarchyByName(String name) {
		logger.info("------- OrgHierarchyDaoImpl getOrgHierarchyByName method start--------");
		
		Session session = (Session) getEntityManager().getDelegate();
		OrgHierarchy orgHierarchy = new OrgHierarchy();
		try {
			Criteria criteria = session.createCriteria(OrgHierarchy.class).add(Restrictions.eq("name", name));
			orgHierarchy = (OrgHierarchy) criteria.uniqueResult();
		}catch (Exception e) {
			logger.error("Exception in OrgHierarchyDaoImpl getOrgHierarchyByName method ", e);
		}
		logger.info("------- OrgHierarchyDaoImpl getOrgHierarchyByName method end--------");
		
		return orgHierarchy;
	}

	public OrgHierarchy getOrgHierarchyByBgIdBuName(Integer bg, String buname) {
		logger.info("------- OrgHierarchyDaoImpl getOrgHierarchyByBgBuId method start--------");
		
		Session session = (Session) getEntityManager().getDelegate();
		OrgHierarchy orgHierarchy = new OrgHierarchy();
		
		try {
			Query query =session.createQuery("FROM OrgHierarchy as oh WHERE oh.parentId.id=:bgId and oh.name=:buname");
			query.setInteger("bgId", bg);
			query.setString("buname", buname);
			orgHierarchy = (OrgHierarchy) query.uniqueResult();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		logger.info("------- OrgHierarchyDaoImpl getOrgHierarchyByBgBuId method end--------");
		return orgHierarchy;
	}

	public Integer findBUIdByBGBUName(final String BGNAme, final String BUName) {
		logger.info("------- OrgHierarchyDaoImpl @FindBUIdByBGBUName method start--------");
		Integer BUId = 0;
		try{
			Session session = (Session) getEntityManager().getDelegate();
			if(session != null){
				Criteria criteria = session.createCriteria(OrgHierarchy.class,"BU");
				criteria.createAlias("BU.parentId", "BG");
				
				criteria.add(Restrictions.and(Restrictions.eq("BU.name", BUName),Restrictions.eq("BG.name", BGNAme)));
				criteria.setProjection(Projections.property("BU.id"));
				BUId = (Integer) criteria.uniqueResult();
				if(BUId == null){
					BUId = 0;
				}
			}
			else{
				logger.error("Session closed");
			}
		}
		catch (Exception e) {
			BUId = 0;
			logger.error("Exception in OrgHierarchyDaoImpl @FindBUIdByBGBUName method " + e.getMessage());
			e.printStackTrace();
		}
		logger.info("------- OrgHierarchyDaoImpl @FindBUIdByBGBUNam method end--------");
		return BUId;
	}

}
