/*
 * Vehicle.java
 * ------------------------------------------------------------
 * ABSTRACT PARENT CLASS of Car, Bike and Truck.
 *
 * Demonstrates:
 *   - Abstraction  : abstract class + abstract methods
 *   - Encapsulation: private fields + public getters/setters
 *   - Inheritance  : Car/Bike/Truck extend this class
 *   - Interface    : implements Rentable
 *   - Overloading  : calculateRentalCost() written twice
 * ------------------------------------------------------------
 */
public abstract class Vehicle implements Rentable {

    // ---------- ENCAPSULATION: data is private ----------
    private String vehicleId;
    private String brand;
    private String model;
    private double rentalRate;      // rent per day
    private boolean available;      // true = free, false = rented

    // ---------- CONSTRUCTOR ----------
    public Vehicle(String vehicleId, String brand, String model, double rentalRate) {
        this.vehicleId  = vehicleId;
        this.brand      = brand;
        this.model      = model;
        this.rentalRate = rentalRate;
        this.available  = true;     // a new vehicle is always free
    }

    // ---------- GETTERS AND SETTERS (controlled access) ----------
    public String getVehicleId()  { return vehicleId; }
    public String getBrand()      { return brand; }
    public String getModel()      { return model; }
    public double getRentalRate() { return rentalRate; }

    public void setBrand(String brand) { this.brand = brand; }
    public void setModel(String model) { this.model = model; }

    // A setter with VALIDATION - this is why fields are private
    public void setRentalRate(double rentalRate) {
        if (rentalRate <= 0) {
            System.out.println("!! Rental rate must be positive. Value ignored.");
            return;
        }
        this.rentalRate = rentalRate;
    }

    // ---------- ABSTRACTION: no body here ----------
    // Every child class MUST decide how to print itself
    public abstract void displayDetails();

    // Every child class MUST tell us what kind of vehicle it is
    public abstract String getType();

    // ---------- METHOD OVERLOADING (same name, different parameters) ----------
    // Version 1: plain cost
    public double calculateRentalCost(int days) {
        return rentalRate * days;
    }

    // Version 2: cost after a discount percentage
    public double calculateRentalCost(int days, double discountPercent) {
        double cost = calculateRentalCost(days);
        return cost - (cost * discountPercent / 100.0);
    }

    // ---------- INTERFACE METHODS (implemented from Rentable) ----------
    @Override
    public void rentVehicle() throws RentalException {
        if (!available) {
            throw new RentalException("Vehicle " + vehicleId + " is already rented.");
        }
        available = false;
    }

    @Override
    public void returnVehicle() throws RentalException {
        if (available) {
            throw new RentalException("Vehicle " + vehicleId + " was never rented out.");
        }
        available = true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    // Helper used by all child classes so the printing style stays the same
    protected String commonInfo() {
        return String.format("%-6s | %-8s | %-10s | %-10s | Rs.%-8.2f | %s",
                vehicleId, getType(), brand, model, rentalRate,
                (available ? "Available" : "Rented"));
    }

    // ---------- OVERRIDING a method of class Object ----------
    @Override
    public String toString() {
        return getType() + " [" + vehicleId + "] " + brand + " " + model;
    }
}
