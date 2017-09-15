package com.pikamachu.services.auth.repositories;

import javax.ejb.Stateless;

import com.pikamachu.services.auth.models.User;
import com.pikamachu.services.common.repository.GenericRepository;

/**
 * The type User repository.
 */
@Stateless
public class UserRepository extends GenericRepository<User> {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

}
