package com.chatop.backend.repository;

import com.chatop.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Number> {

}
