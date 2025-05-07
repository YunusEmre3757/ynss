package com.example.backend.service;

import com.example.backend.dto.AuthResponse;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.model.Customer;
import com.example.backend.model.Role;
import com.example.backend.repository.CustomerRepository;
import com.example.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(CustomerRepository customerRepository, 
                      RoleRepository roleRepository,
                      PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (customerRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Email already in use")
                    .build();
        }

        // Create customer entity
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setBirthDate(request.getBirthDate());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());
        customer.setIsActive(true);

        // Assign USER role
        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("USER");
                    newRole.setDescription("Regular user role");
                    return roleRepository.save(newRole);
                });

        customer.addRole(userRole);
        Customer savedCustomer = customerRepository.save(customer);

        return AuthResponse.builder()
                .userId(savedCustomer.getUserId())
                .email(savedCustomer.getEmail())
                .name(savedCustomer.getName())
                .surname(savedCustomer.getSurname())
                .roles(savedCustomer.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toList()))
                .success(true)
                .message("Registration successful")
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(request.getEmail());
        
        // Check if customer exists
        if (customerOptional.isEmpty()) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Invalid email or password")
                    .build();
        }
        
        Customer customer = customerOptional.get();
        
        // Check if password matches
        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Invalid email or password")
                    .build();
        }
        
        // Check if account is active
        if (!customer.getIsActive()) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Account is disabled")
                    .build();
        }
        
        return AuthResponse.builder()
                .userId(customer.getUserId())
                .email(customer.getEmail())
                .name(customer.getName())
                .surname(customer.getSurname())
                .roles(customer.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toList()))
                .success(true)
                .message("Login successful")
                .build();
    }
} 


