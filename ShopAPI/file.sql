Use shopAPI;

CREATE TABLE `Customers`(
                            id int primary key auto_increment unique not null ,
                            name varchar(255),
                            email varchar(255)
);
CREATE TABLE `Products`(
                           id int primary key auto_increment unique not null ,
                           product_name varchar(255) not null ,
                           price decimal(10,2) not null ,
                           description text

);
CREATE TABLE `order` (
                         id int primary key auto_increment unique not null ,
                         cus_id int,
                         pro_id int,
                         order_date date,
                         price decimal(10,2),
                         status int
)