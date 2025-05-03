package com.chatop.backend.service.map;

import com.chatop.backend.domain.dto.RentalCreationDTO;
import com.chatop.backend.domain.dto.RentalDTO;
import com.chatop.backend.domain.dto.RentalUpdateDTO;
import com.chatop.backend.repository.UserRepository;
import com.chatop.backend.service.StorageService;
import com.chatop.backend.service.UserService;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chatop.backend.domain.Rental;

@Service
public class RentalMapper {

	private final UserService userSrvc;

	private final UserRepository userRepo;

	public RentalMapper(StorageService files, UserService userSrvc, UserRepository userRepo) {
		this.userSrvc = userSrvc;
		this.userRepo = userRepo;
	}

	public Optional<Rental> fromDTO(RentalCreationDTO rental) {
		return Optional.of(new Rental())
			.flatMap(r -> userSrvc.getCurrentUser().flatMap(cu -> userRepo.findById(cu.getId())).map(owner -> {
				r.setOwner(owner);
				return r;
			}))
			.map(r -> {
				r.setName(rental.getName());
				r.setSurface(rental.getSurface());
				r.setPrice(rental.getPrice());
				r.setDescription(rental.getDescription());
				return r;
			});
	}

	public Rental updateFromDto(Rental rental, RentalUpdateDTO rentalDto) {
		rental.setName(rentalDto.getName());
		rental.setSurface(rentalDto.getSurface());
		rental.setPrice(rentalDto.getPrice());
		rental.setDescription(rentalDto.getDescription());
		return rental;
	}

	public RentalDTO toDTO(Rental rental) {
		RentalDTO rentalDto = new RentalDTO();
		rentalDto.setId(rental.getId());
		rentalDto.setName(rental.getName());
		rentalDto.setSurface(rental.getSurface());
		rentalDto.setPrice(rental.getPrice());
		rentalDto.setPicture(rental.getPicture());
		rentalDto.setDescription(rental.getDescription());
		rentalDto.setOwnerId(rental.getOwner().getId());
		rentalDto.setCreatedAt(rental.getCreatedAt());
		rentalDto.setUpdatedAt(rental.getUpdatedAt());
		return rentalDto;
	}

}
