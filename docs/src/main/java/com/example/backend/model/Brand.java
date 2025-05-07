package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Brand")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer brandId;
    
    @Column(nullable = false, length = 50)
    private String brandName;
    
    @Column(length = 100)
    private String slogan;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
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