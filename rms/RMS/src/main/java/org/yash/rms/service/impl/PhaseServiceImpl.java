package org.yash.rms.service.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.PhaseDao;
import org.yash.rms.domain.Phase;
import org.yash.rms.service.PhaseService;

@Service("PhaseService")
public class PhaseServiceImpl implements PhaseService {
	
	
	@Autowired 
	@Qualifier("PhaseDao")
	private PhaseDao phaseDao;
	
	
	private static final Logger logger = LoggerFactory.getLogger(PhaseServiceImpl.class);	
	
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean saveOrupdate(Phase t) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Phase> findAll() {
		logger.info("-----PhaseServiceImpl start findall-------");
		logger.info("-----PhaseServiceImpl end findall-------");
		return phaseDao.findAll();
	}

	public List<Phase> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Phase getPhase(int id) {
		logger.info("-----PhaseServiceImpl start getPhase-------");
		logger.info("-----PhaseServiceImpl end getPhase-------");
		return phaseDao.getPhase(id);
	}

	public Serializable save(Phase phase) {
		logger.info("-----PhaseServiceImpl start save-------");
		return phaseDao.save(phase);
	}

	public void delete(Phase phase) {
		logger.info("-----PhaseServiceImpl start delete-------");
		phaseDao.delete(phase);
		logger.info("-----PhaseServiceImpl end delete-------");
		
	}

	public void update(Phase phase) {
		logger.info("-----PhaseServiceImpl start update-------");
		phaseDao.update(phase);
		
	}

	public boolean isAlreadyExist(String phaseName) {
		// TODO Auto-generated method stub
		return phaseDao.isAlreadyExist(phaseName);
	}

}
