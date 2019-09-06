package org.yash.rms.controller;

import java.net.URLEncoder;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yash.rms.domain.MessageBoard;
import org.yash.rms.domain.Resource;
import org.yash.rms.exception.ControllerException;
import org.yash.rms.exception.DAOException;
import org.yash.rms.exception.RMSServiceException;
import org.yash.rms.service.MessageBoardService;
import org.yash.rms.util.ExceptionConstant;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/messageboard")
public class MessageBoardController {

	private static final Logger logger = LoggerFactory.getLogger(MessageBoardController.class);
	
	@Autowired
	MessageBoardService messageBoardService;
	
	@Autowired
	private UserUtil userUtil;
	
	@RequestMapping(value = "/getallmessagelist/{allmessageStatusVar}",method = RequestMethod.GET)
	public ResponseEntity<String> getAllMessageList(@PathVariable(value="allmessageStatusVar") String messageStatus,@RequestParam(value="sEcho") String sEcho,@RequestParam(value="iDisplayStart", defaultValue="0") Integer page, @RequestParam(value="iDisplayLength", defaultValue="10") Integer size,HttpServletRequest request) {

		logger.info("--------getAllMessageList method starts--------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		long totalCount=0;
		JSONObject resultJSON = new JSONObject();
		
		List<MessageBoard> activeMessageList = messageBoardService.getAllMessageList(messageStatus,request);

		totalCount = totalCountForMessagesList(messageStatus,request).longValue();
		resultJSON.put("sEcho", sEcho);
		resultJSON.put("iTotalRecords", totalCount);
		resultJSON.put("iTotalDisplayRecords", totalCount);
		
		resultJSON.put("aaData", activeMessageList);

        logger.info("--------getAllMessageList method ends--------");
        return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);

	}
	
	private Long totalCountForMessagesList(String messageStatus, HttpServletRequest request){
		logger.info("--------totalCountForMessagesList method starts--------");
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Long totalCount = 0L;
        try {
			totalCount = messageBoardService.totalCountForMessagesList(messageStatus, request);
		} catch (Exception e) {			
			e.printStackTrace();
		}
        logger.info("--------totalCountForMessagesList method ends--------");
        return totalCount;

	}
	
	
	/*
	 * It will be called from menudashboard and by default
	 */
	@RequestMapping(value = "/getallmessages", method = RequestMethod.GET)
	public String getAllMessages(Model uiModel) {

		logger.info("--------getallmessages method starts--------");

		Resource resource = userUtil.getLoggedInResource();
		try {
			if (resource.getUploadImage() != null
					&& resource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage,"UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			} else {
				uiModel.addAttribute("UserImage", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(resource.getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		uiModel.addAttribute("DOJ", months + "-" + year);
		uiModel.addAttribute("ROLE", resource.getUserRole());
		uiModel.addAttribute("firstName", resource.getFirstName()+ " "+ resource.getLastName());
		uiModel.addAttribute("designationName", resource.getDesignationId().getDesignationName());
        logger.info("--------getallmessages method ends--------");
        return "messageboard/list";

	}
	
	/**
	 * Edit API will update the text of message. 
	 * @param id
	 * @return
	 */	
	@RequestMapping(value = "/saveEditedMessage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> saveEditedMessage( MessageBoard updatedMessage ) throws ControllerException {
		logger.info("--------saveEditedMessage method starts--------");
		ControllerException controllerException = new ControllerException();
		String response = null;
		Resource resource=null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		try
		{
			resource=userUtil.getLoggedInResource();
			 messageBoardService.saveEditedMessage(updatedMessage.getId(), updatedMessage.getText(), resource);			
		
				controllerException.setErrCode("200");
				controllerException.setErrMsg(ExceptionConstant.OK);
				response = ControllerException.toJsonArray(controllerException);
			
		}
		catch(Exception exception) {
            if(exception instanceof RMSServiceException) {
                  controllerException.setErrCode(((RMSServiceException) exception).getErrCode());
            }else {
            	controllerException.setErrCode("422");
            }
            controllerException.setErrMsg(exception.getMessage());
            logger.error("Exception occured MessageBoardController saveEditedMessage Not successfully Updated in RMS:" + controllerException.getMessage());
            response = ControllerException.toJsonArray(controllerException);
            return new ResponseEntity<String>(response, headers, HttpStatus.EXPECTATION_FAILED);
         }
		logger.info("--------saveEditedMessage method ends--------");
		return new ResponseEntity<String>(response, headers, HttpStatus.OK);
     }
	
	/**
	 * Add API will add a new message. 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/addMessage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> addMessage( MessageBoard messageText ) throws ControllerException {
		
		logger.info("--------addMessage method starts--------");
		ControllerException controllerException = new ControllerException();
		String response = null;
		Resource resource=null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		try
		{
			resource=userUtil.getLoggedInResource();			
			messageBoardService.addMessage(messageText.getText(), resource);
			
		
				controllerException.setErrCode("200");
				controllerException.setErrMsg(ExceptionConstant.OK);
				response = ControllerException.toJsonArray(controllerException);
			
		}
		catch(Exception exception) {
            if(exception instanceof RMSServiceException) {
                  controllerException.setErrCode(((RMSServiceException) exception).getErrCode());
            }else {
            	controllerException.setErrCode("422");
            }
            controllerException.setErrMsg(exception.getMessage());
            logger.error("Exception occured MessageBoardController addMessage Not successfully executed in RMS:" + controllerException.getMessage());
            response = ControllerException.toJsonArray(controllerException);
            return new ResponseEntity<String>(response, headers, HttpStatus.EXPECTATION_FAILED);
         }
		logger.info("--------addMessage method ends--------");
		return new ResponseEntity<String>(response, headers, HttpStatus.OK);
     }
	
	/**
	 * Reject API will mark the status as REJECTED of the message. 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/rejectMessage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> rejectMessage( MessageBoard message ) throws ControllerException {
		logger.info("--------rejectMessage method starts--------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		ControllerException controllerException=new ControllerException();
		String response = null;
		Resource resource=null;
		try {
			resource = userUtil.getLoggedInResource();
			messageBoardService.rejectMessage(message.getId(), resource);
			controllerException.setErrCode("200");
			controllerException.setErrMsg(ExceptionConstant.OK);
			response = ControllerException.toJsonArray(controllerException);
			logger.info("--------rejectMessage method ends--------");
			return new ResponseEntity<String>(response,headers, HttpStatus.OK);
			
		}catch(Exception exception) {
            if(exception instanceof RMSServiceException) {
                controllerException.setErrCode(((RMSServiceException) exception).getErrCode());
          }else {
          	controllerException.setErrCode("422");
          }
          controllerException.setErrMsg(exception.getMessage());
          logger.error("Exception occured in rejectMessage: Not successfully Rejected in MessageBoard:" + controllerException.getMessage());
          response = ControllerException.toJsonArray(controllerException);
          return new ResponseEntity<String>(response,headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
						
	}
	
	
	@RequestMapping(value = "/deleteMessage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> deleteMessage( MessageBoard message ) throws ControllerException {
		logger.info("--------deleteMessage method starts--------");
		ControllerException controllerException=new ControllerException();
		String response = null;
		Resource resource=null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		try {
			resource = userUtil.getLoggedInResource();
			messageBoardService.deleteMessage(message.getId(), resource);
			controllerException.setErrCode("200");
			controllerException.setErrMsg(ExceptionConstant.OK);
			response = ControllerException.toJsonArray(controllerException);
			logger.info("--------deleteMessage method ends--------");
			return new ResponseEntity<String>(response,headers, HttpStatus.OK);
			
		}catch(Exception exception) {
            if(exception instanceof RMSServiceException) {
                controllerException.setErrCode(((RMSServiceException) exception).getErrCode());
          }else {
          	controllerException.setErrCode("422");
          }
          controllerException.setErrMsg(exception.getMessage());
          logger.error("Exception occured in deleteMessage: Not successfully Deleted in MessageBoard:" + controllerException.getMessage());
          response = ControllerException.toJsonArray(controllerException);
          return new ResponseEntity<String>(response,headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
						
	}
	
	/**
	 * Approve API will mark the status as APPROVED of the message. 
	 * @param id
	 * @return
	 */	
	@RequestMapping(value = "/approveMessage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> approveMessage( MessageBoard message ) throws ControllerException {
		logger.info("--------approveMessage method starts--------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		ControllerException controllerException=new ControllerException();
		String response = null;
		Resource resource=null;
		try {
			resource = userUtil.getLoggedInResource();
			messageBoardService.approveMessage(message.getId(), resource);
			controllerException.setErrCode("200");
			controllerException.setErrMsg(ExceptionConstant.OK);
			response = ControllerException.toJsonArray(controllerException);
			logger.info("--------approveMessage method ends--------");
			return new ResponseEntity<String>(response,headers, HttpStatus.OK);
			
		}catch(Exception exception) {
            if(exception instanceof RMSServiceException) {
                controllerException.setErrCode(((RMSServiceException) exception).getErrCode());
          }else {
          	controllerException.setErrCode("422");
          }
          controllerException.setErrMsg(exception.getMessage());
          logger.error("Exception occured in approveMessage: Not successfully Approved in MessageBoard:" + controllerException.getMessage());
          response = ControllerException.toJsonArray(controllerException);
          return new ResponseEntity<String>(response,headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
						
	}
	

	@RequestMapping(value = "/getMessage/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getMessage(@PathVariable("id") Integer id)  throws Exception{
		logger.info("------------------getMessage method start-----------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		JSONObject resultJSON = new JSONObject();
		MessageBoard messageBoardObj = messageBoardService.findById(id);
		String text = null;
		if(messageBoardObj!=null) {
			text = messageBoardObj.getText();
		}
		resultJSON.put("messageBoardObjText", text);
		logger.info("--------getMessage method ends--------");
        return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
	}
	
}
