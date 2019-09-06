package org.yash.rms.service.impl;

import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchResult;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.impl.ActivityDaoImpl;
import org.yash.rms.domain.User;
import org.yash.rms.domain.User.USER_DATA;
import org.yash.rms.filter.ActiveDirectoryUserInfo;
import org.yash.rms.service.LoginService;

@Service("loginServiceImpl")
public class LoginServiceImpl implements LoginService {
	
	@Value("${rms.domainName}")
	String domainName;
	
	@Value("${rms.serverName}")
	String server;
	

	
	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	public User getLogin(String userName, String password) {
		
		logger.info("Login Service is running!!");
	
		
		User user = null;
		DirContext context = ActiveDirectoryUserInfo.authanticateUser(domainName, server, userName, password);
		if (context != null) {
			user = new User();
			user.setUsername(userName);
			SearchResult searchResult;
			try {
				searchResult = ActiveDirectoryUserInfo.locateUserRecord(domainName, userName, userName + "@" + domainName, context);
				context.close();
				if(null!=searchResult){
					user.setDataFromLDAPExist(true);
					Map<USER_DATA, Object> userData = ActiveDirectoryUserInfo.getGrantedAuthorities(context, searchResult);
					ActiveDirectoryUserInfo.displayUserRecord(userData,user);
				}else{
					user.setDataFromLDAPExist(false);
					logger.info("No user Data to display");
			    }
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		logger.info("Login Service is getting End!!");
		return user;
	}



}
