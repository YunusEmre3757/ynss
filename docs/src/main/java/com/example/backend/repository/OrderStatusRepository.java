package com.example.backend.repository;

import com.example.backend.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    
    Optional<OrderStatus> findByStatusName(String statusName);
    
    boolean existsByStatusName(String statusName);
} 