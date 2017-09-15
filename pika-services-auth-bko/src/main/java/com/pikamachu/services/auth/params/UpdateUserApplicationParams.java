package com.pikamachu.services.auth.params;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.pikamachu.services.common.params.BasicParams;

/**
 * The type Update user application params.
 */
@XmlRootElement(name = "updateUserApplication")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateUserApplicationParams extends BasicParams {

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
	 * The Role.
	 */
	private String role;

	/**
	 * The Is admin.
	 */
	private Boolean isAdmin;

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

}
