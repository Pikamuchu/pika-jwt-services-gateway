package com.pikamachu.services.auth.managers;

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.util.logging.Level;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

import org.apache.commons.codec.binary.Base64;

import com.pikamachu.services.auth.beans.UserBean;
import com.pikamachu.services.auth.clients.ClientLdap;
import com.pikamachu.services.auth.enums.UserTypeEnum;
import com.pikamachu.services.auth.params.LoginParams;
import com.pikamachu.services.common.exceptions.UnauthorizedException;
import com.pikamachu.services.common.managers.BasicServiceManager;
import com.pikamachu.services.common.params.IdParams;

/**
 * The Class LoginServiceManager.
 */
@ApplicationScoped
public class LoginServiceManager extends BasicServiceManager {

	/** The ldap. */
	@Inject
	private ClientLdap ldap;

	/**
	 * Checks if is valid user.
	 *
	 * @param params the params
	 * @return true, if is valid user
	 * @throws Exception the exception
	 */
	public LoginParams basicAuthenticate(IdParams params) throws Exception {
		
		if (params == null || params.getId() == null) {
			throw new UnauthorizedException("Null authentication!");
		}
		
		StringTokenizer st = new StringTokenizer(params.getId());
		if (!st.hasMoreTokens()) {
			throw new UnauthorizedException("Invalid authentication format!");
		}
		
		String basic = st.nextToken();
		if (!basic.equalsIgnoreCase("Basic")) {
			throw new UnauthorizedException("Invalid authentication format!");
		}
		
		LoginParams auth = new LoginParams();
		try {
			String credentials = new String(Base64.decodeBase64(st.nextToken()), "UTF-8");
			log.fine("Credentials: " + credentials);
			int p = credentials.indexOf(":");
			if (p != -1) {
				auth.setUsername(credentials.substring(0, p).trim());
				auth.setPassword(credentials.substring(p + 1).trim());
			} else {
				throw new UnauthorizedException("Invalid authentication token!");
			}
		} catch (UnsupportedEncodingException e) {
			throw new UnauthorizedException("Couldn't retrieve authentication!", e);
		}
		
		return auth;

	}

	/**
	 * Checks if is valid user.
	 *
	 * @param login the login
	 * @return true, if is valid user
	 */
	public UserBean doUserLogin(LoginParams login) {
		SearchResult searchResult = null;
		UserBean user = null;
		try {
			searchResult = ldap.isValidLdapUser(login.getUsername(), login.getPassword());
			if (searchResult != null) {
				Attributes attributes = searchResult.getAttributes();
				String username = null;
				String name = null;
				Object o;
				if (attributes != null) {
					// account name
					o = attributes.get("samaccountname");
					if (o != null) {
						username = o.toString().replaceFirst("sAMAccountName: ", "");
						// Person name
						o = attributes.get("name");
						if (o != null) {
							name = o.toString().replaceFirst("name: ", "");
						}
						user = new UserBean();
						user.setUsername(username);
						user.setDisplayname(name);
						user.setType(UserTypeEnum.LDAP.name());
					}
				}
			}
		} catch (Exception e) {
			if (log.isLoggable(Level.FINE)) {
				log.log(Level.SEVERE, "Error validating user login. Exception " + e.getMessage(), e);
			} else {
				log.log(Level.SEVERE, "Error validating user login. Exception " + e.getMessage());
			}
		}
		return user;
	}

}
