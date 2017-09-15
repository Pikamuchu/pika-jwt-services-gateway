package com.pikamachu.services.common.utils;

import javax.inject.Singleton;

import org.mindrot.jbcrypt.BCrypt;

/**
 * The Class Password Utils.
 */
@Singleton
public final class PasswordUtils {

	/**
	 * Hash password.
	 *
	 * @param plaintext the plaintext
	 * @return the string
	 */
	public String hashPassword(String plaintext) {
		return BCrypt.hashpw(plaintext, BCrypt.gensalt());
	}

	/**
	 * Check password.
	 *
	 * @param plaintext the plaintext
	 * @param hashed the hashed
	 * @return the boolean
	 */
	public boolean checkPassword(String plaintext, String hashed) {
		return BCrypt.checkpw(plaintext, hashed);
	}

}
