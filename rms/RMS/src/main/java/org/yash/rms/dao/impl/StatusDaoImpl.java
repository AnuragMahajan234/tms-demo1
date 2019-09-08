package org.yash.rms.dao.impl;

import org.springframework.stereotype.Repository;
import org.yash.rms.dao.StatusDao;
import org.yash.rms.domain.Status;
import org.yash.rms.exception.DAOException;
import org.yash.rms.exception.DaoRestException;
import org.yash.rms.rest.dao.generic.impl.HibernateGenericDao;

@Repository
public class StatusDaoImpl extends HibernateGenericDao<Integer, Status> implements StatusDao {

	public StatusDaoImpl() {
		super(Status.class);
	}

	public Status createStatus(Status status) throws DAOException{
		try {
			return super.create(status);
		} catch (DaoRestException e) {
			throw new DAOException("503", e.getMessage());
		}
	}

	public Status updateStatus(Status status) throws DAOException {
		try {
			return super.update(status);
		} catch (DaoRestException e) {
			throw new DAOException("503", e.getMessage());
		}
	}
	
	

}
