package org.yash.rms.service;

import java.math.BigInteger;

import org.yash.rms.domain.KeyGenerator;

import javassist.NotFoundException;

public interface KeyGeneratorService {

	KeyGenerator findById(int id);

	Integer save(KeyGenerator keyGenerator) throws NotFoundException;

	KeyGenerator findKeyByStatusType(String type, String status);
	
	boolean saveOrUpdate(KeyGenerator keyGenerator);
	
	Long findMaxAssginedValue();
	
	Integer generateToken(final String type,final String status,final Long value) throws Exception;
	
	void generateToken(String type, String status, Long minValue,Long MaxValue);
}
