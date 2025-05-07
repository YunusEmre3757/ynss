package com.example.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Comments", indexes = {
    @Index(name = "idx_comment_product", columnList = "productId"),
    @Index(name = "idx_comment_user", columnList = "userId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;
    
    @Column(nullable = false, length = 500)
    private String commentBody;
    
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer rating;
    
    private LocalDateTime commentDate;
    
    private Boolean isApproved = false;
    
    @PrePersist
    protected void onCreate() {
        this.commentDate = LocalDateTime.now();
    }
} 