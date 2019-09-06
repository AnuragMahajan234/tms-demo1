package org.yash.rms.rest.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.yash.rms.rest.service.generic.IRequestScopeDataService;
import org.yash.rms.util.AppContext;
import org.apache.commons.codec.binary.Base64;
/**
 * The Class ExceptionUtil.
 * @version 1.0
 *
 */
public class ExceptionUtil {

	
	/** The logger. */
	private static final Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);
	
	/** The model code. */
	private static Map<String,String> modelCode;
	
	/** The layer code. */
	private static Map<String,String> layerCode;
	
	/** The exception code. */
	private static Map<String,String> exceptionCode;

	/** The msg. */
	//private static PropertiesConfiguration msg;
	
	
	static
	{
		/*try {
			msg= new PropertiesConfiguration(ConfigUtil.MSG_PROPS);
			
			msg.setReloadingStrategy(new FileChangedReloadingStrategy());
		} catch (ConfigurationException e) {
			  logger.error(e.getMessage());
		}*/
		
		
		layerCode=new HashMap<String, String>();
		modelCode=new HashMap<String, String>();
		exceptionCode=new HashMap<String, String>();
		
		
					modelCode.put("Visa","100");
					modelCode.put("UserTimeSheet","101");
					modelCode.put("UserProfile","102");
					modelCode.put("UserNotification","103");
					modelCode.put("UserActivity","104");
					modelCode.put("ResourceComment","105");
					modelCode.put("RequestRequisition","106");
					modelCode.put("RequestRequisitionSkill","107");
					modelCode.put("Ownership","108");
					modelCode.put("Address","109");
					modelCode.put("InfogramInctiveResource","110");
					modelCode.put("ResourceAllocation","111");
					modelCode.put("Project","112");
					modelCode.put("Resource","113");
					modelCode.put("InfogramActiveResource","114");
				
	   
	    	
		
       	   
		layerCode.put("Rest","1");
		layerCode.put("Service","2");
		layerCode.put("Dao","3");
		
		exceptionCode.put("Exception","000");
		exceptionCode.put("JSONException","001");
		exceptionCode.put("IllegalAccessException","002");
		exceptionCode.put("InvocationTargetException","003");
		exceptionCode.put("SecurityException","004");
		exceptionCode.put("NoSuchMethodException","005");
		exceptionCode.put("NoResultException","006");
		exceptionCode.put("DataAccessException","007");
		exceptionCode.put("EmptyResultDataAccessException","008");
		exceptionCode.put("ValueNotFoundException","009");
		exceptionCode.put("ParseException","010");
		exceptionCode.put("BusinessException","011");
		exceptionCode.put("AddressNotFoundException","012");
		exceptionCode.put("DataIntegrityViolationException","013");
		exceptionCode.put("ConstraintViolationException","014");
		exceptionCode.put("IOException","015");
		exceptionCode.put("DocumentMissingException","016");
		exceptionCode.put("NullPointerException","017");
		exceptionCode.put("AddressException","018");
		exceptionCode.put("MessagingException","019");
		exceptionCode.put("VelocityException","020");
		exceptionCode.put("URISyntaxException","021");
		exceptionCode.put("PasswordValidationFailedException","022");
		exceptionCode.put("HttpException","023");
		exceptionCode.put("PersistenceException","024");
		exceptionCode.put("TransactionSystemException","025");
		exceptionCode.put("ConstraintViolationExceptionDuplicate","026");
		exceptionCode.put("MySQLIntegrityConstraintViolationException","027");
		exceptionCode.put("MySQLIntegrityConstraintViolationExceptionDuplicate","028");
		exceptionCode.put("MySQLIntegrityConstraintViolationExceptionLength","029");
		exceptionCode.put("ConstraintViolationExceptionLength","030");
		exceptionCode.put("MySQLIntegrityConstraintViolationExceptionUnique","031");
		exceptionCode.put("ConstraintViolationExceptionUnique","032");
		exceptionCode.put("MysqlDataTruncation","033");
	    exceptionCode.put("QueryException","034"); 
	    exceptionCode.put("ConnectException","035");
	    exceptionCode.put("WebServiceException","036");
	    exceptionCode.put("UnknownHostException","037");
	    exceptionCode.put("UnsupportedEncodingException","038");
	    exceptionCode.put("SAXException","039");
	    exceptionCode.put("QueryTimeoutException","040");
		exceptionCode.put("HibernateJdbcException","041");
		exceptionCode.put("SQLException","042");


		
	}
	
	/**
	 * Time (in milliseconds) after which all custom indices are recalculated.
	 *
	 * @param key the key
	 * @return the message prop
	 */

	/**
	 * sms email configuration keys
	 */
	
	



	public static String getMessageProp(String key)
	{		
			//return (String) msg.getProperty(key);
		return key;
	}
	
	
	/**
	 * Generate exception code.
	 *
	 * @param layer the layer
	 * @param model the model
	 * @param e the e
	 * @return the string
	 */
	public static String generateExceptionCode(String layer,String model ,Throwable e)
	{
		String code="";
		String codeLayer=layerCode.get(layer);
		if(codeLayer==null)
		{
			codeLayer="9";
		}
		code+=codeLayer;
		
		 codeLayer=modelCode.get(model);
		 if(codeLayer==null)
			{
			 codeLayer="999";
			}
		 code+=codeLayer;
		 
		 codeLayer=exceptionCode.get(e.getClass().getSimpleName());
		 
		
		
			if(e.getMessage()!=null && e.getMessage().contains("Duplicate"))
			{
				 codeLayer=exceptionCode.get(e.getClass().getSimpleName()+"Duplicate");	
			}
		 
			
		 if(codeLayer==null)
			{
			 codeLayer="999";
			}
		 code+=codeLayer;
		 if(e.getCause()!=null)
		  {
			  
			  generateExceptionCode(layer,model ,e.getCause());
		  }else
		  {
			 IRequestScopeDataService requestData = AppContext.getApplicationContext().getBean(IRequestScopeDataService.class);
			 requestData.setExceptionInList(code, e);
		  }
		return code;
	}
	
	
	
}
