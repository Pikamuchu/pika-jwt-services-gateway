package com.pikamachu.services.auth.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.pikamachu.services.auth.enums.UserTypeEnum;

/**
 * The type User.
 */
@Entity
@Table(name = "AUTH_USER", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User implements Serializable {

	/** Default value included to remove warning. Remove or modify at will. */
	private static final long serialVersionUID = 1L;

	/**
	 * The Id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	/**
	 * The Username.
	 */
	@NotEmpty
	private String username;

	/**
	 * The Displayname.
	 */
	@NotEmpty
	private String displayname;

	/**
	 * The Password.
	 */
	private String password;

	/**
	 * The Type.
	 */
	@NotNull
	@Enumerated(EnumType.STRING)
	private UserTypeEnum type;

	/**
	 * The Description.
	 */
	private String description;

	/**
	 * The Applications.
	 */
	@OneToMany(mappedBy = "user")
	private List<UserApplication> applications;

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
	public UserTypeEnum getType() {
		return type;
	}

	/**
	 * Sets type.
	 *
	 * @param type the type
	 */
	public void setType(UserTypeEnum type) {
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
	 * Gets applications.
	 *
	 * @return the applications
	 */
	public List<UserApplication> getApplications() {
		return applications;
	}

	/**
	 * Sets applications.
	 *
	 * @param applications the applications
	 */
	public void setApplications(List<UserApplication> applications) {
		this.applications = applications;
	}

}