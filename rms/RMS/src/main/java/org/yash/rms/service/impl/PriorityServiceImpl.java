/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.PriorityDao;
import org.yash.rms.domain.Priority;
import org.yash.rms.dto.PriorityDTO;
import org.yash.rms.service.PriorityService;
import org.yash.rms.util.DozerMapperUtility;

/**
 * @author samiksha.sant
 *
 */
@Service
public class PriorityServiceImpl implements PriorityService {

	@Autowired
	PriorityDao priorityDao;
	
	@Autowired
	DozerMapperUtility mapperUtility;
	
	/* (non-Javadoc)
	 * @see org.yash.rms.service.PriorityService#getAllPriorityTypes()
	 */
	public List<PriorityDTO> getAllPriorityTypes() {
		List<Priority> priorities =  priorityDao.getAllPriorityTypes();
		List<PriorityDTO> priorityDTOs = mapperUtility.mapPriorityDomainToDTO(priorities);
		return priorityDTOs;
	}

}
