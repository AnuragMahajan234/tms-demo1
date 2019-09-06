package org.yash.rms.dao;

 
import org.yash.rms.domain.Roles;

public interface RoleDao extends RmsCRUDDAO<Roles>{

	
	public Roles findById(int id);
	
	public Roles findByRole(String role);
}
