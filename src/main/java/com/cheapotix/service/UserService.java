package com.cheapotix.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cheapotix.model.AppUser;
import com.cheapotix.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public AppUser registerNewUser(String email, String rawPassword) {
		AppUser user = new AppUser();
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(rawPassword));
		return userRepository.save(user);
	}
	
	public AppUser findByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	public void updateUserPreferences(String email, List<String> arenaIds, double threshhold, int updatesUntilEmail, int emailFrequency) throws Exception {
		userRepository.updateArenaIdsAndThreshhold(email, arenaIds, threshhold, updatesUntilEmail, emailFrequency);
		
	}
}
