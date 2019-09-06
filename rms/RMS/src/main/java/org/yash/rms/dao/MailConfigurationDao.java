package org.yash.rms.dao;

 
import java.util.List;

import org.yash.rms.domain.MailConfiguration;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Roles;

public interface MailConfigurationDao extends RmsCRUDDAO<MailConfiguration>{

	
	public List<String> findById(int id);
	public boolean saveConfigurations(List<MailConfiguration> list);
	public List<MailConfiguration> findByProjectId(int id,int confg);
	
	public MailConfiguration getMailConfg(int projectId, int confg, int roleId);
	
	public boolean delete(int projectId, int confgId);
	public void saveDefaultConfigs(Project project); 
}
