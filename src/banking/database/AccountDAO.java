
package banking.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import banking.Account;
import banking.ChequeAccount;
import banking.Customer;
import banking.InvestmentAccount;
import banking.SavingsAccount;

public class AccountDAO {

    // CREATE ACCOUNT
    public boolean create(Account account) throws SQLException {

        String accountType;
        boolean employed = false;
        String employer = null;
        String companyAddress = null;

        if (account instanceof SavingsAccount) {
            accountType = "SAVINGS";

        } else if (account instanceof InvestmentAccount) {
            accountType = "INVESTMENT";

        } else if (account instanceof ChequeAccount chequeAccount) {
            accountType = "CHEQUE";
            employed = true;
            employer = chequeAccount.getEmployer();
            companyAddress = chequeAccount.getCompanyAddress();

        } else {
            throw new IllegalArgumentException("Unsupported account type");
        }

        String sql = """
            INSERT INTO ACCOUNTS (
                ACCOUNT_NUMBER,
                CUSTOMER_ID,
                ACCOUNT_TYPE,
                BALANCE,
                INITIAL_DEPOSIT,
                IS_EMPLOYED,
                EMPLOYER_NAME,
                EMPLOYER_ADDRESS
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, account.getAccountNumber());
            statement.setString(2, account.getOwner().getCustomerId());
            statement.setString(3, accountType);

            BigDecimal balance = BigDecimal.valueOf(account.getBalance());

            statement.setBigDecimal(4, balance);
            statement.setBigDecimal(5, balance);
            statement.setBoolean(6, employed);
            statement.setString(7, employer);
            statement.setString(8, companyAddress);

            return statement.executeUpdate() == 1;
        }
    }

    // FIND ACCOUNT BY ACCOUNT NUMBER
    public Account findByAccountNumber(String accountNumber)
            throws SQLException {

        String sql = """
            SELECT ACCOUNT_NUMBER, CUSTOMER_ID, ACCOUNT_TYPE,
            BALANCE, IS_EMPLOYED, EMPLOYER_NAME,
            EMPLOYER_ADDRESS
            FROM ACCOUNTS
            WHERE ACCOUNT_NUMBER = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);

            try (ResultSet result = statement.executeQuery()) {

                if (!result.next()) {
                    return null;
                }

                String number = result.getString("ACCOUNT_NUMBER");
                String customerId = result.getString("CUSTOMER_ID");
                String type = result.getString("ACCOUNT_TYPE");
                double balance = result.getDouble("BALANCE");

                CustomerDAO customerDAO = new CustomerDAO();
                Customer owner = customerDAO.findById(customerId);

                if (owner == null) {
                    return null;
                }

                switch (type) {

                    case "SAVINGS":
                        return new SavingsAccount(number, balance, owner);

                    case "INVESTMENT":
                        return new InvestmentAccount(
                            number, balance, owner, true
                        );

                    case "CHEQUE":
                        boolean employed =
                            result.getBoolean("IS_EMPLOYED");

                        String employer =
                            result.getString("EMPLOYER_NAME");

                        String address =
                            result.getString("EMPLOYER_ADDRESS");

                        return new ChequeAccount(
                            number, balance, owner,
                            employed, employer, address
                        );

                    default:
                        throw new SQLException(
                            "Unknown account type: " + type
                        );
                }
            }
        }
    }

    // FIND ALL ACCOUNTS BELONGING TO A CUSTOMER
    public List<Account> findByCustomerId(String customerId)
            throws SQLException {

        List<Account> accounts = new ArrayList<>();

        String sql = """
            SELECT ACCOUNT_NUMBER
            FROM ACCOUNTS
            WHERE CUSTOMER_ID = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customerId);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    String accountNumber =
                        result.getString("ACCOUNT_NUMBER");

                    Account account =
                        findByAccountNumber(accountNumber);

                    if (account != null) {
                        accounts.add(account);
                    }
                }
            }
        }

        return accounts;
    }

    // UPDATE ACCOUNT BALANCE
    public boolean updateBalance(String accountNumber, double newBalance)
            throws SQLException {

        if (newBalance < 0) {
            throw new IllegalArgumentException(
                "Account balance cannot be negative."
            );
        }

        String sql = """
            UPDATE ACCOUNTS
            SET BALANCE = ?
            WHERE ACCOUNT_NUMBER = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBigDecimal(
                1, BigDecimal.valueOf(newBalance)
            );

            statement.setString(2, accountNumber);

            return statement.executeUpdate() == 1;
        }
    }

    // DELETE ACCOUNT BY ACCOUNT NUMBER
    public boolean deleteByAccountNumber(String accountNumber)
            throws SQLException {

        String sql = """
            DELETE FROM ACCOUNTS
            WHERE ACCOUNT_NUMBER = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);

            return statement.executeUpdate() == 1;
        }
    }

}
