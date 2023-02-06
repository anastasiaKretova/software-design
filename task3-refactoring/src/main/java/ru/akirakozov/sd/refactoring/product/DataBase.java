package ru.akirakozov.sd.refactoring.product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    public static Connection getConnection() throws SQLException {
        String DB_URL = "jdbc:sqlite:test.db";
        return DriverManager.getConnection(DB_URL);
    }
}
