/**
 * 
 */
package org.yash.rms.dao;

import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.form.TimeSheet;

/**
 * @author arpan.badjatiya
 *
 */
@Transactional
public interface TimeSheetDao extends RmsCRUDDAO<TimeSheet> {

}
