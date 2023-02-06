package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.product.Product;
import ru.akirakozov.sd.refactoring.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static ru.akirakozov.sd.refactoring.product.DataBase.getConnection;

public class QueryTest extends AbstractTest {
    @Mock
    private ProductDao productDao;

    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    private AbstractServlet servlet;
    StringWriter stringWriter = new StringWriter();
    PrintWriter printer = new PrintWriter(stringWriter);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void querySetUp() throws IOException, SQLException {
        MockitoAnnotations.openMocks(this);
        servlet = new QueryServlet(productDao);

        when(servletResponse.getWriter()).thenReturn(printer);

        productDao.insertProduct(new Product("iphone6", 300));
        productDao.insertProduct(new Product("iphone12", 600));
        productDao.insertProduct(new Product("iphone14", 6000));
    }

    @Test
    public void queryMax() throws SQLException {
        Product product = new Product("iphone14", 6000);

        when(servletRequest.getParameter("command")).thenReturn("max");
        when(productDao.getMaxPriceProduct()).thenReturn(Optional.of(product));

        servlet.doGet(servletRequest, servletResponse);
        Assert.assertEquals("<html><body>\n<h1>Product with max price: </h1>\n" + product.toHTML() + "\n</body></html>\n", stringWriter.toString());
    }

    @Test
    public void queryMin() throws SQLException {
        Product product = new Product("iphone6", 300);

        when(servletRequest.getParameter("command")).thenReturn("min");
        when(productDao.getMinPriceProduct()).thenReturn(Optional.of(product));

        servlet.doGet(servletRequest, servletResponse);
        Assert.assertEquals("<html><body>\n<h1>Product with min price: </h1>\n" + product.toHTML() + "\n</body></html>\n", stringWriter.toString());
    }

    @Test
    public void querySum() throws SQLException {
        when(servletRequest.getParameter("command")).thenReturn("sum");
        when(productDao.getProductsSum()).thenReturn(Long.valueOf(6900));

        servlet.doGet(servletRequest, servletResponse);
        Assert.assertEquals("<html><body>\nSummary price: \n6900\n</body></html>\n", stringWriter.toString());
    }

    @Test
    public void queryCount() throws SQLException {
        when(servletRequest.getParameter("command")).thenReturn("count");
        when(productDao.getProductsCount()).thenReturn(3);

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

