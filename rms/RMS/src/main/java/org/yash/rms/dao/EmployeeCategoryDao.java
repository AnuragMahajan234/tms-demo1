/**
 * 
 */
package org.yash.rms.dao;

import org.yash.rms.domain.EmployeeCategory;


 
public interface EmployeeCategoryDao extends RmsCRUDDAO<EmployeeCategory> {

	public EmployeeCategory findById(int id);

	public EmployeeCategory getEmployeeCategoryByName(String employeeType);
}
