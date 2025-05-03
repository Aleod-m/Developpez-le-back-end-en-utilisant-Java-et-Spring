package com.chatop.backend.constroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.backend.domain.dto.RegisterRequestDTO;
import com.chatop.backend.domain.dto.UserDTO;
import com.chatop.backend.domain.dto.AuthRequestDTO;
import com.chatop.backend.domain.dto.AuthResponseDTO;
import com.chatop.backend.service.JwtService;
import com.chatop.backend.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends AbstractController {

	@Autowired
	JwtService jwtService;

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	UserService userSrvc;

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authReq) {
		Authentication auth = authManager
			.authenticate(new UsernamePasswordAuthenticationToken(authReq.getEmail(), authReq.getPassword()));
		return ResponseEntity.ok(new AuthResponseDTO(jwtService.genToken(auth).getTokenValue()));
	}

	@PostMapping("/register")
	public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO authReq) {
		return responseFromOptional(
				userSrvc.addNewUser(authReq.getEmail(), authReq.getName(), authReq.getPassword()).map(u -> {
					Authentication auth = authManager.authenticate(
							new UsernamePasswordAuthenticationToken(authReq.getEmail(), authReq.getPassword()));
					return new AuthResponseDTO(jwtService.genToken(auth).getTokenValue());
				}), HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/me")
	public ResponseEntity<UserDTO> getCurrentUser() {
		return responseFromOptional(userSrvc.getCurrentUser());
	}

}
