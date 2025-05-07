package com.example.backend.repository;

import com.example.backend.model.Customer;
import com.example.backend.model.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Integer> {
    
    List<PaymentInfo> findByCustomer(Customer customer);
    
    Optional<PaymentInfo> findByCustomerAndIsDefaultTrue(Customer customer);
    
    List<PaymentInfo> findByCustomerOrderByIsDefaultDesc(Customer customer);
} 