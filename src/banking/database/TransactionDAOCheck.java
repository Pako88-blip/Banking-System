
package banking.database;

import java.math.BigDecimal;
import java.util.List;

import banking.Account;
import banking.Customer;
import banking.SavingsAccount;

public class TransactionDAOCheck {

    public static void main(String[] args) throws Exception {

        // 1. Initialize database
        DatabaseInitializer.initialize();

        CustomerDAO customerDAO = new CustomerDAO();
        AccountDAO accountDAO = new AccountDAO();
        TransactionDAO transactionDAO = new TransactionDAO();

        // 2. Create test customer
        String id = String.valueOf(System.currentTimeMillis());

        Customer customer = new Customer(
            "TRX" + id,
            "Transaction",
            "Tester",
            "72000000",
            "transaction.test." + id + "@example.com"
        );

        boolean customerCreated =
            customerDAO.create(customer, "TEST_HASH", "TEST_SALT");

        System.out.println("CREATE CUSTOMER: " + customerCreated);

        if (!customerCreated) {
            throw new IllegalStateException("Customer creation failed.");
        }

        // 3. Create test savings account
        Account account = new SavingsAccount(
            "TRX" + id,
            2000.00,
            customer
        );

        boolean accountCreated = accountDAO.create(account);

        System.out.println("CREATE ACCOUNT: " + accountCreated);

        if (!accountCreated) {
            throw new IllegalStateException("Account creation failed.");
        }

        // 4. CREATE transactions
        System.out.println("CREATE DEPOSIT: " +
            transactionDAO.create(
                account.getAccountNumber(),
                "DEPOSIT",
                new BigDecimal("500.00"),
                new BigDecimal("2500.00")
            ));

        System.out.println("CREATE WITHDRAWAL: " +
            transactionDAO.create(
                account.getAccountNumber(),
                "WITHDRAWAL",
                new BigDecimal("200.00"),
                new BigDecimal("2300.00")
            ));

        // 5. READ transactions by account number
        List<TransactionDAO.TransactionRecord> history =
            transactionDAO.findByAccountNumber(
                account.getAccountNumber()
            );

        System.out.println("READ ACCOUNT HISTORY: " + history.size());

        // 6. READ transaction by ID
        if (!history.isEmpty()) {

            long transactionId = history.get(0).getTransactionId();

            TransactionDAO.TransactionRecord retrieved =
                transactionDAO.findById(transactionId);

            System.out.println("READ BY ID: " +
                (retrieved != null));

            System.out.println("TRANSACTION TYPE: " +
                retrieved.getTransactionType());

            System.out.println("TRANSACTION AMOUNT: " +
                retrieved.getAmount());
        }

        // 7. READ all transactions
        List<TransactionDAO.TransactionRecord> allTransactions =
            transactionDAO.findAll();

        System.out.println("READ ALL TRANSACTIONS: " +
            allTransactions.size());

        System.out.println("TRANSACTION DAO TEST: COMPLETE");
    }
}
