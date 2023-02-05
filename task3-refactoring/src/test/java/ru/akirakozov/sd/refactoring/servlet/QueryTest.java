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

public class QueryTest extends AbstractTest {
    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    private QueryServlet servlet;
    StringWriter stringWriter = new StringWriter();
    PrintWriter printer = new PrintWriter(stringWriter);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void querySetUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        servlet = new QueryServlet();

        when(servletResponse.getWriter()).thenReturn(printer);

        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"iphone6\",300), (\"iphone12\",600), (\"iphone14\",6000)";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void queryMax() throws IOException {
        when(servletRequest.getParameter("command")).thenReturn("max");

        servlet.doGet(servletRequest, servletResponse);
        Assert.assertEquals("<html><body>\n<h1>Product with max price: </h1>\niphone14\t6000</br>\n</body></html>\n", stringWriter.toString());
    }

    @Test
    public void queryMin() throws IOException {
        when(servletRequest.getParameter("command")).thenReturn("min");

        servlet.doGet(servletRequest, servletResponse);
        Assert.assertEquals("<html><body>\n<h1>Product with min price: </h1>\niphone6\t300</br>\n</body></html>\n", stringWriter.toString());
    }

    @Test
    public void querySum() throws IOException {
        when(servletRequest.getParameter("command")).thenReturn("sum");

        servlet.doGet(servletRequest, servletResponse);
        Assert.assertEquals("<html><body>\nSummary price: \n6900\n</body></html>\n", stringWriter.toString());
    }

    @Test
    public void queryCount() throws IOException {
        when(servletRequest.getParameter("command")).thenReturn("count");

        servlet.doGet(servletRequest, servletResponse);
        Assert.assertEquals("<html><body>\nNumber of products: \n3\n</body></html>\n", stringWriter.toString());
    }

    @Test
    public void queryUndefined() throws IOException {
        when(servletRequest.getParameter("command")).thenReturn("fff");

        servlet.doGet(servletRequest, servletResponse);
        Assert.assertEquals("Unknown command: fff\n", stringWriter.toString());
    }
}

