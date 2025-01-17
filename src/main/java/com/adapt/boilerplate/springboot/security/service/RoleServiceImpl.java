package com.adapt.boilerplate.springboot.security.service;

import com.adapt.boilerplate.springboot.exceptions.RoleIdNotExistsException;
import com.adapt.boilerplate.springboot.model.Role;
import com.adapt.boilerplate.springboot.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public Role findById(Long id) {
        final Optional<Role> role = roleRepository.findById(id);

        if (role.isEmpty()) {
            throw new RoleIdNotExistsException();
        }

        return role.get();
    }

    @Override
    public Role findByName(String name) {
        final Role role = roleRepository.findByName(name);
        return role;
    }
}
