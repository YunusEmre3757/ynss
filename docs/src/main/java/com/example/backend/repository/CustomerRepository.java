package com.example.backend.repository;

import com.example.backend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    
    Optional<Customer> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<Customer> findByIsActiveTrue();
    
    @Query("SELECT c FROM Customer c WHERE " +
           "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.surname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Customer> searchCustomers(String keyword);
} 