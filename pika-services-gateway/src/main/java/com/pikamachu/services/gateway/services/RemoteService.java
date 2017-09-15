package com.pikamachu.services.gateway.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.pikamachu.services.common.services.BasicService;
import com.pikamachu.services.gateway.clients.RemoteClient;

/**
 * Tiers Testing service.
 */
@Path("/remote")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class RemoteService extends BasicService {

	/**
	 * The Remote tiers client.
	 */
	@Inject
	private RemoteClient remoteClient;

	/**
	 * Gets tiers test.
	 *
	 * @return the test
	 * @throws Exception the exception
	 */
	@GET
	public Response getTiers() throws Exception {
		
		String bean = remoteClient.getRemoteService();
		
		// user not found in the Active Directory or cannot generate token
		return Response.ok(bean).build();

	}
}
