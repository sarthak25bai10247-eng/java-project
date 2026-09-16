/*
 * VehicleRentalSystem.java
 * ------------------------------------------------------------
 * The "brain" of the project. It stores all data in ArrayLists
 * and contains every operation the menu can perform.
 *
 * Demonstrates:
 *   - Collections (ArrayList)
 *   - Polymorphism (ArrayList<Vehicle> holding Car/Bike/Truck)
 *   - Method overloading (three searchVehicle methods)
 *   - Exception handling (throws RentalException)
 * ------------------------------------------------------------
 */
import java.util.ArrayList;

public class VehicleRentalSystem {

    // ---------- COLLECTIONS ----------
    private ArrayList<Vehicle> vehicles   = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Rental> rentals     = new ArrayList<>();

    private int rentalCounter = 1;   // used to auto-generate rental ids

    // ============================================================
    // 1. ADD VEHICLE
    // ============================================================
    public void addVehicle(Vehicle v) throws RentalException {
        if (v == null) {
            throw new RentalException("Cannot add a null vehicle.");
        }
        // reject duplicate ids
        for (Vehicle existing : vehicles) {
            if (existing.getVehicleId().equalsIgnoreCase(v.getVehicleId())) {
                throw new RentalException("Vehicle ID " + v.getVehicleId() + " already exists.");
            }
        }
        vehicles.add(v);
        System.out.println(">> " + v.getType() + " added successfully: " + v);
    }

    // ============================================================
    // 2. DISPLAY VEHICLES  (POLYMORPHISM in action)
    // ============================================================
    public void displayAllVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles in the system yet.");
            return;
        }
        printVehicleHeader();
        for (Vehicle v : vehicles) {
            // Same call, different behaviour depending on the real object
            v.displayDetails();
        }
    }

    public void displayAvailableVehicles() {
        boolean found = false;
        printVehicleHeader();
        for (Vehicle v : vehicles) {
            if (v.isAvailable()) {
                v.displayDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("(No vehicle is free at the moment.)");
        }
    }

    private void printVehicleHeader() {
        System.out.println("ID     | Type     | Brand      | Model      | Rate/Day    | Status");
        System.out.println("---------------------------------------------------------------------------");
    }

    // ============================================================
    // 3. SEARCH  -- METHOD OVERLOADING (same name, 3 signatures)
    // ============================================================

    // (a) search by vehicle id -> returns one vehicle
    public Vehicle searchVehicle(String vehicleId) throws RentalException {
        for (Vehicle v : vehicles) {
            if (v.getVehicleId().equalsIgnoreCase(vehicleId)) {
                return v;
            }
        }
        throw new RentalException("No vehicle found with ID: " + vehicleId);
    }

    // (b) search by brand AND model -> returns a list
    public ArrayList<Vehicle> searchVehicle(String brand, String model) {
        ArrayList<Vehicle> result = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (v.getBrand().equalsIgnoreCase(brand) && v.getModel().equalsIgnoreCase(model)) {
                result.add(v);
            }
        }
        return result;
    }

    // (c) search by maximum rate -> returns a list
    public ArrayList<Vehicle> searchVehicle(double maxRate) {
        ArrayList<Vehicle> result = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (v.getRentalRate() <= maxRate) {
                result.add(v);
            }
        }
        return result;
    }

    // ============================================================
    // 4. CUSTOMERS
    // ============================================================
    public void registerCustomer(Customer c) throws RentalException {
        for (Customer existing : customers) {
            if (existing.getCustomerId().equalsIgnoreCase(c.getCustomerId())) {
                throw new RentalException("Customer ID " + c.getCustomerId() + " already exists.");
            }
        }
        customers.add(c);
        System.out.println(">> Customer registered: " + c);
    }

    public Customer searchCustomer(String customerId) throws RentalException {
        for (Customer c : customers) {
            if (c.getCustomerId().equalsIgnoreCase(customerId)) {
                return c;
            }
        }
        throw new RentalException("No customer found with ID: " + customerId);
    }

    public void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers registered yet.");
            return;
        }
        System.out.println("ID     | Name            | Phone        | Driving License");
        System.out.println("---------------------------------------------------------");
        for (Customer c : customers) {
            c.displayDetails();
        }
    }

    // ============================================================
    // 5. RENT A VEHICLE
    // ============================================================
    public Rental rentVehicle(String customerId, String vehicleId, int days) throws RentalException {
        if (days <= 0) {
            throw new RentalException("Number of days must be at least 1.");
        }
        Customer customer = searchCustomer(customerId);   // may throw
        Vehicle vehicle   = searchVehicle(vehicleId);     // may throw

        vehicle.rentVehicle();     // interface method; throws if already rented

        String rentalId = "R" + (rentalCounter++);
        Rental rental = new Rental(rentalId, customer, vehicle, days);
        rentals.add(rental);
        return rental;
    }

    // ============================================================
    // 6. RETURN A VEHICLE
    // ============================================================
    public Rental returnVehicle(String vehicleId) throws RentalException {
        Vehicle vehicle = searchVehicle(vehicleId);

        for (Rental r : rentals) {
            if (r.isActive() && r.getVehicle().getVehicleId().equalsIgnoreCase(vehicleId)) {
                vehicle.returnVehicle();   // interface method
                r.closeRental();
                return r;
            }
        }
        throw new RentalException("No ongoing rental found for vehicle " + vehicleId);
    }

    // ============================================================
    // 7. COST ESTIMATE (uses overloaded + overridden methods)
    // ============================================================
    public double estimateCost(String vehicleId, int days) throws RentalException {
        if (days <= 0) {
            throw new RentalException("Number of days must be at least 1.");
        }
        Vehicle v = searchVehicle(vehicleId);
        return v.calculateRentalCost(days);
    }

    public double estimateCost(String vehicleId, int days, double discountPercent) throws RentalException {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new RentalException("Discount must be between 0 and 100.");
        }
        double cost = estimateCost(vehicleId, days);
        return cost - (cost * discountPercent / 100.0);
    }

    // ============================================================
    // 8. RENTAL RECORDS
    // ============================================================
    public void displayAllRentals() {
        if (rentals.isEmpty()) {
            System.out.println("No rental records yet.");
            return;
        }
        System.out.println("ID     | Customer           | Vehicle                | Days | Total Cost    | Status");
        System.out.println("---------------------------------------------------------------------------------------");
        for (Rental r : rentals) {
            r.displayDetails();
        }
    }

    // ============================================================
    // SAMPLE DATA so the program is usable immediately
    // ============================================================
    public void loadSampleData() {
        try {
            addVehicleSilently(new Car("V101", "Maruti", "Swift", 1500, 5));
            addVehicleSilently(new Car("V102", "Hyundai", "i20", 1800, 5, false));
            addVehicleSilently(new Bike("V201", "Honda", "Shine", 400, 125));
            addVehicleSilently(new Truck("V301", "Tata", "407", 3000, 2.5));

            customers.add(new Customer("C001", "Rahul Sharma", "9876543210", "MP04-2021-001"));
            customers.add(new Customer("C002", "Ananya Verma", "9123456780", "MP09-2022-117"));
        } catch (RentalException e) {
            System.out.println("Sample data error: " + e.getMessage());
        }
    }

    private void addVehicleSilently(Vehicle v) throws RentalException {
        vehicles.add(v);
    }
}
