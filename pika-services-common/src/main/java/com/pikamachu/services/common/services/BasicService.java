package com.pikamachu.services.common.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.Response;

import com.pikamachu.services.common.exceptions.BadRequestException;
import com.pikamachu.services.common.security.TokenContext;

/**
 * The Class BasicService.
 */
public abstract class BasicService {

	/**
	 * The constant BEARER_SCHEMA.
	 */
	protected final static String BEARER_SCHEMA = "Bearer";

	/**
	 * The constant AUTHORIZATION_HEADER.
	 */
	protected final static String AUTHORIZATION_HEADER = "Authorization";

	/** The log. */
	@Inject
	protected Logger log;

	/** The token context. */
	@Inject
	protected TokenContext tokenContext;

	/** The validator. */
	protected Validator validator;

	/**
	 * Sets up.
	 */
	@PostConstruct
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	/**
	 * <p>
	 * Validates the given Manager variable and throws validation exceptions
	 * based on the type of error. If the error is standard bean validation
	 * errors then it will throw a ConstraintValidationException with the set of
	 * the constraints violated.
	 * </p>
	 * <p>
	 * If the error is caused because an existing manager with the same email is
	 * registered it throws a regular validation exception so that it can be
	 * interpreted separately.
	 * </p>
	 *
	 * @param model the model
	 * @throws ConstraintViolationException If Bean Validation errors exist
	 * @throws ConstraintViolationException If Bean Validation errors exist
	 */
	protected void validate(Object model) throws ConstraintViolationException, ValidationException {
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Object>> violations = validator.validate(model);

		if (!violations.isEmpty()) {
			// TODO !
			ConstraintViolation<Object> violation = violations.iterator().next();
			throw new BadRequestException( violation.getPropertyPath() + " " + violation.getMessage() + " " + violation.getInvalidValue());
		}
	}

	/**
	 * Creates a JAX-RS "Bad Request" response including a map of all violation
	 * fields, and their message. This can then be used by clients to show
	 * violations.
	 *
	 * @param violations A set of violations that needs to be reported
	 * @return JAX -RS response containing all violations
	 */
	protected Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<Object>> violations) {
		getLog().fine("Validation completed. violations found: " + violations.size());

		Map<String, String> responseObj = new HashMap<String, String>();

		for (ConstraintViolation<?> violation : violations) {
			responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
		}

		return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
	}

	/**
	 * Gets log.
	 *
	 * @return the log
	 */
	public Logger getLog() {
		return log;
	}

	/**
	 * Sets log.
	 *
	 * @param log the log
	 */
	public void setLog(Logger log) {
		this.log = log;
	}

	/**
	 * Gets validator.
	 *
	 * @return the validator
	 */
	public Validator getValidator() {
		return validator;
	}

	/**
	 * Sets validator.
	 *
	 * @param validator the validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

}