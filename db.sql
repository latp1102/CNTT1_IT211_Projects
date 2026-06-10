drop database if exists badminton_booking;
create database badminton_booking;
use badminton_booking;
create table users (
	id bigint primary key auto_increment,
	username varchar(100) not null unique,
	email varchar(255) not null unique,
	password varchar(255) not null,
	full_name varchar(255),
	role enum('ROLE_ADMIN','ROLE_MANAGER','ROLE_CUSTOMER') not null,
	enabled boolean default true,
	created_at timestamp default current_timestamp
);

create table refresh_tokens (
	id bigint primary key auto_increment,
	token varchar(500) not null unique,
	expiry_date datetime not null,
	user_id bigint not null,
	constraint fk_refresh_user foreign key (user_id) references users(id)
);

create table token_blacklist (
	id bigint primary key auto_increment,
    token varchar(500) not null,
    expiry_date datetime not null
);

create table courts (
	id bigint primary key auto_increment,
	name varchar(255) not null,
	address varchar(500),
	price decimal(12,2) not null,
	description text,
	image_url varchar(1000),
	active boolean default true
);

create table court_images (
	id bigint primary key auto_increment,
	image_url varchar(1000) not null,
	court_id bigint not null,
	constraint fk_court_image foreign key (court_id) references courts(id)
);

create table bookings (
	id bigint primary key auto_increment,
	booking_date date not null,
	start_time time not null,
	end_time time not null,
	status enum('PENDING','APPROVED','REJECTED') default 'PENDING',
	note varchar(500),
	user_id bigint not null,
	court_id bigint not null,
	created_at timestamp default current_timestamp,
	constraint fk_booking_user foreign key (user_id) references users(id),
    constraint fk_booking_court foreign key (court_id) references courts(id)
);

create table audit_logs (
	id bigint primary key auto_increment,
	username varchar(255),
	action varchar(500),
	status varchar(50),
	created_at datetime
);

insert into users (username,email,password,full_name,role,enabled)
values('admin','[admin@gmail.com](mailto:admin@gmail.com)','$2a$10$7EqJtq98hPqEX7fNZaFWoOHiY7R6czS4EzFqqpwPIYOvTo95Ofzmi','System Administrator','ROLE_ADMIN',TRUE),
('manager','[manager@gmail.com](mailto:manager@gmail.com)','$2a$10$7EqJtq98hPqEX7fNZaFWoOHiY7R6czS4EzFqqpwPIYOvTo95Ofzmi','Court Manager','ROLE_MANAGER',TRUE),
('customer','[customer@gmail.com](mailto:customer@gmail.com)','$2a$10$7EqJtq98hPqEX7fNZaFWoOHiY7R6czS4EzFqqpwPIYOvTo95Ofzmi','Customer Demo','ROLE_CUSTOMER',TRUE);

insert into courts (name,address,price,description,image_url,active)
values('Court A','Thanh Xuan, Ha Noi',120000,'San trong nha','https://demo-image.com/court-a.jpg',TRUE),
('Court B','Cau Giay, Ha Noi',150000,'San tieu chuan thi dau','https://demo-image.com/court-b.jpg',TRUE);
insert into bookings (booking_date,start_time,end_time,status,note,user_id,court_id)
values(curdate(),'18:00:00','20:00:00','PENDING','Dat san buoi toi',3,1);

insert into audit_logs (username,action,status,created_at)
values('customer','CREATE_BOOKING','SUCCESS',now());
