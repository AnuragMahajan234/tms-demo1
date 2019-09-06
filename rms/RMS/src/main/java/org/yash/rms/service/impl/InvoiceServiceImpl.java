/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.InvoiceBy;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;

/**
 * @author arpan.badjatiya
 *
 */
@Service("InvoiceService")
public class InvoiceServiceImpl implements RmsCRUDService<InvoiceBy> {

	@Autowired @Qualifier("InvoiceByDao")
	RmsCRUDDAO<InvoiceBy> invoiceByDao;
	
	@Autowired
	private DozerMapperUtility mapper;
	
	public boolean delete(int id) {
		return invoiceByDao.delete(id);
	}

	public boolean saveOrupdate(InvoiceBy invoice) {
		return invoiceByDao.saveOrupdate(mapper.convertDTOObjectToDomain(invoice));
	}

	public List<InvoiceBy> findAll() {
		return mapper.convertInvoiceDomainListToDTOList(invoiceByDao.findAll());
	}

	public List<InvoiceBy> findByEntries(int firstResult, int sizeNo) {
		return mapper.convertInvoiceDomainListToDTOList(invoiceByDao.findByEntries(firstResult, sizeNo));
	}

	public long countTotal() {
		return invoiceByDao.countTotal();
	}

}
