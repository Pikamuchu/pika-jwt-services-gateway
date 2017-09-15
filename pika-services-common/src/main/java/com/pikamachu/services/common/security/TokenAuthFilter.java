package com.pikamachu.services.common.security;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;

import com.pikamachu.services.common.annotations.Config;

/**
 * The type Token auth filter.
 */
public abstract class TokenAuthFilter implements Filter {

	/**
	 * The constant BEARER_SCHEMA.
	 */
	protected static final String BEARER_SCHEMA = "Bearer";

	/**
	 * The constant AUTHORIZATION_HEADER.
	 */
	protected static final String AUTHORIZATION_HEADER = "Authorization";

	/**
	 * The constant AUTH_ERROR_MSG.
	 */
	protected static final String AUTH_ERROR_MSG = "Please make sure your request has an Authorization header with format: Bearer [token]", /**
	 * The EXPIRE _ eRROR _ mSG.
	 */
	EXPIRE_ERROR_MSG = "Token has expired", /**
	 * The JWT _ eRROR _ mSG.
	 */
	JWT_ERROR_MSG = "Unable to parse JWT", /**
	 * The JWT _ iNVALID _ mSG.
	 */
	JWT_INVALID_MSG = "Invalid JWT token", /**
	 * The JWT _ iNVALID _ iSSUER.
	 */
	JWT_INVALID_ISSUER = "Invalid JWT claim iss", /**
	 * The JWT _ iNVALID _ aUDIENCE.
	 */
	JWT_INVALID_AUDIENCE = "Invalid JWT claim aud";

	/**
	 * The Token manager.
	 */
	@Inject
	protected TokenManager tokenManager;

	/**
	 * The Token context.
	 */
	@Inject
	protected TokenContext tokenContext;

	/**
	 * The Application audience.
	 */
	@Inject
	@Config
	protected String applicationAudience;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String authHeader = httpRequest.getHeader(AUTHORIZATION_HEADER);
		
		if (authHeader == null || !authHeader.startsWith(BEARER_SCHEMA)) {
			sendError(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, AUTH_ERROR_MSG);
			return;
		}

		JwtClaims claims = null;
		try {
			String serializedJwe = authHeader.split(" ")[1];
			claims = tokenManager.decodeAuthToken(serializedJwe);
		} catch (Exception e) {
			sendError(httpResponse, HttpServletResponse.SC_BAD_REQUEST, JWT_INVALID_MSG);
			return;
		}

		if (!checkExpiration(claims)) {
			sendError(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, EXPIRE_ERROR_MSG);
			return;			
		}
		
		if (!checkIssuer(claims)) {
			sendError(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, JWT_INVALID_ISSUER);
			return;			
		}
		
		if (!checkAudience(claims)) {
			sendError(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, JWT_INVALID_AUDIENCE);
			return;			
		}

		tokenContext.initialize(claims);

		try {
			chain.doFilter(request, response);
		} finally {
			tokenContext.cleanup();
		}

	}

	/**
	 * Check expiration.
	 *
	 * @param claims the claims
	 * @return the boolean
	 */
	protected boolean checkExpiration(JwtClaims claims) {
		// ensure that the token is not expired
		Calendar expireDate = null;
		try {
			expireDate = Calendar.getInstance();
			expireDate.setTimeInMillis(claims.getExpirationTime().getValueInMillis());
		} catch (Exception e) {
			return false;
		}

		if (expireDate == null || expireDate.before(Calendar.getInstance())) {
			return false;
		}
		
		return true;
	}

	/**
	 * Check issuer.
	 *
	 * @param claims the claims
	 * @return the boolean
	 */
	protected boolean checkIssuer(JwtClaims claims) {
		String issuer = null;
		try {
			issuer = claims.getIssuer();
		} catch (Exception e) {
			return false;
		}
		
		if (issuer == null || issuer.isEmpty() || !issuer.contains(tokenManager.getAuthIssuer())) {
			return false;
		}
		
		return true;
	}

	/**
	 * Check audience.
	 *
	 * @param claims the claims
	 * @return the boolean
	 */
	protected boolean checkAudience(JwtClaims claims) {
		List<String> audiences = null;
		try {
			audiences = claims.getAudience();
		} catch (MalformedClaimException e) {
			return false;
		}
	
		if (audiences == null || audiences.isEmpty() || !audiences.contains(applicationAudience)) {
			return false;
		}
		
		return true;
	}

	/**
	 * Check roles.
	 *
	 * @param claims the claims
	 * @return the boolean
	 */
	protected boolean checkRoles(JwtClaims claims) {
		return true;
	}

	/**
	 * Send error.
	 *
	 * @param httpResponse the http response
	 * @param statusCode the status code
	 * @param errorMessage the error message
	 * @throws IOException the iO exception
	 * @throws IOException the iO exception
	 */
	protected void sendError(HttpServletResponse httpResponse, int statusCode, String errorMessage) throws IOException, ServletException {
		httpResponse.setContentType(MediaType.APPLICATION_JSON + ";charset=UTF-8");
		httpResponse.setStatus(statusCode);
		httpResponse.getWriter().print("{ \"error\": \"" + errorMessage +"\" }");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Nothing to do
		
	}

	@Override
	public void destroy() {
		// Nothing to do	
	}

}
