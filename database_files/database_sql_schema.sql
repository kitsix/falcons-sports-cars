
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

CREATE VIEW Sales_Emps_Info AS
	SELECT SE.id, SE.dealership_number, P.first_name, P.last_name, P.email, P.street, P.zip, A.phone_number
	FROM sales_emps SE, people P, addresses A
	WHERE SE.id = P.id AND P.street = A.street AND P.zip = A.zip;

CREATE VIEW Test_Drives_Per_Sales_Emp_Current_Year (id, test_drives) AS
	SELECT SE.id, COUNT(*)
	FROM sales_emps SE, test_drives TD, customers C
	WHERE SE.id = C.assigned_emp_id AND C.id = TD.customer_id AND YEAR(TD.datetime) = YEAR(NOW())
	GROUP BY SE.id;

CREATE VIEW Sales_Per_Sales_Emp_Current_Year (id, sales) AS
	SELECT SE.id, COUNT(*)
	FROM sales_emps SE, vehicles V, purchase_vehicle PV, customers C
	WHERE SE.id = C.assigned_emp_id AND PV.stock_number = V.stock_number AND C.id = PV.customer_id AND YEAR(V.sale_datetime) = YEAR(NOW())
	GROUP BY SE.id;

CREATE VIEW Commissions_Per_Sales_Emp_Current_Year (id, commissions_total) AS
	SELECT SE.id, SUM(V.price * (PV.percentage/100))
	FROM sales_emps SE, people P, purchase_vehicle PV, vehicles V, customers C
	WHERE SE.id = P.id AND PV.stock_number = V.stock_number AND PV.customer_id = C.id AND C.assigned_emp_id = SE.id AND YEAR(V.delivery_datetime) = YEAR(NOW())
	GROUP BY SE.id;

CREATE VIEW Customers_Info AS
	SELECT C.id, P.first_name, P.last_name, C.customer_notes, SE.dealership_number AS assigned_emp_dealership_number, C.assigned_emp_id
	FROM customers C, people P, Sales_Emps SE
	WHERE C.id = P.id AND C.assigned_emp_id = SE.id;

CREATE VIEW Customers_Test_Driven_Vehicles AS
	SELECT C.id, TD.stock_number, TD.datetime, V.make, V.model, V.year, V.new, V.price, V.dealership_number
	FROM customers C, sales_emps SE, Test_Drives TD, vehicles V
	WHERE C.id = TD.customer_id AND C.assigned_emp_id = SE.id AND SE.dealership_number = V.dealership_number AND TD.stock_number = V.stock_number;

CREATE VIEW Customers_Purchased_Vehicles AS
	SELECT C.id, PV.stock_number, V.make, V.model, V.price, V.sale_datetime, V.dealership_number AS sold_vehicle_dealership_number
	FROM customers C, Sales_Emps SE, purchase_vehicle PV, vehicles V
	WHERE C.assigned_emp_id = SE.id AND SE.dealership_number = V.dealership_number AND C.id = PV.customer_id AND V.stock_number = PV.stock_number;






	
