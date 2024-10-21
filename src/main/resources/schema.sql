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
    reserved_by INTEGER NOT NULL, -- customer id
    CONSTRAINT fk_customer_reservations FOREIGN KEY (reserved_by) REFERENCES Customers(id) ON DELETE CASCADE
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
('Margherita Pizza', 'Classic Italian pizza with fresh ingredients.', 12.99),
('Caesar Salad', 'Crisp romaine with a traditional Caesar dressing.', 8.99),
('Spaghetti Bolognese', 'A hearty Italian pasta dish.', 14.99),
('Cheeseburger', 'Juicy cheeseburger with fresh toppings.', 10.99),
('Grilled Chicken Sandwich', 'Healthy grilled chicken sandwich.', 11.99);

-- Insert sample data into Reviews
INSERT INTO Reviews (written_by, stars_given, description) VALUES
(1, 5, 'The Margherita Pizza was amazing! Fresh ingredients and perfect crust.'),
(2, 4, 'I enjoyed the Caesar Salad, but it could use more dressing.'),
(3, 5, 'Spaghetti Bolognese is my favorite dish here. Very filling and delicious.'),
(4, 3, 'The Cheeseburger was decent, but a bit too greasy for my taste.'),
(5, 4, 'Grilled Chicken Sandwich was very tasty and healthy.');

-- Insert sample data into Orders
INSERT INTO Orders (ordered_by, total_price, order_complete) VALUES
(1, 24.99, TRUE),
(2, 8.99, TRUE),
(3, 14.99, FALSE),
(4, 10.99, TRUE),
(5, 11.99, FALSE);

-- Insert sample data into OrderItems
INSERT INTO OrderItems (order_id, item_id) VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 3),
(4, 4),
(5, 5);


-- Insert sample data into Reservations
INSERT INTO Reservations (reservation_time, reserved_by) VALUES
('2024-10-17 19:00:00', 1), -- Reservation for customer with ID 1
('2024-10-18 20:30:00', 2), -- Reservation for customer with ID 2
('2024-10-19 18:00:00', 3), -- Reservation for customer with ID 3
('2024-10-20 21:00:00', 4), -- Reservation for customer with ID 4
('2024-10-21 19:30:00', 5); -- Reservation for customer with ID 5
