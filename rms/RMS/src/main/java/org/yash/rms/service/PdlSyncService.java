package org.yash.rms.service;

import javax.naming.AuthenticationException;

public interface PdlSyncService {
	public boolean syncPdl(Integer userId, String username, String password) throws AuthenticationException; 
}
