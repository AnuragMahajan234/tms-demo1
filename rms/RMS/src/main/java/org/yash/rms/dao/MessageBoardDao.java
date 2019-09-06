package org.yash.rms.dao;

import java.util.List;


import org.yash.rms.domain.MessageBoard;
import org.yash.rms.domain.Resource;
import org.yash.rms.exception.DAOException;
import org.yash.rms.util.SearchCriteriaGeneric;

public interface MessageBoardDao {

	public List<MessageBoard> getAllMessageList(String messageStatus, SearchCriteriaGeneric searchCriteriaGeneric);

	public Long getTotalCountForMessagesList(String messageStatus, SearchCriteriaGeneric searchCriteriaGeneric);
	
	public void updateMessageObject(MessageBoard messageBoardObj) throws DAOException;
	
	public MessageBoard findById(Integer id) throws DAOException;

	public List<MessageBoard> getApprovedMessages();
	
	public MessageBoard create(MessageBoard messageBoard);

	public MessageBoard update(MessageBoard messageBoard);
	public Resource findByEmployeeId(int id) ;

}
