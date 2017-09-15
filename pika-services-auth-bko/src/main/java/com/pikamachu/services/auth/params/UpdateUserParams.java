package com.pikamachu.services.auth.params;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.pikamachu.services.common.params.BasicParams;

/**
 * The type Update user params.
 */
@XmlRootElement(name = "updateUser")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateUserParams extends BasicParams {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Id.
	 */
	@NotEmpty
	@XmlElement(required=true)
	private String id;

	/**
	 * The Username.
	 */
	private String username;

	/**
	 * The Displayname.
	 */
	private String displayname;

	/**
	 * The Password.
	 */
	private String password;

	/**
	 * The Type.
	 */
	private String type;

	/**
	 * The Description.
	 */
	private String description;

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(String id) {
		this.id = id;
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
	 * Gets displayname.
	 *
	 * @return the displayname
	 */
	public String getDisplayname() {
		return displayname;
	}

	/**
	 * Sets displayname.
	 *
	 * @param displayname the displayname
	 */
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
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

	/**
	 * Gets type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets type.
	 *
	 * @param type the type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets description.
	 *
	 * @param description the description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
