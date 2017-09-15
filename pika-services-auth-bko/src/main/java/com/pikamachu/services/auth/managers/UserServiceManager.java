package com.pikamachu.services.auth.managers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;

import com.pikamachu.services.auth.beans.UserApplicationBean;
import com.pikamachu.services.auth.beans.UserBean;
import com.pikamachu.services.auth.enums.UserTypeEnum;
import com.pikamachu.services.auth.models.User;
import com.pikamachu.services.auth.models.UserApplication;
import com.pikamachu.services.auth.params.CreateUserApplicationParams;
import com.pikamachu.services.auth.params.CreateUserParams;
import com.pikamachu.services.auth.params.FindUserApplicationParams;
import com.pikamachu.services.auth.params.FindUserParams;
import com.pikamachu.services.auth.params.UpdateUserApplicationParams;
import com.pikamachu.services.auth.params.UpdateUserParams;
import com.pikamachu.services.auth.repositories.UserApplicationRepository;
import com.pikamachu.services.auth.repositories.UserRepository;
import com.pikamachu.services.common.beans.IdBean;
import com.pikamachu.services.common.enums.ApplicationNameEnum;
import com.pikamachu.services.common.exceptions.BadRequestException;
import com.pikamachu.services.common.managers.BasicServiceManager;
import com.pikamachu.services.common.params.IdParams;
import com.pikamachu.services.common.utils.PasswordUtils;

/**
 * The Class UserServiceManager.
 */
@Stateless
public class UserServiceManager extends BasicServiceManager {

	/**
	 * The User repository.
	 */
	@Inject
	private UserRepository userRepository;

	/**
	 * The User application repository.
	 */
	@Inject
	private UserApplicationRepository userApplicationRepository;

	/**
	 * The Password utils.
	 */
	@Inject
	private PasswordUtils passwordUtils;

	/**
	 * Gets user by id.
	 *
	 * @param params the params
	 * @return the user by id
	 * @throws Exception the exception
	 */
	public UserBean getUserById(IdParams params) throws Exception {
		User user = userRepository.findById(User.class, params.getId());

		UserBean bean = new UserBean();
		BeanUtils.copyProperties(bean, user);

		return bean;
	}

	/**
	 * Find user.
	 *
	 * @param params the params
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<UserBean> findUser(FindUserParams params) throws Exception {
		
		List<User> users = null;

		boolean isFindAll = true;
		
		if (params.getUsername() != null) {
			users = userRepository.findByProperty(User.class, "username", params.getUsername());
			isFindAll = false;
		}
		
		if (isFindAll) {
			users = userRepository.findAll(User.class);
		}

		List<UserBean> listBean = new ArrayList<UserBean>();

		if (users == null || users.isEmpty()) {
			return null;
		}

		for (User user : users) {
			UserBean bean = new UserBean();
			BeanUtils.copyProperties(bean, user);
			listBean.add(bean);
		}

		return listBean;
	}

	/**
	 * Create user.
	 *
	 * @param params the params
	 * @return the id bean
	 * @throws Exception the exception
	 */
	public IdBean createUser(CreateUserParams params) throws Exception {
		User user = new User();
		
		// Mapping
		user.setUsername(params.getUsername());
		user.setDisplayname(params.getDisplayname());
		user.setDescription(params.getDescription());
		
		userTypeMapping(user, params.getType());

		userPasswordMapping(user, params.getPassword());
		
		// Persist user
		userRepository.save(user);
		
		return new IdBean(user.getId());
	}

	/**
	 * Update user.
	 *
	 * @param params the params
	 * @throws Exception the exception
	 */
	public void updateUser(UpdateUserParams params) throws Exception {
		// Get User by Id
		User user = userRepository.findById(User.class, params.getId());

		// Mapping
		String value = params.getUsername();
		if (value != null && !value.isEmpty()) {
			user.setUsername(value);
		}
		value = params.getDisplayname();
		if (value != null && !value.isEmpty()) {
			user.setDisplayname(value);
		}
		value = params.getDescription();
		if (value != null && !value.isEmpty()) {
			user.setDescription(value);
		}
		
		value = params.getType();
		if (value != null && !value.isEmpty()) {
			userTypeMapping(user, value);
		}
		
		value = params.getPassword();
		if (value != null && !value.isEmpty()) {
			userPasswordMapping(user, value);
		}

		// Persist user
		userRepository.save(user);
	}

	/**
	 * Remove user.
	 *
	 * @param params the params
	 * @throws Exception the exception
	 */
	public void removeUser(IdParams params) throws Exception {
		User user = userRepository.findById(User.class, params.getId());
		userRepository.delete(user);
	}

	/**
	 * User type mapping.
	 *
	 * @param user the user
	 * @param type the type
	 */
	private void userTypeMapping(User user, String type) {
		if (type == null || type.equalsIgnoreCase(UserTypeEnum.LOCAL.name())) {
			user.setType(UserTypeEnum.LOCAL);
		} else if (type.equalsIgnoreCase(UserTypeEnum.LDAP.name())) {
			user.setType(UserTypeEnum.LDAP);
		} else {
			throw new BadRequestException("Unknown type " + type);
		}
	}

	/**
	 * User password mapping.
	 *
	 * @param user the user
	 * @param password the password
	 */
	private void userPasswordMapping(User user, String password) {
		if (password != null && UserTypeEnum.LOCAL.equals(user.getType())) {
			user.setPassword(passwordUtils.hashPassword(password));
		} else if (password == null && UserTypeEnum.LDAP.equals(user.getType())) {
			// LDAP Users not need password
		} else {
			throw new BadRequestException("Password cannot be null!");
		}
	}
	
	/*
	 * User Application
	 */

	/**
	 * Gets user application by id.
	 *
	 * @param params the params
	 * @return the user application by id
	 * @throws Exception the exception
	 */
	public UserApplicationBean getUserApplicationById(IdParams params) throws Exception {
		UserApplication userApplication = userApplicationRepository.findById(UserApplication.class, params.getId());

		UserApplicationBean bean = new UserApplicationBean();
		BeanUtils.copyProperties(bean, userApplication);

		return bean;
	}

	/**
	 * Find user application.
	 *
	 * @param params the params
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<UserApplicationBean> findUserApplication(FindUserApplicationParams params) throws Exception {

		List<UserApplication> userApplications = null;

		if (params.getUserId() != null) {
			User user = userRepository.findById(User.class, params.getUserId());
			if (user != null) {
				userApplications = user.getApplications();
			}
		}
		
		List<UserApplicationBean> listBean = new ArrayList<UserApplicationBean>();

		if (userApplications == null || userApplications.isEmpty()) {
			return listBean;
		}
		
		for (UserApplication userApplication : userApplications) {
			UserApplicationBean bean = new UserApplicationBean();
			BeanUtils.copyProperties(bean, userApplication);
			listBean.add(bean);
		}

		return listBean;
	}

	/**
	 * Create user application.
	 *
	 * @param params the params
	 * @return the id bean
	 * @throws Exception the exception
	 */
	public IdBean createUserApplication(CreateUserApplicationParams params) throws Exception {

		User user = userRepository.findById(User.class, params.getUserId());
		if (user == null) {
			throw new BadRequestException("User with id " + params.getUserId() + " not found!");
		}
		
		UserApplication userApplication = new UserApplication();

		// Mapping
		userApplication.setRole(params.getRole());
		userApplication.setIsAdmin(params.getIsAdmin());
		userApplication.setCode(params.getName());
		userApplication.setUser(user);
		
		userApplicationNameMapping(userApplication, params.getName());
		
		// Persist user
		userApplicationRepository.save(userApplication);
		
		return new IdBean(userApplication.getId());
	}

	/**
	 * Update user application.
	 *
	 * @param params the params
	 * @throws Exception the exception
	 */
	public void updateUserApplication(UpdateUserApplicationParams params) throws Exception {
		UserApplication user = userApplicationRepository.findById(UserApplication.class, params.getId());

		// Mapping
		String value = params.getRole();
		if (value != null && !value.isEmpty()) {
			user.setRole(value);
		}
		Boolean is = params.getIsAdmin();
		if (is != null) {
			user.setIsAdmin(is);
		}
		
		// Persist user
		userApplicationRepository.save(user);
	}

	/**
	 * User application name mapping.
	 *
	 * @param userApplication the user application
	 * @param name the name
	 */
	private void userApplicationNameMapping(UserApplication userApplication, String name) {
		if (name == null || name.equalsIgnoreCase(ApplicationNameEnum.ICG_DSHOPS_SERVICES.name())) {
			userApplication.setName(ApplicationNameEnum.ICG_DSHOPS_SERVICES);
		} else if (name.equalsIgnoreCase(ApplicationNameEnum.AUTH_BKO_SERVICES.name())) {
			userApplication.setName(ApplicationNameEnum.AUTH_BKO_SERVICES);
		} else if (name.equalsIgnoreCase(ApplicationNameEnum.AUTH_SERVICES.name())) {
			userApplication.setName(ApplicationNameEnum.AUTH_SERVICES);
		} else {
			throw new BadRequestException("Unknown name " + name);
		}
	}

	/**
	 * Remove user application.
	 *
	 * @param params the params
	 * @throws Exception the exception
	 */
	public void removeUserApplication(IdParams params) throws Exception {
		UserApplication user = userApplicationRepository.findById(UserApplication.class, params.getId());
		userApplicationRepository.delete(user);
	}

}
