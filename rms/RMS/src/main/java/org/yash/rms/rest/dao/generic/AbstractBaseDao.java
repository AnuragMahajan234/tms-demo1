package org.yash.rms.rest.dao.generic;

import java.util.List;

import org.yash.rms.rest.dao.criteria.IQueryCriteriaBuilder;
import org.yash.rms.rest.dao.criteria.IQueryExecutor;
import org.yash.rms.rest.utils.QueryObject;


/**
 *
 * @param <Pk>  the generic type
 * @param <Entity> the generic type
 */
public abstract class AbstractBaseDao<Pk, Entity> implements IBaseDao<Pk, Entity> {
	/**
	 * represents the Class of entity. 
	 */
	private Class<Entity> type;
	/**
	 * represents criteria builder which constructs the object representation of query
	 * to be executed. 
	 */
	private IQueryCriteriaBuilder<Entity> criteriaBuilder;
	/**
	 * represents the query executor service which executes the criteria build using 
	 * criteria builder.
	 */
	private IQueryExecutor<Entity> queryExecutor;
	/**
	 * Instantiates a new abstract base dao.
	 *
	 * @param type the type
	 */
	protected AbstractBaseDao(Class<Entity> type) {
		this.type = type;
	}
	
	/**
	 * Find.
	 *
	 * @param queryObject the query object
	 * @return the list
	 * @see com.inn.redesign.dao.IBaseDao#find(com.inn.redesign.dao.QueryObject)
	 */
	public List<Entity>find(QueryObject queryObject){
		Object criteria = criteriaBuilder.buildCriteria(queryObject, getType());
		return queryExecutor.select(criteria);
	}

	/**
	 * Count.
	 *
	 * @param queryObject the query object
	 * @return the int
	 * @see com.inn.redesign.dao.IBaseDao#count(com.inn.redesign.dao.QueryObject)
	 */
	public Long count(QueryObject queryObject) {
		
		queryObject.setPaginationLowerLimit(-1);
		queryObject.setPaginationUpperLimit(-1);
		Object criteria = criteriaBuilder.buildCriteria(queryObject, getType());
		return queryExecutor.count(criteria);
	}
	/**
	 * Gets the type.
	 *
	 * @return returns the entity class.
	 */
	public Class<Entity> getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(Class<Entity> type) {
		this.type = type;
	}

	/**
	 * Gets the criteria builder.
	 *
	 * @return the criteria builder
	 */
	public IQueryCriteriaBuilder<Entity> getCriteriaBuilder() {
		return criteriaBuilder;
	}

	/**
	 * Gets the query executor.
	 *
	 * @return the query executor
	 */
	public IQueryExecutor<Entity> getQueryExecutor() {
		return queryExecutor;
	}

	/**
	 * Sets the criteria builder.
	 *
	 * @param criteriaBuilder the new criteria builder
	 */
	public void setCriteriaBuilder(IQueryCriteriaBuilder<Entity> criteriaBuilder) {
		this.criteriaBuilder = criteriaBuilder;
	}

	/**
	 * Sets the query executor.
	 *
	 * @param queryExecutor the new query executor
	 */
	public void setQueryExecutor(IQueryExecutor<Entity> queryExecutor) {
		this.queryExecutor = queryExecutor;
	}
}
