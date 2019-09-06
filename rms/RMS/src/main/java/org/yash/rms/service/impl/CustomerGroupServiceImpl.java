package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.CustomerGroupDao;


import org.yash.rms.domain.CustomerGroup;
import org.yash.rms.dto.CustomerGroupDTO;
import org.yash.rms.service.CustomerGroupService;

import org.yash.rms.util.DozerMapperUtility;

@Service("CustomerGroupService")
@Transactional
public class CustomerGroupServiceImpl implements CustomerGroupService{

	@Autowired @Qualifier("CustomerGroupDao")
	private CustomerGroupDao customerGroupDao;
	
	@Autowired
	private DozerMapperUtility mapper;
	

	public List<CustomerGroup> findCustomerGroupByCustomerId(int customerId) {
	
		return customerGroupDao.findCustomerGroupByCustomerId(customerId);
	}

	public List<CustomerGroup> findById(int customerId) {
		List<CustomerGroup> customerGroups =  customerGroupDao.findById(customerId);
		
		return customerGroups;
		
	}
	
	public CustomerGroup findByGroupId(int groupId) {
		return customerGroupDao.findByGroupId(groupId);
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		 return customerGroupDao.delete(id);
	}


	public boolean saveOrupdate(CustomerGroup customerGroup) {
		// TODO Auto-generated method stub
		 return customerGroupDao.saveOrupdate(customerGroup);
	}


	public List<CustomerGroup> findAll() {
		// TODO Auto-generated method stub
		 return customerGroupDao.findAll();
	}


	public List<CustomerGroup> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}


	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}


	public List<CustomerGroup> findARllActiveCustomerGroup(int customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CustomerGroupDTO> findCustomerGroupByCustomerIds(List<Integer> customerIds) {
		List<CustomerGroup> customerGroupList = customerGroupDao.findCustomerGroupByCustomerIds(customerIds);
		List<CustomerGroupDTO> customerGroupDTOList = new ArrayList<CustomerGroupDTO>();
		CustomerGroupDTO customerGroupDTO = null;
		for(CustomerGroup customerGroup : customerGroupList){
			customerGroupDTO = mapper.convertCustomerGroupToCustomerGroupDTO(customerGroup);
			customerGroupDTOList.add(customerGroupDTO);
		}
		return customerGroupDTOList;
	}
	
	//Below method is rewrite because we need to add/remove some additional parameter in method
	/*   public boolean activateOrDeactivateCustomerGroup(int id, String activateStatus,String CustGroupEmail)
    	{
		 return customerGroupDao.activateOrDeactivateCustomerGroup(id,activateStatus,CustGroupEmail);
	   }  */
	
	public boolean activateOrDeactivateCustomerGroup(int id, String groupStatus, int custId, String custGroupName)
	{
		return customerGroupDao.activateOrDeactivateCustomerGroup(id, groupStatus,custId,custGroupName);
	}

	public boolean validateCustGroupName(int custId, String custGroupName) {
		return customerGroupDao.validateCustGroupName(custId, custGroupName);
	}

    public List<CustomerGroup> findCustGroupByCustomerId(int customerId) {
		return customerGroupDao.findCustGroupByCustomerId(customerId);
	}

	public List<CustomerGroup> findAllActiveCustomerGroup(int customerId) {
		// TODO Auto-generated method stub
		return null;
	}
	//SaveOrUpdate CustomerGroup
	 public boolean saveUpdateCustomer(String poGroupId, String poGroupName,
			String mCusotmerId) {
		 return customerGroupDao.saveUpdateCustomer(poGroupId, poGroupName,mCusotmerId);
	}

	
}
