-- Roles
INSERT INTO roles (name, description) VALUES ('USER', 'Regular user role');
INSERT INTO roles (name, description) VALUES ('ADMIN', 'Administrator role');

-- Admin user (password: admin123)
INSERT INTO customers (name, surname, email, password, is_active, created_at, updated_at) 
VALUES ('Admin', 'User', 'admin@example.com', '$2a$10$OuVZugrxUOllU8ZPG74qhO6FIGlb53mG6wVKfcfnZOQ4r.d8P7iRe', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Assign ADMIN role to admin user
INSERT INTO customer_roles (customer_id, role_id) VALUES (1, 2);

-- Initial body styles
INSERT INTO body_styles (body_style_name, created_at) VALUES ('Sedan', CURRENT_TIMESTAMP);
INSERT INTO body_styles (body_style_name, created_at) VALUES ('SUV', CURRENT_TIMESTAMP);
INSERT INTO body_styles (body_style_name, created_at) VALUES ('Hatchback', CURRENT_TIMESTAMP);
INSERT INTO body_styles (body_style_name, created_at) VALUES ('Coupe', CURRENT_TIMESTAMP);
INSERT INTO body_styles (body_style_name, created_at) VALUES ('Convertible', CURRENT_TIMESTAMP);

-- Initial transmissions
INSERT INTO transmissions (transmission_name, created_at) VALUES ('Manual', CURRENT_TIMESTAMP);
INSERT INTO transmissions (transmission_name, created_at) VALUES ('Automatic', CURRENT_TIMESTAMP);
INSERT INTO transmissions (transmission_name, created_at) VALUES ('CVT', CURRENT_TIMESTAMP);
INSERT INTO transmissions (transmission_name, created_at) VALUES ('Dual-Clutch', CURRENT_TIMESTAMP);

-- Initial energy sources
INSERT INTO energy_sources (energy_source_name, created_at) VALUES ('Petrol', CURRENT_TIMESTAMP);
INSERT INTO energy_sources (energy_source_name, created_at) VALUES ('Diesel', CURRENT_TIMESTAMP);
INSERT INTO energy_sources (energy_source_name, created_at) VALUES ('Hybrid', CURRENT_TIMESTAMP);
INSERT INTO energy_sources (energy_source_name, created_at) VALUES ('Electric', CURRENT_TIMESTAMP);

-- Initial brands
INSERT INTO brand (brand_name, slogan, created_at, updated_at) VALUES ('Toyota', 'Let''s Go Places', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO brand (brand_name, slogan, created_at, updated_at) VALUES ('Honda', 'The Power of Dreams', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO brand (brand_name, slogan, created_at, updated_at) VALUES ('BMW', 'The Ultimate Driving Machine', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO brand (brand_name, slogan, created_at, updated_at) VALUES ('Mercedes-Benz', 'The Best or Nothing', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Initial colors
INSERT INTO colors (color_name, finish_type, created_at) VALUES ('Black', 'Metallic', CURRENT_TIMESTAMP);
INSERT INTO colors (color_name, finish_type, created_at) VALUES ('White', 'Pearl', CURRENT_TIMESTAMP);
INSERT INTO colors (color_name, finish_type, created_at) VALUES ('Silver', 'Metallic', CURRENT_TIMESTAMP);
INSERT INTO colors (color_name, finish_type, created_at) VALUES ('Red', 'Solid', CURRENT_TIMESTAMP);
INSERT INTO colors (color_name, finish_type, created_at) VALUES ('Blue', 'Metallic', CURRENT_TIMESTAMP);

-- Initial package types
INSERT INTO package_types (package_type_name, description, created_at) VALUES ('Basic', 'Entry level package with essential features', CURRENT_TIMESTAMP);
INSERT INTO package_types (package_type_name, description, created_at) VALUES ('Comfort', 'Mid-range package with additional comfort features', CURRENT_TIMESTAMP);
INSERT INTO package_types (package_type_name, description, created_at) VALUES ('Premium', 'High-end package with luxury features', CURRENT_TIMESTAMP);
INSERT INTO package_types (package_type_name, description, created_at) VALUES ('Sport', 'Performance-oriented package with sport features', CURRENT_TIMESTAMP);

-- Order Status
INSERT INTO order_status (status_name, description) VALUES ('Pending', 'Order is pending processing');
INSERT INTO order_status (status_name, description) VALUES ('Processing', 'Order is being processed');
INSERT INTO order_status (status_name, description) VALUES ('Shipped', 'Order has been shipped');
INSERT INTO order_status (status_name, description) VALUES ('Delivered', 'Order has been delivered');
INSERT INTO order_status (status_name, description) VALUES ('Cancelled', 'Order has been cancelled');

-- Order Item Status
INSERT INTO order_item_status (status_name, description) VALUES ('Pending', 'Item is pending');
INSERT INTO order_item_status (status_name, description) VALUES ('Shipped', 'Item has been shipped');
INSERT INTO order_item_status (status_name, description) VALUES ('Delivered', 'Item has been delivered');
INSERT INTO order_item_status (status_name, description) VALUES ('Returned', 'Item has been returned');
INSERT INTO order_item_status (status_name, description) VALUES ('Cancelled', 'Item has been cancelled'); 