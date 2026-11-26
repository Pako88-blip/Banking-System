
package banking.database;

import banking.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    public boolean create(Customer customer,
            String passwordHash,
            String passwordSalt) throws SQLException {

        String sql = """
            INSERT INTO CUSTOMERS (
                CUSTOMER_ID,
                FIRST_NAME,
                LAST_NAME,
                PHONE_NUMBER,
                EMAIL,
                PASSWORD_HASH,
                PASSWORD_SALT
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setString(1, customer.getCustomerId());
            statement.setString(2, customer.getFirstName());
            statement.setString(3, customer.getLastName());
            statement.setString(4, customer.getPhoneNumber());
            statement.setString(5, customer.getEmail());
            statement.setString(6, passwordHash);
            statement.setString(7, passwordSalt);

            return statement.executeUpdate() == 1;
        }
    }


public Customer findById(String customerId) throws SQLException {

    String sql = """
        SELECT CUSTOMER_ID, FIRST_NAME, LAST_NAME,
        PHONE_NUMBER, EMAIL
        FROM CUSTOMERS
        WHERE CUSTOMER_ID = ?
        """;

    try (
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)
    ) {

        statement.setString(1, customerId);

        try (ResultSet result = statement.executeQuery()) {
            if (result.next()) {
            return new Customer(
                result.getString("CUSTOMER_ID"),
                result.getString("FIRST_NAME"),
                result.getString("LAST_NAME"),
                result.getString("PHONE_NUMBER"),
                result.getString("EMAIL")
                );
            }

            return null;
        }
    }
}

public Customer findByEmail(String email) throws SQLException {

    String sql = """
        SELECT CUSTOMER_ID, FIRST_NAME, LAST_NAME,
        PHONE_NUMBER, EMAIL
        FROM CUSTOMERS
        WHERE EMAIL = ?
        """;

    try (
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)
    ) {

        statement.setString(1, email);

        try (ResultSet result = statement.executeQuery()) {

            if (result.next()) {
                return new Customer(
                    result.getString("CUSTOMER_ID"),
                    result.getString("FIRST_NAME"),
                    result.getString("LAST_NAME"),
                    result.getString("PHONE_NUMBER"),
                    result.getString("EMAIL")
                );
            }

            return null;
        }
    }
}

public boolean update(Customer customer) throws SQLException {

    String sql = """
        UPDATE CUSTOMERS
        SET FIRST_NAME = ?,
            LAST_NAME = ?,
            PHONE_NUMBER = ?,
            EMAIL = ?
        WHERE CUSTOMER_ID = ?
        """;

    try (
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)
    ) {

        statement.setString(1, customer.getFirstName());
        statement.setString(2, customer.getLastName());
        statement.setString(3, customer.getPhoneNumber());
        statement.setString(4, customer.getEmail());
        statement.setString(5, customer.getCustomerId());

        return statement.executeUpdate() == 1;
    }
}

public boolean deleteById(String customerId) throws SQLException {

    String sql = """
        DELETE FROM CUSTOMERS
        WHERE CUSTOMER_ID = ?
        """;

    try (
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)
    ) {

        statement.setString(1, customerId);

        return statement.executeUpdate() == 1;
    }
}

}
