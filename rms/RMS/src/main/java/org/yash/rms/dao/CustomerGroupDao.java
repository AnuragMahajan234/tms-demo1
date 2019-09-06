package org.yash.rms.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.domain.CustomerGroup;

@Transactional
public interface CustomerGroupDao {

	public boolean saveOrupdate(CustomerGroup customerGroup);
	public boolean create(CustomerGroup invoice);
	public boolean delete(int groupId);
	public List<CustomerGroup> findAll();
	public List<CustomerGroup> findCustomerGroupByCustomerId(int customerId);

	
	public List<CustomerGroup> findByEntries(int firstResult, int sizeNo) ;
	public long countTotal();
	
	public List<CustomerGroup> findAllActiveCustomerGroup(int customerId);
	public CustomerGroup findByGroupId(int groupId);
	public List<CustomerGroup> findById(int customerId);
	
	public List<CustomerGroup> findCustomerGroupByCustomerIds(List<Integer> customerIds);
	
	//Below method is rewrite because we need to add/remove some additional parameter in method
	//public boolean activateOrDeactivateCustomerGroup(int id, String activateStatus, String custGroupEmail);
	public boolean activateOrDeactivateCustomerGroup(int id, String groupStatus, int custId, String custGroupName);
	
	 public boolean validateCustGroupName(int custId, String custGroupName);
     public List<CustomerGroup> findCustGroupByCustomerId(int customerId);
   
    //SaveOrUpdate CustomerGroup
	public boolean saveUpdateCustomer(String poGroupId, String poGroupName,String mCusotmerId);
		
	}
