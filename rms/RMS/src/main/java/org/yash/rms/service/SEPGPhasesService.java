package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.Phase;
import org.yash.rms.domain.SEPGPhases;

public interface SEPGPhasesService extends RmsCRUDService<SEPGPhases> {

	void save(List<SEPGPhases> sepgPhasesList);
	void delete(SEPGPhases sepgPhases);
	void update(SEPGPhases sepgPhases);
	SEPGPhases findSEPGPhaseById(int id);
	public List<SEPGPhases> findAll();
	public List<SEPGPhases> findByPhaseId(int id);
	
}
