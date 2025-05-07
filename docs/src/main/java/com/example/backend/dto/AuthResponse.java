package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    
    private Integer userId;
    private String email;
    private String name;
    private String surname;
    private List<String> roles;
    private boolean success;
    private String message;
} 