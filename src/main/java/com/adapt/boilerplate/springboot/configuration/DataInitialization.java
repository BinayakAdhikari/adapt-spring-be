package com.adapt.boilerplate.springboot.configuration;

import com.adapt.boilerplate.springboot.model.Role;
import com.adapt.boilerplate.springboot.repository.RoleRepository;
import com.adapt.boilerplate.springboot.utils.ProjectConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class DataInitialization {

  private final RoleRepository roleRepository;

  @PostConstruct
  public void initData() {
    createRoleIfNotExists(ProjectConstants.ROLE_ADMIN);
    createRoleIfNotExists(ProjectConstants.ROLE_MEMBER);
  }

  private void createRoleIfNotExists(String userRole) {
    Role role = roleRepository.findByName(userRole);
    if (role == null) {
      role = Role.builder().name(userRole).build();
      roleRepository.save(role);
    }
  }
}
