package com.chatop.backend.service.map;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chatop.backend.domain.Message;
import com.chatop.backend.domain.dto.MessageRequestDTO;
import com.chatop.backend.repository.RentalRepository;
import com.chatop.backend.repository.UserRepository;

@Service
public class MessageMapper {

	private final UserRepository userRepo;

	private final RentalRepository rentalRepo;

	public MessageMapper(RentalRepository rentalRepo, UserRepository userRepo) {
		this.userRepo = userRepo;
		this.rentalRepo = rentalRepo;
	}

	public Optional<Message> fromRequest(MessageRequestDTO messageReq) {
		return Optional.of(new Message()).flatMap(m -> userRepo.findById(messageReq.getUserId()).map(u -> {
			m.setUser(u);
			return m;
		})).flatMap(m -> rentalRepo.findById(messageReq.getRentalId()).map(r -> {
			m.setRental(r);
			return m;
		})).map(m -> {
			m.setMessage(messageReq.getMessage());
			return m;
		});
	}

}
