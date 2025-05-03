package com.chatop.backend.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageRequestDTO {

	private long rentalId;

	private long userId;

	private String message;

	@JsonProperty("rental_id")
	public long getRentalId() {
		return rentalId;
	}

	@JsonProperty("user_id")
	public long getUserId() {
		return userId;
	}

	public String getMessage() {
		return message;
	}

}
