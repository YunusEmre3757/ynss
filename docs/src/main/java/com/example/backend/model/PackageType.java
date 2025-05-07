package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "PackageTypes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer packageTypeId;
    
    @Column(nullable = false, length = 50)
    private String packageTypeName;
    
    @Column(length = 200)
    private String description;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
} 