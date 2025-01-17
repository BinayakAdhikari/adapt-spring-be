package com.adapt.boilerplate.springboot.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.adapt.boilerplate.springboot.utils.ExceptionMessageAccessor;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {
	@Autowired
	private ExceptionMessageAccessor accessor;

	@ExceptionHandler(InternalServerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<?> handleConversion(RuntimeException ex) {
		final InternalExceptionResponse response = new InternalExceptionResponse(
				ExceptionConstants.INTERNAL_SERVER_ERROR.getCode(),
				accessor.getMessage(null, ExceptionConstants.INTERNAL_SERVER_ERROR.getMessageName()),
				ExceptionUtils.getStackTrace(ex));
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ValidationExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getDefaultMessage());
		}

		final ValidationExceptionResponse response = new ValidationExceptionResponse();
		response.setError_code(ExceptionConstants.INVALID_SYNTAX.getCode());
		response.setMessage(accessor.getMessage(null, ExceptionConstants.INVALID_SYNTAX.getMessageName()));
		response.setDetails(errors);
		log.warn("InvalidSyntaxException: {}", response.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ValidationExceptionResponse> handleConstraintViolationException(
			ConstraintViolationException ex) {
		List<String> errors = new ArrayList<>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getMessage());
		}

		final ValidationExceptionResponse response = new ValidationExceptionResponse();
		response.setError_code(ExceptionConstants.INVALID_SYNTAX.getCode());
		response.setMessage(accessor.getMessage(null, ExceptionConstants.INVALID_SYNTAX.getMessageName()));
		response.setDetails(errors);

		log.warn("ConstraintViolationException: {}", response.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(value = { InvalidSyntaxException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ValidationExceptionResponse> handleInvalidSyntaxExceptions(InvalidSyntaxException ex) {

		final ValidationExceptionResponse response = new ValidationExceptionResponse();
		response.setError_code(ExceptionConstants.INVALID_SYNTAX.getCode());
		response.setMessage(accessor.getMessage(null, ExceptionConstants.INVALID_SYNTAX.getMessageName()));
		response.setDetails(ex.getErrors());
		log.warn("InvalidSyntaxException: {}", response.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(EmailAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	ResponseEntity<ApiExceptionResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception) {

		final ApiExceptionResponse response = new ApiExceptionResponse(
				ExceptionConstants.EMAIL_ALREADY_EXISTS.getCode(),
				accessor.getMessage(null, ExceptionConstants.EMAIL_ALREADY_EXISTS.getMessageName()));
		log.warn("EmailAlreadyExistsException: {}", response.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}


	@ExceptionHandler(UnauthorizedAdminDeleteOtherAdminsException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	ResponseEntity<ApiExceptionResponse> handleForbiddenException(UnauthorizedAdminDeleteOtherAdminsException exception) {
		ApiExceptionResponse response = new ApiExceptionResponse(
				ExceptionConstants.UNAUTHORIZED_ADMIN_DELETE_OTHER_ADMINS.getCode(),
				accessor.getMessage(null, ExceptionConstants.UNAUTHORIZED_ADMIN_DELETE_OTHER_ADMINS.getMessageName()));
		log.warn("UnauthorizedAdminDeleteOtherAdminsException: {}", response.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}
}
