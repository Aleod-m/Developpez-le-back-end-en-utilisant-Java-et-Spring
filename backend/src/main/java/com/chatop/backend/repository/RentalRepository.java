package com.chatop.backend.repository;

import com.chatop.backend.domain.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Number> {
}
