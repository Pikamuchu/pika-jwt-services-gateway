package com.pikamachu.services.auth.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.pikamachu.services.common.beans.BasicBean;

/**
 * The type Token bean.
 */
@XmlRootElement(name = "token")
@XmlAccessorType(XmlAccessType.FIELD)
public class TokenBean extends BasicBean {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Token.
	 */
	private String token;

	/**
	 * Instantiates a new Token bean.
	 */
	public TokenBean() {
		super();
	}

	/**
	 * Instantiates a new Token bean.
	 *
	 * @param token the token
	 */
	public TokenBean(String token) {
		super();
		this.token = token;
	}

	/**
	 * Gets token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets token.
	 *
	 * @param token the token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "TokenBean [token=" + token + "]";
	}
	
}
