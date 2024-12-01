package com.adapt.boilerplate.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adapt.boilerplate.springboot.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	Optional<User> findById(Long id);

	boolean existsByEmail(String email);

}
