package org.yash.rms.rest.dao.generic.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Collections;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import org.apache.cxf.jaxrs.ext.search.SearchCondition;
import org.apache.cxf.jaxrs.ext.search.SearchContext;
import org.apache.cxf.jaxrs.ext.search.jpa.JPACriteriaQueryVisitor;
import org.apache.cxf.jaxrs.ext.search.jpa.JPATypedQueryVisitor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.internal.impl.AbstractAuditQuery;
import org.hibernate.proxy.HibernateProxyHelper;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.yash.rms.exception.DaoRestException;
import org.yash.rms.rest.dao.criteria.IQueryCriteriaBuilder;
import org.yash.rms.rest.dao.criteria.IQueryExecutor;
import org.yash.rms.rest.dao.criteria.impl.HibernateCriteriaBuilder;
import org.yash.rms.rest.dao.criteria.impl.JPACriteriaBuilder;
import org.yash.rms.rest.dao.generic.IGenericDao;
import org.yash.rms.rest.service.generic.AbstractService;
import org.yash.rms.rest.utils.ExceptionUtil;
import org.yash.rms.rest.utils.QueryObject;
import org.yash.rms.rest.utils.QueryObject.SortOrder;



public class HibernateGenericDao<Pk, Entity> extends JPABaseDao<Pk, Entity>
		implements IGenericDao<Pk, Entity> {
	
/** represents the logger instance. */
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HibernateGenericDao.class);	

/** The Constant IDENTIFIER_METHOD. */
public static final String IDENTIFIER_METHOD="getPrimaryKeyIdentifier";

/** The Constant USERNAME_METHOD. */
public static final String USERNAME_METHOD="getUsername";

/** The Constant MODIFCATIONTIME_METHOD. */
public static final String MODIFCATIONTIME_METHOD="getModifiedTime";

/** The Constant MODIFIEDBY_METHOD. */
public static final String MODIFIEDBY_METHOD  ="last_modifier";

/** The Constant CHANGES. */
public static final String CHANGES = "changes";

/** The Constant CHANGED_BY. */
public static final String CHANGED_BY = "changed_by";

/** The Constant CHANGED_ON. */
public static final String CHANGED_ON = "changed_on";

/** The Constant GET. */
public static final String GET = "get";

/** The Constant SET. */
public static final String SET = "set";

/** The Constant CREATED_TIME. */
public static final String CREATED_TIME = "CreatedTime";

/** The Constant MODIFIED_TIME. */
public static final String MODIFIED_TIME = "ModifiedTime";

/** The Constant CREATOR. */
public static final String CREATOR = "Creator";

/** The Constant LAST_MODIFIER. */
public static final String LAST_MODIFIER = "LastModifier";

/** The Constant DOMAIN. */
public static final String DOMAIN = "Domain";

/** The Constant DESC. */
public static final String DESC ="desc";

/** The Constant ADMIN. */
public static final String ADMIN = "admin";

/**
 * Sets the criteria builder.
 *
 * @param criteriaBuilder the new criteria builder
 * @see com.inn.decent.dao.generic.AbstractBaseDao.setCriteriaBuilder(com.inn.decent.dao.criteria.IQueryCriteriaBuilder)
 */
@Override
@Autowired(required = true)
@Qualifier("hibernateCriteriaBuilder")
public void setCriteriaBuilder(IQueryCriteriaBuilder<Entity> criteriaBuilder) {
	super.setCriteriaBuilder(criteriaBuilder);
}

/**
 * Sets the query executor.
 *
 * @param queryExecutor the new query executor
 * @see com.inn.decent.dao.generic.AbstractBaseDao.setQueryExecutor(com.inn.decent.dao.criteria.IQueryExecutor)
 */
@Override
@Autowired(required = true)
@Qualifier("hibernateQueryExecutor")
public void setQueryExecutor(IQueryExecutor<Entity> queryExecutor) {
	super.setQueryExecutor(queryExecutor);
}

/**
 * Instantiates a new hibernate generic dao.
 *
 * @param type the type
 */
public HibernateGenericDao(Class<Entity> type) {
	super(type);
}

/**
 * Returns the List of Entities that match the search criteria specified
 * through the Example. Searches all Entities that match the properties set
 * in the Example entity.
 *
 * @param refEntity            Example Element to search for.
 * @param excludeProperty the exclude property
 * @return List of entities that match the search criteria specified through
 *         all properties set in the Example.
 */
public List<Entity> findByExample(Entity refEntity, String[] excludeProperty) {
	logger.info(
			"performing findByExample using entity {} excludedProperty{}",
			refEntity, excludeProperty);
	Session session = (Session) getEntityManager().getDelegate();

	Criteria criteria = session.createCriteria(getType());

	//addSimpleFieldCriteria(refEntity, criteria);

	List<Entity> result = criteria.list();
	logger.info("filtered result after findByExample {}", result);
	return result;
}





/**
 * Returns the record by searching Entity.
 *
 * @param ctx the ctx
 * @param maxLimit the max limit
 * @param minLimit the min limit
 * @param orderby the orderby
 * @param orderType the order type
 * @return the list
 * @throws DaoRestException 
 */
public List<Entity> search(SearchContext ctx, Integer maxLimit,
		Integer minLimit, String orderby, String orderType) throws DaoRestException {
	logger.info("HibernateGenericDao-search method start With param maxLimit : "+maxLimit+" , minLimit:"+minLimit+", orderby : "+orderby+" ,orderType : "+orderType);
	try {
	SearchCondition<Entity> sc = ctx.getCondition(getType());
	String searchExpression=ctx.getSearchExpression();
	if (sc != null) {
		return getResultsForSearchCondition(sc,maxLimit,minLimit,orderby,orderType);
	} else if(searchExpression==null){
		if (orderby != null && orderType != null) {
			if (orderType.equalsIgnoreCase(DESC)) {
				logger.info("HibernateGenericDao-search-findAllWithPagination-SortOrder.DESC");
				return findAllWithPagination(minLimit, maxLimit, orderby,
						SortOrder.DESC);
			} else {
				logger.info("HibernateGenericDao-search-findAllWithPagination-SortOrder.ASC");
				return findAllWithPagination(minLimit, maxLimit, orderby,
						SortOrder.ASC);
			}
		} else {
			logger.info("HibernateGenericDao-search-findAllWithPagination");
			return findAllWithPagination(minLimit, maxLimit);
		}
	}else{
		logger.info("searchExpression in not null but searchCondition is  null...");
		return Collections.EMPTY_LIST;
	}
	}catch(Exception ex) {
		logger.error("Error Inside  @class :"+this.getClass().getName()+" @Method :search"+ex.getMessage());
		throw new DaoRestException(ExceptionUtil.generateExceptionCode("Dao","InfogramActiveResource",ex));
	}
}


/**
 * Get search results entity for search condition.
 *
 * @param sc the sc
 * @param maxLimit the max limit
 * @param minLimit the min limit
 * @param orderby the orderby
 * @param orderType the order type
 * @return the results for search condition
 */
protected List<Entity> getResultsForSearchCondition(SearchCondition<Entity> sc,Integer maxLimit,
		Integer minLimit,String orderby, String orderType) throws DaoRestException{
	logger.info("HibernateGenericDao-getResultsForSearchCondition start");
	try {
	JPATypedQueryVisitor<Entity> visitor = new JPATypedQueryVisitor<Entity>(
			getEntityManager(), getType());
			
	sc.accept(visitor);
	visitor.visit(sc);
	TypedQuery<Entity> typedQuery = null;
	if (orderby != null && orderType != null) {
		if (orderType.equalsIgnoreCase(DESC)) {
			logger.info("HibernateGenericDao-search-findAllWithPagination-SortOrder.DESC");
									
			typedQuery =visitor.getTypedQuery(orderby,orderType);
		} else {
			logger.info("HibernateGenericDao-search-findAllWithPagination-SortOrder.ASC");
			typedQuery =visitor.getTypedQuery(orderby,orderType);
		}
	}else{
			typedQuery =visitor.getTypedQuery();
		}

	if (maxLimit >= 0) {
		typedQuery.setMaxResults(maxLimit - minLimit + 1);
	}

	if (minLimit >= 0) {
		typedQuery.setFirstResult(minLimit);
	}
	logger.info("HibernateGenericDao-getResultsForSearchCondition end");
	
	return typedQuery.getResultList();
	}catch(Exception ex) {
		logger.error("Error Inside  @class :"+this.getClass().getName()+" @Method :getResultsForSearchCondition"+ex.getMessage());
		throw new DaoRestException(ExceptionUtil.generateExceptionCode("Dao","InfogramActiveResource",ex));
	}
	
} 

/**
 * Returns the record by searching Entity.
 *
 * @param ctx the ctx
 * @param maxLimit the max limit
 * @param minLimit the min limit
 * @return the list
 * @throws DaoRestException 
 */
public List<Entity> search(SearchContext ctx, Integer maxLimit,
		Integer minLimit) throws DaoRestException {
	logger.info("HibernateGenericDao-search method start With param maxLimit : "+maxLimit+" , minLimit:"+minLimit);
	try{
		SearchCondition<Entity> sc = ctx.getCondition(getType());
	
	String searchExpression=ctx.getSearchExpression();
	if (sc != null) {
		return getResultsForSearchCondition(sc,maxLimit,minLimit,null,null);
	} else if(searchExpression==null) {
		logger.info("HibernateGenericDao-search-findAllWithPagination");
		return findAllWithPagination(minLimit, maxLimit);
	}else{
		logger.info("searchExpression in not null but searchCondition is  null...");
		return Collections.EMPTY_LIST;
	}
	}catch(Exception ex) {
		logger.error("Error Inside  @class :"+this.getClass().getName()+" @Method :search without order"+ex.getMessage());
		throw new DaoRestException(ExceptionUtil.generateExceptionCode("Dao","InfogramActiveResource",ex));
	}
}

public Long count(SearchContext ctx) throws DaoRestException {
		logger.info("HibernateGenericDao-count method start ");
		try {
	SearchCondition<Entity> sc = ctx.getCondition(getType());
	String searchExpression=ctx.getSearchExpression();
	logger.info("Inside  @class :"+this.getClass().getName()+" @Method :count ");
	
	Long count=null;
	
	if (sc != null) {
		//return getResultsForSearchCondition(sc,maxLimit,minLimit,orderby,orderType).size();
		JPACriteriaQueryVisitor<Entity, Long> jpa = new JPACriteriaQueryVisitor<Entity, Long>(getEntityManager(), getType(), Long.class);
		sc.accept(jpa);			         
		//count = (long) jpa.count().intValue();
		count = jpa.count();
	} else if(searchExpression==null){
		return count();
	}else{
		logger.info("searchExpression in not null but searchCondition is  null...");
		return 0L;
	}
	return count;
	}catch(Exception ex) {
		logger.error("Error Inside  @class :"+this.getClass().getName()+" @Method :count "+ex.getMessage());
		throw new DaoRestException(ExceptionUtil.generateExceptionCode("Dao","InfogramActiveResource",ex));
	}
		
		
}



	
}

