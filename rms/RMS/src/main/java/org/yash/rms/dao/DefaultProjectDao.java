package org.yash.rms.dao;

import org.yash.rms.domain.DefaultProject;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
/**
 * 
 * @author purva.bhate
 *
 */
public interface DefaultProjectDao extends RmsCRUDDAO<DefaultProject> {
	public DefaultProject getDefaultProjectbyProjectId(Project projectId);
	public DefaultProject getDefaultProjectbyProjectForBU(OrgHierarchy currentBuId);
	
}
