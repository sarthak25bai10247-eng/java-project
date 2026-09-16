# java-project
Console-based Vehicle Rental System in Java demonstrating core OOP concepts (inheritance, polymorphism, abstraction, encapsulation, interfaces, exception handling)
# Vehicle Rental System (Java Console Application)
 
A menu-driven, console-based Vehicle Rental System built in core Java as a second-year
Object-Oriented Programming mini project. No frameworks, no database, no external
libraries — just plain Java and the standard library.
 
---
 
## Overview
 
The application simulates the day-to-day working of a small vehicle rental agency.
An operator can add vehicles to the fleet, register customers, rent a vehicle out for a
given number of days, take it back, and view rental records — all from the terminal.
 
Three kinds of vehicles are supported (Car, Bike, Truck). Each one inherits common
behaviour from an abstract `Vehicle` class but calculates its own rental cost using its
own pricing rule, which is where polymorphism does the real work in this project.
 
The project was written specifically to demonstrate the major OOP pillars in a single,
logically connected program rather than as isolated code snippets:
 
| Concept | Demonstrated by |
|---|---|
| Classes & Objects | All 10 classes |
| Encapsulation | Private fields with validated setters in `Vehicle`, `Customer` |
| Inheritance | `Car`, `Bike`, `Truck` extend `Vehicle` |
| Abstraction | `abstract class Vehicle` with abstract `displayDetails()`, `getType()` |
| Polymorphism | `ArrayList<Vehicle>` holding mixed child objects; dynamic method dispatch |
| Method Overloading | `calculateRentalCost()`, `searchVehicle()`, `estimateCost()`, `Car` constructors |
| Method Overriding | `displayDetails()`, `calculateRentalCost()`, `toString()` |
| Constructors | `super(...)` in children, `this(...)` chaining in `Car` |
| Interfaces | `Rentable` implemented by `Vehicle` |
| Collections | Three `ArrayList`s inside `VehicleRentalSystem` |
| Exception Handling | Custom `RentalException`, `NumberFormatException`, `try/catch/finally` |
 
---
 
## Features
 
1. **Add Vehicle** — add a Car, Bike or Truck with its own extra attributes
   (seats/AC, engine capacity, load capacity). Duplicate IDs are rejected.
2. **Display Vehicles** — list the whole fleet or only the vehicles currently free.
3. **Search Vehicle** — three overloaded searches: by ID, by brand + model, or by
   maximum rent per day.
4. **Register Customer** — store customer ID, name, phone number and driving licence.
5. **Rent Vehicle** — links a customer to an available vehicle for N days, auto-generates
   a rental ID and computes the total cost.
6. **Return Vehicle** — closes the rental and marks the vehicle available again.
7. **Calculate Rental Cost** — estimate a cost before renting, with an optional discount
   percentage.
8. **Display Customer Details** — tabular list of all registered customers.
9. **Display Rental Details** — all rental records with ONGOING / RETURNED status.
0. **Exit**
Other behaviour worth noting:
 
- **Sample data is preloaded** (4 vehicles, 2 customers) so the program is usable the
  moment it starts.
- **Input validation** — non-numeric input simply re-prompts instead of crashing.
- **Business rules are enforced**: a rented vehicle cannot be rented again, an unrented
  vehicle cannot be returned, days must be at least 1, discount must be 0–100.
### Pricing rules
 
Base formula: `totalCost = rentalRate × numberOfDays`
 
| Vehicle | Extra charge |
|---|---|
| Car (with AC) | + ₹200 per day |
| Bike | + ₹50 per day (helmet) |
| Truck | + ₹100 per ton per day |
 
Example — Truck at ₹3000/day, 2.5 tons, 2 days, 10% discount:
`3000×2 = 6000` + `100×2.5×2 = 500` → `6500` − 10% → **₹5850**
 
---
 
## Technologies / Tools Used
 
- **Language:** Java (Core Java / JDK 8 or above; tested on JDK 21)
- **Paradigm:** Object-Oriented Programming
- **Libraries:** `java.util.Scanner`, `java.util.ArrayList` (standard library only)
- **Compiler / Runtime:** `javac`, `java`
- **IDE (any of):** VS Code, IntelliJ IDEA, Eclipse, BlueJ, or a plain text editor
- **Interface:** Console / terminal
- **No** database, GUI toolkit, Spring Boot, Maven/Gradle or third-party dependency
---
 
## Project Structure
 
```
VehicleRentalSystem/
├── Main.java                  # entry point: menu, Scanner input, try/catch
├── VehicleRentalSystem.java   # core logic + ArrayList storage
├── Vehicle.java               # abstract parent class
├── Car.java                   # child class  (numberOfSeats, hasAC)
├── Bike.java                  # child class  (engineCapacity)
├── Truck.java                 # child class  (loadCapacity)
├── Customer.java              # customer details (encapsulated)
├── Rental.java                # one rental record (customer + vehicle + days + cost)
├── Rentable.java              # interface: rentVehicle(), returnVehicle(), isAvailable()
├── RentalException.java       # custom checked exception
└── README.md
```
 
Layering: `Main` handles only input/output, `VehicleRentalSystem` holds the data and the
logic, and the model classes hold the state. `Main` never stores data and
`VehicleRentalSystem` never reads the keyboard.
 
---
 
## Installation & How to Run
 
### Prerequisites
 
Install the JDK (8 or above) and confirm it is on your PATH:
 
```bash
java -version
javac -version
```
 
If `javac` is missing you have only the JRE — install the full JDK
(for example OpenJDK from adoptium.net, or `sudo apt install default-jdk` on Ubuntu).
 
### Option A — Command line (recommended)
 
```bash
# 1. Go to the folder that contains all the .java files
cd VehicleRentalSystem
 
# 2. Compile every file at once
javac *.java
 
# 3. Run the program
java Main
```
 
On Windows the commands are identical in Command Prompt or PowerShell.
 
To keep `.class` files out of the source folder:
 
```bash
javac -d bin *.java
java -cp bin Main
```
 
### Option B — IDE
 
1. Create a new Java project.
2. Copy all 10 `.java` files into the `src` folder (keep them in the **default package** —
   none of the files has a `package` statement).
3. Right-click `Main.java` → Run.
### Common issues
 
| Problem | Cause / Fix |
|---|---|
| `Could not find or load main class Main` | You are not in the folder with the `.class` files, or you typed `java Main.java`/`java Main.class`. Use `java Main`. |
| `class Car is public, should be declared in a file named Car.java` | A file was renamed. Each public class must sit in a file of the same name. |
| `cannot find symbol: class Vehicle` | You compiled a single file. Compile all of them together with `javac *.java`. |
| `javac: command not found` | JRE installed instead of JDK. |
 
---
 
## Testing Instructions
 
The program is menu-driven, so testing is a matter of walking through the cases below and
comparing the printed output with the expected result.
 
### Test 1 — Preloaded data loads correctly
Choose `2` → `1`. Expect 4 vehicles (V101, V102, V201, V301), all marked **Available**.
Choose `8`. Expect customers C001 and C002.
 
### Test 2 — Polymorphic display
In the same vehicle list, confirm each row ends with a *different* extra field:
seats/AC for cars, engine cc for the bike, load tons for the truck. This proves the child
version of `displayDetails()` is being called through a `Vehicle` reference.
 
### Test 3 — Rent a vehicle and check the cost
Choose `5` → customer `C001`, vehicle `V101`, days `3`.
Expected total: `1500×3 + 200×3 = ₹5100`.
Then choose `2` → `2`; V101 should no longer appear in the available list.
 
### Test 4 — Double renting is blocked
Choose `5` again with vehicle `V101`.
Expected: `ERROR: Vehicle V101 is already rented.`
 
### Test 5 — Overloaded cost calculation
Choose `7` → vehicle `V301`, days `2`, discount `10`.
Expected: `Rs.5850.00` (see the pricing example above).
Repeat with discount `0`; expected `Rs.6500.00`.
 
### Test 6 — Overloaded search
Choose `3` → `1` → `V201` (found).
Choose `3` → `2` → brand `Maruti`, model `Swift` (found).
Choose `3` → `3` → max rate `500` (only the bike should appear).
 
### Test 7 — Return flow
Choose `6` → `V101`. Expect a success message and the amount payable.
Choose `6` → `V101` again. Expect `ERROR: No ongoing rental found for vehicle V101`.
Choose `9`; rental R1 should now show **RETURNED**.
 
### Test 8 — Exception handling
| Input | Expected output |
|---|---|
| Menu choice `15` | `ERROR: Invalid choice. Please pick a number from 0 to 9.` |
| Menu choice `abc` | `!! Please enter a valid whole number.` then re-prompt |
| Search ID `V999` | `ERROR: No vehicle found with ID: V999` |
| Rent with days `0` | `ERROR: Number of days must be at least 1.` |
| Rent with customer `C999` | `ERROR: No customer found with ID: C999` |
| Add vehicle with ID `V101` | `ERROR: Vehicle ID V101 already exists.` |
 
The program should never terminate with a stack trace — every failure is caught and
reported as a readable message.
 
### Automated smoke test (optional)
 
You can pipe a whole session in at once instead of typing:
 
```bash
printf '2\n1\n5\nC001\nV101\n3\n7\nV301\n2\n10\n6\nV101\n9\n0\n' | java Main
```
 
This adds up to: list vehicles → rent V101 for 3 days → estimate the truck with 10% off →
return V101 → show rental records → exit.
 
---
 
## Sample Output
 
```
========= VEHICLE RENTAL SYSTEM =========
1. Add Vehicle
2. Display Vehicles
3. Search Vehicle
4. Register Customer
5. Rent Vehicle
6. Return Vehicle
7. Calculate Rental Cost
8. Display Customer Details
9. Display Rental Details
0. Exit
=========================================
Enter your choice: 2
1. All vehicles   2. Only available vehicles
Choose: 1
ID     | Type     | Brand      | Model      | Rate/Day    | Status
---------------------------------------------------------------------------
V101   | Car      | Maruti     | Swift      | Rs.1500.00  | Available | Seats: 5 | AC: Yes
V102   | Car      | Hyundai    | i20        | Rs.1800.00  | Available | Seats: 5 | AC: No
V201   | Bike     | Honda      | Shine      | Rs.400.00   | Available | Engine: 125 cc
V301   | Truck    | Tata       | 407        | Rs.3000.00  | Available | Load: 2.5 tons
 
Enter your choice: 5
Customer ID : C001
Vehicle ID  : V101
Number of days: 3
>> Rental created successfully!
   Rental ID  : R1
   Vehicle    : Car [V101] Maruti Swift
   Customer   : Rahul Sharma (C001)
   Days       : 3
   Total Cost : Rs.5100.00
 
Enter your choice: 6
Vehicle ID to return: V101
>> Vehicle returned successfully.
   Rental R1 closed. Amount payable: Rs.5100.00
 
Enter your choice: 9
ID     | Customer           | Vehicle                | Days | Total Cost    | Status
---------------------------------------------------------------------------------------
R1     | Rahul Sharma (C001) | Car [V101] Maruti Swift | 3    | Rs.5100.00    | RETURNED
 
Enter your choice: 0
Thank you for using the Vehicle Rental System. Bye!
```
 
---
 
## Screenshots
 
Add terminal screenshots here before submitting. Suggested set:
 
| # | Screenshot | File |
|---|---|---|
| 1 | Main menu on startup | `screenshots/01-menu.png` |
| 2 | Vehicle list showing all three types | `screenshots/02-vehicles.png` |
| 3 | Successful rental with total cost | `screenshots/03-rent.png` |
| 4 | Rental records table | `screenshots/04-rentals.png` |
| 5 | An error message (e.g. invalid choice) | `screenshots/05-error.png` |
 
Embed them in Markdown like this:
 
```markdown
![Main Menu](screenshots/01-menu.png)
![Rental Created](screenshots/03-rent.png)
```
 
---
 
## Possible Future Enhancements
 
- File handling (`FileWriter` / serialization) so data survives after the program closes
- Late-return penalty and an actual return-date calculation using `LocalDate`
- `HashMap<String, Vehicle>` for O(1) lookup by ID instead of a linear search
- A `Rentable` implementation for non-vehicle items to show interface reuse
- JUnit test cases for the cost-calculation logic
---
 
## Author
 
*Add your name, roll number, course/semester and college here before submission.*
 
