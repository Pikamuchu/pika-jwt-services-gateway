package com.pikamachu.services.auth.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.pikamachu.services.common.beans.BasicBean;

/**
 * The type User application bean.
 */
@XmlRootElement(name = "userApplication")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserApplicationBean extends BasicBean {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Id.
	 */
	private String id;

	/**
	 * The Name.
	 */
	private String name;

	/**
	 * The Code.
	 */
	private String code;

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
	 * Gets code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets code.
	 *
	 * @param code the code
	 */
	public void setCode(String code) {
		this.code = code;
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
