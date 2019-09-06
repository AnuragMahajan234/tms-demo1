package org.yash.rms.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.util.UserUtil;

import waffle.servlet.WindowsPrincipal;

public class CustomAuthenticationToken  implements Authentication {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationToken.class);
	
    private static final long serialVersionUID = 1L;
    
    private WindowsPrincipal windowsPrincipal = null;
    private UserContextDetails principal = null;

    private Integer employeeId;
	
	private String userName;
	
	private String userRole;
	
	private boolean isBehalfManager;
	
	private String employeeName;
	
	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	
	private List<OrgHierarchy> accessRight= new ArrayList<OrgHierarchy>();
	

    // TODO: Move this to a config
    public static final String LDAP_DOMAIN = "YASH";

     /**
     * Constructor that fully initializes the principal
     *
     * @param windowsPrincipal windows principal
     */
    public CustomAuthenticationToken(WindowsPrincipal windowsPrincipal,String userName,String password, UserUtil userUtil,boolean userAuthenticated) {
    	init(windowsPrincipal, userName, password, userUtil,userAuthenticated);
    }
    
    public CustomAuthenticationToken(String userName,String password, UserUtil userUtil,boolean userAuthenticated) {
    	init(userName, password, userUtil,userAuthenticated);
    }

	private void init(WindowsPrincipal windowsPrincipal,String userName,String password, UserUtil userUtil,boolean userAuthenticated) {
		principal  = null;
        this.windowsPrincipal = windowsPrincipal;
        String username = userName;// windowsPrincipal.getName().substring(LDAP_DOMAIN.length() + 1);
		try{
			if(windowsPrincipal==null && !userAuthenticated){
				throw new RuntimeException("Unauthorized user");
			}
			principal = userUtil.getCurrentResource(username);
			 if(principal != null){
				 
				 if(!principal.getUserRole().equalsIgnoreCase("ROLE_ADMIN")  ) {
					 principal.getAuthorities().add(new SimpleGrantedAuthority(principal.getUserRole()));
				 }else{
					 principal.getAuthorities().add(new SimpleGrantedAuthority(principal.getUserRole()));
				 }
			}else {
				principal = new UserContextDetails();
				principal.setUserRole("ROLE_ANONYMOUS");
				principal.setUserName(username);
				principal.getAuthorities().add(new SimpleGrantedAuthority(principal.getUserRole()));
			}
		}catch(Exception exception){
			logger.error("Exception occured in Authentication :-"+exception);
			principal = new UserContextDetails();
			principal.setUserRole("ROLE_ANONYMOUS");
			principal.setUserName(username);
			principal.getAuthorities().add(new SimpleGrantedAuthority(principal.getUserRole()));
//			throw new RuntimeException(exception.getMessage());
		}
	}
	
	
	private void init(String userName,String password, UserUtil userUtil,boolean userAuthenticated) {
		principal  = null;
       
        String username = userName;// windowsPrincipal.getName().substring(LDAP_DOMAIN.length() + 1);
		try{
			if(!userAuthenticated){
				throw new RuntimeException("Unauthorized user");
			}
			principal = userUtil.getCurrentResource(username);
			 if(principal != null){
				 
				 if(!principal.getUserRole().equalsIgnoreCase("ROLE_ADMIN")  ) {
					 principal.getAuthorities().add(new SimpleGrantedAuthority(principal.getUserRole()));
				 }else{
					 principal.getAuthorities().add(new SimpleGrantedAuthority(principal.getUserRole()));
				 }
			}else {
				principal = new UserContextDetails();
				principal.setUserRole("ROLE_ANONYMOUS");
				principal.setUserName(username);
				principal.getAuthorities().add(new SimpleGrantedAuthority(principal.getUserRole()));
			}
		}catch(Exception exception){
			logger.error("Exception occured in Authentication :-"+exception);
			principal = new UserContextDetails();
			principal.setUserRole("ROLE_ANONYMOUS");
			principal.setUserName(username);
			principal.getAuthorities().add(new SimpleGrantedAuthority(principal.getUserRole()));
			throw new RuntimeException(exception.getMessage());
		}
	}

    public Object getCredentials() {
        return null;
    }

    public Object getDetails() {
        return windowsPrincipal;
    }

    public Object getPrincipal() {
        return principal;
    }

    public boolean isAuthenticated() {
        return (principal != null);
    }

    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException();
    }

    public String getName() {
        return principal.getUsername();
    }

	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return principal.getAuthorities();
	}
	
	public Integer getEmployeeId() {
		return principal.getEmployeeId();
	}

	public String getUserName() {
		return principal.getUsername();
	}

	public String getUserRole() {
		return principal.getUserRole();
	}

	public boolean isBehalfManager() {
		return principal.isBehalfManager();
	}

	public String getEmployeeName() {
		return principal.getEmployeeName();
	}

	public List<OrgHierarchy> getAccessRight() {
		return principal.getAccessRight();
	}
}