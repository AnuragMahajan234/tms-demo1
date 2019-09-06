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
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.EventDao;
import org.yash.rms.domain.Event;
import org.yash.rms.util.UserUtil;
@Transactional
@Repository("eventDao")
public class EventDaoImpl implements EventDao {
	private static final Logger logger = LoggerFactory.getLogger(EventDaoImpl.class);

  

@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> findAll() {
		logger.info("--------EventDaoImpl findAll method start-------");

		List<Event> eventList = new ArrayList<Event>();
		List<Event> events = new ArrayList<Event>();
		
		Session session=(Session) getEntityManager().getDelegate();
		
		try {
			eventList = session.createQuery("FROM Event").list(); 
			for (Event event : eventList) {
				System.out.println(event.getId());
				if (event.getId() > 0 ) {
					events.add(event);
				} 
			}
		
		} catch (HibernateException e) {
			 logger.error("Exception occured in findAll method at DAO layer:-"+e);
	         e.printStackTrace(); 
	      }finally {
	          
	      }
		logger.info("------------EventDaoImpl findAll method end------------");

		return events ;
	}

		@SuppressWarnings("unused")
		public boolean saveOrupdate(Event event) {
			logger.info("------EventDaoImpl saveOrUpdate method start------");
			if(null == event) return false;
			
			Session currentSession = (Session) getEntityManager().getDelegate();
			Transaction transaction=null;
			boolean isSuccess=true;
			try {
				populateEvent(event);
				if(event.getId()==0)
				{
					event.setCreatedId( UserUtil.getUserContextDetails().getUserName());
					event.setCreationTimestamp(new Date());
				}
				
				event.setLastUpdatedId( UserUtil.getUserContextDetails().getUserName());
				event.setLastupdatedTimestamp(new Date(System.currentTimeMillis()));
				isSuccess = currentSession.merge(event)!=null?true:false;
			} catch (HibernateException e) {
				if (transaction != null) {
				}
				isSuccess = false;
				logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
				e.printStackTrace();
			} catch (Exception ex) {
				if (transaction != null) {
				}
				isSuccess = false;
				logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+ex);
				ex.printStackTrace();
			} finally {

			}
			logger.info("------EventDaoImpl saveOrUpate method end-----");
			return isSuccess;
		}
		
		public boolean delete(int id) {
			logger.info("------EventDaoImpl delete method start------");
			boolean isSuccess=true;
			try {

				 Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(Event.DELETE_EVENT_BASED_ON_ID).setInteger("id", id);
				 int totalRowsDeleted = query.executeUpdate();
				System.out.println("Total Rows Deleted::" + totalRowsDeleted);

			} catch (HibernateException e) {
			
				isSuccess = false;
				logger.error("HibernateException occured in delete method at DAO layer:-"+e);
				e.printStackTrace();
				 throw e;
			} catch (Exception ex) {
				
				isSuccess = false;
				logger.error("HibernateException occured in delete method at DAO layer:-"+ex);
				ex.printStackTrace();
			} finally {

			}
			logger.info("------EventDaoImpl delete method end------");
			return isSuccess;
		}
		


		public List<Event> findByEntries(int firstResult, int sizeNo) {
			return null;
		}

		public long countTotal() {
			return 0;
		}

		public Event findById(int id) {
			// TODO Auto-generated method stub
			Event event= null;
			logger.info("--------EventDaoImpl findById method start-------");
			Session session=(Session) getEntityManager().getDelegate();
			try {
				event = (Event)session.createCriteria(Event.class).add(Restrictions.eq("id", id)).uniqueResult(); 
				populateEvent(event);
			} catch (HibernateException e) {
		         e.printStackTrace(); 
		         logger.error("EXCEPTION CAUSED IN GETTING EVENT By Id " +id+ e.getMessage());
		         throw e;
		     }finally {
		         //session.close(); 
		      }
			logger.info("------EventDaoImpl findById method end-----");
			return event ;
		}
		
		public Event findByName(String name) {
			// TODO Auto-generated method stub
			Event event= null;
			logger.info("--------EventDaoImpl findByName method start-------");
			Session session=(Session) getEntityManager().getDelegate();
			try {
				event = (Event)session.createCriteria(Event.class).add(Restrictions.eq("event", name)).uniqueResult(); 
				populateEvent(event);
			} catch (HibernateException e) {
		         e.printStackTrace(); 
		         logger.error("EXCEPTION CAUSED IN GETTING EVENT By Id " +name+ e.getMessage());
		         throw e;
		     }finally {
		         //session.close(); 
		      }
			logger.info("------EventDaoImpl findByName method end-----");
			return event ;
		}
		
		private void populateEvent(Event event) {
			
			if(event!=null){
			if (event.getEmployeeId() == null) {
				event.setEmployeeId('N');
			}
			if (event.gethRCommentsTimestamp() == null) {
				event.sethRCommentsTimestamp('N');
			}
			if (event.gethRName() == null) {
				event.sethRName('N');
			}
			if (event.gethRComments() == null) {
				event.sethRComments('N');
			}
			if (event.getbUCommentsTimestamp() == null) {
				event.setbUCommentsTimestamp('N');
			}
			if (event.getbUHComments() == null) {
				event.setbUHComments('N');
			}
			if (event.getbUHName() == null) {
				event.setbUHName('N');
			}
			if (event.getbGCommentsTimestamp() == null) {
				event.setbGCommentsTimestamp('N');
			}
			if (event.getbGHComments() == null) {
				event.setbGHComments('N');
			}
			if (event.getbGHName() == null) {
				event.setbGHName('N');
			}
			if (event.getTransferDate() == null) {
				event.setTransferDate('N');
			}
			if (event.getReleaseDate() == null) {
				event.setReleaseDate('N');
			}
			if (event.getPenultimateAppraisal() == null) {
				event.setPenultimateAppraisal('N');
			}
			if (event.getLastAppraisal() == null) {
				event.setLastAppraisal('N');
			}
			if (event.getConfirmationDate() == null) {
				event.setConfirmationDate('N');
			}
			if (event.getDateOfJoining() == null) {
				event.setDateOfJoining('N');
			}
			if (event.getContactNumberTwo() == null) {
				event.setContactNumberTwo('N');
			}
			if (event.getEmailId() == null) {
				event.setEmailId('N');
			}
			if (event.getCurrentBuId() == null) {
				event.setCurrentBuId('N');
			}
			if (event.getBuId() == null) {
				event.setBuId('N');
			}
			if (event.getPayrollLocation() == null) {
				event.setPayrollLocation('N');
			}
			if (event.getYashEmpId() == null) {
				event.setYashEmpId('N');
			}
			if (event.getContactNumber() == null ) {
				event.setContactNumber('N');
			}
			if (event.getCurrentReportingManager() == null) {
				event.setCurrentReportingManager('N');
			}
			if (event.getCurrentReportingTwo() == null) {
				event.setCurrentReportingTwo('N');
			}
			if (event.getOwnership() == null) {
				event.setOwnership('N');
			}
			if (event.getDesignationId() == null) {
				event.setDesignationId('N');
			}
			if (event.getGradeId() == null) {
				event.setGradeId('N');
			}
			if (event.getLocationId() == null) {
				event.setLocationId('N');
			}
			if (event.getEmployeeCategory() == null) {
				event.setEmployeeCategory('N');
			}
			if (event.getEndTransferDate() == null) {
				event.setEndTransferDate('N');
			}
			}
		}

		public List<Event> findAllEvents() {
			logger.info("--------EventDaoImpl findAll method start-------");

			List<Object[]> eventList = new ArrayList<Object[]>();
			List<Event> populatedEvents = new ArrayList<Event>();
			List<Event> events = new ArrayList<Event>();
			Session session=(Session) getEntityManager().getDelegate();
			try {
				org.hibernate.Criteria criteria = (org.hibernate.Criteria) session.createCriteria(Event.class);
				ProjectionList projList = Projections.projectionList();
				projList.add(Projections.property("id"));
				projList.add(Projections.property("event"));
				projList.add(Projections.property("description"));
				criteria.setProjection(Projections.distinct(projList));
				//put order by: refer PrjAllocationController
				criteria.addOrder(Order.desc("id"));
				eventList = criteria.list(); 
				populatedEvents = populate(eventList);
				
				for (Event event : populatedEvents) {
					System.out.println(event.getId());
					if (event.getId() > 0) {
						events.add(event);
					} 
				}
			} catch (HibernateException e) {
				 logger.error("Exception occured in findAll method at DAO layer:-"+e);
		         e.printStackTrace(); 
		      }finally {
		      }
			logger.info("------------EventDaoImpl findAll method end------------");
			//System.out.println(events.size());
			return events ;
		}

		private List<Event> populate(List<Object[]> obj) {
			// TODO Auto-generated method stub
			List<Event> events = new ArrayList<Event>();
			for (Object[] objects : obj) {
				Event event = new Event();
				if (objects[1]!= null) {
					event.setEvent(objects[1].toString());
					event.setId(Integer.parseInt(objects[0].toString()));
					//check if description is null 
					if (objects[2] == null) {
						event.setDescription(null);
					}else {
						event.setDescription(objects[2].toString());
					}
					events.add(event);
				}
			}
			return events;
		}
		
		
		
	}

