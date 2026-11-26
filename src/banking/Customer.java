package banking;

public class Customer {

    // Customer details
    private String customerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    // Constructor
    public Customer(String customerId,
                    String firstName,
                    String lastName,
                    String phoneNumber,
                    String email) {

        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters
    public String getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}