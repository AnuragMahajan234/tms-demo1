package org.yash.rms.rest;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.apache.cxf.jaxrs.ext.search.SearchContext;
import org.codehaus.jackson.map.annotate.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.domain.MessageBoard;
import org.yash.rms.domain.Resource;
import org.yash.rms.exception.RestException;
import org.yash.rms.rest.utils.ExceptionUtil;
import org.yash.rms.service.MessageBoardService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.View;

@Path("/resource")
@Service("ResourceRestImpl")
public class ResourceRestImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceRestImpl.class);
	
	@Autowired
	public ResourceService resourceService;
	
	
	@Autowired
	public MessageBoardService messageBoardService;
	
	@GET
	@Path("search")
	@Produces("application/json")
	public List<Resource> search(@DefaultValue("0") @QueryParam("llimit") Integer lowerLimit, @DefaultValue("9") @QueryParam("ulimit") Integer upperLimit, @Context SearchContext context) throws RestException {
		try{
		logger.debug("Searching resources by limit from "+lowerLimit+" to "+upperLimit);
		return resourceService.findAllResources(lowerLimit, upperLimit,context);
		//return "success";
	}catch(Exception ex)
	{
		logger.error("Error Inside  @class :"+this.getClass().getName()+" @Method :search"+ex.getMessage());
		//throw new Exception(ex.getMessage());
		throw new RestException(ExceptionUtil.generateExceptionCode("Rest","ResourceRest",ex));
	}
		
	}
	
	@GET
	@Path("findById/{id}")
	@Produces("application/json")
	@JsonView(View.MyJSONVIEW.class)
	public Resource findById(@PathParam("id") Integer id)
	{
		return resourceService.findById(id);
	}
	
	
	@POST
	@Path("createMessage")
	@Produces("application/json")
	public MessageBoard createMessage(MessageBoard messageBoard) throws RestException
	{
		try {
			return messageBoardService.create(messageBoard);
		} catch (RestException ex) {
			// TODO Auto-generated catch block
			throw new RestException(ExceptionUtil.generateExceptionCode("Rest","ResourceRest",ex));
		}
	}
	
	@POST
	@Path("updateMessage")
	@Produces("application/json")
	public MessageBoard updateMessage(MessageBoard messageBoard) throws RestException
	{
		try {
			return messageBoardService.update(messageBoard);
		} catch (RestException ex) {
			// TODO Auto-generated catch block
			throw new RestException(ExceptionUtil.generateExceptionCode("Rest","ResourceRest",ex));
		}
	}

}
