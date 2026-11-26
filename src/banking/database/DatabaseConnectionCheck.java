
package banking.database;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionCheck {

    public static void main(String[] args) {

        System.out.println("Connecting to BankingSystemDB...");

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            System.out.println("Connection successful!");

            System.out.println(
                "Database: " +
                connection.getMetaData().getDatabaseProductName()
            );

            System.out.println(
                "JDBC URL: " +
                connection.getMetaData().getURL()
            );

            System.out.println(
                "Connection valid: " +
                connection.isValid(2)
            );

        } catch (SQLException e) {

            System.err.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}
