package com.chatop.backend.constroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.backend.domain.dto.MessageRequestDTO;
import com.chatop.backend.domain.dto.MessageResponseDTO;
import com.chatop.backend.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController extends AbstractController {

	private final MessageService messageSrvc;

	public MessageController(MessageService messageSrvc) {
		this.messageSrvc = messageSrvc;
	}

	@PostMapping
	public ResponseEntity<MessageResponseDTO> create(@RequestBody MessageRequestDTO messageReq) {
		return responseFromOptional(
				messageSrvc.save(messageReq).map(m -> new MessageResponseDTO("Message send with success")),
				HttpStatus.BAD_REQUEST);
	}

}
