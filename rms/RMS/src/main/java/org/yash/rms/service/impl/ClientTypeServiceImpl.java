package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ClientTypeDao;
import org.yash.rms.domain.ClientType;
import org.yash.rms.dto.ClientTypeDTO;
import org.yash.rms.service.ClientTypeService;

@Service("ClientTypeService")
public class ClientTypeServiceImpl implements ClientTypeService {

	@Autowired
	@Qualifier("mapper")
	DozerBeanMapper mapper;
	
	
	@Autowired
	ClientTypeDao clientTypeDao;
	
	public List<ClientType> getAllClientTypes() {
		
		return clientTypeDao.getAllClientTypes();
	}

}
