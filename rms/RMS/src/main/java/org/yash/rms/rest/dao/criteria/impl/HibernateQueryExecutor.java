package org.yash.rms.rest.dao.criteria.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.yash.rms.rest.dao.annotation.CriteriaExecutor;
import org.yash.rms.rest.dao.criteria.IQueryExecutor;



@CriteriaExecutor
public class HibernateQueryExecutor<Entity> implements IQueryExecutor<Entity> {
	/**
	 * returns the list of entities filtered after applying criteria.
	 */
	public List<Entity> select(Object criteria) {
		Criteria hibernateCriteria = (Criteria) criteria;
		return hibernateCriteria.list();
	}
	/**
	 * @return int returns the total count of entities filtered using provided criteria.
	 */
	public Long count(Object criteria) {
		Criteria hibernateCriteria = (Criteria) criteria;
		hibernateCriteria.setProjection(Projections.rowCount());
		List<Entity> list = hibernateCriteria.list();
		list.get(0);
		Long rowCount = (Long) list.get(0);
		return rowCount;
		//return	this.select(criteria).size();
	}

}
