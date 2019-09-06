package org.yash.rms.dao;

import java.util.List;
import org.yash.rms.domain.AllocationType;
public interface RmsCRUDDAO<T> {
	
	public boolean delete(int id);
	
	public boolean saveOrupdate(T t);

	public List<T> findAll();

	public List<T> findByEntries(int firstResult, int sizeNo);

	public long countTotal();
	
}
