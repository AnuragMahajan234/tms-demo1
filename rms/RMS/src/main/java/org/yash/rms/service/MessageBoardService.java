package org.yash.rms.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.domain.MessageBoard;
import org.yash.rms.domain.Resource;
import org.yash.rms.exception.RMSServiceException;
import org.yash.rms.exception.RestException;
import org.yash.rms.rest.service.generic.IGenericService;

public interface MessageBoardService extends IGenericService<Integer, MessageBoard> {

	List<MessageBoard> getAllMessageList(String messageStatus, HttpServletRequest request);

	Long totalCountForMessagesList(String messageStatus, HttpServletRequest request) throws Exception;

	void saveEditedMessage(Integer id, String text, Resource loggedInResource)  throws RMSServiceException;

	public void rejectMessage(Integer id, Resource loggedInResource) throws RMSServiceException;
	
	public void approveMessage(Integer id, Resource loggedInResource) throws RMSServiceException;

	void addMessage(String messageText, Resource loggedInResource) throws RMSServiceException;

	public void deleteMessage(Integer id, Resource loggedInResource)  throws RMSServiceException;

	List<MessageBoard> getApprovedMessages();

	public MessageBoard findById(Integer id) throws RMSServiceException;
	
	public MessageBoard create(MessageBoard messageBoard) throws RestException;

	MessageBoard update(MessageBoard messageBoard)  throws RestException;


}
