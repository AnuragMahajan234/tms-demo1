package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.ShiftTypes;

public interface ShiftTypeDao {

	public List<ShiftTypes> getAllShiftTypes();
	public ShiftTypes getShiftTypeById(Integer id);
}
