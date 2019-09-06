package org.yash.rms.rest.dao.generic.impl;


import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yash.rms.exception.DaoRestException;
import org.yash.rms.rest.dao.criteria.impl.JPACriteriaBuilder;
import org.yash.rms.rest.dao.criteria.impl.JPAQueryExecutor;
import org.yash.rms.rest.dao.generic.AbstractBaseDao;
import org.yash.rms.rest.utils.QueryObject;
import org.yash.rms.rest.utils.QueryObject.SortOrder;


public class JPABaseDao<Pk, Entity> extends AbstractBaseDao<Pk, Entity>{
	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JPABaseDao.class);
	/**
	 * represents the JPA entity manager.
	 */
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;

	/**
	 * This constructor needs the real type of the generic type Entity
	 * so it can be passed to the {@link EntityManager}.
	 *
	 * @param type the type
	 */
	protected JPABaseDao(Class<Entity> type) {
		super(type);
		this.setCriteriaBuilder(new JPACriteriaBuilder<Entity>());
		this.setQueryExecutor(new JPAQueryExecutor<Entity>());
	}
	
	/**
	 * Gets the entity manager.
	 *
	 * @return returns the {@link EntityManager}
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * sets the {@link EntityManager} using autowiring.
	 *
	 * @param entityManager the new entity manager
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Creates an entity in the persistent store. 
	 *
	 * @param anEntity Entity to create
	 * @return the entity
	 * @throws DaoException the dao exception
	 */
	public Entity create(Entity anEntity) throws DaoRestException {
		Entity anEntityLocal=anEntity;
		if(anEntityLocal == null){
			logger.warn("Cannot create a null entity.");
			throw new IllegalArgumentException("Cannot create null entity["+getType().getName()+"]");
		}
		anEntityLocal = getEntityManager().merge(anEntityLocal);
		return anEntityLocal;
	}
	
	/**
	 * Updates an entity in the persistent store. 
	 *
	 * @param anEntity Entity to update.
	 * @return the entity
	 * @throws DaoException the dao exception
	 */
	public Entity update(Entity anEntity) throws DaoRestException {
		if(anEntity == null){
			logger.warn("Cannot update a null entity.");
			throw new IllegalArgumentException("Cannot update null entity["+getType().getName()+"]");
		}
		return getEntityManager().merge(anEntity);
	}
	
	/**
	 * Delete the entity from the persistent store. 
	 * Note: Entity's primary key must be populated.
	 *
	 * @param anEntity the an entity
	 * @throws DaoException the dao exception
	 */
	public void delete(Entity anEntity) throws DaoRestException{
		if(anEntity == null){
			logger.warn("Cannot delete a null entity.");
			throw new IllegalArgumentException("Cannot delete null entity["+getType().getName()+"]");
		}
		getEntityManager().remove(getEntityManager().contains(anEntity)? anEntity: getEntityManager().merge(anEntity));
	}
	
	/**
	 * Delete the entity from the persistent store identified by its primary key. 
	 *
	 * @param entityPk Primary key object of the entity.
	 * @throws DaoException the dao exception
	 */
	public void deleteByPk(Pk entityPk) throws DaoRestException{
		if(entityPk == null){
			logger.warn("Cannot delete entity["+getType().getName()+"] since specified primary key is null");
			throw new IllegalArgumentException("Cannot delete entity["+getType().getName()+"] since specified primary key is null");
		}
		Entity anEntity = this.findByPk(entityPk);
		this.delete(anEntity);
	}
	/**
	 * Checks if an entity with similar attribute values exists.
	 * 
	 * @param anEntity represents entity which is referred for equality comparison. 
	 * @return returns true if one or more entities exists else returns false.
	 */
	public boolean contains(Entity anEntity) {
		if(anEntity == null){
			logger.warn("Illegal argument null for entity["+getType().getName()+"] contains method ");
			return false;
		}

		return this.getEntityManager().contains(getEntityManager().contains(anEntity)? anEntity: getEntityManager().merge(anEntity));
	}
	/**
	 * Find by primary key.
	 * Search for an entity of the specified class and primary key.
	 * If the entity instance is contained in the persistence context
	 * it is returned from there.
	 *
	 * @param entityPk primary key.
	 * @return the found entity instance or null if the entity does
	 * not exist
	 * @throws DaoException the dao exception
	 */
	public Entity findByPk(Pk entityPk) throws DaoRestException{
		if(entityPk == null){
			logger.warn("Cannot find entity["+getType().getName()+"] with null primary key");
			return null;
		}

		return this.getEntityManager().find(getType(), entityPk);
	}
	
	/**
	 * Find all.
	 *
	 * @return returns all the entities in the persistent store.
	 * @throws DaoException the dao exception
	 */
	public List<Entity> findAll() throws DaoRestException{
		return this.find(new QueryObject());
	}
	
	/**
	 * Find all.
	 *
	 * @param order the order
	 * @param sort the sort
	 * @return the list
	 */
	public List<Entity> findAll(String order,SortOrder sort) {
		HashMap<String, SortOrder> orderMap=new HashMap<String, QueryObject.SortOrder>();
		orderMap.put(order,sort);
		QueryObject query=new QueryObject();
		query.setOrderByMode(orderMap);
		return this.find(query);
	}
	
	/**
	 * Find all with pagination.
	 *
	 * @param lowerLimit the lower limit
	 * @param upperLimit the upper limit
	 * @return the list
	 */
	public List<Entity> findAllWithPagination(int lowerLimit,int upperLimit) {
               QueryObject queryObject=new QueryObject();
               queryObject.setPaginationLowerLimit(lowerLimit);
               queryObject.setPaginationUpperLimit(upperLimit);
               return this.find(queryObject);
       }
       
       /**
        * Find all with pagination.
        *
        * @param lowerLimit the lower limit
        * @param upperLimit the upper limit
        * @param order the order
        * @param sort the sort
        * @return the list
        */
       public List<Entity> findAllWithPagination(int lowerLimit,int upperLimit,String order,SortOrder sort) {
       			HashMap<String, SortOrder> orderMap=new HashMap<String, QueryObject.SortOrder>();
				orderMap.put(order,sort);
               QueryObject queryObject=new QueryObject();
               queryObject.setPaginationLowerLimit(lowerLimit);
               queryObject.setPaginationUpperLimit(upperLimit);
               queryObject.setOrderByMode(orderMap);
               return this.find(queryObject);
       }
       
       public Long count() {
          QueryObject queryObject=new QueryObject();               
          return this.count(queryObject);
}
}
