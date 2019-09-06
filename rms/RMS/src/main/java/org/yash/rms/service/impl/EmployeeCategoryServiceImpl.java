package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.EmployeeCategoryDao;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.EmployeeCategory;
import org.yash.rms.domain.Ownership;
import org.yash.rms.service.EmployeeCategoryService;
import org.yash.rms.service.OwnershipService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;

@Service("EmployeeCategoryService")
public class EmployeeCategoryServiceImpl implements EmployeeCategoryService {

	@Autowired 
	EmployeeCategoryDao employeeCategoryDao;
	
	@Autowired
	private DozerMapperUtility mapper;

	public boolean delete(int id) {
		return employeeCategoryDao.delete(id);
	}

	public boolean saveOrupdate(EmployeeCategory employeeCategory) {
		return employeeCategoryDao.saveOrupdate(mapper.convertDTOObjectToDomain(employeeCategory));
	}

	public List<EmployeeCategory> findAll() {
		return mapper.convertEmployeeCategoryDomainListToDTOList(employeeCategoryDao.findAll());
	}

	public List<EmployeeCategory> findByEntries(int firstResult, int sizeNo) {
		return mapper.convertEmployeeCategoryDomainListToDTOList(employeeCategoryDao.findByEntries(firstResult, sizeNo));
	}

	public long countTotal() {
		return employeeCategoryDao.countTotal();
	}

	public EmployeeCategory findById(int id) {
		// TODO Auto-generated method stub
		return employeeCategoryDao.findById(id);
	}

	public EmployeeCategory getEmployeeCategoryByName(String employeeType) {
		return employeeCategoryDao.getEmployeeCategoryByName(employeeType);
	}
	


}
