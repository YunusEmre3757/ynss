package com.example.backend.repository;

import com.example.backend.model.Comment;
import com.example.backend.model.Customer;
import com.example.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    
    List<Comment> findByProduct(Product product);
    
    List<Comment> findByCustomer(Customer customer);
    
    List<Comment> findByIsApprovedTrue();
    
    List<Comment> findByProductAndIsApprovedTrue(Product product);
    
    @Query("SELECT c FROM Comment c WHERE c.product.productId = :productId AND c.isApproved = true ORDER BY c.commentDate DESC")
    List<Comment> findApprovedCommentsByProductIdOrderedByDate(Integer productId);
    
    @Query("SELECT AVG(c.rating) FROM Comment c WHERE c.product.productId = :productId AND c.isApproved = true")
    Double getAverageRatingByProductId(Integer productId);
    
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.isApproved = false")
    Long countUnapprovedComments();
} 