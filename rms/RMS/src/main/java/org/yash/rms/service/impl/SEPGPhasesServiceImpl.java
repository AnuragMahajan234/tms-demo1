package org.yash.rms.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.SEPGPhasesDao;
import org.yash.rms.dao.impl.EventDaoImpl;
import org.yash.rms.domain.Phase;
import org.yash.rms.domain.SEPGPhases;
import org.yash.rms.service.SEPGPhasesService;

@Service("SEPGPhasesService")
public class SEPGPhasesServiceImpl implements SEPGPhasesService{

	@Autowired 
	@Qualifier("SEPGPhaseDao")
	private SEPGPhasesDao sepgPhasesDao;
	
	private static final Logger logger = LoggerFactory.getLogger(SEPGPhasesServiceImpl.class);
	
	
	public List<SEPGPhases> findAll() {
		
		return sepgPhasesDao.findAll();
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean saveOrupdate(SEPGPhases t) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<SEPGPhases> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void save(List<SEPGPhases> sepgPhasesList) {
		// TODO Auto-generated method stub
		sepgPhasesDao.save(sepgPhasesList);
	}

	public void delete(SEPGPhases sepgPhases) {
		// TODO Auto-generated method stub
		sepgPhasesDao.delete(sepgPhases);
	}

	public void update(SEPGPhases sepgPhases) {
		sepgPhasesDao.update(sepgPhases);
		
	}

	public SEPGPhases findSEPGPhaseById(int id) {
		// TODO Auto-generated method stub
		return sepgPhasesDao.findSEPGPhaseById(id);
	}

	public List<SEPGPhases> findByPhaseId(int id) {
		// TODO Auto-generated method stub
		return sepgPhasesDao.findByPhaseId(id);
	}
}
