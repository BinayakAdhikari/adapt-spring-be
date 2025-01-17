package com.adapt.boilerplate.springboot.repository;

import com.adapt.boilerplate.springboot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String userRole);
}
