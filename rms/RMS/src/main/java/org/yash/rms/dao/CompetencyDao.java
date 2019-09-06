/**
 * 
 */
package org.yash.rms.dao;

import org.yash.rms.domain.Competency;



 
public interface CompetencyDao extends RmsCRUDDAO<Competency> {

	public Competency findById(int id);
}
