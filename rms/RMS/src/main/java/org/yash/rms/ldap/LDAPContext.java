package org.yash.rms.ldap;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sun.jndi.ldap.LdapCtxFactory;

@Component
public class LDAPContext {
	
	@Value("${rms.serverName}")
	private String serverName ;
	
	@Value("${rms.domainName}")
	private String domainName;
	
	private String baseDomain;
	
	private static final Logger logger = LoggerFactory.getLogger(LDAPContext.class);
	
	@SuppressWarnings("restriction")
	public LdapContext getConnection(String userName, String password) throws AuthenticationException{
		LdapContext ldapContext = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.SECURITY_PRINCIPAL, userName+"@"+domainName);
			env.put(Context.SECURITY_CREDENTIALS,password);
			logger.info("Attempting to connect with LDAP server.......");
			ldapContext = (LdapContext)LdapCtxFactory.getLdapCtxInstance("ldap://" + serverName
					+ "." + domainName + '/', env);
			logger.info("Conneceted successfully with LDAP server.......");
		} catch(AuthenticationException e){
			throw new AuthenticationException("Invalid user credential");
		}catch (NamingException ex) {
			logger.error("LDAP Connection Exception: failed to connect with the LDAP server.");
			ex.printStackTrace();
		}
		return ldapContext;
	}
	
	public void closeConnection(LdapContext ldapContext){
		try{
			if(ldapContext !=null){
				ldapContext.close();
				ldapContext = null;
			}
		}catch (NamingException ex) {
			logger.error("Error while closing Ldap connection.");
			ex.printStackTrace();
		}
	}

	public String getServerName() {
		return serverName;
	}

	public String getDomainName() {
		return domainName;
	}
	
	public String getBaseDomain() {
		char[] namePair = domainName.toUpperCase().toCharArray();
	    String dn = "DC=";
	    for (int i = 0; i < namePair.length; i++) {
	        if (namePair[i] == '.') {
	            dn += ",DC=" + namePair[++i];
	        } else {
	            dn += namePair[i];
	        }
	    }
	   return dn;
    }

}
