/**
 * 
 */
package org.yash.rms.dao;

import org.yash.rms.domain.Activity;
import org.yash.rms.domain.Location;
 
/**
 * @author arpan.badjatiya
 *
 */
public interface LocationDao extends RmsCRUDDAO<Location> {

	public Location findById(int id);

	public Location findLocationByName(String locationName);
}
