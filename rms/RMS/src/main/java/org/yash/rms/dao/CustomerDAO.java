package org.yash.rms.dao;


import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.domain.Customer;

/**
 * @author sumit.paul
 *
 */

@Transactional
public interface CustomerDAO extends RmsCRUDDAO<Customer> {

	public Customer findCustomer(int id);

	//Customer Count here
	public Long getcustomerCount();
	
}
