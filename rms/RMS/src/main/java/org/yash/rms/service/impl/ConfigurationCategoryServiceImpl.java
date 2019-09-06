/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ConfigurationDao;
import org.yash.rms.dao.DesignationDao;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.dao.RoleDao;
import org.yash.rms.domain.ConfigurationCategory;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.Roles;
import org.yash.rms.service.ConfigurationCategoryService;
import org.yash.rms.service.DesignationService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.service.RoleService;
import org.yash.rms.util.DozerMapperUtility;

/**
 * @author arpan.badjatiya
 *
 */
@Service("ConfigurationService")
public class ConfigurationCategoryServiceImpl implements ConfigurationCategoryService {

	@Autowired  
	ConfigurationDao configuartionDao;
	
	@Autowired
	private DozerMapperUtility mapper;
	
	public boolean delete(int id) {
		return configuartionDao.delete(id);
	}

	public boolean saveOrupdate(ConfigurationCategory configuration) {
		return configuartionDao.saveOrupdate( configuration);
	}

	public List<ConfigurationCategory> findAll() {
		return  configuartionDao.findAll();
	}

	public long countTotal() {
		return configuartionDao.countTotal();
	}

	public List<ConfigurationCategory> findByEntries(int firstResult, int sizeNo) {
		return  configuartionDao.findByEntries(firstResult, sizeNo);
	}

	public ConfigurationCategory findById(int id) {
		// TODO Auto-generated method stub
		return configuartionDao.findById(id);
	}

}
