DROP DATABASE IF EXISTS badminton_booking;

CREATE DATABASE badminton_booking;

USE badminton_booking;

CREATE TABLE users (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(100) NOT NULL UNIQUE,
email VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
full_name VARCHAR(255),
role ENUM(
'ROLE_ADMIN',
'ROLE_MANAGER',
'ROLE_CUSTOMER'
) NOT NULL,
enabled BOOLEAN DEFAULT TRUE,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE refresh_tokens (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
token VARCHAR(500) NOT NULL UNIQUE,
expiry_date DATETIME NOT NULL,
user_id BIGINT NOT NULL,
CONSTRAINT fk_refresh_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
);

CREATE TABLE token_blacklist (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
token VARCHAR(500) NOT NULL,
expiry_date DATETIME NOT NULL
);

CREATE TABLE courts (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(255) NOT NULL,
address VARCHAR(500),
price DECIMAL(12,2) NOT NULL,
description TEXT,
image_url VARCHAR(1000),
active BOOLEAN DEFAULT TRUE
);

CREATE TABLE court_images (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
image_url VARCHAR(1000) NOT NULL,
court_id BIGINT NOT NULL,
CONSTRAINT fk_court_image
    FOREIGN KEY (court_id)
    REFERENCES courts(id)
);

CREATE TABLE bookings (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
booking_date DATE NOT NULL,
start_time TIME NOT NULL,
end_time TIME NOT NULL,
status ENUM(
    'PENDING',
    'APPROVED',
    'REJECTED'
) DEFAULT 'PENDING',
note VARCHAR(500),
user_id BIGINT NOT NULL,
court_id BIGINT NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT fk_booking_user
    FOREIGN KEY (user_id)
    REFERENCES users(id),
CONSTRAINT fk_booking_court
    FOREIGN KEY (court_id)
    REFERENCES courts(id)
);

CREATE TABLE audit_logs (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(255),
action VARCHAR(500),
status VARCHAR(50),
created_at DATETIME);

INSERT INTO users (username,email,password,full_name,role,enabled)
VALUES('admin','[admin@gmail.com](mailto:admin@gmail.com)','$2a$10$7EqJtq98hPqEX7fNZaFWoOHiY7R6czS4EzFqqpwPIYOvTo95Ofzmi','System Administrator','ROLE_ADMIN',TRUE),
('manager','[manager@gmail.com](mailto:manager@gmail.com)','$2a$10$7EqJtq98hPqEX7fNZaFWoOHiY7R6czS4EzFqqpwPIYOvTo95Ofzmi','Court Manager','ROLE_MANAGER',TRUE),
('customer','[customer@gmail.com](mailto:customer@gmail.com)','$2a$10$7EqJtq98hPqEX7fNZaFWoOHiY7R6czS4EzFqqpwPIYOvTo95Ofzmi','Customer Demo','ROLE_CUSTOMER',TRUE);

INSERT INTO courts (name,address,price,description,image_url,active)
VALUES('Court A','Thanh Xuan, Ha Noi',120000,'San trong nha','https://demo-image.com/court-a.jpg',TRUE),
('Court B','Cau Giay, Ha Noi',150000,'San tieu chuan thi dau','https://demo-image.com/court-b.jpg',TRUE);
INSERT INTO bookings (booking_date,start_time,end_time,status,note,user_id,court_id)
VALUES(CURDATE(),'18:00:00','20:00:00','PENDING','Dat san buoi toi',3,1);

INSERT INTO audit_logs (username,action,status,created_at)
VALUES('customer','CREATE_BOOKING','SUCCESS',NOW());
