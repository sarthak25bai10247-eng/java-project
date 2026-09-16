/*
 * Truck.java
 * ------------------------------------------------------------
 * CHILD CLASS of Vehicle.
 * Extra property : loadCapacity (in tons)
 * Business rule  : Rs.100 extra per ton per day (heavy vehicle charge).
 * ------------------------------------------------------------
 */
public class Truck extends Vehicle {

    private double loadCapacity;   // in tons

    public Truck(String vehicleId, String brand, String model, double rentalRate, double loadCapacity) {
        super(vehicleId, brand, model, rentalRate);
        this.loadCapacity = loadCapacity;
    }

    public double getLoadCapacity() { return loadCapacity; }

    @Override
    public String getType() {
        return "Truck";
    }

    @Override
    public void displayDetails() {
        System.out.println(commonInfo() + " | Load: " + loadCapacity + " tons");
    }

    @Override
    public double calculateRentalCost(int days) {
        double cost = super.calculateRentalCost(days);
        cost += 100 * loadCapacity * days;    // heavy load surcharge
        return cost;
    }
}
