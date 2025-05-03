package com.chatop.backend.service.map;

import org.springframework.stereotype.Service;

import com.chatop.backend.domain.User;
import com.chatop.backend.domain.dto.UserDTO;

@Service
public class UserMapper {

	public UserMapper() {
	}

	public UserDTO toDTO(User user) {
		UserDTO userDto = new UserDTO();
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setName(user.getName());
		userDto.setCreatedAt(user.getCreatedAt());
		userDto.setUpdatedAt(user.getUpdatedAt());
		return userDto;
	}

}
