DELETE FROM sales_emps;
DELETE FROM customers;
DELETE FROM manages;
DELETE FROM schedules;
DELETE FROM test_drives;
DELETE FROM purchase_vehicle;

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

INSERT INTO schedules VALUES("2019-10-03 07:42:30");
INSERT INTO schedules VALUES("2019-11-03 10:42:30");
INSERT INTO schedules VALUES("2019-07-03 11:42:30");
INSERT INTO schedules VALUES("2019-08-03 12:42:30");
INSERT INTO schedules VALUES("2019-09-03 08:42:30");

INSERT INTO test_drives VALUES (11, 20, "2019-10-03 07:42:30");
INSERT INTO test_drives VALUES (11, 135, "2019-11-03 10:42:30");
INSERT INTO test_drives VALUES (14, 486, "2019-07-03 11:42:30");
INSERT INTO test_drives VALUES (14, 584, "2019-08-03 12:42:30");
INSERT INTO test_drives VALUES (14, 889, "2019-09-03 08:42:30");

INSERT INTO schedules VALUES("2019-11-03 07:42:30");
INSERT INTO schedules VALUES("2019-12-03 09:42:30");
INSERT INTO schedules VALUES("2019-08-03 09:42:30");
INSERT INTO schedules VALUES("2019-09-03 09:42:30");
INSERT INTO schedules VALUES("2019-07-03 09:42:30");

INSERT INTO test_drives VALUES (16, 463, "2019-11-03 07:42:30");
INSERT INTO test_drives VALUES (16, 485, "2019-12-03 09:42:30");
INSERT INTO test_drives VALUES (19, 600, "2019-08-03 09:42:30");
INSERT INTO test_drives VALUES (19, 612, "2019-09-03 09:42:30");
INSERT INTO test_drives VALUES (19, 651, "2019-07-03 09:42:30");

INSERT INTO purchase_vehicle VALUES (11, 20, 5);
INSERT INTO purchase_vehicle VALUES (11, 135, 5);
INSERT INTO purchase_vehicle VALUES (14, 486, 5);
INSERT INTO purchase_vehicle VALUES (14, 584, 5);
INSERT INTO purchase_vehicle VALUES (14, 889, 5);

INSERT INTO purchase_vehicle VALUES (16, 463, 5);
INSERT INTO purchase_vehicle VALUES (16, 485, 5);
INSERT INTO purchase_vehicle VALUES (19, 600, 5);
INSERT INTO purchase_vehicle VALUES (19, 612, 5);
INSERT INTO purchase_vehicle VALUES (19, 651, 5);

