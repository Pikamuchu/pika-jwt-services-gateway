package com.pikamachu.services.common.exceptions;

import javax.ws.rs.core.Response;

/**
 * Thrown when HTTP Internal Service Error (500) is encountered.
 */
public class InternalServerErrorException extends ServiceException {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new Internal server error exception.
	 *
	 * @param s the s
	 */
	public InternalServerErrorException(String s) {
		super(s, 500);
	}

	/**
	 * Instantiates a new Internal server error exception.
	 *
	 * @param s the s
	 * @param response the response
	 */
	public InternalServerErrorException(String s, Response response) {
		super(s, response);
	}

	/**
	 * Instantiates a new Internal server error exception.
	 *
	 * @param s the s
	 * @param throwable the throwable
	 * @param response the response
	 */
	public InternalServerErrorException(String s, Throwable throwable, Response response) {
		super(s, throwable, response);
	}

	/**
	 * Instantiates a new Internal server error exception.
	 *
	 * @param s the s
	 * @param throwable the throwable
	 */
	public InternalServerErrorException(String s, Throwable throwable) {
		super(s, throwable, 500);
	}

	/**
	 * Instantiates a new Internal server error exception.
	 *
	 * @param throwable the throwable
	 */
	public InternalServerErrorException(Throwable throwable) {
		super(throwable, 500);
	}

	/**
	 * Instantiates a new Internal server error exception.
	 *
	 * @param throwable the throwable
	 * @param response the response
	 */
	public InternalServerErrorException(Throwable throwable, Response response) {
		super(throwable, response);
	}

}