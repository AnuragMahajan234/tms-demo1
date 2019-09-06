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
import org.yash.rms.dao.RoleDao;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.Roles;
import org.yash.rms.service.DesignationService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.service.RoleService;
import org.yash.rms.util.DozerMapperUtility;

/**
 * @author arpan.badjatiya
 *
 */
@Service("RoleService")
public class RoleServiceImpl implements RoleService {

	@Autowired  
	RoleDao roleDao;
	
	@Autowired
	private DozerMapperUtility mapper;
	
	public boolean delete(int id) {
		return roleDao.delete(id);
	}

	public boolean saveOrupdate(Roles role) {
		return roleDao.saveOrupdate(mapper.convertDTOObjectToDomain(role));
	}

	public List<Roles> findAll() {
		return  roleDao.findAll();
	}

	public long countTotal() {
		return roleDao.countTotal();
	}

	public List<Roles> findByEntries(int firstResult, int sizeNo) {
		return  roleDao.findByEntries(firstResult, sizeNo);
	}

	public Roles findById(int id) {
		// TODO Auto-generated method stub
		return roleDao.findById(id);
	}

	public Roles findByRole(String role) {
		// TODO Auto-generated method stub
		return roleDao.findByRole(role);
	}

}
