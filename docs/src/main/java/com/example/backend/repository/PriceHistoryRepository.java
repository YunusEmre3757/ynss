package com.example.backend.repository;

import com.example.backend.model.PriceHistory;
import com.example.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Integer> {
    
    List<PriceHistory> findByProduct(Product product);
    
    List<PriceHistory> findByProductOrderByChangeDateDesc(Product product);
    
    List<PriceHistory> findByChangeDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT p FROM PriceHistory p WHERE p.product.productId = :productId ORDER BY p.changeDate DESC")
    List<PriceHistory> findPriceHistoryByProductId(Integer productId);
} 