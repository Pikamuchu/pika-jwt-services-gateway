package com.pikamachu.services.gateway.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class TestBean.
 */
@XmlRootElement(name = "test")
@XmlAccessorType(XmlAccessType.FIELD)
public class TestBean implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The Result.
	 */
	private String result;

	/**
	 * Instantiates a new Test bean.
	 */
	public TestBean() {
		super();
	}

	/**
	 * Instantiates a new Test bean.
	 *
	 * @param result the result
	 */
	public TestBean(String result) {
		super();
		this.result = result;
	}

	/**
	 * Gets result.
	 *
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Sets result.
	 *
	 * @param result the result
	 */
	public void setResult(String result) {
		this.result = result;
	}

}
