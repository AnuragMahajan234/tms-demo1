/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.LocationDao;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.Location;
import org.yash.rms.service.LocationService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;

/**
 * @author ankita.shukla
 *
 */
@Service("LocationService")
public class LocationServiceImpl implements LocationService {

	@Autowired  
	LocationDao locationDao;

	@Autowired
	private DozerMapperUtility mapper;
	
	public boolean delete(int id) {
		return locationDao.delete(id);
	}

	public boolean saveOrupdate(Location location) {
		return locationDao.saveOrupdate(mapper.convertDTOObjectToDomain(location));
	}

	public List<Location> findAll() {
		return mapper.convertlocationListToDTOList(locationDao.findAll());
	}

	public List<Location> findByEntries(int firstResult, int sizeNo) {
		return mapper.convertlocationListToDTOList(locationDao.findByEntries(firstResult, sizeNo));
	}

	public long countTotal() {
		return locationDao.countTotal();
	}

	public Location findById(int id) {
		// TODO Auto-generated method stub
		return locationDao.findById(id);
	}

	public Location findLocationByName(String locationName) {
		return locationDao.findLocationByName(locationName);
	}
	


}
