
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


