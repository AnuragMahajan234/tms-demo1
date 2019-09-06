package org.yash.rms.service;

import org.springframework.transaction.annotation.Transactional;

import org.yash.rms.dto.CustomerDTO;

/**
 * @author sumit.paul
 * 
 */

@Transactional
public interface CustomerService extends RmsCRUDService<CustomerDTO> {

	/**
	 * This method is used to fetch customer based on customer id.
	 * 
	 * @param id
	 *            <code>Integer</code> value corresponds to customer id.
	 * 
	 * @return <code>ResponseEntity</code> having customer information and
	 *         status.
	 */
	public CustomerDTO findCustomer(int id);

	//Customer Count here
	public Long getcustomerCount();

}
