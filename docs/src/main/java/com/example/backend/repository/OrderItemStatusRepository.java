package com.example.backend.repository;

import com.example.backend.model.OrderItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemStatusRepository extends JpaRepository<OrderItemStatus, Integer> {
    
    Optional<OrderItemStatus> findByStatusName(String statusName);
    
    boolean existsByStatusName(String statusName);
} 