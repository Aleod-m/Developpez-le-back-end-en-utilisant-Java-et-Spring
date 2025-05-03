package com.chatop.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chatop.backend.domain.Message;
import com.chatop.backend.domain.dto.MessageRequestDTO;
import com.chatop.backend.service.map.MessageMapper;
import com.chatop.backend.repository.MessageRepository;

@Service
public class MessageService {

	private final MessageRepository messageRepo;

	private final MessageMapper messageMapper;

	public MessageService(MessageRepository messageRepo, MessageMapper messageMapper) {
		this.messageRepo = messageRepo;
		this.messageMapper = messageMapper;
	}

	public Optional<Message> save(MessageRequestDTO message) {
		return messageMapper.fromRequest(message).map(m -> messageRepo.save(m));
	}

}
