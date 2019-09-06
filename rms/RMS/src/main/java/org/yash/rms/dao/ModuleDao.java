package org.yash.rms.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.domain.BillingScale;
import org.yash.rms.domain.Module;

@Transactional
public interface ModuleDao {

	
	
 
	
	 
	
	public boolean update(Module module);
	
	public List<Module> findAllModule();

	public List<Module> findModuleEntries(int firstResult, int sizeNo);

	public Long countModule();

	public boolean delete(int id);

	public boolean create(Module module);
}
