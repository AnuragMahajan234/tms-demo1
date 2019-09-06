package org.yash.rms.exception;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.yash.rms.rest.service.generic.IRequestScopeDataService;
import org.yash.rms.rest.utils.ExceptionUtil;


/**
 * The Class RestExceptionMapper.
 * @version 1.0
 *
 */
 public class RestExceptionMapper implements ExceptionMapper<RestException>{

      /** The request data. */
	 @Autowired
		private IRequestScopeDataService requestData;
		
		
		/**
		 * To response.
		 *
		 * @param exception the exception
		 * @return the response
		 */
	 //@Override
		public Response toResponse(RestException exception) {

			
			Map<String, Throwable> exceptionMap = requestData.getExceptionList();
			if(exceptionMap==null){
		
		String exeMsg=exception.getMessage();
				 if(exeMsg!=null){
				  exeMsg = exeMsg.replaceAll("[^a-zA-Z0-9~!@#$%^&*()_+`-{}|\\/.,';:=]", " ");
				  }
/*			return Response.status(Status.OK)
					.type(MediaType.APPLICATION_JSON)
					.entity("[{\"errorMsg\": \""+exeMsg+"\",\"errorCode\": \""+exception.getErrCode()+"\",\"userExcepMsg\": \""+getMsgForCode(exception.getErrCode())+"\"}]")
					.build();*/
			return Response.status(Status.OK)
					.type(MediaType.APPLICATION_JSON)
					.entity("[{\"errorMsg\": \""+exeMsg+"\",\"errorCode\": \""+exception.getErrCode()+"\"}]")
					.build();
			}else
			{
				String msg="[";
				
				Iterator entries = exceptionMap.entrySet().iterator();
				while (entries.hasNext()) {
				  Entry<String, Throwable> thisEntry = (Entry) entries.next();
				  Object key = thisEntry.getKey();
				  Throwable value = thisEntry.getValue();
				  if(!"RollbackException".equalsIgnoreCase(value.getClass().getSimpleName())){
				  
				  String exeMsg=value.getMessage();
				 if(exeMsg!=null){
				  exeMsg = exeMsg.replaceAll("[^a-zA-Z0-9~!@#$%^&*()_+`-{}|\\/.,';:=]", " ");
				  }
				  /*msg+="{\"errorMsg\": \""+exeMsg+"\",\"errorCode\": \""+key.toString()+"\",\"userExcepMsg\": \""+getMsgForCode(key.toString())+"\"},";*/
				 msg+="{\"errorMsg\": \""+exeMsg+"\",\"errorCode\": \""+key.toString()+"\"},";
				  }
				  }
				msg=msg.substring(0,msg.length()-1);
				msg+="]";
				return  Response.status(Status.OK)
						.type(MediaType.APPLICATION_JSON)
						.entity(msg)
						.build();
			}
		}
			
		/**
		 * Gets the msg for code.
		 *
		 * @param code the code
		 * @return the msg for code
		 */
		private String getMsgForCode(String code)
		{
			try{/*
			UserConfig userconfig = CustomerInfo.getLocaleInContext();
			if(userconfig!=null){
				if(userconfig.getUserLanguage().toString().equalsIgnoreCase("en"))
				{
					return ExceptionUtil.getMessageProp(code);
				}else
				{
					return  ExceptionUtil.getMessageProp(code);
				}
				}else
				{
					return  ExceptionUtil.getMessageProp(code);
				}*/
				return  ExceptionUtil.getMessageProp(code);
		}catch(Exception e)
		{
			return  ExceptionUtil.getMessageProp(code);
		}
		}
	}
