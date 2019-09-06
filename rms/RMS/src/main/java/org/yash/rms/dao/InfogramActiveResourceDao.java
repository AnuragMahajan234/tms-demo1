package org.yash.rms.dao;

import java.text.ParseException;
import java.util.List;

import org.apache.cxf.jaxrs.ext.search.SearchContext;
import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.InfogramActiveResourceDTO;
import org.yash.rms.exception.BusinessException;
import org.yash.rms.exception.DAOException;
import org.yash.rms.exception.DaoRestException;
import org.yash.rms.util.SearchCriteriaGeneric;

/**
 * Dao interface for InfogramActiveResource.
 * @author samiksha.sant
 *
 */

public interface InfogramActiveResourceDao {

	public List<InfogramActiveResource> getAllInfogramActiveResources();
	public List<InfogramActiveResource> getAllInfogramActiveResources(String resourceType, String processStatus, String recordStatus, SearchCriteriaGeneric searchCriteriaGeneric);

	public InfogramActiveResource getInfogramActiveResourceById(Integer id) throws DAOException;;

	public void updateInfogramObject(InfogramActiveResource infogramActiveResource) throws DAOException;

	public void updateInfogramResourceToDiscarded(String id, Resource resource) throws DAOException;

	public Long getTotalCountInfogramResource(String status, String processStatus,SearchCriteriaGeneric searchCriteriaGeneric) throws ParseException;
	
	public List<InfogramActiveResourceDTO> getAllInfogramActiveResourcesReport();
	
	public List<InfogramActiveResource> searchWithLimitAndOrderBy(SearchContext ctx,Integer maxLimit, Integer minLimit, String orderby, String orderType)throws DaoRestException;
	
	public List<InfogramActiveResource> searchWithLimit(SearchContext context, Integer maxLimit, Integer minLimit) throws DaoRestException;
	
	public Long count(SearchContext ctx)throws DaoRestException;
	
	public InfogramActiveResource create(InfogramActiveResource infogramActiveResource) throws DaoRestException;
	
	public InfogramActiveResource update(InfogramActiveResource infogramActiveResource) throws DaoRestException;
	public void deleteByPk(Integer entityPk) throws DaoRestException;
	
}

