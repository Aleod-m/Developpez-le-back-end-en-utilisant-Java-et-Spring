package com.chatop.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatop.backend.domain.dto.RentalCreationDTO;
import com.chatop.backend.domain.dto.RentalDTO;
import com.chatop.backend.domain.dto.RentalUpdateDTO;
import com.chatop.backend.repository.RentalRepository;
import com.chatop.backend.service.map.RentalMapper;

@Service
public class RentalService {

	private final RentalMapper mapper;

	private final RentalRepository rentalRepo;

	private final UserService userSrvc;

	private final StorageService files;

	public RentalService(RentalRepository rentalRepo, UserService userSrvc, RentalMapper mapper, StorageService files,
			StorageService files2) {
		this.mapper = mapper;
		this.rentalRepo = rentalRepo;
		this.userSrvc = userSrvc;
		this.files = files2;
	}

	/**
	 * Tries to retrieve a rental from its unique identifier.
	 * @param rentalId the rental unique identifier.
	 * @return The rental Data Transfer Object if found `Optional.empty()` otherwise.
	 */
	@Transactional(readOnly = true)
	public Optional<RentalDTO> findById(Long rentalId) {
		return rentalRepo.findById(rentalId).map(u -> mapper.toDTO(u));
	}

	/**
	 * @return The list of rental Data Transfer Objects.
	 */
	@Transactional(readOnly = true)
	public List<RentalDTO> getAll() {
		return rentalRepo.findAll().stream().map(r -> mapper.toDTO(r)).toList();
	}

	/**
	 * Store a newly created rental to the database.
	 * @param rentalCreation The rental creation information.
	 * @return The stored rental Data Transfer Object.
	 */
	@Transactional
	public Optional<RentalDTO> create(RentalCreationDTO rentalCreation) {
		return mapper.fromDTO(rentalCreation)
			.map(r -> rentalRepo.save(r))
			.flatMap(r -> files.store(r, rentalCreation.getPicture()))
			.map(r -> mapper.toDTO(rentalRepo.save(r)));
	}

	public Optional<RentalDTO> update(Long id, RentalUpdateDTO rental) {
		return rentalRepo.findById(id).filter(r -> {
			return userSrvc.getCurrentUser().map(u -> u.getId() == r.getOwner().getId()).orElse(false);
		}).map(r -> mapper.toDTO(rentalRepo.save(mapper.updateFromDto(r, rental))));
	}

}
