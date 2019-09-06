package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.Visa;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;

@Service("VisaService")
public class VisaServiceImpl implements RmsCRUDService<Visa> {

	@Autowired @Qualifier("VisaDao")
	RmsCRUDDAO<Visa> visaDao;

	@Autowired
	private DozerMapperUtility mapper;
	
	public boolean delete(int id) {
		return visaDao.delete(id);
	}

	public boolean saveOrupdate(Visa visa) {
		return visaDao.saveOrupdate(mapper.convertDTOObjectToDomain(visa));
	}

	public List<Visa> findAll() {
		return mapper.convertvisaDomainListToDTOList(visaDao.findAll());
	}

	public List<Visa> findByEntries(int firstResult, int sizeNo) {
		return mapper.convertvisaDomainListToDTOList(visaDao.findByEntries(firstResult, sizeNo));
	}

	public long countTotal() {
		return visaDao.countTotal();
	}
	


}
