package org.yash.rms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.KeyGeneratorDao;
import org.yash.rms.domain.KeyGenerator;
import org.yash.rms.service.KeyGeneratorService;
import org.yash.rms.util.Constants;

import javassist.NotFoundException;

@Service("keyGeneratorService")
@Component
public class KeyGeneratorServiceImpl implements KeyGeneratorService {
	
	private static final Logger logger = LoggerFactory.getLogger(KeyGeneratorServiceImpl.class);

	@Autowired
	@Qualifier("keyGeneratorDao")
	private KeyGeneratorDao keyGeneratorDao;

	public KeyGenerator findById(int id) {
		return keyGeneratorDao.findById(id);
	}

	public Integer save(KeyGenerator keyGenerator) throws NotFoundException {
		return keyGeneratorDao.save(keyGenerator);
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public KeyGenerator findKeyByStatusType(String type, String status) {
		return keyGeneratorDao.findKeyByStatusType(type, status);
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public boolean saveOrUpdate(KeyGenerator keyGenerator) {
		return keyGeneratorDao.saveOrupdate(keyGenerator);
	}

	public Long findMaxAssginedValue() {
		return keyGeneratorDao.findMaxAssginedValue();
	}
	
	
	@Transactional(isolation=Isolation.SERIALIZABLE, propagation=Propagation.REQUIRES_NEW)
	public Integer generateToken(String type, String status, Long value) throws Exception {
		if(value == null || value < 1)
			throw new IllegalArgumentException("Value can't be "+value);
		if(status == null)
			status = Constants.UNASSIGNED;
		KeyGenerator keyGenerator = new KeyGenerator();
		keyGenerator.setStatus(status);
		keyGenerator.setType(type);
		keyGenerator.setValue(value);
		try {
			return keyGeneratorDao.save(keyGenerator);
		} catch (Exception e) {
			logger.error("-------------Exception occured in GenerateToken --------------");
			e.getMessage();
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional(isolation=Isolation.SERIALIZABLE, propagation=Propagation.REQUIRES_NEW)
	public void generateToken(String type, String status, Long minValue,Long maxValue){
		 keyGeneratorDao.generateToken(type, status, minValue, maxValue);
	}
	
}
