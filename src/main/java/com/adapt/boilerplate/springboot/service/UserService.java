package com.adapt.boilerplate.springboot.service;

import com.adapt.boilerplate.springboot.dto.request.UpdateUserRequest;
import com.adapt.boilerplate.springboot.dto.response.UpdateUserResponse;
import com.adapt.boilerplate.springboot.dto.request.UserUpdateInformationRequest;
import com.adapt.boilerplate.springboot.dto.response.UserInfoResponse;
import com.adapt.boilerplate.springboot.security.dto.AuthenticatedUserDto;
import com.adapt.boilerplate.springboot.security.dto.RegistrationRequest;
import com.adapt.boilerplate.springboot.security.dto.RegistrationResponse;
import org.springframework.data.domain.Pageable;

import com.adapt.boilerplate.springboot.configuration.dto.PageDto;
import com.adapt.boilerplate.springboot.model.User;
import com.adapt.boilerplate.springboot.security.dto.*;

public interface UserService {
	PageDto<UserInfoResponse> getPage(Pageable pageable);

	User findByEmail(String email);

	RegistrationResponse registration(RegistrationRequest registrationRequest);

	AuthenticatedUserDto findAuthenticatedUserById(Long id);

	AuthenticatedUserDto findAuthenticatedUserByEmail(String email);

	void deleteUserById(Long id);

	UpdateUserResponse updateUser(Long id, UpdateUserRequest updateUserRequest);

	UpdateUserResponse updateInformationByUser(String username,
			UserUpdateInformationRequest userUpdateInformationRequest);

	User findById(Long id);

}
