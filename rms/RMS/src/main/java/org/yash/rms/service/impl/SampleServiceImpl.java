package org.yash.rms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.UserDao;
import org.yash.rms.service.SampleService;

@Service("SampleServiceImpl")
public class SampleServiceImpl implements SampleService {
	
	@Autowired
	UserDao userdao;

	public void save() {
		userdao.save();		
	}

}
