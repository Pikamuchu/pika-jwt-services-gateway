package com.pikamachu.services.auth.clients;

/**
 * The type User ldap.
 */
public class UserLdap {

	/**
	 * The Username.
	 */
	private String username;
	/**
	 * The Display name.
	 */
	private String displayName;

	/**
	 * Instantiates a new User ldap.
	 */
	public UserLdap() {
		super();
	}

	/**
	 * Instantiates a new User ldap.
	 *
	 * @param username the username
	 * @param displayName the display name
	 */
	public UserLdap(String username, String displayName) {
		this.username = username;
		this.displayName = displayName;
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

	/**
	 * Gets display name.
	 *
	 * @return the display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets display name.
	 *
	 * @param displayName the display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return "UserLdap [username=" + username + ", displayName=" + displayName + "]";
	}
}
