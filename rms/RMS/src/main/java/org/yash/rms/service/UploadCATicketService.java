package org.yash.rms.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.yash.rms.domain.CATicket;
import org.yash.rms.domain.CATicketDiscrepencies;
import org.yash.rms.domain.Landscape;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Region;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.RootCause;
import org.yash.rms.domain.Unit;

@Service("UploadCATicketService")
public interface UploadCATicketService {

	public Unit getUnitByUnitName(String unitName);

	public Landscape getLandscapeByName(String landscapeName);

	public Project getModuleByModuleName(String moduleName);

	public Resource getEmployeeByRECFId(String recfId);

	public RootCause getRootCauseByRootCauseName(String rootCauseName);

	public Serializable saveDiscrepencies(
			CATicketDiscrepencies caTicketDiscrepencies);

	public List<CATicketDiscrepencies> findAllDiscrepencyTickets();

	public CATicketDiscrepencies findDiscrepenciesTicketById(int id);
	
	public Region findRegionByUnit(int unitId);
}
