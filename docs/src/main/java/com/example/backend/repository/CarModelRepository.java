package com.example.backend.repository;

import com.example.backend.model.Brand;
import com.example.backend.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Integer> {
    
    List<CarModel> findByModelNameContainingIgnoreCase(String keyword);
    
    List<CarModel> findByBrand(Brand brand);
    
    List<CarModel> findByIsActiveTrue();
    
    @Query("SELECT c FROM CarModel c WHERE c.isActive = true AND " +
           "(LOWER(c.modelName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.brand.brandName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<CarModel> searchModels(String keyword);
    
    @Query("SELECT DISTINCT c FROM CarModel c " +
           "WHERE c.isActive = true " +
           "AND (:brandId IS NULL OR c.brand.brandId = :brandId) " +
           "AND (:bodyStyleId IS NULL OR c.bodyStyle.bodyStyleId = :bodyStyleId) " +
           "AND (:transmissionId IS NULL OR c.transmission.transmissionId = :transmissionId) " +
           "AND (:energySourceId IS NULL OR c.energySource.energySourceId = :energySourceId)")
    List<CarModel> filterModels(Integer brandId, Integer bodyStyleId, Integer transmissionId, Integer energySourceId);
} 