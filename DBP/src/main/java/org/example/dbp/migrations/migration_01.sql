create table User
(
    id           int primary key auto_increment,
    name         varchar(30),
    role         varchar(12)  not null,
    email        varchar(45) unique,
    hire_date    date,
    phone_number varchar(10),
    password     varchar(100) NOT NULL,
    salary       real         NOT NULL
);

create table Customer
(
    id            int primary key auto_increment,
    customer_name varchar(25) NOT NULL,
    phone_number  varchar(10)
);

create table Order_table
(
    id                int primary key auto_increment,
    created_date_time datetime,
    cashier_id        int,
    customer_id       int,
    foreign key (cashier_id) references User (id),
    foreign key (customer_id) references Customer (id)
);

create table category
(
    id   int primary key auto_increment,
    name varchar(30) NOT NULL unique
);

create table Menu_item
(
    id          int primary key auto_increment,
    item_name   varchar(30) NOT NULL UNIQUE,
    price       real        not null,
    category_id int,
    foreign key (category_id) references category (id) ON DELETE CASCADE
);

create table Order_Line
(
    order_id         int,
    menu_item_id     int,
    ordered_Quantity int,
    primary key (order_id, menu_item_id),
    foreign key (order_id) references Order_table (id),
    foreign key (menu_item_id) references menu_item (id) ON DELETE CASCADE
);

create table Inventory
(
    id       int primary key auto_increment,
    name     varchar(20),
    admin_id int,
    foreign key (admin_id) references User (id)
);

CREATE TABLE Purchase_Order
(
    id           int primary key auto_increment,
    date         DATE NOT NULL,
    total_amount real,
    inventory_id int,
    FOREIGN KEY (inventory_id) REFERENCES Inventory (id)
);

create table Inventory_item
(
    id              int primary key auto_increment,
    name            varchar(20) NOT NULL UNIQUE,
    Quantity        real,
    production_date date,
    expiry_date     date,
    inventory_id    int,
    foreign key (inventory_id) references Inventory (id)
);

CREATE TABLE Inventory_Order_Line
(
    id                INT PRIMARY KEY AUTO_INCREMENT,
    purchase_order_id INT,
    inventory_item_id INT,
    quantity          INT NOT NULL,
    unit_price        REAL,
    FOREIGN KEY (purchase_order_id) REFERENCES Purchase_Order (id),
    FOREIGN KEY (inventory_item_id) REFERENCES Inventory_item (id)
);


create table Invoice
(
    id                int primary key auto_increment,
    created_date_time datetime,
    amount            real,
    cashier_id        int,
    order_id          int,
    foreign key (cashier_id) references User (id),
    foreign key (order_id) references Order_table (id)
);



insert into category (name)
values ('Hot Drinks')
     , ('Cold Drinks')
     , ('Ice')
     , ('Mohito')
     , ('Milk Chick')
     , ('Smoothies');

insert into menu_item(item_name, price, category_id)
values ('Espresso', 10, 1),
       ('Americano', 12, 1),
       ('Cappuccino', 16, 1),
       ('Nescafe', 16, 1),
       ('Cafe latte', 16, 1),
       ('Spanish latte', 16, 1),
       ('Hot chocolate', 16, 1),
       ('Tea latte', 16, 1),
       ('Hazelnut latte ', 16, 1),
       ('Fresh Vanilla', 16, 1),
       ('Caramel', 16, 1),
       ('Sahlab', 12, 1),
       ('Arabic coffee', 12, 1),
       ('Mint tea ', 12, 1),
       ('Green tea', 12, 1),
       ('Ginger,cinnamon and honey', 16, 1);

insert into menu_item(item_name, price, category_id)
values ('Orange juice', 17, 2),
       ('Lemon and mint', 17, 2),
       ('Watermelon', 17, 2),
       ('Mango and orange', 18, 2),
       ('Mango and banana ', 18, 2),
       ('Banana and milk', 18, 2),
       ('Strawberry', 18, 2);


insert into menu_item (item_name, price, category_id)
values ('Ice tea', 18, 3),
       ('Ice latte', 17, 3),
       ('Ice coffee', 18, 3),
       ('Ice mocha', 18, 3),
       ('Ice tea latte', 18, 3),
       ('Ice hazelnut latte', 18, 3),
       ('Ice spanish latte', 18, 3),
       ('Ice fresh vanilla latte', 18, 3),
       ('Ice caramel latte', 18, 3);


insert into menu_item (item_name, price, category_id)
values ('Blueberry', 18, 5),
       ('Oreo', 17, 5),
       ('chocolate', 18, 5),
       ('Strawberry and Blueberry', 18, 5),
       ('Mix berry', 18, 5),
       ('Vanilla', 16, 5),
       ('Lotus', 18, 5),
       ('snickers', 18, 5);

insert into menu_item (item_name, price, category_id)
values ('Gengar', 17, 4),
       ('Strawberry mohito ', 17, 4),
       ('raspberry', 17, 4),
       ('Mesflora ', 18, 4),
       ('Mix berry mohito', 17, 4),
       ('Blackberry', 17, 4),
       ('kewe', 17, 4);


insert into menu_item (item_name, price, category_id)
values ('Mesflora smoothie', 18, 6),
       ('pineapple ', 18, 6),
       ('Strawberry  smoothie', 20, 6),
       ('Mango smoothie', 20, 6);



insert into user(name, role, email, hire_date, phone_number, password, salary)
values ('Ayman', 'Admin', 'nabilA02@gmail.com', '2023-12-12', '0594276', 122, 3600),
       ('sam', 'cashier', 'sam@gmail.com', '2023-12-12', '0594276', 122, 3200);

INSERT INTO Inventory (name, admin_id)
VALUES ('Main Inventory', 1);

INSERT INTO Purchase_Order (date, total_amount, inventory_id)
VALUES ('2024-01-01', 500.00, 1),
       ('2024-01-02', 150.00, 1),
       ('2024-01-03', 200.00, 1),
       ('2024-01-04', 800.00, 1),
       ('2024-01-05', 350.00, 1),
       ('2024-01-06', 120.00, 1),
       ('2024-01-07', 600.00, 1),
       ('2024-01-08', 950.00, 1),
       ('2024-01-09', 400.00, 1),
       ('2024-01-10', 720.00, 1),
       ('2024-01-11', 850.00, 1),
       ('2024-01-12', 320.00, 1),
       ('2024-01-13', 100.00, 1),
       ('2024-01-14', 450.00, 1),
       ('2024-01-15', 530.00, 1);


INSERT INTO Inventory_item (name, Quantity, production_date, expiry_date, inventory_id)
VALUES ('Coke', 100, '2024-01-01', '2025-01-01', 1),
       ('Pepsi', 200, '2024-01-02', '2025-01-02', 1),
       ('Chips', 300, '2024-01-03', '2025-01-03', 1),
       ('Chocolate', 150, '2024-01-04', '2025-01-04', 1),
       ('Ice Cream', 50, '2024-01-05', '2025-01-05', 1),
       ('Apples', 500, '2024-01-06', '2025-01-06', 1),
       ('Carrots', 400, '2024-01-07', '2025-01-07', 1),
       ('Sofa', 20, '2024-01-08', NULL, 1),
       ('Shirt', 60, '2024-01-09', NULL, 1),
       ('Notebook', 300, '2024-01-10', '2025-01-10', 1),
       ('Pen', 1000, '2024-01-11', '2026-01-11', 1),
       ('Hammer', 50, '2024-01-12', NULL, 1),
       ('Dog Food', 80, '2024-01-13', '2025-01-13', 1),
       ('Cat Litter', 40, '2024-01-14', '2025-01-14', 1),
       ('Cake', 120, '2024-01-15', '2024-01-30', 1);

INSERT INTO Inventory_Order_Line (purchase_order_id, inventory_item_id, quantity, unit_price)
VALUES (1, 1, 10, 2.50),
       (1, 2, 5, 2.00),
       (2, 3, 20, 1.50),
       (3, 4, 15, 3.00),
       (4, 5, 8, 5.00),
       (5, 6, 50, 1.00),
       (6, 7, 40, 1.20),
       (7, 8, 2, 200.00),
       (8, 9, 5, 25.00),
       (9, 10, 10, 3.00),
       (10, 11, 30, 0.50),
       (11, 12, 5, 15.00),
       (12, 13, 8, 10.00),
       (13, 14, 4, 12.00),
       (14, 15, 20, 6.00);


-- Users (Admins and Cashiers)
INSERT INTO User (name, role, email, hire_date, phone_number, password, salary)
VALUES ('John Doe', 'Cashier', 'john.doe@email.com', '2023-05-01', '1234567890', 'hashedpassword1', 2500.0),
       ('Jane Smith', 'Cashier', 'jane.smith@email.com', '2023-06-02', '1234567891', 'hashedpassword2', 2700.0),
       ('Mark Johnson', 'Cashier', 'mark.johnson@email.com', '2023-07-01', '1234567892', 'hashedpassword3', 2600.0),
       ('Emily Davis', 'Cashier', 'emily.davis@email.com', '2023-07-15', '1234567893', 'hashedpassword4', 2800.0),
       ('Michael Brown', 'Cashier', 'michael.brown@email.com', '2023-08-01', '1234567894', 'hashedpassword5', 2500.0),
       ('Sarah Miller', 'Cashier', 'sarah.miller@email.com', '2023-08-15', '1234567895', 'hashedpassword6', 2650.0),
       ('David Wilson', 'Cashier', 'david.wilson@email.com', '2023-09-01', '1234567896', 'hashedpassword7', 2900.0),
       ('Olivia White', 'Cashier', 'olivia.white@email.com', '2023-09-15', '1234567897', 'hashedpassword8', 2750.0),
       ('James Taylor', 'Cashier', 'james.taylor@email.com', '2023-10-01', '1234567898', 'hashedpassword9', 2600.0),
       ('Lily Harris', 'Cashier', 'lily.harris@email.com', '2023-10-10', '1234567899', 'hashedpassword10', 2800.0),
       ('Daniel Clark', 'Cashier', 'daniel.clark@email.com', '2023-11-01', '1234567900', 'hashedpassword11', 2700.0),
       ('Sophie Lewis', 'Cashier', 'sophie.lewis@email.com', '2023-11-10', '1234567901', 'hashedpassword12', 2650.0),
       ('Aaron Walker', 'Cashier', 'aaron.walker@email.com', '2023-12-01', '1234567902', 'hashedpassword13', 2500.0),
       ('Zoe Scott', 'Cashier', 'zoe.scott@email.com', '2023-12-05', '1234567903', 'hashedpassword14', 2750.0),
       ('Chris Adams', 'Cashier', 'chris.adams@email.com', '2023-12-15', '1234567904', 'hashedpassword15', 2700.0),
       ('Grace Parker', 'Cashier', 'grace.parker@email.com', '2023-12-20', '1234567905', 'hashedpassword16', 2650.0),
       ('Tom Mitchell', 'Admin', 'tom.mitchell@email.com', '2024-01-01', '1234567906', 'hashedpassword17', 3500.0),
       ('Ella Perez', 'Admin', 'ella.perez@email.com', '2024-01-10', '1234567907', 'hashedpassword18', 3600.0),
       ('Lucas Gonzalez', 'Admin', 'lucas.gonzalez@email.com', '2024-01-15', '1234567908', 'hashedpassword19', 3700.0),
       ('Charlotte Nelson', 'Admin', 'charlotte.nelson@email.com', '2024-02-01', '1234567909', 'hashedpassword20',
        3800.0),
       ('Isaac Roberts', 'Admin', 'isaac.roberts@email.com', '2024-02-10', '1234567910', 'hashedpassword21', 3900.0),
       ('Hannah Carter', 'Admin', 'hannah.carter@email.com', '2024-03-01', '1234567911', 'hashedpassword22', 4000.0),
       ('Ethan Phillips', 'Admin', 'ethan.phillips@email.com', '2024-03-10', '1234567912', 'hashedpassword23', 4200.0),
       ('Mason Cooper', 'Admin', 'mason.cooper@email.com', '2024-03-15', '1234567913', 'hashedpassword24', 4300.0),
       ('Chloe Evans', 'Admin', 'chloe.evans@email.com', '2024-03-20', '1234567914', 'hashedpassword25', 4400.0);


-- Customers
INSERT INTO Customer (customer_name, phone_number)
VALUES ('Alice Walker', '9876543210'),
       ('Bob Johnson', '9876543211'),
       ('Charlie Lee', '9876543212'),
       ('David Kim', '9876543213'),
       ('Eva White', '9876543214'),
       ('Fiona Green', '9876543215'),
       ('George Brown', '9876543216'),
       ('Hannah King', '9876543217'),
       ('Isaac Moore', '9876543218'),
       ('Julia Scott', '9876543219'),
       ('Katherine Harris', '9876543220'),
       ('Leo Turner', '9876543221'),
       ('Megan Clark', '9876543222'),
       ('Noah Davis', '9876543223'),
       ('Olivia Martinez', '9876543224'),
       ('Paul Young', '9876543225'),
       ('Quincy Allen', '9876543226'),
       ('Rachel Harris', '9876543227'),
       ('Sophia Adams', '9876543228'),
       ('Thomas Walker', '9876543229'),
       ('Uma Lewis', '9876543230'),
       ('Victor Wright', '9876543231'),
       ('Wendy Hall', '9876543232'),
       ('Xander Lee', '9876543233'),
       ('Yara Green', '9876543234'),
       ('Zachary Moore', '9876543235');

-- Orders (with varying dates in 2024)
INSERT INTO Order_table (created_date_time, cashier_id, customer_id)
VALUES ('2024-01-05 08:30:00', 1, 1),
       ('2024-01-10 09:00:00', 2, 2),
       ('2024-02-01 10:30:00', 3, 3),
       ('2024-02-03 11:00:00', 4, 4),
       ('2024-02-15 12:00:00', 5, 5),
       ('2024-03-01 13:30:00', 6, 6),
       ('2024-03-10 14:00:00', 7, 7),
       ('2024-04-01 10:30:00', 8, 8),
       ('2024-04-05 11:30:00', 9, 9),
       ('2024-04-15 15:00:00', 10, 10),
       ('2024-05-01 16:00:00', 11, 11),
       ('2024-05-10 17:00:00', 12, 12),
       ('2024-05-20 18:00:00', 13, 13),
       ('2024-06-01 08:45:00', 14, 14),
       ('2024-06-10 09:30:00', 15, 15),
       ('2024-06-15 10:00:00', 16, 16),
       ('2024-07-01 11:15:00', 17, 17),
       ('2024-07-05 12:15:00', 18, 18),
       ('2024-07-10 14:30:00', 19, 19),
       ('2024-08-01 15:00:00', 20, 20),
       ('2024-08-05 16:30:00', 21, 21),
       ('2024-08-15 17:30:00', 22, 22),
       ('2024-09-01 08:00:00', 23, 23),
       ('2024-09-10 09:15:00', 24, 24),
       ('2024-09-20 10:30:00', 25, 25);


-- Order Lines (each order has different menu items and quantities)
INSERT INTO Order_Line (order_id, menu_item_id, ordered_Quantity)
VALUES (1, 1, 2),
       (1, 2, 1),
       (2, 3, 3),
       (2, 4, 1),
       (3, 5, 2),
       (3, 6, 1),
       (4, 7, 2),
       (4, 8, 3),
       (5, 9, 1),
       (5, 10, 2),
       (6, 11, 2),
       (6, 12, 1),
       (7, 13, 3),
       (7, 14, 1),
       (8, 15, 2),
       (8, 16, 2),
       (9, 17, 1),
       (9, 18, 2),
       (10, 19, 1),
       (10, 20, 3),
       (11, 21, 2),
       (11, 22, 1),
       (12, 23, 3),
       (12, 24, 2),
       (13, 25, 1),
       (13, 26, 2),
       (14, 27, 2);


-- Invoices (related to orders)
INSERT INTO Invoice (created_date_time, amount, cashier_id, order_id)
VALUES ('2024-01-05 08:45:00', 50.00, 1, 1),
       ('2024-01-10 09:30:00', 60.00, 2, 2),
       ('2024-02-01 11:00:00', 90.00, 3, 3),
       ('2024-02-03 11:30:00', 80.00, 4, 4),
       ('2024-02-15 12:30:00', 120.00, 5, 5),
       ('2024-03-01 13:45:00', 75.00, 6, 6),
       ('2024-03-10 14:15:00', 95.00, 7, 7),
       ('2024-04-01 10:45:00', 110.00, 8, 8),
       ('2024-04-05 12:00:00', 105.00, 9, 9),
       ('2024-04-15 15:30:00', 135.00, 10, 10),
       ('2024-05-01 16:15:00', 80.00, 11, 11),
       ('2024-05-10 17:30:00', 85.00, 12, 12),
       ('2024-05-20 18:30:00', 95.00, 13, 13),
       ('2024-06-01 08:55:00', 70.00, 14, 14),
       ('2024-06-10 09:45:00', 75.00, 15, 15),
       ('2024-06-15 10:30:00', 120.00, 16, 16),
       ('2024-07-01 11:30:00', 100.00, 17, 17),
       ('2024-07-05 12:45:00', 115.00, 18, 18),
       ('2024-07-10 14:45:00', 125.00, 19, 19),
       ('2024-08-01 15:15:00', 90.00, 20, 20),
       ('2024-08-05 16:45:00', 105.00, 21, 21),
       ('2024-08-15 17:45:00', 110.00, 22, 22),
       ('2024-09-01 08:30:00', 130.00, 23, 23),
       ('2024-09-10 09:30:00', 120.00, 24, 24),
       ('2024-09-20 10:45:00', 115.00, 25, 25);



SELECT *
FROM User;
SELECT *
FROM customer;
SELECT *
FROM category;
SELECT *
FROM menu_item;
SELECT *
FROM order_table;
SELECT *
FROM order_line;
SELECT *
FROM invoice;
SELECT *
FROM inventory;
SELECT *
FROM inventory_item;
SELECT *
FROM purchase_order;
