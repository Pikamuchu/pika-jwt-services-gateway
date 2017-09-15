package com.pikamachu.services.common.exceptions;

import javax.ws.rs.core.Response;

/**
 * The Class ServiceException.
 */
public class ServiceException extends RuntimeException {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Error code.
	 */
	protected int errorCode = -1;

	/**
	 * The Response.
	 */
	protected Response response;

	/**
	 * Instantiates a new Service exception.
	 *
	 * @param s             the s
	 * @param response             the response
	 */
	public ServiceException(String s, Response response) {
		super(s);
		this.response = response;
	}

	/**
	 * Instantiates a new Service exception.
	 *
	 * @param s             the s
	 * @param throwable             the throwable
	 * @param response             the response
	 */
	public ServiceException(String s, Throwable throwable, Response response) {
		super(s, throwable);
		this.response = response;
	}

	/**
	 * Instantiates a new Service exception.
	 *
	 * @param throwable             the throwable
	 * @param response             the response
	 */
	public ServiceException(Throwable throwable, Response response) {
		super(throwable);
		this.response = response;
	}

	/**
	 * Instantiates a new Service exception.
	 *
	 * @param s             the s
	 * @param throwable             the throwable
	 */
	public ServiceException(String s, Throwable throwable) {
		super(s, throwable);
		this.errorCode = 500;
	}

	/**
	 * Instantiates a new Service exception.
	 *
	 * @param throwable             the throwable
	 */
	public ServiceException(Throwable throwable) {
		super(throwable);
		this.errorCode = 500;
	}

	/**
	 * Instantiates a new Service exception.
	 *
	 * @param s             the s
	 */
	public ServiceException(String s) {
		super(s);
		this.errorCode = 500;
	}

	/**
	 * Instantiates a new Service exception.
	 *
	 * @param errorCode             the error code
	 */
	public ServiceException(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Instantiates a new Service exception.
	 *
	 * @param s             the s
	 * @param errorCode             the error code
	 */
	public ServiceException(String s, int errorCode) {
		super(s);
		this.errorCode = errorCode;
	}

	/**
	 * Instantiates a new Service exception.
	 *
	 * @param s             the s
	 * @param throwable             the throwable
	 * @param errorCode             the error code
	 */
	public ServiceException(String s, Throwable throwable, int errorCode) {
		super(s, throwable);
		this.errorCode = errorCode;
	}

	/**
	 * Instantiates a new Service exception.
	 *
	 * @param throwable             the throwable
	 * @param errorCode             the error code
	 */
	public ServiceException(Throwable throwable, int errorCode) {
		super(throwable);
		this.errorCode = errorCode;
	}

	/**
	 * Gets error code.
	 *
	 * @return the error code
	 */
	public int getErrorCode() {
		return this.errorCode;
	}

	/**
	 * Sets error code.
	 *
	 * @param errorCode             the error code
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets response.
	 *
	 * @return the response
	 */
	public Response getResponse() {
		return this.response;
	}

	/**
	 * Sets response.
	 *
	 * @param response             the response
	 */
	public void setResponse(Response response) {
		this.response = response;
	}

}