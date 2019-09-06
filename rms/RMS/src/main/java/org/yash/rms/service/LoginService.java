package org.yash.rms.service;

import org.yash.rms.domain.User;

public interface LoginService {
	public User getLogin(String userName, String password);
}
