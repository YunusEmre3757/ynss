package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "OrdersItems", indexes = {
    @Index(name = "idx_orderitem_order", columnList = "orderId"),
    @Index(name = "idx_orderitem_product", columnList = "productId"),
    @Index(name = "idx_orderitem_status", columnList = "itemStatusId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemId;
    
    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private OrderDetail order;
    
    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;
    
    @Column(nullable = false)
    private Integer quantity = 1;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;
    
    @ManyToOne
    @JoinColumn(name = "itemStatusId", nullable = false)
    private OrderItemStatus itemStatus;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
} 