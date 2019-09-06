/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.Currency;
 
import org.yash.rms.dto.ReleaseNotes;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;

/**
 * @author deepti.gupta
 *
 */
@Service("ReleaseNoteService")
public class ReleaseNotesServiceImpl implements RmsCRUDService<ReleaseNotes> {

	@Autowired @Qualifier("ReleaseNoteDao")
	RmsCRUDDAO<org.yash.rms.domain.ReleaseNotes> releaseNoteDao;
	
	@Autowired
	private DozerMapperUtility mapper;
	
	public boolean delete(int id) {
		return true;
	}

	public boolean create(Currency currency) {
		return  true;
	}

	 

	public List<ReleaseNotes> findAll() {
		return  mapper.convertReleaseNotesDomainListToDTOList(releaseNoteDao.findAll());
	}

	public long countTotal() {
		return 0;
	}

	public List<ReleaseNotes> findByEntries(int firstResult, int sizeNo) {
		return null;
	}

	public boolean saveOrupdate(ReleaseNotes t) {
		// TODO Auto-generated method stub
		return false;
	}

}
