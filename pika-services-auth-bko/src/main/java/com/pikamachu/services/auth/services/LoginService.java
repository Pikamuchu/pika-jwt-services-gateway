package com.pikamachu.services.auth.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.enunciate.jaxrs.TypeHint;

import com.pikamachu.services.auth.beans.TokenBean;
import com.pikamachu.services.auth.beans.UserBean;
import com.pikamachu.services.auth.managers.LoginServiceManager;
import com.pikamachu.services.auth.managers.LoginTokenServiceManager;
import com.pikamachu.services.auth.params.LoginParams;
import com.pikamachu.services.common.exceptions.InternalServerErrorException;
import com.pikamachu.services.common.exceptions.UnauthorizedException;
import com.pikamachu.services.common.params.IdParams;
import com.pikamachu.services.common.services.BasicService;

/**
 * The LoginService is used for perform login.
 */
@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class LoginService extends BasicService {

	/** The login service manager. */
	@Inject
	private LoginServiceManager loginServiceManager;

	/**
	 * The Token service manager.
	 */
	@Inject
	private LoginTokenServiceManager tokenServiceManager;

	/**
	 * Authentication Service using Basic Authorization header.
	 * Returns JWE token to use as Bearer Authorization header.
	 *
	 * @param authHeader Authorization.
	 * @return The Token.
	 * @throws Exception If any errors.
	 */
	@GET
	@TypeHint(TokenBean.class)
	public Response basicAuthentication(@HeaderParam("Authorization") String authHeader) throws Exception {
		
		LoginParams params = loginServiceManager.basicAuthenticate(new IdParams(authHeader));
		
		return doLogin(params);
	}

	/**
	 * Authentication Service using Post data.
	 * Returns JWE token to use as Bearer Authorization header.
	 *
	 * @param params User credentials.
	 * @return The Token.
	 * @throws Exception If any errors.
	 */
	@POST
	@TypeHint(TokenBean.class)
	public Response doLogin(LoginParams params) throws Exception {
		
		super.validate(params);

		UserBean user = loginServiceManager.doUserLogin(params);
		if (user == null) {
			// user not found in the Active Directory or cannot generate token
			throw new UnauthorizedException();
		}
		
		TokenBean bean = tokenServiceManager.generateLoginToken(params);
		if (bean == null) {
			throw new InternalServerErrorException("Token is null!");
		}
		
		// user not found in the Active Directory or cannot generate token
		return Response.ok(bean).header(AUTHORIZATION_HEADER, BEARER_SCHEMA + " " + bean.getToken()).build();


	}
}
