package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.AllocationTypeDao;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.service.AllocationTypeService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;
@Service("allocatioTypeService")
public class AllocationTypeServiceImpl implements AllocationTypeService {
	@Autowired
	@Qualifier("allocationTypeDao")
    private  AllocationTypeDao allocationTypeDao;
	
	@Autowired
	private DozerMapperUtility mapper;
	
	public void saveAllocatioType(AllocationType allocationType) {
		
	}



	public boolean delete(int id) {
		return allocationTypeDao.delete(id);
	}


	public boolean saveOrupdate(AllocationType t) {
		return allocationTypeDao.saveOrupdate(mapper.convertDTOObjectToDomain(t));
	}

	public List<AllocationType> findAll() {
		return mapper.convertAllocationTypeDomainListToDTOList(allocationTypeDao.findAll());
	}

	public List<AllocationType> findByEntries(int firstResult, int sizeNo) {
		return null;
	}

	public long countTotal() {
		return 0;
	}

	public List<AllocationType> getActiveAllocationTypesForRRF(){
		return allocationTypeDao.getActiveAllocationTypesForRRF();
	}
	public AllocationType getAllocationTypeById(Integer id) {
		return allocationTypeDao.getAllocationTypeById(id);
	}
}
