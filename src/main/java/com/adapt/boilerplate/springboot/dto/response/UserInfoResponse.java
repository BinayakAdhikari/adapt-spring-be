package com.adapt.boilerplate.springboot.dto.response;

import com.adapt.boilerplate.springboot.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {

    private Long id;

    private String full_name;

    private String email;

    private Role role;
}