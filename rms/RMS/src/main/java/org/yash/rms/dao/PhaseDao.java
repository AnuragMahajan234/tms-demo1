package org.yash.rms.dao;

import java.io.Serializable;

import org.yash.rms.domain.Phase;

public interface PhaseDao extends RmsCRUDDAO<Phase> {

	public Phase getPhase(int id);

	public Serializable save(Phase phase);

	public void delete(Phase phase);

	public void update(Phase phase);

	public boolean isAlreadyExist(String phaseName);
}
