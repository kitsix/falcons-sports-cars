-- DELETE operations to reset the changes made in this file so as to be able to consecutively execute this file.
DELETE FROM vehicles WHERE stock_number = 3010;
DELETE FROM addresses WHERE addresses.street = 'somestreet_11' AND addresses.zip = 'somezip_11';
DELETE FROM addresses WHERE addresses.street = 'new_street_11' AND addresses.zip = 'new_zip_11';
DELETE FROM addresses WHERE addresses.street = 'new_street_11_updated' AND addresses.zip = 'newzip11';
DELETE FROM customers WHERE id = 1011;
DELETE FROM sales_emps WHERE id = 2011;
DELETE FROM test_drives WHERE stock_number = 541;

/*
Transaction 1: A manager/salesperson should be able to add customers and update their personal info.
*/
-- Add a customer (remember that since we have street/zip as the PK of addresses we cannot have duplicate street/zip combinations likewise for customer IDs)
CALL add_customer(
	1011,
	'new_first_name',
    'new_last_name',
    'new_email',
    'new_street_11',
    'new_zip_11',
	'new_city',
    'new_state',
    123123123,
	'some customer notes here',
    7);

-- Update customer info (this does not include customer notes -- for that see the next transaction)
CALL update_customer_personal_info(
	1011,
	'new_first_name',
    'new_last_name',
    'new_email',
    'new_street_11_updated',
    'newzip11',
	'new_city',
    'new_state',
    123123123);

/********************************************************************************************************************/

/*
Transaction 2: A manager/salesperson should be able to update a customer's visit history including locations, cars test-driven (if any), and customer notes.
*/

CALL update_customer_notes(
	1011,
	'updated customer notes for customer with ID = 1011');

-- This one works because customer 1011 has a sales employee assigned who works at dealership #2 which is also the dealership where the vehicle with stock number 541 is located.
CALL add_customer_test_driven_vehicle(
	1011,
    541,
	'2019-08-03 09:42:30');

/*
-- This one fails because the vehicle with stock number 1000 is not located at the dealership of customer 1011.
CALL add_customer_test_driven_vehicle(
	1011,
    1000,
	"2019-08-03 09:42:30");
*/

/********************************************************************************************************************/

/*
Transaction 3: A manager should be able to reassign customers to individual salespeople.
*/

CALL update_customer_assigned_employee(
	1011,
	6);

CALL update_customer_assigned_employee(
	1011,
	7);

/********************************************************************************************************************/

/*
Transaction 4: A manager should be able to add/remove salespeople.
*/

CALL add_sales_emp(
	2011,
	'someusername',
    'somepassword',
    'floor',
    8,
    'somefirstname',
    'somelastname',
    'someemail',
    'somestreet_11',
    'somezip_11',
	'somecity',
    'somestate',
    123123123);

-- NOTE: This procedure will NOT remove the corresponding street/zip combination from addresses for the removed sales emp -- this is fixable via an ON DELETE trigger.
-- This is also the case for any customer who has the removed sales emp assigned to them.
-- CALL remove_sales_emp(2011);

/********************************************************************************************************************/

/*
Transaction 5: A manager should be able to update the inventory after a vehicle is added or sold.
*/

CALL add_vehicle(
	3010,
	'Chevrolet',
    'Astro', 
    '0000-00-00 00:00:00', 
    '0000-00-00 00:00:00', 
    '1999', 
    '1', 
    null, 
    100180.46, 
    '', 
    3);

CALL update_sold_vehicle(
	3010,
    '2019-12-03 09:42:30',
    3);

CALL update_delivered_vehicle(
	3010,
    '2020-12-03 09:42:30',
    3);

/*
CALL update_sold_vehicle(
	3010,
	'0000-00-00 00:00:00',
    3);

CALL update_delivered_vehicle(
	3010,
    '0000-00-00 00:00:00',
    3)
*/
