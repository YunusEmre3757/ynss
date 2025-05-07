package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PriceHistory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer priceHistoryId;
    
    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal oldPrice;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal newPrice;
    
    @Column(length = 3)
    private String currencyCode = "USD"; // Default currency
    
    @Column(nullable = false)
    private LocalDateTime changeDate;
    
    @Column(length = 50)
    private String changedBy; // User who made the change
    
    @PrePersist
    protected void onCreate() {
        this.changeDate = LocalDateTime.now();
    }
} 