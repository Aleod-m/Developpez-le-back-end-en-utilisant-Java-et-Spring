package com.chatop.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.backend.domain.User;
import com.chatop.backend.domain.dto.UserDTO;
import com.chatop.backend.service.map.UserMapper;
import com.chatop.backend.repository.UserRepository;

@Service
public class UserService {

	private final UserMapper mapper;

	private final UserRepository userRepo;

	private final PasswordEncoder pwdEncoder;

	@Autowired
	public UserService(PasswordEncoder pwdEncoder, UserRepository userRepo, UserMapper mapper) {
		this.mapper = mapper;
		this.pwdEncoder = pwdEncoder;
		this.userRepo = userRepo;
	}

	/**
	 * Tries to retrieve a user from its unique identifier.
	 * @param userId the user unique identifier.
	 * @return The user Data Transfer Object if found `Optional.empty()` otherwise.
	 */
	public Optional<UserDTO> findById(Long userId) {
		return userRepo.findById(userId).map(u -> mapper.toDTO(u));
	}

	/**
	 * Tries to retrieve a user from its email.
	 * @param userEmail the user email.
	 * @return The user Data Transfer Object if found `Optional.empty()` otherwise.
	 */
	public Optional<UserDTO> findByEmail(String userEmail) {
		return userRepo.findByEmail(userEmail).map(u -> mapper.toDTO(u));
	}

	/**
	 * Try to store a new user in database and returns it when saved. If the user email is
	 * already in use will return `Optional.empty()` and not save the user.
	 * @param newUser the user to register.
	 * @return The saved user Data Transfer Object if found `Optional.empty()` otherwise.
	 */
	public Optional<UserDTO> addNewUser(String email, String name, String password) {
		// The user email is already in use. Abort the user registration.
		if (findByEmail(email).isPresent())
			return Optional.empty();
		User newUser = new User();
		newUser.setName(name);
		newUser.setEmail(email);
		newUser.setPassword(pwdEncoder.encode(password));
		return Optional.of(mapper.toDTO(userRepo.save(newUser)));
	}

	/**
	 * Get the currently logged in user.
	 * @return The user data transfer object if found `Optional.empty()` otherwise.
	 */
	public Optional<UserDTO> getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return findByEmail(email);
	}

}
