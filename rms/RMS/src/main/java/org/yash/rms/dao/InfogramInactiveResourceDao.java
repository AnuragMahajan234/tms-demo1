package org.yash.rms.dao;
/**
 * Dao interface for InfogramInActiveResource.
 * @author samiksha.sant
 *
 */

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.domain.InfogramInactiveResource;
import org.yash.rms.dto.InfogramInactiveResourceDTO;
import org.yash.rms.exception.DAOException;
import org.yash.rms.util.HibernateUtil;
import org.yash.rms.util.InfogramProcessStatusConstants;
import org.yash.rms.util.SearchCriteriaGeneric;

public interface InfogramInactiveResourceDao {

	public List<InfogramInactiveResource> getAllInfogramInactiveResources(SearchCriteriaGeneric searchCriteriaGeneric);

	public InfogramInactiveResource getInfogramInactiveResourceById(Integer id);

	public void updateInfogramInactiveResource(InfogramInactiveResource inactiveResource) throws DAOException;
	
	public Long getTotalCountInactResources(SearchCriteriaGeneric searchCriteriaGeneric) ;
	
	public List<InfogramInactiveResource> getAllInfogramInactiveResources();
	
	public List<InfogramInactiveResourceDTO> getAllInfogramInActiveResourcesReport();
		
}
