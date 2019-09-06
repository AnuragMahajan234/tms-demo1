package org.yash.rms.ldap.dao;

import java.util.List;

import javax.naming.ldap.LdapContext;

public interface PdlDao {

	public List<String> getGroupMembers(String baseDomain, String pdlName, LdapContext ldapContext);
}
