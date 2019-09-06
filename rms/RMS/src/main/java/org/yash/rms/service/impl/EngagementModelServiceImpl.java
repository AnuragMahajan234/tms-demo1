/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.EngagementModelDao;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.EngagementModel;
import org.yash.rms.domain.SEPGPhases;
import org.yash.rms.service.EngagementModelService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;

/**
 * @author ankita.shukla
 *
 */
@Service("EngagementModelService")
public class EngagementModelServiceImpl implements EngagementModelService {

	@Autowired @Qualifier("EngagementModelDao")
	EngagementModelDao engagementModelDao;
	
	@Autowired
	private DozerMapperUtility mapper;

	public boolean delete(int id) {
		return engagementModelDao.delete(id);
	}

	public boolean saveOrupdate(EngagementModel engagementModel) {
		return engagementModelDao.saveOrupdate(mapper.convertDTOObjectToDomain(engagementModel));
	}

	public List<EngagementModel> findAll() {
		return mapper.convertengagementModelDomainListToDTOList(engagementModelDao.findAll());	}

	public List<EngagementModel> findByEntries(int firstResult, int sizeNo) {
		return mapper.convertengagementModelDomainListToDTOList(engagementModelDao.findByEntries(firstResult, sizeNo));
	}

	public long countTotal() {
		return engagementModelDao.countTotal();
	}

	public List<EngagementModel> findLeftSepgEngagement(List<Integer> ids) {
		// TODO Auto-generated method stub
		return engagementModelDao.findLeftSepgEngagement(ids);
	}

	public List<EngagementModel> findSelectSepgEngagement(List<Integer> ids) {
		// TODO Auto-generated method stub
		return engagementModelDao.findSelectSepgEngagement(ids);
	}

	public List<SEPGPhases> getSEPGIdsWithEngagementModel() {
		// TODO Auto-generated method stub
		return engagementModelDao.getSEPGIdsWithEngagementModel();
	}

	public EngagementModel findById(Integer id) throws Exception {
		return engagementModelDao.findById(id);
	}

	public EngagementModel findEngagementModelByProjectId(Integer id) throws Exception {
		return engagementModelDao.findEngagementModelByProjectId(id);
	}
	


}
