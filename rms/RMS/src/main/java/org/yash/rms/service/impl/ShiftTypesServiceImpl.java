package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ShiftTypeDao;
import org.yash.rms.domain.ShiftTypes;
import org.yash.rms.service.ShiftTypesService;

@Service
public class ShiftTypesServiceImpl implements ShiftTypesService {

	@Autowired
	ShiftTypeDao shiftTypeDao;
	
	public List<ShiftTypes> getAllShiftTypes() {
		return shiftTypeDao.getAllShiftTypes();
	}

	public ShiftTypes getShiftTypeById(Integer id) {
		return shiftTypeDao.getShiftTypeById(id);
	}

}
