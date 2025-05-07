package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "EnergySources")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergySource {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer energySourceId;
    
    @Column(nullable = false, length = 50)
    private String energySourceName;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
} 