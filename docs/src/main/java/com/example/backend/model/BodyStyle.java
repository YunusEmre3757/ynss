package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "BodyStyles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BodyStyle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bodyStyleId;
    
    @Column(nullable = false, length = 50)
    private String bodyStyleName;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
} 