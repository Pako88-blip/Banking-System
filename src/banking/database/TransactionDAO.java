package banking.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // CREATE TRANSACTION
    public boolean create(
            String accountNumber,
            String transactionType,
            BigDecimal amount,
            BigDecimal balanceAfter) throws SQLException {

        String sql = """
            INSERT INTO TRANSACTIONS (
                ACCOUNT_NUMBER,
                TRANSACTION_TYPE,
                AMOUNT,
                BALANCE_AFTER
            )
            VALUES (?, ?, ?, ?)
            """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, accountNumber);
            statement.setString(2, transactionType);
            statement.setBigDecimal(3, amount);
            statement.setBigDecimal(4, balanceAfter);

            return statement.executeUpdate() == 1;
        }
    }

    // FIND TRANSACTION BY ID
    public TransactionRecord findById(long transactionId)
            throws SQLException {

        String sql = """
            SELECT *
            FROM TRANSACTIONS
            WHERE TRANSACTION_ID = ?
            """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setLong(1, transactionId);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return mapTransaction(result);
                }

                return null;
            }
        }
    }

    // FIND TRANSACTIONS BY ACCOUNT NUMBER
    public List<TransactionRecord> findByAccountNumber(
            String accountNumber) throws SQLException {

        List<TransactionRecord> transactions = new ArrayList<>();

        String sql = """
            SELECT *
            FROM TRANSACTIONS
            WHERE ACCOUNT_NUMBER = ?
            ORDER BY TRANSACTION_DATE DESC, TRANSACTION_ID DESC
            """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, accountNumber);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    transactions.add(mapTransaction(result));
                }
            }
        }

        return transactions;
    }

    // FIND ALL TRANSACTIONS
    public List<TransactionRecord> findAll() throws SQLException {

        List<TransactionRecord> transactions = new ArrayList<>();

        String sql = """
            SELECT *
            FROM TRANSACTIONS
            ORDER BY TRANSACTION_DATE DESC, TRANSACTION_ID DESC
            """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery()
        ) {

            while (result.next()) {
                transactions.add(mapTransaction(result));
            }
        }

        return transactions;
    }

    // MAP DATABASE RESULT TO TRANSACTION RECORD
    private TransactionRecord mapTransaction(ResultSet result)
            throws SQLException {

        return new TransactionRecord(
            result.getLong("TRANSACTION_ID"),
            result.getString("ACCOUNT_NUMBER"),
            result.getString("TRANSACTION_TYPE"),
            result.getBigDecimal("AMOUNT"),
            result.getTimestamp("TRANSACTION_DATE"),
            result.getBigDecimal("BALANCE_AFTER")
        );
    }
    // TRANSACTION DATA MODEL
    public static class TransactionRecord {

        private final long transactionId;
        private final String accountNumber;
        private final String transactionType;
        private final BigDecimal amount;
        private final Timestamp transactionDate;
        private final BigDecimal balanceAfter;

        public TransactionRecord(
                long transactionId,
                String accountNumber,
                String transactionType,
                BigDecimal amount,
                Timestamp transactionDate,
                BigDecimal balanceAfter) {

            this.transactionId = transactionId;
            this.accountNumber = accountNumber;
            this.transactionType = transactionType;
            this.amount = amount;
            this.transactionDate = transactionDate;
            this.balanceAfter = balanceAfter;
        }

        public long getTransactionId() {
            return transactionId;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public Timestamp getTransactionDate() {
            return transactionDate;
        }

        public BigDecimal getBalanceAfter() {
            return balanceAfter;
        }
    }
}
