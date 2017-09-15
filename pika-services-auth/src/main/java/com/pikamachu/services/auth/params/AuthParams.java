package com.pikamachu.services.auth.params;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.pikamachu.services.common.params.BasicParams;

/**
 * The type Auth params.
 */
@XmlRootElement(name = "auth")
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthParams extends BasicParams {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The username.
	 */
	@NotEmpty
	@XmlElement(required=true)
	private String username;

	/**
	 * The password.
	 */
	@NotEmpty
	@XmlElement(required=true)
	private String password;

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

	/**
	 * Gets password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets password.
	 *
	 * @param password the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AuthParams [username=" + username + ", password=" + password + "]";
	}

}
