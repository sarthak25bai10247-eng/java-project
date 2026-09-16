/*
 * Rentable.java
 * ------------------------------------------------------------
 * INTERFACE: describes WHAT a rentable thing can do,
 * but not HOW it does it. Any class that implements this
 * interface MUST provide a body for all three methods.
 * ------------------------------------------------------------
 */
public interface Rentable {

    // Marks the item as rented out
    void rentVehicle() throws RentalException;

    // Marks the item as returned / available again
    void returnVehicle() throws RentalException;

    // Tells us whether the item can be rented right now
    boolean isAvailable();
}
