package com.example.backend.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyService {
    
    // Exchange rates map (currency code -> rate against base currency)
    private Map<String, BigDecimal> exchangeRates;
    
    // Base currency (all rates are against this currency)
    private final String baseCurrency = "USD";
    
    public CurrencyService() {
        initExchangeRates();
    }
    
    private void initExchangeRates() {
        exchangeRates = new HashMap<>();
        // Default exchange rates (example values)
        exchangeRates.put("USD", BigDecimal.ONE);
        exchangeRates.put("EUR", new BigDecimal("0.92"));
        exchangeRates.put("GBP", new BigDecimal("0.79"));
        exchangeRates.put("TRY", new BigDecimal("32.5"));
        exchangeRates.put("JPY", new BigDecimal("150.12"));
    }
    
    /**
     * Convert price from base currency to target currency
     * @param price Price in base currency (USD)
     * @param targetCurrency Target currency code
     * @return Converted price in target currency
     */
    public BigDecimal convertPrice(BigDecimal price, String targetCurrency) {
        if (price == null || targetCurrency == null) {
            throw new IllegalArgumentException("Price and target currency cannot be null");
        }
        
        // Get exchange rate for target currency
        BigDecimal rate = exchangeRates.getOrDefault(targetCurrency, BigDecimal.ONE);
        
        // Convert price
        return price.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Get all available currencies
     * @return Map of currency codes and their exchange rates
     */
    public Map<String, BigDecimal> getAvailableCurrencies() {
        return exchangeRates;
    }
    
    /**
     * Update exchange rate for a currency
     * @param currencyCode Currency code
     * @param newRate New exchange rate
     */
    public void updateExchangeRate(String currencyCode, BigDecimal newRate) {
        if (currencyCode == null || newRate == null || newRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid currency code or exchange rate");
        }
        
        exchangeRates.put(currencyCode, newRate);
    }
    
    /**
     * Add a new currency
     * @param currencyCode Currency code
     * @param rate Exchange rate
     */
    public void addCurrency(String currencyCode, BigDecimal rate) {
        if (currencyCode == null || rate == null || rate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid currency code or exchange rate");
        }
        
        if (exchangeRates.containsKey(currencyCode)) {
            throw new IllegalArgumentException("Currency already exists");
        }
        
        exchangeRates.put(currencyCode, rate);
    }
    
    /**
     * Get base currency
     * @return Base currency code
     */
    public String getBaseCurrency() {
        return baseCurrency;
    }
} 