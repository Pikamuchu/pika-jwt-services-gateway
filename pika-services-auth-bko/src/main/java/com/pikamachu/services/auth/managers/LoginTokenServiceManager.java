package com.pikamachu.services.auth.managers;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.pikamachu.services.auth.beans.TokenBean;
import com.pikamachu.services.auth.params.LoginParams;
import com.pikamachu.services.common.annotations.Config;
import com.pikamachu.services.common.managers.BasicServiceManager;
import com.pikamachu.services.common.security.TokenManager;

/**
 * The Class LoginServiceManager.
 */
@ApplicationScoped
public class LoginTokenServiceManager extends BasicServiceManager {

	/**
	 * The Token manager.
	 */
	@Inject
	protected TokenManager tokenManager;

	/**
	 * The Application audience.
	 */
	@Inject
	@Config
	protected String applicationAudience;

	/**
	 * Checks if is valid user.
	 *
	 * @param login the login
	 * @return true, if is valid user
	 * @throws Exception the exception
	 */
	public TokenBean generateLoginToken(LoginParams login) throws Exception {
		
		List<String> applications = new ArrayList<String>();
		
		applications.add(applicationAudience);
		
		String token = tokenManager.generateAuthToken(login.getUsername(), applications);
		
		TokenBean tokenBean = new TokenBean(token);
		
		return tokenBean;
	
	}

}
