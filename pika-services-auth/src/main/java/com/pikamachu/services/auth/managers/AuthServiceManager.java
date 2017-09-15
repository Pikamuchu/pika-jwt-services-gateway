package com.pikamachu.services.auth.managers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;

import com.pikamachu.services.auth.beans.UserBean;
import com.pikamachu.services.auth.models.User;
import com.pikamachu.services.auth.models.UserApplication;
import com.pikamachu.services.auth.params.AuthParams;
import com.pikamachu.services.auth.repositories.UserRepository;
import com.pikamachu.services.common.exceptions.UnauthorizedException;
import com.pikamachu.services.common.managers.BasicServiceManager;
import com.pikamachu.services.common.params.IdParams;
import com.pikamachu.services.common.utils.PasswordUtils;

/**
 * The Class AuthServiceManager.
 */
@Stateless
public class AuthServiceManager extends BasicServiceManager {

	/**
	 * The Repository.
	 */
	@Inject
	private UserRepository repository;

	/**
	 * The Password utils.
	 */
	@Inject
	private PasswordUtils passwordUtils;

	/**
	 * Checks if is valid user.
	 *
	 * @param params the params
	 * @return true, if is valid user
	 * @throws Exception the exception
	 */
	public AuthParams basicAuthenticate(IdParams params) throws Exception {
		
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
		
		AuthParams auth = new AuthParams();
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
	 * @param auth the auth
	 * @return true, if is valid user
	 * @throws Exception the exception
	 */
	public UserBean doAuthenticate(AuthParams auth) throws Exception {

		List<User> users = repository.findByProperty(User.class, "username", auth.getUsername());

		if (users.size() != 1) {
			throw new UnauthorizedException();
		}

		User user = users.get(0);
		String type = user.getType().name();
		if (type == null || !type.equalsIgnoreCase("LOCAL")) {
			log.warning("User " + user.getUsername() + " type not LOCAL!");
			throw new UnauthorizedException();
		}

		if (!passwordUtils.checkPassword(auth.getPassword(), user.getPassword())) {
			log.warning("User " + user.getUsername() + " password mismatch!");
			throw new UnauthorizedException();
		}

		UserBean bean = new UserBean();
		// Mappings
		BeanUtils.copyProperties(bean, user);

		// Applications
		List<UserApplication> applications = user.getApplications();
		if (applications == null || applications.isEmpty()) {
			log.warning("User " + user.getUsername() + " without applications!");
			throw new UnauthorizedException();
		}

		List<String> applicationNames = new ArrayList<String>();
		for (UserApplication application : applications) {
			String applicationName = application.getName().name();
			Boolean isAdmin = application.getIsAdmin();
			if (isAdmin != null && isAdmin.booleanValue()) {
				log.info("User " + user.getUsername() + " is admin for application " + applicationName + ". Application not added!");
				continue;
			}
			applicationNames.add(applicationName);
		}

		if (applicationNames.isEmpty()) {
			log.warning("User " + user.getUsername() + " without applicationNames!");
			throw new UnauthorizedException();
		}

		bean.setApplicationNames(applicationNames);

		return bean;

	}

}
