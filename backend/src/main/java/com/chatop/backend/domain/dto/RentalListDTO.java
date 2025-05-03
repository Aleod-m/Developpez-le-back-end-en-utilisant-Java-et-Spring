package com.chatop.backend.domain.dto;

import java.util.List;

public class RentalListDTO {

	private final List<RentalDTO> rentals;

	public RentalListDTO(List<RentalDTO> rentals) {
		this.rentals = rentals;
	}

	public List<RentalDTO> getRentals() {
		return this.rentals;
	}

}
