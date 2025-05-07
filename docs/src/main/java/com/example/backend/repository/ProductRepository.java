package com.example.backend.repository;

import com.example.backend.model.CarModel;
import com.example.backend.model.Color;
import com.example.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    List<Product> findByModel(CarModel model);
    
    List<Product> findByColor(Color color);
    
    List<Product> findByIsActiveTrue();
    
    List<Product> findByIsActiveTrueAndStockGreaterThan(Integer minimumStock);
    
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.stock > 0 AND " +
           "p.model.modelId = :modelId ORDER BY p.price ASC")
    List<Product> findAvailableProductsByModelIdOrderedByPrice(Integer modelId);
    
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.stock > 0 AND " +
           "(:modelId IS NULL OR p.model.modelId = :modelId) AND " +
           "(:colorId IS NULL OR p.color.colorId = :colorId) AND " +
           "(:packageTypeId IS NULL OR p.packageType.packageTypeId = :packageTypeId) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> filterProducts(Integer modelId, Integer colorId, Integer packageTypeId, 
                                BigDecimal minPrice, BigDecimal maxPrice);
} 