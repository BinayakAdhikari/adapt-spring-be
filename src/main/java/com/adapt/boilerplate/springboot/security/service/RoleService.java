package com.adapt.boilerplate.springboot.security.service;

import com.adapt.boilerplate.springboot.model.Role;

public interface RoleService {
    Role findById(Long id);
    Role findByName(String name);
}
