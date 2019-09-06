/**
 * 
 */
package org.yash.rms.dao;

 
 
import java.util.List;

import org.yash.rms.domain.Event;
 
 
public interface EventDao extends RmsCRUDDAO<Event> {

	public Event findById(int id);
	
	public List<Event> findAllEvents();
	
	public Event findByName(String name);
}
