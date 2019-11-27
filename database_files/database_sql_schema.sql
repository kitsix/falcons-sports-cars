
CREATE TABLE Addresses(
	street VARCHAR(100),
	zip VARCHAR(10),
	city VARCHAR(100) NOT NULL,
	state VARCHAR(100) NOT NULL,
	phone_number INT,
    PRIMARY KEY (street, zip));

CREATE TABLE Dealerships(
	dealership_number INT PRIMARY KEY,
    dealership_name VARCHAR(100) NOT NULL,
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

-- Transaction 1 Part 1: A manager/salesperson should be able to add a customer.
-- NOTE: all the SELECT statements are for testing purposes to make sure that the changes performed correctly.
DELIMITER $$
CREATE PROCEDURE add_customer(
	IN id INT(11), 
    IN first_name VARCHAR(100), 
    IN last_name VARCHAR(100),
    IN email VARCHAR(100),
    IN street VARCHAR(100),
    IN zip VARCHAR(10),
    IN city VARCHAR(100),
    IN state VARCHAR(100),
    IN phone_number INT(11),
	IN customer_notes VARCHAR(1000),
    IN assigned_emp_id INT(11))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'PROCEDURE SQL EXCEPTION: add_customer: INSERT operations rolled back';
	END;
    
    START TRANSACTION;
		INSERT INTO addresses VALUES (street, zip, city, state, phone_number);
		INSERT INTO people VALUES (id, first_name, last_name, email, street, zip);
		INSERT INTO customers VALUES (id, customer_notes, assigned_emp_id);
	COMMIT;
    
	SELECT *
    FROM addresses a, people p, customers c
    WHERE p.id = id AND c.id = id AND p.street = a.street AND p.zip = a.zip;
END;$$
DELIMITER ;

-- Transaction 1 Part 2: A manager/salesperson should be able to update a customer's personal info.
-- NOTE: all the SELECT statements are for testing purposes to make sure that the changes performed correctly.
DELIMITER $$
CREATE PROCEDURE update_customer_personal_info(
	IN id INT(11), 
    IN first_name VARCHAR(100), 
    IN last_name VARCHAR(100),
    IN email VARCHAR(100),
    IN street VARCHAR(100),
    IN zip VARCHAR(10),
    IN city VARCHAR(100),
    IN state VARCHAR(100),
    IN phone_number INT(11))
BEGIN
	DECLARE temp_street VARCHAR(100) DEFAULT NULL;
    DECLARE temp_zip VARCHAR(10) DEFAULT NULL;

	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'PROCEDURE SQL EXCEPTION: update_customer_personal_info: UPDATE operations rolled back';
	END;

	SELECT p.street, p.zip
	INTO temp_street, temp_zip
	FROM people p
	WHERE p.id = id;
	
    START TRANSACTION;
		UPDATE addresses a SET
		a.street = street,
		a.zip = zip,
		a.city = city,
		a.state = state,
		a.phone_number = phone_number
		WHERE a.street = temp_street AND a.zip = temp_zip;

		UPDATE people p SET
		p.first_name = first_name,
		p.last_name = last_name,
		p.email = email
		WHERE p.id = id;
	COMMIT;
    
	SELECT *
    FROM addresses a, people p, customers c
    WHERE p.id = id AND c.id = id AND p.street = a.street AND p.zip = a.zip;
END;$$
DELIMITER ;


-- Transaction 2: A manager/salesperson should be able to update a customer's visit history including locations, cars test-driven (if any), and customer notes.
-- NOTE: all the SELECT statements are for testing purposes to make sure that the changes performed correctly.
DELIMITER $$
CREATE PROCEDURE update_customer_notes(
	IN id INT(11),
	IN customer_notes VARCHAR(1000))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'PROCEDURE SQL EXCEPTION: update_customer_notes: UPDATE operations rolled back';
	END;
	
    START TRANSACTION;
		UPDATE customers c SET
		c.customer_notes = customer_notes
		WHERE c.id = id;
	COMMIT;
    
	SELECT *
    FROM customers c
    WHERE c.id = id;
END;$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE add_customer_test_driven_vehicle(
	IN id INT(11), 
	IN stock_number INT(11),
	IN datetime VARCHAR(100))
BEGIN
	DECLARE customer_dealership_number, vehicle_dealership_number INT(11) DEFAULT NULL;

	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'PROCEDURE SQL EXCEPTION: add_customer_test_driven_vehicle: INSERT operations rolled back';
	END;
    
    SELECT td.*
    FROM test_drives td
    WHERE td.customer_id = id AND td.stock_number = stock_number;
    
    -- The following SQL statement is NOT testing code -- it is required for the conditional in the transaction.
    SELECT se.dealership_number, v.dealership_number
    INTO customer_dealership_number, vehicle_dealership_number
    FROM customers c, sales_emps se, vehicles v
    WHERE c.id = id AND c.assigned_emp_id = se.id AND v.stock_number = stock_number AND v.dealership_number = se.dealership_number;
    
    SELECT customer_dealership_number, vehicle_dealership_number;
    
	START TRANSACTION;
		BEGIN IF customer_dealership_number IS NOT NULL AND vehicle_dealership_number IS NOT NULL THEN
			INSERT INTO test_drives VALUES (id, stock_number, datetime);
		ELSE
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'PROCEDURE SQL EXCEPTION: add_customer_test_driven_vehicle: customer_dealership_number != vehicle_dealership_number';
		END IF;
	COMMIT;
    
	SELECT td.*
    FROM test_drives td
    WHERE td.customer_id = id AND td.stock_number = stock_number;
END;
END $$
DELIMITER ;

-- Transaction 3: A manager should be able to reassign customers to individual salespeople.
-- NOTE: all the SELECT statements are for testing purposes to make sure that the changes performed correctly.
DELIMITER $$
CREATE PROCEDURE update_customer_assigned_employee(
	IN id INT(11),
    IN assigned_emp_id INT(11))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'PROCEDURE SQL EXCEPTION: update_customer_assigned_employee: UPDATE operations rolled back';
	END;

	SELECT *
    FROM customers c
    WHERE c.id = id;
	
    START TRANSACTION;
		UPDATE customers c SET
        c.assigned_emp_id = assigned_emp_id
		WHERE c.id = id;
	COMMIT;
    
	SELECT *
    FROM customers c
    WHERE c.id = id;
END;$$
DELIMITER ;

-- Transaction 4 Part 1: A manager/salesperson should be able to add a sales employee.
DELIMITER $$
CREATE PROCEDURE add_sales_emp(
	IN id INT(11), 
    IN username VARCHAR(100),
    IN password VARCHAR(100),
    IN role VARCHAR(100),
    IN dealership_number INT(11),
    IN first_name VARCHAR(100), 
    IN last_name VARCHAR(100),
    IN email VARCHAR(100),
    IN street VARCHAR(100),
    IN zip VARCHAR(10),
    IN city VARCHAR(100),
    IN state VARCHAR(100),
    IN phone_number INT(11))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'PROCEDURE SQL EXCEPTION: add_sales_emp: INSERT operations rolled back';
	END;
    
    START TRANSACTION;
		INSERT INTO addresses VALUES (street, zip, city, state, phone_number);
		INSERT INTO people VALUES (id, first_name, last_name, email, street, zip);
		INSERT INTO sales_emps VALUES (id, username, password, role, dealership_number);
	COMMIT;
    
	SELECT *
    FROM sales_emps se
    WHERE se.id = id;
END;$$
DELIMITER ;

-- Transaction 4 Part 2: A manager/salesperson should be able to remove a sales employee.
-- NOTE: This procedure will NOT remove the corresponding street/zip combination from addresses for the removed sales emp -- this is fixable via an ON DELETE trigger.
-- This is also the case for any customer who has the removed sales emp assigned to them.
DELIMITER $$
CREATE PROCEDURE remove_sales_emp(
	IN id INT(11))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'PROCEDURE SQL EXCEPTION: remove_sales_emp: DELETE operations rolled back';
	END;
    
    START TRANSACTION;
		DELETE FROM people WHERE people.id = id;
	COMMIT;
    
	SELECT *
    FROM sales_emps se
    WHERE se.id = id;
END;$$
DELIMITER ;

-- Transaction 4 Part 1: A manager should be able to update the inventory after a vehicle is added.
DELIMITER $$
CREATE PROCEDURE add_vehicle(
	IN stock_number INT(11), 
    IN make VARCHAR(100),
    IN model VARCHAR(100),
    IN sale_datetime VARCHAR(100),
    IN delivery_datetime VARCHAR(100),
    IN year INT(11), 
    IN new BOOLEAN,
    IN image BLOB,
    IN price DECIMAL(12, 2),
    IN car_and_driver_hyperlink VARCHAR(100),
    IN dealership_number INT(11))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'PROCEDURE SQL EXCEPTION: add_vehicle: INSERT operations rolled back';
	END;
    
	SELECT *
    FROM vehicles v
    WHERE v.stock_number = stock_number;
       
    START TRANSACTION;
		INSERT INTO vehicles VALUES(stock_number, make, model, sale_datetime, delivery_datetime, year, new, image, price, car_and_driver_hyperlink, dealership_number);
	COMMIT;
    
	SELECT *
    FROM vehicles v
    WHERE v.stock_number = stock_number;
END;$$
DELIMITER ;

-- Transaction 4 Part 2: A manager should be able to update the inventory after a vehicle is sold.
DELIMITER $$
CREATE PROCEDURE update_sold_vehicle(
	IN stock_number INT(11), 
    IN sale_datetime VARCHAR(100),
    IN dealership_number INT(11))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'PROCEDURE SQL EXCEPTION: update_sold_vehicle: UPDATE operations rolled back';
	END;
    
	SELECT *
    FROM vehicles v
    WHERE v.stock_number = stock_number;
    
    START TRANSACTION;
		UPDATE vehicles SET vehicles.sale_datetime = sale_datetime WHERE vehicles.stock_number = stock_number AND vehicles.dealership_number = dealership_number;
	COMMIT;
    
	SELECT *
    FROM vehicles v
    WHERE v.stock_number = stock_number;
END;$$
DELIMITER ;

-- Transaction 4 Part 3: A manager should be able to update the inventory after a vehicle is delivered.
-- NOTE: this one wasn't on the list of transactions, but I added it anyway.
DELIMITER $$
CREATE PROCEDURE update_delivered_vehicle(
	IN stock_number INT(11), 
    IN delivery_datetime VARCHAR(100),
    IN dealership_number INT(11))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'PROCEDURE SQL EXCEPTION: update_delivered_vehicle: UPDATE operations rolled back';
	END;
    
	SELECT *
    FROM vehicles v
    WHERE v.stock_number = stock_number;
    
    START TRANSACTION;
		UPDATE vehicles SET vehicles.delivery_datetime = delivery_datetime WHERE vehicles.stock_number = stock_number AND vehicles.dealership_number = dealership_number;
	COMMIT;
    
	SELECT *
    FROM vehicles v
    WHERE v.stock_number = stock_number;
END;$$
DELIMITER ;
