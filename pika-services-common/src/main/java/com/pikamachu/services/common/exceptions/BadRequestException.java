package com.pikamachu.services.common.exceptions;

import javax.ws.rs.core.Response;

/**
 * Thrown when HTTP Bad Request (400) is encountered.
 */
public class BadRequestException extends ServiceException {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new Bad request exception.
	 *
	 * @param s the s
	 */
	public BadRequestException(String s) {
		super(s, 400);
	}

	/**
	 * Instantiates a new Bad request exception.
	 *
	 * @param s the s
	 * @param response the response
	 */
	public BadRequestException(String s, Response response) {
		super(s, response);
	}

	/**
	 * Instantiates a new Bad request exception.
	 *
	 * @param s the s
	 * @param throwable the throwable
	 * @param response the response
	 */
	public BadRequestException(String s, Throwable throwable, Response response) {
		super(s, throwable, response);
	}

	/**
	 * Instantiates a new Bad request exception.
	 *
	 * @param s the s
	 * @param throwable the throwable
	 */
	public BadRequestException(String s, Throwable throwable) {
		super(s, throwable, 400);
	}

	/**
	 * Instantiates a new Bad request exception.
	 *
	 * @param throwable the throwable
	 */
	public BadRequestException(Throwable throwable) {
		super(throwable, 400);
	}

	/**
	 * Instantiates a new Bad request exception.
	 *
	 * @param throwable the throwable
	 * @param response the response
	 */
	public BadRequestException(Throwable throwable, Response response) {
		super(throwable, response);
	}

}
