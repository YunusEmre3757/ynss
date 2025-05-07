package com.example.backend.controller;

import com.example.backend.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {
    
    private final CurrencyService currencyService;
    
    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
    
    /**
     * Get all available currencies and their exchange rates
     * @return Map of currency codes and rates
     */
    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getAllCurrencies() {
        return ResponseEntity.ok(currencyService.getAvailableCurrencies());
    }
    
    /**
     * Add a new currency
     * @param currencyCode Currency code
     * @param rate Exchange rate
     * @return Success message
     */
    @PostMapping
    public ResponseEntity<String> addCurrency(
            @RequestParam String currencyCode,
            @RequestParam BigDecimal rate) {
        try {
            currencyService.addCurrency(currencyCode, rate);
            return ResponseEntity.ok("Currency added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Update exchange rate for a currency
     * @param currencyCode Currency code
     * @param newRate New exchange rate
     * @return Success message
     */
    @PutMapping("/{currencyCode}")
    public ResponseEntity<String> updateCurrency(
            @PathVariable String currencyCode,
            @RequestParam BigDecimal newRate) {
        try {
            currencyService.updateExchangeRate(currencyCode, newRate);
            return ResponseEntity.ok("Currency rate updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Convert a price to a specific currency
     * @param amount Price amount
     * @param targetCurrency Target currency code
     * @return Converted price
     */
    @GetMapping("/convert")
    public ResponseEntity<?> convertPrice(
            @RequestParam BigDecimal amount,
            @RequestParam String targetCurrency) {
        try {
            BigDecimal convertedPrice = currencyService.convertPrice(amount, targetCurrency);
            return ResponseEntity.ok(Map.of(
                "originalAmount", amount,
                "convertedAmount", convertedPrice,
                "currencyCode", targetCurrency,
                "baseCurrency", currencyService.getBaseCurrency()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Get the base currency
     * @return Base currency code
     */
    @GetMapping("/base")
    public ResponseEntity<String> getBaseCurrency() {
        return ResponseEntity.ok(currencyService.getBaseCurrency());
    }
} 