package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.Module;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;


@Service("ModuleService")
public class ModuleServiceImpl implements RmsCRUDService<Module> {

	
	@Autowired @Qualifier("ModuleDao")
	
	RmsCRUDDAO<Module> moduleDao;
	//ModuleDao moduleDao;


	

	@Autowired
	private DozerMapperUtility mapper;


	public boolean saveOrupdate(Module t) {
		// TODO Auto-generated method stub
		return  moduleDao.saveOrupdate(mapper.convertDTOObjectToDomain(t));

	}

	public List<Module> findAll() {
		// TODO Auto-generated method stub
		return mapper.convertModuleDomainListToDTOList(moduleDao.findAll());
	}

	public List<Module> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return moduleDao.delete(id);
	}

}
