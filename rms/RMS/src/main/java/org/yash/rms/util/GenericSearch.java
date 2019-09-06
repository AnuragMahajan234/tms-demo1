package org.yash.rms.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;

@Component
public class GenericSearch {
	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public  List<org.yash.rms.domain.Resource> getObjectsWithSearchAndPaginationForResource(String userInput){
		
		Session session = (Session) getEntityManager().getDelegate();
		List<Resource> resources = new ArrayList<Resource>();
		try {
			Criteria criteriamain = session.createCriteria(Resource.class);
			Criteria criteria = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
			
			userInput = userInput.replaceAll(" ", "%");
			criteria.add(Restrictions.or(Restrictions.ge("releaseDate", new Date()),Restrictions.isNull("releaseDate")));

			criteria.add(ConcatenableIlikeCriterion.ilike(userInput,
					MatchMode.ANYWHERE, "firstName", "middleName","lastName","emailId","yashEmpId"));
					criteria.setFirstResult(0);
					criteria.setMaxResults(20);
			resources=criteria.list();
			if(resources.isEmpty())
			{
				return new ArrayList<Resource>();
			}
			
			resources =criteriamain.add(Restrictions.in("employeeId", resources)).list();
			/*criteria.addOrder(Order.asc("employeeId"));
			criteria.setFirstResult(0);
			criteria.setMaxResults(10);*/
		/*	
			resources = criteria.add(Restrictions.disjunction().
					add(Restrictions.like("emailId", "%"+userInput+"%")).
					add(Restrictions.like("firstName", "%"+userInput+"%")).
					add(Restrictions.like("middleName", "%"+userInput+"%")).
					add(Restrictions.like("lastName", "%"+userInput+"%"))).
					add(Restrictions.or(Restrictions.ge("releaseDate", new Date()),Restrictions.isNull("releaseDate"))).list();
*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resources;
		
	}
	
public  List<RequestRequisitionSkill> getObjectsWithSearchAndPaginationForRRFName(String userInput){
		
		Session session = (Session) getEntityManager().getDelegate();
		List<RequestRequisitionSkill> requestRequisitionSkills = new ArrayList<RequestRequisitionSkill>();
		try {
			Criteria criteriamain = session.createCriteria(RequestRequisitionSkill.class);
			Criteria criteria = session.createCriteria(RequestRequisitionSkill.class).setProjection(Projections.property("id"));
			
			userInput = userInput.replaceAll(" ", "%");
			criteria.add(Restrictions.or(Restrictions.eq("isDeleted", false)));

			criteria.add(ConcatenableIlikeCriterion.ilike(userInput,
					MatchMode.ANYWHERE, "requirementId"));
					criteria.setFirstResult(0);
					criteria.setMaxResults(20);
					requestRequisitionSkills=criteria.list();
			if(requestRequisitionSkills.isEmpty())
			{
				return new ArrayList<RequestRequisitionSkill>();
			}
			
			requestRequisitionSkills =criteriamain.add(Restrictions.in("id", requestRequisitionSkills)).list();
			/*criteria.addOrder(Order.asc("employeeId"));
			criteria.setFirstResult(0);
			criteria.setMaxResults(10);*/
		/*	
			resources = criteria.add(Restrictions.disjunction().
					add(Restrictions.like("emailId", "%"+userInput+"%")).
					add(Restrictions.like("firstName", "%"+userInput+"%")).
					add(Restrictions.like("middleName", "%"+userInput+"%")).
					add(Restrictions.like("lastName", "%"+userInput+"%"))).
					add(Restrictions.or(Restrictions.ge("releaseDate", new Date()),Restrictions.isNull("releaseDate"))).list();
*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return requestRequisitionSkills;
		
	}
	
	public List<Resource> getObjectsWithSearchAndPaginationForResourceByRole(String userInput,
			String role) {
		
		Session session = (Session) getEntityManager().getDelegate();
		List<Resource> resources = new ArrayList<Resource>();
		try {
			Criteria criteriamain = session.createCriteria(Resource.class);
			Criteria criteria = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
			
			userInput = userInput.replaceAll(" ", "%");
			criteria.add(Restrictions.or(Restrictions.ge("releaseDate", new Date()),Restrictions.isNull("releaseDate")));
			criteria.add(Restrictions.eq("userRole", role));
			
			criteria.add(ConcatenableIlikeCriterion.ilike(userInput,
					MatchMode.ANYWHERE, "firstName", "middleName","lastName","emailId","yashEmpId"));
					criteria.setFirstResult(0);
					criteria.setMaxResults(20);
			resources=criteria.list();
			if(resources.isEmpty())
			{
				return new ArrayList<Resource>();
			}
			
			resources = criteriamain.add(Restrictions.in("employeeId", resources)).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resources;
	}
	
	
	public List<Resource> getObjectsWithSearchAndPaginationForBusinessGroupAdmin(String userInput, List<OrgHierarchy> businessGroup) {
		
		Session session = (Session) getEntityManager().getDelegate();
		List<Resource> resources = new ArrayList<Resource>();
		try {
			Integer loggedInUser=0;
			if(UserUtil.getUserContextDetails()!=null)
			{
				loggedInUser=UserUtil.getUserContextDetails().getEmployeeId();
			}
			
			Criteria criteriamain = session.createCriteria(Resource.class);
			Criteria criteria = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
			

			criteria.add(Restrictions.or(Restrictions.ge("releaseDate", new Date()),Restrictions.isNull("releaseDate")));
			
			Criterion criterion = Restrictions.or(Restrictions.in("currentBuId", businessGroup),
					Restrictions.or(Restrictions.eq("currentReportingManager.employeeId", loggedInUser),
					Restrictions.eq("currentReportingManagerTwo.employeeId", loggedInUser)));
			criteria.add(criterion);
			
			userInput = userInput.replaceAll(" ", "%");
			criteria.add(ConcatenableIlikeCriterion.ilike(userInput,
					MatchMode.ANYWHERE, "firstName", "middleName","lastName","emailId","yashEmpId"));
					criteria.setFirstResult(0);
					criteria.setMaxResults(20);
					
			resources=criteria.list();
			if(resources.isEmpty())
			{
				return new ArrayList<Resource>();
			}
			resources = criteriamain.add(Restrictions.in("employeeId", resources)).list();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resources;
	}	
}
