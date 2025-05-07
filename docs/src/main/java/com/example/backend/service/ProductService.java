package com.example.backend.service;

import com.example.backend.model.*;
import com.example.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CarModelRepository carModelRepository;
    private final ColorRepository colorRepository;
    private final PackageTypeRepository packageTypeRepository;
    private final CommentRepository commentRepository;
    private final PriceHistoryRepository priceHistoryRepository;
    private final CurrencyService currencyService;
    
    @Autowired
    public ProductService(
            ProductRepository productRepository,
            CarModelRepository carModelRepository,
            ColorRepository colorRepository,
            PackageTypeRepository packageTypeRepository,
            CommentRepository commentRepository,
            PriceHistoryRepository priceHistoryRepository,
            CurrencyService currencyService) {
        this.productRepository = productRepository;
        this.carModelRepository = carModelRepository;
        this.colorRepository = colorRepository;
        this.packageTypeRepository = packageTypeRepository;
        this.commentRepository = commentRepository;
        this.priceHistoryRepository = priceHistoryRepository;
        this.currencyService = currencyService;
    }
    
    /**
     * Get all active products
     * @return List of products
     */
    public List<Product> getAllActiveProducts() {
        return productRepository.findByIsActiveTrue();
    }
    
    /**
     * Get product with price in specific currency
     * @param productId Product ID
     * @param currencyCode Currency code
     * @return Product with price in target currency
     */
    public Map<String, Object> getProductWithCurrency(Integer productId, String currencyCode) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        
        BigDecimal convertedPrice = currencyService.convertPrice(product.getPrice(), currencyCode);
        
        Map<String, Object> result = new HashMap<>();
        result.put("product", product);
        result.put("convertedPrice", convertedPrice);
        result.put("currencyCode", currencyCode);
        
        return result;
    }
    
    /**
     * Find all customers who ordered a specific product
     * @param productId Product ID
     * @return List of customers
     */
    public List<Customer> getCustomersWhoOrderedProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        
        // Get all orders containing this product
        List<OrderItem> orderItems = product.getOrderItems();
        
        // Extract unique customers from these orders
        return orderItems.stream()
                .map(item -> item.getOrder().getCustomer())
                .distinct()
                .collect(Collectors.toList());
    }
    
    /**
     * Update product stock
     * @param productId Product ID
     * @param newStock New stock value
     * @return Updated product
     */
    @Transactional
    public Product updateProductStock(Integer productId, Integer newStock) {
        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                
        product.setStock(newStock);
        return productRepository.save(product);
    }
    
    /**
     * Add a new car model
     * @param carModel Car model
     * @return Saved car model
     */
    @Transactional
    public CarModel addCarModel(CarModel carModel) {
        return carModelRepository.save(carModel);
    }
    
    /**
     * Update product price and record history
     * @param productId Product ID
     * @param newPrice New price
     * @param currencyCode Currency code
     * @param changedBy User who made the change
     * @return Updated product
     */
    @Transactional
    public Product updateProductPrice(Integer productId, BigDecimal newPrice, 
                                     String currencyCode, String changedBy) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                
        BigDecimal oldPrice = product.getPrice();
        
        // Convert price if currency is not base currency
        if (!currencyCode.equals(currencyService.getBaseCurrency())) {
            // Convert from provided currency to base currency (USD)
            BigDecimal exchangeRate = currencyService.getAvailableCurrencies().get(currencyCode);
            if (exchangeRate == null) {
                throw new IllegalArgumentException("Invalid currency code");
            }
            newPrice = newPrice.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP);
        }
        
        // Create price history record
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setProduct(product);
        priceHistory.setOldPrice(oldPrice);
        priceHistory.setNewPrice(newPrice);
        priceHistory.setCurrencyCode(currencyService.getBaseCurrency());
        priceHistory.setChangedBy(changedBy);
        priceHistoryRepository.save(priceHistory);
        
        // Update product price
        product.setPrice(newPrice);
        return productRepository.save(product);
    }
    
    /**
     * Remove a product and all its comments
     * @param productId Product ID
     */
    @Transactional
    public void removeProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        
        // Delete comments first (they have ON DELETE CASCADE)
        commentRepository.deleteAll(product.getComments());
        
        // Delete product
        productRepository.delete(product);
    }
    
    /**
     * Get car model inventory summary
     * @return Map of car model to total stock
     */
    public Map<CarModel, Integer> getCarModelInventorySummary() {
        List<CarModel> carModels = carModelRepository.findByIsActiveTrue();
        Map<CarModel, Integer> inventory = new HashMap<>();
        
        for (CarModel model : carModels) {
            // Get all products for this model
            List<Product> products = productRepository.findByModel(model);
            
            // Sum up stock quantities
            int totalStock = products.stream()
                    .filter(Product::getIsActive)
                    .mapToInt(Product::getStock)
                    .sum();
                    
            inventory.put(model, totalStock);
        }
        
        return inventory;
    }
    
    /**
     * Add a new product
     * @param product Product
     * @return Saved product
     */
    @Transactional
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
    
    /**
     * Get product price history
     * @param productId Product ID
     * @return List of price history records
     */
    public List<PriceHistory> getProductPriceHistory(Integer productId) {
        return priceHistoryRepository.findPriceHistoryByProductId(productId);
    }
} 