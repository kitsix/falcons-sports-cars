/*
Query 1: A manager/salesperson should be able to view the visit details of a given customer.
I understand customer visit details to include:
- id
- first name
- last name
- customer_notes
- dealership_address
- assigned sales employee id
- test driven vehicles and the corresponding datetime of the test drive
I do not include the customer's purchased vehicles -- there is a separate view (customers_purchased_vehicles) for that if we need it.
*/

SELECT CI.id, CI.first_name, CI.last_name, CI.customer_notes, CTDV.dealership_number, D.street, D.zip, CTDV.stock_number, CTDV.datetime
FROM customers_info CI, customers_test_driven_vehicles CTDV, dealerships D
WHERE CI.id = CTDV.id AND CTDV.dealership_number = D.dealership_number;

/*
-- Implementation example:
SELECT CI.id, CI.first_name, CI.last_name, CI.customer_notes, CTDV.dealership_number, D.street, D.zip, CTDV.stock_number, CTDV.datetime
FROM customers_info CI, customers_test_driven_vehicles CTDV, dealerships D
WHERE CI.id = ? AND CI.id = CTDV.id AND CTDV.dealership_number = D.dealership_number;
*/

/*
Query 2: A manager/salesperson, for a given make and model, should be able to view the list of customers who test drove the vehicle.
*/

SELECT D.dealership_number, C.id, P.first_name, P.last_name, TD.stock_number, V.make, V.model, TD.datetime
FROM customers C, people P, sales_emps E, test_drives TD, vehicles V, dealerships D
WHERE V.stock_number = TD.stock_number AND TD.customer_id = C.id AND C.id = P.id AND C.assigned_emp_id = E.id AND E.dealership_number = D.dealership_number;

/*
-- Implementation example:
SELECT D.dealership_number, C.id, P.first_name, P.last_name, TD.stock_number, V.make, V.model, TD.datetime
FROM customers C, people P, sales_emps E, test_drives TD, vehicles V, dealerships D
WHERE V.make = ? AND V.model = ? AND V.stock_number = TD.stock_number AND TD.customer_id = C.id AND C.id = P.id AND C.assigned_emp_id = E.id AND E.dealership_number = D.dealership_number;
*/

/*
Query 3: A manager/salesperson should be able to view the top five vehicles based on the number sold.
*/

SELECT COUNT(*) AS num_sold_vehicles, V.make, V.model, V.year, V.new
FROM vehicles V, purchase_vehicle PV
WHERE PV.stock_number = V.stock_number
GROUP BY V.make, V.model, V.year, V.new
ORDER BY num_sold_vehicles DESC
LIMIT 0, 5;

/*
Query 4: A manager should be able to list the dealership locations in order of their total sales amount in the current month.
*/

SELECT D.dealership_number, SUM(V.price) AS total_amount_sold
FROM vehicles V, purchase_vehicle PV, dealerships D
WHERE PV.stock_number = V.stock_number AND V.dealership_number = D.dealership_number AND MONTH(V.sale_datetime) = MONTH(NOW())
GROUP BY D.dealership_number
ORDER BY total_amount_sold DESC;

/*
Query 5: A manager should be able to view the details of all salespeople, including number of test-drives, number of sales, and total commission received in the current year.
*/

SELECT SEI.id, SEI.dealership_number, SEI.first_name, SEI.last_name, SEI.email, SEI.street, SEI.zip, SEI.phone_number, TDSE.test_drives, SSE.sales, CSE.commissions_total
FROM sales_emps_info SEI, test_drives_per_sales_emp_current_year TDSE, sales_per_sales_emp_current_year SSE, commissions_per_sales_emp_current_year CSE
WHERE SEI.id = TDSE.id AND SEI.id = SSE.id AND SEI.id = CSE.id
ORDER BY CSE.commissions_total DESC;