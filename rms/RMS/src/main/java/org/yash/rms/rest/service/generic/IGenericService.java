package org.yash.rms.rest.service.generic;

import java.util.List;


import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.exception.DaoRestException;
import org.yash.rms.exception.RestException;
import org.apache.cxf.jaxrs.ext.search.SearchContext;


 @Transactional
public interface IGenericService<Pk, Entity> {
	
	/**
	 * API which performs a search based on the values in the entity. All the
	 * values provided in the entity are compared using equal operator. 
	 * This doesn't support regular expression based search.
	 * 
	 * @param entity represents the entity instance.
	 * 
	 * @return returns the list of entities filtered after performing search.
	 */
	List<Entity> search(Entity entity)throws Exception;
	
	Entity create(Entity entity)throws Exception;
	
	
	/**
	 * search on entity with FIQL latest
	 * @param context,maxLimit,Minlimit
	 */
	List<Entity> searchWithLimit(SearchContext context,Integer maxLimit,Integer minLimit )throws Exception;
	
	
	/**
	 * search on entity with FIQL latest
	 * @param context,maxLimit,Minlimit,orderby,orderType
	 */
	 List<Entity> searchWithLimitAndOrderBy(SearchContext ctx ,Integer maxLimit,Integer minLimit,String orderby,String orderType )throws Exception;
	 void removeById(Pk primaryKey) throws RestException, DaoRestException;
}
