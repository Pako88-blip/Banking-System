package banking;

public class Customer {

    // Customer details
    private String customerId;
    private String fullName;
    private String phoneNumber;
    private String email;

    // Constructor
    public Customer(String customerId, String fullName,
                    String phoneNumber, String email) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    
    //getters
    public String getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}