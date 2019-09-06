package org.yash.rms.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.jaxrs.ext.search.SearchContext;
import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.InfogramActiveResourceDTO;
import org.yash.rms.exception.BusinessException;
import org.yash.rms.exception.RMSServiceException;
import org.yash.rms.rest.service.generic.IGenericService;
import org.yash.rms.util.SearchCriteriaGeneric;

public interface InfogramActiveResourceService extends IGenericService<Integer, InfogramActiveResource>{

	public void updateInfogramObject(InfogramActiveResource infogramActiveResource) throws RMSServiceException;
	public List<InfogramActiveResourceDTO> getAllInfogramActiveResource();
	public List<InfogramActiveResource> getAllInfogramActiveResource(String resourceType, String processStatus, String recordStatus, HttpServletRequest httpRequest);
	public Boolean saveInfogramActiveResource(Integer id, Resource resource) throws Exception;

	public void saveEditedInfogramActiveResource(InfogramActiveResourceDTO infogramActiveResourceDTO) throws RMSServiceException;

	public void discardInfogramResourceById(Integer id, Resource resource ) throws RMSServiceException;
	
	public Long getTotalCountInfogramResource(String status,String processStatus,HttpServletRequest request) throws Exception;
	
	public void updateProcessStatus(Integer id);
	
	public List<InfogramActiveResourceDTO> getInfogramActiveResourceReport();
	public InfogramActiveResource findById(Integer id);
	public List<InfogramActiveResource> getAllInfogramActiveResources();
public List<InfogramActiveResource> searchWithLimit(SearchContext context, Integer maxLimit, Integer minLimit) throws Exception;
	public List<InfogramActiveResource> searchWithLimitAndOrderBy(SearchContext ctx,Integer maxLimit, Integer minLimit, String orderby, String orderType)throws BusinessException;
	public Long count(SearchContext ctx)throws BusinessException;
	InfogramActiveResource create(InfogramActiveResource infogramActiveResource) throws Exception;
	InfogramActiveResource update(InfogramActiveResource infogramActiveResource) throws Exception;	
	public void removeById(Integer primaryKey) throws BusinessException;
}

