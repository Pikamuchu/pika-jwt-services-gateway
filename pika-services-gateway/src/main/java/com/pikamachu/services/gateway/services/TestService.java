package com.pikamachu.services.gateway.services;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.enunciate.jaxrs.TypeHint;

import com.pikamachu.services.common.services.BasicService;
import com.pikamachu.services.gateway.beans.TestBean;

/**
 * Testing service.
 */
@Path("/test")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class TestService extends BasicService {

	/**
	 * Gets test.
	 *
	 * @return the test
	 * @throws Exception the exception
	 */
	@GET
	@TypeHint(TestBean.class)
	public Response getTest() throws Exception {
		
		// user not found in the Active Directory or cannot generate token
		return Response.ok(new TestBean("OK!")).build();

	}
}
