package org.yash.rms.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.UploadCATicketDao;
import org.yash.rms.domain.CATicket;
import org.yash.rms.domain.CATicketDiscrepencies;
import org.yash.rms.domain.Landscape;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Region;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.RootCause;
import org.yash.rms.domain.Unit;
import org.yash.rms.service.UploadCATicketService;

@Service("UploadCATicketService")
public class UploadCATicketServiceImpl implements UploadCATicketService {

	@Autowired
	UploadCATicketDao caTicketDao;

	public Unit getUnitByUnitName(String unitName) {
		return caTicketDao.getUnitByUnitName(unitName);
	}

	public Landscape getLandscapeByName(String landscapeName) {
		return caTicketDao.getLandscapeByName(landscapeName);
	}

	public Project getModuleByModuleName(String moduleName) {
		return caTicketDao.getModuleByModuleName(moduleName);
	}

	public Resource getEmployeeByRECFId(String recfId) {
		return caTicketDao.getEmployeeByRECFId(recfId);
	}

	public RootCause getRootCauseByRootCauseName(String rootCauseName) {
		return caTicketDao.getRootCauseByRootCauseName(rootCauseName);
	}

	public Serializable saveDiscrepencies(
			CATicketDiscrepencies caTicketDiscrepencies) {
		return caTicketDao.saveDiscrepencies(caTicketDiscrepencies);
	}

	public List<CATicketDiscrepencies> findAllDiscrepencyTickets() {
		return caTicketDao.findAllDiscrepencyTickets();
	}

	public CATicketDiscrepencies findDiscrepenciesTicketById(int id) {
		return caTicketDao.findDiscrepenciesTicketById(id);
	}

	public Region findRegionByUnit(int unitId) {
		return caTicketDao.findRegionByUnit(unitId);
	}

}
