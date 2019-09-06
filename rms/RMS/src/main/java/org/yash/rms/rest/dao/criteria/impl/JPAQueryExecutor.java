package org.yash.rms.rest.dao.criteria.impl;

import java.util.List;

import org.yash.rms.rest.dao.annotation.CriteriaExecutor;
import org.yash.rms.rest.dao.criteria.IQueryExecutor;



@CriteriaExecutor
public class JPAQueryExecutor<Entity> implements IQueryExecutor<Entity> {

	/**
	 * Instantiates a new JPA query executor.
	 */
	public JPAQueryExecutor() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Select.
	 *
	 * @param criteria the criteria
	 * @return the list
	 * @see com.inn.decent.dao.criteria.IQueryExecutor.select(java.lang.Object)
	 */
	public List<Entity> select(Object criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Count.
	 *
	 * @param criteria the criteria
	 * @return the int
	 * @see com.inn.decent.dao.criteria.IQueryExecutor.count(java.lang.Object)
	 */
	public Long count(Object criteria) {
		// TODO Auto-generated method stub
		return 0L;
	}

}
