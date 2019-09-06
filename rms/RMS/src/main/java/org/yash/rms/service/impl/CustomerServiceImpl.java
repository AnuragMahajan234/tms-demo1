/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.CustomerDAO;
import org.yash.rms.domain.Customer;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.CustomerDTO;
import org.yash.rms.service.CustomerService;
import org.yash.rms.util.DozerMapperUtility;

/**
 * @author sumit.paul
 *
 */
@Service("CustomerService")
public class CustomerServiceImpl implements CustomerService {

	@Autowired @Qualifier("CustomerDao")
	CustomerDAO customerDao;
	
	@Autowired
	private DozerMapperUtility mapper;
	
	
	private static final Logger logger =  LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	public boolean delete(int id) {
	      return true;
	}

	public CustomerDTO findCustomer(int id) {
		logger.info("------CustomerServiceImpl  findCustomer method start------");
		return mapper.convertCustomerDomainToDTO(customerDao.findCustomer(id));
	}
	
	
	public boolean saveOrupdate(CustomerDTO customer) {
		logger.info("-----CustomerServiceImpl  saveOrUpate method start-----");
		boolean saveOrupdate = false;
		org.yash.rms.domain.Customer customerDomain = mapper.convertCustomerDTOObjectToDomain(customer);
		if(null != customerDomain){
			saveOrupdate =  customerDao.saveOrupdate(customerDomain);
		}		
		
		logger.info("-----CustomerServiceImpl  saveOrUpate method end-----");
		return saveOrupdate;
	}

	public List<CustomerDTO> findAll() {
		logger.info("-----CustomerServiceImpl  findAll method start-----");
		List<Customer> domainList = customerDao.findAll();
		return  mapper.convertCustomerDomainListToDTOList(domainList);
	}

	public long countTotal() {
		return 0;
	}

	public List<CustomerDTO> findByEntries(int firstResult, int sizeNo) {		
		return null;
	}
	//Customer Count here
	public Long getcustomerCount() {
		return customerDao.getcustomerCount();
	}
}
