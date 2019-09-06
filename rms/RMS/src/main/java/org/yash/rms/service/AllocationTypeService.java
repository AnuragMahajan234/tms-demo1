package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.AllocationType;

public interface AllocationTypeService extends RmsCRUDService<AllocationType> {
	public void saveAllocatioType(AllocationType allocationType);
	public boolean delete(int id);
	public boolean saveOrupdate(AllocationType t);
	public List<AllocationType> findAll();
	public List<AllocationType> findByEntries(int firstResult, int sizeNo);
	public long countTotal();
	public List<AllocationType> getActiveAllocationTypesForRRF();
	public AllocationType getAllocationTypeById (Integer id);
	
}
