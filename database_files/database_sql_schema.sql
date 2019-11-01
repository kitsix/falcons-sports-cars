CREATE TABLE Addresses(
	street VARCHAR(100),
	zip VARCHAR(10),
	city VARCHAR(100) NOT NULL,
	state VARCHAR(100) NOT NULL,
	phone_number INT,
    PRIMARY KEY (street, zip));

CREATE TABLE Dealerships(
	dealership_number INT PRIMARY KEY,
	street VARCHAR(100) NOT NULL,
	zip VARCHAR(10) NOT NULL,
	FOREIGN KEY (street, zip) REFERENCES Addresses(street, zip)
		ON DELETE CASCADE
        ON UPDATE CASCADE);

CREATE TABLE People(
	id INT PRIMARY KEY,
	first_name VARCHAR(100) NOT NULL,
	last_name VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL,
	street VARCHAR(100) NOT NULL,
	zip VARCHAR(10) NOT NULL,
	FOREIGN KEY (street, zip) REFERENCES Addresses(street, zip)
		ON DELETE CASCADE
        ON UPDATE CASCADE);	

CREATE TABLE Sales_Emps(
	id INT PRIMARY KEY,
	username VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL,
	role VARCHAR(100) NOT NULL,
	dealership_number INT,
    FOREIGN KEY (id) REFERENCES People(id)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (dealership_number) REFERENCES Dealerships(dealership_number)
		ON DELETE CASCADE
		ON UPDATE CASCADE);

CREATE TABLE Customers(
	id INT PRIMARY KEY,
	customer_notes VARCHAR(1000),
	assigned_emp_id INT NOT NULL,
	FOREIGN KEY (assigned_emp_id) REFERENCES Sales_Emps(id)
		ON DELETE CASCADE
		ON UPDATE CASCADE);

CREATE TABLE Manages(
	dealership_number INTEGER PRIMARY KEY,
	manager_id INTEGER NOT NULL,
    FOREIGN KEY (dealership_number) REFERENCES Dealerships(dealership_number)
		ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (manager_id) REFERENCES Sales_Emps(id)
		ON DELETE CASCADE
        ON UPDATE CASCADE);

CREATE TABLE Vehicles(
	stock_number INT PRIMARY KEY,
	make VARCHAR(100) NOT NULL,
	model VARCHAR(100) NOT NULL,
	sale_datetime DATETIME,
	delivery_datetime DATETIME,
	year INT NOT NULL,
	new BOOLEAN NOT NULL,
	image BLOB,
	price DECIMAL(12, 2) NOT NULL,
	car_and_driver_hyperlink VARCHAR(100),
	dealership_number INT NOT NULL,
	FOREIGN KEY(dealership_number) REFERENCES Dealerships(dealership_number)
		ON DELETE CASCADE
		ON UPDATE CASCADE);

CREATE TABLE Schedules(
	datetime DATETIME PRIMARY KEY);

CREATE TABLE Test_Drives(
	customer_id INT,
	stock_number INT,
	datetime DATETIME,
	PRIMARY KEY(customer_id, stock_number, datetime),
	FOREIGN KEY (customer_id) REFERENCES Customers(id)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (stock_number) REFERENCES Vehicles(stock_number)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (datetime) REFERENCES Schedules(datetime)
		ON DELETE CASCADE
        ON UPDATE CASCADE);

CREATE TABLE Purchase_Vehicle(
	customer_id INT,
	stock_number INT,
	percentage INT NOT NULL,
	PRIMARY KEY(customer_id, stock_number),
	FOREIGN KEY (customer_id) REFERENCES Customers(id)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (stock_number) REFERENCES Vehicles(stock_number)
		ON DELETE CASCADE
        ON UPDATE CASCADE);
