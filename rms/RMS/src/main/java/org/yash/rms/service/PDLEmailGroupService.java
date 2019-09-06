package org.yash.rms.service;

import java.util.ArrayList;
import java.util.List;

import org.yash.rms.domain.PDLEmailGroup;
import org.yash.rms.dto.PDLEmailGroupDTO;

public interface PDLEmailGroupService {
	
	public List<PDLEmailGroup> getPdlEmails() throws Exception;

	public List<String> findPdlByIds(ArrayList<Integer> pdlList);
	
	public List<PDLEmailGroupDTO> findAll();
	
	public boolean delete(int id);
	
	public boolean saveOrUpdate(PDLEmailGroupDTO pdlEmailGroupDto);
	
	public boolean activateOrDeactivatePDLEmailGroup(int id, String activateStatus);
	//For Get Only Active PDL Email for RRF PDL Mail
	public List<PDLEmailGroup> getActivePdlEmails() throws Exception;
	//Update pdlEmailId according to customerId
	public List<PDLEmailGroup> updatePdlEmail(String custEmail, String newcustEmail);

}
