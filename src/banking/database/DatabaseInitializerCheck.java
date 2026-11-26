package banking.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseInitializerCheck {

    public static void main(String[] args) {

        System.out.println("Initializing BankingSystemDB...");

        try {
            // Create the database tables
            DatabaseInitializer.initialize();

            // Verify that the tables exist
            try (Connection connection =
                     DatabaseConnection.getConnection()) {

                String sql = """
                    SELECT COUNT(*)
                    FROM INFORMATION_SCHEMA.TABLES
                    WHERE TABLE_SCHEMA = 'PUBLIC'
                    AND TABLE_NAME = ?
                    """;

                String[] tables = {
                    "CUSTOMERS",
                    "ACCOUNTS",
                    "TRANSACTIONS"
                };

                try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                    for (String table : tables) {

                        statement.setString(1, table);

                        try (ResultSet result =
                                 statement.executeQuery()) {

                            if (result.next() &&
                                result.getInt(1) == 1) {

                                System.out.println(
                                    table + " - CREATED"
                                );

                            } else {
                                System.out.println(
                                    table + " - NOT FOUND"
                                );
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println(
                "Database initialization failed!"
            );
            e.printStackTrace();
        }
    }
}
