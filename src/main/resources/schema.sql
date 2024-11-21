CREATE TABLE IF NOT EXISTS Customers (
    id SERIAL PRIMARY KEY,
    firstName VARCHAR(250) NOT NULL,
    lastName VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL UNIQUE,
    phone VARCHAR(250) NOT NULL,
    passkey VARCHAR(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS Employees (
    id SERIAL PRIMARY KEY,
    firstName VARCHAR(250) NOT NULL,
    lastName VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL UNIQUE,
    phone VARCHAR(250) NOT NULL,
    addr VARCHAR(250) NOT NULL,
    passkey VARCHAR(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS Menu (
    id SERIAL PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    description TEXT NOT NULL,
    price FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS Reviews (
    id SERIAL PRIMARY KEY,
    written_by INTEGER NOT NULL, -- customer id
    stars_given INTEGER NOT NULL,
    description TEXT NOT NULL,
    CONSTRAINT fk_customer_reviews FOREIGN KEY (written_by) REFERENCES Customers(id) ON DELETE CASCADE -- if customer deleted, all their reviews are deleted too
);


CREATE TABLE IF NOT EXISTS Orders (
    id SERIAL PRIMARY KEY,
    ordered_by INTEGER NOT NULL, -- customer id
    date_ordered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_price FLOAT NOT NULL,
    order_complete BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_customer_orders FOREIGN KEY (ordered_by) REFERENCES Customers(id) ON DELETE CASCADE -- if customer deleted, all their orders are deleted too
);

CREATE TABLE IF NOT EXISTS Reservations (
    id SERIAL PRIMARY KEY,
    reservation_time TIMESTAMP NOT NULL, -- combined date and time
    customer_id INTEGER REFERENCES Customers(id) ON DELETE CASCADE -- add this column and set a foreign key relationship
);


CREATE TABLE IF NOT EXISTS OrderItems (
    order_id INTEGER NOT NULL,
    item_id INTEGER NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_menu_item FOREIGN KEY (item_id) REFERENCES Menu(id) ON DELETE CASCADE
);


-- Insert sample data into Customers
INSERT INTO Customers (firstName, lastName, email, phone, passkey) VALUES
('John', 'Doe', 'john.doe@example.com', '123-456-7890', 'password123'),
('Jane', 'Smith', 'jane.smith@example.com', '234-567-8901', 'password456'),
('Alice', 'Johnson', 'alice.johnson@example.com', '345-678-9012', 'password789'),
('Bob', 'Williams', 'bob.williams@example.com', '456-789-0123', 'passwordabc'),
('Charlie', 'Brown', 'charlie.brown@example.com', '567-890-1234', 'passworddef');


-- Insert sample data into Employees
INSERT INTO Employees (firstName, lastName, email, phone, addr, passkey) VALUES
('Emma', 'Clark', 'emma.clark@example.com', '678-901-2345', '123 Main St', 'emmapass123'),
('Liam', 'Miller', 'liam.miller@example.com', '789-012-3456', '456 Oak St', 'liampass456'),
('Olivia', 'Garcia', 'olivia.garcia@example.com', '890-123-4567', '789 Pine St', 'oliviapass789'),
('Noah', 'Martinez', 'noah.martinez@example.com', '901-234-5678', '101 Elm St', 'noahpassabc'),
('Sophia', 'Taylor', 'sophia.taylor@example.com', '012-345-6789', '202 Maple St', 'sophiapassdef');


-- Insert sample data into Menu
INSERT INTO Menu (name, description, price) VALUES
('Bulgogi Beef Bowl', 'Marinated beef with rice, kimchi, and vegetables.', 12.99),
('Spicy Pork Bowl', 'Korean-style spicy pork with rice and kimchi.', 11.99),
('Chicken Teriyaki Bowl', 'Grilled chicken with teriyaki sauce, rice, and veggies.', 10.99),
('Tofu Veggie Bowl', 'Tofu with mixed vegetables, rice, and spicy gochujang sauce.', 9.99),
('Bibimbap', 'Traditional Korean mixed rice bowl with assorted veggies and fried egg.', 13.99),
('Kimchi Fried Rice', 'Fried rice with kimchi, vegetables, and choice of protein.', 9.49),
('Korean BBQ Short Rib Bowl', 'Grilled short ribs with rice, kimchi, and vegetables.', 15.99),
('Honey Garlic Shrimp Bowl', 'Shrimp with honey garlic sauce over rice.', 13.49),
('Spicy Tofu Bowl', 'Crispy tofu with spicy sauce, rice, and veggies.', 10.49),
('Korean Fried Chicken Bowl', 'Korean-style fried chicken with rice and pickled radish.', 12.49);


-- Insert sample data into Reviews
INSERT INTO Reviews (written_by, stars_given, description) VALUES
(1, 5, 'The Margherita Pizza was amazing! Fresh ingredients and perfect crust.'),
(2, 4, 'I enjoyed the Caesar Salad, but it could use more dressing.'),
(3, 5, 'Spaghetti Bolognese is my favorite dish here. Very filling and delicious.'),
(4, 3, 'The Cheeseburger was decent, but a bit too greasy for my taste.'),
(5, 4, 'Grilled Chicken Sandwich was very tasty and healthy.');

-- Insert sample data into Orders
INSERT INTO Orders (ordered_by, date_ordered, total_price, order_complete) VALUES
(1, '2024-10-31', 25.98, TRUE),
(2, '2024-10-31', 15.99, FALSE),
(3, '2024-10-31', 30.47, TRUE),
(4, '2024-10-31', 18.49, TRUE),
(5, '2024-11-01', 26.98, TRUE),
(1, '2024-11-01', 13.99, FALSE),
(2, '2024-11-01', 29.98, TRUE),
(3, '2024-11-01', 19.99, TRUE),
(4, '2024-11-02', 11.49, TRUE),
(5, '2024-11-02', 15.99, FALSE),
(1, '2024-11-02', 23.49, TRUE),
(2, '2024-11-02', 27.98, TRUE),
(3, '2024-11-03', 18.99, FALSE),
(4, '2024-11-03', 21.99, TRUE),
(5, '2024-11-03', 24.49, TRUE),
(1, '2024-11-03', 16.99, FALSE),
(2, '2024-11-04', 22.49, TRUE),
(3, '2024-11-04', 29.98, TRUE),
(4, '2024-11-04', 17.49, FALSE);

-- Insert sample data into OrderItems
INSERT INTO OrderItems (order_id, item_id) VALUES
(1, 1), (1, 3),
(2, 2), (2, 5),
(3, 1), (3, 4), (3, 6),
(4, 7), (4, 8),
(5, 5), (5, 10),
(6, 3), (6, 7),
(7, 6), (7, 9),
(8, 2), (8, 8),
(9, 4), (9, 10),
(10, 1), (10, 3), (10, 9),
(11, 8), (11, 10),
(12, 2), (12, 5), (12, 7),
(13, 4), (13, 6),
(14, 3), (14, 9), (14, 10),
(15, 1), (15, 2), (15, 8),
(16, 5), (16, 7),
(17, 6), (17, 9), (17, 10),
(18, 1), (18, 3),
(19, 8), (19, 4);

-- Insert sample data into Reservations 
INSERT INTO Reservations (reservation_time, customer_id) VALUES
('2024-10-27 19:00:00', 1),
('2024-10-28 20:00:00', 2),
('2024-10-30 19:00:00', 3),
('2024-10-31 21:00:00', 4);
