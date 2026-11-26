package banking.database;

import banking.Customer;

public class CustomerDAOCheck {

    public static void main(String[] args) throws Exception {

        DatabaseInitializer.initialize();

        CustomerDAO customerDAO = new CustomerDAO();

        String id = String.valueOf(System.currentTimeMillis());

        Customer customer = new Customer(
            "CUS" + id,
            "Test",
            "Customer",
            "73000000",
            "customer.test." + id + "@example.com"
        );

        // 1. CREATE
        boolean created =
            customerDAO.create(customer, "TEST_HASH", "TEST_SALT");

        System.out.println("CREATE CUSTOMER: " + created);

        if (!created) {
            throw new IllegalStateException("Customer creation failed.");
        }

        // 2. READ BY ID
        Customer retrieved =
            customerDAO.findById(customer.getCustomerId());

        System.out.println("READ BY ID: " + (retrieved != null));

        // 3. READ BY EMAIL
        Customer byEmail =
            customerDAO.findByEmail(customer.getEmail());

        System.out.println("READ BY EMAIL: " + (byEmail != null));

        // 4. UPDATE
        Customer updated = new Customer(
            customer.getCustomerId(),
            "Updated",
            "Customer",
            "73999999",
            customer.getEmail()
        );

        boolean updateResult = customerDAO.update(updated);

        System.out.println("UPDATE CUSTOMER: " + updateResult);

        Customer verified =
            customerDAO.findById(customer.getCustomerId());

        System.out.println("VERIFY UPDATE: " +
            (verified != null &&
            verified.getFirstName().equals("Updated") &&
            verified.getPhoneNumber().equals("73999999")));

        // 5. DELETE
        boolean deleted =
            customerDAO.deleteById(customer.getCustomerId());

        System.out.println("DELETE CUSTOMER: " + deleted);

        Customer afterDeletion =
            customerDAO.findById(customer.getCustomerId());

        System.out.println("VERIFY DELETE: " +
            (afterDeletion == null));

        System.out.println("CUSTOMER DAO TEST: COMPLETE");
    }
}
