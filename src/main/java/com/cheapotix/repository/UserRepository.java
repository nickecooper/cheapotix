package com.cheapotix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheapotix.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {
	Optional<AppUser> findByEmail(String email);
}
