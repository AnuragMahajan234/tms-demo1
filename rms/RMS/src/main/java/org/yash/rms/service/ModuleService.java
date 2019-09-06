package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.Module;

 

public interface ModuleService {

	
	 

	public boolean create(Module module);
	
	public boolean delete(int id);
	
	public boolean update(Module module);

	public List<Module> findAllModule();

	public List<Module> findModuleEntries(int firstResult, int sizeNo);

	public long countModule();
}
