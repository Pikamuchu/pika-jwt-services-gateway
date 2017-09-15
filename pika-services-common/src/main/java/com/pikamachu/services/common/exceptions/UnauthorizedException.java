package com.pikamachu.services.common.exceptions;

import javax.ws.rs.core.Response;

/**
 * Thrown when HTTP Unauthorized (401) is encountered.
 */
public class UnauthorizedException extends ServiceException {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new Unauthorized exception.
	 */
	public UnauthorizedException() {
		super(401);
	}

	/**
	 * Instantiates a new Unauthorized exception.
	 *
	 * @param s the s
	 */
	public UnauthorizedException(String s) {
		super(s, 401);
	}

	/**
	 * Instantiates a new Unauthorized exception.
	 *
	 * @param s the s
	 * @param response the response
	 */
	public UnauthorizedException(String s, Response response) {
		super(s, response);
	}

	/**
	 * Instantiates a new Unauthorized exception.
	 *
	 * @param s the s
	 * @param throwable the throwable
	 * @param response the response
	 */
	public UnauthorizedException(String s, Throwable throwable, Response response) {
		super(s, throwable, response);
	}

	/**
	 * Instantiates a new Unauthorized exception.
	 *
	 * @param s the s
	 * @param throwable the throwable
	 */
	public UnauthorizedException(String s, Throwable throwable) {
		super(s, throwable, 401);
	}

	/**
	 * Instantiates a new Unauthorized exception.
	 *
	 * @param throwable the throwable
	 */
	public UnauthorizedException(Throwable throwable) {
		super(throwable, 401);
	}

	/**
	 * Instantiates a new Unauthorized exception.
	 *
	 * @param throwable the throwable
	 * @param response the response
	 */
	public UnauthorizedException(Throwable throwable, Response response) {
		super(throwable, response);
	}
}
