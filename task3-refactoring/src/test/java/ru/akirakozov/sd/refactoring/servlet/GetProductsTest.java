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
import java.util.Collections;

import static org.mockito.Mockito.when;
import static ru.akirakozov.sd.refactoring.product.DataBase.getConnection;

public class GetProductsTest extends AbstractTest {

    @Mock
    private ProductDao productDao;

    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    private AbstractServlet servlet;

    StringWriter stringWriter = new StringWriter();
    PrintWriter printer = new PrintWriter(stringWriter);

    @Before
    public void getSetUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        servlet = new GetProductsServlet(productDao);

        when(servletResponse.getWriter()).thenReturn(printer);
    }

    @Test
    public void getProductEmpty() throws SQLException, IOException {
        servlet.doRequest(servletRequest, servletResponse);
        Assert.assertEquals("<html><body>\n</body></html>\n", stringWriter.toString());
    }

    @Test
    public void getProductServletTest() throws SQLException {
        Product product = new Product("iphone6", 300);
        when(productDao.getProducts()) .thenReturn(Arrays.asList(product));
        servlet.doGet(servletRequest, servletResponse);
        Assert.assertEquals("<html><body>\n" + product.toHTML() + "\n</body></html>\n", stringWriter.toString());
    }

}
