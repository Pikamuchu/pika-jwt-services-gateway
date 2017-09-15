package com.pikamachu.services.auth.clients;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.pikamachu.services.auth.beans.UserBean;
import com.pikamachu.services.auth.enums.UserTypeEnum;
import com.pikamachu.services.common.annotations.Config;

/**
 * The Class client LDAP.
 */
@Singleton
public class ClientLdap {

	/** The log. */
	@Inject
	private Logger log;

	/** The ldap ad server. */
	@Inject
	@Config
	private String ldapAdServer;

	/** The ldap search base. */
	@Inject
	@Config
	private String ldapSearchBase;

	/** The ldap username. */
	@Inject
	@Config
	private String ldapUsername;

	/** The ldap password. */
	@Inject
	@Config
	private String ldapPassword;

	/**
	 * Checks if is valid LDAP user.
	 *
	 * @param username the username
	 * @param password the password
	 * @return true, if is valid user
	 * @throws NamingException the naming exception
	 */
	public SearchResult isValidLdapUser(String username, String password) throws NamingException {

		// Buscamos el usuario que nos indican utilizando las credenciales del usuario generico
		SearchResult searchResult = checkLdapCredentials(getLdapUsername(), getLdapPassword(), username);
		if (searchResult == null) {
			// Usuario no encontrado
			log.info("User " + username + " not found!");
			return null;
		}

		// Realizamos la misma busqueda utilizando las credenciales que nos pasan para validar password
		searchResult = checkLdapCredentials(searchResult.getNameInNamespace(), password, username);
		if (searchResult == null) {
			// Password incorrecto
			log.info("User " + username + " with invalid password!");
			return null;
		}

		// User OK.
		log.info("User " + username + " login ok!");
		return searchResult;
	}

	/**
	 * Check ldap credentials.
	 *
	 * @param ldapName the ldap name
	 * @param pLdapPassword the ldap password
	 * @param username the username
	 * @return the search result
	 * @throws NamingException the naming exception
	 */
	public SearchResult checkLdapCredentials(String ldapName, String pLdapPassword, String username) throws NamingException {

		Hashtable<String, Object> env = new Hashtable<String, Object>();
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		if (ldapName != null) {
			env.put(Context.SECURITY_PRINCIPAL, ldapName);
		}
		if (pLdapPassword != null) {
			env.put(Context.SECURITY_CREDENTIALS, pLdapPassword);
		}
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapAdServer);

		// ensures that objectSID attribute values
		// will be returned as a byte[] instead of a String
		env.put("java.naming.ldap.attributes.binary", "objectSID");

		// the following is helpful in debugging errors
		// env.put("com.sun.jndi.ldap.trace.ber", System.err);

		LdapContext ctx = new InitialLdapContext(env, null);

		// lookup the ldap account
		SearchResult srLdapUser = findAccountByAccountName(ctx, getLdapSearchBase(), username);

		if (srLdapUser != null) {
			log.info("--Ldap user found " + srLdapUser.getName());
		} else {
			log.info("--Ldap user not found " + username);
		}

		return srLdapUser;

	}

	/**
	 * Find account by account name.
	 *
	 * @param ctx the ctx
	 * @param pLdapSearchBase the ldap search base
	 * @param accountName the account name
	 * @return the search result
	 * @throws NamingException the naming exception
	 */
	public SearchResult findAccountByAccountName(DirContext ctx, String pLdapSearchBase, String accountName) throws NamingException {

		String searchFilter = "(&(objectClass=user)(sAMAccountName=" + accountName + "))";

		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		NamingEnumeration<SearchResult> results = ctx.search(pLdapSearchBase, searchFilter, searchControls);

		SearchResult searchResult = null;
		if (results.hasMoreElements()) {
			searchResult = (SearchResult) results.nextElement();

			// make sure there is not another item available, there should be only 1 match
			if (results.hasMoreElements()) {
				log.info("Matched multiple users for the accountName: " + accountName);
				return null;
			}
		}

		return searchResult;
	}

	/**
	 * Find group by sid.
	 *
	 * @param ctx the ctx
	 * @param ldapSearchBase the ldap search base
	 * @param sid the sid
	 * @return the string
	 * @throws NamingException the naming exception
	 */
	public String findGroupBySID(DirContext ctx, String ldapSearchBase, String sid) throws NamingException {

		String searchFilter = "(&(objectClass=group)(objectSid=" + sid + "))";

		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);

		if (results.hasMoreElements()) {
			SearchResult searchResult = (SearchResult) results.nextElement();

			// make sure there is not another item available, there should be only 1 match
			if (results.hasMoreElements()) {
				log.info("Matched multiple groups for the group with SID: " + sid);
				return null;
			} else {
				return (String) searchResult.getAttributes().get("sAMAccountName").get();
			}
		}
		return null;
	}

	/**
	 * Gets the primary group sid.
	 *
	 * @param srLdapUser the sr ldap user
	 * @return the primary group sid
	 * @throws NamingException the naming exception
	 */
	public String getPrimaryGroupSID(SearchResult srLdapUser) throws NamingException {
		byte[] objectSID = (byte[]) srLdapUser.getAttributes().get("objectSid").get();
		String strPrimaryGroupID = (String) srLdapUser.getAttributes().get("primaryGroupID").get();

		String strObjectSid = decodeSID(objectSID);

		return strObjectSid.substring(0, strObjectSid.lastIndexOf('-') + 1) + strPrimaryGroupID;
	}

	/**
	 * The binary data is in the form: byte[0] - revision level byte[1] - count of sub-authorities byte[2-7] - 48 bit authority (big-endian) and then
	 * count x 32 bit sub authorities (little-endian)
	 * <p>
	 * The String value is: S-Revision-Authority-SubAuthority[n]...
	 * </p>
	 * Based on code from here - http://forums.oracle.com/forums/thread.jspa?threadID=1155740&tstart=0
	 *
	 * @param sid the sid
	 * @return the string
	 */
	public static String decodeSID(byte[] sid) {

		final StringBuilder strSid = new StringBuilder("S-");

		// get version
		final int revision = sid[0];
		strSid.append(Integer.toString(revision));

		// next byte is the count of sub-authorities
		final int countSubAuths = sid[1] & 0xFF;

		// get the authority
		long authority = 0;
		// String rid = "";
		for (int i = 2; i <= 7; i++) {
			authority |= ((long) sid[i]) << (8 * (5 - (i - 2)));
		}
		strSid.append("-");
		strSid.append(Long.toHexString(authority));

		// iterate all the sub-auths
		int offset = 8;
		int size = 4; // 4 bytes for each sub auth
		for (int j = 0; j < countSubAuths; j++) {
			long subAuthority = 0;
			for (int k = 0; k < size; k++) {
				subAuthority |= (long) (sid[offset + k] & 0xFF) << (8 * k);
			}

			strSid.append("-");
			strSid.append(subAuthority);

			offset += size;
		}

		return strSid.toString();
	}

	/**
	 * Search users.
	 *
	 * @param name the name
	 * @return the list
	 * @throws NamingException the naming exception
	 */
	public List<UserBean> searchUsers(String name) throws NamingException {

		Hashtable<String, Object> env = new Hashtable<String, Object>();
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, getLdapUsername());
		env.put(Context.SECURITY_CREDENTIALS, getLdapPassword());
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapAdServer);

		env.put("java.naming.ldap.attributes.binary", "objectSID");
		LdapContext ctx = new InitialLdapContext(env, null);

		String searchFilter = "(&(objectClass=user)(|(displayName=" + name + "*)(sAMAccountName=" + name + "*)))";

		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		NamingEnumeration<SearchResult> results = ctx.search(getLdapSearchBase(), searchFilter, searchControls);

		SearchResult searchResult = null;
		List<UserBean> users = new ArrayList<UserBean>();
		UserBean user = null;
		while (results.hasMoreElements()) {
			searchResult = (SearchResult) results.nextElement();
			if (searchResult != null) {
				String username = searchResult.getAttributes().get("samaccountname") != null ? searchResult.getAttributes().get("samaccountname").toString()
						.replaceFirst("sAMAccountName: ", "") : null;
				if (username == null) {
					continue;
				}
				String displayName = searchResult.getAttributes().get("displayname") != null ? searchResult.getAttributes().get("displayname").toString()
						.replaceFirst("displayName: ", "") : username;
				user = new UserBean();
				user.setUsername(username);
				user.setDisplayname(displayName);
				user.setType(UserTypeEnum.LDAP.name());
				users.add(user);
			}
		}
		if (users.isEmpty()) {
			log.info("Ldap user not found " + name);
		}
		return users;
	}

	/**
	 * Gets ldap ad server.
	 *
	 * @return the ldap ad server
	 */
	public String getLdapAdServer() {
		return ldapAdServer;
	}

	/**
	 * Sets ldap ad server.
	 *
	 * @param ldapAdServer the ldap ad server
	 */
	public void setLdapAdServer(String ldapAdServer) {
		this.ldapAdServer = ldapAdServer;
	}

	/**
	 * Gets ldap search base.
	 *
	 * @return the ldap search base
	 */
	public String getLdapSearchBase() {
		return ldapSearchBase;
	}

	/**
	 * Sets ldap search base.
	 *
	 * @param ldapSearchBase the ldap search base
	 */
	public void setLdapSearchBase(String ldapSearchBase) {
		this.ldapSearchBase = ldapSearchBase;
	}

	/**
	 * Gets ldap username.
	 *
	 * @return the ldap username
	 */
	public String getLdapUsername() {
		return ldapUsername;
	}

	/**
	 * Sets ldap username.
	 *
	 * @param ldapUsername the ldap username
	 */
	public void setLdapUsername(String ldapUsername) {
		this.ldapUsername = ldapUsername;
	}

	/**
	 * Gets ldap password.
	 *
	 * @return the ldap password
	 */
	public String getLdapPassword() {
		return ldapPassword;
	}

	/**
	 * Sets ldap password.
	 *
	 * @param ldapPassword the ldap password
	 */
	public void setLdapPassword(String ldapPassword) {
		this.ldapPassword = ldapPassword;
	}

}