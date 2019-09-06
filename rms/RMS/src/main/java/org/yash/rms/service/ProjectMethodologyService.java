package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.BillingScale;
import org.yash.rms.domain.ProjectMethodology;

public interface ProjectMethodologyService {

 

	public boolean create(ProjectMethodology projectMethodology);
	
	public boolean delete(int id);
	
	public boolean update(ProjectMethodology projectMethodology);

	public List<ProjectMethodology> findAllProjectMethodology();

	public List<ProjectMethodology> findProjectMethodologyEntries(int firstResult, int sizeNo);

	public long countProjectMethodology();
}
