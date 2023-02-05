package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AbstractTest {
    @Before
    public void setUp() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    @After
    public void cleanUp() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            String sql = "DELETE FROM PRODUCT";
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }
}
