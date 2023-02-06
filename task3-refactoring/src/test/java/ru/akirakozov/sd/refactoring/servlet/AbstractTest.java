package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Before;

import java.sql.SQLException;

import static ru.akirakozov.sd.refactoring.product.DataBase.createTable;
import static ru.akirakozov.sd.refactoring.product.DataBase.dropTable;

public class AbstractTest {
    @Before
    public void setUp() throws SQLException {
        createTable();
    }

    @After
    public void cleanUp() throws SQLException {
        dropTable();
    }
}
