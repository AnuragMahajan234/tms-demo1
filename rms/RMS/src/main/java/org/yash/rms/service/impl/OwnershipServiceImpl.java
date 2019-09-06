package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.OwnershipDao;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.Ownership;
import org.yash.rms.service.OwnershipService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;

@Service("OwnershipService")
public class OwnershipServiceImpl implements OwnershipService {

	@Autowired 
	OwnershipDao ownershipDao;
	
	@Autowired
	private DozerMapperUtility mapper;

	public boolean delete(int id) {
		return ownershipDao.delete(id);
	}

	public boolean saveOrupdate(Ownership ownership) {
		return ownershipDao.saveOrupdate(mapper.convertDTOObjectToDomain(ownership));
	}

	public List<Ownership> findAll() {
		return mapper.convertOwnershipDomainListToDTOList(ownershipDao.findAll());
	}

	public List<Ownership> findByEntries(int firstResult, int sizeNo) {
		return mapper.convertOwnershipDomainListToDTOList(ownershipDao.findByEntries(firstResult, sizeNo));
	}

	public long countTotal() {
		return ownershipDao.countTotal();
	}

	public Ownership findById(int id) {
		// TODO Auto-generated method stub
		return ownershipDao.findById(id);
	}
	


}
