package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transmissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transmission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transmissionId;
    
    @Column(nullable = false, length = 50)
    private String transmissionName;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
} 