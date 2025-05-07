package com.example.backend.repository;

import com.example.backend.model.EnergySource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnergySourceRepository extends JpaRepository<EnergySource, Integer> {
    
    Optional<EnergySource> findByEnergySourceName(String energySourceName);
    
    List<EnergySource> findByEnergySourceNameContainingIgnoreCase(String keyword);
    
    boolean existsByEnergySourceName(String energySourceName);
} 