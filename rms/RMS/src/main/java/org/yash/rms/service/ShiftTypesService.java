package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.ShiftTypes;

public interface ShiftTypesService {
	
	public List<ShiftTypes> getAllShiftTypes();

	public ShiftTypes getShiftTypeById(Integer id);

}
