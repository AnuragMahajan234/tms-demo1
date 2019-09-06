package org.yash.rms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.OrgHierarchyDao;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.service.OrgHierarchyService;

 
@Service("OrgHierarchyService")
public class OrgHierarchyServiceImpl implements OrgHierarchyService {
	
	@Autowired
	OrgHierarchyDao orgHierarchyDao;

	public boolean delete(int id) {
		return orgHierarchyDao.delete(id);
	}

	public boolean saveOrupdate(OrgHierarchy t) {
		return orgHierarchyDao.saveOrupdate(t);
	}

	public List<OrgHierarchy> findAll() {
		return orgHierarchyDao.findAll();
	}

	public List<OrgHierarchy> findByEntries(int firstResult, int sizeNo) {
		return null;
	}

	public long countTotal() {
		return 0;
	}

	public OrgHierarchy fingOrgHierarchiesById(Integer id) {
		return orgHierarchyDao.findOrgHierarchiesById(id);
	}

	public boolean move(int id, int newParentId) {
		return orgHierarchyDao.move(id, newParentId);
	}

	public Map<Integer, String> showHierarhicalPath(int id) {
		return orgHierarchyDao.showHierarhicalPath(id);
	}

	public List<OrgHierarchy> findAllBu() {
		return orgHierarchyDao.findAllBus();
	}

	public List<OrgHierarchy> findAllBGs() {
		return orgHierarchyDao.findAllBGs();
	}

	public boolean validateBG(Integer parentId, String BGName) {
		// TODO Auto-generated method stub
		return orgHierarchyDao.validateBG(parentId, BGName);
	}

	public boolean activateOrDeactivateOrgHierarchyChild(int id,
			boolean activateStatus) {
		// TODO Auto-generated method stub
		return orgHierarchyDao.activateOrDeactivateOrgHierarchyChild(id, activateStatus);
	}

	public Long countResource(int id) {
		// TODO Auto-generated method stub
		return orgHierarchyDao.countResource(id);
	}

	public OrgHierarchy findOrgHierarchyList() {
		// TODO Auto-generated method stub
		return orgHierarchyDao.findOrgHierarchyList();
	}
// added for US:3119
	public List<OrgHierarchy> findAllBusForBGADMIN(){
		return orgHierarchyDao.findAllBusForBGADMIN();
	}

	public OrgHierarchy getOrgHierarchyByName(String name) {
		return orgHierarchyDao.getOrgHierarchyByName(name);
	}

	public OrgHierarchy getOrgHierarchyByBgIdBuName(Integer bg, String bu) {
		return orgHierarchyDao.getOrgHierarchyByBgIdBuName(bg, bu);
	}

	public Integer findBUIdByBGBUName(String BGNAme, String BUName) {
		return orgHierarchyDao.findBUIdByBGBUName(BGNAme, BUName);
	}
}
