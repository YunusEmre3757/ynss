package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Customers", indexes = {
    @Index(name = "idx_customer_email", columnList = "email", unique = true)
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    
    @Column(nullable = false, length = 50)
    private String name;
    
    @Column(nullable = false, length = 50)
    private String surname;
    
    @Column(nullable = false, length = 100, unique = true)
    private String email;
    
    private LocalDate birthDate;
    
    @Column(nullable = false, length = 255)
    private String password;
    
    @Column(length = 20)
    private String phoneNumber;
    
    @Column(length = 500)
    private String address;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private Boolean isActive = true;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "customer_roles",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Helper method to add a role
    public void addRole(Role role) {
        this.roles.add(role);
    }
} 