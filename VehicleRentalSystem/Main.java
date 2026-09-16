/*
 * Main.java
 * ------------------------------------------------------------
 * Entry point of the application.
 * Responsible ONLY for talking to the user (menu + input),
 * while VehicleRentalSystem does the actual work.
 *
 * Demonstrates EXCEPTION HANDLING:
 *   - try / catch / finally
 *   - custom checked exception (RentalException)
 *   - NumberFormatException for bad numeric input
 * ------------------------------------------------------------
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static VehicleRentalSystem system = new VehicleRentalSystem();

    public static void main(String[] args) {

        system.loadSampleData();   // preload a few vehicles and customers
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1: addVehicleMenu();       break;
                    case 2: displayVehiclesMenu();  break;
                    case 3: searchVehicleMenu();    break;
                    case 4: registerCustomerMenu(); break;
                    case 5: rentVehicleMenu();      break;
                    case 6: returnVehicleMenu();    break;
                    case 7: calculateCostMenu();    break;
                    case 8: system.displayAllCustomers(); break;
                    case 9: system.displayAllRentals();   break;
                    case 0:
                        running = false;
                        System.out.println("Thank you for using the Vehicle Rental System. Bye!");
                        break;
                    default:
                        // invalid menu number -> throw our own exception
                        throw new RentalException("Invalid choice. Please pick a number from 0 to 9.");
                }
            } catch (RentalException e) {
                System.out.println("ERROR: " + e.getMessage());
            } catch (Exception e) {
                // safety net so the program never crashes
                System.out.println("UNEXPECTED ERROR: " + e);
            } finally {
                System.out.println();   // always runs - keeps output tidy
            }
        }
        sc.close();
    }

    // ------------------------------------------------------------
    private static void printMenu() {
        System.out.println("========= VEHICLE RENTAL SYSTEM =========");
        System.out.println("1. Add Vehicle");
        System.out.println("2. Display Vehicles");
        System.out.println("3. Search Vehicle");
        System.out.println("4. Register Customer");
        System.out.println("5. Rent Vehicle");
        System.out.println("6. Return Vehicle");
        System.out.println("7. Calculate Rental Cost");
        System.out.println("8. Display Customer Details");
        System.out.println("9. Display Rental Details");
        System.out.println("0. Exit");
        System.out.println("=========================================");
    }

    // ------------------------------------------------------------
    // 1. ADD VEHICLE  -> creates Car / Bike / Truck (POLYMORPHISM)
    // ------------------------------------------------------------
    private static void addVehicleMenu() throws RentalException {
        System.out.println("Vehicle type -> 1.Car  2.Bike  3.Truck");
        int type = readInt("Choose type: ");

        String id    = readString("Vehicle ID    : ");
        String brand = readString("Brand         : ");
        String model = readString("Model         : ");
        double rate  = readDouble("Rent per day  : ");

        Vehicle v;   // PARENT REFERENCE
        switch (type) {
            case 1:
                int seats = readInt("Number of seats: ");
                String ac = readString("Has AC (y/n)  : ");
                v = new Car(id, brand, model, rate, seats, ac.equalsIgnoreCase("y"));
                break;
            case 2:
                int cc = readInt("Engine (cc)   : ");
                v = new Bike(id, brand, model, rate, cc);
                break;
            case 3:
                double load = readDouble("Load capacity (tons): ");
                v = new Truck(id, brand, model, rate, load);
                break;
            default:
                throw new RentalException("Unknown vehicle type.");
        }
        system.addVehicle(v);
    }

    // ------------------------------------------------------------
    private static void displayVehiclesMenu() {
        System.out.println("1. All vehicles   2. Only available vehicles");
        int c = readInt("Choose: ");
        if (c == 2) {
            system.displayAvailableVehicles();
        } else {
            system.displayAllVehicles();
        }
    }

    // ------------------------------------------------------------
    // 3. SEARCH -> demonstrates the three OVERLOADED search methods
    // ------------------------------------------------------------
    private static void searchVehicleMenu() throws RentalException {
        System.out.println("1. By ID   2. By Brand+Model   3. By maximum rate");
        int c = readInt("Choose: ");

        if (c == 1) {
            Vehicle v = system.searchVehicle(readString("Vehicle ID: "));
            System.out.println("Vehicle found:");
            v.displayDetails();
        } else if (c == 2) {
            String brand = readString("Brand: ");
            String model = readString("Model: ");
            printList(system.searchVehicle(brand, model));
        } else if (c == 3) {
            double max = readDouble("Maximum rent per day: ");
            printList(system.searchVehicle(max));
        } else {
            throw new RentalException("Invalid search option.");
        }
    }

    private static void printList(ArrayList<Vehicle> list) {
        if (list.isEmpty()) {
            System.out.println("No matching vehicle found.");
            return;
        }
        for (Vehicle v : list) {
            v.displayDetails();
        }
    }

    // ------------------------------------------------------------
    private static void registerCustomerMenu() throws RentalException {
        Customer c = new Customer(
                readString("Customer ID     : "),
                readString("Name            : "),
                readString("Phone Number    : "),
                readString("Driving License : "));
        system.registerCustomer(c);
    }

    // ------------------------------------------------------------
    private static void rentVehicleMenu() throws RentalException {
        String cid  = readString("Customer ID : ");
        String vid  = readString("Vehicle ID  : ");
        int days    = readInt("Number of days: ");

        Rental r = system.rentVehicle(cid, vid, days);
        System.out.println(">> Rental created successfully!");
        System.out.println("   Rental ID  : " + r.getRentalId());
        System.out.println("   Vehicle    : " + r.getVehicle());
        System.out.println("   Customer   : " + r.getCustomer());
        System.out.println("   Days       : " + r.getNumberOfDays());
        System.out.printf("   Total Cost : Rs.%.2f%n", r.getTotalCost());
    }

    // ------------------------------------------------------------
    private static void returnVehicleMenu() throws RentalException {
        String vid = readString("Vehicle ID to return: ");
        Rental r = system.returnVehicle(vid);
        System.out.println(">> Vehicle returned successfully.");
        System.out.printf("   Rental %s closed. Amount payable: Rs.%.2f%n",
                r.getRentalId(), r.getTotalCost());
    }

    // ------------------------------------------------------------
    private static void calculateCostMenu() throws RentalException {
        String vid = readString("Vehicle ID   : ");
        int days   = readInt("Number of days: ");
        double disc = readDouble("Discount % (0 if none): ");

        double cost = (disc == 0)
                ? system.estimateCost(vid, days)              // overloaded version 1
                : system.estimateCost(vid, days, disc);       // overloaded version 2

        System.out.printf(">> Estimated rental cost = Rs.%.2f%n", cost);
    }

    // ============================================================
    // INPUT HELPERS with exception handling
    // ============================================================
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("!! Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("!! Please enter a valid number.");
            }
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
}
