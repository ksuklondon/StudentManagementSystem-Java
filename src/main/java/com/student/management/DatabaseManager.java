package com.student.management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:student_management.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void initializeDatabase() {
        try (Connection connection = getConnection()) {
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS students (
                    studentID TEXT PRIMARY KEY,
                    name TEXT NOT NULL,
                    age INTEGER NOT NULL,
                    grade REAL NOT NULL
                )
            """;
            connection.createStatement().execute(createTableSQL);
            System.out.println("Tabela 'students' została utworzona.");
        } catch (SQLException e) {
            System.err.println("SQL Error while initializing database: " + e.getMessage());
            throw new RuntimeException("SQL Error while initializing database", e);
        }
    }
}
