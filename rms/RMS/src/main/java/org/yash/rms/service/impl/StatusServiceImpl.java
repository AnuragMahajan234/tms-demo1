package org.yash.rms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.StatusDao;
import org.yash.rms.domain.Status;
import org.yash.rms.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService {

	@Autowired
	private StatusDao statusDao;
	
	private final String APPROVED =  "Approved";

	private final String DECLINED =  "Declined";
	
	public void createStatus(Status status) {
		
		status = statusDao.createStatus(status);
		String msg = status.getType()+ " is "+status.getAction()+" by "+status.getCreator();
		status.setActivity(msg);
	}

	public void approveStatus(Status status) {
		//status.setAction(APPROVED);
		status = statusDao.createStatus(status);
		String msg = status.getType()+ " is "+APPROVED+" by "+status.getCreator();
		status.setActivity(msg);
	}

	public void declinedStatus(Status status) {
		status = statusDao.createStatus(status);
		String msg = status.getType()+ " is "+DECLINED+" by "+status.getCreator();
		status.setActivity(msg);
	}

}
