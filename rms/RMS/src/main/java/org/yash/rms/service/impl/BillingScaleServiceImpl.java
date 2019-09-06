/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.dto.BillingScaleDTO;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;

/**
 * @author arpan.badjatiya
 *
 */
@Service("BillingScaleService")
public class BillingScaleServiceImpl implements RmsCRUDService<BillingScaleDTO> {

	@Autowired @Qualifier("BillingScaleDao")
	RmsCRUDDAO<org.yash.rms.domain.BillingScale> billingScaleDao;
	
	@Autowired
	private DozerMapperUtility mapper;
	
	public void save() {

	}

	public void add() {

	}

	public List<BillingScaleDTO> findAll() {
		return mapper.convertDomainListToDTOList(billingScaleDao.findAll());
	}

	public List<BillingScaleDTO> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return mapper.convertDomainListToDTOList(billingScaleDao.findByEntries(firstResult, sizeNo));
	}

	public long countTotal() {
		return billingScaleDao.countTotal();
	}

	public boolean saveOrupdate(BillingScaleDTO billingScale) {
		return billingScaleDao.saveOrupdate(mapper.convertDTOObjectToDomain(billingScale));
	}

	public boolean delete(int id) {
		return billingScaleDao.delete(id);
	}

	public boolean create(BillingScaleDTO billingScale) {
		return billingScaleDao.saveOrupdate(mapper.convertDTOObjectToDomain(billingScale));
	}

}
