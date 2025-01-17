package com.adapt.boilerplate.springboot.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.adapt.boilerplate.springboot.model.User;
import com.adapt.boilerplate.springboot.security.dto.AuthenticatedUserDto;
import com.adapt.boilerplate.springboot.security.dto.RegistrationRequest;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthenticationMapper {

	AuthenticationMapper INSTANCE = Mappers.getMapper(AuthenticationMapper.class);

	User convertToUser(RegistrationRequest registrationRequest);

	AuthenticatedUserDto convertToAuthenticatedUserDto(User user);

	User convertToUser(AuthenticatedUserDto authenticatedUserDto);


}
