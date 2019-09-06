package org.yash.rms.rest.dao.criteria.impl;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.yash.rms.rest.dao.criteria.IQueryCriteriaBuilder;
import org.yash.rms.rest.utils.QueryObject;


/**
 * The Class JPACriteriaBuilder.
 *
 * @param <Entity> the generic type
 */
@org.yash.rms.rest.dao.annotation.CriteriaBuilder

public class JPACriteriaBuilder<Entity> implements IQueryCriteriaBuilder<Entity> {
	/**
	 * represents the JPA entity manager.
	 */
	private EntityManager entityManager;

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the entity manager.
	 *
	 * @param entityManager the new entity manager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Builds the criteria.
	 *
	 * @param queryObject the query object
	 * @param entityClass the entity class
	 * @return the object
	
	 */
	public Object buildCriteria (QueryObject queryObject, Class<Entity> entityClass){
		CriteriaBuilder cBuilder= getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Entity> cQuery = cBuilder.createQuery(entityClass);
		Root<Entity> root = cQuery.from(entityClass);
		cQuery.select(root);
		addWhereClause(cQuery, queryObject);
		return null;
	}

	/**
	 * Adds the where clause.
	 *
	 * @param cQuery the c query
	 * @param queryObject the query object
	 */
	private void addWhereClause(CriteriaQuery<Entity> cQuery, QueryObject queryObject) {
	}

}
