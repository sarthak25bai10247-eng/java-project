/*
 * Rental.java
 * ------------------------------------------------------------
 * One rental record = which CUSTOMER took which VEHICLE
 * for how many DAYS and at what TOTAL COST.
 *
 * This class shows COMPOSITION ("has-a" relationship):
 * a Rental HAS-A Customer and HAS-A Vehicle.
 *
 * It also shows POLYMORPHISM: the field is of type Vehicle,
 * but at runtime it may hold a Car, a Bike or a Truck.
 * ------------------------------------------------------------
 */
public class Rental {

    private String rentalId;
    private Customer customer;
    private Vehicle vehicle;        // parent reference -> any child object
    private int numberOfDays;
    private double totalCost;
    private boolean active;         // true = not returned yet

    public Rental(String rentalId, Customer customer, Vehicle vehicle, int numberOfDays) {
        this.rentalId     = rentalId;
        this.customer     = customer;
        this.vehicle      = vehicle;
        this.numberOfDays = numberOfDays;
        // DYNAMIC METHOD DISPATCH: the child's version of
        // calculateRentalCost() is chosen automatically at runtime.
        this.totalCost    = vehicle.calculateRentalCost(numberOfDays);
        this.active       = true;
    }

    public String getRentalId()   { return rentalId; }
    public Customer getCustomer() { return customer; }
    public Vehicle getVehicle()   { return vehicle; }
    public int getNumberOfDays()  { return numberOfDays; }
    public double getTotalCost()  { return totalCost; }
    public boolean isActive()     { return active; }

    public void closeRental() { this.active = false; }

    public void displayDetails() {
        System.out.printf("%-6s | %-18s | %-22s | %-4d | Rs.%-10.2f | %s%n",
                rentalId,
                customer.toString(),
                vehicle.toString(),
                numberOfDays,
                totalCost,
                (active ? "ONGOING" : "RETURNED"));
    }
}
