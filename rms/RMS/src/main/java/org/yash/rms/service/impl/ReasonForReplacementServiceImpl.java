package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ReasonForReplacementDao;
import org.yash.rms.dto.ReasonForReplacementDTO;
import org.yash.rms.util.DozerMapperUtility;

@Service
public class ReasonForReplacementServiceImpl implements org.yash.rms.service.ReasonForReplacementService {

	@Autowired
	ReasonForReplacementDao reasonForReplacementDao;

	@Autowired
	DozerMapperUtility dozerMapperUtility;

	public List<ReasonForReplacementDTO> getAllReasons() {
		List<ReasonForReplacementDTO> dtoList = dozerMapperUtility.convertReasonsDomainToDTO(reasonForReplacementDao.getAllReasons());
		return dtoList;
	}

}
