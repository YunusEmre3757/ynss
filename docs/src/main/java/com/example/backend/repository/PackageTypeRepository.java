package com.example.backend.repository;

import com.example.backend.model.PackageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageTypeRepository extends JpaRepository<PackageType, Integer> {
    
    Optional<PackageType> findByPackageTypeName(String packageTypeName);
    
    List<PackageType> findByPackageTypeNameContainingIgnoreCase(String keyword);
    
    boolean existsByPackageTypeName(String packageTypeName);
} 