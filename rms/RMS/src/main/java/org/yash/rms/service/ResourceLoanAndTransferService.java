/**
 * 
 */
package org.yash.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.yash.rms.domain.ResourceLoanTransfer;
import org.yash.rms.form.ResourceLoanTransferForm;
 
 
 
 
 
 
 

 

 
/**
 * @author varun.haria
 *
 */
@Service("ResourceLoanAndTransferService")
public interface ResourceLoanAndTransferService extends RmsCRUDService<ResourceLoanTransfer> {
	
	public boolean save(ResourceLoanTransferForm form);

	public List<ResourceLoanTransfer> find(int resourceId);
}
