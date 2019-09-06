package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.EventDao;
import org.yash.rms.dao.GradeDao;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.Event;
import org.yash.rms.domain.Grade;
import org.yash.rms.form.EventDataForm;
import org.yash.rms.service.EventService;
import org.yash.rms.service.GradeService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;
@Service("eventService")
public class EventServiceImpl implements EventService {

	@Autowired
	EventDao eventDao;

	@Autowired
	private DozerMapperUtility mapper;
	
	@SuppressWarnings("unchecked")
	public boolean update(Event event) {
		// TODO Auto-generated method stub
		return eventDao.saveOrupdate(event);
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return eventDao.delete(id);
	}

	public boolean saveOrupdateEvent(EventDataForm eventDataForm) {
		// TODO Auto-generated method stub
		return eventDao.saveOrupdate(mapper.convertDTOObjectToDomain(eventDataForm));
	}

	@SuppressWarnings("unchecked")
	public List<Event> findAll() {
		// TODO Auto-generated method stub
		return  mapper.convertEventDomainListToDTOList(eventDao.findAll());
	}

	public List<Event> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Event findById(int id) {
		// TODO Auto-generated method stub
		return eventDao.findById(id);
	}

	public List<Event> findAllEvents() {
		return  mapper.convertEventDomainListToDTOList(eventDao.findAllEvents());
	}

	public boolean saveOrupdate(Event t) {
		// TODO Auto-generated method stub
		return eventDao.saveOrupdate(mapper.convertDTOObjectToDomain(t));
	} 

	/*public boolean create(Grade grade) {
		// TODO Auto-generated method stub
		return gradeDao.create(grade);
	}*/
	
	public Event findByName(String name) {
		// TODO Auto-generated method stub
		return eventDao.findByName(name);
	}

}
