package com.pikamachu.services.common.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The type Id bean.
 */
@XmlRootElement(name = "id")
@XmlAccessorType(XmlAccessType.FIELD)
public class IdBean extends BasicBean {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Id.
	 */
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
	 * Instantiates a new Id bean.
	 */
	public IdBean() {
		super();
	}

	/**
	 * Instantiates a new Id bean.
	 *
	 * @param id the id
	 */
	public IdBean(String id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "IdBean [id=" + id + "]";
	}
	
}
