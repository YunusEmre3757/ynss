package com.example.backend.controller;

import com.example.backend.model.PaymentInfo;
import com.example.backend.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment-info")
public class PaymentInfoController {
    
    private final PaymentInfoService paymentInfoService;
    
    @Autowired
    public PaymentInfoController(PaymentInfoService paymentInfoService) {
        this.paymentInfoService = paymentInfoService;
    }
    
    /**
     * Get all payment methods for a customer
     * @param customerId Customer ID
     * @return List of payment information
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PaymentInfo>> getCustomerPaymentMethods(@PathVariable Integer customerId) {
        try {
            List<PaymentInfo> paymentMethods = paymentInfoService.getCustomerPaymentMethods(customerId);
            return ResponseEntity.ok(paymentMethods);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Add a new payment method for a customer
     * @param customerId Customer ID
     * @param paymentInfo Payment information
     * @return Saved payment information
     */
    @PostMapping("/customer/{customerId}")
    public ResponseEntity<PaymentInfo> addPaymentMethod(
            @PathVariable Integer customerId,
            @RequestBody PaymentInfo paymentInfo) {
        try {
            PaymentInfo savedInfo = paymentInfoService.addPaymentMethod(customerId, paymentInfo);
            return ResponseEntity.ok(savedInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Update a payment method
     * @param paymentInfoId Payment info ID
     * @param paymentInfo Updated payment information
     * @return Updated payment information
     */
    @PutMapping("/{paymentInfoId}")
    public ResponseEntity<PaymentInfo> updatePaymentMethod(
            @PathVariable Integer paymentInfoId,
            @RequestBody PaymentInfo paymentInfo) {
        try {
            PaymentInfo updatedInfo = paymentInfoService.updatePaymentMethod(paymentInfoId, paymentInfo);
            return ResponseEntity.ok(updatedInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Delete a payment method
     * @param paymentInfoId Payment info ID
     * @return Success status
     */
    @DeleteMapping("/{paymentInfoId}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Integer paymentInfoId) {
        try {
            paymentInfoService.deletePaymentMethod(paymentInfoId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get a specific payment method
     * @param paymentInfoId Payment info ID
     * @return Payment information
     */
    @GetMapping("/{paymentInfoId}")
    public ResponseEntity<PaymentInfo> getPaymentMethod(@PathVariable Integer paymentInfoId) {
        try {
            PaymentInfo paymentInfo = paymentInfoService.getPaymentMethod(paymentInfoId);
            return ResponseEntity.ok(paymentInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get the default payment method for a customer
     * @param customerId Customer ID
     * @return Default payment information
     */
    @GetMapping("/customer/{customerId}/default")
    public ResponseEntity<PaymentInfo> getDefaultPaymentMethod(@PathVariable Integer customerId) {
        try {
            Optional<PaymentInfo> paymentInfo = paymentInfoService.getDefaultPaymentMethod(customerId);
            return paymentInfo.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 