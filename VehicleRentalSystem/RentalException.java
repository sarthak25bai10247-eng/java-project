/*
 * RentalException.java
 * ------------------------------------------------------------
 * A user-defined CHECKED exception.
 * We throw this whenever a business rule is broken, e.g.
 *   - vehicle id not found
 *   - vehicle already rented
 *   - customer does not exist
 * Because it extends Exception (not RuntimeException), the
 * compiler forces us to handle it with try/catch.
 * ------------------------------------------------------------
 */
public class RentalException extends Exception {

    public RentalException(String message) {
        super(message);   // pass message to the parent Exception class
    }
}
