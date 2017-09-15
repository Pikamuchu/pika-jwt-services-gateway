package com.pikamachu.services.auth.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.pikamachu.services.common.beans.BasicBean;

/**
 * The type User bean.
 */
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserBean extends BasicBean {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Id.
	 */
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
	 * The Type.
	 */
	private String type;

	/**
	 * The Description.
	 */
	private String description;

	/**
	 * The Application names.
	 */
	private List<String> applicationNames;

	/**
	 * Instantiates a new User bean.
	 */
	public UserBean() {
		super();
	}

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

	/**
	 * Gets application names.
	 *
	 * @return the application names
	 */
	public List<String> getApplicationNames() {
		return applicationNames;
	}

	/**
	 * Sets application names.
	 *
	 * @param applicationNames the application names
	 */
	public void setApplicationNames(List<String> applicationNames) {
		this.applicationNames = applicationNames;
	}
	
}
