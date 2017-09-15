package com.pikamachu.services.auth.services;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.enunciate.jaxrs.TypeHint;

import com.pikamachu.services.auth.beans.UserApplicationBean;
import com.pikamachu.services.auth.beans.UserBean;
import com.pikamachu.services.auth.managers.UserServiceManager;
import com.pikamachu.services.auth.params.CreateUserApplicationParams;
import com.pikamachu.services.auth.params.CreateUserParams;
import com.pikamachu.services.auth.params.FindUserApplicationParams;
import com.pikamachu.services.auth.params.FindUserParams;
import com.pikamachu.services.auth.params.UpdateUserApplicationParams;
import com.pikamachu.services.auth.params.UpdateUserParams;
import com.pikamachu.services.common.beans.IdBean;
import com.pikamachu.services.common.params.IdParams;
import com.pikamachu.services.common.services.BasicService;

/**
 * The UserService is used for user management.
 */
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserService extends BasicService {

	/**
	 * The Log.
	 */
	@Inject
    private Logger log;

	/** The user service manager. */
	@Inject
	private UserServiceManager userServiceManager;

	/**
	 * Find user by username.
	 *
	 * @param username The username.
	 * @return The User.
	 * @throws Exception If any errors.
	 */
	@GET
	@TypeHint(UserBean.class)
	public Response findUser(@QueryParam("username") String username) throws Exception {
		
		FindUserParams params = new FindUserParams();
		
		params.setUsername(username);
		
		super.validate(params);

		List<UserBean> user = userServiceManager.findUser(params);

		return Response.ok(user).build();
		
	}

	/**
	 * Get user by id.
	 *
	 * @param id The id.
	 * @return The User.
	 * @throws Exception If any errors.
	 */
	@GET
	@Path("/{id}")
	@TypeHint(UserBean.class)
	public Response getUser(@PathParam("id") String id) throws Exception {
		
		IdParams params = new IdParams(id);
		
		super.validate(params);

		UserBean user = userServiceManager.getUserById(params);

		return Response.ok(user).build();
		
	}

	/**
	 * Create new user.
	 *
	 * @param params The user info.
	 * @return The user id.
	 * @throws Exception If any errors.
	 */
	@POST
	@TypeHint(IdBean.class)
	public Response createUser(CreateUserParams params) throws Exception {
		
		super.validate(params);

		IdBean bean = userServiceManager.createUser(params);

		return Response.status(201).entity(bean).location(new URI("/auth-bko/api/v1/user/" + bean.getId())).build();
		
	}

	/**
	 * Update existing user.
	 *
	 * @param params The user info.
	 * @return Nothing. response
	 * @throws Exception If any errors.
	 */
	@PUT
	@Path("/{id}")
	public Response updateUser(UpdateUserParams params) throws Exception {
		
		super.validate(params);

		userServiceManager.updateUser(params);

		return Response.ok().build();
		
	}

	/**
	 * Delete user by id.
	 *
	 * @param id The id.
	 * @return Nothing. response
	 * @throws Exception If any errors.
	 */
	@DELETE
	@Path("/{id}")
	public Response deleteUser(@PathParam("id") String id) throws Exception {
		
		IdParams params = new IdParams(id);
		
		super.validate(params);

		userServiceManager.removeUser(params);

		return Response.ok().build();
		
	}

	/**
	 * Find user application.
	 *
	 * @param userId the user id
	 * @return the response
	 * @throws Exception the exception
	 */
	@GET
	@Path("/{userId}/application")
	@TypeHint(UserApplicationBean.class)
	public Response findUserApplication(@PathParam("userId") String userId) throws Exception {
		
		FindUserApplicationParams params = new FindUserApplicationParams();
		
		params.setUserId(userId);
		
		super.validate(params);

		List<UserApplicationBean> user = userServiceManager.findUserApplication(params);

		return Response.ok(user).build();
		
	}

	/**
	 * Gets user application.
	 *
	 * @param id the id
	 * @return the user application
	 * @throws Exception the exception
	 */
	@GET
	@Path("/{userId}/application/{id}")
	@TypeHint(UserApplicationBean.class)
	public Response getUserApplication(@PathParam("id") String id) throws Exception {
		
		IdParams params = new IdParams(id);
		
		super.validate(params);

		UserApplicationBean user = userServiceManager.getUserApplicationById(params);

		return Response.ok(user).build();
		
	}

	/**
	 * Create user application.
	 *
	 * @param params the params
	 * @return the response
	 * @throws Exception the exception
	 */
	@POST
	@Path("/{userId}/application")
	@TypeHint(IdBean.class)
	public Response createUserApplication(CreateUserApplicationParams params) throws Exception {
		
		super.validate(params);

		IdBean bean = userServiceManager.createUserApplication(params);

		return Response.status(201).entity(bean).location(new URI("/auth-bko/api/v1/user/" + params.getUserId() + "/application/" + bean.getId())).build();
		
	}

	/**
	 * Update user application.
	 *
	 * @param params the params
	 * @return the response
	 * @throws Exception the exception
	 */
	@PUT
	@Path("/{userId}/application/{id}")
	public Response updateUserApplication(UpdateUserApplicationParams params) throws Exception {
		
		super.validate(params);

		userServiceManager.updateUserApplication(params);

		return Response.ok().build();
		
	}

	/**
	 * Delete user application.
	 *
	 * @param id the id
	 * @return the response
	 * @throws Exception the exception
	 */
	@DELETE
	@Path("/{userId}/application/{id}")
	public Response deleteUserApplication(@PathParam("id") String id) throws Exception {
		
		IdParams params = new IdParams(id);
		
		super.validate(params);

		userServiceManager.removeUserApplication(params);

		return Response.ok().build();
		
	}
	
}
