package org.yash.rms.dao;

import java.math.BigInteger;

import org.yash.rms.domain.KeyGenerator;

import javassist.NotFoundException;

public interface KeyGeneratorDao extends RmsCRUDDAO<KeyGenerator>{

	KeyGenerator findById(int id);
	
	int save(KeyGenerator keyGenerator) throws NotFoundException;
	
	KeyGenerator findKeyByStatusType(final String type,final String status);
	
	Long findMaxAssginedValue();
	
	void generateToken(String type, String status, Long minValue,Long MaxValue);
}
