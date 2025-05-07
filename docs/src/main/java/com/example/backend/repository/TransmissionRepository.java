package com.example.backend.repository;

import com.example.backend.model.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransmissionRepository extends JpaRepository<Transmission, Integer> {
    
    Optional<Transmission> findByTransmissionName(String transmissionName);
    
    List<Transmission> findByTransmissionNameContainingIgnoreCase(String keyword);
    
    boolean existsByTransmissionName(String transmissionName);
} 

