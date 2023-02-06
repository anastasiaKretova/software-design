package ru.akirakozov.sd.refactoring.product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    public static Connection getConnection() throws SQLException {
        String DB_URL = "jdbc:sqlite:test.db";
        return DriverManager.getConnection(DB_URL);
    }

    public static void createTable() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";

            statement.executeUpdate(sql);
        }
    }

    public static void dropTable() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            String sql = "DELETE FROM PRODUCT";
            statement.executeUpdate(sql);
        }
    }
}
