package com.example.backend.repository;

import com.example.backend.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    
    Optional<Brand> findByBrandName(String brandName);
    
    List<Brand> findByBrandNameContainingIgnoreCase(String keyword);
    
    boolean existsByBrandName(String brandName);
} 