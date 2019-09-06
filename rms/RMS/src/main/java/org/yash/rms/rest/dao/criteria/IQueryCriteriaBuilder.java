package org.yash.rms.rest.dao.criteria;

import org.yash.rms.rest.utils.QueryObject;

public interface IQueryCriteriaBuilder<Entity> {
	/**
	 * converts the query object into a query.
	 * @param queryObject
	 * @param entityClass
	 * @return
	 */
	Object buildCriteria(QueryObject queryObject, Class<Entity> entityClass);
}
