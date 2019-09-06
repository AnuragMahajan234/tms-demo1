/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ResourceAllocationDao;
import org.yash.rms.dao.ResourceLoanAndTransferDao;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceLoanTransfer;
import org.yash.rms.form.ResourceLoanTransferForm;
import org.yash.rms.service.ResourceLoanAndTransferService;
import org.yash.rms.util.DozerMapperUtility;
/**
 * @author varun.haria
 * 
 */
@Service("ResourceLoanAndTransferService")
public class ResourceLoanAndTransferServiceImpl implements ResourceLoanAndTransferService {

	/*@Autowired
	@Qualifier("ResourceDao")
	Resource resourceDao;*/
	
	@Autowired
	@Qualifier("ResourceLoanAndTransferDao")
	ResourceLoanAndTransferDao resourceLoanAndTransferDao;
	
	@Autowired
	ResourceAllocationDao resourceAllocationDao;
	
	
	@Autowired
	private DozerMapperUtility mapperUtility;
	
	
	public boolean save(ResourceLoanTransferForm resourceLoanTransferForm) {
		// TODO Auto-generated method stub
		Resource resource=new Resource();
		//populateResource(resource,resourceLoanTransferForm);
		return resourceLoanAndTransferDao.saveOrupdate(mapperUtility.convertResourceLoanTransferToDomain(resourceLoanTransferForm));
	}


	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean saveOrupdate(Resource t) {
		// TODO Auto-generated method stub
		return false;
	}



	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}


	public boolean saveOrupdate(ResourceLoanTransfer t) {
		// TODO Auto-generated method stub
		return false;
	}


	public List<ResourceLoanTransfer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


	public List<ResourceLoanTransfer> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<ResourceLoanTransfer> find(int resourceId) {
		// TODO Auto-generated method stub
		return resourceLoanAndTransferDao.find(resourceId);
	}

	

}
