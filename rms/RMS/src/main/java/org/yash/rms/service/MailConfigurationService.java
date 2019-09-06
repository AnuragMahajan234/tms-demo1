package org.yash.rms.service;

 

import java.util.List;

import org.yash.rms.domain.MailConfiguration;
import org.yash.rms.domain.Project;
 

 

public interface MailConfigurationService extends RmsCRUDService<MailConfiguration>{

	
	 
public List<String> findById(int id);

public boolean saveConfigurations(List<MailConfiguration> list);

public List<MailConfiguration> findByProjectId(int id, int confg);

public MailConfiguration getMailConfg(int projectId, int confg, int roleId);

public boolean delete(int projectId, int confgId);
	 
public void saveDefaultConfigs(Project project);

}
