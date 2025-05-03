package com.chatop.backend.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chatop.backend.domain.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

}
