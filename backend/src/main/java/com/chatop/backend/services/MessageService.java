package com.chatop.backend.services;

import com.chatop.backend.domain.Message;
import com.chatop.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    public MessageRepository repository;

    public Message save(Message message) {
        return message;
    }
}
