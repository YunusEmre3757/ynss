package com.example.backend.repository;

import com.example.backend.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    
    Optional<Color> findByColorName(String colorName);
    
    List<Color> findByColorNameContainingIgnoreCase(String keyword);
    
    List<Color> findByFinishType(String finishType);
    
    boolean existsByColorName(String colorName);
} 