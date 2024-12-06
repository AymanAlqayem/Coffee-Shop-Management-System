create table User
(
    id           int primary key auto_increment,
    name         varchar(30),
    role         varchar(12) not null,
    email        varchar(45) unique,
    hire_date    date,
    phone_number varchar(10),
    password     varchar(30) NOT NULL,
    salary       real        NOT NULL
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
    foreign key (menu_item_id) references menu_item (id)
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
values ("Hot Drinks")
     , ("Cold Drinks")
     , ("Ice")
     , ("Mohito")
     , ("Milk Chick")
     , ("Smoothies");

insert into menu_item(item_name, price, category_id)
values ("Espresso", 10, 1),
       ("Americano", 12, 1),
       ("Cappuccino", 16, 1),
       ("Nescafe", 16, 1),
       ("Cafe latte", 16, 1),
       ("Spanish latte", 16, 1),
       ("Hot chocolate", 16, 1),
       ("Tea latte", 16, 1),
       ("Hazelnut latte ", 16, 1),
       ("Fresh Vanilla", 16, 1),
       ("Caramel", 16, 1),
       ("Sahlab", 12, 1),
       ("Arabic coffee", 12, 1),
       ("Mint tea ", 12, 1),
       ("Green tea", 12, 1),
       ("Ginger,cinnamon and honey", 16, 1);

insert into menu_item(item_name, price, category_id)
values ("Orange juice", 17, 2),
       ("Lemon and mint", 17, 2),
       ("Watermelon", 17, 2),
       ("Mango and orange", 18, 2),
       ("Mango and banana ", 18, 2),
       ("Banana and milk", 18, 2),
       ("Strawberry", 18, 2);


insert into menu_item (item_name, price, category_id)
values ("Ice tea", 18, 3),
       ("Ice latte", 17, 3),
       ("Ice coffee", 18, 3),
       ("Ice mocha", 18, 3),
       ("Ice tea latte", 18, 3),
       ("Ice hazelnut latte", 18, 3),
       ("Ice spanish latte", 18, 3),
       ("Ice fresh vanilla latte", 18, 3),
       ("Ice caramel latte", 18, 3);


insert into menu_item (item_name, price, category_id)
values ("Blueberry", 18, 5),
       ("Oreo", 17, 5),
       ("chocolate", 18, 5),
       ("Strawberry and Blueberry", 18, 5),
       ("Mix berry", 18, 5),
       ("Vanilla", 16, 5),
       ("Lotus", 18, 5),
       ("snickers", 18, 5);

insert into menu_item (item_name, price, category_id)
values ("Gengar", 17, 4),
       ("Strawberry mohito ", 17, 4),
       ("raspberry", 17, 4),
       ("Mesflora ", 18, 4),
       ("Mix berry mohito", 17, 4),
       ("Blackberry", 17, 4),
       ("kewe", 17, 4);


insert into menu_item (item_name, price, category_id)
values ("Mesflora smoothie", 18, 6),
       ("pineapple ", 18, 6),
       ("Strawberry  smoothie", 20, 6),
       ("Mango smoothie", 20, 6);



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


