package com.chatop.backend.config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.chatop.backend.service.UserDetailsServiceImpl;
import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
public class AuthConfig {

	@Value("${estate.security.jwt.secretKey}")
	private String secretKey;

	@Autowired
	private UserDetailsServiceImpl customUserDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtEncoder jwtEncoder() {
		try {
			byte[] secretKeyBytes = secretKey.getBytes();
			return new NimbusJwtEncoder(new ImmutableSecret<>(secretKeyBytes));
		}
		catch (final Exception e) {
			throw new RuntimeException("Secret Key for jwt not found.");
		}
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		byte[] secretKeyBytes = secretKey.getBytes();
		final SecretKeySpec secretKey = new SecretKeySpec(secretKeyBytes, 0, secretKeyBytes.length, "RSA");
		return NimbusJwtDecoder.withSecretKey(secretKey).build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder pwdEncoder) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
			.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(pwdEncoder);
		return authenticationManagerBuilder.build();
	}

}
