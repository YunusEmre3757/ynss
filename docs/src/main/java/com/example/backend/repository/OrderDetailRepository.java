package com.example.backend.repository;

import com.example.backend.model.Customer;
import com.example.backend.model.OrderDetail;
import com.example.backend.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    
    List<OrderDetail> findByCustomer(Customer customer);
    
    List<OrderDetail> findByStatus(OrderStatus status);
    
    List<OrderDetail> findByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT o FROM OrderDetail o WHERE o.customer.userId = :userId ORDER BY o.transactionDate DESC")
    List<OrderDetail> findCustomerOrdersOrderedByDateDesc(Integer userId);
    
    @Query("SELECT COUNT(o) FROM OrderDetail o WHERE o.status.statusId = :statusId")
    Long countOrdersByStatusId(Integer statusId);
} 