/*
Populate in this order with CSV files:
- addresses
- dealerships
- people
- vehicles

Then execute "insert_data_01.sql" for the remaining tables
*/

DELETE FROM addresses;
DELETE FROM dealerships;
DELETE FROM people;
DELETE FROM sales_emps;
DELETE FROM customers;
DELETE FROM manages;
DELETE FROM vehicles;
DELETE FROM schedules;
DELETE FROM test_drives;
DELETE FROM purchase_vehicle;