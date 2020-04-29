#create database db_spomex;
use db_spomex;

# DEV
DROP TABLE IF EXISTS shipment;
DROP TABLE IF EXISTS driver;
DROP TABLE IF EXISTS product;

# Prepare tables
create TABLE driver (
id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(500) NOT NULL,
surname VARCHAR(500) NOT NULL
);

create TABLE product (
id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(500) NOT NULL,
quantity INT NOT NULL
);

create TABLE shipment (
id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
driver_id INT UNSIGNED NOT NULL,
FOREIGN KEY (driver_id) REFERENCES driver(id)
);

create TABLE shipment_product (
shipment_id INT UNSIGNED NOT NULL,
product_id INT UNSIGNED NOT NULL,
FOREIGN KEY (shipment_id) REFERENCES shipment(id),
FOREIGN KEY (product_id) REFERENCES product(id)
);

# DEV
INSERT INTO driver VALUES (1, "Daniel", "SQL-script-init");

INSERT INTO product VALUES (1, "bricks", 5000);

INSERT INTO shipment VALUES(1, 1);
