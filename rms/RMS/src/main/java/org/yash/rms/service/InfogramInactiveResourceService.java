package org.yash.rms.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.yash.rms.domain.InfogramInactiveResource;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.InfogramInactiveResourceDTO;
import org.yash.rms.exception.RMSServiceException;
/**
 * InfogramInactiveResourceService Interface.
 * @author samiksha.sant
 *
 */
public interface InfogramInactiveResourceService {

	public List<InfogramInactiveResource> getAllInfogramInactiveResources(HttpServletRequest httpRequest);

	public Boolean updateExitDetails(Integer id, Resource resource) throws Exception;

	public void discardInfogramInactiveResource(Integer id, Resource resource);
	
	public Long getTotalCountInactResources(HttpServletRequest httpRequest) throws Exception;
	
	public void updateInfogramInactiveResource(InfogramInactiveResource infogramInactiveResource) throws RMSServiceException;
	
	public void updateProcessStatus(Integer id);
	
	public List<InfogramInactiveResourceDTO> getInfogramInActiveResourceReport();
	
	public List<InfogramInactiveResource> getAllInfogramInactiveResources();
}
		
