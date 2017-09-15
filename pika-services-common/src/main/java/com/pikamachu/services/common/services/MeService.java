package com.pikamachu.services.common.services;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.enunciate.jaxrs.TypeHint;
import org.jose4j.jwt.JwtClaims;

import com.pikamachu.services.common.beans.MeBean;
import com.pikamachu.services.common.exceptions.UnauthorizedException;

/**
 * The MeService is used for checking Bearer tokens.
 */
@Path("/me")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class MeService extends BasicService {

	/**
	 * Checks token claims.
	 *
	 * @return the me claims
	 * @throws Exception the exception
	 */
	@GET
	@TypeHint(MeBean.class)
	public Response getMeClaims() throws Exception {

		JwtClaims claims = tokenContext.getCurrent();
		if (claims == null) {
			throw new UnauthorizedException();
		}

		return Response.ok(new MeBean(claims.getSubject())).build();

	}
}
