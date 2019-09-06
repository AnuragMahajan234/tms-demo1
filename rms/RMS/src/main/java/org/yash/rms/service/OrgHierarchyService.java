package org.yash.rms.service;

import java.util.List;
import java.util.Map;

import org.yash.rms.domain.OrgHierarchy;

public interface OrgHierarchyService extends RmsCRUDService<OrgHierarchy> {

	public OrgHierarchy fingOrgHierarchiesById(Integer id);

	public boolean move(int id, int newParentId);

	public Map<Integer, String> showHierarhicalPath(int id);

	public List<OrgHierarchy> findAllBu();

	public List<OrgHierarchy> findAllBGs();
	
	public boolean validateBG(Integer parentId, String BGName);
	public boolean activateOrDeactivateOrgHierarchyChild(int id, boolean activateStatus);
	public Long countResource(int id);
	public OrgHierarchy findOrgHierarchyList();
	//added for US:3119
	public List<OrgHierarchy> findAllBusForBGADMIN();
	
	public OrgHierarchy getOrgHierarchyByName(String name);
	public OrgHierarchy getOrgHierarchyByBgIdBuName(Integer bg, String bu);

	Integer findBUIdByBGBUName(final String BGNAme, final String BUName);

}
