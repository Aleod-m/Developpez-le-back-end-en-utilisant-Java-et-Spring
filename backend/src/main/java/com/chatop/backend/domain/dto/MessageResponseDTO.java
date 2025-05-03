package com.chatop.backend.domain.dto;

public class MessageResponseDTO {

	private String message;

	public MessageResponseDTO(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

}
