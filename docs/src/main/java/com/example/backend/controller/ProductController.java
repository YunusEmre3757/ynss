package com.example.backend.controller;

import com.example.backend.model.*;
import com.example.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    private final ProductService productService;
    
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    /**
     * Get all active products
     * @return List of products
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllActiveProducts());
    }
    
    /**
     * Get product with price in specific currency
     * @param productId Product ID
     * @param currencyCode Currency code
     * @return Product with price in target currency
     */
    @GetMapping("/{productId}/currency")
    public ResponseEntity<?> getProductWithCurrency(
            @PathVariable Integer productId,
            @RequestParam(defaultValue = "USD") String currencyCode) {
        try {
            Map<String, Object> result = productService.getProductWithCurrency(productId, currencyCode);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Find all customers who ordered a specific product
     * @param productId Product ID
     * @return List of customers
     */
    @GetMapping("/{productId}/customers")
    public ResponseEntity<?> getCustomersWhoOrderedProduct(@PathVariable Integer productId) {
        try {
            List<Customer> customers = productService.getCustomersWhoOrderedProduct(productId);
            
            // Project to simplified objects to avoid circular references
            List<Map<String, Object>> simplifiedCustomers = customers.stream()
                    .map(c -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("userId", c.getUserId());
                        map.put("name", c.getName());
                        map.put("surname", c.getSurname());
                        map.put("email", c.getEmail());
                        return map;
                    })
                    .collect(Collectors.toList());
                    
            return ResponseEntity.ok(simplifiedCustomers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Update product stock
     * @param productId Product ID
     * @param newStock New stock value
     * @return Updated product
     */
    @PutMapping("/{productId}/stock")
    public ResponseEntity<?> updateProductStock(
            @PathVariable Integer productId,
            @RequestParam Integer newStock) {
        try {
            Product updatedProduct = productService.updateProductStock(productId, newStock);
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Add a new car model
     * @param carModel Car model
     * @return Saved car model
     */
    @PostMapping("/models")
    public ResponseEntity<CarModel> addCarModel(@RequestBody CarModel carModel) {
        return ResponseEntity.ok(productService.addCarModel(carModel));
    }
    
    /**
     * Update product price and record history
     * @param productId Product ID
     * @param newPrice New price
     * @param currencyCode Currency code
     * @param changedBy User who made the change
     * @return Updated product
     */
    @PutMapping("/{productId}/price")
    public ResponseEntity<?> updateProductPrice(
            @PathVariable Integer productId,
            @RequestParam BigDecimal newPrice,
            @RequestParam(defaultValue = "USD") String currencyCode,
            @RequestParam(required = false) String changedBy) {
        try {
            Product updatedProduct = productService.updateProductPrice(
                    productId, newPrice, currencyCode, changedBy);
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Remove a product and all its comments
     * @param productId Product ID
     * @return Success message
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable Integer productId) {
        try {
            productService.removeProduct(productId);
            return ResponseEntity.ok("Product and all its comments deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Get car model inventory summary
     * @return Map of car model to total stock
     */
    @GetMapping("/models/inventory")
    public ResponseEntity<Map<String, Object>> getCarModelInventorySummary() {
        Map<CarModel, Integer> inventory = productService.getCarModelInventorySummary();
        
        // Convert to a simpler representation to avoid circular references
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<CarModel, Integer> entry : inventory.entrySet()) {
            Map<String, Object> modelInfo = new HashMap<>();
            modelInfo.put("modelId", entry.getKey().getModelId());
            modelInfo.put("modelName", entry.getKey().getModelName());
            modelInfo.put("brandName", entry.getKey().getBrand().getBrandName());
            modelInfo.put("totalStock", entry.getValue());
            
            result.put(entry.getKey().getModelName(), modelInfo);
        }
        
        return ResponseEntity.ok(result);
    }
    
    /**
     * Add a new product
     * @param product Product
     * @return Saved product
     */
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }
    
    /**
     * Get product price history
     * @param productId Product ID
     * @return List of price history records
     */
    @GetMapping("/{productId}/price-history")
    public ResponseEntity<?> getProductPriceHistory(@PathVariable Integer productId) {
        try {
            List<PriceHistory> priceHistory = productService.getProductPriceHistory(productId);
            return ResponseEntity.ok(priceHistory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 