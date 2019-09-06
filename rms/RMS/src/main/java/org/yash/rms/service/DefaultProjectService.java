package org.yash.rms.service;

import org.yash.rms.domain.DefaultProject;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
/**
 * 
 * @author purva.bhate
 *
 */
public interface DefaultProjectService  extends RmsCRUDService<DefaultProject>{
	public boolean saveOrupdate(org.yash.rms.form.DefaultProjectForm form) ;
	public DefaultProject getDefaultProjectbyProjectId(Project projectId);
	public DefaultProject getDefaultProjectbyProjectForBU(OrgHierarchy projectId);
}
