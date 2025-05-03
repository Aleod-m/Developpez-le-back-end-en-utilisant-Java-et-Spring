package com.chatop.backend.constroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chatop.backend.domain.dto.UserDTO;
import com.chatop.backend.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractController {

	private final UserService userSrvc;

	public UserController(UserService userSrvc) {
		this.userSrvc = userSrvc;
	}

	@GetMapping("{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		return responseFromOptional(userSrvc.findById(id));
	}

}
