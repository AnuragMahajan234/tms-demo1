package org.yash.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface RmsCRUDService<T> {
	public boolean delete(int id);
	
	public boolean saveOrupdate(T t);

	public List<T> findAll();

	public List<T> findByEntries(int firstResult, int sizeNo);

	public long countTotal();
	

}
