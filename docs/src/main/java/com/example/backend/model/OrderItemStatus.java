package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OrderItemStatus")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemStatus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusId;
    
    @Column(nullable = false, length = 50)
    private String statusName;
    
    @Column(length = 100)
    private String description;
} 