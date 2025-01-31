package com.chatop.backend.services;

import com.chatop.backend.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService {
    @Autowired
    public RentalRepository rentalRepository;

}
