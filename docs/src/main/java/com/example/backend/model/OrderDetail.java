package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "OrdersDetails", indexes = {
    @Index(name = "idx_order_user", columnList = "userId"),
    @Index(name = "idx_order_status", columnList = "statusId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    
    private LocalDateTime transactionDate;
    
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "statusId", nullable = false)
    private OrderStatus status;
    
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;
    
   
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.transactionDate = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
} 