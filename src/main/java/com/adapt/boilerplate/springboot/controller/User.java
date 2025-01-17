package com.adapt.boilerplate.springboot.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import com.adapt.boilerplate.springboot.dto.response.UserInfoResponse;
import com.adapt.boilerplate.springboot.exceptions.InvalidSyntaxException;
import com.adapt.boilerplate.springboot.dto.response.UpdateUserResponse;
import com.adapt.boilerplate.springboot.dto.request.UserUpdateInformationRequest;
import com.adapt.boilerplate.springboot.utils.ValidationMessageAccessor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import com.adapt.boilerplate.springboot.configuration.dto.PageDto;
import com.adapt.boilerplate.springboot.service.UserService;
import com.adapt.boilerplate.springboot.utils.ExceptionMessageAccessor;
import lombok.extern.slf4j.Slf4j;

import com.adapt.boilerplate.springboot.exceptions.ApiExceptionResponse;
import com.adapt.boilerplate.springboot.exceptions.ExceptionConstants;
import com.adapt.boilerplate.springboot.exceptions.UserIdNotExistsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/users")
public class User {

	final private UserService userService;

	final private ExceptionMessageAccessor accessor;

	final private ValidationMessageAccessor validationMessageAccessor;

	@GetMapping
	public ResponseEntity<PageDto<UserInfoResponse>> filterUser(
			@Positive(message = "{greater_zero}") @RequestParam(name = "page_size", required = false, defaultValue = DefaultConstants.DEFAULT_PAGE_SIZE) Integer pageSize,

			@Positive(message = "{greater_zero}") @RequestParam(name = "page", required = false, defaultValue = DefaultConstants.DEFAULT_PAGE) Integer page) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return ResponseEntity.status(HttpStatus.OK).body(userService.getPage(pageable));
	}


	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateInformationRequest userUpdateInformationRequest) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails == null) {
			final ApiExceptionResponse response = new ApiExceptionResponse(ExceptionConstants.UNAUTHORIZED.getCode(),
					accessor.getMessage(null, ExceptionConstants.UNAUTHORIZED.getMessageName()));
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}

		//username is the email the user logs in to
		String email = userDetails.getUsername();

		try {
			final UpdateUserResponse userUpdate = userService.updateInformationByUser(email, userUpdateInformationRequest);

			return ResponseEntity.ok(userUpdate);
		} catch (NumberFormatException e) {
			throw new InvalidSyntaxException(validationMessageAccessor.getMessage(null, "user_id_invalid"));
		}
	}
	@ExceptionHandler(UserIdNotExistsException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	ResponseEntity<ApiExceptionResponse> handleUserIdNotExistsException(UserIdNotExistsException exception) {

		final ApiExceptionResponse response = new ApiExceptionResponse(ExceptionConstants.USER_ID_NOT_EXISTS.getCode(),
				accessor.getMessage(null, ExceptionConstants.USER_ID_NOT_EXISTS.getMessageName()));
		log.warn("UserIdNotExistsException: {}", response.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	ResponseEntity<ApiExceptionResponse> handleAccessDeniedException(AccessDeniedException exception) {
		final ApiExceptionResponse response = new ApiExceptionResponse(ExceptionConstants.UNAUTHORIZED.getCode(),
				accessor.getMessage(null, ExceptionConstants.UNAUTHORIZED.getMessageName()));
		log.warn("AccessDeniedException: {}", response.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}

}

