/*
 * Bike.java
 * ------------------------------------------------------------
 * CHILD CLASS of Vehicle.
 * Extra property : engineCapacity (in cc)
 * Business rule  : Rs.50 per day helmet charge is added.
 * ------------------------------------------------------------
 */
public class Bike extends Vehicle {

    private int engineCapacity;   // in cc

    public Bike(String vehicleId, String brand, String model, double rentalRate, int engineCapacity) {
        super(vehicleId, brand, model, rentalRate);
        this.engineCapacity = engineCapacity;
    }

    public int getEngineCapacity() { return engineCapacity; }

    @Override
    public String getType() {
        return "Bike";
    }

    @Override
    public void displayDetails() {
        System.out.println(commonInfo() + " | Engine: " + engineCapacity + " cc");
    }

    @Override
    public double calculateRentalCost(int days) {
        double cost = super.calculateRentalCost(days);
        cost += 50 * days;               // helmet rent
        return cost;
    }
}
