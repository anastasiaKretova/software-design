package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.when;

public class GetProductsTest extends AbstractTest {
    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    private GetProductsServlet servlet;
    StringWriter stringWriter = new StringWriter();
    PrintWriter printer = new PrintWriter(stringWriter);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void getSetUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        servlet = new GetProductsServlet();

        when(servletResponse.getWriter()).thenReturn(printer);
    }

    @Test
    public void getProductEmpty() throws SQLException, IOException {
        servlet.doGet(servletRequest, servletResponse);
        Assert.assertEquals("<html><body>\n</body></html>\n", stringWriter.toString());
    }

    @Test
    public void getProductSuccess() throws SQLException, IOException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"iphone6\",300)";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        servlet.doGet(servletRequest, servletResponse);
        Assert.assertEquals("<html><body>\niphone6\t300</br>\n</body></html>\n", stringWriter.toString());
    }

}

