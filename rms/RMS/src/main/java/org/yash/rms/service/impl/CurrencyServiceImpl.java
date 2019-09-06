/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.Currency;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;

/**
 * @author arpan.badjatiya
 *
 */
@Service("CurrencyService")
public class CurrencyServiceImpl implements RmsCRUDService<Currency> {

	@Autowired @Qualifier("CurrencyDao")
	RmsCRUDDAO<Currency> currencyDao;
	
	@Autowired
	private DozerMapperUtility mapper;
	
	public boolean delete(int id) {
		return currencyDao.delete(id);
	}

	public boolean create(Currency currency) {
		return currencyDao.saveOrupdate(mapper.convertDTOObjectToDomain(currency));
	}

	public boolean saveOrupdate(Currency currency) {
		return currencyDao.saveOrupdate(mapper.convertDTOObjectToDomain(currency));
	}

	public List<Currency> findAll() {
		return mapper.convertCurrencyDomainListToDTOList(currencyDao.findAll());
	}

	public long countTotal() {
		return currencyDao.countTotal();
	}

	public List<Currency> findByEntries(int firstResult, int sizeNo) {
		return currencyDao.findByEntries(firstResult, sizeNo);
	}

}
