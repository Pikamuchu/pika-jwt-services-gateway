package com.pikamachu.services.auth.repositories;

import javax.ejb.Stateless;

import com.pikamachu.services.auth.models.UserApplication;
import com.pikamachu.services.common.repository.GenericRepository;

/**
 * The type User application repository.
 */
@Stateless
public class UserApplicationRepository extends GenericRepository<UserApplication> {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

}
