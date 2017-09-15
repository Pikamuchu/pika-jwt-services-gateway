package com.pikamachu.services.common.security;

import javax.inject.Singleton;

import org.jose4j.jwt.JwtClaims;

/**
 * The Class Token Context.
 */
@Singleton
public class TokenContext {

	/**
	 * The Context.
	 */
	private ThreadLocal<JwtClaims> context = new ThreadLocal<>();

	/**
	 * Initialize void.
	 */
	public void initialize() {
		context.set(new JwtClaims());
	}

	/**
	 * Initialize void.
	 *
	 * @param claims the claims
	 */
	public void initialize(JwtClaims claims) {
		context.set(claims);
	}

	/**
	 * Cleanup void.
	 */
	public void cleanup() {
		context.set(null);
	}

	/**
	 * Gets current.
	 *
	 * @return the current
	 */
	public JwtClaims getCurrent() {
		return context.get();
	}

}
