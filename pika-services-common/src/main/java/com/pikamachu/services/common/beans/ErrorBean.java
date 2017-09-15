package com.pikamachu.services.common.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The type Error bean.
 */
@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorBean extends BasicBean {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Error.
	 */
	private String error;

	/**
	 * Instantiates a new Error bean.
	 */
	public ErrorBean() {
		super();
	}

	/**
	 * Instantiates a new Error bean.
	 *
	 * @param error the error
	 */
	public ErrorBean(String error) {
		super();
		this.error = error;
	}

	/**
	 * Gets error.
	 *
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * Sets error.
	 *
	 * @param error the error
	 */
	public void setError(String error) {
		this.error = error;
	}

}
