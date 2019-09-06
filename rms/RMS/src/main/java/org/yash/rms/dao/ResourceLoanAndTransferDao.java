/**
 * 
 */
package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.ResourceLoanTransfer;

/**
 * @author varun.haria
 * 
 */
public interface ResourceLoanAndTransferDao extends RmsCRUDDAO<ResourceLoanTransfer> {
	public List<ResourceLoanTransfer> find(int resourceId);
	
}
