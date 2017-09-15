package com.pikamachu.services.auth.params;

import javax.ws.rs.PathParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.pikamachu.services.common.params.BasicParams;

/**
 * The type Create user application params.
 */
@XmlRootElement(name = "createUserApplication")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateUserApplicationParams extends BasicParams {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Name.
	 */
	@NotEmpty
	@XmlElement(required=true)
	private String name;

	/**
	 * The Role.
	 */
	private String role;

	/**
	 * The Is admin.
	 */
	private Boolean isAdmin;

	/**
	 * The User id.
	 */
	@PathParam("userId")
	@NotEmpty
	@XmlElement(required=true)
	private String userId;

	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name.
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets role.
	 *
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets role.
	 *
	 * @param role the role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Gets is admin.
	 *
	 * @return the is admin
	 */
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	/**
	 * Sets is admin.
	 *
	 * @param isAdmin the is admin
	 */
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

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
