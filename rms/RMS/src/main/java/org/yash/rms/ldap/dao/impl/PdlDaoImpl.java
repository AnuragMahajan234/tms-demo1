package org.yash.rms.ldap.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import org.springframework.stereotype.Repository;
import org.yash.rms.ldap.dao.PdlDao;

@Repository("pdlDao")
public class PdlDaoImpl implements PdlDao {
	
	public List<String> getGroupMembers(String baseDomain, String pdlName, LdapContext ldapContext) {
		List<String> emailList = null;
		try{
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			StringBuilder ldapQuery = new StringBuilder();
			
			ldapQuery.append("(&(objectCategory=Person)(objectClass=User)(mail=*)(memberOf=");
			ldapQuery.append("CN="+pdlName);
			ldapQuery.append(",OU=Distribution Groups,DC=yash,DC=com))");
			
			System.out.println("Ldap Query :: "+ldapQuery.toString());
			
			NamingEnumeration<SearchResult> searchResults = ldapContext.search(baseDomain, ldapQuery.toString(),
					constraints);
			System.out.println("Ldap Query2 :: "+ldapQuery.toString());
			emailList = new ArrayList<String>();
			while(searchResults.hasMoreElements()){
				Attributes attrs = ((SearchResult) searchResults.next()).getAttributes();
				emailList.add(attrs.get("mail").get().toString());
			}
		}catch (NamingException nex) {
			nex.printStackTrace();
		}
		return emailList;
	}
	
	
}
