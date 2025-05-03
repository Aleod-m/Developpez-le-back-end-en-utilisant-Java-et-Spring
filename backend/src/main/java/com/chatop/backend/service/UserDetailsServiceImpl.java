package com.chatop.backend.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chatop.backend.domain.User;
import com.chatop.backend.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepo;

	public UserDetailsServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// We use the email for login.
		Optional<User> user = userRepo.findByEmail(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("User email not found: " + username);
		}
		return org.springframework.security.core.userdetails.User.withUsername(username)
			.password(user.get().getPassword())
			.build();
	}

}
