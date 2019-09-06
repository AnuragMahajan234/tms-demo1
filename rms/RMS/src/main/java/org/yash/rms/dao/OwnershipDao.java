/**
 * 
 */
package org.yash.rms.dao;

import org.yash.rms.domain.Activity;
import org.yash.rms.domain.Ownership;
 
public interface OwnershipDao extends RmsCRUDDAO<Ownership> {

	public Ownership findById(int id);
}
