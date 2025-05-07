package com.example.backend.repository;

import com.example.backend.model.OrderDetail;
import com.example.backend.model.OrderItem;
import com.example.backend.model.OrderItemStatus;
import com.example.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    
    List<OrderItem> findByOrder(OrderDetail order);
    
    List<OrderItem> findByProduct(Product product);
    
    List<OrderItem> findByItemStatus(OrderItemStatus status);
    
    Optional<OrderItem> findByOrderAndProduct(OrderDetail order, Product product);
    
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.orderId = :orderId")
    List<OrderItem> findByOrderId(Integer orderId);
    
    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.product.productId = :productId")
    Long sumQuantityByProductId(Integer productId);
} 