package org.yash.rms.service;
import java.util.List;

import org.yash.rms.domain.CustomerGroup;
import org.yash.rms.dto.CustomerGroupDTO;


public interface CustomerGroupService extends RmsCRUDService<CustomerGroup> {

public List<CustomerGroup> findCustomerGroupByCustomerId(int customerId);
public List<CustomerGroup> findAllActiveCustomerGroup(int customerId);
public CustomerGroup findByGroupId(int groupId);
public List<CustomerGroup> findById(int customerId);
public List<CustomerGroup> findCustGroupByCustomerId(int customerId);
public boolean validateCustGroupName(int custId, String custGroupName);
public List<CustomerGroupDTO> findCustomerGroupByCustomerIds(List<Integer> customerIds);

//Below method is rewrite because we need to add/remove some additional parameter in method
//public boolean activateOrDeactivateCustomerGroup(int id, String activateStatus, String custGroupEmail);
public boolean activateOrDeactivateCustomerGroup(int id, String groupStatus, int custId, String custGroupName);

//SaveOrUpdate CustomerGroup
 public boolean saveUpdateCustomer(String poGroupId, String poGroupName,String mCusotmerId);
}
