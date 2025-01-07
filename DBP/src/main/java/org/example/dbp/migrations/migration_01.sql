create table User
(
    id           int primary key auto_increment,
    name         varchar(30),
    role         varchar(12)  not null,
    email        varchar(45) unique,
    hire_date    date,
    phone_number long,
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


CREATE TABLE Address
(
    addressId INT AUTO_INCREMENT PRIMARY KEY,
    street    VARCHAR(255) NOT NULL,
    city      VARCHAR(100) NOT NULL
);

CREATE TABLE Vendor
(
    vendorId   INT PRIMARY KEY AUTO_INCREMENT,
    addressId  INT,
    vendorName VARCHAR(20),
    FOREIGN KEY (addressId) REFERENCES Address (addressId) -- Consistent case
);

CREATE TABLE PurchaseOrder
(
    purchaseOrderId int primary key auto_increment,
    vendorId        int,
    totalPrice      int,
    orderDate       date,
    inventory_id    int,
    FOREIGN KEY (vendorId) REFERENCES Vendor (vendorId),
    FOREIGN KEY (inventory_id) REFERENCES Inventory (id)
);

create table ingredient
(
    ingredientId   int primary key auto_increment,
    ingredientName varchar(20) NOT NULL UNIQUE,
    unit           enum ('KG','L'),
    quantity double

);

CREATE TABLE PurchaseOrderLine
(
    lineId          int primary key auto_increment,
    purchaseOrderId int,
    ingredientId    int,
    quantity double,
    cost_per_unit double,
    FOREIGN KEY (purchaseOrderId) REFERENCES PurchaseOrder (purchaseOrderId),
    FOREIGN KEY (ingredientId) REFERENCES ingredient (ingredientId)
);

INSERT INTO Inventory (name, admin_id)
VALUES ('Main Inventory', 14);



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



INSERT INTO Address (street, city)
VALUES ('123 Maple St', 'Springfield'),
       ('456 Oak Ave', 'Shelbyville'),
       ('789 Pine Rd', 'Capital City'),
       ('101 Elm St', 'Springfield'),
       ('202 Birch Ln', 'Shelbyville'),
       ('303 Cedar Dr', 'Springfield'),
       ('404 Walnut St', 'Capital City'),
       ('505 Poplar Ave', 'Springfield'),
       ('606 Ash Rd', 'Shelbyville'),
       ('707 Chestnut St', 'Capital City'),
       ('808 Sycamore Ln', 'Springfield'),
       ('909 Hickory Dr', 'Shelbyville'),
       ('110 Redwood St', 'Springfield'),
       ('120 Cypress Ave', 'Capital City'),
       ('130 Magnolia Ln', 'Springfield'),
       ('140 Dogwood Dr', 'Shelbyville'),
       ('150 Juniper St', 'Capital City'),
       ('160 Fir Ave', 'Springfield'),
       ('170 Spruce Ln', 'Shelbyville'),
       ('180 Alder Rd', 'Capital City'),
       ('190 Holly St', 'Springfield'),
       ('200 Willow Ave', 'Shelbyville'),
       ('210 Sequoia Ln', 'Capital City'),
       ('220 Beech Dr', 'Springfield'),
       ('230 Maple Rd', 'Shelbyville'),
       ('240 Pine St', 'Capital City'),
       ('250 Elm Ave', 'Springfield'),
       ('260 Birch Ln', 'Shelbyville'),
       ('270 Cedar Dr', 'Springfield'),
       ('280 Walnut St', 'Capital City');


INSERT INTO Vendor (addressId, vendorName)
VALUES (1, 'Vendor A'),
       (2, 'Vendor B'),
       (3, 'Vendor C'),
       (4, 'Vendor D'),
       (5, 'Vendor E'),
       (6, 'Vendor F'),
       (7, 'Vendor G'),
       (8, 'Vendor H'),
       (9, 'Vendor I'),
       (10, 'Vendor J'),
       (11, 'Vendor K'),
       (12, 'Vendor L'),
       (13, 'Vendor M'),
       (14, 'Vendor N'),
       (15, 'Vendor O'),
       (16, 'Vendor P'),
       (17, 'Vendor Q'),
       (18, 'Vendor R'),
       (19, 'Vendor S'),
       (20, 'Vendor T'),
       (21, 'Vendor U'),
       (22, 'Vendor V'),
       (23, 'Vendor W'),
       (24, 'Vendor X'),
       (25, 'Vendor Y'),
       (26, 'Vendor Z'),
       (27, 'Vendor AA'),
       (28, 'Vendor BB'),
       (29, 'Vendor CC'),
       (30, 'Vendor DD');


INSERT INTO PurchaseOrder (vendorId, totalPrice, orderDate, inventory_id)
VALUES (1, 500, '2025-01-01', 1),
       (2, 700, '2025-01-02', 1),
       (3, 350, '2025-01-03', 1),
       (4, 400, '2025-01-04', 1),
       (5, 450, '2025-01-05', 1),
       (6, 550, '2025-01-06', 1),
       (7, 650, '2025-01-07', 1),
       (8, 750, '2025-01-08', 1),
       (9, 850, '2025-01-09', 1),
       (10, 950, '2025-01-10', 1),
       (11, 1000, '2025-01-11', 1),
       (12, 1100, '2025-01-12', 1),
       (13, 1200, '2025-01-13', 1),
       (14, 1300, '2025-01-14', 1),
       (15, 1400, '2025-01-15', 1),
       (16, 1500, '2025-01-16', 1),
       (17, 1600, '2025-01-17', 1),
       (18, 1700, '2025-01-18', 1),
       (19, 1800, '2025-01-19', 1),
       (20, 1900, '2025-01-20', 1),
       (21, 2000, '2025-01-21', 1),
       (22, 2100, '2025-01-22', 1),
       (23, 2200, '2025-01-23', 1),
       (24, 2300, '2025-01-24', 1),
       (25, 2400, '2025-01-25', 1),
       (26, 2500, '2025-01-26', 1),
       (27, 2600, '2025-01-27', 1),
       (28, 2700, '2025-01-28', 1),
       (29, 2800, '2025-01-29', 1),
       (30, 2900, '2025-01-30', 1);


INSERT INTO Ingredient (ingredientName, unit, quantity)
VALUES ('Sugar', 'KG', 10),
       ('Milk', 'L', 20),
       ('Coffee', 'KG', 15),
       ('Tea', 'KG', 12),
       ('Flour', 'KG', 25),
       ('Butter', 'KG', 8),
       ('Salt', 'KG', 5),
       ('Pepper', 'KG', 4),
       ('Oil', 'L', 30),
       ('Vinegar', 'L', 7),
       ('Chocolate', 'KG', 10),
       ('Cocoa', 'KG', 9),
       ('Eggs', 'KG', 18),
       ('Cheese', 'KG', 13),
       ('Honey', 'KG', 6),
       ('Yeast', 'KG', 2),
       ('Rice', 'KG', 40),
       ('Pasta', 'KG', 28),
       ('Beans', 'KG', 17),
       ('Tomatoes', 'KG', 50),
       ('Carrots', 'KG', 35),
       ('Potatoes', 'KG', 45),
       ('Apples', 'KG', 22),
       ('Bananas', 'KG', 24),
       ('Oranges', 'KG', 30),
       ('Peppers', 'KG', 20),
       ('Fish', 'KG', 19),
       ('Chicken', 'KG', 33),
       ('Beef', 'KG', 32),
       ('Lamb', 'KG', 29);

INSERT INTO PurchaseOrderLine (purchaseOrderId, ingredientId, quantity, cost_per_unit)
VALUES (1, 1, 5, 2.5),
       (1, 2, 3, 3.0),
       (2, 3, 4, 4.0),
       (2, 4, 6, 2.2),
       (3, 5, 8, 1.5),
       (3, 6, 2, 4.5),
       (4, 7, 10, 1.0),
       (4, 8, 12, 0.8),
       (5, 9, 15, 3.5),
       (5, 10, 20, 2.7),
       (6, 11, 6, 4.0),
       (6, 12, 9, 3.3),
       (7, 13, 8, 2.8),
       (7, 14, 5, 5.0),
       (8, 15, 4, 6.0),
       (8, 16, 7, 1.5),
       (9, 17, 12, 0.9),
       (9, 18, 14, 1.3),
       (10, 19, 20, 2.0),
       (10, 20, 25, 1.2),
       (11, 21, 16, 1.8),
       (11, 22, 18, 1.6),
       (12, 23, 22, 2.1),
       (12, 24, 30, 1.4),
       (13, 25, 27, 1.9),
       (13, 26, 35, 2.3),
       (14, 27, 40, 3.0),
       (14, 28, 50, 1.7),
       (15, 29, 60, 2.6),
       (15, 30, 70, 2.8);



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
FROM Address;
SELECT *
FROM Vendor;
SELECT *
FROM purchaseorder;
SELECT *
FROM purchaseorderline;
SELECT *
FROM ingredient;

