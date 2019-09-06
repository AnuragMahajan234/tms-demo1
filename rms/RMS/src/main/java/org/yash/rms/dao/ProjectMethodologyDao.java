package org.yash.rms.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.domain.BillingScale;
import org.yash.rms.domain.Module;
import org.yash.rms.domain.ProjectMethodology;

@Transactional
public interface ProjectMethodologyDao {

	
	
 
	
	 
	
	public boolean update(ProjectMethodology projectMethodology);
	
	public List<ProjectMethodology> findAllProjectMethodologies();

	public List<ProjectMethodology> findProjectMethodologyEntries(int firstResult, int sizeNo);

	public Long countProjectMethodology();

	public boolean delete(int id);

	public boolean create(ProjectMethodology projectMethodology);
}
