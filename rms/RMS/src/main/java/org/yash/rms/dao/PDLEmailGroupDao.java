/*package org.yash.rms.dao;

import java.util.ArrayList;
import java.util.List;

import org.yash.rms.domain.PDLEmailGroup;

public interface PDLEmailGroupDao {
	
	public List<PDLEmailGroup> getPdlEmails() throws Exception;

	public List<String> findPdlByIds(ArrayList<Integer> pdlList);

}
*/
package org.yash.rms.dao;

import java.util.ArrayList;
import java.util.List;

import org.yash.rms.domain.PDLEmailGroup;

public interface PDLEmailGroupDao extends RmsCRUDDAO<PDLEmailGroup>{
	
	public List<PDLEmailGroup> getPdlEmails() throws Exception;

	public List<String> findPdlByIds(ArrayList<Integer> pdlList);
	
	public List<String> findPdl_nameByIds(ArrayList<Integer> pdlList);
	
	public boolean activateOrDeactivatePDLEmailGroup(int id, String activateStatus);
	//For Get Only Active PDL Email for RRF PDL Mail
	public List<PDLEmailGroup> getActivePdlEmails() throws Exception;
	//Update pdlEmailId according to customerId
	public List<PDLEmailGroup> updatePdlEmail(String custEmail,
			String newcustEmail);

}
