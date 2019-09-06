package org.yash.rms.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.RequestRequisitionResourceStatusDao;
import org.yash.rms.domain.RequestRequisitionResourceStatus;
import org.yash.rms.service.RequestRequisitionResourceStatusService;

@Service("requestRequisitionResourceStatusService")
public class RequestRequisitionResourceStatusServiceImpl implements RequestRequisitionResourceStatusService{

	@Autowired
	@Qualifier("requestRequisitionResourceStatusDao")
	private RequestRequisitionResourceStatusDao requestRequisitionResourceStatusDao;
	
	private static final Logger logger = LoggerFactory.getLogger(RequestRequisitionResourceStatusServiceImpl.class);
			
	public RequestRequisitionResourceStatus findById(Integer id) {
		
			return requestRequisitionResourceStatusDao.findById(id);
	}

}
