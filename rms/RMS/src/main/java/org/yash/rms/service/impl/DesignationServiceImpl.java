/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.DesignationDao;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.Designation;
import org.yash.rms.service.DesignationService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;

/**
 * @author arpan.badjatiya
 *
 */
@Service("DesignationService")
public class DesignationServiceImpl implements DesignationService {

	@Autowired  
	DesignationDao designationDao;
	
	@Autowired
	private DozerMapperUtility mapper;
	
	public boolean delete(int id) {
		return designationDao.delete(id);
	}

	public boolean saveOrupdate(Designation designation) {
		return designationDao.saveOrupdate(mapper.convertDTOObjectToDomain(designation));
	}

	public List<Designation> findAll() {
		return mapper.convertDesignationDomainListToDTOList(designationDao.findAll());
	}

	public long countTotal() {
		return designationDao.countTotal();
	}

	public List<Designation> findByEntries(int firstResult, int sizeNo) {
		return mapper.convertDesignationDomainListToDTOList(designationDao.findByEntries(firstResult, sizeNo));
	}

	public Designation findById(int id) {
		// TODO Auto-generated method stub
		return designationDao.findById(id);
	}
	
	public Designation findByNameIgnoreSpace(String name) {
		return designationDao.findByNameIgnoreSpace(name);
	}

}
