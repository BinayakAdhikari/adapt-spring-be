package com.adapt.boilerplate.springboot.maper.user;

import com.adapt.boilerplate.springboot.dto.request.UpdateUserRequest;
import com.adapt.boilerplate.springboot.dto.request.UserUpdateInformationRequest;
import com.adapt.boilerplate.springboot.dto.response.UserInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.adapt.boilerplate.springboot.configuration.dto.user.UserDto;
import com.adapt.boilerplate.springboot.model.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	UserDto convertToUserDto(User user);
	User convertToUser(UpdateUserRequest updateUserRequest);

	User convertToUser(UserUpdateInformationRequest userUpdateInformationRequest);

	@Mapping(source = "user.fullname", target = "full_name")
	UserInfoResponse convertToUserInfoResponse(User user);
}
