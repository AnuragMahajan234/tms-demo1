package org.yash.rms.dao;

import java.util.List;
import java.util.Map;

import org.yash.rms.domain.Activity;
import org.yash.rms.domain.OrgHierarchy;

public interface OrgHierarchyDao extends RmsCRUDDAO<OrgHierarchy> {
	public OrgHierarchy findOrgHierarchiesById(Integer id);
	
	public boolean activateOrDeactivateOrgHierarchyChild(int id, boolean activateStatus);
	public boolean move(int id, int newParentId);
	public  Map<Integer, String> showHierarhicalPath(int id);
	public List<OrgHierarchy> findAllBus();
	public List<OrgHierarchy> findAllBGs();
	public boolean validateBG(Integer parentId, String BGName);
	public Long countResource(int id);
	public OrgHierarchy findOrgHierarchyList();
	//added for US:3119
	public List<OrgHierarchy> findAllBusForBGADMIN();

	public OrgHierarchy getOrgHierarchyByName(String name);

	public OrgHierarchy getOrgHierarchyByBgIdBuName(Integer bg, String bu);

	Integer findBUIdByBGBUName(final String BGNAme, final String BUName);
}
