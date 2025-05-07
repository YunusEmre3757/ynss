package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "PaymentInfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentInfoId;
    
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Customer customer;
    
    @Column(length = 50)
    private String bankName;
    
    @Column(length = 20)
    private String accountNumber;
    
    @Column(length = 16)
    private String cardNumber; // Storing last 4 digits or tokenized card
    
    @Column(length = 100)
    private String cardHolderName;
    
    @Column(length = 10)
    private String expiryDate;
    
    private Boolean isDefault = false;
    
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