package com.pikamachu.services.common.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The type Me bean.
 */
@XmlRootElement(name = "me")
@XmlAccessorType(XmlAccessType.FIELD)
public class MeBean extends BasicBean {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Username.
	 */
	private String username;

	/**
	 * Instantiates a new Me bean.
	 */
	public MeBean() {
		super();
	}

	/**
	 * Instantiates a new Me bean.
	 *
	 * @param username the username
	 */
	public MeBean(String username) {
		super();
		this.username = username;
	}

	/**
	 * Gets username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets username.
	 *
	 * @param username the username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
}
