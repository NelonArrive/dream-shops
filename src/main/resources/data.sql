-- CATEGORIES
INSERT INTO category (name)
VALUES ('Electronics'),
       ('Clothing'),
       ('Shoes'),
       ('Books'),
       ('Home');

-- PRODUCTS
INSERT INTO product (name, brand, price, inventory, description, category_id)
VALUES ('iPhone 15', 'Apple', 999.99, 50, 'Latest Apple smartphone', 1),
       ('Galaxy S24', 'Samsung', 899.99, 40, 'Flagship Samsung phone', 1),
       ('MacBook Air', 'Apple', 1299.99, 25, 'Lightweight laptop', 1),

       ('T-Shirt', 'Nike', 29.99, 100, 'Comfortable cotton t-shirt', 2),
       ('Jeans', 'Levis', 79.99, 60, 'Classic blue jeans', 2),

       ('Running Shoes', 'Adidas', 119.99, 45, 'Shoes for running', 3),
       ('Sneakers', 'Puma', 99.99, 55, 'Everyday sneakers', 3),

       ('Clean Code', 'Prentice Hall', 45.00, 30, 'Book about writing clean code', 4),
       ('Spring in Action', 'Manning', 55.00, 20, 'Spring Framework guide', 4),

       ('Coffee Maker', 'Philips', 149.99, 15, 'Automatic coffee machine', 5);
