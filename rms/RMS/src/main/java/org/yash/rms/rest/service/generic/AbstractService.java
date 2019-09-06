package org.yash.rms.rest.service.generic;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yash.rms.exception.BusinessException;
import org.yash.rms.exception.DaoRestException;
import org.yash.rms.rest.dao.generic.IGenericDao;
import org.yash.rms.rest.utils.AdvanceSearchResult;
import org.yash.rms.rest.utils.QueryObject;
import org.apache.cxf.jaxrs.ext.search.SearchContext;

public abstract class AbstractService<Pk, Entity> implements IGenericService<Pk, Entity>{
	/**
	 * represents the data access object which provides implementation
	 * of data persistence and retrieval from the store. 
	 */
	private IGenericDao<Pk, Entity> dao;
	/**
	 * Gets the dao.
	 *
	 * @return returns the resepective data access object instance.
	 */
	public IGenericDao<Pk, Entity> getDao() {
		return dao;
	}
	/**
	 * Concrete service class must override this to autowire the specific data access
	 * object instance with this service.
	 *
	 * @param dao the dao
	 */
	public void setDao(IGenericDao<Pk, Entity> dao) {
		this.dao = dao;
	}
	
	/**
	 * Advance search.
	 *
	 * @param queryObject represents the query.
	 * @return returns the filtered entities after performing advance search.
	 * @throws BusinessException the business exception
	 * @param queryObject represents the query.
	 */
	public AdvanceSearchResult<Entity> advanceSearch(QueryObject queryObject) throws BusinessException {
		logger.debug("performing advance search using queryObject{}", queryObject);
		try{
		List<Entity> results = dao.find(queryObject);
		AdvanceSearchResult<Entity> asr = new AdvanceSearchResult<Entity>();
		asr.setLowerBound(queryObject.getPaginationLowerLimit());
		asr.setUpperBound(queryObject.getPaginationUpperLimit());
		asr.setResults(results);
		asr.setTotalRecords(dao.count(queryObject));
		logger.debug("filtered results {}", asr);
		return asr;
		}catch (DaoRestException e) {
		throw new BusinessException(e.getErrCode());
		}
	}
	
	/**
	 * performs a search based on the values in the entity. All the values provided
	 * in the entity are compared using equal operator. This doesn't support regular
	 * expression based search.
	 * 
	 * @param entity represents the entity instance.
	 * @return returns the list of entities filtered after performing search.
	 * @throws BusinessException the business exception
	 */
	public List<Entity> search(Entity entity) throws Exception{
		String[] exclude = {};
		return dao.findByExample(entity, exclude);
		
	}
	
	/** represents the logger instance. */
	private static final Logger logger = LoggerFactory.getLogger(AbstractService.class);
	
	/**
	 * search on entity with FIQL latest.
	 *
	 * @param ctx the ctx
	 * @param maxLimit the max limit
	 * @param minLimit the min limit
	 * @return the list
	 * @throws BusinessException the business exception
	 */
	public  List<Entity> searchWithLimit(SearchContext ctx,Integer maxLimit,Integer minLimit ) throws Exception
	{	
		return dao.search(ctx, maxLimit, minLimit);
		
		
	}
	
	/**
	 * search on entity with FIQL latest.
	 *
	 * @param ctx the ctx
	 * @param maxLimit the max limit
	 * @param minLimit the min limit
	 * @param orderby the orderby
	 * @param orderType the order type
	 * @return the list
	 * @throws BusinessException the business exception
	 */
	public  List<Entity> searchWithLimitAndOrderBy(SearchContext ctx,Integer maxLimit,Integer minLimit,String orderby,String orderType )throws Exception
	{	
		
		return dao.search(ctx, maxLimit, minLimit,orderby,orderType);
		
	}

	}
