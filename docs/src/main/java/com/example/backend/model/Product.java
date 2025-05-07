package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Products", indexes = {
    @Index(name = "idx_product_model", columnList = "modelId"),
    @Index(name = "idx_product_color", columnList = "colorId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    
    @ManyToOne
    @JoinColumn(name = "modelId", nullable = false)
    private CarModel model;
    
    @ManyToOne
    @JoinColumn(name = "colorId", nullable = false)
    private Color color;
    
    @ManyToOne
    @JoinColumn(name = "packageTypeId", nullable = false)
    private PackageType packageType;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(nullable = false)
    private Integer stock = 0;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private Boolean isActive = true;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
} 