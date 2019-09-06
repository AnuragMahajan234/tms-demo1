package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.ldap.LdapContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.PDLAndResourceMappingDao;
import org.yash.rms.dao.PDLEmailGroupDao;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.domain.PDLAndResourceMapping;
import org.yash.rms.domain.PDLEmailGroup;
import org.yash.rms.domain.Resource;
import org.yash.rms.ldap.LDAPContext;
import org.yash.rms.ldap.dao.PdlDao;
import org.yash.rms.service.PdlSyncService;

@Service("pdlSyncService")
@Transactional
public class PdlSyncServiceImpl implements PdlSyncService {
	
	private static final Logger logger = LoggerFactory.getLogger(PdlSyncServiceImpl.class);
	
	@Autowired
	@Qualifier("pdlDao")
	private PdlDao pdlDao;
	
	@Autowired
	@Qualifier("pdlEmailDao")
	private PDLEmailGroupDao pDLEmailGroupDao;
	
	@Autowired
	private PDLAndResourceMappingDao pDLAndResourceMappingDao;
	
	@Autowired
	private LDAPContext customLdapContext;
	
	@Autowired
	@Qualifier("ResourceDao")
	private ResourceDao resourceDao;
	
	public boolean syncPdl(Integer userId, String username, String password) throws AuthenticationException{
		boolean syncStatus = false; 
		try{
			logger.info("--------PdlSyncServiceImpl syncPdl method start-------");
			 List<PDLEmailGroup> pdlGroups = getPDLEmailGroups(); 
			 List<PDLAndResourceMapping> pdlGroupMemberList =  getPDLGroupMembers(userId, username, password, pdlGroups);
			 if(pdlGroupMemberList != null && pdlGroupMemberList.size()>0){
				 addMemberInPDL(pdlGroupMemberList);
			 }
			 syncStatus = true;
			 logger.info("--------PdlSyncServiceImpl syncPdl method ended-------");
		}catch(AuthenticationException ae){
			throw new AuthenticationException("Invalid user credential");
		}catch (Exception e) {
			logger.info("--------Exception in PdlSyncServiceImpl syncPdl method-------");
			e.printStackTrace();
		}
		return syncStatus;
	}
	
	private void deleteAllMemberFromPDL(){
		 pDLAndResourceMappingDao.deleteAll();
	}
	
	private List<PDLEmailGroup> getPDLEmailGroups() throws Exception{
		 return pDLEmailGroupDao.getPdlEmails();
	}
	
	private List<PDLAndResourceMapping> getPDLGroupMembers(Integer userId, String username, 
		String password, List<PDLEmailGroup> pdlGroups) throws AuthenticationException {
		LdapContext ldapContext = customLdapContext.getConnection(username,password);
		 ArrayList<PDLAndResourceMapping> pdlGroupMemberList = null;
		if(ldapContext !=null){
			 deleteAllMemberFromPDL();
			 pdlGroupMemberList = new ArrayList<PDLAndResourceMapping>();
			 for(PDLEmailGroup pDLEmailGroup : pdlGroups){
				List<String> emailAdressList =  pdlDao.getGroupMembers(
						customLdapContext.getBaseDomain(),pDLEmailGroup.getPdlName(), ldapContext);
				if(emailAdressList !=null && emailAdressList.size()>0){
					List<Resource> resourceList =  getResourceList(emailAdressList);
					for(Resource resource : resourceList){
						PDLAndResourceMapping pdlGroupMember = new PDLAndResourceMapping();
						pdlGroupMember.setResourceID(resource);
						pdlGroupMember.setCreatedBy(userId);
						pdlGroupMember.setCreatedDate(new Date());
						pdlGroupMember.setPdl_id(pDLEmailGroup.getId());
						pdlGroupMemberList.add(pdlGroupMember);
					}
				}
			 }
		 }
		 customLdapContext.closeConnection(ldapContext);
		return pdlGroupMemberList;
	}
	
	private void addMemberInPDL(List<PDLAndResourceMapping> pdlGroupMemberList){
		pDLAndResourceMappingDao.addResourcesInPDL(pdlGroupMemberList);
	}
	
	private List<Resource> getResourceList(List<String> emailAdressList){
		List<Resource> resourceList = resourceDao.findResourcesByEmailIds(emailAdressList);
		return resourceList;
	}
}
