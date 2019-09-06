package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.Event;
import org.yash.rms.domain.Grade;
import org.yash.rms.domain.Location;
import org.yash.rms.form.EventDataForm;
 
public interface EventService extends RmsCRUDService<Event>{

public Event findById(int id);

public List<Event> findAllEvents();

public boolean saveOrupdateEvent(EventDataForm dataForm);

public Event findByName(String name);
	 
}
