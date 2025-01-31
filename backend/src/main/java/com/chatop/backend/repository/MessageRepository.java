package com.chatop.backend.repository;

import com.chatop.backend.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Number> {
}
