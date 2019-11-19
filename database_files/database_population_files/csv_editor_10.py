import csv

sourceFile = open("source.csv", "r")
sourceFileContents = csv.reader(sourceFile, delimiter=",")

addressesOutputFile = open("addresses.csv", "w", newline="")
addressesWriter = csv.writer(addressesOutputFile, delimiter=",")

dealershipsOutputFile = open("dealerships.csv", "w", newline="")
dealershipsWriter = csv.writer(dealershipsOutputFile, delimiter=",")

peopleOutputFile = open("people.csv", "w", newline="")
peopleWriter = csv.writer(peopleOutputFile, delimiter=",")

vehiclesOutputFile = open("vehicles.csv", "w", newline="")
vehiclesWriter = csv.writer(vehiclesOutputFile, delimiter=",")

# street, zip, city, state, phone_number,
# id, first_name, last_name, email, dealership_number,
# username, password, role, customer_notes, assigned_emp_id,
# manager_id, stock_number, make, model, sale_date,
# delivery_date, year, new, image, price,
# car_and_driver_hyperlink, datetime

x = 0

print("starting...")

for sourceRow in sourceFileContents:
    addressesWriter.writerow([sourceRow[0], sourceRow[1], sourceRow[2], sourceRow[3], sourceRow[4]])
        
    if x == 0:
        dealershipsWriter.writerow([sourceRow[9], "dealership_name", sourceRow[0], sourceRow[1]])
        
    elif x > 0 and x <= 20:
        dealershipsWriter.writerow([x, "Dealership #" + str(x), sourceRow[0], sourceRow[1]])
    
    peopleWriter.writerow([sourceRow[5], sourceRow[6], sourceRow[7], sourceRow[8], sourceRow[0], sourceRow[1]])
        
    if x == 0:
        dealership_number = sourceRow[9]
    
    else:
        dealership_number = int(sourceRow[9])
        dealership_number = (dealership_number % 20) + 1
    
    if (x % 4) == 0:
        vehiclesWriter.writerow([sourceRow[16], sourceRow[17], sourceRow[18], sourceRow[19], sourceRow[20], sourceRow[21], sourceRow[22], sourceRow[23], sourceRow[24], sourceRow[25], dealership_number])
    
    elif (x % 2) == 0:
        vehiclesWriter.writerow([sourceRow[16], sourceRow[17], sourceRow[18], sourceRow[19], "0000-00-00 00:00:00", sourceRow[21], sourceRow[22], sourceRow[23], sourceRow[24], sourceRow[25], dealership_number])
    
    else:
        vehiclesWriter.writerow([sourceRow[16], sourceRow[17], sourceRow[18], "0000-00-00 00:00:00", "0000-00-00 00:00:00", sourceRow[21], sourceRow[22], sourceRow[23], sourceRow[24], sourceRow[25], dealership_number])

    x = x + 1

print("done")

addressesOutputFile.close()
dealershipsOutputFile.close()
peopleOutputFile.close()
vehiclesOutputFile.close()
sourceFile.close()