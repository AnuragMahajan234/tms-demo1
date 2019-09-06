/*package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.PDLEmailGroupDao;
import org.yash.rms.domain.PDLEmailGroup;
import org.yash.rms.service.PDLEmailGroupService;

@Service("PDLEmailService")
public class PDLEmailGroupServiceImpl implements PDLEmailGroupService {

	@Autowired
	PDLEmailGroupDao pdlEmailGroupDao;
	
	public List<PDLEmailGroup> getPdlEmails() throws Exception{
		return pdlEmailGroupDao.getPdlEmails();
	}

	public List<String> findPdlByIds(ArrayList<Integer> pdlList) {
		
		return pdlEmailGroupDao.findPdlByIds(pdlList);
	}
}
*/
package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.PDLEmailGroupDao;
import org.yash.rms.domain.PDLEmailGroup;
import org.yash.rms.dto.PDLEmailGroupDTO;
import org.yash.rms.service.PDLEmailGroupService;
import org.yash.rms.util.DozerMapperUtility;

@Service("PDLEmailService")
public class PDLEmailGroupServiceImpl implements PDLEmailGroupService {

	@Autowired
	PDLEmailGroupDao pdlEmailGroupDao;
	
	@Autowired
	private DozerMapperUtility mapper;
	
	public List<PDLEmailGroup> getPdlEmails() throws Exception{
		return pdlEmailGroupDao.getPdlEmails();
	}
	//For Get Only Active PDL Email for RRF PDL Mail
	public List<PDLEmailGroup> getActivePdlEmails() throws Exception{
		return pdlEmailGroupDao.getActivePdlEmails();
	}
	//Update pdlEmailId according to customerId
	public List<PDLEmailGroup> updatePdlEmail(String custEmail,String NewcustEmail) {
		return pdlEmailGroupDao.updatePdlEmail(custEmail,NewcustEmail);
	}
	
	public List<String> findPdlByIds(ArrayList<Integer> pdlList) {
		
		return pdlEmailGroupDao.findPdlByIds(pdlList);
	}
	
public List<String> findPdl_NameByIds(ArrayList<Integer> pdlList) {
		
		return pdlEmailGroupDao.findPdl_nameByIds(pdlList);
	}

	public List<PDLEmailGroupDTO> findAll() {
		return mapper.convertPDLDomainListToDTOList(pdlEmailGroupDao.findAll());
	}
	
	public boolean delete(int id)
	{
		return pdlEmailGroupDao.delete(id);
	}

	public boolean saveOrUpdate(PDLEmailGroupDTO pdlEmailGroupDto) {
		return pdlEmailGroupDao.saveOrupdate(mapper.convertDTOObjectToDomain(pdlEmailGroupDto));
	}

	public boolean activateOrDeactivatePDLEmailGroup(int id, String activateStatus) {
		return pdlEmailGroupDao.activateOrDeactivatePDLEmailGroup(id, activateStatus);
	}

	
}
