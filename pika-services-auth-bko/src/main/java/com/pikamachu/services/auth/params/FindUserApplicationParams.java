package com.pikamachu.services.auth.params;

import com.pikamachu.services.common.params.BasicParams;

/**
 * The type Find user application params.
 */
public class FindUserApplicationParams extends BasicParams {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The User id.
	 */
	private String userId;

	/**
	 * Gets user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets user id.
	 *
	 * @param userId the user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
