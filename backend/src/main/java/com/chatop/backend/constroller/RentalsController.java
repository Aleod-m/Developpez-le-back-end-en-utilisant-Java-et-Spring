package com.chatop.backend.constroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.backend.domain.dto.RentalCreationDTO;
import com.chatop.backend.domain.dto.RentalDTO;
import com.chatop.backend.domain.dto.RentalListDTO;
import com.chatop.backend.domain.dto.RentalResponseDTO;
import com.chatop.backend.domain.dto.RentalUpdateDTO;
import com.chatop.backend.service.RentalService;
import com.chatop.backend.service.StorageService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController extends AbstractController {

	private final RentalService rentalSrvc;

	public RentalsController(RentalService rentalSrvc, StorageService storageSrvc) {
		this.rentalSrvc = rentalSrvc;
	}

	@GetMapping
	public ResponseEntity<RentalListDTO> getAllRentals() {
		return ResponseEntity.ok(new RentalListDTO(rentalSrvc.getAll()));
	}

	@Operation(summary = "Create a rental.")
	@PostMapping
	public ResponseEntity<RentalResponseDTO> createRental(@ModelAttribute RentalCreationDTO rentalCreation) {
		return responseFromOptional(rentalSrvc.create(rentalCreation).map(r -> new RentalResponseDTO("Rental created")),
				HttpStatus.NOT_ACCEPTABLE);
	}

	@Operation(summary = "Retrieve a rental from its id.")
	@GetMapping("/{id}")
	public ResponseEntity<RentalDTO> update(@PathVariable Long id) {
		return responseFromOptional(rentalSrvc.findById(id));
	}

	@Operation(summary = "Upate a rental from its id.")
	@PutMapping("/{id}")
	public ResponseEntity<RentalResponseDTO> update(@PathVariable Long id, @ModelAttribute RentalUpdateDTO rental) {
		rentalSrvc.update(id, rental);

		return ResponseEntity.ok(new RentalResponseDTO("Rental updated"));
	}

}
