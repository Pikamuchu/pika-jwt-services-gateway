package com.pikamachu.services.auth.params;

import com.pikamachu.services.common.params.BasicParams;

/**
 * The type Find user params.
 */
public class FindUserParams extends BasicParams {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Username.
	 */
	private String username;

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
