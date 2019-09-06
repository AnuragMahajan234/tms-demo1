package org.yash.rms.rest.dao.criteria;

import java.util.List;


public interface IQueryExecutor<Entity> {
	/**
	 * execute the select query and generate the result in form of list.
	 * @param criteria represents the query object.
	 * @return returns the list of entities filtered after applying criteria
	 * generated using query object.
	 */
	List<Entity> select(Object criteria);

	/**
	 * execute the select query using the respective query object and returns the 
	 * count of entities filtered after applying criteria.
	 * @param criteria represents the query object.
	 * @return returns the count of entities filtered after applying criteria.
	 */
	Long count(Object criteria);
}
