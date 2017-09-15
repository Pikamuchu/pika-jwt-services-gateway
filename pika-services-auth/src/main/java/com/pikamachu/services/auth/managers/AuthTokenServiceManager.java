package com.pikamachu.services.auth.managers;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.pikamachu.services.auth.beans.TokenBean;
import com.pikamachu.services.auth.beans.UserBean;
import com.pikamachu.services.common.annotations.Config;
import com.pikamachu.services.common.enums.ApplicationNameEnum;
import com.pikamachu.services.common.managers.BasicServiceManager;
import com.pikamachu.services.common.security.TokenManager;

/**
 * The Class LoginServiceManager.
 */
@ApplicationScoped
public class AuthTokenServiceManager extends BasicServiceManager {

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
	 * @param params the params
	 * @return true, if is valid user
	 * @throws Exception the exception
	 */
	public TokenBean generateAuthToken(UserBean params) throws Exception {
		
		List<String> applicationNames = params.getApplicationNames();
		if (applicationNames != null && !applicationNames.contains(ApplicationNameEnum.AUTH_SERVICES.name())) {
			applicationNames.add(ApplicationNameEnum.AUTH_SERVICES.name());
		}
		
		String token = tokenManager.generateAuthToken(params.getUsername(), applicationNames);
		
		TokenBean tokenBean = new TokenBean(token);
		
		return tokenBean;
	
	}

}
