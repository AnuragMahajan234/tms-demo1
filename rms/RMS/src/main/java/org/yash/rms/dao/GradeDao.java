/**
 * 
 */
package org.yash.rms.dao;

 
 
import org.yash.rms.domain.Grade;
 
 
public interface GradeDao extends RmsCRUDDAO<Grade> {

	public Grade findById(int id);

	public Grade findByName(String name);
}
