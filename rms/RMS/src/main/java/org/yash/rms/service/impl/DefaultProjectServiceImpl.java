package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.DefaultProjectDao;
import org.yash.rms.domain.DefaultProject;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.service.DefaultProjectService;
import org.yash.rms.util.DozerMapperUtility;

/**
 * 
 * @author purva.bhate
 *
 */
@Service("DefaultProjectService")
@Transactional
public class DefaultProjectServiceImpl implements DefaultProjectService {
	@Autowired @Qualifier("defaultProjectDao")
	DefaultProjectDao defaultProjectDao;  
	
	
	@Autowired
	private DozerMapperUtility mapperUtility;
	@Autowired
	@Qualifier("defaultProjectDao")
	DefaultProjectDao projectDao;
	
	@Transactional
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
 
	@Transactional
	public boolean saveOrupdate(org.yash.rms.form.DefaultProjectForm form) {
		// TODO Auto-generated method stub
		return projectDao.saveOrupdate(mapperUtility.convertDefaultProjectFormTODefaultProjectDomain(form));
	}

	//find all default project list added by purva For US3119 
	@Transactional
	public List<DefaultProject> findAll() {
		// TODO Auto-generated method stub
		List<DefaultProject> defaultProjectList = new ArrayList<DefaultProject>();
		defaultProjectList=projectDao.findAll();
		return defaultProjectList;
	}
	
	@Transactional
	public List<DefaultProject> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Transactional
	public boolean saveOrupdate(DefaultProject t) {
		// TODO Auto-generated method stub
		return false;
	}

	public DefaultProject  getDefaultProjectbyProjectId(Project projectId){
		return defaultProjectDao.getDefaultProjectbyProjectId(projectId);	
	}
	
	public DefaultProject  getDefaultProjectbyProjectForBU(OrgHierarchy currentBuId){
		return defaultProjectDao.getDefaultProjectbyProjectForBU(currentBuId);	
	}

	 
	

}
