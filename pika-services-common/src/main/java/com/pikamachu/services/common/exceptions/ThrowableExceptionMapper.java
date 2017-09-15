package com.pikamachu.services.common.exceptions;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.pikamachu.services.common.beans.ErrorBean;

/**
 * Throwable Exception Mapper.
 */
@Provider
public class ThrowableExceptionMapper implements ExceptionMapper<Throwable> {

	/** The log. */
	private static Logger log = Logger.getLogger(ThrowableExceptionMapper.class.getName());
	
	@Override
	public Response toResponse(Throwable exception) {
		// Get exception Cause
		Throwable exceptionCause = exception;
		while (exceptionCause.getCause() != null) {
			exceptionCause = exceptionCause.getCause();
		}
		// Get error code
		int errorCode = 500;
		if ( exceptionCause instanceof ServiceException ) {
			errorCode = ((ServiceException) exceptionCause).getErrorCode();
		}
		// Build error message
		String exceptionMessage = exceptionCause.getMessage();
		if (exceptionCause instanceof ConstraintViolationException) {
			Collection<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) exceptionCause).getConstraintViolations();
			if (constraintViolations != null && !constraintViolations.isEmpty()) {
				ConstraintViolation<?> violation = constraintViolations.iterator().next();
				exceptionMessage = violation.getPropertyPath() + " " + violation.getMessage() + " " + violation.getInvalidValue();
			}
		}
		if (exceptionMessage == null || exceptionMessage.isEmpty()) {
			exceptionMessage = exceptionCause.getClass().getSimpleName().replace("Exception", "");
		}
		// Log error
		if (errorCode == 500) {
			log.log(Level.SEVERE, exceptionMessage, exceptionCause);
		} else {
			log.log(Level.WARNING, exceptionMessage);
		}
		// build response
		if (exceptionMessage.startsWith("{") && exceptionMessage.endsWith("}")) {
			// The exception message seems a json
			return Response.status(errorCode).entity(exceptionMessage).build();
		} else {
			return Response.status(errorCode).entity(new ErrorBean(exceptionMessage)).build();
		}
	}
}
