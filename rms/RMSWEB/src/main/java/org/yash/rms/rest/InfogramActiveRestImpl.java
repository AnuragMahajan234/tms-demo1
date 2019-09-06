package org.yash.rms.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.search.SearchContext;
import org.codehaus.jackson.map.annotate.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.exception.BusinessException;
import org.yash.rms.exception.RestException;
import org.yash.rms.rest.utils.ExceptionUtil;
import org.yash.rms.security.CustomAuthenticationToken;
import org.yash.rms.security.RMSHttpRequestWrapper;
import org.yash.rms.service.InfogramActiveResourceService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.UserUtil;
import org.yash.rms.util.View;



@Path("/InfogramActive")
@Service("InfogramActiveRestImpl")
public class InfogramActiveRestImpl {

	private static final Logger logger = LoggerFactory.getLogger(InfogramActiveRestImpl.class);

	@Autowired
	private InfogramActiveResourceService infogramActiveResourceService;
	
	@Autowired
	private ResourceService resourceService;
	
    @Autowired
    private UserUtil userUtil;

	
	@Path("getInfogramActiveById/{id}")
	@Produces("application/json")
	@GET
	public InfogramActiveResource getInfogramActiveById(@PathParam("id") Integer id,@Context SearchContext context)
	{
		System.out.println("context exp ---------------"+context.getSearchExpression());
		return infogramActiveResourceService.findById(id);
	}
	
	@Path("getResourceByYashEmpId/{id}")
	@Produces("application/json")
	@GET
	@JsonView(View.Public.class)
	public Resource getResourceByYashEmpId(@PathParam("id") String yashEmpId,@Context SearchContext context)
	{
		System.out.println("context exp ---------------"+context.getSearchExpression());
		return resourceService.findResourcesByYashEmpIdEquals(yashEmpId,context);
	}
	
	/**
	 * Search.
	 *
	 * @param entity the entity
	 * @return the list
	 * @throws Exception 
	 * @throws RestException the rest exception
	 * @see com.inn.decent.rest.generic.IGenericRest.search(java.lang.Object)
	 */
	/*@GET
	public List<InfogramActiveResource> search(@QueryParam("") InfogramActiveResource entity) throws Exception{
		try{
		logger.debug("Searching resource");
		return infogramActiveResourceService.search(entity);
	}catch(Exception ex)
	{
		logger.error("Error  occurred  @class"   + this.getClass().getName()  , ex);
		throw new Exception(ex.getMessage());
	}
	}*/
	
	/**
	 * Search.
	 *
	 * @param lowerLimit the lower limit
	 * @param upperLimit the upper limit
	 * @return the list
	 * @throws Exception 
	 * @throws RestException the rest exception
	 */
	@GET
	@Path("search")
	@Produces("application/json")
	public List<InfogramActiveResource> search(@DefaultValue("0") @QueryParam("llimit") Integer lowerLimit, @DefaultValue("9") @QueryParam("ulimit") Integer upperLimit,@DefaultValue("id") @QueryParam("orderBy") String orderBy,@DefaultValue("desc") @QueryParam("orderType") String orderType,@Context SearchContext context) throws RestException {
		try{
		logger.debug("Searching resources by limit from "+lowerLimit+" to "+upperLimit+" order by {} and orderType {} ",orderBy,orderType);
		//return infogramActiveResourceService.searchWithLimit(context,upperLimit,lowerLimit);
		return infogramActiveResourceService.searchWithLimitAndOrderBy(context,upperLimit,lowerLimit,orderBy,orderType);
	}catch(Exception ex)
	{
		logger.error("Error Inside  @class :"+this.getClass().getName()+" @Method :search"+ex.getMessage());
		//throw new Exception(ex.getMessage());
		throw new RestException(ExceptionUtil.generateExceptionCode("Rest","InfogramActiveResource",ex));
	}
		
	}
	
	@GET
	@Path("count")
	@Produces("application/json")
	public Long count(@Context SearchContext context) throws RestException {

		try{
			logger.debug("Counting InfogramActiveResource ");
			return infogramActiveResourceService.count(context);
		}catch(Exception ex)
		{
			logger.error("Error Inside  @class :"+this.getClass().getName()+" @Method :search"+ex.getMessage());		
			throw new RestException(ExceptionUtil.generateExceptionCode("Rest","InfogramActiveResource",ex));
		}
	}
	
	@POST
	@Path("create")
	@Produces("application/json")
	@Consumes("application/json")
	public InfogramActiveResource create(@Valid InfogramActiveResource infogramActiveResource) throws RestException {
		try{
		logger.debug("Create InfogramActiveResource inside create methode"+infogramActiveResource.toString());
		//return infogramActiveResourceService.searchWithLimit(context,upperLimit,lowerLimit);
		return infogramActiveResourceService.create(infogramActiveResource);
	}catch(Exception ex)
	{
		logger.error("Error Inside  @class :"+this.getClass().getName()+" @Method :search"+ex.getMessage());
		//throw new Exception(ex.getMessage());
		throw new RestException(ExceptionUtil.generateExceptionCode("Rest","InfogramActiveResource",ex));
	}
	}
	
	@POST
	@Path("update")
	@Produces("application/json")
	@Consumes("application/json")
	public InfogramActiveResource update(@Valid InfogramActiveResource infogramActiveResource) throws RestException {
		try{
		logger.debug("Update InfogramActiveResource inside update methode"+infogramActiveResource.toString());
	
		return infogramActiveResourceService.update(infogramActiveResource);
	}catch(Exception ex)
	{
		logger.error("Error Inside  @class :"+this.getClass().getName()+" @Method :update"+ex.getMessage());
		//throw new Exception(ex.getMessage());
		throw new RestException(ExceptionUtil.generateExceptionCode("Rest","InfogramActiveResource",ex));
	}
	}
	
	
	@DELETE
	@Path("/{id}")
	public void removeById(@PathParam("id") Integer primaryKey) throws RestException {
		try{
		logger.debug("Call for removing FileResource Object by primary key :"
				+ primaryKey);
		infogramActiveResourceService.removeById(primaryKey);
	}catch(Exception ex)
	{
		logger.error("Error  occurred  @class"   + this.getClass().getName()  , ex);
		throw new RestException(ExceptionUtil.generateExceptionCode("Rest","FileResource",ex));
	}
	}
	
}