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

INSERT INTO User (name, role, email, hire_date, phone_number, password, salary)
VALUES ('John Doe', 'admin', 'john.doe@example.com', '2024-01-01', '1234567890', 'password123', 5000),
       ('Jane Smith', 'admin', 'jane.smith@example.com', '2024-01-02', '0987654321', 'password123', 5000),
       ('Alice Johnson', 'cashier', 'alice.johnson@example.com', '2024-02-01', '2345678901', 'password123', 3000),
       ('Bob Brown', 'cashier', 'bob.brown@example.com', '2024-03-01', '3456789012', 'password123', 3000),
       ('Charlie White', 'cashier', 'charlie.white@example.com', '2024-04-01', '4567890123', 'password123', 3000),
       ('Diana Green', 'cashier', 'diana.green@example.com', '2024-05-01', '5678901234', 'password123', 3000),
       ('Ethan Black', 'cashier', 'ethan.black@example.com', '2024-06-01', '6789012345', 'password123', 3000),
       ('Liam Carter', 'admin', 'liam.carter@example.com', '2024-07-01', '7890123456', 'password123', 5000),
       ('Sophia Taylor', 'admin', 'sophia.taylor@example.com', '2024-08-01', '8901234567', 'password123', 5000),
       ('Isabella Harris', 'cashier', 'isabella.harris@example.com', '2024-09-01', '9012345678', 'password123', 3000),
       ('Mason Turner', 'cashier', 'mason.turner@example.com', '2024-10-01', '9123456789', 'password123', 3000),
       ('Ava Lopez', 'cashier', 'ava.lopez@example.com', '2024-11-01', '9234567890', 'password123', 3000),
       ('Lucas Walker', 'admin', 'lucas.walker@example.com', '2024-12-01', '9345678901', 'password123', 5000),
       ('Ayman', 'admin', 'Ayman@gmail.com', '2024-01-01', '1234567890', '1220040', 5000);



INSERT INTO Customer (customer_name, phone_number)
VALUES ('Michael Davis', '1111111111'),
       ('Emily Garcia', '2222222222'),
       ('Daniel Harris', '3333333333'),
       ('Sophia Martinez', '4444444444'),
       ('Matthew Clark', '5555555555'),
       ('Olivia Rodriguez', '6666666666'),
       ('Lucas Lewis', '7777777777'),
       ('Emma Walker', '8888888888'),
       ('Jack Allen', '9999999999'),
       ('Chloe Hall', '1010101010'),
       ('Henry Young', '1212121212'),
       ('Ella King', '1313131313'),
       ('Alexander Wright', '1414141414'),
       ('Amelia Scott', '1515151515'),
       ('William Turner', '1616161616'),
       ('Isabella Hill', '1717171717'),
       ('Benjamin Moore', '1818181818'),
       ('Mia Baker', '1919191919'),
       ('James Carter', '2020202020'),
       ('Charlotte Adams', '2121212121'),
       ('Jacob Nelson', '2222222222'),
       ('Lily Torres', '2323232323'),
       ('Elijah Lee', '2424242424'),
       ('Sophie Ramirez', '2525252525'),
       ('Mason Perez', '2626262626'),
       ('Grace Collins', '2727272727'),
       ('Logan Murphy', '2828282828'),
       ('Hannah Barnes', '2929292929'),
       ('Sebastian Reed', '3030303030'),
       ('Zoe Sanders', '3131313131'),
       ('Noah Foster', '3232323232'),
       ('Ava Brooks', '3333333333'),
       ('Ethan Bennett', '3434343434'),
       ('Harper Howard', '3535353535');



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



INSERT INTO Order_Table (created_date_time, customer_id, cashier_id)
VALUES ('2024-11-01 12:30:00', 1, 3),
       ('2024-11-02 14:15:00', 2, 4),
       ('2024-11-03 16:00:00', 3, 5),
       ('2024-11-04 10:45:00', 4, 6),
       ('2024-11-05 13:20:00', 5, 7),
       ('2024-11-06 15:00:00', 6, 3),
       ('2024-11-07 09:30:00', 7, 4),
       ('2024-11-08 11:45:00', 8, 5),
       ('2024-11-09 14:00:00', 9, 6),
       ('2024-11-10 12:20:00', 10, 7),
       ('2024-11-11 16:30:00', 11, 3),
       ('2024-11-12 18:00:00', 12, 4),
       ('2024-11-13 10:00:00', 13, 5),
       ('2024-11-14 15:30:00', 14, 6),
       ('2024-11-15 19:15:00', 15, 7),
       ('2024-11-16 12:45:00', 16, 3),
       ('2024-11-17 09:15:00', 17, 4),
       ('2024-11-18 17:00:00', 18, 5),
       ('2024-11-19 13:00:00', 19, 6),
       ('2024-11-20 11:00:00', 20, 7),
       ('2024-12-01 12:30:00', 1, 3),
       ('2024-12-02 14:15:00', 2, 4),
       ('2024-12-03 16:00:00', 3, 5),
       ('2024-12-04 10:45:00', 4, 6),
       ('2024-12-05 13:20:00', 5, 7),
       ('2024-12-06 15:00:00', 6, 3),
       ('2024-12-07 09:30:00', 7, 4),
       ('2024-12-08 11:45:00', 8, 5),
       ('2024-12-09 14:00:00', 9, 6),
       ('2024-12-10 12:20:00', 10, 7),
       ('2024-12-11 16:30:00', 11, 3),
       ('2024-12-12 18:00:00', 12, 4),
       ('2024-12-13 10:00:00', 13, 5),
       ('2024-12-14 15:30:00', 14, 6),
       ('2024-12-15 19:15:00', 15, 7);



INSERT INTO Order_Line (order_id, menu_item_id, ordered_quantity)
VALUES (1, 1, 2),
       (1, 2, 1),
       (2, 3, 1),
       (2, 4, 2),
       (3, 5, 1),
       (3, 6, 3),
       (4, 7, 2),
       (4, 8, 1),
       (5, 9, 1),
       (5, 10, 4),
       (6, 11, 2),
       (6, 12, 1),
       (7, 13, 3),
       (7, 14, 2),
       (8, 15, 1),
       (8, 16, 2),
       (9, 17, 1),
       (9, 18, 3),
       (10, 19, 2),
       (10, 20, 1),
       (11, 21, 3),
       (11, 22, 1),
       (12, 23, 2),
       (12, 24, 4),
       (13, 25, 1),
       (13, 26, 3),
       (14, 27, 2),
       (14, 28, 1),
       (15, 29, 1),
       (15, 30, 2),
       (16, 1, 3),
       (16, 2, 1),
       (17, 3, 2),
       (17, 4, 1),
       (18, 5, 3),
       (18, 6, 1),
       (19, 7, 2),
       (19, 8, 1),
       (20, 9, 3),
       (20, 10, 1),
       (21, 11, 2),
       (21, 12, 1),
       (22, 13, 1),
       (22, 14, 2),
       (23, 15, 3),
       (23, 16, 1),
       (24, 17, 1),
       (24, 18, 3),
       (25, 19, 2),
       (25, 20, 1),
       (26, 21, 1),
       (26, 22, 2),
       (27, 23, 3),
       (27, 24, 1),
       (28, 25, 2),
       (28, 26, 1),
       (29, 27, 3),
       (29, 28, 1),
       (30, 29, 2),
       (30, 30, 1),
       (31, 1, 2),
       (31, 2, 3),
       (32, 3, 1),
       (32, 4, 2),
       (33, 5, 3),
       (33, 6, 1),
       (34, 7, 2),
       (34, 8, 1),
       (35, 9, 3),
       (35, 10, 2);



INSERT INTO Invoice (created_date_time, amount, cashier_id, order_id)
VALUES ('2024-11-01 12:35:00', 20.50, 3, 1),
       ('2024-11-02 14:20:00', 32.00, 4, 2),
       ('2024-11-03 16:05:00', 15.75, 5, 3),
       ('2024-11-04 10:50:00', 45.00, 6, 4),
       ('2024-11-05 13:25:00', 25.00, 7, 5),
       ('2024-11-06 15:05:00', 18.25, 3, 6),
       ('2024-11-07 09:35:00', 22.50, 4, 7),
       ('2024-11-08 11:50:00', 35.00, 5, 8),
       ('2024-11-09 14:05:00', 40.00, 6, 9),
       ('2024-11-10 12:25:00', 19.50, 7, 10),
       ('2024-11-11 16:35:00', 27.00, 3, 11),
       ('2024-11-12 18:05:00', 31.75, 4, 12),
       ('2024-11-13 10:05:00', 50.00, 5, 13),
       ('2024-11-14 15:35:00', 20.00, 6, 14),
       ('2024-11-15 19:20:00', 44.00, 7, 15),
       ('2024-11-16 12:50:00', 33.00, 3, 16),
       ('2024-11-17 09:20:00', 25.00, 4, 17),
       ('2024-11-18 17:05:00', 38.00, 5, 18),
       ('2024-11-19 13:05:00', 42.50, 6, 19),
       ('2024-11-20 11:05:00', 19.75, 7, 20),
       ('2024-12-01 12:35:00', 20.50, 3, 21),
       ('2024-12-02 14:20:00', 32.00, 4, 22),
       ('2024-12-03 16:05:00', 15.75, 5, 23),
       ('2024-12-04 10:50:00', 45.00, 6, 24),
       ('2024-12-05 13:25:00', 25.00, 7, 25),
       ('2024-12-06 15:05:00', 18.25, 3, 26),
       ('2024-12-07 09:35:00', 22.50, 4, 27),
       ('2024-12-08 11:50:00', 35.00, 5, 28),
       ('2024-12-09 14:05:00', 40.00, 6, 29),
       ('2024-12-10 12:25:00', 19.50, 7, 30),
       ('2024-12-11 16:35:00', 27.00, 3, 31),
       ('2024-12-12 18:05:00', 31.75, 4, 32),
       ('2024-12-13 10:05:00', 50.00, 5, 33),
       ('2024-12-14 15:35:00', 20.00, 6, 34),
       ('2024-12-15 19:20:00', 44.00, 7, 35);



INSERT INTO Inventory (name, admin_id)
VALUES ('Main Inventory', 14);

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
FROM Inventory_Order_Line;
SELECT *
FROM purchase_order;
