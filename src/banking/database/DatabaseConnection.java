package banking.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    // H2 database configuration
    private static final String DATABASE_DIRECTORY = "data";

    private static final String JDBC_URL =
            "jdbc:h2:file:./data/BankingSystemDB";

    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    // Prevent object creation
    private DatabaseConnection() {
    }

    // Establish a database connection
    public static Connection getConnection() throws SQLException {

        try {
            Files.createDirectories(
                Path.of(DATABASE_DIRECTORY)
            );
        } catch (IOException e) {
            throw new SQLException(
                "Unable to create database directory.", e
            );
        }

        return DriverManager.getConnection(
            JDBC_URL,
            USERNAME,
            PASSWORD
        );
    }
}