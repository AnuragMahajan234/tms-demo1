/**
 * 
 */
package org.yash.rms.dao;

 
import org.yash.rms.domain.Designation;
 
 
public interface DesignationDao extends RmsCRUDDAO<Designation> {

	public Designation findById(int id);

	public Designation findByNameIgnoreSpace(String name);
}
