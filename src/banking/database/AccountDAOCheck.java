
package banking.database;

import java.util.List;

import banking.Account;
import banking.ChequeAccount;
import banking.Customer;
import banking.InvestmentAccount;
import banking.SavingsAccount;

public class AccountDAOCheck {

    public static void main(String[] args) throws Exception {

        // 1. Initialize database
        DatabaseInitializer.initialize();

        CustomerDAO customerDAO = new CustomerDAO();
        AccountDAO accountDAO = new AccountDAO();

        // 2. Create test customer
        String id = String.valueOf(System.currentTimeMillis());

        Customer customer = new Customer(
            "ACC" + id,
            "Test",
            "Customer",
            "71000000",
            "account.test." + id + "@example.com"
        );

        boolean customerCreated =
            customerDAO.create(customer, "TEST_HASH", "TEST_SALT");

        System.out.println("CREATE CUSTOMER: " + customerCreated);

        if (!customerCreated) {
            throw new IllegalStateException("Test customer creation failed.");
        }

        // 3. CREATE Savings Account
        Account savings = new SavingsAccount(
            "SAV" + id,
            1000.00,
            customer
        );

        System.out.println("CREATE SAVINGS: " +
            accountDAO.create(savings));

        // 4. READ Savings Account
        Account retrievedSavings =
            accountDAO.findByAccountNumber(savings.getAccountNumber());

        System.out.println("READ SAVINGS: " +
            (retrievedSavings instanceof SavingsAccount));

        // 5. READ accounts belonging to customer
        List<Account> accounts =
            accountDAO.findByCustomerId(customer.getCustomerId());

        System.out.println("READ BY CUSTOMER: " +
            accounts.size());

        // 6. UPDATE Savings Account balance
        System.out.println("UPDATE SAVINGS: " +
            accountDAO.updateBalance(
                savings.getAccountNumber(), 1500.00
            ));

        Account updatedSavings =
            accountDAO.findByAccountNumber(savings.getAccountNumber());

        System.out.println("UPDATED BALANCE: " +
            (updatedSavings != null
                ? updatedSavings.getBalance()
                : "Account not found"));

        // 7. CREATE Investment Account
        Account investment = new InvestmentAccount(
            "INV" + id,
            1000.00,
            customer
        );

        System.out.println("CREATE INVESTMENT: " +
            accountDAO.create(investment));

        // 8. READ Investment Account
        Account retrievedInvestment =
            accountDAO.findByAccountNumber(investment.getAccountNumber());

        System.out.println("READ INVESTMENT: " +
            (retrievedInvestment instanceof InvestmentAccount));

        // 9. CREATE Cheque Account
        Account cheque = new ChequeAccount(
            "CHQ" + id,
            2000.00,
            customer,
            true,
            "Test Company",
            "Gaborone"
        );

        System.out.println("CREATE CHEQUE: " +
            accountDAO.create(cheque));

        // 10. READ Cheque Account
        Account retrievedCheque =
            accountDAO.findByAccountNumber(cheque.getAccountNumber());

        System.out.println("READ CHEQUE: " +
            (retrievedCheque instanceof ChequeAccount));

        // 11. DELETE Savings Account
        System.out.println("DELETE SAVINGS: " +
            accountDAO.deleteByAccountNumber(
                savings.getAccountNumber()
            ));

        // 12. VERIFY Savings Account deletion
        System.out.println("VERIFY DELETE: " +
            (accountDAO.findByAccountNumber(
                savings.getAccountNumber()
            ) == null));

        // 13. Clean up remaining test data
        accountDAO.deleteByAccountNumber(investment.getAccountNumber());
        accountDAO.deleteByAccountNumber(cheque.getAccountNumber());
        customerDAO.deleteById(customer.getCustomerId());

        System.out.println("TEST CLEANUP: COMPLETE");
    }
}
