package com.adapt.boilerplate.springboot.controller;

import com.adapt.boilerplate.springboot.exceptions.ApiExceptionResponse;
import com.adapt.boilerplate.springboot.exceptions.ExceptionConstants;
import com.adapt.boilerplate.springboot.exceptions.InvalidAuthenticationException;
import com.adapt.boilerplate.springboot.exceptions.InvalidSyntaxException;
import com.adapt.boilerplate.springboot.exceptions.*;
import com.adapt.boilerplate.springboot.security.dto.LoginRequest;
import com.adapt.boilerplate.springboot.security.dto.LoginResponse;
import com.adapt.boilerplate.springboot.security.dto.RegistrationRequest;
import com.adapt.boilerplate.springboot.security.dto.RegistrationResponse;
import com.adapt.boilerplate.springboot.security.jwt.JwtTokenService;
import com.adapt.boilerplate.springboot.service.UserService;
import com.adapt.boilerplate.springboot.utils.ExceptionMessageAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class Authentication {
    private final JwtTokenService jwtTokenService;

    private final UserService userService;

    private final ExceptionMessageAccessor accessor;

    @PostMapping(value="/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> loginRequest(@Valid @RequestBody LoginRequest loginRequest) {

            final LoginResponse loginResponse = jwtTokenService.getLoginResponse(loginRequest);
            return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(value="/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationResponse> registrationRequest(@Valid @RequestBody RegistrationRequest registrationRequest) {
        if (!registrationRequest.getPassword().equals(registrationRequest.getConfirm_password())) {
            throw new InvalidSyntaxException(accessor.getMessage(null, "password_and_confirm_password_no_match"));
        }

        final RegistrationResponse registrationResponse = userService.registration(registrationRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);

    }

    @ExceptionHandler(InvalidAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseEntity<ApiExceptionResponse> handleInvalidAuthenticationException(InvalidAuthenticationException exception) {

        final ApiExceptionResponse response = new ApiExceptionResponse(ExceptionConstants.INVALID_AUTHENTICATED.getCode(),
                accessor.getMessage(null, ExceptionConstants.INVALID_AUTHENTICATED.getMessageName()));
        log.warn("InvalidAuthenticationException: {}", response.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}
