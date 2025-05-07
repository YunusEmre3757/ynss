package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    
    @Column(nullable = false, unique = true, length = 50)
    private String name;
    
    @Column(length = 100)
    private String description;
} 