/*
 * Car.java
 * ------------------------------------------------------------
 * CHILD CLASS of Vehicle (INHERITANCE).
 * Extra property : numberOfSeats, hasAC
 * Overrides      : displayDetails(), getType(), calculateRentalCost()
 * Also shows CONSTRUCTOR OVERLOADING + use of this(...) and super(...)
 * ------------------------------------------------------------
 */
public class Car extends Vehicle {

    private int numberOfSeats;   // extra property of a Car
    private boolean hasAC;

    // Constructor 1 (short version) - assumes the car has AC
    public Car(String vehicleId, String brand, String model, double rentalRate, int numberOfSeats) {
        this(vehicleId, brand, model, rentalRate, numberOfSeats, true);  // calls Constructor 2
    }

    // Constructor 2 (full version)
    public Car(String vehicleId, String brand, String model, double rentalRate,
               int numberOfSeats, boolean hasAC) {
        super(vehicleId, brand, model, rentalRate);   // parent constructor runs first
        this.numberOfSeats = numberOfSeats;
        this.hasAC = hasAC;
    }

    public int getNumberOfSeats() { return numberOfSeats; }
    public boolean isHasAC()      { return hasAC; }

    @Override
    public String getType() {
        return "Car";
    }

    // ---------- METHOD OVERRIDING ----------
    @Override
    public void displayDetails() {
        System.out.println(commonInfo()
                + " | Seats: " + numberOfSeats
                + " | AC: " + (hasAC ? "Yes" : "No"));
    }

    // ---------- METHOD OVERRIDING with extra business rule ----------
    // A car with AC costs Rs.200 extra per day.
    @Override
    public double calculateRentalCost(int days) {
        double cost = super.calculateRentalCost(days);   // reuse parent logic
        if (hasAC) {
            cost += 200 * days;
        }
        return cost;
    }
}
