package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "CarModels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer modelId;
    
    @Column(nullable = false, length = 50)
    private String modelName;
    
    @ManyToOne
    @JoinColumn(name = "brandId", nullable = false)
    private Brand brand;
    
    @ManyToOne
    @JoinColumn(name = "transmissionId", nullable = false)
    private Transmission transmission;
    
    @ManyToOne
    @JoinColumn(name = "bodyStyleId", nullable = false)
    private BodyStyle bodyStyle;
    
    @ManyToOne
    @JoinColumn(name = "energySourceId", nullable = false)
    private EnergySource energySource;
    
    @Column(precision = 3, scale = 1)
    private BigDecimal engineSize;
    
    private Integer enginePower;
    
    private Integer doorCount;
    
    private Integer weight;
    
    private Integer maxSpeed;
    
    private Integer baggageSize;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private Boolean isActive = true;
    
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