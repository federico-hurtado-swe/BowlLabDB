CREATE TABLE IF NOT EXISTS Customers (
    id SERIAL PRIMARY KEY,
    firstName VARCHAR(250) NOT NULL,
    lastName VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL UNIQUE,
    phone VARCHAR(250) NOT NULL,
    passkey VARCHAR(250) NOT NULL,
    rewards_points INTEGER DEFAULT 0
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
    ingredients TEXT NOT NULL, -- unlimited characters
    description TEXT NOT NULL,
    price FLOAT NOT NULL,
    reward_value INTEGER NOT NULL
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
    total_reward_points INTEGER NOT NULL,
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

CREATE TABLE IF NOT EXISTS CustomerOrders (
    customer_id INTEGER NOT NULL,
    order_id INTEGER NOT NULL,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES Customers(id) ON DELETE CASCADE,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE
);
