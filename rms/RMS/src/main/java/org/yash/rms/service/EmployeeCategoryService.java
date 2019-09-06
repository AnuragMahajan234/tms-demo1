package org.yash.rms.service;

import org.yash.rms.domain.EmployeeCategory;

 

public interface EmployeeCategoryService extends RmsCRUDService<EmployeeCategory>{

	
	 
public EmployeeCategory findById(int id);

public EmployeeCategory getEmployeeCategoryByName(String employeeType);
	 
}
