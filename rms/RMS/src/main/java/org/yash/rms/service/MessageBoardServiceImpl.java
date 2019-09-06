package org.yash.rms.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.jaxrs.ext.search.SearchContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import org.yash.rms.dao.MessageBoardDao;
import org.yash.rms.domain.MessageBoard;
import org.yash.rms.domain.Resource;
import org.yash.rms.exception.DAOException;
import org.yash.rms.exception.DaoRestException;
import org.yash.rms.exception.RMSServiceException;
import org.yash.rms.exception.RestException;
import org.yash.rms.util.AppContext;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DataTableParser;
import org.yash.rms.util.SearchCriteriaGeneric;
import org.yash.rms.util.UserUtil;
import org.yash.rms.util.messageBoardStatusConstants;



@Service
@Transactional
public class MessageBoardServiceImpl implements MessageBoardService{
	
	@Autowired
	private MessageBoardDao messageBoardDao;
	
	@Autowired
	ResourceService resourceService;
	
	private static final Logger logger = LoggerFactory.getLogger(MessageBoardServiceImpl.class);

	public List<MessageBoard> getAllMessageList(String messageStatus,HttpServletRequest request) {
		logger.info("------------------getAllMessageList for dataTable method start-----------");
		SearchCriteriaGeneric searchCriteriaGeneric=DataTableParser.getSerchCriteria(request,new MessageBoard());
	    List<MessageBoard> messageList =  messageBoardDao.getAllMessageList(messageStatus,searchCriteriaGeneric);
		logger.info("------------------getAllMessageList for dataTable method end-----------");
		return messageList;
	}

	public Long totalCountForMessagesList(String messageStatus, HttpServletRequest request) throws Exception{
		logger.info("------------------totalCountForMessagesList method start-----------");
		Long count = 0L;
		SearchCriteriaGeneric searchCriteriaGeneric=DataTableParser.getSerchCriteria(request,new MessageBoard());
		try {
		count =  messageBoardDao.getTotalCountForMessagesList(messageStatus,searchCriteriaGeneric);
		
		}catch (Exception e) {

			count = 0L;
			e.printStackTrace();
			logger.error("Exception Occurred while counting message "+ e.getMessage());
			throw e;
		}
		logger.info("------------------totalCountForMessagesList method start-----------");
		return count;
	}

	@Transactional
	public void saveEditedMessage(Integer id, String text, Resource loggedInResource)  throws RMSServiceException{
		logger.info("------------------saveEditedMessage method start-----------");
		RMSServiceException serviceException = new RMSServiceException();
		MessageBoard messageBoardObj = null;
		try {
			messageBoardObj = messageBoardDao.findById(id);
			messageBoardObj.setText(text);
			messageBoardObj.setModifiedBy(loggedInResource.getEmployeeId());
			messageBoardObj.setModifiedName(loggedInResource.getEmployeeName());
			messageBoardObj.setModifiedTime(new Date(System.currentTimeMillis()));
			messageBoardDao.updateMessageObject(messageBoardObj);
		}catch (DAOException exception) {
			if(exception instanceof DAOException) {
                  serviceException.setErrCode(((DAOException) exception).getErrCode());
            }else {
                  ((RMSServiceException) serviceException).setErrCode("404");
            }
            ((RMSServiceException) serviceException).setMessage(exception.getMessage());           
            throw serviceException;
		} 
		logger.info("------------------saveEditedMessage method end-----------");
	}

	@Transactional
	public void addMessage(String text, Resource loggedInResource)  throws RMSServiceException{
		logger.info("------------------addMessage method start-----------");
		RMSServiceException serviceException = new RMSServiceException();
		MessageBoard messageBoardObj = new MessageBoard();
		try {
			Integer loggedInEmpId = loggedInResource.getEmployeeId();
			String loggedInEmpName = loggedInResource.getEmployeeName();
			messageBoardObj.setText(text);
			messageBoardObj.setMessageStatus(messageBoardStatusConstants.NEW.toString());
			//messageBoardObj.setCreatedBy(loggedInEmpId);
			//messageBoardObj.setCreatedName(loggedInEmpName);
			//messageBoardObj.setCreatedTime(new Date(System.currentTimeMillis()));
			
			//messageBoardObj.setModifiedBy(loggedInEmpId);
			//messageBoardObj.setModifiedName(loggedInEmpName);
			//messageBoardObj.setModifiedTime(new Date(System.currentTimeMillis()));
			messageBoardDao.updateMessageObject(messageBoardObj);
		}catch (DAOException exception) {
			if(exception instanceof DAOException) {
                  serviceException.setErrCode(((DAOException) exception).getErrCode());
            }else {
                  ((RMSServiceException) serviceException).setErrCode("404");
            }
            ((RMSServiceException) serviceException).setMessage(exception.getMessage());           
            throw serviceException;
		} 
		logger.info("------------------addMessage method end-----------");
	}
	
	@Transactional
	public void rejectMessage(Integer id, Resource loggedInResource ) throws RMSServiceException {
		logger.info("------------------rejectMessage method start-----------");
		MessageBoard messageBoardObj = null;
		try {
			messageBoardObj = messageBoardDao.findById(id);
				if(messageBoardObj!=null){
					messageBoardObj.setModifiedBy(loggedInResource.getEmployeeId());
					messageBoardObj.setModifiedName(loggedInResource.getEmployeeName());
					messageBoardObj.setModifiedTime(new Date(System.currentTimeMillis()));
					messageBoardObj.setMessageStatus(messageBoardStatusConstants.REJECTED.toString());
					messageBoardDao.updateMessageObject(messageBoardObj);
					logger.info("------------------rejectMessage method end-----------");
				}
				else
				{
					throw new RMSServiceException("404","Message Not found in MessageBoard ");
				}
		}
		catch(DAOException exception) {
            throw new RMSServiceException(exception.getErrCode(),exception.getMessage());
		}
	}
	
	@Transactional
	public void deleteMessage(Integer id, Resource loggedInResource ) throws RMSServiceException {
		logger.info("------------------deleteMessage method start-----------");
		MessageBoard messageBoardObj = null;
		try {
			messageBoardObj = messageBoardDao.findById(id);
				if(messageBoardObj!=null){
					messageBoardObj.setModifiedBy(loggedInResource.getEmployeeId());
					messageBoardObj.setModifiedName(loggedInResource.getEmployeeName());
					messageBoardObj.setModifiedTime(new Date(System.currentTimeMillis()));
					messageBoardObj.setIsDeleted(true);
					messageBoardDao.updateMessageObject(messageBoardObj);
					logger.info("------------------deleteMessage method end-----------");
				}
				else
				{
					throw new RMSServiceException("404","Message Not found in MessageBoard ");
				}
		}
		catch(DAOException exception) {
            throw new RMSServiceException(exception.getErrCode(),exception.getMessage());
		}
	}
	
	@Transactional
	public void approveMessage(Integer id, Resource loggedInResource ) throws RMSServiceException {
		logger.info("------------------approveMessage method start-----------");
		MessageBoard messageBoardObj = null;
		try {
			messageBoardObj = messageBoardDao.findById(id);
				if(messageBoardObj!=null){
					Integer loggedInEmpId = loggedInResource.getEmployeeId();
					String loggedInEmpName = loggedInResource.getEmployeeName();
					messageBoardObj.setModifiedBy(loggedInEmpId);
					messageBoardObj.setModifiedName(loggedInEmpName);
					messageBoardObj.setModifiedTime(new Date(System.currentTimeMillis()));
					messageBoardObj.setApprovedBy(loggedInEmpId);
					messageBoardObj.setApprovedName(loggedInEmpName);
					messageBoardObj.setMessageStatus(messageBoardStatusConstants.APPROVED.toString());
					messageBoardDao.updateMessageObject(messageBoardObj);
					logger.info("------------------approveMessage method end-----------");
				}
				else
				{
					throw new RMSServiceException("404","Message Not found in MessageBoard ");
				}
		}
		catch(DAOException exception) {
            throw new RMSServiceException(exception.getErrCode(),exception.getMessage());
		}
	}

	
	public List<MessageBoard> getApprovedMessages() {
		logger.info("------------------getApprovedMessages method start-----------");
		List<MessageBoard> myMessages = new ArrayList<MessageBoard>();
		myMessages = messageBoardDao.getApprovedMessages();
		logger.info("------------------getApprovedMessages method end-----------");
		return myMessages;
		
	}

	
	public MessageBoard findById(Integer id) throws RMSServiceException {
		logger.info("------------------findById method start-----------");
		MessageBoard messageBoardObj = null;
		try
		{
			messageBoardObj = messageBoardDao.findById(id);
		}catch(DAOException exception) {
            throw new RMSServiceException(exception.getErrCode(),exception.getMessage());
		}
		logger.info("------------------findById method end-----------");
		return messageBoardObj;
	}

	public List<MessageBoard> search(MessageBoard entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MessageBoard> searchWithLimit(SearchContext context, Integer maxLimit, Integer minLimit)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MessageBoard> searchWithLimitAndOrderBy(SearchContext ctx, Integer maxLimit, Integer minLimit,
			String orderby, String orderType) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeById(Integer primaryKey) throws RestException, DaoRestException {
		// TODO Auto-generated method stub
		
	}

	@Transactional
	public MessageBoard create(MessageBoard messageBoard) throws RestException {
		messageBoard.setMessageStatus(messageBoardStatusConstants.NEW.toString());
		// TODO Auto-generated method stub
		return messageBoardDao.create(messageBoard);
	}
	
	@Transactional
	public MessageBoard update(MessageBoard messageBoard) throws RestException {
		//messageBoard.setMessageStatus(messageBoardStatusConstants.NEW.toString());
		// TODO Auto-generated method stub
		MessageBoard messageBoard1=messageBoardDao.findById(messageBoard.getId());
		messageBoard1.setText(messageBoard.getText());
		return messageBoardDao.update(messageBoard1);
	}
	
	
}
