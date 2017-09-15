package com.pikamachu.services.common.params;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * The type Id params.
 */
@XmlRootElement(name = "id")
@XmlAccessorType(XmlAccessType.FIELD)
public class IdParams extends BasicParams {

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
	 * Instantiates a new Id params.
	 */
	public IdParams() {
		super();
	}

	/**
	 * Instantiates a new Id params.
	 *
	 * @param id the id
	 */
	public IdParams(String id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "IdParams [id=" + id + "]";
	}

}
