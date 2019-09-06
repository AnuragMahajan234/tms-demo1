package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.AllocationType;

public interface AllocationTypeDao extends RmsCRUDDAO<AllocationType>  {
	public List<AllocationType> getActiveAllocationTypesForRRF();
	public long countTotal();
	public List<AllocationType> findByEntries(int firstResult, int sizeNo);
	public boolean saveOrupdate(AllocationType allocationType);
	public List<AllocationType> findAll();
	public boolean delete(int id);
	public void saveGrade(AllocationType allocationType) throws Exception;
	public AllocationType getAllocationTypeById (Integer id);
	
	public AllocationType getAllocationTypeByType(final String allocationType);
}
