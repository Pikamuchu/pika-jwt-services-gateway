package com.pikamachu.services.auth.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.pikamachu.services.common.enums.ApplicationNameEnum;

/**
 * The type User application.
 */
@Entity
@Table(name = "AUTH_USER_APP", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "user_id"}))
public class UserApplication implements Serializable {

	/** Default value included to remove warning. Remove or modify at will. */
	private static final long serialVersionUID = 1L;

	/**
	 * The Id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	/**
	 * The Name.
	 */
	@NotNull
	@Enumerated(EnumType.STRING)
	private ApplicationNameEnum name;

	/**
	 * The Code.
	 */
	@NotEmpty
	private String code;

	/**
	 * The Role.
	 */
	@NotEmpty
	private String role;

	/**
	 * The Is admin.
	 */
	@NotNull
	private Boolean isAdmin;

	/**
	 * The User.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

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
	public ApplicationNameEnum getName() {
		return name;
	}

	/**
	 * Sets name.
	 *
	 * @param name the name
	 */
	public void setName(ApplicationNameEnum name) {
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

	/**
	 * Gets user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets user.
	 *
	 * @param user the user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
}