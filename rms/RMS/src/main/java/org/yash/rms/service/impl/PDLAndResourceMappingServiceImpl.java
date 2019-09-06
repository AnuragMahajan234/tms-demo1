package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.PDLAndResourceMappingDao;
import org.yash.rms.service.PDLAndResourceMappingService;

@Service
public class PDLAndResourceMappingServiceImpl implements PDLAndResourceMappingService {

	@Autowired
	PDLAndResourceMappingDao pdlAndResourceMappingDao;
	
	public Integer getResourceIdByPDLId(Integer pdl_id) {
		return pdlAndResourceMappingDao.getResourceIdByPDLId(pdl_id);
	}

	public List<Integer> getPdlIdsByResourceId(Integer resource_id) {
		return pdlAndResourceMappingDao.getPDLIdbyResourceId(resource_id);
	}

	public void deleteAll() {
		pdlAndResourceMappingDao.deleteAll();
	}

}
