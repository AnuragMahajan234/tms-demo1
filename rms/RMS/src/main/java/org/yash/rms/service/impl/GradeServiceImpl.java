package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.GradeDao;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.Grade;
import org.yash.rms.service.GradeService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;
@Service("gradeService")
public class GradeServiceImpl implements GradeService {

	@Autowired
	GradeDao gradeDao;

	@Autowired
	private DozerMapperUtility mapper;
	
	@SuppressWarnings("unchecked")
	public boolean update(Grade grade) {
		// TODO Auto-generated method stub
		return gradeDao.saveOrupdate(grade);
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return gradeDao.delete(id);
	}

	public boolean saveOrupdate(Grade t) {
		// TODO Auto-generated method stub
		return gradeDao.saveOrupdate(mapper.convertDTOObjectToDomain(t));
	}

	@SuppressWarnings("unchecked")
	public List<Grade> findAll() {
		// TODO Auto-generated method stub
		return  mapper.convertGradeDomainListToDTOList(gradeDao.findAll());
	}

	public List<Grade> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Grade findById(int id) {
		// TODO Auto-generated method stub
		return gradeDao.findById(id);
	}

	public Grade findByName(String name) {
		// TODO Auto-generated method stub
		return gradeDao.findByName(name);
	}

	/*public boolean create(Grade grade) {
		// TODO Auto-generated method stub
		return gradeDao.create(grade);
	}*/

}
