package com.chatop.backend.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chatop.backend.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
