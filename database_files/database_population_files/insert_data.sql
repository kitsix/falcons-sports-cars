DELETE FROM sales_emps;
DELETE FROM customers;
DELETE FROM manages;
DELETE FROM test_drives;
DELETE FROM purchase_vehicle;

DELETE FROM vehicles WHERE stock_number = 2000 AND dealership_number = 1;
DELETE FROM vehicles WHERE stock_number = 2001 AND dealership_number = 1;

DELETE FROM vehicles WHERE stock_number = 2002 AND dealership_number = 2;
DELETE FROM vehicles WHERE stock_number = 2003 AND dealership_number = 2;

DELETE FROM vehicles WHERE stock_number = 2004 AND dealership_number = 2;
DELETE FROM vehicles WHERE stock_number = 2005 AND dealership_number = 2;
DELETE FROM vehicles WHERE stock_number = 2006 AND dealership_number = 2;

INSERT INTO vehicles VALUES(2000, 'Audi', 'A8', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '2012', '1', null, 34678.16, 'https://www.kbb.com/audi/a8/2012/', 1);
INSERT INTO vehicles VALUES(2001, 'Audi', 'A8', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '2012', '1', null, 16270.74, 'https://www.kbb.com/audi/a8/2012/l-42-quattro-sedan-4d/', 1);

INSERT INTO vehicles VALUES(2002, 'Chevrolet', 'Astro', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '1999', '0', null, 80731.34, 'https://www.kbb.com/chevrolet/astro/1999/', 2);
INSERT INTO vehicles VALUES(2003, 'Chevrolet', 'Astro', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '1999', '0', null, 10180.46, 'https://www.kbb.com/chevrolet/astro-cargo/1999/minivan/', 2);

INSERT INTO vehicles VALUES(2004, 'Chevrolet', 'Astro', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '1999', '1', null, 89533.98, 'https://www.kbb.com/chevrolet/astro-passenger/1999/minivan-3d/', 2);
INSERT INTO vehicles VALUES(2005, 'Chevrolet', 'Astro', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '1999', '1', null, 80731.34, 'https://www.kbb.com/chevrolet/astro-cargo/1999/', 2);
INSERT INTO vehicles VALUES(2006, 'Chevrolet', 'Astro', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '1999', '1', null, 10180.46, 'https://www.kbb.com/chevrolet/astro-passenger/1999/consumer-reviews/', 2);

INSERT INTO sales_emps VALUES (1, 'callkins', 'pass1', 'floor', 1);
INSERT INTO sales_emps VALUES (2, 'ybigg', 'pass2', 'floor', 1);
INSERT INTO sales_emps VALUES (3, 'mdelap', 'pass3', 'internet', 1);
INSERT INTO sales_emps VALUES (4, 'mbreitling', 'pass4', 'internet', 1);
INSERT INTO sales_emps VALUES (5, 'slayfield', 'pass5', 'manager', 1);

INSERT INTO sales_emps VALUES (6, 'sgarraway', 'pass6', 'floor', 2);
INSERT INTO sales_emps VALUES (7, 'sbuesnel', 'pass7', 'floor', 2);
INSERT INTO sales_emps VALUES (8, 'ghanretty', 'pass8', 'internet', 2);
INSERT INTO sales_emps VALUES (9, 'bsweetnam', 'pass9', 'internet', 2);
INSERT INTO sales_emps VALUES (10, 'bpachta', 'pass10', 'manager', 2);

INSERT INTO manages VALUES (1, 5);
INSERT INTO manages VALUES (2, 10);

INSERT INTO customers VALUES (11, 'notes11', 1);
INSERT INTO customers VALUES (12, 'notes12', 1);
INSERT INTO customers VALUES (13, 'notes13', 1);
INSERT INTO customers VALUES (14, '', 2);
INSERT INTO customers VALUES (15, '', 2);

INSERT INTO customers VALUES (16, 'notes16', 6);
INSERT INTO customers VALUES (17, 'notes17', 6);
INSERT INTO customers VALUES (18, 'notes18', 6);
INSERT INTO customers VALUES (19, '', 7);
INSERT INTO customers VALUES (20, '', 7);

INSERT INTO test_drives VALUES (11, 2000, "2019-10-03 07:42:30");
INSERT INTO test_drives VALUES (11, 2001, "2019-11-03 10:42:30");
INSERT INTO test_drives VALUES (14, 2002, "2019-07-03 11:42:30");
INSERT INTO test_drives VALUES (14, 2003, "2019-08-03 12:42:30");
INSERT INTO test_drives VALUES (14, 2004, "2019-09-03 08:42:30");
INSERT INTO purchase_vehicle VALUES (11, 20, 5);
INSERT INTO purchase_vehicle VALUES (11, 135, 5);
INSERT INTO purchase_vehicle VALUES (14, 486, 5);
INSERT INTO purchase_vehicle VALUES (14, 584, 5);
INSERT INTO purchase_vehicle VALUES (14, 889, 5);
UPDATE vehicles SET vehicles.sale_datetime = NOW() WHERE vehicles.stock_number = 20 AND vehicles.dealership_number = 1;
UPDATE vehicles SET vehicles.sale_datetime = NOW() WHERE vehicles.stock_number = 135 AND vehicles.dealership_number = 1;
UPDATE vehicles SET vehicles.sale_datetime = NOW() WHERE vehicles.stock_number = 486 AND vehicles.dealership_number = 1;
UPDATE vehicles SET vehicles.sale_datetime = NOW() WHERE vehicles.stock_number = 584 AND vehicles.dealership_number = 1;
UPDATE vehicles SET vehicles.sale_datetime = NOW() WHERE vehicles.stock_number = 889 AND vehicles.dealership_number = 1;

INSERT INTO test_drives VALUES (16, 463, "2019-11-03 07:42:30");
INSERT INTO test_drives VALUES (16, 485, "2019-12-03 09:42:30");
INSERT INTO test_drives VALUES (19, 600, "2019-08-03 09:42:30");
INSERT INTO test_drives VALUES (19, 612, "2019-09-03 09:42:30");
INSERT INTO test_drives VALUES (19, 651, "2019-07-03 09:42:30");
INSERT INTO purchase_vehicle VALUES (16, 463, 5);
INSERT INTO purchase_vehicle VALUES (16, 485, 5);
INSERT INTO purchase_vehicle VALUES (19, 600, 5);
INSERT INTO purchase_vehicle VALUES (19, 612, 5);
INSERT INTO purchase_vehicle VALUES (19, 651, 5);
UPDATE vehicles SET vehicles.sale_datetime = NOW() WHERE vehicles.stock_number = 463 AND vehicles.dealership_number = 2;
UPDATE vehicles SET vehicles.sale_datetime = NOW() WHERE vehicles.stock_number = 485 AND vehicles.dealership_number = 2;
UPDATE vehicles SET vehicles.sale_datetime = NOW() WHERE vehicles.stock_number = 600 AND vehicles.dealership_number = 2;
UPDATE vehicles SET vehicles.sale_datetime = NOW() WHERE vehicles.stock_number = 612 AND vehicles.dealership_number = 2;
UPDATE vehicles SET vehicles.sale_datetime = NOW() WHERE vehicles.stock_number = 651 AND vehicles.dealership_number = 2;

INSERT INTO test_drives VALUES (12, 2000, "2019-11-03 07:42:30");
INSERT INTO test_drives VALUES (12, 2001, "2019-12-03 09:42:30");
INSERT INTO purchase_vehicle VALUES (12, 2000, 5);
INSERT INTO purchase_vehicle VALUES (12, 2001, 5);
UPDATE vehicles SET vehicles.sale_datetime = NOW() WHERE vehicles.stock_number = 2000 AND vehicles.dealership_number = 1;
UPDATE vehicles SET vehicles.sale_datetime = NOW() WHERE vehicles.stock_number = 2001 AND vehicles.dealership_number = 1;
UPDATE vehicles SET vehicles.delivery_datetime = NOW() WHERE vehicles.stock_number = 2000 AND vehicles.dealership_number = 1;
UPDATE vehicles SET vehicles.delivery_datetime = NOW() WHERE vehicles.stock_number = 2001 AND vehicles.dealership_number = 1;

INSERT INTO test_drives VALUES (18, 2002, "2019-08-03 09:42:30");
INSERT INTO test_drives VALUES (18, 2003, "2019-09-03 09:42:30");
INSERT INTO purchase_vehicle VALUES (18, 2002, 5);
INSERT INTO purchase_vehicle VALUES (18, 2003, 5);
UPDATE vehicles SET vehicles.sale_datetime = "2019-10-14 09:22:30" WHERE vehicles.stock_number = 2002 AND vehicles.dealership_number = 2;
UPDATE vehicles SET vehicles.sale_datetime = "2019-10-15 09:32:30" WHERE vehicles.stock_number = 2003 AND vehicles.dealership_number = 2;

INSERT INTO test_drives VALUES (18, 2004, NOW());
INSERT INTO test_drives VALUES (18, 2005, "2015-04-30 11:32:30");
INSERT INTO test_drives VALUES (18, 2006, "2019-10-30 11:32:30");
INSERT INTO purchase_vehicle VALUES (18, 2004, 5);
INSERT INTO purchase_vehicle VALUES (18, 2005, 5);
INSERT INTO purchase_vehicle VALUES (18, 2006, 5);
UPDATE vehicles SET vehicles.sale_datetime = NOW() WHERE vehicles.stock_number = 2004 AND vehicles.dealership_number = 2;
UPDATE vehicles SET vehicles.sale_datetime = "2015-05-30 11:32:30" WHERE vehicles.stock_number = 2005 AND vehicles.dealership_number = 2;
UPDATE vehicles SET vehicles.sale_datetime = "2019-11-30 11:32:30" WHERE vehicles.stock_number = 2006 AND vehicles.dealership_number = 2;
UPDATE vehicles SET vehicles.delivery_datetime = NOW() WHERE vehicles.stock_number = 2004 AND vehicles.dealership_number = 2;
UPDATE vehicles SET vehicles.delivery_datetime = "2015-06-30 11:32:30" WHERE vehicles.stock_number = 2005 AND vehicles.dealership_number = 2;
UPDATE vehicles SET vehicles.delivery_datetime = "2019-11-30 11:32:30" WHERE vehicles.stock_number = 2006 AND vehicles.dealership_number = 2;