/*
 * Customer.java
 * ------------------------------------------------------------
 * A plain ENCAPSULATED class: all fields private, access only
 * through public getters and setters (some with validation).
 * ------------------------------------------------------------
 */
public class Customer {

    private String customerId;
    private String name;
    private String phoneNumber;
    private String drivingLicense;

    public Customer(String customerId, String name, String phoneNumber, String drivingLicense) {
        this.customerId     = customerId;
        this.name           = name;
        this.phoneNumber    = phoneNumber;
        this.drivingLicense = drivingLicense;
    }

    public String getCustomerId()     { return customerId; }
    public String getName()           { return name; }
    public String getPhoneNumber()    { return phoneNumber; }
    public String getDrivingLicense() { return drivingLicense; }

    public void setName(String name) { this.name = name; }

    // Validation inside the setter = the real benefit of encapsulation
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.length() == 10) {
            this.phoneNumber = phoneNumber;
        } else {
            System.out.println("!! Phone number must be 10 digits. Value ignored.");
        }
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public void displayDetails() {
        System.out.printf("%-6s | %-15s | %-12s | %s%n",
                customerId, name, phoneNumber, drivingLicense);
    }

    @Override
    public String toString() {
        return name + " (" + customerId + ")";
    }
}
