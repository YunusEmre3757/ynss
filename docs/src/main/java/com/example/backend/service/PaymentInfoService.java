package com.example.backend.service;

import com.example.backend.model.Customer;
import com.example.backend.model.PaymentInfo;
import com.example.backend.repository.CustomerRepository;
import com.example.backend.repository.PaymentInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentInfoService {
    
    private final PaymentInfoRepository paymentInfoRepository;
    private final CustomerRepository customerRepository;
    
    @Autowired
    public PaymentInfoService(PaymentInfoRepository paymentInfoRepository, CustomerRepository customerRepository) {
        this.paymentInfoRepository = paymentInfoRepository;
        this.customerRepository = customerRepository;
    }
    
    /**
     * Get all payment methods for a customer
     * @param customerId Customer ID
     * @return List of payment information
     */
    public List<PaymentInfo> getCustomerPaymentMethods(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
                
        return paymentInfoRepository.findByCustomerOrderByIsDefaultDesc(customer);
    }
    
    /**
     * Add a new payment method for a customer
     * @param customerId Customer ID
     * @param paymentInfo Payment information
     * @return Saved payment information
     */
    @Transactional
    public PaymentInfo addPaymentMethod(Integer customerId, PaymentInfo paymentInfo) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        
        paymentInfo.setCustomer(customer);
        
        // If this is the first payment method or set as default
        if (paymentInfo.getIsDefault()) {
            // Set all other payment methods as non-default
            paymentInfoRepository.findByCustomerAndIsDefaultTrue(customer)
                    .ifPresent(existingDefault -> {
                        existingDefault.setIsDefault(false);
                        paymentInfoRepository.save(existingDefault);
                    });
        } else if (paymentInfoRepository.findByCustomer(customer).isEmpty()) {
            // If this is the first payment method, set it as default
            paymentInfo.setIsDefault(true);
        }
        
        // For security, we might want to mask card number (keep only last 4 digits)
        if (paymentInfo.getCardNumber() != null && paymentInfo.getCardNumber().length() > 4) {
            String lastFourDigits = paymentInfo.getCardNumber()
                    .substring(paymentInfo.getCardNumber().length() - 4);
            paymentInfo.setCardNumber("****-****-****-" + lastFourDigits);
        }
        
        return paymentInfoRepository.save(paymentInfo);
    }
    


    /**
     * Update a payment method
     * @param paymentInfoId Payment info ID
     * @param updatedInfo Updated payment information
     * @return Updated payment information
     */
    @Transactional
    public PaymentInfo updatePaymentMethod(Integer paymentInfoId, PaymentInfo updatedInfo) {
        PaymentInfo existingInfo = paymentInfoRepository.findById(paymentInfoId)
                .orElseThrow(() -> new IllegalArgumentException("Payment information not found"));
                
        Customer customer = existingInfo.getCustomer();
        
        // Update fields
        if (updatedInfo.getBankName() != null) {
            existingInfo.setBankName(updatedInfo.getBankName());
        }
        
        if (updatedInfo.getAccountNumber() != null) {
            existingInfo.setAccountNumber(updatedInfo.getAccountNumber());
        }
        
        if (updatedInfo.getCardHolderName() != null) {
            existingInfo.setCardHolderName(updatedInfo.getCardHolderName());
        }
        
        if (updatedInfo.getExpiryDate() != null) {
            existingInfo.setExpiryDate(updatedInfo.getExpiryDate());
        }
        
        // Handle default payment method logic
        if (updatedInfo.getIsDefault() != null && updatedInfo.getIsDefault() 
                && !existingInfo.getIsDefault()) {
            // Set all other payment methods as non-default
            paymentInfoRepository.findByCustomerAndIsDefaultTrue(customer)
                    .ifPresent(oldDefault -> {
                        oldDefault.setIsDefault(false);
                        paymentInfoRepository.save(oldDefault);
                    });
            
            existingInfo.setIsDefault(true);
        }
        
        return paymentInfoRepository.save(existingInfo);
    }
    


    /**
     * Delete a payment method
     * @param paymentInfoId Payment info ID
     */
    @Transactional
    public void deletePaymentMethod(Integer paymentInfoId) {
        PaymentInfo paymentInfo = paymentInfoRepository.findById(paymentInfoId)
                .orElseThrow(() -> new IllegalArgumentException("Payment information not found"));
        
        // If this was the default payment method, select another one as default
        if (paymentInfo.getIsDefault()) {
            Customer customer = paymentInfo.getCustomer();
            List<PaymentInfo> otherPaymentMethods = paymentInfoRepository.findByCustomer(customer);
            otherPaymentMethods.remove(paymentInfo);
            
            if (!otherPaymentMethods.isEmpty()) {
                PaymentInfo newDefault = otherPaymentMethods.get(0);
                newDefault.setIsDefault(true);
                paymentInfoRepository.save(newDefault);
            }
        }
        
        paymentInfoRepository.delete(paymentInfo);
    }
    
    /**
     * Get a specific payment method
     * @param paymentInfoId Payment info ID
     * @return Payment information
     */
    public PaymentInfo getPaymentMethod(Integer paymentInfoId) {
        return paymentInfoRepository.findById(paymentInfoId)
                .orElseThrow(() -> new IllegalArgumentException("Payment information not found"));
    }
    


    /**
     * Get the default payment method for a customer
     * @param customerId Customer ID
     * @return Default payment information
     */
    public Optional<PaymentInfo> getDefaultPaymentMethod(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
                
        return paymentInfoRepository.findByCustomerAndIsDefaultTrue(customer);
    }

    
} 