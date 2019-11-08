This folder contains the following:  
- all files that end in .csv are database test data files and can be imported directly through MySQL Workbench EXCEPT FOR source.csv which is used as the baseline for the Python script.  
- insert_data_01.sql: this file has manually-defined SQL insert statements that will add a small amount of test data to the remaining tables not covered by the .csv files.  
- delete_data_01.sql: this file will simply delete all rows from all tables (note that there is a MySQL Workbench setting in Edit -> Preferences -> SQL Editor -> "Safe Updates" option that must be disabled for this file to work).  
- csv_editor_08.py: this is the Python script used to convert the source.csv file into the other .csv files.  

Import the files in this order:  
1: addresses.csv  
2: dealerships.csv  
3: people.csv  
4: vehicles.csv  

Then execute "insert_data_01.sql" for the remaining tables.  