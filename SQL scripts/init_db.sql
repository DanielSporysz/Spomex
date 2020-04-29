use db_spomex;

drop table driver;

create TABLE driver (
id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(500) NOT NULL,
surname VARCHAR(500) NOT NULL
)

