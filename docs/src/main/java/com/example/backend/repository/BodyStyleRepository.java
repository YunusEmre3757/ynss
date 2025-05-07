package com.example.backend.repository;

import com.example.backend.model.BodyStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BodyStyleRepository extends JpaRepository<BodyStyle, Integer> {
    
    Optional<BodyStyle> findByBodyStyleName(String bodyStyleName);
    
    List<BodyStyle> findByBodyStyleNameContainingIgnoreCase(String keyword);
    
    boolean existsByBodyStyleName(String bodyStyleName);
} 