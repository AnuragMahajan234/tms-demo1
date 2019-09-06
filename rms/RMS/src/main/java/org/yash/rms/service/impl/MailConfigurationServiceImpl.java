/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.DesignationDao;
import org.yash.rms.dao.MailConfigurationDao;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.dao.RoleDao;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.MailConfiguration;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Roles;
import org.yash.rms.service.DesignationService;
import org.yash.rms.service.MailConfigurationService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.service.RoleService;
import org.yash.rms.util.DozerMapperUtility;

/**
 * @author arpan.badjatiya
 *
 */
@Service("MailConfigurationService")
public class MailConfigurationServiceImpl implements MailConfigurationService {

	@Autowired  
	MailConfigurationDao configDao;
	
	@Autowired
	private DozerMapperUtility mapper;

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return configDao.delete(id);
	}

	public boolean saveOrupdate(MailConfiguration t) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<MailConfiguration> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MailConfiguration> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<String> findById(int id) {
		// TODO Auto-generated method stub
		return configDao.findById(id);
	}

	public boolean saveConfigurations(List<MailConfiguration> list) {
		// TODO Auto-generated method stub
		return configDao.saveConfigurations(list);
	}

	public List<MailConfiguration> findByProjectId(int id,int conf) {
		// TODO Auto-generated method stub
		return configDao.findByProjectId(id,conf);
	}

	public MailConfiguration getMailConfg(int projectId, int confg, int roleId) {
		// TODO Auto-generated method stub
		return configDao.getMailConfg(projectId, confg, roleId);
	}
	
/*	public boolean delete(int id) {
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
	}*/
	public boolean delete(int projectId, int confgId)
	{
		return configDao.delete(projectId,confgId);
	}

	public void saveDefaultConfigs(Project project) {
		configDao.saveDefaultConfigs(project);
		
	}
}
