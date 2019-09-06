/**
 * 
 */
package org.yash.rms.filter;

import static javax.naming.directory.SearchControls.SUBTREE_SCOPE;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.util.StringUtils;
import org.yash.rms.domain.User;
import org.yash.rms.domain.User.USER_DATA;

import com.sun.jndi.ldap.LdapCtxFactory;

/**
 * @author purvesh.maheshwari
 * 
 */
@SuppressWarnings({ "restriction" })
public class ActiveDirectoryUserInfo {

	/**
	 * @param domainName
	 * @param serverName
	 * @param username
	 * @param password
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static DirContext authanticateUser(String domainName,
			String serverName, String username, String password) {
		DirContext context = null;
		try {
			System.out.println("Authenticating " + username + "@" + domainName
					+ " through " + serverName + "." + domainName);
			// bind by using the specified username/password
			Hashtable props = new Hashtable();
			String principalName = username + "@" + domainName;
			props.put(Context.SECURITY_PRINCIPAL, principalName);
			props.put(Context.SECURITY_CREDENTIALS, password);
			context = LdapCtxFactory.getLdapCtxInstance("ldap://" + serverName
					+ "." + domainName + '/', props);
			System.out.println("Authentication succeeded!");
		} catch (AuthenticationException a) {
			System.err.println("Authentication failed:" + a);
		} catch (NamingException e) {
			System.err
					.println("Failed to bind to LDAP / get account information:"
							+ e);
		}
		return context;
	}

	/**
	 * @param groups
	 */
	@SuppressWarnings("unchecked")
	public static void displayUserRecord(Map<USER_DATA, Object> dataMap,
			User user) {
		System.out.println("User belongs to: ");
		Iterator<Entry<USER_DATA, Object>> ig = dataMap.entrySet().iterator();
		while (ig.hasNext()) {
			Entry<USER_DATA, Object> entry = ig.next();
			String key = entry.getKey().name();
			Object value = entry.getValue();
			if (entry.getValue().getClass().isAssignableFrom(List.class)) {
				System.out.println(key + ": ");
				for (String objectValue : (List<String>) value) {
					System.out.println("   " + objectValue);
				}
			} else {
				System.out.println(entry.getKey().name() + ": "
						+ entry.getValue());
			}

			try {
				User.class.getDeclaredMethod("set"+StringUtils.capitalize(key.toLowerCase()), value.getClass()).invoke(user, value);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * locate this user's record
	 * 
	 * @param domainName
	 * @param username
	 * @param principalName
	 * @param context
	 * @return
	 * @throws NamingException
	 */
	public static SearchResult locateUserRecord(String domainName,
			String username, String principalName, DirContext context)
			throws NamingException {
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SUBTREE_SCOPE);
		NamingEnumeration<SearchResult> renum = context.search(
				toDC(domainName), "(& (userPrincipalName=" + principalName
						+ ")(objectClass=user))", controls);
		if (!renum.hasMore()) {
			return null;
		}
		return renum.next();
	}

	public static Map<USER_DATA, Object> getGrantedAuthorities(
			DirContext context, SearchResult result) throws NamingException {
		Map<USER_DATA, Object> userDetailMap = new HashMap<USER_DATA, Object>();
		Attributes attributes = result.getAttributes();
		Attribute memberOf = attributes.get("memberOf");
		List<String> groups = null;
		if (memberOf != null) {// null if this user belongs to no group at all
			groups = new ArrayList<String>();
			for (int i = 0; i < memberOf.size(); i++) {
				Attributes atts = context.getAttributes(memberOf.get(i)
						.toString(), new String[] { "CN" });
				Attribute att = atts.get("CN");
				groups.add(att.get().toString().trim());
			}
		}
		userDetailMap.put(USER_DATA.MEMBER_OF, groups);
		userDetailMap.put(USER_DATA.MAIL, getValueFromAttributes(attributes.get("mail")));
		userDetailMap.put(USER_DATA.DEPARTMENT, getValueFromAttributes(attributes.get("department")));
		userDetailMap.put(USER_DATA.OFFICE, getValueFromAttributes(attributes.get("office")));
		userDetailMap.put(USER_DATA.MOBILE, getValueFromAttributes(attributes.get("mobile")));
		userDetailMap.put(USER_DATA.MANAGER, getValueFromAttributes(attributes.get("manager")));
		userDetailMap.put(USER_DATA.TELEPHONE_NUMBER,getValueFromAttributes(attributes.get("telephoneNumber")));
		return userDetailMap;
	}

	private static String getValueFromAttributes(Attribute attribute) throws NamingException{
		Object attributeObject = attribute!=null?attribute.get():null;
		return attributeObject!=null?attributeObject.toString().trim():"";
	}

	private static String toDC(String domainName) {
		StringBuilder buf = new StringBuilder();
		for (String token : domainName.split("\\.")) {
			if (token.length() == 0)
				continue; // defensive check
			if (buf.length() > 0)
				buf.append(",");
			buf.append("DC=").append(token);
		}
		return buf.toString();
	}

}
