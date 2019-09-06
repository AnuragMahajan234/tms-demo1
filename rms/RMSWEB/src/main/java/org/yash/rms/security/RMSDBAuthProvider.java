package org.yash.rms.security;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.util.UserUtil;

@Component(value="userService")
public class RMSDBAuthProvider implements UserDetailsService {
   
	@Autowired
	private UserUtil userUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(RMSDBAuthProvider.class);
	
   public UserContextDetails mapUserFromContext(DirContextOperations ctx, String userName, Collection<? extends GrantedAuthority> authorities) {
      
	   UserContextDetails userContextDetails = userUtil.getCurrentResource(userName);
	   return userContextDetails;
   }

   public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
   }
   
   public UserUtil getUserUtil() {
	   return userUtil;
	}

	public void setUserUtil(UserUtil userUtil) {
		this.userUtil = userUtil;
	}

	@Transactional
	public UserDetails  loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		
		UserContextDetails userContextDetails  = null;
		try{
			 userContextDetails = userUtil.getCurrentResource(userName);
			 if(userContextDetails != null){
				 //redmine 110 need to allow user if user's logged in time is greater then user's release date.
             if(userContextDetails.getReleaseDate()!=null){
				 Date ReleaseDate = userContextDetails.getReleaseDate();
				 Date currentdate = new Date();
				 
				 if(ReleaseDate.before(currentdate)){
					 userContextDetails.setUserRole("ROLE_ANONYMOUS");
				 }
             }
             //redmine 110 need to allow user if user's logged in time is greater then user's release date.
				 if(!userContextDetails.getUserRole().equalsIgnoreCase("ROLE_ADMIN")  ) {
					 userContextDetails.getAuthorities().add(new SimpleGrantedAuthority(userContextDetails.getUserRole()));
					}
				 userContextDetails.getAuthorities().add(new SimpleGrantedAuthority(userContextDetails.getUserRole()));
				 userContextDetails.getAuthorities().add(new SimpleGrantedAuthority(userContextDetails.getOstVisibility()));
			}else {
				userContextDetails = new UserContextDetails();
				userContextDetails.setUserRole("ROLE_ANONYMOUS");
				userContextDetails.setUserName(userName);
			}
		}catch(Exception exception){
			logger.error("Exception occured in Authentication :-"+exception);
			exception.printStackTrace();
			userContextDetails = new UserContextDetails();
			userContextDetails.setUserRole("ROLE_ANONYMOUS");
			userContextDetails.setUserName(userName);
			 
		}
		return userContextDetails;
	}
}